package com.waze.routes;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.main.navigate.EventOnRoute;
import com.waze.navigate.social.ShareDriveActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.AnimationUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Queue;
import java.util.Vector;
import java.util.concurrent.LinkedBlockingQueue;

public class ETATrafficBar extends RelativeLayout {
    private static int EVENT_TYPE_ACCIDENT = 2;
    private static int EVENT_TYPE_HAZARD = 5;
    private static int EVENT_TYPE_POLICE = 1;
    private static int EVENT_TYPE_REPORTED_JAM = 3;
    private static int EVENT_TYPE_TRAFFIC = 4;
    protected static final long TIMEOUT_BETWEEN_EVENTS = 100;
    private static final int TRAFFIC_EVENT_ANIMATION_DURATION = 500;
    private static int TRAFFIC_REPORT_EXT = 2;
    private Runnable eventAnimationTask;
    EventHandler[] handlers;
    private boolean mAnimate;
    private final Context mContext;
    private Queue<View> mEventImagesQueue;
    private Vector<SortableView> mEventImagesVector;
    private EventOnRoute[] mEvents;
    private ArrayList<ExtEvent> mExtEvents;
    private Handler mHandler;
    private boolean mHasTraffic;
    private int mRouteTimeSec;
    private RelativeLayout otherEventsLayout;
    private RelativeLayout trafficEventsLayout;
    private int width;

    class C25651 implements Comparator<EventOnRoute> {
        C25651() {
        }

        public int compare(EventOnRoute lhs, EventOnRoute rhs) {
            return lhs.start - rhs.start;
        }
    }

    class C25662 implements Comparator<SortableView> {
        C25662() {
        }

        public int compare(SortableView lhs, SortableView rhs) {
            return lhs.pos - rhs.pos;
        }
    }

    class C25673 implements Runnable {
        C25673() {
        }

        public void run() {
            if (!ETATrafficBar.this.mEventImagesQueue.isEmpty()) {
                View bubble = (View) ETATrafficBar.this.mEventImagesQueue.poll();
                bubble.setVisibility(0);
                AnimationUtils.overshootCustomPopView(bubble);
                ETATrafficBar.this.mHandler.postDelayed(ETATrafficBar.this.eventAnimationTask, ETATrafficBar.TIMEOUT_BETWEEN_EVENTS);
            }
        }
    }

    private interface EventHandler {
        void done();

        void handleEvent(EventOnRoute eventOnRoute);

        void init();
    }

    private abstract class StackableEventHandler implements EventHandler {
        private boolean needStackMarker;
        private EventOnRoute previousEvent;
        private int previousEventLast;

        abstract View getBubble(EventOnRoute eventOnRoute, boolean z);

        abstract boolean supportedAlertType(EventOnRoute eventOnRoute);

        private StackableEventHandler() {
            this.previousEvent = null;
            this.needStackMarker = false;
        }

        public void init() {
        }

        public void handleEvent(EventOnRoute event) {
            if (!supportedAlertType(event)) {
                return;
            }
            if (this.previousEvent == null) {
                this.previousEvent = event;
                return;
            }
            int maxOverlap = 74;
            if (!ETATrafficBar.this.isInEditMode()) {
                maxOverlap = (PixelMeasure.dp(62) * 40) / 100;
            }
            if (((event.start - this.previousEvent.start) * ETATrafficBar.this.width) / 100 < maxOverlap) {
                this.previousEventLast = event.start;
                this.needStackMarker = true;
                return;
            }
            addEvent(this.previousEvent);
            this.previousEvent = event;
            this.previousEventLast = event.start;
        }

        protected double getStartPos(EventOnRoute event) {
            return (double) ((this.previousEvent.start + this.previousEventLast) / 2);
        }

        private void addEvent(EventOnRoute event) {
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            View bubble = getBubble(event, this.needStackMarker);
            this.needStackMarker = false;
            layoutParams.setMargins((int) ((getStartPos(event) * ((double) ETATrafficBar.this.width)) / 100.0d), 0, 0, 0);
            bubble.setLayoutParams(layoutParams);
            ETATrafficBar.this.otherEventsLayout.addView(bubble);
            if (ETATrafficBar.this.mAnimate) {
                bubble.setVisibility(4);
                ETATrafficBar.this.mEventImagesVector.add(new SortableView(bubble, (int) getStartPos(event)));
            }
        }

        public void done() {
            if (this.previousEvent != null) {
                addEvent(this.previousEvent);
            }
        }
    }

    private abstract class ImageEventHandler extends StackableEventHandler {
        protected abstract int getIcon();

        private ImageEventHandler() {
            super();
        }

