package android.support.v4.hardware.display;

import android.content.Context;
import android.os.Build.VERSION;
import android.view.Display;
import android.view.WindowManager;
import java.util.WeakHashMap;

public abstract class DisplayManagerCompat {
    public static final String DISPLAY_CATEGORY_PRESENTATION = "android.hardware.display.category.PRESENTATION";
    private static final WeakHashMap<Context, DisplayManagerCompat> sInstances = new WeakHashMap();

    private static class JellybeanMr1Impl extends DisplayManagerCompat {
        private final Object mDisplayManagerObj;

        public JellybeanMr1Impl(Context $r1) throws  {
            this.mDisplayManagerObj = DisplayManagerJellybeanMr1.getDisplayManager($r1);
        }

        public Display getDisplay(int $i0) throws  {
            return DisplayManagerJellybeanMr1.getDisplay(this.mDisplayManagerObj, $i0);
        }

        public Display[] getDisplays() throws  {
            return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj);
        }

        public Display[] getDisplays(String $r1) throws  {
            return DisplayManagerJellybeanMr1.getDisplays(this.mDisplayManagerObj, $r1);
        }
    }

    private static class LegacyImpl extends DisplayManagerCompat {
        private final WindowManager mWindowManager;

        public LegacyImpl(Context $r1) throws  {
            this.mWindowManager = (WindowManager) $r1.getSystemService("window");
        }

        public Display getDisplay(int $i0) throws  {
            Display $r2 = this.mWindowManager.getDefaultDisplay();
            return $r2.getDisplayId() == $i0 ? $r2 : null;
        }

        public Display[] getDisplays() throws  {
            return new Display[]{this.mWindowManager.getDefaultDisplay()};
        }

        public Display[] getDisplays(String $r1) throws  {
            return $r1 == null ? getDisplays() : new Display[0];
        }
    }

    public abstract Display getDisplay(int i) throws ;

    public abstract Display[] getDisplays() throws ;

    public abstract Display[] getDisplays(String str) throws ;

    DisplayManagerCompat() throws  {
    }

    public static DisplayManagerCompat getInstance(Context $r0) throws  {
        DisplayManagerCompat $r4;
        synchronized (sInstances) {
            $r4 = (DisplayManagerCompat) sInstances.get($r0);
            if ($r4 == null) {
                if (VERSION.SDK_INT >= 17) {
                    $r4 = r6;
                    DisplayManagerCompat r6 = new JellybeanMr1Impl($r0);
                } else {
                    $r4 = r7;
                    DisplayManagerCompat r7 = new LegacyImpl($r0);
                }
                sInstances.put($r0, $r4);
            }
        }
        return $r4;
    }
}
