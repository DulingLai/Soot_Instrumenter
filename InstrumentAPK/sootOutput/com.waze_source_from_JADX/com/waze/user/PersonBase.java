package com.waze.user;

import java.io.Serializable;

public class PersonBase implements Serializable {
    private static final long serialVersionUID = 1;
    public int mID;
    protected String mImageUrl;
    boolean mIsOnWaze = false;
    public String mNickName;
    public String mPhone = null;

    public String getName() {
        return this.mNickName;
    }

    public void setID(int id) {
        this.mID = id;
    }

    public String getPhone() {
        return this.mPhone;
    }

    public String getImage() {
        return this.mImageUrl;
    }

    public void setImage(String Image) {
        this.mImageUrl = Image;
    }

    public int getID() {
        return this.mID;
    }

    public boolean getIsOnWaze() {
        return this.mIsOnWaze;
    }

    public void setIsOnWaze(boolean bIsOnWaze) {
        this.mIsOnWaze = bIsOnWaze;
    }

    public PersonBase(PersonBase p) {
        if (p.mNickName != null) {
            this.mNickName = new String(p.mNickName);
        }
        if (p.mPhone != null) {
            this.mPhone = new String(p.mPhone);
        }
        if (p.mImageUrl != null) {
            this.mImageUrl = new String(p.mImageUrl);
        }
        this.mID = p.mID;
        this.mIsOnWaze = p.mIsOnWaze;
    }

    public String toString() {
        return getName();
    }
}
