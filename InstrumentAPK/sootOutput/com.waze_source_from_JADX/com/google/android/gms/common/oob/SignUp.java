package com.google.android.gms.common.oob;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerCallback;
import android.accounts.AccountManagerFuture;
import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.internal.zzd;
import dalvik.annotation.Signature;
import java.io.IOException;

/* compiled from: dalvik_source_com.waze.apk */
public class SignUp {
    public static final String ACTION_OOB_SIGN_UP = "com.google.android.gms.common.oob.OOB_SIGN_UP";
    public static final String EXTRAS_CALLING_APP_DESCRIPTION = "com.google.android.gms.common.oob.EXTRAS_APP_DESCRIPTION";
    public static final String EXTRAS_CLIENT_CALLING_APP_PACKAGE = "com.google.android.gms.common.oob.EXTRAS_CLIENT_CALLING_APP_PACKAGE";
    public static final String EXTRAS_PROMO_APP_PACKAGE = "com.google.android.gms.common.oob.EXTRAS_PROMO_APP_PACKAGE";
    public static final String EXTRAS_PROMO_APP_TEXT = "com.google.android.gms.common.oob.EXTRAS_PROMO_APP_TEXT";
    public static final String EXTRA_ACCOUNT_NAME = "com.google.android.gms.common.oob.EXTRA_ACCOUNT_NAME";
    public static final String EXTRA_BACK_BUTTON_NAME = "com.google.android.gms.common.oob.EXTRA_BACK_BUTTON_NAME";
    public static final String EXTRA_GPSRC = "com.google.android.gms.plus.GPSRC";
    public static final String EXTRA_OVERRIDE_THEME = "com.google.android.gms.plus.OVERRIDE_THEME";
    public static final String[] GOOGLE_PLUS_REQUIRED_FEATURES = zzd.GOOGLE_PLUS_REQUIRED_FEATURES;
    public static final int THEME_DEFAULT = 0;
    public static final int THEME_FULL = 1;
    public static final int THEME_SETUP_WIZARD = 2;

    private SignUp() throws  {
    }

    public static AccountManagerFuture<Boolean> checkSignUpState(@Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Landroid/accounts/AccountManagerCallback", "<", "Ljava/lang/Boolean;", ">;", "Landroid/os/Handler;", ")", "Landroid/accounts/AccountManagerFuture", "<", "Ljava/lang/Boolean;", ">;"}) Context $r0, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Landroid/accounts/AccountManagerCallback", "<", "Ljava/lang/Boolean;", ">;", "Landroid/os/Handler;", ")", "Landroid/accounts/AccountManagerFuture", "<", "Ljava/lang/Boolean;", ">;"}) String $r1, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Landroid/accounts/AccountManagerCallback", "<", "Ljava/lang/Boolean;", ">;", "Landroid/os/Handler;", ")", "Landroid/accounts/AccountManagerFuture", "<", "Ljava/lang/Boolean;", ">;"}) String[] $r2, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Landroid/accounts/AccountManagerCallback", "<", "Ljava/lang/Boolean;", ">;", "Landroid/os/Handler;", ")", "Landroid/accounts/AccountManagerFuture", "<", "Ljava/lang/Boolean;", ">;"}) AccountManagerCallback<Boolean> $r3, @Signature({"(", "Landroid/content/Context;", "Ljava/lang/String;", "[", "Ljava/lang/String;", "Landroid/accounts/AccountManagerCallback", "<", "Ljava/lang/Boolean;", ">;", "Landroid/os/Handler;", ")", "Landroid/accounts/AccountManagerFuture", "<", "Ljava/lang/Boolean;", ">;"}) Handler $r4) throws  {
        zzab.zzb(!TextUtils.isEmpty($r1), (Object) "The accountName is required");
        zzab.zzb($r2 != null, (Object) "The requiredFeatures parameter is required");
        AccountManager $r5 = AccountManager.get($r0);
        boolean $z0 = false;
        for (Account $r7 : $r5.getAccountsByType("com.google")) {
            if ($r1.equals($r7.name)) {
                $z0 = true;
            }
        }
        if ($z0) {
            return $r5.hasFeatures(new Account($r1, "com.google"), $r2, $r3, $r4);
        }
        throw new IllegalStateException("Given account does not exist on the device");
    }

    public static boolean isSignedUpBlocking(Context $r0, String $r1, String[] $r2) throws AuthenticatorException, OperationCanceledException, IOException {
        return ((Boolean) checkSignUpState($r0, $r1, $r2, null, null).getResult()).booleanValue();
    }

    public static Intent newSignUpIntent(String $r0) throws  {
        return newSignUpIntent($r0, null);
    }

    public static Intent newSignUpIntent(String $r0, String $r1) throws  {
        Intent $r2 = new Intent();
        $r2.setPackage("com.google.android.gms");
        $r2.setAction(ACTION_OOB_SIGN_UP);
        $r2.putExtra(EXTRA_ACCOUNT_NAME, $r0);
        $r2.putExtra(EXTRA_BACK_BUTTON_NAME, $r1);
        return $r2;
    }

    public static Intent newSignUpIntent(String $r0, String $r1, String $r2, String $r3) throws  {
        Intent $r4 = new Intent();
        $r4.setPackage("com.google.android.gms");
        $r4.setAction(ACTION_OOB_SIGN_UP);
        $r4.putExtra(EXTRA_ACCOUNT_NAME, $r0);
        $r4.putExtra(EXTRA_BACK_BUTTON_NAME, $r1);
        $r4.putExtra(EXTRAS_PROMO_APP_PACKAGE, $r2);
        $r4.putExtra(EXTRAS_PROMO_APP_TEXT, $r3);
        return $r4;
    }
}
