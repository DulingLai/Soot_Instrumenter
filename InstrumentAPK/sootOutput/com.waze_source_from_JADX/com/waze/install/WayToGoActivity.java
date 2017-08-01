package com.waze.install;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;

public final class WayToGoActivity extends ActivityBase {
    private NativeManager nativeManager;
    private InstallNativeManager nm;

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        this.nativeManager = AppService.getNativeManager();
        ((TextView) findViewById(C1283R.id.titleBar)).setText(this.nativeManager.getLanguageString(426));
        this.nm = new InstallNativeManager();
    }

    public void acceptClicked(View v) throws  {
        Log.d(Logger.TAG, "accept pressed");
        this.nm.termsOfUseResponse(1);
        setResult(-1);
        finish();
    }

    public void declineClicked(View v) throws  {
        Log.d(Logger.TAG, "decline pressed");
        this.nm.termsOfUseResponse(0);
        setResult(-1);
        finish();
    }
}
