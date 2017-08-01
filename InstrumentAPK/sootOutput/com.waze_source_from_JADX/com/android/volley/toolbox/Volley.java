package com.android.volley.toolbox;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.http.AndroidHttpClient;
import android.os.Build.VERSION;
import com.android.volley.RequestQueue;
import java.io.File;

public class Volley {
    private static final String DEFAULT_CACHE_DIR = "volley";

    public static RequestQueue newRequestQueue(Context $r0, HttpStack $r4) throws  {
        File $r1 = r14;
        File r14 = new File($r0.getCacheDir(), DEFAULT_CACHE_DIR);
        String $r6 = "volley/0";
        try {
            String $r7 = $r0.getPackageName();
            $r6 = $r7 + "/" + $r0.getPackageManager().getPackageInfo($r7, 0).versionCode;
        } catch (NameNotFoundException e) {
        }
        if ($r4 == null) {
            if (VERSION.SDK_INT >= 9) {
                $r4 = r16;
                HurlStack r16 = new HurlStack();
            } else {
                Object $r42 = r0;
                HttpClientStack httpClientStack = new HttpClientStack(AndroidHttpClient.newInstance($r6));
            }
        }
        RequestQueue $r3 = r0;
        RequestQueue requestQueue = new RequestQueue(new DiskBasedCache($r1), new BasicNetwork($r4));
        $r3.start();
        return $r3;
    }

    public static RequestQueue newRequestQueue(Context $r0) throws  {
        return newRequestQueue($r0, null);
    }
}
