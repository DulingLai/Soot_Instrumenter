package com.waze.reports;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.AutoResizeTextView;

public class ThankYouDialog extends Dialog {
    private static final int ANIM_CIRCLE_DELAY = 200;
    private static final int ANIM_CIRCLE_DUR = 150;
    private static final int ANIM_DOGE_DELAY = 800;
    private static final int ANIM_DOGE_DUR = 250;
    private static final int ANIM_ONES_DELAY = 500;
    private static final int ANIM_ONES_DUR = 150;
    private static final int ANIM_POINTS_DELAY = 650;
    private static final int ANIM_POINTS_DUR = 150;
    private static final int ANIM_TENS_DELAY = 350;
    private static final int ANIM_TENS_DUR = 150;
    int mEarnedPoints;

    public ThankYouDialog(Context context, int earnedPoints, boolean bShowMore, OnClickListener onLater, OnClickListener onMore, int thanks1DS, int thanks2DS, int moreDS, int laterDS, boolean pending) {
        super(context, C1283R.style.PopInDialog);
        this.mEarnedPoints = earnedPoints;
        setContentView(C1283R.layout.place_thank_you);
        Window window = getWindow();
        window.setLayout(-2, -2);
        window.setGravity(17);
        NativeManager nm = NativeManager.getInstance();
        if (earnedPoints > 0) {
            ((TextView) findViewById(C1283R.id.placeThankYouYouEarned)).setText(nm.getLanguageString(DisplayStrings.DS_YOU_EARNED));
            ((TextView) findViewById(C1283R.id.placeThankYouOnes)).setText("" + (this.mEarnedPoints % 10));
            ((TextView) findViewById(C1283R.id.placeThankYouTens)).setText("" + (this.mEarnedPoints / 10));
            animatePoints();
        } else {
            findViewById(C1283R.id.placeThankYouYouEarned).setVisibility(8);
            findViewById(C1283R.id.placeThankYouCircle).setVisibility(8);
        }
        View doge = findViewById(C1283R.id.placeThankYouDoge);
        if (bShowMore) {
            AnimationSet dogeAnimation = new AnimationSet(true);
            dogeAnimation.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f));
            dogeAnimation.addAnimation(new RotateAnimation(-45.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
            dogeAnimation.setDuration(250);
            dogeAnimation.setFillBefore(true);
            dogeAnimation.setStartOffset(800);
            doge.startAnimation(dogeAnimation);
        } else {
            doge.setVisibility(8);
        }
        View addMoreButton = findViewById(C1283R.id.placeThankYouAddMore);
        if (bShowMore) {
            addMoreButton.setOnClickListener(onMore);
        } else {
            addMoreButton.setVisibility(8);
        }
        findViewById(C1283R.id.placeThankYouMaybeLater).setOnClickListener(onLater);
        if (bShowMore) {
            ((TextView) findViewById(C1283R.id.placeThankYouAddMoreText)).setText(nm.getLanguageString(moreDS));
            ((TextView) findViewById(C1283R.id.placeThankYouMaybeLater)).setText(nm.getLanguageString(laterDS));
            ((TextView) findViewById(C1283R.id.placeThankYouAddMorePoints)).setText("+30");
        } else {
            ((TextView) findViewById(C1283R.id.placeThankYouMaybeLater)).setText(nm.getLanguageString(laterDS));
        }
        String thanks = "";
        if (DisplayStrings.isValid(thanks1DS)) {
            thanks = thanks + nm.getLanguageString(thanks1DS);
            if (DisplayStrings.isValid(thanks2DS)) {
                thanks = thanks + '\n';
            }
        }
        if (DisplayStrings.isValid(thanks2DS)) {
            thanks = thanks + nm.getLanguageString(thanks2DS);
        }
        ((TextView) findViewById(C1283R.id.placeThankYouText1)).setText(thanks);
        if (pending) {
            ((TextView) findViewById(C1283R.id.placeThankYouPending)).setText(nm.getLanguageString(DisplayStrings.DS_PENDING_COMMUNITY_REVIEW));
        } else {
            findViewById(C1283R.id.placeThankYouPending).setVisibility(8);
        }
        ((AutoResizeTextView) findViewById(C1283R.id.placeThankYouPoints)).setText(nm.getLanguageString(DisplayStrings.DS_POINTS).toUpperCase());
    }

    private void animatePoints() {
        View circle = findViewById(C1283R.id.placeThankYouCircle);
        ScaleAnimation circleAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        circleAnimation.setDuration(150);
        circleAnimation.setFillBefore(true);
        circleAnimation.setStartOffset(200);
        circle.startAnimation(circleAnimation);
        View tens = findViewById(C1283R.id.placeThankYouTens);
        ScaleAnimation tensAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        tensAnimation.setDuration(150);
        tensAnimation.setFillBefore(true);
        tensAnimation.setStartOffset(350);
        tens.startAnimation(tensAnimation);
        View ones = findViewById(C1283R.id.placeThankYouOnes);
        ScaleAnimation onesAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        onesAnimation.setDuration(150);
        onesAnimation.setFillBefore(true);
        onesAnimation.setStartOffset(500);
        ones.startAnimation(onesAnimation);
        View points = findViewById(C1283R.id.placeThankYouPoints);
        ScaleAnimation pointsAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        pointsAnimation.setDuration(150);
        pointsAnimation.setFillBefore(true);
        pointsAnimation.setStartOffset(650);
        points.startAnimation(pointsAnimation);
    }

    public void onBackPressed() {
        findViewById(C1283R.id.placeThankYouMaybeLater).performClick();
    }
}