        View getBubble(EventOnRoute event, boolean stack) {
            int i;
            View bubble = LayoutInflater.from(ETATrafficBar.this.mContext).inflate(C1283R.layout.eta_traffic_bar_event, null);
            View findViewById = bubble.findViewById(C1283R.id.trafficBarEventStack);
            if (stack) {
                i = 0;
            } else {
                i = 8;
            }
            findViewById.setVisibility(i);
            ((ImageView) bubble.findViewById(C1283R.id.trafficBarEventIcon)).setImageResource(getIcon());
            bubble.findViewById(C1283R.id.trafficBarEventTimeLayout).setVisibility(8);
            return bubble;
        }
    }

    private class AccidentHandler extends ImageEventHandler {
        private AccidentHandler() {
            super();
        }

        protected int getIcon() {
            return C1283R.drawable.traffic_bar_report_accident;
        }

        boolean supportedAlertType(EventOnRoute event) {
            return event.alertType == ETATrafficBar.EVENT_TYPE_ACCIDENT;
        }
    }

    private class DetectedJamHandler implements EventHandler {
        private DetectedJamHandler() {
        }

        public void init() {
        }

        public void handleEvent(EventOnRoute event) {
            int i = 0;
            if (event.alertType == ETATrafficBar.EVENT_TYPE_TRAFFIC) {
                ETATrafficBar.this.mHasTraffic = true;
                int length = ((int) (((double) (ETATrafficBar.this.width * (event.end - event.start))) / 100.0d)) + PixelMeasure.dp(6);
                int start = (int) (((((double) event.start) / 100.0d) * ((double) ETATrafficBar.this.width)) - ((double) PixelMeasure.dp(3)));
                if (start < 0) {
                    start = 0;
                }
                if (start + length > ETATrafficBar.this.width) {
                    length = ETATrafficBar.this.width - start;
                }
                LayoutParams layoutParams = new LayoutParams(length, -1);
                ImageView trafficImage = new ImageView(ETATrafficBar.this.mContext);
                layoutParams.setMargins(start, 0, 0, 0);
                trafficImage.setScaleType(ScaleType.FIT_XY);
                if (event.severity == 3) {
                    trafficImage.setImageResource(C1283R.drawable.eta_traffic_4);
                } else if (event.severity == 2) {
                    trafficImage.setImageResource(C1283R.drawable.eta_traffic_3);
                } else if (event.severity == 1) {
                    trafficImage.setImageResource(C1283R.drawable.eta_traffic_2);
                } else if (event.severity == 0) {
                    trafficImage.setImageResource(C1283R.drawable.eta_traffic_1);
                }
                trafficImage.setLayoutParams(layoutParams);
                ETATrafficBar.this.trafficEventsLayout.addView(trafficImage);
                if (ETATrafficBar.this.mAnimate) {
                    i = 500;
                }
                AnimationUtils.stretchViewHorizontally(trafficImage, i);
            }
        }

        public void done() {
        }
    }

    private class ExtEvent {
        final Drawable image;
        final int position;

        ExtEvent(Drawable d, int pos) {
            this.image = d;
            this.position = pos;
        }
    }

    private class ExtEventHandler extends ImageEventHandler {
        private ExtEventHandler() {
            super();
        }

        public void handleEvent(EventOnRoute event) {
        }

        protected int getIcon() {
            return C1283R.drawable.traffic_bar_report_police;
        }

        boolean supportedAlertType(EventOnRoute event) {
            return false;
        }

        public void done() {
            Iterator it = ETATrafficBar.this.mExtEvents.iterator();
            while (it.hasNext()) {
                ExtEvent ee = (ExtEvent) it.next();
                addEvent(ee.image, ee.position);
            }
            super.done();
        }

        private void addEvent(Drawable d, int pos) {
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            View bubble = LayoutInflater.from(ETATrafficBar.this.mContext).inflate(C1283R.layout.eta_traffic_bar_event, null);
            bubble.findViewById(C1283R.id.trafficBarEventStack).setVisibility(8);
            ((ImageView) bubble.findViewById(C1283R.id.trafficBarEventIcon)).setImageResource(C1283R.drawable.traffic_bar_report_empty);
            bubble.findViewById(C1283R.id.trafficBarEventTime).setVisibility(8);
            bubble.findViewById(C1283R.id.trafficBarEventUnits).setVisibility(8);
            bubble.findViewById(C1283R.id.trafficBarEventTimeLayout).setBackgroundDrawable(d);
            layoutParams.setMargins((ETATrafficBar.this.width * pos) / 100, 0, 0, 0);
            bubble.setLayoutParams(layoutParams);
            ETATrafficBar.this.otherEventsLayout.addView(bubble);
            if (ETATrafficBar.this.mAnimate) {
                bubble.setVisibility(4);
                ETATrafficBar.this.mEventImagesVector.add(new SortableView(bubble, pos));
            }
        }
    }

