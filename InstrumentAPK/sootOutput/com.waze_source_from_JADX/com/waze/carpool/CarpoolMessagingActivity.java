package com.waze.carpool;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.abaltatech.mcp.weblink.core.WLTypes;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ChatNotificationManager;
import com.waze.ChatNotificationManager.ChatHandler;
import com.waze.ConfigManager;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolNativeManager.CarpoolMessagesReceiveCompleteListener;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.map.CanvasFont;
import com.waze.settings.SettingsNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.anim.AnimationUtils.AnimationEndListener;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.WazeEditText;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

public class CarpoolMessagingActivity extends ActivityBase implements ChatHandler, CarpoolMessagesReceiveCompleteListener {
    static final int MAX_SCROLL = PixelMeasure.dp(100);
    private static final int REQ_SHARE_LOC = 1001;
    private MessagingListAdapter mAdapter;
    private CarpoolNativeManager mCnm;
    private DateFormat mDateFormat;
    private boolean mIsPaused = true;
    private boolean mKeyboardIsOpen = false;
    private RecyclerView mList;
    private MyLLM mLlm;
    private ArrayList<ListItem> mMessageList;
    private NativeManager mNm;
    private boolean mReadonly = false;
    private CarpoolRide mRide;
    private CarpoolUserData mRider;
    private boolean mSending = false;
    private boolean mShouldMarkAsReadOnResume = false;
    private boolean mShouldOpenOverflowOnKeyboardClose = false;
    private boolean mStackFromEndWhenKeyboardIsClosed = false;
    private boolean mStackFromEndWhenKeyboardIsOpen = false;
    private DateFormat mTimeFormat;

