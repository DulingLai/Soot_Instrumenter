package com.waze.carpool;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;

public class CarpoolUserData implements Parcelable {
    public static final int ABUSE_STATUS_FLAGGED = 2;
    public static final int ABUSE_STATUS_MISMATCH = 3;
    public static final int ABUSE_STATUS_OK = 1;
    public static final int ABUSE_STATUS_PENDING = 0;
    public static final Creator<CarpoolUserData> CREATOR = new C16131();
    public static final int DEFAULT_TIMES_DAY = 7;
    public static final int NUM_COMMUTE_DAYS = 8;
    public static final int PROMPT_ADD_PAYMENT_ACCOUNT = 1;
    public static final int PROMPT_FIX_PAYMENT_ACCOUNT = 3;
    public static final int PROMPT_MIGRATE_PAYMENT_ACCOUNT = 2;
    public static final int PROMPT_NOT_RECEIVED = 99;
    public static final int PROMPT_PAYMENT_ACTION_UNSPECIFIED = 0;
    public int available_seats;
    boolean bank_account_updated;
    CarInfo car_info;
    CarpoolCommuteDay[] commute_days;
    int completed_rides_driver;
    int completed_rides_pax;
    boolean confirmed_credit_card;
    CarpoolUserSocialNetworks[] driver_social_networks;
    public String email;
    String family_name;
    String full_photo_url;
    String given_name;
    boolean has_inferred_commute;
    public boolean home_conflict;
    public boolean home_missing;
    public String id;
    public CarpoolLocation inferred_home;
    public CarpoolLocation inferred_home_other;
    public CarpoolLocation inferred_work;
    public CarpoolLocation inferred_work_other;
    boolean is_driver;
    long join_time_utc_seconds;
    String motto;
    String name;
    public int notification_frequency;
    String organization;
    String phone;
    int photoAbuseStatus;
    String photo_url;
    public int prompt_payment_action;
    CarpoolUserSocialNetworks[] rider_social_networks;
    float star_rating_as_driver;
    float star_rating_as_pax;
    int total_carpooled_meters_driver;
    int total_carpooled_meters_pax;
    long waze_join_date_sec;
    public boolean work_conflict;
    String work_email;
    boolean work_email_verified;
    public boolean work_missing;

    static class C16131 implements Creator<CarpoolUserData> {
        C16131() throws  {
        }

        public CarpoolUserData createFromParcel(Parcel $r1) throws  {
            return new CarpoolUserData($r1);
        }

        public CarpoolUserData[] newArray(int $i0) throws  {
            return new CarpoolUserData[$i0];
        }
    }

