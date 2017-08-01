package com.waze.reports;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

class ReportMenu$16 implements AnimationListener {
    final /* synthetic */ ReportMenu this$0;
    final /* synthetic */ ViewGroup val$reportContainer;

    ReportMenu$16(ReportMenu this$0, ViewGroup viewGroup) {
        this.this$0 = this$0;
        this.val$reportContainer = viewGroup;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        this.val$reportContainer.removeView(ReportMenu.access$600(this.this$0));
        if (ReportMenu.access$700(this.this$0) != null) {
            ReportMenu.access$700(this.this$0).setVisibility(0);
            ReportMenu.access$702(this.this$0, null);
        }
        ReportMenu.access$602(this.this$0, null);
        ReportMenu.access$500(this.this$0).removeReportMenu();
    }
}
