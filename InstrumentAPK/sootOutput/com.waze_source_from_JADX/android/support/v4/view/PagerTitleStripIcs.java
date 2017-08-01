package android.support.v4.view;

import android.content.Context;
import android.text.method.SingleLineTransformationMethod;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

class PagerTitleStripIcs {

    private static class SingleLineAllCapsTransform extends SingleLineTransformationMethod {
        private static final String TAG = "SingleLineAllCapsTransform";
        private Locale mLocale;

        public SingleLineAllCapsTransform(Context $r1) throws  {
            this.mLocale = $r1.getResources().getConfiguration().locale;
        }

        public CharSequence getTransformation(CharSequence $r3, View $r1) throws  {
            $r3 = super.getTransformation($r3, $r1);
            return $r3 != null ? $r3.toString().toUpperCase(this.mLocale) : null;
        }
    }

    PagerTitleStripIcs() throws  {
    }

    public static void setSingleLineAllCaps(TextView $r0) throws  {
        $r0.setTransformationMethod(new SingleLineAllCapsTransform($r0.getContext()));
    }
}
