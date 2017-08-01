package com.abaltatech.mcp.weblink.core.commandhandling;

import com.abaltatech.mcp.weblink.core.DataBuffer;

public class MouseCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!MouseCommand.class.desiredAssertionStatus());
    public static final int ACT_MOUSE_BTN_DBL_CLICK = 5;
    public static final int ACT_MOUSE_BTN_DOWN = 2;
    public static final int ACT_MOUSE_BTN_UP = 3;
    public static final int ACT_MOUSE_MOVED = 1;
    public static final int ACT_NONE = 0;
    public static final int BTN_LEFT = 1;
    public static final int BTN_MASK = 15;
    public static final int BTN_MIDDLE = 4;
    public static final int BTN_NONE = 0;
    public static final int BTN_RIGHT = 2;
    private static final int CMD_FIXED_SIZE = 16;
    public static final short ID = (short) 16;

    public MouseCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 16) {
            throw new AssertionError();
        }
    }

    public MouseCommand(int $i0, int $i1, int $i2, int $i3) throws  {
        super((short) 16, 16);
        if (isValid()) {
            this.m_binaryCommandContainer.putInt(8, $i0);
            this.m_binaryCommandContainer.putInt(12, $i1);
            this.m_binaryCommandContainer.putInt(16, $i2);
            this.m_binaryCommandContainer.putInt(20, $i3);
        }
    }

    public int getX() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 16 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int getY() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 16 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }

    public int getActionType() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 16 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(16);
        }
        throw new AssertionError();
    }

    public int getButton() throws  {
        if ($assertionsDisabled || (getCommandID() == (short) 16 && this.m_binaryCommandContainer.getSize() == 24)) {
            return this.m_binaryCommandContainer.getInt(20);
        }
        throw new AssertionError();
    }
}
