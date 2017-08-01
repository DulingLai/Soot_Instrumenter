package com.facebook.share.internal;

import android.graphics.Bitmap;
import android.net.Uri;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.internal.Utility;
import com.facebook.internal.Validate;
import com.facebook.share.model.ShareContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.ShareOpenGraphAction;
import com.facebook.share.model.ShareOpenGraphContent;
import com.facebook.share.model.ShareOpenGraphObject;
import com.facebook.share.model.ShareOpenGraphValueContainer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareVideo;
import com.facebook.share.model.ShareVideoContent;
import java.util.List;
import java.util.Locale;

public class ShareContentValidation {
    private static Validator ApiValidator;
    private static Validator DefaultValidator;
    private static Validator WebShareValidator;

    private static class Validator {
        private boolean isOpenGraphContent;

        private Validator() throws  {
            this.isOpenGraphContent = false;
        }

        public void validate(ShareLinkContent $r1) throws  {
            ShareContentValidation.validateLinkContent($r1, this);
        }

        public void validate(SharePhotoContent $r1) throws  {
            ShareContentValidation.validatePhotoContent($r1, this);
        }

        public void validate(ShareVideoContent $r1) throws  {
            ShareContentValidation.validateVideoContent($r1, this);
        }

        public void validate(ShareOpenGraphContent $r1) throws  {
            this.isOpenGraphContent = true;
            ShareContentValidation.validateOpenGraphContent($r1, this);
        }

        public void validate(ShareOpenGraphAction $r1) throws  {
            ShareContentValidation.validateOpenGraphAction($r1, this);
        }

        public void validate(ShareOpenGraphObject $r1) throws  {
            ShareContentValidation.validateOpenGraphObject($r1, this);
        }

        public void validate(ShareOpenGraphValueContainer $r1, boolean $z0) throws  {
            ShareContentValidation.validateOpenGraphValueContainer($r1, this, $z0);
        }

        public void validate(SharePhoto $r1) throws  {
            ShareContentValidation.validatePhotoForNativeDialog($r1, this);
        }

        public void validate(ShareVideo $r1) throws  {
            ShareContentValidation.validateVideo($r1, this);
        }

        public boolean isOpenGraphContent() throws  {
            return this.isOpenGraphContent;
        }
    }

    private static class ApiValidator extends Validator {
        private ApiValidator() throws  {
            super();
        }

        public void validate(SharePhoto $r1) throws  {
            ShareContentValidation.validatePhotoForApi($r1, this);
        }
    }

    private static class WebShareValidator extends Validator {
        private WebShareValidator() throws  {
            super();
        }

        public void validate(SharePhotoContent photoContent) throws  {
            throw new FacebookException("Cannot share SharePhotoContent via web sharing dialogs");
        }

        public void validate(ShareVideoContent videoContent) throws  {
            throw new FacebookException("Cannot share ShareVideoContent via web sharing dialogs");
        }

        public void validate(SharePhoto $r1) throws  {
            ShareContentValidation.validatePhotoForWebDialog($r1, this);
        }
    }

    public static void validateForMessage(ShareContent $r0) throws  {
        validate($r0, getDefaultValidator());
    }

    public static void validateForNativeShare(ShareContent $r0) throws  {
        validate($r0, getDefaultValidator());
    }

    public static void validateForWebShare(ShareContent $r0) throws  {
        validate($r0, getWebShareValidator());
    }

    public static void validateForApiShare(ShareContent $r0) throws  {
        validate($r0, getApiValidator());
    }

    private static Validator getDefaultValidator() throws  {
        if (DefaultValidator == null) {
            DefaultValidator = new Validator();
        }
        return DefaultValidator;
    }

    private static Validator getApiValidator() throws  {
        if (ApiValidator == null) {
            ApiValidator = new ApiValidator();
        }
        return ApiValidator;
    }

    private static Validator getWebShareValidator() throws  {
        if (WebShareValidator == null) {
            WebShareValidator = new WebShareValidator();
        }
        return WebShareValidator;
    }

    private static void validate(ShareContent $r1, Validator $r0) throws FacebookException {
        if ($r1 == null) {
            throw new FacebookException("Must provide non-null content to share");
        } else if ($r1 instanceof ShareLinkContent) {
            $r0.validate((ShareLinkContent) $r1);
        } else if ($r1 instanceof SharePhotoContent) {
            $r0.validate((SharePhotoContent) $r1);
        } else if ($r1 instanceof ShareVideoContent) {
            $r0.validate((ShareVideoContent) $r1);
        } else if ($r1 instanceof ShareOpenGraphContent) {
            $r0.validate((ShareOpenGraphContent) $r1);
        }
    }

    private static void validateLinkContent(ShareLinkContent $r0, Validator validator) throws  {
        Uri $r2 = $r0.getImageUrl();
        if ($r2 != null && !Utility.isWebUri($r2)) {
            throw new FacebookException("Image Url must be an http:// or https:// url");
        }
    }

    private static void validatePhotoContent(SharePhotoContent $r0, Validator $r1) throws  {
        List<SharePhoto> $r2 = $r0.getPhotos();
        if ($r2 == null || $r2.isEmpty()) {
            throw new FacebookException("Must specify at least one Photo in SharePhotoContent.");
        } else if ($r2.size() > 6) {
            throw new FacebookException(String.format(Locale.ROOT, "Cannot add more than %d photos.", new Object[]{Integer.valueOf(6)}));
        } else {
            for (SharePhoto validate : $r2) {
                $r1.validate(validate);
            }
        }
    }

