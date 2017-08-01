package com.waze.view.navbar;

import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.content.Context;
import android.graphics.Outline;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.SettingsSound;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;

public class NavBarSoundButton extends FrameLayout {
    private static final int LARGE_ASSET_INDEX = 1;
    private static final int SMALL_ASSET_INDEX = 0;
    private static final int[][][] SOUND_ICONS;
    private OnClickListener mClickListener;
    private View mExpandedClickView;
    private boolean mIsExpanded;
    private ImageView mLargeImage;
    private float mLastRatio;
    private View mMinimizedClickView;
    private ImageView mSmallImage;

    class C30271 implements OnClickListener {
        C30271() {
        }

        public void onClick(View v) {
            NavBarSoundButton.this.performClickAction();
        }
    }

    class C30282 extends ViewOutlineProvider {
        C30282() {
        }

        public void getOutline(View view, Outline outline) {
            int top = (int) (((float) (-view.getMeasuredHeight())) * NavBarSoundButton.this.mLastRatio);
            if (VERSION.SDK_INT >= 21) {
                outline.setOval(0, top, (int) (((float) view.getMeasuredWidth()) * (1.0f + NavBarSoundButton.this.mLastRatio)), view.getMeasuredHeight());
            }
        }
    }

    class C30293 implements Runnable {
        C30293() {
        }

        public void run() {
            ViewGroup parent = (ViewGroup) NavBarSoundButton.this.getParent();
            NavBarSoundButton.this.mExpandedClickView = parent.findViewById(C1283R.id.navBarSoundButtonExpandedTouchView);
            NavBarSoundButton.this.mMinimizedClickView = parent.findViewById(C1283R.id.navBarSoundButtonMinimizedTouchView);
            NavBarSoundButton.this.mMinimizedClickView.setOnClickListener(NavBarSoundButton.this.mClickListener);
        }
    }

    class C30315 implements AnimatorUpdateListener {
        C30315() {
        }

        public void onAnimationUpdate(ValueAnimator animation) {
            NavBarSoundButton.this.adjustSoundButtonAnimationPhase(((Float) animation.getAnimatedValue()).floatValue());
        }
    }

    static {
        r0 = new int[2][][];
        r0[0] = new int[][]{new int[]{C1283R.drawable.sounds_mini_on_night, C1283R.drawable.sounds_mini_alerts_only_night, C1283R.drawable.sounds_mini_mute_night}, new int[]{C1283R.drawable.sounds_mini_on, C1283R.drawable.sounds_mini_alerts_only, C1283R.drawable.sounds_mini_mute}};
        r0[1] = new int[][]{new int[]{C1283R.drawable.sounds_on_night, C1283R.drawable.sounds_alerts_only_night, C1283R.drawable.sounds_mute_night}, new int[]{C1283R.drawable.sounds_on, C1283R.drawable.sounds_alerts_only, C1283R.drawable.sounds_mute}};
        SOUND_ICONS = r0;
    }

    public NavBarSoundButton(Context context) {
        this(context, null);
    }

