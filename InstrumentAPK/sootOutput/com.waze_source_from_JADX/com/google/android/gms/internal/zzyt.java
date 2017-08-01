package com.google.android.gms.internal;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzyt {
    public String aML;
    public String aNi;
    public String aNj;
    public String mimeType;

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        $r1.append("DataKind<");
        $r1.append(" resourcePackageName=").append(this.aML);
        $r1.append(" mimeType=").append(this.mimeType);
        $r1.append(" summaryColumn=").append(this.aNi);
        $r1.append(" detailColumn=").append(this.aNj);
        $r1.append(">");
        return $r1.toString();
    }
}