    private static void validatePhotoForApi(SharePhoto $r0, Validator $r1) throws  {
        if ($r0 == null) {
            throw new FacebookException("Cannot share a null SharePhoto");
        }
        Bitmap $r3 = $r0.getBitmap();
        Uri $r4 = $r0.getImageUrl();
        if ($r3 != null) {
            return;
        }
        if ($r4 == null) {
            throw new FacebookException("SharePhoto does not have a Bitmap or ImageUrl specified");
        } else if (Utility.isWebUri($r4) && !$r1.isOpenGraphContent()) {
            throw new FacebookException("Cannot set the ImageUrl of a SharePhoto to the Uri of an image on the web when sharing SharePhotoContent");
        }
    }

    private static void validatePhotoForNativeDialog(SharePhoto $r0, Validator $r1) throws  {
        validatePhotoForApi($r0, $r1);
        if ($r0.getBitmap() != null || !Utility.isWebUri($r0.getImageUrl())) {
            Validate.hasContentProvider(FacebookSdk.getApplicationContext());
        }
    }

    private static void validatePhotoForWebDialog(SharePhoto $r0, Validator validator) throws  {
        if ($r0 == null) {
            throw new FacebookException("Cannot share a null SharePhoto");
        }
        Uri $r3 = $r0.getImageUrl();
        if ($r3 == null || !Utility.isWebUri($r3)) {
            throw new FacebookException("SharePhoto must have a non-null imageUrl set to the Uri of an image on the web");
        }
    }

    private static void validateVideoContent(ShareVideoContent $r0, Validator $r1) throws  {
        $r1.validate($r0.getVideo());
        SharePhoto $r3 = $r0.getPreviewPhoto();
        if ($r3 != null) {
            $r1.validate($r3);
        }
    }

    private static void validateVideo(ShareVideo $r0, Validator validator) throws  {
        if ($r0 == null) {
            throw new FacebookException("Cannot share a null ShareVideo");
        }
        Uri $r3 = $r0.getLocalUrl();
        if ($r3 == null) {
            throw new FacebookException("ShareVideo does not have a LocalUrl specified");
        } else if (!Utility.isContentUri($r3) && !Utility.isFileUri($r3)) {
            throw new FacebookException("ShareVideo must reference a video that is on the device");
        }
    }

    private static void validateOpenGraphContent(ShareOpenGraphContent $r0, Validator $r1) throws  {
        $r1.validate($r0.getAction());
        String $r3 = $r0.getPreviewPropertyName();
        if (Utility.isNullOrEmpty($r3)) {
            throw new FacebookException("Must specify a previewPropertyName.");
        } else if ($r0.getAction().get($r3) == null) {
            throw new FacebookException("Property \"" + $r3 + "\" was not found on the action. " + "The name of the preview property must match the name of an " + "action property.");
        }
    }

    private static void validateOpenGraphAction(ShareOpenGraphAction $r0, Validator $r1) throws  {
        if ($r0 == null) {
            throw new FacebookException("Must specify a non-null ShareOpenGraphAction");
        } else if (Utility.isNullOrEmpty($r0.getActionType())) {
            throw new FacebookException("ShareOpenGraphAction must have a non-empty actionType");
        } else {
            $r1.validate($r0, false);
        }
    }

    private static void validateOpenGraphObject(ShareOpenGraphObject $r0, Validator $r1) throws  {
        if ($r0 == null) {
            throw new FacebookException("Cannot share a null ShareOpenGraphObject");
        }
        $r1.validate($r0, true);
    }

    private static void validateOpenGraphValueContainer(ShareOpenGraphValueContainer $r0, Validator $r1, boolean $z0) throws  {
        for (String $r5 : $r0.keySet()) {
            validateOpenGraphKey($r5, $z0);
            Object $r4 = $r0.get($r5);
            if ($r4 instanceof List) {
                for (Object $r42 : (List) $r42) {
                    if ($r42 == null) {
                        throw new FacebookException("Cannot put null objects in Lists in ShareOpenGraphObjects and ShareOpenGraphActions");
                    }
                    validateOpenGraphValueContainerObject($r42, $r1);
                }
                continue;
            } else {
                validateOpenGraphValueContainerObject($r42, $r1);
            }
        }
    }

    private static void validateOpenGraphKey(String $r0, boolean $z0) throws  {
        if ($z0) {
            String[] $r2 = $r0.split(":");
            if ($r2.length < 2) {
                throw new FacebookException("Open Graph keys must be namespaced: %s", $r0);
            }
            for (String $r1 : $r2) {
                if ($r1.isEmpty()) {
                    throw new FacebookException("Invalid key found in Open Graph dictionary: %s", $r0);
                }
            }
        }
    }

    private static void validateOpenGraphValueContainerObject(Object $r1, Validator $r0) throws  {
        if ($r1 instanceof ShareOpenGraphObject) {
            $r0.validate((ShareOpenGraphObject) $r1);
        } else if ($r1 instanceof SharePhoto) {
            $r0.validate((SharePhoto) $r1);
        }
    }
}
