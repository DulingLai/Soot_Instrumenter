package android.support.v4.net;

import android.net.ConnectivityManager;

class ConnectivityManagerCompatJellyBean {
    ConnectivityManagerCompatJellyBean() throws  {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager $r0) throws  {
        return $r0.isActiveNetworkMetered();
    }
}
