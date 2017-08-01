package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideoContent;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import org.json.JSONException;
import org.json.JSONObject;

public class LegacyNativeDialogParameters {
    private static Bundle create(ShareVideoContent videoContent, boolean dataErrorsFatal) throws  {
        return null;
    }

    public static Bundle create(UUID $r0, ShareContent $r1, boolean $z0) throws  {
        Validate.notNull($r1, "shareContent");
        Validate.notNull($r0, "callId");
        if ($r1 instanceof ShareLinkContent) {
            return create((ShareLinkContent) $r1, $z0);
        }
        if ($r1 instanceof SharePhotoContent) {
            SharePhotoContent $r5 = (SharePhotoContent) $r1;
            return create($r5, ShareInternalUtility.getPhotoUrls($r5, $r0), $z0);
        } else if ($r1 instanceof ShareVideoContent) {
            return create((ShareVideoContent) $r1, $z0);
        } else {
            if (!($r1 instanceof ShareOpenGraphContent)) {
                return null;
            }
            ShareOpenGraphContent $r8 = (ShareOpenGraphContent) $r1;
            try {
                return create($r8, ShareInternalUtility.toJSONObjectForCall($r0, $r8), $z0);
            } catch (JSONException $r2) {
                throw new FacebookException("Unable to create a JSON Object from the provided ShareOpenGraphContent: " + $r2.getMessage());
            }
        }
    }

    private static Bundle create(ShareLinkContent $r0, boolean $z0) throws  {
        Bundle $r1 = createBaseParameters($r0, $z0);
        Utility.putNonEmptyString($r1, ShareConstants.LEGACY_TITLE, $r0.getContentTitle());
        Utility.putNonEmptyString($r1, ShareConstants.LEGACY_DESCRIPTION, $r0.getContentDescription());
        Utility.putUri($r1, ShareConstants.LEGACY_IMAGE, $r0.getImageUrl());
        return $r1;
    }

    private static Bundle create(@Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)", "Landroid/os/Bundle;"}) SharePhotoContent $r0, @Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)", "Landroid/os/Bundle;"}) List<String> $r1, @Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)", "Landroid/os/Bundle;"}) boolean $z0) throws  {
        Bundle $r3 = createBaseParameters($r0, $z0);
        $r3.putStringArrayList(ShareConstants.LEGACY_PHOTOS, new ArrayList($r1));
        return $r3;
    }

    private static Bundle create(ShareOpenGraphContent $r0, JSONObject $r1, boolean $z0) throws  {
        Bundle $r2 = createBaseParameters($r0, $z0);
        Utility.putNonEmptyString($r2, ShareConstants.LEGACY_PREVIEW_PROPERTY_NAME, $r0.getPreviewPropertyName());
        Utility.putNonEmptyString($r2, ShareConstants.LEGACY_ACTION_TYPE, $r0.getAction().getActionType());
        Utility.putNonEmptyString($r2, ShareConstants.LEGACY_ACTION, $r1.toString());
        return $r2;
    }

    private static Bundle createBaseParameters(ShareContent $r0, boolean $z0) throws  {
        Bundle $r1 = new Bundle();
        Utility.putUri($r1, ShareConstants.LEGACY_LINK, $r0.getContentUrl());
        Utility.putNonEmptyString($r1, ShareConstants.LEGACY_PLACE_TAG, $r0.getPlaceId());
        Utility.putNonEmptyString($r1, ShareConstants.LEGACY_REF, $r0.getRef());
        $r1.putBoolean(ShareConstants.LEGACY_DATA_FAILURES_FATAL, $z0);
        Collection $r4 = $r0.getPeopleIds();
        if (Utility.isNullOrEmpty($r4)) {
            return $r1;
        }
        $r1.putStringArrayList(ShareConstants.LEGACY_FRIEND_TAGS, new ArrayList($r4));
        return $r1;
    }
}
