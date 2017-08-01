package com.abaltatech.mcs.pipe;

import com.abaltatech.mcs.common.IMCSDataLayer;

public class Pipe {
    private PipeConnector m_connector1 = new PipeConnector();
    private PipeConnector m_connector2 = new PipeConnector();
    private ByteDataLayer m_firstLayer = new ByteDataLayer();
    private ByteDataLayer m_secondLayer = new ByteDataLayer();

    private static class PipeConnector implements IDataNotification {
        private ByteDataLayer m_dataLayerOut;

        public void init(ByteDataLayer $r1, ByteDataLayer $r2) throws  {
            this.m_dataLayerOut = $r2;
            $r1.init(this);
        }

        public void onDataReceived(byte[] $r1, int $i0) throws  {
            this.m_dataLayerOut.addData($r1, $i0);
        }
    }

    public Pipe() throws  {
        this.m_connector1.init(this.m_firstLayer, this.m_secondLayer);
        this.m_connector2.init(this.m_secondLayer, this.m_firstLayer);
    }

    public IMCSDataLayer getFirstEnd() throws  {
        return this.m_firstLayer;
    }

    public IMCSDataLayer getSecondEnd() throws  {
        return this.m_secondLayer;
    }
}
