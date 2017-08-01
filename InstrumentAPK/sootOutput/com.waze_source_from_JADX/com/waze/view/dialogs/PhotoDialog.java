package com.waze.view.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import com.waze.C1283R;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.utils.ImageRepository;
import com.waze.view.map.ProgressAnimation;

public class PhotoDialog extends Dialog {
    private static final int ANIMATION_DURATION = 200;
    private ActivityBase mActivity;
    private final View mFrame;
    private Bundle mImageLocation = null;

    class C29901 implements OnClickListener {
        C29901() {
        }

        public void onClick(View v) {
            PhotoDialog.this.animateImageOut();
        }
    }

    class C29912 implements OnGlobalLayoutListener {
        C29912() {
        }

        public void onGlobalLayout() {
            PhotoDialog.this.mFrame.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            PhotoDialog.this.animateImageIn();
        }
    }

    class C29923 implements AnimationListener {
        C29923() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationRepeat(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            PhotoDialog.this.dismiss();
        }
    }

    public PhotoDialog(ActivityBase ab, String imageUrl, Bundle imageLocation) {
        super(ab, C1283R.style.ReportDialog);
        this.mActivity = ab;
        setContentView(C1283R.layout.photo_dialog);
        getWindow().setLayout(-1, -1);
        findViewById(C1283R.id.forMarginesOnly).setOnClickListener(new C29901());
        this.mFrame = findViewById(C1283R.id.dialogPhotoFrme);
        calculateSize();
        ProgressAnimation pa = (ProgressAnimation) findViewById(C1283R.id.dialogPhotoProgress);
        if (ImageRepository.instance.isCached(imageUrl)) {
            pa.setVisibility(8);
        } else {
            pa.start();
        }
        ImageView image = (ImageView) findViewById(C1283R.id.dialogPhoto);
        boolean isFile = imageUrl.startsWith("file");
        ImageRepository.instance.getImage(imageUrl, 0, image, pa, this.mActivity);
        if (imageLocation != null) {
            this.mImageLocation = imageLocation;
            this.mFrame.getViewTreeObserver().addOnGlobalLayoutListener(new C29912());
        }
    }

    public void calculateSize() {
        int height = this.mActivity.getResources().getDisplayMetrics().heightPixels - ((int) (this.mActivity.getResources().getDisplayMetrics().density * 40.0f));
        int width = this.mActivity.getResources().getDisplayMetrics().widthPixels - ((int) (this.mActivity.getResources().getDisplayMetrics().density * 10.0f));
        LayoutParams p = (LayoutParams) this.mFrame.getLayoutParams();
        if (width > height) {
            p.height = height;
            p.width = (height * 4) / 3;
        } else {
            p.width = width;
            p.height = (width * 3) / 4;
        }
        this.mFrame.setLayoutParams(p);
    }

    private void animateImageIn() {
        int left = this.mImageLocation.getInt("left");
        int top = this.mImageLocation.getInt("top");
        int width = this.mImageLocation.getInt("width");
        int height = this.mImageLocation.getInt("height");
        int[] location = new int[2];
        this.mFrame.getLocationInWindow(location);
        AnimationSet as = new AnimationSet(false);
        int pagerWidth = this.mFrame.getWidth();
        int pagerHeight = this.mFrame.getHeight();
        ScaleAnimation sa = new ScaleAnimation(((float) width) / ((float) pagerWidth), 1.0f, ((float) height) / ((float) pagerHeight), 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(200);
        as.addAnimation(sa);
        TranslateAnimation ta = new TranslateAnimation(0, (float) (((width / 2) + left) - (location[0] + (pagerWidth / 2))), 1, 0.0f, 0, (float) (((height / 2) + top) - (location[1] + (pagerHeight / 2))), 1, 0.0f);
        ta.setDuration(200);
        ta.setInterpolator(new LinearInterpolator());
        as.addAnimation(ta);
        this.mFrame.startAnimation(as);
    }

    private void animateImageOut() {
        if (this.mImageLocation == null) {
            dismiss();
            return;
        }
        int left = this.mImageLocation.getInt("left");
        int top = this.mImageLocation.getInt("top");
        int width = this.mImageLocation.getInt("width");
        int height = this.mImageLocation.getInt("height");
        int[] location = new int[2];
        this.mFrame.getLocationInWindow(location);
        Animation animationSet = new AnimationSet(false);
        int pagerWidth = this.mFrame.getWidth();
        int pagerHeight = this.mFrame.getHeight();
        ScaleAnimation sa = new ScaleAnimation(1.0f, ((float) width) / ((float) pagerWidth), 1.0f, ((float) height) / ((float) pagerHeight), 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(300);
        animationSet.addAnimation(sa);
        TranslateAnimation ta = new TranslateAnimation(1, 0.0f, 0, (float) (((width / 2) + left) - (location[0] + (pagerWidth / 2))), 1, 0.0f, 0, (float) (((height / 2) + top) - (location[1] + (pagerHeight / 2))));
        ta.setDuration(200);
        ta.setInterpolator(new LinearInterpolator());
        animationSet.addAnimation(ta);
        animationSet.setAnimationListener(new C29923());
        this.mFrame.startAnimation(animationSet);
    }

    public void onBackPressed() {
        animateImageOut();
    }
}
