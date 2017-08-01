package com.facebook.share.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.DialogPresenter.ParameterProvider;
import com.facebook.internal.FacebookDialogBase;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class LikeDialog extends FacebookDialogBase<LikeContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.Like.toRequestCode();
    private static final String TAG = "LikeDialog";

    private class NativeHandler extends ModeHandler {
        private NativeHandler() throws  {
            super();
        }

        public boolean canShow(LikeContent $r1) throws  {
            return $r1 != null && LikeDialog.canShowNativeDialog();
        }

        public AppCall createAppCall(final LikeContent $r1) throws  {
            AppCall $r3 = LikeDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForNativeDialog($r3, new ParameterProvider() {
                public Bundle getParameters() throws  {
                    return LikeDialog.createParameters($r1);
                }

                public Bundle getLegacyParameters() throws  {
                    Log.e(LikeDialog.TAG, "Attempting to present the Like Dialog with an outdated Facebook app on the device");
                    return new Bundle();
                }
            }, LikeDialog.getFeature());
            return $r3;
        }
    }

    public static final class Result {
        private final Bundle bundle;

        public Result(Bundle $r1) throws  {
            this.bundle = $r1;
        }

        public Bundle getData() throws  {
            return this.bundle;
        }
    }

    private class WebFallbackHandler extends ModeHandler {
        private WebFallbackHandler() throws  {
            super();
        }

        public boolean canShow(LikeContent $r1) throws  {
            return $r1 != null && LikeDialog.canShowWebFallback();
        }

        public AppCall createAppCall(LikeContent $r1) throws  {
            AppCall $r3 = LikeDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebFallbackDialog($r3, LikeDialog.createParameters($r1), LikeDialog.getFeature());
            return $r3;
        }
    }

    public static boolean canShowNativeDialog() throws  {
        return VERSION.SDK_INT >= 14 && DialogPresenter.canPresentNativeDialogWithFeature(getFeature());
    }

    public static boolean canShowWebFallback() throws  {
        return VERSION.SDK_INT >= 14 && DialogPresenter.canPresentWebFallbackDialogWithFeature(getFeature());
    }

    public LikeDialog(Activity $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    public LikeDialog(Fragment $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    protected AppCall createBaseAppCall() throws  {
        return new AppCall(getRequestCode());
    }

    protected List<ModeHandler> getOrderedModeHandlers() throws  {
        ArrayList $r1 = new ArrayList();
        $r1.add(new NativeHandler());
        $r1.add(new WebFallbackHandler());
        return $r1;
    }

    protected void registerCallbackImpl(@Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/internal/LikeDialog$Result;", ">;)V"}) CallbackManagerImpl $r1, @Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/internal/LikeDialog$Result;", ">;)V"}) final FacebookCallback<Result> $r2) throws  {
        C05921 $r4;
        if ($r2 == null) {
            $r4 = null;
        } else {
            $r4 = new ResultProcessor($r2) {
                public void onSuccess(AppCall appCall, Bundle $r2) throws  {
                    $r2.onSuccess(new Result($r2));
                }
            };
        }
        $r1.registerCallback(getRequestCode(), new Callback() {
            public boolean onActivityResult(int $i0, Intent $r1) throws  {
                return ShareInternalUtility.handleActivityResult(LikeDialog.this.getRequestCode(), $i0, $r1, $r4);
            }
        });
    }

    private static DialogFeature getFeature() throws  {
        return LikeDialogFeature.LIKE_DIALOG;
    }

    private static Bundle createParameters(LikeContent $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putString("object_id", $r0.getObjectId());
        $r1.putString("object_type", $r0.getObjectType());
        return $r1;
    }
}
