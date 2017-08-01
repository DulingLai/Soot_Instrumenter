package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;

class SingleDocumentFile extends DocumentFile {
    private Context mContext;
    private Uri mUri;

    SingleDocumentFile(DocumentFile $r1, Context $r2, Uri $r3) throws  {
        super($r1);
        this.mContext = $r2;
        this.mUri = $r3;
    }

    public DocumentFile createFile(String mimeType, String displayName) throws  {
        throw new UnsupportedOperationException();
    }

    public DocumentFile createDirectory(String displayName) throws  {
        throw new UnsupportedOperationException();
    }

    public Uri getUri() throws  {
        return this.mUri;
    }

    public String getName() throws  {
        return DocumentsContractApi19.getName(this.mContext, this.mUri);
    }

    public String getType() throws  {
        return DocumentsContractApi19.getType(this.mContext, this.mUri);
    }

    public boolean isDirectory() throws  {
        return DocumentsContractApi19.isDirectory(this.mContext, this.mUri);
    }

    public boolean isFile() throws  {
        return DocumentsContractApi19.isFile(this.mContext, this.mUri);
    }

    public long lastModified() throws  {
        return DocumentsContractApi19.lastModified(this.mContext, this.mUri);
    }

    public long length() throws  {
        return DocumentsContractApi19.length(this.mContext, this.mUri);
    }

    public boolean canRead() throws  {
        return DocumentsContractApi19.canRead(this.mContext, this.mUri);
    }

    public boolean canWrite() throws  {
        return DocumentsContractApi19.canWrite(this.mContext, this.mUri);
    }

    public boolean delete() throws  {
        return DocumentsContractApi19.delete(this.mContext, this.mUri);
    }

    public boolean exists() throws  {
        return DocumentsContractApi19.exists(this.mContext, this.mUri);
    }

    public DocumentFile[] listFiles() throws  {
        throw new UnsupportedOperationException();
    }

    public boolean renameTo(String displayName) throws  {
        throw new UnsupportedOperationException();
    }
}
