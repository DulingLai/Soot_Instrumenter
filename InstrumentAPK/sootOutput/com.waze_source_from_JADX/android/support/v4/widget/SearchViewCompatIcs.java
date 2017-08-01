package android.support.v4.widget;

import android.content.Context;
import android.view.View;
import android.widget.SearchView;

class SearchViewCompatIcs {

    public static class MySearchView extends SearchView {
        public MySearchView(Context $r1) throws  {
            super($r1);
        }

        public void onActionViewCollapsed() throws  {
            setQuery("", false);
            super.onActionViewCollapsed();
        }
    }

    SearchViewCompatIcs() throws  {
    }

    public static View newSearchView(Context $r0) throws  {
        return new MySearchView($r0);
    }

    public static void setImeOptions(View $r0, int $i0) throws  {
        ((SearchView) $r0).setImeOptions($i0);
    }

    public static void setInputType(View $r0, int $i0) throws  {
        ((SearchView) $r0).setInputType($i0);
    }
}
