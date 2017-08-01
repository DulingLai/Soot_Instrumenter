package com.google.android.gms.gcm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.RequiresPermission;
import com.google.android.gms.iid.InstanceID;
import java.io.IOException;
import java.util.regex.Pattern;

/* compiled from: dalvik_source_com.waze.apk */
public class GcmPubSub {
    private static GcmPubSub aqM;
    private static final Pattern aqO = Pattern.compile("/topics/[a-zA-Z0-9-_.~%]{1,900}");
    private InstanceID aqN;

    private GcmPubSub(Context $r1) throws  {
        this.aqN = InstanceID.getInstance($r1);
    }

    public static synchronized GcmPubSub getInstance(Context $r0) throws  {
        Class cls = GcmPubSub.class;
        synchronized (cls) {
            try {
                if (aqM == null) {
                    aqM = new GcmPubSub($r0);
                }
                GcmPubSub $r1 = aqM;
                return $r1;
            } finally {
                cls = GcmPubSub.class;
            }
        }
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void subscribe(String $r1, String $r2, Bundle $r3) throws IOException {
        if ($r1 == null || $r1.isEmpty()) {
            $r2 = "Invalid appInstanceToken: ";
            $r1 = String.valueOf($r1);
            throw new IllegalArgumentException($r1.length() != 0 ? $r2.concat($r1) : new String("Invalid appInstanceToken: "));
        } else if ($r2 == null || !aqO.matcher($r2).matches()) {
            $r1 = "Invalid topic name: ";
            $r2 = String.valueOf($r2);
            throw new IllegalArgumentException($r2.length() != 0 ? $r1.concat($r2) : new String("Invalid topic name: "));
        } else {
            if ($r3 == null) {
                $r3 = new Bundle();
            }
            $r3.putString("gcm.topic", $r2);
            this.aqN.getToken($r1, $r2, $r3);
        }
    }

    @RequiresPermission("com.google.android.c2dm.permission.RECEIVE")
    public void unsubscribe(String $r1, String $r2) throws IOException {
        Bundle $r3 = new Bundle();
        $r3.putString("gcm.topic", $r2);
        this.aqN.deleteToken($r1, $r2, $r3);
    }
}
