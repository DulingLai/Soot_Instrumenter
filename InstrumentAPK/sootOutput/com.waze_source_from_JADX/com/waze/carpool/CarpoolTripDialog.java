package com.waze.carpool;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.login.widget.ToolTipPopup;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ChatNotificationManager;
import com.waze.ChatNotificationManager.ChatHandler;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager.CarpoolRidePickupMeetingDetails;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolUtils.RiderImageAndMessageCounter;
import com.waze.ifs.async.UpdateHandlers.MicroHandler;
import com.waze.ifs.async.UpdateHandlers.MicroHandler.MicroHandlerCallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.map.CanvasFont;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.anim.ParticleSystem;
import com.waze.view.popups.UpcomingCarpoolBar;
import com.waze.view.timer.TimerBar;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TimeZone;

public class CarpoolTripDialog extends Dialog implements ITrackOrientation, MicroHandlerCallback {
    public static final int ANIM_CELEBRATION_DURATION_MILLIS = 300;
    public static final int ANIM_STEP_DURATION_MILLIS = 250;
    private static final int CANCEL_RIDE = 4;
    public static final float MAX_IMAGE_SHIFT = 0.375f;
    private static final int NO_SHOW = 3;
    public static final int STROKE_DP = 4;
    private static final int VIEW_RIDE = 2;
    private float[] imageShiftsWithDriver;
    private float[] imageShiftsWithoutDriver;
    private ActivityBase mActivity;
    private View mAltStartButton;
    private TextView mAltStartText;
    private final boolean mArrived;
    private View mBottomButtons;
    private View mCallButton;
    private TextView mCallText;
    private ChatHandler mChatHandler;
    @Nullable
    private View mCpBar;
    @Nullable
    private View mCpBarButton;
    private CarpoolNativeManager mCpnm;
    private final CarpoolRidePickupMeetingDetails mDetails;
    private CarpoolDrive mDrive;
    private final int mDriveState;
    private ImageView mDriverImage;
    private Runnable mGetRideRunnable;
    private boolean mGotImgsFromMeeting = false;
    private boolean mIsMultipax;
    private View mLaterButton;
    private TextView mLaterText;
    private TextView mMessageBadge;
    private View mMessageButton;
    private TextView mMessageText;
    private FrameLayout mPicLayout;
    @Nullable
    private TextView mPickup;
    private View mProblemButton;
    private TextView mProblemText;
    HashMap<String, RiderImageAndMessageCounter> mRiderImageAndMessageCounter = new HashMap();
    private HashMap<String, ImageView> mRiderImages = new HashMap(8);
    private ArrayList<ImageView> mRiderImagesInOrder = new ArrayList(8);
    private View mStartButton;
    private TextView mStartText;
    @Nullable
    private TextView mSubTitle;
    @Nullable
    private TextView mTakeoverAltText;
    @Nullable
    private TextView mTakeoverText;
    @Nullable
    private TextView mTitle;
    private MicroHandler microHandler;
    private boolean showDriver = false;

    class C16021 implements Runnable {
        C16021() throws  {
        }

        public void run() throws  {
            CarpoolTripDialog.this.getDriveData();
        }
    }

    class AnonymousClass28 implements ImageRequestListener {
        final /* synthetic */ ImageView val$riderImage;

        AnonymousClass28(ImageView $r2) throws  {
            this.val$riderImage = $r2;
        }

        public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
            this.val$riderImage.setImageDrawable(new CircleFrameDrawable($r1, 0, 4));
        }

