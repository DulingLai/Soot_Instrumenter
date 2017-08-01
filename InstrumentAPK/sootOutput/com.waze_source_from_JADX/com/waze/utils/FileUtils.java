package com.waze.utils;

public class FileUtils {
    private static final String PATH_SEPARATOR = "/";

    public static String getOnlyPath(String fullPath) {
        return fullPath.substring(0, fullPath.lastIndexOf(PATH_SEPARATOR));
    }

    public static String getFilenameOnly(String fullPath) {
        return fullPath.substring(fullPath.lastIndexOf(PATH_SEPARATOR) + 1);
    }
}
