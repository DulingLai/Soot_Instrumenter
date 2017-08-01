package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;
import android.os.Build.VERSION;
import java.io.File;

public abstract class DocumentFile {
    static final String TAG = "DocumentFile";
    private final DocumentFile mParent;

    public abstract boolean canRead() throws ;

    public abstract boolean canWrite() throws ;

    public abstract DocumentFile createDirectory(String str) throws ;

    public abstract DocumentFile createFile(String str, String str2) throws ;

    public abstract boolean delete() throws ;

    public abstract boolean exists() throws ;

    public abstract String getName() throws ;

    public abstract String getType() throws ;

    public abstract Uri getUri() throws ;

    public abstract boolean isDirectory() throws ;

    public abstract boolean isFile() throws ;

    public abstract long lastModified() throws ;

    public abstract long length() throws ;

    public abstract DocumentFile[] listFiles() throws ;

    public abstract boolean renameTo(String str) throws ;

    DocumentFile(DocumentFile $r1) throws  {
        this.mParent = $r1;
    }

    public static DocumentFile fromFile(File $r0) throws  {
        return new RawDocumentFile(null, $r0);
    }

    public static DocumentFile fromSingleUri(Context $r0, Uri $r1) throws  {
        if (VERSION.SDK_INT >= 19) {
            return new SingleDocumentFile(null, $r0, $r1);
        }
        return null;
    }

    public static DocumentFile fromTreeUri(Context $r0, Uri $r1) throws  {
        if (VERSION.SDK_INT >= 21) {
            return new TreeDocumentFile(null, $r0, DocumentsContractApi21.prepareTreeUri($r1));
        }
        return null;
    }

    public static boolean isDocumentUri(Context $r0, Uri $r1) throws  {
        if (VERSION.SDK_INT >= 19) {
            return DocumentsContractApi19.isDocumentUri($r0, $r1);
        }
        return false;
    }

    public DocumentFile getParentFile() throws  {
        return this.mParent;
    }

    public DocumentFile findFile(String $r1) throws  {
        for (DocumentFile this : listFiles()) {
            if ($r1.equals(getName())) {
                return this;
            }
        }
        return null;
    }
}
