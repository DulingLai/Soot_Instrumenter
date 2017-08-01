package com.google.android.gms.internal;

import dalvik.annotation.Signature;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzawj {
    public static final zzavh bZA = zza(Class.class, bZz);
    public static final zzavg<BitSet> bZB = new zzavg<BitSet>() {
        public void zza(zzawn $r1, BitSet $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
                return;
            }
            $r1.hI();
            for (int $i0 = 0; $i0 < $r2.length(); $i0++) {
                $r1.zzdf((long) ($r2.get($i0)));
            }
            $r1.hJ();
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzx($r1);
        }

        public BitSet zzx(zzawl $r1) throws IOException {
            String $r7;
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            BitSet $r2 = new BitSet();
            $r1.beginArray();
            zzawm $r3 = $r1.hC();
            int $i0 = 0;
            while ($r3 != zzawm.END_ARRAY) {
                boolean $z0;
                switch ($r3) {
                    case NUMBER:
                        if ($r1.nextInt() == 0) {
                            $z0 = false;
                            break;
                        }
                        $z0 = true;
                        break;
                    case BOOLEAN:
                        $z0 = $r1.nextBoolean();
                        break;
                    case STRING:
                        String $r9 = $r1.nextString();
                        try {
                            if (Integer.parseInt($r9) == 0) {
                                $z0 = false;
                                break;
                            }
                            $z0 = true;
                            break;
                        } catch (NumberFormatException e) {
                            $r7 = "Error: Expecting: bitset number value (1, 0), Found: ";
                            $r9 = String.valueOf($r9);
                            throw new zzavd($r9.length() != 0 ? $r7.concat($r9) : new String("Error: Expecting: bitset number value (1, 0), Found: "));
                        }
                    default:
                        $r7 = String.valueOf($r3);
                        throw new zzavd(new StringBuilder(String.valueOf($r7).length() + 27).append("Invalid bitset value type: ").append($r7).toString());
                }
                if ($z0) {
                    $r2.set($i0);
                }
                $i0++;
                $r3 = $r1.hC();
            }
            $r1.endArray();
            return $r2;
        }
    };
    public static final zzavh bZC = zza(BitSet.class, bZB);
    public static final zzavg<Boolean> bZD = new zzavg<Boolean>() {
        public void zza(zzawn $r1, Boolean $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
            } else {
                $r1.zzeh($r2.booleanValue());
            }
        }

        public Boolean zzae(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return $r1.hC() == zzawm.STRING ? Boolean.valueOf(Boolean.parseBoolean($r1.nextString())) : Boolean.valueOf($r1.nextBoolean());
            } else {
                $r1.nextNull();
                return null;
            }
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzae($r1);
        }
    };
    public static final zzavg<Boolean> bZE = new zzavg<Boolean>() {
        public void zza(zzawn $r1, Boolean $r2) throws IOException {
            $r1.zzyp($r2 == null ? "null" : $r2.toString());
        }

        public Boolean zzae(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return Boolean.valueOf($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzae($r1);
        }
    };
    public static final zzavh bZF = zza(Boolean.TYPE, Boolean.class, bZD);
    public static final zzavg<Number> bZG = new zzavg<Number>() {
        public void zza(zzawn $r1, Number $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Byte.valueOf((byte) $r1.nextInt());
            } catch (Throwable $r5) {
                throw new zzavd($r5);
            }
        }
    };
    public static final zzavh bZH = zza(Byte.TYPE, Byte.class, bZG);
    public static final zzavg<Number> bZI = new zzavg<Number>() {
        public void zza(zzawn $r1, Number $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Short.valueOf((short) $r1.nextInt());
            } catch (Throwable $r5) {
                throw new zzavd($r5);
            }
        }
    };
    public static final zzavh bZJ = zza(Short.TYPE, Short.class, bZI);
    public static final zzavg<Number> bZK = new zzavg<Number>() {
        public void zza(zzawn $r1, Number $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Integer.valueOf($r1.nextInt());
            } catch (Throwable $r5) {
                throw new zzavd($r5);
            }
        }
    };
    public static final zzavh bZL = zza(Integer.TYPE, Integer.class, bZK);
    public static final zzavg<Number> bZM = new zzavg<Number>() {
        public void zza(zzawn $r1, Number $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Long.valueOf($r1.nextLong());
            } catch (Throwable $r5) {
                throw new zzavd($r5);
            }
        }
    };
    public static final zzavg<Number> bZN = new zzavg<Number>() {
        public void zza(zzawn $r1, Number $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return Float.valueOf((float) $r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }
    };
    public static final zzavg<Number> bZO = new C08052();
    public static final zzavg<Number> bZP = new C08063();
    public static final zzavh bZQ = zza(Number.class, bZP);
    public static final zzavg<Character> bZR = new C08074();
    public static final zzavh bZS = zza(Character.TYPE, Character.class, bZR);
    public static final zzavg<String> bZT = new C08085();
    public static final zzavg<BigDecimal> bZU = new C08096();
    public static final zzavg<BigInteger> bZV = new C08107();
    public static final zzavh bZW = zza(String.class, bZT);
    public static final zzavg<StringBuilder> bZX = new C08118();
    public static final zzavh bZY = zza(StringBuilder.class, bZX);
    public static final zzavg<StringBuffer> bZZ = new C08129();
    public static final zzavg<Class> bZz = new C08041();
    public static final zzavh caa = zza(StringBuffer.class, bZZ);
    public static final zzavg<URL> cab = new zzavg<URL>() {
        public void zza(zzawn $r1, URL $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : $r2.toExternalForm());
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzv($r1);
        }

        public URL zzv(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            String $r5 = $r1.nextString();
            return !"null".equals($r5) ? new URL($r5) : null;
        }
    };
    public static final zzavh cac = zza(URL.class, cab);
    public static final zzavg<URI> cad = new zzavg<URI>() {
        public void zza(zzawn $r1, URI $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : $r2.toASCIIString());
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzw($r1);
        }

        public URI zzw(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                String $r5 = $r1.nextString();
                return !"null".equals($r5) ? new URI($r5) : null;
            } catch (Throwable $r7) {
                throw new zzauv($r7);
            }
        }
    };
    public static final zzavh cae = zza(URI.class, cad);
    public static final zzavg<InetAddress> caf = new zzavg<InetAddress>() {
        public void zza(zzawn $r1, InetAddress $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : $r2.getHostAddress());
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzy($r1);
        }

        public InetAddress zzy(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return InetAddress.getByName($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }
    };
    public static final zzavh cag = zzb(InetAddress.class, caf);
    public static final zzavg<UUID> cah = new zzavg<UUID>() {
        public void zza(zzawn $r1, UUID $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : $r2.toString());
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzz($r1);
        }

        public UUID zzz(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return UUID.fromString($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }
    };
    public static final zzavh cai = zza(UUID.class, cah);
    public static final zzavh caj = new zzavh() {
        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            if ($r2.hN() != Timestamp.class) {
                return null;
            }
            final zzavg $r5 = $r1.zzj(Date.class);
            return new zzavg<Timestamp>(this) {
                final /* synthetic */ AnonymousClass15 cas;

                public void zza(zzawn $r1, Timestamp $r2) throws IOException {
                    $r5.zza($r1, $r2);
                }

                public Timestamp zzaa(zzawl $r1) throws IOException {
                    Date $r5 = (Date) $r5.zzb($r1);
                    return $r5 != null ? new Timestamp($r5.getTime()) : null;
                }

                public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
                    return zzaa($r1);
                }
            };
        }
    };
    public static final zzavg<Calendar> cak = new zzavg<Calendar>() {
        public void zza(zzawn $r1, Calendar $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
                return;
            }
            $r1.hK();
            $r1.zzyo("year");
            $r1.zzdf((long) $r2.get(1));
            $r1.zzyo("month");
            $r1.zzdf((long) $r2.get(2));
            $r1.zzyo("dayOfMonth");
            $r1.zzdf((long) $r2.get(5));
            $r1.zzyo("hourOfDay");
            $r1.zzdf((long) $r2.get(11));
            $r1.zzyo("minute");
            $r1.zzdf((long) $r2.get(12));
            $r1.zzyo("second");
            $r1.zzdf((long) $r2.get(13));
            $r1.hL();
        }

        public Calendar zzab(zzawl $r1) throws IOException {
            int $i0 = 0;
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            $r1.beginObject();
            int $i1 = 0;
            int $i2 = 0;
            int $i3 = 0;
            int $i4 = 0;
            int $i5 = 0;
            while ($r1.hC() != zzawm.END_OBJECT) {
                String $r5 = $r1.nextName();
                int $i6 = $r1.nextInt();
                if ("year".equals($r5)) {
                    $i5 = $i6;
                } else if ("month".equals($r5)) {
                    $i4 = $i6;
                } else if ("dayOfMonth".equals($r5)) {
                    $i3 = $i6;
                } else if ("hourOfDay".equals($r5)) {
                    $i2 = $i6;
                } else if ("minute".equals($r5)) {
                    $i1 = $i6;
                } else if ("second".equals($r5)) {
                    $i0 = $i6;
                }
            }
            $r1.endObject();
            return new GregorianCalendar($i5, $i4, $i3, $i2, $i1, $i0);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzab($r1);
        }
    };
    public static final zzavh cal = zzb(Calendar.class, GregorianCalendar.class, cak);
    public static final zzavg<Locale> cam = new zzavg<Locale>() {
        public void zza(zzawn $r1, Locale $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : $r2.toString());
        }

        public Locale zzac(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            StringTokenizer $r6 = new StringTokenizer($r1.nextString(), "_");
            String $r5 = $r6.hasMoreElements() ? $r6.nextToken() : null;
            String $r7 = $r6.hasMoreElements() ? $r6.nextToken() : null;
            String $r8 = $r6.hasMoreElements() ? $r6.nextToken() : null;
            return ($r7 == null && $r8 == null) ? new Locale($r5) : $r8 == null ? new Locale($r5, $r7) : new Locale($r5, $r7, $r8);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzac($r1);
        }
    };
    public static final zzavh can = zza(Locale.class, cam);
    public static final zzavg<zzauu> cao = new zzavg<zzauu>() {
        public void zza(zzawn $r1, zzauu $r2) throws IOException {
            if ($r2 == null || $r2.hk()) {
                $r1.hM();
            } else if ($r2.hj()) {
                zzava $r3 = $r2.hn();
                if ($r3.hq()) {
                    $r1.zza($r3.hb());
                } else if ($r3.hp()) {
                    $r1.zzeh($r3.hg());
                } else {
                    $r1.zzyp($r3.hc());
                }
            } else if ($r2.hh()) {
                $r1.hI();
                $r7 = $r2.hm().iterator();
                while ($r7.hasNext()) {
                    zza($r1, (zzauu) $r7.next());
                }
                $r1.hJ();
            } else if ($r2.hi()) {
                $r1.hK();
                for (Entry $r11 : $r2.hl().entrySet()) {
                    $r1.zzyo((String) $r11.getKey());
                    zza($r1, (zzauu) $r11.getValue());
                }
                $r1.hL();
            } else {
                String $r5 = String.valueOf($r2.getClass());
                throw new IllegalArgumentException(new StringBuilder(String.valueOf($r5).length() + 15).append("Couldn't write ").append($r5).toString());
            }
        }

        public zzauu zzad(zzawl $r1) throws IOException {
            switch ($r1.hC()) {
                case NUMBER:
                    return new zzava(new zzavr($r1.nextString()));
                case BOOLEAN:
                    return new zzava(Boolean.valueOf($r1.nextBoolean()));
                case STRING:
                    return new zzava($r1.nextString());
                case NULL:
                    $r1.nextNull();
                    return zzauw.bXK;
                case BEGIN_ARRAY:
                    zzaur $r9 = new zzaur();
                    $r1.beginArray();
                    while ($r1.hasNext()) {
                        $r9.zzc((zzauu) zzb($r1));
                    }
                    $r1.endArray();
                    return $r9;
                case BEGIN_OBJECT:
                    zzaux com_google_android_gms_internal_zzaux = new zzaux();
                    $r1.beginObject();
                    while ($r1.hasNext()) {
                        com_google_android_gms_internal_zzaux.zza($r1.nextName(), (zzauu) zzb($r1));
                    }
                    $r1.endObject();
                    return com_google_android_gms_internal_zzaux;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzad($r1);
        }
    };
    public static final zzavh cap = zzb(zzauu.class, cao);
    public static final zzavh caq = new zzavh() {
        public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
            Class $r3 = $r2.hN();
            Class $r4 = $r3;
            if (!Enum.class.isAssignableFrom($r3) || $r3 == Enum.class) {
                return null;
            }
            if (!$r3.isEnum()) {
                $r4 = $r3.getSuperclass();
            }
            return new zza($r4);
        }
    };

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08041 extends zzavg<Class> {
        C08041() throws  {
        }

        public void zza(zzawn $r1, Class $r2) throws IOException {
            if ($r2 == null) {
                $r1.hM();
            } else {
                String $r4 = String.valueOf($r2.getName());
                throw new UnsupportedOperationException(new StringBuilder(String.valueOf($r4).length() + 76).append("Attempted to serialize java.lang.Class: ").append($r4).append(". Forgot to register a type adapter?").toString());
            }
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzo($r1);
        }

        public Class zzo(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08052 extends zzavg<Number> {
        C08052() throws  {
        }

        public void zza(zzawn $r1, Number $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return Double.valueOf($r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08063 extends zzavg<Number> {
        C08063() throws  {
        }

        public void zza(zzawn $r1, Number $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzg($r1);
        }

        public Number zzg(zzawl $r1) throws IOException {
            zzawm $r2 = $r1.hC();
            switch ($r2) {
                case NUMBER:
                    return new zzavr($r1.nextString());
                case BOOLEAN:
                case STRING:
                    break;
                case NULL:
                    $r1.nextNull();
                    return null;
                default:
                    break;
            }
            String $r5 = String.valueOf($r2);
            throw new zzavd(new StringBuilder(String.valueOf($r5).length() + 23).append("Expecting number, got: ").append($r5).toString());
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08074 extends zzavg<Character> {
        C08074() throws  {
        }

        public void zza(zzawn $r1, Character $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : String.valueOf($r2));
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzp($r1);
        }

        public Character zzp(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            String $r4 = $r1.nextString();
            if ($r4.length() == 1) {
                return Character.valueOf($r4.charAt(0));
            }
            String $r6 = "Expecting character, got: ";
            $r4 = String.valueOf($r4);
            throw new zzavd($r4.length() != 0 ? $r6.concat($r4) : new String("Expecting character, got: "));
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08085 extends zzavg<String> {
        C08085() throws  {
        }

        public void zza(zzawn $r1, String $r2) throws IOException {
            $r1.zzyp($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzq($r1);
        }

        public String zzq(zzawl $r1) throws IOException {
            zzawm $r2 = $r1.hC();
            if ($r2 != zzawm.NULL) {
                return $r2 == zzawm.BOOLEAN ? Boolean.toString($r1.nextBoolean()) : $r1.nextString();
            } else {
                $r1.nextNull();
                return null;
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08096 extends zzavg<BigDecimal> {
        C08096() throws  {
        }

        public void zza(zzawn $r1, BigDecimal $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzr($r1);
        }

        public BigDecimal zzr(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return new BigDecimal($r1.nextString());
            } catch (Throwable $r6) {
                throw new zzavd($r6);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08107 extends zzavg<BigInteger> {
        C08107() throws  {
        }

        public void zza(zzawn $r1, BigInteger $r2) throws IOException {
            $r1.zza($r2);
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzs($r1);
        }

        public BigInteger zzs(zzawl $r1) throws IOException {
            if ($r1.hC() == zzawm.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return new BigInteger($r1.nextString());
            } catch (Throwable $r6) {
                throw new zzavd($r6);
            }
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08118 extends zzavg<StringBuilder> {
        C08118() throws  {
        }

        public void zza(zzawn $r1, StringBuilder $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : $r2.toString());
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzt($r1);
        }

        public StringBuilder zzt(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return new StringBuilder($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    static class C08129 extends zzavg<StringBuffer> {
        C08129() throws  {
        }

        public void zza(zzawn $r1, StringBuffer $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : $r2.toString());
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzu($r1);
        }

        public StringBuffer zzu(zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return new StringBuffer($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private static final class zza<T extends Enum<T>> extends zzavg<T> {
        private final Map<String, T> caA;
        private final Map<T, String> caB;

        public zza(@dalvik.annotation.Signature({"(", "Ljava/lang/Class", "<TT;>;)V"}) java.lang.Class<T> r23) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0061 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r22 = this;
            r0 = r22;
            r0.<init>();
            r2 = new java.util.HashMap;
            r2.<init>();
            r0 = r22;
            r0.caA = r2;
            r2 = new java.util.HashMap;
            r2.<init>();
            r0 = r22;
            r0.caB = r2;
            r0 = r23;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r3 = r0.getEnumConstants();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r5 = r3;
            r5 = (java.lang.Enum[]) r5;
            r4 = r5;
            r6 = r4.length;
            r7 = 0;
        L_0x0023:
            if (r7 >= r6) goto L_0x007f;
        L_0x0025:
            r8 = r4[r7];	 Catch:{ NoSuchFieldException -> 0x0076 }
            r9 = r8.name();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r10 = r9;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r23;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r11 = r0.getField(r9);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r13 = com.google.android.gms.internal.zzavj.class;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r12 = r11.getAnnotation(r13);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r15 = r12;
            r15 = (com.google.android.gms.internal.zzavj) r15;
            r14 = r15;
            if (r14 == 0) goto L_0x0061;	 Catch:{ NoSuchFieldException -> 0x0076 }
        L_0x003e:
            r10 = r14.value();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r16 = r14.ht();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r16;
            r0 = r0.length;
            r17 = r0;
            r18 = 0;
        L_0x004d:
            r0 = r18;
            r1 = r17;
            if (r0 >= r1) goto L_0x0061;
        L_0x0053:
            r9 = r16[r18];
            r0 = r22;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r0.caA;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r19 = r0;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0.put(r9, r8);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r18 = r18 + 1;
            goto L_0x004d;
        L_0x0061:
            r0 = r22;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r0.caA;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r19 = r0;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0.put(r10, r8);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r22;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r0.caB;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r19 = r0;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0.put(r8, r10);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r7 = r7 + 1;
            goto L_0x0023;
        L_0x0076:
            r20 = move-exception;
            r21 = new java.lang.AssertionError;
            r0 = r21;
            r0.<init>();
            throw r21;
        L_0x007f:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzawj.zza.<init>(java.lang.Class):void");
        }

        public void zza(@Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) zzawn $r1, @Signature({"(", "Lcom/google/android/gms/internal/zzawn;", "TT;)V"}) T $r2) throws IOException {
            $r1.zzyp($r2 == null ? null : (String) this.caB.get($r2));
        }

        public T zzaf(@Signature({"(", "Lcom/google/android/gms/internal/zzawl;", ")TT;"}) zzawl $r1) throws IOException {
            if ($r1.hC() != zzawm.NULL) {
                return (Enum) this.caA.get($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public /* synthetic */ Object zzb(zzawl $r1) throws IOException {
            return zzaf($r1);
        }
    }

    public static <TT> zzavh zza(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawk", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final zzawk<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzawk", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final zzavg<TT> $r1) throws  {
        return new zzavh() {
            public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
                return $r2.equals($r0) ? $r1 : null;
            }
        };
    }

    public static <TT> zzavh zza(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final Class<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final zzavg<TT> $r1) throws  {
        return new zzavh() {
            public String toString() throws  {
                String $r2 = String.valueOf($r0.getName());
                String $r4 = String.valueOf($r1);
                return new StringBuilder((String.valueOf($r2).length() + 23) + String.valueOf($r4).length()).append("Factory[type=").append($r2).append(",adapter=").append($r4).append("]").toString();
            }

            public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
                return $r2.hN() == $r0 ? $r1 : null;
            }
        };
    }

    public static <TT> zzavh zza(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<-TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final Class<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<-TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final Class<TT> $r1, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<-TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final zzavg<? super TT> $r2) throws  {
        return new zzavh() {
            public String toString() throws  {
                String $r2 = String.valueOf($r1.getName());
                String $r3 = String.valueOf($r0.getName());
                String $r5 = String.valueOf($r2);
                return new StringBuilder(((String.valueOf($r2).length() + 24) + String.valueOf($r3).length()) + String.valueOf($r5).length()).append("Factory[type=").append($r2).append("+").append($r3).append(",adapter=").append($r5).append("]").toString();
            }

            public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
                Class $r3 = $r2.hN();
                return ($r3 == $r0 || $r3 == $r1) ? $r2 : null;
            }
        };
    }

    public static <TT> zzavh zzb(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final Class<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final zzavg<TT> $r1) throws  {
        return new zzavh() {
            public String toString() throws  {
                String $r2 = String.valueOf($r0.getName());
                String $r4 = String.valueOf($r1);
                return new StringBuilder((String.valueOf($r2).length() + 32) + String.valueOf($r4).length()).append("Factory[typeHierarchy=").append($r2).append(",adapter=").append($r4).append("]").toString();
            }

            public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
                return $r0.isAssignableFrom($r2.hN()) ? $r1 : null;
            }
        };
    }

    public static <TT> zzavh zzb(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<+TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<-TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final Class<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<+TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<-TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final Class<? extends TT> $r1, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<+TTT;>;", "Lcom/google/android/gms/internal/zzavg", "<-TTT;>;)", "Lcom/google/android/gms/internal/zzavh;"}) final zzavg<? super TT> $r2) throws  {
        return new zzavh() {
            public String toString() throws  {
                String $r2 = String.valueOf($r0.getName());
                String $r3 = String.valueOf($r1.getName());
                String $r5 = String.valueOf($r2);
                return new StringBuilder(((String.valueOf($r2).length() + 24) + String.valueOf($r3).length()) + String.valueOf($r5).length()).append("Factory[type=").append($r2).append("+").append($r3).append(",adapter=").append($r5).append("]").toString();
            }

            public <T> zzavg<T> zza(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzauo com_google_android_gms_internal_zzauo, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/android/gms/internal/zzauo;", "Lcom/google/android/gms/internal/zzawk", "<TT;>;)", "Lcom/google/android/gms/internal/zzavg", "<TT;>;"}) zzawk<T> $r2) throws  {
                Class $r3 = $r2.hN();
                return ($r3 == $r0 || $r3 == $r1) ? $r2 : null;
            }
        };
    }
}
