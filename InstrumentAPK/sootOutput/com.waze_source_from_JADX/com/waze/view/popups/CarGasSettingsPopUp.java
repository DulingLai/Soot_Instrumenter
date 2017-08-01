package com.waze.view.popups;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.WazeSwitchView;
import com.waze.map.CanvasFont;
import com.waze.navigate.DriveToNativeManager;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.WazeSettingsView.SettingsToggleCallback;
import com.waze.strings.DisplayStrings;
import com.waze.utils.CarGasTypeManager;
import com.waze.utils.CarGasTypeManager.GasTypeLoadedListener;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.popups.CarTypeOptionView.CarTypeOptionViewListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CarGasSettingsPopUp extends FrameLayout {
    private boolean mAvoidTollsModeOnStart;
    private boolean mCarSaveEnabled;
    private CarTypeOptionViewListener mCarTypeButtonListener;
    private LinearLayout mCurrentlyDisplayContainer;
    private List<CarTypeOptionView> mGasOptionViews;
    private boolean mGasSaveEnabled;
    private RelativeLayout mGasTypeButton;
    private CarTypeOptionViewListener mGasTypeButtonListener;
    private FrameLayout mGasTypeCancelButton;
    private TextView mGasTypeCancelLabel;
    private LinearLayout mGasTypeContainer;
    private TextView mGasTypeContainerTitle;
    private LinearLayout mGasTypeOptionsContainer;
    private FrameLayout mGasTypeSaveButton;
    private TextView mGasTypeSaveLabel;
    private TextView mGasTypeSelectedLabel;
    private TextView mGasTypeTitleLabel;
    private CarTypeOptionView mPrivateCarOptionView;
    private LinearLayout mSettingsContainer;
    private TextView mSettingsTitleLabel;
    private boolean mStringsInitialized;
    private int mTappedCarIndex;
    private boolean mTappedCarIndexIsPermanent;
    private int mTappedGasIndex;
    private CarTypeOptionView mTaxiCarOptionView;
    private RelativeLayout mTollRoadButton;
    private TextView mTollRoadLabel;
    private WazeSwitchView mTollRoadSwitch;
    private RelativeLayout mVehicleTypeButton;
    private FrameLayout mVehicleTypeCancelButton;
    private TextView mVehicleTypeCancelLabel;
    private LinearLayout mVehicleTypeContainer;
    private TextView mVehicleTypeContainerTitle;
    private ImageView mVehicleTypeImage;
    private FrameLayout mVehicleTypeSaveButton;
    private TextView mVehicleTypeSaveLabel;
    private TextView mVehicleTypeSelectedLabel;
    private TextView mVehicleTypeTitleLabel;

    class C30731 implements CarTypeOptionViewListener {
        C30731() {
        }

        public void onCheckedChanged(int index, boolean isChecked) {
            CarGasSettingsPopUp.this.onCarTypeSwitchToggled(index, isChecked);
        }

        public void onTapped(int index) {
            CarGasSettingsPopUp.this.onCarTypeTapped(index);
        }
    }

    class C30742 implements CarTypeOptionViewListener {
        C30742() {
        }

        public void onCheckedChanged(int index, boolean isChecked) {
        }

        public void onTapped(int index) {
            CarGasSettingsPopUp.this.onGasTypeTapped(index);
        }
    }

    class C30753 implements OnClickListener {
        C30753() {
        }

        public void onClick(View v) {
            CarGasSettingsPopUp.this.transitionToVehicleType();
        }
    }

    class C30764 implements OnClickListener {
        C30764() {
        }

        public void onClick(View v) {
            CarGasSettingsPopUp.this.transitionToGasType();
        }
    }

    class C30775 implements OnClickListener {
        C30775() {
        }

        public void onClick(View v) {
            CarGasSettingsPopUp.this.hide();
        }
    }

    class C30786 implements OnClickListener {
        C30786() {
        }

        public void onClick(View v) {
            CarGasSettingsPopUp.this.saveVehicleType();
        }
    }

    class C30797 implements SettingsToggleCallback {
        C30797() {
        }

        public void onToggle(boolean bOn) {
            Analytics.log(bOn ? AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_ACTION_SHEET_AVOID_TOLL_ROADS_ON : AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_ACTION_SHEET_AVOID_TOLL_ROADS_OFF);
            ConfigManager.getInstance().setConfigValueBool(141, bOn);
        }
    }

    class C30808 implements OnClickListener {
        C30808() {
        }

        public void onClick(View v) {
            CarGasSettingsPopUp.this.mTollRoadSwitch.toggle();
        }
    }

    class C30819 implements OnClickListener {
        C30819() {
        }

        public void onClick(View v) {
            CarGasSettingsPopUp.this.saveGasType();
        }
    }

    public CarGasSettingsPopUp(Context context) {
        this(context, null);
    }

    public CarGasSettingsPopUp(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CarGasSettingsPopUp(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mCarTypeButtonListener = new C30731();
        this.mGasTypeButtonListener = new C30742();
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.car_gas_settings_popup, null);
        this.mSettingsContainer = (LinearLayout) content.findViewById(C1283R.id.settingsContainer);
        this.mVehicleTypeContainer = (LinearLayout) content.findViewById(C1283R.id.vehicleTypeContainer);
        this.mGasTypeContainer = (LinearLayout) content.findViewById(C1283R.id.gasTypeContainer);
        this.mSettingsTitleLabel = (TextView) content.findViewById(C1283R.id.lblSettingsTitle);
        this.mVehicleTypeButton = (RelativeLayout) content.findViewById(C1283R.id.btnVehicleType);
        this.mVehicleTypeImage = (ImageView) content.findViewById(C1283R.id.imgVehicleType);
        this.mVehicleTypeTitleLabel = (TextView) content.findViewById(C1283R.id.lblVehicleTypeTitle);
        this.mVehicleTypeSelectedLabel = (TextView) content.findViewById(C1283R.id.lblVehicleTypeSelected);
        this.mGasTypeButton = (RelativeLayout) content.findViewById(C1283R.id.btnGasType);
        this.mGasTypeTitleLabel = (TextView) content.findViewById(C1283R.id.lblGasTypeTitle);
        this.mGasTypeSelectedLabel = (TextView) content.findViewById(C1283R.id.lblGasTypeSelected);
        this.mTollRoadButton = (RelativeLayout) content.findViewById(C1283R.id.btnAvoidTollRoad);
        this.mTollRoadLabel = (TextView) content.findViewById(C1283R.id.lblAvoidTollRoadTitle);
        this.mTollRoadSwitch = (WazeSwitchView) content.findViewById(C1283R.id.tollRoadSwitch);
        this.mVehicleTypeContainerTitle = (TextView) content.findViewById(C1283R.id.lblVehicleContainerTitle);
        this.mPrivateCarOptionView = (CarTypeOptionView) content.findViewById(C1283R.id.carTypeOptionPrivate);
        this.mTaxiCarOptionView = (CarTypeOptionView) content.findViewById(C1283R.id.carTypeOptionTaxi);
        this.mVehicleTypeCancelButton = (FrameLayout) content.findViewById(C1283R.id.btnVehicleTypeCancel);
        this.mVehicleTypeSaveButton = (FrameLayout) content.findViewById(C1283R.id.btnVehicleTypeSave);
        this.mVehicleTypeCancelLabel = (TextView) content.findViewById(C1283R.id.lblVehicleTypeCancel);
        this.mVehicleTypeSaveLabel = (TextView) content.findViewById(C1283R.id.lblVehicleTypeSave);
        this.mGasTypeContainerTitle = (TextView) content.findViewById(C1283R.id.lblGasContainerTitle);
        this.mGasTypeOptionsContainer = (LinearLayout) content.findViewById(C1283R.id.gasTypeOptionsContainer);
        this.mGasTypeCancelButton = (FrameLayout) content.findViewById(C1283R.id.btnGasTypeCancel);
        this.mGasTypeSaveButton = (FrameLayout) content.findViewById(C1283R.id.btnGasTypeSave);
        this.mGasTypeCancelLabel = (TextView) content.findViewById(C1283R.id.lblGasTypeCancel);
        this.mGasTypeSaveLabel = (TextView) content.findViewById(C1283R.id.lblGasTypeSave);
        this.mVehicleTypeButton.setOnClickListener(new C30753());
        this.mGasTypeButton.setOnClickListener(new C30764());
        OnClickListener cancelClickListener = new C30775();
        this.mGasTypeCancelButton.setOnClickListener(cancelClickListener);
        this.mVehicleTypeCancelButton.setOnClickListener(cancelClickListener);
        this.mVehicleTypeSaveButton.setOnClickListener(new C30786());
        this.mTollRoadSwitch.setOnChecked(new C30797());
        this.mTollRoadButton.setOnClickListener(new C30808());
        this.mGasTypeSaveButton.setOnClickListener(new C30819());
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CarGasSettingsPopUp.this.hide();
            }
        });
        this.mGasOptionViews = new ArrayList();
        this.mTappedCarIndex = -1;
        this.mTappedGasIndex = -1;
        OnClickListener interceptClickListener = new OnClickListener() {
            public void onClick(View v) {
            }
        };
        this.mSettingsContainer.setOnClickListener(interceptClickListener);
        this.mVehicleTypeContainer.setOnClickListener(interceptClickListener);
        this.mGasTypeContainer.setOnClickListener(interceptClickListener);
        this.mPrivateCarOptionView.setKeepSettingsOnlyForNow(false);
        this.mTaxiCarOptionView.setKeepSettingsOnlyForNow(false);
        addView(content);
    }

    private void initStrings() {
        if (!this.mStringsInitialized) {
            String saveText = DisplayStrings.displayString(DisplayStrings.DS_SAVE);
            String cancelText = DisplayStrings.displayString(344);
            String vehicleTypeText = DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_VEHICLE_TYPE_TITLE);
            String gasTypeText = DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_GAS_TYPE_TITLE);
            this.mSettingsTitleLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_SETTINGS_TITLE));
            this.mPrivateCarOptionView.setTitle(DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_PRIVATE_CAR));
            this.mTaxiCarOptionView.setTitle(DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_TAXI));
            this.mTaxiCarOptionView.setDescription(DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_TAXI_DETAIL_TEXT));
            this.mTollRoadLabel.setText(DisplayStrings.displayString(147));
            this.mVehicleTypeTitleLabel.setText(vehicleTypeText);
            this.mVehicleTypeContainerTitle.setText(vehicleTypeText);
            this.mGasTypeTitleLabel.setText(gasTypeText);
            this.mGasTypeContainerTitle.setText(gasTypeText);
            this.mGasTypeSaveLabel.setText(saveText);
            this.mVehicleTypeSaveLabel.setText(saveText);
            this.mGasTypeCancelLabel.setText(cancelText);
            this.mVehicleTypeCancelLabel.setText(cancelText);
            this.mStringsInitialized = true;
        }
    }

    public void show() {
        CarGasTypeManager.getInstance().reloadCarAndGasData(new GasTypeLoadedListener() {

            class C30721 implements Runnable {
                C30721() {
                }

                public void run() {
                    CarGasSettingsPopUp.this.setFields();
                    CarGasSettingsPopUp.this.performEntranceAnimation();
                }
            }

            public void onGasTypeLoaded() {
                CarGasSettingsPopUp.this.post(new C30721());
            }
        });
    }

    public void hide() {
        if (ConfigManager.getInstance().getConfigValueBool(141) != this.mAvoidTollsModeOnStart) {
            DriveToNativeManager.getInstance().reroute(true);
        }
        performExitAnimation();
    }

    public boolean reactToBackButton() {
        if (getVisibility() == 8) {
            return false;
        }
        if (this.mSettingsContainer.getVisibility() == 0) {
            hide();
        } else {
            transitionBackToMainContainer();
        }
        return true;
    }

    private void transitionBackToMainContainer() {
        ViewPropertyAnimatorHelper.initAnimation(this.mCurrentlyDisplayContainer).translationX((float) getMeasuredWidth()).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mCurrentlyDisplayContainer));
        this.mSettingsContainer.setVisibility(0);
        this.mSettingsContainer.setTranslationX((float) (-getMeasuredWidth()));
        ViewPropertyAnimatorHelper.initAnimation(this.mSettingsContainer).translationX(0.0f).setListener(null);
        this.mCurrentlyDisplayContainer = this.mSettingsContainer;
    }

    private void setFields() {
        boolean z = true;
        initStrings();
        int selectedGasIndex = CarGasTypeManager.getInstance().getSelectedGasIndex();
        StringBuilder selectedVehicleText = new StringBuilder(CarGasTypeManager.getInstance().getSelectedVehicleDisplayString());
        if (!CarGasTypeManager.getInstance().isSelectedCarTypePermanent()) {
            selectedVehicleText.append(" ");
            selectedVehicleText.append(DisplayStrings.displayString(DisplayStrings.DS_CAR_TYPE_THIS_DRIVE_ONLY));
        }
        this.mVehicleTypeSelectedLabel.setText(selectedVehicleText);
        this.mVehicleTypeImage.setImageResource(CarGasTypeManager.getInstance().getSelecteVehicleResId());
        this.mGasTypeSelectedLabel.setText(CarGasTypeManager.getInstance().getSelectedGasDisplayString());
        boolean isAvoidTollRoads = ConfigManager.getInstance().getConfigValueBool(141);
        this.mAvoidTollsModeOnStart = isAvoidTollRoads;
        this.mTollRoadSwitch.setValue(isAvoidTollRoads);
        int totalGasTypes = CarGasTypeManager.getInstance().getTotalGasTypes();
        this.mGasTypeOptionsContainer.removeAllViews();
        this.mGasOptionViews.clear();
        for (int i = 0; i < totalGasTypes; i++) {
            boolean z2;
            CarTypeOptionView gasTypeOptionView = new CarTypeOptionView(getContext());
            gasTypeOptionView.removeImage();
            gasTypeOptionView.setLayoutParams(new LayoutParams(-1, PixelMeasure.dimension(C1283R.dimen.carTypeOptionViewHeight)));
            gasTypeOptionView.setTitle(CarGasTypeManager.getInstance().getGasDisplayString(i));
            if (i == selectedGasIndex) {
                z2 = true;
            } else {
                z2 = false;
            }
            gasTypeOptionView.setTypeSelected(z2);
            gasTypeOptionView.setIndex(i);
            gasTypeOptionView.setListener(this.mGasTypeButtonListener);
            this.mGasTypeOptionsContainer.addView(gasTypeOptionView);
            this.mGasOptionViews.add(gasTypeOptionView);
            View separator = new View(getContext());
            separator.setLayoutParams(new LayoutParams(-1, PixelMeasure.dp(1)));
            separator.setBackgroundColor(getResources().getColor(C1283R.color.PassiveGrey));
            this.mGasTypeOptionsContainer.addView(separator);
        }
        this.mTappedCarIndex = CarGasTypeManager.getInstance().getSelectedVehicleIndex();
        this.mTappedGasIndex = CarGasTypeManager.getInstance().getSelectedGasIndex();
        this.mPrivateCarOptionView.setTypeSelected(this.mTappedCarIndex == 0);
        CarTypeOptionView carTypeOptionView = this.mTaxiCarOptionView;
        if (this.mTappedCarIndex != 1) {
            z = false;
        }
        carTypeOptionView.setTypeSelected(z);
        this.mPrivateCarOptionView.collapse(false);
        this.mTaxiCarOptionView.collapse(false);
        this.mPrivateCarOptionView.setListener(this.mCarTypeButtonListener);
        this.mTaxiCarOptionView.setListener(this.mCarTypeButtonListener);
        adjustGasSaveButton();
        adjustCarSaveButton();
    }

    private void saveVehicleType() {
        if (this.mCarSaveEnabled) {
            boolean carTypeChanged = this.mTappedCarIndex != CarGasTypeManager.getInstance().getSelectedVehicleIndex();
            String vehicleType = CarGasTypeManager.getInstance().getVehicleCode(this.mTappedCarIndex);
            ConfigManager.getInstance().setConfigValueString(140, vehicleType);
            boolean shouldBePermanent = this.mTappedCarIndex == 0 ? !this.mPrivateCarOptionView.isKeepSettingsOnlyForNow() : !this.mTaxiCarOptionView.isKeepSettingsOnlyForNow();
            if (shouldBePermanent) {
                ConfigManager.getInstance().setConfigValueString(336, vehicleType);
            }
            boolean isSwitchOn = this.mTappedCarIndex == 0 ? this.mPrivateCarOptionView.isKeepSettingsOnlyForNow() : this.mTaxiCarOptionView.isKeepSettingsOnlyForNow();
            String paramName = String.format(Locale.US, "%s|%s", new Object[]{"TYPE", AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_ACTION_SHEET_VEHICLE_TYPE_SWITCH_MODE});
            Locale locale = Locale.US;
            String str = "%s|%s";
            Object[] objArr = new Object[2];
            objArr[0] = vehicleType;
            objArr[1] = isSwitchOn ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE;
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_ACTION_SHEET_VEHICLE_TYPE_SELECTED, paramName, String.format(locale, str, objArr));
            if (carTypeChanged) {
                DriveToNativeManager.getInstance().reroute(true);
            }
            hide();
        }
    }

    private void saveGasType() {
        if (this.mGasSaveEnabled) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CAR_TYPE_ACTION_SHEET_GAS_TYPE_SELECTED, "TYPE", String.format(Locale.US, "%d", new Object[]{Integer.valueOf(this.mTappedGasIndex)}));
            SettingsNativeManager.getInstance().setPreferredType(this.mTappedGasIndex);
            hide();
        }
    }

    private void onCarTypeSwitchToggled(int index, boolean isOn) {
        if (index == CarGasTypeManager.getInstance().getSelectedVehicleIndex()) {
            this.mTappedCarIndexIsPermanent = !isOn;
        }
        adjustCarSaveButton();
    }

    private void onCarTypeTapped(int index) {
        if (index == 0) {
            this.mTaxiCarOptionView.setTypeSelected(false);
            this.mTaxiCarOptionView.collapse(true);
            adjustCarTypeButton(index, this.mPrivateCarOptionView);
        } else if (index == 1) {
            this.mPrivateCarOptionView.setTypeSelected(false);
            this.mPrivateCarOptionView.collapse(true);
            adjustCarTypeButton(index, this.mTaxiCarOptionView);
        }
    }

    private void adjustCarTypeButton(int index, CarTypeOptionView optionView) {
        if (index != this.mTappedCarIndex) {
            this.mTappedCarIndex = index;
            optionView.setTypeSelected(true);
            adjustCarSaveButton();
        }
        if (!CarGasTypeManager.getInstance().isCarTypePermanent(CarGasTypeManager.getInstance().getVehicleCode(index))) {
            if (optionView.isExpanded()) {
                optionView.collapse(true);
            } else {
                optionView.expand();
            }
        }
    }

    private void onGasTypeTapped(int index) {
        if (index != this.mTappedGasIndex) {
            this.mTappedGasIndex = index;
            int i = 0;
            while (i < this.mGasOptionViews.size()) {
                ((CarTypeOptionView) this.mGasOptionViews.get(i)).setTypeSelected(i == this.mTappedGasIndex);
                i++;
            }
            adjustGasSaveButton();
        }
    }

    private void adjustGasSaveButton() {
        this.mGasSaveEnabled = this.mTappedGasIndex != CarGasTypeManager.getInstance().getSelectedGasIndex();
        this.mGasTypeSaveButton.setAlpha(this.mGasSaveEnabled ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
    }

    private void adjustCarSaveButton() {
        boolean z = this.mTappedCarIndex != CarGasTypeManager.getInstance().getSelectedVehicleIndex() || (this.mTappedCarIndex == CarGasTypeManager.getInstance().getSelectedVehicleIndex() && this.mTappedCarIndexIsPermanent);
        this.mCarSaveEnabled = z;
        this.mVehicleTypeSaveButton.setAlpha(this.mCarSaveEnabled ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
    }

    private void performEntranceAnimation() {
        setVisibility(0);
        this.mSettingsContainer.setVisibility(0);
        this.mVehicleTypeContainer.setVisibility(8);
        this.mGasTypeContainer.setVisibility(8);
        this.mCurrentlyDisplayContainer = this.mSettingsContainer;
        int containerHeight = getExactSettingsContainerHeight();
        this.mSettingsContainer.setTranslationX(0.0f);
        this.mSettingsContainer.setTranslationY((float) containerHeight);
        setAlpha(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this).alpha(1.0f).setListener(null);
        ViewPropertyAnimatorHelper.initAnimation(this.mSettingsContainer).translationY(0.0f).setListener(null);
    }

    private void performExitAnimation() {
        if (this.mCurrentlyDisplayContainer != null) {
            ViewPropertyAnimatorHelper.initAnimation(this.mCurrentlyDisplayContainer).translationY((float) this.mCurrentlyDisplayContainer.getMeasuredHeight());
        }
        ViewPropertyAnimatorHelper.initAnimation(this).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this));
    }

    private void transitionToVehicleType() {
        transitionToContainer(this.mVehicleTypeContainer);
    }

    private void transitionToGasType() {
        transitionToContainer(this.mGasTypeContainer);
    }

    private void transitionToContainer(LinearLayout container) {
        container.setVisibility(0);
        container.setTranslationX((float) getMeasuredWidth());
        container.setTranslationY(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this.mSettingsContainer).translationX((float) (-getMeasuredWidth())).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mSettingsContainer));
        ViewPropertyAnimatorHelper.initAnimation(container).translationX(0.0f).setListener(null);
        this.mCurrentlyDisplayContainer = container;
    }

    private int getExactSettingsContainerHeight() {
        return PixelMeasure.dp(64) + (PixelMeasure.dimension(C1283R.dimen.carTypeOptionViewHeight) * 2);
    }
}
