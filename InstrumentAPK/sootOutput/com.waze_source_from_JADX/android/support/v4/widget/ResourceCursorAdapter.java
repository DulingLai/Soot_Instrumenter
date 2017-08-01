package android.support.v4.widget;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class ResourceCursorAdapter extends CursorAdapter {
    private int mDropDownLayout;
    private LayoutInflater mInflater;
    private int mLayout;

    @Deprecated
    public ResourceCursorAdapter(@Deprecated Context $r1, @Deprecated int $i0, @Deprecated Cursor $r2) throws  {
        super($r1, $r2);
        this.mDropDownLayout = $i0;
        this.mLayout = $i0;
        this.mInflater = (LayoutInflater) $r1.getSystemService("layout_inflater");
    }

    public ResourceCursorAdapter(Context $r1, int $i0, Cursor $r2, boolean $z0) throws  {
        super($r1, $r2, $z0);
        this.mDropDownLayout = $i0;
        this.mLayout = $i0;
        this.mInflater = (LayoutInflater) $r1.getSystemService("layout_inflater");
    }

    public ResourceCursorAdapter(Context $r1, int $i0, Cursor $r2, int $i1) throws  {
        super($r1, $r2, $i1);
        this.mDropDownLayout = $i0;
        this.mLayout = $i0;
        this.mInflater = (LayoutInflater) $r1.getSystemService("layout_inflater");
    }

    public View newView(Context context, Cursor cursor, ViewGroup $r3) throws  {
        return this.mInflater.inflate(this.mLayout, $r3, false);
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup $r3) throws  {
        return this.mInflater.inflate(this.mDropDownLayout, $r3, false);
    }

    public void setViewResource(int $i0) throws  {
        this.mLayout = $i0;
    }

    public void setDropDownViewResource(int $i0) throws  {
        this.mDropDownLayout = $i0;
    }
}
