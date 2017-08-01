package com.facebook.share.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogFeature;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.DialogPresenter.ParameterProvider;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.Sharer;
import com.facebook.share.Sharer.Result;
import com.facebook.share.internal.LegacyNativeDialogParameters;
import com.facebook.share.internal.MessageDialogFeature;
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphMessageDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public final class MessageDialog extends FacebookDialogBase<ShareContent, Result> implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.Message.toRequestCode();
    private boolean shouldFailOnDataError = false;

    private class NativeHandler extends ModeHandler {
        private NativeHandler() throws  {
            super();
        }

        public boolean canShow(ShareContent $r1) throws  {
            return $r1 != null && MessageDialog.canShow($r1.getClass());
        }

        public AppCall createAppCall(final ShareContent $r1) throws  {
            ShareContentValidation.validateForMessage($r1);
            final AppCall $r3 = MessageDialog.this.createBaseAppCall();
            final boolean $z0 = MessageDialog.this.getShouldFailOnDataError();
            MessageDialog.this.getActivityContext();
            DialogPresenter.setupAppCallForNativeDialog($r3, new ParameterProvider() {
                public Bundle getParameters() throws  {
                    return NativeDialogParameters.create($r3.getCallId(), $r1, $z0);
                }

                public Bundle getLegacyParameters() throws  {
                    return LegacyNativeDialogParameters.create($r3.getCallId(), $r1, $z0);
                }
            }, MessageDialog.getFeature($r1.getClass()));
            return $r3;
        }
    }

    public static void show(Activity $r0, ShareContent $r1) throws  {
        new MessageDialog($r0).show($r1);
    }

    public static void show(Fragment $r0, ShareContent $r1) throws  {
        new MessageDialog($r0).show($r1);
    }

    public static boolean canShow(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/facebook/share/model/ShareContent;", ">;)Z"}) Class<? extends ShareContent> $r0) throws  {
        DialogFeature $r1 = getFeature($r0);
        return $r1 != null && DialogPresenter.canPresentNativeDialogWithFeature($r1);
    }

    public MessageDialog(Activity $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    public MessageDialog(Fragment $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    MessageDialog(Activity $r1, int $i0) throws  {
        super($r1, $i0);
        ShareInternalUtility.registerStaticShareCallback($i0);
    }

    MessageDialog(Fragment $r1, int $i0) throws  {
        super($r1, $i0);
        ShareInternalUtility.registerStaticShareCallback($i0);
    }

    protected void registerCallbackImpl(@Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) CallbackManagerImpl $r1, @Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r2) throws  {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), $r1, $r2);
    }

    public boolean getShouldFailOnDataError() throws  {
        return this.shouldFailOnDataError;
    }

    public void setShouldFailOnDataError(boolean $z0) throws  {
        this.shouldFailOnDataError = $z0;
    }

    protected AppCall createBaseAppCall() throws  {
        return new AppCall(getRequestCode());
    }

    protected List<ModeHandler> getOrderedModeHandlers() throws  {
        ArrayList $r1 = new ArrayList();
        $r1.add(new NativeHandler());
        return $r1;
    }

    private static DialogFeature getFeature(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/facebook/share/model/ShareContent;", ">;)", "Lcom/facebook/internal/DialogFeature;"}) Class<? extends ShareContent> $r0) throws  {
        if (ShareLinkContent.class.isAssignableFrom($r0)) {
            return MessageDialogFeature.MESSAGE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom($r0)) {
            return MessageDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom($r0)) {
            return MessageDialogFeature.VIDEO;
        }
        return ShareOpenGraphContent.class.isAssignableFrom($r0) ? OpenGraphMessageDialogFeature.OG_MESSAGE_DIALOG : null;
    }
}