    class C14311 implements OnClickListener {
        C14311() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_OPTION).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_OPTIONS).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolMessagingActivity.this.mRide.getId()).send();
            if (CarpoolMessagingActivity.this.mKeyboardIsOpen) {
                EditTextUtils.closeKeyboard(CarpoolMessagingActivity.this, CarpoolMessagingActivity.this.findViewById(C1283R.id.messagingInput));
                CarpoolMessagingActivity.this.mShouldOpenOverflowOnKeyboardClose = true;
                return;
            }
            CarpoolMessagingActivity.this.showOverflowMenu();
        }
    }

    class C14376 implements OnClickListener {
        C14376() throws  {
        }

        public void onClick(View v) throws  {
            EditTextUtils.closeKeyboard(CarpoolMessagingActivity.this, CarpoolMessagingActivity.this.mList);
        }
    }

    class C14408 implements Runnable {
        C14408() throws  {
        }

        public void run() throws  {
            CarpoolMessagingActivity.this.mCnm.requestRideChatMessages(CarpoolMessagingActivity.this.mRide.getId());
        }
    }

    interface ListItem {
        String getText() throws ;

        long getTime() throws ;

        int getType() throws ;

        void populate(View view) throws ;
    }

    abstract class TimedListItem implements ListItem {
        private final long mTimeSec;

        public String getText() throws  {
            return null;
        }

        public TimedListItem(long $l0) throws  {
            this.mTimeSec = $l0;
        }

        public long getTime() throws  {
            return this.mTimeSec;
        }
    }

    class ListItemDate extends TimedListItem {
        private String mLabel;

        public int getType() throws  {
            return C1283R.layout.carpool_messaging_date;
        }

        ListItemDate(long $l0) throws  {
            super($l0);
            Calendar $r2 = Calendar.getInstance();
            Calendar $r3 = Calendar.getInstance();
            $r3.setTimeInMillis($l0 * 1000);
            if ($r2.get(5) == $r3.get(5)) {
                this.mLabel = CarpoolMessagingActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_TODAY_CAP);
            } else if ($r2.get(5) - $r3.get(5) == 1) {
                this.mLabel = CarpoolMessagingActivity.this.mNm.getLanguageString((int) DisplayStrings.DS_YESTERDAY);
            } else {
                long $l02 = $l0 * 1000;
                $l0 = $l02;
                this.mLabel = CarpoolMessagingActivity.this.mDateFormat.format(Long.valueOf($l02));
            }
        }

        public void populate(View $r1) throws  {
            ((TextView) $r1.findViewById(C1283R.id.messagingDate)).setText(this.mLabel);
        }
    }

    abstract class ListItemMessage extends TimedListItem {
        private final String mMessage;
        private final String mTime;

        class C14441 implements OnLongClickListener {
            C14441() throws  {
            }

            public boolean onLongClick(View v) throws  {
                ((ClipboardManager) CarpoolMessagingActivity.this.getSystemService("clipboard")).setPrimaryClip(ClipData.newPlainText(ListItemMessage.this.mMessage, ListItemMessage.this.mMessage));
                Toast.makeText(CarpoolMessagingActivity.this, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MESSAGING_COPIED_TO_CLIPBOARD), 0).show();
                return true;
            }
        }

        public ListItemMessage(String $r2, long $l0) throws  {
            super($l0);
            this.mMessage = $r2;
            if ($l0 > 0) {
                this.mTime = CarpoolMessagingActivity.this.mTimeFormat.format(Long.valueOf(1000 * $l0));
            } else {
                this.mTime = CarpoolMessagingActivity.this.mNm.getLanguageString(0);
            }
        }

        public void populate(View $r1) throws  {
            ((TextView) $r1.findViewById(C1283R.id.messagingMessage)).setText(this.mMessage);
            ((TextView) $r1.findViewById(C1283R.id.messagingTime)).setText(this.mTime);
            $r1.setOnLongClickListener(new C14441());
        }

        public String getText() throws  {
            return this.mMessage;
        }
    }

    class ListItemDriverMessage extends ListItemMessage {
        final String mId;
        boolean mWasSeen;

        public int getType() throws  {
            return C1283R.layout.carpool_messaging_driver;
        }

        public ListItemDriverMessage(String $r2, long $l0, boolean $z0, String $r3) throws  {
            super($r2, $l0);
            this.mWasSeen = $z0;
            this.mId = $r3;
        }

        public void populate(View $r1) throws  {
            super.populate($r1);
            TextView $r2 = (TextView) $r1.findViewById(C1283R.id.messagingSeen);
            $r2.setText(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MESSAGING_SEEN));
            $r2.setVisibility(this.mWasSeen ? (byte) 0 : (byte) 8);
        }

        boolean isMatchingId(String $r1) throws  {
            return $r1.contentEquals(this.mId);
        }

        public void markAsSeen(View $r1) throws  {
            this.mWasSeen = true;
            if ($r1 != null) {
                $r1.findViewById(C1283R.id.messagingSeen).setVisibility(0);
            }
        }
    }

    class ListItemHeader implements ListItem {
        private CarpoolRide mRide;
        private CarpoolUserData mRider;

        class C14432 implements OnClickListener {
            C14432() throws  {
            }

            public void onClick(View v) throws  {
                Intent $r2 = new Intent(CarpoolMessagingActivity.this, CarpoolRiderProfileActivity.class);
                $r2.putExtra("CarpoolUserData", ListItemHeader.this.mRider);
                $r2.putExtra("CarpoolRide", ListItemHeader.this.mRide);
                $r2.setFlags(131072);
                CarpoolMessagingActivity.this.startActivity($r2);
            }
        }

        public String getText() throws  {
            return null;
        }

        public long getTime() throws  {
            return -1;
        }

        public int getType() throws  {
            return C1283R.layout.carpool_messaging_header;
        }

        ListItemHeader(CarpoolUserData $r2, CarpoolRide $r3) throws  {
            this.mRider = $r2;
            this.mRide = $r3;
        }

        public void populate(View $r1) throws  {
            ((TextView) $r1.findViewById(C1283R.id.messagingHeaderName)).setText(this.mRider.getName());
            String $r5 = CarpoolMessagingActivity.this.mDateFormat.format(new Date(this.mRide.itinerary.window_start_time * 1000));
            ((TextView) $r1.findViewById(C1283R.id.messagingHeaderPickupDate)).setText(CarpoolMessagingActivity.this.mNm.getFormattedString(DisplayStrings.DS_MESSAGING_PICKUP_FORMAT, $r5));
            if (this.mRide.state == 10 || this.mRide.state == 7 || this.mRide.state == 16 || this.mRide.state == 8 || this.mRide.state == 4) {
                $r5 = CarpoolMessagingActivity.this.mTimeFormat.format(new Date(this.mRide.getTime() * 1000));
            } else {
                NativeManager $r11 = CarpoolMessagingActivity.this.mNm;
                Object[] $r12 = new Object[2];
                $r12[0] = CarpoolMessagingActivity.this.mTimeFormat.format(new Date(this.mRide.itinerary.window_start_time * 1000));
                DateFormat $r7 = CarpoolMessagingActivity.this.mTimeFormat;
                long $l0 = this.mRide.itinerary.window_start_time;
                long $l2 = this.mRide.itinerary.window_duration_seconds;
                int $i1 = $l2;
                $l2 = (long) $l2;
                $r12[1] = $r7.format(new Date(($l0 + $l2) * 1000));
                $r5 = $r11.getFormattedString(DisplayStrings.DS_RIDE_ITEM_RANGE, $r12);
            }
            ((TextView) $r1.findViewById(C1283R.id.messagingHeaderPickupTimes)).setText($r5);
            final View view = $r1;
            VolleyManager.getInstance().loadImageFromUrl(this.mRider.getImage(), new ImageRequestListener() {
                public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
                    ((ImageView) view.findViewById(C1283R.id.messagingHeaderPhoto)).setImageDrawable(new CircleShaderDrawable($r1, 0));
                }

                public void onImageLoadFailed(Object token, long duration) throws  {
                }
            });
            $r1.setOnClickListener(new C14432());
        }
    }

    class ListItemRiderMessage extends ListItemMessage {
        public int getType() throws  {
            return C1283R.layout.carpool_messaging_rider;
        }

        public ListItemRiderMessage(String $r2, long $l0) throws  {
            super($r2, $l0);
        }
    }

    class ListItemStatus extends TimedListItem {
        private String mStatus;

        public int getType() throws  {
            return C1283R.layout.carpool_messaging_status;
        }

        ListItemStatus(String $r2, long $l0) throws  {
            super($l0);
            this.mStatus = $r2 + " " + CarpoolMessagingActivity.this.mTimeFormat.format(Long.valueOf(1000 * $l0));
        }

        public void populate(View $r1) throws  {
            ((TextView) $r1.findViewById(C1283R.id.messagingStatus)).setText(this.mStatus);
        }
    }

    class LoaderItem implements ListItem {
        public String getText() throws  {
            return null;
        }

        public long getTime() throws  {
            return Long.MAX_VALUE;
        }

        public int getType() throws  {
            return C1283R.layout.carpool_messaging_loader;
        }

        LoaderItem() throws  {
        }

        public void populate(View $r1) throws  {
            ((ProgressAnimation) $r1.findViewById(C1283R.id.carpoolMessagingLoaderAnimation)).start();
        }
    }

    class MessagingListAdapter extends Adapter {
        private final Context mContext;
        private LayoutInflater mInflater;
        ArrayList<ListItem> mItems;
        public int mLastPosition = 0;

        class Holder extends ViewHolder {
            public View view;

            public Holder(View $r2) throws  {
                super($r2);
                this.view = $r2;
            }
        }

        public MessagingListAdapter(@Signature({"(", "Landroid/content/Context;", "Ljava/util/ArrayList", "<", "Lcom/waze/carpool/CarpoolMessagingActivity$ListItem;", ">;)V"}) Context $r2, @Signature({"(", "Landroid/content/Context;", "Ljava/util/ArrayList", "<", "Lcom/waze/carpool/CarpoolMessagingActivity$ListItem;", ">;)V"}) ArrayList<ListItem> $r3) throws  {
            this.mItems = $r3;
            this.mContext = $r2;
            this.mLastPosition = $r3.size() - 1;
            this.mInflater = (LayoutInflater) this.mContext.getSystemService("layout_inflater");
        }

        void update(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/carpool/CarpoolMessagingActivity$ListItem;", ">;)V"}) ArrayList<ListItem> $r1) throws  {
            this.mItems = $r1;
            notifyDataSetChanged();
        }

        public ViewHolder onCreateViewHolder(ViewGroup $r1, int $i0) throws  {
            return new Holder(this.mInflater.inflate($i0, $r1, false));
        }

        public void onBindViewHolder(ViewHolder $r1, int $i0) throws  {
            Holder $r3 = (Holder) $r1;
            ((ListItem) this.mItems.get($i0)).populate($r3.view);
            if ($i0 > this.mLastPosition) {
                this.mLastPosition = $i0;
                AnimationSet $r2 = new AnimationSet(false);
                $r2.addAnimation(new AlphaAnimation(0.0f, 1.0f));
                $r2.addAnimation(new TranslateAnimation(0.0f, 0.0f, (float) PixelMeasure.dp(100), 0.0f));
                $r2.setDuration(500);
                $r3.view.startAnimation($r2);
            }
        }

        public int getItemCount() throws  {
            return this.mItems.size();
        }

        public int getItemViewType(int $i0) throws  {
            return ((ListItem) this.mItems.get($i0)).getType();
        }
    }

    class MyLLM extends LinearLayoutManager {
        public int yPosition = 0;

        public MyLLM(Context $r2) throws  {
            super($r2);
        }

        public int scrollVerticallyBy(int $i0, Recycler $r1, State $r2) throws  {
            $i0 = super.scrollVerticallyBy($i0, $r1, $r2);
            updateShadow();
            return $i0;
        }

        private void updateShadow() throws  {
            float $f0 = 0.0f;
            if (CarpoolMessagingActivity.this.mLlm.findFirstVisibleItemPosition() > 0) {
                $f0 = 1.0f;
            } else {
                View $r5 = CarpoolMessagingActivity.this.mList.findChildViewUnder(0.0f, 0.0f);
                if ($r5 != null) {
                    Rect $r1 = new Rect();
                    $r5.getGlobalVisibleRect($r1);
                    int $i1 = $r1.height();
                    int $i0 = $i1;
                    if ($i1 > CarpoolMessagingActivity.MAX_SCROLL) {
                        $i0 = CarpoolMessagingActivity.MAX_SCROLL;
                    }
                    $f0 = (((float) (CarpoolMessagingActivity.MAX_SCROLL - $i0)) * 1.0f) / ((float) CarpoolMessagingActivity.MAX_SCROLL);
                }
            }
            CarpoolMessagingActivity.this.animateShadow($f0);
        }

        public void onLayoutChildren(Recycler $r1, State $r2) throws  {
            super.onLayoutChildren($r1, $r2);
            updateShadow();
            boolean $z0 = getStackFromEnd();
            if ($z0) {
                if (!CarpoolMessagingActivity.this.mKeyboardIsOpen || !CarpoolMessagingActivity.this.mStackFromEndWhenKeyboardIsOpen) {
                    if (!CarpoolMessagingActivity.this.mKeyboardIsOpen && CarpoolMessagingActivity.this.mStackFromEndWhenKeyboardIsClosed) {
                        return;
                    }
                }
                return;
            }
            int $i0 = 0;
            for (int $i1 = 0; $i1 < getItemCount(); $i1++) {
                if (getChildAt($i1) != null) {
                    $i0 += getChildAt($i1).getHeight();
                }
            }
            final boolean $z1 = $i0 > getHeight();
            if ($z1 != $z0) {
                if (CarpoolMessagingActivity.this.mKeyboardIsOpen) {
                    CarpoolMessagingActivity.this.mStackFromEndWhenKeyboardIsOpen = $z1;
                } else {
                    CarpoolMessagingActivity.this.mStackFromEndWhenKeyboardIsClosed = $z1;
                }
                AppService.getActiveActivity().post(new Runnable() {
                    public void run() throws  {
                        MyLLM.this.setStackFromEnd($z1);
                    }
                });
            }
        }
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        setContentView(C1283R.layout.carpool_messaging);
        this.mNm = NativeManager.getInstance();
        this.mCnm = CarpoolNativeManager.getInstance();
        this.mRider = (CarpoolUserData) getIntent().getParcelableExtra("rider");
        this.mRide = (CarpoolRide) getIntent().getParcelableExtra("ride");
        if (this.mRider == null || this.mRide == null) {
            Logger.m38e("CarpoolMessagingActivity called without rider or ride extras");
            finish();
            return;
        }
        boolean $z0 = getIntent().getBooleanExtra("openKeyboard", false);
        int $i0 = ConfigManager.getInstance().getConfigValueInt(39);
        if (this.mRide.state == 2 || this.mRide.state == 3 || this.mRide.state == 15) {
            if (this.mRide.getTime() + ((long) (($i0 * 60) * 60)) < new Date().getTime() / 1000) {
                this.mReadonly = true;
            }
        }
        ChatNotificationManager.getInstance(true).setChatUpdateHandler(this.mRide.getId(), this);
        this.mTimeFormat = DateFormat.getTimeInstance(3, new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV()));
        this.mDateFormat = new SimpleDateFormat("EEE, MMM dd");
        TitleBar $r18 = (TitleBar) findViewById(C1283R.id.theTitleBar);
        $r18.init(this, this.mNm.getFormattedString(DisplayStrings.DS_MESSAGING_TITLE_DS, this.mRider.getName()));
        $r18.setCloseVisibility(false);
        $r18.setButtonTwo(C1283R.drawable.carpool_fullscreen_overflow, null, new C14311());
        $i0 = getResources().getDisplayMetrics().heightPixels;
        View $r17 = findViewById(C1283R.id.messagingRootView);
        final View view = $r17;
        final int i = $i0;
        $r17.getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

            class C14321 implements Runnable {
                C14321() throws  {
                }

                public void run() throws  {
                    CarpoolMessagingActivity.this.mAdapter.notifyDataSetChanged();
                }
            }

            public void onGlobalLayout() throws  {
                int $i0 = view.getMeasuredHeight();
                boolean $z0 = CarpoolMessagingActivity.this.mKeyboardIsOpen;
                CarpoolMessagingActivity.this.mKeyboardIsOpen = $i0 < (i * 90) / 100;
                if ($z0 != CarpoolMessagingActivity.this.mKeyboardIsOpen) {
                    CarpoolMessagingActivity.this.mList.post(new C14321());
                    if (CarpoolMessagingActivity.this.mShouldOpenOverflowOnKeyboardClose) {
                        CarpoolMessagingActivity.this.mShouldOpenOverflowOnKeyboardClose = false;
                        CarpoolMessagingActivity.this.showOverflowMenu();
                    }
                }
            }
        });
        if (this.mReadonly) {
            findViewById(C1283R.id.messagingInputLayout).setVisibility(8);
        } else {
            final WazeEditText wazeEditText;
            WazeEditText $r29 = (WazeEditText) findViewById(C1283R.id.messagingInput);
            ImageView $r30 = (ImageView) findViewById(C1283R.id.messagingSend);
            $r30.setEnabled(false);
            $r29.setHint(this.mNm.getLanguageString((int) DisplayStrings.DS_CARPOOL_MESSAGING_NEW_MESSAGE_HINT));
            final ImageView imageView = $r30;
            $r29.addTextChangedListener(new TextWatcher() {
                public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
                }

                public void onTextChanged(CharSequence $r1, int start, int before, int count) throws  {
                    if ($r1.length() <= 0 || CarpoolMessagingActivity.this.mSending) {
                        imageView.setEnabled(false);
                    } else {
                        imageView.setEnabled(true);
                    }
                }

                public void afterTextChanged(Editable s) throws  {
                }
            });
            if ($z0) {
                wazeEditText = $r29;
                $r29.postDelayed(new Runnable() {
                    public void run() throws  {
                        EditTextUtils.openKeyboard(CarpoolMessagingActivity.this, wazeEditText);
                    }
                }, 500);
            }
            wazeEditText = $r29;
            final ImageView imageView2 = $r30;
            $r30.setOnClickListener(new OnClickListener() {
                public void onClick(View v) throws  {
                    if (wazeEditText.getText().length() > 0 && !CarpoolMessagingActivity.this.mSending) {
                        CarpoolMessagingActivity.this.mSending = true;
                        String $r5 = wazeEditText.getText().toString();
                        wazeEditText.setText("");
                        imageView2.setEnabled(false);
                        CarpoolMessagingActivity.this.mMessageList.add(new ListItemDriverMessage($r5, -1, false, null));
                        CarpoolMessagingActivity.this.mAdapter.notifyDataSetChanged();
                        CarpoolMessagingActivity.this.mList.scrollToPosition(CarpoolMessagingActivity.this.mMessageList.size() - 1);
                        CarpoolMessagingActivity.this.mCnm.sendChatMessage(CarpoolMessagingActivity.this.mRide.getId(), $r5);
                    }
                }
            });
        }
        this.mMessageList = new ArrayList();
        this.mAdapter = new MessagingListAdapter(this, this.mMessageList);
        this.mList = (RecyclerView) findViewById(C1283R.id.messagingListView);
        this.mList.setAdapter(this.mAdapter);
        this.mCnm.requestRideChatMessages(this.mRide.getId());
        this.mCnm.getCarpoolRideMessages(this.mRide, this);
        this.mLlm = new MyLLM(this);
        this.mList.setLayoutManager(this.mLlm);
        this.mList.setOnClickListener(new C14376());
        animateShadow(0.0f);
    }

    private void showOverflowMenu() throws  {
        $r2 = new String[2];
        $r2[0] = DisplayStrings.displayStringF(DisplayStrings.DS_CARPOOL_MESSAGING_MENU_REPORT_PS, new Object[]{this.mRider.getFirstName()});
        $r2[1] = DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MESSAGING_MENU_CLEAR_HISTORY);
        final BottomSheet $r1 = new BottomSheet(this, DisplayStrings.DS_CARPOOL_REPORT_USER_OPTIONS_TITLE, Mode.COLUMN_TEXT);
        $r1.setAdapter(new BottomSheet.Adapter() {

            class C14381 implements DialogInterface.OnClickListener {
                C14381() throws  {
                }

                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        CarpoolMessagingActivity.this.mCnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CLEAR_CHAT_RES, CarpoolMessagingActivity.this.mHandler);
                        CarpoolMessagingActivity.this.mNm.OpenProgressPopup(DisplayStrings.displayString(290));
                        CarpoolMessagingActivity.this.mCnm.clearChatHistory(CarpoolMessagingActivity.this.mRide);
                        CarpoolMessagingActivity.this.mStackFromEndWhenKeyboardIsOpen = false;
                        CarpoolMessagingActivity.this.mStackFromEndWhenKeyboardIsClosed = false;
                    }
                }
            }

            public int getCount() throws  {
                return $r2.length;
            }

            public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                $r1.setItem($r2[$i0]);
            }

            public void onClick(int $i0) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_OPTION).addParam("ACTION", (long) $i0).send();
                $r1.dismiss();
                switch ($i0) {
                    case 0:
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_OPTION).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_REPORT_RIDER).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolMessagingActivity.this.mRide.getId()).send();
                        CarpoolMessagingActivity $r3 = CarpoolMessagingActivity.this;
                        CarpoolMessagingActivity $r6 = CarpoolMessagingActivity.this;
                        CarpoolRide $r4 = $r6.mRide;
                        $r6 = CarpoolMessagingActivity.this;
                        CarpoolRiderProfileActivity.selectReport($r3, $r4, $r6.mRider);
                        return;
                    case 1:
                        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_REPORT_USER_OPTION).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CLEAR_CHAT_HISTORY).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDE_ID, CarpoolMessagingActivity.this.mRide.getId()).send();
                        MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava(CarpoolMessagingActivity.this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_DIALOG_CLEAR_HISTORY_TITLE), CarpoolMessagingActivity.this.mNm.getFormattedString(DisplayStrings.DS_CARPOOL_DIALOG_CLEAR_HISTORY_TEXT_PS, CarpoolMessagingActivity.this.mRider.getName()), false, new C14381(), CarpoolMessagingActivity.this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_DIALOG_CLEAR_HISTORY_YES), CarpoolMessagingActivity.this.mNm.getLanguageString(DisplayStrings.DS_CARPOOL_DIALOG_CLEAR_HISTORY_NO), -1);
                        return;
                    default:
                        return;
                }
            }
        });
        $r1.show();
    }

    protected void onDestroy() throws  {
        if (this.mRide != null) {
            ChatNotificationManager.getInstance(true).unsetChatUpdateHandler(this.mRide.getId(), this);
        }
        super.onDestroy();
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i0 == 1001 && $i1 == -1 && $r1 != null && $r1.hasExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LONGITUDE)) {
            $r1.getIntExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LONGITUDE, 0);
            $r1.getIntExtra(WLTypes.VEHICLEDATA_ATTRIBUTE_LATITUDE, 0);
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    private void animateShadow(float $f0) throws  {
        findViewById(C1283R.id.messagingShadow).setAlpha($f0);
    }

    public boolean onChatMessage(String message) throws  {
        post(new C14408());
        return isVisible();
    }

    public void onMessagesLoaded() throws  {
        this.mCnm.getCarpoolRideMessages(this.mRide, this);
    }

    public void onMessageSent(final boolean $z0) throws  {
        post(new Runnable() {
            public void run() throws  {
                CarpoolMessagingActivity.this.mSending = false;
                WazeEditText $r3 = (WazeEditText) CarpoolMessagingActivity.this.findViewById(C1283R.id.messagingInput);
                if (!$z0) {
                    MessagingListAdapter $r4 = CarpoolMessagingActivity.this.mAdapter;
                    $r4.mLastPosition--;
                    MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
                    $r3.setText(((ListItem) CarpoolMessagingActivity.this.mMessageList.get(CarpoolMessagingActivity.this.mMessageList.size() - 1)).getText());
                    CarpoolMessagingActivity.this.mMessageList.remove(CarpoolMessagingActivity.this.mMessageList.size() - 1);
                    this = this;
                    CarpoolMessagingActivity.this.mAdapter.notifyDataSetChanged();
                }
                if ($r3.getText().length() > 0) {
                    C14419 $r1 = this;
                    this = $r1;
                    ((ImageView) CarpoolMessagingActivity.this.findViewById(C1283R.id.messagingSend)).setEnabled(true);
                }
            }
        });
    }

    public void onMessageRead(String $r1) throws  {
        int $i0 = this.mMessageList.size() - 1;
        while ($i0 >= 0) {
            ListItem $r4 = (ListItem) this.mMessageList.get($i0);
            if ($r4.getType() == C1283R.layout.carpool_messaging_driver && ($r4 instanceof ListItemDriverMessage)) {
                ListItemDriverMessage $r5 = (ListItemDriverMessage) $r4;
                if ($r5.isMatchingId($r1)) {
                    int $i1 = this.mLlm.findLastVisibleItemPosition();
                    int $i2 = this.mLlm.findFirstVisibleItemPosition();
                    if ($i1 < $i0 || $i2 > $i0) {
                        $r5.markAsSeen(null);
                        return;
                    } else {
                        $r5.markAsSeen(this.mLlm.findViewByPosition($i0));
                        return;
                    }
                }
            }
            $i0--;
        }
    }

    private void removeLoader(final Runnable $r1) throws  {
        post(new Runnable() {

            class C14301 extends AnimationEndListener {
                C14301() throws  {
                }

                public void onAnimationEnd(Animation animation) throws  {
                    MessagingListAdapter $r4 = CarpoolMessagingActivity.this.mAdapter;
                    $r4.mLastPosition--;
                    CarpoolMessagingActivity.this.mMessageList.remove(CarpoolMessagingActivity.this.mMessageList.size() - 1);
                    $r1.run();
                }
            }

            public void run() throws  {
                int $i0 = CarpoolMessagingActivity.this.mMessageList.size() - 1;
                if ($i0 < 0 || ((ListItem) CarpoolMessagingActivity.this.mMessageList.get($i0)).getType() != C1283R.layout.carpool_messaging_loader) {
                    Runnable $r14 = $r1;
                    $r14.run();
                    return;
                }
                Animation animation = r0;
                Animation animationSet = new AnimationSet(true);
                animation.addAnimation(new AlphaAnimation(1.0f, 0.0f));
                animation.addAnimation(new RotateAnimation(0.0f, 180.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                animation.addAnimation(new ScaleAnimation(1.0f, 0.0f, 1.0f, 0.0f, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR, 1, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR));
                animation.setDuration(300);
                animation.setAnimationListener(new C14301());
                if (CarpoolMessagingActivity.this.mLlm.findLastVisibleItemPosition() == $i0) {
                    CarpoolMessagingActivity.this.mList.getChildAt(CarpoolMessagingActivity.this.mList.getChildCount() - 1).startAnimation(animation);
                    return;
                }
                $r14 = $r1;
                $r14.run();
            }
        });
    }

    public void onComplete(final CarpoolRideMessages $r1) throws  {
        removeLoader(new Runnable() {
            public void run() throws  {
                CarpoolMessagingActivity.this.buildMessageList($r1);
                CarpoolMessagingActivity.this.mList.scrollToPosition(CarpoolMessagingActivity.this.mMessageList.size() - 1);
            }
        });
    }

    private void buildMessageList(CarpoolRideMessages $r1) throws  {
        int $i1;
        int $i0;
        this.mMessageList.clear();
        this.mMessageList.add(new ListItemHeader(this.mRider, this.mRide));
        CarpoolRideState[] $r11 = this.mCnm.getStateHistory(this.mRide);
        if ($r11 != null) {
            for (CarpoolRideState $r5 : $r11) {
                addStateItem($r5);
            }
        }
        if (!($r1 == null || $r1.messages == null)) {
            CarpoolMessage[] $r12 = $r1.messages;
            if ($r12.length > 0) {
                $r12 = $r1.messages;
                CarpoolMessage[] $r122 = $r12;
                $i0 = $r12.length;
                for ($i1 = 0; $i1 < $i0; $i1++) {
                    CarpoolMessage $r4 = $r122[$i1];
                    if ($r4.from_me) {
                        boolean $z0;
                        ArrayList $r6 = this.mMessageList;
                        String $r14 = $r4.text;
                        long $l2 = $r4.sent_seconds;
                        if ($r4.unread) {
                            $z0 = false;
                        } else {
                            $z0 = true;
                        }
                        $r6.add(new ListItemDriverMessage($r14, $l2, $z0, $r4.id));
                    } else {
                        this.mMessageList.add(new ListItemRiderMessage($r4.text, $r4.sent_seconds));
                    }
                }
            }
        }
        HashMap hashMap = new HashMap();
        Iterator $r16 = this.mMessageList.iterator();
        while ($r16.hasNext()) {
            $l2 = ((ListItem) $r16.next()).getTime();
            if ($l2 != -1) {
                Calendar $r19 = Calendar.getInstance();
                $r19.setTimeInMillis(1000 * $l2);
                $r19.set(10, 0);
                $r19.set(12, 0);
                $r19.set(13, 0);
                if (hashMap.get($r19) == null) {
                    hashMap.put($r19, new ListItemDate($r19.getTimeInMillis() / 1000));
                }
            }
        }
        this.mMessageList.addAll(hashMap.values());
        Collections.sort(this.mMessageList, new Comparator<ListItem>() {
            public int compare(ListItem $r1, ListItem $r2) throws  {
                return (int) ($r1.getTime() - $r2.getTime());
            }
        });
        MessagingListAdapter $r23 = this.mAdapter;
        $r23.notifyDataSetChanged();
        if (this.mIsPaused) {
            this.mShouldMarkAsReadOnResume = true;
            return;
        }
        CarpoolNativeManager $r10 = this.mCnm;
        String $r2 = this.mRide.getId();
        $r6 = this.mMessageList;
        ArrayList $r24 = this.mMessageList;
        $r10.markCarpoolRideMessagesRead($r2, ((ListItem) $r6.get($r24.size() - 1)).getTime());
    }

    protected void onResume() throws  {
        super.onResume();
        this.mIsPaused = false;
        ChatNotificationManager.getInstance(true).hideNotifications(this.mRide.getId());
        if (this.mShouldMarkAsReadOnResume) {
            this.mShouldMarkAsReadOnResume = false;
            this.mCnm.markCarpoolRideMessagesRead(this.mRide.getId(), ((ListItem) this.mMessageList.get(this.mMessageList.size() - 1)).getTime());
        }
    }

    protected void onPause() throws  {
        this.mIsPaused = true;
        super.onPause();
    }

    private void addStateItem(CarpoolRideState $r1) throws  {
        ListItem $r2 = null;
        if ($r1.entry == 7) {
            $r2 = getStateItem(DisplayStrings.DS_CARPOOL_MESSAGING_ACCEPTED_RIDE, $r1.time);
        } else if ($r1.entry == 15) {
            $r2 = getStateItem(DisplayStrings.DS_CARPOOL_MESSAGING_ANOTHER_DRIVER_ACCEPTED, $r1.time);
        } else if ($r1.entry == 10 || $r1.entry == 16) {
            $r2 = r8;
            r8 = new ListItemStatus(this.mNm.getFormattedString(DisplayStrings.DS_CARPOOL_MESSAGING_STARTED_DRIVING, this.mRider.getName()), $r1.time);
        } else if ($r1.entry == 8) {
            $r2 = r8;
            r8 = new ListItemStatus(this.mNm.getFormattedString(DisplayStrings.DS_CARPOOL_MESSAGING_PICKED_UP_RIDER_PS, this.mRider.getName()), $r1.time);
        } else if ($r1.entry == 9) {
            $r2 = r8;
            r8 = new ListItemStatus(this.mNm.getFormattedString(DisplayStrings.DS_CARPOOL_MESSAGING_DROPPED_OFF_RIDER_PS, this.mRider.getName()), $r1.time);
        }
        if ($r2 != null) {
            this.mMessageList.add($r2);
        }
    }

    private ListItem getStateItem(int $i0, long $l1) throws  {
        return new ListItemStatus(this.mNm.getLanguageString($i0), $l1);
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what != CarpoolNativeManager.UH_CARPOOL_CLEAR_CHAT_RES) {
            return super.myHandleMessage($r1);
        }
        this.mNm.CloseProgressPopup();
        this.mCnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_CLEAR_CHAT_RES, this.mHandler);
        if ($r1.getData().getInt("res") == 0) {
            final NativeManager $r3 = NativeManager.getInstance();
            $r3.OpenProgressIconPopup($r3.getLanguageString((int) DisplayStrings.DS_CARPOOL_DIALOG_CLEAR_HISTORY_DONE), "sign_up_big_v");
            postDelayed(new Runnable() {
                public void run() throws  {
                    $r3.CloseProgressPopup();
                }
            }, 1000);
        } else {
            MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) throws  {
                }
            });
        }
        return true;
    }
}
