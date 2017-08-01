package com.abaltatech.mcp.weblink.core.commandhandling;

public interface IWLConnection {
    void registerHandler(short s, ICommandHandler iCommandHandler) throws ;

    boolean sendCommand(Command command) throws ;
}
