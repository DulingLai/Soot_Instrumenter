package com.waze;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.Person;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.EndDriveListener;
import com.waze.navigate.social.EndDriveData;
import com.waze.phone.AddressBookImpl;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;
import com.waze.view.anim.EasingInterpolators;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.anim.WidthAnimation;
import com.waze.view.drawables.MultiContactsBitmap;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class FriendsBarFragment extends Fragment {
    public static final int DELAY_MILLIS = 5000;
    public static final double END_LOCATION_POSITION = 1.0d;
    public static final int ETA_CONSIDERED_ARRIVAL = 60;
    private static final double LOCATION_MARGIN = 0.13d;
    public static final int MARGIN_FOR_TOP_POSITION = 45;
    public static final double START_LOCATION_POSITION = 0.0d;
    private AtomicBoolean mAlreadyPopulatingFriends = new AtomicBoolean(false);
    private List<Integer> mArrivedFriends;
    private ImageView mArrivedImagePlaceHolder;
    private RelativeLayout mArrivedLayout = null;
    private int mBottomMargin;
    private ImageView mDriver = null;
    private Map<Integer, RelativeLayout> mDrivingFriends;
    RelativeLayout mEndDriveFriendsLayout;
    private FriendsPositions mFp = new FriendsPositions();
    private FrameLayout mFrameLayout;
    private int mFriendBubbleWidth = -1;
    private boolean mFriendsBarShown;
    private boolean mFriendsBarStartShown = false;
    private int mFriendsBarUsableMargin;
    private View mFriendsBarView;
    private LayoutInflater mInflater;
    private LayoutManager mLayoutManager;
    private MultiContactsBitmap mMcb = new MultiContactsBitmap(onArrivedPicDone());
    private int mTopMargin;
    private View mViewLayout;
    private int nMaxETA = 0;

    class C11321 implements OnClickListener {
        C11321() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_DRIVING_BAR_CLICKED);
            FriendsBarFragment.this.startActivity(new Intent(AppService.getAppContext(), FriendsActivity.class));
        }
    }

    class C11332 implements Runnable {
        C11332() throws  {
        }

        public void run() throws  {
            FriendsBarFragment.this.mFriendsBarView.setVisibility(8);
            FriendsBarFragment.this.mViewLayout.setVisibility(8);
        }
    }

    class C11353 implements EndDriveListener {
        C11353() throws  {
        }

        public void onComplete(EndDriveData $r1) throws  {
            int $i0 = AppService.getAppContext().getResources().getConfiguration().orientation;
            if ($r1.friends.length == 0 || $i0 == 2 || !FriendsBarFragment.this.mFriendsBarShown) {
                FriendsBarFragment.this.setFriendsBarVisibilty(false);
                FriendsBarFragment.this.mAlreadyPopulatingFriends.set(false);
                return;
            }
            if ($r1.friends.length == 0) {
                FriendsBarFragment.this.nMaxETA = 0;
            }
            if (FriendsBarFragment.this.nMaxETA == 0 || $r1.maxEtaSecondsBar > FriendsBarFragment.this.nMaxETA) {
                FriendsBarFragment.this.nMaxETA = $r1.maxEtaSecondsBar;
                Logger.m36d("FriendsBarFragment: max ETA=" + FriendsBarFragment.this.nMaxETA + "; driver eta=" + $r1.myEtaSeconds);
            }
            RelativeLayout $r10 = FriendsBarFragment.this.mEndDriveFriendsLayout;
            final EndDriveData endDriveData = $r1;
            $r10.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
                public void onGlobalLayout() throws  {
                    FriendsBarFragment.this.mEndDriveFriendsLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    FriendsBarFragment.this.mTopMargin = FriendsBarFragment.this.mFriendsBarView.getTop();
                    FriendsBarFragment.this.mBottomMargin = FriendsBarFragment.this.mFriendsBarView.getBottom();
                    FriendsBarFragment $r6 = FriendsBarFragment.this;
                    int $i2 = FriendsBarFragment.this.mBottomMargin - PixelMeasure.dp(44);
                    int i = $i2;
                    $r6.mBottomMargin = $i2;
                    $r6 = FriendsBarFragment.this;
                    $i2 = FriendsBarFragment.this.mTopMargin + PixelMeasure.dp(4);
                    i = $i2;
                    $r6.mTopMargin = $i2;
                    $r6 = FriendsBarFragment.this;
                    $i2 = FriendsBarFragment.this.mBottomMargin - FriendsBarFragment.this.mTopMargin;
                    i = $i2;
                    $r6.mFriendsBarUsableMargin = $i2;
                    if (FriendsBarFragment.this.mFriendBubbleWidth <= 0) {
                        FriendsBarFragment.this.mArrivedLayout.measure(0, 0);
                        FriendsBarFragment.this.mFriendBubbleWidth = FriendsBarFragment.this.mArrivedLayout.getMeasuredWidth();
                    }
                    boolean $z0 = DriveToNativeManager.getInstance().isDayMode();
                    FriendsPositions $r12 = FriendsBarFragment.this.mFp;
                    FriendUserData[] $r14 = endDriveData;
                    EndDriveData $r13 = $r14;
                    FriendUserData[] $r142 = $r14.friends;
                    i = FriendsBarFragment.this.nMaxETA;
                    $i2 = endDriveData;
                    $r13 = $i2;
                    double $d0 = $r12.getPositions($r142, i, $i2.myEtaSeconds);
                    ArrayList arrayList = new ArrayList();
                    arrayList = new ArrayList();
                    arrayList = new ArrayList();
                    HashSet hashSet;
                    if (FriendsBarFragment.this.mDrivingFriends.size() > 0) {
                        hashSet = new HashSet(FriendsBarFragment.this.mDrivingFriends.keySet());
                    } else {
                        hashSet = new HashSet();
                    }
                    $r14 = endDriveData;
                    $r13 = $r14;
                    $i2 = $r14.friends;
                    $r142 = $i2;
                    i = $i2.length;
                    for (int $i3 = 0; $i3 < i; $i3++) {
                        FriendUserData $r4 = $r142[$i3];
                        String $r18 = $r4.getImage();
                        String $r19 = $r4.getName();
                        if ($r4.mContactID != -1) {
                            Person $r21 = AddressBookImpl.getInstance().GetPersonFromID($r4.mContactID);
                            if ($r21 != null) {
                                String $r22 = $r21.getImage();
                                if (!($r22 == null || $r22.isEmpty())) {
                                    $r18 = $r22;
                                }
                                $r22 = $r21.getName();
                                if (!($r22 == null || $r22.isEmpty())) {
                                    $r19 = $r22;
                                }
                            }
                        }
                        $r19 = FriendsBarFragment.this.getAbbrevName($r19);
                        if ($r4.mEtaSeconds <= 60) {
                            arrayList.add($r19);
                            arrayList.add($r18);
                            $i2 = $r4.mId;
                            arrayList.add(Integer.valueOf($i2));
                            $r6 = FriendsBarFragment.this;
                            $i2 = $r4.mID;
                            $r6.removeFriend($i2);
                        } else {
                            FriendsBarFragment.this.addUserToTimeline($r4.mEtaSeconds, $r4.mEtaFraction, $r18, $r19, $r4.mContactID, false, $z0, $r4.mID);
                        }
                        $i2 = $r4.mId;
                        $r16.remove(Integer.valueOf($i2));
                    }
                    if (arrayList.size() > 0) {
                        if (!(arrayList.size() == FriendsBarFragment.this.mArrivedFriends.size() && arrayList.containsAll(FriendsBarFragment.this.mArrivedFriends))) {
                            FriendsBarFragment.this.handleArrived(arrayList, arrayList, $z0);
                            FriendsBarFragment.this.mArrivedFriends = arrayList;
                        }
                        FriendsBarFragment.this.mArrivedLayout.setVisibility(0);
                    } else {
                        FriendsBarFragment.this.mArrivedLayout.setVisibility(8);
                    }
                    for (Integer $r23 : $r16) {
                        FriendsBarFragment.this.removeFriend($r23.intValue());
                    }
                    $r6 = FriendsBarFragment.this;
                    $i2 = endDriveData;
                    $r13 = $i2;
                    i = $i2.myEtaSeconds;
                    String $r182 = endDriveData;
                    String $r132 = $r182;
                    $r6.addUserToTimeline(i, $d0, $r182.myPictureUrl, null, -1, true, $z0, -1);
                }
            });
            $r10 = FriendsBarFragment.this.mEndDriveFriendsLayout;
            $r10.requestLayout();
            $r10 = FriendsBarFragment.this.mEndDriveFriendsLayout;
            $r10.invalidate();
            if (FriendsBarFragment.this.mFriendsBarShown) {
                FriendsBarFragment.this.mViewLayout.setVisibility(0);
                FriendsBarFragment.this.mFriendsBarView.setVisibility(0);
                if (!FriendsBarFragment.this.mFriendsBarStartShown) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FRIENDS_DRIVING_BAR_SHOWN);
                    FriendsBarFragment.this.mFriendsBarStartShown = true;
                }
            }
            FriendsBarFragment.this.mAlreadyPopulatingFriends.set(false);
            FriendsBarFragment.this.mLayoutManager.updateControlsVisibilty();
        }
    }

    class C11385 extends ImageRepositoryListener {
        C11385() throws  {
        }

        public void onImageRetrieved(final Bitmap $r1) throws  {
            if (FriendsBarFragment.this.mArrivedLayout != null && $r1 != null) {
                final ImageView $r5 = (ImageView) FriendsBarFragment.this.mArrivedLayout.findViewById(C1283R.id.endDriveFriendImage);
                FriendsBarFragment.this.mArrivedLayout.post(new Runnable() {
                    public void run() throws  {
                        $r5.setImageDrawable(new CircleShaderDrawable($r1, 0));
                    }
                });
            }
        }
    }

    class C11396 implements OnClickListener {
        final /* synthetic */ int val$contactId;
        final /* synthetic */ RelativeLayout val$friendLayout;
        final /* synthetic */ String val$name;

        C11396(RelativeLayout $r2, String $r3, int $i0) throws  {
            this.val$friendLayout = $r2;
            this.val$name = $r3;
            this.val$contactId = $i0;
        }

        public void onClick(View v) throws  {
            FriendsBarFragment.this.openOpenedFriend(this.val$friendLayout, this.val$name, this.val$contactId, false);
        }
    }

    private static class FriendsPositions {
        private FriendsPositions() throws  {
        }

        public double getPositions(FriendUserData[] $r1, int $i0, int $i1) throws  {
            int $i2;
            double $d1;
            for ($i2 = 0; $i2 < $r1.length; $i2++) {
                if ($r1[$i2].mEtaSeconds > 60) {
                    int $i3 = $r1[$i2].mEtaSeconds - 60;
                    if ($i3 < 0) {
                        $i3 = 0;
                    }
                    $d1 = FriendsBarFragment.END_LOCATION_POSITION - (((double) $i3) / ((double) ($i0 - 60)));
                    $r1[$i2].mEtaFraction = getPosFrac($d1);
                } else {
                    $r1[$i2].mEtaFraction = -1.0d;
                }
            }
            Arrays.sort($r1);
            $d1 = -0.13d;
            for ($i2 = 0; $i2 < $r1.length; $i2++) {
                double $d2 = $r1[$i2].mEtaFraction;
                if ($d2 != -1.0d) {
                    double $d0 = $d2 + (((double) (($r1.length - $i2) - 1)) * FriendsBarFragment.LOCATION_MARGIN);
                    if ($d0 > FriendsBarFragment.END_LOCATION_POSITION) {
                        $d2 = ($d2 - $d0) + FriendsBarFragment.END_LOCATION_POSITION;
                    }
                    if ($d2 < FriendsBarFragment.LOCATION_MARGIN + $d1) {
                        $d2 = $d1 + FriendsBarFragment.LOCATION_MARGIN;
                    }
                    $r1[$i2].mEtaFraction = getPosFrac($d2);
                    $d1 = $r1[$i2].mEtaFraction;
                }
            }
            $i1 -= 60;
            if ($i1 < 0) {
                $i1 = 0;
            }
            return getPosFrac(FriendsBarFragment.END_LOCATION_POSITION - (((double) $i1) / ((double) ($i0 - 60))));
        }

        public double getPosFrac(double $d0) throws  {
            if ($d0 > FriendsBarFragment.END_LOCATION_POSITION) {
                return FriendsBarFragment.END_LOCATION_POSITION;
            }
            return $d0 < 0.0d ? 0.0d : $d0;
        }
    }

    private void addUserToTimeline(int r39, double r40, java.lang.String r42, java.lang.String r43, int r44, boolean r45, boolean r46, int r47) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:20:0x016a in {2, 11, 12, 14, 15, 19, 21} preds:[]
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
        r38 = this;
        r6 = new android.widget.RelativeLayout$LayoutParams;
        r7 = -2;
        r8 = -2;
        r6.<init>(r7, r8);
        r7 = 11;
        r6.addRule(r7);
        r10 = -4616189618054758400; // 0xbff0000000000000 float:0.0 double:-1.0;
        r9 = (r40 > r10 ? 1 : (r40 == r10 ? 0 : -1));
        if (r9 != 0) goto L_0x0016;
    L_0x0015:
        return;
    L_0x0016:
        r0 = r38;
        r12 = r0.mBottomMargin;
        r0 = r38;
        r13 = r0.mFriendsBarUsableMargin;
        r14 = (double) r13;
        r40 = r14 * r40;
        r0 = r40;
        r13 = (int) r0;
        r12 = r12 - r13;
        if (r45 != 0) goto L_0x015c;
    L_0x0027:
        r0 = r38;
        r0 = r0.mDrivingFriends;
        r16 = r0;
        r0 = r47;
        r17 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r17;
        r45 = r0.containsKey(r1);
        if (r45 != 0) goto L_0x012a;
    L_0x003d:
        r0 = r38;
        r0 = r0.mInflater;
        r18 = r0;
        r0 = r38;
        r0 = r0.mEndDriveFriendsLayout;
        r19 = r0;
        r7 = 2130903201; // 0x7f0300a1 float:1.7413213E38 double:1.052806066E-314;
        r8 = 0;
        r0 = r18;
        r1 = r19;
        r20 = r0.inflate(r7, r1, r8);
        r21 = r20;
        r21 = (android.widget.RelativeLayout) r21;
        r19 = r21;
        r0 = r38;
        r1 = r46;
        r2 = r19;
        r0.changeResourcesPerSkin(r1, r2);
        r7 = 20;
        r13 = com.waze.utils.PixelMeasure.dp(r7);
        r7 = 0;
        r8 = 0;
        r22 = 0;
        r0 = r22;
        r6.setMargins(r7, r8, r13, r0);
        r0 = r19;
        r0.setLayoutParams(r6);
        r0 = (float) r12;
        r23 = r0;
        r0 = r19;
        r1 = r23;
        r0.setTranslationY(r1);
        r7 = 2131690685; // 0x7f0f04bd float:1.901042E38 double:1.053195135E-314;
        r0 = r19;
        r20 = r0.findViewById(r7);
        r25 = r20;
        r25 = (android.widget.ImageView) r25;
        r24 = r25;
        r7 = 2131690687; // 0x7f0f04bf float:1.9010425E38 double:1.053195136E-314;
        r0 = r19;
        r20 = r0.findViewById(r7);
        r27 = r20;
        r27 = (android.widget.TextView) r27;
        r26 = r27;
        r0 = r43;
        r28 = com.waze.share.ShareUtility.getInitials(r0);
        r0 = r26;
        r1 = r28;
        r0.setText(r1);
        r7 = 0;
        r0 = r26;
        r0.setVisibility(r7);
        if (r42 == 0) goto L_0x00d4;
    L_0x00b5:
        r0 = r42;
        r45 = r0.isEmpty();
        if (r45 != 0) goto L_0x00d4;
    L_0x00bd:
        r29 = com.waze.utils.ImageRepository.instance;
        r30 = com.waze.AppService.getMainActivity();
        r7 = 3;
        r31 = 0;
        r0 = r29;
        r1 = r42;
        r2 = r7;
        r3 = r24;
        r4 = r31;
        r5 = r30;
        r0.getImage(r1, r2, r3, r4, r5);
    L_0x00d4:
        r7 = 0;
        r0 = r19;
        r0.setVisibility(r7);
        r17 = new java.lang.Integer;
        r0 = r17;
        r1 = r39;
        r0.<init>(r1);
        r0 = r19;
        r1 = r17;
        r0.setTag(r1);
        r32 = new com.waze.FriendsBarFragment$6;
        r0 = r32;
        r1 = r38;
        r2 = r19;
        r3 = r43;
        r4 = r44;
        r0.<init>(r2, r3, r4);
        r0 = r19;
        r1 = r32;
        r0.setOnClickListener(r1);
        r0 = r38;
        r0 = r0.mDrivingFriends;
        r16 = r0;
        r0 = r47;
        r17 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r17;
        r2 = r19;
        r0.put(r1, r2);
        r0 = r38;
        r0 = r0.mEndDriveFriendsLayout;
        r33 = r0;
        r1 = r19;
        r0.addView(r1);
    L_0x0120:
        r0 = r38;
        r0 = r0.mLayoutManager;
        r34 = r0;
        r0.updateControlsVisibilty();
        return;
    L_0x012a:
        r0 = r38;
        r0 = r0.mDrivingFriends;
        r16 = r0;
        r0 = r47;
        r17 = java.lang.Integer.valueOf(r0);
        r0 = r16;
        r1 = r17;
        r35 = r0.get(r1);
        r36 = r35;
        r36 = (android.widget.RelativeLayout) r36;
        r19 = r36;
        r17 = new java.lang.Integer;
        r0 = r17;
        r1 = r39;
        r0.<init>(r1);
        r0 = r19;
        r1 = r17;
        r0.setTag(r1);
        r0 = r38;
        r1 = r19;
        r0.animateMovement(r1, r12);
        goto L_0x0120;
    L_0x015c:
        r0 = r38;
        r0 = r0.mDriver;
        r24 = r0;
        if (r24 != 0) goto L_0x01b3;
    L_0x0164:
        r24 = new android.widget.ImageView;
        goto L_0x016e;
    L_0x0167:
        goto L_0x0120;
        goto L_0x016e;
    L_0x016b:
        goto L_0x0120;
    L_0x016e:
        r0 = r38;
        r37 = r0.getActivity();
        r0 = r24;
        r1 = r37;
        r0.<init>(r1);
        r0 = r24;
        r1 = r38;
        r1.mDriver = r0;
        r0 = r38;
        r0 = r0.mDriver;
        r24 = r0;
        r7 = 2130838152; // 0x7f020288 float:1.7281278E38 double:1.0527739278E-314;
        r0 = r24;
        r0.setImageResource(r7);
        r0 = r38;
        r0 = r0.mDriver;
        r24 = r0;
        r0 = (float) r12;
        r23 = r0;
        r0 = r24;
        r1 = r23;
        r0.setTranslationY(r1);
        r0 = r38;
        r0 = r0.mEndDriveFriendsLayout;
        r19 = r0;
        r0 = r38;
        r0 = r0.mDriver;
        r24 = r0;
        r0 = r19;
        r1 = r24;
        r0.addView(r1, r6);
        goto L_0x0167;
    L_0x01b3:
        r0 = r38;
        r0 = r0.mDriver;
        r24 = r0;
        r0 = r38;
        r1 = r24;
        r0.animateMovement(r1, r12);
        goto L_0x016b;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.FriendsBarFragment.addUserToTimeline(int, double, java.lang.String, java.lang.String, int, boolean, boolean, int):void");
    }

    public void setLayoutManager(LayoutManager $r1) throws  {
        this.mLayoutManager = $r1;
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle $r3) throws  {
        super.onCreateView($r1, $r2, $r3);
        this.mInflater = $r1;
        this.mFrameLayout = new FrameLayout(getActivity());
        this.mDrivingFriends = new HashMap();
        this.mArrivedFriends = new ArrayList();
        populateView($r1);
        return this.mFrameLayout;
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
        populateView(LayoutInflater.from(getActivity()));
    }

    private void populateView(LayoutInflater $r1) throws  {
        if (this.mFrameLayout != null) {
            this.mFrameLayout.removeAllViews();
            this.mDrivingFriends.clear();
            this.mArrivedFriends.clear();
        }
        this.mDriver = null;
        $r1.inflate(C1283R.layout.friends_bar, this.mFrameLayout, true);
        this.mViewLayout = this.mFrameLayout.findViewById(C1283R.id.friendsbar_layout);
        LayoutParams $r8 = (LayoutParams) this.mViewLayout.getLayoutParams();
        $r8.gravity = 85;
        $r8.rightMargin = 0;
        this.mViewLayout.setLayoutParams($r8);
        this.mFriendsBarView = this.mFrameLayout.findViewById(C1283R.id.friendsbar_main);
        this.mFriendsBarView.setOnClickListener(new C11321());
        this.mFriendsBarView.setVisibility(8);
        this.mViewLayout.setVisibility(8);
        this.mEndDriveFriendsLayout = (RelativeLayout) this.mFrameLayout.findViewById(C1283R.id.endDriveTimeLineFriends);
        this.mArrivedLayout = (RelativeLayout) this.mInflater.inflate(C1283R.layout.friends_bar_friend, this.mEndDriveFriendsLayout, false);
        ViewGroup.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(11);
        this.mArrivedImagePlaceHolder = (ImageView) this.mArrivedLayout.findViewById(C1283R.id.endDriveFriendImage);
        this.mArrivedImagePlaceHolder.setImageDrawable(new CircleShaderDrawable(BitmapFactory.decodeResource(AppService.getAppContext().getResources(), C1283R.drawable.friends_bar_arrived_placeholder), 0));
        layoutParams.setMargins(0, PixelMeasure.dp(45), PixelMeasure.dp(20), 0);
        this.mArrivedLayout.setLayoutParams(layoutParams);
        this.mArrivedLayout.findViewById(C1283R.id.endDriveFriendInitials).setVisibility(0);
        RelativeLayout $r10 = this.mEndDriveFriendsLayout;
        RelativeLayout $r16 = this.mArrivedLayout;
        $r10.addView($r16);
        this.mArrivedLayout.setVisibility(8);
        if (AppService.getAppContext().getResources().getConfiguration().orientation == 2) {
            this.mFriendsBarShown = false;
            this.mFriendsBarStartShown = false;
        } else if (this.mDriver != null) {
            this.mDriver.setVisibility(0);
        }
    }

    public void disappearAllVisibilty() throws  {
        this.mFriendsBarView.setVisibility(8);
        this.mViewLayout.setVisibility(8);
        this.mFriendsBarShown = false;
        this.mFriendsBarStartShown = false;
        this.mViewLayout.setVisibility(8);
        this.mFriendsBarStartShown = false;
        this.mLayoutManager.updateControlsVisibilty();
    }

    public void setFriendsBarVisibilty(boolean bIsVisible) throws  {
        this.mFriendsBarShown = true;
        if (this.mFriendsBarShown) {
            this.mFriendsBarShown = true;
        } else {
            this.mFriendsBarShown = true;
            if (DriveToNativeManager.getInstance().isDayMode()) {
                ((ImageView) this.mFriendsBarView).setImageResource(C1283R.drawable.friends_bar);
            } else {
                ((ImageView) this.mFriendsBarView).setImageResource(C1283R.drawable.friends_bar_night);
            }
            this.mViewLayout.setAlpha(0.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mViewLayout).alpha(1.0f).setListener(null).setDuration(100);
        }
        this.mLayoutManager.updateControlsVisibilty();
    }

    public boolean isFriendsBarVisible() throws  {
        return this.mFriendsBarShown && this.mViewLayout.getVisibility() == 0;
    }

    public void populateViewWithFriends(int $i0, int nFriendsPending) throws  {
        if (this.mAlreadyPopulatingFriends.compareAndSet(false, true)) {
            if (!NativeManager.getInstance().isNavigatingNTV()) {
                this.nMaxETA = 0;
            }
            if ($i0 > 0) {
                DriveToNativeManager.getInstance().getEndDriveData(populateDrivers());
                return;
            }
            setFriendsBarVisibilty(false);
            this.mAlreadyPopulatingFriends.set(false);
        }
    }

    @NonNull
    private EndDriveListener populateDrivers() throws  {
        return new C11353();
    }

    private void removeFriend(int $i0) throws  {
        if (this.mDrivingFriends.containsKey(Integer.valueOf($i0))) {
            RelativeLayout $r4 = (RelativeLayout) this.mDrivingFriends.get(Integer.valueOf($i0));
            this.mDrivingFriends.remove(Integer.valueOf($i0));
            $r4.setVisibility(8);
            this.mEndDriveFriendsLayout.removeView($r4);
        }
    }

    private String getAbbrevName(String $r1) throws  {
        if ($r1 == null) {
            return "";
        }
        int $i1 = $r1.indexOf(32);
        if ($i1 <= 0) {
            return $r1;
        }
        int $i0 = $i1 + 2;
        if ($i0 > $r1.length()) {
            return $r1.substring(0, $i1);
        }
        return $r1.substring(0, $i0) + FileUploadSession.SEPARATOR;
    }

    private void handleArrived(@Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;Z)V"}) ArrayList<String> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;Z)V"}) ArrayList<String> $r2, @Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;Z)V"}) boolean $z0) throws  {
        String $r13;
        changeResourcesPerSkin($z0, this.mArrivedLayout);
        this.mArrivedImagePlaceHolder.setImageDrawable(new CircleShaderDrawable(BitmapFactory.decodeResource(AppService.getAppContext().getResources(), C1283R.drawable.friends_bar_arrived_placeholder), 0));
        this.mArrivedImagePlaceHolder.setVisibility(0);
        this.mMcb.buildBitmap($r2);
        StringBuilder $r3 = new StringBuilder();
        Iterator $r11 = $r1.iterator();
        while ($r11.hasNext()) {
            $r13 = (String) $r11.next();
            int $i0 = $r13.indexOf(32);
            if ($i0 > 0) {
                $r3.append($r13.substring(0, $i0)).append(",");
            } else {
                $r3.append($r13).append(",");
            }
        }
        $r3.setLength($r3.length() - 1);
        $r13 = $r3.toString();
        this.mArrivedLayout.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                FriendsBarFragment.this.openOpenedFriend(FriendsBarFragment.this.mArrivedLayout, $r13, -1, true);
            }
        });
        this.mArrivedLayout.setVisibility(0);
    }

    @NonNull
    private ImageRepositoryListener onArrivedPicDone() throws  {
        return new C11385();
    }

    private void animateMovement(View $r1, int $i0) throws  {
        ViewPropertyAnimatorHelper.initAnimation($r1).translationY((float) $i0);
    }

    private void openOpenedFriend(RelativeLayout $r1, String $r2, int $i0, boolean $z0) throws  {
        $r1.setOnClickListener(null);
        TextView $r6 = (TextView) $r1.findViewById(C1283R.id.endDriveFriendTitle);
        TextView $r7 = (TextView) $r1.findViewById(C1283R.id.endDriveFriendContent);
        if ($z0) {
            $r6.setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_ARRIVED));
            $r7.setText($r2);
        } else {
            Integer $r13 = (Integer) $r1.getTag();
            $r6.setText($r2);
            $r7.setText(String.format("%d %s", new Object[]{Integer.valueOf($r13.intValue() / 60), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MIN)}));
        }
        $r6.measure(0, 0);
        int $i2 = $r6.getMeasuredWidth();
        int $i1 = $i2;
        $r7.measure(0, 0);
        if ($r7.getMeasuredWidth() > $i2) {
            $i1 = $r7.getMeasuredWidth();
        }
        WidthAnimation $r4 = r0;
        WidthAnimation widthAnimation = new WidthAnimation($r1, this.mFriendBubbleWidth, (this.mFriendBubbleWidth + $i1) + PixelMeasure.dp(4));
        C11407 $r3 = r0;
        final RelativeLayout relativeLayout = $r1;
        final String str = $r2;
        final int i = $i0;
        final boolean z = $z0;
        C11407 c11407 = new Runnable() {
            public void run() throws  {
                FriendsBarFragment.this.closeOpenedFriend(relativeLayout, str, i, z, null);
            }
        };
        final TextView textView = $r6;
        final TextView textView2 = $r7;
        final RelativeLayout relativeLayout2 = $r1;
        final String str2 = $r2;
        final int i2 = $i0;
        final boolean z2 = $z0;
        final C11407 c114072 = $r3;
        $r4.setAnimationListener(new AnimationEndListener() {

            class C11411 implements OnClickListener {
                C11411() throws  {
                }

                public void onClick(View v) throws  {
                    FriendsBarFragment.this.closeOpenedFriend(relativeLayout2, str2, i2, z2, c114072);
                }
            }

            public void onAnimationEnd(Animation animation) throws  {
                textView.setVisibility(0);
                textView2.setVisibility(0);
                relativeLayout2.setOnClickListener(new C11411());
            }
        });
        $r4.setDuration(200);
        $r4.setInterpolator(EasingInterpolators.BOUNCE_OUT);
        $r1.startAnimation($r4);
        $r1.postDelayed($r3, 5000);
    }

    private void closeOpenedFriend(RelativeLayout $r1, String $r2, int $i0, boolean $z0, Runnable $r3) throws  {
        $r1.setOnClickListener(null);
        if ($r3 != null) {
            $r1.removeCallbacks($r3);
        }
        $r1.measure(0, 0);
        WidthAnimation $r4 = r9;
        WidthAnimation r9 = new WidthAnimation($r1, $r1.getMeasuredWidth(), this.mFriendBubbleWidth);
        final RelativeLayout relativeLayout = $r1;
        final String str = $r2;
        final int i = $i0;
        final boolean z = $z0;
        $r4.setAnimationListener(new AnimationEndListener() {

            class C11431 implements OnClickListener {
                C11431() throws  {
                }

                public void onClick(View v) throws  {
                    FriendsBarFragment.this.openOpenedFriend(relativeLayout, str, i, z);
                }
            }

            public void onAnimationEnd(Animation animation) throws  {
                relativeLayout.setOnClickListener(new C11431());
            }
        });
        $r4.setDuration(200);
        $r4.setInterpolator(EasingInterpolators.BOUNCE_IN);
        ((TextView) $r1.findViewById(C1283R.id.endDriveFriendTitle)).setVisibility(8);
        ((TextView) $r1.findViewById(C1283R.id.endDriveFriendContent)).setVisibility(8);
        $r1.startAnimation($r4);
    }

    @NonNull
    private void getDebugEndDriveData(EndDriveListener $r1) throws  {
        EndDriveData $r2 = new EndDriveData();
        $r2.maxEtaSeconds = 1000;
        $r2.myEtaSeconds = DisplayStrings.DS_RED_LIGHT_CAM;
        $r2.friends = new FriendUserData[7];
        $r2.friends[0] = new FriendUserData();
        $r2.friends[0].mContactID = 3831;
        $r2.friends[0].mEtaSeconds = 70;
        $r2.friends[1] = new FriendUserData();
        $r2.friends[1].mContactID = 5556;
        $r2.friends[1].mEtaSeconds = 30;
        $r2.friends[2] = new FriendUserData();
        $r2.friends[2].mContactID = 8045;
        $r2.friends[2].mEtaSeconds = 61;
        $r2.friends[3] = new FriendUserData();
        $r2.friends[3].mContactID = 6916;
        $r2.friends[3].mEtaSeconds = 61;
        $r2.friends[4] = new FriendUserData();
        $r2.friends[4].mContactID = DisplayStrings.DS_Z_SPEED_SENT_DRIVE_TEXT;
        $r2.friends[4].mEtaSeconds = DisplayStrings.DS_RED_LIGHT_CAM;
        $r2.friends[5] = new FriendUserData();
        $r2.friends[5].mContactID = 4072;
        $r2.friends[5].mEtaSeconds = DisplayStrings.DS_EVENT;
        $r2.friends[6] = new FriendUserData();
        $r2.friends[6].mContactID = 4646;
        $r2.friends[6].mEtaSeconds = 30;
        $r1.onComplete($r2);
    }

    public void setNoFriends() throws  {
        this.mViewLayout.setVisibility(8);
    }

    public void setDayMode(boolean $z0) throws  {
        for (View changeResourcesPerSkin : this.mDrivingFriends.values()) {
            changeResourcesPerSkin($z0, changeResourcesPerSkin);
        }
        if (this.mArrivedLayout != null) {
            changeResourcesPerSkin($z0, this.mArrivedLayout);
        }
        if ($z0) {
            ((ImageView) this.mFriendsBarView).setImageResource(C1283R.drawable.friends_bar);
        } else {
            ((ImageView) this.mFriendsBarView).setImageResource(C1283R.drawable.friends_bar_night);
        }
    }

    private void changeResourcesPerSkin(boolean $z0, View $r1) throws  {
        if ($z0) {
            $r1.setBackgroundResource(C1283R.drawable.friend_pin);
            ((TextView) $r1.findViewById(C1283R.id.endDriveFriendTitle)).setTextColor(-10263709);
            ((TextView) $r1.findViewById(C1283R.id.endDriveFriendContent)).setTextColor(-7105645);
            return;
        }
        $r1.setBackgroundResource(C1283R.drawable.friend_pin_night);
        ((TextView) $r1.findViewById(C1283R.id.endDriveFriendTitle)).setTextColor(-1);
        ((TextView) $r1.findViewById(C1283R.id.endDriveFriendContent)).setTextColor(-7685425);
    }
}
