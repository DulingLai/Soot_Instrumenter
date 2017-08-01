package com.waze.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.WazeSettingsView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.EditTextDialog;
import com.waze.view.title.TitleBar;

public class ReasonSelectionActivity extends ActivityBase {
    public static final int CANCEL_RIDE = 2;
    public static final int REJECT_REQUEST = 1;
    private boolean[] mChecks;
    private CarpoolDrive mDrive;
    private ListView mList;
    private NativeManager mNm;
    private String mOtherReason = null;
    private String[] mReasons;
    private boolean mSingleSelection;
    private TitleBar mTitlebar;
    private int mWhatToUpdate = 1;

    class C16781 implements OnClickListener {
        C16781() throws  {
        }

        public void onClick(View v) throws  {
            ReasonSelectionActivity.this.finishWithReasons();
        }
    }

    class C16822 extends BaseAdapter {
        public Object getItem(int position) throws  {
            return null;
        }

        public long getItemId(int position) throws  {
            return 0;
        }

        C16822() throws  {
        }

        public int getCount() throws  {
            return ReasonSelectionActivity.this.mReasons.length;
        }

        public View getView(final int $i0, View $r1, ViewGroup parent) throws  {
            WazeSettingsView $r3 = (WazeSettingsView) $r1;
            if ($r3 == null) {
                $r3 = r10;
                WazeSettingsView r10 = new WazeSettingsView(ReasonSelectionActivity.this);
                if (ReasonSelectionActivity.this.mSingleSelection) {
                    $r3.setType(3);
                } else {
                    $r3.setType(5);
                }
                $r3.setIcon(null);
            }
            if ($i0 == 0) {
                $r3.setPosition(1);
            } else if ($i0 == ReasonSelectionActivity.this.mReasons.length - 1) {
                $r3.setPosition(2);
            } else {
                $r3.setPosition(0);
            }
            $r3.setText(ReasonSelectionActivity.this.mNm.getLanguageString(ReasonSelectionActivity.this.mReasons[$i0]));
            $r3.setValue(ReasonSelectionActivity.this.mChecks[$i0]);
            $r3.setOnChecked(new SettingsToggleCallback() {
                public void onToggle(boolean $z0) throws  {
                    ReasonSelectionActivity.this.mChecks[$i0] = $z0;
                    if ($z0 && ReasonSelectionActivity.this.mReasons[$i0].toLowerCase().startsWith(FacebookRequestErrorClassification.KEY_OTHER)) {
                        final EditTextDialog $r1 = new EditTextDialog(ReasonSelectionActivity.this);
                        $r1.setHint(DisplayStrings.DS_CARPOOL_DECLINE_REASON_ENTER_HERE___);
                        $r1.setTitle(DisplayStrings.DS_CARPOOL_CANCEL_REASON_ADD_REASON);
                        $r1.setOnOk(new OnClickListener() {
                            public void onClick(View v) throws  {
                                $r1.dismiss();
                                ReasonSelectionActivity.this.mOtherReason = $r1.getText();
                                ReasonSelectionActivity.this.updateTitlebarButton();
                            }
                        });
                        $r1.setOnCancel(new OnClickListener() {
                            public void onClick(View v) throws  {
                                $r1.dismiss();
                                $r3.setValue(false);
                                ReasonSelectionActivity.this.mChecks[$i0] = false;
                                ReasonSelectionActivity.this.updateTitlebarButton();
                            }
                        });
                        $r1.show();
                        return;
                    }
                    ReasonSelectionActivity.this.updateTitlebarButton();
                }
            });
            return $r3;
        }
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mNm = NativeManager.getInstance();
        Intent $r3 = getIntent();
        if ($r3 != null) {
            this.mDrive = (CarpoolDrive) $r3.getParcelableExtra("CarpoolDrive");
        } else {
            this.mDrive = null;
        }
        if (this.mDrive == null) {
            Logger.m38e("ReasonSelectionActivity: Cannot cancel ride - ride is null");
            MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
            setResult(0);
            finish();
        }
        setContentView(C1283R.layout.reason_selection);
        String $r6 = $r3.getStringExtra("title");
        this.mTitlebar = (TitleBar) findViewById(C1283R.id.theTitleBar);
        this.mTitlebar.init(this, $r6);
        this.mTitlebar.setOnClickCloseListener(new C16781());
        this.mReasons = $r3.getStringArrayExtra("reasons");
        this.mWhatToUpdate = $r3.getIntExtra("updateServer", 1);
        this.mSingleSelection = $r3.getBooleanExtra("singleSelection", false);
        int $i0 = this.mReasons;
        String[] $r12 = $i0;
        boolean[] $r13 = $i0.length;
        int $i02 = $r13;
        $r13 = new boolean[$r13];
        boolean[] $r132 = $r13;
        this.mChecks = $r13;
        updateTitlebarButton();
        this.mList = (ListView) findViewById(C1283R.id.reasonSelectionList);
        this.mList.setAdapter(new C16822());
    }

    private void finishWithReasons() throws  {
        String $r1 = "";
        String $r2 = "";
        for (int $i0 = 0; $i0 < this.mReasons.length; $i0++) {
            if (this.mChecks[$i0]) {
                String $r5;
                if ($i0 == this.mReasons.length - 1) {
                    $r5 = this.mOtherReason;
                } else {
                    $r5 = this.mReasons[$i0];
                }
                if (!$r1.isEmpty()) {
                    $r1 = $r1 + ";";
                }
                if (!$r2.isEmpty()) {
                    $r2 = $r2 + "|";
                }
                $r1 = $r1 + $r5;
                $r2 = $r2 + $r5;
            }
        }
        if (this.mWhatToUpdate == 1) {
            CarpoolUtils.rejectDriveInServer($r1, $r2, this.mDrive);
        } else if (this.mWhatToUpdate == 2) {
            CarpoolUtils.cancelDriveInServer($r2, this.mDrive);
        }
        setResult(-1);
        finish();
    }

    private void updateTitlebarButton() throws  {
        boolean $z1 = false;
        for (boolean $z0 : this.mChecks) {
            if ($z0) {
                $z1 = true;
                break;
            }
        }
        if (!$z1) {
            this.mTitlebar.setCloseText(this.mNm.getLanguageString((int) DisplayStrings.DS_SKIP));
        } else if (this.mSingleSelection) {
            finishWithReasons();
        } else {
            this.mTitlebar.setCloseImageResource(C1283R.drawable.v_button);
        }
    }
}
