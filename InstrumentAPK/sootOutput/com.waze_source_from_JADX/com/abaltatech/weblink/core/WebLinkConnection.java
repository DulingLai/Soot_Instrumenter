package com.abaltatech.weblink.core;

import com.abaltatech.mcs.common.IMCSDataLayer;
import com.abaltatech.mcs.common.IMCSDataLayerNotification;
import com.abaltatech.weblink.core.commandhandling.Command;
import com.abaltatech.weblink.core.commandhandling.ICommandHandler;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

public abstract class WebLinkConnection implements IMCSDataLayerNotification {
    static final /* synthetic */ boolean $assertionsDisabled = (!WebLinkConnection.class.desiredAssertionStatus());
    private static final int MAX_COMMANDS = 16;
    protected long m_bytesRead = 0;
    protected long m_bytesReadPrev = 0;
    protected long m_bytesWritten = 0;
    protected long m_bytesWrittenPrev = 0;
    protected ArrayBlockingQueue<Command> m_cmdQueue = new ArrayBlockingQueue(16);
    protected boolean m_cmdStartDetected;
    protected OnCommandDetectedListener m_commandDetectedListener;
    protected boolean m_connClosed = false;
    protected IMCSDataLayer m_dataLayer = null;
    protected ConcurrentHashMap<Short, ICommandHandler> m_handlers = new ConcurrentHashMap();
    protected DataBuffer m_inputDataBuffer = new DataBuffer(16384);
    protected byte[] m_readBuffer = new byte[16384];
    protected int m_readDataRate = 0;
    protected long m_statsReadTimestamp = 0;
    protected long m_statsWriteTimestamp = 0;
    protected boolean m_suspended = false;
    protected Thread m_thread = null;
    protected int m_writeDataRate = 0;

