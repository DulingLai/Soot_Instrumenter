package com.waze.reports;

import android.app.Fragment;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.AlphaAnimation;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LocationFactory;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.animation.easing.AnimationEasingManager;
import com.waze.animation.easing.AnimationEasingManager.EaseType;
import com.waze.animation.easing.AnimationEasingManager.EasingCallback;
import com.waze.animation.easing.Circ;
import com.waze.animation.easing.Elastic;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.PointsView;
import com.waze.map.CanvasFont;
import com.waze.map.MapView;
import com.waze.navigate.NavigateNativeManager;
import com.waze.reports.EditPlacePointsHolder.PointsType;
import com.waze.reports.EditPlaceValidatorsHolder.ValidatorType;
import com.waze.reports.PhotoPagerSection.VenueImage;
import com.waze.reports.PlacePhotoGallery.IPhotoGalleryListener;
import com.waze.reports.PointsViewTextWatcher.HasContentValidator;
import com.waze.reports.PointsViewTextWatcher.LengthValidator;
import com.waze.reports.PointsViewTextWatcher.PaternValidator;
import com.waze.reports.PointsViewTextWatcher.TextValidator;
import com.waze.reports.PointsViewTextWatcher.ValidatedPointsViewsMgr;
import com.waze.settings.SettingsTitleText;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class EditPlaceFragment extends Fragment implements ValidatedPointsViewsMgr {
    private WazeTextView mAboutEdit;
    private PointsView mCategoriesPtsView;
    private WazeTextView mCityStreetMain;
    private WazeTextView mCityStreetSub;
    private float mDensity;
    private boolean mDidEdit = false;
    private EditText mETName;
    private EditText mETNumber;
    private EditText mETPhone;
    private EditText mETWebsite;
    private int mEarnedPoints = 0;
    private TextValidator mHasContentValidator = new HasContentValidator();
    private ArrayList<VenueImage> mImageArray;
    private boolean mIsRolling = false;
    private boolean mIsSettingPreExistingValues;
    private boolean mIsUploading = false;
    private ImageView mMapImage;
    private MapView mMapView;
    private final RunnableExecutor mNativeCanvasReadyEvent = new C24591();
    private NavigateNativeManager mNavNtvMgr;
    private NativeManager mNm;
    private VenueData mOrigVenue;
    private PhotoPagerSection mPhotoPagerSection;
    private int mPrevPoints = 0;
    private Runnable mReplaceMapRunnable;
    private int mScrollY = 0;
    private ThankYouDialog mTyd;
    private ArrayList<PointsView> mValidatedPointsViews = new ArrayList(16);
    private VenueData mVenue;
    private boolean mWasAddressOptionsViewSet = false;
    private boolean mWereCategoriesChanged = false;
    private boolean mWereCategoriesPointsGiven = false;
    private View f93r;

    class C24591 extends RunnableExecutor {
        C24591() {
        }

        public void event() {
            if (EditPlaceFragment.this.mVenue.longitude == 0 || EditPlaceFragment.this.mVenue.latitude == 0) {
                NativeLocation nLoc = LocationFactory.getNativeLocation(LocationFactory.getInstance().getLastLocation());
                EditPlaceFragment.this.mVenue.longitude = nLoc.mLongtitude;
                EditPlaceFragment.this.mVenue.latitude = nLoc.mLatitude;
            }
            EditPlaceFragment.this.mNavNtvMgr.SetPreviewPoiPosition(EditPlaceFragment.this.mVenue.longitude, EditPlaceFragment.this.mVenue.latitude, null, false);
            EditPlaceFragment.this.mNavNtvMgr.PreviewCanvasFocusOn(EditPlaceFragment.this.mVenue.longitude, EditPlaceFragment.this.mVenue.latitude, DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT);
        }
    }

    class C24624 implements OnClickListener {
        C24624() {
        }

        public void onClick(View v) {
            EditPlaceFragment.this.checkIfUSerIsGood();
        }
    }

    class C24635 implements OnClickListener {
        C24635() {
        }

        public void onClick(View v) {
            if (!EditPlaceFragment.this.mIsUploading) {
                Bundle cameraButtonLocation = new Bundle();
                int[] location = new int[2];
                v.getLocationInWindow(location);
                cameraButtonLocation.putInt("left", location[0]);
                cameraButtonLocation.putInt("top", location[1]);
                cameraButtonLocation.putInt("width", v.getWidth());
                cameraButtonLocation.putInt("height", v.getHeight());
                EditTextUtils.closeKeyboard(EditPlaceFragment.this.getActivity(), EditPlaceFragment.this.f93r);
                ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).goToTakePhoto(cameraButtonLocation);
            }
        }
    }

    class C24646 implements IPhotoGalleryListener {
        C24646() {
        }

        public void onScroll(int position) {
        }

        public void onFlag(int position, int reason) {
            EditPlaceFragment.this.mNm.venueFlagImage(EditPlaceFragment.this.mVenue.id, EditPlaceFragment.this.mVenue.imageURLs[position], reason);
            EditPlaceFragment.this.mVenue.removeImage(position);
            EditPlaceFragment.this.mImageArray.remove(position);
            EditPlaceFragment.this.mPhotoPagerSection.venueUpdated(EditPlaceFragment.this.mImageArray);
        }

        public void onDelete(int position) {
            EditPlaceFragment.this.mNm.venueDeleteImage(EditPlaceFragment.this.mVenue.id, EditPlaceFragment.this.mVenue.imageURLs[position]);
            EditPlaceFragment.this.mVenue.removeNewImageId(position - (EditPlaceFragment.this.mVenue.numImages - EditPlaceFragment.this.mVenue.numNewImages));
            EditPlaceFragment.this.mVenue.removeImage(position);
            EditPlaceFragment.this.mImageArray.remove(position);
            EditPlaceFragment.this.mPhotoPagerSection.venueUpdated(EditPlaceFragment.this.mImageArray);
            EditPlaceFragment.this.mEarnedPoints = EditPlaceFragment.this.mEarnedPoints - EditPlacePointsHolder.getPoints(PointsType.Images);
            EditPlaceFragment.this.updatePoints();
        }

        public void onLike(int position) {
            EditPlaceFragment.this.mVenue.imageLiked[position] = true;
            EditPlaceFragment.this.mNm.venueLikeImage(EditPlaceFragment.this.mVenue.id, EditPlaceFragment.this.mVenue.imageURLs[position]);
        }

        public void onUnlike(int position) {
            EditPlaceFragment.this.mVenue.imageLiked[position] = false;
            EditPlaceFragment.this.mNm.venueUnlikeImage(EditPlaceFragment.this.mVenue.id, EditPlaceFragment.this.mVenue.imageURLs[position]);
        }
    }

    class C24657 implements OnClickListener {
        C24657() {
        }

        public void onClick(View v) {
            ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).goToPickCityStreet();
        }
    }

    class C24668 implements OnClickListener {
        C24668() {
        }

        public void onClick(View v) {
            EditTextUtils.closeKeyboard(EditPlaceFragment.this.getActivity(), EditPlaceFragment.this.f93r);
            ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).goToEditMap();
        }
    }

    class C24679 implements Runnable {
        C24679() {
        }

        public void run() {
        }
    }

    public void setVenue(VenueData venueData) {
        this.mVenue = venueData;
        this.mOrigVenue = this.mVenue.clone();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int i = 0;
        super.onCreateView(inflater, container, savedInstanceState);
        this.mDensity = getResources().getDisplayMetrics().density;
        this.f93r = inflater.inflate(C1283R.layout.edit_place, container, false);
        this.mValidatedPointsViews.clear();
        this.mEarnedPoints = 0;
        this.mWereCategoriesPointsGiven = false;
        setUpFragment();
        updateVenueFields();
        int pts = EditPlacePointsHolder.getPoints(PointsType.Images);
        int i2 = this.mVenue.numNewImages;
        if (this.mIsUploading) {
            i = 1;
        }
        updatePoints((i2 + i) * pts);
        if (this.mVenue.wasLocationChanged) {
            updatePoints(EditPlacePointsHolder.getPoints(PointsType.Location));
        }
        if (this.mEarnedPoints == 0) {
            this.f93r.findViewById(C1283R.id.editPlacePointsCollectedLayout).setVisibility(8);
        }
        updatePoints();
        if (this.mScrollY > 0) {
            final ScrollView sv = (ScrollView) this.f93r.findViewById(C1283R.id.theScrollView);
            sv.post(new Runnable() {
                public void run() {
                    sv.scrollTo(0, EditPlaceFragment.this.mScrollY);
                }
            });
        }
        return this.f93r;
    }

    public void onDestroy() {
        if (this.mTyd != null) {
            this.mTyd.dismiss();
        }
        super.onDestroy();
    }

    private void unsetAddressOptionsView() {
        if (this.mWasAddressOptionsViewSet) {
            this.mWasAddressOptionsViewSet = false;
        }
    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(EditPlaceFragment.class.getName() + ".mVenue", this.mVenue);
        outState.putParcelable(EditPlaceFragment.class.getName() + ".mOrigVenue", this.mOrigVenue);
        outState.putInt(EditPlaceFragment.class.getName() + ".mScrollY", this.mScrollY);
        outState.putBoolean(EditPlaceFragment.class.getName() + ".mIsUploading", this.mIsUploading);
        outState.putBoolean(EditPlaceFragment.class.getName() + ".mDidEdit", this.mDidEdit);
        super.onSaveInstanceState(outState);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNm = NativeManager.getInstance();
        this.mNavNtvMgr = NavigateNativeManager.instance();
        if (savedInstanceState != null) {
            this.mVenue = (VenueData) savedInstanceState.getParcelable(EditPlaceFragment.class.getName() + ".mVenue");
            this.mOrigVenue = (VenueData) savedInstanceState.getParcelable(EditPlaceFragment.class.getName() + ".mOrigVenue");
            this.mScrollY = savedInstanceState.getInt(EditPlaceFragment.class.getName() + ".mScrollY");
            this.mIsUploading = savedInstanceState.getBoolean(EditPlaceFragment.class.getName() + ".mIsUploading");
            this.mDidEdit = savedInstanceState.getBoolean(EditPlaceFragment.class.getName() + ".mDidEdit");
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.mMapView.onPause();
        setUpFragment();
        this.mMapView.onResume();
    }

    void updateVenueFields() {
        this.mIsSettingPreExistingValues = true;
        this.mETNumber.setText(this.mVenue.houseNumber);
        this.mETName.setText(this.mVenue.name);
        this.mETPhone.setText(this.mVenue.phoneNumber);
        this.mETWebsite.setText(this.mVenue.website);
        this.mIsSettingPreExistingValues = false;
    }

    void updateExtVenueFields() {
        this.mIsSettingPreExistingValues = true;
        if (this.mVenue.street.isEmpty()) {
            this.mCityStreetMain.setText(this.mVenue.city);
            this.mCityStreetSub.setVisibility(8);
        } else if (this.mVenue.city.isEmpty()) {
            this.mCityStreetMain.setText(this.mVenue.street);
            this.mCityStreetSub.setVisibility(8);
        } else {
            this.mCityStreetMain.setText(this.mVenue.street);
            this.mCityStreetSub.setText(this.mVenue.city);
            this.mCityStreetSub.setVisibility(0);
        }
        PointsView numberPts = (PointsView) this.f93r.findViewById(C1283R.id.editPlaceDetailsNumberPoints);
        if (this.mVenue.street == null || this.mVenue.street.isEmpty()) {
            this.mETNumber.setText("");
            this.mETNumber.setEnabled(false);
            this.mETNumber.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            numberPts.setAlpha(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        } else {
            this.mETNumber.setEnabled(true);
            this.mETNumber.setAlpha(1.0f);
            numberPts.setAlpha(1.0f);
        }
        this.mAboutEdit.setText(this.mVenue.about);
        ((WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceServicesText)).setText(EditPlaceServicesFragment.getServicesString(this.mVenue));
        ((WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceOpeningHrsText)).setText(getOpeningHoursString(this.mVenue));
        this.mIsSettingPreExistingValues = false;
    }

    private void checkIfUSerIsGood() {
        boolean z = true;
        String houseNumber = this.mETNumber.getText().toString();
        boolean isUserGood = true;
        Iterator it = this.mValidatedPointsViews.iterator();
        while (it.hasNext()) {
            PointsView pv = (PointsView) it.next();
            if (!pv.isValid()) {
                if (isUserGood) {
                    focusOnView(pv);
                }
                AnimationUtils.flashView(pv);
                isUserGood = false;
            }
        }
        if (this.mETName.getText().toString().isEmpty() && !this.mOrigVenue.name.isEmpty()) {
            if (isUserGood) {
                focusOnView(this.f93r.findViewById(C1283R.id.editPlaceNameLayout));
            }
            AnimationUtils.flashView(this.f93r.findViewById(C1283R.id.editPlaceNamePoints));
            isUserGood = false;
        }
        if (this.mVenue.city.isEmpty() && !this.mOrigVenue.city.isEmpty()) {
            if (isUserGood) {
                focusOnView(this.f93r.findViewById(C1283R.id.editPlaceDetailsCityStreetLayout));
            }
            AnimationUtils.flashView(this.f93r.findViewById(C1283R.id.editPlaceDetailsCityStreetPoints));
            isUserGood = false;
        }
        if (this.mVenue.numCategories == 0 && this.mOrigVenue.numCategories != 0) {
            if (isUserGood) {
                focusOnView(this.f93r.findViewById(C1283R.id.editPlaceCategoriesPlaceholder));
            }
            AnimationUtils.flashView(this.mCategoriesPtsView);
            isUserGood = false;
        }
        if (isUserGood) {
            int i;
            this.mVenue.houseNumber = houseNumber;
            this.mVenue.name = this.mETName.getText().toString();
            this.mVenue.phoneNumber = this.mETPhone.getText().toString();
            this.mVenue.website = this.mETWebsite.getText().toString();
            int i2 = this.mVenue.numNewImages;
            if (this.mIsUploading) {
                i = 1;
            } else {
                i = 0;
            }
            int photosTaken = i2 + i;
            EditPlaceFlowActivity editPlaceFlowActivity = (EditPlaceFlowActivity) getActivity();
            VenueData venueData = this.mVenue;
            VenueData venueData2 = this.mOrigVenue;
            if (photosTaken <= 0) {
                z = false;
            }
            editPlaceFlowActivity.sendVenue(venueData, venueData2, z, this.mEarnedPoints);
            this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
        }
    }

    private final void focusOnView(View v) {
        final ViewParent sv = (ScrollView) this.f93r.findViewById(C1283R.id.theScrollView);
        while (v.getParent().getParent() != sv) {
            v = (View) v.getParent();
        }
        final View child = v;
        sv.post(new Runnable() {
            public void run() {
                sv.smoothScrollTo(0, child.getTop() - ((int) (EditPlaceFragment.this.mDensity * 100.0f)));
            }
        });
    }

    private void setUpFragment() {
        TitleBar titleBar = (TitleBar) this.f93r.findViewById(C1283R.id.theTitleBar);
        titleBar.init(getActivity(), (int) DisplayStrings.DS_EDIT_PLACE, 375);
        titleBar.setOnClickCloseListener(new C24624());
        if (!this.mDidEdit) {
            titleBar.setCloseButtonDisabled(true);
        }
        ((WazeTextView) this.f93r.findViewById(C1283R.id.editPlacePointsCollected)).setText(this.mNm.getLanguageString(DisplayStrings.DS_POINTS_COLLECTED));
        if (this.mVenue.numImages > 1) {
            ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceAddPhotoTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_PHOTOS));
        } else {
            ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceAddPhotoTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_PHOTO));
        }
        this.mPhotoPagerSection = new PhotoPagerSection((ActivityBase) getActivity(), this.f93r, true, new C24635());
        this.mPhotoPagerSection.setInProgress(this.mIsUploading);
        imageArrayLoad();
        this.mPhotoPagerSection.setVenue(this.mImageArray, new C24646());
        ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceNameTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_NAME));
        int pts = EditPlacePointsHolder.getPoints(PointsType.Name);
        this.mETName = setEditTextAndPoints(C1283R.id.editPlaceNamePoints, C1283R.id.editPlaceName, 1500, this.mOrigVenue.name, pts, new PaternValidator(EditPlaceValidatorsHolder.getValidator(ValidatorType.Name), this.mOrigVenue.name.isEmpty()));
        this.mETName.setFilters(new InputFilter[]{new LengthFilter(this.mNm.getVenueMaxNameLengthNTV())});
        ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceDetailsTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_ADDRESS));
        pts = EditPlacePointsHolder.getPoints(PointsType.City);
        PointsView cityPts = (PointsView) this.f93r.findViewById(C1283R.id.editPlaceDetailsCityStreetPoints);
        this.mCityStreetMain = (WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceDetailsCityStreetMain);
        this.mCityStreetSub = (WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceDetailsCityStreetSub);
        String origString = this.mOrigVenue.street;
        if (origString == null || origString.isEmpty()) {
            origString = this.mOrigVenue.city;
        }
        this.mCityStreetMain.addTextChangedListener(new PointsViewTextWatcher(this, cityPts, pts, this.mHasContentValidator, origString));
        this.mCityStreetMain.setHint(this.mNm.getLanguageString(DisplayStrings.DS_STREET_NAME));
        this.mCityStreetSub.setHint(this.mNm.getLanguageString(DisplayStrings.DS_CITY));
        this.f93r.findViewById(C1283R.id.editPlaceDetailsCityStreetLayout).setOnClickListener(new C24657());
        pts = EditPlacePointsHolder.getPoints(PointsType.HouseNumber);
        PaternValidator houseValidator = null;
        if (EditPlaceValidatorsHolder.getValidator(ValidatorType.HouseNumber) != null) {
            PaternValidator paternValidator = new PaternValidator(EditPlaceValidatorsHolder.getValidator(ValidatorType.HouseNumber), true);
        }
        this.mETNumber = setEditTextAndPoints(C1283R.id.editPlaceDetailsNumberPoints, C1283R.id.editPlaceDetailsNumber, DisplayStrings.DS_, this.mOrigVenue.houseNumber, pts, houseValidator);
        this.mETNumber.setHint(this.mNm.getLanguageString(DisplayStrings.DS_HOUSE_NUMBER));
        ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceLocationTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_LOCATION));
        this.mMapView = (MapView) this.f93r.findViewById(C1283R.id.editPlaceAddressMap);
        this.mMapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEvent);
        this.mMapImage = (ImageView) this.f93r.findViewById(C1283R.id.editPlaceAddressMapImage);
        this.f93r.findViewById(C1283R.id.editPlaceAddressMapFrame).setOnClickListener(new C24668());
        this.mMapView.setHandleTouch(false);
        this.mReplaceMapRunnable = new C24679();
        ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceCategoriesTitle)).setText(this.mNm.getLanguageString(350));
        refreshCategories((LinearLayout) this.f93r.findViewById(C1283R.id.editPlaceCategoriesPlaceholder));
        ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceServicesTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_SERVICES));
        this.f93r.findViewById(C1283R.id.editPlaceServices).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditTextUtils.closeKeyboard(EditPlaceFragment.this.getActivity(), EditPlaceFragment.this.f93r);
                EditPlaceFragment.this.mVenue.numServices = EditPlaceServicesFragment.sortServiceIdsArray(EditPlaceFragment.this.mVenue.services, EditPlaceFragment.this.mVenue.numServices);
                EditPlaceFragment.this.mOrigVenue.numServices = EditPlaceServicesFragment.sortServiceIdsArray(EditPlaceFragment.this.mOrigVenue.services, EditPlaceFragment.this.mOrigVenue.numServices);
                ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).goToServices();
            }
        });
        ((WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceServicesText)).setHint(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        PointsView srvcPts = (PointsView) this.f93r.findViewById(C1283R.id.editPlaceServicesPts);
        pts = EditPlacePointsHolder.getPoints(PointsType.Services);
        String servicesString = EditPlaceServicesFragment.getServicesString(this.mOrigVenue);
        WazeTextView servicesTextView = (WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceServicesText);
        servicesTextView.setText(servicesString);
        servicesTextView.addTextChangedListener(new PointsViewTextWatcher(this, srvcPts, pts, null, servicesString));
        ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceOpeningHrsTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_OPENING_HOURS));
        this.f93r.findViewById(C1283R.id.editPlaceOpeningHrs).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditTextUtils.closeKeyboard(EditPlaceFragment.this.getActivity(), EditPlaceFragment.this.f93r);
                ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).goToOpeningHours();
            }
        });
        ((WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceOpeningHrsText)).setHint(this.mNm.getLanguageString(DisplayStrings.DS_TAP_TO_ADD));
        PointsView opHrsPts = (PointsView) this.f93r.findViewById(C1283R.id.editPlaceOpeningHrsPts);
        pts = EditPlacePointsHolder.getPoints(PointsType.OpeningHours);
        String openingHoursString = getOpeningHoursString(this.mOrigVenue);
        WazeTextView opHrsTextView = (WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceOpeningHrsText);
        opHrsTextView.setText(openingHoursString);
        opHrsTextView.addTextChangedListener(new PointsViewTextWatcher(this, opHrsPts, pts, null, openingHoursString));
        ((SettingsTitleText) this.f93r.findViewById(C1283R.id.editPlaceMoreDetailsTitle)).setText(this.mNm.getLanguageString(369));
        pts = EditPlacePointsHolder.getPoints(PointsType.Description);
        PointsView aboutPts = (PointsView) this.f93r.findViewById(C1283R.id.editPlaceAboutPoints);
        this.mAboutEdit = (WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceAbout);
        this.mAboutEdit.addTextChangedListener(new PointsViewTextWatcher(this, aboutPts, pts, new LengthValidator(3, true), this.mOrigVenue.about));
        this.mAboutEdit.setHint(this.mNm.getLanguageString(DisplayStrings.DS_ABOUT2));
        this.mAboutEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).goToEditAbout(EditPlaceFragment.this.mAboutEdit.getText().toString());
            }
        });
        this.mETPhone = setEditTextAndPoints(C1283R.id.editPlacePhonePoints, C1283R.id.editPlacePhone, DisplayStrings.DS_PHONE_NUMBER, this.mOrigVenue.phoneNumber, EditPlacePointsHolder.getPoints(PointsType.PhoneNumber), new PaternValidator(EditPlaceValidatorsHolder.getValidator(ValidatorType.PhoneNumber), true));
        this.mETWebsite = setEditTextAndPoints(C1283R.id.editPlaceWebsitePoints, C1283R.id.editPlaceWebSite, DisplayStrings.DS_WEBSITE, this.mOrigVenue.website, EditPlacePointsHolder.getPoints(PointsType.URL), new PaternValidator(EditPlaceValidatorsHolder.getValidator(ValidatorType.URL), true));
        WazeSettingsView report = (WazeSettingsView) this.f93r.findViewById(C1283R.id.editPlaceReport);
        report.setText(this.mNm.getLanguageString(DisplayStrings.DS_REPORT_A_PROBLEM));
        report.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditTextUtils.closeKeyboard(EditPlaceFragment.this.getActivity(), EditPlaceFragment.this.f93r);
                ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).showReportSubmenu();
            }
        });
        updatePoints();
    }

    private void imageArrayLoad() {
        this.mImageArray = new ArrayList(this.mVenue.numImages);
        for (int i = 0; i < this.mVenue.numImages; i++) {
            VenueImage vi;
            String uri = this.mVenue.imageURLs[i];
            String thumbUri = this.mVenue.imageThumbnailURLs[i];
            if (uri.startsWith("/")) {
                vi = new VenueImage(Uri.fromFile(new File(uri)).toString(), thumbUri, "", "", false, false, true);
            } else {
                vi = new VenueImage(uri, thumbUri, this.mVenue.imageReporters[i], this.mVenue.imageReporterMoods[i], this.mVenue.imageLiked[i], this.mVenue.imageByMe[i], false);
            }
            this.mImageArray.add(vi);
        }
    }

    private EditText setEditTextAndPoints(int pointsViewId, int editTextId, int hintDS, String initialValue, int points, TextValidator validator) {
        PointsView pv = (PointsView) this.f93r.findViewById(pointsViewId);
        EditText et = (EditText) this.f93r.findViewById(editTextId);
        et.setText(initialValue);
        et.addTextChangedListener(new PointsViewTextWatcher(this, pv, points, validator, initialValue));
        et.setHint(this.mNm.getLanguageString(hintDS));
        return et;
    }

    void updatePoints() {
        if (this.f93r != null) {
            setPointsWheels(true);
        }
    }

    public void onPause() {
        unsetAddressOptionsView();
        this.mMapView.onPause();
        this.mScrollY = ((ScrollView) this.f93r.findViewById(C1283R.id.theScrollView)).getScrollY();
        super.onPause();
    }

    public void onResume() {
        this.mMapView.onResume();
        super.onResume();
        updateExtVenueFields();
    }

    public void refreshCategories(LinearLayout categoriesPlace) {
        boolean z;
        categoriesPlace.removeAllViews();
        if (this.mVenue.numCategories != this.mOrigVenue.numCategories) {
            z = true;
        } else {
            z = false;
        }
        this.mWereCategoriesChanged = z;
        boolean hasParking = false;
        if (this.mVenue != null) {
            int i = 0;
            while (i < this.mVenue.numCategories) {
                if (!(this.mWereCategoriesChanged || this.mVenue.categories[i].contentEquals(this.mOrigVenue.categories[i]))) {
                    this.mWereCategoriesChanged = true;
                }
                if (this.mVenue.categories[i].equals(VenueData.PARKING_LOT_CATEGORY)) {
                    hasParking = true;
                }
                View line = addCategoryLine(categoriesPlace, this.mVenue.categories[i], EditPlaceCategoriesHolder.getCategoryById(this.mVenue.categories[i]), null, true);
                if (hasParking) {
                    line.setBackgroundResource(C1283R.drawable.item_selector_bottom);
                } else if (i == 0) {
                    line.setBackgroundResource(C1283R.drawable.item_selector_top);
                } else {
                    line.setBackgroundResource(C1283R.drawable.item_selector_middle);
                }
                line.setPadding(0, 0, 0, 0);
                i++;
            }
        }
        if (hasParking) {
            this.f93r.findViewById(C1283R.id.editPlaceCategoryCommentLayout).setVisibility(0);
            ((TextView) this.f93r.findViewById(C1283R.id.editPlaceCategoryComment)).setText(DisplayStrings.displayString(DisplayStrings.DS_PLACE_PARKING_CATEGORY_FOOTER));
            return;
        }
        View add = addCategoryLine(categoriesPlace, null, DisplayStrings.displayString(DisplayStrings.DS_TAP_TO_ADD_CATEGORIES), null, false);
        add.setBackgroundResource(this.mVenue.numCategories == 0 ? C1283R.drawable.item_selector_single : C1283R.drawable.item_selector_bottom);
        add.setPadding(0, 0, 0, 0);
        add.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                EditPlaceFragment.this.mVenue.numCategories = EditPlaceCategoriesHolder.sortCategoryIdsArray(EditPlaceFragment.this.mVenue.categories);
                EditPlaceFragment.this.mOrigVenue.numCategories = EditPlaceCategoriesHolder.sortCategoryIdsArray(EditPlaceFragment.this.mOrigVenue.categories);
                EditTextUtils.closeKeyboard(EditPlaceFragment.this.getActivity(), EditPlaceFragment.this.f93r);
                ((EditPlaceFlowActivity) EditPlaceFragment.this.getActivity()).goToAddCategory();
            }
        });
        this.f93r.findViewById(C1283R.id.editPlaceCategoryCommentLayout).setVisibility(8);
    }

    protected View addCategoryLine(final LinearLayout categoriesPlace, final String id, String top, String bottom, boolean bClearable) {
        View newLine = getActivity().getLayoutInflater().inflate(C1283R.layout.two_line_clearable, categoriesPlace, false);
        ((WazeTextView) newLine.findViewById(C1283R.id.twoLineClearableLine1)).setText(top);
        WazeTextView bottomView = (WazeTextView) newLine.findViewById(C1283R.id.twoLineClearableLine2);
        if (bottom == null || bottom.isEmpty()) {
            bottomView.setVisibility(8);
        } else {
            bottomView.setText(bottom);
        }
        if (bClearable) {
            newLine.findViewById(C1283R.id.twoLineClearableClear).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    EditPlaceFragment.this.mVenue.removeCategory(id);
                    EditPlaceFragment.this.refreshCategories(categoriesPlace);
                }
            });
        } else {
            newLine.findViewById(C1283R.id.twoLineClearableClear).setVisibility(8);
            this.mCategoriesPtsView = (PointsView) newLine.findViewById(C1283R.id.twoLineClearablePoints);
            this.mCategoriesPtsView.setVisibility(0);
            boolean valid = this.mVenue.numCategories > 0;
            this.mCategoriesPtsView.setValid(valid);
            int points = EditPlacePointsHolder.getPoints(PointsType.Categories);
            this.mCategoriesPtsView.setPoints(points, this.mOrigVenue.numCategories > 0);
            PointsView pointsView = this.mCategoriesPtsView;
            boolean z = this.mWereCategoriesChanged && valid;
            pointsView.setIsOn(z, valid, false);
            if (this.mWereCategoriesPointsGiven) {
                updatePoints(-points);
            }
            if (this.mWereCategoriesChanged) {
                updatePoints(points);
                onEdit();
            }
            this.mWereCategoriesPointsGiven = this.mWereCategoriesChanged;
            updatePoints();
        }
        categoriesPlace.addView(newLine);
        newLine.getLayoutParams().height = getActivity().getResources().getDimensionPixelSize(C1283R.dimen.settingsItemHeight);
        return newLine;
    }

    public void setOpeningHours(VenueData venue) {
        this.mVenue = venue;
        if (this.f93r == null) {
            this.mDidEdit = true;
            return;
        }
        ((WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceOpeningHrsText)).setText(getOpeningHoursString(venue));
        onEdit();
    }

    public void setServices(VenueData venue) {
        this.mVenue = venue;
        if (this.f93r == null) {
            this.mDidEdit = true;
            return;
        }
        ((WazeTextView) this.f93r.findViewById(C1283R.id.editPlaceServicesText)).setText(EditPlaceServicesFragment.getServicesString(venue));
        onEdit();
    }

    private String getOpeningHoursString(VenueData vd) {
        StringBuilder bob = new StringBuilder();
        for (int i = 0; i < vd.numOpeningHours; i++) {
            OpeningHours oh = vd.openingHours[i];
            bob.append(oh.getDaysString());
            bob.append(": ");
            bob.append("‎");
            bob.append(oh.getHoursString());
            bob.append('\n');
        }
        if (bob.length() > 0) {
            bob.deleteCharAt(bob.length() - 1);
        }
        return bob.toString();
    }

    public void updateMapLocation(VenueData venue) {
        this.mVenue = venue;
        if (this.mMapView == null) {
            this.mDidEdit = true;
            return;
        }
        this.mMapView.setVisibility(0);
        this.mMapView.onResume();
        this.mMapView.removeCallbacks(this.mReplaceMapRunnable);
        this.mMapView.postDelayed(this.mReplaceMapRunnable, 2000);
        if (this.mVenue.wasLocationChanged) {
            updatePoints(EditPlacePointsHolder.getPoints(PointsType.Location));
            updatePoints();
            onEdit();
        }
    }

    public void photoTaken(VenueData venue) {
        this.mVenue = venue;
        this.mIsUploading = true;
        imageArrayLoad();
        if (this.f93r == null) {
            this.mDidEdit = true;
            return;
        }
        this.mPhotoPagerSection.setInProgress(this.mIsUploading);
        this.mPhotoPagerSection.venueUpdated(this.mImageArray);
        updatePoints(EditPlacePointsHolder.getPoints(PointsType.Images));
        updatePoints();
        onEdit();
    }

    public void updatePhotos(VenueData venue) {
        this.mVenue = venue;
        this.mIsUploading = false;
        imageArrayLoad();
        if (this.mPhotoPagerSection != null) {
            this.mPhotoPagerSection.setInProgress(this.mIsUploading);
            this.mPhotoPagerSection.venueUpdated(this.mImageArray);
        }
    }

    public void updateCategories(VenueData venue) {
        this.mVenue = venue;
        if (this.f93r == null) {
            this.mDidEdit = true;
            return;
        }
        refreshCategories((LinearLayout) this.f93r.findViewById(C1283R.id.editPlaceCategoriesPlaceholder));
        onEdit();
    }

    public void updateAbout(VenueData venue) {
        this.mVenue = venue;
        if (this.mAboutEdit != null) {
            this.mAboutEdit.setText(this.mVenue.about);
        }
    }

    private void setPointsWheels(boolean animate) {
        if (!this.mIsRolling) {
            int below;
            int center;
            TextView belowTV;
            TextView centerTV;
            final ScrollView onesSV = (ScrollView) this.f93r.findViewById(C1283R.id.editPlacePointsScrollRight);
            final ScrollView tensSV = (ScrollView) this.f93r.findViewById(C1283R.id.editPlacePointsScrollLeft);
            View layout = this.f93r.findViewById(C1283R.id.editPlacePointsCollectedLayout);
            if (layout.getVisibility() != 0 && this.mEarnedPoints > 0) {
                layout.setVisibility(0);
                if (animate) {
                    AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
                    aa.setDuration(200);
                    layout.setAnimation(aa);
                }
            }
            if (animate) {
                boolean last;
                if (this.mPrevPoints > this.mEarnedPoints) {
                    this.mIsRolling = true;
                    last = this.mPrevPoints - this.mEarnedPoints == 1;
                    below = this.mPrevPoints % 10;
                    center = (below + 9) % 10;
                    belowTV = (TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollBelow);
                    centerTV = (TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollCenter);
                    ((TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + ((below + 8) % 10));
                    belowTV.setText("" + below);
                    centerTV.setText("" + center);
                    onesSV.scrollTo(0, (int) (40.0f * this.mDensity));
                    if (below == 0) {
                        below = this.mPrevPoints / 10;
                        center = (below + 9) % 10;
                        belowTV = (TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollBelow);
                        centerTV = (TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollCenter);
                        ((TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + ((below + 8) % 10));
                        belowTV.setText("" + below);
                        centerTV.setText("" + center);
                        tensSV.scrollTo(0, (int) (40.0f * this.mDensity));
                        roll(onesSV, tensSV, true, last);
                        return;
                    }
                    ((TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + (this.mPrevPoints / 10));
                    tensSV.scrollTo(0, 0);
                    roll(onesSV, null, true, last);
                    return;
                } else if (this.mPrevPoints < this.mEarnedPoints) {
                    this.mIsRolling = true;
                    last = this.mEarnedPoints - this.mPrevPoints == 1;
                    int above = this.mPrevPoints % 10;
                    center = (above + 1) % 10;
                    below = (above + 2) % 10;
                    belowTV = (TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollBelow);
                    centerTV = (TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollCenter);
                    ((TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + above);
                    belowTV.setText("" + below);
                    centerTV.setText("" + center);
                    onesSV.scrollTo(0, 0);
                    if (above == 9) {
                        above = this.mPrevPoints / 10;
                        center = (above + 1) % 10;
                        below = (above + 2) % 10;
                        belowTV = (TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollBelow);
                        centerTV = (TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollCenter);
                        ((TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + above);
                        belowTV.setText("" + below);
                        centerTV.setText("" + center);
                        tensSV.scrollTo(0, 0);
                        roll(onesSV, tensSV, false, last);
                        return;
                    }
                    ((TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + (this.mPrevPoints / 10));
                    tensSV.scrollTo(0, 0);
                    roll(onesSV, null, false, last);
                    return;
                }
            }
            center = this.mEarnedPoints % 10;
            below = (this.mEarnedPoints + 1) % 10;
            belowTV = (TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollBelow);
            centerTV = (TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollCenter);
            ((TextView) onesSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + ((this.mEarnedPoints + 9) % 10));
            belowTV.setText("" + below);
            centerTV.setText("" + center);
            onesSV.scrollTo(0, (int) (20.0f * this.mDensity));
            onesSV.post(new Runnable() {
                public void run() {
                    onesSV.scrollTo(0, (int) (20.0f * EditPlaceFragment.this.mDensity));
                }
            });
            center = this.mEarnedPoints / 10;
            below = (this.mEarnedPoints + 1) % 10;
            belowTV = (TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollBelow);
            centerTV = (TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollCenter);
            ((TextView) tensSV.findViewById(C1283R.id.editPlacePointsScrollAbove)).setText("" + ((this.mEarnedPoints + 9) % 10));
            belowTV.setText("" + below);
            centerTV.setText("" + center);
            tensSV.scrollTo(0, (int) (20.0f * this.mDensity));
            tensSV.post(new Runnable() {
                public void run() {
                    tensSV.scrollTo(0, (int) (20.0f * EditPlaceFragment.this.mDensity));
                }
            });
        }
    }

    private void roll(final ScrollView sv, final ScrollView sv2, final boolean up, boolean last) {
        new AnimationEasingManager(new EasingCallback() {
            public void onValueChanged(double value, double oldValue) {
                int y = up ? (int) (((double) (40.0f * EditPlaceFragment.this.mDensity)) - value) : (int) value;
                sv.scrollTo(0, y);
                if (sv2 != null) {
                    sv2.scrollTo(0, y);
                }
            }

            public void onStarted(double value) {
            }

            public void onFinished(double value) {
                if (up) {
                    EditPlaceFragment.this.mPrevPoints = EditPlaceFragment.this.mPrevPoints - 1;
                } else {
                    EditPlaceFragment.this.mPrevPoints = EditPlaceFragment.this.mPrevPoints + 1;
                }
                EditPlaceFragment.this.mIsRolling = false;
                EditPlaceFragment.this.setPointsWheels(true);
            }
        }).start(last ? Elastic.class : Circ.class, EaseType.EaseOut, 0.0d, (double) (20.0f * this.mDensity), last ? DisplayStrings.DS_SENDING_REPORT_FAILED__PLEASE_RESEND_LATER : 100);
    }

    public void onEdit() {
        if (!this.mDidEdit) {
            this.mDidEdit = true;
            ((TitleBar) this.f93r.findViewById(C1283R.id.theTitleBar)).setCloseButtonDisabled(false);
        }
    }

    public void updateAddress(VenueData venue) {
        this.mVenue = venue;
        if (this.f93r == null) {
            this.mDidEdit = true;
        } else if (this.mCityStreetMain != null && this.mCityStreetSub != null) {
            if (this.mVenue.street.isEmpty()) {
                this.mCityStreetMain.setText(this.mVenue.city);
                this.mCityStreetSub.setVisibility(8);
            } else if (this.mVenue.city.isEmpty()) {
                this.mCityStreetMain.setText(this.mVenue.street);
                this.mCityStreetSub.setVisibility(8);
            } else {
                this.mCityStreetMain.setText(this.mVenue.street);
                this.mCityStreetSub.setText(this.mVenue.city);
                this.mCityStreetSub.setVisibility(0);
            }
            onEdit();
        }
    }

    public void addValidatedPointsView(PointsView pv) {
        this.mValidatedPointsViews.add(pv);
    }

    public void updatePoints(int ptsMod) {
        if (!this.mIsSettingPreExistingValues) {
            this.mEarnedPoints += ptsMod;
        }
    }
}
