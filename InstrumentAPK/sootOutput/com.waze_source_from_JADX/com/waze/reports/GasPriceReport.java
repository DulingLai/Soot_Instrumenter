package com.waze.reports;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.widget.AutoScrollHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ActionMode;
import android.view.ActionMode.Callback;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalFocusChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.WazeSwitchView;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.PriceFormatsListener;
import com.waze.navigate.DriveToNativeManager.ProductListener;
import com.waze.navigate.NearbyStation;
import com.waze.navigate.Product;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.SliderSeparator;
import com.waze.view.button.ReportMenuButton;
import com.waze.view.popups.GenericTakeover;
import com.waze.view.text.AutoResizeTextView;
import com.waze.view.text.AutoResizeTextView.OnTextResizeListener;
import com.waze.view.text.WazeEditText;
import java.util.ArrayList;

public class GasPriceReport extends ActivityBase implements ProductListener {
    private static final String DEFAULT_CATEGORY_ICON = "category_menu_GAS_STATION";
    private static final float DISABLED_ALPHA = 0.3f;
    public static final String NEARBY_STATIONS = "nearby_stations";
    private static final int[] priceItemIds = new int[]{C1283R.id.reportGasItem1, C1283R.id.reportGasItem2, C1283R.id.reportGasItem3, C1283R.id.reportGasItem4};
    private View contentView;
    private int focusedItem = -1;
    private DriveToNativeManager mDtnMgr;
    private NearbyStation[] mNearbyStations;
    private Product mProduct;
    private int mSelectedIndex = -1;
    private NativeManager nativeManager;
    private String[] previousPrices;
    private char priceFormatSeparator = FilenameUtils.EXTENSION_SEPARATOR;
    private int[] priceFormatsFractionalDigits = null;
    private String[] priceFormatsSuffixes = null;
    private int[] priceFormatsWholeDigits = null;
    private View[] priceItems;
    private boolean[] restoredModified;
    private String[] restoredPrices;
    private SliderSeparator separator;
    private boolean settingData = false;
    private OnClickListener submitClick;
    private PriceWatcher[] textChangedListeners;

    class C24751 implements Runnable {
        C24751() {
        }

        public void run() {
            GasPriceReport.this.nativeManager.CloseProgressPopup();
            GasPriceReport.this.setResult(ReportMenu.RESULT_CLOSE_REPORT_MENU);
            GasPriceReport.this.finish();
        }
    }

    class C24762 implements PriceFormatsListener {
        C24762() {
        }

        public void onComplete(String[] format) {
            int i;
            int numFormats = format.length;
            if (numFormats < GasPriceReport.priceItemIds.length) {
                numFormats = GasPriceReport.priceItemIds.length;
            }
            for (i = 0; i < numFormats; i++) {
                if (format[i] == null) {
                    numFormats = i;
                    break;
                }
            }
            GasPriceReport.this.priceFormatsWholeDigits = new int[numFormats];
            GasPriceReport.this.priceFormatsFractionalDigits = new int[numFormats];
            GasPriceReport.this.priceFormatsSuffixes = new String[numFormats];
            i = 0;
            while (i < numFormats) {
                GasPriceReport.this.priceFormatsWholeDigits[i] = 2;
                GasPriceReport.this.priceFormatsFractionalDigits[i] = 1;
                GasPriceReport.this.priceFormatsSuffixes[i] = "";
                if (format != null && i < format.length) {
                    String[] splitted = format[i].split("\\#+");
                    if (splitted.length > 1) {
                        GasPriceReport.this.priceFormatSeparator = splitted[1].charAt(0);
                        String[] splittedDigits = format[i].split("\\" + GasPriceReport.this.priceFormatSeparator);
                        if (splittedDigits.length != 2) {
                            Logger.e("GasPriceReport: too few or too many digit groups: " + splittedDigits.length);
                        } else {
                            int length;
                            GasPriceReport.this.priceFormatsWholeDigits[i] = splittedDigits[0].length();
                            int[] access$300 = GasPriceReport.this.priceFormatsFractionalDigits;
                            int length2 = splittedDigits[1].length();
                            if (splitted.length == 3) {
                                length = splitted[2].length();
                            } else {
                                length = 0;
                            }
                            access$300[i] = length2 - length;
                            if (splitted.length > 2) {
                                GasPriceReport.this.priceFormatsSuffixes[i] = splitted[2];
                            } else {
                                GasPriceReport.this.priceFormatsSuffixes[i] = "";
                            }
                        }
                    } else {
                        GasPriceReport.this.priceFormatSeparator = '#';
                        GasPriceReport.this.priceFormatsWholeDigits[i] = format.length;
                        GasPriceReport.this.priceFormatsFractionalDigits[i] = 0;
                    }
                }
                i++;
            }
        }
    }

