package com.google.android.gms.common.images;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

/* compiled from: dalvik_source_com.waze.apk */
public final class WebImage extends AbstractSafeParcelable {
    public static final Creator<WebImage> CREATOR = new zzb();
    private final Uri HS;
    private final int mVersionCode;
    private final int zzaiq;
    private final int zzair;

    WebImage(int $i0, Uri $r1, int $i1, int $i2) throws  {
        this.mVersionCode = $i0;
        this.HS = $r1;
        this.zzaiq = $i1;
        this.zzair = $i2;
    }

    public WebImage(Uri $r1) throws IllegalArgumentException {
        this($r1, 0, 0);
    }

    public WebImage(Uri $r1, int $i0, int $i1) throws IllegalArgumentException {
        this(1, $r1, $i0, $i1);
        if ($r1 == null) {
            throw new IllegalArgumentException("url cannot be null");
        } else if ($i0 < 0 || $i1 < 0) {
            throw new IllegalArgumentException("width and height must not be negative");
        }
    }

    public WebImage(JSONObject $r1) throws IllegalArgumentException {
        this(zzn($r1), $r1.optInt("width", 0), $r1.optInt("height", 0));
    }

    private static Uri zzn(JSONObject $r0) throws  {
        if (!$r0.has("url")) {
            return null;
        }
        try {
            return Uri.parse($r0.getString("url"));
        } catch (JSONException e) {
            return null;
        }
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || !($r1 instanceof WebImage)) {
            return false;
        }
        WebImage $r2 = (WebImage) $r1;
        return zzaa.equal(this.HS, $r2.HS) && this.zzaiq == $r2.zzaiq && this.zzair == $r2.zzair;
    }

    public int getHeight() throws  {
        return this.zzair;
    }

    public Uri getUrl() throws  {
        return this.HS;
    }

    int getVersionCode() throws  {
        return this.mVersionCode;
    }

    public int getWidth() throws  {
        return this.zzaiq;
    }

    public int hashCode() throws  {
        return zzaa.hashCode(this.HS, Integer.valueOf(this.zzaiq), Integer.valueOf(this.zzair));
    }

    public JSONObject toJson() throws  {
        JSONObject $r1 = new JSONObject();
        try {
            $r1.put("url", this.HS.toString());
            $r1.put("width", this.zzaiq);
            $r1.put("height", this.zzair);
            return $r1;
        } catch (JSONException e) {
            return $r1;
        }
    }

    public String toString() throws  {
        return String.format(Locale.US, "Image %dx%d %s", new Object[]{Integer.valueOf(this.zzaiq), Integer.valueOf(this.zzair), this.HS.toString()});
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        zzb.zza(this, $r1, $i0);
    }
}
