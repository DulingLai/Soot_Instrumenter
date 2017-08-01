package com.spotify.protocol.client;

public class Debug {
    private static Assertion sAssertion = VoidAssertion.INSTANCE;
    private static Logger sLogger = VoidLogger.INSTANCE;

    public interface Assertion {
        void assertTrue(boolean z, String str) throws ;
    }

    public interface Logger {
        void mo3660d(String str, Object... objArr) throws ;

        void mo3661d(Throwable th, String str, Object... objArr) throws ;

        void mo3662e(String str, Object... objArr) throws ;

        void mo3663e(Throwable th, String str, Object... objArr) throws ;
    }

    private static class VoidAssertion implements Assertion {
        private static final VoidAssertion INSTANCE = new VoidAssertion();

        private VoidAssertion() throws  {
        }

        public void assertTrue(boolean expression, String message) throws  {
        }
    }

    private static class VoidLogger implements Logger {
        private static final VoidLogger INSTANCE = new VoidLogger();

        private VoidLogger() throws  {
        }

        public void mo3663e(Throwable e, String format, Object... args) throws  {
        }

        public void mo3661d(Throwable e, String format, Object... args) throws  {
        }

        public void mo3662e(String format, Object... args) throws  {
        }

        public void mo3660d(String format, Object... args) throws  {
        }
    }

    private Debug() throws  {
    }

    public static void setLogger(Logger $r1) throws  {
        if ($r1 == null) {
            $r1 = VoidLogger.INSTANCE;
        }
        sLogger = $r1;
    }

    public static void setAssertion(Assertion $r1) throws  {
        if ($r1 == null) {
            $r1 = VoidAssertion.INSTANCE;
        }
        sAssertion = $r1;
    }

    public static void m35e(Throwable $r0, String $r1, Object... $r2) throws  {
        sLogger.mo3663e($r0, $r1, $r2);
    }

    public static void m33d(Throwable $r0, String $r1, Object... $r2) throws  {
        sLogger.mo3661d($r0, $r1, $r2);
    }

    public static void m34e(String $r0, Object... $r1) throws  {
        sLogger.mo3662e($r0, $r1);
    }

    public static void m32d(String $r0, Object... $r1) throws  {
        sLogger.mo3660d($r0, $r1);
    }

    public static void assertTrue(boolean $z0, String $r0) throws  {
        sAssertion.assertTrue($z0, $r0);
    }
}
