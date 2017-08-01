package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import dalvik.annotation.Signature;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TreeSet;

/* compiled from: dalvik_source_com.waze.apk */
public final class PasswordSpecification extends AbstractSafeParcelable {
    public static final String ALL_PRINTABLE = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890 !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static final String ALPHANUMERIC = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    public static final String ALPHANUMERIC_DISTINGUISHABLE = "abcdefghijkmnopqrstxyzABCDEFGHJKLMNPQRSTXY3456789";
    public static final PasswordSpecificationCreator CREATOR = new PasswordSpecificationCreator();
    public static final PasswordSpecification DEFAULT = new Builder().ofLength(12, 16).allow(ALPHANUMERIC_DISTINGUISHABLE).require(LOWER_ALPHA_DISTINGUISHABLE, 1).require(UPPER_ALPHA_DISTINGUISHABLE, 1).require(NUMERALS_DISTINGUISHABLE, 1).build();
    public static final PasswordSpecification DEFAULT_FOR_VALIDATION = new Builder().ofLength(12, 16).allow(ALPHANUMERIC).require(LOWER_ALPHA, 1).require(UPPER_ALPHA, 1).require(NUMERALS, 1).build();
    public static final String LOWER_ALPHA = "abcdefghijklmnopqrstuvwxyz";
    public static final String LOWER_ALPHA_DISTINGUISHABLE = "abcdefghijkmnopqrstxyz";
    public static final String NUMERALS = "1234567890";
    public static final String NUMERALS_DISTINGUISHABLE = "3456789";
    public static final int PASSWORD_CONFORMS = 0;
    public static final int PASSWORD_DISALLOWED_CHARACTER = 3;
    public static final int PASSWORD_LENGTH_MISMATCH = 1;
    public static final int PASSWORD_REQUIRED_CHARACTER_MISSING = 2;
    public static final String SYMBOLS = " !\"#$%&'()*+,-./:;<=>?@[\\]^_`{|}~";
    public static final String UPPER_ALPHA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    public static final String UPPER_ALPHA_DISTINGUISHABLE = "ABCDEFGHJKLMNPQRSTXY";
    final String eU;
    final List<String> eV;
    final List<Integer> eW;
    final int eX;
    final int eY;
    private final int[] eZ = zzady();
    final int mVersionCode;
    private final Random zzawh = new SecureRandom();

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        public static final int DEFAULT_MAXIMUM_SIZE = 16;
        public static final int DEFAULT_MINIMUM_SIZE = 12;
        private final List<String> eV = new ArrayList();
        private final List<Integer> eW = new ArrayList();
        private int eX = 12;
        private int eY = 16;
        private final TreeSet<Character> fa = new TreeSet();

