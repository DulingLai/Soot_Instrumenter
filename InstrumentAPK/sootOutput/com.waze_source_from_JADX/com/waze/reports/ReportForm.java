package com.waze.reports;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.speech.SpeechRecognizer;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ViewFlipper;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.RequestPermissionActivity;
import com.waze.map.CanvasFont;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.utils.FileUtils;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.anim.SelectorBg;
import com.waze.view.button.ReportMenuButton;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.WazeEditText;
import com.waze.view.text.WazeEditText.OnBackPressedListener;
import com.waze.view.timer.TimerBar;
import com.waze.voice.WazeSpeechRecognizer;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class ReportForm extends ViewFlipper {
    private static final int DURATION_ALL_DAY = 3;
    private static final int DURATION_LESS_THAN_HOUR = 1;
    private static final int DURATION_LONG_TERM = 5;
    private static final int DURATION_SEVERAL_DAYS = 4;
    private static final int DURATION_SEVERAL_HOURS = 2;
    private static final int DURATION_UNKNOWN = 0;
    private static final String IMAGE_FILE_NAME = "waze_report";
    static final int NONE = -1;
    public Animation animation;
    protected String audioFilename;
    private Uri capturedImageURI;
    protected String imageFilename;
    protected LayoutInflater inflater;
    protected boolean mAreSendButtonsActive = true;
    private View[] mButtons;
    protected Context mCtx;
    private int mDefaultButton = -1;
    protected WazeEditText mEditText;
    private Bitmap mImageBitmap;
    protected boolean mIsAddDetailsActive = true;
    protected boolean mIsDurationActive = false;
    private boolean mIsInEditText = false;
    protected boolean mIsSelectLaneActive = true;
    protected boolean mIsTakePictureActive = true;
    protected int mLayoutResId = -1;
    protected int mNumOfButtons = 3;
    private int mReportDuration = -1;
    protected ReportMenu mReportMenu;
    protected int mSelectedButton = -1;
    protected int mSubSelected = -1;
    protected int mTitle = DisplayStrings.DS_;
    CharSequence mTmpDescription;
    public boolean myLane = true;
    protected NativeManager nativeManager;
    protected boolean pendingTypingAllowed = false;
    private boolean retryToTakeImageOnResult = false;

    class C25182 implements OnClickListener {

        class C25171 implements AnimationListener {
            C25171() {
            }

            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                ReportForm.this.mReportMenu.close();
            }

            public void onAnimationRepeat(Animation animation) {
            }
        }

        C25182() {
        }

        public void onClick(View v) {
            ReportForm.this.stop();
            ReportForm.this.disappearify(0, 150, null, new C25171());
        }
    }

    class C25193 implements OnClickListener {
        C25193() {
        }

        public void onClick(View v) {
            ReportForm.this.mReportMenu.removeReportForm();
        }
    }

    class C25204 implements OnClickListener {
        C25204() {
        }

        public void onClick(View v) {
            ReportForm.this.stop();
            ReportForm.this.takePicture();
        }
    }

    class C25215 implements OnClickListener {
        C25215() {
        }

        public void onClick(View v) {
            ReportForm.this.onLater();
        }
    }

    class C25226 implements OnClickListener {
        C25226() {
        }

        public void onClick(View v) {
            ReportForm.this.onSend();
        }
    }

    class C25248 implements AnimationListener {
        C25248() {
        }

        public void onAnimationStart(Animation animation) {
        }

        public void onAnimationEnd(Animation animation) {
            ReportForm.this.mReportMenu.close();
        }

        public void onAnimationRepeat(Animation animation) {
        }
    }

    class C25259 implements OnBackPressedListener {
        C25259() {
        }

        public void onBackPressed() {
            ReportForm.this.returnFromEditText();
        }
    }

    private final class OnButtonClickListener implements OnClickListener {
        private final int index;

        private OnButtonClickListener(int index) {
            this.index = index;
        }

        public void onClick(View v) {
            if (ReportForm.this.mSelectedButton == this.index) {
                if (ReportForm.this.mSelectedButton >= 0) {
                    ReportForm.this.clearSelection();
                }
                ReportForm.this.onButtonUnselected(this.index);
                return;
            }
            if (ReportForm.this.mSelectedButton >= 0) {
                ((TextView) ReportForm.this.mButtons[ReportForm.this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonText)).setText(ReportForm.this.nativeManager.getLanguageString(ReportForm.this.getButtonDisplayStrings()[ReportForm.this.mSelectedButton]));
                ((ImageView) ReportForm.this.mButtons[ReportForm.this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonImage)).setImageResource(ReportForm.this.getButtonResourceIds()[ReportForm.this.mSelectedButton]);
                ReportForm.this.mButtons[ReportForm.this.mSelectedButton].setSelected(false);
                ((SelectorBg) ReportForm.this.mButtons[ReportForm.this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonSelector)).animateUnselected();
            }
            ReportForm.this.mSelectedButton = this.index;
            v.setSelected(true);
            ((SelectorBg) v.findViewById(C1283R.id.reportGenericButtonSelector)).animateSelected();
            ReportForm.this.onButtonClicked(this.index);
        }
    }

    protected abstract int[] getButtonDisplayStrings();

    protected abstract int[] getButtonResourceIds();

    public abstract int getDelayedReportButtonResource();

    protected abstract int[] getReportSubtypes();

    protected abstract int getReportType();

    protected void clearSelection() {
        ((TextView) this.mButtons[this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonText)).setText(this.nativeManager.getLanguageString(getButtonDisplayStrings()[this.mSelectedButton]));
        ((ImageView) this.mButtons[this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonImage)).setImageResource(getButtonResourceIds()[this.mSelectedButton]);
        this.mButtons[this.mSelectedButton].setSelected(false);
        ((SelectorBg) this.mButtons[this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonSelector)).animateUnselected();
        this.mSelectedButton = -1;
        this.mSubSelected = -1;
    }

    protected ReportForm(Context context, ReportMenu reportMenu, int title) {
        super(context);
        this.mCtx = context;
        this.mReportMenu = reportMenu;
        this.mTitle = title;
    }

    public void close() {
        this.mReportMenu.close();
    }

    protected void initLayout() {
        this.nativeManager = AppService.getNativeManager();
        this.inflater = (LayoutInflater) this.mCtx.getSystemService("layout_inflater");
        this.inflater.inflate(C1283R.layout.report_generic, this);
        ((TextView) findViewById(C1283R.id.reportTitle)).setText(this.nativeManager.getLanguageString(this.mTitle));
        if (this.mLayoutResId >= 0) {
            ViewGroup layout = (ViewGroup) findViewById(C1283R.id.reportGenericLayout);
            if (this.mCtx.getResources().getConfiguration().orientation == 2) {
                layout = (ViewGroup) layout.findViewById(C1283R.id.reportGenericLandscapeContent);
            }
            layout.removeView(findViewById(C1283R.id.reportGenericSelectorArea));
            layout.removeView(findViewById(C1283R.id.reportGenericDetailsArea));
            ViewGroup content = (ViewGroup) findViewById(C1283R.id.reportGenericExtArea);
            content.setVisibility(0);
            this.inflater.inflate(this.mLayoutResId, content);
        } else {
            buildButtons();
        }
        String sendText = this.nativeManager.getLanguageString(293);
        TextView sendTextView = (TextView) findViewById(C1283R.id.reportSendText);
        if (sendTextView != null) {
            sendTextView.setText(sendText);
        }
        String closeText = this.nativeManager.getLanguageString(292);
        TextView closeTextView = (TextView) findViewById(C1283R.id.reportLaterText);
        if (closeTextView != null) {
            closeTextView.setText(closeText);
        }
        if (!(this.mIsAddDetailsActive || this.mIsTakePictureActive || this.mIsDurationActive || this.mIsSelectLaneActive)) {
            findViewById(C1283R.id.reportGenericDetailsArea).setVisibility(8);
        }
        if (this.mIsAddDetailsActive) {
            initAddDetails();
        }
        if (this.mIsDurationActive && !this.mIsAddDetailsActive) {
            String durationText = this.nativeManager.getLanguageString(DisplayStrings.DS_DURATION);
            final TextView laneTextView = (TextView) findViewById(C1283R.id.reportGenericAddDetailsText);
            if (laneTextView != null) {
                laneTextView.setText(durationText);
            }
            ImageView laneImageView = (ImageView) findViewById(C1283R.id.reportGenericAddDetailsImage);
            if (laneImageView != null) {
                laneImageView.setImageResource(C1283R.drawable.icon_reports_add_duration);
            }
            View button = findViewById(C1283R.id.reportGenericAddDetails);
            if (button != null) {
                button.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ReportForm.this.stop();
                        final int[] options = new int[]{DisplayStrings.DS_UNKNOWN, DisplayStrings.DS_LESS_THANN_AN_HOUR, DisplayStrings.DS_SEVERAL_HOURS, DisplayStrings.DS_ALL_DAY, DisplayStrings.DS_SEVERAL_DAYS, DisplayStrings.DS_LONG_TERM};
                        final int[] optionValues = new int[]{0, 1, 2, 3, 4, 5};
                        ReportForm.this.showListSubmenu(DisplayStrings.DS_DURATION, options, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                ReportForm.this.mReportDuration = optionValues[which];
                                if (laneTextView != null) {
                                    laneTextView.setText(ReportForm.this.nativeManager.getLanguageString(options[which]));
                                }
                                dialog.dismiss();
                            }
                        });
                    }
                });
            }
        }
        findViewById(C1283R.id.reportCloseAll).setOnClickListener(new C25182());
        View reportButton = findViewById(C1283R.id.reportCloseButton);
        reportButton.setOnClickListener(new C25193());
        if (reportButton instanceof ReportMenuButton) {
            ((ReportMenuButton) reportButton).setCancelAlphaChanges(true);
        }
        if (this.mAreSendButtonsActive) {
            initSendButtons();
        } else {
            findViewById(C1283R.id.reportGenericButtonsArea).setVisibility(4);
        }
        View pictureButton;
        if (this.mIsTakePictureActive && this.nativeManager.ReportAllowImagesNTV()) {
            pictureButton = findViewById(C1283R.id.reportGenericAddPhoto);
            if (pictureButton != null) {
                pictureButton.setOnClickListener(new C25204());
            }
            TextView addPictureText = (TextView) findViewById(C1283R.id.reportGenericAddPhotoText);
            if (addPictureText != null) {
                addPictureText.setText(this.nativeManager.getLanguageString(DisplayStrings.DS_ADD_A_PHOTO));
                return;
            }
            return;
        }
        pictureButton = findViewById(C1283R.id.reportGenericAddPhoto);
        if (pictureButton != null) {
            pictureButton.setVisibility(8);
        }
        View sep = findViewById(C1283R.id.reportGenericDetailsAreaSeperator);
        if (sep != null) {
            sep.setVisibility(8);
        }
    }

    protected void initSendButtons() {
        View laterButton = findViewById(C1283R.id.reportLater);
        if (laterButton != null) {
            laterButton.setOnClickListener(new C25215());
            if (VERSION.SDK_INT >= 21) {
                AnimationUtils.api21RippleInit(laterButton);
            }
        }
        View sendButton = findViewById(C1283R.id.reportSend);
        if (sendButton != null) {
            sendButton.setOnClickListener(new C25226());
            if (VERSION.SDK_INT >= 21) {
                AnimationUtils.api21RippleInit(sendButton);
            }
        }
    }

    void onLater() {
        stop();
        this.mReportMenu.setDelayedReport((ReportMenuButton) findViewById(C1283R.id.reportCloseButton));
    }

    protected void onSend() {
        stop();
        String tmpImageFilePath = null;
        String tmpImageFileNameOnly = null;
        if (this.imageFilename != null) {
            tmpImageFilePath = FileUtils.getOnlyPath(this.imageFilename);
            tmpImageFileNameOnly = FileUtils.getFilenameOnly(this.imageFilename);
        }
        final String imageFilePath = tmpImageFilePath;
        final String imageFileNameOnly = tmpImageFileNameOnly;
        String tmpAudioFilePath = null;
        String tmpAudioFileNameOnly = null;
        if (this.audioFilename != null) {
            tmpAudioFilePath = FileUtils.getOnlyPath(this.audioFilename);
            tmpAudioFileNameOnly = FileUtils.getFilenameOnly(this.audioFilename);
        }
        final String audioFilePath = tmpAudioFilePath;
        final String audioFileNameOnly = tmpAudioFileNameOnly;
        String tmpDescription = null;
        if (this.mEditText != null) {
            tmpDescription = this.mEditText.getText().toString().trim();
        }
        final String description = tmpDescription;
        final int direction = this.myLane ? 1 : 2;
        final int reportType = getReportType();
        final int reportSubtype = getReportSubtype();
        final int duration = getDuration();
        NativeManager.Post(new Runnable() {
            public void run() {
                ReportForm.this.nativeManager.sendAlertRequest(imageFilePath, imageFileNameOnly, audioFilePath, audioFileNameOnly, description, direction, reportType, reportSubtype, duration);
            }
        });
        disappearify(0, 150, null, null);
        ((View) getLayoutAreas().get(0)).clearAnimation();
        ReportMenuButton rmb = (ReportMenuButton) findViewById(C1283R.id.reportCloseButton);
        rmb.clearAnimation();
        TranslateAnimation ta = new TranslateAnimation(2, 0.0f, 2, 0.0f, 2, 0.0f, 2, 4.0f);
        ta.setStartOffset(100);
        ta.setDuration(400);
        ta.setFillAfter(true);
        ta.setInterpolator(new AnticipateInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
        ta.setAnimationListener(new C25248());
        rmb.startAnimation(ta);
    }

    private void initAddDetails() {
        ((TextView) findViewById(C1283R.id.reportGenericAddDetailsText)).setText(this.nativeManager.getLanguageString(4));
        this.inflater.inflate(C1283R.layout.report_edit_text, this);
        this.mEditText = (WazeEditText) findViewById(C1283R.id.reportEditTextEditText);
        this.mEditText.setHint(this.nativeManager.getLanguageString(280));
        this.mEditText.setOnBackPressedListener(new C25259());
        this.mEditText.setOnEditorActionListener(new OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (event == null || event.getAction() == 0) {
                    ReportForm.this.returnFromEditText();
                }
                return true;
            }
        });
        this.mEditText.setOnKeyListener(new OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                return false;
            }
        });
        findViewById(C1283R.id.reportGenericAddDetails).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ReportForm.this.goToEditText();
            }
        });
        if (this.nativeManager.ReportAllowVoiceRecordingsNTV() && SpeechRecognizer.isRecognitionAvailable(this.mCtx)) {
            if (findViewById(C1283R.id.reportEditTextVoice) != null) {
                if (NativeManager.getManufacturer() != null && NativeManager.getManufacturer().equals("samsung") && NativeManager.getModel() != null && NativeManager.getModel().equals("GT-I9100") && NativeManager.getDevice() != null && NativeManager.getDevice().equals("GT-I9100")) {
                    findViewById(C1283R.id.reportEditTextVoice).setVisibility(8);
                }
                findViewById(C1283R.id.reportEditTextVoice).setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        ReportForm.this.stop();
                        ReportForm.this.recordAudio();
                    }
                });
            }
        } else if (findViewById(C1283R.id.reportEditTextVoice) != null) {
            findViewById(C1283R.id.reportEditTextVoice).setVisibility(8);
        }
    }

    protected void goToEditText() {
        stop();
        this.mIsInEditText = true;
        setOutAnimation(this.mCtx, C1283R.anim.slide_out_left);
        setInAnimation(this.mCtx, C1283R.anim.slide_in_right);
        showNext();
        postDelayed(new Runnable() {
            public void run() {
                AppService.getActiveActivity().getWindow().setSoftInputMode(16);
                ReportForm.this.mEditText.requestFocus();
                EditTextUtils.openKeyboard(AppService.getMainActivity(), ReportForm.this.mEditText);
            }
        }, 100);
    }

    protected void returnFromEditText() {
        AppService.getActiveActivity().getWindow().setSoftInputMode(32);
        String text = this.mEditText.getText().toString().trim();
        TextView textView = (TextView) findViewById(C1283R.id.reportGenericAddDetailsText);
        if (text.isEmpty()) {
            textView.setText(this.nativeManager.getLanguageString(4));
        } else {
            textView.setText(text);
        }
        this.mIsInEditText = false;
        setOutAnimation(this.mCtx, C1283R.anim.slide_out_right);
        setInAnimation(this.mCtx, C1283R.anim.slide_in_left);
        EditTextUtils.closeKeyboard(AppService.getMainActivity(), this.mEditText);
        postDelayed(new Runnable() {
            public void run() {
                ReportForm.this.showPrevious();
            }
        }, 100);
    }

    protected void buildButtons() {
        int[] displayStrings = getButtonDisplayStrings();
        int[] images = getButtonResourceIds();
        this.mButtons = new View[this.mNumOfButtons];
        ViewGroup container = (ViewGroup) findViewById(C1283R.id.reportButtons);
        container.removeAllViews();
        for (int i = 0; i < this.mNumOfButtons; i++) {
            this.mButtons[i] = buildButton(container, displayStrings[i], images[i]);
            this.mButtons[i].setOnClickListener(new OnButtonClickListener(i));
        }
    }

    protected View getButton(int index) {
        return this.mButtons[index];
    }

    private View buildButton(ViewGroup container, int displayString, int image) {
        View v = this.inflater.inflate(C1283R.layout.report_generic_button, container, false);
        ((TextView) v.findViewById(C1283R.id.reportGenericButtonText)).setText(this.nativeManager.getLanguageString(displayString));
        ((ImageView) v.findViewById(C1283R.id.reportGenericButtonImage)).setImageResource(image);
        container.addView(v);
        return v;
    }

    protected void takePicture() {
        Activity activity = AppService.getMainActivity();
        if (activity != null) {
            Intent permissionsIntent;
            try {
                File imageFile = File.createTempFile(IMAGE_FILE_NAME, null, Environment.getExternalStorageDirectory());
                imageFile.deleteOnExit();
                this.imageFilename = imageFile.getAbsolutePath();
                this.capturedImageURI = Uri.fromFile(imageFile);
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra("output", this.capturedImageURI);
                permissionsIntent = new Intent(activity, RequestPermissionActivity.class);
                permissionsIntent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"});
                permissionsIntent.putExtra(RequestPermissionActivity.INT_ON_GRANTED, intent);
                this.retryToTakeImageOnResult = false;
                activity.startActivityForResult(permissionsIntent, MainActivity.CAPTURE_IMAGE_CODE);
            } catch (IOException e) {
                if (!this.retryToTakeImageOnResult) {
                    permissionsIntent = new Intent(activity, RequestPermissionActivity.class);
                    permissionsIntent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.CAMERA", "android.permission.WRITE_EXTERNAL_STORAGE"});
                    this.retryToTakeImageOnResult = true;
                    activity.startActivityForResult(permissionsIntent, MainActivity.CAPTURE_IMAGE_CODE);
                }
            }
        }
    }

    protected void recordAudio() {
        WazeSpeechRecognizer.simpleIntent(MainActivity.REPORT_MENU_SPEECH_REQUEST_CODE);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onActivityResult(android.app.Activity r9, int r10, int r11, android.content.Intent r12) {
        /*
        r8 = this;
        r7 = 100;
        r6 = -1;
        r4 = 32770; // 0x8002 float:4.592E-41 double:1.61905E-319;
        if (r10 != r4) goto L_0x0066;
    L_0x0008:
        r4 = r8.retryToTakeImageOnResult;
        if (r4 == 0) goto L_0x0010;
    L_0x000c:
        r8.takePicture();
    L_0x000f:
        return;
    L_0x0010:
        r4 = 2131691670; // 0x7f0f0896 float:1.9012418E38 double:1.0531956217E-314;
        r1 = r8.findViewById(r4);
        r1 = (android.widget.ImageView) r1;
        if (r11 != r6) goto L_0x00a7;
    L_0x001b:
        r3 = 0;
        if (r12 == 0) goto L_0x0045;
    L_0x001e:
        r4 = "data";
        r4 = r12.hasExtra(r4);
        if (r4 == 0) goto L_0x0045;
    L_0x0027:
        r0 = r12.getExtras();
        r4 = "data";
        r4 = r0.get(r4);
        r4 = (android.graphics.Bitmap) r4;
        r8.mImageBitmap = r4;
        r4 = r8.mImageBitmap;
        if (r4 == 0) goto L_0x0045;
    L_0x003a:
        r4 = r8.mImageBitmap;
        r1.setImageBitmap(r4);
        r4 = android.widget.ImageView.ScaleType.CENTER_CROP;
        r1.setScaleType(r4);
        r3 = 1;
    L_0x0045:
        if (r3 != 0) goto L_0x0064;
    L_0x0047:
        r4 = r8.mCtx;
        r4 = r4.getContentResolver();
        r5 = r8.capturedImageURI;
        r4 = com.waze.profile.ImageTaker.decodeSampledBitmapFromResource(r4, r5, r7, r7);
        r8.mImageBitmap = r4;
        r4 = r8.mImageBitmap;
        if (r4 == 0) goto L_0x0064;
    L_0x0059:
        r4 = r8.mImageBitmap;
        r1.setImageBitmap(r4);
        r4 = android.widget.ImageView.ScaleType.CENTER_CROP;
        r1.setScaleType(r4);
        r3 = 1;
    L_0x0064:
        if (r3 != 0) goto L_0x0066;
    L_0x0066:
        r4 = 32769; // 0x8001 float:4.5919E-41 double:1.619E-319;
        if (r10 != r4) goto L_0x006b;
    L_0x006b:
        r4 = 32790; // 0x8016 float:4.5949E-41 double:1.62004E-319;
        if (r10 != r4) goto L_0x000f;
    L_0x0070:
        if (r11 != r6) goto L_0x008d;
    L_0x0072:
        if (r12 == 0) goto L_0x008d;
    L_0x0074:
        r4 = "android.speech.extra.RESULTS";
        r2 = r12.getStringArrayListExtra(r4);
        r4 = r2.size();
        if (r4 <= 0) goto L_0x008d;
    L_0x0081:
        r5 = r8.mEditText;
        r4 = 0;
        r4 = r2.get(r4);
        r4 = (java.lang.CharSequence) r4;
        r5.append(r4);
    L_0x008d:
        r4 = r8.mEditText;
        r4.requestFocus();
        r4 = com.waze.AppService.getActiveActivity();
        r5 = r8.mEditText;
        com.waze.utils.EditTextUtils.openKeyboard(r4, r5);
        r4 = new com.waze.reports.ReportForm$16;
        r4.<init>();
        r6 = 100;
        com.waze.AppService.Post(r4, r6);
        goto L_0x000f;
    L_0x00a7:
        if (r11 != 0) goto L_0x0066;
    L_0x00a9:
        r4 = 0;
        r8.imageFilename = r4;
        r4 = 2130838321; // 0x7f020331 float:1.7281621E38 double:1.0527740112E-314;
        r1.setImageResource(r4);
        goto L_0x0066;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.reports.ReportForm.onActivityResult(android.app.Activity, int, int, android.content.Intent):void");
    }

    public void start() {
        TimerBar timerBar = (TimerBar) findViewById(C1283R.id.reportLaterTimer);
        if (timerBar != null) {
            timerBar.start();
            timerBar.setVisibility(0);
        }
    }

    public void stop() {
        TimerBar timerBar = (TimerBar) findViewById(C1283R.id.reportLaterTimer);
        if (timerBar != null) {
            timerBar.stop();
        }
    }

    protected void setSelectedButton(int index) {
        this.mSelectedButton = index;
        this.mButtons[index].setSelected(true);
        ((SelectorBg) this.mButtons[index].findViewById(C1283R.id.reportGenericButtonSelector)).animateSelected();
    }

    protected void setButtonText(int index, int textDS) {
        ((TextView) this.mButtons[index].findViewById(C1283R.id.reportGenericButtonText)).setText(this.nativeManager.getLanguageString(textDS));
    }

    protected int getReportSubtype() {
        if (this.mSelectedButton < 0) {
            return -1;
        }
        return getReportSubtypes()[this.mSelectedButton];
    }

    protected void onButtonClicked(int buttonIndex) {
        stop();
    }

    protected void onButtonUnselected(int buttonIndex) {
        stop();
    }

    protected int getDuration() {
        return this.mReportDuration;
    }

    public void beforeOrientationChanged() {
        this.mTmpDescription = null;
        if (this.mEditText != null) {
            this.mTmpDescription = this.mEditText.getText();
        }
    }

    public void afterOrientationChanged() {
        TextView details = (TextView) findViewById(C1283R.id.reportGenericAddDetailsText);
        if (this.mTmpDescription != null && this.mTmpDescription.length() > 0) {
            this.mEditText.setText(this.mTmpDescription);
            if (details != null) {
                details.setText(this.mTmpDescription);
            }
        } else if (details != null) {
            details.setText(this.nativeManager.getLanguageString(4));
        }
        ImageView pic = (ImageView) findViewById(C1283R.id.reportPictureImage);
        if (pic == null) {
            return;
        }
        if (this.mImageBitmap != null) {
            pic.setImageBitmap(this.mImageBitmap);
            pic.setScaleType(ScaleType.CENTER_CROP);
            return;
        }
        pic.setImageResource(C1283R.drawable.icon_reports_addphoto);
    }

    public void onOrientationChanged(int orientation) {
        initLayout();
        if (this.mIsInEditText) {
            goToEditText();
        }
    }

    protected List<View> getLayoutAreas() {
        ArrayList<View> list = new ArrayList(5);
        View v = findViewById(C1283R.id.reportGenericTopArea);
        if (v != null) {
            list.add(v);
        }
        if (this.mLayoutResId < 0) {
            v = findViewById(C1283R.id.reportGenericSelectorArea);
            if (v != null) {
                list.add(v);
            }
            v = findViewById(C1283R.id.reportGenericDetailsArea);
            if (v != null) {
                list.add(v);
            }
        } else {
            v = findViewById(C1283R.id.reportGenericExtArea);
            if (v != null) {
                list.add(v);
            }
        }
        if (this.mAreSendButtonsActive) {
            v = findViewById(C1283R.id.reportGenericButtonsArea);
            if (v != null) {
                list.add(v);
            }
        }
        return list;
    }

    public int appearify(int startLatency, int duration, ReportMenuButton origRmb) {
        List<View> layoutAreas = getLayoutAreas();
        float density = this.mCtx.getResources().getDisplayMetrics().density;
        int latency = startLatency + (duration / 4);
        int i = origRmb != null ? 1 : 0;
        while (i < layoutAreas.size()) {
            View v = (View) layoutAreas.get(i);
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(new TranslateAnimation(0.0f, 0.0f, LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT * density, 0.0f));
            as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            as.setDuration((long) duration);
            as.setStartOffset((long) latency);
            as.setFillBefore(true);
            as.setInterpolator(new DecelerateInterpolator());
            v.startAnimation(as);
            latency += duration / 4;
            i++;
        }
        final ReportMenuButton rmb = (ReportMenuButton) findViewById(C1283R.id.reportCloseButton);
        rmb.setVisibility(0);
        rmb.setAlpha(1.0f);
        rmb.skipAnimation();
        if (origRmb != null) {
            rmb.setBackgroundColor(origRmb.getBackgroundColor());
            rmb.setImageResource(origRmb.getImageResId());
            final ReportMenuButton reportMenuButton = origRmb;
            final int i2 = startLatency;
            rmb.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

                class C25151 implements AnimationListener {
                    C25151() {
                    }

                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        ReportForm.this.start();
                    }
                }

                public void onGlobalLayout() {
                    rmb.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    int[] origLoc = new int[2];
                    reportMenuButton.getLocationOnScreen(origLoc);
                    int[] location = new int[2];
                    rmb.getLocationOnScreen(location);
                    TranslateAnimation ta = new TranslateAnimation((float) (origLoc[0] - location[0]), 0.0f, (float) (origLoc[1] - location[1]), 0.0f);
                    ta.setInterpolator(new OvershootInterpolator(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                    ta.setDuration(400);
                    ta.setStartOffset((long) (i2 / 2));
                    ta.setFillAfter(true);
                    rmb.startAnimation(ta);
                    ta.setAnimationListener(new C25151());
                }
            });
            AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
            aa.setDuration((long) duration);
            aa.setStartOffset((long) startLatency);
            aa.setFillBefore(true);
            findViewById(C1283R.id.reportTitle).startAnimation(aa);
        }
        return (duration / 4) + latency;
    }

    @SuppressLint({"NewApi"})
    public void animateButton(ReportMenuButton delayedReportButton) {
        boolean bTryParent;
        ReportMenuButton rmb = (ReportMenuButton) findViewById(C1283R.id.reportCloseButton);
        rmb.clearAnimation();
        rmb.setOpen();
        rmb.setVisibility(0);
        rmb.setAlpha(1.0f);
        ((View) getLayoutAreas().get(0)).clearAnimation();
        ((View) getLayoutAreas().get(0)).setVisibility(0);
        ((View) getLayoutAreas().get(0)).setAlpha(1.0f);
        int[] source = new int[2];
        int[] dest = new int[2];
        delayedReportButton.getLocationOnScreen(source);
        rmb.getLocationOnScreen(dest);
        if ((VERSION.SDK_INT < 19 || rmb.isAttachedToWindow()) && !(VERSION.SDK_INT < 19 && dest[0] == 0 && dest[1] == 0)) {
            bTryParent = false;
        } else {
            bTryParent = true;
        }
        if (bTryParent) {
            ((View) rmb.getParent()).getLocationOnScreen(dest);
            dest[0] = dest[0] + rmb.getLeft();
            dest[1] = dest[1] + rmb.getTop();
        }
        TranslateAnimation ta = new TranslateAnimation(0.0f, (float) (dest[0] - source[0]), 0.0f, (float) (dest[1] - source[1]));
        ta.setInterpolator(new DecelerateInterpolator(CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR));
        ta.setFillAfter(true);
        ta.setDuration(200);
        delayedReportButton.startAnimation(ta);
    }

    public int disappearify(int startLatency, int duration, ReportMenuButton origRmb, AnimationListener animLis) {
        AlphaAnimation aa;
        List<View> layoutAreas = getLayoutAreas();
        float density = this.mCtx.getResources().getDisplayMetrics().density;
        int latency = startLatency;
        int i = layoutAreas.size() - 1;
        while (true) {
            if (i < (origRmb != null ? 1 : 0)) {
                break;
            }
            View v = (View) layoutAreas.get(i);
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(new TranslateAnimation(0.0f, 0.0f, 0.0f, LayoutManager.WAZE_LAYOUT_EDIT_HEIGHT * density));
            as.addAnimation(new AlphaAnimation(1.0f, 0.0f));
            as.setDuration((long) duration);
            as.setStartOffset((long) latency);
            as.setFillAfter(true);
            as.setInterpolator(new AccelerateInterpolator());
            v.startAnimation(as);
            latency += duration / 4;
            i--;
        }
        ReportMenuButton rmb = (ReportMenuButton) findViewById(C1283R.id.reportCloseButton);
        if (origRmb != null) {
            int[] origLoc = new int[2];
            origRmb.getLocationOnScreen(origLoc);
            int[] location = new int[2];
            rmb.getLocationOnScreen(location);
            if (location[0] == 0 && location[1] == 0) {
                ((View) rmb.getParent()).getLocationOnScreen(location);
                location[0] = location[0] + rmb.getLeft();
                location[1] = location[1] + rmb.getTop();
            }
            TranslateAnimation ta = new TranslateAnimation(0.0f, (float) (origLoc[0] - location[0]), 0.0f, (float) (origLoc[1] - location[1]));
            ta.setInterpolator(new AccelerateDecelerateInterpolator());
            ta.setDuration(200);
            ta.setStartOffset((long) (startLatency / 2));
            ta.setFillAfter(true);
            rmb.startAnimation(ta);
            final AnimationListener animationListener = animLis;
            final ReportMenuButton reportMenuButton = origRmb;
            ta.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    animationListener.onAnimationStart(animation);
                }

                public void onAnimationRepeat(Animation animation) {
                    animationListener.onAnimationRepeat(animation);
                }

                public void onAnimationEnd(Animation animation) {
                    animationListener.onAnimationEnd(animation);
                    reportMenuButton.setVisibility(0);
                }
            });
        } else {
            aa = new AlphaAnimation(1.0f, 0.0f);
            aa.setDuration((long) duration);
            aa.setStartOffset((long) latency);
            aa.setAnimationListener(animLis);
            rmb.startAnimation(aa);
        }
        aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setDuration((long) duration);
        aa.setFillAfter(true);
        findViewById(C1283R.id.reportTitle).startAnimation(aa);
        return latency;
    }

    public void setButton(int imageResId, int backgroundColor) {
        ReportMenuButton rmb = (ReportMenuButton) findViewById(C1283R.id.reportCloseButton);
        rmb.setBackgroundColor(backgroundColor);
        rmb.setImageResource(imageResId);
        rmb.skipAnimation();
    }

    protected void setTitle(String title) {
        ((TextView) findViewById(C1283R.id.reportTitle)).setText(title);
    }

    protected void setSubTitle(String subTitle) {
        TextView sub = (TextView) findViewById(C1283R.id.reportSubTitle);
        sub.setText(subTitle);
        sub.setVisibility(0);
        if (this.mCtx.getResources().getConfiguration().orientation == 1) {
            TextView title = (TextView) findViewById(C1283R.id.reportTitle);
            LayoutParams p = (LayoutParams) title.getLayoutParams();
            p.setMargins(0, 0, 0, (int) (-2.0f * getContext().getResources().getDisplayMetrics().density));
            title.setLayoutParams(p);
        }
    }

    protected void setCustomWeights(float topWeight, float extWeight) {
        if (this.mCtx.getResources().getConfiguration().orientation == 1) {
            View top = findViewById(C1283R.id.reportGenericTopArea);
            LinearLayout.LayoutParams p = (LinearLayout.LayoutParams) top.getLayoutParams();
            p.weight = topWeight;
            top.setLayoutParams(p);
            View content = findViewById(C1283R.id.reportGenericExtArea);
            p = (LinearLayout.LayoutParams) content.getLayoutParams();
            p.weight = extWeight;
            content.setLayoutParams(p);
        }
    }

    public boolean goBack() {
        if (!this.mIsInEditText) {
            return false;
        }
        returnFromEditText();
        return true;
    }

    protected void setButtonsEnabled(boolean enabled) {
        this.mAreSendButtonsActive = enabled;
        findViewById(C1283R.id.reportGenericButtonsArea).setVisibility(enabled ? 0 : 4);
    }

    protected void showGridSubmenu(int dialogTitleDS, String[] options, int[] optionIds, int[] optionValues) {
        this.mSubSelected = -1;
        SimpleBottomSheetValue[] values = new SimpleBottomSheetValue[options.length];
        for (int i = 0; i < options.length; i++) {
            values[i] = new SimpleBottomSheetValue(optionValues[i], options[i], this.mCtx.getResources().getDrawable(optionIds[i]));
        }
        new SimpleBottomSheet(this.mCtx, dialogTitleDS, values, new SimpleBottomSheetListener() {
            public void onComplete(SimpleBottomSheetValue value) {
                if (ReportForm.this.mSelectedButton >= 0 && ReportForm.this.mSelectedButton < ReportForm.this.mButtons.length) {
                    ((TextView) ReportForm.this.mButtons[ReportForm.this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonText)).setText(value.display);
                    ImageView ivImage = (ImageView) ReportForm.this.mButtons[ReportForm.this.mSelectedButton].findViewById(C1283R.id.reportGenericButtonImage);
                    ivImage.setImageDrawable(value.icon);
                    ReportForm.this.mSubSelected = value.id;
                    ReportForm.this.onGridSubmenuSelected(ReportForm.this.mSubSelected, ivImage);
                }
            }
        }) {
            public void onClick(int itemId) {
                super.onClick(itemId);
                dismiss();
            }
        }.show();
    }

    protected void onGridSubmenuSelected(int selected, ImageView imageView) {
    }

    protected void showListSubmenu(int dialogTitleDS, final int[] optionDSs, final DialogInterface.OnClickListener l) {
        final BottomSheet dialog = new BottomSheet(this.mCtx, dialogTitleDS, Mode.COLUMN_TEXT);
        dialog.setAdapter(new Adapter() {
            public int getCount() {
                return optionDSs.length;
            }

            public void onConfigItem(int position, ItemDetails item) {
                item.setItem(optionDSs[position]);
            }

            public void onClick(int position) {
                l.onClick(dialog, position);
            }
        });
        dialog.show();
    }

    public ReportMenu getReportMenu() {
        return this.mReportMenu;
    }
}
