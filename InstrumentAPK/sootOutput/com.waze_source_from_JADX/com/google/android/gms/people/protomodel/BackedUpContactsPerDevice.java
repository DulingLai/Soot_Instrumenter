package com.google.android.gms.people.protomodel;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public interface BackedUpContactsPerDevice extends Parcelable, Freezable<BackedUpContactsPerDevice> {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private List<SourceStats> aUE;
        private String aUF;
        private String qy;

        public Builder(BackedUpContactsPerDevice $r1) throws  {
            this.qy = $r1.getDeviceId();
            this.aUE = $r1.getSourceStats() == null ? null : new ArrayList($r1.getSourceStats());
            this.aUF = $r1.getDeviceDisplayName();
        }

        public Builder addSourceStats(SourceStats... $r1) throws  {
            if (this.aUE == null) {
                this.aUE = new ArrayList();
            }
            for (SourceStats $r5 : $r1) {
                if ($r5 != null) {
                    this.aUE.add((SourceStats) $r5.freeze());
                }
            }
            return this;
        }

        public BackedUpContactsPerDevice build() throws  {
            return new BackedUpContactsPerDeviceEntity(this.qy, this.aUE, this.aUF, true);
        }

        public Builder setDeviceDisplayName(String $r1) throws  {
            this.aUF = $r1;
            return this;
        }

        public Builder setDeviceId(String $r1) throws  {
            this.qy = $r1;
            return this;
        }
    }

    String getDeviceDisplayName() throws ;

    String getDeviceId() throws ;

    List<SourceStats> getSourceStats() throws ;
}
