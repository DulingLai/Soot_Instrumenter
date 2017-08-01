package com.waze.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.map.CanvasFont;
import com.waze.settings.SettingsNativeManager;
import com.waze.strings.DisplayStrings;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class DisplayUtils {

    private enum DayType {
        TODAY,
        TOMORROW,
        WEEKDAY
    }

    public interface OnOrientationReallyChanged {
        void onChanged(int i);
    }

    private enum TimeType {
        NIGHT,
        MORNING,
        DAY,
        AFTERNOON,
        EVENING
    }

    public static int dpiToPixels(float dp) {
        return Math.round((scale() * dp) + CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
    }

    public static float pixelsToDpi(int px) {
        return ((float) px) / scale();
    }

    public static float scale() {
        return AppService.getAppContext().getResources().getDisplayMetrics().density;
    }

    public static String getDayString(Context ctx, long timeMs, boolean supportHours, boolean pastStillToday) {
        int daysDiff;
        NativeManager nm = AppService.getNativeManager();
        Calendar now = Calendar.getInstance();
        Locale locale = new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV());
        Date date = new Date(timeMs);
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        cal.setTimeInMillis(timeMs);
        if (now.before(cal)) {
            if (cal.get(1) != now.get(1)) {
                daysDiff = 0;
                while (now.before(cal)) {
                    now.add(5, 1);
                    daysDiff++;
                    if (daysDiff > 14) {
                        break;
                    }
                }
            }
            daysDiff = cal.get(6) - now.get(6);
        } else {
            int diff = now.get(5) - cal.get(5);
            if (diff == 1) {
                return nm.getLanguageString(DisplayStrings.DS_YESTERDAY);
            }
            if (pastStillToday && diff == 0) {
                return nm.getLanguageString(DisplayStrings.DS_TODAY_CAP);
            }
            daysDiff = 300;
        }
        long hoursDiff = ((((cal.getTime().getTime() - now.getTime().getTime()) / 1000) / 60) + 30) / 60;
        if (daysDiff == 0 && supportHours && hoursDiff <= 6) {
            return NativeManager.getInstance().getRelativeTimeStringNTV(cal.getTime().getTime() / 1000, false);
        } else if (daysDiff == 0) {
            day = nm.getLanguageString(DisplayStrings.DS_TODAY_CAP);
            return day.substring(0, 1).toUpperCase(locale) + day.substring(1);
        } else if (daysDiff == 1) {
            day = nm.getLanguageString(DisplayStrings.DS_TOMORROW);
            return day.substring(0, 1).toUpperCase(locale) + day.substring(1);
        } else if (daysDiff < 7) {
            sdf = new SimpleDateFormat("EEEE", locale);
            sdf.setTimeZone(tz);
            return sdf.format(date);
        } else if (daysDiff < 14) {
            sdf = new SimpleDateFormat("EEEE", locale);
            sdf.setTimeZone(tz);
            return sdf.format(date);
        } else {
            DateFormat df = SimpleDateFormat.getDateInstance(2, locale);
            df.setTimeZone(tz);
            return df.format(date);
        }
    }

    public static String getDayTimeString(Context ctx, long timeMs, String format) {
        Calendar cal = Calendar.getInstance();
        TimeZone tz = cal.getTimeZone();
        cal.setTimeInMillis(timeMs);
        Date date = new Date(timeMs);
        DateFormat tf = android.text.format.DateFormat.getTimeFormat(ctx);
        tf.setTimeZone(tz);
        String time = tf.format(date);
        return String.format(format, new Object[]{getDayString(ctx, timeMs, false, false), time});
    }

    public static String getTimeString(Context ctx, long timeMs) {
        TimeZone tz = Calendar.getInstance().getTimeZone();
        Date date = new Date(timeMs);
        DateFormat tf = android.text.format.DateFormat.getTimeFormat(ctx);
        tf.setTimeZone(tz);
        return tf.format(date);
    }

    public static String getApproximateTimeString(long timeMs) {
        DayType day;
        TimeType time;
        Calendar now = Calendar.getInstance();
        Calendar rideTime = Calendar.getInstance();
        rideTime.setTimeInMillis(timeMs);
        if (now.get(6) == rideTime.get(6)) {
            day = DayType.TODAY;
        } else {
            now.add(5, 1);
            if (now.get(6) == rideTime.get(6)) {
                day = DayType.TOMORROW;
            } else {
                day = DayType.WEEKDAY;
            }
        }
        int hour = rideTime.get(11);
        if (hour < 4) {
            time = TimeType.NIGHT;
        } else if (hour < 10) {
            time = TimeType.MORNING;
        } else if (hour < 14) {
            time = TimeType.DAY;
        } else if (hour < 17) {
            time = TimeType.AFTERNOON;
        } else if (hour < 20) {
            time = TimeType.EVENING;
        } else {
            time = TimeType.NIGHT;
        }
        String text = DisplayStrings.displayString(getDayTimeDsId(day, time));
        if (!text.contains("<DAY>")) {
            return text;
        }
        return text.replace("<DAY>", new SimpleDateFormat("EEEE", NativeManager.getInstance().getLocale()).format(rideTime.getTime()));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static int getDayTimeDsId(com.waze.utils.DisplayUtils.DayType r2, com.waze.utils.DisplayUtils.TimeType r3) {
        /*
        r0 = com.waze.utils.DisplayUtils.C29393.$SwitchMap$com$waze$utils$DisplayUtils$DayType;
        r1 = r2.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 1: goto L_0x000e;
            case 2: goto L_0x0019;
            case 3: goto L_0x0024;
            default: goto L_0x000b;
        };
    L_0x000b:
        r0 = 2257; // 0x8d1 float:3.163E-42 double:1.115E-320;
    L_0x000d:
        return r0;
    L_0x000e:
        r0 = com.waze.utils.DisplayUtils.C29393.$SwitchMap$com$waze$utils$DisplayUtils$TimeType;
        r1 = r3.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 1: goto L_0x0033;
            case 2: goto L_0x0036;
            case 3: goto L_0x0039;
            case 4: goto L_0x003c;
            case 5: goto L_0x003f;
            default: goto L_0x0019;
        };
    L_0x0019:
        r0 = com.waze.utils.DisplayUtils.C29393.$SwitchMap$com$waze$utils$DisplayUtils$TimeType;
        r1 = r3.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 1: goto L_0x0042;
            case 2: goto L_0x0045;
            case 3: goto L_0x0048;
            case 4: goto L_0x004b;
            case 5: goto L_0x004e;
            default: goto L_0x0024;
        };
    L_0x0024:
        r0 = com.waze.utils.DisplayUtils.C29393.$SwitchMap$com$waze$utils$DisplayUtils$TimeType;
        r1 = r3.ordinal();
        r0 = r0[r1];
        switch(r0) {
            case 1: goto L_0x0030;
            case 2: goto L_0x0051;
            case 3: goto L_0x0054;
            case 4: goto L_0x0057;
            case 5: goto L_0x005a;
            default: goto L_0x002f;
        };
    L_0x002f:
        goto L_0x000b;
    L_0x0030:
        r0 = 2325; // 0x915 float:3.258E-42 double:1.1487E-320;
        goto L_0x000d;
    L_0x0033:
        r0 = 2315; // 0x90b float:3.244E-42 double:1.144E-320;
        goto L_0x000d;
    L_0x0036:
        r0 = 2316; // 0x90c float:3.245E-42 double:1.1443E-320;
        goto L_0x000d;
    L_0x0039:
        r0 = 2317; // 0x90d float:3.247E-42 double:1.1448E-320;
        goto L_0x000d;
    L_0x003c:
        r0 = 2318; // 0x90e float:3.248E-42 double:1.145E-320;
        goto L_0x000d;
    L_0x003f:
        r0 = 2319; // 0x90f float:3.25E-42 double:1.1457E-320;
        goto L_0x000d;
    L_0x0042:
        r0 = 2320; // 0x910 float:3.251E-42 double:1.146E-320;
        goto L_0x000d;
    L_0x0045:
        r0 = 2321; // 0x911 float:3.252E-42 double:1.1467E-320;
        goto L_0x000d;
    L_0x0048:
        r0 = 2322; // 0x912 float:3.254E-42 double:1.147E-320;
        goto L_0x000d;
    L_0x004b:
        r0 = 2323; // 0x913 float:3.255E-42 double:1.1477E-320;
        goto L_0x000d;
    L_0x004e:
        r0 = 2324; // 0x914 float:3.257E-42 double:1.148E-320;
        goto L_0x000d;
    L_0x0051:
        r0 = 2326; // 0x916 float:3.26E-42 double:1.149E-320;
        goto L_0x000d;
    L_0x0054:
        r0 = 2327; // 0x917 float:3.261E-42 double:1.1497E-320;
        goto L_0x000d;
    L_0x0057:
        r0 = 2328; // 0x918 float:3.262E-42 double:1.15E-320;
        goto L_0x000d;
    L_0x005a:
        r0 = 2329; // 0x919 float:3.264E-42 double:1.1507E-320;
        goto L_0x000d;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.utils.DisplayUtils.getDayTimeDsId(com.waze.utils.DisplayUtils$DayType, com.waze.utils.DisplayUtils$TimeType):int");
    }

    public static void setChildColorMatrix(View child, ColorFilter f, int drawableAlpha, int textAlpha) {
        Drawable d;
        if (child instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) child;
            int num = vg.getChildCount();
            for (int i = 0; i < num; i++) {
                setChildColorMatrix(vg.getChildAt(i), f, drawableAlpha, textAlpha);
            }
        }
        if (child instanceof ImageView) {
            d = ((ImageView) child).getDrawable();
            if (d != null) {
                d.setColorFilter(f);
                d.setAlpha(drawableAlpha);
            }
        }
        if (child instanceof TextView) {
            TextView tv = (TextView) child;
            tv.setTextColor(tv.getTextColors().withAlpha(textAlpha));
        }
        d = child.getBackground();
        if (d != null) {
            d.setColorFilter(f);
            d.setAlpha(drawableAlpha);
        }
    }

    public static void setButtonEnabled(TextView textView, boolean enabled) {
        if (enabled) {
            textView.getBackground().setAlpha(255);
            textView.setTextColor(textView.getResources().getColor(C1283R.color.text_wt_button_enabled));
            return;
        }
        textView.getBackground().setAlpha(125);
        textView.setTextColor(Color.parseColor("#77000000"));
    }

    public static void lmkWhenOrientationReallyChanged(final View v, final int orientation, final OnOrientationReallyChanged oorc) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                int w = v.getRootView().getMeasuredWidth();
                int h = v.getRootView().getMeasuredHeight();
                if (w != 0 && h != 0) {
                    if (orientation == 1 && w > h) {
                        return;
                    }
                    if (orientation != 2 || w >= h) {
                        v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        oorc.onChanged(orientation);
                    }
                }
            }
        });
    }

    public static void runOnLayout(final View v, final Runnable toRun) {
        v.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                v.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                toRun.run();
            }
        });
    }
}
