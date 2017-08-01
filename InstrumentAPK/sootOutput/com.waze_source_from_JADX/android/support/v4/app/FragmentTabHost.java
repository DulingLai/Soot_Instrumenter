package android.support.v4.app;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.BaseSavedState;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabHost.TabSpec;
import android.widget.TabWidget;
import dalvik.annotation.Signature;
import java.util.ArrayList;

public class FragmentTabHost extends TabHost implements OnTabChangeListener {
    private boolean mAttached;
    private int mContainerId;
    private Context mContext;
    private FragmentManager mFragmentManager;
    private TabInfo mLastTab;
    private OnTabChangeListener mOnTabChangeListener;
    private FrameLayout mRealTabContent;
    private final ArrayList<TabInfo> mTabs = new ArrayList();

    static class DummyTabFactory implements TabContentFactory {
        private final Context mContext;

        public DummyTabFactory(Context $r1) throws  {
            this.mContext = $r1;
        }

        public View createTabContent(String tag) throws  {
            View $r2 = new View(this.mContext);
            $r2.setMinimumWidth(0);
            $r2.setMinimumHeight(0);
            return $r2;
        }
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C00291();
        String curTab;

        static class C00291 implements Creator<SavedState> {
            C00291() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        private SavedState(Parcel $r1) throws  {
            super($r1);
            this.curTab = $r1.readString();
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeString(this.curTab);
        }

        public String toString() throws  {
            return "FragmentTabHost.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " curTab=" + this.curTab + "}";
        }
    }

    static final class TabInfo {
        private final Bundle args;
        private final Class<?> clss;
        private Fragment fragment;
        private final String tag;

