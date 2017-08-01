package com.waze.navigate;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolLocation;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolOnboardingManager;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.carpool.CarpoolUserData;
import com.waze.carpool.CommuteModelWeekActivity;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.settings.SettingsDialogListener;
import com.waze.settings.SettingsUtils;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.view.text.WazeTextView;
import dalvik.annotation.Signature;
import java.util.Arrays;

public final class AddHomeWorkActivity extends ActivityBase {
    public static final int HOME_ADD_RC = 5;
    public static final int HOME_EMPTY_RC = 6;
    private static final int RQ_ADD = 1001;
    AddressItem aiHome = null;
    AddressItem aiWork = null;
    INextActionCallback getNextCb = new C20571();
    private boolean hadProblem;
    private boolean homeEdited = false;
    private boolean mCarpool;
    private CarpoolNativeManager mCpMgr;
    private DriveToNativeManager mDtnMgr;
    private boolean mEditHome;
    private OnClickListener mEditHomeListener;
    private boolean mEditWork;
    private OnClickListener mEditWorkListener;
    private WazeSettingsView mHome;
    private NativeManager mNatMgr;
    private boolean mOnboarding;
    private boolean mProblemHome;
    private boolean mProblemWork;
    private boolean mSkipOnReturn = false;
    private CarpoolUserData mUserData;
    private WazeSettingsView mWork;
    boolean mbGotHome = false;
    boolean mbGotWork = false;
    private boolean workEdited = false;

    class C20571 implements INextActionCallback {
        C20571() throws  {
        }

        public void setNextIntent(Intent $r1) throws  {
            AddHomeWorkActivity.this.startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
        }

        public void setNextFragment(Fragment fragment) throws  {
            Logger.m38e("AddHomeWork: unexpected action: setNextFragment");
        }

        public void setNextResult(int $i0) throws  {
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                super.onBackPressed();
            } else if ($i0 == -1) {
                AddHomeWorkActivity.this.setResult(-1);
                AddHomeWorkActivity.this.finish();
            } else {
                Logger.m38e("AddHomeWork: received unexpected result:" + $i0);
            }
        }

