package com.waze.reports;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.PriceFormatListener;
import com.waze.navigate.DriveToNativeManager.ProductListener;
import com.waze.navigate.Product;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.TypingLockListener;
import com.waze.view.title.TitleBar;
import java.util.MissingFormatWidthException;

public class UpdatePriceActivity extends ActivityBase {
    private static final String PRICE_NOT_AVAILABLE = "N/A";
    private static String s_priceFormatString = null;
    private EditText currentEditText;
    private DriveToNativeManager driveToNativeManager;
    private boolean isPriceChanged = false;
    private boolean isTextBoxFilledByUser = true;
    protected boolean mIgnoredeletionCheck;
    private int mIndex;
    protected int nProducts = -1;
    OnClickListener onClickYesListener = new C25491();
    protected boolean overrideFilter = false;
    private Product product;
    protected boolean skipOnTextChanged = false;

    class C25491 implements OnClickListener {
        C25491() {
        }

        public void onClick(View v) {
            float[] updatedPrices = new float[UpdatePriceActivity.this.product.labels.length];
            int[] updated = new int[UpdatePriceActivity.this.product.labels.length];
            if (UpdatePriceActivity.this.product.labels.length == 4) {
                setData(updatedPrices, updated, 0, C1283R.id.updatePriceEdit1);
                setData(updatedPrices, updated, 1, C1283R.id.updatePriceEdit2);
                setData(updatedPrices, updated, 2, C1283R.id.updatePriceEdit3);
                setData(updatedPrices, updated, 3, C1283R.id.updatePriceEdit4);
            } else if (UpdatePriceActivity.this.product.labels.length == 3) {
                setData(updatedPrices, updated, 0, C1283R.id.updatePriceEdit1);
                setData(updatedPrices, updated, 1, C1283R.id.updatePriceEdit2);
                setData(updatedPrices, updated, 2, C1283R.id.updatePriceEdit3);
            } else if (UpdatePriceActivity.this.product.labels.length == 2) {
                setData(updatedPrices, updated, 0, C1283R.id.updatePriceEdit1);
                setData(updatedPrices, updated, 1, C1283R.id.updatePriceEdit2);
            } else {
                setData(updatedPrices, updated, 0, C1283R.id.updatePriceEdit1);
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_GASUPDATE);
            NativeManager nativeManager = AppService.getNativeManager();
            nativeManager.setUpdateHandler(NativeManager.UH_GAS_PRICE_UPDATED, UpdatePriceActivity.this.mHandler);
            UpdatePriceActivity.this.driveToNativeManager.setProductPrices(UpdatePriceActivity.this.mIndex, updatedPrices, updated, UpdatePriceActivity.this.nProducts);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceUpdated).setVisibility(8);
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceAreThesePrices)).setText(nativeManager.getLanguageString(218));
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceButtons).setVisibility(8);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceProgress).setVisibility(0);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceEdit1).setEnabled(false);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceEdit2).setEnabled(false);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceEdit3).setEnabled(false);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceEdit4).setEnabled(false);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceDummy).requestFocus();
            if (UpdatePriceActivity.this.currentEditText != null) {
                ((InputMethodManager) UpdatePriceActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(UpdatePriceActivity.this.currentEditText.getWindowToken(), 0);
            }
        }

        private void setData(float[] updatedPrices, int[] updated, int i, int resource) {
            String edited = ((EditText) UpdatePriceActivity.this.findViewById(resource)).getText().toString();
            if (edited.equals(UpdatePriceActivity.PRICE_NOT_AVAILABLE)) {
                updatedPrices[i] = -1.0f;
                updated[i] = 2;
                return;
            }
            try {
                updatedPrices[i] = Float.parseFloat(edited.replace(',', FilenameUtils.EXTENSION_SEPARATOR));
                if (Float.compare(updatedPrices[i], UpdatePriceActivity.this.product.prices[i]) == 0) {
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

    class C25512 implements ProductListener {

        class C25501 implements OnClickListener {
            C25501() {
            }

            public void onClick(View v) {
                ((TitleBar) UpdatePriceActivity.this.findViewById(C1283R.id.theTitleBar)).onCloseClicked();
            }
        }

        C25512() {
        }

        public void onComplete(Product product) {
            UpdatePriceActivity.this.nProducts = product.labels.length;
            UpdatePriceActivity.this.product = product;
            NativeManager nativeManager = AppService.getNativeManager();
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceName)).setText(product.name);
            ((ImageView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceIcon)).setImageDrawable(ResManager.GetSkinDrawable(product.icon + ResManager.mImageExtension));
            boolean foundAtLeastOne = false;
            if (UpdatePriceActivity.this.nProducts == 4) {
                foundAtLeastOne = (((false | UpdatePriceActivity.this.displayEdit(product, 0, C1283R.id.updatePriceEdit1)) | UpdatePriceActivity.this.displayEdit(product, 1, C1283R.id.updatePriceEdit2)) | UpdatePriceActivity.this.displayEdit(product, 2, C1283R.id.updatePriceEdit3)) | UpdatePriceActivity.this.displayEdit(product, 3, C1283R.id.updatePriceEdit4);
            } else if (UpdatePriceActivity.this.nProducts == 1) {
                foundAtLeastOne = false | UpdatePriceActivity.this.displayEdit(product, 0, C1283R.id.updatePriceEdit1);
            } else if (UpdatePriceActivity.this.nProducts == 2) {
                foundAtLeastOne = (false | UpdatePriceActivity.this.displayEdit(product, 0, C1283R.id.updatePriceEdit1)) | UpdatePriceActivity.this.displayEdit(product, 1, C1283R.id.updatePriceEdit2);
            } else if (UpdatePriceActivity.this.nProducts == 3) {
                foundAtLeastOne = ((false | UpdatePriceActivity.this.displayEdit(product, 0, C1283R.id.updatePriceEdit1)) | UpdatePriceActivity.this.displayEdit(product, 1, C1283R.id.updatePriceEdit2)) | UpdatePriceActivity.this.displayEdit(product, 2, C1283R.id.updatePriceEdit3);
            }
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceClose).setOnClickListener(new C25501());
            int[] fields = new int[]{C1283R.id.updatePrice1, C1283R.id.updatePrice2, C1283R.id.updatePrice3, C1283R.id.updatePrice4};
            int[] labels = new int[]{C1283R.id.updatePriceLabel1, C1283R.id.updatePriceLabel2, C1283R.id.updatePriceLabel3, C1283R.id.updatePriceLabel4};
            for (int i = 0; i < UpdatePriceActivity.this.nProducts; i++) {
                UpdatePriceActivity.this.findViewById(fields[i]).setVisibility(0);
                ((TextView) UpdatePriceActivity.this.findViewById(labels[i])).setText(product.labels[i]);
            }
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceCurrency1)).setText(product.currency);
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceCurrency2)).setText(product.currency);
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceCurrency3)).setText(product.currency);
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceCurrency4)).setText(product.currency);
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceCloseText)).setText(nativeManager.getLanguageString(354));
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceYesText)).setText(nativeManager.getLanguageString(DisplayStrings.DS_YES));
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNoText)).setText(nativeManager.getLanguageString(DisplayStrings.DS_NO));
            ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceAreThesePrices)).setText(nativeManager.getLanguageString(212));
            if (!foundAtLeastOne) {
                ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceAreThesePrices)).setText("");
                UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceYes).setVisibility(8);
                UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNo).setVisibility(8);
                UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceClose).setVisibility(0);
            }
            if (product.lastUpdated != -1) {
                ((TextView) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceUpdated)).setText(AddressPreviewActivity.formatUpdatedTimeUserString(product.updatedBy, (((System.currentTimeMillis() / 1000) - ((long) product.lastUpdated)) / 3600) / 24));
            } else {
                UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceUpdated).setVisibility(4);
            }
            UpdatePriceActivity.this.setEditTextChangedListener(C1283R.id.updatePriceEdit1, 0, C1283R.id.updatePriceEdit2);
            UpdatePriceActivity.this.setEditTextChangedListener(C1283R.id.updatePriceEdit2, 1, C1283R.id.updatePriceEdit3);
            UpdatePriceActivity.this.setEditTextChangedListener(C1283R.id.updatePriceEdit3, 2, C1283R.id.updatePriceEdit4);
            UpdatePriceActivity.this.setEditTextChangedListener(C1283R.id.updatePriceEdit4, 3, 0);
            setEditTextMaxLength(product);
        }

        private void setEditTextMaxLength(Product product) {
            int[] priceEdits = new int[]{C1283R.id.updatePriceEdit1, C1283R.id.updatePriceEdit2, C1283R.id.updatePriceEdit3, C1283R.id.updatePriceEdit4};
            for (int i = 0; i < UpdatePriceActivity.this.nProducts; i++) {
                ((EditText) UpdatePriceActivity.this.findViewById(priceEdits[i])).setFilters(new InputFilter[]{new LengthFilter(product.formats[i].length())});
            }
        }
    }

    class C25523 implements OnClickListener {
        C25523() {
        }

        public void onClick(View v) {
            EditText editText = (EditText) UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceEdit1);
            editText.requestFocus();
            ((InputMethodManager) UpdatePriceActivity.this.getSystemService("input_method")).showSoftInput(editText, 1);
        }
    }

    class C25534 implements OnClickListener {
        C25534() {
        }

        public void onClick(View v) {
            UpdatePriceActivity.this.focusNextField();
        }
    }

    class C25545 implements OnClickListener {
        C25545() {
        }

        public void onClick(View v) {
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons1).setVisibility(8);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons2).setVisibility(0);
        }
    }

    class C25556 implements OnClickListener {
        C25556() {
        }

        public void onClick(View v) {
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons1).setVisibility(0);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons2).setVisibility(8);
            if (UpdatePriceActivity.this.currentEditText != null) {
                UpdatePriceActivity.this.currentEditText.setText(UpdatePriceActivity.PRICE_NOT_AVAILABLE);
            }
            UpdatePriceActivity.this.findNextField();
        }
    }

    class C25567 implements OnClickListener {
        C25567() {
        }

        public void onClick(View v) {
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons1).setVisibility(0);
            UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons2).setVisibility(8);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.update_price);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, 209);
        this.mIndex = getIntent().getExtras().getInt("index");
        this.driveToNativeManager = DriveToNativeManager.getInstance();
        this.driveToNativeManager.getProduct(this.mIndex, new C25512());
        findViewById(C1283R.id.updatePriceNo).setOnClickListener(new C25523());
        findViewById(C1283R.id.updatePriceYes).setOnClickListener(this.onClickYesListener);
        setEditTextListener(C1283R.id.updatePriceEdit1, C1283R.id.updatePriceImage1, 0);
        setEditTextListener(C1283R.id.updatePriceEdit2, C1283R.id.updatePriceImage2, 1);
        setEditTextListener(C1283R.id.updatePriceEdit3, C1283R.id.updatePriceImage3, 2);
        setEditTextListener(C1283R.id.updatePriceEdit4, C1283R.id.updatePriceImage4, 3);
        findViewById(C1283R.id.updatePriceNext).setOnClickListener(new C25534());
        findViewById(C1283R.id.updatePriceNotOffered).setOnClickListener(new C25545());
        findViewById(C1283R.id.updatePriceRemoveYes).setOnClickListener(new C25556());
        findViewById(C1283R.id.updatePriceRemoveNo).setOnClickListener(new C25567());
        NativeManager nativeManager = AppService.getNativeManager();
        ((Button) findViewById(C1283R.id.updatePriceRemoveNo)).setText(nativeManager.getLanguageString(DisplayStrings.DS_NO));
        ((Button) findViewById(C1283R.id.updatePriceRemoveYes)).setText(nativeManager.getLanguageString(DisplayStrings.DS_YES));
        ((TextView) findViewById(C1283R.id.updatePriceRemoveText)).setText(nativeManager.getLanguageString(125));
        ((Button) findViewById(C1283R.id.updatePriceNotOffered)).setText(nativeManager.getLanguageString(DisplayStrings.DS_NOT_OFFERED));
        ((Button) findViewById(C1283R.id.updatePriceNext)).setText(nativeManager.getLanguageString(DisplayStrings.DS_NEXT));
    }

    protected void onDestroy() {
        AppService.getNativeManager().unsetUpdateHandler(NativeManager.UH_GAS_PRICE_UPDATED, this.mHandler);
        super.onDestroy();
    }

    protected void onResume() {
        super.onResume();
        findViewById(C1283R.id.updatePriceDummy).requestFocus();
    }

    private void setEditTextListener(int resource, int imageResource, int index) {
        final EditText editText = (EditText) findViewById(resource);
        final ImageView imageView = (ImageView) findViewById(imageResource);
        final int i = resource;
        final int i2 = index;
        editText.setOnFocusChangeListener(new OnFocusChangeListener() {

            class C25571 implements TypingLockListener {
                C25571() {
                }

                public void shouldLock() {
                    UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceDummy).requestFocus();
                }
            }

            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    AppService.getNativeManager().checkTypingLock(new C25571());
                    handleFocusIn(editText, imageView, view);
                    return;
                }
                handleFocusOut(i, i2, imageView, view);
            }

            private void handleFocusOut(int resource, int index, ImageView imageView, View view) {
                UpdatePriceActivity.this.isTextBoxFilledByUser = false;
                UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons).setVisibility(8);
                EditText editText = (EditText) view;
                String currentPrice = editText.getText().toString().replace(',', FilenameUtils.EXTENSION_SEPARATOR);
                if (isPriceAndFormattedOK(currentPrice)) {
                    if (!currentPrice.equalsIgnoreCase(UpdatePriceActivity.PRICE_NOT_AVAILABLE)) {
                        String priceZeroPadded = UpdatePriceActivity.padWithZeroRightToPeriod(UpdatePriceActivity.this.product.formats[index], Float.parseFloat(currentPrice));
                        UpdatePriceActivity.this.skipOnTextChanged = true;
                        editText.setText(priceZeroPadded);
                    }
                    imageView.setImageResource(C1283R.drawable.price_bg_v);
                    return;
                }
                restorePriceFromPlaceHolder(resource, index, imageView);
            }

            private boolean isPriceAndFormattedOK(String currentPrice) {
                return !currentPrice.equals("") && (currentPrice.equalsIgnoreCase(UpdatePriceActivity.PRICE_NOT_AVAILABLE) || UpdatePriceActivity.this.isParseFloatOk(currentPrice));
            }

            private void handleFocusIn(EditText editText, ImageView imageView, View view) {
                UpdatePriceActivity.this.currentEditText = editText;
                UpdatePriceActivity.this.findViewById(C1283R.id.updatePriceNextButtons).setVisibility(0);
                imageView.setImageResource(C1283R.drawable.price_bg_sel);
                ((EditText) view).setHint(((EditText) view).getText().toString());
                ((EditText) view).setText("");
                UpdatePriceActivity.this.isTextBoxFilledByUser = true;
                ((InputMethodManager) UpdatePriceActivity.this.getSystemService("input_method")).showSoftInput(editText, 1);
            }

            private void restorePriceFromPlaceHolder(int resource, int index, ImageView imageView) {
                UpdatePriceActivity.this.mIgnoredeletionCheck = true;
                imageView.setImageResource(C1283R.drawable.price_bg);
                UpdatePriceActivity.this.displayEdit(UpdatePriceActivity.this.product, index, resource);
            }
        });
    }

    private void setEditTextChangedListener(int resource, final int index, int nextResource) {
        final EditText editText = (EditText) findViewById(resource);
        editText.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence seq, int start, int before, int count) {
                if (UpdatePriceActivity.this.skipOnTextChanged) {
                    UpdatePriceActivity.this.skipOnTextChanged = false;
                    return;
                }
                UpdatePriceActivity.this.priceChanged();
                String str = editText.getText().toString();
                if (UpdatePriceActivity.this.product.formats.length <= index) {
                    Log.d(Logger.TAG, "Index is out from product format");
                    return;
                }
                if (UpdatePriceActivity.this.mIgnoredeletionCheck) {
                    UpdatePriceActivity.this.mIgnoredeletionCheck = false;
                } else if (deleteAllInputIfUserShortenedInput(editText, before, count)) {
                    return;
                }
                if (!str.equalsIgnoreCase(UpdatePriceActivity.PRICE_NOT_AVAILABLE)) {
                    if (str.trim().length() == UpdatePriceActivity.this.product.formats[index].length()) {
                        if (UpdatePriceActivity.this.isTextBoxFilledByUser) {
                            UpdatePriceActivity.this.focusNextField();
                        }
                    } else if ((start > seq.length() - 1 || Character.isDigit(seq.charAt(start))) && str.trim().length() > 0) {
                        fitInputIntoFormat(index, editText, str);
                    }
                }
            }

            private boolean deleteAllInputIfUserShortenedInput(EditText editText, int before, int count) {
                if (before <= count) {
                    return false;
                }
                editText.setText("");
                return true;
            }

            private void fitInputIntoFormat(int index, EditText editText, String str) {
                if (str.trim().length() <= UpdatePriceActivity.this.product.formats[index].length()) {
                    char nextFormatChar = UpdatePriceActivity.this.product.formats[index].charAt(str.trim().length());
                    if (nextFormatChar != '#') {
                        UpdatePriceActivity.this.overrideFilter = true;
                        editText.append("" + nextFormatChar);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Index out of bound: tried to access char at: " + index + " in format: " + UpdatePriceActivity.this.product.formats[index] + " to compare with price: " + str);
                editText.setText("");
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        });
        editText.setFilters(new InputFilter[]{new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                if (UpdatePriceActivity.this.overrideFilter) {
                    UpdatePriceActivity.this.overrideFilter = false;
                    return null;
                } else if (source.equals(FileUploadSession.SEPARATOR) || source.equals(",")) {
                    return "";
                } else {
                    return null;
                }
            }
        }});
    }

    protected boolean findNextField() {
        if (this.currentEditText == null) {
            return false;
        }
        int[] fields = new int[]{C1283R.id.updatePriceEdit1, C1283R.id.updatePriceEdit2, C1283R.id.updatePriceEdit3, C1283R.id.updatePriceEdit4};
        boolean found = false;
        int i = 0;
        while (this.currentEditText.getId() != fields[i]) {
            i++;
        }
        EditText editText = null;
        while (null == null) {
            i++;
            if (i >= fields.length || i >= this.nProducts) {
                break;
            }
            editText = (EditText) findViewById(fields[i]);
            if (!editText.getText().toString().equals(PRICE_NOT_AVAILABLE)) {
                found = true;
                break;
            }
        }
        if (!found) {
            return false;
        }
        editText.requestFocus();
        return true;
    }

    private void priceChanged() {
        if (!this.isPriceChanged) {
            this.isPriceChanged = true;
            ((TextView) findViewById(C1283R.id.updatePriceAreThesePrices)).setText("");
            findViewById(C1283R.id.updatePriceYes).setVisibility(8);
            findViewById(C1283R.id.updatePriceNo).setVisibility(8);
            findViewById(C1283R.id.updatePriceClose).setVisibility(8);
            NativeManager nativeManager = NativeManager.getInstance();
            TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
            titleBar.setCloseImageResource(C1283R.drawable.done_selector);
            titleBar.setCloseEnabled(false);
            titleBar.setCloseText(nativeManager.getLanguageString(293));
            titleBar.setOnClickCloseListener(this.onClickYesListener);
        }
    }

    public static String getPriceFormatString(DriveToNativeManager nm, String ProductID) {
        if (s_priceFormatString != null) {
            return s_priceFormatString;
        }
        DriveToNativeManager.getInstance().getPriceFormat(new PriceFormatListener() {
            public void onComplete(String priceFormat) {
                int nDigitsAfterDotInPrice = UpdatePriceActivity.getNumberOfDigits(priceFormat);
                if (nDigitsAfterDotInPrice <= 2 || nDigitsAfterDotInPrice > 5) {
                    UpdatePriceActivity.s_priceFormatString = "%2.2f";
                } else {
                    UpdatePriceActivity.s_priceFormatString = "%2." + nDigitsAfterDotInPrice + "f";
                }
            }
        }, ProductID);
        return "%2.2f";
    }

    private static int getNumberOfDigits(String priceFormat) {
        int pos = priceFormat.indexOf(46);
        if (pos == -1) {
            return 0;
        }
        return (priceFormat.length() - pos) - 1;
    }

    private boolean displayEdit(Product product, int index, int resource) {
        if (product == null) {
            return false;
        }
        EditText editText = (EditText) findViewById(resource);
        if (product.prices.length <= index || ((int) (product.prices[index] * 100.0f)) <= 0) {
            editText.setText(PRICE_NOT_AVAILABLE);
            return false;
        }
        editText.setText(padWithZeroRightToPeriod(product.formats[index], product.prices[index]));
        return true;
    }

    public static String padWithZeroRightToPeriod(String serverFormat, float unformattedNumber) {
        String formattedPrice = String.format("%." + getNumberOfDigitsAfterPeriod(serverFormat) + "f", new Object[]{Float.valueOf(unformattedNumber)});
        if (serverFormat.indexOf(44) != -1) {
            return formattedPrice.replace(FilenameUtils.EXTENSION_SEPARATOR, ',');
        }
        return formattedPrice;
    }

    public static String padWithZeros(String serverFormat, float unformattedNumber) {
        int nPre = getNumberOfDigitsBeforePeriod(serverFormat);
        String floatingFormat = "%" + nPre + FileUploadSession.SEPARATOR + getNumberOfDigitsAfterPeriod(serverFormat) + "f";
        String formattedPrice = null;
        try {
            formattedPrice = String.format(floatingFormat, new Object[]{Float.valueOf(unformattedNumber)});
            if (serverFormat.indexOf(44) != -1) {
                formattedPrice = formattedPrice.replace(FilenameUtils.EXTENSION_SEPARATOR, ',');
            }
        } catch (MissingFormatWidthException e) {
        }
        return formattedPrice;
    }

    public static int getNumberOfDigitsAfterPeriod(String format) {
        int pos = format.indexOf(46);
        if (pos != -1) {
            return (format.length() - pos) - 1;
        }
        pos = format.indexOf(44);
        if (pos == -1) {
            return 0;
        }
        return (format.length() - pos) - 1;
    }

    public static int getNumberOfDigitsBeforePeriod(String format) {
        int pos = format.indexOf(46);
        if (pos != -1) {
            return pos;
        }
        pos = format.indexOf(44);
        if (pos == -1) {
            return 0;
        }
        return pos;
    }

    private void updateActivityDone(String title, String text) {
        NativeManager nativeManager = AppService.getNativeManager();
        findViewById(C1283R.id.updatePriceProgress).setVisibility(8);
        ((TextView) findViewById(C1283R.id.updatePriceAreThesePrices)).setText(nativeManager.getLanguageString(title));
        ((TextView) findViewById(C1283R.id.updatePriceYouveEarned)).setText(nativeManager.getLanguageString(text));
        findViewById(C1283R.id.updatePriceYouveEarned).setVisibility(0);
        TitleBar titleBar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        titleBar.setCloseImageResource(C1283R.drawable.close_selector);
        titleBar.setCloseEnabled(true);
        titleBar.setCloseText(null);
        titleBar.setOnClickCloseListener(null);
    }

    private void focusNextField() {
        if (!findNextField()) {
            findViewById(C1283R.id.updatePriceDummy).requestFocus();
            if (this.currentEditText != null) {
                ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(this.currentEditText.getWindowToken(), 0);
            }
        }
    }

    private boolean isParseFloatOk(String value) {
        try {
            Float.parseFloat(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    protected boolean myHandleMessage(Message msg) {
        if (msg.what != NativeManager.UH_GAS_PRICE_UPDATED) {
            return super.myHandleMessage(msg);
        }
        AppService.getNativeManager().unsetUpdateHandler(NativeManager.UH_GAS_PRICE_UPDATED, this.mHandler);
        Bundle b = msg.getData();
        updateActivityDone(b.getString("title"), b.getString("text"));
        return true;
    }
}
