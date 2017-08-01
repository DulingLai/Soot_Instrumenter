package android.support.v4.media;

import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat implements Parcelable {
    public static final Creator<RatingCompat> CREATOR = new C00741();
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    static class C00741 implements Creator<RatingCompat> {
        C00741() throws  {
        }

        public RatingCompat createFromParcel(Parcel $r1) throws  {
            return new RatingCompat($r1.readInt(), $r1.readFloat());
        }

        public RatingCompat[] newArray(int $i0) throws  {
            return new RatingCompat[$i0];
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface StarStyle {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Style {
    }

    private RatingCompat(int $i0, float $f0) throws  {
        this.mRatingStyle = $i0;
        this.mRatingValue = $f0;
    }

    public String toString() throws  {
        return "Rating:style=" + this.mRatingStyle + " rating=" + (this.mRatingValue < 0.0f ? "unrated" : String.valueOf(this.mRatingValue));
    }

    public int describeContents() throws  {
        return this.mRatingStyle;
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeInt(this.mRatingStyle);
        $r1.writeFloat(this.mRatingValue);
    }

    public static RatingCompat newUnratedRating(int $i0) throws  {
        switch ($i0) {
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                return new RatingCompat($i0, RATING_NOT_RATED);
            default:
                return null;
        }
    }

    public static RatingCompat newHeartRating(boolean $z0) throws  {
        return new RatingCompat(1, $z0 ? 1.0f : 0.0f);
    }

    public static RatingCompat newThumbRating(boolean $z0) throws  {
        return new RatingCompat(2, $z0 ? 1.0f : 0.0f);
    }

    public static RatingCompat newStarRating(int $i0, float $f0) throws  {
        float $f1;
        switch ($i0) {
            case 3:
                $f1 = 3.0f;
                break;
            case 4:
                $f1 = 4.0f;
                break;
            case 5:
                $f1 = 5.0f;
                break;
            default:
                Log.e(TAG, "Invalid rating style (" + $i0 + ") for a star rating");
                return null;
        }
        if ($f0 >= 0.0f && $f0 <= $f1) {
            return new RatingCompat($i0, $f0);
        }
        Log.e(TAG, "Trying to set out of range star-based rating");
        return null;
    }

    public static RatingCompat newPercentageRating(float $f0) throws  {
        if ($f0 >= 0.0f && $f0 <= 100.0f) {
            return new RatingCompat(6, $f0);
        }
        Log.e(TAG, "Invalid percentage-based rating value");
        return null;
    }

    public boolean isRated() throws  {
        return this.mRatingValue >= 0.0f;
    }

    public int getRatingStyle() throws  {
        return this.mRatingStyle;
    }

    public boolean hasHeart() throws  {
        boolean $z0 = true;
        if (this.mRatingStyle != 1) {
            return false;
        }
        if (this.mRatingValue != 1.0f) {
            $z0 = false;
        }
        return $z0;
    }

    public boolean isThumbUp() throws  {
        if (this.mRatingStyle != 2) {
            return false;
        }
        return this.mRatingValue == 1.0f;
    }

    public float getStarRating() throws  {
        switch (this.mRatingStyle) {
            case 3:
            case 4:
            case 5:
                if (isRated()) {
                    return this.mRatingValue;
                }
                break;
            default:
                break;
        }
        return RATING_NOT_RATED;
    }

    public float getPercentRating() throws  {
        return (this.mRatingStyle == 6 && isRated()) ? this.mRatingValue : RATING_NOT_RATED;
    }

    public static RatingCompat fromRating(Object $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        if (VERSION.SDK_INT < 21) {
            return null;
        }
        RatingCompat $r1;
        int $i0 = RatingCompatApi21.getRatingStyle($r0);
        if (RatingCompatApi21.isRated($r0)) {
            switch ($i0) {
                case 1:
                    $r1 = newHeartRating(RatingCompatApi21.hasHeart($r0));
                    break;
                case 2:
                    $r1 = newThumbRating(RatingCompatApi21.isThumbUp($r0));
                    break;
                case 3:
                case 4:
                case 5:
                    $r1 = newStarRating($i0, RatingCompatApi21.getStarRating($r0));
                    break;
                case 6:
                    $r1 = newPercentageRating(RatingCompatApi21.getPercentRating($r0));
                    break;
                default:
                    return null;
            }
        }
        $r1 = newUnratedRating($i0);
        $r1.mRatingObj = $r0;
        return $r1;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public java.lang.Object getRating() throws  {
        /*
        r6 = this;
        r0 = r6.mRatingObj;
        if (r0 != 0) goto L_0x000a;
    L_0x0004:
        r1 = android.os.Build.VERSION.SDK_INT;
        r2 = 21;
        if (r1 >= r2) goto L_0x000d;
    L_0x000a:
        r0 = r6.mRatingObj;
        return r0;
    L_0x000d:
        r3 = r6.isRated();
        if (r3 == 0) goto L_0x004b;
    L_0x0013:
        r1 = r6.mRatingStyle;
        switch(r1) {
            case 1: goto L_0x001b;
            case 2: goto L_0x0028;
            case 3: goto L_0x0033;
            case 4: goto L_0x0033;
            case 5: goto L_0x0033;
            case 6: goto L_0x0040;
            default: goto L_0x0018;
        };
    L_0x0018:
        goto L_0x0019;
    L_0x0019:
        r4 = 0;
        return r4;
    L_0x001b:
        r3 = r6.hasHeart();
        r0 = android.support.v4.media.RatingCompatApi21.newHeartRating(r3);
        r6.mRatingObj = r0;
    L_0x0025:
        r0 = r6.mRatingObj;
        return r0;
    L_0x0028:
        r3 = r6.isThumbUp();
        r0 = android.support.v4.media.RatingCompatApi21.newThumbRating(r3);
        r6.mRatingObj = r0;
        goto L_0x0025;
    L_0x0033:
        r1 = r6.mRatingStyle;
        r5 = r6.getStarRating();
        r0 = android.support.v4.media.RatingCompatApi21.newStarRating(r1, r5);
        r6.mRatingObj = r0;
        goto L_0x0025;
    L_0x0040:
        r5 = r6.getPercentRating();
        r0 = android.support.v4.media.RatingCompatApi21.newPercentageRating(r5);
        r6.mRatingObj = r0;
        goto L_0x0019;
    L_0x004b:
        r1 = r6.mRatingStyle;
        r0 = android.support.v4.media.RatingCompatApi21.newUnratedRating(r1);
        r6.mRatingObj = r0;
        goto L_0x0025;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.media.RatingCompat.getRating():java.lang.Object");
    }
}
