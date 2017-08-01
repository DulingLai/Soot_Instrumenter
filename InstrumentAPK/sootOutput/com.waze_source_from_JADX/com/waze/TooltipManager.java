package com.waze;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolRideDetailsActivity;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.ifs.ui.LayoutTooltip;
import com.waze.ifs.ui.UserTooltipView;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.utils.DisplayUtils;
import com.waze.utils.DisplayUtils.OnOrientationReallyChanged;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.popups.CarpoolStripNotification;
import com.waze.view.popups.ShareStatusPopUp;
import java.util.ArrayList;

public class TooltipManager {
    private static final int TOOLTIP_AUTO_CLOSE_TIMEOUT = 30000;
    private boolean isTooltipCarpoolPromoTipShown;
    private boolean isTooltipCarpoolStripShown;
    private boolean isTooltipFriendsShown;
    private boolean isTooltipMainMenuShown;
    private boolean isTooltipRidewithRequestsShownLarge;
    private boolean isTooltipRidewithRequestsShownSmall;
    private boolean isTooltipRoadClosureShown;
    private boolean isTooltipShareShown;
    private boolean isTooltipUpcomingCarpoolShown;
    private CarpoolStripNotification mCarpoolTicker;
    Runnable mCloseTooltipRunnable = null;
    LayoutTooltip mCurLayoutTooltip;
    private int mCustomTimeout = 0;
    boolean mIsToolTipDisplayed = false;
    ArrayList<QueuedTooltip> mToolTipQueue = new ArrayList(4);
    private UserTooltipView mTooltipCarpoolPromo;
    private LayoutTooltip mTooltipFriends;
    private LayoutTooltip mTooltipMainMenu;
    private UserTooltipView mTooltipRidewithRequests;
    private LayoutTooltip mTooltipRoadClosure;
    private ShareStatusPopUp mTooltipShare;
    private UserTooltipView mTooltipUpcomingRide;
    final LayoutManager myLayoutManager;

    class C13223 implements Runnable {
        C13223() throws  {
        }

        public void run() throws  {
            TooltipManager.this.myLayoutManager.openRightSide();
        }
    }

    class C13288 implements Runnable {
        C13288() throws  {
        }

