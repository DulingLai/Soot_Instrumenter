package com.waze.view.popups;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.MoodManager;
import com.waze.NativeManager;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.CanvasFont;
import com.waze.share.ShareUtility;
import com.waze.utils.ImageRepository;
import com.waze.view.button.ReportMenuButton;
import com.waze.view.dialogs.PhotoDialog;

public abstract class GenericTakeover extends PopUp {
    private static final int[] RMB_BG_COLORS = new int[]{-8859415, -28528, -3034903, -10098765, -6830380, -13468526, -5311};
    protected Context mContext = null;
    private boolean mIsShown = false;
    protected LayoutManager mLayoutManager;
    private OnClickListener[] mOnClickListeners = new OnClickListener[3];
    private PhotoDialog mPhotoDialog;
    private String mPictureUrl = null;

    class C31531 implements OnClickListener {
        C31531() {
        }

        public void onClick(View v) {
            GenericTakeover.this.openReportPhotoFromView(v, GenericTakeover.this.mPictureUrl, AppService.getActiveActivity());
        }
    }

    class C31542 implements OnClickListener {
        C31542() {
        }

        public void onClick(View v) {
            GenericTakeover.this.openReportPhotoFromView(v, GenericTakeover.this.mPictureUrl, AppService.getActiveActivity());
        }
    }

    class C31553 implements OnDismissListener {
        C31553() {
        }

        public void onDismiss(DialogInterface dialog) {
            GenericTakeover.this.mPhotoDialog = null;
        }
    }

    public boolean isShown() {
        return this.mIsShown;
    }

    public GenericTakeover(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mContext = context;
        this.mLayoutManager = layoutManager;
        init();
    }

    public void dismiss() {
        this.mIsShown = false;
        this.mLayoutManager.dismiss(this);
    }

