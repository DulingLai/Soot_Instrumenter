package com.abaltatech.weblinkserver;

import android.bluetooth.BluetoothSocket;
import com.abaltatech.mcs.common.IMCSDataStats;
import com.abaltatech.mcs.common.MCSDataLayerBase;
import com.abaltatech.mcs.logger.MCSLogger;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class BluetoothLayer extends MCSDataLayerBase {
    private int m_endIndex = 0;
    private InputStream m_input = null;
    private OutputStream m_output = null;
    private byte[] m_readBuffer = null;
    private ReadThread m_readThread = null;
    private BluetoothSocket m_socket = null;
    private int m_startIndex = 0;

    private class ReadThread extends Thread {
        private volatile boolean m_stopped;

        private ReadThread() throws  {
            this.m_stopped = false;
        }

        public void interrupt() throws  {
            this.m_stopped = true;
            super.interrupt();
        }

        public void run() throws  {
            while (!this.m_stopped) {
                try {
                    int $i0 = BluetoothLayer.this.m_input.read(BluetoothLayer.this.m_readBuffer, BluetoothLayer.this.m_endIndex, BluetoothLayer.this.m_readBuffer.length - BluetoothLayer.this.m_endIndex);
                    if ($i0 <= 0) {
                        if (BluetoothLayer.this.m_startIndex == BluetoothLayer.this.m_endIndex) {
                            continue;
                        }
                    }
                    synchronized (this) {
                        BluetoothLayer.this.m_endIndex = BluetoothLayer.this.m_endIndex + $i0;
                    }
                    BluetoothLayer.this.notifyForData();
                    synchronized (this) {
                        if (BluetoothLayer.this.m_startIndex == BluetoothLayer.this.m_endIndex) {
                            BluetoothLayer.this.m_startIndex = BluetoothLayer.this.m_endIndex = 0;
                        }
                    }
                } catch (IOException e) {
                    BluetoothLayer.this.closeConnection();
                    return;
                }
            }
        }
    }

    public boolean attachBluetooth(BluetoothSocket $r1) throws  {
        MCSLogger.log("BluetoothLayer", "attachBluetooth");
        this.m_socket = $r1;
        try {
            InputStream $r4 = this.m_socket.getInputStream();
            OutputStream $r5 = this.m_socket.getOutputStream();
            this.m_socket = $r1;
            this.m_input = $r4;
            this.m_output = $r5;
            this.m_readBuffer = new byte[16384];
            this.m_readThread = new ReadThread();
            this.m_readThread.start();
            MCSLogger.log("BluetoothLayer", "IO streams created");
            return true;
        } catch (IOException $r2) {
            MCSLogger.log("BluetoothLayer", "Can't get socket streams", $r2);
            return false;
        }
    }

    public void closeConnection() throws  {
        boolean $z0 = false;
        synchronized (this) {
            if (this.m_socket != null) {
                try {
                    this.m_input.close();
                } catch (Exception e) {
                }
                try {
                    this.m_output.close();
                } catch (Exception e2) {
                }
                try {
                    this.m_socket.close();
                } catch (Exception e3) {
                }
                if (this.m_readThread != null) {
                    this.m_readThread.interrupt();
                    this.m_readThread = null;
                }
                this.m_socket = null;
                this.m_readThread = null;
                $z0 = true;
            }
        }
        if ($z0) {
            notifyForConnectionClosed();
            clearNotifiables();
        }
    }

    public int readData(byte[] $r1, int $i0) throws  {
        IMCSDataStats $r2 = getDataStats();
        synchronized (this) {
            $i0 = Math.min($i0, this.m_endIndex - this.m_startIndex);
            System.arraycopy(this.m_readBuffer, this.m_startIndex, $r1, 0, $i0);
            this.m_startIndex += $i0;
        }
        if ($r2 == null) {
            return $i0;
        }
        $r2.onDataReceived($i0);
        return $i0;
    }

    protected void writeDataInternal(byte[] $r1, int $i0) throws  {
        if (this.m_output != null) {
            IMCSDataStats $r3 = getDataStats();
            try {
                this.m_output.write($r1, 0, $i0);
                if ($r3 != null) {
                    $r3.onDataSent($i0);
                }
            } catch (Exception e) {
                closeConnection();
            }
        }
    }
}
