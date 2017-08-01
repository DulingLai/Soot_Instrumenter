package com.spotify.protocol.mappers.gson;

import android.util.Base64;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.spotify.protocol.mappers.JsonArray;
import com.spotify.protocol.mappers.JsonMapper;
import com.spotify.protocol.mappers.JsonMappingException;
import com.spotify.protocol.mappers.JsonObject;
import com.spotify.protocol.types.ImageUri;
import dalvik.annotation.Signature;
import java.lang.reflect.Type;

public class GsonMapper implements JsonMapper {
    private final Gson mGson;

    private static class ByteArrayToBase64TypeAdapter implements JsonSerializer<byte[]>, JsonDeserializer<byte[]> {
        private ByteArrayToBase64TypeAdapter() throws  {
        }

        public byte[] deserialize(JsonElement $r1, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Base64.decode($r1.getAsJsonPrimitive().getAsString(), 2);
        }

        public JsonElement serialize(byte[] $r1, Type typeOfSrc, JsonSerializationContext context) throws  {
            return new JsonPrimitive(Base64.encodeToString($r1, 2));
        }
    }

    private static class GsonJsonArray implements JsonArray {
        private final Gson mGson;
        private final com.google.gson.JsonArray mJsonArray;

        GsonJsonArray(Gson $r1, JsonElement $r2) throws  {
            this.mGson = $r1;
            this.mJsonArray = $r2.getAsJsonArray();
        }

        public int getIntAt(int $i0) throws  {
            try {
                return this.mJsonArray.get($i0).getAsInt();
            } catch (RuntimeException e) {
                return 0;
            }
        }

        public String getStringAt(int $i0) throws  {
            try {
                return this.mJsonArray.get($i0).getAsString();
            } catch (RuntimeException e) {
                return null;
            }
        }

        public JsonObject getObjectAt(int $i0) throws  {
            try {
                return new GsonJsonObject(this.mGson, this.mJsonArray.get($i0));
            } catch (RuntimeException e) {
                return null;
            }
        }
    }

    private static class GsonJsonObject implements JsonObject {
        private final Gson mGson;
        private final JsonElement mJsonElement;

        GsonJsonObject(Gson $r1, JsonElement $r2) throws  {
            this.mGson = $r1;
            this.mJsonElement = $r2;
        }

        public <T> T getAs(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws JsonMappingException {
            try {
                return this.mGson.fromJson(this.mJsonElement, (Class) $r1);
            } catch (RuntimeException $r2) {
                throw new JsonMappingException($r2);
            }
        }

        public String toJson() throws JsonMappingException {
            return this.mGson.toJson(this.mJsonElement);
        }
    }

    private static class ImageUriGson implements JsonDeserializer<ImageUri>, JsonSerializer<ImageUri> {
        private ImageUriGson() throws  {
        }

        public ImageUri deserialize(JsonElement $r1, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new ImageUri($r1.getAsString());
        }

        public JsonElement serialize(ImageUri $r1, Type typeOfSrc, JsonSerializationContext $r3) throws  {
            return $r3.serialize($r1.raw);
        }
    }

    public static GsonMapper create() throws  {
        return new GsonMapper(new GsonBuilder().registerTypeAdapter(ImageUri.class, new ImageUriGson()).registerTypeAdapter(byte[].class, new ByteArrayToBase64TypeAdapter()).create());
    }

    private GsonMapper(Gson $r1) throws  {
        this.mGson = $r1;
    }

    public JsonArray toJsonArray(String $r1) throws JsonMappingException {
        try {
            return new GsonJsonArray(this.mGson, (JsonElement) this.mGson.fromJson($r1, JsonElement.class));
        } catch (RuntimeException $r2) {
            throw new JsonMappingException($r2);
        }
    }

    public String toJson(Object $r1) throws JsonMappingException {
        return this.mGson.toJson($r1);
    }
}
