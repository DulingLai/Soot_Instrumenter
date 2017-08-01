package com.google.android.apps.analytics;

/* compiled from: dalvik_source_com.waze.apk */
class CustomVariable {
    public static final String INDEX_ERROR_MSG = "Index must be between 1 and 5 inclusive.";
    public static final int MAX_CUSTOM_VARIABLES = 5;
    public static final int MAX_CUSTOM_VARIABLE_LENGTH = 64;
    public static final int PAGE_SCOPE = 3;
    public static final int SESSION_SCOPE = 2;
    public static final int VISITOR_SCOPE = 1;
    private final int index;
    private final String name;
    private final int scope;
    private final String value;

    public CustomVariable(int $i0, String $r1, String $r2) throws  {
        this($i0, $r1, $r2, 3);
    }

    public CustomVariable(int $i0, String $r1, String $r2, int $i1) throws  {
        if ($i1 != 1 && $i1 != 2 && $i1 != 3) {
            throw new IllegalArgumentException("Invalid Scope:" + $i1);
        } else if ($i0 < 1 || $i0 > 5) {
            throw new IllegalArgumentException(INDEX_ERROR_MSG);
        } else if ($r1 == null || $r1.length() == 0) {
            throw new IllegalArgumentException("Invalid argument for name:  Cannot be empty");
        } else if ($r2 == null || $r2.length() == 0) {
            throw new IllegalArgumentException("Invalid argument for value:  Cannot be empty");
        } else {
            int $i2 = AnalyticsParameterEncoder.encode($r1 + $r2).length();
            if ($i2 > 64) {
                throw new IllegalArgumentException("Encoded form of name and value must not exceed 64 characters combined.  Character length: " + $i2);
            }
            this.index = $i0;
            this.scope = $i1;
            this.name = $r1;
            this.value = $r2;
        }
    }

    public int getIndex() throws  {
        return this.index;
    }

    public String getName() throws  {
        return this.name;
    }

    public int getScope() throws  {
        return this.scope;
    }

    public String getValue() throws  {
        return this.value;
    }
}
