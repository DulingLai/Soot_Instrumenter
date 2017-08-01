package com.google.gson;

import com.google.gson.internal.Streams;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser {
    public JsonElement parse(String $r1) throws JsonSyntaxException {
        return parse(new StringReader($r1));
    }

    public JsonElement parse(Reader $r1) throws JsonIOException, JsonSyntaxException {
        try {
            JsonReader $r2 = new JsonReader($r1);
            JsonElement $r3 = parse($r2);
            if ($r3.isJsonNull() || $r2.peek() == JsonToken.END_DOCUMENT) {
                return $r3;
            }
            throw new JsonSyntaxException("Did not consume the entire document.");
        } catch (Throwable $r7) {
            throw new JsonSyntaxException($r7);
        } catch (Throwable $r8) {
            throw new JsonIOException($r8);
        } catch (Throwable $r10) {
            throw new JsonSyntaxException($r10);
        }
    }

    public JsonElement parse(JsonReader $r1) throws JsonIOException, JsonSyntaxException {
        boolean $z0 = $r1.isLenient();
        $r1.setLenient(true);
        try {
            JsonElement $r2 = Streams.parse($r1);
            $r1.setLenient($z0);
            return $r2;
        } catch (StackOverflowError $r3) {
            throw new JsonParseException("Failed parsing JSON source: " + $r1 + " to Json", $r3);
        } catch (OutOfMemoryError $r8) {
            throw new JsonParseException("Failed parsing JSON source: " + $r1 + " to Json", $r8);
        } catch (Throwable th) {
            $r1.setLenient($z0);
        }
    }
}
