package com.facebook;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import android.util.Pair;
import com.facebook.internal.NativeAppCallAttachmentStore;
import dalvik.annotation.Signature;
import java.io.FileNotFoundException;
import java.util.UUID;

public class FacebookContentProvider extends ContentProvider {
    private static final String ATTACHMENT_URL_BASE = "content://com.facebook.app.FacebookContentProvider";
    private static final String TAG = FacebookContentProvider.class.getName();

    public int delete(Uri uri, String s, String[] strings) throws  {
        return 0;
    }

    public String getType(Uri uri) throws  {
        return null;
    }

    public Uri insert(Uri uri, ContentValues contentValues) throws  {
        return null;
    }

    public boolean onCreate() throws  {
        return true;
    }

    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) throws  {
        return null;
    }

    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) throws  {
        return 0;
    }

    public static String getAttachmentUrl(String $r0, UUID $r1, String $r2) throws  {
        return String.format("%s%s/%s/%s", new Object[]{ATTACHMENT_URL_BASE, $r0, $r1.toString(), $r2});
    }

    public ParcelFileDescriptor openFile(Uri $r1, String mode) throws FileNotFoundException {
        Pair $r4 = parseCallIdAndAttachmentName($r1);
        if ($r4 == null) {
            throw new FileNotFoundException();
        }
        try {
            return ParcelFileDescriptor.open(NativeAppCallAttachmentStore.openAttachment((UUID) $r4.first, (String) $r4.second), 268435456);
        } catch (FileNotFoundException $r3) {
            Log.e(TAG, "Got unexpected exception:" + $r3);
            throw $r3;
        }
    }

    Pair<UUID, String> parseCallIdAndAttachmentName(@Signature({"(", "Landroid/net/Uri;", ")", "Landroid/util/Pair", "<", "Ljava/util/UUID;", "Ljava/lang/String;", ">;"}) Uri $r1) throws  {
        try {
            String[] $r5 = $r1.getPath().substring(1).split("/");
            String $r3 = $r5[0];
            return new Pair(UUID.fromString($r3), $r5[1]);
        } catch (Exception e) {
            return null;
        }
    }
}
