package com.waze.view.tabs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.R;

public class TabBar extends RelativeLayout {
    public static final int TAB_CONTROLLER_TYPE_2_TABS = 0;
    public static final int TAB_CONTROLLER_TYPE_3_TABS = 1;
    public static final int TAB_ID_0 = 0;
    public static final int TAB_ID_1 = 1;
    public static final int TAB_ID_2 = 2;
    private final OnClickListener mOnClickListener = new C32701();
    IOnTabClickListener mOnTabClickListener = null;
    private View mSelectedTab = null;
    private View mTabCenter = null;
    private View mTabLeft = null;
    private View mTabRight = null;
    private int mType;

    public interface IOnTabClickListener {
        void onClick(int i);
    }

    class C32701 implements OnClickListener {
        C32701() {
        }

        public void onClick(View tab) {
            int tabId = tab.getId();
            TabBar.this.setSelected(tab);
            if (TabBar.this.mOnTabClickListener != null) {
                if (tabId == C1283R.id.tabs_left_container) {
                    TabBar.this.mOnTabClickListener.onClick(0);
                }
                if (tabId == C1283R.id.tabs_center_container) {
                    TabBar.this.mOnTabClickListener.onClick(1);
                }
                if (tabId == C1283R.id.tabs_right_container) {
                    TabBar.this.mOnTabClickListener.onClick(2);
                }
            }
        }
    }

    public TabBar(Context context) {
        super(context);
        init(context, 1);
    }

    public TabBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, context.obtainStyledAttributes(attrs, R.styleable.TabController).getInteger(0, 1));
    }

    public void setListener(IOnTabClickListener listener) {
        this.mOnTabClickListener = listener;
    }

    public void setSelected(int tabId) {
        setSelected(getTab(tabId));
    }

    public void setText(int tabId, String text) {
        ((TextView) getTab(tabId).findViewById(C1283R.id.tabs_tab_text)).setText(text);
    }

    public void setAltRoutesTabs() {
        for (int i = 0; i < 3; i++) {
        }
    }

    public void showNumber(int tabId, boolean value) {
        View tab = getTab(tabId);
        if (value) {
            tab.findViewById(C1283R.id.tabsTabNumberLayout).setVisibility(0);
        } else {
            tab.findViewById(C1283R.id.tabsTabNumberLayout).setVisibility(8);
        }
    }

    public void setNumber(int tabId, int number) {
        ((TextView) getTab(tabId).findViewById(C1283R.id.tabsTabNumberText)).setText("" + number);
    }

    public void setEnabled(int tabId, boolean enabled) {
        int colorDisabled = getResources().getColor(C1283R.color.disabled_white_soft);
        View tab = getTab(tabId);
        tab.setClickable(enabled);
        ((TextView) tab.findViewById(C1283R.id.tabs_tab_text)).setTextColor(colorDisabled);
    }

    private void setSelected(View tab) {
        if (this.mSelectedTab != null) {
            this.mSelectedTab.setBackgroundResource(C1283R.drawable.tabs_idle);
            this.mSelectedTab.setPadding(0, 0, 0, 0);
        }
        tab.setBackgroundResource(C1283R.drawable.tabs_selected);
        tab.setPadding(0, 0, 0, 0);
        this.mSelectedTab = tab;
    }

    private void init(Context context, int type) {
        ((LayoutInflater) context.getSystemService("layout_inflater")).inflate(C1283R.layout.tabs, this);
        setPadding(0, 0, 0, 0);
        this.mType = type;
        this.mTabLeft = findViewById(C1283R.id.tabs_left_container);
        this.mTabCenter = findViewById(C1283R.id.tabs_center_container);
        this.mTabRight = findViewById(C1283R.id.tabs_right_container);
        this.mTabLeft.setOnClickListener(this.mOnClickListener);
        this.mTabCenter.setOnClickListener(this.mOnClickListener);
        this.mTabRight.setOnClickListener(this.mOnClickListener);
        if (this.mType == 0) {
            this.mTabRight.setVisibility(8);
        }
    }

    private View getTab(int tabId) {
        if (tabId == 0) {
            return this.mTabLeft;
        }
        if (tabId == 1) {
            return this.mTabCenter;
        }
        if (tabId == 2) {
            return this.mTabRight;
        }
        return null;
    }
}
