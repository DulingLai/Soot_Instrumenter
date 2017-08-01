package com.abaltatech.mcp.mcs.logger;

public class MCSLogger {
    private static final int MAX_OBJECTS = 5;
    private static IMCSLogHandler[] m_loggers = new IMCSLogHandler[5];
    private static int m_maxIndex = 0;

    public static synchronized void registerLogger(IMCSLogHandler $r0) throws  {
        synchronized (MCSLogger.class) {
            int $i0 = 5;
            int $i1 = 0;
            while ($i1 < 5) {
                if (m_loggers[$i1] == $r0) {
                    break;
                }
                try {
                    if (m_loggers[$i1] == null && $i1 < 5) {
                        $i0 = $i1;
                        break;
                    }
                    $i1++;
                } catch (Throwable th) {
                    Class cls = MCSLogger.class;
                }
            }
            if ($i0 < 5) {
                m_loggers[$i0] = $r0;
                $i0++;
                if (m_maxIndex < $i0) {
                    m_maxIndex = $i0;
                }
            }
        }
    }

    public static synchronized void unregisterLogger(IMCSLogHandler $r0) throws  {
        synchronized (MCSLogger.class) {
            int $i0 = 0;
            while ($i0 < 5) {
                try {
                    if (m_loggers[$i0] == $r0) {
                        m_loggers[$i0] = null;
                        if (m_maxIndex == $i0 + 1) {
                            m_maxIndex--;
                        }
                    } else {
                        $i0++;
                    }
                } catch (Throwable th) {
                    Class cls = MCSLogger.class;
                }
            }
        }
    }

    public static synchronized void log(String $r0) throws  {
        synchronized (MCSLogger.class) {
            if ($r0 == null) {
                try {
                    $r0 = "< NULL >";
                } catch (Throwable th) {
                    Class cls = MCSLogger.class;
                }
            }
            for (int $i0 = 0; $i0 < m_maxIndex; $i0++) {
                if (m_loggers[$i0] != null) {
                    try {
                        m_loggers[$i0].log($r0);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public static synchronized void log(String $r0, String $r1) throws  {
        synchronized (MCSLogger.class) {
            if ($r1 == null) {
                try {
                    $r1 = "< NULL >";
                } catch (Throwable th) {
                    Class cls = MCSLogger.class;
                }
            }
            if ($r0 == null) {
                $r0 = "< NULL >";
            }
            for (int $i0 = 0; $i0 < m_maxIndex; $i0++) {
                if (m_loggers[$i0] != null) {
                    try {
                        m_loggers[$i0].log($r0, $r1);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    public static synchronized void log(String $r1, String $r2, Throwable $r0) throws  {
        synchronized (MCSLogger.class) {
            if ($r2 == null) {
                try {
                    $r2 = "< NULL >";
                } catch (Throwable th) {
                    Class cls = MCSLogger.class;
                }
            }
            if ($r1 == null) {
                $r1 = "< NULL >";
            }
            for (int $i0 = 0; $i0 < m_maxIndex; $i0++) {
                if (m_loggers[$i0] != null) {
                    try {
                        m_loggers[$i0].log($r1, $r2, $r0);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }
}
