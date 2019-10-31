package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

   public static DatenBerechnung db ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);




        if(db==null){
            db=new DatenBerechnung();
        }else{
            felderEinsetzen();
        }

    }// Ende onCreate-Methode


    /* Menu-Icon wird in toolbar angezeigt */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }//Ende der onCreateOptionMenu-Methode

    //soll Activity wechseln, wenn ein Item im MEnu angeklickt wurde
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.help:
                Intent helpIntent = new Intent(MainActivity.this,HilfeActivity.class);
                startActivity(helpIntent);
                return true;
            case R.id.daten:
                Intent datenIntent = new Intent(MainActivity.this,ListeDatenbank.class);
                startActivity(datenIntent);
            default:
                return super.onOptionsItemSelected(item);
        }//Ende switch
    }//Ende onOptionsItemSelected


    public void felderEinsetzen(){
        EditText gewicht =(EditText) findViewById(R.id.gewicht);
        EditText groesse =(EditText) findViewById(R.id.groesse);
        EditText alter =(EditText) findViewById(R.id.alter);
        gewicht.setText(db.getGewicht()+"");
        groesse.setText(db.getGroesse()+"");
        alter.setText(db.getAlter()+"");

    }

    public void berechnen(View v){
        EditText textGewicht= (EditText) findViewById(R.id.gewicht);
        EditText textGroesse= (EditText) findViewById(R.id.groesse);
        EditText textAlter =(EditText) findViewById(R.id.alter);
        RadioButton geschlecht = (RadioButton) findViewById(R.id.radio_maennlich);


        try{
            db.setMaennlich(geschlecht.isChecked());
             db.setGroesse(Integer.parseInt(textGroesse.getText().toString()));
            db.setAlter(Integer.parseInt(textAlter.getText().toString()));
            db.setGewicht(Double.parseDouble(textGewicht.getText().toString()));
            db.berechneGrundumsatz();
           // Intent intent= new Intent(this, AktivitaetActivity.class);
            startActivity(new Intent(this, AktivitaetActivity.class));
        }catch (NumberFormatException nfe){
            Toast.makeText(this,  getString(R.string.nurZahlen), Toast.LENGTH_LONG).show();
        }catch(WertebereichException wbe){
            Toast.makeText(this, getString(R.string.Wertebereich), Toast.LENGTH_LONG).show();
        }


    }
}
