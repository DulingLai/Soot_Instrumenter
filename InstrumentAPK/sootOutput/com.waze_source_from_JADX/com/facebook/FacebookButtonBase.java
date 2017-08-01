package com.facebook;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.facebook.appevents.AppEventsLogger;

public abstract class FacebookButtonBase extends Button {
    private String analyticsButtonCreatedEventName;
    private String analyticsButtonTappedEventName;
    private OnClickListener externalOnClickListener;
    private OnClickListener internalOnClickListener;
    private boolean overrideCompoundPadding;
    private int overrideCompoundPaddingLeft;
    private int overrideCompoundPaddingRight;
    private Fragment parentFragment;

    class C04801 implements OnClickListener {
        C04801() throws  {
        }

        public void onClick(View $r1) throws  {
            FacebookButtonBase.this.logButtonTapped(FacebookButtonBase.this.getContext());
            if (FacebookButtonBase.this.internalOnClickListener != null) {
                FacebookButtonBase.this.internalOnClickListener.onClick($r1);
            } else if (FacebookButtonBase.this.externalOnClickListener != null) {
                FacebookButtonBase.this.externalOnClickListener.onClick($r1);
            }
        }
    }

    protected abstract int getDefaultRequestCode() throws ;

    protected int getDefaultStyleResource() throws  {
        return 0;
    }

    protected FacebookButtonBase(Context $r1, AttributeSet $r2, int $i0, int $i1, String $r3, String $r4) throws  {
        super($r1, $r2, 0);
        if ($i1 == 0) {
            $i1 = getDefaultStyleResource();
        }
        if ($i1 == 0) {
            $i1 = C0496R.style.com_facebook_button;
        }
        configureButton($r1, $r2, $i0, $i1);
        this.analyticsButtonCreatedEventName = $r3;
        this.analyticsButtonTappedEventName = $r4;
    }

    public void setFragment(Fragment $r1) throws  {
        this.parentFragment = $r1;
    }

    public Fragment getFragment() throws  {
        return this.parentFragment;
    }

    public void setOnClickListener(OnClickListener $r1) throws  {
        this.externalOnClickListener = $r1;
    }

    public int getRequestCode() throws  {
        return getDefaultRequestCode();
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        if (!isInEditMode()) {
            logButtonCreated(getContext());
        }
    }

    protected void onDraw(Canvas $r1) throws  {
        if ((getGravity() & 1) != 0) {
            int $i3 = getCompoundPaddingLeft();
            int $i2 = getCompoundPaddingRight();
            int $i1 = Math.min((((getWidth() - ($i3 + getCompoundDrawablePadding())) - $i2) - measureTextWidth(getText().toString())) / 2, ($i3 - getPaddingLeft()) / 2);
            this.overrideCompoundPaddingLeft = $i3 - $i1;
            this.overrideCompoundPaddingRight = $i2 + $i1;
            this.overrideCompoundPadding = true;
        }
        super.onDraw($r1);
        this.overrideCompoundPadding = false;
    }

    public int getCompoundPaddingLeft() throws  {
        if (this.overrideCompoundPadding) {
            return this.overrideCompoundPaddingLeft;
        }
        return super.getCompoundPaddingLeft();
    }

    public int getCompoundPaddingRight() throws  {
        if (this.overrideCompoundPadding) {
            return this.overrideCompoundPaddingRight;
        }
        return super.getCompoundPaddingRight();
    }

    protected Activity getActivity() throws  {
        Context $r1 = getContext();
        while (!($r1 instanceof Activity) && ($r1 instanceof ContextWrapper)) {
            $r1 = ((ContextWrapper) $r1).getBaseContext();
        }
        if ($r1 instanceof Activity) {
            return (Activity) $r1;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    protected int measureTextWidth(String $r1) throws  {
        return (int) Math.ceil((double) getPaint().measureText($r1));
    }

    protected void configureButton(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        parseBackgroundAttributes($r1, $r2, $i0, $i1);
        parseCompoundDrawableAttributes($r1, $r2, $i0, $i1);
        parseContentAttributes($r1, $r2, $i0, $i1);
        parseTextAttributes($r1, $r2, $i0, $i1);
        setupOnClickListener();
    }

    protected void callExternalOnClickListener(View $r1) throws  {
        if (this.externalOnClickListener != null) {
            this.externalOnClickListener.onClick($r1);
        }
    }

    protected void setInternalOnClickListener(OnClickListener $r1) throws  {
        this.internalOnClickListener = $r1;
    }

    private void logButtonCreated(Context $r1) throws  {
        AppEventsLogger.newLogger($r1).logSdkEvent(this.analyticsButtonCreatedEventName, null, null);
    }

    private void logButtonTapped(Context $r1) throws  {
        AppEventsLogger.newLogger($r1).logSdkEvent(this.analyticsButtonTappedEventName, null, null);
    }

    private void parseBackgroundAttributes(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        if (!isInEditMode()) {
            TypedArray $r5 = $r1.getTheme().obtainStyledAttributes($r2, new int[]{16842964}, $i0, $i1);
            try {
                if ($r5.hasValue(0)) {
                    $i0 = $r5.getResourceId(0, 0);
                    if ($i0 != 0) {
                        setBackgroundResource($i0);
                    } else {
                        setBackgroundColor($r5.getColor(0, 0));
                    }
                } else {
                    setBackgroundColor($r5.getColor(0, C0496R.color.com_facebook_blue));
                }
                $r5.recycle();
            } catch (Throwable th) {
                $r5.recycle();
            }
        }
    }

    private void parseCompoundDrawableAttributes(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        TypedArray $r5 = $r1.getTheme().obtainStyledAttributes($r2, new int[]{16843119, 16843117, 16843120, 16843118, 16843121}, $i0, $i1);
        try {
            setCompoundDrawablesWithIntrinsicBounds($r5.getResourceId(0, 0), $r5.getResourceId(1, 0), $r5.getResourceId(2, 0), $r5.getResourceId(3, 0));
            setCompoundDrawablePadding($r5.getDimensionPixelSize(4, 0));
        } finally {
            $r5.recycle();
        }
    }

    private void parseContentAttributes(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        TypedArray $r5 = $r1.getTheme().obtainStyledAttributes($r2, new int[]{16842966, 16842967, 16842968, 16842969}, $i0, $i1);
        try {
            setPadding($r5.getDimensionPixelSize(0, 0), $r5.getDimensionPixelSize(1, 0), $r5.getDimensionPixelSize(2, 0), $r5.getDimensionPixelSize(3, 0));
        } finally {
            $r5.recycle();
        }
    }

    private void parseTextAttributes(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        TypedArray $r5 = $r1.getTheme().obtainStyledAttributes($r2, new int[]{16842904}, $i0, $i1);
        try {
            setTextColor($r5.getColor(0, -1));
            $r5 = $r1.getTheme().obtainStyledAttributes($r2, new int[]{16842927}, $i0, $i1);
            try {
                setGravity($r5.getInt(0, 17));
                $r5 = $r1.getTheme().obtainStyledAttributes($r2, new int[]{16842901, 16842903, 16843087}, $i0, $i1);
                try {
                    setTextSize(0, (float) $r5.getDimensionPixelSize(0, 0));
                    setTypeface(Typeface.defaultFromStyle($r5.getInt(1, 1)));
                    setText($r5.getString(2));
                } finally {
                    $r5.recycle();
                }
            } finally {
                $r5.recycle();
            }
        } finally {
            $r5.recycle();
        }
    }

    private void setupOnClickListener() throws  {
        super.setOnClickListener(new C04801());
    }
}
