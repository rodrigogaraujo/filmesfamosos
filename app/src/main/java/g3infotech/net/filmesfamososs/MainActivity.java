package g3infotech.net.filmesfamososs;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import g3infotech.net.filmesfamososs.adapter.FilmAdapter;
import g3infotech.net.filmesfamososs.constants.Constants;
import g3infotech.net.filmesfamososs.entity.Movie;
import g3infotech.net.filmesfamososs.tasks.MovieTasks;
import g3infotech.net.filmesfamososs.util.Utils;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Movie>> {

    RecyclerView mRvFilmList;
    FilmAdapter mFilmAdapter;
    ProgressBar mProgressBar;
    LoaderManager mLoaderManager;
    Bundle mBundle;
    Loader<List<Movie>> listLoader;
    @BindView(R.id.tv_no_connection) TextView mTvConnection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRvFilmList = findViewById(R.id.rv_film_list);
        mProgressBar = findViewById(R.id.pb_load);

        mFilmAdapter = new FilmAdapter();
        mBundle = new Bundle();

        mLoaderManager = getSupportLoaderManager();
        listLoader = mLoaderManager.getLoader(Constants.LOADER_ID);

        if (Utils.isConected(this)) {
            if (savedInstanceState != null) {
                mBundle = savedInstanceState;
            }else{
                mBundle.putString(Constants.TYPE_QUERY, Constants.QUERY.TYPE_POPULAR);
            }
            verifyInitLoader(mLoaderManager);
        }else{
            mTvConnection.setVisibility(View.VISIBLE);
        }

        GridLayoutManager manager = new GridLayoutManager(this, 2);
        mRvFilmList.setLayoutManager(manager);
        mRvFilmList.setAdapter(mFilmAdapter);
        mRvFilmList.setHasFixedSize(true);

        showLoading();
    }

    private void verifyInitLoader(LoaderManager manager) {
        if (manager == null) {
            mLoaderManager.initLoader(Constants.LOADER_ID, mBundle, this);
        } else {
            mLoaderManager.restartLoader(Constants.LOADER_ID, mBundle, this);
        }
    }

    private void showLoading() {
        mRvFilmList.setVisibility(View.INVISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void showData() {
        mRvFilmList.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.INVISIBLE);
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
                if (Utils.isConected(this)) {
                    verifyInitLoader(mLoaderManager);
                }else{
                    showLoading();
                    mTvConnection.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.most_popular:
                mBundle.putString(Constants.TYPE_QUERY, Constants.QUERY.TYPE_POPULAR);
                if (Utils.isConected(this)) {
                    verifyInitLoader(mLoaderManager);
                }else{
                    showLoading();
                    mTvConnection.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.favorite:
                mBundle.putString(Constants.TYPE_QUERY, Constants.QUERY.TYPE_FAVORITES);
                if (Utils.isConected(this)) {
                    verifyInitLoader(mLoaderManager);
                }else{
                    showLoading();
                    mTvConnection.setVisibility(View.VISIBLE);
                }
                break;
        }
        return true;
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
        showData();
    }

    @Override
    public void onLoaderReset(Loader<List<Movie>> loader) {

    }
}
