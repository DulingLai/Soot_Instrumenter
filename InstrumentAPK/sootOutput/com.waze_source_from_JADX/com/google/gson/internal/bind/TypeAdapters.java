package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters {
    public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN = new C10492().nullSafe();
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY = newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
    public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER = new C10471().nullSafe();
    public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY = new C10513().nullSafe();
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY = newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY = newFactory(AtomicInteger.class, ATOMIC_INTEGER);
    public static final TypeAdapter<BigDecimal> BIG_DECIMAL = new TypeAdapter<BigDecimal>() {
        public BigDecimal read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return new BigDecimal($r1.nextString());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }

        public void write(JsonWriter $r1, BigDecimal $r2) throws IOException {
            $r1.value((Number) $r2);
        }
    };
    public static final TypeAdapter<BigInteger> BIG_INTEGER = new TypeAdapter<BigInteger>() {
        public BigInteger read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return new BigInteger($r1.nextString());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }

        public void write(JsonWriter $r1, BigInteger $r2) throws IOException {
            $r1.value((Number) $r2);
        }
    };
    public static final TypeAdapter<BitSet> BIT_SET = new C10546();
    public static final TypeAdapterFactory BIT_SET_FACTORY = newFactory(BitSet.class, BIT_SET);
    public static final TypeAdapter<Boolean> BOOLEAN = new C10557();
    public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING = new C10568();
    public static final TypeAdapterFactory BOOLEAN_FACTORY = newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
    public static final TypeAdapter<Number> BYTE = new C10579();
    public static final TypeAdapterFactory BYTE_FACTORY = newFactory(Byte.TYPE, Byte.class, BYTE);
    public static final TypeAdapter<Calendar> CALENDAR = new TypeAdapter<Calendar>() {
        private static final String DAY_OF_MONTH = "dayOfMonth";
        private static final String HOUR_OF_DAY = "hourOfDay";
        private static final String MINUTE = "minute";
        private static final String MONTH = "month";
        private static final String SECOND = "second";
        private static final String YEAR = "year";

        public Calendar read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            $r1.beginObject();
            int $i0 = 0;
            int $i1 = 0;
            int $i2 = 0;
            int $i3 = 0;
            int $i4 = 0;
            int $i5 = 0;
            while ($r1.peek() != JsonToken.END_OBJECT) {
                String $r5 = $r1.nextName();
                int $i6 = $r1.nextInt();
                if (YEAR.equals($r5)) {
                    $i0 = $i6;
                } else if (MONTH.equals($r5)) {
                    $i1 = $i6;
                } else if (DAY_OF_MONTH.equals($r5)) {
                    $i2 = $i6;
                } else if (HOUR_OF_DAY.equals($r5)) {
                    $i3 = $i6;
                } else if (MINUTE.equals($r5)) {
                    $i4 = $i6;
                } else if (SECOND.equals($r5)) {
                    $i5 = $i6;
                }
            }
            $r1.endObject();
            return new GregorianCalendar($i0, $i1, $i2, $i3, $i4, $i5);
        }

        public void write(JsonWriter $r1, Calendar $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            $r1.beginObject();
            $r1.name(YEAR);
            $r1.value((long) $r2.get(1));
            $r1.name(MONTH);
            $r1.value((long) $r2.get(2));
            $r1.name(DAY_OF_MONTH);
            $r1.value((long) $r2.get(5));
            $r1.name(HOUR_OF_DAY);
            $r1.value((long) $r2.get(11));
            $r1.name(MINUTE);
            $r1.value((long) $r2.get(12));
            $r1.name(SECOND);
            $r1.value((long) $r2.get(13));
            $r1.endObject();
        }
    };
    public static final TypeAdapterFactory CALENDAR_FACTORY = newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
    public static final TypeAdapter<Character> CHARACTER = new TypeAdapter<Character>() {
        public Character read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            String $r4 = $r1.nextString();
            if ($r4.length() == 1) {
                return Character.valueOf($r4.charAt(0));
            }
            throw new JsonSyntaxException("Expecting character, got: " + $r4);
        }

        public void write(JsonWriter $r1, Character $r2) throws IOException {
            $r1.value($r2 == null ? null : String.valueOf($r2));
        }
    };
    public static final TypeAdapterFactory CHARACTER_FACTORY = newFactory(Character.TYPE, Character.class, CHARACTER);
    public static final TypeAdapter<Class> CLASS = new C10535();
    public static final TypeAdapterFactory CLASS_FACTORY = newFactory(Class.class, CLASS);
    public static final TypeAdapter<Currency> CURRENCY = new C10524().nullSafe();
    public static final TypeAdapterFactory CURRENCY_FACTORY = newFactory(Currency.class, CURRENCY);
    public static final TypeAdapter<Number> DOUBLE = new TypeAdapter<Number>() {
        public Number read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return Double.valueOf($r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            $r1.value($r2);
        }
    };
    public static final TypeAdapterFactory ENUM_FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson gson, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
            Class $r3 = $r2.getRawType();
            Class $r4 = $r3;
            if (!Enum.class.isAssignableFrom($r3) || $r3 == Enum.class) {
                return null;
            }
            if (!$r3.isEnum()) {
                $r4 = $r3.getSuperclass();
            }
            return new EnumTypeAdapter($r4);
        }
    };
    public static final TypeAdapter<Number> FLOAT = new TypeAdapter<Number>() {
        public Number read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return Float.valueOf((float) $r1.nextDouble());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            $r1.value($r2);
        }
    };
    public static final TypeAdapter<InetAddress> INET_ADDRESS = new TypeAdapter<InetAddress>() {
        public InetAddress read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return InetAddress.getByName($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, InetAddress $r2) throws IOException {
            $r1.value($r2 == null ? null : $r2.getHostAddress());
        }
    };
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY = newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
    public static final TypeAdapter<Number> INTEGER = new TypeAdapter<Number>() {
        public Number read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Integer.valueOf($r1.nextInt());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            $r1.value($r2);
        }
    };
    public static final TypeAdapterFactory INTEGER_FACTORY = newFactory(Integer.TYPE, Integer.class, INTEGER);
    public static final TypeAdapter<JsonElement> JSON_ELEMENT = new TypeAdapter<JsonElement>() {
        public JsonElement read(JsonReader $r1) throws IOException {
            switch ($r1.peek()) {
                case NUMBER:
                    return new JsonPrimitive(new LazilyParsedNumber($r1.nextString()));
                case BOOLEAN:
                    return new JsonPrimitive(Boolean.valueOf($r1.nextBoolean()));
                case STRING:
                    return new JsonPrimitive($r1.nextString());
                case NULL:
                    $r1.nextNull();
                    return JsonNull.INSTANCE;
                case BEGIN_ARRAY:
                    JsonElement $r6 = r15;
                    JsonElement r15 = new JsonArray();
                    $r1.beginArray();
                    while ($r1.hasNext()) {
                        ((JsonArray) $r6).add(read($r1));
                    }
                    $r1.endArray();
                    return $r6;
                case BEGIN_OBJECT:
                    JsonObject $r2 = r0;
                    JsonObject jsonObject = new JsonObject();
                    $r1.beginObject();
                    while ($r1.hasNext()) {
                        $r2.add($r1.nextName(), read($r1));
                    }
                    $r1.endObject();
                    return $r2;
                default:
                    throw new IllegalArgumentException();
            }
        }

        public void write(JsonWriter $r1, JsonElement $r2) throws IOException {
            if ($r2 == null || $r2.isJsonNull()) {
                $r1.nullValue();
            } else if ($r2.isJsonPrimitive()) {
                JsonPrimitive $r3 = $r2.getAsJsonPrimitive();
                if ($r3.isNumber()) {
                    $r1.value($r3.getAsNumber());
                } else if ($r3.isBoolean()) {
                    $r1.value($r3.getAsBoolean());
                } else {
                    $r1.value($r3.getAsString());
                }
            } else if ($r2.isJsonArray()) {
                $r1.beginArray();
                $r7 = $r2.getAsJsonArray().iterator();
                while ($r7.hasNext()) {
                    write($r1, (JsonElement) $r7.next());
                }
                $r1.endArray();
            } else if ($r2.isJsonObject()) {
                $r1.beginObject();
                for (Entry $r11 : $r2.getAsJsonObject().entrySet()) {
                    $r1.name((String) $r11.getKey());
                    write($r1, (JsonElement) $r11.getValue());
                }
                $r1.endObject();
            } else {
                throw new IllegalArgumentException("Couldn't write " + $r2.getClass());
            }
        }
    };
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY = newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
    public static final TypeAdapter<Locale> LOCALE = new TypeAdapter<Locale>() {
        public Locale read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            StringTokenizer $r2 = new StringTokenizer($r1.nextString(), "_");
            String $r6 = null;
            String $r7 = null;
            String $r8 = null;
            if ($r2.hasMoreElements()) {
                $r6 = $r2.nextToken();
            }
            if ($r2.hasMoreElements()) {
                $r7 = $r2.nextToken();
            }
            if ($r2.hasMoreElements()) {
                $r8 = $r2.nextToken();
            }
            if ($r7 == null && $r8 == null) {
                return new Locale($r6);
            }
            if ($r8 == null) {
                return new Locale($r6, $r7);
            }
            return new Locale($r6, $r7, $r8);
        }

        public void write(JsonWriter $r1, Locale $r2) throws IOException {
            $r1.value($r2 == null ? null : $r2.toString());
        }
    };
    public static final TypeAdapterFactory LOCALE_FACTORY = newFactory(Locale.class, LOCALE);
    public static final TypeAdapter<Number> LONG = new TypeAdapter<Number>() {
        public Number read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Long.valueOf($r1.nextLong());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            $r1.value($r2);
        }
    };
    public static final TypeAdapter<Number> NUMBER = new TypeAdapter<Number>() {
        public Number read(JsonReader $r1) throws IOException {
            JsonToken $r2 = $r1.peek();
            switch ($r2) {
                case NUMBER:
                    return new LazilyParsedNumber($r1.nextString());
                case BOOLEAN:
                case STRING:
                    break;
                case NULL:
                    $r1.nextNull();
                    return null;
                default:
                    break;
            }
            throw new JsonSyntaxException("Expecting number, got: " + $r2);
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            $r1.value($r2);
        }
    };
    public static final TypeAdapterFactory NUMBER_FACTORY = newFactory(Number.class, NUMBER);
    public static final TypeAdapter<Number> SHORT = new TypeAdapter<Number>() {
        public Number read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Short.valueOf((short) $r1.nextInt());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            $r1.value($r2);
        }
    };
    public static final TypeAdapterFactory SHORT_FACTORY = newFactory(Short.TYPE, Short.class, SHORT);
    public static final TypeAdapter<String> STRING = new TypeAdapter<String>() {
        public String read(JsonReader $r1) throws IOException {
            JsonToken $r2 = $r1.peek();
            if ($r2 == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            } else if ($r2 == JsonToken.BOOLEAN) {
                return Boolean.toString($r1.nextBoolean());
            } else {
                return $r1.nextString();
            }
        }

        public void write(JsonWriter $r1, String $r2) throws IOException {
            $r1.value($r2);
        }
    };
    public static final TypeAdapter<StringBuffer> STRING_BUFFER = new TypeAdapter<StringBuffer>() {
        public StringBuffer read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return new StringBuffer($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, StringBuffer $r2) throws IOException {
            $r1.value($r2 == null ? null : $r2.toString());
        }
    };
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY = newFactory(StringBuffer.class, STRING_BUFFER);
    public static final TypeAdapter<StringBuilder> STRING_BUILDER = new TypeAdapter<StringBuilder>() {
        public StringBuilder read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return new StringBuilder($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, StringBuilder $r2) throws IOException {
            $r1.value($r2 == null ? null : $r2.toString());
        }
    };
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY = newFactory(StringBuilder.class, STRING_BUILDER);
    public static final TypeAdapterFactory STRING_FACTORY = newFactory(String.class, STRING);
    public static final TypeAdapterFactory TIMESTAMP_FACTORY = new TypeAdapterFactory() {
        public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson $r1, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
            if ($r2.getRawType() != Timestamp.class) {
                return null;
            }
            final TypeAdapter $r5 = $r1.getAdapter(Date.class);
            return new TypeAdapter<Timestamp>() {
                public Timestamp read(JsonReader $r1) throws IOException {
                    Date $r4 = (Date) $r5.read($r1);
                    return $r4 != null ? new Timestamp($r4.getTime()) : null;
                }

                public void write(JsonWriter $r1, Timestamp $r2) throws IOException {
                    $r5.write($r1, $r2);
                }
            };
        }
    };
    public static final TypeAdapter<URI> URI = new TypeAdapter<URI>() {
        public URI read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                String $r6 = $r1.nextString();
                if ("null".equals($r6)) {
                    return null;
                }
                return new URI($r6);
            } catch (Throwable $r2) {
                throw new JsonIOException($r2);
            }
        }

        public void write(JsonWriter $r1, URI $r2) throws IOException {
            $r1.value($r2 == null ? null : $r2.toASCIIString());
        }
    };
    public static final TypeAdapterFactory URI_FACTORY = newFactory(URI.class, URI);
    public static final TypeAdapter<URL> URL = new TypeAdapter<URL>() {
        public URL read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            String $r5 = $r1.nextString();
            return !"null".equals($r5) ? new URL($r5) : null;
        }

        public void write(JsonWriter $r1, URL $r2) throws IOException {
            $r1.value($r2 == null ? null : $r2.toExternalForm());
        }
    };
    public static final TypeAdapterFactory URL_FACTORY = newFactory(URL.class, URL);
    public static final TypeAdapter<UUID> UUID = new TypeAdapter<UUID>() {
        public UUID read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return UUID.fromString($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, UUID $r2) throws IOException {
            $r1.value($r2 == null ? null : $r2.toString());
        }
    };
    public static final TypeAdapterFactory UUID_FACTORY = newFactory(UUID.class, UUID);

    static class C10471 extends TypeAdapter<AtomicInteger> {
        C10471() throws  {
        }

        public AtomicInteger read(JsonReader $r1) throws IOException {
            try {
                return new AtomicInteger($r1.nextInt());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }

        public void write(JsonWriter $r1, AtomicInteger $r2) throws IOException {
            $r1.value((long) $r2.get());
        }
    }

    static class C10492 extends TypeAdapter<AtomicBoolean> {
        C10492() throws  {
        }

        public AtomicBoolean read(JsonReader $r1) throws IOException {
            return new AtomicBoolean($r1.nextBoolean());
        }

        public void write(JsonWriter $r1, AtomicBoolean $r2) throws IOException {
            $r1.value($r2.get());
        }
    }

    static class C10513 extends TypeAdapter<AtomicIntegerArray> {
        C10513() throws  {
        }

        public AtomicIntegerArray read(JsonReader $r1) throws IOException {
            ArrayList $r4 = new ArrayList();
            $r1.beginArray();
            while ($r1.hasNext()) {
                try {
                    $r4.add(Integer.valueOf($r1.nextInt()));
                } catch (Throwable $r3) {
                    throw new JsonSyntaxException($r3);
                }
            }
            $r1.endArray();
            int $i0 = $r4.size();
            AtomicIntegerArray $r2 = new AtomicIntegerArray($i0);
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r2.set($i1, ((Integer) $r4.get($i1)).intValue());
            }
            return $r2;
        }

        public void write(JsonWriter $r1, AtomicIntegerArray $r2) throws IOException {
            $r1.beginArray();
            int $i1 = $r2.length();
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                $r1.value((long) $r2.get($i0));
            }
            $r1.endArray();
        }
    }

    static class C10524 extends TypeAdapter<Currency> {
        C10524() throws  {
        }

        public Currency read(JsonReader $r1) throws IOException {
            return Currency.getInstance($r1.nextString());
        }

        public void write(JsonWriter $r1, Currency $r2) throws IOException {
            $r1.value($r2.getCurrencyCode());
        }
    }

    static class C10535 extends TypeAdapter<Class> {
        C10535() throws  {
        }

        public void write(JsonWriter $r1, Class $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + $r2.getName() + ". Forgot to register a type adapter?");
        }

        public Class read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
        }
    }

    static class C10546 extends TypeAdapter<BitSet> {
        C10546() throws  {
        }

        public BitSet read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            BitSet $r5 = new BitSet();
            $r1.beginArray();
            int $i0 = 0;
            JsonToken $r3 = $r1.peek();
            while ($r3 != JsonToken.END_ARRAY) {
                boolean $z0;
                switch ($r3) {
                    case NUMBER:
                        if ($r1.nextInt() == 0) {
                            $z0 = false;
                            break;
                        }
                        $z0 = true;
                        break;
                    case BOOLEAN:
                        $z0 = $r1.nextBoolean();
                        break;
                    case STRING:
                        String $r9 = $r1.nextString();
                        try {
                            $z0 = Integer.parseInt($r9) != 0;
                            break;
                        } catch (NumberFormatException e) {
                            throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + $r9);
                        }
                    default:
                        throw new JsonSyntaxException("Invalid bitset value type: " + $r3);
                }
                if ($z0) {
                    $r5.set($i0);
                }
                $i0++;
                $r3 = $r1.peek();
            }
            $r1.endArray();
            return $r5;
        }

        public void write(JsonWriter $r1, BitSet $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
                return;
            }
            $r1.beginArray();
            for (int $i0 = 0; $i0 < $r2.length(); $i0++) {
                $r1.value((long) ($r2.get($i0)));
            }
            $r1.endArray();
        }
    }

    static class C10557 extends TypeAdapter<Boolean> {
        C10557() throws  {
        }

        public Boolean read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            } else if ($r1.peek() == JsonToken.STRING) {
                return Boolean.valueOf(Boolean.parseBoolean($r1.nextString()));
            } else {
                return Boolean.valueOf($r1.nextBoolean());
            }
        }

        public void write(JsonWriter $r1, Boolean $r2) throws IOException {
            if ($r2 == null) {
                $r1.nullValue();
            } else {
                $r1.value($r2.booleanValue());
            }
        }
    }

    static class C10568 extends TypeAdapter<Boolean> {
        C10568() throws  {
        }

        public Boolean read(JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return Boolean.valueOf($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public void write(JsonWriter $r1, Boolean $r2) throws IOException {
            $r1.value($r2 == null ? "null" : $r2.toString());
        }
    }

    static class C10579 extends TypeAdapter<Number> {
        C10579() throws  {
        }

        public Number read(JsonReader $r1) throws IOException {
            if ($r1.peek() == JsonToken.NULL) {
                $r1.nextNull();
                return null;
            }
            try {
                return Byte.valueOf((byte) $r1.nextInt());
            } catch (Throwable $r2) {
                throw new JsonSyntaxException($r2);
            }
        }

        public void write(JsonWriter $r1, Number $r2) throws IOException {
            $r1.value($r2);
        }
    }

    private static final class EnumTypeAdapter<T extends Enum<T>> extends TypeAdapter<T> {
        private final Map<T, String> constantToName;
        private final Map<String, T> nameToConstant;

        public EnumTypeAdapter(@dalvik.annotation.Signature({"(", "Ljava/lang/Class", "<TT;>;)V"}) java.lang.Class<T> r25) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0061 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r24 = this;
            r0 = r24;
            r0.<init>();
            r2 = new java.util.HashMap;
            r2.<init>();
            r0 = r24;
            r0.nameToConstant = r2;
            r2 = new java.util.HashMap;
            r2.<init>();
            r0 = r24;
            r0.constantToName = r2;
            r0 = r25;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r3 = r0.getEnumConstants();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r5 = r3;
            r5 = (java.lang.Enum[]) r5;
            r4 = r5;
            r6 = r4.length;
            r7 = 0;
        L_0x0023:
            if (r7 >= r6) goto L_0x00a4;
        L_0x0025:
            r8 = r4[r7];	 Catch:{ NoSuchFieldException -> 0x0076 }
            r9 = r8.name();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r10 = r9;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r25;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r11 = r0.getField(r9);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r13 = com.google.gson.annotations.SerializedName.class;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r12 = r11.getAnnotation(r13);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r15 = r12;
            r15 = (com.google.gson.annotations.SerializedName) r15;
            r14 = r15;
            if (r14 == 0) goto L_0x0061;	 Catch:{ NoSuchFieldException -> 0x0076 }
        L_0x003e:
            r10 = r14.value();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r16 = r14.alternate();	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r16;
            r0 = r0.length;
            r17 = r0;
            r18 = 0;
        L_0x004d:
            r0 = r18;
            r1 = r17;
            if (r0 >= r1) goto L_0x0061;
        L_0x0053:
            r9 = r16[r18];
            r0 = r24;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r0.nameToConstant;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r19 = r0;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0.put(r9, r8);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r18 = r18 + 1;
            goto L_0x004d;
        L_0x0061:
            r0 = r24;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r0.nameToConstant;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r19 = r0;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0.put(r10, r8);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r24;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0 = r0.constantToName;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r19 = r0;	 Catch:{ NoSuchFieldException -> 0x0076 }
            r0.put(r8, r10);	 Catch:{ NoSuchFieldException -> 0x0076 }
            r7 = r7 + 1;
            goto L_0x0023;
        L_0x0076:
            r20 = move-exception;
            r21 = new java.lang.AssertionError;
            r22 = new java.lang.StringBuilder;
            r0 = r22;
            r0.<init>();
            r23 = "Missing field in ";
            r0 = r22;
            r1 = r23;
            r22 = r0.append(r1);
            r0 = r25;
            r10 = r0.getName();
            r0 = r22;
            r22 = r0.append(r10);
            r0 = r22;
            r10 = r0.toString();
            r0 = r21;
            r1 = r20;
            r0.<init>(r10, r1);
            throw r21;
        L_0x00a4:
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.gson.internal.bind.TypeAdapters.EnumTypeAdapter.<init>(java.lang.Class):void");
        }

        public T read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT;"}) JsonReader $r1) throws IOException {
            if ($r1.peek() != JsonToken.NULL) {
                return (Enum) this.nameToConstant.get($r1.nextString());
            }
            $r1.nextNull();
            return null;
        }

        public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT;)V"}) T $r2) throws IOException {
            $r1.value($r2 == null ? null : (String) this.constantToName.get($r2));
        }
    }

    private TypeAdapters() throws  {
        throw new UnsupportedOperationException();
    }

    public static <TT> TypeAdapterFactory newFactory(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/reflect/TypeToken", "<TTT;>;", "Lcom/google/gson/TypeAdapter", "<TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final TypeToken<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/reflect/TypeToken", "<TTT;>;", "Lcom/google/gson/TypeAdapter", "<TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final TypeAdapter<TT> $r1) throws  {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson gson, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
                return $r2.equals($r0) ? $r1 : null;
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/gson/TypeAdapter", "<TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final Class<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/gson/TypeAdapter", "<TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final TypeAdapter<TT> $r1) throws  {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson gson, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
                return $r2.getRawType() == $r0 ? $r1 : null;
            }

            public String toString() throws  {
                return "Factory[type=" + $r0.getName() + ",adapter=" + $r1 + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/gson/TypeAdapter", "<-TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final Class<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/gson/TypeAdapter", "<-TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final Class<TT> $r1, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<TTT;>;", "Lcom/google/gson/TypeAdapter", "<-TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final TypeAdapter<? super TT> $r2) throws  {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson gson, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
                Class $r3 = $r2.getRawType();
                return ($r3 == $r0 || $r3 == $r1) ? $r2 : null;
            }

            public String toString() throws  {
                return "Factory[type=" + $r1.getName() + "+" + $r0.getName() + ",adapter=" + $r2 + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(@Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<+TTT;>;", "Lcom/google/gson/TypeAdapter", "<-TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final Class<TT> $r0, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<+TTT;>;", "Lcom/google/gson/TypeAdapter", "<-TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final Class<? extends TT> $r1, @Signature({"<TT:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TTT;>;", "Ljava/lang/Class", "<+TTT;>;", "Lcom/google/gson/TypeAdapter", "<-TTT;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final TypeAdapter<? super TT> $r2) throws  {
        return new TypeAdapterFactory() {
            public <T> TypeAdapter<T> create(@Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) Gson gson, @Signature({"<T:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT;>;)", "Lcom/google/gson/TypeAdapter", "<TT;>;"}) TypeToken<T> $r2) throws  {
                Class $r3 = $r2.getRawType();
                return ($r3 == $r0 || $r3 == $r1) ? $r2 : null;
            }

            public String toString() throws  {
                return "Factory[type=" + $r0.getName() + "+" + $r1.getName() + ",adapter=" + $r2 + "]";
            }
        };
    }

    public static <T1> TypeAdapterFactory newTypeHierarchyFactory(@Signature({"<T1:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT1;>;", "Lcom/google/gson/TypeAdapter", "<TT1;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final Class<T1> $r0, @Signature({"<T1:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TT1;>;", "Lcom/google/gson/TypeAdapter", "<TT1;>;)", "Lcom/google/gson/TypeAdapterFactory;"}) final TypeAdapter<T1> $r1) throws  {
        return new TypeAdapterFactory() {
            public <T2> TypeAdapter<T2> create(@Signature({"<T2:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT2;>;)", "Lcom/google/gson/TypeAdapter", "<TT2;>;"}) Gson gson, @Signature({"<T2:", "Ljava/lang/Object;", ">(", "Lcom/google/gson/Gson;", "Lcom/google/gson/reflect/TypeToken", "<TT2;>;)", "Lcom/google/gson/TypeAdapter", "<TT2;>;"}) TypeToken<T2> $r2) throws  {
                final Class $r3 = $r2.getRawType();
                return !$r0.isAssignableFrom($r3) ? null : new TypeAdapter<T1>() {
                    public void write(@Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT1;)V"}) JsonWriter $r1, @Signature({"(", "Lcom/google/gson/stream/JsonWriter;", "TT1;)V"}) T1 $r2) throws IOException {
                        $r1.write($r1, $r2);
                    }

                    public T1 read(@Signature({"(", "Lcom/google/gson/stream/JsonReader;", ")TT1;"}) JsonReader $r1) throws IOException {
                        Object $r4 = $r1.read($r1);
                        if ($r4 == null || $r3.isInstance($r4)) {
                            return $r4;
                        }
                        throw new JsonSyntaxException("Expected a " + $r3.getName() + " but was " + $r4.getClass().getName());
                    }
                };
            }

            public String toString() throws  {
                return "Factory[typeHierarchy=" + $r0.getName() + ",adapter=" + $r1 + "]";
            }
        };
    }
}
