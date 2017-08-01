package com.waze.phone;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.SearchResultsActivity;
import com.waze.phone.PhoneAlreadyAWazerActivity.HomeWorkFavorites;
import com.waze.strings.DisplayStrings;
import java.util.EnumSet;

public class PhoneVerifyYourAccountActivity extends ActivityBase {
    private static final int AFTER_SEARCH_ADDRESS_RETURNED = 1;
    protected static final float HINT_SIZE = 14.0f;
    protected static final float TEXT_SIZE = 16.0f;
    private DriveToNativeManager driveToNativeManager;
    private AddressItem[] mAddressesToVerify = null;
    private EnumSet<HomeWorkFavorites> mHomeWorkAvailableFlags;
    private HomeWorkFavorites mHomeWorkCurrentFlag;
    private LinearLayout mNextButton;
    private TextView mNextText;
    private ScrollView mScrollView;
    private EditText mSearchBox;
    private TextView mVerifyBodyText;
    private TextView mVerifyHeaderText;
    private NativeManager nativeManager;

    class C22931 implements OnClickListener {
        C22931() {
        }

        public void onClick(View v) {
            PhoneVerifyYourAccountActivity.this.searchClicked(v);
        }
    }

    class C22942 implements OnClickListener {
        C22942() {
        }

        public void onClick(View v) {
            PhoneVerifyYourAccountActivity.this.searchClicked(v);
        }
    }

    class C22953 implements OnEditorActionListener {
        C22953() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (actionId != 3) {
                return false;
            }
            PhoneVerifyYourAccountActivity.this.searchClicked(v);
            return true;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout();
        initMembers();
        initFieldsTexts();
        setListeners();
        if (getIntent() != null) {
            this.mHomeWorkAvailableFlags = (EnumSet) getIntent().getExtras().getSerializable(PublicMacros.HOME_WORK_FLAGS);
            if (this.mHomeWorkAvailableFlags.contains(HomeWorkFavorites.HOME)) {
                setCurrentHomeWorkFlag(HomeWorkFavorites.HOME);
            } else {
                setCurrentHomeWorkFlag(HomeWorkFavorites.WORK);
            }
        }
    }

    private void setCurrentHomeWorkFlag(HomeWorkFavorites flag) {
        this.mHomeWorkCurrentFlag = flag;
        switch (flag) {
            case HOME:
                this.mVerifyBodyText.setText(NativeManager.getInstance().getLanguageString(297));
                return;
            case WORK:
                this.mVerifyBodyText.setText(NativeManager.getInstance().getLanguageString(298));
                return;
            default:
                return;
        }
    }

    private void setListeners() {
        setKeyboardVisibilityListener();
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        this.mSearchBox.setOnClickListener(new C22931());
        this.mNextButton.setOnClickListener(new C22942());
        this.mSearchBox.setOnEditorActionListener(new C22953());
    }

    private void setKeyboardVisibilityListener() {
        final View root = findViewById(C1283R.id.rootLayout);
        root.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                if (root.getRootView().getHeight() - root.getHeight() > 100) {
                    PhoneVerifyYourAccountActivity.this.mScrollView.scrollTo(0, PhoneVerifyYourAccountActivity.this.mScrollView.getHeight());
                }
            }
        });
    }

    private void initFieldsTexts() {
        setTextContent();
        setFonts();
    }

    private void setTextContent() {
        this.mVerifyHeaderText.setText(NativeManager.getInstance().getLanguageString(296).toUpperCase());
        this.mSearchBox.setHint(NativeManager.getInstance().getLanguageString(299));
        this.mNextText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_NEXT));
    }

    private void setFonts() {
        this.mSearchBox.setTypeface(ResManager.getRobotoLight(this));
    }

    private void initMembers() {
        this.driveToNativeManager = DriveToNativeManager.getInstance();
        this.nativeManager = AppService.getNativeManager();
        NativeManager.getInstance().OpenSearchIntent();
        NativeManager.getInstance().OpenRoutingIntent();
        this.driveToNativeManager.setSearchMode(false);
        this.mAddressesToVerify = new AddressItem[2];
    }

    private void initLayout() {
        setContentView(C1283R.layout.phone_verify_your_account);
        this.mNextButton = (LinearLayout) findViewById(C1283R.id.nextButton);
        this.mNextText = (TextView) findViewById(C1283R.id.nextText);
        this.mSearchBox = (EditText) findViewById(C1283R.id.searchBox);
        this.mVerifyHeaderText = (TextView) findViewById(C1283R.id.verifyHeaderText);
        this.mVerifyBodyText = (TextView) findViewById(C1283R.id.verifyBodyText);
        this.mScrollView = (ScrollView) findViewById(C1283R.id.rootScroll);
    }

    public void searchClicked(View v) {
        if (!this.mSearchBox.getText().toString().equals("")) {
            Log.d(Logger.TAG, "Search pressed");
            Intent intent = new Intent(this, SearchResultsActivity.class);
            String sCategory = this.nativeManager.isCategorySearchNTV(this.mSearchBox.getText().toString());
            if (sCategory == null || sCategory.equals("")) {
                intent.putExtra(PublicMacros.SEARCH_STRING_KEY, this.mSearchBox.getText().toString());
            } else {
                intent.putExtra(PublicMacros.SEARCH_CATEGORY, sCategory);
            }
            intent.putExtra(PublicMacros.SEARCH_MODE, 7);
            this.mSearchBox.setText("");
            startActivityForResult(intent, 1);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(Logger.TAG, "phoneVerifyYourAccount onActRes requestCode=" + requestCode + " resultCode=" + resultCode + " Intent=" + data);
        if (resultCode == -1) {
            switch (requestCode) {
                case 1:
                    startActivityForResult(new Intent(this, PhoneVerifyYourAccountFailureActivity.class), 1);
                    break;
                default:
                    setResult(-1);
                    finish();
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void handleSearchResult(Intent data) {
        if (data != null) {
            AddressItem ai = (AddressItem) data.getExtras().getSerializable(PublicMacros.ADDRESS_ITEM);
            switch (this.mHomeWorkCurrentFlag) {
                case HOME:
                    this.mHomeWorkAvailableFlags.remove(HomeWorkFavorites.HOME);
                    this.mAddressesToVerify[HomeWorkFavorites.HOME.getNumVal()] = ai;
                    if (this.mHomeWorkAvailableFlags.contains(HomeWorkFavorites.WORK)) {
                        setCurrentHomeWorkFlag(HomeWorkFavorites.WORK);
                        return;
                    }
                    return;
                case WORK:
                    this.mHomeWorkAvailableFlags.remove(HomeWorkFavorites.WORK);
                    this.mAddressesToVerify[HomeWorkFavorites.WORK.getNumVal()] = ai;
                    return;
                default:
                    return;
            }
        }
    }

    public void onPause() {
        super.onPause();
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.mSearchBox.getWindowToken(), 0);
    }
}
