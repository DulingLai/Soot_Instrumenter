package com.waze.reports;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.NativeManager.SpeedLimit;
import com.waze.NativeManager.SpeedLimits;
import com.waze.NativeManager.intResultLListener;
import com.waze.config.ConfigValues;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;

public class SpeedLimitReport extends BottomSheet {
    private final Listener listener;
    private final boolean mAddOther;
    private final Context mContext;
    private final int mCurrentSpeedLimit;
    private final NativeManager mNm = NativeManager.getInstance();
    int[] speeds = null;
    private TextView tvDrawable;
    private View vCurrentMarker;
    private View vDrawable;

    public interface Listener {
        void onSpeedLimitReport(int i);
    }

    class C25391 implements intResultLListener {
        C25391() {
        }

        public void onResult(final int currentRoadType) {
            AppService.getActiveActivity().post(new Runnable() {

                class C25372 implements OnCancelListener {
                    C25372() {
                    }

                    public void onCancel(DialogInterface dialog) {
                        SpeedLimitReport.this.listener.onSpeedLimitReport(-1);
                    }
                }

                public void run() {
                    SpeedLimitReport.this.speeds = null;
                    SpeedLimits limits = NativeManager.getInstance().configGetSpeedLimitsNTV();
                    if (!(limits == null || limits.speedLimits == null)) {
                        for (SpeedLimit limit : limits.speedLimits) {
                            if (limit.roadType == currentRoadType || (SpeedLimitReport.this.speeds == null && limit.roadType == -1)) {
                                SpeedLimitReport.this.speeds = limit.speedLimits;
                            }
                        }
                    }
                    if (SpeedLimitReport.this.speeds == null) {
                        SpeedLimitReport.this.speeds = new int[0];
                    }
                    SpeedLimitReport.this.vDrawable = ((LayoutInflater) SpeedLimitReport.this.mContext.getSystemService("layout_inflater")).inflate(C1283R.layout.speed_limit_menu_item, null);
                    SpeedLimitReport.this.tvDrawable = (TextView) SpeedLimitReport.this.vDrawable.findViewById(C1283R.id.speedLimit);
                    SpeedLimitReport.this.vCurrentMarker = SpeedLimitReport.this.vDrawable.findViewById(C1283R.id.speedLimitCurrent);
                    if (ConfigValues.getStringValue(101).equals("us")) {
                        SpeedLimitReport.this.tvDrawable.setBackgroundResource(C1283R.drawable.speedlimit_us_bottomsheet);
                        SpeedLimitReport.this.tvDrawable.setPadding(0, PixelMeasure.dp(16), 0, 0);
                        LayoutParams params = (LayoutParams) SpeedLimitReport.this.tvDrawable.getLayoutParams();
                        params.width = PixelMeasure.dp(50);
                        SpeedLimitReport.this.tvDrawable.setLayoutParams(params);
                    }
                    final SimpleBottomSheetValue[] values = new SimpleBottomSheetValue[((SpeedLimitReport.this.mAddOther ? 1 : 0) + SpeedLimitReport.this.speeds.length)];
                    for (int i = 0; i < SpeedLimitReport.this.speeds.length; i++) {
                        values[i] = new SimpleBottomSheetValue(i, SpeedLimitReport.this.mNm.speedUnitNTV(), SpeedLimitReport.this.generateDrawable(Integer.toString(SpeedLimitReport.this.mNm.mathToSpeedUnitNTV(SpeedLimitReport.this.speeds[i])), SpeedLimitReport.this.speeds[i]));
                    }
                    if (SpeedLimitReport.this.mAddOther) {
                        values[SpeedLimitReport.this.speeds.length] = new SimpleBottomSheetValue(SpeedLimitReport.this.speeds.length, DisplayStrings.displayString(DisplayStrings.DS_SPEED_LIMITS_OTHER), SpeedLimitReport.this.generateDrawable(DisplayStrings.displayString(DisplayStrings.DS_SPEED_LIMITS_OTHER_VALUE), -1));
                    }
                    SpeedLimitReport.this.setAdapter(new Adapter() {
                        public int getCount() {
                            return values.length;
                        }

                        public void onConfigItem(int position, ItemDetails item) {
                            item.setItem(values[position].display, values[position].icon);
                        }

                        public void onClick(int position) {
                            SpeedLimitReport.this.dismiss();
                            int speed = 0;
                            if (position < SpeedLimitReport.this.speeds.length) {
                                speed = SpeedLimitReport.this.speeds[position];
                            }
                            SpeedLimitReport.this.listener.onSpeedLimitReport(speed);
                        }
                    });
                    SpeedLimitReport.this.setOnCancelListener(new C25372());
                    super.show();
                }
            });
        }
    }

    public SpeedLimitReport(Context context, String address, boolean addOther, int currentSpeedLimit, Listener l) {
        super(context, (int) DisplayStrings.DS_SPEED_LIMITS_TITLE, address, Mode.GRID_LARGE);
        this.mAddOther = addOther;
        this.mCurrentSpeedLimit = currentSpeedLimit;
        this.mContext = context;
        this.listener = l;
    }

    public void show() {
        NativeManager.getInstance().getPoiRoadType(new C25391());
    }

    private boolean sameSpeed(int a, int b) {
        return Math.abs(a - b) < 2;
    }

    private Drawable generateDrawable(String text, int speed) {
        this.tvDrawable.setText(text);
        if (sameSpeed(speed, this.mCurrentSpeedLimit)) {
            this.vCurrentMarker.setAlpha(1.0f);
        } else {
            this.vCurrentMarker.setAlpha(0.0f);
        }
        if (this.vDrawable.getMeasuredHeight() <= 0) {
            int size = MeasureSpec.makeMeasureSpec(PixelMeasure.dp(68), 1073741824);
            this.vDrawable.measure(size, size);
        }
        Bitmap b = Bitmap.createBitmap(this.vDrawable.getMeasuredWidth(), this.vDrawable.getMeasuredHeight(), Config.ARGB_8888);
        Canvas c = new Canvas(b);
        this.vDrawable.layout(0, 0, this.vDrawable.getMeasuredWidth(), this.vDrawable.getMeasuredHeight());
        this.vDrawable.draw(c);
        return new BitmapDrawable(b);
    }
}
