package com.waze.inbox;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.RippleDrawable;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.ifs.ui.WazeCheckBoxView;
import com.waze.settings.SettingsNativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class InboxItemView extends FrameLayout {
    private WazeCheckBoxView mCheckboxButton;
    private TextView mDetailsLabel;
    private ViewGroup mInboxItemViewContainer;
    private InboxItemListener mListener;
    private InboxMessage mModel;
    private TextView mTimestampLabel;
    private TextView mTitleLabel;
    private View mUnreadIndicator;

    class C17701 implements OnClickListener {
        C17701() throws  {
        }

        public void onClick(View v) throws  {
            InboxItemView.this.toggleCheckbox();
        }
    }

    class C17712 implements OnClickListener {
        C17712() throws  {
        }

        public void onClick(View v) throws  {
            if (InboxItemView.this.mListener != null) {
                InboxItemView.this.mListener.onItemSelected(InboxItemView.this.mModel);
            }
        }
    }

    public interface InboxItemListener {
        boolean isChecked(InboxMessage inboxMessage) throws ;

        void onCheckChanged(InboxMessage inboxMessage, boolean z) throws ;

        void onItemSelected(InboxMessage inboxMessage) throws ;
    }

    public InboxItemView(Context $r1) throws  {
        this($r1, null);
    }

    public InboxItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public InboxItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    private void init() throws  {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        View $r4 = LayoutInflater.from(getContext()).inflate(C1283R.layout.inbox_item_view, null);
        this.mCheckboxButton = (WazeCheckBoxView) $r4.findViewById(C1283R.id.inboxItemCheckbox);
        this.mInboxItemViewContainer = (ViewGroup) $r4.findViewById(C1283R.id.inboxItemViewContainer);
        this.mUnreadIndicator = $r4.findViewById(C1283R.id.inboxUnreadIndicator);
        this.mTitleLabel = (TextView) $r4.findViewById(C1283R.id.inboxTitleLabel);
        this.mDetailsLabel = (TextView) $r4.findViewById(C1283R.id.inboxDetailsLabel);
        this.mTimestampLabel = (TextView) $r4.findViewById(C1283R.id.inboxTimeLabel);
        this.mCheckboxButton.setOnClickListener(new C17701());
        setOnClickListener(new C17712());
        if (VERSION.SDK_INT >= 21) {
            this.mInboxItemViewContainer.setBackground(new RippleDrawable(ColorStateList.valueOf(getResources().getColor(C1283R.color.BlueWhaleLight)), getResources().getDrawable(C1283R.drawable.inbox_item_bg, null), null));
            this.mInboxItemViewContainer.setPadding(0, 0, 0, 0);
        }
        int $i0 = PixelMeasure.dp(8);
        setPadding($i0, 0, $i0, $i0);
        setClipToPadding(false);
        addView($r4);
    }

    public void applyFirstItemPadding() throws  {
        int $i0 = PixelMeasure.dp(8);
        setPadding($i0, $i0, $i0, $i0);
    }

    public void applyLastItemPadding() throws  {
        int $i0 = PixelMeasure.dp(80);
        int $i1 = PixelMeasure.dp(8);
        setPadding($i1, $i1, $i1, $i0);
    }

    public void toggleCheckbox() throws  {
        this.mCheckboxButton.toggle();
        if (this.mListener != null) {
            this.mListener.onCheckChanged(this.mModel, this.mCheckboxButton.isChecked());
        }
    }

    public void setListener(InboxItemListener $r1) throws  {
        this.mListener = $r1;
    }

    public void setModel(InboxMessage $r1) throws  {
        this.mModel = $r1;
        setFields();
    }

    public InboxMessage getModel() throws  {
        return this.mModel;
    }

    private void setFields() throws  {
        byte $b1;
        this.mTitleLabel.setText(this.mModel.title);
        this.mDetailsLabel.setText(this.mModel.preview);
        this.mTimestampLabel.setText(formatTime(this.mModel.sentTime * 1000));
        this.mTimestampLabel.setVisibility(TextUtils.isEmpty(this.mModel.sentFString) ? (byte) 8 : (byte) 0);
        View $r4 = this.mUnreadIndicator;
        if (this.mModel.unread) {
            $b1 = (byte) 0;
        } else {
            $b1 = (byte) 8;
        }
        $r4.setVisibility($b1);
        int $i2 = PixelMeasure.dp(8);
        setPadding($i2, 0, $i2, $i2);
        if (this.mListener != null) {
            this.mCheckboxButton.setValue(this.mListener.isChecked(this.mModel));
        }
    }

    private String formatTime(long $l0) throws  {
        SimpleDateFormat $r7;
        Calendar $r2 = Calendar.getInstance();
        $r2.setTimeInMillis($l0);
        TimeZone $r3 = $r2.getTimeZone();
        Locale $r1 = new Locale(SettingsNativeManager.getInstance().getLanguagesLocaleNTV());
        if (!DateUtils.isToday($l0)) {
            Calendar $r9 = Calendar.getInstance();
            $r9.add(5, -1);
            if ($r9.get(1) == $r2.get(1) && $r9.get(2) == $r2.get(2) && $r9.get(5) == $r2.get(5)) {
                return DisplayStrings.displayString(DisplayStrings.DS_YESTERDAY);
            }
            $r7 = new SimpleDateFormat("d MMMM", $r1);
        } else if (NativeManager.getInstance().is24HrClock()) {
            $r7 = new SimpleDateFormat("HH:mm");
        } else {
            $r7 = new SimpleDateFormat("h:mm a");
        }
        $r7.setTimeZone($r3);
        return $r7.format(new Date($l0));
    }
}
