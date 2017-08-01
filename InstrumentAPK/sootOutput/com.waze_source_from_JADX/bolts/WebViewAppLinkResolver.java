package bolts;

import android.content.Context;
import android.net.Uri;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import bolts.AppLink.Target;
import bolts.Task.TaskCompletionSource;
import com.facebook.appevents.AppEventsConstants;
import dalvik.annotation.Signature;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class WebViewAppLinkResolver implements AppLinkResolver {
    private static final String KEY_AL_VALUE = "value";
    private static final String KEY_ANDROID = "android";
    private static final String KEY_APP_NAME = "app_name";
    private static final String KEY_CLASS = "class";
    private static final String KEY_PACKAGE = "package";
    private static final String KEY_SHOULD_FALLBACK = "should_fallback";
    private static final String KEY_URL = "url";
    private static final String KEY_WEB = "web";
    private static final String KEY_WEB_URL = "url";
    private static final String META_TAG_PREFIX = "al";
    private static final String PREFER_HEADER = "Prefer-Html-Meta-Tags";
    private static final String TAG_EXTRACTION_JAVASCRIPT = "javascript:boltsWebViewAppLinkResolverResult.setValue((function() {  var metaTags = document.getElementsByTagName('meta');  var results = [];  for (var i = 0; i < metaTags.length; i++) {    var property = metaTags[i].getAttribute('property');    if (property && property.substring(0, 'al:'.length) === 'al:') {      var tag = { \"property\": metaTags[i].getAttribute('property') };      if (metaTags[i].hasAttribute('content')) {        tag['content'] = metaTags[i].getAttribute('content');      }      results.push(tag);    }  }  return JSON.stringify(results);})())";
    private final Context context;

    private static java.lang.String readFromConnection(java.net.URLConnection r15) throws java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0062 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = r15 instanceof java.net.HttpURLConnection;
        if (r0 == 0) goto L_0x002c;
    L_0x0004:
        r2 = r15;
        r2 = (java.net.HttpURLConnection) r2;
        r1 = r2;
        r3 = r15.getInputStream();	 Catch:{ Exception -> 0x0026 }
    L_0x000c:
        r4 = new java.io.ByteArrayOutputStream;	 Catch:{ Throwable -> 0x0021 }
        r4.<init>();	 Catch:{ Throwable -> 0x0021 }
        r6 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ Throwable -> 0x0021 }
        r5 = new byte[r6];	 Catch:{ Throwable -> 0x0021 }
    L_0x0015:
        r7 = r3.read(r5);	 Catch:{ Throwable -> 0x0021 }
        r6 = -1;	 Catch:{ Throwable -> 0x0021 }
        if (r7 == r6) goto L_0x0031;	 Catch:{ Throwable -> 0x0021 }
    L_0x001c:
        r6 = 0;	 Catch:{ Throwable -> 0x0021 }
        r4.write(r5, r6, r7);	 Catch:{ Throwable -> 0x0021 }
        goto L_0x0015;
    L_0x0021:
        r8 = move-exception;
        r3.close();
        throw r8;
    L_0x0026:
        r9 = move-exception;
        r3 = r1.getErrorStream();
        goto L_0x000c;
    L_0x002c:
        r3 = r15.getInputStream();
        goto L_0x000c;
    L_0x0031:
        r10 = r15.getContentEncoding();	 Catch:{ Throwable -> 0x0021 }
        r11 = r10;
        if (r10 != 0) goto L_0x0062;	 Catch:{ Throwable -> 0x0021 }
    L_0x0038:
        r10 = r15.getContentType();	 Catch:{ Throwable -> 0x0021 }
        r13 = ";";	 Catch:{ Throwable -> 0x0021 }
        r12 = r10.split(r13);	 Catch:{ Throwable -> 0x0021 }
        r7 = r12.length;	 Catch:{ Throwable -> 0x0021 }
        r14 = 0;
    L_0x0044:
        if (r14 >= r7) goto L_0x005e;	 Catch:{ Throwable -> 0x0021 }
    L_0x0046:
        r10 = r12[r14];	 Catch:{ Throwable -> 0x0021 }
        r10 = r10.trim();	 Catch:{ Throwable -> 0x0021 }
        r13 = "charset=";	 Catch:{ Throwable -> 0x0021 }
        r0 = r10.startsWith(r13);	 Catch:{ Throwable -> 0x0021 }
        if (r0 == 0) goto L_0x006f;
    L_0x0054:
        r11 = "charset=";	 Catch:{ Throwable -> 0x0021 }
        r7 = r11.length();	 Catch:{ Throwable -> 0x0021 }
        r11 = r10.substring(r7);	 Catch:{ Throwable -> 0x0021 }
    L_0x005e:
        if (r11 != 0) goto L_0x0062;	 Catch:{ Throwable -> 0x0021 }
    L_0x0060:
        r11 = "UTF-8";	 Catch:{ Throwable -> 0x0021 }
    L_0x0062:
        r10 = new java.lang.String;	 Catch:{ Throwable -> 0x0021 }
        r5 = r4.toByteArray();	 Catch:{ Throwable -> 0x0021 }
        r10.<init>(r5, r11);	 Catch:{ Throwable -> 0x0021 }
        r3.close();
        return r10;
    L_0x006f:
        r14 = r14 + 1;
        goto L_0x0044;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.WebViewAppLinkResolver.readFromConnection(java.net.URLConnection):java.lang.String");
    }

    public WebViewAppLinkResolver(Context $r1) throws  {
        this.context = $r1;
    }

    public Task<AppLink> getAppLinkFromUrlInBackground(@Signature({"(", "Landroid/net/Uri;", ")", "Lbolts/Task", "<", "Lbolts/AppLink;", ">;"}) final Uri $r1) throws  {
        final Capture $r2 = new Capture();
        final Capture $r3 = new Capture();
        return Task.callInBackground(new Callable<Void>() {
            public Void call() throws Exception {
                URL $r1 = new URL($r1.toString());
                URLConnection $r4 = null;
                while ($r1 != null) {
                    URLConnection $r5 = $r1.openConnection();
                    $r4 = $r5;
                    if ($r5 instanceof HttpURLConnection) {
                        ((HttpURLConnection) $r5).setInstanceFollowRedirects(true);
                    }
                    $r5.setRequestProperty(WebViewAppLinkResolver.PREFER_HEADER, WebViewAppLinkResolver.META_TAG_PREFIX);
                    $r5.connect();
                    if ($r5 instanceof HttpURLConnection) {
                        HttpURLConnection $r6 = (HttpURLConnection) $r5;
                        if ($r6.getResponseCode() < 300 || $r6.getResponseCode() >= 400) {
                            $r1 = null;
                        } else {
                            $r1 = new URL($r6.getHeaderField("Location"));
                            $r6.disconnect();
                        }
                    } else {
                        $r1 = null;
                    }
                }
                try {
                    $r2.set(WebViewAppLinkResolver.readFromConnection($r4));
                    $r3.set($r4.getContentType());
                    return null;
                } finally {
                    if ($r4 instanceof HttpURLConnection) {
                        ((HttpURLConnection) $r4).disconnect();
                    }
                }
            }
        }).onSuccessTask(new Continuation<Void, Task<JSONArray>>() {

            class C03031 extends WebViewClient {
                private boolean loaded = false;

                C03031() throws  {
                }

                private void runJavaScript(WebView $r1) throws  {
                    if (!this.loaded) {
                        this.loaded = true;
                        $r1.loadUrl(WebViewAppLinkResolver.TAG_EXTRACTION_JAVASCRIPT);
                    }
                }

                public void onPageFinished(WebView $r1, String $r2) throws  {
                    super.onPageFinished($r1, $r2);
                    runJavaScript($r1);
                }

                public void onLoadResource(WebView $r1, String $r2) throws  {
                    super.onLoadResource($r1, $r2);
                    runJavaScript($r1);
                }
            }

            public Task<JSONArray> then(@Signature({"(", "Lbolts/Task", "<", "Ljava/lang/Void;", ">;)", "Lbolts/Task", "<", "Lorg/json/JSONArray;", ">;"}) Task<Void> task) throws Exception {
                final TaskCompletionSource $r3 = Task.create();
                WebView $r2 = new WebView(WebViewAppLinkResolver.this.context);
                $r2.getSettings().setJavaScriptEnabled(true);
                $r2.setNetworkAvailable(false);
                $r2.setWebViewClient(new C03031());
                $r2.addJavascriptInterface(new Object() {
                    @JavascriptInterface
                    public void setValue(String $r1) throws  {
                        try {
                            $r3.trySetResult(new JSONArray($r1));
                        } catch (JSONException $r2) {
                            $r3.trySetError($r2);
                        }
                    }
                }, "boltsWebViewAppLinkResolverResult");
                String $r9 = null;
                Capture $r10 = $r3;
                if ($r10.get() != null) {
                    $r10 = $r3;
                    $r9 = ((String) $r10.get()).split(";")[0];
                }
                Uri $r13 = $r1;
                String $r14 = $r13.toString();
                $r10 = $r2;
                $r2.loadDataWithBaseURL($r14, (String) $r10.get(), $r9, null, null);
                return $r3.getTask();
            }
        }, Task.UI_THREAD_EXECUTOR).onSuccess(new Continuation<JSONArray, AppLink>() {
            public AppLink then(@Signature({"(", "Lbolts/Task", "<", "Lorg/json/JSONArray;", ">;)", "Lbolts/AppLink;"}) Task<JSONArray> $r1) throws Exception {
                return WebViewAppLinkResolver.makeAppLinkFromAlData(WebViewAppLinkResolver.parseAlData((JSONArray) $r1.getResult()), $r1);
            }
        });
    }

    private static Map<String, Object> parseAlData(@Signature({"(", "Lorg/json/JSONArray;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;"}) JSONArray $r0) throws JSONException {
        HashMap $r1 = r8;
        HashMap r8 = new HashMap();
        for (int $i0 = 0; $i0 < $r0.length(); $i0++) {
            JSONObject $r2 = $r0.getJSONObject($i0);
            String[] $r4 = $r2.getString("property").split(":");
            if ($r4[0].equals(META_TAG_PREFIX)) {
                Map $r5 = $r1;
                int $i1 = 1;
                while ($i1 < $r4.length) {
                    List $r7 = (List) $r5.get($r4[$i1]);
                    if ($r7 == null) {
                        $r7 = r0;
                        ArrayList arrayList = new ArrayList();
                        $r5.put($r4[$i1], $r7);
                    }
                    if ($r7.size() > 0) {
                        $r5 = (Map) $r7.get($r7.size() - 1);
                    } else {
                        $r5 = null;
                    }
                    if ($r5 == null || $i1 == $r4.length - 1) {
                        $r5 = r8;
                        r8 = new HashMap();
                        $r7.add($r5);
                    }
                    $i1++;
                }
                if ($r2.has("content")) {
                    if ($r2.isNull("content")) {
                        $r5.put(KEY_AL_VALUE, null);
                    } else {
                        $r5.put(KEY_AL_VALUE, $r2.getString("content"));
                    }
                }
            }
        }
        return $r1;
    }

    private static List<Map<String, Object>> getAlList(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;>;"}) Map<String, Object> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;>;"}) String $r1) throws  {
        List $r3 = (List) $r0.get($r1);
        if ($r3 == null) {
            return Collections.emptyList();
        }
        return $r3;
    }

    private static AppLink makeAppLinkFromAlData(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;", "Landroid/net/Uri;", ")", "Lbolts/AppLink;"}) Map<String, Object> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;", "Landroid/net/Uri;", ")", "Lbolts/AppLink;"}) Uri $r1) throws  {
        Uri $r12;
        ArrayList $r3 = new ArrayList();
        List $r5 = (List) $r0.get(KEY_ANDROID);
        if ($r5 == null) {
            $r5 = Collections.emptyList();
        }
        for (Map $r7 : $r5) {
            $r5 = getAlList($r7, "url");
            List $r8 = getAlList($r7, KEY_PACKAGE);
            List $r9 = getAlList($r7, KEY_CLASS);
            List $r10 = getAlList($r7, "app_name");
            int $i0 = Math.max($r5.size(), Math.max($r8.size(), Math.max($r9.size(), $r10.size())));
            int $i1 = 0;
            while ($i1 < $i0) {
                Object $r4;
                $r12 = tryCreateUrl((String) ($r5.size() > $i1 ? ((Map) $r5.get($i1)).get(KEY_AL_VALUE) : null));
                String $r11 = (String) ($r8.size() > $i1 ? ((Map) $r8.get($i1)).get(KEY_AL_VALUE) : null);
                if ($r9.size() > $i1) {
                    $r4 = ((Map) $r9.get($i1)).get(KEY_AL_VALUE);
                } else {
                    $r4 = null;
                }
                $r3.add(new Target($r11, (String) $r4, $r12, (String) ($r10.size() > $i1 ? ((Map) $r10.get($i1)).get(KEY_AL_VALUE) : null)));
                $i1++;
            }
        }
        $r12 = $r1;
        $r5 = (List) $r0.get("web");
        if ($r5 != null && $r5.size() > 0) {
            Map $r02 = (Map) $r5.get(0);
            $r5 = (List) $r02.get("url");
            $r8 = (List) $r02.get(KEY_SHOULD_FALLBACK);
            if ($r8 != null && $r8.size() > 0) {
                if (Arrays.asList(new String[]{"no", "false", AppEventsConstants.EVENT_PARAM_VALUE_NO}).contains(((String) ((Map) $r8.get(0)).get(KEY_AL_VALUE)).toLowerCase())) {
                    $r12 = null;
                }
            }
            if (!($r12 == null || $r5 == null || $r5.size() <= 0)) {
                $r12 = tryCreateUrl((String) ((Map) $r5.get(0)).get(KEY_AL_VALUE));
            }
        }
        return new AppLink($r1, $r3, $r12);
    }

    private static Uri tryCreateUrl(String $r0) throws  {
        return $r0 == null ? null : Uri.parse($r0);
    }
}