    class C24773 implements Callback {
        C24773() {
        }

        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public void onDestroyActionMode(ActionMode mode) {
        }

        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
    }

    class C24784 implements OnClickListener {
        C24784() {
        }

        public void onClick(View v) {
            GasPriceReport.this.moveFocus(0);
        }
    }

    class C24795 implements OnClickListener {
        C24795() {
        }

        public void onClick(View v) {
            int nProds = Math.min(GasPriceReport.this.mProduct.labels.length, GasPriceReport.this.priceItems.length);
            float[] updatedPrices = new float[nProds];
            int[] updated = new int[nProds];
            for (int i = 0; i < nProds; i++) {
                String text = ((WazeEditText) GasPriceReport.this.priceItems[i].findViewById(C1283R.id.gasPriceItemEdit)).getText().toString();
                if (text.isEmpty()) {
                    updatedPrices[i] = -1.0f;
                    updated[i] = 2;
                } else {
                    try {
                        updatedPrices[i] = Float.parseFloat(text.replace(Character.toString(GasPriceReport.this.priceFormatSeparator), FileUploadSession.SEPARATOR));
                        if (Float.compare(updatedPrices[i], GasPriceReport.this.mProduct.prices[i]) == 0) {
                            updated[i] = 0;
                        } else {
                            updated[i] = 1;
                        }
                    } catch (Exception e) {
                        updatedPrices[i] = 0.0f;
                        updated[i] = 0;
                    }
                }
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_GASUPDATE);
            GasPriceReport.this.nativeManager.OpenProgressPopup(GasPriceReport.this.nativeManager.getLanguageString(218));
            GasPriceReport.this.nativeManager.setUpdateHandler(NativeManager.UH_GAS_PRICE_UPDATED, GasPriceReport.this.mHandler);
            GasPriceReport.this.mDtnMgr.setProductPrices(GasPriceReport.this.mSelectedIndex, updatedPrices, updated, nProds);
        }
    }

    class C24806 implements OnClickListener {
        C24806() {
        }

        public void onClick(View v) {
            for (View item : GasPriceReport.this.priceItems) {
                item.findViewById(C1283R.id.gasPriceItemEdit).clearFocus();
            }
        }
    }

    class C24817 implements OnClickListener {
        C24817() {
        }

        public void onClick(View v) {
            GasPriceReport.this.finish();
        }
    }

    class C24828 implements OnClickListener {
        C24828() {
        }

        public void onClick(View v) {
            GasPriceReport.this.finish();
        }
    }

    class C24839 implements OnTextResizeListener {
        boolean changed = false;
        float minSize = AutoScrollHelper.NO_MAX;

        C24839() {
        }

        public void onTextResize(TextView textView, float oldSize, float newSize) {
            int i;
            this.changed = false;
            for (i = 0; i < GasPriceReport.this.mProduct.labels.length; i++) {
                float size = ((AutoResizeTextView) GasPriceReport.this.findViewById(GasPriceReport.priceItemIds[i]).findViewById(C1283R.id.gasPriceItemLabel)).getCalculatedTextSize();
                if (size < this.minSize) {
                    this.minSize = size;
                    this.changed = true;
                }
            }
            if (this.changed) {
                for (i = 0; i < GasPriceReport.this.mProduct.labels.length; i++) {
                    AutoResizeTextView tv = (AutoResizeTextView) GasPriceReport.this.findViewById(GasPriceReport.priceItemIds[i]).findViewById(C1283R.id.gasPriceItemLabel);
                    tv.forceTextSize(this.minSize);
                    tv.resizeText();
                }
                GasPriceReport.this.findViewById(C1283R.id.reportGasPrices).forceLayout();
            }
        }
    }

