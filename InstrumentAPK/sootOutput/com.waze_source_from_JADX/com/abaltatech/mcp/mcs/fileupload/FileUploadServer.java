package com.abaltatech.mcp.mcs.fileupload;

import com.abaltatech.mcp.mcs.http.HttpUtils;
import com.abaltatech.mcp.mcs.http.IHttpLayer;
import com.abaltatech.mcp.mcs.http.IRequestHandler;
import com.abaltatech.mcp.mcs.http.Request;
import com.abaltatech.mcp.mcs.http.Response;
import com.waze.strings.DisplayStrings;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class FileUploadServer implements IRequestHandler {
    private static final String CHECKSUM = "Checksum";
    private static final String CONTENT_LENGTH = "Content-Length";
    private static final String FILE_OFFSET = "File-Offset";
    private static final String FILE_SIZE = "File-Size";
    private ConcurrentHashMap<String, FileUploadSession> m_connDataMap = new ConcurrentHashMap();
    private String m_dataDirectory;
    private IHttpLayer m_httpLayer = null;
    private List<IFileUploadNotification> m_notificationList;
    private String m_serverPath;

    public Response getAdditionalResponseData(int connectionID) throws  {
        return null;
    }

    protected com.abaltatech.mcp.mcs.http.Response handlePostRequest(com.abaltatech.mcp.mcs.http.Request r34, int r35) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:43:0x016b in {8, 9, 11, 16, 23, 26, 28, 31, 42, 44, 50, 52, 54, 55} preds:[]
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
        r33 = this;
        r0 = r34;	 Catch:{ Exception -> 0x0034 }
        r6 = r0.Url;	 Catch:{ Exception -> 0x0034 }
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r7 = r0.m_serverPath;	 Catch:{ Exception -> 0x0034 }
        r8 = r6.startsWith(r7);	 Catch:{ Exception -> 0x0034 }
        if (r8 == 0) goto L_0x0193;	 Catch:{ Exception -> 0x0034 }
    L_0x000e:
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r7 = r0.m_serverPath;	 Catch:{ Exception -> 0x0034 }
        r35 = r7.length();	 Catch:{ Exception -> 0x0034 }
        r0 = r35;	 Catch:{ Exception -> 0x0034 }
        r6 = r6.substring(r0);	 Catch:{ Exception -> 0x0034 }
        r9 = "/";	 Catch:{ Exception -> 0x0034 }
        r8 = r6.contains(r9);	 Catch:{ Exception -> 0x0034 }
        if (r8 == 0) goto L_0x006d;	 Catch:{ Exception -> 0x0034 }
    L_0x0024:
        r9 = "\\";	 Catch:{ Exception -> 0x0034 }
        r8 = r6.contains(r9);	 Catch:{ Exception -> 0x0034 }
        if (r8 != 0) goto L_0x006d;	 Catch:{ Exception -> 0x0034 }
    L_0x002c:
        r10 = new java.security.InvalidParameterException;	 Catch:{ Exception -> 0x0034 }
        r9 = "File name is invalid!";	 Catch:{ Exception -> 0x0034 }
        r10.<init>(r9);	 Catch:{ Exception -> 0x0034 }
        throw r10;	 Catch:{ Exception -> 0x0034 }
    L_0x0034:
        r11 = move-exception;
    L_0x0035:
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r9 = "<h1>400 - BadRequest</h1><p>";
        r12 = r12.append(r9);
        r6 = r11.getMessage();
        r12 = r12.append(r6);
        r9 = "</p>";
        r12 = r12.append(r9);
        r6 = r12.toString();
        r13 = new com.abaltatech.mcp.mcs.http.Response;
        r14 = r6.getBytes();
        r9 = "Bad Request";
        r15 = 400; // 0x190 float:5.6E-43 double:1.976E-321;
        r16 = "text/html";
        r17 = 1;
        r0 = r13;
        r1 = r9;
        r2 = r15;
        r3 = r16;
        r4 = r14;
        r5 = r17;
        r0.<init>(r1, r2, r3, r4, r5);
        return r13;
    L_0x006d:
        r15 = 0;	 Catch:{ Exception -> 0x0034 }
        r0 = new java.lang.String[r15];	 Catch:{ Exception -> 0x0034 }
        r18 = r0;	 Catch:{ Exception -> 0x0034 }
        java.nio.file.Paths.get(r6, r0);	 Catch:{ Exception -> 0x0034 }
        r9 = "File-Offset";	 Catch:{ Exception -> 0x0034 }
        r0 = r34;	 Catch:{ Exception -> 0x0034 }
        r7 = r0.getRequestParamByName(r9);	 Catch:{ Exception -> 0x0034 }
        r19 = java.lang.Long.parseLong(r7);	 Catch:{ Exception -> 0x0034 }
        r9 = "File-Size";	 Catch:{ Exception -> 0x0034 }
        r0 = r34;	 Catch:{ Exception -> 0x0034 }
        r7 = r0.getRequestParamByName(r9);	 Catch:{ Exception -> 0x0034 }
        r21 = java.lang.Long.parseLong(r7);	 Catch:{ Exception -> 0x0034 }
        r9 = "Content-Length";	 Catch:{ Exception -> 0x0034 }
        r0 = r34;	 Catch:{ Exception -> 0x0034 }
        r7 = r0.getRequestParamByName(r9);	 Catch:{ Exception -> 0x0034 }
        r23 = java.lang.Long.parseLong(r7);	 Catch:{ Exception -> 0x0034 }
        r9 = "Checksum";	 Catch:{ Exception -> 0x0034 }
        r0 = r34;	 Catch:{ Exception -> 0x0034 }
        r7 = r0.getRequestParamByName(r9);	 Catch:{ Exception -> 0x0034 }
        if (r7 != 0) goto L_0x00ab;	 Catch:{ Exception -> 0x0034 }
    L_0x00a3:
        r10 = new java.security.InvalidParameterException;	 Catch:{ Exception -> 0x0034 }
        r9 = "Checksum header is missing!";	 Catch:{ Exception -> 0x0034 }
        r10.<init>(r9);	 Catch:{ Exception -> 0x0034 }
        throw r10;	 Catch:{ Exception -> 0x0034 }
    L_0x00ab:
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r25 = r0.findSession(r6);	 Catch:{ Exception -> 0x0034 }
        if (r25 != 0) goto L_0x0119;	 Catch:{ Exception -> 0x0034 }
    L_0x00b3:
        r27 = 0;
        r26 = (r19 > r27 ? 1 : (r19 == r27 ? 0 : -1));
        if (r26 == 0) goto L_0x00c1;
    L_0x00b9:
        r10 = new java.security.InvalidParameterException;	 Catch:{ Exception -> 0x0034 }
        r9 = "Offset needs to be 0 for new sessions!";	 Catch:{ Exception -> 0x0034 }
        r10.<init>(r9);	 Catch:{ Exception -> 0x0034 }
        throw r10;	 Catch:{ Exception -> 0x0034 }
    L_0x00c1:
        r25 = new com.abaltatech.mcp.mcs.fileupload.FileUploadSession;	 Catch:{ Exception -> 0x0034 }
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r0 = r0.m_dataDirectory;	 Catch:{ Exception -> 0x0034 }
        r29 = r0;	 Catch:{ Exception -> 0x0034 }
        r0 = r25;	 Catch:{ Exception -> 0x0034 }
        r1 = r29;	 Catch:{ Exception -> 0x0034 }
        r2 = r6;	 Catch:{ Exception -> 0x0034 }
        r3 = r7;	 Catch:{ Exception -> 0x0034 }
        r4 = r21;	 Catch:{ Exception -> 0x0034 }
        r0.<init>(r1, r2, r3, r4);	 Catch:{ Exception -> 0x0034 }
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r0.notifyForFileUploadStarted(r6);	 Catch:{ Exception -> 0x0034 }
        r0 = r34;	 Catch:{ Exception -> 0x0034 }
        r14 = r0.getBody();	 Catch:{ Exception -> 0x0034 }
        r0 = r25;	 Catch:{ Exception -> 0x0034 }
        r1 = r23;	 Catch:{ Exception -> 0x0034 }
        r0.write(r14, r1);	 Catch:{ Exception -> 0x0034 }
        r0 = r25;	 Catch:{ Exception -> 0x0034 }
        r8 = r0.isFinished();	 Catch:{ Exception -> 0x0034 }
        if (r8 == 0) goto L_0x0111;	 Catch:{ Exception -> 0x0034 }
    L_0x00ee:
        r0 = r25;	 Catch:{ Exception -> 0x0034 }
        r0.complete();	 Catch:{ Exception -> 0x0034 }
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r0.notifyForFileUploadCompleted(r6);	 Catch:{ Exception -> 0x0034 }
    L_0x00f8:
        r13 = new com.abaltatech.mcp.mcs.http.Response;	 Catch:{ Exception -> 0x0034 }
        r9 = "OK";	 Catch:{ Exception -> 0x0034 }
        r15 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ Exception -> 0x0034 }
        r30 = 0;	 Catch:{ Exception -> 0x0034 }
        r31 = 0;	 Catch:{ Exception -> 0x0034 }
        r17 = 1;	 Catch:{ Exception -> 0x0034 }
        r0 = r13;	 Catch:{ Exception -> 0x0034 }
        r1 = r9;	 Catch:{ Exception -> 0x0034 }
        r2 = r15;	 Catch:{ Exception -> 0x0034 }
        r3 = r30;	 Catch:{ Exception -> 0x0034 }
        r4 = r31;	 Catch:{ Exception -> 0x0034 }
        r5 = r17;	 Catch:{ Exception -> 0x0034 }
        r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0034 }
        return r13;
    L_0x0111:
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r1 = r25;	 Catch:{ Exception -> 0x0034 }
        r0.addSession(r6, r1);	 Catch:{ Exception -> 0x0034 }
        goto L_0x00f8;
    L_0x0119:
        r0 = r25;	 Catch:{ Exception -> 0x0034 }
        r1 = r6;	 Catch:{ Exception -> 0x0034 }
        r2 = r21;	 Catch:{ Exception -> 0x0034 }
        r4 = r19;	 Catch:{ Exception -> 0x0034 }
        r8 = r0.isValid(r1, r2, r4);	 Catch:{ Exception -> 0x0034 }
        if (r8 == 0) goto L_0x0182;	 Catch:{ Exception -> 0x0034 }
    L_0x0126:
        r0 = r25;	 Catch:{ Exception -> 0x0034 }
        r8 = r0.validateChecksum(r7);	 Catch:{ Exception -> 0x0034 }
        if (r8 == 0) goto L_0x0171;	 Catch:{ Exception -> 0x0034 }
    L_0x012e:
        r0 = r34;	 Catch:{ Exception -> 0x0034 }
        r14 = r0.getBody();	 Catch:{ Exception -> 0x0034 }
        r0 = r25;	 Catch:{ Exception -> 0x0034 }
        r1 = r23;	 Catch:{ Exception -> 0x0034 }
        r0.write(r14, r1);	 Catch:{ Exception -> 0x0034 }
        r13 = new com.abaltatech.mcp.mcs.http.Response;	 Catch:{ Exception -> 0x0034 }
        r9 = "OK";	 Catch:{ Exception -> 0x0034 }
        r15 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ Exception -> 0x0034 }
        r30 = 0;	 Catch:{ Exception -> 0x0034 }
        r31 = 0;	 Catch:{ Exception -> 0x0034 }
        r17 = 1;	 Catch:{ Exception -> 0x0034 }
        r0 = r13;	 Catch:{ Exception -> 0x0034 }
        r1 = r9;	 Catch:{ Exception -> 0x0034 }
        r2 = r15;	 Catch:{ Exception -> 0x0034 }
        r3 = r30;	 Catch:{ Exception -> 0x0034 }
        r4 = r31;	 Catch:{ Exception -> 0x0034 }
        r5 = r17;	 Catch:{ Exception -> 0x0034 }
        r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x0034 }
        r0 = r25;	 Catch:{ Exception -> 0x016f }
        r8 = r0.isFinished();	 Catch:{ Exception -> 0x016f }
        if (r8 == 0) goto L_0x0196;	 Catch:{ Exception -> 0x016f }
    L_0x015b:
        r0 = r25;	 Catch:{ Exception -> 0x016f }
        r0.complete();	 Catch:{ Exception -> 0x016f }
        r0 = r33;	 Catch:{ Exception -> 0x016f }
        r0.removeSession(r6);	 Catch:{ Exception -> 0x016f }
        r0 = r33;	 Catch:{ Exception -> 0x016f }
        r0.notifyForFileUploadCompleted(r6);	 Catch:{ Exception -> 0x016f }
        return r13;
        goto L_0x016f;
    L_0x016c:
        goto L_0x0035;
    L_0x016f:
        r11 = move-exception;
        goto L_0x016c;
    L_0x0171:
        r32 = com.abaltatech.mcp.mcs.fileupload.EFileUploadError.FE_CHECKSUM_FAILED;
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r1 = r32;	 Catch:{ Exception -> 0x0034 }
        r0.notifyForFileUploadFailed(r6, r1);	 Catch:{ Exception -> 0x0034 }
        r10 = new java.security.InvalidParameterException;	 Catch:{ Exception -> 0x0034 }
        r9 = "Invalid checksum was specified!";	 Catch:{ Exception -> 0x0034 }
        r10.<init>(r9);	 Catch:{ Exception -> 0x0034 }
        throw r10;	 Catch:{ Exception -> 0x0034 }
    L_0x0182:
        r32 = com.abaltatech.mcp.mcs.fileupload.EFileUploadError.FE_INVALID_HEADERS;	 Catch:{ Exception -> 0x0034 }
        r0 = r33;	 Catch:{ Exception -> 0x0034 }
        r1 = r32;	 Catch:{ Exception -> 0x0034 }
        r0.notifyForFileUploadFailed(r6, r1);	 Catch:{ Exception -> 0x0034 }
        r10 = new java.security.InvalidParameterException;	 Catch:{ Exception -> 0x0034 }
        r9 = "Invalid header values were specified!";	 Catch:{ Exception -> 0x0034 }
        r10.<init>(r9);	 Catch:{ Exception -> 0x0034 }
        throw r10;	 Catch:{ Exception -> 0x0034 }
    L_0x0193:
        r30 = 0;
        return r30;
    L_0x0196:
        return r13;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.fileupload.FileUploadServer.handlePostRequest(com.abaltatech.mcp.mcs.http.Request, int):com.abaltatech.mcp.mcs.http.Response");
    }

    public boolean interruptProcessing() throws  {
        return false;
    }

    public boolean init(String $r4, String $r1) throws  {
        if (!($r4.endsWith("/") || $r4.endsWith("\\"))) {
            $r4 = $r4 + "/";
        }
        this.m_serverPath = $r4;
        try {
            Paths.get($r1, new String[0]);
            this.m_dataDirectory = $r1;
            File[] $r7 = new File($r1).listFiles();
            if ($r7 != null) {
                for (File $r2 : $r7) {
                    $r4 = $r2.getName();
                    int $i2 = $r4.lastIndexOf(FileUploadSession.SEPARATOR);
                    if ($i2 > -1 && $r4.substring($i2).equals(FileUploadSession.PARTIAL)) {
                        FileUploadSession $r8 = FileUploadSession.InitFromFile($r4);
                        addSession($r8.getFilename(), $r8);
                    }
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Response processRequest(Request $r1, int $i0) throws  {
        if ($r1.Method.toUpperCase().equalsIgnoreCase("POST")) {
            return handlePostRequest($r1, $i0);
        }
        return new Response("<h1>501 - Not Implemented</h1>", DisplayStrings.DS_NO, "text/plain", new String("The server is unable to handle the request, because it uses an unimplemented request method!").getBytes(), true);
    }

    public boolean canProcess(Request $r1) throws  {
        String $r2 = $r1 != null ? HttpUtils.extractUrlPath($r1.Url) : null;
        if ($r2 == null || !$r2.toLowerCase().startsWith(this.m_serverPath)) {
            return false;
        }
        return true;
    }

    public void setHttpParams(IHttpLayer $r1) throws  {
        this.m_httpLayer = $r1;
    }

    private String validateAndParseUrl(String $r1) throws  {
        if (!$r1.startsWith(this.m_serverPath)) {
            return null;
        }
        $r1 = $r1.substring(this.m_serverPath.length() + 1);
        if ($r1.contains("/")) {
            return null;
        }
        if ($r1.contains("\\")) {
            return null;
        }
        try {
            Paths.get($r1, new String[0]);
            return $r1;
        } catch (InvalidPathException e) {
            return null;
        } catch (NullPointerException e2) {
            return null;
        }
    }

    private FileUploadSession findSession(String $r1) throws  {
        return (FileUploadSession) this.m_connDataMap.get($r1);
    }

    protected void addSession(String $r1, FileUploadSession $r2) throws  {
        this.m_connDataMap.put($r1, $r2);
    }

    protected void removeSession(String $r1) throws  {
        this.m_connDataMap.remove($r1);
    }

    public void terminate() throws  {
        Iterator $r3 = this.m_connDataMap.keySet().iterator();
        while ($r3.hasNext()) {
            String $r5 = (String) $r3.next();
            ((FileUploadSession) this.m_connDataMap.get($r5)).pause();
            removeSession($r5);
        }
        this.m_connDataMap.clear();
    }

    public void registerForNotifications(IFileUploadNotification $r1) throws  {
        this.m_notificationList.add($r1);
    }

    public void unregisterForNotification(IFileUploadNotification $r1) throws  {
        this.m_notificationList.remove($r1);
    }

    protected void notifyForFileUploadStarted(String $r1) throws  {
        for (int $i0 = 0; $i0 < this.m_notificationList.size(); $i0++) {
            ((IFileUploadNotification) this.m_notificationList.get($i0)).onFileUploadStarted($r1);
        }
    }

    protected void notifyForFileUploadCompleted(String $r1) throws  {
        for (int $i0 = 0; $i0 < this.m_notificationList.size(); $i0++) {
            ((IFileUploadNotification) this.m_notificationList.get($i0)).onFileUploadComplete($r1);
        }
    }

    protected void notifyForFileUploadFailed(String $r1, EFileUploadError $r2) throws  {
        for (int $i0 = 0; $i0 < this.m_notificationList.size(); $i0++) {
            ((IFileUploadNotification) this.m_notificationList.get($i0)).onFileUploadFailed($r1, $r2);
        }
    }
}
