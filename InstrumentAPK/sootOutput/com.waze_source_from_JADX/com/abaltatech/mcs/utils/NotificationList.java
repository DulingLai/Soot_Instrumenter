package com.abaltatech.mcs.utils;

import java.util.Arrays;

public class NotificationList {
    static final /* synthetic */ boolean $assertionsDisabled = (!NotificationList.class.desiredAssertionStatus());
    private static final int c_maxNotifications = 10;
    protected int m_lastNotification = -1;
    protected Object[] m_registeredNotifications = new Object[10];

    public Object GetObject(int $i0) throws  {
        return $i0 == -1 ? null : this.m_registeredNotifications[$i0];
    }

    public void Register(Object $r1) throws  {
        boolean $z0 = false;
        synchronized (this) {
            this.m_lastNotification = -1;
            for (int $i0 = 0; $i0 < 10; $i0++) {
                if (this.m_registeredNotifications[$i0] != null) {
                    this.m_lastNotification = $i0;
                } else if (!$z0) {
                    this.m_registeredNotifications[$i0] = $r1;
                    $z0 = true;
                    this.m_lastNotification = $i0;
                }
            }
        }
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        }
    }

    public void Unregister(Object $r1) throws  {
        boolean $z0 = false;
        synchronized (this) {
            this.m_lastNotification = -1;
            for (int $i0 = 0; $i0 < 10; $i0++) {
                if (this.m_registeredNotifications[$i0] == $r1) {
                    this.m_registeredNotifications[$i0] = null;
                    $z0 = true;
                } else if (this.m_registeredNotifications[$i0] != null) {
                    this.m_lastNotification = $i0;
                }
            }
        }
        if (!$assertionsDisabled && !$z0) {
            throw new AssertionError();
        }
    }

    public void ClearAll() throws  {
        synchronized (this) {
            this.m_lastNotification = 0;
            Arrays.fill(this.m_registeredNotifications, null);
        }
    }

    public int Start() throws  {
        return GetNext(-1);
    }

    public int GetNext(int $i0) throws  {
        synchronized (this) {
            $i0++;
            while (true) {
                if (this.m_registeredNotifications[$i0] == null && $i0 <= this.m_lastNotification) {
                    $i0++;
                }
            }
            if ($i0 > this.m_lastNotification) {
                $i0 = -1;
            }
        }
        return $i0;
    }
}
