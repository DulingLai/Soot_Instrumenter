package com.google.i18n.phonenumbers;

import com.google.i18n.phonenumbers.Phonemetadata.PhoneMetadata;

interface MetadataSource {
    PhoneMetadata getMetadataForNonGeographicalRegion(int i) throws ;

    PhoneMetadata getMetadataForRegion(String str) throws ;
}
