package org.achartengine.util;

import com.waze.FriendsBarFragment;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class MathHelper {
    private static final NumberFormat FORMAT = NumberFormat.getNumberInstance();
    public static final double NULL_VALUE = Double.MAX_VALUE;

    private MathHelper() {
    }

    public static double[] minmax(List<Double> list) {
        if (list.size() == 0) {
            return new double[2];
        }
        double doubleValue = ((Double) list.get(0)).doubleValue();
        int size = list.size();
        double d = doubleValue;
        double d2 = doubleValue;
        for (int i = 1; i < size; i++) {
            double doubleValue2 = ((Double) list.get(i)).doubleValue();
            d2 = Math.min(d2, doubleValue2);
            d = Math.max(d, doubleValue2);
        }
        return new double[]{d2, d};
    }

    public static List<Double> getLabels(double d, double d2, int i) {
        FORMAT.setMaximumFractionDigits(5);
        List<Double> arrayList = new ArrayList();
        double[] computeLabels = computeLabels(d, d2, i);
        int i2 = ((int) ((computeLabels[1] - computeLabels[0]) / computeLabels[2])) + 1;
        for (int i3 = 0; i3 < i2; i3++) {
            double d3 = computeLabels[0] + (((double) i3) * computeLabels[2]);
            try {
                d3 = FORMAT.parse(FORMAT.format(d3)).doubleValue();
            } catch (ParseException e) {
            }
            arrayList.add(Double.valueOf(d3));
        }
        return arrayList;
    }

    private static double[] computeLabels(double d, double d2, int i) {
        if (Math.abs(d - d2) < 1.0000000116860974E-7d) {
            return new double[]{d, d, 0.0d};
        }
        Object obj = null;
        if (d > d2) {
            obj = 1;
        } else {
            double d3 = d2;
            d2 = d;
            d = d3;
        }
        double roundUp = roundUp(Math.abs(d2 - d) / ((double) i));
        double ceil = Math.ceil(d2 / roundUp) * roundUp;
        double floor = Math.floor(d / roundUp) * roundUp;
        if (obj != null) {
            return new double[]{floor, ceil, roundUp * -1.0d};
        }
        return new double[]{ceil, floor, roundUp};
    }

    private static double roundUp(double d) {
        double d2 = 5.0d;
        int floor = (int) Math.floor(Math.log10(d));
        double pow = Math.pow(10.0d, (double) (-floor)) * d;
        if (pow > 5.0d) {
            d2 = 10.0d;
        } else if (pow <= 2.0d) {
            if (pow > FriendsBarFragment.END_LOCATION_POSITION) {
                d2 = 2.0d;
            } else {
                d2 = pow;
            }
        }
        return d2 * Math.pow(10.0d, (double) floor);
    }

    public static float[] getFloats(List<Float> list) {
        int size = list.size();
        float[] fArr = new float[size];
        for (int i = 0; i < size; i++) {
            fArr[i] = ((Float) list.get(i)).floatValue();
        }
        return fArr;
    }
}
