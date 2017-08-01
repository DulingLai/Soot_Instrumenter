package com.abaltatech.wlappservices;

import dalvik.annotation.Signature;
import java.util.List;

interface IDefaultServiceSelector {
    ServiceDescriptor onSelectDefaultService(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;)", "Lcom/abaltatech/wlappservices/ServiceDescriptor;"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Lcom/abaltatech/wlappservices/ServiceDescriptor;", ">;)", "Lcom/abaltatech/wlappservices/ServiceDescriptor;"}) List<ServiceDescriptor> list) throws ;
}