    protected CarpoolUserData(android.os.Parcel r30) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:32:0x0257
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r29 = this;
        r3 = 1;
        r0 = r29;
        r0.<init>();
        r5 = 2;
        r4 = new com.waze.carpool.CarpoolUserSocialNetworks[r5];
        r0 = r29;
        r0.rider_social_networks = r4;
        r5 = 2;
        r4 = new com.waze.carpool.CarpoolUserSocialNetworks[r5];
        r0 = r29;
        r0.driver_social_networks = r4;
        r5 = 8;
        r6 = new com.waze.carpool.CarpoolCommuteDay[r5];
        r0 = r29;
        r0.commute_days = r6;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.id = r7;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.email = r7;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.name = r7;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.given_name = r7;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.family_name = r7;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.photo_url = r7;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.full_photo_url = r7;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.phone = r7;
        r8 = com.waze.carpool.CarInfo.class;
        r9 = r8.getClassLoader();
        r0 = r30;
        r10 = r0.readParcelable(r9);
        r12 = r10;
        r12 = (com.waze.carpool.CarInfo) r12;
        r11 = r12;
        r0 = r29;
        r0.car_info = r11;
        r13 = com.waze.carpool.CarpoolUserSocialNetworks.CREATOR;
        r0 = r30;
        r14 = r0.createTypedArray(r13);
        r15 = r14;
        r15 = (com.waze.carpool.CarpoolUserSocialNetworks[]) r15;
        r4 = r15;
        r0 = r29;
        r0.rider_social_networks = r4;
        r13 = com.waze.carpool.CarpoolUserSocialNetworks.CREATOR;
        r0 = r30;
        r14 = r0.createTypedArray(r13);
        r16 = r14;
        r16 = (com.waze.carpool.CarpoolUserSocialNetworks[]) r16;
        r4 = r16;
        r0 = r29;
        r0.driver_social_networks = r4;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x025b;
    L_0x00aa:
        r18 = 1;
    L_0x00ac:
        r0 = r18;
        r1 = r29;
        r1.confirmed_credit_card = r0;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.organization = r7;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x0262;
    L_0x00c4:
        r18 = 1;
    L_0x00c6:
        r0 = r18;
        r1 = r29;
        r1.work_email_verified = r0;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.work_email = r7;
        r0 = r30;
        r19 = r0.readFloat();
        r0 = r19;
        r1 = r29;
        r1.star_rating_as_driver = r0;
        r0 = r30;
        r19 = r0.readFloat();
        r0 = r19;
        r1 = r29;
        r1.star_rating_as_pax = r0;
        r0 = r30;
        r20 = r0.readInt();
        r0 = r20;
        r1 = r29;
        r1.completed_rides_pax = r0;
        r0 = r30;
        r20 = r0.readInt();
        r0 = r20;
        r1 = r29;
        r1.completed_rides_driver = r0;
        r0 = r30;
        r20 = r0.readInt();
        r0 = r20;
        r1 = r29;
        r1.total_carpooled_meters_pax = r0;
        r0 = r30;
        r20 = r0.readInt();
        r0 = r20;
        r1 = r29;
        r1.total_carpooled_meters_driver = r0;
        r0 = r30;
        r21 = r0.readLong();
        r0 = r21;
        r2 = r29;
        r2.join_time_utc_seconds = r0;
        r0 = r30;
        r21 = r0.readLong();
        r0 = r21;
        r2 = r29;
        r2.waze_join_date_sec = r0;
        r0 = r30;
        r7 = r0.readString();
        r0 = r29;
        r0.motto = r7;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x0269;
    L_0x0148:
        r18 = 1;
    L_0x014a:
        r0 = r18;
        r1 = r29;
        r1.bank_account_updated = r0;
        r0 = r30;
        r20 = r0.readInt();
        r0 = r20;
        r1 = r29;
        r1.photoAbuseStatus = r0;
        r0 = r30;
        r20 = r0.readInt();
        r0 = r20;
        r1 = r29;
        r1.available_seats = r0;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x026c;
    L_0x0170:
        r18 = 1;
    L_0x0172:
        r0 = r18;
        r1 = r29;
        r1.has_inferred_commute = r0;
        r8 = com.waze.carpool.CarpoolLocation.class;
        r9 = r8.getClassLoader();
        r0 = r30;
        r10 = r0.readParcelable(r9);
        r24 = r10;
        r24 = (com.waze.carpool.CarpoolLocation) r24;
        r23 = r24;
        r0 = r23;
        r1 = r29;
        r1.inferred_home = r0;
        r8 = com.waze.carpool.CarpoolLocation.class;
        r9 = r8.getClassLoader();
        r0 = r30;
        r10 = r0.readParcelable(r9);
        r25 = r10;
        r25 = (com.waze.carpool.CarpoolLocation) r25;
        r23 = r25;
        r0 = r23;
        r1 = r29;
        r1.inferred_work = r0;
        r8 = com.waze.carpool.CarpoolLocation.class;
        r9 = r8.getClassLoader();
        r0 = r30;
        r10 = r0.readParcelable(r9);
        r26 = r10;
        r26 = (com.waze.carpool.CarpoolLocation) r26;
        r23 = r26;
        r0 = r23;
        r1 = r29;
        r1.inferred_home_other = r0;
        r8 = com.waze.carpool.CarpoolLocation.class;
        r9 = r8.getClassLoader();
        r0 = r30;
        r10 = r0.readParcelable(r9);
        r27 = r10;
        r27 = (com.waze.carpool.CarpoolLocation) r27;
        r23 = r27;
        r0 = r23;
        r1 = r29;
        r1.inferred_work_other = r0;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x026f;
    L_0x01e0:
        r18 = 1;
    L_0x01e2:
        r0 = r18;
        r1 = r29;
        r1.is_driver = r0;
        r0 = r30;
        r20 = r0.readInt();
        goto L_0x01f2;
    L_0x01ef:
        goto L_0x0172;
    L_0x01f2:
        r0 = r20;
        r1 = r29;
        r1.notification_frequency = r0;
        r13 = com.waze.carpool.CarpoolCommuteDay.CREATOR;
        r0 = r30;
        r14 = r0.createTypedArray(r13);
        r28 = r14;
        r28 = (com.waze.carpool.CarpoolCommuteDay[]) r28;
        r6 = r28;
        r0 = r29;
        r0.commute_days = r6;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x0272;
    L_0x0212:
        r18 = 1;
    L_0x0214:
        r0 = r18;
        r1 = r29;
        r1.home_missing = r0;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x0275;
    L_0x0222:
        r18 = 1;
    L_0x0224:
        r0 = r18;
        r1 = r29;
        r1.work_missing = r0;
        goto L_0x022e;
    L_0x022b:
        goto L_0x01e2;
    L_0x022e:
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x0278;
    L_0x0236:
        r18 = 1;
    L_0x0238:
        r0 = r18;
        r1 = r29;
        r1.home_conflict = r0;
        r0 = r30;
        r17 = r0.readByte();
        if (r17 == 0) goto L_0x027b;
    L_0x0246:
        r0 = r29;
        r0.work_conflict = r3;
        r0 = r30;
        r20 = r0.readInt();
        r0 = r20;
        r1 = r29;
        r1.prompt_payment_action = r0;
        return;
        goto L_0x025b;
    L_0x0258:
        goto L_0x00ac;
    L_0x025b:
        r18 = 0;
        goto L_0x0258;
        goto L_0x0262;
    L_0x025f:
        goto L_0x00c6;
    L_0x0262:
        r18 = 0;
        goto L_0x025f;
        goto L_0x0269;
    L_0x0266:
        goto L_0x014a;
    L_0x0269:
        r18 = 0;
        goto L_0x0266;
    L_0x026c:
        r18 = 0;
        goto L_0x01ef;
    L_0x026f:
        r18 = 0;
        goto L_0x022b;
    L_0x0272:
        r18 = 0;
        goto L_0x0214;
    L_0x0275:
        r18 = 0;
        goto L_0x0224;
    L_0x0278:
        r18 = 0;
        goto L_0x0238;
    L_0x027b:
        r3 = 0;
        goto L_0x0246;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolUserData.<init>(android.os.Parcel):void");
    }

    public int describeContents() throws  {
        return 0;
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        boolean $z1;
        boolean $z0 = true;
        $r1.writeString(this.id);
        $r1.writeString(this.email);
        $r1.writeString(this.name);
        $r1.writeString(this.given_name);
        $r1.writeString(this.family_name);
        $r1.writeString(this.photo_url);
        $r1.writeString(this.full_photo_url);
        $r1.writeString(this.phone);
        $r1.writeParcelable(this.car_info, $i0);
        $r1.writeTypedArray(this.rider_social_networks, $i0);
        $r1.writeTypedArray(this.driver_social_networks, $i0);
        $r1.writeByte((byte) (this.confirmed_credit_card));
        $r1.writeString(this.organization);
        if (this.work_email_verified) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r1.writeByte((byte) $z1);
        $r1.writeString(this.work_email);
        $r1.writeFloat(this.star_rating_as_driver);
        $r1.writeFloat(this.star_rating_as_pax);
        $r1.writeInt(this.completed_rides_pax);
        $r1.writeInt(this.completed_rides_driver);
        $r1.writeInt(this.total_carpooled_meters_pax);
        $r1.writeInt(this.total_carpooled_meters_driver);
        $r1.writeLong(this.join_time_utc_seconds);
        $r1.writeLong(this.waze_join_date_sec);
        $r1.writeString(this.motto);
        $r1.writeByte((byte) (this.bank_account_updated));
        $r1.writeInt(this.photoAbuseStatus);
        $r1.writeInt(this.available_seats);
        if (this.has_inferred_commute) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r1.writeByte((byte) $z1);
        $r1.writeParcelable(this.inferred_home, $i0);
        $r1.writeParcelable(this.inferred_work, $i0);
        $r1.writeParcelable(this.inferred_home_other, $i0);
        $r1.writeParcelable(this.inferred_work_other, $i0);
        if (this.is_driver) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r1.writeByte((byte) $z1);
        $r1.writeInt(this.notification_frequency);
        $r1.writeTypedArray(this.commute_days, $i0);
        if (this.home_missing) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r1.writeByte((byte) $z1);
        if (this.work_missing) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r1.writeByte((byte) $z1);
        if (this.home_conflict) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        $r1.writeByte((byte) $z1);
        if (!this.work_conflict) {
            $z0 = false;
        }
        $r1.writeByte((byte) $z0);
        $r1.writeInt(this.prompt_payment_action);
    }

    public boolean inferredHomeConflict() throws  {
        return (!this.home_conflict || this.inferred_home == null || this.inferred_home_other == null) ? false : true;
    }

    public boolean inferredWorkConflict() throws  {
        return (!this.work_conflict || this.inferred_work == null || this.inferred_work_other == null) ? false : true;
    }

    public boolean inferredHomeMissing() throws  {
        return this.home_missing;
    }

    public boolean inferredWorkMissing() throws  {
        return this.work_missing;
    }

    public CarpoolUserData() throws  {
        this.rider_social_networks = new CarpoolUserSocialNetworks[2];
        this.driver_social_networks = new CarpoolUserSocialNetworks[2];
        this.commute_days = new CarpoolCommuteDay[8];
    }

    public boolean hadPrevRides() throws  {
        return this.completed_rides_driver > 0;
    }

    public boolean didFinishOnboarding() throws  {
        return CarpoolNativeManager.getInstance().isDriverOnboarded();
    }

    public boolean isGetRidesRequestsEnabled() throws  {
        return this.is_driver;
    }

    public String getRealPhoneNumber() throws  {
        return this.phone;
    }

    public String getName() throws  {
        String $r1 = getGivenName();
        if (this.family_name != null) {
            return $r1 + " " + this.family_name;
        }
        return $r1;
    }

    public boolean isValid() throws  {
        return (this.id == null || this.id.isEmpty()) ? false : true;
    }

    public String getImage() throws  {
        return this.photo_url;
    }

    public String getFullImage() throws  {
        if (this.full_photo_url == null || this.full_photo_url.isEmpty()) {
            return this.photo_url;
        }
        return this.full_photo_url;
    }

    public String getWorkplace() throws  {
        return this.organization;
    }

    public boolean isWorkEmailVerified() throws  {
        return this.work_email_verified;
    }

    public String getWorkEmail() throws  {
        return this.work_email;
    }

    public String getEmail() throws  {
        return this.email;
    }

    public String getFirstName() throws  {
        if (getGivenName() != null && !getGivenName().isEmpty()) {
            return getGivenName();
        }
        if (this.name == null) {
            return "";
        }
        int $i0 = this.name.indexOf(32);
        if ($i0 > 0) {
            return this.name.substring(0, $i0);
        }
        return this.name;
    }

    public String getLastName() throws  {
        if (this.family_name != null && !this.family_name.isEmpty()) {
            return this.family_name;
        }
        if (this.name == null) {
            return "";
        }
        int $i0 = this.name.indexOf(32);
        return $i0 > 0 ? this.name.substring($i0 + 1, this.name.length()) : "";
    }

    public String getFirstNameAndInitials() throws  {
        if (this.given_name == null || this.given_name.isEmpty()) {
            if (this.name == null) {
                return "";
            }
            int $i1 = this.name.indexOf(32);
            if ($i1 <= 0) {
                return this.name;
            }
            int $i0 = $i1 + 2;
            if ($i0 > this.name.length()) {
                return this.name.substring(0, $i1);
            }
            return this.name.substring(0, $i0) + FileUploadSession.SEPARATOR;
        } else if (this.family_name == null || this.family_name.isEmpty()) {
            return this.given_name;
        } else {
            return this.given_name + " " + this.family_name.substring(0, 1) + FileUploadSession.SEPARATOR;
        }
    }

    public boolean hasPhoneNumber() throws  {
        return (this.phone == null || this.phone.isEmpty()) ? false : true;
    }

    public boolean hasBankDetails() throws  {
        return this.bank_account_updated;
    }

    public String getGivenName() throws  {
        return this.given_name;
    }

    public CarpoolUserSocialNetworks[] getDriverSocialNetworks() throws  {
        return this.driver_social_networks;
    }

    public CarpoolUserSocialNetworks[] getRiderSocialNetworks() throws  {
        return this.rider_social_networks;
    }

    public String getId() throws  {
        return this.id;
    }
}
