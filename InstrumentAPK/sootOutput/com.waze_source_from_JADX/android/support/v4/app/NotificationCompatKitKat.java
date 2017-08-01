package android.support.v4.app;

import android.app.Notification;
import android.os.Bundle;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v4.app.NotificationCompatBase.Action.Factory;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;
import android.util.SparseArray;
import java.util.List;

class NotificationCompatKitKat {

    public static class Builder implements NotificationBuilderWithActions, NotificationBuilderWithBuilderAccessor {
        private android.app.Notification.Builder f4b;
        private List<Bundle> mActionExtrasList;
        private Bundle mExtras;

        public Builder(@dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) android.content.Context r21, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) android.app.Notification r22, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) java.lang.CharSequence r23, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) java.lang.CharSequence r24, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) java.lang.CharSequence r25, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) android.widget.RemoteViews r26, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) int r27, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) android.app.PendingIntent r28, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) android.app.PendingIntent r29, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) android.graphics.Bitmap r30, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) int r31, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) int r32, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) boolean r33, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) boolean r34, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) boolean r35, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) int r36, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) java.lang.CharSequence r37, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) boolean r38, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) java.util.ArrayList<java.lang.String> r39, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) android.os.Bundle r40, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) java.lang.String r41, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) boolean r42, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Landroid/app/Notification;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Landroid/widget/RemoteViews;", "I", "Landroid/app/PendingIntent;", "Landroid/app/PendingIntent;", "Landroid/graphics/Bitmap;", "IIZZZI", "Ljava/lang/CharSequence;", "Z", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Ljava/lang/String;", "Z", "Ljava/lang/String;", ")V"}) java.lang.String r43) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:30:0x0195
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
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
            r20 = this;
            r0 = r20;
            r0.<init>();
            r3 = new java.util.ArrayList;
            r3.<init>();
            r0 = r20;
            r0.mActionExtrasList = r3;
            r4 = new android.app.Notification$Builder;
            r0 = r21;
            r4.<init>(r0);
            r0 = r22;
            r5 = r0.when;
            r4 = r4.setWhen(r5);
            r0 = r34;
            r4 = r4.setShowWhen(r0);
            r0 = r22;
            r7 = r0.icon;
            r0 = r22;
            r8 = r0.iconLevel;
            r4 = r4.setSmallIcon(r7, r8);
            r0 = r22;
            r9 = r0.contentView;
            r4 = r4.setContent(r9);
            r0 = r22;
            r10 = r0.tickerText;
            r0 = r26;
            r4 = r4.setTicker(r10, r0);
            r0 = r22;
            r11 = r0.sound;
            r0 = r22;
            r7 = r0.audioStreamType;
            r4 = r4.setSound(r11, r7);
            r0 = r22;
            r12 = r0.vibrate;
            r4 = r4.setVibrate(r12);
            r0 = r22;
            r8 = r0.ledARGB;
            r0 = r22;
            r13 = r0.ledOnMS;
            r0 = r22;
            r7 = r0.ledOffMS;
            r4 = r4.setLights(r8, r13, r7);
            r0 = r22;
            r7 = r0.flags;
            r7 = r7 & 2;
            if (r7 == 0) goto L_0x0199;
        L_0x006d:
            r34 = 1;
        L_0x006f:
            r0 = r34;
            r4 = r4.setOngoing(r0);
            r0 = r22;
            r7 = r0.flags;
            r7 = r7 & 8;
            if (r7 == 0) goto L_0x01a0;
        L_0x007d:
            r34 = 1;
        L_0x007f:
            r0 = r34;
            r4 = r4.setOnlyAlertOnce(r0);
            r0 = r22;
            r7 = r0.flags;
            r7 = r7 & 16;
            if (r7 == 0) goto L_0x01a7;
        L_0x008d:
            r34 = 1;
        L_0x008f:
            r0 = r34;
            r4 = r4.setAutoCancel(r0);
            r0 = r22;
            r7 = r0.defaults;
            r4 = r4.setDefaults(r7);
            r0 = r23;
            r4 = r4.setContentTitle(r0);
            r0 = r24;
            r4 = r4.setContentText(r0);
            r0 = r37;
            r4 = r4.setSubText(r0);
            r0 = r25;
            r4 = r4.setContentInfo(r0);
            r0 = r28;
            r4 = r4.setContentIntent(r0);
            r0 = r22;
            r0 = r0.deleteIntent;
            r28 = r0;
            r4 = r4.setDeleteIntent(r0);
            r0 = r22;
            r7 = r0.flags;
            r7 = r7 & 128;
            if (r7 == 0) goto L_0x01aa;
        L_0x00cd:
            r34 = 1;
        L_0x00cf:
            r0 = r29;
            r1 = r34;
            r4 = r4.setFullScreenIntent(r0, r1);
            r0 = r30;
            r4 = r4.setLargeIcon(r0);
            r0 = r27;
            r4 = r4.setNumber(r0);
            r0 = r35;
            r4 = r4.setUsesChronometer(r0);
            r0 = r36;
            r4 = r4.setPriority(r0);
            r0 = r31;
            r1 = r32;
            r2 = r33;
            r4 = r4.setProgress(r0, r1, r2);
            r0 = r20;
            r0.f4b = r4;
            r14 = new android.os.Bundle;
            r14.<init>();
            r0 = r20;
            r0.mExtras = r14;
            if (r40 == 0) goto L_0x0111;
        L_0x0108:
            r0 = r20;
            r14 = r0.mExtras;
            r0 = r40;
            r14.putAll(r0);
        L_0x0111:
            if (r39 == 0) goto L_0x0144;
        L_0x0113:
            r0 = r39;
            r33 = r0.isEmpty();
            if (r33 != 0) goto L_0x0144;
        L_0x011b:
            r0 = r20;
            r0 = r0.mExtras;
            r40 = r0;
            r0 = r39;
            r27 = r0.size();
            r0 = r27;
            r15 = new java.lang.String[r0];
            r0 = r39;
            r16 = r0.toArray(r15);
            r17 = r16;
            r17 = (java.lang.String[]) r17;
            r15 = r17;
            goto L_0x013b;
        L_0x0138:
            goto L_0x00cf;
        L_0x013b:
            r18 = "android.people";
            r0 = r40;
            r1 = r18;
            r0.putStringArray(r1, r15);
        L_0x0144:
            if (r38 == 0) goto L_0x0159;
        L_0x0146:
            r0 = r20;
            r0 = r0.mExtras;
            r40 = r0;
            r18 = "android.support.localOnly";
            r19 = 1;
            r0 = r40;
            r1 = r18;
            r2 = r19;
            r0.putBoolean(r1, r2);
        L_0x0159:
            if (r41 == 0) goto L_0x0181;
        L_0x015b:
            r0 = r20;
            r0 = r0.mExtras;
            r40 = r0;
            r18 = "android.support.groupKey";
            r0 = r40;
            r1 = r18;
            r2 = r41;
            r0.putString(r1, r2);
            if (r42 == 0) goto L_0x01ad;
        L_0x016e:
            r0 = r20;
            r0 = r0.mExtras;
            r40 = r0;
            r18 = "android.support.isGroupSummary";
            r19 = 1;
            r0 = r40;
            r1 = r18;
            r2 = r19;
            r0.putBoolean(r1, r2);
        L_0x0181:
            if (r43 == 0) goto L_0x01c1;
        L_0x0183:
            r0 = r20;
            r0 = r0.mExtras;
            r40 = r0;
            r18 = "android.support.sortKey";
            r0 = r40;
            r1 = r18;
            r2 = r43;
            r0.putString(r1, r2);
            return;
            goto L_0x0199;
        L_0x0196:
            goto L_0x006f;
        L_0x0199:
            r34 = 0;
            goto L_0x0196;
            goto L_0x01a0;
        L_0x019d:
            goto L_0x007f;
        L_0x01a0:
            r34 = 0;
            goto L_0x019d;
            goto L_0x01a7;
        L_0x01a4:
            goto L_0x008f;
        L_0x01a7:
            r34 = 0;
            goto L_0x01a4;
        L_0x01aa:
            r34 = 0;
            goto L_0x0138;
        L_0x01ad:
            r0 = r20;
            r0 = r0.mExtras;
            r40 = r0;
            r18 = "android.support.useSideChannel";
            r19 = 1;
            r0 = r40;
            r1 = r18;
            r2 = r19;
            r0.putBoolean(r1, r2);
            goto L_0x0181;
        L_0x01c1:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompatKitKat.Builder.<init>(android.content.Context, android.app.Notification, java.lang.CharSequence, java.lang.CharSequence, java.lang.CharSequence, android.widget.RemoteViews, int, android.app.PendingIntent, android.app.PendingIntent, android.graphics.Bitmap, int, int, boolean, boolean, boolean, int, java.lang.CharSequence, boolean, java.util.ArrayList, android.os.Bundle, java.lang.String, boolean, java.lang.String):void");
        }

        public void addAction(Action $r1) throws  {
            this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.f4b, $r1));
        }

        public android.app.Notification.Builder getBuilder() throws  {
            return this.f4b;
        }

        public Notification build() throws  {
            SparseArray $r2 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
            if ($r2 != null) {
                this.mExtras.putSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS, $r2);
            }
            this.f4b.setExtras(this.mExtras);
            return this.f4b.build();
        }
    }

    NotificationCompatKitKat() throws  {
    }

    public static Bundle getExtras(Notification $r0) throws  {
        return $r0.extras;
    }

    public static int getActionCount(Notification $r0) throws  {
        return $r0.actions != null ? $r0.actions.length : 0;
    }

    public static Action getAction(Notification $r0, int $i0, Factory $r1, RemoteInput.Factory $r2) throws  {
        Notification.Action $r5 = $r0.actions[$i0];
        Bundle $r7 = null;
        SparseArray $r9 = $r0.extras.getSparseParcelableArray(NotificationCompatExtras.EXTRA_ACTION_EXTRAS);
        if ($r9 != null) {
            $r7 = (Bundle) $r9.get($i0);
        }
        return NotificationCompatJellybean.readAction($r1, $r2, $r5.icon, $r5.title, $r5.actionIntent, $r7);
    }

    public static boolean getLocalOnly(Notification $r0) throws  {
        return $r0.extras.getBoolean(NotificationCompatExtras.EXTRA_LOCAL_ONLY);
    }

    public static String getGroup(Notification $r0) throws  {
        return $r0.extras.getString(NotificationCompatExtras.EXTRA_GROUP_KEY);
    }

    public static boolean isGroupSummary(Notification $r0) throws  {
        return $r0.extras.getBoolean(NotificationCompatExtras.EXTRA_GROUP_SUMMARY);
    }

    public static String getSortKey(Notification $r0) throws  {
        return $r0.extras.getString(NotificationCompatExtras.EXTRA_SORT_KEY);
    }
}
