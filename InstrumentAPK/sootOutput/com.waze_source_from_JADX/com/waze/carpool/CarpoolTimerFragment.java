package com.waze.carpool;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.config.ConfigValues;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import java.lang.ref.WeakReference;
import java.util.Locale;

public class CarpoolTimerFragment extends Fragment {
    private static final long UPDATE_TIMER = 1000;
    CarpoolNativeManager mCpnm;
    private INextActionCallback mGetAnswerCb = new C15961();
    Handler mHandler = new MyHandler(this);
    private boolean mIsOnboarding = false;
    private Runnable mUpdateRunnable = new C15972();
    String statCampaign = "";

    class C15961 implements INextActionCallback {
        C15961() throws  {
        }

        public void setNextIntent(Intent $r1) throws  {
            CarpoolTimerFragment.this.startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
        }

        public void setNextFragment(Fragment $r1) throws  {
            AppService.getMainActivity().getLayoutMgr().getRightSideMenu().replaceCarpoolFragment($r1);
        }

        public void setNextResult(int $i0) throws  {
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                CarpoolTimerFragment.this.getActivity().onBackPressed();
            } else if ($i0 == -1) {
                Logger.m41i("CarpoolRidesFragment: received RESULT OK");
            } else {
                Logger.m38e("CarpoolRidesFragment: received unexpected result:" + $i0);
            }
        }

        public Context getContext() throws  {
            return CarpoolTimerFragment.this.getActivity();
        }
    }

    class C15972 implements Runnable {
        C15972() throws  {
        }

        public void run() throws  {
            CarpoolTimerFragment.this.postDelayed(CarpoolTimerFragment.this.mUpdateRunnable, CarpoolTimerFragment.UPDATE_TIMER);
            View $r4 = CarpoolTimerFragment.this.getView();
            if ($r4 != null) {
                CarpoolTimerFragment.this.updateTime($r4);
            }
        }
    }

    class C15983 implements OnClickListener {
        C15983() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_COUNTDOWN_EDIT_PROFILE_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_UPDATE_PROFILE).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CAMPAIGN, CarpoolTimerFragment.this.statCampaign).send();
            CarpoolTimerFragment.this.startActivityForResult(new Intent(CarpoolTimerFragment.this.getActivity(), CarpoolDriverProfileActivity.class), 0);
        }
    }

    class C15994 implements ITrackOrientation {
        C15994() throws  {
        }

        public void onOrientationChanged(int orientation) throws  {
        }
    }

    private static class MyHandler extends Handler {
        final WeakReference<CarpoolTimerFragment> ref;

        MyHandler(CarpoolTimerFragment $r1) throws  {
            this.ref = new WeakReference($r1);
        }

        public void handleMessage(Message $r1) throws  {
            CarpoolTimerFragment $r4 = (CarpoolTimerFragment) this.ref.get();
            if ($r4 != null) {
                $r4.handleMessage($r1);
            }
            super.handleMessage($r1);
        }
    }

    @Nullable
    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle $r3) throws  {
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.statCampaign = ConfigValues.getStringValue(69);
        if ($r3 == null) {
            String str = "TIME";
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_COUNTDOWN_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_CAMPAIGN, this.statCampaign).addParam(str, (long) this.mCpnm.configGetOpenTimeLeftNTV()).send();
        }
        $r3 = getArguments();
        if ($r3 != null) {
            this.mIsOnboarding = $r3.getBoolean("onboarding", false);
        }
        View $r7 = $r1.inflate(C1283R.layout.carpool_timer, $r2, false);
        ((TextView) $r7.findViewById(C1283R.id.carpoolTimerTitle)).setText(DisplayStrings.displayString(3501));
        ((TextView) $r7.findViewById(C1283R.id.carpoolTimerDays)).setText(DisplayStrings.displayString(3504));
        ((TextView) $r7.findViewById(C1283R.id.carpoolTimerHours)).setText(DisplayStrings.displayString(3505));
        ((TextView) $r7.findViewById(C1283R.id.carpoolTimerMins)).setText(DisplayStrings.displayString(3506));
        ((TextView) $r7.findViewById(C1283R.id.carpoolTimerSecs)).setText(DisplayStrings.displayString(3507));
        ((TextView) $r7.findViewById(C1283R.id.carpoolTimerMeanwhile)).setText(DisplayStrings.displayString(3502));
        TextView $r9 = (TextView) $r7.findViewById(C1283R.id.carpoolTimerUpdate);
        $r9.setText(EditTextUtils.underlineSpan(3503));
        $r9.setOnClickListener(new C15983());
        updateTime($r7);
        return $r7;
    }

    private void updateTime(View $r1) throws  {
        this = this;
        int $i0 = this.mCpnm.configGetOpenTimeLeftNTV();
        int $i3 = $i0;
        if ($i0 <= 0) {
            $i3 = 0;
            MainActivity $r3 = AppService.getMainActivity();
            if ($r3 != null) {
                $r3.getLayoutMgr().refreshCarpoolPanel();
            }
        }
        $i0 = $i3 / 86400;
        int $i1 = ($i3 % 86400) / DisplayStrings.DS_CUSTOM_PROMPT_200_METERS;
        int $i2 = ($i3 % DisplayStrings.DS_CUSTOM_PROMPT_200_METERS) / 60;
        $i3 %= 60;
        Locale $r6 = NativeManager.getInstance().getLocale();
        ((TextView) $r1.findViewById(C1283R.id.carpoolTimerNumDays)).setText(String.format($r6, "%02d", new Object[]{Integer.valueOf($i0)}));
        ((TextView) $r1.findViewById(C1283R.id.carpoolTimerNumHours)).setText(String.format($r6, "%02d", new Object[]{Integer.valueOf($i1)}));
        ((TextView) $r1.findViewById(C1283R.id.carpoolTimerNumMins)).setText(String.format($r6, "%02d", new Object[]{Integer.valueOf($i2)}));
        ((TextView) $r1.findViewById(C1283R.id.carpoolTimerNumSecs)).setText(String.format($r6, "%02d", new Object[]{Integer.valueOf($i3)}));
    }

    public void onActivityCreated(Bundle $r1) throws  {
        super.onActivityCreated($r1);
        if ($r1 != null) {
            setupFragment();
        } else {
            setupFragment();
        }
    }

    public void onResume() throws  {
        super.onResume();
        postDelayed(this.mUpdateRunnable, UPDATE_TIMER);
        CarpoolUserData $r3 = this.mCpnm.getCarpoolProfileNTV();
        boolean $z0 = $r3 == null || !$r3.didFinishOnboarding();
        if ($z0) {
            Activity $r4 = getActivity();
            if ($r4 instanceof MainActivity) {
                ((MainActivity) $r4).getLayoutMgr().refreshCarpoolPanel();
            }
        }
    }

    public void onPause() throws  {
        this.mHandler.removeCallbacks(this.mUpdateRunnable);
        super.onPause();
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
    }

    public boolean onBackPressed() throws  {
        if (!this.mIsOnboarding) {
            return false;
        }
        CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.mGetAnswerCb);
        return true;
    }

    public void setupFragment() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            $r2.addOrientationTracker(new C15994());
        }
    }

    public void handleMessage(Message msg) throws  {
        if (!isAdded()) {
        }
    }

    void postDelayed(Runnable $r1, long $l0) throws  {
        this.mHandler.postDelayed($r1, $l0);
    }

    public void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        super.onActivityResult($i0, $i1, $r1);
    }
}
