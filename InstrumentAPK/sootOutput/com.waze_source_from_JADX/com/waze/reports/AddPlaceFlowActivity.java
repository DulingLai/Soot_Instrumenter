package com.waze.reports;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.os.Parcelable;
import android.view.View;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.LocationFactory;
import com.waze.MsgBox;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.NativeManager.AddressStrings;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.OmniSelectionFragment;
import com.waze.ifs.ui.OmniSelectionFragment.IOmniSelect;
import com.waze.reports.EditMapLocationFragment.IEditMap;
import com.waze.reports.EditPlacePointsHolder.PointsType;
import com.waze.reports.TakePhotoFragment.ITakePhoto;
import com.waze.settings.SettingsValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

public class AddPlaceFlowActivity extends ActivityBase implements ITakePhoto, IOmniSelect, IEditMap {
    private static final int STATE_CHOOSE_PLACE = 2;
    private static final int STATE_MAP_LOCATION = 3;
    private static final int STATE_NEW_PLACE = 5;
    private static final int STATE_SELECT_ADDRESS = 4;
    private static final int STATE_SELECT_CATEGORY = 6;
    private static final int STATE_TAKE_PHOTO = 1;
    private static final String TAG = AddPlaceFlowActivity.class.getName();
    private AddressStrings mAddressStrings;
    private String mDestinationVenueId = null;
    private boolean mForceHouseNumber = false;
    private boolean mFromReportMenu = false;
    private boolean mIsPublic = false;
    private boolean mIsSending = false;
    private boolean mIsUpdate = false;
    private NativeManager mNatMgr;
    private VenueData mOrigVenue = null;
    private String mPhotoId;
    private String mPhotoPath;
    private String mPhotoThumbnailUrl;
    private String mPhotoUrl;
    private int mPoints;
    private String mQuestionId = null;
    private boolean mRetain;
    private boolean mSayThankYou = false;
    private Parcelable[] mSearchVenueResults;
    private VenueData mSelectedVenue = null;
    private int mState;
    private ThankYouDialog mTyd;
    private VenueData mVenue;
    private boolean mbConfirmResidential = false;

    class C24141 implements Runnable {
        C24141() {
        }

        public void run() {
            AddPlaceFlowActivity.this.mAddressStrings = AddPlaceFlowActivity.this.mNatMgr.getAddressByLocationNTV(AddPlaceFlowActivity.this.mVenue.longitude, AddPlaceFlowActivity.this.mVenue.latitude);
        }
    }

    class C24152 implements OnClickListener {
        C24152() {
        }

        public void onClick(DialogInterface dialog, int which) {
            AddPlaceFlowActivity.this.finish();
        }
    }

    class C24173 implements Runnable {

        class C24161 implements OnClickListener {
            C24161() {
            }

            public void onClick(DialogInterface dialog, int which) {
                AddPlaceFlowActivity.this.finish();
            }
        }

        C24173() {
        }

        public void run() {
            if (AddPlaceFlowActivity.this.mSearchVenueResults == null && !AddPlaceFlowActivity.this.isFinishing()) {
                MsgBox.openMessageBoxWithCallback(AddPlaceFlowActivity.this.mNatMgr.getLanguageString(DisplayStrings.DS_UHHOHE), AddPlaceFlowActivity.this.mNatMgr.getLanguageString(DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), false, new C24161());
            }
        }
    }

