package com.abaltatech.mcp.weblink.sdk.widgets;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build.VERSION;
import android.view.ContextMenu;
import android.view.ContextThemeWrapper;
import android.view.View;
import com.abaltatech.mcp.weblink.sdk.WEBLINK;
import com.abaltatech.mcp.weblink.sdk.WLMirrorMode;
import java.lang.reflect.Field;

public class WLContextUtils {
    public static final int DefaultContextMenuThemeInPresentationMode = 16973835;

    public static ContextThemeWrapper wrapContext(Context $r0, int $i0) throws  {
        return new ContextThemeWrapper($r0, $i0);
    }

    public static void changeContextMenuTheme(ContextMenu $r0) throws  {
        changeContextMenuTheme($r0, DefaultContextMenuThemeInPresentationMode, WLMirrorMode.instance.getOriginalConfiguration());
    }

    @SuppressLint({"NewApi"})
    public static void changeContextMenuTheme(ContextMenu $r0, int $i0, Configuration $r1) throws  {
        Field $r4;
        ContextThemeWrapper $r7;
        if (WEBLINK.instance.getUiMode() == 2 && VERSION.SDK_INT == 19) {
            try {
                $r4 = Class.forName("com.android.internal.view.menu.MenuBuilder").getDeclaredField("mContext");
                $r4.setAccessible(true);
                $r7 = new ContextThemeWrapper((Context) $r4.get($r0), $i0);
                if ($r1 != null) {
                    $r7.applyOverrideConfiguration($r1);
                }
                $r4.set($r0, $r7);
            } catch (ClassNotFoundException $r8) {
                $r8.printStackTrace();
            } catch (NoSuchFieldException $r9) {
                $r9.printStackTrace();
            } catch (IllegalAccessException $r10) {
                $r10.printStackTrace();
            } catch (IllegalArgumentException $r11) {
                $r11.printStackTrace();
            }
        } else if ($r1 != null) {
            try {
                $r4 = Class.forName("com.android.internal.view.menu.MenuBuilder").getDeclaredField("mContext");
                $r4.setAccessible(true);
                Context $r6 = (Context) $r4.get($r0);
                int $i02 = $r6.getApplicationInfo().theme;
                $r7 = new ContextThemeWrapper($r6, $i02);
                if ($r1 != null) {
                    $r7.applyOverrideConfiguration($r1);
                }
                $r4.set($r0, $r7);
            } catch (ClassNotFoundException $r13) {
                $r13.printStackTrace();
            } catch (NoSuchFieldException $r14) {
                $r14.printStackTrace();
            } catch (IllegalAccessException $r15) {
                $r15.printStackTrace();
            } catch (IllegalArgumentException $r16) {
                $r16.printStackTrace();
            }
        }
    }

    public static Context changeViewTheme(View $r0) throws  {
        return changeViewTheme($r0, DefaultContextMenuThemeInPresentationMode);
    }

    public static Context changeViewTheme(View $r0, int $i0) throws  {
        Context $r2 = $r0.getContext();
        setViewContext($r0, new ContextThemeWrapper($r2, $i0));
        return $r2;
    }

    public static void setViewContext(View $r0, Context $r1) throws  {
        try {
            Field $r2 = View.class.getDeclaredField("mContext");
            $r2.setAccessible(true);
            $r2.set($r0, $r1);
        } catch (NoSuchFieldException $r4) {
            $r4.printStackTrace();
        } catch (IllegalAccessException $r5) {
            $r5.printStackTrace();
        } catch (IllegalArgumentException $r6) {
            $r6.printStackTrace();
        }
    }
}
