package com.abaltatech.wlappservices;

import com.abaltatech.weblink.service.interfaces.IWLAppsServiceHandler;
import dalvik.annotation.Signature;
import java.util.List;

class ServiceProxy_Remote extends ServiceProxy {
    private IWLAppsServiceHandler m_handler;

    public ServiceProxy_Remote(@Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) IWLAppsServiceHandler $r1, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String $r2, @Signature({"(", "Lcom/abaltatech/weblink/service/interfaces/IWLAppsServiceHandler;", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> $r3) throws  {
        super($r2, $r3);
        this.m_handler = $r1;
        if ($r1 == null) {
            throw new IllegalArgumentException("handler cannot be null");
        }
    }

    public IWLAppsServiceHandler getHandler() throws  {
        return this.m_handler;
    }
}
