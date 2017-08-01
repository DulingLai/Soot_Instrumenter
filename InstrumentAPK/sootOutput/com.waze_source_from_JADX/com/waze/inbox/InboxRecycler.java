package com.waze.inbox;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.ViewGroup;
import com.waze.AppService;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.inbox.InboxItemView.InboxItemListener;
import com.waze.inbox.InboxNativeManager.InboxDataListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InboxRecycler extends RecyclerView implements InboxItemListener, InboxDataListener {
    private static final long ANIMATION_DELAY_INCREMENTAL_STEP = 50;
    private Map<String, Boolean> mCheckedItemsRegistry;
    private boolean mHasMore;
    private boolean mIsLoadingMore;
    private boolean mIsReloading;
    private boolean mIsSelectingAll;
    private boolean mIsSelfInvoked;
    private InboxListener mListener;
    private List<InboxMessage> mMessages;

    public interface InboxListener {
        void onInboxItemSelected() throws ;

        void onInboxLoaded() throws ;

        void onNoInboxItemsSelected() throws ;
    }

    class C17862 implements Runnable {
        C17862() throws  {
        }

        public void run() throws  {
            for (InboxMessage $r6 : InboxRecycler.this.mMessages) {
                InboxRecycler.this.mCheckedItemsRegistry.put($r6.id, Boolean.valueOf(true));
            }
            InboxRecycler.this.mIsSelectingAll = false;
        }
    }

    class C17884 implements Runnable {
        C17884() throws  {
        }

        public void run() throws  {
            for (InboxMessage $r6 : InboxRecycler.this.mMessages) {
                InboxRecycler.this.mCheckedItemsRegistry.put($r6.id, Boolean.valueOf(false));
            }
            InboxRecycler.this.mIsSelectingAll = false;
        }
    }

    class C17895 implements Runnable {
        C17895() throws  {
        }

        public void run() throws  {
            InboxRecycler.this.reloadData();
        }
    }

    class C17906 implements Runnable {
        C17906() throws  {
        }

        public void run() throws  {
            InboxRecycler.this.getAdapter().notifyDataSetChanged();
        }
    }

    private class InboxAdapter extends Adapter<InboxViewHolder> {
        private InboxAdapter() throws  {
        }

        public InboxViewHolder onCreateViewHolder(ViewGroup parent, int viewType) throws  {
            return new InboxViewHolder(new InboxItemView(InboxRecycler.this.getContext()));
        }

        public void onBindViewHolder(InboxViewHolder $r1, int $i0) throws  {
            boolean $z1;
            boolean $z0 = true;
            InboxMessage $r5 = (InboxMessage) InboxRecycler.this.mMessages.get($i0);
            if ($i0 == 0) {
                $z1 = true;
            } else {
                $z1 = false;
            }
            if ($i0 != InboxRecycler.this.mMessages.size() - 1) {
                $z0 = false;
            }
            $r1.bindView($r5, $z1, $z0);
            if ($i0 >= InboxRecycler.this.mMessages.size() - 1 && InboxRecycler.this.mHasMore) {
                InboxRecycler.this.loadMoreMessages();
            }
        }

        public int getItemCount() throws  {
            return InboxRecycler.this.mMessages.size();
        }
    }

    private class InboxViewHolder extends ViewHolder {
        private InboxItemView mInboxItemView;

        public InboxViewHolder(InboxItemView $r2) throws  {
            super($r2);
            this.mInboxItemView = $r2;
            this.mInboxItemView.setListener(InboxRecycler.this);
        }

        public void bindView(InboxMessage $r1, boolean $z0, boolean $z1) throws  {
            this.mInboxItemView.setModel($r1);
            if ($z0) {
                this.mInboxItemView.applyFirstItemPadding();
            }
            if ($z1) {
                this.mInboxItemView.applyLastItemPadding();
            }
        }
    }

    public InboxRecycler(Context $r1) throws  {
        this($r1, null);
    }

    public InboxRecycler(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public InboxRecycler(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        this.mCheckedItemsRegistry = new HashMap();
        this.mMessages = new ArrayList();
        setLayoutManager(new LinearLayoutManager(getContext()));
        setAdapter(new InboxAdapter());
    }

    public void setListener(InboxListener $r1) throws  {
        this.mListener = $r1;
    }

    public void reloadData() throws  {
        if (!this.mIsReloading) {
            this.mIsReloading = true;
            this.mIsSelfInvoked = true;
            InboxNativeManager $r1 = InboxNativeManager.getInstance();
            $r1.removeDataListener(this);
            $r1.addDataListener(this);
            InboxNativeManager.getInstance().requestRefresh();
            $r1.getMessageList(this);
        }
    }

    public void selectAll() throws  {
        if (!this.mIsSelectingAll) {
            this.mIsSelectingAll = true;
            int $i0 = 0;
            for (int $i1 = 0; $i1 < getChildCount(); $i1++) {
                if (getChildAt($i1) instanceof InboxItemView) {
                    final InboxItemView $r2 = (InboxItemView) getChildAt($i1);
                    postDelayed(new Runnable() {
                        public void run() throws  {
                            InboxRecycler.this.setItemSelected($r2, true);
                        }
                    }, (long) $i0);
                }
                $i0 = (int) (((long) $i0) + ANIMATION_DELAY_INCREMENTAL_STEP);
            }
            postDelayed(new C17862(), (long) $i0);
        }
    }

    public void unselectAll() throws  {
        if (!this.mIsSelectingAll) {
            this.mIsSelectingAll = true;
            int $i0 = 0;
            for (int $i1 = 0; $i1 < getChildCount(); $i1++) {
                if (getChildAt($i1) instanceof InboxItemView) {
                    final InboxItemView $r2 = (InboxItemView) getChildAt($i1);
                    postDelayed(new Runnable() {
                        public void run() throws  {
                            InboxRecycler.this.setItemSelected($r2, false);
                        }
                    }, (long) $i0);
                }
                $i0 = (int) (((long) $i0) + ANIMATION_DELAY_INCREMENTAL_STEP);
            }
            postDelayed(new C17884(), (long) $i0);
        }
    }

    public void markSelectedAsRead() throws  {
        List<InboxMessage> $r1 = getSelectedItems();
        String[] $r2 = getSelectedItemIds();
        for (InboxMessage inboxMessage : $r1) {
            inboxMessage.unread = false;
        }
        InboxNativeManager.getInstance().setRead($r2, true);
        getAdapter().notifyDataSetChanged();
    }

    public void deleteSelected() throws  {
        InboxNativeManager.getInstance().deleteMessages(getSelectedItemIds());
        postDelayed(new C17895(), 1500);
    }

    private String[] getSelectedItemIds() throws  {
        List $r2 = getSelectedItems();
        String[] $r1 = new String[$r2.size()];
        for (int $i0 = 0; $i0 < $r2.size(); $i0++) {
            $r1[$i0] = ((InboxMessage) $r2.get($i0)).id;
        }
        return $r1;
    }

    private List<InboxMessage> getSelectedItems() throws  {
        ArrayList $r1 = new ArrayList();
        for (InboxMessage $r5 : this.mMessages) {
            if (isChecked($r5)) {
                $r1.add($r5);
            }
        }
        return $r1;
    }

    private void setItemSelected(InboxItemView $r1, boolean $z0) throws  {
        InboxMessage $r2 = $r1.getModel();
        if ((isChecked($r2) && !$z0) || (!isChecked($r2) && $z0)) {
            $r1.toggleCheckbox();
        }
    }

    public boolean isChecked(InboxMessage $r1) throws  {
        return this.mCheckedItemsRegistry.containsKey($r1.id) && ((Boolean) this.mCheckedItemsRegistry.get($r1.id)).booleanValue();
    }

    public void onCheckChanged(InboxMessage $r1, boolean $z0) throws  {
        this.mCheckedItemsRegistry.put($r1.id, Boolean.valueOf($z0));
        postDelayed(new C17906(), 200);
        if ($z0 && this.mListener != null) {
            this.mListener.onInboxItemSelected();
        } else if (!isAnySelected() && this.mListener != null) {
            this.mListener.onNoInboxItemsSelected();
        }
    }

    public boolean isAnySelected() throws  {
        boolean $z0 = false;
        for (int $i0 = 0; $i0 < this.mMessages.size() && !$z0; $i0++) {
            if (isChecked((InboxMessage) this.mMessages.get($i0))) {
                $z0 = true;
            }
        }
        return $z0;
    }

    public boolean isAllSelected() throws  {
        boolean $z0 = false;
        for (int $i0 = 0; $i0 < this.mMessages.size() && !$z0; $i0++) {
            if (!isChecked((InboxMessage) this.mMessages.get($i0))) {
                $z0 = true;
            }
        }
        return !$z0;
    }

    public int getTotalUnreadMessages() throws  {
        int $i0 = 0;
        if (this.mMessages == null) {
            return 0;
        }
        if (this.mMessages.size() <= 0) {
            return 0;
        }
        for (InboxMessage inboxMessage : this.mMessages) {
            if (inboxMessage.unread) {
                $i0++;
            }
        }
        return $i0;
    }

    public void onItemSelected(InboxMessage $r1) throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_INBOX_TITLE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_MESSAGE_ID, $r1.id);
        if ($r1.unread) {
            $r1.unread = false;
            InboxNativeManager.getInstance().setRead(new String[]{$r1.id}, true);
            getAdapter().notifyDataSetChanged();
        }
        InboxPreviewActivity.Start(AppService.getActiveActivity(), $r1, null);
    }

    public void onData(InboxMessage[] $r1, int badge, int unread, boolean $z0) throws  {
        if (this.mIsSelfInvoked) {
            this.mIsSelfInvoked = false;
            this.mIsLoadingMore = false;
            this.mMessages.clear();
            if (this.mIsReloading) {
                this.mIsReloading = false;
            }
            this.mMessages.addAll(Arrays.asList($r1));
            this.mHasMore = $z0;
            if (this.mListener != null) {
                this.mListener.onInboxLoaded();
            }
            getAdapter().notifyDataSetChanged();
        }
    }

    private void loadMoreMessages() throws  {
        if (!this.mIsLoadingMore) {
            this.mIsLoadingMore = true;
            this.mIsSelfInvoked = true;
            InboxNativeManager.getInstance().loadMoreMessages();
        }
    }
}
