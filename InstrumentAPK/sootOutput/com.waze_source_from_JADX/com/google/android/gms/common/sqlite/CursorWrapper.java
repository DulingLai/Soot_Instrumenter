package com.google.android.gms.common.sqlite;

import android.annotation.TargetApi;
import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import com.google.android.gms.common.util.zzr;
import java.lang.reflect.Field;

/* compiled from: dalvik_source_com.waze.apk */
public class CursorWrapper extends android.database.CursorWrapper implements CrossProcessCursor {
    private static Field LH = null;
    private AbstractWindowedCursor LI;

    public CursorWrapper(Cursor $r1) throws  {
        super($r1);
        this.LI = zzb($r1);
    }

    @TargetApi(10)
    private static Cursor zza(android.database.CursorWrapper $r0) throws  {
        if (zzr.zzaza()) {
            return $r0.getWrappedCursor();
        }
        if ($r0 instanceof CursorWrapper) {
            return (AbstractWindowedCursor) ((CursorWrapper) $r0).getWrappedCursor();
        }
        if (LH == null) {
            try {
                LH = android.database.CursorWrapper.class.getDeclaredField("mCursor");
                LH.setAccessible(true);
            } catch (Exception $r6) {
                throw new IllegalStateException("Unable to unwrap cursor", $r6);
            }
        }
        return (Cursor) LH.get($r0);
    }

    private static AbstractWindowedCursor zzb(Cursor $r0) throws  {
        int $i0 = 0;
        while ($i0 < 10 && ($r0 instanceof android.database.CursorWrapper)) {
            $i0++;
            $r0 = zza((android.database.CursorWrapper) $r0);
        }
        if ($r0 instanceof AbstractWindowedCursor) {
            return (AbstractWindowedCursor) $r0;
        }
        String $r3 = "Unknown type: ";
        String $r5 = String.valueOf($r0.getClass().getName());
        throw new IllegalArgumentException($r5.length() != 0 ? $r3.concat($r5) : new String("Unknown type: "));
    }

    public void fillWindow(int $i0, CursorWindow $r1) throws  {
        this.LI.fillWindow($i0, $r1);
    }

    public CursorWindow getWindow() throws  {
        return this.LI.getWindow();
    }

    public AbstractWindowedCursor getWrappedCursor() throws  {
        return this.LI;
    }

    public boolean onMove(int $i0, int $i1) throws  {
        return this.LI.onMove($i0, $i1);
    }

    public void setWindow(CursorWindow $r1) throws  {
        this.LI.setWindow($r1);
    }
}
