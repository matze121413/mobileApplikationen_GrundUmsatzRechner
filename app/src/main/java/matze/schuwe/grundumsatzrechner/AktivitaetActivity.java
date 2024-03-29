package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AktivitaetActivity extends AppCompatActivity {
    public static DatenBerechnung db ;
      private EditText schlaf, sitzend, kaumAktiv, stehend, sport;
     public AktivitaetActivity(){

    }
    //Dient zur Untersuchung der Eingabefelder auf Änderung, um die Gesamtanzahl ausgeben zu können.
    class TextSucher implements TextWatcher{
          EditText schlaf, sitzend, kaumAktiv, stehend, sport;
          private TextSucher(){
              schlaf= findViewById(R.id.schlaf);
              sitzend= findViewById(R.id.sitzend);
              kaumAktiv= findViewById(R.id.kaumAktiv);
              stehend= findViewById(R.id.stehend);
              sport= findViewById(R.id.sport);
          }


        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }
        //Methode wird aufgerufen falls sich der Text in einem Eingabefeld ändert.
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            double summe;
            double sitzendWert =0;
            double schlafWert= 0;
            double kaumAktivWert = 0;
            double stehendWert = 0;
            double sportWert = 0;
            if(!sitzend.getText().toString().equals(""))
                    sitzendWert = Double.parseDouble(sitzend.getText().toString());
            if(!schlaf.getText().toString().equals(""))
                schlafWert = Double.parseDouble(schlaf.getText().toString());
            if(!kaumAktiv.getText().toString().equals(""))
                kaumAktivWert = Double.parseDouble(kaumAktiv.getText().toString());
            if(!stehend.getText().toString().equals(""))
                stehendWert = Double.parseDouble(stehend.getText().toString());
            if(!sport.getText().toString().equals(""))
                sportWert = Double.parseDouble(sport.getText().toString());
            summe= sitzendWert+ schlafWert + kaumAktivWert+ stehendWert+ sportWert;
            TextView stunden= findViewById(R.id.stundenInsgesamt);
            stunden.setText(""+summe);
        }
    }    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitaet);
        try {
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }catch(NullPointerException npe){
            Toast.makeText(this, "Es ist ein Fehler aufgetreten!", Toast.LENGTH_LONG).show();
        }
         schlaf= findViewById(R.id.schlaf);
         sitzend= findViewById(R.id.sitzend);
         kaumAktiv= findViewById(R.id.kaumAktiv);
         stehend= findViewById(R.id.stehend);
         sport= findViewById(R.id.sport);
        if(db==null){
            db=MainActivity.db;
        }else{
            felderEinsetzen();
        }
        db= MainActivity.db;
        TextWatcher tw = new TextSucher();
        schlaf.addTextChangedListener(tw);
        sitzend.addTextChangedListener(tw);
        kaumAktiv.addTextChangedListener(tw);
        stehend.addTextChangedListener(tw);
        sport.addTextChangedListener(tw);
    }

    // falls ein Datensatz eingefügt wird, werden hier die Felder dafür gefüllt
    public void felderEinsetzen(){
        schlaf.setText(db.getPalSchlaf()+"");
        sitzend.setText(db.getPalSitzend()+"");
        kaumAktiv.setText(db.getPalKaumAktiv()+"");
        stehend.setText(db.getPalStehend()+"");
        sport.setText(db.getPalSport()+"");
    }

    /* Menu-Icon wird in toolbar angezeigt */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }//Ende der onCreateOptionMenu-Methode

    //soll Activity wechseln, wenn ein Item in der toolbar angeklickt wurde
    public boolean onOptionsItemSelected(MenuItem item){
         int id = item.getItemId();
         if(id== android.R.id.home)
             startActivity(new Intent(this, MainActivity.class));
         if(id== R.id.help)
             startActivity(new Intent(this, HilfeActivity.class));
        if(id== R.id.daten)
            startActivity(new Intent(this, ListeDatenbank.class));
        if(id== R.id.berechnung)
            startActivity(new Intent(this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
    // bei Buttondruck werden Werte in Objekt von DatenBerechnung gespeichert und durch Einen Intent wird die ErgebnisActivity aufgerufen
        public void berechnen(View v){
        try {
            double schlafendWert = Double.parseDouble(schlaf.getText().toString());
            double sitzendWert = Double.parseDouble(sitzend.getText().toString());
            double kaumAktivWert = Double.parseDouble(kaumAktiv.getText().toString());
            double stehendWert = Double.parseDouble(stehend.getText().toString());
            double sportWert = Double.parseDouble(sport.getText().toString());
            db.setPalWerte(schlafendWert, sitzendWert, kaumAktivWert, stehendWert, sportWert);
            db.berechneKalorienverbrauch();
            startActivity(new Intent(this, ErgebnisActivity.class));
        }catch(NumberFormatException nfe){
            Toast.makeText(this, getString(R.string.vervollstaendigeFelder), Toast.LENGTH_LONG).show();
        }catch(WertebereichException wbe){
            Toast.makeText(this, getString(R.string.summeMeassage), Toast.LENGTH_LONG).show();
        }

    }

}
