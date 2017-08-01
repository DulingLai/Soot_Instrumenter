package com.abaltatech.mcs.pipe;

import com.abaltatech.mcs.common.IMCSDataStats;
import com.abaltatech.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcs.common.MCSException;
import com.abaltatech.mcs.common.MemoryPool;
import com.abaltatech.mcs.logger.MCSLogger;
import com.abaltatech.mcs.utils.DataQueueArr;
import com.abaltatech.mcs.utils.DataQueueInt;

public class ByteDataLayer extends MCSDataLayerBase {
    private static final int MaxQueueSize = 16;
    private DataQueueArr m_inDataBuffers = new DataQueueArr(16);
    private DataQueueInt m_inDataSizes = new DataQueueInt(16);
    private IDataNotification m_notifiable;

    public void init(IDataNotification $r1) throws  {
        this.m_notifiable = $r1;
    }

    public void closeConnection() throws  {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public int readData(byte[] r13, int r14) throws  {
        /*
        r12 = this;
        monitor-enter(r12);
        r0 = r12.m_inDataBuffers;	 Catch:{ Throwable -> 0x0042 }
        r1 = r0.emptyq();	 Catch:{ Throwable -> 0x0042 }
        if (r1 == 0) goto L_0x000c;
    L_0x0009:
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0042 }
        r2 = 0;
        return r2;
    L_0x000c:
        r3 = 0;
        r0 = r12.m_inDataBuffers;	 Catch:{ Throwable -> 0x0042 }
        r4 = r0.delete();	 Catch:{ MCSException -> 0x0024 }
        r3 = r4;
        r5 = r12.m_inDataSizes;	 Catch:{ MCSException -> 0x0024 }
        r6 = r5.delete();	 Catch:{ MCSException -> 0x0024 }
        if (r14 >= r6) goto L_0x0038;
    L_0x001c:
        r7 = new com.abaltatech.mcs.common.MCSException;	 Catch:{ MCSException -> 0x0024 }
        r8 = "Buffer size too small";
        r7.<init>(r8);	 Catch:{ MCSException -> 0x0024 }
        throw r7;	 Catch:{ MCSException -> 0x0024 }
    L_0x0024:
        r7 = move-exception;
        r9 = r7.toString();	 Catch:{ Throwable -> 0x0042 }
        r8 = "ERROR";
        com.abaltatech.mcs.logger.MCSLogger.log(r8, r9);	 Catch:{ Throwable -> 0x0042 }
        if (r3 == 0) goto L_0x0035;
    L_0x0030:
        r8 = "ConnectionPointMTP";
        com.abaltatech.mcs.common.MemoryPool.freeMem(r3, r8);	 Catch:{ Throwable -> 0x0042 }
    L_0x0035:
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0042 }
        r2 = 0;
        return r2;
    L_0x0038:
        r2 = 0;
        r10 = 0;
        java.lang.System.arraycopy(r4, r2, r13, r10, r6);	 Catch:{ MCSException -> 0x0024 }
        com.abaltatech.mcs.common.MemoryPool.freeMem(r4);	 Catch:{ MCSException -> 0x0024 }
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0042 }
        return r6;
    L_0x0042:
        r11 = move-exception;
        monitor-exit(r12);	 Catch:{ Throwable -> 0x0042 }
        throw r11;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcs.pipe.ByteDataLayer.readData(byte[], int):int");
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        this.m_notifiable.onDataReceived($r1, $i0);
        IMCSDataStats $r3 = getDataStats();
        if ($r3 != null) {
            $r3.onDataSent($i0);
        }
    }

    public void addData(byte[] $r1, int $i0) throws  {
        try {
            byte[] $r3 = MemoryPool.getMem($i0, "ByteDataLayer");
            System.arraycopy($r1, 0, $r3, 0, $i0);
            synchronized (this) {
                this.m_inDataBuffers.insert($r3);
                this.m_inDataSizes.insert($i0);
                IMCSDataStats $r6 = getDataStats();
                if ($r6 != null) {
                    $r6.onDataReceived($i0);
                }
            }
            notifyForData();
        } catch (MCSException $r2) {
            MCSLogger.log("ByteDataLayer Exception", $r2.toString());
        }
    }
}
