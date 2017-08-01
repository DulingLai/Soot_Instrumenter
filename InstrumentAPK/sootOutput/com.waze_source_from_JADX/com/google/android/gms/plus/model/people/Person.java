package com.google.android.gms.plus.model.people;

import com.google.android.gms.common.data.Freezable;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public interface Person extends Freezable<Person> {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface AgeRange extends Freezable<AgeRange> {
        int getMax() throws ;

        int getMin() throws ;

        boolean hasMax() throws ;

        boolean hasMin() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Cover extends Freezable<Cover> {

        /* compiled from: dalvik_source_com.waze.apk */
        public interface CoverInfo extends Freezable<CoverInfo> {
            int getLeftImageOffset() throws ;

            int getTopImageOffset() throws ;

            boolean hasLeftImageOffset() throws ;

            boolean hasTopImageOffset() throws ;
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public interface CoverPhoto extends Freezable<CoverPhoto> {
            int getHeight() throws ;

            String getUrl() throws ;

            int getWidth() throws ;

            boolean hasHeight() throws ;

            boolean hasUrl() throws ;

            boolean hasWidth() throws ;
        }

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Layout {
            public static final int BANNER = 0;

            private Layout() throws  {
            }
        }

        CoverInfo getCoverInfo() throws ;

        CoverPhoto getCoverPhoto() throws ;

        int getLayout() throws ;

        boolean hasCoverInfo() throws ;

        boolean hasCoverPhoto() throws ;

        boolean hasLayout() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Image extends Freezable<Image> {
        String getUrl() throws ;

        boolean hasUrl() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Name extends Freezable<Name> {
        String getFamilyName() throws ;

        String getFormatted() throws ;

        String getGivenName() throws ;

        String getHonorificPrefix() throws ;

        String getHonorificSuffix() throws ;

        String getMiddleName() throws ;

        boolean hasFamilyName() throws ;

        boolean hasFormatted() throws ;

        boolean hasGivenName() throws ;

        boolean hasHonorificPrefix() throws ;

        boolean hasHonorificSuffix() throws ;

        boolean hasMiddleName() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Organizations extends Freezable<Organizations> {

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Type {
            public static final int SCHOOL = 1;
            public static final int WORK = 0;

            private Type() throws  {
            }
        }

        String getDepartment() throws ;

        String getDescription() throws ;

        String getEndDate() throws ;

        String getLocation() throws ;

        String getName() throws ;

        String getStartDate() throws ;

        String getTitle() throws ;

        int getType() throws ;

        boolean hasDepartment() throws ;

        boolean hasDescription() throws ;

        boolean hasEndDate() throws ;

        boolean hasLocation() throws ;

        boolean hasName() throws ;

        boolean hasPrimary() throws ;

        boolean hasStartDate() throws ;

        boolean hasTitle() throws ;

        boolean hasType() throws ;

        boolean isPrimary() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PlacesLived extends Freezable<PlacesLived> {
        String getValue() throws ;

        boolean hasPrimary() throws ;

        boolean hasValue() throws ;

        boolean isPrimary() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Urls extends Freezable<Urls> {

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Type {
            public static final int CONTRIBUTOR = 6;
            public static final int OTHER = 4;
            public static final int OTHER_PROFILE = 5;
            public static final int WEBSITE = 7;

            private Type() throws  {
            }
        }

        String getLabel() throws ;

        int getType() throws ;

        String getValue() throws ;

        boolean hasLabel() throws ;

        boolean hasType() throws ;

        boolean hasValue() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class Gender {
        public static final int FEMALE = 1;
        public static final int MALE = 0;
        public static final int OTHER = 2;

        private Gender() throws  {
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class ObjectType {
        public static final int PAGE = 1;
        public static final int PERSON = 0;

        private ObjectType() throws  {
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class RelationshipStatus {
        public static final int ENGAGED = 2;
        public static final int IN_A_RELATIONSHIP = 1;
        public static final int IN_CIVIL_UNION = 8;
        public static final int IN_DOMESTIC_PARTNERSHIP = 7;
        public static final int ITS_COMPLICATED = 4;
        public static final int MARRIED = 3;
        public static final int OPEN_RELATIONSHIP = 5;
        public static final int SINGLE = 0;
        public static final int WIDOWED = 6;

        private RelationshipStatus() throws  {
        }
    }

    String getAboutMe() throws ;

    AgeRange getAgeRange() throws ;

    String getBirthday() throws ;

    String getBraggingRights() throws ;

    int getCircledByCount() throws ;

    Cover getCover() throws ;

    String getCurrentLocation() throws ;

    String getDisplayName() throws ;

    int getGender() throws ;

    String getId() throws ;

    Image getImage() throws ;

    String getLanguage() throws ;

    Name getName() throws ;

    String getNickname() throws ;

    int getObjectType() throws ;

    List<Organizations> getOrganizations() throws ;

    List<PlacesLived> getPlacesLived() throws ;

    int getPlusOneCount() throws ;

    int getRelationshipStatus() throws ;

    String getTagline() throws ;

    String getUrl() throws ;

    List<Urls> getUrls() throws ;

    boolean hasAboutMe() throws ;

    boolean hasAgeRange() throws ;

    boolean hasBirthday() throws ;

    boolean hasBraggingRights() throws ;

    boolean hasCircledByCount() throws ;

    boolean hasCover() throws ;

    boolean hasCurrentLocation() throws ;

    boolean hasDisplayName() throws ;

    boolean hasGender() throws ;

    boolean hasId() throws ;

    boolean hasImage() throws ;

    boolean hasIsPlusUser() throws ;

    boolean hasLanguage() throws ;

    boolean hasName() throws ;

    boolean hasNickname() throws ;

    boolean hasObjectType() throws ;

    boolean hasOrganizations() throws ;

    boolean hasPlacesLived() throws ;

    boolean hasPlusOneCount() throws ;

    boolean hasRelationshipStatus() throws ;

    boolean hasTagline() throws ;

    boolean hasUrl() throws ;

    boolean hasUrls() throws ;

    boolean hasVerified() throws ;

    boolean isPlusUser() throws ;

    boolean isVerified() throws ;
}
