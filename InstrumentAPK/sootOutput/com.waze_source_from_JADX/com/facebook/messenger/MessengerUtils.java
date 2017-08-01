package com.facebook.messenger;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import bolts.AppLinks;
import com.facebook.FacebookSdk;
import com.facebook.messenger.MessengerThreadParams.Origin;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MessengerUtils {
    public static final String EXTRA_APP_ID = "com.facebook.orca.extra.APPLICATION_ID";
    public static final String EXTRA_EXTERNAL_URI = "com.facebook.orca.extra.EXTERNAL_URI";
    public static final String EXTRA_IS_COMPOSE = "com.facebook.orca.extra.IS_COMPOSE";
    public static final String EXTRA_IS_REPLY = "com.facebook.orca.extra.IS_REPLY";
    public static final String EXTRA_METADATA = "com.facebook.orca.extra.METADATA";
    public static final String EXTRA_PARTICIPANTS = "com.facebook.orca.extra.PARTICIPANTS";
    public static final String EXTRA_PROTOCOL_VERSION = "com.facebook.orca.extra.PROTOCOL_VERSION";
    public static final String EXTRA_REPLY_TOKEN_KEY = "com.facebook.orca.extra.REPLY_TOKEN";
    public static final String EXTRA_THREAD_TOKEN_KEY = "com.facebook.orca.extra.THREAD_TOKEN";
    public static final String ORCA_THREAD_CATEGORY_20150314 = "com.facebook.orca.category.PLATFORM_THREAD_20150314";
    public static final String PACKAGE_NAME = "com.facebook.orca";
    public static final int PROTOCOL_VERSION_20150314 = 20150314;
    private static final String TAG = "MessengerUtils";

    public static void shareToMessenger(Activity $r0, int $i0, ShareToMessengerParams $r1) throws  {
        if (!hasMessengerInstalled($r0)) {
            openMessengerInPlayStore($r0);
        } else if (getAllAvailableProtocolVersions($r0).contains(Integer.valueOf(PROTOCOL_VERSION_20150314))) {
            shareToMessenger20150314($r0, $i0, $r1);
        } else {
            openMessengerInPlayStore($r0);
        }
    }

    private static void shareToMessenger20150314(Activity $r0, int $i0, ShareToMessengerParams $r1) throws  {
        try {
            Intent $r3 = new Intent("android.intent.action.SEND");
            $r3.setFlags(1);
            $r3.setPackage(PACKAGE_NAME);
            $r3.putExtra("android.intent.extra.STREAM", $r1.uri);
            $r3.setType($r1.mimeType);
            String $r5 = FacebookSdk.getApplicationId();
            if ($r5 != null) {
                $r3.putExtra(EXTRA_PROTOCOL_VERSION, PROTOCOL_VERSION_20150314);
                $r3.putExtra(EXTRA_APP_ID, $r5);
                $r3.putExtra(EXTRA_METADATA, $r1.metaData);
                $r3.putExtra(EXTRA_EXTERNAL_URI, $r1.externalUri);
            }
            $r0.startActivityForResult($r3, $i0);
        } catch (ActivityNotFoundException e) {
            $r0.startActivity($r0.getPackageManager().getLaunchIntentForPackage(PACKAGE_NAME));
        }
    }

    public static MessengerThreadParams getMessengerThreadParamsForIntent(Intent $r0) throws  {
        Set $r2 = $r0.getCategories();
        if ($r2 == null) {
            return null;
        }
        if (!$r2.contains(ORCA_THREAD_CATEGORY_20150314)) {
            return null;
        }
        Bundle $r3 = AppLinks.getAppLinkExtras($r0);
        String $r4 = $r3.getString(EXTRA_THREAD_TOKEN_KEY);
        String $r5 = $r3.getString(EXTRA_METADATA);
        String $r6 = $r3.getString(EXTRA_PARTICIPANTS);
        boolean $z0 = $r3.getBoolean(EXTRA_IS_REPLY);
        boolean $z1 = $r3.getBoolean(EXTRA_IS_COMPOSE);
        Origin $r7 = Origin.UNKNOWN;
        if ($z0) {
            $r7 = Origin.REPLY_FLOW;
        } else if ($z1) {
            $r7 = Origin.COMPOSE_FLOW;
        }
        return new MessengerThreadParams($r7, $r4, $r5, parseParticipants($r6));
    }

    public static void finishShareToMessenger(Activity $r0, ShareToMessengerParams $r1) throws  {
        Intent $r2 = $r0.getIntent();
        Set $r3 = $r2.getCategories();
        if ($r3 == null) {
            $r0.setResult(0, null);
            $r0.finish();
        } else if ($r3.contains(ORCA_THREAD_CATEGORY_20150314)) {
            Bundle $r4 = AppLinks.getAppLinkExtras($r2);
            $r2 = new Intent();
            if ($r3.contains(ORCA_THREAD_CATEGORY_20150314)) {
                $r2.putExtra(EXTRA_PROTOCOL_VERSION, PROTOCOL_VERSION_20150314);
                $r2.putExtra(EXTRA_THREAD_TOKEN_KEY, $r4.getString(EXTRA_THREAD_TOKEN_KEY));
                $r2.setDataAndType($r1.uri, $r1.mimeType);
                $r2.setFlags(1);
                $r2.putExtra(EXTRA_APP_ID, FacebookSdk.getApplicationId());
                $r2.putExtra(EXTRA_METADATA, $r1.metaData);
                $r2.putExtra(EXTRA_EXTERNAL_URI, $r1.externalUri);
                $r0.setResult(-1, $r2);
                $r0.finish();
                return;
            }
            throw new RuntimeException();
        } else {
            $r0.setResult(0, null);
            $r0.finish();
        }
    }

    public static boolean hasMessengerInstalled(Context $r0) throws  {
        try {
            $r0.getPackageManager().getPackageInfo(PACKAGE_NAME, 0);
            return true;
        } catch (NameNotFoundException e) {
            return false;
        }
    }

    public static void openMessengerInPlayStore(Context $r0) throws  {
        try {
            startViewUri($r0, "market://details?id=com.facebook.orca");
        } catch (ActivityNotFoundException e) {
            startViewUri($r0, "http://play.google.com/store/apps/details?id=com.facebook.orca");
        }
    }

    private static Set<Integer> getAllAvailableProtocolVersions(@Signature({"(", "Landroid/content/Context;", ")", "Ljava/util/Set", "<", "Ljava/lang/Integer;", ">;"}) Context $r0) throws  {
        ContentResolver $r3 = $r0.getContentResolver();
        HashSet $r2 = new HashSet();
        Cursor $r5 = $r3.query(Uri.parse("content://com.facebook.orca.provider.MessengerPlatformProvider/versions"), new String[]{"version"}, null, null, null);
        if ($r5 == null) {
            return $r2;
        }
        try {
            int $i0 = $r5.getColumnIndex("version");
            while ($r5.moveToNext()) {
                $r2.add(Integer.valueOf($r5.getInt($i0)));
            }
            return $r2;
        } finally {
            $r5.close();
        }
    }

    private static void startViewUri(Context $r0, String $r1) throws  {
        $r0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse($r1)));
    }

    private static List<String> parseParticipants(@Signature({"(", "Ljava/lang/String;", ")", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"}) String $r0) throws  {
        if ($r0 == null || $r0.length() == 0) {
            return Collections.emptyList();
        }
        String[] $r3 = $r0.split(",");
        ArrayList $r2 = new ArrayList();
        for (String $r02 : $r3) {
            $r2.add($r02.trim());
        }
        return $r2;
    }
}
