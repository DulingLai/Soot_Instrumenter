package com.waze;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;
import java.util.HashMap;
import java.util.Locale;

public final class TtsManager implements OnInitListener {
    private final int TTS_RES_STATUS_ERROR = 1;
    private final int TTS_RES_STATUS_NO_RESULTS = 8;
    private final int TTS_RES_STATUS_PARTIAL_SUCCESS = 2;
    private final int TTS_RES_STATUS_SUCCESS = 4;
    private HashMap<String, Long> mRequestMap;
    private TextToSpeech mTts = null;
    private volatile boolean mTtsInitilized = false;

    class C13301 implements Runnable {
        C13301() throws  {
        }

        public void run() throws  {
            TtsManager.this.mTts = new TextToSpeech(AppService.getAppContext(), TtsManager.this);
        }
    }

    private final class UtteranceCompletedListener implements OnUtteranceCompletedListener {
        private UtteranceCompletedListener() throws  {
        }

        public void onUtteranceCompleted(String $r1) throws  {
            Log.w(Logger.TAG, "Request completed for " + $r1);
            Long $r7 = (Long) TtsManager.this.mRequestMap.get($r1);
            if ($r7 == null) {
                Logger.ee("WazeTttManager Error. There is no request for " + $r1);
            }
            TtsManager.this.TtsManagerCallbackNTV($r7.longValue(), 4);
        }
    }

    private native void InitTtsManagerNTV() throws ;

    private native void TtsManagerCallbackNTV(long j, int i) throws ;

    TtsManager() throws  {
    }

    public void InitNativeLayer() throws  {
        InitTtsManagerNTV();
    }

    public void Submit(String $r1, String $r2, String $r3, long $l0) throws  {
        if (this.mTtsInitilized) {
            Log.w(Logger.TAG, "Received request for " + $r1 + ". Path: " + $r2);
            if (setLanguage($r3)) {
                HashMap $r4 = new HashMap();
                $r4.put("utteranceId", $r1);
                if (this.mTts.synthesizeToFile($r1, $r4, $r2) == -1) {
                    TtsManagerCallbackNTV($l0, 1);
                    return;
                } else {
                    this.mRequestMap.put($r1, new Long($l0));
                    return;
                }
            }
            Log.w(Logger.TAG, "Error setting language for  " + $r3);
            TtsManagerCallbackNTV($l0, 1);
        }
    }

    public void Prepare() throws  {
        checkData();
    }

    public void onDataCheckResult(int $i0, Intent data) throws  {
        if ($i0 == 1) {
            NativeManager.Post(new C13301());
            return;
        }
        Log.w(Logger.TAG, "TTS Data doesn't present - installing");
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            data = new Intent();
            data.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
            $r3.startActivityForResult(data, 16384);
        }
    }

    public void onDataInstallResult(int resultCode, Intent data) throws  {
        checkData();
    }

    public void onInit(int $i0) throws  {
        if ($i0 == 0) {
            this.mTtsInitilized = true;
            this.mRequestMap = new HashMap();
            this.mTts.setOnUtteranceCompletedListener(new UtteranceCompletedListener());
        }
    }

    private void checkData() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            Intent $r1 = new Intent();
            $r1.setAction("android.speech.tts.engine.CHECK_TTS_DATA");
            $r2.startActivityForResult($r1, 8192);
        }
    }

    private boolean setLanguage(String $r1) throws  {
        Locale $r2 = null;
        if ($r1.equalsIgnoreCase("italiano")) {
            $r2 = Locale.ITALIAN;
        }
        if ($r2 == null) {
            Logger.ee("Error setting locale for language: " + $r1);
            return false;
        } else if (this.mTts.isLanguageAvailable($r2) != 0) {
            return false;
        } else {
            Log.i(Logger.TAG, "Setting TTS language to " + $r2.toString());
            this.mTts.setLanguage($r2);
            return true;
        }
    }
}
