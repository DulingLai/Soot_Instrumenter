package com.waze.mywaze;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.FriendsActivity;
import com.waze.InboxActivity;
import com.waze.MoodManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.inbox.InboxNativeManager;
import com.waze.inbox.InboxNativeManager.InboxCountersHandler;
import com.waze.mywaze.MyWazeNativeManager.ISetUserUpdateResult;
import com.waze.mywaze.moods.MoodsActivity;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.profile.MyProfileActivity;
import com.waze.profile.TempUserProfileActivity;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository.ImageRepositoryListener;

public class MyWazeActivity extends ActivityBase implements ISetUserUpdateResult {
    protected static final int ACTIVITY_CODE = 1000;
    private boolean hasCarppolProfile;
    private WazeSettingsView mEmailView;
    private WazeSettingsView mFriendsView;
    private String mImageUrl = null;
    private MyWazeData mMyWazeData;
    private WazeSettingsView mSwInvisible;
    private NativeManager nativeManager = AppService.getNativeManager();

    class C19861 implements OnClickListener {
        C19861() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ME_ON_MAP).send();
            if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
                MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, TempUserProfileActivity.class), 0);
                return;
            }
            MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, MyProfileActivity.class), 0);
        }
    }

    class C19872 implements SettingsToggleCallback {
        C19872() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            MyWazeActivity.this.onOptionInvisible($z0);
        }
    }

    class C19883 implements OnClickListener {
        C19883() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_MOOD).send();
            MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, MoodsActivity.class), 0);
        }
    }

    class C19894 implements OnClickListener {
        C19894() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", "FRIENDS").send();
            MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, FriendsActivity.class), 0);
        }
    }

    class C19905 implements OnClickListener {
        C19905() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", "INBOX").send();
            MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, InboxActivity.class), 0);
        }
    }

    class C19916 implements OnClickListener {
        C19916() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_STORES).send();
            Intent $r2 = new Intent(MyWazeActivity.this, MyStoresActivity.class);
            $r2.putExtra("source", 1);
            MyWazeActivity.this.startActivityForResult($r2, 0);
        }
    }

    class C19927 implements OnClickListener {
        C19927() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_HOME_WORK).send();
            MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, AddHomeWorkActivity.class), 0);
        }
    }

    class C19938 implements OnClickListener {
        C19938() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", "GROUPS").send();
            if (AppService.isNetworkAvailable()) {
                MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, GroupsActivity.class), 0);
                return;
            }
            MsgBox.openMessageBox(MyWazeActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_NO_NETWORK_CONNECTION), MyWazeActivity.this.nativeManager.getLanguageString(252), false);
        }
    }

    class C19949 implements OnClickListener {
        C19949() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MY_WAZE_ITEM_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_SCORE).send();
            if (AppService.isNetworkAvailable()) {
                MyWazeActivity.this.startActivityForResult(new Intent(MyWazeActivity.this, ScoreboardActivity.class), 0);
                return;
            }
            MsgBox.openMessageBox(MyWazeActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_NO_NETWORK_CONNECTION), MyWazeActivity.this.nativeManager.getLanguageString(252), false);
        }
    }

    protected void onCreate(android.os.Bundle r53) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:19:0x0322
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
        r52 = this;
        r0 = r52;
        r1 = r53;
        super.onCreate(r1);
        r8 = 2130903270; // 0x7f0300e6 float:1.7413353E38 double:1.0528061003E-314;
        r0 = r52;
        r0.setContentView(r8);
        r9 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r0 = r52;
        r9.getMyWazeData(r0);
        r0 = r52;
        r0.updateInvisibility();
        r10 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r11 = r10.configIsOnNTV();
        if (r11 == 0) goto L_0x0326;
    L_0x0027:
        r10 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r11 = r10.hasCarpoolProfileNTV();
        if (r11 == 0) goto L_0x0326;
    L_0x0031:
        r11 = 1;
    L_0x0032:
        r0 = r52;
        r0.hasCarppolProfile = r11;
        r8 = 2131691167; // 0x7f0f069f float:1.9011398E38 double:1.053195373E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r14 = r12;
        r14 = (com.waze.settings.WazeSettingsView) r14;
        r13 = r14;
        r0 = r52;
        r0.mSwInvisible = r13;
        r9 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r11 = r9.GetInvisible();
        r0 = r52;
        r13 = r0.mSwInvisible;
        r13.setValue(r11);
        if (r11 == 0) goto L_0x0328;
    L_0x0058:
        r0 = r52;
        r0.invisibleData();
    L_0x005d:
        r0 = r52;
        r13 = r0.mSwInvisible;
        r15 = com.waze.NativeManager.getInstance();
        r8 = 68;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r0 = r52;
        r13 = r0.mSwInvisible;
        r17 = new com.waze.mywaze.MyWazeActivity$2;
        r0 = r17;
        r1 = r52;
        r0.<init>();
        r0 = r17;
        r13.setOnChecked(r0);
        r9 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r11 = r9.isGuestUserNTV();
        if (r11 == 0) goto L_0x034d;
    L_0x008c:
        r8 = 2131691170; // 0x7f0f06a2 float:1.9011404E38 double:1.0531953746E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r18 = r12;
        r18 = (com.waze.settings.WazeSettingsView) r18;
        r13 = r18;
        r8 = 817; // 0x331 float:1.145E-42 double:4.037E-321;
        r19 = com.waze.profile.TempUserProfileActivity.class;
        r20 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r21 = "MY_WAZE_ITEM_CLICKED";
        r22 = "ACTION";
        r23 = "ACCOUNT";
        r0 = r13;
        r1 = r52;
        r2 = r8;
        r3 = r19;
        r4 = r20;
        r5 = r21;
        r6 = r22;
        r7 = r23;
        r0.initDrillDownAnalytics(r1, r2, r3, r4, r5, r6, r7);
    L_0x00b8:
        r8 = 2131691177; // 0x7f0f06a9 float:1.9011418E38 double:1.053195378E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r25 = r12;
        r25 = (android.widget.TextView) r25;
        r24 = r25;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 70;
        r16 = r15.getLanguageString(r8);
        r0 = r24;
        r1 = r16;
        r0.setText(r1);
        r8 = 2131691175; // 0x7f0f06a7 float:1.9011414E38 double:1.053195377E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r26 = r12;
        r26 = (com.waze.settings.WazeSettingsView) r26;
        r13 = r26;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 711; // 0x2c7 float:9.96E-43 double:3.513E-321;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131690848; // 0x7f0f0560 float:1.9010751E38 double:1.0531952156E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r28 = r12;
        r28 = (com.waze.view.title.TitleBar) r28;
        r27 = r28;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 10;
        r16 = r15.getLanguageString(r8);
        r0 = r27;
        r1 = r52;
        r2 = r16;
        r0.init(r1, r2);
        r8 = 2131691172; // 0x7f0f06a4 float:1.9011408E38 double:1.0531953756E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r29 = r12;
        r29 = (com.waze.settings.WazeSettingsView) r29;
        r13 = r29;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 1166; // 0x48e float:1.634E-42 double:5.76E-321;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131691169; // 0x7f0f06a1 float:1.9011402E38 double:1.053195374E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r30 = r12;
        r30 = (com.waze.settings.WazeSettingsView) r30;
        r13 = r30;
        r0 = r52;
        r0.mFriendsView = r13;
        r0 = r52;
        r13 = r0.mFriendsView;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 3496; // 0xda8 float:4.899E-42 double:1.7273E-320;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131691168; // 0x7f0f06a0 float:1.90114E38 double:1.0531953737E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r31 = r12;
        r31 = (com.waze.settings.WazeSettingsView) r31;
        r13 = r31;
        r0 = r52;
        r0.mEmailView = r13;
        r0 = r52;
        r13 = r0.mEmailView;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 3495; // 0xda7 float:4.898E-42 double:1.727E-320;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r0 = r52;
        r0.setNumbers();
        r32 = com.waze.ConfigManager.getInstance();
        r8 = 409; // 0x199 float:5.73E-43 double:2.02E-321;
        r0 = r32;
        r11 = r0.getConfigValueBool(r8);
        r8 = 2131691173; // 0x7f0f06a5 float:1.901141E38 double:1.053195376E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        if (r11 == 0) goto L_0x0382;
    L_0x019e:
        r33 = 0;
    L_0x01a0:
        r0 = r33;
        r12.setVisibility(r0);
        r8 = 2131691173; // 0x7f0f06a5 float:1.901141E38 double:1.053195376E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r34 = r12;
        r34 = (com.waze.settings.WazeSettingsView) r34;
        r13 = r34;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 3460; // 0xd84 float:4.848E-42 double:1.7095E-320;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131691171; // 0x7f0f06a3 float:1.9011406E38 double:1.053195375E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r35 = r12;
        r35 = (com.waze.settings.WazeSettingsView) r35;
        r13 = r35;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 2353; // 0x931 float:3.297E-42 double:1.1625E-320;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131691175; // 0x7f0f06a7 float:1.9011414E38 double:1.053195377E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r36 = r12;
        r36 = (com.waze.settings.WazeSettingsView) r36;
        r13 = r36;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 711; // 0x2c7 float:9.96E-43 double:3.513E-321;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131691174; // 0x7f0f06a6 float:1.9011412E38 double:1.0531953766E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r37 = r12;
        r37 = (com.waze.settings.WazeSettingsView) r37;
        r13 = r37;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 995; // 0x3e3 float:1.394E-42 double:4.916E-321;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131691176; // 0x7f0f06a8 float:1.9011416E38 double:1.0531953776E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r38 = r12;
        r38 = (com.waze.settings.WazeSettingsView) r38;
        r13 = r38;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 1059; // 0x423 float:1.484E-42 double:5.23E-321;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r8 = 2131691172; // 0x7f0f06a4 float:1.9011408E38 double:1.0531953756E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r39 = new com.waze.mywaze.MyWazeActivity$3;
        r0 = r39;
        r1 = r52;
        r0.<init>();
        r0 = r39;
        r12.setOnClickListener(r0);
        r0 = r52;
        r13 = r0.mFriendsView;
        r40 = new com.waze.mywaze.MyWazeActivity$4;
        r0 = r40;
        r1 = r52;
        r0.<init>();
        r0 = r40;
        r13.setOnClickListener(r0);
        r0 = r52;
        r13 = r0.mEmailView;
        r41 = new com.waze.mywaze.MyWazeActivity$5;
        r0 = r41;
        r1 = r52;
        r0.<init>();
        r0 = r41;
        r13.setOnClickListener(r0);
        r8 = 2131691173; // 0x7f0f06a5 float:1.901141E38 double:1.053195376E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r42 = new com.waze.mywaze.MyWazeActivity$6;
        r0 = r42;
        r1 = r52;
        r0.<init>();
        r0 = r42;
        r12.setOnClickListener(r0);
        r8 = 2131691171; // 0x7f0f06a3 float:1.9011406E38 double:1.053195375E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r43 = new com.waze.mywaze.MyWazeActivity$7;
        r0 = r43;
        r1 = r52;
        r0.<init>();
        r0 = r43;
        r12.setOnClickListener(r0);
        r9 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r11 = r9.GroupsEnabledNTV();
        if (r11 != 0) goto L_0x0385;
    L_0x02ae:
        r8 = 2131691175; // 0x7f0f06a7 float:1.9011414E38 double:1.053195377E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r8 = 8;
        r12.setVisibility(r8);
    L_0x02bc:
        r8 = 2131691174; // 0x7f0f06a6 float:1.9011412E38 double:1.0531953766E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r44 = r12;
        r44 = (com.waze.settings.WazeSettingsView) r44;
        r13 = r44;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 995; // 0x3e3 float:1.394E-42 double:4.916E-321;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r45 = new com.waze.mywaze.MyWazeActivity$9;
        r0 = r45;
        r1 = r52;
        r0.<init>();
        r0 = r45;
        r13.setOnClickListener(r0);
        r8 = 2131691176; // 0x7f0f06a8 float:1.9011416E38 double:1.0531953776E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r46 = r12;
        r46 = (com.waze.settings.WazeSettingsView) r46;
        r13 = r46;
        r0 = r52;
        r15 = r0.nativeManager;
        r8 = 1059; // 0x423 float:1.484E-42 double:5.23E-321;
        r16 = r15.getLanguageString(r8);
        r0 = r16;
        r13.setText(r0);
        r47 = new com.waze.mywaze.MyWazeActivity$10;
        r0 = r47;
        r1 = r52;
        r0.<init>();
        r0 = r47;
        r13.setOnClickListener(r0);
        r21 = "MY_WAZE_SHOWN";
        r0 = r21;
        r48 = com.waze.analytics.AnalyticsBuilder.analytics(r0);
        r0 = r48;
        r0.send();
        return;
        goto L_0x0326;
    L_0x0323:
        goto L_0x0032;
    L_0x0326:
        r11 = 0;
        goto L_0x0323;
    L_0x0328:
        r0 = r52;
        r0.updateData();
        goto L_0x0331;
    L_0x032e:
        goto L_0x02bc;
    L_0x0331:
        r8 = 2131691150; // 0x7f0f068e float:1.9011364E38 double:1.053195365E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r49 = new com.waze.mywaze.MyWazeActivity$1;
        r0 = r49;
        r1 = r52;
        r0.<init>();
        goto L_0x0347;
    L_0x0344:
        goto L_0x005d;
    L_0x0347:
        r0 = r49;
        r12.setOnClickListener(r0);
        goto L_0x0344;
    L_0x034d:
        r8 = 2131691170; // 0x7f0f06a2 float:1.9011404E38 double:1.0531953746E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r50 = r12;
        r50 = (com.waze.settings.WazeSettingsView) r50;
        r13 = r50;
        goto L_0x0360;
    L_0x035d:
        goto L_0x00b8;
    L_0x0360:
        r8 = 817; // 0x331 float:1.145E-42 double:4.037E-321;
        r19 = com.waze.profile.MyProfileActivity.class;
        r20 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r21 = "MY_WAZE_ITEM_CLICKED";
        r22 = "ACTION";
        r23 = "ACCOUNT";
        r0 = r13;
        r1 = r52;
        r2 = r8;
        r3 = r19;
        r4 = r20;
        r5 = r21;
        r6 = r22;
        r7 = r23;
        r0.initDrillDownAnalytics(r1, r2, r3, r4, r5, r6, r7);
        goto L_0x035d;
        goto L_0x0382;
    L_0x037f:
        goto L_0x01a0;
    L_0x0382:
        r33 = 8;
        goto L_0x037f;
    L_0x0385:
        r8 = 2131691175; // 0x7f0f06a7 float:1.9011414E38 double:1.053195377E-314;
        r0 = r52;
        r12 = r0.findViewById(r8);
        r51 = new com.waze.mywaze.MyWazeActivity$8;
        r0 = r51;
        r1 = r52;
        r0.<init>();
        r0 = r51;
        r12.setOnClickListener(r0);
        goto L_0x032e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.mywaze.MyWazeActivity.onCreate(android.os.Bundle):void");
    }

    private void setNumbers() throws  {
        InboxNativeManager.getInstance().getInboxCounters(new InboxCountersHandler() {
            public void onCounters(int $i0, int unread, int total) throws  {
                if ($i0 == 1) {
                    MyWazeActivity.this.mEmailView.setValueText(DisplayStrings.displayString(DisplayStrings.DS_MY_WAZE_ONE_NEW_MESSAGE));
                } else if ($i0 > 1) {
                    MyWazeActivity.this.mEmailView.setValueText(String.format(DisplayStrings.displayString(DisplayStrings.DS_MY_WAZE_NEW_MESSAGES_PD), new Object[]{Integer.valueOf($i0)}));
                } else if ($i0 < 1) {
                    MyWazeActivity.this.mEmailView.setValueText(null);
                }
            }
        });
        int $i0 = MyWazeNativeManager.getInstance().getNumberOfFriendsOnline();
        if ($i0 == 1) {
            this.mFriendsView.setValueText(DisplayStrings.displayString(3500));
        } else if ($i0 > 1) {
            this.mFriendsView.setValueText(String.format(DisplayStrings.displayString(DisplayStrings.DS_MY_WAZE_FRIENDS_ONLINE_PD), new Object[]{Integer.valueOf($i0)}));
        } else if ($i0 < 1) {
            this.mFriendsView.setValueText(null);
        }
    }

    protected void onDestroy() throws  {
        super.onDestroy();
    }

    private void goToMyProfile() throws  {
        if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
            startActivityForResult(new Intent(this, TempUserProfileActivity.class), 0);
        } else {
            startActivityForResult(new Intent(this, MyProfileActivity.class), 0);
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        } else if ($i1 == 4) {
            MyWazeNativeManager.getInstance().getMyWazeData((ActivityBase) this);
            SetMoodInView();
        } else {
            updateInvisibility();
            super.onActivityResult($i0, $i1, $r1);
        }
        setNumbers();
    }

    private void updateInvisibility() throws  {
    }

    public void onResume() throws  {
        super.onResume();
        updateImage();
    }

    public void SetMoodInView() throws  {
    }

    public void SetUserUpdateResult(boolean bIsSuccess) throws  {
        MyWazeNativeManager.getInstance().getMyWazeData((ActivityBase) this);
    }

    public void finish() throws  {
        super.finish();
        overridePendingTransition(C1283R.anim.stack_up, C1283R.anim.slide_down_bottom);
    }

    private void onOptionInvisible(boolean $z0) throws  {
        if ($z0) {
            findViewById(C1283R.id.youOnMapFrame).setVisibility(8);
            MyWazeNativeManager.getInstance().setInvisible($z0);
            invisibleData();
            return;
        }
        if (!MyWazeNativeManager.getInstance().isGuestUserNTV()) {
            findViewById(C1283R.id.youOnMapFrame).setVisibility(0);
        }
        MyWazeNativeManager.getInstance().setInvisible($z0);
        updateData();
    }

    public void setMood() throws  {
        MoodManager $r1 = MoodManager.getInstance();
        ((ImageView) findViewById(C1283R.id.youOnMapMood)).setImageDrawable(MoodManager.getBigMoodDrawble(this, $r1.getWazerMood()));
        int $i0 = $r1.getBigAddonDrawble(this);
        if ($i0 == 0) {
            ((ImageView) findViewById(C1283R.id.youOnMapAddOn)).setImageDrawable(null);
        } else {
            ((ImageView) findViewById(C1283R.id.youOnMapAddOn)).setImageResource($i0);
        }
    }

    public void setInvisibleMood() throws  {
        ((ImageView) findViewById(C1283R.id.youOnMapMood)).setImageResource(C1283R.drawable.invisible);
        ((ImageView) findViewById(C1283R.id.youOnMapAddOn)).setImageDrawable(null);
    }

    public void SetMyWazeData(MyWazeData $r1) throws  {
        this.mMyWazeData = $r1;
        this.mImageUrl = this.mMyWazeData.mImageUrl;
        updateData();
    }

    private void updateData() throws  {
        MyWazeData $r1 = this.mMyWazeData;
        this = this;
        if ($r1 != null) {
            ((TextView) findViewById(C1283R.id.youOnMapName)).setText(this.mMyWazeData.mFirstName + " " + this.mMyWazeData.mLastName);
            TextView $r3 = (TextView) findViewById(C1283R.id.youOnMapShown);
            if (this.hasCarppolProfile) {
                $r3.setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_YOU_MAP_SHOWN_TO_RW));
            } else {
                $r3.setText(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_YOU_MAP_SHOWN_TO));
            }
            if (!MyWazeNativeManager.getInstance().isGuestUserNTV()) {
                $r3.setVisibility(0);
            }
            $r3 = (TextView) findViewById(C1283R.id.youOnMapNick);
            $r3.setVisibility(0);
            $r3.setText(this.mMyWazeData.mUserName);
            $r3 = (TextView) findViewById(C1283R.id.youOnMapJoined);
            $r3.setVisibility(0);
            $r3.setText(this.mMyWazeData.mJoinedStr);
            $r3 = (TextView) findViewById(C1283R.id.youOnMapSpeed);
            $r3.setVisibility(0);
            this = this;
            $r3.setText(this.mMyWazeData.mSpeedStr);
            if (!MyWazeNativeManager.getInstance().isGuestUserNTV()) {
                findViewById(C1283R.id.youOnMapSepLine).setVisibility(0);
            }
            findViewById(C1283R.id.youOnMapSepEye).setVisibility(8);
            $r3 = (TextView) findViewById(C1283R.id.youOnMapPointsRank);
            String $r5 = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_YOU_MAP_POINTS_DP_RANK_DP);
            Object[] $r8 = new Object[1];
            int $i0 = this.mMyWazeData.mPts;
            $r8[0] = Integer.valueOf($i0);
            $r3.setText(String.format($r5, $r8));
            setMood();
            updateImage();
            if (MyWazeNativeManager.getInstance().GetInvisible()) {
                invisibleData();
            }
        }
    }

    private void invisibleData() throws  {
        ((TextView) findViewById(C1283R.id.youOnMapName)).setText(NativeManager.getInstance().getLanguageString(72));
        findViewById(C1283R.id.youOnMapShown).setVisibility(8);
        findViewById(C1283R.id.youOnMapNick).setVisibility(8);
        findViewById(C1283R.id.youOnMapJoined).setVisibility(8);
        findViewById(C1283R.id.youOnMapSpeed).setVisibility(8);
        findViewById(C1283R.id.youOnMapSepLine).setVisibility(8);
        findViewById(C1283R.id.youOnMapSepEye).setVisibility(0);
        ((TextView) findViewById(C1283R.id.youOnMapPointsRank)).setText(NativeManager.getInstance().getLanguageString(71));
        setInvisibleMood();
        findViewById(C1283R.id.youOnMapFrame).setVisibility(8);
    }

    public void updateImage() throws  {
        final ImageView $r4 = (ImageView) findViewById(C1283R.id.youOnMapImage);
        if (this.mImageUrl != null && this.mImageUrl.length() > 0) {
            ImageRepository.instance.getImage(this.mImageUrl, new ImageRepositoryListener() {
                public void onImageRetrieved(final Bitmap $r1) throws  {
                    MyWazeActivity.this.post(new Runnable() {
                        public void run() throws  {
                            if ($r1 != null) {
                                $r4.setImageDrawable(new CircleShaderDrawable($r1, 0));
                            }
                        }
                    });
                }
            });
        }
    }
}
