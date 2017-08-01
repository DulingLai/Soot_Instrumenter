package com.abaltatech.mcp.mcs.filedownload;

import com.abaltatech.mcp.mcs.http.HttpUtils;
import com.abaltatech.mcp.mcs.http.IHttpLayer;
import com.abaltatech.mcp.mcs.http.IRequestHandler;
import com.abaltatech.mcp.mcs.http.Request;
import com.abaltatech.mcp.mcs.http.Response;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.waze.strings.DisplayStrings;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.zip.CRC32;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class FileDownloadServer implements IRequestHandler {
    static final /* synthetic */ boolean $assertionsDisabled = (!FileDownloadServer.class.desiredAssertionStatus());
    private static final String mc_acceptRanges = "Accept-Ranges: bytes";
    private static final String mc_contentLength = "Content-Length: ";
    private ConcurrentHashMap<Integer, RequestData> m_connDataMap = new ConcurrentHashMap();
    private IHttpLayer m_httpLayer = null;
    private String m_name;
    private ConcurrentHashMap<String, String> m_pathMap = new ConcurrentHashMap();
    private ConcurrentHashMap<String, FileResource> m_resourceMap = new ConcurrentHashMap();

    private class RequestData {
        int m_connectionID;
        long m_fileOffset;
        boolean m_isGetRange;
        long m_offset;
        long m_size;
        InputStream m_stream;
        long m_totalSize;

        private RequestData() throws  {
            this.m_stream = null;
            this.m_size = 0;
            this.m_fileOffset = 0;
            this.m_offset = 0;
            this.m_connectionID = 0;
            this.m_totalSize = 0;
            this.m_isGetRange = false;
        }
    }

    public boolean interruptProcessing() throws  {
        return true;
    }

    synchronized com.abaltatech.mcp.mcs.http.Response processRequestData(com.abaltatech.mcp.mcs.filedownload.FileDownloadServer.RequestData r31, boolean r32) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:69:0x01d7 in {6, 7, 9, 18, 19, 31, 35, 37, 44, 46, 48, 49, 51, 60, 62, 64, 65, 70, 74} preds:[]
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
        r30 = this;
        monitor-enter(r30);
        r6 = $assertionsDisabled;	 Catch:{ Throwable -> 0x000d }
        if (r6 != 0) goto L_0x0010;	 Catch:{ Throwable -> 0x000d }
    L_0x0005:
        if (r31 != 0) goto L_0x0010;	 Catch:{ Throwable -> 0x000d }
    L_0x0007:
        r7 = new java.lang.AssertionError;	 Catch:{ Throwable -> 0x000d }
        r7.<init>();	 Catch:{ Throwable -> 0x000d }
        throw r7;	 Catch:{ Throwable -> 0x000d }
    L_0x000d:
        r8 = move-exception;
    L_0x000e:
        monitor-exit(r30);
        throw r8;
    L_0x0010:
        if (r31 == 0) goto L_0x01e1;
    L_0x0012:
        r10 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Throwable -> 0x000d }
        r9 = new byte[r10];	 Catch:{ Throwable -> 0x000d }
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r11 = r0.m_size;	 Catch:{ Exception -> 0x01dd }
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r13 = r0.m_offset;	 Catch:{ Exception -> 0x01dd }
        r11 = r11 - r13;	 Catch:{ Throwable -> 0x000d }
        r15 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Throwable -> 0x000d }
        r17 = 10000; // 0x2710 float:1.4013E-41 double:4.9407E-320;	 Catch:{ Throwable -> 0x000d }
        r16 = (r11 > r17 ? 1 : (r11 == r17 ? 0 : -1));	 Catch:{ Throwable -> 0x000d }
        if (r16 >= 0) goto L_0x0028;	 Catch:{ Exception -> 0x01dd }
    L_0x0027:
        r15 = (int) r11;	 Catch:{ Exception -> 0x01dd }
    L_0x0028:
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r0 = r0.m_stream;	 Catch:{ Exception -> 0x01dd }
        r19 = r0;	 Catch:{ Exception -> 0x01dd }
        r10 = 0;	 Catch:{ Exception -> 0x01dd }
        r0 = r19;	 Catch:{ Exception -> 0x01dd }
        r15 = r0.read(r9, r10, r15);	 Catch:{ Exception -> 0x01dd }
        if (r15 <= 0) goto L_0x01e1;	 Catch:{ Exception -> 0x01dd }
    L_0x0037:
        r0 = new byte[r15];	 Catch:{ Exception -> 0x01dd }
        r20 = r0;	 Catch:{ Exception -> 0x01dd }
        r10 = 0;	 Catch:{ Exception -> 0x01dd }
        r21 = 0;	 Catch:{ Exception -> 0x01dd }
        r0 = r20;	 Catch:{ Exception -> 0x01dd }
        r1 = r21;	 Catch:{ Exception -> 0x01dd }
        java.lang.System.arraycopy(r9, r10, r0, r1, r15);	 Catch:{ Exception -> 0x01dd }
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r11 = r0.m_offset;	 Catch:{ Exception -> 0x01dd }
        r13 = (long) r15;	 Catch:{ Throwable -> 0x000d }
        r11 = r11 + r13;	 Catch:{ Exception -> 0x01dd }
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r0.m_offset = r11;	 Catch:{ Exception -> 0x01dd }
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r11 = r0.m_offset;	 Catch:{ Exception -> 0x01dd }
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r13 = r0.m_size;	 Catch:{ Exception -> 0x01dd }
        r16 = (r11 > r13 ? 1 : (r11 == r13 ? 0 : -1));	 Catch:{ Throwable -> 0x000d }
        if (r16 < 0) goto L_0x0140;	 Catch:{ Throwable -> 0x000d }
    L_0x005b:
        r6 = 1;	 Catch:{ Exception -> 0x01dd }
    L_0x005c:
        r0 = r31;	 Catch:{ Exception -> 0x01dd }
        r0 = r0.m_isGetRange;	 Catch:{ Exception -> 0x01dd }
        r22 = r0;	 Catch:{ Exception -> 0x01dd }
        if (r22 == 0) goto L_0x0142;
    L_0x0064:
        r23 = new com.abaltatech.mcp.mcs.http.Response;	 Catch:{ Exception -> 0x01dd }
        r24 = "Partial Content";	 Catch:{ Exception -> 0x01dd }
        r10 = 206; // 0xce float:2.89E-43 double:1.02E-321;	 Catch:{ Exception -> 0x01dd }
        r25 = 0;	 Catch:{ Exception -> 0x01dd }
        r0 = r23;	 Catch:{ Exception -> 0x01dd }
        r1 = r24;	 Catch:{ Exception -> 0x01dd }
        r2 = r10;	 Catch:{ Exception -> 0x01dd }
        r3 = r25;	 Catch:{ Exception -> 0x01dd }
        r4 = r20;	 Catch:{ Exception -> 0x01dd }
        r5 = r6;	 Catch:{ Exception -> 0x01dd }
        r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x01dd }
    L_0x0079:
        if (r32 != 0) goto L_0x0158;
    L_0x007b:
        r6 = 1;
    L_0x007c:
        r0 = r23;	 Catch:{ Exception -> 0x01a7 }
        r0.SendOnlyData = r6;	 Catch:{ Exception -> 0x01a7 }
        if (r32 == 0) goto L_0x013e;	 Catch:{ Exception -> 0x01a7 }
    L_0x0082:
        r0 = r31;	 Catch:{ Exception -> 0x01a7 }
        r0 = r0.m_isGetRange;	 Catch:{ Exception -> 0x01a7 }
        r32 = r0;	 Catch:{ Exception -> 0x01a7 }
        if (r32 == 0) goto L_0x015a;	 Catch:{ Exception -> 0x01a7 }
    L_0x008a:
        r10 = 3;	 Catch:{ Exception -> 0x01a7 }
        r0 = new java.lang.String[r10];	 Catch:{ Exception -> 0x01a7 }
        r26 = r0;	 Catch:{ Exception -> 0x01a7 }
        r1 = r23;	 Catch:{ Exception -> 0x01a7 }
        r1.AdditionalHeaders = r0;	 Catch:{ Exception -> 0x01a7 }
        r0 = r23;	 Catch:{ Exception -> 0x01a7 }
        r0 = r0.AdditionalHeaders;	 Catch:{ Exception -> 0x01a7 }
        r26 = r0;	 Catch:{ Exception -> 0x01a7 }
        r27 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r0.<init>();	 Catch:{ Exception -> 0x01a7 }
        r24 = "Content-Length: ";	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r24;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r0 = r31;	 Catch:{ Exception -> 0x01a7 }
        r11 = r0.m_size;	 Catch:{ Exception -> 0x01a7 }
        r28 = java.lang.Long.toString(r11);	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r28;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r28 = r0.toString();	 Catch:{ Exception -> 0x01a7 }
        r10 = 0;	 Catch:{ Exception -> 0x01a7 }
        r26[r10] = r28;	 Catch:{ Exception -> 0x01a7 }
        r0 = r23;	 Catch:{ Exception -> 0x01a7 }
        r0 = r0.AdditionalHeaders;	 Catch:{ Exception -> 0x01a7 }
        r26 = r0;	 Catch:{ Exception -> 0x01a7 }
        goto L_0x00cd;	 Catch:{ Exception -> 0x01a7 }
    L_0x00ca:
        goto L_0x005c;	 Catch:{ Exception -> 0x01a7 }
    L_0x00cd:
        r10 = 1;	 Catch:{ Exception -> 0x01a7 }
        r24 = "Accept-Ranges: bytes";	 Catch:{ Exception -> 0x01a7 }
        r26[r10] = r24;	 Catch:{ Exception -> 0x01a7 }
        r0 = r23;	 Catch:{ Exception -> 0x01a7 }
        r0 = r0.AdditionalHeaders;	 Catch:{ Exception -> 0x01a7 }
        r26 = r0;	 Catch:{ Exception -> 0x01a7 }
        r27 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r0.<init>();	 Catch:{ Exception -> 0x01a7 }
        goto L_0x00e3;	 Catch:{ Exception -> 0x01a7 }
    L_0x00e0:
        goto L_0x0079;	 Catch:{ Exception -> 0x01a7 }
    L_0x00e3:
        r24 = "Content-Range: bytes ";	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r24;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        goto L_0x00f1;	 Catch:{ Exception -> 0x01a7 }
    L_0x00ee:
        goto L_0x007c;	 Catch:{ Exception -> 0x01a7 }
    L_0x00f1:
        r0 = r31;	 Catch:{ Exception -> 0x01a7 }
        r11 = r0.m_fileOffset;	 Catch:{ Exception -> 0x01a7 }
        r28 = java.lang.Long.toString(r11);	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r28;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r24 = "-";	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r24;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r0 = r31;	 Catch:{ Exception -> 0x01a7 }
        r11 = r0.m_size;	 Catch:{ Exception -> 0x01a7 }
        r28 = java.lang.Long.toString(r11);	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r28;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r24 = "/";	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r24;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r0 = r31;	 Catch:{ Exception -> 0x01a7 }
        r11 = r0.m_totalSize;	 Catch:{ Exception -> 0x01a7 }
        r28 = java.lang.Long.toString(r11);	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r28;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r28 = r0.toString();	 Catch:{ Exception -> 0x01a7 }
        r10 = 2;	 Catch:{ Exception -> 0x01a7 }
        r26[r10] = r28;	 Catch:{ Exception -> 0x01a7 }
    L_0x013e:
        monitor-exit(r30);
        return r23;
    L_0x0140:
        r6 = 0;
        goto L_0x00ca;
    L_0x0142:
        r23 = new com.abaltatech.mcp.mcs.http.Response;
        r24 = "OK";	 Catch:{ Exception -> 0x01dd }
        r10 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;	 Catch:{ Exception -> 0x01dd }
        r25 = 0;	 Catch:{ Exception -> 0x01dd }
        r0 = r23;	 Catch:{ Exception -> 0x01dd }
        r1 = r24;	 Catch:{ Exception -> 0x01dd }
        r2 = r10;	 Catch:{ Exception -> 0x01dd }
        r3 = r25;	 Catch:{ Exception -> 0x01dd }
        r4 = r20;	 Catch:{ Exception -> 0x01dd }
        r5 = r6;	 Catch:{ Exception -> 0x01dd }
        r0.<init>(r1, r2, r3, r4, r5);	 Catch:{ Exception -> 0x01dd }
        goto L_0x00e0;
    L_0x0158:
        r6 = 0;
        goto L_0x00ee;
    L_0x015a:
        r10 = 2;	 Catch:{ Exception -> 0x01a7 }
        r0 = new java.lang.String[r10];	 Catch:{ Exception -> 0x01a7 }
        r26 = r0;	 Catch:{ Exception -> 0x01a7 }
        r1 = r23;	 Catch:{ Exception -> 0x01a7 }
        r1.AdditionalHeaders = r0;	 Catch:{ Exception -> 0x01a7 }
        r0 = r23;	 Catch:{ Exception -> 0x01a7 }
        r0 = r0.AdditionalHeaders;	 Catch:{ Exception -> 0x01a7 }
        r26 = r0;	 Catch:{ Exception -> 0x01a7 }
        r27 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r0.<init>();	 Catch:{ Exception -> 0x01a7 }
        r24 = "Content-Length: ";	 Catch:{ Exception -> 0x01a7 }
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r24;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        r0 = r31;	 Catch:{ Exception -> 0x01a7 }
        r11 = r0.m_size;	 Catch:{ Exception -> 0x01a7 }
        r28 = java.lang.Long.toString(r11);	 Catch:{ Exception -> 0x01a7 }
        goto L_0x0186;	 Catch:{ Exception -> 0x01a7 }
    L_0x0183:
        goto L_0x013e;	 Catch:{ Exception -> 0x01a7 }
    L_0x0186:
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r1 = r28;	 Catch:{ Exception -> 0x01a7 }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x01a7 }
        goto L_0x0192;	 Catch:{ Exception -> 0x01a7 }
    L_0x018f:
        goto L_0x013e;	 Catch:{ Exception -> 0x01a7 }
    L_0x0192:
        r0 = r27;	 Catch:{ Exception -> 0x01a7 }
        r28 = r0.toString();	 Catch:{ Exception -> 0x01a7 }
        r10 = 0;	 Catch:{ Exception -> 0x01a7 }
        r26[r10] = r28;	 Catch:{ Exception -> 0x01a7 }
        r0 = r23;	 Catch:{ Exception -> 0x01a7 }
        r0 = r0.AdditionalHeaders;	 Catch:{ Exception -> 0x01a7 }
        r26 = r0;	 Catch:{ Exception -> 0x01a7 }
        r10 = 1;	 Catch:{ Exception -> 0x01a7 }
        r24 = "Accept-Ranges: bytes";	 Catch:{ Exception -> 0x01a7 }
        r26[r10] = r24;	 Catch:{ Exception -> 0x01a7 }
        goto L_0x013e;
    L_0x01a7:
        r29 = move-exception;
    L_0x01a8:
        r27 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01db }
        r0 = r27;	 Catch:{ Throwable -> 0x01db }
        r0.<init>();	 Catch:{ Throwable -> 0x01db }
        r24 = "processRequestData exception: ";	 Catch:{ Throwable -> 0x01db }
        r0 = r27;	 Catch:{ Throwable -> 0x01db }
        r1 = r24;	 Catch:{ Throwable -> 0x01db }
        r27 = r0.append(r1);	 Catch:{ Throwable -> 0x01db }
        r0 = r29;	 Catch:{ Throwable -> 0x01db }
        r28 = r0.toString();	 Catch:{ Throwable -> 0x01db }
        r0 = r27;	 Catch:{ Throwable -> 0x01db }
        r1 = r28;	 Catch:{ Throwable -> 0x01db }
        r27 = r0.append(r1);	 Catch:{ Throwable -> 0x01db }
        r0 = r27;	 Catch:{ Throwable -> 0x01db }
        r28 = r0.toString();	 Catch:{ Throwable -> 0x01db }
        r24 = "FileDownloadServer";	 Catch:{ Throwable -> 0x01db }
        r0 = r24;	 Catch:{ Throwable -> 0x01db }
        r1 = r28;	 Catch:{ Throwable -> 0x01db }
        com.abaltatech.mcp.mcs.logger.MCSLogger.log(r0, r1);	 Catch:{ Throwable -> 0x01db }
        goto L_0x0183;
        goto L_0x01db;
    L_0x01d8:
        goto L_0x000e;
    L_0x01db:
        r8 = move-exception;
        goto L_0x01d8;
    L_0x01dd:
        r29 = move-exception;
        r23 = 0;
        goto L_0x01a8;
    L_0x01e1:
        r23 = 0;
        goto L_0x018f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.mcs.filedownload.FileDownloadServer.processRequestData(com.abaltatech.mcp.mcs.filedownload.FileDownloadServer$RequestData, boolean):com.abaltatech.mcp.mcs.http.Response");
    }

    public void init(String $r1) throws  {
        this.m_name = "/" + $r1;
        MCSLogger.log("File download server request URL: " + this.m_name);
    }

    public void terminate() throws  {
        Iterator $r3 = this.m_connDataMap.keySet().iterator();
        while ($r3.hasNext()) {
            removeRequest(((Integer) $r3.next()).intValue());
        }
        this.m_connDataMap.clear();
    }

    public List<FileResource> getFileResources() throws  {
        ArrayList $r1 = new ArrayList();
        for (Entry value : this.m_resourceMap.entrySet()) {
            $r1.add((FileResource) value.getValue());
        }
        return $r1;
    }

    public boolean addFileResource(String $r1, String $r2) throws  {
        try {
            File $r7 = new File($r1);
            if (!$r7.exists()) {
                return false;
            }
            CRC32 $r5 = new CRC32();
            FileInputStream $r6 = new FileInputStream($r7);
            int $i1 = 0;
            byte[] $r3 = new byte[1024];
            while (true) {
                int $i2 = $r6.read($r3);
                if ($i2 != -1) {
                    $i1 += $i2;
                    $r5.update($r3, 0, $i2);
                } else {
                    $r6.close();
                    FileResource fileResource = new FileResource($r1, $r2, (long) $i1, $r5.getValue());
                    ConcurrentHashMap concurrentHashMap = this.m_resourceMap;
                    ConcurrentHashMap $r9 = concurrentHashMap;
                    concurrentHashMap.put($r2, fileResource);
                    concurrentHashMap = this.m_pathMap;
                    $r9 = concurrentHashMap;
                    concurrentHashMap.put($r1, $r2);
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean removeFileResource(String $r1) throws  {
        String $r4 = (String) this.m_pathMap.get($r1);
        if ($r4 == null) {
            return false;
        }
        synchronized (this.m_resourceMap) {
            this.m_resourceMap.remove($r4);
        }
        synchronized (this.m_pathMap) {
            this.m_pathMap.remove($r1);
        }
        return true;
    }

    public Response processRequest(Request $r1, int $i0) throws  {
        String $r2 = $r1.Method.toUpperCase();
        if ($r2.equalsIgnoreCase("PROPFIND")) {
            return handlePropFindRequest($r1, $i0);
        }
        if ($r2.equalsIgnoreCase("GET")) {
            return handleGetRequest($r1, $i0);
        }
        return new Response("Not Implemented", DisplayStrings.DS_NO, null, null, true);
    }

    public boolean canProcess(Request $r1) throws  {
        String $r2 = $r1 != null ? HttpUtils.extractUrlPath($r1.Url) : null;
        if ($r2 == null || !$r2.toLowerCase().startsWith(this.m_name)) {
            return false;
        }
        return true;
    }

    public void setHttpParams(IHttpLayer $r1) throws  {
        this.m_httpLayer = $r1;
    }

    protected Response handlePropFindRequest(Request request, int connectionID) throws  {
        try {
            Document $r7 = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            Element $r8 = $r7.createElement("D:multistatus");
            $r8.setAttribute("xmlns:D", "DAV");
            $r7.appendChild($r8);
            for (Entry entry : this.m_resourceMap.entrySet()) {
                String $r14 = (String) entry.getKey();
                FileResource $r15 = (FileResource) entry.getValue();
                Element $r16 = $r7.createElement("D:response");
                Element $r17 = $r7.createElement("D:href");
                $r17.setTextContent($r14);
                $r16.appendChild($r17);
                $r17 = $r7.createElement("D:propstat");
                Element $r18 = $r7.createElement("D:prop");
                Element $r19 = $r7.createElement("D:checksum");
                $r19.setTextContent(Long.toString($r15.getLastChecksum()));
                $r18.appendChild($r19);
                $r19 = $r7.createElement("D:getcontentlength");
                $r19.setTextContent(Long.toString($r15.getContentLength()));
                $r18.appendChild($r19);
                $r19 = $r7.createElement("D:status");
                $r19.setTextContent("HTTP/1.1 200 OK");
                $r18.appendChild($r19);
                $r17.appendChild($r18);
                $r16.appendChild($r17);
                $r8.appendChild($r16);
            }
            Transformer $r23 = TransformerFactory.newInstance().newTransformer();
            DOMSource dOMSource = new DOMSource($r7);
            OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            $r23.transform(dOMSource, new StreamResult(byteArrayOutputStream));
            byte[] $r24 = byteArrayOutputStream.toByteArray();
            Response response = new Response("Multi-Status", 207, "text/xml", $r24, true);
            try {
                String[] strArr = new String[1];
                String[] $r25 = strArr;
                response.AdditionalHeaders = strArr;
                $r25 = response.AdditionalHeaders;
                StringBuilder $r26 = new StringBuilder().append(mc_contentLength);
                long $l1 = $r24.length;
                connectionID = $l1;
                $l1 = (long) $l1;
                $r25[0] = $r26.append(Long.toString($l1)).toString();
                return response;
            } catch (Exception e) {
                return new Response("Server Error", DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT, null, null, true);
            }
        } catch (Exception e2) {
            return new Response("Server Error", DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT, null, null, true);
        }
    }

    protected Response handleGetRequest(Request $r1, int $i0) throws  {
        try {
            String[] $r7 = HttpUtils.extractUrlPath($r1.Url).split("/", 3);
            if ($r7.length < 3) {
                return null;
            }
            FileResource $r10 = (FileResource) this.m_resourceMap.get($r7[2]);
            if ($r10 == null) {
                return null;
            }
            long $l2 = $r10.getContentLength();
            long $l3 = 0;
            long $l4 = $l2;
            long $l5 = $l2;
            String $r5 = $r10.getLocalPath();
            HashMap $r11 = $r1.AdditionalHeaders;
            if ($r11 != null) {
                String $r12 = (String) $r11.get("Range");
                if ($r12 != null) {
                    $r7 = $r12.split("=", 2);
                    if ($r7.length == 2) {
                        $r7 = $r7[1].split("-", 2);
                        if ($r7.length >= 1) {
                            $l5 = Long.parseLong($r7[0]);
                            $l3 = $l5;
                            if ($r7.length >= 2) {
                                $l4 = Long.parseLong($r7[1]);
                            }
                            $l5 = ($l4 - $l5) + 1;
                        }
                    }
                }
            }
            long $l42 = new FileInputStream($r5);
            $l3 = $l42.skip($l3);
            $l42 = new RequestData();
            $l42.m_stream = $l42;
            $l42.m_size = $l5;
            $l42.m_fileOffset = $l3;
            $l42.m_offset = 0;
            $l42.m_connectionID = $i0;
            $l42.m_isGetRange = $l5 < $l2;
            $l42.m_totalSize = $l2;
            Response $r13 = processRequestData($l42, true);
            Response $r6 = $r13;
            if ($r13.IsLast) {
                return $r13;
            }
            addRequest($i0, $l42);
            return $r13;
        } catch (Exception $r3) {
            $r3.printStackTrace();
            return null;
        }
    }

    protected String getLocalFilePath(String $r1) throws  {
        return (String) this.m_pathMap.get($r1);
    }

    private RequestData getRequest(int $i0) throws  {
        return (RequestData) this.m_connDataMap.get(Integer.valueOf($i0));
    }

    private void addRequest(int $i0, RequestData $r1) throws  {
        removeRequest($i0);
        if (!$assertionsDisabled && $r1 == null) {
            throw new AssertionError();
        } else if ($r1 != null) {
            this.m_connDataMap.put(Integer.valueOf($i0), $r1);
        }
    }

    private void removeRequest(int $i0) throws  {
        RequestData $r4 = (RequestData) this.m_connDataMap.remove(Integer.valueOf($i0));
        if ($r4 != null) {
            try {
                $r4.m_stream.close();
            } catch (IOException e) {
            }
        }
    }

    public Response getAdditionalResponseData(int $i0) throws  {
        RequestData $r1 = getRequest($i0);
        if ($r1 != null) {
            return processRequestData($r1, false);
        }
        return null;
    }
}
