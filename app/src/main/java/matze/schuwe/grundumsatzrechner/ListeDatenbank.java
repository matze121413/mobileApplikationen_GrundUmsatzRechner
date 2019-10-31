package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.listeners.TableDataClickListener;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class ListeDatenbank extends AppCompatActivity {
    private static final String TAG = "ListeDatenbank";
    DbHelper dbHelper;
    private ListView liste;
    TableView<String[]> tabelle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_liste_datenbank);
        tabelle = (TableView<String[]>) findViewById(R.id.tabelleDatensaetze);
        dbHelper = new DbHelper(this);

        listeFuellen();
        tabelle.addDataClickListener(new TableDataClickListener<String[]>() {
            @Override
            public void onDataClicked(int rowIndex, String[] clickedData) {
                Intent intent = new Intent(ListeDatenbank.this, DetailansichtActivity.class);
                Cursor data = dbHelper.getItemId(clickedData[0], Integer.parseInt(clickedData[1]), Integer.parseInt(clickedData[2]));
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID<-1){
                    Toast.makeText(ListeDatenbank.this, "Keine ID mit diesen Paramentern", Toast.LENGTH_LONG).show();
                }else{
                    intent.putExtra("id", itemID);
                    startActivity(intent);
                }

            }
        });
    }
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
    public void listeFuellen(){
        String[] spalten ={"Name", "Grundumsatz", "Kalorienverbrauch"};
        tabelle.setHeaderAdapter(new SimpleTableHeaderAdapter(this, spalten));
        tabelle.setColumnCount(3);
        Cursor data =dbHelper.getData();
        ArrayList<String[]> listeDaten = new ArrayList<>();
        while(data.moveToNext()){
            String [] anzeigen = new String[3];
            anzeigen[0]= data.getString(1);
            anzeigen[1] = data.getString(12);
            anzeigen[2] = data.getString(11);
            listeDaten.add(anzeigen);
        }
        tabelle.setDataAdapter(new SimpleTableDataAdapter( ListeDatenbank.this, listeDaten));
        //ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listeDaten);
        //liste.setAdapter(adapter);
    }
}
