package com.waze.navbar;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MoodManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.RequestPermissionActivity;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolLocation;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolRideDetailsActivity;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.ContactRiderDialog;
import com.waze.main.navigate.LocationData;
import com.waze.map.CanvasFont;
import com.waze.messages.QuestionData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.AddressItemAppData;
import com.waze.navigate.DriveToNativeManager.LocationDataListener;
import com.waze.navigate.DriveToNativeManager.ObjectListener;
import com.waze.reports.AddPlaceFlowActivity;
import com.waze.reports.PhotoPagerSection;
import com.waze.reports.PhotoPagerSection.VenueImage;
import com.waze.reports.PlacePhotoGallery;
import com.waze.reports.PlacePhotoGallery.IPhotoGalleryListener;
import com.waze.reports.VenueData;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.dialogs.TimeToParkFeedbackDialog;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class NearingDest extends LinearLayout implements INavBarViewEvents {
    LocationData destinationData;
    DriveToNativeManager dtnm;
    private AddressItemAppData mAddressItemAppData;
    private INavBarViewCallbacks mCallbacks;
    private CarpoolDrive mCarpoolDrive;
    private boolean mCarpoolIsPickup;
    private AddressItem mDestAddressItem = null;
    private VenueData mFoundVenue;
    protected final Handler mHandler = new MyHandler();
    private OnClickListener mOnClickLaunchAddPlace;
    private PlacePhotoGallery mPhotoGallery = null;
    private boolean mReceivedSearchResult = false;
    private boolean mSaveNav = false;
    private String mVenueID = null;
    private boolean nearingDisplayed;
    private boolean nightMode;
    NativeManager nm;
    private float scale;

    class C20421 implements OnClickListener {
        C20421() throws  {
        }

        public void onClick(View $r1) throws  {
            if (AppService.isCameraAvailable()) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_NEAR_DESTINATION_PHOTO_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, NearingDest.this.mVenueID);
                if ($r1.getId() == C1283R.id.navBarNearingDestPhotoFrameContainer && NearingDest.this.mFoundVenue != null && NearingDest.this.mFoundVenue.numImages > 0) {
                    NearingDest.this.openGallery($r1);
                } else if (MyWazeNativeManager.getInstance().getInvisibleNTV()) {
                    NativeManager nativeManager = NearingDest.this.nm;
                    String $r5 = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE);
                    nativeManager = NearingDest.this.nm;
                    MsgBox.openMessageBox($r5, NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CANNOT_REPORT_WHILE_INVISIBLE), true);
                } else {
                    Intent $r2 = new Intent(NearingDest.this.getContext(), AddPlaceFlowActivity.class);
                    if (NearingDest.this.destinationData != null) {
                        LocationData $r11 = NearingDest.this.destinationData;
                        $r2.putExtra("name", $r11.destinationName);
                        $r11 = NearingDest.this.destinationData;
                        $r2.putExtra("street", $r11.mStreet);
                        $r11 = NearingDest.this.destinationData;
                        $r2.putExtra("city", $r11.mCity);
                        $r11 = NearingDest.this.destinationData;
                        $r2.putExtra("x", $r11.locationX);
                        $r11 = NearingDest.this.destinationData;
                        $r2.putExtra("y", $r11.locationY);
                    }
                    if (!(NearingDest.this.mDestAddressItem == null || NearingDest.this.mDestAddressItem.venueData == null)) {
                        $r2.putExtra("destination_venue_id", NearingDest.this.mDestAddressItem.venueData.id);
                    }
                    Intent intent = new Intent(NearingDest.this.getContext(), RequestPermissionActivity.class);
                    intent = intent;
                    intent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.CAMERA"});
                    intent.putExtra(RequestPermissionActivity.INT_ON_GRANTED, $r2);
                    if (AppService.getMainActivity() != null) {
                        AppService.getMainActivity().startActivityForResult(intent, MainActivity.NAVBAR_ADD_PLACE_PHOTO_CODE);
                    }
                }
            }
        }
    }

    class C20443 implements IResultObj<CarpoolDrive> {
        C20443() throws  {
        }

        public void onResult(CarpoolDrive $r1) throws  {
            NearingDest.this.mCarpoolDrive = $r1;
            if (NearingDest.this.mCarpoolDrive != null) {
                NearingDest.this.updateRiderImages(NearingDest.this.mCarpoolDrive.getRider());
                NearingDest.this.onOpen();
                NearingDest.this.mCallbacks.onReady(NearingDest.this);
            }
        }
    }

    class C20454 implements ObjectListener {
        C20454() throws  {
        }

        public void onComplete(Object $r2) throws  {
            if ($r2 != null) {
                NearingDest.this.mAddressItemAppData = (AddressItemAppData) $r2;
            }
        }
    }

    class C20465 implements LocationDataListener {
        C20465() throws  {
        }

        public void onComplete(LocationData $r1) throws  {
            NearingDest.this.destinationData = $r1;
            NearingDest.this.dtnm.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, NearingDest.this.mHandler);
            NearingDest.this.nm.venueGet(NearingDest.this.mVenueID, 0);
        }
    }

    class C20476 implements Runnable {
        C20476() throws  {
        }

        public void run() throws  {
            if (!NearingDest.this.mReceivedSearchResult) {
                NearingDest.this.onOpen();
            }
        }
    }

    class C20487 implements OnTouchListener {
        float startY = 0.0f;

        C20487() throws  {
        }

        public boolean onTouch(View $r1, MotionEvent $r2) throws  {
            boolean $z0 = true;
            if (!NearingDest.this.nearingDisplayed) {
                return false;
            }
            if ($r2.getAction() == 0) {
                this.startY = $r2.getY();
            } else if ($r2.getAction() == 2) {
                if (NearingDest.this.mAddressItemAppData.bNearingMinimized && $r2.getY() - this.startY > 40.0f) {
                    NearingDest.this.onMinimize();
                } else if (!NearingDest.this.mAddressItemAppData.bNearingMinimized && this.startY - $r2.getY() > 40.0f) {
                    NearingDest.this.onMinimize();
                }
            } else if ($r2.getAction() == 1) {
                this.startY = 0.0f;
            }
            if ($r1 != NearingDest.this) {
                $z0 = false;
            }
            return $z0;
        }
    }

    class C20498 implements OnClickListener {
        C20498() throws  {
        }

        public void onClick(View v) throws  {
            new TimeToParkFeedbackDialog("NEARING").show();
        }
    }

    class C20509 implements IResultOk {
        final /* synthetic */ TextView val$nameText;

        C20509(TextView $r2) throws  {
            this.val$nameText = $r2;
        }

        public void onResult(boolean $z0) throws  {
            String $r1 = null;
            NearingDest.this.mCarpoolIsPickup = $z0;
            CarpoolLocation $r4;
            if ($z0) {
                $r4 = NearingDest.this.mCarpoolDrive.getPickupLocation();
                if ($r4 != null) {
                    $r1 = $r4.placeName;
                }
            } else {
                $r4 = NearingDest.this.mCarpoolDrive.getDropOffLocation();
                if ($r4 != null) {
                    $r1 = $r4.placeName;
                }
            }
            this.val$nameText.setText($r1);
        }
    }

    private static class MyHandler extends Handler {
        final WeakReference<NearingDest> _navBar;

        private MyHandler(NearingDest $r1) throws  {
            this._navBar = new WeakReference($r1);
        }

        public void handleMessage(Message $r1) throws  {
            super.handleMessage($r1);
            NearingDest $r4 = (NearingDest) this._navBar.get();
            if ($r4 != null) {
                $r4.handleMessage($r1);
            }
        }
    }

    public void adjustImages() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:32:0x0263
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
        r28 = this;
        r1 = 0;
        r2 = com.waze.AppService.getAppContext();
        r3 = r2.getResources();
        r4 = r3.getConfiguration();
        r5 = r4.orientation;
        r6 = 2;
        if (r5 != r6) goto L_0x0013;
    L_0x0012:
        r1 = 1;
    L_0x0013:
        if (r1 == 0) goto L_0x01aa;
    L_0x0015:
        r6 = 2131691222; // 0x7f0f06d6 float:1.901151E38 double:1.0531954003E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r8 = r7.getLayoutParams();
        r10 = r8;
        r10 = (android.widget.FrameLayout.LayoutParams) r10;
        r9 = r10;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1111752704; // 0x42440000 float:49.0 double:5.492788177E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.height = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1115684864; // 0x42800000 float:64.0 double:5.51221563E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.width = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r11 = r11 * r12;
        r13 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r11 = r11 * r12;
        r14 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r11 = r11 * r12;
        r15 = (int) r11;
        r9.setMargins(r5, r13, r14, r15);
        r7.setLayoutParams(r9);
        r6 = 2131691221; // 0x7f0f06d5 float:1.9011508E38 double:1.0531954E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r8 = r7.getLayoutParams();
        r16 = r8;
        r16 = (android.widget.FrameLayout.LayoutParams) r16;
        r9 = r16;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1111752704; // 0x42440000 float:49.0 double:5.492788177E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.height = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1115684864; // 0x42800000 float:64.0 double:5.51221563E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.width = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1091567616; // 0x41100000 float:9.0 double:5.39306059E-315;
        r11 = r12 * r11;
        r5 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r11 = r12 * r11;
        r13 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r11 = r11 * r12;
        r14 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1077936128; // 0x40400000 float:3.0 double:5.325712093E-315;
        r11 = r11 * r12;
        r15 = (int) r11;
        r9.setMargins(r5, r13, r14, r15);
        r7.setLayoutParams(r9);
        r6 = 2131691228; // 0x7f0f06dc float:1.9011522E38 double:1.0531954033E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r6 = 8;
        r7.setVisibility(r6);
    L_0x00c9:
        r6 = 2131691219; // 0x7f0f06d3 float:1.9011504E38 double:1.053195399E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r0 = r28;
        r1 = r0.nightMode;
        r28 = r0;
        if (r1 == 0) goto L_0x0267;
    L_0x00da:
        r17 = "#57717d";
    L_0x00dc:
        r0 = r17;
        r5 = android.graphics.Color.parseColor(r0);
        r7.setBackgroundColor(r5);
        r6 = 2131691235; // 0x7f0f06e3 float:1.9011536E38 double:1.053195407E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r19 = r7;
        r19 = (android.widget.TextView) r19;
        r18 = r19;
        r0 = r28;
        r1 = r0.nightMode;
        r28 = r0;
        if (r1 == 0) goto L_0x026e;
    L_0x00fc:
        r17 = "#add9ea";
    L_0x00fe:
        r0 = r17;
        r5 = android.graphics.Color.parseColor(r0);
        r0 = r18;
        r0.setTextColor(r5);
        r6 = 2131691236; // 0x7f0f06e4 float:1.9011538E38 double:1.0531954072E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r20 = r7;
        r20 = (android.widget.TextView) r20;
        r18 = r20;
        r0 = r28;
        r1 = r0.nightMode;
        r28 = r0;
        if (r1 == 0) goto L_0x0275;
    L_0x0120:
        r17 = "#ffffff";
    L_0x0122:
        r0 = r17;
        r5 = android.graphics.Color.parseColor(r0);
        r0 = r18;
        r0.setTextColor(r5);
        r6 = 2131691243; // 0x7f0f06eb float:1.9011552E38 double:1.0531954107E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r22 = r7;
        r22 = (android.widget.ImageView) r22;
        r21 = r22;
        r0 = r28;
        r1 = r0.nightMode;
        r28 = r0;
        if (r1 == 0) goto L_0x027c;
    L_0x0144:
        r6 = 2130838649; // 0x7f020479 float:1.7282286E38 double:1.0527741733E-314;
        r0 = r21;
        r0.setImageResource(r6);
    L_0x014c:
        r6 = 2131691232; // 0x7f0f06e0 float:1.901153E38 double:1.0531954053E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r23 = r7;
        r23 = (android.widget.ImageView) r23;
        r21 = r23;
        r0 = r28;
        r1 = r0.nightMode;
        r28 = r0;
        if (r1 == 0) goto L_0x0289;
    L_0x0163:
        r6 = 2130838670; // 0x7f02048e float:1.7282329E38 double:1.0527741837E-314;
        r0 = r21;
        r0.setImageResource(r6);
    L_0x016b:
        r6 = 2131691233; // 0x7f0f06e1 float:1.9011532E38 double:1.053195406E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r24 = r7;
        r24 = (android.widget.ImageView) r24;
        r21 = r24;
        r0 = r28;
        r1 = r0.nightMode;
        r28 = r0;
        if (r1 == 0) goto L_0x0296;
    L_0x0182:
        r6 = 2130838673; // 0x7f020491 float:1.7282335E38 double:1.052774185E-314;
        r0 = r21;
        r0.setImageResource(r6);
    L_0x018a:
        r0 = r28;
        r0 = r0.mCarpoolDrive;
        r25 = r0;
        if (r25 != 0) goto L_0x02a6;
    L_0x0192:
        r6 = 2131691240; // 0x7f0f06e8 float:1.9011546E38 double:1.053195409E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r0 = r28;
        r1 = r0.nightMode;
        r28 = r0;
        if (r1 == 0) goto L_0x029f;
    L_0x01a3:
        r6 = 2130838667; // 0x7f02048b float:1.7282323E38 double:1.052774182E-314;
        r7.setBackgroundResource(r6);
        return;
    L_0x01aa:
        r6 = 2131691222; // 0x7f0f06d6 float:1.901151E38 double:1.0531954003E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r8 = r7.getLayoutParams();
        r26 = r8;
        r26 = (android.widget.FrameLayout.LayoutParams) r26;
        r9 = r26;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1124073472; // 0x43000000 float:128.0 double:5.55366086E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.height = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1126694912; // 0x43280000 float:168.0 double:5.566612494E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.width = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r11 = r11 * r12;
        r13 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r11 = r11 * r12;
        r14 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r11 = r11 * r12;
        r15 = (int) r11;
        r9.setMargins(r5, r13, r14, r15);
        r7.setLayoutParams(r9);
        r6 = 2131691221; // 0x7f0f06d5 float:1.9011508E38 double:1.0531954E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        r8 = r7.getLayoutParams();
        r27 = r8;
        r27 = (android.widget.FrameLayout.LayoutParams) r27;
        r9 = r27;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1124073472; // 0x43000000 float:128.0 double:5.55366086E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.height = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1126694912; // 0x43280000 float:168.0 double:5.566612494E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r9.width = r5;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1090519040; // 0x41000000 float:8.0 double:5.38787994E-315;
        r11 = r11 * r12;
        r5 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r11 = r11 * r12;
        r13 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1082130432; // 0x40800000 float:4.0 double:5.34643471E-315;
        r11 = r11 * r12;
        r14 = (int) r11;
        r0 = r28;
        r11 = r0.scale;
        r12 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r11 = r12 * r11;
        r15 = (int) r11;
        r9.setMargins(r5, r13, r14, r15);
        r7.setLayoutParams(r9);
        r6 = 2131691228; // 0x7f0f06dc float:1.9011522E38 double:1.0531954033E-314;
        r0 = r28;
        r7 = r0.findViewById(r6);
        goto L_0x025e;
    L_0x025b:
        goto L_0x00c9;
    L_0x025e:
        r6 = 0;
        r7.setVisibility(r6);
        goto L_0x025b;
        goto L_0x0267;
    L_0x0264:
        goto L_0x00dc;
    L_0x0267:
        r17 = "#262626";
        goto L_0x0264;
        goto L_0x026e;
    L_0x026b:
        goto L_0x00fe;
    L_0x026e:
        r17 = "#ffffff";
        goto L_0x026b;
        goto L_0x0275;
    L_0x0272:
        goto L_0x0122;
    L_0x0275:
        r17 = "#93c4d3";
        goto L_0x0272;
        goto L_0x027c;
    L_0x0279:
        goto L_0x014c;
    L_0x027c:
        r6 = 2130838648; // 0x7f020478 float:1.7282284E38 double:1.052774173E-314;
        r0 = r21;
        r0.setImageResource(r6);
        goto L_0x0279;
        goto L_0x0289;
    L_0x0286:
        goto L_0x016b;
    L_0x0289:
        r6 = 2130838658; // 0x7f020482 float:1.7282305E38 double:1.0527741777E-314;
        r0 = r21;
        r0.setImageResource(r6);
        goto L_0x0286;
        goto L_0x0296;
    L_0x0293:
        goto L_0x018a;
    L_0x0296:
        r6 = 2130838661; // 0x7f020485 float:1.728231E38 double:1.052774179E-314;
        r0 = r21;
        r0.setImageResource(r6);
        goto L_0x0293;
    L_0x029f:
        r6 = 2130838652; // 0x7f02047c float:1.7282292E38 double:1.052774175E-314;
        r7.setBackgroundResource(r6);
        return;
    L_0x02a6:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navbar.NearingDest.adjustImages():void");
    }

    public void onOpen() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:64:0x02c8
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
        r42 = this;
        r2 = 1;
        r0 = r42;
        r0.nearingDisplayed = r2;
        r3 = 0;
        r0 = r42;
        r4 = r0.mDestAddressItem;
        if (r4 == 0) goto L_0x001c;
    L_0x000c:
        r0 = r42;
        r4 = r0.mDestAddressItem;
        r5 = r4.venueData;
        if (r5 == 0) goto L_0x001c;
    L_0x0014:
        r0 = r42;
        r4 = r0.mDestAddressItem;
        r5 = r4.venueData;
        r3 = r5.numImages;
    L_0x001c:
        r0 = r42;
        r0.adjustImages();
        r6 = com.waze.AppService.getMainActivity();
        if (r6 == 0) goto L_0x0030;
    L_0x0027:
        r7 = r6.getLayoutMgr();
        if (r7 == 0) goto L_0x0030;
    L_0x002d:
        r7.hideAlertTicker();
    L_0x0030:
        r2 = 2131691219; // 0x7f0f06d3 float:1.9011504E38 double:1.053195399E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r42;
        r9 = r0.nightMode;
        if (r9 == 0) goto L_0x02cc;
    L_0x003f:
        r10 = "#446270";
    L_0x0041:
        r11 = android.graphics.Color.parseColor(r10);
        r8.setBackgroundColor(r11);
        r2 = 2131691235; // 0x7f0f06e3 float:1.9011536E38 double:1.053195407E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r13 = r8;
        r13 = (android.widget.TextView) r13;
        r12 = r13;
        r0 = r42;
        r9 = r0.nightMode;
        if (r9 == 0) goto L_0x02d3;
    L_0x005b:
        r10 = "#add9ea";
    L_0x005d:
        r11 = android.graphics.Color.parseColor(r10);
        r12.setTextColor(r11);
        r2 = 2131691236; // 0x7f0f06e4 float:1.9011538E38 double:1.0531954072E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r14 = r8;
        r14 = (android.widget.TextView) r14;
        r12 = r14;
        r0 = r42;
        r9 = r0.nightMode;
        if (r9 == 0) goto L_0x02da;
    L_0x0077:
        r10 = "#ffffff";
    L_0x0079:
        r11 = android.graphics.Color.parseColor(r10);
        r12.setTextColor(r11);
        r15 = com.waze.AppService.getAppContext();
        r16 = r15.getResources();
        r0 = r16;
        r17 = r0.getConfiguration();
        r0 = r17;
        r11 = r0.orientation;
        r2 = 2;
        if (r11 != r2) goto L_0x00a0;
    L_0x0095:
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r2 = 1;
        r0 = r18;
        r0.bNearingMinimized = r2;
    L_0x00a0:
        r19 = new java.lang.StringBuilder;
        r0 = r19;
        r0.<init>();
        r0 = r19;
        r19 = r0.append(r3);
        r20 = "|";
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x02e1;
    L_0x00c2:
        r10 = "TRUE";
    L_0x00c4:
        r0 = r19;
        r19 = r0.append(r10);
        r20 = "|";
        r0 = r19;
        r1 = r20;
        r19 = r0.append(r1);
        r0 = r42;
        r10 = r0.mVenueID;
        r0 = r19;
        r19 = r0.append(r10);
        r0 = r19;
        r10 = r0.toString();
        r20 = "PLACES_NEAR_DESTINATION_SHOWN";
        r21 = "NUM_IMAGES|COLLAPSED|VENUE_ID";
        r0 = r20;
        r1 = r21;
        com.waze.analytics.Analytics.log(r0, r1, r10);
        r22 = new com.waze.navbar.NearingDest$7;
        r0 = r22;
        r1 = r42;
        r0.<init>();
        r0 = r42;
        r1 = r22;
        r0.setOnTouchListener(r1);
        r2 = 2131691220; // 0x7f0f06d4 float:1.9011506E38 double:1.0531953993E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r22;
        r8.setOnTouchListener(r0);
        r2 = 2131691240; // 0x7f0f06e8 float:1.9011546E38 double:1.053195409E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r22;
        r8.setOnTouchListener(r0);
        r2 = 2131691235; // 0x7f0f06e3 float:1.9011536E38 double:1.053195407E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x02e8;
    L_0x012f:
        r23 = 8;
    L_0x0131:
        r0 = r23;
        r8.setVisibility(r0);
        r0 = r42;
        r0 = r0.mCarpoolDrive;
        r24 = r0;
        if (r24 != 0) goto L_0x02f2;
    L_0x013e:
        r2 = 2131691220; // 0x7f0f06d4 float:1.9011506E38 double:1.0531953993E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x02ef;
    L_0x0151:
        r23 = 8;
    L_0x0153:
        r0 = r23;
        r8.setVisibility(r0);
        r2 = 2131691230; // 0x7f0f06de float:1.9011526E38 double:1.0531954043E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r2 = 8;
        r8.setVisibility(r2);
        r2 = 2131691231; // 0x7f0f06df float:1.9011528E38 double:1.053195405E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r2 = 8;
        r8.setVisibility(r2);
        r2 = 2131691234; // 0x7f0f06e2 float:1.9011534E38 double:1.0531954063E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r2 = 0;
        r8.setVisibility(r2);
    L_0x0181:
        r2 = 2131691236; // 0x7f0f06e4 float:1.9011538E38 double:1.0531954072E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r25 = r8;
        r25 = (android.widget.TextView) r25;
        r12 = r25;
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x0360;
    L_0x019a:
        r26 = 1096810496; // 0x41600000 float:14.0 double:5.41896386E-315;
    L_0x019d:
        r2 = 2;
        r0 = r26;
        r12.setTextSize(r2, r0);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x0368;
    L_0x01ad:
        r23 = 1;
    L_0x01af:
        r0 = r23;
        r12.setMaxLines(r0);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x036f;
    L_0x01be:
        r23 = 8;
    L_0x01c0:
        r0 = r23;
        r12.setVisibility(r0);
        r27 = com.waze.navigate.DriveToNativeManager.getInstance();
        r0 = r27;
        r10 = r0.getTimeToParkNearingStrNTV();
        if (r10 == 0) goto L_0x020f;
    L_0x01d1:
        r9 = r10.isEmpty();
        if (r9 != 0) goto L_0x020f;
    L_0x01d7:
        r2 = 2131691237; // 0x7f0f06e5 float:1.901154E38 double:1.0531954077E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r29 = r8;
        r29 = (android.widget.TextView) r29;
        r28 = r29;
        r2 = 0;
        r0 = r28;
        r0.setVisibility(r2);
        r0 = r28;
        r0.setText(r10);
        r0 = r42;
        r9 = r0.nightMode;
        if (r9 == 0) goto L_0x01ff;
    L_0x01f7:
        r2 = -16777216; // 0xffffffffff000000 float:-1.7014118E38 double:NaN;
        r0 = r28;
        r0.setBackgroundColor(r2);
    L_0x01ff:
        r30 = new com.waze.navbar.NearingDest$8;
        r0 = r30;
        r1 = r42;
        r0.<init>();
        r0 = r28;
        r1 = r30;
        r0.setOnClickListener(r1);
    L_0x020f:
        r10 = " ";
        r0 = r42;
        r0 = r0.mCarpoolDrive;
        r24 = r0;
        if (r24 == 0) goto L_0x0372;
    L_0x0219:
        r31 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r32 = new com.waze.navbar.NearingDest$9;
        r0 = r32;
        r1 = r42;
        r0.<init>(r12);
        r0 = r31;
        r1 = r32;
        r0.isCurMeetingPickup(r1);
    L_0x022d:
        r20 = "home";
        r0 = r20;
        r9 = r10.equalsIgnoreCase(r0);
        if (r9 != 0) goto L_0x0242;
    L_0x0237:
        r20 = "work";
        r0 = r20;
        r9 = r10.equalsIgnoreCase(r0);
        if (r9 == 0) goto L_0x024c;
    L_0x0242:
        r33 = com.waze.NativeManager.getInstance();
        r0 = r33;
        r10 = r0.getLanguageString(r10);
    L_0x024c:
        r12.setText(r10);
        r34 = r12.getLayoutParams();
        r36 = r34;
        r36 = (android.widget.LinearLayout.LayoutParams) r36;
        r35 = r36;
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x03ec;
    L_0x0263:
        r2 = 2131691240; // 0x7f0f06e8 float:1.9011546E38 double:1.053195409E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r3 = r8.getWidth();
        r0 = r42;
        r11 = r0.getWidth();
        r11 = r11 - r3;
        r0 = r42;
        r0 = r0.scale;
        r26 = r0;
        r37 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r26 = r37 * r26;
        r0 = r26;
        r0 = (int) r0;
        r38 = r0;
        r11 = r11 - r0;
        r0 = r35;
        r0.width = r11;
        r0 = r42;
        r0 = r0.scale;
        r26 = r0;
        r37 = 1101004800; // 0x41a00000 float:20.0 double:5.439686476E-315;
        r26 = r37 * r26;
        r0 = r26;
        r11 = (int) r0;
        r3 = r11 + r3;
        r0 = r35;
        r0.rightMargin = r3;
    L_0x02a0:
        r0 = r35;
        r12.setLayoutParams(r0);
        r2 = 2131691220; // 0x7f0f06d4 float:1.9011506E38 double:1.0531953993E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r2 = 2131691235; // 0x7f0f06e3 float:1.9011536E38 double:1.053195407E-314;
        r0 = r42;
        r39 = r0.findViewById(r2);
        r0 = r42;
        r0.clearAnimation();
        r8.clearAnimation();
        r12.clearAnimation();
        r0 = r39;
        r0.clearAnimation();
        return;
        goto L_0x02cc;
    L_0x02c9:
        goto L_0x0041;
    L_0x02cc:
        r10 = "#2e2e2e";
        goto L_0x02c9;
        goto L_0x02d3;
    L_0x02d0:
        goto L_0x005d;
    L_0x02d3:
        r10 = "#ffffff";
        goto L_0x02d0;
        goto L_0x02da;
    L_0x02d7:
        goto L_0x0079;
    L_0x02da:
        r10 = "#93c4d3";
        goto L_0x02d7;
        goto L_0x02e1;
    L_0x02de:
        goto L_0x00c4;
    L_0x02e1:
        r10 = "FALSE";
        goto L_0x02de;
        goto L_0x02e8;
    L_0x02e5:
        goto L_0x0131;
    L_0x02e8:
        r23 = 0;
        goto L_0x02e5;
        goto L_0x02ef;
    L_0x02ec:
        goto L_0x0153;
    L_0x02ef:
        r23 = 0;
        goto L_0x02ec;
    L_0x02f2:
        r2 = 2131691220; // 0x7f0f06d4 float:1.9011506E38 double:1.0531953993E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r2 = 8;
        r8.setVisibility(r2);
        r2 = 2131691230; // 0x7f0f06de float:1.9011526E38 double:1.0531954043E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x0353;
    L_0x0313:
        r23 = 8;
    L_0x0315:
        r0 = r23;
        r8.setVisibility(r0);
        r2 = 2131691231; // 0x7f0f06df float:1.9011528E38 double:1.053195405E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x0356;
    L_0x032d:
        r23 = 8;
    L_0x032f:
        r0 = r23;
        r8.setVisibility(r0);
        r2 = 2131691234; // 0x7f0f06e2 float:1.9011534E38 double:1.0531954063E-314;
        r0 = r42;
        r8 = r0.findViewById(r2);
        r0 = r42;
        r0 = r0.mAddressItemAppData;
        r18 = r0;
        r9 = r0.bNearingMinimized;
        if (r9 == 0) goto L_0x0359;
    L_0x0347:
        r23 = 8;
        goto L_0x034d;
    L_0x034a:
        goto L_0x0181;
    L_0x034d:
        r0 = r23;
        r8.setVisibility(r0);
        goto L_0x034a;
    L_0x0353:
        r23 = 0;
        goto L_0x0315;
    L_0x0356:
        r23 = 0;
        goto L_0x032f;
    L_0x0359:
        r23 = 0;
        goto L_0x034d;
        goto L_0x0360;
    L_0x035d:
        goto L_0x019d;
    L_0x0360:
        r26 = 1099956224; // 0x41900000 float:18.0 double:5.43450582E-315;
        goto L_0x035d;
        goto L_0x0368;
    L_0x0365:
        goto L_0x01af;
    L_0x0368:
        r23 = 2;
        goto L_0x0365;
        goto L_0x036f;
    L_0x036c:
        goto L_0x01c0;
    L_0x036f:
        r23 = 0;
        goto L_0x036c;
    L_0x0372:
        r0 = r42;
        r0 = r0.destinationData;
        r40 = r0;
        if (r40 == 0) goto L_0x022d;
    L_0x037a:
        r0 = r42;
        r0 = r0.destinationData;
        r40 = r0;
        r0 = r0.destinationName;
        r41 = r0;
        if (r41 == 0) goto L_0x022d;
    L_0x0386:
        r0 = r42;
        r0 = r0.destinationData;
        r40 = r0;
        r0 = r0.destinationName;
        r41 = r0;
        r9 = r0.isEmpty();
        if (r9 != 0) goto L_0x022d;
    L_0x0396:
        r0 = r42;
        r0 = r0.destinationData;
        r40 = r0;
        r10 = r0.destinationName;
        r2 = 287; // 0x11f float:4.02E-43 double:1.42E-321;
        r41 = com.waze.strings.DisplayStrings.displayString(r2);
        r0 = r41;
        r9 = r10.equalsIgnoreCase(r0);
        if (r9 != 0) goto L_0x03be;
    L_0x03ac:
        r2 = 1313; // 0x521 float:1.84E-42 double:6.487E-321;
        r41 = com.waze.strings.DisplayStrings.displayString(r2);
        r0 = r41;
        r9 = r10.equalsIgnoreCase(r0);
        if (r9 == 0) goto L_0x03c5;
    L_0x03ba:
        goto L_0x03be;
    L_0x03bb:
        goto L_0x022d;
    L_0x03be:
        r2 = 287; // 0x11f float:4.02E-43 double:1.42E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r2);
        goto L_0x03bb;
    L_0x03c5:
        r2 = 288; // 0x120 float:4.04E-43 double:1.423E-321;
        r41 = com.waze.strings.DisplayStrings.displayString(r2);
        r0 = r41;
        r9 = r10.equalsIgnoreCase(r0);
        if (r9 != 0) goto L_0x03e5;
    L_0x03d3:
        r2 = 1314; // 0x522 float:1.841E-42 double:6.49E-321;
        r41 = com.waze.strings.DisplayStrings.displayString(r2);
        r0 = r41;
        r9 = r10.equalsIgnoreCase(r0);
        if (r9 == 0) goto L_0x022d;
    L_0x03e1:
        goto L_0x03e5;
    L_0x03e2:
        goto L_0x022d;
    L_0x03e5:
        r2 = 288; // 0x120 float:4.04E-43 double:1.423E-321;
        r10 = com.waze.strings.DisplayStrings.displayString(r2);
        goto L_0x03e2;
    L_0x03ec:
        r2 = -2;
        r0 = r35;
        r0.width = r2;
        r0 = r42;
        r0 = r0.scale;
        r26 = r0;
        r37 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r26 = r37 * r26;
        r0 = r26;
        r3 = (int) r0;
        goto L_0x0403;
    L_0x0400:
        goto L_0x02a0;
    L_0x0403:
        r0 = r35;
        r0.rightMargin = r3;
        goto L_0x0400;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navbar.NearingDest.onOpen():void");
    }

    private void handleMessage(Message $r1) throws  {
        if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
            this.dtnm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            Bundle $r5 = $r1.getData();
            VenueData $r9;
            VenueData $r8;
            String $r12;
            VenueData $r92;
            int $i0;
            if (this.mPhotoGallery == null || !this.mPhotoGallery.isShowing()) {
                this.mReceivedSearchResult = true;
                this.mDestAddressItem = (AddressItem) $r5.getSerializable("address_item");
                if (this.mDestAddressItem != null) {
                    $r9 = this.mDestAddressItem;
                    $r8 = $r9;
                    if ($r9.venueData != null) {
                        String $r122 = this.mDestAddressItem;
                        String $r82 = $r122;
                        $r122 = $r122.VanueID;
                        $r12 = $r122;
                        this.mVenueID = $r122;
                        $r9 = this.mDestAddressItem;
                        $r8 = $r9;
                        $r9 = $r9.venueData;
                        $r92 = $r9;
                        this.mFoundVenue = $r9;
                        if (this.mSaveNav) {
                            this.mSaveNav = false;
                            this.nm.venueSaveNav(this.mFoundVenue);
                        }
                        $r9 = this.mDestAddressItem;
                        $r8 = $r9;
                        updateVenueImages($r9.venueData);
                        $r9 = this.mDestAddressItem;
                        $r8 = $r9;
                        if ($r9.venueData != null) {
                            $r9 = this.mDestAddressItem;
                            $r8 = $r9;
                            $r9 = $r9.venueData;
                            $i0 = $r9.numImages;
                        }
                    }
                }
                if (this.nearingDisplayed) {
                    onOpen();
                    return;
                }
                onOpen();
                INavBarViewCallbacks iNavBarViewCallbacks = this.mCallbacks;
                INavBarViewCallbacks $r18 = iNavBarViewCallbacks;
                iNavBarViewCallbacks.onReady(this);
                return;
            }
            this.mDestAddressItem = (AddressItem) $r5.getSerializable("address_item");
            if (this.mDestAddressItem != null) {
                $r9 = this.mDestAddressItem;
                $r8 = $r9;
                if ($r9.venueData != null) {
                    $r9 = this.mDestAddressItem;
                    $r8 = $r9;
                    $r9 = $r9.venueData;
                    $r92 = $r9;
                    this.mFoundVenue = $r9;
                    $r9 = this.mFoundVenue;
                    ArrayList arrayList = new ArrayList($r9.numImages);
                    $i0 = 0;
                    while (true) {
                        $r9 = this.mFoundVenue;
                        if ($i0 < $r9.numImages) {
                            String[] $r11 = this.mFoundVenue;
                            $r92 = $r11;
                            $r12 = $r11.imageURLs[$i0];
                            $r11 = this.mFoundVenue;
                            $r92 = $r11;
                            String $r13 = $r11.imageThumbnailURLs[$i0];
                            $r11 = this.mFoundVenue;
                            $r92 = $r11;
                            String $r14 = $r11.imageReporters[$i0];
                            $r11 = this.mFoundVenue;
                            $r92 = $r11;
                            String $r15 = $r11.imageReporterMoods[$i0];
                            boolean[] $r16 = this.mFoundVenue;
                            $r92 = $r16;
                            boolean $z0 = $r16.imageLiked[$i0];
                            $r16 = this.mFoundVenue;
                            $r92 = $r16;
                            arrayList.add(new VenueImage($r12, $r13, $r14, $r15, $z0, $r16.imageByMe[$i0], false));
                            $i0++;
                        } else {
                            this.mPhotoGallery.updateImages(arrayList);
                            return;
                        }
                    }
                }
            }
        }
    }

    public NearingDest(Context $r1) throws  {
        super($r1);
        init($r1);
    }

    public NearingDest(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        init($r1);
    }

    public NearingDest(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init($r1);
    }

    @TargetApi(21)
    public NearingDest(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super($r1, $r2, $i0, $i1);
        init($r1);
    }

    private void init(Context $r1) throws  {
        if (!isInEditMode()) {
            this.nm = NativeManager.getInstance();
            this.dtnm = DriveToNativeManager.getInstance();
        }
        setOrientation(1);
        this.scale = $r1.getResources().getDisplayMetrics().density;
        LayoutInflater.from($r1).inflate(C1283R.layout.navbar_nearing_dest_photo, this);
    }

    public void setCallbacks(INavBarViewCallbacks $r1) throws  {
        this.mCallbacks = $r1;
    }

    public void onUpdate() throws  {
        adjustImages();
    }

    public void setSkin(boolean $z0) throws  {
        this.nightMode = $z0;
        adjustImages();
    }

    public View getView() throws  {
        return this;
    }

    void nearingDestinationSetup() throws  {
        this.mOnClickLaunchAddPlace = new C20421();
        findViewById(C1283R.id.navBarNearingDestPhotoFrameContainer).setOnClickListener(this.mOnClickLaunchAddPlace);
        findViewById(C1283R.id.navBarNearingDestPhotoImageLayout).setVisibility(8);
        findViewById(C1283R.id.navBarNearingDestPhotoNoImageLayout).setVisibility(0);
        findViewById(C1283R.id.navBarNearingDestPhotoFrameMore).setVisibility(8);
    }

    private void openGallery(View $r1) throws  {
        if (this.mFoundVenue.bHasMoreData) {
            this.dtnm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            this.dtnm.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            this.nm.venueGet(this.mFoundVenue.id, 1, true);
        }
        ArrayList arrayList = new ArrayList(this.mFoundVenue.numImages);
        int $i0 = 0;
        while (true) {
            int $i1 = this.mFoundVenue.numImages;
            if ($i0 < $i1) {
                arrayList.add(new VenueImage(this.mFoundVenue.imageURLs[$i0], this.mFoundVenue.imageThumbnailURLs[$i0], this.mFoundVenue.imageReporters[$i0], this.mFoundVenue.imageReporterMoods[$i0], this.mFoundVenue.imageLiked[$i0], this.mFoundVenue.imageByMe[$i0], false));
                $i0++;
            } else {
                final ArrayList arrayList2 = arrayList;
                this.mPhotoGallery = PhotoPagerSection.openPlacePhotoGalleryFromView($r1, arrayList, 0, new IPhotoGalleryListener() {
                    public void onScroll(int position) throws  {
                    }

                    public void onFlag(int $i0, int $i1) throws  {
                        NearingDest.this.nm.venueFlagImage(NearingDest.this.mFoundVenue.id, NearingDest.this.mFoundVenue.imageURLs[$i0], $i1);
                        NearingDest.this.mFoundVenue.removeImage($i0);
                        arrayList2.remove($i0);
                        if (NearingDest.this.mFoundVenue.numImages > 0) {
                            NearingDest.this.mPhotoGallery.updateImages(arrayList2);
                        } else {
                            NearingDest.this.mPhotoGallery.dismiss();
                        }
                        if ($i0 == 0) {
                            NearingDest.this.updateVenueImages(NearingDest.this.mFoundVenue);
                        }
                    }

                    public void onDelete(int $i0) throws  {
                        NearingDest.this.nm.venueDeleteImage(NearingDest.this.mFoundVenue.id, NearingDest.this.mFoundVenue.imageURLs[$i0]);
                        NearingDest.this.mFoundVenue.removeImage($i0);
                        arrayList2.remove($i0);
                        if (NearingDest.this.mFoundVenue.numImages > 0) {
                            NearingDest.this.mPhotoGallery.updateImages(arrayList2);
                        } else {
                            NearingDest.this.mPhotoGallery.dismiss();
                        }
                        if ($i0 == 0) {
                            NearingDest.this.updateVenueImages(NearingDest.this.mFoundVenue);
                        }
                    }

                    public void onLike(int $i0) throws  {
                        NearingDest.this.mFoundVenue.imageLiked[$i0] = true;
                        NearingDest.this.nm.venueLikeImage(NearingDest.this.mFoundVenue.id, NearingDest.this.mFoundVenue.imageURLs[$i0]);
                    }

                    public void onUnlike(int $i0) throws  {
                        NearingDest.this.mFoundVenue.imageLiked[$i0] = false;
                        NearingDest.this.nm.venueUnlikeImage(NearingDest.this.mFoundVenue.id, NearingDest.this.mFoundVenue.imageURLs[$i0]);
                    }
                }, AppService.getMainActivity());
                return;
            }
        }
    }

    public void onMinimize() throws  {
        if (this.mAddressItemAppData.bNearingMinimized) {
            restoreNearingPanel();
        } else {
            if (this.mCarpoolDrive != null) {
                String $r5;
                if (this.mCarpoolIsPickup) {
                    $r5 = "PICKUP";
                } else {
                    $r5 = "DROPOFF|COLLAPSE" + this.mCarpoolDrive.getId();
                }
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NEARING_DESTINATION_CLICKED, "TYPE|BUTTON|DRIVE_ID", $r5);
            }
            minimizeNearingPanel();
        }
        this.mAddressItemAppData.bNearingMinimized = !this.mAddressItemAppData.bNearingMinimized;
        this.dtnm.updateAddressItemAppData(this.dtnm.getDestinationIdNTV(), this.mAddressItemAppData);
    }

    public void onShow() throws  {
        this.mDestAddressItem = null;
        this.mFoundVenue = null;
        this.mVenueID = null;
        this.mCarpoolDrive = null;
        this.mAddressItemAppData = new AddressItemAppData();
        this.mAddressItemAppData.bNearingMinimized = false;
        CarpoolNativeManager $r3 = CarpoolNativeManager.getInstance();
        String $r4 = $r3.getCurMeetingIdNTV();
        if ($r4 == null || $r4.isEmpty()) {
            findViewById(C1283R.id.navBarNearingDestButtonMore).setVisibility(8);
            this.dtnm.getAddressItemAppData(this.dtnm.getDestinationIdNTV(), new C20454());
            updateVenueImages(null);
            this.dtnm.getLocationData(1, Integer.valueOf(0), Integer.valueOf(0), new C20465(), null);
            this.mReceivedSearchResult = false;
            C20476 c20476 = new C20476();
            NativeManager $r13 = this.nm;
            postDelayed(c20476, (long) $r13.getVenueGetTimeout());
            return;
        }
        $r3.getDriveInfoByMeetingId($r4, new C20443());
    }

    private void updateVenueImages(VenueData $r1) throws  {
        byte $b0;
        findViewById(C1283R.id.navBarNearingDestRider).setVisibility(8);
        findViewById(C1283R.id.navBarNearingDestCarpoolButtons).setVisibility(8);
        View $r2 = findViewById(C1283R.id.navBarNearingDestPhotoFrameContainer);
        if (this.mAddressItemAppData.bNearingMinimized) {
            $b0 = (byte) 8;
        } else {
            $b0 = (byte) 0;
        }
        $r2.setVisibility($b0);
        findViewById(C1283R.id.navBarNearingDestButton).setOnClickListener(this.mOnClickLaunchAddPlace);
        ((TextView) findViewById(C1283R.id.navBarNearingDestPhotoAlmostThere)).setText(this.nm.getLanguageString((int) DisplayStrings.DS_ALMOST_THERE___));
        ((TextView) findViewById(C1283R.id.navBarNearingDestPhotoAddPhotoText1)).setText(this.nm.getLanguageString((int) DisplayStrings.DS_BE_THE_FIRST_TO_ADD_A_PHOTOE));
        ((TextView) findViewById(C1283R.id.navBarNearingDestNameText)).setText(" ");
        TextView textView = (TextView) findViewById(C1283R.id.navBarNearingDestButtonText);
        textView.setText(this.nm.getLanguageString((int) DisplayStrings.DS_NEARING_DEST_TAKE_PHOTO));
        textView.setTextSize(1, 16.0f);
        ((ImageView) findViewById(C1283R.id.navBarNearingDestButtonImage)).setImageResource(C1283R.drawable.nearing_destination_addphoto_icon);
        $r2 = findViewById(C1283R.id.navBarNearingDestPhotoNoImageLayout);
        if ($r1 == null || $r1.numImages == 0) {
            $r2.setVisibility(0);
            findViewById(C1283R.id.navBarNearingDestPhotoImageLayout).setVisibility(8);
            findViewById(C1283R.id.navBarNearingDestPhotoFrameMore).setVisibility(8);
            return;
        }
        $r2.setVisibility(8);
        findViewById(C1283R.id.navBarNearingDestPhotoImageLayout).setVisibility(0);
        ImageView $r8 = (ImageView) findViewById(C1283R.id.navBarNearingDestPhotoImage);
        ImageView $r9 = (ImageView) findViewById(C1283R.id.navBarNearingDestPhotoByMood);
        textView = (TextView) findViewById(C1283R.id.navBarNearingDestPhotoBy);
        if ($r1.imageReporterMoods[0] == null || $r1.imageReporterMoods[0].isEmpty()) {
            $r9.setVisibility(8);
        } else {
            $r9.setImageDrawable(MoodManager.getMoodDrawable(getContext(), $r1.imageReporterMoods[0]));
        }
        $r8.setImageDrawable(null);
        Context $r11 = AppService.getMainActivity();
        if ($r11 != null) {
            ImageRepository.instance.getImage($r1.imageThumbnailURLs[0], 1, $r8, null, $r11, 3000, 3, AnalyticsEvents.ANALYTICS_EVENT_LATENCY_VENUE_IMAGE);
        }
        textView.setText($r1.imageReporters[0]);
        findViewById(C1283R.id.navBarNearingDestPhotoFrameMore).setVisibility($r1.numImages > 1 ? (byte) 0 : (byte) 8);
    }

    private void updateRiderImages(CarpoolUserData cud) throws  {
        byte $b0;
        ImageView $r3 = (ImageView) findViewById(C1283R.id.navBarNearingDestRider);
        $r3.setVisibility(this.mAddressItemAppData.bNearingMinimized ? (byte) 8 : (byte) 0);
        View $r2 = findViewById(C1283R.id.navBarNearingDestCarpoolButtons);
        if (this.mAddressItemAppData.bNearingMinimized) {
            $b0 = (byte) 8;
        } else {
            $b0 = (byte) 0;
        }
        $r2.setVisibility($b0);
        findViewById(C1283R.id.navBarNearingDestPhotoFrameContainer).setVisibility(8);
        final CarpoolNativeManager $r5 = CarpoolNativeManager.getInstance();
        CarpoolNativeManager.getInstance().isCurMeetingPickup(new IResultOk() {
            public void onResult(boolean $z0) throws  {
                NearingDest.this.mCarpoolIsPickup = $z0;
                NearingDest.this.setButtonsForPickupOrDropOff($r5, $z0);
            }
        });
        ((TextView) findViewById(C1283R.id.navBarNearingDestPhotoAlmostThere)).setText(this.nm.getLanguageString((int) DisplayStrings.DS_ALMOST_THERE___));
        ((TextView) findViewById(C1283R.id.navBarNearingDestNameText)).setText(" ");
        ((ImageView) findViewById(C1283R.id.navBarNearingDestButtonImage)).setVisibility(8);
        if (this.mCarpoolDrive != null) {
            ImageView $r11 = (ImageView) findViewById(C1283R.id.navBarNearingDestButtonMore);
            $r11.setVisibility(0);
            if (this.nightMode) {
                $r11.setBackgroundResource(C1283R.drawable.nearing_destination_night_button_sel);
                $r11.setImageResource(C1283R.drawable.nearing_destination_more_options_day);
            }
            $r11.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    CarpoolNativeManager.getInstance().pickDestinationDialog(NearingDest.this.mCarpoolDrive, null);
                }
            });
        }
        CarpoolDrive $r12 = this.mCarpoolDrive;
        String $r10 = $r12.getRider().getImage();
        if ($r10 == null || $r10.isEmpty()) {
            $r3.setImageResource(C1283R.drawable.rs_profilepic_placeholder);
        } else {
            ImageRepository.instance.getImage($r10, 3, $r3, null, AppService.getMainActivity());
        }
    }

    @NonNull
    private void setButtonsForPickupOrDropOff(final CarpoolNativeManager $r1, final boolean $z0) throws  {
        View $r2 = findViewById(C1283R.id.navBarNearingDestMessageRider);
        View $r3 = findViewById(C1283R.id.navBarNearingDestRideDetails);
        final String $r5 = this.mCarpoolDrive == null ? "" : this.mCarpoolDrive.getId();
        if ($z0) {
            $r2.setVisibility(0);
            $r2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NEARING_DESTINATION_CLICKED, "TYPE|BUTTON|DRIVE_ID", "PICKUP|TEXT" + $r5);
                    if ($r1.isContactRiderAllowed(null, NearingDest.this.mCarpoolDrive.getRide())) {
                        new ContactRiderDialog(AppService.getMainActivity(), NearingDest.this.mCarpoolDrive.getRide().getProxyNumber(), NearingDest.this.mCarpoolDrive.getRider().getGivenName(), NearingDest.this.mCarpoolDrive.getRide(), false).show();
                        return;
                    }
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_PROFILE_COMM_STATUS, "STATUS|RIDE_ID|TYPE", "DISABLE|" + $r5 + "|DETAILS");
                }
            });
            $r3.setVisibility(8);
        } else {
            $r2.setVisibility(8);
            $r3.setVisibility(0);
            $r3.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NEARING_DESTINATION_CLICKED, "TYPE|BUTTON|DRIVE_ID", "DROPOFF|INFO" + $r5);
                    if (NearingDest.this.mCarpoolDrive == null) {
                        Logger.m38e("NearingDest: ride is null! cannot view ride details");
                        MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
                        return;
                    }
                    MainActivity $r9 = AppService.getMainActivity();
                    Intent $r2 = new Intent($r9, CarpoolRideDetailsActivity.class);
                    $r2.putExtra(CarpoolDrive.class.getSimpleName(), NearingDest.this.mCarpoolDrive);
                    $r9.startActivity($r2);
                }
            });
        }
        $r2 = findViewById(C1283R.id.navBarNearingDestButton);
        $r2.setOnClickListener(new OnClickListener() {

            class C20391 implements IResultObj<String> {
                C20391() throws  {
                }

                public void onResult(String $r1) throws  {
                    DriveToNativeManager.getInstance().drive($r1, false);
                }
            }

            public void onClick(View v) throws  {
                if ($z0) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NEARING_DESTINATION_CLICKED, "TYPE|BUTTON|DRIVE_ID", "PICKUP|DRIVE_TO" + NearingDest.this.mCarpoolDrive.getId());
                    CarpoolNativeManager.getInstance().getMeetingIdByDrive(NearingDest.this.mCarpoolDrive, false, new C20391());
                    return;
                }
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NEARING_DESTINATION_CLICKED, "TYPE|BUTTON|DRIVE_ID", "DROPOFF|DRIVE_TO" + NearingDest.this.mCarpoolDrive.getId());
                DriveToNativeManager.getInstance().navigate(CarpoolNativeManager.getInstance().driveGetAddressItem(NearingDest.this.mCarpoolDrive, 4), null);
            }
        });
        TextView $r8 = (TextView) findViewById(C1283R.id.navBarNearingDestButtonText);
        $r8.setTextSize(1, 20.0f);
        if ($z0) {
            $r8.setText(this.nm.getLanguageString((int) DisplayStrings.DS_NEARING_DEST_DRIVE_DROPOFF));
            $r2.setBackgroundResource(C1283R.drawable.rw_pickup_btn_sel);
            return;
        }
        $r8.setText(this.nm.getLanguageString((int) DisplayStrings.DS_NEARING_DEST_DRIVE_DEST));
        $r2.setBackgroundResource(C1283R.drawable.rw_dropoff_btn_sel);
    }

    public void onHide() throws  {
        this.nearingDisplayed = false;
        this.dtnm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
    }

    public void minimizeNearingPanel() throws  {
        final View $r4 = findViewById(C1283R.id.navBarNearingDestPhotoAlmostThere);
        if (this.mCarpoolDrive == null) {
            AlphaAnimation $r2 = new AlphaAnimation(1.0f, 0.0f);
            $r2.setDuration(150);
            $r2.setFillAfter(true);
            $r4.startAnimation($r2);
        }
        View $r6 = findViewById(C1283R.id.navBarNearingDestNameText);
        if (this.mCarpoolDrive == null) {
            $r2 = new AlphaAnimation(1.0f, 0.0f);
            $r2.setDuration(150);
            $r2.setFillAfter(true);
            $r6.startAnimation($r2);
        }
        $r6 = findViewById(C1283R.id.navBarNearingDestButton);
        int $i0 = ((float) $r6.getHeight()) + (16.0f * $r6.getResources().getDisplayMetrics().density);
        int i = $i0;
        $i0 = ((int) $i0) - findViewById(C1283R.id.navBarNearingDestPhoto).getHeight();
        int i2 = $i0;
        Animation translateAnimation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) $i0);
        translateAnimation.setDuration(300);
        translateAnimation.setFillAfter(true);
        translateAnimation.setInterpolator(new OvershootInterpolator());
        startAnimation(translateAnimation);
        View $r7 = findViewById(C1283R.id.navBarNearingDestPhotoFrameContainer);
        if (this.mCarpoolDrive == null) {
            float $f0 = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR * ((float) $r7.getWidth());
            float $f1 = ($r7.getHeight() + $r7.getTop()) - ($r6.getHeight() / 2);
            float f = $f1;
            translateAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, $f0, (float) $f1);
            translateAnimation.setDuration(300);
            translateAnimation.setFillAfter(true);
            $r7.startAnimation(translateAnimation);
            $r2 = new AlphaAnimation(1.0f, 0.0f);
            $r2.setDuration(300);
            $r2.setFillAfter(true);
            $r6.startAnimation($r2);
        }
        final View view = $r7;
        translateAnimation.setAnimationListener(new AnimationListener() {

            class C20401 implements OnGlobalLayoutListener {
                C20401() throws  {
                }

                public void onGlobalLayout() throws  {
                    NearingDest.this.clearAnimation();
                    if (NearingDest.this.mCarpoolDrive == null) {
                        AlphaAnimation $r1 = new AlphaAnimation(0.0f, 1.0f);
                        $r1.setDuration(300);
                        $r1.setFillAfter(true);
                        $r6.startAnimation($r1);
                    }
                    NearingDest.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                }
            }

            public void onAnimationStart(Animation animation) throws  {
            }

            public void onAnimationRepeat(Animation animation) throws  {
            }

            public void onAnimationEnd(Animation animation) throws  {
                $r4.setVisibility(8);
                view.setVisibility(8);
                NearingDest.this.findViewById(C1283R.id.navBarNearingDestPhotoTexts).setVisibility(8);
                NearingDest.this.findViewById(C1283R.id.navBarNearingDestRider).setVisibility(8);
                NearingDest.this.findViewById(C1283R.id.navBarNearingDestCarpoolButtons).setVisibility(8);
                NearingDest.this.getViewTreeObserver().addOnGlobalLayoutListener(new C20401());
                NearingDest.this.requestLayout();
            }
        });
    }

    public void restoreNearingPanel() throws  {
        View $r2 = findViewById(C1283R.id.navBarNearingDestNameText);
        View $r3 = findViewById(C1283R.id.navBarNearingDestButton);
        if (this.mCarpoolDrive == null) {
            AlphaAnimation $r1 = new AlphaAnimation(1.0f, 0.0f);
            $r1.setDuration(100);
            $r3.startAnimation($r1);
        }
        View $r5 = findViewById(C1283R.id.navBarNearingDestPhotoFrameContainer);
        if (this.mCarpoolDrive == null) {
            $r5.setVisibility(0);
        } else {
            findViewById(C1283R.id.navBarNearingDestRider).setVisibility(0);
            findViewById(C1283R.id.navBarNearingDestCarpoolButtons).setVisibility(0);
            findViewById(C1283R.id.navBarNearingDestPhotoTexts).setVisibility(0);
            findViewById(C1283R.id.navBarNearingDestPhotoAlmostThere).setVisibility(0);
            findViewById(C1283R.id.navBarNearingDestNameText).setVisibility(0);
        }
        View $r6 = findViewById(C1283R.id.navBarNearingDestPhoto);
        final View view = $r3;
        final View view2 = $r6;
        final View view3 = $r5;
        final View view4 = $r2;
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            class C20411 implements AnimationListener {
                C20411() throws  {
                }

                public void onAnimationStart(Animation animation) throws  {
                }

                public void onAnimationRepeat(Animation animation) throws  {
                }

                public void onAnimationEnd(Animation animation) throws  {
                    NearingDest.this.findViewById(C1283R.id.navBarNearingDestPhotoTexts).setVisibility(0);
                    View $r5 = NearingDest.this.findViewById(C1283R.id.navBarNearingDestPhotoAlmostThere);
                    $r5.setVisibility(0);
                    view4.setVisibility(0);
                    ((TextView) view4).setTextSize(2, 18.0f);
                    ((TextView) view4).setMaxLines(2);
                    LayoutParams $r9 = (LayoutParams) view4.getLayoutParams();
                    $r9.width = -2;
                    int $i0 = (int) (10.0f * NearingDest.this.scale);
                    $r9.rightMargin = $i0;
                    view4.setLayoutParams($r9);
                    if (NearingDest.this.mCarpoolDrive == null) {
                        Animation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration(150);
                        alphaAnimation.setFillAfter(false);
                        $r5.startAnimation(alphaAnimation);
                        alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
                        alphaAnimation.setDuration(150);
                        alphaAnimation.setFillAfter(false);
                        view4.startAnimation(alphaAnimation);
                        view3.setVisibility(0);
                        view3.clearAnimation();
                    } else {
                        NearingDest.this.findViewById(C1283R.id.navBarNearingDestRider).setVisibility(0);
                        NearingDest.this.findViewById(C1283R.id.navBarNearingDestCarpoolButtons).setVisibility(0);
                    }
                    NearingDest.this.clearAnimation();
                }
            }

            public void onGlobalLayout() throws  {
                int $i0 = (int) (((float) view.getHeight()) + (16.0f * view.getResources().getDisplayMetrics().density));
                TranslateAnimation $r3 = new TranslateAnimation(0.0f, 0.0f, (float) ($i0 - view2.getHeight()), 0.0f);
                $r3.setDuration(300);
                $r3.setFillAfter(false);
                $r3.setInterpolator(new DecelerateInterpolator());
                NearingDest $r8 = NearingDest.this;
                $r8.startAnimation($r3);
                $r8 = NearingDest.this;
                if ($r8.mCarpoolDrive == null) {
                    Animation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR * ((float) view3.getWidth()), (float) ((view3.getHeight() + view3.getTop()) - ($i0 / 2)));
                    scaleAnimation.setDuration(300);
                    scaleAnimation.setFillAfter(false);
                    scaleAnimation.setInterpolator(new DecelerateInterpolator());
                    view3.startAnimation(scaleAnimation);
                    scaleAnimation = new AlphaAnimation(0.0f, 1.0f);
                    scaleAnimation.setDuration(300);
                    view.startAnimation(scaleAnimation);
                }
                $r3.setAnimationListener(new C20411());
                $r8 = NearingDest.this;
                $r8.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });
        requestLayout();
    }

    public void onActivityResult(Activity activity, int $i0, int $i1, Intent $r2) throws  {
        if ($i0 == MainActivity.NAVBAR_ADD_PLACE_PHOTO_CODE && $i1 == -1) {
            QuestionData.ResetQuestionData(AppService.getAppContext());
            this.mSaveNav = true;
            if ($r2 != null && $r2.hasExtra("destination_venue_id")) {
                this.mVenueID = $r2.getStringExtra("destination_venue_id");
            }
            if (!TextUtils.isEmpty(this.mVenueID)) {
                this.dtnm.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
                this.nm.venueGet(this.mVenueID, 1);
            }
        }
    }
}
