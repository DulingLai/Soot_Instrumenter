package com.abaltatech.wlappservices;

import dalvik.annotation.Signature;
import java.util.List;

public class ServiceProxy_Dispatcher extends ServiceProxy {
    private String m_receiverID;
    private String m_senderID;
    private int m_serviceDescriptorID;

    ServiceProxy_Dispatcher(@Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) List<String> $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) int $i0, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r3, @Signature({"(", "Ljava/lang/String;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;I", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r4) throws  {
        super($r1, $r2);
        this.m_serviceDescriptorID = $i0;
        this.m_senderID = $r3;
        this.m_receiverID = $r4;
    }

    public int getServiceDescriptorID() throws  {
        return this.m_serviceDescriptorID;
    }

    public String getSenderID() throws  {
        return this.m_senderID;
    }

    public String getReceiverID() throws  {
        return this.m_receiverID;
    }
}
