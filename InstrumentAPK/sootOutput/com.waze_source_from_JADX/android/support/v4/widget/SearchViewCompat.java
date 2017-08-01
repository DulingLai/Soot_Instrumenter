package android.support.v4.widget;

import android.content.ComponentName;
import android.content.Context;
import android.os.Build.VERSION;
import android.view.View;

public final class SearchViewCompat {
    private static final SearchViewCompatImpl IMPL;

    public static abstract class OnCloseListenerCompat {
        final Object mListener = SearchViewCompat.IMPL.newOnCloseListener(this);

        public boolean onClose() throws  {
            return false;
        }
    }

    public static abstract class OnQueryTextListenerCompat {
        final Object mListener = SearchViewCompat.IMPL.newOnQueryTextListener(this);

        public boolean onQueryTextChange(String newText) throws  {
            return false;
        }

        public boolean onQueryTextSubmit(String query) throws  {
            return false;
        }
    }

    interface SearchViewCompatImpl {
        CharSequence getQuery(View view) throws ;

        boolean isIconified(View view) throws ;

        boolean isQueryRefinementEnabled(View view) throws ;

        boolean isSubmitButtonEnabled(View view) throws ;

        Object newOnCloseListener(OnCloseListenerCompat onCloseListenerCompat) throws ;

        Object newOnQueryTextListener(OnQueryTextListenerCompat onQueryTextListenerCompat) throws ;

        View newSearchView(Context context) throws ;

        void setIconified(View view, boolean z) throws ;

        void setImeOptions(View view, int i) throws ;

        void setInputType(View view, int i) throws ;

        void setMaxWidth(View view, int i) throws ;

        void setOnCloseListener(Object obj, Object obj2) throws ;

        void setOnQueryTextListener(Object obj, Object obj2) throws ;

        void setQuery(View view, CharSequence charSequence, boolean z) throws ;

        void setQueryHint(View view, CharSequence charSequence) throws ;

        void setQueryRefinementEnabled(View view, boolean z) throws ;

        void setSearchableInfo(View view, ComponentName componentName) throws ;

        void setSubmitButtonEnabled(View view, boolean z) throws ;
    }

    static class SearchViewCompatStubImpl implements SearchViewCompatImpl {
        public CharSequence getQuery(View searchView) throws  {
            return null;
        }

        public boolean isIconified(View searchView) throws  {
            return true;
        }

        public boolean isQueryRefinementEnabled(View searchView) throws  {
            return false;
        }

        public boolean isSubmitButtonEnabled(View searchView) throws  {
            return false;
        }

        public Object newOnCloseListener(OnCloseListenerCompat listener) throws  {
            return null;
        }

        public Object newOnQueryTextListener(OnQueryTextListenerCompat listener) throws  {
            return null;
        }

        public View newSearchView(Context context) throws  {
            return null;
        }

        SearchViewCompatStubImpl() throws  {
        }

        public void setSearchableInfo(View searchView, ComponentName searchableComponent) throws  {
        }

        public void setImeOptions(View searchView, int imeOptions) throws  {
        }

        public void setInputType(View searchView, int inputType) throws  {
        }

        public void setOnQueryTextListener(Object searchView, Object listener) throws  {
        }

        public void setOnCloseListener(Object searchView, Object listener) throws  {
        }

        public void setQuery(View searchView, CharSequence query, boolean submit) throws  {
        }

        public void setQueryHint(View searchView, CharSequence hint) throws  {
        }

        public void setIconified(View searchView, boolean iconify) throws  {
        }

        public void setSubmitButtonEnabled(View searchView, boolean enabled) throws  {
        }

        public void setQueryRefinementEnabled(View searchView, boolean enable) throws  {
        }

        public void setMaxWidth(View searchView, int maxpixels) throws  {
        }
    }

    static class SearchViewCompatHoneycombImpl extends SearchViewCompatStubImpl {
        SearchViewCompatHoneycombImpl() throws  {
        }

        public View newSearchView(Context $r1) throws  {
            return SearchViewCompatHoneycomb.newSearchView($r1);
        }

        public void setSearchableInfo(View $r1, ComponentName $r2) throws  {
            SearchViewCompatHoneycomb.setSearchableInfo($r1, $r2);
        }

        public Object newOnQueryTextListener(final OnQueryTextListenerCompat $r1) throws  {
            return SearchViewCompatHoneycomb.newOnQueryTextListener(new OnQueryTextListenerCompatBridge() {
                public boolean onQueryTextSubmit(String $r1) throws  {
                    return $r1.onQueryTextSubmit($r1);
                }

                public boolean onQueryTextChange(String $r1) throws  {
                    return $r1.onQueryTextChange($r1);
                }
            });
        }

        public void setOnQueryTextListener(Object $r1, Object $r2) throws  {
            SearchViewCompatHoneycomb.setOnQueryTextListener($r1, $r2);
        }

        public Object newOnCloseListener(final OnCloseListenerCompat $r1) throws  {
            return SearchViewCompatHoneycomb.newOnCloseListener(new OnCloseListenerCompatBridge() {
                public boolean onClose() throws  {
                    return $r1.onClose();
                }
            });
        }

