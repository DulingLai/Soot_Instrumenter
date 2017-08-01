package com.waze.inbox;

import com.waze.AppService;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import java.util.ArrayList;

public class InboxNativeManager {
    public static final int INBOX_STATUS_FAILURE = Integer.MIN_VALUE;
    public static final int INBOX_STATUS_FAILURE_TIMEOUT = -2147483647;
    public static final int INBOX_STATUS_MORE_MESSAGES = 1;
    public static final int INBOX_STATUS_SUCCESS = 0;
    private static InboxNativeManager mInstance = null;
    private final ArrayList<InboxDataListener> mInboxDataListeners = new ArrayList();
    private boolean mRefreshActive = false;

    static class C17741 extends RunnableExecutor {
        C17741() throws  {
        }

        public void event() throws  {
            InboxNativeManager.mInstance.registerDataListenerNTV();
        }
    }

    class C17752 implements Runnable {
        C17752() throws  {
        }

        public void run() throws  {
            InboxNativeManager.this.InitNativeLayerNTV();
        }
    }

    class C17763 implements Runnable {
        C17763() throws  {
        }

        public void run() throws  {
            InboxNativeManager.this.notifyDataListeners();
        }
    }

    class C17774 implements Runnable {
        C17774() throws  {
        }

        public void run() throws  {
            InboxNativeManager.this.loadMoreMessagesNTV();
        }
    }

    class C17818 implements Runnable {
        C17818() throws  {
        }

        public void run() throws  {
            InboxNativeManager.this.refreshNTV();
        }
    }

    public interface IInboxMessageListDataHandler {
        void onMessageList(InboxMessage[] inboxMessageArr, boolean z) throws ;
    }

    public interface InboxCountersHandler {
        void onCounters(int i, int i2, int i3) throws ;
    }

    public interface InboxDataListener {
        void onData(InboxMessage[] inboxMessageArr, int i, int i2, boolean z) throws ;
    }

    private native void InitNativeLayerNTV() throws ;

    private native void deleteAllMessagesNTV() throws ;

    private native void deleteMessagesNTV(String[] strArr) throws ;

    private native int getInboxSizeNTV() throws ;

    private native InboxMessage[] getMessageListNTV() throws ;

    private native void loadMoreMessagesNTV() throws ;

    private native boolean moreMessagesNTV() throws ;

    private native void refreshNTV() throws ;

    private native void registerDataListenerNTV() throws ;

    private native void resetInboxBadgeNTV() throws ;

    private native void setReadNTV(String[] strArr, boolean z) throws ;

    public native int getInboxBadgeNTV() throws ;

    public native int getInboxUnreadNTV() throws ;

    public static InboxNativeManager getInstance() throws  {
        create();
        return mInstance;
    }

    public static InboxNativeManager create() throws  {
        if (mInstance == null) {
            mInstance = new InboxNativeManager();
            mInstance.initNativeLayer();
            NativeManager.registerOnAppStartedEvent(new C17741());
        }
        return mInstance;
    }

    private void initNativeLayer() throws  {
        NativeManager.Post(new C17752());
    }

    public void getMessageList(InboxDataListener dataHandler) throws  {
        NativeManager.Post(new C17763());
    }

    public void loadMoreMessages() throws  {
        NativeManager.Post(new C17774());
    }

    public void getInboxCounters(final InboxCountersHandler $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            int mBadge;
            int mTotal;
            int mUnread;

            public void event() throws  {
                this.mUnread = InboxNativeManager.this.getInboxUnreadNTV();
                this.mTotal = InboxNativeManager.this.getInboxSizeNTV();
                this.mBadge = InboxNativeManager.this.getInboxBadgeNTV();
            }

            public void callback() throws  {
                if ($r1 != null) {
                    $r1.onCounters(this.mBadge, this.mUnread, this.mTotal);
                }
            }
        });
    }

    public void setRead(final String[] $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                InboxNativeManager.this.setReadNTV($r1, $z0);
            }
        });
    }

    public void addDataListener(InboxDataListener $r1) throws  {
        if ($r1 != null && !this.mInboxDataListeners.contains($r1)) {
            this.mInboxDataListeners.add($r1);
        }
    }

    public void removeDataListener(InboxDataListener $r1) throws  {
        if ($r1 != null && this.mInboxDataListeners.contains($r1)) {
            this.mInboxDataListeners.remove($r1);
        }
    }

    private void notifyDataListeners() throws  {
        int $i0 = getInboxUnreadNTV();
        InboxMessage[] $r2 = getMessageListNTV();
        boolean $z0 = moreMessagesNTV();
        final InboxMessage[] inboxMessageArr = $r2;
        final int inboxBadgeNTV = getInboxBadgeNTV();
        final int i = $i0;
        final boolean z = $z0;
        AppService.Post(new Runnable() {
            public void run() throws  {
                for (int $i2 = 0; $i2 < InboxNativeManager.this.mInboxDataListeners.size(); $i2++) {
                    ((InboxDataListener) InboxNativeManager.this.mInboxDataListeners.get($i2)).onData(inboxMessageArr, inboxBadgeNTV, i, z);
                }
            }
        });
    }

    public void requestRefresh() throws  {
        if (!this.mRefreshActive) {
            NativeManager.Post(new C17818());
        }
    }

    protected void showMessage(final InboxMessage $r1, final boolean $z0) throws  {
        final MainActivity $r2 = AppService.getMainActivity();
        if ($r2 == null) {
            Logger.e_("INBOX", "No activity context available");
        } else {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    InboxPreviewActivity.Start($r2, $r1, Boolean.valueOf($z0));
                }
            });
        }
    }

    private void onRefresh(int $i0) throws  {
        Logger.i_("INBOX", "## onRefresh status: " + $i0);
    }

    private void onMoreMessages(int $i0) throws  {
        Logger.i_("INBOX", "## onMoreMessages status: " + $i0);
    }

    private void onDelete(int $i0) throws  {
        Logger.i_("INBOX", "## onDelete status: " + $i0);
    }

    private void onDataListener() throws  {
        notifyDataListeners();
    }

    public void deleteMessages(final String[] $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                InboxNativeManager.this.deleteMessagesNTV($r1);
            }
        });
    }

    public void deleteAllMessages() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                InboxNativeManager.this.deleteAllMessagesNTV();
            }
        });
    }

    public void resetInboxBadge() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                InboxNativeManager.this.resetInboxBadgeNTV();
            }
        });
    }
}
