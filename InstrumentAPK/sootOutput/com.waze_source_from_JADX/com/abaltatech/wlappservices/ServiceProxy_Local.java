package com.abaltatech.wlappservices;

import dalvik.annotation.Signature;
import java.util.List;

class ServiceProxy_Local extends ServiceProxy {
    IServiceHandler m_handler;

    ServiceProxy_Local(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/IServiceHandler;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/IServiceHandler;", ")V"}) List<String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/abaltatech/wlappservices/IServiceHandler;", ")V"}) IServiceHandler $r3) throws  {
        super($r1, $r2);
        this.m_handler = $r3;
    }
}
