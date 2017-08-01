package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.MenuView.ItemView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

public class ListMenuItemView extends LinearLayout implements ItemView {
    private static final String TAG = "ListMenuItemView";
    private Drawable mBackground;
    private CheckBox mCheckBox;
    private Context mContext;
    private boolean mForceShowIcon;
    private ImageView mIconView;
    private LayoutInflater mInflater;
    private MenuItemImpl mItemData;
    private int mMenuType;
    private boolean mPreserveIconSpacing;
    private RadioButton mRadioButton;
    private TextView mShortcutView;
    private int mTextAppearance;
    private Context mTextAppearanceContext;
    private TextView mTitleView;

    public boolean prefersCondensedTitle() throws  {
        return false;
    }

    public ListMenuItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2);
        this.mContext = $r1;
        TypedArray $r4 = $r1.obtainStyledAttributes($r2, C0192R.styleable.MenuView, $i0, 0);
        this.mBackground = $r4.getDrawable(C0192R.styleable.MenuView_android_itemBackground);
        this.mTextAppearance = $r4.getResourceId(C0192R.styleable.MenuView_android_itemTextAppearance, -1);
        this.mPreserveIconSpacing = $r4.getBoolean(C0192R.styleable.MenuView_preserveIconSpacing, false);
        this.mTextAppearanceContext = $r1;
        $r4.recycle();
    }

    public ListMenuItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    protected void onFinishInflate() throws  {
        super.onFinishInflate();
        setBackgroundDrawable(this.mBackground);
        this.mTitleView = (TextView) findViewById(C0192R.id.title);
        if (this.mTextAppearance != -1) {
            this.mTitleView.setTextAppearance(this.mTextAppearanceContext, this.mTextAppearance);
        }
        this.mShortcutView = (TextView) findViewById(C0192R.id.shortcut);
    }

    public void initialize(MenuItemImpl $r1, int $i0) throws  {
        this.mItemData = $r1;
        this.mMenuType = $i0;
        setVisibility($r1.isVisible() ? (byte) 0 : (byte) 8);
        setTitle($r1.getTitleForItemView(this));
        setCheckable($r1.isCheckable());
        setShortcut($r1.shouldShowShortcut(), $r1.getShortcut());
        setIcon($r1.getIcon());
        setEnabled($r1.isEnabled());
    }

    public void setForceShowIcon(boolean $z0) throws  {
        this.mForceShowIcon = $z0;
        this.mPreserveIconSpacing = $z0;
    }

    public void setTitle(CharSequence $r1) throws  {
        if ($r1 != null) {
            this.mTitleView.setText($r1);
            if (this.mTitleView.getVisibility() != 0) {
                this.mTitleView.setVisibility(0);
            }
        } else if (this.mTitleView.getVisibility() != 8) {
            this.mTitleView.setVisibility(8);
        }
    }

    public MenuItemImpl getItemData() throws  {
        return this.mItemData;
    }

    public void setCheckable(boolean $z0) throws  {
        if ($z0 || this.mRadioButton != null || this.mCheckBox != null) {
            CompoundButton $r4;
            CompoundButton $r5;
            if (this.mItemData.isExclusiveCheckable()) {
                if (this.mRadioButton == null) {
                    insertRadioButton();
                }
                $r4 = this.mRadioButton;
                $r5 = this.mCheckBox;
            } else {
                if (this.mCheckBox == null) {
                    insertCheckBox();
                }
                $r4 = this.mCheckBox;
                $r5 = this.mRadioButton;
            }
            if ($z0) {
                byte $b0;
                $r4.setChecked(this.mItemData.isChecked());
                if ($z0) {
                    $b0 = (byte) 0;
                } else {
                    $b0 = (byte) 8;
                }
                if ($r4.getVisibility() != $b0) {
                    $r4.setVisibility($b0);
                }
                if ($r5 != null && $r5.getVisibility() != 8) {
                    $r5.setVisibility(8);
                    return;
                }
                return;
            }
            if (this.mCheckBox != null) {
                this.mCheckBox.setVisibility(8);
            }
            if (this.mRadioButton != null) {
                this.mRadioButton.setVisibility(8);
            }
        }
    }

    public void setChecked(boolean $z0) throws  {
        CompoundButton $r3;
        if (this.mItemData.isExclusiveCheckable()) {
            if (this.mRadioButton == null) {
                insertRadioButton();
            }
            $r3 = this.mRadioButton;
        } else {
            if (this.mCheckBox == null) {
                insertCheckBox();
            }
            $r3 = this.mCheckBox;
        }
        $r3.setChecked($z0);
    }

    public void setShortcut(boolean $z0, char shortcutKey) throws  {
        byte $b1 = ($z0 && this.mItemData.shouldShowShortcut()) ? (byte) 0 : (byte) 8;
        if ($b1 == (byte) 0) {
            this.mShortcutView.setText(this.mItemData.getShortcutLabel());
        }
        if (this.mShortcutView.getVisibility() != $b1) {
            this.mShortcutView.setVisibility($b1);
        }
    }

    public void setIcon(Drawable $r1) throws  {
        boolean $z0 = this.mItemData.shouldShowIcon() || this.mForceShowIcon;
        if (!$z0 && !this.mPreserveIconSpacing) {
            return;
        }
        if (this.mIconView != null || $r1 != null || this.mPreserveIconSpacing) {
            if (this.mIconView == null) {
                insertIconView();
            }
            if ($r1 != null || this.mPreserveIconSpacing) {
                ImageView $r3 = this.mIconView;
                if (!$z0) {
                    $r1 = null;
                }
                $r3.setImageDrawable($r1);
                if (this.mIconView.getVisibility() != 0) {
                    this.mIconView.setVisibility(0);
                    return;
                }
                return;
            }
            this.mIconView.setVisibility(8);
        }
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        if (this.mIconView != null && this.mPreserveIconSpacing) {
            LayoutParams $r2 = getLayoutParams();
            LinearLayout.LayoutParams $r4 = (LinearLayout.LayoutParams) this.mIconView.getLayoutParams();
            if ($r2.height > 0 && $r4.width <= 0) {
                $r4.width = $r2.height;
            }
        }
        super.onMeasure($i0, $i1);
    }

    private void insertIconView() throws  {
        this.mIconView = (ImageView) getInflater().inflate(C0192R.layout.abc_list_menu_item_icon, this, false);
        addView(this.mIconView, 0);
    }

    private void insertRadioButton() throws  {
        this.mRadioButton = (RadioButton) getInflater().inflate(C0192R.layout.abc_list_menu_item_radio, this, false);
        addView(this.mRadioButton);
    }

    private void insertCheckBox() throws  {
        this.mCheckBox = (CheckBox) getInflater().inflate(C0192R.layout.abc_list_menu_item_checkbox, this, false);
        addView(this.mCheckBox);
    }

    public boolean showsIcon() throws  {
        return this.mForceShowIcon;
    }

    private LayoutInflater getInflater() throws  {
        if (this.mInflater == null) {
            this.mInflater = LayoutInflater.from(this.mContext);
        }
        return this.mInflater;
    }
}
