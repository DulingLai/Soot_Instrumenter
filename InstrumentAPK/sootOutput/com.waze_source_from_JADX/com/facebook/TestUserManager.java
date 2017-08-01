package com.facebook;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.internal.Utility;
import dalvik.annotation.Signature;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TestUserManager {
    static final /* synthetic */ boolean $assertionsDisabled = (!TestUserManager.class.desiredAssertionStatus());
    private static final String LOG_TAG = "TestUserManager";
    private Map<String, JSONObject> appTestAccounts;
    private String testApplicationId;
    private String testApplicationSecret;

    private enum Mode {
        PRIVATE,
        SHARED
    }

    public TestUserManager(String $r1, String $r2) throws  {
        if (Utility.isNullOrEmpty($r2) || Utility.isNullOrEmpty($r1)) {
            throw new FacebookException("Must provide app ID and secret");
        }
        this.testApplicationSecret = $r1;
        this.testApplicationId = $r2;
    }

    public AccessToken getAccessTokenForPrivateUser(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/facebook/AccessToken;"}) List<String> $r1) throws  {
        return getAccessTokenForUser($r1, Mode.PRIVATE, null);
    }

    public AccessToken getAccessTokenForSharedUser(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Lcom/facebook/AccessToken;"}) List<String> $r1) throws  {
        return getAccessTokenForSharedUser($r1, null);
    }

    public AccessToken getAccessTokenForSharedUser(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) String $r2) throws  {
        return getAccessTokenForUser($r1, Mode.SHARED, $r2);
    }

    public synchronized String getTestApplicationId() throws  {
        return this.testApplicationId;
    }

    public synchronized String getTestApplicationSecret() throws  {
        return this.testApplicationSecret;
    }

    private AccessToken getAccessTokenForUser(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) List<String> $r4, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) Mode $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lcom/facebook/AccessToken;"}) String $r2) throws  {
        List $r42;
        JSONObject $r7;
        retrieveTestAccountsForAppIfNeeded();
        if (Utility.isNullOrEmpty((Collection) $r4)) {
            $r42 = Arrays.asList(new String[]{"email", "publish_actions"});
        }
        if ($r1 == Mode.PRIVATE) {
            $r7 = createTestAccount($r42, $r1, $r2);
        } else {
            $r7 = findOrCreateSharedTestAccount($r42, $r1, $r2);
        }
        return new AccessToken($r7.optString("access_token"), this.testApplicationId, $r7.optString("id"), $r42, null, AccessTokenSource.TEST_USER, null, null);
    }

    private synchronized void retrieveTestAccountsForAppIfNeeded() throws  {
        if (this.appTestAccounts == null) {
            this.appTestAccounts = new HashMap();
            GraphRequest.setDefaultBatchApplicationId(this.testApplicationId);
            Bundle $r1 = new Bundle();
            $r1.putString("access_token", getAppAccessToken());
            GraphRequest $r3 = new GraphRequest(null, "app/accounts/test-users", $r1, null);
            $r3.setBatchEntryName("testUsers");
            $r3.setBatchEntryOmitResultOnSuccess(false);
            $r1 = new Bundle();
            $r1.putString("access_token", getAppAccessToken());
            $r1.putString("ids", "{result=testUsers:$.data.*.id}");
            $r1.putString(GraphRequest.FIELDS_PARAM, "name");
            new GraphRequest(null, "", $r1, null).setBatchEntryDependsOn("testUsers");
            List $r8 = GraphRequest.executeBatchAndWait($r3, new GraphRequest(null, "", $r1, null));
            if ($r8 == null || $r8.size() != 2) {
                throw new FacebookException("Unexpected number of results from TestUsers batch query");
            }
            populateTestAccounts(((GraphResponse) $r8.get(0)).getJSONObject().optJSONArray("data"), ((GraphResponse) $r8.get(1)).getJSONObject());
        }
    }

    private synchronized void populateTestAccounts(JSONArray $r1, JSONObject $r2) throws  {
        for (int $i0 = 0; $i0 < $r1.length(); $i0++) {
            JSONObject $r4 = $r1.optJSONObject($i0);
            try {
                $r4.put("name", $r2.optJSONObject($r4.optString("id")).optString("name"));
            } catch (JSONException $r3) {
                Log.e(LOG_TAG, "Could not set name", $r3);
            }
            storeTestAccount($r4);
        }
    }

    private synchronized void storeTestAccount(JSONObject $r1) throws  {
        this.appTestAccounts.put($r1.optString("id"), $r1);
    }

    private synchronized JSONObject findTestAccountMatchingIdentifier(String $r1) throws  {
        JSONObject $r6;
        for (JSONObject $r62 : this.appTestAccounts.values()) {
            if ($r62.optString("name").contains($r1)) {
                break;
            }
        }
        $r62 = null;
        return $r62;
    }

    final String getAppAccessToken() throws  {
        return this.testApplicationId + "|" + this.testApplicationSecret;
    }

    private JSONObject findOrCreateSharedTestAccount(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lorg/json/JSONObject;"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lorg/json/JSONObject;"}) Mode $r2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lorg/json/JSONObject;"}) String $r3) throws  {
        JSONObject $r5 = findTestAccountMatchingIdentifier(getSharedTestAccountIdentifier($r1, $r3));
        return $r5 != null ? $r5 : createTestAccount($r1, $r2, $r3);
    }

    private String getSharedTestAccountIdentifier(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")", "Ljava/lang/String;"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Ljava/lang/String;", ")", "Ljava/lang/String;"}) String $r2) throws  {
        return validNameStringFromInteger((((long) getPermissionsString($r1).hashCode()) & 4294967295L) ^ ($r2 != null ? ((long) $r2.hashCode()) & 4294967295L : 0));
    }

    private String validNameStringFromInteger(long $l0) throws  {
        String $r2 = Long.toString($l0);
        StringBuilder $r1 = new StringBuilder("Perm");
        char $c2 = '\u0000';
        for (char $c4 : $r2.toCharArray()) {
            char $c42;
            if ($c42 == $c2) {
                $c42 = (char) ($c42 + 10);
            }
            $r1.append((char) (($c42 + 97) - 48));
            $c2 = $c42;
        }
        return $r1.toString();
    }

    private JSONObject createTestAccount(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lorg/json/JSONObject;"}) List<String> $r1, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lorg/json/JSONObject;"}) Mode $r2, @Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;", "Lcom/facebook/TestUserManager$Mode;", "Ljava/lang/String;", ")", "Lorg/json/JSONObject;"}) String $r3) throws  {
        Bundle $r6 = new Bundle();
        $r6.putString("installed", ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
        $r6.putString(NativeProtocol.RESULT_ARGS_PERMISSIONS, getPermissionsString($r1));
        $r6.putString("access_token", getAppAccessToken());
        if ($r2 == Mode.SHARED) {
            $r6.putString("name", String.format("Shared %s Testuser", new Object[]{getSharedTestAccountIdentifier($r1, $r3)}));
        }
        GraphResponse $r11 = new GraphRequest(null, String.format("%s/accounts/test-users", new Object[]{this.testApplicationId}), $r6, HttpMethod.POST).executeAndWait();
        FacebookRequestError $r12 = $r11.getError();
        JSONObject $r13 = $r11.getJSONObject();
        if ($r12 != null) {
            return null;
        }
        if (!$assertionsDisabled && $r13 == null) {
            throw new AssertionError();
        } else if ($r2 != Mode.SHARED) {
            return $r13;
        } else {
            try {
                $r13.put("name", $r6.getString("name"));
            } catch (Throwable $r5) {
                Log.e(LOG_TAG, "Could not set name", $r5);
            }
            storeTestAccount($r13);
            return $r13;
        }
    }

    private String getPermissionsString(@Signature({"(", "Ljava/util/List", "<", "Ljava/lang/String;", ">;)", "Ljava/lang/String;"}) List<String> $r1) throws  {
        return TextUtils.join(",", $r1);
    }
}
