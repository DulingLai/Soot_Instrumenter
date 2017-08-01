package com.waze.reports;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.io.Serializable;

public class CandidateProduct implements Parcelable, Serializable {
    public static final Creator<CandidateProduct> CREATOR = new C24311();
    private static final long serialVersionUID = 1;
    public int lastUpdated;
    public float price;
    public String prodId;
    public String updatedBy;

    static class C24311 implements Creator<CandidateProduct> {
        C24311() {
        }

        public CandidateProduct createFromParcel(Parcel in) {
            return new CandidateProduct(in);
        }

        public CandidateProduct[] newArray(int size) {
            return new CandidateProduct[size];
        }
    }

    public CandidateProduct(Parcel in) {
        this.prodId = in.readString();
        this.price = in.readFloat();
        this.lastUpdated = in.readInt();
        this.updatedBy = in.readString();
    }

    public CandidateProduct(CandidateProduct orig) {
        this.prodId = new String(orig.prodId);
        this.price = orig.price;
        this.lastUpdated = orig.lastUpdated;
        this.updatedBy = new String(orig.updatedBy);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.prodId);
        dest.writeFloat(this.price);
        dest.writeInt(this.lastUpdated);
        dest.writeString(this.updatedBy);
    }
}
