package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

final class MultiFileMetadataSourceImpl implements MetadataSource {
    private final ConcurrentHashMap<String, PhoneMetadata> geographicalRegions;
    private final MetadataLoader metadataLoader;
    private final ConcurrentHashMap<Integer, PhoneMetadata> nonGeographicalRegions;
    private final String phoneNumberMetadataFilePrefix;

    MultiFileMetadataSourceImpl(String $r1, MetadataLoader $r2) throws  {
        this.geographicalRegions = new ConcurrentHashMap();
        this.nonGeographicalRegions = new ConcurrentHashMap();
        this.phoneNumberMetadataFilePrefix = $r1;
        this.metadataLoader = $r2;
    }

    MultiFileMetadataSourceImpl(MetadataLoader $r1) throws  {
        this("/com/google/i18n/phonenumbers/data/PhoneNumberMetadataProto", $r1);
    }

    public PhoneMetadata getMetadataForRegion(String $r1) throws  {
        return MetadataManager.getMetadataFromMultiFilePrefix($r1, this.geographicalRegions, this.phoneNumberMetadataFilePrefix, this.metadataLoader);
    }

    public PhoneMetadata getMetadataForNonGeographicalRegion(int $i0) throws  {
        return !isNonGeographical($i0) ? null : MetadataManager.getMetadataFromMultiFilePrefix(Integer.valueOf($i0), this.nonGeographicalRegions, this.phoneNumberMetadataFilePrefix, this.metadataLoader);
    }

    private boolean isNonGeographical(int $i0) throws  {
        List $r4 = (List) CountryCodeToRegionCodeMap.getCountryCodeToRegionCodeMap().get(Integer.valueOf($i0));
        return $r4.size() == 1 && PhoneNumberUtil.REGION_CODE_FOR_NON_GEO_ENTITY.equals($r4.get(0));
    }
}
