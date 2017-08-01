package com.google.android.gms.plus;

import android.content.Intent;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.zzab;
import com.google.android.gms.common.people.data.AudienceMember;

/* compiled from: dalvik_source_com.waze.apk */
public final class PlusReply {

    /* compiled from: dalvik_source_com.waze.apk */
    public static class Builder {
        private final Intent mIntent = new Intent("com.google.android.gms.plus.action.REPLY_INTERNAL_GOOGLE");

        public Builder() throws  {
            this.mIntent.addFlags(524288);
        }

        public Intent getIntent() throws  {
            this.mIntent.setPackage("com.google.android.gms");
            return this.mIntent;
        }

        public Builder setAccountName(String $r1) throws  {
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.ACCOUNT", $r1);
            return this;
        }

        public Builder setActivityId(String $r1) throws  {
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.INTERNAL_REPLY_ACTIVITY_ID", $r1);
            return this;
        }

        public Builder setAddCommentHint(String $r1) throws  {
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.INTERNAL_REPLY_ADD_COMMENT_HINT", $r1);
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

        public Builder setPlusPageId(String $r1) throws  {
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.PLUS_PAGE_ID", $r1);
            return this;
        }

        public Builder setPrefilledPlusMention(String $r1, String $r2, String $r3) throws  {
            String str = "com.google.android.gms.plus.intent.extra.INTERNAL_PREFILLED_PLUS_MENTION";
            this.mIntent.putExtra(str, (Parcelable) AudienceMember.forPersonWithGaiaId($r1, $r2, $r3));
            return this;
        }

        public Builder setShareContextType(String $r1) throws  {
            this.mIntent.putExtra("com.google.android.gms.plus.intent.extra.SHARE_CONTEXT_TYPE", $r1);
            return this;
        }
    }
}
