package com.waze.carpool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GestureDetectorCompat;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.UpdateHandlers.MicroHandler;
import com.waze.ifs.async.UpdateHandlers.MicroHandler.MicroHandlerCallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.share.FacebookLikeActivity;
import com.waze.share.LinkedinProfileActivity;
import com.waze.share.ShareConstants;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.anim.MaterialDesignImageAnimationHelper;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.WazeTextView;
import java.util.Calendar;

public class CarpoolRiderProfileActivity extends ActivityBase {
    private static final int NUM_OPTIONS = 3;
    private static final int OPTION_BLOCK_RIDER = 2;
    private static final int OPTION_CALL = 0;
    private static final int OPTION_MESSAGE = 1;
    public static final int USER_BLOCKED = 461;
    private Animator mAnimator;
    private SimpleBottomSheet mBottomSheet;
    CarpoolNativeManager mCpnm;
    private GestureDetectorCompat mDetector;
    UserExtendedInfo mExtInfo = null;
    private boolean mIsOnboarding = false;
    NativeManager mNm;
    CarpoolRide mRide = null;
    CarpoolUserData mUser = null;

    class C15341 extends SimpleOnGestureListener {
        C15341() throws  {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float $f1) throws  {
            if ($f1 > 13000.0f) {
                CarpoolRiderProfileActivity.this.finish();
            }
            return false;
        }
    }

