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
import com.facebook.share.internal.WebDialogParameters;
import com.facebook.share.model.AppGroupCreationContent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

public class CreateAppGroupDialog extends FacebookDialogBase<AppGroupCreationContent, Result> {
    private static final int DEFAULT_REQUEST_CODE = RequestCodeOffset.AppGroupCreate.toRequestCode();
    private static final String GAME_GROUP_CREATION_DIALOG = "game_group_create";

    public static final class Result {
        private final String id;

        private Result(String $r1) throws  {
            this.id = $r1;
        }

        public String getId() throws  {
            return this.id;
        }
    }

    private class WebHandler extends ModeHandler {
        public boolean canShow(AppGroupCreationContent content) throws  {
            return true;
        }

        private WebHandler() throws  {
            super();
        }

        public AppCall createAppCall(AppGroupCreationContent $r1) throws  {
            AppCall $r3 = CreateAppGroupDialog.this.createBaseAppCall();
            DialogPresenter.setupAppCallForWebDialog($r3, CreateAppGroupDialog.GAME_GROUP_CREATION_DIALOG, WebDialogParameters.create($r1));
            return $r3;
        }
    }

    public static boolean canShow() throws  {
        return true;
    }

    public static void show(Activity $r0, AppGroupCreationContent $r1) throws  {
        new CreateAppGroupDialog($r0).show($r1);
    }

    public static void show(Fragment $r0, AppGroupCreationContent $r1) throws  {
        new CreateAppGroupDialog($r0).show($r1);
    }

    public CreateAppGroupDialog(Activity $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    public CreateAppGroupDialog(Fragment $r1) throws  {
        super($r1, DEFAULT_REQUEST_CODE);
    }

    protected void registerCallbackImpl(@Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/CreateAppGroupDialog$Result;", ">;)V"}) CallbackManagerImpl $r1, @Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/widget/CreateAppGroupDialog$Result;", ">;)V"}) final FacebookCallback<Result> $r2) throws  {
        C06241 $r4;
        if ($r2 == null) {
            $r4 = null;
        } else {
            $r4 = new ResultProcessor($r2) {
                public void onSuccess(AppCall appCall, Bundle $r2) throws  {
                    $r2.onSuccess(new Result($r2.getString("id")));
                }
            };
        }
        $r1.registerCallback(getRequestCode(), new Callback() {
            public boolean onActivityResult(int $i0, Intent $r1) throws  {
                return ShareInternalUtility.handleActivityResult(CreateAppGroupDialog.this.getRequestCode(), $i0, $r1, $r4);
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
