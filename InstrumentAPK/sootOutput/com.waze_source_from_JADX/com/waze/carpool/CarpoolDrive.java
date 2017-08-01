package com.waze.carpool;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.strings.DisplayStrings;
import java.util.ArrayList;

public class CarpoolDrive implements Parcelable {
    public static final Creator<CarpoolDrive> CREATOR = new C13951();
    String countryCode;
    public CarpoolDriveMatchInfo drive_match_info;
    CarpoolRide[] former_rides;
    CarpoolDriveItinerary itinerary;
    CarpoolRide[] rides;
    int state;
    int type;
    String uuid;

    static class C13951 implements Creator<CarpoolDrive> {
        C13951() throws  {
        }

        public CarpoolDrive createFromParcel(Parcel $r1) throws  {
            return new CarpoolDrive($r1);
        }

        public CarpoolDrive[] newArray(int $i0) throws  {
            return new CarpoolDrive[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    protected CarpoolDrive(Parcel $r1) throws  {
        this.uuid = $r1.readString();
        this.itinerary = (CarpoolDriveItinerary) $r1.readParcelable(CarpoolDriveItinerary.class.getClassLoader());
        this.drive_match_info = (CarpoolDriveMatchInfo) $r1.readParcelable(CarpoolDriveMatchInfo.class.getClassLoader());
        this.state = $r1.readInt();
        this.type = $r1.readInt();
        this.rides = (CarpoolRide[]) $r1.createTypedArray(CarpoolRide.CREATOR);
        this.countryCode = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeString(this.uuid);
        $r1.writeParcelable(this.itinerary, $i0);
        $r1.writeParcelable(this.drive_match_info, $i0);
        $r1.writeInt(this.state);
        $r1.writeInt(this.type);
        $r1.writeTypedArray(this.rides, $i0);
        $r1.writeString(this.countryCode);
    }

    public long getTime() throws  {
        if (this.drive_match_info != null && this.drive_match_info.pickup_time_seconds != 0) {
            return this.drive_match_info.pickup_time_seconds;
        }
        if (this.itinerary.window_start_sec != 0) {
            return this.itinerary.window_start_sec;
        }
        CarpoolRide $r1 = getRide();
        if ($r1 != null) {
            return $r1.itinerary.window_start_time != 0 ? $r1.itinerary.window_start_time : 0;
        } else {
            return 0;
        }
    }

    public boolean isRealTimeRide() throws  {
        return this.type == 1;
    }

    public boolean isLive() throws  {
        return CarpoolNativeManager.getInstance().isDriveLiveNTV(getId());
    }

    public boolean isUpcoming() throws  {
        return CarpoolNativeManager.getInstance().isDriveUpcomingNTV(getId());
    }

    public boolean isEmpty() throws  {
        return (this.uuid == null || this.uuid.isEmpty()) && (this.rides == null || this.rides.length == 0);
    }

    public int getState(CarpoolRide $r1) throws  {
        if ($r1 == null) {
            $r1 = getRide();
        }
        if ($r1 == null || $r1.state == 0) {
            return this.state != 0 ? this.state : 0;
        } else {
            return $r1.state;
        }
    }

    public String getId() throws  {
        return this.uuid;
    }

    public String getRewardString(Context $r1) throws  {
        int $i0 = this.drive_match_info.total_fee_minor_units;
        if ($i0 == 0) {
            $i0 = this.drive_match_info.total_fee_minor_units;
        }
        if (this.itinerary.free_offer) {
            $i0 = 0;
        }
        String $r4 = this.countryCode;
        if ($r4 == null || $r4.isEmpty()) {
            CarpoolLocation $r5 = getPickupLocation();
            if ($r5 != null) {
                $r4 = $r5.country_code;
            }
        }
        return CarpoolNativeManager.getInstance().centsToString($r1, $i0, $r4, null);
    }

    public String getOriginalRewardString(Context $r1) throws  {
        return this.drive_match_info.reference_total_fee_minor_units == 0 ? null : CarpoolNativeManager.getInstance().centsToString($r1, this.drive_match_info.reference_total_fee_minor_units, this.countryCode, null);
    }

    public boolean hasParentDrive() throws  {
        if (this.rides != null) {
            return this.rides.length == 0 ? false : this.rides[0].hasDrive();
        } else {
            return false;
        }
    }

    public boolean isParentDrive() throws  {
        return ((this.rides != null && this.rides.length != 0) || this.uuid == null || this.uuid.isEmpty()) ? false : true;
    }

    public String getParentDriveId() throws  {
        return (this.rides == null || this.rides.length == 0) ? null : this.rides[0].getDriveId();
    }

    public long getSortTime(CarpoolDrive $r1) throws  {
        if (this.rides == null || this.rides.length == 0) {
            return this.itinerary.window_start_sec;
        }
        return this.rides[0].getSortTime($r1);
    }

    public CarpoolLocation getPickupLocation() throws  {
        return this.drive_match_info == null ? null : this.drive_match_info.pickup;
    }

    public CarpoolLocation getDropOffLocation() throws  {
        return this.drive_match_info == null ? null : this.drive_match_info.dropoff;
    }

    public String getPickupString() throws  {
        CarpoolLocation $r1 = getPickupLocation();
        CarpoolLocation $r2 = $r1;
        if ($r1 == null || !$r1.isValid()) {
            $r2 = getRide().getPickupLocation();
        }
        if ($r2 == null || !$r2.isValid()) {
            return null;
        }
        String $r4 = $r2.address;
        return ($r4 == null || $r4.isEmpty()) ? $r2.placeName : $r4;
    }

    public String getDropOffString() throws  {
        CarpoolLocation $r1 = getDropOffLocation();
        CarpoolLocation $r2 = $r1;
        if ($r1 == null || !$r1.isValid()) {
            $r2 = getRide().getDropOffLocation();
        }
        if ($r2 == null || !$r2.isValid()) {
            return null;
        }
        String $r4 = $r2.address;
        return ($r4 == null || $r4.isEmpty()) ? $r2.placeName : $r4;
    }

    public boolean hasRider() throws  {
        if (this.rides == null) {
            return false;
        }
        if (this.rides.length == 0) {
            return false;
        }
        return this.rides[0].rider != null;
    }

    public String getRiderName() throws  {
        if (isMultiPax()) {
            return DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        }
        if (!hasRider() || getRiderFirstName().isEmpty()) {
            return DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        }
        return getRiderFirstName();
    }

    public String getRiderFirstName() throws  {
        if (this.rides == null || this.rides.length == 0) {
            return DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        }
        if (this.rides[0].rider == null) {
            return DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_UNKNOWN_RIDER);
        }
        return this.rides[0].rider.getFirstName();
    }

    public String getRiderImage() throws  {
        if (this.rides == null || this.rides.length == 0) {
            return "";
        }
        if (this.rides[0].rider == null) {
            return "";
        }
        return this.rides[0].rider.getImage();
    }

    public ArrayList<String> getRidersImages() throws  {
        ArrayList $r1 = new ArrayList();
        if (this.rides == null || this.rides.length == 0) {
            return $r1;
        }
        for (CarpoolRide $r2 : this.rides) {
            if (!($r2.rider == null || $r2.rider.getImage() == null || $r2.rider.getImage().isEmpty())) {
                $r1.add($r2.rider.getImage());
            }
        }
        return $r1;
    }

    public CarpoolRide getRide() throws  {
        if (this.rides == null || this.rides.length <= 0) {
            return (this.former_rides == null || this.former_rides.length <= 0) ? null : this.former_rides[0];
        } else {
            return this.rides[0];
        }
    }

    public int getLiveRideState() throws  {
        if (this.rides == null) {
            return 0;
        }
        if (this.rides.length == 0) {
            return 0;
        }
        return this.rides[0].rider != null ? this.rides[0].getState() : 0;
    }

    public CarpoolRide getRide(int $i0) throws  {
        return (this.rides == null || this.rides.length <= $i0) ? null : this.rides[$i0];
    }

    public CarpoolRide getRideByRideId(String $r1) throws  {
        if (this.rides == null || this.rides.length == 0 || $r1 == null || $r1.isEmpty()) {
            Logger.m36d("CarpoolDrive: getRideByRideId: no rides or ride id is null");
            return null;
        }
        int $i0 = 0;
        while ($i0 < this.rides.length) {
            if (this.rides[$i0] == null || this.rides[$i0].getId() == null || !this.rides[$i0].getId().equalsIgnoreCase($r1)) {
                Logger.m36d("CarpoolDrive: getRideByRideId: ride number " + $i0 + " id " + this.rides[$i0].getId() + " NE " + $r1);
                $i0++;
            } else {
                Logger.m36d("CarpoolDrive: getRideByRideId: returning ride number " + $i0);
                return this.rides[$i0];
            }
        }
        return null;
    }

    public CarpoolUserData getRider() throws  {
        CarpoolRide $r1 = getRide();
        return $r1 == null ? null : $r1.rider;
    }

    public CarpoolUserData getRider(int $i0) throws  {
        CarpoolRide $r1 = getRide($i0);
        return $r1 == null ? null : $r1.rider;
    }

    public boolean hasTimeZone() throws  {
        if (this.rides == null) {
            return false;
        }
        if (this.rides.length == 0) {
            return false;
        }
        if (this.rides[0].itinerary != null) {
            return this.rides[0].itinerary.time_zone_id != null;
        } else {
            return false;
        }
    }

    public String timeZone() throws  {
        if (this.rides == null || this.rides.length == 0) {
            return "";
        }
        if (this.rides[0].itinerary == null) {
            return "";
        }
        return this.rides[0].itinerary.time_zone_id;
    }

    public CarpoolLocation getDestination() throws  {
        return this.itinerary == null ? null : this.itinerary.destination;
    }

    public int getDestinationType() throws  {
        CarpoolLocation $r1 = getDestination();
        return $r1 == null ? 0 : $r1.getAddressItemType();
    }

    public String getDestinationString() throws  {
        CarpoolLocation $r1 = getDestination();
        if ($r1 == null) {
            return null;
        }
        String $r2 = $r1.placeName;
        return ($r2 == null || $r2.isEmpty()) ? $r1.address : $r2;
    }

    public CarpoolLocation getOrigin() throws  {
        return this.itinerary == null ? null : this.itinerary.origin;
    }

    public int getOriginType() throws  {
        CarpoolLocation $r1 = getOrigin();
        return $r1 == null ? 0 : $r1.getAddressItemType();
    }

    public String getOriginString() throws  {
        CarpoolLocation $r1 = getOrigin();
        if ($r1 == null) {
            return null;
        }
        String $r2 = $r1.placeName;
        return ($r2 == null || $r2.isEmpty()) ? $r1.address : $r2;
    }

    public int getDetourMinutes() throws  {
        return (this.drive_match_info.detour_duration_seconds + 59) / 60;
    }

    public String getDetourString(int $i0) throws  {
        return String.format(NativeManager.getInstance().getLanguageString($i0), new Object[]{Integer.valueOf(getDetourMinutes())});
    }

    public boolean isMultiPax() throws  {
        return this.rides != null && this.rides.length > 1;
    }

    public int getRidesAmount() throws  {
        return this.rides != null ? this.rides.length : 0;
    }

    public CarpoolRide[] getRides() throws  {
        return this.rides;
    }

    public String getSomeRideId() throws  {
        CarpoolRide $r1 = getRide();
        if ($r1 != null) {
            return $r1.getId();
        }
        return null;
    }

    public int getRiderIdx(String $r1) throws  {
        if (this.rides == null) {
            return 0;
        }
        for (int $i0 = 0; $i0 < this.rides.length; $i0++) {
            if (this.rides[$i0].rider.getId() == $r1) {
                return $i0 + 1;
            }
        }
        return 0;
    }

    public String getCountryCode() throws  {
        CarpoolRide $r1 = getRide();
        return $r1 == null ? null : $r1.getPickupLocation().country_code;
    }

    public boolean hasId() throws  {
        return (this.uuid == null || this.uuid.isEmpty()) ? false : true;
    }

    public CarpoolRide getRideById(String $r1) throws  {
        if ($r1 == null || $r1.isEmpty()) {
            return null;
        }
        if (this.rides != null) {
            for (CarpoolRide $r2 : this.rides) {
                if ($r1.equals($r2.getId())) {
                    return $r2;
                }
            }
        }
        if (this.former_rides != null) {
            for (CarpoolRide $r22 : this.former_rides) {
                if ($r1.equals($r22.getId())) {
                    return $r22;
                }
            }
        }
        return null;
    }

    public boolean isCancelled() throws  {
        return this.state == 3;
    }

    public boolean isRequest() throws  {
        if (isMultiPax()) {
            return false;
        }
        CarpoolRide $r1 = getRide();
        if ($r1 != null) {
            return $r1.isPending();
        } else {
            return false;
        }
    }
}
