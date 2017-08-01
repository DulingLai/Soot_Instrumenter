package com.waze.inbox;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.NativeManager.OnUrlHandleResult;
import com.waze.view.web.SimpleWebActivity;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class InboxPreviewActivity extends SimpleWebActivity {
    private InboxMessage mMessage;
    private boolean mShowInbox = false;

    public static void Start(final Activity $r0, final InboxMessage $r1, final Boolean $r2) throws  {
        if ($r1 != null) {
            NativeManager.getInstance().UrlHandler($r1.message, new OnUrlHandleResult() {

                class C17831 implements Runnable {
                    C17831() throws  {
                    }

                    public void run() throws  {
                        InboxPreviewActivity.StartOnUrlHandled($r0, $r1, $r2);
                    }
                }

                public void event() throws  {
                    if (this.result) {
                        Logger.d_("INBOX", "Url was handled - ignoring preview");
                    } else {
                        AppService.Post(new C17831());
                    }
                }
            });
        }
    }

    private static boolean StartOnUrlHandled(Activity $r4, InboxMessage $r0, Boolean $r1) throws  {
        if ($r4 == null) {
            $r4 = AppService.getActiveActivity();
        }
        Intent $r2 = new Intent($r4, InboxPreviewActivity.class);
        $r2.putExtra(InboxConstants.INBOX_EXTRA_ID_SELECTED_MSG, $r0);
        if ($r1 != null) {
            $r2.putExtra(InboxConstants.INBOX_EXTRA_ID_SHOW_INBOX, $r1);
        }
        $r4.startActivityForResult($r2, 15);
        return true;
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mMessage = (InboxMessage) getIntent().getExtras().getSerializable(InboxConstants.INBOX_EXTRA_ID_SELECTED_MSG);
        this.mShowInbox = getIntent().getExtras().getBoolean(InboxConstants.INBOX_EXTRA_ID_SHOW_INBOX, false);
        setTitleStr(this.mMessage.title);
    }

    public void onWebViewSize(int width, int height) throws  {
        if (this.mMessage.messageType == 0 || this.mMessage.messageType == 2) {
            loadUrl(this.mMessage.message);
        }
        if (this.mMessage.messageType == 1) {
            try {
                File $r5 = File.createTempFile("content", ".tmp", getCacheDir());
                FileOutputStream $r2 = new FileOutputStream($r5);
                $r2.write(this.mMessage.message.getBytes());
                $r2.close();
                Log.d(Logger.TAG, "Loading html from file: " + $r5.getAbsolutePath());
                loadUrl("file://" + $r5.getAbsolutePath());
                return;
            } catch (IOException $r1) {
                Log.e(Logger.TAG, "IO exception while creating message preview temp file!", $r1);
                return;
            }
        }
        Log.e(Logger.TAG, "Unsupported message type: " + this.mMessage.messageType);
    }
}
