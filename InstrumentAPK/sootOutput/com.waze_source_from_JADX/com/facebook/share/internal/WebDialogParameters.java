package com.facebook.share.internal;

import android.os.Bundle;
import com.facebook.FacebookException;
import com.facebook.internal.Utility;
import com.facebook.share.model.AppGroupCreationContent;
import com.facebook.share.model.AppGroupCreationContent.AppGroupPrivacy;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphContent;
import java.util.Locale;
import org.json.JSONObject;

public class WebDialogParameters {
    public static Bundle create(AppGroupCreationContent $r0) throws  {
        Bundle $r1 = new Bundle();
        Utility.putNonEmptyString($r1, "name", $r0.getName());
        Utility.putNonEmptyString($r1, "description", $r0.getDescription());
        AppGroupPrivacy $r4 = $r0.getAppGroupPrivacy();
        if ($r4 == null) {
            return $r1;
        }
        Utility.putNonEmptyString($r1, ShareConstants.WEB_DIALOG_PARAM_PRIVACY, $r4.toString().toLowerCase(Locale.ENGLISH));
        return $r1;
    }

    public static Bundle create(GameRequestContent $r0) throws  {
        Bundle $r1 = new Bundle();
        Utility.putNonEmptyString($r1, ShareConstants.WEB_DIALOG_PARAM_MESSAGE, $r0.getMessage());
        Utility.putNonEmptyString($r1, "to", $r0.getTo());
        Utility.putNonEmptyString($r1, "title", $r0.getTitle());
        Utility.putNonEmptyString($r1, "data", $r0.getData());
        if ($r0.getActionType() != null) {
            Utility.putNonEmptyString($r1, ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, $r0.getActionType().toString().toLowerCase(Locale.ENGLISH));
        }
        Utility.putNonEmptyString($r1, "object_id", $r0.getObjectId());
        if ($r0.getFilters() != null) {
            Utility.putNonEmptyString($r1, ShareConstants.WEB_DIALOG_PARAM_FILTERS, $r0.getFilters().toString().toLowerCase(Locale.ENGLISH));
        }
        Utility.putCommaSeparatedStringList($r1, ShareConstants.WEB_DIALOG_PARAM_SUGGESTIONS, $r0.getSuggestions());
        return $r1;
    }

    public static Bundle create(ShareLinkContent $r0) throws  {
        Bundle $r1 = new Bundle();
        Utility.putUri($r1, ShareConstants.WEB_DIALOG_PARAM_HREF, $r0.getContentUrl());
        return $r1;
    }

    public static Bundle create(ShareOpenGraphContent $r0) throws  {
        Bundle $r2 = new Bundle();
        Utility.putNonEmptyString($r2, ShareConstants.WEB_DIALOG_PARAM_ACTION_TYPE, $r0.getAction().getActionType());
        try {
            JSONObject $r5 = ShareInternalUtility.removeNamespacesFromOGJsonObject(ShareInternalUtility.toJSONObjectForWeb($r0), false);
            if ($r5 == null) {
                return $r2;
            }
            Utility.putNonEmptyString($r2, ShareConstants.WEB_DIALOG_PARAM_ACTION_PROPERTIES, $r5.toString());
            return $r2;
        } catch (Throwable $r1) {
            throw new FacebookException("Unable to serialize the ShareOpenGraphContent to JSON", $r1);
        }
    }

    public static Bundle createForFeed(ShareLinkContent $r0) throws  {
        Bundle $r1 = new Bundle();
        Utility.putNonEmptyString($r1, "name", $r0.getContentTitle());
        Utility.putNonEmptyString($r1, "description", $r0.getContentDescription());
        Utility.putNonEmptyString($r1, "link", Utility.getUriString($r0.getContentUrl()));
        Utility.putNonEmptyString($r1, "picture", Utility.getUriString($r0.getImageUrl()));
        return $r1;
    }

    public static Bundle createForFeed(ShareFeedContent $r0) throws  {
        Bundle $r1 = new Bundle();
        Utility.putNonEmptyString($r1, "to", $r0.getToId());
        Utility.putNonEmptyString($r1, "link", $r0.getLink());
        Utility.putNonEmptyString($r1, "picture", $r0.getPicture());
        Utility.putNonEmptyString($r1, "source", $r0.getMediaSource());
        Utility.putNonEmptyString($r1, "name", $r0.getLinkName());
        Utility.putNonEmptyString($r1, ShareConstants.FEED_CAPTION_PARAM, $r0.getLinkCaption());
        Utility.putNonEmptyString($r1, "description", $r0.getLinkDescription());
        return $r1;
    }
}
