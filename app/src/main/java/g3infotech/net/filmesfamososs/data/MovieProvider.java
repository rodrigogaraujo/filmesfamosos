package g3infotech.net.filmesfamososs.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import g3infotech.net.filmesfamososs.R;

/**
 * Created by g3infotech on 08/01/18.
 */

public class MovieProvider extends ContentProvider {

    private MovieDbHelper mMovieDbHelper;
    private static UriMatcher sUriMatcher = buildUriMatcher();

    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE , MovieContract.MovieEntry.MOVIE_PATH);
        uriMatcher.addURI(MovieContract.AUTHORITY, MovieContract.PATH_MOVIE + "/#", MovieContract.MovieEntry.MOVIE_PATH_WITH_ID);
        return uriMatcher;
    }

    @Override
    public boolean onCreate() {
        mMovieDbHelper = new MovieDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        final SQLiteDatabase sqLiteDatabase = mMovieDbHelper.getReadableDatabase();
        Cursor retCursor;
        int matcher = sUriMatcher.match(uri);
        switch (matcher){
            case MovieContract.MovieEntry.MOVIE_PATH:
                retCursor = sqLiteDatabase.query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case MovieContract.MovieEntry.MOVIE_PATH_WITH_ID:
                String id = uri.getPathSegments().get(1);
                String mSqlection = MovieContract.MovieEntry.COLUMN_ID_API + "=?";
                String[] mSelectionArgs = new String[]{id};
                retCursor = sqLiteDatabase.query(
                        MovieContract.MovieEntry.TABLE_NAME,
                        projection,
                        mSqlection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.error_uri) + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), uri);

        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase sqLiteDatabase = mMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        Uri returnUri;
        switch (match){
            case MovieContract.MovieEntry.MOVIE_PATH:
                long id = sqLiteDatabase.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if(id > 0){
                    returnUri = ContentUris.withAppendedId(MovieContract.MovieEntry.CONTENT_URI, id);
                }else{
                    throw new SQLException(getContext().getString(R.string.error_uri) + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.error_uri) + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase sqLiteDatabase = mMovieDbHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);

        int delete;
        switch (match){
            case MovieContract.MovieEntry.MOVIE_PATH:
                delete = sqLiteDatabase.delete(
                        MovieContract.MovieEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException(getContext().getString(R.string.error_uri) + uri);
        }

        if(delete != 0)
            getContext().getContentResolver().notifyChange(uri, null);

        return delete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
