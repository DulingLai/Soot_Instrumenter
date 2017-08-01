package com.abaltatech.mcp.mcs.filedownload;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FileDownloadClient implements IFileDownloadNotification {
    private static final String s_propfindXML = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><propfind xmlns=\"DAV:\"><prop><displayname/><getcontentlength/><getcontenttype/><getchecksum/><getlastmodified/></prop></propfind>";
    ArrayList<FileDownloadSession> m_activeSessions = new ArrayList();
    IFileDownloadNotification m_notifier;
    String m_proxyAddress = "";
    int m_proxyPort = -1;
    ArrayList<FileResource> m_remoteFiles = new ArrayList();
    String m_serverURL;

    class C03121 implements FilenameFilter {
        C03121() throws  {
        }

        public boolean accept(File dir, String $r2) throws  {
            return $r2.endsWith(".info");
        }
    }

    protected void retrieveRemoteFiles() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:37:0x00db
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r51 = this;
        r7 = java.net.Proxy.NO_PROXY;
        r0 = r51;	 Catch:{ Exception -> 0x0128 }
        r8 = r0.m_proxyPort;	 Catch:{ Exception -> 0x0128 }
        if (r8 <= 0) goto L_0x001c;
    L_0x0008:
        r7 = new java.net.Proxy;
        r9 = java.net.Proxy.Type.HTTP;
        r10 = new java.net.InetSocketAddress;	 Catch:{ Exception -> 0x0128 }
        r0 = r51;	 Catch:{ Exception -> 0x0128 }
        r11 = r0.m_proxyAddress;	 Catch:{ Exception -> 0x0128 }
        r0 = r51;	 Catch:{ Exception -> 0x0128 }
        r8 = r0.m_proxyPort;	 Catch:{ Exception -> 0x0128 }
        r10.<init>(r11, r8);	 Catch:{ Exception -> 0x0128 }
        r7.<init>(r9, r10);	 Catch:{ Exception -> 0x0128 }
    L_0x001c:
        r12 = new java.net.URL;	 Catch:{ Exception -> 0x0128 }
        r0 = r51;	 Catch:{ Exception -> 0x0128 }
        r11 = r0.m_serverURL;	 Catch:{ Exception -> 0x0128 }
        r12.<init>(r11);	 Catch:{ Exception -> 0x0128 }
        r13 = r12.openConnection(r7);	 Catch:{ Exception -> 0x0128 }
        r15 = r13;	 Catch:{ Exception -> 0x0128 }
        r15 = (java.net.HttpURLConnection) r15;	 Catch:{ Exception -> 0x0128 }
        r14 = r15;	 Catch:{ Exception -> 0x0128 }
        r16 = "PROPFIND";	 Catch:{ ProtocolException -> 0x00df }
        r0 = r16;	 Catch:{ ProtocolException -> 0x00df }
        r14.setRequestMethod(r0);	 Catch:{ ProtocolException -> 0x00df }
    L_0x0034:
        r16 = "Depth";	 Catch:{ Exception -> 0x0128 }
        r17 = "1";	 Catch:{ Exception -> 0x0128 }
        r0 = r16;	 Catch:{ Exception -> 0x0128 }
        r1 = r17;	 Catch:{ Exception -> 0x0128 }
        r14.setRequestProperty(r0, r1);	 Catch:{ Exception -> 0x0128 }
        r16 = "Content-Type";	 Catch:{ Exception -> 0x0128 }
        r17 = "text/xml";	 Catch:{ Exception -> 0x0128 }
        r0 = r16;	 Catch:{ Exception -> 0x0128 }
        r1 = r17;	 Catch:{ Exception -> 0x0128 }
        r14.setRequestProperty(r0, r1);	 Catch:{ Exception -> 0x0128 }
        r8 = r14.getResponseCode();	 Catch:{ Exception -> 0x0128 }
        r18 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r18;
        if (r8 != r0) goto L_0x015f;
    L_0x0055:
        r19 = 1;
    L_0x0057:
        r18 = 207; // 0xcf float:2.9E-43 double:1.023E-321;
        r0 = r18;
        if (r8 != r0) goto L_0x0162;
    L_0x005d:
        r20 = 1;
    L_0x005f:
        r19 = r20 | r19;
        if (r19 == 0) goto L_0x01ec;	 Catch:{ Exception -> 0x0128 }
    L_0x0063:
        r21 = javax.xml.parsers.DocumentBuilderFactory.newInstance();	 Catch:{ Exception -> 0x0128 }
        r0 = r21;	 Catch:{ Exception -> 0x0128 }
        r22 = r0.newDocumentBuilder();	 Catch:{ Exception -> 0x0128 }
        r23 = r14.getInputStream();	 Catch:{ Exception -> 0x0128 }
        r0 = r22;	 Catch:{ Exception -> 0x0128 }
        r1 = r23;	 Catch:{ Exception -> 0x0128 }
        r24 = r0.parse(r1);	 Catch:{ Exception -> 0x0128 }
        r0 = r24;	 Catch:{ Exception -> 0x0128 }
        r25 = r0.getDocumentElement();	 Catch:{ Exception -> 0x0128 }
        r0 = r25;	 Catch:{ Exception -> 0x0128 }
        r26 = r0.getChildNodes();	 Catch:{ Exception -> 0x0128 }
        r8 = 0;	 Catch:{ Exception -> 0x0128 }
    L_0x0086:
        r0 = r26;	 Catch:{ Exception -> 0x0128 }
        r27 = r0.getLength();	 Catch:{ Exception -> 0x0128 }
        r0 = r27;	 Catch:{ Exception -> 0x0128 }
        if (r8 >= r0) goto L_0x01ed;	 Catch:{ Exception -> 0x0128 }
    L_0x0090:
        r0 = r26;	 Catch:{ Exception -> 0x0128 }
        r28 = r0.item(r8);	 Catch:{ Exception -> 0x0128 }
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r29 = r0.getChildNodes();	 Catch:{ Exception -> 0x0128 }
        r11 = "";
        r30 = "";
        r31 = "";
        goto L_0x00a6;
    L_0x00a3:
        goto L_0x0034;
    L_0x00a6:
        r27 = 0;	 Catch:{ Exception -> 0x0128 }
    L_0x00a8:
        r0 = r29;	 Catch:{ Exception -> 0x0128 }
        r32 = r0.getLength();	 Catch:{ Exception -> 0x0128 }
        r0 = r27;	 Catch:{ Exception -> 0x0128 }
        r1 = r32;	 Catch:{ Exception -> 0x0128 }
        if (r0 >= r1) goto L_0x01c1;	 Catch:{ Exception -> 0x0128 }
    L_0x00b4:
        r0 = r29;	 Catch:{ Exception -> 0x0128 }
        r1 = r27;	 Catch:{ Exception -> 0x0128 }
        r28 = r0.item(r1);	 Catch:{ Exception -> 0x0128 }
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r33 = r0.getNodeName();	 Catch:{ Exception -> 0x0128 }
        r16 = "D:href";	 Catch:{ Exception -> 0x0128 }
        r0 = r33;	 Catch:{ Exception -> 0x0128 }
        r1 = r16;	 Catch:{ Exception -> 0x0128 }
        r19 = r0.equals(r1);	 Catch:{ Exception -> 0x0128 }
        if (r19 == 0) goto L_0x0165;	 Catch:{ Exception -> 0x0128 }
    L_0x00ce:
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r11 = r0.getTextContent();	 Catch:{ Exception -> 0x0128 }
    L_0x00d4:
        r27 = r27 + 1;
        goto L_0x00da;
    L_0x00d7:
        goto L_0x0057;
    L_0x00da:
        goto L_0x00a8;
        goto L_0x00df;
    L_0x00dc:
        goto L_0x005f;
    L_0x00df:
        r34 = move-exception;
        r35 = r14.getClass();	 Catch:{ Exception -> 0x0115 }
        r0 = r35;	 Catch:{ Exception -> 0x0115 }
        r35 = r0.getSuperclass();	 Catch:{ Exception -> 0x0115 }
        r36 = javax.net.ssl.HttpsURLConnection.class;	 Catch:{ Exception -> 0x0115 }
        r0 = r35;	 Catch:{ Exception -> 0x0115 }
        r1 = r36;	 Catch:{ Exception -> 0x0115 }
        if (r0 != r1) goto L_0x0154;	 Catch:{ Exception -> 0x0115 }
    L_0x00f2:
        r0 = r35;	 Catch:{ Exception -> 0x0115 }
        r35 = r0.getSuperclass();	 Catch:{ Exception -> 0x0115 }
        r16 = "method";	 Catch:{ Exception -> 0x0115 }
        r0 = r35;	 Catch:{ Exception -> 0x0115 }
        r1 = r16;	 Catch:{ Exception -> 0x0115 }
        r37 = r0.getDeclaredField(r1);	 Catch:{ Exception -> 0x0115 }
    L_0x0102:
        r18 = 1;	 Catch:{ Exception -> 0x0115 }
        r0 = r37;	 Catch:{ Exception -> 0x0115 }
        r1 = r18;	 Catch:{ Exception -> 0x0115 }
        r0.setAccessible(r1);	 Catch:{ Exception -> 0x0115 }
        r16 = "PROPFIND";	 Catch:{ Exception -> 0x0115 }
        r0 = r37;	 Catch:{ Exception -> 0x0115 }
        r1 = r16;	 Catch:{ Exception -> 0x0115 }
        r0.set(r14, r1);	 Catch:{ Exception -> 0x0115 }
        goto L_0x00a3;
    L_0x0115:
        r38 = move-exception;
        r39 = new java.lang.RuntimeException;
        goto L_0x0120;
    L_0x0119:
        goto L_0x00d7;
        goto L_0x0120;
    L_0x011d:
        goto L_0x00dc;
    L_0x0120:
        r0 = r39;	 Catch:{ Exception -> 0x0128 }
        r1 = r38;	 Catch:{ Exception -> 0x0128 }
        r0.<init>(r1);	 Catch:{ Exception -> 0x0128 }
        throw r39;	 Catch:{ Exception -> 0x0128 }
    L_0x0128:
        r40 = move-exception;
        r41 = java.lang.System.out;
        r42 = new java.lang.StringBuilder;
        r0 = r42;
        r0.<init>();
        r16 = "Failed downloading list: ";
        r0 = r42;
        r1 = r16;
        r42 = r0.append(r1);
        r0 = r40;
        r11 = r0.toString();
        r0 = r42;
        r42 = r0.append(r11);
        r0 = r42;
        r11 = r0.toString();
        r0 = r41;
        r0.print(r11);
        return;
    L_0x0154:
        r16 = "method";	 Catch:{ Exception -> 0x0115 }
        r0 = r35;	 Catch:{ Exception -> 0x0115 }
        r1 = r16;	 Catch:{ Exception -> 0x0115 }
        r37 = r0.getDeclaredField(r1);	 Catch:{ Exception -> 0x0115 }
        goto L_0x0102;
    L_0x015f:
        r19 = 0;
        goto L_0x0119;
    L_0x0162:
        r20 = 0;
        goto L_0x011d;
    L_0x0165:
        r16 = "D:propstat";	 Catch:{ Exception -> 0x0128 }
        r0 = r33;	 Catch:{ Exception -> 0x0128 }
        r1 = r16;	 Catch:{ Exception -> 0x0128 }
        r19 = r0.equals(r1);	 Catch:{ Exception -> 0x0128 }
        if (r19 == 0) goto L_0x00d4;	 Catch:{ Exception -> 0x0128 }
    L_0x0171:
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r28 = r0.getFirstChild();	 Catch:{ Exception -> 0x0128 }
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r43 = r0.getChildNodes();	 Catch:{ Exception -> 0x0128 }
        r32 = 0;	 Catch:{ Exception -> 0x0128 }
    L_0x017f:
        r0 = r43;	 Catch:{ Exception -> 0x0128 }
        r44 = r0.getLength();	 Catch:{ Exception -> 0x0128 }
        r0 = r32;	 Catch:{ Exception -> 0x0128 }
        r1 = r44;	 Catch:{ Exception -> 0x0128 }
        if (r0 >= r1) goto L_0x00d4;	 Catch:{ Exception -> 0x0128 }
    L_0x018b:
        r0 = r43;	 Catch:{ Exception -> 0x0128 }
        r1 = r32;	 Catch:{ Exception -> 0x0128 }
        r28 = r0.item(r1);	 Catch:{ Exception -> 0x0128 }
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r33 = r0.getNodeName();	 Catch:{ Exception -> 0x0128 }
        r16 = "D:checksum";	 Catch:{ Exception -> 0x0128 }
        r0 = r33;	 Catch:{ Exception -> 0x0128 }
        r1 = r16;	 Catch:{ Exception -> 0x0128 }
        r19 = r0.equals(r1);	 Catch:{ Exception -> 0x0128 }
        if (r19 == 0) goto L_0x01ae;	 Catch:{ Exception -> 0x0128 }
    L_0x01a5:
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r30 = r0.getTextContent();	 Catch:{ Exception -> 0x0128 }
    L_0x01ab:
        r32 = r32 + 1;
        goto L_0x017f;	 Catch:{ Exception -> 0x0128 }
    L_0x01ae:
        r16 = "D:getcontentlength";	 Catch:{ Exception -> 0x0128 }
        r0 = r33;	 Catch:{ Exception -> 0x0128 }
        r1 = r16;	 Catch:{ Exception -> 0x0128 }
        r19 = r0.equals(r1);	 Catch:{ Exception -> 0x0128 }
        if (r19 == 0) goto L_0x01ab;	 Catch:{ Exception -> 0x0128 }
    L_0x01ba:
        r0 = r28;	 Catch:{ Exception -> 0x0128 }
        r31 = r0.getTextContent();	 Catch:{ Exception -> 0x0128 }
        goto L_0x01ab;
    L_0x01c1:
        r45 = new com.abaltatech.mcp.mcs.filedownload.FileResource;	 Catch:{ Exception -> 0x0128 }
        r0 = r31;	 Catch:{ Exception -> 0x0128 }
        r46 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x0128 }
        r0 = r30;	 Catch:{ Exception -> 0x0128 }
        r48 = java.lang.Long.parseLong(r0);	 Catch:{ Exception -> 0x0128 }
        r0 = r45;	 Catch:{ Exception -> 0x0128 }
        r1 = r11;	 Catch:{ Exception -> 0x0128 }
        r2 = r11;	 Catch:{ Exception -> 0x0128 }
        r3 = r46;	 Catch:{ Exception -> 0x0128 }
        r5 = r48;	 Catch:{ Exception -> 0x0128 }
        r0.<init>(r1, r2, r3, r5);	 Catch:{ Exception -> 0x0128 }
        r0 = r51;	 Catch:{ Exception -> 0x0128 }
        r0 = r0.m_remoteFiles;	 Catch:{ Exception -> 0x0128 }
        r50 = r0;	 Catch:{ Exception -> 0x0128 }
        r1 = r45;	 Catch:{ Exception -> 0x0128 }
        r0.add(r1);	 Catch:{ Exception -> 0x0128 }
        goto L_0x01e9;	 Catch:{ Exception -> 0x0128 }
    L_0x01e6:
        goto L_0x0086;	 Catch:{ Exception -> 0x0128 }
    L_0x01e9:
        r8 = r8 + 1;
        goto L_0x01e6;
    L_0x01ec:
        return;
    L_0x01ed:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.filedownload.FileDownloadClient.retrieveRemoteFiles():void");
    }

    public void init(String $r1) throws  {
        this.m_serverURL = $r1;
    }

    public void terminate() throws  {
    }

    public void setProxyAddress(String $r1) throws  {
        this.m_proxyAddress = $r1;
    }

    public void setProxyPort(int $i0) throws  {
        this.m_proxyPort = $i0;
    }

    public void requestFileDownload(FileResource $r1, String $r2) throws  {
        if ($r1 != null) {
            FileDownloadSession fileDownloadSession = new FileDownloadSession(this.m_serverURL + "/" + $r1.getRemoteName(), $r2, $r1.getLastChecksum(), $r1.getContentLength());
            if (this.m_proxyPort > -1) {
                String $r22 = this.m_proxyAddress;
                fileDownloadSession.setProxyAddress($r22);
                fileDownloadSession.setProxyPort(this.m_proxyPort);
            }
            fileDownloadSession.setDownloadNotifier(this);
            ArrayList $r6 = this.m_activeSessions;
            $r6.add(fileDownloadSession);
            fileDownloadSession.startDownload();
        }
    }

    public void cancelFileDownload(FileDownloadSession $r1) throws  {
        if ($r1 != null) {
            $r1.stopDownload();
            this.m_activeSessions.remove($r1);
        }
    }

    public void cancelAddDownloads() throws  {
        Iterator $r2 = this.m_activeSessions.iterator();
        while ($r2.hasNext()) {
            ((FileDownloadSession) $r2.next()).stopDownload();
        }
        this.m_activeSessions.clear();
    }

    public void requestFileDownloadByName(String $r1, String $r2) throws  {
        Iterator $r4 = this.m_remoteFiles.iterator();
        while ($r4.hasNext()) {
            FileResource $r6 = (FileResource) $r4.next();
            if ($r1.equals($r6.getRemoteName())) {
                requestFileDownload($r6, $r2);
                return;
            }
        }
    }

    public List<String> getRemoteFiles() throws  {
        retrieveRemoteFiles();
        ArrayList $r1 = new ArrayList();
        Iterator $r3 = this.m_remoteFiles.iterator();
        while ($r3.hasNext()) {
            $r1.add(((FileResource) $r3.next()).getRemoteName());
        }
        return $r1;
    }

    public List<FileDownloadSession> getActiveDownloads() throws  {
        ArrayList $r1 = new ArrayList();
        $r1.addAll(this.m_activeSessions);
        return $r1;
    }

    public void ResumeDownloads(String $r1) throws  {
        for (File $r4 : new File($r1).listFiles(new C03121())) {
            try {
                BufferedReader $r2 = new BufferedReader(new InputStreamReader(new FileInputStream($r4)));
                $r2.readLine();
                $r1 = $r2.readLine();
                $r2.close();
                String $r9 = $r4.getPath();
                requestFileDownloadByName($r1, $r9.substring(0, $r9.lastIndexOf(".info")));
            } catch (IOException $r3) {
                $r3.printStackTrace();
            }
        }
    }

    public void setDownloadNotifier(IFileDownloadNotification $r1) throws  {
        this.m_notifier = $r1;
    }

    public void OnFileDownloadStarted(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadStarted($r1, $r2);
        }
    }

    public void OnFileDownloadCompleted(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadCompleted($r1, $r2);
        }
    }

    public void OnFileDownloadPaused(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadPaused($r1, $r2);
        }
    }

    public void OnFileDownloadResumed(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadResumed($r1, $r2);
        }
    }

    public void OnFileDownloadFailed(String $r1, String $r2) throws  {
        if (this.m_notifier != null) {
            this.m_notifier.OnFileDownloadFailed($r1, $r2);
        }
    }
}
