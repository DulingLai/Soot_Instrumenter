package com.abaltatech.weblink.core.commandhandling;

import com.abaltatech.weblink.core.DataBuffer;

public class TouchCommand extends Command {
    static final /* synthetic */ boolean $assertionsDisabled = (!TouchCommand.class.desiredAssertionStatus());
    protected static final int CMD_FIXED_SIZE = 8;
    public static final short ID = (short) 72;
    protected static final int POINT_DATA_SIZE = 20;
    public static final int TOUCH_BEGIN = 0;
    public static final int TOUCH_END = 2;
    public static final int TOUCH_UPDATE = 1;

    public class TouchPoint {
        public static final int TOUCH_POINT_MOVED = 2;
        public static final int TOUCH_POINT_PRESSED = 1;
        public static final int TOUCH_POINT_RELEASED = 8;
        public static final int TOUCH_POINT_STATEMASK = 15;
        public static final int TOUCH_POINT_STATIONARY = 4;
        public int m_id;
        public int m_posX;
        public int m_posY;
        public float m_pressure;
        public int m_state;
    }

    public TouchCommand(DataBuffer $r1) throws  {
        super($r1);
        if (!$assertionsDisabled && getCommandID() != (short) 72) {
            throw new AssertionError();
        }
    }

    public int getEventType() throws  {
        if ($assertionsDisabled || getCommandID() == (short) 72) {
            return this.m_binaryCommandContainer.getInt(8);
        }
        throw new AssertionError();
    }

    public int getCount() throws  {
        if ($assertionsDisabled || getCommandID() == (short) 72) {
            return this.m_binaryCommandContainer.getInt(12);
        }
        throw new AssertionError();
    }

    public TouchPoint getTouchPoint(int $i0) throws  {
        if ($assertionsDisabled || getCommandID() == (short) 72) {
            int $i2 = getCount();
            if ($i0 < 0) {
                return null;
            }
            if ($i0 >= $i2) {
                return null;
            }
            $i0 = ($i0 * 20) + 16;
            TouchPoint $r2 = new TouchPoint();
            $r2.m_id = this.m_binaryCommandContainer.getInt($i0);
            $i0 += 4;
            $r2.m_posX = this.m_binaryCommandContainer.getInt($i0);
            $i0 += 4;
            $r2.m_posY = this.m_binaryCommandContainer.getInt($i0);
            $i0 += 4;
            $r2.m_state = this.m_binaryCommandContainer.getInt($i0);
            $r2.m_pressure = this.m_binaryCommandContainer.getFloat($i0 + 4);
            return $r2;
        }
        throw new AssertionError();
    }
}
