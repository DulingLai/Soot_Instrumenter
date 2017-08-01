package com.tokenautocomplete;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class FilteredArrayAdapter<T> extends ArrayAdapter<T> {
    private Filter filter;
    private List<T> originalObjects;

    private class AppFilter extends Filter {
        private ArrayList<T> sourceObjects;

        public AppFilter(@Signature({"(", "Ljava/util/List", "<TT;>;)V"}) List<T> $r2) throws  {
            setSourceObjects($r2);
        }

        public void setSourceObjects(@Signature({"(", "Ljava/util/List", "<TT;>;)V"}) List<T> $r1) throws  {
            synchronized (this) {
                this.sourceObjects = new ArrayList($r1);
            }
        }

        protected FilterResults performFiltering(CharSequence $r1) throws  {
            FilterResults $r3 = new FilterResults();
            if ($r1 == null || $r1.length() <= 0) {
                $r3.values = this.sourceObjects;
                $r3.count = this.sourceObjects.size();
                return $r3;
            }
            String $r4 = $r1.toString();
            ArrayList $r2 = new ArrayList();
            Iterator $r6 = this.sourceObjects.iterator();
            while ($r6.hasNext()) {
                Object $r7 = $r6.next();
                if (FilteredArrayAdapter.this.keepObject($r7, $r4)) {
                    $r2.add($r7);
                }
            }
            $r3.count = $r2.size();
            $r3.values = $r2;
            return $r3;
        }

        protected void publishResults(CharSequence constraint, FilterResults $r2) throws  {
            FilteredArrayAdapter.this.clear();
            if ($r2.count > 0) {
                FilteredArrayAdapter.this.addAll((Collection) $r2.values);
                FilteredArrayAdapter.this.notifyDataSetChanged();
                return;
            }
            FilteredArrayAdapter.this.notifyDataSetInvalidated();
        }
    }

    protected abstract boolean keepObject(@Signature({"(TT;", "Ljava/lang/String;", ")Z"}) T t, @Signature({"(TT;", "Ljava/lang/String;", ")Z"}) String str) throws ;

    public FilteredArrayAdapter(@Signature({"(", "Landroid/content/Context;", "I[TT;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "I[TT;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "I[TT;)V"}) T[] $r2) throws  {
        this($r1, $i0, 0, (Object[]) $r2);
    }

    public FilteredArrayAdapter(@Signature({"(", "Landroid/content/Context;", "II[TT;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "II[TT;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "II[TT;)V"}) int $i1, @Signature({"(", "Landroid/content/Context;", "II[TT;)V"}) T[] $r2) throws  {
        this($r1, $i0, $i1, new ArrayList(Arrays.asList($r2)));
    }

    public FilteredArrayAdapter(@Signature({"(", "Landroid/content/Context;", "I", "Ljava/util/List", "<TT;>;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "I", "Ljava/util/List", "<TT;>;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "I", "Ljava/util/List", "<TT;>;)V"}) List<T> $r2) throws  {
        this($r1, $i0, 0, (List) $r2);
    }

    public FilteredArrayAdapter(@Signature({"(", "Landroid/content/Context;", "II", "Ljava/util/List", "<TT;>;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "II", "Ljava/util/List", "<TT;>;)V"}) int $i0, @Signature({"(", "Landroid/content/Context;", "II", "Ljava/util/List", "<TT;>;)V"}) int $i1, @Signature({"(", "Landroid/content/Context;", "II", "Ljava/util/List", "<TT;>;)V"}) List<T> $r2) throws  {
        super($r1, $i0, $i1, new ArrayList($r2));
        this.originalObjects = $r2;
    }

    public void notifyDataSetChanged() throws  {
        ((AppFilter) getFilter()).setSourceObjects(this.originalObjects);
        super.notifyDataSetChanged();
    }

    public void notifyDataSetInvalidated() throws  {
        ((AppFilter) getFilter()).setSourceObjects(this.originalObjects);
        super.notifyDataSetInvalidated();
    }

    public Filter getFilter() throws  {
        if (this.filter == null) {
            this.filter = new AppFilter(this.originalObjects);
        }
        return this.filter;
    }
}
