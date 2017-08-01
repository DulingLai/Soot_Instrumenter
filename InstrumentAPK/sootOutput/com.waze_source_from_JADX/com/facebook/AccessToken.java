package com.facebook;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.internal.Utility;
import com.facebook.internal.Utility.GraphMeRequestWithCacheCallback;
import com.facebook.internal.Validate;
import com.waze.strings.DisplayStrings;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class AccessToken implements Parcelable {
    public static final String ACCESS_TOKEN_KEY = "access_token";
    private static final String APPLICATION_ID_KEY = "application_id";
    public static final Creator<AccessToken> CREATOR = new C04742();
    private static final int CURRENT_JSON_FORMAT = 1;
    private static final String DECLINED_PERMISSIONS_KEY = "declined_permissions";
    private static final AccessTokenSource DEFAULT_ACCESS_TOKEN_SOURCE = AccessTokenSource.FACEBOOK_APPLICATION_WEB;
    private static final Date DEFAULT_EXPIRATION_TIME = MAX_DATE;
    private static final Date DEFAULT_LAST_REFRESH_TIME = new Date();
    private static final String EXPIRES_AT_KEY = "expires_at";
    public static final String EXPIRES_IN_KEY = "expires_in";
    private static final String LAST_REFRESH_KEY = "last_refresh";
    private static final Date MAX_DATE = new Date(Long.MAX_VALUE);
    private static final String PERMISSIONS_KEY = "permissions";
    private static final String SOURCE_KEY = "source";
    private static final String TOKEN_KEY = "token";
    public static final String USER_ID_KEY = "user_id";
    private static final String VERSION_KEY = "version";
    private final String applicationId;
    private final Set<String> declinedPermissions;
    private final Date expires;
    private final Date lastRefresh;
    private final Set<String> permissions;
    private final AccessTokenSource source;
    private final String token;
    private final String userId;

    static class C04742 implements Creator {
        C04742() throws  {
        }

        public AccessToken createFromParcel(Parcel $r1) throws  {
            return new AccessToken($r1);
        }

        public AccessToken[] newArray(int $i0) throws  {
            return new AccessToken[$i0];
        }
    }

    public interface AccessTokenCreationCallback {
        void onError(FacebookException facebookException) throws ;

        void onSuccess(AccessToken accessToken) throws ;
    }

    public int describeContents() throws  {
        return 0;
    }

    public AccessToken(@Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) String $r3, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) Collection<String> $r4, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) Collection<String> $r5, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) AccessTokenSource $r6, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) Date $r7, @Nullable @Signature({"(", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/lang/String;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Ljava/util/Collection", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/util/Date;", ")V"}) Date $r8) throws  {
        Validate.notNullOrEmpty($r1, "accessToken");
        Validate.notNullOrEmpty($r2, "applicationId");
        Validate.notNullOrEmpty($r3, "userId");
        if ($r7 == null) {
            $r7 = DEFAULT_EXPIRATION_TIME;
        }
        this.expires = $r7;
        this.permissions = Collections.unmodifiableSet($r4 != null ? new HashSet($r4) : new HashSet());
        this.declinedPermissions = Collections.unmodifiableSet($r5 != null ? new HashSet($r5) : new HashSet());
        this.token = $r1;
        if ($r6 == null) {
            $r6 = DEFAULT_ACCESS_TOKEN_SOURCE;
        }
        this.source = $r6;
        if ($r8 == null) {
            $r8 = DEFAULT_LAST_REFRESH_TIME;
        }
        this.lastRefresh = $r8;
        this.applicationId = $r2;
        this.userId = $r3;
    }

    public static AccessToken getCurrentAccessToken() throws  {
        return AccessTokenManager.getInstance().getCurrentAccessToken();
    }

    public static void setCurrentAccessToken(AccessToken $r0) throws  {
        AccessTokenManager.getInstance().setCurrentAccessToken($r0);
    }

    public static void refreshCurrentAccessTokenAsync() throws  {
        AccessTokenManager.getInstance().refreshCurrentAccessToken();
    }

    public String getToken() throws  {
        return this.token;
    }

    public Date getExpires() throws  {
        return this.expires;
    }

    public Set<String> getPermissions() throws  {
        return this.permissions;
    }

    public Set<String> getDeclinedPermissions() throws  {
        return this.declinedPermissions;
    }

    public AccessTokenSource getSource() throws  {
        return this.source;
    }

    public Date getLastRefresh() throws  {
        return this.lastRefresh;
    }

    public String getApplicationId() throws  {
        return this.applicationId;
    }

    public String getUserId() throws  {
        return this.userId;
    }

    public static void createFromNativeLinkingIntent(Intent $r0, final String $r1, final AccessTokenCreationCallback $r2) throws  {
        Validate.notNull($r0, "intent");
        if ($r0.getExtras() == null) {
            $r2.onError(new FacebookException("No extras found on intent"));
            return;
        }
        final Bundle $r3 = new Bundle($r0.getExtras());
        String $r7 = $r3.getString("access_token");
        if ($r7 == null || $r7.isEmpty()) {
            $r2.onError(new FacebookException("No access token found on intent"));
            return;
        }
        String $r8 = $r3.getString("user_id");
        if ($r8 == null || $r8.isEmpty()) {
            Utility.getGraphMeRequestWithCacheAsync($r7, new GraphMeRequestWithCacheCallback() {
                public void onSuccess(JSONObject $r1) throws  {
                    try {
                        $r3.putString("user_id", $r1.getString("id"));
                        $r2.onSuccess(AccessToken.createFromBundle(null, $r3, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), $r1));
                    } catch (JSONException e) {
                        $r2.onError(new FacebookException("Unable to generate access token due to missing user id"));
                    }
                }

                public void onFailure(FacebookException $r1) throws  {
                    $r2.onError($r1);
                }
            });
        } else {
            $r2.onSuccess(createFromBundle(null, $r3, AccessTokenSource.FACEBOOK_APPLICATION_WEB, new Date(), $r1));
        }
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder();
        $r1.append("{AccessToken");
        $r1.append(" token:").append(tokenToString());
        appendPermissions($r1);
        $r1.append("}");
        return $r1.toString();
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r13) throws  {
        /*
        r12 = this;
        if (r12 != r13) goto L_0x0004;
    L_0x0002:
        r0 = 1;
        return r0;
    L_0x0004:
        r1 = r13 instanceof com.facebook.AccessToken;
        if (r1 != 0) goto L_0x000a;
    L_0x0008:
        r0 = 0;
        return r0;
    L_0x000a:
        r3 = r13;
        r3 = (com.facebook.AccessToken) r3;
        r2 = r3;
        r4 = r12.expires;
        r5 = r2.expires;
        r1 = r4.equals(r5);
        if (r1 == 0) goto L_0x0058;
    L_0x0018:
        r6 = r12.permissions;
        r7 = r2.permissions;
        r1 = r6.equals(r7);
        if (r1 == 0) goto L_0x0058;
    L_0x0022:
        r6 = r12.declinedPermissions;
        r7 = r2.declinedPermissions;
        r1 = r6.equals(r7);
        if (r1 == 0) goto L_0x0058;
    L_0x002c:
        r8 = r12.token;
        r9 = r2.token;
        r1 = r8.equals(r9);
        if (r1 == 0) goto L_0x0058;
    L_0x0036:
        r10 = r12.source;
        r11 = r2.source;
        if (r10 != r11) goto L_0x0058;
    L_0x003c:
        r4 = r12.lastRefresh;
        r5 = r2.lastRefresh;
        r1 = r4.equals(r5);
        if (r1 == 0) goto L_0x0058;
    L_0x0046:
        r8 = r12.applicationId;
        if (r8 != 0) goto L_0x005a;
    L_0x004a:
        r8 = r2.applicationId;
        if (r8 != 0) goto L_0x0058;
    L_0x004e:
        r8 = r12.userId;
        r9 = r2.userId;
        r1 = r8.equals(r9);
        if (r1 != 0) goto L_0x0065;
    L_0x0058:
        r0 = 0;
        return r0;
    L_0x005a:
        r8 = r12.applicationId;
        r9 = r2.applicationId;
        r1 = r8.equals(r9);
        if (r1 == 0) goto L_0x0058;
    L_0x0064:
        goto L_0x004e;
    L_0x0065:
        r0 = 1;
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.AccessToken.equals(java.lang.Object):boolean");
    }

    public int hashCode() throws  {
        return ((((((((((((((this.expires.hashCode() + DisplayStrings.DS_P2_1F_HOURS_AGO_UC) * 31) + this.permissions.hashCode()) * 31) + this.declinedPermissions.hashCode()) * 31) + this.token.hashCode()) * 31) + this.source.hashCode()) * 31) + this.lastRefresh.hashCode()) * 31) + (this.applicationId == null ? 0 : this.applicationId.hashCode())) * 31) + this.userId.hashCode();
    }

    @SuppressLint({"FieldGetter"})
    static AccessToken createFromRefresh(AccessToken $r0, Bundle $r1) throws  {
        if ($r0.source == AccessTokenSource.FACEBOOK_APPLICATION_WEB || $r0.source == AccessTokenSource.FACEBOOK_APPLICATION_NATIVE || $r0.source == AccessTokenSource.FACEBOOK_APPLICATION_SERVICE) {
            Bundle bundle = $r1;
            Date $r8 = Utility.getBundleLongAsDate(bundle, "expires_in", new Date(0));
            String $r7 = $r1.getString("access_token");
            if (Utility.isNullOrEmpty($r7)) {
                return null;
            }
            return new AccessToken($r7, $r0.applicationId, $r0.getUserId(), $r0.getPermissions(), $r0.getDeclinedPermissions(), $r0.source, $r8, new Date());
        }
        throw new FacebookException("Invalid token source: " + $r0.source);
    }

    static AccessToken createFromLegacyCache(Bundle $r0) throws  {
        List $r2 = getPermissionsFromBundle($r0, LegacyTokenHelper.PERMISSIONS_KEY);
        List $r3 = getPermissionsFromBundle($r0, LegacyTokenHelper.DECLINED_PERMISSIONS_KEY);
        String $r4 = LegacyTokenHelper.getApplicationId($r0);
        String $r5 = $r4;
        if (Utility.isNullOrEmpty($r4)) {
            $r5 = FacebookSdk.getApplicationId();
        }
        $r4 = LegacyTokenHelper.getToken($r0);
        try {
            return new AccessToken($r4, $r5, Utility.awaitGetGraphMeRequestWithCache($r4).getString("id"), $r2, $r3, LegacyTokenHelper.getSource($r0), LegacyTokenHelper.getDate($r0, LegacyTokenHelper.EXPIRATION_DATE_KEY), LegacyTokenHelper.getDate($r0, LegacyTokenHelper.LAST_REFRESH_DATE_KEY));
        } catch (JSONException e) {
            return null;
        }
    }

    static List<String> getPermissionsFromBundle(@Signature({"(", "Landroid/os/Bundle;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) Bundle $r0, @Signature({"(", "Landroid/os/Bundle;", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) String $r1) throws  {
        ArrayList $r3 = $r0.getStringArrayList($r1);
        if ($r3 == null) {
            return Collections.emptyList();
        }
        return Collections.unmodifiableList(new ArrayList($r3));
    }

    public boolean isExpired() throws  {
        return new Date().after(this.expires);
    }

    JSONObject toJSONObject() throws JSONException {
        JSONObject $r2 = new JSONObject();
        $r2.put("version", 1);
        $r2.put(TOKEN_KEY, this.token);
        $r2.put(EXPIRES_AT_KEY, this.expires.getTime());
        $r2.put("permissions", new JSONArray(this.permissions));
        $r2.put(DECLINED_PERMISSIONS_KEY, new JSONArray(this.declinedPermissions));
        $r2.put(LAST_REFRESH_KEY, this.lastRefresh.getTime());
        $r2.put("source", this.source.name());
        $r2.put(APPLICATION_ID_KEY, this.applicationId);
        $r2.put("user_id", this.userId);
        return $r2;
    }

    static AccessToken createFromJSONObject(JSONObject $r0) throws JSONException {
        if ($r0.getInt("version") > 1) {
            throw new FacebookException("Unknown AccessToken serialization format.");
        }
        String $r4 = $r0.getString(TOKEN_KEY);
        Date $r1 = new Date($r0.getLong(EXPIRES_AT_KEY));
        JSONArray $r5 = $r0.getJSONArray("permissions");
        JSONArray $r6 = $r0.getJSONArray(DECLINED_PERMISSIONS_KEY);
        Date date = new Date($r0.getLong(LAST_REFRESH_KEY));
        AccessTokenSource $r8 = AccessTokenSource.valueOf($r0.getString("source"));
        return new AccessToken($r4, $r0.getString(APPLICATION_ID_KEY), $r0.getString("user_id"), Utility.jsonArrayToStringList($r5), Utility.jsonArrayToStringList($r6), $r8, $r1, date);
    }

    private static AccessToken createFromBundle(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) List<String> $r0, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) Bundle $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) AccessTokenSource $r2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) Date $r3, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Landroid/os/Bundle;", "Lcom/facebook/AccessTokenSource;", "Ljava/util/Date;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) String $r4) throws  {
        String $r5 = $r1.getString("access_token");
        Date $r6 = Utility.getBundleLongAsDate($r1, "expires_in", $r3);
        return (Utility.isNullOrEmpty($r5) || $r6 == null) ? null : new AccessToken($r5, $r4, $r1.getString("user_id"), $r0, null, $r2, $r6, new Date());
    }

    private String tokenToString() throws  {
        if (this.token == null) {
            return "null";
        }
        return FacebookSdk.isLoggingBehaviorEnabled(LoggingBehavior.INCLUDE_ACCESS_TOKENS) ? this.token : "ACCESS_TOKEN_REMOVED";
    }

    private void appendPermissions(StringBuilder $r1) throws  {
        $r1.append(" permissions:");
        if (this.permissions == null) {
            $r1.append("null");
            return;
        }
        $r1.append("[");
        $r1.append(TextUtils.join(", ", this.permissions));
        $r1.append("]");
    }

    AccessToken(Parcel $r1) throws  {
        this.expires = new Date($r1.readLong());
        ArrayList $r2 = new ArrayList();
        $r1.readStringList($r2);
        this.permissions = Collections.unmodifiableSet(new HashSet($r2));
        $r2.clear();
        $r1.readStringList($r2);
        this.declinedPermissions = Collections.unmodifiableSet(new HashSet($r2));
        this.token = $r1.readString();
        this.source = AccessTokenSource.valueOf($r1.readString());
        this.lastRefresh = new Date($r1.readLong());
        this.applicationId = $r1.readString();
        this.userId = $r1.readString();
    }

    public void writeToParcel(Parcel $r1, int flags) throws  {
        $r1.writeLong(this.expires.getTime());
        $r1.writeStringList(new ArrayList(this.permissions));
        $r1.writeStringList(new ArrayList(this.declinedPermissions));
        $r1.writeString(this.token);
        $r1.writeString(this.source.name());
        $r1.writeLong(this.lastRefresh.getTime());
        $r1.writeString(this.applicationId);
        $r1.writeString(this.userId);
    }
}
