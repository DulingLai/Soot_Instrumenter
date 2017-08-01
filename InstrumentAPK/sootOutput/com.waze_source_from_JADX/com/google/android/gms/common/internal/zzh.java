package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.util.Log;
import com.google.android.gms.C0643R;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzh {
    public static String zzb(Context $r0, int $i0, String $r1) throws  {
        Resources $r2 = $r0.getResources();
        switch ($i0) {
            case 1:
                if (com.google.android.gms.common.util.zzh.zzb($r2)) {
                    return $r2.getString(C0643R.string.common_google_play_services_install_text_tablet, new Object[]{$r1});
                }
                return $r2.getString(C0643R.string.common_google_play_services_install_text_phone, new Object[]{$r1});
            case 2:
                return $r2.getString(C0643R.string.common_google_play_services_update_text, new Object[]{$r1});
            case 3:
                return $r2.getString(C0643R.string.common_google_play_services_enable_text, new Object[]{$r1});
            case 5:
                return $r2.getString(C0643R.string.common_google_play_services_invalid_account_text);
            case 7:
                return $r2.getString(C0643R.string.common_google_play_services_network_error_text);
            case 9:
                return $r2.getString(C0643R.string.common_google_play_services_unsupported_text, new Object[]{$r1});
            case 16:
                return $r2.getString(C0643R.string.common_google_play_services_api_unavailable_text, new Object[]{$r1});
            case 17:
                return $r2.getString(C0643R.string.common_google_play_services_sign_in_failed_text);
            case 18:
                return $r2.getString(C0643R.string.common_google_play_services_updating_text, new Object[]{$r1});
            case 20:
                return $r2.getString(C0643R.string.common_google_play_services_restricted_profile_text);
            case 42:
                return $r2.getString(C0643R.string.common_google_play_services_wear_update_text);
            default:
                return $r2.getString(C0643R.string.common_google_play_services_unknown_issue, new Object[]{$r1});
        }
    }

    public static String zzc(Context $r0, int $i0, String $r1) throws  {
        return $i0 == 6 ? $r0.getResources().getString(C0643R.string.common_google_play_services_resolution_required_text) : zzb($r0, $i0, $r1);
    }

    @Nullable
    public static String zze(Context $r0, int $i0) throws  {
        Resources $r1 = $r0.getResources();
        switch ($i0) {
            case 1:
                return $r1.getString(C0643R.string.common_google_play_services_install_title);
            case 2:
            case 42:
                return $r1.getString(C0643R.string.common_google_play_services_update_title);
            case 3:
                return $r1.getString(C0643R.string.common_google_play_services_enable_title);
            case 4:
            case 6:
                break;
            case 5:
                Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                return $r1.getString(C0643R.string.common_google_play_services_invalid_account_title);
            case 7:
                Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                return $r1.getString(C0643R.string.common_google_play_services_network_error_title);
            case 8:
                Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                return null;
            case 9:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return $r1.getString(C0643R.string.common_google_play_services_unsupported_title);
            case 10:
                Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                return null;
            case 11:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case 16:
                Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                return null;
            case 17:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return $r1.getString(C0643R.string.common_google_play_services_sign_in_failed_title);
            case 18:
                return $r1.getString(C0643R.string.common_google_play_services_updating_title);
            case 20:
                Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
                return $r1.getString(C0643R.string.common_google_play_services_restricted_profile_title);
            default:
                Log.e("GoogleApiAvailability", "Unexpected error code " + $i0);
                break;
        }
        return null;
    }

    @Nullable
    public static String zzf(Context $r0, int $i0) throws  {
        return $i0 == 6 ? $r0.getResources().getString(C0643R.string.common_google_play_services_resolution_required_title) : zze($r0, $i0);
    }

    public static String zzg(Context $r0, int $i0) throws  {
        Resources $r1 = $r0.getResources();
        switch ($i0) {
            case 1:
                return $r1.getString(C0643R.string.common_google_play_services_install_button);
            case 2:
                return $r1.getString(C0643R.string.common_google_play_services_update_button);
            case 3:
                return $r1.getString(C0643R.string.common_google_play_services_enable_button);
            default:
                return $r1.getString(17039370);
        }
    }
}
