package android.support.v7.view.menu;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.v7.view.menu.MenuView.ItemView;
import android.support.v7.widget.ActionMenuView.ActionMenuChildView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.ListPopupWindow.ForwardingListener;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Toast;
import com.waze.map.CanvasFont;

public class ActionMenuItemView extends AppCompatTextView implements ItemView, ActionMenuChildView, OnClickListener, OnLongClickListener {
    private static final int MAX_ICON_SIZE = 32;
    private static final String TAG = "ActionMenuItemView";
    private boolean mAllowTextWithIcon;
    private boolean mExpandedFormat;
    private ForwardingListener mForwardingListener;
    private Drawable mIcon;
    private MenuItemImpl mItemData;
    private ItemInvoker mItemInvoker;
    private int mMaxIconSize;
    private int mMinWidth;
    private PopupCallback mPopupCallback;
    private int mSavedPaddingLeft;
    private CharSequence mTitle;

    private class ActionMenuItemForwardingListener extends ForwardingListener {
        public ActionMenuItemForwardingListener() throws  {
            super(ActionMenuItemView.this);
        }

        public ListPopupWindow getPopup() throws  {
            if (ActionMenuItemView.this.mPopupCallback != null) {
                return ActionMenuItemView.this.mPopupCallback.getPopup();
            }
            return null;
        }

        protected boolean onForwardingStarted() throws  {
            if (ActionMenuItemView.this.mItemInvoker == null) {
                return false;
            }
            if (!ActionMenuItemView.this.mItemInvoker.invokeItem(ActionMenuItemView.this.mItemData)) {
                return false;
            }
            ListPopupWindow $r4 = getPopup();
            if ($r4 != null) {
                return $r4.isShowing();
            } else {
                return false;
            }
        }
    }

    public static abstract class PopupCallback {
        public abstract ListPopupWindow getPopup() throws ;
    }

    public boolean prefersCondensedTitle() throws  {
        return true;
    }

    public boolean showsIcon() throws  {
        return true;
    }

    public ActionMenuItemView(Context $r1) throws  {
        this($r1, null);
    }

    public ActionMenuItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public ActionMenuItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        Resources $r3 = $r1.getResources();
        this.mAllowTextWithIcon = $r3.getBoolean(C0192R.bool.abc_config_allowActionMenuItemTextWithIcon);
        TypedArray $r5 = $r1.obtainStyledAttributes($r2, C0192R.styleable.ActionMenuItemView, $i0, 0);
        this.mMinWidth = $r5.getDimensionPixelSize(C0192R.styleable.ActionMenuItemView_android_minWidth, 0);
        $r5.recycle();
        this.mMaxIconSize = (int) ((32.0f * $r3.getDisplayMetrics().density) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        setOnClickListener(this);
        setOnLongClickListener(this);
        this.mSavedPaddingLeft = -1;
    }

    public void onConfigurationChanged(Configuration $r1) throws  {
        if (VERSION.SDK_INT >= 8) {
            super.onConfigurationChanged($r1);
        }
        this.mAllowTextWithIcon = getContext().getResources().getBoolean(C0192R.bool.abc_config_allowActionMenuItemTextWithIcon);
        updateTextButtonVisibility();
    }

    public void setPadding(int $i0, int $i1, int $i2, int $i3) throws  {
        this.mSavedPaddingLeft = $i0;
        super.setPadding($i0, $i1, $i2, $i3);
    }

    public MenuItemImpl getItemData() throws  {
        return this.mItemData;
    }

    public void initialize(MenuItemImpl $r1, int menuType) throws  {
        this.mItemData = $r1;
        setIcon($r1.getIcon());
        setTitle($r1.getTitleForItemView(this));
        setId($r1.getItemId());
        setVisibility($r1.isVisible() ? (byte) 0 : (byte) 8);
        setEnabled($r1.isEnabled());
        if ($r1.hasSubMenu() && this.mForwardingListener == null) {
            this.mForwardingListener = new ActionMenuItemForwardingListener();
        }
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        return (this.mItemData.hasSubMenu() && this.mForwardingListener != null && this.mForwardingListener.onTouch(this, $r1)) ? true : super.onTouchEvent($r1);
    }

    public void onClick(View v) throws  {
        if (this.mItemInvoker != null) {
            this.mItemInvoker.invokeItem(this.mItemData);
        }
    }

    public void setItemInvoker(ItemInvoker $r1) throws  {
        this.mItemInvoker = $r1;
    }

