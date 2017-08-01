package com.spotify.protocol.mappers.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.spotify.protocol.types.ImageUri;
import java.io.IOException;

public class ImageUriJson {

    public static class Deserializer extends StdDeserializer<ImageUri> {
        private static final long serialVersionUID = 1;

        public Deserializer() throws  {
            super(ImageUri.class);
        }

        public ImageUri deserialize(JsonParser $r1, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            return new ImageUri($r1.getValueAsString());
        }
    }

    public static class Serializer extends StdSerializer<ImageUri> {
        private static final long serialVersionUID = 1;

        protected Serializer() throws  {
            super(ImageUri.class);
        }

        public void serialize(ImageUri $r1, JsonGenerator $r2, SerializerProvider provider) throws IOException {
            $r2.writeString($r1.raw);
        }
    }
}
