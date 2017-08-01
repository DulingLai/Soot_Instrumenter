package com.waze.navigate.social;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IRefreshFriendsDrivingData;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.Person;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.ui.ActivityBase;
import com.waze.map.MapView;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.PublicMacros;
import com.waze.phone.AddressBookImpl;
import com.waze.share.UserDetailsActivity;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;

public class MyShareDriveActivity extends ActivityBase implements IRefreshFriendsDrivingData {
    public static final int REQUEST_ADD_DRIVE_SHARE = 2001;
    private boolean IsSetMeeting = false;
    ArrayAdapter<PersonBase> adapter;
    private boolean bIsDriving = false;
    private DriveToNativeManager dtnm;
    private ImageView increaseMapButton;
    private Button increaseMapOverlay;
    private boolean isDayMode;
    private boolean isMapBig = false;
    private ImageView leftCorner;
    private ArrayList<PersonBase> mFriendsArray = new ArrayList();
    private LinearLayout mFriendsLayout;
    private FriendsListData mFriendsListData = null;
    private boolean mIsChecked = true;
    private final RunnableExecutor mNativeCanvasReadyEvent = new C22088();
    protected String mShareOwner;
    int mType = 0;
    String mUrl = null;
    private RelativeLayout mapLayout;
    private RelativeLayout mapPlaceholder;
    private MapView mapView;
    private NativeManager nm;
    ArrayList<PersonBase> people;
    private ImageView rightCorner;
    String sMeeting = null;
    protected TextView shareDriveSwitch;

    class C22011 implements OnGlobalLayoutListener {
        C22011() throws  {
        }

        public void onGlobalLayout() throws  {
            LayoutParams $r1 = new LayoutParams(MyShareDriveActivity.this.mapPlaceholder.getMeasuredWidth(), MyShareDriveActivity.this.mapPlaceholder.getMeasuredHeight());
            $r1.topMargin = MyShareDriveActivity.this.mapPlaceholder.getTop();
            $r1.leftMargin = MyShareDriveActivity.this.mapPlaceholder.getLeft();
            MyShareDriveActivity.this.mapLayout.setLayoutParams($r1);
            MyShareDriveActivity.this.mapPlaceholder.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }

    class C22022 implements OnClickListener {
        C22022() throws  {
        }

        public void onClick(View v) throws  {
            MyShareDriveActivity.this.stopMeeting();
        }
    }

    class C22033 implements FriendsListListener {
        C22033() throws  {
        }

        public void onComplete(FriendsListData $r1) throws  {
            MyShareDriveActivity.this.mFriendsListData = $r1;
            MyShareDriveActivity.this.mFriendsArray.clear();
            MyShareDriveActivity.this.mFriendsLayout.removeAllViews();
            if (MyShareDriveActivity.this.mFriendsListData != null) {
                if (MyShareDriveActivity.this.mFriendsListData.friends != null) {
                    for (FriendUserData $r2 : MyShareDriveActivity.this.mFriendsListData.friends) {
                        if ($r2.mContactID != -1) {
                            Person $r8 = AddressBookImpl.getInstance().GetPersonFromID($r2.mContactID);
                            if (!($r8 == null || $r8.getImage() == null)) {
                                $r2.setImage($r8.getImage());
                            }
                        }
                        MyShareDriveActivity.this.mFriendsArray.add($r2);
                    }
                }
                MyShareDriveActivity.this.friendsListPopulateFriends();
            }
            MyShareDriveActivity.this.addAddMoreFriends();
        }
    }

    class C22044 implements OnClickListener {
        final /* synthetic */ FriendUserData val$fud;
        final /* synthetic */ PersonBase val$p;

        C22044(PersonBase $r2, FriendUserData $r3) throws  {
            this.val$p = $r2;
            this.val$fud = $r3;
        }

        public void onClick(View v) throws  {
            if (this.val$p.getID() != -1) {
                MyWazeNativeManager.getInstance().sendSocialAddFriends(new int[]{this.val$p.getID()}, 1, String.format(MyShareDriveActivity.this.nm.getLanguageString((int) DisplayStrings.DS_PS_ADDED), new Object[]{this.val$p.getName()}));
            } else if (this.val$fud.mContactID >= 0) {
                Person $r13 = AddressBookImpl.getInstance().GetPersonFromID(this.val$fud.mContactID);
                if ($r13 != null) {
                    int[] $r2 = new int[1];
                    String[] $r3 = new String[]{$r13.getID()};
                    $r3[0] = $r13.getPhone();
                    MyWazeNativeManager.getInstance().sendSocialInviteFriends($r2, $r3, 1, String.format(MyShareDriveActivity.this.nm.getLanguageString((int) DisplayStrings.DS_PS_INVITED), new Object[]{$r13.getName()}));
                }
            }
        }
    }

