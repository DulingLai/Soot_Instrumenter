package com.waze.reports;

import android.app.Fragment;
import android.content.DialogInterface;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CameraPreview;
import com.waze.ifs.ui.CameraPreview.CameraCallbacks;
import com.waze.ifs.ui.CameraPreview.FlashMode;
import com.waze.map.CanvasFont;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;
import java.io.File;
import java.io.FilenameFilter;

public class TakePhotoFragment extends Fragment implements CameraCallbacks {
    private static final int ANIMATION_DURATION = 550;
    private static final float CAPTURE_HEIGHT = 1200.0f;
    private static final float CAPTURE_WIDTH = 1600.0f;
    private static final String IMAGE_FILE_NAME = "newPlaceImage.jpg";
    private Bundle mCameraButtonLocation;
    private View mClearView;
    private boolean mDoAnimation;
    private ImageButton mDoneButton;
    private String mFileName;
    private ImageButton mFlashButton;
    private FlashMode mFlashMode;
    private boolean mIsPortrait;
    private NativeManager mNm;
    private ImageButton mOkButton;
    private CameraPreview mPreview;
    private ImageButton mRetakeButton;
    private String mSavedFile;
    private WazeTextView mText;
    private int mWindowHeight;
    private int mWindowWidth;
    private View f97r;

    public interface ITakePhoto {
        void photoTaken(Uri uri, String str);
    }

    class C25401 implements OnGlobalLayoutListener {
        C25401() {
        }

        public void onGlobalLayout() {
            TakePhotoFragment.this.mOkButton.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            TakePhotoFragment.this.animateIn();
        }
    }

