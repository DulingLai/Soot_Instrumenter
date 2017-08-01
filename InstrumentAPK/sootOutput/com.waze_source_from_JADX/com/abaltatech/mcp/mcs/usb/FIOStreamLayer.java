package com.abaltatech.mcp.mcs.usb;

import com.abaltatech.mcp.mcs.common.IMCSDataStats;
import com.abaltatech.mcp.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcp.mcs.common.MemoryPool;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public abstract class FIOStreamLayer extends MCSDataLayerBase {
    private static final String TAG = "===> FIOStreamLayer";
    private boolean m_canceled = false;
    protected int m_dataLength;
    protected final int m_dataLengthFieldOffest;
    protected final int m_dataLengthFieldSize;
    protected final int m_headerLength;
    protected FileInputStream m_input = null;
    private final int[] m_magicBytes;
    protected FileOutputStream m_output = null;
    private byte[] m_readBuffer = null;
    private ReadThread m_readThread;
    private Object m_userData;

    private class ReadThread extends Thread {
        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x010b in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r20 = this;
            r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r3.<init>();	 Catch:{ InterruptedException -> 0x00b2 }
            r4 = "Thread started, m_canceled=";	 Catch:{ InterruptedException -> 0x00b2 }
            r3 = r3.append(r4);	 Catch:{ InterruptedException -> 0x00b2 }
            r0 = r20;	 Catch:{ InterruptedException -> 0x00b2 }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ InterruptedException -> 0x00b2 }
            r6 = r5.m_canceled;	 Catch:{ InterruptedException -> 0x00b2 }
            r3 = r3.append(r6);	 Catch:{ InterruptedException -> 0x00b2 }
            r7 = r3.toString();	 Catch:{ InterruptedException -> 0x00b2 }
            r4 = "===> FIOStreamLayer";	 Catch:{ InterruptedException -> 0x00b2 }
            com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r7);	 Catch:{ InterruptedException -> 0x00b2 }
        L_0x0020:
            r0 = r20;	 Catch:{ InterruptedException -> 0x00b2 }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ InterruptedException -> 0x00b2 }
            r6 = r5.m_canceled;	 Catch:{ InterruptedException -> 0x00b2 }
            if (r6 != 0) goto L_0x010b;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
        L_0x002a:
            monitor-enter(r20);	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r0 = r20;	 Catch:{ InterruptedException -> 0x00b2 }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ InterruptedException -> 0x00b2 }
            r8 = r5.m_input;	 Catch:{ InterruptedException -> 0x00b2 }
            r0 = r20;	 Catch:{ InterruptedException -> 0x00b2 }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ InterruptedException -> 0x00b2 }
            r6 = r5.isDataAvailable();	 Catch:{ InterruptedException -> 0x00b2 }
            monitor-exit(r20);	 Catch:{ InterruptedException -> 0x00b2 }
            if (r8 == 0) goto L_0x0103;
        L_0x003c:
            if (r6 != 0) goto L_0x0103;
        L_0x003e:
            r0 = r20;	 Catch:{ InterruptedException -> 0x00b2 }
            r6 = r0.readHeader(r8);	 Catch:{ InterruptedException -> 0x00b2 }
            if (r6 == 0) goto L_0x010b;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
        L_0x0046:
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r9 = r5.m_dataLength;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            if (r9 <= 0) goto L_0x00d7;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
        L_0x004e:
            r0 = r20;	 Catch:{ InterruptedException -> 0x00b2 }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ InterruptedException -> 0x00b2 }
            r10 = r5.m_readBuffer;	 Catch:{ InterruptedException -> 0x00b2 }
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r9 = r5.m_headerLength;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r11 = r5.m_dataLength;	 Catch:{ InterruptedException -> 0x00b2 }
            r9 = r8.read(r10, r9, r11);	 Catch:{ InterruptedException -> 0x00b2 }
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r11 = r5.m_dataLength;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            if (r9 == r11) goto L_0x00d7;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
        L_0x006e:
            r12 = new java.io.IOException;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r3 = new java.lang.StringBuilder;	 Catch:{ InterruptedException -> 0x00b2 }
            r3.<init>();	 Catch:{ InterruptedException -> 0x00b2 }
            r4 = "FIO Layer: incorrect data length received. Expected ";	 Catch:{ InterruptedException -> 0x00b2 }
            r3 = r3.append(r4);	 Catch:{ InterruptedException -> 0x00b2 }
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r11 = r5.m_dataLength;	 Catch:{  }
            r3 = r3.append(r11);	 Catch:{  }
            goto L_0x0089;	 Catch:{  }
        L_0x0086:
            goto L_0x0020;	 Catch:{  }
        L_0x0089:
            r4 = ", received ";	 Catch:{  }
            r3 = r3.append(r4);	 Catch:{  }
            r3 = r3.append(r9);	 Catch:{  }
            goto L_0x0097;	 Catch:{  }
        L_0x0094:
            goto L_0x0020;	 Catch:{  }
        L_0x0097:
            r7 = r3.toString();	 Catch:{  }
            r12.<init>(r7);	 Catch:{  }
            throw r12;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
        L_0x009f:
            r12 = move-exception;
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5.onIOException(r12);	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r4 = "===> FIOStreamLayer";
            r13 = "Thread ended";
            com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r13);
            return;
        L_0x00af:
            r14 = move-exception;
            monitor-exit(r20);	 Catch:{ InterruptedException -> 0x00b2 }
            throw r14;	 Catch:{ InterruptedException -> 0x00b2 }
        L_0x00b2:
            r15 = move-exception;
            r3 = new java.lang.StringBuilder;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r3.<init>();	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r4 = "InterruptedException: ";	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r3 = r3.append(r4);	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r7 = r15.getMessage();	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r3 = r3.append(r7);	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r7 = r3.toString();	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r4 = "===> FIOStreamLayer";	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r7);	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r4 = "===> FIOStreamLayer";
            r13 = "Thread ended";
            com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r13);
            return;
        L_0x00d7:
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r5 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r0 = r20;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r0 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r16 = r0;	 Catch:{ IOException -> 0x009f, Throwable -> 0x00fa }
            r10 = r0.m_readBuffer;	 Catch:{ InterruptedException -> 0x00b2 }
            r0 = r20;
            r0 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;
            r16 = r0;
            r9 = r0.m_headerLength;
            r0 = r20;
            r0 = com.abaltatech.mcp.mcs.usb.FIOStreamLayer.this;
            r16 = r0;
            r11 = r0.m_dataLength;
            r9 = r9 + r11;	 Catch:{ InterruptedException -> 0x00b2 }
            r5.onDataReceived(r10, r9);	 Catch:{ InterruptedException -> 0x00b2 }
            goto L_0x0086;
        L_0x00fa:
            r17 = move-exception;
            r4 = "===> FIOStreamLayer";
            r13 = "Thread ended";
            com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r13);
            throw r17;
        L_0x0103:
            r18 = 1;	 Catch:{ InterruptedException -> 0x00b2 }
            r0 = r18;	 Catch:{ InterruptedException -> 0x00b2 }
            java.lang.Thread.sleep(r0);	 Catch:{ InterruptedException -> 0x00b2 }
            goto L_0x0094;
        L_0x010b:
            r4 = "===> FIOStreamLayer";
            r13 = "Thread ended";
            com.abaltatech.mcp.mcs.logger.MCSLogger.log(r4, r13);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.usb.FIOStreamLayer.ReadThread.run():void");
        }

        private boolean readHeader(FileInputStream $r1) throws IOException {
            int $i0 = FIOStreamLayer.this.m_magicBytes.length;
            int $i2 = FIOStreamLayer.this.m_headerLength;
            int $i3 = $r1.read(FIOStreamLayer.this.m_readBuffer, 0, FIOStreamLayer.this.m_headerLength);
            if ($i3 != $i2) {
                throw new IOException("FIO Layer: incorrect header received. Expected " + FIOStreamLayer.this.m_headerLength + ", received " + ($i2 + $i3));
            }
            for ($i2 = 0; $i2 < $i0; $i2++) {
                if (FIOStreamLayer.this.m_readBuffer[$i2] != ((byte) FIOStreamLayer.this.m_magicBytes[$i2])) {
                    throw new IOException("FIO Layer: Incorrect magic bytes received!");
                }
            }
            FIOStreamLayer.this.m_dataLength = 0;
            for ($i0 = 0; $i0 < FIOStreamLayer.this.m_dataLengthFieldSize; $i0++) {
                $i2 = FIOStreamLayer.toInt(FIOStreamLayer.this.m_readBuffer[FIOStreamLayer.this.m_dataLengthFieldOffest + $i0]);
                FIOStreamLayer.this.m_dataLength <<= 8;
                FIOStreamLayer $r2 = FIOStreamLayer.this;
                $r2.m_dataLength += $i2;
            }
            if (FIOStreamLayer.this.m_dataLength + FIOStreamLayer.this.m_headerLength <= FIOStreamLayer.this.m_readBuffer.length) {
                return true;
            }
            throw new IOException("FIO Layer: TOO BIG packet received: " + (FIOStreamLayer.this.m_dataLength + FIOStreamLayer.this.m_headerLength) + ", max size is: " + FIOStreamLayer.this.m_readBuffer.length);
        }
    }

    protected abstract boolean isDataAvailable() throws ;

    protected abstract void onDataReceived(byte[] bArr, int i) throws ;

    protected abstract byte[] prepareDataHeader(byte[] bArr, int i) throws ;

    public FIOStreamLayer(byte[] $r1, int $i0, int $i1, int $i2) throws  {
        if ($r1 == null || $r1.length < 1) {
            throw new IllegalArgumentException("Argument magicBytes must be not null and with positive size");
        } else if ($i2 < $r1.length) {
            throw new IllegalArgumentException("Argument dataLengthFieldOffest must be >= magicBytes.length");
        } else if ($i1 < 1 || $i1 > 4) {
            throw new IllegalArgumentException("Argument dataLengthFieldSize must be between 1 and 4");
        } else if ($i0 < $i2 + $i1) {
            throw new IllegalArgumentException("Argument headerLength must be >= than dataLengthFieldOffest + dataLengthFieldSize");
        } else {
            this.m_magicBytes = new int[$r1.length];
            for (int $i4 = 0; $i4 < $r1.length; $i4++) {
                this.m_magicBytes[$i4] = toInt($r1[$i4]);
            }
            this.m_headerLength = $i0;
            this.m_dataLengthFieldSize = $i1;
            this.m_dataLengthFieldOffest = $i2;
            this.m_readBuffer = new byte[MemoryPool.BufferSizeBig];
            this.m_readThread = new ReadThread();
            this.m_readThread.setName("FIO Read Thread");
            this.m_readThread.start();
        }
    }

    protected static int toInt(byte $b0) throws  {
        return $b0 & (short) 255;
    }

    public synchronized void attachStreams(FileInputStream $r1, FileOutputStream $r2) throws  {
        this.m_input = $r1;
        this.m_output = $r2;
    }

    protected void onIOException(IOException $r1) throws  {
        MCSLogger.log("===> FIOStreamLayer EXCEPTION", $r1.toString());
        closeConnection();
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        writeInternal(prepareDataHeader($r1, $i0), $r1, $i0);
    }

    protected void writeInternal(byte[] $r1, byte[] $r2, int $i0) throws  {
        synchronized (this) {
            FileOutputStream $r4 = this.m_output;
        }
        if ($r1 != null && $r4 != null) {
            try {
                IMCSDataStats $r5 = getDataStats();
                $r4.write($r1, 0, this.m_headerLength);
                if ($i0 > 0) {
                    $r4.write($r2, 0, $i0);
                }
                if ($r5 != null) {
                    $r5.onDataSent(Math.max(this.m_headerLength, 0) + $i0);
                }
            } catch (IOException $r3) {
                onIOException($r3);
            }
        }
    }

    public void closeConnection() throws  {
        MCSLogger.log(TAG, "===> closeConnection() requested.");
        synchronized (this) {
            this.m_canceled = true;
            if (this.m_input != null) {
                try {
                    this.m_input.close();
                } catch (IOException e) {
                }
                this.m_input = null;
            }
            if (this.m_output != null) {
                try {
                    this.m_output.close();
                } catch (IOException e2) {
                }
                this.m_output = null;
            }
        }
        onConnectionClosed();
        notifyForConnectionClosed();
        clearNotifiables();
    }

    protected void onConnectionClosed() throws  {
    }

    public void setUserData(Object $r1) throws  {
        this.m_userData = $r1;
    }

    public Object getUserData() throws  {
        return this.m_userData;
    }
}
