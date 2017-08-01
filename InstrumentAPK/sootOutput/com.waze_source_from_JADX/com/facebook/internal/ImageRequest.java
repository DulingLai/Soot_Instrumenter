package com.facebook.internal;

import android.content.Context;
import android.net.Uri;
import java.util.Locale;

public class ImageRequest {
    private static final String AUTHORITY = "graph.facebook.com";
    private static final String HEIGHT_PARAM = "height";
    private static final String MIGRATION_PARAM = "migration_overrides";
    private static final String MIGRATION_VALUE = "{october_2012:true}";
    private static final String PATH = "%s/picture";
    private static final String SCHEME = "https";
    public static final int UNSPECIFIED_DIMENSION = 0;
    private static final String WIDTH_PARAM = "width";
    private boolean allowCachedRedirects;
    private Callback callback;
    private Object callerTag;
    private Context context;
    private Uri imageUri;

    public static class Builder {
        private boolean allowCachedRedirects;
        private Callback callback;
        private Object callerTag;
        private Context context;
        private Uri imageUrl;

        public Builder(Context $r1, Uri $r2) throws  {
            Validate.notNull($r2, "imageUri");
            this.context = $r1;
            this.imageUrl = $r2;
        }

        public Builder setCallback(Callback $r1) throws  {
            this.callback = $r1;
            return this;
        }

        public Builder setCallerTag(Object $r1) throws  {
            this.callerTag = $r1;
            return this;
        }

        public Builder setAllowCachedRedirects(boolean $z0) throws  {
            this.allowCachedRedirects = $z0;
            return this;
        }

        public ImageRequest build() throws  {
            return new ImageRequest();
        }
    }

    public interface Callback {
        void onCompleted(ImageResponse imageResponse) throws ;
    }

    public static Uri getProfilePictureUri(String $r0, int $i0, int $i1) throws  {
        Validate.notNullOrEmpty($r0, "userId");
        $i0 = Math.max($i0, 0);
        $i1 = Math.max($i1, 0);
        if ($i0 == 0 && $i1 == 0) {
            throw new IllegalArgumentException("Either width or height must be greater than 0");
        }
        android.net.Uri.Builder $r2 = new android.net.Uri.Builder().scheme(SCHEME).authority(AUTHORITY).path(String.format(Locale.US, PATH, new Object[]{$r0}));
        if ($i1 != 0) {
            $r2.appendQueryParameter(HEIGHT_PARAM, String.valueOf($i1));
        }
        if ($i0 != 0) {
            $r2.appendQueryParameter(WIDTH_PARAM, String.valueOf($i0));
        }
        $r2.appendQueryParameter(MIGRATION_PARAM, MIGRATION_VALUE);
        return $r2.build();
    }

    private ImageRequest(Builder $r1) throws  {
        this.context = $r1.context;
        this.imageUri = $r1.imageUrl;
        this.callback = $r1.callback;
        this.allowCachedRedirects = $r1.allowCachedRedirects;
        this.callerTag = $r1.callerTag == null ? new Object() : $r1.callerTag;
    }

    public Context getContext() throws  {
        return this.context;
    }

    public Uri getImageUri() throws  {
        return this.imageUri;
    }

    public Callback getCallback() throws  {
        return this.callback;
    }

    public boolean isCachedRedirectAllowed() throws  {
        return this.allowCachedRedirects;
    }

    public Object getCallerTag() throws  {
        return this.callerTag;
    }
}
