package com.waze.carpool;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ScrollView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.location.LocationHistory;
import com.waze.location.LocationHistory.LocationHistoryOptInStatus;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;

public class MissingPermissionsActivity extends ActivityBase {
    private final int SCROLL_TIMEOUT = 1000;
    private Intent mMPNext = null;
    private MPType mType = MPType.MissingLocationHistory;
    private boolean mbClicked = false;

    class C16662 implements OnClickListener {

        class C16651 implements LocationHistoryOptInStatus {

            class C16641 implements DialogInterface.OnClickListener {
                C16641() throws  {
                }

                public void onClick(DialogInterface dialog, int which) throws  {
                    if (MissingPermissionsActivity.this.mMPNext != null) {
                        MissingPermissionsActivity.this.startActivity(MissingPermissionsActivity.this.mMPNext);
                    }
                    MissingPermissionsActivity.this.setResult(0);
                    MissingPermissionsActivity.this.finish();
                }
            }

            C16651() throws  {
            }

            public void onLocationHistoryOptInStatus(boolean $z0, boolean optedOut, boolean isOptedIn) throws  {
                if ($z0) {
                    Intent $r1 = new Intent();
                    $r1.putExtra("clicked_yes", true);
                    if (MissingPermissionsActivity.this.mMPNext != null) {
                        MissingPermissionsActivity.this.startActivity(MissingPermissionsActivity.this.mMPNext);
                    }
                    MissingPermissionsActivity.this.setResult(0, $r1);
                    MissingPermissionsActivity.this.finish();
                    return;
                }
                NativeManager $r7 = NativeManager.getInstance();
                MsgBox.openMessageBoxWithCallback($r7.getLanguageString((int) DisplayStrings.DS_UHHOHE), $r7.getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false, new C16641());
            }
        }

