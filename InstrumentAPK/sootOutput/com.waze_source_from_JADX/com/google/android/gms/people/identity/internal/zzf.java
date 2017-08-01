package com.google.android.gms.people.identity.internal;

import com.google.android.gms.people.identity.PersonFactory.ContactData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData;
import com.google.android.gms.people.identity.PersonFactory.RawContactData;
import com.google.android.gms.people.identity.PersonFactory.ServiceData;
import com.google.android.gms.people.identity.internal.models.ImageReferenceImpl;
import com.google.android.gms.people.identity.internal.models.PersonReferenceImpl;
import com.google.android.gms.people.identity.internal.models.zzh;
import com.google.android.gms.people.identity.models.PersonReference;
import com.google.android.gms.people.internal.zzp;
import com.google.android.gms.plus.service.v2whitelisted.models.Person;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Images;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Names;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public class zzf extends zzg<PersonReference> {
    protected static Names zzao(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;", ">;)", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Names;"}) List<Names> $r0) throws  {
        if ($r0 == null || $r0.isEmpty()) {
            return null;
        }
        for (Names $r1 : $r0) {
            if ($r1.zzcit() != null && $r1.zzcit().isPrimary()) {
                return $r1;
            }
        }
        return (Names) $r0.get(0);
    }

    protected static Images zzap(@Signature({"(", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;", ">;)", "Lcom/google/android/gms/plus/service/v2whitelisted/models/Person$Images;"}) List<Images> $r0) throws  {
        if ($r0 == null || $r0.isEmpty()) {
            return null;
        }
        for (Images $r1 : $r0) {
            if ($r1.zzcit() != null && $r1.zzcit().isPrimary()) {
                return $r1;
            }
        }
        return (Images) $r0.get(0);
    }

    protected String zza(PersonReference $r1) throws  {
        return $r1.getQualifiedId();
    }

    protected List<PersonReference> zza(@Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/people/identity/models/PersonReference;", ">;"}) OfflineDatabaseData offlineDatabaseData) throws  {
        throw new IllegalStateException("Not Implemented");
    }

    protected List<PersonReference> zza(@Signature({"(", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/people/identity/models/PersonReference;", ">;"}) ServiceData $r1) throws  {
        ArrayList $r2 = new ArrayList();
        if ($r1 == null || $r1.blob == null) {
            return $r2;
        }
        try {
            zzh $r6 = new zzh();
            $r6.parseNetworkResponse($r1.responseCode, $r1.blob);
            for (Person $r10 : $r6.zzcdd()) {
                Names $r11 = zzao($r10.getNames());
                Images $r12 = zzap($r10.getImages());
                PersonReferenceImpl $r3 = new PersonReferenceImpl();
                String $r4 = "g:";
                String $r13 = String.valueOf($r10.getId());
                if ($r13.length() != 0) {
                    $r4 = $r4.concat($r13);
                } else {
                    String str = new String("g:");
                }
                $r3 = $r3.zzqw($r4);
                if ($r11 != null) {
                    $r3.zzqv($r11.getDisplayName());
                }
                if ($r12 != null) {
                    $r3.zzc(new ImageReferenceImpl().zznm($r12.getUrl()));
                }
                $r2.add($r3);
            }
            return $r2;
        } catch (Throwable $r15) {
            zzp.zzc("DefaultPersonFactory", "ParseException", $r15);
            return $r2;
        }
    }

    protected List<PersonReference> zza(@Signature({"([", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", ")", "Ljava/util/List", "<", "Lcom/google/android/gms/people/identity/models/PersonReference;", ">;"}) ContactData[] $r1) throws  {
        ArrayList $r2 = new ArrayList($r1.length);
        for (ContactData $r3 : $r1) {
            RawContactData $r6 = (RawContactData) $r3.getRawData().get(0);
            String $r7 = com.google.android.gms.people.identity.internal.zzc.zzf.zzp($r6);
            ImageReferenceImpl $r8 = null;
            if ($r7 != null) {
                $r8 = new ImageReferenceImpl().zznm($r7).zzabg(2);
            }
            $r2.add(new PersonReferenceImpl().zzqw(zzc.zznf(com.google.android.gms.people.identity.internal.zzc.zzf.zzn($r6))).zzqv(com.google.android.gms.people.identity.internal.zzc.zzf.zzo($r6)).zzc($r8));
        }
        return $r2;
    }

    protected /* synthetic */ String zzas(Object $r2) throws  {
        return zza((PersonReference) $r2);
    }
}
