package com.waze.now;

import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import com.google.android.now.NowAuthService;
import com.google.android.now.NowAuthService.DisabledException;
import com.google.android.now.NowAuthService.HaveTokenAlreadyException;
import com.google.android.now.NowAuthService.TooManyRequestsException;
import com.google.android.now.NowAuthService.UnauthorizedException;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.push.Constants;
import java.io.IOException;

public class GoogleNowAuthenticator {
    private static long TIME_TO_REFRESH = 86400;

    static class C22331 extends AsyncTask<Void, Void, String> {
        C22331() {
        }

        protected String doInBackground(Void... params) {
            String refresh_token = "";
            try {
                if (GoogleNowAuthenticator.GetLastRefreshTokenTime() + GoogleNowAuthenticator.TIME_TO_REFRESH < System.currentTimeMillis() / 1000) {
                    GoogleNowAuthenticator.SaveLastRefreshTokenTime();
                    refresh_token = NowAuthService.getAuthCode(AppService.getAppContext(), Constants.GOOGLE_NOW_ID);
                }
            } catch (IOException e) {
                Logger.d(Log.getStackTraceString(e));
            } catch (TooManyRequestsException e2) {
                Logger.d(Log.getStackTraceString(e2));
            } catch (DisabledException e3) {
                Logger.d(Log.getStackTraceString(e3));
            } catch (UnauthorizedException e4) {
                Logger.d(Log.getStackTraceString(e4));
            } catch (HaveTokenAlreadyException e5) {
                Logger.d(Log.getStackTraceString(e5));
            }
            Log.e("NowAuthenticator", "Token recevied " + refresh_token);
            return refresh_token;
        }

        protected void onPostExecute(String token) {
            if (token != null && !token.isEmpty()) {
                NativeManager.getInstance().SendGoogleNowToken(token);
            }
        }
    }

    public static void refreshAuthCode() {
        new C22331().execute(new Void[0]);
    }

    public static long GetLastRefreshTokenTime() {
        return AppService.getAppContext().getSharedPreferences("com.waze.Now", 0).getLong("refresh_token", 0);
    }

    public static void SaveLastRefreshTokenTime() {
        SharedPreferences prefs = AppService.getAppContext().getSharedPreferences("com.waze.Now", 0);
        prefs.edit().putLong("refresh_token", System.currentTimeMillis() / 1000).apply();
        prefs.edit().commit();
    }
}