        private TreeSet<Character> zzab(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Ljava/util/TreeSet", "<", "Ljava/lang/Character;", ">;"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", ")", "Ljava/util/TreeSet", "<", "Ljava/lang/Character;", ">;"}) String $r2) throws  {
            if (TextUtils.isEmpty($r1)) {
                throw new InvalidSpecificationError(String.valueOf($r2).concat(" cannot be null or empty"));
            }
            TreeSet $r4 = new TreeSet();
            for (char $c2 : $r1.toCharArray()) {
                if (PasswordSpecification.zzb($c2, 32, 126)) {
                    throw new InvalidSpecificationError(String.valueOf($r2).concat(" must only contain ASCII printable characters"));
                }
                $r4.add(Character.valueOf($c2));
            }
            return $r4;
        }

        private void zzadz() throws  {
            int $i0 = 0;
            for (Integer intValue : this.eW) {
                $i0 = intValue.intValue() + $i0;
            }
            if ($i0 > this.eY) {
                throw new InvalidSpecificationError("required character count cannot be greater than the max password size");
            }
        }

        private void zzaea() throws  {
            boolean[] $r1 = new boolean[95];
            for (String toCharArray : this.eV) {
                for (char $c2 : toCharArray.toCharArray()) {
                    if ($r1[$c2 - 32]) {
                        throw new InvalidSpecificationError("character " + $c2 + " occurs in more than one required character set");
                    }
                    $r1[$c2 - 32] = true;
                }
            }
        }

        public Builder allow(@NonNull String $r1) throws  {
            this.fa.addAll(zzab($r1, "allowedChars"));
            return this;
        }

        public PasswordSpecification build() throws  {
            if (this.fa.isEmpty()) {
                throw new InvalidSpecificationError("no allowed characters specified");
            }
            zzadz();
            zzaea();
            return new PasswordSpecification(1, PasswordSpecification.zzb(this.fa), this.eV, this.eW, this.eX, this.eY);
        }

        public Builder ofLength(int $i0, int $i1) throws  {
            if ($i0 < 1) {
                throw new InvalidSpecificationError("minimumSize must be at least 1");
            } else if ($i0 > $i1) {
                throw new InvalidSpecificationError("maximumSize must be greater than or equal to minimumSize");
            } else {
                this.eX = $i0;
                this.eY = $i1;
                return this;
            }
        }

        public Builder require(@NonNull String $r1, int $i0) throws  {
            if ($i0 < 1) {
                throw new InvalidSpecificationError("count must be at least 1");
            }
            this.eV.add(PasswordSpecification.zzb(zzab($r1, "requiredChars")));
            this.eW.add(Integer.valueOf($i0));
            return this;
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    public static class InvalidSpecificationError extends Error {
        public InvalidSpecificationError(String $r1) throws  {
            super($r1);
        }
    }

    PasswordSpecification(@Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;II)V"}) int $i0, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;II)V"}) String $r1, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;II)V"}) List<String> $r2, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;II)V"}) List<Integer> $r3, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;II)V"}) int $i1, @Signature({"(I", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/util/List", "<", "Ljava/lang/Integer;", ">;II)V"}) int $i2) throws  {
        this.mVersionCode = $i0;
        this.eU = $r1;
        this.eV = Collections.unmodifiableList($r2);
        this.eW = Collections.unmodifiableList($r3);
        this.eX = $i1;
        this.eY = $i2;
    }

    private int zza(char $c0) throws  {
        return $c0 - 32;
    }

    private void zza(@Signature({"(", "Ljava/lang/String;", "I", "Ljava/util/ArrayList", "<", "Ljava/lang/Character;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "I", "Ljava/util/ArrayList", "<", "Ljava/lang/Character;", ">;)V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "I", "Ljava/util/ArrayList", "<", "Ljava/lang/Character;", ">;)V"}) ArrayList<Character> $r2) throws  {
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            $r2.add(Character.valueOf($r1.charAt(this.zzawh.nextInt($r1.length()))));
        }
    }

    private boolean zza(String $r1, char $c0) throws  {
        int $i1 = Arrays.binarySearch($r1.toCharArray(), $c0);
        return $i1 >= 0 && $i1 < $r1.length() && $r1.charAt($i1) == $c0;
    }

    private int[] zzady() throws  {
        int[] $r1 = new int[95];
        Arrays.fill($r1, -1);
        int $i1 = 0;
        for (String toCharArray : this.eV) {
            for (char $c3 : toCharArray.toCharArray()) {
                $r1[zza($c3)] = $i1;
            }
            $i1++;
        }
        return $r1;
    }

    private static String zzb(@Signature({"(", "Ljava/util/Collection", "<", "Ljava/lang/Character;", ">;)", "Ljava/lang/String;"}) Collection<Character> $r0) throws  {
        char[] $r1 = new char[$r0.size()];
        int $i0 = 0;
        for (Character charValue : $r0) {
            $r1[$i0] = charValue.charValue();
            $i0++;
        }
        return new String($r1);
    }

    private static boolean zzb(int $i0, int $i1, int $i2) throws  {
        return $i0 < $i1 || $i0 > $i2;
    }

    public int checkConformance(String $r1) throws  {
        if (TextUtils.isEmpty($r1)) {
            return 1;
        }
        if (zzb($r1.length(), this.eX, this.eY)) {
            return 1;
        }
        ArrayList $r2 = new ArrayList(this.eW);
        for (char $c1 : $r1.toCharArray()) {
            int $i3 = this.eZ[zza($c1)];
            if ($i3 < 0 || ((Integer) $r2.get($i3)).intValue() <= 0) {
                if (!zza(this.eU, $c1)) {
                    return 3;
                }
            } else {
                $r2.set($i3, Integer.valueOf(((Integer) $r2.get($i3)).intValue() - 1));
            }
        }
        Iterator $r8 = $r2.iterator();
        while ($r8.hasNext()) {
            if (((Integer) $r8.next()).intValue() > 0) {
                return 2;
            }
        }
        return 0;
    }

    @NonNull
    public String generate() throws  {
        int $i0 = this.eX + this.zzawh.nextInt((this.eY - this.eX) + 1);
        ArrayList $r1 = new ArrayList($i0);
        for (int $i1 = 0; $i1 < this.eV.size(); $i1++) {
            zza((String) this.eV.get($i1), ((Integer) this.eW.get($i1)).intValue(), $r1);
        }
        zza(this.eU, $i0 - $r1.size(), $r1);
        Collections.shuffle($r1);
        return zzb($r1);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PasswordSpecificationCreator.zza(this, $r1, $i0);
    }
}
