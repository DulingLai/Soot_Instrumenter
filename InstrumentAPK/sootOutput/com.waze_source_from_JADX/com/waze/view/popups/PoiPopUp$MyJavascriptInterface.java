package com.waze.view.popups;

import android.webkit.JavascriptInterface;

class PoiPopUp$MyJavascriptInterface {
    private PoiPopUp activity;

    public PoiPopUp$MyJavascriptInterface(PoiPopUp $r1) throws  {
        this.activity = $r1;
    }

    @JavascriptInterface
    public void processReturnValue(int index, String $r1) throws  {
        PoiPopUp.access$1600(this.activity, PoiPopUp.access$1500(this.activity), $r1);
    }
}
