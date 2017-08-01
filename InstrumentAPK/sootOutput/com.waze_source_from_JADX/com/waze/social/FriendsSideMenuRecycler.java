package com.waze.social;

import android.content.Context;
import android.os.Build.VERSION;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.AddFriendsListener;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.social.AddFriendsData;
import com.waze.navigate.social.FriendsListData;
import com.waze.social.FriendItemView.FriendItemViewListener;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FriendsSideMenuRecycler extends RecyclerView implements FriendItemViewListener {
    public static final int DELAY_MILLIS = 2000;
    private static final int ITEMS_BEFORE_BACK_TO_TOP = 3;
    private static final int ITEM_TYPE_FRIEND = 1;
    private static final int ITEM_TYPE_SEARCH_BAR = 0;
    private static final int ITEM_TYPE_SECTION_HEADER = 2;
    private static final int ITEM_TYPE_SECTION_SPACE = 3;
    public static final int MAX_TRIES = 5;
    private ViewGroup mBackToTopContainer;
    private int mCurrentScrollOffset;
    private List<FriendUserData> mFilteredFriends;
    private List<FriendUserData> mFriends;
    private boolean mIsRetyingSocialInfo;
    private boolean mIsShowingBackToTop;
    private List<Object> mListDataSource;
    private FriendRecyclerListener mListener;
    private NativeManager mNm;
    private FriendListSortCriteria mOnMyWayCriteria;
    private FriendListSortCriteria mPendingApprovalCriteria;
    private FriendListSortCriteria mSharedEtaCriteria;
    private int mTimesFailed;
    private int mTotalFriendFetchCalled;

    private abstract class FriendListSortCriteria {
        int _size;

        abstract String criteriaTitle();

        abstract boolean doesAnswerCriteria(FriendUserData friendUserData);

        private FriendListSortCriteria() {
            this._size = 0;
        }

        void setSize(int size) {
            this._size = size;
        }
    }

    class C29042 extends FriendListSortCriteria {
        C29042() {
            super();
        }

        public boolean doesAnswerCriteria(FriendUserData friendUserData) {
            return friendUserData.mIsPendingMy;
        }

        public String criteriaTitle() {
            if (this._size <= 0) {
                return FriendsSideMenuRecycler.this.mNm.getLanguageString(242);
            }
            return String.format(FriendsSideMenuRecycler.this.mNm.getLanguageString(DisplayStrings.DS_PD_FRIENDS_ADDED_YOU), new Object[]{Integer.valueOf(this._size)});
        }
    }

    class C29053 extends FriendListSortCriteria {
        C29053() {
            super();
        }

        public boolean doesAnswerCriteria(FriendUserData friendUserData) {
            return (TextUtils.isEmpty(friendUserData.mMeetingIdSharedWithMe) && TextUtils.isEmpty(friendUserData.mMeetingIdSharedByMe)) ? false : true;
        }

        public String criteriaTitle() {
            if (this._size <= 0) {
                return FriendsSideMenuRecycler.this.mNm.getLanguageString(391);
            }
            return String.format(FriendsSideMenuRecycler.this.mNm.getLanguageString(DisplayStrings.DS_NOW_SHARING_PD), new Object[]{Integer.valueOf(this._size)});
        }
    }

    class C29064 extends FriendListSortCriteria {
        C29064() {
            super();
        }

        public boolean doesAnswerCriteria(FriendUserData friendUserData) {
            return !TextUtils.isEmpty(friendUserData.arrivedShareText);
        }

        public String criteriaTitle() {
            if (this._size <= 0) {
                return FriendsSideMenuRecycler.this.mNm.getLanguageString(DisplayStrings.DS_FRIENDS_ON_THE_WAY);
            }
            return String.format(FriendsSideMenuRecycler.this.mNm.getLanguageString(DisplayStrings.DS_FRIENDS_LIST_FRIENDS_ON_THE_WAY_PD), new Object[]{Integer.valueOf(this._size)});
        }
    }

    class C29075 implements OnClickListener {
        C29075() {
        }

        public void onClick(View v) {
            FriendsSideMenuRecycler.this.smoothScrollToPosition(0);
        }
    }

    class C29086 implements Runnable {
        C29086() {
        }

        public void run() {
            FriendsSideMenuRecycler.this.mIsRetyingSocialInfo = false;
            FriendsSideMenuRecycler.this.reloadData();
        }
    }

    class C29097 implements AddFriendsListener {
        C29097() {
        }

        public void onComplete(AddFriendsData data) {
            if (data != null) {
                FriendsSideMenuRecycler.this.appendFriends(data.WaitingForApprovalFriends);
            }
        }
    }

    class C29108 implements FriendsListListener {
        C29108() {
        }

        public void onComplete(FriendsListData data) {
            if (data != null) {
                FriendsSideMenuRecycler.this.appendFriends(data.friends);
            }
        }
    }

    private class FriendAdapter extends Adapter<ViewHolder> {
        private FriendAdapter() {
        }

        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                FriendItemView friendView = new FriendItemView(FriendsSideMenuRecycler.this.getContext());
                if (VERSION.SDK_INT >= 21) {
                    friendView.setElevation((float) PixelMeasure.dp(8));
                    friendView.setOutlineProvider(ViewOutlineProvider.BOUNDS);
                }
                return new FriendViewHolder(friendView);
            } else if (viewType == 2) {
                View sectionView = LayoutInflater.from(FriendsSideMenuRecycler.this.getContext()).inflate(C1283R.layout.friend_section_view, null);
                if (VERSION.SDK_INT >= 21) {
                    sectionView.setElevation((float) PixelMeasure.dp(8));
                    sectionView.setOutlineProvider(ViewOutlineProvider.BOUNDS);
                }
                sectionView.setLayoutParams(new LayoutParams(-1, PixelMeasure.dp(40)));
                return new HeaderItemViewHolder(sectionView);
            } else if (viewType != 3) {
                return null;
            } else {
                View spaceView = new View(FriendsSideMenuRecycler.this.getContext());
                spaceView.setLayoutParams(new LayoutParams(-1, PixelMeasure.dp(40)));
                return new StaticItemViewHolder(spaceView);
            }
        }

        public void onBindViewHolder(ViewHolder holder, int position) {
            int rawPosition = position;
            Object item = FriendsSideMenuRecycler.this.mListDataSource.get(position);
            if (item instanceof FriendUserData) {
                ((FriendViewHolder) holder).bindView((FriendUserData) item, getItemViewType(rawPosition + 1) != 3);
            } else if (item instanceof String) {
                ((HeaderItemViewHolder) holder).setText((String) item);
            }
        }

        public int getItemViewType(int position) {
            Object item = FriendsSideMenuRecycler.this.mListDataSource.get(position);
            if (item instanceof String) {
                return 2;
            }
            if (item instanceof FriendUserData) {
                return 1;
            }
            return 3;
        }

        public int getItemCount() {
            return (FriendsSideMenuRecycler.this.mListDataSource == null || FriendsSideMenuRecycler.this.mListDataSource.size() <= 0) ? 0 : FriendsSideMenuRecycler.this.mListDataSource.size();
        }
    }

    public interface FriendRecyclerListener {
        int getRequiredPadding();

        int getScreenHeight();

        String getSearchTerm();

        void hideKeyboard();

        void onFriendsLoaded();

        void onLoadFailed();

        void onNoFriendsLoaded();

        void onSearchBegin();

        void setFriendsProgressVisiblity(boolean z);
    }

    private class FriendViewHolder extends ViewHolder {
        private FriendItemView mFriendItemView;

        public FriendViewHolder(FriendItemView itemView) {
            super(itemView);
            this.mFriendItemView = itemView;
            this.mFriendItemView.setListener(FriendsSideMenuRecycler.this);
        }

        public void bindView(FriendUserData friendUserData, boolean showSeparator) {
            this.mFriendItemView.setModel(friendUserData);
            this.mFriendItemView.setSeparatorVisibility(showSeparator);
        }
    }

    private class FriendsComparator implements Comparator<FriendUserData> {
        private FriendsComparator() {
        }

        public int compare(FriendUserData lhs, FriendUserData rhs) {
            if (lhs.isOnline && !rhs.isOnline) {
                return -1;
            }
            if (!lhs.isOnline && rhs.isOnline) {
                return 1;
            }
            if (lhs.mStatusTimeInSeconds < rhs.mStatusTimeInSeconds) {
                return -1;
            }
            if (lhs.mStatusTimeInSeconds > rhs.mStatusTimeInSeconds) {
                return 1;
            }
            return 0;
        }
    }

    private class HeaderItemViewHolder extends ViewHolder {
        public HeaderItemViewHolder(View itemView) {
            super(itemView);
        }

        public void setText(String text) {
            ((TextView) this.itemView.findViewById(C1283R.id.lblSectionTitle)).setText(text);
        }
    }

    private class StaticItemViewHolder extends ViewHolder {
        public StaticItemViewHolder(View itemView) {
            super(itemView);
        }
    }

    public FriendsSideMenuRecycler(Context context) {
        this(context, null);
    }

    public FriendsSideMenuRecycler(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FriendsSideMenuRecycler(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.mIsRetyingSocialInfo = false;
        this.mTimesFailed = 0;
        init();
    }

    private void init() {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        this.mNm = NativeManager.getInstance();
        int height = getResources().getDimensionPixelSize(C1283R.dimen.friendItemHeight);
        this.mFilteredFriends = new ArrayList();
        setLayoutManager(new LinearLayoutManager(getContext()) {
            public int scrollVerticallyBy(int dy, Recycler recycler, State state) {
                if (FriendsSideMenuRecycler.this.mListener != null) {
                    FriendsSideMenuRecycler.this.mListener.hideKeyboard();
                }
                int result = super.scrollVerticallyBy(dy, recycler, state);
                FriendsSideMenuRecycler.this.mCurrentScrollOffset = FriendsSideMenuRecycler.this.mCurrentScrollOffset + result;
                if (!FriendsSideMenuRecycler.this.mIsShowingBackToTop && FriendsSideMenuRecycler.this.mCurrentScrollOffset >= PixelMeasure.dimension(C1283R.dimen.friendItemHeight) * 3) {
                    FriendsSideMenuRecycler.this.showBackToTop();
                } else if (FriendsSideMenuRecycler.this.mIsShowingBackToTop && FriendsSideMenuRecycler.this.mCurrentScrollOffset < PixelMeasure.dimension(C1283R.dimen.friendItemHeight) * 3) {
                    FriendsSideMenuRecycler.this.hideBackToTop();
                }
                FriendItemView.snapShutOpenCell();
                return result;
            }
        });
        setAdapter(new FriendAdapter());
        this.mFriends = new ArrayList();
        this.mListDataSource = new ArrayList();
        this.mPendingApprovalCriteria = new C29042();
        this.mSharedEtaCriteria = new C29053();
        this.mOnMyWayCriteria = new C29064();
        getRecycledViewPool().setMaxRecycledViews(1, 30);
        for (int i = 0; i < 15; i++) {
            getRecycledViewPool().putRecycledView(getAdapter().createViewHolder(null, 1));
        }
    }

    private void showBackToTop() {
        if (!this.mIsShowingBackToTop) {
            this.mIsShowingBackToTop = true;
            this.mBackToTopContainer.setTranslationY((float) (-PixelMeasure.dp(48)));
            this.mBackToTopContainer.setVisibility(0);
            ViewPropertyAnimatorHelper.initAnimation(this.mBackToTopContainer).translationY(0.0f).setListener(null);
        }
    }

    private void hideBackToTop() {
        if (this.mIsShowingBackToTop) {
            this.mIsShowingBackToTop = false;
            ViewPropertyAnimatorHelper.initAnimation(this.mBackToTopContainer).translationY((float) (-PixelMeasure.dp(48))).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mBackToTopContainer));
        }
    }

    public int getTotalOnlineFriends() {
        int result = 0;
        if (this.mFriends != null && this.mFriends.size() > 0) {
            for (FriendUserData friend : this.mFriends) {
                if (friend.isOnline) {
                    result++;
                }
            }
        }
        return result;
    }

    public void setBackToTopContainer(ViewGroup backToTopContainer) {
        this.mBackToTopContainer = backToTopContainer;
        if (this.mBackToTopContainer != null) {
            this.mBackToTopContainer.setOnClickListener(new C29075());
        }
    }

    public void reloadData() {
        this.mFriends.clear();
        if (MyWazeNativeManager.getInstance().HasSocialInfoNTV()) {
            this.mIsRetyingSocialInfo = false;
            this.mListener.setFriendsProgressVisiblity(false);
            this.mTotalFriendFetchCalled = 0;
            this.mTimesFailed = 0;
            DriveToNativeManager.getInstance().getAddFriendsData(new C29097());
            DriveToNativeManager.getInstance().getFriendsListData(new C29108());
        } else if (!this.mIsRetyingSocialInfo) {
            this.mIsRetyingSocialInfo = true;
            this.mListener.setFriendsProgressVisiblity(true);
            if (this.mTimesFailed >= 5) {
                this.mListener.setFriendsProgressVisiblity(false);
                this.mListener.onLoadFailed();
                return;
            }
            this.mTimesFailed++;
            postDelayed(new C29086(), 2000);
        }
    }

    private synchronized void appendFriends(FriendUserData[] friends) {
        if (friends != null) {
            for (FriendUserData friend : friends) {
                this.mFriends.add(friend);
            }
        }
        this.mTotalFriendFetchCalled++;
        if (this.mTotalFriendFetchCalled >= 2) {
            filterFriends();
            getAdapter().notifyDataSetChanged();
            if (this.mListener != null) {
                if (this.mFriends == null || this.mFriends.size() == 0) {
                    this.mListener.onNoFriendsLoaded();
                } else {
                    this.mListener.onFriendsLoaded();
                }
            }
        }
    }

    private List<FriendUserData> getFriendsMatchingCriteria(FriendListSortCriteria criteria) {
        List<FriendUserData> result = new ArrayList();
        for (FriendUserData friendUserData : this.mFilteredFriends) {
            if (criteria.doesAnswerCriteria(friendUserData)) {
                result.add(friendUserData);
            }
        }
        criteria.setSize(result.size());
        return result;
    }

    private void buildListDataSource() {
        this.mListDataSource.clear();
        List<FriendUserData> criteraFriends = null;
        FriendListSortCriteria matchingCriteria = null;
        for (FriendListSortCriteria criteria : new FriendListSortCriteria[]{this.mSharedEtaCriteria, this.mOnMyWayCriteria, this.mPendingApprovalCriteria}) {
            criteraFriends = getFriendsMatchingCriteria(criteria);
            if (criteraFriends != null && criteraFriends.size() > 0) {
                matchingCriteria = criteria;
                break;
            }
        }
        if (matchingCriteria == null || criteraFriends.size() <= 0) {
            this.mListDataSource.addAll(this.mFilteredFriends);
            this.mListDataSource.add(new Object());
            return;
        }
        List<FriendUserData> allOtherFriends = new ArrayList(this.mFilteredFriends);
        allOtherFriends.removeAll(criteraFriends);
        if (allOtherFriends.size() > 0) {
            this.mListDataSource.add(matchingCriteria.criteriaTitle());
        }
        this.mListDataSource.addAll(criteraFriends);
        this.mListDataSource.add(new Object());
        if (allOtherFriends.size() > 0) {
            this.mListDataSource.add(String.format(this.mNm.getLanguageString(DisplayStrings.DS_ALL_FRIENDS_PD), new Object[]{Integer.valueOf(allOtherFriends.size())}));
            this.mListDataSource.addAll(allOtherFriends);
            this.mListDataSource.add(new Object());
        }
    }

    private void filterFriends() {
        if (this.mFriends != null && this.mFriends.size() != 0) {
            this.mFilteredFriends.clear();
            String searchTerm = this.mListener.getSearchTerm();
            for (FriendUserData friend : this.mFriends) {
                if (TextUtils.isEmpty(searchTerm) || friend.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                    this.mFilteredFriends.add(friend);
                }
            }
            Collections.sort(this.mFilteredFriends, new FriendsComparator());
            buildListDataSource();
            this.mCurrentScrollOffset = 0;
            scrollToPosition(0);
            if (this.mIsShowingBackToTop) {
                hideBackToTop();
            }
        }
    }

    public void setListener(FriendRecyclerListener listener) {
        this.mListener = listener;
    }

    public void onSearchFinished() {
        getAdapter().notifyDataSetChanged();
    }

    public void onSearchTermChange(String searchText) {
        filterFriends();
        getAdapter().notifyDataSetChanged();
    }

    public int getRequiredPadding() {
        return this.mListener.getRequiredPadding();
    }

    public void refreshList() {
        reloadData();
    }

    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec, heightSpec);
    }

    public void initStrings() {
    }
}
