package com.google.android.gms.people;

import android.os.ParcelFileDescriptor;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.data.Freezable;
import com.google.android.gms.people.People.ReleasableResult;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.model.AutocompleteBuffer;
import com.google.android.gms.people.model.ContactGroupPreferredFieldsBuffer;
import dalvik.annotation.Signature;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* compiled from: dalvik_source_com.waze.apk */
public interface Autocomplete {
    public static final int CLIENT_ID_ANDROID_UPDATING_MERGED_CACHE = 3;
    public static final int CLIENT_ID_CONTACTS_PLUS = 1;
    public static final int CLIENT_ID_DEFAULT = 0;
    public static final int CLIENT_ID_GMAIL_ANDROID_COMPOSE = 2;
    public static final int FIELD_TYPE_PERSON_EMAIL = 2;
    public static final int FIELD_TYPE_PERSON_NAME = 1;
    public static final int FIELD_TYPE_PERSON_NICKNAME = 3;
    public static final int FIELD_TYPE_PERSON_ORGANIZATION_DOMAIN = 7;
    public static final int FIELD_TYPE_PERSON_ORGANIZATION_NAME = 6;
    public static final int FIELD_TYPE_PERSON_PHONE = 4;
    public static final int FIELD_TYPE_PERSON_PHONE_CANONICALIZED_FORM = 5;
    public static final int FIELD_TYPE_UNSPECIFIED = 0;
    public static final int OBJECT_TYPE_COMMUNITY = 3;
    public static final int OBJECT_TYPE_CONTACT_GROUP = 5;
    public static final int OBJECT_TYPE_GOOGLE_GROUP = 4;
    public static final int OBJECT_TYPE_PERSON = 1;
    public static final int OBJECT_TYPE_PLUS_PAGE = 2;
    public static final int OBJECT_TYPE_UNSPECIFIED = 0;

