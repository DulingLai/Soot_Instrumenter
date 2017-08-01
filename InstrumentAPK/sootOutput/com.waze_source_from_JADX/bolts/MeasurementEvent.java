package bolts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import dalvik.annotation.Signature;
import java.lang.reflect.Method;
import java.util.Map;

public class MeasurementEvent {
    public static final String APP_LINK_NAVIGATE_IN_EVENT_NAME = "al_nav_in";
    public static final String APP_LINK_NAVIGATE_OUT_EVENT_NAME = "al_nav_out";
    public static final String MEASUREMENT_EVENT_ARGS_KEY = "event_args";
    public static final String MEASUREMENT_EVENT_NAME_KEY = "event_name";
    public static final String MEASUREMENT_EVENT_NOTIFICATION_NAME = "com.parse.bolts.measurement_event";
    private Context appContext;
    private Bundle args;
    private String name;

    private static android.os.Bundle getApplinkLogData(android.content.Context r21, java.lang.String r22, android.os.Bundle r23, android.content.Intent r24) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:53:0x0196 in {2, 6, 9, 12, 22, 27, 29, 31, 36, 37, 51, 54, 56} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r2 = new android.os.Bundle;
        r2.<init>();
        r0 = r21;
        r3 = r0.getPackageManager();
        r0 = r24;
        r4 = r0.resolveActivity(r3);
        if (r4 == 0) goto L_0x001c;
    L_0x0013:
        r5 = r4.getShortClassName();
        r6 = "class";
        r2.putString(r6, r5);
    L_0x001c:
        r5 = "al_nav_out";
        r0 = r22;
        r7 = r5.equals(r0);
        if (r7 == 0) goto L_0x00d5;
    L_0x0026:
        if (r4 == 0) goto L_0x0033;
    L_0x0028:
        r22 = r4.getPackageName();
        r6 = "package";
        r0 = r22;
        r2.putString(r6, r0);
    L_0x0033:
        r0 = r24;
        r8 = r0.getData();
        if (r8 == 0) goto L_0x004c;
    L_0x003b:
        r0 = r24;
        r8 = r0.getData();
        r22 = r8.toString();
        r6 = "outputURL";
        r0 = r22;
        r2.putString(r6, r0);
    L_0x004c:
        r0 = r24;
        r22 = r0.getScheme();
        if (r22 == 0) goto L_0x0061;
    L_0x0054:
        r0 = r24;
        r22 = r0.getScheme();
        r6 = "outputURLScheme";
        r0 = r22;
        r2.putString(r6, r0);
    L_0x0061:
        r0 = r23;
        r9 = r0.keySet();
        r10 = r9.iterator();
    L_0x006b:
        r7 = r10.hasNext();
        if (r7 == 0) goto L_0x01a0;
    L_0x0071:
        r11 = r10.next();
        r12 = r11;
        r12 = (java.lang.String) r12;
        r22 = r12;
        r0 = r23;
        r1 = r22;
        r11 = r0.get(r1);
        r7 = r11 instanceof android.os.Bundle;
        if (r7 == 0) goto L_0x0166;
    L_0x0086:
        r14 = r11;
        r14 = (android.os.Bundle) r14;
        r13 = r14;
        r9 = r13.keySet();
        r15 = r9.iterator();
    L_0x0094:
        r7 = r15.hasNext();
        if (r7 == 0) goto L_0x006b;
    L_0x009a:
        r16 = r15.next();
        r18 = r16;
        r18 = (java.lang.String) r18;
        r17 = r18;
        r19 = r11;
        r19 = (android.os.Bundle) r19;
        r13 = r19;
        r0 = r17;
        r16 = r13.get(r0);
        goto L_0x00b4;
    L_0x00b1:
        goto L_0x0061;
    L_0x00b4:
        r0 = r16;
        r5 = objectToJSONString(r0);
        r6 = "referer_app_link";
        r0 = r22;
        r7 = r0.equals(r6);
        if (r7 == 0) goto L_0x013b;
    L_0x00c4:
        r6 = "url";
        r0 = r17;
        r7 = r0.equalsIgnoreCase(r6);
        if (r7 == 0) goto L_0x011a;
    L_0x00cf:
        r6 = "refererURL";
        r2.putString(r6, r5);
        goto L_0x0094;
    L_0x00d5:
        r5 = "al_nav_in";
        goto L_0x00db;
    L_0x00d8:
        goto L_0x0094;
    L_0x00db:
        r0 = r22;
        r7 = r5.equals(r0);
        goto L_0x00e5;
    L_0x00e2:
        goto L_0x0094;
    L_0x00e5:
        if (r7 == 0) goto L_0x0061;
    L_0x00e7:
        r0 = r24;
        r8 = r0.getData();
        if (r8 == 0) goto L_0x0104;
    L_0x00ef:
        r0 = r24;
        r8 = r0.getData();
        r22 = r8.toString();
        goto L_0x00fd;
    L_0x00fa:
        goto L_0x0094;
    L_0x00fd:
        r6 = "inputURL";
        r0 = r22;
        r2.putString(r6, r0);
    L_0x0104:
        r0 = r24;
        r22 = r0.getScheme();
        if (r22 == 0) goto L_0x0061;
    L_0x010c:
        r0 = r24;
        r22 = r0.getScheme();
        r6 = "inputURLScheme";
        r0 = r22;
        r2.putString(r6, r0);
        goto L_0x00b1;
    L_0x011a:
        r6 = "app_name";
        r0 = r17;
        r7 = r0.equalsIgnoreCase(r6);
        if (r7 == 0) goto L_0x012a;
    L_0x0124:
        r6 = "refererAppName";
        r2.putString(r6, r5);
        goto L_0x00d8;
    L_0x012a:
        r6 = "package";
        r0 = r17;
        r7 = r0.equalsIgnoreCase(r6);
        if (r7 == 0) goto L_0x013b;
    L_0x0134:
        r6 = "sourceApplication";
        r2.putString(r6, r5);
        goto L_0x00e2;
    L_0x013b:
        r20 = new java.lang.StringBuilder;
        r0 = r20;
        r0.<init>();
        r0 = r20;
        r1 = r22;
        r20 = r0.append(r1);
        r6 = "/";
        r0 = r20;
        r20 = r0.append(r6);
        r0 = r20;
        r1 = r17;
        r20 = r0.append(r1);
        r0 = r20;
        r17 = r0.toString();
        r0 = r17;
        r2.putString(r0, r5);
        goto L_0x00fa;
    L_0x0166:
        r5 = objectToJSONString(r11);
        r6 = "target_url";
        r0 = r22;
        r7 = r0.equals(r6);
        if (r7 == 0) goto L_0x019a;
    L_0x0175:
        r8 = android.net.Uri.parse(r5);
        r22 = r8.toString();
        r6 = "targetURL";
        r0 = r22;
        r2.putString(r6, r0);
        r22 = r8.getHost();
        goto L_0x018d;
    L_0x018a:
        goto L_0x006b;
    L_0x018d:
        r6 = "targetURLHost";
        r0 = r22;
        r2.putString(r6, r0);
        goto L_0x018a;
        goto L_0x019a;
    L_0x0197:
        goto L_0x006b;
    L_0x019a:
        r0 = r22;
        r2.putString(r0, r5);
        goto L_0x0197;
    L_0x01a0:
        return r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.MeasurementEvent.getApplinkLogData(android.content.Context, java.lang.String, android.os.Bundle, android.content.Intent):android.os.Bundle");
    }

    private static java.lang.String objectToJSONString(java.lang.Object r10) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0035 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        if (r10 != 0) goto L_0x0004;
    L_0x0002:
        r0 = 0;
        return r0;
    L_0x0004:
        r1 = r10 instanceof org.json.JSONArray;
        if (r1 != 0) goto L_0x000c;
    L_0x0008:
        r1 = r10 instanceof org.json.JSONObject;
        if (r1 == 0) goto L_0x0011;
    L_0x000c:
        r2 = r10.toString();
        return r2;
    L_0x0011:
        r1 = r10 instanceof java.util.Collection;
        if (r1 == 0) goto L_0x0023;
    L_0x0015:
        r3 = new org.json.JSONArray;
        r5 = r10;	 Catch:{ Exception -> 0x003a }
        r5 = (java.util.Collection) r5;	 Catch:{ Exception -> 0x003a }
        r4 = r5;	 Catch:{ Exception -> 0x003a }
        r3.<init>(r4);	 Catch:{ Exception -> 0x003a }
        r2 = r3.toString();	 Catch:{ Exception -> 0x003a }
        return r2;
    L_0x0023:
        r1 = r10 instanceof java.util.Map;
        if (r1 == 0) goto L_0x0035;	 Catch:{ Exception -> 0x003a }
    L_0x0027:
        r6 = new org.json.JSONObject;	 Catch:{ Exception -> 0x003a }
        r8 = r10;	 Catch:{ Exception -> 0x003a }
        r8 = (java.util.Map) r8;	 Catch:{ Exception -> 0x003a }
        r7 = r8;	 Catch:{ Exception -> 0x003a }
        r6.<init>(r7);	 Catch:{ Exception -> 0x003a }
        r2 = r6.toString();	 Catch:{ Exception -> 0x003a }
        return r2;
    L_0x0035:
        r2 = r10.toString();	 Catch:{ Exception -> 0x003a }
        return r2;
    L_0x003a:
        r9 = move-exception;
        r0 = 0;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: bolts.MeasurementEvent.objectToJSONString(java.lang.Object):java.lang.String");
    }

    static void sendBroadcastEvent(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Landroid/content/Intent;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Landroid/content/Intent;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Landroid/content/Intent;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Intent $r2, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "Landroid/content/Intent;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r3) throws  {
        Bundle $r5 = new Bundle();
        if ($r2 != null) {
            Bundle $r6 = AppLinks.getAppLinkData($r2);
            if ($r6 != null) {
                $r5 = getApplinkLogData($r0, $r1, $r6, $r2);
            } else {
                Uri $r12 = $r2.getData();
                if ($r12 != null) {
                    $r5.putString("intentData", $r12.toString());
                }
                $r6 = $r2.getExtras();
                if ($r6 != null) {
                    for (String $r10 : $r6.keySet()) {
                        $r5.putString($r10, objectToJSONString($r6.get($r10)));
                    }
                }
            }
        }
        if ($r3 != null) {
            for (String $r102 : $r3.keySet()) {
                $r5.putString($r102, (String) $r3.get($r102));
            }
        }
        new MeasurementEvent($r0, $r1, $r5).sendBroadcast();
    }

    private MeasurementEvent(Context $r1, String $r2, Bundle $r3) throws  {
        this.appContext = $r1.getApplicationContext();
        this.name = $r2;
        this.args = $r3;
    }

    private void sendBroadcast() throws  {
        if (this.name == null) {
            Log.d(getClass().getName(), "Event name is required");
        }
        try {
            Class $r4 = Class.forName("android.support.v4.content.LocalBroadcastManager");
            Method $r6 = $r4.getMethod("getInstance", new Class[]{Context.class});
            Method $r7 = $r4.getMethod("sendBroadcast", new Class[]{Intent.class});
            Object $r10 = $r6.invoke(null, new Object[]{this.appContext});
            Intent $r2 = new Intent(MEASUREMENT_EVENT_NOTIFICATION_NAME);
            $r2.putExtra(MEASUREMENT_EVENT_NAME_KEY, this.name);
            $r2.putExtra(MEASUREMENT_EVENT_ARGS_KEY, this.args);
            $r7.invoke($r10, new Object[]{$r2});
        } catch (Exception e) {
            Log.d(getClass().getName(), "LocalBroadcastManager in android support library is required to raise bolts event.");
        }
    }
}
