package com.google.android.gms.iid;

import android.annotation.TargetApi;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;

/* compiled from: dalvik_source_com.waze.apk */
public class MessengerCompat implements Parcelable {
    public static final Creator<MessengerCompat> CREATOR = new C07461();
    Messenger atP;
    zzb atQ;

    /* compiled from: dalvik_source_com.waze.apk */
    class C07461 implements Creator<MessengerCompat> {
        C07461() throws  {
        }

        public /* synthetic */ Object createFromParcel(Parcel $r1) throws  {
            return zzov($r1);
        }

        public /* synthetic */ Object[] newArray(int $i0) throws  {
            return zzvy($i0);
        }

        public MessengerCompat zzov(Parcel $r1) throws  {
            IBinder $r2 = $r1.readStrongBinder();
            return $r2 != null ? new MessengerCompat($r2) : null;
        }

        public MessengerCompat[] zzvy(int $i0) throws  {
            return new MessengerCompat[$i0];
        }
    }

    /* compiled from: dalvik_source_com.waze.apk */
    private final class zza extends com.google.android.gms.iid.zzb.zza {
        final /* synthetic */ MessengerCompat atR;
        Handler handler;

        zza(MessengerCompat $r1, Handler $r2) throws  {
            this.atR = $r1;
            this.handler = $r2;
        }

        public void send(Message $r1) throws RemoteException {
            $r1.arg2 = Binder.getCallingUid();
            this.handler.dispatchMessage($r1);
        }
    }

    public MessengerCompat(Handler $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            this.atP = new Messenger($r1);
        } else {
            this.atQ = new zza(this, $r1);
        }
    }

    public MessengerCompat(IBinder $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            this.atP = new Messenger($r1);
        } else {
            this.atQ = com.google.android.gms.iid.zzb.zza.zzkq($r1);
        }
    }

    public static int zzc(Message $r0) throws  {
        return VERSION.SDK_INT >= 21 ? zzd($r0) : $r0.arg2;
    }

    @TargetApi(21)
    private static int zzd(Message $r0) throws  {
        return $r0.sendingUid;
    }

    public int describeContents() throws  {
        return 0;
    }

    public boolean equals(Object $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        try {
            return getBinder().equals(((MessengerCompat) $r1).getBinder());
        } catch (ClassCastException e) {
            return false;
        }
    }

    public IBinder getBinder() throws  {
        return this.atP != null ? this.atP.getBinder() : this.atQ.asBinder();
    }

    public int hashCode() throws  {
        return getBinder().hashCode();
    }

    public void send(Message $r1) throws RemoteException {
        if (this.atP != null) {
            this.atP.send($r1);
        } else {
            this.atQ.send($r1);
        }
    }

    public void writeToParcel(Parcel $r1, int i) throws  {
        if (this.atP != null) {
            $r1.writeStrongBinder(this.atP.getBinder());
        } else {
            $r1.writeStrongBinder(this.atQ.asBinder());
        }
    }
}
