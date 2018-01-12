package g3infotech.net.filmesfamososs.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by g3infotech on 1/7/18.
 */

public class MovieContract {

    private MovieContract(){}

    public static final String AUTHORITY = "g3infotech.net.filmesfamososs";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    public static final String PATH_MOVIE = "movie";

    public static final class MovieEntry implements BaseColumns{

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();
        public static final int MOVIE_PATH = 100;
        public static final int MOVIE_PATH_WITH_ID = 101;

        public static final String TABLE_NAME = "movie";
        public static final String COLUMN_ID_API = "id_api";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_POPULARITY = "popularity";
        public static final String COLUMN_VOTE_AVERAGE = "vote_average";
        public static final String COLUMN_RUNTIME = "runtime";
        public static final String COLUMN_FAVORITE = "favorite";

    }

}
