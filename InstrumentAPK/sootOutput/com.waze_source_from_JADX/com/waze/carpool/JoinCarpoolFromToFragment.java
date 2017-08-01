package com.waze.carpool;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.ifs.ui.PointsView;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AutocompleteSearchActivity;
import com.waze.navigate.PublicMacros;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.utils.DisplayUtils;
import com.waze.utils.DisplayUtils.OnOrientationReallyChanged;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;

public final class JoinCarpoolFromToFragment extends Fragment implements ITrackOrientation {
    private static final int RQ_SEARCH_FROM = 1001;
    private static final int RQ_SEARCH_TO = 1002;
    private AddressItem aiFrom;
    private AddressItem aiTo;
    boolean mCalledFromPush = false;
    private View mContainer;
    private int mCurPage = 1;
    private View mPage1;
    private View mPage2;
    private int mWidth;
    CarpoolOnboardingManager obManager;

    class C16521 implements OnClickListener {
        C16521() throws  {
        }

        public void onClick(View v) throws  {
            JoinCarpoolFromToFragment.this.searchClicked(3, 1001);
        }
    }

    class C16532 implements OnClickListener {
        C16532() throws  {
        }

        public void onClick(View v) throws  {
            JoinCarpoolFromToFragment.this.searchClicked(4, 1002);
        }
    }

    class C16543 implements OnClickListener {
        C16543() throws  {
        }

        public void onClick(View v) throws  {
            JoinCarpoolFromToFragment.this.slideLeft();
        }
    }

    class C16554 implements OnClickListener {
        C16554() throws  {
        }

        public void onClick(View v) throws  {
        }
    }

    class C16565 implements OnClickListener {
        C16565() throws  {
        }

        public void onClick(View v) throws  {
        }
    }

    class C16576 implements OnClickListener {
        C16576() throws  {
        }

        public void onClick(View v) throws  {
            JoinCarpoolFromToFragment.this.joinRequested();
        }
    }

    class C16587 implements Runnable {
        C16587() throws  {
        }

        public void run() throws  {
            JoinCarpoolFromToFragment.this.setWidths();
        }
    }

    class C16608 implements Runnable {

        class C16591 extends AnimationEndListener {
            C16591() throws  {
            }

            public void onAnimationEnd(Animation animation) throws  {
                JoinCarpoolFromToFragment.this.mContainer.clearAnimation();
            }
        }

        C16608() throws  {
        }

        public void run() throws  {
            TranslateAnimation $r1 = r0;
            TranslateAnimation translateAnimation = new TranslateAnimation(0, (float) JoinCarpoolFromToFragment.this.mWidth, 0, 0.0f, 0, 0.0f, 0, 0.0f);
            $r1.setDuration(200);
            $r1.setInterpolator(new AccelerateDecelerateInterpolator());
            $r1.setAnimationListener(new C16591());
            JoinCarpoolFromToFragment.this.mContainer.startAnimation($r1);
        }
    }

    class C16619 implements INextActionCallback {
        C16619() throws  {
        }

        public void setNextIntent(Intent $r1) throws  {
            if ($r1 != null) {
                JoinCarpoolFromToFragment.this.startActivityForResult($r1, CarpoolOnboardingManager.REQ_CARPOOL_JOIN_ACTIVITY);
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

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle savedInstanceState) throws  {
        Logger.m36d("JoinCarpoolFromToFragment");
        this.obManager = CarpoolOnboardingManager.getInstance();
        savedInstanceState = getArguments();
        if (savedInstanceState != null) {
            this.mCalledFromPush = savedInstanceState.getBoolean("called_from_push", false);
        }
        View $r5 = $r1.inflate(C1283R.layout.join_carpool_frag_from_to, $r2, false);
        this.mContainer = $r5.findViewById(C1283R.id.joinCarpoolFromToContainer);
        this.mPage1 = $r5.findViewById(C1283R.id.joinCarpoolFromToPage1);
        this.mPage2 = $r5.findViewById(C1283R.id.joinCarpoolFromToPage2);
        setupPage(this.mPage1, C1283R.drawable.to_from_onboarding_addresses, DisplayStrings.DS_JOIN_CARPOOL_FT_ROUTE_TITLE, DisplayStrings.DS_JOIN_CARPOOL_FT_FROM, "", new C16521(), DisplayStrings.DS_JOIN_CARPOOL_FT_TO, "", new C16532(), DisplayStrings.DS_JOIN_CARPOOL_FT_ROUTE_BUTTON, new C16543());
        setupPage(this.mPage2, C1283R.drawable.to_from_onboarding_time, DisplayStrings.DS_JOIN_CARPOOL_FT_TIME_TITLE, DisplayStrings.DS_JOIN_CARPOOL_FT_DAY, "", new C16554(), DisplayStrings.DS_JOIN_CARPOOL_FT_TIME, "", new C16565(), DisplayStrings.DS_JOIN_CARPOOL_FT_TIME_BUTTON, new C16576());
        DisplayUtils.runOnLayout(this.mContainer, new C16587());
        MainActivity $r14 = AppService.getMainActivity();
        if ($r14 == null) {
            return $r5;
        }
        $r14.addOrientationTracker(this);
        return $r5;
    }

    private void setWidths() throws  {
        int $i0 = 0;
        View $r1 = getView();
        View $r2 = $r1;
        if ($r1 == null) {
            $r2 = (View) this.mContainer.getParent();
        }
        this.mWidth = $r2.getMeasuredWidth();
        LayoutParams $r5 = (LayoutParams) this.mPage1.getLayoutParams();
        $r5.width = this.mWidth;
        this.mPage1.setLayoutParams($r5);
        $r5 = (LayoutParams) this.mPage2.getLayoutParams();
        $r5.width = this.mWidth;
        $r5.leftMargin = this.mWidth;
        this.mPage2.setLayoutParams($r5);
        this.mPage2.setVisibility(0);
        $r5 = (LayoutParams) this.mContainer.getLayoutParams();
        if (this.mCurPage != 1) {
            $i0 = -this.mWidth;
        }
        $r5.leftMargin = $i0;
        this.mContainer.setLayoutParams($r5);
    }

    private void setupPage(View $r1, int $i0, int $i1, int $i2, String $r2, OnClickListener $r3, int $i3, String $r4, OnClickListener $r5, int $i4, OnClickListener $r6) throws  {
        $r1.findViewById(C1283R.id.joinCarpoolImage).setBackgroundResource($i0);
        ((TextView) $r1.findViewById(C1283R.id.joinCarpoolSubtitle)).setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_B_VERIFY_SUBTITLE));
        ((TextView) $r1.findViewById(C1283R.id.joinCarpoolText)).setText(DisplayStrings.displayString(DisplayStrings.DS_JOIN_CARPOOL_B_VERIFY_SUBTITLE_BODY));
        ((TextView) $r1.findViewById(C1283R.id.joinCarpoolBottomTitle)).setText(DisplayStrings.displayString($i1));
        ((WazeSettingsView) $r1.findViewById(C1283R.id.joinCarpoolFrom)).setKeyText(DisplayStrings.displayString($i2)).setValueText($r2).setClickOnEdit(true).setOnClickListener($r3);
        ((WazeSettingsView) $r1.findViewById(C1283R.id.joinCarpoolTo)).setKeyText(DisplayStrings.displayString($i3)).setValueText($r4).setClickOnEdit(true).setOnClickListener($r5);
        ((TextView) $r1.findViewById(C1283R.id.joinCarpoolButton)).setText(DisplayStrings.displayString($i4));
        $r1.findViewById(C1283R.id.joinCarpoolButton).setOnClickListener($r6);
    }

