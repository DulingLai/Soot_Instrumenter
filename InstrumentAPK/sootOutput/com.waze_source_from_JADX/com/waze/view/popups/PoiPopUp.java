package com.waze.view.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.location.Location;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.LocationFactory;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.navigate.NavigateNativeManager;
import com.waze.navigate.NavigateStopReason;
import com.waze.navigate.PublicMacros;
import com.waze.reports.VenueData;
import com.waze.settings.SettingsNativeManager;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.map.ProgressAnimation;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.json.JSONObject;

public class PoiPopUp extends PopUp implements DriveToNavigateCallback {
    private static int mLat;
    private static int mLon;
    private int VOICE_ACTIONS_INDEX;
    private AddressItem mAddressItem;
    private boolean mAnalyticsAdsNotifyClose;
    private boolean mHadError;
    private int mId;
    private String mInfoUrl;
    private boolean mIsInMiddleOfPreload;
    private boolean mIsLoaded;
    private boolean mIsNavigateable;
    private boolean mIsPoiLoadPending;
    private boolean mIsPoiLoaded;
    private boolean mIsShown;
    private boolean mIsTakeOver;
    private boolean mIsTemplatePreloaded;
    private LayoutManager mLayoutManager;
    private final Runnable mPoiLoader;
    private String mPoiUrl;
    private String mPreloadedUrl;
    private String mPreloadedVenueId;
    private ProgressAnimation mProgressAnimation;
    private String mPromotionUrl;
    private List<String> mResults;
    private int mServerId;
    private Runnable mVenuGetFailRunnable;
    private String mVenueContext;
    private Handler mVenueGetHandler;
    private String mVenueID;
    private WebView mWebView;
    private int mX;
    private int mY;

    class C31861 implements Runnable {
        C31861() {
        }

        public void run() {
            PoiPopUp.this.mIsPoiLoadPending = false;
            PoiPopUp.this.mIsPoiLoaded = true;
            PoiPopUp.this.mWebView.loadUrl(PoiPopUp.this.mPoiUrl);
            if (!PoiPopUp.this.mIsTakeOver) {
                PoiPopUp.this.mProgressAnimation.stop();
            }
            PoiPopUp.this.mProgressAnimation.setVisibility(8);
        }
    }

    class C31872 implements Runnable {
        C31872() {
        }

        public void run() {
            AppService.getNativeManager().externalPoiClosedNTV(PoiPopUp.this.mAnalyticsAdsNotifyClose);
        }
    }

    class C31893 implements AnimationListener {

        class C31881 implements Runnable {
            C31881() {
            }

            public void run() {
                PoiPopUp.this.hide();
            }
        }

        C31893() {
        }

        public void onAnimationEnd(Animation arg0) {
            PoiPopUp.this.post(new C31881());
        }

        public void onAnimationRepeat(Animation arg0) {
        }

        public void onAnimationStart(Animation arg0) {
        }
    }

    class C31936 extends WebChromeClient {
        C31936() {
        }

