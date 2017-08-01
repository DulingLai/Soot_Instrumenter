package com.facebook.internal;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookDialog;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.google.android.gms.auth.firstparty.recovery.RecoveryParamConstants;
import dalvik.annotation.Signature;
import java.util.List;

public abstract class FacebookDialogBase<CONTENT, RESULT> implements FacebookDialog<CONTENT, RESULT> {
    protected static final Object BASE_AUTOMATIC_MODE = new Object();
    private static final String TAG = "FacebookDialog";
    private final Activity activity;
    private final Fragment fragment;
    private List<ModeHandler> modeHandlers;
    private int requestCode;

    protected abstract class ModeHandler {
        public abstract boolean canShow(@Signature({"(TCONTENT;)Z"}) CONTENT content) throws ;

        public abstract AppCall createAppCall(@Signature({"(TCONTENT;)", "Lcom/facebook/internal/AppCall;"}) CONTENT content) throws ;

        protected ModeHandler() throws  {
        }

        public Object getMode() throws  {
            return FacebookDialogBase.BASE_AUTOMATIC_MODE;
        }
    }

    protected abstract AppCall createBaseAppCall() throws ;

    protected abstract List<ModeHandler> getOrderedModeHandlers() throws ;

    protected abstract void registerCallbackImpl(@Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;)V"}) CallbackManagerImpl callbackManagerImpl, @Signature({"(", "Lcom/facebook/internal/CallbackManagerImpl;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;)V"}) FacebookCallback<RESULT> facebookCallback) throws ;

    protected FacebookDialogBase(Activity $r1, int $i0) throws  {
        Validate.notNull($r1, RecoveryParamConstants.VALUE_ACTIVITY);
        this.activity = $r1;
        this.fragment = null;
        this.requestCode = $i0;
    }

    protected FacebookDialogBase(Fragment $r1, int $i0) throws  {
        Validate.notNull($r1, "fragment");
        this.fragment = $r1;
        this.activity = null;
        this.requestCode = $i0;
        if ($r1.getActivity() == null) {
            throw new IllegalArgumentException("Cannot use a fragment that is not attached to an activity");
        }
    }

    public final void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;)V"}) CallbackManager $r2, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;)V"}) FacebookCallback<RESULT> $r1) throws  {
        if ($r2 instanceof CallbackManagerImpl) {
            registerCallbackImpl((CallbackManagerImpl) $r2, $r1);
            return;
        }
        throw new FacebookException("Unexpected CallbackManager, please use the provided Factory.");
    }

    public final void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;I)V"}) CallbackManager $r1, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;I)V"}) FacebookCallback<RESULT> $r2, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<TRESU", "LT;", ">;I)V"}) int $i0) throws  {
        setRequestCode($i0);
        registerCallback($r1, $r2);
    }

    protected void setRequestCode(int $i0) throws  {
        if (FacebookSdk.isFacebookRequestCode($i0)) {
            throw new IllegalArgumentException("Request code " + $i0 + " cannot be within the range reserved by the Facebook SDK.");
        }
        this.requestCode = $i0;
    }

    public int getRequestCode() throws  {
        return this.requestCode;
    }

    public boolean canShow(@Signature({"(TCONTENT;)Z"}) CONTENT $r1) throws  {
        return canShowImpl($r1, BASE_AUTOMATIC_MODE);
    }

    protected boolean canShowImpl(@Signature({"(TCONTENT;", "Ljava/lang/Object;", ")Z"}) CONTENT $r1, @Signature({"(TCONTENT;", "Ljava/lang/Object;", ")Z"}) Object $r2) throws  {
        boolean $z0 = $r2 == BASE_AUTOMATIC_MODE;
        for (ModeHandler $r6 : cachedModeHandlers()) {
            if (($z0 || Utility.areObjectsEqual($r6.getMode(), $r2)) && $r6.canShow($r1)) {
                return true;
            }
        }
        return false;
    }

    public void show(@Signature({"(TCONTENT;)V"}) CONTENT $r1) throws  {
        showImpl($r1, BASE_AUTOMATIC_MODE);
    }

    protected void showImpl(@Signature({"(TCONTENT;", "Ljava/lang/Object;", ")V"}) CONTENT $r1, @Signature({"(TCONTENT;", "Ljava/lang/Object;", ")V"}) Object $r2) throws  {
        AppCall $r3 = createAppCallForMode($r1, $r2);
        if ($r3 == null) {
            Log.e(TAG, "No code path should ever result in a null appCall");
            if (FacebookSdk.isDebugEnabled()) {
                throw new IllegalStateException("No code path should ever result in a null appCall");
            }
        } else if (this.fragment != null) {
            DialogPresenter.present($r3, this.fragment);
        } else {
            DialogPresenter.present($r3, this.activity);
        }
    }

    protected Activity getActivityContext() throws  {
        if (this.activity != null) {
            return this.activity;
        }
        return this.fragment != null ? this.fragment.getActivity() : null;
    }

    private AppCall createAppCallForMode(@Signature({"(TCONTENT;", "Ljava/lang/Object;", ")", "Lcom/facebook/internal/AppCall;"}) CONTENT $r1, @Signature({"(TCONTENT;", "Ljava/lang/Object;", ")", "Lcom/facebook/internal/AppCall;"}) Object $r2) throws  {
        boolean $z0 = $r2 == BASE_AUTOMATIC_MODE;
        AppCall $r5 = null;
        for (ModeHandler $r8 : cachedModeHandlers()) {
            if (($z0 || Utility.areObjectsEqual($r8.getMode(), $r2)) && $r8.canShow($r1)) {
                try {
                    $r5 = $r8.createAppCall($r1);
                    break;
                } catch (FacebookException $r3) {
                    AppCall $r9 = createBaseAppCall();
                    $r5 = $r9;
                    DialogPresenter.setupAppCallForValidationError($r9, $r3);
                }
            }
        }
        if ($r5 != null) {
            return $r5;
        }
        $r5 = createBaseAppCall();
        DialogPresenter.setupAppCallForCannotShowError($r5);
        return $r5;
    }

    private List<ModeHandler> cachedModeHandlers() throws  {
        if (this.modeHandlers == null) {
            this.modeHandlers = getOrderedModeHandlers();
        }
        return this.modeHandlers;
    }
}
