package android.support.v7.text;

import android.content.Context;
import android.graphics.Rect;
import android.text.method.TransformationMethod;
import android.view.View;
import java.util.Locale;

public class AllCapsTransformationMethod implements TransformationMethod {
    private Locale mLocale;

    public AllCapsTransformationMethod(Context $r1) throws  {
        this.mLocale = $r1.getResources().getConfiguration().locale;
    }

    public CharSequence getTransformation(CharSequence $r1, View view) throws  {
        return $r1 != null ? $r1.toString().toUpperCase(this.mLocale) : null;
    }

    public void onFocusChanged(View view, CharSequence sourceText, boolean focused, int direction, Rect previouslyFocusedRect) throws  {
    }
}
