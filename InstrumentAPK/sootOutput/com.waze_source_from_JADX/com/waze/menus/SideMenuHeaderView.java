package com.waze.menus;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.MainActivity;
import com.waze.MoodManager;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.inbox.InboxMessage;
import com.waze.inbox.InboxNativeManager;
import com.waze.inbox.InboxNativeManager.InboxDataListener;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.MyWazeDataHandler;
import com.waze.profile.AccountSignInDetails;
import com.waze.profile.ImageTaker;
import com.waze.profile.TempUserProfileActivity;
import com.waze.settings.SettingsMainActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.view.text.WazeTextView;
import java.io.File;

public class SideMenuHeaderView extends FrameLayout implements InboxDataListener {
    private WazeTextView mFriendsOnline;
    private ImageView mFriendsOnlineIcon;
    private ImageTaker mImageTaker;
    private ImageView mMoodAddonImage;
    private ImageView mMoodImage;
    private FrameLayout mMyWazeDetailsContainer;
    private LinearLayout mMyWazeFriendsMailContainer;
    private WazeTextView mNewEmails;
    private ImageView mNewEmailsIcon;
    private int mNewFriendsOnline;
    private int mNewMailMsgs;
    private ImageView mProfileImage;
    private boolean mRequiresLayout;
    private View mSep;
    private ImageButton mSettingsButton;
    private ImageButton mShutdownButton;
    private TextView mWazeNameLabel;

