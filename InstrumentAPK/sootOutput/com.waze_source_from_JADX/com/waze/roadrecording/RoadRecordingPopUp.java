package com.waze.roadrecording;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.reports.ReportMenu;
import com.waze.strings.DisplayStrings;
import com.waze.view.button.AutoResizeTextButton;

public class RoadRecordingPopUp extends Dialog {
    private ReportMenu mReportMenu;

    class C25611 implements OnClickListener {
        C25611() {
        }

        public void onClick(View v) {
            RoadRecordingPopUp.this.dismiss();
        }
    }

    public RoadRecordingPopUp(Context context, ReportMenu reportMenu) {
        super(context, C1283R.style.Dialog);
        this.mReportMenu = reportMenu;
    }

    private void setTitle() {
        ((TextView) findViewById(C1283R.id.roadRecordingTitleText)).setText(AppService.getNativeManager().getLanguageString(202));
    }

    private void setAdditionTextTitle(String text) {
        ((TextView) findViewById(C1283R.id.roadRecordingTextBold)).setText(text);
    }

    private void setAdditionText(String text) {
        ((TextView) findViewById(C1283R.id.roadRecordingText)).setText(text);
    }

    private void setButtonText(String text) {
        ((TextView) findViewById(C1283R.id.PaveButtonText)).setText(text);
    }

    private void initDlg() {
        setContentView(C1283R.layout.road_recording_popup);
        getWindow().setLayout(-1, -1);
        final boolean isRecording = !AppService.getNativeManager().isEditorIgnoreNewRoadsNTV();
        setTitle();
        AutoResizeTextButton closeButton = (AutoResizeTextButton) findViewById(C1283R.id.reportCloseButton);
        closeButton.setText(AppService.getNativeManager().getLanguageString(354));
        closeButton.setOnClickListener(new C25611());
        if (isRecording) {
            setAdditionTextTitle(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_DONEQ));
            setAdditionText(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_CLICK_STOP_AND_MAKE_SURE_TO_GO));
            setButtonText(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_STOP_PAVING));
            ((ImageView) findViewById(C1283R.id.illusImage)).setImageResource(C1283R.drawable.illus_after_record);
        } else {
            setAdditionTextTitle(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_NO_ROAD_HEREQ));
            setAdditionText(AppService.getNativeManager().getLanguageString(DisplayStrings.DS_CLICK_PAVE_AND_SIMPLY_DRIVE));
            setButtonText(AppService.getNativeManager().getLanguageString(202));
            ((ImageView) findViewById(C1283R.id.illusImage)).setImageResource(C1283R.drawable.illus_before_record);
        }
        findViewById(C1283R.id.PaveButton).setOnClickListener(new OnClickListener() {

            class C25621 implements Runnable {
                C25621() {
                }

                public void run() {
                    AppService.getNativeManager().EditorTrackToggleNewRoadsNTV();
                }
            }

            public void onClick(View v) {
                NativeManager.Post(new C25621());
                RoadRecordingPopUp.this.dismiss();
                if (!MyWazeNativeManager.getInstance().isGuestUserNTV()) {
                    RoadRecordingPopUp.this.mReportMenu.toggleRoadPaving(isRecording);
                }
            }
        });
    }

    public void open() {
        initDlg();
        super.show();
    }

    public void onBackPressed() {
        boolean isRecording = !AppService.getNativeManager().isEditorIgnoreNewRoadsNTV();
        if (isRecording) {
            dismiss();
            this.mReportMenu.toggleRoadPaving(isRecording);
            return;
        }
        dismiss();
    }

    public void onOrientationChanged(int orientation) {
        initDlg();
    }
}
