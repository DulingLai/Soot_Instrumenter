package com.abaltatech.wlappservices;

import dalvik.annotation.Signature;
import java.util.List;

public interface IServiceStatusNotification {
    void onServiceRegistered(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) String str, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)V"}) List<String> list) throws ;

    void onServiceUnregistered(String str) throws ;
}
