package com.google.gson;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

final class DefaultDateTypeAdapter implements JsonSerializer<Date>, JsonDeserializer<Date> {
    private final DateFormat enUsFormat;
    private final DateFormat localFormat;

    DefaultDateTypeAdapter() throws  {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    DefaultDateTypeAdapter(String $r1) throws  {
        this(new SimpleDateFormat($r1, Locale.US), new SimpleDateFormat($r1));
    }

    DefaultDateTypeAdapter(int $i0) throws  {
        this(DateFormat.getDateInstance($i0, Locale.US), DateFormat.getDateInstance($i0));
    }

    public DefaultDateTypeAdapter(int $i0, int $i1) throws  {
        this(DateFormat.getDateTimeInstance($i0, $i1, Locale.US), DateFormat.getDateTimeInstance($i0, $i1));
    }

    DefaultDateTypeAdapter(DateFormat $r1, DateFormat $r2) throws  {
        this.enUsFormat = $r1;
        this.localFormat = $r2;
    }

    public JsonElement serialize(Date $r1, Type typeOfSrc, JsonSerializationContext context) throws  {
        JsonPrimitive $r7;
        synchronized (this.localFormat) {
            $r7 = new JsonPrimitive(this.enUsFormat.format($r1));
        }
        return $r7;
    }

    public Date deserialize(JsonElement $r1, Type $r2, JsonDeserializationContext context) throws JsonParseException {
        if ($r1 instanceof JsonPrimitive) {
            Date $r5 = deserializeToDate($r1);
            if ($r2 == Date.class) {
                return $r5;
            }
            if ($r2 == Timestamp.class) {
                return new Timestamp($r5.getTime());
            }
            if ($r2 == java.sql.Date.class) {
                return new java.sql.Date($r5.getTime());
            }
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + $r2);
        }
        throw new JsonParseException("The date should be a string value");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private java.util.Date deserializeToDate(com.google.gson.JsonElement r12) throws  {
        /*
        r11 = this;
        r0 = r11.localFormat;
        monitor-enter(r0);
        r1 = r11.localFormat;
        r2 = r12.getAsString();	 Catch:{ ParseException -> 0x000f }
        r3 = r1.parse(r2);	 Catch:{ ParseException -> 0x000f }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        return r3;
    L_0x000f:
        r4 = move-exception;
        r1 = r11.enUsFormat;
        r2 = r12.getAsString();	 Catch:{ ParseException -> 0x001f }
        r3 = r1.parse(r2);	 Catch:{ ParseException -> 0x001f }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        return r3;
    L_0x001c:
        r5 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        throw r5;
    L_0x001f:
        r6 = move-exception;
        r2 = r12.getAsString();	 Catch:{ ParseException -> 0x0030 }
        r7 = new java.text.ParsePosition;	 Catch:{ ParseException -> 0x0030 }
        r8 = 0;
        r7.<init>(r8);	 Catch:{ ParseException -> 0x0030 }
        r3 = com.google.gson.internal.bind.util.ISO8601Utils.parse(r2, r7);	 Catch:{ ParseException -> 0x0030 }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x001c }
        return r3;
    L_0x0030:
        r9 = move-exception;
        r10 = new com.google.gson.JsonSyntaxException;	 Catch:{ Throwable -> 0x001c }
        r2 = r12.getAsString();	 Catch:{ Throwable -> 0x001c }
        r10.<init>(r2, r9);	 Catch:{ Throwable -> 0x001c }
        throw r10;	 Catch:{ Throwable -> 0x001c }
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.gson.DefaultDateTypeAdapter.deserializeToDate(com.google.gson.JsonElement):java.util.Date");
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        $r1.append(DefaultDateTypeAdapter.class.getSimpleName());
        $r1.append('(').append(this.localFormat.getClass().getSimpleName()).append(')');
        return $r1.toString();
    }
}