    class C15352 implements OnClickListener {
        C15352() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolRiderProfileActivity.this.showBottomSheet(CarpoolRiderProfileActivity.this.mRide, CarpoolRiderProfileActivity.this.mUser);
        }
    }

    class C15439 implements SimpleBottomSheetListener {
        final /* synthetic */ OnClickListener[] val$onClick;

        C15439(OnClickListener[] $r2) throws  {
            this.val$onClick = $r2;
        }

        public void onComplete(SimpleBottomSheetValue $r1) throws  {
            String $r4;
            C15439 $r2;
            this = this;
            if (CarpoolRiderProfileActivity.this.mRide == null) {
                $r4 = "";
            } else {
                this = this;
                $r4 = CarpoolRiderProfileActivity.this.mRide.getId();
            }
            switch ($r1.id) {
                case 0:
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED, "ACTION|RIDE_ID", "CALL|" + $r4);
                    if (this.val$onClick[0] != null) {
                        this.val$onClick[0].onClick(null);
                        break;
                    }
                    break;
                case 1:
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED, "ACTION|RIDE_ID", "MESSAGE|" + $r4);
                    if (this.val$onClick[1] != null) {
                        this.val$onClick[1].onClick(null);
                        break;
                    }
                    break;
                case 2:
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED, "ACTION|RIDE_ID", "BLOCK|" + $r4);
                    $r2 = this;
                    this = $r2;
                    CarpoolRiderProfileActivity.reportRider(CarpoolRiderProfileActivity.this, CarpoolRiderProfileActivity.this.mRide, CarpoolRiderProfileActivity.this.mUser, 0, DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_BLOCK);
                    break;
                default:
                    break;
            }
            $r2 = this;
            this = $r2;
            CarpoolRiderProfileActivity.this.mBottomSheet.dismiss();
        }
    }

    void setVerifications(java.lang.String r25, int r26, boolean r27, java.lang.String r28, boolean r29, java.lang.String r30, int r31, com.waze.carpool.CarpoolUserSocialNetworks[] r32) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:24:0x017d
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
        r24 = this;
        r0 = r24;
        r1 = r32;
        r0.setupSocialProfiles(r1);
        r3 = 2131691843; // 0x7f0f0943 float:1.901277E38 double:1.053195707E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r5 = r2;
        r5 = (android.widget.TextView) r5;
        r4 = r5;
        r3 = 2122; // 0x84a float:2.974E-42 double:1.0484E-320;
        r30 = com.waze.strings.DisplayStrings.displayString(r3);
        r0 = r30;
        r4.setText(r0);
        r0 = r24;
        r6 = r0.mUser;
        r7 = r6.total_carpooled_meters_pax;
        if (r7 <= 0) goto L_0x018c;
    L_0x0027:
        r3 = 2131691844; // 0x7f0f0944 float:1.9012771E38 double:1.0531957076E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r3 = 0;
        r2.setVisibility(r3);
        r8 = com.waze.share.ShareNativeManager.getInstance();
        r9 = r8.isMetricUnitsNTV();
        r0 = r24;
        r6 = r0.mUser;
        r7 = r6.total_carpooled_meters_pax;
        r10 = (float) r7;
        if (r9 == 0) goto L_0x0181;
    L_0x0045:
        r11 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
    L_0x0048:
        r10 = r11 * r10;
        r12 = 1140457472; // 0x43fa0000 float:500.0 double:5.634608575E-315;
        r10 = r10 + r12;
        r12 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r10 = r10 / r12;
        r7 = (int) r10;
        r3 = 2;
        r13 = new java.lang.Object[r3];
        r14 = java.lang.Integer.valueOf(r7);
        r3 = 0;
        r13[r3] = r14;
        if (r9 == 0) goto L_0x0189;
    L_0x005f:
        r15 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
    L_0x0061:
        r30 = com.waze.strings.DisplayStrings.displayString(r15);
        r3 = 1;
        r13[r3] = r30;
        r3 = 2128; // 0x850 float:2.982E-42 double:1.0514E-320;
        r30 = com.waze.strings.DisplayStrings.displayStringF(r3, r13);
        r3 = 2131691844; // 0x7f0f0944 float:1.9012771E38 double:1.0531957076E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r16 = r2;
        r16 = (android.widget.TextView) r16;
        r4 = r16;
        r0 = r30;
        r4.setText(r0);
    L_0x0082:
        r3 = 2131691835; // 0x7f0f093b float:1.9012753E38 double:1.053195703E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r17 = r2;
        r17 = (android.widget.TextView) r17;
        r4 = r17;
        if (r25 == 0) goto L_0x019f;
    L_0x0093:
        r0 = r25;
        r9 = r0.isEmpty();
        if (r9 != 0) goto L_0x019f;
    L_0x009b:
        r0 = r24;
        r0 = r0.mNm;
        r18 = r0;
        r3 = 1;
        r13 = new java.lang.Object[r3];
        r3 = 0;
        r13[r3] = r25;
        r3 = 2123; // 0x84b float:2.975E-42 double:1.049E-320;
        r0 = r18;
        r25 = r0.getFormattedString(r3, r13);
        r0 = r25;
        r4.setText(r0);
    L_0x00b4:
        if (r27 == 0) goto L_0x01f3;
    L_0x00b6:
        r3 = 2131691845; // 0x7f0f0945 float:1.9012773E38 double:1.053195708E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r19 = r2;
        r19 = (android.widget.TextView) r19;
        r4 = r19;
        r0 = r24;
        r0 = r0.mNm;
        r18 = r0;
        r3 = 2130; // 0x852 float:2.985E-42 double:1.0524E-320;
        r0 = r18;
        r25 = r0.getLanguageString(r3);
        r0 = r25;
        r4.setText(r0);
    L_0x00d8:
        r3 = 2131691846; // 0x7f0f0946 float:1.9012775E38 double:1.0531957086E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r20 = r2;
        r20 = (android.widget.TextView) r20;
        r4 = r20;
        r0 = r28;
        r4.setText(r0);
        if (r29 == 0) goto L_0x0206;
    L_0x00ee:
        r3 = 2131691847; // 0x7f0f0947 float:1.9012777E38 double:1.053195709E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r21 = r2;
        r21 = (android.widget.TextView) r21;
        r4 = r21;
        r0 = r24;
        r0 = r0.mNm;
        r18 = r0;
        r3 = 2131; // 0x853 float:2.986E-42 double:1.053E-320;
        r0 = r18;
        r25 = r0.getLanguageString(r3);
        r0 = r25;
        r4.setText(r0);
    L_0x0110:
        if (r26 <= 0) goto L_0x0219;
    L_0x0112:
        r3 = 2131691848; // 0x7f0f0948 float:1.901278E38 double:1.0531957096E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r22 = r2;
        r22 = (android.widget.TextView) r22;
        r4 = r22;
        r0 = r24;
        r0 = r0.mNm;
        r18 = r0;
        r3 = 2124; // 0x84c float:2.976E-42 double:1.0494E-320;
        r0 = r18;
        r25 = r0.getLanguageString(r3);
        r3 = 1;
        r13 = new java.lang.Object[r3];
        r0 = r26;
        r14 = java.lang.Integer.valueOf(r0);
        r3 = 0;
        r13[r3] = r14;
        r0 = r25;
        r25 = java.lang.String.format(r0, r13);
        r0 = r25;
        r4.setText(r0);
    L_0x0146:
        if (r31 <= 0) goto L_0x0228;
    L_0x0148:
        r3 = 2131691849; // 0x7f0f0949 float:1.9012781E38 double:1.05319571E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r23 = r2;
        r23 = (android.widget.TextView) r23;
        r4 = r23;
        r0 = r24;
        r0 = r0.mNm;
        r18 = r0;
        r3 = 2126; // 0x84e float:2.979E-42 double:1.0504E-320;
        r0 = r18;
        r25 = r0.getLanguageString(r3);
        r3 = 1;
        r13 = new java.lang.Object[r3];
        r0 = r31;
        r14 = java.lang.Integer.valueOf(r0);
        r3 = 0;
        r13[r3] = r14;
        r0 = r25;
        r25 = java.lang.String.format(r0, r13);
        r0 = r25;
        r4.setText(r0);
        return;
        goto L_0x0181;
    L_0x017e:
        goto L_0x0048;
    L_0x0181:
        r11 = 1059000875; // 0x3f1f122b float:0.621371 double:5.232159513E-315;
        goto L_0x017e;
        goto L_0x0189;
    L_0x0186:
        goto L_0x0061;
    L_0x0189:
        r15 = 464; // 0x1d0 float:6.5E-43 double:2.29E-321;
        goto L_0x0186;
    L_0x018c:
        r3 = 2131691844; // 0x7f0f0944 float:1.9012771E38 double:1.0531957076E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        goto L_0x0199;
    L_0x0196:
        goto L_0x0082;
    L_0x0199:
        r3 = 8;
        r2.setVisibility(r3);
        goto L_0x0196;
    L_0x019f:
        r0 = r24;
        r6 = r0.mUser;
        r9 = r6.work_email_verified;
        if (r9 == 0) goto L_0x01ed;
    L_0x01a7:
        r0 = r24;
        r6 = r0.mUser;
        r0 = r6.work_email;
        r25 = r0;
        if (r25 == 0) goto L_0x01ed;
    L_0x01b1:
        goto L_0x01b5;
    L_0x01b2:
        goto L_0x0146;
    L_0x01b5:
        r0 = r24;
        r6 = r0.mUser;
        r0 = r6.work_email;
        r25 = r0;
        r9 = r0.isEmpty();
        if (r9 != 0) goto L_0x01ed;
    L_0x01c3:
        r0 = r24;
        r0 = r0.mNm;
        r18 = r0;
        r3 = 1;
        r13 = new java.lang.Object[r3];
        r0 = r24;
        r6 = r0.mUser;
        r0 = r6.work_email;
        r25 = r0;
        r3 = 0;
        r13[r3] = r25;
        r3 = 2134; // 0x856 float:2.99E-42 double:1.0543E-320;
        r0 = r18;
        r25 = r0.getFormattedString(r3, r13);
        goto L_0x01e3;
    L_0x01e0:
        goto L_0x00b4;
    L_0x01e3:
        r0 = r25;
        r4.setText(r0);
        goto L_0x01e0;
        goto L_0x01ed;
    L_0x01ea:
        goto L_0x00b4;
    L_0x01ed:
        r3 = 8;
        r4.setVisibility(r3);
        goto L_0x01ea;
    L_0x01f3:
        r3 = 2131691845; // 0x7f0f0945 float:1.9012773E38 double:1.053195708E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        goto L_0x0200;
    L_0x01fd:
        goto L_0x00d8;
    L_0x0200:
        r3 = 8;
        r2.setVisibility(r3);
        goto L_0x01fd;
    L_0x0206:
        r3 = 2131691847; // 0x7f0f0947 float:1.9012777E38 double:1.053195709E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        goto L_0x0213;
    L_0x0210:
        goto L_0x0110;
    L_0x0213:
        r3 = 8;
        r2.setVisibility(r3);
        goto L_0x0210;
    L_0x0219:
        r3 = 2131691848; // 0x7f0f0948 float:1.901278E38 double:1.0531957096E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r3 = 8;
        r2.setVisibility(r3);
        goto L_0x01b2;
    L_0x0228:
        r3 = 2131691849; // 0x7f0f0949 float:1.9012781E38 double:1.05319571E-314;
        r0 = r24;
        r2 = r0.findViewById(r3);
        r3 = 8;
        r2.setVisibility(r3);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRiderProfileActivity.setVerifications(java.lang.String, int, boolean, java.lang.String, boolean, java.lang.String, int, com.waze.carpool.CarpoolUserSocialNetworks[]):void");
    }

    void showBottomSheet(com.waze.carpool.CarpoolRide r25, com.waze.carpool.CarpoolUserData r26) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:17:0x0136
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
        r24 = this;
        if (r25 == 0) goto L_0x020d;
    L_0x0002:
        if (r26 != 0) goto L_0x0005;
    L_0x0004:
        return;
    L_0x0005:
        r3 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r4 = r3.isMessagingEnabled();
        r6 = 5;
        r5 = com.waze.config.ConfigValues.getBoolValue(r6);
        if (r5 == 0) goto L_0x013a;
    L_0x0014:
        r0 = r25;
        r7 = r0.proxy_number;
        if (r7 == 0) goto L_0x013a;
    L_0x001a:
        r5 = 1;
    L_0x001b:
        r6 = 3;
        r8 = new com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue[r6];
        r9 = new com.waze.reports.SimpleBottomSheet$SimpleBottomSheetValue;
        r6 = 2411; // 0x96b float:3.379E-42 double:1.191E-320;
        r7 = com.waze.strings.DisplayStrings.displayString(r6);
        r0 = r24;
        r10 = r0.getResources();
        r6 = 2130837592; // 0x7f020058 float:1.7280142E38 double:1.052773651E-314;
        r11 = r10.getDrawable(r6);
        r6 = 0;
        r9.<init>(r6, r7, r11);
        r6 = 0;
        r8[r6] = r9;
        r9 = new com.waze.reports.SimpleBottomSheet$SimpleBottomSheetValue;
        r6 = 3202; // 0xc82 float:4.487E-42 double:1.582E-320;
        r7 = com.waze.strings.DisplayStrings.displayString(r6);
        r0 = r24;
        r10 = r0.getResources();
        r6 = 2130837594; // 0x7f02005a float:1.7280146E38 double:1.052773652E-314;
        r11 = r10.getDrawable(r6);
        r6 = 1;
        r9.<init>(r6, r7, r11);
        r6 = 1;
        r8[r6] = r9;
        r9 = new com.waze.reports.SimpleBottomSheet$SimpleBottomSheetValue;
        r6 = 1896; // 0x768 float:2.657E-42 double:9.367E-321;
        r7 = com.waze.strings.DisplayStrings.displayString(r6);
        r0 = r24;
        r10 = r0.getResources();
        r6 = 2130837870; // 0x7f02016e float:1.7280706E38 double:1.0527737884E-314;
        r11 = r10.getDrawable(r6);
        r6 = 2;
        r9.<init>(r6, r7, r11);
        r6 = 2;
        r8[r6] = r9;
        r6 = 2;
        r9 = r8[r6];
        r0 = r24;
        r3 = r0.mCpnm;
        r0 = r24;
        r12 = r0.mUser;
        r7 = r12.id;
        r13 = r3.isRiderBlocked(r7);
        r9.disabled = r13;
        r6 = 3;
        r14 = new android.view.View.OnClickListener[r6];
        r16 = "RW_IN_APP_MSG_CLICKED";
        r0 = r16;
        r15 = com.waze.analytics.AnalyticsBuilder.analytics(r0);
        r16 = "TYPE";
        r17 = "PROFILE";
        r0 = r16;
        r1 = r17;
        r15 = r15.addParam(r0, r1);
        r0 = r25;
        r7 = r0.getId();
        r16 = "RIDE_ID";
        r0 = r16;
        r15 = r15.addParam(r0, r7);
        r16 = "RW_CONTACT_RIDER_CLICKED";
        r0 = r16;
        r18 = com.waze.analytics.AnalyticsBuilder.analytics(r0);
        r16 = "TYPE";
        r17 = "PROFILE";
        r0 = r18;
        r1 = r16;
        r2 = r17;
        r18 = r0.addParam(r1, r2);
        r0 = r25;
        r7 = r0.getId();
        r16 = "RIDE_ID";
        r0 = r18;
        r1 = r16;
        r18 = r0.addParam(r1, r7);
        r0 = r25;
        r0 = r0.state;
        r19 = r0;
        r6 = 1;
        r0 = r19;
        if (r0 == r6) goto L_0x00e7;
    L_0x00db:
        r0 = r25;
        r0 = r0.state;
        r19 = r0;
        r6 = 13;
        r0 = r19;
        if (r0 != r6) goto L_0x0154;
    L_0x00e7:
        if (r4 == 0) goto L_0x013c;
    L_0x00e9:
        r21 = 0;
        r6 = 0;
        r0 = r25;
        r1 = r26;
        r2 = r21;
        r20 = getChatListener(r0, r1, r15, r2, r6);
        r6 = 1;
        r14[r6] = r20;
    L_0x00f9:
        r6 = 0;
        r9 = r8[r6];
        r6 = 1;
        r9.disabled = r6;
    L_0x00ff:
        r22 = new com.waze.reports.SimpleBottomSheet;
        r23 = new com.waze.carpool.CarpoolRiderProfileActivity$9;
        r0 = r23;
        r1 = r24;
        r0.<init>(r14);
        r6 = 1895; // 0x767 float:2.655E-42 double:9.363E-321;
        r0 = r22;
        r1 = r24;
        r2 = r23;
        r0.<init>(r1, r6, r8, r2);
        r0 = r22;
        r1 = r24;
        r1.mBottomSheet = r0;
        r0 = r24;
        r0 = r0.mBottomSheet;
        r22 = r0;
        r0 = r26;
        r7 = r0.getName();
        r0 = r22;
        r0.setTitleStr(r7);
        r0 = r24;
        r0 = r0.mBottomSheet;
        r22 = r0;
        r0.show();
        return;
        goto L_0x013a;
    L_0x0137:
        goto L_0x001b;
    L_0x013a:
        r5 = 0;
        goto L_0x0137;
    L_0x013c:
        r0 = r25;
        r7 = r0.getId();
        goto L_0x0146;
    L_0x0143:
        goto L_0x00ff;
    L_0x0146:
        r6 = 2407; // 0x967 float:3.373E-42 double:1.189E-320;
        r20 = getErrorMessageListener(r6, r7, r15);
        r6 = 1;
        r14[r6] = r20;
        goto L_0x0153;
    L_0x0150:
        goto L_0x00ff;
    L_0x0153:
        goto L_0x00f9;
    L_0x0154:
        r0 = r25;
        r0 = r0.state;
        r19 = r0;
        r6 = 4;
        r0 = r19;
        if (r0 == r6) goto L_0x0173;
    L_0x015f:
        r0 = r25;
        r0 = r0.state;
        r19 = r0;
        goto L_0x016d;
    L_0x0166:
        goto L_0x00ff;
        goto L_0x016d;
    L_0x016a:
        goto L_0x00ff;
    L_0x016d:
        r6 = 9;
        r0 = r19;
        if (r0 != r6) goto L_0x01a3;
    L_0x0173:
        if (r4 == 0) goto L_0x0190;
    L_0x0175:
        r0 = r25;
        r1 = r26;
        r20 = getChatListener(r0, r1, r15);
        r6 = 1;
        r14[r6] = r20;
    L_0x0180:
        if (r5 == 0) goto L_0x019c;
    L_0x0182:
        r0 = r25;
        r7 = r0.proxy_number;
        r0 = r18;
        r20 = getCallListener(r7, r0);
        r6 = 0;
        r14[r6] = r20;
        goto L_0x0143;
    L_0x0190:
        r0 = r25;
        r7 = r0.proxy_number;
        r20 = getSmsListener(r7);
        r6 = 1;
        r14[r6] = r20;
        goto L_0x0180;
    L_0x019c:
        r6 = 0;
        r9 = r8[r6];
        r6 = 1;
        r9.disabled = r6;
        goto L_0x0150;
    L_0x01a3:
        r0 = r25;
        r0 = r0.state;
        r19 = r0;
        r6 = 2;
        r0 = r19;
        if (r0 == r6) goto L_0x01ba;
    L_0x01ae:
        r0 = r25;
        r0 = r0.state;
        r19 = r0;
        r6 = 15;
        r0 = r19;
        if (r0 != r6) goto L_0x01d5;
    L_0x01ba:
        r6 = 0;
        r9 = r8[r6];
        r6 = 1;
        r9.disabled = r6;
        if (r4 == 0) goto L_0x01ce;
    L_0x01c2:
        r0 = r25;
        r1 = r26;
        r20 = getChatListener(r0, r1, r15);
        r6 = 1;
        r14[r6] = r20;
        goto L_0x0166;
    L_0x01ce:
        r6 = 1;
        r9 = r8[r6];
        r6 = 1;
        r9.disabled = r6;
        goto L_0x016a;
    L_0x01d5:
        if (r4 == 0) goto L_0x01f6;
    L_0x01d7:
        r0 = r25;
        r1 = r26;
        r20 = getChatListener(r0, r1, r15);
        r6 = 1;
        r14[r6] = r20;
    L_0x01e2:
        if (r5 == 0) goto L_0x0202;
    L_0x01e4:
        r0 = r25;
        r7 = r0.proxy_number;
        r0 = r18;
        r20 = getCallListener(r7, r0);
        goto L_0x01f2;
    L_0x01ef:
        goto L_0x00ff;
    L_0x01f2:
        r6 = 0;
        r14[r6] = r20;
        goto L_0x01ef;
    L_0x01f6:
        r0 = r25;
        r7 = r0.proxy_number;
        r20 = getSmsListener(r7);
        r6 = 1;
        r14[r6] = r20;
        goto L_0x01e2;
    L_0x0202:
        r6 = 0;
        r9 = r8[r6];
        goto L_0x0209;
    L_0x0206:
        goto L_0x00ff;
    L_0x0209:
        r6 = 1;
        r9.disabled = r6;
        goto L_0x0206;
    L_0x020d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRiderProfileActivity.showBottomSheet(com.waze.carpool.CarpoolRide, com.waze.carpool.CarpoolUserData):void");
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        this.mCpnm = CarpoolNativeManager.getInstance();
        getWindow().setSoftInputMode(3);
        requestWindowFeature(1);
        setContentView(C1283R.layout.rider_profile);
        Intent $r6 = getIntent();
        if ($r6 != null && $r6.hasExtra("CarpoolUserData")) {
            this.mUser = (CarpoolUserData) $r6.getParcelableExtra("CarpoolUserData");
            this.mRide = (CarpoolRide) $r6.getParcelableExtra("CarpoolRide");
            this.mIsOnboarding = $r6.getBooleanExtra("onboarding", false);
        }
        setupActivity();
        String $r10 = "STARS|RIDES";
        String $r12 = this.mUser.star_rating_as_pax + "|" + this.mUser.completed_rides_pax;
        String $r13 = $r12;
        if (this.mRide != null) {
            $r10 = "STARS|RIDES" + "|RIDE_ID";
            $r13 = $r12 + "|" + this.mRide.getId();
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_SHOWN, $r10, $r13);
        this.mDetector = new GestureDetectorCompat(this, new C15341());
        CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_EXT_USER, this.mHandler);
        CarpoolNativeManager.getInstance().getExtUserObject(this.mUser.id);
    }

    protected void onDestroy() throws  {
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_EXT_USER, this.mHandler);
        super.onDestroy();
    }

    private void setupActivity() throws  {
        if (this.mUser == null) {
            finish();
            return;
        }
        int $i0 = 0;
        if (this.mUser.waze_join_date_sec > 0) {
            Calendar $r2 = Calendar.getInstance();
            $r2.setTimeInMillis(this.mUser.waze_join_date_sec * 1000);
            $i0 = $r2.get(1);
        }
        int $i3 = 0;
        if (this.mUser.join_time_utc_seconds > 0) {
            $r2 = Calendar.getInstance();
            $r2.setTimeInMillis(this.mUser.join_time_utc_seconds * 1000);
            $i3 = $r2.get(1);
        }
        setRiderDetails(this.mUser.getName(), this.mUser.getFullImage(), this.mUser.getWorkplace(), this.mUser.star_rating_as_pax, this.mUser.completed_rides_pax, this.mUser.getFirstName());
        setVerifications(this.mUser.organization, $i0, this.mUser.confirmed_credit_card, null, true, this.mUser.work_email, $i3, this.mUser.getRiderSocialNetworks());
        ((TextView) findViewById(C1283R.id.riderProfileVerfEmail)).setText(this.mNm.getLanguageString(DisplayStrings.DS_RIDER_PROF_VER_EMAIL));
        setupButtons();
        updateEndorsements();
    }

    void setRiderDetails(String $r1, String imageUrl, String $r3, float $f0, int $i0, String $r4) throws  {
        findViewById(C1283R.id.riderOptionsButton).setOnClickListener(new C15352());
        ((TextView) findViewById(C1283R.id.riderIs)).setText(DisplayStrings.displayString(DisplayStrings.DS_RIDER_PROF_RIDER_IS));
        ((TextView) findViewById(C1283R.id.riderName)).setText($r1);
        $r1 = this.mUser.getFullImage();
        final TextView $r8 = (TextView) findViewById(C1283R.id.riderNoPhotoText);
        $r8.setText(DisplayStrings.displayStringF(DisplayStrings.DS_RIDER_PROF_RIDER_NO_PHOTO_PS, new Object[]{this.mUser.getFirstName()}));
        final ProgressAnimation $r11 = (ProgressAnimation) findViewById(C1283R.id.riderImageProgress);
        final C15363 $r5 = new Runnable() {
            public void run() throws  {
                $r8.setVisibility(0);
                $r11.setVisibility(8);
            }
        };
        if ($r1 == null || $r1.isEmpty()) {
            $r5.run();
        } else {
            $r11.start();
            postDelayed($r5, 5000);
            VolleyManager.getInstance().loadImageFromUrl($r1, new ImageRequestListener() {
                public void onImageLoadComplete(final Bitmap $r1, Object token, long duration) throws  {
                    CarpoolRiderProfileActivity.this.cancel($r5);
                    ImageView $r7 = (ImageView) CarpoolRiderProfileActivity.this.findViewById(C1283R.id.riderPhoto);
                    BitmapDrawable $r3 = new BitmapDrawable($r1);
                    $r7.setImageDrawable($r3);
                    MaterialDesignImageAnimationHelper.animateImageEntrance($r3, 1500);
                    $r7.setOnClickListener(new OnClickListener() {
                        public void onClick(View $r1) throws  {
                            CarpoolRiderProfileActivity.this.zoomRiderImage($r1, $r1);
                        }
                    });
                }

                public void onImageLoadFailed(Object token, long duration) throws  {
                    CarpoolRiderProfileActivity.this.cancel($r5);
                    $r5.run();
                }
            });
        }
        boolean $z0 = false;
        if ($r3 == null || $r3.isEmpty()) {
            findViewById(C1283R.id.rideRequestRiderWork).setVisibility(8);
        } else {
            $r8 = (TextView) findViewById(C1283R.id.rideRequestRiderWork);
            $r8.setVisibility(0);
            $r8.setText(DisplayStrings.displayStringF(DisplayStrings.DS_DRIVER_PROFILE_WORKPLACE_COMPANY_PS, new Object[]{$r3}));
            $z0 = true;
        }
        findViewById(C1283R.id.rideRequestRiderHome).setVisibility(8);
        findViewById(C1283R.id.rideRequestRiderSchool).setVisibility(8);
        if (!$z0) {
            findViewById(C1283R.id.riderProfileDetailsSep).setVisibility(8);
        }
        CarpoolUtils.setStarsView($f0, $i0, findViewById(C1283R.id.rideRequestRiderRating), $r4);
        $r8 = (TextView) findViewById(C1283R.id.riderProfileMotto);
        if (this.mUser.motto != null) {
            String $r12 = this.mUser.motto;
            if (!$r12.isEmpty()) {
                $r8.setText(String.format(this.mNm.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_MOTTO_PS), new Object[]{this.mUser.motto}));
                findViewById(C1283R.id.riderTogether).setVisibility(8);
            }
        }
        $r8.setVisibility(8);
        findViewById(C1283R.id.riderTogether).setVisibility(8);
    }

    private void updateEndorsements() throws  {
        LinearLayout $r2 = (LinearLayout) findViewById(C1283R.id.endorsements);
        if (this.mExtInfo == null || !this.mExtInfo.hasEndorsements()) {
            $r2.setVisibility(8);
            return;
        }
        $r2.setVisibility(0);
        $r2 = (LinearLayout) $r2.getChildAt(1);
        String $r4 = DisplayStrings.displayString(DisplayStrings.DS_DRIVER_PROFILE_ENDORSEMENTS_PS);
        for (int $i0 = 1; $i0 <= 3; $i0++) {
            LinearLayout $r5 = (LinearLayout) $r2.getChildAt(($i0 * 2) - 1);
            if (this.mExtInfo.endorsement_count[$i0] > 0) {
                $r5.setVisibility(0);
                TextView $r8 = (TextView) $r5.getChildAt(1);
                TextView $r9 = (TextView) $r5.getChildAt(3);
                ((ImageView) $r5.getChildAt(0)).setImageResource(CarpoolEndorsement.icon[$i0]);
                $r9.setText(String.format($r4, new Object[]{DisplayStrings.displayString(CarpoolEndorsement.name[$i0])}));
                $r8.setText(Integer.toString(this.mExtInfo.endorsement_count[$i0]));
            } else {
                $r5.setVisibility(8);
            }
        }
    }

    private static OnClickListener getErrorMessageListener(final int $i0, String rideId, final AnalyticsBuilder $r1) throws  {
        return new OnClickListener() {
            public void onClick(View v) throws  {
                NativeManager $r2 = NativeManager.getInstance();
                MsgBox.openMessageBox($r2.getLanguageString($i0), $r2.getLanguageString((int) DisplayStrings.DS_CARPOOL_CONTACTING_NOT_ALLOWED_STATE), false);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_CONTACT_RIDER);
                if ($r1 != null) {
                    $r1.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_STATUS, AnalyticsEvents.ANALYTICS_EVENT_VALUE_DISABLE).send();
                }
            }
        };
    }

    static OnClickListener getChatListener(CarpoolRide $r0, CarpoolUserData $r1, AnalyticsBuilder $r2) throws  {
        return getChatListener($r0, $r1, $r2, null, false);
    }

    static OnClickListener getChatListener(CarpoolRide $r0, CarpoolUserData $r1, AnalyticsBuilder $r2, INextActionCallback $r3, boolean $z0) throws  {
        final AnalyticsBuilder analyticsBuilder = $r2;
        final CarpoolRide carpoolRide = $r0;
        final CarpoolUserData carpoolUserData = $r1;
        final INextActionCallback iNextActionCallback = $r3;
        final boolean z = $z0;
        return new OnClickListener() {
            public void onClick(View v) throws  {
                if (analyticsBuilder != null) {
                    analyticsBuilder.send();
                }
                CarpoolRiderProfileActivity.startChat(carpoolRide, carpoolUserData, iNextActionCallback, z);
            }
        };
    }

    public static void startChat(CarpoolRide $r0, CarpoolUserData $r1, INextActionCallback $r2, boolean $z0) throws  {
        if ($r2 != null) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, $r2);
            return;
        }
        NativeManager.getInstance();
        Intent $r3 = new Intent(AppService.getActiveActivity(), CarpoolMessagingActivity.class);
        $r3.putExtra("rider", $r1);
        $r3.putExtra("ride", $r0);
        $r3.putExtra("openKeyboard", $z0);
        $r3.setFlags(131072);
        AppService.getActiveActivity().startActivity($r3);
    }

    private static OnClickListener getSmsListener(final String $r0) throws  {
        return new OnClickListener() {
            public void onClick(View v) throws  {
                AppService.getActiveActivity().startActivity(new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + $r0)));
            }
        };
    }

    private static OnClickListener getCallListener(final String $r0, final AnalyticsBuilder $r1) throws  {
        return new OnClickListener() {
            public void onClick(View v) throws  {
                if ($r1 != null) {
                    $r1.send();
                }
                AppService.getActiveActivity().startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + $r0)));
            }
        };
    }

    static void setContactRiderButtons(CarpoolRide $r0, CarpoolUserData $r1, View $r2, @Nullable View $r3, AnalyticsBuilder $r4, AnalyticsBuilder $r5, INextActionCallback $r6) throws  {
        if ($r0 == null || $r1 == null) {
            if ($r2 != null) {
                $r2.setVisibility(8);
            }
            if ($r3 != null) {
                $r3.setVisibility(8);
                return;
            }
            return;
        }
        boolean $z0 = CarpoolNativeManager.getInstance().isMessagingEnabled();
        boolean $z1 = ConfigValues.getBoolValue(5) && $r0.proxy_number != null;
        if ($r0.state == 1 || $r0.state == 13) {
            if ($z0) {
                $r2.setOnClickListener(getChatListener($r0, $r1, $r4, $r6, false));
            } else {
                $r2.setOnClickListener(getErrorMessageListener(DisplayStrings.DS_CARPOOL_CONTACTING_NOT_ALLOWED_STATE_TITLE, $r0.getId(), $r4));
            }
            if ($r3 != null) {
                $r3.setVisibility(8);
            }
        } else if ($r0.state == 4 || $r0.state == 9) {
            if ($z0) {
                $r2.setOnClickListener(getChatListener($r0, $r1, $r4));
            } else {
                $r2.setOnClickListener(getSmsListener($r0.proxy_number));
            }
            if ($r3 == null) {
                return;
            }
            if ($z1) {
                $r3.setOnClickListener(getCallListener($r0.proxy_number, $r5));
            } else {
                $r3.setVisibility(8);
            }
        } else if ($r0.state == 2 || $r0.state == 15) {
            if ($r3 != null) {
                $r3.setVisibility(8);
            }
            if ($z0) {
                $r2.setOnClickListener(getChatListener($r0, $r1, $r4));
            } else {
                $r2.setEnabled(false);
            }
        } else {
            if ($z0) {
                $r2.setOnClickListener(getChatListener($r0, $r1, $r4));
            } else {
                $r2.setOnClickListener(getSmsListener($r0.proxy_number));
            }
            if ($r3 == null) {
                return;
            }
            if ($z1) {
                $r3.setOnClickListener(getCallListener($r0.proxy_number, $r5));
            } else {
                $r3.setVisibility(8);
            }
        }
    }

    private void setupSocialProfiles(CarpoolUserSocialNetworks[] $r1) throws  {
        boolean $z0 = false;
        boolean $z1 = false;
        WazeTextView $r4 = (WazeTextView) findViewById(C1283R.id.riderProfileFacebook);
        WazeTextView $r5 = (WazeTextView) findViewById(C1283R.id.riderProfileLinkedin);
        if ($r1 != null) {
            for (int $i0 = 0; $i0 < $r1.length; $i0++) {
                if ($r1[$i0] == null) {
                    Logger.m43w("CarpoolRiderProfileActivity: Social network is null in position " + $i0);
                } else {
                    String $r2 = $r1[$i0].profile_url;
                    String $r8;
                    CharSequence spannableString;
                    final String str;
                    if ($r1[$i0].network_type == 0) {
                        if (!($r2 == null || $r2.isEmpty())) {
                            $r8 = $r1[$i0].name;
                            spannableString = new SpannableString($r8);
                            spannableString.setSpan(new UnderlineSpan(), 0, $r8.length(), 0);
                            $r4.setText(spannableString);
                            $r4.setTextColor(getResources().getColor(C1283R.color.FacebookBlue));
                            $r4.setFont(11, 0);
                            $r4.setTextSize(1, 17.0f);
                            $r4.setVisibility(0);
                            $z0 = true;
                            str = $r2;
                            $r4.setOnClickListener(new OnClickListener() {
                                public void onClick(View v) throws  {
                                    Intent $r8;
                                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED, "ACTION|RIDE_ID", "FB_CONNECTED|" + (CarpoolRiderProfileActivity.this.mRide == null ? "" : CarpoolRiderProfileActivity.this.mRide.getId()));
                                    String $r5 = "";
                                    try {
                                        String $r7 = "fb://facewebmodal/f?href=" + str;
                                        $r5 = str;
                                        $r8 = new Intent("android.intent.action.VIEW", Uri.parse($r7));
                                        $r8.addFlags(268435456);
                                        Logger.m41i("FB profile rider: from server: " + str + "; app: " + $r7 + "; web: " + $r5);
                                        AppService.getAppContext().startActivity($r8);
                                    } catch (Exception e) {
                                        Logger.m43w("No FB App found opening: " + $r5);
                                        $r8 = new Intent(CarpoolRiderProfileActivity.this, FacebookLikeActivity.class);
                                        $r8.putExtra(ShareConstants.SHARE_EXTRA_ID_LIKE_URL, $r5);
                                        $r8.putExtra(ShareConstants.SHARE_EXTRA_ID_LIKE_TITLE, CarpoolRiderProfileActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_DRIVER_PROFILE_FACEBOOK_TITLE));
                                        CarpoolRiderProfileActivity.this.startActivityForResult($r8, 0);
                                    }
                                }
                            });
                        }
                    } else if (!($r1[$i0].network_type != 1 || $r2 == null || $r2.isEmpty())) {
                        $r8 = $r1[$i0].name;
                        spannableString = new SpannableString($r8);
                        spannableString.setSpan(new UnderlineSpan(), 0, $r8.length(), 0);
                        $r5.setText(spannableString);
                        $r5.setTextColor(getResources().getColor(C1283R.color.LinkedinBlue));
                        $r5.setFont(11, 0);
                        $r5.setTextSize(1, 17.0f);
                        $r5.setVisibility(0);
                        $z1 = true;
                        str = $r2;
                        $r5.setOnClickListener(new OnClickListener() {
                            public void onClick(View v) throws  {
                                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED, "ACTION|RIDE_ID", "LINKEDIN_CONNECTED|" + (CarpoolRiderProfileActivity.this.mRide == null ? "" : CarpoolRiderProfileActivity.this.mRide.getId()));
                                Logger.m43w("LI profile opening: " + str);
                                Intent $r8;
                                try {
                                    CarpoolRiderProfileActivity.this.getPackageManager().getPackageInfo(ShareConstants.SHARE_LINKEDIN_PACKAGE_NAME, 0);
                                    $r8 = new Intent("android.intent.action.VIEW");
                                    $r8.setData(Uri.parse(str));
                                    CarpoolRiderProfileActivity.this.startActivity($r8);
                                } catch (NameNotFoundException e) {
                                    $r8 = new Intent(CarpoolRiderProfileActivity.this, LinkedinProfileActivity.class);
                                    $r8.putExtra(ShareConstants.SHARE_EXTRA_LI_PROFILE_URL, str);
                                    $r8.putExtra(ShareConstants.SHARE_EXTRA_LI_PROFILE_TITLE, CarpoolRiderProfileActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_DRIVER_PROFILE_LINKEDIN_TITLE));
                                    CarpoolRiderProfileActivity.this.startActivityForResult($r8, 0);
                                }
                            }
                        });
                    }
                }
            }
        }
        if (!$z1) {
            $r5.setVisibility(8);
            findViewById(C1283R.id.riderProfileSepLinkedin).setVisibility(8);
        }
        if (!$z0) {
            $r4.setVisibility(8);
            findViewById(C1283R.id.riderProfileSepFacebook).setVisibility(8);
        }
    }

    private void setDisabled(int $i0, int $i1) throws  {
        TextView $r2 = (TextView) findViewById($i0);
        $r2.setText(this.mNm.getLanguageString($i1));
        Drawable[] $r5 = $r2.getCompoundDrawables();
        int $i2 = getResources().getColor(C1283R.color.Light);
        $r2.setTextColor(Color.argb(128, Color.red($i2), Color.green($i2), Color.blue($i2)));
        $r2.setCompoundDrawables($r5[0], null, null, null);
    }

    private void setupButtons() throws  {
        TextView $r2 = (TextView) findViewById(C1283R.id.riderProfileButProblem);
        boolean $z0 = this.mCpnm.isRiderBlocked(this.mUser.id);
        if (this.mIsOnboarding) {
            $r2.setVisibility(8);
        } else if ($z0) {
            $r2.setText(DisplayStrings.displayString(DisplayStrings.DS_RIDER_PROF_RIDER_BLOCKED));
        } else {
            $r2.setText(EditTextUtils.underlineSpan(DisplayStrings.DS_CARPOOL_REPORT_USER_BUTTON_TITLE));
            $r2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED, "ACTION|RIDE_ID", "BLOCK|" + (CarpoolRiderProfileActivity.this.mRide == null ? "" : CarpoolRiderProfileActivity.this.mRide.getId()));
                    CarpoolRiderProfileActivity.selectReport(CarpoolRiderProfileActivity.this, CarpoolRiderProfileActivity.this.mRide, CarpoolRiderProfileActivity.this.mUser);
                }
            });
        }
        findViewById(C1283R.id.riderButtonBack).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                CarpoolRiderProfileActivity.this.onBackPressed();
            }
        });
    }

    static void selectReport(final ActivityBase $r0, final CarpoolRide $r1, final CarpoolUserData $r2) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_REPORT_PROBLEM).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, $r1.getId()).send();
        final int[] $r4 = new int[]{DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_BOTHER, DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_SPAM, DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_OFFENSIVE_CONTENT, DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_HACKED, DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_BLOCK};
        BottomSheet $r3 = new BottomSheet($r0, DisplayStrings.DS_CARPOOL_REPORT_USER_OPTIONS_TITLE, Mode.COLUMN_TEXT);
        $r3.setAdapter(new Adapter() {
            public int getCount() throws  {
                return $r4.length;
            }

            public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                $r1.setItem($r4[$i0]);
            }

            public void onClick(int $i0) throws  {
                byte $b1 = (byte) 0;
                String $r2 = "";
                switch ($r4[$i0]) {
                    case DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_BOTHER /*3366*/:
                        $b1 = (byte) 1;
                        $r2 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_BOTHER;
                        break;
                    case DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_SPAM /*3367*/:
                        $b1 = (byte) 2;
                        $r2 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_SPAM;
                        break;
                    case DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_OFFENSIVE_CONTENT /*3368*/:
                        $b1 = (byte) 3;
                        $r2 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_ABUSE;
                        break;
                    case DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_HACKED /*3369*/:
                        $b1 = (byte) 4;
                        $r2 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_HACKED;
                        break;
                    default:
                        break;
                }
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_OPTION).addParam("ACTION", $r2).send();
                CarpoolRiderProfileActivity.reportRider($r0, $r1, $r2, $b1, $r4[$i0]);
            }
        });
        $r3.show();
    }

    static void reportRider(ActivityBase $r0, CarpoolRide $r1, CarpoolUserData $r2, int $i0, int $i1) throws  {
        boolean $z0;
        if ($i1 == DisplayStrings.DS_CARPOOL_REPORT_USER_OPTION_BLOCK) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        ReportRiderDialog $r3 = new ReportRiderDialog($r0, $r2);
        $r3.setType(DisplayStrings.displayString($i1));
        $r3.setBlock($z0);
        $r3.setMaxLength(ConfigValues.getIntValue(45));
        final ReportRiderDialog reportRiderDialog = $r3;
        final ActivityBase activityBase = $r0;
        final CarpoolUserData carpoolUserData = $r2;
        final CarpoolRide carpoolRide = $r1;
        final int i = $i0;
        $r3.setOnOk(new OnClickListener() {
            public void onClick(View v) throws  {
                int $i0;
                boolean $z0 = reportRiderDialog.getIsChecked();
                String $r4 = reportRiderDialog.getText();
                final NativeManager $r5 = NativeManager.getInstance();
                final CarpoolNativeManager $r6 = CarpoolNativeManager.getInstance();
                final MicroHandler $r2 = new MicroHandler();
                $r2.setCallback(new MicroHandlerCallback() {

                    class C15311 implements Runnable {
                        C15311() throws  {
                        }

                        public void run() throws  {
                            $r5.CloseProgressPopup();
                            activityBase.setResult(-1);
                            activityBase.finish();
                        }
                    }

                    class C15322 implements DialogInterface.OnClickListener {
                        C15322() throws  {
                        }

                        public void onClick(DialogInterface dialog, int which) throws  {
                            activityBase.finish();
                        }
                    }

                    public void handleMessage(Message $r1) throws  {
                        int $i0 = $r1;
                        $r1 = $i0;
                        if ($i0.what == CarpoolNativeManager.UH_CARPOOL_BLOCK_USER_RES) {
                            $r5.CloseProgressPopup();
                            $r6.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_BLOCK_USER_RES, $r2);
                            if ($r1.getData().getInt("res") == 0) {
                                $r5.OpenProgressIconPopup($r5.getLanguageString((int) DisplayStrings.DS_CARPOOL_BLOCK_OK), "sign_up_big_v");
                                $r2.postDelayed(new C15311(), 2000);
                                return;
                            }
                            MsgBox.openMessageBoxTimeout($r5.getLanguageString((int) DisplayStrings.DS_UHHOHE), $r5.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C15322());
                        }
                    }
                });
                if ($z0) {
                    $i0 = CarpoolNativeManager.UH_CARPOOL_BLOCK_USER_RES;
                } else {
                    $i0 = CarpoolNativeManager.UH_CARPOOL_REPORT_USER_RES;
                }
                $r6.setUpdateHandler($i0, $r2);
                String $r9 = carpoolUserData.id;
                CarpoolRide $r10 = carpoolRide;
                $r6.reportUser($r9, $r10.getId(), i, $r4, $z0);
                if ($z0) {
                    $r6.blockUser(carpoolUserData.id);
                }
                $r5.OpenProgressPopup($r5.getLanguageString(290));
            }
        });
        $r3.show();
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_REPORT_USER_RES) {
            this.mNm.CloseProgressPopup();
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_REPORT_USER_RES, this.mHandler);
            if ($r1.getData().getInt("res") == 0) {
                final NativeManager $r3 = NativeManager.getInstance();
                $r3.OpenProgressIconPopup($r3.getLanguageString((int) DisplayStrings.DS_CARPOOL_REPORT_USER_DIALOG_CONFIRMATION), "sign_up_big_v");
                postDelayed(new Runnable() {
                    public void run() throws  {
                        $r3.CloseProgressPopup();
                        CarpoolRiderProfileActivity.this.setResult(-1);
                        CarpoolRiderProfileActivity.this.finish();
                    }
                }, 2000);
            } else {
                MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) throws  {
                        CarpoolRiderProfileActivity.this.finish();
                    }
                });
            }
            return true;
        }
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_EXT_USER) {
            this.mExtInfo = (UserExtendedInfo) $r1.getData().getParcelable(CarpoolNativeManager.BUNDLE_EXT_INFO);
            updateEndorsements();
        }
        return super.myHandleMessage($r1);
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        this.mDetector.onTouchEvent($r1);
        return super.dispatchTouchEvent($r1);
    }

    private void zoomRiderImage(View $r1, Bitmap $r2) throws  {
        float height;
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
        final ImageView $r9 = (ImageView) findViewById(C1283R.id.riderProfileZoomedRider);
        $r9.setVisibility(0);
        Rect $r6 = $r3;
        Rect $r3 = new Rect();
        $r3 = r17;
        Rect r17 = new Rect();
        Point $r4 = r18;
        Point r18 = new Point();
        $r1.getGlobalVisibleRect($r6);
        findViewById(C1283R.id.theScrollView).getGlobalVisibleRect($r3, $r4);
        $r6.offset(-$r4.x, -$r4.y);
        $r3.offset(-$r4.x, -$r4.y);
        if ($r2 != null) {
            int $i0 = ((($r6.height() * $r2.getWidth()) / $r2.getHeight()) - $r6.width()) / 2;
            $r6.set($r6.left - $i0, $r6.top, $r6.right + $i0, $r6.bottom);
            $r9.setImageBitmap($r2);
        }
        float $f0;
        float $f2;
        float f;
        if (((float) $r3.width()) / ((float) $r3.height()) > ((float) $r6.width()) / ((float) $r6.height())) {
            height = ((float) $r6.height()) / ((float) $r3.height());
            $f0 = ((height * ((float) $r3.width())) - ((float) $r6.width())) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            $f2 = (float) $r6.left;
            $f2 -= $f0;
            f = $f2;
            $r6.left = (int) $f2;
            $r6.right = (int) (((float) $r6.right) + $f0);
        } else {
            height = ((float) $r6.width()) / ((float) $r3.width());
            $f0 = ((height * ((float) $r3.height())) - ((float) $r6.height())) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            $f2 = (float) $r6.top;
            $f2 -= $f0;
            f = $f2;
            $r6.top = (int) $f2;
            $r6.bottom = (int) (((float) $r6.bottom) + $f0);
        }
        $r1 = findViewById(C1283R.id.riderProfileZoomedRiderBackground);
        $r9.setPivotX(0.0f);
        $r9.setPivotY(0.0f);
        Animator $r5 = r0;
        Animator animatorSet = new AnimatorSet();
        $r5.play(ObjectAnimator.ofFloat($r9, View.X, new float[]{(float) $r6.left, (float) $r3.left})).with(ObjectAnimator.ofFloat($r9, View.Y, new float[]{(float) $r6.top, (float) $r3.top})).with(ObjectAnimator.ofFloat($r9, View.SCALE_X, new float[]{height, 1.06535322E9f})).with(ObjectAnimator.ofFloat($r9, View.SCALE_Y, new float[]{height, 1.06535322E9f})).with(ObjectAnimator.ofFloat($r1, View.ALPHA, new float[]{0.0f, 1.0f}));
        $r5.setDuration(400);
        $r5.setInterpolator(new OvershootInterpolator(1.0f));
        final View view = $r1;
        $r5.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator $r1) throws  {
                super.onAnimationEnd($r1);
                CarpoolRiderProfileActivity.this.mAnimator = null;
            }

            public void onAnimationStart(Animator $r1) throws  {
                super.onAnimationStart($r1);
                view.setVisibility(0);
            }
        });
        $r5.start();
        this.mAnimator = $r5;
        view = $r1;
        $r1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                $r9.setVisibility(8);
                view.setVisibility(8);
            }
        });
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
    }
}
