package com.waze.share;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.navigate.social.ShareDriveActivity;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.view.title.TitleBar;
import java.util.List;

public class ShareActivity extends ActivityBase {
    protected static final float HINT_SIZE = 14.0f;
    private static final int RQ_CODE_SET_HOME = 1001;
    private static final int RQ_CODE_SET_WORK = 1002;
    protected static final float TEXT_SIZE = 16.0f;
    private boolean bIsFollow = false;
    private boolean isNavigating = false;
    private NativeManager nativeManager = AppService.getNativeManager();
    private EditText searchBox;

    class C28231 implements OnClickListener {
        C28231() {
        }

        public void onClick(View v) {
            if (!ShareDriveActivity.bIsFollow) {
                ShareActivity.this.nativeManager.StopFollow();
            }
            ShareActivity.this.setResult(-1);
            ShareActivity.this.finish();
        }
    }

    class C28252 implements OnClickListener {

        class C28241 implements DriveToGetAddressItemArrayCallback {
            C28241() {
            }

            public void getAddressItemArrayCallback(AddressItem[] ai) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "HOME");
                if (ai != null) {
                    ShareUtility.OpenShareActivity(ShareActivity.this, ShareType.ShareType_ShareSelection, null, ai[0], null);
                    return;
                }
                Intent intent = new Intent(ShareActivity.this, AddHomeWorkActivity.class);
                intent.putExtra(PublicMacros.ADDRESS_TYPE, 2);
                ShareActivity.this.startActivityForResult(intent, 1001);
            }
        }

        C28252() {
        }

        public void onClick(View arg0) {
            DriveToNativeManager.getInstance().getHome(new C28241());
        }
    }

    class C28273 implements OnClickListener {

        class C28261 implements DriveToGetAddressItemArrayCallback {
            C28261() {
            }

            public void getAddressItemArrayCallback(AddressItem[] ai) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "WORK");
                if (ai != null) {
                    ShareUtility.OpenShareActivity(ShareActivity.this, ShareType.ShareType_ShareSelection, null, ai[0], null);
                    return;
                }
                Intent intent = new Intent(ShareActivity.this, AddHomeWorkActivity.class);
                intent.putExtra(PublicMacros.ADDRESS_TYPE, 4);
                ShareActivity.this.startActivityForResult(intent, 1002);
            }
        }

        C28273() {
        }

        public void onClick(View arg0) {
            DriveToNativeManager.getInstance().getWork(new C28261());
        }
    }

    class C28284 implements OnClickListener {
        C28284() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "DRIVE");
            ShareUtility.OpenShareActivity(ShareActivity.this, ShareType.ShareType_ShareDrive, null, null, null);
        }
    }

    class C28295 implements OnClickListener {
        C28295() {
        }

        public void onClick(View arg0) {
            if (ShareActivity.this.isNavigating) {
                ShareActivity.this.onShareMyDestination();
            }
        }
    }

    class C28306 implements OnClickListener {
        C28306() {
        }

        public void onClick(View arg0) {
            ShareActivity.this.onShareMyCurrentLocation();
        }
    }

    class C28317 implements OnClickListener {
        C28317() {
        }

        public void onClick(View arg0) {
            ShareActivity.this.onShareRecentSearchLocation();
        }
    }

    class C28328 implements OnClickListener {
        C28328() {
        }

        public void onClick(View arg0) {
            ShareActivity.this.onShareMySavedLocation();
        }
    }

    class C28339 implements OnEditorActionListener {
        C28339() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.d(Logger.TAG, "a:" + actionId + " ke:" + event);
            if (actionId == 3) {
                ShareActivity.this.searchClicked();
            }
            if (event != null && event.getAction() == 1) {
                ShareActivity.this.searchClicked();
            }
            return true;
        }
    }

    public void onBackPressed() {
        if (!ShareDriveActivity.bIsFollow) {
            this.nativeManager.StopFollow();
        }
        super.onBackPressed();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.send);
        ((TitleBar) findViewById(C1283R.id.SendTitle)).init((Activity) this, this.nativeManager.getLanguageString(DisplayStrings.DS_SEND_TITLE));
        ((TitleBar) findViewById(C1283R.id.SendTitle)).setOnClickCloseListener(new C28231());
        ((TextView) findViewById(C1283R.id.shareDriveAndEtaText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SEND_BUTTON_MY_DRIVE));
        findViewById(C1283R.id.shareDriveAndEtaSubText).setVisibility(8);
        ((TextView) findViewById(C1283R.id.shareMyCurrentLocationText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SEND_BUTTON_CURRENT_LOCATION));
        ((TextView) findViewById(C1283R.id.shareMyDestinationText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_SEND_BUTTON_DESTINATION));
        ((TextView) findViewById(C1283R.id.shareMyHomeText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_MY_HOME));
        ((TextView) findViewById(C1283R.id.shareMyWorkText)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_MY_WORK));
        ((SettingsTitleText) findViewById(C1283R.id.shareOtherOptionsTitle)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_MORE_OPTIONS));
        ((WazeSettingsView) findViewById(C1283R.id.FromHistory)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_FROM_HISTORY));
        ((WazeSettingsView) findViewById(C1283R.id.FromFavorites)).setText(this.nativeManager.getLanguageString(DisplayStrings.DS_FROM_FAVORITES));
        findViewById(C1283R.id.shareMyHome).setOnClickListener(new C28252());
        findViewById(C1283R.id.shareMyWork).setOnClickListener(new C28273());
        findViewById(C1283R.id.shareDriveAndEta).setOnClickListener(new C28284());
        ImageButton speakButton = (ImageButton) findViewById(C1283R.id.speechRecognition);
        if (getPackageManager().queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0).size() == 0) {
            speakButton.setVisibility(8);
        }
        if (this.nativeManager.isNavigatingNTV()) {
            this.isNavigating = true;
        } else {
            View destination = findViewById(C1283R.id.shareMyDestination);
            destination.setEnabled(false);
            destination.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            View eta = findViewById(C1283R.id.shareDriveAndEta);
            eta.setEnabled(false);
            eta.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        }
        findViewById(C1283R.id.shareMyDestination).setOnClickListener(new C28295());
        findViewById(C1283R.id.shareMyCurrentLocation).setOnClickListener(new C28306());
        findViewById(C1283R.id.FromHistory).setOnClickListener(new C28317());
        findViewById(C1283R.id.FromFavorites).setOnClickListener(new C28328());
        this.searchBox = (EditText) findViewById(C1283R.id.searchBox);
        this.searchBox.setOnTouchListener(EditTextUtils.getKeyboardLockOnTouchListener(this, this.searchBox, null));
        this.searchBox.setOnEditorActionListener(new C28339());
        this.searchBox.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable arg0) {
            }

            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            public void onTextChanged(CharSequence arg0, int start, int before, int count) {
                if (arg0.length() == 0) {
                    ShareActivity.this.searchBox.setTextSize(1, ShareActivity.HINT_SIZE);
                } else {
                    ShareActivity.this.searchBox.setTextSize(1, ShareActivity.TEXT_SIZE);
                }
            }
        });
        this.searchBox.setHint(this.nativeManager.getLanguageString(299));
    }

    public void speechRecognitionClicked(View v) {
        Log.d(Logger.TAG, "SR pressed");
        Analytics.log("VOICE_SEARCH");
        Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        startActivityForResult(intent, 1234);
    }

    public void searchClicked() {
        Log.d(Logger.TAG, "Search from share activity pressed");
        if (this.searchBox.getText().length() > 0) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "SEARCH");
            Intent intent = new Intent(this, SearchResultsActivity.class);
            intent.putExtra(PublicMacros.SEARCH_STRING_KEY, this.searchBox.getText().toString());
            intent.putExtra(PublicMacros.SEARCH_MODE, 8);
            startActivityForResult(intent, 1);
        }
    }

    public void onPause() {
        super.onPause();
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.searchBox.getWindowToken(), 0);
    }

    protected void onShareRecentSearchLocation() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "HISTORY");
        startActivityForResult(new Intent(this, ShareRecentSearch.class), 1);
    }

    protected void onShareMySavedLocation() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "FAVORITE");
        startActivityForResult(new Intent(this, MySavedLocationActivity.class), 1);
    }

    protected void onShareMyCurrentLocation() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "CURRENT_LOCATION");
        ShareUtility.OpenShareActivity(this, ShareType.ShareType_ShareLocation, null, null, null);
    }

    protected void onShareMyDestination() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "DESTINATION");
        ShareUtility.OpenShareActivity(this, ShareType.ShareType_ShareDestination, null, null, null);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1234) {
            if (resultCode == -1) {
                List<String> matches = data.getStringArrayListExtra("android.speech.extra.RESULTS");
                if (matches.size() > 0) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VOICE_SEARCH_RECOGNIZED);
                    this.searchBox.setText((CharSequence) matches.get(0));
                    ((InputMethodManager) getSystemService("input_method")).showSoftInput(this.searchBox, 2);
                }
            }
        } else if (requestCode == 1002) {
            DriveToNativeManager.getInstance().getWork(new DriveToGetAddressItemArrayCallback() {
                public void getAddressItemArrayCallback(AddressItem[] ai) {
                    if (ai != null) {
                        ShareUtility.OpenShareActivity(ShareActivity.this, ShareType.ShareType_ShareSelection, null, ai[0], null);
                    }
                }
            });
        } else if (requestCode == 1001) {
            DriveToNativeManager.getInstance().getHome(new DriveToGetAddressItemArrayCallback() {
                public void getAddressItemArrayCallback(AddressItem[] ai) {
                    if (ai != null) {
                        ShareUtility.OpenShareActivity(ShareActivity.this, ShareType.ShareType_ShareSelection, null, ai[0], null);
                    }
                }
            });
        } else if (resultCode == -1) {
            setResult(-1);
            finish();
        }
    }
}
