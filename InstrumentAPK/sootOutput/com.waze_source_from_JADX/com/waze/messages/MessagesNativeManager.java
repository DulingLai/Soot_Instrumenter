package com.waze.messages;

import android.content.Intent;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.user.UserData;

public class MessagesNativeManager {
    private static MessagesNativeManager mInstance = null;
    private final _OnSendHandler mOnSendHandler = new _OnSendHandler();
    private EditorContext mPendingEditor = null;

    class C19661 implements Runnable {
        C19661() throws  {
        }

        public void run() throws  {
            MessagesNativeManager.this.InitNativeLayerNTV();
        }
    }

    class C19672 implements Runnable {
        C19672() throws  {
        }

        public void run() throws  {
            MessagesNativeManager.this.startPrivateMessageNTV();
        }
    }

    class C19683 implements Runnable {
        C19683() throws  {
        }

        public void run() throws  {
            MessagesNativeManager.this.startPublicMessageNTV();
        }
    }

    class C19694 implements Runnable {
        C19694() throws  {
        }

        public void run() throws  {
            Intent $r1 = r7;
            Intent r7 = new Intent(MessagesNativeManager.this.mPendingEditor.mContext, UserMessageEditorActivity.class);
            $r1.putExtra(MessagesConstants.EXTRA_ID_IS_PRIVATE_MSG, true);
            $r1.putExtra(MessagesConstants.EXTRA_ID_USER_DATA, MessagesNativeManager.this.mPendingEditor.mUser);
            $r1.putExtra(MessagesConstants.EXTRA_ID_ON_SEND_HANDLER, MessagesNativeManager.this.mOnSendHandler);
            MessagesNativeManager.this.mPendingEditor.mContext.startActivityForResult($r1, MessagesNativeManager.this.mPendingEditor.mRqCode);
            MessagesNativeManager.this.mPendingEditor = null;
        }
    }

    class C19705 implements Runnable {
        C19705() throws  {
        }

        public void run() throws  {
            MessagesNativeManager.this.mPendingEditor = null;
        }
    }

    class C19716 implements Runnable {
        C19716() throws  {
        }

        public void run() throws  {
            Intent $r1 = r7;
            Intent r7 = new Intent(MessagesNativeManager.this.mPendingEditor.mContext, UserMessageEditorActivity.class);
            $r1.putExtra(MessagesConstants.EXTRA_ID_IS_PRIVATE_MSG, false);
            $r1.putExtra(MessagesConstants.EXTRA_ID_USER_DATA, MessagesNativeManager.this.mPendingEditor.mUser);
            $r1.putExtra(MessagesConstants.EXTRA_ID_ON_SEND_HANDLER, MessagesNativeManager.this.mOnSendHandler);
            MessagesNativeManager.this.mPendingEditor.mContext.startActivityForResult($r1, 0);
            MessagesNativeManager.this.mPendingEditor = null;
        }
    }

    public static class EditorContext {
        ActivityBase mContext;
        public int mRqCode = 0;
        int mType;
        UserData mUser;

        public EditorContext(ActivityBase $r1, int $i0, UserData $r2) throws  {
            this.mContext = $r1;
            this.mType = $i0;
            this.mUser = $r2;
        }
    }

    protected static class _OnSendHandler extends OnSendHandler {
        protected static final long serialVersionUID = 16777217;

        protected _OnSendHandler() throws  {
        }

        public void onSend(final boolean $z0, final UserData $r1, final String $r2) throws  {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    MessagesNativeManager.getInstance().onSendMessageNTV($z0, $r1, $r2);
                }
            });
        }
    }

    private native void InitNativeLayerNTV() throws ;

    private native void onSendMessageNTV(boolean z, UserData userData, String str) throws ;

    private native void startPrivateMessageNTV() throws ;

    private native void startPublicMessageNTV() throws ;

    public static MessagesNativeManager getInstance() throws  {
        create();
        return mInstance;
    }

    public static MessagesNativeManager create() throws  {
        if (mInstance == null) {
            mInstance = new MessagesNativeManager();
            mInstance.initNativeLayer();
        }
        return mInstance;
    }

    private void initNativeLayer() throws  {
        NativeManager.Post(new C19661());
    }

    public void startPrivate(EditorContext $r1) throws  {
        if (this.mPendingEditor != null) {
            Logger.w_(MessagesConstants.LOG_TAG, "There is already message editor request active. Ignoring start");
            return;
        }
        C19672 $r2 = new C19672();
        this.mPendingEditor = $r1;
        NativeManager.Post($r2);
    }

    protected void startPublic(EditorContext $r1) throws  {
        if (this.mPendingEditor != null) {
            Logger.w_(MessagesConstants.LOG_TAG, "There is already message editor request active. Ignoring start");
            return;
        }
        C19683 $r2 = new C19683();
        this.mPendingEditor = $r1;
        NativeManager.Post($r2);
    }

    private void showPrivateMessageEditor(UserData $r1) throws  {
        if (this.mPendingEditor == null) {
            if ($r1 == null) {
                Logger.w_(MessagesConstants.LOG_TAG, "There is no ping editor request active. Ignoring showing");
                return;
            }
            this.mPendingEditor = new EditorContext(AppService.getMainActivity(), 1, $r1);
        }
        AppService.Post(new C19694());
    }

    private void cancelPrivateMessageEditor() throws  {
        AppService.Post(new C19705());
    }

    private void showPublicMessageEditor(UserData $r1) throws  {
        if (this.mPendingEditor == null) {
            if ($r1 == null) {
                Logger.w_(MessagesConstants.LOG_TAG, "There is no ping editor request active. Ignoring showing");
                return;
            }
            this.mPendingEditor = new EditorContext(AppService.getMainActivity(), 0, $r1);
        }
        AppService.Post(new C19716());
    }

    public void sendMessage(final boolean $z0, final UserData $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                MessagesNativeManager.getInstance().onSendMessageNTV($z0, $r1, $r2);
            }
        });
    }
}
