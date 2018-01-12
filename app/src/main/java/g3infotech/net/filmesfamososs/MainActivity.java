package g3infotech.net.filmesfamososs;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import g3infotech.net.filmesfamososs.adapter.FilmAdapter;
import g3infotech.net.filmesfamososs.constants.Constants;
import g3infotech.net.filmesfamososs.data.MovieContract;
import g3infotech.net.filmesfamososs.entity.Movie;
import g3infotech.net.filmesfamososs.entity.Page;
import g3infotech.net.filmesfamososs.tasks.MovieTasks;
import g3infotech.net.filmesfamososs.util.NetworkUtil;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    RecyclerView mRvFilmList;
    FilmAdapter mFilmAdapter;
    ProgressBar mProgressBar;
    LoaderManager mLoaderManager;
    Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRvFilmList = findViewById(R.id.rv_film_list);
        mProgressBar = findViewById(R.id.pb_load);

        mBundle = new Bundle();
        mFilmAdapter = new FilmAdapter();

        if (isConected()) {
            mLoaderManager = getSupportLoaderManager();
            Loader<List<Movie>> listLoader = mLoaderManager.getLoader(Constants.LOADER_ID);
            if (savedInstanceState != null) {
                mBundle = savedInstanceState;
            }

            if (listLoader == null) {
                mBundle.putString(Constants.TYPE_QUERY, Constants.QUERY.TYPE_POPULAR);
                mLoaderManager.initLoader(Constants.LOADER_ID, mBundle, this);
            } else {
                mLoaderManager.restartLoader(Constants.LOADER_ID, mBundle, this);
            }
        } else {
            Toast.makeText(this, R.string.verify_connection, Toast.LENGTH_LONG).show();
        }

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRvFilmList.setLayoutManager(manager);
        mRvFilmList.setAdapter(mFilmAdapter);
        showLoading();
    }

    private void showLoading() {
        mRvFilmList.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = new MenuInflater(this);
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.top_rated:
                mBundle.putString(Constants.TYPE_QUERY, Constants.QUERY.TYPE_TOP_RATED);
                if (isConected()) {
                    mLoaderManager.restartLoader(Constants.LOADER_ID, mBundle, this);
                } else {
                    Toast.makeText(this, R.string.verify_connection, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.most_popular:
                mBundle.putString(Constants.TYPE_QUERY, Constants.QUERY.TYPE_POPULAR);
                if (isConected()) {
                    mLoaderManager.restartLoader(Constants.LOADER_ID, mBundle, this);
                } else {
                    Toast.makeText(this, R.string.verify_connection, Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.favorite:
                mBundle.putString(Constants.TYPE_QUERY, Constants.QUERY.TYPE_FAVORITES);
                if (isConected()) {
                    mLoaderManager.restartLoader(Constants.LOADER_ID, mBundle, this);
                } else {
                    Toast.makeText(this, R.string.verify_connection, Toast.LENGTH_LONG).show();
                }
                break;
        }
        return true;
    }

    public boolean isConected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(Constants.TYPE_QUERY, mBundle.getString(Constants.TYPE_QUERY));
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public Loader<List<Movie>> onCreateLoader(int id, final Bundle args) {
        return new MovieTasks(this, args, mProgressBar);
    }

    @Override
    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
        mFilmAdapter.setDataFilm(data);
        mRvFilmList.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
