package com.waze.autocomplete;

import android.annotation.SuppressLint;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.provider.ContactsContract.Data;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ImageView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.navigate.AddressItem;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import java.util.ArrayList;
import java.util.Arrays;

@SuppressLint({"SetJavaScriptEnabled"})
public class PlacesAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private static String API_KEY = null;
    public static final int AUTOCOMPLETE_EXT_FEATURE_SHOW_EMPTY = 4096;
    public static final int AUTOCOMPLETE_FEATURE_ADS = 32;
    public static final int AUTOCOMPLETE_FEATURE_CONTACTS = 16;
    public static final int AUTOCOMPLETE_FEATURE_FAVORITES = 4;
    public static final int AUTOCOMPLETE_FEATURE_HISTORY = 8;
    public static final int AUTOCOMPLETE_FEATURE_PLACE = 2;
    public static final int AUTOCOMPLETE_FEATURE_QUERY = 1;
    public static final int AUTOCOMPLETE_FEATURE_WAZE = 64;
    private static final String LOG_TAG = "Waze";
    private static final String OUT_JSON = "/json";
    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/queryautocomplete";
    private static View mWeb;
    private boolean bIsAutoCompleteShown = false;
    private PlaceData mAdsResult = null;
    private Context mContext;
    private int mFeatures;
    private IPrepareForSearchResults mIPrepareForSearchResults;
    public String mInput = null;
    private boolean mIsCalendar = false;
    private boolean mIsWait = false;
    private AddressItem[] mLocalData;
    private NativeManager mNatMgr = NativeManager.getInstance();
    private View mTextBox;
    private ArrayList<PlaceData> resultList = null;
    private boolean skipCategories = false;

    class C13581 implements OnAttachStateChangeListener {

        class C13571 implements Runnable {
            C13571() throws  {
            }

            public void run() throws  {
                PlacesAutoCompleteAdapter.mWeb.requestLayout();
            }
        }

        C13581() throws  {
        }

        public void onViewAttachedToWindow(View view) throws  {
            PlacesAutoCompleteAdapter.mWeb.postDelayed(new C13571(), 100);
        }

        public void onViewDetachedFromWindow(View view) throws  {
        }
    }

    class C13602 extends WebViewClient {

        class C13591 implements Runnable {
            C13591() throws  {
            }

            public void run() throws  {
                PlacesAutoCompleteAdapter.mWeb.requestLayout();
            }
        }

        public boolean shouldOverrideUrlLoading(WebView view, String url) throws  {
            return false;
        }

        C13602() throws  {
        }

        public void onPageFinished(WebView $r1, String $r2) throws  {
            super.onPageFinished($r1, $r2);
            PlacesAutoCompleteAdapter.mWeb.postDelayed(new C13591(), 100);
        }
    }

    class C13613 extends Filter {
        C13613() throws  {
        }

        protected FilterResults performFiltering(CharSequence $r3) throws  {
            FilterResults $r1 = new FilterResults();
            if ((PlacesAutoCompleteAdapter.this.mFeatures & 4096) != 0 && $r3 == null) {
                $r3 = "";
            }
            if ($r3 == null) {
                return $r1;
            }
            PlacesAutoCompleteAdapter.this.mInput = $r3.toString();
            ArrayList $r6 = PlacesAutoCompleteAdapter.this.autocomplete($r3.toString());
            if (PlacesAutoCompleteAdapter.this.mAdsResult != null) {
                $r6.add(0, PlacesAutoCompleteAdapter.this.mAdsResult);
            }
            if (PlacesAutoCompleteAdapter.this.mIsCalendar) {
                PlaceData $r2 = new PlaceData();
                $r2.mIsAds = false;
                $r2.mLocalIndex = -4;
                $r2.mTitle = PlacesAutoCompleteAdapter.this.mNatMgr.getLanguageString((int) DisplayStrings.DS_REMOVE_THIS_EVENT);
                $r2.mReference = null;
                $r2.mSecondaryTitle = null;
                $r2.mVenueId = null;
                $r6.add(0, $r2);
            }
            if (!PlacesAutoCompleteAdapter.this.bIsAutoCompleteShown) {
                PlacesAutoCompleteAdapter.this.bIsAutoCompleteShown = true;
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_SHOWN);
            }
            $r1.values = $r6;
            $r1.count = $r6.size();
            return $r1;
        }

        protected void publishResults(CharSequence constraint, FilterResults $r2) throws  {
            if ($r2 == null || $r2.count <= 0) {
                PlacesAutoCompleteAdapter.this.notifyDataSetInvalidated();
                PlacesAutoCompleteAdapter.this.resultList = null;
                return;
            }
            PlacesAutoCompleteAdapter.this.resultList = (ArrayList) $r2.values;
            PlacesAutoCompleteAdapter.this.notifyDataSetChanged();
        }
    }

    class C13624 implements OnTouchListener {
        C13624() throws  {
        }

        public boolean onTouch(View v, MotionEvent $r2) throws  {
            if ($r2.getAction() != 0) {
                return false;
            }
            ((InputMethodManager) PlacesAutoCompleteAdapter.this.mContext.getSystemService("input_method")).hideSoftInputFromWindow(PlacesAutoCompleteAdapter.this.mTextBox.getWindowToken(), 0);
            return false;
        }
    }

    class C13635 implements Runnable {
        C13635() throws  {
        }

        public void run() throws  {
            PlacesAutoCompleteAdapter.mWeb.requestLayout();
        }
    }

    class C13646 implements OnTouchListener {
        C13646() throws  {
        }

        public boolean onTouch(View $r1, MotionEvent $r2) throws  {
            switch ($r2.getAction()) {
                case 1:
                    PlaceData placeData = (PlaceData) $r1.getTag(C1283R.id.adsRow);
                    if (placeData != null) {
                        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_AUTOCOMPLETE_CLICK, "TYPE", AnalyticsEvents.ANALYTICS_AUTOCOMPLETE_ADV);
                        PlacesAutoCompleteAdapter $r5 = PlacesAutoCompleteAdapter.this;
                        $r5.mIPrepareForSearchResults.onPrepareForSearchResults();
                        $r5 = PlacesAutoCompleteAdapter.this;
                        NativeManager $r7 = $r5.mNatMgr;
                        String $r8 = placeData.mVenueId;
                        String $r9 = PlacesAutoCompleteAdapter.this;
                        String $r52 = $r9;
                        $r7.AutoCompleteAdsClicked($r8, $r9.mInput, 0);
                        $r5 = PlacesAutoCompleteAdapter.this;
                        $r7 = $r5.mNatMgr;
                        $r8 = placeData.mVenueId;
                        String $r92 = placeData.mReference;
                        $r9 = PlacesAutoCompleteAdapter.this;
                        $r52 = $r9;
                        $r7.AutoCompletePlaceClicked(null, $r8, $r92, null, null, false, $r9.mInput, false, 0, null, null);
                    }
                    return true;
                default:
                    return true;
            }
        }
    }

    class C13657 implements OnClickListener {
        C13657() throws  {
        }

        public void onClick(View $r1) throws  {
            String $r3 = $r1.getTag().toString();
            if ($r3 != null) {
                ((AutoCompleteTextView) PlacesAutoCompleteAdapter.this.mTextBox).setText($r3 + " ");
                ((AutoCompleteTextView) PlacesAutoCompleteAdapter.this.mTextBox).setSelection($r3.length() + 1);
            }
        }
    }

    public interface IPrepareForSearchResults {
        void onPrepareForSearchResults() throws ;
    }

    private static class ItemHolder2 {
        WebView AdsText;
        View root;

        private ItemHolder2() throws  {
        }
    }

    private static class ItemHolder {
        View CompleteTextImage;
        ImageView RowType;
        WazeTextView SecondaryTitle;
        WazeTextView Title;
        View root;

        private ItemHolder() throws  {
        }
    }

    final class MyWebChromeClient extends WebChromeClient {
        MyWebChromeClient() throws  {
        }

        public boolean onJsConfirm(WebView view, String url, String $r3, final JsResult $r4) throws  {
            new Builder(AppService.getActiveActivity(), C1283R.style.CustomPopup).setTitle("").setMessage($r3).setPositiveButton(17039370, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) throws  {
                    $r4.confirm();
                }
            }).setNegativeButton(17039360, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) throws  {
                    $r4.cancel();
                }
            }).create().show();
            return true;
        }
    }

    private java.util.ArrayList<com.waze.autocomplete.PlaceData> autocomplete(@dalvik.annotation.Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/ArrayList", "<", "Lcom/waze/autocomplete/PlaceData;", ">;"}) java.lang.String r57) throws  {
        /* JADX: method processing error */
/*
Error: java.lang.OutOfMemoryError: Java heap space
	at java.util.Arrays.copyOf(Arrays.java:3181)
	at java.util.ArrayList.grow(ArrayList.java:261)
	at java.util.ArrayList.ensureExplicitCapacity(ArrayList.java:235)
	at java.util.ArrayList.ensureCapacityInternal(ArrayList.java:227)
	at java.util.ArrayList.add(ArrayList.java:458)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:463)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
	at jadx.core.utils.BlockUtils.collectWhileDominates(BlockUtils.java:464)
*/
        /*
        r56 = this;
        r4 = 0;
        r5 = 0;
        r0 = r56;
        r0.mAdsResult = r5;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r0 = r56;
        r9 = r0.mLocalData;
        r10 = r9.length;
        r11 = new int[r10];
        r10 = 0;
        r9 = 0;
        r12 = new java.lang.StringBuilder;
        r12.<init>();
        r13 = new java.lang.StringBuilder;
        r13.<init>();
        r14 = com.waze.LocationFactory.getInstance();
        r15 = r14.getLastLocation();
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0.length();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r17 = 3;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r17;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r0 >= r1) goto L_0x003e;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0032:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mFeatures;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0 & 4096;
        r16 = r0;
        if (r16 == 0) goto L_0x0128;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x003e:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mFeatures;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r16 & 16;
        if (r16 <= 0) goto L_0x004e;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0048:
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r9 = searchContacts(r0);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x004e:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mFeatures;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r16 & 8;
        if (r16 <= 0) goto L_0x00dd;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0058:
        r16 = 0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x005a:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mLocalData;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r18 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.length;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r19 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r19;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r0 >= r1) goto L_0x00dd;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0069:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mLocalData;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r18 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r20 = r18[r16];	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r20;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.getTitle();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.toLowerCase();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r22 = r0.toLowerCase();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r22;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r23 = r0.contains(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r23 != 0) goto L_0x00d5;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x008d:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mLocalData;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r18 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r20 = r18[r16];	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r20;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.getAddress();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.toLowerCase();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r22 = r0.toLowerCase();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r22;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r23 = r0.contains(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r23 != 0) goto L_0x00d5;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x00b1:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mLocalData;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r18 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r20 = r18[r16];	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r20;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.getSecondaryTitle();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.toLowerCase();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r22 = r0.toLowerCase();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r22;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r23 = r0.contains(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r23 == 0) goto L_0x00d9;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x00d5:
        r11[r10] = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r10 = r10 + 1;
    L_0x00d9:
        r16 = r16 + 1;
        goto L_0x005a;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x00dd:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mFeatures;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r16 & 32;
        if (r16 <= 0) goto L_0x00f9;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x00e7:
        r24 = com.waze.navigate.DriveToNativeManager.getInstance();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r24;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r25 = r0.getAutoCompleteAdsResultNative(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r25;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1.mAdsResult = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x00f9:
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0.length();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r17 = 3;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r17;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r0 < r1) goto L_0x0128;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0107:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mFeatures;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r16 & 64;
        if (r16 <= 0) goto L_0x0128;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0111:
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mNatMgr;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r26 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = "utf8";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r6 = java.net.URLEncoder.encode(r0, r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r26;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r6 = r0.GetWazeAutocompleteUrl(r6);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0128:
        r28 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = "https://maps.googleapis.com/maps/api/place/queryautocomplete/json";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r28;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = "?sensor=true&key=";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = API_KEY;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.toString();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r28;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r15 == 0) goto L_0x019d;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x015d:
        r29 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = "&location=";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r30 = r15.getLatitude();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r30;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = ",";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r30 = r15.getLongitude();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r30;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r21 = r0.toString();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r28;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r21;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x019d:
        r27 = "&radius=2000";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r28;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = "&input=";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = "utf8";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r57 = java.net.URLEncoder.encode(r0, r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r29 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r29;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r57 = r0.toString();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r28;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.append(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r32 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r28;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r57 = r0.toString();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r32;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r57;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r32;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r33 = r0.openConnection();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r35 = r33;
        r35 = (java.net.HttpURLConnection) r35;
        r34 = r35;
        r7 = r34;
        r36 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r34;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r37 = r0.getInputStream();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r36;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r37;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r17 = 1024; // 0x400 float:1.435E-42 double:5.06E-321;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r17;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = new char[r0];	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r38 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x020b:
        r0 = r36;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r38;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0.read(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r17 = -1;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r17;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r0 == r1) goto L_0x024d;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x021b:
        r17 = 0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r38;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r17;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r2 = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r12.append(r0, r1, r2);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        goto L_0x020b;
    L_0x0227:
        r39 = move-exception;
        r27 = "Waze";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r40 = "Error processing Places API URL";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r40;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r2 = r39;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        android.util.Log.e(r0, r1, r2);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mFeatures;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0 & 4096;
        r16 = r0;
        if (r16 != 0) goto L_0x0396;
    L_0x0241:
        if (r7 == 0) goto L_0x0246;
    L_0x0243:
        r7.disconnect();
    L_0x0246:
        if (r8 == 0) goto L_0x024b;
    L_0x0248:
        r8.disconnect();
    L_0x024b:
        r5 = 0;
        return r5;
    L_0x024d:
        if (r6 == 0) goto L_0x02c8;
    L_0x024f:
        r23 = r6.isEmpty();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r23 != 0) goto L_0x02c8;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0255:
        r32 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r32;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>(r6);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r32;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r33 = r0.openConnection();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r42 = r33;
        r42 = (java.net.HttpURLConnection) r42;
        r41 = r42;
        r8 = r41;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r27 = "User-Agent";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r40 = "Mozilla/5.0";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r41;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r2 = r40;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.setRequestProperty(r1, r2);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r36 = new java.io.InputStreamReader;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r41;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r37 = r0.getInputStream();	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r36;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r37;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0.<init>(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0286:
        r0 = r36;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r38;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0.read(r1);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r17 = -1;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r17;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        if (r0 == r1) goto L_0x02c8;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
    L_0x0296:
        r17 = 0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r38;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r17;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r2 = r16;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r13.append(r0, r1, r2);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        goto L_0x0286;
    L_0x02a2:
        r43 = move-exception;
        r27 = "Waze";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r40 = "Error connecting to Places API";	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r27;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r1 = r40;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r2 = r43;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        android.util.Log.e(r0, r1, r2);	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r56;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0.mFeatures;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r16 = r0;	 Catch:{ MalformedURLException -> 0x0227, IOException -> 0x02a2, Throwable -> 0x03ac }
        r0 = r0 & 4096;
        r16 = r0;
        if (r16 != 0) goto L_0x03a1;
    L_0x02bc:
        if (r7 == 0) goto L_0x02c1;
    L_0x02be:
        r7.disconnect();
    L_0x02c1:
        if (r8 == 0) goto L_0x02c6;
    L_0x02c3:
        r8.disconnect();
    L_0x02c6:
        r5 = 0;
        return r5;
    L_0x02c8:
        if (r34 == 0) goto L_0x02cf;
    L_0x02ca:
        r0 = r34;
        r0.disconnect();
    L_0x02cf:
        if (r8 == 0) goto L_0x02d4;
    L_0x02d1:
        r8.disconnect();
    L_0x02d4:
        r44 = new org.json.JSONObject;
        r57 = r12.toString();	 Catch:{ JSONException -> 0x074c }
        r0 = r44;	 Catch:{ JSONException -> 0x074c }
        r1 = r57;	 Catch:{ JSONException -> 0x074c }
        r0.<init>(r1);	 Catch:{ JSONException -> 0x074c }
        r16 = r13.length();	 Catch:{ JSONException -> 0x074c }
        if (r16 <= 0) goto L_0x03b8;
    L_0x02e7:
        r45 = new org.json.JSONArray;	 Catch:{ JSONException -> 0x074c }
        r57 = r13.toString();	 Catch:{ JSONException -> 0x074c }
        r0 = r45;	 Catch:{ JSONException -> 0x074c }
        r1 = r57;	 Catch:{ JSONException -> 0x074c }
        r0.<init>(r1);	 Catch:{ JSONException -> 0x074c }
        r17 = 1;	 Catch:{ JSONException -> 0x074c }
        r0 = r45;	 Catch:{ JSONException -> 0x074c }
        r1 = r17;	 Catch:{ JSONException -> 0x074c }
        r45 = r0.getJSONArray(r1);	 Catch:{ JSONException -> 0x074c }
    L_0x02fe:
        r27 = "predictions";	 Catch:{ JSONException -> 0x074c }
        r0 = r44;	 Catch:{ JSONException -> 0x074c }
        r1 = r27;	 Catch:{ JSONException -> 0x074c }
        r46 = r0.getJSONArray(r1);	 Catch:{ JSONException -> 0x074c }
        if (r9 == 0) goto L_0x030a;
    L_0x030a:
        r47 = new java.util.ArrayList;	 Catch:{ JSONException -> 0x074c }
        r0 = r47;	 Catch:{ JSONException -> 0x074c }
        r0.<init>();	 Catch:{ JSONException -> 0x074c }
        if (r10 <= 0) goto L_0x03c0;
    L_0x0313:
        r16 = 0;
    L_0x0315:
        r0 = r16;
        if (r0 >= r10) goto L_0x03c0;
    L_0x0319:
        r25 = new com.waze.autocomplete.PlaceData;
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.<init>();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r56;
        r0 = r0.mLocalData;
        r18 = r0;
        r19 = r11[r16];
        r20 = r18[r19];	 Catch:{ JSONException -> 0x06c3 }
        r0 = r20;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getTitle();	 Catch:{ JSONException -> 0x06c3 }
        goto L_0x0334;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0331:
        goto L_0x02d4;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0334:
        r0 = r57;
        r1 = r25;
        r1.mTitle = r0;
        goto L_0x033e;
    L_0x033b:
        goto L_0x02d4;
    L_0x033e:
        r0 = r25;
        r0 = r0.mTitle;
        r57 = r0;
        if (r57 == 0) goto L_0x0358;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0346:
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r0.mTitle;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0;	 Catch:{ JSONException -> 0x06c3 }
        r27 = "";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r57;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.equals(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 == 0) goto L_0x0368;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0358:
        r0 = r20;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getAddress();	 Catch:{ JSONException -> 0x06c3 }
        goto L_0x0362;	 Catch:{ JSONException -> 0x06c3 }
    L_0x035f:
        goto L_0x02fe;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0362:
        r0 = r57;
        r1 = r25;
        r1.mTitle = r0;
    L_0x0368:
        r27 = "";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r1.mSecondaryTitle = r0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r20;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getSecondaryTitle();	 Catch:{ JSONException -> 0x06c3 }
        if (r57 == 0) goto L_0x0384;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0378:
        r0 = r20;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getSecondaryTitle();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r57;
        r1 = r25;
        r1.mSecondaryTitle = r0;
    L_0x0384:
        r19 = r11[r16];
        r0 = r19;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r1.mLocalIndex = r0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r47;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.add(r1);	 Catch:{ JSONException -> 0x06c3 }
        r16 = r16 + 1;
        goto L_0x0315;
    L_0x0396:
        if (r7 == 0) goto L_0x039b;
    L_0x0398:
        r7.disconnect();
    L_0x039b:
        if (r8 == 0) goto L_0x02d4;
    L_0x039d:
        r8.disconnect();
        goto L_0x0331;
    L_0x03a1:
        if (r7 == 0) goto L_0x03a6;
    L_0x03a3:
        r7.disconnect();
    L_0x03a6:
        if (r8 == 0) goto L_0x02d4;
    L_0x03a8:
        r8.disconnect();
        goto L_0x033b;
    L_0x03ac:
        r48 = move-exception;
        if (r7 == 0) goto L_0x03b2;
    L_0x03af:
        r7.disconnect();
    L_0x03b2:
        if (r8 == 0) goto L_0x03b7;
    L_0x03b4:
        r8.disconnect();
    L_0x03b7:
        throw r48;
    L_0x03b8:
        r45 = new org.json.JSONArray;
        r0 = r45;	 Catch:{ JSONException -> 0x074c }
        r0.<init>();	 Catch:{ JSONException -> 0x074c }
        goto L_0x035f;
    L_0x03c0:
        if (r9 == 0) goto L_0x040f;
    L_0x03c2:
        r10 = 0;
    L_0x03c3:
        r0 = r9.length;
        r16 = r0;
        if (r10 >= r0) goto L_0x040f;
    L_0x03c8:
        r25 = new com.waze.autocomplete.PlaceData;
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.<init>();	 Catch:{ JSONException -> 0x06c3 }
        r20 = r9[r10];	 Catch:{ JSONException -> 0x06c3 }
        r0 = r20;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getTitle();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r57;
        r1 = r25;
        r1.mTitle = r0;
        r27 = "";
        r0 = r27;
        r1 = r25;
        r1.mSecondaryTitle = r0;
        r20 = r9[r10];	 Catch:{ JSONException -> 0x06c3 }
        r0 = r20;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getAddress();	 Catch:{ JSONException -> 0x06c3 }
        if (r57 == 0) goto L_0x03fd;
    L_0x03ef:
        r20 = r9[r10];	 Catch:{ JSONException -> 0x06c3 }
        r0 = r20;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getAddress();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r57;
        r1 = r25;
        r1.mSecondaryTitle = r0;
    L_0x03fd:
        r17 = -2;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r17;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r1.mLocalIndex = r0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r47;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.add(r1);	 Catch:{ JSONException -> 0x06c3 }
        r10 = r10 + 1;
        goto L_0x03c3;
    L_0x040f:
        r0 = r46;	 Catch:{ JSONException -> 0x06c3 }
        r16 = r0.length();	 Catch:{ JSONException -> 0x06c3 }
        r10 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r45;	 Catch:{ JSONException -> 0x06c3 }
        r19 = r0.length();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r19;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        if (r0 <= r1) goto L_0x0429;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0423:
        r0 = r45;	 Catch:{ JSONException -> 0x06c3 }
        r10 = r0.length();	 Catch:{ JSONException -> 0x06c3 }
    L_0x0429:
        r16 = 0;
    L_0x042b:
        r0 = r16;
        if (r0 >= r10) goto L_0x06dd;
    L_0x042f:
        r25 = 0;
        r49 = 0;
        r6 = 0;
        r57 = 0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r46;	 Catch:{ JSONException -> 0x06c3 }
        r19 = r0.length();	 Catch:{ JSONException -> 0x06c3 }
        r19 = r19 + -1;
        r0 = r19;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        if (r0 < r1) goto L_0x0540;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0444:
        r25 = new com.waze.autocomplete.PlaceData;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.<init>();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r46;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r50 = r0.get(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r50;	 Catch:{ JSONException -> 0x06c3 }
        r6 = r0.toString();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.mResponse = r6;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r46;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r44 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "terms";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r51 = r0.getJSONArray(r1);	 Catch:{ JSONException -> 0x06c3 }
        r17 = 0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r51;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r17;	 Catch:{ JSONException -> 0x06c3 }
        r44 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "value";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r6 = r0.getString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r25;
        r0.mTitle = r6;
        r6 = "";
        r19 = 1;	 Catch:{ JSONException -> 0x06c3 }
    L_0x048d:
        r0 = r51;	 Catch:{ JSONException -> 0x06c3 }
        r52 = r0.length();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r19;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r52;	 Catch:{ JSONException -> 0x06c3 }
        if (r0 >= r1) goto L_0x04c8;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0499:
        r0 = r51;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r19;	 Catch:{ JSONException -> 0x06c3 }
        r44 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x06c3 }
        r12 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x06c3 }
        r12.<init>();	 Catch:{ JSONException -> 0x06c3 }
        r12 = r12.append(r6);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "value";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r6 = r0.getString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r12 = r12.append(r6);	 Catch:{ JSONException -> 0x06c3 }
        r27 = " ";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r12 = r12.append(r0);	 Catch:{ JSONException -> 0x06c3 }
        r6 = r12.toString();	 Catch:{ JSONException -> 0x06c3 }
        r19 = r19 + 1;
        goto L_0x048d;	 Catch:{ JSONException -> 0x06c3 }
    L_0x04c8:
        r17 = 2;
        r0 = r17;
        r1 = r25;
        r1.mfeature = r0;
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.mSecondaryTitle = r6;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r46;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r44 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "reference";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r6 = r0.optString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.mReference = r6;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r46;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r44 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "id";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r6 = r0.optString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.mVenueId = r6;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r46;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r44 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "place_id";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r6 = r0.optString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r17 = -1;
        r0 = r17;
        r1 = r25;
        r1.mLocalIndex = r0;
        r0 = r25;
        r0 = r0.mVenueId;
        r21 = r0;
        if (r21 == 0) goto L_0x0534;
    L_0x0522:
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r0.mVenueId;	 Catch:{ JSONException -> 0x06c3 }
        r21 = r0;	 Catch:{ JSONException -> 0x06c3 }
        r27 = "";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r21;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.equals(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 == 0) goto L_0x06a9;
    L_0x0534:
        r0 = r56;
        r0 = r0.mFeatures;
        r19 = r0;
        r19 = r19 & 1;
        if (r19 != 0) goto L_0x0540;	 Catch:{ JSONException -> 0x06c3 }
    L_0x053e:
        r25 = 0;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0540:
        r0 = r45;	 Catch:{ JSONException -> 0x06c3 }
        r19 = r0.length();	 Catch:{ JSONException -> 0x06c3 }
        r19 = r19 + -1;
        r0 = r19;
        r1 = r16;
        if (r0 < r1) goto L_0x0674;
    L_0x054e:
        r49 = new com.waze.autocomplete.PlaceData;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r0.<init>();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r45;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r51 = r0.getJSONArray(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r45;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r16;	 Catch:{ JSONException -> 0x06c3 }
        r50 = r0.get(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r50;	 Catch:{ JSONException -> 0x06c3 }
        r21 = r0.toString();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r21;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r1.mResponse = r0;	 Catch:{ JSONException -> 0x06c3 }
        r17 = 3;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r51;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r17;	 Catch:{ JSONException -> 0x06c3 }
        r44 = r0.getJSONObject(r1);	 Catch:{ JSONException -> 0x06c3 }
        r17 = 0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r51;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r17;	 Catch:{ JSONException -> 0x06c3 }
        r21 = r0.getString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "\f";	 Catch:{ JSONException -> 0x06c3 }
        r40 = "";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r21;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r2 = r40;	 Catch:{ JSONException -> 0x06c3 }
        r21 = r0.replace(r1, r2);	 Catch:{ JSONException -> 0x06c3 }
        r27 = "\u0007";	 Catch:{ JSONException -> 0x06c3 }
        r40 = "";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r21;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r2 = r40;	 Catch:{ JSONException -> 0x06c3 }
        r21 = r0.replace(r1, r2);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r21;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r1.mTitle = r0;	 Catch:{ JSONException -> 0x06c3 }
        r27 = "a";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.isNull(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 != 0) goto L_0x05c3;	 Catch:{ JSONException -> 0x06c3 }
    L_0x05b3:
        r27 = "a";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r21 = r0.getString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r21;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r1.mSecondaryTitle = r0;	 Catch:{ JSONException -> 0x06c3 }
    L_0x05c3:
        r27 = "g";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.isNull(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 != 0) goto L_0x05eb;	 Catch:{ JSONException -> 0x06c3 }
    L_0x05cf:
        r27 = "g";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r51 = r0.getJSONArray(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r51;	 Catch:{ JSONException -> 0x06c3 }
        r19 = r0.length();	 Catch:{ JSONException -> 0x06c3 }
        if (r19 <= 0) goto L_0x05eb;	 Catch:{ JSONException -> 0x06c3 }
    L_0x05e1:
        r17 = 0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r51;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r17;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getString(r1);	 Catch:{ JSONException -> 0x06c3 }
    L_0x05eb:
        r27 = "x";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.isNull(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 != 0) goto L_0x0613;	 Catch:{ JSONException -> 0x06c3 }
    L_0x05f8:
        r27 = "x";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r30 = r0.getDouble(r1);	 Catch:{ JSONException -> 0x06c3 }
        r53 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r30 = r53 * r30;
        r0 = r30;
        r0 = (int) r0;
        r19 = r0;
        r1 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r1.mLocX = r0;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0613:
        r27 = "y";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.isNull(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 != 0) goto L_0x063b;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0620:
        r27 = "y";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r30 = r0.getDouble(r1);	 Catch:{ JSONException -> 0x06c3 }
        r53 = 4696837146684686336; // 0x412e848000000000 float:0.0 double:1000000.0;
        r30 = r53 * r30;
        r0 = r30;
        r0 = (int) r0;
        r19 = r0;
        r1 = r49;
        r1.mLocY = r0;
    L_0x063b:
        r5 = 0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r0.mReference = r5;	 Catch:{ JSONException -> 0x06c3 }
        r27 = "v";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.isNull(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 != 0) goto L_0x065e;	 Catch:{ JSONException -> 0x06c3 }
    L_0x064d:
        r27 = "v";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r21 = r0.getString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r21;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r1.mVenueId = r0;	 Catch:{ JSONException -> 0x06c3 }
    L_0x065e:
        r27 = "c";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r44;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.isNull(r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 != 0) goto L_0x06d4;
    L_0x066a:
        r0 = r56;
        r0 = r0.skipCategories;
        r23 = r0;
        if (r23 == 0) goto L_0x06ba;
    L_0x0672:
        r49 = 0;
    L_0x0674:
        if (r6 == 0) goto L_0x0690;
    L_0x0676:
        if (r57 == 0) goto L_0x0690;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0678:
        r0 = r57;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.isEmpty();	 Catch:{ JSONException -> 0x06c3 }
        if (r23 != 0) goto L_0x0690;
    L_0x0680:
        r0 = r56;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r0.mNatMgr;	 Catch:{ JSONException -> 0x06c3 }
        r26 = r0;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r57;	 Catch:{ JSONException -> 0x06c3 }
        r23 = r0.AutocompleteIsMatchNTV(r6, r1);	 Catch:{ JSONException -> 0x06c3 }
        if (r23 == 0) goto L_0x0690;
    L_0x068e:
        r25 = 0;
    L_0x0690:
        if (r25 == 0) goto L_0x0699;	 Catch:{ JSONException -> 0x06c3 }
    L_0x0692:
        r0 = r47;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.add(r1);	 Catch:{ JSONException -> 0x06c3 }
    L_0x0699:
        if (r49 == 0) goto L_0x06a6;	 Catch:{ JSONException -> 0x06c3 }
    L_0x069b:
        r0 = r47;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r49;	 Catch:{ JSONException -> 0x06c3 }
        r0.add(r1);	 Catch:{ JSONException -> 0x06c3 }
        goto L_0x06a6;	 Catch:{ JSONException -> 0x06c3 }
    L_0x06a3:
        goto L_0x042b;	 Catch:{ JSONException -> 0x06c3 }
    L_0x06a6:
        r16 = r16 + 1;
        goto L_0x06a3;
    L_0x06a9:
        r0 = r56;
        r0 = r0.mFeatures;
        r19 = r0;
        r19 = r19 & 2;
        if (r19 != 0) goto L_0x0540;
    L_0x06b3:
        goto L_0x06b7;
    L_0x06b4:
        goto L_0x0540;
    L_0x06b7:
        r25 = 0;
        goto L_0x06b4;
    L_0x06ba:
        r17 = -3;
        r0 = r17;
        r1 = r49;
        r1.mLocalIndex = r0;
        goto L_0x0674;
    L_0x06c3:
        r55 = move-exception;
        r4 = r47;
    L_0x06c6:
        r27 = "Waze";
        r40 = "Cannot process JSON results";
        r0 = r27;
        r1 = r40;
        r2 = r55;
        android.util.Log.e(r0, r1, r2);
    L_0x06d3:
        return r4;
    L_0x06d4:
        r17 = -1;
        r0 = r17;
        r1 = r49;
        r1.mLocalIndex = r0;
        goto L_0x0674;
    L_0x06dd:
        r0 = r56;
        r10 = r0.mFeatures;
        r10 = r10 & 1;
        if (r10 <= 0) goto L_0x0749;
    L_0x06e5:
        r25 = new com.waze.autocomplete.PlaceData;
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.<init>();	 Catch:{ JSONException -> 0x06c3 }
        r17 = 0;
        r0 = r17;
        r1 = r25;
        r1.mIsAds = r0;
        r17 = -3;
        r0 = r17;
        r1 = r25;
        r1.mLocalIndex = r0;
        r12 = new java.lang.StringBuilder;	 Catch:{ JSONException -> 0x06c3 }
        r12.<init>();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r56;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r0.mNatMgr;	 Catch:{ JSONException -> 0x06c3 }
        r26 = r0;	 Catch:{ JSONException -> 0x06c3 }
        r17 = 1464; // 0x5b8 float:2.052E-42 double:7.233E-321;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r26;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r17;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0.getLanguageString(r1);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r57;	 Catch:{ JSONException -> 0x06c3 }
        r12 = r12.append(r0);	 Catch:{ JSONException -> 0x06c3 }
        r27 = " ";	 Catch:{ JSONException -> 0x06c3 }
        r0 = r27;	 Catch:{ JSONException -> 0x06c3 }
        r12 = r12.append(r0);	 Catch:{ JSONException -> 0x06c3 }
        r0 = r56;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r0.mInput;	 Catch:{ JSONException -> 0x06c3 }
        r57 = r0;	 Catch:{ JSONException -> 0x06c3 }
        r12 = r12.append(r0);	 Catch:{ JSONException -> 0x06c3 }
        r57 = r12.toString();	 Catch:{ JSONException -> 0x06c3 }
        r0 = r57;
        r1 = r25;
        r1.mTitle = r0;
        r5 = 0;
        r0 = r25;
        r0.mReference = r5;
        r5 = 0;
        r0 = r25;
        r0.mSecondaryTitle = r5;
        r5 = 0;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.mVenueId = r5;	 Catch:{ JSONException -> 0x06c3 }
        r0 = r47;	 Catch:{ JSONException -> 0x06c3 }
        r1 = r25;	 Catch:{ JSONException -> 0x06c3 }
        r0.add(r1);	 Catch:{ JSONException -> 0x06c3 }
    L_0x0749:
        r4 = r47;
        goto L_0x06d3;
    L_0x074c:
        r55 = move-exception;
        goto L_0x06c6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.autocomplete.PlacesAutoCompleteAdapter.autocomplete(java.lang.String):java.util.ArrayList<com.waze.autocomplete.PlaceData>");
    }

    public int getViewTypeCount() throws  {
        return 2;
    }

    public PlacesAutoCompleteAdapter(Context $r1, int $i0, AddressItem[] $r2, String $r3, View $r4, IPrepareForSearchResults $r5) throws  {
        super($r1, $i0);
        this.mContext = $r1;
        this.mIPrepareForSearchResults = $r5;
        this.mLocalData = $r2;
        API_KEY = $r3;
        this.mTextBox = $r4;
        this.mFeatures = this.mNatMgr.getAutoCompleteFeatures();
        mWeb = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(C1283R.layout.row_layout_ads, null);
        mWeb.addOnAttachStateChangeListener(new C13581());
        WebView $r10 = (WebView) mWeb.findViewById(C1283R.id.adsRow);
        $r10.setWebChromeClient(new MyWebChromeClient());
        $r10.setWebViewClient(new C13602());
        $r10.getSettings().setJavaScriptEnabled(true);
        $r10.loadUrl(this.mNatMgr.GetWazeAutocompleteAdsUrl());
    }

    public void setSkipCategories(boolean $z0) throws  {
        this.skipCategories = $z0;
    }

    public int getCount() throws  {
        return this.resultList == null ? 0 : this.resultList.size();
    }

    public int getFeatures() throws  {
        return this.mFeatures;
    }

    public void setFeatures(int $i0) throws  {
        this.mFeatures = $i0;
    }

    public void setIsCalendar(boolean $z0) throws  {
        this.mIsCalendar = $z0;
    }

    public PlaceData getItemByPosition(int $i0) throws  {
        return (PlaceData) this.resultList.get($i0);
    }

    public String getItem(int $i0) throws  {
        if (((PlaceData) this.resultList.get($i0)).mLocalIndex == -2) {
            return ((PlaceData) this.resultList.get($i0)).mSecondaryTitle;
        }
        if (((PlaceData) this.resultList.get($i0)).mLocalIndex == -4) {
            return this.mInput;
        }
        if (((PlaceData) this.resultList.get($i0)).mSecondaryTitle == null || ((PlaceData) this.resultList.get($i0)).mSecondaryTitle.equals("")) {
            return ((PlaceData) this.resultList.get($i0)).mTitle;
        }
        return this.mInput;
    }

    public Filter getFilter() throws  {
        return new C13613();
    }

    public int getItemViewType(int $i0) throws  {
        if (this.resultList.size() - 1 >= $i0) {
            return ((PlaceData) this.resultList.get($i0)).mIsAds ? 0 : 1;
        } else {
            return 1;
        }
    }

    public View getView(int $i0, View $r7, ViewGroup $r1) throws  {
        ItemHolder $r8 = null;
        ItemHolder2 $r9 = null;
        if ($r7 == null) {
            LayoutInflater $r12 = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
            if (((PlaceData) this.resultList.get($i0)).mIsAds) {
                $r7 = mWeb;
                $r9 = new ItemHolder2();
                $r9.AdsText = (WebView) $r7.findViewById(C1283R.id.adsRow);
                $r9.root = $r7.findViewById(C1283R.id.mainLayout);
                $r7.setTag(C1283R.layout.row_layout_ads, $r9);
            } else {
                View $r15 = $r12.inflate(C1283R.layout.row_layout, $r1, false);
                $r7 = $r15;
                $r8 = new ItemHolder();
                $r8.Title = (WazeTextView) $r15.findViewById(C1283R.id.autocompletetext);
                $r8.SecondaryTitle = (WazeTextView) $r15.findViewById(C1283R.id.autocompletetext2);
                $r8.CompleteTextImage = $r15.findViewById(C1283R.id.completeTextImage);
                $r8.RowType = (ImageView) $r15.findViewById(C1283R.id.ac_image);
                $r8.root = $r15.findViewById(C1283R.id.mainLayout);
                $r15.setTag(C1283R.layout.row_layout, $r8);
            }
        } else if ($i0 < this.resultList.size()) {
            if (((PlaceData) this.resultList.get($i0)).mIsAds) {
                $r9 = (ItemHolder2) $r7.getTag(C1283R.layout.row_layout_ads);
                if ($r9 == null) {
                    $r9 = new ItemHolder2();
                    $r9.AdsText = (WebView) $r7.findViewById(C1283R.id.adsRow);
                    $r9.root = $r7.findViewById(C1283R.id.mainLayout);
                    $r7.setTag(C1283R.layout.row_layout_ads, $r9);
                }
            } else {
                $r8 = (ItemHolder) $r7.getTag(C1283R.layout.row_layout);
            }
        }
        if ($i0 < this.resultList.size()) {
            $r1.setOnTouchListener(new C13624());
            if (((PlaceData) this.resultList.get($i0)).mIsAds) {
                if ($r9 == null) {
                    $r9 = new ItemHolder2();
                    $r9.AdsText = (WebView) mWeb.findViewById(C1283R.id.adsRow);
                    $r9.root = mWeb.findViewById(C1283R.id.mainLayout);
                    $r7.setTag(C1283R.layout.row_layout_ads, $r9);
                }
                WebView $r2 = $r9.AdsText;
                if ($r2 == null) {
                    return $r7;
                }
                $r2.loadUrl("javascript:window.W.adios.setQueryString(\"" + ((PlaceData) this.resultList.get($i0)).mAdsUrl + "\")");
                $r2.postDelayed(new C13635(), 100);
                $r9.AdsText.setTag(C1283R.id.adsRow, this.resultList.get($i0));
                $r9.AdsText.setOnTouchListener(new C13646());
                if (((PlaceData) this.resultList.get($i0)).mWasAdReported) {
                    return $r7;
                }
                ((PlaceData) this.resultList.get($i0)).mWasAdReported = true;
                this.mNatMgr.AutoCompleteAdsShown(((PlaceData) this.resultList.get($i0)).mVenueId, this.mInput, $i0);
                return $r7;
            }
            if ($r8 == null) {
                $r15 = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(C1283R.layout.row_layout, $r1, false);
                $r7 = $r15;
                $r8 = new ItemHolder();
                $r8.Title = (WazeTextView) $r15.findViewById(C1283R.id.autocompletetext);
                $r8.SecondaryTitle = (WazeTextView) $r15.findViewById(C1283R.id.autocompletetext2);
                $r8.CompleteTextImage = $r15.findViewById(C1283R.id.completeTextImage);
                $r8.RowType = (ImageView) $r15.findViewById(C1283R.id.ac_image);
                $r8.root = $r15.findViewById(C1283R.id.mainLayout);
                $r15.setTag(C1283R.layout.row_layout, $r8);
            }
            if (((PlaceData) this.resultList.get($i0)).mLocalIndex == -4) {
                $r8.CompleteTextImage.setVisibility(8);
            } else {
                $r8.CompleteTextImage.setVisibility(0);
                $r15 = $r8.CompleteTextImage;
                $r15.setTag(new String(((PlaceData) this.resultList.get($i0)).mTitle));
                $r8.CompleteTextImage.setOnClickListener(new C13657());
            }
            WazeTextView $r3 = $r8.Title;
            String $r5 = ((PlaceData) this.resultList.get($i0)).mTitle;
            WazeTextView $r4 = $r8.SecondaryTitle;
            $r8.root.setBackgroundColor(Color.rgb(221, 231, 234));
            if (((PlaceData) this.resultList.get($i0)).mLocalIndex == -1) {
                if (((PlaceData) this.resultList.get($i0)).mVenueId != null) {
                    if (!((PlaceData) this.resultList.get($i0)).mVenueId.equals("")) {
                        if (((PlaceData) this.resultList.get($i0)).mVenueId.contains("oogle")) {
                            $r8.RowType.setImageResource(C1283R.drawable.autocomplete_location);
                        } else {
                            $r8.RowType.setImageResource(C1283R.drawable.autocomplete_location_debug);
                        }
                    }
                }
                $r8.RowType.setImageResource(C1283R.drawable.list_icon_search);
            } else if (((PlaceData) this.resultList.get($i0)).mLocalIndex == -2) {
                $r8.RowType.setImageResource(C1283R.drawable.autocomplete_contact);
            } else if (((PlaceData) this.resultList.get($i0)).mLocalIndex == -3) {
                $r8.RowType.setImageResource(C1283R.drawable.list_icon_search);
            } else if (((PlaceData) this.resultList.get($i0)).mLocalIndex == -4) {
                $r8.RowType.setImageResource(C1283R.drawable.autocomplete_location);
                $r8.root.setBackgroundColor(Color.rgb(192, 216, 223));
            } else {
                int $i1 = this.mLocalData[((PlaceData) this.resultList.get($i0)).mLocalIndex].getType();
                if ($i1 == 5 || $i1 == 6) {
                    $r8.RowType.setImageResource(C1283R.drawable.autocomplete_favorites);
                } else if ($i1 == 8) {
                    $r8.RowType.setImageResource(C1283R.drawable.autocomplete_history);
                } else if ($i1 == 1) {
                    $r8.RowType.setImageResource(C1283R.drawable.autocomplete_home);
                } else if ($i1 == 3) {
                    $r8.RowType.setImageResource(C1283R.drawable.autocomplete_work);
                } else if ($i1 == 16) {
                    $r8.RowType.setImageResource(C1283R.drawable.autocomplete_location);
                } else {
                    $r8.RowType.setImageResource(C1283R.drawable.autocomplete_history);
                }
            }
            String $r6 = ((PlaceData) this.resultList.get($i0)).mSecondaryTitle;
            if ($r6 != null) {
                if (!$r6.equals("")) {
                    $r4.setVisibility(0);
                    $r4.setText($r6);
                    $r3.setText($r5);
                    return $r7;
                }
            }
            $r4.setVisibility(8);
            $r3.setText($r5);
            return $r7;
        } else if ($r8 == null) {
            return $r7;
        } else {
            $r8.root.setVisibility(4);
            return $r7;
        }
    }

    public static AddressItem[] searchContacts(String $r0) throws  {
        Exception $r20;
        Log.d(Logger.TAG, "contact search start " + $r0);
        if (ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.READ_CONTACTS") != 0) {
            return null;
        }
        int $i0;
        AddressItem[] addressItemArr = null;
        int i = 0;
        try {
            Cursor $r9 = AppService.getAppContext().getContentResolver().query(Data.CONTENT_URI, null, "display_name LIKE ? AND mimetype = ?", new String[]{"%" + $r0 + "%", "vnd.android.cursor.item/postal-address_v2"}, null);
            AddressItem[] $r10 = new AddressItem[$r9.getCount()];
            addressItemArr = $r10;
            $r0 = "(?i).*\\b" + $r0 + ".*";
            $i0 = 0;
            while ($r9.moveToNext()) {
                try {
                    String $r3 = $r9.getString($r9.getColumnIndex("display_name"));
                    if ($r3.matches($r0)) {
                        i = $i0 + 1;
                        $r10[$i0] = new AddressItem(null, null, $r3, null, $r9.getString($r9.getColumnIndex("data1")).replace("\n", " "), null, Boolean.valueOf(false), null, Integer.valueOf(5), null, null, null, null, null, null, $r9.getString($r9.getColumnIndex("data10")), $r9.getString($r9.getColumnIndex("data8")), $r9.getString($r9.getColumnIndex("data7")), $r9.getString($r9.getColumnIndex("data4")), "", null, null);
                        $i0 = i;
                    }
                } catch (Exception e) {
                    $r20 = e;
                    i = $i0;
                }
            }
            $r9.close();
            i = $i0;
        } catch (Exception e2) {
            $r20 = e2;
        }
        if (addressItemArr != null) {
            return addressItemArr;
        }
        int length = addressItemArr.length;
        $i0 = length;
        return length <= i ? (AddressItem[]) Arrays.copyOfRange(addressItemArr, 0, i) : addressItemArr;
        Log.d(Logger.TAG, $r20.toString());
        if (addressItemArr != null) {
            return addressItemArr;
        }
        int length2 = addressItemArr.length;
        $i0 = length2;
        if (length2 <= i) {
        }
    }
}
