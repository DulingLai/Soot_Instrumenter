package com.abaltatech.mcp.mcs.common;

public class MCSCompressionControlDefaults {
    private static int ms_defCompressionMode = 2;
    private static int ms_defMinCompressionSize = 128;

    public static int getDefaultCompressionMode() throws  {
        return ms_defCompressionMode;
    }

    public static void setDefaultCompressionMode(int $i0) throws  {
        ms_defCompressionMode = $i0;
    }

    public static int getDefaultMinimumCompressionSize() throws  {
        return ms_defMinCompressionSize;
    }

    public static void getDefaultMinimumCompressionSize(int $i0) throws  {
        ms_defMinCompressionSize = $i0;
    }
}
