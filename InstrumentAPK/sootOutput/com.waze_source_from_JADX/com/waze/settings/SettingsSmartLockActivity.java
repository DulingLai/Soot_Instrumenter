package com.waze.settings;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.install.SmartLockManager;
import com.waze.install.SmartLockManager.SmartLockHasCredentialsListener;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;

public class SettingsSmartLockActivity extends ActivityBase {
    private boolean mHasSmartLock;
    private boolean mIsLoading;
    private WazeSettingsView mSmartLockEnable;

    class C27601 implements OnClickListener {

        class C27591 implements Runnable {
            C27591() {
            }

            public void run() {
                SettingsSmartLockActivity.this.adjustSmartLockButton();
            }
        }

        C27601() {
        }

        public void onClick(View v) {
            if (!SettingsSmartLockActivity.this.mIsLoading) {
                if (SettingsSmartLockActivity.this.mHasSmartLock) {
                    SmartLockManager.getInstance().deleteExistingCredentials();
                } else {
                    SmartLockManager.getInstance().saveCredentials(SettingsSmartLockActivity.this, AnalyticsEvents.ANALYTICS_EVENT_VALUE_MANUAL);
                }
                SettingsSmartLockActivity.this.mIsLoading = true;
                NativeManager.getInstance().OpenProgressPopup(DisplayStrings.displayString(290));
                SettingsSmartLockActivity.this.postDelayed(new C27591(), 3000);
            }
        }
    }

    class C27612 implements DialogInterface.OnClickListener {
        C27612() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == 1) {
                SettingsSmartLockActivity.this.startActivityForResult(new Intent("android.settings.SETTINGS"), 0);
            }
        }
    }

    class C27623 implements SmartLockHasCredentialsListener {
        C27623() {
        }

        public void onHasCredentialsDetermined(boolean hasCredentials) {
            SettingsSmartLockActivity.this.mHasSmartLock = hasCredentials;
            NativeManager.getInstance().CloseProgressPopup();
            SettingsSmartLockActivity.this.mSmartLockEnable.setValue(hasCredentials);
            SettingsSmartLockActivity.this.mIsLoading = false;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1283R.layout.settings_smart_lock);
        ((TitleBar) findViewById(C1283R.id.theTitleBar)).init((Activity) this, (int) DisplayStrings.DS_SMART_LOCK_SETTINGS);
        this.mSmartLockEnable = (WazeSettingsView) findViewById(C1283R.id.smartLockButton);
        ((WazeTextView) findViewById(C1283R.id.smartLockDetails)).setText(DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_DETAILS));
        this.mSmartLockEnable.setOnClickListener(new C27601());
        NativeManager.getInstance().OpenProgressPopup(DisplayStrings.displayString(290));
        adjustSmartLockButton();
        this.mSmartLockEnable.setText(DisplayStrings.displayString(DisplayStrings.DS_ENABLE));
    }

    public void onNeverDetected() {
        MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_NEVER_TITLE), DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_NEVER_BODY), false, new C27612(), DisplayStrings.displayString(DisplayStrings.DS_SMART_LOCK_GO_TO_SETTINGS), DisplayStrings.displayString(DisplayStrings.DS_NO_THANKS), 0);
    }

    public void onSaveCompleted() {
        adjustSmartLockButton();
    }

    private void adjustSmartLockButton() {
        this.mIsLoading = true;
        SmartLockManager.getInstance().determineCredentialsExist(new C27623());
    }

    protected void onResume() {
        super.onResume();
        adjustSmartLockButton();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        SmartLockManager.getInstance().credentialsResolutionActivityResult(requestCode, resultCode, data, null);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
