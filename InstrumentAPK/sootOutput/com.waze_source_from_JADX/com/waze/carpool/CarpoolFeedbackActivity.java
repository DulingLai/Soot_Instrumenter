package com.waze.carpool;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.strings.DisplayStrings;
import com.waze.view.web.SimpleWebActivity;

public class CarpoolFeedbackActivity extends SimpleWebActivity {
    private static final long DIALOG_TIMEOUT = 1000;
    boolean bLoadedJavaScript = false;
    private CarpoolDrive drive;
    private CarpoolRide ride;

    class C14151 implements OnClickListener {
        C14151() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolFeedbackActivity.this.setResult(0);
            CarpoolFeedbackActivity.this.finish();
        }
    }

    class C14172 implements IResultOk {
        C14172() throws  {
        }

        public void onResult(boolean $z0) throws  {
            if ($z0) {
                CarpoolFeedbackActivity.this.setResult(-1);
                final NativeManager $r2 = NativeManager.getInstance();
                $r2.OpenProgressIconPopup($r2.getLanguageString((int) DisplayStrings.DS_RIDE_REQ_REPORTED), "sign_up_big_v");
                CarpoolFeedbackActivity.this.postDelayed(new Runnable() {
                    public void run() throws  {
                        $r2.CloseProgressPopup();
                        CarpoolFeedbackActivity.this.finish();
                    }
                }, CarpoolFeedbackActivity.DIALOG_TIMEOUT);
            }
        }
    }

    private static class MyJavascriptInterface {
        private CarpoolFeedbackActivity activity;

        public MyJavascriptInterface(CarpoolFeedbackActivity $r1) throws  {
            this.activity = $r1;
        }

        @JavascriptInterface
        public void clientCallback(String $r1) throws  {
            this.activity.sendResults($r1);
        }
    }

    public static Intent getIntent(Context $r0, CarpoolDrive $r1, CarpoolRide $r2) throws  {
        Intent $r3 = new Intent($r0, CarpoolFeedbackActivity.class);
        $r3.putExtra(CarpoolDrive.class.getSimpleName(), $r1);
        $r3.putExtra(CarpoolRide.class.getSimpleName(), $r2);
        return $r3;
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.drive = (CarpoolDrive) getIntent().getParcelableExtra(CarpoolDrive.class.getSimpleName());
        this.ride = (CarpoolRide) getIntent().getParcelableExtra(CarpoolRide.class.getSimpleName());
        setTitleStr(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_RIDE_REQ_PROBLEM_FORM_TITLE));
        this.mTitleBar.setOnClickCloseListener(new C14151());
        this.webView.addJavascriptInterface(new MyJavascriptInterface(this), "androidInterface");
        String $r4 = CarpoolNativeManager.getInstance().configGetSurveyUrlNTV();
        if ($r1 == null) {
            loadUrl($r4);
        }
    }

    private void sendResults(String $r1) throws  {
        if (this.drive != null && this.ride != null) {
            CarpoolNativeManager.getInstance().submitSurvey(this.drive, this.ride, $r1, new C14172());
        }
    }
}
