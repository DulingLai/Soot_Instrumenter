package com.facebook.share.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.AnalyticsEvents;
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
import com.facebook.share.internal.NativeDialogParameters;
import com.facebook.share.internal.OpenGraphActionDialogFeature;
import com.facebook.share.internal.ShareContentValidation;
import com.facebook.share.internal.ShareDialogFeature;
import com.facebook.share.internal.ShareFeedContent;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public final class ShareDialog extends FacebookDialogBase<ShareContent, Result> implements Sharer {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.Share.toRequestCode();
    private static final String FEED_DIALOG = "feed";
    private static final String WEB_OG_SHARE_DIALOG = "share_open_graph";
    private static final String WEB_SHARE_DIALOG = "share";
    private boolean isAutomaticMode = true;
    private boolean shouldFailOnDataError = false;

    private class FeedHandler extends ModeHandler {
        private FeedHandler() throws  {
            super();
        }

        public Object getMode() throws  {
            return Mode.FEED;
        }

        public boolean canShow(ShareContent $r1) throws  {
            return ($r1 instanceof ShareLinkContent) || ($r1 instanceof ShareFeedContent);
        }

        public AppCall createAppCall(ShareContent $r1) throws  {
            Bundle $r8;
            ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), $r1, Mode.FEED);
            AppCall $r6 = ShareDialog.this.createBaseAppCall();
            if ($r1 instanceof ShareLinkContent) {
                ShareLinkContent $r7 = (ShareLinkContent) $r1;
                ShareContentValidation.validateForWebShare($r7);
                $r8 = WebDialogParameters.createForFeed($r7);
            } else {
                $r8 = WebDialogParameters.createForFeed((ShareFeedContent) $r1);
            }
            DialogPresenter.setupAppCallForWebDialog($r6, ShareDialog.FEED_DIALOG, $r8);
            return $r6;
        }
    }

    public enum Mode {
        AUTOMATIC,
        NATIVE,
        WEB,
        FEED
    }

    private class NativeHandler extends ModeHandler {
        private NativeHandler() throws  {
            super();
        }

        public Object getMode() throws  {
            return Mode.NATIVE;
        }

        public boolean canShow(ShareContent $r1) throws  {
            return $r1 != null && ShareDialog.canShowNative($r1.getClass());
        }

        public AppCall createAppCall(final ShareContent $r1) throws  {
            ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), $r1, Mode.NATIVE);
            ShareContentValidation.validateForNativeShare($r1);
            final AppCall $r6 = ShareDialog.this.createBaseAppCall();
            final boolean $z0 = ShareDialog.this.getShouldFailOnDataError();
            DialogPresenter.setupAppCallForNativeDialog($r6, new ParameterProvider() {
                public Bundle getParameters() throws  {
                    return NativeDialogParameters.create($r6.getCallId(), $r1, $z0);
                }

                public Bundle getLegacyParameters() throws  {
                    return LegacyNativeDialogParameters.create($r6.getCallId(), $r1, $z0);
                }
            }, ShareDialog.getFeature($r1.getClass()));
            return $r6;
        }
    }

    private class WebShareHandler extends ModeHandler {
        private WebShareHandler() throws  {
            super();
        }

        public Object getMode() throws  {
            return Mode.WEB;
        }

        public boolean canShow(ShareContent $r1) throws  {
            return $r1 != null && ShareDialog.canShowWebTypeCheck($r1.getClass());
        }

        public AppCall createAppCall(ShareContent $r1) throws  {
            Bundle $r8;
            ShareDialog.this.logDialogShare(ShareDialog.this.getActivityContext(), $r1, Mode.WEB);
            AppCall $r6 = ShareDialog.this.createBaseAppCall();
            ShareContentValidation.validateForWebShare($r1);
            if ($r1 instanceof ShareLinkContent) {
                $r8 = WebDialogParameters.create((ShareLinkContent) $r1);
            } else {
                $r8 = WebDialogParameters.create((ShareOpenGraphContent) $r1);
            }
            DialogPresenter.setupAppCallForWebDialog($r6, getActionName($r1), $r8);
            return $r6;
        }

        private String getActionName(ShareContent $r1) throws  {
            if ($r1 instanceof ShareLinkContent) {
                return ShareDialog.WEB_SHARE_DIALOG;
            }
            return $r1 instanceof ShareOpenGraphContent ? ShareDialog.WEB_OG_SHARE_DIALOG : null;
        }
    }

    public static void show(Activity $r0, ShareContent $r1) throws  {
        new ShareDialog($r0).show($r1);
    }

    public static void show(Fragment $r0, ShareContent $r1) throws  {
        new ShareDialog($r0).show($r1);
    }

    public static boolean canShow(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/facebook/share/model/ShareContent;", ">;)Z"}) Class<? extends ShareContent> $r0) throws  {
        return canShowWebTypeCheck($r0) || canShowNative($r0);
    }

    private static boolean canShowNative(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/facebook/share/model/ShareContent;", ">;)Z"}) Class<? extends ShareContent> $r0) throws  {
        DialogFeature $r1 = getFeature($r0);
        return $r1 != null && DialogPresenter.canPresentNativeDialogWithFeature($r1);
    }

    private static boolean canShowWebTypeCheck(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/facebook/share/model/ShareContent;", ">;)Z"}) Class<? extends ShareContent> $r0) throws  {
        return ShareLinkContent.class.isAssignableFrom($r0) || ShareOpenGraphContent.class.isAssignableFrom($r0);
    }

    public ShareDialog(Activity $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    public ShareDialog(Fragment $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
        ShareInternalUtility.registerStaticShareCallback(DEFAULT_REQUEST_CODE);
    }

    ShareDialog(Activity $r1, int $i0) throws  {
        super($r1, $i0);
        ShareInternalUtility.registerStaticShareCallback($i0);
    }

    ShareDialog(Fragment $r1, int $i0) throws  {
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

    public boolean canShow(ShareContent $r1, Mode $r2) throws  {
        Object $r22;
        if ($r2 == Mode.AUTOMATIC) {
            $r22 = FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
        return canShowImpl($r1, $r22);
    }

    public void show(ShareContent $r1, Mode $r2) throws  {
        Object $r22;
        this.isAutomaticMode = $r2 == Mode.AUTOMATIC;
        if (this.isAutomaticMode) {
            $r22 = FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
        showImpl($r1, $r22);
    }

    protected AppCall createBaseAppCall() throws  {
        return new AppCall(getRequestCode());
    }

    protected List<ModeHandler> getOrderedModeHandlers() throws  {
        ArrayList $r1 = new ArrayList();
        $r1.add(new NativeHandler());
        $r1.add(new FeedHandler());
        $r1.add(new WebShareHandler());
        return $r1;
    }

    private static DialogFeature getFeature(@Signature({"(", "Ljava/lang/Class", "<+", "Lcom/facebook/share/model/ShareContent;", ">;)", "Lcom/facebook/internal/DialogFeature;"}) Class<? extends ShareContent> $r0) throws  {
        if (ShareLinkContent.class.isAssignableFrom($r0)) {
            return ShareDialogFeature.SHARE_DIALOG;
        }
        if (SharePhotoContent.class.isAssignableFrom($r0)) {
            return ShareDialogFeature.PHOTOS;
        }
        if (ShareVideoContent.class.isAssignableFrom($r0)) {
            return ShareDialogFeature.VIDEO;
        }
        return ShareOpenGraphContent.class.isAssignableFrom($r0) ? OpenGraphActionDialogFeature.OG_ACTION_DIALOG : null;
    }

    private void logDialogShare(Context $r1, ShareContent $r2, Mode $r4) throws  {
        String $r6;
        String $r10;
        if (this.isAutomaticMode) {
            $r4 = Mode.AUTOMATIC;
        }
        switch ($r4) {
            case AUTOMATIC:
                $r6 = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_AUTOMATIC;
                break;
            case WEB:
                $r6 = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_WEB;
                break;
            case NATIVE:
                $r6 = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE;
                break;
            default:
                $r6 = "unknown";
                break;
        }
        DialogFeature $r8 = getFeature($r2.getClass());
        if ($r8 == ShareDialogFeature.SHARE_DIALOG) {
            $r10 = "status";
        } else if ($r8 == ShareDialogFeature.PHOTOS) {
            $r10 = AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_PHOTO;
        } else if ($r8 == ShareDialogFeature.VIDEO) {
            $r10 = AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_VIDEO;
        } else if ($r8 == OpenGraphActionDialogFeature.OG_ACTION_DIALOG) {
            $r10 = AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_OPENGRAPH;
        } else {
            $r10 = "unknown";
        }
        AppEventsLogger $r11 = AppEventsLogger.newLogger($r1);
        Bundle $r3 = new Bundle();
        $r3.putString("fb_share_dialog_show", $r6);
        $r3.putString(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_TYPE, $r10);
        $r11.logSdkEvent("fb_share_dialog_show", null, $r3);
    }
}
