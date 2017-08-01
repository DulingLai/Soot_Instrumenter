package com.waze;

import android.content.Context;
import android.graphics.drawable.Drawable;
import java.util.Locale;

public class MoodManager {
    private static MoodManager instance;
    private Integer addon;
    private Boolean baby;
    private int exclusiveMoodLevel = -1;
    private NativeManager nativeManager = NativeManager.getInstance();
    private int newbieMilage = 0;
    private String newbieMilageUnit = "";
    private String wazerMood;

    private native void InitMoodManagerNTV() throws ;

    private native int getExclusiveMoodLevelNTV() throws ;

    private native int getMoodAddonNTV() throws ;

    private native String getMoodNTV() throws ;

    private native int getNewbieMilageNTV() throws ;

    private native String getNewbieMilageUnitNTV() throws ;

    private native boolean isBabyNTV() throws ;

    private native void setMoodNTV(String str) throws ;

    public static MoodManager getInstance() throws  {
        if (instance == null) {
            instance = new MoodManager();
        }
        return instance;
    }

    public String getWazerMood() throws  {
        if (this.wazerMood == null) {
            Logger.m43w("Get wazer mood before initializing");
            this.wazerMood = "wazer_baby";
        }
        return this.wazerMood;
    }

    public void refreshMood() throws  {
        String $r2 = getMoodNTV();
        boolean $z0 = isBabyNTV();
        int $i0 = getMoodAddonNTV();
        int $i1 = getExclusiveMoodLevelNTV();
        final String str = $r2;
        final boolean z = $z0;
        final int i = $i0;
        final int i2 = $i1;
        final int newbieMilageNTV = getNewbieMilageNTV();
        final String newbieMilageUnitNTV = getNewbieMilageUnitNTV();
        AppService.Post(new Runnable() {
            public void run() throws  {
                MoodManager.this.wazerMood = str;
                MoodManager.this.baby = Boolean.valueOf(z);
                MoodManager.this.addon = Integer.valueOf(i);
                MoodManager.this.exclusiveMoodLevel = i2;
                MoodManager.this.newbieMilage = newbieMilageNTV;
                MoodManager.this.newbieMilageUnit = newbieMilageUnitNTV;
                final MainActivity $r5 = AppService.getMainActivity();
                if ($r5 != null) {
                    $r5.post(new Runnable() {
                        public void run() throws  {
                            $r5.getLayoutMgr().updateUserData();
                        }
                    });
                }
            }
        });
    }

    public void setWazerMood(final String $r1) throws  {
        if (!this.wazerMood.equals($r1)) {
            this.wazerMood = $r1;
            this.nativeManager.PostRunnable(new Runnable() {
                public void run() throws  {
                    MoodManager.this.setMoodNTV($r1);
                }
            });
        }
    }

    public boolean canSetMood(Context $r1, String $r2) throws  {
        if (isBaby()) {
            return $r2.equals($r1.getResources().getString(C1283R.string.babyMood));
        }
        if (this.exclusiveMoodLevel < 0) {
            Logger.m43w("Check exclusive mode before initializing");
            this.exclusiveMoodLevel = 0;
        }
        if ("wazer_dino".equals($r2)) {
            if (this.exclusiveMoodLevel < 3) {
                return false;
            }
            return true;
        } else if ("wazer_8bit".equals($r2)) {
            if (this.exclusiveMoodLevel < 2) {
                return false;
            }
            return true;
        } else if (!"wazer_robot".equals($r2)) {
            boolean $z0 = ConfigManager.getInstance().getConfigValueBool(412);
            if ("Albert".equals($r2)) {
                if ($z0) {
                    return true;
                }
                return false;
            } else if ("BugBuster".equals($r2)) {
                return $z0;
            } else {
                return true;
            }
        } else if (this.exclusiveMoodLevel < 1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean isBaby() throws  {
        if (this.baby == null) {
            Logger.m43w("Get baby mode before initializing");
            this.baby = Boolean.valueOf(true);
        }
        return this.baby.booleanValue();
    }

    public String getNewbieMessage(int $i0) throws  {
        return String.format(this.nativeManager.getLanguageString($i0), new Object[]{Integer.valueOf(this.newbieMilage), this.newbieMilageUnit});
    }

    public Drawable getMoodDrawable(Context $r1) throws  {
        Drawable $r3 = getMoodDrawable($r1, getWazerMood());
        if ($r3 == null) {
            return getMoodDrawable($r1, "happy");
        }
        return $r3;
    }

    public static Drawable getMoodDrawable(Context context, String $r1) throws  {
        Drawable $r4 = ResManager.GetSkinDrawable("moods/" + $r1 + ResManager.mImageExtension);
        if ($r4 == null) {
            return ResManager.GetSkinDrawable($r1 + ResManager.mImageExtension);
        }
        return $r4;
    }

    public int getBigMoodDrawbleId() throws  {
        return ResManager.GetDrawableId(toAndroidNaming(getWazerMood()));
    }

    public static Drawable getBigMoodDrawble(Context $r0, String $r1) throws  {
        int $i0 = ResManager.GetDrawableId(toAndroidNaming($r1));
        if ($i0 > 0) {
            return $r0.getResources().getDrawable($i0);
        }
        return null;
    }

    public static int getBigMoodDrawableId(String $r0) throws  {
        return ResManager.GetDrawableId(toAndroidNaming($r0));
    }

    public int getBigAddonDrawble(Context $r1) throws  {
        int $i0 = 0;
        if (this.addon == null) {
            Logger.m43w("Get addon before initializing");
            return 0;
        }
        if (this.addon.intValue() == 13) {
            this.addon = Integer.valueOf(4);
        }
        if (this.addon.intValue() == 10) {
            this.addon = Integer.valueOf(6);
        }
        String[] $r4 = $r1.getResources().getStringArray(C1283R.array.addon_list);
        if (this.addon.intValue() >= 0 && this.addon.intValue() < $r4.length) {
            $i0 = ResManager.GetDrawableId($r4[this.addon.intValue()]);
        }
        return $i0;
    }

    private static String toAndroidNaming(String $r0) throws  {
        return $r0.replace("-", "_").toLowerCase(Locale.US);
    }

    public Drawable getMenuMoodDrawable(Context $r1) throws  {
        Drawable $r3 = getMoodDrawable($r1, getWazerMood());
        if ($r3 == null) {
            return getMoodDrawable($r1, "happy");
        }
        return $r3;
    }

    public int getMenuMoodDrawableByName(Context context, String $r2) throws  {
        if ($r2 != null) {
            return ResManager.GetDrawableId(toAndroidNaming($r2));
        }
        return C1283R.drawable.happy;
    }

    private MoodManager() throws  {
        InitMoodManagerNTV();
    }
}
