package com.google.android.gms.people.identity.internal.models;

import com.google.android.gms.people.identity.internal.models.PersonImpl.AboutsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.AddressesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.BirthdaysImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.BraggingRightsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.CoverPhotosImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.CustomFieldsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.EmailsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.EventsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.GendersImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.ImagesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.InstantMessagingImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.LegacyFieldsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MembershipsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.MetadataImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.NamesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.NicknamesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.OccupationsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.OrganizationsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.PersonMetadataImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.PhoneNumbersImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.PlacesLivedImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.ProfileOwnerStatsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.RelationsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.SkillsImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.SortKeysImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.TaglinesImpl;
import com.google.android.gms.people.identity.internal.models.PersonImpl.UrlsImpl;
import com.google.android.gms.plus.service.v2whitelisted.models.Mergedpeoplemetadata;
import com.google.android.gms.plus.service.v2whitelisted.models.Person;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Abouts;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Addresses;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Birthdays;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.BraggingRights;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.CoverPhotos;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.CustomFields;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Emails;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Events;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Genders;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Images;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.InstantMessaging;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.LegacyFields;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Memberships;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Metadata.ProfileOwnerStats;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Names;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Nicknames;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Occupations;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Organizations;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.PhoneNumbers;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.PlacesLived;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Relations;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Skills;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.SortKeys;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Taglines;
import com.google.android.gms.plus.service.v2whitelisted.models.Person.Urls;

