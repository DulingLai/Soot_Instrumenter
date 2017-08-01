package com.waze.view.popups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.view.timer.TimerView;

public class EtaUpdatePopUp extends PopUp {
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;

    class C31391 implements OnClickListener {
        C31391() {
        }

        public void onClick(View v) {
            EtaUpdatePopUp.this.onClose();
        }
    }

    class C31402 implements Runnable {
        C31402() {
        }

        public void run() {
            EtaUpdatePopUp.this.findViewById(C1283R.id.etaUpdatePopupTitleText).requestLayout();
            EtaUpdatePopUp.this.findViewById(C1283R.id.etaUpdatePopupTitleText).invalidate();
        }
    }

    public EtaUpdatePopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    private void setUpTitle(String titleText) {
        ((TextView) findViewById(C1283R.id.etaUpdatePopupTitleText)).setText(titleText);
    }

    private void setUpBackground(int style) {
        View infoView = findViewById(C1283R.id.etaUpdatePopupTitleLayout);
        if (style == 0) {
            infoView.setBackgroundResource(C1283R.drawable.bg_eta_change_title_green);
        } else if (style == 1) {
            infoView.setBackgroundResource(C1283R.drawable.bg_eta_change_title_red);
        } else {
            infoView.setBackgroundResource(C1283R.drawable.bg_eta_change_title_blue);
        }
    }

    private void setText(String header, String body) {
        ((TextView) findViewById(C1283R.id.etaUpdatePopupHeader)).setText(header);
        ((TextView) findViewById(C1283R.id.etaUpdatePopupBody)).setText(body);
    }

    private void stopCloseTimer() {
        ((TimerView) findViewById(C1283R.id.etaUpdatePopupCloseButtonTimer)).stop();
    }

    private void setCloseTimer(int iTimeOut) {
        ((TimerView) findViewById(C1283R.id.etaUpdatePopupCloseButtonTimer)).reset();
        ((TimerView) findViewById(C1283R.id.etaUpdatePopupCloseButtonTimer)).setWhiteColor();
        ((TimerView) findViewById(C1283R.id.etaUpdatePopupCloseButtonTimer)).setTime(iTimeOut);
        ((TimerView) findViewById(C1283R.id.etaUpdatePopupCloseButtonTimer)).start();
    }

    public void hide() {
        this.mIsShown = false;
        stopCloseTimer();
        this.mLayoutManager.dismiss(this);
    }

    private void onClose() {
        hide();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.eta_update_popup, this);
        findViewById(C1283R.id.etaUpdatePopupCloseButton).setOnClickListener(new C31391());
    }

    public void show(int style, String title, String header, String body, int timeout) {
        if (this.mIsShown) {
            hide();
        }
        setUpTitle(title);
        setUpBackground(style);
        setText(header, body);
        this.mIsShown = true;
        LayoutParams p = new LayoutParams(-1, -2);
        p.addRule(2, C1283R.id.MainBottomBarLayout);
        p.addRule(14);
        this.mLayoutManager.addView(this, p);
        setCloseTimer(timeout);
        postDelayed(new C31402(), 1);
    }
}