    private class PriceWatcher implements TextWatcher {
        WazeEditText et;
        String text;

        PriceWatcher(WazeEditText edit) {
            this.et = edit;
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            this.text = s.toString();
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (GasPriceReport.this.focusedItem == -1) {
                this.text = s.toString();
                return;
            }
            WazeSwitchView sw = (WazeSwitchView) GasPriceReport.this.findViewById(C1283R.id.reportGasAvailableSwitch);
            if (count <= before) {
                this.text = s.toString();
                if (this.text.length() > 0 && (this.text.charAt(this.text.length() - 1) == ',' || this.text.charAt(this.text.length() - 1) == FilenameUtils.EXTENSION_SEPARATOR)) {
                    this.text = this.text.substring(0, this.text.length() - 1);
                }
                if (this.text.length() == 0) {
                    sw.setValue(false);
                }
                modified();
                return;
            }
            String str = s.toString();
            sw.setValue(true);
            char newChar = str.charAt(str.length() - 1);
            if (!(GasPriceReport.this.priceItems[GasPriceReport.this.focusedItem].findViewById(C1283R.id.gasPriceItemIndicator).getVisibility() == 0)) {
                str = String.valueOf(newChar);
            }
            if ((newChar == FilenameUtils.EXTENSION_SEPARATOR || newChar == ',') && GasPriceReport.this.priceFormatsFractionalDigits[GasPriceReport.this.focusedItem] == 0) {
                modified();
                this.text = s.toString().substring(0, s.length() - 1) + GasPriceReport.this.priceFormatsSuffixes[GasPriceReport.this.focusedItem];
                next();
                return;
            }
            String separator;
            if (GasPriceReport.this.priceFormatSeparator == FilenameUtils.EXTENSION_SEPARATOR) {
                separator = "\\.";
            } else {
                separator = Character.toString(GasPriceReport.this.priceFormatSeparator);
            }
            String[] split = str.split(separator);
            if (split.length == 2) {
                if (newChar != ',' && newChar != FilenameUtils.EXTENSION_SEPARATOR) {
                    if (split[1].length() >= GasPriceReport.this.priceFormatsFractionalDigits[GasPriceReport.this.focusedItem]) {
                        if (split[1].length() > GasPriceReport.this.priceFormatsFractionalDigits[GasPriceReport.this.focusedItem]) {
                            this.text = split[0] + GasPriceReport.this.priceFormatSeparator + split[1].substring(0, GasPriceReport.this.priceFormatsFractionalDigits[GasPriceReport.this.focusedItem]) + GasPriceReport.this.priceFormatsSuffixes[GasPriceReport.this.focusedItem];
                        } else {
                            this.text = str + GasPriceReport.this.priceFormatsSuffixes[GasPriceReport.this.focusedItem];
                        }
                        modified();
                        next();
                        return;
                    }
                    this.text = str;
                }
            } else if (newChar != ',' && newChar != FilenameUtils.EXTENSION_SEPARATOR) {
                modified();
                if (str.length() < GasPriceReport.this.priceFormatsWholeDigits[GasPriceReport.this.focusedItem]) {
                    this.text = str;
                } else if (GasPriceReport.this.priceFormatsFractionalDigits[GasPriceReport.this.focusedItem] == 0) {
                    this.text = str + GasPriceReport.this.priceFormatsSuffixes[GasPriceReport.this.focusedItem];
                    next();
                } else {
                    this.text = str.substring(0, GasPriceReport.this.priceFormatsWholeDigits[GasPriceReport.this.focusedItem]) + GasPriceReport.this.priceFormatSeparator + str.substring(GasPriceReport.this.priceFormatsWholeDigits[GasPriceReport.this.focusedItem]);
                }
            } else if (this.text.length() > 0) {
                this.text += GasPriceReport.this.priceFormatSeparator;
            }
        }

