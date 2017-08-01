package android.support.v4.net;

import android.os.Build.VERSION;
import java.net.Socket;
import java.net.SocketException;

public final class TrafficStatsCompat {
    private static final TrafficStatsCompatImpl IMPL;

    interface TrafficStatsCompatImpl {
        void clearThreadStatsTag() throws ;

        int getThreadStatsTag() throws ;

        void incrementOperationCount(int i) throws ;

        void incrementOperationCount(int i, int i2) throws ;

        void setThreadStatsTag(int i) throws ;

        void tagSocket(Socket socket) throws SocketException;

        void untagSocket(Socket socket) throws SocketException;
    }

    static class BaseTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        private ThreadLocal<SocketTags> mThreadSocketTags = new C00961();

        class C00961 extends ThreadLocal<SocketTags> {
            C00961() throws  {
            }

            protected SocketTags initialValue() throws  {
                return new SocketTags();
            }
        }

        private static class SocketTags {
            public int statsTag;

            private SocketTags() throws  {
                this.statsTag = -1;
            }
        }

        BaseTrafficStatsCompatImpl() throws  {
        }

        public void clearThreadStatsTag() throws  {
            ((SocketTags) this.mThreadSocketTags.get()).statsTag = -1;
        }

        public int getThreadStatsTag() throws  {
            return ((SocketTags) this.mThreadSocketTags.get()).statsTag;
        }

        public void incrementOperationCount(int operationCount) throws  {
        }

        public void incrementOperationCount(int tag, int operationCount) throws  {
        }

        public void setThreadStatsTag(int $i0) throws  {
            ((SocketTags) this.mThreadSocketTags.get()).statsTag = $i0;
        }

        public void tagSocket(Socket socket) throws  {
        }

        public void untagSocket(Socket socket) throws  {
        }
    }

    static class IcsTrafficStatsCompatImpl implements TrafficStatsCompatImpl {
        IcsTrafficStatsCompatImpl() throws  {
        }

        public void clearThreadStatsTag() throws  {
            TrafficStatsCompatIcs.clearThreadStatsTag();
        }

        public int getThreadStatsTag() throws  {
            return TrafficStatsCompatIcs.getThreadStatsTag();
        }

        public void incrementOperationCount(int $i0) throws  {
            TrafficStatsCompatIcs.incrementOperationCount($i0);
        }

        public void incrementOperationCount(int $i0, int $i1) throws  {
            TrafficStatsCompatIcs.incrementOperationCount($i0, $i1);
        }

        public void setThreadStatsTag(int $i0) throws  {
            TrafficStatsCompatIcs.setThreadStatsTag($i0);
        }

        public void tagSocket(Socket $r1) throws SocketException {
            TrafficStatsCompatIcs.tagSocket($r1);
        }

        public void untagSocket(Socket $r1) throws SocketException {
            TrafficStatsCompatIcs.untagSocket($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new IcsTrafficStatsCompatImpl();
        } else {
            IMPL = new BaseTrafficStatsCompatImpl();
        }
    }

    public static void clearThreadStatsTag() throws  {
        IMPL.clearThreadStatsTag();
    }

    public static int getThreadStatsTag() throws  {
        return IMPL.getThreadStatsTag();
    }

    public static void incrementOperationCount(int $i0) throws  {
        IMPL.incrementOperationCount($i0);
    }

    public static void incrementOperationCount(int $i0, int $i1) throws  {
        IMPL.incrementOperationCount($i0, $i1);
    }

    public static void setThreadStatsTag(int $i0) throws  {
        IMPL.setThreadStatsTag($i0);
    }

    public static void tagSocket(Socket $r0) throws SocketException {
        IMPL.tagSocket($r0);
    }

    public static void untagSocket(Socket $r0) throws SocketException {
        IMPL.untagSocket($r0);
    }

    private TrafficStatsCompat() throws  {
    }
}
