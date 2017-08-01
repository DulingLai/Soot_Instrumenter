package com.google.android.gms.auth.api.signin.internal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.SignInAccount;
import com.google.android.gms.common.annotation.KeepName;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import dalvik.annotation.Signature;

@KeepName
/* compiled from: dalvik_source_com.waze.apk */
public class SignInHubActivity extends FragmentActivity {
    private zzk gd;
    private SignInConfiguration ge;
    private boolean gf;
    private int gg;
    private Intent gh;

    /* compiled from: dalvik_source_com.waze.apk */
    private class zza implements LoaderCallbacks<Void> {
        final /* synthetic */ SignInHubActivity gi;

        private zza(SignInHubActivity $r1) throws  {
            this.gi = $r1;
        }

        public Loader<Void> onCreateLoader(@Signature({"(I", "Landroid/os/Bundle;", ")", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Void;", ">;"}) int i, @Signature({"(I", "Landroid/os/Bundle;", ")", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Void;", ">;"}) Bundle bundle) throws  {
            return new zzb(this.gi, GoogleApiClient.zzarw());
        }

        public /* synthetic */ void onLoadFinished(Loader $r1, Object $r2) throws  {
            zza($r1, (Void) $r2);
        }

        public void onLoaderReset(@Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Void;", ">;)V"}) Loader<Void> loader) throws  {
        }

        public void zza(@Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Void;", ">;", "Ljava/lang/Void;", ")V"}) Loader<Void> loader, @Signature({"(", "Landroid/support/v4/content/Loader", "<", "Ljava/lang/Void;", ">;", "Ljava/lang/Void;", ")V"}) Void voidR) throws  {
            this.gi.setResult(this.gi.gg, this.gi.gh);
            this.gi.finish();
        }
    }

    private void zza(int $i0, Intent $r1) throws  {
        if ($r1 != null) {
            SignInAccount $r3 = (SignInAccount) $r1.getParcelableExtra(GoogleSignInApi.EXTRA_SIGN_IN_ACCOUNT);
            if ($r3 != null && $r3.zzaeo() != null) {
                GoogleSignInAccount $r4 = $r3.zzaeo();
                this.gd.zzb($r4, this.ge.zzaez());
                $r1.removeExtra(GoogleSignInApi.EXTRA_SIGN_IN_ACCOUNT);
                $r1.putExtra("googleSignInAccount", $r4);
                this.gf = true;
                this.gg = $i0;
                this.gh = $r1;
                zzafa();
                return;
            } else if ($r1.hasExtra("errorCode")) {
                zzdg($r1.getIntExtra("errorCode", 8));
                return;
            }
        }
        zzdg(8);
    }

    private void zzafa() throws  {
        getSupportLoaderManager().initLoader(0, null, new zza());
    }

    private void zzdg(int $i0) throws  {
        Status $r2 = new Status($i0);
        Intent $r1 = new Intent();
        $r1.putExtra("googleSignInStatus", $r2);
        setResult(0, $r1);
        finish();
    }

    private void zzj(Intent $r1) throws  {
        $r1.setPackage("com.google.android.gms");
        $r1.putExtra("config", this.ge);
        try {
            startActivityForResult($r1, 40962);
        } catch (ActivityNotFoundException e) {
            Log.w("AuthSignInClient", "Could not launch sign in Intent. Google Play Service is probably being updated...");
            zzdg(8);
        }
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) throws  {
        return true;
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        setResult(0);
        switch ($i0) {
            case 40962:
                zza($i1, $r1);
                return;
            default:
                return;
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.gd = zzk.zzbc(this);
        Intent $r4 = getIntent();
        if (!"com.google.android.gms.auth.GOOGLE_SIGN_IN".equals($r4.getAction())) {
            String $r2 = "Unknown action: ";
            String $r5 = String.valueOf($r4.getAction());
            Log.e("AuthSignInClient", $r5.length() != 0 ? $r2.concat($r5) : new String("Unknown action: "));
            finish();
        }
        this.ge = (SignInConfiguration) $r4.getParcelableExtra("config");
        if (this.ge == null) {
            Log.e("AuthSignInClient", "Activity started with invalid configuration.");
            setResult(0);
            finish();
        } else if ($r1 == null) {
            zzj(new Intent("com.google.android.gms.auth.GOOGLE_SIGN_IN"));
        } else {
            this.gf = $r1.getBoolean("signingInGoogleApiClients");
            if (this.gf) {
                this.gg = $r1.getInt("signInResultCode");
                this.gh = (Intent) $r1.getParcelable("signInResultData");
                zzafa();
            }
        }
    }

    protected void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putBoolean("signingInGoogleApiClients", this.gf);
        if (this.gf) {
            $r1.putInt("signInResultCode", this.gg);
            $r1.putParcelable("signInResultData", this.gh);
        }
    }
}
