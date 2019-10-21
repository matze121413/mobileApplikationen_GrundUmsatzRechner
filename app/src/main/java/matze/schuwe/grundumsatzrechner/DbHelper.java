package matze.schuwe.grundumsatzrechner;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

    public class DbHelper extends SQLiteOpenHelper{

        private static final String LOG_TAG = DbHelper.class.getSimpleName();

        public static final String DB_NAME = "kalorienverauch_liste.db";
        public static final int DB_VERSION = 1;

        public static final String TABLE_KALORIENVERBRAUCH = "kalorienverbrauch_liste";

        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_GRUNGUMSATZ = "product";
        public static final String COLUMN_KALORIENVERBRAUCH = "quantity";

        public static final String SQL_CREATE =
                "CREATE TABLE " + TABLE_KALORIENVERBRAUCH +
                        "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_KALORIENVERBRAUCH + " INTEGER NOT NULL, " +
                        COLUMN_GRUNGUMSATZ + " INTEGER NOT NULL);";


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

        }
    }