        TabInfo(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/Class", "<*>;", "Landroid/os/Bundle;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/Class", "<*>;", "Landroid/os/Bundle;", ")V"}) Class<?> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/Class", "<*>;", "Landroid/os/Bundle;", ")V"}) Bundle $r3) throws  {
            this.tag = $r1;
            this.clss = $r2;
            this.args = $r3;
        }
    }

    public FragmentTabHost(Context $r1) throws  {
        super($r1, null);
        initFragmentTabHost($r1, null);
    }

    public FragmentTabHost(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        initFragmentTabHost($r1, $r2);
    }

    private void initFragmentTabHost(Context $r1, AttributeSet $r2) throws  {
        TypedArray $r4 = $r1.obtainStyledAttributes($r2, new int[]{16842995}, 0, 0);
        this.mContainerId = $r4.getResourceId(0, 0);
        $r4.recycle();
        super.setOnTabChangedListener(this);
    }

    private void ensureHierarchy(Context $r1) throws  {
        if (findViewById(16908307) == null) {
            LinearLayout $r2 = new LinearLayout($r1);
            $r2.setOrientation(1);
            addView($r2, new LayoutParams(-1, -1));
            TabWidget $r3 = new TabWidget($r1);
            $r3.setId(16908307);
            $r3.setOrientation(0);
            $r2.addView($r3, new LinearLayout.LayoutParams(-1, -2, 0.0f));
            FrameLayout $r7 = new FrameLayout($r1);
            $r7.setId(16908305);
            $r2.addView($r7, new LinearLayout.LayoutParams(0, 0, 0.0f));
            $r7 = new FrameLayout($r1);
            this.mRealTabContent = $r7;
            this.mRealTabContent.setId(this.mContainerId);
            $r2.addView($r7, new LinearLayout.LayoutParams(-1, 0, 1.0f));
        }
    }

    @Deprecated
    public void setup() throws  {
        throw new IllegalStateException("Must call setup() that takes a Context and FragmentManager");
    }

    public void setup(Context $r1, FragmentManager $r2) throws  {
        ensureHierarchy($r1);
        super.setup();
        this.mContext = $r1;
        this.mFragmentManager = $r2;
        ensureContent();
    }

    public void setup(Context $r1, FragmentManager $r2, int $i0) throws  {
        ensureHierarchy($r1);
        super.setup();
        this.mContext = $r1;
        this.mFragmentManager = $r2;
        this.mContainerId = $i0;
        ensureContent();
        this.mRealTabContent.setId($i0);
        if (getId() == -1) {
            setId(16908306);
        }
    }

    private void ensureContent() throws  {
        if (this.mRealTabContent == null) {
            this.mRealTabContent = (FrameLayout) findViewById(this.mContainerId);
            if (this.mRealTabContent == null) {
                throw new IllegalStateException("No tab content FrameLayout found for id " + this.mContainerId);
            }
        }
    }

    public void setOnTabChangedListener(OnTabChangeListener $r1) throws  {
        this.mOnTabChangeListener = $r1;
    }

    public void addTab(@Signature({"(", "Landroid/widget/TabHost$TabSpec;", "Ljava/lang/Class", "<*>;", "Landroid/os/Bundle;", ")V"}) TabSpec $r1, @Signature({"(", "Landroid/widget/TabHost$TabSpec;", "Ljava/lang/Class", "<*>;", "Landroid/os/Bundle;", ")V"}) Class<?> $r2, @Signature({"(", "Landroid/widget/TabHost$TabSpec;", "Ljava/lang/Class", "<*>;", "Landroid/os/Bundle;", ")V"}) Bundle $r3) throws  {
        $r1.setContent(new DummyTabFactory(this.mContext));
        String $r7 = $r1.getTag();
        TabInfo $r4 = new TabInfo($r7, $r2, $r3);
        if (this.mAttached) {
            $r4.fragment = this.mFragmentManager.findFragmentByTag($r7);
            if (!($r4.fragment == null || $r4.fragment.isDetached())) {
                FragmentTransaction $r10 = this.mFragmentManager.beginTransaction();
                $r10.detach($r4.fragment);
                $r10.commit();
            }
        }
        this.mTabs.add($r4);
        addTab($r1);
    }

    protected void onAttachedToWindow() throws  {
        super.onAttachedToWindow();
        String $r1 = getCurrentTabTag();
        FragmentTransaction $r2 = null;
        for (int $i0 = 0; $i0 < this.mTabs.size(); $i0++) {
            TabInfo $r5 = (TabInfo) this.mTabs.get($i0);
            $r5.fragment = this.mFragmentManager.findFragmentByTag($r5.tag);
            if (!($r5.fragment == null || $r5.fragment.isDetached())) {
                if ($r5.tag.equals($r1)) {
                    this.mLastTab = $r5;
                } else {
                    if ($r2 == null) {
                        $r2 = this.mFragmentManager.beginTransaction();
                    }
                    $r2.detach($r5.fragment);
                }
            }
        }
        this.mAttached = true;
        $r2 = doTabChanged($r1, $r2);
        if ($r2 != null) {
            $r2.commit();
            this.mFragmentManager.executePendingTransactions();
        }
    }

    protected void onDetachedFromWindow() throws  {
        super.onDetachedFromWindow();
        this.mAttached = false;
    }

    protected Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        $r1.curTab = getCurrentTabTag();
        return $r1;
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            setCurrentTabByTag($r2.curTab);
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    public void onTabChanged(String $r1) throws  {
        if (this.mAttached) {
            FragmentTransaction $r2 = doTabChanged($r1, null);
            if ($r2 != null) {
                $r2.commit();
            }
        }
        if (this.mOnTabChangeListener != null) {
            this.mOnTabChangeListener.onTabChanged($r1);
        }
    }

    private FragmentTransaction doTabChanged(String $r1, FragmentTransaction $r2) throws  {
        TabInfo $r3 = null;
        for (int $i0 = 0; $i0 < this.mTabs.size(); $i0++) {
            TabInfo $r6 = (TabInfo) this.mTabs.get($i0);
            if ($r6.tag.equals($r1)) {
                $r3 = $r6;
            }
        }
        if ($r3 == null) {
            throw new IllegalStateException("No tab known for tag " + $r1);
        } else if (this.mLastTab == $r3) {
            return $r2;
        } else {
            if ($r2 == null) {
                $r2 = this.mFragmentManager.beginTransaction();
            }
            if (!(this.mLastTab == null || this.mLastTab.fragment == null)) {
                $r2.detach(this.mLastTab.fragment);
            }
            if ($r3 != null) {
                if ($r3.fragment == null) {
                    $r3.fragment = Fragment.instantiate(this.mContext, $r3.clss.getName(), $r3.args);
                    $r2.add(this.mContainerId, $r3.fragment, $r3.tag);
                } else {
                    $r2.attach($r3.fragment);
                }
            }
            this.mLastTab = $r3;
            return $r2;
        }
    }
}
