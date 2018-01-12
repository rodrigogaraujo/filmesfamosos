package g3infotech.net.filmesfamososs.tasks;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.AsyncTaskLoader;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import g3infotech.net.filmesfamososs.constants.Constants;
import g3infotech.net.filmesfamososs.data.MovieContract;
import g3infotech.net.filmesfamososs.entity.Movie;
import g3infotech.net.filmesfamososs.entity.Page;
import g3infotech.net.filmesfamososs.util.NetworkUtil;

/**
 * Created by g3infotech on 11/01/18.
 */

public class MovieTasks extends AsyncTaskLoader<List<Movie>> {

    Bundle mBundle;
    ProgressBar mProgressBar;

    public MovieTasks(Context context, Bundle bundle, ProgressBar pb) {
        super(context);
        mBundle = bundle;
        mProgressBar = pb;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if(mBundle == null)
            return;
        mProgressBar.setVisibility(View.VISIBLE);
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        try {
            List<Movie> movies = new ArrayList<>();
            Page<Movie> page;
            //verifica se é favorito caso nao seja, busca os dados locais
            if (!mBundle.getString(Constants.TYPE_QUERY).equals(Constants.QUERY.TYPE_FAVORITES)) {
                URL url = NetworkUtil.buildUrl(mBundle.getString(Constants.TYPE_QUERY));
                Gson gson = new GsonBuilder().create();
                Type type = new TypeToken<Page<Movie>>() {
                }.getType();
                page = gson.fromJson(NetworkUtil.getResponseFromHttpUrl(url), type);
                movies.addAll(page.getResults());
            } else {
                Cursor cursor = getContext().getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                        null, null, null, MovieContract.MovieEntry.COLUMN_ID_API);
                while (cursor.moveToNext()) {
                    int id = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_ID_API));
                    String posterPath = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POSTER_PATH));
                    String overview = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_OVERVIEW));
                    String releaseDate = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RELEASE_DATE));
                    String title = cursor.getString(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_TITLE));
                    double popularity = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_POPULARITY));
                    double voteAverage = cursor.getDouble(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE));
                    int runtime = cursor.getInt(cursor.getColumnIndex(MovieContract.MovieEntry.COLUMN_RUNTIME));

                    Movie movie = new Movie(id, posterPath, overview, releaseDate, title, popularity,
                            voteAverage, runtime, null);
                    movies.add(movie);
                }
            }
            return movies;
        } catch (IOException e) {
            return null;
        }
    }
}
