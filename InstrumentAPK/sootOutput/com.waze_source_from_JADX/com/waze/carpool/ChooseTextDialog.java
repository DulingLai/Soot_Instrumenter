package com.waze.carpool;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.net.Uri;
import com.waze.AppService;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;

public class ChooseTextDialog extends BottomSheet implements Adapter {
    private ActivityBase mActivityBase;
    private NativeManager mNatMgr = AppService.getNativeManager();
    private String mPhoneNumber;
    private boolean mPresets;
    private CarpoolRide mRide;
    private String mRiderName;

    public int getCount() throws  {
        return 3;
    }

    public ChooseTextDialog(ActivityBase $r1, String $r2, String $r3, CarpoolRide $r4, boolean $z0) throws  {
        super($r1, DisplayStrings.DS_CARPOOL_TEXT_TITLE, Mode.COLUMN_TEXT);
        super.setAdapter(this);
        this.mActivityBase = $r1;
        this.mPhoneNumber = $r2;
        this.mRiderName = $r3;
        this.mRide = $r4;
        this.mPresets = $z0;
    }

    private void sendMessage(String $r1) throws  {
        Intent $r2;
        if (!CarpoolNativeManager.getInstance().isMessagingEnabled()) {
            $r2 = new Intent("android.intent.action.SENDTO", Uri.parse("smsto:" + this.mPhoneNumber));
            $r2.putExtra("sms_body", $r1);
            this.mActivityBase.startActivity($r2);
        } else if ($r1.isEmpty()) {
            NativeManager.getInstance();
            $r2 = new Intent(AppService.getActiveActivity(), CarpoolMessagingActivity.class);
            $r2.putExtra("rider", this.mRide.rider);
            $r2.putExtra("ride", this.mRide);
            AppService.getActiveActivity().startActivityForResult($r2, 0);
        } else {
            CarpoolNativeManager.getInstance().sendChatMessage(this.mRide.getId(), $r1);
        }
        dismiss();
    }

    public void onOption(String $r1, boolean $z0) throws  {
        if ($z0) {
            String $r3 = String.format(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_WARNING_BODY), new Object[]{this.mRiderName});
            final String str = $r1;
            MsgBox.openConfirmDialogJavaCallback(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_WARNING_TITLE), $r3, false, new OnClickListener() {
                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        ChooseTextDialog.this.sendMessage(str);
                    }
                }
            }, this.mNatMgr.getLanguageString(357), this.mNatMgr.getLanguageString(344), -1);
            return;
        }
        sendMessage($r1);
    }

    public void onConfigItem(int $i0, ItemDetails $r1) throws  {
        String $r2 = "";
        switch ($i0) {
            case 0:
                $r2 = String.format("\"%s\"", new Object[]{this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_LATE)});
                break;
            case 1:
                $r2 = String.format("\"%s\"", new Object[]{this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_TEXTME)});
                break;
            case 2:
                $r2 = this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_CUSTOM);
                break;
            default:
                break;
        }
        $r1.setItem($r2);
    }

    public void onClick(int $i0) throws  {
        switch ($i0) {
            case 0:
                sendMessage(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_LATE));
                return;
            case 1:
                sendMessage(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_CARPOOL_TEXT_TEXTME));
                return;
            case 2:
                sendMessage("");
                return;
            default:
                return;
        }
    }

    public void show() throws  {
        if (this.mPresets) {
            super.show();
        } else {
            sendMessage("");
        }
    }
}