    /* compiled from: dalvik_source_com.waze.apk */
    public interface AutocompleteResult extends ReleasableResult {
        AutocompleteBuffer getAutocompleteEntries() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class AutocompleteOptions {
        public static final int DEFAULT_NUMBER_OF_RESULTS = 10;
        public final String account;
        public final int autocompleteType;
        public final String directoryAccountType;
        public final boolean isDirectorySearch;
        public final int numberOfResults;
        public final String pageId;
        public final int searchOptions;
        public final boolean useAndroidContactFallback;

        /* compiled from: dalvik_source_com.waze.apk */
        public static final class Builder {
            private String aLw = "com.google";
            private int aLx = 0;
            private int aLy = 10;
            private boolean aLz = true;
            private String mAccount;

            public AutocompleteOptions build() throws  {
                return new AutocompleteOptions();
            }

            public Builder setAccount(String $r1) throws  {
                this.mAccount = $r1;
                return this;
            }

            public Builder setAutocompleteType(int $i0) throws  {
                this.aLx = $i0;
                return this;
            }

            public Builder setNumberOfResults(int $i0) throws  {
                this.aLy = $i0;
                return this;
            }

            public Builder setUseAndroidContactFallback(boolean $z0) throws  {
                this.aLz = $z0;
                return this;
            }
        }

        private AutocompleteOptions(Builder $r1) throws  {
            this.isDirectorySearch = false;
            this.directoryAccountType = $r1.aLw;
            this.account = $r1.mAccount;
            this.pageId = null;
            this.autocompleteType = $r1.aLx;
            this.searchOptions = 0;
            this.numberOfResults = $r1.aLy;
            this.useAndroidContactFallback = $r1.aLz;
        }

        public String toString() throws  {
            return zzl.zzd("isDirectorySearch", Boolean.valueOf(this.isDirectorySearch), "directoryAccountType", this.directoryAccountType, "account", this.account, "pageId", this.pageId, "autocompleteType", Integer.valueOf(this.autocompleteType), "searchOptions", Integer.valueOf(this.searchOptions), "numberOfResults", Integer.valueOf(this.numberOfResults), "useAndroidContactFallback", Boolean.valueOf(this.useAndroidContactFallback));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface AutocompleteSession {
        @RequiresPermission("android.permission.READ_CONTACTS")
        void adjustQuery(GoogleApiClient googleApiClient, String str) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        void close(GoogleApiClient googleApiClient) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        PendingResult<PreferredFieldsResult> getAllPreferredFields(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$ContactGroup;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$ContactGroup;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$PreferredFieldsResult;", ">;"}) ContactGroup contactGroup) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        PendingResult<LoadPhotoResult> loadPrimaryPhoto(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$Person;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$Person;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) Person person, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Autocomplete$Person;", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$LoadPhotoResult;", ">;"}) LoadPhotoOptions loadPhotoOptions) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        void reportDisplay(GoogleApiClient googleApiClient, Autocompletion autocompletion) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        void reportSelection(GoogleApiClient googleApiClient, Autocompletion autocompletion) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        void reportSubmissionSave(GoogleApiClient googleApiClient, Autocompletion autocompletion, String[] strArr) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        void reportSubmissionSend(GoogleApiClient googleApiClient, Autocompletion autocompletion, String[] strArr) throws ;

        @RequiresPermission("android.permission.READ_CONTACTS")
        void startNewQuery(GoogleApiClient googleApiClient) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Autocompletion {
        ContactGroup getContactGroup() throws ;

        DisplayableField[] getMatches() throws ;

        int getObjectType() throws ;

        Person getPerson() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface AutocompletionListener {
        @RequiresPermission("android.permission.READ_CONTACTS")
        void onAutocompletionsAvailable(Autocompletion[] autocompletionArr, int i, int i2) throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class ClientConfig {
        public final int clientId;

        public ClientConfig(int $i0) throws  {
            this.clientId = $i0;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: dalvik_source_com.waze.apk */
    public @interface ClientId {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ContactGroup {
        GroupExtendedData getExtendedData() throws ;

        ContactGroupId getId() throws ;

        int getMemberCount() throws ;

        ContactGroupName getName() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ContactGroupId {
        String getId() throws ;

        String[] getLegacyId() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ContactGroupName {
        CharSequence getFormattedValue() throws ;

        CharSequence getValue() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface ContactPreferredFields extends Freezable<ContactPreferredFields> {
        CharSequence getEmail() throws ;

        CharSequence getName() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface DisplayableField {
        int getIndex() throws ;

        Substring[] getMatchingSubstrings() throws ;

        int getType() throws ;

        CharSequence getValue() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Email {
        CharSequence getValue() throws ;
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: dalvik_source_com.waze.apk */
    public @interface FieldType {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface GroupExtendedData {
        ContactPreferredFields[] getContactPreferences() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class LoadPhotoOptions {
        private final int aLA;
        private final int aLB;

        public LoadPhotoOptions(int $i0, int $i1) throws  {
            this.aLA = $i0;
            this.aLB = $i1;
        }

        public int getImageSize() throws  {
            return this.aLA;
        }

        public int getPhotoOptions() throws  {
            return this.aLB;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadPhotoResult implements ReleasableResult {
        public static final LoadPhotoResult FAILED_RESULT = new LoadPhotoResult(new Status(13), null, false, 0, 0);
        final ParcelFileDescriptor HG;
        final boolean aLC;
        final Status cp;
        final int zzaiq;
        final int zzair;

        public LoadPhotoResult(Status $r1, ParcelFileDescriptor $r2, boolean $z0, int $i0, int $i1) throws  {
            this.cp = $r1;
            this.HG = $r2;
            this.aLC = $z0;
            this.zzaiq = $i0;
            this.zzair = $i1;
        }

        public int getHeight() throws  {
            return this.zzair;
        }

        public ParcelFileDescriptor getParcelFileDescriptor() throws  {
            return this.HG;
        }

        public Status getStatus() throws  {
            return this.cp;
        }

        public int getWidth() throws  {
            return this.zzaiq;
        }

        public boolean isRewindable() throws  {
            return this.aLC;
        }

        public void release() throws  {
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Name {
        CharSequence getDisplayName() throws ;
    }

    @Retention(RetentionPolicy.SOURCE)
    /* compiled from: dalvik_source_com.waze.apk */
    public @interface ObjectType {
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Person {
        Email[] getEmails() throws ;

        PersonMetadata getMetadata() throws ;

        Name[] getNames() throws ;

        Phone[] getPhones() throws ;

        Photo[] getPhotos() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PersonMetadata {
        String getOwnerId() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Phone {
        CharSequence getValue() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Photo {
        boolean isDefault() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface PreferredFieldsResult extends ReleasableResult {
        ContactGroupPreferredFieldsBuffer getPreferredFields() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface Substring {
        int getLength() throws ;

        int getStartIndex() throws ;
    }

    @RequiresPermission("android.permission.READ_CONTACTS")
    AutocompleteSession beginAutocompleteSession(GoogleApiClient googleApiClient, ClientConfig clientConfig, String str, AutocompletionListener autocompletionListener) throws ;

    @RequiresPermission("android.permission.READ_CONTACTS")
    PendingResult<AutocompleteResult> loadAutocompleteList(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Autocomplete$AutocompleteOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Autocomplete$AutocompleteResult;", ">;"}) AutocompleteOptions autocompleteOptions) throws ;
}
