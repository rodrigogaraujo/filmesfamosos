package g3infotech.net.filmesfamososs.tasks;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import g3infotech.net.filmesfamososs.constants.Constants;
import g3infotech.net.filmesfamososs.entity.Movie;
import g3infotech.net.filmesfamososs.entity.Page;
import g3infotech.net.filmesfamososs.entity.Video;
import g3infotech.net.filmesfamososs.util.NetworkUtil;

/**
 * Created by g3infotech on 10/01/18.
 */

public class VideoTasks extends AsyncTask<List<Video>, Void, List<Video>> {

    Movie mMovie;
    private ProgressBar mPgBar;

    public VideoTasks(Movie movie, ProgressBar progressBar){
        mMovie = movie;
        mPgBar = progressBar;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mPgBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected List<Video> doInBackground(List<Video>[] lists) {
        try {
            Gson gson = new Gson();
            URL urlVideo = NetworkUtil.buildUrl(mMovie.getId(), Constants.QUERY.VIDEOS);
            Type type = new TypeToken<Page<Video>>() {}.getType();
            Page<Video> pageVideo = gson.fromJson(NetworkUtil.getResponseFromHttpUrl(urlVideo), type);
            return pageVideo.getResults();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<Video> videos) {
        super.onPostExecute(videos);
        mPgBar.setVisibility(View.INVISIBLE);
    }
}
