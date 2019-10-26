package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ErgebnisActivity extends AppCompatActivity {
    public static DatenBerechnung db ;
    DbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = AktivitaetActivity.db;
        dbHelper= new DbHelper(this);
        TextView grundUmsatzKcal = (TextView) findViewById(R.id.grundUmsatz_kcal);
        TextView grundUmsatzKj = (TextView) findViewById(R.id.grundUmsatz_kj);
        TextView kalorienVerbrauchKcal = (TextView) findViewById(R.id.kalorienVerbrauch_kcal);
        TextView kalorienVerbrauchKj = (TextView) findViewById(R.id.kalorienVerbrauch_kj);
        grundUmsatzKcal.setText(((int) db.getGrundUmsatz()) + " kcal");
        grundUmsatzKj.setText(((int) (4.186 * db.getGrundUmsatz())) + " kj");
        kalorienVerbrauchKcal.setText(((int) db.getKalorienVerbrauch()) + " kcal");
        kalorienVerbrauchKj.setText(((int) (4.186 * db.getKalorienVerbrauch())) + " kj");

    }
    public void neuStarten(View v){
        MainActivity.db=null;
        AktivitaetActivity.db=null;
        Intent intent = new Intent(ErgebnisActivity.this, MainActivity.class);
        startActivity(intent);
    }
    //int alter, int groesse, double gewicht, double schlaf, double sitzend, double stehend, double kaumAktiv, double sport, int geschlecht
    public void speichern(View v){
        int ges= 0;
        if(!db.getMaennlich())
            ges=1;
        boolean hinzugefuegt = dbHelper.addData("Hans", (int)db.getKalorienVerbrauch(), (int) db.getGrundUmsatz(), db.getAlter(), db.getGroesse(), db.getGewicht(), db.getPalSchlaf(), db.getPalSitzend(), db.getPalStehend(), db.getPalKaumAktiv(), db.getPalSport(), ges);
        if(hinzugefuegt){
            Toast.makeText(this, "Daten erfolgreich hinzugefügt", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(ErgebnisActivity.this, ListeDatenbank.class);
            startActivity(intent);
        }else
            Toast.makeText(this, "Daten konnten nicht hinzugefügt werden", Toast.LENGTH_LONG).show();

    }
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id== android.R.id.home)
            startActivity(new Intent(this, AktivitaetActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
