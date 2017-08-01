package com.waze.navigate;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.DecelerateInterpolator;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.facebook.share.internal.ShareConstants;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.DownloadResCallback;
import com.waze.LocationFactory;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.NativeManager.AdsActiveContext;
import com.waze.ResManager;
import com.waze.WzWebView;
import com.waze.WzWebView.WebViewPageProgressCallback;
import com.waze.WzWebView.WebViewUrlOverride;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.ifs.ui.EditTextDialogActivity;
import com.waze.ifs.ui.ObservableScrollView;
import com.waze.ifs.ui.ObservableScrollView.OnScrollListener;
import com.waze.ifs.ui.OmniSelectionActivity;
import com.waze.ifs.ui.SimpleChoiceActivity;
import com.waze.ifs.ui.TinyTooltip;
import com.waze.ifs.ui.UserTooltipView;
import com.waze.map.MapView;
import com.waze.map.MapViewWrapper;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.AddressItem.AdvertiserData;
import com.waze.navigate.DriveToNativeManager.ProductListener;
import com.waze.phone.PhoneNumberSignInActivity;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.phone.PhoneRequestAccessDialog;
import com.waze.phone.PhoneRequestAccessDialog.PhoneRequestAccessResultListener;
import com.waze.reports.EditPlaceFlowActivity;
import com.waze.reports.EditPlaceServicesFragment;
import com.waze.reports.OpeningHours;
import com.waze.reports.PhotoPagerSection;
import com.waze.reports.PhotoPagerSection.VenueImage;
import com.waze.reports.PlacePhotoGallery;
import com.waze.reports.PlacePhotoGallery.IPhotoGalleryListener;
import com.waze.reports.UpdatePriceActivity;
import com.waze.reports.VenueData;
import com.waze.settings.SettingsNativeManager;
import com.waze.settings.SettingsValue;
import com.waze.share.FacebookEventPostActivity;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.social.facebook.FacebookManager;
import com.waze.social.facebook.FacebookManager.FacebookLoginListener;
import com.waze.social.facebook.FacebookManager.FacebookLoginType;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.PixelMeasure;
import com.waze.view.drawables.ShadowedRectDrawable;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.AutoResizeTextView;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import org.json.JSONObject;

public final class AddressPreviewActivity extends ActivityBase implements DriveToNavigateCallback {
    public static final String COMMUTE_MODE = "commute_mode";
    private static final long MINIMUM_DISPLAY_TIME = 2000;
    public static final String OPEN_SET_LOCATION = "open_set_location";
    public static final int RQ_CALENDAR_COMPLETION = 113;
    private static final int RQ_EDIT_PLACE = 109;
    private static final int RQ_FLAG_DUPLICATE_FIND = 107;
    private static final int RQ_FLAG_MOVED_CLOSED = 108;
    private static final int RQ_FLAG_WRONG = 106;
    private static final int RQ_MISC = 333;
    private static final int RQ_REGISTER = 111;
    public static final int SERVICE_WIDTH_DP = 60;
    private static final int WEBVIEW_RQ_SET_OFFER = 1;
    private float LS_X_FACTOR = 12.6f;
    private float LS_Y_FACTOR = 6.3f;
    private OnClickListener calendarRemoveListener;
    private OnClickListener calendarSetLocationListener;
    private CancelTooltipRunnable cancelTooltipAction = null;
    private boolean mAboutTextIsOpen = false;
    private AddressItem mAddressItem;
    private ArrayList<AddressItem> mAddressItemList;
    private AdsActiveContext mAdsCtx;
    private String mAnalyticsType = "UNKNOWN";
    private View mBackToMapButton;
    private View mBackUpButton;
    private int mButtonShadowGoneHeight;
    private View mDangerZoneWarning;
    private float mDensity;
    private DriveToNativeManager mDtnMgr;
    private boolean mEdit;
    private AddressItem mEventAddressItem = null;
    private int mFlagType;
    private int mFrameHeight;
    private PlacePhotoGallery mGallery = null;
    private ArrayList<VenueImage> mImageArray;
    private boolean mIsAdditionalAddToFavorites;
    private boolean mIsClearAdsContext = false;
    private boolean mIsGasStation;
    private boolean mIsMapBig = false;
    private View mLandcapeScrollContainer;
    private AnimationListener mLandscapeAnimateMapClosedListener = new C20802();
    private AnimationListener mLandscapeAnimateMapOpenListener = new C20781();
    private int mLockHeight;
    private ImageView mLogoImg;
    private int mLogoResource = -1;
    private int mMapCovered;
    private ViewGroup mMapLayout;
    private View mMapMask;
    private final RunnableExecutor mMapReadyCallback = new RunnableExecutor() {
        public void event() throws  {
            int $i0;
            int $i1;
            if (AddressPreviewActivity.this.getResources().getConfiguration().orientation == 1) {
                $i0 = AddressPreviewActivity.this.mAddressItem.getLocationX().intValue();
                $i1 = AddressPreviewActivity.this.mAddressItem.getLocationY().intValue() - ((int) (AddressPreviewActivity.this.LS_Y_FACTOR * ((float) AddressPreviewActivity.this.mLockHeight)));
            } else {
                $i0 = AddressPreviewActivity.this.mAddressItem.getLocationX().intValue() + ((int) (((float) AddressPreviewActivity.this.mMapCovered) * AddressPreviewActivity.this.LS_X_FACTOR));
                $i1 = AddressPreviewActivity.this.mAddressItem.getLocationY().intValue();
            }
            AddressPreviewActivity.this.mNavNtvMgr.PreviewCanvasFocusOn($i0, $i1, 2000);
        }
    };
    private MapView mMapView;
    private NativeManager mNatMgr;
    private NavigateNativeManager mNavNtvMgr;
    private OnClickListener mOnSaveEventLocation;
    private int mParkingDistance = 0;
    private int mParkingETA = -1;
    private boolean mParkingMode = false;
    private boolean mParkingPopular = false;
    private VenueData mParkingVenue = null;
    private View mPlaceImagesFrame;
    private ObservableScrollView mScrollView;
    private int mSelectedItem = 0;
    private View[] mServicesRows = null;
    private boolean mShouldLoadVenue = false;
    private final WebViewUrlOverride mSpecialUrlOverride = new WebViewUrlOverride() {

        class C20912 implements AnimatorUpdateListener {
            C20912() throws  {
            }

            public void onAnimationUpdate(ValueAnimator $r1) throws  {
                int $i0 = ((Integer) $r1.getAnimatedValue()).intValue();
                LayoutParams $r7 = AddressPreviewActivity.this.mWebView.getLayoutParams();
                $r7.height = $i0;
                AddressPreviewActivity.this.mWebView.setLayoutParams($r7);
            }
        }

        public boolean onUrlOverride(WebView $r1, String $r4) throws  {
            if ($r4 != null) {
                if ($r4.startsWith(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX)) {
                    AddressPreviewActivity.this.logAnalyticsAds(AnalyticsEvents.ANALYTICS_EVENT_ADS_SPECIAL_PHONE_CLICKED);
                    String str = "android.intent.action.DIAL";
                    final Intent $r3 = new Intent(str, Uri.parse($r4.substring($r4.indexOf(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX), $r4.length())));
                    AddressPreviewActivity.this.runOnUiThread(new Runnable() {
                        public void run() throws  {
                            AddressPreviewActivity.this.startActivity($r3);
                        }
                    });
                    return true;
                }
                if ($r4.startsWith(AnalyticsEvents.ANALYTICS_ADS_URL_PREFIX)) {
                    AddressPreviewActivity.this.logAnalyticsAds(AnalyticsEvents.ANALYTICS_EVENT_ADS_PREVIEW_OFFER_URL_CLICKED);
                } else {
                    if ($r4.startsWith(NativeManager.WAZE_URL_PATTERN)) {
                        NativeManager $r8 = AppService.getNativeManager();
                        String $r12;
                        if ($r4.contains("brand_opt_in")) {
                            MyWazeNativeManager.getInstance().addStoreByBrandId(AddressPreviewActivity.this.mAddressItem.brandId);
                            $r8 = NativeManager.getInstance();
                            String $r42 = AddressPreviewActivity.this.mAddressItem.venueData;
                            String $r11 = $r42;
                            $r4 = $r42.id;
                            $r12 = AddressPreviewActivity.this.mAddressItem.getTitle();
                            String $r13 = AddressPreviewActivity.this.mAddressItem.getStreet();
                            String $r14 = AddressPreviewActivity.this.mAddressItem.getCity();
                            String $r15 = AddressPreviewActivity.this.mAddressItem.mImageURL;
                            $r42 = AddressPreviewActivity.this.mAddressItem.venueData;
                            $r11 = $r42;
                            $r8.addPlaceToRecent($r4, $r12, $r13, $r14, $r15, $r42.context);
                            return true;
                        }
                        if ($r4.contains("brand_opt_out")) {
                            MyWazeNativeManager.getInstance().removeStoreByBrandId(AddressPreviewActivity.this.mAddressItem.brandId);
                            return true;
                        }
                        try {
                            $r12 = Uri.parse(URLDecoder.decode($r4.toString(), "UTF-8")).getQueryParameter("change_info_height");
                            if ($r12 != null) {
                                int $i0 = Integer.parseInt($r12);
                                int[] $r19 = new int[2];
                                $r19[0] = AddressPreviewActivity.this.mWebView.getLayoutParams().height;
                                float $f0 = ((float) $i0) * AddressPreviewActivity.this.mDensity;
                                float f = $f0;
                                $r19[1] = (int) $f0;
                                ValueAnimator $r20 = ValueAnimator.ofInt($r19);
                                $r20.addUpdateListener(new C20912());
                                $r20.setDuration(300);
                                $r20.setInterpolator(new DecelerateInterpolator());
                                $r20.start();
                                return true;
                            }
                            if (!$r8.UrlHandler($r4, true)) {
                                $r1.loadUrl($r4);
                            }
                            return true;
                        } catch (UnsupportedEncodingException e) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    };
    private TitleBar mTitleBar;
    private int mTitleBarHeight;
    private View mTitleBarShadow;
    private boolean mWasResultSet = false;
    private WzWebView mWebView;
    private ProgressBar mWebViewLoadAnimation;
    private boolean mbAddressOptionsViewSet = false;
    private long parkingRequestTime;
    private AddressItem parkingSuggestionAddressItem;
    private int parkingSuggestionDistance;
    private boolean parkingSuggestionLoaded = false;
    private boolean parkingSuggestionMore;
    private boolean parkingSuggestionPopular;
    private boolean shownParkingTooltip;
    private TinyTooltip shownTooltip = null;

    class AnonymousClass16 implements OnClickListener {
        final /* synthetic */ String val$eventAddressItemId;

        class C20741 implements FacebookLoginListener {
            C20741() throws  {
            }

            public void onFacebookLoginResult(boolean $z0) throws  {
                if ($z0) {
                    Intent $r1 = new Intent(AddressPreviewActivity.this, FacebookEventPostActivity.class);
                    $r1.putExtra(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, AddressPreviewActivity.this.mNatMgr.getLanguageString((int) DisplayStrings.DS_POST_EVENT_WALL_TEXT));
                    $r1.putExtra("link", AddressPreviewActivity.this.mNatMgr.getNavLink(AddressPreviewActivity.this.mAddressItem.getLocationY().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationX().intValue()));
                    $r1.putExtra("Id", AddressPreviewActivity.this.mEventAddressItem.getMeetingId());
                    AddressPreviewActivity.this.startActivityForResult($r1, 333);
                }
            }
        }

        AnonymousClass16(String $r2) throws  {
            this.val$eventAddressItemId = $r2;
        }

        public void onClick(View v) throws  {
            AddressPreviewActivity.this.mDtnMgr.VerifyEventByIndex(AddressPreviewActivity.this.mAddressItem.index, AddressPreviewActivity.this.mAddressItem.getMeetingId(), this.val$eventAddressItemId, Boolean.valueOf(false));
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VERIFY_EVENT_SAVE_POST);
            if (AddressPreviewActivity.this.mNatMgr.IsPublishStreamFbPermissionsNTV()) {
                Intent $r2 = new Intent(AddressPreviewActivity.this, FacebookEventPostActivity.class);
                $r2.putExtra(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, AddressPreviewActivity.this.mNatMgr.getLanguageString((int) DisplayStrings.DS_POST_EVENT_WALL_TEXT));
                $r2.putExtra("link", AddressPreviewActivity.this.mNatMgr.getNavLink(AddressPreviewActivity.this.mAddressItem.getLocationY().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationX().intValue()));
                $r2.putExtra("Id", AddressPreviewActivity.this.mEventAddressItem.getMeetingId());
                AddressPreviewActivity.this.startActivityForResult($r2, 333);
                return;
            }
            FacebookManager.getInstance().loginWithFacebook(AddressPreviewActivity.this, FacebookLoginType.PublishStream, false, new C20741());
        }
    }

    class AnonymousClass17 implements OnClickListener {
        final /* synthetic */ String val$eventAddressItemId;

        AnonymousClass17(String $r2) throws  {
            this.val$eventAddressItemId = $r2;
        }

        public void onClick(View v) throws  {
            AddressPreviewActivity.this.mDtnMgr.VerifyEventByIndex(AddressPreviewActivity.this.mAddressItem.index, AddressPreviewActivity.this.mAddressItem.getMeetingId(), this.val$eventAddressItemId, Boolean.valueOf(false));
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VERIFY_EVENT_SAVE_EVENT_LOCATION);
            Intent $r2 = new Intent();
            $r2.putExtra(PublicMacros.ADDRESS_ITEM, AddressPreviewActivity.this.mAddressItem);
            AddressPreviewActivity.this.setResult(MainActivity.VERIFY_EVENT_CODE, $r2);
            AddressPreviewActivity.this.mWasResultSet = true;
            AddressPreviewActivity.this.finish();
        }
    }

    class C20781 implements AnimationListener {
        C20781() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            RelativeLayout.LayoutParams $r5 = (RelativeLayout.LayoutParams) AddressPreviewActivity.this.mLandcapeScrollContainer.getLayoutParams();
            $r5.rightMargin = -AddressPreviewActivity.this.mMapCovered;
            AddressPreviewActivity.this.mLandcapeScrollContainer.setLayoutParams($r5);
            AddressPreviewActivity.this.mLandcapeScrollContainer.clearAnimation();
        }
    }

    class AnonymousClass22 implements WebViewPageProgressCallback {
        final /* synthetic */ AdvertiserData val$ad;

        AnonymousClass22(AdvertiserData $r2) throws  {
            this.val$ad = $r2;
        }

        public void onWebViewPageFinished() throws  {
            int $i0 = 0;
            int $i1 = 0;
            Location $r4 = LocationFactory.getInstance().getLastLocation();
            if ($r4 != null) {
                NativeLocation $r5 = LocationFactory.getNativeLocation($r4);
                $i0 = $r5.mLongtitude;
                $i1 = $r5.mLatitude;
            }
            String $r6 = "";
            if (AddressPreviewActivity.this.mAdsCtx != null) {
                $r6 = AddressPreviewActivity.this.mAdsCtx.event_info;
            }
            String $r11 = new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV()).toString();
            String $r10 = "";
            try {
                JSONObject $r1 = new JSONObject();
                try {
                    $r1.put("sessionid", AddressPreviewActivity.this.mNatMgr.getServerSessionId());
                    $r1.put("cookie", AddressPreviewActivity.this.mNatMgr.getServerCookie());
                    String $r13 = AddressPreviewActivity.this.mAddressItem.venueData;
                    String $r15 = $r13;
                    $r1.put("venue_context", $r13.context);
                    $r1.put("lon", $i0);
                    $r1.put("lat", $i1);
                    $r1.put("locale", $r11);
                    $r1.put("rtserver-id", AddressPreviewActivity.this.mNatMgr.getRTServerId());
                    $r1.put("source", $r6);
                    if (!(AddressPreviewActivity.this.mAddressItem == null || TextUtils.isEmpty(AddressPreviewActivity.this.mAddressItem.brandId))) {
                        $r1.put("opted_id", MyWazeNativeManager.getInstance().isBrandOptedIn(AddressPreviewActivity.this.mAddressItem.brandId));
                    }
                    $r10 = $r1.toString();
                } catch (Exception e) {
                }
            } catch (Exception e2) {
            }
            Object[] $r17 = new Object[2];
            AdvertiserData $r18 = this.val$ad;
            $r17[0] = $r18.query;
            $r17[1] = $r10;
            $r10 = String.format("W.setOffer(%s, %s)", $r17);
            AddressPreviewActivity $r7 = AddressPreviewActivity.this;
            AddressPreviewActivity $r19 = AddressPreviewActivity.this;
            $r7.callJavaScript($r19.mWebView, 1, $r10);
            AddressPreviewActivity.this.mWebViewLoadAnimation.setVisibility(8);
        }

        public void onWebViewPageStarted() throws  {
            AddressPreviewActivity.this.mWebViewLoadAnimation.setVisibility(0);
        }
    }

    class AnonymousClass23 implements OnClickListener {
        final /* synthetic */ String val$phoneNum;

        AnonymousClass23(String $r2) throws  {
            this.val$phoneNum = $r2;
        }

        public void onClick(View v) throws  {
            Logger.m36d("Opening dialer for: " + this.val$phoneNum);
            AddressPreviewActivity.this.logAnalyticsAds(AnalyticsEvents.ANALYTICS_EVENT_ADS_PREVIEW_PHONE_CLICKED);
            AddressPreviewActivity.this.reportClick("CALL");
            Intent $r2 = new Intent("android.intent.action.DIAL");
            $r2.setData(Uri.parse(AnalyticsEvents.ANALYTICS_ADS_PHONE_PREFIX + this.val$phoneNum));
            $r2.setFlags(268435456);
            AddressPreviewActivity.this.startActivity($r2);
        }
    }

    class AnonymousClass25 implements OnLayoutChangeListener {
        final /* synthetic */ View val$layout;
        final /* synthetic */ ImageView val$phoneImage;
        final /* synthetic */ TextView val$siteTitle;

        AnonymousClass25(View $r2, ImageView $r3, TextView $r4) throws  {
            this.val$layout = $r2;
            this.val$phoneImage = $r3;
            this.val$siteTitle = $r4;
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) throws  {
            this.val$layout.removeOnLayoutChangeListener(this);
            left = ((this.val$layout.getMeasuredWidth() / 2) - this.val$phoneImage.getMeasuredWidth()) - PixelMeasure.dp(43);
            LinearLayout.LayoutParams $r5 = (LinearLayout.LayoutParams) this.val$siteTitle.getLayoutParams();
            if (this.val$siteTitle.getMeasuredWidth() > left) {
                $r5.width = left;
                this.val$siteTitle.setLayoutParams($r5);
            }
        }
    }

    class AnonymousClass26 implements OnGlobalLayoutListener {
        final /* synthetic */ LinearLayout val$servicesGrid;

        AnonymousClass26(LinearLayout $r2) throws  {
            this.val$servicesGrid = $r2;
        }

        public void onGlobalLayout() throws  {
            this.val$servicesGrid.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            AddressPreviewActivity.this.setServices(this.val$servicesGrid);
        }
    }

    class AnonymousClass27 implements OnClickListener {
        final /* synthetic */ TextView val$aboutText;
        final /* synthetic */ TextView val$moreText;

        AnonymousClass27(TextView $r2, TextView $r3) throws  {
            this.val$aboutText = $r2;
            this.val$moreText = $r3;
        }

        public void onClick(View v) throws  {
            AddressPreviewActivity.this.aboutTextClicked(this.val$aboutText, this.val$moreText);
        }
    }

    class AnonymousClass28 implements OnLayoutChangeListener {
        final /* synthetic */ TextView val$aboutText;
        final /* synthetic */ TextView val$moreText;

        AnonymousClass28(TextView $r2, TextView $r3) throws  {
            this.val$moreText = $r2;
            this.val$aboutText = $r3;
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) throws  {
            this.val$moreText.removeOnLayoutChangeListener(this);
            left = AddressPreviewActivity.this.getResources().getDimensionPixelSize(C1283R.dimen.addressPreviewAboutClosedHeight);
            if (this.val$aboutText.getMeasuredHeight() <= left) {
                this.val$moreText.setVisibility(8);
                AddressPreviewActivity.this.findViewById(C1283R.id.addressPreviewPlaceAboutCover).setVisibility(8);
                return;
            }
            LayoutParams $r5 = this.val$aboutText.getLayoutParams();
            $r5.height = left;
            this.val$aboutText.setLayoutParams($r5);
        }
    }

    class AnonymousClass29 implements OnClickListener {
        final /* synthetic */ ImageView val$placeImage;

        class C20791 implements IPhotoGalleryListener {
            C20791() throws  {
            }

            public void onScroll(int position) throws  {
            }

            public void onFlag(int $i0, int $i1) throws  {
                AddressPreviewActivity.this.mNatMgr.venueFlagImage(AddressPreviewActivity.this.mAddressItem.venueData.id, AddressPreviewActivity.this.mAddressItem.venueData.imageURLs[$i0], $i1);
                AddressPreviewActivity.this.mAddressItem.venueData.removeImage($i0);
                AddressPreviewActivity.this.mImageArray.remove($i0);
                this = this;
                if (AddressPreviewActivity.this.mAddressItem.venueData.numImages > 0) {
                    this = this;
                    AddressPreviewActivity.this.mGallery.updateImages(AddressPreviewActivity.this.mImageArray);
                } else {
                    this = this;
                    AddressPreviewActivity.this.mGallery.dismiss();
                }
                if ($i0 == 0) {
                    C20791 $r1 = this;
                    this = $r1;
                    if (AddressPreviewActivity.this.mAddressItem.venueData.numImages > 0) {
                        ImageRepository $r11 = ImageRepository.instance;
                        $r1 = this;
                        this = $r1;
                        String $r6 = ((VenueImage) AddressPreviewActivity.this.mImageArray.get(0)).imageUri;
                        $r1 = this;
                        this = $r1;
                        ImageView $r14 = AnonymousClass29.this.val$placeImage;
                        $r11.getImage($r6, $r14);
                        return;
                    }
                    $r1 = this;
                    this = $r1;
                    AddressPreviewActivity.this.mPlaceImagesFrame.setVisibility(8);
                }
            }

            public void onDelete(int $i0) throws  {
                AddressPreviewActivity.this.mNatMgr.venueDeleteImage(AddressPreviewActivity.this.mAddressItem.venueData.id, AddressPreviewActivity.this.mAddressItem.venueData.imageURLs[$i0]);
                AddressPreviewActivity.this.mAddressItem.venueData.removeImage($i0);
                AddressPreviewActivity.this.mImageArray.remove($i0);
                this = this;
                if (AddressPreviewActivity.this.mAddressItem.venueData.numImages > 0) {
                    this = this;
                    AddressPreviewActivity.this.mGallery.updateImages(AddressPreviewActivity.this.mImageArray);
                } else {
                    this = this;
                    AddressPreviewActivity.this.mGallery.dismiss();
                }
                if ($i0 == 0) {
                    C20791 $r1 = this;
                    this = $r1;
                    if (AddressPreviewActivity.this.mAddressItem.venueData.numImages > 0) {
                        ImageRepository $r11 = ImageRepository.instance;
                        $r1 = this;
                        this = $r1;
                        String $r6 = ((VenueImage) AddressPreviewActivity.this.mImageArray.get(0)).imageUri;
                        $r1 = this;
                        this = $r1;
                        ImageView $r14 = AnonymousClass29.this.val$placeImage;
                        $r11.getImage($r6, $r14);
                        return;
                    }
                    $r1 = this;
                    this = $r1;
                    AddressPreviewActivity.this.mPlaceImagesFrame.setVisibility(8);
                }
            }