    class C03901 extends Thread {
        static final /* synthetic */ boolean $assertionsDisabled = (!WebLinkConnection.class.desiredAssertionStatus());
        private volatile boolean m_isStopped = false;

        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005b in list []
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
            r21 = this;
            r2 = 0;
        L_0x0001:
            if (r2 != 0) goto L_0x0051;
        L_0x0003:
            r0 = r21;	 Catch:{ InterruptedException -> 0x0050 }
            r3 = r0.isInterrupted();	 Catch:{ InterruptedException -> 0x0050 }
            if (r3 != 0) goto L_0x0051;
        L_0x000b:
            r0 = r21;
            r3 = r0.m_isStopped;
            if (r3 != 0) goto L_0x0051;
        L_0x0011:
            r0 = r21;
            r4 = com.abaltatech.weblink.core.WebLinkConnection.this;
            r5 = r4.m_cmdQueue;	 Catch:{ InterruptedException -> 0x0050 }
            r6 = r5.take();	 Catch:{ InterruptedException -> 0x0050 }
            r8 = r6;	 Catch:{ InterruptedException -> 0x0050 }
            r8 = (com.abaltatech.weblink.core.commandhandling.Command) r8;	 Catch:{ InterruptedException -> 0x0050 }
            r7 = r8;	 Catch:{ InterruptedException -> 0x0050 }
            r3 = r7.isValid();	 Catch:{ InterruptedException -> 0x0050 }
            if (r3 == 0) goto L_0x0001;	 Catch:{ InterruptedException -> 0x0050 }
        L_0x0025:
            r9 = r7.getRawCommandData();	 Catch:{ InterruptedException -> 0x0050 }
            r0 = r21;
            r4 = com.abaltatech.weblink.core.WebLinkConnection.this;
            r10 = r4.m_dataLayer;
            r0 = r21;
            r4 = com.abaltatech.weblink.core.WebLinkConnection.this;
            r2 = r4.m_connClosed;
            if (r2 != 0) goto L_0x0039;
        L_0x0037:
            if (r10 != 0) goto L_0x0059;
        L_0x0039:
            r2 = 1;
        L_0x003a:
            if (r2 != 0) goto L_0x0001;
        L_0x003c:
            r3 = $assertionsDisabled;
            if (r3 != 0) goto L_0x005b;	 Catch:{ InterruptedException -> 0x0050 }
        L_0x0040:
            goto L_0x0044;	 Catch:{ InterruptedException -> 0x0050 }
        L_0x0041:
            goto L_0x0001;	 Catch:{ InterruptedException -> 0x0050 }
        L_0x0044:
            r11 = r9.getPos();	 Catch:{ InterruptedException -> 0x0050 }
            if (r11 == 0) goto L_0x005b;
        L_0x004a:
            r12 = new java.lang.AssertionError;	 Catch:{ InterruptedException -> 0x0050 }
            r12.<init>();	 Catch:{ InterruptedException -> 0x0050 }
            throw r12;
        L_0x0050:
            r13 = move-exception;
        L_0x0051:
            r14 = "WebLinkConnection";
            r15 = "Processing thread stopped";
            com.abaltatech.mcs.logger.MCSLogger.log(r14, r15);
            return;
        L_0x0059:
            r2 = 0;
            goto L_0x003a;
        L_0x005b:
            r0 = r21;
            r4 = com.abaltatech.weblink.core.WebLinkConnection.this;
            r4.onCommandSendingStarted(r7);	 Catch:{ InterruptedException -> 0x0050 }
            r16 = r9.getData();	 Catch:{ InterruptedException -> 0x0050 }
            r11 = r9.getSize();	 Catch:{ InterruptedException -> 0x0050 }
            r0 = r16;	 Catch:{ InterruptedException -> 0x0050 }
            r10.writeData(r0, r11);	 Catch:{ InterruptedException -> 0x0050 }
            r0 = r21;
            r4 = com.abaltatech.weblink.core.WebLinkConnection.this;
            r0 = r4.m_bytesWritten;	 Catch:{ InterruptedException -> 0x0050 }
            r17 = r0;	 Catch:{ InterruptedException -> 0x0050 }
            r11 = r9.getSize();	 Catch:{ InterruptedException -> 0x0050 }
            r0 = (long) r11;
            r19 = r0;
            r19 = r17 + r19;
            r0 = r19;
            r4.m_bytesWritten = r0;
            r0 = r21;	 Catch:{ InterruptedException -> 0x0050 }
            r4 = com.abaltatech.weblink.core.WebLinkConnection.this;	 Catch:{ InterruptedException -> 0x0050 }
            r4.onCommandSendingCompleted(r7);	 Catch:{ InterruptedException -> 0x0050 }
            goto L_0x0041;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblink.core.WebLinkConnection.1.run():void");
        }

        C03901() throws  {
        }

