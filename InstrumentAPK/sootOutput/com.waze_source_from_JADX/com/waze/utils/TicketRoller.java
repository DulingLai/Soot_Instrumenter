package com.waze.utils;

import junit.framework.Assert;

public class TicketRoller {
    private static int[] typeTickets = new int[Type.values().length];

    public enum Type {
        Test,
        Global,
        Handler,
        ActivityResult,
        Last
    }

    static {
        typeTickets[Type.ActivityResult.ordinal()] = 4096;
    }

    public static synchronized int get(Type t) {
        int i;
        synchronized (TicketRoller.class) {
            int[] iArr = typeTickets;
            int ordinal = t.ordinal();
            i = iArr[ordinal] + 1;
            iArr[ordinal] = i;
        }
        return i;
    }

    public static void testThis() {
        Assert.assertEquals(get(Type.Test), 1);
        Assert.assertEquals(get(Type.Test), 2);
        Assert.assertEquals(get(Type.Test), 3);
        Assert.assertEquals(get(Type.Last), 1);
        Assert.assertEquals(get(Type.Test), 4);
        Assert.assertEquals(get(Type.Test), 5);
        Assert.assertEquals(get(Type.Last), 2);
        Assert.assertEquals(get(Type.Last), 3);
        Assert.assertEquals(get(Type.Last), 4);
        Assert.assertEquals(get(Type.Test), 6);
        Assert.assertEquals(get(Type.Test), 7);
        Assert.assertEquals(get(Type.Last), 5);
    }

    private TicketRoller() {
    }
}
