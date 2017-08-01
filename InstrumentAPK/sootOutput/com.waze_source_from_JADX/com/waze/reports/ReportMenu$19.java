package com.waze.reports;

import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

class ReportMenu$19 implements AnimationListener {
    final /* synthetic */ ReportMenu this$0;

    class C25291 implements Runnable {
        C25291() {
        }

        public void run() {
            ReportMenu.access$500(ReportMenu$19.this.this$0).removeReportMenu();
        }
    }

    ReportMenu$19(ReportMenu this$0) {
        this.this$0 = this$0;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        ReportMenu.access$800(this.this$0).post(new C25291());
    }

    public void onAnimationRepeat(Animation animation) {
    }
}
