package com.google.android.gms.plus.internal.model.people;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.common.data.zzc;
import com.google.android.gms.plus.internal.model.people.PersonEntity.ImageEntity;
import com.google.android.gms.plus.internal.model.people.PersonEntity.zza;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.Person.AgeRange;
import com.google.android.gms.plus.model.people.Person.Cover;
import com.google.android.gms.plus.model.people.Person.Image;
import com.google.android.gms.plus.model.people.Person.Name;
import com.google.android.gms.plus.model.people.Person.Organizations;
import com.google.android.gms.plus.model.people.Person.PlacesLived;
import com.google.android.gms.plus.model.people.Person.Urls;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class zzk extends zzc implements Person {
    public zzk(DataHolder $r1, int $i0) throws  {
        super($r1, $i0);
    }

    public /* synthetic */ Object freeze() throws  {
        return zzchr();
    }

    public String getAboutMe() throws  {
        return null;
    }

    public AgeRange getAgeRange() throws  {
        return null;
    }

    public String getBirthday() throws  {
        return null;
    }

    public String getBraggingRights() throws  {
        return null;
    }

    public int getCircledByCount() throws  {
        return 0;
    }

    public Cover getCover() throws  {
        return null;
    }

    public String getCurrentLocation() throws  {
        return null;
    }

    public String getDisplayName() throws  {
        return getString("displayName");
    }

    public int getGender() throws  {
        return 0;
    }

    public String getId() throws  {
        return getString("personId");
    }

    public Image getImage() throws  {
        return new ImageEntity(getString("image"));
    }

    public String getLanguage() throws  {
        return null;
    }

    public Name getName() throws  {
        return null;
    }

    public String getNickname() throws  {
        return null;
    }

    public int getObjectType() throws  {
        return zza.zzsd(getString("objectType"));
    }

    public /* synthetic */ List getOrganizations() throws  {
        return zzcho();
    }

    public /* synthetic */ List getPlacesLived() throws  {
        return zzchp();
    }

    public int getPlusOneCount() throws  {
        return 0;
    }

    public int getRelationshipStatus() throws  {
        return 0;
    }

    public String getTagline() throws  {
        return null;
    }

    public String getUrl() throws  {
        return getString("url");
    }

    public /* synthetic */ List getUrls() throws  {
        return zzchq();
    }

    public boolean hasAboutMe() throws  {
        return false;
    }

    public boolean hasAgeRange() throws  {
        return false;
    }

    public boolean hasBirthday() throws  {
        return false;
    }

    public boolean hasBraggingRights() throws  {
        return false;
    }

    public boolean hasCircledByCount() throws  {
        return false;
    }

    public boolean hasCover() throws  {
        return false;
    }

    public boolean hasCurrentLocation() throws  {
        return false;
    }

    public boolean hasDisplayName() throws  {
        return true;
    }

    public boolean hasGender() throws  {
        return false;
    }

    public boolean hasId() throws  {
        return true;
    }

    public boolean hasImage() throws  {
        return true;
    }

    public boolean hasIsPlusUser() throws  {
        return false;
    }

    public boolean hasLanguage() throws  {
        return false;
    }

    public boolean hasName() throws  {
        return false;
    }

    public boolean hasNickname() throws  {
        return false;
    }

    public boolean hasObjectType() throws  {
        return true;
    }

    public boolean hasOrganizations() throws  {
        return false;
    }

    public boolean hasPlacesLived() throws  {
        return false;
    }

    public boolean hasPlusOneCount() throws  {
        return false;
    }

    public boolean hasRelationshipStatus() throws  {
        return false;
    }

    public boolean hasTagline() throws  {
        return false;
    }

    public boolean hasUrl() throws  {
        return true;
    }

    public boolean hasUrls() throws  {
        return false;
    }

    public boolean hasVerified() throws  {
        return false;
    }

    public boolean isPlusUser() throws  {
        return false;
    }

    public boolean isVerified() throws  {
        return false;
    }

    public ArrayList<Organizations> zzcho() throws  {
        return null;
    }

    public ArrayList<PlacesLived> zzchp() throws  {
        return null;
    }

    public ArrayList<Urls> zzchq() throws  {
        return null;
    }

    public Person zzchr() throws  {
        return new PersonEntity(getDisplayName(), getId(), (ImageEntity) getImage(), getObjectType(), getUrl());
    }
}
