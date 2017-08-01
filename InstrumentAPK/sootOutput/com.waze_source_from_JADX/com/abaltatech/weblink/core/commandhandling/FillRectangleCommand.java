package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;
import java.nio.ByteBuffer;

public class FillRectangleCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!FillRectangleCommand.class.desiredAssertionStatus());
    private static final int CMD_FIXED_SIZE = 16;
    public static final short ID = (short) 1;

    public FillRectangleCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 1) {
            throw new AssertionError();
        }
    }

    public FillRectangleCommand(int $i0, int $i1, int $i2, int $i3, ByteBuffer $r1) throws  {
        super((short) 1, ($r1.limit() + 16) - $r1.position());
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i1);
            this.m_binaryCommandContainer.putInt(12, $i2);
            this.m_binaryCommandContainer.putInt(16, $i3);
            this.m_binaryCommandContainer.putInt(20, $i0);
            $r1.get(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 16, $r1.limit() - $r1.position());
        }
    }

    public DataBuffer getImageData() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 1 && this.m_binaryCommandContainer.getSize() > 24)) {
            return new DataBuffer(this.m_binaryCommandContainer.getData(), (this.m_binaryCommandContainer.getPos() + 8) + 16, (this.m_binaryCommandContainer.getSize() - 8) - 16);
        }
        throw new AssertionError();
    }

    public int getImageSize() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 1 && this.m_binaryCommandContainer.getSize() > 24)) {
            return (this.m_binaryCommandContainer.getSize() - 8) - 16;
        }
        throw new AssertionError();
    }

    public int getAppID() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 1 && this.m_binaryCommandContainer.getSize() > 24)) {
            return this.m_binaryCommandContainer.getInt(20);
        }
        throw new AssertionError();
    }

    public int getWidth() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 1 && this.m_binaryCommandContainer.getSize() > 24)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int getHeight() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 1 && this.m_binaryCommandContainer.getSize() > 24)) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }
}
