package com.facebook.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;

public abstract class PlatformServiceClient implements ServiceConnection {
    private final String applicationId;
    private final Context context;
    private final Handler handler;
    private CompletedListener listener;
    private final int protocolVersion;
    private int replyMessage;
    private int requestMessage;
    private boolean running;
    private Messenger sender;

    class C05361 extends Handler {
        C05361() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            PlatformServiceClient.this.handleMessage($r1);
        }
    }

    public interface CompletedListener {
        void completed(Bundle bundle) throws ;
    }

    protected abstract void populateRequestBundle(Bundle bundle) throws ;

    public PlatformServiceClient(Context $r1, int $i0, int $i1, int $i2, String $r2) throws  {
        Context $r4 = $r1.getApplicationContext();
        Context $r5 = $r4;
        if ($r4 == null) {
            $r5 = $r1;
        }
        this.context = $r5;
        this.requestMessage = $i0;
        this.replyMessage = $i1;
        this.applicationId = $r2;
        this.protocolVersion = $i2;
        this.handler = new C05361();
    }

    public void setCompletedListener(CompletedListener $r1) throws  {
        this.listener = $r1;
    }

    protected Context getContext() throws  {
        return this.context;
    }

    public boolean start() throws  {
        if (this.running) {
            return false;
        }
        if (NativeProtocol.getLatestAvailableProtocolVersionForService(this.protocolVersion) == -1) {
            return false;
        }
        Intent $r2 = NativeProtocol.createPlatformServiceIntent(this.context);
        if ($r2 == null) {
            return false;
        }
        this.running = true;
        this.context.bindService($r2, this, 1);
        return true;
    }

    public void cancel() throws  {
        this.running = false;
    }

    public void onServiceConnected(ComponentName name, IBinder $r2) throws  {
        this.sender = new Messenger($r2);
        sendMessage();
    }

    public void onServiceDisconnected(ComponentName name) throws  {
        this.sender = null;
        try {
            this.context.unbindService(this);
        } catch (IllegalArgumentException e) {
        }
        callback(null);
    }

    private void sendMessage() throws  {
        Bundle $r1 = new Bundle();
        $r1.putString(NativeProtocol.EXTRA_APPLICATION_ID, this.applicationId);
        populateRequestBundle($r1);
        Message $r3 = Message.obtain(null, this.requestMessage);
        $r3.arg1 = this.protocolVersion;
        $r3.setData($r1);
        $r3.replyTo = new Messenger(this.handler);
        try {
            this.sender.send($r3);
        } catch (RemoteException e) {
            callback(null);
        }
    }

    protected void handleMessage(Message $r1) throws  {
        if ($r1.what == this.replyMessage) {
            Bundle $r2 = $r1.getData();
            if ($r2.getString(NativeProtocol.STATUS_ERROR_TYPE) != null) {
                callback(null);
            } else {
                callback($r2);
            }
            this.context.unbindService(this);
        }
    }

    private void callback(Bundle $r1) throws  {
        if (this.running) {
            this.running = false;
            CompletedListener $r2 = this.listener;
            if ($r2 != null) {
                $r2.completed($r1);
            }
        }
    }
}
