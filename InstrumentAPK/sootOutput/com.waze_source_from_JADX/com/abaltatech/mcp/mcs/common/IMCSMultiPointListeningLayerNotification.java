package com.abaltatech.mcp.mcs.common;

import java.nio.channels.SocketChannel;

public interface IMCSMultiPointListeningLayerNotification extends IMCSMultiPointLayerNotification {
    void newListeningConnectionRequested(SocketChannel socketChannel, IMCSConnectionAddress iMCSConnectionAddress) throws ;
}
