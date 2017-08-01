package com.waze.reports;

import com.waze.view.button.ReportMenuButton.IAnimationListener;

class ReportMenu$18 implements IAnimationListener {
    final /* synthetic */ ReportMenu this$0;

    ReportMenu$18(ReportMenu this$0) {
        this.this$0 = this$0;
    }

    public void onAnimationDone() {
        ReportMenu.access$500(this.this$0).removeReportMenu();
    }
}