    private void slideLeft() throws  {
        if (this.aiFrom == null || this.aiTo == null) {
            PointsView $r4;
            if (this.aiFrom == null) {
                $r4 = ((WazeSettingsView) this.mPage1.findViewById(C1283R.id.joinCarpoolFrom)).getValidation();
                $r4.setVisibility(0);
                $r4.setPoints(0, true);
                $r4.setIsOn(false, true, false);
                $r4.setValid(false);
            }
            if (this.aiTo == null) {
                $r4 = ((WazeSettingsView) this.mPage1.findViewById(C1283R.id.joinCarpoolTo)).getValidation();
                $r4.setVisibility(0);
                $r4.setPoints(0, true);
                $r4.setIsOn(false, true, false);
                $r4.setValid(false);
                return;
            }
            return;
        }
        this.mCurPage = 2;
        LayoutParams $r6 = (LayoutParams) this.mContainer.getLayoutParams();
        $r6.leftMargin = -this.mWidth;
        this.mContainer.setLayoutParams($r6);
        DisplayUtils.runOnLayout(this.mContainer, new C16608());
    }

    private void joinRequested() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ONBOARDING_BECOME_DRIVER_CLICKED);
        this.obManager.getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, new C16619());
    }

    public void onOrientationChanged(int $i0) throws  {
        View $r2 = getView();
        if ($r2 != null) {
            DisplayUtils.lmkWhenOrientationReallyChanged($r2, $i0, new OnOrientationReallyChanged() {
                public void onChanged(int orientation) throws  {
                    JoinCarpoolFromToFragment.this.setWidths();
                }
            });
        }
    }

    private void updateField(WazeSettingsView $r1, AddressItem $r2) throws  {
        if ($r2 == null) {
            $r1.setValueText("");
            $r1.setSelectorImage(0);
        } else if (!$r2.getAddress().isEmpty()) {
            $r1.setValueText($r2.getAddress());
        } else if ($r2.getSecondaryTitle().isEmpty()) {
            $r1.setValueText($r2.getTitle());
        } else {
            $r1.setValueText($r2.getSecondaryTitle());
        }
    }

    public void searchClicked(int type, int $i1) throws  {
        Intent $r1 = new Intent(getActivity(), AutocompleteSearchActivity.class);
        $r1.putExtra(PublicMacros.SKIP_PREVIEW, true);
        $r1.putExtra(PublicMacros.SEARCH_MODE, 12);
        startActivityForResult($r1, $i1);
    }

    public void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if (($i0 != 1001 && $i0 != 1002) || $i1 != -1) {
            super.onActivityResult($i0, $i1, $r1);
        } else if ($r1 != null) {
            AddressItem $r4 = (AddressItem) $r1.getExtras().get("ai");
            if ($r4 != null) {
                if ($r4.getAddress().isEmpty()) {
                    $r4.setAddress($r4.getSecondaryTitle());
                }
                WazeSettingsView $r7;
                if ($i0 == 1001) {
                    this.aiFrom = $r4;
                    $r7 = (WazeSettingsView) this.mPage1.findViewById(C1283R.id.joinCarpoolFrom);
                    updateField($r7, $r4);
                    $r7.getValidation().setVisibility(8);
                    return;
                }
                this.aiTo = $r4;
                $r7 = (WazeSettingsView) this.mPage1.findViewById(C1283R.id.joinCarpoolTo);
                updateField($r7, $r4);
                $r7.getValidation().setVisibility(8);
            }
        }
    }
}
