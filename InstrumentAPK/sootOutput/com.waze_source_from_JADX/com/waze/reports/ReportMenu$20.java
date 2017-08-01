package com.waze.reports;

import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.view.button.ReportMenuButton;
import com.waze.view.button.ReportMenuButton.IAnimationListener;

class ReportMenu$20 implements OnGlobalLayoutListener {
    final /* synthetic */ ReportMenu this$0;
    final /* synthetic */ boolean val$delayed;

    class C25321 implements IAnimationListener {
        C25321() {
        }

        public void onAnimationDone() {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_MENU_SHOWN);
        }
    }

    ReportMenu$20(ReportMenu this$0, boolean z) {
        this.this$0 = this$0;
        this.val$delayed = z;
    }

    public void onGlobalLayout() {
        ReportMenu.access$900(this.this$0).getViewTreeObserver().removeGlobalOnLayoutListener(this);
        if (ReportMenu.access$1000(this.this$0)) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_REPORT_MENU_SHOWN);
            ReportMenu.access$1100(this.this$0).animate(0, null);
            return;
        }
        ReportMenu.access$1100(this.this$0).animate();
        int i;
        AlphaAnimation aa;
        if (this.val$delayed || this.this$0.inMenu) {
            for (i = 0; i < ReportMenu.access$1200(this.this$0).size(); i++) {
                ReportMenuButton rmb = (ReportMenuButton) ReportMenu.access$1200(this.this$0).get(i);
                rmb.setEnabled(false);
                rmb.setClickable(false);
                if (rmb != ReportMenu.access$700(this.this$0)) {
                    aa = new AlphaAnimation(0.0f, 0.0f);
                    aa.setDuration(0);
                    aa.setFillAfter(true);
                    rmb.startAnimation(aa);
                }
                TextView tv = (TextView) ReportMenu.access$1300(this.this$0).get(i);
                aa = new AlphaAnimation(0.0f, 0.0f);
                aa.setDuration(0);
                aa.setFillAfter(true);
                tv.startAnimation(aa);
            }
            ReportMenu.access$800(this.this$0).clearAnimation();
            if (ReportMenu.access$600(this.this$0) != null) {
                ReportMenu.access$600(this.this$0).appearify(this.val$delayed ? 50 : 0, 150, null);
                ReportMenu.access$600(this.this$0).stop();
                if (this.val$delayed) {
                    ReportMenu.access$600(this.this$0).animateButton(ReportMenu.access$500(this.this$0).getDelayedReportButton());
                    return;
                }
                return;
            }
            return;
        }
        int latency = 0;
        int latencyGap = 20;
        for (i = ReportMenu.access$1200(this.this$0).size() - 1; i >= 0; i--) {
            IAnimationListener l = null;
            if (i == 0) {
                l = new C25321();
            }
            ReportMenuButton icv = (ReportMenuButton) ReportMenu.access$1200(this.this$0).get(i);
            icv.clearAnimation();
            icv.startOpenAnimation(latency, 300, l);
            latency += latencyGap;
            latencyGap = (int) (((double) latencyGap) * 1.1d);
            icv.invalidate();
            tv = (TextView) ReportMenu.access$1300(this.this$0).get(i);
            AnimationSet as = new AnimationSet(true);
            as.addAnimation(new AlphaAnimation(0.0f, 1.0f));
            as.addAnimation(new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.5f, 1, 0.0f));
            as.setInterpolator(new DecelerateInterpolator());
            as.setFillBefore(true);
            as.setStartOffset((long) latency);
            as.setDuration(150);
            tv.startAnimation(as);
        }
        aa = new AlphaAnimation(0.0f, 1.0f);
        aa.setDuration(150);
        aa.setStartOffset((long) latency);
        ReportMenu.access$1400(this.this$0).startAnimation(aa);
    }
}