    public void setPopupCallback(PopupCallback $r1) throws  {
        this.mPopupCallback = $r1;
    }

    public void setCheckable(boolean checkable) throws  {
    }

    public void setChecked(boolean checked) throws  {
    }

    public void setExpandedFormat(boolean $z0) throws  {
        if (this.mExpandedFormat != $z0) {
            this.mExpandedFormat = $z0;
            if (this.mItemData != null) {
                this.mItemData.actionFormatChanged();
            }
        }
    }

    private void updateTextButtonVisibility() throws  {
        CharSequence $r1;
        boolean $z0 = false;
        boolean $z1 = !TextUtils.isEmpty(this.mTitle);
        if (this.mIcon == null || (this.mItemData.showsTextAsAction() && (this.mAllowTextWithIcon || this.mExpandedFormat))) {
            $z0 = true;
        }
        if ($z1 & $z0) {
            $r1 = this.mTitle;
        } else {
            $r1 = null;
        }
        setText($r1);
    }

    public void setIcon(Drawable $r1) throws  {
        this.mIcon = $r1;
        if ($r1 != null) {
            float $f0;
            int $i0 = $r1.getIntrinsicWidth();
            int $i1 = $i0;
            int $i2 = $r1.getIntrinsicHeight();
            int $i3 = $i2;
            if ($i0 > this.mMaxIconSize) {
                $f0 = ((float) this.mMaxIconSize) / ((float) $i0);
                $i1 = this.mMaxIconSize;
                $i3 = (int) (((float) $i2) * $f0);
            }
            if ($i3 > this.mMaxIconSize) {
                $f0 = ((float) this.mMaxIconSize) / ((float) $i3);
                $i3 = this.mMaxIconSize;
                $i1 = (int) (((float) $i1) * $f0);
            }
            $r1.setBounds(0, 0, $i1, $i3);
        }
        setCompoundDrawables($r1, null, null, null);
        updateTextButtonVisibility();
    }

    public boolean hasText() throws  {
        return !TextUtils.isEmpty(getText());
    }

    public void setShortcut(boolean showShortcut, char shortcutKey) throws  {
    }

    public void setTitle(CharSequence $r1) throws  {
        this.mTitle = $r1;
        setContentDescription(this.mTitle);
        updateTextButtonVisibility();
    }

    public boolean needsDividerBefore() throws  {
        return hasText() && this.mItemData.getIcon() == null;
    }

    public boolean needsDividerAfter() throws  {
        return hasText();
    }

    public boolean onLongClick(View $r1) throws  {
        if (hasText()) {
            return false;
        }
        int[] $r3 = new int[2];
        Rect $r2 = new Rect();
        getLocationOnScreen($r3);
        getWindowVisibleDisplayFrame($r2);
        Context $r4 = getContext();
        int $i2 = getWidth();
        int $i3 = getHeight();
        int $i0 = $r3[1] + ($i3 / 2);
        $i2 = $r3[0] + ($i2 / 2);
        if (ViewCompat.getLayoutDirection($r1) == 0) {
            $i2 = $r4.getResources().getDisplayMetrics().widthPixels - $i2;
        }
        Toast $r9 = Toast.makeText($r4, this.mItemData.getTitle(), 0);
        if ($i0 < $r2.height()) {
            $r9.setGravity(8388661, $i2, ($r3[1] + $i3) - $r2.top);
        } else {
            $r9.setGravity(81, 0, $i3);
        }
        $r9.show();
        return true;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        boolean $z0 = hasText();
        if ($z0 && this.mSavedPaddingLeft >= 0) {
            super.setPadding(this.mSavedPaddingLeft, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
        super.onMeasure($i0, $i1);
        int $i2 = MeasureSpec.getMode($i0);
        int $i3 = MeasureSpec.getSize($i0);
        $i0 = getMeasuredWidth();
        $i3 = $i2 == Integer.MIN_VALUE ? Math.min($i3, this.mMinWidth) : this.mMinWidth;
        if ($i2 != 1073741824 && this.mMinWidth > 0 && $i0 < $i3) {
            super.onMeasure(MeasureSpec.makeMeasureSpec($i3, 1073741824), $i1);
        }
        if (!$z0 && this.mIcon != null) {
            super.setPadding((getMeasuredWidth() - this.mIcon.getBounds().width()) / 2, getPaddingTop(), getPaddingRight(), getPaddingBottom());
        }
    }
}