    protected void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.generic_takeover, this);
    }

    public static int getResourceId(Context context, String image) {
        return context.getResources().getIdentifier("drawable/" + image.replace("-", "_").toLowerCase(), null, context.getPackageName());
    }

    void setLine1(String str) {
        TextView textView = (TextView) findViewById(C1283R.id.genTakeOverLine1);
        if (str == null) {
            textView.setVisibility(8);
        }
        textView.setVisibility(0);
        textView.setText(str);
    }

    void setLine2(String str) {
        TextView textView = (TextView) findViewById(C1283R.id.genTakeOverLine2);
        if (str == null) {
            textView.setVisibility(8);
        }
        textView.setVisibility(0);
        textView.setText(str);
    }

    void setLine3(String str) {
        TextView textView = (TextView) findViewById(C1283R.id.genTakeOverLine3);
        if (str == null || str.isEmpty()) {
            textView.setVisibility(8);
        }
        textView.setVisibility(0);
        textView.setText(str);
    }

    String getLine(int resId) {
        TextView textView = (TextView) findViewById(resId);
        if (textView != null && textView.getVisibility() == 0) {
            return textView.getText().toString();
        }
        return null;
    }

    void setLine(int resId, String str) {
        TextView textView = (TextView) findViewById(resId);
        if (str == null || str.isEmpty()) {
            textView.setVisibility(8);
        }
        textView.setVisibility(0);
        textView.setText(str);
    }

    void setIcon(String icon) {
        findViewById(C1283R.id.genTakeOverSmallIcon).setVisibility(8);
        setGenImageToIcon(findViewById(C1283R.id.genTakeOverImageFrame), getResourceId(this.mContext, icon));
    }

    static void setGenImageToIcon(View r, int imageId) {
        r.findViewById(C1283R.id.genTakeOverUserImageContainer).setVisibility(8);
        r.findViewById(C1283R.id.genTakeOverPictureContainer).setVisibility(8);
        ImageView image = (ImageView) r.findViewById(C1283R.id.genTakeOverIcon);
        image.setVisibility(0);
        image.setImageResource(imageId);
    }

    void setUserImageMain(String userImageUrl, String name, String mood) {
        findViewById(C1283R.id.genTakeOverSmallIcon).setVisibility(8);
        setGenImageToUser(findViewById(C1283R.id.genTakeOverImageFrame), userImageUrl, name, getResourceDrawable(this.mContext, mood));
    }

    static void setGenImageToUser(View r, String userImageUrl, String name, Drawable mood) {
        r.findViewById(C1283R.id.genTakeOverPictureContainer).setVisibility(8);
        if (userImageUrl != null) {
            r.findViewById(C1283R.id.genTakeOverIcon).setVisibility(8);
            r.findViewById(C1283R.id.genTakeOverUserImageContainer).setVisibility(0);
            TextView initials = (TextView) r.findViewById(C1283R.id.genTakeOverUserImageInitials);
            initials.setText(ShareUtility.getInitials(name));
            String str = userImageUrl;
            ImageRepository.instance.getImage(str, 2, (ImageView) r.findViewById(C1283R.id.genTakeOverUserImage), initials, AppService.getActiveActivity());
            return;
        }
        r.findViewById(C1283R.id.genTakeOverUserImageContainer).setVisibility(8);
        ImageView icon = (ImageView) r.findViewById(C1283R.id.genTakeOverIcon);
        icon.setVisibility(0);
        icon.setImageDrawable(mood);
    }

    void setPicture(String pictureUrl) {
        this.mPictureUrl = pictureUrl;
        setGenImageToPicture(findViewById(C1283R.id.genTakeOverImageFrame), pictureUrl);
        findViewById(C1283R.id.genTakeOverPictureContainer).setOnClickListener(new C31531());
    }

    static void setGenImageToPicture(View r, String userImageUrl) {
        r.findViewById(C1283R.id.genTakeOverIcon).setVisibility(8);
        r.findViewById(C1283R.id.genTakeOverUserImageContainer).setVisibility(8);
        r.findViewById(C1283R.id.genTakeOverPictureContainer).setVisibility(0);
        String str = userImageUrl;
        ImageRepository.instance.getImage(str, 1, (ImageView) r.findViewById(C1283R.id.genTakeOverPicture), null, AppService.getActiveActivity());
    }

    void setSmallIcon(int iconResId) {
        ImageView image = (ImageView) findViewById(C1283R.id.genTakeOverSmallIcon);
        image.setVisibility(0);
        image.setImageResource(iconResId);
    }

    void setSmallIcon(String icon) {
        setSmallIcon(getResourceId(this.mContext, icon));
    }

    void setDistance(String distance, String unit) {
        setLine1(distance + " " + unit + " " + AppService.getNativeManager().getLanguageString(333));
    }

    void setDescription(String descriptionStr) {
        TextView textView = (TextView) findViewById(C1283R.id.genTakeOverLine3);
        if (descriptionStr == null || descriptionStr.isEmpty()) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
        textView.setText(descriptionStr);
    }

    protected void setUser(String senderStr) {
        ((TextView) findViewById(C1283R.id.genTakeOverUser)).setText(senderStr);
    }

    protected void setBottomUserGone() {
        findViewById(C1283R.id.genTakeOverUser).setVisibility(8);
        findViewById(C1283R.id.genTakeOverUserMoodRmb).setVisibility(8);
    }

    protected void setTime(String whenStr) {
        TextView timeTv = (TextView) findViewById(C1283R.id.genTakeOverReportTime);
        if (whenStr == null || whenStr.isEmpty()) {
            timeTv.setVisibility(8);
            return;
        }
        timeTv.setVisibility(0);
        timeTv.setText(whenStr.toUpperCase());
    }

    protected void setTime(long timeSec) {
        setTime(NativeManager.getInstance().getRelativeTimeStringNTV(timeSec, true));
    }

    void setImage(String imageUrl, String Icon) {
    }

    void setButton3(int bgResId, String content, boolean enabled, OnClickListener onClick) {
        setButton(bgResId, content, enabled, findViewById(C1283R.id.genTakeOverRight3Button), (TextView) findViewById(C1283R.id.genTakeOverRight3ButtonText), onClick);
        this.mOnClickListeners[2] = onClick;
    }

    void setButton3Gone() {
        findViewById(C1283R.id.genTakeOverRight3Button).setVisibility(8);
        this.mOnClickListeners[2] = null;
    }

    static void setButton(int bgResId, String content, boolean enabled, View button, TextView tvText, OnClickListener onClick) {
        button.setEnabled(enabled);
        tvText.setBackgroundResource(bgResId);
        tvText.setText(content);
        button.setOnClickListener(onClick);
    }

    void setButton2(int bgResId, String content, boolean enabled, OnClickListener onClick) {
        setButton(bgResId, content, enabled, findViewById(C1283R.id.genTakeOverRight2Button), (TextView) findViewById(C1283R.id.genTakeOverRight2ButtonText), onClick);
        this.mOnClickListeners[1] = onClick;
    }

    void setButton2(int bgResId, int number, boolean enabled, OnClickListener onClick) {
        String content = "";
        if (number > 0) {
            content = content + number;
        }
        setButton2(bgResId, content, enabled, onClick);
    }

    void setButton2Gone() {
        findViewById(C1283R.id.genTakeOverRight2Button).setVisibility(8);
        this.mOnClickListeners[1] = null;
    }

    void setButton1(int bgResId, String content, boolean enabled, OnClickListener onClick) {
        setButton(bgResId, content, enabled, findViewById(C1283R.id.genTakeOverRight1Button), (TextView) findViewById(C1283R.id.genTakeOverRight1ButtonText), onClick);
        this.mOnClickListeners[0] = onClick;
    }

    void setButton1(int bgResId, int number, boolean enabled, OnClickListener onClick) {
        String content = "";
        if (number > 0) {
            content = content + number;
        }
        setButton1(bgResId, content, enabled, onClick);
    }

    void setButton1Gone() {
        findViewById(C1283R.id.genTakeOverRight1Button).setVisibility(8);
        this.mOnClickListeners[0] = null;
    }

    void setUserImageBottom(String userImageUrl, String moodName, String userId) {
        ReportMenuButton userImage = (ReportMenuButton) findViewById(C1283R.id.genTakeOverUserMoodRmb);
        userImage.skipAnimation();
        userImage.setImageResource(MoodManager.getBigMoodDrawableId(moodName));
        userImage.setBackgroundColor(getUserBackgroundColor(userId));
    }

    public static int getUserBackgroundColor(String userId) {
        return RMB_BG_COLORS[Math.abs(userId.hashCode()) % RMB_BG_COLORS.length];
    }

    public static Drawable getResourceDrawable(Context context, String image) {
        return MoodManager.getMoodDrawable(context, image);
    }

    public boolean onBackPressed() {
        this.mLayoutManager.callCloseAllPopups(1);
        return true;
    }

    protected void initView() {
        if (this.mIsShown) {
            dismiss();
        }
        this.mIsShown = true;
    }

    public void hide() {
        CarpoolNativeManager.getInstance().openCarpoolTakeoverIfNecessary();
        dismiss();
    }

    public void setShiftEffect(boolean bOnLeft, float shift) {
        float trans;
        super.setShiftEffect(bOnLeft, shift);
        float alpha = 1.0f - (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * shift);
        if (alpha < 0.0f) {
            alpha = 0.0f;
        }
        for (int buttonId : new int[]{C1283R.id.genTakeOverRight1Button, C1283R.id.genTakeOverRight2Button, C1283R.id.genTakeOverRight3Button, C1283R.id.genTakeOverUserMoodRmb, C1283R.id.genTakeOverUser, C1283R.id.genTakeOverReportTime}) {
            View button = findViewById(buttonId);
            if (button.getVisibility() == 0) {
                button.setAlpha(alpha);
            }
        }
        if (bOnLeft) {
            trans = -shift;
        } else {
            trans = shift;
        }
        float animationForce = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        int[] iArr = new int[3];
        for (int lineId : new int[]{C1283R.id.genTakeOverLine1, C1283R.id.genTakeOverLine2, C1283R.id.genTakeOverLine3}) {
            View line = findViewById(lineId);
            if (line.getVisibility() == 0) {
                TranslateAnimation ta = new TranslateAnimation(2, animationForce * trans, 2, animationForce * trans, 2, 0.0f, 2, 0.0f);
                ta.setFillAfter(true);
                line.startAnimation(ta);
            }
            animationForce += CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }
    }

    public void setPageIndicatorShown(boolean shown) {
        super.setPageIndicatorShown(shown);
        findViewById(C1283R.id.genTakeOverLayout).setPadding(0, getResources().getDimensionPixelSize(shown ? C1283R.dimen.takeover_top_padding : C1283R.dimen.takeover_top_padding_no_indicator), 0, 0);
    }

    public void onOrientationChanged() {
        super.onOrientationChanged();
        ImageView iconView = (ImageView) findViewById(C1283R.id.genTakeOverIcon);
        boolean isIcon = iconView.getVisibility() == 0;
        Drawable icon = iconView.getDrawable();
        boolean isImage = findViewById(C1283R.id.genTakeOverUserImageContainer).getVisibility() == 0;
        Drawable userImage = ((ImageView) findViewById(C1283R.id.genTakeOverUserImage)).getDrawable();
        String userInitials = getLine(C1283R.id.genTakeOverUserImageInitials);
        boolean isPicture = findViewById(C1283R.id.genTakeOverPictureContainer).getVisibility() == 0;
        Drawable picture = ((ImageView) findViewById(C1283R.id.genTakeOverPicture)).getDrawable();
        ImageView smallIconView = (ImageView) findViewById(C1283R.id.genTakeOverSmallIcon);
        boolean hasSmallIcon = smallIconView.getVisibility() == 0;
        Drawable smallIcon = smallIconView.getDrawable();
        String reportTime = getLine(C1283R.id.genTakeOverReportTime);
        String line1 = getLine(C1283R.id.genTakeOverLine1);
        String line2 = getLine(C1283R.id.genTakeOverLine2);
        String line3 = getLine(C1283R.id.genTakeOverLine3);
        ReportMenuButton userMoodRmb = (ReportMenuButton) findViewById(C1283R.id.genTakeOverUserMoodRmb);
        userMoodRmb.skipAnimation();
        boolean hasUserMood = userMoodRmb.getVisibility() == 0;
        int userMoodBgColor = userMoodRmb.getBackgroundColor();
        int userMoodImageRes = userMoodRmb.getImageResId();
        String bottomUser = getLine(C1283R.id.genTakeOverUser);
        View pill1 = findViewById(C1283R.id.genTakeOverRight1Button);
        TextView pill1TextView = (TextView) findViewById(C1283R.id.genTakeOverRight1ButtonText);
        boolean hasPill1 = pill1.getVisibility() == 0;
        boolean pill1IsEnabled = pill1.isEnabled();
        Drawable pill1bg = pill1TextView.getBackground();
        String pill1Text = pill1TextView.getText().toString();
        View pill2 = findViewById(C1283R.id.genTakeOverRight2Button);
        TextView pill2TextView = (TextView) findViewById(C1283R.id.genTakeOverRight2ButtonText);
        boolean hasPill2 = pill2.getVisibility() == 0;
        boolean pill2IsEnabled = pill2.isEnabled();
        Drawable pill2bg = pill2TextView.getBackground();
        String pill2Text = pill2TextView.getText().toString();
        View pill3 = findViewById(C1283R.id.genTakeOverRight3Button);
        TextView pill3TextView = (TextView) findViewById(C1283R.id.genTakeOverRight3ButtonText);
        boolean hasPill3 = pill3.getVisibility() == 0;
        boolean pill3IsEnabled = pill3.isEnabled();
        Drawable pill3bg = pill3TextView.getBackground();
        String pill3Text = pill3TextView.getText().toString();
        removeAllViews();
        init();
        iconView = (ImageView) findViewById(C1283R.id.genTakeOverIcon);
        iconView.setVisibility(isIcon ? 0 : 8);
        if (isIcon) {
            iconView.setImageDrawable(icon);
        }
        findViewById(C1283R.id.genTakeOverUserImageContainer).setVisibility(isImage ? 0 : 8);
        if (isImage) {
            ((ImageView) findViewById(C1283R.id.genTakeOverUserImage)).setImageDrawable(userImage);
            setLine(C1283R.id.genTakeOverUserImageInitials, userInitials);
        }
        View v = findViewById(C1283R.id.genTakeOverPictureContainer);
        v.setVisibility(isPicture ? 0 : 8);
        if (isPicture) {
            ((ImageView) findViewById(C1283R.id.genTakeOverPicture)).setImageDrawable(picture);
            if (this.mPictureUrl != null) {
                v.setOnClickListener(new C31542());
            }
        }
        smallIconView = (ImageView) findViewById(C1283R.id.genTakeOverSmallIcon);
        smallIconView.setVisibility(hasSmallIcon ? 0 : 8);
        if (hasSmallIcon) {
            smallIconView.setImageDrawable(smallIcon);
        }
        setLine(C1283R.id.genTakeOverReportTime, reportTime);
        setLine(C1283R.id.genTakeOverLine1, line1);
        setLine(C1283R.id.genTakeOverLine2, line2);
        setLine(C1283R.id.genTakeOverLine3, line3);
        userMoodRmb = (ReportMenuButton) findViewById(C1283R.id.genTakeOverUserMoodRmb);
        userMoodRmb.skipAnimation();
        userMoodRmb.setVisibility(hasUserMood ? 0 : 8);
        if (hasUserMood) {
            userMoodRmb.setBackgroundColor(userMoodBgColor);
            userMoodRmb.setImageResource(userMoodImageRes);
        }
        setLine(C1283R.id.genTakeOverUser, bottomUser);
        pill1 = findViewById(C1283R.id.genTakeOverRight1Button);
        pill1.setVisibility(hasPill1 ? 0 : 8);
        if (hasPill1) {
            pill1TextView = (TextView) findViewById(C1283R.id.genTakeOverRight1ButtonText);
            pill1.setEnabled(pill1IsEnabled);
            pill1TextView.setBackgroundDrawable(pill1bg);
            pill1TextView.setText(pill1Text);
            pill1.setOnClickListener(this.mOnClickListeners[0]);
        }
        pill2 = findViewById(C1283R.id.genTakeOverRight2Button);
        pill2.setVisibility(hasPill2 ? 0 : 8);
        if (hasPill2) {
            pill2TextView = (TextView) findViewById(C1283R.id.genTakeOverRight2ButtonText);
            pill2.setEnabled(pill2IsEnabled);
            pill2TextView.setBackgroundDrawable(pill2bg);
            pill2TextView.setText(pill2Text);
            pill2.setOnClickListener(this.mOnClickListeners[1]);
        }
        pill3 = findViewById(C1283R.id.genTakeOverRight3Button);
        pill3.setVisibility(hasPill3 ? 0 : 8);
        if (hasPill3) {
            pill3TextView = (TextView) findViewById(C1283R.id.genTakeOverRight3ButtonText);
            pill3.setEnabled(pill3IsEnabled);
            pill3TextView.setBackgroundDrawable(pill3bg);
            pill3TextView.setText(pill3Text);
            pill3.setOnClickListener(this.mOnClickListeners[0]);
        }
        if (this.mPhotoDialog != null && this.mPhotoDialog.isShowing()) {
            this.mPhotoDialog.calculateSize();
        }
    }

    public int GetHeight() {
        return getChildAt(0).getMeasuredHeight();
    }

    public Rect getRect() {
        Rect rect = new Rect();
        getChildAt(0).getHitRect(rect);
        int[] loc = new int[2];
        getLocationInWindow(loc);
        rect.right += loc[0];
        rect.left += loc[0];
        rect.top += loc[1];
        rect.bottom += loc[1];
        return rect;
    }

    public void openReportPhotoFromView(View v, String imageUrl, ActivityBase ab) {
        Bundle imageLocation = new Bundle();
        int[] location = new int[2];
        v.getLocationInWindow(location);
        imageLocation.putInt("left", location[0]);
        imageLocation.putInt("top", location[1]);
        imageLocation.putInt("width", v.getWidth());
        imageLocation.putInt("height", v.getHeight());
        this.mPhotoDialog = new PhotoDialog(ab, imageUrl, imageLocation);
        this.mPhotoDialog.setCanceledOnTouchOutside(true);
        this.mPhotoDialog.show();
        this.mPhotoDialog.setOnDismissListener(new C31553());
    }
}
