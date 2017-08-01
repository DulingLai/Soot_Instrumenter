package com.waze.messages;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.AppService;
import com.waze.carpool.CarpoolRide;
import com.waze.carpool.CarpoolUserData;
import com.waze.location.Position;
import dalvik.annotation.Signature;
import java.util.HashMap;
import java.util.Map;

public class QuestionData implements Parcelable {
    public static final Creator<QuestionData> CREATOR = new C19741();
    public static final String DESTINATION_LAT = "dest_lat";
    public static final String DESTINATION_LON = "dest_lon";
    public static final String DESTINATION_NAME = "dest_name";
    public static final String DESTINATION_VENUE_ID = "dest_venueId";
    public String ActionText1 = null;
    public String ActionText2 = null;
    public String ActionText3 = null;
    public int AnswerType;
    public int BackgroundRGB1 = 0;
    public int BackgroundRGB2 = 0;
    public int BackgroundRGB3 = 0;
    public int ButtonOrientation = 0;
    public boolean HasDriver;
    public String Icon1 = null;
    public String Icon2 = null;
    public String Icon3 = null;
    public String IdText1 = null;
    public String IdText2 = null;
    public String IdText3 = null;
    public String ImageUrl = null;
    public String LinkHtml1 = null;
    public String LinkHtml2 = null;
    public String LinkTitle1 = null;
    public String LinkTitle2 = null;
    public String LinkUrl1 = null;
    public String LinkUrl2 = null;
    public String MessageID = null;
    public String NotificationID = null;
    public String QuestionID = null;
    public String SubText1 = null;
    public String SubText2 = null;
    public String SubText3 = null;
    public String Subtitle = null;
    public String Text = null;
    public int TextRGB1 = 0;
    public int TextRGB2 = 0;
    public int TextRGB3 = 0;
    public String defaultAction = null;
    public CarpoolRide ride = null;
    public String rideId = null;
    public CarpoolUserData rider = null;

    static class C19741 implements Creator<QuestionData> {
        C19741() throws  {
        }

        public QuestionData createFromParcel(Parcel $r1) throws  {
            return new QuestionData($r1);
        }

        public QuestionData[] newArray(int $i0) throws  {
            return new QuestionData[$i0];
        }
    }

    public static class ParkingPosition extends Position {
        public int parkingTime;
    }

    public int describeContents() throws  {
        return 0;
    }

    public static String ReadKeyData(String $r0, Context $r1) throws  {
        return $r1.getSharedPreferences("com.waze.Keys", 0).getString($r0, "");
    }

    public static void SaveQuestionData(QuestionData $r0) throws  {
        SharedPreferences $r2 = AppService.getAppContext().getSharedPreferences("com.waze.question", 0);
        $r2.edit().putInt("Type", $r0.AnswerType).apply();
        if ($r0.Text != null) {
            $r2.edit().putString("Text", $r0.Text).apply();
        } else {
            $r2.edit().putString("Text", "").apply();
        }
        if ($r0.QuestionID != null) {
            $r2.edit().putString("QuestionID", $r0.QuestionID).apply();
        } else {
            $r2.edit().putString("QuestionID", "").apply();
        }
        if ($r0.SubText1 != null) {
            $r2.edit().putString("SubText1", $r0.SubText1).apply();
            $r2.edit().putString("ActionText1", $r0.ActionText1).apply();
            $r2.edit().putString("IdText1", $r0.IdText1).apply();
        } else {
            $r2.edit().putString("SubText1", "").apply();
            $r2.edit().putString("ActionText1", "").apply();
            $r2.edit().putString("IdText1", "").apply();
        }
        if ($r0.SubText2 != null) {
            $r2.edit().putString("SubText2", $r0.SubText2).apply();
            $r2.edit().putString("ActionText2", $r0.ActionText2).apply();
            $r2.edit().putString("IdText2", $r0.IdText2).apply();
        } else {
            $r2.edit().putString("SubText2", "").apply();
            $r2.edit().putString("ActionText2", "").apply();
            $r2.edit().putString("IdText2", "").apply();
        }
        $r2.edit().commit();
    }

    public static QuestionData GetQuestionData(Context $r0) throws  {
        SharedPreferences $r2 = $r0.getSharedPreferences("com.waze.question", 0);
        QuestionData $r1 = new QuestionData();
        $r1.Text = $r2.getString("Text", "");
        $r1.SubText1 = $r2.getString("SubText1", "");
        $r1.IdText1 = $r2.getString("IdText1", "");
        $r1.IdText2 = $r2.getString("IdText2", "");
        $r1.QuestionID = $r2.getString("QuestionID", "");
        $r1.SubText2 = $r2.getString("SubText2", "");
        $r1.ActionText1 = $r2.getString("ActionText1", "");
        $r1.ActionText2 = $r2.getString("ActionText2", "");
        $r1.AnswerType = $r2.getInt("Type", -1);
        return $r1;
    }

    public static void ResetQuestionData(Context $r0) throws  {
        SharedPreferences $r1 = $r0.getSharedPreferences("com.waze.question", 0);
        $r1.edit().putString("Text", "").apply();
        $r1.edit().putString("SubText1", "").apply();
        $r1.edit().putString("ActionText1", "").apply();
        $r1.edit().putString("SubText2", "").apply();
        $r1.edit().putString("ActionText2", "").apply();
        $r1.edit().putInt("Type", -1).apply();
        $r1.edit().putString("IdText1", "").apply();
        $r1.edit().putString("IdText2", "").apply();
        $r1.edit().putString("QuestionID", "").apply();
        $r1.edit().commit();
    }

