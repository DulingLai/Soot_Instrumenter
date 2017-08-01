package android.support.v4.content;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.OperationCanceledException;

class ContentResolverCompatJellybean {
    ContentResolverCompatJellybean() throws  {
    }

    public static Cursor query(ContentResolver $r0, Uri $r1, String[] $r2, String $r3, String[] $r4, String $r5, Object $r6) throws  {
        return $r0.query($r1, $r2, $r3, $r4, $r5, (CancellationSignal) $r6);
    }

    static boolean isFrameworkOperationCanceledException(Exception $r0) throws  {
        return $r0 instanceof OperationCanceledException;
    }
}
