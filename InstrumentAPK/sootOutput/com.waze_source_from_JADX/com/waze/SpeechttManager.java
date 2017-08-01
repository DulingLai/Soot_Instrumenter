package com.waze;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import com.waze.SpeechttManagerBase.Callback;
import java.util.ArrayList;

public class SpeechttManager extends SpeechttManagerBase {
    private volatile boolean mExternalRecognizer = true;
    private volatile SpeechttListener mListener = null;

    class C13032 implements Runnable {
        C13032() throws  {
        }

        public void run() throws  {
            SpeechttManager.this.mListener.getRecognizer().stopListening();
        }
    }

    private final class SpeechttListener implements RecognitionListener {
        private final SpeechRecognizer mRecognizer;

        public SpeechttListener(SpeechRecognizer $r2) throws  {
            this.mRecognizer = $r2;
        }

        public void onEvent(final int $i0, Bundle params) throws  {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    Logger.m43w("Speech to text event: " + $i0);
                }
            });
        }

        public void onResults(Bundle $r1) throws  {
            SpeechttManager.this.ResultsHandler($r1.getStringArrayList("results_recognition"));
            this.mRecognizer.destroy();
        }

        public void onBeginningOfSpeech() throws  {
            Log.i(Logger.TAG, "Recognizer listener: starting speech");
        }

        public void onEndOfSpeech() throws  {
            Log.i(Logger.TAG, "Recognizer listener: end of speech");
        }

        public void onPartialResults(Bundle $r1) throws  {
            String $r2 = new String();
            ArrayList $r3 = $r1.getStringArrayList("results_recognition");
            for (int $i0 = 0; $i0 < $r3.size(); $i0++) {
                String $r6 = $r2 + ((String) $r3.get($i0));
                $r2 = $r6;
                if ($i0 < $r3.size() - 1) {
                    $r2 = $r6 + " ";
                }
            }
            Log.i(Logger.TAG, "Recognizer partial result: " + $r2);
        }

        public void onError(int $i0) throws  {
            Logger.m43w("Error (" + $i0 + ") is reported while recognition process");
            this.mRecognizer.destroy();
            SpeechttManager.this.Reset();
        }

        public void onReadyForSpeech(Bundle params) throws  {
        }

        public void onRmsChanged(float rmsdB) throws  {
        }

        public void onBufferReceived(byte[] buffer) throws  {
        }

        public SpeechRecognizer getRecognizer() throws  {
            return this.mRecognizer;
        }
    }

    public void Start(Callback $r1, long $l0, int $i1, byte[] $r2, byte[] $r3, int $i2) throws  {
        if (this.mBusy) {
            Logger.m43w("Cannot start speech recognition - the engine is busy");
            return;
        }
        this.mExternalRecognizer = ($i2 & 16) > 0;
        if (this.mExternalRecognizer) {
            super.Start($r1, $l0, $i1, $r2, $r3, $i2);
        } else {
            final Intent $r5 = Prepare($r2, $r3, $i2);
            $r5.putExtra("calling_package", AppService.getAppContext().getPackageName());
            AppService.Post(new Runnable() {
                public void run() throws  {
                    SpeechRecognizer $r4 = SpeechRecognizer.createSpeechRecognizer(AppService.getAppContext());
                    SpeechttManager.this.mListener = new SpeechttListener($r4);
                    $r4.setRecognitionListener(SpeechttManager.this.mListener);
                    $r4.startListening($r5);
                }
            });
        }
        this.mBusy = true;
    }

    public void Stop() throws  {
        if (this.mExternalRecognizer || this.mListener != null) {
            Log.i(Logger.TAG, "Stopping the recognition service");
            if (this.mExternalRecognizer) {
                super.Stop();
            } else {
                AppService.Post(new C13032());
            }
        }
    }

    public boolean IsAvailable() throws  {
        return SpeechRecognizer.isRecognitionAvailable(AppService.getAppContext());
    }

    protected void Reset() throws  {
        this.mBusy = false;
        this.mListener = null;
        this.mExternalRecognizer = true;
    }
}
