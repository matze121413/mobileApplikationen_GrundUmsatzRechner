package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailansichtActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView nameFeld, grundumsatzFeld;
    private String  name;
    private int  grundumsatz, kalorienverbrauch, alter, geschlecht, id, groesse;
    private double gewicht,  schlaf, sitzend, stehend, kaumAktiv, sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailansicht);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
         id = b.getInt("id");
        dbHelper = new DbHelper(this);
        Cursor data =dbHelper.getRow(id);
        while(data.moveToNext()) {
            name = data.getString(1);
            grundumsatz = Integer.parseInt(data.getString(12));
            kalorienverbrauch = Integer.parseInt(data.getString(11));
            alter= Integer.parseInt(data.getString(4));
            geschlecht= Integer.parseInt(data.getString(5));
            gewicht= Double.parseDouble(data.getString(2));
            groesse= Integer.parseInt(data.getString(3));
            schlaf = Double.parseDouble(data.getString(6));
            sitzend = Double.parseDouble(data.getString(7));
            stehend = Double.parseDouble(data.getString(8));
            kaumAktiv = Double.parseDouble(data.getString(9));
            sport = Double.parseDouble(data.getString(10));


        }
        felderFuellen();


        /*Cursor data = dbHelper.getItemId(name, grundumsatz, kalorienverbrauch);
        int itemID = -1;
        while(data.moveToNext()){
            itemID = data.getInt(0);
        }
        if(itemID<-1){
            Toast.makeText(this, "Keine ID mit diesen Paramentern", Toast.LENGTH_LONG).show();
        }
        nameFeld= (TextView) findViewById(R.id.db_erg_name);
        grundumsatzFeld= (TextView) findViewById(R.id.db_erg_grundumsatz);
        nameFeld.setText(name);
        grundumsatzFeld.setText(grundumsatz+"");*/
    }
    public void felderFuellen(){
        nameFeld = (TextView) findViewById(R.id.db_erg_name);
        grundumsatzFeld = (TextView) findViewById(R.id.db_erg_grundumsatz);
        nameFeld.setText(name);
        grundumsatzFeld.setText(grundumsatz+"");
    }
    public void datensatzLoeschen(View v){
        dbHelper.deleteRow(id);
        Intent intent = new Intent(DetailansichtActivity.this, ListeDatenbank.class);
        startActivity(intent);
    }
    public void werteEinfuegen(View v){
        DatenBerechnung datenBerechnung = new DatenBerechnung();
        try {
            datenBerechnung.setGroesse(groesse);
            datenBerechnung.setGewicht(gewicht);
            datenBerechnung.setAlter(alter);
            datenBerechnung.setName(name);
            if(geschlecht==0)
                datenBerechnung.setMaennlich(true);
            else
                datenBerechnung.setMaennlich(false);
            datenBerechnung.setPalWerte(schlaf, sitzend, kaumAktiv, stehend, sport);
            datenBerechnung.berechneGrundumsatz();
            datenBerechnung.berechneKalorienverbrauch();
        }catch(WertebereichException wbe){
            Toast.makeText(DetailansichtActivity.this, "Es ist ein Fehler aufgetreten", Toast.LENGTH_LONG).show();
        }
        MainActivity.db=datenBerechnung;
        AktivitaetActivity.db= datenBerechnung;
        ErgebnisActivity.db= datenBerechnung;
        Intent intent = new Intent(DetailansichtActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