        public void onImageLoadFailed(Object token, long duration) throws  {
        }
    }

    class C16043 implements OnClickListener {
        C16043() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolTripDialog.this.animateOut();
        }
    }

    class C16074 implements ChatHandler {

        class C16051 implements Runnable {
            C16051() throws  {
            }

            public void run() throws  {
                CarpoolTripDialog.this.updateChatBadge();
            }
        }

        class C16062 implements Runnable {
            C16062() throws  {
            }

            public void run() throws  {
                CarpoolTripDialog.this.updateChatBadge();
            }
        }

        C16074() throws  {
        }

        public boolean onChatMessage(String message) throws  {
            AppService.Post(new C16051());
            return false;
        }

        public void onMessagesLoaded() throws  {
            AppService.Post(new C16062());
        }

        public void onMessageSent(boolean success) throws  {
        }

        public void onMessageRead(String msgId) throws  {
        }
    }

    class C16085 implements OnClickListener {
        C16085() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolTripDialog.this.confirmPickup();
        }
    }

    class C16107 implements OnClickListener {
        C16107() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolTripDialog.this.confirmDropOff();
        }
    }

    private void initLayout() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:26:0x024b
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
        r39 = this;
        r2 = 1;
        r3 = "CarpoolTripDialog: initLayout";
        com.waze.Logger.m36d(r3);
        r0 = r39;
        r4 = r0.mDriveState;
        r5 = 7;
        if (r4 != r5) goto L_0x024f;
    L_0x000d:
        r6 = 1;
    L_0x000e:
        r0 = r39;
        r0.calcShifts(r6);
        if (r6 == 0) goto L_0x0255;
    L_0x0015:
        r4 = 2130903136; // 0x7f030060 float:1.7413081E38 double:1.052806034E-314;
    L_0x0018:
        r0 = r39;
        r0.setContentView(r4);
        r0 = r39;
        r7 = r0.getWindow();
        if (r7 == 0) goto L_0x002a;
    L_0x0025:
        r5 = -1;
        r8 = -1;
        r7.setLayout(r5, r8);
    L_0x002a:
        r3 = "RW_MEETUP_SHOWN";
        r9 = com.waze.analytics.AnalyticsBuilder.analytics(r3);
        r0 = r39;
        r10 = r0.mDrive;
        if (r10 == 0) goto L_0x0259;
    L_0x0036:
        r0 = r39;
        r10 = r0.mDrive;
        r11 = r10.getId();
    L_0x003e:
        r3 = "DRIVE_ID";
        r9 = r9.addParam(r3, r11);
        r0 = r39;
        r10 = r0.mDrive;
        if (r10 == 0) goto L_0x0266;
    L_0x004a:
        r0 = r39;
        r10 = r0.mDrive;
        r4 = r10.getRidesAmount();
    L_0x0052:
        r12 = (long) r4;
        r3 = "NUM_RIDERS";
        r9 = r9.addParam(r3, r12);
        r9.send();
        r5 = 2131690273; // 0x7f0f0321 float:1.9009585E38 double:1.0531949315E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r16 = r14;
        r16 = (android.widget.TextView) r16;
        r15 = r16;
        r0 = r39;
        r0.mTitle = r15;
        r5 = 2131690274; // 0x7f0f0322 float:1.9009587E38 double:1.053194932E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r17 = r14;
        r17 = (android.widget.TextView) r17;
        r15 = r17;
        r0 = r39;
        r0.mSubTitle = r15;
        r5 = 2131690270; // 0x7f0f031e float:1.9009579E38 double:1.05319493E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r19 = r14;
        r19 = (android.widget.ImageView) r19;
        r18 = r19;
        r0 = r18;
        r1 = r39;
        r1.mDriverImage = r0;
        r5 = 2131690275; // 0x7f0f0323 float:1.900959E38 double:1.0531949325E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r20 = r14;
        r20 = (android.widget.TextView) r20;
        r15 = r20;
        r0 = r39;
        r0.mPickup = r15;
        r5 = 2131690271; // 0x7f0f031f float:1.900958E38 double:1.0531949305E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r21 = r14;
        r21 = (android.widget.TextView) r21;
        r15 = r21;
        r0 = r39;
        r0.mTakeoverText = r15;
        r5 = 2131690272; // 0x7f0f0320 float:1.9009583E38 double:1.053194931E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r22 = r14;
        r22 = (android.widget.TextView) r22;
        r15 = r22;
        r0 = r39;
        r0.mTakeoverAltText = r15;
        r5 = 2131690259; // 0x7f0f0313 float:1.9009557E38 double:1.0531949245E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mCallButton = r14;
        r5 = 2131690260; // 0x7f0f0314 float:1.9009559E38 double:1.053194925E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r23 = r14;
        r23 = (android.widget.TextView) r23;
        r15 = r23;
        r0 = r39;
        r0.mCallText = r15;
        r5 = 2131690261; // 0x7f0f0315 float:1.900956E38 double:1.0531949255E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mMessageButton = r14;
        r5 = 2131690263; // 0x7f0f0317 float:1.9009565E38 double:1.0531949265E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r24 = r14;
        r24 = (android.widget.TextView) r24;
        r15 = r24;
        r0 = r39;
        r0.mMessageText = r15;
        r5 = 2131690262; // 0x7f0f0316 float:1.9009563E38 double:1.053194926E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r25 = r14;
        r25 = (android.widget.TextView) r25;
        r15 = r25;
        r0 = r39;
        r0.mMessageBadge = r15;
        r5 = 2131690264; // 0x7f0f0318 float:1.9009567E38 double:1.053194927E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mProblemButton = r14;
        r5 = 2131690265; // 0x7f0f0319 float:1.9009569E38 double:1.0531949275E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r26 = r14;
        r26 = (android.widget.TextView) r26;
        r15 = r26;
        r0 = r39;
        r0.mProblemText = r15;
        r5 = 2131690250; // 0x7f0f030a float:1.9009538E38 double:1.05319492E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mBottomButtons = r14;
        r5 = 2131690252; // 0x7f0f030c float:1.9009542E38 double:1.053194921E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mLaterButton = r14;
        r5 = 2131690253; // 0x7f0f030d float:1.9009544E38 double:1.0531949216E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r27 = r14;
        r27 = (android.widget.TextView) r27;
        r15 = r27;
        r0 = r39;
        r0.mLaterText = r15;
        r5 = 2131690256; // 0x7f0f0310 float:1.900955E38 double:1.053194923E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mStartButton = r14;
        r5 = 2131690257; // 0x7f0f0311 float:1.9009553E38 double:1.0531949236E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r28 = r14;
        r28 = (android.widget.TextView) r28;
        r15 = r28;
        r0 = r39;
        r0.mStartText = r15;
        r5 = 2131690254; // 0x7f0f030e float:1.9009546E38 double:1.053194922E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mAltStartButton = r14;
        r5 = 2131690255; // 0x7f0f030f float:1.9009548E38 double:1.0531949226E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r29 = r14;
        r29 = (android.widget.TextView) r29;
        r15 = r29;
        r0 = r39;
        r0.mAltStartText = r15;
        r5 = 2131690266; // 0x7f0f031a float:1.900957E38 double:1.053194928E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r31 = r14;
        r31 = (android.widget.FrameLayout) r31;
        r30 = r31;
        r0 = r30;
        r1 = r39;
        r1.mPicLayout = r0;
        r0 = r39;
        r0 = r0.mRiderImages;
        r32 = r0;
        r0.clear();
        r0 = r39;
        r0 = r0.mRiderImagesInOrder;
        r33 = r0;
        r0.clear();
        r5 = 2131692482; // 0x7f0f0bc2 float:1.9014065E38 double:1.053196023E-314;
        r0 = r39;
        r14 = r0.findViewById(r5);
        r0 = r39;
        r0.mCpBar = r14;
        r0 = r39;
        r14 = r0.mCpBar;
        if (r14 == 0) goto L_0x0273;
    L_0x01ea:
        r0 = r39;
        r14 = r0.mCpBar;
        r0 = r39;
        r4 = r0.mDriveState;
        r5 = 0;
        com.waze.view.popups.UpcomingCarpoolBar.setStateAndText(r14, r4, r5);
        r0 = r39;
        r14 = r0.mCpBar;
        r5 = 2131692485; // 0x7f0f0bc5 float:1.9014071E38 double:1.0531960243E-314;
        r14 = r14.findViewById(r5);
        r0 = r39;
        r0.mCpBarButton = r14;
        r0 = r39;
        r14 = r0.mCpBar;
        r34 = new com.waze.carpool.CarpoolTripDialog$3;
        r0 = r34;
        r1 = r39;
        r0.<init>();
        r0 = r34;
        r14.setOnClickListener(r0);
    L_0x0217:
        r0 = r39;
        r10 = r0.mDrive;
        if (r10 == 0) goto L_0x027c;
    L_0x021d:
        r0 = r39;
        r10 = r0.mDrive;
        r2 = r10.isMultiPax();
        r0 = r39;
        r0.mIsMultipax = r2;
    L_0x0229:
        r0 = r39;
        r0.setupControlButtons();
        r0 = r39;
        r0 = r0.mCpnm;
        r35 = r0;
        r36 = r0.getCarpoolProfileNTV();
        if (r36 == 0) goto L_0x029c;
    L_0x023a:
        r0 = r36;
        r11 = r0.getImage();
    L_0x0240:
        r0 = r39;
        r0.setDriverImage(r11);
        r0 = r39;
        r0.driveStateSetup();
        return;
        goto L_0x024f;
    L_0x024c:
        goto L_0x000e;
    L_0x024f:
        r6 = 0;
        goto L_0x024c;
        goto L_0x0255;
    L_0x0252:
        goto L_0x0018;
    L_0x0255:
        r4 = 2130903135; // 0x7f03005f float:1.741308E38 double:1.0528060336E-314;
        goto L_0x0252;
    L_0x0259:
        r0 = r39;
        r0 = r0.mDetails;
        r37 = r0;
        goto L_0x0263;
    L_0x0260:
        goto L_0x003e;
    L_0x0263:
        r11 = r0.meetingId;
        goto L_0x0260;
    L_0x0266:
        r0 = r39;
        r0 = r0.mDetails;
        r37 = r0;
        goto L_0x0270;
    L_0x026d:
        goto L_0x0052;
    L_0x0270:
        r4 = r0.numPax;
        goto L_0x026d;
    L_0x0273:
        r38 = 0;
        r0 = r38;
        r1 = r39;
        r1.mCpBarButton = r0;
        goto L_0x0217;
    L_0x027c:
        r0 = r39;
        r0 = r0.mDetails;
        r37 = r0;
        if (r37 == 0) goto L_0x0296;
    L_0x0284:
        r0 = r39;
        r0 = r0.mDetails;
        r37 = r0;
        r4 = r0.numPax;
        r5 = 1;
        if (r4 <= r5) goto L_0x0294;
    L_0x028f:
        r0 = r39;
        r0.mIsMultipax = r2;
        goto L_0x0229;
    L_0x0294:
        r2 = 0;
        goto L_0x028f;
    L_0x0296:
        r5 = 0;
        r0 = r39;
        r0.mIsMultipax = r5;
        goto L_0x0229;
    L_0x029c:
        r11 = 0;
        goto L_0x0240;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolTripDialog.initLayout():void");
    }

    private void setRidersImages() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:22:0x011a in {4, 10, 11, 17, 18, 21, 23, 27, 41, 42, 46, 52, 54, 55, 56, 57} preds:[]
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
        r43 = this;
        r0 = r43;
        r7 = r0.mActivity;
        r8 = r7.getResources();
        r10 = 2130838951; // 0x7f0205a7 float:1.7282899E38 double:1.0527743225E-314;
        r9 = android.graphics.BitmapFactory.decodeResource(r8, r10);
        r0 = r43;
        r11 = r0.mDriverImage;
        r12 = r11.getLayoutParams();
        r14 = r12;
        r14 = (android.widget.FrameLayout.LayoutParams) r14;
        r13 = r14;
        r0 = r43;
        r15 = r0.mDrive;
        if (r15 == 0) goto L_0x01cf;
    L_0x0021:
        r0 = r43;
        r15 = r0.mDrive;
        r16 = r15.getRidesAmount();
        if (r16 != 0) goto L_0x002c;
    L_0x002b:
        return;
    L_0x002c:
        r0 = r43;
        r0 = r0.mGotImgsFromMeeting;
        r17 = r0;
        if (r17 == 0) goto L_0x007a;
    L_0x0034:
        r18 = "CarpoolTripDialog: clearing imgs from meeting details so can take from Driver";
        r0 = r18;
        com.waze.Logger.m43w(r0);
        r10 = 0;
        r0 = r43;
        r0.mGotImgsFromMeeting = r10;
        r0 = r43;
        r0 = r0.mRiderImagesInOrder;
        r19 = r0;
        r20 = r0.iterator();
    L_0x004a:
        r0 = r20;
        r17 = r0.hasNext();
        if (r17 == 0) goto L_0x0068;
    L_0x0052:
        r0 = r20;
        r21 = r0.next();
        r22 = r21;
        r22 = (android.widget.ImageView) r22;
        r11 = r22;
        r0 = r43;
        r0 = r0.mPicLayout;
        r23 = r0;
        r0.removeView(r11);
        goto L_0x004a;
    L_0x0068:
        r0 = r43;
        r0 = r0.mRiderImages;
        r24 = r0;
        r0.clear();
        r0 = r43;
        r0 = r0.mRiderImagesInOrder;
        r19 = r0;
        r0.clear();
    L_0x007a:
        r25 = 0;
    L_0x007c:
        r0 = r25;
        r1 = r16;
        if (r0 >= r1) goto L_0x033b;
    L_0x0082:
        r0 = r43;
        r15 = r0.mDrive;
        r0 = r25;
        r26 = r15.getRider(r0);
        if (r26 != 0) goto L_0x00cf;
    L_0x008e:
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r18 = "CarpoolTripDialog: rider is null in drive=";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r43;
        r15 = r0.mDrive;
        r28 = r15.getId();
        r0 = r27;
        r1 = r28;
        r27 = r0.append(r1);
        r18 = "position=";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r27;
        r1 = r25;
        r27 = r0.append(r1);
        r0 = r27;
        r28 = r0.toString();
        r0 = r28;
        com.waze.Logger.m38e(r0);
    L_0x00cc:
        r25 = r25 + 1;
        goto L_0x007c;
    L_0x00cf:
        r0 = r43;
        r0 = r0.mRiderImages;
        r24 = r0;
        r0 = r26;
        r0 = r0.id;
        r28 = r0;
        r0 = r24;
        r1 = r28;
        r17 = r0.containsKey(r1);
        if (r17 == 0) goto L_0x011e;
    L_0x00e5:
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r18 = "CarpoolTripDialog: rider ";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r26;
        r0 = r0.id;
        r28 = r0;
        r0 = r27;
        r1 = r28;
        r27 = r0.append(r1);
        r18 = " already has an image";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r27;
        r28 = r0.toString();
        r0 = r28;
        com.waze.Logger.m36d(r0);
        goto L_0x00cc;
        goto L_0x011e;
    L_0x011b:
        goto L_0x00cc;
    L_0x011e:
        r11 = new android.widget.ImageView;
        r0 = r43;
        r7 = r0.mActivity;
        r11.<init>(r7);
        r12 = com.waze.Utils.duplicateLayoutParams(r13);
        r30 = r12;
        r30 = (android.widget.FrameLayout.LayoutParams) r30;
        r29 = r30;
        if (r29 != 0) goto L_0x0176;
    L_0x0133:
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r18 = "CarpoolTripDialog: rider layout parmas are null in drive=";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r43;
        r15 = r0.mDrive;
        goto L_0x014c;
    L_0x0149:
        goto L_0x00cc;
    L_0x014c:
        r28 = r15.getId();
        r0 = r27;
        r1 = r28;
        r27 = r0.append(r1);
        r18 = "position=";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r27;
        r1 = r25;
        r27 = r0.append(r1);
        r0 = r27;
        r28 = r0.toString();
        r0 = r28;
        com.waze.Logger.m38e(r0);
        goto L_0x011b;
    L_0x0176:
        r11.setLayoutParams(r13);
        r0 = r43;
        r0 = r0.mPicLayout;
        r23 = r0;
        r0.addView(r11);
        r0 = r43;
        r0 = r0.mRiderImages;
        r24 = r0;
        goto L_0x018c;
    L_0x0189:
        goto L_0x0149;
    L_0x018c:
        r0 = r26;
        r0 = r0.id;
        r28 = r0;
        r0 = r24;
        r1 = r28;
        r0.put(r1, r11);
        r0 = r43;
        r0 = r0.mRiderImagesInOrder;
        r19 = r0;
        r10 = 0;
        r0 = r19;
        r0.add(r10, r11);
        r0 = r43;
        r0 = r0.mRiderImageAndMessageCounter;
        r24 = r0;
        r31 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r0 = r43;
        r15 = r0.mDrive;
        r0 = r25;
        r32 = r15.getRide(r0);
        r0 = r31;
        r1 = r32;
        r33 = r0.getUnreadChatMessageCount(r1);
        r10 = 4;
        r0 = r26;
        r1 = r24;
        r2 = r11;
        r3 = r9;
        r4 = r33;
        r5 = r10;
        com.waze.carpool.CarpoolUtils.initRiderImagesAndMsgCounts(r0, r1, r2, r3, r4, r5);
        goto L_0x0189;
    L_0x01cf:
        r0 = r43;
        r0 = r0.mDetails;
        r34 = r0;
        if (r34 == 0) goto L_0x033c;
    L_0x01d7:
        r0 = r43;
        r0 = r0.mDetails;
        r34 = r0;
        r0 = r0.meetingImagesUrl;
        r35 = r0;
        if (r35 == 0) goto L_0x033d;
    L_0x01e3:
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r18 = "CarpoolTripDialog: adding imgs from meeting details pax num is ";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r43;
        r0 = r0.mDetails;
        r34 = r0;
        r0 = r0.numPax;
        r16 = r0;
        r0 = r27;
        r1 = r16;
        r27 = r0.append(r1);
        r0 = r27;
        r28 = r0.toString();
        r0 = r28;
        com.waze.Logger.m43w(r0);
        r16 = 0;
    L_0x0213:
        r0 = r43;
        r0 = r0.mDetails;
        r34 = r0;
        r0 = r0.numPax;
        r25 = r0;
        r0 = r16;
        r1 = r25;
        if (r0 >= r1) goto L_0x033e;
    L_0x0223:
        r10 = 1;
        r0 = r43;
        r0.mGotImgsFromMeeting = r10;
        r11 = new android.widget.ImageView;
        r0 = r43;
        r7 = r0.mActivity;
        r11.<init>(r7);
        r12 = com.waze.Utils.duplicateLayoutParams(r13);
        r36 = r12;
        r36 = (android.widget.FrameLayout.LayoutParams) r36;
        r29 = r36;
        if (r29 != 0) goto L_0x027e;
    L_0x023d:
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r18 = "CarpoolTripDialog: rider layout parmas are null in drive=";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r43;
        r15 = r0.mDrive;
        r28 = r15.getId();
        r0 = r27;
        r1 = r28;
        r27 = r0.append(r1);
        r18 = "position=";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r27;
        r1 = r16;
        r27 = r0.append(r1);
        r0 = r27;
        r28 = r0.toString();
        r0 = r28;
        com.waze.Logger.m38e(r0);
    L_0x027b:
        r16 = r16 + 1;
        goto L_0x0213;
    L_0x027e:
        r11.setLayoutParams(r13);
        r0 = r43;
        r0 = r0.mPicLayout;
        r23 = r0;
        r0.addView(r11);
        r37 = new com.waze.ifs.ui.CircleFrameDrawable;
        r10 = 0;
        r38 = 4;
        r0 = r37;
        r1 = r38;
        r0.<init>(r9, r10, r1);
        r0 = r37;
        r11.setImageDrawable(r0);
        r10 = 1;
        r39 = 0;
        r0 = r39;
        r11.setLayerType(r10, r0);
        r0 = r43;
        r0 = r0.mRiderImages;
        r24 = r0;
        r28 = r11.toString();
        r0 = r24;
        r1 = r28;
        r0.put(r1, r11);
        r0 = r43;
        r0 = r0.mRiderImagesInOrder;
        r19 = r0;
        r10 = 0;
        r0 = r19;
        r0.add(r10, r11);
        r0 = r43;
        r0 = r0.mDetails;
        r34 = r0;
        r0 = r0.meetingImagesUrl;
        r35 = r0;
        r0 = r0.length;
        r25 = r0;
        r1 = r16;
        if (r0 >= r1) goto L_0x027b;
    L_0x02d1:
        r0 = r43;
        r0 = r0.mDetails;
        r34 = r0;
        goto L_0x02db;
    L_0x02d8:
        goto L_0x027b;
    L_0x02db:
        r0 = r0.meetingImagesUrl;
        r35 = r0;
        r28 = r35[r16];
        if (r28 == 0) goto L_0x02eb;
    L_0x02e3:
        r0 = r28;
        r17 = r0.isEmpty();
        if (r17 == 0) goto L_0x0310;
    L_0x02eb:
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r18 = "CarpoolTripDialog: rider image from meeting details is null or empty ind=";
        r0 = r27;
        r1 = r18;
        r27 = r0.append(r1);
        r0 = r27;
        r1 = r16;
        r27 = r0.append(r1);
        r0 = r27;
        r28 = r0.toString();
        r0 = r28;
        com.waze.Logger.m38e(r0);
        return;
    L_0x0310:
        r40 = com.waze.utils.VolleyManager.getInstance();
        r41 = new com.waze.carpool.CarpoolTripDialog$28;
        r0 = r41;
        r1 = r43;
        r0.<init>(r11);
        r25 = r11.getWidth();
        r33 = r11.getHeight();
        r39 = 0;
        r42 = 0;
        r0 = r40;
        r1 = r28;
        r2 = r41;
        r3 = r39;
        r4 = r25;
        r5 = r33;
        r6 = r42;
        r0.loadImageFromUrl(r1, r2, r3, r4, r5, r6);
        goto L_0x02d8;
    L_0x033b:
        return;
    L_0x033c:
        return;
    L_0x033d:
        return;
    L_0x033e:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolTripDialog.setRidersImages():void");
    }

    public CarpoolTripDialog(ActivityBase $r1, CarpoolDrive $r2, CarpoolRidePickupMeetingDetails $r3, int $i0, boolean $z0) throws  {
        super($r1, C1283R.style.NoDimDialog);
        this.mActivity = $r1;
        this.mDetails = $r3;
        this.mArrived = $z0;
        this.mDriveState = $i0;
        this.mDrive = $r2;
        this.mCpnm = CarpoolNativeManager.getInstance();
        if (this.mActivity instanceof MainActivity) {
            ((MainActivity) this.mActivity).addOrientationTracker(this);
        }
        initLayout();
        if (CarpoolUtils.isDriveInvalid(this.mDrive)) {
            this.mGetRideRunnable = new C16021();
            AppService.Post(this.mGetRideRunnable, 500);
            return;
        }
        setupChatHandler();
    }

    public void handleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE) {
            Logger.m36d("CarpoolTripDialog: received UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE");
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE, this.microHandler);
            final NativeManager $r4 = NativeManager.getInstance();
            $r4.CloseProgressPopup();
            if ($r1.getData().getInt("res") == 0) {
                $r4.OpenProgressIconPopup($r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRMATION_NON_LAST_RIDER), "sign_up_big_v");
                this.microHandler.postDelayed(new Runnable() {
                    public void run() throws  {
                        $r4.CloseProgressPopup();
                    }
                }, 2000);
            } else {
                MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
            }
        }
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED) {
            Logger.m36d("CarpoolTripDialog: received UH_CARPOOL_DRIVE_UPDATED");
            CarpoolDrive $r10 = (CarpoolDrive) $r1.getData().getParcelable(CarpoolDrive.class.getSimpleName());
            CarpoolDrive carpoolDrive = this.mDrive;
            CarpoolDrive $r11 = carpoolDrive;
            if (CarpoolUtils.areSameDrives(carpoolDrive, $r10)) {
                if ($r10.getRidesAmount() == 0) {
                    dismiss();
                }
                if ($r10.getLiveRideState() == 8) {
                    carpoolDrive = this.mDrive;
                    if (carpoolDrive.getLiveRideState() == 16) {
                        Logger.m43w("CarpoolTripDialog: received UH_CARPOOL_DRIVE_UPDATED, with PAX picked up; Not updating layout;");
                        return;
                    }
                }
                this.mDrive = $r10;
                Logger.m36d("CarpoolTripDialog: received UH_CARPOOL_DRIVE_UPDATED, same drive, initing layout");
                initLayout();
            }
        }
    }

    private void calcShifts(boolean $z0) throws  {
        int $i0;
        int $i1 = getContext().getResources().getDisplayMetrics().widthPixels;
        if ($i1 > getContext().getResources().getDisplayMetrics().heightPixels) {
            $i0 = $i1 / 2;
        } else {
            $i0 = $i1;
        }
        $i1 = 0;
        if (this.mDrive != null) {
            $i1 = this.mDrive.getRidesAmount();
        } else if (this.mDetails != null) {
            $i1 = this.mDetails.numPax;
        }
        float $f1 = getContext().getResources().getDimension($z0 ? C1283R.dimen.carpool_trip_image_size : C1283R.dimen.carpool_to_image_size);
        $i0 -= PixelMeasure.dp(32);
        float $f0 = MAX_IMAGE_SHIFT * $f1;
        this.imageShiftsWithoutDriver = calcShiftsForImages($i0, $i1, $f1, $f0);
        this.imageShiftsWithDriver = calcShiftsForImages($i0, $i1 + 1, $f1, $f0);
    }

    private float[] calcShiftsForImages(int $i0, int $i1, float $f0, float $f1) throws  {
        float $f2 = (((float) $i1) * $f0) - (((float) ($i1 - 1)) * $f1);
        if ($f2 > ((float) $i0)) {
            $f2 = (float) $i0;
            $f1 = ((((float) $i1) * $f0) - ((float) $i0)) / ((float) ($i1 - 1));
        }
        float[] $r1 = new float[$i1];
        $f2 = ($f0 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) - ($f2 / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        for ($i0 = 0; $i0 < $i1; $i0++) {
            $r1[$i0] = (((float) $i0) * ($f0 - $f1)) + $f2;
        }
        return $r1;
    }

    private void setupChatHandler() throws  {
        updateChatBadge();
        if (this.mChatHandler == null) {
            this.mChatHandler = new C16074();
            if (this.mDrive.getRides() != null) {
                for (CarpoolRide $r1 : this.mDrive.getRides()) {
                    ChatNotificationManager.getInstance(true).setChatUpdateHandler($r1.getId(), this.mChatHandler);
                }
            }
            this.microHandler = new MicroHandler();
            this.microHandler.setCallback(this);
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.microHandler);
        }
    }

    private void driveStateSetup() throws  {
        String $r2;
        if (this.mDetails != null && this.mDetails.meetingPlaceName != null && !this.mDetails.meetingPlaceName.isEmpty()) {
            $r2 = this.mDetails.meetingPlaceName;
        } else if (this.mDrive == null || this.mDrive.getPickupString() == null) {
            $r2 = "";
        } else {
            $r2 = this.mDrive.getPickupString();
        }
        String $r3 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        if (this.mDrive != null) {
            $r3 = this.mDrive.getRiderName();
        }
        String $r5 = this.mDrive != null ? this.mDrive.getId() : this.mDetails != null ? this.mDetails.meetingId : "";
        if (this.mDriveState == 7) {
            setupConfirmedDrive($r2, $r3, $r5);
        } else {
            setupLiveDrive($r2, $r3, $r5);
        }
        setRidersImages();
    }

    private void setupLiveDrive(String $r1, String $r2, String $r3) throws  {
        this.mLaterButton.setVisibility(8);
        int $i0 = this.mDrive.getLiveRideState();
        if ($i0 == 10 || $i0 == 16) {
            if (this.mArrived || $i0 == 16) {
                if (this.mIsMultipax) {
                    this.mTakeoverText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_TITLE_MANY));
                } else {
                    this.mTakeoverText.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_TITLE_PS, new Object[]{$r2}));
                }
                this.mStartText.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.v_in_button, 0, 0, 0);
                this.mStartText.setCompoundDrawablePadding(PixelMeasure.dp(8));
                this.mStartButton.setBackgroundResource(C1283R.drawable.button_blue_bg);
                this.mStartText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_ACTION));
                this.mStartButton.setOnClickListener(new C16085());
                return;
            }
            if (this.mIsMultipax) {
                this.mTakeoverText.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_PICKUP_MANY_FROM_PS, new Object[]{NativeManager.getInstance().getLanguageString($r1)}));
                this.mStartText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_ARRIVED_TO_PICKUP_ACTION));
            } else {
                this.mTakeoverText.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_PICKUP_FROM_PS_PS, new Object[]{$r2, NativeManager.getInstance().getLanguageString($r1)}));
                this.mStartText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_ARRIVED_TO_PICKUP_ACTION));
            }
            this.mStartText.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.v_in_button, 0, 0, 0);
            this.mStartText.setCompoundDrawablePadding(PixelMeasure.dp(8));
            this.mStartButton.setBackgroundResource(C1283R.drawable.button_green_bg);
            final String str = $r3;
            final String str2 = $r2;
            this.mStartButton.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    CarpoolTripDialog.this.verifyArrivedMode(str, str2);
                }
            });
        } else if ($i0 == 8) {
            $r1 = "";
            if (!(this.mDrive == null || this.mDrive.getDropOffString() == null)) {
                $r1 = this.mDrive.getDropOffString();
            }
            if ($r1 == null || $r1.isEmpty()) {
                if (this.mIsMultipax) {
                    this.mTakeoverText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_DROPOFF_TITLE_MANY));
                } else {
                    TextView $r6 = this.mTakeoverText;
                    Object[] $r8 = new Object[1];
                    $r8[0] = $r2;
                    $r6.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_DROPOFF_TITLE_PS, $r8));
                }
            } else if (this.mIsMultipax) {
                this.mTakeoverText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_DROPOFF_TITLE_MANY));
            } else {
                this.mTakeoverText.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_DROPOFF_TITLE_PS_PS, new Object[]{$r2, NativeManager.getInstance().getLanguageString($r1)}));
            }
            this.mStartText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_DROPOFF_ACTION));
            this.mStartText.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.v_in_button, 0, 0, 0);
            this.mStartText.setCompoundDrawablePadding(PixelMeasure.dp(8));
            this.mStartButton.setBackgroundResource(C1283R.drawable.button_blue_bg);
            this.mStartButton.setOnClickListener(new C16107());
        }
    }

    private void setupConfirmedDrive(String $r1, String $r2, String $r3) throws  {
        this.mTitle.setText(NativeManager.getInstance().getTimeOfDayGreetingNTV());
        long $l0 = 0;
        if (this.mDrive != null) {
            $l0 = this.mDrive.getTime();
        }
        if ($l0 != 0) {
            TimeZone $r9 = Calendar.getInstance().getTimeZone();
            DateFormat $r11 = android.text.format.DateFormat.getTimeFormat(AppService.getAppContext());
            $r11.setTimeZone($r9);
            String $r6 = $r11.format(new Date(1000 * $l0));
            this.mSubTitle.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_START_CARPOOL_SUB_TITLE_PS, new Object[]{$r6}));
        } else {
            this.mSubTitle.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_START_CARPOOL_SUB_TITLE));
        }
        if ($r1 == null || $r1.isEmpty()) {
            if (this.mIsMultipax) {
                this.mPickup.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_PICKUP_MANY, new Object[null]));
            } else {
                this.mPickup.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_PICKUP_PS, new Object[]{$r2}));
            }
        } else if (this.mIsMultipax) {
            this.mPickup.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_PICKUP_MANY_FROM_PS, new Object[]{NativeManager.getInstance().getLanguageString($r1)}));
        } else {
            this.mPickup.setText(DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_PICKUP_FROM_PS_PS, new Object[]{$r2, NativeManager.getInstance().getLanguageString($r1)}));
        }
        this.mLaterText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LATER_BUTTON));
        View $r14 = this.mLaterButton;
        final String str = $r3;
        $r14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", "CLOSE").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, str).addParam("TYPE", "PICKUP").send();
                CarpoolTripDialog.this.animateOut();
            }
        });
        this.mStartText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_START_CARPOOL_ACTION));
        $r14 = this.mStartButton;
        str = $r3;
        $r14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", "START").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, str).addParam("TYPE", "PICKUP").send();
                CarpoolTripDialog.this.showReallyDriveToPickupDialog();
                CarpoolTripDialog.this.animateOut();
            }
        });
    }

    private void verifyArrivedMode(String $r1, final String $r2) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ARRIVED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive != null ? this.mDrive.getId() : this.mDetails.meetingId).addParam("TYPE", "CARPOOL_ARRIVED").send();
        this.mCpnm.checkDriverArrived($r1, new IResultOk() {
            public void onResult(boolean $z0) throws  {
                if ($z0) {
                    CarpoolTripDialog.this.goToArrivedMode($r2);
                } else {
                    CarpoolTripDialog.this.showReallyArrivedDialog($r2);
                }
            }
        });
    }

    private void showReallyArrivedDialog(final String $r1) throws  {
        String $r3;
        if (this.mIsMultipax) {
            $r3 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_MANY_BODY);
        } else {
            $r3 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_BODY);
        }
        MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_TITLE), $r3, false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int $i0) throws  {
                if ($i0 == 1) {
                    CarpoolTripDialog.this.goToArrivedMode($r1);
                }
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_YES), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_LOCATION_MISMATCH_NO), 0);
    }

    private void showReallyDriveToPickupDialog() throws  {
        String $r4;
        AnalyticsBuilder $r2 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_SHARE_ETA_SHOWN);
        if (this.mDrive != null) {
            $r4 = this.mDrive.getId();
        } else {
            CarpoolRidePickupMeetingDetails $r10 = this.mDetails;
            $r4 = $r10.meetingId;
        }
        $r2.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r4).send();
        if (this.mIsMultipax || this.mDrive == null || this.mDrive.getRiderFirstName() == null || this.mDrive.getRiderFirstName().isEmpty()) {
            $r4 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_START_CARPOOL_MANY_BODY);
        } else {
            $r4 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_START_CARPOOL_BODY_PS, new Object[]{this.mDrive.getRiderFirstName()});
        }
        MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_START_CARPOOL_TITLE), $r4, false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int $i0) throws  {
                if ($i0 == 1) {
                    AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_SHARE_ETA_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_YES).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolTripDialog.this.mDrive != null ? CarpoolTripDialog.this.mDrive.getId() : CarpoolTripDialog.this.mDetails.meetingId).send();
                    CarpoolUtils.confirmDriveToPickUp(CarpoolTripDialog.this.mDrive, CarpoolTripDialog.this.mDetails != null ? CarpoolTripDialog.this.mDetails.meetingId : null, true);
                    return;
                }
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_SHARE_ETA_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_NO).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolTripDialog.this.mDrive != null ? CarpoolTripDialog.this.mDrive.getId() : CarpoolTripDialog.this.mDetails.meetingId).send();
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_START_CARPOOL_YES), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_START_CARPOOL_NO), 0, null, new OnCancelListener() {
            public void onCancel(DialogInterface dialog) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CONFIRM_SHARE_ETA_CLICKED).addParam("ACTION", "BACK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolTripDialog.this.mDrive != null ? CarpoolTripDialog.this.mDrive.getId() : CarpoolTripDialog.this.mDetails.meetingId).send();
            }
        });
    }

    private void goToArrivedMode(String $r1) throws  {
        String $r5;
        this.mCpnm.driverArrived(this.mDrive);
        if (this.mCpBar != null) {
            UpcomingCarpoolBar.setStateAndText(this.mCpBar, this.mDriveState, true);
        }
        if (this.mIsMultipax) {
            $r1 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_TITLE_MANY);
            $r5 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_MANY_ACTION);
        } else {
            $r1 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_TITLE_PS, new Object[]{$r1});
            $r5 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONFIRM_PICKUP_ACTION);
        }
        swapTakeoverText($r1);
        swapActionButton($r5, C1283R.drawable.v_in_button, 0, C1283R.drawable.button_blue_bg);
        this.mStartButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                CarpoolTripDialog.this.confirmPickup();
            }
        });
    }

    private void swapActionButton(String $r1, int $i0, int $i1, int $i2) throws  {
        this.mAltStartButton.setVisibility(0);
        this.mAltStartText.setCompoundDrawablesWithIntrinsicBounds($i0, 0, $i1, 0);
        this.mAltStartText.setCompoundDrawablePadding(PixelMeasure.dp(8));
        this.mAltStartButton.setBackgroundResource($i2);
        TextView textView = this.mAltStartText;
        TextView $r4 = textView;
        textView.setText($r1);
        Animation $r5 = getTransAndFadeAnim(0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, false, false, 250, 0, new AccelerateInterpolator());
        this.mStartButton.startAnimation($r5);
        final int i = $i0;
        final int i2 = $i1;
        final int i3 = $i2;
        final String str = $r1;
        $r5.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation animation) throws  {
                CarpoolTripDialog.this.mStartText.setCompoundDrawablesWithIntrinsicBounds(i, 0, i2, 0);
                CarpoolTripDialog.this.mStartText.setCompoundDrawablePadding(PixelMeasure.dp(8));
                CarpoolTripDialog.this.mStartButton.setBackgroundResource(i3);
                CarpoolTripDialog.this.mStartText.setText(str);
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }
        });
    }

    void confirmPickup() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_DRIVE_TO_DROPOFF).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive != null ? this.mDrive.getId() : this.mDetails.meetingId).addParam("TYPE", "CARPOOL_PICKUP").send();
        CarpoolUtils.confirmPaxPickedUp(this.mDrive);
        goToContinueToDropOffMode();
    }

    void confirmDropOff() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_DROPOFF_COMPLETE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive != null ? this.mDrive.getId() : this.mDetails.meetingId).addParam("TYPE", "CARPOOL_DROPOFF").send();
        CarpoolUtils.confirmPaxDropOff(this.mDrive, this.mActivity);
        animateOut();
    }

    private void swapTakeoverText(String $r1) throws  {
        this.mTakeoverAltText.setText($r1);
        Animation $r4 = getTransAndFadeAnim(0.0f, 0.0f, -0.5f, 0.0f, 0.0f, 1.0f, true, false, 250, 125, new DecelerateInterpolator());
        this.mTakeoverAltText.startAnimation($r4);
        this.mTakeoverText.startAnimation(getTransAndFadeAnim(0.0f, 0.0f, 0.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f, 0.0f, false, true, 250, 0, new AccelerateInterpolator()));
        final String str = $r1;
        $r4.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation animation) throws  {
                CarpoolTripDialog.this.mTakeoverText.setText(str);
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }
        });
    }

    private void goToContinueToDropOffMode() throws  {
        ImageView imageView;
        Animation translateAnimation;
        ((ParticleSystem) findViewById(C1283R.id.cpTripParticleSystem)).emmitDots(30, this.mActivity.getResources().getColor(C1283R.color.CarpoolGreen), 12.0f);
        int $i0 = 0;
        ArrayList $r7 = this.mRiderImagesInOrder;
        Iterator $r8 = $r7.iterator();
        while ($r8.hasNext()) {
            imageView = (ImageView) $r8.next();
            translateAnimation = new TranslateAnimation(0, this.imageShiftsWithoutDriver[$i0], 0, this.imageShiftsWithDriver[$i0], 1, 0.0f, 1, 0.0f);
            translateAnimation.setDuration(300);
            translateAnimation.setFillAfter(true);
            translateAnimation.setInterpolator(new OvershootInterpolator());
            if (imageView != null) {
                imageView.startAnimation(translateAnimation);
            } else {
                Logger.m38e("CarpoolTripData: goToContinueToDropOffMode: Rider image is null, not animating");
            }
            $i0++;
        }
        translateAnimation = new AnimationSet(true);
        translateAnimation.addAnimation(new TranslateAnimation(0, 0.0f, 0, this.imageShiftsWithDriver[$i0], 1, 0.0f, 1, 0.0f));
        translateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new OvershootInterpolator());
        ImageView imageView2 = this.mDriverImage;
        imageView = imageView2;
        imageView2.startAnimation(translateAnimation);
        this.showDriver = true;
        View $r3 = findViewById(C1283R.id.cpTripSun);
        translateAnimation = new AnimationSet(false);
        translateAnimation = new ScaleAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.5f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.5f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.addAnimation(translateAnimation);
        translateAnimation = new AlphaAnimation(0.0f, 1.0f);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.addAnimation(translateAnimation);
        translateAnimation = new RotateAnimation(0.0f, 359.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        translateAnimation.setDuration(ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
        translateAnimation.setInterpolator(new LinearInterpolator());
        translateAnimation.setRepeatCount(-1);
        translateAnimation.addAnimation(translateAnimation);
        $r3.startAnimation(translateAnimation);
        $r3.setVisibility(0);
        $r3 = findViewById(C1283R.id.cpTripRadiance);
        translateAnimation = new AnimationSet(false);
        translateAnimation = new ScaleAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.5f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.5f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        translateAnimation.setDuration(900);
        translateAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        translateAnimation.addAnimation(translateAnimation);
        translateAnimation = new AlphaAnimation(0.0f, 1.0f);
        translateAnimation.setDuration(300);
        translateAnimation.setInterpolator(new AccelerateInterpolator());
        translateAnimation.addAnimation(translateAnimation);
        translateAnimation = new AlphaAnimation(1.0f, 0.0f);
        translateAnimation.setDuration(300);
        translateAnimation.setStartOffset(600);
        translateAnimation.setInterpolator(new DecelerateInterpolator());
        translateAnimation.setFillAfter(true);
        translateAnimation.addAnimation(translateAnimation);
        translateAnimation.setFillAfter(true);
        $r3.startAnimation(translateAnimation);
        $r3.setVisibility(0);
        swapTakeoverText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONTINUE_TO_DROPOFF_TITLE));
        swapActionButton(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CONTINUE_TO_DROPOFF_ACTION), 0, C1283R.drawable.go_green_icon, C1283R.drawable.button_green_bg);
        this.mStartButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                CarpoolTripDialog.this.animateOut();
            }
        });
        TimerBar $r22 = (TimerBar) findViewById(C1283R.id.cpTripStartTimer);
        if ($r22 != null) {
            $r22.start();
            $r22.setVisibility(0);
        }
    }

    public void show() throws  {
        super.show();
        animateIn();
    }

    public void dismiss() throws  {
        if (!(this.mDrive == null || this.mChatHandler == null)) {
            for (CarpoolRide $r1 : this.mDrive.getRides()) {
                ChatNotificationManager.getInstance(true).setChatUpdateHandler($r1.getId(), this.mChatHandler);
            }
            this.mChatHandler = null;
        }
        if (this.microHandler != null) {
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_UPDATED, this.microHandler);
            this.microHandler = null;
        }
        if (this.mGetRideRunnable != null) {
            AppService.Remove(this.mGetRideRunnable);
            this.mGetRideRunnable = null;
        }
        super.dismiss();
    }

    private void setupControlButtons() throws  {
        final boolean $z0 = CarpoolUtils.canCallRider(this.mDrive);
        final boolean $z1 = CarpoolUtils.canChatRider(this.mDrive);
        this.mCallText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_CALL_BUTTON));
        this.mCallButton.setOnClickListener(new OnClickListener() {

            class C16001 implements IResultObj<Integer> {
                C16001() throws  {
                }

                public void onResult(Integer $r1) throws  {
                    CarpoolTripDialog.this.mActivity.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + CarpoolTripDialog.this.mDrive.getRide($r1.intValue()).getProxyNumber())));
                }
            }

            public void onClick(View v) throws  {
                Logger.m36d("CarpoolTripData: Calling rider");
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", "CALL").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolTripDialog.this.mDrive != null ? CarpoolTripDialog.this.mDrive.getId() : CarpoolTripDialog.this.mDetails.meetingId).addParam("TYPE", "CARPOOL_PICKUP").send();
                if (!$z0) {
                    CarpoolUtils.DisplayErrorMsgBox();
                } else if (CarpoolTripDialog.this.mDrive.isMultiPax()) {
                    CarpoolUtils.showSelectRiderBottomSheet(CarpoolTripDialog.this.getContext(), CarpoolTripDialog.this.mDrive, CarpoolTripDialog.this.mRiderImageAndMessageCounter, new C16001(), DisplayStrings.DS_RIDERS_ACTION_SHEET_CALL_TITLE, -1);
                } else {
                    CarpoolTripDialog.this.mActivity.startActivity(new Intent("android.intent.action.DIAL", Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + CarpoolTripDialog.this.mDrive.getRide().getProxyNumber())));
                }
            }
        });
        this.mMessageText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_MESSAGE_BUTTON));
        this.mMessageButton.setOnClickListener(new OnClickListener() {

            class C16011 implements IResultObj<Integer> {
                C16011() throws  {
                }

                public void onResult(Integer $r1) throws  {
                    Intent $r2 = new Intent(CarpoolTripDialog.this.mActivity, CarpoolMessagingActivity.class);
                    $r2.putExtra("rider", CarpoolTripDialog.this.mDrive.getRider($r1.intValue()));
                    $r2.putExtra("ride", CarpoolTripDialog.this.mDrive.getRide($r1.intValue()));
                    CarpoolTripDialog.this.mActivity.startActivity($r2);
                }
            }

            public void onClick(View v) throws  {
                Logger.m36d("CarpoolTripData: In app msg");
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_IAM).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, CarpoolTripDialog.this.mDrive != null ? CarpoolTripDialog.this.mDrive.getId() : CarpoolTripDialog.this.mDetails.meetingId).addParam("TYPE", "CARPOOL_PICKUP").send();
                if (!$z1) {
                    CarpoolUtils.DisplayErrorMsgBox();
                } else if (CarpoolTripDialog.this.mDrive.isMultiPax()) {
                    CarpoolUtils.showSelectRiderBottomSheet(CarpoolTripDialog.this.mActivity, CarpoolTripDialog.this.mDrive, CarpoolTripDialog.this.mRiderImageAndMessageCounter, new C16011(), DisplayStrings.DS_RIDERS_ACTION_SHEET_MESSAGE_TITLE, -1);
                } else {
                    Intent $r3 = new Intent(CarpoolTripDialog.this.mActivity, CarpoolMessagingActivity.class);
                    $r3.putExtra("rider", CarpoolTripDialog.this.mDrive.getRider());
                    $r3.putExtra("ride", CarpoolTripDialog.this.mDrive.getRide());
                    CarpoolTripDialog.this.mActivity.startActivity($r3);
                }
            }
        });
        this.mProblemText.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MEETUP_PROBLEM_BUTTON));
        this.mProblemButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                CarpoolTripDialog.this.openOverflowMenu();
            }
        });
    }

    private void openOverflowMenu() throws  {
        String $r7;
        boolean z;
        AnalyticsBuilder $r5 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_MEETUP_CLICK).addParam("ACTION", "PROBLEM");
        if (this.mDrive != null) {
            $r7 = this.mDrive.getId();
        } else {
            CarpoolRidePickupMeetingDetails $r10 = this.mDetails;
            $r7 = $r10.meetingId;
        }
        $r5.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r7).addParam("TYPE", "CARPOOL_PICKUP").send();
        $r7 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_TAKEOVER_OPTIONS_RIDE_DETAILS);
        boolean $z0 = false;
        if (this.mDriveState == 7 || this.mDriveState == 10 || this.mDriveState == 16) {
            $z0 = true;
        }
        String $r8 = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_NOSHOW_CONFIRM_BUTTON);
        String $r9 = DisplayStrings.displayString(DisplayStrings.DS_RIDE_DETAILS_CANCEL_BUTTON);
        String[] $r1 = new String[]{$r7, $r8, $r9};
        int[] $r2 = new int[3];
        $r2[0] = C1283R.drawable.actionsheet_location_info;
        $r2[1] = C1283R.drawable.carpool_options_no_show;
        $r2[2] = C1283R.drawable.carpool_options_cancel_ride;
        int[] $r3 = new int[]{2, 3, 4};
        boolean[] $r4 = new boolean[3];
        $r4[0] = false;
        if (this.mDriveState == 10 || this.mDriveState == 16) {
            z = false;
        } else {
            z = true;
        }
        $r4[1] = z;
        if ($z0) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $r4[2] = $z0;
        showGridSubmenu(DisplayStrings.DS_CARPOOL_SETTINGS_MORE, $r1, $r2, $r3, $r4);
    }

    protected void showGridSubmenu(int $i0, String[] $r1, int[] $r2, int[] $r3, boolean[] $r4) throws  {
        SimpleBottomSheetValue[] $r6 = new SimpleBottomSheetValue[$r1.length];
        for (int $i1 = 0; $i1 < $r1.length; $i1++) {
            $r6[$i1] = new SimpleBottomSheetValue($r3[$i1], $r1[$i1], this.mActivity.getResources().getDrawable($r2[$i1]), $r4[$i1]);
        }
        new SimpleBottomSheet(this.mActivity, $i0, $r6, new SimpleBottomSheetListener() {
            public void onComplete(SimpleBottomSheetValue $r1) throws  {
                CarpoolTripDialog.this.handleSelectedProblemOption($r1.id);
            }
        }) {
            public void onClick(int $i0) throws  {
                super.onClick($i0);
                dismiss();
            }
        }.show();
    }

    private void handleSelectedProblemOption(int $i0) throws  {
        if (this.mDrive == null) {
            Logger.m38e("CarpoolTripData: handleSelectedProblemOption: drive is null; option: " + $i0);
            MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
            return;
        }
        CarpoolRide $r7 = this.mDrive.getRide();
        this.mDrive.getRider();
        AnalyticsBuilder $r8;
        String $r4;
        CarpoolRidePickupMeetingDetails $r9;
        switch ($i0) {
            case 2:
                Logger.m36d("CarpoolTripData: View ride");
                $r8 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_REPORT_PROBLEM_CLICKED).addParam("ACTION", "DETAILS");
                if (this.mDrive != null) {
                    $r4 = this.mDrive.getId();
                } else {
                    $r9 = this.mDetails;
                    $r4 = $r9.meetingId;
                }
                $r8.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r4).addParam("TYPE", "PICKUP").send();
                if (CarpoolUtils.isDriveInvalid(this.mDrive)) {
                    getDriveData();
                    CarpoolUtils.DisplayErrorMsgBox();
                    return;
                }
                Intent intent = new Intent(this.mActivity, CarpoolRideDetailsActivity.class);
                intent.putExtra(CarpoolDrive.class.getSimpleName(), this.mDrive);
                ActivityBase activityBase = this.mActivity;
                ActivityBase $r10 = activityBase;
                activityBase.startActivity(intent);
                dismiss();
                return;
            case 3:
                Logger.m36d("CarpoolTripData: No show");
                $r8 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_REPORT_PROBLEM_CLICKED).addParam("ACTION", "RIDER_NO_SHOW");
                if (this.mDrive != null) {
                    $r4 = this.mDrive.getId();
                } else {
                    $r9 = this.mDetails;
                    $r4 = $r9.meetingId;
                }
                $r8.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r4).addParam("TYPE", "PICKUP").send();
                if (CarpoolUtils.isRideInvalid($r7)) {
                    getDriveData();
                    CarpoolUtils.DisplayErrorMsgBox();
                    return;
                }
                Context $r102 = AppService.getActiveActivity();
                if (this.mIsMultipax) {
                    final Context context = $r102;
                    CarpoolUtils.showSelectRiderBottomSheet($r102, this.mDrive, this.mRiderImageAndMessageCounter, new IResultObj<Integer>() {
                        public void onResult(Integer $r1) throws  {
                            CarpoolRide $r4 = CarpoolTripDialog.this.mDrive.getRide($r1.intValue());
                            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE, CarpoolTripDialog.this.microHandler);
                            CarpoolUtils.riderDidntShow(CarpoolTripDialog.this.mDrive, $r4, context);
                        }
                    }, DisplayStrings.DS_RIDE_DETAILS_OPTION_REPORT_NO_SHOW, DisplayStrings.DS_RIDERS_ACTION_SHEET_NO_SHOW_BUTTON_PS);
                } else {
                    CarpoolDrive $r2 = this.mDrive;
                    CarpoolDrive $r12 = this.mDrive;
                    CarpoolUtils.riderDidntShow($r2, $r12.getRide(), $r102);
                }
                hide();
                return;
            case 4:
                Logger.m36d("CarpoolTripData: Cancel ride");
                $r8 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_REPORT_PROBLEM_CLICKED).addParam("ACTION", "CANCEL_RIDE");
                if (this.mDrive != null) {
                    $r4 = this.mDrive.getId();
                } else {
                    $r9 = this.mDetails;
                    $r4 = $r9.meetingId;
                }
                $r8.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, $r4).addParam("TYPE", "PICKUP").send();
                if (CarpoolUtils.isRideInvalid($r7)) {
                    getDriveData();
                    CarpoolUtils.DisplayErrorMsgBox();
                    return;
                }
                CarpoolUtils.cancelDriveAfterAccepted(this.mDrive, null, this.mActivity);
                hide();
                return;
            default:
                Logger.m38e("CarpoolTripData: Unsupported overflow option: " + $i0);
                CarpoolUtils.DisplayErrorMsgBox();
                return;
        }
    }

    private void getDriveData() throws  {
        CarpoolNativeManager.getInstance().getDriveInfoByMeetingId(this.mDetails.meetingId, new IResultObj<CarpoolDrive>() {
            public void onResult(CarpoolDrive $r1) throws  {
                if ($r1 == null) {
                    AppService.Post(CarpoolTripDialog.this.mGetRideRunnable, 500);
                    return;
                }
                CarpoolTripDialog.this.mDrive = $r1;
                CarpoolTripDialog.this.setupChatHandler();
                CarpoolTripDialog.this.setupControlButtons();
                CarpoolTripDialog.this.driveStateSetup();
                CarpoolUserData $r5 = CarpoolTripDialog.this.mCpnm.getCarpoolProfileNTV();
                if ($r5 != null) {
                    CarpoolTripDialog.this.setDriverImage($r5.getImage());
                }
                CarpoolTripDialog.this.animateImagesInPlace();
                if (CarpoolTripDialog.this.mGetRideRunnable != null) {
                    AppService.Remove(CarpoolTripDialog.this.mGetRideRunnable);
                    CarpoolTripDialog.this.mGetRideRunnable = null;
                }
            }
        });
    }

    private void animateIn() throws  {
        Animation rotateAnimation;
        View view;
        View $r3;
        TextView textView;
        TextView $r6;
        long $l0 = 0;
        this.mAltStartButton.setVisibility(8);
        if (!(this.mDriveState == 7 || this.mCpBar == null)) {
            rotateAnimation = new RotateAnimation(180.0f, 180.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            rotateAnimation.setDuration(0);
            rotateAnimation.setFillAfter(true);
            if (this.mCpBarButton != null) {
                view = this.mCpBarButton;
                $r3 = view;
                view.startAnimation(rotateAnimation);
            }
            boolean $z0 = this.mActivity;
            ActivityBase $r4 = $z0;
            if ($z0 instanceof MainActivity) {
                DisplayUtils.runOnLayout(this.mCpBar, new Runnable() {
                    public void run() throws  {
                        UpcomingCarpoolBar $r4 = null;
                        if (CarpoolTripDialog.this.mActivity instanceof MainActivity) {
                            $r4 = ((MainActivity) CarpoolTripDialog.this.mActivity).getLayoutMgr().getCarpoolBar();
                        }
                        if ($r4 != null) {
                            int[] $r3 = new int[2];
                            int[] $r2 = new int[2];
                            $r4.getLocationOnScreen($r3);
                            CarpoolTripDialog.this.mCpBar.getLocationOnScreen($r2);
                            Animation animation = r0;
                            float $f0 = $r3[1] - $r2[1];
                            float f = $f0;
                            Animation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 0, (float) $f0, 0, 0.0f);
                            animation.setDuration(125);
                            animation.setFillAfter(true);
                            CarpoolTripDialog.this.mCpBar.startAnimation(animation);
                            Animation $r10 = translateAnimation;
                            translateAnimation = new RotateAnimation(0.0f, 180.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                            $r10.setDuration(125);
                            $r10.setFillAfter(true);
                            if (CarpoolTripDialog.this.mCpBarButton != null) {
                                CarpoolTripDialog.this.mCpBarButton.startAnimation($r10);
                            }
                        }
                    }
                });
            }
        }
        if (this.mTitle != null) {
            rotateAnimation = new AnimationSet(true);
            rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f));
            rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            rotateAnimation.setDuration(250);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setStartOffset(0);
            rotateAnimation.setFillBefore(true);
            textView = this.mTitle;
            $r6 = textView;
            textView.startAnimation(rotateAnimation);
            $l0 = 0 + 62;
        }
        if (this.mSubTitle != null) {
            rotateAnimation = new AnimationSet(true);
            rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f));
            rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            rotateAnimation.setDuration(250);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setStartOffset($l0);
            rotateAnimation.setFillBefore(true);
            textView = this.mSubTitle;
            $r6 = textView;
            textView.startAnimation(rotateAnimation);
            $l0 += 62;
        }
        if (this.mPickup != null) {
            rotateAnimation = new AnimationSet(true);
            rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f));
            rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            rotateAnimation.setDuration(250);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setStartOffset($l0);
            rotateAnimation.setFillBefore(true);
            textView = this.mPickup;
            $r6 = textView;
            textView.startAnimation(rotateAnimation);
        }
        if (this.mTakeoverText != null) {
            rotateAnimation = new AnimationSet(true);
            rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f));
            rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            rotateAnimation.setDuration(250);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setStartOffset($l0);
            rotateAnimation.setFillBefore(true);
            textView = this.mTakeoverText;
            $r6 = textView;
            textView.startAnimation(rotateAnimation);
        }
        int $i1;
        ArrayList $r10;
        Iterator $r11;
        ImageView $r13;
        ImageView imageView;
        if (this.mDriveState == 7) {
            this.showDriver = true;
            $i1 = 0;
            $r10 = this.mRiderImagesInOrder;
            $r11 = $r10.iterator();
            while ($r11.hasNext()) {
                $r13 = (ImageView) $r11.next();
                rotateAnimation = new AnimationSet(true);
                rotateAnimation.addAnimation(new TranslateAnimation(0, 0.0f, 0, this.imageShiftsWithDriver[$i1], 0, 0.0f, 0, 0.0f));
                rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                rotateAnimation.setDuration(250);
                rotateAnimation.setInterpolator(new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
                rotateAnimation.setStartOffset($l0);
                rotateAnimation.setFillBefore(true);
                rotateAnimation.setFillAfter(true);
                if ($r13 != null) {
                    $r13.startAnimation(rotateAnimation);
                }
                $i1++;
            }
            rotateAnimation = new AnimationSet(true);
            rotateAnimation.addAnimation(new TranslateAnimation(0, 0.0f, 0, this.imageShiftsWithDriver[$i1], 0, 0.0f, 0, 0.0f));
            rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            rotateAnimation.setDuration(250);
            rotateAnimation.setInterpolator(new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
            rotateAnimation.setStartOffset($l0);
            rotateAnimation.setFillBefore(true);
            rotateAnimation.setFillAfter(true);
            imageView = this.mDriverImage;
            $r13 = imageView;
            imageView.startAnimation(rotateAnimation);
            $l0 += 62;
        } else {
            this.showDriver = false;
            $i1 = 0;
            $r10 = this.mRiderImagesInOrder;
            $r11 = $r10.iterator();
            while ($r11.hasNext()) {
                float $f0;
                float $f1;
                $r13 = (ImageView) $r11.next();
                rotateAnimation = new AnimationSet(true);
                if (this.showDriver) {
                    $f0 = this.imageShiftsWithDriver[$i1];
                } else {
                    $f0 = this.imageShiftsWithoutDriver[$i1];
                }
                if (this.showDriver) {
                    $f1 = this.imageShiftsWithDriver[$i1];
                } else {
                    $f1 = this.imageShiftsWithoutDriver[$i1];
                }
                rotateAnimation.addAnimation(new TranslateAnimation(0, $f0, 0, $f1, 1, -0.5f, 1, 0.0f));
                rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                rotateAnimation.setDuration(125);
                rotateAnimation.setInterpolator(new DecelerateInterpolator());
                rotateAnimation.setStartOffset($l0);
                rotateAnimation.setFillBefore(true);
                rotateAnimation.setFillAfter(true);
                if ($r13 != null) {
                    $r13.startAnimation(rotateAnimation);
                }
                $i1++;
                Logger.m36d("CarpoolTripDialog: animateIn; else: img #" + $i1);
            }
            rotateAnimation = new AnimationSet(true);
            rotateAnimation.addAnimation(new TranslateAnimation(0, this.showDriver ? this.imageShiftsWithDriver[$i1] : 0.0f, 0, this.showDriver ? this.imageShiftsWithDriver[$i1] : 0.0f, 1, -0.5f, 1, 0.0f));
            rotateAnimation.addAnimation(new AlphaAnimation(0.0f, this.showDriver ? 1.0f : 0.0f));
            rotateAnimation.setDuration(125);
            rotateAnimation.setInterpolator(new DecelerateInterpolator());
            rotateAnimation.setStartOffset($l0);
            rotateAnimation.setFillBefore(true);
            rotateAnimation.setFillAfter(true);
            imageView = this.mDriverImage;
            $r13 = imageView;
            imageView.startAnimation(rotateAnimation);
            $l0 += 62;
        }
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(125);
        rotateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        view = this.mCallButton;
        $r3 = view;
        view.startAnimation(rotateAnimation);
        textView = this.mMessageBadge;
        if (textView.getVisibility() == 0) {
            rotateAnimation = new AnimationSet(true);
            rotateAnimation.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            rotateAnimation.setDuration(125);
            rotateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
            rotateAnimation.setStartOffset($l0);
            rotateAnimation.setFillBefore(true);
            textView = this.mMessageBadge;
            $r6 = textView;
            textView.startAnimation(rotateAnimation);
        }
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.5f, 1, 0.0f));
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(250);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        textView = this.mCallText;
        $r6 = textView;
        textView.startAnimation(rotateAnimation);
        $l0 += 25;
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(125);
        rotateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        view = this.mMessageButton;
        $r3 = view;
        view.startAnimation(rotateAnimation);
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.5f, 1, 0.0f));
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(125);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        textView = this.mMessageText;
        $r6 = textView;
        textView.startAnimation(rotateAnimation);
        $l0 += 25;
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(250);
        rotateAnimation.setInterpolator(new OvershootInterpolator(0.6f));
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        view = this.mProblemButton;
        $r3 = view;
        view.startAnimation(rotateAnimation);
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.5f, 1, 0.0f));
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(250);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        textView = this.mProblemText;
        $r6 = textView;
        textView.startAnimation(rotateAnimation);
        $l0 += 25;
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(250);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        view = this.mBottomButtons;
        $r3 = view;
        view.startAnimation(rotateAnimation);
        rotateAnimation = new AnimationSet(true);
        rotateAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, 0.0f));
        rotateAnimation.addAnimation(new AlphaAnimation(0.0f, 1.0f));
        rotateAnimation.setDuration(250);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setStartOffset($l0);
        rotateAnimation.setFillBefore(true);
        view = this.mLaterButton;
        $r3 = view;
        view.startAnimation(rotateAnimation);
        Animation $r7 = getTransAndFadeAnim(0.0f, 0.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 0.0f, 0.0f, 1.0f, true, false, 250, $l0, new DecelerateInterpolator());
        view = this.mStartButton;
        $r3 = view;
        view.startAnimation($r7);
    }

    @NonNull
    private AnimationSet getTransAndFadeAnim(float $f0, float $f1, float $f2, float $f3, float $f4, float $f5, boolean $z0, boolean $z1, long $l0, long $l1, Interpolator $r1) throws  {
        AnimationSet $r2 = new AnimationSet(true);
        $r2.addAnimation(new TranslateAnimation(1, $f0, 1, $f1, 1, $f2, 1, $f3));
        $r2.addAnimation(new AlphaAnimation($f4, $f5));
        $r2.setDuration($l0);
        $r2.setInterpolator($r1);
        $r2.setStartOffset($l1);
        $r2.setFillBefore($z0);
        $r2.setFillAfter($z1);
        return $r2;
    }

    private void animateOut() throws  {
        Animation $r1;
        Animation r21;
        Animation $r5;
        Animation translateAnimation;
        this.mAltStartButton.setVisibility(8);
        AnimationSet $r3 = r20;
        AnimationSet r20 = new AnimationSet(true);
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(250);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset(250);
        $r3.setFillAfter(true);
        this.mBottomButtons.startAnimation($r3);
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(250);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset(0);
        $r3.setFillAfter(true);
        this.mLaterButton.startAnimation($r3);
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(250);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset(0);
        $r3.setFillAfter(true);
        this.mStartButton.startAnimation($r3);
        long $l0 = 0 + 50;
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(125);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset($l0);
        $r3.setFillAfter(true);
        this.mCallButton.startAnimation($r3);
        TextView $r6 = this.mMessageBadge;
        if ($r6.getVisibility() == 0) {
            $r3 = r20;
            r20 = new AnimationSet(true);
            $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            $r3.setDuration(125);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setStartOffset($l0);
            $r3.setFillAfter(true);
            $r6 = this.mMessageBadge;
            $r6.startAnimation($r3);
        }
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(125);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset($l0);
        $r3.setFillAfter(true);
        $r6 = this.mCallText;
        $r6.startAnimation($r3);
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(125);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset($l0);
        $r3.setFillAfter(true);
        this.mMessageButton.startAnimation($r3);
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(125);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset($l0);
        $r3.setFillAfter(true);
        $r6 = this.mMessageText;
        $r6.startAnimation($r3);
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(125);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset($l0);
        $r3.setFillAfter(true);
        this.mProblemButton.startAnimation($r3);
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
        $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
        $r3.setDuration(125);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset($l0);
        $r3.setFillAfter(true);
        $r6 = this.mProblemText;
        $r6.startAnimation($r3);
        $l0 += 50;
        if (this.mTakeoverText != null) {
            $r3 = r20;
            r20 = new AnimationSet(true);
            $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            $r3.setDuration(125);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setStartOffset($l0);
            $r3.setFillAfter(true);
            $r6 = this.mTakeoverText;
            $r6.startAnimation($r3);
        }
        if (this.mPickup != null) {
            $r3 = r20;
            r20 = new AnimationSet(true);
            $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            $r3.setDuration(125);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setStartOffset($l0);
            $r3.setFillAfter(true);
            $r6 = this.mPickup;
            $r6.startAnimation($r3);
        }
        View $r2 = findViewById(C1283R.id.cpTripSun);
        if ($r2 != null && $r2.getVisibility() == 0) {
            $r1 = r21;
            r21 = new AlphaAnimation(1.0f, 0.0f);
            $r1.setDuration(125);
            $r1.setInterpolator(new AccelerateInterpolator());
            $r1.setStartOffset($l0);
            $r1.setFillAfter(true);
            $r2.startAnimation($r1);
        }
        int $i1 = 0;
        ArrayList $r7 = this.mRiderImagesInOrder;
        Iterator $r8 = $r7.iterator();
        while ($r8.hasNext()) {
            float $f0;
            float $f1;
            ImageView $r10 = (ImageView) $r8.next();
            $r3 = r20;
            r20 = new AnimationSet(true);
            $r5 = translateAnimation;
            if (this.showDriver) {
                $f0 = this.imageShiftsWithDriver[$i1];
            } else {
                $f0 = this.imageShiftsWithoutDriver[$i1];
            }
            if (this.showDriver) {
                $f1 = this.imageShiftsWithDriver[$i1];
            } else {
                $f1 = this.imageShiftsWithoutDriver[$i1];
            }
            translateAnimation = new TranslateAnimation(0, $f0, 0, $f1, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            $r3.addAnimation($r5);
            $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            $r3.setDuration(125);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setStartOffset($l0);
            $r3.setFillAfter(true);
            if ($r10 != null) {
                $r10.startAnimation($r3);
            } else {
                Logger.m38e("CarpoolTripData: Rider image is null, not animating");
            }
            $i1++;
        }
        $r3 = r20;
        r20 = new AnimationSet(true);
        $r5 = translateAnimation;
        translateAnimation = new TranslateAnimation(0, this.showDriver ? this.imageShiftsWithDriver[$i1] : 0.0f, 0, this.showDriver ? this.imageShiftsWithDriver[$i1] : 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        $r3.addAnimation($r5);
        $r1 = r21;
        r21 = new AlphaAnimation(this.showDriver ? 1.0f : 0.0f, 0.0f);
        $r3.addAnimation($r1);
        $r3.setDuration(125);
        $r3.setInterpolator(new AccelerateInterpolator());
        $r3.setStartOffset($l0);
        $r3.setFillAfter(true);
        ImageView $r102 = this.mDriverImage;
        $r102.startAnimation($r3);
        $l0 += 50;
        if (this.mDriveState != 7) {
            boolean $z0 = this.mActivity;
            ActivityBase $r12 = $z0;
            if ($z0 instanceof MainActivity) {
                UpcomingCarpoolBar $r15 = ((MainActivity) this.mActivity).getLayoutMgr().getCarpoolBar();
                if ($r15 != null) {
                    int[] $r16 = new int[2];
                    int[] $r17 = new int[2];
                    $r15.getLocationOnScreen($r16);
                    this.mCpBar.getLocationOnScreen($r17);
                    $r3 = r20;
                    r20 = new AnimationSet(true);
                    float $f02 = $r16[1] - $r17[1];
                    float f = $f02;
                    $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 0, 0.0f, 0, (float) $f02));
                    $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                    $r3.setStartOffset($l0);
                    $r3.setDuration(250);
                    $r3.setFillAfter(true);
                    this.mCpBar.startAnimation($r3);
                    Animation $r18 = translateAnimation;
                    translateAnimation = new RotateAnimation(180.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                    $r18.setDuration(250);
                    $r18.setStartOffset($l0);
                    $r18.setFillAfter(true);
                    if (this.mCpBarButton != null) {
                        this.mCpBarButton.startAnimation($r18);
                    }
                    $l0 += 50;
                } else {
                    return;
                }
            }
        }
        if (this.mSubTitle != null) {
            $r3 = r20;
            r20 = new AnimationSet(true);
            $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            $r3.setDuration(125);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setStartOffset($l0);
            $r3.setFillAfter(true);
            $r6 = this.mSubTitle;
            $r6.startAnimation($r3);
        }
        if (this.mTitle != null) {
            $r3 = r20;
            r20 = new AnimationSet(true);
            $r3.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            $r3.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            $r3.setDuration(125);
            $r3.setInterpolator(new AccelerateInterpolator());
            $r3.setStartOffset($l0);
            $r3.setFillAfter(true);
            $r6 = this.mTitle;
            $r6.startAnimation($r3);
        }
        $r3.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation animation) throws  {
                try {
                    CarpoolTripDialog.this.dismiss();
                } catch (Exception e) {
                }
            }
        });
    }

    private void animateImagesInPlace() throws  {
        Animation translateAnimation;
        int $i0 = 0;
        Iterator $r2 = this.mRiderImagesInOrder.iterator();
        while ($r2.hasNext()) {
            ImageView $r4 = (ImageView) $r2.next();
            translateAnimation = new TranslateAnimation(0, this.imageShiftsWithDriver[$i0], 0, this.imageShiftsWithDriver[$i0], 0, 0.0f, 0, 0.0f);
            translateAnimation.setDuration(0);
            translateAnimation.setFillAfter(true);
            if ($r4 != null) {
                $r4.startAnimation(translateAnimation);
            } else {
                Logger.m38e("CarpoolTripData: Rider image is null, not animating");
            }
            $i0++;
        }
        translateAnimation = new TranslateAnimation(0, this.imageShiftsWithDriver[$i0], 0, this.imageShiftsWithDriver[$i0], 0, 0.0f, 0, 0.0f);
        translateAnimation.setDuration(0);
        translateAnimation.setFillAfter(true);
        this.mDriverImage.startAnimation(translateAnimation);
    }

    public void onBackPressed() throws  {
        animateOut();
    }

    private void setDriverImage(String $r1) throws  {
        this.mDriverImage.setImageDrawable(new CircleFrameDrawable(BitmapFactory.decodeResource(this.mActivity.getResources(), C1283R.drawable.ridecard_profilepic_placeholder), 0, 4));
        this.mDriverImage.setLayerType(1, null);
        if ($r1 != null && !$r1.isEmpty()) {
            VolleyManager.getInstance().loadImageFromUrl($r1, new ImageRequestListener() {
                public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                    CarpoolTripDialog.this.mDriverImage.setImageDrawable(new CircleFrameDrawable($r1, 0, 4));
                }

                public void onImageLoadFailed(Object token, long duration) throws  {
                }
            }, null, this.mDriverImage.getWidth(), this.mDriverImage.getHeight(), null);
        }
    }

    private void updateChatBadge() throws  {
        int $i0 = 0;
        while ($i0 < this.mDrive.getRidesAmount()) {
            if (this.mDrive.getRide($i0) == null || this.mDrive.getRide($i0).getRider() == null) {
                Logger.m38e("CarpoolTripDialog: ride is null in drive=" + this.mDrive.getId() + "position=" + $i0);
            } else {
                String $r5 = this.mDrive.getRide($i0).getRider().getId();
                int $i1 = CarpoolNativeManager.getInstance().getUnreadChatMessageCount(this.mDrive.getRide($i0));
                if (this.mRiderImageAndMessageCounter.containsKey($r5)) {
                    ((RiderImageAndMessageCounter) this.mRiderImageAndMessageCounter.get($r5)).counter = $i1;
                } else {
                    Logger.m38e("CarpoolTripDialog: Internal error! missing rider image for ride in drive=" + this.mDrive.getId() + "position=" + $i0);
                    this.mRiderImageAndMessageCounter.put($r5, new RiderImageAndMessageCounter(null, $i1));
                }
            }
            $i0++;
        }
        $i0 = CarpoolNativeManager.getInstance().getUnreadChatMessageCount(this.mDrive);
        this.mMessageBadge.setText(NativeManager.getInstance().intToString($i0));
        this.mMessageBadge.setVisibility($i0 > 0 ? (byte) 0 : (byte) 8);
    }

    private void collapseToTicker() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 != null) {
            final LayoutManager $r2 = $r1.getLayoutMgr();
            if ($r2 != null && $r2.shouldShowCarpoolBar()) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r2.openUpcomingCarpoolBar(CarpoolTripDialog.this.mDrive);
                    }
                }, 600);
                CarpoolNativeManager.getInstance().setManualRideTickerOpen(true);
            }
        }
    }

    public void onOrientationChanged(int orientation) throws  {
        this.mRiderImages.clear();
        this.mRiderImagesInOrder.clear();
        this.mRiderImageAndMessageCounter.clear();
        initLayout();
        if (CarpoolUtils.isDriveInvalid(this.mDrive)) {
            this.mGetRideRunnable = new Runnable() {
                public void run() throws  {
                    CarpoolTripDialog.this.getDriveData();
                }
            };
            AppService.Post(this.mGetRideRunnable, 500);
        } else {
            setupChatHandler();
        }
        if (this.showDriver) {
            animateImagesInPlace();
        }
    }
}
