package android.support.v4.widget;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnCloseListener;
import android.widget.SearchView.OnQueryTextListener;

class SearchViewCompatHoneycomb {

    interface OnQueryTextListenerCompatBridge {
        boolean onQueryTextChange(String str) throws ;

        boolean onQueryTextSubmit(String str) throws ;
    }

    interface OnCloseListenerCompatBridge {
        boolean onClose() throws ;
    }

    SearchViewCompatHoneycomb() throws  {
    }

    public static View newSearchView(Context $r0) throws  {
        return new SearchView($r0);
    }

    public static void setSearchableInfo(View $r0, ComponentName $r1) throws  {
        SearchView $r2 = (SearchView) $r0;
        $r2.setSearchableInfo(((SearchManager) $r2.getContext().getSystemService("search")).getSearchableInfo($r1));
    }

    public static Object newOnQueryTextListener(final OnQueryTextListenerCompatBridge $r0) throws  {
        return new OnQueryTextListener() {
            public boolean onQueryTextSubmit(String $r1) throws  {
                return $r0.onQueryTextSubmit($r1);
            }

            public boolean onQueryTextChange(String $r1) throws  {
                return $r0.onQueryTextChange($r1);
            }
        };
    }

    public static void setOnQueryTextListener(Object $r0, Object $r1) throws  {
        ((SearchView) $r0).setOnQueryTextListener((OnQueryTextListener) $r1);
    }

    public static Object newOnCloseListener(final OnCloseListenerCompatBridge $r0) throws  {
        return new OnCloseListener() {
            public boolean onClose() throws  {
                return $r0.onClose();
            }
        };
    }

    public static void setOnCloseListener(Object $r0, Object $r1) throws  {
        ((SearchView) $r0).setOnCloseListener((OnCloseListener) $r1);
    }

    public static CharSequence getQuery(View $r1) throws  {
        return ((SearchView) $r1).getQuery();
    }

    public static void setQuery(View $r1, CharSequence $r0, boolean $z0) throws  {
        ((SearchView) $r1).setQuery($r0, $z0);
    }

    public static void setQueryHint(View $r1, CharSequence $r0) throws  {
        ((SearchView) $r1).setQueryHint($r0);
    }

    public static void setIconified(View $r0, boolean $z0) throws  {
        ((SearchView) $r0).setIconified($z0);
    }

    public static boolean isIconified(View $r0) throws  {
        return ((SearchView) $r0).isIconified();
    }

    public static void setSubmitButtonEnabled(View $r0, boolean $z0) throws  {
        ((SearchView) $r0).setSubmitButtonEnabled($z0);
    }

    public static boolean isSubmitButtonEnabled(View $r0) throws  {
        return ((SearchView) $r0).isSubmitButtonEnabled();
    }

    public static void setQueryRefinementEnabled(View $r0, boolean $z0) throws  {
        ((SearchView) $r0).setQueryRefinementEnabled($z0);
    }

    public static boolean isQueryRefinementEnabled(View $r0) throws  {
        return ((SearchView) $r0).isQueryRefinementEnabled();
    }

    public static void setMaxWidth(View $r0, int $i0) throws  {
        ((SearchView) $r0).setMaxWidth($i0);
    }
}
