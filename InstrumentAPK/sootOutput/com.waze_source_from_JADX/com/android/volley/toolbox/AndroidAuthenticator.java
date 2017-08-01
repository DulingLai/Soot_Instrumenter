package com.android.volley.toolbox;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.android.volley.AuthFailureError;

public class AndroidAuthenticator implements Authenticator {
    private final Account mAccount;
    private final String mAuthTokenType;
    private final Context mContext;
    private final boolean mNotifyAuthFailure;

    public AndroidAuthenticator(Context $r1, Account $r2, String $r3) throws  {
        this($r1, $r2, $r3, false);
    }

    public AndroidAuthenticator(Context $r1, Account $r2, String $r3, boolean $z0) throws  {
        this.mContext = $r1;
        this.mAccount = $r2;
        this.mAuthTokenType = $r3;
        this.mNotifyAuthFailure = $z0;
    }

    public Account getAccount() throws  {
        return this.mAccount;
    }

    public String getAuthToken() throws AuthFailureError {
        AccountManagerFuture $r6 = AccountManager.get(this.mContext).getAuthToken(this.mAccount, this.mAuthTokenType, this.mNotifyAuthFailure, null, null);
        try {
            Bundle bundle = (Bundle) $r6.getResult();
            String $r5 = null;
            if ($r6.isDone() && !$r6.isCancelled()) {
                if (bundle.containsKey("intent")) {
                    throw new AuthFailureError((Intent) bundle.getParcelable("intent"));
                }
                $r5 = bundle.getString("authtoken");
            }
            if ($r5 != null) {
                return $r5;
            }
            throw new AuthFailureError("Got null auth token for type: " + this.mAuthTokenType);
        } catch (Exception $r1) {
            throw new AuthFailureError("Error while retrieving auth token", $r1);
        }
    }

    public void invalidateAuthToken(String $r1) throws  {
        AccountManager.get(this.mContext).invalidateAuthToken(this.mAccount.type, $r1);
    }
}
