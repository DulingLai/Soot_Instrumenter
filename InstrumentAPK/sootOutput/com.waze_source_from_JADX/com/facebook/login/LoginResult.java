package com.facebook.login;

import com.facebook.AccessToken;
import dalvik.annotation.Signature;
import java.util.Set;

public class LoginResult {
    private final AccessToken accessToken;
    private final Set<String> recentlyDeniedPermissions;
    private final Set<String> recentlyGrantedPermissions;

    public LoginResult(@Signature({"(", "Lcom/facebook/AccessToken;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) AccessToken $r1, @Signature({"(", "Lcom/facebook/AccessToken;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String> $r2, @Signature({"(", "Lcom/facebook/AccessToken;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;", "Ljava/util/Set", "<", "Ljava/lang/String;", ">;)V"}) Set<String> $r3) throws  {
        this.accessToken = $r1;
        this.recentlyGrantedPermissions = $r2;
        this.recentlyDeniedPermissions = $r3;
    }

    public AccessToken getAccessToken() throws  {
        return this.accessToken;
    }

    public Set<String> getRecentlyGrantedPermissions() throws  {
        return this.recentlyGrantedPermissions;
    }

    public Set<String> getRecentlyDeniedPermissions() throws  {
        return this.recentlyDeniedPermissions;
    }
}
