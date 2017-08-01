package android.support.v4.app;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.InboxStyle;
import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v4.app.NotificationCompatBase.Action.Factory;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;
import android.util.Log;
import android.util.SparseArray;
import dalvik.annotation.Signature;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class NotificationCompatJellybean {
    static final String EXTRA_ACTION_EXTRAS = "android.support.actionExtras";
    static final String EXTRA_GROUP_KEY = "android.support.groupKey";
    static final String EXTRA_GROUP_SUMMARY = "android.support.isGroupSummary";
    static final String EXTRA_LOCAL_ONLY = "android.support.localOnly";
    static final String EXTRA_REMOTE_INPUTS = "android.support.remoteInputs";
    static final String EXTRA_SORT_KEY = "android.support.sortKey";
    static final String EXTRA_USE_SIDE_CHANNEL = "android.support.useSideChannel";
    private static final String KEY_ACTION_INTENT = "actionIntent";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_ICON = "icon";
    private static final String KEY_REMOTE_INPUTS = "remoteInputs";
    private static final String KEY_TITLE = "title";
    public static final String TAG = "NotificationCompat";
    private static Class<?> sActionClass;
    private static Field sActionIconField;
    private static Field sActionIntentField;
    private static Field sActionTitleField;
    private static boolean sActionsAccessFailed;
    private static Field sActionsField;
    private static final Object sActionsLock = new Object();
    private static Field sExtrasField;
    private static boolean sExtrasFieldAccessFailed;
    private static final Object sExtrasLock = new Object();

    public static class Builder implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor {
        private android.app.Notification.Builder f3b;
        private List<Bundle> mActionExtrasList;
        private final Bundle mExtras;

        public Builder(android.content.Context r19, android.app.Notification r20, java.lang.CharSequence r21, java.lang.CharSequence r22, java.lang.CharSequence r23, android.widget.RemoteViews r24, int r25, android.app.PendingIntent r26, android.app.PendingIntent r27, android.graphics.Bitmap r28, int r29, int r30, boolean r31, boolean r32, int r33, java.lang.CharSequence r34, boolean r35, android.os.Bundle r36, java.lang.String r37, boolean r38, java.lang.String r39) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:24:0x0117 in {2, 5, 8, 11, 13, 15, 17, 20, 25, 26, 31, 34, 39, 40} preds:[]
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
            r18 = this;
            r0 = r18;
            r0.<init>();
            r3 = new java.util.ArrayList;
            r3.<init>();
            r0 = r18;
            r0.mActionExtrasList = r3;
            r4 = new android.app.Notification$Builder;
            r0 = r19;
            r4.<init>(r0);
            r0 = r20;
            r5 = r0.when;
            r4 = r4.setWhen(r5);
            r0 = r20;
            r7 = r0.icon;
            r0 = r20;
            r8 = r0.iconLevel;
            r4 = r4.setSmallIcon(r7, r8);
            r0 = r20;
            r9 = r0.contentView;
            r4 = r4.setContent(r9);
            r0 = r20;
            r10 = r0.tickerText;
            r0 = r24;
            r4 = r4.setTicker(r10, r0);
            r0 = r20;
            r11 = r0.sound;
            r0 = r20;
            r7 = r0.audioStreamType;
            r4 = r4.setSound(r11, r7);
            r0 = r20;
            r12 = r0.vibrate;
            r4 = r4.setVibrate(r12);
            r0 = r20;
            r8 = r0.ledARGB;
            r0 = r20;
            r13 = r0.ledOnMS;
            r0 = r20;
            r7 = r0.ledOffMS;
            r4 = r4.setLights(r8, r13, r7);
            r0 = r20;
            r7 = r0.flags;
            r7 = r7 & 2;
            if (r7 == 0) goto L_0x0168;
        L_0x0067:
            r14 = 1;
        L_0x0068:
            r4 = r4.setOngoing(r14);
            r0 = r20;
            r7 = r0.flags;
            r7 = r7 & 8;
            if (r7 == 0) goto L_0x016a;
        L_0x0074:
            r14 = 1;
        L_0x0075:
            r4 = r4.setOnlyAlertOnce(r14);
            r0 = r20;
            r7 = r0.flags;
            r7 = r7 & 16;
            if (r7 == 0) goto L_0x016c;
        L_0x0081:
            r14 = 1;
        L_0x0082:
            r4 = r4.setAutoCancel(r14);
            r0 = r20;
            r7 = r0.defaults;
            r4 = r4.setDefaults(r7);
            r0 = r21;
            r4 = r4.setContentTitle(r0);
            r0 = r22;
            r4 = r4.setContentText(r0);
            r0 = r34;
            r4 = r4.setSubText(r0);
            r0 = r23;
            r4 = r4.setContentInfo(r0);
            r0 = r26;
            r4 = r4.setContentIntent(r0);
            r0 = r20;
            r0 = r0.deleteIntent;
            r26 = r0;
            r4 = r4.setDeleteIntent(r0);
            r0 = r20;
            r7 = r0.flags;
            r7 = r7 & 128;
            if (r7 == 0) goto L_0x016e;
        L_0x00be:
            r14 = 1;
        L_0x00bf:
            r0 = r27;
            r4 = r4.setFullScreenIntent(r0, r14);
            r0 = r28;
            r4 = r4.setLargeIcon(r0);
            r0 = r25;
            r4 = r4.setNumber(r0);
            r0 = r32;
            r4 = r4.setUsesChronometer(r0);
            goto L_0x00db;
        L_0x00d8:
            goto L_0x0068;
        L_0x00db:
            r0 = r33;
            r4 = r4.setPriority(r0);
            goto L_0x00e5;
        L_0x00e2:
            goto L_0x0075;
        L_0x00e5:
            r0 = r29;
            r1 = r30;
            r2 = r31;
            r4 = r4.setProgress(r0, r1, r2);
            goto L_0x00f3;
        L_0x00f0:
            goto L_0x0082;
        L_0x00f3:
            r0 = r18;
            r0.f3b = r4;
            r15 = new android.os.Bundle;
            r15.<init>();
            r0 = r18;
            r0.mExtras = r15;
            if (r36 == 0) goto L_0x010b;
        L_0x0102:
            r0 = r18;
            r15 = r0.mExtras;
            r0 = r36;
            r15.putAll(r0);
        L_0x010b:
            if (r35 == 0) goto L_0x012c;
        L_0x010d:
            r0 = r18;
            r0 = r0.mExtras;
            r36 = r0;
            goto L_0x011b;
        L_0x0114:
            goto L_0x00d8;
            goto L_0x011b;
        L_0x0118:
            goto L_0x00bf;
        L_0x011b:
            r16 = "android.support.localOnly";
            r17 = 1;
            r0 = r36;
            r1 = r16;
            r2 = r17;
            r0.putBoolean(r1, r2);
            goto L_0x012c;
        L_0x0129:
            goto L_0x00e2;
        L_0x012c:
            if (r37 == 0) goto L_0x0154;
        L_0x012e:
            r0 = r18;
            r0 = r0.mExtras;
            r36 = r0;
            r16 = "android.support.groupKey";
            r0 = r36;
            r1 = r16;
            r2 = r37;
            r0.putString(r1, r2);
            if (r38 == 0) goto L_0x0170;
        L_0x0141:
            r0 = r18;
            r0 = r0.mExtras;
            r36 = r0;
            r16 = "android.support.isGroupSummary";
            r17 = 1;
            r0 = r36;
            r1 = r16;
            r2 = r17;
            r0.putBoolean(r1, r2);
        L_0x0154:
            if (r39 == 0) goto L_0x0184;
        L_0x0156:
            r0 = r18;
            r0 = r0.mExtras;
            r36 = r0;
            r16 = "android.support.sortKey";
            r0 = r36;
            r1 = r16;
            r2 = r39;
            r0.putString(r1, r2);
            return;
        L_0x0168:
            r14 = 0;
            goto L_0x0114;
        L_0x016a:
            r14 = 0;
            goto L_0x0129;
        L_0x016c:
            r14 = 0;
            goto L_0x00f0;
        L_0x016e:
            r14 = 0;
            goto L_0x0118;
        L_0x0170:
            r0 = r18;
            r0 = r0.mExtras;
            r36 = r0;
            r16 = "android.support.useSideChannel";
            r17 = 1;
            r0 = r36;
            r1 = r16;
            r2 = r17;
            r0.putBoolean(r1, r2);
            goto L_0x0154;
        L_0x0184:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompatJellybean.Builder.<init>(android.content.Context, android.app.Notification, java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence, android.widget.RemoteViews, int, android.app.PendingIntent, android.app.PendingIntent, android.graphics.Bitmap, int, int, boolean, boolean, int, java.lang.CharSequence, boolean, android.os.Bundle, java.lang.String, boolean, java.lang.String):void");
        }

        public void addAction(Action $r1) throws  {
            this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.f3b, $r1));
        }

        public android.app.Notification.Builder getBuilder() throws  {
            return this.f3b;
        }

        public Notification build() throws  {
            Notification $r3 = this.f3b.build();
            Bundle $r4 = NotificationCompatJellybean.getExtras($r3);
            Bundle $r1 = new Bundle(this.mExtras);
            for (String $r9 : this.mExtras.keySet()) {
                if ($r4.containsKey($r9)) {
                    $r1.remove($r9);
                }
            }
            $r4.putAll($r1);
            SparseArray $r11 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if ($r11 == null) {
                return $r3;
            }
            NotificationCompatJellybean.getExtras($r3).putSparseParcelableArray("android.support.actionExtras", $r11);
            return $r3;
        }
    }

    NotificationCompatJellybean() throws  {
    }

    public static void addBigTextStyle(NotificationBuilderWithBuilderAccessor $r0, CharSequence $r1, boolean $z0, CharSequence $r2, CharSequence $r3) throws  {
        BigTextStyle $r4 = new BigTextStyle($r0.getBuilder()).setBigContentTitle($r1).bigText($r3);
        if ($z0) {
            $r4.setSummaryText($r2);
        }
    }

    public static void addBigPictureStyle(NotificationBuilderWithBuilderAccessor $r0, CharSequence $r1, boolean $z0, CharSequence $r2, Bitmap $r3, Bitmap $r4, boolean $z1) throws  {
        BigPictureStyle $r5 = new BigPictureStyle($r0.getBuilder()).setBigContentTitle($r1).bigPicture($r3);
        if ($z1) {
            $r5.bigLargeIcon($r4);
        }
        if ($z0) {
            $r5.setSummaryText($r2);
        }
    }

    public static void addInboxStyle(@Signature({"(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Ljava/lang/CharSequence;", "Z", "Ljava/lang/CharSequence;", "Ljava/util/ArrayList", "<", "Ljava/lang/CharSequence;", ">;)V"}) NotificationBuilderWithBuilderAccessor $r0, @Signature({"(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Ljava/lang/CharSequence;", "Z", "Ljava/lang/CharSequence;", "Ljava/util/ArrayList", "<", "Ljava/lang/CharSequence;", ">;)V"}) CharSequence $r1, @Signature({"(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Ljava/lang/CharSequence;", "Z", "Ljava/lang/CharSequence;", "Ljava/util/ArrayList", "<", "Ljava/lang/CharSequence;", ">;)V"}) boolean $z0, @Signature({"(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Ljava/lang/CharSequence;", "Z", "Ljava/lang/CharSequence;", "Ljava/util/ArrayList", "<", "Ljava/lang/CharSequence;", ">;)V"}) CharSequence $r2, @Signature({"(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Ljava/lang/CharSequence;", "Z", "Ljava/lang/CharSequence;", "Ljava/util/ArrayList", "<", "Ljava/lang/CharSequence;", ">;)V"}) ArrayList<CharSequence> $r3) throws  {
        InboxStyle $r4 = new InboxStyle($r0.getBuilder()).setBigContentTitle($r1);
        if ($z0) {
            $r4.setSummaryText($r2);
        }
        Iterator $r6 = $r3.iterator();
        while ($r6.hasNext()) {
            $r4.addLine((CharSequence) $r6.next());
        }
    }

    public static SparseArray<Bundle> buildActionExtrasMap(@Signature({"(", "Ljava/util/List", "<", "Landroid/os/Bundle;", ">;)", "Landroid/util/SparseArray", "<", "Landroid/os/Bundle;", ">;"}) List<Bundle> $r0) throws  {
        SparseArray $r1 = null;
        int $i1 = $r0.size();
        for (int $i0 = 0; $i0 < $i1; $i0++) {
            Bundle $r3 = (Bundle) $r0.get($i0);
            if ($r3 != null) {
                if ($r1 == null) {
                    $r1 = new SparseArray();
                }
                $r1.put($i0, $r3);
            }
        }
        return $r1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.os.Bundle getExtras(android.app.Notification r15) throws  {
        /*
        r0 = sExtrasLock;
        monitor-enter(r0);
        r1 = sExtrasFieldAccessFailed;	 Catch:{ Throwable -> 0x004d }
        if (r1 == 0) goto L_0x000a;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x004d }
        r2 = 0;
        return r2;
    L_0x000a:
        r3 = sExtrasField;	 Catch:{ Throwable -> 0x004d }
        if (r3 != 0) goto L_0x0035;
    L_0x000e:
        r4 = android.app.Notification.class;
        r5 = "extras";
        r3 = r4.getDeclaredField(r5);	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r4 = android.os.Bundle.class;
        r6 = r3.getType();	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r1 = r4.isAssignableFrom(r6);	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        if (r1 != 0) goto L_0x002f;
    L_0x0022:
        r5 = "NotificationCompat";
        r7 = "Notification.extras field is not of type Bundle";
        android.util.Log.e(r5, r7);	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r8 = 1;
        sExtrasFieldAccessFailed = r8;	 Catch:{ Throwable -> 0x004d }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x004d }
        r2 = 0;
        return r2;
    L_0x002f:
        r8 = 1;
        r3.setAccessible(r8);	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        sExtrasField = r3;	 Catch:{ Throwable -> 0x004d }
    L_0x0035:
        r3 = sExtrasField;	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r9 = r3.get(r15);	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r11 = r9;
        r11 = (android.os.Bundle) r11;
        r10 = r11;
        if (r10 != 0) goto L_0x004b;
    L_0x0041:
        r10 = new android.os.Bundle;	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r10.<init>();	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r3 = sExtrasField;	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
        r3.set(r15, r10);	 Catch:{ IllegalAccessException -> 0x0050, NoSuchFieldException -> 0x005e }
    L_0x004b:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x004d }
        return r10;
    L_0x004d:
        r12 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x004d }
        throw r12;
    L_0x0050:
        r13 = move-exception;
        r5 = "NotificationCompat";
        r7 = "Unable to access notification extras";
        android.util.Log.e(r5, r7, r13);	 Catch:{ Throwable -> 0x004d }
    L_0x0058:
        r8 = 1;
        sExtrasFieldAccessFailed = r8;	 Catch:{ Throwable -> 0x004d }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x004d }
        r2 = 0;
        return r2;
    L_0x005e:
        r14 = move-exception;
        r5 = "NotificationCompat";
        r7 = "Unable to access notification extras";
        android.util.Log.e(r5, r7, r14);	 Catch:{ Throwable -> 0x004d }
        goto L_0x0058;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompatJellybean.getExtras(android.app.Notification):android.os.Bundle");
    }

    public static Action readAction(Factory $r0, RemoteInput.Factory $r1, int $i0, CharSequence $r2, PendingIntent $r3, Bundle $r4) throws  {
        RemoteInput[] $r5 = null;
        if ($r4 != null) {
            $r5 = RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle($r4, "android.support.remoteInputs"), $r1);
        }
        return $r0.build($i0, $r2, $r3, $r4, $r5);
    }

    public static Bundle writeActionAndGetExtras(android.app.Notification.Builder $r0, Action $r1) throws  {
        $r0.addAction($r1.getIcon(), $r1.getTitle(), $r1.getActionIntent());
        Bundle $r2 = new Bundle($r1.getExtras());
        if ($r1.getRemoteInputs() == null) {
            return $r2;
        }
        $r2.putParcelableArray("android.support.remoteInputs", RemoteInputCompatJellybean.toBundleArray($r1.getRemoteInputs()));
        return $r2;
    }

    public static int getActionCount(Notification $r0) throws  {
        int $i0;
        synchronized (sActionsLock) {
            Object[] $r2 = getActionObjectsLocked($r0);
            $i0 = $r2 != null ? $r2.length : 0;
        }
        return $i0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static android.support.v4.app.NotificationCompatBase.Action getAction(android.app.Notification r26, int r27, android.support.v4.app.NotificationCompatBase.Action.Factory r28, android.support.v4.app.RemoteInputCompatBase.RemoteInput.Factory r29) throws  {
        /*
        r6 = sActionsLock;
        monitor-enter(r6);
        r0 = r26;
        r7 = getActionObjectsLocked(r0);	 Catch:{ IllegalAccessException -> 0x0055 }
        r8 = r7[r27];	 Catch:{ Throwable -> 0x0069 }
        r9 = 0;
        r0 = r26;
        r10 = getExtras(r0);	 Catch:{ IllegalAccessException -> 0x0055 }
        if (r10 == 0) goto L_0x0026;
    L_0x0014:
        r12 = "android.support.actionExtras";
        r11 = r10.getSparseParcelableArray(r12);	 Catch:{ IllegalAccessException -> 0x0055 }
        if (r11 == 0) goto L_0x0026;
    L_0x001c:
        r0 = r27;
        r13 = r11.get(r0);	 Catch:{ IllegalAccessException -> 0x0055 }
        r14 = r13;
        r14 = (android.os.Bundle) r14;	 Catch:{ Throwable -> 0x0069 }
        r9 = r14;
    L_0x0026:
        r15 = sActionIconField;	 Catch:{ IllegalAccessException -> 0x0055 }
        r27 = r15.getInt(r8);	 Catch:{ IllegalAccessException -> 0x0055 }
        r15 = sActionTitleField;	 Catch:{ IllegalAccessException -> 0x0055 }
        r13 = r15.get(r8);	 Catch:{ IllegalAccessException -> 0x0055 }
        r17 = r13;
        r17 = (java.lang.CharSequence) r17;
        r16 = r17;
        r15 = sActionIntentField;	 Catch:{ IllegalAccessException -> 0x0055 }
        r8 = r15.get(r8);	 Catch:{ IllegalAccessException -> 0x0055 }
        r19 = r8;
        r19 = (android.app.PendingIntent) r19;	 Catch:{ IllegalAccessException -> 0x0055 }
        r18 = r19;
        r0 = r28;
        r1 = r29;
        r2 = r27;
        r3 = r16;
        r4 = r18;
        r5 = r9;
        r20 = readAction(r0, r1, r2, r3, r4, r5);	 Catch:{ IllegalAccessException -> 0x0055 }
        monitor-exit(r6);	 Catch:{ Throwable -> 0x0069 }
        return r20;
    L_0x0055:
        r21 = move-exception;
        r12 = "NotificationCompat";
        r22 = "Unable to access notification actions";
        r0 = r22;
        r1 = r21;
        android.util.Log.e(r12, r0, r1);	 Catch:{ Throwable -> 0x0069 }
        r23 = 1;
        sActionsAccessFailed = r23;	 Catch:{ Throwable -> 0x0069 }
        monitor-exit(r6);	 Catch:{ Throwable -> 0x0069 }
        r24 = 0;
        return r24;
    L_0x0069:
        r25 = move-exception;
        monitor-exit(r6);	 Catch:{ Throwable -> 0x0069 }
        throw r25;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompatJellybean.getAction(android.app.Notification, int, android.support.v4.app.NotificationCompatBase$Action$Factory, android.support.v4.app.RemoteInputCompatBase$RemoteInput$Factory):android.support.v4.app.NotificationCompatBase$Action");
    }

    private static Object[] getActionObjectsLocked(Notification $r0) throws  {
        synchronized (sActionsLock) {
            if (ensureActionReflectionReadyLocked()) {
                try {
                    Object[] $r3 = (Object[]) sActionsField.get($r0);
                    return $r3;
                } catch (IllegalAccessException $r1) {
                    Log.e(TAG, "Unable to access notification actions", $r1);
                    sActionsAccessFailed = true;
                    return null;
                }
            }
            return null;
        }
    }

    private static boolean ensureActionReflectionReadyLocked() throws  {
        boolean $z0 = true;
        if (sActionsAccessFailed) {
            return false;
        }
        if (sActionsField == null) {
            try {
                sActionClass = Class.forName("android.app.Notification$Action");
                sActionIconField = sActionClass.getDeclaredField(KEY_ICON);
                sActionTitleField = sActionClass.getDeclaredField("title");
                sActionIntentField = sActionClass.getDeclaredField(KEY_ACTION_INTENT);
                sActionsField = Notification.class.getDeclaredField("actions");
                sActionsField.setAccessible(true);
            } catch (ClassNotFoundException $r2) {
                Log.e(TAG, "Unable to access notification actions", $r2);
                sActionsAccessFailed = true;
            } catch (NoSuchFieldException $r3) {
                Log.e(TAG, "Unable to access notification actions", $r3);
                sActionsAccessFailed = true;
            }
        }
        if (sActionsAccessFailed) {
            $z0 = false;
        }
        return $z0;
    }

    public static Action[] getActionsFromParcelableArrayList(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;", "Landroid/support/v4/app/NotificationCompatBase$Action$Factory;", "Landroid/support/v4/app/RemoteInputCompatBase$RemoteInput$Factory;", ")[", "Landroid/support/v4/app/NotificationCompatBase$Action;"}) ArrayList<Parcelable> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;", "Landroid/support/v4/app/NotificationCompatBase$Action$Factory;", "Landroid/support/v4/app/RemoteInputCompatBase$RemoteInput$Factory;", ")[", "Landroid/support/v4/app/NotificationCompatBase$Action;"}) Factory $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;", "Landroid/support/v4/app/NotificationCompatBase$Action$Factory;", "Landroid/support/v4/app/RemoteInputCompatBase$RemoteInput$Factory;", ")[", "Landroid/support/v4/app/NotificationCompatBase$Action;"}) RemoteInput.Factory $r2) throws  {
        if ($r0 == null) {
            return null;
        }
        Action[] $r3 = $r1.newArray($r0.size());
        for (int $i0 = 0; $i0 < $r3.length; $i0++) {
            $r3[$i0] = getActionFromBundle((Bundle) $r0.get($i0), $r1, $r2);
        }
        return $r3;
    }

    private static Action getActionFromBundle(Bundle $r0, Factory $r1, RemoteInput.Factory $r2) throws  {
        return $r1.build($r0.getInt(KEY_ICON), $r0.getCharSequence("title"), (PendingIntent) $r0.getParcelable(KEY_ACTION_INTENT), $r0.getBundle(KEY_EXTRAS), RemoteInputCompatJellybean.fromBundleArray(BundleUtil.getBundleArrayFromBundle($r0, KEY_REMOTE_INPUTS), $r2));
    }

    public static ArrayList<Parcelable> getParcelableArrayListForActions(@Signature({"([", "Landroid/support/v4/app/NotificationCompatBase$Action;", ")", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;"}) Action[] $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        ArrayList $r3 = new ArrayList($r0.length);
        for (Action $r1 : $r0) {
            $r3.add(getBundleForAction($r1));
        }
        return $r3;
    }

    private static Bundle getBundleForAction(Action $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putInt(KEY_ICON, $r0.getIcon());
        $r1.putCharSequence("title", $r0.getTitle());
        $r1.putParcelable(KEY_ACTION_INTENT, $r0.getActionIntent());
        $r1.putBundle(KEY_EXTRAS, $r0.getExtras());
        $r1.putParcelableArray(KEY_REMOTE_INPUTS, RemoteInputCompatJellybean.toBundleArray($r0.getRemoteInputs()));
        return $r1;
    }

    public static boolean getLocalOnly(Notification $r0) throws  {
        return getExtras($r0).getBoolean("android.support.localOnly");
    }

    public static String getGroup(Notification $r0) throws  {
        return getExtras($r0).getString("android.support.groupKey");
    }

    public static boolean isGroupSummary(Notification $r0) throws  {
        return getExtras($r0).getBoolean("android.support.isGroupSummary");
    }

    public static String getSortKey(Notification $r0) throws  {
        return getExtras($r0).getString("android.support.sortKey");
    }
}
