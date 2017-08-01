package com.waze.ifs.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.waze.C1283R;
import com.waze.ifs.ui.OmniSelectionFragment.IOmniSelect;
import com.waze.settings.SettingsValue;
import java.util.Random;

public class OmniSelectionActivity extends ActivityBase implements IOmniSelect {
    public static final String ARG_EDIT_BOX_HINT = (TAG + ".arg.hint");
    public static final String ARG_EXPANDABLE = (TAG + ".arg.expandable");
    public static final String ARG_FILTER = (TAG + ".arg.filter");
    public static final String ARG_FWD_INTENT = (TAG + ".arg.fwd_intent");
    public static final String ARG_INDEXED = (TAG + ".arg.indexed");
    public static final String ARG_MULTI_SELECT = (TAG + ".arg.multi_select");
    public static final String ARG_SEARCH = (TAG + ".arg.search");
    public static final String ARG_SUBTITLE = (TAG + ".arg.subtitle");
    public static final String ARG_TITLE = (TAG + ".arg.title");
    public static final String ARG_USER_INPUT = (TAG + ".arg.user_input");
    public static final String ARG_USER_INPUT_FORMAT = (TAG + ".arg.user_input_format");
    public static final String ARG_VALUES = (TAG + ".arg.values");
    public static final String RET_SELECTED_IDX = (TAG + ".ret.selected_idx");
    public static final String RET_SELECTED_TITLE = (TAG + ".ret.selected_title");
    public static final String RET_SELECTED_USER_INPUT = (TAG + ".ret.selected_user_input");
    public static final String RET_SELECTED_VAL = (TAG + ".ret.selected_val");
    private static final String TAG = OmniSelectionActivity.class.getName();
    private Intent mFwdIntent;

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.fragment_activity_empty);
        OmniSelectionFragment $r2 = new OmniSelectionFragment();
        Intent $r4 = getIntent();
        $r2.setTitle($r4.getStringExtra(ARG_TITLE));
        $r2.setEditBoxHint($r4.getStringExtra(ARG_EDIT_BOX_HINT));
        Parcelable[] $r6 = $r4.getParcelableArrayExtra(ARG_VALUES);
        SettingsValue[] $r3 = new SettingsValue[$r6.length];
        for (int $i0 = 0; $i0 < $r3.length; $i0++) {
            $r3[$i0] = (SettingsValue) $r6[$i0];
        }
        $r2.setValues($r3);
        $r2.setIndexed($r4.getBooleanExtra(ARG_INDEXED, false));
        $r2.setSearch($r4.getBooleanExtra(ARG_SEARCH, false));
        $r2.setFilter($r4.getBooleanExtra(ARG_FILTER, false));
        $r2.setMultiSelect($r4.getBooleanExtra(ARG_MULTI_SELECT, false));
        $r2.setUserInput($r4.getBooleanExtra(ARG_USER_INPUT, false), $r4.getStringExtra(ARG_USER_INPUT_FORMAT));
        $r2.setExpandable($r4.getBooleanExtra(ARG_EXPANDABLE, false));
        this.mFwdIntent = (Intent) $r4.getParcelableExtra(ARG_FWD_INTENT);
        getFragmentManager().beginTransaction().add(C1283R.id.container, $r2).commit();
    }

    public void omniSelected(int $i0, String $r1, String $r2, boolean $z0) throws  {
        Intent $r4;
        if (this.mFwdIntent == null) {
            $r4 = new Intent();
        } else {
            $r4 = this.mFwdIntent;
        }
        $r4.setExtrasClassLoader(OmniSelectionActivity.class.getClassLoader());
        $r4.putExtra(RET_SELECTED_IDX, $i0);
        $r4.putExtra(RET_SELECTED_VAL, $r1);
        $r4.putExtra(RET_SELECTED_TITLE, $r2);
        if ($z0) {
            $r4.putExtra(RET_SELECTED_USER_INPUT, $z0);
        }
        if (this.mFwdIntent == null) {
            setResult(-1, $r4);
            finish();
            return;
        }
        setResult(-1);
        startActivityForResult(this.mFwdIntent, 0);
    }

    public static SettingsValue[] testValues1() throws  {
        Random $r0 = new Random();
        int $i0 = $r0.nextInt(100) + 50;
        SettingsValue[] $r1 = new SettingsValue[$i0];
        for (int $i4 = 0; $i4 < $i0; $i4++) {
            SettingsValue $r2;
            $r1[$i4] = new SettingsValue("", "", false);
            $r1[$i4].display = "";
            int $i3 = $r0.nextInt(2) + 1;
            for (int $i5 = 0; $i5 < $i3; $i5++) {
                int $i2 = $r0.nextInt(6) + 3;
                int $i6 = 0;
                while ($i6 < $i2) {
                    char $c1 = (char) (($i6 == 0 ? (byte) 65 : (byte) 97) + $r0.nextInt(10));
                    StringBuilder stringBuilder = new StringBuilder();
                    $r2 = $r1[$i4];
                    $r2.display = stringBuilder.append($r2.display).append($c1).toString();
                    $i6++;
                }
                if ($i5 < $i3 - 1) {
                    stringBuilder = new StringBuilder();
                    $r2 = $r1[$i4];
                    $r2.display = stringBuilder.append($r2.display).append(' ').toString();
                }
            }
            $r2 = $r1[$i4];
            String $r4 = $r1[$i4].display;
            $r2.value = $r4;
            $r2 = $r1[$i4];
            $r4 = $r1[$i4].display;
            $r2.display2 = $r4;
        }
        return $r1;
    }

    public static Intent testFilteredArray(ActivityBase $r0) throws  {
        Intent $r1 = r4;
        Intent r4 = new Intent($r0, OmniSelectionActivity.class);
        $r1.putExtra(ARG_TITLE, "testFilteredArray");
        $r1.putExtra(ARG_VALUES, (Parcelable[]) testValues1());
        $r1.putExtra(ARG_SEARCH, true);
        $r1.putExtra(ARG_FILTER, true);
        $r1.putExtra(ARG_USER_INPUT, true);
        $r1.putExtra(ARG_USER_INPUT_FORMAT, "*Add \"%s\"");
        return $r1;
    }

    public static SettingsValue[] testValues2() throws  {
        Random $r0 = new Random();
        int $i0 = $r0.nextInt(50) + 10;
        int $i4 = 0;
        SettingsValue[] $r1 = new SettingsValue[$i0];
        for (int $i5 = 0; $i5 < $i0; $i5++) {
            SettingsValue $r2;
            $r1[$i5] = new SettingsValue("", "", false);
            $r1[$i5].display = "";
            int $i3 = $r0.nextInt(2) + 1;
            for (int $i6 = 0; $i6 < $i3; $i6++) {
                int $i2 = $r0.nextInt(6) + 3;
                int $i7 = 0;
                while ($i7 < $i2) {
                    char $c1 = (char) (($i7 == 0 ? (byte) 65 : (byte) 97) + $r0.nextInt(25));
                    StringBuilder stringBuilder = new StringBuilder();
                    $r2 = $r1[$i5];
                    $r2.display = stringBuilder.append($r2.display).append($c1).toString();
                    $i7++;
                }
                if ($i6 < $i3 - 1) {
                    stringBuilder = new StringBuilder();
                    $r2 = $r1[$i5];
                    $r2.display = stringBuilder.append($r2.display).append(' ').toString();
                }
            }
            $r2 = $r1[$i5];
            String $r4 = $r1[$i5].display;
            $r2.value = $r4;
            if ($i4 == 0) {
                $r1[$i5].isHeader = true;
                $i4 = $r0.nextInt(6) + 1;
            } else {
                $i4--;
            }
        }
        return $r1;
    }

    public static Intent testExpandableArray(ActivityBase $r0) throws  {
        Intent $r1 = r4;
        Intent r4 = new Intent($r0, OmniSelectionActivity.class);
        $r1.putExtra(ARG_TITLE, "testFilteredArray");
        $r1.putExtra(ARG_VALUES, (Parcelable[]) testValues2());
        $r1.putExtra(ARG_EXPANDABLE, true);
        return $r1;
    }

    public void isSearching(int times) throws  {
    }
}
