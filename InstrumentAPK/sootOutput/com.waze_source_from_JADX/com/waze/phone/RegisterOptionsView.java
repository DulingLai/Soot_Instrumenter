package com.waze.phone;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class RegisterOptionsView extends FrameLayout {
    private TextView mDescriptionLabel;
    private LinearLayout mFacebookButton;
    private TextView mFacebookLabel;
    private boolean mIsHiding;
    private RegisterOptionsListener mListener;
    private LinearLayout mMainContent;
    private LinearLayout mPhoneButton;
    private TextView mPhoneLabel;
    private TextView mTitleLabel;

    class C23051 implements OnClickListener {
        C23051() {
        }

        public void onClick(View v) {
            if (RegisterOptionsView.this.mListener != null) {
                RegisterOptionsView.this.mListener.onFacebookClick();
            }
            RegisterOptionsView.this.close();
        }
    }

    class C23062 implements OnClickListener {
        C23062() {
        }

        public void onClick(View v) {
            if (RegisterOptionsView.this.mListener != null) {
                RegisterOptionsView.this.mListener.onPhoneClick();
            }
            RegisterOptionsView.this.close();
        }
    }

    class C23073 implements OnClickListener {
        C23073() {
        }

        public void onClick(View v) {
            RegisterOptionsView.this.close();
        }
    }

    class C23084 implements OnClickListener {
        C23084() {
        }

        public void onClick(View v) {
        }
    }

    class C23095 implements Runnable {
        C23095() {
        }

        public void run() {
            ((ViewGroup) RegisterOptionsView.this.getParent()).removeView(RegisterOptionsView.this);
            if (RegisterOptionsView.this.mListener != null) {
                RegisterOptionsView.this.mListener.onClosed();
            }
        }
    }

    public interface RegisterOptionsListener {
        void onClosed();

        void onFacebookClick();

        void onPhoneClick();
    }

    public RegisterOptionsView(Context context) {
        this(context, null);
    }

    public RegisterOptionsView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RegisterOptionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.register_options_view, null);
        this.mFacebookButton = (LinearLayout) content.findViewById(C1283R.id.btnFacebook);
        this.mPhoneButton = (LinearLayout) content.findViewById(C1283R.id.btnPhone);
        this.mTitleLabel = (TextView) content.findViewById(C1283R.id.lblTitle);
        this.mDescriptionLabel = (TextView) content.findViewById(C1283R.id.lblDescription);
        this.mFacebookLabel = (TextView) content.findViewById(C1283R.id.lblFacebook);
        this.mPhoneLabel = (TextView) content.findViewById(C1283R.id.lblPhone);
        this.mMainContent = (LinearLayout) content.findViewById(C1283R.id.mainContent);
        this.mFacebookButton.setOnClickListener(new C23051());
        this.mPhoneButton.setOnClickListener(new C23062());
        content.setOnClickListener(new C23073());
        this.mMainContent.setOnClickListener(new C23084());
        LayoutParams params = (LayoutParams) this.mMainContent.getLayoutParams();
        params.width = Math.min(PixelMeasure.dp(360), getResources().getDisplayMetrics().widthPixels - PixelMeasure.dp(64));
        this.mMainContent.setLayoutParams(params);
        addView(content);
        setDisplayStrings();
    }

    public void setListener(RegisterOptionsListener listener) {
        this.mListener = listener;
    }

    public void close() {
        if (!this.mIsHiding) {
            this.mIsHiding = true;
            ViewPropertyAnimatorHelper.initAnimation(this).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createAnimationEndListener(new C23095()));
        }
    }

    private void setDisplayStrings() {
        this.mTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_REGISTER_OPTIONS_TITLE));
        this.mDescriptionLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_REGISTER_OPTIONS_DESCRIPTION));
        this.mFacebookLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_REGISTER_OPTIONS_FACEBOOK));
        this.mPhoneLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_REGISTER_OPTIONS_PHONE));
    }

    public static RegisterOptionsView showRegisterOptionsView(Activity activity, RegisterOptionsListener listener) {
        RegisterOptionsView registerOptionsView = new RegisterOptionsView(activity);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -1);
        registerOptionsView.setLayoutParams(layoutParams);
        registerOptionsView.setListener(listener);
        activity.addContentView(registerOptionsView, layoutParams);
        registerOptionsView.setAlpha(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(registerOptionsView).alpha(1.0f);
        return registerOptionsView;
    }
}