        public Context getContext() throws  {
            return AddHomeWorkActivity.this;
        }
    }

    class C20582 implements OnClickListener {
        C20582() throws  {
        }

        public void onClick(View v) throws  {
            if (!AddHomeWorkActivity.this.homeWorkProblem()) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM);
                AddHomeWorkActivity.this.mDtnMgr.StoreAddressItem(AddHomeWorkActivity.this.aiHome, true);
                AddHomeWorkActivity.this.mDtnMgr.StoreAddressItem(AddHomeWorkActivity.this.aiWork, true);
                AddHomeWorkActivity.this.setResult(-1);
                if (AddHomeWorkActivity.this.hadProblem) {
                    AddHomeWorkActivity.this.startActivity(new Intent(AddHomeWorkActivity.this, CommuteModelWeekActivity.class));
                }
                AddHomeWorkActivity.this.finish();
            }
        }
    }

    class C20593 implements OnClickListener {
        C20593() throws  {
        }

        public void onClick(View v) throws  {
            if (!AddHomeWorkActivity.this.homeWorkProblem()) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM);
                AddHomeWorkActivity.this.mDtnMgr.StoreAddressItem(AddHomeWorkActivity.this.aiHome, true);
                AddHomeWorkActivity.this.mDtnMgr.StoreAddressItem(AddHomeWorkActivity.this.aiWork, true);
                AddHomeWorkActivity.this.mCpMgr.refreshCarpoolUser();
                CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, AddHomeWorkActivity.this.getNextCb);
            }
        }
    }

    class C20604 implements OnClickListener {
        C20604() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_CLICK).addParam("ACTION", "HOME").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_STATUS, AddHomeWorkActivity.this.aiHome == null ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_SET : "EDIT").send();
            AddHomeWorkActivity.this.searchClicked(3);
        }
    }

    class C20615 implements OnClickListener {
        C20615() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_CLICK).addParam("ACTION", "WORK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_STATUS, AddHomeWorkActivity.this.aiWork == null ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_SET : "EDIT").send();
            AddHomeWorkActivity.this.searchClicked(4);
        }
    }

    class C20648 implements DriveToGetAddressItemArrayCallback {
        C20648() throws  {
        }

        public void getAddressItemArrayCallback(AddressItem[] $r1) throws  {
            if ($r1 != null && $r1.length > 0) {
                AddHomeWorkActivity.this.aiHome = $r1[0];
            }
            if (!AddHomeWorkActivity.this.mbGotHome) {
                AddHomeWorkActivity.this.mbGotHome = true;
                AddHomeWorkActivity.this.reportAnalyticsHomeWork(true);
            }
            if (AddHomeWorkActivity.this.mOnboarding) {
                AddHomeWorkActivity.this.updateField(AddHomeWorkActivity.this.mHome, AddHomeWorkActivity.this.aiHome, LocationType.HOME_ONBOARDING);
            } else {
                AddHomeWorkActivity.this.updateField(AddHomeWorkActivity.this.mHome, AddHomeWorkActivity.this.aiHome, LocationType.HOME);
            }
            AddHomeWorkActivity.this.checkConfirmEnablement();
        }
    }

    class C20659 implements DriveToGetAddressItemArrayCallback {
        C20659() throws  {
        }

        public void getAddressItemArrayCallback(AddressItem[] $r1) throws  {
            if ($r1 != null && $r1.length > 0) {
                AddHomeWorkActivity.this.aiWork = $r1[0];
            }
            if (!AddHomeWorkActivity.this.mbGotWork) {
                AddHomeWorkActivity.this.mbGotWork = true;
                AddHomeWorkActivity.this.reportAnalyticsHomeWork(true);
            }
            if (AddHomeWorkActivity.this.mOnboarding) {
                AddHomeWorkActivity.this.updateField(AddHomeWorkActivity.this.mWork, AddHomeWorkActivity.this.aiWork, LocationType.WORK_ONBOARDING);
            } else {
                AddHomeWorkActivity.this.updateField(AddHomeWorkActivity.this.mWork, AddHomeWorkActivity.this.aiWork, LocationType.WORK);
            }
            AddHomeWorkActivity.this.checkConfirmEnablement();
        }
    }

    enum LocationType {
        HOME(C1283R.drawable.list_icon_home, DisplayStrings.DS_HOME_WORK_WIZ_SET_HOME_SUBTITLE, DisplayStrings.DS_HOME_WORK_WIZ_EDIT_HOME, DisplayStrings.DS_HOME_WORK_WIZ_RESOLVE_HOME, 287, DisplayStrings.DS_HOME_WORK_WIZ_SET_HOME_TITLE),
        HOME_ONBOARDING(C1283R.drawable.list_icon_home, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_SET_HOME_SUBTITLE, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_EDIT_HOME, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_RESOLVE_HOME, DisplayStrings.DS_HOME_ONBOARDING, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_SET_HOME_TITLE),
        HOME_CARPOOL(C1283R.drawable.list_icon_home, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_SET_HOME_SUBTITLE, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_EDIT_HOME, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_RESOLVE_HOME, DisplayStrings.DS_HOME_CARPOOL, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_SET_HOME_TITLE),
        WORK(C1283R.drawable.list_icon_work, DisplayStrings.DS_HOME_WORK_WIZ_SET_WORK_SUBTITLE, DisplayStrings.DS_HOME_WORK_WIZ_EDIT_WORK, DisplayStrings.DS_HOME_WORK_WIZ_RESOLVE_WORK, 288, DisplayStrings.DS_HOME_WORK_WIZ_SET_WORK_TITLE),
        WORK_ONBOARDING(C1283R.drawable.list_icon_work, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_SET_WORK_SUBTITLE, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_EDIT_WORK, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_RESOLVE_WORK, DisplayStrings.DS_WORK_ONBOARDING, DisplayStrings.DS_HOME_WORK_ONBOARDING_WIZ_SET_WORK_TITLE),
        WORK_CARPOOL(C1283R.drawable.list_icon_work, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_SET_WORK_SUBTITLE, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_EDIT_WORK, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_RESOLVE_WORK, DisplayStrings.DS_WORK_CARPOOL, DisplayStrings.DS_HOME_WORK_CARPOOL_WIZ_SET_WORK_TITLE),
        HOME_PUSH(C1283R.drawable.list_icon_home_push, DisplayStrings.DS_HOME_WORK_WIZ_ADD_HOME, DisplayStrings.DS_HOME_WORK_WIZ_EDIT_HOME, DisplayStrings.DS_HOME_WORK_WIZ_RESOLVE_HOME, 287, DisplayStrings.DS_HOME_WORK_WIZ_SET_HOME_TITLE),
        WORK_PUSH(C1283R.drawable.list_icon_work_push, DisplayStrings.DS_HOME_WORK_WIZ_ADD_WORK, DisplayStrings.DS_HOME_WORK_WIZ_EDIT_WORK, DisplayStrings.DS_HOME_WORK_WIZ_RESOLVE_WORK, 288, DisplayStrings.DS_HOME_WORK_WIZ_SET_WORK_TITLE);
        
        final int addDs;
        final int addTitleDs;
        final int editDs;
        final int iconResource;
        final int resolveDs;
        final int titleDs;

        private LocationType(@Signature({"(IIIIII)V"}) int $i1, @Signature({"(IIIIII)V"}) int $i2, @Signature({"(IIIIII)V"}) int $i3, @Signature({"(IIIIII)V"}) int $i4, @Signature({"(IIIIII)V"}) int $i5, @Signature({"(IIIIII)V"}) int $i6) throws  {
            this.iconResource = $i1;
            this.addDs = $i2;
            this.editDs = $i3;
            this.resolveDs = $i4;
            this.titleDs = $i5;
            this.addTitleDs = $i6;
        }
    }

    protected void onActivityResult(int r20, int r21, android.content.Intent r22) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:30:0x00ab in {9, 14, 19, 29, 31, 33, 37, 39, 43, 56, 59, 63, 65, 71, 75, 78, 80, 81} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r19 = this;
        r4 = 1001; // 0x3e9 float:1.403E-42 double:4.946E-321;
        r0 = r20;
        if (r0 != r4) goto L_0x019e;
    L_0x0006:
        r4 = -1;
        r0 = r21;
        if (r0 != r4) goto L_0x019e;
    L_0x000b:
        if (r22 == 0) goto L_0x0066;
    L_0x000d:
        r0 = r22;
        r5 = r0.getExtras();
        r7 = "ai";
        r6 = r5.get(r7);
        r9 = r6;
        r9 = (com.waze.navigate.AddressItem) r9;
        r8 = r9;
        if (r8 == 0) goto L_0x0066;
    L_0x001f:
        r10 = r8.getAddress();
        r11 = r10.isEmpty();
        if (r11 == 0) goto L_0x0030;
    L_0x0029:
        r10 = r8.getSecondaryTitle();
        r8.setAddress(r10);
    L_0x0030:
        r0 = r19;
        r12 = r0.mDtnMgr;
        r4 = 1;
        r12.StoreAddressItem(r8, r4);
        r0 = r19;
        r22 = r0.getIntent();
        r7 = "AddressType";
        r4 = 0;
        r0 = r22;
        r20 = r0.getIntExtra(r7, r4);
        r4 = 2;
        r0 = r20;
        if (r0 == r4) goto L_0x0051;
    L_0x004c:
        r4 = 4;
        r0 = r20;
        if (r0 != r4) goto L_0x00af;
    L_0x0051:
        r0 = r19;
        r12 = r0.mDtnMgr;
        r13 = new com.waze.navigate.AddHomeWorkActivity$12;
        r0 = r19;
        r13.<init>();
        r4 = 1;
        r14 = 1;
        r12.navigate(r8, r13, r4, r14);
        r0 = r19;
        r0.finish();
    L_0x0066:
        r0 = r19;
        r11 = r0.mCarpool;
        if (r11 != 0) goto L_0x0077;
    L_0x006c:
        r0 = r19;
        r11 = r0.mOnboarding;
        if (r11 != 0) goto L_0x0077;
    L_0x0072:
        r0 = r19;
        r0.getHomeAndWork();
    L_0x0077:
        r0 = r19;
        r0.checkConfirmEnablement();
        r4 = 32783; // 0x800f float:4.5939E-41 double:1.6197E-319;
        r0 = r19;
        r0.setResult(r4);
        r0 = r19;
        r11 = r0.mSkipOnReturn;
        if (r11 == 0) goto L_0x01e8;
    L_0x008a:
        r0 = r19;
        r11 = r0.mCarpool;
        if (r11 != 0) goto L_0x0096;
    L_0x0090:
        r0 = r19;
        r11 = r0.mOnboarding;
        if (r11 == 0) goto L_0x017c;
    L_0x0096:
        r20 = android.os.Build.VERSION.SDK_INT;
        r4 = 15;
        r0 = r20;
        if (r0 < r4) goto L_0x016f;
    L_0x009e:
        r4 = 2131689744; // 0x7f0f0110 float:1.9008512E38 double:1.05319467E-314;
        r0 = r19;
        r15 = r0.findViewById(r4);
        r15.callOnClick();
        return;
        goto L_0x00af;
    L_0x00ac:
        goto L_0x0066;
    L_0x00af:
        r10 = r8.getTitle();
        goto L_0x00b7;
    L_0x00b4:
        goto L_0x0066;
    L_0x00b7:
        r7 = "Work";
        r11 = r10.equals(r7);
        if (r11 == 0) goto L_0x011b;
    L_0x00bf:
        goto L_0x00c3;
    L_0x00c0:
        goto L_0x0066;
    L_0x00c3:
        r0 = r19;
        r0.aiWork = r8;
        r4 = 0;
        r0 = r19;
        r0.mProblemWork = r4;
        r4 = 1;
        r0 = r19;
        r0.workEdited = r4;
        goto L_0x00d5;
    L_0x00d2:
        goto L_0x0066;
    L_0x00d5:
        r0 = r19;
        r11 = r0.mCarpool;
        if (r11 == 0) goto L_0x00f1;
    L_0x00db:
        goto L_0x00df;
    L_0x00dc:
        goto L_0x0066;
    L_0x00df:
        r0 = r19;
        r0 = r0.mWork;
        r16 = r0;
        r17 = com.waze.navigate.AddHomeWorkActivity.LocationType.WORK_CARPOOL;
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r0.updateField(r1, r8, r2);
        goto L_0x00ac;
    L_0x00f1:
        r0 = r19;
        r11 = r0.mOnboarding;
        if (r11 == 0) goto L_0x0109;
    L_0x00f7:
        r0 = r19;
        r0 = r0.mWork;
        r16 = r0;
        r17 = com.waze.navigate.AddHomeWorkActivity.LocationType.WORK_ONBOARDING;
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r0.updateField(r1, r8, r2);
        goto L_0x00b4;
    L_0x0109:
        r0 = r19;
        r0 = r0.mWork;
        r16 = r0;
        r17 = com.waze.navigate.AddHomeWorkActivity.LocationType.WORK;
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r0.updateField(r1, r8, r2);
        goto L_0x00c0;
    L_0x011b:
        r0 = r19;
        r0.aiHome = r8;
        r4 = 0;
        r0 = r19;
        r0.mProblemHome = r4;
        r4 = 1;
        r0 = r19;
        r0.homeEdited = r4;
        r0 = r19;
        r11 = r0.mCarpool;
        if (r11 == 0) goto L_0x0141;
    L_0x012f:
        r0 = r19;
        r0 = r0.mHome;
        r16 = r0;
        r17 = com.waze.navigate.AddHomeWorkActivity.LocationType.HOME_CARPOOL;
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r0.updateField(r1, r8, r2);
        goto L_0x00d2;
    L_0x0141:
        r0 = r19;
        r11 = r0.mOnboarding;
        if (r11 == 0) goto L_0x0159;
    L_0x0147:
        r0 = r19;
        r0 = r0.mHome;
        r16 = r0;
        r17 = com.waze.navigate.AddHomeWorkActivity.LocationType.HOME_ONBOARDING;
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r0.updateField(r1, r8, r2);
        goto L_0x00dc;
    L_0x0159:
        r0 = r19;
        r0 = r0.mHome;
        r16 = r0;
        r17 = com.waze.navigate.AddHomeWorkActivity.LocationType.HOME;
        goto L_0x0165;
    L_0x0162:
        goto L_0x0066;
    L_0x0165:
        r0 = r19;
        r1 = r16;
        r2 = r17;
        r0.updateField(r1, r8, r2);
        goto L_0x0162;
    L_0x016f:
        r4 = 2131692430; // 0x7f0f0b8e float:1.901396E38 double:1.053195997E-314;
        r0 = r19;
        r15 = r0.findViewById(r4);
        r15.performClick();
        return;
    L_0x017c:
        r20 = android.os.Build.VERSION.SDK_INT;
        r4 = 15;
        r0 = r20;
        if (r0 < r4) goto L_0x0191;
    L_0x0184:
        r4 = 2131692430; // 0x7f0f0b8e float:1.901396E38 double:1.053195997E-314;
        r0 = r19;
        r15 = r0.findViewById(r4);
        r15.callOnClick();
        return;
    L_0x0191:
        r4 = 2131692430; // 0x7f0f0b8e float:1.901396E38 double:1.053195997E-314;
        r0 = r19;
        r15 = r0.findViewById(r4);
        r15.performClick();
        return;
    L_0x019e:
        r18 = com.waze.carpool.CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY;
        r0 = r20;
        r1 = r18;
        if (r0 != r1) goto L_0x01b7;
    L_0x01a6:
        r4 = -1;
        r0 = r21;
        if (r0 != r4) goto L_0x01b7;
    L_0x01ab:
        r4 = -1;
        r0 = r19;
        r0.setResult(r4);
        r0 = r19;
        r0.finish();
        return;
    L_0x01b7:
        r4 = -1;
        r0 = r21;
        if (r0 != r4) goto L_0x01c8;
    L_0x01bc:
        r4 = -1;
        r0 = r19;
        r0.setResult(r4);
        r0 = r19;
        r0.finish();
        return;
    L_0x01c8:
        r0 = r19;
        r11 = r0.mSkipOnReturn;
        if (r11 == 0) goto L_0x01dc;
    L_0x01ce:
        r0 = r19;
        r1 = r21;
        r2 = r22;
        r0.setResult(r1, r2);
        r0 = r19;
        r0.finish();
    L_0x01dc:
        r0 = r19;
        r1 = r20;
        r2 = r21;
        r3 = r22;
        super.onActivityResult(r1, r2, r3);
        return;
    L_0x01e8:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.AddHomeWorkActivity.onActivityResult(int, int, android.content.Intent):void");
    }

    public void onCreate(android.os.Bundle r36) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:45:0x030b
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r35 = this;
        r0 = r35;
        r1 = r36;
        super.onCreate(r1);
        r4 = 1;
        r0 = r35;
        r0.requestWindowFeature(r4);
        r4 = 2130903077; // 0x7f030025 float:1.7412962E38 double:1.052806005E-314;
        r0 = r35;
        r0.setContentView(r4);
        r5 = com.waze.NativeManager.getInstance();
        r0 = r35;
        r0.mNatMgr = r5;
        r6 = com.waze.navigate.DriveToNativeManager.getInstance();
        r0 = r35;
        r0.mDtnMgr = r6;
        r7 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r0 = r35;
        r0.mCpMgr = r7;
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "carpool";
        r4 = 0;
        r9 = r8.getBooleanExtra(r10, r4);
        r0 = r35;
        r0.mCarpool = r9;
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "onboarding";
        r4 = 0;
        r9 = r8.getBooleanExtra(r10, r4);
        r0 = r35;
        r0.mOnboarding = r9;
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "AddressType";
        r4 = 0;
        r11 = r8.getIntExtra(r10, r4);
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "edit_home";
        r4 = 0;
        r9 = r8.getBooleanExtra(r10, r4);
        r0 = r35;
        r0.mEditHome = r9;
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "edit_home";
        r8.removeExtra(r10);
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "edit_work";
        r4 = 0;
        r9 = r8.getBooleanExtra(r10, r4);
        r0 = r35;
        r0.mEditWork = r9;
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "edit_work";
        r8.removeExtra(r10);
        r0 = r35;
        r8 = r0.getIntent();
        r10 = "indicate_home";
        r8.removeExtra(r10);
        r0 = r35;
        r7 = r0.mCpMgr;
        r12 = r7.getCarpoolProfileNTV();
        r0 = r35;
        r0.mUserData = r12;
        r0 = r35;
        r12 = r0.mUserData;
        if (r12 != 0) goto L_0x00bb;
    L_0x00b1:
        r10 = "AddHomeWorkActivity: requested carpool screen, but there is no user";
        com.waze.Logger.m38e(r10);
        r4 = 0;
        r0 = r35;
        r0.mCarpool = r4;
    L_0x00bb:
        r4 = 2131689674; // 0x7f0f00ca float:1.900837E38 double:1.0531946355E-314;
        r0 = r35;
        r13 = r0.findViewById(r4);
        r15 = r13;
        r15 = (com.waze.view.title.TitleBar) r15;
        r14 = r15;
        r0 = r35;
        r5 = r0.mNatMgr;
        r4 = 1685; // 0x695 float:2.361E-42 double:8.325E-321;
        r16 = r5.getLanguageString(r4);
        r0 = r35;
        r1 = r16;
        r14.init(r0, r1);
        r4 = 2131689744; // 0x7f0f0110 float:1.9008512E38 double:1.05319467E-314;
        r0 = r35;
        r13 = r0.findViewById(r4);
        r18 = r13;
        r18 = (android.widget.TextView) r18;
        r17 = r18;
        r0 = r35;
        r5 = r0.mNatMgr;
        r4 = 1686; // 0x696 float:2.363E-42 double:8.33E-321;
        r16 = r5.getLanguageString(r4);
        r0 = r17;
        r1 = r16;
        r0.setText(r1);
        r0 = r35;
        r9 = r0.mCarpool;
        if (r9 == 0) goto L_0x031d;
    L_0x00ff:
        r4 = 0;
        r14.setCloseVisibility(r4);
        r0 = r35;
        r12 = r0.mUserData;
        r9 = r12.inferredHomeConflict();
        if (r9 != 0) goto L_0x0117;
    L_0x010d:
        r0 = r35;
        r12 = r0.mUserData;
        r9 = r12.inferredHomeMissing();
        if (r9 == 0) goto L_0x030f;
    L_0x0117:
        r9 = 1;
    L_0x0118:
        r0 = r35;
        r0.mProblemHome = r9;
        r0 = r35;
        r12 = r0.mUserData;
        r9 = r12.inferredWorkConflict();
        if (r9 != 0) goto L_0x0130;
    L_0x0126:
        r0 = r35;
        r12 = r0.mUserData;
        r9 = r12.inferredWorkMissing();
        if (r9 == 0) goto L_0x0315;
    L_0x0130:
        r9 = 1;
    L_0x0131:
        r0 = r35;
        r0.mProblemWork = r9;
        r0 = r35;
        r9 = r0.mProblemHome;
        if (r9 != 0) goto L_0x0141;
    L_0x013b:
        r0 = r35;
        r9 = r0.mProblemWork;
        if (r9 == 0) goto L_0x031b;
    L_0x0141:
        r9 = 1;
    L_0x0142:
        r0 = r35;
        r0.hadProblem = r9;
        r0 = r35;
        r12 = r0.mUserData;
        r0 = r12.inferred_home;
        r19 = r0;
        r20 = r0.toAddressItem();
        r0 = r20;
        r1 = r35;
        r1.aiHome = r0;
        r0 = r35;
        r12 = r0.mUserData;
        r0 = r12.inferred_work;
        r19 = r0;
        r20 = r0.toAddressItem();
        r0 = r20;
        r1 = r35;
        r1.aiWork = r0;
        r4 = 0;
        r0 = r17;
        r0.setVisibility(r4);
        r0 = r35;
        r0.checkConfirmEnablement();
        r21 = new com.waze.navigate.AddHomeWorkActivity$2;
        r0 = r21;
        r1 = r35;
        r0.<init>();
        r0 = r17;
        r1 = r21;
        r0.setOnClickListener(r1);
    L_0x0185:
        r4 = 2131689742; // 0x7f0f010e float:1.9008508E38 double:1.053194669E-314;
        r0 = r35;
        r13 = r0.findViewById(r4);
        r23 = r13;
        r23 = (com.waze.settings.WazeSettingsView) r23;
        r22 = r23;
        r0 = r22;
        r1 = r35;
        r1.mHome = r0;
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r10 = "";
        r0 = r22;
        r0.setValueText(r10);
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r4 = 287; // 0x11f float:4.02E-43 double:1.42E-321;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
        r0 = r22;
        r1 = r16;
        r0.setKeyText(r1);
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r4 = 2130838397; // 0x7f02037d float:1.7281775E38 double:1.052774049E-314;
        r0 = r22;
        r0.setSelectorImage(r4);
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r4 = 2130838435; // 0x7f0203a3 float:1.7281852E38 double:1.0527740676E-314;
        r0 = r22;
        r0.setIcon(r4);
        r24 = new com.waze.navigate.AddHomeWorkActivity$4;
        r0 = r24;
        r1 = r35;
        r0.<init>();
        r0 = r24;
        r1 = r35;
        r1.mEditHomeListener = r0;
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r0 = r35;
        r0 = r0.mEditHomeListener;
        r25 = r0;
        r0 = r22;
        r1 = r25;
        r0.setOnClickListener(r1);
        r4 = 2131689743; // 0x7f0f010f float:1.900851E38 double:1.0531946696E-314;
        r0 = r35;
        r13 = r0.findViewById(r4);
        r26 = r13;
        r26 = (com.waze.settings.WazeSettingsView) r26;
        r22 = r26;
        r0 = r22;
        r1 = r35;
        r1.mWork = r0;
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r4 = 288; // 0x120 float:4.04E-43 double:1.423E-321;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
        r0 = r22;
        r1 = r16;
        r0.setKeyText(r1);
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r4 = 2130838397; // 0x7f02037d float:1.7281775E38 double:1.052774049E-314;
        r0 = r22;
        r0.setSelectorImage(r4);
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r4 = 2130838498; // 0x7f0203e2 float:1.728198E38 double:1.0527740987E-314;
        r0 = r22;
        r0.setIcon(r4);
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r10 = "";
        r0 = r22;
        r0.setValueText(r10);
        r27 = new com.waze.navigate.AddHomeWorkActivity$5;
        r0 = r27;
        r1 = r35;
        r0.<init>();
        r0 = r27;
        r1 = r35;
        r1.mEditWorkListener = r0;
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r0 = r35;
        r0 = r0.mEditWorkListener;
        r25 = r0;
        r0 = r22;
        r1 = r25;
        r0.setOnClickListener(r1);
        r4 = 2131689741; // 0x7f0f010d float:1.9008506E38 double:1.0531946686E-314;
        r0 = r35;
        r13 = r0.findViewById(r4);
        r29 = r13;
        r29 = (com.waze.view.text.WazeTextView) r29;
        r28 = r29;
        r0 = r35;
        r9 = r0.mCarpool;
        if (r9 == 0) goto L_0x0382;
    L_0x0280:
        r0 = r35;
        r0.setupCarpoolExplanation();
    L_0x0285:
        r0 = r35;
        r9 = r0.mOnboarding;
        if (r9 != 0) goto L_0x02fc;
    L_0x028b:
        r0 = r35;
        r8 = r0.getIntent();
        if (r8 == 0) goto L_0x03a8;
    L_0x0293:
        r0 = r35;
        r8 = r0.getIntent();
        r36 = r8.getExtras();
        if (r36 == 0) goto L_0x03a8;
    L_0x029f:
        r0 = r35;
        r8 = r0.getIntent();
        r36 = r8.getExtras();
        r10 = "ai";
        r0 = r36;
        r30 = r0.get(r10);
        if (r30 == 0) goto L_0x03a8;
    L_0x02b3:
        r0 = r35;
        r8 = r0.getIntent();
        r36 = r8.getExtras();
        r10 = "ai";
        r0 = r36;
        r30 = r0.get(r10);
        r31 = r30;
        r31 = (com.waze.navigate.AddressItem) r31;
        r20 = r31;
    L_0x02cb:
        if (r20 == 0) goto L_0x02f7;
    L_0x02cd:
        r0 = r20;
        r32 = r0.getType();
        r4 = 3;
        r0 = r32;
        if (r0 != r4) goto L_0x03d9;
    L_0x02d8:
        r0 = r20;
        r1 = r35;
        r1.aiWork = r0;
        r0 = r35;
        r9 = r0.mCarpool;
        if (r9 == 0) goto L_0x03ab;
    L_0x02e4:
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r33 = com.waze.navigate.AddHomeWorkActivity.LocationType.WORK_CARPOOL;
        r0 = r35;
        r1 = r22;
        r2 = r20;
        r3 = r33;
        r0.updateField(r1, r2, r3);
    L_0x02f7:
        r0 = r35;
        r0.getHomeAndWork();
    L_0x02fc:
        r4 = 2;
        if (r11 != r4) goto L_0x043e;
    L_0x02ff:
        r4 = 10;
        r0 = r35;
        r0.searchClicked(r4);
        goto L_0x030a;
    L_0x0307:
        goto L_0x0285;
    L_0x030a:
        return;
        goto L_0x030f;
    L_0x030c:
        goto L_0x0118;
    L_0x030f:
        r9 = 0;
        goto L_0x030c;
        goto L_0x0315;
    L_0x0312:
        goto L_0x0131;
    L_0x0315:
        r9 = 0;
        goto L_0x0312;
        goto L_0x031b;
    L_0x0318:
        goto L_0x0142;
    L_0x031b:
        r9 = 0;
        goto L_0x0318;
    L_0x031d:
        r0 = r35;
        r9 = r0.mOnboarding;
        if (r9 == 0) goto L_0x037d;
    L_0x0323:
        r4 = 0;
        r14.setCloseVisibility(r4);
        r4 = 2916; // 0xb64 float:4.086E-42 double:1.4407E-320;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
        goto L_0x0331;
    L_0x032e:
        goto L_0x02cb;
    L_0x0331:
        r0 = r17;
        r1 = r16;
        r0.setText(r1);
        r4 = 2913; // 0xb61 float:4.082E-42 double:1.439E-320;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
        r0 = r16;
        r14.setTitle(r0);
        goto L_0x0347;
    L_0x0344:
        goto L_0x0307;
    L_0x0347:
        r4 = 0;
        r0 = r35;
        r0.hadProblem = r4;
        r4 = 0;
        r0 = r17;
        r0.setVisibility(r4);
        r0 = r35;
        r0.getHomeAndWork();
        goto L_0x035b;
    L_0x0358:
        goto L_0x02f7;
    L_0x035b:
        r0 = r35;
        r0.checkConfirmEnablement();
        r34 = new com.waze.navigate.AddHomeWorkActivity$3;
        goto L_0x0366;
    L_0x0363:
        goto L_0x02f7;
    L_0x0366:
        r0 = r34;
        r1 = r35;
        r0.<init>();
        goto L_0x0371;
    L_0x036e:
        goto L_0x0185;
    L_0x0371:
        r0 = r17;
        r1 = r34;
        r0.setOnClickListener(r1);
        goto L_0x036e;
        goto L_0x037d;
    L_0x037a:
        goto L_0x0185;
    L_0x037d:
        r4 = 0;
        r14.setCloseVisibility(r4);
        goto L_0x037a;
    L_0x0382:
        r0 = r35;
        r9 = r0.mOnboarding;
        if (r9 == 0) goto L_0x0396;
    L_0x0388:
        r4 = 2915; // 0xb63 float:4.085E-42 double:1.44E-320;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
        r0 = r28;
        r1 = r16;
        r0.setText(r1);
        goto L_0x0344;
    L_0x0396:
        r4 = 1688; // 0x698 float:2.365E-42 double:8.34E-321;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
        goto L_0x03a0;
    L_0x039d:
        goto L_0x0285;
    L_0x03a0:
        r0 = r28;
        r1 = r16;
        r0.setText(r1);
        goto L_0x039d;
    L_0x03a8:
        r20 = 0;
        goto L_0x032e;
    L_0x03ab:
        r0 = r35;
        r9 = r0.mOnboarding;
        if (r9 == 0) goto L_0x03c5;
    L_0x03b1:
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r33 = com.waze.navigate.AddHomeWorkActivity.LocationType.WORK_ONBOARDING;
        r0 = r35;
        r1 = r22;
        r2 = r20;
        r3 = r33;
        r0.updateField(r1, r2, r3);
        goto L_0x0358;
    L_0x03c5:
        r0 = r35;
        r0 = r0.mWork;
        r22 = r0;
        r33 = com.waze.navigate.AddHomeWorkActivity.LocationType.WORK_PUSH;
        r0 = r35;
        r1 = r22;
        r2 = r20;
        r3 = r33;
        r0.updateField(r1, r2, r3);
        goto L_0x0363;
    L_0x03d9:
        r0 = r20;
        r32 = r0.getType();
        r4 = 1;
        r0 = r32;
        if (r0 != r4) goto L_0x02f7;
    L_0x03e4:
        r0 = r20;
        r1 = r35;
        r1.aiHome = r0;
        r0 = r35;
        r9 = r0.mCarpool;
        if (r9 == 0) goto L_0x0408;
    L_0x03f0:
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r33 = com.waze.navigate.AddHomeWorkActivity.LocationType.HOME_CARPOOL;
        goto L_0x03fc;
    L_0x03f9:
        goto L_0x02f7;
    L_0x03fc:
        r0 = r35;
        r1 = r22;
        r2 = r20;
        r3 = r33;
        r0.updateField(r1, r2, r3);
        goto L_0x03f9;
    L_0x0408:
        r0 = r35;
        r9 = r0.mOnboarding;
        if (r9 == 0) goto L_0x0426;
    L_0x040e:
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r33 = com.waze.navigate.AddHomeWorkActivity.LocationType.HOME_ONBOARDING;
        goto L_0x041a;
    L_0x0417:
        goto L_0x02f7;
    L_0x041a:
        r0 = r35;
        r1 = r22;
        r2 = r20;
        r3 = r33;
        r0.updateField(r1, r2, r3);
        goto L_0x0417;
    L_0x0426:
        r0 = r35;
        r0 = r0.mHome;
        r22 = r0;
        r33 = com.waze.navigate.AddHomeWorkActivity.LocationType.HOME_PUSH;
        goto L_0x0432;
    L_0x042f:
        goto L_0x02f7;
    L_0x0432:
        r0 = r35;
        r1 = r22;
        r2 = r20;
        r3 = r33;
        r0.updateField(r1, r2, r3);
        goto L_0x042f;
    L_0x043e:
        r4 = 4;
        if (r11 != r4) goto L_0x0449;
    L_0x0441:
        r4 = 11;
        r0 = r35;
        r0.searchClicked(r4);
        return;
    L_0x0449:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.AddHomeWorkActivity.onCreate(android.os.Bundle):void");
    }

    public void onResume() throws  {
        super.onResume();
        if (this.mEditHome) {
            this.mEditHomeListener.onClick(findViewById(C1283R.id.addHomeWorkHomeDD));
            this.mEditHome = false;
            this.mSkipOnReturn = true;
        }
        if (this.mEditWork) {
            this.mEditWorkListener.onClick(findViewById(C1283R.id.addHomeWorkWorkDD));
            this.mEditWork = false;
            this.mSkipOnReturn = true;
        }
    }

    private boolean homeWorkProblem() throws  {
        if (!this.mCarpool && !this.mOnboarding) {
            return false;
        }
        if (this.mProblemHome || this.mProblemWork) {
            return true;
        }
        if (this.aiHome == null || this.aiWork == null) {
            return true;
        }
        if ((this.aiHome.getAddress() == null || this.aiHome.getAddress().isEmpty()) && (this.aiHome.getTitle() == null || this.aiHome.getTitle().isEmpty())) {
            return true;
        }
        if (this.aiWork.getAddress() == null || this.aiWork.getAddress().isEmpty()) {
            return this.aiWork.getTitle() == null || this.aiWork.getTitle().isEmpty();
        } else {
            return false;
        }
    }

    private void checkConfirmEnablement() throws  {
        DisplayUtils.setButtonEnabled((TextView) findViewById(C1283R.id.addHomeWorkConfirm), !homeWorkProblem());
    }

    private void setupCarpoolExplanation() throws  {
        WazeTextView $r3 = (WazeTextView) findViewById(C1283R.id.addHomeWorkExplanation);
        if (this.mUserData.inferredHomeConflict() || this.mUserData.inferredWorkConflict()) {
            $r3.setText(this.mNatMgr.getLanguageString(1690));
        } else {
            $r3.setText(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_HOME_WORK_WIZ_EXPLANATION_CARPOOL));
        }
        this.mCpMgr.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            setupCarpoolExplanation();
            getHomeAndWork();
        }
        return super.myHandleMessage($r1);
    }

    protected void onDestroy() throws  {
        reportAnalyticsHomeWork(false);
        if (this.mCarpool) {
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        }
        super.onDestroy();
    }

    public void onBackPressed() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_CLICK).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM).addParam("HOME", this.homeEdited ? "EDIT" : "NONE").addParam("WORK", this.workEdited ? "EDIT" : "NONE").send();
        if (this.mOnboarding) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.getNextCb);
        }
        super.onBackPressed();
    }

    private void updateField(WazeSettingsView $r1, AddressItem $r2, LocationType $r3) throws  {
        if ($r2 == null) {
            $r1.setKeyText(DisplayStrings.displayString($r3.addTitleDs));
            String $r4 = this.mNatMgr.getLanguageString($r3.addDs);
            String $r6 = $r4;
            if ($r4.isEmpty()) {
                $r6 = null;
            }
            $r1.setValueText($r6);
            $r1.setKeyColor(getResources().getColor(C1283R.color.ElectricBlue));
            $r1.setValueColor(getResources().getColor(C1283R.color.ElectricBlue));
            $r1.setSelectorImage(0);
            return;
        }
        $r1.setIcon(getResources().getDrawable($r3.iconResource));
        $r1.setKeyText(DisplayStrings.displayString($r3.titleDs));
        $r1.setValueColor(getResources().getColor(C1283R.color.setting_value));
        $r1.setKeyColor(getResources().getColor(C1283R.color.setting_value));
        $r1.setSelectorImage(C1283R.drawable.list_edit_icon);
        if (!$r2.getAddress().isEmpty()) {
            $r1.setValueText($r2.getAddress());
        } else if ($r2.getSecondaryTitle().isEmpty()) {
            $r1.setValueText($r2.getTitle());
        } else {
            $r1.setValueText($r2.getSecondaryTitle());
        }
    }

    private void resolveConflict(WazeSettingsView $r1, AddressItem $r2, LocationType $r3) throws  {
        updateField($r1, $r2, $r3);
        if ($r3 == LocationType.WORK_CARPOOL) {
            this.mProblemWork = false;
            $r1.setIcon(C1283R.drawable.list_icon_work);
        } else {
            this.mProblemHome = false;
            $r1.setIcon(C1283R.drawable.list_icon_home);
        }
        checkConfirmEnablement();
    }

    private void getHomeAndWork() throws  {
        if (this.mCarpool) {
            final NativeManager $r1 = NativeManager.getInstance();
            if (this.mUserData.inferred_home != null) {
                this.aiHome = this.mUserData.inferred_home.toAddressItem();
                updateField(this.mHome, this.aiHome, LocationType.HOME_CARPOOL);
                if (this.mUserData.inferredHomeConflict()) {
                    if (this.aiHome.getAddress().isEmpty()) {
                        this.mHome.setKeyText(this.aiHome.getTitle());
                    } else {
                        this.mHome.setKeyText(this.aiHome.getAddress());
                    }
                    this.mHome.setValueText(this.mNatMgr.getLanguageString(LocationType.HOME_CARPOOL.resolveDs));
                    this.mHome.setIcon(C1283R.drawable.places_invalid_entry);
                    this.mHome.setOnClickListener(new OnClickListener() {
                        public void onClick(View $r1) throws  {
                            AddHomeWorkActivity.this.openHomeConflictDialog($r1, $r1);
                        }
                    });
                } else if (this.mUserData.inferred_home.address == null || this.mUserData.inferred_home.address.isEmpty()) {
                    this.mHome.setIcon(C1283R.drawable.places_invalid_entry);
                }
            }
            if (this.mUserData.inferred_work != null) {
                this.aiWork = this.mUserData.inferred_work.toAddressItem();
                updateField(this.mWork, this.aiWork, LocationType.WORK_CARPOOL);
                if (this.mUserData.inferredWorkConflict()) {
                    if (this.aiWork.getAddress().isEmpty()) {
                        this.mWork.setKeyText(this.aiWork.getTitle());
                    } else {
                        this.mWork.setKeyText(this.aiWork.getAddress());
                    }
                    this.mWork.setValueText(this.mNatMgr.getLanguageString(LocationType.WORK_CARPOOL.resolveDs));
                    this.mWork.setIcon(C1283R.drawable.places_invalid_entry);
                    this.mWork.setOnClickListener(new OnClickListener() {
                        public void onClick(View $r1) throws  {
                            AddHomeWorkActivity.this.openWorkConflictDialog($r1, $r1);
                        }
                    });
                } else if (this.mUserData.inferred_work.address == null || this.mUserData.inferred_work.address.isEmpty()) {
                    this.mWork.setIcon(C1283R.drawable.places_invalid_entry);
                }
            }
            checkConfirmEnablement();
            return;
        }
        if (this.aiHome == null) {
            this.mDtnMgr.getHome(new C20648());
        }
        if (this.aiWork == null) {
            this.mDtnMgr.getWork(new C20659());
        }
    }

    private void openHomeConflictDialog(View $r1, NativeManager $r2) throws  {
        String[] $r4 = new String[3];
        int $i0 = 0;
        if (this.mUserData.inferred_home.isValid()) {
            $r4[0] = getDescription(this.mUserData.inferred_home);
            $i0 = 0 + 1;
        }
        if (this.mUserData.inferred_home_other.isValid()) {
            $r4[$i0] = getDescription(this.mUserData.inferred_home_other);
            $i0++;
        }
        $r4[$i0] = $r2.getLanguageString((int) DisplayStrings.DS_HOME_WORK_WIZ_ANOTHER_ADDRESS);
        $i0++;
        if ($i0 < $r4.length) {
            $r4 = (String[]) Arrays.copyOf($r4, $i0);
        }
        final View view = $r1;
        SettingsUtils.showSubmenuStyle(this, $r2.getLanguageString((int) DisplayStrings.DS_HOME_WORK_WIZ_CONFLICT_TITLE_HOME), $r4, -1, new SettingsDialogListener() {
            public void onComplete(int $i0) throws  {
                if ($i0 == 0) {
                    AddHomeWorkActivity.this.aiHome = AddHomeWorkActivity.this.fixAddressItem(AddHomeWorkActivity.this.mUserData.inferred_home.toAddressItem(), true);
                    AddHomeWorkActivity.this.resolveConflict(AddHomeWorkActivity.this.mHome, AddHomeWorkActivity.this.aiHome, LocationType.HOME_CARPOOL);
                } else if ($i0 == 1) {
                    AddHomeWorkActivity.this.aiHome = AddHomeWorkActivity.this.fixAddressItem(AddHomeWorkActivity.this.mUserData.inferred_home_other.toAddressItem(), true);
                    AddHomeWorkActivity.this.resolveConflict(AddHomeWorkActivity.this.mHome, AddHomeWorkActivity.this.aiHome, LocationType.HOME_CARPOOL);
                } else {
                    AddHomeWorkActivity.this.mEditHomeListener.onClick(view);
                }
            }
        }, C1283R.style.CustomPopupLargeText);
    }

    private void openWorkConflictDialog(View $r1, NativeManager $r2) throws  {
        String[] $r4 = new String[3];
        int $i0 = 0;
        if (this.mUserData.inferred_work.isValid()) {
            $r4[0] = getDescription(this.mUserData.inferred_work);
            $i0 = 0 + 1;
        }
        if (this.mUserData.inferred_work_other.isValid()) {
            $r4[$i0] = getDescription(this.mUserData.inferred_work_other);
            $i0++;
        }
        $r4[$i0] = $r2.getLanguageString((int) DisplayStrings.DS_HOME_WORK_WIZ_ANOTHER_ADDRESS);
        $i0++;
        if ($i0 < $r4.length) {
            $r4 = (String[]) Arrays.copyOf($r4, $i0);
        }
        final View view = $r1;
        SettingsUtils.showSubmenuStyle(this, $r2.getLanguageString((int) DisplayStrings.DS_HOME_WORK_WIZ_CONFLICT_TITLE_WORK), $r4, -1, new SettingsDialogListener() {
            public void onComplete(int $i0) throws  {
                if ($i0 == 0) {
                    AddHomeWorkActivity.this.aiWork = AddHomeWorkActivity.this.fixAddressItem(AddHomeWorkActivity.this.mUserData.inferred_work.toAddressItem(), false);
                    AddHomeWorkActivity.this.resolveConflict(AddHomeWorkActivity.this.mWork, AddHomeWorkActivity.this.aiWork, LocationType.WORK_CARPOOL);
                } else if ($i0 == 1) {
                    AddHomeWorkActivity.this.aiWork = AddHomeWorkActivity.this.fixAddressItem(AddHomeWorkActivity.this.mUserData.inferred_work_other.toAddressItem(), false);
                    AddHomeWorkActivity.this.resolveConflict(AddHomeWorkActivity.this.mWork, AddHomeWorkActivity.this.aiWork, LocationType.WORK_CARPOOL);
                } else {
                    AddHomeWorkActivity.this.mEditWorkListener.onClick(view);
                }
            }
        }, C1283R.style.CustomPopupLargeText);
    }

    private AddressItem fixAddressItem(AddressItem $r1, boolean $z0) throws  {
        $r1.setCategory(Integer.valueOf(1));
        if (!($r1.getTitle() == null || $r1.getTitle().isEmpty())) {
            $r1.setSecondaryTitle($r1.getTitle());
        }
        if ($z0) {
            $r1.setType(1);
            $r1.setTitle("Home");
            return $r1;
        }
        $r1.setType(3);
        $r1.setTitle("Work");
        return $r1;
    }

    private String getDescription(CarpoolLocation $r1) throws  {
        if ($r1.address == null || $r1.address.isEmpty()) {
            return "";
        }
        return $r1.address;
    }

    private void reportAnalyticsHomeWork(boolean $z0) throws  {
        if (this.mbGotWork && this.mbGotHome) {
            String $r5;
            String $r2 = this.aiHome != null ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F;
            String $r3 = this.aiWork != null ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F;
            if (this.mCarpool) {
                if (this.mUserData.inferredHomeConflict()) {
                    $r2 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFLICT;
                }
                if (this.mUserData.inferredWorkConflict()) {
                    $r3 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFLICT;
                }
            }
            if ($z0) {
                $r5 = AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_SHOWN;
            } else {
                $r5 = AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_SCREEN_LEFT;
            }
            Analytics.log($r5, "HOME|WORK", $r2 + "|" + $r3);
        }
    }

    public void searchClicked(int $i0) throws  {
        boolean $z0;
        Intent $r1 = new Intent(this, AutocompleteSearchActivity.class);
        if (this.mOnboarding || this.mCarpool) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        $r1.putExtra(PublicMacros.SKIP_PREVIEW, $z0);
        $r1.putExtra(PublicMacros.SEARCH_MODE, $i0);
        startActivityForResult($r1, 1001);
    }
}
