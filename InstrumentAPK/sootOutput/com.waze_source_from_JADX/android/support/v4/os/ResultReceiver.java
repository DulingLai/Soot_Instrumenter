package android.support.v4.os;

import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.RemoteException;
import android.support.v4.os.IResultReceiver.Stub;

public class ResultReceiver implements Parcelable {
    public static final Creator<ResultReceiver> CREATOR = new C00971();
    final Handler mHandler;
    final boolean mLocal;
    IResultReceiver mReceiver;

    static class C00971 implements Creator<ResultReceiver> {
        C00971() throws  {
        }

        public ResultReceiver createFromParcel(Parcel $r1) throws  {
            return new ResultReceiver($r1);
        }

        public ResultReceiver[] newArray(int $i0) throws  {
            return new ResultReceiver[$i0];
        }
    }

    class MyResultReceiver extends Stub {
        MyResultReceiver() throws  {
        }

        public void send(int $i0, Bundle $r1) throws  {
            if (ResultReceiver.this.mHandler != null) {
                ResultReceiver.this.mHandler.post(new MyRunnable($i0, $r1));
            } else {
                ResultReceiver.this.onReceiveResult($i0, $r1);
            }
        }
    }

    class MyRunnable implements Runnable {
        final int mResultCode;
        final Bundle mResultData;

        MyRunnable(int $i0, Bundle $r2) throws  {
            this.mResultCode = $i0;
            this.mResultData = $r2;
        }

        public void run() throws  {
            ResultReceiver.this.onReceiveResult(this.mResultCode, this.mResultData);
        }
    }

    public int describeContents() throws  {
        return 0;
    }

    public ResultReceiver(Handler $r1) throws  {
        this.mLocal = true;
        this.mHandler = $r1;
    }

    public void send(int $i0, Bundle $r1) throws  {
        if (this.mLocal) {
            if (this.mHandler != null) {
                this.mHandler.post(new MyRunnable($i0, $r1));
            } else {
                onReceiveResult($i0, $r1);
            }
        } else if (this.mReceiver != null) {
            try {
                this.mReceiver.send($i0, $r1);
            } catch (RemoteException e) {
            }
        }
    }

    protected void onReceiveResult(int resultCode, Bundle resultData) throws  {
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        synchronized (this) {
            if (this.mReceiver == null) {
                this.mReceiver = new MyResultReceiver();
            }
            $r1.writeStrongBinder(this.mReceiver.asBinder());
        }
    }

    ResultReceiver(Parcel $r1) throws  {
        this.mLocal = false;
        this.mHandler = null;
        this.mReceiver = Stub.asInterface($r1.readStrongBinder());
    }
}
