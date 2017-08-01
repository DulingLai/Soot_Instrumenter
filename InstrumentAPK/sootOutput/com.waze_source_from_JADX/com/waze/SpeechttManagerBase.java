package com.waze;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.auth.firstparty.shared.Status;
import dalvik.annotation.Signature;
import java.util.ArrayList;

public class SpeechttManagerBase {
    public static final int SPEECHTT_FLAG_EXTERNAL_RECOGNIZER = 16;
    public static final int SPEECHTT_FLAG_INPUT_CMD = 8;
    public static final int SPEECHTT_FLAG_INPUT_SEARCH = 2;
    public static final int SPEECHTT_FLAG_INPUT_TEXT = 4;
    public static final int SPEECHTT_FLAG_TIMEOUT_ENABLED = 1;
    private static final int SPEECHTT_MAX_RESULTS = 1;
    public static final int SPEECHTT_RES_STATUS_ERROR = 0;
    public static final int SPEECHTT_RES_STATUS_NO_RESULTS = 2;
    public static final int SPEECHTT_RES_STATUS_SUCCESS = 1;
    protected volatile boolean mBusy = false;
    protected Callback mCallback = null;

    public static abstract class Callback {
        public final long CONTEXT_NULL;
        public volatile long mCbContext;

        protected abstract void onResults(long j, String str, int i) throws ;

        public Callback() throws  {
            this.CONTEXT_NULL = 0;
            this.mCbContext = 0;
            this.mCbContext = 0;
        }

        public Callback(long $l0) throws  {
            this.CONTEXT_NULL = 0;
            this.mCbContext = 0;
            this.mCbContext = $l0;
        }

        private void run(String $r1, int $i0) throws  {
            onResults(this.mCbContext, $r1, $i0);
        }
    }

    class C13062 implements Runnable {
        C13062() throws  {
        }

        public void run() throws  {
            MainActivity $r1 = AppService.getMainActivity();
            if ($r1 != null) {
                $r1.finishActivity(4096);
            } else {
                Logger.m38e("Cannot stop speech recognizer. Main activity is not available");
            }
        }
    }

    protected class OnOkMsgNoService implements OnClickListener {
        protected OnOkMsgNoService() throws  {
        }

        public void onClick(DialogInterface $r1, int which) throws  {
            $r1.cancel();
            SpeechttManagerBase.this.ResultsHandler(null);
        }
    }

    protected native void InitSpeechttManagerNTV() throws ;

    public boolean IsAvailable() throws  {
        return true;
    }

    protected native void SpeechttManagerCallbackNTV(long j, String str, int i) throws ;

    public void InitNativeLayer() throws  {
        InitSpeechttManagerNTV();
    }

    public void Start(Callback $r1, long aCbContext, int aTimeout, byte[] $r2, byte[] $r3, int $i2) throws  {
        if (this.mBusy) {
            Logger.m43w("Cannot start speech recognition - the engine is busy");
            return;
        }
        setCallback($r1);
        final Intent $r5 = Prepare($r2, $r3, $i2);
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r4 = AppService.getMainActivity();
                if ($r4 != null) {
                    try {
                        $r4.startActivityForResult($r5, 4096);
                        return;
                    } catch (ActivityNotFoundException $r1) {
                        Logger.m39e("Error. Speech to text service is not available", $r1);
                        MsgBox.ShowOk(Status.EXTRA_KEY_STATUS, "Speech to text service is not available", "Ok", new OnOkMsgNoService());
                        return;
                    }
                }
                Logger.m38e("Cannot start speech recognizer. Main activity is not available");
            }
        });
        this.mBusy = true;
    }

    public void StartNative(long $l0, int $i1, byte[] $r1, byte[] $r2, int $i2) throws  {
        setNativeLayerCallback($l0);
        Start(this.mCallback, $l0, $i1, $r1, $r2, $i2);
    }

    public void Stop() throws  {
        Log.i(Logger.TAG, "Stopping the recognition service");
        AppService.Post(new C13062());
    }

    public void OnResultsExternal(int $i0, Intent $r1) throws  {
        if ($i0 == -1) {
            ResultsHandler($r1.getStringArrayListExtra("android.speech.extra.RESULTS"));
            return;
        }
        if ($i0 == 0) {
            Log.w(Logger.TAG, "Recognition is canceled: " + $i0);
            Logger.m43w("Recognition process is canceled!");
        } else {
            Log.w(Logger.TAG, "Error result is reported: " + $i0);
            Logger.m43w("Not valid result code (" + $i0 + ") is reported while recognition process");
        }
        ResultsHandler(null);
    }

    public void setCallback(Callback $r1) throws  {
        this.mCallback = $r1;
    }

    protected final Intent Prepare(byte[] $r1, byte[] $r2, int $i0) throws  {
        Intent $r3 = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        $r3.putExtra("android.speech.extra.LANGUAGE_MODEL", GetModel($i0));
        $r3.putExtra("android.speech.extra.MAX_RESULTS", 1);
        if ($r1 != null) {
            $r3.putExtra("android.speech.extra.LANGUAGE", new String($r1, 0, $r1.length));
        }
        if ($r2 == null) {
            return $r3;
        }
        $r3.putExtra("android.speech.extra.PROMPT", new String($r2, 0, $r2.length));
        return $r3;
    }

    protected String GetModel(int $i0) throws  {
        return ($i0 & 2) > 0 ? "web_search" : "free_form";
    }

    protected void Reset() throws  {
        this.mBusy = false;
    }

    protected void setNativeLayerCallback(long $l0) throws  {
        this.mCallback = new Callback($l0) {
            protected void onResults(final long $l0, final String $r1, final int $i1) throws  {
                NativeManager.Post(new Runnable() {
                    public void run() throws  {
                        SpeechttManagerBase.this.SpeechttManagerCallbackNTV($l0, $r1, $i1);
                    }
                });
            }
        };
    }

    protected void ResultsHandler(@Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r1) throws  {
        byte $b2;
        String $r2 = null;
        if ($r1 != null) {
            $r2 = new String();
            for (int $i0 = 0; $i0 < $r1.size(); $i0++) {
                String $r5 = $r2 + ((String) $r1.get($i0));
                $r2 = $r5;
                if ($i0 < $r1.size() - 1) {
                    $r2 = $r5 + " ";
                }
            }
            Log.i(Logger.TAG, "Recognizer result: " + $r2);
            $b2 = (byte) 1;
        } else {
            Log.i(Logger.TAG, "There are no results...");
            $b2 = (byte) 2;
        }
        if (this.mCallback != null) {
            this.mCallback.run($r2, $b2);
        }
        Reset();
    }
}
