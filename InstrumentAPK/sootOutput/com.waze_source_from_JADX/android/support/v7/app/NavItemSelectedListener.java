package android.support.v7.app;

import android.support.v7.app.ActionBar.OnNavigationListener;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import dalvik.annotation.Signature;

class NavItemSelectedListener implements OnItemSelectedListener {
    private final OnNavigationListener mListener;

    public NavItemSelectedListener(OnNavigationListener $r1) throws  {
        this.mListener = $r1;
    }

    public void onItemSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long $l1) throws  {
        if (this.mListener != null) {
            this.mListener.onNavigationItemSelected($i0, $l1);
        }
    }

    public void onNothingSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;)V"}) AdapterView<?> adapterView) throws  {
    }
}
