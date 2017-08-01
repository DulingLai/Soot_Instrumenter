package com.waze.navigate;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.PlaceData;
import com.waze.autocomplete.PlacesAutoCompleteAdapter;
import com.waze.autocomplete.PlacesAutoCompleteAdapter.IPrepareForSearchResults;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.SettingsNativeManager.SettingsSearchLangListener;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.view.text.InstantAutoComplete;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Comparator;

public final class AutocompleteSearchActivity extends ActivityBase {
    protected static final float HINT_SIZE = 20.0f;
    public static final int PREVIEW_HOME_WORK_REQUEST = 1237;
    private static final int SEARCH_REQUEST_CODE = 1236;
    protected static final float TEXT_SIZE = 20.0f;
    DriveToGetAddressItemArrayCallback getHistoryCallback = new C21055();
    private boolean isKeyboardVisible;
    private AddressItem[] mAddresses = null;
    int mDsTitle;
    private PlacesAutoCompleteAdapter mPlaceAdapter;
    private boolean mSkipPreview;
    Integer mType;
    private NativeManager natMgr;
    private NativeManager nativeManager;
    private DriveToNativeManager nm;
    InstantAutoComplete searchBox;
    private String speechString;

    class C20981 implements OnItemClickListener {
        C20981() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View $r2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
            PlaceData $r6 = AutocompleteSearchActivity.this.mPlaceAdapter.getItemByPosition($i0);
            if ($r6.mVenueId != null && !$r6.mVenueId.equals("")) {
                AutocompleteSearchActivity.this.PlaceClickedByAutoComplete($r2, $r6);
            } else if ($r6.mLocalIndex >= 0) {
                AutocompleteSearchActivity.this.finishedSearch(AutocompleteSearchActivity.this.mAddresses[$r6.mLocalIndex]);
            }
        }
    }

    class C20992 implements OnEditorActionListener {
        C20992() throws  {
        }

        public boolean onEditorAction(TextView v, int $i0, KeyEvent $r2) throws  {
            Log.d(Logger.TAG, "a:" + $i0 + " ke:" + $r2);
            if ($i0 == 3) {
                AutocompleteSearchActivity.this.continueToSearch();
            }
            if ($r2 == null) {
                return true;
            }
            if ($r2.getAction() != 1) {
                return true;
            }
            AutocompleteSearchActivity.this.continueToSearch();
            return true;
        }
    }

    class C21013 implements TextWatcher {

        class C21001 implements Runnable {
            C21001() throws  {
            }

            public void run() throws  {
                if (!AutocompleteSearchActivity.this.isFinishing() && AutocompleteSearchActivity.this.isRunning()) {
                    AutocompleteSearchActivity.this.searchBox.showDropDown();
                }
            }
        }

        C21013() throws  {
        }

        public void afterTextChanged(Editable arg0) throws  {
            AutocompleteSearchActivity.this.searchBox.postDelayed(new C21001(), 100);
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) throws  {
        }

        public void onTextChanged(CharSequence $r1, int start, int before, int count) throws  {
            if ($r1.length() == 0) {
                AutocompleteSearchActivity.this.searchBox.setTextSize(1, 20.0f);
            } else {
                AutocompleteSearchActivity.this.searchBox.setTextSize(1, 20.0f);
            }
        }
    }

    class C21024 implements SettingsSearchLangListener {
        C21024() throws  {
        }

        public void onComplete(String $r2) throws  {
            try {
                Intent $r1 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                $r1.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                if ($r2 == null || $r2.isEmpty()) {
                    Logger.m38e("SettingsVoiceSearchActivity: Config for search language null or empty. Not setting lang for speech recog");
                } else {
                    $r1.putExtra("android.speech.extra.LANGUAGE", $r2);
                }
                AnalyticsBuilder $r3 = AnalyticsBuilder.analytics("VOICE_SEARCH");
                if ($r2 == null) {
                    $r2 = "";
                }
                $r3.addParam(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_BY_VOICE_LANGUAGE, $r2).addParam(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_BY_VOICE_AUTO_SELECTED, SettingsNativeManager.getInstance().getCurrentSearchVoiceIsAutoNTV() ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_T : AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
                AutocompleteSearchActivity.this.startActivityForResult($r1, 1234);
            } catch (Exception e) {
            }
        }
    }

    class C21055 implements DriveToGetAddressItemArrayCallback {

        class C21031 implements Comparator<AddressItem> {
            C21031() throws  {
            }

            public int compare(AddressItem $r1, AddressItem $r2) throws  {
                int $i0 = $r1.getType();
                int $i1 = $r2.getType();
                if (AutocompleteSearchActivity.this.mType.intValue() == 12) {
                    boolean $z0;
                    boolean $z1;
                    if ($i0 == 1 || $i0 == 3) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    if ($i1 == 1 || $i1 == 3) {
                        $z1 = true;
                    } else {
                        $z1 = false;
                    }
                    if ($z0 && $z1) {
                        return 0;
                    }
                    if ($z0) {
                        return -1;
                    }
                    if ($z1) {
                        return 1;
                    }
                }
                if ($i0 != $i1) {
                    return $i0 == 8 ? -1 : 1;
                } else {
                    return 0;
                }
            }
        }

        class C21042 implements IPrepareForSearchResults {
            C21042() throws  {
            }

            public void onPrepareForSearchResults() throws  {
                AutocompleteSearchActivity.this.SetHandlerForAutocomplete();
            }
        }

        C21055() throws  {
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void getAddressItemArrayCallback(com.waze.navigate.AddressItem[] r32) throws  {
            /*
            r31 = this;
            r7 = com.waze.NativeManager.getInstance();
            r9 = "home";
            r8 = r7.getLanguageString(r9);
            r7 = com.waze.NativeManager.getInstance();
            r9 = "work";
            r10 = r7.getLanguageString(r9);
            r11 = new java.util.ArrayList;
            r12 = r11;
            r11.<init>();
            r13 = 0;
        L_0x001c:
            r0 = r32;
            r14 = r0.length;
            if (r13 >= r14) goto L_0x0096;
        L_0x0021:
            r15 = r32[r13];
            r14 = r15.getType();
            r16 = 6;
            r0 = r16;
            if (r14 == r0) goto L_0x0093;
        L_0x002d:
            r15 = r32[r13];
            r12.add(r15);
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.mType;
            r18 = r0;
            r14 = r0.intValue();
            r16 = 12;
            r0 = r16;
            if (r14 != r0) goto L_0x0093;
        L_0x0046:
            r15 = r32[r13];
            r14 = r15.getType();
            r16 = 5;
            r0 = r16;
            if (r14 != r0) goto L_0x0093;
        L_0x0052:
            r15 = r32[r13];
            r19 = r15.getTitle();
            r20 = "home";
            r0 = r20;
            r1 = r19;
            r21 = r0.equalsIgnoreCase(r1);
            if (r21 != 0) goto L_0x006c;
        L_0x0064:
            r0 = r19;
            r21 = r8.equalsIgnoreCase(r0);
            if (r21 == 0) goto L_0x0075;
        L_0x006c:
            r15 = r32[r13];
            r16 = 1;
            r0 = r16;
            r15.setType(r0);
        L_0x0075:
            r20 = "work";
            r0 = r20;
            r1 = r19;
            r21 = r0.equalsIgnoreCase(r1);
            if (r21 != 0) goto L_0x008a;
        L_0x0082:
            r0 = r19;
            r21 = r10.equalsIgnoreCase(r0);
            if (r21 == 0) goto L_0x0093;
        L_0x008a:
            r15 = r32[r13];
            r16 = 3;
            r0 = r16;
            r15.setType(r0);
        L_0x0093:
            r13 = r13 + 1;
            goto L_0x001c;
        L_0x0096:
            r22 = new com.waze.navigate.AutocompleteSearchActivity$5$1;
            r0 = r22;
            r1 = r31;
            r0.<init>();
            r0 = r22;
            java.util.Collections.sort(r12, r0);
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.mType;
            r18 = r0;
            r13 = r0.intValue();
            r16 = 12;
            r0 = r16;
            if (r13 != r0) goto L_0x00c9;
        L_0x00b8:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r15 = com.waze.navigate.AddressItem.getCurLocAddressItem(r0);
            r16 = 0;
            r0 = r16;
            r12.add(r0, r15);
        L_0x00c9:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r13 = r12.size();
            r0 = new com.waze.navigate.AddressItem[r13];
            r32 = r0;
            r0 = r17;
            r1 = r32;
            r0.mAddresses = r1;
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r32 = r0.mAddresses;
            r0 = r32;
            r12.toArray(r0);
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r23 = new com.waze.autocomplete.PlacesAutoCompleteAdapter;
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r24 = r0;
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r25 = r0;
            r32 = r0.mAddresses;
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r25 = r0;
            r7 = r0.natMgr;
            r8 = r7.getApiKey();
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r25 = r0;
            r0 = r0.searchBox;
            r26 = r0;
            r27 = new com.waze.navigate.AutocompleteSearchActivity$5$2;
            r0 = r27;
            r1 = r31;
            r0.<init>();
            r29 = r26;
            r29 = (android.view.View) r29;
            r28 = r29;
            r16 = 17367050; // 0x109000a float:2.5162954E-38 double:8.580463E-317;
            r0 = r23;
            r1 = r24;
            r2 = r16;
            r3 = r32;
            r4 = r8;
            r5 = r28;
            r6 = r27;
            r0.<init>(r1, r2, r3, r4, r5, r6);
            r0 = r17;
            r1 = r23;
            r0.mPlaceAdapter = r1;
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.mType;
            r18 = r0;
            r13 = r0.intValue();
            r16 = 3;
            r0 = r16;
            if (r13 == r0) goto L_0x01aa;
        L_0x015a:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.mType;
            r18 = r0;
            r13 = r0.intValue();
            r16 = 4;
            r0 = r16;
            if (r13 == r0) goto L_0x01aa;
        L_0x016e:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.mType;
            r18 = r0;
            r13 = r0.intValue();
            r16 = 10;
            r0 = r16;
            if (r13 == r0) goto L_0x01aa;
        L_0x0182:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.mType;
            r18 = r0;
            r13 = r0.intValue();
            r16 = 11;
            r0 = r16;
            if (r13 == r0) goto L_0x01aa;
        L_0x0196:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.mType;
            r18 = r0;
            r13 = r0.intValue();
            r16 = 12;
            r0 = r16;
            if (r13 != r0) goto L_0x01bd;
        L_0x01aa:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r30 = r0.mPlaceAdapter;
            r16 = 1;
            r0 = r30;
            r1 = r16;
            r0.setSkipCategories(r1);
        L_0x01bd:
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r30 = r0.mPlaceAdapter;
            r0 = r30;
            r13 = r0.getFeatures();
            r13 = r13 & -33;
            r13 = r13 & -17;
            r13 = r13 | 8;
            r13 = r13 | 4096;
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r30 = r0.mPlaceAdapter;
            r0 = r30;
            r0.setFeatures(r13);
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.searchBox;
            r26 = r0;
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r30 = r0.mPlaceAdapter;
            r0 = r26;
            r1 = r30;
            r0.setAdapter(r1);
            r0 = r31;
            r0 = com.waze.navigate.AutocompleteSearchActivity.this;
            r17 = r0;
            r0 = r0.searchBox;
            r26 = r0;
            r0.filterNow();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.AutocompleteSearchActivity.5.getAddressItemArrayCallback(com.waze.navigate.AddressItem[]):void");
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        getWindow().setSoftInputMode(5);
        this.mType = Integer.valueOf(getIntent().getExtras().getInt(PublicMacros.SEARCH_MODE));
        this.mSkipPreview = getIntent().getExtras().getBoolean(PublicMacros.SKIP_PREVIEW, false);
        $r1 = getIntent().getExtras();
        Bundle bundle = $r1;
        this.mDsTitle = bundle.getInt(PublicMacros.TITLE_DS, DisplayStrings.DS_NULL);
        this.nm = DriveToNativeManager.getInstance();
        this.natMgr = NativeManager.getInstance();
        this.natMgr.OpenSearchIntent();
        this.isKeyboardVisible = getIntent().getBooleanExtra("keyboard", false);
        this.speechString = getIntent().getStringExtra("Speech");
        initLayout();
        if (this.speechString != null) {
            this.searchBox.setText(this.speechString);
        }
        if (this.isKeyboardVisible || this.speechString != null) {
            this.nm.setSearchMode(true);
            ((InputMethodManager) getSystemService("input_method")).toggleSoftInput(2, 0);
        } else {
            this.nm.setSearchMode(false);
        }
        if (this.mType.intValue() == 3 || this.mType.intValue() == 4) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SET_COMMUTE_MENU_SHOWN, null, null);
        }
        String $r7 = "SEARCH_ACTIVITY";
        if (this.mType.intValue() == 3 || this.mType.intValue() == 10) {
            $r7 = "HOME";
        } else if (this.mType.intValue() == 4 || this.mType.intValue() == 11) {
            $r7 = "WORK";
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_SEARCH_MENU_SHOWN).addParam("TYPE", $r7).addParam("ADD_STOP", AnalyticsEvents.ANALYTICS_EVENT_VALUE_F).send();
    }

    protected void onDestroy() throws  {
        this.nm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        super.onDestroy();
    }

    public void onBackPressed() throws  {
        super.onBackPressed();
    }

    public void SetHandlerForAutocomplete() throws  {
        this.nm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        this.nm.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
    }

    public void searchClickedByAutoComplete(View v) throws  {
        Intent $r2 = new Intent(this, SearchResultsActivity.class);
        $r2.putExtra(PublicMacros.SEARCH_STRING_KEY, this.searchBox.getText().toString());
        $r2.putExtra(PublicMacros.SEARCH_MODE, this.mType);
        $r2.putExtra(PublicMacros.SKIP_PREVIEW, this.mSkipPreview);
        startActivityForResult($r2, 1236);
    }

    public void PlaceClickedByAutoComplete(View v, PlaceData $r2) throws  {
        if (this.mType.intValue() == 3 || this.mType.intValue() == 4 || this.mType.intValue() == 10 || this.mType.intValue() == 11) {
            boolean $z0 = this.mType.intValue() == 3 || this.mType.intValue() == 10;
            Object obj = (this.mType.intValue() == 11 || this.mType.intValue() == 10) ? 1 : null;
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_COMMUTE_AUTOCOMPLETE_CLICK).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_TYPE, $z0 ? "HOME" : "WORK").addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_STATUS, obj != null ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_SET : "EDIT").send();
        }
        this.nm.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        NativeManager $r10 = this.natMgr;
        String $r11 = $r2.mVenueId;
        String $r3 = $r2.mReference;
        String $r4 = $r2.mTitle;
        int $i0 = $r2.mfeature;
        String $r5 = $r2.mResponse;
        InstantAutoComplete $r12 = this.searchBox;
        $r10.AutoCompletePlaceClicked(null, $r11, $r3, null, null, false, $r4, false, $i0, $r5, $r12.getText().toString());
    }

    private void continueToSearch() throws  {
        Intent $r1 = new Intent(this, SearchResultsActivity.class);
        $r1.putExtra(PublicMacros.SEARCH_STRING_KEY, this.searchBox.getText().toString());
        $r1.putExtra(PublicMacros.ADDRESS_ITEM, getIntent().getExtras().getSerializable(PublicMacros.ADDRESS_ITEM));
        $r1.putExtra(PublicMacros.SEARCH_MODE, this.mType);
        $r1.putExtra(PublicMacros.SKIP_PREVIEW, this.mSkipPreview);
        startActivityForResult($r1, 1);
    }

    private void finishedSearch(AddressItem $r1) throws  {
        Intent $r2;
        if (this.mType.intValue() != 3 && this.mType.intValue() != 4 && this.mType.intValue() != 10 && this.mType.intValue() != 11) {
            $r2 = new Intent();
            $r2.putExtra("ai", $r1);
            setResult(-1, $r2);
            finish();
        } else if (this.mSkipPreview) {
            $r2 = new Intent();
            $r1.setCategory(Integer.valueOf(1));
            if (this.mType.intValue() == 10 || this.mType.intValue() == 3) {
                $r1.setTitle("Home");
            } else {
                $r1.setTitle("Work");
            }
            $r2.putExtra("ai", $r1);
            setResult(-1, $r2);
            finish();
        } else {
            String $r4 = getSearchMode(this.mType.intValue());
            $r2 = new Intent(this, AddressPreviewActivity.class);
            $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
            $r2.putExtra(AddressPreviewActivity.COMMUTE_MODE, $r4);
            startActivityForResult($r2, 1237);
        }
    }

    @Nullable
    public static String getSearchMode(int $i0) throws  {
        switch ($i0) {
            case 3:
                return "home";
            case 4:
                return "work";
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                break;
            case 10:
                return "home_go";
            case 11:
                return "work_go";
            default:
                break;
        }
        return null;
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        super.onConfigurationChanged($r1);
        initLayout();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void initLayout() throws  {
        /*
        r33 = this;
        r2 = 2130903097; // 0x7f030039 float:1.7413002E38 double:1.052806015E-314;
        r0 = r33;
        r0.setContentView(r2);
        r3 = com.waze.AppService.getNativeManager();
        r0 = r33;
        r0.nativeManager = r3;
        r0 = r33;
        r3 = r0.nativeManager;
        r2 = 593; // 0x251 float:8.31E-43 double:2.93E-321;
        r4 = r3.getLanguageString(r2);
        r0 = r33;
        r5 = r0.mDsTitle;
        r6 = com.waze.strings.DisplayStrings.DS_NULL;
        if (r5 == r6) goto L_0x010e;
    L_0x0022:
        r0 = r33;
        r5 = r0.mDsTitle;
        r4 = com.waze.strings.DisplayStrings.displayString(r5);
    L_0x002a:
        r2 = 2131689674; // 0x7f0f00ca float:1.900837E38 double:1.0531946355E-314;
        r0 = r33;
        r7 = r0.findViewById(r2);
        r9 = r7;
        r9 = (com.waze.view.title.TitleBar) r9;
        r8 = r9;
        r0 = r33;
        r8.init(r0, r4);
        r2 = 2131689683; // 0x7f0f00d3 float:1.9008388E38 double:1.05319464E-314;
        r0 = r33;
        r7 = r0.findViewById(r2);
        r11 = r7;
        r11 = (com.waze.view.text.InstantAutoComplete) r11;
        r10 = r11;
        r0 = r33;
        r0.searchBox = r10;
        r0 = r33;
        r10 = r0.searchBox;
        r2 = 0;
        r10.setThreshold(r2);
        r0 = r33;
        r10 = r0.searchBox;
        r12 = new android.graphics.drawable.ColorDrawable;
        r2 = 0;
        r12.<init>(r2);
        r10.setDropDownBackgroundDrawable(r12);
        r0 = r33;
        r10 = r0.searchBox;
        r13 = new com.waze.navigate.AutocompleteSearchActivity$1;
        r0 = r33;
        r13.<init>();
        r10.setOnItemClickListener(r13);
        r0 = r33;
        r10 = r0.searchBox;
        r0 = r33;
        r14 = r0.searchBox;
        r16 = r14;
        r16 = (android.widget.EditText) r16;
        r15 = r16;
        r18 = 0;
        r0 = r33;
        r1 = r18;
        r17 = com.waze.utils.EditTextUtils.getKeyboardLockOnTouchListener(r0, r15, r1);
        r0 = r17;
        r10.setOnTouchListener(r0);
        r0 = r33;
        r10 = r0.searchBox;
        r0 = r33;
        r3 = r0.nativeManager;
        r2 = 28;
        r4 = r3.getLanguageString(r2);
        r10.setHint(r4);
        r0 = r33;
        r10 = r0.searchBox;
        r19 = new com.waze.navigate.AutocompleteSearchActivity$2;
        r0 = r19;
        r1 = r33;
        r0.<init>();
        r0 = r19;
        r10.setOnEditorActionListener(r0);
        r0 = r33;
        r10 = r0.searchBox;
        r20 = new com.waze.navigate.AutocompleteSearchActivity$3;
        r0 = r20;
        r1 = r33;
        r0.<init>();
        r0 = r20;
        r10.addTextChangedListener(r0);
        r0 = r33;
        r0 = r0.nm;
        r21 = r0;
        r0 = r33;
        r0 = r0.getHistoryCallback;
        r22 = r0;
        r0 = r21;
        r1 = r22;
        r0.getAutoCompleteData(r1);
        r2 = 2131689684; // 0x7f0f00d4 float:1.900839E38 double:1.0531946405E-314;
        r0 = r33;
        r7 = r0.findViewById(r2);
        r24 = r7;
        r24 = (android.widget.ImageButton) r24;
        r23 = r24;
        r0 = r33;
        r25 = r0.getPackageManager();
        r26 = new android.content.Intent;
        r27 = "android.speech.action.RECOGNIZE_SPEECH";
        r0 = r26;
        r1 = r27;
        r0.<init>(r1);
        r2 = 0;
        r0 = r25;
        r1 = r26;
        r28 = r0.queryIntentActivities(r1, r2);
        r0 = r28;
        r5 = r0.size();
        if (r5 != 0) goto L_0x0188;
    L_0x0106:
        r2 = 8;
        r0 = r23;
        r0.setVisibility(r2);
        return;
    L_0x010e:
        r0 = r33;
        r0 = r0.mType;
        r29 = r0;
        r5 = r0.intValue();
        r2 = 3;
        if (r5 == r2) goto L_0x0129;
    L_0x011b:
        r0 = r33;
        r0 = r0.mType;
        r29 = r0;
        r5 = r0.intValue();
        r2 = 10;
        if (r5 != r2) goto L_0x014b;
    L_0x0129:
        r2 = 3527; // 0xdc7 float:4.942E-42 double:1.7426E-320;
        r4 = com.waze.strings.DisplayStrings.displayString(r2);
        r2 = 2131690002; // 0x7f0f0212 float:1.9009035E38 double:1.0531947976E-314;
        r0 = r33;
        r7 = r0.findViewById(r2);
        r31 = r7;
        r31 = (android.widget.ImageView) r31;
        r30 = r31;
        goto L_0x0142;
    L_0x013f:
        goto L_0x002a;
    L_0x0142:
        r2 = 2130838237; // 0x7f0202dd float:1.728145E38 double:1.0527739697E-314;
        r0 = r30;
        r0.setImageResource(r2);
        goto L_0x013f;
    L_0x014b:
        r0 = r33;
        r0 = r0.mType;
        r29 = r0;
        r5 = r0.intValue();
        r2 = 4;
        if (r5 == r2) goto L_0x0166;
    L_0x0158:
        r0 = r33;
        r0 = r0.mType;
        r29 = r0;
        r5 = r0.intValue();
        r2 = 11;
        if (r5 != r2) goto L_0x002a;
    L_0x0166:
        r2 = 3528; // 0xdc8 float:4.944E-42 double:1.743E-320;
        r4 = com.waze.strings.DisplayStrings.displayString(r2);
        r2 = 2131690002; // 0x7f0f0212 float:1.9009035E38 double:1.0531947976E-314;
        r0 = r33;
        r7 = r0.findViewById(r2);
        r32 = r7;
        r32 = (android.widget.ImageView) r32;
        r30 = r32;
        goto L_0x017f;
    L_0x017c:
        goto L_0x002a;
    L_0x017f:
        r2 = 2130839527; // 0x7f0207e7 float:1.7284067E38 double:1.052774607E-314;
        r0 = r30;
        r0.setImageResource(r2);
        goto L_0x017c;
    L_0x0188:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.AutocompleteSearchActivity.initLayout():void");
    }

    public void speechRecognitionClicked(View v) throws  {
        Log.d(Logger.TAG, "SR pressed");
        SettingsNativeManager.getInstance().getSearchLanguageTag(new C21024());
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i0 == 1234) {
            if ($i1 == -1) {
                ArrayList $r2 = $r1.getStringArrayListExtra("android.speech.extra.RESULTS");
                if ($r2.size() > 0) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VOICE_SEARCH_RECOGNIZED);
                    this.searchBox.setText((CharSequence) $r2.get(0));
                    ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.searchBox, 2);
                }
            } else if ($i1 == 0) {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_LISTENING_CANCELLED).send();
            }
        } else if ($i0 == 1236) {
            setResult($i1, $r1);
            finish();
        } else if ($i0 == 1237 && $i1 == -1 && $r1 != null) {
            AddressItem $r9 = (AddressItem) $r1.getExtras().get("ai");
            Integer $r10 = this.mType;
            if ($r10.intValue() != 10) {
                $r10 = this.mType;
                Integer $r102 = $r10;
                if ($r10.intValue() != 3) {
                    $r9.setSecondaryTitle($r9.getTitle());
                    $r9.setTitle("Work");
                    $r9.setCategory(Integer.valueOf(1));
                    $r1.putExtra("ai", $r9);
                    setResult(-1, $r1);
                    finish();
                }
            }
            $r9.setSecondaryTitle($r9.getTitle());
            $r9.setTitle("Home");
            $r9.setCategory(Integer.valueOf(1));
            $r1.putExtra("ai", $r9);
            setResult(-1, $r1);
            finish();
        } else if ($i1 == 1) {
            setResult(1);
            finish();
        } else if ($i1 != 0) {
            setResult($i1, $r1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what != DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
            return super.myHandleMessage($r1);
        }
        this.nm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        this.natMgr.CloseProgressPopup();
        finishedSearch((AddressItem) $r1.getData().getSerializable("address_item"));
        return true;
    }

    public void onResume() throws  {
        super.onResume();
        this.searchBox.requestFocus();
        EditTextUtils.openKeyboard(this, this.searchBox);
        this.searchBox.performClick();
        this.searchBox.filterNow();
    }

    public void onPause() throws  {
        super.onPause();
        this.nm.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.searchBox.getWindowToken(), 0);
    }
}
