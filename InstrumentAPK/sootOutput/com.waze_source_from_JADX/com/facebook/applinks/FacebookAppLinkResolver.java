package com.facebook.applinks;

import android.net.Uri;
import android.os.Bundle;
import bolts.AppLink;
import bolts.AppLink.Target;
import bolts.AppLinkResolver;
import bolts.Continuation;
import bolts.Task;
import bolts.Task.TaskCompletionSource;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequest.Callback;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookAppLinkResolver implements AppLinkResolver {
    private static final String APP_LINK_ANDROID_TARGET_KEY = "android";
    private static final String APP_LINK_KEY = "app_links";
    private static final String APP_LINK_TARGET_APP_NAME_KEY = "app_name";
    private static final String APP_LINK_TARGET_CLASS_KEY = "class";
    private static final String APP_LINK_TARGET_PACKAGE_KEY = "package";
    private static final String APP_LINK_TARGET_SHOULD_FALLBACK_KEY = "should_fallback";
    private static final String APP_LINK_TARGET_URL_KEY = "url";
    private static final String APP_LINK_WEB_TARGET_KEY = "web";
    private final HashMap<Uri, AppLink> cachedAppLinks = new HashMap();

    private static android.net.Uri getWebFallbackUriFromJson(android.net.Uri r7, org.json.JSONObject r8) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0021 in list []
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
        r0 = "web";	 Catch:{ JSONException -> 0x0024 }
        r8 = r8.getJSONObject(r0);	 Catch:{ JSONException -> 0x0024 }
        r0 = "should_fallback";	 Catch:{ JSONException -> 0x0024 }
        r2 = 1;	 Catch:{ JSONException -> 0x0024 }
        r1 = tryGetBooleanFromJson(r8, r0, r2);	 Catch:{ JSONException -> 0x0024 }
        if (r1 != 0) goto L_0x0012;	 Catch:{ JSONException -> 0x0024 }
    L_0x0010:
        r3 = 0;
        return r3;
    L_0x0012:
        r0 = "url";	 Catch:{ JSONException -> 0x0024 }
        r3 = 0;	 Catch:{ JSONException -> 0x0024 }
        r4 = tryGetStringFromJson(r8, r0, r3);	 Catch:{ JSONException -> 0x0024 }
        r5 = 0;
        if (r4 == 0) goto L_0x0021;	 Catch:{ JSONException -> 0x0024 }
    L_0x001d:
        r5 = android.net.Uri.parse(r4);	 Catch:{ JSONException -> 0x0024 }
    L_0x0021:
        if (r5 != 0) goto L_0x0026;
    L_0x0023:
        return r7;
    L_0x0024:
        r6 = move-exception;
        return r7;
    L_0x0026:
        return r5;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.applinks.FacebookAppLinkResolver.getWebFallbackUriFromJson(android.net.Uri, org.json.JSONObject):android.net.Uri");
    }

    public Task<AppLink> getAppLinkFromUrlInBackground(@Signature({"(", "Landroid/net/Uri;", ")", "Lbolts/Task", "<", "Lbolts/AppLink;", ">;"}) final Uri $r1) throws  {
        ArrayList $r2 = new ArrayList();
        $r2.add($r1);
        return getAppLinkFromUrlsInBackground($r2).onSuccess(new Continuation<Map<Uri, AppLink>, AppLink>() {
            public AppLink then(@Signature({"(", "Lbolts/Task", "<", "Ljava/util/Map", "<", "Landroid/net/Uri;", "Lbolts/AppLink;", ">;>;)", "Lbolts/AppLink;"}) Task<Map<Uri, AppLink>> $r1) throws Exception {
                return (AppLink) ((Map) $r1.getResult()).get($r1);
            }
        });
    }

    public Task<Map<Uri, AppLink>> getAppLinkFromUrlsInBackground(@Signature({"(", "Ljava/util/List", "<", "Landroid/net/Uri;", ">;)", "Lbolts/Task", "<", "Ljava/util/Map", "<", "Landroid/net/Uri;", "Lbolts/AppLink;", ">;>;"}) List<Uri> $r1) throws  {
        final HashMap $r4 = new HashMap();
        final HashSet $r6 = new HashSet();
        StringBuilder $r5 = new StringBuilder();
        for (Uri $r9 : $r1) {
            synchronized (this.cachedAppLinks) {
                AppLink appLink = (AppLink) this.cachedAppLinks.get($r9);
            }
            if (appLink != null) {
                $r4.put($r9, appLink);
            } else {
                if (!$r6.isEmpty()) {
                    $r5.append(',');
                }
                $r5.append($r9.toString());
                $r6.add($r9);
            }
        }
        if ($r6.isEmpty()) {
            return Task.forResult($r4);
        }
        TaskCompletionSource $r16 = Task.create();
        Bundle bundle = new Bundle();
        bundle.putString("ids", $r5.toString());
        bundle.putString(GraphRequest.FIELDS_PARAM, String.format("%s.fields(%s,%s)", new Object[]{APP_LINK_KEY, APP_LINK_ANDROID_TARGET_KEY, "web"}));
        final TaskCompletionSource taskCompletionSource = $r16;
        new GraphRequest(AccessToken.getCurrentAccessToken(), "", bundle, null, new Callback() {
            public void onCompleted(com.facebook.GraphResponse r31) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:21:0x007b in {3, 7, 20, 22, 25, 26, 38, 42} preds:[]
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
                r0 = r31;
                r3 = r0.getError();
                if (r3 == 0) goto L_0x0014;
            L_0x0008:
                r0 = r30;
                r4 = r2;
                r5 = r3.getException();
                r4.setError(r5);
                return;
            L_0x0014:
                r0 = r31;
                r6 = r0.getJSONObject();
                if (r6 != 0) goto L_0x0028;
            L_0x001c:
                r0 = r30;
                r4 = r2;
                r0 = r30;
                r7 = r6;
                r4.setResult(r7);
                return;
            L_0x0028:
                r0 = r30;
                r8 = r7;
                r9 = r8.iterator();
            L_0x0030:
                r10 = r9.hasNext();
                if (r10 == 0) goto L_0x00d4;
            L_0x0036:
                r11 = r9.next();
                r13 = r11;
                r13 = (android.net.Uri) r13;
                r12 = r13;
                r14 = r12.toString();
                r10 = r6.has(r14);
                if (r10 == 0) goto L_0x0030;
            L_0x0048:
                r14 = r12.toString();	 Catch:{ JSONException -> 0x00d2 }
                r15 = r6.getJSONObject(r14);	 Catch:{ JSONException -> 0x00d2 }
                r16 = "app_links";	 Catch:{ JSONException -> 0x00d2 }
                r0 = r16;	 Catch:{ JSONException -> 0x00d2 }
                r15 = r15.getJSONObject(r0);	 Catch:{ JSONException -> 0x00d2 }
                r16 = "android";	 Catch:{ JSONException -> 0x00d2 }
                r0 = r16;	 Catch:{ JSONException -> 0x00d2 }
                r17 = r15.getJSONArray(r0);	 Catch:{ JSONException -> 0x00d2 }
                r0 = r17;	 Catch:{ JSONException -> 0x00d2 }
                r18 = r0.length();	 Catch:{ JSONException -> 0x00d2 }
                r19 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x00d2 }
                r0 = r19;	 Catch:{ JSONException -> 0x00d2 }
                r1 = r18;	 Catch:{ JSONException -> 0x00d2 }
                r0.<init>(r1);	 Catch:{ JSONException -> 0x00d2 }
                r20 = 0;
            L_0x0071:
                r0 = r20;	 Catch:{ JSONException -> 0x00d2 }
                r1 = r18;	 Catch:{ JSONException -> 0x00d2 }
                if (r0 >= r1) goto L_0x0099;	 Catch:{ JSONException -> 0x00d2 }
            L_0x0077:
                goto L_0x007f;	 Catch:{ JSONException -> 0x00d2 }
            L_0x0078:
                goto L_0x0030;	 Catch:{ JSONException -> 0x00d2 }
                goto L_0x007f;	 Catch:{ JSONException -> 0x00d2 }
            L_0x007c:
                goto L_0x0030;	 Catch:{ JSONException -> 0x00d2 }
            L_0x007f:
                r0 = r17;	 Catch:{ JSONException -> 0x00d2 }
                r1 = r20;	 Catch:{ JSONException -> 0x00d2 }
                r21 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x00d2 }
                r0 = r21;	 Catch:{ JSONException -> 0x00d2 }
                r22 = com.facebook.applinks.FacebookAppLinkResolver.getAndroidTargetFromJson(r0);	 Catch:{ JSONException -> 0x00d2 }
                if (r22 == 0) goto L_0x0096;	 Catch:{ JSONException -> 0x00d2 }
            L_0x008f:
                r0 = r19;	 Catch:{ JSONException -> 0x00d2 }
                r1 = r22;	 Catch:{ JSONException -> 0x00d2 }
                r0.add(r1);	 Catch:{ JSONException -> 0x00d2 }
            L_0x0096:
                r20 = r20 + 1;
                goto L_0x0071;
            L_0x0099:
                r23 = com.facebook.applinks.FacebookAppLinkResolver.getWebFallbackUriFromJson(r12, r15);	 Catch:{ JSONException -> 0x00d2 }
                r24 = new bolts.AppLink;	 Catch:{ JSONException -> 0x00d2 }
                r0 = r24;	 Catch:{ JSONException -> 0x00d2 }
                r1 = r19;	 Catch:{ JSONException -> 0x00d2 }
                r2 = r23;	 Catch:{ JSONException -> 0x00d2 }
                r0.<init>(r12, r1, r2);	 Catch:{ JSONException -> 0x00d2 }
                r0 = r30;	 Catch:{ JSONException -> 0x00d2 }
                r7 = r6;	 Catch:{ JSONException -> 0x00d2 }
                r0 = r24;	 Catch:{ JSONException -> 0x00d2 }
                r7.put(r12, r0);	 Catch:{ JSONException -> 0x00d2 }
                r0 = r30;	 Catch:{ JSONException -> 0x00d2 }
                r0 = com.facebook.applinks.FacebookAppLinkResolver.this;	 Catch:{ JSONException -> 0x00d2 }
                r25 = r0;	 Catch:{ JSONException -> 0x00d2 }
                r26 = r0.cachedAppLinks;	 Catch:{ JSONException -> 0x00d2 }
                monitor-enter(r26);
                r0 = r30;	 Catch:{ Throwable -> 0x00cf }
                r0 = com.facebook.applinks.FacebookAppLinkResolver.this;	 Catch:{ Throwable -> 0x00cf }
                r25 = r0;	 Catch:{ Throwable -> 0x00cf }
                r27 = r0.cachedAppLinks;	 Catch:{ Throwable -> 0x00cf }
                r0 = r27;	 Catch:{ Throwable -> 0x00cf }
                r1 = r24;	 Catch:{ Throwable -> 0x00cf }
                r0.put(r12, r1);	 Catch:{ Throwable -> 0x00cf }
                monitor-exit(r26);	 Catch:{ Throwable -> 0x00cf }
                goto L_0x0078;
            L_0x00cf:
                r28 = move-exception;	 Catch:{ Throwable -> 0x00cf }
                monitor-exit(r26);	 Catch:{ Throwable -> 0x00cf }
                throw r28;	 Catch:{ JSONException -> 0x00d2 }
            L_0x00d2:
                r29 = move-exception;
                goto L_0x007c;
            L_0x00d4:
                r0 = r30;
                r4 = r2;
                r0 = r30;
                r7 = r6;
                r4.setResult(r7);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.facebook.applinks.FacebookAppLinkResolver.2.onCompleted(com.facebook.GraphResponse):void");
            }
        }).executeAsync();
        return $r16.getTask();
    }

    private static Target getAndroidTargetFromJson(JSONObject $r0) throws  {
        String $r2 = tryGetStringFromJson($r0, APP_LINK_TARGET_PACKAGE_KEY, null);
        if ($r2 == null) {
            return null;
        }
        String $r3 = tryGetStringFromJson($r0, APP_LINK_TARGET_CLASS_KEY, null);
        String $r4 = tryGetStringFromJson($r0, "app_name", null);
        String $r5 = tryGetStringFromJson($r0, "url", null);
        Uri $r6 = null;
        if ($r5 != null) {
            $r6 = Uri.parse($r5);
        }
        return new Target($r2, $r3, $r6, $r4);
    }

    private static String tryGetStringFromJson(JSONObject $r0, String $r1, String $r3) throws  {
        try {
            return $r0.getString($r1);
        } catch (JSONException e) {
            return $r3;
        }
    }

    private static boolean tryGetBooleanFromJson(JSONObject $r0, String $r1, boolean $z0) throws  {
        try {
            $z0 = $r0.getBoolean($r1);
            return $z0;
        } catch (JSONException e) {
            return $z0;
        }
    }
}
