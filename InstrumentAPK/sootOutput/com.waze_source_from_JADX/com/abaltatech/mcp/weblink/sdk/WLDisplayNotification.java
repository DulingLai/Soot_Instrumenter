package com.abaltatech.mcp.weblink.sdk;

import android.graphics.Bitmap;
import android.os.RemoteException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager.INotification;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager.INotificationEventListener;
import com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationData;
import com.abaltatech.weblink.service.interfaces.IWLDisplayNotificationEventListener.Stub;
import java.util.ArrayList;
import java.util.List;

class WLDisplayNotification implements INotification, Comparable<WLDisplayNotification> {
    private String TAG;
    private IWLDisplayNotificationData m_data = null;
    private long m_id = 0;
    private boolean m_isUpdated;
    private List<INotificationEventListener> m_listeners = new ArrayList();
    int m_notificationAppID;
    String m_notificationAppTag;
    private WLNotification m_originalNotification;

    class C03401 extends Stub {
        C03401() throws  {
        }

        public void onNotificationDisplayed(long notificationID) throws RemoteException {
            WLDisplayNotification.this.notifyShown();
        }

        public void onNotificationDismissed(long notificationID) throws RemoteException {
            WLDisplayNotification.this.notifyDismissed();
            WLNotificationManager.getInstance().onNotificationClosed(WLDisplayNotification.this, false);
        }

        public void onNotificationClicked(long notificationID) throws RemoteException {
            WLDisplayNotification.this.notifyClicked();
            WLNotificationManager.getInstance().onNotificationClosed(WLDisplayNotification.this, false);
        }
    }

    public WLDisplayNotification(WLNotification $r1, int $i0, String $r2) throws  {
        this.m_originalNotification = $r1;
        this.TAG = "WLDisplayNotification";
        this.m_notificationAppID = $i0;
        this.m_notificationAppTag = $r2;
        this.m_isUpdated = false;
    }

    private void setup() throws  {
        try {
            this.m_id = this.m_data.getNotificationID();
        } catch (RemoteException $r2) {
            MCSLogger.log("WLDisplayNotification", "Error retrieving notification ID", $r2);
        }
        this.TAG = "WLDisplayNotification " + this.m_id;
        try {
            this.m_data.registerEventListener(new C03401());
        } catch (RemoteException $r1) {
            MCSLogger.log(this.TAG, "Error registering listener", $r1);
        }
    }

    public void dismissNotification() throws  {
        WLNotificationManager.getInstance().onNotificationClosed(this, true);
        notifyDismissed();
    }

    public void setText(String $r1) throws  {
        try {
            this.m_data.setTitle($r1);
        } catch (RemoteException e) {
        }
    }

    public void setNewTimeout(int $i0) throws  {
        try {
            this.m_data.setTimeout($i0);
        } catch (RemoteException e) {
        }
    }

    public void setImage(Bitmap $r1) throws  {
        try {
            this.m_data.setBitmap($r1);
        } catch (RemoteException e) {
        }
    }

    public void setImageUrl(String $r1) throws  {
        try {
            this.m_data.setBitmapURL($r1);
        } catch (RemoteException e) {
        }
    }

    public void setShowProgress(boolean $z0) throws  {
        try {
            this.m_data.setShowProgress($z0);
        } catch (RemoteException e) {
        }
    }

    public void registerListener(INotificationEventListener $r1) throws  {
        synchronized (this.m_listeners) {
            this.m_listeners.add($r1);
        }
    }

    public void unregisterListener(INotificationEventListener $r1) throws  {
        synchronized (this.m_listeners) {
            this.m_listeners.remove($r1);
        }
    }

    void notifyDismissed() throws  {
        for (INotificationEventListener $r1 : getListeners()) {
            if ($r1 != null) {
                $r1.onNotificationDismissed(this);
            }
        }
    }

    private void notifyClicked() throws  {
        for (INotificationEventListener $r1 : getListeners()) {
            if ($r1 != null) {
                $r1.onNotificationClicked(this);
            }
        }
    }

    private void notifyShown() throws  {
        for (INotificationEventListener $r1 : getListeners()) {
            if ($r1 != null) {
                $r1.onNotificationShown(this);
            }
        }
    }

    private INotificationEventListener[] getListeners() throws  {
        INotificationEventListener[] $r1;
        synchronized (this.m_listeners) {
            $r1 = new INotificationEventListener[this.m_listeners.size()];
            this.m_listeners.toArray($r1);
        }
        return $r1;
    }

    synchronized IWLDisplayNotificationData getNotification() throws  {
        return this.m_data;
    }

    public synchronized int compareTo(WLDisplayNotification $r1) throws  {
        int $i0;
        if (this == null && $r1 == null) {
            $i0 = 0;
        } else if (this == null) {
            $i0 = -1;
        } else if ($r1 == null) {
            $i0 = 1;
        } else {
            $i0 = this.m_originalNotification.compareTo($r1.m_originalNotification);
        }
        return $i0;
    }

    synchronized WLNotification getOriginalNotification() throws  {
        return this.m_originalNotification;
    }

    synchronized void setData(IWLDisplayNotificationData $r1) throws  {
        this.m_data = $r1;
        setup();
        if (this.m_isUpdated) {
            this.m_isUpdated = false;
            copyDataToRemoteNotification();
        }
    }

    synchronized void updatedData(WLNotification $r1) throws  {
        this.m_originalNotification = $r1;
        if (this.m_data != null) {
            copyDataToRemoteNotification();
        } else {
            this.m_isUpdated = true;
        }
    }

    private void copyDataToRemoteNotification() throws  {
        setNewTimeout(this.m_originalNotification.timeoutMilliseconds);
        setText(this.m_originalNotification.text);
        setShowProgress(this.m_originalNotification.showProgress);
        setImage(this.m_originalNotification.largeIcon);
        if (this.m_originalNotification.largeIcon == null) {
            setImageUrl(this.m_originalNotification.largeIconURL);
        }
    }
}