        public void afterTextChanged(Editable s) {
            this.et.removeTextChangedListener(this);
            int pos = (this.et.getSelectionStart() + this.text.length()) - this.et.getText().length();
            this.et.setText(this.text);
            this.et.setSelection(pos);
            this.et.addTextChangedListener(this);
        }

        private void next() {
            if (GasPriceReport.this.focusedItem == GasPriceReport.this.priceFormatsSuffixes.length - 1) {
                GasPriceReport.this.hideKeyboard();
            } else {
                GasPriceReport.this.moveFocus(GasPriceReport.this.focusedItem + 1);
            }
        }

        private void modified() {
            if (!GasPriceReport.this.settingData) {
                GasPriceReport.this.priceItems[GasPriceReport.this.focusedItem].findViewById(C1283R.id.gasPriceItemIndicator).setVisibility(0);
            }
        }
    }

    public boolean myHandleMessage(Message msg) {
        if (msg.what != NativeManager.UH_GAS_PRICE_UPDATED) {
            return false;
        }
        this.nativeManager.unsetUpdateHandler(NativeManager.UH_GAS_PRICE_UPDATED, this.mHandler);
        this.nativeManager.CloseProgressPopup();
        Bundle b = msg.getData();
        String message = b.getString("text");
        if (message == null || message.isEmpty()) {
            message = b.getString("title");
        }
        this.nativeManager.OpenProgressIconPopup(message, "sign_up_big_v");
        postDelayed(new C24751(), 2000);
        return true;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setVisible(false);
        setContentView(C1283R.layout.report_gas_price_content);
        findViewById(C1283R.id.reportGasRoot).setVisibility(8);
        Parcelable[] stations = (Parcelable[]) getIntent().getExtras().getSerializable(NEARBY_STATIONS);
        if (stations == null) {
            finish();
            return;
        }
        this.mNearbyStations = new NearbyStation[stations.length];
        for (int i = 0; i < stations.length; i++) {
            this.mNearbyStations[i] = (NearbyStation) stations[i];
        }
        this.textChangedListeners = new PriceWatcher[priceItemIds.length];
        this.priceItems = new View[priceItemIds.length];
        this.previousPrices = new String[priceItemIds.length];
        if (savedInstanceState != null) {
            this.mSelectedIndex = savedInstanceState.getInt("selected");
        }
        this.nativeManager = NativeManager.getInstance();
        this.mDtnMgr = DriveToNativeManager.getInstance();
        init();
        if (this.mSelectedIndex == -1) {
            openGasStationSelectionActivity();
        } else {
            finishInitialization();
        }
    }

    public void onResume() {
        super.onResume();
        this.mDtnMgr.getPriceFormats(new C24762(), "GAS_STATION");
    }

