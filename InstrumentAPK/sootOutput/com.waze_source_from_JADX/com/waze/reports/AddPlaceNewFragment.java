package com.waze.reports;

import android.app.Fragment;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LocationFactory;
import com.waze.NativeLocation;
import com.waze.NativeManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.PointsView;
import com.waze.map.CanvasFont;
import com.waze.map.MapView;
import com.waze.navigate.NavigateNativeManager;
import com.waze.reports.EditPlacePointsHolder.PointsType;
import com.waze.settings.SettingsTitleText;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.text.WazeTextView;
import com.waze.view.title.TitleBar;
import java.util.ArrayList;
import java.util.Iterator;

public class AddPlaceNewFragment extends Fragment {
    private LinearLayout mCategoriesPlaceHolder;
    private PointsView mCategoryPtsView;
    private TextView mCityStreetMain;
    private TextView mCityStreetSub;
    private EditText mETNumber;
    private int mEarnedPoints = 0;
    private TextValidator mHasContentValidator = new C24211();
    boolean mIsResidential = false;
    private ImageView mMapImage;
    private MapView mMapView;
    private View mMustAdd;
    private final RunnableExecutor mNativeCanvasReadyEvent = new C24232();
    private NavigateNativeManager mNavNtvMgr;
    private NativeManager mNm;
    private TextPointsWatcherImplementation mNumberWatcher;
    private Runnable mReplaceMapRunnable;
    private ArrayList<PointsView> mValidatedPointsViews = new ArrayList(4);
    VenueData mVenue;
    private boolean mbAddressOptionsViewSet = false;
    private View f90r;

    public interface TextValidator {
        boolean isTextValid(String str);
    }

    class C24211 implements TextValidator {
        C24211() {
        }

        public boolean isTextValid(String s) {
            return !s.isEmpty();
        }
    }

    class C24232 extends RunnableExecutor {

        class C24221 implements Runnable {
            C24221() {
            }

            public void run() {
                AddPlaceNewFragment.this.mNavNtvMgr.SetPreviewPoiPosition(AddPlaceNewFragment.this.mVenue.longitude, AddPlaceNewFragment.this.mVenue.latitude, null, false);
                AddPlaceNewFragment.this.mNavNtvMgr.PreviewCanvasFocusOn(AddPlaceNewFragment.this.mVenue.longitude, AddPlaceNewFragment.this.mVenue.latitude, DisplayStrings.DS_NO_NETWORK_CONNECTION__UNABLE_TO_REPORT);
            }
        }

        C24232() {
        }

        public void event() {
            if (AddPlaceNewFragment.this.mVenue.longitude == 0 || AddPlaceNewFragment.this.mVenue.latitude == 0) {
                NativeLocation nLoc = LocationFactory.getNativeLocation(LocationFactory.getInstance().getLastLocation());
                AddPlaceNewFragment.this.mVenue.longitude = nLoc.mLongtitude;
                AddPlaceNewFragment.this.mVenue.latitude = nLoc.mLatitude;
            }
            NativeManager.Post(new C24221());
        }
    }

    class C24243 implements OnClickListener {
        C24243() {
        }

        public void onClick(View v) {
            AddPlaceNewFragment.this.checkIfUSerIsGood();
        }
    }

    class C24254 implements TextValidator {
        C24254() {
        }

        public boolean isTextValid(String s) {
            return ((AddPlaceNewFragment.this.mVenue.street == null || AddPlaceNewFragment.this.mVenue.street.isEmpty()) && (AddPlaceNewFragment.this.mIsResidential || AddPlaceNewFragment.this.mVenue.city == null || AddPlaceNewFragment.this.mVenue.city.isEmpty())) ? false : true;
        }
    }

    class C24265 implements OnClickListener {
        C24265() {
        }

        public void onClick(View v) {
            ((AddPlaceFlowActivity) AddPlaceNewFragment.this.getActivity()).goToPickCityStreet();
        }
    }

    class C24276 implements OnClickListener {
        C24276() {
        }

