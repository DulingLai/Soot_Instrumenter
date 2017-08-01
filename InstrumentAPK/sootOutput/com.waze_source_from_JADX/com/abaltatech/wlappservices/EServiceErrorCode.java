package com.abaltatech.wlappservices;

public enum EServiceErrorCode {
    NotAvailable,
    AccessDenied,
    Busy,
    UnsupportedRequest,
    InvalidArgument,
    CancelledByCaller;

    public static class EServiceErrorCodeWrapper {
        private int m_value;

        EServiceErrorCodeWrapper(int $i0) throws  {
            this.m_value = $i0;
        }

        EServiceErrorCodeWrapper(EServiceErrorCode $r1) throws  {
            this.m_value = EServiceErrorCode.toByte($r1);
        }

        public void setErrorCode(EServiceErrorCode $r1) throws  {
            this.m_value = EServiceErrorCode.toByte($r1);
        }

        public void setErrorCode(int $i0) throws  {
            this.m_value = $i0;
        }

        public EServiceErrorCode getErrorCode() throws  {
            return EServiceErrorCode.valueOf((byte) this.m_value);
        }

        public int getValue() throws  {
            return this.m_value;
        }
    }

    public static byte toByte(EServiceErrorCode $r0) throws  {
        if ($r0 == null) {
            return (byte) -1;
        }
        switch ($r0) {
            case NotAvailable:
                return (byte) 0;
            case AccessDenied:
                return (byte) 1;
            case Busy:
                return (byte) 2;
            case UnsupportedRequest:
                return (byte) 3;
            case InvalidArgument:
                return (byte) 4;
            case CancelledByCaller:
                return (byte) 5;
            default:
                throw new UnsupportedOperationException("Please add the other cases!");
        }
    }

    public static EServiceErrorCode valueOf(byte $b0) throws  {
        switch ($b0) {
            case (byte) -1:
                return null;
            case (byte) 0:
                return NotAvailable;
            case (byte) 1:
                return AccessDenied;
            case (byte) 2:
                return Busy;
            case (byte) 3:
                return UnsupportedRequest;
            case (byte) 4:
                return InvalidArgument;
            case (byte) 5:
                return CancelledByCaller;
            default:
                throw new UnsupportedOperationException("Please add the other cases!");
        }
    }
}
