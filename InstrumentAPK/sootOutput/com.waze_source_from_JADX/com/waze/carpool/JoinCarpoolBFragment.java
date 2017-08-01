package com.waze.carpool;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.settings.SettingsNotificationActivity;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.WazeTextView;
import com.waze.view.web.SimpleWebActivity;

public final class JoinCarpoolBFragment extends Fragment {
    public static String CARPOOL_AB_TESTING_ONBOARDING_JOIN_NA = "NOT_RECEIVED";
    public static String CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_LIST = "LIST_BUTTON";
    public static String CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_REGULAR = "ANIMATION_BUTTON";
    public static String CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_SETTINGS = "SETTINGS";
    public static String CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_SETTINGS_LIST = "SETTINGS_LIST";
    boolean mCalledFromPush = false;
    private OnboardingLayoutOption mLayoutOption;
    private NativeManager mNatMgr;
    int mNumRides = 0;
    private OnClickListener mOnJoinedClicked;
    WazeSettingsView mToggle = null;
    CarpoolOnboardingManager obManager;

    class C16451 implements OnClickListener {
        C16451() throws  {
        }

        public void onClick(View v) throws  {
            JoinCarpoolBFragment.this.joinRequested();
        }
    }

    class C16462 implements OnClickListener {
        C16462() throws  {
        }

        public void onClick(View view) throws  {
            String $r4 = ConfigManager.getInstance().getConfigValueString(21);
            Intent $r2 = new Intent(AppService.getAppContext(), SimpleWebActivity.class);
            $r2.putExtra(SimpleWebActivity.EXTRA_URL, $r4);
            $r2.putExtra(SimpleWebActivity.EXTRA_TITLE, DisplayStrings.displayString(DisplayStrings.DS_LEARN_MORE));
            JoinCarpoolBFragment.this.startActivity($r2);
        }
    }

    class C16473 implements OnClickListener {
        C16473() throws  {
        }

        public void onClick(View view) throws  {
            AppService.getActiveActivity().startActivityForResult(new Intent(AppService.getActiveActivity(), SettingsNotificationActivity.class), 0);
        }
    }

