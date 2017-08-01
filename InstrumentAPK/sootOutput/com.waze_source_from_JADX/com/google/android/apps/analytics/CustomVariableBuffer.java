package com.google.android.apps.analytics;

/* compiled from: dalvik_source_com.waze.apk */
class CustomVariableBuffer {
    private CustomVariable[] customVariables = new CustomVariable[5];

    private void throwOnInvalidIndex(int $i0) throws  {
        if ($i0 < 1 || $i0 > 5) {
            throw new IllegalArgumentException(CustomVariable.INDEX_ERROR_MSG);
        }
    }

    public CustomVariable[] getCustomVariableArray() throws  {
        return (CustomVariable[]) this.customVariables.clone();
    }

    public CustomVariable getCustomVariableAt(int $i0) throws  {
        throwOnInvalidIndex($i0);
        return this.customVariables[$i0 - 1];
    }

    public boolean hasCustomVariables() throws  {
        for (CustomVariable $r2 : this.customVariables) {
            if ($r2 != null) {
                return true;
            }
        }
        return false;
    }

    public boolean isIndexAvailable(int $i0) throws  {
        throwOnInvalidIndex($i0);
        return this.customVariables[$i0 + -1] == null;
    }

    public void setCustomVariable(CustomVariable $r1) throws  {
        throwOnInvalidIndex($r1.getIndex());
        this.customVariables[$r1.getIndex() - 1] = $r1;
    }
}
