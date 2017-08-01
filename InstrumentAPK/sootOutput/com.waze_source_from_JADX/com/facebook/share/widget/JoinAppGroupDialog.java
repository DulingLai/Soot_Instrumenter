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
import com.facebook.share.internal.ResultProcessor;
import com.facebook.share.internal.ShareInternalUtility;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class JoinAppGroupDialog extends FacebookDialogBase<String, Result> {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.AppGroupJoin.toRequestCode();
    private static final String JOIN_GAME_GROUP_DIALOG = "game_group_join";

    public static final class Result {
        private final Bundle data;

        private Result(Bundle $r1) throws  {
            this.data = $r1;
        }

        public Bundle getData() throws  {
            return this.data;
        }
    }

    private class WebHandler extends ModeHandler {
        public boolean canShow(String content) throws  {
            return true;
        }

        private WebHandler() throws  {
            super();
        }

        public AppCall createAppCall(String $r1) throws  {
            AppCall $r3 = JoinAppGroupDialog.this.createBaseAppCall();
            Bundle $r2 = new Bundle();
            $r2.putString("id", $r1);
            DialogPresenter.setupAppCallForWebDialog($r3, JoinAppGroupDialog.JOIN_GAME_GROUP_DIALOG, $r2);
            return $r3;
        }
    }

    public static boolean canShow() throws  {
        return true;
    }

    public static void show(Activity $r0, String $r1) throws  {
        new JoinAppGroupDialog($r0).show($r1);
    }

    public static void show(Fragment $r0, String $r1) throws  {
        new JoinAppGroupDialog($r0).show($r1);
    }

    public JoinAppGroupDialog(Activity $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    public JoinAppGroupDialog(Fragment $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    protected void registerCallbackImpl(@Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/JoinAppGroupDialog$Result;", ">;)V"}) CallbackManagerImpl $r1, @Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/JoinAppGroupDialog$Result;", ">;)V"}) final FacebookCallback<Result> $r2) throws  {
        C06281 $r4;
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
                return ShareInternalUtility.handleActivityResult(JoinAppGroupDialog.this.getRequestCode(), $i0, $r1, $r4);
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
