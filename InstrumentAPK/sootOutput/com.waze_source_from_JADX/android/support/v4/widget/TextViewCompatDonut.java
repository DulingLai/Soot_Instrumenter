package android.support.v4.widget;

import android.util.Log;
import android.widget.TextView;
import java.lang.reflect.Field;

class TextViewCompatDonut {
    private static final int LINES = 1;
    private static final String LOG_TAG = "TextViewCompatDonut";
    private static Field sMaxModeField;
    private static boolean sMaxModeFieldFetched;
    private static Field sMaximumField;
    private static boolean sMaximumFieldFetched;
    private static Field sMinModeField;
    private static boolean sMinModeFieldFetched;
    private static Field sMinimumField;
    private static boolean sMinimumFieldFetched;

    TextViewCompatDonut() throws  {
    }

    static int getMaxLines(TextView $r0) throws  {
        if (!sMaxModeFieldFetched) {
            sMaxModeField = retrieveField("mMaxMode");
            sMaxModeFieldFetched = true;
        }
        if (sMaxModeField != null && retrieveIntFromField(sMaxModeField, $r0) == 1) {
            if (!sMaximumFieldFetched) {
                sMaximumField = retrieveField("mMaximum");
                sMaximumFieldFetched = true;
            }
            if (sMaximumField != null) {
                return retrieveIntFromField(sMaximumField, $r0);
            }
        }
        return -1;
    }

    static int getMinLines(TextView $r0) throws  {
        if (!sMinModeFieldFetched) {
            sMinModeField = retrieveField("mMinMode");
            sMinModeFieldFetched = true;
        }
        if (sMinModeField != null && retrieveIntFromField(sMinModeField, $r0) == 1) {
            if (!sMinimumFieldFetched) {
                sMinimumField = retrieveField("mMinimum");
                sMinimumFieldFetched = true;
            }
            if (sMinimumField != null) {
                return retrieveIntFromField(sMinimumField, $r0);
            }
        }
        return -1;
    }

    private static Field retrieveField(String $r0) throws  {
        Field $r2 = null;
        try {
            Field $r4 = TextView.class.getDeclaredField($r0);
            $r2 = $r4;
            $r4.setAccessible(true);
            return $r4;
        } catch (NoSuchFieldException e) {
            Log.e(LOG_TAG, "Could not retrieve " + $r0 + " field.");
            return $r2;
        }
    }

    private static int retrieveIntFromField(Field $r0, TextView $r1) throws  {
        try {
            return $r0.getInt($r1);
        } catch (IllegalAccessException e) {
            Log.d(LOG_TAG, "Could not retrieve value of " + $r0.getName() + " field.");
            return -1;
        }
    }

    static void setTextAppearance(TextView $r0, int $i0) throws  {
        $r0.setTextAppearance($r0.getContext(), $i0);
    }
}
