package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat21.SharedElementCallback21;
import android.support.v4.content.ContextCompat;
import android.view.View;
import dalvik.annotation.Signature;
import java.util.List;
import java.util.Map;

public class ActivityCompat extends ContextCompat {

    public interface OnRequestPermissionsResultCallback {
        void onRequestPermissionsResult(int i, @NonNull String[] strArr, @NonNull int[] iArr) throws ;
    }

    private static class SharedElementCallback21Impl extends SharedElementCallback21 {
        private SharedElementCallback mCallback;

        public SharedElementCallback21Impl(SharedElementCallback $r1) throws  {
            this.mCallback = $r1;
        }

        public void onSharedElementStart(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> $r2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> $r3) throws  {
            this.mCallback.onSharedElementStart($r1, $r2, $r3);
        }

        public void onSharedElementEnd(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> $r2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> $r3) throws  {
            this.mCallback.onSharedElementEnd($r1, $r2, $r3);
        }

        public void onRejectSharedElements(@Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> $r1) throws  {
            this.mCallback.onRejectSharedElements($r1);
        }

        public void onMapSharedElements(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Map<String, View> $r2) throws  {
            this.mCallback.onMapSharedElements($r1, $r2);
        }

        public Parcelable onCaptureSharedElementSnapshot(View $r1, Matrix $r2, RectF $r3) throws  {
            return this.mCallback.onCaptureSharedElementSnapshot($r1, $r2, $r3);
        }

        public View onCreateSnapshotView(Context $r1, Parcelable $r2) throws  {
            return this.mCallback.onCreateSnapshotView($r1, $r2);
        }
    }

    public static boolean invalidateOptionsMenu(Activity $r0) throws  {
        if (VERSION.SDK_INT < 11) {
            return false;
        }
        ActivityCompatHoneycomb.invalidateOptionsMenu($r0);
        return true;
    }

    public static void startActivity(Activity $r0, Intent $r1, @Nullable Bundle $r2) throws  {
        if (VERSION.SDK_INT >= 16) {
            ActivityCompatJB.startActivity($r0, $r1, $r2);
        } else {
            $r0.startActivity($r1);
        }
    }

    public static void startActivityForResult(Activity $r0, Intent $r1, int $i0, @Nullable Bundle $r2) throws  {
        if (VERSION.SDK_INT >= 16) {
            ActivityCompatJB.startActivityForResult($r0, $r1, $i0, $r2);
        } else {
            $r0.startActivityForResult($r1, $i0);
        }
    }

    public static void finishAffinity(Activity $r0) throws  {
        if (VERSION.SDK_INT >= 16) {
            ActivityCompatJB.finishAffinity($r0);
        } else {
            $r0.finish();
        }
    }

    public static void finishAfterTransition(Activity $r0) throws  {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.finishAfterTransition($r0);
        } else {
            $r0.finish();
        }
    }

    public Uri getReferrer(Activity $r1) throws  {
        if (VERSION.SDK_INT >= 22) {
            return ActivityCompat22.getReferrer($r1);
        }
        Intent $r3 = $r1.getIntent();
        Uri $r2 = (Uri) $r3.getParcelableExtra("android.intent.extra.REFERRER");
        if ($r2 != null) {
            return $r2;
        }
        String $r5 = $r3.getStringExtra("android.intent.extra.REFERRER_NAME");
        return $r5 != null ? Uri.parse($r5) : null;
    }

    public static void setEnterSharedElementCallback(Activity $r0, SharedElementCallback $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.setEnterSharedElementCallback($r0, createCallback($r1));
        }
    }

    public static void setExitSharedElementCallback(Activity $r0, SharedElementCallback $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.setExitSharedElementCallback($r0, createCallback($r1));
        }
    }

    public static void postponeEnterTransition(Activity $r0) throws  {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.postponeEnterTransition($r0);
        }
    }

    public static void startPostponedEnterTransition(Activity $r0) throws  {
        if (VERSION.SDK_INT >= 21) {
            ActivityCompat21.startPostponedEnterTransition($r0);
        }
    }

    public static void requestPermissions(@NonNull final Activity $r0, @NonNull final String[] $r1, final int $i0) throws  {
        if (VERSION.SDK_INT >= 23) {
            ActivityCompatApi23.requestPermissions($r0, $r1, $i0);
        } else if ($r0 instanceof OnRequestPermissionsResultCallback) {
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                public void run() throws  {
                    int[] $r1 = new int[$r1.length];
                    PackageManager $r4 = $r0.getPackageManager();
                    String $r5 = $r0.getPackageName();
                    int $i0 = $r1.length;
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        $r1[$i1] = $r4.checkPermission($r1[$i1], $r5);
                    }
                    ((OnRequestPermissionsResultCallback) $r0).onRequestPermissionsResult($i0, $r1, $r1);
                }
            });
        }
    }

    public static boolean shouldShowRequestPermissionRationale(@NonNull Activity $r0, @NonNull String $r1) throws  {
        if (VERSION.SDK_INT >= 23) {
            return ActivityCompatApi23.shouldShowRequestPermissionRationale($r0, $r1);
        }
        return false;
    }

    private static SharedElementCallback21 createCallback(SharedElementCallback $r0) throws  {
        if ($r0 != null) {
            return new SharedElementCallback21Impl($r0);
        }
        return null;
    }
}
