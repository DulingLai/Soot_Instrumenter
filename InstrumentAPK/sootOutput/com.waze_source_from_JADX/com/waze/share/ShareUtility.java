package com.waze.share;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.View.OnAttachStateChangeListener;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import com.facebook.internal.NativeProtocol;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.RequestPermissionActivity;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.animation.easing.AnimationEasingManager;
import com.waze.animation.easing.AnimationEasingManager.EaseType;
import com.waze.autocomplete.Person;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolUserData;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.ifs.ui.TinyTooltip;
import com.waze.main.navigate.LocationData;
import com.waze.map.CanvasFont;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.DriveToNativeManager.LocationDataListener;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.social.FriendsListData;
import com.waze.navigate.social.ShareDriveActivity;
import com.waze.navigate.social.ShareHelpActivity;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.PhoneRequestAccessDialog;
import com.waze.phone.PhoneRequestAccessDialog.PhoneRequestAccessResultListener;
import com.waze.share.NameYourselfView.NameYourselfViewListener;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.utils.ImageRepository;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager$ImageRequestListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class ShareUtility {
    private static final int ID_SELECTED = 7003;
    private static final int SHARE_REQUEST_CODE = 74923;
    private static boolean bIsSupported = true;
    private static LocationData locationData;
    private static String mBody = null;
    private static String mTitle = null;
    private static int mType = 0;
    private static NativeManager nativeManager = null;
    private static String placeStr;

    static class C28574 implements IResultObj<CarpoolDrive> {
        C28574() {
        }

        public void onResult(CarpoolDrive res) {
            CarpoolUserData carpoolRider = res.getRider();
            String imageUrl = "";
            if (res != null) {
                ShareUtility.showInProgressError(carpoolRider.getImage());
            }
        }
    }

    static class C28617 implements IResultObj<CarpoolDrive> {
        C28617() {
        }

        public void onResult(CarpoolDrive res) {
            CarpoolUserData carpoolRider = res.getRider();
            String imageUrl = "";
            if (res != null) {
                ShareUtility.showInProgressError(carpoolRider.getImage());
            }
        }
    }

    public interface IFriendSelected {
        boolean onFriendSelected(Object obj, boolean z);
    }

    private static final class OnShareClick implements OnClickListener {
        boolean _isSelected;
        private final ActivityBase ctx;
        private final FrameLayout frame;
        private final Object friend;
        private final String name;
        private final IFriendSelected ofs;

        private OnShareClick(ActivityBase ctx, FrameLayout frame, Object friend, boolean isSelected, IFriendSelected ofs, String name) {
            this._isSelected = false;
            this.ctx = ctx;
            this.frame = frame;
            this.friend = friend;
            this.ofs = ofs;
            this.name = name;
            this._isSelected = isSelected;
            setSelected(frame);
        }

        public void onClick(View v) {
            boolean z;
            View selected = this.frame.findViewById(ShareUtility.ID_SELECTED);
            if (this.ofs != null) {
                if (!this.ofs.onFriendSelected(this.friend, !this._isSelected)) {
                    return;
                }
            }
            if (VERSION.SDK_INT < 19) {
                ShareUtility.bIsSupported = false;
            }
            Animation animationSet;
            AlphaAnimation alpha;
            if (this._isSelected) {
                selected.setVisibility(0);
                animationSet = new AnimationSet(true);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_TOGGLE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_OFF);
                animationSet.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                animationSet.addAnimation(new ScaleAnimation(1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR, 1, 0.2f));
                animationSet.setDuration(150);
                animationSet.setFillAfter(true);
                selected.startAnimation(animationSet);
                alpha = new AlphaAnimation(1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                alpha.setDuration(200);
                alpha.setFillAfter(false);
                final View view = v;
                alpha.setAnimationListener(new AnimationListener() {
                    public void onAnimationStart(Animation animation) {
                    }

                    public void onAnimationRepeat(Animation animation) {
                    }

                    public void onAnimationEnd(Animation animation) {
                        view.clearAnimation();
                        view.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                    }
                });
                v.setAlpha(1.0f);
                v.startAnimation(alpha);
            } else {
                selected.setVisibility(0);
                animationSet = new AnimationSet(true);
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_TOGGLE_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_EVENT_ON);
                animationSet.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                animationSet.addAnimation(new ScaleAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR, 1, 0.2f));
                animationSet.setDuration(300);
                animationSet.setFillAfter(true);
                animationSet.setInterpolator(AnimationEasingManager.getElasticInterpolator(EaseType.EaseOut, 0.0d, 0.0d));
                selected.startAnimation(animationSet);
                animationSet = new AnimationSet(false);
                alpha = new AlphaAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f);
                alpha.setDuration(200);
                alpha.setFillAfter(true);
                animationSet.addAnimation(alpha);
                ScaleAnimation scale = new ScaleAnimation(0.9f, 1.0f, 0.9f, 1.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                scale.setDuration(400);
                scale.setFillAfter(false);
                scale.setInterpolator(AnimationEasingManager.getElasticInterpolator(EaseType.EaseOut, 0.0d, 0.0d));
                animationSet.addAnimation(scale);
                v.setAlpha(1.0f);
                v.startAnimation(animationSet);
                if (ShareUtility.bIsSupported) {
                    TinyTooltip tinyTooltip = new TinyTooltip(this.ctx, ShareUtility.getShortened(this.name));
                    tinyTooltip.show(v);
                    final TinyTooltip tinyTooltip2 = tinyTooltip;
                    final Runnable action = new Runnable() {
                        public void run() {
                            if (tinyTooltip2 != null && tinyTooltip2.isShowing()) {
                                tinyTooltip2.dismiss();
                            }
                        }
                    };
                    v.postDelayed(action, 1000);
                    v.addOnAttachStateChangeListener(new OnAttachStateChangeListener() {
                        public void onViewDetachedFromWindow(View v) {
                            v.removeCallbacks(action);
                        }

                        public void onViewAttachedToWindow(View v) {
                        }
                    });
                }
            }
            if (this._isSelected) {
                z = false;
            } else {
                z = true;
            }
            this._isSelected = z;
        }

        private void setSelected(View v) {
            if (this._isSelected) {
                this.frame.findViewById(ShareUtility.ID_SELECTED).setVisibility(0);
                this.frame.setAlpha(1.0f);
                return;
            }
            this.frame.findViewById(ShareUtility.ID_SELECTED).setVisibility(4);
            this.frame.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        }
    }

    public enum ShareType {
        ShareType_ShareDrive,
        ShareType_PickUp,
        ShareType_ShareLocation,
        ShareType_ShareDestination,
        ShareType_ShareSelection
    }

    public static void OpenShareActivityOrLogin(ActivityBase ab, ShareType type, String Url, AddressItem addressItem, PersonBase[] Sharefriends) {
        final NameYourselfView[] viewRef = new NameYourselfView[1];
        final ActivityBase activityBase = ab;
        final ShareType shareType = type;
        final String str = Url;
        final AddressItem addressItem2 = addressItem;
        final PersonBase[] personBaseArr = Sharefriends;
        NameYourselfView.showNameYourselfViewIfNeeded(ab, viewRef, new NameYourselfViewListener() {
            public void onNameYourselfClosed(boolean nameWasChanged) {
                if (nameWasChanged) {
                    ShareUtility.openShareOrLoginWithUserInfo(activityBase, shareType, str, addressItem2, personBaseArr);
                }
                if (AppService.getActiveActivity() == AppService.getMainActivity()) {
                    AppService.getMainActivity().setNameYourselfView(null);
                }
            }
        }, new Runnable() {
            public void run() {
                if (AppService.getActiveActivity() == AppService.getMainActivity()) {
                    AppService.getMainActivity().setNameYourselfView(viewRef[0]);
                }
            }
        });
    }

    private static void openShareOrLoginWithUserInfo(ActivityBase ab, ShareType type, String Url, AddressItem addressItem, PersonBase[] Sharefriends) {
        if (!MyWazeNativeManager.getInstance().isShareAllowedNTV()) {
            Intent intent = new Intent(ab, PhoneRegisterActivity.class);
            intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
            intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
            ab.startActivity(intent);
        } else if (NativeManager.getInstance().IsAccessToContactsEnableNTV()) {
            OpenShareActivity(ab, type, Url, addressItem, Sharefriends);
        } else {
            final ActivityBase activityBase = ab;
            final ShareType shareType = type;
            final String str = Url;
            final AddressItem addressItem2 = addressItem;
            final PersonBase[] personBaseArr = Sharefriends;
            new PhoneRequestAccessDialog(ab, new PhoneRequestAccessResultListener() {
                public void onResult(boolean okToAccess) {
                    if (okToAccess) {
                        ShareUtility.OpenShareActivityOrLogin(activityBase, shareType, str, addressItem2, personBaseArr);
                    }
                }
            }).show();
        }
    }

    public static void OpenShareActivity(ActivityBase ab, ShareType type, String Url, AddressItem addressItem, PersonBase[] Sharefriends) {
        locationData = null;
        if (type == ShareType.ShareType_ShareDrive) {
            CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
            if (cpnm.isCarpoolShareOnly()) {
                String curMeetingId = cpnm.getCurMeetingIdNTV();
                if (curMeetingId == null || curMeetingId.isEmpty()) {
                    showInProgressError(null);
                    return;
                } else {
                    cpnm.getDriveInfoByMeetingId(curMeetingId, new C28574());
                    return;
                }
            }
        }
        if (addressItem != null) {
            placeStr = addressItem.getTitle();
            if (placeStr == null || placeStr.equals("")) {
                placeStr = addressItem.getAddress();
            }
        }
        if (placeStr == null) {
            placeStr = "";
        }
        nativeManager = AppService.getNativeManager();
        int locationX = 0;
        int locationY = 0;
        if (!(type == ShareType.ShareType_ShareDrive || type == ShareType.ShareType_PickUp || addressItem == null)) {
            locationX = addressItem.getLocationX().intValue();
            locationY = addressItem.getLocationY().intValue();
        }
        int nType = 2;
        if (type == ShareType.ShareType_ShareDestination || type == ShareType.ShareType_PickUp || type == ShareType.ShareType_ShareDrive) {
            nType = 1;
        } else if (type == ShareType.ShareType_ShareSelection) {
            nType = 3;
        }
        mType = nType;
        final Intent intent = new Intent(AppService.getAppContext(), ShareDriveActivity.class);
        if (type == ShareType.ShareType_ShareDrive) {
            intent.putExtra("type", 0);
        } else {
            intent.putExtra("type", 1);
        }
        DriveToNativeManager driveToNativeManager = DriveToNativeManager.getInstance();
        String nIndex = null;
        if (addressItem != null) {
            nIndex = addressItem.getId();
        }
        final AddressItem addressItem2 = addressItem;
        final String str = Url;
        final ShareType shareType = type;
        final PersonBase[] personBaseArr = Sharefriends;
        driveToNativeManager.getLocationData(nType, Integer.valueOf(locationX), Integer.valueOf(locationY), new LocationDataListener() {

            class C28581 implements FriendsListListener {
                C28581() {
                }

                public void onComplete(FriendsListData aData) {
                    for (FriendUserData fud : aData.friends) {
                        if (fud.mContactID != -1) {
                            Person p = AddressBookImpl.getInstance().GetPersonFromID(fud.mContactID);
                            if (!(p == null || p.getImage() == null)) {
                                fud.setImage(p.getImage());
                            }
                        }
                    }
                    if (personBaseArr != null) {
                        intent.putExtra(PublicMacros.FriendUserDataList, new ArrayList(Arrays.asList(personBaseArr)));
                        intent.putExtra("selected", true);
                        intent.getSerializableExtra(PublicMacros.FriendUserDataList);
                    } else {
                        intent.putExtra(PublicMacros.FriendUserDataList, new ArrayList(Arrays.asList(aData.friends)));
                    }
                    ShareUtility.requestContactPermissionBeforeActivity(AppService.getActiveActivity(), intent, 1);
                }
            }

            public void onComplete(LocationData lData) {
                ShareUtility.locationData = lData;
                if (ShareUtility.locationData != null) {
                    if (!((ShareUtility.locationData.mVenueId != null && !ShareUtility.locationData.mVenueId.isEmpty()) || addressItem2 == null || addressItem2.VanueID == null)) {
                        ShareUtility.locationData.mVenueId = addressItem2.VanueID;
                    }
                    if (!(ShareUtility.mType != 3 || addressItem2 == null || addressItem2.getTitle().isEmpty())) {
                        ShareUtility.locationData.locationName = addressItem2.getTitle();
                    }
                    intent.putExtra("url", str);
                    intent.putExtra("share_type", ShareUtility.mType);
                    intent.putExtra(PublicMacros.LOCATION_DATA, ShareUtility.locationData);
                    intent.putExtra(PublicMacros.ADDRESS_ITEM, addressItem2);
                    if (shareType == ShareType.ShareType_ShareDrive) {
                        if (personBaseArr != null) {
                            intent.putExtra(PublicMacros.FriendUserDataList, new ArrayList(Arrays.asList(personBaseArr)));
                            intent.putExtra("selected", true);
                        } else if (!(AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null || AppService.getMainActivity().getLayoutMgr().getNotifyFriends() == null)) {
                            FriendUserData[] Friends = AppService.getMainActivity().getLayoutMgr().getNotifyFriends();
                            for (FriendUserData fud : Friends) {
                                if (fud.mContactID != -1) {
                                    Person p = AddressBookImpl.getInstance().GetPersonFromID(fud.mContactID);
                                    if (!(p == null || p.getImage() == null)) {
                                        fud.setImage(p.getImage());
                                    }
                                }
                            }
                            intent.putExtra(PublicMacros.FriendUserDataList, new ArrayList(Arrays.asList(Friends)));
                            intent.putExtra("selected", false);
                        }
                        ShareUtility.requestContactPermissionBeforeActivity(AppService.getActiveActivity(), intent, 1);
                        return;
                    }
                    DriveToNativeManager.getInstance().getShareFriendsListData(ShareUtility.locationData.locationX, ShareUtility.locationData.locationY, new C28581());
                }
            }
        }, nIndex);
    }

    private static void requestContactPermissionBeforeActivity(ActivityBase activity, Intent intent, int result) {
        if (ContextCompat.checkSelfPermission(activity, "android.permission.READ_CONTACTS") != 0) {
            Intent permissionsIntent = new Intent(activity, RequestPermissionActivity.class);
            permissionsIntent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, new String[]{"android.permission.READ_CONTACTS"});
            permissionsIntent.putExtra(RequestPermissionActivity.INT_ON_GRANTED, intent);
            activity.startActivityForResult(permissionsIntent, result);
            return;
        }
        activity.startActivityForResult(intent, result);
    }

    public static View buildShareComponentFriendButton(ActivityBase ctx, String name, String imageUri, boolean isSelected, boolean isPermanent, Object friend, IFriendSelected ofs) {
        FrameLayout frame = new FrameLayout(ctx);
        int dimen = (int) (ctx.getResources().getDisplayMetrics().density * 36.0f);
        LayoutParams params = new LayoutParams(dimen, dimen);
        params.gravity = 17;
        ImageView iv = new ImageView(ctx);
        iv.setImageResource(C1283R.drawable.share_nopicture_back);
        frame.addView(iv, params);
        TextView tv = new TextView(ctx);
        tv.setText(getInitials(name));
        tv.setTextColor(-1);
        tv.setGravity(17);
        frame.addView(tv, params);
        if (imageUri != null && imageUri.length() > 0) {
            iv = new ImageView(ctx);
            ImageRepository.instance.getImage(imageUri, 1, iv, null, ctx);
            iv.setScaleType(ScaleType.CENTER_CROP);
            frame.addView(iv, params);
        }
        iv = new ImageView(ctx);
        iv.setImageResource(C1283R.drawable.share_frame_reg);
        frame.addView(iv);
        iv = new ImageView(ctx);
        iv.setImageResource(isPermanent ? C1283R.drawable.share_frame_sharing : C1283R.drawable.share_frame_selected);
        iv.setId(ID_SELECTED);
        frame.addView(iv);
        if (!isPermanent) {
            frame.setOnClickListener(new OnShareClick(ctx, frame, friend, isSelected, ofs, name));
        }
        return frame;
    }

    public static String getInitials(String name) {
        if (name.startsWith("+") && name.length() <= 3) {
            return name;
        }
        String initials = "";
        for (String tok : name.split(" ")) {
            if (!(tok == null || tok.length() == 0 || !Character.isLetter(tok.charAt(0)))) {
                initials = initials + tok.substring(0, 1).toUpperCase(Locale.US);
            }
        }
        if (initials.length() > 3) {
            initials = initials.substring(0, 3);
        }
        return initials;
    }

    public static String getShortened(String name) {
        if (name.length() <= 6) {
            return name;
        }
        boolean first = true;
        String shortened = "";
        for (String tok : name.split(" ")) {
            if (!(tok == null || tok.length() == 0)) {
                if (first) {
                    first = false;
                    shortened = shortened + tok;
                } else {
                    shortened = (shortened + " ") + tok.substring(0, 1);
                }
            }
        }
        return shortened;
    }

    public static String getFirstName(String name) {
        name = name.trim();
        int end = name.indexOf(32);
        return end < 0 ? name : name.substring(0, end).trim();
    }

    public static void BuildShareStrings(final ShareType type, final String Url, AddressItem addressItem) {
        locationData = null;
        if (addressItem != null) {
            placeStr = addressItem.getTitle();
            if (placeStr == null || placeStr.equals("")) {
                placeStr = addressItem.getAddress();
            }
        }
        if (placeStr == null) {
            placeStr = "";
        }
        nativeManager = AppService.getNativeManager();
        int locationX = 0;
        int locationY = 0;
        if (!(type == ShareType.ShareType_ShareDrive || type == ShareType.ShareType_PickUp || addressItem == null)) {
            locationX = addressItem.getLocationX().intValue();
            locationY = addressItem.getLocationY().intValue();
        }
        int nType = 2;
        if (type == ShareType.ShareType_ShareDestination || type == ShareType.ShareType_PickUp || type == ShareType.ShareType_ShareDrive) {
            nType = 1;
        } else if (type == ShareType.ShareType_ShareSelection) {
            nType = 3;
        }
        mType = nType;
        DriveToNativeManager driveToNativeManager = DriveToNativeManager.getInstance();
        String nIndex = null;
        if (addressItem != null) {
            nIndex = addressItem.getId();
        }
        mTitle = "";
        mBody = "";
        driveToNativeManager.getLocationData(nType, Integer.valueOf(locationX), Integer.valueOf(locationY), new LocationDataListener() {
            public void onComplete(LocationData lData) {
                ShareUtility.locationData = lData;
                if (ShareUtility.locationData != null) {
                    if (type != ShareType.ShareType_PickUp) {
                        String destination = ShareUtility.placeStr;
                        if (ShareUtility.placeStr == null || ShareUtility.placeStr.isEmpty()) {
                            destination = NativeManager.getInstance().getLanguageString(ShareUtility.locationData.destinationName);
                        }
                        if (ShareUtility.mType == 1) {
                            if (destination.equalsIgnoreCase(DisplayStrings.displayString(287)) || destination.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_HOME))) {
                                destination = DisplayStrings.displayString(DisplayStrings.DS_TO_HOME);
                            } else if (destination.equalsIgnoreCase(DisplayStrings.displayString(288)) || destination.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_WORK))) {
                                destination = DisplayStrings.displayString(DisplayStrings.DS_TO_WORK);
                            } else {
                                destination = DisplayStrings.displayStringF(DisplayStrings.DS_TO_LOCATION_PS, destination);
                            }
                        } else if (ShareUtility.mType == 2) {
                            if (destination.equalsIgnoreCase(DisplayStrings.displayString(287)) || destination.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_HOME))) {
                                destination = DisplayStrings.displayString(287);
                            } else if (destination.equalsIgnoreCase(DisplayStrings.displayString(288)) || destination.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_WORK))) {
                                destination = DisplayStrings.displayString(288);
                            }
                        }
                        String url = Url;
                        if (url == null || Url.isEmpty()) {
                            url = ShareUtility.locationData.smsLocationUrlPrefix + "/h" + ShareUtility.locationData.locationHash;
                        }
                        if (ShareUtility.mType == 1) {
                            ShareUtility.mTitle = DisplayStrings.displayStringF(DisplayStrings.DS_SEND_DRIVE_EMAIL_SUBJECT_PS, destination);
                            ShareUtility.mBody = DisplayStrings.displayStringF(DisplayStrings.DS_SEND_DRIVE_EMAIL_BODY_PS_PS_PS_PS, destination, ShareUtility.locationData.locationEta, url, ShareUtility.locationData.downloadUrl);
                        } else if (ShareUtility.mType == 2 || ShareUtility.mType == 3) {
                            ShareUtility.mTitle = DisplayStrings.displayString(DisplayStrings.DS_SEND_LOCATION_EMAIL_SUBJECT);
                            ShareUtility.mBody = DisplayStrings.displayStringF(DisplayStrings.DS_SEND_LOCATION_EMAIL_BODY_PS_PS_PS, destination, url, ShareUtility.locationData.downloadUrl);
                        } else if (type == ShareType.ShareType_ShareDrive) {
                            ShareUtility.mTitle = DisplayStrings.displayStringF(DisplayStrings.DS_SEND_DRIVE_EMAIL_SUBJECT_PS, destination);
                            ShareUtility.mBody = DisplayStrings.displayStringF(DisplayStrings.DS_SEND_DRIVE_EMAIL_BODY_PS_PS_PS_PS, destination, ShareUtility.locationData.locationEta, url, ShareUtility.locationData.downloadUrl);
                        }
                    }
                    if (AppService.getActiveActivity() instanceof ShareDriveActivity) {
                        ((ShareDriveActivity) AppService.getActiveActivity()).SendURL(ShareUtility.mTitle, ShareUtility.mBody);
                    }
                }
            }
        }, nIndex);
    }

    public static void shareLocationOrDrive(final ActivityBase a, final int type) {
        final NativeManager nm = NativeManager.getInstance();
        if (type == 16) {
            CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
            if (cpnm.isCarpoolShareOnly()) {
                String curMeetingId = cpnm.getCurMeetingIdNTV();
                if (curMeetingId == null || curMeetingId.isEmpty()) {
                    showInProgressError(null);
                    return;
                } else {
                    cpnm.getDriveInfoByMeetingId(curMeetingId, new C28617());
                    return;
                }
            }
        }
        if (!MyWazeNativeManager.getInstance().isShareAllowedNTV()) {
            Intent intent = new Intent(a, PhoneRegisterActivity.class);
            intent.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
            intent.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
            AppService.getMainActivity().startActivityForResult(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY | type);
        } else if (nm.IsAccessToContactsEnableNTV() || (type & 32) != 0) {
            doShareAllowed(a, type, nm);
        } else {
            new PhoneRequestAccessDialog(a, new PhoneRequestAccessResultListener() {
                public void onResult(boolean okToAccess) {
                    if (okToAccess) {
                        ShareUtility.doShareAllowed(a, type, nm);
                    }
                }
            }).show();
        }
    }

    private static void showInProgressError(String imageUrl) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_SHARE_DRIVE_BLOCKED_NOTIFICATION);
        final Dialog dlg = MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_IN_PROG_TITLE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_IN_PROG_CONTENT), true, null, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_IN_PROG_BUTTON), null, -1);
        if (imageUrl != null) {
            VolleyManager.getInstance().loadImageFromUrl(imageUrl, new VolleyManager$ImageRequestListener() {
                public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) {
                    ((ImageView) dlg.findViewById(C1283R.id.confirmImage)).setImageDrawable(new CircleShaderDrawable(bitmap, 0));
                    dlg.findViewById(C1283R.id.confirmImageContainer).setVisibility(0);
                    dlg.show();
                }

                public void onImageLoadFailed(Object token, long duration) {
                    dlg.show();
                }
            });
        } else {
            dlg.show();
        }
    }

    private static void doShareAllowed(final ActivityBase a, int type, NativeManager nm) {
        if ((type & 16) != 0) {
            if (NativeManager.getInstance().isNavigatingNTV()) {
                OpenShareActivity(a, ShareType.ShareType_ShareDrive, null, null, null);
            } else {
                a.startActivityForResult(new Intent(a, ShareHelpActivity.class), 1);
            }
        } else if ((type & 32) != 0) {
            a.startActivityForResult(new Intent(a, ShareActivity.class), 0);
        } else if ((type & 64) != 0) {
            OpenShareActivity(a, ShareType.ShareType_ShareLocation, null, null, null);
        } else if ((type & 128) != 0) {
            DriveToNativeManager.getInstance().getHome(new DriveToGetAddressItemArrayCallback() {
                public void getAddressItemArrayCallback(AddressItem[] ai) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "HOME");
                    if (ai != null) {
                        ShareUtility.OpenShareActivity(a, ShareType.ShareType_ShareSelection, null, ai[0], null);
                    }
                }
            });
        } else if ((type & 256) != 0) {
            DriveToNativeManager.getInstance().getWork(new DriveToGetAddressItemArrayCallback() {
                public void getAddressItemArrayCallback(AddressItem[] ai) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_OF, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "WORK");
                    if (ai != null) {
                        ShareUtility.OpenShareActivity(a, ShareType.ShareType_ShareSelection, null, ai[0], null);
                    }
                }
            });
        }
    }
}
