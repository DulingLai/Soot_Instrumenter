package com.android.volley.toolbox;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.ImageView;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader.ImageContainer;
import com.android.volley.toolbox.ImageLoader.ImageListener;

public class NetworkImageView extends ImageView {
    private int mDefaultImageId;
    private int mErrorImageId;
    private ImageContainer mImageContainer;
    private ImageLoader mImageLoader;
    private String mUrl;

    public NetworkImageView(Context $r1) throws  {
        this($r1, null);
    }

    public NetworkImageView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public NetworkImageView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    public void setImageUrl(String $r1, ImageLoader $r2) throws  {
        this.mUrl = $r1;
        this.mImageLoader = $r2;
        loadImageIfNecessary(false);
    }

    public void setDefaultImageResId(int $i0) throws  {
        this.mDefaultImageId = $i0;
    }

    public void setErrorImageResId(int $i0) throws  {
        this.mErrorImageId = $i0;
    }

    private void loadImageIfNecessary(final boolean $z0) throws  {
        int $i0 = getWidth();
        int $i1 = getHeight();
        boolean $z1 = getLayoutParams() != null && getLayoutParams().height == -2 && getLayoutParams().width == -2;
        if ($i0 != 0 || $i1 != 0 || $z1) {
            if (TextUtils.isEmpty(this.mUrl)) {
                if (this.mImageContainer != null) {
                    this.mImageContainer.cancelRequest();
                    this.mImageContainer = null;
                }
                setDefaultImageOrNull();
                return;
            }
            if (!(this.mImageContainer == null || this.mImageContainer.getRequestUrl() == null)) {
                if (!this.mImageContainer.getRequestUrl().equals(this.mUrl)) {
                    this.mImageContainer.cancelRequest();
                    setDefaultImageOrNull();
                } else {
                    return;
                }
            }
            this.mImageContainer = this.mImageLoader.get(this.mUrl, new ImageListener() {
                public void onErrorResponse(VolleyError error) throws  {
                    if (NetworkImageView.this.mErrorImageId != 0) {
                        NetworkImageView.this.setImageResource(NetworkImageView.this.mErrorImageId);
                    }
                }

                public void onResponse(final ImageContainer $r1, boolean $z0) throws  {
                    if ($z0 && $z0) {
                        NetworkImageView.this.post(new Runnable() {
                            public void run() throws  {
                                C04711.this.onResponse($r1, false);
                            }
                        });
                    } else if ($r1.getBitmap() != null) {
                        NetworkImageView.this.setImageBitmap($r1.getBitmap());
                    } else if (NetworkImageView.this.mDefaultImageId != 0) {
                        NetworkImageView.this.setImageResource(NetworkImageView.this.mDefaultImageId);
                    }
                }
            });
        }
    }

    private void setDefaultImageOrNull() throws  {
        if (this.mDefaultImageId != 0) {
            setImageResource(this.mDefaultImageId);
        } else {
            setImageBitmap(null);
        }
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        loadImageIfNecessary(true);
    }

    protected void onDetachedFromWindow() throws  {
        if (this.mImageContainer != null) {
            this.mImageContainer.cancelRequest();
            setImageBitmap(null);
            this.mImageContainer = null;
        }
        super.onDetachedFromWindow();
    }

    protected void drawableStateChanged() throws  {
        super.drawableStateChanged();
        invalidate();
    }
}
