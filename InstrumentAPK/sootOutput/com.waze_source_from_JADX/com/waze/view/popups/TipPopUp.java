package com.waze.view.popups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.ResManager;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.timer.TimerView;

public class TipPopUp extends PopUp {
    private TipPopUp mInstance = null;
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;

    class C32301 implements OnClickListener {
        C32301() {
        }

        public void onClick(View v) {
            TipPopUp.this.onClose();
        }
    }

    class C32312 implements OnClickListener {
        C32312() {
        }

        public void onClick(View v) {
            TipPopUp.this.onClose();
        }
    }

    class C32323 implements OnClickListener {
        C32323() {
        }

        public void onClick(View v) {
            TipPopUp.this.onClose();
        }
    }

    public TipPopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    private void setUpButtonsTxt() {
    }

    public void hide() {
        this.mLayoutManager.dismiss(this);
        this.mIsShown = false;
    }

    private void onClose() {
        hide();
    }

    private void setUpforRTL() {
        if (AppService.getNativeManager().getLanguageRtl()) {
            ((TextView) findViewById(C1283R.id.tipText)).setGravity(5);
            ((TextView) findViewById(C1283R.id.tipTitleText)).setGravity(5);
        }
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.tip, this);
        setUpButtonsTxt();
        setUpforRTL();
        findViewById(C1283R.id.CloseButton).setOnClickListener(new C32301());
    }

    private void setTitleText(String titleTxt) {
        ((TextView) findViewById(C1283R.id.tipTitleText)).setText(titleTxt);
    }

    private void setTipText(String tipText) {
        ((TextView) findViewById(C1283R.id.tipText)).setText(tipText);
    }

    private void setRightTitleText(String titleTxt) {
        ((TextView) findViewById(C1283R.id.tipRightTitle)).setText(titleTxt);
    }

    private void setRightTipText(String tipText) {
        ((TextView) findViewById(C1283R.id.tipRightText)).setText(tipText);
    }

    private void setLongTitleText(String titleTxt) {
        ((TextView) findViewById(C1283R.id.tipTitleLongText)).setText(titleTxt);
    }

    private void setLongTipText(String tipText) {
        ((TextView) findViewById(C1283R.id.tipLongText)).setText(tipText);
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    public void show(String title, String tipText, boolean alignToRight, String icon) {
        if (this.mIsShown) {
            this.mInstance.hide();
        }
        if (alignToRight) {
            findViewById(C1283R.id.ShortTextTip).setVisibility(8);
            findViewById(C1283R.id.RightTip).setVisibility(0);
            setRightTitleText(title);
            setRightTipText(tipText);
            ImageView iv = (ImageView) findViewById(C1283R.id.tipRightImage);
            if (icon == null || icon.length() <= 0) {
                iv.setImageDrawable(null);
            } else {
                iv.setImageDrawable(ResManager.GetSkinDrawable(icon + ResManager.mImageExtension));
            }
            findViewById(C1283R.id.CloseButtonRight).setOnClickListener(new C32312());
            ((TimerView) findViewById(C1283R.id.CloseButtonRightTimer)).reset();
            ((TimerView) findViewById(C1283R.id.CloseButtonRightTimer)).setWhiteColor();
            ((TimerView) findViewById(C1283R.id.CloseButtonRightTimer)).setTime(15);
            ((TimerView) findViewById(C1283R.id.CloseButtonRightTimer)).start();
        } else if (title == null || title.length() <= 6) {
            setTitleText(title);
            setTipText(tipText);
        } else {
            findViewById(C1283R.id.ShortTextTip).setVisibility(8);
            findViewById(C1283R.id.RightTip).setVisibility(8);
            findViewById(C1283R.id.LongTextTip).setVisibility(0);
            setLongTitleText(title);
            setLongTipText(tipText);
            findViewById(C1283R.id.CloseButtonLong).setOnClickListener(new C32323());
            ((TimerView) findViewById(C1283R.id.CloseButtonTimerLong)).reset();
            ((TimerView) findViewById(C1283R.id.CloseButtonTimerLong)).setWhiteColor();
            ((TimerView) findViewById(C1283R.id.CloseButtonTimerLong)).setTime(15);
            ((TimerView) findViewById(C1283R.id.CloseButtonTimerLong)).start();
        }
        this.mIsShown = true;
        this.mLayoutManager.addView(this);
        bringToFront();
        if (alignToRight) {
            AnimationUtils.openAnimateFromPoint(this.mInstance, (float) AppService.getDisplay().getWidth(), (float) AppService.getDisplay().getHeight(), 300);
        } else {
            AnimationUtils.openAnimateFromPoint(this.mInstance, 0.0f, (float) AppService.getDisplay().getHeight(), 300);
        }
    }
}