    class C24184 implements OnClickListener {
        C24184() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == 1) {
                AddPlaceFlowActivity.this.moveToNewPlaceFragment(true);
            }
            AddPlaceFlowActivity.this.mbConfirmResidential = false;
        }
    }

    class C24195 implements View.OnClickListener {
        C24195() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_THANK_YOU_POPUP_CLICKED, "CONTINUE|VENUE_ID", "FALSE|" + AddPlaceFlowActivity.this.mSelectedVenue.id);
            AddPlaceFlowActivity.this.allDone();
        }
    }

    class C24206 implements View.OnClickListener {
        C24206() {
        }

        public void onClick(View v) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_THANK_YOU_POPUP_CLICKED, "CONTINUE|VENUE_ID", "TRUE|" + AddPlaceFlowActivity.this.mSelectedVenue.id);
            AddPlaceFlowActivity.this.wantMore();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mNatMgr = NativeManager.getInstance();
        EditPlaceCategoriesHolder.getCategories();
        EditPlacePointsHolder.getAllPoints();
        Intent caller = getIntent();
        this.mVenue = new VenueData();
        if (caller != null) {
            if (caller.hasExtra("QuestionID")) {
                this.mQuestionId = caller.getStringExtra("QuestionID");
            }
            if (caller.hasExtra("destination_venue_id")) {
                this.mDestinationVenueId = caller.getStringExtra("destination_venue_id");
            }
            if (caller.hasExtra("city")) {
                this.mVenue.city = caller.getStringExtra("city");
            }
            if (caller.hasExtra("street")) {
                this.mVenue.street = caller.getStringExtra("street");
            }
            if (caller.hasExtra("x") && caller.hasExtra("y")) {
                this.mVenue.longitude = caller.getIntExtra("x", 0);
                this.mVenue.latitude = caller.getIntExtra("y", 0);
                this.mVenue.wasLocationChanged = true;
            } else {
                this.mFromReportMenu = true;
                Location loc = LocationFactory.getInstance().getLastLocation();
                if (loc != null) {
                    NativeLocation nLoc = LocationFactory.getNativeLocation(loc);
                    this.mVenue.longitude = nLoc.mLongtitude;
                    this.mVenue.latitude = nLoc.mLatitude;
                    this.mVenue.wasLocationChanged = true;
                } else {
                    finish();
                    return;
                }
            }
            if (caller.hasExtra("venue_data")) {
                this.mSearchVenueResults = caller.getParcelableArrayExtra("venue_data");
            }
        }
        this.mNatMgr.setUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        this.mNatMgr.setUpdateHandler(NativeManager.UH_VENUE_STATUS, this.mHandler);
        NativeManager.Post(new C24141());
        getWindow().setSoftInputMode(3);
        setContentView(C1283R.layout.fragment_activity_empty);
        if (savedInstanceState == null) {
            this.mState = 1;
            getFragmentManager().beginTransaction().add(C1283R.id.container, new TakePhotoFragment()).commit();
        } else {
            this.mState = savedInstanceState.getInt(TAG + ".mState");
            this.mPoints = savedInstanceState.getInt(TAG + ".mPoints");
            this.mIsPublic = savedInstanceState.getBoolean(TAG + ".mIsPublic");
            this.mFromReportMenu = savedInstanceState.getBoolean(TAG + ".mFromReportMenu");
            this.mForceHouseNumber = savedInstanceState.getBoolean(TAG + ".mForceHouseNumber");
            this.mSayThankYou = savedInstanceState.getBoolean(TAG + ".mSayThankYou");
            this.mbConfirmResidential = savedInstanceState.getBoolean(TAG + ".mbConfirmResidential");
            this.mIsSending = savedInstanceState.getBoolean(TAG + ".mIsSending");
            this.mIsUpdate = savedInstanceState.getBoolean(TAG + ".mIsUpdate");
            this.mVenue = (VenueData) savedInstanceState.getParcelable(TAG + ".mVenue");
            this.mSelectedVenue = (VenueData) savedInstanceState.getParcelable(TAG + ".mSelectedVenue");
            this.mOrigVenue = (VenueData) savedInstanceState.getParcelable(TAG + ".mOrigVenue");
            this.mSearchVenueResults = savedInstanceState.getParcelableArray(TAG + ".mSearchVenueResults");
            this.mPhotoPath = savedInstanceState.getString(TAG + ".mPhotoPath");
            this.mPhotoId = savedInstanceState.getString(TAG + ".mPhotoId");
            this.mPhotoUrl = savedInstanceState.getString(TAG + ".mPhotoUrl");
            this.mPhotoThumbnailUrl = savedInstanceState.getString(TAG + ".mPhotoThumbnailUrl");
            this.mQuestionId = savedInstanceState.getString(TAG + ".mQuestionId");
        }
        this.mRetain = false;
        if (this.mSearchVenueResults == null) {
            this.mNatMgr.setUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
            this.mNatMgr.venueSearch(this.mVenue.longitude, this.mVenue.latitude);
        }
        if (this.mbConfirmResidential) {
            confirmResidential();
        } else if (this.mSayThankYou) {
            sayThankYou(0, this.mPoints);
        } else if (this.mIsSending) {
            this.mNatMgr.OpenProgressPopup(this.mNatMgr.getLanguageString(290));
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG + ".mState", this.mState);
        outState.putInt(TAG + ".mPoints", this.mPoints);
        outState.putBoolean(TAG + ".mIsPublic", this.mIsPublic);
        outState.putBoolean(TAG + ".mFromReportMenu", this.mFromReportMenu);
        outState.putBoolean(TAG + ".mForceHouseNumber", this.mForceHouseNumber);
        outState.putBoolean(TAG + ".mSayThankYou", this.mSayThankYou);
        outState.putBoolean(TAG + ".mbConfirmResidential", this.mbConfirmResidential);
        outState.putBoolean(TAG + ".mIsSending", this.mIsSending);
        outState.putBoolean(TAG + ".mIsUpdate", this.mIsUpdate);
        outState.putParcelable(TAG + ".mVenue", this.mVenue);
        outState.putParcelable(TAG + ".mSelectedVenue", this.mSelectedVenue);
        outState.putParcelable(TAG + ".mOrigVenue", this.mOrigVenue);
        outState.putParcelableArray(TAG + ".mSearchVenueResults", this.mSearchVenueResults);
        outState.putString(TAG + ".mPhotoPath", this.mPhotoPath);
        outState.putString(TAG + ".mPhotoId", this.mPhotoId);
        outState.putString(TAG + ".mPhotoUrl", this.mPhotoUrl);
        outState.putString(TAG + ".mPhotoThumbnailUrl", this.mPhotoThumbnailUrl);
        outState.putString(TAG + ".mQuestionId", this.mQuestionId);
        this.mRetain = true;
    }

    protected void onDestroy() {
        if (this.mTyd != null) {
            this.mTyd.dismiss();
        }
        if (!this.mRetain) {
            TakePhotoFragment.cleanUpFiles(null);
        }
        this.mNatMgr.unsetUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
        this.mNatMgr.unsetUpdateHandler(NativeManager.UH_VENUE_STATUS, this.mHandler);
        this.mNatMgr.unsetUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        super.onDestroy();
    }

    public void onBackPressed() {
        switch (this.mState) {
            case 1:
                super.onBackPressed();
                return;
            case 2:
                EditTextUtils.closeKeyboard(this, findViewById(C1283R.id.container));
                this.mState = 1;
                getFragmentManager().beginTransaction().replace(C1283R.id.container, new TakePhotoFragment()).commit();
                return;
            case 3:
                this.mState = 5;
                getFragmentManager().popBackStack();
                return;
            case 4:
                this.mState = 5;
                getFragmentManager().popBackStack();
                return;
            case 5:
                goToVenueSelectFragment();
                return;
            case 6:
                this.mState = 5;
                getFragmentManager().popBackStack();
                return;
            default:
                super.onBackPressed();
                return;
        }
    }

    protected boolean myHandleMessage(Message msg) {
        if (msg.what == NativeManager.UH_SEARCH_VENUES) {
            this.mSearchVenueResults = msg.getData().getParcelableArray("venue_data");
            this.mNatMgr.unsetUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
            if (this.mPhotoPath != null) {
                goToVenueSelectFragment();
            }
            this.mNatMgr.CloseProgressPopup();
            return true;
        } else if (msg.what == NativeManager.UH_VENUE_STATUS) {
            this.mIsSending = false;
            this.mNatMgr.unsetUpdateHandler(NativeManager.UH_VENUE_STATUS, this.mHandler);
            this.mNatMgr.CloseProgressPopup();
            b = msg.getData();
            int res = b.getInt("res");
            int points = b.getInt("points");
            id = b.getString("id");
            if (res >= 0) {
                if (!(this.mIsUpdate || this.mSelectedVenue == null)) {
                    this.mSelectedVenue.id = id;
                }
                Intent result = new Intent();
                result.putExtra("destination_venue_id", id);
                setResult(-1, result);
                sayThankYou(res, points);
            } else if (res == -2) {
                MsgBox.openMessageBoxWithCallback(this.mNatMgr.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNatMgr.getLanguageString(DisplayStrings.DS_YOUVE_BEEN_FLAGGED), false, new C24152());
            } else if (res == -3) {
                this.mForceHouseNumber = true;
                MsgBox.openMessageBoxFull(this.mNatMgr.getLanguageString(DisplayStrings.DS_UHHOHE), this.mNatMgr.getLanguageString(DisplayStrings.DS_PLACE_ADD_LOCATION_ERROR), this.mNatMgr.getLanguageString(334), -1, null);
            } else {
                MsgBox.openMessageBox(this.mNatMgr.getLanguageString(220), this.mNatMgr.getLanguageString(DisplayStrings.DS_PLACE_ADD_ERROR), false);
            }
            return true;
        } else if (msg.what != NativeManager.UH_VENUE_ADD_IMAGE_RESULT) {
            return super.myHandleMessage(msg);
        } else {
            this.mNatMgr.unsetUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
            b = msg.getData();
            String path = b.getString("path");
            id = b.getString("id");
            String imageUrl = b.getString("image_url");
            String imageThumbnailUrl = b.getString("image_thumbnail_url");
            boolean res2 = b.getBoolean("res");
            if (this.mPhotoPath != null && this.mPhotoPath.equals(path) && res2) {
                this.mPhotoId = id;
                this.mPhotoUrl = imageUrl;
                this.mPhotoThumbnailUrl = imageThumbnailUrl;
                if (this.mIsSending) {
                    this.mSelectedVenue.addImage(imageUrl, imageThumbnailUrl, "");
                    this.mSelectedVenue.addNewImageId(this.mPhotoId);
                    reallySendVenue();
                }
            }
            return true;
        }
    }

    public void photoTaken(Uri uri, String path) {
        this.mPhotoPath = path;
        this.mPhotoId = null;
        this.mPhotoThumbnailUrl = null;
        this.mPhotoUrl = null;
        this.mNatMgr.venueAddImage(this.mPhotoPath, 1);
        if (this.mSearchVenueResults != null) {
            goToVenueSelectFragment();
            return;
        }
        this.mNatMgr.OpenProgressPopup(this.mNatMgr.getLanguageString(290));
        postDelayed(new C24173(), (long) this.mNatMgr.getVenueGetTimeout());
    }

    private void goToVenueSelectFragment() {
        this.mState = 2;
        OmniSelectionFragment nameSelect = new OmniSelectionFragment();
        nameSelect.setTitle(this.mNatMgr.getLanguageString(DisplayStrings.DS_SELECT_PLACE));
        nameSelect.setEditBoxHint(this.mNatMgr.getLanguageString(DisplayStrings.DS_ENTER_PLACE_NAME));
        int numNamedVenues = 0;
        int i = 0;
        while (i < this.mSearchVenueResults.length) {
            VenueData vd = this.mSearchVenueResults[i];
            if (!(vd.name == null || vd.name.isEmpty())) {
                numNamedVenues++;
            }
            if (!(this.mDestinationVenueId == null || !vd.id.contentEquals(this.mDestinationVenueId) || i == 0)) {
                Parcelable first = this.mSearchVenueResults[0];
                this.mSearchVenueResults[0] = this.mSearchVenueResults[i];
                this.mSearchVenueResults[i] = first;
            }
            i++;
        }
        SettingsValue[] argValues = new SettingsValue[numNamedVenues];
        nameSelect.setTopOption(this.mNatMgr.getLanguageString(DisplayStrings.DS_PLACE_IS_RESIDENCE), C1283R.drawable.list_icon_home);
        i = 0;
        for (Parcelable p : this.mSearchVenueResults) {
            vd = (VenueData) p;
            if (!(vd.name == null || vd.name.isEmpty())) {
                argValues[i] = new SettingsValue(vd.name, vd.name, false);
                argValues[i].display2 = vd.getAddressString();
                argValues[i].aliases = vd.aliases;
                if (i == 0 && this.mDestinationVenueId != null && vd.id.contentEquals(this.mDestinationVenueId)) {
                    argValues[i].isSelected = true;
                }
                i++;
            }
        }
        nameSelect.setValues(argValues);
        nameSelect.setSearch(true);
        nameSelect.setFilter(true);
        nameSelect.setUserInput(true, this.mNatMgr.getLanguageString(DisplayStrings.DS_ADD_PS));
        nameSelect.setMaxUserInputLength(this.mNatMgr.getVenueMaxNameLengthNTV());
        getFragmentManager().beginTransaction().replace(C1283R.id.container, nameSelect).commit();
    }

    public void omniSelected(int idx, String value, String title, boolean bUserInput) {
        boolean z = false;
        if (this.mState == 2) {
            String str;
            if (bUserInput || idx == -1) {
                this.mIsPublic = bUserInput;
                this.mIsUpdate = false;
                this.mVenue.numCategories = 0;
                if (bUserInput) {
                    this.mVenue.name = value;
                    this.mVenue.bResidence = false;
                } else {
                    this.mVenue.name = "";
                    this.mVenue.bResidence = true;
                }
                if (this.mAddressStrings != null && this.mAddressStrings.numResults > 0) {
                    if (this.mVenue.street == null || this.mVenue.street.isEmpty()) {
                        this.mVenue.street = this.mAddressStrings.street[0];
                    }
                    if (this.mVenue.city == null || this.mVenue.city.isEmpty()) {
                        this.mVenue.city = this.mAddressStrings.city[0];
                    }
                }
                this.mSelectedVenue = this.mVenue;
                EditTextUtils.closeKeyboard(this, findViewById(C1283R.id.container));
                if (bUserInput) {
                    moveToNewPlaceFragment(false);
                } else {
                    if (ConfigManager.getInstance().checkConfigDisplayCounter(0, true) > 0) {
                        z = true;
                    }
                    this.mbConfirmResidential = z;
                    if (this.mbConfirmResidential) {
                        confirmResidential();
                    } else {
                        moveToNewPlaceFragment(true);
                    }
                }
            } else {
                this.mIsPublic = true;
                this.mIsUpdate = true;
                this.mSelectedVenue = (VenueData) this.mSearchVenueResults[idx];
                this.mOrigVenue = this.mSelectedVenue.clone();
                sendVenue();
            }
            String str2 = AnalyticsEvents.ANALYTICS_EVENT_PLACES_CHOOSE_DONE_CLICKED;
            String str3 = "WAS_ADDED|VENUE_ID";
            StringBuilder stringBuilder = new StringBuilder();
            if (bUserInput) {
                str = AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE;
            } else {
                str = AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE;
            }
            Analytics.log(str2, str3, stringBuilder.append(str).append("|").append(this.mSelectedVenue.id).toString());
        } else if (this.mState == 6) {
            this.mSelectedVenue.addCategory(value);
            this.mSelectedVenue.numCategories = EditPlaceCategoriesHolder.sortCategoryIdsArray(this.mSelectedVenue.categories);
            this.mState = 5;
            getFragmentManager().popBackStack();
            ((AddPlaceNewFragment) getFragmentManager().findFragmentByTag("AddPlaceNewFragment")).refreshCategories();
        } else if (this.mState == 4) {
            try {
                JSONObject jsonAddr = new JSONObject(value);
                if (jsonAddr.has("CITY")) {
                    this.mSelectedVenue.city = jsonAddr.getString("CITY");
                } else {
                    this.mSelectedVenue.city = "";
                }
                if (jsonAddr.has("STREET")) {
                    this.mSelectedVenue.street = jsonAddr.getString("STREET");
                } else {
                    this.mSelectedVenue.street = "";
                }
            } catch (JSONException e) {
            }
            this.mState = 5;
            getFragmentManager().popBackStack();
            ((AddPlaceNewFragment) getFragmentManager().findFragmentByTag("AddPlaceNewFragment")).refreshAddress();
        }
    }

    private void confirmResidential() {
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.mNatMgr.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), this.mNatMgr.getLanguageString(DisplayStrings.DS_RESIDENTIAL_PLACE_CONFIRM_DIALOG_BODY), true, new C24184(), this.mNatMgr.getLanguageString(DisplayStrings.DS_RESIDENTIAL_PLACE_CONFIRM_DIALOG_YES), this.mNatMgr.getLanguageString(344), -1);
    }

    public void goToEditMap() {
        this.mState = 3;
        EditMapLocationFragment fragment = new EditMapLocationFragment();
        fragment.setLonLat(this.mVenue.longitude, this.mVenue.latitude);
        fragment.setTitle(DisplayStrings.DS_LOCATION);
        fragment.setAvoiderPin(this.mSelectedVenue.bResidence);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void doneEditMap(int longitude, int latitude) {
        this.mSelectedVenue.longitude = longitude;
        this.mSelectedVenue.latitude = latitude;
        this.mSelectedVenue.wasLocationChanged = true;
        this.mState = 5;
        getFragmentManager().popBackStack();
        ((AddPlaceNewFragment) getFragmentManager().findFragmentByTag("AddPlaceNewFragment")).refreshMapLocation(this.mSelectedVenue);
    }

    private void moveToNewPlaceFragment(boolean residential) {
        this.mState = 5;
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_NEW_PLACE_SCREEN_SHOWN, null, null);
        if (residential) {
            this.mSelectedVenue.wasLocationChanged = false;
        }
        AddPlaceNewFragment fragment = new AddPlaceNewFragment();
        fragment.setVenue(this.mSelectedVenue);
        fragment.setResidential(residential);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment, "AddPlaceNewFragment").commit();
    }

    public void pickCategory() {
        this.mState = 6;
        OmniSelectionFragment categorySelect = new OmniSelectionFragment();
        categorySelect.setTitle(this.mNatMgr.getLanguageString(DisplayStrings.DS_ADD_A_CATEGORY));
        categorySelect.setValues(EditPlaceCategoriesHolder.getCategoryValues());
        categorySelect.setExpandable(true);
        categorySelect.setAutoComplete(true);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, categorySelect).addToBackStack(null).commit();
    }

    private void sayThankYou(int res, int points) {
        int thanks1DS;
        int thanks2DS;
        int moreDS;
        int laterDS;
        boolean z = true;
        if (points < 0) {
            if (this.mPoints > 0) {
                points = this.mPoints;
            } else {
                points = EditPlacePointsHolder.getPoints(PointsType.Images);
            }
        }
        this.mPoints = points;
        this.mSayThankYou = true;
        if (!this.mIsPublic) {
            thanks1DS = DisplayStrings.DS_NULL;
            thanks2DS = DisplayStrings.DS_THANK_YOU_BODY_RESIDENTIAL;
            moreDS = DisplayStrings.DS_NULL;
            laterDS = DisplayStrings.DS_OKAY;
        } else if (this.mIsUpdate) {
            thanks1DS = DisplayStrings.DS_NULL;
            thanks2DS = DisplayStrings.DS_THANK_YOU_BODY_EXISTING;
            moreDS = DisplayStrings.DS_EDIT_DETAILS;
            laterDS = DisplayStrings.DS_NO_THANKS;
        } else {
            thanks1DS = DisplayStrings.DS_THANK_YOU_TITLE_NEW;
            thanks2DS = DisplayStrings.DS_THANK_YOU_BODY_NEW;
            moreDS = DisplayStrings.DS_ADD_MORE_DETAILS;
            laterDS = DisplayStrings.DS_NO_THANKS;
        }
        boolean z2 = this.mIsPublic;
        View.OnClickListener c24195 = new C24195();
        View.OnClickListener c24206 = new C24206();
        if (res != 1) {
            z = false;
        }
        this.mTyd = new ThankYouDialog(this, points, z2, c24195, c24206, thanks1DS, thanks2DS, moreDS, laterDS, z);
        this.mTyd.show();
    }

    void sendVenue() {
        if (!this.mIsUpdate) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_NEW_PLACE_SCREEN_DONE_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, this.mSelectedVenue.id);
        }
        this.mIsSending = true;
        this.mNatMgr.OpenProgressPopup(this.mNatMgr.getLanguageString(290));
        if (this.mPhotoId != null) {
            this.mSelectedVenue.addImage(this.mPhotoUrl, this.mPhotoThumbnailUrl, "");
            this.mSelectedVenue.addNewImageId(this.mPhotoId);
            reallySendVenue();
        }
    }

    void reallySendVenue() {
        String from = this.mFromReportMenu ? "REPORT" : "NEARING";
        if (this.mIsUpdate) {
            this.mNatMgr.venueUpdate(this.mSelectedVenue, this.mOrigVenue, from, this.mQuestionId);
        } else {
            this.mNatMgr.venueCreate(this.mSelectedVenue, from, this.mQuestionId, this.mForceHouseNumber);
        }
    }

    void sendVenue(VenueData venue, int points) {
        this.mSelectedVenue = venue;
        this.mPoints = points;
        sendVenue();
    }

    public void allDone() {
        finish();
    }

    public void wantMore() {
        this.mSelectedVenue.wasLocationChanged = false;
        this.mSelectedVenue.removeNewImageId(0);
        Intent intent = new Intent(this, EditPlaceFlowActivity.class);
        intent.putExtra(VenueData.class.getName(), this.mSelectedVenue);
        intent.putExtra("continue_edit", true);
        startActivity(intent);
        this.mRetain = true;
        finish();
    }

    public void goToPickCityStreet() {
        JSONObject jsonAddr;
        this.mState = 4;
        OmniSelectionFragment addressSelect = new OmniSelectionFragment();
        addressSelect.setTitle(this.mNatMgr.getLanguageString(DisplayStrings.DS_ADDRESS));
        HashSet<String> citySet = new HashSet(this.mAddressStrings.numResults);
        HashSet<String> addressSet = new HashSet(this.mAddressStrings.numResults);
        ArrayList<SettingsValue> valuesList = new ArrayList(this.mAddressStrings.numResults);
        for (int i = 0; i < this.mAddressStrings.numResults; i++) {
            JSONObject jsonAddr2 = null;
            try {
                if (!this.mAddressStrings.city[i].isEmpty()) {
                    citySet.add(this.mAddressStrings.city[i]);
                    if (!this.mAddressStrings.street[i].isEmpty()) {
                        jsonAddr = new JSONObject();
                        try {
                            jsonAddr.put("STREET", this.mAddressStrings.street[i]);
                            jsonAddr.put("CITY", this.mAddressStrings.city[i]);
                            jsonAddr2 = jsonAddr;
                        } catch (JSONException e) {
                            jsonAddr2 = jsonAddr;
                        }
                    }
                } else if (!this.mAddressStrings.street[i].isEmpty()) {
                    jsonAddr = new JSONObject();
                    jsonAddr.put("STREET", this.mAddressStrings.street[i]);
                    jsonAddr2 = jsonAddr;
                }
                if (jsonAddr2 != null) {
                    String addressString = jsonAddr2.toString();
                    if (addressSet.contains(addressString)) {
                        continue;
                    } else {
                        addressSet.add(addressString);
                        valuesList.add(new SettingsValue(addressString, this.mAddressStrings.street[i], this.mAddressStrings.city[i], false));
                    }
                }
            } catch (JSONException e2) {
            }
            if (addressSet.size() >= this.mAddressStrings.numToFilterTo) {
                break;
            }
        }
        if (this.mIsPublic) {
            Iterator it = citySet.iterator();
            while (it.hasNext()) {
                String city = (String) it.next();
                jsonAddr2 = new JSONObject();
                try {
                    jsonAddr2.put("CITY", city);
                    valuesList.add(new SettingsValue(jsonAddr2.toString(), city, false));
                } catch (JSONException e3) {
                }
            }
        }
        addressSelect.setValues((SettingsValue[]) valuesList.toArray(new SettingsValue[valuesList.size()]));
        getFragmentManager().beginTransaction().replace(C1283R.id.container, addressSelect).addToBackStack(null).commit();
    }

    public void isSearching(int times) {
        if (this.mState == 2 && times == 1) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_CHOOSE_SEARCH_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, this.mDestinationVenueId);
        }
    }
}
