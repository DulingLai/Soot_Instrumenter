package android.support.v4.media;

import android.media.VolumeProvider;

class VolumeProviderCompatApi21 {

    public interface Delegate {
        void onAdjustVolume(int i) throws ;

        void onSetVolumeTo(int i) throws ;
    }

    VolumeProviderCompatApi21() throws  {
    }

    public static Object createVolumeProvider(int $i0, int $i1, int $i2, final Delegate $r0) throws  {
        return new VolumeProvider($i0, $i1, $i2) {
            public void onSetVolumeTo(int $i0) throws  {
                $r0.onSetVolumeTo($i0);
            }

            public void onAdjustVolume(int $i0) throws  {
                $r0.onAdjustVolume($i0);
            }
        };
    }

    public static void setCurrentVolume(Object $r0, int $i0) throws  {
        ((VolumeProvider) $r0).setCurrentVolume($i0);
    }
}
