package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.EOFException;
import java.io.Reader;
import java.io.StringReader;
import java.util.Iterator;
import java.util.NoSuchElementException;

public final class JsonStreamParser implements Iterator<JsonElement> {
    private final Object lock;
    private final JsonReader parser;

    public JsonStreamParser(String $r1) throws  {
        this(new StringReader($r1));
    }

    public JsonStreamParser(Reader $r1) throws  {
        this.parser = new JsonReader($r1);
        this.parser.setLenient(true);
        this.lock = new Object();
    }

    public JsonElement next() throws JsonParseException {
        if (hasNext()) {
            try {
                return Streams.parse(this.parser);
            } catch (StackOverflowError $r4) {
                throw new JsonParseException("Failed parsing JSON source to Json", $r4);
            } catch (OutOfMemoryError $r6) {
                throw new JsonParseException("Failed parsing JSON source to Json", $r6);
            } catch (JsonParseException e) {
                RuntimeException $r7 = e;
                if (((JsonParseException) $r7).getCause() instanceof EOFException) {
                    $r7 = $r1;
                    RuntimeException $r1 = new NoSuchElementException();
                }
                throw $r7;
            }
        }
        throw new NoSuchElementException();
    }

    public boolean hasNext() throws  {
        boolean $z0;
        synchronized (this.lock) {
            try {
                $z0 = this.parser.peek() != JsonToken.END_DOCUMENT;
            } catch (Throwable $r5) {
                throw new JsonSyntaxException($r5);
            } catch (Throwable $r8) {
                throw new JsonIOException($r8);
            }
        }
        return $z0;
    }

    public void remove() throws  {
        throw new UnsupportedOperationException();
    }
}
