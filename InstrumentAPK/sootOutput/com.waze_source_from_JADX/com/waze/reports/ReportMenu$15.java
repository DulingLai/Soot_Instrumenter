package com.waze.reports;

import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

class ReportMenu$15 implements AnimationListener {
    final /* synthetic */ ReportMenu this$0;
    final /* synthetic */ ViewGroup val$reportContainer;

    ReportMenu$15(ReportMenu this$0, ViewGroup viewGroup) {
        this.this$0 = this$0;
        this.val$reportContainer = viewGroup;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        this.val$reportContainer.removeView(ReportMenu.access$600(this.this$0));
        ReportMenu.access$602(this.this$0, null);
        if (ReportMenu.access$700(this.this$0) != null) {
            ReportMenu.access$700(this.this$0).setEnabled(true);
            ReportMenu.access$700(this.this$0).setClickable(true);
            ReportMenu.access$700(this.this$0).setOpen();
            ReportMenu.access$700(this.this$0).clearAnimation();
            ReportMenu.access$700(this.this$0).setAlpha(1.0f);
            ReportMenu.access$702(this.this$0, null);
        }
    }
}
