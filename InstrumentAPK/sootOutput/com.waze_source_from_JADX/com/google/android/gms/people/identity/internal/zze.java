package com.google.android.gms.people.identity.internal;

import android.content.Context;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData.AddressData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData.Circle;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData.EmailData;
import com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData.PhoneData;
import com.google.android.gms.people.identity.PersonFactory.RawContactData;
import com.google.android.gms.people.identity.internal.models.ImageReferenceImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.AddressesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.EmailsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.EventsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.ImagesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.InstantMessagingImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MembershipsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.NamesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.NicknamesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.NotesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.OrganizationsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.PersonMetadataImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.PhoneNumbersImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.RelationsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.TaglinesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.UrlsImpl;
import com.google.android.gms.people.identity.internal.zzc.zza;
import com.google.android.gms.people.identity.internal.zzc.zzb;
import com.google.android.gms.people.identity.internal.zzc.zzc;
import com.google.android.gms.people.identity.internal.zzc.zzd;
import com.google.android.gms.people.identity.internal.zzc.zzg;
import com.google.android.gms.people.identity.internal.zzc.zzh;
import com.google.android.gms.people.identity.internal.zzc.zzi;
import com.google.android.gms.people.identity.internal.zzc.zzj;
import com.google.android.gms.people.identity.internal.zzc.zzk;
import com.google.android.gms.people.identity.internal.zzc.zzl;
import com.google.android.gms.people.identity.internal.zzc.zzm;

