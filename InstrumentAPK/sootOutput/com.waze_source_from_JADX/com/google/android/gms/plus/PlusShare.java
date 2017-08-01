package com.google.android.gms.plus;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.plus.internal.model.people.PersonEntity;
import com.google.android.gms.plus.model.people.Person;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;

/* compiled from: dalvik_source_com.waze.apk */
public final class PlusShare {
    public static final String EXTRA_CALL_TO_ACTION = "com.google.android.apps.plus.CALL_TO_ACTION";
    public static final String EXTRA_CONTENT_DEEP_LINK_ID = "com.google.android.apps.plus.CONTENT_DEEP_LINK_ID";
    public static final String EXTRA_CONTENT_DEEP_LINK_METADATA = "com.google.android.apps.plus.CONTENT_DEEP_LINK_METADATA";
    public static final String EXTRA_CONTENT_URL = "com.google.android.apps.plus.CONTENT_URL";
    public static final String EXTRA_IS_INTERACTIVE_POST = "com.google.android.apps.plus.GOOGLE_INTERACTIVE_POST";
    public static final String EXTRA_SENDER_ID = "com.google.android.apps.plus.SENDER_ID";
    public static final String KEY_CALL_TO_ACTION_DEEP_LINK_ID = "deepLinkId";
    public static final String KEY_CALL_TO_ACTION_LABEL = "label";
    public static final String KEY_CALL_TO_ACTION_URL = "url";
    public static final String KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION = "description";
    public static final String KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL = "thumbnailUrl";
    public static final String KEY_CONTENT_DEEP_LINK_METADATA_TITLE = "title";
    public static final String PARAM_CONTENT_DEEP_LINK_ID = "deep_link_id";

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private boolean aYw;
        private ArrayList<Uri> aYx;
        private final Context mContext;
        private final Intent mIntent = new Intent().setAction("android.intent.action.SEND");

        public Builder(Activity $r1) throws  {
            this.mContext = $r1;
            this.mIntent.addFlags(524288);
            if ($r1 != null && $r1.getComponentName() != null) {
                this.aYw = true;
            }
        }

        public Builder(Context $r1) throws  {
            this.mContext = $r1;
        }

        public Builder addCallToAction(String $r1, Uri $r2, String $r3) throws  {
            zzab.zza(this.aYw, (Object) "Must include the launching activity with PlusShare.Builder constructor before setting call-to-action");
            boolean $z0 = ($r2 == null || TextUtils.isEmpty($r2.toString())) ? false : true;
            zzab.zzb($z0, (Object) "Must provide a call to action URL");
            Bundle $r5 = new Bundle();
            if (!TextUtils.isEmpty($r1)) {
                $r5.putString(PlusShare.KEY_CALL_TO_ACTION_LABEL, $r1);
            }
            $r5.putString("url", $r2.toString());
            if (!TextUtils.isEmpty($r3)) {
                zzab.zza(PlusShare.zzrz($r3), (Object) "The specified deep-link ID was malformed.");
                $r5.putString(PlusShare.KEY_CALL_TO_ACTION_DEEP_LINK_ID, $r3);
            }
            this.mIntent.putExtra(PlusShare.EXTRA_CALL_TO_ACTION, $r5);
            this.mIntent.putExtra(PlusShare.EXTRA_IS_INTERACTIVE_POST, true);
            this.mIntent.setType("text/plain");
            return this;
        }

