package com.waze.carpool;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.Utils;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.ActivityBase.IncomingHandler;
import com.waze.ifs.ui.TimeRangeSelector;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;

public class CommuteModelDayActivity extends ActivityBase {
    private static final long NETWORK_TIMEOUT = 10000;
    private boolean bSentRequest;
    private CarpoolNativeManager mCpnm;
    private CarpoolCommuteDay mDay;
    private String mDayName;
    private int mDayNumber;
    private CarpoolCommuteDay mDefaultDay;
    private NativeManager mNm;
    private CarpoolCommuteDay mOriginalDay;
    private Runnable mTimeoutRunnable;

    class C16335 implements OnClickListener {
        C16335() throws  {
        }

        public void onClick(DialogInterface dialog, int which) throws  {
            CommuteModelDayActivity.this.setResult(-1);
            CommuteModelDayActivity.this.finish();
        }
    }

    class C16346 implements Runnable {
        C16346() throws  {
        }

        public void run() throws  {
            CommuteModelDayActivity.this.mNm.CloseProgressPopup();
            CommuteModelDayActivity.this.showError();
        }
    }

    String formatRange(int $i0, int $i1) throws  {
        return this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_TIME_FORMAT).replace("<FROM>", Utils.formatTime(this, $i0)).replace("<TO>", Utils.formatTime(this, $i1));
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.mDayName = getIntent().getStringExtra("dayName");
        this.mDayNumber = getIntent().getIntExtra("dayNumber", -1);
        if (this.mDayNumber == -1 || this.mDayNumber >= 8) {
            Logger.m38e("CommuteModelDayActivity called without dayNumber or with an invalid day number");
            return;
        }
        CarpoolCommuteDay[] $r2 = this.mCpnm.getCarpoolProfileNTV().commute_days;
        this.mOriginalDay = $r2[this.mDayNumber];
        this.mDefaultDay = $r2[7];
        this.mDay = this.mOriginalDay.clone();
        setContentView(C1283R.layout.commute_model_day);
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.init(this, this.mDayName);
        titleBar.setCloseVisibility(false);
        WazeSettingsView $r13 = (WazeSettingsView) findViewById(C1283R.id.commuteModelDayToWorkCheck);
        $r13.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_HOME_TO_WORK));
        WazeSettingsView $r14 = (WazeSettingsView) findViewById(C1283R.id.commuteModelDayToWorkTime);
        $r14.setKeyText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_DEPARTURE));
        if (this.mDay.toWorkValid()) {
            $r14.setLine1(formatRange(this.mDay.toWorkStart, this.mDay.toWorkEnd));
            $r13.setValue(true);
        } else {
            $r14.setLine1(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_UNAVAILABLE));
            $r14.setVisiblyEnabled(false);
            $r13.setValue(false);
        }
        $r14.setLine2(null);
        $r14.setValueText(null);
        final WazeSettingsView wazeSettingsView = $r14;
        final WazeSettingsView wazeSettingsView2 = $r13;
        C16281 c16281 = new View.OnClickListener() {
            public void onClick(View v) throws  {
                int $i0 = 0;
                int $i1 = 0;
                if (CommuteModelDayActivity.this.mDay.toWorkValid()) {
                    $i0 = CommuteModelDayActivity.this.mDay.toWorkStart;
                    $i1 = CommuteModelDayActivity.this.mDay.toWorkEnd;
                } else if (CommuteModelDayActivity.this.mDefaultDay.toWorkValid()) {
                    $i0 = CommuteModelDayActivity.this.mDefaultDay.toWorkStart;
                    $i1 = CommuteModelDayActivity.this.mDefaultDay.toWorkEnd;
                }
                final TimeRangeSelector $r2 = new TimeRangeSelector(CommuteModelDayActivity.this, $i0, $i1);
                $r2.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) throws  {
                        if ($r2.finishedOk()) {
                            wazeSettingsView.setVisiblyEnabled(true);
                            CommuteModelDayActivity.this.mDay.toWorkStart = $r2.getStartTime();
                            CommuteModelDayActivity.this.mDay.toWorkEnd = $r2.getEndTime();
                            wazeSettingsView.setLine1(CommuteModelDayActivity.this.formatRange(CommuteModelDayActivity.this.mDay.toWorkStart, CommuteModelDayActivity.this.mDay.toWorkEnd));
                            wazeSettingsView2.setValue(true);
                        } else if (!CommuteModelDayActivity.this.mDay.toWorkValid()) {
                            wazeSettingsView2.setValue(false);
                            wazeSettingsView.setVisiblyEnabled(false);
                        }
                    }
                });
                $r2.show();
            }
        };
        $r14.setOnClickListener(c16281);
        wazeSettingsView = $r14;
        wazeSettingsView2 = $r13;
        final C16281 c162812 = c16281;
        $r13.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean $z0) throws  {
                if (!$z0) {
                    wazeSettingsView.setVisiblyEnabled(false);
                } else if (CommuteModelDayActivity.this.mDay.toWorkValid()) {
                    wazeSettingsView.setVisiblyEnabled(true);
                } else {
                    wazeSettingsView2.setValue(false);
                    c162812.onClick(null);
                }
            }
        });
        $r13 = (WazeSettingsView) findViewById(C1283R.id.commuteModelDayToHomeCheck);
        $r13.setText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_WORK_TO_HOME));
        $r14 = (WazeSettingsView) findViewById(C1283R.id.commuteModelDayToHomeTime);
        $r14.setKeyText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_DEPARTURE));
        if (this.mDay.toHomeValid()) {
            $r14.setLine1(formatRange(this.mDay.toHomeStart, this.mDay.toHomeEnd));
            $r13.setValue(true);
        } else {
            $r14.setLine1(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_UNAVAILABLE));
            $r14.setVisiblyEnabled(false);
            $r13.setValue(false);
        }
        $r14.setLine2(null);
        $r14.setValueText(null);
        wazeSettingsView = $r14;
        wazeSettingsView2 = $r13;
        C16313 c16313 = new View.OnClickListener() {
            public void onClick(View v) throws  {
                int $i0 = 0;
                int $i1 = 0;
                if (CommuteModelDayActivity.this.mDay.toHomeValid()) {
                    $i0 = CommuteModelDayActivity.this.mDay.toHomeStart;
                    $i1 = CommuteModelDayActivity.this.mDay.toHomeEnd;
                } else if (CommuteModelDayActivity.this.mDefaultDay.toHomeValid()) {
                    $i0 = CommuteModelDayActivity.this.mDefaultDay.toHomeStart;
                    $i1 = CommuteModelDayActivity.this.mDefaultDay.toHomeEnd;
                }
                final TimeRangeSelector $r2 = new TimeRangeSelector(CommuteModelDayActivity.this, $i0, $i1);
                $r2.setOnDismissListener(new OnDismissListener() {
                    public void onDismiss(DialogInterface dialog) throws  {
                        if ($r2.finishedOk()) {
                            wazeSettingsView.setVisiblyEnabled(true);
                            CommuteModelDayActivity.this.mDay.toHomeStart = $r2.getStartTime();
                            CommuteModelDayActivity.this.mDay.toHomeEnd = $r2.getEndTime();
                            wazeSettingsView.setLine1(CommuteModelDayActivity.this.formatRange(CommuteModelDayActivity.this.mDay.toHomeStart, CommuteModelDayActivity.this.mDay.toHomeEnd));
                            wazeSettingsView2.setValue(true);
                        } else if (!CommuteModelDayActivity.this.mDay.toHomeValid()) {
                            wazeSettingsView2.setValue(false);
                            wazeSettingsView.setVisiblyEnabled(false);
                        }
                    }
                });
                $r2.show();
            }
        };
        $r14.setOnClickListener(c16313);
        wazeSettingsView = $r14;
        wazeSettingsView2 = $r13;
        final C16313 c163132 = c16313;
        $r13.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean $z0) throws  {
                if (!$z0) {
                    wazeSettingsView.setVisiblyEnabled(false);
                } else if (CommuteModelDayActivity.this.mDay.toHomeValid()) {
                    wazeSettingsView.setVisiblyEnabled(true);
                } else {
                    wazeSettingsView2.setValue(false);
                    c163132.onClick(null);
                }
            }
        });
        ((TextView) findViewById(C1283R.id.commuteModelDayComment)).setText(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_COMMENT));
        CarpoolNativeManager $r6 = this.mCpnm;
        int $i0 = CarpoolNativeManager.UH_CARPOOL_USER;
        IncomingHandler $r18 = this.mHandler;
        $r6.setUpdateHandler($i0, $r18);
        $r6 = this.mCpnm;
        $i0 = CarpoolNativeManager.UH_CARPOOL_ERROR;
        $r18 = this.mHandler;
        $r6.setUpdateHandler($i0, $r18);
    }

    protected void onDestroy() throws  {
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_ERROR, this.mHandler);
        super.onDestroy();
    }

    private void showError() throws  {
        MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new C16335());
    }

    public void onBackPressed() throws  {
        if (!((WazeSettingsView) findViewById(C1283R.id.commuteModelDayToWorkCheck)).isOn()) {
            CarpoolCommuteDay $r3 = this.mDay;
            this.mDay.toWorkEnd = -1;
            $r3.toWorkStart = -1;
        }
        if (!((WazeSettingsView) findViewById(C1283R.id.commuteModelDayToHomeCheck)).isOn()) {
            $r3 = this.mDay;
            this.mDay.toHomeEnd = -1;
            $r3.toHomeStart = -1;
        }
        if (this.mDay.equals(this.mOriginalDay)) {
            super.onBackPressed();
            return;
        }
        this.mCpnm.updateCommuteModelDay(this.mDayNumber, this.mDay.toWorkStart, this.mDay.toWorkEnd, this.mDay.toHomeStart, this.mDay.toHomeEnd);
        this.bSentRequest = true;
        this.mTimeoutRunnable = new C16346();
        postDelayed(this.mTimeoutRunnable, NETWORK_TIMEOUT);
        this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            if (this.bSentRequest) {
                this.bSentRequest = false;
                Bundle $r2 = $r1.getData();
                cancel(this.mTimeoutRunnable);
                this.mNm.CloseProgressPopup();
                if ($r2.getBoolean("success")) {
                    showPopup(this.mNm.getLanguageString((int) DisplayStrings.DS_COMMUTE_MODEL_DAY_CONFIRMATION), "sign_up_big_v", -1);
                } else {
                    showError();
                }
            }
            return true;
        }
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_ERROR) {
            cancel(this.mTimeoutRunnable);
            this.mNm.CloseProgressPopup();
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ERROR_SHOWN, "ERROR", AnalyticsEvents.ANALYTICS_EVENT_VALUE_OTHER);
            showError();
        }
        return super.myHandleMessage($r1);
    }
}
