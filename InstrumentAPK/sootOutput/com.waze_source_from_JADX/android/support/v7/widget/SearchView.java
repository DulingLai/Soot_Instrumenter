package android.support.v7.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.ResultReceiver;
import android.support.v4.view.KeyEventCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.CollapsibleActionView;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.KeyEvent.DispatcherState;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLayoutChangeListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.gms.actions.SearchIntents;
import dalvik.annotation.Signature;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class SearchView extends LinearLayoutCompat implements CollapsibleActionView {
    private static final boolean DBG = false;
    static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    private static final boolean IS_AT_LEAST_FROYO = (VERSION.SDK_INT >= 8);
    private static final String LOG_TAG = "SearchView";
    private Bundle mAppSearchData;
    private boolean mClearingFocus;
    private final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private final CharSequence mDefaultQueryHint;
    private final AppCompatDrawableManager mDrawableManager;
    private final View mDropDownAnchor;
    private boolean mExpandedInActionView;
    private final ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private CharSequence mOldQueryText;
    private final OnClickListener mOnClickListener;
    private OnCloseListener mOnCloseListener;
    private final OnEditorActionListener mOnEditorActionListener;
    private final OnItemClickListener mOnItemClickListener;
    private final OnItemSelectedListener mOnItemSelectedListener;
    private OnQueryTextListener mOnQueryChangeListener;
    private OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, ConstantState> mOutsideDrawablesCache;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable;
    private final ImageView mSearchButton;
    private final View mSearchEditFrame;
    private final Drawable mSearchHintIcon;
    private final View mSearchPlate;
    private final SearchAutoComplete mSearchSrcTextView;
    private SearchableInfo mSearchable;
    private Runnable mShowImeRunnable;
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    private CursorAdapter mSuggestionsAdapter;
    OnKeyListener mTextKeyListener;
    private TextWatcher mTextWatcher;
    private final Runnable mUpdateDrawableStateRunnable;
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    private final ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    class C02591 implements Runnable {
        C02591() throws  {
        }

        public void run() throws  {
            InputMethodManager $r4 = (InputMethodManager) SearchView.this.getContext().getSystemService("input_method");
            if ($r4 != null) {
                SearchView.HIDDEN_METHOD_INVOKER.showSoftInputUnchecked($r4, SearchView.this, 0);
            }
        }
    }

    class C02602 implements Runnable {
        C02602() throws  {
        }

        public void run() throws  {
            SearchView.this.updateFocusedState();
        }
    }

    class C02613 implements Runnable {
        C02613() throws  {
        }

        public void run() throws  {
            if (SearchView.this.mSuggestionsAdapter != null && (SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter)) {
                SearchView.this.mSuggestionsAdapter.changeCursor(null);
            }
        }
    }

    class C02624 implements OnFocusChangeListener {
        C02624() throws  {
        }

        public void onFocusChange(View v, boolean $z0) throws  {
            if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange(SearchView.this, $z0);
            }
        }
    }

    class C02635 implements OnLayoutChangeListener {
        C02635() throws  {
        }

        public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) throws  {
            SearchView.this.adjustDropDownSizeAndPosition();
        }
    }

    class C02646 implements OnGlobalLayoutListener {
        C02646() throws  {
        }

        public void onGlobalLayout() throws  {
            SearchView.this.adjustDropDownSizeAndPosition();
        }
    }

    class C02657 implements OnClickListener {
        C02657() throws  {
        }

        public void onClick(View $r1) throws  {
            if ($r1 == SearchView.this.mSearchButton) {
                SearchView.this.onSearchClicked();
            } else if ($r1 == SearchView.this.mCloseButton) {
                SearchView.this.onCloseClicked();
            } else if ($r1 == SearchView.this.mGoButton) {
                SearchView.this.onSubmitQuery();
            } else if ($r1 == SearchView.this.mVoiceButton) {
                SearchView.this.onVoiceClicked();
            } else if ($r1 == SearchView.this.mSearchSrcTextView) {
                SearchView.this.forceSuggestionQuery();
            }
        }
    }

    class C02668 implements OnKeyListener {
        C02668() throws  {
        }

        public boolean onKey(View $r1, int $i0, KeyEvent $r2) throws  {
            if (SearchView.this.mSearchable == null) {
                return false;
            }
            if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                return SearchView.this.onSuggestionsKey($r1, $i0, $r2);
            }
            if (SearchView.this.mSearchSrcTextView.isEmpty()) {
                return false;
            }
            if (!KeyEventCompat.hasNoModifiers($r2)) {
                return false;
            }
            if ($r2.getAction() != 1) {
                return false;
            }
            if ($i0 != 66) {
                return false;
            }
            $r1.cancelLongPress();
            SearchView.this.launchQuerySearch(0, null, SearchView.this.mSearchSrcTextView.getText().toString());
            return true;
        }
    }

    class C02679 implements OnEditorActionListener {
        C02679() throws  {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) throws  {
            SearchView.this.onSubmitQuery();
            return true;
        }
    }

    private static class AutoCompleteTextViewReflector {
        private Method doAfterTextChanged;
        private Method doBeforeTextChanged;
        private Method ensureImeVisible;
        private Method showSoftInputUnchecked;

        AutoCompleteTextViewReflector() throws  {
            try {
                this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.doBeforeTextChanged.setAccessible(true);
            } catch (NoSuchMethodException e) {
            }
            try {
                this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.doAfterTextChanged.setAccessible(true);
            } catch (NoSuchMethodException e2) {
            }
            try {
                this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", new Class[]{Boolean.TYPE});
                this.ensureImeVisible.setAccessible(true);
            } catch (NoSuchMethodException e3) {
            }
            try {
                this.showSoftInputUnchecked = InputMethodManager.class.getMethod("showSoftInputUnchecked", new Class[]{Integer.TYPE, ResultReceiver.class});
                this.showSoftInputUnchecked.setAccessible(true);
            } catch (NoSuchMethodException e4) {
            }
        }

        void doBeforeTextChanged(AutoCompleteTextView $r1) throws  {
            if (this.doBeforeTextChanged != null) {
                try {
                    this.doBeforeTextChanged.invoke($r1, new Object[0]);
                } catch (Exception e) {
                }
            }
        }

        void doAfterTextChanged(AutoCompleteTextView $r1) throws  {
            if (this.doAfterTextChanged != null) {
                try {
                    this.doAfterTextChanged.invoke($r1, new Object[0]);
                } catch (Exception e) {
                }
            }
        }

        void ensureImeVisible(AutoCompleteTextView $r1, boolean $z0) throws  {
            if (this.ensureImeVisible != null) {
                try {
                    this.ensureImeVisible.invoke($r1, new Object[]{Boolean.valueOf($z0)});
                } catch (Exception e) {
                }
            }
        }

        void showSoftInputUnchecked(InputMethodManager $r1, View $r2, int $i0) throws  {
            if (this.showSoftInputUnchecked != null) {
                try {
                    this.showSoftInputUnchecked.invoke($r1, new Object[]{Integer.valueOf($i0), null});
                    return;
                } catch (Exception e) {
                }
            }
            $r1.showSoftInput($r2, $i0);
        }
    }

    public interface OnCloseListener {
        boolean onClose() throws ;
    }

    public interface OnQueryTextListener {
        boolean onQueryTextChange(String str) throws ;

        boolean onQueryTextSubmit(String str) throws ;
    }

    public interface OnSuggestionListener {
        boolean onSuggestionClick(int i) throws ;

        boolean onSuggestionSelect(int i) throws ;
    }

    static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C02681();
        boolean isIconified;

        static class C02681 implements Creator<SavedState> {
            C02681() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        public SavedState(Parcel $r1) throws  {
            super($r1);
            this.isIconified = ((Boolean) $r1.readValue(null)).booleanValue();
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeValue(Boolean.valueOf(this.isIconified));
        }

        public String toString() throws  {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.isIconified + "}";
        }
    }

    public static class SearchAutoComplete extends AppCompatAutoCompleteTextView {
        private SearchView mSearchView;
        private int mThreshold;

        public SearchAutoComplete(Context $r1) throws  {
            this($r1, null);
        }

        public SearchAutoComplete(Context $r1, AttributeSet $r2) throws  {
            this($r1, $r2, C0192R.attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context $r1, AttributeSet $r2, int $i0) throws  {
            super($r1, $r2, $i0);
            this.mThreshold = getThreshold();
        }

        void setSearchView(SearchView $r1) throws  {
            this.mSearchView = $r1;
        }

        public void setThreshold(int $i0) throws  {
            super.setThreshold($i0);
            this.mThreshold = $i0;
        }

        private boolean isEmpty() throws  {
            return TextUtils.getTrimmedLength(getText()) == 0;
        }

        protected void replaceText(CharSequence text) throws  {
        }

        public void performCompletion() throws  {
        }

        public void onWindowFocusChanged(boolean $z0) throws  {
            super.onWindowFocusChanged($z0);
            if ($z0 && this.mSearchView.hasFocus() && getVisibility() == 0) {
                ((InputMethodManager) getContext().getSystemService("input_method")).showSoftInput(this, 0);
                if (SearchView.isLandscapeMode(getContext())) {
                    SearchView.HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
                }
            }
        }

        protected void onFocusChanged(boolean $z0, int $i0, Rect $r1) throws  {
            super.onFocusChanged($z0, $i0, $r1);
            this.mSearchView.onTextFocusChanged();
        }

        public boolean enoughToFilter() throws  {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        public boolean onKeyPreIme(int $i0, KeyEvent $r1) throws  {
            if ($i0 == 4) {
                DispatcherState $r2;
                if ($r1.getAction() == 0 && $r1.getRepeatCount() == 0) {
                    $r2 = getKeyDispatcherState();
                    if ($r2 == null) {
                        return true;
                    }
                    $r2.startTracking($r1, this);
                    return true;
                } else if ($r1.getAction() == 1) {
                    $r2 = getKeyDispatcherState();
                    if ($r2 != null) {
                        $r2.handleUpEvent($r1);
                    }
                    if ($r1.isTracking() && !$r1.isCanceled()) {
                        this.mSearchView.clearFocus();
                        this.mSearchView.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme($i0, $r1);
        }
    }

    private android.content.Intent createIntentFromSuggestion(android.database.Cursor r22, int r23, java.lang.String r24) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0036 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r21 = this;
        r8 = "suggest_intent_action";	 Catch:{ RuntimeException -> 0x0089 }
        r0 = r22;	 Catch:{ RuntimeException -> 0x0089 }
        r7 = android.support.v7.widget.SuggestionsAdapter.getColumnString(r0, r8);	 Catch:{ RuntimeException -> 0x0089 }
        r9 = r7;
        if (r7 != 0) goto L_0x001a;
    L_0x000c:
        r10 = android.os.Build.VERSION.SDK_INT;
        r11 = 8;	 Catch:{ RuntimeException -> 0x0089 }
        if (r10 < r11) goto L_0x001a;	 Catch:{ RuntimeException -> 0x0089 }
    L_0x0012:
        r0 = r21;	 Catch:{ RuntimeException -> 0x0089 }
        r12 = r0.mSearchable;	 Catch:{ RuntimeException -> 0x0089 }
        r9 = r12.getSuggestIntentAction();	 Catch:{ RuntimeException -> 0x0089 }
    L_0x001a:
        if (r9 != 0) goto L_0x001e;	 Catch:{ RuntimeException -> 0x0089 }
    L_0x001c:
        r9 = "android.intent.action.SEARCH";	 Catch:{ RuntimeException -> 0x0089 }
    L_0x001e:
        r8 = "suggest_intent_data";	 Catch:{ RuntimeException -> 0x0089 }
        r0 = r22;	 Catch:{ RuntimeException -> 0x0089 }
        r13 = android.support.v7.widget.SuggestionsAdapter.getColumnString(r0, r8);	 Catch:{ RuntimeException -> 0x0089 }
        r7 = r13;
        r14 = IS_AT_LEAST_FROYO;
        if (r14 == 0) goto L_0x0036;
    L_0x002c:
        if (r13 != 0) goto L_0x0036;	 Catch:{ RuntimeException -> 0x0089 }
    L_0x002e:
        r0 = r21;	 Catch:{ RuntimeException -> 0x0089 }
        r12 = r0.mSearchable;	 Catch:{ RuntimeException -> 0x0089 }
        r7 = r12.getSuggestIntentData();	 Catch:{ RuntimeException -> 0x0089 }
    L_0x0036:
        if (r7 == 0) goto L_0x005e;	 Catch:{ RuntimeException -> 0x0089 }
    L_0x0038:
        r8 = "suggest_intent_data_id";	 Catch:{ RuntimeException -> 0x0089 }
        r0 = r22;	 Catch:{ RuntimeException -> 0x0089 }
        r13 = android.support.v7.widget.SuggestionsAdapter.getColumnString(r0, r8);	 Catch:{ RuntimeException -> 0x0089 }
        if (r13 == 0) goto L_0x005e;
    L_0x0043:
        r15 = new java.lang.StringBuilder;	 Catch:{ RuntimeException -> 0x0089 }
        r15.<init>();	 Catch:{ RuntimeException -> 0x0089 }
        r15 = r15.append(r7);	 Catch:{ RuntimeException -> 0x0089 }
        r8 = "/";	 Catch:{ RuntimeException -> 0x0089 }
        r15 = r15.append(r8);	 Catch:{ RuntimeException -> 0x0089 }
        r7 = android.net.Uri.encode(r13);	 Catch:{ RuntimeException -> 0x0089 }
        r15 = r15.append(r7);	 Catch:{ RuntimeException -> 0x0089 }
        r7 = r15.toString();	 Catch:{ RuntimeException -> 0x0089 }
    L_0x005e:
        if (r7 != 0) goto L_0x0084;	 Catch:{ RuntimeException -> 0x0089 }
    L_0x0060:
        r16 = 0;	 Catch:{ RuntimeException -> 0x0089 }
    L_0x0062:
        r8 = "suggest_intent_query";	 Catch:{ RuntimeException -> 0x0089 }
        r0 = r22;	 Catch:{ RuntimeException -> 0x0089 }
        r7 = android.support.v7.widget.SuggestionsAdapter.getColumnString(r0, r8);	 Catch:{ RuntimeException -> 0x0089 }
        r8 = "suggest_intent_extra_data";	 Catch:{ RuntimeException -> 0x0089 }
        r0 = r22;	 Catch:{ RuntimeException -> 0x0089 }
        r13 = android.support.v7.widget.SuggestionsAdapter.getColumnString(r0, r8);	 Catch:{ RuntimeException -> 0x0089 }
        r0 = r21;	 Catch:{ RuntimeException -> 0x0089 }
        r1 = r9;	 Catch:{ RuntimeException -> 0x0089 }
        r2 = r16;	 Catch:{ RuntimeException -> 0x0089 }
        r3 = r13;	 Catch:{ RuntimeException -> 0x0089 }
        r4 = r7;	 Catch:{ RuntimeException -> 0x0089 }
        r5 = r23;	 Catch:{ RuntimeException -> 0x0089 }
        r6 = r24;	 Catch:{ RuntimeException -> 0x0089 }
        r17 = r0.createIntent(r1, r2, r3, r4, r5, r6);	 Catch:{ RuntimeException -> 0x0089 }
        return r17;
    L_0x0084:
        r16 = android.net.Uri.parse(r7);	 Catch:{ RuntimeException -> 0x0089 }
        goto L_0x0062;
    L_0x0089:
        r18 = move-exception;
        r0 = r22;	 Catch:{ RuntimeException -> 0x00b7 }
        r23 = r0.getPosition();	 Catch:{ RuntimeException -> 0x00b7 }
    L_0x0090:
        r15 = new java.lang.StringBuilder;
        r15.<init>();
        r8 = "Search suggestions cursor at row ";
        r15 = r15.append(r8);
        r0 = r23;
        r15 = r15.append(r0);
        r8 = " returned exception.";
        r15 = r15.append(r8);
        r24 = r15.toString();
        r8 = "SearchView";
        r0 = r24;
        r1 = r18;
        android.util.Log.w(r8, r0, r1);
        r19 = 0;
        return r19;
    L_0x00b7:
        r20 = move-exception;
        r23 = -1;
        goto L_0x0090;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.SearchView.createIntentFromSuggestion(android.database.Cursor, int, java.lang.String):android.content.Intent");
    }

    public SearchView(Context $r1) throws  {
        this($r1, null);
    }

    public SearchView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, C0192R.attr.searchViewStyle);
    }

    public SearchView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mShowImeRunnable = new C02591();
        this.mUpdateDrawableStateRunnable = new C02602();
        this.mReleaseCursorRunnable = new C02613();
        this.mOutsideDrawablesCache = new WeakHashMap();
        this.mOnClickListener = new C02657();
        this.mTextKeyListener = new C02668();
        this.mOnEditorActionListener = new C02679();
        this.mOnItemClickListener = new OnItemClickListener() {
            public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
                SearchView.this.onItemClicked($i0, 0, null);
            }
        };
        this.mOnItemSelectedListener = new OnItemSelectedListener() {
            public void onItemSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
                SearchView.this.onItemSelected($i0);
            }

            public void onNothingSelected(@Signature({"(", "Landroid/widget/AdapterView", "<*>;)V"}) AdapterView<?> adapterView) throws  {
            }
        };
        this.mTextWatcher = new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int before, int after) throws  {
            }

            public void onTextChanged(CharSequence $r1, int start, int before, int after) throws  {
                SearchView.this.onTextChanged($r1);
            }

            public void afterTextChanged(Editable s) throws  {
            }
        };
        this.mDrawableManager = AppCompatDrawableManager.get();
        TintTypedArray $r15 = TintTypedArray.obtainStyledAttributes($r1, $r2, C0192R.styleable.SearchView, $i0, 0);
        LayoutInflater.from($r1).inflate($r15.getResourceId(C0192R.styleable.SearchView_layout, C0192R.layout.abc_search_view), this, true);
        this.mSearchSrcTextView = (SearchAutoComplete) findViewById(C0192R.id.search_src_text);
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        SearchAutoComplete $r18 = searchAutoComplete;
        searchAutoComplete.setSearchView(this);
        this.mSearchEditFrame = findViewById(C0192R.id.search_edit_frame);
        this.mSearchPlate = findViewById(C0192R.id.search_plate);
        this.mSubmitArea = findViewById(C0192R.id.submit_area);
        this.mSearchButton = (ImageView) findViewById(C0192R.id.search_button);
        this.mGoButton = (ImageView) findViewById(C0192R.id.search_go_btn);
        this.mCloseButton = (ImageView) findViewById(C0192R.id.search_close_btn);
        this.mVoiceButton = (ImageView) findViewById(C0192R.id.search_voice_btn);
        this.mCollapsedIcon = (ImageView) findViewById(C0192R.id.search_mag_icon);
        this.mSearchPlate.setBackgroundDrawable($r15.getDrawable(C0192R.styleable.SearchView_queryBackground));
        this.mSubmitArea.setBackgroundDrawable($r15.getDrawable(C0192R.styleable.SearchView_submitBackground));
        this.mSearchButton.setImageDrawable($r15.getDrawable(C0192R.styleable.SearchView_searchIcon));
        this.mGoButton.setImageDrawable($r15.getDrawable(C0192R.styleable.SearchView_goIcon));
        this.mCloseButton.setImageDrawable($r15.getDrawable(C0192R.styleable.SearchView_closeIcon));
        this.mVoiceButton.setImageDrawable($r15.getDrawable(C0192R.styleable.SearchView_voiceIcon));
        this.mCollapsedIcon.setImageDrawable($r15.getDrawable(C0192R.styleable.SearchView_searchIcon));
        this.mSearchHintIcon = $r15.getDrawable(C0192R.styleable.SearchView_searchHintIcon);
        this.mSuggestionRowLayout = $r15.getResourceId(C0192R.styleable.SearchView_suggestionRowLayout, C0192R.layout.abc_search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = $r15.getResourceId(C0192R.styleable.SearchView_commitIcon, 0);
        this.mSearchButton.setOnClickListener(this.mOnClickListener);
        this.mCloseButton.setOnClickListener(this.mOnClickListener);
        this.mGoButton.setOnClickListener(this.mOnClickListener);
        this.mVoiceButton.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
        this.mSearchSrcTextView.setOnFocusChangeListener(new C02624());
        setIconifiedByDefault($r15.getBoolean(C0192R.styleable.SearchView_iconifiedByDefault, true));
        $i0 = $r15.getDimensionPixelSize(C0192R.styleable.SearchView_android_maxWidth, -1);
        if ($i0 != -1) {
            setMaxWidth($i0);
        }
        this.mDefaultQueryHint = $r15.getText(C0192R.styleable.SearchView_defaultQueryHint);
        this.mQueryHint = $r15.getText(C0192R.styleable.SearchView_queryHint);
        $i0 = $r15.getInt(C0192R.styleable.SearchView_android_imeOptions, -1);
        if ($i0 != -1) {
            setImeOptions($i0);
        }
        $i0 = $r15.getInt(C0192R.styleable.SearchView_android_inputType, -1);
        if ($i0 != -1) {
            setInputType($i0);
        }
        setFocusable($r15.getBoolean(C0192R.styleable.SearchView_android_focusable, true));
        $r15.recycle();
        this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
        this.mVoiceWebSearchIntent.addFlags(268435456);
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.mVoiceAppSearchIntent.addFlags(268435456);
        searchAutoComplete = this.mSearchSrcTextView;
        this.mDropDownAnchor = findViewById(searchAutoComplete.getDropDownAnchor());
        if (this.mDropDownAnchor != null) {
            if (VERSION.SDK_INT >= 11) {
                addOnLayoutChangeListenerToDropDownAnchorSDK11();
            } else {
                addOnLayoutChangeListenerToDropDownAnchorBase();
            }
        }
        updateViewsVisibility(this.mIconifiedByDefault);
        updateQueryHint();
    }

    @TargetApi(11)
    private void addOnLayoutChangeListenerToDropDownAnchorSDK11() throws  {
        this.mDropDownAnchor.addOnLayoutChangeListener(new C02635());
    }

    private void addOnLayoutChangeListenerToDropDownAnchorBase() throws  {
        this.mDropDownAnchor.getViewTreeObserver().addOnGlobalLayoutListener(new C02646());
    }

    int getSuggestionRowLayout() throws  {
        return this.mSuggestionRowLayout;
    }

    int getSuggestionCommitIconResId() throws  {
        return this.mSuggestionCommitIconResId;
    }

    public void setSearchableInfo(SearchableInfo $r1) throws  {
        this.mSearchable = $r1;
        if (this.mSearchable != null) {
            if (IS_AT_LEAST_FROYO) {
                updateSearchAutoComplete();
            }
            updateQueryHint();
        }
        boolean $z0 = IS_AT_LEAST_FROYO && hasVoiceSearch();
        this.mVoiceButtonEnabled = $z0;
        if (this.mVoiceButtonEnabled) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        updateViewsVisibility(isIconified());
    }

    public void setAppSearchData(Bundle $r1) throws  {
        this.mAppSearchData = $r1;
    }

    public void setImeOptions(int $i0) throws  {
        this.mSearchSrcTextView.setImeOptions($i0);
    }

    public int getImeOptions() throws  {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public void setInputType(int $i0) throws  {
        this.mSearchSrcTextView.setInputType($i0);
    }

    public int getInputType() throws  {
        return this.mSearchSrcTextView.getInputType();
    }

    public boolean requestFocus(int $i0, Rect $r1) throws  {
        if (this.mClearingFocus) {
            return false;
        }
        if (!isFocusable()) {
            return false;
        }
        if (isIconified()) {
            return super.requestFocus($i0, $r1);
        }
        boolean $z0 = this.mSearchSrcTextView.requestFocus($i0, $r1);
        if (!$z0) {
            return $z0;
        }
        updateViewsVisibility(false);
        return $z0;
    }

    public void clearFocus() throws  {
        this.mClearingFocus = true;
        setImeVisibility(false);
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mClearingFocus = false;
    }

    public void setOnQueryTextListener(OnQueryTextListener $r1) throws  {
        this.mOnQueryChangeListener = $r1;
    }

    public void setOnCloseListener(OnCloseListener $r1) throws  {
        this.mOnCloseListener = $r1;
    }

    public void setOnQueryTextFocusChangeListener(OnFocusChangeListener $r1) throws  {
        this.mOnQueryTextFocusChangeListener = $r1;
    }

    public void setOnSuggestionListener(OnSuggestionListener $r1) throws  {
        this.mOnSuggestionListener = $r1;
    }

    public void setOnSearchClickListener(OnClickListener $r1) throws  {
        this.mOnSearchClickListener = $r1;
    }

    public CharSequence getQuery() throws  {
        return this.mSearchSrcTextView.getText();
    }

    public void setQuery(CharSequence $r1, boolean $z0) throws  {
        this.mSearchSrcTextView.setText($r1);
        if ($r1 != null) {
            this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length());
            this.mUserQuery = $r1;
        }
        if ($z0 && !TextUtils.isEmpty($r1)) {
            onSubmitQuery();
        }
    }

    public void setQueryHint(CharSequence $r1) throws  {
        this.mQueryHint = $r1;
        updateQueryHint();
    }

    public CharSequence getQueryHint() throws  {
        if (this.mQueryHint != null) {
            return this.mQueryHint;
        }
        if (!IS_AT_LEAST_FROYO || this.mSearchable == null || this.mSearchable.getHintId() == 0) {
            return this.mDefaultQueryHint;
        }
        return getContext().getText(this.mSearchable.getHintId());
    }

    public void setIconifiedByDefault(boolean $z0) throws  {
        if (this.mIconifiedByDefault != $z0) {
            this.mIconifiedByDefault = $z0;
            updateViewsVisibility($z0);
            updateQueryHint();
        }
    }

    public boolean isIconfiedByDefault() throws  {
        return this.mIconifiedByDefault;
    }

    public void setIconified(boolean $z0) throws  {
        if ($z0) {
            onCloseClicked();
        } else {
            onSearchClicked();
        }
    }

    public boolean isIconified() throws  {
        return this.mIconified;
    }

    public void setSubmitButtonEnabled(boolean $z0) throws  {
        this.mSubmitButtonEnabled = $z0;
        updateViewsVisibility(isIconified());
    }

    public boolean isSubmitButtonEnabled() throws  {
        return this.mSubmitButtonEnabled;
    }

    public void setQueryRefinementEnabled(boolean $z0) throws  {
        this.mQueryRefinement = $z0;
        if (this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
            ((SuggestionsAdapter) this.mSuggestionsAdapter).setQueryRefinement($z0 ? (byte) 2 : (byte) 1);
        }
    }

    public boolean isQueryRefinementEnabled() throws  {
        return this.mQueryRefinement;
    }

    public void setSuggestionsAdapter(CursorAdapter $r1) throws  {
        this.mSuggestionsAdapter = $r1;
        this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
    }

    public CursorAdapter getSuggestionsAdapter() throws  {
        return this.mSuggestionsAdapter;
    }

    public void setMaxWidth(int $i0) throws  {
        this.mMaxWidth = $i0;
        requestLayout();
    }

    public int getMaxWidth() throws  {
        return this.mMaxWidth;
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        if (isIconified()) {
            super.onMeasure($i0, $i1);
            return;
        }
        int $i2 = MeasureSpec.getMode($i0);
        $i0 = MeasureSpec.getSize($i0);
        int $i3 = $i0;
        switch ($i2) {
            case Integer.MIN_VALUE:
                if (this.mMaxWidth <= 0) {
                    $i3 = Math.min(getPreferredWidth(), $i0);
                    break;
                } else {
                    $i3 = Math.min(this.mMaxWidth, $i0);
                    break;
                }
            case 0:
                $i3 = this.mMaxWidth > 0 ? this.mMaxWidth : getPreferredWidth();
                break;
            case 1073741824:
                if (this.mMaxWidth > 0) {
                    $i3 = Math.min(this.mMaxWidth, $i0);
                    break;
                }
                break;
            default:
                break;
        }
        super.onMeasure(MeasureSpec.makeMeasureSpec($i3, 1073741824), $i1);
    }

    private int getPreferredWidth() throws  {
        return getContext().getResources().getDimensionPixelSize(C0192R.dimen.abc_search_view_preferred_width);
    }

    private void updateViewsVisibility(boolean $z0) throws  {
        byte $b1;
        boolean $z2;
        byte $b0 = (byte) 8;
        boolean $z1 = true;
        this.mIconified = $z0;
        if ($z0) {
            $b1 = (byte) 0;
        } else {
            $b1 = (byte) 8;
        }
        if (TextUtils.isEmpty(this.mSearchSrcTextView.getText())) {
            $z2 = false;
        } else {
            $z2 = true;
        }
        this.mSearchButton.setVisibility($b1);
        updateSubmitButton($z2);
        View $r4 = this.mSearchEditFrame;
        if (!$z0) {
            $b0 = (byte) 0;
        }
        $r4.setVisibility($b0);
        if (this.mCollapsedIcon.getDrawable() == null || this.mIconifiedByDefault) {
            $b0 = (byte) 8;
        } else {
            $b0 = (byte) 0;
        }
        this.mCollapsedIcon.setVisibility($b0);
        updateCloseButton();
        if ($z2) {
            $z1 = false;
        }
        updateVoiceButton($z1);
        updateSubmitArea();
    }

    @TargetApi(8)
    private boolean hasVoiceSearch() throws  {
        if (this.mSearchable == null) {
            return false;
        }
        if (!this.mSearchable.getVoiceSearchEnabled()) {
            return false;
        }
        Intent $r2 = null;
        if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
            $r2 = this.mVoiceWebSearchIntent;
        } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
            $r2 = this.mVoiceAppSearchIntent;
        }
        if ($r2 == null) {
            return false;
        }
        if (getContext().getPackageManager().resolveActivity($r2, 65536) != null) {
            return true;
        }
        return false;
    }

    private boolean isSubmitAreaEnabled() throws  {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !isIconified();
    }

    private void updateSubmitButton(boolean $z0) throws  {
        byte $b0 = (byte) 8;
        if (this.mSubmitButtonEnabled && isSubmitAreaEnabled() && hasFocus() && ($z0 || !this.mVoiceButtonEnabled)) {
            $b0 = (byte) 0;
        }
        this.mGoButton.setVisibility($b0);
    }

    private void updateSubmitArea() throws  {
        byte $b0 = (byte) 8;
        if (isSubmitAreaEnabled() && (this.mGoButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0)) {
            $b0 = (byte) 0;
        }
        this.mSubmitArea.setVisibility($b0);
    }

    private void updateCloseButton() throws  {
        boolean $z0 = true;
        byte $b0 = (byte) 0;
        boolean $z1 = !TextUtils.isEmpty(this.mSearchSrcTextView.getText());
        if (!$z1 && (!this.mIconifiedByDefault || this.mExpandedInActionView)) {
            $z0 = false;
        }
        ImageView $r3 = this.mCloseButton;
        if (!$z0) {
            $b0 = (byte) 8;
        }
        $r3.setVisibility($b0);
        Drawable $r4 = this.mCloseButton.getDrawable();
        if ($r4 != null) {
            int[] $r5;
            if ($z1) {
                $r5 = View.ENABLED_STATE_SET;
            } else {
                $r5 = View.EMPTY_STATE_SET;
            }
            $r4.setState($r5);
        }
    }

    private void postUpdateFocusedState() throws  {
        post(this.mUpdateDrawableStateRunnable);
    }

    private void updateFocusedState() throws  {
        int[] $r2;
        if (this.mSearchSrcTextView.hasFocus()) {
            $r2 = View.FOCUSED_STATE_SET;
        } else {
            $r2 = View.EMPTY_STATE_SET;
        }
        Drawable $r4 = this.mSearchPlate.getBackground();
        if ($r4 != null) {
            $r4.setState($r2);
        }
        $r4 = this.mSubmitArea.getBackground();
        if ($r4 != null) {
            $r4.setState($r2);
        }
        invalidate();
    }

    protected void onDetachedFromWindow() throws  {
        removeCallbacks(this.mUpdateDrawableStateRunnable);
        post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    private void setImeVisibility(boolean $z0) throws  {
        if ($z0) {
            post(this.mShowImeRunnable);
            return;
        }
        removeCallbacks(this.mShowImeRunnable);
        InputMethodManager $r4 = (InputMethodManager) getContext().getSystemService("input_method");
        if ($r4 != null) {
            $r4.hideSoftInputFromWindow(getWindowToken(), 0);
        }
    }

    void onQueryRefine(CharSequence $r1) throws  {
        setQuery($r1);
    }

    private boolean onSuggestionsKey(View v, int $i0, KeyEvent $r2) throws  {
        if (this.mSearchable == null) {
            return false;
        }
        if (this.mSuggestionsAdapter == null) {
            return false;
        }
        if ($r2.getAction() != 0) {
            return false;
        }
        if (!KeyEventCompat.hasNoModifiers($r2)) {
            return false;
        }
        if ($i0 == 66 || $i0 == 84 || $i0 == 61) {
            return onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
        }
        if ($i0 == 21 || $i0 == 22) {
            if ($i0 == 21) {
                $i0 = 0;
            } else {
                $i0 = this.mSearchSrcTextView.length();
            }
            this.mSearchSrcTextView.setSelection($i0);
            this.mSearchSrcTextView.setListSelection(0);
            this.mSearchSrcTextView.clearListSelection();
            HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mSearchSrcTextView, true);
            return true;
        } else if ($i0 == 19) {
            return this.mSearchSrcTextView.getListSelection() == 0 ? false : false;
        } else {
            return false;
        }
    }

    private CharSequence getDecoratedHint(CharSequence $r1) throws  {
        boolean $z0 = this.mIconifiedByDefault;
        this = this;
        if (!$z0 || this.mSearchHintIcon == null) {
            return $r1;
        }
        int $i0 = (int) (((double) this.mSearchSrcTextView.getTextSize()) * 1.25d);
        this.mSearchHintIcon.setBounds(0, 0, $i0, $i0);
        SpannableStringBuilder $r3 = new SpannableStringBuilder("   ");
        $r3.setSpan(new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
        $r3.append($r1);
        return $r3;
    }

    private void updateQueryHint() throws  {
        CharSequence $r2 = getQueryHint();
        CharSequence $r3 = $r2;
        SearchAutoComplete $r1 = this.mSearchSrcTextView;
        if ($r2 == null) {
            $r3 = "";
        }
        $r1.setHint(getDecoratedHint($r3));
    }

    @TargetApi(8)
    private void updateSearchAutoComplete() throws  {
        byte $b0 = (byte) 1;
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int $i1 = this.mSearchable.getInputType();
        int $i2 = $i1;
        if (($i1 & 15) == 1) {
            $i2 = $i1 & -65537;
            if (this.mSearchable.getSuggestAuthority() != null) {
                $i2 = ($i2 | 65536) | 524288;
            }
        }
        this.mSearchSrcTextView.setInputType($i2);
        if (this.mSuggestionsAdapter != null) {
            this.mSuggestionsAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            this.mSuggestionsAdapter = new SuggestionsAdapter(getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSearchSrcTextView.setAdapter(this.mSuggestionsAdapter);
            SuggestionsAdapter $r6 = (SuggestionsAdapter) this.mSuggestionsAdapter;
            if (this.mQueryRefinement) {
                $b0 = (byte) 2;
            }
            $r6.setQueryRefinement($b0);
        }
    }

    private void updateVoiceButton(boolean $z0) throws  {
        byte $b0 = (byte) 8;
        if (this.mVoiceButtonEnabled && !isIconified() && $z0) {
            $b0 = (byte) 0;
            this.mGoButton.setVisibility(8);
        }
        this.mVoiceButton.setVisibility($b0);
    }

    private void onTextChanged(CharSequence $r1) throws  {
        boolean $z0 = true;
        Editable $r3 = this.mSearchSrcTextView.getText();
        this.mUserQuery = $r3;
        boolean $z1 = !TextUtils.isEmpty($r3);
        updateSubmitButton($z1);
        if ($z1) {
            $z0 = false;
        }
        updateVoiceButton($z0);
        updateCloseButton();
        updateSubmitArea();
        if (!(this.mOnQueryChangeListener == null || TextUtils.equals($r1, this.mOldQueryText))) {
            this.mOnQueryChangeListener.onQueryTextChange($r1.toString());
        }
        this.mOldQueryText = $r1.toString();
    }

    private void onSubmitQuery() throws  {
        Editable $r2 = this.mSearchSrcTextView.getText();
        if ($r2 != null && TextUtils.getTrimmedLength($r2) > 0) {
            if (this.mOnQueryChangeListener == null || !this.mOnQueryChangeListener.onQueryTextSubmit($r2.toString())) {
                if (this.mSearchable != null) {
                    launchQuerySearch(0, null, $r2.toString());
                }
                setImeVisibility(false);
                dismissSuggestions();
            }
        }
    }

    private void dismissSuggestions() throws  {
        this.mSearchSrcTextView.dismissDropDown();
    }

    private void onCloseClicked() throws  {
        if (!TextUtils.isEmpty(this.mSearchSrcTextView.getText())) {
            this.mSearchSrcTextView.setText("");
            this.mSearchSrcTextView.requestFocus();
            setImeVisibility(true);
        } else if (!this.mIconifiedByDefault) {
        } else {
            if (this.mOnCloseListener == null || !this.mOnCloseListener.onClose()) {
                clearFocus();
                updateViewsVisibility(true);
            }
        }
    }

    private void onSearchClicked() throws  {
        updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        setImeVisibility(true);
        if (this.mOnSearchClickListener != null) {
            this.mOnSearchClickListener.onClick(this);
        }
    }

    @TargetApi(8)
    private void onVoiceClicked() throws  {
        if (this.mSearchable != null) {
            SearchableInfo $r2 = this.mSearchable;
            try {
                if ($r2.getVoiceSearchLaunchWebSearch()) {
                    getContext().startActivity(createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, $r2));
                } else if ($r2.getVoiceSearchLaunchRecognizer()) {
                    getContext().startActivity(createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, $r2));
                }
            } catch (ActivityNotFoundException e) {
                Log.w(LOG_TAG, "Could not find voice search activity");
            }
        }
    }

    void onTextFocusChanged() throws  {
        updateViewsVisibility(isIconified());
        postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            forceSuggestionQuery();
        }
    }

    public void onWindowFocusChanged(boolean $z0) throws  {
        super.onWindowFocusChanged($z0);
        postUpdateFocusedState();
    }

    public void onActionViewCollapsed() throws  {
        setQuery("", false);
        clearFocus();
        updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    public void onActionViewExpanded() throws  {
        if (!this.mExpandedInActionView) {
            this.mExpandedInActionView = true;
            this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions();
            this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions | 33554432);
            this.mSearchSrcTextView.setText("");
            setIconified(false);
        }
    }

    protected Parcelable onSaveInstanceState() throws  {
        SavedState $r1 = new SavedState(super.onSaveInstanceState());
        $r1.isIconified = isIconified();
        return $r1;
    }

    protected void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            updateViewsVisibility($r2.isIconified);
            requestLayout();
            return;
        }
        super.onRestoreInstanceState($r1);
    }

    private void adjustDropDownSizeAndPosition() throws  {
        if (this.mDropDownAnchor.getWidth() > 1) {
            int $i2;
            Resources $r4 = getContext().getResources();
            int $i0 = this.mSearchPlate.getPaddingLeft();
            Rect $r1 = new Rect();
            boolean $z0 = ViewUtils.isLayoutRtl(this);
            int $i1 = this.mIconifiedByDefault ? $r4.getDimensionPixelSize(C0192R.dimen.abc_dropdownitem_icon_width) + $r4.getDimensionPixelSize(C0192R.dimen.abc_dropdownitem_text_padding_left) : 0;
            this.mSearchSrcTextView.getDropDownBackground().getPadding($r1);
            if ($z0) {
                $i2 = -$r1.left;
            } else {
                $i2 = $i0 - ($r1.left + $i1);
            }
            this.mSearchSrcTextView.setDropDownHorizontalOffset($i2);
            this.mSearchSrcTextView.setDropDownWidth((((this.mDropDownAnchor.getWidth() + $r1.left) + $r1.right) + $i1) - $i0);
        }
    }

    private boolean onItemClicked(int $i0, int actionKey, String actionMsg) throws  {
        if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionClick($i0)) {
            return false;
        }
        launchSuggestion($i0, 0, null);
        setImeVisibility(false);
        dismissSuggestions();
        return true;
    }

    private boolean onItemSelected(int $i0) throws  {
        if (this.mOnSuggestionListener != null && this.mOnSuggestionListener.onSuggestionSelect($i0)) {
            return false;
        }
        rewriteQueryFromSuggestion($i0);
        return true;
    }

    private void rewriteQueryFromSuggestion(int $i0) throws  {
        Editable $r2 = this.mSearchSrcTextView.getText();
        Cursor $r4 = this.mSuggestionsAdapter.getCursor();
        if ($r4 != null) {
            if ($r4.moveToPosition($i0)) {
                CharSequence $r5 = this.mSuggestionsAdapter.convertToString($r4);
                if ($r5 != null) {
                    setQuery($r5);
                    return;
                } else {
                    setQuery($r2);
                    return;
                }
            }
            setQuery($r2);
        }
    }

    private boolean launchSuggestion(int $i0, int $i1, String $r1) throws  {
        Cursor $r3 = this.mSuggestionsAdapter.getCursor();
        if ($r3 == null || !$r3.moveToPosition($i0)) {
            return false;
        }
        launchIntent(createIntentFromSuggestion($r3, $i1, $r1));
        return true;
    }

    private void launchIntent(Intent $r1) throws  {
        if ($r1 != null) {
            try {
                getContext().startActivity($r1);
            } catch (RuntimeException $r2) {
                Log.e(LOG_TAG, "Failed launch activity: " + $r1, $r2);
            }
        }
    }

    private void setQuery(CharSequence $r1) throws  {
        this.mSearchSrcTextView.setText($r1);
        this.mSearchSrcTextView.setSelection(TextUtils.isEmpty($r1) ? 0 : $r1.length());
    }

    private void launchQuerySearch(int $i0, String $r1, String $r2) throws  {
        getContext().startActivity(createIntent("android.intent.action.SEARCH", null, null, $r2, $i0, $r1));
    }

    private Intent createIntent(String $r1, Uri $r2, String $r3, String $r4, int $i0, String $r5) throws  {
        Intent $r6 = new Intent($r1);
        $r6.addFlags(268435456);
        if ($r2 != null) {
            $r6.setData($r2);
        }
        $r6.putExtra("user_query", this.mUserQuery);
        if ($r4 != null) {
            $r6.putExtra(SearchIntents.EXTRA_QUERY, $r4);
        }
        if ($r3 != null) {
            $r6.putExtra("intent_extra_data_key", $r3);
        }
        if (this.mAppSearchData != null) {
            $r6.putExtra("app_data", this.mAppSearchData);
        }
        if ($i0 != 0) {
            $r6.putExtra("action_key", $i0);
            $r6.putExtra("action_msg", $r5);
        }
        if (!IS_AT_LEAST_FROYO) {
            return $r6;
        }
        $r6.setComponent(this.mSearchable.getSearchActivity());
        return $r6;
    }

    @TargetApi(8)
    private Intent createVoiceWebSearchIntent(Intent $r1, SearchableInfo $r2) throws  {
        String $r5;
        Intent $r3 = new Intent($r1);
        ComponentName $r4 = $r2.getSearchActivity();
        if ($r4 == null) {
            $r5 = null;
        } else {
            $r5 = $r4.flattenToShortString();
        }
        $r3.putExtra("calling_package", $r5);
        return $r3;
    }

    @TargetApi(8)
    private Intent createVoiceAppSearchIntent(Intent $r1, SearchableInfo $r2) throws  {
        ComponentName $r5 = $r2.getSearchActivity();
        Intent $r4 = new Intent("android.intent.action.SEARCH");
        $r4.setComponent($r5);
        PendingIntent $r7 = PendingIntent.getActivity(getContext(), 0, $r4, 1073741824);
        Bundle $r3 = new Bundle();
        if (this.mAppSearchData != null) {
            $r3.putParcelable("app_data", this.mAppSearchData);
        }
        $r4 = new Intent($r1);
        String $r9 = "free_form";
        String $r10 = null;
        String $r11 = null;
        int $i0 = 1;
        if (VERSION.SDK_INT >= 8) {
            Resources $r12 = getResources();
            if ($r2.getVoiceLanguageModeId() != 0) {
                $r9 = $r12.getString($r2.getVoiceLanguageModeId());
            }
            if ($r2.getVoicePromptTextId() != 0) {
                $r10 = $r12.getString($r2.getVoicePromptTextId());
            }
            if ($r2.getVoiceLanguageId() != 0) {
                $r11 = $r12.getString($r2.getVoiceLanguageId());
            }
            if ($r2.getVoiceMaxResults() != 0) {
                $i0 = $r2.getVoiceMaxResults();
            }
        }
        $r4.putExtra("android.speech.extra.LANGUAGE_MODEL", $r9);
        $r4.putExtra("android.speech.extra.PROMPT", $r10);
        $r4.putExtra("android.speech.extra.LANGUAGE", $r11);
        $r4.putExtra("android.speech.extra.MAX_RESULTS", $i0);
        if ($r5 == null) {
            $r9 = null;
        } else {
            $r9 = $r5.flattenToShortString();
        }
        $r4.putExtra("calling_package", $r9);
        $r4.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", $r7);
        $r4.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", $r3);
        return $r4;
    }

    private void forceSuggestionQuery() throws  {
        HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mSearchSrcTextView);
        HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mSearchSrcTextView);
    }

    static boolean isLandscapeMode(Context $r0) throws  {
        return $r0.getResources().getConfiguration().orientation == 2;
    }
}
