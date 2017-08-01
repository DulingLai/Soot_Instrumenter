package com.abaltatech.mcp.mcs.tcpip;

import com.abaltatech.mcp.mcs.common.IMCSConnectionAddress;
import com.waze.strings.DisplayStrings;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class TCPIPAddress implements IMCSConnectionAddress {
    static final /* synthetic */ boolean $assertionsDisabled = (!TCPIPAddress.class.desiredAssertionStatus());
    public static final InetAddress m_anyAddr;
    protected InetAddress m_address = null;
    protected int m_port = 0;

    static {
        InetAddress $r3;
        try {
            $r3 = InetAddress.getByAddress(new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0});
        } catch (Exception e) {
            $r3 = null;
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
        }
        m_anyAddr = $r3;
    }

    public TCPIPAddress() throws  {
        copyFrom(m_anyAddr, 0);
    }

    public TCPIPAddress(byte[] $r1, int $i0) throws  {
        copyFrom($r1, $i0);
    }

    public TCPIPAddress(int $i0) throws  {
        copyFrom(m_anyAddr, $i0);
    }

    public TCPIPAddress(InetAddress $r1, int $i0) throws  {
        copyFrom($r1, $i0);
    }

    public TCPIPAddress(InetSocketAddress $r1) throws  {
        InetAddress $r2;
        int $i0;
        if ($r1 == null) {
            $r2 = m_anyAddr;
        } else {
            $r2 = $r1.getAddress();
        }
        this.m_address = $r2;
        if ($r1 == null) {
            $i0 = 0;
        } else {
            $i0 = $r1.getPort();
        }
        this.m_port = $i0;
    }

    public InetAddress getAddress() throws  {
        return this.m_address;
    }

    public int getPort() throws  {
        return this.m_port;
    }

    public void copyFrom(TCPIPAddress $r1) throws  {
        this.m_address = $r1.getAddress();
        this.m_port = $r1.getPort();
    }

    public void copyFrom(InetAddress $r1, int $i0) throws  {
        if ($r1 == null) {
            $r1 = m_anyAddr;
        }
        this.m_address = $r1;
        this.m_port = $i0;
    }

    public void copyFrom(byte[] $r1, int $i0) throws  {
        try {
            this.m_address = InetAddress.getByAddress($r1);
        } catch (Exception e) {
            this.m_address = null;
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
        }
        this.m_port = $i0;
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0;
        if ($r1 == this) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if ($z0 || $r1 == null || !($r1 instanceof TCPIPAddress)) {
            return $z0;
        }
        TCPIPAddress $r2 = (TCPIPAddress) $r1;
        InetAddress $r3 = $r2.getAddress();
        int $i0 = $r2.getPort();
        if (((this.m_address != null || $r3 != null) && (this.m_address == null || !this.m_address.equals($r3))) || this.m_port != $i0) {
            return false;
        }
        return true;
    }

    public int hashCode() throws  {
        return (((this.m_address == null ? 0 : this.m_address.hashCode()) + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.m_port;
    }

    public boolean isSameAs(IMCSConnectionAddress $r1) throws  {
        return equals($r1);
    }

    public String toString() throws  {
        return (this.m_address == null ? "0.0.0.0" : this.m_address.toString()) + ":" + this.m_port;
    }

    public boolean isSubsetOf(IMCSConnectionAddress $r1) throws  {
        if ($r1 == null) {
            return false;
        }
        if (!($r1 instanceof TCPIPAddress)) {
            return false;
        }
        TCPIPAddress $r2 = (TCPIPAddress) $r1;
        boolean $z0 = false;
        if ($r2.getAddress() == null) {
            $z0 = true;
        } else if (this.m_address != null) {
            $z0 = $r2.getAddress().isAnyLocalAddress() || this.m_address.equals($r2.getAddress());
        }
        if (!$z0) {
            return false;
        }
        if ($r2.getPort() == 0) {
            return true;
        }
        if (this.m_port == 0) {
            return false;
        }
        if ($r2.m_port != 0) {
            return this.m_port == $r2.m_port;
        } else {
            return false;
        }
    }
}
