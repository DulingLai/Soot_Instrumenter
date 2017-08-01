package android.support.v4.content;

import android.content.SharedPreferences.Editor;
import android.support.annotation.NonNull;

class EditorCompatGingerbread {
    EditorCompatGingerbread() throws  {
    }

    public static void apply(@NonNull Editor $r0) throws  {
        try {
            $r0.apply();
        } catch (AbstractMethodError e) {
            $r0.commit();
        }
    }
}
