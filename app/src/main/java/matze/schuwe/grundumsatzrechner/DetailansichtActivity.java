package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DetailansichtActivity extends AppCompatActivity {
    DbHelper dbHelper;
    TextView nameFeld, grundumsatzFeld;
    private String  name;
    private int  grundumsatz, kalorienverbrauch, alter, geschlecht;
    private double gewicht, grundUmsatz, kalorienVerbrauch, schlaf, sitzend, stehend, kaumAktiv, sport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailansicht);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        int id = b.getInt("id");
        dbHelper = new DbHelper(this);
        Cursor data =dbHelper.getRow(id);
        while(data.moveToNext()) {
            name = data.getString(1);
            grundumsatz = Integer.parseInt(data.getString(12));
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
}
