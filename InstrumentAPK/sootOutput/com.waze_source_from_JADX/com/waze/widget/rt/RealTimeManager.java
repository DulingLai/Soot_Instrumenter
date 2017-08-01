package com.waze.widget.rt;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.util.Log;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.config.WazePreferences;
import com.waze.config.WazeUserPreferences;
import com.waze.messages.QuestionData;
import com.waze.utils.StatsData;
import com.waze.widget.WazeAppWidgetLog;
import com.waze.widget.WazeAppWidgetPreferences;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class RealTimeManager {
    public static final String OFFLINE_STATS_SHARED_PREF = "com.waze.offline_stat";
    private static final int PROTOCL_VERSION = 146;
    public static final String STATS_PREF = "stats";
    private static RealTimeManager mInstance = null;
    final String UpdateBankCommand = "{ \"userId\": \"obfuscated_id\", \"bankAccount\": { \"s7eAccountId\": { \"plaintextValue\": \"plain_text\", \"secureDataType\": 3 }, \"customerFirstName\": \"first_name\", \"customerLastName\": \"last_name\", \"customerMiddleName\": \"middle_name\", \"bankCode\": \"bank_code\", \"branchId\": \"branch_id\" } }";
    private AuthenticateSuccessfulResponse mAuthenticationRsp;
    private boolean mIsSecuredConnection;
    private String mPassword;
    private String mSecuredServerUrl;
    private String mServerUrl;
    private String mUserName;

    public static RealTimeManager getInstance() {
        if (mInstance != null) {
            return mInstance;
        }
        mInstance = new RealTimeManager();
        return mInstance;
    }

    private void loadRealTimeParams() {
        this.mUserName = WazeUserPreferences.getProperty("Realtime.Name");
        this.mPassword = WazeUserPreferences.getProperty("Realtime.PasswordEnc");
        this.mServerUrl = WazeAppWidgetPreferences.ServerUrl();
        this.mSecuredServerUrl = WazeAppWidgetPreferences.SecuredServerUrl();
        this.mIsSecuredConnection = WazeAppWidgetPreferences.isWebServiceSecuredEnabled();
    }

    private RealTimeManager() {
        loadRealTimeParams();
    }

    public String getUserName() {
        return this.mUserName;
    }

    public String getPassword() {
        return this.mPassword;
    }

    public Boolean hasUserName() {
        boolean z = (getUserName() == null || getPassword() == null) ? false : true;
        return Boolean.valueOf(z);
    }

    public String getSessionId() {
        if (this.mAuthenticationRsp == null) {
            return null;
        }
        return this.mAuthenticationRsp.getSessionId();
    }

    public String getCookie() {
        if (this.mAuthenticationRsp == null) {
            return null;
        }
        return this.mAuthenticationRsp.getCookie();
    }

    public boolean ClientInfo(Context context) {
        String sClientInfo = QuestionData.ReadKeyData("client_info", context);
        if (sClientInfo == null || sClientInfo.isEmpty()) {
            WazeAppWidgetLog.m47w("Client info failed");
            return false;
        }
        try {
            String url;
            WazeAppWidgetLog.m45d("Sending client info request");
            HttpClient httpclient = new DefaultHttpClient();
            if (this.mIsSecuredConnection) {
                url = this.mSecuredServerUrl;
            } else {
                url = this.mServerUrl;
            }
            HttpPost httppost = new HttpPost(url + "/distrib/static");
            httppost.addHeader("Content-Type", "binary/octet-stream");
            httppost.setEntity(new StringEntity(sClientInfo));
            HttpResponse rp = httpclient.execute(httppost);
            if (rp.getStatusLine().getStatusCode() == 200) {
                return true;
            }
            WazeAppWidgetLog.m46e("client info failed [error =" + rp.getStatusLine().getStatusCode() + "]");
            return false;
        } catch (IOException e) {
            WazeAppWidgetLog.m46e("client info error [error=" + e.getMessage() + "]");
            return false;
        }
    }

    public String GetURIForStat(Context mContext, String EventName, String[] EventParams, boolean bIsAds) {
        String sClientInfo = QuestionData.ReadKeyData("client_info", mContext);
        if (sClientInfo == null || sClientInfo.isEmpty() || this.mUserName == null || this.mPassword == null) {
            return null;
        }
        String decPassword = NativeManager.decryptPasswordStatic(this.mPassword, mContext);
        if (decPassword == null) {
            WazeAppWidgetLog.m47w("login aborted  / password extraction failed [" + this.mUserName + "]");
            return null;
        }
        return (((sClientInfo + "\n") + "Authenticate," + getProtocol() + "," + this.mUserName + "," + decPassword + "\n") + new StatsData(mContext, EventName, EventParams).buildCmd(bIsAds) + "\n") + "Logout\n";
    }

    public String GetURIForCommand(Context mContext, String command) {
        String sClientInfo = QuestionData.ReadKeyData("client_info", mContext);
        if (sClientInfo == null || sClientInfo.isEmpty() || this.mUserName == null || this.mPassword == null) {
            return null;
        }
        String decPassword = NativeManager.decryptPasswordStatic(this.mPassword, mContext);
        if (decPassword == null) {
            WazeAppWidgetLog.m47w("login aborted  / password extraction failed [" + this.mUserName + "]");
            return null;
        }
        return (((sClientInfo + "\n") + "Authenticate," + getProtocol() + "," + this.mUserName + "," + decPassword + "\n") + command + "\n") + "Logout\n";
    }

    public String GetURIForBankAccount(Context mContext, String id, String firstname, String lastname, String middleName, String branchId, String BankCode, String accountId) {
        return "{ \"userId\": \"obfuscated_id\", \"bankAccount\": { \"s7eAccountId\": { \"plaintextValue\": \"plain_text\", \"secureDataType\": 3 }, \"customerFirstName\": \"first_name\", \"customerLastName\": \"last_name\", \"customerMiddleName\": \"middle_name\", \"bankCode\": \"bank_code\", \"branchId\": \"branch_id\" } }\n".replace("obfuscated_id", URLEncoder.encode(id)).replace("first_name", URLEncoder.encode(firstname)).replace("middle_name", URLEncoder.encode(middleName)).replace("last_name", URLEncoder.encode(lastname)).replace("branch_id", branchId).replace("bank_code", BankCode).replace("plain_text", accountId);
    }

    public void authenticate() {
        if (this.mUserName == null || this.mPassword == null) {
            WazeAppWidgetLog.m47w("login aborted [" + this.mUserName + "]");
            return;
        }
        String decPassword = NativeManager.decryptPasswordStatic(this.mPassword, AppService.getAppContext());
        if (decPassword == null) {
            WazeAppWidgetLog.m47w("login aborted  / password extraction failed [" + this.mUserName + "]");
        } else if (!(this.mIsSecuredConnection && this.mSecuredServerUrl == null) && (this.mIsSecuredConnection || this.mServerUrl != null)) {
            AuthenticateRequest authReq = new AuthenticateRequest(this.mUserName, decPassword);
            try {
                String url;
                WazeAppWidgetLog.m45d("Sending Authenticate request");
                HttpClient httpclient = new DefaultHttpClient();
                if (this.mIsSecuredConnection) {
                    url = this.mSecuredServerUrl;
                } else {
                    url = this.mServerUrl;
                }
                HttpPost httppost = new HttpPost(url + "/distrib/static");
                httppost.addHeader("Content-Type", "binary/octet-stream");
                httppost.setEntity(new StringEntity(authReq.buildCmd()));
                HttpResponse rp = httpclient.execute(httppost);
                if (rp.getStatusLine().getStatusCode() == 200) {
                    this.mAuthenticationRsp = new AuthenticateSuccessfulResponse(EntityUtils.toString(rp.getEntity()));
                } else {
                    WazeAppWidgetLog.m46e("Authenticate failed [error =" + rp.getStatusLine().getStatusCode() + "]");
                }
            } catch (IOException e) {
                WazeAppWidgetLog.m46e("Authenticate error [error=" + e.getMessage() + "]");
            }
        } else {
            WazeAppWidgetLog.m47w("login aborted [mIsSecuredConnection=" + this.mIsSecuredConnection + " mSecuredServerUrl=" + this.mSecuredServerUrl + ",mServerUrl=" + this.mServerUrl + "]");
        }
    }

    public void SendStat(Context mContext, String Eventname, String[] EventParams) {
        SendStat(mContext, Eventname, EventParams, false);
    }

    public void SendStat(Context mContext, String Eventname, String[] EventParams, boolean storeIfFailed) {
        String url;
        boolean bOnUiThread = false;
        if (this.mIsSecuredConnection) {
            url = this.mSecuredServerUrl;
        } else {
            url = this.mServerUrl;
        }
        if (url == null || url.length() <= 0) {
            Log.e("OfflineStats", "server url is null");
            return;
        }
        final String sBuildCommand = getInstance().GetURIForStat(mContext, Eventname, EventParams, false);
        if (sBuildCommand == null) {
            Log.e("OfflineStats", "build command failed");
            return;
        }
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(url + "/distrib/static");
        final boolean z = storeIfFailed;
        final Context context = mContext;
        Runnable post = new Runnable() {
            public void run() {
                try {
                    String sCommand = new String(sBuildCommand.getBytes("UTF-8"), "UTF-8");
                    httppost.addHeader("Content-Type", "binary/octet-stream");
                    httppost.setEntity(new StringEntity(sCommand, "UTF-8"));
                    HttpResponse rp = httpclient.execute(httppost);
                    if (rp.getStatusLine().getStatusCode() != 200) {
                        WazeAppWidgetLog.m46e("Authenticate failed [error =" + rp.getStatusLine().getStatusCode() + "]");
                        if (z) {
                            RealTimeManager.this.storeOfflineStatCommand(sCommand, context);
                        }
                    }
                } catch (IOException e) {
                    WazeAppWidgetLog.m46e("IO error [error=" + e.getMessage() + "]");
                    if (z) {
                        RealTimeManager.this.storeOfflineStatCommand(sBuildCommand, context);
                    }
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            bOnUiThread = true;
        }
        if (bOnUiThread) {
            new Thread(post).start();
        } else {
            post.run();
        }
    }

    public void StoreStat(Context mContext, String Eventname, String[] EventParams) {
        String url;
        if (this.mIsSecuredConnection) {
            url = this.mSecuredServerUrl;
        } else {
            url = this.mServerUrl;
        }
        if (url == null || url.length() <= 0) {
            Log.e("OfflineStats", "server url is null");
            return;
        }
        String sBuildCommand = getInstance().GetURIForStat(mContext, Eventname, EventParams, false);
        if (sBuildCommand == null) {
            Log.e("OfflineStats", "build command failed");
        } else {
            storeOfflineStatCommand(sBuildCommand, mContext);
        }
    }

    private void storeOfflineStatCommand(String command, Context context) {
        HashSet<String> stats;
        Logger.d("STORE_OFFLINE storing " + command);
        SharedPreferences prefs = context.getSharedPreferences(OFFLINE_STATS_SHARED_PREF, 0);
        Set<String> storedStats = prefs.getStringSet(STATS_PREF, null);
        if (storedStats != null) {
            stats = new HashSet(storedStats);
        } else {
            stats = new HashSet();
        }
        stats.add(command);
        Logger.d("STORE_OFFLINE there are " + stats.size() + " stored commands");
        prefs.edit().putStringSet(STATS_PREF, stats).apply();
        prefs.edit().commit();
    }

    public void sendStoredOfflineStatCommands(Context context) {
        boolean bOnUiThread = false;
        Logger.d("STORE_OFFLINE restoring");
        final SharedPreferences prefs = context.getSharedPreferences(OFFLINE_STATS_SHARED_PREF, 0);
        final Set<String> storedStats = prefs.getStringSet(STATS_PREF, null);
        if (storedStats != null) {
            Logger.d("STORE_OFFLINE there are " + storedStats.size() + " stored commands");
            Runnable post = new Runnable() {
                public void run() {
                    String url;
                    if (RealTimeManager.this.mIsSecuredConnection) {
                        url = RealTimeManager.this.mSecuredServerUrl;
                    } else {
                        url = RealTimeManager.this.mServerUrl;
                    }
                    HashSet<String> unsentStats = new HashSet();
                    for (String statCommand : storedStats) {
                        Logger.d("STORE_OFFLINE trying to send " + statCommand);
                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost(url + "/distrib/static");
                        try {
                            httppost.addHeader("Content-Type", "binary/octet-stream");
                            httppost.setEntity(new StringEntity(statCommand));
                            HttpResponse rp = httpclient.execute(httppost);
                            if (rp.getStatusLine().getStatusCode() != 200) {
                                WazeAppWidgetLog.m46e("Authenticate failed [error =" + rp.getStatusLine().getStatusCode() + "]");
                                unsentStats.add(statCommand);
                            }
                        } catch (IOException e) {
                            Logger.d("STORE_OFFLINE failed to send");
                            WazeAppWidgetLog.m46e("IO error [error=" + e.getMessage() + "]");
                            unsentStats.add(statCommand);
                        }
                    }
                    Logger.d("STORE_OFFLINE now there are " + unsentStats.size() + " stored commands");
                    prefs.edit().putStringSet(RealTimeManager.STATS_PREF, unsentStats).apply();
                    prefs.edit().commit();
                }
            };
            if (Looper.myLooper() == Looper.getMainLooper()) {
                bOnUiThread = true;
            }
            if (bOnUiThread) {
                new Thread(post).start();
            } else {
                post.run();
            }
        }
    }

    public void SendAdsStat(Context mContext, String Eventname) {
        String url;
        if (this.mIsSecuredConnection) {
            url = this.mSecuredServerUrl;
        } else {
            url = this.mServerUrl;
        }
        if (url == null || url.length() <= 0) {
            Log.e("OfflineStats", "server url is null");
            return;
        }
        String params = WazePreferences.getProperty("Offline Ads.Params");
        if (params == null || params.isEmpty()) {
            Log.e("OfflineStats", " Ads build params failed");
            return;
        }
        final String sBuildCommand = getInstance().GetURIForStat(mContext, Eventname, new String[]{params}, true);
        if (sBuildCommand == null) {
            Log.e("OfflineStats", " Ads build command failed");
            return;
        }
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(url + "/distrib/static");
        new Thread() {
            public void run() {
                try {
                    httppost.addHeader("Content-Type", "binary/octet-stream");
                    httppost.setEntity(new StringEntity(sBuildCommand));
                    HttpResponse rp = httpclient.execute(httppost);
                    if (rp.getStatusLine().getStatusCode() != 200) {
                        WazeAppWidgetLog.m46e("Authenticate failed [error =" + rp.getStatusLine().getStatusCode() + "]");
                    }
                } catch (IOException e) {
                    WazeAppWidgetLog.m46e("Authenticate error [error=" + e.getMessage() + "]");
                }
            }
        }.start();
    }

    public void SendBankAccount(Context mContext, String token, String id, String firstname, String lastname, String middleName, String branchId, String BankCode, String accountId) {
        String url = CarpoolNativeManager.getInstance().CarpoolServerURLNTV();
        if (url == null || url.length() <= 0) {
            Log.e("SendAccount", "server url is null");
            CarpoolNativeManager.getInstance().onCarpoolUpdateBankAccountSent(false);
            return;
        }
        final String sBuildCommand = getInstance().GetURIForBankAccount(mContext, id, firstname, lastname, middleName, branchId, BankCode, accountId);
        if (sBuildCommand == null) {
            Log.e("SendAccount", " build command failed");
            CarpoolNativeManager.getInstance().onCarpoolUpdateBankAccountSent(false);
            return;
        }
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(url + "/users/" + id + "/payments/storeBankAccount");
        httppost.addHeader("Content-Type", "application/json");
        httppost.addHeader("EES-S7E-Mode", "json");
        httppost.addHeader("X-GFE-SSL", "yes");
        httppost.addHeader("Authorization", "Bearer " + token);
        new Thread() {
            public void run() {
                try {
                    httppost.setEntity(new StringEntity(sBuildCommand));
                    if (httpclient.execute(httppost).getStatusLine().getStatusCode() == 200) {
                        CarpoolNativeManager.getInstance().onCarpoolUpdateBankAccountSent(true);
                    } else {
                        CarpoolNativeManager.getInstance().onCarpoolUpdateBankAccountSent(false);
                    }
                } catch (IOException e) {
                    CarpoolNativeManager.getInstance().onCarpoolUpdateBankAccountSent(false);
                }
            }
        }.start();
    }

    public void sendOfflineCommand(Context mContext, String command) {
        String url;
        if (this.mIsSecuredConnection) {
            url = this.mSecuredServerUrl;
        } else {
            url = this.mServerUrl;
        }
        if (url == null || url.length() <= 0) {
            Log.e("sendOfflineCommand", "server url is null");
            return;
        }
        final String sBuildCommand = getInstance().GetURIForCommand(mContext, command);
        if (sBuildCommand == null) {
            Log.e("sendOfflineCommand", "build command failed");
            return;
        }
        final HttpClient httpclient = new DefaultHttpClient();
        final HttpPost httppost = new HttpPost(url + "/distrib/static");
        Runnable post = new Runnable() {
            public void run() {
                try {
                    httppost.addHeader("Content-Type", "binary/octet-stream");
                    httppost.setEntity(new StringEntity(sBuildCommand));
                    HttpResponse rp = httpclient.execute(httppost);
                    if (rp.getStatusLine().getStatusCode() != 200) {
                        WazeAppWidgetLog.m46e("Authenticate failed [error =" + rp.getStatusLine().getStatusCode() + "]");
                    }
                } catch (IOException e) {
                    WazeAppWidgetLog.m46e("Authenticate error [error=" + e.getMessage() + "]");
                }
            }
        };
        if (Looper.myLooper() == Looper.getMainLooper()) {
            new Thread(post).start();
        } else {
            post.run();
        }
    }

    public static String getProtocol() {
        return Integer.toString(146);
    }
}