        C16662() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_LOCATION_HISTORY_OFF_SCREEN, "ACTION", AnalyticsEvents.ANALYTICS_YES);
            ScrollView $r3 = (ScrollView) MissingPermissionsActivity.this.findViewById(C1283R.id.misLocHisScroll);
            v = MissingPermissionsActivity.this.findViewById(C1283R.id.misLocHisSpace);
            if (AnimationUtils.isViewVisible($r3, v)) {
                MissingPermissionsActivity.this.mbClicked = true;
                LocationHistory.tryToOptIn(MissingPermissionsActivity.this, new C16651());
                return;
            }
            AnimationUtils.scrollPage($r3, v);
        }
    }

    class C16683 implements OnClickListener {

        class C16671 implements DialogInterface.OnClickListener {
            C16671() throws  {
            }

            public void onClick(DialogInterface dialog, int $i0) throws  {
                if ($i0 == 0) {
                    Intent $r2 = new Intent();
                    $r2.putExtra("clicked_no", true);
                    if (MissingPermissionsActivity.this.mMPNext != null) {
                        MissingPermissionsActivity.this.startActivity(MissingPermissionsActivity.this.mMPNext);
                    }
                    MissingPermissionsActivity.this.setResult(0, $r2);
                    MissingPermissionsActivity.this.finish();
                }
            }
        }

        C16683() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_LOCATION_HISTORY_OFF_SCREEN, "ACTION", AnalyticsEvents.ANALYTICS_NO);
            MissingPermissionsActivity.this.mbClicked = true;
            NativeManager $r4 = NativeManager.getInstance();
            C16671 $r2 = new C16671();
            MsgBox.openConfirmDialogJavaCallback($r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_SKIP_LH_DIALOG_TITLE), $r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_SKIP_LH_DIALOG_SURE), true, $r2, $r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_SKIP_LH_DIALOG_NO), $r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_SKIP_LH_DIALOG_YES), -1);
        }
    }

    class C16694 implements OnClickListener {
        C16694() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_MISSING_PERMISSIONS_CLICKED, "ACTION", AnalyticsEvents.ANALYTICS_YES);
            MissingPermissionsActivity.this.mbClicked = true;
            Intent $r2 = new Intent();
            $r2.putExtra("clicked_yes", true);
            MissingPermissionsActivity.this.setResult(-1, $r2);
            if (MissingPermissionsActivity.this.mMPNext != null) {
                MissingPermissionsActivity.this.startActivity(MissingPermissionsActivity.this.mMPNext);
            }
            MissingPermissionsActivity.this.finish();
        }
    }

    class C16705 implements OnClickListener {
        C16705() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_MISSING_PERMISSIONS_CLICKED, "ACTION", AnalyticsEvents.ANALYTICS_NO);
            MissingPermissionsActivity.this.mbClicked = true;
            Intent $r2 = new Intent();
            $r2.putExtra("clicked_no", true);
            MissingPermissionsActivity.this.setResult(-1, $r2);
            if (MissingPermissionsActivity.this.mMPNext != null) {
                MissingPermissionsActivity.this.startActivity(MissingPermissionsActivity.this.mMPNext);
            }
            MissingPermissionsActivity.this.finish();
        }
    }

    public enum MPType {
        MissingLocationHistory,
        MissingPushNotifications
    }

    protected void onCreate(Bundle $r1) throws  {
        String $r7;
        super.onCreate($r1);
        setContentView(C1283R.layout.missing_lh_activity);
        if (getIntent().hasExtra("MPType")) {
            this.mType = MPType.values()[getIntent().getIntExtra("MPType", MPType.MissingLocationHistory.ordinal())];
        }
        if (getIntent().hasExtra("MPNext")) {
            this.mMPNext = (Intent) getIntent().getParcelableExtra("MPNext");
        }
        if (this.mType == MPType.MissingLocationHistory) {
            $r7 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_LOCATION_HISTORY;
        } else {
            $r7 = "PUSH_NOTIFICATIONS";
        }
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_MISSING_PERMISSIONS_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, $r7);
        initFieldsTexts();
        setOnClickListeners();
    }

    private void initFieldsTexts() throws  {
        NativeManager $r1 = NativeManager.getInstance();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, 358, false);
        TextView $r4 = (TextView) findViewById(C1283R.id.misLocHisTitle);
        TextView $r5 = (TextView) findViewById(C1283R.id.misLocHisSubTitle);
        final TextView $r6 = (TextView) findViewById(C1283R.id.misLocHisLearn);
        final TextView $r7 = (TextView) findViewById(C1283R.id.misLocHisLearnMore);
        TextView $r8 = (TextView) findViewById(C1283R.id.misLocHisChoosing);
        TextView $r9 = (TextView) findViewById(C1283R.id.misLocHisButYes);
        TextView $r10 = (TextView) findViewById(C1283R.id.misLocHisButNo);
        int[] $r11 = C16716.$SwitchMap$com$waze$carpool$MissingPermissionsActivity$MPType;
        MPType $r12 = this.mType;
        switch ($r11[$r12.ordinal()]) {
            case 1:
                $r4.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_LOCATION_HISTORY_TITLE));
                $r5.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_LOCATION_HISTORY_SUBTITLE));
                $r6.setText(Html.fromHtml($r1.getLanguageString((int) DisplayStrings.DS_HTML_MISSING_LOCATION_HISTORY_LEARN)));
                $r6.setLinkTextColor($r6.getTextColors());
                $r6.setOnClickListener(new OnClickListener() {
                    public void onClick(View view) throws  {
                        $r7.setVisibility(0);
                        final ScrollView $r4 = (ScrollView) MissingPermissionsActivity.this.findViewById(C1283R.id.misLocHisScroll);
                        MissingPermissionsActivity.this.postDelayed(new Runnable() {
                            public void run() throws  {
                                $r4.smoothScrollTo(0, $r6.getTop());
                            }
                        }, 1000);
                    }
                });
                $r7.setText(Html.fromHtml(String.format($r1.getLanguageString((int) DisplayStrings.DS_HTML_MISSING_LOCATION_HISTORY_LEARN_MORE), new Object[]{CarpoolNativeManager.getInstance().LHLearnMoreURL()})));
                $r7.setMovementMethod(LinkMovementMethod.getInstance());
                $r7.setLinkTextColor($r6.getTextColors());
                $r7.setVisibility(8);
                $r8.setText(Html.fromHtml(String.format($r1.getLanguageString((int) DisplayStrings.DS_HTML_MISSING_LOCATION_HISTORY_CHOOSING_PS), new Object[]{GoogleSignInActivity.GetAuthorizedAccountName(), CarpoolNativeManager.getInstance().LHManageURL()})));
                $r8.setMovementMethod(LinkMovementMethod.getInstance());
                $r8.setLinkTextColor($r6.getTextColors());
                $r9.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_LOCATION_HISTORY_YES));
                $r10.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_LOCATION_HISTORY_NO));
                return;
            case 2:
                $r4.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_PUSH_NOTIFICATIONS_HEADER));
                $r5.setVisibility(8);
                $r6.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_PUSH_NOTIFICATIONS_BODY));
                $r7.setVisibility(8);
                $r8.setVisibility(8);
                $r9.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_PUSH_NOTIFICATIONS_YES));
                $r10.setText($r1.getLanguageString((int) DisplayStrings.DS_MISSING_PUSH_NOTIFICATIONS_NO));
                return;
            default:
                return;
        }
    }

    public void onBackPressed() throws  {
        switch (this.mType) {
            case MissingLocationHistory:
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_LOCATION_HISTORY_OFF_SCREEN, "ACTION", "BACK");
                break;
            default:
                break;
        }
        super.onBackPressed();
    }

    private void setOnClickListeners() throws  {
        TextView $r2 = (TextView) findViewById(C1283R.id.misLocHisButYes);
        TextView $r3 = (TextView) findViewById(C1283R.id.misLocHisButNo);
        switch (this.mType) {
            case MissingLocationHistory:
                $r2.setOnClickListener(new C16662());
                $r3.setOnClickListener(new C16683());
                return;
            case MissingPushNotifications:
                $r2.setOnClickListener(new C16694());
                $r3.setOnClickListener(new C16705());
                return;
            default:
                return;
        }
    }

    protected void onDestroy() throws  {
        if (!this.mbClicked) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_MISSING_PERMISSIONS_CLICKED, "ACTION", "BACK");
            setResult(0);
        }
        super.onDestroy();
    }
}