    protected void init() {
        int i;
        for (i = 0; i < priceItemIds.length; i++) {
            this.priceItems[i] = findViewById(priceItemIds[i]);
            WazeEditText et = (WazeEditText) this.priceItems[i].findViewById(C1283R.id.gasPriceItemEdit);
            et.setRawInputType(3);
            et.setSelectionAllowed(false);
            et.setCustomSelectionActionModeCallback(new C24773());
            this.textChangedListeners[i] = new PriceWatcher(et);
            et.addTextChangedListener(this.textChangedListeners[i]);
        }
        ReportMenuButton rmb = (ReportMenuButton) findViewById(C1283R.id.reportCloseButton);
        rmb.setImageDrawable(getResources().getDrawable(C1283R.drawable.icon_report_gas));
        rmb.setBackgroundColor(ReportMenu.BG_COLOR_GAS_PRICES);
        rmb.skipAnimation();
        TextView tvEdit = (TextView) findViewById(C1283R.id.reportGasEdit);
        tvEdit.setText(DisplayStrings.displayString(DisplayStrings.DS_GAS_PRICES_EDIT));
        tvEdit.setOnClickListener(new C24784());
        this.priceItems = new View[priceItemIds.length];
        for (i = 0; i < priceItemIds.length; i++) {
            this.priceItems[i] = findViewById(priceItemIds[i]);
            ((WazeEditText) this.priceItems[i].findViewById(C1283R.id.gasPriceItemEdit)).setHint(DisplayStrings.displayString(DisplayStrings.DS_GAS_PRICES_UNAVAILABLE));
        }
        this.submitClick = new C24795();
        TextView tvSend = (TextView) findViewById(C1283R.id.reportGasSend);
        tvSend.setText(DisplayStrings.displayString(DisplayStrings.DS_GAS_PRICES_SEND));
        tvSend.setOnClickListener(this.submitClick);
        TextView tvSubmit = (TextView) findViewById(C1283R.id.reportGasEditSubmit);
        tvSubmit.setText(DisplayStrings.displayString(DisplayStrings.DS_GAS_PRICES_SUBMIT));
        tvSubmit.setOnClickListener(this.submitClick);
        ((TextView) findViewById(C1283R.id.reportGasAvailableLabel)).setText(DisplayStrings.displayString(DisplayStrings.DS_GAS_PRICES_AVAILABLE));
        findViewById(16908290).setOnClickListener(new C24806());
        findViewById(C1283R.id.reportCloseAll).setOnClickListener(new C24817());
        this.separator = (SliderSeparator) findViewById(C1283R.id.reportGasKeyboardFooterSeparator);
    }

