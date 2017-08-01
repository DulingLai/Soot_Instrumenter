package com.waze.view.popups;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.abaltatech.mcp.weblink.sdk.WLNotificationManager;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.text.WazeTextView;

public class TrafficDetectionPopUp extends GenericNotification {
    private final int TRAFFIC_VALUE_NO = 0;
    private final int TRAFFIC_VALUE_NO_ANSWER = 2;
    private final int TRAFFIC_VALUE_YES = 1;
    private boolean isClosureEnabled;
    private boolean isFoursquareEnabled;
    private boolean isRandomUser;

    class C32331 implements Runnable {
        C32331() {
        }

        public void run() {
            AppService.getNativeManager().RealtimeReportTrafficNTV(1);
        }
    }

    class C32342 implements Runnable {
        C32342() {
        }

        public void run() {
            AppService.getNativeManager().RealtimeReportTrafficNTV(0);
        }
    }

    class C32353 implements Runnable {
        C32353() {
        }

        public void run() {
            AppService.getNativeManager().RealtimeReportTrafficNTV(2);
        }
    }

    class C32364 implements OnClickListener {
        C32364() {
        }

        public void onClick(View v) {
            TrafficDetectionPopUp.this.onYes();
        }
    }

    class C32375 implements OnClickListener {
        C32375() {
        }

        public void onClick(View v) {
            TrafficDetectionPopUp.this.onNo();
        }
    }

    public TrafficDetectionPopUp(Context context, LayoutManager layoutManager, boolean isRandomUser, boolean isFoursquareEnabled, boolean isClosureEnabled) {
        super(context, layoutManager);
        this.isRandomUser = isRandomUser;
        this.isFoursquareEnabled = isFoursquareEnabled;
        this.isClosureEnabled = isClosureEnabled;
        init();
    }

    public boolean onBackPressed() {
        onClose();
        return super.onBackPressed();
    }

    private void onYes() {
        NativeManager.Post(new C32331());
        hide();
        this.mLayoutManager.showTrafficJamReport(this.isRandomUser, this.isFoursquareEnabled, this.isClosureEnabled);
    }

    private void onNo() {
        NativeManager.Post(new C32342());
        hide();
    }

    private void onClose() {
        NativeManager.Post(new C32353());
        hide();
    }

    void init() {
        super.init();
        final String text1 = AppService.getNativeManager().getLanguageString(DisplayStrings.DS_WESRE_DETECTING_A_SLOW_DOWN);
        final String text2 = AppService.getNativeManager().getLanguageString(DisplayStrings.DS_ARE_YOU_IN_TRAFFICQ);
        setText(text1 + "\n" + text2);
        setIcon((int) C1283R.drawable.notification_traffic_icon);
        setButton1(C1283R.drawable.v_icon_dark_blue, "", true, new C32364());
        setButton2(C1283R.drawable.close_icon_grey, "", true, new C32375());
        getViewTreeObserver().addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                TrafficDetectionPopUp.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                WazeTextView wtv = (WazeTextView) TrafficDetectionPopUp.this.findViewById(C1283R.id.genNotificationText);
                wtv.setText(text1 + "\n" + text2);
                int width = TrafficDetectionPopUp.this.findViewById(C1283R.id.genNotificationButtonsLinearLayout).getWidth();
                if (width > 0) {
                    wtv.setRightBreak(width - ((int) (TrafficDetectionPopUp.this.getContext().getResources().getDisplayMetrics().density * 12.0f)));
                }
            }
        });
    }

    public void show() {
        setCloseTimer(WLNotificationManager.DEFAULT_TIMEOUT_MILLISECONDS);
        super.show();
    }
}
