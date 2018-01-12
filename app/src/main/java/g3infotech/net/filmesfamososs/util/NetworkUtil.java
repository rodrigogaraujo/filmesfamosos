package g3infotech.net.filmesfamososs.util;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import g3infotech.net.filmesfamososs.constants.Constants;
/**
 * Created by g3infotech on 06/12/17.
 */

public class NetworkUtil {

    public static URL buildUrl(String query){
        Uri buildUri = Uri.parse(Constants.BASE).buildUpon()
                .appendPath(Constants.QUERY.MOVIE)
                .appendPath(query)
                .appendQueryParameter(Constants.API_KEY, Constants.API_KEY_VALUE)
                .appendQueryParameter(Constants.LANGUAGE, Constants.LANGUAGE_VALUE)
                .build();

        URL url = null;
        try{
            url = new URL(buildUri.toString());
            return url;
        }catch (Exception e){
            return null;
        }
    }

    public static URL buildUrl(int id, String type){
        Uri buildUri = Uri.parse(Constants.BASE).buildUpon()
                .appendPath(Constants.QUERY.MOVIE)
                .appendPath(String.valueOf(id))
                .appendPath(type)
                .appendQueryParameter(Constants.API_KEY, Constants.API_KEY_VALUE)
                .build();

        URL url = null;
        try{
            url = new URL(buildUri.toString());
            return url;
        }catch (Exception e){
            return null;
        }
    }

    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}
