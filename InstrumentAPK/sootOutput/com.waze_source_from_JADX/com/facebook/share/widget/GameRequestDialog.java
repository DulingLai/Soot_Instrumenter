package com.facebook.share.widget;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import com.facebook.FacebookCallback;
import com.facebook.internal.AppCall;
import com.facebook.internal.CallbackManagerImpl;
import com.facebook.internal.CallbackManagerImpl.Callback;
import com.facebook.internal.CallbackManagerImpl.RequestCodeOffset;
import com.facebook.internal.DialogPresenter;
import com.facebook.internal.FacebookDialogBase;
import com.facebook.share.internal.GameRequestValidation;
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareConstants;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.GameRequestContent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class GameRequestDialog extends FacebookDialogBase<GameRequestContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.GameRequest.toRequestCode();
    private static final String GAME_REQUEST_DIALOG = "apprequests";

    public static final class Result {
        String requestId;
        List<String> to;

        private Result(Bundle $r1) throws  {
            this.requestId = $r1.getString("request");
            this.to = new ArrayList();
            while (true) {
                if ($r1.containsKey(String.format(ShareConstants.WEB_DIALOG_RESULT_PARAM_TO_ARRAY_MEMBER, new Object[]{Integer.valueOf(this.to.size())}))) {
                    this.to.add($r1.getString(String.format(ShareConstants.WEB_DIALOG_RESULT_PARAM_TO_ARRAY_MEMBER, new Object[]{Integer.valueOf(this.to.size())})));
                } else {
                    return;
                }
            }
        }

        public String getRequestId() throws  {
            return this.requestId;
        }

        public List<String> getRequestRecipients() throws  {
            return this.to;
        }
    }

    private class WebHandler extends ModeHandler {
        public boolean canShow(GameRequestContent content) throws  {
            return true;
        }

        private WebHandler() throws  {
            super();
        }

        public AppCall createAppCall(GameRequestContent $r1) throws  {
            GameRequestValidation.validate($r1);
            AppCall $r3 = GameRequestDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebDialog($r3, GameRequestDialog.GAME_REQUEST_DIALOG, WebDialogParameters.create($r1));
            return $r3;
        }
    }

    public static boolean canShow() throws  {
        return true;
    }

    public static void show(Activity $r0, GameRequestContent $r1) throws  {
        new GameRequestDialog($r0).show($r1);
    }

    public static void show(Fragment $r0, GameRequestContent $r1) throws  {
        new GameRequestDialog($r0).show($r1);
    }

    public GameRequestDialog(Activity $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    public GameRequestDialog(Fragment $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    protected void registerCallbackImpl(@Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/GameRequestDialog$Result;", ">;)V"}) CallbackManagerImpl $r1, @Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/GameRequestDialog$Result;", ">;)V"}) final FacebookCallback<Result> $r2) throws  {
        C06261 $r4;
        if ($r2 == null) {
            $r4 = null;
        } else {
            $r4 = new ResultProcessor($r2) {
                public void onSuccess(AppCall $r1, Bundle $r2) throws  {
                    if ($r2 != null) {
                        $r2.onSuccess(new Result($r2));
                    } else {
                        onCancel($r1);
                    }
                }
            };
        }
        $r1.registerCallback(getRequestCode(), new Callback() {
            public boolean onActivityResult(int $i0, Intent $r1) throws  {
                return ShareInternalUtility.handleActivityResult(GameRequestDialog.this.getRequestCode(), $i0, $r1, $r4);
            }
        });
    }

    protected AppCall createBaseAppCall() throws  {
        return new AppCall(getRequestCode());
    }

    protected List<ModeHandler> getOrderedModeHandlers() throws  {
        ArrayList $r1 = new ArrayList();
        $r1.add(new WebHandler());
        return $r1;
    }
}
