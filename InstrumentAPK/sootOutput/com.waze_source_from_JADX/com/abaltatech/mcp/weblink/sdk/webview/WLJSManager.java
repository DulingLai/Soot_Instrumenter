package com.abaltatech.mcp.weblink.sdk.webview;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.abaltatech.mcp.weblink.sdk.WEBLINK;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager.INotification;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager.INotificationEventListener;
import com.abaltatech.mcp.weblink.servercore.WebLinkServerCore;
import com.abaltatech.weblink.service.interfaces.IWLVehicleDataAvailableCallback.Stub;
import com.abaltatech.weblink.service.interfaces.IWLVehicleInfo;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WLJSManager extends Stub {
    private static final String TAG = "WLJSManager";
    private static final String ms_jsInjectNotifications = WebViewJSInjections.getResource("weblink_inject_notifications", WEBLINK.instance.getContext());
    private static final String ms_jsInjectPromises = WebViewJSInjections.getResource("promiscuous", WEBLINK.instance.getContext());
    private static final String ms_jsInjectSpeechRecognition = WebViewJSInjections.getResource("weblink_inject_speechrecognition", WEBLINK.instance.getContext());
    private static final String ms_jsInjectVehicleInfo = WebViewJSInjections.getResource("vehicle_info", WEBLINK.instance.getContext());
    private Context m_context = null;
    private Handler m_handler;
    private INotificationListener m_notificationListener = null;
    private Options m_options;
    private SpeechRecognizer m_speechRecognizer;
    private Bundle m_vehicleInfoData = null;
    private IWLVehicleInfo m_vehicleInfoService = null;
    private WebView m_webView;

    class C03691 implements ServiceConnection {
        C03691() throws  {
        }

        public void onServiceDisconnected(ComponentName name) throws  {
            WLJSManager.this.m_vehicleInfoService = null;
        }

        public void onServiceConnected(ComponentName name, IBinder $r2) throws  {
            WLJSManager.this.m_vehicleInfoService = IWLVehicleInfo.Stub.asInterface($r2);
            try {
                WLJSManager.this.m_vehicleInfoService.subscribe(WLJSManager.this);
            } catch (RemoteException $r3) {
                MCSLogger.log(WLJSManager.TAG, "Error connecting to the WL Vehicle Info service", $r3);
            }
        }
    }

    public interface INotificationListener {
        void onNotificationClicked() throws ;
    }

    private class JSBridge implements INotificationEventListener {
        private static final int DEFAULT_WEB_NOTIFICATION_TIMEOUT = 8000;
        private int m_currentSRID;
        private Handler m_handler;
        @SuppressLint({"UseSparseArrays"})
        final Map<Integer, INotification> m_idToNotification;
        private int m_notificationIndex;
        final Map<INotification, Integer> m_notificationToID;

        class SRListener implements RecognitionListener {
            SRListener() throws  {
            }

            public void onReadyForSpeech(Bundle params) throws  {
                Log.d(WLJSManager.TAG, "onReadyForSpeech");
            }

            public void onBeginningOfSpeech() throws  {
                Log.d(WLJSManager.TAG, "onBeginningOfSpeech");
            }

            public void onRmsChanged(float rmsdB) throws  {
            }

            public void onBufferReceived(byte[] buffer) throws  {
                Log.d(WLJSManager.TAG, "onBufferReceived");
            }

            public void onEndOfSpeech() throws  {
                Log.d(WLJSManager.TAG, "onEndofSpeech");
            }

            public void onError(int $i0) throws  {
                Log.d(WLJSManager.TAG, "SR Error " + $i0);
                JSBridge.this.onSRError("no-speech", "no-speech: " + $i0);
            }

            public void onResults(Bundle $r1) throws  {
                String $r6;
                Log.d(WLJSManager.TAG, "onResults");
                ArrayList $r2 = $r1.getStringArrayList("results_recognition");
                String $r3 = "{ results: [";
                if (!($r2 == null || $r2.isEmpty())) {
                    boolean $z0 = true;
                    Iterator $r4 = $r2.iterator();
                    while ($r4.hasNext()) {
                        $r6 = (String) $r4.next();
                        if ($z0) {
                            $z0 = false;
                        } else {
                            $r3 = $r3 + ", ";
                        }
                        $r3 = (($r3 + "{ transcript: \"") + escapeQuotes($r6)) + "\" } ";
                    }
                }
                $r3 = $r3 + "] }";
                $r6 = "WebLink.speechrecognition.onResult('" + $r3 + "')";
                Log.d(WLJSManager.TAG, "SpeechRecognition RESULT: " + $r3);
                JSBridge.this.executeJS($r6);
                JSBridge.this.stopSpeechRecognition();
            }

            private String escapeQuotes(String $r1) throws  {
                if ($r1 == null || !$r1.contains("'")) {
                    return $r1;
                }
                return $r1.replace("'", "\\'");
            }

            public void onPartialResults(Bundle partialResults) throws  {
                Log.d(WLJSManager.TAG, "onPartialResults");
            }

            public void onEvent(int $i0, Bundle params) throws  {
                Log.d(WLJSManager.TAG, "onEvent " + $i0);
            }
        }

        @android.webkit.JavascriptInterface
        @android.annotation.SuppressLint({"DefaultLocale"})
        public int addNotification(java.lang.String r31, java.lang.String r32, java.lang.String r33, int r34, boolean r35) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:31:0x011f in {5, 8, 13, 18, 26, 30, 32, 37, 39} preds:[]
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
            r30 = this;
            monitor-enter(r30);
            r0 = r30;	 Catch:{ Throwable -> 0x011c }
            r4 = r0.m_notificationIndex;	 Catch:{ Throwable -> 0x011c }
            r4 = r4 + 1;	 Catch:{ Throwable -> 0x011c }
            r0 = r30;	 Catch:{ Throwable -> 0x011c }
            r0.m_notificationIndex = r4;	 Catch:{ Throwable -> 0x011c }
            r0 = r30;	 Catch:{ Throwable -> 0x011c }
            r4 = r0.m_notificationIndex;	 Catch:{ Throwable -> 0x011c }
            monitor-exit(r30);	 Catch:{ Throwable -> 0x011c }
            if (r34 > 0) goto L_0x0014;
        L_0x0012:
            r34 = 8000; // 0x1f40 float:1.121E-41 double:3.9525E-320;
        L_0x0014:
            r5 = 0;
            if (r32 != 0) goto L_0x0123;
        L_0x0017:
            r32 = "";
        L_0x0019:
            r0 = r32;
            r6 = r0.toLowerCase();
            r0 = r32;
            r7 = r0.length();
            if (r7 <= 0) goto L_0x0039;
        L_0x0027:
            r9 = "file://";
            r8 = r6.startsWith(r9);
            if (r8 == 0) goto L_0x0039;
        L_0x002f:
            r0 = r30;
            r1 = r32;
            r5 = r0.getBitmap(r1);
            r32 = "";
        L_0x0039:
            r10 = 0;
            r0 = r30;
            r11 = com.abaltatech.mcp.weblink.sdk.webview.WLJSManager.this;
            r12 = r11.m_context;
            if (r12 == 0) goto L_0x0087;
        L_0x0044:
            r0 = r30;
            r11 = com.abaltatech.mcp.weblink.sdk.webview.WLJSManager.this;
            r13 = r11.m_options;
            r14 = r13.activityClass;
            if (r14 == 0) goto L_0x0087;
        L_0x0050:
            r15 = new android.content.Intent;
            r0 = r30;
            r11 = com.abaltatech.mcp.weblink.sdk.webview.WLJSManager.this;
            r12 = r11.m_context;
            r0 = r30;
            r11 = com.abaltatech.mcp.weblink.sdk.webview.WLJSManager.this;
            r13 = r11.m_options;
            r14 = r13.activityClass;
            r15.<init>(r12, r14);
            r16 = java.lang.System.currentTimeMillis();
            r18 = 268435455; // 0xfffffff float:2.5243547E-29 double:1.326247364E-315;
            r0 = r16;
            r2 = r18;
            r0 = r0 & r2;
            r16 = r0;
            r7 = (int) r0;
            r0 = r30;
            r11 = com.abaltatech.mcp.weblink.sdk.webview.WLJSManager.this;
            r12 = r11.m_context;
            r20 = 134217728; // 0x8000000 float:3.85186E-34 double:6.63123685E-316;
            r0 = r20;
            r10 = android.app.PendingIntent.getActivity(r12, r7, r15, r0);
        L_0x0087:
            r21 = new com.abaltatech.mcp.weblink.sdk.WLNotification$Builder;
            r0 = r30;
            r11 = com.abaltatech.mcp.weblink.sdk.webview.WLJSManager.this;
            r22 = r11.m_webView;
            r0 = r22;
            r12 = r0.getContext();
            r0 = r21;
            r0.<init>(r12);
            r0 = r21;
            r1 = r31;
            r21 = r0.setContentText(r1);
            r0 = r21;
            r1 = r32;
            r21 = r0.setLargeIconURL(r1);
            r0 = r21;
            r21 = r0.setLargeIcon(r5);
            r0 = r21;
            r1 = r34;
            r21 = r0.setTimeout(r1);
            r20 = 0;
            r0 = r21;
            r1 = r20;
            r21 = r0.setPriority(r1);
            r0 = r21;
            r1 = r35;
            r21 = r0.setShowProgress(r1);
            r0 = r21;
            r21 = r0.setContentIntent(r10);
            r0 = r21;
            r23 = r0.build();
            r24 = com.abaltatech.mcp.weblink.sdk.WLNotificationManager.getInstance();
            r20 = -1;
            r0 = r24;
            r1 = r33;
            r2 = r20;
            r3 = r23;
            r25 = r0.notify(r1, r2, r3);
            if (r25 == 0) goto L_0x012d;
        L_0x00ec:
            monitor-enter(r30);
            r0 = r30;	 Catch:{ Throwable -> 0x012a }
            r0 = r0.m_idToNotification;	 Catch:{ Throwable -> 0x012a }
            r26 = r0;	 Catch:{ Throwable -> 0x012a }
            r27 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x012a }
            r0 = r26;	 Catch:{ Throwable -> 0x012a }
            r1 = r27;	 Catch:{ Throwable -> 0x012a }
            r2 = r25;	 Catch:{ Throwable -> 0x012a }
            r0.put(r1, r2);	 Catch:{ Throwable -> 0x012a }
            r0 = r30;	 Catch:{ Throwable -> 0x012a }
            r0 = r0.m_notificationToID;	 Catch:{ Throwable -> 0x012a }
            r26 = r0;	 Catch:{ Throwable -> 0x012a }
            r27 = java.lang.Integer.valueOf(r4);	 Catch:{ Throwable -> 0x012a }
            r0 = r26;	 Catch:{ Throwable -> 0x012a }
            r1 = r25;	 Catch:{ Throwable -> 0x012a }
            r2 = r27;	 Catch:{ Throwable -> 0x012a }
            r0.put(r1, r2);	 Catch:{ Throwable -> 0x012a }
            monitor-exit(r30);	 Catch:{ Throwable -> 0x012a }
            r0 = r25;
            r1 = r30;
            r0.registerListener(r1);
            return r4;
        L_0x011c:
            r28 = move-exception;
            monitor-exit(r30);	 Catch:{ Throwable -> 0x011c }
            throw r28;
            goto L_0x0123;
        L_0x0120:
            goto L_0x0019;
        L_0x0123:
            r0 = r32;
            r32 = r0.trim();
            goto L_0x0120;
        L_0x012a:
            r29 = move-exception;
            monitor-exit(r30);	 Catch:{ Throwable -> 0x012a }
            throw r29;
        L_0x012d:
            r20 = -1;
            return r20;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.abaltatech.mcp.weblink.sdk.webview.WLJSManager.JSBridge.addNotification(java.lang.String, java.lang.String, java.lang.String, int, boolean):int");
        }

        private JSBridge() throws  {
            this.m_idToNotification = new HashMap();
            this.m_notificationToID = new HashMap();
            this.m_notificationIndex = 0;
            this.m_handler = new Handler();
            this.m_currentSRID = 0;
        }

        private Bitmap getBitmap(String $r1) throws  {
            Bitmap $r3 = null;
            InputStream $r4 = null;
            try {
                InputStream $r7 = (InputStream) new URL($r1).getContent();
                $r4 = $r7;
                Bitmap $r8 = BitmapFactory.decodeStream($r7);
                $r3 = $r8;
                $r7.close();
                return $r8;
            } catch (Exception e) {
                if ($r4 == null) {
                    return $r3;
                }
                try {
                    $r4.close();
                    return $r3;
                } catch (IOException e2) {
                    return $r3;
                }
            }
        }

        @JavascriptInterface
        public void closeNotification(int $i0) throws  {
            INotification $r1 = null;
            synchronized (this) {
                if (this.m_idToNotification.containsKey(Integer.valueOf($i0))) {
                    $r1 = (INotification) this.m_idToNotification.get(Integer.valueOf($i0));
                }
            }
            if ($r1 != null) {
                $r1.dismissNotification();
            }
        }

        public void onNotificationDismissed(INotification $r1) throws  {
            int $i0 = -1;
            synchronized (this) {
                if (this.m_notificationToID.containsKey($r1)) {
                    $i0 = ((Integer) this.m_notificationToID.get($r1)).intValue();
                }
                this.m_notificationToID.remove($r1);
                this.m_idToNotification.remove(Integer.valueOf($i0));
            }
            if ($i0 > -1) {
                executeJS("WebLink.notifications.onNotificationEvent('onclose', " + $i0 + ");");
            }
        }

        public void onNotificationClicked(INotification $r1) throws  {
            int $i0 = -1;
            synchronized (this) {
                if (this.m_notificationToID.containsKey($r1)) {
                    $i0 = ((Integer) this.m_notificationToID.get($r1)).intValue();
                }
            }
            if ($i0 > -1) {
                executeJS("WebLink.notifications.onNotificationEvent('onclick', " + $i0 + ");");
                if (WLJSManager.this.m_notificationListener != null) {
                    WLJSManager.this.m_notificationListener.onNotificationClicked();
                }
            }
        }

        public void onNotificationShown(INotification $r1) throws  {
            int $i0 = -1;
            synchronized (this) {
                if (this.m_notificationToID.containsKey($r1)) {
                    $i0 = ((Integer) this.m_notificationToID.get($r1)).intValue();
                }
            }
            if ($i0 > -1) {
                executeJS("WebLink.notifications.onNotificationEvent('onshow', " + $i0 + ");");
            }
        }

        @JavascriptInterface
        public synchronized void startSpeechRecognition(final int $i0) throws  {
            this.m_handler.post(new Runnable() {
                public void run() throws  {
                    if (WLJSManager.this.m_speechRecognizer == null && $i0 > 0) {
                        WLJSManager.this.m_speechRecognizer = SpeechRecognizer.createSpeechRecognizer(WEBLINK.instance.getContext());
                        WLJSManager.this.m_speechRecognizer.setRecognitionListener(new SRListener());
                        Intent $r1 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                        $r1.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                        $r1.putExtra("calling_package", "com.abaltatech.mcp.weblink.sdk.webview");
                        $r1.putExtra("android.speech.extra.MAX_RESULTS", $i0);
                        WLJSManager.this.m_speechRecognizer.startListening($r1);
                        Log.i(WLJSManager.TAG, "Start SR");
                        JSBridge.this.m_currentSRID = JSBridge.this.m_currentSRID + 1;
                        final int $i0 = JSBridge.this.m_currentSRID;
                        JSBridge.this.m_handler.postDelayed(new Runnable() {
                            public void run() throws  {
                                synchronized (JSBridge.this) {
                                    if ($i0 == JSBridge.this.m_currentSRID) {
                                        JSBridge.this.onSRError("no-speech", "no-speech");
                                    }
                                }
                            }
                        }, 10000);
                    }
                }
            });
        }

        protected synchronized void onSRError(String $r1, String $r2) throws  {
            if (WLJSManager.this.m_speechRecognizer != null) {
                executeJS("WebLink.speechrecognition.onError('" + $r1 + "', '" + $r2 + "')");
            }
            stopSpeechRecognition();
        }

        @JavascriptInterface
        public synchronized void stopSpeechRecognition() throws  {
            if (WLJSManager.this.m_speechRecognizer != null) {
                WLJSManager.this.m_speechRecognizer.stopListening();
                WLJSManager.this.m_speechRecognizer = null;
            }
            this.m_currentSRID++;
        }

        private void executeJS(final String $r1) throws  {
            this.m_handler.post(new Runnable() {
                public void run() throws  {
                    WebView $r3 = WLJSManager.this.m_webView;
                    if ($r3 != null) {
                        $r3.loadUrl("javascript: " + $r1);
                    }
                }
            });
        }
    }

    public static class Options {
        public Class activityClass = null;
        public boolean injectNotifications = true;
        public boolean injectSpeechRecognitionAPI = true;
        public boolean injectVehicleDataAPI = false;
    }

    private class VehicleInfoProperty {
        public String InterfaceName = "";
        public String PropertyName = "";
        public String PropertyValue = "";

        public VehicleInfoProperty(String $r2, String $r3, String $r4) throws  {
            this.InterfaceName = $r2;
            this.PropertyName = $r3;
            this.PropertyValue = $r4;
        }
    }

    public WLJSManager(WebView $r1, Options $r2) throws  {
        if ($r1 == null) {
            throw new IllegalArgumentException("webView cannot be null");
        }
        this.m_webView = $r1;
        if ($r2 == null) {
            $r2 = new Options();
        }
        this.m_options = $r2;
        this.m_handler = new Handler(Looper.getMainLooper());
        this.m_webView.addJavascriptInterface(new JSBridge(), TAG);
        if (!WEBLINK.bindService(WEBLINK.instance.getContext(), WebLinkServerCore.WLSERVICE_IWLVEHICLEINFO, new C03691())) {
            MCSLogger.log(TAG, "Error connecting to the WL Vehicle Info service");
        }
        this.m_context = this.m_webView.getContext();
    }

    public void injectJS(WebView $r1) throws  {
        if (this.m_webView != $r1) {
            Log.i(TAG, "Incorrect WebView provided!");
        }
        if (this.m_options.injectNotifications) {
            $r1.loadUrl("javascript:" + ms_jsInjectNotifications);
        }
        if (this.m_options.injectVehicleDataAPI) {
            $r1.loadUrl("javascript:" + ms_jsInjectPromises);
            $r1.loadUrl("javascript:" + ms_jsInjectVehicleInfo);
            if (this.m_vehicleInfoData != null) {
                onVehicleDataAvailable(this.m_vehicleInfoData);
            }
        }
        if (this.m_options.injectSpeechRecognitionAPI) {
            $r1.loadUrl("javascript:" + ms_jsInjectSpeechRecognition);
        }
    }

    public void onVehicleDataAvailable(Bundle $r1) throws  {
        if (this.m_options.injectVehicleDataAPI && $r1 != null) {
            String $r4 = "";
            for (String vehicleInfoProperty : $r1.keySet()) {
                VehicleInfoProperty $r8 = getVehicleInfoProperty(vehicleInfoProperty, $r1);
                if ($r8 != null) {
                    $r4 = $r4 + "navigator.vehicle._setValue('" + $r8.InterfaceName + "', '" + $r8.PropertyName + "', '" + $r8.PropertyValue + "');";
                }
            }
            if ($r4.length() > 0) {
                $r4 = "javascript:" + $r4;
                this.m_handler.post(new Runnable() {
                    public void run() throws  {
                        WLJSManager.this.m_webView.loadUrl($r4);
                    }
                });
            }
        }
        this.m_vehicleInfoData = $r1;
    }

    private VehicleInfoProperty getVehicleInfoProperty(String $r1, Bundle $r2) throws  {
        if ($r1.compareTo(WLTypes.VEHICLEDATA_ATTRIBUTE_SPEED) != 0) {
            return null;
        }
        return new VehicleInfoProperty("vehicleSpeed", WLTypes.VEHICLEDATA_ATTRIBUTE_SPEED, "" + $r2.getFloat($r1));
    }

    public INotificationListener getNotificationListener() throws  {
        return this.m_notificationListener;
    }

    public void setNotificationListener(INotificationListener $r1) throws  {
        this.m_notificationListener = $r1;
    }
}
