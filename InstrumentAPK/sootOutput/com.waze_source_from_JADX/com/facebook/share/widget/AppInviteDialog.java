package com.facebook.share.widget;

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
import com.facebook.share.internal.AppInviteDialogFeature;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.AppInviteContent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class AppInviteDialog extends FacebookDialogBase<AppInviteContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.AppInvite.toRequestCode();
    private static final String TAG = "AppInviteDialog";

    private class NativeHandler extends ModeHandler {
        private NativeHandler() throws  {
            super();
        }

        public boolean canShow(AppInviteContent content) throws  {
            return AppInviteDialog.canShowNativeDialog();
        }

        public AppCall createAppCall(final AppInviteContent $r1) throws  {
            AppCall $r3 = AppInviteDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForNativeDialog($r3, new ParameterProvider() {
                public Bundle getParameters() throws  {
                    return AppInviteDialog.createParameters($r1);
                }

                public Bundle getLegacyParameters() throws  {
                    Log.e(AppInviteDialog.TAG, "Attempting to present the AppInviteDialog with an outdated Facebook app on the device");
                    return new Bundle();
                }
            }, AppInviteDialog.getFeature());
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

        public boolean canShow(AppInviteContent content) throws  {
            return AppInviteDialog.canShowWebFallback();
        }

        public AppCall createAppCall(AppInviteContent $r1) throws  {
            AppCall $r3 = AppInviteDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebFallbackDialog($r3, AppInviteDialog.createParameters($r1), AppInviteDialog.getFeature());
            return $r3;
        }
    }

    public static boolean canShow() throws  {
        return canShowNativeDialog() || canShowWebFallback();
    }

    public static void show(Activity $r0, AppInviteContent $r1) throws  {
        new AppInviteDialog($r0).show($r1);
    }

    public static void show(Fragment $r0, AppInviteContent $r1) throws  {
        new AppInviteDialog($r0).show($r1);
    }

    private static boolean canShowNativeDialog() throws  {
        return VERSION.SDK_INT >= 14 && DialogPresenter.canPresentNativeDialogWithFeature(getFeature());
    }

    private static boolean canShowWebFallback() throws  {
        return VERSION.SDK_INT >= 14 && DialogPresenter.canPresentWebFallbackDialogWithFeature(getFeature());
    }

    public AppInviteDialog(Activity $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    public AppInviteDialog(Fragment $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    protected void registerCallbackImpl(@Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/AppInviteDialog$Result;", ">;)V"}) CallbackManagerImpl $r1, @Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/AppInviteDialog$Result;", ">;)V"}) final FacebookCallback<Result> $r2) throws  {
        C06211 $r4;
        if ($r2 == null) {
            $r4 = null;
        } else {
            $r4 = new ResultProcessor($r2) {
                public void onSuccess(AppCall appCall, Bundle $r2) throws  {
                    if ("cancel".equalsIgnoreCase(ShareInternalUtility.getNativeDialogCompletionGesture($r2))) {
                        $r2.onCancel();
                    } else {
                        $r2.onSuccess(new Result($r2));
                    }
                }
            };
        }
        $r1.registerCallback(getRequestCode(), new Callback() {
            public boolean onActivityResult(int $i0, Intent $r1) throws  {
                return ShareInternalUtility.handleActivityResult(AppInviteDialog.this.getRequestCode(), $i0, $r1, $r4);
            }
        });
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

    private static DialogFeature getFeature() throws  {
        return AppInviteDialogFeature.APP_INVITES_DIALOG;
    }

    private static Bundle createParameters(AppInviteContent $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putString(ShareConstants.APPLINK_URL, $r0.getApplinkUrl());
        $r1.putString(ShareConstants.PREVIEW_IMAGE_URL, $r0.getPreviewImageUrl());
        return $r1;
    }
}