    private void openGasStationSelectionActivity() {
        Intent selectStation = new Intent(this, NearbyGasStationsActivity.class);
        ArrayList<NearbyStation> nearbyStations = new ArrayList();
        for (NearbyStation nearbyStation : this.mNearbyStations) {
            nearbyStations.add(nearbyStation);
        }
        selectStation.putParcelableArrayListExtra(NEARBY_STATIONS, nearbyStations);
        startActivityForResult(selectStation, MainActivity.REPORT_MENU_PICK_ONE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == MainActivity.REPORT_MENU_PICK_ONE) {
            if (resultCode != -1 || data == null) {
                finish();
            } else {
                int selection = data.getIntExtra("selection", -1);
                if (selection >= 0) {
                    this.mSelectedIndex = selection;
                    finishInitialization();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void finishInitialization() {
        setVisible(true);
        NearbyStation mNearbyStation = this.mNearbyStations[this.mSelectedIndex];
        this.mDtnMgr.getProduct(this.mSelectedIndex, this);
        ((TextView) findViewById(C1283R.id.reportTitle)).setText(mNearbyStation.result);
        if (!mNearbyStation.icon.equals(DEFAULT_CATEGORY_ICON)) {
            findViewById(C1283R.id.reportCloseButton).setVisibility(8);
            View frame = findViewById(C1283R.id.reportLogoFrameRectangle);
            frame.setVisibility(0);
            frame.setOnClickListener(new C24828());
            ((ImageView) findViewById(C1283R.id.reportLogoRectangle)).setImageDrawable(ResManager.GetSkinDrawable(mNearbyStation.icon + ResManager.mImageExtension));
        }
    }

    public void onComplete(Product product) {
        int i;
        findViewById(C1283R.id.reportGasRoot).setVisibility(0);
        this.mProduct = product;
        NativeManager natMgr = NativeManager.getInstance();
        String gasPriceString = natMgr.getLanguageString(205);
        if (!(product.currency == null || product.currency.isEmpty())) {
            gasPriceString = gasPriceString + " (" + natMgr.getLanguageString(product.currency) + ")";
        }
        ((TextView) findViewById(C1283R.id.reportSubTitle)).setText(gasPriceString);
        this.priceItems = new View[priceItemIds.length];
        for (i = 0; i < priceItemIds.length; i++) {
            this.priceItems[i] = findViewById(priceItemIds[i]);
        }
        OnTextResizeListener listener = new C24839();
        this.settingData = true;
        for (i = 0; i < this.mProduct.labels.length; i++) {
            View item = findViewById(priceItemIds[i]);
            WazeEditText et = (WazeEditText) item.findViewById(C1283R.id.gasPriceItemEdit);
            AutoResizeTextView tv = (AutoResizeTextView) item.findViewById(C1283R.id.gasPriceItemLabel);
            View change = item.findViewById(C1283R.id.gasPriceItemIndicator);
            View border = item.findViewById(C1283R.id.gasPriceItemBorder);
            change.setVisibility(8);
            border.setVisibility(8);
            if (product.prices[i] > 0.0f) {
                et.setText(UpdatePriceActivity.padWithZeroRightToPeriod(product.formats[i], product.prices[i]).replace(',', this.priceFormatSeparator).replace(FilenameUtils.EXTENSION_SEPARATOR, this.priceFormatSeparator));
            } else {
                et.setText("");
            }
            tv.setText(this.mProduct.labels[i]);
            tv.setOnResizeListener(listener);
        }
        for (i = this.mProduct.labels.length; i < priceItemIds.length; i++) {
            findViewById(priceItemIds[i]).setVisibility(8);
        }
        if (this.restoredPrices != null) {
            this.settingData = true;
            for (i = 0; i < this.priceItems.length; i++) {
                ((WazeEditText) this.priceItems[i].findViewById(C1283R.id.gasPriceItemEdit)).setText(this.restoredPrices[i]);
                this.priceItems[i].findViewById(C1283R.id.gasPriceItemIndicator).setVisibility(this.restoredModified[i] ? 0 : 8);
            }
            this.settingData = false;
            this.restoredPrices = null;
            this.restoredModified = null;
        }
        this.settingData = false;
        TextView updatedBy = (TextView) findViewById(C1283R.id.reportGasPriceUserUpdated);
        ReportMenuButton updatedByPic = (ReportMenuButton) findViewById(C1283R.id.reportGasPriceUserRmb);
        if (product.lastUpdated == -1 || product.updatedBy.isEmpty()) {
            updatedBy.setVisibility(4);
            updatedByPic.setVisibility(4);
        } else {
            String lastUpdate = AddressPreviewActivity.formatUpdatedTimeUserString(product.updatedBy, (((System.currentTimeMillis() / 1000) - ((long) product.lastUpdated)) / 3600) / 24);
            updatedBy.setVisibility(0);
            updatedBy.setText(lastUpdate);
            updatedByPic.setVisibility(0);
            int color = GenericTakeover.getUserBackgroundColor(product.updatedBy);
            updatedByPic.skipAnimation();
            updatedByPic.setImageResource(C1283R.drawable.happy);
            updatedByPic.setBackgroundColor(color);
        }
        this.contentView = findViewById(16908290);
        this.contentView.getViewTreeObserver().addOnGlobalFocusChangeListener(new OnGlobalFocusChangeListener() {
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (newFocus != GasPriceReport.this.findViewById(C1283R.id.reportGasAvailableSwitch)) {
                    GasPriceReport.this.onClearFocus(false);
                    for (int i = 0; i < GasPriceReport.this.priceItems.length; i++) {
                        View item = GasPriceReport.this.priceItems[i];
                        if (item.findViewById(C1283R.id.gasPriceItemEdit) == newFocus) {
                            GasPriceReport.this.focusedItem = i;
                            GasPriceReport.this.updateNavButtons();
                            GasPriceReport.this.onSetFocus(item);
                            return;
                        }
                    }
                }
            }
        });
        this.contentView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            private int mPreviousHeight;

            public void onGlobalLayout() {
                int newHeight = GasPriceReport.this.contentView.getHeight();
                if (this.mPreviousHeight != 0) {
                    if (this.mPreviousHeight > newHeight) {
                        GasPriceReport.this.findViewById(C1283R.id.reportGasFullscreenFooter).setVisibility(8);
                        GasPriceReport.this.findViewById(C1283R.id.reportGasPriceButtonsArea).setVisibility(8);
                        GasPriceReport.this.findViewById(C1283R.id.reportGasKeyboardIndicator).setVisibility(0);
                        GasPriceReport.this.findViewById(C1283R.id.reportGasKeyboardFooter).setVisibility(0);
                        GasPriceReport.this.findViewById(C1283R.id.reportLogoFramesLayout).setVisibility(8);
                    } else if (this.mPreviousHeight < newHeight) {
                        GasPriceReport.this.findViewById(C1283R.id.reportGasFullscreenFooter).setVisibility(0);
                        GasPriceReport.this.findViewById(C1283R.id.reportGasPriceButtonsArea).setVisibility(0);
                        GasPriceReport.this.findViewById(C1283R.id.reportGasKeyboardIndicator).setVisibility(8);
                        GasPriceReport.this.findViewById(C1283R.id.reportGasKeyboardFooter).setVisibility(8);
                        GasPriceReport.this.findViewById(C1283R.id.reportLogoFramesLayout).setVisibility(0);
                        if (GasPriceReport.this.focusedItem != -1) {
                            GasPriceReport.this.clearFocus();
                        }
                        if (GasPriceReport.this.separator != null) {
                            GasPriceReport.this.separator.setPosition(0.0f);
                            GasPriceReport.this.separator.setIndent(0);
                        }
                    }
                }
                this.mPreviousHeight = newHeight;
            }
        });
        this.contentView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GasPriceReport.this.clearFocus();
            }
        });
        clearFocus();
    }

    private void clearFocus() {
        if (this.focusedItem != -1) {
            this.priceItems[this.focusedItem].findViewById(C1283R.id.gasPriceItemEdit).clearFocus();
            onClearFocus(true);
            hideKeyboard();
        }
    }

    private void showKeyboard(View edit) {
        ((InputMethodManager) getSystemService("input_method")).showSoftInput(edit, 0);
    }

    private void hideKeyboard() {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
    }

    private void onClearFocus(boolean completeClear) {
        if (this.focusedItem != -1) {
            View oldItem = this.priceItems[this.focusedItem];
            oldItem.findViewById(C1283R.id.gasPriceItemBorder).setVisibility(8);
            WazeEditText et = (WazeEditText) oldItem.findViewById(C1283R.id.gasPriceItemEdit);
            et.setTextColor(getResources().getColor(C1283R.color.White));
            if (et.length() > 0) {
                String separator;
                String text = et.getText().toString();
                if (this.priceFormatSeparator == FilenameUtils.EXTENSION_SEPARATOR) {
                    separator = "\\.";
                } else {
                    separator = Character.toString(this.priceFormatSeparator);
                }
                String[] split = text.split(separator);
                if (this.priceFormatsFractionalDigits[this.focusedItem] != 0) {
                    int i;
                    if (split.length == 1) {
                        text = split[0] + this.priceFormatSeparator;
                        for (i = 0; i < this.priceFormatsFractionalDigits[this.focusedItem]; i++) {
                            text = text + '0';
                        }
                    } else {
                        text = split[0] + this.priceFormatSeparator + split[1];
                        for (i = split[1].length(); i < this.priceFormatsFractionalDigits[this.focusedItem]; i++) {
                            text = text + '0';
                        }
                    }
                }
                if (text.length() < ((this.priceFormatsWholeDigits[this.focusedItem] + this.priceFormatsFractionalDigits[this.focusedItem]) + 1) + this.priceFormatsSuffixes[this.focusedItem].length()) {
                    text = text + this.priceFormatsSuffixes[this.focusedItem];
                }
                et.removeTextChangedListener(this.textChangedListeners[this.focusedItem]);
                et.setText(text);
                et.addTextChangedListener(this.textChangedListeners[this.focusedItem]);
            }
            this.focusedItem = -1;
            if (completeClear) {
                findViewById(C1283R.id.reportGasFocusDummy).requestFocus();
            }
        }
    }

    private void moveFocus(int newItem) {
        onClearFocus(false);
        View edit = this.priceItems[newItem].findViewById(C1283R.id.gasPriceItemEdit);
        edit.requestFocus();
        onSetFocus(this.priceItems[newItem]);
        this.focusedItem = newItem;
        showKeyboard(edit);
    }

    private void onSetFocus(View newItem) {
        boolean z;
        newItem.findViewById(C1283R.id.gasPriceItemBorder).setVisibility(0);
        final WazeEditText et = (WazeEditText) newItem.findViewById(C1283R.id.gasPriceItemEdit);
        et.setTextColor(getResources().getColor(C1283R.color.ActiveGreen));
        et.setCursorVisible(false);
        et.post(new Runnable() {
            public void run() {
                et.setSelection(et.getText().length());
                et.setCursorVisible(true);
            }
        });
        final WazeSwitchView sw = (WazeSwitchView) findViewById(C1283R.id.reportGasAvailableSwitch);
        if (et.getText().toString().isEmpty()) {
            z = false;
        } else {
            z = true;
        }
        sw.setValue(z);
        sw.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean bOn) {
                if (!bOn) {
                    GasPriceReport.this.previousPrices[GasPriceReport.this.focusedItem] = et.getText().toString();
                    et.setText("");
                } else if (GasPriceReport.this.focusedItem != -1 && GasPriceReport.this.previousPrices[GasPriceReport.this.focusedItem] != null) {
                    et.setText(GasPriceReport.this.previousPrices[GasPriceReport.this.focusedItem]);
                    GasPriceReport.this.previousPrices[GasPriceReport.this.focusedItem] = null;
                    GasPriceReport.this.moveFocus(GasPriceReport.this.focusedItem);
                }
            }
        });
        sw.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                sw.toggle();
            }
        });
        int[] location = new int[2];
        et.getLocationOnScreen(location);
        if (this.separator != null) {
            this.separator.animateSelected((float) (location[0] + (et.getWidth() / 2)));
        }
    }

    private void updateNavButtons() {
        if (this.focusedItem == -1) {
            hideKeyboard();
            return;
        }
        View btnLeft = findViewById(C1283R.id.reportGasEditLeft);
        View btnRight = findViewById(C1283R.id.reportGasEditRight);
        if (this.focusedItem == 0) {
            btnLeft.setEnabled(false);
            btnLeft.setAlpha(0.3f);
        } else {
            btnLeft.setEnabled(true);
            btnLeft.setAlpha(1.0f);
            btnLeft.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    GasPriceReport.this.moveFocus(GasPriceReport.this.focusedItem - 1);
                }
            });
        }
        if (this.focusedItem == this.priceFormatsSuffixes.length - 1) {
            btnRight.setEnabled(false);
            btnRight.setAlpha(0.3f);
            return;
        }
        btnRight.setEnabled(true);
        btnRight.setAlpha(1.0f);
        btnRight.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                GasPriceReport.this.moveFocus(GasPriceReport.this.focusedItem + 1);
            }
        });
    }

    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putSerializable("stations", this.mNearbyStations);
        savedInstanceState.putInt("selected", this.mSelectedIndex);
        if (this.priceItems != null) {
            String[] prices = new String[this.priceItems.length];
            boolean[] modified = new boolean[this.priceItems.length];
            for (int i = 0; i < this.priceItems.length; i++) {
                prices[i] = ((WazeEditText) this.priceItems[i].findViewById(C1283R.id.gasPriceItemEdit)).getText().toString();
                modified[i] = this.priceItems[i].findViewById(C1283R.id.gasPriceItemIndicator).getVisibility() == 0;
            }
            savedInstanceState.putSerializable("prices", prices);
            savedInstanceState.putSerializable("modified", modified);
        }
        super.onSaveInstanceState(savedInstanceState);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        this.settingData = true;
        super.onRestoreInstanceState(savedInstanceState);
        this.settingData = false;
        this.restoredPrices = (String[]) savedInstanceState.getSerializable("prices");
        this.restoredModified = (boolean[]) savedInstanceState.getSerializable("modified");
    }
}
