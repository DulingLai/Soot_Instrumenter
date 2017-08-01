package com.facebook.share.internal;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.facebook.C0496R;
import com.waze.LayoutManager;

public class LikeBoxCountView extends FrameLayout {
    private int additionalTextPadding;
    private Paint borderPaint;
    private float borderRadius;
    private float caretHeight;
    private LikeBoxCountViewCaretPosition caretPosition = LikeBoxCountViewCaretPosition.LEFT;
    private float caretWidth;
    private TextView likeCountLabel;
    private int textPadding;

    public enum LikeBoxCountViewCaretPosition {
        LEFT,
        TOP,
        RIGHT,
        BOTTOM
    }

    public LikeBoxCountView(Context $r1) throws  {
        super($r1);
        initialize($r1);
    }

    public void setText(String $r1) throws  {
        this.likeCountLabel.setText($r1);
    }

    public void setCaretPosition(LikeBoxCountViewCaretPosition $r1) throws  {
        this.caretPosition = $r1;
        switch ($r1) {
            case LEFT:
                setAdditionalTextPadding(this.additionalTextPadding, 0, 0, 0);
                return;
            case TOP:
                setAdditionalTextPadding(0, this.additionalTextPadding, 0, 0);
                return;
            case RIGHT:
                setAdditionalTextPadding(0, 0, this.additionalTextPadding, 0);
                return;
            case BOTTOM:
                setAdditionalTextPadding(0, 0, 0, this.additionalTextPadding);
                return;
            default:
                return;
        }
    }

    protected void onDraw(Canvas $r1) throws  {
        super.onDraw($r1);
        int $i0 = getPaddingTop();
        int $i1 = $i0;
        int $i2 = getPaddingLeft();
        int $i3 = $i2;
        int $i4 = getWidth() - getPaddingRight();
        int $i5 = getHeight() - getPaddingBottom();
        float $f0;
        float $f1;
        switch (this.caretPosition) {
            case LEFT:
                $f0 = (float) $i2;
                $f1 = this.caretHeight;
                $i3 = (int) ($f0 + $f1);
                break;
            case TOP:
                $f0 = (float) $i0;
                $f1 = this.caretHeight;
                $i1 = (int) ($f0 + $f1);
                break;
            case RIGHT:
                $f0 = (float) $i4;
                $f1 = this.caretHeight;
                $i4 = (int) ($f0 - $f1);
                break;
            case BOTTOM:
                $f0 = (float) $i5;
                $f1 = this.caretHeight;
                $i5 = (int) ($f0 - $f1);
                break;
            default:
                break;
        }
        drawBorder($r1, (float) $i3, (float) $i1, (float) $i4, (float) $i5);
    }

    private void initialize(Context $r1) throws  {
        setWillNotDraw(false);
        this.caretHeight = getResources().getDimension(C0496R.dimen.com_facebook_likeboxcountview_caret_height);
        this.caretWidth = getResources().getDimension(C0496R.dimen.com_facebook_likeboxcountview_caret_width);
        this.borderRadius = getResources().getDimension(C0496R.dimen.com_facebook_likeboxcountview_border_radius);
        this.borderPaint = new Paint();
        this.borderPaint.setColor(getResources().getColor(C0496R.color.com_facebook_likeboxcountview_border_color));
        this.borderPaint.setStrokeWidth(getResources().getDimension(C0496R.dimen.com_facebook_likeboxcountview_border_width));
        this.borderPaint.setStyle(Style.STROKE);
        initializeLikeCountLabel($r1);
        addView(this.likeCountLabel);
        setCaretPosition(this.caretPosition);
    }

    private void initializeLikeCountLabel(Context $r1) throws  {
        this.likeCountLabel = new TextView($r1);
        this.likeCountLabel.setLayoutParams(new LayoutParams(-1, -1));
        this.likeCountLabel.setGravity(17);
        this.likeCountLabel.setTextSize(0, getResources().getDimension(C0496R.dimen.com_facebook_likeboxcountview_text_size));
        this.likeCountLabel.setTextColor(getResources().getColor(C0496R.color.com_facebook_likeboxcountview_text_color));
        this.textPadding = getResources().getDimensionPixelSize(C0496R.dimen.com_facebook_likeboxcountview_text_padding);
        this.additionalTextPadding = getResources().getDimensionPixelSize(C0496R.dimen.com_facebook_likeboxcountview_caret_height);
    }

    private void setAdditionalTextPadding(int $i0, int $i1, int $i2, int $i3) throws  {
        this.likeCountLabel.setPadding(this.textPadding + $i0, this.textPadding + $i1, this.textPadding + $i2, this.textPadding + $i3);
    }

    private void drawBorder(Canvas $r1, float $f0, float $f1, float $f2, float $f3) throws  {
        Path $r2 = new Path();
        float $f4 = LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * this.borderRadius;
        $r2.addArc(new RectF($f0, $f1, $f0 + $f4, $f1 + $f4), -180.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.TOP) {
            $r2.lineTo(((($f2 - $f0) - this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f0, $f1);
            $r2.lineTo((($f2 - $f0) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f0, $f1 - this.caretHeight);
            $r2.lineTo(((($f2 - $f0) + this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f0, $f1);
        }
        $r2.lineTo($f2 - this.borderRadius, $f1);
        $r2.addArc(new RectF($f2 - $f4, $f1, $f2, $f1 + $f4), -90.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.RIGHT) {
            $r2.lineTo($f2, ((($f3 - $f1) - this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f1);
            $r2.lineTo(this.caretHeight + $f2, (($f3 - $f1) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f1);
            $r2.lineTo($f2, ((($f3 - $f1) + this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f1);
        }
        $r2.lineTo($f2, $f3 - this.borderRadius);
        $r2.addArc(new RectF($f2 - $f4, $f3 - $f4, $f2, $f3), 0.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.BOTTOM) {
            $r2.lineTo(((($f2 - $f0) + this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f0, $f3);
            $r2.lineTo((($f2 - $f0) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f0, this.caretHeight + $f3);
            $r2.lineTo(((($f2 - $f0) - this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f0, $f3);
        }
        $r2.lineTo(this.borderRadius + $f0, $f3);
        $r2.addArc(new RectF($f0, $f3 - $f4, $f0 + $f4, $f3), 90.0f, 90.0f);
        if (this.caretPosition == LikeBoxCountViewCaretPosition.LEFT) {
            $r2.lineTo($f0, ((($f3 - $f1) + this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f1);
            $r2.lineTo($f0 - this.caretHeight, (($f3 - $f1) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f1);
            $r2.lineTo($f0, ((($f3 - $f1) - this.caretWidth) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) + $f1);
        }
        $r2.lineTo($f0, this.borderRadius + $f1);
        $r1.drawPath($r2, this.borderPaint);
    }
}
