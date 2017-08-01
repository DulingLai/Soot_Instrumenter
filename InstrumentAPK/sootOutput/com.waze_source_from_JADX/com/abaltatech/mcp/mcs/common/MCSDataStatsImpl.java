package com.abaltatech.mcp.mcs.common;

import java.util.Date;

public class MCSDataStatsImpl implements IMCSDataStats {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int c_maxAveragingLen = 2000;
    private static StatInfo ms_freeList = null;
    private static boolean ms_statsticsEnabled = true;
    private long m_bytesReceived = 0;
    private long m_bytesSent = 0;
    private boolean m_enabled = false;
    private int m_maxReceivedThroughput = 0;
    private int m_maxSentThroughput = 0;
    private StatInfo m_receiveInfo = null;
    private StatInfo m_sendInfo = null;

    private static class StatInfo {
        int m_bytesCount;
        StatInfo m_next;
        long m_timestamp;

        private StatInfo() throws  {
        }
    }

    static {
        boolean $z0;
        if (MCSDataStatsImpl.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
    }

    public long getBytesSent() throws  {
        return this.m_bytesSent;
    }

    public long getBytesReceived() throws  {
        return this.m_bytesReceived;
    }

    public int getAverageSentThroughput() throws  {
        if (!this.m_enabled) {
            return 0;
        }
        StatInfo $r2 = this.m_sendInfo;
        long $l0 = new Date().getTime();
        int $i1 = 0;
        StatInfo $r1 = null;
        synchronized (this) {
            while ($r2 != null) {
                if (((int) ($l0 - $r2.m_timestamp)) > 2000) {
                    break;
                }
                $i1 += $r2.m_bytesCount;
                $r1 = $r2;
                if ($assertionsDisabled || $r2 != $r2.m_next) {
                    $r2 = $r2.m_next;
                } else {
                    throw new AssertionError();
                }
            }
            if ($r2 != null) {
                if ($r1 != null) {
                    if ($assertionsDisabled || $r1.m_next == $r2) {
                        $r1.m_next = null;
                    } else {
                        throw new AssertionError();
                    }
                } else if ($assertionsDisabled || this.m_sendInfo == $r2) {
                    this.m_sendInfo = null;
                } else {
                    throw new AssertionError();
                }
                while ($r2 != null) {
                    $r1 = $r2;
                    if ($assertionsDisabled || $r2 != $r2.m_next) {
                        $r2 = $r2.m_next;
                        releaseInfo($r1);
                    } else {
                        throw new AssertionError();
                    }
                }
            }
            if ($i1 == 0) {
                $i1 = 0;
            } else {
                $i1 = ($i1 * 1000) / 2000;
            }
        }
        return $i1;
    }

    public int getAverageReceivedThroughput() throws  {
        if (!this.m_enabled) {
            return 0;
        }
        StatInfo $r2 = this.m_receiveInfo;
        long $l0 = new Date().getTime();
        int $i1 = 0;
        StatInfo $r1 = null;
        synchronized (this) {
            while ($r2 != null) {
                if (((int) ($l0 - $r2.m_timestamp)) > 2000) {
                    break;
                }
                $i1 += $r2.m_bytesCount;
                $r1 = $r2;
                if ($assertionsDisabled || $r2 != $r2.m_next) {
                    $r2 = $r2.m_next;
                } else {
                    throw new AssertionError();
                }
            }
            if ($r2 != null) {
                if ($r1 != null) {
                    if ($assertionsDisabled || $r1.m_next == $r2) {
                        $r1.m_next = null;
                    } else {
                        throw new AssertionError();
                    }
                } else if ($assertionsDisabled || this.m_receiveInfo == $r2) {
                    this.m_receiveInfo = null;
                } else {
                    throw new AssertionError();
                }
                while ($r2 != null) {
                    $r1 = $r2;
                    if ($assertionsDisabled || $r2 != $r2.m_next) {
                        $r2 = $r2.m_next;
                        releaseInfo($r1);
                    } else {
                        throw new AssertionError();
                    }
                }
            }
            if ($i1 == 0) {
                $i1 = 0;
            } else {
                $i1 = ($i1 * 1000) / 2000;
            }
        }
        return $i1;
    }

    public int getMaxSentThroughput() throws  {
        return this.m_maxSentThroughput;
    }

    public int getMaxReceivedThroughput() throws  {
        return this.m_maxReceivedThroughput;
    }

    public synchronized void resetStats() throws  {
        this.m_bytesSent = 0;
        this.m_bytesReceived = 0;
        this.m_maxSentThroughput = 0;
        this.m_maxReceivedThroughput = 0;
        while (this.m_receiveInfo != null) {
            StatInfo $r1 = this.m_receiveInfo;
            if ($assertionsDisabled || this.m_receiveInfo != this.m_receiveInfo.m_next) {
                this.m_receiveInfo = this.m_receiveInfo.m_next;
                releaseInfo($r1);
            } else {
                throw new AssertionError();
            }
        }
        while (this.m_sendInfo != null) {
            $r1 = this.m_sendInfo;
            if ($assertionsDisabled || this.m_sendInfo != this.m_sendInfo.m_next) {
                this.m_sendInfo = this.m_sendInfo.m_next;
                releaseInfo($r1);
            } else {
                throw new AssertionError();
            }
        }
    }

    public void enable(boolean $z0) throws  {
        this.m_enabled = $z0;
    }

    public boolean isEnabled() throws  {
        return this.m_enabled;
    }

    public void onDataSent(int $i0) throws  {
        if (this.m_enabled) {
            StatInfo $r1 = getInfo();
            synchronized (this) {
                this.m_bytesSent += (long) $i0;
                if ($assertionsDisabled || $r1 != null) {
                    if ($r1 != null) {
                        $r1.m_bytesCount = $i0;
                        $r1.m_next = this.m_sendInfo;
                        this.m_sendInfo = $r1;
                        $i0 = getAverageSentThroughput();
                        if (this.m_maxSentThroughput < $i0) {
                            this.m_maxSentThroughput = $i0;
                        }
                    }
                } else {
                    throw new AssertionError();
                }
            }
        }
    }

    public void onDataReceived(int $i0) throws  {
        if (this.m_enabled) {
            StatInfo $r1 = getInfo();
            synchronized (this) {
                this.m_bytesReceived += (long) $i0;
                if ($assertionsDisabled || $r1 != null) {
                    if ($r1 != null) {
                        $r1.m_bytesCount = $i0;
                        $r1.m_next = this.m_receiveInfo;
                        this.m_receiveInfo = $r1;
                        $i0 = getAverageReceivedThroughput();
                        if (this.m_maxReceivedThroughput < $i0) {
                            this.m_maxReceivedThroughput = $i0;
                        }
                    }
                } else {
                    throw new AssertionError();
                }
            }
        }
    }

    public static void enableStats(boolean $z0) throws  {
        ms_statsticsEnabled = $z0;
    }

    public static boolean isStatsEnabled() throws  {
        return ms_statsticsEnabled;
    }

    private static StatInfo getInfo() throws  {
        StatInfo $r1 = null;
        synchronized (MCSDataStatsImpl.class) {
            try {
                if (ms_freeList != null) {
                    $r1 = ms_freeList;
                    if ($assertionsDisabled || ms_freeList != $r1.m_next) {
                        ms_freeList = $r1.m_next;
                    } else {
                        throw new AssertionError();
                    }
                }
            } catch (Throwable th) {
                Class cls = MCSDataStatsImpl.class;
            }
        }
        if ($r1 == null) {
            $r1 = new StatInfo();
        }
        if ($r1 == null) {
            return $r1;
        }
        $r1.m_next = null;
        $r1.m_bytesCount = 0;
        $r1.m_timestamp = new Date().getTime();
        return $r1;
    }

    private static void releaseInfo(StatInfo $r0) throws  {
        if (!$assertionsDisabled && $r0 == null) {
            throw new AssertionError();
        } else if ($r0 != null) {
            Class cls = MCSDataStatsImpl.class;
            synchronized (cls) {
                try {
                    if ($assertionsDisabled || $r0 != ms_freeList) {
                        $r0.m_next = ms_freeList;
                        ms_freeList = $r0;
                        return;
                    }
                    throw new AssertionError();
                } finally {
                    cls = MCSDataStatsImpl.class;
                }
            }
        }
    }
}
