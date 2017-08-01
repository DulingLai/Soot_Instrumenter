package com.waze.view.popups;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.R;
import com.waze.ifs.ui.WazeSwitchView;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.HeightAnimation;

public class CarTypeOptionView extends FrameLayout {
    private TextView mCarTypeDescriptionLabel;
    private ImageView mCarTypeImage;
    private TextView mCarTypeTitleLabel;
    private int mIndex;
    private boolean mIsExpanded;
    private boolean mIsSelected;
    private TextView mKeepSettingsLabel;
    private WazeSwitchView mKeepSettingsSwitch;
    private CarTypeOptionViewListener mListener;
    private ImageView mSelectedImage;

    public interface CarTypeOptionViewListener {
        void onCheckedChanged(int i, boolean z);

        void onTapped(int i);
    }

    class C30821 implements SettingsToggleCallback {
        C30821() {
        }

        public void onToggle(boolean bOn) {
            if (CarTypeOptionView.this.mListener != null) {
                CarTypeOptionView.this.mListener.onCheckedChanged(CarTypeOptionView.this.mIndex, bOn);
            }
        }
    }

    class C30832 implements OnClickListener {
        C30832() {
        }

        public void onClick(View v) {
            CarTypeOptionView.this.mKeepSettingsSwitch.toggle();
        }
    }

    class C30843 implements OnClickListener {
        C30843() {
        }

        public void onClick(View v) {
            if (CarTypeOptionView.this.mListener != null) {
                CarTypeOptionView.this.mListener.onTapped(CarTypeOptionView.this.mIndex);
            }
        }
    }

    public CarTypeOptionView(Context context) {
        this(context, null);
    }

    public CarTypeOptionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarTypeOptionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.car_type_option_view, null);
        this.mCarTypeImage = (ImageView) content.findViewById(C1283R.id.imgCarTypeIcon);
        this.mCarTypeTitleLabel = (TextView) content.findViewById(C1283R.id.lblCarTypeTitle);
        this.mCarTypeDescriptionLabel = (TextView) content.findViewById(C1283R.id.lblCarTypeDescription);
        this.mKeepSettingsLabel = (TextView) content.findViewById(C1283R.id.lblKeepForThisDriveOnly);
        this.mSelectedImage = (ImageView) content.findViewById(C1283R.id.imgCarTypeSelected);
        this.mKeepSettingsSwitch = (WazeSwitchView) content.findViewById(C1283R.id.keepForThisDriveSwitch);
        if (attrs != null) {
            TypedArray attrArray = getContext().obtainStyledAttributes(attrs, R.styleable.CarTypeOptionView);
            Drawable iconResource = attrArray.getDrawable(0);
            String titleText = attrArray.getString(1);
            String descriptionText = attrArray.getString(2);
            this.mIndex = attrArray.getInteger(3, 0);
            this.mCarTypeImage.setImageDrawable(iconResource);
            this.mCarTypeTitleLabel.setText(titleText);
            setDescription(descriptionText);
            attrArray.recycle();
        }
        this.mKeepSettingsSwitch.setOnChecked(new C30821());
        this.mKeepSettingsLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_KEEP_SETTINGS_TEXT));
        this.mKeepSettingsSwitch.setOnClickListener(new C30832());
        ((RelativeLayout) content.findViewById(C1283R.id.frontContainer)).setOnClickListener(new C30843());
        addView(content);
    }

    public void removeImage() {
        this.mCarTypeImage.setVisibility(8);
    }

    public void expand() {
        if (!this.mIsExpanded) {
            this.mIsExpanded = true;
            int collapsedHeight = PixelMeasure.dimension(C1283R.dimen.carTypeOptionViewHeight);
            HeightAnimation heightAnimation = new HeightAnimation(this, collapsedHeight, collapsedHeight * 2);
            heightAnimation.setDuration(200);
            startAnimation(heightAnimation);
        }
    }

    public void collapse(boolean animated) {
        if (this.mIsExpanded) {
            this.mIsExpanded = false;
            int collapsedHeight = PixelMeasure.dimension(C1283R.dimen.carTypeOptionViewHeight);
            int expandedHeight = collapsedHeight * 2;
            if (animated) {
                HeightAnimation heightAnimation = new HeightAnimation(this, expandedHeight, collapsedHeight);
                heightAnimation.setDuration(200);
                startAnimation(heightAnimation);
                return;
            }
            getLayoutParams().height = collapsedHeight;
            setLayoutParams(getLayoutParams());
        }
    }

    public void setTypeSelected(boolean isSelected) {
        if (this.mIsSelected != isSelected) {
            this.mIsSelected = isSelected;
            this.mSelectedImage.setVisibility(isSelected ? 0 : 4);
        }
    }

    public boolean isExpanded() {
        return this.mIsExpanded;
    }

    public boolean isTypeSelected() {
        return this.mIsSelected;
    }

    public int getIndex() {
        return this.mIndex;
    }

    public void setIndex(int index) {
        this.mIndex = index;
    }

    public void setTitle(String title) {
        this.mCarTypeTitleLabel.setText(title);
    }

    public void setDescription(String description) {
        this.mCarTypeDescriptionLabel.setText(description);
        if (TextUtils.isEmpty(description)) {
            this.mCarTypeDescriptionLabel.setVisibility(8);
        } else {
            this.mCarTypeDescriptionLabel.setVisibility(0);
        }
    }

    public void setListener(CarTypeOptionViewListener listener) {
        this.mListener = listener;
    }

    public void setKeepSettingsOnlyForNow(boolean keepSettings) {
        this.mKeepSettingsSwitch.setValue(keepSettings);
    }

    public boolean isKeepSettingsOnlyForNow() {
        return this.mKeepSettingsSwitch.isChecked();
    }
}
