package android.support.v4.media.session;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ParcelableVolumeInfo implements Parcelable {
    public static final Creator<ParcelableVolumeInfo> CREATOR = new C00921();
    public int audioStream;
    public int controlType;
    public int currentVolume;
    public int maxVolume;
    public int volumeType;

    static class C00921 implements Creator<ParcelableVolumeInfo> {
        C00921() throws  {
        }

        public ParcelableVolumeInfo createFromParcel(Parcel $r1) throws  {
            return new ParcelableVolumeInfo($r1);
        }

        public ParcelableVolumeInfo[] newArray(int $i0) throws  {
            return new ParcelableVolumeInfo[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public ParcelableVolumeInfo(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
        this.volumeType = $i0;
        this.audioStream = $i1;
        this.controlType = $i2;
        this.maxVolume = $i3;
        this.currentVolume = $i4;
    }

    public ParcelableVolumeInfo(Parcel $r1) throws  {
        this.volumeType = $r1.readInt();
        this.controlType = $r1.readInt();
        this.maxVolume = $r1.readInt();
        this.currentVolume = $r1.readInt();
        this.audioStream = $r1.readInt();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeInt(this.volumeType);
        $r1.writeInt(this.controlType);
        $r1.writeInt(this.maxVolume);
        $r1.writeInt(this.currentVolume);
        $r1.writeInt(this.audioStream);
    }
}
