package com.waze.settings;

import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.provider.CalendarContract.Calendars;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.facebook.appevents.AppEventsConstants;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SettingsCalendarSelectionActivity extends ActivityBase {
    public static final String[] CALENDAR_PROJECTION = new String[]{"_id", "calendar_displayName", "account_name", "ownerAccount", "visible"};
    private Account[] mAccounts;
    private String[] mExcludedAddresses;
    private String mExcludedAddressesString;
    private NativeManager mNM = NativeManager.getInstance();

    class C26261 extends BaseAdapter {
        C26261() {
        }

        public int getCount() {
            return SettingsCalendarSelectionActivity.this.mAccounts.length;
        }

        public Object getItem(int position) {
            return null;
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {
            boolean z = true;
            WazeSettingsView calendarItem = null;
            SettingsTitleText headerItem = null;
            if (convertView != null) {
                if (SettingsCalendarSelectionActivity.this.mAccounts[position].header && (convertView instanceof SettingsTitleText)) {
                    headerItem = (SettingsTitleText) convertView;
                } else if (SettingsCalendarSelectionActivity.this.mAccounts[position].header || !(convertView instanceof WazeSettingsView)) {
                    convertView = null;
                } else {
                    calendarItem = (WazeSettingsView) convertView;
                }
            }
            if (convertView == null) {
                if (SettingsCalendarSelectionActivity.this.mAccounts[position].header) {
                    headerItem = new SettingsTitleText(SettingsCalendarSelectionActivity.this, true);
                    convertView = headerItem;
                } else {
                    View calendarItem2 = new WazeSettingsView(SettingsCalendarSelectionActivity.this);
                    convertView = calendarItem2;
                    calendarItem2.setType(2);
                }
            }
            Account ac = SettingsCalendarSelectionActivity.this.mAccounts[position];
            if (ac.header) {
                headerItem.setText(ac.displayName);
                if (position != 0) {
                    z = false;
                }
                headerItem.setTop(z);
            } else {
                if (position < SettingsCalendarSelectionActivity.this.mAccounts.length - 1 && SettingsCalendarSelectionActivity.this.mAccounts[position + 1] != null && SettingsCalendarSelectionActivity.this.mAccounts[position + 1].header) {
                    calendarItem.setPosition(2);
                } else if (position == 0 || !SettingsCalendarSelectionActivity.this.mAccounts[position - 1].header) {
                    calendarItem.setPosition(0);
                } else {
                    calendarItem.setPosition(1);
                }
                calendarItem.setText(ac.displayName);
                calendarItem.setIcon(SettingsCalendarSelectionActivity.this.getResources().getDrawable(C1283R.drawable.list_icon_calendar));
                calendarItem.setValue(ac.enabled);
                calendarItem.setOnChecked(new SettingsToggleCallback() {
                    public void onToggle(boolean bOn) {
                        SettingsCalendarSelectionActivity.this.mAccounts[position].enabled = bOn;
                    }
                });
            }
            return convertView;
        }
    }

    class Account {
        String account;
        String displayName;
        boolean enabled;
        boolean header;
        String id;
        String ownerAccount;
        boolean primary;

        Account(String id, String displayName, String account, String ownerAccount) {
            this.id = id;
            this.displayName = displayName;
            this.account = account;
            this.ownerAccount = ownerAccount;
        }
    }

    private Drawable getIconForAccount(String type) {
        for (AuthenticatorDescription description : AccountManager.get(this).getAuthenticatorTypes()) {
            if (description.type.equals(type)) {
                return getPackageManager().getDrawable(description.packageName, description.iconId, null);
            }
        }
        return null;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_calendar_selection);
        ContentResolver cr = getContentResolver();
        if (ActivityCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.READ_CALENDAR") != 0) {
            Logger.e("SettingsCalendarSelectionActivity has no permission to read calendars");
            finish();
            return;
        }
        Cursor calendarCurr = cr.query(Calendars.CONTENT_URI, CALENDAR_PROJECTION, null, null, null);
        if (calendarCurr == null) {
            Logger.e("SettingsCalendarSelectionActivity failed to query calendars");
            finish();
            return;
        }
        Map<String, ArrayList<Account>> accounts = new HashMap();
        this.mExcludedAddressesString = this.mNM.getExcludedCalendarsNTV();
        this.mExcludedAddresses = this.mExcludedAddressesString.split(",");
        int primaryNum = 0;
        int subAccountNum = 0;
        boolean unfinished = calendarCurr.moveToFirst();
        while (unfinished) {
            Account ac;
            String calendarId = calendarCurr.getString(0);
            String displayName = calendarCurr.getString(1);
            String account = calendarCurr.getString(2);
            String ownerAccount = calendarCurr.getString(3);
            if (!calendarCurr.getString(4).equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                unfinished = calendarCurr.moveToNext();
            } else if (ownerAccount == null || account == null) {
                Logger.w("Calendar: skipping account. ownerAccount is " + ownerAccount + ", account is " + account + ",displayName is " + displayName);
                unfinished = calendarCurr.moveToNext();
            } else {
                boolean primary = ownerAccount.equals(account);
                if (primary) {
                    displayName = this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SELECTION_MAIN_CALENDAR);
                    primaryNum++;
                } else {
                    subAccountNum++;
                }
                ac = new Account(calendarId, displayName, account, ownerAccount);
                ac.enabled = true;
                ac.primary = primary;
                for (String split : this.mExcludedAddresses) {
                    String[] ex = split.split(DriveToNativeManager.IGNORED_CALENDAR_DELIMITER);
                    if (ex.length == 2 && ex[0].equals(ac.account) && ex[1].equals(ac.ownerAccount)) {
                        ac.enabled = false;
                    }
                }
                ArrayList<Account> arr = (ArrayList) accounts.get(ac.account);
                if (arr == null) {
                    arr = new ArrayList();
                    accounts.put(ac.account, arr);
                }
                arr.add(ac);
                unfinished = calendarCurr.moveToNext();
            }
        }
        this.mAccounts = new Account[(((primaryNum * 2) + subAccountNum) + 1)];
        int index = 0;
        for (String id : accounts.keySet()) {
            Iterator it = ((ArrayList) accounts.get(id)).iterator();
            while (it.hasNext()) {
                ac = (Account) it.next();
                if (ac.primary) {
                    Account header = new Account("", ac.account, ac.account, ac.ownerAccount);
                    header.header = true;
                    int index2 = index + 1;
                    this.mAccounts[index] = header;
                    index = index2 + 1;
                    this.mAccounts[index2] = ac;
                }
            }
            it = ((ArrayList) accounts.get(id)).iterator();
            while (it.hasNext()) {
                ac = (Account) it.next();
                if (!ac.primary) {
                    index2 = index + 1;
                    this.mAccounts[index] = ac;
                    index = index2;
                }
            }
        }
        this.mAccounts[index] = new Account("", "", "", "");
        this.mAccounts[index].header = true;
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_CALENDAR_SETTINGS_CALENDARS);
        ((ListView) findViewById(C1283R.id.calendarsListView)).setAdapter(new C26261());
    }

    protected void onDestroy() {
        StringBuilder sb = new StringBuilder();
        for (Account ac : this.mAccounts) {
            if (!(ac == null || ac.enabled || ac.header)) {
                if (sb.length() > 0) {
                    sb.append(",");
                }
                sb.append(DriveToNativeManager.getIgnoredCalendarId(ac.account, ac.ownerAccount));
            }
        }
        String excludeString = sb.toString();
        if (!excludeString.equals(this.mExcludedAddressesString)) {
            this.mNM.setExcludedCalendarsNTV(excludeString);
        }
        super.onDestroy();
    }
}
