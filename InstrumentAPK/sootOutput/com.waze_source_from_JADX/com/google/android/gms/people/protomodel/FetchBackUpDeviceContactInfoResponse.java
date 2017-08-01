package com.google.android.gms.people.protomodel;

import android.os.Parcelable;
import com.google.android.gms.common.data.Freezable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public interface FetchBackUpDeviceContactInfoResponse extends Parcelable, Freezable<FetchBackUpDeviceContactInfoResponse> {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private List<BackedUpContactsPerDevice> aUH;

        public Builder(FetchBackUpDeviceContactInfoResponse $r1) throws  {
            this.aUH = $r1.getContactsPerDevice() == null ? null : new ArrayList($r1.getContactsPerDevice());
        }

        public Builder addContactsPerDevice(BackedUpContactsPerDevice... $r1) throws  {
            if (this.aUH == null) {
                this.aUH = new ArrayList();
            }
            for (BackedUpContactsPerDevice $r5 : $r1) {
                if ($r5 != null) {
                    this.aUH.add((BackedUpContactsPerDevice) $r5.freeze());
                }
            }
            return this;
        }

        public FetchBackUpDeviceContactInfoResponse build() throws  {
            return new FetchBackUpDeviceContactInfoResponseEntity(this.aUH, true);
        }
    }

    List<BackedUpContactsPerDevice> getContactsPerDevice() throws ;
}
