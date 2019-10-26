package matze.schuwe.grundumsatzrechner;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

public class HilfeActivity  extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
    }

    /* Menu-Icon wird in toolbar angezeigt */
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.helppage_menu, menu);
        return true;
    }//Ende der onCreateOptionMenu-Methode

    //soll Activity wechseln, wenn ein Item im MEnu angeklickt wurde
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.berechnung:
                Intent helpIntent = new Intent(HilfeActivity.this,MainActivity.class);
                startActivity(helpIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }//Ende switch
    }//Ende onOptionsItemSelected
}
