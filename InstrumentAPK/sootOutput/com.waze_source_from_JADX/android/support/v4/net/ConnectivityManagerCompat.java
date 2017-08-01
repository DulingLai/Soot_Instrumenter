package android.support.v4.net;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build.VERSION;

public final class ConnectivityManagerCompat {
    private static final ConnectivityManagerCompatImpl IMPL;

    interface ConnectivityManagerCompatImpl {
        boolean isActiveNetworkMetered(ConnectivityManager connectivityManager) throws ;
    }

    static class BaseConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        BaseConnectivityManagerCompatImpl() throws  {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager $r1) throws  {
            NetworkInfo $r2 = $r1.getActiveNetworkInfo();
            if ($r2 != null) {
                switch ($r2.getType()) {
                    case 0:
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

    static class GingerbreadConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        GingerbreadConnectivityManagerCompatImpl() throws  {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager $r1) throws  {
            return ConnectivityManagerCompatGingerbread.isActiveNetworkMetered($r1);
        }
    }

    static class HoneycombMR2ConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        HoneycombMR2ConnectivityManagerCompatImpl() throws  {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager $r1) throws  {
            return ConnectivityManagerCompatHoneycombMR2.isActiveNetworkMetered($r1);
        }
    }

    static class JellyBeanConnectivityManagerCompatImpl implements ConnectivityManagerCompatImpl {
        JellyBeanConnectivityManagerCompatImpl() throws  {
        }

        public boolean isActiveNetworkMetered(ConnectivityManager $r1) throws  {
            return ConnectivityManagerCompatJellyBean.isActiveNetworkMetered($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new JellyBeanConnectivityManagerCompatImpl();
        } else if (VERSION.SDK_INT >= 13) {
            IMPL = new HoneycombMR2ConnectivityManagerCompatImpl();
        } else if (VERSION.SDK_INT >= 8) {
            IMPL = new GingerbreadConnectivityManagerCompatImpl();
        } else {
            IMPL = new BaseConnectivityManagerCompatImpl();
        }
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager $r0) throws  {
        return IMPL.isActiveNetworkMetered($r0);
    }

    public static NetworkInfo getNetworkInfoFromBroadcast(ConnectivityManager $r0, Intent $r1) throws  {
        NetworkInfo $r3 = (NetworkInfo) $r1.getParcelableExtra("networkInfo");
        if ($r3 != null) {
            return $r0.getNetworkInfo($r3.getType());
        }
        return null;
    }

    private ConnectivityManagerCompat() throws  {
    }
}