/* compiled from: dalvik_source_com.waze.apk */
public class zzg {
    private static AboutsImpl zza(Abouts $r0) throws  {
        AboutsImpl $r1 = new AboutsImpl();
        if ($r0.zzcds()) {
            $r1.zza(zza($r0.zzcit()));
        }
        if ($r0.hasType()) {
            $r1.zznr($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzns($r0.getValue());
        return $r1;
    }

    private static AddressesImpl zza(Addresses $r0) throws  {
        AddressesImpl $r1 = new AddressesImpl();
        if ($r0.zzcds()) {
            $r1.zzb(zza($r0.zzcit()));
        }
        if ($r0.zzciv()) {
            $r1.zznt($r0.zzciu());
        }
        if ($r0.zzciw()) {
            $r1.zznu($r0.getCountry());
        }
        if ($r0.zzciy()) {
            $r1.zznx($r0.zzcix());
        }
        if ($r0.zzciz()) {
            $r1.zzny($r0.getPostalCode());
        }
        if ($r0.zzcja()) {
            $r1.zznz($r0.getRegion());
        }
        if ($r0.zzcjb()) {
            $r1.zzoa($r0.getStreetAddress());
        }
        if ($r0.hasType()) {
            $r1.zzob($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzoc($r0.getValue());
        return $r1;
    }

    private static BirthdaysImpl zza(Birthdays $r0) throws  {
        BirthdaysImpl $r1 = new BirthdaysImpl();
        if ($r0.zzcds()) {
            $r1.zzc(zza($r0.zzcit()));
        }
        if (!$r0.zzcjd()) {
            return $r1;
        }
        $r1.zzod($r0.zzcjc());
        return $r1;
    }

    private static BraggingRightsImpl zza(BraggingRights $r0) throws  {
        BraggingRightsImpl $r1 = new BraggingRightsImpl();
        if ($r0.zzcds()) {
            $r1.zzd(zza($r0.zzcit()));
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzoe($r0.getValue());
        return $r1;
    }

    private static CoverPhotosImpl zza(CoverPhotos $r0) throws  {
        CoverPhotosImpl $r1 = new CoverPhotosImpl();
        if ($r0.hasHeight()) {
            $r1.zzabs($r0.getHeight());
        }
        if ($r0.hasId()) {
            $r1.zzof($r0.getId());
        }
        if ($r0.hasUrl()) {
            $r1.zza(new ImageReferenceImpl().zznm($r0.getUrl()).zzabg(1));
        }
        if ($r0.hasWidth()) {
            $r1.zzabt($r0.getWidth());
        }
        if (!$r0.zzcje()) {
            return $r1;
        }
        $r1.zzcr($r0.isDefault());
        return $r1;
    }

    private static CustomFieldsImpl zza(CustomFields $r0) throws  {
        CustomFieldsImpl $r1 = new CustomFieldsImpl();
        if ($r0.hasKey()) {
            $r1.zzog($r0.getKey());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzoh($r0.getValue());
        return $r1;
    }

    private static EmailsImpl zza(Emails $r0) throws  {
        EmailsImpl $r1 = new EmailsImpl();
        if ($r0.zzcds()) {
            $r1.zze(zza($r0.zzcit()));
        }
        if ($r0.zzcjg()) {
            $r1.zzoi($r0.zzcjf());
        }
        if ($r0.hasType()) {
            $r1.zzoj($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzok($r0.getValue());
        return $r1;
    }

    private static EventsImpl zza(Events $r0) throws  {
        EventsImpl $r1 = new EventsImpl();
        if ($r0.zzcds()) {
            $r1.zzf(zza($r0.zzcit()));
        }
        if ($r0.zzcjg()) {
            $r1.zzol($r0.zzcjf());
        }
        if ($r0.hasType()) {
            $r1.zzom($r0.getType());
        }
        if (!$r0.zzcjd()) {
            return $r1;
        }
        $r1.zzon($r0.zzcjc());
        return $r1;
    }

    private static GendersImpl zza(Genders $r0) throws  {
        GendersImpl $r1 = new GendersImpl();
        if ($r0.zzcds()) {
            $r1.zzg(zza($r0.zzcit()));
        }
        if ($r0.zzcjh()) {
            $r1.zzoo($r0.getFormattedValue());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzop($r0.getValue());
        return $r1;
    }

    private static ImagesImpl zza(Images $r0) throws  {
        ImagesImpl $r1 = new ImagesImpl();
        if ($r0.zzcds()) {
            $r1.zzh(zza($r0.zzcit()));
        }
        if ($r0.hasUrl()) {
            $r1.zzb(new ImageReferenceImpl().zznm($r0.getUrl()).zzabg(1));
        }
        if (!$r0.zzcje()) {
            return $r1;
        }
        $r1.zzcs($r0.isDefault());
        return $r1;
    }

    private static InstantMessagingImpl zza(InstantMessaging $r0) throws  {
        InstantMessagingImpl $r1 = new InstantMessagingImpl();
        if ($r0.zzcds()) {
            $r1.zzi(zza($r0.zzcit()));
        }
        if ($r0.zzcjj()) {
            $r1.zzoq($r0.zzcji());
        }
        if ($r0.zzcjg()) {
            $r1.zzor($r0.zzcjf());
        }
        if ($r0.zzcjk()) {
            $r1.zzos($r0.getProtocol());
        }
        if ($r0.hasType()) {
            $r1.zzot($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzou($r0.getValue());
        return $r1;
    }

    private static LegacyFieldsImpl zza(LegacyFields $r0) throws  {
        LegacyFieldsImpl $r1 = new LegacyFieldsImpl();
        if (!$r0.zzcjm()) {
            return $r1;
        }
        $r1.zzov($r0.zzcjl());
        return $r1;
    }

    private static MembershipsImpl zza(Memberships $r0) throws  {
        MembershipsImpl $r1 = new MembershipsImpl();
        if ($r0.zzcds()) {
            $r1.zzj(zza($r0.zzcit()));
        }
        if ($r0.zzcjo()) {
            $r1.zzow($r0.zzcjn());
        }
        if ($r0.zzcjq()) {
            $r1.zzox($r0.zzcjp());
        }
        if (!$r0.zzcjs()) {
            return $r1;
        }
        $r1.zzoy($r0.zzcjr());
        return $r1;
    }

    private static MetadataImpl zza(Mergedpeoplemetadata $r0) throws  {
        MetadataImpl $r1 = new MetadataImpl();
        $r1.zzoz($r0.zzchs());
        $r1.zzpa($r0.zzcht());
        $r1.zzpb($r0.getContainerId());
        $r1.zzct($r0.isPrimary());
        return $r1;
    }

    private static NamesImpl zza(Names $r0) throws  {
        NamesImpl $r1 = new NamesImpl();
        if ($r0.zzcds()) {
            $r1.zzk(zza($r0.zzcit()));
        }
        if ($r0.hasDisplayName()) {
            $r1.zzpc($r0.getDisplayName());
        }
        if ($r0.hasFamilyName()) {
            $r1.zzpd($r0.getFamilyName());
        }
        if ($r0.hasFormatted()) {
            $r1.zzpe($r0.getFormatted());
        }
        if ($r0.hasGivenName()) {
            $r1.zzpf($r0.getGivenName());
        }
        if ($r0.hasHonorificPrefix()) {
            $r1.zzpg($r0.getHonorificPrefix());
        }
        if ($r0.hasHonorificSuffix()) {
            $r1.zzph($r0.getHonorificSuffix());
        }
        if ($r0.hasMiddleName()) {
            $r1.zzpi($r0.getMiddleName());
        }
        if ($r0.zzckv()) {
            $r1.zzpj($r0.zzcku());
        }
        if ($r0.zzckx()) {
            $r1.zzpk($r0.zzckw());
        }
        if ($r0.zzckz()) {
            $r1.zzpl($r0.zzcky());
        }
        if (!$r0.zzclb()) {
            return $r1;
        }
        $r1.zzpm($r0.zzcla());
        return $r1;
    }

    private static NicknamesImpl zza(Nicknames $r0) throws  {
        NicknamesImpl $r1 = new NicknamesImpl();
        if ($r0.zzcds()) {
            $r1.zzl(zza($r0.zzcit()));
        }
        if ($r0.hasType()) {
            $r1.zzpn($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzpo($r0.getValue());
        return $r1;
    }

    private static OccupationsImpl zza(Occupations $r0) throws  {
        OccupationsImpl $r1 = new OccupationsImpl();
        if ($r0.zzcds()) {
            $r1.zzn(zza($r0.zzcit()));
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzpq($r0.getValue());
        return $r1;
    }

    private static OrganizationsImpl zza(Organizations $r0) throws  {
        OrganizationsImpl $r1 = new OrganizationsImpl();
        if ($r0.zzcds()) {
            $r1.zzo(zza($r0.zzcit()));
        }
        if ($r0.zzcld()) {
            $r1.zzcw($r0.zzclc());
        }
        if ($r0.hasDepartment()) {
            $r1.zzpr($r0.getDepartment());
        }
        if ($r0.hasDescription()) {
            $r1.zzps($r0.getDescription());
        }
        if ($r0.zzcle()) {
            $r1.zzpt($r0.getDomain());
        }
        if ($r0.hasEndDate()) {
            $r1.zzpu($r0.getEndDate());
        }
        if ($r0.hasLocation()) {
            $r1.zzpv($r0.getLocation());
        }
        if ($r0.hasName()) {
            $r1.zzpw($r0.getName());
        }
        if ($r0.zzclg()) {
            $r1.zzpx($r0.zzclf());
        }
        if ($r0.hasStartDate()) {
            $r1.zzpy($r0.getStartDate());
        }
        if ($r0.zzclh()) {
            $r1.zzpz($r0.getSymbol());
        }
        if ($r0.hasTitle()) {
            $r1.zzqa($r0.getTitle());
        }
        if (!$r0.hasType()) {
            return $r1;
        }
        $r1.zzqb($r0.getType());
        return $r1;
    }

    private static PersonMetadataImpl zza(Metadata $r0) throws  {
        PersonMetadataImpl $r1 = new PersonMetadataImpl();
        if ($r0.zzcju()) {
            $r1.zzv($r0.zzcjt());
        }
        if ($r0.zzcjw()) {
            $r1.zzw($r0.zzcjv());
        }
        if ($r0.zzcjy()) {
            $r1.zzx($r0.getCircles());
        }
        if ($r0.zzcka()) {
            $r1.zzy($r0.zzcjz());
        }
        if ($r0.zzcke()) {
            $r1.zzz($r0.zzckd());
        }
        if ($r0.zzcki()) {
            $r1.zzaa($r0.zzckh());
        }
        if ($r0.hasObjectType()) {
            $r1.zzqd($r0.zzbid());
        }
        if ($r0.zzckj()) {
            $r1.zzqe($r0.getOwnerId());
        }
        if ($r0.zzckl()) {
            $r1.zzab($r0.zzckk());
        }
        if ($r0.zzckn()) {
            $r1.zzqf($r0.zzckm());
        }
        if ($r0.zzckp()) {
            $r1.zza(zza($r0.zzcko()));
        }
        if ($r0.zzcjx()) {
            $r1.zzcx($r0.isBlocked());
        }
        if ($r0.zzckc()) {
            $r1.zzcy($r0.zzckb());
        }
        if (!$r0.zzckg()) {
            return $r1;
        }
        $r1.zzcz($r0.zzckf());
        return $r1;
    }

    private static PhoneNumbersImpl zza(PhoneNumbers $r0) throws  {
        PhoneNumbersImpl $r1 = new PhoneNumbersImpl();
        if ($r0.zzcds()) {
            $r1.zzp(zza($r0.zzcit()));
        }
        if ($r0.zzclj()) {
            $r1.zzqg($r0.zzcli());
        }
        if ($r0.zzcjg()) {
            $r1.zzqh($r0.zzcjf());
        }
        if ($r0.hasType()) {
            $r1.zzqi($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzqj($r0.getValue());
        return $r1;
    }

    private static PlacesLivedImpl zza(PlacesLived $r0) throws  {
        PlacesLivedImpl $r1 = new PlacesLivedImpl();
        if ($r0.zzcds()) {
            $r1.zzq(zza($r0.zzcit()));
        }
        if ($r0.zzcld()) {
            $r1.zzda($r0.zzclc());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzqk($r0.getValue());
        return $r1;
    }

    private static ProfileOwnerStatsImpl zza(ProfileOwnerStats $r0) throws  {
        ProfileOwnerStatsImpl $r1 = new ProfileOwnerStatsImpl();
        if ($r0.zzckr()) {
            $r1.zzcb($r0.zzckq());
        }
        if (!$r0.zzckt()) {
            return $r1;
        }
        $r1.zzcc($r0.zzcks());
        return $r1;
    }

    private static RelationsImpl zza(Relations $r0) throws  {
        RelationsImpl $r1 = new RelationsImpl();
        if ($r0.zzcds()) {
            $r1.zzr(zza($r0.zzcit()));
        }
        if ($r0.zzcjg()) {
            $r1.zzql($r0.zzcjf());
        }
        if ($r0.hasType()) {
            $r1.zzqm($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzqn($r0.getValue());
        return $r1;
    }

    private static SkillsImpl zza(Skills $r0) throws  {
        SkillsImpl $r1 = new SkillsImpl();
        if ($r0.zzcds()) {
            $r1.zzs(zza($r0.zzcit()));
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzqo($r0.getValue());
        return $r1;
    }

    private static SortKeysImpl zza(SortKeys $r0) throws  {
        SortKeysImpl $r1 = new SortKeysImpl();
        if ($r0.zzcll()) {
            $r1.zzqp($r0.zzclk());
        }
        if (!$r0.hasName()) {
            return $r1;
        }
        $r1.zzqq($r0.getName());
        return $r1;
    }

    private static TaglinesImpl zza(Taglines $r0) throws  {
        TaglinesImpl $r1 = new TaglinesImpl();
        if ($r0.zzcds()) {
            $r1.zzt(zza($r0.zzcit()));
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzqr($r0.getValue());
        return $r1;
    }

    private static UrlsImpl zza(Urls $r0) throws  {
        UrlsImpl $r1 = new UrlsImpl();
        if ($r0.zzcds()) {
            $r1.zzu(zza($r0.zzcit()));
        }
        if ($r0.zzcjg()) {
            $r1.zzqs($r0.zzcjf());
        }
        if ($r0.hasType()) {
            $r1.zzqt($r0.getType());
        }
        if (!$r0.hasValue()) {
            return $r1;
        }
        $r1.zzqu($r0.getValue());
        return $r1;
    }

    public static PersonImpl zza(Person $r0, PersonImpl $r1) throws  {
        if ($r0.zzchu()) {
            for (Abouts zza : $r0.getAbouts()) {
                $r1.zza(zza(zza));
            }
        }
        if ($r0.zzchv()) {
            for (Addresses zza2 : $r0.getAddresses()) {
                $r1.zza(zza(zza2));
            }
        }
        if ($r0.zzchw()) {
            for (Birthdays zza3 : $r0.getBirthdays()) {
                $r1.zza(zza(zza3));
            }
        }
        if ($r0.hasBraggingRights()) {
            for (BraggingRights zza4 : $r0.getBraggingRights()) {
                $r1.zza(zza(zza4));
            }
        }
        if ($r0.zzchx()) {
            for (CoverPhotos zza5 : $r0.getCoverPhotos()) {
                $r1.zza(zza(zza5));
            }
        }
        if ($r0.zzchy()) {
            for (CustomFields zza6 : $r0.getCustomFields()) {
                $r1.zza(zza(zza6));
            }
        }
        if ($r0.zzchz()) {
            for (Emails zza7 : $r0.getEmails()) {
                $r1.zza(zza(zza7));
            }
        }
        if ($r0.zzcia()) {
            $r1.zznn($r0.getEtag());
        }
        if ($r0.zzcib()) {
            for (Events zza8 : $r0.getEvents()) {
                $r1.zza(zza(zza8));
            }
        }
        if ($r0.zzcic()) {
            for (Genders zza9 : $r0.getGenders()) {
                $r1.zza(zza(zza9));
            }
        }
        if ($r0.hasId()) {
            $r1.zzno($r0.getId());
        }
        if ($r0.hasImages()) {
            for (Images zza10 : $r0.getImages()) {
                $r1.zza(zza(zza10));
            }
        }
        if ($r0.zzcid()) {
            for (InstantMessaging zza11 : $r0.getInstantMessaging()) {
                $r1.zza(zza(zza11));
            }
        }
        if ($r0.hasLanguage()) {
            $r1.zznp($r0.getLanguage());
        }
        if ($r0.zzcif()) {
            $r1.zza(zza($r0.zzcie()));
        }
        if ($r0.zzcig()) {
            for (Memberships zza12 : $r0.getMemberships()) {
                $r1.zza(zza(zza12));
            }
        }
        if ($r0.zzcds()) {
            $r1.zza(zza($r0.zzcih()));
        }
        if ($r0.zzcii()) {
            for (Names zza13 : $r0.getNames()) {
                $r1.zza(zza(zza13));
            }
        }
        if ($r0.zzcij()) {
            for (Nicknames zza14 : $r0.getNicknames()) {
                $r1.zza(zza(zza14));
            }
        }
        if ($r0.zzcik()) {
            for (Occupations zza15 : $r0.getOccupations()) {
                $r1.zza(zza(zza15));
            }
        }
        if ($r0.hasOrganizations()) {
            for (Organizations zza16 : $r0.getOrganizations()) {
                $r1.zza(zza(zza16));
            }
        }
        if ($r0.zzcil()) {
            for (PhoneNumbers zza17 : $r0.getPhoneNumbers()) {
                $r1.zza(zza(zza17));
            }
        }
        if ($r0.hasPlacesLived()) {
            for (PlacesLived zza18 : $r0.getPlacesLived()) {
                $r1.zza(zza(zza18));
            }
        }
        if ($r0.zzcin()) {
            $r1.zznq($r0.zzcim());
        }
        if ($r0.zzcio()) {
            for (Relations zza19 : $r0.getRelations()) {
                $r1.zza(zza(zza19));
            }
        }
        if ($r0.zzcip()) {
            for (Skills zza20 : $r0.getSkills()) {
                $r1.zza(zza(zza20));
            }
        }
        if ($r0.zzcir()) {
            $r1.zza(zza($r0.zzciq()));
        }
        if ($r0.zzcis()) {
            for (Taglines zza21 : $r0.getTaglines()) {
                $r1.zza(zza(zza21));
            }
        }
        if (!$r0.hasUrls()) {
            return $r1;
        }
        for (Urls zza22 : $r0.getUrls()) {
            $r1.zza(zza(zza22));
        }
        return $r1;
    }
}
