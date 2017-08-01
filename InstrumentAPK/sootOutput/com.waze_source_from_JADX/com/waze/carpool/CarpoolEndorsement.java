package com.waze.carpool;

import com.waze.C1283R;
import com.waze.strings.DisplayStrings;

public class CarpoolEndorsement {
    public static final int CAREFUL = 5;
    public static final int CLEAN = 4;
    public static final int COOL = 1;
    public static final int FIRST = 1;
    public static final int LAST = 6;
    public static final int PUNCTUAL = 2;
    public static final int QUICK_RES = 3;
    public static final int SWEET = 6;
    public static final int UNSPECIFIED = 0;
    public static final int[] icon = new int[]{0, C1283R.drawable.carpool_cool_idle, C1283R.drawable.carpool_punctual_idle, C1283R.drawable.carpool_responsive_idle, C1283R.drawable.carpool_clean_idle, C1283R.drawable.carpool_safe_idle, C1283R.drawable.carpool_sweet_idle};
    public static final int[] name = new int[]{0, DisplayStrings.DS_ENDORSEMENT_COOL, DisplayStrings.DS_ENDORSEMENT_PUNCTUAL, DisplayStrings.DS_ENDORSEMENT_QUICK_RES, DisplayStrings.DS_ENDORSEMENT_CLEAN, DisplayStrings.DS_ENDORSEMENT_CAREFUL, DisplayStrings.DS_ENDORSEMENT_SWEET};
}
