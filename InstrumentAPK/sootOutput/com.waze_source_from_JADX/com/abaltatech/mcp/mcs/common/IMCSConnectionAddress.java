package com.abaltatech.mcp.mcs.common;

public interface IMCSConnectionAddress {
    boolean isSameAs(IMCSConnectionAddress iMCSConnectionAddress) throws ;

    boolean isSubsetOf(IMCSConnectionAddress iMCSConnectionAddress) throws ;

    String toString() throws ;
}
