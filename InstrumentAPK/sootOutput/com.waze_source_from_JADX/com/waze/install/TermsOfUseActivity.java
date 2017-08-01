package com.waze.install;

import android.app.backup.BackupManager;
import android.app.backup.RestoreObserver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.phone.LoginOptionsActivity;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import java.util.Locale;

public final class TermsOfUseActivity extends ActivityBase {
    public static final String EXTRA_LEAD_TO_SIGNUP = "lead_to_signup";
    private boolean mLeadToSignup;
    private FrameLayout mScrollToBottomButton;
    private TermsOfUserScrollView mScrollView;
    private NativeManager nativeManager;
    private InstallNativeManager nm;

    class C18341 implements OnClickListener {
        C18341() throws  {
        }

        public void onClick(View v) throws  {
            TermsOfUseActivity.this.mScrollView.fullScroll(130);
        }
    }

    class C18352 extends RestoreObserver {
        C18352() throws  {
        }

        public void restoreStarting(int $i0) throws  {
            super.restoreStarting($i0);
        }

        public void restoreFinished(int $i0) throws  {
            super.restoreFinished($i0);
        }
    }

    class C18363 implements OnClickListener {
        C18363() throws  {
        }

        public void onClick(View v) throws  {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_AGREEMENT_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_SUCCESS, true);
            TermsOfUseActivity.this.nm.termsOfUseResponse(1);
            if (TermsOfUseActivity.this.mLeadToSignup) {
                Intent $r2 = new Intent(TermsOfUseActivity.this, LoginOptionsActivity.class);
                $r2.putExtra(LoginOptionsActivity.EXTRA_IS_INSTALL_FLOW, true);
                TermsOfUseActivity.this.startActivity($r2);
            } else {
                MyWazeNativeManager.getInstance().skipSignup();
                NativeManager.getInstance().signup_finished();
            }
            TermsOfUseActivity.this.setResult(-1);
            TermsOfUseActivity.this.finish();
        }
    }

    class C18374 implements OnClickListener {
        C18374() throws  {
        }

        public void onClick(View v) throws  {
            NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_AGREEMENT_RESPONSE, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_FAILURE, true);
            TermsOfUseActivity.this.nm.termsOfUseResponse(0);
        }
    }

    public static class TermsOfUserScrollView extends ScrollView {
        private boolean mIsHidden;
        private View mViewToHide;

        public TermsOfUserScrollView(Context $r1) throws  {
            super($r1);
        }

        public TermsOfUserScrollView(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
        }

        public TermsOfUserScrollView(Context $r1, AttributeSet $r2, int $i0) throws  {
            super($r1, $r2, $i0);
        }

        public void setViewToHide(View $r1) throws  {
            this.mViewToHide = $r1;
        }

        protected void onScrollChanged(int $i0, int $i1, int $i2, int $i3) throws  {
            super.onScrollChanged($i0, $i1, $i2, $i3);
            if (getChildAt(getChildCount() - 1).getBottom() - (getHeight() + getScrollY()) <= PixelMeasure.dp(16)) {
                hideView();
            } else {
                showView();
            }
        }

        private void showView() throws  {
            if (this.mIsHidden) {
                this.mIsHidden = false;
                this.mViewToHide.animate().cancel();
                this.mViewToHide.setVisibility(0);
                this.mViewToHide.setAlpha(0.0f);
                ViewPropertyAnimatorHelper.initAnimation(this.mViewToHide).alpha(1.0f).setListener(null);
            }
        }

        private void hideView() throws  {
            if (!this.mIsHidden) {
                this.mIsHidden = true;
                this.mViewToHide.animate().cancel();
                this.mViewToHide.setAlpha(1.0f);
                ViewPropertyAnimatorHelper.initAnimation(this.mViewToHide).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mViewToHide));
            }
        }
    }

    public void onCreate(Bundle $r1) throws  {
        TextView $r13;
        super.onCreate($r1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.terms_of_use);
        this.mScrollToBottomButton = (FrameLayout) findViewById(C1283R.id.btnScrollToBottom);
        this.mScrollView = (TermsOfUserScrollView) findViewById(C1283R.id.termsOfUseScrollView);
        this.mScrollToBottomButton.setOnClickListener(new C18341());
        this.mScrollView.setViewToHide(this.mScrollToBottomButton);
        this.nativeManager = AppService.getNativeManager();
        this.mLeadToSignup = getIntent().getBooleanExtra(EXTRA_LEAD_TO_SIGNUP, false);
        try {
            new BackupManager(this).requestRestore(new C18352());
        } catch (Exception e) {
        }
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_AGREEMENT, null, null, true);
        if (Locale.getDefault().getCountry().equalsIgnoreCase("IL")) {
            $r13 = (TextView) findViewById(C1283R.id.termsOfUse);
            $r13.setText(C1283R.string.termsOfUseHeb);
        } else {
            $r13 = (TextView) findViewById(C1283R.id.termsOfUse);
        }
        findViewById(C1283R.id.AcceptButton).setOnClickListener(new C18363());
        findViewById(C1283R.id.DeclineButton).setOnClickListener(new C18374());
        ((TextView) findViewById(C1283R.id.TermsOfUseTitle)).setText(this.nativeManager.getLanguageString("Waze™ End User License Agreement"));
        ((TextView) findViewById(C1283R.id.AcceptButtonText)).setText(this.nativeManager.getLanguageString(277));
        ((TextView) findViewById(C1283R.id.DeclineButtonTextText)).setText(this.nativeManager.getLanguageString(278));
        try {
            Linkify.addLinks($r13, 1);
        } catch (NullPointerException e2) {
            Logger.m38e("A NullPointerException occurred in TOS screen");
        }
        this.nm = new InstallNativeManager();
    }

    public void onBackPressed() throws  {
    }
}
