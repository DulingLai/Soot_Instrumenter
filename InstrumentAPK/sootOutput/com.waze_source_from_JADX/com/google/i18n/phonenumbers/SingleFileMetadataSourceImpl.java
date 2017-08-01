package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import java.util.concurrent.atomic.AtomicReference;

final class SingleFileMetadataSourceImpl implements MetadataSource {
    private final MetadataLoader metadataLoader;
    private final String phoneNumberMetadataFileName;
    private final AtomicReference<SingleFileMetadataMaps> phoneNumberMetadataRef;

    SingleFileMetadataSourceImpl(String $r1, MetadataLoader $r2) throws  {
        this.phoneNumberMetadataRef = new AtomicReference();
        this.phoneNumberMetadataFileName = $r1;
        this.metadataLoader = $r2;
    }

    SingleFileMetadataSourceImpl(MetadataLoader $r1) throws  {
        this("/com/google/i18n/phonenumbers/data/SingleFilePhoneNumberMetadataProto", $r1);
    }

    public PhoneMetadata getMetadataForRegion(String $r1) throws  {
        return MetadataManager.getSingleFileMetadataMaps(this.phoneNumberMetadataRef, this.phoneNumberMetadataFileName, this.metadataLoader).get($r1);
    }

    public PhoneMetadata getMetadataForNonGeographicalRegion(int $i0) throws  {
        return MetadataManager.getSingleFileMetadataMaps(this.phoneNumberMetadataRef, this.phoneNumberMetadataFileName, this.metadataLoader).get($i0);
    }
}
