package com.waze.ifs.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.NativeManager;
import com.waze.NativeSoundManager;
import com.waze.NavBarManager;
import com.waze.mywaze.MyWazeData;
import com.waze.navigate.AddressItem;
import com.waze.navigate.AddressPreviewActivity;
import com.waze.navigate.PublicMacros;
import com.waze.settings.SettingsCustomPrompts;
import com.waze.settings.SettingsPowerSaving;
import com.waze.voice.AsrSpeechRecognizer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class ActivityBase extends Activity {
    private static Map<String, Class<?>> ACTIVITY_MAP = null;
    private static final long DIALOG_TIMEOUT = 1000;
    private static final String LOG_TAG = "WAZE_ActivityBase";
    public static final int MOOD_SET = 4;
    public static final int RESULT_CLOSE_PARENT = 3;
    private static ArrayList<ActivityBase> mActivitiesList = new ArrayList();
    private static boolean mIsScreenBacklightOn = true;
    private static boolean mIsScreenDimmed;
    private static long mLastTouchTime;
    protected Dialog dialog;
    private ArrayList<ActivityResultCallback> mActivityResultCallbacks = new ArrayList();
    private volatile boolean mAlive = false;
    protected final IncomingHandler mHandler = new IncomingHandler();
    protected volatile boolean mIsMainActivity = false;
    final Messenger mMessenger = new Messenger(this.mHandler);
    private List<Runnable> mRunOnResume;
    private volatile boolean mRunning = false;
    private volatile boolean mVisible = false;

    class C17031 implements Runnable {
        C17031() throws  {
        }

        public void run() throws  {
            NativeManager.getInstance().CloseProgressPopup();
        }
    }

    public class IncomingHandler extends Handler {
        public void handleMessage(Message $r1) throws  {
            if (!ActivityBase.this.myHandleMessage($r1)) {
                super.handleMessage($r1);
            }
        }
    }

    protected boolean isCloseable() throws  {
        return true;
    }

    protected boolean myHandleMessage(Message msg) throws  {
        return false;
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.mAlive = true;
        mActivitiesList.add(this);
        NativeSoundManager.setVolumeControlStreamSetting(this);
        if (VERSION.SDK_INT >= 21) {
            initApi21();
        }
        if (mIsScreenBacklightOn) {
            getWindow().addFlags(128);
        }
    }

    @TargetApi(21)
    private void initApi21() throws  {
        getWindow().addFlags(Integer.MIN_VALUE);
    }

    protected void onResume() throws  {
        super.onResume();
        AppService.setActiveActivity(this);
        this.mRunning = true;
        if (this.mRunOnResume != null) {
            for (Runnable run : this.mRunOnResume) {
                run.run();
            }
            this.mRunOnResume = null;
        }
    }

    protected void onPause() throws  {
        super.onPause();
        this.mRunning = false;
        if (isFinishing() && mActivitiesList.size() == 1) {
            ActivityLifetimeHandler.stopHandler();
        }
    }

    protected void onDestroy() throws  {
        super.onDestroy();
        this.mAlive = false;
        mActivitiesList.remove(this);
        removeDialogs();
        ActivityLifetimeHandler.destroyHandler();
    }

    protected void onStart() throws  {
        super.onStart();
        this.mVisible = true;
        if (getVisibleCount() == 1) {
            ActivityLifetimeHandler.startHandler(this);
        }
    }

    protected void onStop() throws  {
        super.onStop();
        this.mVisible = false;
        if (getVisibleCount() == 0) {
            ActivityLifetimeHandler.stopHandler();
        }
    }

    public static void setScreenState(boolean $z0) throws  {
        if (!$z0) {
            ActivityLifetimeHandler.screenOffHandler();
        }
    }

    public static void setGlobalOrientation(int $i0) throws  {
        for (int $i1 = 0; $i1 < mActivitiesList.size(); $i1++) {
            ((ActivityBase) mActivitiesList.get($i1)).setRequestedOrientation($i0);
        }
    }

    public static int getVisibleCount() throws  {
        int $i0 = 0;
        for (int $i1 = 0; $i1 < mActivitiesList.size(); $i1++) {
            if (((ActivityBase) mActivitiesList.get($i1)).isVisible()) {
                $i0++;
            }
        }
        return $i0;
    }

    public static int getRunningCount() throws  {
        int $i0 = 0;
        for (int $i1 = 0; $i1 < mActivitiesList.size(); $i1++) {
            if (((ActivityBase) mActivitiesList.get($i1)).isRunning()) {
                $i0++;
            }
        }
        return $i0;
    }

    public static int getCount() throws  {
        int $i0 = 0;
        for (int $i1 = 0; $i1 < mActivitiesList.size(); $i1++) {
            if (((ActivityBase) mActivitiesList.get($i1)) != null) {
                $i0++;
            }
        }
        return $i0;
    }

    public static void finishAll() throws  {
        for (int $i0 = 0; $i0 < mActivitiesList.size(); $i0++) {
            ActivityBase $r2 = (ActivityBase) mActivitiesList.get($i0);
            if ($r2 != null) {
                Log.d(LOG_TAG, "Finishing activity:  " + $r2.toString());
                $r2.finish();
            }
        }
    }

    public static void closeAllActivities() throws  {
        for (int $i0 = 0; $i0 < mActivitiesList.size(); $i0++) {
            ActivityBase $r2 = (ActivityBase) mActivitiesList.get($i0);
            if ($r2 == null || $r2.mIsMainActivity) {
                if ($r2 != null && $r2.mIsMainActivity) {
                    ((MainActivity) $r2).dismissMenus();
                }
            } else if ($r2.isCloseable()) {
                Log.d(LOG_TAG, "Finishing activity:  " + $r2.toString());
                $r2.finish();
            } else {
                Log.d(LOG_TAG, "Not finishing activity as non-closeable:  " + $r2.toString());
            }
        }
    }

    public boolean isRunning() throws  {
        return this.mRunning;
    }

    public boolean isVisible() throws  {
        return this.mVisible;
    }

    public boolean isAlive() throws  {
        return this.mAlive;
    }

    public void removeDialogs() throws  {
        if (this.dialog != null) {
            TextView $r3 = (TextView) this.dialog.findViewById(C1283R.id.confirmText);
            if ($r3 != null) {
                Log.d(LOG_TAG, "Removing dialog: " + this.dialog + ", Confirm: " + $r3.getText());
            } else {
                Log.d(LOG_TAG, "Removing dialog: " + this.dialog + ", nothing");
            }
            this.dialog.dismiss();
        }
        this.dialog = null;
    }

    public void setDialog(Dialog $r1) throws  {
        this.dialog = $r1;
    }

    public void startNativeOptionActivity(String $r1, String $r2, int $i0, int $i1, String $r3, String $r4, AddressItem $r5, String $r6, String $r7, String $r8, String $r9, String $r10, AddressItem $r11, String $r12) throws  {
        AddressItem addressItem = new AddressItem(Integer.valueOf($i1), Integer.valueOf($i0), $r1, null, $r2, null, null, null, Integer.valueOf(99), null, Integer.valueOf(6), $r3, $r4, null, $r12, $r6, $r7, $r8, $r9, $r10, null, null);
        if ($r5 != null) {
            addressItem.setMeetingId($r5.getMeetingId());
            addressItem.setCategory(Integer.valueOf(7));
            addressItem.setTitle($r5.getTitle());
            Intent intent = new Intent(this, AddressPreviewActivity.class);
            intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem);
            intent.putExtra(PublicMacros.CALENDAR_ADDRESS_ITEM, $r5);
            intent.putExtra(PublicMacros.ACTION_BUTTON, 1);
            intent.putExtra(PublicMacros.CLEAR_ADS_CONTEXT, true);
            startActivityForResult(intent, 1);
            return;
        }
        AddressItem $r13;
        if ($r11 != null) {
            $r13 = $r11;
            if ($r11.getCategory() == null) {
                $r11.setCategory(Integer.valueOf(99));
            }
        }
        intent = new Intent(this, AddressPreviewActivity.class);
        intent.putExtra(PublicMacros.ADDRESS_ITEM, $r13);
        intent.putExtra(PublicMacros.ACTION_BUTTON, 1);
        startActivityForResult(intent, MainActivity.ADDRESS_OPTIONS_CODE);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        Iterator $r3 = this.mActivityResultCallbacks.iterator();
        while ($r3.hasNext()) {
            ((ActivityResultCallback) $r3.next()).onActivitResult(this, $i0, $i1, $r1);
        }
        if ($i1 == 3) {
            setResult($i1);
            finish();
        }
    }

    public static void setScreenBacklightOn(boolean $z0) throws  {
        mIsScreenBacklightOn = $z0;
        for (int $i0 = 0; $i0 < mActivitiesList.size(); $i0++) {
            ActivityBase $r2 = (ActivityBase) mActivitiesList.get($i0);
            if ($r2.isAlive()) {
                if ($z0) {
                    $r2.getWindow().addFlags(128);
                } else {
                    $r2.getWindow().clearFlags(128);
                }
            }
        }
    }

    public void SetMyWazeData(MyWazeData Data) throws  {
    }

    public void IsSeeFof(boolean bIsSeeFof) throws  {
    }

    public void IsSeeCloseFriends(boolean bIsSeeCloseFriends) throws  {
    }

    public void addActivityResultCallback(ActivityResultCallback $r1) throws  {
        if ($r1 != null && !this.mActivityResultCallbacks.contains($r1)) {
            this.mActivityResultCallbacks.add($r1);
        }
    }

    public void removeActivityResultCallback(ActivityResultCallback $r1) throws  {
        if ($r1 != null && this.mActivityResultCallbacks.contains($r1)) {
            this.mActivityResultCallbacks.remove($r1);
        }
    }

    public static ActivityBase getSingleRunningActivity() throws  {
        int $i0 = 0;
        ActivityBase $r0 = null;
        for (int $i1 = 0; $i1 < mActivitiesList.size(); $i1++) {
            ActivityBase $r3 = (ActivityBase) mActivitiesList.get($i1);
            if ($r3.isRunning()) {
                $i0++;
                $r0 = $r3;
            }
        }
        return $i0 == 1 ? $r0 : null;
    }

    protected void sendMessageToMyself(int $i0, int $i1, int $i2, Object $r1, long $l3) throws  {
        Message $r2 = Message.obtain(this.mHandler, $i0, $i1, $i2, $r1);
        $r2.replyTo = this.mMessenger;
        this.mHandler.sendMessageDelayed($r2, $l3);
    }

    public void sendMessage(int $i0, int $i1, int $i2, Object $r1) throws  {
        this.mHandler.sendMessage(Message.obtain(null, $i0, $i1, $i2, $r1));
    }

    public static void sendMessageToActive(int $i0, int $i1, int $i2, Object $r0) throws  {
        ActivityBase $r1 = AppService.getActiveActivity();
        if ($r1 != null) {
            $r1.sendMessage($i0, $i1, $i2, $r0);
        }
    }

    public void postDelayed(Runnable $r1, long $l0) throws  {
        this.mHandler.postDelayed($r1, $l0);
    }

    public void post(Runnable $r1) throws  {
        if (isRunning()) {
            this.mHandler.post($r1);
            return;
        }
        if (this.mRunOnResume == null) {
            this.mRunOnResume = new ArrayList();
        }
        this.mRunOnResume.add($r1);
    }

    public void cancel(Runnable $r1) throws  {
        this.mHandler.removeCallbacks($r1);
    }

    protected boolean isPortrait() throws  {
        return getResources().getConfiguration().orientation == 1;
    }

    public void showPopup(String $r1, String $r2) throws  {
        NativeManager.getInstance().OpenProgressIconPopup($r1, $r2);
        postDelayed(new C17031(), DIALOG_TIMEOUT);
    }

    public void showPopup(String $r1, String $r2, int $i0) throws  {
        showPopup($r1, $r2, $i0, null);
    }

    public void showPopup(String $r1, String $r2, final int $i0, final Runnable $r3) throws  {
        NativeManager.getInstance().OpenProgressIconPopup($r1, $r2);
        postDelayed(new Runnable() {
            public void run() throws  {
                ActivityBase.this.setResult($i0);
                NativeManager.getInstance().CloseProgressPopup();
                if ($r3 != null) {
                    $r3.run();
                }
                ActivityBase.this.finish();
            }
        }, DIALOG_TIMEOUT);
    }

    public void onRequestPermissionsResult(int $i0, String[] $r1, int[] $r2) throws  {
        super.onRequestPermissionsResult($i0, $r1, $r2);
        if ($i0 == AsrSpeechRecognizer.REQUEST_CODE_RECORD_AUDIO_PERMISSION && $r2.length > 0 && $r2[0] == 0) {
            AsrSpeechRecognizer.getInstance().beginSpeechSession();
        }
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        mLastTouchTime = System.currentTimeMillis();
        if (mIsScreenDimmed) {
            NavBarManager.updatePowerSavingConditions();
        }
        return super.dispatchTouchEvent($r1);
    }

    public static long getTimeSinceLastTouch() throws  {
        if (mLastTouchTime == 0) {
            mLastTouchTime = System.currentTimeMillis();
        }
        return System.currentTimeMillis() - mLastTouchTime;
    }

    public static void setScreenIsDimmed(boolean $z0) throws  {
        mIsScreenDimmed = $z0;
    }

    private static void initActivityMap() throws  {
        ACTIVITY_MAP = new HashMap();
        ACTIVITY_MAP.put(SettingsCustomPrompts.ACTION_TAG, SettingsCustomPrompts.class);
        ACTIVITY_MAP.put(SettingsPowerSaving.ACTION_TAG, SettingsPowerSaving.class);
    }

    public static void startActivityForTag(String $r0) throws  {
        if (ACTIVITY_MAP == null) {
            initActivityMap();
        }
        if (ACTIVITY_MAP.containsKey($r0)) {
            ActivityBase $r3 = AppService.getActiveActivity();
            if ($r3 != null) {
                $r3.startActivity(new Intent($r3, (Class) ACTIVITY_MAP.get($r0)));
            }
        }
    }
}
