package com.facebook;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.internal.ImageRequest;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.internal.Validate;
import com.waze.strings.DisplayStrings;
import org.json.JSONException;
import org.json.JSONObject;

public final class Profile implements Parcelable {
    public static final Creator<Profile> CREATOR = new C04932();
    private static final String FIRST_NAME_KEY = "first_name";
    private static final String ID_KEY = "id";
    private static final String LAST_NAME_KEY = "last_name";
    private static final String LINK_URI_KEY = "link_uri";
    private static final String MIDDLE_NAME_KEY = "middle_name";
    private static final String NAME_KEY = "name";
    private final String firstName;
    private final String id;
    private final String lastName;
    private final Uri linkUri;
    private final String middleName;
    private final String name;

    static class C04921 implements GraphMeRequestWithCacheCallback {
        C04921() throws  {
        }

        public void onSuccess(JSONObject $r1) throws  {
            String $r3 = $r1.optString("id");
            if ($r3 != null) {
                String $r4 = $r1.optString("link");
                Profile.setCurrentProfile(new Profile($r3, $r1.optString(Profile.FIRST_NAME_KEY), $r1.optString(Profile.MIDDLE_NAME_KEY), $r1.optString(Profile.LAST_NAME_KEY), $r1.optString("name"), $r4 != null ? Uri.parse($r4) : null));
            }
        }

        public void onFailure(FacebookException error) throws  {
        }
    }

    static class C04932 implements Creator {
        C04932() throws  {
        }

        public Profile createFromParcel(Parcel $r1) throws  {
            return new Profile($r1);
        }

        public Profile[] newArray(int $i0) throws  {
            return new Profile[$i0];
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public static Profile getCurrentProfile() throws  {
        return ProfileManager.getInstance().getCurrentProfile();
    }

    public static void setCurrentProfile(Profile $r0) throws  {
        ProfileManager.getInstance().setCurrentProfile($r0);
    }

    public static void fetchProfileForCurrentAccessToken() throws  {
        AccessToken $r1 = AccessToken.getCurrentAccessToken();
        if ($r1 == null) {
            setCurrentProfile(null);
        } else {
            Utility.getGraphMeRequestWithCacheAsync($r1.getToken(), new C04921());
        }
    }

    public Profile(String $r1, @Nullable String $r2, @Nullable String $r3, @Nullable String $r4, @Nullable String $r5, @Nullable Uri $r6) throws  {
        Validate.notNullOrEmpty($r1, "id");
        this.id = $r1;
        this.firstName = $r2;
        this.middleName = $r3;
        this.lastName = $r4;
        this.name = $r5;
        this.linkUri = $r6;
    }

    public Uri getProfilePictureUri(int $i0, int $i1) throws  {
        return ImageRequest.getProfilePictureUri(this.id, $i0, $i1);
    }

    public String getId() throws  {
        return this.id;
    }

    public String getFirstName() throws  {
        return this.firstName;
    }

    public String getMiddleName() throws  {
        return this.middleName;
    }

    public String getLastName() throws  {
        return this.lastName;
    }

    public String getName() throws  {
        return this.name;
    }

    public Uri getLinkUri() throws  {
        return this.linkUri;
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if (!($r1 instanceof Profile)) {
            return false;
        }
        Profile $r2 = (Profile) $r1;
        if (this.id.equals($r2.id) && this.firstName == null) {
            if ($r2.firstName != null) {
                return false;
            }
            return true;
        } else if (this.firstName.equals($r2.firstName) && this.middleName == null) {
            if ($r2.middleName != null) {
                return false;
            }
            return true;
        } else if (this.middleName.equals($r2.middleName) && this.lastName == null) {
            if ($r2.lastName != null) {
                return false;
            }
            return true;
        } else if (this.lastName.equals($r2.lastName) && this.name == null) {
            if ($r2.name != null) {
                return false;
            }
            return true;
        } else if (!this.name.equals($r2.name) || this.linkUri != null) {
            return this.linkUri.equals($r2.linkUri);
        } else {
            if ($r2.linkUri != null) {
                return false;
            }
            return true;
        }
    }

    public int hashCode() throws  {
        int $i0 = this.id.hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC;
        if (this.firstName != null) {
            $i0 = ($i0 * 31) + this.firstName.hashCode();
        }
        if (this.middleName != null) {
            $i0 = ($i0 * 31) + this.middleName.hashCode();
        }
        if (this.lastName != null) {
            $i0 = ($i0 * 31) + this.lastName.hashCode();
        }
        if (this.name != null) {
            $i0 = ($i0 * 31) + this.name.hashCode();
        }
        if (this.linkUri != null) {
            return ($i0 * 31) + this.linkUri.hashCode();
        }
        return $i0;
    }

    JSONObject toJSONObject() throws  {
        JSONObject $r3 = new JSONObject();
        try {
            $r3.put("id", this.id);
            $r3.put(FIRST_NAME_KEY, this.firstName);
            $r3.put(MIDDLE_NAME_KEY, this.middleName);
            $r3.put(LAST_NAME_KEY, this.lastName);
            $r3.put("name", this.name);
            if (this.linkUri == null) {
                return $r3;
            }
            $r3.put(LINK_URI_KEY, this.linkUri.toString());
            return $r3;
        } catch (JSONException e) {
            return null;
        }
    }

    Profile(JSONObject $r1) throws  {
        Uri $r2 = null;
        this.id = $r1.optString("id", null);
        this.firstName = $r1.optString(FIRST_NAME_KEY, null);
        this.middleName = $r1.optString(MIDDLE_NAME_KEY, null);
        this.lastName = $r1.optString(LAST_NAME_KEY, null);
        this.name = $r1.optString("name", null);
        String $r3 = $r1.optString(LINK_URI_KEY, null);
        if ($r3 != null) {
            $r2 = Uri.parse($r3);
        }
        this.linkUri = $r2;
    }

    private Profile(Parcel $r1) throws  {
        Uri $r3;
        this.id = $r1.readString();
        this.firstName = $r1.readString();
        this.middleName = $r1.readString();
        this.lastName = $r1.readString();
        this.name = $r1.readString();
        String $r2 = $r1.readString();
        if ($r2 == null) {
            $r3 = null;
        } else {
            $r3 = Uri.parse($r2);
        }
        this.linkUri = $r3;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeString(this.id);
        $r1.writeString(this.firstName);
        $r1.writeString(this.middleName);
        $r1.writeString(this.lastName);
        $r1.writeString(this.name);
        $r1.writeString(this.linkUri == null ? null : this.linkUri.toString());
    }
}
