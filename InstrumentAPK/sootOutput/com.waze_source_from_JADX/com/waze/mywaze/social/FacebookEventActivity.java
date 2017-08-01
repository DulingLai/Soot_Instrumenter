package com.waze.mywaze.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.main.navigate.LocationData;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.view.title.TitleBar;

public class FacebookEventActivity extends ActivityBase {
    protected static final float HINT_SIZE = 14.0f;
    protected static final float TEXT_SIZE = 16.0f;
    private boolean isNavigating = false;
    protected LocationData locationData;
    private AddressItem mAddressItem;
    private String mBody = null;
    private String mSubject = null;
    private String mUrl = null;
    private NativeManager nativeManager = AppService.getNativeManager();
    EditText searchBox;

    class C20081 implements OnClickListener {

        class C20071 implements DialogInterface.OnClickListener {
            C20071() throws  {
            }

            public void onClick(DialogInterface dialog, int $i0) throws  {
                if ($i0 == 1) {
                    DriveToNativeManager.getInstance().VerifyEventWithNoAddress(FacebookEventActivity.this.mAddressItem.getMeetingId());
                    FacebookEventActivity.this.setResult(-1);
                    FacebookEventActivity.this.finish();
                }
            }
        }

        C20081() throws  {
        }

        public void onClick(View v) throws  {
            C20071 $r2 = new C20071();
            MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(FacebookEventActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_ARE_YOU_SURE_Q), FacebookEventActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_EVENT_CONFIRM_NO_LOCATION), true, $r2, FacebookEventActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_REMOVE_EVENT), FacebookEventActivity.this.nativeManager.getLanguageString(344), -1);
        }
    }

    class C20092 implements OnClickListener {
        C20092() throws  {
        }

        public void onClick(View v) throws  {
            FacebookEventActivity.this.searchClicked();
        }
    }

    class C20103 implements OnEditorActionListener {
        C20103() throws  {
        }

        public boolean onEditorAction(TextView v, int $i0, KeyEvent $r2) throws  {
            Log.d(Logger.TAG, "a:" + $i0 + " ke:" + $r2);
            if ($i0 == 3) {
                FacebookEventActivity.this.searchClicked();
            }
            if ($r2 == null) {
                return true;
            }
            if ($r2.getAction() != 1) {
                return true;
            }
            FacebookEventActivity.this.searchClicked();
            return true;
        }
    }

    class C20114 implements TextWatcher {
        C20114() throws  {
        }

        public void afterTextChanged(Editable arg0) throws  {
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) throws  {
        }

        public void onTextChanged(CharSequence $r1, int start, int before, int count) throws  {
            if ($r1.length() == 0) {
                FacebookEventActivity.this.searchBox.setTextSize(1, FacebookEventActivity.HINT_SIZE);
            } else {
                FacebookEventActivity.this.searchBox.setTextSize(1, FacebookEventActivity.TEXT_SIZE);
            }
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.facebook_event);
        ((TitleBar) findViewById(C1283R.id.shareTitle)).init(this, DisplayStrings.DS_FACEBOOK_EVENT);
        this.mAddressItem = (AddressItem) getIntent().getExtras().getSerializable(PublicMacros.ADDRESS_ITEM);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VERIFY_EVENT_SCREEN);
        ((TextView) findViewById(C1283R.id.EventTitleText)).setText(this.mAddressItem.getTitle());
        ((TextView) findViewById(C1283R.id.EventDate)).setText(this.mAddressItem.getStartTime());
        ((TextView) findViewById(C1283R.id.EventBody)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_THIS_LOCATION_ADDRESS_HAS_NOT));
        ((TextView) findViewById(C1283R.id.NoAddressText)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_THIS_EVENT_HAS_NO_LOCATION));
        ((TextView) findViewById(C1283R.id.NoAddressText)).setPaintFlags(8);
        ((TextView) findViewById(C1283R.id.NoAddressText)).setOnClickListener(new C20081());
        ((Button) findViewById(C1283R.id.UseAddressButton)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_USE_THIS_ADDRESS));
        findViewById(C1283R.id.UseAddressButton).setOnClickListener(new C20092());
        this.searchBox = (EditText) findViewById(C1283R.id.searchBoxEvent);
        this.searchBox.setOnTouchListener(EditTextUtils.getKeyboardLockOnTouchListener(this, this.searchBox, null));
        this.searchBox.setOnEditorActionListener(new C20103());
        this.searchBox.addTextChangedListener(new C20114());
        this.searchBox.setText(this.mAddressItem.getAddress());
    }

    public void searchClicked() throws  {
        Log.d(Logger.TAG, "Search from facebook event pressed");
        Intent $r1 = new Intent(this, SearchResultsActivity.class);
        if (this.searchBox.getText().length() > 0) {
            $r1.putExtra(PublicMacros.SEARCH_STRING_KEY, this.searchBox.getText().toString());
            $r1.putExtra(PublicMacros.ADDRESS_ITEM, this.mAddressItem);
            $r1.putExtra(PublicMacros.SEARCH_MODE, 6);
            startActivityForResult($r1, 1);
        }
    }

    public void onPause() throws  {
        super.onPause();
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.searchBox.getWindowToken(), 0);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 != 0) {
            setResult($i1, $r1);
            finish();
        } else if ($i1 == MainActivity.VERIFY_EVENT_CODE) {
            setResult(MainActivity.VERIFY_EVENT_CODE, $r1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }
}
