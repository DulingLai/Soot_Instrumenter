package com.waze.voice;

import android.media.AudioRecord;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.waze.AppService;
import com.waze.NativeManager;
import com.waze.NativeSoundManager;
import com.waze.analytics.AnalyticsEvents;
import com.waze.map.CanvasFont;
import com.waze.view.text.AutoResizeTextView;

public class AsrSpeechRecognizer {
    private static final int AUDIO_CHANNEL = 16;
    private static final int AUDIO_FORMAT = 2;
    private static final float AUDIO_LEVEL_STEP = 0.01f;
    private static final int AUDIO_SAMPLE_RATE = 16000;
    private static final String JNI_AUDIO_FORMAT = "PCM";
    private static final int JNI_BITS_PER_CHANNEL = 16;
    private static final int JNI_TOTAL_CHANNELS = 1;
    public static final int MAX_AUDIO_LEVEL = 15;
    public static final int REQUEST_CODE_RECORD_AUDIO_PERMISSION = 5642234;
    private static AsrSpeechRecognizer _instance;
    private float mAudioLevelStep;
    private AudioRecord mAudioRecord;
    private int mAudioWaveLevel;
    private boolean mContinueRecording;
    private boolean mIsRecording;
    private AsrAudioEventListener mListener;
    private int mMinBufferSize;
    private boolean mPauseRecording;
    private Thread mRecordingThread;

    public interface AsrAudioEventListener {
        void onPauseListening();

        void onResumeListening();

        void onStopListening();
    }

    class C32931 implements Runnable {
        C32931() {
        }

        public void run() {
            AsrDialogView.showAsrDialog(AppService.getActiveActivity());
        }
    }

    class C32942 implements Runnable {
        C32942() {
        }

        public void run() {
            AsrDialogView.showAsrUnavailableDialog(AppService.getActiveActivity());
        }
    }

    class C32953 implements Runnable {
        C32953() {
        }

        public void run() {
            AsrSpeechRecognizer.this.mAudioRecord.startRecording();
            byte[] buffer = new byte[AsrSpeechRecognizer.this.mMinBufferSize];
            while (AsrSpeechRecognizer.this.mContinueRecording) {
                long startTime = System.currentTimeMillis();
                if (AsrSpeechRecognizer.this.mPauseRecording) {
                    AsrSpeechRecognizer.this.mAudioWaveLevel = 0;
                    AsrSpeechRecognizer.this.mAudioLevelStep = AsrSpeechRecognizer.AUDIO_LEVEL_STEP;
                } else {
                    if (AsrSpeechRecognizer.this.mAudioRecord != null) {
                        AsrSpeechRecognizer.this.mAudioRecord.read(buffer, 0, AsrSpeechRecognizer.this.mMinBufferSize);
                        NativeManager.getInstance().asrSendBuffer(buffer);
                    }
                    Log.i("GilTestAsr", "Buffer read and send time = " + (System.currentTimeMillis() - startTime));
                    AsrSpeechRecognizer.this.adjustAudioWaveLevel(buffer);
                }
                Log.i("GilTestAsr", "Total iteration time = " + (System.currentTimeMillis() - startTime));
            }
            AsrSpeechRecognizer.this.mAudioRecord.stop();
            AsrSpeechRecognizer.this.mAudioRecord.release();
            AsrSpeechRecognizer.this.mAudioRecord = null;
            AsrSpeechRecognizer.this.mIsRecording = false;
        }
    }

    class C32964 implements Runnable {
        C32964() {
        }

        public void run() {
            if (AsrSpeechRecognizer.this.mListener != null) {
                AsrSpeechRecognizer.this.mListener.onStopListening();
            }
        }
    }

    class C32975 implements Runnable {
        C32975() {
        }

        public void run() {
            if (AsrSpeechRecognizer.this.mListener != null) {
                AsrSpeechRecognizer.this.mListener.onPauseListening();
            }
        }
    }

    class C32986 implements Runnable {
        C32986() {
        }

        public void run() {
            if (AsrSpeechRecognizer.this.mListener != null) {
                AsrSpeechRecognizer.this.mListener.onResumeListening();
            }
        }
    }