        public void run() throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_TOOL_TIP_CLICKED);
            TooltipManager.this.myLayoutManager.openRightSide();
            TooltipManager.this.closeTooltip(true, 8);
        }
    }

    class C13299 implements Runnable {
        C13299() throws  {
        }

        public void run() throws  {
            TooltipManager.this.closeTooltip(true, 8);
        }
    }

    class QueuedTooltip {
        final long cb;
        final int intParam;
        final int numUsers;
        final int type;
        final FriendUserData userData;

        public QueuedTooltip(int $i0, int $i1, FriendUserData $r2, long $l2, int $i3) throws  {
            this.type = $i0;
            this.numUsers = $i1;
            this.userData = $r2;
            this.cb = $l2;
            this.intParam = $i3;
        }
    }

    private boolean isAnyTouchClosesToolTip(int $i0) throws  {
        return $i0 == 0 || $i0 == 6;
    }

    private boolean isAutoCloseToolTip(int $i0) throws  {
        return $i0 != 1 ? $i0 != 5 ? $i0 != 3 ? $i0 != 4 ? $i0 != 8 ? $i0 != 9 ? $i0 != 2 ? $i0 != 0 ? $i0 != 10 ? $i0 == 11 : true : true : true : true : true : true : true : true : true;
    }

    public TooltipManager(LayoutManager $r1) throws  {
        this.myLayoutManager = $r1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void showToolTip(int r45, int r46, com.waze.user.FriendUserData r47, long r48, int r50) throws  {
        /*
        r44 = this;
        r9 = 0;
        r0 = r44;
        r1 = r45;
        r10 = r0.isAutoCloseToolTip(r1);
        if (r10 == 0) goto L_0x002b;
    L_0x000b:
        r0 = r44;
        r10 = r0.mIsToolTipDisplayed;
        if (r10 == 0) goto L_0x002b;
    L_0x0011:
        r0 = r44;
        r11 = r0.mToolTipQueue;
        r12 = new com.waze.TooltipManager$QueuedTooltip;
        r0 = r12;
        r1 = r44;
        r2 = r45;
        r3 = r46;
        r4 = r47;
        r5 = r48;
        r7 = r50;
        r0.<init>(r2, r3, r4, r5, r7);
        r11.add(r12);
    L_0x002a:
        return;
    L_0x002b:
        r13 = com.waze.NativeManager.getInstance();
        r0 = r44;
        r14 = r0.myLayoutManager;
        if (r14 == 0) goto L_0x003c;
    L_0x0035:
        r0 = r44;
        r14 = r0.myLayoutManager;
        r14.hideSpotifyButton();
    L_0x003c:
        r16 = 0;
        r15 = (r48 > r16 ? 1 : (r48 == r16 ? 0 : -1));
        if (r15 == 0) goto L_0x0049;
    L_0x0042:
        r0 = r48;
        r2 = r50;
        r13.callCallbackInt(r0, r2);
    L_0x0049:
        switch(r45) {
            case 0: goto L_0x004e;
            case 1: goto L_0x0190;
            case 2: goto L_0x0190;
            case 3: goto L_0x0190;
            case 4: goto L_0x0190;
            case 5: goto L_0x00d4;
            case 6: goto L_0x002a;
            case 7: goto L_0x004d;
            case 8: goto L_0x012c;
            case 9: goto L_0x015f;
            case 10: goto L_0x02a5;
            case 11: goto L_0x01a4;
            default: goto L_0x004c;
        };
    L_0x004c:
        goto L_0x004d;
    L_0x004d:
        return;
    L_0x004e:
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.isTooltipMainMenuShown = r0;
        r19 = new com.waze.ifs.ui.LayoutTooltip;
        r20 = com.waze.AppService.getMainActivity();
        r18 = 2130903478; // 0x7f0301b6 float:1.7413775E38 double:1.052806203E-314;
        r21 = 0;
        r0 = r19;
        r1 = r20;
        r2 = r18;
        r3 = r21;
        r0.<init>(r1, r2, r3);
        r0 = r19;
        r1 = r44;
        r1.mCurLayoutTooltip = r0;
        r0 = r44;
        r0 = r0.mCurLayoutTooltip;
        r19 = r0;
        r0 = r44;
        r14 = r0.myLayoutManager;
        r22 = r14.getMainLayout();
        r18 = 2131691019; // 0x7f0f060b float:1.9011098E38 double:1.0531953E-314;
        r0 = r22;
        r1 = r18;
        r23 = r0.findViewById(r1);
        r0 = r19;
        r1 = r23;
        r0.setAnchor(r1);
        r0 = r44;
        r0 = r0.mCurLayoutTooltip;
        r19 = r0;
        r1 = r44;
        r1.mTooltipMainMenu = r0;
        r18 = 1323; // 0x52b float:1.854E-42 double:6.536E-321;
        r0 = r18;
        r24 = r13.getLanguageString(r0);
        r9 = 2130839394; // 0x7f020762 float:1.7283797E38 double:1.0527745414E-314;
    L_0x00a7:
        r0 = r44;
        r0 = r0.mCurLayoutTooltip;
        r19 = r0;
        r23 = r0.getView();
        r26 = r23;
        r26 = (android.widget.LinearLayout) r26;
        r25 = r26;
        r18 = 0;
        r0 = r44;
        r1 = r25;
        r2 = r24;
        r3 = r18;
        r0.setTooltipShowEvents(r1, r2, r9, r3);
        r0 = r44;
        r0 = r0.mCurLayoutTooltip;
        r19 = r0;
        r0 = r44;
        r1 = r45;
        r2 = r19;
        r0.setCloseTooltipEvents(r1, r2);
        return;
    L_0x00d4:
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.isTooltipRoadClosureShown = r0;
        r19 = new com.waze.ifs.ui.LayoutTooltip;
        r20 = com.waze.AppService.getMainActivity();
        r18 = 2130903479; // 0x7f0301b7 float:1.7413777E38 double:1.0528062036E-314;
        r21 = 0;
        r0 = r19;
        r1 = r20;
        r2 = r18;
        r3 = r21;
        r0.<init>(r1, r2, r3);
        r0 = r19;
        r1 = r44;
        r1.mCurLayoutTooltip = r0;
        r0 = r44;
        r0 = r0.mCurLayoutTooltip;
        r19 = r0;
        r0 = r44;
        r14 = r0.myLayoutManager;
        r22 = r14.getMainLayout();
        r18 = 2131690992; // 0x7f0f05f0 float:1.9011043E38 double:1.0531952867E-314;
        r0 = r22;
        r1 = r18;
        r23 = r0.findViewById(r1);
        r0 = r19;
        r1 = r23;
        r0.setAnchor(r1);
        r0 = r44;
        r0 = r0.mCurLayoutTooltip;
        r19 = r0;
        r1 = r44;
        r1.mTooltipRoadClosure = r0;
        r18 = 1327; // 0x52f float:1.86E-42 double:6.556E-321;
        r0 = r18;
        r24 = r13.getLanguageString(r0);
        goto L_0x00a7;
    L_0x012c:
        r0 = r44;
        r14 = r0.myLayoutManager;
        r10 = r14.isAnyMenuOpen();
        if (r10 != 0) goto L_0x0341;
    L_0x0136:
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.mIsToolTipDisplayed = r0;
        if (r50 <= 0) goto L_0x0155;
    L_0x0140:
        r27 = new com.waze.TooltipManager$1;
        r0 = r27;
        r1 = r44;
        r2 = r45;
        r3 = r46;
        r4 = r50;
        r0.<init>(r2, r3, r4);
        r0 = r27;
        com.waze.NativeManager.Post(r0);
        return;
    L_0x0155:
        r0 = r44;
        r1 = r46;
        r2 = r50;
        r0.showCarpoolSmallTip(r1, r2);
        return;
    L_0x015f:
        r0 = r44;
        r14 = r0.myLayoutManager;
        r10 = r14.isAnyMenuOpen();
        if (r10 != 0) goto L_0x0342;
    L_0x0169:
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.isTooltipUpcomingCarpoolShown = r0;
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.mIsToolTipDisplayed = r0;
        r28 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r29 = new com.waze.TooltipManager$2;
        r0 = r29;
        r1 = r44;
        r2 = r45;
        r0.<init>(r2);
        r0 = r28;
        r1 = r29;
        r0.getUpcomingDrive(r1);
        return;
    L_0x0190:
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.isTooltipShareShown = r0;
        r0 = r44;
        r1 = r45;
        r2 = r46;
        r3 = r47;
        r0.showFollowingToolTip(r1, r2, r3);
        return;
    L_0x01a4:
        r0 = r44;
        r14 = r0.myLayoutManager;
        r10 = r14.isAnyMenuOpen();
        if (r10 != 0) goto L_0x0343;
    L_0x01ae:
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.isTooltipCarpoolPromoTipShown = r0;
        r20 = com.waze.AppService.getMainActivity();
        r0 = r44;
        r14 = r0.myLayoutManager;
        r22 = r14.getMainLayout();
        r18 = 2131691023; // 0x7f0f060f float:1.9011106E38 double:1.053195302E-314;
        r0 = r22;
        r1 = r18;
        r23 = r0.findViewById(r1);
        r18 = 3484; // 0xd9c float:4.882E-42 double:1.7213E-320;
        r0 = r18;
        r24 = com.waze.strings.DisplayStrings.displayString(r0);
        r18 = 3485; // 0xd9d float:4.884E-42 double:1.722E-320;
        r0 = r18;
        r30 = com.waze.strings.DisplayStrings.displayString(r0);
        r13 = com.waze.NativeManager.getInstance();
        r31 = r13.getLocale();
        r18 = 2;
        r0 = r18;
        r0 = new java.lang.Object[r0];
        r32 = r0;
        r18 = 0;
        r32[r18] = r24;
        r18 = 1;
        r32[r18] = r30;
        r33 = "<b>%s</b><br/>%s";
        r0 = r31;
        r1 = r33;
        r2 = r32;
        r24 = java.lang.String.format(r0, r1, r2);
        r18 = 0;
        r16 = 0;
        r33 = "CARPOOL_PROMO";
        r21 = 0;
        r0 = r20;
        r1 = r23;
        r2 = r18;
        r3 = r24;
        r4 = r16;
        r6 = r33;
        r7 = r21;
        r34 = com.waze.ifs.ui.UserTooltipView.showUserTooltip(r0, r1, r2, r3, r4, r6, r7);
        r0 = r34;
        r1 = r44;
        r1.mTooltipCarpoolPromo = r0;
        r0 = r44;
        r0 = r0.mTooltipCarpoolPromo;
        r34 = r0;
        if (r34 == 0) goto L_0x0344;
    L_0x0229:
        r0 = r44;
        r0 = r0.mTooltipCarpoolPromo;
        r34 = r0;
        r0.setCarpoolStyle();
        r0 = r44;
        r0 = r0.mTooltipCarpoolPromo;
        r34 = r0;
        r35 = r0.getTextView();
        r18 = 2130837923; // 0x7f0201a3 float:1.7280814E38 double:1.0527738146E-314;
        r21 = 0;
        r36 = 0;
        r37 = 0;
        r0 = r35;
        r1 = r18;
        r2 = r21;
        r3 = r36;
        r4 = r37;
        r0.setCompoundDrawablesWithIntrinsicBounds(r1, r2, r3, r4);
        r0 = r44;
        r0 = r0.mTooltipCarpoolPromo;
        r34 = r0;
        r35 = r0.getTextView();
        r18 = 12;
        r0 = r18;
        r46 = com.waze.utils.PixelMeasure.dp(r0);
        r0 = r35;
        r1 = r46;
        r0.setCompoundDrawablePadding(r1);
        r0 = r44;
        r0 = r0.mTooltipCarpoolPromo;
        r34 = r0;
        r38 = new com.waze.TooltipManager$3;
        r0 = r38;
        r1 = r44;
        r0.<init>();
        r0 = r34;
        r1 = r38;
        r0.setOnClick(r1);
        r0 = r44;
        r0 = r0.mTooltipCarpoolPromo;
        r34 = r0;
        r39 = new com.waze.TooltipManager$4;
        r0 = r39;
        r1 = r44;
        r2 = r45;
        r0.<init>(r2);
        r0 = r34;
        r1 = r39;
        r0.setOnClose(r1);
        r40 = 0;
        r0 = r44;
        r1 = r45;
        r2 = r40;
        r0.setCloseTooltipEvents(r1, r2);
        return;
    L_0x02a5:
        r20 = com.waze.AppService.getMainActivity();
        r18 = 1;
        r0 = r18;
        r1 = r44;
        r1.isTooltipCarpoolStripShown = r0;
        r41 = new com.waze.view.popups.CarpoolStripNotification;
        r0 = r44;
        r14 = r0.myLayoutManager;
        r0 = r41;
        r1 = r20;
        r0.<init>(r1, r14);
        r0 = r41;
        r1 = r44;
        r1.mCarpoolTicker = r0;
        r18 = 3482; // 0xd9a float:4.88E-42 double:1.7203E-320;
        r0 = r18;
        r24 = com.waze.strings.DisplayStrings.displayString(r0);
        r18 = 3483; // 0xd9b float:4.881E-42 double:1.721E-320;
        r0 = r18;
        r30 = com.waze.strings.DisplayStrings.displayString(r0);
        r18 = 50;
        r0 = r18;
        r46 = com.waze.config.ConfigValues.getIntValue(r0);
        r0 = r44;
        r0 = r0.mCarpoolTicker;
        r41 = r0;
        r42 = new com.waze.TooltipManager$5;
        r0 = r42;
        r1 = r44;
        r2 = r45;
        r0.<init>(r2);
        r43 = new com.waze.TooltipManager$6;
        r0 = r43;
        r1 = r44;
        r2 = r45;
        r0.<init>(r2);
        r33 = "";
        r18 = 1;
        r21 = 1;
        r0 = r41;
        r1 = r24;
        r2 = r30;
        r3 = r33;
        r4 = r46;
        r5 = r42;
        r6 = r43;
        r7 = r18;
        r8 = r21;
        r0.show(r1, r2, r3, r4, r5, r6, r7, r8);
        r0 = r44;
        r0 = r0.mCarpoolTicker;
        r41 = r0;
        r18 = 2130837911; // 0x7f020197 float:1.728079E38 double:1.0527738087E-314;
        r0 = r41;
        r1 = r18;
        r0.setIcon(r1);
        r0 = r44;
        r0 = r0.mCarpoolTicker;
        r41 = r0;
        r18 = 0;
        r0 = r41;
        r1 = r18;
        r0.setFrameVisible(r1);
        r33 = "RW_PROMO_STRIP_SHOWN";
        r0 = r33;
        com.waze.analytics.Analytics.log(r0);
        r0 = r44;
        r14 = r0.myLayoutManager;
        r14.setPopupShown();
        return;
    L_0x0341:
        return;
    L_0x0342:
        return;
    L_0x0343:
        return;
    L_0x0344:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.TooltipManager.showToolTip(int, int, com.waze.user.FriendUserData, long, int):void");
    }

    private void getUserImageAndShowLargeTip(CarpoolDrive $r1) throws  {
        final CarpoolDrive carpoolDrive = $r1;
        VolleyManager.getInstance().loadImageFromUrl($r1.getRider().getImage(), new ImageRequestListener() {
            boolean didFail = false;
            boolean didSucceed = false;

            class C13261 implements Runnable {
                C13261() throws  {
                }

                public void run() throws  {
                    if (!C13277.this.didSucceed) {
                        C13277.this.didFail = true;
                        TooltipManager.this.showCarpoolLargeTip(carpoolDrive, null);
                    }
                }
            }

            public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                if (!this.didFail) {
                    this.didSucceed = true;
                    TooltipManager.this.showCarpoolLargeTip(carpoolDrive, new CircleFrameDrawable($r1, 0, 3));
                }
            }

            public void onImageLoadFailed(Object token, long duration) throws  {
                AppService.Post(new C13261(), 1000);
            }
        }, null, PixelMeasure.dp(56), PixelMeasure.dp(56), null);
    }

    private void showCarpoolSmallTip(int $i0, int $i1) throws  {
        UserTooltipView $r12;
        UserTooltipView $r122;
        String str = "MESSAGES";
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_TOOL_TIP_SHOWN).addParam("RIDES", "" + $i1).addParam(str, "" + $i0).send();
        this.isTooltipRidewithRequestsShownSmall = true;
        Activity $r5 = AppService.getMainActivity();
        View $r8 = this.myLayoutManager.getMainLayout().findViewById(C1283R.id.mainBottomBarRight);
        TextView $r13;
        if ($i0 <= 0 || $i1 <= 0) {
            if ($i0 > 0) {
                this.mTooltipRidewithRequests = UserTooltipView.showUserTooltip($r5, $r8, 0, DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MESSAGES_TIP_PD, new Object[]{Integer.valueOf($i0)}), 0, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_TYPE_PENDING_RIDES, false);
                if (this.mTooltipRidewithRequests != null) {
                    $r12 = this.mTooltipRidewithRequests;
                    $r13 = $r12.getTextView();
                    $r13.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.message_icon_white, 0, 0, 0);
                    $r13.setPadding(PixelMeasure.dp(4), 0, 0, 0);
                    $r13.setCompoundDrawablePadding(PixelMeasure.dp(12));
                } else {
                    return;
                }
            }
            this.mTooltipRidewithRequests = UserTooltipView.showUserTooltip($r5, $r8, 0, DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_REQUESTS_TIP_PD, new Object[]{Integer.valueOf($i1)}), 0, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_TYPE_PENDING_RIDES, false);
            if (this.mTooltipRidewithRequests != null) {
                $r12 = this.mTooltipRidewithRequests;
                $r13 = $r12.getTextView();
                $r13.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.carpool_icon_white, 0, 0, 0);
                $r13.setPadding(PixelMeasure.dp(4), 0, 0, 0);
                $r13.setCompoundDrawablePadding(PixelMeasure.dp(12));
            } else {
                return;
            }
            $r12 = this.mTooltipRidewithRequests;
            $r12.removeCloseButton();
        } else {
            String $r4 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MESSAGES_TIP_PD, new Object[]{Integer.valueOf($i0)});
            String $r11 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_REQUESTS_TIP_PD, new Object[]{Integer.valueOf($i1)});
            this.mTooltipRidewithRequests = UserTooltipView.showUserTooltip($r5, $r8, 0, $r4, 0, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_TYPE_PENDING_RIDES, false);
            if (this.mTooltipRidewithRequests != null) {
                $r12 = this.mTooltipRidewithRequests;
                $r122 = $r12;
                $r13 = $r12.rightSideText($r11);
                $r13.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.carpool_icon_white, 0, 0, 0);
                $r13.setCompoundDrawablePadding(PixelMeasure.dp(12));
                $r12 = this.mTooltipRidewithRequests;
                $r13 = $r12.getTextView();
                $r13.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.message_icon_white, 0, 0, 0);
                $r13.setPadding(PixelMeasure.dp(4), 0, 0, 0);
                $r13.setCompoundDrawablePadding(PixelMeasure.dp(12));
            } else {
                return;
            }
        }
        $r12 = this.mTooltipRidewithRequests;
        $r12.setCarpoolStyle();
        this.mCustomTimeout = 3000;
        C13288 c13288 = new C13288();
        $r12 = this.mTooltipRidewithRequests;
        $r122 = $r12;
        $r12.setOnClick(c13288);
        $r12 = this.mTooltipRidewithRequests;
        $r122 = $r12;
        $r12.setOnCloseButton(c13288);
        this.mTooltipRidewithRequests.setOnClose(new C13299());
        setCloseTooltipEvents(8, null);
    }

    private void showCarpoolLargeTip(CarpoolDrive $r1, Drawable $r2) throws  {
        this.isTooltipRidewithRequestsShownLarge = true;
        MainActivity $r4 = AppService.getMainActivity();
        View $r7 = this.myLayoutManager.getMainLayout().findViewById(C1283R.id.mainBottomBarRight);
        String $r10 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_REQUEST_TIP_TITLE_PS, new Object[]{$r1.getRider().getFirstName()});
        long $l0 = $r1.getTime() * 1000;
        long j = $l0;
        String $r11 = DisplayUtils.getApproximateTimeString($l0);
        StringBuilder $r12 = new StringBuilder().append($r11.substring(0, 1).toUpperCase());
        $r11 = $r11.substring(1);
        $r11 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_REQUEST_TIP_TEXT_PS_PS_HTML, new Object[]{$r12.append($r11).toString(), $r1.getRewardString($r4)});
        this.mTooltipRidewithRequests = UserTooltipView.showUserTooltip($r4, $r7, 0, String.format(NativeManager.getInstance().getLocale(), "<b>%s</b><br/>%s", new Object[]{$r10, $r11}), 0, AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_TYPE_EARLIEST_RIDE, false);
        if (this.mTooltipRidewithRequests != null) {
            UserTooltipView $r16 = this.mTooltipRidewithRequests;
            TextView $r17 = $r16.getTextView();
            if ($r2 == null) {
                $r17.setCompoundDrawablesWithIntrinsicBounds(C1283R.drawable.carpool_icon_white, 0, 0, 0);
                $r17.setPadding(PixelMeasure.dp(4), 0, 0, 0);
            } else {
                $r2.setBounds(0, 0, PixelMeasure.dp(56), PixelMeasure.dp(56));
                $r17.setCompoundDrawables($r2, null, null, null);
            }
            $r17.setCompoundDrawablePadding(PixelMeasure.dp(12));
            $r16 = this.mTooltipRidewithRequests;
            $r16.setCarpoolStyle();
            AnonymousClass10 anonymousClass10 = new Runnable() {
                public void run() throws  {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_TOOL_TIP_CLICKED);
                    TooltipManager.this.myLayoutManager.openRightSide();
                }
            };
            $r16 = this.mTooltipRidewithRequests;
            UserTooltipView $r162 = $r16;
            $r16.setOnClick(anonymousClass10);
            this.mTooltipRidewithRequests.setOnClose(new Runnable() {
                public void run() throws  {
                    TooltipManager.this.closeTooltip(true, 8);
                }
            });
            setCloseTooltipEvents(8, null);
        }
    }

    private void showCarpoolStrip(CarpoolDrive $r1) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        this.isTooltipCarpoolStripShown = true;
        this.mCarpoolTicker = new CarpoolStripNotification($r2, this.myLayoutManager);
        String $r7 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_REQUEST_STRIP_TITLE_PS, new Object[]{$r1.getRider().getFirstName()});
        long $l0 = $r1.getTime() * 1000;
        long j = $l0;
        String $r8 = DisplayUtils.getApproximateTimeString($l0);
        StringBuilder $r9 = new StringBuilder().append($r8.substring(0, 1).toUpperCase());
        $r8 = $r8.substring(1);
        $r8 = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_REQUEST_STRIP_TEXT_PS_PS_PD, new Object[]{$r9.append($r8).toString(), $r1.getRewardString($r2), Integer.valueOf($r1.getDetourMinutes())});
        final CarpoolDrive carpoolDrive = $r1;
        this.mCarpoolTicker.show($r7, $r8, "", 12, new OnClickListener() {
            public void onClick(View v) throws  {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_OFFER_STRIP_ENTER);
                MainActivity $r3 = AppService.getMainActivity();
                Intent $r2 = new Intent($r3, CarpoolRideDetailsActivity.class);
                $r2.putExtra(CarpoolDrive.class.getSimpleName(), carpoolDrive);
                $r3.startActivity($r2);
                TooltipManager.this.closeTooltip(true, 8);
            }
        }, new OnClickListener() {
            public void onClick(View v) throws  {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_OFFER_STRIP_CLOSE);
                TooltipManager.this.closeTooltip(true, 8);
            }
        }, true, true);
        if ($r1.isMultiPax()) {
            this.mCarpoolTicker.setUserImages($r1);
        } else {
            this.mCarpoolTicker.setUserImage($r1.getRider().getImage());
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CARPOOL_OFFER_STRIP_SHOWN);
        this.myLayoutManager.setPopupShown();
    }

    private void showFollowingToolTip(final int $i0, int $i1, FriendUserData $r1) throws  {
        if (this.myLayoutManager.isManualRidePopupShowing()) {
            Logger.m36d("Manual rides: Tooltip not showing because manual ride takeover is shown");
            return;
        }
        this.mTooltipShare = new ShareStatusPopUp(AppService.getMainActivity(), this.myLayoutManager, $i0, $i1, $r1);
        if (this.mTooltipShare.show(false)) {
            this.mTooltipShare.setOnClose(new Runnable() {
                public void run() throws  {
                    TooltipManager.this.closeTooltip(true, $i0);
                }
            });
            setCloseTooltipEvents($i0, null);
        }
    }

    private void setTooltipShowEvents(final LinearLayout $r1, final String $r2, final int $i0, final boolean $z0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                showAnimatedTooltip($r1, $r2, $i0, $z0);
            }

            private void showAnimatedTooltip(LinearLayout $r1, String $r2, int $i0, boolean $z0) throws  {
                ((TextView) $r1.findViewById(C1283R.id.tipText)).setText($r2);
                if ($i0 > 0) {
                    ImageView $r5 = (ImageView) $r1.findViewById(C1283R.id.tipImage);
                    if ($r5 != null) {
                        $r5.setImageResource($i0);
                    }
                }
                View $r3 = $r1.findViewById(C1283R.id.tipImageRight);
                if ($r3 != null) {
                    byte $b1;
                    if ($z0) {
                        $b1 = (byte) 0;
                    } else {
                        $b1 = (byte) 8;
                    }
                    $r3.setVisibility($b1);
                }
                TooltipManager.this.mCurLayoutTooltip.setDismissOnBgTouch(true);
                TooltipManager.this.mCurLayoutTooltip.show();
            }
        });
    }

    private void setCloseTooltipEvents(final int $i0, LayoutTooltip currentTooltip) throws  {
        if (isAutoCloseToolTip($i0)) {
            if (this.mCloseTooltipRunnable != null) {
                AppService.Remove(this.mCloseTooltipRunnable);
            }
            this.mCloseTooltipRunnable = new Runnable() {
                public void run() throws  {
                    TooltipManager.this.mCloseTooltipRunnable = null;
                    TooltipManager.this.closeTooltip(true, $i0, true);
                }
            };
            this.mIsToolTipDisplayed = true;
            AppService.Post(this.mCloseTooltipRunnable, this.mCustomTimeout != 0 ? (long) this.mCustomTimeout : 30000);
            this.mCustomTimeout = 0;
        }
        if (isAnyTouchClosesToolTip($i0)) {
            View $r6 = this.myLayoutManager.getMainLayout().findViewById(C1283R.id.tooltipFrameForTouchEvents);
            $r6.setVisibility(0);
            $r6.setOnTouchListener(new OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) throws  {
                    TooltipManager.this.closeTooltip(false, 6);
                    TooltipManager.this.closeTooltip(false, 0);
                    TooltipManager.this.myLayoutManager.getMainLayout().findViewById(C1283R.id.tooltipFrameForTouchEvents).setVisibility(8);
                    return false;
                }
            });
        }
    }

    void closeTooltip() throws  {
        if (this.myLayoutManager != null) {
            this.myLayoutManager.showSpotifyButton();
        }
        if (this.isTooltipRoadClosureShown) {
            closeTooltip(true, 5);
        } else if (this.isTooltipMainMenuShown) {
            closeTooltip(true, 0);
        } else if (this.isTooltipRidewithRequestsShownSmall || this.isTooltipRidewithRequestsShownLarge) {
            closeTooltip(true, 8);
        } else if (this.isTooltipCarpoolStripShown) {
            closeTooltip(true, 8);
            closeTooltip(true, 10);
        } else if (this.isTooltipUpcomingCarpoolShown) {
            closeTooltip(true, 9);
        } else if (this.isTooltipShareShown) {
            closeTooltip(true, 1);
        } else if (this.isTooltipFriendsShown) {
            closeTooltip(true, 6);
        } else if (this.isTooltipCarpoolPromoTipShown) {
            closeTooltip(true, 11);
        }
    }

    public void closeTooltip(boolean $z0, int $i0) throws  {
        closeTooltip($z0, $i0, false);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void closeTooltip(boolean r8, int r9, boolean r10) throws  {
        /*
        r7 = this;
        switch(r9) {
            case 0: goto L_0x000a;
            case 1: goto L_0x0020;
            case 2: goto L_0x0004;
            case 3: goto L_0x0004;
            case 4: goto L_0x0004;
            case 5: goto L_0x0007;
            case 6: goto L_0x0023;
            case 7: goto L_0x0004;
            case 8: goto L_0x000d;
            case 9: goto L_0x001d;
            case 10: goto L_0x0029;
            case 11: goto L_0x0026;
            default: goto L_0x0003;
        };
    L_0x0003:
        goto L_0x0004;
    L_0x0004:
        if (r8 != 0) goto L_0x002c;
    L_0x0006:
        return;
    L_0x0007:
        r8 = r7.isTooltipRoadClosureShown;
        goto L_0x0004;
    L_0x000a:
        r8 = r7.isTooltipMainMenuShown;
        goto L_0x0004;
    L_0x000d:
        r8 = r7.isTooltipRidewithRequestsShownSmall;
        if (r8 != 0) goto L_0x0019;
    L_0x0011:
        r8 = r7.isTooltipRidewithRequestsShownLarge;
        if (r8 != 0) goto L_0x0019;
    L_0x0015:
        r8 = r7.isTooltipCarpoolStripShown;
        if (r8 == 0) goto L_0x001b;
    L_0x0019:
        r8 = 1;
    L_0x001a:
        goto L_0x0004;
    L_0x001b:
        r8 = 0;
        goto L_0x001a;
    L_0x001d:
        r8 = r7.isTooltipUpcomingCarpoolShown;
        goto L_0x0004;
    L_0x0020:
        r8 = r7.isTooltipShareShown;
        goto L_0x0004;
    L_0x0023:
        r8 = r7.isTooltipFriendsShown;
        goto L_0x0004;
    L_0x0026:
        r8 = r7.isTooltipCarpoolPromoTipShown;
        goto L_0x0004;
    L_0x0029:
        r8 = r7.isTooltipCarpoolStripShown;
        goto L_0x0004;
    L_0x002c:
        r8 = 1;
        switch(r9) {
            case 0: goto L_0x0032;
            case 1: goto L_0x00a4;
            case 2: goto L_0x00a4;
            case 3: goto L_0x00a4;
            case 4: goto L_0x00a4;
            case 5: goto L_0x004b;
            case 6: goto L_0x00b7;
            case 7: goto L_0x0031;
            case 8: goto L_0x0051;
            case 9: goto L_0x0084;
            case 10: goto L_0x00d5;
            case 11: goto L_0x00bd;
            default: goto L_0x0030;
        };
    L_0x0030:
        goto L_0x0031;
    L_0x0031:
        return;
    L_0x0032:
        r0 = r7.mTooltipMainMenu;
        r1 = 0;
        r7.isTooltipMainMenuShown = r1;
    L_0x0037:
        r2 = r7.myLayoutManager;
        if (r2 == 0) goto L_0x0040;
    L_0x003b:
        r2 = r7.myLayoutManager;
        r2.showSpotifyButton();
    L_0x0040:
        if (r0 == 0) goto L_0x0045;
    L_0x0042:
        r0.dismiss();
    L_0x0045:
        if (r8 == 0) goto L_0x00eb;
    L_0x0047:
        r7.toolTipClosed(r9);
        return;
    L_0x004b:
        r0 = r7.mTooltipRoadClosure;
        r1 = 0;
        r7.isTooltipRoadClosureShown = r1;
        goto L_0x0037;
    L_0x0051:
        r3 = r7.mTooltipRidewithRequests;
        if (r3 == 0) goto L_0x006a;
    L_0x0055:
        if (r10 == 0) goto L_0x005c;
    L_0x0057:
        r3 = r7.mTooltipRidewithRequests;
        r3.reportTimeout();
    L_0x005c:
        r3 = r7.mTooltipRidewithRequests;
        r3.hideTooltip();
        r4 = 0;
        r7.mTooltipRidewithRequests = r4;
        r1 = 0;
        r7.isTooltipRidewithRequestsShownSmall = r1;
        r1 = 0;
        r7.isTooltipRidewithRequestsShownLarge = r1;
    L_0x006a:
        r5 = r7.mCarpoolTicker;
        if (r5 == 0) goto L_0x007d;
    L_0x006e:
        r5 = r7.mCarpoolTicker;
        r5.hide();
        r4 = 0;
        r7.mCarpoolTicker = r4;
        r1 = 0;
        r7.isTooltipCarpoolStripShown = r1;
        goto L_0x007d;
    L_0x007a:
        goto L_0x0037;
    L_0x007d:
        r2 = r7.myLayoutManager;
        r2.setPopupShown();
        r0 = 0;
        goto L_0x0037;
    L_0x0084:
        r3 = r7.mTooltipUpcomingRide;
        goto L_0x008a;
    L_0x0087:
        goto L_0x0037;
    L_0x008a:
        if (r3 == 0) goto L_0x00a2;
    L_0x008c:
        if (r10 == 0) goto L_0x0093;
    L_0x008e:
        r3 = r7.mTooltipUpcomingRide;
        r3.reportTimeout();
    L_0x0093:
        r3 = r7.mTooltipUpcomingRide;
        goto L_0x0099;
    L_0x0096:
        goto L_0x0037;
    L_0x0099:
        r3.hideTooltip();
        r4 = 0;
        r7.mTooltipUpcomingRide = r4;
        r1 = 0;
        r7.isTooltipUpcomingCarpoolShown = r1;
    L_0x00a2:
        r0 = 0;
        goto L_0x0037;
    L_0x00a4:
        r0 = 0;
        r6 = r7.mTooltipShare;
        if (r6 == 0) goto L_0x00b1;
    L_0x00a9:
        r6 = r7.mTooltipShare;
        r6.hide();
        r4 = 0;
        r7.mTooltipShare = r4;
    L_0x00b1:
        r8 = r7.isTooltipShareShown;
        r1 = 0;
        r7.isTooltipShareShown = r1;
        goto L_0x0037;
    L_0x00b7:
        r0 = r7.mTooltipFriends;
        r1 = 0;
        r7.isTooltipFriendsShown = r1;
        goto L_0x007a;
    L_0x00bd:
        r3 = r7.mTooltipCarpoolPromo;
        if (r3 == 0) goto L_0x00d0;
    L_0x00c1:
        if (r10 == 0) goto L_0x00c8;
    L_0x00c3:
        r3 = r7.mTooltipCarpoolPromo;
        r3.reportTimeout();
    L_0x00c8:
        r3 = r7.mTooltipCarpoolPromo;
        r3.hideTooltip();
        r4 = 0;
        r7.mTooltipCarpoolPromo = r4;
    L_0x00d0:
        r1 = 0;
        r7.isTooltipCarpoolPromoTipShown = r1;
        r0 = 0;
        goto L_0x0087;
    L_0x00d5:
        r1 = 0;
        r7.isTooltipCarpoolStripShown = r1;
        r2 = r7.myLayoutManager;
        r2.setPopupShown();
        r5 = r7.mCarpoolTicker;
        if (r5 == 0) goto L_0x00e9;
    L_0x00e1:
        r5 = r7.mCarpoolTicker;
        r5.hide();
        r4 = 0;
        r7.mCarpoolTicker = r4;
    L_0x00e9:
        r0 = 0;
        goto L_0x0096;
    L_0x00eb:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.TooltipManager.closeTooltip(boolean, int, boolean):void");
    }

    void toolTipClosed(int $i0) throws  {
        if (isAutoCloseToolTip($i0)) {
            this.mIsToolTipDisplayed = false;
            if (this.mCloseTooltipRunnable != null) {
                AppService.Remove(this.mCloseTooltipRunnable);
                this.mCloseTooltipRunnable = null;
            }
            if (!this.mToolTipQueue.isEmpty()) {
                final QueuedTooltip $r4 = (QueuedTooltip) this.mToolTipQueue.remove(0);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        TooltipManager.this.showToolTip($r4.type, $r4.numUsers, $r4.userData, $r4.cb, $r4.intParam);
                    }
                }, 1000);
            }
        }
        this.myLayoutManager.setPopupShown();
        if (this.myLayoutManager != null) {
            this.myLayoutManager.showSpotifyButton();
        }
    }

    public void onOrientationChanged(int $i0) throws  {
        if (this.mTooltipRidewithRequests != null || this.mTooltipUpcomingRide != null) {
            final View $r4 = this.myLayoutManager.getMainLayout().findViewById(C1283R.id.mainBottomBarRight);
            DisplayUtils.lmkWhenOrientationReallyChanged($r4, $i0, new OnOrientationReallyChanged() {
                public void onChanged(int orientation) throws  {
                    MainActivity $r1 = AppService.getMainActivity();
                    if (TooltipManager.this.mTooltipUpcomingRide != null) {
                        UserTooltipView.repositionUserTooltip(TooltipManager.this.mTooltipUpcomingRide, $r1, $r4, 0, false);
                    }
                    if (TooltipManager.this.mTooltipRidewithRequests != null) {
                        UserTooltipView.repositionUserTooltip(TooltipManager.this.mTooltipRidewithRequests, $r1, $r4, 0, false);
                    }
                }
            });
        }
    }

    public boolean areCarpoolTipsShown() throws  {
        return this.isTooltipUpcomingCarpoolShown || this.isTooltipRidewithRequestsShownLarge || this.isTooltipCarpoolPromoTipShown;
    }
}
