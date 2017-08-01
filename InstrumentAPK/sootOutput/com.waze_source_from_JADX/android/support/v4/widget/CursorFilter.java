package android.support.v4.widget;

import android.database.Cursor;
import android.widget.Filter;
import android.widget.Filter.FilterResults;

class CursorFilter extends Filter {
    CursorFilterClient mClient;

    interface CursorFilterClient {
        void changeCursor(Cursor cursor) throws ;

        CharSequence convertToString(Cursor cursor) throws ;

        Cursor getCursor() throws ;

        Cursor runQueryOnBackgroundThread(CharSequence charSequence) throws ;
    }

    CursorFilter(CursorFilterClient $r1) throws  {
        this.mClient = $r1;
    }

    public CharSequence convertResultToString(Object $r2) throws  {
        return this.mClient.convertToString((Cursor) $r2);
    }

    protected FilterResults performFiltering(CharSequence $r1) throws  {
        Cursor $r4 = this.mClient.runQueryOnBackgroundThread($r1);
        FilterResults $r2 = new FilterResults();
        if ($r4 != null) {
            $r2.count = $r4.getCount();
            $r2.values = $r4;
            return $r2;
        }
        $r2.count = 0;
        $r2.values = null;
        return $r2;
    }

    protected void publishResults(CharSequence constraint, FilterResults $r2) throws  {
        Cursor $r4 = this.mClient.getCursor();
        if ($r2.values != null && $r2.values != $r4) {
            this.mClient.changeCursor((Cursor) $r2.values);
        }
    }
}
