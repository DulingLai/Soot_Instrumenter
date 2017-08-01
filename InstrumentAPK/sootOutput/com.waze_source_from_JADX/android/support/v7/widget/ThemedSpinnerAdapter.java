package android.support.v7.widget;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.widget.SpinnerAdapter;

public interface ThemedSpinnerAdapter extends SpinnerAdapter {

    public static final class Helper {
        private final Context mContext;
        private LayoutInflater mDropDownInflater;
        private final LayoutInflater mInflater;

        public Helper(@NonNull Context $r1) throws  {
            this.mContext = $r1;
            this.mInflater = LayoutInflater.from($r1);
        }

        public void setDropDownViewTheme(@Nullable Theme $r1) throws  {
            if ($r1 == null) {
                this.mDropDownInflater = null;
            } else if ($r1 == this.mContext.getTheme()) {
                this.mDropDownInflater = this.mInflater;
            } else {
                this.mDropDownInflater = LayoutInflater.from(new ContextThemeWrapper(this.mContext, $r1));
            }
        }

        @Nullable
        public Theme getDropDownViewTheme() throws  {
            return this.mDropDownInflater == null ? null : this.mDropDownInflater.getContext().getTheme();
        }

        @NonNull
        public LayoutInflater getDropDownViewInflater() throws  {
            return this.mDropDownInflater != null ? this.mDropDownInflater : this.mInflater;
        }
    }

    @Nullable
    Theme getDropDownViewTheme() throws ;

    void setDropDownViewTheme(@Nullable Theme theme) throws ;
}
