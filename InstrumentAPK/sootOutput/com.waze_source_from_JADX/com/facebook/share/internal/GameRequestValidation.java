package com.facebook.share.internal;

import com.facebook.internal.Validate;
import com.facebook.share.model.GameRequestContent;
import com.facebook.share.model.GameRequestContent.ActionType;

public class GameRequestValidation {
    public static void validate(GameRequestContent $r0) throws  {
        boolean $z1;
        boolean $z0 = false;
        Validate.notNull($r0.getMessage(), ShareConstants.WEB_DIALOG_PARAM_MESSAGE);
        if ($r0.getObjectId() != null) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        if ($r0.getActionType() == ActionType.ASKFOR || $r0.getActionType() == ActionType.SEND) {
            $z0 = true;
        }
        if ($z1 ^ $z0) {
            throw new IllegalArgumentException("Object id should be provided if and only if action type is send or askfor");
        }
        int $i0 = 0;
        if ($r0.getTo() != null) {
            $i0 = 0 + 1;
        }
        if ($r0.getSuggestions() != null) {
            $i0++;
        }
        if ($r0.getFilters() != null) {
            $i0++;
        }
        if ($i0 > 1) {
            throw new IllegalArgumentException("Parameters to, filters and suggestions are mutually exclusive");
        }
    }
}