        public void onClick(View v) {
            AddPlaceNewFragment.this.mMapView.removeCallbacks(AddPlaceNewFragment.this.mReplaceMapRunnable);
            EditTextUtils.closeKeyboard(AddPlaceNewFragment.this.getActivity(), AddPlaceNewFragment.this.f90r);
            ((AddPlaceFlowActivity) AddPlaceNewFragment.this.getActivity()).goToEditMap();
        }
    }

    class C24287 implements Runnable {
        C24287() {
        }

        public void run() {
        }
    }

    class C24298 implements OnClickListener {
        C24298() {
        }

        public void onClick(View v) {
            EditTextUtils.closeKeyboard(AddPlaceNewFragment.this.getActivity(), AddPlaceNewFragment.this.f90r);
            ((AddPlaceFlowActivity) AddPlaceNewFragment.this.getActivity()).pickCategory();
        }
    }

    private final class TextPointsWatcherImplementation implements TextWatcher {
        private String _orig;
        private final int _pts;
        private final PointsView _ptsView;
        private TextValidator _validator;

        private TextPointsWatcherImplementation(PointsView ptsView, int pts, boolean isOn, TextValidator v, String originalText) {
            this._validator = null;
            this._ptsView = ptsView;
            this._pts = pts;
            this._orig = originalText;
            if (this._orig == null) {
                this._orig = "";
            }
            this._ptsView.setPoints(pts);
            if (isOn) {
                AddPlaceNewFragment.this.mEarnedPoints = AddPlaceNewFragment.this.mEarnedPoints + pts;
            }
            this._validator = v;
            if (v == null) {
                this._ptsView.setValid(true);
            } else {
                this._ptsView.setValid(v.isTextValid(this._orig));
                AddPlaceNewFragment.this.mValidatedPointsViews.add(ptsView);
            }
            this._ptsView.setIsOn(isOn, isOn, false);
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void afterTextChanged(Editable s) {
            boolean hasContent;
            boolean isOn;
            if (this._ptsView.isOn()) {
                AddPlaceNewFragment.this.mEarnedPoints = AddPlaceNewFragment.this.mEarnedPoints - this._pts;
            }
            boolean isValid = true;
            if (this._validator != null) {
                isValid = this._validator.isTextValid(s.toString());
                this._ptsView.setValid(isValid);
            }
            boolean wasEdited;
            if (this._orig.contentEquals(s)) {
                wasEdited = false;
            } else {
                wasEdited = true;
            }
            if (s.length() > 0) {
                hasContent = true;
            } else {
                hasContent = false;
            }
            if (hasContent && wasEdited && isValid) {
                isOn = true;
            } else {
                isOn = false;
            }
            this._ptsView.setIsOn(isOn, hasContent, true);
            if (isOn) {
                AddPlaceNewFragment.this.mEarnedPoints = AddPlaceNewFragment.this.mEarnedPoints + this._pts;
            }
        }
    }

    public void setVenue(VenueData venue) {
        this.mVenue = venue;
    }

    public void setResidential(boolean on) {
        this.mIsResidential = on;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        getActivity().getWindow().setSoftInputMode(3);
        this.mNm = NativeManager.getInstance();
        this.mNavNtvMgr = NavigateNativeManager.instance();
        this.f90r = inflater.inflate(C1283R.layout.add_place_new, container, false);
        this.mValidatedPointsViews.clear();
        setUpFragment();
        this.mETNumber.setText(this.mVenue.houseNumber == null ? "" : this.mVenue.houseNumber);
        ((WazeTextView) this.f90r.findViewById(C1283R.id.addPlaceNewPlaceName)).setText(this.mVenue.name);
        return this.f90r;
    }

