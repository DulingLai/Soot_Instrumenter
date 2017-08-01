package com.waze.install;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingValueAdapter;
import com.waze.settings.SettingsValue;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class SelectCountryActivity extends ActivityBase {
    private static String[] countryCodes = new String[]{"AF", "AX", "AL", "DZ", "AS", AnalyticsEvents.ANALYTICS_EVENT_INFO_AD, "AO", "AI", "AQ", "AG", "AR", "AM", "AW", "AU", "AT", "AZ", "BS", "BH", "BD", "BB", "BY", "BE", "BZ", "BJ", "BM", "BT", "BO", "BQ", "BA", "BW", "BV", "BR", "IO", "BN", "BG", "BF", "BI", "KH", "CM", "CA", "CV", "KY", "CF", "TD", "CL", "CN", "CX", "CC", "CO", "KM", "CG", "CD", "CK", "CR", "CI", "HR", "CU", "CW", "CY", "CZ", "DK", "DJ", "DM", "DO", "EC", "EG", "SV", "GQ", "ER", "EE", "ET", "FK", "FO", "FJ", "FI", "FR", "GF", "PF", "TF", "GA", "GM", "GE", "DE", "GH", "GI", "GR", "GL", "GD", "GP", "GU", "GT", "GG", "GN", "GW", "GY", "HT", "HM", "VA", "HN", "HK", "HU", "IS", "IN", AnalyticsEvents.ANALYTICS_EVENT_INFO_ID, "IR", "IQ", "IE", "IM", "IL", "IT", "JM", "JP", "JE", "JO", "KZ", "KE", "KI", "KP", "KR", "KW", "KG", "LA", "LV", "LB", "LS", "LR", "LY", "LI", "LT", "LU", "MO", "MK", "MG", "MW", "MY", "MV", "ML", "MT", "MH", "MQ", "MR", "MU", "YT", "MX", "FM", "MD", "MC", "MN", "ME", "MS", "MA", "MZ", "MM", AnalyticsEvents.ANALYTICS_EVENT_NETWORK_MODE_NA, "NR", "NP", "NL", "NC", "NZ", "NI", "NE", "NG", "NU", "NF", "MP", AnalyticsEvents.ANALYTICS_NO, "OM", "PK", "PW", "PS", "PA", "PG", "PY", "PE", "PH", "PN", "PL", "PT", "PR", "QA", "RE", "RO", "RU", "RW", "BL", "SH", "KN", "LC", "MF", "PM", "VC", "WS", "SM", "ST", "SA", "SN", "RS", "SC", "SL", "SG", "SX", "SK", "SI", "SB", "SO", "ZA", "GS", "SS", "ES", "LK", "SD", "SR", "SJ", "SZ", "SE", "CH", "SY", "TW", "TJ", "TZ", "TH", "TL", "TG", "TK", AnalyticsEvents.ANALYTICS_EVENT_VALUE_TO, "TT", "TN", "TR", "TM", "TC", "TV", "UG", "UA", "AE", "GB", "US", "UM", "UY", "UZ", "VU", "VE", "VN", "VG", "VI", "WF", "EH", "YE", "ZM", "ZW"};
    private static String[] countryNames = new String[]{"Afghanistan", "Aland Islands", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla", "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia, Plurinational State of", "Bonaire, Sint Eustatius and Saba", "Bosnia and Herzegovina", "Botswana", "Bouvet Island", "Brazil", "British Indian Ocean Territory", "Brunei Darussalam", "Bulgaria", "Burkina Faso", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde", "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island", "Cocos (Keeling) Islands", "Colombia", "Comoros", "Congo", "Congo, the Democratic Republic of the", "Cook Islands", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Curaçao", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Ethiopia", "Falkland Islands (Malvinas)", "Faroe Islands", "Fiji", "Finland", "France", "French Guiana", "French Polynesia", "French Southern Territories", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece", "Greenland", "Grenada", "Guadeloupe", "Guam", "Guatemala", "Guernsey", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Heard Island and McDonald Islands", "Holy See (Vatican City State)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India", "Indonesia", "Iran, Islamic Republic of", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Jamaica", "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Kyrgyzstan", "Lao People's Democratic Republic", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macao", "Macedonia, the former Yugoslav Republic of", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Martinique", "Mauritania", "Mauritius", "Mayotte", "Mexico", "Micronesia, Federated States of", "Moldova, Republic of", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco", "Mozambique", "Myanmar", "Namibia", "Nauru", "Nepal", "Netherlands", "New Caledonia", "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Palestinian territories", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn", "Poland", "Portugal", "Puerto Rico", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saint Barthelemy", "Saint Helena, Ascension and Tristan da Cunha", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin (French part)", "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Sint Maarten (Dutch part)", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Georgia and the South Sandwich Islands", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Svalbard and Jan Mayen", "Swaziland", "Sweden", "Switzerland", "Syrian Arab Republic", "Taiwan", "Tajikistan", "Tanzania, United Republic of", "Thailand", "Timor-Leste", "Togo", "Tokelau", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "United States Minor Outlying Islands", "Uruguay", "Uzbekistan", "Vanuatu", "Venezuela, Bolivarian Republic of", "Viet Nam", "Virgin Islands, British", "Virgin Islands, U.S.", "Wallis and Futuna", "Western Sahara", "Yemen", "Zambia", "Zimbabwe"};
    private static String[] specialCountries = new String[]{"US", "IT", "FR", "GB", "ES"};
    private SettingsValue[] countries;
    private NativeManager nativeManager;

    class C18191 implements OnItemClickListener {
        C18191() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View arg1, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long arg3) throws  {
            InstallNativeManager $r3 = new InstallNativeManager();
            InstallNativeManager.setCountry(SelectCountryActivity.this.countries[$i0].value);
            SelectCountryActivity.this.setResult(-1);
            SelectCountryActivity.this.finish();
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        initCountries();
        NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_LOCATION_COUNTRY, null, null, true);
        this.nativeManager = AppService.getNativeManager();
        setContentView(C1283R.layout.settings_values);
        TitleBar $r5 = (TitleBar) findViewById(C1283R.id.theTitleBar);
        $r5.init(this, this.nativeManager.getLanguageString(getString(C1283R.string.selectCountry)), false);
        $r5.setUpButtonDisabled();
        SettingValueAdapter $r2 = r10;
        SettingValueAdapter r10 = new SettingValueAdapter(this);
        ListView $r7 = (ListView) findViewById(C1283R.id.settingsValueList);
        $r7.setAdapter($r2);
        $r7.setOnItemClickListener(new C18191());
        SettingsValue[] $r9 = this.countries;
        $r2.setValues($r9);
        $r7.invalidateViews();
    }

    private void initCountries() throws  {
        int $i0;
        String $r8 = ((TelephonyManager) getSystemService("phone")).getNetworkCountryIso();
        String $r3 = $r8;
        if ($r8 != null && $r8.length() > 0) {
            $r3 = $r8.toUpperCase();
        }
        ArrayList $r4 = new ArrayList();
        HashSet $r1 = new HashSet();
        HashMap $r2 = new HashMap();
        for (int $i1 = 0; $i1 < countryNames.length; $i1++) {
            $r2.put(countryCodes[$i1], new SettingsValue(countryCodes[$i1], countryNames[$i1], false));
        }
        if ($r3 != null && $r3.length() > 0) {
            addCountryToList($r4, $r1, $r2, $r3);
        }
        for (String $r32 : specialCountries) {
            addCountryToList($r4, $r1, $r2, $r32);
        }
        for ($i0 = 0; $i0 < countryNames.length; $i0++) {
            addCountryToList($r4, $r1, $r2, countryCodes[$i0]);
        }
        SettingsValue[] $r11 = new SettingsValue[$r4.size()];
        this.countries = (SettingsValue[]) $r4.toArray($r11);
    }

    private void addCountryToList(@Signature({"(", "Ljava/util/List", "<", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/lang/String;", ")V"}) List<SettingsValue> $r1, @Signature({"(", "Ljava/util/List", "<", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/lang/String;", ")V"}) Set<String> $r2, @Signature({"(", "Ljava/util/List", "<", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/lang/String;", ")V"}) Map<String, SettingsValue> $r3, @Signature({"(", "Ljava/util/List", "<", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Lcom/waze/settings/SettingsValue;", ">;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
        if (!$r2.contains($r4)) {
            $r1.add($r3.get($r4));
            $r2.add($r4);
        }
    }

    public void onBackPressed() throws  {
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }
}
