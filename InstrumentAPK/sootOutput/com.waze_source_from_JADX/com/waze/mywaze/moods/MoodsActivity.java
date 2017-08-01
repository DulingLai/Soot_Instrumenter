package com.waze.mywaze.moods;

import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.MoodManager;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.settings.SettingValueAdapter;
import com.waze.settings.SettingsValue;
import com.waze.strings.DisplayStrings;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;
import java.util.ArrayList;

public class MoodsActivity extends ActivityBase {
    private MoodManager moodManager = MoodManager.getInstance();
    private NativeManager nativeManager = AppService.getNativeManager();

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.moods);
        ((TitleBar) findViewById(C1283R.id.moodsTitle)).init(this, DisplayStrings.DS_MY_MOOD);
        final MoodItem[] $r6 = getItems();
        final SettingValueAdapter $r2 = r16;
        SettingValueAdapter r16 = new SettingValueAdapter(this);
        SettingsValue[] $r3 = new SettingsValue[$r6.length];
        String $r8 = this.moodManager.getWazerMood();
        for (int $i0 = 0; $i0 < $r6.length; $i0++) {
            boolean $z0 = false;
            if ($r6[$i0].mood != null) {
                String $r10 = $r6[$i0].mood;
                $z0 = $r10.equals($r8);
            }
            $r3[$i0] = new SettingsValue($r6[$i0].mood, $r6[$i0].caption, $z0);
            SettingsValue $r11 = $r3[$i0];
            Drawable drawable = $r6[$i0].image;
            Drawable $r13 = drawable;
            $r11.icon = drawable;
            $r3[$i0].isHeader = $r6[$i0].title;
        }
        $r2.setValues($r3);
        ListView $r14 = (ListView) findViewById(C1283R.id.moodList);
        $r14.setAdapter($r2);
        $r14.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View $r2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
                MoodItem $r3 = $r6[$i0];
                if ($r3.enabled) {
                    MoodsActivity.this.moodManager.setWazerMood($r3.mood);
                    $r2.setSelected($r2, $i0);
                    MoodsActivity.this.setResult(4);
                    MoodsActivity.this.finish();
                }
            }
        });
        if (this.moodManager.isBaby()) {
            showBabyMessage();
        }
        if (VERSION.SDK_INT >= 21) {
            AnimationUtils.api21RippleInitList($r14);
        }
    }

    private MoodItem[] getItems() throws  {
        ArrayList $r1 = new ArrayList();
        if (this.moodManager.isBaby()) {
            $r1.add(new MoodItem(null, this.nativeManager.getLanguageString((int) DisplayStrings.DS_WAZE_NEWBIE) + " " + this.moodManager.getNewbieMessage(78), null, true, false, false, false));
            $r1.add(new MoodItem(getResources().getString(C1283R.string.babyMood), getLocalizedName(getResources().getString(C1283R.string.babyMoodCaption)), MoodManager.getBigMoodDrawble(this, getResources().getString(C1283R.string.babyMood)), false, true, true, true));
        }
        $r1.add(new MoodItem(null, this.nativeManager.getLanguageString(76), null, true, false, false, false));
        String[] $r11 = getResources().getStringArray(C1283R.array.mood_list_exclusive);
        int $i0 = 0;
        while ($i0 < $r11.length) {
            $r1.add(new MoodItem($r11[$i0], getLocalizedName($r11[$i0]), MoodManager.getBigMoodDrawble(this, $r11[$i0]), false, this.moodManager.canSetMood(this, $r11[$i0]), $i0 == 0, $i0 == $r11.length + -1));
            $i0++;
        }
        if (ConfigManager.getInstance().getConfigValueBool(412)) {
            $r1.add(new MoodItem(null, this.nativeManager.getLanguageString(77), null, true, false, false, false));
            $r11 = getResources().getStringArray(C1283R.array.mood_list_beta);
            $i0 = 0;
            while ($i0 < $r11.length) {
                $r1.add(new MoodItem($r11[$i0], getLocalizedName($r11[$i0]), MoodManager.getBigMoodDrawble(this, $r11[$i0]), false, this.moodManager.canSetMood(this, $r11[$i0]), $i0 == 0, $i0 == $r11.length + -1));
                $i0++;
            }
        }
        $r1.add(new MoodItem(null, this.nativeManager.getLanguageString(75) + " " + this.nativeManager.getLanguageString((int) DisplayStrings.DS_OAVAILABLE_TO_ALLU), null, true, false, false, false));
        $r11 = getResources().getStringArray(C1283R.array.mood_list);
        $i0 = 0;
        while ($i0 < $r11.length) {
            $r1.add(new MoodItem($r11[$i0], getLocalizedName($r11[$i0]), MoodManager.getBigMoodDrawble(this, $r11[$i0]), false, this.moodManager.canSetMood(this, $r11[$i0]), $i0 == 0, $i0 == $r11.length + -1));
            $i0++;
        }
        MoodItem[] $r13 = new MoodItem[0];
        return (MoodItem[]) $r1.toArray($r13);
    }

    private void showBabyMessage() throws  {
        MsgBox.openMessageBox(this.nativeManager.getLanguageString(79), this.moodManager.getNewbieMessage(80), false);
    }

    private String getLocalizedName(String $r1) throws  {
        $r1 = this.nativeManager.getLanguageString($r1);
        if ($r1 == null || !$r1.endsWith(FileUploadSession.SEPARATOR)) {
            return $r1;
        }
        return $r1.substring(0, $r1.length() - 1);
    }
}
