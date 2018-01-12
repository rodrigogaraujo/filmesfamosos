package g3infotech.net.filmesfamososs.constants;

import g3infotech.net.filmesfamososs.BuildConfig;

/**
 * Created by g3infotech on 06/12/17.
 */

public class Constants {

    public static final String BASE = "https://api.themoviedb.org/3";
    public static final String API_KEY = "api_key";
    public static final String API_KEY_VALUE = BuildConfig.MY_API_KEY;
    public static final String LANGUAGE = "language";
    public static final String LANGUAGE_VALUE = "pt-BR";
    public static final String TYPE_QUERY = "type_query";
    public static final int LOADER_ID = 22;
    public static final String DATABASE_NAME = "popular_film.db";
    public static final int DATABASE_VERSION = 1;

    public class QUERY{
        public static final String MOVIE = "movie";
        public static final String VIDEOS = "videos";
        public static final String REVIEWS = "reviews";
        public static final String TYPE_POPULAR = "popular";
        public static final String TYPE_TOP_RATED = "top_rated";
        public static final String TYPE_FAVORITES = "favorites";
    }

    public class IMAGES{
        public static final String BASE = "https://image.tmdb.org/t/p/w500";
    }

    public class FILM {
        public static final String ID = "id";
    }
}
