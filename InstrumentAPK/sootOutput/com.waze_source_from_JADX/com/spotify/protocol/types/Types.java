package com.spotify.protocol.types;

import java.util.Locale;

public interface Types {

    public static class RequestId {
        public static final RequestId NONE = new RequestId(-1);
        private final int mValue;

        public int getRaw() throws  {
            return this.mValue;
        }

        private RequestId(int $i0) throws  {
            this.mValue = $i0;
        }

        public static RequestId from(int $i0) throws  {
            return new RequestId($i0);
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null || getClass() != $r1.getClass()) {
                return false;
            }
            return this.mValue == ((RequestId) $r1).mValue;
        }

        public int hashCode() throws  {
            return this.mValue;
        }

        public String toString() throws  {
            return String.format(Locale.US, "RequestId{%d}", new Object[]{Integer.valueOf(this.mValue)});
        }
    }

    public static class SubscriptionId {
        public static final SubscriptionId NONE = new SubscriptionId(-1);
        private final int mValue;

        public int getRaw() throws  {
            return this.mValue;
        }

        private SubscriptionId(int $i0) throws  {
            this.mValue = $i0;
        }

        public static SubscriptionId from(int $i0) throws  {
            return new SubscriptionId($i0);
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null || getClass() != $r1.getClass()) {
                return false;
            }
            return this.mValue == ((SubscriptionId) $r1).mValue;
        }

        public int hashCode() throws  {
            return this.mValue;
        }

        public String toString() throws  {
            return String.format(Locale.US, "SubscriptionId{%d}", new Object[]{Integer.valueOf(this.mValue)});
        }
    }
}
