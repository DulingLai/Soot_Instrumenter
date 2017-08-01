package com.abaltatech.mcp.mcs.fileupload;

import com.abaltatech.mcp.mcs.utils.FilenameUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileUploadSession {
    public static final String PARTIAL = ".partial";
    public static final String SEPARATOR = ".";
    private String m_checksum;
    private String m_dataDirectory;
    private long m_expectedSize;
    private String m_fileName;
    private long m_offset;
    private FileOutputStream m_outputFile;
    private File m_partialFilePath;

    public FileUploadSession(String $r1, String $r2, String $r3, long $l0) throws IOException {
        init($r1, $r2, $r3, 0, $l0);
    }

    private boolean init(String $r1, String $r2, String $r3, long $l0, long $l1) throws IOException {
        this.m_fileName = $r2;
        this.m_offset = $l0;
        this.m_checksum = $r3;
        this.m_expectedSize = $l1;
        this.m_dataDirectory = $r1;
        this.m_partialFilePath = new File(this.m_dataDirectory, this.m_fileName + SEPARATOR + this.m_checksum + SEPARATOR + this.m_expectedSize + PARTIAL);
        this.m_outputFile = new FileOutputStream(this.m_partialFilePath, true);
        return true;
    }

    public static FileUploadSession InitFromFile(String $r0) throws  {
        String[] $r2 = FilenameUtils.getName($r0).split("\\.");
        if ((SEPARATOR + $r2[$r2.length - 1]).compareTo(PARTIAL) != 0 || $r2.length < 3) {
            return null;
        }
        long $l1 = Long.valueOf($r2[$r2.length - 2]).longValue();
        String $r1 = $r2[$r2.length - 3];
        String $r5 = FilenameUtils.getPath($r0);
        long $l2 = new File($r0).length();
        $r0 = "";
        for (int $i0 = 0; $i0 < $r2.length - 4; $i0++) {
            $r0 = $r0 + $r2[$i0];
        }
        try {
            FileUploadSession fileUploadSession = new FileUploadSession($r5, $r0, $r1, $l1);
            try {
                fileUploadSession.setOffset($l2);
                return fileUploadSession;
            } catch (IOException e) {
                return fileUploadSession;
            }
        } catch (IOException e2) {
            return null;
        }
    }

    public void setFileSize(long $l0) throws  {
        this.m_expectedSize = $l0;
    }

    public void setChecksum(String $r1) throws  {
        this.m_checksum = $r1;
    }

    public void setOffset(long $l0) throws  {
        this.m_offset = $l0;
    }

    public void write(byte[] $r1, long $l0) throws IOException {
        if (this.m_outputFile != null) {
            this.m_outputFile.write($r1, 0, (int) $l0);
            this.m_outputFile.flush();
            this.m_offset += $l0;
        }
    }

    public boolean isValid(String $r1, long $l0, long $l1) throws  {
        return $r1.compareTo(this.m_fileName) == 0 && $l1 == this.m_offset && $l0 == this.m_expectedSize;
    }

    public boolean validateChecksum(String $r1) throws  {
        return $r1.compareTo(this.m_checksum) == 0;
    }

    public boolean isFinished() throws  {
        return this.m_offset == this.m_expectedSize;
    }

    public void complete() throws IOException {
        File $r5 = new File(this.m_dataDirectory, this.m_fileName + PARTIAL);
        FileInputStream $r4 = new FileInputStream($r5.getAbsolutePath());
        byte[] $r1 = new byte[1024];
        try {
            MessageDigest $r8 = MessageDigest.getInstance("MD5");
            int $i0;
            do {
                $i0 = $r4.read($r1);
                if ($i0 > 0) {
                    $r8.update($r1, 0, $i0);
                }
            } while ($i0 != -1);
            $r4.close();
            if (validateChecksum(new String($r8.digest()))) {
                this.m_partialFilePath.renameTo($r5);
            } else {
                this.m_partialFilePath.delete();
                throw new IOException("Invalid checksum");
            }
        } catch (NoSuchAlgorithmException e) {
            $r4.close();
            throw new IOException("Unable to calculate file MD5 checksum");
        }
    }

    public String getChecksum() throws  {
        return this.m_checksum;
    }

    public void pause() throws  {
    }

    public String getFilename() throws  {
        return this.m_fileName;
    }

    public void terminate() throws  {
        this.m_partialFilePath.delete();
        this.m_partialFilePath = null;
    }
}
