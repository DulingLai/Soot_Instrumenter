package com.facebook.login.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.facebook.C0496R;
import com.facebook.FacebookException;
import com.facebook.LoggingBehavior;
import com.facebook.internal.ImageDownloader;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.ImageRequest.Builder;
import com.facebook.internal.ImageRequest.Callback;
import com.facebook.internal.ImageResponse;
import com.facebook.internal.Logger;
import com.facebook.internal.Utility;

public class ProfilePictureView extends FrameLayout {
    private static final String BITMAP_HEIGHT_KEY = "ProfilePictureView_height";
    private static final String BITMAP_KEY = "ProfilePictureView_bitmap";
    private static final String BITMAP_WIDTH_KEY = "ProfilePictureView_width";
    public static final int CUSTOM = -1;
    private static final boolean IS_CROPPED_DEFAULT_VALUE = true;
    private static final String IS_CROPPED_KEY = "ProfilePictureView_isCropped";
    public static final int LARGE = -4;
    private static final int MIN_SIZE = 1;
    public static final int NORMAL = -3;
    private static final String PENDING_REFRESH_KEY = "ProfilePictureView_refresh";
    private static final String PRESET_SIZE_KEY = "ProfilePictureView_presetSize";
    private static final String PROFILE_ID_KEY = "ProfilePictureView_profileId";
    public static final int SMALL = -2;
    private static final String SUPER_STATE_KEY = "ProfilePictureView_superState";
    public static final String TAG = ProfilePictureView.class.getSimpleName();
    private Bitmap customizedDefaultProfilePicture = null;
    private ImageView image;
    private Bitmap imageContents;
    private boolean isCropped = true;
    private ImageRequest lastRequest;
    private OnErrorListener onErrorListener;
    private int presetSizeType = -1;
    private String profileId;
    private int queryHeight = 0;
    private int queryWidth = 0;

    class C05641 implements Callback {
        C05641() throws  {
        }

        public void onCompleted(ImageResponse $r1) throws  {
            ProfilePictureView.this.processResponse($r1);
        }
    }

    public interface OnErrorListener {
        void onError(FacebookException facebookException) throws ;
    }

    public ProfilePictureView(Context $r1) throws  {
        super($r1);
        initialize($r1);
    }

