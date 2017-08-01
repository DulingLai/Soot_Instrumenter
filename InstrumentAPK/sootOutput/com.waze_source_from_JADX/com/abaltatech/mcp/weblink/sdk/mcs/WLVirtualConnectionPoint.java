package com.abaltatech.mcp.weblink.sdk.mcs;

import android.os.RemoteException;
import com.abaltatech.mcp.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcp.mcs.common.MCSException;
import com.abaltatech.mcp.mcs.logger.MCSLogger;
import com.abaltatech.mcp.weblink.core.DataBuffer;
import com.abaltatech.mcp.weblink.sdk.internal.WLUtils;
import com.abaltatech.weblink.service.interfaces.IWLVirtualConnectionHandler;
import java.util.Arrays;

class WLVirtualConnectionPoint extends MCSDataLayerBase {
    static final /* synthetic */ boolean $assertionsDisabled;
    private static final int MAX_CHUNK_SIZE = 8192;
    private static final String TAG = "WLConnectionPoint";
    private static int ms_connID = 0;
    private final int m_connID;
    private final byte[] m_fromAddress;
    private final IWLVirtualConnectionHandler m_handler;
    private final DataBuffer m_inDataBuffer = new DataBuffer();
    private boolean m_isClosed;
    private final byte[] m_toAddress;

    static {
        boolean $z0;
        if (WLVirtualConnectionPoint.class.desiredAssertionStatus()) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        $assertionsDisabled = $z0;
    }

    public WLVirtualConnectionPoint(byte[] $r1, byte[] $r2, IWLVirtualConnectionHandler $r3) throws MCSException {
        int $i0 = ms_connID + 1;
        ms_connID = $i0;
        this.m_connID = $i0;
        if ($assertionsDisabled || !($r1 == null || $r2 == null || $r3 == null)) {
            this.m_fromAddress = $r1;
            this.m_toAddress = $r2;
            this.m_handler = $r3;
            return;
        }
        throw new AssertionError();
    }

    public boolean checkForAddressMatch(byte[] $r1, byte[] $r2) throws  {
        return Arrays.equals(this.m_fromAddress, $r1) && Arrays.equals(this.m_toAddress, $r2);
    }

    public byte[] getFromAddress() throws  {
        return this.m_fromAddress;
    }

    public byte[] getToAddress() throws  {
        return this.m_toAddress;
    }

    public boolean isReady() throws  {
        return !this.m_isClosed;
    }

    public int readData(byte[] $r1, int $i0) throws  {
        synchronized (this.m_inDataBuffer) {
            $i0 = this.m_inDataBuffer.getBytes($r1, 0, $i0);
            if (this.m_inDataBuffer.getPos() >= 32768) {
                this.m_inDataBuffer.normalizeBuffer();
            }
        }
        return $i0;
    }

    public void closeConnection() throws  {
        try {
            this.m_handler.closeVirtualConnection(this.m_fromAddress, this.m_toAddress);
        } catch (RemoteException e) {
        }
        closeRequested();
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        int $i1 = 0;
        while ($i0 > 0) {
            byte[] $r4 = $r1;
            int $i2 = $i0;
            if ($i0 > 8192 || $i1 > 0 || $r1.length != $i0) {
                int $i3 = Math.min(8192, $i0);
                $i2 = $i3;
                $r4 = Arrays.copyOfRange($r1, $i1, $i1 + $i3);
            }
            try {
                this.m_handler.sendVirtualConnectionData(this.m_fromAddress, this.m_toAddress, $r4);
                $i1 += $i2;
                $i0 -= $i2;
            } catch (RemoteException e) {
                return;
            }
        }
    }

    void readDataInternal(byte[] $r1) throws  {
        synchronized (this.m_inDataBuffer) {
            this.m_inDataBuffer.addBytes($r1, 0, $r1.length);
        }
        notifyForData();
    }

    void closeRequested() throws  {
        this.m_isClosed = true;
        notifyForConnectionClosed();
        MCSLogger.log(TAG, "Connection " + this.m_connID + ": Connection closed(from='" + WLUtils.extractAddress(this.m_fromAddress) + "' to='" + WLUtils.extractAddress(this.m_toAddress) + "')");
    }
}
