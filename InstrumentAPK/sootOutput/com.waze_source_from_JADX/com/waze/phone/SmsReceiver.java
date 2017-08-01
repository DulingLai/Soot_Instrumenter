package com.waze.phone;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsMessage;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsEvents;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SmsReceiver extends BroadcastReceiver {
    static SmsReceiver mInstace = null;

    public void onReceive(Context context, Intent intent) {
        for (Object pdu : (Object[]) intent.getExtras().get("pdus")) {
            SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdu);
            Log.d(Logger.TAG, "SMS message");
            String origin = msg.getOriginatingAddress();
            String body = msg.getMessageBody();
            if (body != null && body.toUpperCase().contains("(WAZEAPP)")) {
                NativeManager.getInstance().SignUplogAnalytics(AnalyticsEvents.ANALYTICS_EVENT_SIGN_UP_READ_SMS, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AppEventsConstants.EVENT_PARAM_VALUE_NO, true);
                String sPin = null;
                Matcher m = Pattern.compile("\\w+([0-9]+)\\w+([0-9]+)").matcher(body);
                m.find();
                if (m.groupCount() > 0) {
                    sPin = m.group();
                }
                if (sPin != null) {
                    NativeManager.getInstance().AuthPin(body);
                    abortBroadcast();
                }
            }
        }
    }

    public static void Register(Context context) {
        if (mInstace == null) {
            IntentFilter n = new IntentFilter();
            n.addAction("android.provider.Telephony.SMS_RECEIVED");
            n.addCategory("android.intent.category.DEFAULT");
            n.setPriority(1000);
            mInstace = new SmsReceiver();
            context.registerReceiver(mInstace, n);
        }
    }

    public static void UnRegister(Context context) {
        if (mInstace != null) {
            context.unregisterReceiver(mInstace);
            mInstace = null;
        }
    }
}
