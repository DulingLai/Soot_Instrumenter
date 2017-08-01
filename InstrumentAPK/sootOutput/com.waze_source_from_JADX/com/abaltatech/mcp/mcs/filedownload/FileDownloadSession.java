package com.abaltatech.mcp.mcs.filedownload;

public class FileDownloadSession {
    private static final long c_getRange = 1000000;
    private static final String c_infoExtension = ".info";
    private static final String c_partialExtension = ".partial";
    protected long m_chunkChecksum;
    protected EDownloadStatus m_downloadStatus;
    protected Thread m_downloadThread;
    protected int m_fileHandle;
    protected long m_fileLength;
    protected String m_localFilePath;
    protected IFileDownloadNotification m_notifier;
    protected long m_offset;
    protected String m_proxyAddress = "";
    protected int m_proxyPort = -1;
    protected long m_remoteFileChecksum;
    protected String m_remoteFileURI;

    private class DownloadThread extends Thread {
        public void run() throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:54:0x025b in {8, 9, 12, 16, 17, 22, 26, 35, 37, 42, 50, 53, 55, 59, 62, 69, 73, 77, 78, 80, 84, 85, 87} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r40 = this;
            r4 = new java.io.File;
            r5 = new java.lang.StringBuilder;
            r5.<init>();
            r0 = r40;
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;
            r7 = r6.m_localFilePath;
            r5 = r5.append(r7);
            r8 = ".partial";
            r5 = r5.append(r8);
            r7 = r5.toString();
            r4.<init>(r7);
            r9 = new java.io.File;
            r5 = new java.lang.StringBuilder;
            r5.<init>();
            r0 = r40;
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;
            r7 = r6.m_localFilePath;
            r5 = r5.append(r7);
            r8 = ".info";
            r5 = r5.append(r8);
            r7 = r5.toString();
            r9.<init>(r7);
            r0 = r40;
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;
            r10 = 0;
            r6.m_offset = r10;
            r12 = r9.exists();	 Catch:{ Exception -> 0x022e }
            if (r12 == 0) goto L_0x025f;	 Catch:{ Exception -> 0x022e }
        L_0x004a:
            r13 = new java.io.InputStreamReader;
            r14 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x022e }
            r14.<init>(r9);	 Catch:{ Exception -> 0x022e }
            r13.<init>(r14);	 Catch:{ Exception -> 0x022e }
            r15 = new java.io.BufferedReader;	 Catch:{ Exception -> 0x022e }
            r15.<init>(r13);	 Catch:{ Exception -> 0x022e }
            r7 = r15.readLine();	 Catch:{ Exception -> 0x022e }
            r15.close();	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_remoteFileChecksum;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r18 = java.lang.Long.toString(r0);	 Catch:{ Exception -> 0x022e }
            r0 = r18;	 Catch:{ Exception -> 0x022e }
            r12 = r7.equals(r0);	 Catch:{ Exception -> 0x022e }
            if (r12 != 0) goto L_0x0077;	 Catch:{ Exception -> 0x022e }
        L_0x0074:
            r4.delete();	 Catch:{ Exception -> 0x022e }
        L_0x0077:
            r9.delete();	 Catch:{ Exception -> 0x022e }
        L_0x007a:
            r9.createNewFile();	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_remoteFileChecksum;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r7 = java.lang.Long.toString(r0);	 Catch:{ Exception -> 0x022e }
            r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x022e }
            r5.<init>();	 Catch:{ Exception -> 0x022e }
            r5 = r5.append(r7);	 Catch:{ Exception -> 0x022e }
            r8 = "\n";	 Catch:{ Exception -> 0x022e }
            r5 = r5.append(r8);	 Catch:{ Exception -> 0x022e }
            r7 = r5.toString();	 Catch:{ Exception -> 0x022e }
            r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x022e }
            r5.<init>();	 Catch:{ Exception -> 0x022e }
            r5 = r5.append(r7);	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r7 = r6.m_remoteFileURI;	 Catch:{ Exception -> 0x022e }
            r5 = r5.append(r7);	 Catch:{ Exception -> 0x022e }
            r7 = r5.toString();	 Catch:{ Exception -> 0x022e }
            r19 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x022e }
            r0 = r19;	 Catch:{ Exception -> 0x022e }
            r0.<init>(r9);	 Catch:{ Exception -> 0x022e }
            r20 = new java.io.OutputStreamWriter;	 Catch:{ Exception -> 0x022e }
            r0 = r20;	 Catch:{ Exception -> 0x022e }
            r1 = r19;	 Catch:{ Exception -> 0x022e }
            r0.<init>(r1);	 Catch:{ Exception -> 0x022e }
            r0 = r20;	 Catch:{ Exception -> 0x022e }
            r0.write(r7);	 Catch:{ Exception -> 0x022e }
            r0 = r20;	 Catch:{ Exception -> 0x022e }
            r0.close();	 Catch:{ Exception -> 0x022e }
            r12 = r4.exists();	 Catch:{ Exception -> 0x022e }
            if (r12 != 0) goto L_0x0263;	 Catch:{ Exception -> 0x022e }
        L_0x00d3:
            r4.createNewFile();	 Catch:{ Exception -> 0x022e }
        L_0x00d6:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_offset;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r10 = 0;
            r21 = (r16 > r10 ? 1 : (r16 == r10 ? 0 : -1));
            if (r21 != 0) goto L_0x0274;	 Catch:{ Exception -> 0x022e }
        L_0x00e4:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r22 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.EDownloadStatus.DS_Started;	 Catch:{ Exception -> 0x022e }
            r0 = r22;	 Catch:{ Exception -> 0x022e }
            r6.SetDownloadStatus(r0);	 Catch:{ Exception -> 0x022e }
        L_0x00ef:
            r19 = new java.io.FileOutputStream;	 Catch:{ Exception -> 0x022e }
            r23 = 1;	 Catch:{ Exception -> 0x022e }
            r0 = r19;	 Catch:{ Exception -> 0x022e }
            r1 = r23;	 Catch:{ Exception -> 0x022e }
            r0.<init>(r4, r1);	 Catch:{ Exception -> 0x022e }
            r24 = java.net.Proxy.NO_PROXY;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_proxyPort;	 Catch:{ Exception -> 0x022e }
            r25 = r0;	 Catch:{ Exception -> 0x022e }
            if (r25 <= 0) goto L_0x012a;	 Catch:{ Exception -> 0x022e }
        L_0x0106:
            r24 = new java.net.Proxy;
            r26 = java.net.Proxy.Type.HTTP;
            r27 = new java.net.InetSocketAddress;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r7 = r6.m_proxyAddress;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_proxyPort;	 Catch:{ Exception -> 0x022e }
            r25 = r0;	 Catch:{ Exception -> 0x022e }
            r0 = r27;	 Catch:{ Exception -> 0x022e }
            r1 = r25;	 Catch:{ Exception -> 0x022e }
            r0.<init>(r7, r1);	 Catch:{ Exception -> 0x022e }
            r0 = r24;	 Catch:{ Exception -> 0x022e }
            r1 = r26;	 Catch:{ Exception -> 0x022e }
            r2 = r27;	 Catch:{ Exception -> 0x022e }
            r0.<init>(r1, r2);	 Catch:{ Exception -> 0x022e }
        L_0x012a:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_offset;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_fileLength;	 Catch:{ Exception -> 0x022e }
            r28 = r0;	 Catch:{ Exception -> 0x022e }
            r10 = 1;
            r0 = r28;
            r0 = r0 - r10;
            r28 = r0;
            r21 = (r16 > r28 ? 1 : (r16 == r28 ? 0 : -1));
            if (r21 >= 0) goto L_0x0284;	 Catch:{ Exception -> 0x022e }
        L_0x0145:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r12 = r0.isInterrupted();	 Catch:{ Exception -> 0x022e }
            if (r12 != 0) goto L_0x0284;	 Catch:{ Exception -> 0x022e }
        L_0x014d:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_offset;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r10 = 1000000; // 0xf4240 float:1.401298E-39 double:4.940656E-318;
            r0 = r16;
            r0 = r0 + r10;
            r16 = r0;
            r10 = 1;	 Catch:{ Exception -> 0x022e }
            r0 = r16;	 Catch:{ Exception -> 0x022e }
            r0 = r0 - r10;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_fileLength;	 Catch:{ Exception -> 0x022e }
            r28 = r0;	 Catch:{ Exception -> 0x022e }
            r10 = 1;
            r0 = r28;
            r0 = r0 - r10;
            r28 = r0;
            r21 = (r16 > r28 ? 1 : (r16 == r28 ? 0 : -1));
            if (r21 < 0) goto L_0x0186;	 Catch:{ Exception -> 0x022e }
        L_0x0177:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_fileLength;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r10 = 1;
            r0 = r16;
            r0 = r0 - r10;
            r16 = r0;
        L_0x0186:
            r30 = new java.net.URL;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r7 = r6.m_remoteFileURI;	 Catch:{ Exception -> 0x022e }
            r0 = r30;	 Catch:{ Exception -> 0x022e }
            r0.<init>(r7);	 Catch:{ Exception -> 0x022e }
            r0 = r30;	 Catch:{ Exception -> 0x022e }
            r1 = r24;	 Catch:{ Exception -> 0x022e }
            r31 = r0.openConnection(r1);	 Catch:{ Exception -> 0x022e }
            r33 = r31;	 Catch:{ Exception -> 0x022e }
            r33 = (java.net.HttpURLConnection) r33;	 Catch:{ Exception -> 0x022e }
            r32 = r33;	 Catch:{ Exception -> 0x022e }
            r8 = "GET";	 Catch:{ Exception -> 0x022e }
            r0 = r32;	 Catch:{ Exception -> 0x022e }
            r0.setRequestMethod(r8);	 Catch:{ Exception -> 0x022e }
            r5 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x022e }
            r8 = "bytes=";	 Catch:{ Exception -> 0x022e }
            r5.<init>(r8);	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_offset;	 Catch:{ Exception -> 0x022e }
            r28 = r0;	 Catch:{ Exception -> 0x022e }
            r7 = java.lang.Long.toString(r0);	 Catch:{ Exception -> 0x022e }
            r5.append(r7);	 Catch:{ Exception -> 0x022e }
            r8 = "-";	 Catch:{ Exception -> 0x022e }
            r5.append(r8);	 Catch:{ Exception -> 0x022e }
            r0 = r16;	 Catch:{ Exception -> 0x022e }
            r7 = java.lang.Long.toString(r0);	 Catch:{ Exception -> 0x022e }
            r5.append(r7);	 Catch:{ Exception -> 0x022e }
            r7 = r5.toString();	 Catch:{ Exception -> 0x022e }
            r8 = "Range";	 Catch:{ Exception -> 0x022e }
            r0 = r32;	 Catch:{ Exception -> 0x022e }
            r0.setRequestProperty(r8, r7);	 Catch:{ Exception -> 0x022e }
            r0 = r32;	 Catch:{ Exception -> 0x022e }
            r0.connect();	 Catch:{ Exception -> 0x022e }
            r0 = r32;	 Catch:{ Exception -> 0x022e }
            r25 = r0.getResponseCode();	 Catch:{ Exception -> 0x022e }
            r23 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ Exception -> 0x022e }
            r0 = r25;	 Catch:{ Exception -> 0x022e }
            r1 = r23;	 Catch:{ Exception -> 0x022e }
            if (r0 == r1) goto L_0x01f2;	 Catch:{ Exception -> 0x022e }
        L_0x01ea:
            r23 = 206; // 0xce float:2.89E-43 double:1.02E-321;	 Catch:{ Exception -> 0x022e }
            r0 = r25;	 Catch:{ Exception -> 0x022e }
            r1 = r23;	 Catch:{ Exception -> 0x022e }
            if (r0 != r1) goto L_0x012a;	 Catch:{ Exception -> 0x022e }
        L_0x01f2:
            r0 = r32;	 Catch:{ Exception -> 0x022e }
            r34 = r0.getInputStream();	 Catch:{ Exception -> 0x022e }
            r23 = 8192; // 0x2000 float:1.14794E-41 double:4.0474E-320;	 Catch:{ Exception -> 0x022e }
            r0 = r23;	 Catch:{ Exception -> 0x022e }
            r0 = new byte[r0];	 Catch:{ Exception -> 0x022e }
            r35 = r0;	 Catch:{ Exception -> 0x022e }
        L_0x0200:
            r0 = r34;	 Catch:{ Exception -> 0x022e }
            r1 = r35;	 Catch:{ Exception -> 0x022e }
            r25 = r0.read(r1);	 Catch:{ Exception -> 0x022e }
            if (r25 <= 0) goto L_0x012a;	 Catch:{ Exception -> 0x022e }
        L_0x020a:
            r23 = 0;	 Catch:{ Exception -> 0x022e }
            r0 = r19;	 Catch:{ Exception -> 0x022e }
            r1 = r35;	 Catch:{ Exception -> 0x022e }
            r2 = r23;	 Catch:{ Exception -> 0x022e }
            r3 = r25;	 Catch:{ Exception -> 0x022e }
            r0.write(r1, r2, r3);	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_offset;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r0 = r25;
            r0 = (long) r0;
            r28 = r0;
            r0 = r16;	 Catch:{ Exception -> 0x022e }
            r2 = r28;	 Catch:{ Exception -> 0x022e }
            r0 = r0 + r2;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r6.m_offset = r0;	 Catch:{ Exception -> 0x022e }
            goto L_0x0200;
        L_0x022e:
            r36 = move-exception;
            r0 = r40;
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;
            r22 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.EDownloadStatus.DS_Failed;
            r0 = r22;
            r6.SetDownloadStatus(r0);
            r37 = java.lang.System.out;
            r5 = new java.lang.StringBuilder;
            r5.<init>();
            r8 = "Download failed: ";
            r5 = r5.append(r8);
            r0 = r36;
            r7 = r0.toString();
            r5 = r5.append(r7);
            r7 = r5.toString();
            r0 = r37;
            r0.println(r7);
            return;
            goto L_0x025f;
        L_0x025c:
            goto L_0x007a;
        L_0x025f:
            r4.delete();	 Catch:{ Exception -> 0x022e }
            goto L_0x025c;	 Catch:{ Exception -> 0x022e }
        L_0x0263:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r16 = r4.length();	 Catch:{ Exception -> 0x022e }
            goto L_0x026f;	 Catch:{ Exception -> 0x022e }
        L_0x026c:
            goto L_0x00d6;	 Catch:{ Exception -> 0x022e }
        L_0x026f:
            r0 = r16;	 Catch:{ Exception -> 0x022e }
            r6.m_offset = r0;	 Catch:{ Exception -> 0x022e }
            goto L_0x026c;	 Catch:{ Exception -> 0x022e }
        L_0x0274:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r22 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.EDownloadStatus.DS_Resumed;	 Catch:{ Exception -> 0x022e }
            goto L_0x027e;	 Catch:{ Exception -> 0x022e }
        L_0x027b:
            goto L_0x00ef;	 Catch:{ Exception -> 0x022e }
        L_0x027e:
            r0 = r22;	 Catch:{ Exception -> 0x022e }
            r6.SetDownloadStatus(r0);	 Catch:{ Exception -> 0x022e }
            goto L_0x027b;
        L_0x0284:
            r0 = r19;	 Catch:{ Exception -> 0x022e }
            r0.close();	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_offset;	 Catch:{ Exception -> 0x022e }
            r16 = r0;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_fileLength;	 Catch:{ Exception -> 0x022e }
            r28 = r0;	 Catch:{ Exception -> 0x022e }
            r21 = (r16 > r28 ? 1 : (r16 == r28 ? 0 : -1));
            if (r21 < 0) goto L_0x031f;	 Catch:{ Exception -> 0x022e }
        L_0x029d:
            r38 = new java.util.zip.CRC32;	 Catch:{ Exception -> 0x022e }
            r0 = r38;	 Catch:{ Exception -> 0x022e }
            r0.<init>();	 Catch:{ Exception -> 0x022e }
            r14 = new java.io.FileInputStream;	 Catch:{ Exception -> 0x022e }
            r14.<init>(r4);	 Catch:{ Exception -> 0x022e }
            r23 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ Exception -> 0x022e }
            r0 = r23;	 Catch:{ Exception -> 0x022e }
            r0 = new byte[r0];	 Catch:{ Exception -> 0x022e }
            r35 = r0;	 Catch:{ Exception -> 0x022e }
        L_0x02b1:
            r0 = r35;	 Catch:{ Exception -> 0x022e }
            r25 = r14.read(r0);	 Catch:{ Exception -> 0x022e }
            r23 = -1;	 Catch:{ Exception -> 0x022e }
            r0 = r25;	 Catch:{ Exception -> 0x022e }
            r1 = r23;	 Catch:{ Exception -> 0x022e }
            if (r0 == r1) goto L_0x02cd;	 Catch:{ Exception -> 0x022e }
        L_0x02bf:
            r23 = 0;	 Catch:{ Exception -> 0x022e }
            r0 = r38;	 Catch:{ Exception -> 0x022e }
            r1 = r35;	 Catch:{ Exception -> 0x022e }
            r2 = r23;	 Catch:{ Exception -> 0x022e }
            r3 = r25;	 Catch:{ Exception -> 0x022e }
            r0.update(r1, r2, r3);	 Catch:{ Exception -> 0x022e }
            goto L_0x02b1;	 Catch:{ Exception -> 0x022e }
        L_0x02cd:
            r14.close();	 Catch:{ Exception -> 0x022e }
            r0 = r38;	 Catch:{ Exception -> 0x022e }
            r16 = r0.getValue();	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r0 = r6.m_remoteFileChecksum;	 Catch:{ Exception -> 0x022e }
            r28 = r0;	 Catch:{ Exception -> 0x022e }
            r21 = (r16 > r28 ? 1 : (r16 == r28 ? 0 : -1));
            if (r21 == 0) goto L_0x02f4;	 Catch:{ Exception -> 0x022e }
        L_0x02e2:
            r4.delete();	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r22 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.EDownloadStatus.DS_Failed_RemoteFileChanged;	 Catch:{ Exception -> 0x022e }
            r0 = r22;	 Catch:{ Exception -> 0x022e }
            r6.SetDownloadStatus(r0);	 Catch:{ Exception -> 0x022e }
        L_0x02f0:
            r9.delete();	 Catch:{ Exception -> 0x022e }
            return;
        L_0x02f4:
            r39 = new java.io.File;	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r7 = r6.m_localFilePath;	 Catch:{ Exception -> 0x022e }
            r0 = r39;	 Catch:{ Exception -> 0x022e }
            r0.<init>(r7);	 Catch:{ Exception -> 0x022e }
            r0 = r39;	 Catch:{ Exception -> 0x022e }
            r12 = r0.exists();	 Catch:{ Exception -> 0x022e }
            if (r12 == 0) goto L_0x030e;	 Catch:{ Exception -> 0x022e }
        L_0x0309:
            r0 = r39;	 Catch:{ Exception -> 0x022e }
            r0.delete();	 Catch:{ Exception -> 0x022e }
        L_0x030e:
            r0 = r39;	 Catch:{ Exception -> 0x022e }
            r4.renameTo(r0);	 Catch:{ Exception -> 0x022e }
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r22 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.EDownloadStatus.DS_Completed;	 Catch:{ Exception -> 0x022e }
            r0 = r22;	 Catch:{ Exception -> 0x022e }
            r6.SetDownloadStatus(r0);	 Catch:{ Exception -> 0x022e }
            goto L_0x02f0;	 Catch:{ Exception -> 0x022e }
        L_0x031f:
            r0 = r40;	 Catch:{ Exception -> 0x022e }
            r6 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.this;	 Catch:{ Exception -> 0x022e }
            r22 = com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.EDownloadStatus.DS_Paused;	 Catch:{ Exception -> 0x022e }
            r0 = r22;	 Catch:{ Exception -> 0x022e }
            r6.SetDownloadStatus(r0);	 Catch:{ Exception -> 0x022e }
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.filedownload.FileDownloadSession.DownloadThread.run():void");
        }

        DownloadThread() throws  {
        }
    }

    public enum EDownloadStatus {
        DS_Waiting,
        DS_Started,
        DS_Completed,
        DS_Paused,
        DS_Resumed,
        DS_Failed,
        DS_Failed_RemoteFileChanged,
        DS_Failed_RemoteFileRemoved
    }

    public FileDownloadSession(String $r1, String $r2, long $l0, long $l1) throws  {
        this.m_remoteFileURI = $r1;
        this.m_localFilePath = $r2;
        this.m_remoteFileChecksum = $l0;
        this.m_fileLength = $l1;
        this.m_proxyAddress = "";
        this.m_proxyPort = -1;
    }

    public int getProxyPort() throws  {
        return this.m_proxyPort;
    }

    public void setProxyPort(int $i0) throws  {
        this.m_proxyPort = $i0;
    }

    public String getProxyAddress() throws  {
        return this.m_proxyAddress;
    }

    public void setProxyAddress(String $r1) throws  {
        this.m_proxyAddress = $r1;
    }

    public String getRemoteFileURI() throws  {
        return this.m_remoteFileURI;
    }

    public void setRemoteFileURI(String $r1) throws  {
        this.m_remoteFileURI = $r1;
    }

    public String getLocalFilePath() throws  {
        return this.m_localFilePath;
    }

    public void setLocalFilePath(String $r1) throws  {
        this.m_localFilePath = $r1;
    }

    public long getRemoteFileChecksum() throws  {
        return this.m_remoteFileChecksum;
    }

    public void setRemoteFileChecksum(long $l0) throws  {
        this.m_remoteFileChecksum = $l0;
    }

    public EDownloadStatus getDownloadStatus() throws  {
        return this.m_downloadStatus;
    }

    public int getDownloadProgress() throws  {
        if (this.m_fileLength > 0) {
            return (int) (((double) ((int) (((double) this.m_offset) / ((double) this.m_fileLength)))) * 100.0d);
        }
        return 0;
    }

    protected void SetDownloadStatus(EDownloadStatus $r1) throws  {
        this.m_downloadStatus = $r1;
        switch (this.m_downloadStatus) {
            case DS_Started:
                OnFileDownloadStarted(this.m_remoteFileURI, this.m_localFilePath);
                return;
            case DS_Paused:
                OnFileDownloadPaused(this.m_remoteFileURI, this.m_localFilePath);
                return;
            case DS_Resumed:
                OnFileDownloadResumed(this.m_remoteFileURI, this.m_localFilePath);
                return;
            case DS_Failed:
            case DS_Failed_RemoteFileChanged:
            case DS_Failed_RemoteFileRemoved:
                OnFileDownloadFailed(this.m_remoteFileURI, this.m_localFilePath);
                return;
            case DS_Completed:
                OnFileDownloadCompleted(this.m_remoteFileURI, this.m_localFilePath);
                return;
            default:
                return;
        }
    }

    public void setDownloadNotifier(IFileDownloadNotification $r1) throws  {
        this.m_notifier = $r1;
    }

    protected void OnFileDownloadStarted(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadStarted($r1, $r2);
        }
    }

    protected void OnFileDownloadCompleted(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadCompleted($r1, $r2);
        }
    }

    protected void OnFileDownloadPaused(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadPaused($r1, $r2);
        }
    }

    protected void OnFileDownloadResumed(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadResumed($r1, $r2);
        }
    }

    protected void OnFileDownloadFailed(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadFailed($r1, $r2);
        }
    }

    public void startDownload() throws  {
        this.m_downloadThread = new DownloadThread();
        this.m_downloadThread.start();
    }

    public void stopDownload() throws  {
        if (this.m_downloadThread != null && this.m_downloadThread.isAlive()) {
            this.m_downloadThread.interrupt();
            try {
                this.m_downloadThread.join();
            } catch (InterruptedException e) {
            }
            this.m_downloadThread = null;
        }
    }
}
