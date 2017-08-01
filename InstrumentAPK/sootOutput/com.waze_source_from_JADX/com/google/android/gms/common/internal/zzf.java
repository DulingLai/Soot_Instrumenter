package com.google.android.gms.common.internal;

import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zzf {
    public static final zzf IO = zza((CharSequence) "\t\n\u000b\f\r     　 ᠎ ").zza(zza(' ', ' '));
    public static final zzf IP = zza((CharSequence) "\t\n\u000b\f\r     　").zza(zza(' ', ' ')).zza(zza(' ', ' '));
    public static final zzf IQ = zza('\u0000', '');
    public static final zzf IR;
    public static final zzf IS = zza('\t', '\r').zza(zza('\u001c', ' ')).zza(zzc(' ')).zza(zzc('᠎')).zza(zza(' ', ' ')).zza(zza(' ', '​')).zza(zza(' ', ' ')).zza(zzc(' ')).zza(zzc('　'));
    public static final zzf IT = new C06981();
    public static final zzf IU = new C07025();
    public static final zzf IV = new C07036();
    public static final zzf IW = new C07047();
    public static final zzf IX = new C07058();
    public static final zzf IY = zza('\u0000', '\u001f').zza(zza('', ''));
    public static final zzf IZ = zza('\u0000', ' ').zza(zza('', ' ')).zza(zzc('­')).zza(zza('؀', '؃')).zza(zza((CharSequence) "۝܏ ឴឵᠎")).zza(zza(' ', '‏')).zza(zza(' ', ' ')).zza(zza(' ', '⁤')).zza(zza('⁪', '⁯')).zza(zzc('　')).zza(zza('?', '')).zza(zza((CharSequence) "﻿￹￺￻"));
    public static final zzf Ja = zza('\u0000', 'ӹ').zza(zzc('־')).zza(zza('א', 'ת')).zza(zzc('׳')).zza(zzc('״')).zza(zza('؀', 'ۿ')).zza(zza('ݐ', 'ݿ')).zza(zza('฀', '๿')).zza(zza('Ḁ', '₯')).zza(zza('℀', '℺')).zza(zza('ﭐ', '﷿')).zza(zza('ﹰ', '﻿')).zza(zza('｡', 'ￜ'));
    public static final zzf Jb = new C07069();
    public static final zzf Jc = new zzf() {
        public zzf zza(zzf $r1) throws  {
            return (zzf) zzab.zzag($r1);
        }

        public boolean zzb(CharSequence $r1) throws  {
            return $r1.length() == 0;
        }

        public boolean zzd(char c) throws  {
            return false;
        }
    };

    /* compiled from: dalvik_source_com.waze.apk */
    class AnonymousClass11 extends zzf {
        final /* synthetic */ char Ji;

        AnonymousClass11(char $c0) throws  {
            this.Ji = $c0;
        }

        public zzf zza(zzf $r1) throws  {
            return $r1.zzd(this.Ji) ? $r1 : super.zza($r1);
        }

        public boolean zzd(char $c0) throws  {
            return $c0 == this.Ji;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06981 extends zzf {
        C06981() throws  {
        }

        public boolean zzd(char $c0) throws  {
            return Character.isDigit($c0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C06992 extends zzf {
        final /* synthetic */ char Jd;
        final /* synthetic */ char Je;

        C06992(char $c0, char $c1) throws  {
            this.Jd = $c0;
            this.Je = $c1;
        }

        public boolean zzd(char $c0) throws  {
            return $c0 == this.Jd || $c0 == this.Je;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07003 extends zzf {
        final /* synthetic */ char[] Jf;

        C07003(char[] $r1) throws  {
            this.Jf = $r1;
        }

        public boolean zzd(char $c0) throws  {
            return Arrays.binarySearch(this.Jf, $c0) >= 0;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07014 extends zzf {
        final /* synthetic */ char Jg;
        final /* synthetic */ char Jh;

        C07014(char $c0, char $c1) throws  {
            this.Jg = $c0;
            this.Jh = $c1;
        }

        public boolean zzd(char $c0) throws  {
            return this.Jg <= $c0 && $c0 <= this.Jh;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07025 extends zzf {
        C07025() throws  {
        }

        public boolean zzd(char $c0) throws  {
            return Character.isLetter($c0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07036 extends zzf {
        C07036() throws  {
        }

        public boolean zzd(char $c0) throws  {
            return Character.isLetterOrDigit($c0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07047 extends zzf {
        C07047() throws  {
        }

        public boolean zzd(char $c0) throws  {
            return Character.isUpperCase($c0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07058 extends zzf {
        C07058() throws  {
        }

        public boolean zzd(char $c0) throws  {
            return Character.isLowerCase($c0);
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    class C07069 extends zzf {
        C07069() throws  {
        }

        public zzf zza(zzf $r1) throws  {
            zzab.zzag($r1);
            return this;
        }

        public boolean zzb(CharSequence $r1) throws  {
            zzab.zzag($r1);
            return true;
        }

        public boolean zzd(char c) throws  {
            return true;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static class zza extends zzf {
        List<zzf> Jj;

        zza(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/common/internal/zzf;", ">;)V"}) List<zzf> $r1) throws  {
            this.Jj = $r1;
        }

        public zzf zza(zzf $r1) throws  {
            ArrayList $r2 = new ArrayList(this.Jj);
            $r2.add((zzf) zzab.zzag($r1));
            return new zza($r2);
        }

        public boolean zzd(char $c0) throws  {
            for (zzf zzd : this.Jj) {
                if (zzd.zzd($c0)) {
                    return true;
                }
            }
            return false;
        }
    }

    static {
        zzf $r0 = zza('0', '9');
        for (char $c2 : "٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".toCharArray()) {
            $r0 = $r0.zza(zza($c2, (char) ($c2 + 9)));
        }
        IR = $r0;
    }

    public static zzf zza(char $c0, char $c1) throws  {
        zzab.zzbn($c1 >= $c0);
        return new C07014($c0, $c1);
    }

    public static zzf zza(CharSequence $r0) throws  {
        switch ($r0.length()) {
            case 0:
                return Jc;
            case 1:
                return zzc($r0.charAt(0));
            case 2:
                return new C06992($r0.charAt(0), $r0.charAt(1));
            default:
                char[] $r2 = $r0.toString().toCharArray();
                Arrays.sort($r2);
                return new C07003($r2);
        }
    }

    public static zzf zzc(char $c0) throws  {
        return new AnonymousClass11($c0);
    }

    public zzf zza(zzf $r1) throws  {
        return new zza(Arrays.asList(new zzf[]{this, (zzf) zzab.zzag($r1)}));
    }

    public boolean zzb(CharSequence $r1) throws  {
        for (int $i0 = $r1.length() - 1; $i0 >= 0; $i0--) {
            if (!zzd($r1.charAt($i0))) {
                return false;
            }
        }
        return true;
    }

    public abstract boolean zzd(char c) throws ;
}
