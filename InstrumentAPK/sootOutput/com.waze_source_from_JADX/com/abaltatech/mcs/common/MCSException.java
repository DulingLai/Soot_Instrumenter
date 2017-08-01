package com.abaltatech.mcs.common;

import com.abaltatech.mcs.logger.MCSLogger;

public class MCSException extends Exception {
    private static final long serialVersionUID = 6808755760512918196L;

    public MCSException(String $r1) throws  {
        super($r1);
        MCSLogger.log("MCSException", $r1);
    }
}