        public void interrupt() throws  {
            this.m_isStopped = true;
            super.interrupt();
        }
    }

    public interface OnCommandDetectedListener {
        void onCommandDetected(long j, int i) throws ;
    }

    protected abstract long getSystemTime() throws ;

    public void onDataReceived(com.abaltatech.mcs.common.IMCSDataLayer r35) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:45:0x0176 in {7, 11, 15, 18, 22, 31, 40, 42, 44, 46, 51, 54, 59, 65} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r34 = this;
        monitor-enter(r34);
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0 = r0.m_dataLayer;	 Catch:{ Throwable -> 0x0047 }
        r35 = r0;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r2 = r0.getSystemTime();	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r4 = r0.m_connClosed;	 Catch:{ Throwable -> 0x0047 }
        if (r4 != 0) goto L_0x0038;	 Catch:{ Throwable -> 0x0047 }
    L_0x0013:
        if (r35 == 0) goto L_0x0038;	 Catch:{ Throwable -> 0x0047 }
    L_0x0015:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r6 = r5.getSize();	 Catch:{ Throwable -> 0x0047 }
        r4 = 0;	 Catch:{ Throwable -> 0x0047 }
        if (r6 != 0) goto L_0x003a;	 Catch:{ Throwable -> 0x0047 }
    L_0x0020:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r5.reset();	 Catch:{ Throwable -> 0x0047 }
    L_0x0027:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r7 = r0.m_readBuffer;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r8 = r0.m_readBuffer;	 Catch:{ Throwable -> 0x0047 }
        r6 = r8.length;	 Catch:{ Throwable -> 0x0047 }
        r0 = r35;	 Catch:{ Throwable -> 0x0047 }
        r6 = r0.readData(r7, r6);	 Catch:{ Throwable -> 0x0047 }
        if (r6 > 0) goto L_0x004a;	 Catch:{ Throwable -> 0x0047 }
    L_0x0038:
        monitor-exit(r34);	 Catch:{ Throwable -> 0x0047 }
        return;
    L_0x003a:
        r9 = 32768; // 0x8000 float:4.5918E-41 double:1.61895E-319;	 Catch:{ Throwable -> 0x0047 }
        if (r6 <= r9) goto L_0x0027;	 Catch:{ Throwable -> 0x0047 }
    L_0x003f:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r5.normalizeBuffer();	 Catch:{ Throwable -> 0x0047 }
        goto L_0x0027;	 Catch:{ Throwable -> 0x0047 }
    L_0x0047:
        r10 = move-exception;	 Catch:{ Throwable -> 0x0047 }
        monitor-exit(r34);	 Catch:{ Throwable -> 0x0047 }
        throw r10;
    L_0x004a:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r11 = r0.m_bytesRead;	 Catch:{ Throwable -> 0x0047 }
        r13 = (long) r6;	 Catch:{ Throwable -> 0x0047 }
        r11 = r11 + r13;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0.m_bytesRead = r11;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r7 = r0.m_readBuffer;	 Catch:{ Throwable -> 0x0047 }
        r9 = 0;	 Catch:{ Throwable -> 0x0047 }
        r15 = r5.addBytes(r7, r9, r6);	 Catch:{ Throwable -> 0x0047 }
        if (r15 != 0) goto L_0x006f;	 Catch:{ Throwable -> 0x0047 }
    L_0x0063:
        r16 = "WLConnection";	 Catch:{ Throwable -> 0x0047 }
        r17 = "Error allocating data buffer !";	 Catch:{ Throwable -> 0x0047 }
        r0 = r16;	 Catch:{ Throwable -> 0x0047 }
        r1 = r17;	 Catch:{ Throwable -> 0x0047 }
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);	 Catch:{ Throwable -> 0x0047 }
        goto L_0x0038;	 Catch:{ Throwable -> 0x0047 }
    L_0x006f:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r18 = r5.getSize();	 Catch:{ Throwable -> 0x0047 }
        r9 = 8;	 Catch:{ Throwable -> 0x0047 }
        r0 = r18;	 Catch:{ Throwable -> 0x0047 }
        if (r0 < r9) goto L_0x01fc;	 Catch:{ Throwable -> 0x0047 }
    L_0x007d:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r19 = r5.getPos();	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r7 = r5.getData();	 Catch:{ Throwable -> 0x0047 }
        r0 = r19;	 Catch:{ Throwable -> 0x0047 }
        r1 = r18;	 Catch:{ Throwable -> 0x0047 }
        r20 = com.abaltatech.weblink.core.commandhandling.Command.findValidCommand(r7, r0, r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r20;	 Catch:{ Throwable -> 0x0047 }
        r1 = r19;	 Catch:{ Throwable -> 0x0047 }
        if (r0 < r1) goto L_0x020b;	 Catch:{ Throwable -> 0x0047 }
    L_0x009b:
        r19 = r20 - r19;	 Catch:{ Throwable -> 0x0047 }
        r0 = r18;	 Catch:{ Throwable -> 0x0047 }
        r1 = r19;	 Catch:{ Throwable -> 0x0047 }
        r0 = r0 - r1;	 Catch:{ Throwable -> 0x0047 }
        r18 = r0;	 Catch:{ Throwable -> 0x0047 }
        r5 = new com.abaltatech.weblink.core.DataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r21 = r0;	 Catch:{ Throwable -> 0x0047 }
        r7 = r0.getData();	 Catch:{ Throwable -> 0x0047 }
        r0 = r20;	 Catch:{ Throwable -> 0x0047 }
        r1 = r18;	 Catch:{ Throwable -> 0x0047 }
        r5.<init>(r7, r0, r1);	 Catch:{ Throwable -> 0x0047 }
        r22 = com.abaltatech.weblink.core.commandhandling.Command.getCommandID(r5);	 Catch:{ Throwable -> 0x0047 }
        r23 = com.abaltatech.weblink.core.commandhandling.Command.getPayloadSize(r5);	 Catch:{ Throwable -> 0x0047 }
        r23 = r23 + 8;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r15 = r0.m_cmdStartDetected;	 Catch:{ Throwable -> 0x0047 }
        if (r15 != 0) goto L_0x00df;	 Catch:{ Throwable -> 0x0047 }
    L_0x00c7:
        r9 = 1;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0.m_cmdStartDetected = r9;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0 = r0.m_commandDetectedListener;	 Catch:{ Throwable -> 0x0047 }
        r24 = r0;	 Catch:{ Throwable -> 0x0047 }
        if (r24 == 0) goto L_0x00df;	 Catch:{ Throwable -> 0x0047 }
    L_0x00d4:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0 = r0.m_commandDetectedListener;	 Catch:{ Throwable -> 0x0047 }
        r24 = r0;	 Catch:{ Throwable -> 0x0047 }
        r1 = r22;	 Catch:{ Throwable -> 0x0047 }
        r0.onCommandDetected(r2, r1);	 Catch:{ Throwable -> 0x0047 }
    L_0x00df:
        r0 = r18;	 Catch:{ Throwable -> 0x0047 }
        r1 = r23;	 Catch:{ Throwable -> 0x0047 }
        if (r0 < r1) goto L_0x01bf;	 Catch:{ Throwable -> 0x0047 }
    L_0x00e5:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r4 = r0.isSuspended();	 Catch:{ Throwable -> 0x0047 }
        if (r4 != 0) goto L_0x0132;	 Catch:{ Throwable -> 0x0047 }
    L_0x00ed:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0 = r0.m_handlers;	 Catch:{ Throwable -> 0x0047 }
        r25 = r0;	 Catch:{ Throwable -> 0x0047 }
        r0 = r22;	 Catch:{ Throwable -> 0x0047 }
        r26 = java.lang.Short.valueOf(r0);	 Catch:{ Throwable -> 0x0047 }
        r0 = r25;	 Catch:{ Throwable -> 0x0047 }
        r1 = r26;	 Catch:{ Throwable -> 0x0047 }
        r27 = r0.get(r1);	 Catch:{ Throwable -> 0x0047 }
        r29 = r27;	 Catch:{ Throwable -> 0x0047 }
        r29 = (com.abaltatech.weblink.core.commandhandling.ICommandHandler) r29;	 Catch:{ Throwable -> 0x0047 }
        r28 = r29;	 Catch:{ Throwable -> 0x0047 }
        if (r28 == 0) goto L_0x018c;	 Catch:{ Throwable -> 0x0047 }
    L_0x0109:
        r5 = new com.abaltatech.weblink.core.DataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r21 = r0;	 Catch:{ Throwable -> 0x0047 }
        r7 = r0.getData();	 Catch:{ Throwable -> 0x0047 }
        r0 = r20;	 Catch:{ Throwable -> 0x0047 }
        r1 = r23;	 Catch:{ Throwable -> 0x0047 }
        r5.<init>(r7, r0, r1);	 Catch:{ Throwable -> 0x0047 }
        r30 = new com.abaltatech.weblink.core.commandhandling.Command;	 Catch:{ Throwable -> 0x0047 }
        r0 = r30;	 Catch:{ Throwable -> 0x0047 }
        r0.<init>(r5);	 Catch:{ Throwable -> 0x0047 }
        r0 = r30;	 Catch:{ Throwable -> 0x0047 }
        r4 = r0.isValid();	 Catch:{ Throwable -> 0x0047 }
        if (r4 == 0) goto L_0x0180;	 Catch:{ Throwable -> 0x0047 }
    L_0x012b:
        r0 = r28;	 Catch:{ Throwable -> 0x0047 }
        r1 = r30;	 Catch:{ Throwable -> 0x0047 }
        r0.handleCommand(r1);	 Catch:{ Throwable -> 0x0047 }
    L_0x0132:
        if (r19 <= 0) goto L_0x0166;	 Catch:{ Throwable -> 0x0047 }
    L_0x0134:
        r31 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r0.<init>();	 Catch:{ Throwable -> 0x0047 }
        r16 = "Warning: Discarded ";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r19;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r16 = " bytes!";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r32 = r0.toString();	 Catch:{ Throwable -> 0x0047 }
        r16 = "WLConnection";	 Catch:{ Throwable -> 0x0047 }
        r0 = r16;	 Catch:{ Throwable -> 0x0047 }
        r1 = r32;	 Catch:{ Throwable -> 0x0047 }
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);	 Catch:{ Throwable -> 0x0047 }
    L_0x0166:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r20 = r19 + r23;	 Catch:{ Throwable -> 0x0047 }
        r0 = r20;	 Catch:{ Throwable -> 0x0047 }
        r5.discardBytesFromStart(r0);	 Catch:{ Throwable -> 0x0047 }
        r4 = 1;	 Catch:{ Throwable -> 0x0047 }
        goto L_0x017a;	 Catch:{ Throwable -> 0x0047 }
    L_0x0173:
        goto L_0x006f;	 Catch:{ Throwable -> 0x0047 }
        goto L_0x017a;	 Catch:{ Throwable -> 0x0047 }
    L_0x0177:
        goto L_0x0132;	 Catch:{ Throwable -> 0x0047 }
    L_0x017a:
        r9 = 0;	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r0.m_cmdStartDetected = r9;	 Catch:{ Throwable -> 0x0047 }
        goto L_0x0173;	 Catch:{ Throwable -> 0x0047 }
    L_0x0180:
        r4 = $assertionsDisabled;	 Catch:{ Throwable -> 0x0047 }
        if (r4 != 0) goto L_0x0132;	 Catch:{ Throwable -> 0x0047 }
    L_0x0184:
        r33 = new java.lang.AssertionError;	 Catch:{ Throwable -> 0x0047 }
        r0 = r33;	 Catch:{ Throwable -> 0x0047 }
        r0.<init>();	 Catch:{ Throwable -> 0x0047 }
        throw r33;	 Catch:{ Throwable -> 0x0047 }
    L_0x018c:
        r31 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r0.<init>();	 Catch:{ Throwable -> 0x0047 }
        r16 = "Command ";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r22;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r16 = " is not handled !";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r32 = r0.toString();	 Catch:{ Throwable -> 0x0047 }
        r16 = "WLConnection";	 Catch:{ Throwable -> 0x0047 }
        r0 = r16;	 Catch:{ Throwable -> 0x0047 }
        r1 = r32;	 Catch:{ Throwable -> 0x0047 }
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);	 Catch:{ Throwable -> 0x0047 }
        goto L_0x0177;	 Catch:{ Throwable -> 0x0047 }
    L_0x01bf:
        if (r19 <= 0) goto L_0x01fc;	 Catch:{ Throwable -> 0x0047 }
    L_0x01c1:
        r31 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r0.<init>();	 Catch:{ Throwable -> 0x0047 }
        r16 = "Warning: Discarded ";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r19;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r16 = " bytes!";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r32 = r0.toString();	 Catch:{ Throwable -> 0x0047 }
        r16 = "WLConnection";	 Catch:{ Throwable -> 0x0047 }
        r0 = r16;	 Catch:{ Throwable -> 0x0047 }
        r1 = r32;	 Catch:{ Throwable -> 0x0047 }
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r0 = r19;	 Catch:{ Throwable -> 0x0047 }
        r5.discardBytesFromStart(r0);	 Catch:{ Throwable -> 0x0047 }
    L_0x01fc:
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r15 = r0.m_connClosed;	 Catch:{ Throwable -> 0x0047 }
        if (r15 != 0) goto L_0x0038;	 Catch:{ Throwable -> 0x0047 }
    L_0x0202:
        if (r6 > 0) goto L_0x0015;	 Catch:{ Throwable -> 0x0047 }
    L_0x0204:
        goto L_0x0208;	 Catch:{ Throwable -> 0x0047 }
    L_0x0205:
        goto L_0x0038;	 Catch:{ Throwable -> 0x0047 }
    L_0x0208:
        if (r4 != 0) goto L_0x0015;	 Catch:{ Throwable -> 0x0047 }
    L_0x020a:
        goto L_0x0205;	 Catch:{ Throwable -> 0x0047 }
    L_0x020b:
        r9 = 8;	 Catch:{ Throwable -> 0x0047 }
        r0 = r18;	 Catch:{ Throwable -> 0x0047 }
        if (r0 <= r9) goto L_0x01fc;	 Catch:{ Throwable -> 0x0047 }
    L_0x0211:
        r31 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r0.<init>();	 Catch:{ Throwable -> 0x0047 }
        r16 = "Warning: Discarded ";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r20 = r18 + -8;	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r20;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r16 = " bytes!";	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r1 = r16;	 Catch:{ Throwable -> 0x0047 }
        r31 = r0.append(r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r31;	 Catch:{ Throwable -> 0x0047 }
        r32 = r0.toString();	 Catch:{ Throwable -> 0x0047 }
        r16 = "WLConnection";	 Catch:{ Throwable -> 0x0047 }
        r0 = r16;	 Catch:{ Throwable -> 0x0047 }
        r1 = r32;	 Catch:{ Throwable -> 0x0047 }
        com.abaltatech.mcs.logger.MCSLogger.log(r0, r1);	 Catch:{ Throwable -> 0x0047 }
        r0 = r34;	 Catch:{ Throwable -> 0x0047 }
        r5 = r0.m_inputDataBuffer;	 Catch:{ Throwable -> 0x0047 }
        r20 = r18 + -8;	 Catch:{ Throwable -> 0x0047 }
        r0 = r20;	 Catch:{ Throwable -> 0x0047 }
        r5.discardBytesFromStart(r0);	 Catch:{ Throwable -> 0x0047 }
        goto L_0x01fc;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.weblink.core.WebLinkConnection.onDataReceived(com.abaltatech.mcs.common.IMCSDataLayer):void");
    }

    public boolean init(IMCSDataLayer $r1) throws  {
        if (!$assertionsDisabled && this.m_dataLayer != null) {
            throw new AssertionError();
        } else if (this.m_dataLayer != null) {
            return false;
        } else {
            this.m_readDataRate = 0;
            this.m_writeDataRate = 0;
            this.m_bytesWritten = 0;
            this.m_bytesReadPrev = 0;
            this.m_bytesWrittenPrev = 0;
            this.m_statsReadTimestamp = 0;
            this.m_statsWriteTimestamp = 0;
            this.m_inputDataBuffer.reset();
            this.m_dataLayer = $r1;
            this.m_connClosed = false;
            this.m_suspended = false;
            this.m_dataLayer.registerNotification(this);
            this.m_cmdQueue.clear();
            this.m_thread = new C03901();
            this.m_thread.start();
            onDataReceived(this.m_dataLayer);
            return true;
        }
    }

    public void terminate() throws  {
        if (this.m_dataLayer != null) {
            Thread $r1 = this.m_thread;
            this.m_suspended = false;
            this.m_connClosed = true;
            this.m_dataLayer.closeConnection();
            if ($r1 != null) {
                try {
                    $r1.join();
                } catch (InterruptedException e) {
                }
            }
        }
    }

    public boolean suspend() throws  {
        boolean $z0 = false;
        synchronized (this) {
            if (!this.m_suspended) {
                this.m_suspended = true;
                $z0 = true;
            }
        }
        return $z0;
    }

    public boolean resume() throws  {
        boolean $z0 = false;
        synchronized (this) {
            if (this.m_suspended) {
                this.m_suspended = false;
                $z0 = true;
            }
        }
        return $z0;
    }

    public boolean isSuspended() throws  {
        return this.m_suspended;
    }

    public void registerHandler(short $s0, ICommandHandler $r1) throws  {
        if ($r1 != null) {
            this.m_handlers.put(Short.valueOf($s0), $r1);
        }
    }

    public void unregisterHandler(ICommandHandler $r1) throws  {
        Iterator $r4 = this.m_handlers.entrySet().iterator();
        while ($r4.hasNext()) {
            if (((Entry) $r4.next()).getValue() == $r1) {
                $r4.remove();
            }
        }
    }

    public boolean canSendCommand() throws  {
        return this.m_cmdQueue.size() < 16 && !isSuspended();
    }

    public boolean hasCommand(int $i0) throws  {
        Iterator $r2 = this.m_cmdQueue.iterator();
        while ($r2.hasNext()) {
            if (((Command) $r2.next()).getCommandID() == $i0) {
                return true;
            }
        }
        return false;
    }

    public boolean sendCommand(Command $r1) throws  {
        if (!$assertionsDisabled && $r1 == null) {
            throw new AssertionError();
        } else if ($r1 == null) {
            return false;
        } else {
            if (isSuspended()) {
                return false;
            }
            try {
                this.m_cmdQueue.put($r1);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
    }

    public int getReceiveDataRate() throws  {
        long $l0 = System.currentTimeMillis();
        if (this.m_statsReadTimestamp == 0) {
            this.m_statsReadTimestamp = $l0;
            this.m_readDataRate = 0;
        } else if ($l0 - this.m_statsReadTimestamp >= 1000) {
            this.m_readDataRate = (int) (((this.m_bytesRead - this.m_bytesReadPrev) * 1000) / ($l0 - this.m_statsReadTimestamp));
            this.m_bytesReadPrev = this.m_bytesRead;
            this.m_statsReadTimestamp = $l0;
        }
        return this.m_readDataRate;
    }

    public int getSendDataRate() throws  {
        long $l0 = System.currentTimeMillis();
        if (this.m_statsWriteTimestamp == 0) {
            this.m_statsWriteTimestamp = $l0;
            this.m_writeDataRate = 0;
        } else if ($l0 - this.m_statsWriteTimestamp >= 1000) {
            this.m_writeDataRate = (int) (((this.m_bytesWritten - this.m_bytesWrittenPrev) * 1000) / ($l0 - this.m_statsWriteTimestamp));
            this.m_bytesWrittenPrev = this.m_bytesWritten;
            this.m_statsWriteTimestamp = $l0;
        }
        return this.m_writeDataRate;
    }

    public void setCommandDetectedListener(OnCommandDetectedListener $r1) throws  {
        this.m_commandDetectedListener = $r1;
    }

    public void onConnectionClosed(IMCSDataLayer connection) throws  {
        this.m_dataLayer.unRegisterNotification(this);
        this.m_connClosed = true;
        this.m_dataLayer = null;
        this.m_cmdQueue.clear();
        this.m_inputDataBuffer.reset();
        if (this.m_thread != null) {
            this.m_thread.interrupt();
            this.m_thread = null;
        }
    }

    protected void onCommandSendingStarted(Command cmd) throws  {
    }

    protected void onCommandSendingCompleted(Command cmd) throws  {
    }

    public boolean isClosed() throws  {
        return this.m_connClosed;
    }
}
