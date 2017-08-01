package com.abaltatech.weblinkserver;

import java.nio.ByteBuffer;

public interface WLLayer {
    boolean canRender() throws ;

    WLRect getScreenRect() throws ;

    void render(ByteBuffer byteBuffer, int i, int i2, int i3) throws ;

    void setScreenRect(WLRect wLRect) throws ;
}
