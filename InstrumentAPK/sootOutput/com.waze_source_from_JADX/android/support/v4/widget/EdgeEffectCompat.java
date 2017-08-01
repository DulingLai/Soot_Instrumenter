package android.support.v4.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Build.VERSION;

public final class EdgeEffectCompat {
    private static final EdgeEffectImpl IMPL;
    private Object mEdgeEffect;

    interface EdgeEffectImpl {
        boolean draw(Object obj, Canvas canvas) throws ;

        void finish(Object obj) throws ;

        boolean isFinished(Object obj) throws ;

        Object newEdgeEffect(Context context) throws ;

        boolean onAbsorb(Object obj, int i) throws ;

        boolean onPull(Object obj, float f) throws ;

        boolean onPull(Object obj, float f, float f2) throws ;

        boolean onRelease(Object obj) throws ;

        void setSize(Object obj, int i, int i2) throws ;
    }

    static class BaseEdgeEffectImpl implements EdgeEffectImpl {
        public boolean draw(Object edgeEffect, Canvas canvas) throws  {
            return false;
        }

        public boolean isFinished(Object edgeEffect) throws  {
            return true;
        }

        public Object newEdgeEffect(Context context) throws  {
            return null;
        }

        public boolean onAbsorb(Object edgeEffect, int velocity) throws  {
            return false;
        }

        public boolean onPull(Object edgeEffect, float deltaDistance) throws  {
            return false;
        }

        public boolean onPull(Object edgeEffect, float deltaDistance, float displacement) throws  {
            return false;
        }

        public boolean onRelease(Object edgeEffect) throws  {
            return false;
        }

        BaseEdgeEffectImpl() throws  {
        }

        public void setSize(Object edgeEffect, int width, int height) throws  {
        }

        public void finish(Object edgeEffect) throws  {
        }
    }

    static class EdgeEffectIcsImpl implements EdgeEffectImpl {
        EdgeEffectIcsImpl() throws  {
        }

        public Object newEdgeEffect(Context $r1) throws  {
            return EdgeEffectCompatIcs.newEdgeEffect($r1);
        }

        public void setSize(Object $r1, int $i0, int $i1) throws  {
            EdgeEffectCompatIcs.setSize($r1, $i0, $i1);
        }

        public boolean isFinished(Object $r1) throws  {
            return EdgeEffectCompatIcs.isFinished($r1);
        }

        public void finish(Object $r1) throws  {
            EdgeEffectCompatIcs.finish($r1);
        }

        public boolean onPull(Object $r1, float $f0) throws  {
            return EdgeEffectCompatIcs.onPull($r1, $f0);
        }

        public boolean onRelease(Object $r1) throws  {
            return EdgeEffectCompatIcs.onRelease($r1);
        }

        public boolean onAbsorb(Object $r1, int $i0) throws  {
            return EdgeEffectCompatIcs.onAbsorb($r1, $i0);
        }

        public boolean draw(Object $r1, Canvas $r2) throws  {
            return EdgeEffectCompatIcs.draw($r1, $r2);
        }

        public boolean onPull(Object $r1, float $f0, float displacement) throws  {
            return EdgeEffectCompatIcs.onPull($r1, $f0);
        }
    }

    static class EdgeEffectLollipopImpl extends EdgeEffectIcsImpl {
        EdgeEffectLollipopImpl() throws  {
        }

        public boolean onPull(Object $r1, float $f0, float $f1) throws  {
            return EdgeEffectCompatLollipop.onPull($r1, $f0, $f1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new EdgeEffectLollipopImpl();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new EdgeEffectIcsImpl();
        } else {
            IMPL = new BaseEdgeEffectImpl();
        }
    }

    public EdgeEffectCompat(Context $r1) throws  {
        this.mEdgeEffect = IMPL.newEdgeEffect($r1);
    }

    public void setSize(int $i0, int $i1) throws  {
        IMPL.setSize(this.mEdgeEffect, $i0, $i1);
    }

    public boolean isFinished() throws  {
        return IMPL.isFinished(this.mEdgeEffect);
    }

    public void finish() throws  {
        IMPL.finish(this.mEdgeEffect);
    }

    public boolean onPull(float $f0) throws  {
        return IMPL.onPull(this.mEdgeEffect, $f0);
    }

    public boolean onPull(float $f0, float $f1) throws  {
        return IMPL.onPull(this.mEdgeEffect, $f0, $f1);
    }

    public boolean onRelease() throws  {
        return IMPL.onRelease(this.mEdgeEffect);
    }

    public boolean onAbsorb(int $i0) throws  {
        return IMPL.onAbsorb(this.mEdgeEffect, $i0);
    }

    public boolean draw(Canvas $r1) throws  {
        return IMPL.draw(this.mEdgeEffect, $r1);
    }
}
