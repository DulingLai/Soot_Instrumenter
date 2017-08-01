package com.waze.reports;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.waze.MsgBox;
import com.waze.navigate.NavigateNativeManager;
import com.waze.strings.DisplayStrings;
import java.io.Serializable;

public class VenueData implements Parcelable, Serializable, Cloneable {
    public static final Creator<VenueData> CREATOR = new C25601();
    public static final int FlagRequest_Type_ARRAYSIZE = 11;
    public static final int FlagRequest_Type_CLOSED = 1;
    public static final int FlagRequest_Type_DOES_NOT_MATCH_SEARCH = 10;
    public static final int FlagRequest_Type_DUPLICATE = 4;
    public static final int FlagRequest_Type_INAPPROPRIATE = 5;
    public static final int FlagRequest_Type_LOW_QUALITY = 7;
    public static final int FlagRequest_Type_MOVED = 2;
    public static final int FlagRequest_Type_OTHER = 9;
    public static final int FlagRequest_Type_RESIDENTIAL = 3;
    public static final int FlagRequest_Type_UNRELATED = 8;
    public static final int FlagRequest_Type_WRONG_DETAILS = 6;
    public static int MAX_ARR_SIZE = 30;
    public static final String PARKING_LOT_CATEGORY = "PARKING_LOT";
    private static final long serialVersionUID = 1;
    public String RoutingContext;
    public String about;
    public String[] aliases;
    public boolean bHasMoreData;
    public boolean bResidence;
    public boolean bUpdateable;
    public String brand;
    public String[] categories;
    public String city;
    public String context;
    public String country;
    public String details;
    public String houseNumber;
    public String id;
    public boolean[] imageByMe;
    public boolean[] imageLiked;
    public String[] imageReporterMoods;
    public String[] imageReporters;
    public String[] imageThumbnailURLs;
    public String[] imageURLs;
    public int latitude;
    public int longitude;
    public String name;
    public String[] newImageIds;
    public int numAliases;
    public int numCategories;
    public int numImages;
    public int numNewImages;
    public int numOpeningHours;
    public int numProducts;
    public int numServices;
    public OpeningHours[] openingHours;
    public String phoneNumber;
    public CandidateProduct[] products;
    public String providerId;
    public String reporter;
    public String reporterMood;
    public String[] services;
    public String state;
    public String street;
    public String updater;
    public String updaterMood;
    public boolean wasLocationChanged;
    public String website;
    public String websiteDisplayText;
    public String zip;

    static class C25601 implements Creator<VenueData> {
        C25601() {
        }

        public VenueData createFromParcel(Parcel in) {
            return new VenueData(in);
        }

        public VenueData[] newArray(int size) {
            return new VenueData[size];
        }
    }

    public VenueData() {
        this.numImages = 0;
        this.numNewImages = 0;
        this.wasLocationChanged = false;
        this.numCategories = 0;
        this.numServices = 0;
        this.numOpeningHours = 0;
        this.numAliases = 0;
        this.numProducts = 0;
        this.imageURLs = new String[MAX_ARR_SIZE];
        this.imageThumbnailURLs = new String[MAX_ARR_SIZE];
        this.imageReporters = new String[MAX_ARR_SIZE];
        this.imageReporterMoods = new String[MAX_ARR_SIZE];
        this.imageLiked = new boolean[MAX_ARR_SIZE];
        this.imageByMe = new boolean[MAX_ARR_SIZE];
        this.newImageIds = new String[MAX_ARR_SIZE];
        this.categories = new String[MAX_ARR_SIZE];
        this.services = new String[MAX_ARR_SIZE];
        this.openingHours = new OpeningHours[MAX_ARR_SIZE];
        this.aliases = new String[MAX_ARR_SIZE];
        this.products = new CandidateProduct[MAX_ARR_SIZE];
    }

    public void addImage(String url, String thumbUrl, String reporter) {
        while (this.numImages >= MAX_ARR_SIZE) {
            removeImage(0);
        }
        this.imageURLs[this.numImages] = url;
        this.imageThumbnailURLs[this.numImages] = thumbUrl;
        this.imageReporters[this.numImages] = reporter;
        this.imageReporterMoods[this.numImages] = null;
        this.imageLiked[this.numImages] = false;
        this.imageByMe[this.numImages] = false;
        this.numImages++;
    }

    public void replaceImage(String mPhotoPath, String imageUrl, String imageThumbnailUrl) {
        if (mPhotoPath != null) {
            int i = 0;
            while (i < this.numImages) {
                if (this.imageURLs[i] == null || !this.imageURLs[i].contentEquals(mPhotoPath)) {
                    i++;
                } else {
                    this.imageURLs[i] = imageUrl;
                    this.imageThumbnailURLs[i] = imageThumbnailUrl;
                    this.imageLiked[i] = false;
                    this.imageByMe[i] = true;
                    return;
                }
            }
        }
    }

