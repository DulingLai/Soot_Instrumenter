package com.google.android.gms.plus.service.v2whitelisted.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Abouts;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Addresses;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Birthdays;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.BraggingRights;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Calendars;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.ClientData;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.CoverPhotos;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.CustomFields;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Events;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.ExtendedData;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.ExternalIds;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Genders;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Images;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.InstantMessaging;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Interests;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Languages;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.LegacyFields;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Memberships;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Names;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Nicknames;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Occupations;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Organizations;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.OtherKeywords;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.PhoneNumbers;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.PlacesLived;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Relations;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.SipAddress;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Skills;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.SortKeys;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Taglines;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Urls;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzj implements Creator<Person> {
    static void zza(Person $r0, Parcel $r1, int $i0) throws  {
        int $i1 = zzb.zzea($r1);
        Set $r2 = $r0.aOu;
        if ($r2.contains(Integer.valueOf(1))) {
            zzb.zzc($r1, 1, $r0.mVersionCode);
        }
        if ($r2.contains(Integer.valueOf(2))) {
            zzb.zzc($r1, 2, $r0.aOx, true);
        }
        if ($r2.contains(Integer.valueOf(3))) {
            zzb.zzc($r1, 3, $r0.aMy, true);
        }
        if ($r2.contains(Integer.valueOf(5))) {
            zzb.zzc($r1, 5, $r0.aOz, true);
        }
        if ($r2.contains(Integer.valueOf(6))) {
            zzb.zzc($r1, 6, $r0.aOA, true);
        }
        if ($r2.contains(Integer.valueOf(7))) {
            zzb.zzc($r1, 7, $r0.aZI, true);
        }
        if ($r2.contains(Integer.valueOf(8))) {
            zzb.zzc($r1, 8, $r0.aZJ, true);
        }
        if ($r2.contains(Integer.valueOf(9))) {
            zzb.zzc($r1, 9, $r0.aOB, true);
        }
        if ($r2.contains(Integer.valueOf(10))) {
            zzb.zzc($r1, 10, $r0.aOC, true);
        }
        if ($r2.contains(Integer.valueOf(11))) {
            zzb.zzc($r1, 11, $r0.aMz, true);
        }
        if ($r2.contains(Integer.valueOf(12))) {
            zzb.zza($r1, 12, $r0.aOD, true);
        }
        if ($r2.contains(Integer.valueOf(13))) {
            zzb.zzc($r1, 13, $r0.zzalu, true);
        }
        if ($r2.contains(Integer.valueOf(14))) {
            zzb.zza($r1, 14, $r0.aZK, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(15))) {
            zzb.zzc($r1, 15, $r0.aZL, true);
        }
        if ($r2.contains(Integer.valueOf(17))) {
            zzb.zzc($r1, 17, $r0.aOE, true);
        }
        if ($r2.contains(Integer.valueOf(18))) {
            zzb.zza($r1, 18, $r0.zzbgd, true);
        }
        if ($r2.contains(Integer.valueOf(19))) {
            zzb.zzc($r1, 19, $r0.zzbfe, true);
        }
        if ($r2.contains(Integer.valueOf(21))) {
            zzb.zzc($r1, 21, $r0.aOF, true);
        }
        if ($r2.contains(Integer.valueOf(22))) {
            zzb.zzc($r1, 22, $r0.aZM, true);
        }
        if ($r2.contains(Integer.valueOf(24))) {
            zzb.zza($r1, 24, $r0.zzcuw, true);
        }
        if ($r2.contains(Integer.valueOf(25))) {
            zzb.zzc($r1, 25, $r0.aZN, true);
        }
        if ($r2.contains(Integer.valueOf(26))) {
            zzb.zza($r1, 26, $r0.aZO, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(28))) {
            zzb.zzc($r1, 28, $r0.aOI, true);
        }
        if ($r2.contains(Integer.valueOf(29))) {
            zzb.zza($r1, 29, $r0.aZP, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(30))) {
            zzb.zzc($r1, 30, $r0.aOK, true);
        }
        if ($r2.contains(Integer.valueOf(31))) {
            zzb.zzc($r1, 31, $r0.aOL, true);
        }
        if ($r2.contains(Integer.valueOf(32))) {
            zzb.zzc($r1, 32, $r0.aOM, true);
        }
        if ($r2.contains(Integer.valueOf(33))) {
            zzb.zzc($r1, 33, $r0.aON, true);
        }
        if ($r2.contains(Integer.valueOf(34))) {
            zzb.zzc($r1, 34, $r0.aZQ, true);
        }
        if ($r2.contains(Integer.valueOf(36))) {
            zzb.zzc($r1, 36, $r0.aOO, true);
        }
        if ($r2.contains(Integer.valueOf(38))) {
            zzb.zzc($r1, 38, $r0.aOP, true);
        }
        if ($r2.contains(Integer.valueOf(39))) {
            zzb.zza($r1, 39, $r0.aOQ, true);
        }
        if ($r2.contains(Integer.valueOf(40))) {
            zzb.zzc($r1, 40, $r0.aOR, true);
        }
        if ($r2.contains(Integer.valueOf(43))) {
            zzb.zzc($r1, 43, $r0.aZR, true);
        }
        if ($r2.contains(Integer.valueOf(44))) {
            zzb.zzc($r1, 44, $r0.aOU, true);
        }
        if ($r2.contains(Integer.valueOf(45))) {
            zzb.zza($r1, 45, $r0.aZS, $i0, true);
        }
        if ($r2.contains(Integer.valueOf(46))) {
            zzb.zzc($r1, 46, $r0.aOW, true);
        }
        if ($r2.contains(Integer.valueOf(47))) {
            zzb.zzc($r1, 47, $r0.aOX, true);
        }
        zzb.zzaj($r1, $i1);
    }

    public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
        return zzvx($r1);
    }

    public /* synthetic */ Object[] newArray(int $i0) throws  {
        return zzafa($i0);
    }

    public Person[] zzafa(int $i0) throws  {
        return new Person[$i0];
    }

    public Person zzvx(Parcel $r1) throws  {
        int $i0 = zza.zzdz($r1);
        HashSet hashSet = new HashSet();
        int $i1 = 0;
        ArrayList $r3 = null;
        ArrayList $r4 = null;
        ArrayList $r5 = null;
        ArrayList $r6 = null;
        ArrayList $r7 = null;
        ArrayList $r8 = null;
        ArrayList $r9 = null;
        ArrayList $r10 = null;
        ArrayList $r11 = null;
        String $r12 = null;
        ArrayList $r13 = null;
        ExtendedData $r14 = null;
        ArrayList $r15 = null;
        ArrayList $r16 = null;
        String $r17 = null;
        ArrayList $r18 = null;
        ArrayList $r19 = null;
        ArrayList $r20 = null;
        String $r21 = null;
        ArrayList $r22 = null;
        LegacyFields $r23 = null;
        ArrayList $r24 = null;
        Metadata $r25 = null;
        ArrayList $r26 = null;
        ArrayList $r27 = null;
        ArrayList $r28 = null;
        ArrayList $r29 = null;
        ArrayList $r30 = null;
        ArrayList $r31 = null;
        ArrayList $r32 = null;
        String $r33 = null;
        ArrayList $r34 = null;
        ArrayList $r35 = null;
        ArrayList $r36 = null;
        SortKeys $r37 = null;
        ArrayList $r38 = null;
        ArrayList $r39 = null;
        while ($r1.dataPosition() < $i0) {
            int $i2 = zza.zzdy($r1);
            Parcelable $r42;
            switch (zza.zziv($i2)) {
                case 1:
                    $i1 = zza.zzg($r1, $i2);
                    hashSet.add(Integer.valueOf(1));
                    continue;
                case 2:
                    $r3 = zza.zzc($r1, $i2, Abouts.CREATOR);
                    hashSet.add(Integer.valueOf(2));
                    continue;
                case 3:
                    $r4 = zza.zzc($r1, $i2, Addresses.CREATOR);
                    hashSet.add(Integer.valueOf(3));
                    continue;
                case 4:
                case 16:
                case 20:
                case 23:
                case 27:
                case 35:
                case 37:
                case 41:
                case 42:
                    break;
                case 5:
                    $r5 = zza.zzc($r1, $i2, Birthdays.CREATOR);
                    hashSet.add(Integer.valueOf(5));
                    continue;
                case 6:
                    $r6 = zza.zzc($r1, $i2, BraggingRights.CREATOR);
                    hashSet.add(Integer.valueOf(6));
                    continue;
                case 7:
                    $r7 = zza.zzc($r1, $i2, Calendars.CREATOR);
                    hashSet.add(Integer.valueOf(7));
                    continue;
                case 8:
                    $r8 = zza.zzc($r1, $i2, ClientData.CREATOR);
                    hashSet.add(Integer.valueOf(8));
                    continue;
                case 9:
                    $r9 = zza.zzc($r1, $i2, CoverPhotos.CREATOR);
                    hashSet.add(Integer.valueOf(9));
                    continue;
                case 10:
                    $r10 = zza.zzc($r1, $i2, CustomFields.CREATOR);
                    hashSet.add(Integer.valueOf(10));
                    continue;
                case 11:
                    $r11 = zza.zzc($r1, $i2, Emails.CREATOR);
                    hashSet.add(Integer.valueOf(11));
                    continue;
                case 12:
                    $r12 = zza.zzq($r1, $i2);
                    hashSet.add(Integer.valueOf(12));
                    continue;
                case 13:
                    $r13 = zza.zzc($r1, $i2, Events.CREATOR);
                    hashSet.add(Integer.valueOf(13));
                    continue;
                case 14:
                    $r42 = zza.zza($r1, $i2, ExtendedData.CREATOR);
                    hashSet.add(Integer.valueOf(14));
                    $r14 = (ExtendedData) $r42;
                    continue;
                case 15:
                    $r15 = zza.zzc($r1, $i2, ExternalIds.CREATOR);
                    hashSet.add(Integer.valueOf(15));
                    continue;
                case 17:
                    $r16 = zza.zzc($r1, $i2, Genders.CREATOR);
                    hashSet.add(Integer.valueOf(17));
                    continue;
                case 18:
                    $r17 = zza.zzq($r1, $i2);
                    hashSet.add(Integer.valueOf(18));
                    continue;
                case 19:
                    $r18 = zza.zzc($r1, $i2, Images.CREATOR);
                    hashSet.add(Integer.valueOf(19));
                    continue;
                case 21:
                    $r19 = zza.zzc($r1, $i2, InstantMessaging.CREATOR);
                    hashSet.add(Integer.valueOf(21));
                    continue;
                case 22:
                    $r20 = zza.zzc($r1, $i2, Interests.CREATOR);
                    hashSet.add(Integer.valueOf(22));
                    continue;
                case 24:
                    $r21 = zza.zzq($r1, $i2);
                    hashSet.add(Integer.valueOf(24));
                    continue;
                case 25:
                    $r22 = zza.zzc($r1, $i2, Languages.CREATOR);
                    hashSet.add(Integer.valueOf(25));
                    continue;
                case 26:
                    $r42 = zza.zza($r1, $i2, LegacyFields.CREATOR);
                    hashSet.add(Integer.valueOf(26));
                    $r23 = (LegacyFields) $r42;
                    continue;
                case 28:
                    $r24 = zza.zzc($r1, $i2, Memberships.CREATOR);
                    hashSet.add(Integer.valueOf(28));
                    continue;
                case 29:
                    $r42 = zza.zza($r1, $i2, Metadata.CREATOR);
                    hashSet.add(Integer.valueOf(29));
                    $r25 = (Metadata) $r42;
                    continue;
                case 30:
                    $r26 = zza.zzc($r1, $i2, Names.CREATOR);
                    hashSet.add(Integer.valueOf(30));
                    continue;
                case 31:
                    $r27 = zza.zzc($r1, $i2, Nicknames.CREATOR);
                    hashSet.add(Integer.valueOf(31));
                    continue;
                case 32:
                    $r28 = zza.zzc($r1, $i2, Occupations.CREATOR);
                    hashSet.add(Integer.valueOf(32));
                    continue;
                case 33:
                    $r29 = zza.zzc($r1, $i2, Organizations.CREATOR);
                    hashSet.add(Integer.valueOf(33));
                    continue;
                case 34:
                    $r30 = zza.zzc($r1, $i2, OtherKeywords.CREATOR);
                    hashSet.add(Integer.valueOf(34));
                    continue;
                case 36:
                    $r31 = zza.zzc($r1, $i2, PhoneNumbers.CREATOR);
                    hashSet.add(Integer.valueOf(36));
                    continue;
                case 38:
                    $r32 = zza.zzc($r1, $i2, PlacesLived.CREATOR);
                    hashSet.add(Integer.valueOf(38));
                    continue;
                case 39:
                    $r33 = zza.zzq($r1, $i2);
                    hashSet.add(Integer.valueOf(39));
                    continue;
                case 40:
                    $r34 = zza.zzc($r1, $i2, Relations.CREATOR);
                    hashSet.add(Integer.valueOf(40));
                    continue;
                case 43:
                    $r35 = zza.zzc($r1, $i2, SipAddress.CREATOR);
                    hashSet.add(Integer.valueOf(43));
                    continue;
                case 44:
                    $r36 = zza.zzc($r1, $i2, Skills.CREATOR);
                    hashSet.add(Integer.valueOf(44));
                    continue;
                case 45:
                    $r42 = zza.zza($r1, $i2, SortKeys.CREATOR);
                    hashSet.add(Integer.valueOf(45));
                    $r37 = (SortKeys) $r42;
                    continue;
                case 46:
                    $r38 = zza.zzc($r1, $i2, Taglines.CREATOR);
                    hashSet.add(Integer.valueOf(46));
                    continue;
                case 47:
                    $r39 = zza.zzc($r1, $i2, Urls.CREATOR);
                    hashSet.add(Integer.valueOf(47));
                    continue;
                default:
                    break;
            }
            zza.zzb($r1, $i2);
        }
        if ($r1.dataPosition() == $i0) {
            return new Person(hashSet, $i1, $r3, $r4, $r5, $r6, $r7, $r8, $r9, $r10, $r11, $r12, $r13, $r14, $r15, $r16, $r17, $r18, $r19, $r20, $r21, $r22, $r23, $r24, $r25, $r26, $r27, $r28, $r29, $r30, $r31, $r32, $r33, $r34, $r35, $r36, $r37, $r38, $r39);
        }
        throw new zza.zza("Overread allowed size end=" + $i0, $r1);
    }
}
