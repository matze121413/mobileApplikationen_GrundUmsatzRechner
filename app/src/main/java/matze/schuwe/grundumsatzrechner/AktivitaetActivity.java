package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AktivitaetActivity extends AppCompatActivity {
    public static DatenBerechnung db ;
     static  EditText schlaf, sitzend, kaumAktiv, stehend, sport;
     public AktivitaetActivity(){

    }
    class TextSucher implements TextWatcher{
          EditText schlaf, sitzend, kaumAktiv, stehend, sport;
          public TextSucher(){
              schlaf=(EditText) findViewById(R.id.schlaf);
              sitzend=(EditText) findViewById(R.id.sitzend);
              kaumAktiv=(EditText) findViewById(R.id.kaumAktiv);
              stehend=(EditText) findViewById(R.id.stehend);
              sport=(EditText) findViewById(R.id.sport);
          }


        public void afterTextChanged(Editable s) {}

        public void beforeTextChanged(CharSequence s, int start,
                                      int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            double summe = 0;
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
            TextView stunden=(TextView) findViewById(R.id.stundenInsgesamt);
            stunden.setText(""+summe);
        }
    }    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aktivitaet);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         schlaf=(EditText) findViewById(R.id.schlaf);
         sitzend=(EditText) findViewById(R.id.sitzend);
         kaumAktiv=(EditText) findViewById(R.id.kaumAktiv);
         stehend=(EditText) findViewById(R.id.stehend);
         sport=(EditText) findViewById(R.id.sport);
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

    public void felderEinsetzen(){
        schlaf.setText(db.getPalSchlaf()+"");
        sitzend.setText(db.getPalSitzend()+"");
        kaumAktiv.setText(db.getPalKaumAktiv()+"");
        stehend.setText(db.getPalStehend()+"");
        sport.setText(db.getPalSport()+"");
    }

    public boolean onOptionsItemSelected(MenuItem item){
         int id = item.getItemId();
         if(id== android.R.id.home)
             startActivity(new Intent(this, MainActivity.class));
        return super.onOptionsItemSelected(item);
    }
    public void berechnen(View v){
        double schlafendWert = Double.parseDouble(schlaf.getText().toString());
        double sitzendWert = Double.parseDouble(sitzend.getText().toString());
        double kaumAktivWert = Double.parseDouble(kaumAktiv.getText().toString());
        double stehendWert = Double.parseDouble(stehend.getText().toString());
        double sportWert = Double.parseDouble(sport.getText().toString());
        try {
            db.setPalWerte(schlafendWert, sitzendWert, kaumAktivWert, stehendWert, sportWert);
            db.berechneKalorienverbrauch();
            startActivity(new Intent(this, ErgebnisActivity.class));
           // Toast.makeText(this, ""+db.getKalorienVerbrauch(), Toast.LENGTH_LONG).show();
        }catch(WertebereichException wbe){
            Toast.makeText(this, "Alle Werte zusammen müssen 24h ergeben!", Toast.LENGTH_LONG).show();
        }
    }

}