    class C16484 implements SettingsToggleCallback {
        C16484() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            if ($z0) {
                JoinCarpoolBFragment.this.joinRequested();
            }
        }
    }

    class C16495 implements INextActionCallback {
        C16495() throws  {
        }

        public void setNextIntent(Intent $r1) throws  {
            if ($r1 != null) {
                JoinCarpoolBFragment.this.startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
            } else {
                Logger.m38e("JoinCarpoolBFragment: received null intent");
            }
        }

        public void setNextFragment(Fragment $r1) throws  {
            AppService.getMainActivity().getLayoutMgr().getRightSideMenu().replaceCarpoolFragment($r1);
        }

        public void setNextResult(int $i0) throws  {
            if ($i0 == -1) {
                Logger.m41i("CarpoolRidesFragment: received RESULT OK");
            } else {
                Logger.m38e("JoinCarpoolBFragment: received unexpected setNextResult");
            }
        }

        public Context getContext() throws  {
            return AppService.getMainActivity();
        }
    }

    class C16506 implements OnClickListener {
        C16506() throws  {
        }

        public void onClick(View view) throws  {
            JoinCarpoolBFragment.this.showGridSubmenu();
        }
    }

    public enum OnboardingLayoutOption {
        regular,
        listButton,
        settings,
        settingsList
    }

    @android.support.annotation.Nullable
    public android.view.View onCreateView(android.view.LayoutInflater r52, android.view.ViewGroup r53, android.os.Bundle r54) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:29:0x035f
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
        r51 = this;
        r3 = com.waze.carpool.CarpoolOnboardingManager.getInstance();
        r0 = r51;
        r0.obManager = r3;
        r4 = com.waze.NativeManager.getInstance();
        r0 = r51;
        r0.mNatMgr = r4;
        r0 = r51;
        r54 = r0.getArguments();
        if (r54 == 0) goto L_0x0032;
    L_0x0018:
        r6 = "num_rides";
        r7 = 0;
        r0 = r54;
        r5 = r0.getInt(r6, r7);
        r0 = r51;
        r0.mNumRides = r5;
        r6 = "called_from_push";
        r7 = 0;
        r0 = r54;
        r8 = r0.getBoolean(r6, r7);
        r0 = r51;
        r0.mCalledFromPush = r8;
    L_0x0032:
        r9 = new com.waze.carpool.JoinCarpoolBFragment$1;
        r0 = r51;
        r9.<init>();
        r0 = r51;
        r0.mOnJoinedClicked = r9;
        r0 = r51;
        r5 = r0.mNumRides;
        if (r5 != 0) goto L_0x036d;
    L_0x0043:
        r10 = com.waze.ConfigManager.getInstance();
        r7 = 38;
        r11 = r10.getConfigValueString(r7);
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_LIST;
        r8 = r11.equalsIgnoreCase(r12);
        if (r8 == 0) goto L_0x0133;
    L_0x0055:
        r13 = com.waze.carpool.JoinCarpoolBFragment.OnboardingLayoutOption.listButton;
        r0 = r51;
        r0.mLayoutOption = r13;
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_LIST;
        r7 = 2130903231; // 0x7f0300bf float:1.7413274E38 double:1.052806081E-314;
        r15 = 0;
        r0 = r52;
        r1 = r53;
        r14 = r0.inflate(r7, r1, r15);
        r16 = r14;
        r7 = 2131690763; // 0x7f0f050b float:1.9010579E38 double:1.0531951736E-314;
        r17 = r14.findViewById(r7);
        r19 = r17;
        r19 = (com.waze.view.text.WazeTextView) r19;
        r18 = r19;
        r7 = 2959; // 0xb8f float:4.146E-42 double:1.462E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        r0 = r51;
        r0 = r0.mOnJoinedClicked;
        r21 = r0;
        r0 = r18;
        r1 = r21;
        r0.setOnClickListener(r1);
        r7 = 2131690756; // 0x7f0f0504 float:1.9010565E38 double:1.05319517E-314;
        r17 = r14.findViewById(r7);
        r22 = r17;
        r22 = (com.waze.view.text.WazeTextView) r22;
        r18 = r22;
        r7 = 2935; // 0xb77 float:4.113E-42 double:1.45E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        r7 = 2131690757; // 0x7f0f0505 float:1.9010567E38 double:1.0531951706E-314;
        r17 = r14.findViewById(r7);
        r23 = r17;
        r23 = (com.waze.view.text.WazeTextView) r23;
        r18 = r23;
        r7 = 2936; // 0xb78 float:4.114E-42 double:1.4506E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        r0 = r51;
        r0.setupListItems(r14);
    L_0x00cb:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r6 = "JoinCarpoolBFragment: AB testing join screen val= ";
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r24;
        r24 = r0.append(r11);
        r6 = "; enum=";
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r51;
        r13 = r0.mLayoutOption;
        r0 = r24;
        r24 = r0.append(r13);
        r0 = r24;
        r20 = r0.toString();
        r0 = r20;
        com.waze.Logger.m36d(r0);
        r20 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_NA;
        r0 = r20;
        r8 = r11.equalsIgnoreCase(r0);
        if (r8 == 0) goto L_0x0363;
    L_0x0107:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r6 = "JoinCarpoolBFragment: AB testing value: ";
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r24;
        r24 = r0.append(r11);
        r6 = " means did not receive AB testing value from server; Not sending stat";
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r24;
        r11 = r0.toString();
        com.waze.Logger.m43w(r11);
    L_0x012d:
        r0 = r51;
        r0.reportAnalytics();
        return r16;
    L_0x0133:
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_SETTINGS;
        r8 = r11.equalsIgnoreCase(r12);
        if (r8 == 0) goto L_0x0211;
    L_0x013b:
        r7 = 2130903232; // 0x7f0300c0 float:1.7413276E38 double:1.0528060815E-314;
        r15 = 0;
        r0 = r52;
        r1 = r53;
        r14 = r0.inflate(r7, r1, r15);
        r16 = r14;
        r13 = com.waze.carpool.JoinCarpoolBFragment.OnboardingLayoutOption.settings;
        r0 = r51;
        r0.mLayoutOption = r13;
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_SETTINGS;
        r7 = 2131690896; // 0x7f0f0590 float:1.9010849E38 double:1.0531952393E-314;
        r17 = r14.findViewById(r7);
        r25 = r17;
        r25 = (com.waze.view.text.WazeTextView) r25;
        r18 = r25;
        r0 = r51;
        r1 = r18;
        r0.setupRiderLink(r1);
        r7 = 2937; // 0xb79 float:4.116E-42 double:1.451E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r7 = 2938; // 0xb7a float:4.117E-42 double:1.4516E-320;
        r26 = com.waze.strings.DisplayStrings.displayString(r7);
        r27 = new android.text.SpannableString;
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r0 = r24;
        r1 = r20;
        r24 = r0.append(r1);
        r0 = r24;
        r1 = r26;
        r24 = r0.append(r1);
        r0 = r24;
        r28 = r0.toString();
        r0 = r27;
        r1 = r28;
        r0.<init>(r1);
        r29 = new android.text.style.UnderlineSpan;
        r0 = r29;
        r0.<init>();
        r0 = r20;
        r5 = r0.length();
        r0 = r20;
        r30 = r0.length();
        r0 = r26;
        r31 = r0.length();
        r0 = r30;
        r1 = r31;
        r0 = r0 + r1;
        r30 = r0;
        r7 = 0;
        r0 = r27;
        r1 = r29;
        r2 = r30;
        r0.setSpan(r1, r5, r2, r7);
        r7 = 2131690910; // 0x7f0f059e float:1.9010877E38 double:1.053195246E-314;
        r17 = r14.findViewById(r7);
        r7 = 8;
        r0 = r17;
        r0.setVisibility(r7);
        r7 = 2131690909; // 0x7f0f059d float:1.9010875E38 double:1.0531952457E-314;
        r17 = r14.findViewById(r7);
        r7 = 8;
        r0 = r17;
        r0.setVisibility(r7);
        r7 = 2131690908; // 0x7f0f059c float:1.9010873E38 double:1.053195245E-314;
        r17 = r14.findViewById(r7);
        r32 = r17;
        r32 = (com.waze.view.text.WazeTextView) r32;
        r18 = r32;
        r7 = 0;
        r0 = r18;
        r0.setVisibility(r7);
        r0 = r18;
        r1 = r27;
        r0.setText(r1);
        r33 = new com.waze.carpool.JoinCarpoolBFragment$2;
        r0 = r33;
        r1 = r51;
        r0.<init>();
        r0 = r18;
        r1 = r33;
        r0.setOnClickListener(r1);
        goto L_0x020b;
    L_0x0208:
        goto L_0x00cb;
    L_0x020b:
        r0 = r51;
        r0.setupToggle(r14);
        goto L_0x0208;
    L_0x0211:
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_SETTINGS_LIST;
        r8 = r11.equalsIgnoreCase(r12);
        if (r8 == 0) goto L_0x02a7;
    L_0x0219:
        r7 = 2130903232; // 0x7f0300c0 float:1.7413276E38 double:1.0528060815E-314;
        r15 = 0;
        r0 = r52;
        r1 = r53;
        r14 = r0.inflate(r7, r1, r15);
        r16 = r14;
        r13 = com.waze.carpool.JoinCarpoolBFragment.OnboardingLayoutOption.settingsList;
        r0 = r51;
        r0.mLayoutOption = r13;
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_SETTINGS_LIST;
        r7 = 2131690896; // 0x7f0f0590 float:1.9010849E38 double:1.0531952393E-314;
        r17 = r14.findViewById(r7);
        r34 = r17;
        r34 = (com.waze.view.text.WazeTextView) r34;
        r18 = r34;
        r0 = r51;
        r1 = r18;
        r0.setupRiderLink(r1);
        r7 = 2131690908; // 0x7f0f059c float:1.9010873E38 double:1.053195245E-314;
        r17 = r14.findViewById(r7);
        r7 = 8;
        r0 = r17;
        r0.setVisibility(r7);
        r7 = 2131690910; // 0x7f0f059e float:1.9010877E38 double:1.053195246E-314;
        r17 = r14.findViewById(r7);
        r7 = 0;
        r0 = r17;
        r0.setVisibility(r7);
        r7 = 2131690909; // 0x7f0f059d float:1.9010875E38 double:1.0531952457E-314;
        r17 = r14.findViewById(r7);
        r35 = r17;
        r35 = (com.waze.view.text.WazeTextView) r35;
        r18 = r35;
        r7 = 2939; // 0xb7b float:4.118E-42 double:1.452E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        r7 = 0;
        r0 = r18;
        r0.setVisibility(r7);
        r0 = r51;
        r0.setupListItems(r14);
        r7 = 2131690909; // 0x7f0f059d float:1.9010875E38 double:1.0531952457E-314;
        r17 = r14.findViewById(r7);
        r36 = r17;
        r36 = (com.waze.view.text.WazeTextView) r36;
        r18 = r36;
        r7 = 2934; // 0xb76 float:4.111E-42 double:1.4496E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        goto L_0x02a1;
    L_0x029e:
        goto L_0x00cb;
    L_0x02a1:
        r0 = r51;
        r0.setupToggle(r14);
        goto L_0x029e;
    L_0x02a7:
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_REGULAR;
        r8 = r11.equalsIgnoreCase(r12);
        if (r8 != 0) goto L_0x02d5;
    L_0x02af:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r6 = "JoinCarpoolBFragment: AB testing value: ";
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r24;
        r24 = r0.append(r11);
        r6 = " is not supported! Could be was not received in time from RT, using regular join screen";
        r0 = r24;
        r24 = r0.append(r6);
        r0 = r24;
        r12 = r0.toString();
        com.waze.Logger.m38e(r12);
    L_0x02d5:
        r12 = CARPOOL_AB_TESTING_ONBOARDING_JOIN_SCREEN_REGULAR;
        r13 = com.waze.carpool.JoinCarpoolBFragment.OnboardingLayoutOption.regular;
        r0 = r51;
        r0.mLayoutOption = r13;
        r7 = 2130903228; // 0x7f0300bc float:1.7413268E38 double:1.0528060796E-314;
        r15 = 0;
        r0 = r52;
        r1 = r53;
        r14 = r0.inflate(r7, r1, r15);
        r16 = r14;
        r7 = 2131690896; // 0x7f0f0590 float:1.9010849E38 double:1.0531952393E-314;
        r17 = r14.findViewById(r7);
        r37 = r17;
        r37 = (com.waze.view.text.WazeTextView) r37;
        r18 = r37;
        r0 = r51;
        r1 = r18;
        r0.setupRiderLink(r1);
        r7 = 2131690763; // 0x7f0f050b float:1.9010579E38 double:1.0531951736E-314;
        r17 = r14.findViewById(r7);
        r38 = r17;
        r38 = (com.waze.view.text.WazeTextView) r38;
        r18 = r38;
        r7 = 2960; // 0xb90 float:4.148E-42 double:1.4624E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        r0 = r51;
        r0 = r0.mOnJoinedClicked;
        r21 = r0;
        r0 = r18;
        r1 = r21;
        r0.setOnClickListener(r1);
        r7 = 2131690756; // 0x7f0f0504 float:1.9010565E38 double:1.05319517E-314;
        r17 = r14.findViewById(r7);
        r39 = r17;
        r39 = (com.waze.view.text.WazeTextView) r39;
        r18 = r39;
        r7 = 2933; // 0xb75 float:4.11E-42 double:1.449E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        r7 = 2131690757; // 0x7f0f0505 float:1.9010567E38 double:1.0531951706E-314;
        r14 = r14.findViewById(r7);
        r40 = r14;
        r40 = (com.waze.view.text.WazeTextView) r40;
        r18 = r40;
        r7 = 2934; // 0xb76 float:4.111E-42 double:1.4496E-320;
        r20 = com.waze.strings.DisplayStrings.displayString(r7);
        goto L_0x0357;
    L_0x0354:
        goto L_0x00cb;
    L_0x0357:
        r0 = r18;
        r1 = r20;
        r0.setText(r1);
        goto L_0x0354;
        goto L_0x0363;
    L_0x0360:
        goto L_0x012d;
    L_0x0363:
        r6 = "RW_ABT_ONOARDING_JOIN_SCREEN_LAYOUT";
        r41 = "VAUE";
        r0 = r41;
        com.waze.analytics.Analytics.log(r6, r0, r12);
        goto L_0x0360;
    L_0x036d:
        r7 = 2130903228; // 0x7f0300bc float:1.7413268E38 double:1.0528060796E-314;
        r15 = 0;
        r0 = r52;
        r1 = r53;
        r14 = r0.inflate(r7, r1, r15);
        r16 = r14;
        r7 = 2131690896; // 0x7f0f0590 float:1.9010849E38 double:1.0531952393E-314;
        r17 = r14.findViewById(r7);
        r42 = r17;
        r42 = (com.waze.view.text.WazeTextView) r42;
        r18 = r42;
        r7 = 2131690763; // 0x7f0f050b float:1.9010579E38 double:1.0531951736E-314;
        r17 = r14.findViewById(r7);
        r44 = r17;
        r44 = (com.waze.view.text.WazeTextView) r44;
        r43 = r44;
        r7 = 2967; // 0xb97 float:4.158E-42 double:1.466E-320;
        r11 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r43;
        r0.setText(r11);
        r0 = r51;
        r0 = r0.mOnJoinedClicked;
        r21 = r0;
        r0 = r43;
        r1 = r21;
        r0.setOnClickListener(r1);
        r7 = 2131690895; // 0x7f0f058f float:1.9010847E38 double:1.053195239E-314;
        r17 = r14.findViewById(r7);
        r46 = r17;
        r46 = (com.waze.view.button.BadgeButton) r46;
        r45 = r46;
        r0 = r51;
        r5 = r0.mNumRides;
        r11 = java.lang.Integer.toString(r5);
        r0 = r45;
        r0.setBadgeText(r11);
        r7 = 0;
        r0 = r45;
        r0.setVisibility(r7);
        r0 = r51;
        r47 = r0.getResources();
        r7 = 2131623946; // 0x7f0e000a float:1.8875058E38 double:1.0531621616E-314;
        r0 = r47;
        r5 = r0.getColor(r7);
        r0 = r45;
        r0.setBadgeBackgroundColor(r5);
        r7 = 2131690756; // 0x7f0f0504 float:1.9010565E38 double:1.05319517E-314;
        r17 = r14.findViewById(r7);
        r48 = r17;
        r48 = (com.waze.view.text.WazeTextView) r48;
        r43 = r48;
        r7 = 2957; // 0xb8d float:4.144E-42 double:1.461E-320;
        r11 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r43;
        r0.setText(r11);
        r7 = 2131690757; // 0x7f0f0505 float:1.9010567E38 double:1.0531951706E-314;
        r14 = r14.findViewById(r7);
        r49 = r14;
        r49 = (com.waze.view.text.WazeTextView) r49;
        r43 = r49;
        r7 = 2958; // 0xb8e float:4.145E-42 double:1.4614E-320;
        r11 = com.waze.strings.DisplayStrings.displayString(r7);
        r0 = r43;
        r0.setText(r11);
        r0 = r51;
        r8 = r0.mCalledFromPush;
        if (r8 == 0) goto L_0x0458;
    L_0x0417:
        r7 = 2966; // 0xb96 float:4.156E-42 double:1.4654E-320;
        r11 = com.waze.strings.DisplayStrings.displayString(r7);
        r27 = new android.text.SpannableString;
        r0 = r27;
        r0.<init>(r11);
        r29 = new android.text.style.UnderlineSpan;
        r0 = r29;
        r0.<init>();
        r5 = r11.length();
        r7 = 0;
        r15 = 0;
        r0 = r27;
        r1 = r29;
        r0.setSpan(r1, r7, r5, r15);
        r0 = r18;
        r1 = r27;
        r0.setText(r1);
        r50 = new com.waze.carpool.JoinCarpoolBFragment$3;
        r0 = r50;
        r1 = r51;
        r0.<init>();
        goto L_0x044c;
    L_0x0449:
        goto L_0x012d;
    L_0x044c:
        r0 = r18;
        r1 = r50;
        r0.setOnClickListener(r1);
        goto L_0x0449;
        goto L_0x0458;
    L_0x0455:
        goto L_0x012d;
    L_0x0458:
        r7 = 8;
        r0 = r18;
        r0.setVisibility(r7);
        goto L_0x0455;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.JoinCarpoolBFragment.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle):android.view.View");
    }

    private void setupToggle(View $r1) throws  {
        this.mToggle = (WazeSettingsView) $r1.findViewById(C1283R.id.JoinCarpoolSettings);
        this.mToggle.setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_SETTINGS_OPTION_TOGGLE_TEXT));
        this.mToggle.setOnChecked(new C16484());
    }

    private void joinRequested() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ONBOARDING_BECOME_DRIVER_CLICKED);
        this.obManager.getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, new C16495());
    }

    private void setupListItems(View $r1) throws  {
        ((WazeTextView) $r1.findViewById(C1283R.id.JoinCarpoolLine1Text)).setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_LIST_OPTION_ITEM1));
        ((WazeTextView) $r1.findViewById(C1283R.id.JoinCarpoolLine2Text)).setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_LIST_OPTION_ITEM2));
        ((WazeTextView) $r1.findViewById(C1283R.id.JoinCarpoolLine3Text)).setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_LIST_OPTION_ITEM3));
    }

    private void setupRiderLink(WazeTextView $r1) throws  {
        String $r3 = DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_BE_A_RIDER_BUTTON);
        SpannableString $r2 = new SpannableString($r3);
        $r2.setSpan(new UnderlineSpan(), 0, $r3.length(), 0);
        $r1.setText($r2);
        $r1.setOnClickListener(new C16506());
    }

    public void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == 0 && this.mToggle != null) {
            this.mToggle.setValue(false);
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    protected void showGridSubmenu() throws  {
        final BottomSheet $r1 = new BottomSheet(getActivity(), DisplayStrings.DS_JOIN_CARPOOL_BE_A_RIDER_TITLE, Mode.COLUMN_TEXT_ICON);
        $r1.setAdapter(new Adapter() {
            public int getCount() throws  {
                return 1;
            }

            public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                switch ($i0) {
                    case 0:
                        $r1.setItem(DisplayStrings.DS_JOIN_CARPOOL_GET_RIDER_APP_PAGE_TITLE, C1283R.drawable.quicklinks_rider_app);
                        return;
                    default:
                        return;
                }
            }

            public void onClick(int $i0) throws  {
                switch ($i0) {
                    case 0:
                        CarpoolUtils.openRiderApp(AppService.getMainActivity());
                        break;
                    default:
                        break;
                }
                $r1.dismiss();
            }
        });
        $r1.show();
    }

    void reportAnalytics() throws  {
        if (this.mNumRides > 0) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SEE_RIDE_REQUESTS_SHOWN);
        } else {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ONBOARDING_SHOWN);
        }
    }
}
