package com.facebook.messenger;

import android.net.Uri;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ShareToMessengerParams {
    public static final Set<String> VALID_EXTERNAL_URI_SCHEMES;
    public static final Set<String> VALID_MIME_TYPES;
    public static final Set<String> VALID_URI_SCHEMES;
    public final Uri externalUri;
    public final String metaData;
    public final String mimeType;
    public final Uri uri;

    static {
        HashSet $r0 = new HashSet();
        $r0.add("image/*");
        $r0.add("image/jpeg");
        $r0.add("image/png");
        $r0.add("image/gif");
        $r0.add("image/webp");
        $r0.add("video/*");
        $r0.add("video/mp4");
        $r0.add("audio/*");
        $r0.add("audio/mpeg");
        VALID_MIME_TYPES = Collections.unmodifiableSet($r0);
        $r0 = new HashSet();
        $r0.add("content");
        $r0.add("android.resource");
        $r0.add("file");
        VALID_URI_SCHEMES = Collections.unmodifiableSet($r0);
        $r0 = new HashSet();
        $r0.add("http");
        $r0.add("https");
        VALID_EXTERNAL_URI_SCHEMES = Collections.unmodifiableSet($r0);
    }

    ShareToMessengerParams(ShareToMessengerParamsBuilder $r1) throws  {
        this.uri = $r1.getUri();
        this.mimeType = $r1.getMimeType();
        this.metaData = $r1.getMetaData();
        this.externalUri = $r1.getExternalUri();
        if (this.uri == null) {
            throw new NullPointerException("Must provide non-null uri");
        } else if (this.mimeType == null) {
            throw new NullPointerException("Must provide mimeType");
        } else if (!VALID_URI_SCHEMES.contains(this.uri.getScheme())) {
            throw new IllegalArgumentException("Unsupported URI scheme: " + this.uri.getScheme());
        } else if (!VALID_MIME_TYPES.contains(this.mimeType)) {
            throw new IllegalArgumentException("Unsupported mime-type: " + this.mimeType);
        } else if (this.externalUri != null && !VALID_EXTERNAL_URI_SCHEMES.contains(this.externalUri.getScheme())) {
            throw new IllegalArgumentException("Unsupported external uri scheme: " + this.externalUri.getScheme());
        }
    }

    public static ShareToMessengerParamsBuilder newBuilder(Uri $r0, String $r1) throws  {
        return new ShareToMessengerParamsBuilder($r0, $r1);
    }
}
