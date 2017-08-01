package android.support.v4.net;

import android.net.TrafficStats;
import java.net.Socket;
import java.net.SocketException;

class TrafficStatsCompatIcs {
    TrafficStatsCompatIcs() throws  {
    }

    public static void clearThreadStatsTag() throws  {
        TrafficStats.clearThreadStatsTag();
    }

    public static int getThreadStatsTag() throws  {
        return TrafficStats.getThreadStatsTag();
    }

    public static void incrementOperationCount(int $i0) throws  {
        TrafficStats.incrementOperationCount($i0);
    }

    public static void incrementOperationCount(int $i0, int $i1) throws  {
        TrafficStats.incrementOperationCount($i0, $i1);
    }

    public static void setThreadStatsTag(int $i0) throws  {
        TrafficStats.setThreadStatsTag($i0);
    }

    public static void tagSocket(Socket $r0) throws SocketException {
        TrafficStats.tagSocket($r0);
    }

    public static void untagSocket(Socket $r0) throws SocketException {
        TrafficStats.untagSocket($r0);
    }
}
