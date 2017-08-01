package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class FrameDiagnosticCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!FrameDiagnosticCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 16;
    public static final short ID = (short) 74;

    public FrameDiagnosticCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 74) {
            throw new AssertionError();
        }
    }

    public FrameDiagnosticCommand(int $i0, int $i1, int $i2, int $i3) throws  {
        super((short) 74, 16);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i0);
            this.m_binaryCommandContainer.putInt(12, $i1);
            this.m_binaryCommandContainer.putInt(16, $i2);
            this.m_binaryCommandContainer.putInt(20, $i3);
        }
    }

    public int getFrameIndex() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 74 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int getFrameCaptureTime() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 74 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }

    public int getFrameEncodeTime() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 74 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(16);
        }
        throw new AssertionError();
    }

    public int getFrameProcessingTime() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 74 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(20);
        }
        throw new AssertionError();
    }
}