    public static void SetGeoFencingWakeUpFlag(Context $r0, int $i0) throws  {
        $r0.getSharedPreferences("com.waze.geofencing", 0).edit().putInt("GeofencingWakeUp", $i0).apply();
    }

    public static int GetGeoFencingWakeUpFlag() throws  {
        return AppService.getAppContext().getSharedPreferences("com.waze.geofencing", 0).getInt("GeofencingWakeUp", -1);
    }

    public static void SetParking(Context $r0, double $d0, double $d1, int $i0, String $r1) throws  {
        if ($r1 == null || $r1.isEmpty()) {
            $r1 = "com.waze.parked";
        } else {
            $r1 = "com.waze.parked." + $r1;
        }
        SharedPreferences $r2 = $r0.getSharedPreferences($r1, 0);
        $r2.edit().putString("lon", Double.toString($d0)).apply();
        $r2.edit().putString("lat", Double.toString($d1)).apply();
        $r2.edit().putString(WLTypes.VEHICLEDATA_ATTRIBUTE_TIME, Integer.toString($i0)).apply();
        $r2.edit().commit();
    }

    public static void SetLocationData(Context $r0, double $d0, double $d1, String $r1, String $r2) throws  {
        Editor $r4 = $r0.getSharedPreferences("com.waze.parked", 0).edit();
        $r4.putString(DESTINATION_LON, String.valueOf($d0));
        $r4.putString(DESTINATION_LAT, String.valueOf($d1));
        $r4.putString(DESTINATION_NAME, $r1);
        $r4.putString(DESTINATION_VENUE_ID, $r2);
        $r4.commit();
    }

    public static void ResetLocationData(Context $r0) throws  {
        Editor $r4 = $r0.getSharedPreferences("com.waze.parked", 0).edit();
        for (String $r1 : new String[]{DESTINATION_LON, DESTINATION_LAT, DESTINATION_NAME, DESTINATION_VENUE_ID}) {
            $r4.remove($r1);
        }
        $r4.commit();
    }

    public static Map<String, String> GetLocationData(@Signature({"(", "Landroid/content/Context;", ")", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;"}) Context $r0) throws  {
        SharedPreferences $r4 = $r0.getSharedPreferences("com.waze.parked", 0);
        HashMap $r3 = new HashMap();
        for (String $r1 : new String[]{DESTINATION_LON, DESTINATION_LAT, DESTINATION_NAME, DESTINATION_VENUE_ID}) {
            $r3.put($r1, $r4.getString($r1, ""));
        }
        return $r3;
    }

    public static ParkingPosition ReadParking(Context $r0, String $r1) throws  {
        if ($r1 == null || $r1.isEmpty()) {
            $r1 = "com.waze.parked";
        } else {
            $r1 = "com.waze.parked." + $r1;
        }
        SharedPreferences $r3 = $r0.getSharedPreferences($r1, 0);
        ParkingPosition $r2 = new ParkingPosition();
        $r2.longitude = Double.parseDouble($r3.getString("lon", "-1"));
        $r2.latitude = Double.parseDouble($r3.getString("lat", "-1"));
        $r2.parkingTime = Integer.parseInt($r3.getString(WLTypes.VEHICLEDATA_ATTRIBUTE_TIME, "-1"));
        return $r2;
    }

    public static void ClearParking(Context $r0) throws  {
        for (String $r2 : new String[]{"com.waze.parked", "com.waze.parked.available", "com.waze.parked.gpsLock"}) {
            SharedPreferences $r3 = $r0.getSharedPreferences($r2, 0);
            $r3.edit().putString("lon", "-1").apply();
            $r3.edit().putString("lat", "-1").apply();
            $r3.edit().putString(WLTypes.VEHICLEDATA_ATTRIBUTE_TIME, "-1").apply();
            $r3.edit().commit();
        }
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeString(this.Text);
        $r1.writeString(this.defaultAction);
        $r1.writeString(this.SubText1);
        $r1.writeString(this.SubText2);
        $r1.writeString(this.ActionText1);
        $r1.writeString(this.ActionText2);
        $r1.writeString(this.QuestionID);
        $r1.writeString(this.IdText1);
        $r1.writeString(this.IdText2);
        $r1.writeInt(this.AnswerType);
        $r1.writeString(this.NotificationID);
        $r1.writeString(this.MessageID);
        $r1.writeByte((byte) (this.HasDriver));
        $r1.writeString(this.rideId);
        $r1.writeParcelable(this.ride, $i0);
        $r1.writeParcelable(this.rider, $i0);
    }

    public QuestionData(Parcel $r1) throws  {
        boolean $z0 = false;
        this.Text = $r1.readString();
        this.defaultAction = $r1.readString();
        this.SubText1 = $r1.readString();
        this.SubText2 = $r1.readString();
        this.ActionText1 = $r1.readString();
        this.ActionText2 = $r1.readString();
        this.QuestionID = $r1.readString();
        this.IdText1 = $r1.readString();
        this.IdText2 = $r1.readString();
        this.AnswerType = $r1.readInt();
        this.NotificationID = $r1.readString();
        this.MessageID = $r1.readString();
        if ($r1.readByte() != (byte) 0) {
            $z0 = true;
        }
        this.HasDriver = $z0;
        this.rideId = $r1.readString();
        this.ride = (CarpoolRide) $r1.readParcelable(CarpoolRide.class.getClassLoader());
        this.rider = (CarpoolUserData) $r1.readParcelable(CarpoolUserData.class.getClassLoader());
    }
}