    public boolean removeImage(int position) {
        if (this.numImages <= position || position < 0) {
            return false;
        }
        for (int i = position + 1; i < this.numImages; i++) {
            this.imageURLs[i - 1] = this.imageURLs[i];
            this.imageThumbnailURLs[i - 1] = this.imageThumbnailURLs[i];
            this.imageReporters[i - 1] = this.imageReporters[i];
            this.imageReporterMoods[i - 1] = this.imageReporterMoods[i];
            this.imageLiked[i - 1] = this.imageLiked[i];
            this.imageByMe[i - 1] = this.imageByMe[i];
        }
        this.numImages--;
        this.imageURLs[this.numImages] = null;
        this.imageThumbnailURLs[this.numImages] = null;
        this.imageReporters[this.numImages] = null;
        this.imageReporterMoods[this.numImages] = null;
        this.imageLiked[this.numImages] = false;
        this.imageByMe[this.numImages] = false;
        return true;
    }

    public void addNewImageId(String id) {
        if (this.numNewImages != MAX_ARR_SIZE) {
            this.newImageIds[this.numNewImages] = id;
            this.numNewImages++;
        }
    }

    public boolean removeNewImageId(int position) {
        if (this.numNewImages <= position || position < 0) {
            return false;
        }
        for (int i = position + 1; i < this.numNewImages; i++) {
            this.newImageIds[i - 1] = this.newImageIds[i];
        }
        this.numNewImages--;
        this.newImageIds[this.numNewImages] = null;
        return true;
    }

    public void addCategory(String category) {
        if (category.equals(PARKING_LOT_CATEGORY) && this.numCategories > 0) {
            MsgBox.getInstance().OpenMessageBoxTimeoutCb(DisplayStrings.displayString(DisplayStrings.DS_PLACE_CANNOT_ADD_CATEGORY_TITLE), DisplayStrings.displayString(DisplayStrings.DS_PLACE_CANNOT_ADD_CATEGORY_BODY), -1, -1);
        } else if (this.numCategories != MAX_ARR_SIZE) {
            this.categories[this.numCategories] = category;
            this.numCategories++;
        }
    }

    public boolean removeCategory(String category) {
        boolean found = false;
        if (this.numCategories == 0) {
            return false;
        }
        for (int i = 0; i < this.numCategories; i++) {
            if (found) {
                this.categories[i - 1] = this.categories[i];
            } else if (category.equals(this.categories[i])) {
                found = true;
            }
        }
        if (found) {
            this.numCategories--;
            this.categories[this.numCategories] = null;
        }
        return found;
    }

    public void addOpHrs(OpeningHours opHr) {
        if (this.numOpeningHours != MAX_ARR_SIZE) {
            this.openingHours[this.numOpeningHours] = opHr;
            this.numOpeningHours++;
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeString(this.id);
        dest.writeString(this.name);
        dest.writeString(this.providerId);
        dest.writeInt(this.bUpdateable ? 1 : 0);
        if (this.bResidence) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeInt(i);
        dest.writeStringArray(this.imageURLs);
        dest.writeStringArray(this.imageThumbnailURLs);
        dest.writeStringArray(this.imageReporters);
        dest.writeStringArray(this.imageReporterMoods);
        dest.writeBooleanArray(this.imageLiked);
        dest.writeBooleanArray(this.imageByMe);
        dest.writeInt(this.numImages);
        dest.writeStringArray(this.newImageIds);
        dest.writeInt(this.numNewImages);
        dest.writeString(this.street);
        dest.writeString(this.houseNumber);
        dest.writeString(this.city);
        dest.writeInt(this.longitude);
        dest.writeInt(this.latitude);
        if (!this.wasLocationChanged) {
            i2 = 0;
        }
        dest.writeInt(i2);
        dest.writeStringArray(this.categories);
        dest.writeInt(this.numCategories);
        dest.writeStringArray(this.services);
        dest.writeInt(this.numServices);
        dest.writeTypedArray(this.openingHours, 0);
        dest.writeInt(this.numOpeningHours);
        dest.writeStringArray(this.aliases);
        dest.writeInt(this.numAliases);
        dest.writeTypedArray(this.products, 0);
        dest.writeInt(this.numProducts);
        dest.writeString(this.about);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.website);
        dest.writeString(this.websiteDisplayText);
        dest.writeString(this.context);
        dest.writeString(this.reporter);
        dest.writeString(this.reporterMood);
        dest.writeString(this.updater);
        dest.writeString(this.updaterMood);
    }

