package org.achartengine.renderer;

import java.io.Serializable;

public class SimpleSeriesRenderer implements Serializable {
    private int mColor = -16776961;

    public int getColor() {
        return this.mColor;
    }

    public void setColor(int i) {
        this.mColor = i;
    }
}
