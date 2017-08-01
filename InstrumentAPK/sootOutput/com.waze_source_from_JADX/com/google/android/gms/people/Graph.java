package com.google.android.gms.people;

import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.people.People.ReleasableResult;
import com.google.android.gms.people.PeopleConstants.PeopleColumnBitmask;
import com.google.android.gms.people.exp.ContactGaiaIdRawBuffer;
import com.google.android.gms.people.exp.PersonForAggregationRawBuffer;
import com.google.android.gms.people.internal.zzl;
import com.google.android.gms.people.model.AggregatedPersonBuffer;
import com.google.android.gms.people.model.CircleBuffer;
import com.google.android.gms.people.model.ContactGaiaIdBuffer;
import com.google.android.gms.people.model.OwnerBuffer;
import com.google.android.gms.people.model.PersonBuffer;
import com.google.android.gms.people.model.PhoneNumberBuffer;
import com.google.android.gms.people.protomodel.FetchBackUpDeviceContactInfoResponse;
import dalvik.annotation.Signature;
import java.util.Collection;

/* compiled from: dalvik_source_com.waze.apk */
public interface Graph {

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadOwnersResult extends ReleasableResult {
        OwnerBuffer getOwners() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadPhoneNumbersResult extends ReleasableResult {
        PhoneNumberBuffer getPhoneNumbers() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface FetchBackUpDeviceContactInfoResult extends Result {
        FetchBackUpDeviceContactInfoResponse getResponse() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadCirclesResult extends ReleasableResult {
        CircleBuffer getCircles() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadPeopleResult extends ReleasableResult {
        PersonBuffer getPeople() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadAggregatedPeopleResult extends ReleasableResult {
        AggregatedPersonBuffer getAggregatedPeople() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadPeopleForAggregationResult extends ReleasableResult {
        Bundle getEmailTypeMapBundle() throws ;

        ContactGaiaIdRawBuffer getGaiaMap() throws ;

        PersonForAggregationRawBuffer getPeople() throws ;

        Bundle getPhoneTypeMapBundle() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public interface LoadContactsGaiaIdsResult extends ReleasableResult {
        ContactGaiaIdBuffer getContactsGaiaIds() throws ;
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadAggregatedPeopleOptions {
        public static final LoadAggregatedPeopleOptions aLD = new LoadAggregatedPeopleOptions();
        private boolean aLE;
        private boolean aLF;
        private int aLG = PeopleColumnBitmask.ALL;
        private int aLH;
        private String aLI;
        private boolean aLJ;
        private int aLK = 7;
        private int aLL = 3;
        private int aLM = 0;
        private String zzaoj;

        public int getExtraColumns() throws  {
            return this.aLH;
        }

        public int getFilterGaiaEdgeTypes() throws  {
            return this.aLL;
        }

        public String getFilterGaiaId() throws  {
            return this.aLI;
        }

        public int getProjection() throws  {
            return this.aLG;
        }

        public String getQuery() throws  {
            return this.zzaoj;
        }

        public int getSearchFields() throws  {
            return this.aLK;
        }

        public int getSortOrder() throws  {
            return this.aLM;
        }

        public boolean isIncludeEvergreenPeople() throws  {
            return this.aLJ;
        }

        public boolean isIncludeInvisible() throws  {
            return this.aLE;
        }

        public boolean isPeopleOnly() throws  {
            return this.aLF;
        }

        public LoadAggregatedPeopleOptions setExtraColumns(int $i0) throws  {
            this.aLH = $i0;
            return this;
        }

        public LoadAggregatedPeopleOptions setFilterGaiaEdgeType(int $i0) throws  {
            this.aLL = $i0;
            return this;
        }

        public LoadAggregatedPeopleOptions setFilterGaiaId(String $r1) throws  {
            this.aLI = $r1;
            return this;
        }

        public LoadAggregatedPeopleOptions setIncludeEvergreenPeople(boolean $z0) throws  {
            this.aLJ = $z0;
            return this;
        }

        public LoadAggregatedPeopleOptions setIncludeInvisible(boolean $z0) throws  {
            this.aLE = $z0;
            return this;
        }

        public LoadAggregatedPeopleOptions setPeopleOnly(boolean $z0) throws  {
            this.aLF = $z0;
            return this;
        }

        public LoadAggregatedPeopleOptions setProjection(int $i0) throws  {
            this.aLG = $i0;
            return this;
        }

        public LoadAggregatedPeopleOptions setQuery(String $r1) throws  {
            this.zzaoj = $r1;
            return this;
        }

        public LoadAggregatedPeopleOptions setSearchFields(int $i0) throws  {
            this.aLK = $i0;
            return this;
        }

        public LoadAggregatedPeopleOptions setSortOrder(int $i0) throws  {
            this.aLM = $i0;
            return this;
        }

        public String toString() throws  {
            return zzl.zzd("mIncludeInvisible", Boolean.valueOf(this.aLE), "mQuery", this.zzaoj, "mPeopleOnly", Boolean.valueOf(this.aLF), "mProjection", Integer.valueOf(this.aLG), "mExtraColumns", Integer.valueOf(this.aLH), "mFilterGaiaId", this.aLI, "mIncludeEvergreenPeople", Boolean.valueOf(this.aLJ), "mSearchFields", Integer.valueOf(this.aLK), "mFilterGaiaEdgeTypes", Integer.valueOf(this.aLL), "mSortOrder", Integer.valueOf(this.aLM));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadCirclesOptions {
        public static final LoadCirclesOptions aLN = new LoadCirclesOptions();
        private int aLO = -999;
        private String aLP;
        private String aLQ;
        private boolean aLR;

        public String getFilterCircleId() throws  {
            return this.aLP;
        }

        public String getFilterCircleNamePrefix() throws  {
            return this.aLQ;
        }

        public int getFilterCircleType() throws  {
            return this.aLO;
        }

        public boolean isGetVisibility() throws  {
            return this.aLR;
        }

        public LoadCirclesOptions setFilterCircleId(String $r1) throws  {
            this.aLP = $r1;
            return this;
        }

        public LoadCirclesOptions setFilterCircleNamePrefix(String $r1) throws  {
            this.aLQ = $r1;
            return this;
        }

        public LoadCirclesOptions setFilterCircleType(int $i0) throws  {
            this.aLO = $i0;
            return this;
        }

        public LoadCirclesOptions setGetVisibility(boolean $z0) throws  {
            this.aLR = $z0;
            return this;
        }

        public String toString() throws  {
            return zzl.zzd("mFilterCircleType", Integer.valueOf(this.aLO), "mFilterCircleId", this.aLP, "mFilterCircleNamePrefix", this.aLQ, "mGetVisibility", Boolean.valueOf(this.aLR));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadContactsGaiaIdsOptions {
        public static final LoadContactsGaiaIdsOptions aLS = new LoadContactsGaiaIdsOptions();
        private String aLI;
        private int aLL = 3;
        private String aLT;

        public String getFilterContactInfo() throws  {
            return this.aLT;
        }

        public int getFilterGaiaEdgeTypes() throws  {
            return this.aLL;
        }

        public String getFilterGaiaId() throws  {
            return this.aLI;
        }

        public LoadContactsGaiaIdsOptions setFilterContactInfo(String $r1) throws  {
            this.aLT = $r1;
            return this;
        }

        public LoadContactsGaiaIdsOptions setFilterGaiaEdgeTypes(int $i0) throws  {
            this.aLL = $i0;
            return this;
        }

        public LoadContactsGaiaIdsOptions setFilterGaiaId(String $r1) throws  {
            this.aLI = $r1;
            return this;
        }

        public String toString() throws  {
            return zzl.zzd("mFilterContactInfo", this.aLT, "mFilterGaiaId", this.aLI, "mFilterGaiaEdgeTypes", Integer.valueOf(this.aLL));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadOwnersOptions {
        public static final LoadOwnersOptions aLU = new LoadOwnersOptions();
        private int aLM = 0;
        private boolean aLV;

        public int getSortOrder() throws  {
            return this.aLM;
        }

        public boolean isIncludePlusPages() throws  {
            return this.aLV;
        }

        public LoadOwnersOptions setIncludePlusPages(boolean $z0) throws  {
            this.aLV = $z0;
            return this;
        }

        public LoadOwnersOptions setSortOrder(int $i0) throws  {
            this.aLM = $i0;
            return this;
        }

        public String toString() throws  {
            return zzl.zzd("mIncludePlusPages", Boolean.valueOf(this.aLV), "mSortOrder", Integer.valueOf(this.aLM));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class LoadPeopleOptions {
        public static final LoadPeopleOptions aLW = new LoadPeopleOptions();
        private String KN;
        private boolean aLF;
        private int aLG = PeopleColumnBitmask.ALL;
        private int aLH;
        private int aLK = 7;
        private int aLM = 0;
        private Collection<String> aLX;
        private long aLY;
        private String zzaoj;

        public long getChangedSince() throws  {
            return this.aLY;
        }

        public String getCircleId() throws  {
            return this.KN;
        }

        public int getExtraColumns() throws  {
            return this.aLH;
        }

        public int getProjection() throws  {
            return this.aLG;
        }

        public Collection<String> getQualifiedIds() throws  {
            return this.aLX;
        }

        public String getQuery() throws  {
            return this.zzaoj;
        }

        public int getSearchFields() throws  {
            return this.aLK;
        }

        public int getSortOrder() throws  {
            return this.aLM;
        }

        public boolean isPeopleOnly() throws  {
            return this.aLF;
        }

        public LoadPeopleOptions setChangedSince(long $l0) throws  {
            this.aLY = $l0;
            return this;
        }

        public LoadPeopleOptions setCircleId(String $r1) throws  {
            this.KN = $r1;
            return this;
        }

        public LoadPeopleOptions setExtraColumns(int $i0) throws  {
            this.aLH = $i0;
            return this;
        }

        public LoadPeopleOptions setPeopleOnly(boolean $z0) throws  {
            this.aLF = $z0;
            return this;
        }

        public LoadPeopleOptions setProjection(int $i0) throws  {
            this.aLG = $i0;
            return this;
        }

        public LoadPeopleOptions setQualifiedIds(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;)", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;"}) Collection<String> $r1) throws  {
            this.aLX = $r1;
            return this;
        }

        public LoadPeopleOptions setQuery(String $r1) throws  {
            this.zzaoj = $r1;
            return this;
        }

        public LoadPeopleOptions setSearchFields(int $i0) throws  {
            this.aLK = $i0;
            return this;
        }

        public LoadPeopleOptions setSortOrder(int $i0) throws  {
            this.aLM = $i0;
            return this;
        }

        public String toString() throws  {
            return zzl.zzd("mCircleId", this.KN, "mQualifiedIds", this.aLX, "mProjection", Integer.valueOf(this.aLG), "mPeopleOnly", Boolean.valueOf(this.aLF), "mChangedSince", Long.valueOf(this.aLY), "mQuery", this.zzaoj, "mSearchFields", Integer.valueOf(this.aLK), "mSortOrder", Integer.valueOf(this.aLM), "mExtraColumns", Integer.valueOf(this.aLH));
        }
    }

    PendingResult<LoadPeopleForAggregationResult> expLoadPeopleForAggregation(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleForAggregationResult;", ">;"}) LoadAggregatedPeopleOptions loadAggregatedPeopleOptions) throws ;

    PendingResult<FetchBackUpDeviceContactInfoResult> fetchBackUpDeviceContactInfo(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$FetchBackUpDeviceContactInfoResult;", ">;"}) String str2) throws ;

    @RequiresPermission("android.permission.READ_CONTACTS")
    PendingResult<LoadAggregatedPeopleResult> loadAggregatedPeople(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadAggregatedPeopleResult;", ">;"}) LoadAggregatedPeopleOptions loadAggregatedPeopleOptions) throws ;

    PendingResult<LoadCirclesResult> loadCircles(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadCirclesOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadCirclesResult;", ">;"}) LoadCirclesOptions loadCirclesOptions) throws ;

    PendingResult<LoadContactsGaiaIdsResult> loadContactsGaiaIds(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadContactsGaiaIdsResult;", ">;"}) LoadContactsGaiaIdsOptions loadContactsGaiaIdsOptions) throws ;

    PendingResult<LoadOwnersResult> loadOwner(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) String str2) throws ;

    PendingResult<LoadOwnersResult> loadOwners(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadOwnersOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Lcom/google/android/gms/people/Graph$LoadOwnersOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadOwnersResult;", ">;"}) LoadOwnersOptions loadOwnersOptions) throws ;

    PendingResult<LoadPeopleResult> loadPeople(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "Lcom/google/android/gms/people/Graph$LoadPeopleOptions;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPeopleResult;", ">;"}) LoadPeopleOptions loadPeopleOptions) throws ;

    PendingResult<LoadPhoneNumbersResult> loadPhoneNumbers(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Landroid/os/Bundle;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/people/Graph$LoadPhoneNumbersResult;", ">;"}) Bundle bundle) throws ;

    PendingResult<Result> restoreBackedUpDeviceContacts(@Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) GoogleApiClient googleApiClient, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String str, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String str2, @Signature({"(", "Lcom/google/android/gms/common/api/GoogleApiClient;", "Ljava/lang/String;", "Ljava/lang/String;", "[", "Ljava/lang/String;", ")", "Lcom/google/android/gms/common/api/PendingResult", "<", "Lcom/google/android/gms/common/api/Result;", ">;"}) String[] strArr) throws ;
}