    public ProfilePictureView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        initialize($r1);
        parseAttributes($r2);
    }

    public ProfilePictureView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        initialize($r1);
        parseAttributes($r2);
    }

    public final int getPresetSize() throws  {
        return this.presetSizeType;
    }

    public final void setPresetSize(int $i0) throws  {
        switch ($i0) {
            case LARGE /*-4*/:
            case -3:
            case -2:
            case -1:
                this.presetSizeType = $i0;
                requestLayout();
                return;
            default:
                throw new IllegalArgumentException("Must use a predefined preset size");
        }
    }

    public final boolean isCropped() throws  {
        return this.isCropped;
    }

    public final void setCropped(boolean $z0) throws  {
        this.isCropped = $z0;
        refreshImage(false);
    }

    public final String getProfileId() throws  {
        return this.profileId;
    }

    public final void setProfileId(String $r1) throws  {
        boolean $z0 = false;
        if (Utility.isNullOrEmpty(this.profileId) || !this.profileId.equalsIgnoreCase($r1)) {
            setBlankProfilePicture();
            $z0 = true;
        }
        this.profileId = $r1;
        refreshImage($z0);
    }

    public final OnErrorListener getOnErrorListener() throws  {
        return this.onErrorListener;
    }

    public final void setOnErrorListener(OnErrorListener $r1) throws  {
        this.onErrorListener = $r1;
    }

    public final void setDefaultProfilePicture(Bitmap $r1) throws  {
        this.customizedDefaultProfilePicture = $r1;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        LayoutParams $r1 = getLayoutParams();
        boolean $z0 = false;
        int $i2 = MeasureSpec.getSize($i1);
        int $i3 = MeasureSpec.getSize($i0);
        if (MeasureSpec.getMode($i1) != 1073741824 && $r1.height == -2) {
            $i1 = getPresetSizeInPixels(true);
            $i2 = $i1;
            $i1 = MeasureSpec.makeMeasureSpec($i1, 1073741824);
            $z0 = true;
        }
        if (MeasureSpec.getMode($i0) != 1073741824 && $r1.width == -2) {
            $i0 = getPresetSizeInPixels(true);
            $i3 = $i0;
            $i0 = MeasureSpec.makeMeasureSpec($i0, 1073741824);
            $z0 = true;
        }
        if ($z0) {
            setMeasuredDimension($i3, $i2);
            measureChildren($i0, $i1);
            return;
        }
        super.onMeasure($i0, $i1);
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        refreshImage(false);
    }

    protected Parcelable onSaveInstanceState() throws  {
        boolean $z0;
        Parcelable $r2 = super.onSaveInstanceState();
        Bundle $r1 = new Bundle();
        $r1.putParcelable(SUPER_STATE_KEY, $r2);
        $r1.putString(PROFILE_ID_KEY, this.profileId);
        $r1.putInt(PRESET_SIZE_KEY, this.presetSizeType);
        $r1.putBoolean(IS_CROPPED_KEY, this.isCropped);
        $r1.putParcelable(BITMAP_KEY, this.imageContents);
        $r1.putInt(BITMAP_WIDTH_KEY, this.queryWidth);
        $r1.putInt(BITMAP_HEIGHT_KEY, this.queryHeight);
        if (this.lastRequest != null) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        $r1.putBoolean(PENDING_REFRESH_KEY, $z0);
        return $r1;
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1.getClass() != Bundle.class) {
            super.onRestoreInstanceState($r1);
            return;
        }
        Bundle $r3 = (Bundle) $r1;
        super.onRestoreInstanceState($r3.getParcelable(SUPER_STATE_KEY));
        this.profileId = $r3.getString(PROFILE_ID_KEY);
        this.presetSizeType = $r3.getInt(PRESET_SIZE_KEY);
        this.isCropped = $r3.getBoolean(IS_CROPPED_KEY);
        this.queryWidth = $r3.getInt(BITMAP_WIDTH_KEY);
        this.queryHeight = $r3.getInt(BITMAP_HEIGHT_KEY);
        setImageBitmap((Bitmap) $r3.getParcelable(BITMAP_KEY));
        if ($r3.getBoolean(PENDING_REFRESH_KEY)) {
            refreshImage(true);
        }
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        this.lastRequest = null;
    }

    private void initialize(Context $r1) throws  {
        removeAllViews();
        this.image = new ImageView($r1);
        this.image.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.image.setScaleType(ScaleType.CENTER_INSIDE);
        addView(this.image);
    }

    private void parseAttributes(AttributeSet $r1) throws  {
        TypedArray $r4 = getContext().obtainStyledAttributes($r1, C0496R.styleable.com_facebook_profile_picture_view);
        setPresetSize($r4.getInt(C0496R.styleable.com_facebook_profile_picture_view_com_facebook_preset_size, -1));
        this.isCropped = $r4.getBoolean(C0496R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped, true);
        $r4.recycle();
    }

    private void refreshImage(boolean $z0) throws  {
        boolean $z1 = updateImageQueryParameters();
        if (this.profileId == null || this.profileId.length() == 0 || (this.queryWidth == 0 && this.queryHeight == 0)) {
            setBlankProfilePicture();
        } else if ($z1 || $z0) {
            sendImageRequest(true);
        }
    }

    private void setBlankProfilePicture() throws  {
        if (this.lastRequest != null) {
            ImageDownloader.cancelRequest(this.lastRequest);
        }
        if (this.customizedDefaultProfilePicture == null) {
            setImageBitmap(BitmapFactory.decodeResource(getResources(), isCropped() ? C0496R.drawable.com_facebook_profile_picture_blank_square : C0496R.drawable.com_facebook_profile_picture_blank_portrait));
            return;
        }
        updateImageQueryParameters();
        setImageBitmap(Bitmap.createScaledBitmap(this.customizedDefaultProfilePicture, this.queryWidth, this.queryHeight, false));
    }

    private void setImageBitmap(Bitmap $r1) throws  {
        if (this.image != null && $r1 != null) {
            this.imageContents = $r1;
            this.image.setImageBitmap($r1);
        }
    }

    private void sendImageRequest(boolean $z0) throws  {
        ImageRequest $r6 = new Builder(getContext(), ImageRequest.getProfilePictureUri(this.profileId, this.queryWidth, this.queryHeight)).setAllowCachedRedirects($z0).setCallerTag(this).setCallback(new C05641()).build();
        if (this.lastRequest != null) {
            ImageDownloader.cancelRequest(this.lastRequest);
        }
        this.lastRequest = $r6;
        ImageDownloader.downloadAsync($r6);
    }

    private void processResponse(ImageResponse $r1) throws  {
        if ($r1.getRequest() == this.lastRequest) {
            this.lastRequest = null;
            Bitmap $r5 = $r1.getBitmap();
            Throwable $r6 = $r1.getError();
            if ($r6 != null) {
                OnErrorListener $r2 = this.onErrorListener;
                if ($r2 != null) {
                    $r2.onError(new FacebookException("Error in downloading profile picture for profileId: " + getProfileId(), $r6));
                } else {
                    Logger.log(LoggingBehavior.REQUESTS, 6, TAG, $r6.toString());
                }
            } else if ($r5 != null) {
                setImageBitmap($r5);
                if ($r1.isCachedRedirect()) {
                    sendImageRequest(false);
                }
            }
        }
    }

    private boolean updateImageQueryParameters() throws  {
        boolean $z0 = true;
        int $i0 = getHeight();
        int $i1 = $i0;
        int $i2 = getWidth();
        int $i3 = $i2;
        if ($i2 < 1 || $i0 < 1) {
            return false;
        }
        $i0 = getPresetSizeInPixels(false);
        if ($i0 != 0) {
            $i3 = $i0;
            $i1 = $i0;
        }
        if ($i3 <= $i1) {
            $i1 = isCropped() ? $i3 : 0;
        } else {
            $i3 = isCropped() ? $i1 : 0;
        }
        if ($i3 == this.queryWidth && $i1 == this.queryHeight) {
            $z0 = false;
        }
        this.queryWidth = $i3;
        this.queryHeight = $i1;
        return $z0;
    }

    private int getPresetSizeInPixels(boolean $z0) throws  {
        int $i0;
        switch (this.presetSizeType) {
            case LARGE /*-4*/:
                $i0 = C0496R.dimen.com_facebook_profilepictureview_preset_size_large;
                break;
            case -3:
                $i0 = C0496R.dimen.com_facebook_profilepictureview_preset_size_normal;
                break;
            case -2:
                $i0 = C0496R.dimen.com_facebook_profilepictureview_preset_size_small;
                break;
            case -1:
                if ($z0) {
                    $i0 = C0496R.dimen.com_facebook_profilepictureview_preset_size_normal;
                    break;
                }
                return 0;
            default:
                return 0;
        }
        return getResources().getDimensionPixelSize($i0);
    }
}
