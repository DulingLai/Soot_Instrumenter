package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolPayee implements Parcelable {
    public static final Creator<CarpoolPayee> CREATOR = new C14911();
    public String address_country;
    public String currency_code;
    public int paid_balance;
    public String payout_account_name;
    public int unpaid_balance;

    static class C14911 implements Creator<CarpoolPayee> {
        C14911() throws  {
        }

        public CarpoolPayee createFromParcel(Parcel $r1) throws  {
            return new CarpoolPayee($r1);
        }

        public CarpoolPayee[] newArray(int $i0) throws  {
            return new CarpoolPayee[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolPayee(Parcel $r1) throws  {
        this.payout_account_name = $r1.readString();
        this.unpaid_balance = $r1.readInt();
        this.paid_balance = $r1.readInt();
        this.address_country = $r1.readString();
        this.currency_code = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.payout_account_name);
        $r1.writeInt(this.unpaid_balance);
        $r1.writeInt(this.paid_balance);
        $r1.writeString(this.address_country);
        $r1.writeString(this.currency_code);
    }
}
