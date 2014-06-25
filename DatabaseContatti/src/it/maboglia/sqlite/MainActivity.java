package it.maboglia.sqlite;

import it.maboglia.sqlite.R;
import it.maboglia.sqlite.helper.DatabaseHelper;
import it.maboglia.sqlite.model.Cartella;
import it.maboglia.sqlite.model.SMS;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends Activity {

	// Database Helper
	DatabaseHelper db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		db = new DatabaseHelper(getApplicationContext());

		// Crea Cartelle
		Cartella cartella1 = new Cartella("Famiglia");
		Cartella cartella2 = new Cartella("Amici");
		Cartella cartella3 = new Cartella("Lavoro");
		Cartella cartella4 = new Cartella("Sport");

		// Inserisci Cartelle in db
		long cartella1_id = db.createTag(cartella1);
		long cartella2_id = db.createTag(cartella2);
		long cartella3_id = db.createTag(cartella3);
		long cartella4_id = db.createTag(cartella4);

		Log.d("Cartella Count", "Cartella Count: " + db.getAllTags().size());

		// Creare nuovi SMS
		SMS SMS1 = new SMS("Messaggio 1", 0);
		SMS SMS2 = new SMS("Messaggio 2", 0);
		SMS SMS3 = new SMS("Messaggio 222", 0);

		SMS SMS4 = new SMS("Messaggio 333", 0);
		SMS SMS5 = new SMS("Messaggio 444", 0);
		SMS SMS6 = new SMS("Messaggio 564", 0);
		SMS SMS7 = new SMS("Messaggio 365", 0);

		SMS SMS8 = new SMS("Messaggio ciao", 0);
		SMS SMS9 = new SMS("Messaggio 1564564", 0);

		SMS SMS10 = new SMS("Messaggio 2365", 0);
		SMS SMS11 = new SMS("Messaggio 12313", 0);

		// Riempio il database
		long SMS1_id = db.createSMS(SMS1, new long[] { cartella1_id });
		long SMS2_id = db.createSMS(SMS2, new long[] { cartella1_id });
		long SMS3_id = db.createSMS(SMS3, new long[] { cartella1_id });

		// Riempio il database
		long SMS4_id = db.createSMS(SMS4, new long[] { cartella3_id });
		long SMS5_id = db.createSMS(SMS5, new long[] { cartella3_id });
		long SMS6_id = db.createSMS(SMS6, new long[] { cartella3_id });
		long SMS7_id = db.createSMS(SMS7, new long[] { cartella3_id });

		// Riempio il database
		long SMS8_id = db.createSMS(SMS8, new long[] { cartella2_id });
		long SMS9_id = db.createSMS(SMS9, new long[] { cartella2_id });

		// Riempio il database
		long SMS10_id = db.createSMS(SMS10, new long[] { cartella4_id });
		long SMS11_id = db.createSMS(SMS11, new long[] { cartella4_id });

		Log.e("SMS Count", "SMS count: " + db.getSMSCount());

		// Assegno messaggi a cartelle
		db.createSMSTag(SMS10_id, cartella2_id);

		// Mostra tutte le Cartelle
		Log.d("Get Cartelle", "Mostra tutte le Cartelle");

		List<Cartella> allTags = db.getAllTags();
		for (Cartella cartella : allTags) {
			Log.d("Cartella Name", cartella.getTagName());
		}

		// Tutti gli SMS
		Log.d("Get SMS", "Mostra tutti gli SMS");

		List<SMS> allSMS = db.getAllSMS();
		for (SMS SMS : allSMS) {
			Log.d("SMS", SMS.getNote());
		}

		// Lista SMS per Cartella
		Log.d("SMS", "Mostra singolo SMS");

		List<SMS> cartelleWatchList = db.getAllSMSByTag(cartella3.getTagName());
		for (SMS SMS : cartelleWatchList) {
			Log.d("SMS Watchlist", SMS.getNote());
		}

		// Elimina SMS
		Log.d("Delete SMS", "Deleting a SMS");
		Log.d("Cartella Count", "Cartella Count prima di cancellare: " + db.getSMSCount());

		db.deleteSMS(SMS8_id);

		Log.d("Cartella Count", "Cartella Count Dopo aver cancellato: " + db.getSMSCount());

		// Deleting all SMS under "Shopping" Cartella
		Log.d("Cartella Count",
				"Cartella Count prima di cancellare 'Famiglia' SMS: "
						+ db.getSMSCount());

		db.deleteTag(cartella1, true);

		Log.d("Cartella Count",
				"Cartella Count Dopo aver cancellato 'Famiglia' SMS: "
						+ db.getSMSCount());

		// Update Cartella 
		cartella3.setTagName("Film");
		db.updateTag(cartella3);

		// Chiudi la connessione al database
		db.closeDB();
		
	}
	
	//implementa i metodi per i pulsanti d'azione
	//crea e inserisci le activity nel manifest
	public void contaContatti(View v) {
		// TODO Auto-generated method stub

	}
	public void contaPerCartella(View v) {
		// TODO Auto-generated method stub
		
	}
	public void listaContatti(View v) {
		// TODO Auto-generated method stub
		
	}
	public void listaCartelle(View v) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
