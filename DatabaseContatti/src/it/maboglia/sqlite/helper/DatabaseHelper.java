package it.maboglia.sqlite.helper;

import it.maboglia.sqlite.model.Cartella;
import it.maboglia.sqlite.model.SMS;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

	// Logcat Cartella
	private static final String LOG = "DatabaseHelper";

	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "contactsManager";

	// Table Names
	private static final String TABLE_SMS = "SMS";
	private static final String TABLE_Cartella = "cartella";
	private static final String TABLE_SMS_Cartella = "SMS_Cartellas";

	// Common nome campi
	private static final String KEY_ID = "id";
	private static final String KEY_CREATED_AT = "created_at";

	// NOTES Table - nome campi
	private static final String KEY_SMS = "SMS";
	private static final String KEY_STATUS = "status";

	// Cartelle Table - nome campi
	private static final String KEY_TAG_NAME = "cartella_name";

	// NOTE_Cartelle Table - nome campi
	private static final String KEY_SMS_ID = "SMS_id";
	private static final String KEY_TAG_ID = "cartella_id";

	// Table Create Statements
	// SMS table create statement
	private static final String CREATE_TABLE_SMS = "CREATE TABLE "
			+ TABLE_SMS + "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_SMS
			+ " TEXT," + KEY_STATUS + " INTEGER," + KEY_CREATED_AT
			+ " DATETIME" + ")";

	// Cartella table create statement
	private static final String CREATE_TABLE_Cartella = "CREATE TABLE " + TABLE_Cartella
			+ "(" + KEY_ID + " INTEGER PRIMARY KEY," + KEY_TAG_NAME + " TEXT,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	// SMS_cartella table create statement
	private static final String CREATE_TABLE_SMS_TAG = "CREATE TABLE "
			+ TABLE_SMS_Cartella + "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_SMS_ID + " INTEGER," + KEY_TAG_ID + " INTEGER,"
			+ KEY_CREATED_AT + " DATETIME" + ")";

	public DatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		// creating required tables
		db.execSQL(CREATE_TABLE_SMS);
		db.execSQL(CREATE_TABLE_Cartella);
		db.execSQL(CREATE_TABLE_SMS_TAG);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// on upgrade drop older tables
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Cartella);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_SMS_Cartella);

		// create new tables
		onCreate(db);
	}

	// ------------------------ "SMS" table methods ----------------//

	/*
	 * Crea a SMS
	 */
	public long createSMS(SMS SMS, long[] cartella_ids) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SMS, SMS.getNote());
		values.put(KEY_STATUS, SMS.getStatus());
		values.put(KEY_CREATED_AT, getDateTime());

		//  row
		long SMS_id = db.insert(TABLE_SMS, null, values);

		//  cartella_ids
		for (long cartella_id : cartella_ids) {
			createSMSTag(SMS_id, cartella_id);
		}

		return SMS_id;
	}

	/*
	 *  single SMS
	 */
	public SMS getSMS(long SMS_id) {
		SQLiteDatabase db = this.getReadableDatabase();

		String selectQuery = "SELECT  * FROM " + TABLE_SMS + " WHERE "
				+ KEY_ID + " = " + SMS_id;

		Log.e(LOG, selectQuery);

		Cursor c = db.rawQuery(selectQuery, null);

		if (c != null)
			c.moveToFirst();

		SMS td = new SMS();
		td.setId(c.getInt(c.getColumnIndex(KEY_ID)));
		td.setNote((c.getString(c.getColumnIndex(KEY_SMS))));
		td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

		return td;
	}

	/**
	 *  all SMS
	 * */
	public List<SMS> getAllSMS() {
		List<SMS> SMS = new ArrayList<SMS>();
		String selectQuery = "SELECT  * FROM " + TABLE_SMS;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// loop
		if (c.moveToFirst()) {
			do {
				SMS td = new SMS();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setNote((c.getString(c.getColumnIndex(KEY_SMS))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// to SMS list
				SMS.add(td);
			} while (c.moveToNext());
		}

		return SMS;
	}

	/**
	 * single Cartella
	 * */
	public List<SMS> getAllSMSByTag(String cartella_name) {
		List<SMS> SMS = new ArrayList<SMS>();

		String selectQuery = "SELECT  * FROM " + TABLE_SMS + " td, "
				+ TABLE_Cartella + " tg, " + TABLE_SMS_Cartella + " tt WHERE tg."
				+ KEY_TAG_NAME + " = '" + cartella_name + "'" + " AND tg." + KEY_ID
				+ " = " + "tt." + KEY_TAG_ID + " AND td." + KEY_ID + " = "
				+ "tt." + KEY_SMS_ID;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// loop
		if (c.moveToFirst()) {
			do {
				SMS td = new SMS();
				td.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				td.setNote((c.getString(c.getColumnIndex(KEY_SMS))));
				td.setCreatedAt(c.getString(c.getColumnIndex(KEY_CREATED_AT)));

				// aggiungi alla SMS list
				SMS.add(td);
			} while (c.moveToNext());
		}

		return SMS;
	}

	/*
	 * conta SMS 
	 */
	public int getSMSCount() {
		String countQuery = "SELECT  * FROM " + TABLE_SMS;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(countQuery, null);

		int count = cursor.getCount();
		cursor.close();

		// return count
		return count;
	}

	/*
	 * SMS
	 */
	public int updateSMS(SMS SMS) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SMS, SMS.getNote());
		values.put(KEY_STATUS, SMS.getStatus());

		//  row
		return db.update(TABLE_SMS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(SMS.getId()) });
	}

	/*
	 * Elimina a SMS
	 */
	public void deleteSMS(long tado_id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SMS, KEY_ID + " = ?",
				new String[] { String.valueOf(tado_id) });
	}

	// ------------------------ "Cartelle"  ----------------//

	/*
	 * Crea cartella
	 */
	public long createTag(Cartella cartella) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TAG_NAME, cartella.getTagName());
		values.put(KEY_CREATED_AT, getDateTime());

		// inserisci riga
		long cartella_id = db.insert(TABLE_Cartella, null, values);

		return cartella_id;
	}

	/**
	 * tutte le Cartelle
	 * */
	public List<Cartella> getAllTags() {
		List<Cartella> cartelle = new ArrayList<Cartella>();
		String selectQuery = "SELECT  * FROM " + TABLE_Cartella;

		Log.e(LOG, selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor c = db.rawQuery(selectQuery, null);

		// loop
		if (c.moveToFirst()) {
			do {
				Cartella t = new Cartella();
				t.setId(c.getInt((c.getColumnIndex(KEY_ID))));
				t.setTagName(c.getString(c.getColumnIndex(KEY_TAG_NAME)));

				// aggiungi a cartelle list
				cartelle.add(t);
			} while (c.moveToNext());
		}
		return cartelle;
	}

	/*
	 * Updating cartella
	 */
	public int updateTag(Cartella cartella) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TAG_NAME, cartella.getTagName());

		// updating row
		return db.update(TABLE_Cartella, values, KEY_ID + " = ?",
				new String[] { String.valueOf(cartella.getId()) });
	}

	/*
	 * Elimina cartella
	 */
	public void deleteTag(Cartella cartella, boolean should_delete_all_cartella_SMS) {
		SQLiteDatabase db = this.getWritableDatabase();

		// prima di eliminare cartella
		// controlla se gli SMS in cartella devono essere eliminati
		if (should_delete_all_cartella_SMS) {
			// prendi tutti i mesg
			List<SMS> allTagSMS = getAllSMSByTag(cartella.getTagName());

			// elimina tutti gli SMS
			for (SMS SMS : allTagSMS) {
				// eliminazione SMS
				deleteSMS(SMS.getId());
			}
		}

		// elimina la cartella
		db.delete(TABLE_Cartella, KEY_ID + " = ?",
				new String[] { String.valueOf(cartella.getId()) });
	}

	// ------------------------ "SMS_cartelle" table methods ----------------//

	/*
	 * Crea SMS_cartella
	 */
	public long createSMSTag(long SMS_id, long cartella_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_SMS_ID, SMS_id);
		values.put(KEY_TAG_ID, cartella_id);
		values.put(KEY_CREATED_AT, getDateTime());

		long id = db.insert(TABLE_SMS_Cartella, null, values);

		return id;
	}

	/*
	 * Updating a SMS cartella
	 */
	public int updateNoteTag(long id, long cartella_id) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put(KEY_TAG_ID, cartella_id);

		// updating row
		return db.update(TABLE_SMS, values, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	/*
	 * Elimina a SMS cartella
	 */
	public void deleteSMSTag(long id) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(TABLE_SMS, KEY_ID + " = ?",
				new String[] { String.valueOf(id) });
	}

	// closing database
	public void closeDB() {
		SQLiteDatabase db = this.getReadableDatabase();
		if (db != null && db.isOpen())
			db.close();
	}

	/**
	 * get datetime
	 * */
	private String getDateTime() {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss", Locale.getDefault());
		Date date = new Date();
		return dateFormat.format(date);
	}
}