/* compiled from: dalvik_source_com.waze.apk */
public abstract class zze<PersonType extends PersonImpl> {
    private void zza(android.content.Context r29, com.google.android.gms.people.identity.internal.models.PersonImpl r30, com.google.android.gms.people.identity.PersonFactory.ContactData r31) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:31:0x00b7
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r28 = this;
        r2 = new java.util.HashSet;
        r2.<init>();
        r0 = r31;
        r3 = r0.getRawData();
        r4 = r3.iterator();
    L_0x000f:
        r5 = r4.hasNext();
        if (r5 == 0) goto L_0x020c;
    L_0x0015:
        r6 = r4.next();
        r8 = r6;
        r8 = (com.google.android.gms.people.identity.PersonFactory.RawContactData) r8;
        r7 = r8;
        r10 = 0;
        r9 = r7.getData(r10);
        r5 = android.text.TextUtils.isEmpty(r9);
        if (r5 == 0) goto L_0x0034;
    L_0x0028:
        r10 = 13;
        r9 = r7.getData(r10);
        r5 = android.text.TextUtils.isEmpty(r9);
        if (r5 != 0) goto L_0x000f;
    L_0x0034:
        r9 = r7.getContactId();
        r5 = r2.contains(r9);
        if (r5 != 0) goto L_0x005f;
    L_0x003e:
        r9 = r7.getContactId();
        r2.add(r9);
        r11 = new com.google.android.gms.people.identity.internal.models.PersonImpl$MembershipsImpl;
        r11.<init>();
        r9 = r7.getContactId();
        r11 = r11.zzox(r9);
        r12 = zzah(r7);
        r11 = r11.zzj(r12);
        r0 = r30;
        r0.zza(r11);
    L_0x005f:
        r9 = r7.getMimeType();
        r13 = -1;
        r14 = r9.hashCode();
        switch(r14) {
            case -1569536764: goto L_0x007f;
            case -1328682538: goto L_0x008c;
            case -1079224304: goto L_0x00ea;
            case -1079210633: goto L_0x0122;
            case -601229436: goto L_0x00f8;
            case 456415478: goto L_0x0106;
            case 684173810: goto L_0x00d0;
            case 689862072: goto L_0x00bf;
            case 905843021: goto L_0x0114;
            case 950831081: goto L_0x0099;
            case 1409846529: goto L_0x00dd;
            case 2034973555: goto L_0x00a6;
            default: goto L_0x006b;
        };
    L_0x006b:
        goto L_0x006c;
    L_0x006c:
        switch(r13) {
            case 0: goto L_0x0071;
            case 1: goto L_0x0130;
            case 2: goto L_0x0144;
            case 3: goto L_0x0158;
            case 4: goto L_0x016a;
            case 5: goto L_0x017c;
            case 6: goto L_0x0190;
            case 7: goto L_0x01a4;
            case 8: goto L_0x01b6;
            case 9: goto L_0x01ca;
            case 10: goto L_0x01dc;
            case 11: goto L_0x01fa;
            default: goto L_0x006f;
        };
    L_0x006f:
        goto L_0x0070;
    L_0x0070:
        goto L_0x000f;
    L_0x0071:
        r0 = r28;
        r1 = r29;
        r15 = r0.zzd(r1, r7);
        r0 = r30;
        r0.zza(r15);
        goto L_0x000f;
    L_0x007f:
        r16 = "vnd.android.cursor.item/email_v2";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x008a:
        r13 = 0;
        goto L_0x006c;
    L_0x008c:
        r16 = "vnd.android.cursor.item/contact_event";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x0097:
        r13 = 1;
        goto L_0x006c;
    L_0x0099:
        r16 = "vnd.android.cursor.item/im";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x00a4:
        r13 = 2;
        goto L_0x006c;
    L_0x00a6:
        r16 = "vnd.android.cursor.item/nickname";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x00b1:
        r13 = 3;
        goto L_0x00b6;
    L_0x00b3:
        goto L_0x006c;
    L_0x00b6:
        goto L_0x006c;
        goto L_0x00bf;
    L_0x00b8:
        goto L_0x006c;
        goto L_0x00bf;
    L_0x00bc:
        goto L_0x006c;
    L_0x00bf:
        r16 = "vnd.android.cursor.item/organization";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x00ca:
        r13 = 4;
        goto L_0x00cf;
    L_0x00cc:
        goto L_0x006c;
    L_0x00cf:
        goto L_0x006c;
    L_0x00d0:
        r16 = "vnd.android.cursor.item/phone_v2";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x00db:
        r13 = 5;
        goto L_0x006c;
    L_0x00dd:
        r16 = "vnd.android.cursor.item/relation";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x00e8:
        r13 = 6;
        goto L_0x006c;
    L_0x00ea:
        r16 = "vnd.android.cursor.item/name";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x00f5:
        r13 = 7;
        goto L_0x006c;
    L_0x00f8:
        r16 = "vnd.android.cursor.item/postal-address_v2";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x0103:
        r13 = 8;
        goto L_0x00b3;
    L_0x0106:
        r16 = "vnd.android.cursor.item/website";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x0111:
        r13 = 9;
        goto L_0x00b8;
    L_0x0114:
        r16 = "vnd.android.cursor.item/photo";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x011f:
        r13 = 10;
        goto L_0x00bc;
    L_0x0122:
        r16 = "vnd.android.cursor.item/note";
        r0 = r16;
        r5 = r9.equals(r0);
        if (r5 == 0) goto L_0x006c;
    L_0x012d:
        r13 = 11;
        goto L_0x00cc;
    L_0x0130:
        r0 = r28;
        r1 = r29;
        r17 = r0.zze(r1, r7);
        goto L_0x013c;
    L_0x0139:
        goto L_0x000f;
    L_0x013c:
        r0 = r30;
        r1 = r17;
        r0.zza(r1);
        goto L_0x0139;
    L_0x0144:
        r0 = r28;
        r1 = r29;
        r18 = r0.zzf(r1, r7);
        goto L_0x0150;
    L_0x014d:
        goto L_0x000f;
    L_0x0150:
        r0 = r30;
        r1 = r18;
        r0.zza(r1);
        goto L_0x014d;
    L_0x0158:
        r0 = r28;
        r19 = r0.zzai(r7);
        goto L_0x0162;
    L_0x015f:
        goto L_0x000f;
    L_0x0162:
        r0 = r30;
        r1 = r19;
        r0.zza(r1);
        goto L_0x015f;
    L_0x016a:
        r0 = r28;
        r20 = r0.zzaj(r7);
        goto L_0x0174;
    L_0x0171:
        goto L_0x000f;
    L_0x0174:
        r0 = r30;
        r1 = r20;
        r0.zza(r1);
        goto L_0x0171;
    L_0x017c:
        r0 = r28;
        r1 = r29;
        r21 = r0.zzg(r1, r7);
        goto L_0x0188;
    L_0x0185:
        goto L_0x000f;
    L_0x0188:
        r0 = r30;
        r1 = r21;
        r0.zza(r1);
        goto L_0x0185;
    L_0x0190:
        r0 = r28;
        r1 = r29;
        r22 = r0.zzh(r1, r7);
        goto L_0x019c;
    L_0x0199:
        goto L_0x000f;
    L_0x019c:
        r0 = r30;
        r1 = r22;
        r0.zza(r1);
        goto L_0x0199;
    L_0x01a4:
        r0 = r28;
        r23 = r0.zzak(r7);
        goto L_0x01ae;
    L_0x01ab:
        goto L_0x000f;
    L_0x01ae:
        r0 = r30;
        r1 = r23;
        r0.zza(r1);
        goto L_0x01ab;
    L_0x01b6:
        r0 = r28;
        r1 = r29;
        r24 = r0.zzi(r1, r7);
        goto L_0x01c2;
    L_0x01bf:
        goto L_0x000f;
    L_0x01c2:
        r0 = r30;
        r1 = r24;
        r0.zza(r1);
        goto L_0x01bf;
    L_0x01ca:
        r0 = r28;
        r25 = r0.zzal(r7);
        goto L_0x01d4;
    L_0x01d1:
        goto L_0x000f;
    L_0x01d4:
        r0 = r30;
        r1 = r25;
        r0.zza(r1);
        goto L_0x01d1;
    L_0x01dc:
        r10 = 13;
        r9 = r7.getData(r10);
        r5 = android.text.TextUtils.isEmpty(r9);
        if (r5 != 0) goto L_0x000f;
    L_0x01e8:
        r0 = r28;
        r26 = r0.zzan(r7);
        goto L_0x01f2;
    L_0x01ef:
        goto L_0x000f;
    L_0x01f2:
        r0 = r30;
        r1 = r26;
        r0.zza(r1);
        goto L_0x01ef;
    L_0x01fa:
        r0 = r28;
        r27 = r0.zzam(r7);
        goto L_0x0204;
    L_0x0201:
        goto L_0x000f;
    L_0x0204:
        r0 = r30;
        r1 = r27;
        r0.zza(r1);
        goto L_0x0201;
    L_0x020c:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.zze.zza(android.content.Context, com.google.android.gms.people.identity.internal.models.PersonImpl, com.google.android.gms.people.identity.PersonFactory$ContactData):void");
    }

    private void zza(PersonImpl $r1, OfflineDatabaseData $r2) throws  {
        if ($r2 != null) {
            PersonMetadataImpl $r3;
            if ($r1.zzcds()) {
                $r3 = (PersonMetadataImpl) $r1.getMetadata();
            } else {
                $r3 = new PersonMetadataImpl();
                $r1.zza($r3);
            }
            switch ($r2.getProfileType()) {
                case 1:
                    $r3.zzqd("person");
                    break;
                case 2:
                    $r3.zzqd("page");
                    break;
                default:
                    break;
            }
            if ($r2.getCircles() != null) {
                for (Circle $r7 : $r2.getCircles()) {
                    $r3.zzqc($r7.getId());
                    $r1.zza(new MembershipsImpl().zzow($r7.getId()).zzj(new MetadataImpl().zzoz(Scopes.PROFILE).zzpb($r2.getGaiaId())));
                }
            }
            $r1.zza(new NamesImpl().zzpc($r2.getDisplayName()).zzk(new MetadataImpl().zzoz(Scopes.PROFILE).zzct(true).zzcv(false).zzcu($r2.getNameVerified()))).zzno($r2.getGaiaId()).zza(new TaglinesImpl().zzqr($r2.getTagline()).zzt(new MetadataImpl().zzoz(Scopes.PROFILE).zzct(true).zzcv(false))).zza(new ImagesImpl().zzb(new ImageReferenceImpl().zznm($r2.getCompressedAvatarUrl()).zzabg(1)).zzcs(true).zzh(new MetadataImpl().zzoz(Scopes.PROFILE).zzct(true).zzcv(false)));
            if ($r2.getAddresses() != null) {
                for (AddressData $r17 : $r2.getAddresses()) {
                    $r1.zza(new AddressesImpl().zzoa($r17.getAddress()).zznw($r17.getType()).zzb(new MetadataImpl().zzoz(Scopes.PROFILE).zzct(true).zzcv(false)));
                }
            }
            if ($r2.getPhones() != null) {
                for (PhoneData $r19 : $r2.getPhones()) {
                    $r1.zza(new PhoneNumbersImpl().zzqj($r19.getPhone()).zzqh($r19.getType()).zzp(new MetadataImpl().zzoz(Scopes.PROFILE).zzct(true).zzcv(false)));
                }
            }
            if ($r2.getEmails() != null) {
                for (EmailData $r21 : $r2.getEmails()) {
                    $r1.zza(new EmailsImpl().zzok($r21.getEmailAddress()).zzoi($r21.getType()).zze(new MetadataImpl().zzoz(Scopes.PROFILE).zzct(true).zzcv(false)));
                }
            }
        }
    }

    private static MetadataImpl zzah(RawContactData $r0) throws  {
        return new MetadataImpl().zzct($r0.isPrimary()).zzcv(!$r0.isReadOnly()).zzoz("cp2").zzpb($r0.getContactId()).zzabv(Integer.parseInt($r0.getRawContactId()));
    }

    private NicknamesImpl zzai(RawContactData $r1) throws  {
        return new NicknamesImpl().zzl(zzah($r1)).zzpo(zzh.zzo($r1)).zzpn(zzh.zza($r1));
    }

    private OrganizationsImpl zzaj(RawContactData $r1) throws  {
        return new OrganizationsImpl().zzo(zzah($r1)).zzpw(zzj.zzo($r1)).zzqb(zzj.zza($r1)).zzqa(zzj.zzz($r1)).zzpr(zzj.zzaa($r1)).zzps(zzj.zzab($r1)).zzpz(zzj.zzac($r1)).zzpx(zzj.zzad($r1)).zzpv(zzj.zzae($r1));
    }

    private NamesImpl zzak(RawContactData $r1) throws  {
        return new NamesImpl().zzk(zzah($r1)).zzpc(zzg.zzq($r1)).zzpf(zzg.zzr($r1)).zzpd(zzg.zzs($r1)).zzpg(zzg.zzt($r1)).zzpi(zzg.zzu($r1)).zzph(zzg.zzv($r1)).zzpk(zzg.zzw($r1)).zzpj(zzg.zzx($r1));
    }

    private UrlsImpl zzal(RawContactData $r1) throws  {
        return new UrlsImpl().zzu(zzah($r1)).zzqu(zzm.zzm($r1)).zzqt(zzm.zza($r1));
    }

    private NotesImpl zzam(RawContactData $r1) throws  {
        return new NotesImpl().zzm(zzah($r1)).zzpp(zzi.zzy($r1));
    }

    private ImagesImpl zzan(RawContactData $r1) throws  {
        return new ImagesImpl().zzh(zzah($r1)).zzb(new ImageReferenceImpl().zznm(com.google.android.gms.people.identity.internal.zzc.zze.zzm($r1)).zzabg(2));
    }

    private EmailsImpl zzd(Context $r1, RawContactData $r2) throws  {
        return new EmailsImpl().zze(zzah($r2)).zzok(zzb.zzi($r2)).zzoj(zzb.zza($r2)).zzoi(zzb.zza($r1, $r2)).zzabu($r2.getTimesUsed());
    }

    private EventsImpl zze(Context $r1, RawContactData $r2) throws  {
        return new EventsImpl().zzf(zzah($r2)).zzon(zzc.zzj($r2)).zzom(zzc.zzb($r1, $r2)).zzol(zzc.zzk($r2));
    }

    private InstantMessagingImpl zzf(Context $r1, RawContactData $r2) throws  {
        return new InstantMessagingImpl().zzi(zzah($r2)).zzou(zzd.zzi($r2)).zzot(zzd.zza($r2)).zzor(zzd.zza($r1, $r2)).zzos(zzd.zzl($r2)).zzoq(zzd.zzc($r1, $r2));
    }

    private PhoneNumbersImpl zzg(Context $r1, RawContactData $r2) throws  {
        return new PhoneNumbersImpl().zzp(zzah($r2)).zzqj(zzk.zzaf($r2)).zzqi(zzk.zza($r2)).zzqh(zzk.zza($r1, $r2)).zzabw($r2.getTimesUsed());
    }

    private RelationsImpl zzh(Context $r1, RawContactData $r2) throws  {
        return new RelationsImpl().zzr(zzah($r2)).zzqn(zzl.zzag($r2)).zzqm(zzl.zza($r2)).zzql(zzl.zza($r1, $r2));
    }

    private AddressesImpl zzi(Context $r1, RawContactData $r2) throws  {
        return new AddressesImpl().zzb(zzah($r2)).zzob(zza.zza($r2)).zznw(zza.zza($r1, $r2)).zzoa(zza.zzb($r2)).zznx(zza.zzc($r2)).zznt(zza.zzd($r2)).zznz(zza.zze($r2)).zzny(zza.zzf($r2)).zznu(zza.zzg($r2)).zznv(zza.zzh($r2));
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public PersonType zza(@dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) android.content.Context r18, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) java.lang.Object r19, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) com.google.android.gms.people.identity.PersonFactory.ServiceData r20, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) com.google.android.gms.people.identity.PersonFactory.ContactData r21, @dalvik.annotation.Signature({"(", "Landroid/content/Context;", "Ljava/lang/Object;", "Lcom/google/android/gms/people/identity/PersonFactory$ServiceData;", "Lcom/google/android/gms/people/identity/PersonFactory$ContactData;", "Lcom/google/android/gms/people/identity/PersonFactory$OfflineDatabaseData;", ")TPersonType;"}) com.google.android.gms.people.identity.PersonFactory.OfflineDatabaseData r22) throws  {
        /*
        r17 = this;
        r3 = 0;
        r0 = r17;
        r4 = r0.zzcda();
        if (r20 == 0) goto L_0x0034;
    L_0x0009:
        r0 = r20;
        r5 = r0.blob;
        if (r5 == 0) goto L_0x0034;
    L_0x000f:
        r0 = r20;
        r6 = r0.format;
        switch(r6) {
            case 2: goto L_0x0020;
            case 3: goto L_0x0017;
            case 4: goto L_0x004b;
            default: goto L_0x0016;
        };
    L_0x0016:
        goto L_0x0017;
    L_0x0017:
        r7 = "DefaultPersonFactory";
        r8 = "Unrecognized data format";
        com.google.android.gms.people.internal.zzp.zzan(r7, r8);	 Catch:{ ParseException -> 0x007f }
        r9 = 0;
        return r9;
    L_0x0020:
        r10 = new com.google.android.gms.plus.service.v2whitelisted.models.Person;	 Catch:{ ParseException -> 0x007f }
        r10.<init>();	 Catch:{ ParseException -> 0x007f }
        r0 = r20;
        r6 = r0.responseCode;
        r0 = r20;
        r5 = r0.blob;	 Catch:{ ParseException -> 0x007f }
        r10.parseNetworkResponse(r6, r5);	 Catch:{ ParseException -> 0x007f }
        com.google.android.gms.people.identity.internal.models.zzg.zza(r10, r4);	 Catch:{ ParseException -> 0x007f }
        r3 = 1;
    L_0x0034:
        if (r3 != 0) goto L_0x003f;
    L_0x0036:
        if (r22 == 0) goto L_0x003f;
    L_0x0038:
        r0 = r17;
        r1 = r22;
        r0.zza(r4, r1);
    L_0x003f:
        if (r21 == 0) goto L_0x004a;
    L_0x0041:
        r0 = r17;
        r1 = r18;
        r2 = r21;
        r0.zza(r1, r4, r2);
    L_0x004a:
        return r4;
    L_0x004b:
        r11 = new com.google.android.gms.people.identity.internal.models.zzh;
        r11.<init>();	 Catch:{ ParseException -> 0x007f }
        r0 = r20;
        r6 = r0.responseCode;
        r0 = r20;
        r5 = r0.blob;	 Catch:{ ParseException -> 0x007f }
        r11.parseNetworkResponse(r6, r5);	 Catch:{ ParseException -> 0x007f }
        r12 = r11.zzcde();	 Catch:{ ParseException -> 0x007f }
        if (r12 == 0) goto L_0x0034;
    L_0x0061:
        r13 = r11.zzcdd();	 Catch:{ ParseException -> 0x007f }
        r6 = r13.size();	 Catch:{ ParseException -> 0x007f }
        if (r6 <= 0) goto L_0x0034;
    L_0x006b:
        r13 = r11.zzcdd();	 Catch:{ ParseException -> 0x007f }
        r14 = 0;
        r19 = r13.get(r14);	 Catch:{ ParseException -> 0x007f }
        r15 = r19;
        r15 = (com.google.android.gms.plus.service.v2whitelisted.models.Person) r15;	 Catch:{ ParseException -> 0x007f }
        r10 = r15;
        com.google.android.gms.people.identity.internal.models.zzg.zza(r10, r4);	 Catch:{ ParseException -> 0x007f }
        r3 = 1;
        goto L_0x0034;
    L_0x007f:
        r16 = move-exception;
        r7 = "DefaultPersonFactory";
        r8 = "ParseException";
        r0 = r16;
        com.google.android.gms.people.internal.zzp.zzc(r7, r8, r0);
        r9 = 0;
        return r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.people.identity.internal.zze.zza(android.content.Context, java.lang.Object, com.google.android.gms.people.identity.PersonFactory$ServiceData, com.google.android.gms.people.identity.PersonFactory$ContactData, com.google.android.gms.people.identity.PersonFactory$OfflineDatabaseData):PersonType");
    }

    protected abstract PersonType zzcda() throws ;
}
