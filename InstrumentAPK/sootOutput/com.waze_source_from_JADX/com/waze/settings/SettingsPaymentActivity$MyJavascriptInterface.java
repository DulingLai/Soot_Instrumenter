package com.waze.settings;

import android.webkit.JavascriptInterface;

class SettingsPaymentActivity$MyJavascriptInterface {
    private SettingsPaymentActivity activity;

    public SettingsPaymentActivity$MyJavascriptInterface(SettingsPaymentActivity $r1) throws  {
        this.activity = $r1;
    }

    @JavascriptInterface
    public void registrationEvent(String $r1) throws  {
        SettingsPaymentActivity.access$000(this.activity, Integer.valueOf($r1).intValue());
    }
}
