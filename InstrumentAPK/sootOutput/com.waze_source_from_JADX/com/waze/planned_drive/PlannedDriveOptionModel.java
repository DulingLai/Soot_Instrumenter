package com.waze.planned_drive;

import com.waze.map.CanvasFont;
import com.waze.view.anim.EasingInterpolators;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PlannedDriveOptionModel {
    public static final int BARS_PER_ROW = 10;
    private long mEtaMillis;
    private float[] mGraphValues = new float[10];
    private long mTimeMillis;

    public static List<PlannedDriveOptionModel> createOptionModels(int[] etas, int[] times) {
        int i = 0;
        List<PlannedDriveOptionModel> result = new ArrayList();
        if (!(etas == null || etas.length == 0 || times == null || times.length == 0)) {
            int minEta = etas[0];
            int maxEta = etas[0];
            int length = etas.length;
            while (i < length) {
                int eta = etas[i];
                if (eta < minEta) {
                    minEta = eta;
                }
                if (eta > maxEta) {
                    maxEta = eta;
                }
                i++;
            }
            for (int i2 = 0; i2 < etas.length; i2++) {
                int fromEta;
                int toEta;
                int currentEta = etas[i2];
                if (i2 > 0) {
                    fromEta = etas[i2 - 1];
                } else {
                    fromEta = currentEta;
                }
                if (i2 < etas.length - 1) {
                    toEta = etas[i2 + 1];
                } else {
                    toEta = currentEta;
                }
                result.add(new PlannedDriveOptionModel((long) times[i2], currentEta, fromEta, toEta, minEta, maxEta));
            }
        }
        return result;
    }

    private PlannedDriveOptionModel(long timeSeconds, int etaSeconds, int fromEtaSeconds, int toEtaSeconds, int minEta, int maxEta) {
        this.mTimeMillis = 1000 * timeSeconds;
        this.mEtaMillis = ((long) etaSeconds) * 1000;
        float fromRatio = ((float) (fromEtaSeconds - minEta)) / ((float) (maxEta - minEta));
        float toRatio = ((float) (toEtaSeconds - minEta)) / ((float) (maxEta - minEta));
        float currentRatio = ((float) (etaSeconds - minEta)) / ((float) (maxEta - minEta));
        for (int i = 0; i < this.mGraphValues.length; i++) {
            if (((float) i) < 5.0f) {
                this.mGraphValues[i] = ((currentRatio - fromRatio) * EasingInterpolators.EASE_IN_EASE_OUT.getInterpolation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR + (((float) i) / 10.0f))) + fromRatio;
            } else {
                this.mGraphValues[i] = ((toRatio - currentRatio) * EasingInterpolators.EASE_IN_EASE_OUT.getInterpolation((((float) i) - 5.0f) / 10.0f)) + currentRatio;
            }
        }
    }

    public long getTimeMillis() {
        return this.mTimeMillis;
    }

    public long getEtaMillis() {
        return this.mEtaMillis;
    }

    public float[] getGraphValues() {
        return this.mGraphValues;
    }

    public int getHour() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(this.mTimeMillis);
        return calendar.get(11);
    }
}
