package com.waze.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.view.anim.MaterialDesignImageAnimationHelper;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.map.ProgressAnimation;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class ImageRepository {
    private static final boolean DEBUG_LOGS = true;
    private static final int DEF_RETRIES = 3;
    private static final int DEF_TIMEOUT = 0;
    private static final int ENTER_ANIM_DURATION = 1500;
    public static final int FLAG_ROUND = 2;
    public static final int FLAG_THUMBNAIL = 1;
    public static final int IMAGE_SIZE_DP = 160;
    private static final int LOAD_TIME_TO_ANIMATE = 300;
    private static final int MAX_EXEC_THREADS = 5;
    private static final int MAX_IMAGES = 25;
    private static final int MAX_NUM_OF_PIXELS = 1048576;
    private static final int MAX_THUMBNAILS = 100;
    private static final int MICRO_MAX_NUM_OF_PIXELS = 16384;
    private static final int MICRO_MIN_SIDE_LENGTH = 100;
    private static final int MIN_SIDE_LENGTH = 1024;
    private static final int REMOVE_ANIM_DURATION = 200;
    public static final int THUMBNAIL_SIZE_DP = 40;
    private static final int THUMB_MAX_NUM_OF_PIXELS = 262144;
    private static final int THUMB_MIN_SIDE_LENGTH = 512;
    public static ImageRepository instance = new ImageRepository();
    ScheduledExecutorService mExec;
    int mTimesExecInit = 0;

    public boolean isCached(String $r1) throws  {
        return VolleyManager.getInstance().hasCache($r1, PixelMeasure.dp(160), PixelMeasure.dp(160));
    }

    public void unCache(String $r1) throws  {
        VolleyManager $r2 = VolleyManager.getInstance();
        $r2.removeCache($r1, PixelMeasure.dp(40), PixelMeasure.dp(40));
        $r2.removeCache($r1, PixelMeasure.dp(160), PixelMeasure.dp(160));
    }

    public void forceCache(String $r1, Bitmap $r2, boolean $z0) throws  {
        VolleyManager $r3 = VolleyManager.getInstance();
        if ($z0) {
            $r3.forceCache($r1, $r2, PixelMeasure.dp(40), PixelMeasure.dp(40));
        } else {
            $r3.forceCache($r1, $r2, PixelMeasure.dp(160), PixelMeasure.dp(160));
        }
    }

    public boolean isThumbnailCached(String $r1) throws  {
        return VolleyManager.getInstance().hasCache($r1, PixelMeasure.dp(40), PixelMeasure.dp(40));
    }

    public synchronized void initExecutor() throws  {
        if (this.mExec == null) {
            this.mExec = Executors.newScheduledThreadPool(5);
        }
        this.mTimesExecInit++;
    }

    public synchronized void endExecutor() throws  {
        this.mTimesExecInit--;
        if (this.mTimesExecInit == 0) {
            ScheduledExecutorService $r1 = this.mExec;
            this.mExec = null;
            $r1.shutdownNow();
            System.gc();
        }
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated ImageRepositoryListener $r2) throws  {
        getImage($r1, false, $r2);
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated boolean $z0, @Deprecated ImageRepositoryListener $r2) throws  {
        getImage($r1, $z0, $r2, 0, 3, null, AppService.getActiveActivity());
    }

    private void log(String $r1) throws  {
        Logger.m36d($r1);
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated ImageView $r2) throws  {
        if ($r2 != null) {
            $r2.setTag($r1);
        }
        getImage($r1, new 1(this, $r2, $r1));
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated ImageView $r2, @Deprecated ActivityBase $r3) throws  {
        getImage($r1, 0, $r2, null, $r3, 0, 3, null);
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated int $i0, @Deprecated ImageView $r2, @Deprecated View $r3, @Deprecated ActivityBase $r4) throws  {
        getImage($r1, $i0, $r2, $r3, $r4, 0, 3, null);
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated int $i0, @Deprecated ImageView $r2, @Deprecated View $r3, @Deprecated ActivityBase $r4, @Deprecated String $r5) throws  {
        getImage($r1, $i0, $r2, $r3, $r4, 0, 3, $r5);
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated int $i0, @Deprecated ImageView $r2, @Deprecated View $r3, @Deprecated ActivityBase $r4, @Deprecated long $l1, @Deprecated int $i2) throws  {
        getImage($r1, $i0, $r2, $r3, $r4, $l1, $i2, null);
    }

    public void getImage(String $r1, int $i0, ImageView $r2, View $r3, Context $r4, long $l1, int $i2, String $r5) throws  {
        if ($r2 != null) {
            $r2.setTag($r1);
        }
        getImage($r1, ($i0 & 1) != 0, new 2(this, $r2, $r3, $i0, ($i0 & 2) != 0, $r1), $l1, $i2, $r5, $r4);
    }

    @Deprecated
    public void getImage(@Deprecated String $r1, @Deprecated boolean $z0, @Deprecated ImageRepositoryListener $r2, @Deprecated long retryTimeoutMs, @Deprecated int maxRetries, @Deprecated String $r3, @Deprecated Context context) throws  {
        if ($r1 == null || $r1.length() == 0) {
            $r2.onImageRetrieved(null);
            return;
        }
        int $i2;
        if ($z0) {
            maxRetries = PixelMeasure.dp(40);
            $i2 = PixelMeasure.dp(40);
        } else {
            maxRetries = PixelMeasure.dp(160);
            $i2 = PixelMeasure.dp(160);
        }
        VolleyManager.getInstance().loadImageFromUrl($r1, new 3(this, $r2), null, maxRetries, $i2, $r3);
    }

    public static void imageViewAnimatedChange(ImageView $r0, View $r1, Bitmap $r2, int $i0) throws  {
        Drawable $r6;
        if ($r1 != null) {
            if ($r1 instanceof ProgressAnimation) {
                ((ProgressAnimation) $r1).stop();
            }
            ViewPropertyAnimatorHelper.initAnimation($r1, 200).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener($r1));
        }
        if (($i0 & 2) != 0) {
            $r6 = r8;
            Drawable r8 = new CircleShaderDrawable($r2, 0);
        } else {
            $r6 = r9;
            Drawable r9 = new BitmapDrawable($r0.getResources(), $r2);
        }
        $r0.setImageDrawable($r6);
        MaterialDesignImageAnimationHelper.animateImageEntrance($r6, 1500);
    }
}
