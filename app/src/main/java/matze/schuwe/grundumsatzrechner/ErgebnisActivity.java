package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class ErgebnisActivity extends AppCompatActivity {
    public static DatenBerechnung db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ergebnis);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        db = AktivitaetActivity.db;
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
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if(id== android.R.id.home)
            startActivity(new Intent(this, AktivitaetActivity.class));
        return super.onOptionsItemSelected(item);
    }
}