    public VenueData(Parcel in) {
        boolean z;
        boolean z2 = true;
        this();
        this.id = in.readString();
        this.name = in.readString();
        this.providerId = in.readString();
        this.bUpdateable = in.readInt() != 0;
        if (in.readInt() != 0) {
            z = true;
        } else {
            z = false;
        }
        this.bResidence = z;
        in.readStringArray(this.imageURLs);
        in.readStringArray(this.imageThumbnailURLs);
        in.readStringArray(this.imageReporters);
        in.readStringArray(this.imageReporterMoods);
        in.readBooleanArray(this.imageLiked);
        in.readBooleanArray(this.imageByMe);
        this.numImages = in.readInt();
        in.readStringArray(this.newImageIds);
        this.numNewImages = in.readInt();
        this.street = in.readString();
        this.houseNumber = in.readString();
        this.city = in.readString();
        this.longitude = in.readInt();
        this.latitude = in.readInt();
        if (in.readInt() == 0) {
            z2 = false;
        }
        this.wasLocationChanged = z2;
        in.readStringArray(this.categories);
        this.numCategories = in.readInt();
        in.readStringArray(this.services);
        this.numServices = in.readInt();
        in.readTypedArray(this.openingHours, OpeningHours.CREATOR);
        this.numOpeningHours = in.readInt();
        in.readStringArray(this.aliases);
        this.numAliases = in.readInt();
        in.readTypedArray(this.products, CandidateProduct.CREATOR);
        this.numProducts = in.readInt();
        this.about = in.readString();
        this.phoneNumber = in.readString();
        this.website = in.readString();
        this.websiteDisplayText = in.readString();
        this.context = in.readString();
        this.reporter = in.readString();
        this.reporterMood = in.readString();
        this.updater = in.readString();
        this.updaterMood = in.readString();
    }

    public String getAddressString() {
        StringBuilder bob = new StringBuilder();
        if (!(this.street == null || this.street.isEmpty())) {
            bob.append(this.street);
            if (!(this.houseNumber == null || this.houseNumber.isEmpty())) {
                bob.append(" ");
                bob.append(this.houseNumber);
            }
        }
        if (!(this.city == null || this.city.isEmpty())) {
            if (bob.length() > 0) {
                bob.append(", ");
            }
            bob.append(this.city);
        }
        return bob.toString();
    }

    private String cloneString(String src) {
        if (src == null) {
            return null;
        }
        return new String(src);
    }

    public VenueData clone() {
        int i;
        VenueData copy = new VenueData();
        copy.id = cloneString(this.id);
        copy.name = cloneString(this.name);
        copy.providerId = cloneString(this.providerId);
        copy.bUpdateable = this.bUpdateable;
        copy.bResidence = this.bResidence;
        copy.numImages = Math.min(copy.imageURLs.length, this.numImages);
        for (i = 0; i < copy.numImages; i++) {
            copy.imageURLs[i] = cloneString(this.imageURLs[i]);
            copy.imageThumbnailURLs[i] = cloneString(this.imageThumbnailURLs[i]);
            copy.imageReporters[i] = cloneString(this.imageReporters[i]);
            copy.imageReporterMoods[i] = cloneString(this.imageReporterMoods[i]);
            copy.imageLiked[i] = this.imageLiked[i];
            copy.imageByMe[i] = this.imageByMe[i];
        }
        copy.numNewImages = Math.min(copy.newImageIds.length, this.numNewImages);
        for (i = 0; i < copy.numNewImages; i++) {
            copy.newImageIds[i] = cloneString(this.newImageIds[i]);
        }
        copy.street = cloneString(this.street);
        copy.houseNumber = cloneString(this.houseNumber);
        copy.city = cloneString(this.city);
        copy.longitude = this.longitude;
        copy.latitude = this.latitude;
        copy.wasLocationChanged = this.wasLocationChanged;
        copy.numCategories = Math.min(copy.categories.length, this.numCategories);
        for (i = 0; i < copy.numCategories; i++) {
            copy.categories[i] = cloneString(this.categories[i]);
        }
        copy.numServices = Math.min(copy.services.length, this.numServices);
        for (i = 0; i < copy.numServices; i++) {
            copy.services[i] = cloneString(this.services[i]);
        }
        copy.numOpeningHours = Math.min(copy.openingHours.length, this.numOpeningHours);
        for (i = 0; i < copy.numOpeningHours; i++) {
            copy.openingHours[i] = new OpeningHours(this.openingHours[i]);
        }
        copy.numAliases = Math.min(copy.aliases.length, this.numAliases);
        for (i = 0; i < copy.numAliases; i++) {
            copy.aliases[i] = cloneString(this.aliases[i]);
        }
        copy.numProducts = Math.min(copy.products.length, this.numProducts);
        for (i = 0; i < copy.numProducts; i++) {
            copy.products[i] = new CandidateProduct(this.products[i]);
        }
        copy.about = cloneString(this.about);
        copy.phoneNumber = cloneString(this.phoneNumber);
        copy.website = cloneString(this.website);
        copy.websiteDisplayText = cloneString(this.websiteDisplayText);
        copy.context = cloneString(this.context);
        copy.reporter = cloneString(this.reporter);
        copy.reporterMood = cloneString(this.reporterMood);
        copy.updater = cloneString(this.updater);
        copy.updaterMood = cloneString(this.updaterMood);
        return copy;
    }

    public boolean isParkingCategory() {
        int i = 0;
        while (i < this.numCategories && i < this.categories.length) {
            String cat = this.categories[i];
            if (cat != null && NavigateNativeManager.instance().isParkingCategoryNTV(cat)) {
                return true;
            }
            i++;
        }
        return false;
    }
}
