package com.google.gson.stream;

import com.facebook.internal.ServerProtocol;
import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.Writer;

public class JsonWriter implements Closeable, Flushable {
    private static final String[] HTML_SAFE_REPLACEMENT_CHARS = ((String[]) REPLACEMENT_CHARS.clone());
    private static final String[] REPLACEMENT_CHARS = new String[128];
    private String deferredName;
    private boolean htmlSafe;
    private String indent;
    private boolean lenient;
    private final Writer out;
    private String separator;
    private boolean serializeNulls;
    private int[] stack = new int[32];
    private int stackSize = 0;

    static {
        for (int $i0 = 0; $i0 <= 31; $i0++) {
            REPLACEMENT_CHARS[$i0] = String.format("\\u%04x", new Object[]{Integer.valueOf($i0)});
        }
        REPLACEMENT_CHARS[34] = "\\\"";
        REPLACEMENT_CHARS[92] = "\\\\";
        REPLACEMENT_CHARS[9] = "\\t";
        REPLACEMENT_CHARS[8] = "\\b";
        REPLACEMENT_CHARS[10] = "\\n";
        REPLACEMENT_CHARS[13] = "\\r";
        REPLACEMENT_CHARS[12] = "\\f";
        HTML_SAFE_REPLACEMENT_CHARS[60] = "\\u003c";
        HTML_SAFE_REPLACEMENT_CHARS[62] = "\\u003e";
        HTML_SAFE_REPLACEMENT_CHARS[38] = "\\u0026";
        HTML_SAFE_REPLACEMENT_CHARS[61] = "\\u003d";
        HTML_SAFE_REPLACEMENT_CHARS[39] = "\\u0027";
    }

    public JsonWriter(Writer $r1) throws  {
        push(6);
        this.separator = ":";
        this.serializeNulls = true;
        if ($r1 == null) {
            throw new NullPointerException("out == null");
        }
        this.out = $r1;
    }

    public final void setIndent(String $r1) throws  {
        if ($r1.length() == 0) {
            this.indent = null;
            this.separator = ":";
            return;
        }
        this.indent = $r1;
        this.separator = ": ";
    }

    public final void setLenient(boolean $z0) throws  {
        this.lenient = $z0;
    }

    public boolean isLenient() throws  {
        return this.lenient;
    }

    public final void setHtmlSafe(boolean $z0) throws  {
        this.htmlSafe = $z0;
    }

    public final boolean isHtmlSafe() throws  {
        return this.htmlSafe;
    }

    public final void setSerializeNulls(boolean $z0) throws  {
        this.serializeNulls = $z0;
    }

    public final boolean getSerializeNulls() throws  {
        return this.serializeNulls;
    }

    public JsonWriter beginArray() throws IOException {
        writeDeferredName();
        return open(1, "[");
    }

    public JsonWriter endArray() throws IOException {
        return close(1, 2, "]");
    }

    public JsonWriter beginObject() throws IOException {
        writeDeferredName();
        return open(3, "{");
    }

    public JsonWriter endObject() throws IOException {
        return close(3, 5, "}");
    }

    private JsonWriter open(int $i0, String $r1) throws IOException {
        beforeValue();
        push($i0);
        this.out.write($r1);
        return this;
    }

    private JsonWriter close(int $i0, int $i1, String $r1) throws IOException {
        int $i2 = peek();
        if ($i2 != $i1 && $i2 != $i0) {
            throw new IllegalStateException("Nesting problem.");
        } else if (this.deferredName != null) {
            throw new IllegalStateException("Dangling name: " + this.deferredName);
        } else {
            this.stackSize--;
            if ($i2 == $i1) {
                newline();
            }
            this.out.write($r1);
            return this;
        }
    }

    private void push(int $i0) throws  {
        int[] $r1;
        if (this.stackSize == this.stack.length) {
            $r1 = new int[(this.stackSize * 2)];
            System.arraycopy(this.stack, 0, $r1, 0, this.stackSize);
            this.stack = $r1;
        }
        $r1 = this.stack;
        int $i1 = this.stackSize;
        this.stackSize = $i1 + 1;
        $r1[$i1] = $i0;
    }

    private int peek() throws  {
        if (this.stackSize != 0) {
            return this.stack[this.stackSize - 1];
        }
        throw new IllegalStateException("JsonWriter is closed.");
    }

    private void replaceTop(int $i0) throws  {
        this.stack[this.stackSize - 1] = $i0;
    }

