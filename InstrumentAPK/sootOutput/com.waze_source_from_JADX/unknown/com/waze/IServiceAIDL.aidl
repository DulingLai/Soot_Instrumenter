// IServiceAIDL.aidl
package com.waze;

// Declare any non-default types here with import statements

interface IServiceAIDL {
    /** Request the process ID of this service, to do evil things with it. */
    int getPid();

    /** Send encrypt key that generated each session to the Waze app*/
    void sendKey(String key);

    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);
}
