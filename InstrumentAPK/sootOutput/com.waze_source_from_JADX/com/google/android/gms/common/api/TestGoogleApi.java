package com.google.android.gms.common.api;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import com.google.android.gms.common.api.Api.ApiOptions;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public class TestGoogleApi<O extends ApiOptions> extends zzc<O> {
    public TestGoogleApi(@NonNull @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Api<O> $r2, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) O $r3, @Signature({"(", "Landroid/content/Context;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Looper $r4) throws  {
        super($r1, (Api) $r2, (ApiOptions) $r3, $r4);
    }

    public TestGoogleApi(@NonNull @Signature({"(", "Landroid/support/v4/app/FragmentActivity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) FragmentActivity $r1, @Signature({"(", "Landroid/support/v4/app/FragmentActivity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) Api<O> $r2, @Signature({"(", "Landroid/support/v4/app/FragmentActivity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;)V"}) O $r3) throws  {
        super((Activity) $r1, (Api) $r2, (ApiOptions) $r3);
    }

    public TestGoogleApi(@NonNull @Signature({"(", "Landroid/support/v4/app/FragmentActivity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) FragmentActivity $r1, @Signature({"(", "Landroid/support/v4/app/FragmentActivity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Api<O> $r2, @Signature({"(", "Landroid/support/v4/app/FragmentActivity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) O $r3, @Signature({"(", "Landroid/support/v4/app/FragmentActivity;", "Lcom/google/android/gms/common/api/Api", "<TO;>;TO;", "Landroid/os/Looper;", ")V"}) Looper $r4) throws  {
        super((Activity) $r1, (Api) $r2, (ApiOptions) $r3, $r4);
    }

    public GoogleApiClient asGoogleApiClient() throws  {
        return super.asGoogleApiClient();
    }
}