    public JsonWriter name(String $r1) throws IOException {
        if ($r1 == null) {
            throw new NullPointerException("name == null");
        } else if (this.deferredName != null) {
            throw new IllegalStateException();
        } else if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        } else {
            this.deferredName = $r1;
            return this;
        }
    }

    private void writeDeferredName() throws IOException {
        if (this.deferredName != null) {
            beforeName();
            string(this.deferredName);
            this.deferredName = null;
        }
    }

    public JsonWriter value(String $r0) throws IOException {
        if ($r0 == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue();
        string($r0);
        return this;
    }

    public JsonWriter jsonValue(String $r0) throws IOException {
        if ($r0 == null) {
            return nullValue();
        }
        writeDeferredName();
        beforeValue();
        this.out.append($r0);
        return this;
    }

    public JsonWriter nullValue() throws IOException {
        if (this.deferredName != null) {
            if (this.serializeNulls) {
                writeDeferredName();
            } else {
                this.deferredName = null;
                return this;
            }
        }
        beforeValue();
        this.out.write("null");
        return this;
    }

    public JsonWriter value(boolean $z0) throws IOException {
        writeDeferredName();
        beforeValue();
        this.out.write($z0 ? ServerProtocol.DIALOG_RETURN_SCOPES_TRUE : "false");
        return this;
    }

    public JsonWriter value(double $d0) throws IOException {
        if (Double.isNaN($d0) || Double.isInfinite($d0)) {
            throw new IllegalArgumentException("Numeric values must be finite, but was " + $d0);
        }
        writeDeferredName();
        beforeValue();
        this.out.append(Double.toString($d0));
        return this;
    }

    public JsonWriter value(long $l0) throws IOException {
        writeDeferredName();
        beforeValue();
        this.out.write(Long.toString($l0));
        return this;
    }

    public JsonWriter value(Number $r0) throws IOException {
        if ($r0 == null) {
            return nullValue();
        }
        writeDeferredName();
        String $r2 = $r0.toString();
        if (this.lenient || !($r2.equals("-Infinity") || $r2.equals("Infinity") || $r2.equals("NaN"))) {
            beforeValue();
            this.out.append($r2);
            return this;
        }
        throw new IllegalArgumentException("Numeric values must be finite, but was " + $r0);
    }

    public void flush() throws IOException {
        if (this.stackSize == 0) {
            throw new IllegalStateException("JsonWriter is closed.");
        }
        this.out.flush();
    }

    public void close() throws IOException {
        this.out.close();
        int $i0 = this.stackSize;
        if ($i0 > 1 || ($i0 == 1 && this.stack[$i0 - 1] != 7)) {
            throw new IOException("Incomplete document");
        }
        this.stackSize = 0;
    }

    private void string(String $r1) throws IOException {
        String[] $r2 = this.htmlSafe ? HTML_SAFE_REPLACEMENT_CHARS : REPLACEMENT_CHARS;
        this.out.write("\"");
        int $i0 = 0;
        int $i1 = $r1.length();
        for (int $i2 = 0; $i2 < $i1; $i2++) {
            char $c3 = $r1.charAt($i2);
            String $r4;
            if ($c3 < '') {
                $r4 = $r2[$c3];
                if ($r4 == null) {
                }
                if ($i0 < $i2) {
                    this.out.write($r1, $i0, $i2 - $i0);
                }
                this.out.write($r4);
                $i0 = $i2 + 1;
            } else {
                if ($c3 == ' ') {
                    $r4 = "\\u2028";
                } else if ($c3 == ' ') {
                    $r4 = "\\u2029";
                }
                if ($i0 < $i2) {
                    this.out.write($r1, $i0, $i2 - $i0);
                }
                this.out.write($r4);
                $i0 = $i2 + 1;
            }
        }
        if ($i0 < $i1) {
            this.out.write($r1, $i0, $i1 - $i0);
        }
        this.out.write("\"");
    }

    private void newline() throws IOException {
        if (this.indent != null) {
            this.out.write("\n");
            int $i0 = this.stackSize;
            for (int $i1 = 1; $i1 < $i0; $i1++) {
                this.out.write(this.indent);
            }
        }
    }

    private void beforeName() throws IOException {
        int $i0 = peek();
        if ($i0 == 5) {
            this.out.write(44);
        } else if ($i0 != 3) {
            throw new IllegalStateException("Nesting problem.");
        }
        newline();
        replaceTop(4);
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private void beforeValue() throws java.io.IOException {
        /*
        r7 = this;
        r0 = r7.peek();
        switch(r0) {
            case 1: goto L_0x0021;
            case 2: goto L_0x0029;
            case 3: goto L_0x0008;
            case 4: goto L_0x0034;
            case 5: goto L_0x0008;
            case 6: goto L_0x001c;
            case 7: goto L_0x0010;
            default: goto L_0x0007;
        };
    L_0x0007:
        goto L_0x0008;
    L_0x0008:
        r1 = new java.lang.IllegalStateException;
        r2 = "Nesting problem.";
        r1.<init>(r2);
        throw r1;
    L_0x0010:
        r3 = r7.lenient;
        if (r3 != 0) goto L_0x001c;
    L_0x0014:
        r1 = new java.lang.IllegalStateException;
        r2 = "JSON must have only one top-level value.";
        r1.<init>(r2);
        throw r1;
    L_0x001c:
        r4 = 7;
        r7.replaceTop(r4);
        return;
    L_0x0021:
        r4 = 2;
        r7.replaceTop(r4);
        r7.newline();
        return;
    L_0x0029:
        r5 = r7.out;
        r4 = 44;
        r5.append(r4);
        r7.newline();
        return;
    L_0x0034:
        r5 = r7.out;
        r6 = r7.separator;
        r5.append(r6);
        r4 = 5;
        r7.replaceTop(r4);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.stream.JsonWriter.beforeValue():void");
    }
}
