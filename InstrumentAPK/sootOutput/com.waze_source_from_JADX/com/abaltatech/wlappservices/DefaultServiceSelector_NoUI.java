package com.abaltatech.wlappservices;

import dalvik.annotation.Signature;
import java.util.List;

class DefaultServiceSelector_NoUI implements IDefaultServiceSelector {
    DefaultServiceSelector_NoUI() throws  {
    }

    public ServiceDescriptor onSelectDefaultService(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;)", "Lcom/abaltatech/wlappservices/ServiceDescriptor;"}) String protocol, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;)", "Lcom/abaltatech/wlappservices/ServiceDescriptor;"}) List<ServiceDescriptor> $r2) throws  {
        if ($r2 == null || $r2.size() < 1) {
            return null;
        }
        ServiceDescriptor $r3 = null;
        for (ServiceDescriptor $r6 : $r2) {
            if ($r6 != null && ($r3 == null || $r3.getTimestamp().compareTo($r6.getTimestamp()) < 0)) {
                $r3 = $r6;
            }
        }
        return $r3;
    }
}
