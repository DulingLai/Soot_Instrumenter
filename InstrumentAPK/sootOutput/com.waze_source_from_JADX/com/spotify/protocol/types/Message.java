package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Message implements Item {
    @SerializedName("message")
    @JsonProperty("message")
    public final String message;

    public Message(String $r1) throws  {
        this.message = $r1;
    }

    private Message() throws  {
        this(null);
    }

    public boolean equals(Object $r1) throws  {
        if (this == $r1) {
            return true;
        }
        if ($r1 == null || getClass() != $r1.getClass()) {
            return false;
        }
        Message $r4 = (Message) $r1;
        if (this.message != null) {
            return this.message.equals($r4.message);
        }
        return $r4.message == null;
    }

    public int hashCode() throws  {
        return this.message != null ? this.message.hashCode() : 0;
    }

    public String toString() throws  {
        return "Message{message='" + this.message + '\'' + '}';
    }
}
