package com.google.android.gms.people.internal.agg;

import android.os.Bundle;
import android.text.TextUtils;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.people.internal.zzq;
import com.google.android.gms.people.model.EmailAddress;
import com.google.android.gms.people.model.PhoneNumber;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class PhoneEmailDecoder<T> {
    public static EmailDecoder DummyEmailDecoder = new EmailDecoder(Bundle.EMPTY);
    public static PhoneDecoder DummyPhoneDecoder = new PhoneDecoder(Bundle.EMPTY);
    private final char aSn;
    private final char aSo;
    private final String aSp = Pattern.quote(String.valueOf(this.aSn));
    private final String aSq = Pattern.quote(String.valueOf(this.aSo));
    private final Bundle aSr;

    /* compiled from: dalvik_source_com.waze.apk */
    public static class EmailDecoder extends PhoneEmailDecoder<EmailAddress> {
        public EmailDecoder(Bundle $r1) throws  {
            super($r1, '\u0001', '\u0002');
        }

        protected EmailAddress build(String $r1, String $r2, double $d0, double $d1, double $d2, double $d3, double $d4, String $r3, String $r4, String $r5, String $r6, String $r7) throws  {
            return new zzc($r2, $r1, $d0, $d1, $d2, $d3, $d4, $r3, $r4, $r5, $r6, $r7);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class PhoneDecoder extends PhoneEmailDecoder<PhoneNumber> {
        public PhoneDecoder(Bundle $r1) throws  {
            super($r1, '\u0001', '\u0002');
        }

        protected PhoneNumber build(String $r1, String $r2, double d, double d2, double d3, double d4, double d5, String str, String str2, String str3, String str4, String str5) throws  {
            return new zzg($r2, $r1);
        }
    }

    PhoneEmailDecoder(Bundle $r1, char $c0, char $c1) throws  {
        this.aSr = $r1;
        this.aSn = $c0;
        this.aSo = $c1;
    }

    private static double parseDouble(String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            return 0.0d;
        }
        try {
            return Double.parseDouble($r0);
        } catch (NumberFormatException $r1) {
            zzp.zzd("PhoneEmailDecoder", "NumberFormatException", $r1);
            return 0.0d;
        }
    }

    private static int zza(String $r0, char $c0, int $i1, int $i2) throws  {
        $i1 = $r0.indexOf($c0, $i1);
        return ($i1 < 0 || $i1 >= $i2) ? -1 : $i1;
    }

    private final void zza(@Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", ")V"}) ArrayList<T> $r1, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        int $i0 = $r2.length();
        int i = 0;
        while (i < $i0) {
            int $i3 = $r2.indexOf(this.aSo, i);
            int i2 = $i3;
            if ($i3 < 0) {
                i2 = $r2.length();
            }
            $i3 = zza($r2, this.aSn, i, i2);
            int $i5 = zza($r2, this.aSn, $i3 + 1, i2);
            if ($i3 >= 0 && $i5 >= 0) {
                String $r3 = $r2.substring(i, $i3);
                String $r4 = $r2.substring($i3 + 1, $i5);
                zza($r1, $r3, $r4, $r2.substring($i5 + 1, i2), 0.0d, 0.0d, 0.0d, 0.0d, 0.0d, null, null, null, null, null);
            }
            i = i2 + 1;
        }
    }

    private void zza(@Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) ArrayList<T> $r1, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) double $d0, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) double $d1, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) double $d2, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) double $d3, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) double $d4, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r5, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r6, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r7, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r8, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r9) throws  {
        if (!TextUtils.isEmpty($r4)) {
            Bundle bundle = this.aSr;
            Bundle $r10 = bundle;
            $r2 = bundle.getString($r2);
            String $r11 = $r2;
            if (TextUtils.isEmpty($r2)) {
                $r11 = $r3;
            }
            $r1.add(build($r4, $r11, $d0, $d1, $d2, $d3, $d4, $r5, $r6, $r7, $r8, $r9));
        }
    }

    private final void zzb(@Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", ")V"}) ArrayList<T> $r1, @Signature({"(", "Ljava/util/ArrayList", "<TT;>;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        for (String $r22 : TextUtils.split($r22, this.aSq)) {
            String[] $r5 = TextUtils.split($r22, this.aSp);
            if ($r5.length < 13) {
                zzp.zzan("PhoneEmailDecoder", "Invalid string");
            } else {
                zza($r1, $r5[0], $r5[1], $r5[2], parseDouble($r5[3]), parseDouble($r5[4]), parseDouble($r5[5]), parseDouble($r5[6]), parseDouble($r5[7]), zzq.zzqy($r5[8]), zzq.zzqy($r5[9]), zzq.zzqy($r5[10]), zzq.zzqy($r5[11]), zzq.zzqy($r5[12]));
            }
        }
    }

    protected abstract T build(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) String str2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) double d, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) double d2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) double d3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) double d4, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) double d5, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) String str3, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) String str4, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) String str5, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) String str6, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "DDDDD", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", ")TT;"}) String str7) throws ;

    public final ArrayList<T> decode(@Signature({"(", "Ljava/lang/String;", "Z)", "Ljava/util/ArrayList", "<TT;>;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Z)", "Ljava/util/ArrayList", "<TT;>;"}) boolean $z0) throws  {
        ArrayList $r2 = new ArrayList();
        if (TextUtils.isEmpty($r1)) {
            return $r2;
        }
        if ($z0) {
            zzb($r2, $r1);
            return $r2;
        }
        zza($r2, $r1);
        return $r2;
    }
}