    public static synchronized AsrSpeechRecognizer getInstance() {
        AsrSpeechRecognizer asrSpeechRecognizer;
        synchronized (AsrSpeechRecognizer.class) {
            if (_instance == null) {
                _instance = new AsrSpeechRecognizer();
            }
            asrSpeechRecognizer = _instance;
        }
        return asrSpeechRecognizer;
    }

    private AsrSpeechRecognizer() {
    }

    public void beginSpeechSession() {
        NativeSoundManager.getInstance().beginAsrSpeechSession();
    }

    public void endSpeechSession(String reason) {
        NativeSoundManager.getInstance().endAsrSpeechSession(reason);
    }

    public boolean startListening() {
        if (this.mIsRecording) {
            return false;
        }
        if (ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.RECORD_AUDIO") != 0) {
            ActivityCompat.requestPermissions(AppService.getActiveActivity(), new String[]{"android.permission.RECORD_AUDIO"}, REQUEST_CODE_RECORD_AUDIO_PERMISSION);
            endSpeechSession(AnalyticsEvents.f51x5f684e94);
            return false;
        }
        this.mMinBufferSize = AudioRecord.getMinBufferSize(AUDIO_SAMPLE_RATE, 16, 2);
        this.mAudioRecord = new AudioRecord(1, AUDIO_SAMPLE_RATE, 16, 2, this.mMinBufferSize);
        startRecording();
        if (this.mListener != null) {
            this.mListener.onStopListening();
            this.mListener = null;
        }
        AppService.getActiveActivity().runOnUiThread(new C32931());
        return true;
    }

    public void showUnavailableDialog() {
        AppService.getActiveActivity().runOnUiThread(new C32942());
    }

    public void setListener(AsrAudioEventListener listener) {
        this.mListener = listener;
    }

    private void startRecording() {
        if (!this.mIsRecording) {
            this.mIsRecording = true;
            this.mContinueRecording = true;
            this.mPauseRecording = false;
            this.mAudioLevelStep = AUDIO_LEVEL_STEP;
            this.mAudioWaveLevel = 0;
            this.mRecordingThread = new Thread(new C32953());
            this.mRecordingThread.start();
        }
    }

    private void adjustAudioWaveLevel(byte[] buffer) {
        float absValue = Math.abs(getMaxVolume(buffer) - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        this.mAudioWaveLevel = (int) (absValue / this.mAudioLevelStep);
        if (this.mAudioWaveLevel > 15) {
            this.mAudioLevelStep = absValue / AutoResizeTextView.MIN_TEXT_SIZE;
            this.mAudioWaveLevel = 15;
        }
    }

    private float getMaxVolume(byte[] byteArray) {
        int length = byteArray.length / 2;
        float result = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        for (int i = 0; i < length; i++) {
            float currentRatio = (((float) ((short) (((byteArray[(i * 2) + 1] & 255) << 8) | (byteArray[i * 2] & 255)))) - -32768.0f) / 65535.0f;
            if (Math.abs(currentRatio - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) > Math.abs(result - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR)) {
                result = currentRatio;
            }
        }
        return result;
    }

    public int getAudioWaveLevel() {
        return this.mAudioWaveLevel;
    }

    public String getAudioFormat() {
        return JNI_AUDIO_FORMAT;
    }

    public int getChannels() {
        return 1;
    }

    public int getSampleRate() {
        return AUDIO_SAMPLE_RATE;
    }

    public int getBitsPerChannel() {
        return 16;
    }

    public void stopListening() {
        if (this.mIsRecording) {
            this.mContinueRecording = false;
            AppService.getActiveActivity().runOnUiThread(new C32964());
        }
    }

    public void pauseListening() {
        if (this.mIsRecording) {
            this.mPauseRecording = true;
            AppService.getActiveActivity().runOnUiThread(new C32975());
        }
    }

    public void resumeListening() {
        if (this.mIsRecording) {
            this.mPauseRecording = false;
            AppService.getActiveActivity().runOnUiThread(new C32986());
        }
    }
}
