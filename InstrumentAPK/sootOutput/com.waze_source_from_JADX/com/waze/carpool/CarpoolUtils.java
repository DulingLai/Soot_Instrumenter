package com.waze.carpool;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityBase.IncomingHandler;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.map.CanvasFont;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.button.RidersImages;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.CustomAdapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import dalvik.annotation.Signature;
import java.util.Date;
import java.util.HashMap;

public class CarpoolUtils {
    public static final int DIALOG_OK = 1;
    public static final int RQ_CODE_FEEDBACK = 1001;
    public static final int RQ_REJECT_REASONS = 1004;

    static class C16151 implements OnClickListener {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ Context val$context;
        final /* synthetic */ CarpoolDrive val$drive;

        C16151(CarpoolDrive $r1, Context $r2, Activity $r3) throws  {
            this.val$drive = $r1;
            this.val$context = $r2;
            this.val$activity = $r3;
        }

        public void onClick(DialogInterface dialogInterface, int $i0) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CANCELATION_POPUP_CLICKED).addParam("ACTION", $i0 == 1 ? "CANCEL_RIDE" : "BACK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, this.val$drive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.val$drive.getId()).send();
            if ($i0 != 1) {
                return;
            }
            if (CarpoolNativeManager.getInstance().configShouldAskCancelReasonNTV()) {
                Intent $r2 = new Intent(this.val$context, ReasonSelectionActivity.class);
                Bundle $r3 = new Bundle();
                $r3.putStringArray("reasons", CarpoolNativeManager.getInstance().configGetCancelReasonsNTV());
                $r3.putString("title", NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CARPOOL_DECLINE_REASON_CHOOSE_REASON));
                $r3.putBoolean("singleSelection", true);
                $r3.putParcelable("CarpoolDrive", this.val$drive);
                $r3.putInt("updateServer", 2);
                $r2.putExtras($r3);
                if (this.val$activity != null) {
                    this.val$activity.startActivityForResult($r2, 1004);
                    return;
                } else {
                    this.val$context.startActivity($r2);
                    return;
                }
            }
            if (this.val$activity instanceof CarpoolRideDetailsActivity) {
                NativeManager.getInstance().OpenProgressPopup("");
            }
            CarpoolUtils.cancelDriveInServer("", this.val$drive);
        }
    }

    static class C16195 implements Runnable {
        C16195() throws  {
        }

        public void run() throws  {
            NativeManager.getInstance().CloseProgressPopup();
        }
    }

    public static class RiderImageAndMessageCounter {
        public int counter;
        public Drawable image;

        public RiderImageAndMessageCounter(Drawable $r1, int $i0) throws  {
            this.image = $r1;
            this.counter = $i0;
        }
    }

    public static boolean canCancelRide(int $i0) throws  {
        switch ($i0) {
            case 1:
            case 3:
            case 4:
            case 5:
            case 6:
            case 8:
                return false;
            case 2:
            case 7:
                break;
            default:
                break;
        }
        return true;
    }

    public static android.app.Dialog cancelDriveAfterAccepted(com.waze.carpool.CarpoolDrive r22, @android.support.annotation.Nullable android.app.Activity r23, android.content.Context r24) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:12:0x0098
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
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
        r8 = "RW_RIDE_SCREEN_CLICKED";
        r7 = com.waze.analytics.AnalyticsBuilder.analytics(r8);
        r8 = "ACTION";
        r9 = "CANCEL_RIDE";
        r7 = r7.addParam(r8, r9);
        r0 = r22;
        r10 = r0.getSomeRideId();
        r8 = "RIDE_ID";
        r7 = r7.addParam(r8, r10);
        r0 = r22;
        r10 = r0.getId();
        r8 = "DRIVE_ID";
        r7 = r7.addParam(r8, r10);
        r7.send();
        r0 = r22;
        r11 = r0.isMultiPax();
        if (r11 != 0) goto L_0x00a7;
    L_0x0031:
        r0 = r22;
        r12 = r0.getRider();
        if (r12 == 0) goto L_0x009c;
    L_0x0039:
        r10 = r12.getFirstName();
        if (r10 == 0) goto L_0x009c;
    L_0x003f:
        r10 = r12.getFirstName();
    L_0x0043:
        r14 = 1;
        r13 = new java.lang.Object[r14];
        r14 = 0;
        r13[r14] = r10;
        r14 = 2724; // 0xaa4 float:3.817E-42 double:1.346E-320;
        r10 = com.waze.strings.DisplayStrings.displayStringF(r14, r13);
    L_0x004f:
        r0 = r22;
        sendCancelAnalytics(r0);
        r14 = 2723; // 0xaa3 float:3.816E-42 double:1.3453E-320;
        r15 = com.waze.strings.DisplayStrings.displayString(r14);
        r16 = new com.waze.carpool.CarpoolUtils$1;
        r0 = r16;
        r1 = r22;
        r2 = r24;
        r3 = r23;
        r0.<init>(r1, r2, r3);
        r14 = 2728; // 0xaa8 float:3.823E-42 double:1.348E-320;
        r17 = com.waze.strings.DisplayStrings.displayString(r14);
        r14 = 2727; // 0xaa7 float:3.821E-42 double:1.3473E-320;
        r18 = com.waze.strings.DisplayStrings.displayString(r14);
        r14 = 0;
        r20 = -1;
        r0 = r15;
        r1 = r10;
        r2 = r14;
        r3 = r16;
        r4 = r17;
        r5 = r18;
        r6 = r20;
        r19 = com.waze.MsgBox.openConfirmDialogJavaCallback(r0, r1, r2, r3, r4, r5, r6);
        r0 = r22;
        r21 = r0.getRidesAmount();
        if (r21 <= 0) goto L_0x00fd;
    L_0x008d:
        r14 = 1;
        r0 = r22;
        r1 = r24;
        r2 = r19;
        setDialogRiderImages(r0, r1, r2, r14);
        return r19;
        goto L_0x009c;
    L_0x0099:
        goto L_0x004f;
    L_0x009c:
        r14 = 902; // 0x386 float:1.264E-42 double:4.456E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r14);
        goto L_0x0043;
        goto L_0x00a7;
    L_0x00a4:
        goto L_0x004f;
    L_0x00a7:
        r0 = r22;
        r21 = r0.getRidesAmount();
        r14 = 2;
        r0 = r21;
        if (r0 != r14) goto L_0x00f6;
    L_0x00b2:
        r14 = 0;
        r0 = r22;
        r12 = r0.getRider(r14);
        if (r12 == 0) goto L_0x00e8;
    L_0x00bb:
        r10 = r12.getFirstName();
        if (r10 == 0) goto L_0x00e8;
    L_0x00c1:
        r10 = r12.getFirstName();
    L_0x00c5:
        r14 = 1;
        r0 = r22;
        r12 = r0.getRider(r14);
        if (r12 == 0) goto L_0x00ef;
    L_0x00ce:
        r15 = r12.getFirstName();
        if (r15 == 0) goto L_0x00ef;
    L_0x00d4:
        r15 = r12.getFirstName();
    L_0x00d8:
        r14 = 2;
        r13 = new java.lang.Object[r14];
        r14 = 0;
        r13[r14] = r10;
        r14 = 1;
        r13[r14] = r15;
        r14 = 2725; // 0xaa5 float:3.819E-42 double:1.3463E-320;
        r10 = com.waze.strings.DisplayStrings.displayStringF(r14, r13);
        goto L_0x0099;
    L_0x00e8:
        r14 = 902; // 0x386 float:1.264E-42 double:4.456E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r14);
        goto L_0x00c5;
    L_0x00ef:
        r14 = 902; // 0x386 float:1.264E-42 double:4.456E-321;
        r15 = com.waze.strings.DisplayStrings.displayString(r14);
        goto L_0x00d8;
    L_0x00f6:
        r14 = 2726; // 0xaa6 float:3.82E-42 double:1.347E-320;
        r10 = com.waze.strings.DisplayStrings.displayString(r14);
        goto L_0x00a4;
    L_0x00fd:
        return r19;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolUtils.cancelDriveAfterAccepted(com.waze.carpool.CarpoolDrive, android.app.Activity, android.content.Context):android.app.Dialog");
    }

    public static void parseCarpoolUserCreateError(int r8, java.lang.String r9, java.lang.String r10, java.lang.String r11) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:14:0x0073 in {1, 6, 8, 9, 10, 12, 13, 15, 16, 22, 23} preds:[]
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
        switch(r8) {
            case 1: goto L_0x0077;
            case 2: goto L_0x004f;
            case 3: goto L_0x003d;
            case 4: goto L_0x00c2;
            case 5: goto L_0x0004;
            case 6: goto L_0x0004;
            case 7: goto L_0x0087;
            case 8: goto L_0x005f;
            default: goto L_0x0003;
        };
    L_0x0003:
        goto L_0x0004;
    L_0x0004:
        r0 = "RW_ERROR_SHOWN";
        r1 = "ERROR";
        r2 = "OTHER";
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r3 = 2590; // 0xa1e float:3.63E-42 double:1.2796E-320;
        r10 = com.waze.strings.DisplayStrings.displayString(r3);
        if (r9 == 0) goto L_0x0023;
    L_0x0015:
        r4 = r9.isEmpty();
        if (r4 != 0) goto L_0x0023;
    L_0x001b:
        r5 = com.waze.NativeManager.getInstance();
        r10 = r5.getLanguageString(r9);
    L_0x0023:
        showError(r10);
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        r0 = "A carpool error has occurred: ";
        r6 = r6.append(r0);
        r6 = r6.append(r10);
        r9 = r6.toString();
        com.waze.Logger.m38e(r9);
        return;
    L_0x003d:
        r0 = "RW_ERROR_SHOWN";
        r1 = "ERROR";
        r2 = "ALREADY_EXISTS";
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r5 = com.waze.NativeManager.getInstance();
        r10 = r5.getLanguageString(r9);
        goto L_0x0023;
    L_0x004f:
        r0 = "RW_ERROR_SHOWN";
        r1 = "ERROR";
        r2 = "NO_EMAIL";
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r3 = 2591; // 0xa1f float:3.631E-42 double:1.28E-320;
        r10 = com.waze.strings.DisplayStrings.displayString(r3);
        goto L_0x0023;
    L_0x005f:
        r0 = "RW_ERROR_SHOWN";
        r1 = "ERROR";
        r2 = "NO_NAME";
        com.waze.analytics.Analytics.log(r0, r1, r2);
        goto L_0x006c;
    L_0x0069:
        goto L_0x0023;
    L_0x006c:
        r3 = 2592; // 0xa20 float:3.632E-42 double:1.2806E-320;
        r10 = com.waze.strings.DisplayStrings.displayString(r3);
        goto L_0x0023;
        goto L_0x0077;
    L_0x0074:
        goto L_0x0023;
    L_0x0077:
        r0 = "RW_ERROR_SHOWN";
        r1 = "ERROR";
        r2 = "NO_GAIA";
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r3 = 2590; // 0xa1e float:3.63E-42 double:1.2796E-320;
        r10 = com.waze.strings.DisplayStrings.displayString(r3);
        goto L_0x0023;
    L_0x0087:
        r0 = "RW_ERROR_SHOWN";
        r1 = "ERROR";
        r2 = "BAD_NAME";
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r3 = 2596; // 0xa24 float:3.638E-42 double:1.2826E-320;
        r9 = com.waze.strings.DisplayStrings.displayString(r3);
        r3 = 1;
        r7 = new java.lang.Object[r3];
        r6 = new java.lang.StringBuilder;
        r6.<init>();
        if (r10 == 0) goto L_0x00bc;
    L_0x00a0:
        r6 = r6.append(r10);
        r0 = " ";
        r6 = r6.append(r0);
        if (r11 == 0) goto L_0x00bf;
    L_0x00ac:
        r6 = r6.append(r11);
        r10 = r6.toString();
        r3 = 0;
        r7[r3] = r10;
        r10 = java.lang.String.format(r9, r7);
        goto L_0x0069;
    L_0x00bc:
        r10 = "";
        goto L_0x00a0;
    L_0x00bf:
        r11 = "";
        goto L_0x00ac;
    L_0x00c2:
        r3 = 2595; // 0xa23 float:3.636E-42 double:1.282E-320;
        r10 = com.waze.strings.DisplayStrings.displayString(r3);
        r0 = "RW_ERROR_SHOWN";
        r1 = "ERROR";
        r2 = "PHONE_NUMBER_ALREADY_IN_USE";
        com.waze.analytics.Analytics.log(r0, r1, r2);
        goto L_0x0074;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolUtils.parseCarpoolUserCreateError(int, java.lang.String, java.lang.String, java.lang.String):void");
    }

    public static void setDialogRiderImages(CarpoolDrive $r0, Context $r1, Dialog $r2, boolean $z0) throws  {
        FrameLayout $r7 = (FrameLayout) $r2.findViewById(C1283R.id.confirmImageContainer);
        $r7.removeAllViews();
        $r7.setForeground(null);
        $r7.setBackgroundDrawable(null);
        $r7.setVisibility(0);
        $r7.setClipChildren(false);
        $r7.setClipToPadding(false);
        ViewGroup $r9 = (ViewGroup) $r7.getParent();
        $r9.setClipChildren(false);
        $r9.setClipToPadding(false);
        RidersImages $r4 = r14;
        RidersImages r14 = new RidersImages($r1);
        $r4.setStrokeDp(4);
        $r4.setPlaceholderResId(C1283R.drawable.rs_profilepic_placeholder);
        for (CarpoolRide $r5 : $r0.getRides()) {
            if (!($r5 == null || $r5.getRider() == null)) {
                $r4.addImage($r5.getRider().getImage());
            }
        }
        LayoutParams $r13 = r0;
        LayoutParams layoutParams = new FrameLayout.LayoutParams(0, PixelMeasure.dp(80));
        $r13.gravity = 17;
        $r7.addView($r4, $r13);
        if ($z0) {
            View $r3 = r0;
            View imageView = new ImageView($r1);
            $r3.setImageResource(C1283R.drawable.carpool_sadcloud);
            $r13 = layoutParams;
            layoutParams = new FrameLayout.LayoutParams(-2, -2);
            $r13.gravity = 53;
            $r13.rightMargin = -PixelMeasure.dp(4);
            $r13.topMargin = -PixelMeasure.dp(2);
            $r7.addView($r3, $r13);
        }
    }

    public static Dialog removeRiderFromDrive(CarpoolDrive $r0, CarpoolRide $r1, CarpoolUserData $r2, int $i0, Context context, IncomingHandler $r4) throws  {
        String $r6;
        if ($r2 == null || $r2.getFirstName() == null) {
            $r6 = DisplayStrings.displayString(DisplayStrings.DS_UNKNOWN);
        } else {
            $r6 = $r2.getFirstName();
        }
        final CarpoolDrive carpoolDrive = $r0;
        final CarpoolRide carpoolRide = $r1;
        final CarpoolUserData carpoolUserData = $r2;
        final int i = $i0;
        final IncomingHandler incomingHandler = $r4;
        Dialog $r11 = MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_CANCEL_RIDER_CONFIRMATION_TITLE_PS, new Object[]{$r6}), DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_CANCEL_RIDER_CONFIRMATION_TEXT_PS, new Object[]{$r6}), false, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int $i0) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_REMOVE_RIDER_CONFIRMATION_POPUP_CLICKED).addParam("ACTION", $i0 == 1 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM : "BACK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, carpoolDrive.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, carpoolRide.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_USER_ID, carpoolUserData.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_IDX, (long) i).send();
                if ($i0 == 1) {
                    if (incomingHandler != null) {
                        CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE, incomingHandler);
                    }
                    CarpoolNativeManager.getInstance().removeRideFromDrive(carpoolDrive, carpoolRide.getId(), false, null);
                }
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_CANCEL_RIDER_CONFIRMATION_CONFIRM), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_CANCEL_RIDER_CONFIRMATION_CANCEL), -1, null, null, false, true, false, null, null);
        if ($r11 == null) {
            return $r11;
        }
        $r11.show();
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_REMOVE_RIDER_CONFIRMATION_POPUP_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r0.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r1.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_USER_ID, $r2.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_IDX, (long) $i0).send();
        return $r11;
    }

    public static void sendCancelAnalytics(CarpoolDrive $r0) throws  {
        if (($r0.getTime() * 1000) - new Date().getTime() < ((long) CarpoolNativeManager.getInstance().getEarlyCancelTimeNTV())) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CANCELATION_POPUP_SHOWN).addParam("TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SHORT).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r0.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r0.getId()).send();
            return;
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CANCELATION_POPUP_SHOWN).addParam("TYPE", AnalyticsEvents.ANALYTICS_EVENT_VALUE_LONG).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r0.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r0.getId()).send();
    }

    public static void rejectDriveInServer(String $r0, String $r1, CarpoolDrive $r2) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_DECLINE_REASON).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $r0).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r2.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r2.getId()).send();
        CarpoolNativeManager.getInstance().rejectRideRequest($r2.uuid, $r1);
    }

    public static void cancelDriveInServer(String $r0, CarpoolDrive $r1) throws  {
        CarpoolNativeManager.getInstance().deleteDrive($r1, $r0);
    }

    public static void riderDidntShow(CarpoolDrive $r0, CarpoolRide $r1, ActivityBase $r2) throws  {
        String $r7;
        if (!$r0.isMultiPax()) {
            sendCancelAnalytics($r0);
        }
        final String $r4 = CarpoolNativeManager.getInstance().configGetNoShowCancelReasonNTV();
        String $r5 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        if (!($r1 == null || $r1.getRider() == null || $r1.getRider().getName() == null || $r1.getRider().getName().isEmpty())) {
            $r5 = $r1.getRider().getName();
        }
        if ($r0.isMultiPax()) {
            $r7 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRM_TEXT_MANY_PS, new Object[]{$r5});
        } else {
            $r7 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRM_TEXT_PS, new Object[]{$r5});
        }
        final CarpoolDrive carpoolDrive = $r0;
        final CarpoolRide carpoolRide = $r1;
        final ActivityBase activityBase = $r2;
        MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRM_TITLE_PS, new Object[]{$r5}), $r7, false, new OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int $i0) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CANCELATION_POPUP_CLICKED).addParam("ACTION", $i0 == 1 ? "CANCEL_RIDE" : "BACK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, carpoolDrive.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, carpoolDrive.getId()).send();
                if ($i0 != 1) {
                    return;
                }
                if (carpoolDrive.isMultiPax()) {
                    CarpoolNativeManager.getInstance().removeRideFromDrive(carpoolDrive, carpoolRide.getId(), true, null);
                    return;
                }
                CarpoolUtils.cancelDriveInServer($r4, carpoolDrive);
                CarpoolUtils.displaySuccess(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_NOSHOW_COMFIRMATION_LAST_RIDER), 1000, activityBase);
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRM_BUTTON), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_BLOCK_CONFIRM_NO), -1);
    }

    public static void startFeedbackActivity(@Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolRide;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/ifs/ui/ActivityBase;", "Landroid/content/Context;", "Z)V"}) final CarpoolDrive $r0, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolRide;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/ifs/ui/ActivityBase;", "Landroid/content/Context;", "Z)V"}) CarpoolRide $r1, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolRide;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/ifs/ui/ActivityBase;", "Landroid/content/Context;", "Z)V"}) HashMap<String, RiderImageAndMessageCounter> $r2, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolRide;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/ifs/ui/ActivityBase;", "Landroid/content/Context;", "Z)V"}) ActivityBase $r3, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolRide;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/ifs/ui/ActivityBase;", "Landroid/content/Context;", "Z)V"}) Context $r4, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolRide;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/ifs/ui/ActivityBase;", "Landroid/content/Context;", "Z)V"}) boolean $z0) throws  {
        if ($z0) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OPTION).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_FEEDBACK).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r0.getSomeRideId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r0.getId()).send();
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_REPORT_PROBLEM_CLICKED);
        Intent $r7;
        if (!$r0.isMultiPax()) {
            $r7 = CarpoolFeedbackActivity.getIntent($r4, $r0, $r0.getRide());
            if ($r3 != null) {
                $r3.startActivityForResult($r7, 1001);
            } else {
                $r4.startActivity($r7);
            }
        } else if ($r1 != null) {
            $r7 = CarpoolFeedbackActivity.getIntent($r4, $r0, $r1);
            if ($r3 != null) {
                $r3.startActivityForResult($r7, 1001);
            } else {
                $r4.startActivity($r7);
            }
        } else {
            final Context context = $r4;
            final ActivityBase activityBase = $r3;
            showSelectRiderBottomSheet($r4, $r0, $r2, new IResultObj<Integer>() {
                public void onResult(Integer $r1) throws  {
                    Intent $r6 = CarpoolFeedbackActivity.getIntent(context, $r0, $r0.getRide($r1.intValue()));
                    if (activityBase != null) {
                        activityBase.startActivityForResult($r6, 1001);
                    } else {
                        context.startActivity($r6);
                    }
                }
            }, DisplayStrings.DS_RIDERS_ACTION_SHEET_FEEDBACK_TITLE, -1);
        }
    }

    public static boolean confirmPaxDropOff(CarpoolDrive $r0, ActivityBase $r1) throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CONFIRM_DROPOFF, "ACTION", "DESTINATION");
        displaySuccess(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_RIDE_COMPLETE), 2000, $r1);
        if ($r0 == null) {
            Logger.m38e("Manual rides: confirmPaxDropOff: ride is null! navigate to destination");
            DisplayErrorMsgBox();
            return false;
        }
        Logger.m36d("Manual rides - navigating to dropoff: ");
        CarpoolNativeManager.getInstance().manualRideNavigateToDestination($r0 != null ? $r0.getId() : null, true);
        return true;
    }

    public static boolean confirmPaxPickedUp(CarpoolDrive $r0) throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_CONFIRM_PICKUP, "ACTION", "DROPOFF");
        if ($r0 == null) {
            Logger.m38e("Manual rides: confirmPaxPickedUp: ride is null! navigate to destination");
            DisplayErrorMsgBox();
            return false;
        }
        Logger.m36d("Manual rides - navigating to pickup: ");
        CarpoolNativeManager.getInstance().manualRideDriveToDropoff($r0 != null ? $r0.getId() : null, true);
        return true;
    }

    public static boolean confirmDriveToPickUp(CarpoolDrive $r0, String $r1, boolean $z0) throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_TAKEOVER_START_CARPOOL, "ACTION", "PICKUP");
        if ($r0 == null && $r1 == null) {
            Logger.m38e("Manual rides: confirmDriveToPickUp: ride is null!");
            DisplayErrorMsgBox();
            return false;
        }
        Logger.m36d("Manual rides - confirmed pickup");
        CarpoolNativeManager.getInstance().manualRideDriveToMeetingWithPickup($r1, $r0 != null ? $r0.getId() : null, $z0);
        return true;
    }

    public static void displaySuccess(String $r0, int $i0, ActivityBase activity) throws  {
        NativeManager.getInstance().OpenMainActivityProgressIconPopup($r0, "sign_up_big_v");
        AppService.getMainActivity().postDelayed(new C16195(), (long) $i0);
    }

    public static void DisplayErrorMsgBox() throws  {
        MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
    }

    public static void closeSwipableLayout() throws  {
        LayoutManager $r1 = AppService.getMainActivity().getLayoutMgr();
        if ($r1 != null && $r1.isSwipeableLayoutOpened()) {
            $r1.closeSwipeableLayout();
        }
    }

    public static boolean isRideInvalid(CarpoolRide $r0) throws  {
        return $r0 == null || $r0.getId() == null;
    }

    public static boolean isDriveInvalid(CarpoolDrive $r0) throws  {
        return $r0 == null || $r0.isEmpty();
    }

    public static boolean areBankDetailsAvailable() throws  {
        CarpoolUserData $r1 = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
        if (ConfigManager.getInstance().getConfigValueBool(15)) {
            CarpoolPayee $r3 = CarpoolNativeManager.getInstance().getCachedPayeeNTV();
            if ($r1.prompt_payment_action == 0 || !($r3 == null || $r3.payout_account_name == null || $r3.payout_account_name.isEmpty())) {
                Logger.m36d("areBankDetailsAvailable: bank details available from payee");
                return true;
            }
            Logger.m36d("areBankDetailsAvailable: bank details NOT available from payee");
            return false;
        } else if ($r1 == null || !$r1.hasBankDetails()) {
            Logger.m36d("areBankDetailsAvailable: bank details NOT available from profile");
            return false;
        } else {
            Logger.m36d("areBankDetailsAvailable: bank details available from profile");
            return true;
        }
    }

    public static int getBalance() throws  {
        CarpoolUserData $r1 = CarpoolNativeManager.getInstance().getCarpoolProfileNTV();
        if (ConfigManager.getInstance().getConfigValueBool(15)) {
            CarpoolPayee $r3 = CarpoolNativeManager.getInstance().getCachedPayeeNTV();
            if ($r3 == null || ($r1.prompt_payment_action != 0 && ($r3.payout_account_name == null || $r3.payout_account_name.isEmpty()))) {
                Logger.m36d("areBankDetailsAvailable: bank details NOT available from payee");
                return -1;
            }
            Logger.m36d("areBankDetailsAvailable: bank details available from payee");
            return $r3.unpaid_balance;
        }
        Logger.m36d("areBankDetailsAvailable: bank details NOT available");
        return -1;
    }

    public static void openRiderApp(Activity $r0) throws  {
        try {
            $r0.startActivity($r0.getPackageManager().getLaunchIntentForPackage("com.ridewith"));
        } catch (Exception e) {
            try {
                $r0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.ridewith")));
            } catch (Exception e2) {
                $r0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.ridewith")));
            }
        }
    }

    public static void isIdInDrivesList(final String $r0, final IResultOk $r1) throws  {
        if ($r0 == null) {
            $r1.onResult(false);
        }
        CarpoolNativeManager.getInstance().getDrives(false, new IResultObj<CarpoolDrive[]>() {
            public void onResult(CarpoolDrive[] $r1) throws  {
                if ($r1 != null) {
                    for (CarpoolDrive $r2 : $r1) {
                        if (!($r2 == null || $r2.isEmpty() || $r2.getId() == null)) {
                            if ($r0.contentEquals($r2.getId())) {
                                $r1.onResult(true);
                                return;
                            }
                            int $i2 = 0;
                            while ($i2 < $r2.getRidesAmount()) {
                                if ($r2.getRide($i2) == null || $r2.getRide($i2).getId() == null || !$r0.contentEquals($r2.getRide($i2).getId())) {
                                    $i2++;
                                } else {
                                    $r1.onResult(true);
                                    return;
                                }
                            }
                            continue;
                        }
                    }
                }
                $r1.onResult(false);
            }
        });
    }

    public static Spanned getSpannedLegal(String $r0) throws  {
        if ($r0 == null || !$r0.equalsIgnoreCase("ILS")) {
            return Html.fromHtml(String.format(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_PAYMENTS_PAYONEER_LEGAL_US), new Object[]{ConfigManager.getInstance().getConfigValueString(17), ConfigManager.getInstance().getConfigValueString(18), ConfigManager.getInstance().getConfigValueString(16)}));
        }
        return Html.fromHtml(String.format(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_PAYMENTS_PAYONEER_LEGAL_IL), new Object[]{ConfigManager.getInstance().getConfigValueString(19), ConfigManager.getInstance().getConfigValueString(20)}));
    }

    public static String getBoardedState() throws  {
        CarpoolOnboardingManager.getInstance();
        return CarpoolOnboardingManager.isDriverOnboarded() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_ONBOARDED : AnalyticsEvents.ANALYTICS_EVENT_VALUE_NOT_ONBOARDED;
    }

    public static void showError(String $r0) throws  {
        if ($r0 == null) {
            $r0 = DisplayStrings.displayString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_);
        }
        MsgBox.openMessageBox(DisplayStrings.displayString(DisplayStrings.DS_CONNECT_POPUP_TITLE), $r0, false);
    }

    public static boolean hasPaymentMeans(boolean $z0) throws  {
        CarpoolNativeManager $r0 = CarpoolNativeManager.getInstance();
        return hasPaymentMeans($r0.getCarpoolProfileNTV(), $z0, $r0.getCachedPayeeNTV());
    }

    public static boolean hasPaymentMeans(CarpoolUserData $r0, boolean $z0) throws  {
        return hasPaymentMeans($r0, $z0, CarpoolNativeManager.getInstance().getCachedPayeeNTV());
    }

    public static boolean hasPaymentMeans(CarpoolUserData $r0, boolean $z0, CarpoolPayee $r1) throws  {
        if (ConfigManager.getInstance().getConfigValueBool(15)) {
            Logger.m36d("hasPaymentMeans: prompt_payment_action=" + $r0.prompt_payment_action);
            if ($r0.prompt_payment_action == 0 || $z0 || !($r1 == null || $r1.payout_account_name == null || $r1.payout_account_name.isEmpty())) {
                Logger.m36d("hasPaymentMeans: setting bank details from payee");
                return true;
            }
            Logger.m36d("hasPaymentMeans: info not complete in payee");
            return false;
        } else if ($r0 == null || !$r0.hasBankDetails()) {
            Logger.m36d("hasPaymentMeans: info not complete in CarpoolData");
            return false;
        } else {
            Logger.m36d("hasPaymentMeans: setting bank details from CarpoolData");
            return true;
        }
    }

    public static String getPhoneIfLoggedIn() throws  {
        if (MyWazeNativeManager.getInstance().getContactLoggedInNTV()) {
            String $r1 = MyWazeNativeManager.getInstance().getPhoneNumberNTV();
            Logger.m36d("getPhoneIfLoggedIn: User already logged in, phone is " + $r1);
            return $r1;
        }
        Logger.m36d("getPhoneIfLoggedIn: User not logged in with phone, passing null for phone");
        return null;
    }

    static void setStarImage(ImageView $r0, float $f0, float $f1) throws  {
        int $i1 = $f0 >= $f1 ? C1283R.drawable.small_star_full : $f0 >= $f1 - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR ? C1283R.drawable.small_star_half_full : C1283R.drawable.small_star_empty;
        $r0.setImageResource($i1);
    }

    static void setStarsView(float $f0, int $i0, View $r0, String $r1) throws  {
        if ($f0 > 0.0f) {
            $r0.setVisibility(0);
            setStarImage((ImageView) $r0.findViewById(C1283R.id.rideRequestRiderRatingStar1), $f0, 1.0f);
            setStarImage((ImageView) $r0.findViewById(C1283R.id.rideRequestRiderRatingStar2), $f0, LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
            setStarImage((ImageView) $r0.findViewById(C1283R.id.rideRequestRiderRatingStar3), $f0, 3.0f);
            setStarImage((ImageView) $r0.findViewById(C1283R.id.rideRequestRiderRatingStar4), $f0, 4.0f);
            setStarImage((ImageView) $r0.findViewById(C1283R.id.rideRequestRiderRatingStar5), $f0, 5.0f);
            ((TextView) $r0.findViewById(C1283R.id.rideRequestRiderRatingText)).setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_DETAILS_NUMBER_OF_RIDES_PARENTHESES_PD, new Object[]{Integer.valueOf($i0)}));
        } else if ($i0 > 0) {
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar1).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar2).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar3).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar4).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar5).setVisibility(8);
            ((TextView) $r0.findViewById(C1283R.id.rideRequestRiderRatingText)).setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_DETAILS_NUMBER_OF_RIDES_PD, new Object[]{Integer.valueOf($i0)}));
        } else if ($r1 == null || $r1.isEmpty()) {
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar1).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar2).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar3).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar4).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar5).setVisibility(8);
            ((TextView) $r0.findViewById(C1283R.id.rideRequestRiderRatingText)).setText(DisplayStrings.displayString(DisplayStrings.DS_DRIVER_PROFILE_NO_RATINGS));
        } else {
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar1).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar2).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar3).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar4).setVisibility(8);
            $r0.findViewById(C1283R.id.rideRequestRiderRatingStar5).setVisibility(8);
            ((TextView) $r0.findViewById(C1283R.id.rideRequestRiderRatingText)).setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDE_DETAILS_FIRST_RIDE_PS, new Object[]{$r1}));
        }
    }

    public static CarpoolUserData getDummyRider() throws  {
        CarpoolUserData $r0 = new CarpoolUserData();
        $r0.id = "dummy-dummy";
        $r0.email = "dummy@dummy.com";
        $r0.name = "Damn Damnation";
        $r0.given_name = "Damn";
        $r0.family_name = "Damnation";
        $r0.photo_url = "https://lh3.googleusercontent.com/-wA0C-eDxCrY/AAAAAAAAAAI/AAAAAAAAAA8/BZN2wwT5r9A/photo.jpg";
        $r0.confirmed_credit_card = true;
        $r0.work_email_verified = true;
        $r0.star_rating_as_pax = 3.5f;
        $r0.organization = "Dummy Town";
        return $r0;
    }

    public static CarpoolDrive getDummyDrive() throws  {
        CarpoolDrive $r0 = new CarpoolDrive();
        $r0.countryCode = "IL";
        $r0.drive_match_info.reference_total_fee_minor_units = 1500;
        $r0.drive_match_info.total_fee_minor_units = 2500;
        return $r0;
    }

    public static boolean canCallRider(CarpoolDrive $r0) throws  {
        if ($r0 == null) {
            return false;
        }
        CarpoolRide $r1 = $r0.getRide();
        if (isDriveInvalid($r0)) {
            return false;
        }
        if (isRideInvalid($r1)) {
            return false;
        }
        if (!CarpoolNativeManager.getInstance().isContactRiderAllowed($r0, $r1)) {
            return false;
        }
        if (!ConfigValues.getBoolValue(5)) {
            return false;
        }
        if ($r1.getProxyNumber() != null) {
            return !$r1.getProxyNumber().isEmpty();
        } else {
            return false;
        }
    }

    public static boolean canChatRider(CarpoolDrive $r0) throws  {
        if ($r0 == null) {
            return false;
        }
        CarpoolRide $r1 = $r0.getRide();
        if (isDriveInvalid($r0)) {
            return false;
        }
        if (isRideInvalid($r1)) {
            return false;
        }
        if (CarpoolNativeManager.getInstance().isContactRiderAllowed($r0, $r1)) {
            return CarpoolNativeManager.getInstance().isMessagingEnabled();
        } else {
            return false;
        }
    }

    public static void showSelectRiderBottomSheet(@Signature({"(", "Landroid/content/Context;", "Lcom/waze/carpool/CarpoolDrive;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/Integer;", ">;II)V"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Lcom/waze/carpool/CarpoolDrive;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/Integer;", ">;II)V"}) CarpoolDrive $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/waze/carpool/CarpoolDrive;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/Integer;", ">;II)V"}) HashMap<String, RiderImageAndMessageCounter> $r2, @Signature({"(", "Landroid/content/Context;", "Lcom/waze/carpool/CarpoolDrive;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/Integer;", ">;II)V"}) IResultObj<Integer> $r3, @Signature({"(", "Landroid/content/Context;", "Lcom/waze/carpool/CarpoolDrive;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/Integer;", ">;II)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "Lcom/waze/carpool/CarpoolDrive;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/Integer;", ">;II)V"}) int $i1) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CHOOSE_RIDER_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r1.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_NUM_RIDERS, (long) $r1.getRidesAmount()).send();
        BottomSheet $r4 = r0;
        BottomSheet bottomSheet = new BottomSheet($r0, $i0, null, 1, true, C1283R.layout.bottom_sheet_row_item_with_badge);
        final CarpoolDrive carpoolDrive = $r1;
        final HashMap<String, RiderImageAndMessageCounter> hashMap = $r2;
        final int i = $i1;
        final IResultObj<Integer> iResultObj = $r3;
        final BottomSheet bottomSheet2 = $r4;
        $r4.setAdapter(new CustomAdapter() {

            class C16211 implements View.OnClickListener {
                final /* synthetic */ int val$position;

                C16211(int $i0) throws  {
                    this.val$position = $i0;
                }

                public void onClick(View v) throws  {
                    C16227.this.onItemClicked(this.val$position);
                }
            }

            public boolean isCustomItem(int position) throws  {
                return true;
            }

            public android.view.View setupCustomItem(android.widget.GridView r35, int r36, android.view.View r37) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:18:0x00d1
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                /*
                r34 = this;
                if (r37 != 0) goto L_0x0014;
            L_0x0002:
                r3 = com.waze.AppService.getActiveActivity();
                r4 = r3.getLayoutInflater();
                r5 = 2130903107; // 0x7f030043 float:1.7413023E38 double:1.05280602E-314;
                r6 = 0;
                r0 = r35;
                r37 = r4.inflate(r5, r0, r6);
            L_0x0014:
                r0 = r34;
                r7 = r1;
                r0 = r36;
                r8 = r7.getRide(r0);
                if (r8 == 0) goto L_0x0024;
            L_0x0020:
                r9 = r8.rider;
                if (r9 != 0) goto L_0x0060;
            L_0x0024:
                r5 = 2131690037; // 0x7f0f0235 float:1.9009106E38 double:1.053194815E-314;
                r0 = r37;
                r10 = r0.findViewById(r5);
                r12 = r10;
                r12 = (android.widget.ImageView) r12;
                r11 = r12;
                r5 = 2130839007; // 0x7f0205df float:1.7283012E38 double:1.05277435E-314;
                r11.setImageResource(r5);
                r5 = 2131690038; // 0x7f0f0236 float:1.9009108E38 double:1.0531948154E-314;
                r0 = r37;
                r10 = r0.findViewById(r5);
                r14 = r10;
                r14 = (android.widget.TextView) r14;
                r13 = r14;
                r5 = 902; // 0x386 float:1.264E-42 double:4.456E-321;
                r15 = com.waze.strings.DisplayStrings.displayString(r5);
                r13.setText(r15);
            L_0x004d:
                r16 = new com.waze.carpool.CarpoolUtils$7$1;
                r0 = r16;
                r1 = r34;
                r2 = r36;
                r0.<init>(r2);
                r0 = r37;
                r1 = r16;
                r0.setOnClickListener(r1);
                return r37;
            L_0x0060:
                r0 = r34;
                r0 = r2;
                r17 = r0;
                r9 = r8.rider;
                r15 = r9.id;
                r0 = r17;
                r18 = r0.get(r15);
                r20 = r18;
                r20 = (com.waze.carpool.CarpoolUtils.RiderImageAndMessageCounter) r20;
                r19 = r20;
                r0 = r19;
                r0 = r0.image;
                r21 = r0;
                if (r21 == 0) goto L_0x0110;
            L_0x007e:
                r5 = 2131690037; // 0x7f0f0235 float:1.9009106E38 double:1.053194815E-314;
                r0 = r37;
                r10 = r0.findViewById(r5);
                r22 = r10;
                r22 = (android.widget.ImageView) r22;
                r11 = r22;
                r0 = r19;
                r0 = r0.image;
                r21 = r0;
                r11.setImageDrawable(r0);
            L_0x0096:
                r5 = 2131690040; // 0x7f0f0238 float:1.9009112E38 double:1.0531948163E-314;
                r0 = r37;
                r10 = r0.findViewById(r5);
                r23 = r10;
                r23 = (android.widget.TextView) r23;
                r13 = r23;
                goto L_0x00a9;
            L_0x00a6:
                goto L_0x004d;
            L_0x00a9:
                r0 = r19;
                r0 = r0.counter;
                r24 = r0;
                if (r24 <= 0) goto L_0x0126;
            L_0x00b1:
                r25 = com.waze.NativeManager.getInstance();
                r0 = r25;
                r26 = r0.getLocale();
                r5 = 1;
                r0 = new java.lang.Object[r5];
                r27 = r0;
                r0 = r19;
                r0 = r0.counter;
                r24 = r0;
                r28 = java.lang.Integer.valueOf(r0);
                r5 = 0;
                r27[r5] = r28;
                goto L_0x00d5;
            L_0x00ce:
                goto L_0x004d;
                goto L_0x00d5;
            L_0x00d2:
                goto L_0x0096;
            L_0x00d5:
                r29 = "%d";
                r0 = r26;
                r1 = r29;
                r2 = r27;
                r15 = java.lang.String.format(r0, r1, r2);
                r13.setText(r15);
                r5 = 0;
                r13.setVisibility(r5);
            L_0x00e8:
                r0 = r34;
                r0 = r3;
                r24 = r0;
                r5 = -1;
                r0 = r24;
                if (r0 != r5) goto L_0x012c;
            L_0x00f3:
                r5 = 2131690038; // 0x7f0f0236 float:1.9009108E38 double:1.0531948154E-314;
                r0 = r37;
                r10 = r0.findViewById(r5);
                r30 = r10;
                r30 = (android.widget.TextView) r30;
                r13 = r30;
                r9 = r8.rider;
                r15 = r9.getName();
                r13.setText(r15);
                goto L_0x00a6;
                goto L_0x0110;
            L_0x010d:
                goto L_0x00ce;
            L_0x0110:
                r5 = 2131690037; // 0x7f0f0235 float:1.9009106E38 double:1.053194815E-314;
                r0 = r37;
                r10 = r0.findViewById(r5);
                r31 = r10;
                r31 = (android.widget.ImageView) r31;
                r11 = r31;
                r5 = 2130839007; // 0x7f0205df float:1.7283012E38 double:1.05277435E-314;
                r11.setImageResource(r5);
                goto L_0x00d2;
            L_0x0126:
                r5 = 8;
                r13.setVisibility(r5);
                goto L_0x00e8;
            L_0x012c:
                r0 = r34;
                r0 = r3;
                r24 = r0;
                r15 = com.waze.strings.DisplayStrings.displayString(r0);
                r5 = 1;
                r0 = new java.lang.Object[r5];
                r27 = r0;
                r9 = r8.rider;
                r32 = r9.getName();
                r5 = 0;
                r27[r5] = r32;
                r0 = r27;
                r15 = java.lang.String.format(r15, r0);
                r5 = 2131690038; // 0x7f0f0236 float:1.9009108E38 double:1.0531948154E-314;
                r0 = r37;
                r10 = r0.findViewById(r5);
                r33 = r10;
                r33 = (android.widget.TextView) r33;
                r13 = r33;
                r13.setText(r15);
                goto L_0x010d;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolUtils.7.setupCustomItem(android.widget.GridView, int, android.view.View):android.view.View");
            }

            public int getCount() throws  {
                return carpoolDrive.getRidesAmount();
            }

            public void onConfigItem(int position, ItemDetails item) throws  {
            }

            public void onClick(int $i0) throws  {
                onItemClicked($i0);
            }

            private void onItemClicked(int $i0) throws  {
                String $r1 = "";
                if (!(carpoolDrive.getRide($i0) == null || carpoolDrive.getRide($i0).rider == null)) {
                    $r1 = carpoolDrive.getRide($i0).rider.getId();
                }
                long $l1 = (long) $i0;
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CHOOSE_RIDER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, carpoolDrive.getId()).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CHOOSE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_IDX, $l1).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_USER_ID, $r1).send();
                iResultObj.onResult(Integer.valueOf($i0));
                bottomSheet2.dismiss();
            }
        });
        carpoolDrive = $r1;
        $r4.setCancelListener(new OnCancelListener() {
            public void onCancel(DialogInterface dialog) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CHOOSE_RIDER_CLICKED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, carpoolDrive.getId()).addParam("ACTION", "CANCEL").send();
            }
        });
        $r4.show();
    }

    public static void initRiderImagesAndMsgCounts(@Signature({"(", "Lcom/waze/carpool/CarpoolUserData;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Landroid/widget/ImageView;", "Landroid/graphics/Bitmap;", "II)V"}) CarpoolUserData $r0, @Signature({"(", "Lcom/waze/carpool/CarpoolUserData;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Landroid/widget/ImageView;", "Landroid/graphics/Bitmap;", "II)V"}) HashMap<String, RiderImageAndMessageCounter> $r1, @Signature({"(", "Lcom/waze/carpool/CarpoolUserData;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Landroid/widget/ImageView;", "Landroid/graphics/Bitmap;", "II)V"}) ImageView $r2, @Signature({"(", "Lcom/waze/carpool/CarpoolUserData;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Landroid/widget/ImageView;", "Landroid/graphics/Bitmap;", "II)V"}) Bitmap $r3, @Signature({"(", "Lcom/waze/carpool/CarpoolUserData;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Landroid/widget/ImageView;", "Landroid/graphics/Bitmap;", "II)V"}) int $i0, @Signature({"(", "Lcom/waze/carpool/CarpoolUserData;", "Ljava/util/HashMap", "<", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolUtils$RiderImageAndMessageCounter;", ">;", "Landroid/widget/ImageView;", "Landroid/graphics/Bitmap;", "II)V"}) int $i1) throws  {
        CircleFrameDrawable $r4 = new CircleFrameDrawable($r3, 0, $i1);
        $r2.setImageDrawable($r4);
        $r2.setLayerType(1, null);
        $r1.put($r0.id, new RiderImageAndMessageCounter($r4, $i0));
        String $r5 = $r0.getImage();
        if ($r5 == null || $r5.isEmpty()) {
            Logger.m38e("CarpoolTripDialog: rider image is null or empty id=" + $r0.id);
            return;
        }
        final int i = $i1;
        final ImageView imageView = $r2;
        final HashMap<String, RiderImageAndMessageCounter> hashMap = $r1;
        final CarpoolUserData carpoolUserData = $r0;
        VolleyManager.getInstance().loadImageFromUrl($r5, new ImageRequestListener() {
            public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                imageView.setImageDrawable(new CircleFrameDrawable($r1, 0, i));
                if (hashMap != null) {
                    ((RiderImageAndMessageCounter) hashMap.get(carpoolUserData.id)).image = new CircleFrameDrawable($r1, 0, 0);
                }
            }

            public void onImageLoadFailed(Object token, long duration) throws  {
            }
        }, null, $r2.getWidth(), $r2.getHeight(), null);
    }

    public static boolean areSameDrives(CarpoolDrive $r0, CarpoolDrive $r1) throws  {
        return ($r0 == null || $r1 == null || (($r0.uuid == null || !$r0.uuid.equals($r1.uuid)) && ($r0.getRide() == null || $r1.getRide() == null || $r1.getRide().uuid == null || !$r1.getRide().uuid.equals($r0.getRide().uuid)))) ? false : true;
    }
}
