package android.support.v7.widget.util;

import android.support.v7.util.SortedList.Callback;
import android.support.v7.widget.RecyclerView.Adapter;

public abstract class SortedListAdapterCallback<T2> extends Callback<T2> {
    final Adapter mAdapter;

    public SortedListAdapterCallback(Adapter $r1) throws  {
        this.mAdapter = $r1;
    }

    public void onInserted(int $i0, int $i1) throws  {
        this.mAdapter.notifyItemRangeInserted($i0, $i1);
    }

    public void onRemoved(int $i0, int $i1) throws  {
        this.mAdapter.notifyItemRangeRemoved($i0, $i1);
    }

    public void onMoved(int $i0, int $i1) throws  {
        this.mAdapter.notifyItemMoved($i0, $i1);
    }

    public void onChanged(int $i0, int $i1) throws  {
        this.mAdapter.notifyItemRangeChanged($i0, $i1);
    }
}
