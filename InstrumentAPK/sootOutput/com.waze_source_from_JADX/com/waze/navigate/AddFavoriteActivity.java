package com.waze.navigate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.internal.view.SupportMenu;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.animation.easing.AnimationEasingManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;

public final class AddFavoriteActivity extends ActivityBase {
    protected static final float HINT_SIZE = 14.0f;
    protected static final float TEXT_SIZE = 16.0f;
    private AnimationEasingManager managerX;
    private AnimationEasingManager managerY;
    private NativeManager nativeManager;
    EditText searchBox;
    Integer type;

    class C20543 implements OnEditorActionListener {
        C20543() throws  {
        }

        public boolean onEditorAction(TextView $r1, int $i0, KeyEvent $r2) throws  {
            if ($i0 == 3) {
                AddFavoriteActivity.this.searchClicked($r1);
            }
            if ($r2 == null) {
                return true;
            }
            if ($r2.getAction() != 1) {
                return true;
            }
            AddFavoriteActivity.this.searchClicked($r1);
            return true;
        }
    }

    class C20554 implements TextWatcher {
        C20554() throws  {
        }

        public void afterTextChanged(Editable arg0) throws  {
        }

        public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) throws  {
        }

        public void onTextChanged(CharSequence $r1, int start, int before, int count) throws  {
            if ($r1.length() == 0) {
                AddFavoriteActivity.this.searchBox.setTextSize(1, AddFavoriteActivity.HINT_SIZE);
            } else {
                AddFavoriteActivity.this.searchBox.setTextSize(1, AddFavoriteActivity.TEXT_SIZE);
            }
        }
    }

    class C20565 implements Runnable {
        C20565() throws  {
        }

        public void run() throws  {
            AddFavoriteActivity.this.searchBox.requestFocus();
            ((InputMethodManager) AddFavoriteActivity.this.getSystemService("input_method")).showSoftInput(AddFavoriteActivity.this.searchBox, 2);
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        setContentView(C1283R.layout.add_fav);
        this.nativeManager = AppService.getNativeManager();
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, this.nativeManager.getLanguageString(34));
        this.searchBox = (EditText) findViewById(C1283R.id.searchBox);
        this.searchBox.setOnTouchListener(EditTextUtils.getKeyboardLockOnTouchListener(this, this.searchBox, null));
        this.searchBox.setHint(this.nativeManager.getLanguageString(27));
        ((TextView) findViewById(C1283R.id.commuteImage)).setText(this.nativeManager.getLanguageString(152) + "\n\n" + this.nativeManager.getLanguageString(151) + "\n\n" + this.nativeManager.getLanguageString(385) + "\n");
        ((TextView) findViewById(C1283R.id.addFavAddressNotVerified)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_LOCATION_NOT_YET_VERIFIED));
        final View $r4 = findViewById(C1283R.id.noMapGrid);
        LayoutParams $r12 = $r4.getLayoutParams();
        int $i0 = getResources().getDisplayMetrics().widthPixels;
        $i0 += 400;
        int $i02 = $i0;
        $r12.width = $i0;
        $r4.setLayoutParams($r12);
        Animation c20511 = new Animation() {
            protected void applyTransformation(float $f0, Transformation $r1) throws  {
                super.applyTransformation($f0, $r1);
                $r4.setTranslationX(((float) Math.cos((((double) ($f0 * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN)) * 3.141592653589793d) * 2.7d)) * 200.0f);
                $r4.setTranslationY(((float) Math.sin((((double) ($f0 * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN)) * 3.141592653589793d) * 2.3d)) * 200.0f);
            }
        };
        c20511.setDuration(100000);
        c20511.setInterpolator(new LinearInterpolator());
        c20511.setRepeatCount(-1);
        c20511.setRepeatMode(2);
        $r4.startAnimation(c20511);
        this.type = Integer.valueOf(getIntent().getExtras().getInt(PublicMacros.ADDRESS_TYPE));
        findViewById(C1283R.id.addFavRemoveCalendarButton).setVisibility(8);
        findViewById(C1283R.id.addFavRemoveCalendarOr).setVisibility(8);
        ImageView $r18 = (ImageView) findViewById(C1283R.id.addFavPlaceLogo);
        Integer $r17 = this.type;
        switch ($r17.intValue()) {
            case 2:
                $r18.setImageResource(C1283R.drawable.list_icon_home);
                ((WazeTextView) findViewById(C1283R.id.addFavPlaceName)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_MY_HOME));
                break;
            case 4:
                $r18.setImageResource(C1283R.drawable.list_icon_work);
                ((WazeTextView) findViewById(C1283R.id.addFavPlaceName)).setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_MY_WORK));
                break;
            case 6:
                $r18.setImageResource(C1283R.drawable.list_icon_favorite);
                findViewById(C1283R.id.commuteImage).setVisibility(8);
                ((WazeTextView) findViewById(C1283R.id.addFavPlaceName)).setText(this.nativeManager.getLanguageString(34));
                break;
            case 11:
                $r18.setImageResource(C1283R.drawable.list_icon_calendar);
                String $r6 = getIntent().getExtras().getString(PublicMacros.SEARCH_STRING_KEY);
                AddressItem $r26 = (AddressItem) getIntent().getExtras().getSerializable(PublicMacros.ADDRESS_ITEM);
                if ($r6 != null) {
                    this.searchBox.setText($r6);
                    this.searchBox.setSelection($r6.length());
                }
                ((TitleBar) findViewById(C1283R.id.theTitleBar)).init(this, this.nativeManager.getLanguageString((int) DisplayStrings.DS_VERIFY_ADDRESS));
                findViewById(C1283R.id.commuteImage).setVisibility(8);
                ((TextView) findViewById(C1283R.id.addFavPlaceName)).setText($r26.getTitle());
                TextView $r10 = (TextView) findViewById(C1283R.id.addFavPleaseVerify);
                $r10.setVisibility(0);
                $r10.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_TO_CONFIRM_OR_UPDATE_EVENT_LOCATION));
                $r10 = (TextView) findViewById(C1283R.id.addFavRemoveCalendarOr);
                $r10.setVisibility(0);
                $r10.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_OR));
                $r10 = (TextView) findViewById(C1283R.id.addFavRemoveCalendarButton);
                $r10.setVisibility(0);
                $r10.setPaintFlags($r10.getPaintFlags() | 8);
                $r10.setTextColor(SupportMenu.CATEGORY_MASK);
                $r10.setText(this.nativeManager.getLanguageString((int) DisplayStrings.DS_REMOVE_THIS_EVENT));
                final AddressItem addressItem = $r26;
                $r10.setOnClickListener(new OnClickListener() {

                    class C20521 implements Runnable {
                        C20521() throws  {
                        }

                        public void run() throws  {
                            AddFavoriteActivity.this.nativeManager.CloseProgressPopup();
                            AddFavoriteActivity.this.setResult(MainActivity.RELOAD_SEARCH_CODE, new Intent());
                            AddFavoriteActivity.this.finish();
                        }
                    }

                    public void onClick(View v) throws  {
                        DriveToNativeManager.getInstance().CalendarAddressRemove(addressItem.getMeetingId());
                        AddFavoriteActivity.this.nativeManager.OpenProgressIconPopup(AddFavoriteActivity.this.nativeManager.getLanguageString((int) DisplayStrings.DS_EVENT_REMOVED), "sign_up_big_v");
                        new Handler().postDelayed(new C20521(), 1000);
                    }
                });
                break;
            default:
                break;
        }
        this.searchBox.setOnEditorActionListener(new C20543());
        this.searchBox.addTextChangedListener(new C20554());
        ImageButton $r21 = (ImageButton) findViewById(C1283R.id.speechRecognition);
        if (getPackageManager().queryIntentActivities(new Intent("android.speech.action.RECOGNIZE_SPEECH"), 0).size() == 0) {
            $r21.setVisibility(8);
        }
    }

    public void onResume() throws  {
        super.onResume();
        if (this.type.intValue() == 6) {
            new Handler().postDelayed(new C20565(), 100);
        }
    }

    public void onPause() throws  {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.searchBox.getWindowToken(), 0);
        super.onPause();
    }

    public void speechRecognitionClicked(View v) throws  {
        Logger.m36d("SR pressed");
        Intent $r2 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        $r2.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
        startActivityForResult($r2, 1234);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        Logger.m36d("AddFavorite onActRes requestCode=" + $i0 + " resultCode=" + $i1 + " Intent=" + $r1);
        if ($i0 == 1234) {
            if ($i1 == -1) {
                ArrayList $r4 = $r1.getStringArrayListExtra("android.speech.extra.RESULTS");
                if ($r4.size() > 0) {
                    this.searchBox.setText((CharSequence) $r4.get(0));
                }
            }
        } else if ($i1 != 0) {
            setResult($i1);
            finish();
        }
    }

    public void searchClicked(View v) throws  {
        Logger.m36d("Search pressed");
        Intent $r2 = new Intent(this, SearchResultsActivity.class);
        $r2.putExtra(PublicMacros.SEARCH_STRING_KEY, this.searchBox.getText().toString());
        byte $b0 = (byte) 0;
        switch (this.type.intValue()) {
            case 2:
                $b0 = (byte) 3;
                break;
            case 4:
                $b0 = (byte) 4;
                break;
            case 6:
                $b0 = (byte) 1;
                break;
            case 11:
                AddressItem $r10 = (AddressItem) getIntent().getExtras().getSerializable(PublicMacros.ADDRESS_ITEM);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CALENDAR_SEARCH, "ID|VALUE", $r10.getMeetingId() + "|" + this.searchBox.getText().toString());
                $b0 = (byte) 9;
                $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r10);
                break;
            default:
                break;
        }
        $r2.putExtra(PublicMacros.SEARCH_MODE, $b0);
        startActivityForResult($r2, 1);
    }
}
