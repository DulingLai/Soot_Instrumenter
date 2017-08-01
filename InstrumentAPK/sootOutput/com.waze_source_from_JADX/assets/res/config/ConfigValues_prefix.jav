package com.waze.config;

public class ConfigValues {
    public static int getIntValue(int value) {
        return com.waze.ConfigManager.getInstance().getConfigValueInt(value);
    }
    public static boolean getBoolValue(int value) {
        return com.waze.ConfigManager.getInstance().getConfigValueBool(value);
    }
    public static String getStringValue(int value) {
        return com.waze.ConfigManager.getInstance().getConfigValueString(value);
    }
    public static void setBoolValue(int id, boolean value) {
        com.waze.ConfigManager.getInstance().setConfigValueBool(id,value);
    }
    public static void setStringValue(int id, String value) {
        com.waze.ConfigManager.getInstance().setConfigValueString(id,value);
    }
