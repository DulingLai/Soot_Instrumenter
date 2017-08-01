package com.waze.reports;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;

class ReportMenu$1 implements OnGlobalLayoutListener {
    final /* synthetic */ ReportMenu this$0;
    final /* synthetic */ LayoutInflater val$inflater;
    final /* synthetic */ View val$reportContainer;

    ReportMenu$1(ReportMenu this$0, View view, LayoutInflater layoutInflater) {
        this.this$0 = this$0;
        this.val$reportContainer = view;
        this.val$inflater = layoutInflater;
    }

    public void onGlobalLayout() {
        int w = this.val$reportContainer.getMeasuredWidth();
        int h = this.val$reportContainer.getMeasuredHeight();
        if (w != 0 && h != 0) {
            this.val$reportContainer.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            ReportMenu.access$000(this.this$0, this.val$inflater);
        }
    }
}
