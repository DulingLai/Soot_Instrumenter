package android.support.v4.view;

import android.view.LayoutInflater;

class LayoutInflaterCompatLollipop {
    LayoutInflaterCompatLollipop() throws  {
    }

    static void setFactory(LayoutInflater $r0, LayoutInflaterFactory $r1) throws  {
        $r0.setFactory2($r1 != null ? new FactoryWrapperHC($r1) : null);
    }
}