        public Builder addStream(Uri $r0) throws  {
            Uri $r4 = (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            if ($r4 == null) {
                return setStream($r0);
            }
            if (this.aYx == null) {
                this.aYx = new ArrayList();
            }
            this.aYx.add($r4);
            this.aYx.add($r0);
            return this;
        }

        public Intent getIntent() throws  {
            boolean $z0 = true;
            boolean $z1 = this.aYx != null && this.aYx.size() > 1;
            boolean $z2 = "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
            boolean $z3 = this.mIntent.getBooleanExtra(PlusShare.EXTRA_IS_INTERACTIVE_POST, false);
            boolean $z4 = ($z1 && $z3) ? false : true;
            zzab.zza($z4, (Object) "Call-to-action buttons are only available for URLs.");
            $z4 = !$z3 || this.mIntent.hasExtra(PlusShare.EXTRA_CONTENT_URL);
            zzab.zza($z4, (Object) "The content URL is required for interactive posts.");
            if (!(!$z3 || this.mIntent.hasExtra(PlusShare.EXTRA_CONTENT_URL) || this.mIntent.hasExtra(PlusShare.EXTRA_CONTENT_DEEP_LINK_ID))) {
                $z0 = false;
            }
            zzab.zza($z0, (Object) "Must set content URL or content deep-link ID to use a call-to-action button.");
            if (this.mIntent.hasExtra(PlusShare.EXTRA_CONTENT_DEEP_LINK_ID)) {
                zzab.zza(PlusShare.zzrz(this.mIntent.getStringExtra(PlusShare.EXTRA_CONTENT_DEEP_LINK_ID)), (Object) "The specified deep-link ID was malformed.");
            }
            if (!$z1 && $z2) {
                this.mIntent.setAction("android.intent.action.SEND");
                if (this.aYx == null || this.aYx.isEmpty()) {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                } else {
                    this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable) this.aYx.get(0));
                }
                this.aYx = null;
            }
            if ($z1 && !$z2) {
                this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
                if (this.aYx == null || this.aYx.isEmpty()) {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                } else {
                    this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.aYx);
                }
            }
            if ("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE".equals(this.mIntent.getAction())) {
                this.mIntent.setPackage("com.google.android.gms");
                return this.mIntent;
            } else if (this.mIntent.hasExtra("android.intent.extra.STREAM")) {
                this.mIntent.setPackage("com.google.android.apps.plus");
                return this.mIntent;
            } else {
                this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_GOOGLE");
                this.mIntent.setPackage("com.google.android.gms");
                return this.mIntent;
            }
        }

        public Builder setAccountName(String $r1) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.ACCOUNT", $r1);
            return this;
        }

        public Builder setAppIconResourceId(int $i0) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.INTERNAL_APP_ICON_RESOURCE", $i0);
            return this;
        }

        public Builder setAppName(String $r1) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.INTERNAL_APP_NAME", $r1);
            return this;
        }

        public Builder setClientApplicationId(String $r1) throws  {
            zzab.zzb(!TextUtils.isEmpty($r1), (Object) "clientApplicationId must not be empty.");
            try {
                Integer.parseInt($r1);
                this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.CLIENT_APPLICATION_ID", $r1);
                return this;
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("clientApplicationId must be parsable to an int.");
            }
        }

        public Builder setContentDeepLinkId(String $r1) throws  {
            return setContentDeepLinkId($r1, null, null, null);
        }

        public Builder setContentDeepLinkId(String $r1, String $r2, String $r3, Uri $r4) throws  {
            zzab.zzb(this.aYw, (Object) "Must include the launching activity with PlusShare.Builder constructor before setting deep links");
            zzab.zzb(!TextUtils.isEmpty($r1), (Object) "The deepLinkId parameter is required.");
            Bundle $r5 = PlusShare.zza($r2, $r3, $r4);
            this.mIntent.putExtra(PlusShare.EXTRA_CONTENT_DEEP_LINK_ID, $r1);
            this.mIntent.putExtra(PlusShare.EXTRA_CONTENT_DEEP_LINK_METADATA, $r5);
            this.mIntent.setType("text/plain");
            return this;
        }

        public Builder setContentUrl(Uri $r1) throws  {
            String $r2 = null;
            if ($r1 != null) {
                $r2 = $r1.toString();
            }
            if (TextUtils.isEmpty($r2)) {
                this.mIntent.removeExtra(PlusShare.EXTRA_CONTENT_URL);
                return this;
            }
            this.mIntent.putExtra(PlusShare.EXTRA_CONTENT_URL, $r2);
            return this;
        }

        public Builder setPlusPageDisplayName(String $r1) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.PLUS_PAGE_DISPLAY_NAME", $r1);
            return this;
        }

        public Builder setPlusPageId(String $r1) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.PLUS_PAGE_ID", $r1);
            return this;
        }

        public Builder setPlusPageImageUrl(String $r1) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.PLUS_PAGE_IMAGE_URL", $r1);
            return this;
        }

        public Builder setRecipients(@Signature({"(", "Lcom/google/android/gms/plus/model/people/Person;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/model/people/Person;", ">;)", "Lcom/google/android/gms/plus/PlusShare$Builder;"}) Person $r1, @Signature({"(", "Lcom/google/android/gms/plus/model/people/Person;", "Ljava/util/List", "<", "Lcom/google/android/gms/plus/model/people/Person;", ">;)", "Lcom/google/android/gms/plus/PlusShare$Builder;"}) List<Person> $r2) throws  {
            this.mIntent.putExtra(PlusShare.EXTRA_SENDER_ID, $r1 != null ? $r1.getId() : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            int $i0 = $r2 != null ? $r2.size() : 0;
            if ($i0 == 0) {
                this.mIntent.removeExtra("com.google.android.apps.plus.RECIPIENT_IDS");
                this.mIntent.removeExtra("com.google.android.apps.plus.RECIPIENT_DISPLAY_NAMES");
                return this;
            }
            ArrayList $r5 = new ArrayList($i0);
            ArrayList $r6 = new ArrayList($i0);
            for (Person $r12 : $r2) {
                $r5.add($r12.getId());
                $r6.add($r12.getDisplayName());
            }
            this.mIntent.putStringArrayListExtra("com.google.android.apps.plus.RECIPIENT_IDS", $r5);
            this.mIntent.putStringArrayListExtra("com.google.android.apps.plus.RECIPIENT_DISPLAY_NAMES", $r6);
            return this;
        }

        public Builder setShareButtonName(String $r1) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.INTERNAL_SHARE_BUTTON_NAME", $r1);
            return this;
        }

        public Builder setShareContextType(String $r1) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.SHARE_CONTEXT_TYPE", $r1);
            return this;
        }

        public Builder setShareOnGooglePlus(boolean $z0) throws  {
            this.mIntent.setAction("com.google.android.gms.plus.action.SHARE_INTERNAL_GOOGLE");
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.SHARE_ON_PLUS", $z0);
            return this;
        }

        public Builder setStream(Uri $r1) throws  {
            this.aYx = null;
            this.mIntent.putExtra("android.intent.extra.STREAM", $r1);
            return this;
        }

        public Builder setText(CharSequence $r1) throws  {
            this.mIntent.putExtra("android.intent.extra.TEXT", $r1);
            return this;
        }

        public Builder setType(String $r1) throws  {
            this.mIntent.setType($r1);
            return this;
        }
    }

    @Deprecated
    protected PlusShare() throws  {
        throw new AssertionError();
    }

    public static Person createPerson(String $r0, String $r1) throws  {
        if (TextUtils.isEmpty($r0)) {
            throw new IllegalArgumentException("MinimalPerson ID must not be empty.");
        } else if (!TextUtils.isEmpty($r1)) {
            return new PersonEntity($r1, $r0, null, 0, null);
        } else {
            throw new IllegalArgumentException("Display name must not be empty.");
        }
    }

    public static String getDeepLinkId(Intent $r0) throws  {
        return $r0 != null ? $r0.getData() != null ? $r0.getData().getQueryParameter(PARAM_CONTENT_DEEP_LINK_ID) : null : null;
    }

    public static Bundle zza(String $r0, String $r1, Uri $r2) throws  {
        Bundle $r3 = new Bundle();
        $r3.putString("title", $r0);
        $r3.putString("description", $r1);
        if ($r2 == null) {
            return $r3;
        }
        $r3.putString(KEY_CONTENT_DEEP_LINK_METADATA_THUMBNAIL_URL, $r2.toString());
        return $r3;
    }

    protected static boolean zzrz(String $r0) throws  {
        if (TextUtils.isEmpty($r0)) {
            Log.e("GooglePlusPlatform", "The provided deep-link ID is empty.");
            return false;
        } else if (!$r0.contains(" ")) {
            return true;
        } else {
            Log.e("GooglePlusPlatform", "Spaces are not allowed in deep-link IDs.");
            return false;
        }
    }
}