    class C25412 implements OnTouchListener {
        C25412() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == 0) {
                TakePhotoFragment.this.mPreview.focus(event.getX() / ((float) TakePhotoFragment.this.mPreview.getWidth()), event.getY() / ((float) TakePhotoFragment.this.mPreview.getHeight()));
            }
            return false;
        }
    }

    class C25423 implements OnClickListener {
        C25423() {
        }

        public void onClick(View v) {
            TakePhotoFragment.this.lockOrientation();
            TakePhotoFragment.this.mOkButton.setOnClickListener(null);
            TakePhotoFragment.this.dimOkButton();
            TakePhotoFragment.this.mPreview.capture();
        }
    }

    class C25434 implements OnClickListener {
        C25434() {
        }

        public void onClick(View v) {
            TakePhotoFragment.this.nextFlashMode();
        }
    }

    class C25445 implements OnGlobalLayoutListener {
        C25445() {
        }

        public void onGlobalLayout() {
            TakePhotoFragment.this.setCaptureRect();
            TakePhotoFragment.this.mPreview.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    class C25456 implements Runnable {
        C25456() {
        }

        public void run() {
            float left = ((float) TakePhotoFragment.this.mClearView.getLeft()) / ((float) TakePhotoFragment.this.mPreview.getWidth());
            float width = ((float) TakePhotoFragment.this.mClearView.getWidth()) / ((float) TakePhotoFragment.this.mPreview.getWidth());
            TakePhotoFragment.this.mPreview.setCaptureRect(Float.valueOf(left), Float.valueOf(((float) TakePhotoFragment.this.mClearView.getTop()) / ((float) TakePhotoFragment.this.mPreview.getHeight())), Float.valueOf(width), Float.valueOf(((float) TakePhotoFragment.this.mClearView.getHeight()) / ((float) TakePhotoFragment.this.mPreview.getHeight())));
        }
    }

    class C25467 implements OnClickListener {
        C25467() {
        }

        public void onClick(View v) {
            TakePhotoFragment.this.animateBlackFlashStart();
        }
    }

    class C25478 implements OnClickListener {
        C25478() {
        }

        public void onClick(View v) {
            TakePhotoFragment.this.mPreview.saveToFile();
            File f = new File(TakePhotoFragment.this.mSavedFile);
            ((ITakePhoto) TakePhotoFragment.this.getActivity()).photoTaken(Uri.fromFile(f), f.getAbsolutePath());
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_TAKING_PHOTO_APPROVE_CLICKED, null, null);
        }
    }

    public TakePhotoFragment() {
        this.mFlashMode = FlashMode.Off;
        this.mIsPortrait = true;
        this.mDoAnimation = false;
        this.mFileName = IMAGE_FILE_NAME;
    }

    public TakePhotoFragment(String filename) {
        this.mFlashMode = FlashMode.Off;
        this.mIsPortrait = true;
        this.mDoAnimation = false;
        this.mFileName = filename;
    }

    public void setAnimateButton(Bundle buttonLocation) {
        this.mDoAnimation = buttonLocation != null;
        this.mCameraButtonLocation = buttonLocation;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mNm = NativeManager.getInstance();
        this.mWindowHeight = getResources().getDisplayMetrics().heightPixels;
        this.mWindowWidth = getResources().getDisplayMetrics().widthPixels;
        File f = new File(ResManager.mAppDir + File.separator + this.mFileName);
        int i = 0;
        while (f.exists()) {
            i++;
            f = new File(ResManager.mAppDir + File.separator + this.mFileName + i);
        }
        if (i > 0) {
            this.mFileName += i;
        }
        int maxCapSize = ConfigValues.getIntValue(401);
        CameraPreview.CaptureConfig(this.mWindowWidth, this.mWindowHeight, ConfigValues.getBoolValue(400), 90, ResManager.mAppDir, this.mFileName, maxCapSize, this);
        this.mSavedFile = ResManager.mAppDir + File.separator + this.mFileName;
        this.f97r = inflater.inflate(C1283R.layout.camera_preview, container, false);
        setUpFragment();
        if (this.mDoAnimation) {
            this.mDoAnimation = false;
            this.mOkButton.getViewTreeObserver().addOnGlobalLayoutListener(new C25401());
        }
        return this.f97r;
    }

    private void setUpFragment() {
        unlockOrientation();
        this.mText = (WazeTextView) this.f97r.findViewById(C1283R.id.explainText);
        this.mText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CAMERA_EXPLAIN_TEXT));
        this.mPreview = (CameraPreview) this.f97r.findViewById(C1283R.id.cameraPreview);
        this.mPreview.setOnTouchListener(new C25412());
        this.mClearView = this.f97r.findViewById(C1283R.id.cameraCaptureWindow);
        setClearViewLayout();
        this.mDoneButton = (ImageButton) this.f97r.findViewById(C1283R.id.cameraDone);
        this.mDoneButton.setOnClickListener(null);
        this.mDoneButton.setVisibility(8);
        this.mOkButton = (ImageButton) this.f97r.findViewById(C1283R.id.cameraOk);
        this.mOkButton.setVisibility(0);
        this.mOkButton.clearAnimation();
        this.mOkButton.setOnClickListener(new C25423());
        this.mRetakeButton = (ImageButton) this.f97r.findViewById(C1283R.id.cameraRetake);
        this.mRetakeButton.setEnabled(false);
        buttonAlphaAnim(this.mRetakeButton, 1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mFlashButton = (ImageButton) this.f97r.findViewById(C1283R.id.cameraFlash);
        this.mFlashButton.setEnabled(true);
        this.mFlashButton.clearAnimation();
        setFlashMode();
        this.mFlashButton.setOnClickListener(new C25434());
        this.mPreview.getViewTreeObserver().addOnGlobalLayoutListener(new C25445());
    }

    public void onResume() {
        super.onResume();
        this.mPreview.focus();
    }

    private void setCaptureRect() {
        ((ActivityBase) getActivity()).postDelayed(new C25456(), 10);
    }

    public void onDestroy() {
        unlockOrientation();
        super.onDestroy();
    }

    private void setFlashMode() {
        if (this.mFlashMode == FlashMode.Off) {
            this.mFlashButton.setImageResource(C1283R.drawable.take_photo_flashoff);
        } else if (this.mFlashMode == FlashMode.On) {
            this.mFlashButton.setImageResource(C1283R.drawable.take_photo_flash);
        } else if (this.mFlashMode == FlashMode.Auto) {
            this.mFlashButton.setImageResource(C1283R.drawable.take_photo_autoflash);
        }
        this.mPreview.setFlash(this.mFlashMode);
    }

    private void nextFlashMode() {
        if (this.mFlashMode == FlashMode.Off) {
            this.mFlashMode = FlashMode.On;
        } else if (this.mFlashMode == FlashMode.On) {
            this.mFlashMode = FlashMode.Auto;
        } else if (this.mFlashMode == FlashMode.Auto) {
            this.mFlashMode = FlashMode.Off;
        }
        setFlashMode();
    }

    private void setClearViewLayout() {
        LayoutParams p = this.mClearView.getLayoutParams();
        if (this.mWindowHeight > this.mWindowWidth) {
            p.height = (int) ((((float) this.mWindowWidth) / CAPTURE_WIDTH) * CAPTURE_HEIGHT);
        } else {
            this.mIsPortrait = false;
            p.width = (int) ((((float) this.mWindowHeight) / CAPTURE_HEIGHT) * CAPTURE_WIDTH);
        }
        this.mClearView.setLayoutParams(p);
    }

    public void onCapture(boolean aRes) {
        if (aRes) {
            animateFlash();
            animateDoneButton();
            this.mText.setText(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_CAMERA_POST_CAPTURE));
            this.mRetakeButton.setEnabled(true);
            buttonAlphaAnim(this.mRetakeButton, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f);
            this.mRetakeButton.setOnClickListener(new C25467());
            this.mFlashButton.setEnabled(false);
            buttonAlphaAnim(this.mFlashButton, 1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            this.mDoneButton.setOnClickListener(new C25478());
            return;
        }
        animateBlackFlashStart();
    }

    public void onSize(Size previewSize) {
        this.mWindowHeight = this.mPreview.getHeight();
        this.mWindowWidth = this.mPreview.getWidth();
        setClearViewLayout();
        int pvHeight;
        int pvWidth;
        final LayoutParams p;
        if (this.mWindowHeight > this.mWindowWidth) {
            if (previewSize.height > previewSize.width) {
                pvHeight = previewSize.height;
                pvWidth = previewSize.width;
            } else {
                pvHeight = previewSize.width;
                pvWidth = previewSize.height;
            }
            pvHeight = (int) ((((float) this.mWindowWidth) / ((float) pvWidth)) * ((float) pvHeight));
            pvWidth = this.mWindowWidth;
            p = this.mPreview.getLayoutParams();
            if (Math.abs(p.height - pvHeight) > 10) {
                p.height = pvHeight;
                ((ActivityBase) getActivity()).postDelayed(new Runnable() {
                    public void run() {
                        TakePhotoFragment.this.mPreview.setLayoutParams(p);
                    }
                }, 0);
            }
        } else {
            if (previewSize.height < previewSize.width) {
                pvHeight = previewSize.height;
                pvWidth = previewSize.width;
            } else {
                pvHeight = previewSize.width;
                pvWidth = previewSize.height;
            }
            pvWidth = (int) ((((float) this.mWindowHeight) / ((float) pvHeight)) * ((float) pvWidth));
            pvHeight = this.mWindowHeight;
            p = this.mPreview.getLayoutParams();
            if (Math.abs(p.width - pvWidth) > 10) {
                p.width = pvWidth;
                this.mPreview.setLayoutParams(p);
            }
        }
        setCaptureRect();
    }

    public void onError() {
        if (getActivity() != null && !getActivity().isFinishing()) {
            MsgBox.openMessageBoxWithCallback(this.mNm.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNm.getLanguageString(343), false, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    TakePhotoFragment.this.getActivity().finish();
                }
            });
        }
    }

    private void animateIn() {
        int left = this.mCameraButtonLocation.getInt("left");
        int top = this.mCameraButtonLocation.getInt("top");
        int width = this.mCameraButtonLocation.getInt("width");
        int height = this.mCameraButtonLocation.getInt("height");
        int[] location = new int[2];
        this.mOkButton.getLocationInWindow(location);
        Animation animationSet = new AnimationSet(false);
        int butWidth = this.mOkButton.getWidth();
        int butHeight = this.mOkButton.getHeight();
        ScaleAnimation sa = new ScaleAnimation(((float) width) / ((float) butWidth), 1.0f, ((float) height) / ((float) butHeight), 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(825);
        sa.setInterpolator(new OvershootInterpolator(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
        animationSet.addAnimation(sa);
        TranslateAnimation ta = new TranslateAnimation(0, (float) (((width / 2) + left) - (location[0] + (butWidth / 2))), 1, 0.0f, 0, (float) (((height / 2) + top) - (location[1] + (butHeight / 2))), 1, 0.0f);
        ta.setDuration(550);
        ta.setInterpolator(new AnticipateInterpolator(0.875f));
        animationSet.addAnimation(ta);
        this.mOkButton.startAnimation(animationSet);
        View curtain1 = this.f97r.findViewById(C1283R.id.curtains);
        View curtain2 = this.f97r.findViewById(C1283R.id.bottomCurtains);
        ScaleAnimation sa1 = new ScaleAnimation(this.mIsPortrait ? 1.0f : 0.0f, 1.0f, this.mIsPortrait ? 0.0f : 1.0f, 1.0f, 1, 0.0f, 1, 0.0f);
        ScaleAnimation sa2 = new ScaleAnimation(this.mIsPortrait ? 1.0f : 0.0f, 1.0f, this.mIsPortrait ? 0.0f : 1.0f, 1.0f, 1, 1.0f, 1, 1.0f);
        sa1.setDuration(550);
        sa1.setStartOffset(275);
        sa1.setFillBefore(true);
        sa2.setDuration(550);
        sa2.setStartOffset(275);
        sa2.setFillBefore(true);
        curtain1.startAnimation(sa1);
        curtain2.startAnimation(sa2);
    }

    private void animateFlash() {
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setRepeatMode(2);
        aa.setRepeatCount(1);
        aa.setInterpolator(new AccelerateInterpolator(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
        aa.setDuration(200);
        final View flash = this.f97r.findViewById(C1283R.id.cameraFlashView);
        flash.setBackgroundColor(-1);
        aa.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                flash.setVisibility(8);
            }
        });
        flash.setVisibility(0);
        flash.startAnimation(aa);
    }

    private void animateDoneButton() {
        View buttonFrame = this.f97r.findViewById(C1283R.id.camraButtonFrame);
        ScaleAnimation sa = new ScaleAnimation(1.0f, CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR, 1.0f, CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        sa.setDuration(200);
        sa.setRepeatMode(2);
        sa.setRepeatCount(1);
        sa.setInterpolator(new AnticipateInterpolator());
        buttonFrame.startAnimation(sa);
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                TakePhotoFragment.this.mDoneButton.setVisibility(0);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                TakePhotoFragment.this.mOkButton.setVisibility(8);
            }
        });
        aa.setDuration(100);
        this.mOkButton.startAnimation(aa);
    }

    private void dimOkButton() {
        AlphaAnimation aa = new AlphaAnimation(1.0f, CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR);
        aa.setDuration(300);
        aa.setRepeatMode(2);
        aa.setRepeatCount(100);
        aa.setInterpolator(new AccelerateDecelerateInterpolator());
        this.mOkButton.startAnimation(aa);
    }

    private void animateBlackFlashStart() {
        AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setInterpolator(new AccelerateInterpolator(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
        aa.setDuration(200);
        final View flash = this.f97r.findViewById(C1283R.id.cameraFlashView);
        flash.setBackgroundColor(-16777216);
        aa.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
                flash.setVisibility(0);
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                TakePhotoFragment.this.mPreview.reset();
                TakePhotoFragment.this.setUpFragment();
                TakePhotoFragment.this.animateBlackFlashEnd();
            }
        });
        flash.startAnimation(aa);
    }

    private void animateBlackFlashEnd() {
        AlphaAnimation aa = new AlphaAnimation(1.0f, 0.0f);
        aa.setInterpolator(new AccelerateInterpolator(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
        aa.setDuration(200);
        final View flash = this.f97r.findViewById(C1283R.id.cameraFlashView);
        flash.setBackgroundColor(-16777216);
        flash.setVisibility(0);
        aa.setAnimationListener(new AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationRepeat(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                flash.setVisibility(8);
            }
        });
        flash.startAnimation(aa);
    }

    private void buttonAlphaAnim(View v, float from, float to) {
        AlphaAnimation aa = new AlphaAnimation(from, to);
        aa.setInterpolator(new AccelerateInterpolator(LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
        aa.setDuration(200);
        aa.setFillAfter(true);
        v.startAnimation(aa);
    }

    public static void cleanUpFiles(String fileName) {
        if (fileName == null) {
            fileName = IMAGE_FILE_NAME;
        }
        final String prefix = fileName;
        for (File f : new File(ResManager.mAppDir).listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.startsWith(prefix);
            }
        })) {
            f.delete();
        }
    }

    private void lockOrientation() {
        getActivity().setRequestedOrientation(getScreenOrientation());
    }

    private void unlockOrientation() {
        getActivity().setRequestedOrientation(4);
    }

    private int getScreenOrientation() {
        int rotation = getActivity().getWindowManager().getDefaultDisplay().getRotation();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        if (((rotation == 0 || rotation == 2) && height > width) || ((rotation == 1 || rotation == 3) && width > height)) {
            switch (rotation) {
                case 0:
                    return 1;
                case 1:
                    return 0;
                case 2:
                    return 9;
                case 3:
                    return 8;
                default:
                    return 1;
            }
        }
        switch (rotation) {
            case 0:
                return 0;
            case 1:
                return 1;
            case 2:
                return 8;
            case 3:
                return 9;
            default:
                return 0;
        }
    }
}
