package android.support.v4.speech.tts;

import android.content.Context;
import android.os.Build.VERSION;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

class TextToSpeechICS {
    private static final String TAG = "android.support.v4.speech.tts";

    TextToSpeechICS() throws  {
    }

    static TextToSpeech construct(Context $r0, OnInitListener $r1, String $r2) throws  {
        if (VERSION.SDK_INT >= 14) {
            return new TextToSpeech($r0, $r1, $r2);
        }
        if ($r2 == null) {
            return new TextToSpeech($r0, $r1);
        }
        Log.w(TAG, "Can't specify tts engine on this device");
        return new TextToSpeech($r0, $r1);
    }
}
