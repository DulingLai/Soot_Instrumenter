package android.support.v4.speech.tts;

import android.os.Build.VERSION;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.speech.tts.UtteranceProgressListener;
import dalvik.annotation.Signature;
import java.util.Locale;
import java.util.Set;

class TextToSpeechICSMR1 {
    public static final String KEY_FEATURE_EMBEDDED_SYNTHESIS = "embeddedTts";
    public static final String KEY_FEATURE_NETWORK_SYNTHESIS = "networkTts";

    interface UtteranceProgressListenerICSMR1 {
        void onDone(String str) throws ;

        void onError(String str) throws ;

        void onStart(String str) throws ;
    }

    TextToSpeechICSMR1() throws  {
    }

    static Set<String> getFeatures(@Signature({"(", "Landroid/speech/tts/TextToSpeech;", "Ljava/util/Locale;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) TextToSpeech $r0, @Signature({"(", "Landroid/speech/tts/TextToSpeech;", "Ljava/util/Locale;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) Locale $r1) throws  {
        if (VERSION.SDK_INT >= 15) {
            return $r0.getFeatures($r1);
        }
        return null;
    }

    static void setUtteranceProgressListener(TextToSpeech $r0, final UtteranceProgressListenerICSMR1 $r1) throws  {
        if (VERSION.SDK_INT >= 15) {
            $r0.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                public void onStart(String $r1) throws  {
                    $r1.onStart($r1);
                }

                public void onError(String $r1) throws  {
                    $r1.onError($r1);
                }

                public void onDone(String $r1) throws  {
                    $r1.onDone($r1);
                }
            });
        } else {
            $r0.setOnUtteranceCompletedListener(new OnUtteranceCompletedListener() {
                public void onUtteranceCompleted(String $r1) throws  {
                    $r1.onStart($r1);
                    $r1.onDone($r1);
                }
            });
        }
    }
}
