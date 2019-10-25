package matze.schuwe.grundumsatzrechner;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DbHelper extends SQLiteOpenHelper{

        private static final String LOG_TAG = DbHelper.class.getSimpleName();

        public static final String DB_NAME = "kalorienverauch_liste.db";
        public static final int DB_VERSION = 1;

        public static final String TABLE_KALORIENVERBRAUCH = "kalorienverbrauch_liste";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_GRUNDUMSATZ = "grundumsatz";
        public static final String COLUMN_KALORIENVERBRAUCH = "kalorienverbrauch";
    public static final String COLUMN_TEST = "test";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_KALORIENVERBRAUCH +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        //COLUMN_TEST + " INTEGER NOT NULL, " +
                        COLUMN_KALORIENVERBRAUCH + " INTEGER NOT NULL, " +
                        COLUMN_GRUNDUMSATZ + " INTEGER NOT NULL);";


        public DbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
            Log.d(LOG_TAG, "DbHelper hat die Datenbank: " + getDatabaseName() + " erzeugt.");
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                Log.d(LOG_TAG, "Die Tabelle wird mit SQL-Befehl: " + SQL_CREATE + " angelegt.");
                db.execSQL(SQL_CREATE);
            }
            catch (Exception ex) {
                Log.e(LOG_TAG, "Fehler beim Anlegen der Tabelle: " + ex.getMessage());
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_KALORIENVERBRAUCH);
            onCreate(db);
        }

        public boolean addData(int kalorienVerbrauch, int grundUmsatz){
            int test =1;
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues =  new ContentValues();
            contentValues.put(COLUMN_GRUNDUMSATZ, grundUmsatz);
           // contentValues.put(COLUMN_TEST, test);
            contentValues.put(COLUMN_KALORIENVERBRAUCH, kalorienVerbrauch);
            Log.d(TAG, "Daten hinzugefügt:"+ kalorienVerbrauch + "und "+ grundUmsatz +" zu" +TABLE_KALORIENVERBRAUCH );
            long result = db.insert(TABLE_KALORIENVERBRAUCH, null, contentValues);
            return result != -1;
        }
        public Cursor getData(){
            SQLiteDatabase db= getWritableDatabase();
            String query = "SELECT * FROM "+ TABLE_KALORIENVERBRAUCH;
            Cursor data = db.rawQuery(query, null );
            return data;
        }
    }

