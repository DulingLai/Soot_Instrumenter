package android.support.v4.app;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.media.session.MediaController;
import android.os.Parcelable;
import android.view.View;
import dalvik.annotation.Signature;
import java.util.List;
import java.util.Map;

class ActivityCompat21 {

    public static abstract class SharedElementCallback21 {
        public abstract Parcelable onCaptureSharedElementSnapshot(View view, Matrix matrix, RectF rectF) throws ;

        public abstract View onCreateSnapshotView(Context context, Parcelable parcelable) throws ;

        public abstract void onMapSharedElements(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) List<String> list, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Map<String, View> map) throws ;

        public abstract void onRejectSharedElements(@Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list) throws ;

        public abstract void onSharedElementEnd(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<String> list, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list3) throws ;

        public abstract void onSharedElementStart(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<String> list, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Ljava/util/List", "<", "Landroid/view/View;", ">;)V"}) List<View> list3) throws ;
    }

    private static class SharedElementCallbackImpl extends SharedElementCallback {
        private SharedElementCallback21 mCallback;

        public SharedElementCallbackImpl(SharedElementCallback21 $r1) throws  {
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

    ActivityCompat21() throws  {
    }

    public static void setMediaController(Activity $r0, Object $r1) throws  {
        $r0.setMediaController((MediaController) $r1);
    }

    public static void finishAfterTransition(Activity $r0) throws  {
        $r0.finishAfterTransition();
    }

    public static void setEnterSharedElementCallback(Activity $r0, SharedElementCallback21 $r1) throws  {
        $r0.setEnterSharedElementCallback(createCallback($r1));
    }

    public static void setExitSharedElementCallback(Activity $r0, SharedElementCallback21 $r1) throws  {
        $r0.setExitSharedElementCallback(createCallback($r1));
    }

    public static void postponeEnterTransition(Activity $r0) throws  {
        $r0.postponeEnterTransition();
    }

    public static void startPostponedEnterTransition(Activity $r0) throws  {
        $r0.startPostponedEnterTransition();
    }

    private static SharedElementCallback createCallback(SharedElementCallback21 $r0) throws  {
        if ($r0 != null) {
            return new SharedElementCallbackImpl($r0);
        }
        return null;
    }
}
