package com.waze.navigate.social;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.DecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.LayoutParams;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.ViewFlipper;
import com.facebook.login.widget.ToolTipPopup;
import com.tokenautocomplete.TokenCompleteTextView.TokenListener;
import com.tokenautocomplete.TokenCompleteTextView.ViewListener;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.NativeManager.PeopleAppData;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.ContactsCompletionView;
import com.waze.autocomplete.ContactsCompletionView.IGetViewForObject;
import com.waze.autocomplete.ContactsCompletionView.IOnBackPressed;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.LayoutTooltip;
import com.waze.ifs.ui.WazeCheckBoxView;
import com.waze.main.navigate.LocationData;
import com.waze.map.CanvasFont;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.IFriendsChanged;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.PublicMacros;
import com.waze.navigate.social.AddFromActivity.IOnReadDone;
import com.waze.navigate.social.AddFromActivity.PersonArrayAdapter;
import com.waze.navigate.social.AddFromActivity.PersonFilteredArrayAdapter;
import com.waze.phone.AddressBookImpl;
import com.waze.phone.PhoneRequestAccessDialog;
import com.waze.phone.PhoneRequestAccessDialog.PhoneRequestAccessResultListener;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.social.AddFriendDialog;
import com.waze.social.AddFriendDialog.IAddFriendDialog;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.utils.EditTextUtils;
import com.waze.view.title.TitleBar;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShareDriveActivity extends ActivityBase implements TokenListener, OnItemClickListener, IFriendsChanged {
    private static final String TAG = ShareDriveActivity.class.getName();
    public static final int TOOLTIP_TIME_MILLIS = 6000;
    private static boolean USE_PEOPLE_APP_DATA = true;
    public static boolean bIsFollow = false;
    private static boolean mAskToAddFriend = true;
    private AddFriendDialog mAddFriendDialog;
    private AddressItem mAddress = null;
    private PersonBase mAskToAdd;
    private PeopleAppData mAskToAddData = null;
    private ContactsCompletionView mCompletionView;
    private View mCompletionViewContainer;
    private SparseArray<PersonBase> mContactsByUid;
    private SparseArray<FriendUserData> mCurFriendsUids;
    private float mDensity;
    private TextView mDoneBtn;
    private boolean mDoneButtonActive = false;
    private boolean mDontScroll = false;
    private DriveToNativeManager mDriveTo;
    private ListView mFriendsListView;
    private boolean mIsPortrait;
    private View mListHeaderMapDetailsView;
    private View mListHeaderSeachBoxView;
    private View mListHeaderView;
    private int mLocContHeight;
    private LocationData mLocation = null;
    private TextView mMoreBtn;
    private NativeManager mNatMgr;
    private int mNumSuggestions;
    private ArrayList<PersonBase> mPersonArray;
    private PersonArrayAdapter mPersonArrayAdapter;
    private PersonFilteredArrayAdapter mPersonFilteredArrayAdapter;
    private SparseArray<PersonBase> mPreSelected = new SparseArray();
    private HorizontalScrollView mSearchScroll;
    private View mShareDetailsView;
    int mShareType = 0;
    private SparseArray<PersonBase> mSuggested = new SparseArray();
    private TitleBar mTitleBar;
    int mType = 0;
    String mUrl = null;
    String mUrlText = null;
    ArrayList<PersonBase> people;

    class C22091 implements OnClickListener {
        C22091() {
        }

        public void onClick(View v) {
            ShareDriveActivity.this.SendMeeting();
        }
    }

    class C22102 implements OnEditorActionListener {
        C22102() {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if (!ShareDriveActivity.this.mCompletionView.enoughToFilter() || ShareDriveActivity.this.mPersonFilteredArrayAdapter == null || ShareDriveActivity.this.mPersonFilteredArrayAdapter.getCount() <= 0) {
                ((InputMethodManager) ShareDriveActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(ShareDriveActivity.this.mCompletionView.getWindowToken(), 0);
            } else {
                if (!ShareDriveActivity.this.mCompletionView.getObjects().contains(ShareDriveActivity.this.mPersonFilteredArrayAdapter.getItem(0))) {
                    ShareDriveActivity.this.mCompletionView.performCompletion();
                    ShareDriveActivity.this.mPersonArrayAdapter.notifyDataSetChanged();
                }
            }
            return true;
        }
    }

    class C22113 implements IOnBackPressed {
        C22113() {
        }

        public void onBackPressed() {
            ShareDriveActivity.this.closeKeyboard();
        }
    }

    class C22124 implements IGetViewForObject {
        C22124() {
        }

        public View getViewForObject(Object object) {
            PersonBase p = (PersonBase) object;
            View view = AddFriendsUtils.inflateFriendToken(p, (ViewGroup) ShareDriveActivity.this.mCompletionView.getParent());
            view.setTag(p);
            return view;
        }
    }

    class C22165 implements ViewListener {
        LayoutTooltip mCurTooltip;
        View mCurTooltipView;
        Runnable mTooltipRunnable;

        class C22142 implements OnTouchListener {
            C22142() {
            }

            public boolean onTouch(View v, MotionEvent event) {
                C22165.this.dismiss(true);
                return false;
            }
        }

        class C22153 implements Runnable {
            C22153() {
            }

            public void run() {
                C22165.this.dismiss(true);
            }
        }

        C22165() {
        }

        public void onViewSelected(View v, float drawX, float drawY) {
            dismiss(false);
            final PersonBase p = (PersonBase) v.getTag();
            this.mCurTooltip = new LayoutTooltip(ShareDriveActivity.this, C1283R.layout.contacts_token_tip, C1283R.id.contactTokenTipLayout);
            ((TextView) this.mCurTooltip.getView().findViewById(C1283R.id.contactTokenTipName)).setText(ShareUtility.getShortened(p.getName()));
            this.mCurTooltip.getView().findViewById(C1283R.id.contactTokenTipClose).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    C22165.this.dismiss(true);
                    ShareDriveActivity.this.removeFromSelection(p);
                }
            });
            this.mCurTooltip.getBackground().setOnTouchListener(new C22142());
            this.mCurTooltip.setDelta((int) (((drawX - ((float) (ShareDriveActivity.this.mCompletionView.getWidth() / 2))) - ((float) ShareDriveActivity.this.mCompletionView.getScrollX())) + ((float) (v.getWidth() / 2))), (int) drawY);
            this.mCurTooltip.setAnimations(C1283R.anim.contact_tooltip_show, C1283R.anim.contact_tooltip_hide);
            this.mCurTooltip.show(ShareDriveActivity.this.mCompletionView);
            this.mCurTooltipView = v;
            this.mTooltipRunnable = new C22153();
            this.mCurTooltipView.postDelayed(this.mTooltipRunnable, ToolTipPopup.DEFAULT_POPUP_DISPLAY_TIME);
        }

        private void dismiss(boolean animate) {
            if (this.mTooltipRunnable != null) {
                this.mCurTooltipView.removeCallbacks(this.mTooltipRunnable);
                this.mTooltipRunnable = null;
            }
            if (this.mCurTooltip != null) {
                this.mCurTooltip.dismiss(animate);
                this.mCurTooltip = null;
            }
            if (this.mCurTooltipView != null) {
                this.mCurTooltipView = null;
            }
            ShareDriveActivity.this.mCompletionView.clearSelections();
        }

        public void onViewUnselected(View v) {
            if (this.mCurTooltipView == v) {
                dismiss(true);
            }
        }
    }

    class C22176 implements OnClickListener {
        C22176() {
        }

        public void onClick(View v) {
            if (ShareDriveActivity.this.mFriendsListView.getFirstVisiblePosition() == 0) {
                ShareDriveActivity.this.mFriendsListView.smoothScrollToPositionFromTop(1, (int) (64.0f * ShareDriveActivity.this.mDensity));
            }
            ShareDriveActivity.this.openKeyboard();
        }
    }

    class C22187 implements OnClickListener {
        C22187() {
        }

        public void onClick(View v) {
            ShareDriveActivity.this.mCompletionView.performClick();
        }
    }

    class C22198 implements OnClickListener {
        C22198() {
        }

        public void onClick(View v) {
            ShareDriveActivity.this.mCompletionView.performClick();
            ShareDriveActivity.this.mCompletionView.requestFocus();
            ShareDriveActivity.this.mCompletionView.setSelection(ShareDriveActivity.this.mCompletionView.getText().length());
            ShareDriveActivity.this.mSearchScroll.smoothScrollTo(ShareDriveActivity.this.mCompletionView.getWidth(), 0);
            ShareDriveActivity.this.openKeyboard();
        }
    }

    class C22209 implements OnScrollListener {
        C22209() {
        }

        public void onScrollStateChanged(AbsListView view, int scrollState) {
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            ShareDriveActivity.this.onScrollChanged();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AddressBookImpl.getInstance().performSync(false, null);
        this.mDensity = getResources().getDisplayMetrics().density;
        this.mIsPortrait = getResources().getConfiguration().orientation == 1;
        this.mDriveTo = DriveToNativeManager.getInstance();
        this.mNatMgr = NativeManager.getInstance();
        requestWindowFeature(1);
        setContentView(C1283R.layout.share_drive);
        if (getResources().getConfiguration().orientation == 2) {
            findViewById(C1283R.id.ShareDriveLocationContainer).getLayoutParams().width = getResources().getDisplayMetrics().widthPixels - getResources().getDisplayMetrics().heightPixels;
        }
        bIsFollow = this.mNatMgr.isFollowActiveNTV();
        Serializable friendsData = getIntent().getExtras().getSerializable(PublicMacros.FriendUserDataList);
        if (friendsData instanceof ArrayList) {
            Iterator it = ((ArrayList) friendsData).iterator();
            while (it.hasNext()) {
                PersonBase o = it.next();
                if (o instanceof PersonBase) {
                    PersonBase p = o;
                    this.mSuggested.put(p.getID(), p);
                }
            }
        }
        if (getIntent().getExtras().getBoolean("selected", false)) {
            this.mPreSelected = this.mSuggested;
            this.mSuggested = new SparseArray(0);
        }
        this.mUrlText = getIntent().getStringExtra("link");
        this.mUrl = getIntent().getStringExtra("url");
        this.mType = getIntent().getExtras().getInt("type");
        this.mShareType = getIntent().getExtras().getInt("share_type");
        this.mLocation = (LocationData) getIntent().getExtras().get(PublicMacros.LOCATION_DATA);
        this.mAddress = (AddressItem) getIntent().getExtras().get(PublicMacros.ADDRESS_ITEM);
        this.mShareDetailsView = findViewById(C1283R.id.ShareDriveLocationContainer);
        this.mDoneBtn = (TextView) findViewById(C1283R.id.ShareDriveDoneButton);
        this.mDoneBtn.setText(this.mNatMgr.getLanguageString(293));
        this.mDoneBtn.setOnClickListener(new C22091());
        this.mDoneBtn.setClickable(false);
        AlphaAnimation animation = new AlphaAnimation(1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
        animation.setDuration(0);
        animation.setFillAfter(true);
        this.mDoneBtn.startAnimation(animation);
        ((TextView) findViewById(C1283R.id.ShareDriveSearchLable)).setText(this.mNatMgr.getLanguageString(DisplayStrings.DS_SELECT_CONTACTS_TO));
        this.mCompletionViewContainer = findViewById(C1283R.id.ShareDriveTextBoxLayout_ref);
        this.mSearchScroll = (HorizontalScrollView) findViewById(C1283R.id.ShareDriveSearchScrollView);
        this.mCompletionView = (ContactsCompletionView) findViewById(C1283R.id.ShareDriveSearchView);
        this.mCompletionView.setHint(this.mNatMgr.getLanguageString(DisplayStrings.DS_SEARCH_FRIENDS));
        if (VERSION.SDK_INT <= 19) {
            this.mCompletionView.setDropDownBackgroundDrawable(new ColorDrawable(Color.parseColor("#cbd6da")));
        }
        this.mCompletionView.allowDuplicates(false);
        this.mCompletionView.setDropDownAnchor(C1283R.id.ShareDriveTextBoxLayout_ref);
        this.mCompletionView.setOnEditorActionListener(new C22102());
        this.mCompletionView.setIOnBackPressed(new C22113());
        this.mCompletionView.setIGetViewForObject(new C22124());
        this.mCompletionView.setViewListener(new C22165());
        this.mCompletionView.setOnClickListener(new C22176());
        this.mCompletionView.requestFocus();
        this.mCompletionViewContainer.setOnClickListener(new C22187());
        findViewById(C1283R.id.ShareDriveSearchAddButton).setOnClickListener(new C22198());
        this.mFriendsListView = (ListView) findViewById(C1283R.id.ShareDriveListView);
        this.mListHeaderView = findViewById(C1283R.id.ShareDriveListHeaderLayout);
        this.mListHeaderMapDetailsView = this.mListHeaderView.findViewById(C1283R.id.ShareDriveMapTransparent);
        this.mListHeaderSeachBoxView = this.mListHeaderView.findViewById(C1283R.id.ShareDriveSearchPlaceholder);
        if (this.mIsPortrait) {
            ((RelativeLayout) this.mListHeaderView.getParent()).removeView(this.mListHeaderView);
            this.mListHeaderView.setLayoutParams(new LayoutParams(-2, -2));
            this.mFriendsListView.addHeaderView(this.mListHeaderView);
        }
        this.mFriendsListView.setOnScrollListener(new C22209());
        if (this.mNatMgr.IsAccessToContactsEnableNTV()) {
            loadPersonArray();
        } else {
            new PhoneRequestAccessDialog(this, new PhoneRequestAccessResultListener() {
                public void onResult(boolean okToAccess) {
                    if (okToAccess) {
                        ShareDriveActivity.this.loadPersonArray();
                        return;
                    }
                    ShareDriveActivity.this.setResult(0);
                    ShareDriveActivity.this.finish();
                }
            }).show();
        }
        OnClickListener shareButtonOnClick = new OnClickListener() {
            public void onClick(View v) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_MORE_OPTIONS, "TYPE", ShareDriveActivity.this.mType == 0 ? "DRIVE" : "LOCATION");
                String sURL = MyWazeNativeManager.getInstance().GetLastShareURL();
                if (ShareDriveActivity.this.mType == 0 && ShareDriveActivity.this.mNatMgr.isFollowActiveNTV() && sURL != null && !sURL.equals("")) {
                    ShareUtility.BuildShareStrings(ShareType.ShareType_ShareDrive, sURL, null);
                } else if (ShareDriveActivity.this.mType == 0) {
                    ShareDriveActivity.this.mNatMgr.CreateMeeting(ShareDriveActivity.this.mLocation.locationName, "ShareDrive", ShareDriveActivity.this.mLocation.locationX, ShareDriveActivity.this.mLocation.locationY, ShareDriveActivity.this.mLocation.mStreet, ShareDriveActivity.this.mLocation.mCity, null, ShareDriveActivity.this.mLocation.mVenueId);
                } else if (ShareDriveActivity.this.mShareType == 2) {
                    ShareUtility.BuildShareStrings(ShareType.ShareType_ShareLocation, ShareDriveActivity.this.mUrl, null);
                } else {
                    ShareUtility.BuildShareStrings(ShareType.ShareType_ShareSelection, ShareDriveActivity.this.mUrl, ShareDriveActivity.this.mAddress);
                }
            }
        };
        this.mMoreBtn = (TextView) findViewById(C1283R.id.ShareDriveMoreButton);
        this.mMoreBtn.setText(this.mNatMgr.getLanguageString(DisplayStrings.DS_MORE));
        this.mMoreBtn.setOnClickListener(shareButtonOnClick);
        this.mTitleBar = (TitleBar) findViewById(C1283R.id.shareTitle);
        if (this.mType == 0) {
            this.mTitleBar.init((Activity) this, this.mNatMgr.getLanguageString(DisplayStrings.DS_SEND_LOCATION_TITLE_ETA), false);
        } else {
            this.mTitleBar.init((Activity) this, this.mNatMgr.getLanguageString(DisplayStrings.DS_SEND_LOCATION_TITLE), false);
        }
        String placeStr = "";
        if (this.mAddress != null) {
            placeStr = this.mAddress.getTitle();
            if (this.mType == 0) {
                placeStr = this.mAddress.getType() == 1 ? DisplayStrings.displayString(DisplayStrings.DS_TO_HOME) : this.mAddress.getType() == 3 ? DisplayStrings.displayString(DisplayStrings.DS_TO_WORK) : DisplayStrings.displayStringF(DisplayStrings.DS_TO_LOCATION_PS, placeStr);
            } else if (this.mType == 1) {
                if (this.mAddress.getType() == 1) {
                    placeStr = DisplayStrings.displayString(287);
                } else if (this.mAddress.getType() == 3) {
                    placeStr = DisplayStrings.displayString(288);
                }
            }
            if (placeStr == null || placeStr.equals("")) {
                placeStr = this.mAddress.getAddress();
            }
        } else if (this.mLocation.locationName != null && this.mLocation.locationName.length() > 0) {
            placeStr = NativeManager.getInstance().getLanguageString(this.mLocation.locationName);
            if (this.mType == 0) {
                placeStr = (placeStr.equalsIgnoreCase(DisplayStrings.displayString(287)) || placeStr.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_HOME))) ? DisplayStrings.displayString(DisplayStrings.DS_TO_HOME) : (placeStr.equalsIgnoreCase(DisplayStrings.displayString(288)) || placeStr.equalsIgnoreCase(DisplayStrings.displayString(DisplayStrings.DS_TO_WORK))) ? DisplayStrings.displayString(DisplayStrings.DS_TO_WORK) : DisplayStrings.displayStringF(DisplayStrings.DS_TO_LOCATION_PS, placeStr);
            }
        }
        placeStr = NativeManager.getInstance().getLanguageString(placeStr);
        TextView shareDriveString = (TextView) findViewById(C1283R.id.ShareDriveLocationText);
        if (this.mType == 0) {
            shareDriveString.setText(String.format(this.mNatMgr.getLanguageString(DisplayStrings.DS_SEND_DRIVE_DISPLAY_TEXT_PS_PS), new Object[]{placeStr, this.mLocation.locationEta}));
        } else {
            shareDriveString.setText(String.format(this.mNatMgr.getLanguageString(DisplayStrings.DS_SEND_LOCATION_DISPLAY_TEXT_PS), new Object[]{placeStr}));
        }
        ImageView illu = (ImageView) findViewById(C1283R.id.ShareDriveLocationImage);
        int addrType = -1;
        if (this.mAddress != null) {
            addrType = this.mAddress.getType();
        }
        if (this.mShareType == 1) {
            illu.setImageResource(C1283R.drawable.share_destination_icon);
        } else if (this.mShareType == 2) {
            illu.setImageResource(C1283R.drawable.share_current_icon);
        } else if (this.mType == 0) {
            illu.setImageResource(C1283R.drawable.share_eta_icon);
        } else if (addrType == 1) {
            illu.setImageResource(C1283R.drawable.share_home_icon);
        } else if (addrType == 3) {
            illu.setImageResource(C1283R.drawable.share_work_icon);
        } else if (addrType == 5) {
            illu.setImageResource(C1283R.drawable.share_favorite_icon);
        } else if (addrType == 8) {
            illu.setImageResource(C1283R.drawable.share_history_icon);
        } else if (addrType == 11) {
            illu.setImageResource(C1283R.drawable.share_calendar_icon);
        } else {
            illu.setImageResource(C1283R.drawable.share_current_icon);
        }
        this.mFriendsListView.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                ShareDriveActivity.this.mFriendsListView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ShareDriveActivity.this.mLocContHeight = ShareDriveActivity.this.findViewById(C1283R.id.ShareDriveLocationContainer).getHeight();
                ShareDriveActivity.this.mListHeaderMapDetailsView.getLayoutParams().height = ShareDriveActivity.this.mLocContHeight;
                ShareDriveActivity.this.onScrollChanged();
            }
        });
        this.mDriveTo.getFriendsListData(new FriendsListListener() {
            public void onComplete(FriendsListData aData) {
                ShareDriveActivity.this.mCurFriendsUids = new SparseArray(aData.friends.length);
                if (aData != null && aData.friends.length > 0) {
                    for (FriendUserData fud : aData.friends) {
                        ShareDriveActivity.this.mCurFriendsUids.put(fud.getID(), fud);
                    }
                }
            }
        });
    }

    private void loadPersonArray() {
        this.mPersonArray = new ArrayList();
        AddFromActivity.getFriendsDataFromShare(this.mPersonArray, this.mPreSelected, this.mSuggested, new IOnReadDone() {
            public void onReadDone(int numSuggested, ArrayList<PersonBase> setSelected) {
                ShareDriveActivity.this.mNumSuggestions = numSuggested;
                ShareDriveActivity.this.friendsListPopulateFriends();
                Iterator it = setSelected.iterator();
                while (it.hasNext()) {
                    ShareDriveActivity.this.mCompletionView.addObject((PersonBase) it.next());
                }
            }
        });
    }

    private void onScrollChanged() {
        View v = this.mFriendsListView.getChildAt(0);
        View shadow = findViewById(C1283R.id.ShareDriveSearchShadow);
        int top = v == null ? 0 : v.getTop();
        if (this.mIsPortrait) {
            if (this.mFriendsListView.getFirstVisiblePosition() == 0) {
                int realTop = this.mListHeaderSeachBoxView.getTop() + top;
                this.mCompletionViewContainer.setTranslationY((float) Math.max(0, realTop));
                if (realTop > 0) {
                    shadow.setAlpha(0.0f);
                    return;
                } else if (realTop < -50) {
                    shadow.setAlpha(1.0f);
                    return;
                } else {
                    shadow.setAlpha(((float) realTop) / -50.0f);
                    return;
                }
            }
            this.mCompletionViewContainer.setTranslationY(0.0f);
            shadow.setAlpha(1.0f);
        } else if (top < -50 || this.mFriendsListView.getFirstVisiblePosition() > 0) {
            shadow.setAlpha(1.0f);
        } else {
            shadow.setAlpha(((float) top) / -50.0f);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TAG + ".mAskToAdd", this.mAskToAdd);
    }

    protected void onPause() {
        super.onPause();
        if (this.mType == 0) {
            this.mDriveTo.unsetMeeting();
        }
    }

    protected void onResume() {
        super.onResume();
        postDelayed(new Runnable() {
            public void run() {
                ShareDriveActivity.this.setDoneButtonClickableOrNot();
            }
        }, 100);
    }

    boolean canAskToAdd() {
        this.mAskToAdd = null;
        Iterator it = this.mCompletionView.getObjects().iterator();
        while (it.hasNext()) {
            FriendUserData fud;
            PersonBase p = (PersonBase) it.next();
            boolean isMyFriend = false;
            if (p instanceof FriendUserData) {
                fud = (FriendUserData) p;
            } else {
                fud = (FriendUserData) this.mCurFriendsUids.get(p.getID());
            }
            if (fud != null) {
                if ((fud.mIsFriend || fud.mIsPendingFriend) && !fud.mIsPendingMy) {
                    isMyFriend = true;
                } else {
                    isMyFriend = false;
                }
            }
            if (!isMyFriend) {
                if (USE_PEOPLE_APP_DATA) {
                    this.mAskToAddData = this.mNatMgr.getPeopleAppDataNTV(p.getID(), p.getIsOnWaze());
                }
                if (this.mAskToAddData == null || this.mAskToAddData.friendship_suggest_count == 0) {
                    this.mAskToAdd = p;
                    return true;
                }
                this.mAskToAddData = null;
            }
        }
        return false;
    }

    void SendMeeting() {
        List<Object> selected = this.mCompletionView.getObjects();
        if (selected == null) {
            setResult(-1);
            finish();
            return;
        }
        int[] addUids = new int[selected.size()];
        String[] invitePhones = new String[selected.size()];
        int[] inviteIds = new int[selected.size()];
        int numAdd = 0;
        int numInvite = 0;
        String[] wazesPhones = new String[selected.size()];
        Iterator it = selected.iterator();
        while (it.hasNext()) {
            PersonBase p = (PersonBase) it.next();
            if (p.getIsOnWaze()) {
                wazesPhones[numAdd] = p.getPhone();
                addUids[numAdd] = p.getID();
                numAdd++;
            } else {
                invitePhones[numInvite] = p.getPhone();
                inviteIds[numInvite] = p.getID();
                numInvite++;
            }
        }
        if (numAdd > 0 || numInvite > 0) {
            String Type;
            if (this.mType == 0) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_DRIVE_SENT, null, null);
                Type = "ShareDrive";
            } else {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_LOCATION_SENT, null, null);
                Type = "ShareLocation";
            }
            String sURL = MyWazeNativeManager.getInstance().GetLastShareURL();
            NativeManager nm = NativeManager.getInstance();
            if (this.mType == 0 && nm.isFollowActiveNTV() && sURL != null) {
                if (!sURL.equals("")) {
                    if (numAdd > 0) {
                        nm.AddToMeeting(addUids, numAdd, wazesPhones, false);
                    }
                    if (numInvite > 0) {
                        nm.InviteToMeeting(invitePhones, inviteIds, numInvite, 4);
                    }
                }
            }
            nm.CreateMeetingBulk(this.mLocation.locationName, Type, this.mLocation.locationX, this.mLocation.locationY, addUids, invitePhones, inviteIds, numAdd, numInvite, true, wazesPhones, this.mLocation.mStreet, this.mLocation.mCity, null, true, this.mLocation.mVenueId);
        }
        setResult(-1);
        finish();
    }

    private void askToAddAFriend(final PersonBase askToAdd) {
        if (USE_PEOPLE_APP_DATA) {
            if (this.mAskToAddData == null) {
                this.mAskToAddData = new PeopleAppData();
                this.mAskToAddData.friendship_suggest_count = 1;
            } else {
                PeopleAppData peopleAppData = this.mAskToAddData;
                peopleAppData.friendship_suggest_count++;
            }
            this.mNatMgr.setPeopleAppDataNTV(askToAdd.getID(), askToAdd.getIsOnWaze(), this.mAskToAddData);
        }
        this.mAddFriendDialog = new AddFriendDialog(this, askToAdd, new IAddFriendDialog() {
            public void onSendRequestClicked() {
                if (askToAdd.getIsOnWaze()) {
                    int[] uid = new int[]{ShareDriveActivity.this.mAskToAdd.getID()};
                    if (uid[0] != 0) {
                        MyWazeNativeManager.getInstance().sendSocialAddFriends(uid, 1, String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PS_ADDED), new Object[]{askToAdd.getName()}));
                    }
                } else {
                    String[] phones = new String[1];
                    int[] ids = new int[]{askToAdd.getPhone()};
                    ids[0] = askToAdd.getID();
                    MyWazeNativeManager.getInstance().sendSocialInviteFriends(ids, phones, 1, String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PS_INVITED), new Object[]{askToAdd.getName()}));
                }
                ShareDriveActivity.this.mAddFriendDialog.dismiss();
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ASK_FRIENDSHIP_FROM_SHARE, "ACTION", "SEND");
            }

            public void onNotNowClicked() {
                ShareDriveActivity.mAskToAddFriend = false;
                ShareDriveActivity.this.mAddFriendDialog.dismiss();
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ASK_FRIENDSHIP_FROM_SHARE, "ACTION", "NOT_NOW");
            }
        });
        this.mAddFriendDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                ShareDriveActivity.this.SendMeeting();
            }
        });
        this.mAddFriendDialog.show();
    }

    public void onBackPressed() {
        if (!bIsFollow) {
            NativeManager.getInstance().StopFollow();
        }
        super.onBackPressed();
    }

    private void setDoneButtonClickableOrNot() {
        boolean isEmpty = true;
        List<Object> selected = this.mCompletionView.getObjects();
        if (selected != null) {
            isEmpty = selected.size() == 0;
        }
        NativeManager nm = NativeManager.getInstance();
        this.mCompletionView.postDelayed(new Runnable() {
            public void run() {
                if (ShareDriveActivity.this.mSearchScroll != null && ShareDriveActivity.this.mCompletionView != null) {
                    ShareDriveActivity.this.mSearchScroll.smoothScrollTo(ShareDriveActivity.this.mCompletionView.getWidth(), 0);
                }
            }
        }, 10);
        if (this.mDoneButtonActive != (!isEmpty)) {
            this.mDoneButtonActive = !isEmpty;
            this.mDoneBtn.setClickable(!isEmpty);
            ViewFlipper flip = (ViewFlipper) findViewById(C1283R.id.ShareDriveSearchButtonFlipper);
            AlphaAnimation animation;
            if (isEmpty) {
                this.mDoneBtn.setText(nm.getLanguageString(293));
                animation = new AlphaAnimation(1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
                animation.setDuration(300);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.setFillAfter(true);
                this.mDoneBtn.startAnimation(animation);
                flip.showPrevious();
                return;
            }
            setDoneText(selected, nm);
            animation = new AlphaAnimation(CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1.0f);
            animation.setDuration(300);
            animation.setInterpolator(new DecelerateInterpolator());
            animation.setFillAfter(true);
            this.mDoneBtn.startAnimation(animation);
            flip.showNext();
        } else if (this.mDoneButtonActive) {
            setDoneText(selected, nm);
        }
    }

    private void setDoneText(List<Object> selected, NativeManager nm) {
        String buttonText;
        if (selected.size() == 1) {
            PersonBase p = (PersonBase) selected.get(0);
            buttonText = String.format(nm.getLanguageString(DisplayStrings.DS_SEND_LOCATION_SEND_TO_PS), new Object[]{ShareUtility.getFirstName(p.getName())});
        } else {
            buttonText = String.format(nm.getLanguageString(DisplayStrings.DS_SEND_LOCATION_SEND_TO_PD), new Object[]{Integer.valueOf(numSelected)});
        }
        this.mDoneBtn.setText(buttonText);
    }

    public void SendURL(String title, String body) {
        bIsFollow = true;
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/plain");
        i.putExtra("android.intent.extra.SUBJECT", title);
        i.putExtra("android.intent.extra.TEXT", body);
        i.putExtra("exit_on_sent", true);
        startActivityForResult(Intent.createChooser(i, DisplayStrings.displayString(DisplayStrings.DS_SHARE)), 4000);
    }

    private void friendsListPopulateFriends() {
        if (this.mPersonArray != null) {
            this.mPersonArrayAdapter = new PersonArrayAdapter(this, this.mPersonArray, this.mNumSuggestions, true);
            this.mPersonArrayAdapter.setShowOnline(false);
            this.mFriendsListView.setAdapter(this.mPersonArrayAdapter);
            this.mFriendsListView.setOnItemClickListener(this);
            this.mPersonFilteredArrayAdapter = new PersonFilteredArrayAdapter(this, this.mPersonArray);
            this.mCompletionView.setAdapter(this.mPersonFilteredArrayAdapter);
            this.mCompletionView.setTokenListener(this);
            for (PersonBase p : this.mCompletionView.getObjects()) {
                this.mPersonArrayAdapter.setSelected(p.getID());
            }
        }
    }

    public void onTokenAdded(Object token) {
        this.mPersonArrayAdapter.setSelected(((PersonBase) token).getID());
        View v = this.mFriendsListView.findViewWithTag(token);
        if (v != null) {
            WazeCheckBoxView cb = (WazeCheckBoxView) v.findViewById(C1283R.id.addFriendsCheckbox);
            if (!cb.isChecked()) {
                cb.setValue(true, false);
            }
        }
        setDoneButtonClickableOrNot();
    }

    public void onTokenRemoved(Object token) {
        this.mPersonArrayAdapter.setUnselected(((PersonBase) token).getID());
        View v = this.mFriendsListView.findViewWithTag(token);
        if (v != null) {
            WazeCheckBoxView cb = (WazeCheckBoxView) v.findViewById(C1283R.id.addFriendsCheckbox);
            if (cb.isChecked()) {
                cb.setValue(false);
            }
        }
        setDoneButtonClickableOrNot();
    }

    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        if (!(view instanceof TextView)) {
            WazeCheckBoxView cb = (WazeCheckBoxView) view.findViewById(C1283R.id.addFriendsCheckbox);
            if (cb != null) {
                PersonBase p = (PersonBase) view.getTag();
                if (cb.isChecked()) {
                    cb.setValue(false, true);
                    removeFromSelection(p);
                    return;
                }
                cb.setValue(true, true);
                for (Object o : this.mCompletionView.getObjects()) {
                    if (((PersonBase) o).getID() == p.getID()) {
                        this.mCompletionView.removeObject(o);
                    }
                }
                this.mCompletionView.addObject(p);
            }
        }
    }

    private void removeFromSelection(PersonBase p) {
        this.mCompletionView.removeObject(p);
        for (Object o : this.mCompletionView.getObjects()) {
            if (((PersonBase) o).getID() == p.getID()) {
                this.mCompletionView.removeObject(o);
            }
        }
    }

    public void onFriendsChanged() {
    }

    private void openKeyboard() {
        EditTextUtils.openKeyboard(this, this.mCompletionView);
        this.mTitleBar.setCloseText(this.mNatMgr.getLanguageString(DisplayStrings.DS_OK));
        this.mTitleBar.setOnClickCloseListener(new OnClickListener() {
            public void onClick(View v) {
                ShareDriveActivity.this.closeKeyboard();
            }
        });
    }

    private void closeKeyboard() {
        this.mTitleBar.setCloseText(null);
        this.mTitleBar.setCloseVisibility(false);
        EditTextUtils.closeKeyboard(this, this.mCompletionView);
    }
}
