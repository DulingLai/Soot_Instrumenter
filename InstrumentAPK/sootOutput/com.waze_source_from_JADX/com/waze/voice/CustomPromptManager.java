package com.waze.voice;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.media.MediaRecorder;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import com.waze.AppService;
import com.waze.ConfigManager;
import com.waze.NativeSoundManager;
import com.waze.strings.DisplayStrings;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomPromptManager {
    public static final String CUSTOM_PROMPTS_FOLDER_NAME = "/custom_recordings/";
    public static final int PROMPT_IMPERIAL_ONLY = 2;
    public static final int PROMPT_METRIC_AND_IMPERIAL = 0;
    public static final int PROMPT_METRIC_ONLY = 1;
    private static CustomPromptManager _instance;
    private PromptDefinition[] mAllPrompts = new PromptDefinition[]{new PromptDefinition(this, "StartDrive1", DisplayStrings.DS_CUSTOM_PROMPT_START_1, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive2", DisplayStrings.DS_CUSTOM_PROMPT_START_2, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive3", DisplayStrings.DS_CUSTOM_PROMPT_START_3, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive4", DisplayStrings.DS_CUSTOM_PROMPT_START_4, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive5", DisplayStrings.DS_CUSTOM_PROMPT_START_5, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive6", DisplayStrings.DS_CUSTOM_PROMPT_START_6, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive7", DisplayStrings.DS_CUSTOM_PROMPT_START_7, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive8", DisplayStrings.DS_CUSTOM_PROMPT_START_8, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition(this, "StartDrive9", DisplayStrings.DS_CUSTOM_PROMPT_START_9, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_START, 6), new PromptDefinition("200", DisplayStrings.DS_CUSTOM_PROMPT_TENTH_MILE, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 2), new PromptDefinition("400", DisplayStrings.DS_CUSTOM_PROMPT_QUARTER_MILE, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 2), new PromptDefinition("800", DisplayStrings.DS_CUSTOM_PROMPT_HALF_MILE, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 2), new PromptDefinition("200meters", DisplayStrings.DS_CUSTOM_PROMPT_200_METERS, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 1), new PromptDefinition("400meters", DisplayStrings.DS_CUSTOM_PROMPT_400_METERS, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 1), new PromptDefinition("800meters", DisplayStrings.DS_CUSTOM_PROMPT_800_METERS, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 1), new PromptDefinition("1000meters", DisplayStrings.DS_CUSTOM_PROMPT_1000_METERS, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 1), new PromptDefinition("1500meters", DisplayStrings.DS_CUSTOM_PROMPT_1500_METERS, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_NUMBERS, 3, 1), new PromptDefinition(this, "KeepLeft", DisplayStrings.DS_CUSTOM_PROMPT_KEEP_LEFT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "KeepRight", DisplayStrings.DS_CUSTOM_PROMPT_KEEP_RIGHT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "TurnLeft", DisplayStrings.DS_CUSTOM_PROMPT_TURN_LEFT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "TurnRight", DisplayStrings.DS_CUSTOM_PROMPT_TURN_RIGHT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "ExitLeft", DisplayStrings.DS_CUSTOM_PROMPT_EXIT_LEFT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "ExitRight", DisplayStrings.DS_CUSTOM_PROMPT_EXIT_RIGHT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Straight", DisplayStrings.DS_CUSTOM_PROMPT_STRAIGHT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "uturn", DisplayStrings.DS_CUSTOM_PROMPT_U_TURN, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Roundabout", DisplayStrings.DS_CUSTOM_PROMPT_ROUNDABOUT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "First", DisplayStrings.DS_CUSTOM_PROMPT_FIRST, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Second", DisplayStrings.DS_CUSTOM_PROMPT_SECOND, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Third", DisplayStrings.DS_CUSTOM_PROMPT_THIRD, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Fourth", DisplayStrings.DS_CUSTOM_PROMPT_FOURTH, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Fifth", DisplayStrings.DS_CUSTOM_PROMPT_FIFTH, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Sixth", DisplayStrings.DS_CUSTOM_PROMPT_SIXTH, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Seventh", DisplayStrings.DS_CUSTOM_PROMPT_SEVENTH, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_INSTRUCTIONS, 3), new PromptDefinition(this, "Police", DisplayStrings.DS_CUSTOM_PROMPT_POLICE, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_OBJECTS, 6), new PromptDefinition(this, "ApproachAccident", DisplayStrings.DS_CUSTOM_PROMPT_ACCIDENT, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_OBJECTS, 6), new PromptDefinition(this, "ApproachHazard", DisplayStrings.DS_CUSTOM_PROMPT_HAZARD, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_OBJECTS, 6), new PromptDefinition(this, "ApproachTraffic", DisplayStrings.DS_CUSTOM_PROMPT_TRAFFIC, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_OBJECTS, 6), new PromptDefinition(this, "ApproachRedLightCam", DisplayStrings.DS_CUSTOM_PROMPT_RED_LIGHT_CAM, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_OBJECTS, 6), new PromptDefinition(this, "ApproachSpeedCam", DisplayStrings.DS_CUSTOM_PROMPT_SPEED_CAM, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_OBJECTS, 6), new PromptDefinition(this, "AndThen", DisplayStrings.DS_CUSTOM_PROMPT_AND_THEN, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_SPECIAL, 3), new PromptDefinition(this, "TickerPoints", DisplayStrings.DS_CUSTOM_PROMPT_RECALCULATE, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_SPECIAL, 5), new PromptDefinition(this, "Arrive", DisplayStrings.DS_CUSTOM_PROMPT_ARRIVE, DisplayStrings.DS_CUSTOM_PROMPTS_CATEGORY_SPECIAL, 6)};
    private List<String> mBlackListPrompts;
    private boolean mContinueRecording;
    private PromptDefinition mCurrentRecordingPrompt;
    private boolean mDeleteAfterRecord;
    private boolean mIsRecording;
    private MediaRecorder mMediaRecorder;
    private Map<String, String> mMetersMapping = new HashMap();
    private boolean mPreviewPlaying;
    private String mRecordPath;
    private long mRecordStartTime;
    private Thread mRecordingThread;

    public class PromptDefinition {
        private int mCategory;
        private int mDisplayText;
        private String mId;
        private int mMaxSeconds;
        private int mMode;

        public PromptDefinition(CustomPromptManager this$0, String id, int displayText, int category, int maxSeconds) {
            this(id, displayText, category, maxSeconds, 0);
        }

        public PromptDefinition(String id, int displayText, int category, int maxSeconds, int mode) {
            this.mId = id;
            this.mDisplayText = displayText;
            this.mCategory = category;
            this.mMaxSeconds = maxSeconds;
            this.mMode = mode;
        }

        public String getId() {
            return this.mId;
        }

        public String getDisplayText() {
            return DisplayStrings.displayString(this.mDisplayText);
        }

        public String getCategory() {
            return DisplayStrings.displayString(this.mCategory);
        }

        public int getMaxSeconds() {
            return this.mMaxSeconds;
        }

        public int getMode() {
            return this.mMode;
        }
    }

    public interface RecordListener {
        void onRecordComplete();

        void onRecordStart();
    }

    public static synchronized CustomPromptManager getInstance() {
        CustomPromptManager customPromptManager;
        synchronized (CustomPromptManager.class) {
            if (_instance == null) {
                _instance = new CustomPromptManager();
            }
            customPromptManager = _instance;
        }
        return customPromptManager;
    }

    private CustomPromptManager() {
        this.mMetersMapping.put("200.mp3", "200meters.mp3");
        this.mMetersMapping.put("400.mp3", "400meters.mp3");
        this.mMetersMapping.put("800.mp3", "800meters.mp3");
        this.mMetersMapping.put("1000.mp3", "1000meters.mp3");
        this.mMetersMapping.put("1500.mp3", "1500meters.mp3");
        this.mBlackListPrompts = new ArrayList();
        this.mBlackListPrompts.add("m.mp3");
        this.mBlackListPrompts.add("ft.mp3");
        this.mBlackListPrompts.add("within.mp3");
        this.mBlackListPrompts.add("Exit.mp3");
    }

    public void initialize(Context context) {
        if (this.mRecordPath == null) {
            this.mRecordPath = context.getFilesDir() + CUSTOM_PROMPTS_FOLDER_NAME;
        }
        File recordPath = new File(this.mRecordPath);
        if (!recordPath.exists() && ContextCompat.checkSelfPermission(AppService.getActiveActivity(), "android.permission.WRITE_EXTERNAL_STORAGE") == 0) {
            Log.i("GilTestCP", "Mkdrs success = " + recordPath.mkdirs());
        }
    }

    public PromptDefinition[] getPromptDefinitions() {
        return this.mAllPrompts;
    }

    public boolean isCurrentlyRecordingPrompt(PromptDefinition promptDefinition) {
        return this.mCurrentRecordingPrompt == promptDefinition;
    }

    public boolean isCurrentlyRecording() {
        return this.mIsRecording;
    }

    public void stopRecording(boolean deleteAfterStopped) {
        if (this.mIsRecording && this.mContinueRecording) {
            this.mContinueRecording = false;
            this.mDeleteAfterRecord = deleteAfterStopped;
        }
    }

    public void beginRecordingPrompt(PromptDefinition prompt, final RecordListener listener) {
        if (!this.mIsRecording) {
            Log.i("GilTestCP", "Starting to record: " + prompt.getId());
            String filePath = this.mRecordPath + prompt.getId() + "_temp.mp3";
            this.mCurrentRecordingPrompt = prompt;
            this.mDeleteAfterRecord = false;
            this.mIsRecording = true;
            this.mMediaRecorder = new MediaRecorder();
            this.mMediaRecorder.setAudioSource(1);
            this.mMediaRecorder.setOutputFormat(2);
            this.mMediaRecorder.setOutputFile(filePath);
            this.mMediaRecorder.setAudioEncoder(1);
            this.mMediaRecorder.setMaxDuration(prompt.getMaxSeconds() * 1000);
            try {
                this.mMediaRecorder.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.mMediaRecorder.start();
            if (listener != null) {
                listener.onRecordStart();
            }
            this.mRecordStartTime = System.currentTimeMillis();
            this.mRecordingThread = new Thread(new Runnable() {
                public void run() {
                    long currentTime = System.currentTimeMillis();
                    CustomPromptManager.this.mContinueRecording = true;
                    while (true) {
                        if (!CustomPromptManager.this.mContinueRecording && currentTime - CustomPromptManager.this.mRecordStartTime >= 300) {
                            break;
                        }
                        currentTime = System.currentTimeMillis();
                        if (currentTime - CustomPromptManager.this.mRecordStartTime > ((long) (CustomPromptManager.this.mCurrentRecordingPrompt.getMaxSeconds() * 1000))) {
                            CustomPromptManager.this.mContinueRecording = false;
                        }
                    }
                    Log.i("GilTestCP", "Finished recording");
                    CustomPromptManager.this.mMediaRecorder.stop();
                    CustomPromptManager.this.mMediaRecorder.release();
                    if (CustomPromptManager.this.mDeleteAfterRecord) {
                        CustomPromptManager.this.mDeleteAfterRecord = false;
                        CustomPromptManager.this.deletePrompt(CustomPromptManager.this.mCurrentRecordingPrompt.getId(), true);
                    }
                    CustomPromptManager.this.mCurrentRecordingPrompt = null;
                    CustomPromptManager.this.mIsRecording = false;
                    if (listener != null) {
                        listener.onRecordComplete();
                    }
                }
            });
            this.mRecordingThread.start();
        }
    }

    public void savePrompt(String promptId) {
        File tempFile = new File(this.mRecordPath + promptId + "_temp.mp3");
        if (tempFile.exists()) {
            Log.i("GilTestCP", "save file success = " + tempFile.renameTo(new File(this.mRecordPath + promptId + ".mp3")));
            return;
        }
        Log.i("GilTestCP", "temp file not found: " + promptId);
    }

    public boolean doesFileExist(String promptId, boolean temp) {
        return new File(getPromptPath(promptId, temp)).exists();
    }

    public String checkCustomFileName(String originalFileName) {
        if (!ConfigManager.getInstance().getConfigValueBool(414) || !ConfigManager.getInstance().getConfigValueBool(415)) {
            return originalFileName;
        }
        String unitSystemName = ConfigManager.getInstance().getConfigValueString(200);
        boolean isMetric = unitSystemName != null && unitSystemName.equals("metric");
        Log.i("GilTestCP", "Checking for file swap for " + originalFileName);
        String fileNameNoPath = new File(originalFileName).getName();
        if (isMetric && this.mMetersMapping.containsKey(fileNameNoPath)) {
            Log.i("GilTestCP", "Mapping filename: " + fileNameNoPath);
            fileNameNoPath = (String) this.mMetersMapping.get(fileNameNoPath);
        }
        if (this.mBlackListPrompts.contains(fileNameNoPath)) {
            return "";
        }
        Log.i("GilTestCP", "the file name is " + fileNameNoPath);
        File customFile = new File(this.mRecordPath + fileNameNoPath);
        if (customFile.exists()) {
            return customFile.getAbsolutePath();
        }
        return originalFileName;
    }

    public long getFileDuration(String promptName, boolean temp) {
        String filePath = getPromptPath(promptName, temp);
        if (!new File(filePath).exists()) {
            return 0;
        }
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        mmr.setDataSource(filePath);
        return (long) Integer.parseInt(mmr.extractMetadata(9));
    }

    public void deletePrompt(String promptName, boolean temp) {
        new File(getPromptPath(promptName, temp)).delete();
    }

    public void deleteAllPrompts() {
        File directory = new File(this.mRecordPath);
        String[] files = directory.list();
        if (files != null) {
            for (String filename : files) {
                new File(directory, filename).delete();
            }
        }
    }

    public boolean previewPrompt(String promptName, boolean temp) {
        return previewPrompt(promptName, temp, null);
    }

    public boolean previewPrompt(String promptName, boolean temp, final Runnable onEndCallback) {
        if (this.mPreviewPlaying) {
            return false;
        }
        this.mPreviewPlaying = true;
        NativeSoundManager.getInstance().PlayFile(getPromptPath(promptName, temp).getBytes(), 0, 0, true, new Runnable() {
            public void run() {
                CustomPromptManager.this.mPreviewPlaying = false;
                if (onEndCallback != null) {
                    onEndCallback.run();
                }
            }
        });
        return true;
    }

    private String getPromptPath(String promptName, boolean temp) {
        return this.mRecordPath + promptName + (temp ? "_temp" : "") + ".mp3";
    }
}
