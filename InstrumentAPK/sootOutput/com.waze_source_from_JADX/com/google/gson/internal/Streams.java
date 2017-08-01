package com.google.gson.internal;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.io.Writer;

public final class Streams {

    private static final class AppendableWriter extends Writer {
        private final Appendable appendable;
        private final CurrentWrite currentWrite = new CurrentWrite();

        static class CurrentWrite implements CharSequence {
            char[] chars;

            CurrentWrite() throws  {
            }

            public int length() throws  {
                return this.chars.length;
            }

            public char charAt(int $i0) throws  {
                return this.chars[$i0];
            }

            public CharSequence subSequence(int $i0, int $i1) throws  {
                return new String(this.chars, $i0, $i1 - $i0);
            }
        }

        AppendableWriter(Appendable $r1) throws  {
            this.appendable = $r1;
        }

        public void write(char[] $r1, int $i0, int $i1) throws IOException {
            this.currentWrite.chars = $r1;
            this.appendable.append(this.currentWrite, $i0, $i0 + $i1);
        }

        public void write(int $i0) throws IOException {
            this.appendable.append((char) $i0);
        }

        public void flush() throws  {
        }

        public void close() throws  {
        }
    }

    private Streams() throws  {
        throw new UnsupportedOperationException();
    }

    public static JsonElement parse(JsonReader $r0) throws JsonParseException {
        boolean $z0 = true;
        try {
            $r0.peek();
            $z0 = false;
            return (JsonElement) TypeAdapters.JSON_ELEMENT.read($r0);
        } catch (Throwable $r4) {
            if ($z0) {
                return JsonNull.INSTANCE;
            }
            throw new JsonSyntaxException($r4);
        } catch (Throwable $r6) {
            throw new JsonSyntaxException($r6);
        } catch (Throwable $r7) {
            throw new JsonIOException($r7);
        } catch (Throwable $r9) {
            throw new JsonSyntaxException($r9);
        }
    }

    public static void write(JsonElement $r0, JsonWriter $r1) throws IOException {
        TypeAdapters.JSON_ELEMENT.write($r1, $r0);
    }

    public static Writer writerForAppendable(Appendable $r0) throws  {
        return $r0 instanceof Writer ? (Writer) $r0 : new AppendableWriter($r0);
    }
}
