package com.facebook.internal;

import android.content.Intent;
import java.util.UUID;

public class AppCall {
    private static AppCall currentPendingCall;
    private UUID callId;
    private int requestCode;
    private Intent requestIntent;

    public static AppCall getCurrentPendingCall() throws  {
        return currentPendingCall;
    }

    public static synchronized AppCall finishPendingCall(UUID $r0, int $i0) throws  {
        AppCall $r2;
        synchronized (AppCall.class) {
            try {
                AppCall $r1 = getCurrentPendingCall();
                $r2 = $r1;
                if ($r1 != null && $r1.getCallId().equals($r0) && $r1.getRequestCode() == $i0) {
                    setCurrentPendingCall(null);
                } else {
                    $r2 = null;
                }
            } catch (Throwable th) {
                Class cls = AppCall.class;
            }
        }
        return $r2;
    }

    private static synchronized boolean setCurrentPendingCall(AppCall $r0) throws  {
        boolean $z0;
        synchronized (AppCall.class) {
            try {
                AppCall $r1 = getCurrentPendingCall();
                currentPendingCall = $r0;
                $z0 = $r1 != null;
            } catch (Throwable th) {
                Class cls = AppCall.class;
            }
        }
        return $z0;
    }

    public AppCall(int $i0) throws  {
        this($i0, UUID.randomUUID());
    }

    public AppCall(int $i0, UUID $r1) throws  {
        this.callId = $r1;
        this.requestCode = $i0;
    }

    public Intent getRequestIntent() throws  {
        return this.requestIntent;
    }

    public UUID getCallId() throws  {
        return this.callId;
    }

    public int getRequestCode() throws  {
        return this.requestCode;
    }

    public void setRequestCode(int $i0) throws  {
        this.requestCode = $i0;
    }

    public void setRequestIntent(Intent $r1) throws  {
        this.requestIntent = $r1;
    }

    public boolean setPending() throws  {
        return setCurrentPendingCall(this);
    }
}
