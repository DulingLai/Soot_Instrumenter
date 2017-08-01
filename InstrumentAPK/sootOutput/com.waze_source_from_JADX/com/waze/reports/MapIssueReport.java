package com.waze.reports;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.NativeManager.StringResultListener;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.map.CanvasFont;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.reports.SpeedLimitReport.Listener;
import com.waze.rtalerts.MapProblem;
import com.waze.rtalerts.RTAlertsNativeManager;
import com.waze.rtalerts.RTAlertsNativeManager.MapProblemsListener;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.SelectorBg;

public class MapIssueReport extends ReportForm {
    public static final int ISSUE = 0;
    public static final int MAP_PROBLEM_SPEED_LIMIT = 17;
    private static final int PAVE = 1;
    private boolean mAutomaticDescription;
    private boolean mIsRecording;
    private MapProblem[] mProblems;

    class C24841 implements OnGlobalLayoutListener {
        C24841() {
        }

        public void onGlobalLayout() {
            MapIssueReport.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            MapIssueReport.this.setSelectedButton(1);
            MapIssueReport.this.setAddDetailsToPaving();
        }
    }

    class C24852 implements OnClickListener {
        C24852() {
        }

        public void onClick(View v) {
            MapIssueReport.this.goToEditText();
        }
    }

    class C24863 implements OnClickListener {
        C24863() {
        }

        public void onClick(View v) {
        }
    }

    class C24874 implements Runnable {
        C24874() {
        }

        public void run() {
            AppService.getNativeManager().EditorTrackToggleNewRoadsNTV();
        }
    }

    class C24938 implements Runnable {
        C24938() {
        }

        public void run() {
            AppService.getNativeManager().EditorTrackToggleNewRoadsNTV();
        }
    }

    class C24949 implements Runnable {
        C24949() {
        }

        public void run() {
            AppService.getNativeManager().EditorTrackToggleNewRoadsNTV();
        }
    }

    public MapIssueReport(Context context, ReportMenu reportMenu) {
        boolean z;
        super(context, reportMenu, 203);
        this.mAutomaticDescription = false;
        this.mNumOfButtons = 2;
        this.mIsTakePictureActive = false;
        if (AppService.getNativeManager().isEditorIgnoreNewRoadsNTV()) {
            z = false;
        } else {
            z = true;
        }
        this.mIsRecording = z;
        initLayout();
        if (this.mIsRecording) {
            getViewTreeObserver().addOnGlobalLayoutListener(new C24841());
        }
        TextView legal = (TextView) findViewById(C1283R.id.reportEditTextLegal);
        legal.setVisibility(0);
        legal.setText(DisplayStrings.displayString(DisplayStrings.DS_MAP_ISSUE_MORE_INFO_DISCLAIMER));
        findViewById(C1283R.id.reportSend).setEnabled(false);
        findViewById(C1283R.id.reportSend).setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
    }

    public int getDelayedReportButtonResource() {
        return C1283R.drawable.icon_report_map_editor;
    }

    protected int[] getReportSubtypes() {
        return new int[]{0, 1};
    }

    protected int getReportType() {
        return -1;
    }

    protected int[] getButtonResourceIds() {
        return new int[]{C1283R.drawable.map_issue_map_issue, C1283R.drawable.map_issue_pave};
    }

    protected int[] getButtonDisplayStrings() {
        return new int[]{203, DisplayStrings.DS_PAVE};
    }

    private void restoreAddDetails() {
        ((TextView) findViewById(C1283R.id.reportGenericAddDetailsText)).setText(DisplayStrings.displayString(4));
        ((ImageView) findViewById(C1283R.id.reportGenericAddDetailsImage)).setVisibility(0);
        findViewById(C1283R.id.reportGenericAddDetails).setOnClickListener(new C24852());
        ((TextView) findViewById(C1283R.id.reportSendText)).setText(DisplayStrings.displayString(293));
        findViewById(C1283R.id.reportLater).setVisibility(0);
    }

