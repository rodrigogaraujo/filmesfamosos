package g3infotech.net.filmesfamososs.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import g3infotech.net.filmesfamososs.constants.Constants;

/**
 * Created by g3infotech on 1/7/18.
 */

public class MovieDbHelper extends SQLiteOpenHelper {

    public MovieDbHelper(Context context){
        super(context, Constants.DATABASE_NAME, null, Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_TABLE = "CREATE TABLE " + MovieContract.MovieEntry.TABLE_NAME + "(  " +
                MovieContract.MovieEntry._ID +" INTEGER PRIMARY KEY AUTOINCREMENT ," +
                MovieContract.MovieEntry.COLUMN_ID_API +" INTEGER NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_OVERVIEW +" TEXT NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_POPULARITY +" REAL NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_POSTER_PATH +" TEXT NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_RELEASE_DATE +" TEXT NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_TITLE +" TEXT NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE +" REAL NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_FAVORITE +" INTEGER NOT NULL ," +
                MovieContract.MovieEntry.COLUMN_RUNTIME +" INTEGER NOT NULL  )";
        db.execSQL(SQL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ MovieContract.MovieEntry.TABLE_NAME);
        onCreate(db);
    }
}
