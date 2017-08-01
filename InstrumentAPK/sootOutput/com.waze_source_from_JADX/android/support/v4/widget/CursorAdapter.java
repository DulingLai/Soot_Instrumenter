package android.support.v4.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;

public abstract class CursorAdapter extends BaseAdapter implements CursorFilterClient, Filterable {
    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
    protected boolean mAutoRequery;
    protected ChangeObserver mChangeObserver;
    protected Context mContext;
    protected Cursor mCursor;
    protected CursorFilter mCursorFilter;
    protected DataSetObserver mDataSetObserver;
    protected boolean mDataValid;
    protected FilterQueryProvider mFilterQueryProvider;
    protected int mRowIDColumn;

    private class ChangeObserver extends ContentObserver {
        public boolean deliverSelfNotifications() throws  {
            return true;
        }

        public ChangeObserver() throws  {
            super(new Handler());
        }

        public void onChange(boolean selfChange) throws  {
            CursorAdapter.this.onContentChanged();
        }
    }

    private class MyDataSetObserver extends DataSetObserver {
        private MyDataSetObserver() throws  {
        }

        public void onChanged() throws  {
            CursorAdapter.this.mDataValid = true;
            CursorAdapter.this.notifyDataSetChanged();
        }

        public void onInvalidated() throws  {
            CursorAdapter.this.mDataValid = false;
            CursorAdapter.this.notifyDataSetInvalidated();
        }
    }

    public abstract void bindView(View view, Context context, Cursor cursor) throws ;

    public boolean hasStableIds() throws  {
        return true;
    }

    public abstract View newView(Context context, Cursor cursor, ViewGroup viewGroup) throws ;

    @Deprecated
    public CursorAdapter(@Deprecated Context $r1, @Deprecated Cursor $r2) throws  {
        init($r1, $r2, 1);
    }

    public CursorAdapter(Context $r1, Cursor $r2, boolean $z0) throws  {
        int $b0;
        if ($z0) {
            $b0 = (byte) 1;
        } else {
            $b0 = (byte) 2;
        }
        init($r1, $r2, $b0);
    }

    public CursorAdapter(Context $r1, Cursor $r2, int $i0) throws  {
        init($r1, $r2, $i0);
    }

    @Deprecated
    protected void init(@Deprecated Context $r1, @Deprecated Cursor $r2, @Deprecated boolean $z0) throws  {
        init($r1, $r2, $z0 ? (byte) 1 : (byte) 2);
    }

    void init(Context $r1, Cursor $r2, int $i1) throws  {
        boolean $z0 = true;
        if (($i1 & 1) == 1) {
            $i1 |= 2;
            this.mAutoRequery = true;
        } else {
            this.mAutoRequery = false;
        }
        if ($r2 == null) {
            $z0 = false;
        }
        this.mCursor = $r2;
        this.mDataValid = $z0;
        this.mContext = $r1;
        this.mRowIDColumn = $z0 ? $r2.getColumnIndexOrThrow("_id") : -1;
        if (($i1 & 2) == 2) {
            this.mChangeObserver = new ChangeObserver();
            this.mDataSetObserver = new MyDataSetObserver();
        } else {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        }
        if ($z0) {
            if (this.mChangeObserver != null) {
                $r2.registerContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                $r2.registerDataSetObserver(this.mDataSetObserver);
            }
        }
    }

    public Cursor getCursor() throws  {
        return this.mCursor;
    }

    public int getCount() throws  {
        if (!this.mDataValid || this.mCursor == null) {
            return 0;
        }
        return this.mCursor.getCount();
    }

    public Object getItem(int $i0) throws  {
        if (!this.mDataValid || this.mCursor == null) {
            return null;
        }
        this.mCursor.moveToPosition($i0);
        return this.mCursor;
    }

    public long getItemId(int $i0) throws  {
        if (!this.mDataValid) {
            return 0;
        }
        if (this.mCursor == null) {
            return 0;
        }
        if (this.mCursor.moveToPosition($i0)) {
            return this.mCursor.getLong(this.mRowIDColumn);
        }
        return 0;
    }

    public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
        if (!this.mDataValid) {
            throw new IllegalStateException("this should only be called when the cursor is valid");
        } else if (this.mCursor.moveToPosition($i0)) {
            if ($r1 == null) {
                $r1 = newView(this.mContext, this.mCursor, $r2);
            }
            bindView($r1, this.mContext, this.mCursor);
            return $r1;
        } else {
            throw new IllegalStateException("couldn't move cursor to position " + $i0);
        }
    }

    public View getDropDownView(int $i0, View $r1, ViewGroup $r2) throws  {
        if (!this.mDataValid) {
            return null;
        }
        this.mCursor.moveToPosition($i0);
        if ($r1 == null) {
            $r1 = newDropDownView(this.mContext, this.mCursor, $r2);
        }
        bindView($r1, this.mContext, this.mCursor);
        return $r1;
    }

    public View newDropDownView(Context $r1, Cursor $r2, ViewGroup $r3) throws  {
        return newView($r1, $r2, $r3);
    }

    public void changeCursor(Cursor $r1) throws  {
        $r1 = swapCursor($r1);
        if ($r1 != null) {
            $r1.close();
        }
    }

    public Cursor swapCursor(Cursor $r1) throws  {
        if ($r1 == this.mCursor) {
            return null;
        }
        Cursor $r2 = this.mCursor;
        if ($r2 != null) {
            if (this.mChangeObserver != null) {
                $r2.unregisterContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                $r2.unregisterDataSetObserver(this.mDataSetObserver);
            }
        }
        this.mCursor = $r1;
        if ($r1 != null) {
            if (this.mChangeObserver != null) {
                $r1.registerContentObserver(this.mChangeObserver);
            }
            if (this.mDataSetObserver != null) {
                $r1.registerDataSetObserver(this.mDataSetObserver);
            }
            this.mRowIDColumn = $r1.getColumnIndexOrThrow("_id");
            this.mDataValid = true;
            notifyDataSetChanged();
            return $r2;
        }
        this.mRowIDColumn = -1;
        this.mDataValid = false;
        notifyDataSetInvalidated();
        return $r2;
    }

    public CharSequence convertToString(Cursor $r1) throws  {
        return $r1 == null ? "" : $r1.toString();
    }

    public Cursor runQueryOnBackgroundThread(CharSequence $r1) throws  {
        if (this.mFilterQueryProvider != null) {
            return this.mFilterQueryProvider.runQuery($r1);
        }
        return this.mCursor;
    }

    public Filter getFilter() throws  {
        if (this.mCursorFilter == null) {
            this.mCursorFilter = new CursorFilter(this);
        }
        return this.mCursorFilter;
    }

    public FilterQueryProvider getFilterQueryProvider() throws  {
        return this.mFilterQueryProvider;
    }

    public void setFilterQueryProvider(FilterQueryProvider $r1) throws  {
        this.mFilterQueryProvider = $r1;
    }

    protected void onContentChanged() throws  {
        if (this.mAutoRequery && this.mCursor != null && !this.mCursor.isClosed()) {
            this.mDataValid = this.mCursor.requery();
        }
    }
}
