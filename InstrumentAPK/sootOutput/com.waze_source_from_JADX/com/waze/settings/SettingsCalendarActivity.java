package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.internal.view.SupportMenu;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.WazeSettingsView.GetIndex;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.utils.TextViewLinkHandler;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class SettingsCalendarActivity extends ActivityBase {
    private NativeManager mNM = NativeManager.getInstance();

    class C26171 implements GetIndex {
        C26171() {
        }

        public int fromConfig() {
            return ConfigManager.getInstance().getConfigValueBool(87) ? 0 : 1;
        }
    }

    class C26193 implements OnClickListener {
        C26193() {
        }

        public void onClick(View v) {
            SettingsCalendarActivity.this.startActivityForResult(new Intent(SettingsCalendarActivity.this, SettingsCalendarSelectionActivity.class), 0);
        }
    }

    class C26204 extends TextViewLinkHandler {
        C26204() {
        }

        public void onLinkClick(String url) {
            if (url.equals("general")) {
                SettingsCalendarActivity.this.startActivity(new Intent(SettingsCalendarActivity.this, SettingsGeneralActivity.class));
            } else if (url.equals("notifications")) {
                SettingsCalendarActivity.this.startActivity(new Intent(SettingsCalendarActivity.this, SettingsNotificationActivity.class));
            }
        }
    }

    class C26235 implements OnClickListener {

        class C26221 implements DialogInterface.OnClickListener {

            class C26211 implements Runnable {
                C26211() {
                }

                public void run() {
                    SettingsCalendarActivity.this.mNM.CloseProgressPopup();
                }
            }

            C26221() {
            }

            public void onClick(DialogInterface dialog, int which) {
                if (which == 1) {
                    SettingsCalendarActivity.this.mNM.resetEvents();
                    SettingsCalendarActivity.this.mNM.OpenProgressPopup(SettingsCalendarActivity.this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_CLEAR_DONE));
                    SettingsCalendarActivity.this.postDelayed(new C26211(), 1000);
                }
            }
        }

        C26235() {
        }

        public void onClick(View v) {
            String body = SettingsCalendarActivity.this.mNM.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q);
            MsgBox.openConfirmDialogJavaCallback(SettingsCalendarActivity.this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_CLEAR), body, true, new C26221(), SettingsCalendarActivity.this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_CLEAR_CONFIRM), SettingsCalendarActivity.this.mNM.getLanguageString(344), -1);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_calendar);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_FUTURE_DRIVES_LIST_TITLE);
        ((SettingsTitleText) findViewById(C1283R.id.settingsCalendarTitleGeneral)).setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_MAIN_HEADER));
        setupSyncFlag();
        ((SettingsTitleText) findViewById(C1283R.id.settingsCalendarTitleReminders)).setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_SECOND_HEADER));
        final WazeSettingsView reminderType = (WazeSettingsView) findViewById(C1283R.id.settingsCalendarReminderType);
        reminderType.setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_REMINDER_OPTIONS));
        final String[] reminderLabels = new String[]{this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_REMINDER_OPTIONS_EARLY_AND_TTL), this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_REMINDER_OPTIONS_TTL_ONLY)};
        reminderType.initSelection(this, new C26171(), DisplayStrings.DS_CALENDAR_SETTINGS_REMINDER_OPTIONS, reminderLabels, reminderLabels, new SettingsDialogListener() {
            public void onComplete(int position) {
                ConfigManager.getInstance().setConfigValueBool(87, position == 0);
                reminderType.setValueText(reminderLabels[position]);
            }
        });
        reminderType.setEnabled(true);
        WazeSettingsView calendarSelectionView = (WazeSettingsView) findViewById(C1283R.id.settingsCalendarSelection);
        calendarSelectionView.setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_CALENDARS));
        calendarSelectionView.setOnClickListener(new C26193());
        WazeTextView reminderExplanation = (WazeTextView) findViewById(C1283R.id.settingsCalendarRemindersExplanation);
        reminderExplanation.setText(Html.fromHtml(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_SECOND_FOOTER_HTML_ANDROID)));
        reminderExplanation.setLinkTextColor(reminderExplanation.getTextColors());
        reminderExplanation.setMovementMethod(new C26204());
        ((WazeTextView) findViewById(C1283R.id.settingsCalendarExplanation)).setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_SECOND_FOOTER_ANDROID));
        ((SettingsTitleText) findViewById(C1283R.id.settingsCalendarTitleAdvanced)).setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_ADVANCED_HEADER));
        WazeSettingsView removeView = (WazeSettingsView) findViewById(C1283R.id.settingsCalendarRemoveLocations);
        removeView.setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_CLEAR));
        removeView.setKeyTextColor(SupportMenu.CATEGORY_MASK);
        removeView.setOnClickListener(new C26235());
        ((SettingsTitleText) findViewById(C1283R.id.settingsCalendarFooterAdvanced)).setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_ADVANCED_FOOTER));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == NativeManager.CALENDAR_ACCESS_REQUEST_CODE) {
            setupSyncFlag();
        } else if (resultCode == -1) {
            setResult(resultCode);
            finish();
        }
    }

    void setupSyncFlag() {
        WazeSettingsView syncView = (WazeSettingsView) findViewById(C1283R.id.settingsCalendarSync);
        WazeSettingsView reminderType = (WazeSettingsView) findViewById(C1283R.id.settingsCalendarReminderType);
        syncView.setText(this.mNM.getLanguageString(DisplayStrings.DS_CALENDAR_SETTINGS_SYNC));
        boolean on = this.mNM.calendarAccessEnabled() && this.mNM.calendarAuthorizedNTV();
        syncView.setValue(on);
        final WazeSettingsView calendarSelectionView = (WazeSettingsView) findViewById(C1283R.id.settingsCalendarSelection);
        calendarSelectionView.setEnabled(on);
        syncView.setOnChecked(new SettingsToggleCallback() {
            public void onToggle(boolean bOn) {
                if (bOn) {
                    NativeManager.getInstance().CalendaRequestAccessNTV();
                } else {
                    SettingsCalendarActivity.this.mNM.disableCalendarNTV();
                }
                calendarSelectionView.setEnabled(bOn);
            }
        });
    }
}
