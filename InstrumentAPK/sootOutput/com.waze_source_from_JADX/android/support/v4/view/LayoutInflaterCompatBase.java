package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.View;

class LayoutInflaterCompatBase {

    static class FactoryWrapper implements Factory {
        final LayoutInflaterFactory mDelegateFactory;

        FactoryWrapper(LayoutInflaterFactory $r1) throws  {
            this.mDelegateFactory = $r1;
        }

        public View onCreateView(String $r1, Context $r2, AttributeSet $r3) throws  {
            return this.mDelegateFactory.onCreateView(null, $r1, $r2, $r3);
        }

        public String toString() throws  {
            return getClass().getName() + "{" + this.mDelegateFactory + "}";
        }
    }

    LayoutInflaterCompatBase() throws  {
    }

    static void setFactory(LayoutInflater $r0, LayoutInflaterFactory $r1) throws  {
        $r0.setFactory($r1 != null ? new FactoryWrapper($r1) : null);
    }

    static LayoutInflaterFactory getFactory(LayoutInflater $r0) throws  {
        Factory $r1 = $r0.getFactory();
        if ($r1 instanceof FactoryWrapper) {
            return ((FactoryWrapper) $r1).mDelegateFactory;
        }
        return null;
    }
}
