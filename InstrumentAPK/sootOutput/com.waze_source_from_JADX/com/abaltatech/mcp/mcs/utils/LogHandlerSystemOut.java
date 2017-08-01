package com.abaltatech.mcp.mcs.utils;

import com.abaltatech.mcp.mcs.logger.IMCSLogHandler;

public class LogHandlerSystemOut implements IMCSLogHandler {
    public void log(String $r1) throws  {
        System.out.println("" + System.currentTimeMillis() + "  " + $r1);
    }

    public void log(String $r1, String $r2) throws  {
        log($r1 + ": " + $r2);
    }

    public void log(String $r1, String $r2, Throwable e) throws  {
        log($r1 + ": " + $r2);
    }
}
