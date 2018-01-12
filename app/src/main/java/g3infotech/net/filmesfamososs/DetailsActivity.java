package g3infotech.net.filmesfamososs;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.ButterKnife;
import g3infotech.net.filmesfamososs.adapter.ReviewAdapter;
import g3infotech.net.filmesfamososs.adapter.VideoAdapter;
import g3infotech.net.filmesfamososs.constants.Constants;
import g3infotech.net.filmesfamososs.data.MovieContract;
import g3infotech.net.filmesfamososs.entity.Movie;
import g3infotech.net.filmesfamososs.entity.Page;
import g3infotech.net.filmesfamososs.entity.Video;
import g3infotech.net.filmesfamososs.tasks.ReviewTasks;
import g3infotech.net.filmesfamososs.tasks.VideoTasks;
import g3infotech.net.filmesfamososs.util.NetworkUtil;
import g3infotech.net.filmesfamososs.util.Utils;

public class DetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_banner_film) ImageView mIvBannerFilm;
    @BindView(R.id.tv_title_film) TextView mTvTitleFilm;
    @BindView(R.id.tv_year_film) TextView mTvYear;
    @BindView(R.id.tv_time_film) TextView mTvMinutes;
    @BindView(R.id.tv_votes) TextView mTvVotes;
    @BindView(R.id.tv_overriew_film) TextView mTvOverriew;
    @BindView(R.id.btn_favorite) Button btnFavorite;
    private ProgressBar mProgressBar;
    private RecyclerView mRecyclerViewVideos;
    private RecyclerView mRecyclerViewReview;
    private Context mContext;
    private Movie mMovie;
    private ReviewTasks mReviewTask;
    private VideoTasks mVideoTask;
    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        ButterKnife.bind(this);
        mContext = this;

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        mProgressBar = findViewById(R.id.pb_load);
        mRecyclerViewReview = findViewById(R.id.recycler_reviews);
        mRecyclerViewVideos = findViewById(R.id.recycler_videos);

        getMovie();
        verifyConnection();

        try {
            LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mVideoAdapter = new VideoAdapter(mVideoTask.get());
            mRecyclerViewVideos.setLayoutManager(manager);
            mRecyclerViewVideos.setAdapter(mVideoAdapter);

            LinearLayoutManager manager1 = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mReviewAdapter = new ReviewAdapter(mReviewTask.get());
            mRecyclerViewReview.setLayoutManager(manager1);
            mRecyclerViewReview.setAdapter(mReviewAdapter);

            if (verifyFavorite()) {
                btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        setData(mMovie);
    }

    private void verifyConnection() {
        if (Utils.isConected(this)) {
            //new ResultsTasks().execute();
            mVideoTask = new VideoTasks(mMovie, mProgressBar);
            mVideoTask.execute();
            mReviewTask = new ReviewTasks(mMovie, mProgressBar);
            mReviewTask.execute();
        } else {
            Toast.makeText(this, R.string.verify_connection, Toast.LENGTH_LONG).show();
        }
    }

    private void getMovie() {
        Intent i = getIntent();
        if (i != null) {
            if (i.hasExtra(Constants.FILM.ID)) {
                mMovie = (Movie) i.getParcelableExtra(Constants.FILM.ID);
            }
        }
    }

    private boolean verifyFavorite() {
        //if(mMovie.isFavorite())
        String id = String.valueOf(mMovie.getId());
        Cursor cursor = getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI,
                null, MovieContract.MovieEntry.COLUMN_ID_API + "=?", new String[]{id}, null);
        return cursor.moveToFirst();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    public void addFavorite(View view) {
        if (!verifyFavorite()) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(MovieContract.MovieEntry.COLUMN_ID_API, mMovie.getId());
            contentValues.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovie.getTitle());
            contentValues.put(MovieContract.MovieEntry.COLUMN_POSTER_PATH, mMovie.getPosterPath());
            contentValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mMovie.getOverview());
            contentValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, mMovie.getReleaseDate());
            contentValues.put(MovieContract.MovieEntry.COLUMN_POPULARITY, mMovie.getPopularity());
            contentValues.put(MovieContract.MovieEntry.COLUMN_RUNTIME, mMovie.getRuntime());
            contentValues.put(MovieContract.MovieEntry.COLUMN_FAVORITE, 1);
            contentValues.put(MovieContract.MovieEntry.COLUMN_VOTE_AVERAGE, mMovie.getVoteAverage());

            btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

            Uri uri = getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, contentValues);
            if (uri != null)
                Toast.makeText(getBaseContext(), R.string.add_favorites, Toast.LENGTH_LONG).show();
            return;
        }
        String id = String.valueOf(mMovie.getId());
        int delete = getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI,
                MovieContract.MovieEntry.COLUMN_ID_API + "=?",
                new String[]{id}
        );
        if (delete != 0) {
            btnFavorite.setBackgroundColor(getResources().getColor(R.color.colorTitleFilme));
            Toast.makeText(this, R.string.remove_favorites, Toast.LENGTH_LONG).show();
        }
    }

    private void setData(Movie film) {
        mTvTitleFilm.setText(film.getTitle());
        Picasso.with(mContext).load(Constants.IMAGES.BASE + film.getPosterPath()).into(mIvBannerFilm);
        mTvOverriew.setText(film.getOverview());
        String date = film.getReleaseDate();
        if (date != null) {
            mTvYear.setText(date);
        }
        mTvVotes.setText(String.valueOf(film.getVoteAverage() + getString(R.string.ten)));
        mTvMinutes.setText(String.valueOf(film.getRuntime() + getString(R.string.min)));
    }
}