    private class HazardHandler extends ImageEventHandler {
        private HazardHandler() {
            super();
        }

        protected int getIcon() {
            return C1283R.drawable.traffic_bar_report_hazard;
        }

        boolean supportedAlertType(EventOnRoute event) {
            return event.alertType == ETATrafficBar.EVENT_TYPE_HAZARD;
        }
    }

    private class JamTimeHandler extends StackableEventHandler {
        private JamTimeHandler() {
            super();
        }

        protected double getStartPos(EventOnRoute event) {
            return (double) ((event.start + event.end) / 2);
        }

        View getBubble(EventOnRoute event, boolean stack) {
            View bubble = LayoutInflater.from(ETATrafficBar.this.mContext).inflate(C1283R.layout.eta_traffic_bar_event, null);
            bubble.findViewById(C1283R.id.trafficBarEventStack).setVisibility(8);
            ((ImageView) bubble.findViewById(C1283R.id.trafficBarEventIcon)).setImageResource(C1283R.drawable.traffic_bar_report_empty);
            LinearLayout timeLayout = (LinearLayout) bubble.findViewById(C1283R.id.trafficBarEventTimeLayout);
            timeLayout.setVisibility(0);
            if (event.severity == 3) {
                timeLayout.setBackgroundResource(C1283R.drawable.eta_traffic_circle_4);
            } else if (event.severity == 2) {
                timeLayout.setBackgroundResource(C1283R.drawable.eta_traffic_circle_3);
            } else if (event.severity == 1) {
                timeLayout.setBackgroundResource(C1283R.drawable.eta_traffic_circle_2);
            } else if (event.severity == 0) {
                timeLayout.setBackgroundResource(C1283R.drawable.eta_traffic_circle_1);
            }
            TextView tvTime = (TextView) bubble.findViewById(C1283R.id.trafficBarEventTime);
            TextView tvUnits = (TextView) bubble.findViewById(C1283R.id.trafficBarEventUnits);
            int time = (ETATrafficBar.this.mRouteTimeSec * event.percentage) / ShareDriveActivity.TOOLTIP_TIME_MILLIS;
            String label = Integer.toString(time);
            if (time > 99) {
                label = "99+";
            }
            tvTime.setText(label);
            tvUnits.setText(DisplayStrings.displayString(DisplayStrings.DS_MIN));
            return bubble;
        }

        boolean supportedAlertType(EventOnRoute event) {
            if (event.alertType == ETATrafficBar.EVENT_TYPE_TRAFFIC && ETATrafficBar.this.aboveThreshold(event)) {
                return true;
            }
            return false;
        }
    }

    private class PoliceReportHandler extends ImageEventHandler {
        private PoliceReportHandler() {
            super();
        }

        protected int getIcon() {
            return C1283R.drawable.traffic_bar_report_police;
        }

        boolean supportedAlertType(EventOnRoute event) {
            return event.alertType == ETATrafficBar.EVENT_TYPE_POLICE;
        }
    }

    private class ReportedJamHandler extends ImageEventHandler {
        private ReportedJamHandler() {
            super();
        }

        protected int getIcon() {
            return C1283R.drawable.traffic_bar_report_trafficjam;
        }

        boolean supportedAlertType(EventOnRoute event) {
            if (event.alertType != ETATrafficBar.EVENT_TYPE_REPORTED_JAM) {
                return false;
            }
            for (EventOnRoute otherEvent : ETATrafficBar.this.mEvents) {
                if (otherEvent != null && otherEvent.alertType == ETATrafficBar.EVENT_TYPE_TRAFFIC && otherEvent.start - ETATrafficBar.TRAFFIC_REPORT_EXT <= event.start && otherEvent.end + ETATrafficBar.TRAFFIC_REPORT_EXT >= event.start) {
                    return false;
                }
            }
            return true;
        }
    }

    private class SortableView {
        public int pos;
        public View view;

        SortableView(View view, int pos) {
            this.view = view;
            this.pos = pos;
        }
    }

    private boolean aboveThreshold(EventOnRoute event) {
        if ((event.percentage * this.mRouteTimeSec) / ShareDriveActivity.TOOLTIP_TIME_MILLIS >= NativeManager.getInstance().getAltRoutesPinMinutesThresholdNTV() && ((float) event.percentage) >= 100.0f * NativeManager.getInstance().getAltRoutesPinPercentThresholdNTV()) {
            return true;
        }
        return false;
    }

