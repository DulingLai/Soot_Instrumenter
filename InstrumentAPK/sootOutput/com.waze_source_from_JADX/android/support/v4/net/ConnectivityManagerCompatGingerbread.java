package android.support.v4.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class ConnectivityManagerCompatGingerbread {
    ConnectivityManagerCompatGingerbread() throws  {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager $r0) throws  {
        NetworkInfo $r1 = $r0.getActiveNetworkInfo();
        if ($r1 != null) {
            switch ($r1.getType()) {
                case 0:
                case 2:
                case 3:
                case 4:
                case 5:
                case 6:
                    break;
                case 1:
                    return false;
                default:
                    return true;
            }
        }
        return true;
    }
}
