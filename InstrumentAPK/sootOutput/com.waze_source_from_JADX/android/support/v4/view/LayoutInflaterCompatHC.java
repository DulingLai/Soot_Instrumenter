package android.support.v4.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.LayoutInflater.Factory;
import android.view.LayoutInflater.Factory2;
import android.view.View;
import java.lang.reflect.Field;

class LayoutInflaterCompatHC {
    private static final String TAG = "LayoutInflaterCompatHC";
    private static boolean sCheckedField;
    private static Field sLayoutInflaterFactory2Field;

    static class FactoryWrapperHC extends FactoryWrapper implements Factory2 {
        FactoryWrapperHC(LayoutInflaterFactory $r1) throws  {
            super($r1);
        }

        public View onCreateView(View $r1, String $r2, Context $r3, AttributeSet $r4) throws  {
            return this.mDelegateFactory.onCreateView($r1, $r2, $r3, $r4);
        }
    }

    LayoutInflaterCompatHC() throws  {
    }

    static void setFactory(LayoutInflater $r0, LayoutInflaterFactory $r1) throws  {
        FactoryWrapperHC $r3 = $r1 != null ? new FactoryWrapperHC($r1) : null;
        $r0.setFactory2($r3);
        Factory $r2 = $r0.getFactory();
        if ($r2 instanceof Factory2) {
            forceSetFactory2($r0, (Factory2) $r2);
        } else {
            forceSetFactory2($r0, $r3);
        }
    }

    static void forceSetFactory2(LayoutInflater $r0, Factory2 $r1) throws  {
        if (!sCheckedField) {
            try {
                sLayoutInflaterFactory2Field = LayoutInflater.class.getDeclaredField("mFactory2");
                sLayoutInflaterFactory2Field.setAccessible(true);
            } catch (NoSuchFieldException $r4) {
                Log.e(TAG, "forceSetFactory2 Could not find field 'mFactory2' on class " + LayoutInflater.class.getName() + "; inflation may have unexpected results.", $r4);
            }
            sCheckedField = true;
        }
        if (sLayoutInflaterFactory2Field != null) {
            try {
                sLayoutInflaterFactory2Field.set($r0, $r1);
            } catch (IllegalAccessException $r7) {
                Log.e(TAG, "forceSetFactory2 could not set the Factory2 on LayoutInflater " + $r0 + "; inflation may have unexpected results.", $r7);
            }
        }
    }
}