    private void setAddDetailsToPaving() {
        TextView details = (TextView) findViewById(C1283R.id.reportGenericAddDetailsText);
        if (this.mIsRecording) {
            details.setText(DisplayStrings.displayString(DisplayStrings.DS_CLICK_STOP_AND_MAKE_SURE_TO_GO));
        } else {
            details.setText(DisplayStrings.displayString(DisplayStrings.DS_CLICK_PAVE_AND_SIMPLY_DRIVE));
        }
        ((ImageView) findViewById(C1283R.id.reportGenericAddDetailsImage)).setVisibility(8);
        findViewById(C1283R.id.reportGenericAddDetails).setOnClickListener(new C24863());
        ((TextView) findViewById(C1283R.id.reportSendText)).setText(DisplayStrings.displayString(this.mIsRecording ? DisplayStrings.DS_STOP : DisplayStrings.DS_PAVE));
        findViewById(C1283R.id.reportLater).setVisibility(8);
        findViewById(C1283R.id.reportSend).setBackgroundResource(this.mIsRecording ? C1283R.drawable.button_red_bg : C1283R.drawable.button_blue_bg);
    }

    protected void onButtonClicked(int buttonIndex) {
        super.onButtonClicked(buttonIndex);
        findViewById(C1283R.id.reportSend).setEnabled(true);
        findViewById(C1283R.id.reportSend).setAlpha(1.0f);
        if (buttonIndex == 0) {
            this.mSelectedButton = 0;
            restoreAddDetails();
            setButtonText(1, DisplayStrings.DS_PAVE);
            if (this.mIsRecording) {
                NativeManager.Post(new C24874());
                this.mReportMenu.toggleRoadPaving(this.mIsRecording);
                this.mIsRecording = false;
            }
            final RTAlertsNativeManager rtanm = RTAlertsNativeManager.getInstance();
            rtanm.getMapProblems(new MapProblemsListener() {
                public void onComplete(MapProblem[] problems) {
                    if (problems != null && problems.length > 0) {
                        String[] options = new String[problems.length];
                        int[] optionIcons = new int[problems.length];
                        int[] optionValues = new int[problems.length];
                        for (int i = 0; i < problems.length; i++) {
                            options[i] = problems[i].description;
                            optionIcons[i] = rtanm.getMapIssueIcon(problems[i].type);
                            optionValues[i] = problems[i].type;
                            if (problems[i].type == 17) {
                                if (ConfigValues.getStringValue(101).equals("us")) {
                                    optionIcons[i] = C1283R.drawable.icon_report_speedlimit_us;
                                } else {
                                    optionIcons[i] = C1283R.drawable.icon_report_speedlimit_world;
                                }
                            }
                        }
                        MapIssueReport.this.showGridSubmenu(203, options, optionIcons, optionValues);
                    }
                }
            });
        } else if (this.mSelectedButton == 1) {
            this.mSelectedButton = 1;
            setAddDetailsToPaving();
        }
    }

    protected void clearSelection() {
        super.clearSelection();
        if (this.mAutomaticDescription) {
            ((TextView) findViewById(C1283R.id.reportGenericAddDetailsText)).setText(this.nativeManager.getLanguageString(4));
            this.mEditText.setText(null);
            this.mAutomaticDescription = false;
        }
    }