    public void onDestroy() {
        super.onDestroy();
    }

    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(AddPlaceNewFragment.class.getName() + ".mVenue", this.mVenue);
        outState.putBoolean(AddPlaceNewFragment.class.getName() + ".mIsResidential", this.mIsResidential);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            this.mVenue = (VenueData) savedInstanceState.getParcelable(AddPlaceNewFragment.class.getName() + ".mVenue");
            this.mIsResidential = savedInstanceState.getBoolean(AddPlaceNewFragment.class.getName() + ".mIsResidential");
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (this.mMapView != null) {
            this.mMapView.onPause();
        }
        setUpFragment();
        if (this.mMapView != null) {
            this.mMapView.onResume();
        }
    }

    public void onPause() {
        if (this.mMapView != null) {
            this.mMapView.removeCallbacks(this.mReplaceMapRunnable);
            this.mMapView.onPause();
        }
        super.onPause();
    }

    public void onResume() {
        int i = 0;
        if (this.mMapView != null) {
            this.mMapView.setVisibility(0);
            this.mMapView.onResume();
            this.mMapView.removeCallbacks(this.mReplaceMapRunnable);
            this.mMapView.postDelayed(this.mReplaceMapRunnable, 2000);
            View view = this.mMustAdd;
            if (this.mVenue.wasLocationChanged) {
                i = 8;
            }
            view.setVisibility(i);
        }
        updateVenueFields();
        super.onResume();
    }

