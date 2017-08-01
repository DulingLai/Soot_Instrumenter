package com.waze.share;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.messages.QuestionData;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.web.SimpleWebActivity;

public class EncouragementDialog extends Dialog implements ITrackOrientation {
    public static final int MAX_HEIGHT = PixelMeasure.dp(DisplayStrings.DS_RECORDING___);
    public static final int MAX_WIDTH = PixelMeasure.dp(360);
    private final QuestionData mData;
    private NativeManager mNm = AppService.getNativeManager();
    private final ActivityBase mParent;

    class C28031 implements OnCancelListener {
        C28031() {
        }

        public void onCancel(DialogInterface dialog) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ENCOURAGEMENT_CLICKED_BUTTON).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ID, EncouragementDialog.this.mData.QuestionID).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "BACK").addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_IMAGE_NAME, EncouragementDialog.this.mData.ImageUrl).send();
        }
    }

    class C28053 implements OnClickListener {
        C28053() {
        }

        public void onClick(View v) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ENCOURAGEMENT_CLICKED_BUTTON).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ID, EncouragementDialog.this.mData.QuestionID).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "X").addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_IMAGE_NAME, EncouragementDialog.this.mData.ImageUrl).send();
            EncouragementDialog.this.dismiss();
        }
    }

    class C28064 implements Runnable {
        C28064() {
        }

        public void run() {
            EncouragementDialog.this.dismiss();
        }
    }

    public EncouragementDialog(ActivityBase parent, QuestionData data) {
        super(parent, C1283R.style.PopInDialog);
        this.mParent = parent;
        this.mData = data;
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (initLayout()) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ENCOURAGEMENT_DISPLAYED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ID, this.mData.QuestionID).addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_IMAGE_NAME, this.mData.ImageUrl).send();
            setOnCancelListener(new C28031());
        }
    }

    protected void onStart() {
        super.onStart();
        if (this.mParent instanceof MainActivity) {
            ((MainActivity) this.mParent).addOrientationTracker(this);
        }
    }

    protected void onStop() {
        super.onStop();
        if (this.mParent instanceof MainActivity) {
            ((MainActivity) this.mParent).removeOrientationTracker(this);
        }
    }

    public void show() {
        super.show();
        getWindow().setBackgroundDrawable(new ColorDrawable(0));
        getWindow().addFlags(2);
        updateSize();
    }

    private void updateSize() {
        Display display = this.mParent.getWindowManager().getDefaultDisplay();
        int width = display.getWidth();
        int height = display.getHeight();
        if (this.mParent.getResources().getConfiguration().orientation == 1) {
            if (width > MAX_WIDTH) {
                width = MAX_WIDTH;
            }
            if (height > MAX_HEIGHT) {
                height = MAX_HEIGHT;
            }
        } else {
            if (width > MAX_HEIGHT) {
                width = MAX_HEIGHT;
            }
            if (height > MAX_WIDTH) {
                height = MAX_WIDTH;
            }
        }
        getWindow().setLayout(width, height);
    }

    private void setLink(int resId, String visibleHTML, final String targetURL, final String title, final String description) {
        TextView tvLink = (TextView) findViewById(resId);
        tvLink.setVisibility(0);
        tvLink.setText(Html.fromHtml(visibleHTML));
        tvLink.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (targetURL.contains(NativeManager.WAZE_URL_PATTERN)) {
                    EncouragementDialog.this.mParent.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(targetURL)));
                } else {
                    Intent webView = new Intent(EncouragementDialog.this.mParent, SimpleWebActivity.class);
                    webView.putExtra(SimpleWebActivity.EXTRA_URL, targetURL);
                    webView.putExtra(SimpleWebActivity.EXTRA_TITLE, title);
                    EncouragementDialog.this.mParent.startActivityForResult(webView, 0);
                }
                EncouragementDialog.this.dismiss();
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ENCOURAGEMENT_CLICKED_BUTTON).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ID, EncouragementDialog.this.mData.QuestionID).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, description).addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_IMAGE_NAME, EncouragementDialog.this.mData.ImageUrl).send();
            }
        });
    }

    public void onOrientationChanged(int orientation) {
        updateSize();
        initLayout();
    }

    private boolean initLayout() {
        String failedNoImage = null;
        setContentView(C1283R.layout.encouragement);
        TextView tvTitle = (TextView) findViewById(C1283R.id.encourageMainTitle);
        if (this.mData.Text != null) {
            tvTitle.setText(Html.fromHtml(this.mData.Text));
        } else {
            tvTitle.setVisibility(8);
        }
        TextView tvSubtitle = (TextView) findViewById(C1283R.id.encourageSubtitle);
        if (this.mData.Subtitle != null) {
            tvSubtitle.setText(Html.fromHtml(this.mData.Subtitle));
        } else {
            tvSubtitle.setVisibility(8);
        }
        ImageView ivBackground = (ImageView) findViewById(C1283R.id.encourageBackground);
        if (this.mData.ImageUrl != null) {
            Bitmap image = ResManager.GetEncBitmap(this.mNm.getEncImagePathNTV(this.mData.ImageUrl));
            if (image == null) {
                failedNoImage = "BACKGROUND";
            }
            ivBackground.setImageDrawable(new BitmapDrawable(image));
        } else {
            ivBackground.setVisibility(8);
        }
        findViewById(C1283R.id.encourageX).setOnClickListener(new C28053());
        View mainButton = findViewById(C1283R.id.button1);
        mainButton.setVisibility(0);
        if (!setupButton(mainButton, C1283R.id.button1icon, C1283R.id.button1text, this.mData.SubText1, this.mData.Icon1, this.mData.BackgroundRGB1, this.mData.TextRGB1, this.mData.ActionText1, "MAIN")) {
            failedNoImage = "ICON1";
        }
        View secondButton = findViewById(C1283R.id.button2);
        if (this.mData.SubText2 != null) {
            secondButton.setVisibility(0);
            if (!setupButton(secondButton, C1283R.id.button2icon, C1283R.id.button2text, this.mData.SubText2, this.mData.Icon2, this.mData.BackgroundRGB2, this.mData.TextRGB2, this.mData.ActionText2, "SECOND")) {
                failedNoImage = "ICON2";
            }
        } else {
            secondButton.setVisibility(8);
        }
        View thirdButton = findViewById(C1283R.id.button3);
        if (this.mData.SubText3 == null) {
            thirdButton.setVisibility(8);
        } else if (!setupButton(thirdButton, C1283R.id.button3icon, C1283R.id.button3text, this.mData.SubText3, this.mData.Icon3, this.mData.BackgroundRGB3, this.mData.TextRGB3, this.mData.ActionText3, "THIRD")) {
            failedNoImage = "ICON3";
        }
        if (this.mData.ButtonOrientation == 1) {
            ((LinearLayout) findViewById(C1283R.id.EncouragementButtons)).setOrientation(1);
            LayoutParams params = (LinearLayout.LayoutParams) mainButton.getLayoutParams();
            params.width = -1;
            mainButton.setLayoutParams(params);
            secondButton.setLayoutParams(params);
            thirdButton.setLayoutParams(params);
        }
        if (!(this.mData.LinkHtml1 == null || this.mData.LinkUrl1 == null)) {
            setLink(C1283R.id.encourageLink, this.mData.LinkHtml1, this.mData.LinkUrl1, this.mData.LinkTitle1, "LINK1");
        }
        if (!(this.mData.LinkHtml2 == null || this.mData.LinkUrl2 == null)) {
            setLink(C1283R.id.encourageLegal, this.mData.LinkHtml2, this.mData.LinkUrl2, this.mData.LinkTitle2, "LINK2");
        }
        if (failedNoImage == null) {
            return true;
        }
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ENCOURAGEMENT_SKIPPED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ID, this.mData.QuestionID).addParam("REASON", "NO_IMAGE").addParam("IMAGE", failedNoImage).addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_IMAGE_NAME, this.mData.ImageUrl).send();
        hide();
        this.mParent.post(new C28064());
        return false;
    }

    private boolean setupButton(View button, int iconId, int labelId, String text, String icon, int backgroundRGB, int textRGB, String url, String buttonName) {
        final String str = url;
        final String str2 = buttonName;
        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (!(str == null || str.isEmpty())) {
                    EncouragementDialog.this.mParent.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)));
                }
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ENCOURAGEMENT_CLICKED_BUTTON).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_ID, EncouragementDialog.this.mData.QuestionID).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, str2).addParam(AnalyticsEvents.ANALYTICS_EVENT_VALUE_IMAGE_NAME, EncouragementDialog.this.mData.ImageUrl).send();
                EncouragementDialog.this.dismiss();
            }
        });
        if (Color.alpha(backgroundRGB) > 0) {
            Drawable background = this.mParent.getResources().getDrawable(C1283R.drawable.white_button).mutate();
            Drawable pressed = this.mParent.getResources().getDrawable(C1283R.drawable.white_button_prs).mutate();
            background.setColorFilter(backgroundRGB, Mode.MULTIPLY);
            pressed.setColorFilter(backgroundRGB, Mode.MULTIPLY);
            StateListDrawable states = new StateListDrawable();
            states.addState(new int[]{16842919}, pressed);
            states.addState(new int[0], background);
            button.setBackgroundDrawable(states);
        }
        ImageView ivIcon = (ImageView) findViewById(iconId);
        if (icon == null || icon.isEmpty()) {
            ivIcon.setVisibility(8);
        } else {
            Bitmap image = ResManager.GetEncBitmap(this.mNm.getEncImagePathNTV(icon));
            if (image == null) {
                return false;
            }
            ivIcon.setImageDrawable(new BitmapDrawable(image));
            ivIcon.getLayoutParams().width = image.getWidth();
            ivIcon.getLayoutParams().height = image.getHeight();
        }
        TextView tvLabel = (TextView) findViewById(labelId);
        if (text == null || text.isEmpty()) {
            tvLabel.setVisibility(8);
        } else {
            tvLabel.setText(text);
            if (Color.alpha(textRGB) > 0) {
                tvLabel.setTextColor(textRGB);
            }
        }
        return true;
    }
}
