package android.support.v7.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build.VERSION;
import android.support.v4.view.ActionProvider;
import android.support.v7.appcompat.C0192R;
import android.support.v7.widget.ActivityChooserModel.OnChooseActivityListener;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.SubMenu;
import android.view.View;

public class ShareActionProvider extends ActionProvider {
    private static final int DEFAULT_INITIAL_ACTIVITY_COUNT = 4;
    public static final String DEFAULT_SHARE_HISTORY_FILE_NAME = "share_history.xml";
    private final Context mContext;
    private int mMaxShownActivityCount = 4;
    private OnChooseActivityListener mOnChooseActivityListener;
    private final ShareMenuItemOnMenuItemClickListener mOnMenuItemClickListener = new ShareMenuItemOnMenuItemClickListener();
    private OnShareTargetSelectedListener mOnShareTargetSelectedListener;
    private String mShareHistoryFileName = DEFAULT_SHARE_HISTORY_FILE_NAME;

    public interface OnShareTargetSelectedListener {
        boolean onShareTargetSelected(ShareActionProvider shareActionProvider, Intent intent) throws ;
    }

    private class ShareActivityChooserModelPolicy implements OnChooseActivityListener {
        private ShareActivityChooserModelPolicy() throws  {
        }

        public boolean onChooseActivity(ActivityChooserModel host, Intent $r2) throws  {
            if (ShareActionProvider.this.mOnShareTargetSelectedListener != null) {
                ShareActionProvider.this.mOnShareTargetSelectedListener.onShareTargetSelected(ShareActionProvider.this, $r2);
            }
            return false;
        }
    }

    private class ShareMenuItemOnMenuItemClickListener implements OnMenuItemClickListener {
        private ShareMenuItemOnMenuItemClickListener() throws  {
        }

        public boolean onMenuItemClick(MenuItem $r1) throws  {
            Intent $r6 = ActivityChooserModel.get(ShareActionProvider.this.mContext, ShareActionProvider.this.mShareHistoryFileName).chooseActivity($r1.getItemId());
            if ($r6 != null) {
                String $r4 = $r6.getAction();
                if ("android.intent.action.SEND".equals($r4) || "android.intent.action.SEND_MULTIPLE".equals($r4)) {
                    ShareActionProvider.this.updateIntent($r6);
                }
                ShareActionProvider.this.mContext.startActivity($r6);
            }
            return true;
        }
    }

    public boolean hasSubMenu() throws  {
        return true;
    }

    public ShareActionProvider(Context $r1) throws  {
        super($r1);
        this.mContext = $r1;
    }

    public void setOnShareTargetSelectedListener(OnShareTargetSelectedListener $r1) throws  {
        this.mOnShareTargetSelectedListener = $r1;
        setActivityChooserPolicyIfNeeded();
    }

    public View onCreateActionView() throws  {
        ActivityChooserView $r1 = new ActivityChooserView(this.mContext);
        if (!$r1.isInEditMode()) {
            $r1.setActivityChooserModel(ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName));
        }
        TypedValue $r2 = new TypedValue();
        this.mContext.getTheme().resolveAttribute(C0192R.attr.actionModeShareDrawable, $r2, true);
        $r1.setExpandActivityOverflowButtonDrawable(AppCompatDrawableManager.get().getDrawable(this.mContext, $r2.resourceId));
        $r1.setProvider(this);
        $r1.setDefaultActionButtonContentDescription(C0192R.string.abc_shareactionprovider_share_with_application);
        $r1.setExpandActivityOverflowButtonContentDescription(C0192R.string.abc_shareactionprovider_share_with);
        return $r1;
    }

    public void onPrepareSubMenu(SubMenu $r1) throws  {
        $r1.clear();
        ActivityChooserModel $r4 = ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName);
        PackageManager $r5 = this.mContext.getPackageManager();
        int $i0 = $r4.getActivityCount();
        int $i1 = Math.min($i0, this.mMaxShownActivityCount);
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            ResolveInfo $r6 = $r4.getActivity($i2);
            $r1.add(0, $i2, $i2, $r6.loadLabel($r5)).setIcon($r6.loadIcon($r5)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
        }
        if ($i1 < $i0) {
            $r1 = $r1.addSubMenu(0, $i1, $i1, this.mContext.getString(C0192R.string.abc_activity_chooser_view_see_all));
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r6 = $r4.getActivity($i1);
                $r1.add(0, $i1, $i1, $r6.loadLabel($r5)).setIcon($r6.loadIcon($r5)).setOnMenuItemClickListener(this.mOnMenuItemClickListener);
            }
        }
    }

    public void setShareHistoryFileName(String $r1) throws  {
        this.mShareHistoryFileName = $r1;
        setActivityChooserPolicyIfNeeded();
    }

    public void setShareIntent(Intent $r1) throws  {
        if ($r1 != null) {
            String $r2 = $r1.getAction();
            if ("android.intent.action.SEND".equals($r2) || "android.intent.action.SEND_MULTIPLE".equals($r2)) {
                updateIntent($r1);
            }
        }
        ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setIntent($r1);
    }

    private void setActivityChooserPolicyIfNeeded() throws  {
        if (this.mOnShareTargetSelectedListener != null) {
            if (this.mOnChooseActivityListener == null) {
                this.mOnChooseActivityListener = new ShareActivityChooserModelPolicy();
            }
            ActivityChooserModel.get(this.mContext, this.mShareHistoryFileName).setOnChooseActivityListener(this.mOnChooseActivityListener);
        }
    }

    private void updateIntent(Intent $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            $r1.addFlags(134742016);
        } else {
            $r1.addFlags(524288);
        }
    }
}