        public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
            return super.onConsoleMessage(consoleMessage);
        }
    }

    class C31947 implements OnTouchListener {
        C31947() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            PoiPopUp.this.mLayoutManager.SetPopUpInterrupt(false);
            return false;
        }
    }

    private static final class CompatabilityWrapper {
        private CompatabilityWrapper() {
        }

        @SuppressLint({"NewApi"})
        public static void setLayerType(WebView mWebView) {
            mWebView.setLayerType(1, null);
        }
    }

    private final class PoiBgWebClient extends WebViewClient {
        long loadStart;

        class C31962 implements Runnable {
            C31962() {
            }

            public void run() {
                PoiPopUp.this.onNavigateButton();
            }
        }

        class C31973 implements Runnable {
            C31973() {
            }

            public void run() {
                PoiPopUp.this.onPromotionButton();
            }
        }

        private PoiBgWebClient() {
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Logger.d("PoiPopUp.shouldOverrideUrlLoading() url=" + url);
            url = url.replace('+', ' ');
            if (url.contains(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX)) {
                PoiPopUp.this.mLayoutManager.SetPopUpInterrupt(true);
                Analytics.logAdsContext(AnalyticsEvents.ANALYTICS_EVENT_ADS_POPUP_PHONE_CLICKED);
                final Intent intent = new Intent("android.intent.action.DIAL", Uri.parse(url.substring(url.indexOf(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX), url.length())));
                final Activity activity = AppService.getMainActivity();
                activity.runOnUiThread(new Runnable() {
                    public void run() {
                        PoiPopUp.this.mAnalyticsAdsNotifyClose = false;
                        PoiPopUp.this.mLayoutManager.callCloseAllPopups(1);
                        activity.startActivity(intent);
                    }
                });
                Analytics.logAdsContextDisplayTime("CLICK");
            } else if (url.contains("external_poi_nav")) {
                PoiPopUp.this.mLayoutManager.SetPopUpInterrupt(true);
                AppService.getMainActivity().runOnUiThread(new C31962());
                Analytics.logAdsContextDisplayTime("CLICK");
            } else if (url.contains("external_poi_info")) {
                AppService.getMainActivity().runOnUiThread(new C31973());
                Analytics.logAdsContextDisplayTime("CLICK");
            } else {
                if (PoiPopUp.this.mAddressItem != null) {
                    if (url.contains("brand_opt_in")) {
                        MyWazeNativeManager.getInstance().addStoreByBrandId(PoiPopUp.this.mAddressItem.brandId);
                        NativeManager.getInstance().addPlaceToRecent(PoiPopUp.this.mVenueID, PoiPopUp.this.mAddressItem.getTitle(), PoiPopUp.this.mAddressItem.getStreet(), PoiPopUp.this.mAddressItem.getCity(), PoiPopUp.this.mAddressItem.mImageURL, PoiPopUp.this.mVenueContext);
                    } else if (url.contains("brand_opt_out")) {
                        MyWazeNativeManager.getInstance().removeStoreByBrandId(PoiPopUp.this.mAddressItem.brandId);
                    }
                }
                if (AppService.getNativeManager().UrlHandler(url, true)) {
                    Analytics.logAdsContextDisplayTime("CLICK");
                } else {
                    view.loadUrl(url);
                }
            }
            return true;
        }

        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            Logger.d("PoiPopUp.onPageStarted() url=" + url);
            PoiPopUp.this.mIsLoaded = false;
            this.loadStart = System.currentTimeMillis();
            super.onPageStarted(view, url, favicon);
            if (!PoiPopUp.this.mIsTakeOver) {
                PoiPopUp.this.mProgressAnimation.start();
                PoiPopUp.this.mProgressAnimation.setVisibility(0);
            }
        }

        public void onPageFinished(WebView view, String url) {
            Logger.d("PoiPopUp.onPageFinished() mHadError=" + PoiPopUp.this.mHadError + "; mIsLoaded=" + PoiPopUp.this.mIsLoaded + "; url=" + url + ";  mIsTemplatePreloaded = " + PoiPopUp.this.mIsTemplatePreloaded + "; mIsPoiLoadPending = " + PoiPopUp.this.mIsPoiLoadPending);
            if (!PoiPopUp.this.mHadError) {
                PoiPopUp.this.mIsLoaded = true;
                super.onPageFinished(view, url);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_LATENCY_TO_LOAD, "TIME", "" + (System.currentTimeMillis() - this.loadStart));
                PoiPopUp.this.mIsTemplatePreloaded = true;
                if (PoiPopUp.this.mIsPoiLoadPending) {
                    PoiPopUp.this.postDelayed(PoiPopUp.this.mPoiLoader, 10);
                }
            }
        }

        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            PoiPopUp.this.mIsLoaded = false;
            PoiPopUp.this.mHadError = true;
            Logger.d("PoiPopUp.onReceivedError() errorCode=" + errorCode + "; desc= " + description + "; url=" + failingUrl);
            super.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    private void processJsReturnValue(int r7, java.lang.String r8) {
        /* JADX: method processing error */
/*
Error: java.util.NoSuchElementException
	at java.util.HashMap$HashIterator.nextNode(HashMap.java:1431)
	at java.util.HashMap$KeyIterator.next(HashMap.java:1453)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.applyRemove(BlockFinallyExtract.java:535)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.extractFinally(BlockFinallyExtract.java:175)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.processExceptionHandler(BlockFinallyExtract.java:79)
	at jadx.core.dex.visitors.blocksmaker.BlockFinallyExtract.visit(BlockFinallyExtract.java:51)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r6 = this;
        r5 = 0;
        if (r8 != 0) goto L_0x0024;
    L_0x0003:
        r2 = com.waze.NativeManager.getInstance();	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r3 = r6.mResults;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r3 = r3.toArray();	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r2.SetVoiceActionsStr(r3);	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r2 = r6.GetTimer();
        if (r2 > 0) goto L_0x0023;
    L_0x0016:
        r2 = com.waze.NativeManager.getInstance();
        r3 = com.waze.PopupAction.popup_shown;
        r3 = r3.ordinal();
        r2.PopupAction(r3, r5, r5);
    L_0x0023:
        return;
    L_0x0024:
        r1 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r1.<init>(r8);	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r0 = 0;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
    L_0x002a:
        r2 = r1.length();	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        if (r0 >= r2) goto L_0x003e;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
    L_0x0030:
        r3 = r6.mResults;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r2 = r1.get(r0);	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r2 = (java.lang.String) r2;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r3.add(r2);	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r0 = r0 + 1;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        goto L_0x002a;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
    L_0x003e:
        r2 = com.waze.NativeManager.getInstance();	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r3 = r6.mResults;	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r3 = r3.toArray();	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r2.SetVoiceActionsStr(r3);	 Catch:{ JSONException -> 0x005f, all -> 0x0074 }
        r2 = r6.GetTimer();
        if (r2 > 0) goto L_0x0023;
    L_0x0051:
        r2 = com.waze.NativeManager.getInstance();
        r3 = com.waze.PopupAction.popup_shown;
        r3 = r3.ordinal();
        r2.PopupAction(r3, r5, r5);
        goto L_0x0023;
    L_0x005f:
        r2 = move-exception;
        r2 = r6.GetTimer();
        if (r2 > 0) goto L_0x0023;
    L_0x0066:
        r2 = com.waze.NativeManager.getInstance();
        r3 = com.waze.PopupAction.popup_shown;
        r3 = r3.ordinal();
        r2.PopupAction(r3, r5, r5);
        goto L_0x0023;
    L_0x0074:
        r2 = move-exception;
        r3 = r6.GetTimer();
        if (r3 > 0) goto L_0x0088;
    L_0x007b:
        r3 = com.waze.NativeManager.getInstance();
        r4 = com.waze.PopupAction.popup_shown;
        r4 = r4.ordinal();
        r3.PopupAction(r4, r5, r5);
    L_0x0088:
        throw r2;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.view.popups.PoiPopUp.processJsReturnValue(int, java.lang.String):void");
    }

    public PoiPopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mIsShown = false;
        this.mIsTakeOver = false;
        this.mVenueID = null;
        this.mAddressItem = null;
        this.mVenueContext = null;
        this.mProgressAnimation = null;
        this.mWebView = null;
        this.mPoiUrl = null;
        this.mInfoUrl = null;
        this.VOICE_ACTIONS_INDEX = 1;
        this.mPromotionUrl = null;
        this.mPreloadedUrl = null;
        this.mPreloadedVenueId = null;
        this.mIsNavigateable = true;
        this.mIsTemplatePreloaded = false;
        this.mIsInMiddleOfPreload = false;
        this.mIsPoiLoaded = false;
        this.mIsPoiLoadPending = false;
        this.mIsLoaded = false;
        this.mResults = null;
        this.mAnalyticsAdsNotifyClose = true;
        this.mPoiLoader = new C31861();
        this.mResults = new ArrayList();
        this.mLayoutManager = layoutManager;
        init();
    }

    public void onOrientationChanged() {
        removeAllViews();
        init();
        this.mIsPoiLoadPending = true;
        loadTemplate();
    }

    public boolean onBackPressed() {
        this.mLayoutManager.callCloseAllPopups(1, PopupCloseReason.popup_close_reason_user_close.ordinal());
        return true;
    }

    public void hide() {
        this.mIsShown = false;
        this.mAddressItem = null;
        this.mIsTemplatePreloaded = false;
        this.mIsPoiLoaded = false;
        this.mPreloadedUrl = null;
        this.mPreloadedVenueId = null;
        this.mPoiUrl = null;
        this.mIsPoiLoadPending = false;
        this.mPoiUrl = null;
        this.mLayoutManager.dismiss(this);
        removeAllViews();
        NativeManager.Post(new C31872());
        CarpoolNativeManager.getInstance().openCarpoolTakeoverIfNecessary();
    }

    public void onPreviewActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.d("onPreviewActivityResult. Result: " + resultCode);
        if (resultCode == -1) {
            this.mLayoutManager.callCloseAllPopups(1);
            Analytics.adsContextClear();
        }
        if (resultCode != 0) {
        }
    }

    public int GetHeight() {
        return this.mWebView.getHeight();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.popup_poi, this);
        this.mProgressAnimation = (ProgressAnimation) findViewById(C1283R.id.progressAnimation1);
        initWebView();
    }

    private void hideDlg() {
        AnimationUtils.closeAnimateToPoint(this, (float) this.mX, (float) this.mY, 300, new C31893());
    }

    private void onNavigateButton() {
        this.mAnalyticsAdsNotifyClose = true;
        final NativeManager nm = NativeManager.getInstance();
        final DriveToNativeManager dtnm = DriveToNativeManager.getInstance();
        NavigateNativeManager.instance().stopNavigationReason(NavigateStopReason.NAV_END_REASON_NEW_DEST);
        Analytics.logAdsContext(AnalyticsEvents.ANALYTICS_EVENT_ADS_POPUP_NAVIGATE);
        Analytics.adsContextNavigationInit();
        nm.OpenProgressPopup(nm.getLanguageString(290));
        this.mVenueGetHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
                    NativeManager.getInstance().CloseProgressPopup();
                    PoiPopUp.this.mVenueGetHandler.removeCallbacks(PoiPopUp.this.mVenuGetFailRunnable);
                    AddressItem ai = (AddressItem) msg.getData().getSerializable("address_item");
                    dtnm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, PoiPopUp.this.mVenueGetHandler);
                    if (ai.hasLocation()) {
                        PoiPopUp.this.mVenuGetFailRunnable = null;
                        PoiPopUp.this.mLayoutManager.callCloseAllPopups(1, PopupCloseReason.popup_close_reason_user_click.ordinal());
                        dtnm.navigate(ai, PoiPopUp.this, false, true);
                    } else if (PoiPopUp.this.mVenuGetFailRunnable != null) {
                        PoiPopUp.this.mVenuGetFailRunnable.run();
                        PoiPopUp.this.mVenuGetFailRunnable = null;
                    }
                }
            }
        };
        dtnm.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mVenueGetHandler);
        nm.AutoCompletePlaceClicked(null, this.mVenueID, null, null, this.mVenueContext, false, null, false, 0, null, null);
        this.mVenuGetFailRunnable = new Runnable() {

            class C31911 implements Runnable {
                C31911() {
                }

                public void run() {
                    AppService.getNativeManager().navigateToExternalPoiNTV(PoiPopUp.mLat, PoiPopUp.mLon, PoiPopUp.this.mServerId, PoiPopUp.this.mVenueID, PoiPopUp.this.mVenueContext);
                }
            }

            public void run() {
                dtnm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, PoiPopUp.this.mVenueGetHandler);
                nm.CloseProgressPopup();
                NativeManager.Post(new C31911());
                PoiPopUp.this.mLayoutManager.callCloseAllPopups(1);
            }
        };
        this.mVenueGetHandler.postDelayed(this.mVenuGetFailRunnable, (long) nm.getVenueGetTimeout());
    }

    private void onPromotionButton() {
        this.mAnalyticsAdsNotifyClose = false;
        new VenueData().context = this.mVenueContext;
        if (AppService.getMainActivity() != null) {
            Intent intent = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, this.mAddressItem);
            intent.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
            intent.putExtra(PublicMacros.CLEAR_ADS_CONTEXT, false);
            AppService.getActiveActivity().startActivityForResult(intent, MainActivity.POI_POPUP_INFO_REQUEST_CODE);
        }
    }

    private void stopCloseTimer() {
    }

    public void setCloseTime(int timer) {
    }

    public void SetAction(String Action) {
        this.mWebView.loadUrl("javascript:window.W.triggerVoiceAction(\"" + Action + "\")");
    }

    public void prepare(int iID, String templateUrl) {
        Logger.d("PoiPopUp.prepare() iID=" + iID + "; templateUrl=" + templateUrl);
        this.mAddressItem = null;
        Log.d(Logger.TAG, String.format("external_poi_preload:: prepare loaging iID: %d", new Object[]{Integer.valueOf(iID)}));
        this.mPreloadedUrl = templateUrl;
        this.mIsPoiLoaded = false;
        this.mId = iID;
        loadTemplate();
    }

    private void loadTemplate() {
        this.mIsTemplatePreloaded = false;
        this.mIsLoaded = false;
        this.mWebView.setBackgroundColor(0);
        this.mWebView.loadUrl(this.mPreloadedUrl);
    }

    public boolean isPreloaded(int iID) {
        boolean preloaded = this.mId == iID && this.mIsLoaded && this.mIsPoiLoaded;
        Logger.d("PoiPopUp.isPreloaded; isPreloaded=" + preloaded + "; mIsLoaded=" + this.mIsLoaded + "; mIsPoiLoaded=" + this.mIsPoiLoaded + "; mId=" + this.mId + "; iID=" + iID);
        return preloaded;
    }

    public void enterAddressCandidateToPoi(String venueId, String venueDataContext, String source, String advertiserWebData, AddressItem ai) {
        Exception e;
        Logger.d("PoiPopUp.enterAddressCandidateToPoi; venueId=" + venueId);
        this.mVenueID = venueId;
        this.mAddressItem = ai;
        this.mVenueContext = venueDataContext;
        int lon = 0;
        int lat = 0;
        Location loc = LocationFactory.getInstance().getLastLocation();
        if (loc != null) {
            NativeLocation nLoc = LocationFactory.getNativeLocation(loc);
            lon = nLoc.mLongtitude;
            lat = nLoc.mLatitude;
        }
        String localeStr = new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV()).toString();
        NativeManager mNatMgr = NativeManager.getInstance();
        String params = "";
        try {
            JSONObject jsonParams = new JSONObject();
            try {
                jsonParams.put("sessionid", mNatMgr.getServerSessionId());
                String serverCookie = mNatMgr.getServerCookie();
                if (!(serverCookie == null || serverCookie.isEmpty())) {
                    jsonParams.put("cookie", serverCookie);
                }
                jsonParams.put("rtserver-id", mNatMgr.getRTServerId());
                jsonParams.put("lon", lon);
                jsonParams.put("lat", lat);
                jsonParams.put("locale", localeStr);
                jsonParams.put("venue_context", venueDataContext);
                jsonParams.put("client_version", mNatMgr.getCoreVersion());
                jsonParams.put("source", source);
                if (!(this.mAddressItem == null || TextUtils.isEmpty(this.mAddressItem.brandId))) {
                    jsonParams.put("opted_id", MyWazeNativeManager.getInstance().isBrandOptedIn(this.mAddressItem.brandId));
                }
                params = jsonParams.toString();
                this.mPoiUrl = String.format("javascript:W.setOffer(%s, %s)", new Object[]{advertiserWebData, params});
                if (this.mIsTemplatePreloaded) {
                    this.mPoiLoader.run();
                } else {
                    this.mIsPoiLoadPending = true;
                }
                Logger.d("PoiPopUp.enterAddressCandidateToPoi; venueId=" + venueId + "; to run=" + this.mPoiUrl + "; mIsTemplatePreloaded = " + this.mIsTemplatePreloaded);
            } catch (Exception e2) {
                e = e2;
                JSONObject jSONObject = jsonParams;
                Logger.e("PoiPopUp:Exception pccurred while trying to create json", e);
            }
        } catch (Exception e3) {
            e = e3;
            Logger.e("PoiPopUp:Exception pccurred while trying to create json", e);
        }
    }

    public void callJavascriptUpdateClient() {
        if (this.mAddressItem != null && !TextUtils.isEmpty(this.mAddressItem.brandId)) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("opted_in", MyWazeNativeManager.getInstance().isBrandOptedIn(this.mAddressItem.brandId));
                this.mWebView.loadUrl("javascript:if(W.updateClientEnv) W.updateClientEnv(" + jsonObject.toString() + ");");
            } catch (Exception e) {
                Logger.e("PoiPopUp:Exception pccurred while trying to create json", e);
            }
        }
    }

    public boolean isPoiTemplateLoaded() {
        Logger.d("PoiPopUp.isPoiTemplateLoaded; mIsTemplatePreloaded=" + this.mIsTemplatePreloaded);
        return this.mIsTemplatePreloaded;
    }

    public void navigateCallback(int rc) {
        if (rc == 0) {
            Analytics.adsContextNavigationInit();
        }
    }

    public boolean isPoiShowing() {
        return this.mIsShown;
    }

    public PopUp GetView(int iID, int x, int y, int lat, int lon, int seconds, boolean isNavigable, String Address, int Height, int ServerId, String VenueId, String VenueContext) {
        if (this.mIsShown) {
            this.mLayoutManager.callCloseAllPopups(0);
        }
        this.mIsShown = true;
        this.mResults.clear();
        this.mId = iID;
        this.mX = x;
        this.mY = y;
        mLat = lat;
        mLon = lon;
        this.mServerId = ServerId;
        this.mAnalyticsAdsNotifyClose = true;
        this.mVenueContext = VenueContext;
        this.mVenueID = VenueId;
        if (seconds > 0) {
            this.mIsTakeOver = true;
            setCloseTime(seconds);
        } else {
            this.mIsTakeOver = false;
            this.mIsLoaded = false;
        }
        Logger.d("PoiPopUp.GetView() VenueId=" + VenueId + "; mIsTemplatePreloaded=" + this.mIsTemplatePreloaded + "; mIsPoiLoaded=" + this.mIsPoiLoaded + "; mIsLoaded=" + this.mIsLoaded);
        this.mWebView.setVisibility(0);
        return this;
    }

    private void initWebView() {
        this.mWebView = (WebView) findViewById(C1283R.id.popupPoiWeb);
        this.mWebView.setWebViewClient(new PoiBgWebClient());
        this.mWebView.setWebChromeClient(new C31936());
        this.mWebView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.setOnTouchListener(new C31947());
        if (VERSION.SDK_INT > 13) {
            CompatabilityWrapper.setLayerType(this.mWebView);
        }
    }

    public Rect getRect() {
        Rect rect = new Rect();
        this.mWebView.getHitRect(rect);
        int[] loc = new int[2];
        ((View) this.mWebView.getParent()).getLocationInWindow(loc);
        rect.right += loc[0];
        rect.left += loc[0];
        rect.top += loc[1];
        rect.bottom += loc[1];
        return rect;
    }
}
