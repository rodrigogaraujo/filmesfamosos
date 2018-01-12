package g3infotech.net.filmesfamososs.tasks;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

import g3infotech.net.filmesfamososs.constants.Constants;
import g3infotech.net.filmesfamososs.entity.Movie;
import g3infotech.net.filmesfamososs.entity.Page;
import g3infotech.net.filmesfamososs.entity.Review;
import g3infotech.net.filmesfamososs.util.NetworkUtil;

/**
 * Created by g3infotech on 10/01/18.
 */

public class ReviewTasks extends AsyncTask<List<Review>, Void, List<Review>> {

    private Movie mMovie;
    private ProgressBar mPgBar;

    public ReviewTasks(Movie movie, ProgressBar progressBar){
        mMovie = movie;
        mPgBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mPgBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Review> doInBackground(List<Review>[] lists) {
        try {
            Gson gson = new Gson();
            URL urlVideo = NetworkUtil.buildUrl(mMovie.getId(), Constants.QUERY.REVIEWS);
            Type type = new TypeToken<Page<Review>>() {}.getType();
            Page<Review> pageVideo = gson.fromJson(NetworkUtil.getResponseFromHttpUrl(urlVideo), type);
            return pageVideo.getResults();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Review> reviews) {
        super.onPostExecute(reviews);
        mPgBar.setVisibility(View.INVISIBLE);
    }

}
