package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;

public class UserExtendedInfo implements Parcelable {
    public static final Creator<UserExtendedInfo> CREATOR = new C16931();
    int[] endorsement_count;

    static class C16931 implements Creator<UserExtendedInfo> {
        C16931() throws  {
        }

        public UserExtendedInfo createFromParcel(Parcel $r1) throws  {
            return new UserExtendedInfo($r1);
        }

        public UserExtendedInfo[] newArray(int $i0) throws  {
            return new UserExtendedInfo[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public boolean hasEndorsements() throws  {
        for (int $i0 = 1; $i0 < 6; $i0++) {
            if (this.endorsement_count[$i0] > 0) {
                return true;
            }
        }
        return false;
    }

    public int nextEndorsement(int $i0) throws  {
        do {
            $i0++;
            if ($i0 >= this.endorsement_count.length) {
                break;
            }
        } while (this.endorsement_count[$i0] == 0);
        return $i0 == this.endorsement_count.length ? -1 : $i0;
    }

    protected UserExtendedInfo(Parcel $r1) throws  {
        this.endorsement_count = $r1.createIntArray();
    }

    public void writeToParcel(@NonNull Parcel $r1, int flags) throws  {
        $r1.writeIntArray(this.endorsement_count);
    }
}
