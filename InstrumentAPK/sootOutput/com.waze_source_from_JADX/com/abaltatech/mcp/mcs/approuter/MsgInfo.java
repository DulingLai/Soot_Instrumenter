package com.abaltatech.mcp.mcs.approuter;

public class MsgInfo {
    public final AppMessage Message;
    public final long Timestamp = System.currentTimeMillis();

    public MsgInfo(AppMessage $r1) throws  {
        this.Message = $r1;
    }
}
