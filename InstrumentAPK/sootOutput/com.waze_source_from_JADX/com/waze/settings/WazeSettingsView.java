package com.waze.settings;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.ConfigItem;
import com.waze.ConfigManager;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.R;
import com.waze.analytics.Analytics;
import com.waze.ifs.ui.BalloonTooltip;
import com.waze.ifs.ui.PointsView;
import com.waze.ifs.ui.SettingsChoiceDialog;
import com.waze.ifs.ui.WazeCheckBoxView;
import com.waze.ifs.ui.WazeRadioView;
import com.waze.ifs.ui.WazeSwitchView;
import com.waze.map.CanvasFont;
import com.waze.utils.PixelMeasure;
import com.waze.view.text.AutoResizeTextView;
import java.util.List;

public class WazeSettingsView extends RelativeLayout {
    private static final int ANIM_DURATION_EDIT_TEXT_BG = 200;
    public static final int BOTTOM = 2;
    public static final int CHECK_BOX = 5;
    public static final int DRILL_DOWN = 0;
    public static final int EDIT_TEXT = 6;
    private static final float KEY_SCALE_FACTOR = 1.2f;
    public static final int MAX_VALUE = 100;
    public static final int MIDDLE = 0;
    public static final int ON_OFF = 2;
    public static final int RADIO = 3;
    public static final int RIGHT_LINES = 7;
    public static final int SEEK_BAR = 4;
    public static final int SELECTION = 1;
    public static final int SINGLE = 3;
    public static final int TOP = 1;
    public static final int UNSET = -1;
    private WazeCheckBoxView _checkbox;
    private float _density;
    private ImageView _icon;
    private boolean _isSwipable = false;
    private TextView _key;
    private int _keyColor;
    private int _position = 0;
    private WazeRadioView _radio;
    private FrameLayout _rightDecor;
    private SeekBar _seekbar;
    private ImageView _selector;
    private View _sep;
    private WazeSwitchView _switch;
    private int _type = -1;
    private TextView _value;
    private int _valueColor;
    private LayoutInflater inflater;
    private int keyAnimationDy;
    private boolean mReverseBooleanSwitch;

    public interface SettingsToggleCallback {
        void onToggle(boolean z);
    }

    public interface GetIndex {
        int fromConfig();
    }

