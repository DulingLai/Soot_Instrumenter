package com.spotify.android.appremote.internal;

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
import android.util.Log;
import com.spotify.android.appremote.api.error.SpotifyConnectionTerminatedException;
import com.spotify.protocol.client.AppProtocolIo;
import com.spotify.protocol.client.AppProtocolIo.DataInput;
import com.spotify.protocol.client.PendingResult;
import com.spotify.protocol.error.SpotifyAppRemoteException;
import java.lang.ref.WeakReference;

public class RemoteServiceIo implements ServiceConnection, AppProtocolIo {
    private static final int HANDSHAKE = 1;
    private static final int MESSAGE = 2;
    private static final String MESSAGE_BODY_KEY = "MESSAGE_BODY";
    private static final String SPOTIFY_SERVICE_ACTION = "com.spotify.mobile.appprotocol.action.START_APP_PROTOCOL_SERVICE";
    private static final String TAG = RemoteServiceIo.class.getSimpleName();
    private final Context mContext;
    private DataInput mDataInput;
    private final Messenger mIncomingMessenger = new Messenger(new IncomingHandler(this));
    private OnConnectionTerminatedListener mOnConnectionTerminatedListener;
    private Messenger mOutgoingMessenger;
    private PendingServiceConnectionResult mPendingResult;
    private final String mSpotifyPackageName;
    private State mState = State.DISCONNECTED;

    public interface OnConnectionTerminatedListener {
        void onTerminated() throws ;
    }

    private static class IncomingHandler extends Handler {
        private final WeakReference<RemoteServiceIo> mContext;

        public IncomingHandler(RemoteServiceIo $r1) throws  {
            this.mContext = new WeakReference($r1);
        }

        public void handleMessage(Message $r1) throws  {
            RemoteServiceIo $r4 = (RemoteServiceIo) this.mContext.get();
            if ($r4 != null) {
                $r4.handleMessage($r1);
            }
        }
    }

    private enum State {
        DISCONNECTED,
        CONNECTING,
        CONNECTED,
        TERMINATED
    }

    private void handleMessage(Message $r1) throws  {
        switch ($r1.what) {
            case 1:
                if ($r1.replyTo != null) {
                    this.mOutgoingMessenger = $r1.replyTo;
                    this.mPendingResult.deliverSuccess();
                    return;
                }
                this.mPendingResult.deliverFailure(new SpotifyAppRemoteException("Can't connect to Spotify service"));
                return;
            case 2:
                byte[] $r9 = $r1.getData().getByteArray(MESSAGE_BODY_KEY);
                Log.d(TAG, "Message from Spotify: " + new String($r9));
                this.mDataInput.onData($r9, $r9.length);
                return;
            default:
                Log.e(TAG, "Unknown message: " + $r1.what);
                return;
        }
    }

    public RemoteServiceIo(String $r1, Context $r2) throws  {
        this.mSpotifyPackageName = $r1;
        this.mContext = $r2;
    }

    public void setOnConnectionTerminatedListener(OnConnectionTerminatedListener $r1) throws  {
        this.mOnConnectionTerminatedListener = $r1;
    }

    public PendingResult<Void> connect() throws  {
        Log.d(TAG, "start remote client");
        Intent $r1 = new Intent(SPOTIFY_SERVICE_ACTION);
        $r1.setPackage(this.mSpotifyPackageName);
        this.mPendingResult = new PendingServiceConnectionResult();
        if (this.mContext.getApplicationContext().bindService($r1, this, 1)) {
            Log.d(TAG, "Connecting to Spotify service");
            this.mState = State.CONNECTING;
        } else {
            Log.e(TAG, "Can't connect to Spotify service");
            this.mPendingResult.deliverFailure(new IllegalStateException("Can't connect to Spotify service with package " + this.mSpotifyPackageName));
        }
        return this.mPendingResult;
    }

    public void disconnect() throws  {
        Log.d(TAG, "stop remote client");
        try {
            this.mContext.getApplicationContext().unbindService(this);
        } catch (IllegalArgumentException e) {
        }
        this.mState = State.DISCONNECTED;
    }

    public boolean isConnected() throws  {
        return this.mState == State.CONNECTED;
    }

    public boolean isConnecting() throws  {
        return this.mState == State.CONNECTING;
    }

    public void writeData(byte[] $r1, int length) throws SpotifyAppRemoteException {
        if (this.mState == State.TERMINATED) {
            throw new SpotifyConnectionTerminatedException();
        } else if (this.mOutgoingMessenger != null) {
            Bundle $r2 = new Bundle();
            $r2.putByteArray(MESSAGE_BODY_KEY, $r1);
            Message $r8 = Message.obtain();
            $r8.setData($r2);
            try {
                this.mOutgoingMessenger.send($r8);
            } catch (RemoteException $r3) {
                Log.e(TAG, "Couldn't send message to Spotify App " + $r3.getMessage());
            }
        } else {
            Log.e(TAG, "No outgoing messenger");
        }
    }

    public void setDataInput(DataInput $r1) throws  {
        this.mDataInput = $r1;
    }

    public void onServiceConnected(ComponentName name, IBinder $r2) throws  {
        Log.d(TAG, "Spotify service connected");
        Messenger $r4 = new Messenger($r2);
        Message $r5 = Message.obtain();
        $r5.replyTo = this.mIncomingMessenger;
        try {
            $r4.send($r5);
        } catch (RemoteException e) {
            Log.e(TAG, "Could not send message to Spotify");
        }
        this.mState = State.CONNECTED;
    }

    public void onServiceDisconnected(ComponentName name) throws  {
        Log.e(TAG, "Spotify service disconnected");
        this.mOutgoingMessenger = null;
        this.mState = State.TERMINATED;
        if (this.mOnConnectionTerminatedListener != null) {
            this.mOnConnectionTerminatedListener.onTerminated();
        }
    }
}
