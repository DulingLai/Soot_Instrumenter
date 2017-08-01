package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class CarpoolCommuteDay implements Parcelable, Cloneable {
    public static final Creator<CarpoolCommuteDay> CREATOR = new C13941();
    int toHomeEnd;
    int toHomeStart;
    int toWorkEnd;
    int toWorkStart;

    static class C13941 implements Creator<CarpoolCommuteDay> {
        C13941() throws  {
        }

        public CarpoolCommuteDay createFromParcel(Parcel $r1) throws  {
            return new CarpoolCommuteDay($r1);
        }

        public CarpoolCommuteDay[] newArray(int $i0) throws  {
            return new CarpoolCommuteDay[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public CarpoolCommuteDay clone() throws  {
        CarpoolCommuteDay $r1 = new CarpoolCommuteDay();
        $r1.toWorkStart = this.toWorkStart;
        $r1.toWorkEnd = this.toWorkEnd;
        $r1.toHomeStart = this.toHomeStart;
        $r1.toHomeEnd = this.toHomeEnd;
        return $r1;
    }

    protected CarpoolCommuteDay(Parcel $r1) throws  {
        this.toWorkStart = $r1.readInt();
        this.toWorkEnd = $r1.readInt();
        this.toHomeStart = $r1.readInt();
        this.toHomeEnd = $r1.readInt();
    }

    boolean toWorkValid() throws  {
        return this.toWorkStart >= 0 && this.toWorkEnd >= 0;
    }

    boolean toHomeValid() throws  {
        return this.toHomeStart >= 0 && this.toHomeEnd >= 0;
    }

    public int hashCode() throws  {
        return (((this.toWorkStart * 17) * this.toWorkEnd) * this.toHomeStart) * this.toHomeEnd;
    }

    public boolean equals(Object $r1) throws  {
        if (!($r1 instanceof CarpoolCommuteDay)) {
            return false;
        }
        if ($r1 == this) {
            return true;
        }
        CarpoolCommuteDay $r2 = (CarpoolCommuteDay) $r1;
        return $r2.toWorkStart == this.toWorkStart && $r2.toWorkEnd == this.toWorkEnd && $r2.toHomeStart == this.toHomeStart && $r2.toHomeEnd == this.toHomeEnd;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeInt(this.toWorkStart);
        $r1.writeInt(this.toWorkEnd);
        $r1.writeInt(this.toHomeStart);
        $r1.writeInt(this.toHomeEnd);
    }
}
