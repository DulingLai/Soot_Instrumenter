package com.waze.view.popups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.navbar.NavBar;
import com.waze.settings.SettingsCheckboxView;
import com.waze.strings.DisplayStrings;
import com.waze.view.timer.TimerView;

public class UpdateGasPopup extends PopUp {
    private static LayoutManager mLayoutManager;
    private SettingsCheckboxView NeverShowAgain;
    private long callback;
    private boolean mIsShown = false;
    private boolean mTimerSet = false;

    class C32461 implements Runnable {

        class C32451 implements Runnable {
            C32451() {
            }

            public void run() {
                AppService.getMainActivity().getLayoutMgr().showGasReport();
            }
        }

        C32461() {
        }

        public void run() {
            AppService.getMainActivity().post(new C32451());
        }
    }

    class C32494 implements OnCheckedChangeListener {
        C32494() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            NativeManager.getInstance().SetShowGasPricePopupAgain(!isChecked);
        }
    }

    public UpdateGasPopup(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        mLayoutManager = layoutManager;
        init();
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    public void setUpButtonsTxt() {
        ((TextView) findViewById(C1283R.id.UpdatePopUpButtonText)).setText(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_UPDATE_PRICES));
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.update_gas_popup, this);
        setUpButtonsTxt();
    }

    private void setTitle(String title) {
        ((TextView) findViewById(C1283R.id.alerterTitleText)).setText(title);
    }

    public void dismiss() {
        this.mIsShown = false;
        this.mTimerSet = false;
        mLayoutManager.dismiss(this);
    }

    public void hide() {
        ((TimerView) findViewById(C1283R.id.CloseButtonTimer)).stop();
        dismiss();
    }

    public void setCloseTime(int timer) {
        if (!this.mTimerSet) {
            ((TimerView) findViewById(C1283R.id.CloseButtonTimer)).setWhiteColor();
            ((TimerView) findViewById(C1283R.id.CloseButtonTimer)).setTime(timer);
            ((TimerView) findViewById(C1283R.id.CloseButtonTimer)).start();
            this.mTimerSet = true;
            float scale = getResources().getDisplayMetrics().density;
            View closeButton = findViewById(C1283R.id.CloseButtonImage);
            LayoutParams layoutParams = closeButton.getLayoutParams();
            layoutParams.width = (int) (30.0f * scale);
            closeButton.setLayoutParams(layoutParams);
        }
    }

    private void OnUpdatePrices() {
        NativeManager.Post(new C32461());
        hide();
    }

    public void show(long cb, long lcontext) {
        if (this.mIsShown) {
            hide();
        }
        this.callback = cb;
        final long j = cb;
        final long j2 = lcontext;
        findViewById(C1283R.id.CloseButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UpdateGasPopup.this.hide();
                NativeManager.getInstance().NativeManagerCallback(4, j, j2);
            }
        });
        ((TimerView) findViewById(C1283R.id.CloseButtonTimer)).reset();
        j = cb;
        j2 = lcontext;
        findViewById(C1283R.id.UpdatePopUpButton).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                UpdateGasPopup.this.OnUpdatePrices();
                NativeManager.getInstance().NativeManagerCallback(3, j, j2);
            }
        });
        setTitle(NativeManager.getInstance().getLanguageString(205));
        ((TextView) findViewById(C1283R.id.UpdatePriceTextBody)).setText(NativeManager.getInstance().getLanguageString(253));
        this.NeverShowAgain = (SettingsCheckboxView) findViewById(C1283R.id.UpdatePriceNeverShow);
        this.NeverShowAgain.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NEVER_ASK_ME_AGAIN));
        this.NeverShowAgain.setValue(false);
        this.NeverShowAgain.setOnCheckedChangeListener(new C32494());
        RelativeLayout layout = (RelativeLayout) findViewById(C1283R.id.alerterLayout);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) layout.getLayoutParams();
        NavBar navBar = NativeManager.getInstance().getNavBarManager().getNavBar();
        int topMargin = 0;
        if (navBar != null) {
            topMargin = navBar.getNavBarHeight();
        }
        params.setMargins(0, topMargin, 0, 0);
        layout.setLayoutParams(params);
        this.mIsShown = true;
        mLayoutManager.addView(this);
    }
}
