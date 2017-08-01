package android.support.v4.media;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserCompat.MediaItem;
import dalvik.annotation.Signature;
import java.util.List;

public class MediaBrowserCompatUtils {
    public static boolean areSameOptions(Bundle $r0, Bundle $r1) throws  {
        if ($r0 == $r1) {
            return true;
        }
        if ($r0 == null) {
            if ($r1.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && $r1.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1) {
                return true;
            }
            return false;
        } else if ($r1 != null) {
            return $r0.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == $r1.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) && $r0.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == $r1.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        } else {
            if ($r0.getInt(MediaBrowserCompat.EXTRA_PAGE, -1) == -1 && $r0.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1) == -1) {
                return true;
            }
            return false;
        }
    }

    public static boolean hasDuplicatedItems(Bundle $r0, Bundle $r1) throws  {
        int $i0;
        int $i1;
        int $i2;
        int $i3;
        if ($r0 == null) {
            $i0 = -1;
        } else {
            $i0 = $r0.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        }
        if ($r1 == null) {
            $i1 = -1;
        } else {
            $i1 = $r1.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        }
        if ($r0 == null) {
            $i2 = -1;
        } else {
            $i2 = $r0.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        }
        if ($r1 == null) {
            $i3 = -1;
        } else {
            $i3 = $r1.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        }
        if ($i0 == -1 || $i2 == -1) {
            $i0 = 0;
            $i2 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        } else {
            $i0 = $i2 * ($i0 - 1);
            $i2 = ($i0 + $i2) - 1;
        }
        if ($i1 == -1 || $i3 == -1) {
            $i1 = 0;
            $i3 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        } else {
            $i1 = $i3 * ($i1 - 1);
            $i3 = ($i1 + $i3) - 1;
        }
        if ($i0 > $i1 || $i1 > $i2) {
            return $i0 <= $i3 && $i3 <= $i2;
        } else {
            return true;
        }
    }

    public static List<MediaItem> applyOptions(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;"}) List<MediaItem> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;", "Landroid/os/Bundle;", ")", "Ljava/util/List", "<", "Landroid/support/v4/media/MediaBrowserCompat$MediaItem;", ">;"}) Bundle $r0) throws  {
        int $i1 = $r0.getInt(MediaBrowserCompat.EXTRA_PAGE, -1);
        int $i2 = $r0.getInt(MediaBrowserCompat.EXTRA_PAGE_SIZE, -1);
        if ($i1 == -1 && $i2 == -1) {
            return $r1;
        }
        int $i0 = $i2 * ($i1 - 1);
        int $i3 = $i0 + $i2;
        if ($i1 < 1 || $i2 < 1 || $i0 >= $r1.size()) {
            return null;
        }
        if ($i3 > $r1.size()) {
            $i3 = $r1.size();
        }
        return $r1.subList($i0, $i3);
    }
}
