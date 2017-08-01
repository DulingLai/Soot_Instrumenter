package com.google.android.gms.people.identity.models;

import android.os.Parcelable;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public interface Person extends Parcelable {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface MetadataHolder extends Parcelable {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Abouts extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Addresses extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Birthdays extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface BraggingRights extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface CoverPhotos extends Parcelable {
        ImageReference getImageReference() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface CustomFields extends Parcelable {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Emails extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Events extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Genders extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Images extends Parcelable, MetadataHolder {
        ImageReference getImageReference() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface InstantMessaging extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LegacyFields extends Parcelable {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Memberships extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Metadata extends Parcelable {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Names extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Nicknames extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Notes extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Occupations extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Organizations extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PersonMetadata extends Parcelable {
        ProfileOwnerStats getProfileOwnerStats() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PhoneNumbers extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PlacesLived extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ProfileOwnerStats extends Parcelable {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Relations extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface RelationshipInterests extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface RelationshipStatuses extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Skills extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface SortKeys extends Parcelable {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Taglines extends Parcelable, MetadataHolder {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Urls extends Parcelable, MetadataHolder {
    }

    List<? extends Abouts> getAbouts() throws ;

    List<? extends Addresses> getAddresses() throws ;

    List<? extends Birthdays> getBirthdays() throws ;

    List<? extends BraggingRights> getBraggingRights() throws ;

    List<? extends CoverPhotos> getCoverPhotos() throws ;

    List<? extends CustomFields> getCustomFields() throws ;

    List<? extends Emails> getEmails() throws ;

    List<? extends Events> getEvents() throws ;

    List<? extends Genders> getGenders() throws ;

    List<? extends Images> getImages() throws ;

    List<? extends InstantMessaging> getInstantMessaging() throws ;

    LegacyFields getLegacyFields() throws ;

    List<? extends Person> getLinkedPeople() throws ;

    List<? extends Memberships> getMemberships() throws ;

    PersonMetadata getMetadata() throws ;

    List<? extends Names> getNames() throws ;

    List<? extends Nicknames> getNicknames() throws ;

    List<? extends Notes> getNotes() throws ;

    List<? extends Occupations> getOccupations() throws ;

    List<? extends Organizations> getOrganizations() throws ;

    List<? extends PhoneNumbers> getPhoneNumbers() throws ;

    List<? extends PlacesLived> getPlacesLived() throws ;

    List<? extends Relations> getRelations() throws ;

    List<? extends RelationshipInterests> getRelationshipInterests() throws ;

    List<? extends RelationshipStatuses> getRelationshipStatuses() throws ;

    List<? extends Skills> getSkills() throws ;

    SortKeys getSortKeys() throws ;

    List<? extends Taglines> getTaglines() throws ;

    List<? extends Urls> getUrls() throws ;
}
