package com.abaltatech.mcp.mcs.common;

public interface IDatagramReceiver {
    void OnDatagramReceived(int i, IMCSConnectionAddress iMCSConnectionAddress, byte[] bArr, int i2) throws ;
}
