package com.abaltatech.mcp.mcs.common;

import com.facebook.appevents.AppEventsConstants;

public class Version {
    public static String REVISION = "9922";
    public static String VERSION = "2.1.9922";
    public static String VERSION_MAJOR = "2";
    public static String VERSION_MINOR = AppEventsConstants.EVENT_PARAM_VALUE_YES;

    public static String getVersion() throws  {
        return VERSION;
    }
}
