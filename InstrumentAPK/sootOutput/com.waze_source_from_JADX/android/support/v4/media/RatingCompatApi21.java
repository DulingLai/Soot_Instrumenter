package android.support.v4.media;

import android.media.Rating;

class RatingCompatApi21 {
    RatingCompatApi21() throws  {
    }

    public static Object newUnratedRating(int $i0) throws  {
        return Rating.newUnratedRating($i0);
    }

    public static Object newHeartRating(boolean $z0) throws  {
        return Rating.newHeartRating($z0);
    }

    public static Object newThumbRating(boolean $z0) throws  {
        return Rating.newThumbRating($z0);
    }

    public static Object newStarRating(int $i0, float $f0) throws  {
        return Rating.newStarRating($i0, $f0);
    }

    public static Object newPercentageRating(float $f0) throws  {
        return Rating.newPercentageRating($f0);
    }

    public static boolean isRated(Object $r0) throws  {
        return ((Rating) $r0).isRated();
    }

    public static int getRatingStyle(Object $r0) throws  {
        return ((Rating) $r0).getRatingStyle();
    }

    public static boolean hasHeart(Object $r0) throws  {
        return ((Rating) $r0).hasHeart();
    }

    public static boolean isThumbUp(Object $r0) throws  {
        return ((Rating) $r0).isThumbUp();
    }

    public static float getStarRating(Object $r0) throws  {
        return ((Rating) $r0).getStarRating();
    }

    public static float getPercentRating(Object $r0) throws  {
        return ((Rating) $r0).getPercentRating();
    }
}
