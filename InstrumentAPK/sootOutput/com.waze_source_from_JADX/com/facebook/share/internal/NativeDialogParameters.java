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

public class NativeDialogParameters {
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
            ShareVideoContent $r7 = (ShareVideoContent) $r1;
            return create($r7, ShareInternalUtility.getVideoUrl($r7, $r0), $z0);
        } else if (!($r1 instanceof ShareOpenGraphContent)) {
            return null;
        } else {
            ShareOpenGraphContent $r9 = (ShareOpenGraphContent) $r1;
            try {
                return create($r9, ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForCall($r0, $r9), false), $z0);
            } catch (JSONException $r2) {
                throw new FacebookException("Unable to create a JSON Object from the provided ShareOpenGraphContent: " + $r2.getMessage());
            }
        }
    }

    private static Bundle create(ShareLinkContent $r0, boolean $z0) throws  {
        Bundle $r1 = createBaseParameters($r0, $z0);
        Utility.putNonEmptyString($r1, ShareConstants.TITLE, $r0.getContentTitle());
        Utility.putNonEmptyString($r1, ShareConstants.DESCRIPTION, $r0.getContentDescription());
        Utility.putUri($r1, "IMAGE", $r0.getImageUrl());
        return $r1;
    }

    private static Bundle create(@Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)", "Landroid/os/Bundle;"}) SharePhotoContent $r0, @Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)", "Landroid/os/Bundle;"}) List<String> $r1, @Signature({"(", "Lcom/facebook/share/model/SharePhotoContent;", "Ljava/util/List", "<", "Ljava/lang/String;", ">;Z)", "Landroid/os/Bundle;"}) boolean $z0) throws  {
        Bundle $r3 = createBaseParameters($r0, $z0);
        $r3.putStringArrayList(ShareConstants.PHOTOS, new ArrayList($r1));
        return $r3;
    }

    private static Bundle create(ShareVideoContent $r0, String $r1, boolean $z0) throws  {
        Bundle $r2 = createBaseParameters($r0, $z0);
        Utility.putNonEmptyString($r2, ShareConstants.TITLE, $r0.getContentTitle());
        Utility.putNonEmptyString($r2, ShareConstants.DESCRIPTION, $r0.getContentDescription());
        Utility.putNonEmptyString($r2, ShareConstants.VIDEO_URL, $r1);
        return $r2;
    }

    private static Bundle create(ShareOpenGraphContent $r0, JSONObject $r1, boolean $z0) throws  {
        Bundle $r2 = createBaseParameters($r0, $z0);
        Utility.putNonEmptyString($r2, ShareConstants.PREVIEW_PROPERTY_NAME, (String) ShareInternalUtility.getFieldNameAndNamespaceFromFullName($r0.getPreviewPropertyName()).second);
        Utility.putNonEmptyString($r2, ShareConstants.ACTION_TYPE, $r0.getAction().getActionType());
        Utility.putNonEmptyString($r2, "ACTION", $r1.toString());
        return $r2;
    }

    private static Bundle createBaseParameters(ShareContent $r0, boolean $z0) throws  {
        Bundle $r1 = new Bundle();
        Utility.putUri($r1, ShareConstants.CONTENT_URL, $r0.getContentUrl());
        Utility.putNonEmptyString($r1, "PLACE", $r0.getPlaceId());
        Utility.putNonEmptyString($r1, ShareConstants.REF, $r0.getRef());
        $r1.putBoolean(ShareConstants.DATA_FAILURES_FATAL, $z0);
        Collection $r4 = $r0.getPeopleIds();
        if (Utility.isNullOrEmpty($r4)) {
            return $r1;
        }
        $r1.putStringArrayList("FRIENDS", new ArrayList($r4));
        return $r1;
    }
}
