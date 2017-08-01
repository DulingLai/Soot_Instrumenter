package com.waze.phone;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.settings.SettingsValue;
import com.waze.strings.DisplayStrings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class PhoneInputView extends FrameLayout {
    private static final long ERROR_ICON_APPEAR_TIMEOUT = 2000;
    public static final int RQ_CHOOSE_COUNTRY = 1000;
    private static List<String> countryCodes;
    private static List<String> countryDisplayStrings;
    private static List<SettingsValue> countrySettingsValues;
    private static List<String> labelDisplayStrings;
    private LinearLayout mCountryCodeButton;
    private TextView mCountryCodeLabel;
    private String mFormattedPhone;
    private PhoneInputViewListener mListener;
    private EditText mPhoneEditText;
    private ImageView mPhoneInvalidImage;
    private ImageView mPhoneValidImage;
    private int mSelectedCountryCodeIndex;
    private Runnable mShowErrorRunnable;

    public interface PhoneInputViewListener {
        void onPhoneValidChanged(boolean z);

        void onSendClick();
    }

    class C22541 implements OnClickListener {
        C22541() {
        }

        public void onClick(View v) {
            Activity currentActivity = AppService.getActiveActivity();
            currentActivity.startActivityForResult(new Intent(currentActivity, ChooseCountryPhoneActivity.class), 1000);
        }
    }

    class C22552 implements TextWatcher {
        C22552() {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void afterTextChanged(Editable s) {
            boolean phoneWasValid;
            boolean phoneIsValid;
            if (PhoneInputView.this.mFormattedPhone != null) {
                phoneWasValid = true;
            } else {
                phoneWasValid = false;
            }
            PhoneInputView.this.mFormattedPhone = PhoneInputView.getFormattedPhone(s.toString(), (String) PhoneInputView.countryCodes.get(PhoneInputView.this.mSelectedCountryCodeIndex));
            PhoneInputView.this.mPhoneInvalidImage.setVisibility(8);
            PhoneInputView.this.removeCallbacks(PhoneInputView.this.mShowErrorRunnable);
            if (PhoneInputView.this.mFormattedPhone != null) {
                phoneIsValid = true;
            } else {
                phoneIsValid = false;
            }
            if (!phoneIsValid) {
                PhoneInputView.this.postDelayed(PhoneInputView.this.mShowErrorRunnable, PhoneInputView.ERROR_ICON_APPEAR_TIMEOUT);
            }
            if (phoneWasValid != phoneIsValid) {
                PhoneInputView.this.adjustPhoneValidationSymbol();
                if (PhoneInputView.this.mListener != null) {
                    PhoneInputView.this.mListener.onPhoneValidChanged(phoneIsValid);
                }
            }
        }
    }

    class C22563 implements Runnable {
        C22563() {
        }

        public void run() {
            PhoneInputView.this.mPhoneInvalidImage.setVisibility(0);
        }
    }

    class C22574 implements OnEditorActionListener {
        C22574() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (!(actionId != 4 || PhoneInputView.this.mListener == null || PhoneInputView.this.mFormattedPhone == null)) {
                PhoneInputView.this.mListener.onSendClick();
            }
            return false;
        }
    }

    public PhoneInputView(Context context) {
        this(context, null);
    }

    public PhoneInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PhoneInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mFormattedPhone = null;
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.phone_input_view, null);
        this.mCountryCodeButton = (LinearLayout) content.findViewById(C1283R.id.btnCountryCode);
        this.mCountryCodeLabel = (TextView) content.findViewById(C1283R.id.lblCountryCode);
        this.mPhoneEditText = (EditText) content.findViewById(C1283R.id.phoneEditText);
        this.mPhoneValidImage = (ImageView) content.findViewById(C1283R.id.imgPhoneValid);
        this.mPhoneInvalidImage = (ImageView) content.findViewById(C1283R.id.imgPhoneError);
        this.mCountryCodeButton.setOnClickListener(new C22541());
        if (!isInEditMode()) {
            this.mPhoneEditText.setHint(DisplayStrings.displayString(DisplayStrings.DS_PHONE_NUMBER));
        }
        this.mPhoneEditText.addTextChangedListener(new C22552());
        this.mShowErrorRunnable = new C22563();
        if (!isInEditMode()) {
            setCountryCode(setupCountryCodes());
        }
        content.setPadding(0, 0, 0, 0);
        setPadding(0, 0, 0, 0);
        addView(content);
    }

    public void setKeyboardPerformsNext() {
        this.mPhoneEditText.setImeOptions(4);
        this.mPhoneEditText.setOnEditorActionListener(new C22574());
    }

    public void getFocus() {
        this.mPhoneEditText.requestFocus();
    }

    public EditText getEditText() {
        return this.mPhoneEditText;
    }

    private void adjustPhoneValidationSymbol() {
        this.mPhoneValidImage.setVisibility(this.mFormattedPhone != null ? 0 : 8);
    }

    public void setListener(PhoneInputViewListener listener) {
        this.mListener = listener;
    }

    public String getPhoneNumber() {
        return this.mFormattedPhone;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.mPhoneEditText.setText(phoneNumber);
    }

    public String getCountryCode() {
        return (String) countryCodes.get(this.mSelectedCountryCodeIndex);
    }

    public void setCountryCode(int countryCodeIndex) {
        this.mCountryCodeLabel.setText((CharSequence) labelDisplayStrings.get(countryCodeIndex));
        this.mSelectedCountryCodeIndex = countryCodeIndex;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static java.lang.String getFormattedPhone(java.lang.String r7, java.lang.String r8) {
        /*
        r1 = 0;
        r5 = com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance();	 Catch:{ NumberParseException -> 0x0031 }
        r4 = r5.parse(r7, r8);	 Catch:{ NumberParseException -> 0x0031 }
        r2 = r5.isValidNumber(r4);	 Catch:{ NumberParseException -> 0x0031 }
        if (r2 != 0) goto L_0x0010;
    L_0x000f:
        return r1;
    L_0x0010:
        r6 = com.waze.NativeManager.getInstance();	 Catch:{ NumberParseException -> 0x0031 }
        r6 = r6.ValidateMobileTypeNTV();	 Catch:{ NumberParseException -> 0x0031 }
        if (r6 == 0) goto L_0x002a;
    L_0x001a:
        r3 = r5.getNumberType(r4);	 Catch:{ NumberParseException -> 0x0031 }
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE;	 Catch:{ NumberParseException -> 0x0031 }
        if (r3 == r6) goto L_0x002a;
    L_0x0022:
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.MOBILE;	 Catch:{ NumberParseException -> 0x0031 }
        if (r3 == r6) goto L_0x002a;
    L_0x0026:
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER;	 Catch:{ NumberParseException -> 0x0031 }
        if (r3 != r6) goto L_0x000f;
    L_0x002a:
        r6 = com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat.E164;	 Catch:{ NumberParseException -> 0x0031 }
        r1 = r5.format(r4, r6);	 Catch:{ NumberParseException -> 0x0031 }
        goto L_0x000f;
    L_0x0031:
        r0 = move-exception;
        goto L_0x000f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.phone.PhoneInputView.getFormattedPhone(java.lang.String, java.lang.String):java.lang.String");
    }

    private static int setupCountryCodes() {
        int selectedCodeIndex;
        String selectedCountryZipCode = getSelectedCountryZipCode();
        int i;
        if (countrySettingsValues == null) {
            List<String> supportedRegionList = new ArrayList(PhoneNumberUtil.getInstance().getSupportedRegions());
            selectedCodeIndex = TextUtils.isEmpty(selectedCountryZipCode) ? 0 : -1;
            countrySettingsValues = new ArrayList();
            countryCodes = new ArrayList(supportedRegionList);
            countryDisplayStrings = new ArrayList();
            labelDisplayStrings = new ArrayList();
            i = 0;
            while (i < supportedRegionList.size()) {
                String countryCode = (String) supportedRegionList.get(i);
                int countryNumber = PhoneNumberUtil.getInstance().getCountryCodeForRegion(countryCode);
                if (!TextUtils.isEmpty(selectedCountryZipCode) && countryCode.equals(selectedCountryZipCode)) {
                    selectedCodeIndex = i;
                }
                String countryName = new Locale("", countryCode).getDisplayCountry();
                String displayString = String.format(Locale.US, "%s (%d)", new Object[]{countryName, Integer.valueOf(countryNumber)});
                String labelDisplayString = String.format(Locale.US, "%s (+%d)", new Object[]{countryCode, Integer.valueOf(countryNumber)});
                countrySettingsValues.add(new SettingsValue(String.valueOf(i), displayString, selectedCodeIndex == i));
                countryDisplayStrings.add(displayString);
                labelDisplayStrings.add(labelDisplayString);
                i++;
            }
            Collections.sort(countrySettingsValues, SettingsValue.comparator);
        } else {
            selectedCodeIndex = 0;
            for (i = 0; i < countryCodes.size(); i++) {
                if (((String) countryCodes.get(i)).equals(selectedCountryZipCode)) {
                    selectedCodeIndex = i;
                    break;
                }
            }
        }
        if (selectedCodeIndex != -1) {
            return selectedCodeIndex;
        }
        Log.e("PhoneInputView", "could not find selected country code!");
        ((SettingsValue) countrySettingsValues.get(0)).isSelected = true;
        return 0;
    }

    private static String getSelectedCountryZipCode() {
        return ((TelephonyManager) AppService.getActiveActivity().getSystemService("phone")).getSimCountryIso().toUpperCase();
    }

    public static SettingsValue[] getCountryCodes() {
        if (countrySettingsValues == null) {
            setupCountryCodes();
        }
        SettingsValue[] result = new SettingsValue[countrySettingsValues.size()];
        countrySettingsValues.toArray(result);
        return result;
    }
}
