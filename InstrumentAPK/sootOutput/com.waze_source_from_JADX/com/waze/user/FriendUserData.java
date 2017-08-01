package com.waze.user;

public class FriendUserData extends UserData implements Comparable {
    private static final long serialVersionUID = 1;
    public String arrivedShareText;
    public String arrivedText;
    public String arrivedTime;
    public boolean isOnline;
    public boolean mAllowBeepBeep;
    public int mContactID;
    public double mEtaFraction = 0.0d;
    public int mId = -1;
    public boolean mIsFriend;
    public boolean mIsPendingFriend;
    public boolean mIsPendingMy;
    public String mMeetingIdSharedByMe;
    public String mMeetingIdSharedWithMe;
    public boolean mShowETA;
    public String name;
    public String pictureUrl;
    public String sharestatusLine;
    public String statusLine;

    public String getName() {
        return this.name;
    }

    public String getImage() {
        return this.pictureUrl;
    }

    public void setImage(String Image) {
        this.pictureUrl = Image;
        super.setImage(Image);
    }

    public boolean getIsOnWaze() {
        return true;
    }

    public int compareTo(Object another) {
        if (another == null) {
            throw new NullPointerException();
        } else if (!(another instanceof FriendUserData)) {
            return 1;
        } else {
            FriendUserData a = (FriendUserData) another;
            if (a.mEtaFraction == this.mEtaFraction) {
                return 0;
            }
            if (this.mEtaFraction <= a.mEtaFraction) {
                return -1;
            }
            return 1;
        }
    }
}
