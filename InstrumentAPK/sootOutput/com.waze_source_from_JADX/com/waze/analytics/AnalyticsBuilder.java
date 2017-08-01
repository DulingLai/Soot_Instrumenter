package com.waze.analytics;

import android.text.TextUtils;
import com.waze.Logger;
import java.util.HashMap;
import java.util.Map;

public class AnalyticsBuilder {
    private String mEventName;
    private Map<String, String> mParams = new HashMap();

    public static AnalyticsBuilder analytics(String $r0) throws  {
        return new AnalyticsBuilder($r0);
    }

    private AnalyticsBuilder(String $r1) throws  {
        this.mEventName = $r1;
    }

    public AnalyticsBuilder addParam(String $r1, String $r2) throws  {
        if (TextUtils.isEmpty($r1)) {
            Logger.m38e("AnalyticsBuilder - Cannot add empty param name");
            return this;
        }
        if (this.mParams.containsKey($r1)) {
            Logger.m43w("AnalyticsBuilder - Param " + $r1 + " already exists with value: " + ((String) this.mParams.get($r1)) + ". Replacing with new value: " + $r2);
        }
        this.mParams.put($r1, $r2);
        return this;
    }

    public AnalyticsBuilder addParam(String $r1, long $l0) throws  {
        return addParam($r1, String.valueOf($l0));
    }

    public AnalyticsBuilder addParam(String $r1, float $f0) throws  {
        return addParam($r1, String.valueOf($f0));
    }

    public AnalyticsBuilder addParam(String $r1, double $d0) throws  {
        return addParam($r1, String.valueOf($d0));
    }

    public void send() throws  {
        if (this.mParams.isEmpty()) {
            Analytics.log(this.mEventName);
            Logger.m41i("AnalyticsBuilder - Sending analytics: " + this.mEventName);
            return;
        }
        StringBuilder $r1 = new StringBuilder();
        StringBuilder $r2 = new StringBuilder();
        int $i0 = 0;
        for (String $r4 : this.mParams.keySet()) {
            if ($i0 > 0) {
                $r1.append("|");
                $r2.append("|");
            }
            $r1.append($r4);
            $r2.append((String) this.mParams.get($r4));
            $i0++;
        }
        Analytics.log(this.mEventName, $r1.toString(), $r2.toString());
        Logger.m41i("AnalyticsBuilder - Sending analytics: " + this.mEventName + ", params: " + $r1.toString() + ", values: " + $r2.toString());
    }

    public void sendAndClear() throws  {
        send();
        clear();
    }

    public void clear() throws  {
        this.mParams.clear();
    }

    public String[] getParamsAsStringPair() throws  {
        StringBuilder $r1 = new StringBuilder();
        StringBuilder $r3 = new StringBuilder();
        boolean $z0 = true;
        for (String $r8 : this.mParams.keySet()) {
            if (!$z0) {
                $r1.append("|");
                $r3.append("|");
            }
            $r1.append($r8);
            $r3.append((String) this.mParams.get($r8));
            $z0 = false;
        }
        return new String[]{$r1.toString(), $r3.toString()};
    }
}
