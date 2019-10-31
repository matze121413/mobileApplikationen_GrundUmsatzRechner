package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class DetailansichtActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView nameFeld, grundumsatzFeld,kalorienverbrauchFeld, groesseFeld, geschlechtFeld, gewichtFeld, alterFeld, schlafFeld, sitzenFeld, kaumAktivFeld,stehendFeld, sportFeld;
    private String  name;
    private int  grundumsatz, kalorienverbrauch, alter, geschlecht, id, groesse;
    private double gewicht,  schlaf, sitzend, stehend, kaumAktiv, sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailansicht);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
    }

    /* Menu-Icon wird in toolbar angezeigt */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }//Ende der onCreateOptionMenu-Methode
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id== android.R.id.home)
            startActivity(new Intent(this, ListeDatenbank.class));
        if(id== R.id.help)
            startActivity(new Intent(this, HilfeActivity.class));
        if(id== R.id.daten)
            startActivity(new Intent(this, ListeDatenbank.class));
        return super.onOptionsItemSelected(item);
    }
    public void felderFuellen(){
        nameFeld = (TextView) findViewById(R.id.db_erg_name);
        nameFeld.setText(name);
        grundumsatzFeld = (TextView) findViewById(R.id.db_erg_grundumsatz);
        grundumsatzFeld.setText(grundumsatz+"");
        groesseFeld = (TextView) findViewById(R.id.db_erg_groesse);
        groesseFeld.setText(groesse+"");
        geschlechtFeld = (TextView) findViewById(R.id.db_erg_geschlecht);
        if(geschlecht==0){
            geschlechtFeld.setText("männlich");
        }else{
            geschlechtFeld.setText("weiblich");
        }
        gewichtFeld = (TextView) findViewById(R.id.db_erg_gewicht);
        gewichtFeld.setText(gewicht+"");
        alterFeld = (TextView) findViewById(R.id.db_erg_alter);
        alterFeld.setText(alter+"");
        schlafFeld = (TextView) findViewById(R.id.db_erg_schlaf);
        schlafFeld.setText(schlaf+"");
        sitzenFeld = (TextView) findViewById(R.id.db_erg_sitzend);
        sitzenFeld.setText(sitzend+"");
        kaumAktivFeld = (TextView) findViewById(R.id.db_erg_kaumAktiv);
        kaumAktivFeld.setText(kaumAktiv+"");
        stehendFeld = (TextView) findViewById(R.id.db_erg_stehend);
        stehendFeld.setText(stehend+"");
        sportFeld = (TextView) findViewById(R.id.db_erg_sport);
        sportFeld.setText(sport+"");
        kalorienverbrauchFeld = (TextView) findViewById(R.id.db_erg_kalorienverbrauch);
        kalorienverbrauchFeld.setText(kalorienverbrauch+"");

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
