package com.waze.ifs.ui;

import android.content.Intent;
import android.os.Bundle;
import com.waze.C1283R;
import com.waze.reports.SimpleChoiceFragment;
import com.waze.reports.SimpleChoiceFragment.ISimplyChoose;
import com.waze.reports.SimpleChoiceFragment.SimpleChoice;

public class SimpleChoiceActivity extends ActivityBase implements ISimplyChoose {
    public static final String ARG_ALLOW_COMMENT = (TAG + ".arg.allow_comment");
    public static final String ARG_CHOICES = (TAG + ".arg.choices");
    public static final String ARG_FWD_INTENT = (TAG + ".arg.fwd_intent");
    public static final String ARG_HINT_DS = (TAG + ".arg.hint");
    public static final String ARG_INPUT_TYPE = (TAG + ".arg.input_type");
    public static final String ARG_SINGLE_LINE = (TAG + ".arg.single_line");
    public static final String ARG_SUBTITLE_DS = (TAG + ".arg.subtitle");
    public static final String ARG_TITLE_DS = (TAG + ".arg.title");
    public static final String RET_CHOICE = (TAG + ".ret.choice");
    public static final String RET_COMMENT = (TAG + ".ret.comment");
    private static final String TAG = SimpleChoiceActivity.class.getName();
    private Intent mFwdIntent;

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.fragment_activity_empty);
        SimpleChoiceFragment $r3 = r13;
        SimpleChoiceFragment r13 = new SimpleChoiceFragment();
        Intent $r4 = getIntent();
        $r3.setTitle($r4.getIntExtra(ARG_TITLE_DS, 0));
        $r3.setSubtitle($r4.getIntExtra(ARG_SUBTITLE_DS, 0));
        $r3.setHint($r4.getIntExtra(ARG_HINT_DS, 0));
        $r3.setSingleLine($r4.getBooleanExtra(ARG_SINGLE_LINE, false));
        $r3.setAllowComment($r4.getBooleanExtra(ARG_ALLOW_COMMENT, false));
        $r3.setInputType($r4.getIntExtra(ARG_INPUT_TYPE, 0));
        Object[] $r7 = (Object[]) $r4.getSerializableExtra(ARG_CHOICES);
        SimpleChoice[] $r2 = new SimpleChoice[$r7.length];
        for (int $i0 = 0; $i0 < $r2.length; $i0++) {
            $r2[$i0] = (SimpleChoice) $r7[$i0];
        }
        $r3.setChoices($r2);
        this.mFwdIntent = (Intent) $r4.getParcelableExtra(ARG_FWD_INTENT);
        getFragmentManager().beginTransaction().add(C1283R.id.container, $r3).commit();
    }

    public void choiceMade(SimpleChoice $r1, String $r2) throws  {
        Intent $r4;
        if (this.mFwdIntent == null) {
            $r4 = $r7;
            Intent $r7 = new Intent();
        } else {
            $r4 = this.mFwdIntent;
        }
        $r4.setExtrasClassLoader(SimpleChoiceActivity.class.getClassLoader());
        $r4.putExtra(RET_CHOICE, $r1);
        $r4.putExtra(RET_COMMENT, $r2);
        if (this.mFwdIntent == null) {
            setResult(-1, $r4);
            finish();
            return;
        }
        setResult(-1);
        startActivityForResult(this.mFwdIntent, 0);
    }
}
