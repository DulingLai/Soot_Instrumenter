package com.google.android.gms.people.identity.internal;

import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Event;
import android.provider.ContactsContract.CommonDataKinds.Im;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Relation;
import android.provider.ContactsContract.CommonDataKinds.StructuredPostal;
import android.provider.ContactsContract.DisplayPhoto;
import android.text.TextUtils;
import com.facebook.internal.FacebookRequestErrorClassification;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.util.zzr;
import com.google.android.gms.gcm.nts.GcmScheduler;
import com.google.android.gms.people.identity.PersonFactory.RawContactData;
import com.google.android.gms.people.internal.zzp;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* compiled from: dalvik_source_com.waze.apk */
public class zzc {

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zza {
        private static final Map<Integer, String> aNq;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(1), "home");
            $r0.put(Integer.valueOf(3), FacebookRequestErrorClassification.KEY_OTHER);
            $r0.put(Integer.valueOf(2), "work");
            $r0.put(Integer.valueOf(0), "custom");
            aNq = Collections.unmodifiableMap($r0);
        }

        public static String zza(Context $r0, RawContactData $r1) throws  {
            Integer $r2 = zzc.zza($r1, 1);
            return $r2 == null ? null : StructuredPostal.getTypeLabel($r0.getResources(), $r2.intValue(), $r1.getData(2)).toString();
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNq.containsKey($r1)) {
                return (String) aNq.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 31).append("Invalid StructuredPostal Type: ").append($r4).toString());
            return null;
        }

        public static final String zzb(RawContactData $r0) throws  {
            return $r0.getData(3);
        }

        public static final String zzc(RawContactData $r0) throws  {
            return $r0.getData(4);
        }

        public static final String zzd(RawContactData $r0) throws  {
            return $r0.getData(6);
        }

        public static final String zze(RawContactData $r0) throws  {
            return $r0.getData(7);
        }

        public static final String zzf(RawContactData $r0) throws  {
            return $r0.getData(8);
        }

        public static final String zzg(RawContactData $r0) throws  {
            String $r1 = $r0.getData(9);
            return ($r1 == null || !TextUtils.isDigitsOnly($r1)) ? $r1 : null;
        }

        public static final String zzh(RawContactData $r0) throws  {
            String $r1 = $r0.getData(9);
            return ($r1 == null || !TextUtils.isDigitsOnly($r1)) ? null : $r1;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzb {
        private static final Map<Integer, String> aNr;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(1), "home");
            $r0.put(Integer.valueOf(4), "mobile");
            $r0.put(Integer.valueOf(3), FacebookRequestErrorClassification.KEY_OTHER);
            $r0.put(Integer.valueOf(2), "work");
            $r0.put(Integer.valueOf(0), "custom");
            aNr = Collections.unmodifiableMap($r0);
        }

        public static String zza(Context $r0, RawContactData $r1) throws  {
            Integer $r2 = zzc.zza($r1, 1);
            return $r2 == null ? null : Email.getTypeLabel($r0.getResources(), $r2.intValue(), $r1.getData(2)).toString();
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNr.containsKey($r1)) {
                return (String) aNr.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 20).append("Invalid Email Type: ").append($r4).toString());
            return null;
        }

        public static final String zzi(RawContactData $r0) throws  {
            return $r0.getData(0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzc {
        private static final Map<Integer, String> aNs;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(1), "anniversary");
            $r0.put(Integer.valueOf(3), "birthday");
            $r0.put(Integer.valueOf(2), FacebookRequestErrorClassification.KEY_OTHER);
            $r0.put(Integer.valueOf(0), "custom");
            aNs = Collections.unmodifiableMap($r0);
        }

        public static final String zzb(Context $r0, RawContactData $r1) throws  {
            Integer $r2 = zzc.zza($r1, 1);
            return $r2 == null ? null : $r0.getString(Event.getTypeResource($r2));
        }

        public static final String zzj(RawContactData $r0) throws  {
            return $r0.getData(0);
        }

        public static final String zzk(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNs.containsKey($r1)) {
                return (String) aNs.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 20).append("Invalid Event Type: ").append($r4).toString());
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzd {
        private static final Map<Integer, String> aNt;
        private static final Map<Integer, String> aNu;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(1), "home");
            $r0.put(Integer.valueOf(3), FacebookRequestErrorClassification.KEY_OTHER);
            $r0.put(Integer.valueOf(2), "work");
            $r0.put(Integer.valueOf(0), "custom");
            aNt = Collections.unmodifiableMap($r0);
            $r0 = new HashMap();
            $r0.put(Integer.valueOf(0), "aim");
            $r0.put(Integer.valueOf(-1), "custom");
            $r0.put(Integer.valueOf(5), "googleTalk");
            $r0.put(Integer.valueOf(6), "icq");
            $r0.put(Integer.valueOf(7), "jabber");
            $r0.put(Integer.valueOf(1), "msn");
            $r0.put(Integer.valueOf(8), "netMeeting");
            $r0.put(Integer.valueOf(4), "qq");
            $r0.put(Integer.valueOf(3), "skype");
            $r0.put(Integer.valueOf(2), "yahoo");
            aNu = Collections.unmodifiableMap($r0);
        }

        public static String zza(Context $r0, RawContactData $r1) throws  {
            Integer $r2 = zzc.zza($r1, 1);
            return $r2 == null ? null : Im.getTypeLabel($r0.getResources(), $r2.intValue(), $r1.getData(2)).toString();
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNt.containsKey($r1)) {
                return (String) aNt.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 17).append("Invalid IM Type: ").append($r4).toString());
            return null;
        }

        public static String zzc(Context $r0, RawContactData $r1) throws  {
            Integer $r2 = zzc.zza($r1, 4);
            return $r2 == null ? null : Im.getProtocolLabel($r0.getResources(), $r2.intValue(), $r1.getData(5)).toString();
        }

        public static String zzi(RawContactData $r0) throws  {
            return $r0.getData(0);
        }

        public static String zzl(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 4);
            if ($r1 != null && aNu.containsKey($r1)) {
                return (String) aNu.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 21).append("Invalid IM Protocol: ").append($r4).toString());
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zze {
        @TargetApi(14)
        public static final String zzm(RawContactData $r0) throws  {
            String $r1 = $r0.getData(13);
            if ($r1 == null) {
                return null;
            }
            if (!zzr.zzazd()) {
                return null;
            }
            try {
                return ContentUris.withAppendedId(DisplayPhoto.CONTENT_URI, Long.valueOf($r1).longValue()).toString();
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzf {
        public static final String zzn(RawContactData $r0) throws  {
            return $r0.getData(0);
        }

        public static final String zzo(RawContactData $r0) throws  {
            return $r0.getData(1);
        }

        @TargetApi(14)
        public static final String zzp(RawContactData $r0) throws  {
            String $r1 = $r0.getData(2);
            if ($r1 == null) {
                return null;
            }
            if (!zzr.zzazd()) {
                return null;
            }
            try {
                return ContentUris.withAppendedId(DisplayPhoto.CONTENT_URI, Long.valueOf($r1).longValue()).toString();
            } catch (NumberFormatException e) {
                return null;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzg {
        public static final String zzq(RawContactData $r0) throws  {
            return $r0.getData(0);
        }

        public static final String zzr(RawContactData $r0) throws  {
            return $r0.getData(1);
        }

        public static final String zzs(RawContactData $r0) throws  {
            return $r0.getData(2);
        }

        public static final String zzt(RawContactData $r0) throws  {
            return $r0.getData(3);
        }

        public static final String zzu(RawContactData $r0) throws  {
            return $r0.getData(4);
        }

        public static final String zzv(RawContactData $r0) throws  {
            return $r0.getData(5);
        }

        public static final String zzw(RawContactData $r0) throws  {
            return $r0.getData(6);
        }

        public static final String zzx(RawContactData $r0) throws  {
            return $r0.getData(8);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzh {
        private static final Map<Integer, String> aNv;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(1), "default");
            $r0.put(Integer.valueOf(5), "initials");
            $r0.put(Integer.valueOf(3), "maidenName");
            $r0.put(Integer.valueOf(2), "otherName");
            $r0.put(Integer.valueOf(4), "shortName");
            $r0.put(Integer.valueOf(0), "custom");
            aNv = Collections.unmodifiableMap($r0);
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNv.containsKey($r1)) {
                return (String) aNv.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 23).append("Invalid Nickname Type: ").append($r4).toString());
            return null;
        }

        public static final String zzo(RawContactData $r0) throws  {
            return $r0.getData(0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzi {
        public static final String zzy(RawContactData $r0) throws  {
            return $r0.getData(0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzj {
        private static final Map<Integer, String> aNw;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(2), FacebookRequestErrorClassification.KEY_OTHER);
            $r0.put(Integer.valueOf(1), "work");
            $r0.put(Integer.valueOf(0), "custom");
            aNw = Collections.unmodifiableMap($r0);
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNw.containsKey($r1)) {
                return (String) aNw.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 27).append("Invalid Organization Type: ").append($r4).toString());
            return null;
        }

        public static final String zzaa(RawContactData $r0) throws  {
            return $r0.getData(4);
        }

        public static final String zzab(RawContactData $r0) throws  {
            return $r0.getData(5);
        }

        public static final String zzac(RawContactData $r0) throws  {
            return $r0.getData(6);
        }

        public static final String zzad(RawContactData $r0) throws  {
            return $r0.getData(7);
        }

        public static final String zzae(RawContactData $r0) throws  {
            return $r0.getData(8);
        }

        public static final String zzo(RawContactData $r0) throws  {
            return $r0.getData(0);
        }

        public static final String zzz(RawContactData $r0) throws  {
            return $r0.getData(3);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzk {
        private static final Map<Integer, String> aNx;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(19), "assistant");
            $r0.put(Integer.valueOf(8), GcmScheduler.INTENT_PARAM_CALLBACK);
            $r0.put(Integer.valueOf(9), "car");
            $r0.put(Integer.valueOf(10), "mainCompany");
            $r0.put(Integer.valueOf(5), "homeFax");
            $r0.put(Integer.valueOf(4), "workFax");
            $r0.put(Integer.valueOf(1), "home");
            $r0.put(Integer.valueOf(11), "isdn");
            $r0.put(Integer.valueOf(12), "main");
            $r0.put(Integer.valueOf(20), "mms");
            $r0.put(Integer.valueOf(2), "mobile");
            $r0.put(Integer.valueOf(7), FacebookRequestErrorClassification.KEY_OTHER);
            $r0.put(Integer.valueOf(13), "otherFax");
            $r0.put(Integer.valueOf(6), "pager");
            $r0.put(Integer.valueOf(14), "radio");
            $r0.put(Integer.valueOf(15), "telex");
            $r0.put(Integer.valueOf(16), "ttytdd");
            $r0.put(Integer.valueOf(3), "work");
            $r0.put(Integer.valueOf(17), "workMobile");
            $r0.put(Integer.valueOf(18), "workPager");
            $r0.put(Integer.valueOf(0), "custom");
            aNx = Collections.unmodifiableMap($r0);
        }

        public static String zza(Context $r0, RawContactData $r1) throws  {
            Integer $r2 = zzc.zza($r1, 1);
            return $r2 == null ? null : Phone.getTypeLabel($r0.getResources(), $r2.intValue(), $r1.getData(2)).toString();
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNx.containsKey($r1)) {
                return (String) aNx.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 20).append("Invalid Phone Type: ").append($r4).toString());
            return null;
        }

        public static final String zzaf(RawContactData $r0) throws  {
            return $r0.getData(0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzl {
        private static final Map<Integer, String> aNy;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(1), "assistant");
            $r0.put(Integer.valueOf(2), "brother");
            $r0.put(Integer.valueOf(3), "child");
            $r0.put(Integer.valueOf(4), "domesticPartner");
            $r0.put(Integer.valueOf(5), "father");
            $r0.put(Integer.valueOf(6), "friend");
            $r0.put(Integer.valueOf(7), "manager");
            $r0.put(Integer.valueOf(8), "mother");
            $r0.put(Integer.valueOf(9), "parent");
            $r0.put(Integer.valueOf(10), "partner");
            $r0.put(Integer.valueOf(11), "referredBy");
            $r0.put(Integer.valueOf(12), "relative");
            $r0.put(Integer.valueOf(13), "sister");
            $r0.put(Integer.valueOf(14), "spouse");
            $r0.put(Integer.valueOf(0), "custom");
            aNy = Collections.unmodifiableMap($r0);
        }

        @TargetApi(11)
        public static String zza(Context $r0, RawContactData $r1) throws  {
            Integer $r2 = zzc.zza($r1, 1);
            return ($r2 == null || !zzr.zzaza()) ? null : Relation.getTypeLabel($r0.getResources(), $r2.intValue(), $r1.getData(2)).toString();
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNy.containsKey($r1)) {
                return (String) aNy.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 23).append("Invalid Relation Type: ").append($r4).toString());
            return null;
        }

        public static final String zzag(RawContactData $r0) throws  {
            return $r0.getData(0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static final class zzm {
        private static final Map<Integer, String> aNz;

        static {
            HashMap $r0 = new HashMap();
            $r0.put(Integer.valueOf(2), "blog");
            $r0.put(Integer.valueOf(6), "ftp");
            $r0.put(Integer.valueOf(4), "home");
            $r0.put(Integer.valueOf(1), "homePage");
            $r0.put(Integer.valueOf(7), FacebookRequestErrorClassification.KEY_OTHER);
            $r0.put(Integer.valueOf(3), Scopes.PROFILE);
            $r0.put(Integer.valueOf(5), "work");
            $r0.put(Integer.valueOf(0), "custom");
            aNz = Collections.unmodifiableMap($r0);
        }

        public static String zza(RawContactData $r0) throws  {
            Integer $r1 = zzc.zza($r0, 1);
            if ($r1 != null && aNz.containsKey($r1)) {
                return (String) aNz.get($r1);
            }
            String $r4 = String.valueOf($r1);
            zzp.zzan("ContactData", new StringBuilder(String.valueOf($r4).length() + 27).append("Invalid Organization Type: ").append($r4).toString());
            return null;
        }

        public static final String zzm(RawContactData $r0) throws  {
            return $r0.getData(0);
        }
    }

    private static Integer zza(RawContactData $r0, int $i0) throws  {
        String $r1 = $r0.getData($i0);
        if (!TextUtils.isEmpty($r1)) {
            try {
                return Integer.valueOf(Integer.parseInt($r1));
            } catch (NumberFormatException $r3) {
                String $r4 = String.valueOf($r0.getMimeType());
                zzp.zzc("ContactData", new StringBuilder((String.valueOf($r4).length() + 28) + String.valueOf($r1).length()).append("Invalid ID: ").append($r4).append("[").append($i0).append("] = ").append($r1).toString(), $r3);
            }
        }
        return null;
    }

    public static boolean zznd(String $r0) throws  {
        return $r0 != null && $r0.startsWith("c:");
    }

    public static String zzne(String $r0) throws  {
        return !zznd($r0) ? null : $r0.substring("c:".length());
    }

    public static String zznf(String $r0) throws  {
        String $r1 = String.valueOf("c:");
        $r0 = String.valueOf($r0);
        return $r0.length() != 0 ? $r1.concat($r0) : new String($r1);
    }

    public static Set<String> zzng(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;"}) String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            return Collections.emptySet();
        }
        HashSet $r2 = new HashSet();
        $r2.add($r0);
        return $r2;
    }

    public static boolean zznh(String $r0) throws  {
        return $r0 != null && $r0.startsWith("f:");
    }

    public static boolean zzni(String $r0) throws  {
        return $r0 != null && $r0.startsWith("p:");
    }

    public static String zznj(String $r0) throws  {
        return !zznh($r0) ? null : $r0.substring("f:".length());
    }

    public static String zznk(String $r0) throws  {
        String $r1 = String.valueOf("f:");
        $r0 = String.valueOf($r0);
        return $r0.length() != 0 ? $r1.concat($r0) : new String($r1);
    }

    public static String zznl(String $r0) throws  {
        return !zzni($r0) ? null : $r0.substring("p:".length());
    }
}
