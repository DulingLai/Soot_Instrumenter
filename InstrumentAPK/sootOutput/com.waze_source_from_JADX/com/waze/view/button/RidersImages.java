package com.waze.view.button;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.waze.C1283R;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;

public class RidersImages extends FrameLayout {
    private static final int DEF_SIZE_DP = 40;
    private int mMargin = 0;
    private int mPlaceholderResId = C1283R.drawable.ridecard_profilepic_placeholder;
    private int mRiderSize = 0;
    private int mStrokeDp = 3;

    public RidersImages(Context context) {
        super(context);
        init(context);
    }

    public RidersImages(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public RidersImages(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(21)
    public RidersImages(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    void init(Context context) {
        setLayerType(1, null);
        if (isInEditMode()) {
            PixelMeasure.setResourceIfUnset(context.getResources());
            addImage("");
            addImage("");
        }
    }

    public void clearImages() {
        removeAllViews();
    }

    public void addImage(String imageUrl) {
        Bitmap placeholder = BitmapFactory.decodeResource(getContext().getResources(), this.mPlaceholderResId);
        final ImageView image = new ImageView(getContext());
        image.setScaleType(ScaleType.CENTER);
        image.setImageDrawable(new CircleFrameDrawable(placeholder, this.mMargin, this.mStrokeDp, this.mStrokeDp));
        addView(image);
        if (this.mRiderSize > 0) {
            recalculateLayout();
            postInvalidate();
            requestLayout();
        }
        if (imageUrl != null && !imageUrl.isEmpty() && !isInEditMode()) {
            int size = this.mRiderSize;
            if (size == 0) {
                size = getHeight();
            }
            if (size == 0) {
                size = PixelMeasure.dp(40);
            }
            VolleyManager.getInstance().loadImageFromUrl(imageUrl, new VolleyManager$ImageRequestListener() {
                public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
                    image.setImageDrawable(new CircleFrameDrawable(bitmap, RidersImages.this.mMargin, RidersImages.this.mStrokeDp, RidersImages.this.mStrokeDp));
                }

                public void onImageLoadFailed(Object token, long duration) {
                }
            }, null, size, size, null);
        }
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width;
        this.mRiderSize = MeasureSpec.getSize(heightMeasureSpec);
        int count = getChildCount();
        if (count == 0) {
            width = 0;
        } else if (count == 1) {
            width = this.mRiderSize;
        } else {
            width = this.mRiderSize + getLeftMargin(count, 0);
        }
        recalculateLayout();
        for (int i = 0; i < count; i++) {
            getChildAt(i).measure(MeasureSpec.makeMeasureSpec(this.mRiderSize, 1073741824), MeasureSpec.makeMeasureSpec(this.mRiderSize, 1073741824));
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec(width, 1073741824), heightMeasureSpec);
    }

    private void recalculateLayout() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            LayoutParams p = (LayoutParams) child.getLayoutParams();
            p.width = this.mRiderSize;
            p.height = this.mRiderSize;
            p.leftMargin = getLeftMargin(count, i);
            child.setLayoutParams(p);
        }
    }

    private int getLeftMargin(int count, int i) {
        if (i == count - 1) {
            return 0;
        }
        int m;
        switch (count) {
            case 0:
            case 1:
                return 0;
            case 2:
                m = (this.mRiderSize * 3) / 8;
                break;
            case 3:
                m = (this.mRiderSize * 4) / 8;
                break;
            default:
                m = (this.mRiderSize * 5) / 8;
                break;
        }
        return ((count - i) - 1) * (this.mRiderSize - m);
    }

    public void setPlaceholderResId(int resId) {
        this.mPlaceholderResId = resId;
    }

    public void setStrokeDp(int strokeDp) {
        this.mStrokeDp = strokeDp;
    }

    public void setMargin(int margin) {
        this.mMargin = margin;
    }
}
