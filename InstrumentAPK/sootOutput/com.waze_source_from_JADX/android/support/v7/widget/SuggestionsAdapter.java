package android.support.v7.widget;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.NotFoundException;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ResourceCursorAdapter;
import android.support.v7.appcompat.C0192R;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.facebook.appevents.AppEventsConstants;
import dalvik.annotation.Signature;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class SuggestionsAdapter extends ResourceCursorAdapter implements OnClickListener {
    private static final boolean DBG = false;
    static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE = 0;
    private boolean mClosed = false;
    private final int mCommitIconResId;
    private int mFlagsCol = -1;
    private int mIconName1Col = -1;
    private int mIconName2Col = -1;
    private final WeakHashMap<String, ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private int mQueryRefinement = 1;
    private final SearchManager mSearchManager = ((SearchManager) this.mContext.getSystemService("search"));
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private int mText1Col = -1;
    private int mText2Col = -1;
    private int mText2UrlCol = -1;
    private ColorStateList mUrlColor;

    private static final class ChildViewCache {
        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View $r1) throws  {
            this.mText1 = (TextView) $r1.findViewById(16908308);
            this.mText2 = (TextView) $r1.findViewById(16908309);
            this.mIcon1 = (ImageView) $r1.findViewById(16908295);
            this.mIcon2 = (ImageView) $r1.findViewById(16908296);
            this.mIconRefine = (ImageView) $r1.findViewById(C0192R.id.edit_query);
        }
    }

    public boolean hasStableIds() throws  {
        return false;
    }

    public SuggestionsAdapter(@Signature({"(", "Landroid/content/Context;", "Landroid/support/v7/widget/SearchView;", "Landroid/app/SearchableInfo;", "Ljava/util/WeakHashMap", "<", "Ljava/lang/String;", "Landroid/graphics/drawable/Drawable$ConstantState;", ">;)V"}) Context $r1, @Signature({"(", "Landroid/content/Context;", "Landroid/support/v7/widget/SearchView;", "Landroid/app/SearchableInfo;", "Ljava/util/WeakHashMap", "<", "Ljava/lang/String;", "Landroid/graphics/drawable/Drawable$ConstantState;", ">;)V"}) SearchView $r2, @Signature({"(", "Landroid/content/Context;", "Landroid/support/v7/widget/SearchView;", "Landroid/app/SearchableInfo;", "Ljava/util/WeakHashMap", "<", "Ljava/lang/String;", "Landroid/graphics/drawable/Drawable$ConstantState;", ">;)V"}) SearchableInfo $r3, @Signature({"(", "Landroid/content/Context;", "Landroid/support/v7/widget/SearchView;", "Landroid/app/SearchableInfo;", "Ljava/util/WeakHashMap", "<", "Ljava/lang/String;", "Landroid/graphics/drawable/Drawable$ConstantState;", ">;)V"}) WeakHashMap<String, ConstantState> $r4) throws  {
        super($r1, $r2.getSuggestionRowLayout(), null, true);
        this.mSearchView = $r2;
        this.mSearchable = $r3;
        this.mCommitIconResId = $r2.getSuggestionCommitIconResId();
        this.mProviderContext = $r1;
        this.mOutsideDrawablesCache = $r4;
    }

    public void setQueryRefinement(int $i0) throws  {
        this.mQueryRefinement = $i0;
    }

    public int getQueryRefinement() throws  {
        return this.mQueryRefinement;
    }

    public Cursor runQueryOnBackgroundThread(CharSequence $r1) throws  {
        String $r3;
        if ($r1 == null) {
            $r3 = "";
        } else {
            $r3 = $r1.toString();
        }
        if (this.mSearchView.getVisibility() != 0) {
            return null;
        }
        if (this.mSearchView.getWindowVisibility() != 0) {
            return null;
        }
        try {
            Cursor $r6 = getSearchManagerSuggestions(this.mSearchable, $r3, 50);
            if ($r6 == null) {
                return null;
            }
            $r6.getCount();
            return $r6;
        } catch (RuntimeException $r2) {
            Log.w(LOG_TAG, "Search suggestions query threw an exception.", $r2);
            return null;
        }
    }

    public void close() throws  {
        changeCursor(null);
        this.mClosed = true;
    }

    public void notifyDataSetChanged() throws  {
        super.notifyDataSetChanged();
        updateSpinnerState(getCursor());
    }

    public void notifyDataSetInvalidated() throws  {
        super.notifyDataSetInvalidated();
        updateSpinnerState(getCursor());
    }

    private void updateSpinnerState(Cursor $r1) throws  {
        Bundle $r2 = $r1 != null ? $r1.getExtras() : null;
        if ($r2 != null && !$r2.getBoolean("in_progress")) {
        }
    }

    public void changeCursor(Cursor $r1) throws  {
        if (this.mClosed) {
            Log.w(LOG_TAG, "Tried to change cursor after adapter was closed.");
            if ($r1 != null) {
                $r1.close();
                return;
            }
            return;
        }
        try {
            super.changeCursor($r1);
            if ($r1 != null) {
                this.mText1Col = $r1.getColumnIndex("suggest_text_1");
                this.mText2Col = $r1.getColumnIndex("suggest_text_2");
                this.mText2UrlCol = $r1.getColumnIndex("suggest_text_2_url");
                this.mIconName1Col = $r1.getColumnIndex("suggest_icon_1");
                this.mIconName2Col = $r1.getColumnIndex("suggest_icon_2");
                this.mFlagsCol = $r1.getColumnIndex("suggest_flags");
            }
        } catch (Exception $r2) {
            Log.e(LOG_TAG, "error changing cursor and caching columns", $r2);
        }
    }

    public View newView(Context $r1, Cursor $r2, ViewGroup $r3) throws  {
        View $r4 = super.newView($r1, $r2, $r3);
        $r4.setTag(new ChildViewCache($r4));
        ((ImageView) $r4.findViewById(C0192R.id.edit_query)).setImageResource(this.mCommitIconResId);
        return $r4;
    }

    public void bindView(View $r1, Context context, Cursor $r3) throws  {
        ChildViewCache $r5 = (ChildViewCache) $r1.getTag();
        int $i0 = 0;
        if (this.mFlagsCol != -1) {
            $i0 = $r3.getInt(this.mFlagsCol);
        }
        if ($r5.mText1 != null) {
            setViewText($r5.mText1, getStringOrNull($r3, this.mText1Col));
        }
        if ($r5.mText2 != null) {
            CharSequence $r8;
            String $r7 = getStringOrNull($r3, this.mText2UrlCol);
            if ($r7 != null) {
                $r8 = formatUrl($r7);
            } else {
                $r8 = getStringOrNull($r3, this.mText2Col);
            }
            if (TextUtils.isEmpty($r8)) {
                if ($r5.mText1 != null) {
                    $r5.mText1.setSingleLine(false);
                    $r5.mText1.setMaxLines(2);
                }
            } else if ($r5.mText1 != null) {
                $r5.mText1.setSingleLine(true);
                $r5.mText1.setMaxLines(1);
            }
            setViewText($r5.mText2, $r8);
        }
        if ($r5.mIcon1 != null) {
            setViewDrawable($r5.mIcon1, getIcon1($r3), 4);
        }
        if ($r5.mIcon2 != null) {
            setViewDrawable($r5.mIcon2, getIcon2($r3), 8);
        }
        if (this.mQueryRefinement == 2 || (this.mQueryRefinement == 1 && ($i0 & 1) != 0)) {
            $r5.mIconRefine.setVisibility(0);
            $r5.mIconRefine.setTag($r5.mText1.getText());
            $r5.mIconRefine.setOnClickListener(this);
            return;
        }
        $r5.mIconRefine.setVisibility(8);
    }

    public void onClick(View $r1) throws  {
        Object $r2 = $r1.getTag();
        if ($r2 instanceof CharSequence) {
            this.mSearchView.onQueryRefine((CharSequence) $r2);
        }
    }

    private CharSequence formatUrl(CharSequence $r1) throws  {
        if (this.mUrlColor == null) {
            TypedValue $r2 = new TypedValue();
            this.mContext.getTheme().resolveAttribute(C0192R.attr.textColorSearchUrl, $r2, true);
            this.mUrlColor = this.mContext.getResources().getColorStateList($r2.resourceId);
        }
        SpannableString $r3 = new SpannableString($r1);
        $r3.setSpan(new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, $r1.length(), 33);
        return $r3;
    }

    private void setViewText(TextView $r1, CharSequence $r2) throws  {
        $r1.setText($r2);
        if (TextUtils.isEmpty($r2)) {
            $r1.setVisibility(8);
        } else {
            $r1.setVisibility(0);
        }
    }

    private Drawable getIcon1(Cursor $r1) throws  {
        if (this.mIconName1Col == -1) {
            return null;
        }
        Drawable $r3 = getDrawableFromResourceValue($r1.getString(this.mIconName1Col));
        return $r3 == null ? getDefaultIcon1($r1) : $r3;
    }

    private Drawable getIcon2(Cursor $r1) throws  {
        return this.mIconName2Col == -1 ? null : getDrawableFromResourceValue($r1.getString(this.mIconName2Col));
    }

    private void setViewDrawable(ImageView $r1, Drawable $r2, int $i0) throws  {
        $r1.setImageDrawable($r2);
        if ($r2 == null) {
            $r1.setVisibility($i0);
            return;
        }
        $r1.setVisibility(0);
        $r2.setVisible(false, false);
        $r2.setVisible(true, false);
    }

    public CharSequence convertToString(Cursor $r1) throws  {
        if ($r1 == null) {
            return null;
        }
        String $r2 = getColumnString($r1, "suggest_intent_query");
        if ($r2 != null) {
            return $r2;
        }
        if (this.mSearchable.shouldRewriteQueryFromData()) {
            $r2 = getColumnString($r1, "suggest_intent_data");
            if ($r2 != null) {
                return $r2;
            }
        }
        if (this.mSearchable.shouldRewriteQueryFromText()) {
            $r2 = getColumnString($r1, "suggest_text_1");
            if ($r2 != null) {
                return $r2;
            }
        }
        return null;
    }

    public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
        try {
            return super.getView($i0, $r1, $r2);
        } catch (RuntimeException $r3) {
            Log.w(LOG_TAG, "Search suggestions cursor threw exception.", $r3);
            $r1 = newView(this.mContext, this.mCursor, $r2);
            if ($r1 == null) {
                return $r1;
            }
            ((ChildViewCache) $r1.getTag()).mText1.setText($r3.toString());
            return $r1;
        }
    }

    private Drawable getDrawableFromResourceValue(String $r1) throws  {
        Drawable $r5;
        if ($r1 == null || $r1.length() == 0 || AppEventsConstants.EVENT_PARAM_VALUE_NO.equals($r1)) {
            return null;
        }
        try {
            int $i0 = Integer.parseInt($r1);
            String $r2 = "android.resource://" + this.mProviderContext.getPackageName() + "/" + $i0;
            $r5 = checkIconCache($r2);
            if ($r5 != null) {
                return $r5;
            }
            $r5 = ContextCompat.getDrawable(this.mProviderContext, $i0);
            storeInIconCache($r2, $r5);
            return $r5;
        } catch (NumberFormatException e) {
            $r5 = checkIconCache($r1);
            if ($r5 != null) {
                return $r5;
            }
            $r5 = getDrawable(Uri.parse($r1));
            storeInIconCache($r1, $r5);
            return $r5;
        } catch (NotFoundException e2) {
            Log.w(LOG_TAG, "Icon resource not found: " + $r1);
            return null;
        }
    }

    private Drawable getDrawable(Uri $r1) throws  {
        InputStream $r10;
        try {
            if ("android.resource".equals($r1.getScheme())) {
                return getDrawableFromResourceUri($r1);
            }
            $r10 = this.mProviderContext.getContentResolver().openInputStream($r1);
            if ($r10 == null) {
                throw new FileNotFoundException("Failed to open " + $r1);
            }
            Drawable $r5 = Drawable.createFromStream($r10, null);
            try {
                $r10.close();
                return $r5;
            } catch (IOException $r11) {
                Log.e(LOG_TAG, "Error closing icon stream for " + $r1, $r11);
                return $r5;
            }
        } catch (NotFoundException e) {
            throw new FileNotFoundException("Resource does not exist: " + $r1);
        } catch (FileNotFoundException $r2) {
            Log.w(LOG_TAG, "Icon not found: " + $r1 + ", " + $r2.getMessage());
            return null;
        } catch (Throwable th) {
            try {
                $r10.close();
            } catch (Throwable $r13) {
                Log.e(LOG_TAG, "Error closing icon stream for " + $r1, $r13);
            }
        }
    }

    private Drawable checkIconCache(String $r1) throws  {
        ConstantState $r4 = (ConstantState) this.mOutsideDrawablesCache.get($r1);
        return $r4 == null ? null : $r4.newDrawable();
    }

    private void storeInIconCache(String $r1, Drawable $r2) throws  {
        if ($r2 != null) {
            this.mOutsideDrawablesCache.put($r1, $r2.getConstantState());
        }
    }

    private Drawable getDefaultIcon1(Cursor cursor) throws  {
        Drawable $r4 = getActivityIconWithCache(this.mSearchable.getSearchActivity());
        return $r4 != null ? $r4 : this.mContext.getPackageManager().getDefaultActivityIcon();
    }

    private Drawable getActivityIconWithCache(ComponentName $r1) throws  {
        String $r2 = $r1.flattenToShortString();
        ConstantState $r5;
        if (this.mOutsideDrawablesCache.containsKey($r2)) {
            $r5 = (ConstantState) this.mOutsideDrawablesCache.get($r2);
            if ($r5 == null) {
                return null;
            }
            return $r5.newDrawable(this.mProviderContext.getResources());
        }
        Drawable $r8 = getActivityIcon($r1);
        if ($r8 == null) {
            $r5 = null;
        } else {
            $r5 = $r8.getConstantState();
        }
        this.mOutsideDrawablesCache.put($r2, $r5);
        return $r8;
    }

    private Drawable getActivityIcon(ComponentName $r1) throws  {
        PackageManager $r4 = this.mContext.getPackageManager();
        try {
            ActivityInfo $r5 = $r4.getActivityInfo($r1, 128);
            int $i0 = $r5.getIconResource();
            if ($i0 == 0) {
                return null;
            }
            Drawable $r8 = $r4.getDrawable($r1.getPackageName(), $i0, $r5.applicationInfo);
            if ($r8 != null) {
                return $r8;
            }
            Log.w(LOG_TAG, "Invalid icon resource " + $i0 + " for " + $r1.flattenToShortString());
            return null;
        } catch (NameNotFoundException $r2) {
            Log.w(LOG_TAG, $r2.toString());
            return null;
        }
    }

    public static String getColumnString(Cursor $r0, String $r1) throws  {
        return getStringOrNull($r0, $r0.getColumnIndex($r1));
    }

    private static String getStringOrNull(Cursor $r0, int $i0) throws  {
        if ($i0 == -1) {
            return null;
        }
        try {
            return $r0.getString($i0);
        } catch (Exception $r1) {
            Log.e(LOG_TAG, "unexpected error retrieving valid column from cursor, did the remote process die?", $r1);
            return null;
        }
    }

    Drawable getDrawableFromResourceUri(Uri $r1) throws FileNotFoundException {
        String $r4 = $r1.getAuthority();
        if (TextUtils.isEmpty($r4)) {
            throw new FileNotFoundException("No authority: " + $r1);
        }
        try {
            Resources $r9 = this.mContext.getPackageManager().getResourcesForApplication($r4);
            List $r10 = $r1.getPathSegments();
            if ($r10 == null) {
                throw new FileNotFoundException("No path: " + $r1);
            }
            int $i0 = $r10.size();
            if ($i0 == 1) {
                try {
                    $i0 = Integer.parseInt((String) $r10.get(0));
                } catch (NumberFormatException e) {
                    throw new FileNotFoundException("Single path segment is not a resource ID: " + $r1);
                }
            } else if ($i0 == 2) {
                $i0 = $r9.getIdentifier((String) $r10.get(1), (String) $r10.get(0), $r4);
            } else {
                throw new FileNotFoundException("More than two path segments: " + $r1);
            }
            if ($i0 != 0) {
                return $r9.getDrawable($i0);
            }
            throw new FileNotFoundException("No resource found for: " + $r1);
        } catch (NameNotFoundException e2) {
            throw new FileNotFoundException("No package found for authority: " + $r1);
        }
    }

    Cursor getSearchManagerSuggestions(SearchableInfo $r1, String $r2, int $i0) throws  {
        if ($r1 == null) {
            return null;
        }
        String $r3 = $r1.getSuggestAuthority();
        if ($r3 == null) {
            return null;
        }
        Builder $r4 = new Builder().scheme("content").authority($r3).query("").fragment("");
        $r3 = $r1.getSuggestPath();
        if ($r3 != null) {
            $r4.appendEncodedPath($r3);
        }
        $r4.appendPath("search_suggest_query");
        $r3 = $r1.getSuggestSelection();
        String[] $r5 = null;
        if ($r3 != null) {
            $r5 = new String[]{$r2};
        } else {
            $r4.appendPath($r2);
        }
        if ($i0 > 0) {
            $r4.appendQueryParameter("limit", String.valueOf($i0));
        }
        return this.mContext.getContentResolver().query($r4.build(), null, $r3, $r5, null);
    }
}
