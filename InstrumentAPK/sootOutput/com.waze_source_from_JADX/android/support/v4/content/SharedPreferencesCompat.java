package android.support.v4.content;

import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;

public final class SharedPreferencesCompat {

    public static final class EditorCompat {
        private static EditorCompat sInstance;
        private final Helper mHelper;

        private interface Helper {
            void apply(@NonNull Editor editor) throws ;
        }

        private static class EditorHelperApi9Impl implements Helper {
            private EditorHelperApi9Impl() throws  {
            }

            public void apply(@NonNull Editor $r1) throws  {
                EditorCompatGingerbread.apply($r1);
            }
        }

        private static class EditorHelperBaseImpl implements Helper {
            private EditorHelperBaseImpl() throws  {
            }

            public void apply(@NonNull Editor $r1) throws  {
                $r1.commit();
            }
        }

        private EditorCompat() throws  {
            if (VERSION.SDK_INT >= 9) {
                this.mHelper = new EditorHelperApi9Impl();
            } else {
                this.mHelper = new EditorHelperBaseImpl();
            }
        }

        public static EditorCompat getInstance() throws  {
            if (sInstance == null) {
                sInstance = new EditorCompat();
            }
            return sInstance;
        }

        public void apply(@NonNull Editor $r1) throws  {
            this.mHelper.apply($r1);
        }
    }

    private SharedPreferencesCompat() throws  {
    }
}
