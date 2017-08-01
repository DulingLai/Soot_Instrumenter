package com.waze;

import android.media.MediaRecorder;

public class SoundRecorder {
    private static final long MAX_FILE_SIZE_BYTES = 100000;
    private static final int SAMPLING_RATE = 16000;
    private static SoundRecorder mInstance = null;
    private MediaRecorder mMR = null;

    private static final class CompatabilityWrapper {
        private CompatabilityWrapper() throws  {
        }

        public static void setSamplingRate(MediaRecorder $r0, int $i0) throws  {
            $r0.setAudioSamplingRate($i0);
        }
    }

    private static class OnErrorListener implements android.media.MediaRecorder.OnErrorListener {
        private OnErrorListener() throws  {
        }

        public void onError(MediaRecorder aMR, int $i0, int $i1) throws  {
            Logger.m38e("Error: " + $i0 + " in recording process. Extra " + $i1);
        }
    }

    private static class OnInfoListener implements android.media.MediaRecorder.OnInfoListener {
        private OnInfoListener() throws  {
        }

        public void onInfo(MediaRecorder aMR, int $i0, int $i1) throws  {
            Logger.ii("Info: " + $i0 + " in recording process. Extra " + $i1);
        }
    }

    private native void InitSoundRecorderNTV() throws ;

    public static void Create() throws  {
        mInstance = new SoundRecorder();
    }

    public int Start(String $r1, int $i0) throws  {
        byte $b1 = (byte) 0;
        if (this.mMR != null) {
            Logger.m38e("The recorder is already initialized!!");
            return -1;
        }
        Logger.m36d("Recoridng file to: " + $r1);
        this.mMR = new MediaRecorder();
        this.mMR.setAudioSource(1);
        this.mMR.setOutputFormat(2);
        this.mMR.setAudioEncoder(1);
        this.mMR.setOutputFile($r1);
        this.mMR.setOnErrorListener(new OnErrorListener());
        this.mMR.setOnInfoListener(new OnInfoListener());
        int $i02 = (int) (((double) $i0) * 1.05d);
        this.mMR.setMaxDuration($i02);
        this.mMR.setMaxFileSize(MAX_FILE_SIZE_BYTES);
        try {
            this.mMR.prepare();
            this.mMR.start();
        } catch (Throwable $r2) {
            Logger.ee("Error starting media recorder", $r2);
            $b1 = (byte) -1;
        }
        return $b1;
    }

    public void Stop() throws  {
        try {
            Logger.m36d("Stop recording file");
            if (this.mMR != null) {
                this.mMR.stop();
                this.mMR.reset();
                this.mMR.release();
                this.mMR = null;
            }
        } catch (Exception $r1) {
            Logger.ee("Error stopping media recorder", $r1);
        }
    }

    private SoundRecorder() throws  {
        InitSoundRecorderNTV();
    }
}