    class C19481 implements OnClickListener {
        C19481() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "MY_WAZE");
            MyWazeNativeManager.getInstance().launchMyWaze(SideMenuHeaderView.this.getContext());
        }
    }

    class C19492 implements OnClickListener {
        C19492() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "SETTINGS");
            Intent $r2 = new Intent(SideMenuHeaderView.this.getContext(), SettingsMainActivity.class);
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().startActivityForResult($r2, MainActivity.SETTINGS_CODE);
            } else if (AppService.getActiveActivity() != null) {
                AppService.getActiveActivity().startActivityForResult($r2, MainActivity.SETTINGS_CODE);
            }
        }
    }

    class C19503 implements OnClickListener {
        C19503() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, AnalyticsEvents.ANALYTICS_SWITCH_OFF);
            Analytics.flush();
            NativeManager.getInstance().StopWaze();
        }
    }

    class C19514 implements OnClickListener {
        C19514() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_CLICK, AnalyticsEvents.ANALYTICS_EVENT_INFO_VALUE, "MY_WAZE");
            if (MyWazeNativeManager.getInstance().isGuestUserNTV()) {
                AppService.getActiveActivity().startActivityForResult(new Intent(SideMenuHeaderView.this.getContext(), TempUserProfileActivity.class), 0);
                return;
            }
            if (SideMenuHeaderView.this.mImageTaker == null) {
                SideMenuHeaderView.this.mImageTaker = new ImageTaker(AppService.getActiveActivity(), AccountSignInDetails.PROFILE_IMAGE_FILE);
                int $i0 = ConfigValues.getIntValue(40);
                SideMenuHeaderView.this.mImageTaker.setOutputResolution($i0, $i0, 1, 1);
            }
            AppService.getMainActivity().setImageTaker(SideMenuHeaderView.this.mImageTaker);
            SideMenuHeaderView.this.mImageTaker.sendIntent();
        }
    }

    class C19535 implements MyWazeDataHandler {
        C19535() throws  {
        }

        public void onMyWazeDataReceived(final MyWazeData $r1) throws  {
            SideMenuHeaderView.this.post(new Runnable() {
                public void run() throws  {
                    SideMenuHeaderView.this.applyMyWazeData($r1);
                }
            });
        }
    }

    class C19546 implements Runnable {
        C19546() throws  {
        }

        public void run() throws  {
            SideMenuHeaderView.this.setUserData();
        }
    }

    class C19567 extends ImageRepositoryListener {
        final /* synthetic */ MyWazeData val$data;

        C19567(MyWazeData $r2) throws  {
            this.val$data = $r2;
        }

        public void onImageRetrieved(final Bitmap $r1) throws  {
            SideMenuHeaderView.this.post(new Runnable() {
                public void run() throws  {
                    if ($r1 != null) {
                        VolleyManager.getInstance().forceCache(C19567.this.val$data.mImageUrl, $r1, PixelMeasure.dp(160), PixelMeasure.dp(160));
                        SideMenuHeaderView.this.mProfileImage.setImageDrawable(new CircleShaderDrawable($r1, 0));
                    }
                }
            });
        }
    }

    private void applyMyWazeData(com.waze.mywaze.MyWazeData r27) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:26:0x0108
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r26 = this;
        r5 = 0;
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r7 = r6.getText();
        r8 = r7.toString();
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r0 = r26;
        r9 = r0.getResources();
        r11 = 2131623937; // 0x7f0e0001 float:1.887504E38 double:1.053162157E-314;
        r10 = r9.getColor(r11);
        r6.setTextColor(r10);
        r12 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r13 = r12.isGuestUserNTV();
        if (r13 == 0) goto L_0x00b9;
    L_0x002b:
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r14 = com.waze.NativeManager.getInstance();
        r11 = 2828; // 0xb0c float:3.963E-42 double:1.397E-320;
        r15 = r14.getLanguageString(r11);
        r6.setText(r15);
    L_0x003c:
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r7 = r6.getText();
        r15 = r7.toString();
        r13 = r15.equals(r8);
        if (r13 != 0) goto L_0x017a;
    L_0x004e:
        r13 = 1;
    L_0x004f:
        r0 = r26;
        r0.mRequiresLayout = r13;
        r0 = r27;
        r10 = r0.mNewEmails;
        r0 = r27;
        r0 = r0.nFriendsOnline;
        r16 = r0;
        r0 = r26;
        r1 = r16;
        r0.updateMailFriendsByLine(r10, r1);
        r0 = r26;
        r0.requestLayout();
        r0 = r27;
        r8 = r0.mImageUrl;
        r13 = android.text.TextUtils.isEmpty(r8);
        if (r13 != 0) goto L_0x008b;
    L_0x0073:
        r17 = com.waze.utils.ImageRepository.instance;
        r0 = r27;
        r8 = r0.mImageUrl;
        r18 = new com.waze.menus.SideMenuHeaderView$7;
        r0 = r18;
        r1 = r26;
        r2 = r27;
        r0.<init>(r2);
        r0 = r17;
        r1 = r18;
        r0.getImage(r8, r1);
    L_0x008b:
        r11 = 2131691053; // 0x7f0f062d float:1.9011167E38 double:1.053195317E-314;
        r0 = r26;
        r19 = r0.findViewById(r11);
        r20 = com.waze.ConfigManager.getInstance();
        goto L_0x009c;
    L_0x0099:
        goto L_0x003c;
    L_0x009c:
        r11 = 199; // 0xc7 float:2.79E-43 double:9.83E-322;
        r0 = r20;
        r13 = r0.getConfigValueBool(r11);
        if (r13 == 0) goto L_0x017c;
    L_0x00a6:
        goto L_0x00aa;
    L_0x00a7:
        goto L_0x003c;
    L_0x00aa:
        r0 = r19;
        r0.setVisibility(r5);
        r0 = r26;
        r0.updateMood();
        goto L_0x00b8;
    L_0x00b5:
        goto L_0x003c;
    L_0x00b8:
        return;
    L_0x00b9:
        r0 = r27;
        r15 = r0.mFirstName;
        r13 = android.text.TextUtils.isEmpty(r15);
        goto L_0x00c5;
    L_0x00c2:
        goto L_0x003c;
    L_0x00c5:
        if (r13 == 0) goto L_0x00d1;
    L_0x00c7:
        r0 = r27;
        r15 = r0.mLastName;
        r13 = android.text.TextUtils.isEmpty(r15);
        if (r13 != 0) goto L_0x010c;
    L_0x00d1:
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r21 = new java.lang.StringBuilder;
        r0 = r21;
        r0.<init>();
        r0 = r27;
        r15 = r0.mFirstName;
        r0 = r21;
        r21 = r0.append(r15);
        r22 = " ";
        r0 = r21;
        r1 = r22;
        r21 = r0.append(r1);
        r0 = r27;
        r15 = r0.mLastName;
        goto L_0x00f8;
    L_0x00f5:
        goto L_0x00b5;
    L_0x00f8:
        r0 = r21;
        r21 = r0.append(r15);
        r0 = r21;
        r15 = r0.toString();
        r6.setText(r15);
        goto L_0x0099;
        goto L_0x010c;
    L_0x0109:
        goto L_0x00c2;
    L_0x010c:
        r0 = r27;
        r15 = r0.mUserName;
        r13 = android.text.TextUtils.isEmpty(r15);
        if (r13 != 0) goto L_0x0126;
    L_0x0116:
        goto L_0x011a;
    L_0x0117:
        goto L_0x00aa;
    L_0x011a:
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r0 = r27;
        r15 = r0.mUserName;
        r6.setText(r15);
        goto L_0x00a7;
    L_0x0126:
        r0 = r27;
        r15 = r0.mFaceBookNickName;
        r13 = android.text.TextUtils.isEmpty(r15);
        if (r13 != 0) goto L_0x013c;
    L_0x0130:
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r0 = r27;
        r15 = r0.mFaceBookNickName;
        r6.setText(r15);
        goto L_0x00f5;
    L_0x013c:
        r0 = r27;
        r15 = r0.mNickName;
        r13 = android.text.TextUtils.isEmpty(r15);
        if (r13 != 0) goto L_0x0152;
    L_0x0146:
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        r0 = r27;
        r15 = r0.mNickName;
        r6.setText(r15);
        goto L_0x0109;
    L_0x0152:
        r23 = new com.waze.menus.SideMenuHeaderView$6;
        r0 = r23;
        r1 = r26;
        r0.<init>();
        r24 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r0 = r26;
        r1 = r23;
        r2 = r24;
        r0.postDelayed(r1, r2);
        r0 = r26;
        r6 = r0.mWazeNameLabel;
        goto L_0x016e;
    L_0x016b:
        goto L_0x003c;
    L_0x016e:
        r22 = "";
        r0 = r22;
        r6.setText(r0);
        goto L_0x016b;
        goto L_0x017a;
    L_0x0177:
        goto L_0x004f;
    L_0x017a:
        r13 = 0;
        goto L_0x0177;
    L_0x017c:
        r5 = 8;
        goto L_0x0117;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.SideMenuHeaderView.applyMyWazeData(com.waze.mywaze.MyWazeData):void");
    }

    public SideMenuHeaderView(Context $r1) throws  {
        this($r1, null);
    }

    public SideMenuHeaderView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SideMenuHeaderView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mNewMailMsgs = 0;
        this.mNewFriendsOnline = 0;
        init();
    }

    private void init() throws  {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        LayoutInflater.from(getContext()).inflate(C1283R.layout.main_side_menu_header, this);
        this.mProfileImage = (ImageView) findViewById(C1283R.id.myWazeProfileImage);
        this.mWazeNameLabel = (TextView) findViewById(C1283R.id.myWazeName);
        this.mWazeNameLabel.setText("");
        this.mSettingsButton = (ImageButton) findViewById(C1283R.id.menuSettings);
        this.mShutdownButton = (ImageButton) findViewById(C1283R.id.menuSwitchOff);
        this.mMoodImage = (ImageView) findViewById(C1283R.id.myWazeProfileMood);
        this.mMoodAddonImage = (ImageView) findViewById(C1283R.id.myWazeProfileMoodAddon);
        this.mNewEmails = (WazeTextView) findViewById(C1283R.id.myWazeMail);
        this.mNewEmailsIcon = (ImageView) findViewById(C1283R.id.myWazeMailIcon);
        this.mFriendsOnline = (WazeTextView) findViewById(C1283R.id.myWazeFriends);
        this.mFriendsOnlineIcon = (ImageView) findViewById(C1283R.id.myWazeFriendsIcon);
        this.mMyWazeDetailsContainer = (FrameLayout) findViewById(C1283R.id.myWazeDetailsContainer);
        this.mMyWazeFriendsMailContainer = (LinearLayout) findViewById(C1283R.id.myWazeMailFriendsContainer);
        this.mSep = findViewById(C1283R.id.myWazeDetailsSep);
        this.mMyWazeDetailsContainer.setOnClickListener(new C19481());
        this.mSettingsButton.setOnClickListener(new C19492());
        this.mShutdownButton.setOnClickListener(new C19503());
        this.mProfileImage.setOnClickListener(new C19514());
        TextView $r6 = (TextView) findViewById(C1283R.id.debugVersion);
        if (isInEditMode() || !NativeManager.getInstance().isDebug()) {
            $r6.setVisibility(8);
        } else {
            $r6.setVisibility(0);
            $r6.setText(NativeManager.getInstance().getCoreVersionAndServer());
        }
        InboxNativeManager.getInstance().addDataListener(this);
    }

    public void informProfilePictureChanged() throws  {
        if (this.mImageTaker != null && this.mImageTaker.hasImage()) {
            NativeManager.getInstance().UploadProfileImage(new File(this.mImageTaker.getImagePath()).getAbsolutePath());
            setUserData();
        }
    }

    public void setUserData() throws  {
        MyWazeNativeManager.getInstance().getMyWazeData(new C19535());
        TextView $r4 = (TextView) findViewById(C1283R.id.debugVersion);
        if (isInEditMode() || !NativeManager.getInstance().isDebug()) {
            $r4.setVisibility(8);
            return;
        }
        $r4.setVisibility(0);
        $r4.setText(NativeManager.getInstance().getCoreVersionAndServer());
    }

    private void updateMailFriendsByLine(int $i0, int $i1) throws  {
        if ($i0 != this.mNewMailMsgs) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_MESSAGES_UPDATED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COUNT, (long) $i0).send();
            this.mNewMailMsgs = $i0;
        }
        if ($i1 != this.mNewFriendsOnline) {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_MAIN_MENU_FRIENDS_UPDATED).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_COUNT, (long) $i1).send();
            this.mNewFriendsOnline = $i1;
        }
        if ($i0 > 0 || $i1 > 0) {
            this.mMyWazeFriendsMailContainer.setVisibility(0);
            if ($i0 <= 0 || $i1 <= 0) {
                this.mSep.setVisibility(8);
                if ($i0 > 0) {
                    this.mNewEmails.setVisibility(0);
                    this.mNewEmailsIcon.setVisibility(0);
                    if ($i0 == 1) {
                        this.mNewEmails.setText(DisplayStrings.displayString(DisplayStrings.DS_MAIN_MENU_HEADER_ONE_NEW_MESSAGE));
                    } else {
                        this.mNewEmails.setText(String.format(DisplayStrings.displayString(DisplayStrings.DS_MAIN_MENU_HEADER_NEW_MESSAGES_PD), new Object[]{Integer.valueOf($i0)}));
                    }
                    this.mFriendsOnline.setVisibility(8);
                    this.mFriendsOnlineIcon.setVisibility(8);
                } else {
                    this.mFriendsOnline.setVisibility(0);
                    this.mFriendsOnlineIcon.setVisibility(0);
                    if ($i1 == 1) {
                        this.mFriendsOnline.setText(DisplayStrings.displayString(DisplayStrings.DS_MAIN_MENU_HEADER_ONE_FRIEND_ONLINE));
                    } else {
                        this.mFriendsOnline.setText(String.format(DisplayStrings.displayString(DisplayStrings.DS_MAIN_MENU_HEADER_FRIENDS_ONLINE_PD), new Object[]{Integer.valueOf($i1)}));
                    }
                    this.mNewEmails.setVisibility(8);
                    this.mNewEmailsIcon.setVisibility(8);
                }
            } else {
                this.mFriendsOnline.setText(String.valueOf($i1));
                this.mFriendsOnline.setVisibility(0);
                this.mFriendsOnlineIcon.setVisibility(0);
                this.mNewEmails.setText(String.valueOf($i0));
                this.mNewEmails.setVisibility(0);
                this.mNewEmailsIcon.setVisibility(0);
                this.mSep.setVisibility(0);
            }
        } else {
            this.mMyWazeFriendsMailContainer.setVisibility(8);
        }
        requestLayout();
    }

    private void updateMood() throws  {
        if (MyWazeNativeManager.getInstance().GetInvisible()) {
            this.mMoodImage.setImageResource(C1283R.drawable.invisible);
        } else {
            this.mMoodImage.setImageResource(ResManager.GetDrawableId(MoodManager.getInstance().getWazerMood().replace("-", "_").toLowerCase()));
        }
        int $i0 = MoodManager.getInstance().getBigAddonDrawble(getContext());
        if ($i0 == 0) {
            this.mMoodAddonImage.setVisibility(8);
            return;
        }
        this.mMoodAddonImage.setVisibility(0);
        this.mMoodAddonImage.setImageResource($i0);
    }

    public boolean doesRequireLayout() throws  {
        return this.mRequiresLayout;
    }

    public void setLayoutPerformed() throws  {
        this.mRequiresLayout = false;
    }

    public void onData(InboxMessage[] list, int $i0, int unread, boolean more) throws  {
        updateMailFriendsByLine($i0, this.mNewFriendsOnline);
    }

    public void updateOnlineFriends(int $i0) throws  {
        updateMailFriendsByLine(this.mNewMailMsgs, $i0);
    }
}
