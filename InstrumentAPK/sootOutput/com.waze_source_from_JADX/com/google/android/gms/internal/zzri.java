package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import dalvik.annotation.Signature;

/* compiled from: dalvik_source_com.waze.apk */
public interface zzri {
    void startActivityForResult(Intent intent, int i) throws ;

    <T extends zzrh> T zza(@Signature({"<T:", "Lcom/google/android/gms/internal/zzrh;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) String str, @Signature({"<T:", "Lcom/google/android/gms/internal/zzrh;", ">(", "Ljava/lang/String;", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> cls) throws ;

    void zza(String str, @NonNull zzrh com_google_android_gms_internal_zzrh) throws ;

    Activity zzauk() throws ;
}
