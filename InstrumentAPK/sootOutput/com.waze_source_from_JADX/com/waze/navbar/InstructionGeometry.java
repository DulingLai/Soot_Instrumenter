package com.waze.navbar;

import android.graphics.PointF;
import android.util.Log;

public class InstructionGeometry {
    public static final int TYPE_PRIMARY = 0;
    public static final int TYPE_PRIMARY_WITH_ARROW = 1;
    public static final int TYPE_SECONDARY = 2;
    public static final int TYPE_SECONDARY_NO_ENTRY = 3;
    private PointF[] mEndPoints;
    private boolean mIsRoundabout;
    private PointF[] mStartPoints;
    private int[] mTypes;

    public InstructionGeometry(float[] $r1, float[] $r2, float[] $r3, float[] $r4, int[] $r5, boolean $z0) throws  {
        if ($r1 == null || $r2 == null || $r3 == null || $r4 == null || $r5 == null) {
            Log.e("InstructionGeometry", "Some of the coordinates were null. Aborting creation.");
        } else if ($r1.length == $r2.length && $r1.length == $r3.length && $r1.length == $r4.length && $r1.length == $r5.length) {
            this.mStartPoints = new PointF[$r1.length];
            this.mEndPoints = new PointF[$r3.length];
            this.mTypes = new int[$r5.length];
            for (int $i0 = 0; $i0 < this.mStartPoints.length; $i0++) {
                this.mStartPoints[$i0] = new PointF($r1[$i0], $r2[$i0]);
                this.mEndPoints[$i0] = new PointF($r3[$i0], $r4[$i0]);
                this.mTypes[$i0] = $r5[$i0];
            }
            this.mIsRoundabout = $z0;
        } else {
            Log.e("InstructionGeometry", "Coordinate arrays are not of same length. Aborting creation.");
        }
    }

    public PointF getStartPoint(int $i0) throws  {
        return this.mStartPoints[$i0];
    }

    public PointF getEndPoint(int $i0) throws  {
        return this.mEndPoints[$i0];
    }

    public int getType(int $i0) throws  {
        return this.mTypes[$i0];
    }

    public int getTotalPoints() throws  {
        return this.mTypes.length;
    }

    public boolean isRoundabout() throws  {
        return this.mIsRoundabout;
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        for (int $i0 = 0; $i0 < getTotalPoints(); $i0++) {
            $r1.append("Point #");
            $r1.append($i0);
            $r1.append(") ");
            $r1.append("Start: ");
            $r1.append(getStartPoint($i0).toString());
            $r1.append(" End: ");
            $r1.append(getEndPoint($i0).toString());
            $r1.append(" Type: ");
            $r1.append(getType($i0));
            $r1.append("\n");
        }
        return $r1.toString();
    }
}
