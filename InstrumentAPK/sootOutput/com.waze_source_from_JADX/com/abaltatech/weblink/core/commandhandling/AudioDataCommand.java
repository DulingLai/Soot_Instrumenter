package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;
import java.nio.ByteBuffer;

public class AudioDataCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!AudioDataCommand.class.desiredAssertionStatus());
    public static final short ID = (short) 69;

    public AudioDataCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 69) {
            throw new AssertionError();
        }
    }

    public AudioDataCommand(byte[] $r1, int $i0, int $i1) throws  {
        super((short) 69, $i1);
        if (isValid()) {
            System.arraycopy($r1, $i0, this.m_binaryCommandContainer.getData(), 8, $i1);
        }
    }

    public AudioDataCommand(ByteBuffer $r1) throws  {
        super((short) 69, $r1.limit() - $r1.position());
        if (isValid()) {
            $r1.get(this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + 8, $r1.limit() - $r1.position());
        }
    }

    public DataBuffer getAudioData() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 69 && this.m_binaryCommandContainer.getSize() >= 8)) {
            return new DataBuffer(this.m_binaryCommandContainer.getData(), this.m_binaryCommandContainer.getPos() + 8, this.m_binaryCommandContainer.getSize() - 8);
        }
        throw new AssertionError();
    }

    public int getAudioDataSize() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 69 && this.m_binaryCommandContainer.getSize() >= 8)) {
            return this.m_binaryCommandContainer.getSize() - 8;
        }
        throw new AssertionError();
    }
}
