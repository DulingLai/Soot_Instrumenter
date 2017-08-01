package com.waze;

import android.os.AsyncTask;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import java.io.IOException;

public class AdsTracking {

    static class C10951 extends AsyncTask<Void, Void, AdsData> {
        C10951() throws  {
        }

        protected AdsData doInBackground(Void... params) throws  {
            Info $r2 = null;
            try {
                $r2 = AdvertisingIdClient.getAdvertisingIdInfo(AppService.getAppContext());
            } catch (IOException e) {
            } catch (GooglePlayServicesNotAvailableException e2) {
            } catch (IllegalStateException e3) {
            } catch (GooglePlayServicesRepairableException e4) {
            }
            if ($r2 == null) {
                return null;
            }
            return new AdsData($r2.getId(), !$r2.isLimitAdTrackingEnabled());
        }

        protected void onPostExecute(AdsData $r1) throws  {
            if ($r1 != null) {
                NativeManager.getInstance().mAdsData = $r1;
            }
        }
    }

    public static class AdsData {
        public boolean bIsTrackingAllowed;
        public String token;

        public AdsData(String $r1, boolean $z0) throws  {
            this.token = $r1;
            this.bIsTrackingAllowed = $z0;
        }
    }

    public static void getAdsTrackingData() throws  {
        new C10951().execute(new Void[0]);
    }
}
