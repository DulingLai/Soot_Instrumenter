package com.waze;

import android.app.Activity;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.telephony.TelephonyManager;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.strings.DisplayStrings;
import com.waze.voice.AsrSpeechRecognizer;
import com.waze.voice.CustomPromptManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class NativeSoundManager {
    public static final int ASR_DICTATION_MODE_SEARCH_ON_MAP = 1;
    public static final int ASR_DICTATION_MODE_SIDE_MENU = 0;
    private static final String LOG_TAG = "Sound_Manager";
    public static final int ROUTING_STATE_SPEAKER = 1;
    public static final int ROUTING_STATE_SYSTEM = 0;
    private static final boolean SOUND_BUFFERING_ENABLED = false;
    public static final int VOLUME_MAX = 100;
    private static NativeSoundManager mInstance = null;
    private static boolean mRouteToSpeaker = false;
    final OnAudioFocusChangeListener mAfChangeListener = new C12362();
    private boolean mAfGained = false;
    private AudioManager mAudioManager = null;
    private String mBTDeviceName = null;
    private WazeAudioPlayer mBufferedPlayer = null;
    private Map<String, Boolean> mBypassMuteMap;
    private int mCurrentMPVolume = 100;
    private WazeAudioPlayer mCurrentPlayer = null;
    private String mDefaultDeviceName = null;
    private volatile boolean mIsMuted = false;
    private Map<String, Runnable> mJavaCallbackMap;
    private ArrayList<Long> mPendingCallbackContextList;
    private ArrayList<Long> mPendingCallbackList;
    private ArrayList<String> mPendingPlayersList;
    private volatile boolean mPlaying = false;
    private int mRoutingState = 0;
    private String mSpeakerDeviceName = null;
    private int mSystemMode = 0;
    private volatile boolean mSystemSpeakerState = false;

    class C12362 implements OnAudioFocusChangeListener {
        C12362() throws  {
        }

        public void onAudioFocusChange(int $i0) throws  {
            if (($i0 & -1) > 0) {
                Logger.d_(NativeSoundManager.LOG_TAG, "Audio focus is lost. State: " + $i0);
            } else if (($i0 & 1) > 0) {
                Logger.d_(NativeSoundManager.LOG_TAG, "Audio focus is gained. State: " + $i0);
            } else {
                Logger.d_(NativeSoundManager.LOG_TAG, "Audio focus unknown state: " + $i0);
            }
        }
    }

    class C12373 implements Runnable {
        C12373() throws  {
        }

        public void run() throws  {
            NativeSoundManager.this.beginAsrSpeechSessionNTV();
        }
    }

    private final class WazeAudioPlayer extends Thread {
        private boolean mBuffered = false;
        private boolean mBuffering = false;
        private Long mCallback;
        private Long mCallbackContext;
        private String mFileName;
        private Runnable mJavaCallback;
        private MediaPlayer mMP = null;

        class C12401 implements Runnable {
            C12401() throws  {
            }

            public void run() throws  {
                NativeSoundManager.this.PlayNext();
            }
        }

        class C12412 implements Runnable {
            C12412() throws  {
            }

            public void run() throws  {
                NativeSoundManager.this.SoundCallbackNTV(WazeAudioPlayer.this.mCallback.longValue(), WazeAudioPlayer.this.mCallbackContext.longValue());
            }
        }

        private final class CompletionListener implements OnCompletionListener {
            private CompletionListener() throws  {
            }

            public void onCompletion(MediaPlayer $r1) throws  {
                WazeAudioPlayer.this.finalizePlay($r1);
            }
        }

        private final class ErrorListener implements OnErrorListener {
            private ErrorListener() throws  {
            }

            public boolean onError(MediaPlayer $r1, int what, int extra) throws  {
                WazeAudioPlayer.this.finalizePlay($r1);
                return true;
            }
        }

        WazeAudioPlayer(String $r2, Long $r3, Long $r4) throws  {
            this.mFileName = CustomPromptManager.getInstance().checkCustomFileName($r2);
            if (NativeSoundManager.this.mJavaCallbackMap.containsKey($r2)) {
                this.mJavaCallback = (Runnable) NativeSoundManager.this.mJavaCallbackMap.remove($r2);
            }
            this.mCallback = $r3;
            this.mCallbackContext = $r4;
        }

        public void Play() throws  {
            if (!this.mBuffering || this.mBuffered) {
                NativeSoundManager.this.mPlaying = true;
                if (this.mBuffered) {
                    try {
                        synchronized (this) {
                            notify();
                        }
                        return;
                    } catch (Exception $r1) {
                        Logger.m39e("Audio Player. Error notifying the thread", $r1);
                        return;
                    }
                }
                start();
            }
        }

        public void Buffer() throws  {
            this.mBuffering = true;
            start();
        }

        public void run() throws  {
            try {
                if (!this.mBuffered) {
                    BufferInternal();
                }
                if (this.mBuffering) {
                    synchronized (this) {
                        wait();
                    }
                }
                Logger.d_("NativeSoundManager_Thread", "About to start media player: " + this.mFileName);
                this.mMP.start();
            } catch (Exception $r1) {
                Logger.ee("Audio Player. Error playing the file " + this.mFileName, $r1);
                $r1.printStackTrace();
                if (this.mMP != null) {
                    finalizePlay(this.mMP);
                }
            }
        }

        public String getFileName() throws  {
            return this.mFileName;
        }

        private void BufferInternal() throws IOException {
            this.mMP = new MediaPlayer();
            this.mMP.setOnCompletionListener(new CompletionListener());
            this.mMP.setOnErrorListener(new ErrorListener());
            if (NativeSoundManager.mRouteToSpeaker) {
                this.mMP.setAudioStreamType(0);
            } else {
                this.mMP.setAudioStreamType(3);
            }
            this.mMP.setDataSource(new FileInputStream(this.mFileName).getFD());
            float $f0 = NativeSoundManager.this.getExpVolumeValue();
            Logger.d_(NativeSoundManager.LOG_TAG, "Setting volume: " + $f0);
            if (NativeSoundManager.this.mIsMuted) {
                this.mMP.setVolume(0.0f, 0.0f);
            } else {
                this.mMP.setVolume($f0, $f0);
            }
            this.mMP.prepare();
            this.mBuffered = true;
            Logger.d_("NativeSoundManager_Thread", "Buffer Internal Finished: " + this.mFileName);
        }

        private void finalizePlay(MediaPlayer $r1) throws  {
            $r1.release();
            this.mMP = null;
            NativeSoundManager.this.mPlaying = false;
            NativeSoundManager.this.mCurrentPlayer = null;
            NativeManager.Post(new C12401());
            if (!(this.mCallback == null || this.mCallback.longValue() == 0)) {
                NativeManager.Post(new C12412());
            }
            if (this.mJavaCallback != null) {
                this.mJavaCallback.run();
            }
            Logger.d_("NativeSoundManager_Thread", "Finalize called. About to interrupt: " + this.mFileName);
            interrupt();
        }
    }

    private native void InitSoundManagerNTV() throws ;

    private native void SoundCallbackNTV(long j, long j2) throws ;

    private native void beginAsrDictationSessionNTV(int i) throws ;

    private native void beginAsrSpeechSessionNTV() throws ;

    private native void endAsrSpeechSessionNTV(String str) throws ;

    private native boolean shouldMuteNTV() throws ;

    public static void create() throws  {
        getInstance();
        mInstance.requestConfig();
        mInstance.saveSystemState();
        mInstance.mAudioManager = (AudioManager) AppService.getAppContext().getSystemService("audio");
    }

    public static NativeSoundManager getInstance() throws  {
        if (mInstance == null) {
            mInstance = new NativeSoundManager();
        }
        return mInstance;
    }

    private NativeSoundManager() throws  {
        InitSoundManagerNTV();
        this.mPendingPlayersList = new ArrayList();
        this.mPendingCallbackList = new ArrayList();
        this.mPendingCallbackContextList = new ArrayList();
        this.mBypassMuteMap = new HashMap();
        this.mJavaCallbackMap = new HashMap();
    }

    public void updateConfigItems() throws  {
        routeSoundToSpeaker(ConfigManager.getInstance().getConfigValueBool(157));
        setVolume(ConfigManager.getInstance().getConfigValueInt(158));
    }

    private void requestConfig() throws  {
        C12351 $r1 = new RunnableExecutor(AppService.getInstance()) {
            public void event() throws  {
                NativeManager $r1 = NativeManager.getInstance();
                NativeSoundManager.this.mSpeakerDeviceName = $r1.getLanguageString((int) DisplayStrings.DS_SOUND_DEVICE_SPEAKER);
                NativeSoundManager.this.mBTDeviceName = $r1.getLanguageString((int) DisplayStrings.DS_SOUND_DEVICE_BT);
                NativeSoundManager.this.mDefaultDeviceName = $r1.getLanguageString((int) DisplayStrings.DS_SOUND_DEVICE_DEFAULT);
                NativeSoundManager.this.updateConfigItems();
            }
        };
        if (NativeManager.IsAppStarted()) {
            $r1.run();
        } else {
            NativeManager.registerOnAppStartedEvent($r1);
        }
    }

    public void setVolume(int $i0) throws  {
        Logger.d_(LOG_TAG, "Setting media player volume to: " + $i0);
        this.mCurrentMPVolume = $i0;
    }

    public void setDevice(String $r1) throws  {
        Logger.d_(LOG_TAG, "Trying to set sound device: " + $r1);
        if ($r1.equals(this.mSpeakerDeviceName)) {
            routeSoundToSpeaker(true);
        } else if ($r1.equals(this.mDefaultDeviceName)) {
            routeSoundToSpeaker(false);
        } else {
            Logger.ee("Unrecognized sound device value: " + $r1);
        }
    }

    public String getSpeakerDeviceName() throws  {
        return this.mSpeakerDeviceName;
    }

    public String getBTDeviceName() throws  {
        return this.mBTDeviceName;
    }

    public String getDefaultDeviceName() throws  {
        return this.mDefaultDeviceName;
    }

    private void saveSystemState() throws  {
        AudioManager $r1 = getAudioManager();
        this.mSystemMode = $r1.getMode();
        this.mSystemSpeakerState = $r1.isSpeakerphoneOn();
    }

    private void setRoutingSystemState() throws  {
        if (this.mRoutingState != 0) {
            AudioManager $r1 = getAudioManager();
            $r1.setMode(this.mSystemMode);
            $r1.setSpeakerphoneOn(false);
            this.mRoutingState = 0;
        }
    }

    private void setRoutingSpeakerState() throws  {
        if (this.mRoutingState != 1) {
            getAudioManager().setSpeakerphoneOn(true);
            this.mRoutingState = 1;
        }
    }

    private float getExpVolumeValue() throws  {
        return (float) ((Math.pow(10.0d, ((double) this.mCurrentMPVolume) / 100.0d) - FriendsBarFragment.END_LOCATION_POSITION) / (Math.pow(10.0d, FriendsBarFragment.END_LOCATION_POSITION) - FriendsBarFragment.END_LOCATION_POSITION));
    }

    public void shutdown() throws  {
        Logger.d_(LOG_TAG, "Shutting down sound manager. Restoring startup state.");
        setRoutingSystemState();
    }

    public void routeSoundToSpeaker(boolean $z0) throws  {
        if (mRouteToSpeaker != $z0) {
            mRouteToSpeaker = $z0;
            setVolumeControlStreamSetting(AppService.getActiveActivity());
        }
    }

    public static void setVolumeControlStreamSetting(Activity $r0) throws  {
        if ($r0 != null) {
            $r0.setVolumeControlStream(mRouteToSpeaker ? (byte) 0 : (byte) 3);
        }
    }

    public void routeSoundToBT(boolean $z0) throws  {
        AudioManager $r3 = (AudioManager) AppService.getAppContext().getSystemService("audio");
        if ($z0) {
            $r3.setMode(0);
            $r3.setBluetoothA2dpOn(true);
            if (!$r3.isBluetoothA2dpOn()) {
                $r3.setBluetoothScoOn(true);
                return;
            }
            return;
        }
        $r3.setBluetoothA2dpOn(false);
        $r3.setBluetoothScoOn(false);
    }

    public void LoadSoundData(byte[] aDataDir) throws  {
    }

    public void PlayFile(byte[] $r1, long $l0, long $l1, boolean $z0) throws  {
        PlayFile($r1, $l0, $l1, $z0, null);
    }

    public void PlayFile(byte[] $r1, long $l0, long $l1, boolean $z0, Runnable $r2) throws  {
        String $r3 = new String($r1, 0, $r1.length);
        File $r4 = new File($r3);
        this.mPendingPlayersList.add($r3);
        this.mPendingCallbackList.add(Long.valueOf($l0));
        this.mPendingCallbackContextList.add(Long.valueOf($l1));
        if ($z0) {
            this.mBypassMuteMap.put($r3, Boolean.TRUE);
        }
        if ($r2 != null) {
            this.mJavaCallbackMap.put($r3, $r2);
        }
        PlayNext();
    }

    public void PlayBuffer(byte[] $r1) throws  {
        try {
            File $r4 = File.createTempFile(ResManager.mSoundDir, null, new File(ResManager.mAppDir + ResManager.mSoundDir));
            FileOutputStream $r3 = new FileOutputStream($r4);
            $r3.write($r1);
            $r3.close();
            this.mPendingPlayersList.add($r4.getAbsolutePath());
            PlayNext();
        } catch (Exception $r2) {
            Logger.ee("Error playing sound buffer", $r2);
            $r2.printStackTrace();
        }
    }

    private AudioManager getAudioManager() throws  {
        return (AudioManager) AppService.getAppContext().getSystemService("audio");
    }

    private boolean isInCall() throws  {
        TelephonyManager $r3 = (TelephonyManager) AppService.getAppContext().getSystemService("phone");
        Logger.d_(LOG_TAG, "Current call state: " + $r3.getCallState());
        return $r3.getCallState() == 2;
    }

    public boolean IsInCall() throws  {
        return isInCall();
    }

    private void setRouting() throws  {
        if (isInCall()) {
            setRoutingSystemState();
        } else if (mRouteToSpeaker) {
            setRoutingSpeakerState();
        } else {
            setRoutingSystemState();
        }
    }

    private synchronized void PlayNext() throws  {
        this.mIsMuted = shouldMuteNTV();
        if (this.mPlaying) {
            BufferNext();
        } else {
            String $r4;
            if (this.mBufferedPlayer != null) {
                $r4 = this.mCurrentPlayer.getFileName();
                if (this.mBypassMuteMap.containsKey($r4)) {
                    this.mBypassMuteMap.remove($r4);
                    this.mIsMuted = false;
                }
                requestAf();
                this.mCurrentPlayer = this.mBufferedPlayer;
                this.mCurrentPlayer.Play();
                this.mBufferedPlayer = null;
            } else if (this.mPendingPlayersList.size() > 0) {
                $r4 = (String) this.mPendingPlayersList.remove(0);
                if (this.mBypassMuteMap.containsKey($r4)) {
                    this.mBypassMuteMap.remove($r4);
                    this.mIsMuted = false;
                }
                this.mCurrentPlayer = new WazeAudioPlayer($r4, (Long) this.mPendingCallbackList.remove(0), (Long) this.mPendingCallbackContextList.remove(0));
                requestAf();
                this.mCurrentPlayer.Play();
            } else {
                abandonAf();
            }
            BufferNext();
        }
    }

    private void BufferNext() throws  {
    }

    private boolean requestAf() throws  {
        if (!(this.mAfGained || this.mIsMuted)) {
            int $i0;
            if (mRouteToSpeaker) {
                $i0 = this.mAudioManager.requestAudioFocus(this.mAfChangeListener, 0, 3);
            } else {
                $i0 = this.mAudioManager.requestAudioFocus(this.mAfChangeListener, 3, 3);
            }
            if ($i0 == 1) {
                Logger.d_(LOG_TAG, "Audio focus is granted");
                this.mAfGained = true;
            } else {
                Logger.w_(LOG_TAG, "Problem gaining the audio focus. Result: " + $i0);
                this.mAfGained = false;
            }
            setRouting();
        }
        return this.mAfGained;
    }

    private void abandonAf() throws  {
        if (this.mAfGained) {
            Logger.d_(LOG_TAG, "Audio focus is abandoned with state: " + this.mAudioManager.abandonAudioFocus(this.mAfChangeListener));
            setRoutingSystemState();
        }
        this.mAfGained = false;
    }

    public String GrpcGetSoundFormat() throws  {
        return AsrSpeechRecognizer.getInstance().getAudioFormat();
    }

    public int GrpcGetChannels() throws  {
        return AsrSpeechRecognizer.getInstance().getChannels();
    }

    public int GrpcGetBitsPerChannel() throws  {
        return AsrSpeechRecognizer.getInstance().getBitsPerChannel();
    }

    public int GrpcGetSampleRate() throws  {
        return AsrSpeechRecognizer.getInstance().getSampleRate();
    }

    public void beginAsrSpeechSession() throws  {
        NativeManager.Post(new C12373());
    }

    public void endAsrSpeechSession(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NativeSoundManager.this.endAsrSpeechSessionNTV($r1);
            }
        });
    }

    public void beginAsrDictationSession(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NativeSoundManager.this.beginAsrDictationSessionNTV($i0);
            }
        });
    }

    public boolean isAsrV2Enabled() throws  {
        Logger.d_(LOG_TAG, "isAsrV2Enabled: Checking if ASR (v2) is enabled.");
        if (ConfigManager.getInstance().getConfigValueBool(186)) {
            Logger.d_(LOG_TAG, "isAsrV2Enabled: The '##@asrv2' tech code is set - ASR (v2) is enabled.");
            return true;
        } else if (ConfigManager.getInstance().getConfigValueBool(185)) {
            Logger.d_(LOG_TAG, "isAsrV2Enabled: The '##@asrv1' tech code is set - ASR (v2) is NOT enabled.");
            return false;
        } else if (ConfigManager.getInstance().getConfigValueBool(176)) {
            String $r2 = ConfigManager.getInstance().getConfigValueString(172);
            Logger.d_(LOG_TAG, "isAsrV2Enabled: ASR type: " + $r2 + FileUploadSession.SEPARATOR);
            return $r2.equals("v2");
        } else {
            Logger.d_(LOG_TAG, "isAsrV2Enabled: The ASR feature isn't set - ASR (v2) is NOT enabled.");
            return false;
        }
    }

    public void showUnavailableAsrDialog() throws  {
        AsrSpeechRecognizer.getInstance().showUnavailableDialog();
    }
}
