package com.waze.view.anim;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ListView;
import android.widget.ScrollView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.CPUProfiler;
import com.waze.LayoutManager;
import com.waze.animation.easing.AnimationEasingManager;
import com.waze.animation.easing.AnimationEasingManager.EaseType;
import com.waze.animation.easing.Cubic;
import com.waze.map.CanvasFont;
import com.waze.navigate.DriveToNativeManager;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import com.waze.view.button.PillButton;
import java.lang.ref.WeakReference;

public class AnimationUtils {
    static final long NAV_RES_DURATION_CLOSE = 250;
    static final long NAV_RES_DURATION_OPEN = 150;
    private static final int TOOLTIP_ANIM_DURATION = 500;
    public static final int UH_SELF_DESTRUCT = TicketRoller.get(Type.Handler);

    private static class AnimationEndHandler extends Handler {
        private final WeakReference<Runnable> mRun;

        public AnimationEndHandler(Runnable r) {
            this.mRun = new WeakReference(r);
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == AnimationUtils.UH_SELF_DESTRUCT) {
                DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_ANIMATION_ENDED, this);
                this.mRun.clear();
                removeCallbacksAndMessages(null);
            }
            if (msg.what == DriveToNativeManager.UH_ANIMATION_ENDED) {
                DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_ANIMATION_ENDED, this);
                Runnable r = (Runnable) this.mRun.get();
                if (r != null) {
                    r.run();
                }
                this.mRun.clear();
                removeCallbacksAndMessages(null);
            }
        }
    }

    public static abstract class AnimationEndListener implements AnimationListener {
        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    static class StartShape {
        int bottom = (this.top + this.height);
        int height;
        int top;
        int width;

        StartShape(Context ctx) {
            DisplayMetrics ds = ctx.getResources().getDisplayMetrics();
            this.width = ds.widthPixels / 2;
            this.top = ds.heightPixels;
            this.height = ((int) ds.density) * 10;
        }
    }

    public static void closeAnimateAlpha(View view, int duration, AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
        alphaAnimation.setDuration((long) duration);
        animation.addAnimation(alphaAnimation);
        animation.setAnimationListener(listener);
        view.startAnimation(animation);
    }

    public static void closeAnimateMenu(final View view, final AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 1.1f);
        scaleAnimation.setDuration(100);
        animation.addAnimation(scaleAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -0.1f);
        translateAnimation.setDuration(100);
        animation.addAnimation(translateAnimation);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                AnimationSet animation2 = new AnimationSet(true);
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.9f, 0.0f, 1.1f, 0.0f);
                scaleAnimation2.setDuration((long) 200);
                animation2.addAnimation(scaleAnimation2);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.1f, 1, -0.1f, 1, 1.0f);
                translateAnimation2.setDuration((long) 200);
                animation2.addAnimation(translateAnimation2);
                animation2.setAnimationListener(listener);
                view.startAnimation(animation2);
            }
        });
        view.startAnimation(animation);
    }

    public static void closeAnimateReport(final View view, final AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.9f, 1.0f, 1.1f);
        scaleAnimation.setDuration(100);
        animation.addAnimation(scaleAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.1f, 1, 0.0f, 1, -0.1f);
        translateAnimation.setDuration(100);
        animation.addAnimation(translateAnimation);
        animation.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                AnimationSet animation2 = new AnimationSet(true);
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(0.9f, 0.0f, 1.1f, 0.0f);
                scaleAnimation2.setDuration((long) 200);
                animation2.addAnimation(scaleAnimation2);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.1f, 1, 0.9f, 1, -0.1f, 1, 1.0f);
                translateAnimation2.setDuration((long) 200);
                animation2.addAnimation(translateAnimation2);
                animation2.setAnimationListener(listener);
                view.startAnimation(animation2);
            }
        });
        view.startAnimation(animation);
    }

    public static void closeAnimateToPoint(View view, float x, float y, int duration, AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        animation.setDuration((long) duration);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.setFillAfter(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f);
        scaleAnimation.setDuration((long) duration);
        scaleAnimation.setFillAfter(true);
        animation.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.2f);
        alphaAnimation.setDuration((long) duration);
        animation.addAnimation(alphaAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, x, 0, 0.0f, 0, y);
        translateAnimation.setDuration((long) duration);
        animation.addAnimation(translateAnimation);
        animation.setAnimationListener(listener);
        view.startAnimation(animation);
    }

    public static void openAnimateMenu(final View view, final AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, PillButton.PRESSED_SCALE_DOWN, 0.0f, 1.03f);
        scaleAnimation.setDuration(100);
        animation.addAnimation(scaleAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.1f, 1, 0.0f, 1, 1.0f, 1, -0.03f);
        translateAnimation.setDuration(100);
        animation.addAnimation(translateAnimation);
        animation.setAnimationListener(new AnimationListener() {

            class C29481 implements AnimationListener {
                C29481() {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation aAnimation) {
                    AnimationSet animation3 = new AnimationSet(true);
                    ScaleAnimation scaleAnimation3 = new ScaleAnimation(1.02f, 1.0f, 0.97f, 1.0f);
                    scaleAnimation3.setDuration(100);
                    animation3.addAnimation(scaleAnimation3);
                    TranslateAnimation translateAnimation3 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.03f, 1, 0.0f);
                    translateAnimation3.setDuration(100);
                    animation3.addAnimation(translateAnimation3);
                    if (listener != null) {
                        animation3.setAnimationListener(listener);
                    }
                    view.startAnimation(animation3);
                }
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation aAnimation) {
                AnimationSet animation2 = new AnimationSet(true);
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(PillButton.PRESSED_SCALE_DOWN, 1.02f, 1.03f, 0.97f);
                scaleAnimation2.setDuration(100);
                animation2.addAnimation(scaleAnimation2);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -0.03f, 1, 0.03f);
                translateAnimation2.setDuration(100);
                animation2.addAnimation(translateAnimation2);
                animation2.setAnimationListener(new C29481());
                view.startAnimation(animation2);
            }
        });
        view.setDrawingCacheEnabled(true);
        view.startAnimation(animation);
    }

    public static void openAnimateAlert(View view, AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -2.0f);
        translateAnimation.setDuration(NAV_RES_DURATION_OPEN);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.addAnimation(translateAnimation);
        animation.setAnimationListener(listener);
        view.setDrawingCacheEnabled(true);
        view.startAnimation(animation);
    }

    public static void openNavigateScreen(View view, AnimationListener listener, boolean isLandscape) {
        Animation animation;
        if (isLandscape) {
            animation = android.view.animation.AnimationUtils.loadAnimation(AppService.getAppContext(), C1283R.anim.slide_in_search_bar_ls);
        } else {
            animation = android.view.animation.AnimationUtils.loadAnimation(AppService.getAppContext(), C1283R.anim.slide_in_search_bar);
        }
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setAnimationListener(listener);
        animation.setFillAfter(true);
        view.setDrawingCacheEnabled(true);
        view.startAnimation(animation);
    }

    public static void openNavigateScreenWithFadeIn(View view) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(AppService.getAppContext(), C1283R.anim.fade_in);
        animation.setInterpolator(new AccelerateInterpolator());
        view.startAnimation(animation);
    }

    public static void SearchBarBackAnimation(View view) {
        AnimationSet animation = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -5.0f, 1, -2.0f);
        translateAnimation.setDuration(NAV_RES_DURATION_OPEN);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.addAnimation(translateAnimation);
        view.setDrawingCacheEnabled(true);
        view.startAnimation(animation);
    }

    public static void openAnimateAlert2(View view, AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -2.0f, 1, 0.0f);
        translateAnimation.setDuration(400);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.addAnimation(translateAnimation);
        animation.setAnimationListener(listener);
        view.setDrawingCacheEnabled(true);
        view.startAnimation(animation);
    }

    public static void openAnimateReport(final View view, final AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, PillButton.PRESSED_SCALE_DOWN, 0.0f, 1.03f);
        scaleAnimation.setDuration(100);
        animation.addAnimation(scaleAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.9f, 1, 0.05f, 1, 1.0f, 1, -0.03f);
        translateAnimation.setDuration(100);
        animation.addAnimation(translateAnimation);
        animation.setAnimationListener(new AnimationListener() {

            class C29501 implements AnimationListener {
                C29501() {
                }

                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationEnd(Animation aAnimation) {
                    AnimationSet animation3 = new AnimationSet(true);
                    ScaleAnimation scaleAnimation3 = new ScaleAnimation(1.02f, 1.0f, 0.97f, 1.0f);
                    scaleAnimation3.setDuration(100);
                    animation3.addAnimation(scaleAnimation3);
                    TranslateAnimation translateAnimation3 = new TranslateAnimation(1, -0.02f, 1, 0.0f, 1, 0.03f, 1, 0.0f);
                    translateAnimation3.setDuration(100);
                    animation3.addAnimation(translateAnimation3);
                    if (listener != null) {
                        animation3.setAnimationListener(listener);
                    }
                    view.startAnimation(animation3);
                }
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation aAnimation) {
                AnimationSet animation2 = new AnimationSet(true);
                ScaleAnimation scaleAnimation2 = new ScaleAnimation(PillButton.PRESSED_SCALE_DOWN, 1.02f, 1.03f, 0.97f);
                scaleAnimation2.setDuration(100);
                animation2.addAnimation(scaleAnimation2);
                TranslateAnimation translateAnimation2 = new TranslateAnimation(1, 0.05f, 1, -0.02f, 1, -0.03f, 1, 0.03f);
                translateAnimation2.setDuration(100);
                animation2.addAnimation(translateAnimation2);
                animation2.setAnimationListener(new C29501());
                view.startAnimation(animation2);
            }
        });
        view.setDrawingCacheEnabled(true);
        view.startAnimation(animation);
    }

    public static void openAnimateFromPoint(View view, float x, float y, int duration, AnimationListener listener) {
        AnimationSet animation = new AnimationSet(true);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f);
        scaleAnimation.setDuration((long) duration);
        animation.addAnimation(scaleAnimation);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration((long) duration);
        animation.addAnimation(alphaAnimation);
        TranslateAnimation translateAnimation = new TranslateAnimation(0, x, 0, 0.0f, 0, y, 0, 0.0f);
        translateAnimation.setDuration((long) duration);
        animation.addAnimation(translateAnimation);
        if (listener != null) {
            animation.setAnimationListener(listener);
        }
        view.setDrawingCacheEnabled(true);
        view.startAnimation(animation);
    }

    public static void openNavResultsFromRectangle(View view, AnimationListener listener) {
        if (CPUProfiler.shouldAnimate()) {
            AnimationSet animation = new AnimationSet(false);
            StartShape shape = new StartShape(view.getContext());
            ScaleAnimation scaleAnimation = new ScaleAnimation(((float) shape.width) / ((float) view.getWidth()), 1.0f, ((float) shape.height) / ((float) view.getHeight()), 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, 0.0f);
            scaleAnimation.setDuration(NAV_RES_DURATION_OPEN);
            scaleAnimation.setFillBefore(true);
            scaleAnimation.setInterpolator(AnimationEasingManager.getInterpolator(Cubic.class, EaseType.EaseOut));
            animation.addAnimation(scaleAnimation);
            int[] n = new int[2];
            view.getLocationOnScreen(n);
            TranslateAnimation translateAnimation = new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, (float) (shape.top - n[1]), 0, 0.0f);
            translateAnimation.setDuration(NAV_RES_DURATION_OPEN);
            translateAnimation.setInterpolator(AnimationEasingManager.getInterpolator(Cubic.class, EaseType.EaseOut));
            animation.addAnimation(translateAnimation);
            AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
            alphaAnimation.setDuration(50);
            alphaAnimation.setInterpolator(new LinearInterpolator());
            animation.addAnimation(alphaAnimation);
            if (listener != null) {
                animation.setAnimationListener(listener);
            }
            view.startAnimation(animation);
        } else if (listener != null) {
            listener.onAnimationEnd(null);
        }
    }

    public static void closeNavResultsToRectangle(View view, AnimationListener listener) {
        if (CPUProfiler.shouldAnimate()) {
            AnimationSet animation = new AnimationSet(true);
            StartShape shape = new StartShape(view.getContext());
            animation.addAnimation(new ScaleAnimation(1.0f, ((float) shape.width) / ((float) view.getWidth()), 1.0f, ((float) shape.height) / ((float) view.getHeight()), 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, 0.0f));
            int[] n = new int[2];
            view.getLocationOnScreen(n);
            animation.addAnimation(new TranslateAnimation(0, 0.0f, 0, 0.0f, 0, 0.0f, 0, (float) (shape.top - n[1])));
            animation.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            animation.setDuration(250);
            animation.setInterpolator(new AnticipateInterpolator());
            if (listener != null) {
                animation.setAnimationListener(listener);
            }
            view.startAnimation(animation);
        } else if (listener != null) {
            listener.onAnimationEnd(null);
        }
    }

    public static void openAnimateFromPoint(View view, float x, float y, int duration) {
        openAnimateFromPoint(view, x, y, duration, null);
    }

    public static void stretchViewHorizontally(View view, int duration) {
        AnimationSet animation = new AnimationSet(false);
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.0f, 1.0f, 1.0f, 1.0f);
        scaleAnimation.setDuration((long) duration);
        animation.addAnimation(scaleAnimation);
        view.startAnimation(animation);
    }

    public static void overshootCustomPopView(View view) {
        Animation animation = android.view.animation.AnimationUtils.loadAnimation(AppService.getAppContext(), C1283R.anim.pop_in_event_on_route);
        animation.setFillAfter(true);
        view.startAnimation(animation);
    }

    public static void overshootNativePopView(View view) {
        view.startAnimation(android.view.animation.AnimationUtils.loadAnimation(AppService.getAppContext(), C1283R.anim.pop_in_popup));
    }

    public static void slideTooltip(final View toolTipLayout) {
        TranslateAnimation layoutTranslateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, 0.0f);
        layoutTranslateAnimation.setDuration(500);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1.0f);
        alphaAnimation.setDuration(500);
        final TranslateAnimation textTranslateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 2, 0.1f, 1, 0.0f);
        textTranslateAnimation.setDuration(500);
        AnimationSet as = new AnimationSet(true);
        as.addAnimation(layoutTranslateAnimation);
        as.addAnimation(alphaAnimation);
        as.setInterpolator(new DecelerateInterpolator());
        as.setDuration(500);
        toolTipLayout.startAnimation(as);
        as.setAnimationListener(new AnimationListener() {

            class C29521 implements Runnable {
                C29521() {
                }

                public void run() {
                    toolTipLayout.findViewById(C1283R.id.tipText).startAnimation(textTranslateAnimation);
                }
            }

            public void onAnimationStart(Animation animation) {
                AppService.Post(new C29521(), 50);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                AnimationUtils.shakeTooltip(toolTipLayout);
            }
        });
    }

    private static void shakeTooltip(View toolTipLayout) {
        TranslateAnimation breathUpTranslateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 2, 0.0f, 1, -0.05f);
        breathUpTranslateAnimation.setDuration(1500);
        breathUpTranslateAnimation.setRepeatCount(9);
        breathUpTranslateAnimation.setRepeatMode(2);
        breathUpTranslateAnimation.setInterpolator(new DecelerateInterpolator());
        breathUpTranslateAnimation.setFillAfter(true);
        toolTipLayout.startAnimation(breathUpTranslateAnimation);
    }

    private static void setNextAnimation(final View v, Animation a, final Animation next) {
        a.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                v.startAnimation(next);
            }
        });
    }

    public static void flashView(View view) {
        ScaleAnimation sa1 = new ScaleAnimation(1.0f, 1.3f, 1.0f, 1.3f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa1.setDuration(80);
        sa1.setRepeatCount(1);
        sa1.setRepeatMode(2);
        sa1.setInterpolator(new AccelerateInterpolator());
        ScaleAnimation sa2 = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa2.setDuration(80);
        sa1.setRepeatCount(1);
        sa1.setRepeatMode(2);
        sa2.setInterpolator(new AccelerateInterpolator(1.0f));
        setNextAnimation(view, sa1, sa2);
        ScaleAnimation sa3 = new ScaleAnimation(1.4f, 1.0f, 1.4f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa3.setDuration(320);
        sa3.setInterpolator(new DecelerateInterpolator());
        setNextAnimation(view, sa2, sa3);
        view.startAnimation(sa1);
    }

    public static boolean isViewVisible(ScrollView sv, View v) {
        Rect scrollBounds = new Rect();
        sv.getHitRect(scrollBounds);
        return v.getLocalVisibleRect(scrollBounds);
    }

    public static void focusOnView(ScrollView sv, View v) {
        focusOnView(sv, v, -100);
    }

    public static void focusOnView(final ScrollView sv, View v, final int offset_dp) {
        while (v.getParent().getParent() != sv) {
            v = (View) v.getParent();
        }
        final View child = v;
        sv.post(new Runnable() {
            public void run() {
                sv.smoothScrollTo(0, child.getTop() + ((int) (sv.getResources().getDisplayMetrics().density * ((float) offset_dp))));
            }
        });
    }

    public static void scrollPage(final ScrollView sv, View v) {
        int scrollTo;
        while (v.getParent().getParent() != sv) {
            v = (View) v.getParent();
        }
        int getTo = v.getTop() + v.getMeasuredHeight();
        int screen = sv.getMeasuredHeight() - ((int) (sv.getResources().getDisplayMetrics().density * 20.0f));
        int curBottom = sv.getScrollY() + screen;
        if (getTo - curBottom > screen) {
            scrollTo = curBottom;
        } else {
            scrollTo = getTo;
        }
        sv.post(new Runnable() {
            public void run() {
                sv.smoothScrollTo(0, scrollTo);
            }
        });
    }

    public static void setOnMapAnimationEndRunnable(Runnable r) {
        AnimationEndHandler h = new AnimationEndHandler(r);
        DriveToNativeManager.getInstance().setUpdateHandler(DriveToNativeManager.UH_ANIMATION_ENDED, h);
        h.sendMessageDelayed(h.obtainMessage(UH_SELF_DESTRUCT), 5000);
    }

    public static void growViewToFullsize(final View view) {
        int start = view.getHeight();
        LayoutParams p = view.getLayoutParams();
        p.height = -2;
        view.setLayoutParams(p);
        view.measure(MeasureSpec.makeMeasureSpec(view.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
        int target = view.getMeasuredHeight();
        p.height = start;
        view.setLayoutParams(p);
        ValueAnimator anim = ValueAnimator.ofInt(new int[]{start, target});
        anim.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                int val = ((Integer) valueAnimator.getAnimatedValue()).intValue();
                LayoutParams p = view.getLayoutParams();
                p.height = val;
                view.setLayoutParams(p);
            }
        });
        anim.setDuration(300);
        anim.setInterpolator(new DecelerateInterpolator());
        anim.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) {
            }

            public void onAnimationRepeat(Animator animation) {
            }

            public void onAnimationEnd(Animator animation) {
                LayoutParams p = view.getLayoutParams();
                p.height = -2;
                view.setLayoutParams(p);
            }

            public void onAnimationCancel(Animator animation) {
            }
        });
        anim.start();
    }

    @TargetApi(21)
    public static void api21RippleInit(View v) {
        v.setBackground(new RippleDrawable(ColorStateList.valueOf(v.getResources().getColor(C1283R.color.blue_bg)), v.getBackground(), null));
    }

    @TargetApi(21)
    public static void api21RippleInit(View v, int bgColor, int rippleColor) {
        v.setBackground(new RippleDrawable(ColorStateList.valueOf(rippleColor), new ColorDrawable(bgColor), null));
    }

    @TargetApi(21)
    public static void api21RippleInit(View v, Drawable bgDraw, int rippleColor) {
        v.setBackground(new RippleDrawable(ColorStateList.valueOf(rippleColor), bgDraw, null));
    }

    public static void viewBgInit(View v, int bgColor, int rippleColor, int touchColor) {
        if (VERSION.SDK_INT >= 21) {
            api21RippleInit(v, bgColor, rippleColor);
            return;
        }
        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{16842919}, new ColorDrawable(touchColor));
        sld.addState(new int[0], new ColorDrawable(bgColor));
        v.setBackgroundDrawable(sld);
    }

    public static void viewBgInit(View v, Drawable bgDraw, int rippleColor, int touchColor) {
        if (VERSION.SDK_INT >= 21) {
            api21RippleInit(v, bgDraw, rippleColor);
            return;
        }
        StateListDrawable sld = new StateListDrawable();
        sld.addState(new int[]{16842919}, new ColorDrawable(touchColor));
        sld.addState(new int[0], bgDraw);
        v.setBackgroundDrawable(sld);
    }

    @TargetApi(21)
    public static void api21RippleInitList(ListView list) {
        list.setSelector(new RippleDrawable(ColorStateList.valueOf(list.getResources().getColor(C1283R.color.blue_bg)), new ColorDrawable(-1), null));
    }

    public static void hoverAnimation(View v) {
        TranslateAnimation ta1 = new TranslateAnimation(1, 0.0f, 1, 0.005f, 1, 0.0f, 1, -0.01f);
        ta1.setDuration(1300);
        ta1.setInterpolator(new AccelerateDecelerateInterpolator());
        ta1.setRepeatMode(2);
        ta1.setRepeatCount(-1);
        TranslateAnimation ta2 = new TranslateAnimation(1, 0.0f, 1, -0.005f, 1, 0.0f, 1, 0.01f);
        ta2.setDuration(500);
        ta2.setInterpolator(new AccelerateDecelerateInterpolator());
        ta2.setRepeatMode(2);
        ta2.setRepeatCount(-1);
        TranslateAnimation ta3 = new TranslateAnimation(1, 0.0f, 1, -0.01f, 1, 0.0f, 1, 0.005f);
        ta3.setDuration(700);
        ta3.setInterpolator(new AccelerateDecelerateInterpolator());
        ta3.setRepeatMode(2);
        ta3.setRepeatCount(-1);
        TranslateAnimation ta4 = new TranslateAnimation(1, 0.0f, 1, 0.01f, 1, 0.0f, 1, -0.005f);
        ta4.setDuration(1100);
        ta4.setInterpolator(new AccelerateDecelerateInterpolator());
        ta4.setRepeatMode(2);
        ta4.setRepeatCount(-1);
        AnimationSet set = new AnimationSet(false);
        set.addAnimation(ta1);
        set.addAnimation(ta2);
        set.addAnimation(ta3);
        set.addAnimation(ta4);
        v.startAnimation(set);
    }

    public static void pulseAnimation(View v) {
        ScaleAnimation sa = new ScaleAnimation(1.0f, LayoutManager.REPORT_SCALE_ON_PRESS, 1.0f, LayoutManager.REPORT_SCALE_ON_PRESS, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(1300);
        sa.setInterpolator(new AccelerateDecelerateInterpolator());
        sa.setRepeatMode(2);
        sa.setRepeatCount(-1);
        v.startAnimation(sa);
    }

    public static int mixColors(int color1, int color2, float ratio) {
        return Color.argb(mixChannel(Color.alpha(color1), Color.alpha(color2), ratio), mixChannel(Color.red(color1), Color.red(color2), ratio), mixChannel(Color.green(color1), Color.green(color2), ratio), mixChannel(Color.blue(color1), Color.blue(color2), ratio));
    }

    private static int mixChannel(int c1, int c2, float ratio) {
        return Math.round((((float) c1) * ratio) + (((float) c2) * (1.0f - ratio)));
    }
}