    public ETATrafficBar(Context context) {
        super(context);
        this.mEventImagesVector = new Vector();
        this.mEventImagesQueue = new LinkedBlockingQueue();
        this.mExtEvents = new ArrayList(4);
        this.handlers = new EventHandler[]{new DetectedJamHandler(), new ReportedJamHandler(), new HazardHandler(), new AccidentHandler(), new PoliceReportHandler(), new ExtEventHandler(), new JamTimeHandler()};
        this.eventAnimationTask = new C25673();
        this.mContext = context;
        init();
    }

    public ETATrafficBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mEventImagesVector = new Vector();
        this.mEventImagesQueue = new LinkedBlockingQueue();
        this.mExtEvents = new ArrayList(4);
        this.handlers = new EventHandler[]{new DetectedJamHandler(), new ReportedJamHandler(), new HazardHandler(), new AccidentHandler(), new PoliceReportHandler(), new ExtEventHandler(), new JamTimeHandler()};
        this.eventAnimationTask = new C25673();
        this.mContext = context;
        init();
    }

    private void init() {
        inflate(getContext(), C1283R.layout.eta_traffic_bar, this);
        this.trafficEventsLayout = (RelativeLayout) findViewById(C1283R.id.routeEventsOnRouteEventsTraffic);
        this.otherEventsLayout = (RelativeLayout) findViewById(C1283R.id.routeEventsOnRouteEventsOther);
    }

    public void clearEventsOnRoute() {
        this.mEvents = null;
        this.mHasTraffic = false;
        this.trafficEventsLayout.removeAllViews();
        this.otherEventsLayout.removeAllViews();
        findViewById(C1283R.id.routeEventsOnRouteEventsLabel).setVisibility(8);
        this.mExtEvents.clear();
    }

    public void populateEventsOnRoute(AlternativeRoute route, EventOnRoute[] events, int routeTimeSec, Handler handler, boolean animate) {
        this.mHandler = handler;
        this.mAnimate = animate;
        this.mRouteTimeSec = routeTimeSec;
        this.width = this.trafficEventsLayout.getMeasuredWidth();
        TextView tvLabel = (TextView) findViewById(C1283R.id.routeEventsOnRouteEventsLabel);
        if (events == null || events.length == 0) {
            tvLabel.setVisibility(0);
            tvLabel.setText(DisplayStrings.displayString(DisplayStrings.DS_ALTERNATE_ROUTES_NO_EVENTS_ON_ROUTE));
            return;
        }
        tvLabel.setVisibility(8);
        int count = 0;
        for (EventOnRoute e : events) {
            EventOnRoute e2;
            if (e2 != null) {
                count++;
            }
        }
        this.mEvents = new EventOnRoute[count];
        int length = events.length;
        int i = 0;
        int count2 = 0;
        while (i < length) {
            e2 = events[i];
            if (e2 != null) {
                count = count2 + 1;
                this.mEvents[count2] = e2;
            } else {
                count = count2;
            }
            i++;
            count2 = count;
        }
        Arrays.sort(this.mEvents, new C25651());
        this.mHasTraffic = false;
        this.trafficEventsLayout.removeAllViews();
        this.otherEventsLayout.removeAllViews();
        for (EventHandler eventHandler : this.handlers) {
            eventHandler.init();
            for (EventOnRoute event : this.mEvents) {
                if (event != null && (route == null || event.alertRouteId == route.id || event.alertRouteId == 0)) {
                    eventHandler.handleEvent(event);
                }
            }
            eventHandler.done();
        }
        if (animate) {
            SortableView[] arr = (SortableView[]) this.mEventImagesVector.toArray(new SortableView[this.mEventImagesVector.size()]);
            Arrays.sort(arr, new C25662());
            for (SortableView v : arr) {
                this.mEventImagesQueue.add(v.view);
            }
            if (this.mHasTraffic) {
                this.mHandler.postDelayed(this.eventAnimationTask, 500);
            } else {
                this.mHandler.postDelayed(this.eventAnimationTask, 0);
            }
        }
    }

    private EventOnRoute[] generateTestEvents() {
        Vector<EventOnRoute> vec = new Vector();
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_TRAFFIC, 0, 3, 0, 100, 100));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_POLICE, 0, 3, 0, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_POLICE, 0, 3, 0, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_HAZARD, 0, 0, 15, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_HAZARD, 0, 0, 20, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_HAZARD, 0, 0, 30, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_HAZARD, 0, 0, 40, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_HAZARD, 0, 0, 60, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_HAZARD, 0, 0, 67, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_REPORTED_JAM, 0, 2, 70, 0, 0));
        vec.add(new EventOnRoute(0, 0, EVENT_TYPE_HAZARD, 0, 0, 100, 0, 0));
        return (EventOnRoute[]) vec.toArray(new EventOnRoute[vec.size()]);
    }

    public void addExtEvent(Drawable d, int position) {
        this.mExtEvents.add(new ExtEvent(d, position));
    }
}
