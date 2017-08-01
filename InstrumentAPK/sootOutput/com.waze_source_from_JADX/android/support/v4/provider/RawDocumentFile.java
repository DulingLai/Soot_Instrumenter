package android.support.v4.provider;

import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

class RawDocumentFile extends DocumentFile {
    private File mFile;

    RawDocumentFile(DocumentFile $r1, File $r2) throws  {
        super($r1);
        this.mFile = $r2;
    }

    public DocumentFile createFile(String $r1, String $r4) throws  {
        $r1 = MimeTypeMap.getSingleton().getExtensionFromMimeType($r1);
        if ($r1 != null) {
            $r4 = $r4 + FileUploadSession.SEPARATOR + $r1;
        }
        File $r3 = new File(this.mFile, $r4);
        try {
            $r3.createNewFile();
            return new RawDocumentFile(this, $r3);
        } catch (IOException $r2) {
            Log.w("DocumentFile", "Failed to createFile: " + $r2);
            return null;
        }
    }

    public DocumentFile createDirectory(String $r1) throws  {
        File $r2 = new File(this.mFile, $r1);
        if ($r2.isDirectory() || $r2.mkdir()) {
            return new RawDocumentFile(this, $r2);
        }
        return null;
    }

    public Uri getUri() throws  {
        return Uri.fromFile(this.mFile);
    }

    public String getName() throws  {
        return this.mFile.getName();
    }

    public String getType() throws  {
        return this.mFile.isDirectory() ? null : getTypeForName(this.mFile.getName());
    }

    public boolean isDirectory() throws  {
        return this.mFile.isDirectory();
    }

    public boolean isFile() throws  {
        return this.mFile.isFile();
    }

    public long lastModified() throws  {
        return this.mFile.lastModified();
    }

    public long length() throws  {
        return this.mFile.length();
    }

    public boolean canRead() throws  {
        return this.mFile.canRead();
    }

    public boolean canWrite() throws  {
        return this.mFile.canWrite();
    }

    public boolean delete() throws  {
        deleteContents(this.mFile);
        return this.mFile.delete();
    }

    public boolean exists() throws  {
        return this.mFile.exists();
    }

    public DocumentFile[] listFiles() throws  {
        ArrayList $r2 = new ArrayList();
        File[] $r3 = this.mFile.listFiles();
        if ($r3 != null) {
            for (File $r1 : $r3) {
                $r2.add(new RawDocumentFile(this, $r1));
            }
        }
        return (DocumentFile[]) $r2.toArray(new DocumentFile[$r2.size()]);
    }

    public boolean renameTo(String $r1) throws  {
        File $r2 = new File(this.mFile.getParentFile(), $r1);
        if (!this.mFile.renameTo($r2)) {
            return false;
        }
        this.mFile = $r2;
        return true;
    }

    private static String getTypeForName(String $r0) throws  {
        int $i0 = $r0.lastIndexOf(46);
        if ($i0 >= 0) {
            $r0 = MimeTypeMap.getSingleton().getMimeTypeFromExtension($r0.substring($i0 + 1).toLowerCase());
            if ($r0 != null) {
                return $r0;
            }
        }
        return "application/octet-stream";
    }

    private static boolean deleteContents(File $r0) throws  {
        File[] $r1 = $r0.listFiles();
        boolean $z0 = true;
        if ($r1 == null) {
            return true;
        }
        for (File $r02 : $r1) {
            if ($r02.isDirectory()) {
                $z0 &= deleteContents($r02);
            }
            if (!$r02.delete()) {
                Log.w("DocumentFile", "Failed to delete " + $r02);
                $z0 = false;
            }
        }
        return $z0;
    }
}