            public void onLike(int $i0) throws  {
                AddressPreviewActivity.this.mAddressItem.venueData.imageLiked[$i0] = true;
                AddressPreviewActivity.this.mNatMgr.venueLikeImage(AddressPreviewActivity.this.mAddressItem.venueData.id, AddressPreviewActivity.this.mAddressItem.venueData.imageURLs[$i0]);
            }

            public void onUnlike(int $i0) throws  {
                AddressPreviewActivity.this.mAddressItem.venueData.imageLiked[$i0] = false;
                AddressPreviewActivity.this.mNatMgr.venueUnlikeImage(AddressPreviewActivity.this.mAddressItem.venueData.id, AddressPreviewActivity.this.mAddressItem.venueData.imageURLs[$i0]);
            }
        }

        AnonymousClass29(ImageView $r2) throws  {
            this.val$placeImage = $r2;
        }

        public void onClick(View $r1) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ADDRESS_PREVIEW_IMAGE_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, AddressPreviewActivity.this.mAddressItem.venueData.id);
            this = this;
            if (AddressPreviewActivity.this.mAddressItem.venueData.bHasMoreData) {
                AddressPreviewActivity.this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, AddressPreviewActivity.this.mHandler);
                AddressPreviewActivity.this.mDtnMgr.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, AddressPreviewActivity.this.mHandler);
                this = this;
                AddressPreviewActivity.this.mNatMgr.venueGet(AddressPreviewActivity.this.mAddressItem.venueData.id, 1);
            }
            AnonymousClass29 $r4 = this;
            this = $r4;
            AddressPreviewActivity.this.mGallery = PhotoPagerSection.openPlacePhotoGalleryFromView($r1, AddressPreviewActivity.this.mImageArray, 0, new C20791(), AddressPreviewActivity.this);
        }
    }

    class C20802 implements AnimationListener {
        C20802() throws  {
        }

        public void onAnimationStart(Animation animation) throws  {
        }

        public void onAnimationRepeat(Animation animation) throws  {
        }

        public void onAnimationEnd(Animation animation) throws  {
            RelativeLayout.LayoutParams $r5 = (RelativeLayout.LayoutParams) AddressPreviewActivity.this.mLandcapeScrollContainer.getLayoutParams();
            $r5.rightMargin = 0;
            AddressPreviewActivity.this.mLandcapeScrollContainer.setLayoutParams($r5);
            AddressPreviewActivity.this.mLandcapeScrollContainer.clearAnimation();
        }
    }

    class AnonymousClass30 implements DownloadResCallback {
        final /* synthetic */ String val$icon;
        final /* synthetic */ View val$rectangleFrame;
        final /* synthetic */ ImageView val$rectangleImage;

        AnonymousClass30(String $r2, View $r3, ImageView $r4) throws  {
            this.val$icon = $r2;
            this.val$rectangleFrame = $r3;
            this.val$rectangleImage = $r4;
        }

        public void downloadResCallback(int $i0) throws  {
            Drawable $r3;
            if ($i0 > 0) {
                Logger.m36d("Successfully downloaded preview icon " + this.val$icon + ". ");
                $r3 = ResManager.GetSkinDrawable(this.val$icon + ResManager.mImageExtension);
                if ($r3 != null) {
                    this.val$rectangleFrame.setVisibility(0);
                    this.val$rectangleImage.setScaleType(ScaleType.CENTER_CROP);
                    this.val$rectangleImage.setImageDrawable($r3);
                    return;
                }
                return;
            }
            Logger.m36d("Failed download preview icon " + this.val$icon + ". Going to display the fallback one: " + AddressPreviewActivity.this.mAddressItem.getIcon());
            if (AddressPreviewActivity.this.mAddressItem.hasIcon()) {
                $r3 = ResManager.GetSkinDrawable(AddressPreviewActivity.this.mAddressItem.getIcon() + ResManager.mImageExtension);
                if ($r3 != null) {
                    this.val$rectangleFrame.setVisibility(0);
                    this.val$rectangleImage.setScaleType(ScaleType.FIT_CENTER);
                    this.val$rectangleImage.setImageDrawable($r3);
                }
            }
        }
    }

    class AnonymousClass33 implements Runnable {
        final /* synthetic */ ObservableScrollView val$scroll;

        AnonymousClass33(ObservableScrollView $r2) throws  {
            this.val$scroll = $r2;
        }

        public void run() throws  {
            this.val$scroll.scrollTo(0, this.val$scroll.getChildAt(0).getHeight());
            this.val$scroll.setDisabled(true);
        }
    }

    class C20823 implements Runnable {
        C20823() throws  {
        }

        public void run() throws  {
            Intent $r1 = new Intent(AddressPreviewActivity.this, AutocompleteSearchActivity.class);
            $r1.putExtra(PublicMacros.SEARCH_MODE, 9);
            $r1.putExtra(PublicMacros.ADDRESS_ITEM, AddressPreviewActivity.this.mAddressItem);
            $r1.putExtra("Speech", AddressPreviewActivity.this.mAddressItem.getAddress());
            AppService.getActiveActivity().startActivityForResult($r1, 113);
        }
    }

    class C20834 implements Runnable {
        C20834() throws  {
        }

        public void run() throws  {
            AddressPreviewActivity.this.mWebViewLoadAnimation.setVisibility(8);
        }
    }

    class C20926 extends RunnableUICallback {
        private int dangerZone;

        C20926() throws  {
        }

        public void event() throws  {
            this.dangerZone = AddressPreviewActivity.this.mDtnMgr.isInDangerZoneNTV(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue());
        }

        public void callback() throws  {
            if (this.dangerZone >= 0) {
                AddressPreviewActivity.this.setDangerZoneTitle(this.dangerZone);
            }
        }
    }

    class C20937 implements OnClickListener {
        C20937() throws  {
        }

        public void onClick(View view) throws  {
            AddressPreviewActivity.this.onBackPressed();
        }
    }

    class C20948 implements Runnable {
        C20948() throws  {
        }

        public void run() throws  {
            AddressPreviewActivity.this.mScrollView.scrollTo(0, AddressPreviewActivity.this.mLockHeight);
        }
    }

    class C20959 implements OnTouchListener {
        final /* synthetic */ ScrollAndTouchListener val$scrollAndTouchListener;

        C20959(ScrollAndTouchListener $r2) throws  {
            this.val$scrollAndTouchListener = $r2;
        }

        public boolean onTouch(View v, MotionEvent $r2) throws  {
            if (!this.val$scrollAndTouchListener._isMapOpen) {
                return false;
            }
            if ($r2.getAction() == 0) {
                AddressPreviewActivity.this.mScrollView.requestDisallowInterceptTouchEventForce(true);
            }
            if ($r2.getAction() != 1) {
                return false;
            }
            AddressPreviewActivity.this.mScrollView.requestDisallowInterceptTouchEventForce(false);
            return false;
        }
    }

    private class CancelTooltipRunnable implements Runnable {
        public View associatedView;
        public boolean needRun;

        private CancelTooltipRunnable() throws  {
            this.needRun = true;
            this.associatedView = null;
        }

        public void run() throws  {
            if (this.needRun && AddressPreviewActivity.this.shownTooltip != null) {
                AddressPreviewActivity.this.shownTooltip.dismiss(true);
                AddressPreviewActivity.this.shownTooltip = null;
                this.needRun = false;
                AddressPreviewActivity.this.cancelTooltipAction = null;
            }
        }
    }

    private final class ScrollAndTouchListener implements OnTouchListener, OnScrollListener {
        private boolean _isMapOpen;
        private boolean _isTouched;
        private final float density;
        private final ObservableScrollView osv;

        private ScrollAndTouchListener(float $f0, ObservableScrollView $r2) throws  {
            this._isMapOpen = false;
            this._isTouched = false;
            this.density = $f0;
            this.osv = $r2;
        }

        public void onScrollChanged(ObservableScrollView scrollView, int x, int $i1, int oldx, int $i3) throws  {
            if (!AddressPreviewActivity.this.unvalidateCalendarMode()) {
                float $f0;
                if (!this._isTouched) {
                    if ($i1 < AddressPreviewActivity.this.mLockHeight && $i3 >= AddressPreviewActivity.this.mLockHeight) {
                        this.osv.scrollTo(0, AddressPreviewActivity.this.mLockHeight);
                        return;
                    } else if ($i1 > AddressPreviewActivity.this.mLockHeight && $i3 <= AddressPreviewActivity.this.mLockHeight) {
                        this.osv.scrollTo(0, AddressPreviewActivity.this.mLockHeight);
                        return;
                    }
                }
                int $i2;
                if ($i1 < AddressPreviewActivity.this.mLockHeight) {
                    x = (int) ((-AddressPreviewActivity.this.LS_Y_FACTOR) * ((float) $i1));
                    oldx = 6;
                    if (AddressPreviewActivity.this.mParkingMode && AddressPreviewActivity.this.mParkingVenue != null) {
                        $i2 = AddressPreviewActivity.this.mParkingVenue.longitude - AddressPreviewActivity.this.mAddressItem.getLocationX().intValue();
                        oldx = $i2;
                        oldx = Math.abs($i2);
                        $i2 = AddressPreviewActivity.this.mParkingVenue.latitude - AddressPreviewActivity.this.mAddressItem.getLocationY().intValue();
                        $i3 = $i2;
                        oldx = Math.max(oldx, Math.abs($i2)) / 2;
                    } else if (AddressPreviewActivity.this.mLockHeight != 0) {
                        $i2 = ($i1 * 2000) / AddressPreviewActivity.this.mLockHeight;
                        oldx = $i2;
                        oldx = $i2 + 1000;
                    }
                    AddressPreviewActivity.this.mNavNtvMgr.PreviewCanvasFocusOn(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue() + x, oldx);
                    if (AddressPreviewActivity.this.mLockHeight != 0) {
                        $i2 = ((($i1 - AddressPreviewActivity.this.mLockHeight) * AddressPreviewActivity.this.mPlaceImagesFrame.getWidth()) * 2) / AddressPreviewActivity.this.mLockHeight;
                        x = $i2;
                        AddressPreviewActivity.this.mPlaceImagesFrame.setTranslationX((float) $i2);
                    }
                    AddressPreviewActivity.this.mMapMask.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    AddressPreviewActivity.this.fadeTitleBar(0.0f);
                } else if ($i1 < AddressPreviewActivity.this.mFrameHeight) {
                    $i2 = AddressPreviewActivity.this.mFrameHeight - AddressPreviewActivity.this.mLockHeight;
                    x = $i2;
                    if (((float) ($i1 - AddressPreviewActivity.this.mLockHeight)) / ((float) $i2) > 1.0f) {
                        x = AddressPreviewActivity.this.mFrameHeight - AddressPreviewActivity.this.mTitleBarHeight;
                    } else {
                        x = AddressPreviewActivity.this.mFrameHeight - AddressPreviewActivity.this.mTitleBarHeight;
                    }
                    if ($i1 < x) {
                        AddressPreviewActivity.this.fadeTitleBar(0);
                    } else {
                        $f0 = ((float) ($i1 - x)) / ((float) (AddressPreviewActivity.this.mTitleBarHeight / 2));
                        if ($f0 > 1.0f) {
                            $f0 = 1.0f;
                        }
                        AddressPreviewActivity.this.fadeTitleBar($f0);
                    }
                    AddressPreviewActivity.this.mPlaceImagesFrame.setTranslationX(0);
                    AddressPreviewActivity.this.mNavNtvMgr.PreviewCanvasFocusOn(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue() - ((int) (AddressPreviewActivity.this.LS_Y_FACTOR * ((float) AddressPreviewActivity.this.mLockHeight))), 3000);
                } else {
                    AddressPreviewActivity.this.fadeTitleBar(1065353216);
                }
                if (AddressPreviewActivity.this.mButtonShadowGoneHeight > 0) {
                    $f0 = ((float) AddressPreviewActivity.this.mButtonShadowGoneHeight) - (AddressPreviewActivity.this.mDensity * AutoResizeTextView.MIN_TEXT_SIZE);
                    if (((float) $i1) > $f0) {
                        AddressPreviewActivity.this.findViewById(C1283R.id.rideRequestButtonsLayoutShadow).setAlpha(1.0f - ((((float) $i1) - $f0) / (AddressPreviewActivity.this.mDensity * AutoResizeTextView.MIN_TEXT_SIZE)));
                    } else {
                        AddressPreviewActivity.this.findViewById(C1283R.id.rideRequestButtonsLayoutShadow).setAlpha(1065353216);
                    }
                }
                if (AddressPreviewActivity.this.cancelTooltipAction != null) {
                    AddressPreviewActivity.this.cancelTooltipAction.run();
                }
            }
        }

        public boolean onTouch(View v, MotionEvent $r2) throws  {
            if (AddressPreviewActivity.this.unvalidateCalendarMode()) {
                return false;
            }
            if ($r2.getAction() == 1 || $r2.getAction() == 3) {
                this._isTouched = false;
                float $f0 = (float) this.osv.getScrollY();
                if (this._isMapOpen) {
                    if ($f0 >= ((float) AddressPreviewActivity.this.mLockHeight)) {
                        return false;
                    }
                    if ($f0 > ((float) (AddressPreviewActivity.this.mLockHeight / 4))) {
                        lockScrollTo(AddressPreviewActivity.this.mLockHeight);
                        return false;
                    }
                    lockScrollTo(0);
                    return false;
                } else if ($f0 >= ((float) AddressPreviewActivity.this.mLockHeight)) {
                    return false;
                } else {
                    if ($f0 > ((float) ((AddressPreviewActivity.this.mLockHeight * 3) / 4))) {
                        lockScrollTo(AddressPreviewActivity.this.mLockHeight);
                        return false;
                    }
                    lockScrollTo(0);
                    return false;
                }
            } else if ($r2.getAction() != 0 && $r2.getAction() != 2) {
                return false;
            } else {
                this._isTouched = true;
                return false;
            }
        }

        private void lockScrollTo(final int $i0) throws  {
            this.osv.post(new Runnable() {
                public void run() throws  {
                    boolean $z0 = false;
                    ScrollAndTouchListener.this.osv.smoothScrollTo(0, $i0);
                    ScrollAndTouchListener $r2 = ScrollAndTouchListener.this;
                    if ($i0 == 0) {
                        $z0 = true;
                    }
                    $r2._isMapOpen = $z0;
                }
            });
        }
    }

    private class WebViewInterface {
        private WebViewInterface() throws  {
        }

        @JavascriptInterface
        public void onError(String $r1) throws  {
            Logger.m38e("callJavaScript - onError(" + $r1 + ")");
        }

        @JavascriptInterface
        public void onResponse(int $i0, final int $i1) throws  {
            Logger.m36d("callJavaScript - onResponse(" + $i0 + ", " + $i1 + ")");
            if ($i0 == 1) {
                AddressPreviewActivity.this.post(new Runnable() {
                    public void run() throws  {
                        LayoutParams $r4 = AddressPreviewActivity.this.mWebView.getLayoutParams();
                        $r4.height = (int) (((float) $i1) * AddressPreviewActivity.this.mDensity);
                        AddressPreviewActivity.this.mWebView.setLayoutParams($r4);
                        AddressPreviewActivity.this.mWebView.requestLayout();
                    }
                });
            }
        }

        @JavascriptInterface
        public void onLogAnalyticsAds(String $r1) throws  {
            AddressPreviewActivity.this.logAnalyticsAds($r1);
        }
    }

    private void refreshView() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:203:0x0ab3
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r169 = this;
        r0 = r169;
        r8 = r0.mAddressItemList;
        if (r8 == 0) goto L_0x0a24;
    L_0x0006:
        r0 = r169;
        r8 = r0.mAddressItemList;
        r9 = r8.size();
        r0 = r169;
        r10 = r0.mSelectedItem;
        if (r9 <= r10) goto L_0x0a24;
    L_0x0014:
        r0 = r169;
        r8 = r0.mAddressItemList;
        r0 = r169;
        r9 = r0.mSelectedItem;
        r11 = r8.get(r9);
        r13 = r11;
        r13 = (com.waze.navigate.AddressItem) r13;
        r12 = r13;
        r0 = r169;
        r0.mAddressItem = r12;
        r0 = r169;
        r12 = r0.mAddressItem;
        r14 = r12.getCategory();
        r9 = r14.intValue();
        r15 = 7;
        if (r9 == r15) goto L_0x0048;
    L_0x0037:
        r0 = r169;
        r0 = r0.mDtnMgr;
        r16 = r0;
        r0 = r169;
        r12 = r0.mAddressItem;
        r9 = r12.index;
        r0 = r16;
        r0.notifyAddressItemShownBeforeNavigate(r9);
    L_0x0048:
        r0 = r169;
        r0.setupPreview();
        r0 = r169;
        r12 = r0.mAddressItem;
        r9 = r12.getType();
        r15 = 1;
        if (r9 != r15) goto L_0x0a3a;
    L_0x0058:
        r17 = "HOME";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
    L_0x0060:
        r18 = new com.waze.navigate.AddressPreviewActivity$13;
        r19 = r18;
        r0 = r18;
        r1 = r169;
        r0.<init>();
        r20 = 0;
        r0 = r20;
        r1 = r169;
        r1.mOnSaveEventLocation = r0;
        r0 = r169;
        r12 = r0.mAddressItem;
        r14 = r12.getCategory();
        r10 = r14.intValue();
        r15 = 7;
        if (r10 != r15) goto L_0x0123;
    L_0x0082:
        r0 = r169;
        r12 = r0.mEventAddressItem;
        if (r12 == 0) goto L_0x0098;
    L_0x0088:
        r0 = r169;
        r12 = r0.mEventAddressItem;
        r21 = r12.getIsValidate();
        r0 = r21;
        r22 = r0.booleanValue();
        if (r22 == 0) goto L_0x00ae;
    L_0x0098:
        r0 = r169;
        r12 = r0.mAddressItem;
        if (r12 == 0) goto L_0x0123;
    L_0x009e:
        r0 = r169;
        r12 = r0.mAddressItem;
        r21 = r12.getIsValidate();
        r0 = r21;
        r22 = r0.booleanValue();
        if (r22 != 0) goto L_0x0123;
    L_0x00ae:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.VanueID;
        r23 = r0;
        if (r23 != 0) goto L_0x00c0;
    L_0x00b8:
        r0 = r169;
        r12 = r0.mEventAddressItem;
        r0 = r12.VanueID;
        r23 = r0;
    L_0x00c0:
        if (r23 != 0) goto L_0x00c4;
    L_0x00c2:
        r23 = "";
    L_0x00c4:
        r17 = "CALENDAR_OPTIONS";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r0 = r169;
        r12 = r0.mAddressItem;
        r25 = r12.getMeetingId();
        r0 = r24;
        r1 = r25;
        r26 = r0.append(r1);
        r17 = "|";
        r0 = r26;
        r1 = r17;
        r26 = r0.append(r1);
        r0 = r26;
        r1 = r23;
        r26 = r0.append(r1);
        r0 = r26;
        r23 = r0.toString();
        r17 = "CALENDAR_OPTIONS";
        r27 = "ID|VENUE";
        r0 = r17;
        r1 = r27;
        r2 = r23;
        com.waze.analytics.Analytics.log(r0, r1, r2);
        r28 = new com.waze.navigate.AddressPreviewActivity$14;
        r0 = r28;
        r1 = r169;
        r0.<init>();
        r0 = r28;
        r1 = r169;
        r1.mOnSaveEventLocation = r0;
        r29 = new com.waze.navigate.AddressPreviewActivity$15;
        r19 = r29;
        r0 = r29;
        r1 = r169;
        r0.<init>();
    L_0x0123:
        r0 = r169;
        r12 = r0.mAddressItem;
        r14 = r12.getCategory();
        r10 = r14.intValue();
        r15 = 6;
        if (r10 != r15) goto L_0x0192;
    L_0x0132:
        r0 = r169;
        r12 = r0.mEventAddressItem;
        if (r12 == 0) goto L_0x0148;
    L_0x0138:
        r0 = r169;
        r12 = r0.mEventAddressItem;
        r21 = r12.getIsValidate();
        r0 = r21;
        r22 = r0.booleanValue();
        if (r22 == 0) goto L_0x015e;
    L_0x0148:
        r0 = r169;
        r12 = r0.mAddressItem;
        if (r12 == 0) goto L_0x0192;
    L_0x014e:
        r0 = r169;
        r12 = r0.mAddressItem;
        r21 = r12.getIsValidate();
        r0 = r21;
        r22 = r0.booleanValue();
        if (r22 != 0) goto L_0x0192;
    L_0x015e:
        r0 = r169;
        r12 = r0.mEventAddressItem;
        if (r12 == 0) goto L_0x0ab7;
    L_0x0164:
        r0 = r169;
        r12 = r0.mEventAddressItem;
        r23 = r12.getId();
    L_0x016c:
        r17 = "VERIFY_EVENT_SAVE_EVENT_LOCATION";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        r30 = new com.waze.navigate.AddressPreviewActivity$16;
        r0 = r30;
        r1 = r169;
        r2 = r23;
        r0.<init>(r2);
        r0 = r30;
        r1 = r169;
        r1.mOnSaveEventLocation = r0;
        r31 = new com.waze.navigate.AddressPreviewActivity$17;
        r19 = r31;
        r0 = r31;
        r1 = r169;
        r2 = r23;
        r0.<init>(r2);
    L_0x0192:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        if (r32 != 0) goto L_0x0abe;
    L_0x019c:
        r22 = 1;
    L_0x019e:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.mIsNavigable;
        r33 = r0;
        if (r33 == 0) goto L_0x0b07;
    L_0x01a8:
        r0 = r169;
        r12 = r0.mAddressItem;
        r10 = r12.getType();
        r15 = 20;
        if (r10 == r15) goto L_0x0b07;
    L_0x01b4:
        r15 = 2131689851; // 0x7f0f017b float:1.900873E38 double:1.053194723E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r36 = r34;
        r36 = (android.widget.TextView) r36;
        r35 = r36;
        r0 = r169;
        r0 = r0.mParkingMode;
        r33 = r0;
        if (r33 == 0) goto L_0x0ac1;
    L_0x01cb:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1786; // 0x6fa float:2.503E-42 double:8.824E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
    L_0x01e0:
        r15 = 2131689850; // 0x7f0f017a float:1.9008727E38 double:1.0531947225E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r0 = r34;
        r1 = r19;
        r0.setOnClickListener(r1);
        r15 = 2131689848; // 0x7f0f0178 float:1.9008723E38 double:1.0531947215E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r0 = r34;
        com.waze.planned_drive.PlannedDriveActivity.setupDriveLaterButton(r0);
        r38 = new com.waze.navigate.AddressPreviewActivity$18;
        r0 = r38;
        r1 = r169;
        r0.<init>();
        r0 = r34;
        r1 = r38;
        r0.setOnClickListener(r1);
    L_0x020e:
        r15 = 2131689863; // 0x7f0f0187 float:1.9008753E38 double:1.053194729E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r39 = r34;
        r39 = (android.widget.TextView) r39;
        r35 = r39;
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getTitle();
        r17 = "";
        r0 = r23;
        r1 = r17;
        r33 = r0.equals(r1);
        if (r33 == 0) goto L_0x0b1c;
    L_0x0231:
        r15 = 8;
        r0 = r35;
        r0.setVisibility(r15);
        r0 = r169;
        r0 = r0.mTitleBar;
        r40 = r0;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 549; // 0x225 float:7.7E-43 double:2.71E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r40;
        r1 = r23;
        r0.setTitle(r1);
    L_0x0253:
        r15 = 2131689867; // 0x7f0f018b float:1.9008761E38 double:1.053194731E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r42 = r34;
        r42 = (android.widget.TextView) r42;
        r41 = r42;
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getAddress();
        r25 = r23;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        if (r32 == 0) goto L_0x02c9;
    L_0x0276:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.details;
        r43 = r0;
        if (r43 == 0) goto L_0x02c9;
    L_0x0284:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.details;
        r43 = r0;
        r33 = r0.isEmpty();
        if (r33 != 0) goto L_0x02c9;
    L_0x0296:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r0 = r24;
        r1 = r23;
        r26 = r0.append(r1);
        r17 = "\n";
        r0 = r26;
        r1 = r17;
        r26 = r0.append(r1);
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.details;
        r23 = r0;
        r0 = r26;
        r1 = r23;
        r26 = r0.append(r1);
        r0 = r26;
        r25 = r0.toString();
    L_0x02c9:
        r17 = "";
        r0 = r25;
        r1 = r17;
        r33 = r0.equals(r1);
        if (r33 == 0) goto L_0x0b45;
    L_0x02d5:
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
    L_0x02dc:
        r15 = 2131689868; // 0x7f0f018c float:1.9008764E38 double:1.0531947314E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r44 = r34;
        r44 = (android.widget.TextView) r44;
        r41 = r44;
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getDistance();
        r17 = "";
        r0 = r23;
        r1 = r17;
        r33 = r0.equals(r1);
        if (r33 != 0) goto L_0x0309;
    L_0x02ff:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.mIsNavigable;
        r33 = r0;
        if (r33 != 0) goto L_0x0b57;
    L_0x0309:
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
    L_0x0310:
        r0 = r169;
        r45 = r0.getResources();
        r0 = r45;
        r46 = r0.getConfiguration();
        r0 = r46;
        r10 = r0.orientation;
        r15 = 1;
        if (r10 != r15) goto L_0x033d;
    L_0x0323:
        r0 = r169;
        r0 = r0.mScrollView;
        r47 = r0;
        r48 = r0.getViewTreeObserver();
        r49 = new com.waze.navigate.AddressPreviewActivity$19;
        r0 = r49;
        r1 = r169;
        r0.<init>();
        r0 = r48;
        r1 = r49;
        r0.addOnGlobalLayoutListener(r1);
    L_0x033d:
        r15 = 2131689828; // 0x7f0f0164 float:1.9008682E38 double:1.0531947116E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r50 = r34;
        r50 = (android.widget.TextView) r50;
        r41 = r50;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.mIsNavigable;
        r33 = r0;
        if (r33 == 0) goto L_0x0b9c;
    L_0x0356:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1794; // 0x702 float:2.514E-42 double:8.864E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r15 = 5;
        if (r9 == r15) goto L_0x0374;
    L_0x036e:
        r15 = 1;
        if (r9 == r15) goto L_0x0374;
    L_0x0371:
        r15 = 3;
        if (r9 != r15) goto L_0x0b71;
    L_0x0374:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838827; // 0x7f02052b float:1.7282647E38 double:1.052774261E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r20 = 0;
        r52 = 0;
        r53 = 0;
        r0 = r41;
        r1 = r51;
        r2 = r20;
        r3 = r52;
        r4 = r53;
        r0.setCompoundDrawablesWithIntrinsicBounds(r1, r2, r3, r4);
    L_0x0396:
        r54 = new com.waze.navigate.AddressPreviewActivity$20;
        r0 = r54;
        r1 = r169;
        r0.<init>();
        r0 = r41;
        r1 = r54;
        r0.setOnClickListener(r1);
    L_0x03a6:
        r15 = 2131689870; // 0x7f0f018e float:1.9008768E38 double:1.0531947324E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r55 = r34;
        r55 = (android.widget.TextView) r55;
        r41 = r55;
        r15 = 2131689871; // 0x7f0f018f float:1.900877E38 double:1.053194733E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r57 = r34;
        r57 = (android.widget.TextView) r57;
        r56 = r57;
        if (r22 != 0) goto L_0x03d4;
    L_0x03c6:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numOpeningHours;
        r58 = r0;
        if (r58 != 0) goto L_0x0ba4;
    L_0x03d4:
        r15 = 8;
        r0 = r56;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
    L_0x03e2:
        r15 = 2131689879; // 0x7f0f0197 float:1.9008786E38 double:1.053194737E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r59 = r34;
        r59 = (android.widget.TextView) r59;
        r41 = r59;
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
        r15 = 2131689884; // 0x7f0f019c float:1.9008796E38 double:1.0531947393E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 2131689885; // 0x7f0f019d float:1.9008798E38 double:1.05319474E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r60;
        r0.setVisibility(r15);
        r15 = 2131689886; // 0x7f0f019e float:1.90088E38 double:1.0531947403E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r0 = r169;
        r12 = r0.mAddressItem;
        r61 = r12.getAdvertiserData();
        if (r61 == 0) goto L_0x043f;
    L_0x042b:
        r0 = r61;
        r0 = r0.url;
        r23 = r0;
        if (r23 == 0) goto L_0x043f;
    L_0x0433:
        r0 = r61;
        r0 = r0.url;
        r23 = r0;
        r33 = r0.isEmpty();
        if (r33 == 0) goto L_0x0c22;
    L_0x043f:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
    L_0x0446:
        if (r22 != 0) goto L_0x0456;
    L_0x0448:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.bResidence;
        r33 = r0;
        if (r33 == 0) goto L_0x0d04;
    L_0x0456:
        r15 = 2131689895; // 0x7f0f01a7 float:1.9008818E38 double:1.0531947447E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689894; // 0x7f0f01a6 float:1.9008816E38 double:1.053194744E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
    L_0x0476:
        r15 = 2131689913; // 0x7f0f01b9 float:1.9008855E38 double:1.0531947536E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 2131689914; // 0x7f0f01ba float:1.9008857E38 double:1.053194754E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r62 = r60;
        r62 = (android.widget.TextView) r62;
        r41 = r62;
        r15 = 2131689915; // 0x7f0f01bb float:1.9008859E38 double:1.0531947546E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r64 = r60;
        r64 = (android.widget.LinearLayout) r64;
        r63 = r64;
        if (r22 != 0) goto L_0x04ad;
    L_0x049f:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numServices;
        r58 = r0;
        if (r58 != 0) goto L_0x0ef9;
    L_0x04ad:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r63;
        r0.setVisibility(r15);
    L_0x04c2:
        r15 = 2131689910; // 0x7f0f01b6 float:1.9008849E38 double:1.053194752E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 2131689912; // 0x7f0f01b8 float:1.9008853E38 double:1.053194753E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r65 = r60;
        r65 = (android.widget.LinearLayout) r65;
        r63 = r65;
        r15 = 2131689911; // 0x7f0f01b7 float:1.900885E38 double:1.0531947526E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r66 = r60;
        r66 = (android.widget.TextView) r66;
        r41 = r66;
        if (r22 != 0) goto L_0x04f9;
    L_0x04eb:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numOpeningHours;
        r58 = r0;
        if (r58 != 0) goto L_0x0f3d;
    L_0x04f9:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r63;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
    L_0x050e:
        r15 = 2131689916; // 0x7f0f01bc float:1.900886E38 double:1.053194755E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 2131689918; // 0x7f0f01be float:1.9008865E38 double:1.053194756E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r15 = 2131689917; // 0x7f0f01bd float:1.9008863E38 double:1.0531947556E-314;
        r0 = r169;
        r67 = r0.findViewById(r15);
        r68 = r67;
        r68 = (android.widget.TextView) r68;
        r41 = r68;
        if (r22 != 0) goto L_0x0543;
    L_0x0531:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.about;
        r23 = r0;
        r33 = android.text.TextUtils.isEmpty(r0);
        if (r33 == 0) goto L_0x1023;
    L_0x0543:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r60;
        r0.setVisibility(r15);
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
    L_0x0558:
        r15 = 2131689837; // 0x7f0f016d float:1.90087E38 double:1.053194716E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r0 = r34;
        r1 = r169;
        r1.mPlaceImagesFrame = r0;
        if (r22 != 0) goto L_0x0577;
    L_0x0569:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numImages;
        r58 = r0;
        if (r58 != 0) goto L_0x10bd;
    L_0x0577:
        r0 = r169;
        r0 = r0.mPlaceImagesFrame;
        r34 = r0;
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
    L_0x0584:
        r15 = 2131689833; // 0x7f0f0169 float:1.9008693E38 double:1.053194714E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r70 = r34;
        r70 = (android.widget.ImageView) r70;
        r69 = r70;
        r0 = r69;
        r1 = r169;
        r1.mLogoImg = r0;
        r15 = 2131689836; // 0x7f0f016c float:1.9008699E38 double:1.0531947156E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r71 = r34;
        r71 = (android.widget.ImageView) r71;
        r69 = r71;
        r15 = 2131689835; // 0x7f0f016b float:1.9008697E38 double:1.053194715E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 2131689834; // 0x7f0f016a float:1.9008695E38 double:1.0531947146E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r73 = r60;
        r73 = (android.widget.ProgressBar) r73;
        r72 = r73;
        r0 = r72;
        r1 = r169;
        r1.mWebViewLoadAnimation = r0;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.mPreviewIcon;
        r23 = r0;
        r0 = r169;
        r0 = r0.mShouldLoadVenue;
        r33 = r0;
        r22 = r33;
        if (r33 != 0) goto L_0x0621;
    L_0x05d8:
        if (r23 == 0) goto L_0x0621;
    L_0x05da:
        r0 = r23;
        r33 = r0.isEmpty();
        if (r33 != 0) goto L_0x0621;
    L_0x05e2:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r0 = r24;
        r1 = r23;
        r26 = r0.append(r1);
        r17 = ".png";
        r0 = r26;
        r1 = r17;
        r26 = r0.append(r1);
        r0 = r26;
        r25 = r0.toString();
        r0 = r25;
        r51 = com.waze.ResManager.GetSkinDrawable(r0);
        if (r51 == 0) goto L_0x121f;
    L_0x0609:
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r74 = android.widget.ImageView.ScaleType.CENTER_CROP;
        r0 = r69;
        r1 = r74;
        r0.setScaleType(r1);
        r0 = r69;
        r1 = r51;
        r0.setImageDrawable(r1);
        r22 = 1;
    L_0x0621:
        r0 = r169;
        r0 = r0.mLogoResource;
        r58 = r0;
        r15 = -1;
        r0 = r58;
        if (r0 == r15) goto L_0x0665;
    L_0x062c:
        r0 = r169;
        r45 = r0.getResources();
        r0 = r169;
        r0 = r0.mLogoResource;
        r58 = r0;
        r0 = r45;
        r1 = r58;
        r51 = r0.getDrawable(r1);
        r76 = r51;
        r76 = (android.graphics.drawable.BitmapDrawable) r76;
        r75 = r76;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        r22 = 1;
    L_0x0665:
        if (r22 != 0) goto L_0x06ad;
    L_0x0667:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        if (r32 == 0) goto L_0x06ad;
    L_0x0671:
        r0 = r169;
        r0 = r0.mIsGasStation;
        r33 = r0;
        if (r33 == 0) goto L_0x06ad;
    L_0x0679:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838192; // 0x7f0202b0 float:1.728136E38 double:1.0527739475E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r79 = r51;
        r79 = (android.graphics.drawable.BitmapDrawable) r79;
        r75 = r79;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        r22 = 1;
    L_0x06ad:
        if (r22 != 0) goto L_0x06e5;
    L_0x06af:
        switch(r9) {
            case 1: goto L_0x130c;
            case 2: goto L_0x06b3;
            case 3: goto L_0x1343;
            case 4: goto L_0x06b3;
            case 5: goto L_0x12d5;
            case 6: goto L_0x06b3;
            case 7: goto L_0x06b3;
            case 8: goto L_0x129e;
            case 9: goto L_0x06b3;
            case 10: goto L_0x06b3;
            case 11: goto L_0x137a;
            case 12: goto L_0x06b3;
            case 13: goto L_0x1244;
            default: goto L_0x06b2;
        };
    L_0x06b2:
        goto L_0x06b3;
    L_0x06b3:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838055; // 0x7f020227 float:1.7281082E38 double:1.05277388E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r80 = r51;
        r80 = (android.graphics.drawable.BitmapDrawable) r80;
        r75 = r80;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
    L_0x06e5:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        if (r32 == 0) goto L_0x072f;
    L_0x06ef:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.reporter;
        r23 = r0;
        if (r23 == 0) goto L_0x070f;
    L_0x06fd:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.reporter;
        r23 = r0;
        r22 = r0.isEmpty();
        if (r22 == 0) goto L_0x13b1;
    L_0x070f:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.updater;
        r23 = r0;
        if (r23 == 0) goto L_0x072f;
    L_0x071d:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.updater;
        r23 = r0;
        r22 = r0.isEmpty();
        if (r22 == 0) goto L_0x13b1;
    L_0x072f:
        r15 = 2131689842; // 0x7f0f0172 float:1.900871E38 double:1.0531947185E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
    L_0x073f:
        r0 = r169;
        r12 = r0.mAddressItem;
        r58 = r12.getType();
        r15 = 11;
        r0 = r58;
        if (r0 != r15) goto L_0x09f8;
    L_0x074d:
        r81 = new com.waze.navigate.AddressPreviewActivity$31;
        r0 = r81;
        r1 = r169;
        r0.<init>();
        r0 = r81;
        r1 = r169;
        r1.calendarSetLocationListener = r0;
        r82 = new com.waze.navigate.AddressPreviewActivity$32;
        r0 = r82;
        r1 = r169;
        r0.<init>();
        r0 = r82;
        r1 = r169;
        r1.calendarRemoveListener = r0;
        r15 = 2131689867; // 0x7f0f018b float:1.9008761E38 double:1.053194731E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r0 = r169;
        r0 = r0.mDtnMgr;
        r16 = r0;
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getMeetingId();
        r0 = r16;
        r1 = r23;
        r83 = r0.fetchCalendarEvent(r1);
        r15 = 2131689864; // 0x7f0f0188 float:1.9008755E38 double:1.0531947294E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        if (r83 == 0) goto L_0x1577;
    L_0x07a2:
        r0 = r83;
        r0 = r0.beginTime;
        r23 = r0;
        r84 = java.lang.Long.parseLong(r0);
        r0 = r84;
        r86 = java.lang.Long.valueOf(r0);
        r0 = r83;
        r0 = r0.title;
        r23 = r0;
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        r87 = com.waze.settings.SettingsNativeManager.getInstance();
        r88 = new java.util.Locale;
        r0 = r87;
        r23 = r0.getLanguagesLocaleNTV();
        r0 = r88;
        r1 = r23;
        r0.<init>(r1);
        r89 = new java.text.SimpleDateFormat;
        r90 = r89;
        r17 = "EEEE HH:mm";
        r0 = r89;
        r1 = r17;
        r2 = r88;
        r0.<init>(r1, r2);
        r91 = java.util.Calendar.getInstance();
        r0 = r91;
        r92 = r0.getTimeZone();
        r0 = r90;
        r1 = r92;
        r0.setTimeZone(r1);
        r93 = new java.util.Date;
        r0 = r86;
        r84 = r0.longValue();
        r0 = r93;
        r1 = r84;
        r0.<init>(r1);
        r0 = r90;
        r1 = r93;
        r23 = r0.format(r1);
        r15 = 2131689865; // 0x7f0f0189 float:1.9008757E38 double:1.05319473E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689865; // 0x7f0f0189 float:1.9008757E38 double:1.05319473E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r94 = r34;
        r94 = (android.widget.TextView) r94;
        r35 = r94;
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
    L_0x082e:
        r15 = 2131689866; // 0x7f0f018a float:1.900876E38 double:1.0531947304E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r95 = r34;
        r95 = (android.widget.TextView) r95;
        r35 = r95;
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getAddress();
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689847; // 0x7f0f0177 float:1.900872E38 double:1.053194721E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689745; // 0x7f0f0111 float:1.9008514E38 double:1.0531946706E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r96 = r34;
        r96 = (com.waze.ifs.ui.ObservableScrollView) r96;
        r47 = r96;
        r15 = 1;
        if (r10 != r15) goto L_0x088b;
    L_0x086e:
        r15 = 0;
        r0 = r169;
        r0.mLockHeight = r15;
        r0 = r169;
        r0 = r0.mScrollView;
        r97 = r0;
        r98 = new com.waze.navigate.AddressPreviewActivity$33;
        r0 = r98;
        r1 = r169;
        r2 = r47;
        r0.<init>(r2);
        r0 = r97;
        r1 = r98;
        r0.post(r1);
    L_0x088b:
        r0 = r169;
        r22 = r0.unvalidateCalendarMode();
        if (r22 == 0) goto L_0x09f8;
    L_0x0893:
        r0 = r169;
        r0 = r0.mMapView;
        r99 = r0;
        r15 = 4;
        r0 = r99;
        r0.setVisibility(r15);
        r15 = 2131689829; // 0x7f0f0165 float:1.9008684E38 double:1.053194712E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689828; // 0x7f0f0164 float:1.9008682E38 double:1.0531947116E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689823; // 0x7f0f015f float:1.9008672E38 double:1.053194709E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r100 = r34;
        r100 = (android.widget.TextView) r100;
        r35 = r100;
        r15 = 0;
        r0 = r35;
        r0.setVisibility(r15);
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1739; // 0x6cb float:2.437E-42 double:8.59E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689895; // 0x7f0f01a7 float:1.9008818E38 double:1.0531947447E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689894; // 0x7f0f01a6 float:1.9008816E38 double:1.053194744E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689904; // 0x7f0f01b0 float:1.9008837E38 double:1.053194749E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r102 = r34;
        r102 = (android.view.ViewGroup) r102;
        r101 = r102;
        r15 = 0;
        r0 = r101;
        r0.setVisibility(r15);
        r0 = r169;
        r0 = r0.calendarRemoveListener;
        r19 = r0;
        r0 = r101;
        r1 = r19;
        r0.setOnClickListener(r1);
        r15 = 2131689906; // 0x7f0f01b2 float:1.900884E38 double:1.05319475E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r103 = r34;
        r103 = (android.widget.TextView) r103;
        r35 = r103;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1741; // 0x6cd float:2.44E-42 double:8.6E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689907; // 0x7f0f01b3 float:1.9008843E38 double:1.0531947506E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r104 = r34;
        r104 = (android.view.ViewGroup) r104;
        r101 = r104;
        r15 = 0;
        r0 = r101;
        r0.setVisibility(r15);
        r105 = new com.waze.navigate.AddressPreviewActivity$34;
        r0 = r105;
        r1 = r169;
        r0.<init>();
        r0 = r101;
        r1 = r105;
        r0.setOnClickListener(r1);
        r15 = 2131689909; // 0x7f0f01b5 float:1.9008847E38 double:1.0531947516E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r106 = r34;
        r106 = (android.widget.TextView) r106;
        r35 = r106;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1742; // 0x6ce float:2.441E-42 double:8.607E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689848; // 0x7f0f0178 float:1.9008723E38 double:1.0531947215E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689851; // 0x7f0f017b float:1.900873E38 double:1.053194723E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r108 = r34;
        r108 = (com.waze.view.text.WazeTextView) r108;
        r107 = r108;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1740; // 0x6cc float:2.438E-42 double:8.597E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r107;
        r1 = r23;
        r0.setText(r1);
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130839115; // 0x7f02064b float:1.7283231E38 double:1.0527744035E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r0 = r107;
        r1 = r51;
        r0.setDrawableRight(r1);
        r15 = 2131689850; // 0x7f0f017a float:1.9008727E38 double:1.0531947225E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r0 = r169;
        r0 = r0.calendarSetLocationListener;
        r19 = r0;
        r0 = r34;
        r1 = r19;
        r0.setOnClickListener(r1);
    L_0x09f8:
        r0 = r169;
        r0.setupMoreMenu(r9);
        r15 = 2131689851; // 0x7f0f017b float:1.900873E38 double:1.053194723E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r109 = r34;
        r109 = (com.waze.view.text.WazeTextView) r109;
        r107 = r109;
        r0 = r169;
        r110 = r0.getIntent();
        r17 = "commute_mode";
        r0 = r110;
        r1 = r17;
        r23 = r0.getStringExtra(r1);
        if (r23 != 0) goto L_0x1595;
    L_0x0a1e:
        r0 = r169;
        r0.setupParking();
        return;
    L_0x0a24:
        r0 = r169;
        r0 = r0.mDtnMgr;
        r16 = r0;
        r0 = r169;
        r12 = r0.mAddressItem;
        r9 = r12.index;
        goto L_0x0a34;
    L_0x0a31:
        goto L_0x0048;
    L_0x0a34:
        r0 = r16;
        r0.notifyAddressItemShownBeforeNavigate(r9);
        goto L_0x0a31;
    L_0x0a3a:
        r15 = 3;
        if (r9 != r15) goto L_0x0a4a;
    L_0x0a3d:
        goto L_0x0a41;
    L_0x0a3e:
        goto L_0x0060;
    L_0x0a41:
        r17 = "WORK";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        goto L_0x0a3e;
    L_0x0a4a:
        r15 = 5;
        if (r9 != r15) goto L_0x0a5a;
    L_0x0a4d:
        goto L_0x0a51;
    L_0x0a4e:
        goto L_0x0060;
    L_0x0a51:
        r17 = "OTHER_FAV";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        goto L_0x0a4e;
    L_0x0a5a:
        r15 = 8;
        if (r9 != r15) goto L_0x0a6b;
    L_0x0a5e:
        goto L_0x0a62;
    L_0x0a5f:
        goto L_0x0060;
    L_0x0a62:
        r17 = "HISTORY";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        goto L_0x0a5f;
    L_0x0a6b:
        r15 = 13;
        if (r9 != r15) goto L_0x0a7c;
    L_0x0a6f:
        goto L_0x0a73;
    L_0x0a70:
        goto L_0x0060;
    L_0x0a73:
        r17 = "SHARED_LOCATION";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        goto L_0x0a70;
    L_0x0a7c:
        r15 = 14;
        if (r9 != r15) goto L_0x0a8d;
    L_0x0a80:
        goto L_0x0a84;
    L_0x0a81:
        goto L_0x0060;
    L_0x0a84:
        r17 = "DROPOFF";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        goto L_0x0a81;
    L_0x0a8d:
        r15 = 15;
        if (r9 != r15) goto L_0x0a9e;
    L_0x0a91:
        goto L_0x0a95;
    L_0x0a92:
        goto L_0x0060;
    L_0x0a95:
        r17 = "PICKUP";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        goto L_0x0a92;
    L_0x0a9e:
        r0 = r169;
        r0 = r0.mParkingMode;
        r22 = r0;
        if (r22 == 0) goto L_0x0060;
    L_0x0aa6:
        goto L_0x0aaa;
    L_0x0aa7:
        goto L_0x0060;
    L_0x0aaa:
        r17 = "PARKING";
        r0 = r17;
        r1 = r169;
        r1.mAnalyticsType = r0;
        goto L_0x0aa7;
        goto L_0x0ab7;
    L_0x0ab4:
        goto L_0x016c;
    L_0x0ab7:
        r23 = "";
        goto L_0x0ab4;
        goto L_0x0abe;
    L_0x0abb:
        goto L_0x019e;
    L_0x0abe:
        r22 = 0;
        goto L_0x0abb;
    L_0x0ac1:
        r37 = com.waze.NativeManager.getInstance();
        r0 = r37;
        r33 = r0.isSkipConfirmWaypointNTV();
        if (r33 == 0) goto L_0x0ae7;
    L_0x0acd:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1787; // 0x6fb float:2.504E-42 double:8.83E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        goto L_0x0adf;
    L_0x0adc:
        goto L_0x01e0;
    L_0x0adf:
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        goto L_0x0adc;
    L_0x0ae7:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1785; // 0x6f9 float:2.501E-42 double:8.82E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r23;
        r23 = r0.toUpperCase();
        goto L_0x0aff;
    L_0x0afc:
        goto L_0x01e0;
    L_0x0aff:
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        goto L_0x0afc;
    L_0x0b07:
        r15 = 2131689824; // 0x7f0f0160 float:1.9008674E38 double:1.0531947096E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        goto L_0x0b14;
    L_0x0b11:
        goto L_0x020e;
    L_0x0b14:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        goto L_0x0b11;
    L_0x0b1c:
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getTitle();
        r0 = r35;
        r1 = r23;
        r0.setText(r1);
        r0 = r169;
        r0 = r0.mTitleBar;
        r40 = r0;
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getTitle();
        goto L_0x0b3d;
    L_0x0b3a:
        goto L_0x0253;
    L_0x0b3d:
        r0 = r40;
        r1 = r23;
        r0.setTitle(r1);
        goto L_0x0b3a;
    L_0x0b45:
        r15 = 0;
        r0 = r41;
        r0.setVisibility(r15);
        goto L_0x0b4f;
    L_0x0b4c:
        goto L_0x02dc;
    L_0x0b4f:
        r0 = r41;
        r1 = r25;
        r0.setText(r1);
        goto L_0x0b4c;
    L_0x0b57:
        r15 = 0;
        r0 = r41;
        r0.setVisibility(r15);
        r0 = r169;
        r12 = r0.mAddressItem;
        r23 = r12.getDistance();
        goto L_0x0b69;
    L_0x0b66:
        goto L_0x0310;
    L_0x0b69:
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        goto L_0x0b66;
    L_0x0b71:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838826; // 0x7f02052a float:1.7282645E38 double:1.0527742608E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        goto L_0x0b84;
    L_0x0b81:
        goto L_0x0396;
    L_0x0b84:
        r20 = 0;
        r52 = 0;
        r53 = 0;
        r0 = r41;
        r1 = r51;
        r2 = r20;
        r3 = r52;
        r4 = r53;
        r0.setCompoundDrawablesWithIntrinsicBounds(r1, r2, r3, r4);
        goto L_0x0b81;
        goto L_0x0b9c;
    L_0x0b99:
        goto L_0x03a6;
    L_0x0b9c:
        r15 = 8;
        r0 = r41;
        r0.setVisibility(r15);
        goto L_0x0b99;
    L_0x0ba4:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1811; // 0x713 float:2.538E-42 double:8.948E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1812; // 0x714 float:2.539E-42 double:8.95E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r56;
        r1 = r23;
        r0.setText(r1);
        r33 = 0;
        r58 = 0;
    L_0x0bd2:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numOpeningHours;
        r111 = r0;
        r0 = r58;
        r1 = r111;
        if (r0 >= r1) goto L_0x0bfe;
    L_0x0be4:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.openingHours;
        r112 = r0;
        r113 = r112[r58];
        r0 = r169;
        r1 = r113;
        r114 = r0.isOpen(r1);
        if (r114 == 0) goto L_0x0c19;
    L_0x0bfc:
        r33 = 1;
    L_0x0bfe:
        if (r33 == 0) goto L_0x0c1c;
    L_0x0c00:
        r115 = 0;
    L_0x0c02:
        r0 = r41;
        r1 = r115;
        r0.setVisibility(r1);
        if (r33 == 0) goto L_0x0c1f;
    L_0x0c0b:
        r115 = 8;
        goto L_0x0c11;
    L_0x0c0e:
        goto L_0x03e2;
    L_0x0c11:
        r0 = r56;
        r1 = r115;
        r0.setVisibility(r1);
        goto L_0x0c0e;
    L_0x0c19:
        r58 = r58 + 1;
        goto L_0x0bd2;
    L_0x0c1c:
        r115 = 8;
        goto L_0x0c02;
    L_0x0c1f:
        r115 = 0;
        goto L_0x0c11;
    L_0x0c22:
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689888; // 0x7f0f01a0 float:1.9008804E38 double:1.0531947412E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r116 = r34;
        r116 = (com.waze.view.text.WazeTextView) r116;
        r107 = r116;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 748; // 0x2ec float:1.048E-42 double:3.696E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r107;
        r1 = r23;
        r0.setText(r1);
        r117 = new com.waze.navigate.AddressPreviewActivity$21;
        r0 = r117;
        r1 = r169;
        r0.<init>();
        r0 = r117;
        com.waze.NativeManager.Post(r0);
        r17 = "AddressPreviewActivity: Ad identified, set to clear context if not set by intent";
        r0 = r17;
        com.waze.Logger.m36d(r0);
        r0 = r169;
        r110 = r0.getIntent();
        if (r110 == 0) goto L_0x0c7b;
    L_0x0c69:
        r0 = r169;
        r110 = r0.getIntent();
        r17 = "ClearAdsContext";
        r0 = r110;
        r1 = r17;
        r33 = r0.hasExtra(r1);
        if (r33 != 0) goto L_0x0c80;
    L_0x0c7b:
        r15 = 1;
        r0 = r169;
        r0.mIsClearAdsContext = r15;
    L_0x0c80:
        r15 = 2131689887; // 0x7f0f019f float:1.9008802E38 double:1.053194741E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r119 = r34;
        r119 = (com.waze.WzWebView) r119;
        r118 = r119;
        r0 = r118;
        r1 = r169;
        r1.mWebView = r0;
        r0 = r169;
        r0 = r0.mWebView;
        r118 = r0;
        r15 = 65536; // 0x10000 float:9.18355E-41 double:3.2379E-319;
        r0 = r118;
        r0.setFlags(r15);
        r0 = r169;
        r0 = r0.mWebView;
        r118 = r0;
        r0 = r169;
        r0 = r0.mSpecialUrlOverride;
        r120 = r0;
        r0 = r118;
        r1 = r120;
        r0.setUrlOverride(r1);
        r0 = r169;
        r0 = r0.mWebView;
        r118 = r0;
        r121 = new com.waze.navigate.AddressPreviewActivity$WebViewInterface;
        r20 = 0;
        r0 = r121;
        r1 = r169;
        r2 = r20;
        r0.<init>();
        r17 = "Android";
        r0 = r118;
        r1 = r121;
        r2 = r17;
        r0.addJavascriptInterface(r1, r2);
        r0 = r169;
        r0 = r0.mWebView;
        r118 = r0;
        r122 = new com.waze.navigate.AddressPreviewActivity$22;
        r0 = r122;
        r1 = r169;
        r2 = r61;
        r0.<init>(r2);
        r0 = r118;
        r1 = r122;
        r0.setPageProgressCallback(r1);
        r0 = r169;
        r0 = r0.mWebView;
        r118 = r0;
        r0 = r61;
        r0 = r0.url;
        r23 = r0;
        goto L_0x0cfc;
    L_0x0cf9:
        goto L_0x0446;
    L_0x0cfc:
        r0 = r118;
        r1 = r23;
        r0.loadUrl(r1);
        goto L_0x0cf9;
    L_0x0d04:
        r15 = 2131689895; // 0x7f0f01a7 float:1.9008818E38 double:1.0531947447E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689894; // 0x7f0f01a6 float:1.9008816E38 double:1.053194744E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689896; // 0x7f0f01a8 float:1.900882E38 double:1.053194745E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 2131689898; // 0x7f0f01aa float:1.9008824E38 double:1.053194746E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r123 = r60;
        r123 = (android.widget.TextView) r123;
        r41 = r123;
        r15 = 2131689897; // 0x7f0f01a9 float:1.9008822E38 double:1.0531947457E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r124 = r60;
        r124 = (android.widget.ImageView) r124;
        r69 = r124;
        if (r22 != 0) goto L_0x0ed7;
    L_0x0d4b:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.phoneNumber;
        r23 = r0;
        r33 = android.text.TextUtils.isEmpty(r0);
        if (r33 != 0) goto L_0x0ed7;
    L_0x0d5d:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.phoneNumber;
        r23 = r0;
    L_0x0d69:
        r33 = 0;
        r0 = r23;
        r114 = r0.isEmpty();
        if (r114 != 0) goto L_0x0dc3;
    L_0x0d73:
        r33 = 1;
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r17 = "<u>";
        r0 = r24;
        r1 = r17;
        r26 = r0.append(r1);
        r0 = r26;
        r1 = r23;
        r26 = r0.append(r1);
        r17 = "</u>";
        r0 = r26;
        r1 = r17;
        r26 = r0.append(r1);
        r0 = r26;
        r25 = r0.toString();
        r0 = r25;
        r125 = android.text.Html.fromHtml(r0);
        r0 = r41;
        r1 = r125;
        r0.setText(r1);
        r126 = new com.waze.navigate.AddressPreviewActivity$23;
        r0 = r126;
        r1 = r169;
        r2 = r23;
        r0.<init>(r2);
        r0 = r34;
        r1 = r126;
        r0.setOnClickListener(r1);
    L_0x0dc3:
        r15 = 2131689900; // 0x7f0f01ac float:1.9008828E38 double:1.053194747E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r15 = 2131689902; // 0x7f0f01ae float:1.9008832E38 double:1.053194748E-314;
        r0 = r169;
        r67 = r0.findViewById(r15);
        r127 = r67;
        r127 = (android.widget.TextView) r127;
        r41 = r127;
        if (r22 != 0) goto L_0x0e83;
    L_0x0ddd:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.website;
        r23 = r0;
        r114 = android.text.TextUtils.isEmpty(r0);
        if (r114 != 0) goto L_0x0e83;
    L_0x0def:
        r33 = 1;
        r15 = 0;
        r0 = r60;
        r0.setVisibility(r15);
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.websiteDisplayText;
        r23 = r0;
        r25 = r23;
        if (r23 == 0) goto L_0x0e0f;
    L_0x0e07:
        r0 = r23;
        r114 = r0.isEmpty();
        if (r114 == 0) goto L_0x0e6c;
    L_0x0e0f:
        r24 = new java.lang.StringBuilder;
        r0 = r24;
        r0.<init>();
        r17 = "<u>";
        r0 = r24;
        r1 = r17;
        r26 = r0.append(r1);
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.website;
        r23 = r0;
        r17 = "http://";
        r27 = "";
        r0 = r23;
        r1 = r17;
        r2 = r27;
        r23 = r0.replaceFirst(r1, r2);
        r17 = "https://";
        r27 = "";
        r0 = r23;
        r1 = r17;
        r2 = r27;
        r23 = r0.replaceFirst(r1, r2);
        r0 = r26;
        r1 = r23;
        r26 = r0.append(r1);
        r17 = "</u>";
        r0 = r26;
        r1 = r17;
        r26 = r0.append(r1);
        r0 = r26;
        r23 = r0.toString();
        r0 = r23;
        r125 = android.text.Html.fromHtml(r0);
        r0 = r125;
        r25 = r0.toString();
    L_0x0e6c:
        r0 = r41;
        r1 = r25;
        r0.setText(r1);
        r128 = new com.waze.navigate.AddressPreviewActivity$24;
        r0 = r128;
        r1 = r169;
        r0.<init>();
        r0 = r60;
        r1 = r128;
        r0.setOnClickListener(r1);
    L_0x0e83:
        r0 = r34;
        r58 = r0.getVisibility();
        if (r58 != 0) goto L_0x0ec1;
    L_0x0e8b:
        r0 = r60;
        r58 = r0.getVisibility();
        if (r58 != 0) goto L_0x0ec1;
    L_0x0e93:
        r15 = 2131689899; // 0x7f0f01ab float:1.9008826E38 double:1.0531947467E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689895; // 0x7f0f01a7 float:1.9008818E38 double:1.0531947447E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r129 = new com.waze.navigate.AddressPreviewActivity$25;
        r0 = r129;
        r1 = r169;
        r2 = r34;
        r3 = r69;
        r4 = r41;
        r0.<init>(r2, r3, r4);
        r0 = r34;
        r1 = r129;
        r0.addOnLayoutChangeListener(r1);
    L_0x0ec1:
        if (r33 == 0) goto L_0x0ee4;
    L_0x0ec3:
        r15 = 2131689894; // 0x7f0f01a6 float:1.9008816E38 double:1.053194744E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        goto L_0x0ed0;
    L_0x0ecd:
        goto L_0x0476;
    L_0x0ed0:
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        goto L_0x0ecd;
    L_0x0ed7:
        r0 = r169;
        r12 = r0.mAddressItem;
        goto L_0x0edf;
    L_0x0edc:
        goto L_0x0d69;
    L_0x0edf:
        r23 = r12.getPhone();
        goto L_0x0edc;
    L_0x0ee4:
        r15 = 2131689894; // 0x7f0f01a6 float:1.9008816E38 double:1.053194744E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        goto L_0x0ef1;
    L_0x0eee:
        goto L_0x0476;
    L_0x0ef1:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        goto L_0x0eee;
    L_0x0ef9:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1796; // 0x704 float:2.517E-42 double:8.873E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 0;
        r0 = r41;
        r0.setVisibility(r15);
        r15 = 0;
        r0 = r63;
        r0.setVisibility(r15);
        r0 = r63;
        r48 = r0.getViewTreeObserver();
        r130 = new com.waze.navigate.AddressPreviewActivity$26;
        r0 = r130;
        r1 = r169;
        r2 = r63;
        r0.<init>(r2);
        goto L_0x0f35;
    L_0x0f32:
        goto L_0x04c2;
    L_0x0f35:
        r0 = r48;
        r1 = r130;
        r0.addOnGlobalLayoutListener(r1);
        goto L_0x0f32;
    L_0x0f3d:
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 0;
        r0 = r63;
        r0.setVisibility(r15);
        r15 = 0;
        r0 = r41;
        r0.setVisibility(r15);
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1795; // 0x703 float:2.515E-42 double:8.87E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r0 = r63;
        r0.removeAllViews();
        r58 = 0;
    L_0x0f6b:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numOpeningHours;
        r111 = r0;
        r0 = r58;
        r1 = r111;
        if (r0 >= r1) goto L_0x050e;
    L_0x0f7d:
        if (r58 == 0) goto L_0x0fb6;
    L_0x0f7f:
        r60 = new android.view.View;
        r34 = r60;
        r0 = r60;
        r1 = r169;
        r0.<init>(r1);
        r15 = 2130838706; // 0x7f0204b2 float:1.7282402E38 double:1.0527742015E-314;
        r0 = r34;
        r0.setBackgroundResource(r15);
        r15 = 1;
        r20 = 0;
        r0 = r34;
        r1 = r20;
        r0.setLayerType(r15, r1);
        r0 = r169;
        r0 = r0.mDensity;
        r131 = r0;
        r132 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r131 = r132 * r131;
        r0 = r131;
        r0 = (int) r0;
        r111 = r0;
        r15 = -1;
        r0 = r63;
        r1 = r34;
        r2 = r111;
        r0.addView(r1, r15, r2);
    L_0x0fb6:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.openingHours;
        r112 = r0;
        r113 = r112[r58];
        goto L_0x0fc8;
    L_0x0fc5:
        goto L_0x0f6b;
    L_0x0fc8:
        r0 = r169;
        r133 = r0.getLayoutInflater();
        r15 = 2130903086; // 0x7f03002e float:1.741298E38 double:1.0528060094E-314;
        r134 = 0;
        r0 = r133;
        r1 = r63;
        r2 = r134;
        r34 = r0.inflate(r15, r1, r2);
        r15 = 2131689935; // 0x7f0f01cf float:1.90089E38 double:1.0531947645E-314;
        r0 = r34;
        r60 = r0.findViewById(r15);
        r135 = r60;
        r135 = (android.widget.TextView) r135;
        r41 = r135;
        r17 = ", ";
        r0 = r113;
        r1 = r17;
        r23 = r0.getDaysString(r1);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689936; // 0x7f0f01d0 float:1.9008901E38 double:1.053194765E-314;
        r0 = r34;
        r60 = r0.findViewById(r15);
        r136 = r60;
        r136 = (android.widget.TextView) r136;
        r41 = r136;
        r0 = r113;
        r23 = r0.getHoursString();
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r0 = r63;
        r1 = r34;
        r0.addView(r1);
        r58 = r58 + 1;
        goto L_0x0fc5;
    L_0x1023:
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 0;
        r0 = r60;
        r0.setVisibility(r15);
        r15 = 0;
        r0 = r41;
        r0.setVisibility(r15);
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1797; // 0x705 float:2.518E-42 double:8.88E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689919; // 0x7f0f01bf float:1.9008867E38 double:1.0531947566E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r137 = r34;
        r137 = (android.widget.TextView) r137;
        r41 = r137;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.about;
        r23 = r0;
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689921; // 0x7f0f01c1 float:1.9008871E38 double:1.0531947576E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r138 = r34;
        r138 = (android.widget.TextView) r138;
        r56 = r138;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1809; // 0x711 float:2.535E-42 double:8.94E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r56;
        r1 = r23;
        r0.setText(r1);
        r139 = new com.waze.navigate.AddressPreviewActivity$27;
        r0 = r139;
        r1 = r169;
        r2 = r41;
        r3 = r56;
        r0.<init>(r2, r3);
        r0 = r56;
        r1 = r139;
        r0.setOnClickListener(r1);
        r140 = new com.waze.navigate.AddressPreviewActivity$28;
        r0 = r140;
        r1 = r169;
        r2 = r56;
        r3 = r41;
        r0.<init>(r2, r3);
        goto L_0x10b5;
    L_0x10b2:
        goto L_0x0558;
    L_0x10b5:
        r0 = r56;
        r1 = r140;
        r0.addOnLayoutChangeListener(r1);
        goto L_0x10b2;
    L_0x10bd:
        r0 = r169;
        r0 = r0.mPlaceImagesFrame;
        r34 = r0;
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689838; // 0x7f0f016e float:1.9008703E38 double:1.0531947165E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numImages;
        r58 = r0;
        r15 = 1;
        r0 = r58;
        if (r0 <= r15) goto L_0x11a7;
    L_0x10e3:
        r115 = 0;
    L_0x10e5:
        r0 = r34;
        r1 = r115;
        r0.setVisibility(r1);
        r15 = 2131689841; // 0x7f0f0171 float:1.9008709E38 double:1.053194718E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r141 = r34;
        r141 = (android.widget.ImageView) r141;
        r69 = r141;
        r142 = new java.util.ArrayList;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numImages;
        r58 = r0;
        r0 = r142;
        r1 = r58;
        r0.<init>(r1);
        r0 = r142;
        r1 = r169;
        r1.mImageArray = r0;
        r58 = 0;
    L_0x1118:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.numImages;
        r111 = r0;
        r0 = r58;
        r1 = r111;
        if (r0 >= r1) goto L_0x11aa;
    L_0x112a:
        r0 = r169;
        r8 = r0.mImageArray;
        r143 = new com.waze.reports.PhotoPagerSection$VenueImage;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.imageURLs;
        r144 = r0;
        r23 = r144[r58];
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        goto L_0x114a;
    L_0x1147:
        goto L_0x10e5;
    L_0x114a:
        r0 = r0.imageThumbnailURLs;
        r144 = r0;
        r25 = r144[r58];
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.imageReporters;
        r144 = r0;
        r43 = r144[r58];
        goto L_0x1162;
    L_0x115f:
        goto L_0x1118;
    L_0x1162:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.imageReporterMoods;
        r144 = r0;
        r145 = r144[r58];
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.imageLiked;
        r146 = r0;
        r22 = r146[r58];
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.imageByMe;
        r146 = r0;
        r33 = r146[r58];
        r15 = 0;
        r0 = r143;
        r1 = r23;
        r2 = r25;
        r3 = r43;
        r4 = r145;
        r5 = r22;
        r6 = r33;
        r7 = r15;
        r0.<init>(r1, r2, r3, r4, r5, r6, r7);
        r0 = r143;
        r8.add(r0);
        r58 = r58 + 1;
        goto L_0x115f;
    L_0x11a7:
        r115 = 4;
        goto L_0x1147;
    L_0x11aa:
        r0 = r169;
        r8 = r0.mImageArray;
        r15 = 0;
        r11 = r8.get(r15);
        r148 = r11;
        r148 = (com.waze.reports.PhotoPagerSection.VenueImage) r148;
        r147 = r148;
        r15 = 2131689840; // 0x7f0f0170 float:1.9008707E38 double:1.0531947175E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r150 = r34;
        r150 = (com.waze.view.map.ProgressAnimation) r150;
        r149 = r150;
        r151 = com.waze.utils.ImageRepository.instance;
        r0 = r147;
        r0 = r0.imageThumbnailUri;
        r23 = r0;
        r0 = r151;
        r1 = r23;
        r22 = r0.isThumbnailCached(r1);
        if (r22 == 0) goto L_0x1219;
    L_0x11da:
        r15 = 8;
        r0 = r149;
        r0.setVisibility(r15);
    L_0x11e1:
        r151 = com.waze.utils.ImageRepository.instance;
        r0 = r147;
        r0 = r0.imageThumbnailUri;
        r23 = r0;
        r152 = r149;
        r152 = (android.view.View) r152;
        r34 = r152;
        r15 = 1;
        r17 = "LATENCY_VENUE_IMAGE";
        r0 = r151;
        r1 = r23;
        r2 = r15;
        r3 = r69;
        r4 = r34;
        r5 = r169;
        r6 = r17;
        r0.getImage(r1, r2, r3, r4, r5, r6);
        r153 = new com.waze.navigate.AddressPreviewActivity$29;
        r0 = r153;
        r1 = r169;
        r2 = r69;
        r0.<init>(r2);
        goto L_0x1211;
    L_0x120e:
        goto L_0x0584;
    L_0x1211:
        r0 = r69;
        r1 = r153;
        r0.setOnClickListener(r1);
        goto L_0x120e;
    L_0x1219:
        r0 = r149;
        r0.start();
        goto L_0x11e1;
    L_0x121f:
        r154 = com.waze.ResourceDownloadType.RES_DOWNLOAD_IMAGE_JAVA;
        r0 = r154;
        r58 = r0.getValue();
        r155 = new com.waze.navigate.AddressPreviewActivity$30;
        r0 = r155;
        r1 = r169;
        r2 = r23;
        r3 = r34;
        r4 = r69;
        r0.<init>(r2, r3, r4);
        goto L_0x123a;
    L_0x1237:
        goto L_0x0621;
    L_0x123a:
        r0 = r58;
        r1 = r23;
        r2 = r155;
        com.waze.ResManager.downloadRes(r0, r1, r2);
        goto L_0x1237;
    L_0x1244:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838101; // 0x7f020255 float:1.7281175E38 double:1.0527739026E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r156 = r51;
        r156 = (android.graphics.drawable.BitmapDrawable) r156;
        r75 = r156;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.mImageURL;
        r23 = r0;
        if (r23 == 0) goto L_0x06e5;
    L_0x1280:
        r151 = com.waze.utils.ImageRepository.instance;
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        goto L_0x128c;
    L_0x1289:
        goto L_0x06e5;
    L_0x128c:
        r15 = 2;
        r20 = 0;
        r0 = r151;
        r1 = r23;
        r2 = r15;
        r3 = r69;
        r4 = r20;
        r5 = r169;
        r0.getImage(r1, r2, r3, r4, r5);
        goto L_0x1289;
    L_0x129e:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838234; // 0x7f0202da float:1.7281445E38 double:1.0527739683E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r157 = r51;
        r157 = (android.graphics.drawable.BitmapDrawable) r157;
        r75 = r157;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        goto L_0x12cd;
    L_0x12ca:
        goto L_0x06e5;
    L_0x12cd:
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        goto L_0x12ca;
    L_0x12d5:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838123; // 0x7f02026b float:1.728122E38 double:1.0527739134E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r158 = r51;
        r158 = (android.graphics.drawable.BitmapDrawable) r158;
        r75 = r158;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        goto L_0x1304;
    L_0x1301:
        goto L_0x06e5;
    L_0x1304:
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        goto L_0x1301;
    L_0x130c:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130838238; // 0x7f0202de float:1.7281453E38 double:1.05277397E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r159 = r51;
        r159 = (android.graphics.drawable.BitmapDrawable) r159;
        r75 = r159;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        goto L_0x133b;
    L_0x1338:
        goto L_0x06e5;
    L_0x133b:
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        goto L_0x1338;
    L_0x1343:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130839528; // 0x7f0207e8 float:1.728407E38 double:1.0527746076E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r160 = r51;
        r160 = (android.graphics.drawable.BitmapDrawable) r160;
        r75 = r160;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        goto L_0x1372;
    L_0x136f:
        goto L_0x06e5;
    L_0x1372:
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        goto L_0x136f;
    L_0x137a:
        r0 = r169;
        r45 = r0.getResources();
        r15 = 2130837797; // 0x7f020125 float:1.7280558E38 double:1.0527737524E-314;
        r0 = r45;
        r51 = r0.getDrawable(r15);
        r161 = r51;
        r161 = (android.graphics.drawable.BitmapDrawable) r161;
        r75 = r161;
        r0 = r75;
        r77 = r0.getBitmap();
        r0 = r169;
        r0 = r0.mLogoImg;
        r69 = r0;
        r78 = new com.waze.ifs.ui.CircleShaderDrawable;
        r15 = 0;
        r0 = r78;
        r1 = r77;
        r0.<init>(r1, r15);
        goto L_0x13a9;
    L_0x13a6:
        goto L_0x06e5;
    L_0x13a9:
        r0 = r69;
        r1 = r78;
        r0.setImageDrawable(r1);
        goto L_0x13a6;
    L_0x13b1:
        r15 = 2131689842; // 0x7f0f0172 float:1.900871E38 double:1.0531947185E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r22 = r0.getLanguageRtl();
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.reporter;
        r23 = r0;
        if (r23 == 0) goto L_0x13ea;
    L_0x13d8:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.reporter;
        r23 = r0;
        r33 = r0.isEmpty();
        if (r33 == 0) goto L_0x142f;
    L_0x13ea:
        r15 = 2131689854; // 0x7f0f017e float:1.9008735E38 double:1.0531947244E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
    L_0x13fa:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.updater;
        r23 = r0;
        if (r23 == 0) goto L_0x141a;
    L_0x1408:
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.updater;
        r23 = r0;
        r33 = r0.isEmpty();
        if (r33 == 0) goto L_0x14d3;
    L_0x141a:
        r15 = 2131689858; // 0x7f0f0182 float:1.9008743E38 double:1.0531947264E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        goto L_0x1427;
    L_0x1424:
        goto L_0x073f;
    L_0x1427:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        goto L_0x1424;
    L_0x142f:
        r15 = 2131689854; // 0x7f0f017e float:1.9008735E38 double:1.0531947244E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689855; // 0x7f0f017f float:1.9008737E38 double:1.053194725E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r162 = r60;
        r162 = (android.widget.ImageView) r162;
        r69 = r162;
        r15 = 2131689856; // 0x7f0f0180 float:1.900874E38 double:1.0531947254E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r163 = r60;
        r163 = (android.widget.TextView) r163;
        r41 = r163;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        goto L_0x1466;
    L_0x1463:
        goto L_0x13fa;
    L_0x1466:
        r15 = 1534; // 0x5fe float:2.15E-42 double:7.58E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689857; // 0x7f0f0181 float:1.9008741E38 double:1.053194726E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r164 = r60;
        r164 = (android.widget.TextView) r164;
        r56 = r164;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.reporter;
        r23 = r0;
        r0 = r56;
        r1 = r23;
        r0.setText(r1);
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.reporterMood;
        r23 = r0;
        r0 = r169;
        r1 = r23;
        r51 = com.waze.MoodManager.getMoodDrawable(r0, r1);
        r0 = r69;
        r1 = r51;
        r0.setImageDrawable(r1);
        if (r22 == 0) goto L_0x13fa;
    L_0x14b4:
        r132 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r0 = r34;
        r1 = r132;
        r0.setScaleX(r1);
        r132 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r0 = r41;
        r1 = r132;
        r0.setScaleX(r1);
        r132 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r0 = r56;
        r1 = r132;
        r0.setScaleX(r1);
        goto L_0x1463;
    L_0x14d3:
        r15 = 2131689858; // 0x7f0f0182 float:1.9008743E38 double:1.0531947264E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 0;
        r0 = r34;
        r0.setVisibility(r15);
        r15 = 2131689859; // 0x7f0f0183 float:1.9008745E38 double:1.053194727E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r165 = r60;
        r165 = (android.widget.ImageView) r165;
        r69 = r165;
        r15 = 2131689860; // 0x7f0f0184 float:1.9008747E38 double:1.0531947274E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r166 = r60;
        r166 = (android.widget.TextView) r166;
        r41 = r166;
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1535; // 0x5ff float:2.151E-42 double:7.584E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r41;
        r1 = r23;
        r0.setText(r1);
        r15 = 2131689861; // 0x7f0f0185 float:1.900875E38 double:1.053194728E-314;
        r0 = r169;
        r60 = r0.findViewById(r15);
        r167 = r60;
        r167 = (android.widget.TextView) r167;
        r56 = r167;
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.updater;
        r23 = r0;
        r0 = r56;
        r1 = r23;
        r0.setText(r1);
        r0 = r169;
        r12 = r0.mAddressItem;
        r0 = r12.venueData;
        r32 = r0;
        r0 = r0.updaterMood;
        r23 = r0;
        r0 = r169;
        r1 = r23;
        r51 = com.waze.MoodManager.getMoodDrawable(r0, r1);
        r0 = r69;
        r1 = r51;
        r0.setImageDrawable(r1);
        if (r22 == 0) goto L_0x073f;
    L_0x1554:
        r132 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r0 = r34;
        r1 = r132;
        r0.setScaleX(r1);
        r132 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r0 = r41;
        r1 = r132;
        r0.setScaleX(r1);
        goto L_0x156c;
    L_0x1569:
        goto L_0x073f;
    L_0x156c:
        r132 = -1082130432; // 0xffffffffbf800000 float:-1.0 double:NaN;
        r0 = r56;
        r1 = r132;
        r0.setScaleX(r1);
        goto L_0x1569;
    L_0x1577:
        r17 = "";
        r0 = r35;
        r1 = r17;
        r0.setText(r1);
        r15 = 2131689865; // 0x7f0f0189 float:1.9008757E38 double:1.05319473E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        goto L_0x158d;
    L_0x158a:
        goto L_0x082e;
    L_0x158d:
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        goto L_0x158a;
    L_0x1595:
        r17 = "home";
        r0 = r23;
        r1 = r17;
        r22 = r0.contains(r1);
        r17 = "_go";
        r0 = r23;
        r1 = r17;
        r33 = r0.contains(r1);
        r15 = 2131689848; // 0x7f0f0178 float:1.9008723E38 double:1.0531947215E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r15 = 8;
        r0 = r34;
        r0.setVisibility(r15);
        if (r22 == 0) goto L_0x1623;
    L_0x15bb:
        if (r33 == 0) goto L_0x160d;
    L_0x15bd:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1828; // 0x724 float:2.562E-42 double:9.03E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r107;
        r1 = r23;
        r0.setText(r1);
    L_0x15d2:
        r20 = 0;
        r0 = r107;
        r1 = r20;
        r0.setDrawableRight(r1);
        r0 = r169;
        r45 = r0.getResources();
        if (r22 == 0) goto L_0x1651;
    L_0x15e3:
        r9 = 2130839156; // 0x7f020674 float:1.7283315E38 double:1.052774424E-314;
    L_0x15e6:
        r0 = r45;
        r51 = r0.getDrawable(r9);
        r0 = r107;
        r1 = r51;
        r0.setDrawableLeft(r1);
        r15 = 2131689850; // 0x7f0f017a float:1.9008727E38 double:1.0531947225E-314;
        r0 = r169;
        r34 = r0.findViewById(r15);
        r168 = new com.waze.navigate.AddressPreviewActivity$35;
        r0 = r168;
        r1 = r169;
        r0.<init>();
        r0 = r34;
        r1 = r168;
        r0.setOnClickListener(r1);
        return;
    L_0x160d:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1827; // 0x723 float:2.56E-42 double:9.027E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r107;
        r1 = r23;
        r0.setText(r1);
        goto L_0x15d2;
    L_0x1623:
        if (r33 == 0) goto L_0x163b;
    L_0x1625:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1830; // 0x726 float:2.564E-42 double:9.04E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r107;
        r1 = r23;
        r0.setText(r1);
        goto L_0x15d2;
    L_0x163b:
        r0 = r169;
        r0 = r0.mNatMgr;
        r37 = r0;
        r15 = 1829; // 0x725 float:2.563E-42 double:9.036E-321;
        r0 = r37;
        r23 = r0.getLanguageString(r15);
        r0 = r107;
        r1 = r23;
        r0.setText(r1);
        goto L_0x15d2;
    L_0x1651:
        r9 = 2130839157; // 0x7f020675 float:1.7283317E38 double:1.0527744243E-314;
        goto L_0x15e6;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.AddressPreviewActivity.refreshView():void");
    }

    private void setUpActivity() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:56:0x0602
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r61 = this;
        r5 = 2130903081; // 0x7f030029 float:1.741297E38 double:1.052806007E-314;
        r0 = r61;
        r0.setContentView(r5);
        r0 = r61;
        r6 = r0.getIntent();
        r7 = r6.getExtras();
        r9 = "AddressItemList";
        r8 = r7.getSerializable(r9);
        r11 = r8;
        r11 = (java.util.ArrayList) r11;
        r10 = r11;
        r0 = r61;
        r0.mAddressItemList = r10;
        r0 = r61;
        r6 = r0.getIntent();
        r7 = r6.getExtras();
        r9 = "AddressItemSelected";
        r12 = r7.getInt(r9);
        r0 = r61;
        r0.mSelectedItem = r12;
        r0 = r61;
        r10 = r0.mAddressItemList;
        if (r10 == 0) goto L_0x0044;
    L_0x003a:
        r0 = r61;
        r10 = r0.mAddressItemList;
        r12 = r10.size();
        if (r12 != 0) goto L_0x0412;
    L_0x0044:
        r0 = r61;
        r6 = r0.getIntent();
        r7 = r6.getExtras();
        r9 = "AddressItem";
        r8 = r7.getSerializable(r9);
        r14 = r8;
        r14 = (com.waze.navigate.AddressItem) r14;
        r13 = r14;
        r0 = r61;
        r0.mAddressItem = r13;
    L_0x005c:
        r0 = r61;
        r0.setupPreview();
        r0 = r61;
        r6 = r0.getIntent();
        r9 = "AddressItem";
        r15 = r6.hasExtra(r9);
        if (r15 == 0) goto L_0x042d;
    L_0x006f:
        r0 = r61;
        r6 = r0.getIntent();
        r7 = r6.getExtras();
        r9 = "AddressItem";
        r8 = r7.getSerializable(r9);
        r16 = r8;
        r16 = (com.waze.navigate.AddressItem) r16;
        r13 = r16;
        r0 = r61;
        r0.mEventAddressItem = r13;
    L_0x0089:
        r5 = 2131689674; // 0x7f0f00ca float:1.900837E38 double:1.0531946355E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r19 = r17;
        r19 = (com.waze.view.title.TitleBar) r19;
        r18 = r19;
        r0 = r18;
        r1 = r61;
        r1.mTitleBar = r0;
        r0 = r61;
        r0 = r0.mTitleBar;
        r18 = r0;
        r0 = r61;
        r13 = r0.mAddressItem;
        r20 = r13.getTitle();
        r0 = r18;
        r1 = r61;
        r2 = r20;
        r0.init(r1, r2);
        r5 = 2131689872; // 0x7f0f0190 float:1.9008772E38 double:1.0531947333E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r1 = r61;
        r1.mDangerZoneWarning = r0;
        r0 = r61;
        r0 = r0.mDangerZoneWarning;
        r17 = r0;
        r5 = 8;
        r0 = r17;
        r0.setVisibility(r5);
        r0 = r61;
        r13 = r0.mAddressItem;
        if (r13 == 0) goto L_0x00e5;
    L_0x00d7:
        r21 = new com.waze.navigate.AddressPreviewActivity$6;
        r0 = r21;
        r1 = r61;
        r0.<init>();
        r0 = r21;
        com.waze.NativeManager.Post(r0);
    L_0x00e5:
        r0 = r61;
        r0 = r0.mTitleBar;
        r18 = r0;
        r5 = 2131692429; // 0x7f0f0b8d float:1.9013958E38 double:1.0531959967E-314;
        r0 = r18;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r1 = r61;
        r1.mBackToMapButton = r0;
        r5 = 2131689843; // 0x7f0f0173 float:1.9008713E38 double:1.053194719E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r1 = r61;
        r1.mBackUpButton = r0;
        r0 = r61;
        r0 = r0.mBackUpButton;
        r17 = r0;
        r22 = new com.waze.navigate.AddressPreviewActivity$7;
        r0 = r22;
        r1 = r61;
        r0.<init>();
        r0 = r17;
        r1 = r22;
        r0.setOnClickListener(r1);
        r5 = 2131689844; // 0x7f0f0174 float:1.9008715E38 double:1.0531947195E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r1 = r61;
        r1.mTitleBarShadow = r0;
        r0 = r61;
        r23 = r0.getResources();
        r5 = 2131361945; // 0x7f0a0099 float:1.8343657E38 double:1.053032716E-314;
        r0 = r23;
        r12 = r0.getDimensionPixelSize(r5);
        r0 = r61;
        r0 = r0.mDensity;
        r24 = r0;
        r25 = 1086324736; // 0x40c00000 float:6.0 double:5.367157323E-315;
        r24 = r25 * r24;
        r0 = r24;
        r0 = (int) r0;
        r26 = r0;
        r12 = r12 + r0;
        r0 = r61;
        r0.mTitleBarHeight = r12;
        r25 = 0;
        r0 = r61;
        r1 = r25;
        r0.fadeTitleBar(r1);
        r5 = 2131689826; // 0x7f0f0162 float:1.9008678E38 double:1.0531947106E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r28 = r17;
        r28 = (android.view.ViewGroup) r28;
        r27 = r28;
        r0 = r27;
        r1 = r61;
        r1.mMapLayout = r0;
        r5 = 2131689827; // 0x7f0f0163 float:1.900868E38 double:1.053194711E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r1 = r61;
        r1.mMapMask = r0;
        r5 = 2131689822; // 0x7f0f015e float:1.900867E38 double:1.0531947086E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r30 = r17;
        r30 = (com.waze.map.MapViewWrapper) r30;
        r29 = r30;
        r0 = r29;
        r31 = r0.getMapView();
        r0 = r31;
        r1 = r61;
        r1.mMapView = r0;
        r5 = 2131689745; // 0x7f0f0111 float:1.9008514E38 double:1.0531946706E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r33 = r17;
        r33 = (com.waze.ifs.ui.ObservableScrollView) r33;
        r32 = r33;
        r0 = r32;
        r1 = r61;
        r1.mScrollView = r0;
        r0 = r61;
        r23 = r0.getResources();
        r0 = r23;
        r34 = r0.getConfiguration();
        r0 = r34;
        r12 = r0.orientation;
        r0 = r61;
        r13 = r0.mAddressItem;
        r15 = r13.mIsNavigable;
        if (r15 == 0) goto L_0x04af;
    L_0x01c7:
        r0 = r61;
        r0 = r0.mMapView;
        r31 = r0;
        r0 = r61;
        r0 = r0.mMapReadyCallback;
        r35 = r0;
        r0 = r31;
        r1 = r35;
        r0.registerOnMapReadyCallback(r1);
        r0 = r61;
        r0 = r0.mMapView;
        r31 = r0;
        r5 = 1;
        r0 = r31;
        r0.setHandleTouch(r5);
        r5 = 1;
        if (r12 != r5) goto L_0x045a;
    L_0x01e9:
        r0 = r61;
        r23 = r0.getResources();
        r0 = r23;
        r36 = r0.getDisplayMetrics();
        r0 = r36;
        r12 = r0.heightPixels;
        r0 = r61;
        r0 = r0.mDensity;
        r24 = r0;
        r25 = 1130102784; // 0x435c0000 float:220.0 double:5.58344962E-315;
        r24 = r25 * r24;
        r0 = r24;
        r0 = (int) r0;
        r26 = r0;
        r12 = r12 - r0;
        r0 = r61;
        r0.mFrameHeight = r12;
        r0 = r61;
        r12 = r0.mFrameHeight;
        r0 = (float) r12;
        r24 = r0;
        r0 = r61;
        r0 = r0.mDensity;
        r37 = r0;
        r25 = 1127481344; // 0x43340000 float:180.0 double:5.570497984E-315;
        r37 = r25 * r37;
        r0 = r24;
        r1 = r37;
        r0 = r0 - r1;
        r24 = r0;
        r12 = (int) r0;
        r0 = r61;
        r0.mLockHeight = r12;
        r0 = r61;
        r0 = r0.mMapLayout;
        r27 = r0;
        r38 = r0.getLayoutParams();
        r0 = r61;
        r12 = r0.mFrameHeight;
        r0 = r38;
        r0.height = r12;
        r0 = r61;
        r0 = r0.mMapView;
        r31 = r0;
        r38 = r0.getLayoutParams();
        r0 = r61;
        r12 = r0.mFrameHeight;
        r0 = (float) r12;
        r24 = r0;
        r0 = r61;
        r0 = r0.mDensity;
        r37 = r0;
        r25 = 1092616192; // 0x41200000 float:10.0 double:5.398241246E-315;
        r37 = r25 * r37;
        r0 = r24;
        r1 = r37;
        r0 = r0 + r1;
        r24 = r0;
        r12 = (int) r0;
        r0 = r38;
        r0.height = r12;
        r39 = new com.waze.navigate.AddressPreviewActivity$ScrollAndTouchListener;
        r0 = r61;
        r0 = r0.mDensity;
        r24 = r0;
        r0 = r61;
        r0 = r0.mScrollView;
        r32 = r0;
        r40 = 0;
        r0 = r39;
        r1 = r61;
        r2 = r24;
        r3 = r32;
        r4 = r40;
        r0.<init>(r2, r3);
        r0 = r61;
        r0 = r0.mScrollView;
        r32 = r0;
        r1 = r39;
        r0.setOnScrollListener(r1);
        r0 = r61;
        r0 = r0.mScrollView;
        r32 = r0;
        r1 = r39;
        r0.setOnTouchListener(r1);
        r0 = r61;
        r0 = r0.mScrollView;
        r32 = r0;
        r41 = new com.waze.navigate.AddressPreviewActivity$8;
        r0 = r41;
        r1 = r61;
        r0.<init>();
        r0 = r32;
        r1 = r41;
        r0.post(r1);
        r0 = r61;
        r0 = r0.mMapLayout;
        r27 = r0;
        r42 = new com.waze.navigate.AddressPreviewActivity$9;
        r0 = r42;
        r1 = r61;
        r2 = r39;
        r0.<init>(r2);
        r0 = r27;
        r1 = r42;
        r0.setOnTouchListener(r1);
    L_0x02c7:
        r5 = 2131689880; // 0x7f0f0198 float:1.9008788E38 double:1.0531947373E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r5 = 8;
        r0 = r17;
        r0.setVisibility(r5);
        r5 = 2131689881; // 0x7f0f0199 float:1.900879E38 double:1.053194738E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r5 = 8;
        r0 = r17;
        r0.setVisibility(r5);
        r5 = 2131689882; // 0x7f0f019a float:1.9008792E38 double:1.0531947383E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r5 = 8;
        r0 = r17;
        r0.setVisibility(r5);
        r5 = 2131689883; // 0x7f0f019b float:1.9008794E38 double:1.053194739E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r5 = 8;
        r0 = r17;
        r0.setVisibility(r5);
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.venueData;
        r43 = r0;
        if (r43 == 0) goto L_0x0322;
    L_0x0311:
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.venueData;
        r43 = r0;
        r12 = r0.numProducts;
        if (r12 <= 0) goto L_0x0322;
    L_0x031d:
        r0 = r61;
        r0.fillGasPrices();
    L_0x0322:
        r0 = r61;
        r0.refreshView();
        r44 = new java.lang.StringBuilder;
        r0 = r44;
        r0.<init>();
        r0 = r61;
        r0 = r0.mAnalyticsType;
        r20 = r0;
        r0 = r44;
        r1 = r20;
        r44 = r0.append(r1);
        r9 = "|";
        r0 = r44;
        r44 = r0.append(r9);
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.VanueID;
        r20 = r0;
        r0 = r44;
        r1 = r20;
        r44 = r0.append(r1);
        r9 = "|";
        r0 = r44;
        r44 = r0.append(r9);
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.venueData;
        r43 = r0;
        if (r43 == 0) goto L_0x0606;
    L_0x0368:
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.venueData;
        r43 = r0;
        r12 = r0.numImages;
    L_0x0372:
        r0 = r44;
        r44 = r0.append(r12);
        r0 = r44;
        r20 = r0.toString();
        r9 = "ADDRESS_PREVIEW_SHOWN";
        r45 = "TYPE|VENUE_ID|NUM_PHOTOS";
        r0 = r45;
        r1 = r20;
        com.waze.analytics.Analytics.log(r9, r0, r1);
        r0 = r61;
        r6 = r0.getIntent();
        r9 = "commute_mode";
        r20 = r6.getStringExtra(r9);
        if (r20 == 0) goto L_0x061d;
    L_0x0397:
        r9 = "home";
        r0 = r20;
        r15 = r0.contains(r9);
        r9 = "_go";
        r0 = r20;
        r46 = r0.contains(r9);
        r9 = "COMMUTE_PREVIEW_SHOWN";
        r47 = com.waze.analytics.AnalyticsBuilder.analytics(r9);
        r0 = r61;
        r0 = r0.mAnalyticsType;
        r20 = r0;
        r9 = "TYPE";
        r0 = r47;
        r1 = r20;
        r47 = r0.addParam(r9, r1);
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.VanueID;
        r20 = r0;
        r9 = "VENUE_ID";
        r0 = r47;
        r1 = r20;
        r47 = r0.addParam(r9, r1);
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.venueData;
        r43 = r0;
        if (r43 == 0) goto L_0x060c;
    L_0x03d9:
        r0 = r61;
        r13 = r0.mAddressItem;
        r0 = r13.venueData;
        r43 = r0;
        r12 = r0.numImages;
        r0 = (long) r12;
        r48 = r0;
    L_0x03e6:
        r9 = "NUM_PHOTOS";
        r0 = r47;
        r1 = r48;
        r47 = r0.addParam(r9, r1);
        if (r15 == 0) goto L_0x0613;
    L_0x03f2:
        r20 = "HOME";
    L_0x03f4:
        r9 = "COMMUTE_TYPE";
        r0 = r47;
        r1 = r20;
        r47 = r0.addParam(r9, r1);
        if (r46 == 0) goto L_0x061a;
    L_0x0400:
        r20 = "SET";
    L_0x0402:
        r9 = "COMMUTE_STATUS";
        r0 = r47;
        r1 = r20;
        r47 = r0.addParam(r9, r1);
        r0 = r47;
        r0.send();
        return;
    L_0x0412:
        r0 = r61;
        r10 = r0.mAddressItemList;
        r0 = r61;
        r12 = r0.mSelectedItem;
        r50 = r10.get(r12);
        r51 = r50;
        r51 = (com.waze.navigate.AddressItem) r51;
        r13 = r51;
        goto L_0x0428;
    L_0x0425:
        goto L_0x005c;
    L_0x0428:
        r0 = r61;
        r0.mAddressItem = r13;
        goto L_0x0425;
    L_0x042d:
        r0 = r61;
        r6 = r0.getIntent();
        r9 = "CalendarAddressItem";
        r15 = r6.hasExtra(r9);
        if (r15 == 0) goto L_0x0089;
    L_0x043b:
        r0 = r61;
        r6 = r0.getIntent();
        r7 = r6.getExtras();
        r9 = "CalendarAddressItem";
        r8 = r7.getSerializable(r9);
        r52 = r8;
        r52 = (com.waze.navigate.AddressItem) r52;
        r13 = r52;
        goto L_0x0455;
    L_0x0452:
        goto L_0x0089;
    L_0x0455:
        r0 = r61;
        r0.mEventAddressItem = r13;
        goto L_0x0452;
    L_0x045a:
        r5 = 2131689845; // 0x7f0f0175 float:1.9008717E38 double:1.05319472E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r1 = r61;
        r1.mLandcapeScrollContainer = r0;
        r0 = r61;
        r0 = r0.mDensity;
        r24 = r0;
        r25 = 1132593152; // 0x43820000 float:260.0 double:5.59575367E-315;
        r24 = r25 * r24;
        r0 = r24;
        r12 = (int) r0;
        r0 = r61;
        r0.mMapCovered = r12;
        r0 = r61;
        r0 = r0.mMapView;
        r31 = r0;
        r53 = new com.waze.navigate.AddressPreviewActivity$10;
        r0 = r53;
        r1 = r61;
        r0.<init>();
        r0 = r31;
        r1 = r53;
        r0.setOnTouchListener(r1);
        r5 = 2131689846; // 0x7f0f0176 float:1.9008719E38 double:1.0531947205E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r54 = new com.waze.navigate.AddressPreviewActivity$11;
        r0 = r54;
        r1 = r61;
        r0.<init>();
        goto L_0x04a7;
    L_0x04a4:
        goto L_0x02c7;
    L_0x04a7:
        r0 = r17;
        r1 = r54;
        r0.setOnTouchListener(r1);
        goto L_0x04a4;
    L_0x04af:
        r0 = r61;
        r0 = r0.mMapView;
        r31 = r0;
        r5 = 4;
        r0 = r31;
        r0.setVisibility(r5);
        r0 = r61;
        r0 = r0.mMapLayout;
        r27 = r0;
        r5 = 0;
        r0 = r27;
        r0.setVisibility(r5);
        r0 = r61;
        r0 = r0.mMapLayout;
        r27 = r0;
        r0 = r61;
        r23 = r0.getResources();
        r5 = 2131623990; // 0x7f0e0036 float:1.8875147E38 double:1.0531621833E-314;
        r0 = r23;
        r26 = r0.getColor(r5);
        r0 = r27;
        r1 = r26;
        r0.setBackgroundColor(r1);
        r5 = 1;
        if (r12 != r5) goto L_0x059b;
    L_0x04e6:
        r5 = 2131689832; // 0x7f0f0168 float:1.900869E38 double:1.0531947136E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r38 = r0.getLayoutParams();
        r56 = r38;
        r56 = (android.widget.RelativeLayout.LayoutParams) r56;
        r55 = r56;
        r0 = r61;
        r0 = r0.mDensity;
        r24 = r0;
        r25 = 1084227584; // 0x40a00000 float:5.0 double:5.356796015E-315;
        r24 = r25 * r24;
        r0 = r24;
        r12 = (int) r0;
        r5 = 0;
        r57 = 0;
        r58 = 0;
        r0 = r55;
        r1 = r57;
        r2 = r58;
        r0.setMargins(r5, r12, r1, r2);
        r0 = r17;
        r1 = r55;
        r0.setLayoutParams(r1);
        r5 = 2131689837; // 0x7f0f016d float:1.90087E38 double:1.053194716E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r38 = r0.getLayoutParams();
        r59 = r38;
        r59 = (android.widget.RelativeLayout.LayoutParams) r59;
        r55 = r59;
        r0 = r61;
        r0 = r0.mDensity;
        r24 = r0;
        r25 = 1088421888; // 0x40e00000 float:7.0 double:5.37751863E-315;
        r24 = r25 * r24;
        r0 = r24;
        r12 = (int) r0;
        r5 = 0;
        r57 = 0;
        r58 = 0;
        r0 = r55;
        r1 = r57;
        r2 = r58;
        r0.setMargins(r5, r12, r1, r2);
        r5 = 8;
        r57 = 0;
        r0 = r55;
        r1 = r57;
        r0.addRule(r5, r1);
        r5 = 7;
        r57 = 0;
        r0 = r55;
        r1 = r57;
        r0.addRule(r5, r1);
        r5 = 10;
        r0 = r55;
        r0.addRule(r5);
        r5 = 11;
        r0 = r55;
        r0.addRule(r5);
        r0 = r17;
        r1 = r55;
        r0.setLayoutParams(r1);
        r5 = 2131689825; // 0x7f0f0161 float:1.9008676E38 double:1.05319471E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r61;
        r23 = r0.getResources();
        r5 = 2131623989; // 0x7f0e0035 float:1.8875145E38 double:1.053162183E-314;
        r0 = r23;
        r12 = r0.getColor(r5);
        goto L_0x0595;
    L_0x0592:
        goto L_0x02c7;
    L_0x0595:
        r0 = r17;
        r0.setBackgroundColor(r12);
        goto L_0x0592;
    L_0x059b:
        r0 = r61;
        r0 = r0.mLandcapeScrollContainer;
        r17 = r0;
        r38 = r0.getLayoutParams();
        r5 = -1;
        r0 = r38;
        r0.width = r5;
        r5 = 2131689837; // 0x7f0f016d float:1.90087E38 double:1.053194716E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        r0 = r17;
        r38 = r0.getLayoutParams();
        r60 = r38;
        r60 = (android.widget.RelativeLayout.LayoutParams) r60;
        r55 = r60;
        r0 = r61;
        r0 = r0.mDensity;
        r24 = r0;
        r25 = 1114636288; // 0x42700000 float:60.0 double:5.507034975E-315;
        r24 = r25 * r24;
        r0 = r24;
        r12 = (int) r0;
        r5 = 0;
        r57 = 0;
        r58 = 0;
        r0 = r55;
        r1 = r57;
        r2 = r58;
        r0.setMargins(r5, r1, r12, r2);
        r5 = 6;
        r57 = 2131689745; // 0x7f0f0111 float:1.9008514E38 double:1.0531946706E-314;
        r0 = r55;
        r1 = r57;
        r0.addRule(r5, r1);
        r0 = r17;
        r1 = r55;
        r0.setLayoutParams(r1);
        r5 = 2131689846; // 0x7f0f0176 float:1.9008719E38 double:1.0531947205E-314;
        r0 = r61;
        r17 = r0.findViewById(r5);
        goto L_0x05fa;
    L_0x05f7:
        goto L_0x02c7;
    L_0x05fa:
        r5 = 8;
        r0 = r17;
        r0.setVisibility(r5);
        goto L_0x05f7;
        goto L_0x0606;
    L_0x0603:
        goto L_0x0372;
    L_0x0606:
        r12 = 0;
        goto L_0x0603;
        goto L_0x060c;
    L_0x0609:
        goto L_0x03e6;
    L_0x060c:
        r48 = 0;
        goto L_0x0609;
        goto L_0x0613;
    L_0x0610:
        goto L_0x03f4;
    L_0x0613:
        r20 = "WORK";
        goto L_0x0610;
        goto L_0x061a;
    L_0x0617:
        goto L_0x0402;
    L_0x061a:
        r20 = "EDIT";
        goto L_0x0617;
    L_0x061d:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.AddressPreviewActivity.setUpActivity():void");
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        requestWindowFeature(1);
        this.mDensity = getResources().getDisplayMetrics().density;
        float $f0 = this.LS_X_FACTOR * 3.0f;
        float $f1 = this.mDensity;
        this.LS_X_FACTOR = $f0 / $f1;
        $f0 = this.LS_Y_FACTOR * 3.0f;
        $f1 = this.mDensity;
        this.LS_Y_FACTOR = $f0 / $f1;
        this.mDtnMgr = DriveToNativeManager.getInstance();
        this.mNavNtvMgr = NavigateNativeManager.instance();
        EditPlaceServicesFragment.getServices();
        NativeManager.getInstance().OpenRoutingIntent();
        this.mNatMgr = AppService.getNativeManager();
        $r1 = getIntent().getExtras();
        if ($r1 != null) {
            this.mIsAdditionalAddToFavorites = $r1.getBoolean(PublicMacros.ADDITIONAL_BUTTON);
            this.mIsClearAdsContext = $r1.getBoolean(PublicMacros.CLEAR_ADS_CONTEXT, false);
            this.mShouldLoadVenue = $r1.getBoolean(PublicMacros.PREVIEW_LOAD_VENUE, false);
            this.mParkingMode = $r1.getBoolean(PublicMacros.PREVIEW_PARKING_MODE, false);
            this.mParkingDistance = $r1.getInt(PublicMacros.PREVIEW_PARKING_DISTANCE, 0);
            this.mParkingVenue = (VenueData) $r1.getSerializable(PublicMacros.PREVIEW_PARKING_VENUE);
            this.mParkingPopular = $r1.getBoolean(PublicMacros.PREVIEW_PARKING_POPULAR);
            this.mParkingETA = $r1.getInt(PublicMacros.f84PREVIEW_PARKING_ETA, -1);
            this.mEdit = $r1.getBoolean(PublicMacros.EDIT, false);
            this.mLogoResource = $r1.getInt("logo", -1);
        }
        setResult(-1);
        this.mDtnMgr.setUpdateHandler(DriveToNativeManager.UH_ETA, this.mHandler);
        this.mDtnMgr.setUpdateHandler(DriveToNativeManager.UH_DANGER_ZONE_FOUND, this.mHandler);
        setUpActivity();
        if (getIntent().hasExtra(OPEN_SET_LOCATION)) {
            post(new C20823());
        }
        logAnalyticsAds(AnalyticsEvents.ANALYTICS_EVENT_ADS_PREVIEW_SHOWN);
        if (this.mShouldLoadVenue) {
            String $r13;
            NativeManager $r6;
            String $r12;
            C20834 c20834;
            NativeManager $r62;
            this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            this.mDtnMgr.setUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            VenueData $r9 = this.mAddressItem;
            VenueData $r122 = $r9;
            if ($r9.venueData != null) {
                $r9 = this.mAddressItem;
                $r122 = $r9;
                $r13 = $r9.venueData;
                String $r92 = $r13;
                $r13 = $r13.context;
                if (!TextUtils.isEmpty($r13)) {
                    $r6 = NativeManager.getInstance();
                    AddressItem $r123 = this.mAddressItem;
                    String $r132 = $r123.getId();
                    $r13 = this.mAddressItem;
                    $r12 = $r13;
                    String $r14 = $r13.VanueID;
                    $r9 = this.mAddressItem;
                    $r122 = $r9;
                    $r13 = $r9.venueData;
                    $r92 = $r13;
                    $r6.AutoCompletePlaceClicked($r132, $r14, null, null, $r13.context, false, null, false, 0, null, null);
                    this.mWebViewLoadAnimation.setVisibility(0);
                    c20834 = new C20834();
                    $r62 = this.mNatMgr;
                    postDelayed(c20834, (long) $r62.getVenueGetTimeout());
                }
            }
            $r6 = this.mNatMgr;
            $r13 = this.mAddressItem;
            $r12 = $r13;
            $r6.venueGet($r13.VanueID, 1);
            this.mWebViewLoadAnimation.setVisibility(0);
            c20834 = new C20834();
            $r62 = this.mNatMgr;
            postDelayed(c20834, (long) $r62.getVenueGetTimeout());
        }
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        boolean $z0 = false;
        if ($i0 == 106) {
            if ($i1 == -1) {
                this.mNatMgr.venueFlag(this.mAddressItem.venueData.id, this.mFlagType, $r1.getStringExtra(EditTextDialogActivity.RET_VAL), null);
                thanksForReport();
            }
        } else if ($i0 == 107) {
            if ($i1 == -1) {
                this.mNatMgr.venueFlag(this.mAddressItem.venueData.id, this.mFlagType, null, $r1.getStringExtra(OmniSelectionActivity.RET_SELECTED_VAL));
                thanksForReport();
            }
        } else if ($i0 != 108) {
            if ($i0 == 109 && $i1 == 3) {
                setResult(0);
                this.mWasResultSet = true;
                finish();
            }
            if ($i0 == 113 && $i1 == -1) {
                if ($r1 != null) {
                    if ($r1.hasExtra("ai")) {
                        AddressItem $r5 = (AddressItem) $r1.getExtras().get("ai");
                        if ($r5 != null) {
                            $z0 = true;
                            final AddressItem addressItem = $r5;
                            final int i = $i0;
                            final int i2 = $i1;
                            final Intent intent = $r1;
                            NativeManager.Post(new RunnableUICallback() {
                                private int danger;

                                class C20841 implements DialogInterface.OnClickListener {
                                    C20841() throws  {
                                    }

                                    public void onClick(DialogInterface dialog, int $i0) throws  {
                                        if ($i0 == 1) {
                                            AddressPreviewActivity.this.setMeetingAddress(i, i2, intent, addressItem);
                                            AddressPreviewActivity.this.mDtnMgr.addDangerZoneStat(addressItem.getLocationX().intValue(), addressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                                            return;
                                        }
                                        AddressPreviewActivity.this.mDtnMgr.addDangerZoneStat(addressItem.getLocationX().intValue(), addressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
                                    }
                                }

                                class C20852 implements OnCancelListener {
                                    C20852() throws  {
                                    }

                                    public void onCancel(DialogInterface dialog) throws  {
                                        AddressPreviewActivity.this.mDtnMgr.addDangerZoneStat(addressItem.getLocationX().intValue(), addressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, "BACK");
                                    }
                                }

                                public void event() throws  {
                                    this.danger = AddressPreviewActivity.this.mDtnMgr.isInDangerZoneNTV(addressItem.getLocationX().intValue(), addressItem.getLocationY().intValue());
                                }

                                public void callback() throws  {
                                    if (this.danger >= 0) {
                                        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(AddressPreviewActivity.this.mNatMgr.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), AddressPreviewActivity.this.mNatMgr.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE), false, new C20841(), AddressPreviewActivity.this.mNatMgr.getLanguageString(DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE_BUTTON), AddressPreviewActivity.this.mNatMgr.getLanguageString(344), -1, "dangerous_zone_icon", new C20852(), true, true);
                                        return;
                                    }
                                    AddressPreviewActivity.this.setMeetingAddress(i, i2, intent, addressItem);
                                }
                            });
                        }
                    }
                }
                if (!$z0) {
                    setMeetingAddress($i0, $i1, $r1, null);
                    $z0 = true;
                }
            }
            if ($i0 == 333) {
                if ($i1 == -1) {
                    Intent intent2 = new Intent();
                    intent2 = intent2;
                    intent2.putExtra(PublicMacros.ADDRESS_ITEM, this.mAddressItem);
                    setResult(MainActivity.VERIFY_EVENT_CODE, intent2);
                    this.mWasResultSet = true;
                    finish();
                } else {
                    setResult(-1);
                    this.mWasResultSet = true;
                    finish();
                }
            } else if ($i1 == 1 && !$z0) {
                setResult(1);
                this.mWasResultSet = true;
                finish();
            } else if ($i1 == -1 && !$z0) {
                setResult(-1);
                this.mWasResultSet = true;
                finish();
            }
            if (!$z0) {
                super.onActivityResult($i0, $i1, $r1);
            }
        } else if ($i1 == -1) {
            this.mNatMgr.venueFlag(this.mAddressItem.venueData.id, this.mFlagType, $r1.getStringExtra(EditTextDialogActivity.RET_VAL), null);
            thanksForReport();
        }
    }

    private void setMeetingAddress(int $i0, int $i1, Intent $r1, AddressItem $r2) throws  {
        if ($r2 != null) {
            this.mDtnMgr.updateEvent(this.mAddressItem.getMeetingId(), $r2);
        }
        setResult($i1);
        this.mWasResultSet = true;
        finish();
        super.onActivityResult($i0, $i1, $r1);
    }

    private void setDangerZoneTitle(int $i0) throws  {
        if (this.mDangerZoneWarning.getVisibility() != 0) {
            ((WazeTextView) findViewById(C1283R.id.addressPreviewDangerZoneTitle)).setText(this.mNatMgr.getLanguageString($i0 + DisplayStrings.DS_DANGEROUS_ADDRESS_PREVIEW_TITLE));
            this.mDangerZoneWarning.setVisibility(0);
        }
    }

    private void setupPreview() throws  {
        if (this.mAddressItem == null) {
            return;
        }
        if (this.mParkingVenue == null || this.mAddressItem.venueData == null) {
            this.mNavNtvMgr.SetPreviewPoiPosition(this.mAddressItem.getLocationX().intValue(), this.mAddressItem.getLocationY().intValue(), null, false);
            return;
        }
        this.mNavNtvMgr.SetParkingPoiPosition(this.mAddressItem.venueData);
        this.mNavNtvMgr.SetPreviewPoiPosition(this.mParkingVenue.longitude, this.mParkingVenue.latitude, this.mParkingVenue.name, false);
    }

    private void fadeTitleBar(float $f0) throws  {
        AlphaAnimation $r1 = new AlphaAnimation($f0, $f0);
        $r1.setFillAfter(true);
        this.mTitleBar.startAnimation($r1);
        $r1 = new AlphaAnimation($f0, $f0);
        $r1.setFillAfter(true);
        this.mTitleBarShadow.startAnimation($r1);
    }

    private void fillGasPrices() throws  {
        this.mDtnMgr.getProduct(this.mAddressItem.index, new ProductListener() {
            public void onComplete(Product $r1) throws  {
                if ($r1 == null || $r1.labels == null || $r1.prices == null || $r1.formats == null || $r1.prices.length == 0) {
                    Logger.m38e("AddressPreviewActivity.fillGasPrices().onComplete() product is null or has null members");
                    return;
                }
                String $r20;
                AddressPreviewActivity.this.mIsGasStation = true;
                if (AddressPreviewActivity.this.mLogoImg != null) {
                    AddressPreviewActivity.this.mLogoImg.setImageDrawable(new CircleShaderDrawable(((BitmapDrawable) AddressPreviewActivity.this.getResources().getDrawable(C1283R.drawable.gas_illustration)).getBitmap(), 0));
                }
                int $i1 = $r1.labels.length;
                AddressPreviewActivity.this.findViewById(C1283R.id.addressPreviewSepGas).setVisibility(0);
                TextView $r16 = (TextView) AddressPreviewActivity.this.findViewById(C1283R.id.addressPreviewGasPriceTitle);
                $r16.setVisibility(0);
                String $r18 = AddressPreviewActivity.this.mNatMgr.getLanguageString(205);
                String $r19 = $r18;
                if ($r1.currency != null) {
                    $r20 = $r1.currency;
                    if (!$r20.isEmpty()) {
                        $r19 = $r18 + " (" + AddressPreviewActivity.this.mNatMgr.getLanguageString($r1.currency) + ")";
                    }
                }
                $r16.setText($r19);
                View $r15 = AddressPreviewActivity.this.findViewById(C1283R.id.addressPreviewGasPricesEditTexts_ref);
                $r15.setVisibility(0);
                int[] $r4 = new int[]{C1283R.id.addressPreviewGasPrice1, C1283R.id.addressPreviewGasPrice2, C1283R.id.addressPreviewGasPrice3, C1283R.id.addressPreviewGasPrice4};
                int[] $r2 = new int[]{C1283R.id.addressPreviewGasPriceEdit1, C1283R.id.addressPreviewGasPriceEdit2, C1283R.id.addressPreviewGasPriceEdit3, C1283R.id.addressPreviewGasPriceEdit4};
                int[] $r3 = new int[]{C1283R.id.addressPreviewGasPriceLabel1, C1283R.id.addressPreviewGasPriceLabel2, C1283R.id.addressPreviewGasPriceLabel3, C1283R.id.addressPreviewGasPriceLabel4};
                int $i2 = 0;
                while ($i2 < 4) {
                    if ($i1 <= $i2 || $r1.prices[$i2] <= 0.0f) {
                        $r15.findViewById($r4[$i2]).setVisibility(8);
                    } else {
                        $r16 = (TextView) $r15.findViewById($r2[$i2]);
                        $r16.setText(UpdatePriceActivity.padWithZeros($r1.formats[$i2], $r1.prices[$i2]));
                        $r16.setBackgroundDrawable(new ShadowedRectDrawable(AddressPreviewActivity.this.getResources().getColor(C1283R.color.ElectricBlue)));
                        ((TextView) $r15.findViewById($r3[$i2])).setText($r1.labels[$i2]);
                    }
                    $i2++;
                }
                $r16 = (TextView) AddressPreviewActivity.this.findViewById(C1283R.id.addressPreviewGasPriceUpdated);
                if ($r1.lastUpdated != -1) {
                    long currentTimeMillis = (((System.currentTimeMillis() / 1000) - ((long) $r1.lastUpdated)) / 3600) / 24;
                    $r20 = $r1.updatedBy;
                    $r18 = $r20;
                    $r18 = AddressPreviewActivity.formatUpdatedTimeUserString($r20, currentTimeMillis);
                    $r16.setVisibility(0);
                    $r16.setText($r18);
                    return;
                }
                $r16.setVisibility(4);
            }
        });
    }

    public static String formatUpdatedTimeUserString(String $r0, long $l0) throws  {
        if ($r0 == null) {
            return "";
        }
        if ($l0 == 1) {
            return DisplayStrings.displayString(DisplayStrings.DS_LAST_UPDATE_YESTERDAY_FORMAT).replaceAll("<USER>", $r0);
        }
        if ($l0 <= 0) {
            return DisplayStrings.displayString(DisplayStrings.DS_LAST_UPDATE_TODAY_FORMAT).replaceAll("<USER>", $r0);
        }
        return DisplayStrings.displayString(DisplayStrings.DS_LAST_UPDATE_DAYS_FORMAT).replaceAll("<DAYS>", Long.toString($l0)).replaceAll("<USER>", $r0);
    }

    void reportClick(String $r1) throws  {
        AnalyticsBuilder $r2 = AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_ADDRESS_PREVIEW_CLICK);
        $r2.addParam("TYPE", this.mAnalyticsType);
        $r2.addParam("ACTION", $r1);
        $r1 = getIntent().getStringExtra(COMMUTE_MODE);
        if ($r1 != null) {
            boolean $z0 = $r1.contains("home");
            boolean $z1 = $r1.contains("_go");
            if ($z0) {
                $r1 = "HOME";
            } else {
                $r1 = "WORK";
            }
            $r2.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_TYPE, $r1);
            if ($z1) {
                $r1 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_SET;
            } else {
                $r1 = "EDIT";
            }
            $r2.addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COMMUTE_STATUS, $r1);
        }
        $r2.send();
    }

    private void setServices(LinearLayout $r1) throws  {
        int $i4;
        int $i0 = (int) (this.mDensity * 60.0f);
        int $i1 = $r1.getMeasuredWidth() / $i0;
        int $i2 = ((this.mAddressItem.venueData.numServices + $i1) - 1) / $i1;
        if (this.mServicesRows != null) {
            for (View $r8 : this.mServicesRows) {
                $r1.removeView($r8);
            }
        }
        this.mServicesRows = new View[$i2];
        for ($i4 = 0; $i4 < $i2; $i4++) {
            LinearLayout $r4 = new LinearLayout(this);
            this.mServicesRows[$i4] = $r4;
            LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
            layoutParams.gravity = 17;
            $r1.addView($r4, layoutParams);
            int $i5 = this.mAddressItem.venueData.numServices - ($i4 * $i1);
            if ($i5 > $i1) {
                $i5 = $i1;
            }
            for (int $i6 = 0; $i6 < $i5; $i6++) {
                View imageView = new ImageView(this);
                int $i3 = $i6 + ($i4 * $i1);
                Drawable $r12 = ResManager.GetSkinDrawable(EditPlaceServicesFragment.getServiceIconById(this.mAddressItem.venueData.services[$i3]) + ResManager.mImageExtension);
                if ($r12 != null) {
                    imageView.setImageDrawable($r12);
                } else {
                    imageView.setImageResource(C1283R.drawable.preview_services_default);
                }
                layoutParams = new LinearLayout.LayoutParams($i0, -2);
                layoutParams.gravity = 17;
                int i = (int) (10.0f * this.mDensity);
                int $i7 = i;
                layoutParams.topMargin = i;
                i = (int) (10.0f * this.mDensity);
                $i7 = i;
                layoutParams.bottomMargin = i;
                $r4.addView(imageView, layoutParams);
                final int i2 = $i3;
                final View view = imageView;
                imageView.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        String $r6 = EditPlaceServicesFragment.getServiceById(AddressPreviewActivity.this.mAddressItem.venueData.services[i2]);
                        if (!TextUtils.isEmpty($r6)) {
                            this = this;
                            if (AddressPreviewActivity.this.cancelTooltipAction != null) {
                                if (AddressPreviewActivity.this.cancelTooltipAction.associatedView == view) {
                                    AddressPreviewActivity.this.cancelTooltipAction.run();
                                    return;
                                }
                                this = this;
                                AddressPreviewActivity.this.cancelTooltipAction.run();
                            }
                            AnonymousClass36 $r2 = this;
                            this = $r2;
                            AddressPreviewActivity.this.shownTooltip = new TinyTooltip(AddressPreviewActivity.this, $r6);
                            $r2 = this;
                            this = $r2;
                            AddressPreviewActivity.this.cancelTooltipAction = new CancelTooltipRunnable();
                            $r2 = this;
                            this = $r2;
                            AddressPreviewActivity.this.cancelTooltipAction.associatedView = view;
                            ImageView $r8 = view;
                            $r2 = this;
                            this = $r2;
                            $r8.postDelayed(AddressPreviewActivity.this.cancelTooltipAction, 3000);
                            $r2 = this;
                            this = $r2;
                            AddressPreviewActivity.this.shownTooltip.setAnimations(C1283R.anim.contact_tooltip_show, C1283R.anim.contact_tooltip_hide);
                            $r2 = this;
                            AddressPreviewActivity.this.shownTooltip.show(view);
                        }
                    }
                });
            }
            findViewById(C1283R.id.addressPreviewDetailsLayout).setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    if (AddressPreviewActivity.this.cancelTooltipAction != null) {
                        AddressPreviewActivity.this.cancelTooltipAction.run();
                    }
                }
            });
        }
    }

    private boolean unvalidateCalendarMode() throws  {
        return this.mAddressItem.getType() == 11 && !(this.mAddressItem.getIsValidate().booleanValue() && this.mAddressItem.hasLocation());
    }

    private void aboutTextClicked(TextView $r1, TextView $r2) throws  {
        int $i0;
        int $i2;
        if (this.mAboutTextIsOpen) {
            $i0 = $r1.getMeasuredHeight();
            $i2 = getResources().getDimensionPixelSize(C1283R.dimen.addressPreviewAboutClosedHeight);
        } else {
            int $i1 = $r1.getHeight();
            $i0 = $i1;
            LayoutParams $r11 = $r1.getLayoutParams();
            $r11.height = -2;
            $r1.setLayoutParams($r11);
            $r1.measure(MeasureSpec.makeMeasureSpec($r1.getWidth(), 1073741824), MeasureSpec.makeMeasureSpec(0, 0));
            $i2 = $r1.getMeasuredHeight();
            $r11.height = $i1;
            $r1.setLayoutParams($r11);
        }
        ValueAnimator $r6 = ValueAnimator.ofInt(new int[]{$i0, $i2});
        final TextView textView = $r1;
        $r6.addUpdateListener(new AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator $r1) throws  {
                int $i0 = ((Integer) $r1.getAnimatedValue()).intValue();
                LayoutParams $r5 = textView.getLayoutParams();
                $r5.height = $i0;
                textView.setLayoutParams($r5);
            }
        });
        $r6.setDuration(300);
        $r6.setInterpolator(new DecelerateInterpolator());
        textView = $r1;
        final TextView textView2 = $r2;
        $r6.addListener(new AnimatorListener() {
            public void onAnimationStart(Animator animation) throws  {
            }

            public void onAnimationRepeat(Animator animation) throws  {
            }

            public void onAnimationEnd(Animator animation) throws  {
                AddressPreviewActivity.this.mAboutTextIsOpen = !AddressPreviewActivity.this.mAboutTextIsOpen;
                if (AddressPreviewActivity.this.mAboutTextIsOpen) {
                    LayoutParams $r5 = textView.getLayoutParams();
                    $r5.height = -2;
                    textView.setLayoutParams($r5);
                    textView2.setText(AddressPreviewActivity.this.mNatMgr.getLanguageString((int) DisplayStrings.DS_LOCATION_PREVIEW_ABOUT_LESS));
                    return;
                }
                $r5 = textView.getLayoutParams();
                $r5.height = $i2;
                textView.setLayoutParams($r5);
                textView2.setText(AddressPreviewActivity.this.mNatMgr.getLanguageString((int) DisplayStrings.DS_LOCATION_PREVIEW_ABOUT_MORE));
            }

            public void onAnimationCancel(Animator animation) throws  {
            }
        });
        $r6.start();
        AlphaAnimation $r3 = new AlphaAnimation(this.mAboutTextIsOpen ? 0.0f : 1.0f, this.mAboutTextIsOpen ? 1.0f : 0.0f);
        $r3.setDuration(100);
        $r3.setStartOffset(200);
        $r3.setFillAfter(true);
        findViewById(C1283R.id.addressPreviewPlaceAboutCover).startAnimation($r3);
    }

    private void setupParking() throws  {
        TextView $r10;
        boolean $z0 = false;
        NavigateNativeManager $r1 = NavigateNativeManager.instance();
        if (unvalidateCalendarMode()) {
            $z0 = true;
        } else if (this.mParkingMode) {
            $z0 = true;
            if (!this.mAddressItem.hasIcon()) {
                this.mLogoImg.setImageDrawable(new CircleShaderDrawable(((BitmapDrawable) getResources().getDrawable(C1283R.drawable.parking_icon_small)).getBitmap(), 0));
            }
            $r10 = (TextView) findViewById(C1283R.id.addressPreviewPlaceDistance);
            $r10.setVisibility(0);
            $r10.setTextColor(getResources().getColor(C1283R.color.ElectricBlue));
            if (this.mParkingETA != -1) {
                formatETA();
            } else {
                ProgressAnimation $r13 = (ProgressAnimation) findViewById(C1283R.id.addressPreviewPlaceLoader);
                $r13.setVisibility(0);
                $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_ETA_LOADING));
                $r13.start();
                if (this.mAddressItem.venueData != null) {
                    $r1.setUpdateHandler(NavigateNativeManager.UH_CALC_ETA, this.mHandler);
                    $r1.calculateETA(this.mAddressItem.venueData, null);
                }
            }
            if (this.mParkingVenue != null && this.mParkingPopular) {
                findViewById(C1283R.id.addressPreviewParkingModeLayout).setVisibility(0);
                ((TextView) findViewById(C1283R.id.addressPreviewParkingModeLabel)).setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_POPULAR_COMMENT));
                $r10 = (TextView) findViewById(C1283R.id.addressPreviewParkingModeName);
                String $r12 = this.mParkingVenue;
                String $r11 = $r12;
                $r10.setText($r12.name);
            }
        } else if (!$r1.suggestParkingEnabled()) {
            $z0 = true;
        } else if (this.mAddressItem == null) {
            $z0 = true;
        } else {
            if (this.mAddressItem.venueData != null) {
                VenueData $r112 = this.mAddressItem.venueData;
                if ($r112.isParkingCategory()) {
                    $z0 = true;
                }
            }
            if (this.parkingSuggestionLoaded && this.parkingSuggestionAddressItem == null) {
                $z0 = true;
            }
        }
        if ($z0) {
            findViewById(C1283R.id.addressPreviewParkingSuggestions).setVisibility(8);
            if (!unvalidateCalendarMode() && !this.mParkingMode && this.parkingSuggestionLoaded) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ADDRESS_PREVIEW_SUGGESTED_PARKING_SHOWN, "MORE_PARKINGLOTS|SHOWN_AT_TOP", "F|NONE");
                return;
            }
            return;
        }
        ((TextView) findViewById(C1283R.id.addressPreviewParkingTitle)).setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_TITLE));
        final View $r2 = findViewById(C1283R.id.addressPreviewParking1);
        if (this.parkingSuggestionLoaded) {
            String $r122;
            $r13 = (ProgressAnimation) $r2.findViewById(C1283R.id.suggestedParkingLoader);
            $r13.setVisibility(8);
            $r13.stop();
            $r2.findViewById(C1283R.id.suggestedParkingMore).setVisibility(0);
            $r10 = (TextView) $r2.findViewById(C1283R.id.suggesedParkingTitle);
            $r10.setVisibility(0);
            if (this.parkingSuggestionPopular) {
                $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_POPULAR));
            } else {
                $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_NEAREST));
            }
            $r10 = (TextView) $r2.findViewById(C1283R.id.suggesedParkingDetail);
            $r10.setVisibility(0);
            if (this.parkingSuggestionAddressItem.getTitle().isEmpty()) {
                $r10.setText(this.parkingSuggestionAddressItem.getAddress());
            } else {
                $r10.setText(this.parkingSuggestionAddressItem.getTitle());
            }
            $r2.findViewById(C1283R.id.suggesedParkingViewMore).setVisibility(8);
            $r2.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    AddressPreviewActivity.this.reportClick(AnalyticsEvents.ANALYTICS_EVENT_VALUE_BEST_PARKING);
                    Intent $r2 = r6;
                    Intent r6 = new Intent(AddressPreviewActivity.this, AddressPreviewActivity.class);
                    $r2.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
                    $r2.putExtra(PublicMacros.ADDRESS_ITEM, AddressPreviewActivity.this.parkingSuggestionAddressItem);
                    $r2.putExtra(PublicMacros.PREVIEW_PARKING_MODE, true);
                    $r2.putExtra(PublicMacros.PREVIEW_PARKING_DISTANCE, AddressPreviewActivity.this.parkingSuggestionDistance);
                    $r2.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, AddressPreviewActivity.this.mAddressItem.getVenueDataForParking());
                    $r2.putExtra(PublicMacros.PREVIEW_PARKING_POPULAR, AddressPreviewActivity.this.parkingSuggestionPopular);
                    AddressPreviewActivity.this.startActivityForResult($r2, 0);
                }
            });
            if (this.parkingSuggestionMore) {
                View $r15 = findViewById(C1283R.id.addressPreviewParking2);
                $r15.setVisibility(0);
                $r15.findViewById(C1283R.id.suggesedParkingTitle).setVisibility(8);
                $r15.findViewById(C1283R.id.suggesedParkingDetail).setVisibility(8);
                $r10 = (TextView) $r15.findViewById(C1283R.id.suggesedParkingViewMore);
                $r10.setVisibility(0);
                $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_MORE));
                $r15.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) throws  {
                        AddressPreviewActivity.this.reportClick(AnalyticsEvents.ANALYTICS_EVENT_VALUE_MORE_PARKING);
                        Intent $r2 = r6;
                        Intent r6 = new Intent(AddressPreviewActivity.this, ParkingSearchResultsActivity.class);
                        $r2.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, AddressPreviewActivity.this.mAddressItem.getVenueDataForParking());
                        $r2.putExtra(PublicMacros.PREVIEW_PARKING_CONTEXT, "MORE");
                        AddressPreviewActivity.this.startActivityForResult($r2, 0);
                    }
                });
            }
            StringBuilder $r19 = new StringBuilder().append(this.parkingSuggestionMore ? "T|" : "F|");
            if (this.parkingSuggestionPopular) {
                $r122 = "POPULAR";
            } else {
                $r122 = AnalyticsEvents.ANALYTICS_EVENT_VALUE_NEAREST;
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ADDRESS_PREVIEW_SUGGESTED_PARKING_SHOWN, "MORE_PARKINGLOTS|SHOWN_AT_TOP", $r19.append($r122).toString());
            if (!this.shownParkingTooltip && !ConfigValues.getBoolValue(308)) {
                this.shownParkingTooltip = true;
                postDelayed(new Runnable() {
                    public void run() throws  {
                        if ((UserTooltipView.showUserTooltip(AppService.getActiveActivity(), $r2, 0, DisplayStrings.displayString(DisplayStrings.DS_FTE_PREVIEW_PARKING_BUBBLE_TEXT), AddressPreviewActivity.MINIMUM_DISPLAY_TIME, null, true) != null ? 1 : null) != null) {
                            ConfigValues.setBoolValue(308, true);
                            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_VALUE_FTE_TYPE_PREVIEW_PARKING);
                        }
                    }
                }, (long) ConfigValues.getIntValue(300));
                return;
            }
            return;
        }
        findViewById(C1283R.id.addressPreviewParkingSuggestions).setVisibility(0);
        findViewById(C1283R.id.addressPreviewParking2).setVisibility(8);
        $r10 = (TextView) $r2.findViewById(C1283R.id.suggesedParkingViewMore);
        $r10.setVisibility(0);
        $r2.findViewById(C1283R.id.suggesedParkingTitle).setVisibility(8);
        $r2.findViewById(C1283R.id.suggesedParkingDetail).setVisibility(8);
        $r2.findViewById(C1283R.id.suggestedParkingMore).setVisibility(8);
        $r13 = (ProgressAnimation) $r2.findViewById(C1283R.id.suggestedParkingLoader);
        $r10.setText(DisplayStrings.displayString(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_SEARCHING));
        $r13.setVisibility(0);
        $r13.start();
        this.parkingRequestTime = System.currentTimeMillis();
        $r1.setUpdateHandler(NavigateNativeManager.UH_SUGGEST_BEST_PARKING, this.mHandler);
        $r1.suggestParkingRequestBestParking(this.mAddressItem.getVenueDataForParking());
        $r2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                Intent $r2 = r6;
                Intent r6 = new Intent(AddressPreviewActivity.this, ParkingSearchResultsActivity.class);
                $r2.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, AddressPreviewActivity.this.mAddressItem.getVenueDataForParking());
                $r2.putExtra(PublicMacros.PREVIEW_PARKING_CONTEXT, "MORE");
                AddressPreviewActivity.this.startActivityForResult($r2, 0);
            }
        });
    }

    private void setupMoreMenu(int $i0) throws  {
        if (this.mAddressItem.mIsNavigable) {
            AnonymousClass1Action anonymousClass51;
            final ArrayList $r1 = new ArrayList(1);
            if (this.mOnSaveEventLocation != null) {
                AnonymousClass44 $r5 = new AnonymousClass1Action() {
                    public void run() throws  {
                        AddressPreviewActivity.this.mOnSaveEventLocation.onClick(null);
                    }
                };
                $r5.titleDS = DisplayStrings.DS_SAVE_EVENT_LOCATION;
                $r5.imageResId = C1283R.drawable.list_icon_location;
                $r1.add($r5);
            }
            AnonymousClass45 $r6 = new AnonymousClass1Action() {
                public void run() throws  {
                    AddressPreviewActivity.this.openShareActivity();
                }
            };
            $r6.titleDS = DisplayStrings.DS_LOCATION_PREVIEW_SEND_ACTION;
            $r6.imageResId = C1283R.drawable.list_icon_send_location;
            $r1.add($r6);
            AnonymousClass46 $r9 = new AnonymousClass1Action() {
                public void run() throws  {
                    AddressPreviewActivity.this.mDtnMgr.centerMapInAddressOptionsView(AddressPreviewActivity.this.mAddressItem.index, AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue(), false, AddressPreviewActivity.this.mAddressItem.getIcon());
                    if (AddressPreviewActivity.this.mAddressItem.venueData != null) {
                        AddressPreviewActivity.this.mDtnMgr.showOnMap(AddressPreviewActivity.this.mAddressItem.venueData);
                    } else {
                        AddressPreviewActivity.this.mDtnMgr.showOnMap(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue());
                    }
                    AddressPreviewActivity.this.setResult(3);
                    AddressPreviewActivity.this.mWasResultSet = true;
                    AddressPreviewActivity.this.finish();
                }
            };
            $r9.titleDS = 114;
            $r9.imageResId = C1283R.drawable.list_icon_show_on_map;
            $r1.add($r9);
            AnonymousClass47 $r8 = new AnonymousClass1Action() {
                public void run() throws  {
                    AddressPreviewActivity.this.mDtnMgr.setStartPoint(AddressPreviewActivity.this.mAddressItem);
                    AddressPreviewActivity.this.setResult(3);
                    AddressPreviewActivity.this.mWasResultSet = true;
                    AddressPreviewActivity.this.finish();
                }
            };
            $r8.titleDS = DisplayStrings.DS_SET_AS_START_POINT;
            $r8.imageResId = C1283R.drawable.list_icon_set_as_start;
            $r1.add($r8);
            if (this.calendarSetLocationListener != null) {
                AnonymousClass48 $r7 = new AnonymousClass1Action() {
                    public void run() throws  {
                        AddressPreviewActivity.this.calendarSetLocationListener.onClick(null);
                    }
                };
                $r7.titleDS = DisplayStrings.DS_LOCATION_PREVIEW_CALENDAR_CHANGE_ACTION;
                $r7.imageResId = C1283R.drawable.list_icon_request_location;
                $r1.add($r7);
            }
            if (this.calendarRemoveListener != null) {
                AnonymousClass49 $r13 = new AnonymousClass1Action() {
                    public void run() throws  {
                        AddressPreviewActivity.this.calendarRemoveListener.onClick(null);
                    }
                };
                $r13.titleDS = DisplayStrings.DS_LOCATION_PREVIEW_CALENDAR_REMOVE_ACTION;
                $r13.imageResId = C1283R.drawable.list_icon_remove;
                $r1.add($r13);
            }
            if (!(this.mAddressItem.venueData == null || this.mAddressItem.venueData.bResidence || !this.mAddressItem.venueData.bUpdateable)) {
                AnonymousClass50 $r4 = new AnonymousClass1Action() {
                    public void run() throws  {
                        AddressPreviewActivity.this.showReportSubmenu();
                    }
                };
                $r4.titleDS = DisplayStrings.DS_REPORT_A_PROBLEM;
                $r4.imageResId = C1283R.drawable.list_icon_flag;
                $r1.add($r4);
            }
            if ($i0 == 9 || $i0 == 8 || $i0 == 13) {
                anonymousClass51 = new AnonymousClass1Action() {
                    public void run() throws  {
                        AddressPreviewActivity.this.mDtnMgr.eraseAddressItem(AddressPreviewActivity.this.mAddressItem);
                        AddressPreviewActivity.this.setResult(MainActivity.RELOAD_SEARCH_CODE);
                        AddressPreviewActivity.this.mWasResultSet = true;
                        AddressPreviewActivity.this.finish();
                    }
                };
                anonymousClass51.titleDS = DisplayStrings.DS_REMOVE_FROM_HISTORY;
                anonymousClass51.imageResId = C1283R.drawable.list_icon_remove;
                $r1.add(anonymousClass51);
            }
            if (!(this.mAddressItem.venueData == null || !this.mAddressItem.venueData.bUpdateable || this.mAddressItem.venueData.bResidence || !this.mAddressItem.mIsNavigable || MyWazeNativeManager.getInstance().getInvisibleNTV())) {
                anonymousClass51 = new AnonymousClass1Action() {
                    public void run() throws  {
                        AddressPreviewActivity.this.onEdit();
                    }
                };
                anonymousClass51.titleDS = DisplayStrings.DS_LOCATION_PREVIEW_EDIT_BUTTON;
                anonymousClass51.imageResId = C1283R.drawable.list_icon_edit;
                $r1.add(anonymousClass51);
            }
            NativeManager $r17 = this.mNatMgr;
            if ($r17.RealtimeDebugEnabled()) {
                anonymousClass51 = new AnonymousClass1Action() {
                    public void run() throws  {
                        AddressPreviewActivity.this.mNatMgr.Set_Parking(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue(), (int) (System.currentTimeMillis() / 1000), null, true);
                        AddressPreviewActivity.this.finish();
                    }
                };
                anonymousClass51.titleDS = DisplayStrings.DS_MAP_POPUP_PARKED_TITLE;
                anonymousClass51.imageResId = C1283R.drawable.parked_here_illustration;
                $r1.add(anonymousClass51);
            }
            findViewById(C1283R.id.addressPreviewMore).setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    AddressPreviewActivity.this.reportClick(AnalyticsEvents.ANALYTICS_EVENT_VALUE_OPTIONS);
                    final BottomSheet $r2 = new BottomSheet(AddressPreviewActivity.this, DisplayStrings.DS_LOCATION_PREVIEW_MORE_ACTION_SHEET_TITLE, Mode.COLUMN_TEXT_ICON);
                    $r2.setAdapter(new Adapter() {
                        public int getCount() throws  {
                            return $r1.size();
                        }

                        public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                            AnonymousClass1Action $r5 = (AnonymousClass1Action) $r1.get($i0);
                            $r1.setItem($r5.titleDS, $r5.imageResId);
                        }

                        public void onClick(int $i0) throws  {
                            ((AnonymousClass1Action) $r1.get($i0)).run();
                            $r2.dismiss();
                        }
                    });
                    $r2.show();
                }
            });
            return;
        }
        findViewById(C1283R.id.addressPreviewMore).setVisibility(8);
    }

    private void toggleFav() throws  {
        int $i0 = this.mAddressItem.getType();
        if ($i0 == 5 || $i0 == 1 || $i0 == 3) {
            AnonymousClass55 $r1 = new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        AddressPreviewActivity.this.mDtnMgr.eraseAddressItem(AddressPreviewActivity.this.mAddressItem);
                        AddressPreviewActivity.this.setResult(-1);
                        AddressPreviewActivity.this.mWasResultSet = true;
                        AddressPreviewActivity.this.finish();
                    }
                }
            };
            MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_ARE_YOU_SURE_Q), this.mNatMgr.getLanguageString((int) DisplayStrings.DS_REMOVE_FROM_FAVORITES), true, $r1, this.mNatMgr.getLanguageString((int) DisplayStrings.DS_YES), this.mNatMgr.getLanguageString(344), -1);
            return;
        }
        NativeManager.Post(new RunnableUICallback() {
            private int danger;

            class C20871 implements DialogInterface.OnClickListener {
                C20871() throws  {
                }

                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        AddressPreviewActivity.this.addToFavorites();
                        AddressPreviewActivity.this.mDtnMgr.addDangerZoneStat(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                        return;
                    }
                    AddressPreviewActivity.this.mDtnMgr.addDangerZoneStat(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
                }
            }

            class C20882 implements OnCancelListener {
                C20882() throws  {
                }

                public void onCancel(DialogInterface dialog) throws  {
                    AddressPreviewActivity.this.mDtnMgr.addDangerZoneStat(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, "BACK");
                }
            }

            public void event() throws  {
                this.danger = AddressPreviewActivity.this.mDtnMgr.isInDangerZoneNTV(AddressPreviewActivity.this.mAddressItem.getLocationX().intValue(), AddressPreviewActivity.this.mAddressItem.getLocationY().intValue());
            }

            public void callback() throws  {
                if (this.danger >= 0) {
                    MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(AddressPreviewActivity.this.mNatMgr.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), AddressPreviewActivity.this.mNatMgr.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE), false, new C20871(), AddressPreviewActivity.this.mNatMgr.getLanguageString(DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE_BUTTON), AddressPreviewActivity.this.mNatMgr.getLanguageString(344), -1, "dangerous_zone_icon", new C20882(), true, true);
                    return;
                }
                AddressPreviewActivity.this.addToFavorites();
            }
        });
    }

    private void addToFavorites() throws  {
        NameFavoriteView.showNameFavorite(this, this.mAddressItem, new Runnable() {
            public void run() throws  {
                AddressPreviewActivity.this.finish();
            }
        });
    }

    public void showReportSubmenu() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_PLACE_FLAGGING_POPUP_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, this.mAddressItem.venueData.id);
        String $r6 = this.mNatMgr.getLanguageString((int) DisplayStrings.DS_WHATS_WRONG_WITH_THIS_PLACEQ);
        String[] $r3 = new String[]{this.mNatMgr.getLanguageString((int) DisplayStrings.DS_PLACE_DUPLICATE), this.mNatMgr.getLanguageString((int) DisplayStrings.DS_PLACE_CLOSED_MOVED), this.mNatMgr.getLanguageString((int) DisplayStrings.DS_PLACE_INAPPROPRIATE), this.mNatMgr.getLanguageString((int) DisplayStrings.DS_PLACE_WRONG), this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RESIDENTIAL_PLACE), this.mNatMgr.getLanguageString((int) DisplayStrings.DS_FLAG_WRONG_PLACE)};
        final int[] $r2 = new int[]{4, 1, 5, 6, 3, 10};
        this.mFlagType = -1;
        Builder $r1 = new Builder(this, C1283R.style.CustomPopup);
        $r1.setTitle($r6);
        $r1.setItems($r3, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int $i0) throws  {
                AddressPreviewActivity.this.mFlagType = $r2[$i0];
                switch (AddressPreviewActivity.this.mFlagType) {
                    case 1:
                        AddressPreviewActivity.this.gotoMovedOrClosed();
                        return;
                    case 2:
                    case 7:
                    case 8:
                    case 9:
                        break;
                    case 3:
                        AddressPreviewActivity.this.gotoFlagResidential();
                        return;
                    case 4:
                        AddressPreviewActivity.this.gotoFindDuplicate();
                        return;
                    case 5:
                        AddressPreviewActivity.this.gotoIappropreateOrWrongDetails(true);
                        return;
                    case 6:
                        AddressPreviewActivity.this.gotoIappropreateOrWrongDetails(false);
                        return;
                    case 10:
                        AddressPreviewActivity.this.mNatMgr.venueFlag(AddressPreviewActivity.this.mAddressItem.venueData.id, AddressPreviewActivity.this.mFlagType, null, null);
                        AddressPreviewActivity.this.thanksForReport();
                        return;
                    default:
                        break;
                }
            }
        });
        $r1.setIcon(C1283R.drawable.flag_it_popup);
        $r1.setCancelable(true);
        AlertDialog $r10 = $r1.create();
        $r10.setCanceledOnTouchOutside(true);
        $r10.show();
    }

    private void gotoIappropreateOrWrongDetails(boolean $z0) throws  {
        Intent $r1 = new Intent(this, EditTextDialogActivity.class);
        $r1.putExtra(EditTextDialogActivity.ARG_TITLE_DS, $z0 ? (short) 1497 : (short) 1498);
        $r1.putExtra(EditTextDialogActivity.ARG_SUBTITLE_DS, DisplayStrings.DS_TELL_US_MORE);
        $r1.putExtra(EditTextDialogActivity.ARG_HINT_DS, $z0 ? (short) 1513 : (short) 1514);
        $r1.putExtra(EditTextDialogActivity.ARG_SINGLE_LINE, false);
        $r1.putExtra(EditTextDialogActivity.ARG_MIN_LINES, 6);
        $r1.putExtra(EditTextDialogActivity.ARG_SPEECH, false);
        $r1.putExtra(EditTextDialogActivity.ARG_TYPE, 1);
        startActivityForResult($r1, 106);
    }

    private void gotoFindDuplicate() throws  {
        this.mNatMgr.setUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
        this.mNatMgr.venueSearch(this.mAddressItem.venueData.longitude, this.mAddressItem.venueData.latitude);
        this.mNatMgr.OpenProgressPopup(this.mNatMgr.getLanguageString(290));
    }

    private void gotoFlagResidential() throws  {
        AnonymousClass59 $r1 = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int $i0) throws  {
                if ($i0 == 1) {
                    AddressPreviewActivity.this.mNatMgr.venueFlag(AddressPreviewActivity.this.mAddressItem.venueData.id, AddressPreviewActivity.this.mFlagType, null, null);
                    AddressPreviewActivity.this.thanksForReport();
                }
            }
        };
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_ARE_YOU_SURE_Q), this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RESIDENTIAL_PLACE_CONFIRM_DIALOG_BODY), true, $r1, this.mNatMgr.getLanguageString((int) DisplayStrings.DS_RESIDENTIAL_PLACE_CONFIRM_DIALOG_YES), this.mNatMgr.getLanguageString(344), -1);
    }

    private void gotoMovedOrClosed() throws  {
        Intent $r1 = new Intent(this, EditTextDialogActivity.class);
        $r1.putExtra(EditTextDialogActivity.ARG_TITLE_DS, DisplayStrings.DS_PLACE_CLOSED_MOVED);
        $r1.putExtra(EditTextDialogActivity.ARG_SUBTITLE_DS, DisplayStrings.DS_TELL_US_MORE);
        $r1.putExtra(SimpleChoiceActivity.ARG_HINT_DS, 294);
        $r1.putExtra(EditTextDialogActivity.ARG_SINGLE_LINE, false);
        $r1.putExtra(EditTextDialogActivity.ARG_MIN_LINES, 6);
        $r1.putExtra(EditTextDialogActivity.ARG_SPEECH, false);
        $r1.putExtra(EditTextDialogActivity.ARG_TYPE, 1);
        startActivityForResult($r1, 108);
    }

    private void thanksForReport() throws  {
        this.mNatMgr.OpenProgressIconPopup(this.mNatMgr.getLanguageString((int) DisplayStrings.DS_REPORT_PLACE_THANK_YOU), "flag_verified");
        postDelayed(new Runnable() {
            public void run() throws  {
                AddressPreviewActivity.this.mNatMgr.CloseProgressPopup();
            }
        }, 4000);
    }

    public void onPause() throws  {
        super.onPause();
        this.mMapView.onPause();
        if (this.mGallery != null && this.mGallery.isShowing()) {
            this.mGallery.dismiss();
        }
        this.mGallery = null;
        this.mNavNtvMgr.ClearPreviews();
        ((MapViewWrapper) findViewById(C1283R.id.addressPreviewMap)).onPause();
    }

    protected void onDestroy() throws  {
        this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_ETA, this.mHandler);
        this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
        this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_DANGER_ZONE_FOUND, this.mHandler);
        if (isFinishing()) {
            Logger.m36d("AddressPreviewActivity is finishing");
            if (this.mIsClearAdsContext) {
                Analytics.adsContextClear();
            }
        } else {
            Logger.m36d("AddressPreviewActivity is in Orientation Change Process ... ");
        }
        super.onDestroy();
    }

    public void onBackPressed() throws  {
        if (!this.mWasResultSet) {
            Intent $r1 = r4;
            Intent r4 = new Intent();
            $r1.putExtra("position", this.mSelectedItem);
            $r1.putExtra("venue", this.mAddressItem.venueData);
            setResult(0, $r1);
        }
        super.onBackPressed();
    }

    public void onResume() throws  {
        super.onResume();
        if (getResources().getConfiguration().orientation == 1) {
            this.mScrollView.post(new Runnable() {
                public void run() throws  {
                    AddressPreviewActivity.this.mScrollView.scrollTo(0, AddressPreviewActivity.this.mLockHeight);
                }
            });
        }
        this.mMapView.registerOnMapReadyCallback(this.mMapReadyCallback);
        this.mMapView.onResume();
        setupPreview();
        if (this.mEdit) {
            this.mEdit = false;
            onEdit();
        }
        ((MapViewWrapper) findViewById(C1283R.id.addressPreviewMap)).onResume();
        callJavascriptUpdateClient();
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        Bundle $r4;
        if ($r1.what == DriveToNativeManager.UH_ETA) {
            $r4 = $r1.getData();
            onUpdateEta($r4.getString("provider"), $r4.getString("distance"), $r4.getString("id"));
            return true;
        } else if ($r1.what == NativeManager.UH_SEARCH_VENUES) {
            VenueData[] $r9 = (VenueData[]) $r1.getData().getParcelableArray("venue_data");
            this.mNatMgr.unsetUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
            NativeManager $r10 = this.mNatMgr;
            $r10.CloseProgressPopup();
            if ($r9 == null || $r9.length == 0) {
                return true;
            }
            Intent $r2 = r0;
            Intent intent = new Intent(this, OmniSelectionActivity.class);
            $r2.putExtra(OmniSelectionActivity.ARG_TITLE, this.mNatMgr.getLanguageString((int) DisplayStrings.DS_PLACE_DUPLICATE));
            $r2.putExtra(OmniSelectionActivity.ARG_EDIT_BOX_HINT, this.mNatMgr.getLanguageString((int) DisplayStrings.DS_SEARCH_PLACE_TO_MERGE));
            SettingsValue[] $r12 = new SettingsValue[$r9.length];
            $i1 = 0;
            for (VenueData $r3 : $r9) {
                $r14 = this.mAddressItem;
                $r13 = $r14;
                $r14 = $r14.venueData;
                if (!($r14.id.equals($r3.id) || $r3.name == null || $r3.name.isEmpty())) {
                    $r12[$i1] = new SettingsValue($r3.id, $r3.name, false);
                    $r12[$i1].display2 = $r3.getAddressString();
                    $i1++;
                }
            }
            if ($i1 < $r12.length) {
                $r12 = (SettingsValue[]) Arrays.copyOf($r12, $i1);
            }
            $r2.putExtra(OmniSelectionActivity.ARG_VALUES, (Parcelable[]) $r12);
            $r2.putExtra(OmniSelectionActivity.ARG_SEARCH, true);
            $r2.putExtra(OmniSelectionActivity.ARG_FILTER, true);
            startActivityForResult($r2, 107);
            return true;
        } else if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
            this.mDtnMgr.unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this.mHandler);
            AddressItem $r13 = (AddressItem) $r1.getData().getSerializable("address_item");
            if ($r13 == null || $r13.venueData == null) {
                Logger.m38e("AddressPreviewActivity.myHandleMessage(UH_SEARCH_ADD_RESULT) - null address");
                return true;
            } else if (this.mShouldLoadVenue) {
                this.mShouldLoadVenue = false;
                this.mWebViewLoadAnimation.setVisibility(8);
                $r20 = this.mAddressItem;
                $r13.setType($r20.getType());
                $r20 = this.mAddressItem;
                $r13.setId($r20.getId());
                if ($r13.getImage() == null) {
                    $r20 = this.mAddressItem;
                    $r13.setImage($r20.getImage());
                }
                if ($r13.getTitle().isEmpty()) {
                    $r20 = this.mAddressItem;
                    $r13.setTitle($r20.getTitle());
                }
                if ($r13.getDistance().isEmpty()) {
                    $r20 = this.mAddressItem;
                    $r13.setDistance($r20.getDistance());
                }
                this.mAddressItem = $r13;
                refreshView();
                return true;
            } else {
                $r14 = $r13.venueData;
                this.mImageArray = new ArrayList($r14.numImages);
                $i1 = 0;
                while (true) {
                    $r14 = $r13.venueData;
                    if ($i1 >= $r14.numImages) {
                        break;
                    }
                    ArrayList $r22 = this.mImageArray;
                    String[] $r24 = $r13.venueData;
                    $r3 = $r24;
                    String $r5 = $r24.imageURLs[$i1];
                    $r24 = $r13.venueData;
                    $r3 = $r24;
                    String $r6 = $r24.imageThumbnailURLs[$i1];
                    $r24 = $r13.venueData;
                    $r3 = $r24;
                    String $r7 = $r24.imageReporters[$i1];
                    $r24 = $r13.venueData;
                    $r3 = $r24;
                    String $r25 = $r24.imageReporterMoods[$i1];
                    boolean[] $r26 = $r13.venueData;
                    $r3 = $r26;
                    boolean $z0 = $r26.imageLiked[$i1];
                    $r26 = $r13.venueData;
                    $r3 = $r26;
                    $r22.add(new VenueImage($r5, $r6, $r7, $r25, $z0, $r26.imageByMe[$i1], false));
                    $i1++;
                }
                if (this.mGallery != null) {
                    PlacePhotoGallery $r27 = this.mGallery;
                    if ($r27.isShowing()) {
                        this.mGallery.updateImages(this.mImageArray);
                    }
                }
                $r14 = this.mAddressItem;
                $r13 = $r14;
                $r14.venueData.bHasMoreData = false;
                return true;
            }
        } else if ($r1.what == NavigateNativeManager.UH_SUGGEST_BEST_PARKING) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_PARKING_SEARCH_LATENCY).addParam("TIME", System.currentTimeMillis() - this.parkingRequestTime).send();
            NavigateNativeManager.instance().unsetUpdateHandler(NavigateNativeManager.UH_SUGGEST_BEST_PARKING, this.mHandler);
            this.parkingSuggestionLoaded = true;
            $r4 = $r1.getData();
            this.parkingSuggestionMore = $r4.getBoolean("more");
            this.parkingSuggestionAddressItem = (AddressItem) $r4.getSerializable("addressItem");
            this.parkingSuggestionDistance = $r4.getInt("parkingDistance");
            this.parkingSuggestionPopular = $r4.getBoolean("parkingPopular");
            setupParking();
            refreshView();
            return true;
        } else if ($r1.what == NavigateNativeManager.UH_CALC_ETA) {
            this.mParkingETA = $r1.getData().getInt("eta");
            formatETA();
            return true;
        } else {
            if ($r1.what == DriveToNativeManager.UH_DANGER_ZONE_FOUND) {
                $r4 = $r1.getData();
                $i2 = $r4.getInt("lon", 0);
                $i1 = $r4.getInt("lat", 0);
                if (this.mAddressItem != null) {
                    $r20 = this.mAddressItem;
                    if ($r20.getLocationX().intValue() == $i2) {
                        $r20 = this.mAddressItem;
                        if ($r20.getLocationY().intValue() == $i1) {
                            setDangerZoneTitle($r4.getInt("tv"));
                        }
                    }
                }
            }
            return super.myHandleMessage($r1);
        }
    }

    private void formatETA() throws  {
        ProgressAnimation $r2 = (ProgressAnimation) findViewById(C1283R.id.addressPreviewPlaceLoader);
        $r2.stop();
        $r2.setVisibility(8);
        TextView $r3 = (TextView) findViewById(C1283R.id.addressPreviewPlaceDistance);
        $r3.setVisibility(0);
        TimeZone $r5 = Calendar.getInstance().getTimeZone();
        DateFormat $r6 = android.text.format.DateFormat.getTimeFormat(this);
        $r6.setTimeZone($r5);
        int $i0 = NavigateNativeManager.instance().calcWalkingMinutesNTV(this.mParkingDistance);
        long $l1 = System.currentTimeMillis();
        int $i2 = this.mParkingETA;
        long $l3 = $i2 * 1000;
        int $i22 = $l3;
        $l3 = (long) $l3;
        String $r9 = $r6.format(new Date($l1 + $l3));
        if ($i0 > 0) {
            $r3.setText(DisplayStrings.displayStringF(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_ETA_AND_WALK_PS_PD, new Object[]{$r9, Integer.valueOf($i0)}));
            return;
        }
        $r3.setText(DisplayStrings.displayStringF(DisplayStrings.DS_LOCATION_PREVIEW_PARKING_ETA_PS, new Object[]{$r9}));
    }

    public void nextSearchResult(View v) throws  {
        if (this.mAddressItemList != null && this.mAddressItemList.size() > this.mSelectedItem + 1) {
            this.mSelectedItem++;
            refreshView();
            this.mDtnMgr.notifyAddressItemClicked(this.mSelectedItem);
            this.mDtnMgr.centerMapInAddressOptionsView(this.mAddressItem.index, this.mAddressItem.getLocationX().intValue(), this.mAddressItem.getLocationY().intValue(), true, this.mAddressItem.getIcon());
            this.mDtnMgr.notifyAddressItemShownBeforeNavigate(this.mAddressItem.index);
            logAnalyticsAds(AnalyticsEvents.ANALYTICS_EVENT_ADS_PREVIEW_SHOWN);
        }
    }

    public void prevSearchResult(View v) throws  {
        if (this.mSelectedItem != 0) {
            this.mSelectedItem--;
            refreshView();
            this.mDtnMgr.centerMapInAddressOptionsView(this.mAddressItem.index, this.mAddressItem.getLocationX().intValue(), this.mAddressItem.getLocationY().intValue(), true, this.mAddressItem.getIcon());
            this.mDtnMgr.notifyAddressItemShownBeforeNavigate(this.mAddressItem.index);
        }
    }

    public void navigateCallback(int $i0) throws  {
        if ($i0 == 0) {
            DriveToNativeManager.getInstance().notifyAddressItemNavigate(this.mAddressItem.index);
            setResult(-1);
            this.mWasResultSet = true;
        }
    }

    public AddressItem getAddressItem() throws  {
        return this.mAddressItem;
    }

    private void logAnalyticsAds(String $r1) throws  {
        Analytics.logAdsContext($r1);
    }

    public void onUpdateEta(String provider, String $r2, String $r3) throws  {
        if (this.mAddressItem.getId() != null && this.mAddressItem.getId().equals($r3)) {
            ((TextView) findViewById(C1283R.id.addressPreviewPlaceDistance)).setText($r2);
        }
    }

    public boolean isOpen(OpeningHours $r1) throws  {
        String $r2 = $r1.from;
        if ($r2 == null || $r2.isEmpty()) {
            $r2 = "00:00";
        }
        String $r3 = $r1.to;
        String $r4 = $r3;
        if ($r3 == null || $r3.isEmpty()) {
            $r4 = "24:00";
        }
        int $i1 = Integer.parseInt($r2.substring(0, $r2.indexOf(58)));
        int $i0 = Integer.parseInt($r4.substring(0, $r4.indexOf(58)));
        if ($i1 <= $i0) {
            if ($r1.days[Calendar.getInstance().get(7) - 1] != 0) {
                if ($r2.contentEquals($r4)) {
                    return true;
                }
                if (checkCours(Calendar.getInstance().get(11), $i1, $i0, Calendar.getInstance().get(12), Integer.parseInt($r2.substring($r2.indexOf(58) + 1)), Integer.parseInt($r4.substring($r4.indexOf(58) + 1)))) {
                    return true;
                }
            }
        }
        int $i2 = Calendar.getInstance().get(11);
        int $i3 = Calendar.getInstance().get(7) - 1;
        if ($i2 <= $i0) {
            if ($r1.days[($i3 + 6) % 7] != 0) {
                if (checkCours($i2, 0, $i0, Calendar.getInstance().get(12), 0, Integer.parseInt($r4.substring($r4.indexOf(58) + 1)))) {
                    return true;
                }
            }
        } else if ($i2 >= $i1 && $r1.days[$i3] != 0) {
            $i3 = Calendar.getInstance().get(12);
            int $i4 = Integer.parseInt($r2.substring($r2.indexOf(58) + 1));
            int $i5 = Integer.parseInt($r4.substring($r4.indexOf(58) + 1));
            if (checkCours($i2, $i1, 24, $i3, $i4, 0)) {
                return true;
            }
            if (checkCours($i2, 0, $i0, $i3, 0, $i5)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCours(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5) throws  {
        if ($i0 < $i2 && $i0 > $i1) {
            return true;
        }
        if ($i0 != $i1 || $i0 >= $i2) {
            if ($i0 != $i2 || $i0 <= $i1) {
                if ($i0 == $i2 && $i0 == $i1 && $i3 < $i5 && $i3 >= $i4) {
                    return true;
                }
            } else if ($i3 < $i5) {
                return true;
            }
        } else if ($i3 >= $i4) {
            return true;
        }
        return false;
    }

    private void callJavaScript(WebView $r1, int $i0, String $r2) throws  {
        StringBuilder $r3 = new StringBuilder();
        $r3.append("javascript:try{Android.onResponse(");
        $r3.append($i0);
        $r3.append(",");
        $r3.append($r2);
        $r3.append(")}catch(error){Android.onError(error.message);}");
        $r2 = $r3.toString();
        Logger.m36d("callJavaScript: " + $r2);
        $r1.loadUrl($r2);
    }

    private void callJavascriptUpdateClient() throws  {
        if (this.mAddressItem != null && !TextUtils.isEmpty(this.mAddressItem.brandId)) {
            try {
                JSONObject $r2 = new JSONObject();
                $r2.put("opted_in", MyWazeNativeManager.getInstance().isBrandOptedIn(this.mAddressItem.brandId));
                this.mWebView.loadUrl("javascript:if(W.updateClientEnv) W.updateClientEnv(" + $r2.toString() + ");");
            } catch (Exception $r1) {
                Logger.m39e("AddressPreviewActivity:Exception pccurred while trying to create json", $r1);
            }
        }
    }

    protected void openShareActivity() throws  {
        if (!MyWazeNativeManager.getInstance().getContactLoggedInNTV()) {
            Intent $r2 = new Intent(this, PhoneRegisterActivity.class);
            $r2.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 0);
            $r2.putExtra(PhoneNumberSignInActivity.FON_SHON_REA_SON, AnalyticsEvents.ANALYTICS_PHONE_DIALOG_MODE_FEATURE_REQ);
            startActivityForResult($r2, 111);
        } else if (NativeManager.getInstance().IsAccessToContactsEnableNTV()) {
            ShareUtility.OpenShareActivity(this, ShareType.ShareType_ShareSelection, null, this.mAddressItem, null);
        } else {
            new PhoneRequestAccessDialog(this, new PhoneRequestAccessResultListener() {
                public void onResult(boolean $z0) throws  {
                    if ($z0) {
                        AddressPreviewActivity.this.openShareActivity();
                    }
                }
            }).show();
        }
    }

    private void onEdit() throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_SUGGEST_EDIT, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, this.mAddressItem.venueData.id);
        reportClick("EDIT");
        Intent $r1 = r6;
        Intent r6 = new Intent(this, EditPlaceFlowActivity.class);
        $r1.putExtra(VenueData.class.getName(), this.mAddressItem.venueData);
        startActivityForResult($r1, 109);
    }
}
