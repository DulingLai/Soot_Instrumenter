package android.support.v4.media;

import android.media.browse.MediaBrowser.MediaItem;
import dalvik.annotation.Signature;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

class ParceledListSliceAdapterApi21 {
    private static Constructor sConstructor;

    ParceledListSliceAdapterApi21() throws  {
    }

    static {
        ReflectiveOperationException $r3;
        try {
            Class $r0 = Class.forName("android.content.pm.ParceledListSlice");
            Class[] $r1 = new Class[1];
            $r1[0] = List.class;
            sConstructor = $r0.getConstructor($r1);
        } catch (ClassNotFoundException e) {
            $r3 = e;
            $r3.printStackTrace();
        } catch (NoSuchMethodException e2) {
            $r3 = e2;
            $r3.printStackTrace();
        }
    }

    static Object newInstance(@Signature({"(", "Ljava/util/List", "<", "Landroid/media/browse/MediaBrowser$MediaItem;", ">;)", "Ljava/lang/Object;"}) List<MediaItem> $r0) throws  {
        ReflectiveOperationException $r4;
        try {
            return sConstructor.newInstance(new Object[]{$r0});
        } catch (InstantiationException e) {
            $r4 = e;
            $r4.printStackTrace();
            return null;
        } catch (IllegalAccessException e2) {
            $r4 = e2;
            $r4.printStackTrace();
            return null;
        } catch (InvocationTargetException e3) {
            $r4 = e3;
            $r4.printStackTrace();
            return null;
        }
    }
}
