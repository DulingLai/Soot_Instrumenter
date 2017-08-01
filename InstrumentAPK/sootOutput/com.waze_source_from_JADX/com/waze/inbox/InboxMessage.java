package com.waze.inbox;

import java.io.Serializable;

public class InboxMessage implements Serializable {
    protected static final int MSG_TYPE_CONTENT = 1;
    protected static final int MSG_TYPE_LINK = 0;
    protected static final int MSG_TYPE_SECURED_LINK = 2;
    private static final long serialVersionUID = 1;
    protected String id;
    protected String message;
    protected int messageType;
    protected String preview;
    protected long sent;
    protected String sentFString;
    protected long sentTime;
    protected String title;
    protected boolean unread;

    public int hashCode() throws  {
        return this.id.hashCode();
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null) {
            return false;
        }
        if (getClass() != $r1.getClass()) {
            return false;
        }
        return this.id.equals(((InboxMessage) $r1).id);
    }
}
