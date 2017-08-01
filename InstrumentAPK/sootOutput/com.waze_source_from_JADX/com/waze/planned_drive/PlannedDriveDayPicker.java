package com.waze.planned_drive;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.State;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.text.WazeTextView;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PlannedDriveDayPicker extends FrameLayout {
    public static String[] dayNames;
    private DayPickerListener mListener;
    private RecyclerView mRecyclerView;
    private List<String> mValues;

    public interface DayPickerListener {
        void onDayPicked(int i, String str);
    }

    class C23321 implements OnClickListener {
        C23321() {
        }

        public void onClick(View v) {
            PlannedDriveDayPicker.this.hide();
        }
    }

    private class DayPickerAdapter extends Adapter<DayPickerViewHolder> {
        private DayPickerAdapter() {
        }

        public DayPickerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            WazeTextView textView = new WazeTextView(PlannedDriveDayPicker.this.getContext());
            LayoutParams params = new LayoutParams(-1, -2);
            params.leftMargin = PixelMeasure.dp(16);
            params.bottomMargin = PixelMeasure.dp(16);
            params.topMargin = PixelMeasure.dp(16);
            textView.setLayoutParams(params);
            textView.setTextSize(20.0f);
            textView.setSingleLine(true);
            textView.setMaxLines(1);
            textView.setTextColor(PlannedDriveDayPicker.this.getResources().getColor(C1283R.color.Light));
            return new DayPickerViewHolder(textView);
        }

        public void onBindViewHolder(final DayPickerViewHolder holder, final int position) {
            holder.setText((String) PlannedDriveDayPicker.this.mValues.get(position));
            holder.itemView.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    if (PlannedDriveDayPicker.this.mListener != null) {
                        PlannedDriveDayPicker.this.mListener.onDayPicked(position, holder.mTextView.getText().toString());
                    }
                    PlannedDriveDayPicker.this.hide();
                }
            });
        }

        public int getItemCount() {
            return PlannedDriveDayPicker.this.mValues.size();
        }
    }

    private class DayPickerViewHolder extends ViewHolder {
        private TextView mTextView;

        public DayPickerViewHolder(TextView itemView) {
            super(itemView);
            this.mTextView = itemView;
        }

        public void setText(String text) {
            this.mTextView.setText(text);
        }
    }

    private class SeparatorDecoration extends ItemDecoration {
        private Paint mPaint = new Paint();

        public SeparatorDecoration() {
            this.mPaint.setColor(PlannedDriveDayPicker.this.getResources().getColor(C1283R.color.PassiveGrey));
            this.mPaint.setStrokeWidth((float) PixelMeasure.dp(1));
            this.mPaint.setStyle(Style.STROKE);
        }

        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
            super.getItemOffsets(outRect, view, parent, state);
        }

        public void onDraw(Canvas c, RecyclerView parent, State state) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount - 1; i++) {
                int lineY = parent.getChildAt(i).getBottom() + PixelMeasure.dp(16);
                c.drawLine((float) PixelMeasure.dp(2), (float) lineY, (float) PlannedDriveDayPicker.this.mRecyclerView.getMeasuredWidth(), (float) lineY, this.mPaint);
            }
        }
    }

    public PlannedDriveDayPicker(Context context) {
        this(context, null);
    }

    public PlannedDriveDayPicker(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PlannedDriveDayPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mValues = new ArrayList();
        init();
    }

    private void init() {
        View content = LayoutInflater.from(getContext()).inflate(C1283R.layout.planned_drive_day_picker, null);
        this.mRecyclerView = (RecyclerView) content.findViewById(C1283R.id.recycler);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        this.mRecyclerView.setAdapter(new DayPickerAdapter());
        this.mRecyclerView.addItemDecoration(new SeparatorDecoration());
        if (dayNames == null) {
            dayNames = new String[]{"", NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SUNDAY), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_MONDAY), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TUESDAY), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_WEDNESDAY), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_THURSDAY), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_FRIDAY), NativeManager.getInstance().getLanguageString(DisplayStrings.DS_SATURDAY)};
        }
        addView(content);
        setOnClickListener(new C23321());
    }

    public void hide() {
        setAlpha(1.0f);
        ViewPropertyAnimatorHelper.initAnimation(this).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this));
    }

    public void show() {
        setVisibility(0);
        setAlpha(0.0f);
        ViewPropertyAnimatorHelper.initAnimation(this).alpha(1.0f).setListener(null);
    }

    public void setListener(DayPickerListener listener) {
        this.mListener = listener;
    }

    public void setVisibility(int visibility) {
        super.setVisibility(visibility);
        if (visibility == 0) {
            buildDays();
        }
    }

    public void buildDays() {
        this.mValues.clear();
        this.mValues.add(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TODAY_CAP));
        this.mValues.add(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TOMORROW));
        Date today = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(5, 2);
        for (int i = 0; i < 5; i++) {
            this.mValues.add(dayNames[calendar.get(7)]);
            calendar.add(5, 1);
        }
        this.mRecyclerView.getAdapter().notifyDataSetChanged();
    }

    public String getValue(int daysFromNow) {
        if (this.mValues == null || this.mValues.size() <= daysFromNow || daysFromNow < 0) {
            return NativeManager.getInstance().getLanguageString(DisplayStrings.DS_TODAY_CAP);
        }
        return (String) this.mValues.get(daysFromNow);
    }
}
