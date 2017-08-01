package com.google.android.gms.auth.firstparty.dataservice;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* compiled from: dalvik_source_com.waze.apk */
public class PostSignInData extends AbstractSafeParcelable {
    public static final PostSignInDataCreator CREATOR = new PostSignInDataCreator();
    public final PendingIntent accountInstallationCompletionAction;
    public final Intent postSignInForeignIntent;
    final int version;

    PostSignInData(int $i0, Intent $r1, PendingIntent $r2) throws  {
        this.version = $i0;
        this.postSignInForeignIntent = $r1;
        this.accountInstallationCompletionAction = $r2;
    }

    public PostSignInData(Intent $r1, PendingIntent $r2) throws  {
        this(1, $r1, $r2);
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        PostSignInDataCreator.zza(this, $r1, $i0);
    }
}