    protected void onGridSubmenuSelected(int selected, final ImageView imageView) {
        if (selected == 17) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SPEEDOMETER_ISSUE_TAPPED);
            NativeManager.getInstance().getPoiAddress(new StringResultListener() {
                public void onResult(final String address) {
                    MapIssueReport.this.post(new Runnable() {

                        class C24891 implements Listener {
                            C24891() {
                            }

                            public void onSpeedLimitReport(int speedMMSec) {
                                TextView details = (TextView) MapIssueReport.this.findViewById(C1283R.id.reportGenericAddDetailsText);
                                if (speedMMSec == -1) {
                                    MapIssueReport.this.clearSelection();
                                    details.setText("");
                                    return;
                                }
                                String iconText = "?";
                                if (speedMMSec > 0) {
                                    int speed = MapIssueReport.this.nativeManager.mathToSpeedUnitNTV(speedMMSec);
                                    String units = MapIssueReport.this.nativeManager.speedUnitNTV();
                                    String comment = DisplayStrings.displayStringF(DisplayStrings.DS_SPEED_LIMITS_COMMENT_PD_PS, Integer.valueOf(speed), units);
                                    details.setText(comment);
                                    MapIssueReport.this.mEditText.setText(comment);
                                    MapIssueReport.this.mAutomaticDescription = true;
                                    iconText = Integer.toString(speed);
                                }
                                imageView.setImageBitmap(MapIssueReport.this.generateDrawable(iconText));
                                imageView.setPadding(0, 0, 0, 0);
                            }
                        }

                        public void run() {
                            new SpeedLimitReport(MapIssueReport.this.mCtx, address, true, -1, new C24891()).show();
                        }
                    });
                }
            });
        }
    }

    private Bitmap generateDrawable(String text) {
        View vDrawable = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.speed_limit_issue_report_item, null);
        TextView tvDrawable = (TextView) vDrawable.findViewById(C1283R.id.speedLimit);
        if (ConfigValues.getStringValue(101).equals("us")) {
            tvDrawable.setBackgroundResource(C1283R.drawable.icon_report_speedlimit_us);
        }
        tvDrawable.setText(text);
        if (vDrawable.getMeasuredHeight() <= 0) {
            vDrawable.measure(-2, -2);
        }
        Bitmap b = Bitmap.createBitmap(vDrawable.getMeasuredWidth(), vDrawable.getMeasuredHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(b);
        vDrawable.layout(0, 0, vDrawable.getMeasuredWidth(), vDrawable.getMeasuredHeight());
        vDrawable.draw(c);
        return b;
    }

    public void setSpeedSelection() {
        int resource;
        if (ConfigValues.getStringValue(101).equals("us")) {
            resource = C1283R.drawable.icon_report_speedlimit_us;
        } else {
            resource = C1283R.drawable.icon_report_speedlimit_world;
        }
        final Drawable drawable = getResources().getDrawable(resource);
        final View button = getButton(0);
        ((SelectorBg) button.findViewById(C1283R.id.reportGenericButtonSelector)).animateSelected();
        RTAlertsNativeManager.getInstance().getMapProblems(new MapProblemsListener() {
            public void onComplete(MapProblem[] problems) {
                for (MapProblem problem : problems) {
                    if (problem.type == 17) {
                        ((TextView) button.findViewById(C1283R.id.reportGenericButtonText)).setText(NativeManager.getInstance().getLanguageString(problem.description));
                        ((ImageView) button.findViewById(C1283R.id.reportGenericButtonImage)).setImageDrawable(drawable);
                        MapIssueReport.this.mSubSelected = 17;
                    }
                }
            }
        });
        findViewById(C1283R.id.reportSend).setEnabled(true);
        findViewById(C1283R.id.reportSend).setAlpha(1.0f);
        this.mSelectedButton = 0;
    }

    private void startPaving() {
        NativeManager.Post(new C24938());
        if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
            this.mReportMenu.close();
            return;
        }
        onLater();
        this.mIsRecording = true;
        setAddDetailsToPaving();
    }

    private void stopPaving() {
        NativeManager.Post(new C24949());
        this.mReportMenu.toggleRoadPaving(this.mIsRecording);
        this.mIsRecording = false;
    }

    protected void onButtonUnselected(int buttonIndex) {
        super.onButtonUnselected(buttonIndex);
        if (buttonIndex == 1) {
            if (this.mIsRecording) {
                stopPaving();
            }
            setAddDetailsToPaving();
            setButtonText(1, DisplayStrings.DS_PAVE);
        }
    }

    protected void onSend() {
        stop();
        if (this.mSelectedButton == 0) {
            RTAlertsNativeManager.getInstance().reportMapIssue(this.mEditText.getText().toString(), this.mSubSelected);
        } else if (this.mSelectedButton == 1) {
            if (this.mIsRecording) {
                stopPaving();
            } else {
                startPaving();
            }
        }
        this.mReportMenu.close();
    }
}
