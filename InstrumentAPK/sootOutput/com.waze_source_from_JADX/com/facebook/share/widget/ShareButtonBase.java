package com.facebook.share.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View.OnClickListener;
import com.facebook.CallbackManager;
import com.facebook.FacebookButtonBase;
import com.facebook.FacebookCallback;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer.Result;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.ShareContent;
import dalvik.annotation.Signature;

public abstract class ShareButtonBase extends FacebookButtonBase {
    private int requestCode = 0;
    private ShareContent shareContent;

    protected abstract OnClickListener getShareOnClickListener() throws ;

    protected ShareButtonBase(Context $r1, AttributeSet $r2, int $i0, String $r3, String $r4) throws  {
        int $i1 = 0;
        super($r1, $r2, $i0, 0, $r3, $r4);
        if (!isInEditMode()) {
            $i1 = getDefaultRequestCode();
        }
        this.requestCode = $i1;
    }

    public ShareContent getShareContent() throws  {
        return this.shareContent;
    }

    public void setShareContent(ShareContent $r1) throws  {
        this.shareContent = $r1;
    }

    public int getRequestCode() throws  {
        return this.requestCode;
    }

    protected void setRequestCode(int $i0) throws  {
        if (FacebookSdk.isFacebookRequestCode($i0)) {
            throw new IllegalArgumentException("Request code " + $i0 + " cannot be within the range reserved by the Facebook SDK.");
        }
        this.requestCode = $i0;
    }

    public void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) CallbackManager $r1, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;)V"}) FacebookCallback<Result> $r2) throws  {
        ShareInternalUtility.registerSharerCallback(getRequestCode(), $r1, $r2);
    }

    public void registerCallback(@Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;I)V"}) CallbackManager $r1, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;I)V"}) FacebookCallback<Result> $r2, @Signature({"(", "Lcom/facebook/CallbackManager;", "Lcom/facebook/FacebookCallback", "<", "Lcom/facebook/share/Sharer$Result;", ">;I)V"}) int $i0) throws  {
        setRequestCode($i0);
        registerCallback($r1, $r2);
    }

    protected void configureButton(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super.configureButton($r1, $r2, $i0, $i1);
        setInternalOnClickListener(getShareOnClickListener());
    }
}
