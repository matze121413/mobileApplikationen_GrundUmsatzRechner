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
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_GEWICHT = "gewicht";
        public static final String COLUMN_GROESSE = "groesse";
        public static final String COLUMN_ALTER = "alterPerson";
        public static final String COLUMN_SCHLAF = "schlaf";
        public static final String COLUMN_SITZEND = "sitzend";
        public static final String COLUMN_STEHEND = "stehend";
        public static final String COLUMN_KAUMAKTIV = "kaumAktiv";
        public static final String COLUMN_SPORT = "sport";
     public static final String COLUMN_GESCHLECHT = "geschlecht";

    public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_KALORIENVERBRAUCH +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                         COLUMN_GEWICHT +" REAL  NOT NULL, "+
                        COLUMN_GROESSE +" INTEGER  NOT NULL, "+
                        COLUMN_ALTER +" INTEGER  NOT NULL, "+
                        COLUMN_GESCHLECHT +" INTEGER NOT NULL, "+
                        COLUMN_SCHLAF +" REAL  NOT NULL, "+
                        COLUMN_SITZEND +" REAL  NOT NULL, "+
                        COLUMN_STEHEND +" REAL  NOT NULL, "+
                        COLUMN_KAUMAKTIV +" REAL  NOT NULL, "+
                        COLUMN_SPORT + " REAL NOT NULL, "+
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
        public boolean addData(String name, int kalorienVerbrauch, int grundUmsatz, int alter, int groesse, double gewicht, double schlaf, double sitzend, double stehend, double kaumAktiv, double sport, int geschlecht){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues =  new ContentValues();
            contentValues.put(COLUMN_GRUNDUMSATZ, grundUmsatz);
            contentValues.put(COLUMN_GEWICHT, gewicht);
             contentValues.put(COLUMN_GROESSE, groesse);
            contentValues.put(COLUMN_ALTER, alter);
             contentValues.put(COLUMN_SCHLAF, schlaf);
            contentValues.put(COLUMN_SITZEND, sitzend);
             contentValues.put(COLUMN_STEHEND, stehend);
             contentValues.put(COLUMN_KAUMAKTIV, kaumAktiv);
            contentValues.put(COLUMN_SPORT, sport);
            contentValues.put(COLUMN_GESCHLECHT ,geschlecht);
            contentValues.put(COLUMN_NAME ,name);
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
        public Cursor getItemId(String name, int grundumsatz, int kalorienverbrauch){
            SQLiteDatabase db= getWritableDatabase();
            String query ="SELECT "+ COLUMN_ID + " FROM "+ TABLE_KALORIENVERBRAUCH+ " WHERE "+ COLUMN_GRUNDUMSATZ+ " = "+ grundumsatz + " AND "+ COLUMN_NAME+ " = '"+ name + "' AND "+ COLUMN_KALORIENVERBRAUCH+ " = "+ kalorienverbrauch;
            Cursor data = db.rawQuery(query, null );
            return data;
        }
        public Cursor getRow(int id){
            SQLiteDatabase db= getWritableDatabase();
            String query ="SELECT * FROM "+ TABLE_KALORIENVERBRAUCH+ " WHERE "+ COLUMN_ID + " = "+ id;
            Cursor data = db.rawQuery(query, null );
            return data;
        }
    }

