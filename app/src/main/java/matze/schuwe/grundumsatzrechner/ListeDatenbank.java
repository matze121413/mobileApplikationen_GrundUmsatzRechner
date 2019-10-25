package matze.schuwe.grundumsatzrechner;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
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
}
