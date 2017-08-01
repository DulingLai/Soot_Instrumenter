package com.facebook.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.Uri;
import android.util.Log;
import com.facebook.FacebookContentProvider;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import dalvik.annotation.Signature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

public final class NativeAppCallAttachmentStore {
    static final String ATTACHMENTS_DIR_NAME = "com.facebook.NativeAppCallAttachmentStore.files";
    private static final String TAG = NativeAppCallAttachmentStore.class.getName();
    private static File attachmentsDirectory;

    public static final class Attachment {
        private final String attachmentName;
        private final String attachmentUrl;
        private Bitmap bitmap;
        private final UUID callId;
        private boolean isContentUri;
        private Uri originalUri;
        private boolean shouldCreateFile;

        private Attachment(UUID $r1, Bitmap $r2, Uri $r3) throws  {
            String $r4;
            boolean $z0 = true;
            this.callId = $r1;
            this.bitmap = $r2;
            this.originalUri = $r3;
            if ($r3 != null) {
                $r4 = $r3.getScheme();
                if ("content".equalsIgnoreCase($r4)) {
                    this.isContentUri = true;
                    if ($r3.getAuthority() == null || $r3.getAuthority().startsWith("media")) {
                        $z0 = false;
                    }
                    this.shouldCreateFile = $z0;
                } else if ("file".equalsIgnoreCase($r3.getScheme())) {
                    this.shouldCreateFile = true;
                } else if (!Utility.isWebUri($r3)) {
                    throw new FacebookException("Unsupported scheme for media Uri : " + $r4);
                }
            } else if ($r2 != null) {
                this.shouldCreateFile = true;
            } else {
                throw new FacebookException("Cannot share media without a bitmap or Uri set");
            }
            this.attachmentName = !this.shouldCreateFile ? null : UUID.randomUUID().toString();
            if (this.shouldCreateFile) {
                $r4 = FacebookContentProvider.getAttachmentUrl(FacebookSdk.getApplicationId(), $r1, this.attachmentName);
            } else {
                $r4 = this.originalUri.toString();
            }
            this.attachmentUrl = $r4;
        }

        public String getAttachmentUrl() throws  {
            return this.attachmentUrl;
        }
    }

    private NativeAppCallAttachmentStore() throws  {
    }

    public static Attachment createAttachment(UUID $r0, Bitmap $r1) throws  {
        Validate.notNull($r0, "callId");
        Validate.notNull($r1, "attachmentBitmap");
        return new Attachment($r0, $r1, null);
    }

    public static Attachment createAttachment(UUID $r0, Uri $r1) throws  {
        Validate.notNull($r0, "callId");
        Validate.notNull($r1, "attachmentUri");
        return new Attachment($r0, null, $r1);
    }

    private static void processAttachmentBitmap(Bitmap $r0, File $r1) throws IOException {
        FileOutputStream $r2 = new FileOutputStream($r1);
        try {
            $r0.compress(CompressFormat.JPEG, 100, $r2);
        } finally {
            Utility.closeQuietly($r2);
        }
    }

    private static void processAttachmentFile(Uri $r0, boolean $z0, File $r1) throws IOException {
        InputStream $r5;
        FileOutputStream $r3 = new FileOutputStream($r1);
        if ($z0) {
            $r5 = FacebookSdk.getApplicationContext().getContentResolver().openInputStream($r0);
        } else {
            try {
                $r5 = new FileInputStream($r0.getPath());
            } catch (Throwable th) {
                Utility.closeQuietly($r3);
            }
        }
        Utility.copyAndCloseInputStream($r5, $r3);
        Utility.closeQuietly($r3);
    }

    public static void addAttachments(@Signature({"(", "Ljava/util/Collection", "<", "Lcom/facebook/internal/NativeAppCallAttachmentStore$Attachment;", ">;)V"}) Collection<Attachment> $r0) throws  {
        if ($r0 != null && $r0.size() != 0) {
            if (attachmentsDirectory == null) {
                cleanupAllAttachments();
            }
            ensureAttachmentsDirectoryExists();
            ArrayList<File> $r2 = new ArrayList();
            File $r3;
            try {
                for (Attachment $r6 : $r0) {
                    if ($r6.shouldCreateFile) {
                        $r3 = getAttachmentFile($r6.callId, $r6.attachmentName, true);
                        $r2.add($r3);
                        if ($r6.bitmap != null) {
                            processAttachmentBitmap($r6.bitmap, $r3);
                        } else if ($r6.originalUri != null) {
                            processAttachmentFile($r6.originalUri, $r6.isContentUri, $r3);
                        }
                    }
                }
            } catch (Throwable $r1) {
                Log.e(TAG, "Got unexpected exception:" + $r1);
                for (File $r32 : $r2) {
                    try {
                        $r32.delete();
                    } catch (Exception e) {
                    }
                }
                throw new FacebookException($r1);
            }
        }
    }

    public static void cleanupAttachmentsForCall(UUID $r0) throws  {
        File $r1 = getAttachmentsDirectoryForCall($r0, false);
        if ($r1 != null) {
            Utility.deleteDirectory($r1);
        }
    }

    public static File openAttachment(UUID $r0, String $r1) throws FileNotFoundException {
        if (Utility.isNullOrEmpty($r1) || $r0 == null) {
            throw new FileNotFoundException();
        }
        try {
            return getAttachmentFile($r0, $r1, false);
        } catch (IOException e) {
            throw new FileNotFoundException();
        }
    }

    static synchronized File getAttachmentsDirectory() throws  {
        Class cls = NativeAppCallAttachmentStore.class;
        synchronized (cls) {
            try {
                if (attachmentsDirectory == null) {
                    attachmentsDirectory = new File(FacebookSdk.getApplicationContext().getCacheDir(), ATTACHMENTS_DIR_NAME);
                }
                File $r0 = attachmentsDirectory;
                return $r0;
            } finally {
                cls = NativeAppCallAttachmentStore.class;
            }
        }
    }

    static File ensureAttachmentsDirectoryExists() throws  {
        File $r0 = getAttachmentsDirectory();
        $r0.mkdirs();
        return $r0;
    }

    static File getAttachmentsDirectoryForCall(UUID $r0, boolean $z0) throws  {
        if (attachmentsDirectory == null) {
            return null;
        }
        File $r1 = new File(attachmentsDirectory, $r0.toString());
        if (!$z0 || $r1.exists()) {
            return $r1;
        }
        $r1.mkdirs();
        return $r1;
    }

    static File getAttachmentFile(UUID $r0, String $r1, boolean $z0) throws IOException {
        File $r4 = getAttachmentsDirectoryForCall($r0, $z0);
        if ($r4 == null) {
            return null;
        }
        try {
            return new File($r4, URLEncoder.encode($r1, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }

    public static void cleanupAllAttachments() throws  {
        Utility.deleteDirectory(getAttachmentsDirectory());
    }
}
