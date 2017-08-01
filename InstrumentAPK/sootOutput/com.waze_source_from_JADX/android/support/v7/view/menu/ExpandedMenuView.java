package android.support.v7.view.menu;

import android.content.Context;
import android.support.v7.view.menu.MenuBuilder.ItemInvoker;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public final class ExpandedMenuView extends ListView implements ItemInvoker, MenuView, OnItemClickListener {
    private static final int[] TINT_ATTRS = new int[]{16842964, 16843049};
    private int mAnimations;
    private MenuBuilder mMenu;

    public ExpandedMenuView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 16842868);
    }

    public ExpandedMenuView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2);
        setOnItemClickListener(this);
        TintTypedArray $r4 = TintTypedArray.obtainStyledAttributes($r1, $r2, TINT_ATTRS, $i0, 0);
        if ($r4.hasValue(0)) {
            setBackgroundDrawable($r4.getDrawable(0));
        }
        if ($r4.hasValue(1)) {
            setDivider($r4.getDrawable(1));
        }
        $r4.recycle();
    }

    public void initialize(MenuBuilder $r1) throws  {
        this.mMenu = $r1;
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        setChildrenDrawingCacheEnabled(false);
    }

    public boolean invokeItem(MenuItemImpl $r1) throws  {
        return this.mMenu.performItemAction($r1, 0);
    }

    public void onItemClick(AdapterView parent, View v, int $i0, long id) throws  {
        invokeItem((MenuItemImpl) getAdapter().getItem($i0));
    }

    public int getWindowAnimations() throws  {
        return this.mAnimations;
    }
}
