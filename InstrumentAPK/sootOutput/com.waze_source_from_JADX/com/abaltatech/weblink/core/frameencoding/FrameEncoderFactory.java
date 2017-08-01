package com.abaltatech.weblink.core.frameencoding;

import com.abaltatech.mcs.logger.MCSLogger;
import dalvik.annotation.Signature;
import java.util.HashMap;

public class FrameEncoderFactory {
    private static final String TAG = "FrameEncoderFactory";
    private HashMap<Integer, Class<? extends IFrameEncoder>> m_encoderMap;

    private static class SingletonHolder {
        public static final FrameEncoderFactory INSTANCE = new FrameEncoderFactory();

        private SingletonHolder() throws  {
        }
    }

    private FrameEncoderFactory() throws  {
        this.m_encoderMap = new HashMap();
    }

    public static FrameEncoderFactory getInstance() throws  {
        return SingletonHolder.INSTANCE;
    }

    public void registerEncoder(@Signature({"(I", "Ljava/lang/Class", "<+", "Lcom/abaltatech/weblink/core/frameencoding/IFrameEncoder;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/Class", "<+", "Lcom/abaltatech/weblink/core/frameencoding/IFrameEncoder;", ">;)V"}) Class<? extends IFrameEncoder> $r1) throws  {
        this.m_encoderMap.put(Integer.valueOf($i0), $r1);
    }

    public void unregisterEncoder(int $i0) throws  {
        this.m_encoderMap.remove(Integer.valueOf($i0));
    }

    public IFrameEncoder createEncoder(int $i0) throws  {
        Class $r5 = (Class) this.m_encoderMap.get(Integer.valueOf($i0));
        if ($r5 == null) {
            return null;
        }
        try {
            return (IFrameEncoder) $r5.newInstance();
        } catch (Exception $r1) {
            MCSLogger.log(TAG, $r1.toString());
            return null;
        }
    }

    public IFrameEncoder createBestEncoder(int $i0) throws  {
        IFrameEncoder $r1 = null;
        for (int $i1 = 1; $i1 != 0; $i1 <<= 1) {
            if (($i1 & $i0) != 0) {
                IFrameEncoder $r2 = createEncoder($i1);
                if ($r2 != null) {
                    boolean $z0 = $r1 == null || $r1.getPriority() < $r2.getPriority();
                    if ($z0) {
                        $r1 = $r2;
                    }
                }
            }
        }
        return $r1;
    }
}
