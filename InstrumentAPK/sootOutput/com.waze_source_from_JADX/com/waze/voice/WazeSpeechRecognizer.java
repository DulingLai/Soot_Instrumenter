package com.waze.voice;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.SystemClock;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.strings.DisplayStrings;
import java.util.List;

public class WazeSpeechRecognizer {
    private static long callback;
    private static boolean inProgress = false;
    private static SpeechRecognizer recognizer = null;
    private static long startListeningTime;

    public interface OnResultsListener {
        void onResults(List<String> list, float[] fArr);
    }

    static class C33131 implements OnResultsListener {
        C33131() {
        }

        public void onResults(List<String> results, float[] scores) {
            WazeSpeechRecognizer.internalResults(results, scores);
        }
    }

    public static void stop() {
        if (recognizer != null && inProgress) {
            Log.d(Logger.TAG, "Waze speech: Stop");
            recognizer.cancel();
        }
        reset();
    }

    private static void internalResults(List<String> results, float[] scores) {
        reset();
        if (results == null || results.size() == 0) {
            Log.d(Logger.TAG, "Waze speech: No results");
            AppService.getNativeManager().asrListenCallback(callback, new String[0], new float[0]);
            return;
        }
        int i = 0;
        for (String match : results) {
            if (scores != null) {
                int i2 = i + 1;
                Log.d(Logger.TAG, "Voice match: " + match + " score: " + scores[i]);
                i = i2;
            }
        }
        if (callback != -1) {
            AppService.getNativeManager().asrListenCallback(callback, results.toArray(), scores);
        }
    }

    public static void start(long aCallback, String lang) {
        callback = aCallback;
        start(lang, new C33131());
    }

    public static void start(String lang, final OnResultsListener onRes) {
        if (!inProgress) {
            Log.d(Logger.TAG, "Waze speech: Start");
            inProgress = true;
            Intent intent;
            if (VERSION.SDK_INT < 8) {
                intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
                intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
                if (AppService.getMainActivity() != null) {
                    AppService.getMainActivity().startActivityForResult(intent, 1234);
                    return;
                }
                return;
            }
            intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
            intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
            intent.putExtra("calling_package", "com.waze.dictate");
            intent.putExtra("android.speech.extra.LANGUAGE", lang);
            recognizer = SpeechRecognizer.createSpeechRecognizer(AppService.getAppContext());
            recognizer.setRecognitionListener(new RecognitionListener() {
                public void onResults(Bundle resultBundle) {
                    Log.d(Logger.TAG, "Waze speech: Results");
                    onRes.onResults(resultBundle.getStringArrayList("results_recognition"), resultBundle.getFloatArray("confidence_scores"));
                }

                public void onBeginningOfSpeech() {
                    Log.d(Logger.TAG, "Waze speech: Begin");
                }

                public void onBufferReceived(byte[] buffer) {
                }

                public void onEndOfSpeech() {
                    Log.d(Logger.TAG, "Waze speech: End");
                }

                public void onError(int error) {
                    Log.d(Logger.TAG, "Waze speech: Error " + error);
                    if (error == 2 || error == 1) {
                        if (AppService.getMainActivity() != null) {
                            AppService.getMainActivity().getLayoutMgr().displayVoiceError();
                        }
                    } else if (error != 7 || SystemClock.uptimeMillis() - WazeSpeechRecognizer.startListeningTime >= 500) {
                        onRes.onResults(null, null);
                    } else {
                        Log.d(Logger.TAG, "Waze speech: Ignoring error_no_match becuase the timeout was too short");
                        return;
                    }
                    WazeSpeechRecognizer.reset();
                }

                public void onEvent(int eventType, Bundle params) {
                }

                public void onPartialResults(Bundle resultBundle) {
                    Log.d(Logger.TAG, "Waze speech: Partial Results");
                    onRes.onResults(resultBundle.getStringArrayList("results_recognition"), resultBundle.getFloatArray("confidence_scores"));
                }

                public void onReadyForSpeech(Bundle params) {
                    Log.d(Logger.TAG, "Waze speech: Ready");
                    if (AppService.getMainActivity() != null) {
                        AppService.getMainActivity().getLayoutMgr().displayVoiceListening();
                    }
                }

                public void onRmsChanged(float rmsdB) {
                }
            });
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().getLayoutMgr().displayVoiceWait();
            }
            startListeningTime = SystemClock.uptimeMillis();
            recognizer.startListening(intent);
        }
    }

    private static void reset() {
        inProgress = false;
        if (recognizer != null) {
            try {
                recognizer.destroy();
            } catch (Exception e) {
            }
            recognizer = null;
        }
    }

    public static void simpleIntent(int requestCode) {
        try {
            Intent intent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
            intent.putExtra("android.speech.extra.LANGUAGE_MODEL", "free_form");
            AppService.getActiveActivity().startActivityForResult(intent, requestCode);
        } catch (Exception ex) {
            Logger.e("onSpeechRecognitionClick exception", ex);
            NativeManager nm = NativeManager.getInstance();
            MsgBox.openMessageBox(nm.getLanguageString(DisplayStrings.DS_SPEECH_ACTIVITY_NOT_FOUND_TITLE), nm.getLanguageString(DisplayStrings.DS_SPEECH_ACTIVITY_NOT_FOUND_CONTENT), false);
        }
    }
}
