package com.waze.strings;

import com.waze.NativeManager;

public class DisplayStrings {

    static NativeManager nm = NativeManager.getInstance();
    
    // Note: do not use these functions when it's possible NativeManager was not initialized
    public static String displayString(int ds) {
        return nm.getLanguageString(ds);
    }
    public static String displayStringF(int ds, Object...args) {
        return nm.getFormattedString(ds, args);
    }

    public static boolean isValid(int value) {
        return (value > DS_NULL && value < DS_ZZZ_LAST);
    }
   
    public static int DS_NULL = -1;
