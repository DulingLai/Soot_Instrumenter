package com.facebook.messenger;

import android.net.Uri;

public class ShareToMessengerParamsBuilder {
    private Uri mExternalUri;
    private String mMetaData;
    private final String mMimeType;
    private final Uri mUri;

    ShareToMessengerParamsBuilder(Uri $r1, String $r2) throws  {
        this.mUri = $r1;
        this.mMimeType = $r2;
    }

    public Uri getUri() throws  {
        return this.mUri;
    }

    public String getMimeType() throws  {
        return this.mMimeType;
    }

    public ShareToMessengerParamsBuilder setMetaData(String $r1) throws  {
        this.mMetaData = $r1;
        return this;
    }

    public String getMetaData() throws  {
        return this.mMetaData;
    }

    public ShareToMessengerParamsBuilder setExternalUri(Uri $r1) throws  {
        this.mExternalUri = $r1;
        return this;
    }

    public Uri getExternalUri() throws  {
        return this.mExternalUri;
    }

    public ShareToMessengerParams build() throws  {
        return new ShareToMessengerParams(this);
    }
}