        public void setOnCloseListener(Object $r1, Object $r2) throws  {
            SearchViewCompatHoneycomb.setOnCloseListener($r1, $r2);
        }

        public CharSequence getQuery(View $r1) throws  {
            return SearchViewCompatHoneycomb.getQuery($r1);
        }

        public void setQuery(View $r1, CharSequence $r2, boolean $z0) throws  {
            SearchViewCompatHoneycomb.setQuery($r1, $r2, $z0);
        }

        public void setQueryHint(View $r1, CharSequence $r2) throws  {
            SearchViewCompatHoneycomb.setQueryHint($r1, $r2);
        }

        public void setIconified(View $r1, boolean $z0) throws  {
            SearchViewCompatHoneycomb.setIconified($r1, $z0);
        }

        public boolean isIconified(View $r1) throws  {
            return SearchViewCompatHoneycomb.isIconified($r1);
        }

        public void setSubmitButtonEnabled(View $r1, boolean $z0) throws  {
            SearchViewCompatHoneycomb.setSubmitButtonEnabled($r1, $z0);
        }

        public boolean isSubmitButtonEnabled(View $r1) throws  {
            return SearchViewCompatHoneycomb.isSubmitButtonEnabled($r1);
        }

        public void setQueryRefinementEnabled(View $r1, boolean $z0) throws  {
            SearchViewCompatHoneycomb.setQueryRefinementEnabled($r1, $z0);
        }

        public boolean isQueryRefinementEnabled(View $r1) throws  {
            return SearchViewCompatHoneycomb.isQueryRefinementEnabled($r1);
        }

        public void setMaxWidth(View $r1, int $i0) throws  {
            SearchViewCompatHoneycomb.setMaxWidth($r1, $i0);
        }
    }

    static class SearchViewCompatIcsImpl extends SearchViewCompatHoneycombImpl {
        SearchViewCompatIcsImpl() throws  {
        }

        public View newSearchView(Context $r1) throws  {
            return SearchViewCompatIcs.newSearchView($r1);
        }

        public void setImeOptions(View $r1, int $i0) throws  {
            SearchViewCompatIcs.setImeOptions($r1, $i0);
        }

        public void setInputType(View $r1, int $i0) throws  {
            SearchViewCompatIcs.setInputType($r1, $i0);
        }
    }

    static {
        if (VERSION.SDK_INT >= 14) {
            IMPL = new SearchViewCompatIcsImpl();
        } else if (VERSION.SDK_INT >= 11) {
            IMPL = new SearchViewCompatHoneycombImpl();
        } else {
            IMPL = new SearchViewCompatStubImpl();
        }
    }

    private SearchViewCompat(Context context) throws  {
    }

    public static View newSearchView(Context $r0) throws  {
        return IMPL.newSearchView($r0);
    }

    public static void setSearchableInfo(View $r0, ComponentName $r1) throws  {
        IMPL.setSearchableInfo($r0, $r1);
    }

    public static void setImeOptions(View $r0, int $i0) throws  {
        IMPL.setImeOptions($r0, $i0);
    }

    public static void setInputType(View $r0, int $i0) throws  {
        IMPL.setInputType($r0, $i0);
    }

    public static void setOnQueryTextListener(View $r0, OnQueryTextListenerCompat $r1) throws  {
        IMPL.setOnQueryTextListener($r0, $r1.mListener);
    }

    public static void setOnCloseListener(View $r0, OnCloseListenerCompat $r1) throws  {
        IMPL.setOnCloseListener($r0, $r1.mListener);
    }

    public static CharSequence getQuery(View $r0) throws  {
        return IMPL.getQuery($r0);
    }

    public static void setQuery(View $r0, CharSequence $r1, boolean $z0) throws  {
        IMPL.setQuery($r0, $r1, $z0);
    }

    public static void setQueryHint(View $r0, CharSequence $r1) throws  {
        IMPL.setQueryHint($r0, $r1);
    }

    public static void setIconified(View $r0, boolean $z0) throws  {
        IMPL.setIconified($r0, $z0);
    }

    public static boolean isIconified(View $r0) throws  {
        return IMPL.isIconified($r0);
    }

    public static void setSubmitButtonEnabled(View $r0, boolean $z0) throws  {
        IMPL.setSubmitButtonEnabled($r0, $z0);
    }

    public static boolean isSubmitButtonEnabled(View $r0) throws  {
        return IMPL.isSubmitButtonEnabled($r0);
    }

    public static void setQueryRefinementEnabled(View $r0, boolean $z0) throws  {
        IMPL.setQueryRefinementEnabled($r0, $z0);
    }

    public static boolean isQueryRefinementEnabled(View $r0) throws  {
        return IMPL.isQueryRefinementEnabled($r0);
    }

    public static void setMaxWidth(View $r0, int $i0) throws  {
        IMPL.setMaxWidth($r0, $i0);
    }
}
