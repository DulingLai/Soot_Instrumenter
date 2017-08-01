package com.google.android.gms.people.protomodel;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;

/* compiled from: dalvik_source_com.waze.apk */
public interface SourceStats extends Parcelable, Freezable<SourceStats> {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private Integer aUJ;
        private String zzcun;

        public Builder(SourceStats $r1) throws  {
            this.zzcun = $r1.getSource();
            this.aUJ = $r1.getNumContacts();
        }

        public SourceStats build() throws  {
            return new SourceStatsEntity(this.zzcun, this.aUJ, true);
        }

        public Builder setNumContacts(Integer $r1) throws  {
            this.aUJ = $r1;
            return this;
        }

        public Builder setSource(String $r1) throws  {
            this.zzcun = $r1;
            return this;
        }
    }

    Integer getNumContacts() throws ;

    String getSource() throws ;
}
