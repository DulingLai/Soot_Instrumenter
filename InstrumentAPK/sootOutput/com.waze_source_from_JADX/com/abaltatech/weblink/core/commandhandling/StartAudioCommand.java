package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class StartAudioCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!StartAudioCommand.class.desiredAssertionStatus());
    public static final int BO_BIG_ENDIAN = 0;
    public static final int BO_LITTE_ENDIAN = 1;
    public static final int BO_UNDEFINED = -1;
    private static final int CMD_FIXED_SIZE = 20;
    public static final int CODEC_ID_AAC = 86018;
    public static final int CODEC_ID_AC3 = 86018;
    public static final int CODEC_ID_MP2 = 86016;
    public static final int CODEC_ID_MP3 = 86017;
    public static final int CODEC_ID_PCM = 0;
    public static final short ID = (short) 67;

    public StartAudioCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 67) {
            throw new AssertionError();
        }
    }

    public StartAudioCommand(int $i0) throws  {
        super((short) 67, 20);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i0);
            this.m_binaryCommandContainer.putInt(12, -1);
            this.m_binaryCommandContainer.putInt(16, -1);
            this.m_binaryCommandContainer.putInt(20, -1);
            this.m_binaryCommandContainer.putInt(24, -1);
        }
    }

    public StartAudioCommand(int $i0, int $i1, int $i2, int $i3, int $i4) throws  {
        super((short) 67, 20);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i0);
            this.m_binaryCommandContainer.putInt(12, $i1);
            this.m_binaryCommandContainer.putInt(16, $i2);
            this.m_binaryCommandContainer.putInt(20, $i3);
            this.m_binaryCommandContainer.putInt(24, $i4);
        }
    }

    public int getAudioCodec() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 67 && this.m_binaryCommandContainer.getSize() == 28)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int getSampleRate() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 67 && this.m_binaryCommandContainer.getSize() == 28)) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }

    public int getSampleSize() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 67 && this.m_binaryCommandContainer.getSize() == 28)) {
            return this.m_binaryCommandContainer.getInt(16);
        }
        throw new AssertionError();
    }

    public int getChannelCount() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 67 && this.m_binaryCommandContainer.getSize() == 28)) {
            return this.m_binaryCommandContainer.getInt(20);
        }
        throw new AssertionError();
    }

    public int getByteOrder() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 67 && this.m_binaryCommandContainer.getSize() == 28)) {
            return this.m_binaryCommandContainer.getInt(24);
        }
        throw new AssertionError();
    }
}
