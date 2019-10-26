package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class ListeDatenbank extends AppCompatActivity {
    private static final String TAG = "ListeDatenbank";
    DbHelper dbHelper;
    private ListView liste;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liste_datenbank);
        liste = (ListView) findViewById(R.id.listeDatenbankObjekte);
        dbHelper = new DbHelper(this);
        listeFuellen();
    }
    public void listeFuellen(){
        Cursor data =dbHelper.getData();
        ArrayList<String> listeDaten = new ArrayList<>();
        while(data.moveToNext()){
            listeDaten.add(data.getString(1));
        }
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listeDaten);
        liste.setAdapter(adapter);
    }

    /* Menu-Icon wird in toolbar angezeigt */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.datenpage, menu);
        return true;
    }//Ende der onCreateOptionMenu-Methode

    //soll Activity wechseln, wenn ein Item im MEnu angeklickt wurde
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.datenberechnung:
                Intent berechnungIntent = new Intent(ListeDatenbank.this,MainActivity.class);
                startActivity(berechnungIntent);
                return true;
            case R.id.datenhelp:
                Intent  helpIntent = new Intent(ListeDatenbank.this,HilfeActivity.class);
                startActivity(helpIntent);
            default:
                return super.onOptionsItemSelected(item);
        }//Ende switch
    }//Ende onOptionsItemSelected
}
