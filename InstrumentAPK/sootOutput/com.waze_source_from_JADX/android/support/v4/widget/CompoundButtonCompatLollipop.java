package android.support.v4.widget;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff.Mode;
import android.widget.CompoundButton;

class CompoundButtonCompatLollipop {
    CompoundButtonCompatLollipop() throws  {
    }

    static void setButtonTintList(CompoundButton $r0, ColorStateList $r1) throws  {
        $r0.setButtonTintList($r1);
    }

    static ColorStateList getButtonTintList(CompoundButton $r0) throws  {
        return $r0.getButtonTintList();
    }

    static void setButtonTintMode(CompoundButton $r0, Mode $r1) throws  {
        $r0.setButtonTintMode($r1);
    }

    static Mode getButtonTintMode(CompoundButton $r0) throws  {
        return $r0.getButtonTintMode();
    }
}
