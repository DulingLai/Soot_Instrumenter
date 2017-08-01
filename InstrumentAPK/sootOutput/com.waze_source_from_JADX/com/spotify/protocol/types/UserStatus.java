package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatus implements Item {
    public static final int STATUS_CODE_NOT_LOGGED_IN = 1;
    public static final int STATUS_CODE_OK = 0;
    @SerializedName("code")
    @JsonProperty("code")
    public final int code;
    @SerializedName("long_text")
    @JsonProperty("long_text")
    public final String longMessage;
    @SerializedName("short_text")
    @JsonProperty("short_text")
    public final String shortMessage;

    private UserStatus() throws  {
        this(1, null, null);
    }

    public UserStatus(int $i0, String $r1, String $r2) throws  {
        this.code = $i0;
        this.shortMessage = $r1;
        this.longMessage = $r2;
    }

    @JsonIgnore
    public boolean isLoggedIn() throws  {
        return this.code == 0;
    }

    public boolean equals(Object $r1) throws  {
        boolean $z0 = true;
        if (this == $r1) {
            return true;
        }
        if ($r1 == null) {
            return false;
        }
        if (getClass() != $r1.getClass()) {
            return false;
        }
        UserStatus $r4 = (UserStatus) $r1;
        if (this.code != $r4.code) {
            return false;
        }
        if (this.shortMessage != null) {
            if (!this.shortMessage.equals($r4.shortMessage)) {
                return false;
            }
        } else if ($r4.shortMessage != null) {
            return false;
        }
        if (this.longMessage != null) {
            $z0 = this.longMessage.equals($r4.longMessage);
        } else if ($r4.longMessage != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i2;
        int $i0 = 0;
        int $i1 = this.code * 31;
        if (this.shortMessage != null) {
            $i2 = this.shortMessage.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.longMessage != null) {
            $i0 = this.longMessage.hashCode();
        }
        return $i1 + $i0;
    }

    public String toString() throws  {
        return "UserStatus{code=" + this.code + ", shortMessage='" + this.shortMessage + '\'' + ", longMessage='" + this.longMessage + '\'' + '}';
    }
}