    class C22055 implements OnClickListener {
        final /* synthetic */ PersonBase val$p;

        C22055(PersonBase $r2) throws  {
            this.val$p = $r2;
        }

        public void onClick(View v) throws  {
            Intent $r2 = r5;
            Intent r5 = new Intent(MyShareDriveActivity.this, UserDetailsActivity.class);
            $r2.putExtra(PublicMacros.FriendUserData, this.val$p);
            MyShareDriveActivity.this.startActivityForResult($r2, 0);
        }
    }

    class C22066 implements OnClickListener {
        C22066() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_ADD_FRIENDS_CLICK, null, null);
            Intent $r2 = new Intent(MyShareDriveActivity.this, AddFromActivity.class);
            $r2.putExtra(AddFromActivity.INTENT_FROM_WHERE, AddFromActivity.INTENT_FROM_SHARE);
            $r2.putExtra("type", 1);
            MyShareDriveActivity.this.startActivityForResult($r2, 2001);
        }
    }

    class C22077 implements DialogInterface.OnClickListener {
        C22077() throws  {
        }

        public void onClick(DialogInterface dialog, int $i0) throws  {
            if ($i0 == 1) {
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MY_SHARED_DRIVE_STOP_SHARING, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, MyShareDriveActivity.this.sMeeting);
                MyShareDriveActivity.this.nm.StopFollow();
                MyShareDriveActivity.this.setResult(3);
                MyShareDriveActivity.this.finish();
            }
        }
    }

    class C22088 extends RunnableExecutor {
        C22088() throws  {
        }

        public void event() throws  {
            if (!MyShareDriveActivity.this.IsSetMeeting) {
                MyShareDriveActivity.this.dtnm.setMeeting(MyShareDriveActivity.this.sMeeting);
                MyShareDriveActivity.this.IsSetMeeting = true;
            }
        }
    }

    private void addFriend(com.waze.user.PersonBase r29) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:31:0x0110
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r28 = this;
        r4 = r29;
        r4 = (com.waze.user.FriendUserData) r4;
        r3 = r4;
        r6 = "layout_inflater";
        r0 = r28;
        r5 = r0.getSystemService(r6);
        r8 = r5;
        r8 = (android.view.LayoutInflater) r8;
        r7 = r8;
        r10 = 2130903073; // 0x7f030021 float:1.7412954E38 double:1.052806003E-314;
        r11 = 0;
        r9 = r7.inflate(r10, r11);
        r12 = r3.getName();
        r13 = r3.getImage();
        r0 = r28;
        com.waze.navigate.social.AddFriendsUtils.setNameAndImage(r0, r9, r12, r13);
        r12 = r3.sharestatusLine;
        if (r12 == 0) goto L_0x0032;
    L_0x002a:
        r12 = r3.sharestatusLine;
        r14 = r12.length();
        if (r14 != 0) goto L_0x0140;
    L_0x0032:
        r12 = r3.statusLine;
        if (r12 == 0) goto L_0x003e;
    L_0x0036:
        r12 = r3.statusLine;
        r14 = r12.length();
        if (r14 != 0) goto L_0x0120;
    L_0x003e:
        r10 = 2131689713; // 0x7f0f00f1 float:1.900845E38 double:1.053194655E-314;
        r15 = r9.findViewById(r10);
        r10 = 8;
        r15.setVisibility(r10);
    L_0x004a:
        r10 = 2131689706; // 0x7f0f00ea float:1.9008435E38 double:1.0531946513E-314;
        r15 = r9.findViewById(r10);
        r0 = r3.isOnline;
        r16 = r0;
        if (r16 == 0) goto L_0x0164;
    L_0x0057:
        r17 = 0;
    L_0x0059:
        r0 = r17;
        r15.setVisibility(r0);
        r10 = 2131689716; // 0x7f0f00f4 float:1.9008455E38 double:1.0531946563E-314;
        r15 = r9.findViewById(r10);
        r19 = r15;
        r19 = (android.widget.ImageView) r19;
        r18 = r19;
        r10 = 2131689715; // 0x7f0f00f3 float:1.9008453E38 double:1.053194656E-314;
        r15 = r9.findViewById(r10);
        r0 = r3.mIsFriend;
        r16 = r0;
        if (r16 != 0) goto L_0x0167;
    L_0x0078:
        r0 = r3.mIsPendingFriend;
        r16 = r0;
        if (r16 != 0) goto L_0x0167;
    L_0x007e:
        r0 = r3.mIsFbFriend;
        r16 = r0;
        if (r16 != 0) goto L_0x0167;
    L_0x0084:
        r11 = 0;
        r9.setOnClickListener(r11);
        r10 = 8;
        r15.setVisibility(r10);
        r10 = 0;
        r0 = r18;
        r0.setVisibility(r10);
        r10 = 2130837599; // 0x7f02005f float:1.7280157E38 double:1.0527736545E-314;
        r0 = r18;
        r0.setImageResource(r10);
        r20 = new com.waze.navigate.social.MyShareDriveActivity$4;
        r0 = r20;
        r1 = r28;
        r2 = r29;
        r0.<init>(r2, r3);
        r0 = r18;
        r1 = r20;
        r0.setOnClickListener(r1);
    L_0x00ad:
        r21 = new com.waze.navigate.social.MyShareDriveActivity$5;
        goto L_0x00b3;
    L_0x00b0:
        goto L_0x004a;
    L_0x00b3:
        r0 = r21;
        r1 = r28;
        r2 = r29;
        r0.<init>(r2);
        r0 = r21;
        r9.setOnClickListener(r0);
        r0 = r3.mShowETA;
        r16 = r0;
        if (r16 == 0) goto L_0x018b;
    L_0x00c7:
        goto L_0x00cb;
    L_0x00c8:
        goto L_0x004a;
    L_0x00cb:
        r10 = 2131689714; // 0x7f0f00f2 float:1.9008451E38 double:1.0531946553E-314;
        r15 = r9.findViewById(r10);
        goto L_0x00d6;
    L_0x00d3:
        goto L_0x0059;
    L_0x00d6:
        r10 = 0;
        r15.setVisibility(r10);
        r10 = 2131689718; // 0x7f0f00f6 float:1.900846E38 double:1.0531946573E-314;
        r15 = r9.findViewById(r10);
        r23 = r15;
        r23 = (android.widget.TextView) r23;
        r22 = r23;
        r12 = r3.arrivedText;
        r0 = r22;
        r0.setText(r12);
        goto L_0x00f2;
    L_0x00ef:
        goto L_0x00b0;
    L_0x00f2:
        r10 = 2131689719; // 0x7f0f00f7 float:1.9008461E38 double:1.0531946578E-314;
        r15 = r9.findViewById(r10);
        r24 = r15;
        r24 = (android.widget.TextView) r24;
        r22 = r24;
        r12 = r3.arrivedTime;
        r0 = r22;
        r0.setText(r12);
    L_0x0106:
        r0 = r28;
        r0 = r0.mFriendsLayout;
        r25 = r0;
        r0.addView(r9);
        goto L_0x011f;
        goto L_0x0114;
    L_0x0111:
        goto L_0x00ad;
    L_0x0114:
        goto L_0x00c8;
        goto L_0x011f;
        goto L_0x011c;
    L_0x0119:
        goto L_0x00ad;
    L_0x011c:
        goto L_0x00d3;
    L_0x011f:
        return;
    L_0x0120:
        r10 = 2131689713; // 0x7f0f00f1 float:1.900845E38 double:1.053194655E-314;
        r15 = r9.findViewById(r10);
        r10 = 0;
        r15.setVisibility(r10);
        r10 = 2131689713; // 0x7f0f00f1 float:1.900845E38 double:1.053194655E-314;
        r15 = r9.findViewById(r10);
        r26 = r15;
        r26 = (android.widget.TextView) r26;
        r22 = r26;
        r12 = r3.statusLine;
        r0 = r22;
        r0.setText(r12);
        goto L_0x00ef;
    L_0x0140:
        r10 = 2131689713; // 0x7f0f00f1 float:1.900845E38 double:1.053194655E-314;
        r15 = r9.findViewById(r10);
        r10 = 0;
        r15.setVisibility(r10);
        goto L_0x014f;
    L_0x014c:
        goto L_0x0106;
    L_0x014f:
        r10 = 2131689713; // 0x7f0f00f1 float:1.900845E38 double:1.053194655E-314;
        r15 = r9.findViewById(r10);
        r27 = r15;
        r27 = (android.widget.TextView) r27;
        r22 = r27;
        r12 = r3.sharestatusLine;
        r0 = r22;
        r0.setText(r12);
        goto L_0x0114;
    L_0x0164:
        r17 = 4;
        goto L_0x011c;
    L_0x0167:
        r0 = r3.mIsPendingFriend;
        r16 = r0;
        if (r16 == 0) goto L_0x017e;
    L_0x016d:
        r11 = 0;
        r9.setOnClickListener(r11);
        r10 = 8;
        r15.setVisibility(r10);
        r10 = 8;
        r0 = r18;
        r0.setVisibility(r10);
        goto L_0x0111;
    L_0x017e:
        r10 = 8;
        r15.setVisibility(r10);
        r10 = 8;
        r0 = r18;
        r0.setVisibility(r10);
        goto L_0x0119;
    L_0x018b:
        r10 = 2131689714; // 0x7f0f00f2 float:1.9008451E38 double:1.0531946553E-314;
        r15 = r9.findViewById(r10);
        r10 = 8;
        r15.setVisibility(r10);
        goto L_0x014c;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.social.MyShareDriveActivity.addFriend(com.waze.user.PersonBase):void");
    }

    protected void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        this.nm = NativeManager.getInstance();
        this.dtnm = DriveToNativeManager.getInstance();
        requestWindowFeature(1);
        setRequestedOrientation(1);
        setContentView(C1283R.layout.my_share_drive);
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MY_SHARED_DRIVE_SHOWN, null, null);
        ((TitleBar) findViewById(C1283R.id.myShareDriveTitle)).init(this, this.nm.getLanguageString((int) DisplayStrings.DS_SHARED_DRIVE_TITLE));
        this.mType = getIntent().getIntExtra("type", 0);
        this.sMeeting = getIntent().getStringExtra("meeting");
        ((TextView) findViewById(C1283R.id.myShareDriveTopText)).setText(this.nm.getLanguageString((int) DisplayStrings.DS_SHARED_DRIVE_STATUS_BAR));
        ((TextView) findViewById(C1283R.id.myShareDriveStopButton)).setText(this.nm.getLanguageString((int) DisplayStrings.DS_STOP_SHARING));
        if (this.sMeeting == null) {
            findViewById(C1283R.id.myShareDriveButtonLayout).setVisibility(8);
        } else if (this.nm.isMeetingActiveNTV(this.sMeeting)) {
            this.bIsDriving = true;
        }
        this.mFriendsLayout = (LinearLayout) findViewById(C1283R.id.myShareDriveFriendsDriving);
        this.mapPlaceholder = (RelativeLayout) findViewById(C1283R.id.myShareDriveMapLayoutPlaceholder);
        this.mapLayout = (RelativeLayout) findViewById(C1283R.id.myShareDriveMapLayout);
        RelativeLayout $r10 = this.mapPlaceholder;
        $r10.getViewTreeObserver().addOnGlobalLayoutListener(new C22011());
        this.increaseMapOverlay = (Button) findViewById(C1283R.id.myShareDriveIncreaseMapOverlay);
        this.mapView = (MapView) findViewById(C1283R.id.myShareDriveMap);
        this.mapView.setHandleTouch(false);
        this.increaseMapOverlay.setVisibility(0);
        this.mapView.registerOnMapReadyCallback(this.mNativeCanvasReadyEvent);
        findViewById(C1283R.id.myShareDriveStopButton).setOnClickListener(new C22022());
        this.isDayMode = this.dtnm.isDayMode();
        this.increaseMapButton = (ImageView) findViewById(C1283R.id.myShareDriveIncreaseMapButton);
        this.increaseMapButton.setImageResource(C1283R.drawable.map_smaller_day);
        if ($r1 != null) {
        }
    }

    public void onRefresh() throws  {
        this.dtnm.getMySharedDriveUsers(new C22033());
    }

    private void friendsListPopulateFriends() throws  {
        if (this.mFriendsArray != null && !this.mFriendsArray.isEmpty()) {
            Iterator $r2 = this.mFriendsArray.iterator();
            while ($r2.hasNext()) {
                addFriend((PersonBase) $r2.next());
            }
        }
    }

    private void addAddMoreFriends() throws  {
        View $r3 = ((LayoutInflater) getSystemService("layout_inflater")).inflate(C1283R.layout.add_friends_in_list, null);
        ((TextView) $r3.findViewById(C1283R.id.addFriendsName)).setText(this.nm.getLanguageString((int) DisplayStrings.DS_ADD_MORE_PEOPLE));
        $r3.findViewById(C1283R.id.addFriendsStatus).setVisibility(8);
        $r3.findViewById(C1283R.id.addFriendsFriendOnline).setVisibility(4);
        $r3.findViewById(C1283R.id.addFriendsImage).setVisibility(8);
        $r3.findViewById(C1283R.id.addFriendsImageContainer).setVisibility(4);
        $r3.findViewById(C1283R.id.addFriendsAddMore).setVisibility(0);
        ImageView $r8 = (ImageView) $r3.findViewById(C1283R.id.AddFriendButton);
        $r3.findViewById(C1283R.id.RemoveFriendButton).setVisibility(8);
        $r8.setVisibility(8);
        $r3.setOnClickListener(new C22066());
        LinearLayout $r10 = this.mFriendsLayout;
        $r10.addView($r3);
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i0 == 2001 && $i1 == -1) {
            addToMeeting((ArrayList) $r1.getExtras().getSerializable(AddFromActivity.INTENT_SELECTED));
        } else if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    void addToMeeting(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ArrayList<PersonBase> $r1) throws  {
        if ($r1 != null && !$r1.isEmpty()) {
            int[] $r2 = new int[$r1.size()];
            String[] $r4 = new String[$r1.size()];
            int[] $r3 = new int[$r1.size()];
            String[] $r5 = new String[$r1.size()];
            int $i0 = 0;
            int $i1 = 0;
            Iterator $r6 = $r1.iterator();
            while ($r6.hasNext()) {
                PersonBase $r8 = (PersonBase) $r6.next();
                if ($r8.getIsOnWaze()) {
                    $r5[$i0] = $r8.getPhone();
                    $r2[$i0] = $r8.getID();
                    $i0++;
                } else {
                    $r4[$i1] = $r8.getPhone();
                    $r3[$i1] = $r8.getID();
                    $i1++;
                }
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_SHARE_SENT, null, null);
            if ($i0 > 0) {
                NativeManager.getInstance().AddToMeeting($r2, $i0, $r5, false);
            }
            if ($i1 > 0) {
                NativeManager.getInstance().InviteToMeeting($r4, $r3, $i1, 4);
            }
        }
    }

    void stopMeeting() throws  {
        C22077 $r1 = new C22077();
        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(this.nm.getLanguageString((int) DisplayStrings.DS_CONFIRM_STOP_FOLLOW_TITLE), this.nm.getLanguageString((int) DisplayStrings.DS_CONFIRM_STOP_FOLLOW_BODY), true, $r1, this.nm.getLanguageString((int) DisplayStrings.DS_YES), this.nm.getLanguageString((int) DisplayStrings.DS_NO), -1);
    }

    private void setMapLayoutListener(View $r1) throws  {
        LayoutParams $r2 = new LayoutParams($r1.getMeasuredWidth(), $r1.getMeasuredHeight());
        $r2.topMargin = $r1.getTop();
        $r2.leftMargin = $r1.getLeft();
        this.mapLayout.setLayoutParams($r2);
    }

    public void increaseMapClicked(View v) throws  {
        if (this.isMapBig) {
            this.isMapBig = false;
            this.mapView.setHandleTouch(false);
            this.increaseMapOverlay.setVisibility(0);
            this.increaseMapButton.setImageResource(C1283R.drawable.map_smaller_day);
            setMapLayoutListener(this.mapPlaceholder);
            this.dtnm.setMapMode(true);
            return;
        }
        this.isMapBig = true;
        this.mapView.setHandleTouch(true);
        this.increaseMapOverlay.setVisibility(8);
        this.increaseMapButton.setImageResource(C1283R.drawable.map_bigger_day);
        setMapLayoutListener(findViewById(C1283R.id.myShareDriveContent));
        this.dtnm.setMapMode(false);
    }

    protected void onPause() throws  {
        super.onPause();
        this.mapView.onPause();
        this.IsSetMeeting = false;
        this.dtnm.unsetMeeting();
    }

    protected void onResume() throws  {
        super.onResume();
        this.mapView.onResume();
    }

    public void onBackPressed() throws  {
        super.onBackPressed();
    }
}
