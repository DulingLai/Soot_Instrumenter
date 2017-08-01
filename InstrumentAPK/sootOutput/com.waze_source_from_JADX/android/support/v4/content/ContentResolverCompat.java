package android.support.v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build.VERSION;
import android.support.v4.os.CancellationSignal;
import android.support.v4.os.OperationCanceledException;

public final class ContentResolverCompat {
    private static final ContentResolverCompatImpl IMPL;

    interface ContentResolverCompatImpl {
        Cursor query(ContentResolver contentResolver, Uri uri, String[] strArr, String str, String[] strArr2, String str2, CancellationSignal cancellationSignal) throws ;
    }

    static class ContentResolverCompatImplBase implements ContentResolverCompatImpl {
        ContentResolverCompatImplBase() throws  {
        }

        public Cursor query(ContentResolver $r1, Uri $r2, String[] $r3, String $r4, String[] $r5, String $r6, CancellationSignal $r7) throws  {
            if ($r7 != null) {
                $r7.throwIfCanceled();
            }
            return $r1.query($r2, $r3, $r4, $r5, $r6);
        }
    }

    static class ContentResolverCompatImplJB extends ContentResolverCompatImplBase {
        ContentResolverCompatImplJB() throws  {
        }

        public Cursor query(ContentResolver $r1, Uri $r2, String[] $r3, String $r4, String[] $r5, String $r6, CancellationSignal $r7) throws  {
            Object $r9;
            if ($r7 != null) {
                try {
                    $r9 = $r7.getCancellationSignalObject();
                } catch (Exception $r8) {
                    if (ContentResolverCompatJellybean.isFrameworkOperationCanceledException($r8)) {
                        throw new OperationCanceledException();
                    }
                    throw $r8;
                }
            }
            $r9 = null;
            return ContentResolverCompatJellybean.query($r1, $r2, $r3, $r4, $r5, $r6, $r9);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new ContentResolverCompatImplJB();
        } else {
            IMPL = new ContentResolverCompatImplBase();
        }
    }

    private ContentResolverCompat() throws  {
    }

    public static Cursor query(ContentResolver $r0, Uri $r1, String[] $r2, String $r3, String[] $r4, String $r5, CancellationSignal $r6) throws  {
        return IMPL.query($r0, $r1, $r2, $r3, $r4, $r5, $r6);
    }
}
