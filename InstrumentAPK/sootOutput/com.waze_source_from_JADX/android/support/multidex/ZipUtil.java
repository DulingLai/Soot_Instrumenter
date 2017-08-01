package android.support.multidex;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

final class ZipUtil {
    private static final int BUFFER_SIZE = 16384;
    private static final int ENDHDR = 22;
    private static final int ENDSIG = 101010256;

    static class CentralDirectory {
        long offset;
        long size;

        CentralDirectory() throws  {
        }
    }

    ZipUtil() throws  {
    }

    static long getZipCrc(File $r0) throws IOException {
        RandomAccessFile $r1 = new RandomAccessFile($r0, "r");
        try {
            long $l0 = computeCrcOfCentralDir($r1, findCentralDirectory($r1));
            return $l0;
        } finally {
            $r1.close();
        }
    }

    static CentralDirectory findCentralDirectory(RandomAccessFile $r0) throws IOException, ZipException {
        long $l0 = $r0.length() - 22;
        if ($l0 < 0) {
            throw new ZipException("File too short to be a zip file: " + $r0.length());
        }
        long $l2 = $l0 - 65536;
        if ($l2 < 0) {
            $l2 = 0;
        }
        int $i3 = Integer.reverseBytes(ENDSIG);
        do {
            $r0.seek($l0);
            if ($r0.readInt() == $i3) {
                $r0.skipBytes(2);
                $r0.skipBytes(2);
                $r0.skipBytes(2);
                $r0.skipBytes(2);
                CentralDirectory $r1 = new CentralDirectory();
                $r1.size = ((long) Integer.reverseBytes($r0.readInt())) & 4294967295L;
                $r1.offset = ((long) Integer.reverseBytes($r0.readInt())) & 4294967295L;
                return $r1;
            }
            $l0--;
        } while ($l0 >= $l2);
        throw new ZipException("End Of Central Directory signature not found");
    }

    static long computeCrcOfCentralDir(RandomAccessFile $r0, CentralDirectory $r1) throws IOException {
        CRC32 $r3 = new CRC32();
        long $l0 = $r1.size;
        $r0.seek($r1.offset);
        byte[] $r2 = new byte[16384];
        int $i2 = $r0.read($r2, 0, (int) Math.min(16384, $l0));
        while ($i2 != -1) {
            $r3.update($r2, 0, $i2);
            $l0 -= (long) $i2;
            if ($l0 == 0) {
                break;
            }
            $i2 = $r0.read($r2, 0, (int) Math.min(16384, $l0));
        }
        return $r3.getValue();
    }
}
