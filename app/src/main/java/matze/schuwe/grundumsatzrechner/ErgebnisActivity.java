package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ErgebnisActivity extends AppCompatActivity {
    public static DatenBerechnung db ;
    DbHelper dbHelper;
    TextView eingabeFeld;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException npe){
            Toast.makeText(this, "Es ist ein Fehler aufgetreten!", Toast.LENGTH_LONG).show();
        }
        db = AktivitaetActivity.db;
        dbHelper= new DbHelper(this);
        TextView grundUmsatzKcal =  findViewById(R.id.grundUmsatz_kcal);
        TextView grundUmsatzKj =  findViewById(R.id.grundUmsatz_kj);
        TextView kalorienVerbrauchKcal =  findViewById(R.id.kalorienVerbrauch_kcal);
        TextView kalorienVerbrauchKj =  findViewById(R.id.kalorienVerbrauch_kj);
        grundUmsatzKcal.setText(((int) db.getGrundUmsatz()) + " kcal");
        grundUmsatzKj.setText(((int) (4.186 * db.getGrundUmsatz())) + " kj");
        kalorienVerbrauchKcal.setText(((int) db.getKalorienVerbrauch()) + " kcal");
        kalorienVerbrauchKj.setText(((int) (4.186 * db.getKalorienVerbrauch())) + " kj");

        eingabeFeld =  findViewById(R.id.editTextEingabeName);

    }
    //durch Buttondruck auf Neu Starten werden die vorhandenen Objekte von DatenBerechnung gelöscht und somit die Felder leer gemacht.
    public void neuStarten(View v){
        MainActivity.db=null;
        AktivitaetActivity.db=null;
        Intent intent = new Intent(ErgebnisActivity.this, MainActivity.class);
        startActivity(intent);
    }
    //int alter, int groesse, double gewicht, double schlaf, double sitzend, double stehend, double kaumAktiv, double sport, int geschlecht
    public void speichern(View v) {
        int ges = 0;
        String name = eingabeFeld.getText().toString();
        if (name.equals("")) {
            Toast.makeText(this, getString(R.string.nameEingeben), Toast.LENGTH_LONG).show();
        } else {
            if (!db.getMaennlich())
                ges = 1;
            boolean hinzugefuegt = dbHelper.addData(name, (int) db.getKalorienVerbrauch(), (int) db.getGrundUmsatz(), db.getAlter(), db.getGroesse(), db.getGewicht(), db.getPalSchlaf(), db.getPalSitzend(), db.getPalStehend(), db.getPalKaumAktiv(), db.getPalSport(), ges);
            if (hinzugefuegt) {
                Toast.makeText(this, getString(R.string.datenHinzugefuegt), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(ErgebnisActivity.this, ListeDatenbank.class);
                startActivity(intent);
            } else
                Toast.makeText(this, getString(R.string.datenNichtHinzugefuegt), Toast.LENGTH_LONG).show();
        }
        }

        /* Menu-Icon wird in toolbar angezeigt */
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.toolbar_menu, menu);
            return true;
        }//Ende der onCreateOptionMenu-Methode

        // zur Menü- und Zurücktasten Steuerung
        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            if (id == android.R.id.home)
                startActivity(new Intent(this, AktivitaetActivity.class));
            if (id == R.id.help)
                startActivity(new Intent(this, HilfeActivity.class));
            if (id == R.id.daten) {
                Intent dateIntent = new Intent(ErgebnisActivity.this, ListeDatenbank.class);
                startActivity(dateIntent);
            }
            if (id == R.id.berechnung) {
                Intent dateIntent = new Intent(ErgebnisActivity.this, MainActivity.class);
                startActivity(dateIntent);
            }
            return super.onOptionsItemSelected(item);
        }

}
