package android.support.v4.provider;

import android.content.Context;
import android.net.Uri;

class TreeDocumentFile extends DocumentFile {
    private Context mContext;
    private Uri mUri;

    TreeDocumentFile(DocumentFile $r1, Context $r2, Uri $r3) throws  {
        super($r1);
        this.mContext = $r2;
        this.mUri = $r3;
    }

    public DocumentFile createFile(String $r1, String $r2) throws  {
        Uri $r3 = DocumentsContractApi21.createFile(this.mContext, this.mUri, $r1, $r2);
        return $r3 != null ? new TreeDocumentFile(this, this.mContext, $r3) : null;
    }

    public DocumentFile createDirectory(String $r1) throws  {
        Uri $r2 = DocumentsContractApi21.createDirectory(this.mContext, this.mUri, $r1);
        return $r2 != null ? new TreeDocumentFile(this, this.mContext, $r2) : null;
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
        Uri[] $r3 = DocumentsContractApi21.listFiles(this.mContext, this.mUri);
        DocumentFile[] $r1 = new DocumentFile[$r3.length];
        for (int $i0 = 0; $i0 < $r3.length; $i0++) {
            $r1[$i0] = new TreeDocumentFile(this, this.mContext, $r3[$i0]);
        }
        return $r1;
    }

    public boolean renameTo(String $r1) throws  {
        Uri $r2 = DocumentsContractApi21.renameTo(this.mContext, this.mUri, $r1);
        if ($r2 == null) {
            return false;
        }
        this.mUri = $r2;
        return true;
    }
}
