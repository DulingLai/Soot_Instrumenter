package com.spotify.protocol.types;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;
import dalvik.annotation.Signature;
import java.util.Arrays;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HelloDetails implements Item {
    @SerializedName("authid")
    @JsonProperty("authid")
    public final String authid;
    @SerializedName("authmethods")
    @JsonProperty("authmethods")
    public final String[] authmethods;
    @SerializedName("extras")
    @JsonProperty("extras")
    public final Map<String, String> extras;
    @SerializedName("info")
    @JsonProperty("info")
    public final Info info;
    @SerializedName("roles")
    @JsonProperty("roles")
    public final Roles roles;

    private HelloDetails() throws  {
        this(null, null, null, null, null);
    }

    public HelloDetails(@Signature({"(", "Lcom/spotify/protocol/types/Roles;", "Lcom/spotify/protocol/types/Info;", "[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Roles $r1, @Signature({"(", "Lcom/spotify/protocol/types/Roles;", "Lcom/spotify/protocol/types/Info;", "[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Info $r2, @Signature({"(", "Lcom/spotify/protocol/types/Roles;", "Lcom/spotify/protocol/types/Info;", "[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String[] $r3, @Signature({"(", "Lcom/spotify/protocol/types/Roles;", "Lcom/spotify/protocol/types/Info;", "[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) String $r4, @Signature({"(", "Lcom/spotify/protocol/types/Roles;", "Lcom/spotify/protocol/types/Info;", "[", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;)V"}) Map<String, String> $r5) throws  {
        this.roles = $r1;
        this.info = $r2;
        this.authmethods = $r3;
        this.authid = $r4;
        this.extras = $r5;
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
        HelloDetails $r4 = (HelloDetails) $r1;
        if (this.roles != null) {
            if (!this.roles.equals($r4.roles)) {
                return false;
            }
        } else if ($r4.roles != null) {
            return false;
        }
        if (this.info != null) {
            if (!this.info.equals($r4.info)) {
                return false;
            }
        } else if ($r4.info != null) {
            return false;
        }
        if (!Arrays.equals(this.authmethods, $r4.authmethods)) {
            return false;
        }
        if (this.authid != null) {
            String $r11 = this.authid;
            String $r12 = $r4.authid;
            if (!$r11.equals($r12)) {
                return false;
            }
        } else if ($r4.authid != null) {
            return false;
        }
        if (this.extras != null) {
            $z0 = this.extras.equals($r4.extras);
        } else if ($r4.extras != null) {
            $z0 = false;
        }
        return $z0;
    }

    public int hashCode() throws  {
        int $i2;
        int $i0 = 0;
        int $i1 = (this.roles != null ? this.roles.hashCode() : 0) * 31;
        if (this.info != null) {
            $i2 = this.info.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ((($i1 + $i2) * 31) + Arrays.hashCode(this.authmethods)) * 31;
        if (this.authid != null) {
            $i2 = this.authid.hashCode();
        } else {
            $i2 = 0;
        }
        $i1 = ($i1 + $i2) * 31;
        if (this.extras != null) {
            $i0 = this.extras.hashCode();
        }
        return $i1 + $i0;
    }

    public String toString() throws  {
        return "HelloDetails{roles=" + this.roles + ", info=" + this.info + ", authmethods=" + Arrays.toString(this.authmethods) + ", authid='" + this.authid + '\'' + ", extras=" + this.extras + '}';
    }
}
