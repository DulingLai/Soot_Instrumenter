package com.waze.navigate.social;

import android.util.SparseIntArray;
import com.waze.user.FriendUserData;

public class AddFriendsData {
    public FriendUserData[] SuggestionFriends;
    public SparseIntArray SuggestionFriendsUids;
    public FriendUserData[] WaitingForApprovalFriends;
    public SparseIntArray WaitingForApprovalFriendsUids;
    public boolean contactLoggedIn;
    public boolean facebookLoggedIn;
}
