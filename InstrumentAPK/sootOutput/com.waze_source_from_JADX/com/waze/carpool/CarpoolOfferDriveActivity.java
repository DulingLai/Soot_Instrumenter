package com.waze.carpool;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.WazeSwitchView;
import com.waze.main.navigate.LocationData;
import com.waze.map.CanvasFont;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AutocompleteSearchActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.LocationDataListener;
import com.waze.navigate.PublicMacros;
import com.waze.push.Alerter;
import com.waze.reports.SimpleBottomSheet;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.title.TitleBar;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class CarpoolOfferDriveActivity extends ActivityBase {
    public static final long DAY_IN_MS = 86400000;
    private static final long NETWORK_TIMEOUT = 10000;
    private static final int RQ_SEARCH_FROM = 1001;
    private static final int RQ_SEARCH_TO = 1002;
    public static final int TIME_INTERVAL_MIN = (ConfigValues.getIntValue(61) / 60);
    private AddressItem aiFrom;
    private AddressItem aiTo;
    private SimpleBottomSheet mBottomSheet;
    private boolean mBuildingOfferMode = true;
    private CarpoolNativeManager mCpnm;
    private DateFormat mDayFormat;
    private CarpoolDrive mDrive;
    private DriveToNativeManager mDtnm;
    private boolean mIsFreeOffer = false;
    private NativeManager mNm;
    private TextView mReward1;
    private TextView mReward2;
    private DateFormat mTimeFormat;
    private Runnable mTimeoutRunnable;
    private TitleBar mTitleBar;
    private TextView mTvDaySelect;
    private TextView mTvFrom;
    private TextView mTvHourSelect;
    private TextView mTvTo;
    private int mWaitForAddress;
    private boolean mWaitingForUpdate;
    private int selectedDay;
    private int selectedHour;
    private int selectedMinute = 0;

    class C14751 implements SettingsToggleCallback {
        C14751() throws  {
        }

        public void onToggle(boolean $z0) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_ZERO_PRICE_TOGGLE).send();
            CarpoolOfferDriveActivity.this.setFreeOffer($z0);
        }
    }

    class C14762 implements OnClickListener {
        final /* synthetic */ WazeSwitchView val$switchView;

        C14762(WazeSwitchView $r2) throws  {
            this.val$switchView = $r2;
        }

        public void onClick(View v) throws  {
            this.val$switchView.toggle();
        }
    }

    class C14773 implements OnClickListener {
        C14773() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_FROM).send();
            CarpoolOfferDriveActivity.this.searchClicked(1001, DisplayStrings.DS_OFFER_RIDE_SELECT_FROM_TITLE);
        }
    }

    class C14784 implements OnClickListener {
        C14784() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_TO).send();
            CarpoolOfferDriveActivity.this.searchClicked(1002, DisplayStrings.DS_OFFER_RIDE_SELECT_TO_TITLE);
        }
    }

    class C14795 implements OnClickListener {
        C14795() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CANCEL_OFFER).send();
            CarpoolOfferDriveActivity.this.showCancelDialog();
        }
    }

    class C14806 implements OnClickListener {
        C14806() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_WHEN).send();
            CarpoolOfferDriveActivity.this.openSelectDayDialog();
        }
    }

    class C14817 implements OnClickListener {
        C14817() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_LEAVE_BY).send();
            CarpoolOfferDriveActivity.this.openSelectTimeDialog();
        }
    }

    class C14828 implements OnClickListener {
        C14828() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", "OFFER_RIDE").send();
            CarpoolOfferDriveActivity.this.confirmAndFinish();
        }
    }

    class C14839 implements OnClickListener {
        C14839() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", "CANCEL").send();
            CarpoolOfferDriveActivity.this.setResult(0);
            CarpoolOfferDriveActivity.this.finish();
        }
    }

    private void setupActivity() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:26:0x0334
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
        r4 = 2130903292; // 0x7f0300fc float:1.7413398E38 double:1.052806111E-314;
        r0 = r52;
        r0.setContentView(r4);
        r4 = 2131689674; // 0x7f0f00ca float:1.900837E38 double:1.0531946355E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r7 = r5;
        r7 = (com.waze.view.title.TitleBar) r7;
        r6 = r7;
        r0 = r52;
        r0.mTitleBar = r6;
        r0 = r52;
        r6 = r0.mTitleBar;
        r4 = 3541; // 0xdd5 float:4.962E-42 double:1.7495E-320;
        r0 = r52;
        r6.init(r0, r4);
        r0 = r52;
        r6 = r0.mTitleBar;
        r4 = 0;
        r6.setCloseVisibility(r4);
        r0 = r52;
        r6 = r0.mTitleBar;
        r4 = 3541; // 0xdd5 float:4.962E-42 double:1.7495E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r6.setTitle(r8);
        r4 = 2131691277; // 0x7f0f070d float:1.9011621E38 double:1.0531954275E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r10 = r5;
        r10 = (android.widget.TextView) r10;
        r9 = r10;
        r4 = 3542; // 0xdd6 float:4.963E-42 double:1.75E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691281; // 0x7f0f0711 float:1.901163E38 double:1.0531954295E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r11 = r5;
        r11 = (android.widget.TextView) r11;
        r9 = r11;
        r4 = 3543; // 0xdd7 float:4.965E-42 double:1.7505E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691285; // 0x7f0f0715 float:1.9011638E38 double:1.0531954315E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r12 = r5;
        r12 = (android.widget.TextView) r12;
        r9 = r12;
        r4 = 3544; // 0xdd8 float:4.966E-42 double:1.751E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691289; // 0x7f0f0719 float:1.9011646E38 double:1.0531954334E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r13 = r5;
        r13 = (android.widget.TextView) r13;
        r9 = r13;
        r4 = 3545; // 0xdd9 float:4.968E-42 double:1.7515E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691292; // 0x7f0f071c float:1.9011652E38 double:1.053195435E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r14 = r5;
        r14 = (android.widget.TextView) r14;
        r9 = r14;
        r4 = 3546; // 0xdda float:4.969E-42 double:1.752E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691293; // 0x7f0f071d float:1.9011654E38 double:1.0531954354E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r15 = r5;
        r15 = (android.widget.TextView) r15;
        r9 = r15;
        r0 = r52;
        r0.mReward1 = r9;
        r0 = r52;
        r9 = r0.mReward1;
        r4 = 8;
        r9.setVisibility(r4);
        r4 = 2131691294; // 0x7f0f071e float:1.9011656E38 double:1.053195436E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r16 = r5;
        r16 = (android.widget.TextView) r16;
        r9 = r16;
        r0 = r52;
        r0.mReward2 = r9;
        r0 = r52;
        r9 = r0.mReward2;
        r4 = 8;
        r9.setVisibility(r4);
        r0 = r52;
        r17 = r0.getResources();
        r0 = r17;
        r18 = r0.getConfiguration();
        r0 = r18;
        r0 = r0.orientation;
        r19 = r0;
        r4 = 1;
        r0 = r19;
        if (r0 != r4) goto L_0x0338;
    L_0x00f4:
        r20 = 1;
    L_0x00f6:
        r4 = 2131691298; // 0x7f0f0722 float:1.9011664E38 double:1.053195438E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r4 = 2131691297; // 0x7f0f0721 float:1.9011662E38 double:1.0531954374E-314;
        r0 = r52;
        r21 = r0.findViewById(r4);
        r4 = 59;
        r22 = com.waze.config.ConfigValues.getBoolValue(r4);
        r0 = r52;
        r0 = r0.mBuildingOfferMode;
        r23 = r0;
        if (r23 == 0) goto L_0x0342;
    L_0x0116:
        if (r22 == 0) goto L_0x0342;
    L_0x0118:
        r4 = 0;
        r5.setVisibility(r4);
        if (r20 == 0) goto L_0x033f;
    L_0x011e:
        r24 = 0;
    L_0x0120:
        r0 = r21;
        r1 = r24;
        r0.setVisibility(r1);
        r0 = r52;
        r0 = r0.mIsFreeOffer;
        r20 = r0;
        r0 = r52;
        r1 = r20;
        r0.setFreeOffer(r1);
        r4 = 2131691299; // 0x7f0f0723 float:1.9011666E38 double:1.0531954384E-314;
        r0 = r52;
        r21 = r0.findViewById(r4);
        r25 = r21;
        r25 = (android.widget.TextView) r25;
        r9 = r25;
        r4 = 3586; // 0xe02 float:5.025E-42 double:1.7717E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691301; // 0x7f0f0725 float:1.901167E38 double:1.0531954394E-314;
        r0 = r52;
        r21 = r0.findViewById(r4);
        r26 = r21;
        r26 = (android.widget.TextView) r26;
        r9 = r26;
        r4 = 3587; // 0xe03 float:5.026E-42 double:1.772E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691296; // 0x7f0f0720 float:1.901166E38 double:1.053195437E-314;
        r0 = r52;
        r21 = r0.findViewById(r4);
        r27 = r21;
        r27 = (android.widget.TextView) r27;
        r9 = r27;
        r4 = 3585; // 0xe01 float:5.024E-42 double:1.771E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691300; // 0x7f0f0724 float:1.9011668E38 double:1.053195439E-314;
        r0 = r52;
        r21 = r0.findViewById(r4);
        r29 = r21;
        r29 = (com.waze.ifs.ui.WazeSwitchView) r29;
        r28 = r29;
        r0 = r52;
        r0 = r0.mIsFreeOffer;
        r20 = r0;
        r0 = r28;
        r1 = r20;
        r0.setValue(r1);
        r30 = new com.waze.carpool.CarpoolOfferDriveActivity$1;
        r0 = r30;
        r1 = r52;
        r0.<init>();
        r0 = r28;
        r1 = r30;
        r0.setOnChecked(r1);
        r31 = new com.waze.carpool.CarpoolOfferDriveActivity$2;
        r0 = r31;
        r1 = r52;
        r2 = r28;
        r0.<init>(r2);
        r0 = r31;
        r5.setOnClickListener(r0);
    L_0x01b8:
        r4 = 2131691278; // 0x7f0f070e float:1.9011623E38 double:1.053195428E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r32 = r5;
        r32 = (android.widget.TextView) r32;
        r9 = r32;
        r0 = r52;
        r0.mTvFrom = r9;
        r0 = r52;
        r9 = r0.mTvFrom;
        r0 = r52;
        r0 = r0.aiFrom;
        r33 = r0;
        r0 = r52;
        r1 = r33;
        r0.updateField(r9, r1);
        r0 = r52;
        r0 = r0.mBuildingOfferMode;
        r20 = r0;
        if (r20 == 0) goto L_0x03a9;
    L_0x01e4:
        r0 = r52;
        r9 = r0.mTvFrom;
        r34 = new com.waze.carpool.CarpoolOfferDriveActivity$3;
        r0 = r34;
        r1 = r52;
        r0.<init>();
        r0 = r34;
        r9.setOnClickListener(r0);
    L_0x01f6:
        r4 = 2131691282; // 0x7f0f0712 float:1.9011631E38 double:1.05319543E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r35 = r5;
        r35 = (android.widget.TextView) r35;
        r9 = r35;
        r0 = r52;
        r0.mTvTo = r9;
        r0 = r52;
        r9 = r0.mTvTo;
        r0 = r52;
        r0 = r0.aiTo;
        r33 = r0;
        r0 = r52;
        r1 = r33;
        r0.updateField(r9, r1);
        r0 = r52;
        r0 = r0.mBuildingOfferMode;
        r20 = r0;
        if (r20 == 0) goto L_0x03c6;
    L_0x0222:
        r0 = r52;
        r9 = r0.mTvTo;
        r36 = new com.waze.carpool.CarpoolOfferDriveActivity$4;
        r0 = r36;
        r1 = r52;
        r0.<init>();
        r0 = r36;
        r9.setOnClickListener(r0);
    L_0x0234:
        r0 = r52;
        r0 = r0.mBuildingOfferMode;
        r20 = r0;
        if (r20 == 0) goto L_0x03e3;
    L_0x023c:
        r4 = 2131691274; // 0x7f0f070a float:1.9011615E38 double:1.053195426E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r4 = 0;
        r5.setVisibility(r4);
        r4 = 2131691302; // 0x7f0f0726 float:1.9011672E38 double:1.05319544E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r4 = 8;
        r5.setVisibility(r4);
        r0 = r52;
        r0.setButtons();
    L_0x025c:
        r37 = java.util.Calendar.getInstance();
        r0 = r52;
        r0 = r0.selectedDay;
        r19 = r0;
        r4 = 6;
        r0 = r37;
        r1 = r19;
        r0.add(r4, r1);
        r0 = r52;
        r0 = r0.selectedHour;
        r19 = r0;
        r4 = 11;
        r0 = r37;
        r1 = r19;
        r0.set(r4, r1);
        r0 = r52;
        r0 = r0.selectedMinute;
        r19 = r0;
        r4 = 12;
        r0 = r37;
        r1 = r19;
        r0.set(r4, r1);
        r4 = 13;
        r38 = 0;
        r0 = r37;
        r1 = r38;
        r0.set(r4, r1);
        r4 = 14;
        r38 = 0;
        r0 = r37;
        r1 = r38;
        r0.set(r4, r1);
        r4 = 2131691286; // 0x7f0f0716 float:1.901164E38 double:1.053195432E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r39 = r5;
        r39 = (android.widget.TextView) r39;
        r9 = r39;
        r0 = r52;
        r0.mTvDaySelect = r9;
        r0 = r52;
        r9 = r0.mTvDaySelect;
        r0 = r37;
        r40 = r0.getTimeInMillis();
        r4 = 1;
        r0 = r52;
        r1 = r40;
        r8 = r0.getDayString(r1, r4);
        r9.setText(r8);
        r0 = r52;
        r0 = r0.mBuildingOfferMode;
        r20 = r0;
        if (r20 == 0) goto L_0x0432;
    L_0x02d3:
        r0 = r52;
        r9 = r0.mTvDaySelect;
        r42 = new com.waze.carpool.CarpoolOfferDriveActivity$6;
        r0 = r42;
        r1 = r52;
        r0.<init>();
        r0 = r42;
        r9.setOnClickListener(r0);
    L_0x02e5:
        r4 = 2131691290; // 0x7f0f071a float:1.9011648E38 double:1.053195434E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r43 = r5;
        r43 = (android.widget.TextView) r43;
        r9 = r43;
        r0 = r52;
        r0.mTvHourSelect = r9;
        r0 = r52;
        r9 = r0.mTvHourSelect;
        r0 = r52;
        r0 = r0.mTimeFormat;
        r44 = r0;
        r0 = r37;
        r40 = r0.getTimeInMillis();
        r0 = r40;
        r45 = java.lang.Long.valueOf(r0);
        r0 = r44;
        r1 = r45;
        r8 = r0.format(r1);
        r9.setText(r8);
        r0 = r52;
        r0 = r0.mBuildingOfferMode;
        r20 = r0;
        if (r20 == 0) goto L_0x044f;
    L_0x0321:
        r0 = r52;
        r9 = r0.mTvHourSelect;
        r46 = new com.waze.carpool.CarpoolOfferDriveActivity$7;
        r0 = r46;
        r1 = r52;
        r0.<init>();
        r0 = r46;
        r9.setOnClickListener(r0);
        return;
        goto L_0x0338;
    L_0x0335:
        goto L_0x00f6;
    L_0x0338:
        r20 = 0;
        goto L_0x0335;
        goto L_0x033f;
    L_0x033c:
        goto L_0x0120;
    L_0x033f:
        r24 = 8;
        goto L_0x033c;
    L_0x0342:
        r4 = 8;
        r5.setVisibility(r4);
        r4 = 8;
        r0 = r21;
        r0.setVisibility(r4);
        r0 = r52;
        r0 = r0.mDrive;
        r47 = r0;
        if (r47 == 0) goto L_0x0396;
    L_0x0356:
        r0 = r52;
        r0 = r0.mDrive;
        r47 = r0;
        r0 = r0.itinerary;
        r48 = r0;
        r0 = r0.free_offer;
        r20 = r0;
        if (r20 == 0) goto L_0x0396;
    L_0x0366:
        r4 = 2131691295; // 0x7f0f071f float:1.9011658E38 double:1.0531954364E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r4 = 0;
        r5.setVisibility(r4);
        r4 = 0;
        r0 = r21;
        r0.setVisibility(r4);
        r4 = 2131691296; // 0x7f0f0720 float:1.901166E38 double:1.053195437E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r49 = r5;
        r49 = (android.widget.TextView) r49;
        r9 = r49;
        r4 = 3585; // 0xe01 float:5.024E-42 double:1.771E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        goto L_0x0392;
    L_0x038f:
        goto L_0x01b8;
    L_0x0392:
        r9.setText(r8);
        goto L_0x038f;
    L_0x0396:
        r4 = 2131691295; // 0x7f0f071f float:1.9011658E38 double:1.0531954364E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        goto L_0x03a3;
    L_0x03a0:
        goto L_0x01b8;
    L_0x03a3:
        r4 = 8;
        r5.setVisibility(r4);
        goto L_0x03a0;
    L_0x03a9:
        r0 = r52;
        r9 = r0.mTvFrom;
        r0 = r52;
        r17 = r0.getResources();
        r4 = 2131623948; // 0x7f0e000c float:1.8875062E38 double:1.0531621626E-314;
        r0 = r17;
        r19 = r0.getColor(r4);
        goto L_0x03c0;
    L_0x03bd:
        goto L_0x01f6;
    L_0x03c0:
        r0 = r19;
        r9.setTextColor(r0);
        goto L_0x03bd;
    L_0x03c6:
        r0 = r52;
        r9 = r0.mTvTo;
        r0 = r52;
        r17 = r0.getResources();
        r4 = 2131623948; // 0x7f0e000c float:1.8875062E38 double:1.0531621626E-314;
        r0 = r17;
        r19 = r0.getColor(r4);
        goto L_0x03dd;
    L_0x03da:
        goto L_0x0234;
    L_0x03dd:
        r0 = r19;
        r9.setTextColor(r0);
        goto L_0x03da;
    L_0x03e3:
        r4 = 2131691274; // 0x7f0f070a float:1.9011615E38 double:1.053195426E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r4 = 8;
        r5.setVisibility(r4);
        r4 = 2131691302; // 0x7f0f0726 float:1.9011672E38 double:1.05319544E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r4 = 0;
        r5.setVisibility(r4);
        r4 = 2131691303; // 0x7f0f0727 float:1.9011674E38 double:1.0531954404E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r50 = r5;
        r50 = (android.widget.TextView) r50;
        r9 = r50;
        r4 = 3561; // 0xde9 float:4.99E-42 double:1.7594E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r4);
        r9.setText(r8);
        r4 = 2131691302; // 0x7f0f0726 float:1.9011672E38 double:1.05319544E-314;
        r0 = r52;
        r5 = r0.findViewById(r4);
        r51 = new com.waze.carpool.CarpoolOfferDriveActivity$5;
        r0 = r51;
        r1 = r52;
        r0.<init>();
        goto L_0x042c;
    L_0x0429:
        goto L_0x025c;
    L_0x042c:
        r0 = r51;
        r5.setOnClickListener(r0);
        goto L_0x0429;
    L_0x0432:
        r0 = r52;
        r9 = r0.mTvDaySelect;
        r0 = r52;
        r17 = r0.getResources();
        r4 = 2131623948; // 0x7f0e000c float:1.8875062E38 double:1.0531621626E-314;
        r0 = r17;
        r19 = r0.getColor(r4);
        goto L_0x0449;
    L_0x0446:
        goto L_0x02e5;
    L_0x0449:
        r0 = r19;
        r9.setTextColor(r0);
        goto L_0x0446;
    L_0x044f:
        r0 = r52;
        r9 = r0.mTvHourSelect;
        r0 = r52;
        r17 = r0.getResources();
        r4 = 2131623948; // 0x7f0e000c float:1.8875062E38 double:1.0531621626E-314;
        r0 = r17;
        r19 = r0.getColor(r4);
        r0 = r19;
        r9.setTextColor(r0);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolOfferDriveActivity.setupActivity():void");
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        this.mDtnm = DriveToNativeManager.getInstance();
        this.mCpnm = CarpoolNativeManager.getInstance();
        if (getIntent().hasExtra(CarpoolDrive.class.getSimpleName())) {
            this.mDrive = (CarpoolDrive) getIntent().getParcelableExtra(CarpoolDrive.class.getSimpleName());
            this.mBuildingOfferMode = false;
        }
        Calendar $r10 = Calendar.getInstance();
        TimeZone $r11 = $r10.getTimeZone();
        this.mDayFormat = getShortDateFormat();
        DateFormat dateFormat = this.mDayFormat;
        DateFormat $r13 = dateFormat;
        dateFormat.setTimeZone($r11);
        this.mTimeFormat = android.text.format.DateFormat.getTimeFormat(this);
        dateFormat = this.mTimeFormat;
        $r13 = dateFormat;
        dateFormat.setTimeZone($r11);
        getWindow().setSoftInputMode(3);
        requestWindowFeature(1);
        if ($r1 == null) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_SHOWN).addParam("TYPE", this.mBuildingOfferMode ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_CREATE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_REMOVE).send();
            if (this.mBuildingOfferMode) {
                this.aiFrom = AddressItem.getCurLocAddressItem(this);
                this.aiTo = null;
                setDefaultTime($r10);
            } else {
                this.aiFrom = this.mCpnm.driveGetAddressItem(this.mDrive, 1);
                this.aiTo = this.mCpnm.driveGetAddressItem(this.mDrive, 4);
                long $l0 = this.mDrive.getTime() * 1000;
                long j = $l0;
                $r10.setTimeInMillis($l0);
                setSelectedTime($r10);
            }
        } else {
            this.aiFrom = (AddressItem) $r1.getSerializable(getClass().getCanonicalName() + ".aiFrom");
            this.aiTo = (AddressItem) $r1.getSerializable(getClass().getCanonicalName() + ".aiTo");
            this.selectedDay = $r1.getInt(getClass().getCanonicalName() + ".selectedDay");
            this.selectedHour = $r1.getInt(getClass().getCanonicalName() + ".selectedHour");
            this.selectedMinute = $r1.getInt(getClass().getCanonicalName() + ".selectedMinute");
            this.mIsFreeOffer = $r1.getBoolean(getClass().getCanonicalName() + ".mIsFreeOffer");
        }
        setupActivity();
    }

    public static SimpleDateFormat getShortDateFormat() throws  {
        Locale $r0 = new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV());
        String $r2 = ((SimpleDateFormat) SimpleDateFormat.getDateInstance(2)).toLocalizedPattern();
        return new SimpleDateFormat($r2.replaceAll($r2.contains("de") ? "[^Mm]*[Yy]+[^Mm]*" : "[^DdMm]*[Yy]+[^DdMm]*", ""), $r0);
    }

    private void setDefaultTime(Calendar $r1) throws  {
        int $i0 = $r1.get(11);
        if ($i0 >= 18 || $i0 < 10) {
            byte $b1;
            if ($i0 >= 18) {
                $b1 = (byte) 1;
            } else {
                $b1 = (byte) 0;
            }
            this.selectedDay = $b1;
            this.selectedHour = 8;
        } else {
            this.selectedDay = 0;
            this.selectedHour = 17;
        }
        if (this.selectedHour > $i0 || this.selectedDay != 0) {
            this.selectedMinute = 0;
            return;
        }
        this.selectedHour = $i0;
        $i0 = (($r1.get(12) / TIME_INTERVAL_MIN) + 1) * TIME_INTERVAL_MIN;
        if ($i0 >= 60) {
            $i0 -= 60;
            this.selectedHour++;
        }
        this.selectedMinute = $i0;
    }

    private void setSelectedTime(Calendar $r1) throws  {
        Calendar $r2 = Calendar.getInstance();
        if ($r1.get(1) != $r2.get(1)) {
            this.selectedDay = 0;
            while ($r2.before($r1)) {
                $r2.add(5, 1);
                this.selectedDay++;
                if (this.selectedDay > 14) {
                    break;
                }
            }
        }
        this.selectedDay = $r1.get(6) - $r2.get(6);
        this.selectedHour = $r1.get(11);
        this.selectedMinute = $r1.get(12);
    }

    protected void onResume() throws  {
        super.onResume();
    }

    protected void onPause() throws  {
        super.onPause();
    }

    protected void onDestroy() throws  {
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED, this.mHandler);
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        $r1.putSerializable(getClass().getCanonicalName() + ".aiFrom", this.aiFrom);
        $r1.putSerializable(getClass().getCanonicalName() + ".aiTo", this.aiTo);
        $r1.putInt(getClass().getCanonicalName() + ".selectedDay", this.selectedDay);
        $r1.putInt(getClass().getCanonicalName() + ".selectedHour", this.selectedHour);
        $r1.putInt(getClass().getCanonicalName() + ".selectedMinute", this.selectedMinute);
        $r1.putBoolean(getClass().getCanonicalName() + ".mIsFreeOffer", this.mIsFreeOffer);
        super.onSaveInstanceState($r1);
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED) {
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED, this.mHandler);
            this.mNm.CloseProgressPopup();
            String $r6 = $r1.getData().getString("driveId");
            if ($r6 == null || $r6.isEmpty()) {
                onError(DisplayStrings.DS_OFFER_RIDE_CREATE_FAILED_MESSAGE, 0);
                return true;
            }
            onExitAndConfirm(DisplayStrings.DS_OFFER_RIDE_CONFIRMED);
            return true;
        } else if ($r1.what != CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED) {
            return super.myHandleMessage($r1);
        } else {
            if (this.mDrive == null) {
                return true;
            }
            if (!this.mDrive.getId().contentEquals($r1.getData().getString("id", ""))) {
                return true;
            }
            this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED, this.mHandler);
            this.mNm.CloseProgressPopup();
            setResult(-1);
            finish();
            return true;
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if (($i0 == 1001 || $i0 == 1002) && $i1 == -1) {
            if ($r1 != null) {
                AddressItem $r4 = (AddressItem) $r1.getExtras().get("ai");
                if ($r4 != null) {
                    TextView $r6;
                    if ($r4.getAddress().isEmpty()) {
                        $r4.setAddress($r4.getSecondaryTitle());
                    }
                    if ($i0 == 1001) {
                        this.aiFrom = $r4;
                        $r6 = this.mTvFrom;
                    } else {
                        this.aiTo = $r4;
                        $r6 = this.mTvTo;
                    }
                    updateField($r6, $r4);
                }
            }
        } else if ($i1 == -1) {
            setResult($i1);
            finish();
        }
    }

    public void onBackPressed() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDE_OFFER_SCREEN_CLICKED).addParam("ACTION", "BACK").send();
        super.onBackPressed();
    }

    private void setFreeOffer(boolean $z0) throws  {
        boolean $z1 = true;
        byte $b0 = (byte) 0;
        if (getResources().getConfiguration().orientation != 1) {
            $z1 = false;
        }
        this.mIsFreeOffer = $z0;
        View $r3 = findViewById(C1283R.id.offerRideAngleLayout);
        View $r4 = findViewById(C1283R.id.offerRideSepFree);
        if ($z0) {
            $r3.setVisibility(0);
            this.mReward1.setVisibility(8);
            this.mReward2.setVisibility(8);
            $r4.setVisibility(0);
            return;
        }
        $r3.setVisibility(8);
        if (!$z1) {
            $b0 = (byte) 8;
        }
        $r4.setVisibility($b0);
    }

    private void setButtons() throws  {
        TextView $r2 = (TextView) findViewById(C1283R.id.offerRideButAccept);
        $r2.setText(DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_OFFER));
        $r2.setOnClickListener(new C14828());
        $r2 = (TextView) findViewById(C1283R.id.offerRideButDismiss);
        $r2.setText(DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_CANCEL));
        $r2.setOnClickListener(new C14839());
    }

    private void showCancelDialog() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CANCEL_RIDE_OFFER_POPUP_SHOWN).send();
        MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_CANCEL_DIALOG_TITLE), DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_CANCEL_DIALOG_BODY), false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int $i0) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_CANCEL_RIDE_OFFER_POPUP_CLICKED).addParam("ACTION", $i0 == 1 ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_CONFIRM : "CANCEL").send();
                if ($i0 == 1) {
                    CarpoolOfferDriveActivity.this.cancelRide();
                }
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_CANCEL_DIALOG_YES), DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_CANCEL_DIALOG_NO), 0);
    }

    private void cancelRide() throws  {
        this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_REMOVED, this.mHandler);
        this.mCpnm.cancelRideOffer(this.mDrive.getId());
        this.mNm.OpenProgressPopup(DisplayStrings.displayString(290));
    }

    private void confirmAndFinish() throws  {
        if (this.aiTo == null) {
            this.mTvTo.setTextColor(getResources().getColor(C1283R.color.pastrami_pink));
            ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            scaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
            scaleAnimation.setRepeatCount(1);
            scaleAnimation.setRepeatMode(2);
            scaleAnimation.setDuration(100);
            this.mTvTo.startAnimation(scaleAnimation);
            return;
        }
        if (!badCurrentLocation(this.aiFrom)) {
            if (!badCurrentLocation(this.aiTo)) {
                Calendar $r6 = Calendar.getInstance();
                $r6.add(6, this.selectedDay);
                $r6.set(11, this.selectedHour);
                $r6.set(12, this.selectedMinute);
                $r6.set(13, 0);
                $r6.set(14, 0);
                if (((long) ConfigValues.getIntValue(64)) + (($r6.getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000) < 0) {
                    MsgBox.openMessageBox(DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_TIME_ERROR_TITLE), DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_TIME_ERROR_BODY), false);
                    return;
                }
                final Calendar calendar;
                this.mWaitForAddress = 0;
                if (this.aiFrom.getType() == 16 || this.aiFrom.getAddress().isEmpty()) {
                    this.mWaitForAddress++;
                    calendar = $r6;
                    DriveToNativeManager.getInstance().getLocationData(3, this.aiFrom.getLocationX(), this.aiFrom.getLocationY(), new LocationDataListener() {
                        public void onComplete(LocationData $r1) throws  {
                            CarpoolOfferDriveActivity.this.mWaitForAddress = CarpoolOfferDriveActivity.this.mWaitForAddress - 1;
                            if (!($r1 == null || $r1.locationName == null || $r1.locationName.isEmpty())) {
                                CarpoolOfferDriveActivity.this.aiFrom.setAddress($r1.locationName);
                            }
                            CarpoolOfferDriveActivity.this.offerRide(calendar);
                        }
                    }, null);
                }
                if (this.aiTo.getType() == 16 || this.aiTo.getAddress().isEmpty()) {
                    this.mWaitForAddress++;
                    calendar = $r6;
                    DriveToNativeManager.getInstance().getLocationData(3, this.aiTo.getLocationX(), this.aiTo.getLocationY(), new LocationDataListener() {
                        public void onComplete(LocationData $r1) throws  {
                            CarpoolOfferDriveActivity.this.mWaitForAddress = CarpoolOfferDriveActivity.this.mWaitForAddress - 1;
                            if (!($r1 == null || $r1.locationName == null || $r1.locationName.isEmpty())) {
                                CarpoolOfferDriveActivity.this.aiTo.setAddress($r1.locationName);
                            }
                            CarpoolOfferDriveActivity.this.offerRide(calendar);
                        }
                    }, null);
                }
                offerRide($r6);
            }
        }
    }

    private void offerRide(Calendar $r1) throws  {
        if (this.mWaitForAddress <= 0) {
            this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_DRIVE_CREATED, this.mHandler);
            this.mCpnm.offerRide(this.aiFrom.getLocationX().intValue(), this.aiFrom.getLocationY().intValue(), this.aiFrom.getAddress(), this.aiTo.getLocationX().intValue(), this.aiTo.getLocationY().intValue(), this.aiTo.getAddress(), $r1.getTimeInMillis() / 1000, this.mIsFreeOffer);
            this.mNm.OpenProgressPopup(DisplayStrings.displayString(290));
        }
    }

    private void onExitAndConfirm(final int $i0) throws  {
        doneWaitingForUpdate(null, null);
        setResult(-1);
        finish();
        AppService.Post(new Runnable() {

            class C14741 implements Runnable {
                C14741() throws  {
                }

                public void run() throws  {
                    CarpoolOfferDriveActivity.this.mNm.CloseProgressPopup();
                }
            }

            public void run() throws  {
                CarpoolOfferDriveActivity.this.mNm.OpenProgressIconPopup(CarpoolOfferDriveActivity.this.mNm.getLanguageString($i0), "sign_up_big_v");
                CarpoolOfferDriveActivity.this.postDelayed(new C14741(), 2500);
            }
        });
    }

    private boolean badCurrentLocation(AddressItem $r1) throws  {
        if ($r1.getLocationX().intValue() != 0) {
            return false;
        }
        if ($r1.getLocationY().intValue() != 0) {
            return false;
        }
        Location $r3 = Alerter.getBestLocation(this);
        int $i0 = ConfigValues.getIntValue(63);
        if ($r3 == null || $r3.getAccuracy() > ((float) $i0)) {
            MsgBox.openMessageBox(DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_CURRENT_LOCATION_ERROR_TITLE), DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_CURRENT_LOCATION_ERROR_BODY), false);
            return true;
        }
        $r1.setLocationX(Integer.valueOf((int) ($r3.getLongitude() * 1000000.0d)));
        $r1.setLocationY(Integer.valueOf((int) ($r3.getLatitude() * 1000000.0d)));
        return false;
    }

    private void updateField(TextView $r1, AddressItem $r2) throws  {
        if ($r2 == null) {
            $r1.setText(DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_ADD_LOCATION));
            return;
        }
        if (!$r2.getAddress().isEmpty()) {
            $r1.setText($r2.getAddress());
        } else if ($r2.getSecondaryTitle().isEmpty()) {
            $r1.setText($r2.getTitle());
        } else {
            $r1.setText($r2.getSecondaryTitle());
        }
        $r1.setTextColor(getResources().getColor(C1283R.color.ElectricBlue));
    }

    void setReward(int $i0) throws  {
        String $r2 = this.mCpnm.centsToString(this, $i0, null, null);
        this.mReward1.setVisibility(0);
        this.mReward1.setText(DisplayStrings.displayStringF(DisplayStrings.DS_OFFER_RIDE_REWARD_LINE1_PS, new Object[]{$r2}));
        this.mReward2.setVisibility(0);
        this.mReward2.setText(DisplayStrings.displayString(DisplayStrings.DS_OFFER_RIDE_REWARD_LINE2));
    }

    void openSelectDayDialog() throws  {
        int $i0 = ConfigValues.getIntValue(62);
        long $l1 = System.currentTimeMillis();
        final ArrayList $r2 = new ArrayList(8);
        for (long $l2 = 0; $l2 < ((long) $i0); $l2++) {
            boolean $z0;
            long $l3 = (86400000 * $l2) + $l1;
            if ($l2 > 1) {
                $z0 = true;
            } else {
                $z0 = false;
            }
            $r2.add(getDayString($l3, $z0));
        }
        BottomSheet bottomSheet = new BottomSheet(this, DisplayStrings.DS_OFFER_RIDE_SELECT_DAY_TITLE, Mode.COLUMN_TEXT);
        final BottomSheet bottomSheet2 = bottomSheet;
        bottomSheet.setAdapter(new Adapter() {
            public int getCount() throws  {
                return $r2.size();
            }

            public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                $r1.setItem((String) $r2.get($i0));
            }

            public void onClick(int $i0) throws  {
                CarpoolOfferDriveActivity.this.selectedDay = $i0;
                CarpoolOfferDriveActivity.this.mTvDaySelect.setText((CharSequence) $r2.get($i0));
                bottomSheet2.dismiss();
            }
        });
        bottomSheet.setSelected(this.selectedDay);
        bottomSheet.show();
    }

    private String getDayString(long $l0, boolean $z0) throws  {
        String $r1 = DisplayUtils.getDayString(this, $l0, false, true);
        if (!$z0) {
            return $r1;
        }
        String $r4 = this.mDayFormat.format(new Date($l0));
        return DisplayStrings.displayStringF(DisplayStrings.DS_OFFER_RIDE_SELECT_DAY_FORMAT_PS_PS, new Object[]{$r1, $r4});
    }

    void openSelectTimeDialog() throws  {
        Calendar $r3 = Calendar.getInstance();
        $r3.set(11, 0);
        $r3.set(12, 0);
        $r3.set(13, 0);
        $r3.set(14, 0);
        long $l1 = $r3.getTimeInMillis();
        int $i0 = ((this.selectedHour * 60) + this.selectedMinute) / TIME_INTERVAL_MIN;
        ArrayList $r1 = new ArrayList(8);
        long $l3 = 0;
        while ($l3 <= 1440) {
            long $l5 = (60 * $l3) * 1000;
            long j = $l5;
            $r1.add(this.mTimeFormat.format(new Date($l5 + $l1)));
            $l5 = (long) TIME_INTERVAL_MIN;
            $l3 += $l5;
        }
        BottomSheet bottomSheet = new BottomSheet(this, DisplayStrings.DS_OFFER_RIDE_SELECT_TIME_TITLE, Mode.COLUMN_TEXT);
        bottomSheet.setMagneticChooser();
        final ArrayList arrayList = $r1;
        final long j2 = $l1;
        final BottomSheet bottomSheet2 = bottomSheet;
        bottomSheet.setAdapter(new Adapter() {
            public int getCount() throws  {
                return arrayList.size();
            }

            public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                $r1.setItem((String) arrayList.get($i0));
            }

            public void onClick(int $i0) throws  {
                $i0 *= CarpoolOfferDriveActivity.TIME_INTERVAL_MIN;
                CarpoolOfferDriveActivity.this.selectedHour = $i0 / 60;
                CarpoolOfferDriveActivity.this.selectedMinute = $i0 % 60;
                CarpoolOfferDriveActivity.this.mTvHourSelect.setText(CarpoolOfferDriveActivity.this.mTimeFormat.format(Long.valueOf(j2 + ((long) (($i0 * 60) * 1000)))));
                bottomSheet2.dismiss();
            }
        });
        bottomSheet.setSelected($i0);
        bottomSheet.show();
    }

    private synchronized void doneWaitingForUpdate(String $r1, String $r2) throws  {
        if (this.mWaitingForUpdate) {
            cancel(this.mTimeoutRunnable);
            this.mWaitingForUpdate = false;
            if ($r1 == null) {
                this.mNm.CloseProgressPopup();
            } else {
                showPopup($r1, $r2, 10);
            }
        }
    }

    private void waitForUpdate() throws  {
        waitForUpdate(-1);
    }

    private void waitForUpdate(final int $i0) throws  {
        if (this.mTimeoutRunnable != null) {
            cancel(this.mTimeoutRunnable);
        }
        this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
        this.mWaitingForUpdate = true;
        this.mTimeoutRunnable = new Runnable() {
            public void run() throws  {
                CarpoolOfferDriveActivity.this.onError($i0);
            }
        };
        postDelayed(this.mTimeoutRunnable, NETWORK_TIMEOUT);
    }

    private void onError(int $i0) throws  {
        onError(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_, $i0);
    }

    private void onError(int $i0, final int $i1) throws  {
        doneWaitingForUpdate(null, null);
        MsgBox.openMessageBoxTimeout(this.mNm.getLanguageString((int) DisplayStrings.DS_UHHOHE), this.mNm.getLanguageString($i0), 5, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) throws  {
                CarpoolOfferDriveActivity.this.setResult($i1);
                CarpoolOfferDriveActivity.this.finish();
            }
        });
    }

    public void searchClicked(int $i0, int $i1) throws  {
        Intent $r1 = new Intent(this, AutocompleteSearchActivity.class);
        $r1.putExtra(PublicMacros.SKIP_PREVIEW, true);
        $r1.putExtra(PublicMacros.TITLE_DS, $i1);
        $r1.putExtra(PublicMacros.SEARCH_MODE, 12);
        startActivityForResult($r1, $i0);
    }
}
