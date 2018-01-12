package g3infotech.net.filmesfamososs.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by g3infotech on 11/01/18.
 */

public class Utils {

    public static boolean isConected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        return info != null && info.isConnectedOrConnecting();
    }
}
