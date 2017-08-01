package com.spotify.protocol.mappers.jackson;

import android.annotation.SuppressLint;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.spotify.protocol.mappers.JsonArray;
import com.spotify.protocol.mappers.JsonMapper;
import com.spotify.protocol.mappers.JsonMappingException;
import com.spotify.protocol.mappers.JsonObject;
import dalvik.annotation.Signature;
import java.io.IOException;

@SuppressLint({"ConstructingObjectMapper", "ConfiguringObjectMapper"})
public class JacksonMapper implements JsonMapper {
    private final ObjectMapper mObjectMapper;

    private class JacksonJsonArray implements JsonArray {
        private final JsonNode mJsonNode;

        JacksonJsonArray(String $r2) throws IOException {
            this.mJsonNode = JacksonMapper.this.mObjectMapper.readTree($r2);
        }

        public int getIntAt(int $i0) throws  {
            JsonNode $r1 = this.mJsonNode.get($i0);
            if ($r1 != null) {
                return $r1.asInt();
            }
            return 0;
        }

        public String getStringAt(int $i0) throws  {
            JsonNode $r1 = this.mJsonNode.get($i0);
            if ($r1 != null) {
                return $r1.asText();
            }
            return null;
        }

        public JsonObject getObjectAt(int $i0) throws  {
            return new JacksonJsonObject(this.mJsonNode.get($i0));
        }
    }

    private class JacksonJsonObject implements JsonObject {
        private final JsonNode mJsonNode;

        JacksonJsonObject(JsonNode $r2) throws  {
            this.mJsonNode = $r2;
        }

        public <T> T getAs(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT;>;)TT;"}) Class<T> $r1) throws JsonMappingException {
            try {
                return JacksonMapper.this.mObjectMapper.convertValue(this.mJsonNode, $r1);
            } catch (IllegalArgumentException $r2) {
                throw new JsonMappingException($r2);
            }
        }

        public String toJson() throws JsonMappingException {
            try {
                return JacksonMapper.this.mObjectMapper.writeValueAsString(this.mJsonNode);
            } catch (JsonProcessingException $r1) {
                throw new JsonMappingException((Throwable) $r1);
            }
        }
    }

    public static JacksonMapper create() throws  {
        return new JacksonMapper(new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false));
    }

    private JacksonMapper(ObjectMapper $r1) throws  {
        this.mObjectMapper = $r1;
    }

    public JsonArray toJsonArray(String $r1) throws JsonMappingException {
        try {
            return new JacksonJsonArray($r1);
        } catch (IOException $r2) {
            throw new JsonMappingException($r2);
        }
    }

    public String toJson(Object $r1) throws JsonMappingException {
        try {
            return this.mObjectMapper.writeValueAsString($r1);
        } catch (JsonProcessingException $r2) {
            throw new JsonMappingException((Throwable) $r2);
        }
    }
}
