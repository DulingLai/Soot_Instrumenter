package com.waze.menus;

import android.location.Location;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import com.waze.ConfigManager;
import com.waze.LocationFactory;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.PlaceData;
import com.waze.autocomplete.PlacesAutoCompleteAdapter;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.SettingsNativeManager;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class AutoCompleteRequest extends AsyncTask<Void, Void, List<PlaceData>> {
    public static final int AUTOCOMPLETE_EXT_FEATURE_SHOW_EMPTY = 4096;
    public static final int AUTOCOMPLETE_FEATURE_ADS = 32;
    public static final int AUTOCOMPLETE_FEATURE_CONTACTS = 16;
    public static final int AUTOCOMPLETE_FEATURE_FAVORITES = 4;
    public static final int AUTOCOMPLETE_FEATURE_HISTORY = 8;
    public static final int AUTOCOMPLETE_FEATURE_PLACE = 2;
    public static final int AUTOCOMPLETE_FEATURE_QUERY = 1;
    public static final int AUTOCOMPLETE_FEATURE_WAZE = 64;
    private static final int MAX_FAVORITE_ITEMS = 2;
    private static final int MAX_HISTORY_ITEMS = 2;
    private static final String OUT_JSON = "/json";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/queryautocomplete";
    private int errorCode;
    private int mFeatures = NativeManager.getInstance().getAutoCompleteFeatures();
    private AddressItem[] mLocalHistory;
    private List<PlaceData> mResult = new ArrayList();
    private AutoCompleteResultHandler mResultHandler;
    private String mSearchTerm;

    public interface AutoCompleteResultHandler {
        void onAutoCompleteResult(@Signature({"(", "Lcom/waze/menus/AutoCompleteRequest;", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;I)V"}) AutoCompleteRequest autoCompleteRequest, @Signature({"(", "Lcom/waze/menus/AutoCompleteRequest;", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;I)V"}) List<PlaceData> list, @Signature({"(", "Lcom/waze/menus/AutoCompleteRequest;", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;I)V"}) int i) throws ;
    }

    private java.lang.String loadJsonResultsAsString(java.lang.String r20, boolean r21) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0029 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r19 = this;
        r0 = r20;
        r1 = android.text.TextUtils.isEmpty(r0);
        if (r1 == 0) goto L_0x000a;
    L_0x0008:
        r2 = 0;
        return r2;
    L_0x000a:
        r3 = 0;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = new java.net.URL;
        r0 = r20;	 Catch:{ MalformedURLException -> 0x00b3, IOException -> 0x0077 }
        r5.<init>(r0);	 Catch:{ MalformedURLException -> 0x00b3, IOException -> 0x0077 }
        r6 = r5.openConnection();	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r8 = r6;	 Catch:{ Throwable -> 0x00af }
        r8 = (java.net.HttpURLConnection) r8;	 Catch:{ Throwable -> 0x00af }
        r7 = r8;	 Catch:{ Throwable -> 0x00af }
        r3 = r7;
        if (r21 == 0) goto L_0x0029;	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
    L_0x0022:
        r9 = "User-Agent";	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r10 = "Mozilla/5.0";	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r7.setRequestProperty(r9, r10);	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
    L_0x0029:
        r11 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r12 = r7.getInputStream();	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r11.<init>(r12);	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r14 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r13 = new char[r14];	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
    L_0x0036:
        r15 = r11.read(r13);	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r14 = -1;	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        if (r15 == r14) goto L_0x006d;	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
    L_0x003d:
        r14 = 0;	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        r4.append(r13, r14, r15);	 Catch:{ MalformedURLException -> 0x0042, IOException -> 0x00b1 }
        goto L_0x0036;
    L_0x0042:
        r16 = move-exception;
    L_0x0043:
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00a8 }
        r4.<init>();	 Catch:{ Throwable -> 0x00a8 }
        r9 = "Malformed url exception: ";	 Catch:{ Throwable -> 0x00a8 }
        r4 = r4.append(r9);	 Catch:{ Throwable -> 0x00a8 }
        r0 = r16;	 Catch:{ Throwable -> 0x00a8 }
        r20 = r0.toString();	 Catch:{ Throwable -> 0x00a8 }
        r0 = r20;	 Catch:{ Throwable -> 0x00a8 }
        r4 = r4.append(r0);	 Catch:{ Throwable -> 0x00a8 }
        r20 = r4.toString();	 Catch:{ Throwable -> 0x00a8 }
        r9 = "AutoCompleteRequest";	 Catch:{ Throwable -> 0x00a8 }
        r0 = r20;	 Catch:{ Throwable -> 0x00a8 }
        android.util.Log.e(r9, r0);	 Catch:{ Throwable -> 0x00a8 }
        if (r3 == 0) goto L_0x00b5;
    L_0x0067:
        r3.disconnect();
        r9 = "-3";
        return r9;
    L_0x006d:
        if (r7 == 0) goto L_0x0072;
    L_0x006f:
        r7.disconnect();
    L_0x0072:
        r20 = r4.toString();
        return r20;
    L_0x0077:
        r17 = move-exception;
    L_0x0078:
        r4 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x00a8 }
        r4.<init>();	 Catch:{ Throwable -> 0x00a8 }
        r9 = "IO exception: ";	 Catch:{ Throwable -> 0x00a8 }
        r4 = r4.append(r9);	 Catch:{ Throwable -> 0x00a8 }
        r0 = r17;	 Catch:{ Throwable -> 0x00a8 }
        r20 = r0.toString();	 Catch:{ Throwable -> 0x00a8 }
        r0 = r20;	 Catch:{ Throwable -> 0x00a8 }
        r4 = r4.append(r0);	 Catch:{ Throwable -> 0x00a8 }
        r9 = " (probably due to request cancel)";	 Catch:{ Throwable -> 0x00a8 }
        r4 = r4.append(r9);	 Catch:{ Throwable -> 0x00a8 }
        r20 = r4.toString();	 Catch:{ Throwable -> 0x00a8 }
        r9 = "AutoCompleteRequest";	 Catch:{ Throwable -> 0x00a8 }
        r0 = r20;	 Catch:{ Throwable -> 0x00a8 }
        android.util.Log.w(r9, r0);	 Catch:{ Throwable -> 0x00a8 }
        if (r3 == 0) goto L_0x00b8;
    L_0x00a2:
        r3.disconnect();
        r9 = "-4";
        return r9;
    L_0x00a8:
        r18 = move-exception;
    L_0x00a9:
        if (r3 == 0) goto L_0x00ae;
    L_0x00ab:
        r3.disconnect();
    L_0x00ae:
        throw r18;
    L_0x00af:
        r18 = move-exception;
        goto L_0x00a9;
    L_0x00b1:
        r17 = move-exception;
        goto L_0x0078;
    L_0x00b3:
        r16 = move-exception;
        goto L_0x0043;
    L_0x00b5:
        r9 = "-3";
        return r9;
    L_0x00b8:
        r9 = "-4";
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.AutoCompleteRequest.loadJsonResultsAsString(java.lang.String, boolean):java.lang.String");
    }

    private void processGoogleAndWazeResponse(java.lang.String r13, java.lang.String r14) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x004d in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r12 = this;
        r0 = new org.json.JSONObject;
        r0.<init>(r13);	 Catch:{ JSONException -> 0x004e }
        r1 = android.text.TextUtils.isEmpty(r14);	 Catch:{ JSONException -> 0x0050 }
        if (r1 == 0) goto L_0x0042;
    L_0x000b:
        r2 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0050 }
        r2.<init>();	 Catch:{ JSONException -> 0x0050 }
    L_0x0010:
        r4 = "predictions";	 Catch:{ JSONException -> 0x0050 }
        r3 = r0.getJSONArray(r4);	 Catch:{ JSONException -> 0x0050 }
        r5 = r3.length();	 Catch:{ JSONException -> 0x0050 }
        r6 = r2.length();	 Catch:{ JSONException -> 0x0050 }
        r5 = java.lang.Math.max(r5, r6);	 Catch:{ JSONException -> 0x0050 }
        r6 = 0;
    L_0x0023:
        if (r6 >= r5) goto L_0x004d;	 Catch:{ JSONException -> 0x0050 }
    L_0x0025:
        r7 = r3.length();	 Catch:{ JSONException -> 0x0050 }
        if (r6 >= r7) goto L_0x0032;	 Catch:{ JSONException -> 0x0050 }
    L_0x002b:
        r0 = r3.getJSONObject(r6);	 Catch:{ JSONException -> 0x0050 }
        r12.processGoogleJsonObject(r0);	 Catch:{ JSONException -> 0x0050 }
    L_0x0032:
        r7 = r2.length();	 Catch:{ JSONException -> 0x0050 }
        if (r6 >= r7) goto L_0x003f;	 Catch:{ JSONException -> 0x0050 }
    L_0x0038:
        r8 = r2.getJSONArray(r6);	 Catch:{ JSONException -> 0x0050 }
        r12.addWazeJsonResult(r8);	 Catch:{ JSONException -> 0x0050 }
    L_0x003f:
        r6 = r6 + 1;
        goto L_0x0023;
    L_0x0042:
        r2 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x0050 }
        r2.<init>(r14);	 Catch:{ JSONException -> 0x0050 }
        r9 = 1;	 Catch:{ JSONException -> 0x0050 }
        r2 = r2.getJSONArray(r9);	 Catch:{ JSONException -> 0x0050 }
        goto L_0x0010;
    L_0x004d:
        return;
    L_0x004e:
        r10 = move-exception;
        return;
    L_0x0050:
        r11 = move-exception;
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.AutoCompleteRequest.processGoogleAndWazeResponse(java.lang.String, java.lang.String):void");
    }

    public AutoCompleteRequest(String $r1, AddressItem[] $r2, AutoCompleteResultHandler $r3) throws  {
        this.mSearchTerm = $r1;
        this.mLocalHistory = $r2;
        this.mResultHandler = $r3;
    }

    public String getSearchTerm() throws  {
        return this.mSearchTerm;
    }

    protected List<PlaceData> doInBackground(@Signature({"([", "Ljava/lang/Void;", ")", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;"}) Void... params) throws  {
        String $r3;
        processContactMatches();
        processLocalHistoryMatches();
        if (ConfigManager.getInstance().getConfigValueBool(290)) {
            $r3 = getWazeUrlString().replace("gxy=0", "gxy=1");
        } else {
            $r3 = getWazeUrlString();
        }
        long $l0 = System.currentTimeMillis();
        $l0 = System.currentTimeMillis() - $l0;
        int $i2 = processWazeResponse(loadJsonResultsAsString($r3, true));
        this.errorCode = 0;
        if ($i2 < 0) {
            this.errorCode = $i2;
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_REQUEST).addParam("QUERY", this.mSearchTerm).addParam("LATENCY_MS", $l0).send();
        addMoreResult();
        return this.mResult;
    }

    protected void onPostExecute(@Signature({"(", "Ljava/util/List", "<", "Lcom/waze/autocomplete/PlaceData;", ">;)V"}) List<PlaceData> $r1) throws  {
        if (this.mResultHandler != null) {
            this.mResultHandler.onAutoCompleteResult(this, $r1, this.errorCode);
        }
    }

    private String getAdsQuery(PlaceData $r1) throws JSONException {
        NativeManager $r3 = NativeManager.getInstance();
        SettingsNativeManager $r4 = SettingsNativeManager.getInstance();
        JSONObject $r2 = new JSONObject();
        $r2.put("cookie", $r3.getServerCookie());
        $r2.put("sessionid", $r3.getServerSessionId());
        float $f0 = 0.0f;
        float $f1 = 0.0f;
        Location $r7 = LocationFactory.getInstance().getLastLocation();
        if ($r7 != null) {
            NativeLocation $r8 = LocationFactory.getNativeLocation($r7);
            $f0 = ((float) $r8.mLongtitude) * 1.0E-6f;
            $f1 = ((float) $r8.mLatitude) * 1.0E-6f;
        }
        $r2.put("lon", (double) $f0);
        $r2.put("lat", (double) $f1);
        $r2.put("locale", $r4.getLanguagesLocaleNTV());
        String $r5 = $r3.getRTServerId();
        if ($r5 != null) {
            $r2.put("rtserver-id", $r5);
        }
        $r2.put("venue_context", $r1.mVenueContext);
        $r2.put("client_version", $r3.getCoreVersion());
        $r2.put("distance_units", $r3.getTripUnits());
        $r2.put("distance_units_trans", $r3.getLanguageString($r3.getTripUnits()));
        return String.format("window.W.setOffer(%s, %s)", new Object[]{$r1.mAdsAdvilJson, $r2.toString()});
    }

    private void processAdResult(JSONArray $r1) throws JSONException {
        DriveToNativeManager $r2 = DriveToNativeManager.getInstance();
        PlaceData $r3 = null;
        if ($r2.isAutocompleteServerAds()) {
            JSONObject $r4 = $r1.getJSONObject(2).getJSONObject("a");
            if ($r4 != null) {
                $r3 = new PlaceData();
                $r3.mIsAds = true;
                $r3.mAdsIsServer = true;
                $r3.mVenueId = $r4.getString("venue_id");
                $r3.mVenueContext = $r4.getString("venue_context");
                $r3.mAdsAdvilJson = $r4.getString("advil_json");
                $r3.mAdsUrl = getAdsQuery($r3);
            }
        } else {
            $r3 = $r2.getAutoCompleteAdsResultNative(this.mSearchTerm);
        }
        if ($r3 != null) {
            this.mResult.add(0, $r3);
        }
    }

    private void addMoreResult() throws  {
        PlaceData $r1 = new PlaceData();
        $r1.mIsAds = false;
        $r1.mLocalIndex = -3;
        $r1.mTitle = NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MORE_RESULT_FOR) + " " + this.mSearchTerm;
        $r1.mReference = null;
        $r1.mSecondaryTitle = null;
        $r1.mVenueId = null;
        $r1.mSearchTerm = this.mSearchTerm;
        this.mResult.add($r1);
    }

    private void processLocalHistoryMatches() throws  {
        AddressItem[] $r1 = this.mLocalHistory;
        this = this;
        if ($r1 != null && !TextUtils.isEmpty(this.mSearchTerm)) {
            AddressItem[] $r12;
            PlaceData $r4;
            boolean $z0;
            int $i0 = 0;
            int $i1 = 0;
            while (true) {
                $r12 = this;
                this = $r12;
                if ($i1 >= $r12.mLocalHistory.length) {
                    break;
                }
                $r12 = this;
                this = $r12;
                if (matches($r12.mLocalHistory[$i1])) {
                    $r12 = this;
                    this = $r12;
                    if ($r12.mLocalHistory[$i1].getType() == 5) {
                        $r4 = getPlaceData($i1);
                        $z0 = false;
                        for (PlaceData isDuplicate : this.mResult) {
                            if (isDuplicate(isDuplicate, $r4)) {
                                $z0 = true;
                                break;
                            }
                        }
                        if ($z0) {
                            continue;
                        } else {
                            this.mResult.add($r4);
                            $i0++;
                            if ($i0 >= 2) {
                                break;
                            }
                        }
                    } else {
                        continue;
                    }
                }
                $i1++;
            }
            $i0 = 0;
            $i1 = 0;
            while (true) {
                $r12 = this;
                this = $r12;
                if ($i1 < $r12.mLocalHistory.length) {
                    $r12 = this;
                    this = $r12;
                    if (matches($r12.mLocalHistory[$i1])) {
                        $r12 = this;
                        this = $r12;
                        if ($r12.mLocalHistory[$i1].getType() == 8) {
                            $r4 = getPlaceData($i1);
                            $z0 = false;
                            for (PlaceData isDuplicate2 : this.mResult) {
                                if (isDuplicate(isDuplicate2, $r4)) {
                                    $z0 = true;
                                    break;
                                }
                            }
                            if ($z0) {
                                continue;
                            } else {
                                this.mResult.add($r4);
                                $i0++;
                                if ($i0 >= 2) {
                                    return;
                                }
                            }
                        } else {
                            continue;
                        }
                    }
                    $i1++;
                } else {
                    return;
                }
            }
        }
    }

    private boolean matches(AddressItem $r1) throws  {
        String[] $r4 = this.mSearchTerm.split("\\s+");
        ArrayList<String> $r2 = new ArrayList();
        String[] $r5 = $r1.getTitle().split("\\s+");
        String[] $r6 = $r1.getAddress().split("\\s+");
        String[] $r7 = $r1.getSecondaryTitle().split("\\s+");
        for (String $r3 : $r5) {
            $r2.add($r3.toLowerCase());
        }
        for (String $r32 : $r6) {
            $r2.add($r32.toLowerCase());
        }
        for (String $r322 : $r7) {
            $r2.add($r322.toLowerCase());
        }
        for (String $r3222 : $r4) {
            boolean $z0 = false;
            int $i2 = 0;
            int $i3 = 0;
            for (String $r10 : $r2) {
                if ($r10.startsWith($r3222.toLowerCase())) {
                    $z0 = true;
                    $i3 = $i2;
                    break;
                }
                $i2++;
            }
            if (!$z0) {
                return false;
            }
            $r2.remove($i3);
        }
        return true;
    }

    private PlaceData getPlaceData(int $i0) throws  {
        PlaceData $r1 = new PlaceData();
        $r1.mTitle = this.mLocalHistory[$i0].getTitle();
        boolean $z0 = false;
        if (TextUtils.isEmpty($r1.mTitle)) {
            $r1.mTitle = this.mLocalHistory[$i0].getAddress();
            $z0 = true;
        }
        $r1.mSecondaryTitle = this.mLocalHistory[$i0] != null ? this.mLocalHistory[$i0].getSecondaryTitle() : "";
        if (TextUtils.isEmpty($r1.mSecondaryTitle) && !$z0) {
            $r1.mSecondaryTitle = this.mLocalHistory[$i0].getAddress();
        }
        $r1.mLocalIndex = $i0;
        $r1.mLocX = this.mLocalHistory[$i0].getLocationX().intValue();
        $r1.mLocY = this.mLocalHistory[$i0].getLocationY().intValue();
        return $r1;
    }

    private void processContactMatches() throws  {
        AddressItem[] $r4 = PlacesAutoCompleteAdapter.searchContacts(this.mSearchTerm);
        if ($r4 != null) {
            for (AddressItem $r1 : $r4) {
                PlaceData $r2 = new PlaceData();
                $r2.mTitle = $r1.getTitle();
                $r2.mLocX = $r1.getLocationX().intValue();
                $r2.mLocY = $r1.getLocationY().intValue();
                $r2.mLocalIndex = -5;
                $r2.mSearchTerm = $r1.getAddress();
                $r2.mSecondaryTitle = $r1.getAddress();
                if (!($r2.mSearchTerm == null || $r2.mSearchTerm.isEmpty())) {
                    this.mResult.add($r2);
                }
            }
        }
    }

    private int processWazeResponse(String $r1) throws  {
        if ($r1.equals("-3")) {
            return -3;
        }
        if ($r1.equals("-4")) {
            return -4;
        }
        JSONArray $r3;
        int $i0 = -2;
        if (TextUtils.isEmpty($r1)) {
            $r3 = new JSONArray();
            $i0 = -1;
        } else {
            $r3 = new JSONArray($r1);
        }
        try {
            JSONArray $r4 = $r3.getJSONArray(1);
            int $i1 = $r4.length();
            $i0 = $i1;
            for (int $i2 = 0; $i2 < $r4.length(); $i2++) {
                if ($i2 < $r4.length()) {
                    addWazeJsonResult($r4.getJSONArray($i2));
                }
            }
            processAdResult($r3);
            return $i1;
        } catch (JSONException $r2) {
            Log.e("AutoCompleteRequest", "Error processing waze result: " + $r2.toString());
            return $i0;
        }
    }

    private void processGoogleJsonObject(JSONObject $r1) throws JSONException {
        if ($r1 != null) {
            PlaceData $r2 = new PlaceData();
            $r2.mVenueId = $r1.optString("id");
            if ($r2.mVenueId == null || $r2.mVenueId.equals("")) {
                if ((this.mFeatures & 1) == 0) {
                    return;
                }
            } else if ((this.mFeatures & 2) == 0) {
                return;
            }
            $r2.mResponse = $r1.toString();
            JSONArray $r5 = $r1.getJSONArray("terms");
            $r2.mTitle = $r5.getJSONObject(0).getString("value");
            StringBuilder $r3 = new StringBuilder();
            for (int $i0 = 1; $i0 < $r5.length(); $i0++) {
                $r3.append($r5.getJSONObject($i0).getString("value"));
                $r3.append(" ");
            }
            $r2.mSecondaryTitle = $r3.toString();
            $r2.mfeature = 2;
            $r2.mReference = $r1.optString("reference");
            $r2.mLocalIndex = -1;
            this.mResult.add($r2);
        }
    }

    private void addWazeJsonResult(JSONArray $r1) throws JSONException {
        PlaceData $r2 = processWazeJsonObject($r1);
        if ($r2 != null) {
            boolean $z0 = false;
            for (PlaceData isDuplicate : this.mResult) {
                if (isDuplicate(isDuplicate, $r2)) {
                    $z0 = true;
                    break;
                }
            }
            if (!$z0) {
                this.mResult.add($r2);
            }
        }
    }

    private boolean isDuplicate(PlaceData $r1, PlaceData $r2) throws  {
        if ($r1 == null && $r2 == null) {
            return true;
        }
        if ($r1 == null || $r2 == null) {
            return false;
        }
        return isStringEqual($r1.mTitle, $r2.mTitle) && isStringEqual($r1.mSecondaryTitle, $r2.mSecondaryTitle);
    }

    private boolean isStringEqual(String $r1, String $r2) throws  {
        if ($r1 == null && $r2 == null) {
            return true;
        }
        return ($r1 == null || $r2 == null) ? false : $r1.equalsIgnoreCase($r2);
    }

    private PlaceData processWazeJsonObject(JSONArray $r1) throws JSONException {
        if ($r1 == null) {
            return null;
        }
        byte $b1;
        PlaceData $r2 = new PlaceData();
        $r2.mResponse = $r1.toString();
        $r2.mTitle = $r1.getString(0).replace("\f", "").replace("\u0007", "");
        $r2.mReference = null;
        JSONObject $r4 = $r1.getJSONObject(3);
        if (!$r4.isNull("a")) {
            $r2.mSecondaryTitle = $r4.getString("a");
        }
        if (!$r4.isNull("x")) {
            $r2.mLocX = (int) ($r4.getDouble("x") * 1000000.0d);
        }
        if (!$r4.isNull("y")) {
            $r2.mLocY = (int) ($r4.getDouble("y") * 1000000.0d);
        }
        if (!$r4.isNull("v")) {
            $r2.mVenueId = $r4.getString("v");
        }
        if ($r4.isNull("c")) {
            $b1 = (byte) -1;
        } else {
            $b1 = (byte) -3;
        }
        $r2.mLocalIndex = $b1;
        if ($r2.mLocalIndex != -3) {
            return $r2;
        }
        $r2.mSearchTerm = $r2.mTitle;
        return $r2;
    }

    private String getGoogleUrlString() throws  {
        StringBuilder $r1 = new StringBuilder("https://maps.googleapis.com/maps/api/place/queryautocomplete/json");
        Location $r4 = LocationFactory.getInstance().getLastLocation();
        $r1.append("?sensor=true&key=" + NativeManager.getInstance().getApiKey());
        if ($r4 != null) {
            $r1.append("&location=" + $r4.getLatitude() + "," + $r4.getLongitude());
        }
        $r1.append("&radius=2000");
        try {
            $r1.append("&input=" + URLEncoder.encode(this.mSearchTerm, "utf8"));
        } catch (UnsupportedEncodingException $r2) {
            Log.e("AutoCompleteRequest", "Unsupported encoding excaption getting google url string: " + $r2.toString());
        }
        return $r1.toString();
    }

    private String getWazeUrlString() throws  {
        try {
            return NativeManager.getInstance().GetWazeAutocompleteUrl(URLEncoder.encode(this.mSearchTerm, "utf8"));
        } catch (UnsupportedEncodingException $r1) {
            Log.e("AutoCompleteRequest", "Unsupported encoding excaption getting waze url string: " + $r1.toString());
            return null;
        }
    }
}