    public static abstract class OnSeekBarChangeListenerBasic implements OnSeekBarChangeListener {
        public abstract void onProgressChanged(SeekBar seekBar, int i);

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            onProgressChanged(seekBar, progress);
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
        }
    }

    class C27941 implements OnClickListener {
        C27941() {
        }

        public void onClick(View v) {
            WazeSettingsView.this._switch.toggle();
        }
    }

    class C27952 implements OnClickListener {
        C27952() {
        }

        public void onClick(View v) {
            WazeSettingsView.this._radio.toggle();
        }
    }

    class C27963 implements OnClickListener {
        C27963() {
        }

        public void onClick(View v) {
            WazeSettingsView.this._checkbox.toggle();
        }
    }

    class C27974 implements OnClickListener {
        C27974() {
        }

        public void onClick(View v) {
            WazeSettingsView.this.setValueText("");
        }
    }

    class C27985 implements OnGlobalLayoutListener {
        C27985() {
        }

        public void onGlobalLayout() {
            WazeSettingsView.this._key.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            int keyHeight = (int) (((float) WazeSettingsView.this._key.getHeight()) * WazeSettingsView.KEY_SCALE_FACTOR);
            WazeSettingsView.this.keyAnimationDy = ((((View) WazeSettingsView.this._key.getParent()).getHeight() - keyHeight) / 2) - WazeSettingsView.this._key.getTop();
        }
    }

    class C27996 implements OnFocusChangeListener {
        C27996() {
        }

        public void onFocusChange(View v, boolean hasFocus) {
            AlphaAnimation alpha;
            View bg = WazeSettingsView.this.findViewById(C1283R.id.settingsViewEditTextFrameBgOn);
            bg.setVisibility(0);
            CharSequence valueText = WazeSettingsView.this._value.getText();
            CharSequence hintText = WazeSettingsView.this._value.getHint();
            if (hasFocus) {
                if ((valueText == null || valueText.length() == 0) && (hintText == null || hintText.length() == 0)) {
                    WazeSettingsView.this.hideKeyAsHint(false);
                }
                alpha = new AlphaAnimation(0.0f, 1.0f);
                WazeSettingsView.this._value.setGravity(16);
            } else {
                if ((valueText == null || valueText.length() == 0) && (hintText == null || hintText.length() == 0)) {
                    WazeSettingsView.this.showKeyAsHint(false);
                }
                alpha = new AlphaAnimation(1.0f, 0.0f);
                WazeSettingsView.this._value.setGravity(19);
            }
            alpha.setDuration(200);
            alpha.setFillAfter(true);
            bg.startAnimation(alpha);
        }
    }

    public WazeSettingsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.SettingsView);
        boolean isSwipable = attrArray.getBoolean(2, false);
        int position = attrArray.getInteger(0, 0);
        Drawable drawable = attrArray.getDrawable(3);
        int type = attrArray.getInteger(1, 0);
        attrArray.recycle();
        init(context, isSwipable, position, drawable, type);
    }

    private void initMembers(Context context) {
        this.inflater = LayoutInflater.from(context);
        this.inflater.inflate(C1283R.layout.waze_settings_view, this);
        if (VERSION.SDK_INT >= 21) {
            api21Init();
        } else {
            setBackgroundColor(getResources().getColor(C1283R.color.White));
        }
        this._sep = findViewById(C1283R.id.settingsViewSeparator);
        this._rightDecor = (FrameLayout) findViewById(C1283R.id.settingsViewRightDecor);
        this._icon = (ImageView) findViewById(C1283R.id.settingsViewIcon);
        this._key = (TextView) findViewById(C1283R.id.settingsViewKey);
        this._value = (TextView) findViewById(C1283R.id.settingsViewValue);
        this._keyColor = getContext().getResources().getColor(C1283R.color.setting_key);
        this._valueColor = getContext().getResources().getColor(C1283R.color.setting_value);
        this._density = getContext().getResources().getDisplayMetrics().density;
    }

    @TargetApi(21)
    private void api21Init() {
        setBackground(new RippleDrawable(ColorStateList.valueOf(getResources().getColor(C1283R.color.blue_bg)), new ColorDrawable(-1), null));
    }

    private void init(Context context, boolean isSwipable, int position, Drawable drawable, int type) {
        initMembers(context);
        setIcon(drawable);
        setPosition(position);
        setSwipable(isSwipable);
        setType(type);
    }

    public void setType(int type) {
        if (this._type != type) {
            this._type = type;
            if (type == 0) {
                setToDrillDown();
            } else if (type == 1) {
                setToSelector();
            } else if (type == 2) {
                setToSwitch();
            } else if (type == 3) {
                setToRadio();
            } else if (type == 4) {
                setToSeekBar();
            } else if (type == 5) {
                setToCheckBox();
            } else if (type == 6) {
                setToEditText();
            } else if (type == 7) {
                setToRightLines();
            }
        }
    }

    public void setHintForEditText(String hint) {
        if (this._type == 6) {
            this._value.setHint(hint);
        }
    }

    public void setCharacterLimitForEditText(int characterLimit) {
        if (this._type == 6) {
            this._value.setFilters(new InputFilter[]{new LengthFilter(characterLimit)});
        }
    }

    public void setPosition(int myPos, int total) {
        int i;
        int i2 = 0;
        if (myPos == 0) {
            i = 1;
        } else {
            i = 0;
        }
        if (myPos == total - 1) {
            i2 = 2;
        }
        setPosition(i | i2);
    }

    public void setPosition(int posType) {
        if (this._position != posType) {
            if (posType == 2 || posType == 3) {
                this._sep.setVisibility(8);
            } else {
                this._sep.setVisibility(0);
            }
            this._position = posType;
        }
    }

    public void setReverseBooleanSwitch(boolean reverseBooleanSwitch) {
        this.mReverseBooleanSwitch = reverseBooleanSwitch;
    }

    public WazeSettingsView(Context context, boolean isSwipable, int position, Drawable drawable, int type) {
        super(context);
        init(context, isSwipable, position, drawable, type);
        setBackgroundColor(-1);
    }

    public WazeSettingsView(Context context) {
        super(context);
        initMembers(context);
        setBackgroundColor(-1);
    }

    private void reset() {
        this._rightDecor.removeAllViews();
        this._selector = null;
        this._switch = null;
        this._radio = null;
        this._seekbar = null;
        this._checkbox = null;
        if (isClickable()) {
            setOnClickListener(null);
            setClickable(false);
        }
    }

    private void setToDrillDown() {
        reset();
    }

    private void setToSelector() {
        reset();
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        ViewGroup frame = (ViewGroup) findViewById(C1283R.id.settingsViewEditTextFrame_ref);
        frame.setVisibility(0);
        layoutInflater.inflate(C1283R.layout.waze_settings_selector, frame);
        this._selector = (ImageView) frame.findViewById(C1283R.id.settingsViewSelectorDecoration);
        this._selector.setImageResource(C1283R.drawable.small_arrow);
        LayoutParams params = (LayoutParams) this._selector.getLayoutParams();
        params.rightMargin = (int) (AutoResizeTextView.MIN_TEXT_SIZE * this._density);
        params.height = -2;
        this._selector.setLayoutParams(params);
    }

    public void setSelectorImage(int resId) {
        if (this._type != 1) {
            throw new IllegalStateException();
        }
        this._selector.setImageResource(resId);
    }

    private void setToSwitch() {
        reset();
        this._switch = new WazeSwitchView(getContext());
        this._rightDecor.addView(this._switch);
        ((FrameLayout.LayoutParams) this._switch.getLayoutParams()).rightMargin = (int) (LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN * this._density);
        setOnClickListener(new C27941());
    }

    private void setToRadio() {
        reset();
        this._radio = new WazeRadioView(getContext());
        this._rightDecor.addView(this._radio);
        ((FrameLayout.LayoutParams) this._radio.getLayoutParams()).rightMargin = (int) (16.0f * this._density);
        setOnClickListener(new C27952());
    }

    private void setToSeekBar() {
        reset();
        this._seekbar = (SeekBar) ((ViewGroup) ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.waze_settings_seekbar, this._rightDecor)).getChildAt(0);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) this._seekbar.getLayoutParams();
        params.rightMargin = (int) (-4.0f * this._density);
        params.width = (int) (160.0f * this._density);
    }

    private void setToCheckBox() {
        reset();
        this._checkbox = new WazeCheckBoxView(getContext());
        this._rightDecor.addView(this._checkbox);
        ((FrameLayout.LayoutParams) this._checkbox.getLayoutParams()).rightMargin = (int) (AutoResizeTextView.MIN_TEXT_SIZE * this._density);
        setOnClickListener(new C27963());
    }

    private void setToEditText() {
        reset();
        findViewById(C1283R.id.settingsViewText).setVisibility(4);
        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService("layout_inflater");
        ViewGroup frame = (ViewGroup) findViewById(C1283R.id.settingsViewEditTextFrame_ref);
        frame.setVisibility(0);
        layoutInflater.inflate(C1283R.layout.waze_settings_edit_text, frame);
        this._key = (TextView) frame.findViewById(C1283R.id.settingsViewEditKey);
        this._value = (TextView) frame.findViewById(C1283R.id.settingsViewEditValue);
        frame.findViewById(C1283R.id.settingsViewEditDelete).setOnClickListener(new C27974());
        this._key.getViewTreeObserver().addOnGlobalLayoutListener(new C27985());
        hideKeyAsHint(true);
        this._value.setOnFocusChangeListener(new C27996());
    }

    public EditText getEdit() {
        return (EditText) this._value;
    }

    public void setImeOptions(int imeActionNext) {
        this._value.setImeOptions(imeActionNext);
    }

    public View getKey() {
        return this._key;
    }

    public View getValue() {
        return this._value;
    }

    public PointsView getValidation() {
        return (PointsView) findViewById(C1283R.id.settingsViewEditValidation);
    }

    private void setToRightLines() {
        reset();
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.waze_settings_right_lines, this._rightDecor);
    }

    private void showKeyAsHint(boolean now) {
        if (this._value.getText().length() <= 0) {
            AnimationSet a = new AnimationSet(true);
            a.addAnimation(new ScaleAnimation(1.0f, KEY_SCALE_FACTOR, 1.0f, KEY_SCALE_FACTOR));
            a.addAnimation(new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) this.keyAnimationDy));
            a.setDuration(now ? 0 : 200);
            a.setFillAfter(true);
            a.setInterpolator(new AccelerateDecelerateInterpolator());
            this._key.startAnimation(a);
        }
    }

    public void hideKeyAsHint(boolean now) {
        AnimationSet a = new AnimationSet(true);
        a.addAnimation(new ScaleAnimation(KEY_SCALE_FACTOR, 1.0f, KEY_SCALE_FACTOR, 1.0f));
        a.addAnimation(new TranslateAnimation(0.0f, 0.0f, (float) this.keyAnimationDy, 0.0f));
        a.setDuration(now ? 0 : 200);
        a.setInterpolator(new AccelerateDecelerateInterpolator());
        this._key.startAnimation(a);
    }

    public WazeSettingsView setKeyText(String keyText) {
        this._key.setText(keyText);
        return this;
    }

    public void setKeyTextColor(int textColor) {
        this._key.setTextColor(textColor);
    }

    public WazeSettingsView setKeyText(Spanned keyText) {
        this._key.setText(keyText);
        return this;
    }

    public void setKeyDrawableRight(int resId) {
        Drawable right = getResources().getDrawable(resId);
        if (VERSION.SDK_INT >= 17) {
            this._key.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, right, null);
        } else {
            this._key.setCompoundDrawables(null, null, right, null);
        }
    }

    public WazeSettingsView setValueText(String valueText) {
        if (valueText != null) {
            CharSequence oldValue = this._value.getText();
            if (oldValue.length() == 0) {
                oldValue = this._value.getHint();
            }
            this._value.setText(valueText);
            this._value.setVisibility(0);
            if (this._type == 6) {
                if (valueText.isEmpty() && !this._value.hasFocus() && TextUtils.isEmpty(this._value.getHint())) {
                    showKeyAsHint(true);
                } else if (oldValue == null || oldValue.length() == 0) {
                    hideKeyAsHint(true);
                }
            }
        } else {
            this._value.setVisibility(8);
        }
        return this;
    }

    public void setEditCapizalized(int style) {
        if (this._type == 6) {
            this._value.setInputType(style | 1);
        }
    }

    public void setSwipable(boolean isSwipable) {
        if (this._isSwipable != isSwipable) {
            RelativeLayout.LayoutParams p = (RelativeLayout.LayoutParams) this._sep.getLayoutParams();
            p.addRule(5, isSwipable ? C1283R.id.settingsViewText : 0);
            this._sep.setLayoutParams(p);
            this._isSwipable = isSwipable;
        }
    }

    public void setIsBottom(boolean isBottom) {
        this._sep.setVisibility(isBottom ? 8 : 0);
    }

    public void setKeyColor(int color) {
        this._keyColor = color;
        this._key.setTextColor(color);
    }

    public void setValueColor(int color) {
        this._valueColor = color;
        this._value.setTextColor(color);
    }

    public WazeSettingsView setIcon(int iconId) {
        if (iconId != 0) {
            this._icon.setImageResource(iconId);
        } else {
            this._icon.setVisibility(8);
        }
        return this;
    }

    public void setIcon(Drawable icon) {
        if (icon != null) {
            this._icon.setImageDrawable(icon);
        } else {
            this._icon.setVisibility(8);
        }
    }

    public void setIcon(Drawable icon, boolean superSize) {
        if (icon != null) {
            this._icon.setImageDrawable(icon);
            if (superSize) {
                double viewWidthToBitmapWidthRatio = ((double) (this._density * 40.0f)) / ((double) icon.getIntrinsicWidth());
                this._icon.getLayoutParams().height = (int) (((double) icon.getIntrinsicHeight()) * viewWidthToBitmapWidthRatio);
                return;
            }
            return;
        }
        this._icon.setVisibility(8);
    }

    public void setSmallIcon() {
        this._icon.setScaleType(ScaleType.FIT_CENTER);
        this._icon.getLayoutParams().width = PixelMeasure.dp(28);
        this._icon.getLayoutParams().height = PixelMeasure.dp(28);
    }

    public WazeSettingsView setIconHeight(int height_dp) {
        this._icon.setScaleType(ScaleType.FIT_CENTER);
        this._icon.getLayoutParams().height = PixelMeasure.dp(height_dp);
        return this;
    }

    public void setEnabled(boolean isEnabled) {
        super.setEnabled(isEnabled);
        setVisiblyEnabled(isEnabled);
    }

    public void setVisiblyEnabled(boolean isEnabled) {
        int color;
        if (isEnabled) {
            color = this._keyColor;
        } else {
            color = getContext().getResources().getColor(C1283R.color.setting_key_disabled);
        }
        if (this._type == 6) {
            this._value.setEnabled(isEnabled);
            setAlpha(isEnabled ? 1.0f : CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            return;
        }
        this._key.setTextColor(color);
        if (this._type == 1) {
            int i;
            TextView textView = this._value;
            if (isEnabled) {
                i = this._valueColor;
            } else {
                i = getContext().getResources().getColor(C1283R.color.setting_value_disabled);
            }
            textView.setTextColor(i);
        }
    }

    public void initSelection(Context context, GetIndex getIndex, int displayStr, String[] options, String[] values, SettingsDialogListener l) {
        setType(1);
        final NativeManager nm = NativeManager.getInstance();
        final String langDisplayStr = nm.getLanguageString(displayStr);
        setKeyText(langDisplayStr);
        setValueText(nm.getLanguageString(options[getIndex.fromConfig()]));
        final String[] strArr = options;
        final GetIndex getIndex2 = getIndex;
        final Context context2 = context;
        final SettingsDialogListener settingsDialogListener = l;
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String[] displayOptions = new String[strArr.length];
                int i = 0;
                for (String option : strArr) {
                    displayOptions[i] = nm.getLanguageString(option);
                    i++;
                }
                WazeSettingsView.this.showSubmenu(context2, langDisplayStr, displayOptions, getIndex2.fromConfig(), settingsDialogListener);
            }
        });
    }

    public void initSelectionNoTranslation(Context context, GetIndex getIndex, int displayStr, String[] options, String[] values, SettingsDialogListener l) {
        setType(1);
        final NativeManager nm = NativeManager.getInstance();
        final String langDisplayStr = nm.getLanguageString(displayStr);
        setKeyText(langDisplayStr);
        setValueText(options[getIndex.fromConfig()]);
        final String[] strArr = options;
        final GetIndex getIndex2 = getIndex;
        final Context context2 = context;
        final SettingsDialogListener settingsDialogListener = l;
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String[] displayOptions = new String[strArr.length];
                int i = 0;
                for (String option : strArr) {
                    displayOptions[i] = nm.getLanguageString(option);
                    i++;
                }
                WazeSettingsView.this.showSubmenu(context2, langDisplayStr, displayOptions, getIndex2.fromConfig(), settingsDialogListener);
            }
        });
    }

    public void initSelection(Context context, String screenName, final List<ConfigItem> configItems, int displayStr, String[] options, final String[] values, final int configIndex) {
        final List<ConfigItem> list = configItems;
        final int i = configIndex;
        final String[] strArr = options;
        final String[] strArr2 = values;
        final String str = screenName;
        initSelection(context, new GetIndex() {
            public int fromConfig() {
                return SettingsUtils.findValueIndex(values, ((ConfigItem) configItems.get(configIndex)).getStringValue());
            }
        }, displayStr, options, values, new SettingsDialogListener() {
            public void onComplete(int position) {
                ConfigItem configItem = (ConfigItem) list.get(i);
                WazeSettingsView.this.setValueText(NativeManager.getInstance().getLanguageString(strArr[position]));
                configItem.setStringValue(strArr2[position]);
                ConfigManager.getInstance().setConfig(configItem, str);
            }
        });
    }

    public void showSubmenu(Context context, String dialogTitle, String[] options, int selected, SettingsDialogListener listener) {
        new SettingsChoiceDialog(context, dialogTitle, options, selected, listener).show();
    }

    public void initDrillDown(final Activity activity, int displayStr, final Class<?> activityClass, final int activityCode) {
        setType(0);
        this._value.setVisibility(8);
        setKeyText(NativeManager.getInstance().getLanguageString(displayStr));
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                activity.startActivityForResult(new Intent(activity, activityClass), activityCode);
            }
        });
    }

    public void initDrillDownAnalytics(Activity activity, int displayStr, Class<?> activityClass, int activityCode, String analyticsEvent, String analyticsEventInfo, String analyticsEventValue) {
        setType(0);
        this._value.setVisibility(8);
        setKeyText(NativeManager.getInstance().getLanguageString(displayStr));
        final String str = analyticsEvent;
        final String str2 = analyticsEventInfo;
        final String str3 = analyticsEventValue;
        final Activity activity2 = activity;
        final Class<?> cls = activityClass;
        final int i = activityCode;
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Analytics.log(str, str2, str3);
                activity2.startActivityForResult(new Intent(activity2, cls), i);
            }
        });
    }

    public void initDrillDownAnalytics(int displayStr, Runnable toDo, String analyticsEvent, String analyticsEventInfo, String analyticsEventValue) {
        setType(0);
        this._value.setVisibility(8);
        setKeyText(NativeManager.getInstance().getLanguageString(displayStr));
        final String str = analyticsEvent;
        final String str2 = analyticsEventInfo;
        final String str3 = analyticsEventValue;
        final Runnable runnable = toDo;
        setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Analytics.log(str, str2, str3);
                runnable.run();
            }
        });
    }

    public void setValue(boolean on) {
        switch (this._type) {
            case 2:
                this._switch.setValue(on);
                return;
            case 3:
                this._radio.setValue(on);
                return;
            case 5:
                this._checkbox.setValue(on);
                return;
            default:
                throw new IllegalStateException();
        }
    }

    public boolean isOn() {
        switch (this._type) {
            case 2:
                return this._switch.isChecked();
            case 3:
                return this._radio.isSelected();
            case 5:
                return this._checkbox.isChecked();
            default:
                throw new IllegalStateException();
        }
    }

    public void setOnChecked(SettingsToggleCallback toggleCallback) {
        switch (this._type) {
            case 2:
                this._switch.setOnChecked(toggleCallback);
                return;
            case 3:
                this._radio.setOnChecked(toggleCallback);
                return;
            case 5:
                this._checkbox.setOnChecked(toggleCallback);
                return;
            default:
                throw new IllegalStateException();
        }
    }

    public void setText(String languageString) {
        setKeyText(languageString);
        setValueText(null);
    }

    public void setText(int ds) {
        setText(NativeManager.getInstance().getLanguageString(ds));
    }

    public void initToggleCallback(String screenName, List<ConfigItem> configItems, int configIndex, OnCheckedChangeListener customCallback) {
        final List<ConfigItem> list = configItems;
        final int i = configIndex;
        final String str = screenName;
        final OnCheckedChangeListener onCheckedChangeListener = customCallback;
        SettingsToggleCallback listener = new SettingsToggleCallback() {
            public void onToggle(boolean bIsChecked) {
                ConfigItem configItem = (ConfigItem) list.get(i);
                configItem.setBooleanValue(WazeSettingsView.this.mReverseBooleanSwitch != bIsChecked);
                ConfigManager.getInstance().setConfig(configItem, str);
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(null, bIsChecked);
                }
            }
        };
        if (this._type == 2) {
            this._switch.setOnChecked(listener);
        } else if (this._type == 5) {
            this._checkbox.setOnChecked(listener);
        }
    }

    public void initToggleCallbackBoolean(final int configIndex, final OnCheckedChangeListener customCallback) {
        SettingsToggleCallback listener = new SettingsToggleCallback() {
            public void onToggle(boolean bIsChecked) {
                ConfigManager.getInstance().setConfigValueBool(configIndex, bIsChecked);
                if (customCallback != null) {
                    customCallback.onCheckedChanged(null, bIsChecked);
                }
            }
        };
        if (this._type == 2) {
            this._switch.setOnChecked(listener);
        } else if (this._type == 5) {
            this._checkbox.setOnChecked(listener);
        }
    }

    public void initToggleCallbackNoConfig(final OnCheckedChangeListener customCallback) {
        SettingsToggleCallback listener = new SettingsToggleCallback() {
            public void onToggle(boolean bIsChecked) {
                if (customCallback != null) {
                    customCallback.onCheckedChanged(null, bIsChecked);
                }
            }
        };
        if (this._type == 2) {
            this._switch.setOnChecked(listener);
        } else if (this._type == 5) {
            this._checkbox.setOnChecked(listener);
        }
    }

    public void setRadioValue(boolean on, boolean animate) {
        if (this._type == 3) {
            if (animate) {
                this._radio.setValueAnimated(on);
            } else {
                this._radio.setValue(on);
            }
        }
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        this._seekbar.setOnSeekBarChangeListener(listener);
    }

    public void setOnSeekBarChangeListener(OnSeekBarChangeListenerBasic listener) {
        this._seekbar.setOnSeekBarChangeListener(listener);
    }

    public void setProgress(Integer valueOf) {
        this._seekbar.setProgress(valueOf.intValue());
    }

    public void initSeekBarBaloonTip() {
        this._seekbar.setOnTouchListener(new OnTouchListener() {
            BalloonTooltip bt = null;
            MotionEvent ignoreEvent = null;

            public boolean onTouch(View v, MotionEvent event) {
                boolean z = true;
                if (event != this.ignoreEvent) {
                    int action = event.getAction();
                    if (action == 0) {
                        this.ignoreEvent = event;
                        v.dispatchTouchEvent(event);
                        event.setAction(1);
                        v.dispatchTouchEvent(event);
                        event.setAction(0);
                        this.ignoreEvent = null;
                        this.bt = new BalloonTooltip(v.getContext());
                        this.bt.setProgress(WazeSettingsView.this._seekbar.getProgress());
                        this.bt.show(v);
                    } else if (action == 1 || action == 3) {
                        if (this.bt != null && this.bt.isShowing()) {
                            BalloonTooltip balloonTooltip = this.bt;
                            if (action != 1) {
                                z = false;
                            }
                            balloonTooltip.dismiss(z);
                            this.bt = null;
                        }
                    } else if (action == 2) {
                        this.bt.setProgress(WazeSettingsView.this._seekbar.getProgress());
                    }
                }
                return false;
            }
        });
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this._value.addTextChangedListener(textWatcher);
    }

    public CharSequence getValueText() {
        return this._value.getText();
    }

    public void setValueHint(String hint) {
    }

    public void setOnEditorActionListener(OnEditorActionListener onEditorActionListener) {
        this._value.setOnEditorActionListener(onEditorActionListener);
    }

    public void setValueType(int type) {
        this._value.setInputType(type);
    }

    public WazeSettingsView setClickOnEdit(boolean clickOnEdit) {
        if (this._type == 6) {
            if (clickOnEdit) {
                this._value.setOnTouchListener(new OnTouchListener() {
                    public boolean onTouch(View v, MotionEvent event) {
                        if (event.getAction() == 1) {
                            WazeSettingsView.this.performClick();
                        }
                        return true;
                    }
                });
                findViewById(C1283R.id.settingsViewEditDelete).setVisibility(8);
            } else {
                this._value.setOnTouchListener(null);
                findViewById(C1283R.id.settingsViewEditDelete).setVisibility(0);
            }
        }
        return this;
    }

    public void setValueSelection(int length) {
        ((EditText) this._value).setSelection(length);
    }

    public void setUnfocusable() {
        switch (this._type) {
            case 2:
                this._switch.setFocusable(false);
                this._switch.setClickable(false);
                break;
            case 3:
                this._radio.setFocusable(false);
                this._radio.setClickable(false);
                break;
            case 5:
                this._checkbox.setFocusable(false);
                this._checkbox.setClickable(false);
                break;
            case 6:
                this._value.setFocusable(false);
                this._value.setClickable(true);
                break;
        }
        setFocusable(false);
        setClickable(false);
    }

    public void setLine1(String line) {
        TextView tvText = (TextView) this._rightDecor.findViewById(C1283R.id.waze_settings_line1);
        if (tvText != null) {
            if (line == null) {
                tvText.setVisibility(8);
                return;
            }
            tvText.setVisibility(0);
            tvText.setText(line);
        }
    }

    public void setLine2(String line) {
        TextView tvText = (TextView) this._rightDecor.findViewById(C1283R.id.waze_settings_line2);
        if (tvText != null) {
            if (line == null) {
                tvText.setVisibility(8);
                return;
            }
            tvText.setVisibility(0);
            tvText.setText(line);
        }
    }

    public void setIconVisibility(int vis) {
        this._rightDecor.findViewById(C1283R.id.waze_settings_right_icon).setVisibility(vis);
    }
}
