package com.waze.reports;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.view.View;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.AddressStrings;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.EditTextDialogFragment;
import com.waze.ifs.ui.EditTextDialogFragment.IEditText;
import com.waze.ifs.ui.OmniSelectionFragment;
import com.waze.ifs.ui.OmniSelectionFragment.IOmniSelect;
import com.waze.navigate.DriveToNativeManager;
import com.waze.reports.EditMapLocationFragment.IEditMap;
import com.waze.reports.SimpleChoiceFragment.ISimplyChoose;
import com.waze.reports.SimpleChoiceFragment.SimpleChoice;
import com.waze.reports.TakePhotoFragment.ITakePhoto;
import com.waze.settings.SettingsValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository$ImageRepositoryListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class EditPlaceFlowActivity extends ActivityBase implements ITakePhoto, IOmniSelect, IEditText, ISimplyChoose, IEditMap {
    private static final int STATE_ADD_CATEGORY = 8;
    private static final int STATE_DUPLICATE_FIND = 7;
    private static final int STATE_EDIT_ABOUT = 3;
    private static final int STATE_EDIT_REASON = 4;
    private static final int STATE_MAIN = 1;
    private static final int STATE_MAP_LOCATION = 6;
    private static final int STATE_MOVED_CLOSED = 9;
    private static final int STATE_OPENING_HOURS = 5;
    private static final int STATE_SELECT_ADDRESS = 11;
    private static final int STATE_SERVICES = 10;
    private static final int STATE_TAKE_PHOTO = 2;
    private static final String TAG = EditPlaceFlowActivity.class.getName();
    Fragment curFragment = null;
    private AddressStrings mAddressStrings;
    private DriveToNativeManager mDriveTo;
    private int mEarnedPoints;
    private int mFlagType;
    private boolean mIsContinueEdit = false;
    private boolean mIsSending = false;
    private boolean mIsWaitingForImage = false;
    private NativeManager mNm;
    private VenueData mOrigVenue;
    private String mPhotoPath;
    private boolean mRetain;
    private Runnable mRetryImageLoad = null;
    private int mState;
    private ThankYouDialog mTyd;
    private VenueData mVenue;
    EditPlaceFragment mainFragment;

    class C24511 implements Runnable {
        C24511() {
        }

        public void run() {
            EditPlaceFlowActivity.this.mNm.CloseProgressPopup();
        }
    }

    class C24522 implements Runnable {
        C24522() {
        }

        public void run() {
            EditPlaceFlowActivity.this.mAddressStrings = EditPlaceFlowActivity.this.mNm.getAddressByLocationNTV(EditPlaceFlowActivity.this.mVenue.longitude, EditPlaceFlowActivity.this.mVenue.latitude);
        }
    }

    class C24544 implements OnClickListener {
        C24544() {
        }

        public void onClick(DialogInterface dialog, int which) {
            if (which == 1) {
                EditPlaceFlowActivity.this.mNm.venueFlag(EditPlaceFlowActivity.this.mVenue.id, EditPlaceFlowActivity.this.mFlagType, null, null);
                EditPlaceFlowActivity.this.thanksForReport();
            }
        }
    }

    class C24555 implements View.OnClickListener {
        C24555() {
        }

        public void onClick(View v) {
            EditPlaceFlowActivity.this.setResult(3);
            EditPlaceFlowActivity.this.finish();
        }
    }

    public void goToOpeningHours() {
        this.mState = 5;
        ArrayList<OpeningHours> arrOpeningHours = new ArrayList(this.mVenue.numOpeningHours);
        for (int i = 0; i < this.mVenue.numOpeningHours; i++) {
            arrOpeningHours.add(this.mVenue.openingHours[i]);
        }
        EditOpeningHoursFragment fragment = new EditOpeningHoursFragment();
        fragment.setOpeningHours(arrOpeningHours);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void doneOpeningHours(ArrayList<OpeningHours> arrOpeningHours) {
        this.mVenue.numOpeningHours = arrOpeningHours.size();
        if (this.mVenue.numOpeningHours > VenueData.MAX_ARR_SIZE) {
            this.mVenue.numOpeningHours = VenueData.MAX_ARR_SIZE;
        }
        for (int i = 0; i < this.mVenue.numOpeningHours; i++) {
            this.mVenue.openingHours[i] = (OpeningHours) arrOpeningHours.get(i);
        }
        this.mainFragment.setOpeningHours(this.mVenue);
        this.mState = 1;
        getFragmentManager().popBackStack();
    }

    public void goToServices() {
        this.mState = 10;
        List<String> srvList = new ArrayList(this.mVenue.numServices);
        for (int i = 0; i < this.mVenue.numServices; i++) {
            srvList.add(this.mVenue.services[i]);
        }
        HashSet<String> setServiceIds = new HashSet(srvList);
        EditPlaceServicesFragment fragment = new EditPlaceServicesFragment();
        fragment.setServiceIds(setServiceIds);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void doneServices(List<String> setServices) {
        this.mVenue.numServices = setServices.size();
        if (this.mVenue.numServices > VenueData.MAX_ARR_SIZE) {
            this.mVenue.numServices = VenueData.MAX_ARR_SIZE;
        }
        int i = 0;
        for (String srv : setServices) {
            this.mVenue.services[i] = srv;
            i++;
            if (i == this.mVenue.numServices) {
                break;
            }
        }
        this.mainFragment.setServices(this.mVenue);
        this.mState = 1;
        getFragmentManager().popBackStack();
    }

    public void goToEditMap() {
        this.mState = 6;
        EditMapLocationFragment fragment = new EditMapLocationFragment();
        fragment.setLonLat(this.mVenue.longitude, this.mVenue.latitude);
        fragment.setTitle(DisplayStrings.DS_LOCATION);
        fragment.setAvoiderPin(this.mVenue.bResidence);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void doneEditMap(int longitude, int latitude) {
        if (!(longitude == this.mVenue.longitude && latitude == this.mVenue.latitude)) {
            this.mVenue.longitude = longitude;
            this.mVenue.latitude = latitude;
            this.mVenue.wasLocationChanged = true;
        }
        this.mainFragment.updateMapLocation(this.mVenue);
        this.mState = 1;
        getFragmentManager().popBackStack();
    }

    public void goToTakePhoto(Bundle cameraButtonLocation) {
        this.mState = 2;
        TakePhotoFragment fragment = new TakePhotoFragment();
        fragment.setAnimateButton(cameraButtonLocation);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void photoTaken(Uri uri, String path) {
        this.mPhotoPath = path;
        this.mVenue.addImage(path, "", "");
        this.mIsWaitingForImage = true;
        this.mNm.setUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        this.mNm.venueAddImage(this.mPhotoPath, 1);
        this.mainFragment.photoTaken(this.mVenue);
        this.mState = 1;
        getFragmentManager().popBackStack();
    }

    public void goToAddCategory() {
        this.mState = 8;
        OmniSelectionFragment fragment = new OmniSelectionFragment();
        fragment.setTitle(this.mNm.getLanguageString(350));
        fragment.setValues(EditPlaceCategoriesHolder.getCategoryValues());
        fragment.setExpandable(true);
        fragment.setAutoComplete(true);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void omniSelected(int idx, String value, String title, boolean bUserInput) {
        if (this.mState == 8) {
            this.mVenue.addCategory(value);
            this.mVenue.numCategories = EditPlaceCategoriesHolder.sortCategoryIdsArray(this.mVenue.categories);
            this.mainFragment.updateCategories(this.mVenue);
        } else if (this.mState == 7) {
            this.mNm.venueFlag(this.mVenue.id, this.mFlagType, null, value);
            thanksForReport();
        } else if (this.mState == 11) {
            try {
                JSONObject jsonAddr = new JSONObject(value);
                if (jsonAddr.has("CITY")) {
                    this.mVenue.city = jsonAddr.getString("CITY");
                } else {
                    this.mVenue.city = "";
                }
                if (jsonAddr.has("STREET")) {
                    this.mVenue.street = jsonAddr.getString("STREET");
                } else {
                    this.mVenue.street = "";
                }
                this.mainFragment.updateAddress(this.mVenue);
            } catch (JSONException e) {
            }
        }
        this.mState = 1;
        getFragmentManager().popBackStack();
    }

    public void goToEditAbout(String initial) {
        this.mState = 3;
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        fragment.setTitle(DisplayStrings.DS_EDIT_PLACE);
        fragment.setSubtitle(DisplayStrings.DS_ABOUT2);
        fragment.setHint(DisplayStrings.DS_DESCRIBE_PLACE_HINT);
        fragment.setExplanation(String.format(this.mNm.getLanguageString(DisplayStrings.DS_MAX_PD_CHARACTERS), new Object[]{Integer.valueOf(300)}));
        fragment.setSingleLine(false);
        fragment.setSpeech(false);
        fragment.setMaxLength(300);
        fragment.setMinLines(6);
        fragment.setInitial(this.mVenue.about);
        fragment.setInputType(1);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void textEdited(String value) {
        if (this.mState == 3) {
            this.mVenue.about = value;
            this.mainFragment.updateAbout(this.mVenue);
        } else if (this.mState == 4) {
            this.mNm.venueFlag(this.mVenue.id, this.mFlagType, value, null);
            thanksForReport();
        }
        this.mState = 1;
        getFragmentManager().popBackStack();
    }

    public void choiceMade(SimpleChoice choice, String comment) {
        if (this.mState == 9) {
            this.mNm.venueFlag(this.mVenue.id, choice.value, comment, null);
            thanksForReport();
        }
        this.mState = 1;
        getFragmentManager().popBackStack();
    }

    private void thanksForReport() {
        this.mNm.OpenProgressIconPopup(this.mNm.getLanguageString(DisplayStrings.DS_REPORT_PLACE_THANK_YOU), "flag_verified");
        postDelayed(new C24511(), 4000);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EditPlaceServicesFragment.getServices();
        EditPlaceCategoriesHolder.getCategories();
        EditPlacePointsHolder.getAllPoints();
        EditPlaceValidatorsHolder.getAllValidators();
        if (getIntent().hasExtra(VenueData.class.getName())) {
            this.mVenue = (VenueData) getIntent().getParcelableExtra(VenueData.class.getName());
        } else {
            this.mVenue = new VenueData();
        }
        if (getIntent().hasExtra("continue_edit")) {
            this.mIsContinueEdit = true;
        }
        if (savedInstanceState != null) {
            this.mVenue = (VenueData) savedInstanceState.getParcelable(TAG + ".mVenue");
        }
        getWindow().setSoftInputMode(3);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_PLACE_DETAILS_SCREEN_SHOWN, "CONTINUE|VENUE_ID", (this.mIsContinueEdit ? AnalyticsEvents.ANALYTICS_EVENT_VALUE_TRUE : AnalyticsEvents.ANALYTICS_EVENT_VALUE_FALSE) + "|" + this.mVenue.id);
        this.mDriveTo = DriveToNativeManager.getInstance();
        this.mNm = NativeManager.getInstance();
        NativeManager.Post(new C24522());
        setContentView(C1283R.layout.fragment_activity_empty);
        if (savedInstanceState == null) {
            this.mState = 1;
            this.mainFragment = new EditPlaceFragment();
            this.mainFragment.setVenue(this.mVenue);
            getFragmentManager().beginTransaction().add(C1283R.id.container, this.mainFragment, "EditPlaceFragment").commit();
        } else {
            this.mState = savedInstanceState.getInt(TAG + ".mState");
            this.mFlagType = savedInstanceState.getInt(TAG + ".mFlagType");
            this.mVenue = (VenueData) savedInstanceState.getParcelable(TAG + ".mVenue");
            this.mIsContinueEdit = savedInstanceState.getBoolean(TAG + ".mIsContinueEdit");
            this.mIsSending = savedInstanceState.getBoolean(TAG + ".mIsSending");
            this.mIsWaitingForImage = savedInstanceState.getBoolean(TAG + ".mIsWaitingForImage");
            this.mPhotoPath = savedInstanceState.getString(TAG + ".mPhotoPath");
            this.mainFragment = (EditPlaceFragment) getFragmentManager().findFragmentByTag("EditPlaceFragment");
        }
        this.mRetain = false;
        this.mNm.setUpdateHandler(NativeManager.UH_VENUE_STATUS, this.mHandler);
        if (this.mIsWaitingForImage && this.mPhotoPath != null) {
            this.mNm.setUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TAG + ".mState", this.mState);
        outState.putInt(TAG + ".mFlagType", this.mFlagType);
        outState.putParcelable(TAG + ".mVenue", this.mVenue);
        outState.putBoolean(TAG + ".mIsContinueEdit", this.mIsContinueEdit);
        outState.putBoolean(TAG + ".mIsSending", this.mIsSending);
        outState.putBoolean(TAG + ".mIsWaitingForImage", this.mIsWaitingForImage);
        outState.putString(TAG + ".mPhotoPath", this.mPhotoPath);
        this.mRetain = true;
    }

    protected void onDestroy() {
        if (this.mTyd != null) {
            this.mTyd.dismiss();
        }
        if (!this.mRetain) {
            TakePhotoFragment.cleanUpFiles(null);
        }
        this.mNm.unsetUpdateHandler(NativeManager.UH_VENUE_STATUS, this.mHandler);
        this.mNm.unsetUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
        this.mNm.unsetUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
        super.onDestroy();
    }

    protected void onPause() {
        super.onPause();
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void showReportSubmenu() {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_PLACE_FLAGGING_POPUP_SHOWN, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, this.mVenue.id);
        String dialogTitle = this.mNm.getLanguageString(DisplayStrings.DS_WHATS_WRONG_WITH_THIS_PLACEQ);
        String[] options = new String[]{this.mNm.getLanguageString(DisplayStrings.DS_PLACE_CLOSED_MOVED), this.mNm.getLanguageString(DisplayStrings.DS_PLACE_DUPLICATE), this.mNm.getLanguageString(DisplayStrings.DS_PLACE_INAPPROPRIATE), this.mNm.getLanguageString(DisplayStrings.DS_RESIDENTIAL_PLACE), this.mNm.getLanguageString(DisplayStrings.DS_PLACE_WRONG)};
        final int[] optionValues = new int[]{1, 4, 5, 3, 6};
        this.mFlagType = -1;
        Builder bob = new Builder(this, C1283R.style.CustomPopup);
        bob.setTitle(dialogTitle);
        bob.setItems(options, new OnClickListener() {
            public void onClick(DialogInterface dialog, int item) {
                EditPlaceFlowActivity.this.mFlagType = optionValues[item];
                switch (EditPlaceFlowActivity.this.mFlagType) {
                    case 1:
                        EditPlaceFlowActivity.this.gotoMovedOrClosed();
                        return;
                    case 3:
                        EditPlaceFlowActivity.this.gotoFlagResidential();
                        return;
                    case 4:
                        EditPlaceFlowActivity.this.gotoFindDuplicate();
                        return;
                    case 5:
                        EditPlaceFlowActivity.this.gotoIappropreateOrWrongDetails(true);
                        return;
                    case 6:
                        EditPlaceFlowActivity.this.gotoIappropreateOrWrongDetails(false);
                        return;
                    default:
                        return;
                }
            }
        });
        bob.setIcon(C1283R.drawable.flag_it_popup);
        bob.setCancelable(true);
        AlertDialog alertDialog = bob.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }

    private void gotoIappropreateOrWrongDetails(boolean isInappropriate) {
        this.mState = 4;
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        fragment.setTitle(isInappropriate ? DisplayStrings.DS_PLACE_INAPPROPRIATE : DisplayStrings.DS_PLACE_WRONG);
        fragment.setSubtitle(DisplayStrings.DS_TELL_US_MORE);
        fragment.setHint(isInappropriate ? DisplayStrings.DS_THIS_PLACE_IS_INAPPROPRIATE_BECAUSE___ : DisplayStrings.DS_THE_DETAILS_ARE_WRONG_BECAUSE___);
        fragment.setSingleLine(false);
        fragment.setMinLines(6);
        fragment.setSpeech(false);
        fragment.setInputType(1);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    private void gotoFindDuplicate() {
        this.mNm.OpenProgressPopup(this.mNm.getLanguageString(290));
        this.mNm.setUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
        this.mNm.venueSearch(this.mVenue.longitude, this.mVenue.latitude);
    }

    private void gotoFlagResidential() {
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.mNm.getLanguageString(DisplayStrings.DS_ARE_YOU_SURE_Q), this.mNm.getLanguageString(DisplayStrings.DS_RESIDENTIAL_PLACE_CONFIRM_DIALOG_BODY), true, new C24544(), this.mNm.getLanguageString(DisplayStrings.DS_RESIDENTIAL_PLACE_CONFIRM_DIALOG_YES), this.mNm.getLanguageString(344), -1);
    }

    private void gotoMovedOrClosed() {
        this.mState = 4;
        EditTextDialogFragment fragment = new EditTextDialogFragment();
        fragment.setTitle(DisplayStrings.DS_PLACE_CLOSED_MOVED);
        fragment.setSubtitle(DisplayStrings.DS_WHAT_HAPPENEDQ);
        fragment.setHint(294);
        fragment.setSingleLine(false);
        fragment.setMinLines(6);
        fragment.setSpeech(false);
        fragment.setInputType(1);
        getFragmentManager().beginTransaction().replace(C1283R.id.container, fragment).addToBackStack(null).commit();
    }

    public void sendVenue(VenueData venue, VenueData origVenue, boolean picsAdded, int earnedPoints) {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_PLACES_PLACE_DETAILS_SCREEN_DONE_CLICKED, AnalyticsEvents.ANALYTICS_EVENT_INFO_PLACES_VENUE_ID, this.mVenue.id);
        this.mIsSending = true;
        this.mVenue = venue;
        this.mOrigVenue = origVenue;
        this.mEarnedPoints = earnedPoints;
        if (!this.mIsWaitingForImage) {
            this.mNm.venueUpdate(this.mVenue, this.mOrigVenue, this.mIsContinueEdit ? "CONTINUE" : "PREVIEW", null);
        }
    }

    private void sayThankYou(int res, int points) {
        int thanks1DS;
        int thanks2DS;
        boolean z = true;
        if (points < 0) {
            points = this.mEarnedPoints;
        }
        if (points == 0) {
            thanks1DS = DisplayStrings.DS_THANK_YOU_TITLE_ZERO_POINTS;
            thanks2DS = DisplayStrings.DS_THANK_YOU_BODY_ZERO_POINTS;
        } else {
            thanks1DS = DisplayStrings.DS_THANK_YOU_TITLE_FULL_EDIT;
            thanks2DS = DisplayStrings.DS_THANK_YOU_BODY_FULL_EDIT;
        }
        View.OnClickListener c24555 = new C24555();
        int i = DisplayStrings.DS_NULL;
        if (res != 1) {
            z = false;
        }
        this.mTyd = new ThankYouDialog(this, points, false, c24555, null, thanks1DS, thanks2DS, i, DisplayStrings.DS_OKAY, z);
        this.mTyd.show();
    }

    protected boolean myHandleMessage(Message msg) {
        if (msg.what == NativeManager.UH_SEARCH_VENUES) {
            VenueData[] possibleVenues = (VenueData[]) msg.getData().getParcelableArray("venue_data");
            this.mNm.unsetUpdateHandler(NativeManager.UH_SEARCH_VENUES, this.mHandler);
            this.mNm.CloseProgressPopup();
            if (possibleVenues == null || possibleVenues.length == 0) {
                return true;
            }
            this.mState = 7;
            OmniSelectionFragment nameSelect = new OmniSelectionFragment();
            nameSelect.setTitle(this.mNm.getLanguageString(DisplayStrings.DS_PLACE_DUPLICATE));
            nameSelect.setEditBoxHint(this.mNm.getLanguageString(DisplayStrings.DS_SEARCH_PLACE_TO_MERGE));
            SettingsValue[] argValues = new SettingsValue[possibleVenues.length];
            int i = 0;
            for (VenueData vd : possibleVenues) {
                if (!(this.mVenue.id.equals(vd.id) || vd.name == null || vd.name.isEmpty())) {
                    argValues[i] = new SettingsValue(vd.id, vd.name, false);
                    argValues[i].display2 = vd.getAddressString();
                    i++;
                }
            }
            if (i < argValues.length) {
                argValues = (SettingsValue[]) Arrays.copyOf(argValues, i);
            }
            nameSelect.setValues(argValues);
            nameSelect.setSearch(true);
            nameSelect.setFilter(true);
            getFragmentManager().beginTransaction().replace(C1283R.id.container, nameSelect).addToBackStack(null).commit();
            return true;
        } else if (msg.what == NativeManager.UH_VENUE_STATUS) {
            this.mIsSending = false;
            this.mNm.CloseProgressPopup();
            b = msg.getData();
            int res = b.getInt("res");
            int points = b.getInt("points");
            id = b.getString("id");
            if (res >= 0) {
                sayThankYou(res, points);
            } else {
                MsgBox.openMessageBox(this.mNm.getLanguageString(220), this.mNm.getLanguageString(DisplayStrings.DS_PLACE_ADD_ERROR), true);
            }
            return true;
        } else if (msg.what != NativeManager.UH_VENUE_ADD_IMAGE_RESULT) {
            return super.myHandleMessage(msg);
        } else {
            this.mNm.unsetUpdateHandler(NativeManager.UH_VENUE_ADD_IMAGE_RESULT, this.mHandler);
            b = msg.getData();
            final String path = b.getString("path");
            id = b.getString("id");
            final String imageUrl = b.getString("image_url");
            final String imageThumbnailUrl = b.getString("image_thumbnail_url");
            boolean res2 = b.getBoolean("res");
            final String from = this.mIsContinueEdit ? "CONTINUE" : "PREVIEW";
            if (this.mPhotoPath != null && this.mPhotoPath.equals(path)) {
                this.mIsWaitingForImage = false;
                if (res2) {
                    final ImageRepository$ImageRepositoryListener imageListener = new ImageRepository$ImageRepositoryListener() {

                        class C24561 implements Runnable {
                            C24561() {
                            }

                            public void run() {
                                EditPlaceFlowActivity.this.mainFragment.updatePhotos(EditPlaceFlowActivity.this.mVenue);
                            }
                        }

                        public void onImageRetrieved(Bitmap bitmap) {
                            EditPlaceFlowActivity.this.mVenue.replaceImage(EditPlaceFlowActivity.this.mPhotoPath, imageUrl, imageThumbnailUrl);
                            EditPlaceFlowActivity.this.mVenue.addNewImageId(id);
                            EditPlaceFlowActivity.this.mPhotoPath = null;
                            EditPlaceFlowActivity.this.post(new C24561());
                        }
                    };
                    this.mRetryImageLoad = new Runnable() {
                        public void run() {
                            if (EditPlaceFlowActivity.this.mPhotoPath == null) {
                                EditPlaceFlowActivity.this.mRetryImageLoad = null;
                                if (EditPlaceFlowActivity.this.mIsSending) {
                                    EditPlaceFlowActivity.this.mNm.venueUpdate(EditPlaceFlowActivity.this.mVenue, EditPlaceFlowActivity.this.mOrigVenue, from, null);
                                }
                            } else if (!EditPlaceFlowActivity.this.mPhotoPath.equals(path)) {
                            } else {
                                if (EditPlaceFlowActivity.this.mIsSending) {
                                    EditPlaceFlowActivity.this.mVenue.replaceImage(EditPlaceFlowActivity.this.mPhotoPath, imageUrl, imageThumbnailUrl);
                                    EditPlaceFlowActivity.this.mVenue.addNewImageId(id);
                                    EditPlaceFlowActivity.this.mPhotoPath = null;
                                    EditPlaceFlowActivity.this.mNm.venueUpdate(EditPlaceFlowActivity.this.mVenue, EditPlaceFlowActivity.this.mOrigVenue, from, null);
                                    return;
                                }
                                ImageRepository.instance.getImage(imageThumbnailUrl, true, imageListener);
                                if (EditPlaceFlowActivity.this.mRetryImageLoad != null) {
                                    EditPlaceFlowActivity.this.postDelayed(EditPlaceFlowActivity.this.mRetryImageLoad, 3000);
                                }
                            }
                        }
                    };
                    postDelayed(this.mRetryImageLoad, 3000);
                } else if (this.mIsSending) {
                    this.mNm.venueUpdate(this.mVenue, this.mOrigVenue, from, null);
                }
            }
            return true;
        }
    }

    public void goToPickCityStreet() {
        this.mState = 11;
        OmniSelectionFragment addressSelect = new OmniSelectionFragment();
        addressSelect.setTitle(this.mNm.getLanguageString(DisplayStrings.DS_ADDRESS));
        HashSet<String> citySet = new HashSet(this.mAddressStrings.numResults);
        HashSet<String> addressSet = new HashSet(this.mAddressStrings.numResults);
        ArrayList<SettingsValue> valuesList = new ArrayList(this.mAddressStrings.numResults);
        for (int i = 0; i < this.mAddressStrings.numResults; i++) {
            JSONObject jsonAddr = null;
            try {
                JSONObject jsonAddr2;
                if (!this.mAddressStrings.city[i].isEmpty()) {
                    citySet.add(this.mAddressStrings.city[i]);
                    if (!this.mAddressStrings.street[i].isEmpty()) {
                        jsonAddr2 = new JSONObject();
                        try {
                            jsonAddr2.put("STREET", this.mAddressStrings.street[i]);
                            jsonAddr2.put("CITY", this.mAddressStrings.city[i]);
                            jsonAddr = jsonAddr2;
                        } catch (JSONException e) {
                            jsonAddr = jsonAddr2;
                        }
                    }
                } else if (!this.mAddressStrings.street[i].isEmpty()) {
                    jsonAddr2 = new JSONObject();
                    jsonAddr2.put("STREET", this.mAddressStrings.street[i]);
                    jsonAddr = jsonAddr2;
                }
                if (jsonAddr != null) {
                    String addressString = jsonAddr.toString();
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
        Iterator it = citySet.iterator();
        while (it.hasNext()) {
            String city = (String) it.next();
            jsonAddr = new JSONObject();
            try {
                jsonAddr.put("CITY", city);
                valuesList.add(new SettingsValue(jsonAddr.toString(), city, false));
            } catch (JSONException e3) {
            }
        }
        addressSelect.setValues((SettingsValue[]) valuesList.toArray(new SettingsValue[valuesList.size()]));
        getFragmentManager().beginTransaction().replace(C1283R.id.container, addressSelect).addToBackStack(null).commit();
    }

    public void isSearching(int times) {
    }
}
