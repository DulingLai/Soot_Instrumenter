package com.abaltatech.mcs.common;

import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.utils.DataQueueArr;
import java.lang.reflect.Array;

public class MemoryPool {
    private static final String BIG_TAG = "Big";
    public static int BufferCount = 512;
    public static int BufferSizeBig = 16384;
    private static final boolean DEBUG_POOL = false;
    private static byte[][] m_buffersBig = null;
    private static boolean m_checkFreedMemory = false;
    private static MemoryPoolTrackerList m_checkedOut;
    private static boolean m_dumpMemoryInfo = false;
    private static DataQueueArr m_freeBuffersBig;
    private static boolean m_isInitOK = false;

    public static boolean isInitialized() throws  {
        return m_isInitOK;
    }

    public static void setCheckFreedMemory(boolean $z0) throws  {
        synchronized (m_freeBuffersBig) {
            m_checkFreedMemory = $z0;
        }
    }

    public static boolean getCheckFreedMemory() throws  {
        boolean z0;
        synchronized (m_freeBuffersBig) {
            z0 = m_checkFreedMemory;
        }
        return z0;
    }

    public static void setDumpMemoryInfo(boolean $z0) throws  {
        synchronized (m_freeBuffersBig) {
            m_dumpMemoryInfo = $z0;
        }
    }

    public static boolean getDumpMemoryInfo() throws  {
        boolean z0;
        synchronized (m_freeBuffersBig) {
            z0 = m_dumpMemoryInfo;
        }
        return z0;
    }

    public static void init(int $i0, int $i1) throws MCSException {
        BufferCount = $i1;
        m_checkedOut = new MemoryPoolTrackerList();
        BufferSizeBig = $i0;
        $i0 = BufferCount;
        $i1 = BufferSizeBig;
        m_buffersBig = (byte[][]) Array.newInstance(Byte.TYPE, new int[]{$i0, $i1});
        m_freeBuffersBig = new DataQueueArr(BufferCount);
        synchronized (m_freeBuffersBig) {
            for ($i0 = 0; $i0 < BufferCount; $i0++) {
                byte[] $r0 = m_buffersBig[$i0];
                m_freeBuffersBig.insert($r0);
                if ($r0.length != BufferSizeBig) {
                    throw new MCSException("Invalid Buffer Size");
                }
            }
            m_isInitOK = true;
        }
    }

    public static byte[] getMem(int $i0, String $r0) throws MCSException {
        if (m_freeBuffersBig == null) {
            throw new MCSException("Memory Manager is not initialized");
        }
        byte[] $r5;
        synchronized (m_freeBuffersBig) {
            if (!m_isInitOK) {
                throw new MCSException("Memory Manager is not initialized");
            } else if ($i0 > BufferSizeBig) {
                throw new MCSException("Requested memory size too large");
            } else if ($i0 > BufferSizeBig || m_freeBuffersBig.size() <= 0) {
                throw new MCSException("No free memory of such big size - " + $i0);
            } else {
                $r5 = getMem(m_freeBuffersBig);
                printMemStat(false, BIG_TAG, m_freeBuffersBig, $r0);
            }
        }
        return $r5;
    }

    private static void printMemStat(boolean $z0, String $r0, DataQueueArr $r1, String $r2) throws  {
        if (m_dumpMemoryInfo) {
            MCSLogger.log("FREE MEM " + $r0 + ($z0 ? " + " : " - "), "" + $r1.size() + "   " + $r2);
        }
    }

    private static byte[] getMem(DataQueueArr $r0) throws MCSException {
        byte[] $r1 = $r0.get();
        $r0.delete();
        return $r1;
    }

    public static void freeMem(byte[] $r0) throws MCSException {
        if (m_freeBuffersBig == null) {
            throw new MCSException("Memory Manager is not initialized");
        }
        synchronized (m_freeBuffersBig) {
            if (m_isInitOK) {
                if ($r0 != null) {
                    synchronized (m_freeBuffersBig) {
                        if ($r0.length == BufferSizeBig) {
                            checkMem($r0, m_freeBuffersBig);
                            m_freeBuffersBig.insert($r0);
                            printMemStat(true, BIG_TAG, m_freeBuffersBig, "");
                        } else {
                            throw new MCSException("Returning memory of invalid size");
                        }
                    }
                }
            } else {
                throw new MCSException("Returning memory which is not allocated by MemoryPool");
            }
        }
    }

    private static void freeMemTrack(byte[] $r0) throws  {
        synchronized (m_checkedOut) {
            for (MemoryPoolTracker $r3 = m_checkedOut.Head; $r3 != null; $r3 = $r3.m_next) {
                if ($r3.m_memory == $r0) {
                    m_checkedOut.remove($r3);
                    return;
                }
            }
        }
    }

    public static void freeMem(byte[] $r0, String $r1) throws  {
        if (m_dumpMemoryInfo) {
            try {
                MCSLogger.log("FREE MEM BIG", $r1);
            } catch (MCSException $r2) {
                MCSLogger.log($r1, $r2.toString());
                return;
            }
        }
        freeMem($r0);
    }

    private static void checkMem(byte[] $r0, DataQueueArr $r1) throws MCSException {
        if (!m_checkFreedMemory) {
            return;
        }
        if ($r0.length != BufferSizeBig) {
            throw new MCSException("Invalid buffer size in freeMem()");
        } else if ($r1.contains($r0)) {
            throw new MCSException("Memory freed twice");
        }
    }
}