    public NavBarSoundButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NavBarSoundButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mClickListener = new C30271();
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.nav_bar_sound_button, null);
        this.mSmallImage = (ImageView) content.findViewById(C1283R.id.imgSmallSoundIcon);
        this.mLargeImage = (ImageView) content.findViewById(C1283R.id.imgLargeSoundIcon);
        addView(content);
        if (VERSION.SDK_INT >= 21) {
            setOutlineProvider(new C30282());
        }
        setClipChildren(false);
        setClipToPadding(false);
        post(new C30293());
    }

    public void bringToFront() {
        super.bringToFront();
        if (this.mExpandedClickView != null) {
            this.mExpandedClickView.bringToFront();
        }
        if (this.mMinimizedClickView != null) {
            this.mMinimizedClickView.bringToFront();
        }
    }

    public void setTranslationX(float translationX) {
        super.setTranslationX(translationX);
        if (this.mExpandedClickView != null) {
            this.mExpandedClickView.setTranslationX(translationX);
        }
        if (this.mMinimizedClickView != null) {
            this.mMinimizedClickView.setTranslationX(translationX);
        }
    }

    public void setTranslationY(float translationY) {
        super.setTranslationY(translationY);
        if (this.mExpandedClickView != null) {
            this.mExpandedClickView.setTranslationY(translationY);
        }
        if (this.mMinimizedClickView != null) {
            this.mMinimizedClickView.setTranslationY(translationY);
        }
    }

    private void performClickAction() {
        if (getVisibility() == 0 && AppService.getMainActivity() != null) {
            AppService.getMainActivity().openSoundActions();
        }
    }

    public void adjustSoundButton() {
        boolean isDayMode = DriveToNativeManager.getInstance().isDayMode();
        final int soundSelection = SettingsSound.getSoundSelectionFromConfig();
        final int dayModeIndex = isDayMode ? 1 : 0;
        post(new Runnable() {
            public void run() {
                NavBarSoundButton.this.mSmallImage.setImageResource(NavBarSoundButton.SOUND_ICONS[0][dayModeIndex][soundSelection]);
                NavBarSoundButton.this.mLargeImage.setImageResource(NavBarSoundButton.SOUND_ICONS[1][dayModeIndex][soundSelection]);
            }
        });
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        this.mLargeImage.setPivotX(0.0f);
        this.mLargeImage.setPivotY((float) getMeasuredHeight());
        this.mSmallImage.setPivotX(0.0f);
        this.mSmallImage.setPivotY((float) getMeasuredHeight());
    }

    public void adjustSoundButtonAnimationPhase(float ratio) {
        this.mLastRatio = ratio;
        if (ratio <= 0.0f) {
            this.mSmallImage.setVisibility(0);
            this.mSmallImage.setAlpha(1.0f);
            this.mSmallImage.setScaleX(1.0f);
            this.mSmallImage.setScaleY(1.0f);
            this.mLargeImage.setVisibility(8);
            setTranslationX(0.0f);
            if (VERSION.SDK_INT >= 21) {
                setElevation(0.0f);
            }
            if (this.mIsExpanded) {
                this.mIsExpanded = false;
                this.mExpandedClickView.setOnClickListener(null);
                return;
            }
            return;
        }
        int i;
        float alphaRatio = Math.max(0.0f, 1.0f - (4.0f * ratio));
        ImageView imageView = this.mSmallImage;
        if (alphaRatio == 0.0f) {
            i = 8;
        } else {
            i = 0;
        }
        imageView.setVisibility(i);
        this.mSmallImage.setAlpha(alphaRatio);
        this.mLargeImage.setVisibility(0);
        float scale = 1.0f + ratio;
        this.mLargeImage.setScaleX(scale);
        this.mLargeImage.setScaleY(scale);
        this.mSmallImage.setScaleX(scale);
        this.mSmallImage.setScaleY(scale);
        setTranslationX(((float) PixelMeasure.dp(32)) * ratio);
        if (VERSION.SDK_INT >= 21) {
            setElevation(((float) PixelMeasure.dp(8)) * ratio);
            invalidateOutline();
        }
        if (!this.mIsExpanded) {
            this.mIsExpanded = true;
            this.mExpandedClickView.setOnClickListener(this.mClickListener);
        }
    }

    public void animateSoundButton(boolean animateToGrow, boolean withAnimation) {
        float from = this.mLastRatio;
        float to = animateToGrow ? 1.0f : 0.0f;
        if (withAnimation) {
            ValueAnimator animator = ValueAnimator.ofFloat(new float[]{from, to});
            animator.addUpdateListener(new C30315());
            animator.setDuration((long) (Math.abs(from - to) * 300.0f));
            animator.setInterpolator(ViewPropertyAnimatorHelper.STANDARD_INTERPOLATOR);
            animator.start();
            return;
        }
        adjustSoundButtonAnimationPhase(to);
    }

    public void setSmallSoundButtonVisibility(int visibility) {
        if (this.mSmallImage.getVisibility() != visibility) {
            this.mSmallImage.setVisibility(visibility);
        }
    }
}
