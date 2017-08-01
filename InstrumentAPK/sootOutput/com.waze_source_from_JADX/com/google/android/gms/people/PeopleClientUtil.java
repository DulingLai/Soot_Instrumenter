package com.google.android.gms.people;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.ParcelFileDescriptor;
import com.google.android.gms.common.util.IOUtils;
import java.io.Closeable;
import java.io.FileInputStream;

/* compiled from: dalvik_source_com.waze.apk */
public class PeopleClientUtil {
    private PeopleClientUtil() throws  {
    }

    public static Bitmap decodeFileDescriptor(ParcelFileDescriptor $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        Closeable $r1 = new FileInputStream($r0.getFileDescriptor());
        try {
            Bitmap $r3 = BitmapFactory.decodeStream($r1);
            return $r3;
        } finally {
            IOUtils.closeQuietly($r1);
        }
    }
}
