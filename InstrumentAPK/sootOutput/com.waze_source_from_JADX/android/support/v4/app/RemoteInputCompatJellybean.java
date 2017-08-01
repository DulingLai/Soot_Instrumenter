package android.support.v4.app;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput.Factory;

class RemoteInputCompatJellybean {
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    private static final String KEY_ALLOW_FREE_FORM_INPUT = "allowFreeFormInput";
    private static final String KEY_CHOICES = "choices";
    private static final String KEY_EXTRAS = "extras";
    private static final String KEY_LABEL = "label";
    private static final String KEY_RESULT_KEY = "resultKey";
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";

    RemoteInputCompatJellybean() throws  {
    }

    static RemoteInput fromBundle(Bundle $r0, Factory $r1) throws  {
        return $r1.build($r0.getString(KEY_RESULT_KEY), $r0.getCharSequence("label"), $r0.getCharSequenceArray(KEY_CHOICES), $r0.getBoolean(KEY_ALLOW_FREE_FORM_INPUT), $r0.getBundle(KEY_EXTRAS));
    }

    static Bundle toBundle(RemoteInput $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putString(KEY_RESULT_KEY, $r0.getResultKey());
        $r1.putCharSequence("label", $r0.getLabel());
        $r1.putCharSequenceArray(KEY_CHOICES, $r0.getChoices());
        $r1.putBoolean(KEY_ALLOW_FREE_FORM_INPUT, $r0.getAllowFreeFormInput());
        $r1.putBundle(KEY_EXTRAS, $r0.getExtras());
        return $r1;
    }

    static RemoteInput[] fromBundleArray(Bundle[] $r0, Factory $r1) throws  {
        if ($r0 == null) {
            return null;
        }
        RemoteInput[] $r2 = $r1.newArray($r0.length);
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r2[$i0] = fromBundle($r0[$i0], $r1);
        }
        return $r2;
    }

    static Bundle[] toBundleArray(RemoteInput[] $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        Bundle[] $r2 = new Bundle[$r0.length];
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r2[$i0] = toBundle($r0[$i0]);
        }
        return $r2;
    }

    static Bundle getResultsFromIntent(Intent $r0) throws  {
        ClipData $r2 = $r0.getClipData();
        if ($r2 == null) {
            return null;
        }
        ClipDescription $r3 = $r2.getDescription();
        if ($r3.hasMimeType("text/vnd.android.intent")) {
            return $r3.getLabel().equals("android.remoteinput.results") ? (Bundle) $r2.getItemAt(0).getIntent().getExtras().getParcelable("android.remoteinput.resultsData") : null;
        } else {
            return null;
        }
    }

    static void addResultsToIntent(RemoteInput[] $r0, Intent $r1, Bundle $r2) throws  {
        Bundle $r5 = new Bundle();
        for (RemoteInput $r4 : $r0) {
            Object $r7 = $r2.get($r4.getResultKey());
            if ($r7 instanceof CharSequence) {
                $r5.putCharSequence($r4.getResultKey(), (CharSequence) $r7);
            }
        }
        Intent $r3 = new Intent();
        $r3.putExtra("android.remoteinput.resultsData", $r5);
        $r1.setClipData(ClipData.newIntent("android.remoteinput.results", $r3));
    }
}
