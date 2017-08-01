package com.abaltatech.mcs.utils;

import com.abaltatech.mcs.logger.IMCSLogHandler;

public class LogHandlerBlackBerry implements IMCSLogHandler {
    private static final String m_prefix = "===================  ";

    public void log(String $r1) throws  {
        System.out.println(m_prefix + $r1);
    }

    public void log(String $r1, String $r2) throws  {
        System.out.println(m_prefix + $r1 + ": " + $r2);
    }

    public void log(String $r1, String $r2, Throwable e) throws  {
        System.out.println(m_prefix + $r1 + ": " + $r2);
    }
}