    private void setUpFragment() {
        TitleBar titleBar = (TitleBar) this.f90r.findViewById(C1283R.id.theTitleBar);
        if (this.mIsResidential) {
            titleBar.init(getActivity(), (int) DisplayStrings.DS_RESIDENTIAL_PLACE);
        } else {
            titleBar.init(getActivity(), (int) DisplayStrings.DS_NEW_PLACE);
        }
        ((WazeTextView) this.f90r.findViewById(C1283R.id.addPlaceNewDoneText)).setText(this.mNm.getLanguageString(375));
        ((WazeTextView) this.f90r.findViewById(C1283R.id.addPlaceNewSubText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_TELL_US_MORE_ABOUT_THIS_PLACE));
        this.f90r.findViewById(C1283R.id.addPlaceNewDoneButton).setOnClickListener(new C24243());
        ((SettingsTitleText) this.f90r.findViewById(C1283R.id.addPlaceNewCategoriesTitle)).setText(this.mNm.getLanguageString(350));
        ((SettingsTitleText) this.f90r.findViewById(C1283R.id.addPlaceNewDetailsTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_ADDRESS));
        int pts = EditPlacePointsHolder.getPoints(PointsType.City);
        PointsView cityPts = (PointsView) this.f90r.findViewById(C1283R.id.addPlaceNewDetailsCityStreetPoints);
        this.mCityStreetMain = (WazeTextView) this.f90r.findViewById(C1283R.id.addPlaceNewDetailsCityStreetMain);
        this.mCityStreetSub = (WazeTextView) this.f90r.findViewById(C1283R.id.addPlaceNewDetailsCityStreetSub);
        this.mCityStreetMain.addTextChangedListener(new TextPointsWatcherImplementation(cityPts, pts, false, new C24254(), ""));
        this.mCityStreetMain.setHint(this.mNm.getLanguageString(DisplayStrings.DS_STREET_NAME));
        this.mCityStreetSub.setHint(this.mNm.getLanguageString(DisplayStrings.DS_CITY));
        this.f90r.findViewById(C1283R.id.addPlaceNewDetailsCityStreetLayout).setOnClickListener(new C24265());
        pts = EditPlacePointsHolder.getPoints(PointsType.HouseNumber);
        PointsView numberPts = (PointsView) this.f90r.findViewById(C1283R.id.addPlaceNewDetailsNumberPoints);
        this.mETNumber = (EditText) this.f90r.findViewById(C1283R.id.addPlaceNewDetailsNumber);
        this.mNumberWatcher = new TextPointsWatcherImplementation(numberPts, pts, false, this.mIsResidential ? this.mHasContentValidator : null, "");
        this.mETNumber.addTextChangedListener(this.mNumberWatcher);
        this.mETNumber.setHint(this.mNm.getLanguageString(DisplayStrings.DS_HOUSE_NUMBER));
        if (this.mIsResidential) {
            this.f90r.findViewById(C1283R.id.addPlaceNewPlaceName).setVisibility(8);
            this.f90r.findViewById(C1283R.id.addPlaceNewSubText).setVisibility(8);
            this.f90r.findViewById(C1283R.id.addPlaceNewCategoriesPlaceholder).setVisibility(8);
            this.f90r.findViewById(C1283R.id.addPlaceNewCategoriesTitle).setVisibility(8);
            ((SettingsTitleText) this.f90r.findViewById(C1283R.id.addPlaceNewLocationTitle)).setText(this.mNm.getLanguageString(DisplayStrings.DS_LOCATION));
            this.mMustAdd = this.f90r.findViewById(C1283R.id.addPlaceNewAddressMapMustSetFrame);
            this.mMustAdd.setVisibility(this.mVenue.wasLocationChanged ? 8 : 0);
            ((TextView) this.f90r.findViewById(C1283R.id.addPlaceNewAddressMapMustSetText)).setText(this.mNm.getLanguageString(DisplayStrings.DS_RESIDENCE_MUST_SET_LOCATION));
            this.mMapView = (MapView) this.f90r.findViewById(C1283R.id.addPlaceNewAddressMap);
            this.mMapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEvent);
            this.mMapImage = (ImageView) this.f90r.findViewById(C1283R.id.addPlaceNewAddressMapImage);
            this.f90r.findViewById(C1283R.id.addPlaceNewAddressMapFrame).setOnClickListener(new C24276());
            this.mMapView.setHandleTouch(false);
            this.mReplaceMapRunnable = new C24287();
            return;
        }
        this.f90r.findViewById(C1283R.id.addPlaceNewLocationTitle).setVisibility(8);
        this.f90r.findViewById(C1283R.id.addPlaceNewAddressMapFrame).setVisibility(8);
    }

    void updateVenueFields() {
        if (this.mVenue.street == null || this.mVenue.street.isEmpty()) {
            this.mCityStreetMain.setText(this.mVenue.city == null ? "" : this.mVenue.city);
            this.mCityStreetSub.setVisibility(8);
        } else if (this.mVenue.city == null || this.mVenue.city.isEmpty()) {
            this.mCityStreetMain.setText(this.mVenue.street == null ? "" : this.mVenue.street);
            this.mCityStreetSub.setVisibility(8);
        } else {
            this.mCityStreetMain.setText(this.mVenue.street);
            this.mCityStreetSub.setText(this.mVenue.city);
            this.mCityStreetSub.setVisibility(0);
        }
        PointsView numberPts = (PointsView) this.f90r.findViewById(C1283R.id.addPlaceNewDetailsNumberPoints);
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
        if (!this.mIsResidential) {
            this.mCategoriesPlaceHolder = (LinearLayout) this.f90r.findViewById(C1283R.id.addPlaceNewCategoriesPlaceholder);
            refreshCategories();
        }
    }

    private void checkIfUSerIsGood() {
        boolean bUserGood = true;
        Iterator it = this.mValidatedPointsViews.iterator();
        while (it.hasNext()) {
            PointsView pv = (PointsView) it.next();
            if (!pv.isValid()) {
                AnimationUtils.flashView(pv);
                bUserGood = false;
            }
        }
        if (this.mIsResidential && !this.mVenue.wasLocationChanged) {
            AnimationUtils.flashView(this.f90r.findViewById(C1283R.id.addPlaceNewAddressMapMustSetText));
            bUserGood = false;
        }
        if (bUserGood) {
            this.mVenue.houseNumber = this.mETNumber.getText().toString();
            this.mEarnedPoints += this.mVenue.numNewImages * EditPlacePointsHolder.getPoints(PointsType.Images);
            if (!this.mIsResidential) {
                this.mEarnedPoints += EditPlacePointsHolder.getPoints(PointsType.Categories);
            }
            this.mEarnedPoints += EditPlacePointsHolder.getPoints(PointsType.CreatePlace);
            EditTextUtils.closeKeyboard(getActivity(), this.f90r);
            ((AddPlaceFlowActivity) getActivity()).sendVenue(this.mVenue, this.mEarnedPoints);
        }
    }

    void refreshCategories() {
        if (this.mCategoriesPlaceHolder != null) {
            this.mCategoriesPlaceHolder.removeAllViews();
            boolean hasParking = false;
            if (this.mVenue != null) {
                for (int i = 0; i < this.mVenue.numCategories; i++) {
                    View line = addCategoryLine(this.mCategoriesPlaceHolder, this.mVenue.categories[i], EditPlaceCategoriesHolder.getCategoryById(this.mVenue.categories[i]), null, true);
                    if (this.mVenue.categories[i].equals(VenueData.PARKING_LOT_CATEGORY)) {
                        hasParking = true;
                    }
                    if (hasParking) {
                        line.setBackgroundResource(C1283R.drawable.item_selector_bottom);
                    } else if (i == 0) {
                        line.setBackgroundResource(C1283R.drawable.item_selector_top);
                    } else {
                        line.setBackgroundResource(C1283R.drawable.item_selector_middle);
                    }
                    line.setPadding(0, 0, 0, 0);
                }
            }
            if (hasParking) {
                this.f90r.findViewById(C1283R.id.addPlaceCategoryCommentLayout).setVisibility(0);
                ((TextView) this.f90r.findViewById(C1283R.id.addPlaceCategoryComment)).setText(DisplayStrings.displayString(DisplayStrings.DS_PLACE_PARKING_CATEGORY_FOOTER));
                return;
            }
            View add = addCategoryLine(this.mCategoriesPlaceHolder, null, DisplayStrings.displayString(DisplayStrings.DS_TAP_TO_ADD_CATEGORIES), null, false);
            add.setBackgroundResource(this.mVenue.numCategories == 0 ? C1283R.drawable.item_selector_single : C1283R.drawable.item_selector_bottom);
            add.setPadding(0, 0, 0, 0);
            add.setOnClickListener(new C24298());
            this.f90r.findViewById(C1283R.id.addPlaceCategoryCommentLayout).setVisibility(8);
        }
    }

    void refreshMapLocation(VenueData venue) {
        this.mVenue = venue;
        if (this.mMapView != null) {
            this.mMapView.setVisibility(0);
            this.mMapView.onResume();
            this.mMapView.removeCallbacks(this.mReplaceMapRunnable);
            this.mMapView.postDelayed(this.mReplaceMapRunnable, 2000);
        }
    }

    View addCategoryLine(LinearLayout categoriesPlace, final String id, String top, String bottom, boolean bClearable) {
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
                    AddPlaceNewFragment.this.mVenue.removeCategory(id);
                    AddPlaceNewFragment.this.refreshCategories();
                }
            });
        } else {
            boolean isValid;
            newLine.findViewById(C1283R.id.twoLineClearableClear).setVisibility(8);
            if (this.mCategoryPtsView != null) {
                this.mValidatedPointsViews.remove(this.mCategoryPtsView);
            }
            this.mCategoryPtsView = (PointsView) newLine.findViewById(C1283R.id.twoLineClearablePoints);
            this.mCategoryPtsView.setVisibility(0);
            if (this.mVenue.numCategories > 0) {
                isValid = true;
            } else {
                isValid = false;
            }
            this.mCategoryPtsView.setValid(isValid);
            this.mValidatedPointsViews.add(this.mCategoryPtsView);
            this.mCategoryPtsView.setPoints(EditPlacePointsHolder.getPoints(PointsType.Categories));
            this.mCategoryPtsView.setIsOn(isValid, isValid, false);
        }
        categoriesPlace.addView(newLine);
        newLine.getLayoutParams().height = getResources().getDimensionPixelSize(C1283R.dimen.settingsItemHeight);
        return newLine;
    }

    public void refreshAddress() {
    }
}
