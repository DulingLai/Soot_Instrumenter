package com.abaltatech.mcp.weblink.sdk;

import android.hardware.display.VirtualDisplay;
import android.os.Build.VERSION;
import android.view.Surface;

public class WLDisplay {
    public static final int DEFAULT_CLIENT_DPI = 160;
    private Object m_display;
    private int m_displayID;
    private int m_height;
    private String m_name;
    private Surface m_surface;
    private final int m_uiMode;
    private VirtualDisplay m_virtualDisplay;
    private int m_width;
    private int m_xdpi = 160;
    private int m_ydpi = 160;

    WLDisplay(Object $r1, int $i0, String $r2, int $i1) throws  {
        this.m_display = $r1;
        this.m_displayID = $i0;
        this.m_name = $r2;
        this.m_width = 0;
        this.m_height = 0;
        this.m_uiMode = $i1;
    }

    WLDisplay(int $i0, int $i1, Surface $r1, int $i2, String $r2, int $i3) throws  {
        this.m_surface = $r1;
        this.m_width = $i0;
        this.m_height = $i1;
        this.m_displayID = $i2;
        this.m_name = $r2;
        this.m_uiMode = $i3;
    }

    Object getDisplay() throws  {
        return this.m_display;
    }

    public int getDisplayId() throws  {
        return this.m_displayID;
    }

    public String getName() throws  {
        return this.m_name;
    }

    public int getWidth() throws  {
        return this.m_width;
    }

    public int getHeight() throws  {
        return this.m_height;
    }

    void setVirtualDisplay(VirtualDisplay $r1) throws  {
        this.m_virtualDisplay = $r1;
    }

    void release() throws  {
        if (VERSION.SDK_INT < 19 || this.m_uiMode != 2) {
            WLDisplayManager.getInstance().releaseDisplay(this);
            if (this.m_surface != null) {
                this.m_surface.release();
                this.m_surface = null;
            }
        } else if (this.m_virtualDisplay != null) {
            this.m_virtualDisplay.release();
            this.m_virtualDisplay = null;
        }
    }

    public int getUiMode() throws  {
        return this.m_uiMode;
    }

    public int getClientXdpi() throws  {
        return this.m_xdpi;
    }

    public void setClientXdpi(int $i0) throws  {
        this.m_xdpi = $i0;
    }

    public int getClientYdpi() throws  {
        return this.m_ydpi;
    }

    public void setClientYdpi(int $i0) throws  {
        this.m_ydpi = $i0;
    }
}
