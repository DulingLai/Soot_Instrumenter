package android.support.v4.util;

import android.util.Log;
import java.io.Writer;

public class LogWriter extends Writer {
    private StringBuilder mBuilder = new StringBuilder(128);
    private final String mTag;

    public LogWriter(String $r1) throws  {
        this.mTag = $r1;
    }

    public void close() throws  {
        flushBuilder();
    }

    public void flush() throws  {
        flushBuilder();
    }

    public void write(char[] $r1, int $i0, int $i1) throws  {
        for (int $i3 = 0; $i3 < $i1; $i3++) {
            char $c2 = $r1[$i0 + $i3];
            if ($c2 == '\n') {
                flushBuilder();
            } else {
                this.mBuilder.append($c2);
            }
        }
    }

    private void flushBuilder() throws  {
        if (this.mBuilder.length() > 0) {
            Log.d(this.mTag, this.mBuilder.toString());
            this.mBuilder.delete(0, this.mBuilder.length());
        }
    }
}
