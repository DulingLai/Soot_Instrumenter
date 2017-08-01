package com.waze.view.popups;

import android.content.Context;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.navbar.NavBar;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;

public class ShareStatusPopUp extends GenericNotification {
    private Runnable mRunnable;
    private boolean mWasInit;

    class C32102 implements OnTouchListener {
        C32102() {
        }

        public boolean onTouch(View v, MotionEvent event) {
            ShareStatusPopUp.this.hide();
            return true;
        }
    }

    class C32113 implements Runnable {
        C32113() {
        }

        public void run() {
            ShareStatusPopUp.this.hide();
        }
    }

    public ShareStatusPopUp(Context context, LayoutManager layoutManager, int type, int numFriends, FriendUserData fud) {
        super(context, layoutManager);
        init(type, numFriends, fud);
    }

    public boolean onBackPressed() {
        hide();
        return super.onBackPressed();
    }

    public void setup(int type, int numFriends, FriendUserData fud) {
        init(type, numFriends, fud);
    }

    void init(int type, int numFriends, FriendUserData fud) {
        super.init();
        this.mWasInit = true;
        final NativeManager nm = NativeManager.getInstance();
        CarpoolNativeManager cpnm = CarpoolNativeManager.getInstance();
        final boolean bCarpoolSharing = cpnm.isCarpoolShareOnly();
        if (bCarpoolSharing) {
            final int i = type;
            final int i2 = numFriends;
            cpnm.getDriveInfoByMeetingId(cpnm.getCurMeetingIdNTV(), new IResultObj<CarpoolDrive>() {
                public void onResult(CarpoolDrive res) {
                    if (res != null && res.hasRider()) {
                        ShareStatusPopUp.this.initText(i, i2, nm, res.getRider().getFirstName(), bCarpoolSharing);
                        if (res.isMultiPax()) {
                            ShareStatusPopUp.this.setUserImages(res);
                            return;
                        }
                        String image = res.getRider().getImage();
                        if (TextUtils.isEmpty(image)) {
                            ShareStatusPopUp.this.setIcon((int) C1283R.drawable.notification_share_icon_green);
                        } else {
                            ShareStatusPopUp.this.setUserImage(image);
                        }
                    }
                }
            });
        } else if (fud != null) {
            if (!(fud.getName() == null || fud.getName().isEmpty())) {
                initText(type, numFriends, nm, fud.getName(), bCarpoolSharing);
            }
            if (!(fud.getImage() == null || fud.getImage().isEmpty())) {
                String image = fud.getImage();
                if (TextUtils.isEmpty(image)) {
                    setIcon((int) C1283R.drawable.notification_share_icon_green);
                } else {
                    setUserImage(image);
                }
            }
        } else {
            this.mWasInit = false;
        }
        setButton1Gone();
        setButton2Gone();
    }

    private void initText(int type, int numFriends, NativeManager nm, String name, boolean bCarpoolSharing) {
        String text = null;
        if (type == 1) {
            text = bCarpoolSharing ? numFriends > 1 ? nm.getLanguageString(DisplayStrings.DS_CARPOOL_SHARE_TIP_MANY_RIDERS) : (name == null || name.isEmpty()) ? nm.getLanguageString(DisplayStrings.DS_CARPOOL_SHARE_TIP_NO_NAME) : DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_SHARE_TIP_PS, name) : nm.getLanguageString(DisplayStrings.DS_ETA_SENTE_SEE_PEOPLE_VIEWING_YOUR_DRIVE);
        } else if (type == 3) {
            if (numFriends == 1 && name != null && !name.isEmpty()) {
                text = String.format(nm.getLanguageString(DisplayStrings.DS_ETA_UPDATE_SENT_TO_PS), new Object[]{name});
            } else if (bCarpoolSharing) {
                text = numFriends == 1 ? DisplayStrings.displayString(DisplayStrings.DS_ETA_UPDATE_SENT_TO_RIDER) : DisplayStrings.displayString(DisplayStrings.DS_ETA_UPDATE_SENT_TO_MANY_RIDERS);
            } else if (numFriends >= 1) {
                text = String.format(nm.getLanguageString(DisplayStrings.DS_ETA_UPDATE_SENT_TO_PD_FRIENDS), new Object[]{Integer.valueOf(numFriends)});
            }
        } else if (type == 4) {
            if (numFriends == 1 && name != null && !name.isEmpty()) {
                text = DisplayStrings.displayStringF(DisplayStrings.DS_ARRIVAL_NOTIFICATION_SENT_TO_PS, name);
            } else if (bCarpoolSharing) {
                text = numFriends == 1 ? DisplayStrings.displayString(DisplayStrings.DS_ARRIVAL_NOTIFICATION_SENT_TO_RIDER) : DisplayStrings.displayString(DisplayStrings.DS_ARRIVAL_NOTIFICATION_SENT_TO_MANY_RIDERS);
            } else if (numFriends >= 1) {
                text = DisplayStrings.displayStringF(DisplayStrings.DS_ARRIVAL_NOTIFICATION_SENT_TO_PD_FRIENDS, Integer.valueOf(numFriends));
            }
        } else if (type == 2) {
            if (numFriends == 1 && name != null && !name.isEmpty()) {
                text = DisplayStrings.displayStringF(DisplayStrings.DS_PS_IS_VIEWING_YOUR_DRIVE, name);
            } else if (bCarpoolSharing) {
                text = numFriends == 1 ? DisplayStrings.displayString(DisplayStrings.DS_RIDER_VIEWING_YOUR_DRIVE) : DisplayStrings.displayString(DisplayStrings.DS_MANY_RIDERS_VIEWING_YOUR_DRIVE);
            } else if (numFriends >= 1) {
                text = DisplayStrings.displayStringF(DisplayStrings.DS_PD_FRIENDS_ARE_VIEWING_YOUR_DRIVE, Integer.valueOf(numFriends));
            }
        }
        if (text != null) {
            setText(text);
        }
    }

    public boolean show(boolean autoClose) {
        if (!this.mWasInit) {
            return false;
        }
        setOnTouchListener(new C32102());
        if (autoClose) {
            this.mRunnable = new C32113();
            postDelayed(this.mRunnable, 8000);
        }
        NavBar n = this.mLayoutManager.getNavBar();
        if (n != null) {
            n.setNextEnabled(false);
        }
        super.show();
        return true;
    }

    public void hide() {
        removeCallbacks(this.mRunnable);
        this.mRunnable = null;
        NavBar n = this.mLayoutManager.getNavBar();
        if (n != null) {
            n.setNextEnabled(true);
        }
        if (this.mLayoutManager != null) {
            this.mLayoutManager.hideSpotifyButton();
        }
        super.hide();
    }
}
