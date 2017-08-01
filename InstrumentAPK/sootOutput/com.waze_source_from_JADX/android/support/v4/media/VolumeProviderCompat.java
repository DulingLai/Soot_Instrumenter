package android.support.v4.media;

import android.os.Build.VERSION;
import android.support.v4.media.VolumeProviderCompatApi21.Delegate;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public abstract class VolumeProviderCompat {
    public static final int VOLUME_CONTROL_ABSOLUTE = 2;
    public static final int VOLUME_CONTROL_FIXED = 0;
    public static final int VOLUME_CONTROL_RELATIVE = 1;
    private Callback mCallback;
    private final int mControlType;
    private int mCurrentVolume;
    private final int mMaxVolume;
    private Object mVolumeProviderObj;

    class C00831 implements Delegate {
        C00831() throws  {
        }

        public void onSetVolumeTo(int $i0) throws  {
            VolumeProviderCompat.this.onSetVolumeTo($i0);
        }

        public void onAdjustVolume(int $i0) throws  {
            VolumeProviderCompat.this.onAdjustVolume($i0);
        }
    }

    public static abstract class Callback {
        public abstract void onVolumeChanged(VolumeProviderCompat volumeProviderCompat) throws ;
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ControlType {
    }

    public VolumeProviderCompat(int $i0, int $i1, int $i2) throws  {
        this.mControlType = $i0;
        this.mMaxVolume = $i1;
        this.mCurrentVolume = $i2;
    }

    public final int getCurrentVolume() throws  {
        return this.mCurrentVolume;
    }

    public final int getVolumeControl() throws  {
        return this.mControlType;
    }

    public final int getMaxVolume() throws  {
        return this.mMaxVolume;
    }

    public final void setCurrentVolume(int $i0) throws  {
        this.mCurrentVolume = $i0;
        Object $r1 = getVolumeProvider();
        if ($r1 != null) {
            VolumeProviderCompatApi21.setCurrentVolume($r1, $i0);
        }
        if (this.mCallback != null) {
            this.mCallback.onVolumeChanged(this);
        }
    }

    public void onSetVolumeTo(int volume) throws  {
    }

    public void onAdjustVolume(int direction) throws  {
    }

    public void setCallback(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    public Object getVolumeProvider() throws  {
        if (this.mVolumeProviderObj != null || VERSION.SDK_INT < 21) {
            return this.mVolumeProviderObj;
        }
        this.mVolumeProviderObj = VolumeProviderCompatApi21.createVolumeProvider(this.mControlType, this.mMaxVolume, this.mCurrentVolume, new C00831());
        return this.mVolumeProviderObj;
    }
}
