package matze.schuwe.grundumsatzrechner;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DataSource {
    private static final String LOG_TAG = DataSource.class.getSimpleName();

    private SQLiteDatabase database;
    private DbHelper dbHelper;
    public DataSource(Context context) {
        Log.d(LOG_TAG, "DataSource erzeugt DbHelper.");
        dbHelper = new DbHelper(context);
    }
}
