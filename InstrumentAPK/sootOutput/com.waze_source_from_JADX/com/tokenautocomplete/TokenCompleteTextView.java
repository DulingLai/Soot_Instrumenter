package com.tokenautocomplete;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Layout;
import android.text.SpanWatcher;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.QwertyKeyListener;
import android.text.style.ReplacementSpan;
import android.text.style.TextAppearanceSpan;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.Filter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView.CommaTokenizer;
import android.widget.MultiAutoCompleteTextView.Tokenizer;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import dalvik.annotation.Signature;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class TokenCompleteTextView extends MultiAutoCompleteTextView implements OnEditorActionListener {
    static final /* synthetic */ boolean $assertionsDisabled = (!TokenCompleteTextView.class.desiredAssertionStatus());
    private boolean allowCollapse = true;
    private boolean allowDuplicates = true;
    private TokenDeleteStyle deletionStyle = TokenDeleteStyle._Parent;
    private boolean hintVisible = false;
    boolean inInvalidate = false;
    private boolean initialized = false;
    private Layout lastLayout = null;
    private TokenListener listener;
    private ViewListener mViewListener;
    private ArrayList<Object> objects;
    private String prefix = "";
    private boolean savingState = false;
    private Object selectedObject;
    private boolean shouldFocusNext = false;
    private TokenSpanWatcher spanWatcher;
    private TokenClickStyle tokenClickStyle = TokenClickStyle.None;
    private Tokenizer tokenizer;

    class C10871 implements InputFilter {
        C10871() throws  {
        }

        public CharSequence filter(CharSequence $r1, int start, int end, Spanned dest, int $i2, int $i3) throws  {
            if ($r1.length() != 1 || $r1.charAt(0) != ',') {
                return ($i2 >= TokenCompleteTextView.this.prefix.length() || $i3 != TokenCompleteTextView.this.prefix.length()) ? null : TokenCompleteTextView.this.prefix.substring($i2, $i3);
            } else {
                TokenCompleteTextView.this.performCompletion();
                return "";
            }
        }
    }

    class C10904 implements Runnable {
        C10904() throws  {
        }

        public void run() throws  {
            TokenCompleteTextView.this.handleFocus(TokenCompleteTextView.this.isFocused());
        }
    }

    private class ViewSpan extends ReplacementSpan {
        protected float drawX;
        protected float drawY;
        protected View view;

        public ViewSpan(View $r2) throws  {
            this.view = $r2;
        }

        private void prepView() throws  {
            this.view.measure(MeasureSpec.makeMeasureSpec((int) TokenCompleteTextView.this.maxTextWidth(), Integer.MIN_VALUE), MeasureSpec.makeMeasureSpec(0, 0));
            this.view.layout(0, 0, this.view.getMeasuredWidth(), this.view.getMeasuredHeight());
        }

        public void draw(Canvas $r1, CharSequence text, int start, int end, float $f0, int $i2, int y, int $i4, Paint paint) throws  {
            prepView();
            $r1.save();
            start = ($i4 - this.view.getBottom()) - ((($i4 - $i2) - this.view.getBottom()) / 2);
            $r1.translate($f0, (float) start);
            this.view.draw($r1);
            $r1.restore();
            this.drawX = $f0;
            this.drawY = (float) start;
        }

        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, FontMetricsInt $r3) throws  {
            prepView();
            if ($r3 != null) {
                i = this.view.getMeasuredHeight() - ($r3.descent - $r3.ascent);
                if (i > 0) {
                    i2 = i / 2;
                    $r3.descent += i - i2;
                    $r3.ascent -= i2;
                    $r3.bottom += i - i2;
                    $r3.top -= i / 2;
                }
            }
            return this.view.getRight();
        }
    }

    private class CountSpan extends ViewSpan {
        private int count;
        public String text = "";

        public CountSpan(int $i0, Context $r2, int $i1, int $i2, int $i3) throws  {
            super(new TextView($r2));
            TextView $r3 = (TextView) this.view;
            $r3.setLayoutParams(new LayoutParams(-2, -2));
            $r3.setTextColor($i1);
            $r3.setTextSize(0, (float) $i2);
            $r3.setMinimumWidth($i3);
            setCount($i0);
        }

        public void setCount(int $i0) throws  {
            this.count = $i0;
            this.text = "+" + this.count;
            ((TextView) this.view).setText(this.text);
        }

        public int getCount() throws  {
            return this.count;
        }
    }

    public static class HintSpan extends TextAppearanceSpan {
        public HintSpan(String $r1, int $i0, int $i1, ColorStateList $r2, ColorStateList $r3) throws  {
            super($r1, $i0, $i1, $r2, $r3);
        }
    }

    private static class SavedState extends BaseSavedState {
        public static final Creator<SavedState> CREATOR = new C10921();
        boolean allowDuplicates;
        ArrayList<Serializable> baseObjects;
        String prefix;
        TokenClickStyle tokenClickStyle;
        TokenDeleteStyle tokenDeleteStyle;

        static class C10921 implements Creator<SavedState> {
            C10921() throws  {
            }

            public SavedState createFromParcel(Parcel $r1) throws  {
                return new SavedState($r1);
            }

            public SavedState[] newArray(int $i0) throws  {
                return new SavedState[$i0];
            }
        }

        SavedState(Parcel $r1) throws  {
            super($r1);
            this.prefix = $r1.readString();
            this.allowDuplicates = $r1.readInt() != 0;
            this.tokenClickStyle = TokenClickStyle.values()[$r1.readInt()];
            this.tokenDeleteStyle = TokenDeleteStyle.values()[$r1.readInt()];
            this.baseObjects = (ArrayList) $r1.readSerializable();
        }

        SavedState(Parcelable $r1) throws  {
            super($r1);
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            super.writeToParcel($r1, $i0);
            $r1.writeString(this.prefix);
            $r1.writeInt(this.allowDuplicates ? (byte) 1 : (byte) 0);
            $r1.writeInt(this.tokenClickStyle.ordinal());
            $r1.writeInt(this.tokenDeleteStyle.ordinal());
            $r1.writeSerializable(this.baseObjects);
        }

        public String toString() throws  {
            return ("TokenCompleteTextView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " tokens=" + this.baseObjects) + "}";
        }
    }

    public enum TokenClickStyle {
        None(false),
        Delete(false),
        Select(true),
        SelectDeselect(true);
        
        private boolean mIsSelectable;

        private TokenClickStyle(@Signature({"(Z)V"}) boolean $z0) throws  {
            this.mIsSelectable = false;
            this.mIsSelectable = $z0;
        }

        public boolean isSelectable() throws  {
            return this.mIsSelectable;
        }
    }

    public enum TokenDeleteStyle {
        _Parent,
        Clear,
        PartialCompletion,
        ToString
    }

    protected class TokenImageSpan extends ViewSpan {
        private Object token;

        public TokenImageSpan(View $r2, Object $r3) throws  {
            super($r2);
            this.token = $r3;
        }

        public Object getToken() throws  {
            return this.token;
        }

        public void onClick() throws  {
            Editable $r2 = TokenCompleteTextView.this.getText();
            if ($r2 != null) {
                switch (TokenCompleteTextView.this.tokenClickStyle) {
                    case Select:
                    case SelectDeselect:
                        if (!this.view.isSelected()) {
                            TokenCompleteTextView.this.clearSelections();
                            this.view.setSelected(true);
                            if (TokenCompleteTextView.this.mViewListener != null) {
                                TokenCompleteTextView.this.mViewListener.onViewSelected(this.view, this.drawX, this.drawY);
                                return;
                            }
                            return;
                        } else if (TokenCompleteTextView.this.tokenClickStyle == TokenClickStyle.SelectDeselect) {
                            this.view.setSelected(false);
                            if (TokenCompleteTextView.this.mViewListener != null) {
                                TokenCompleteTextView.this.mViewListener.onViewUnselected(this.view);
                            }
                            TokenCompleteTextView.this.invalidate();
                            return;
                        }
                        break;
                    case Delete:
                        break;
                    default:
                        if (TokenCompleteTextView.this.getSelectionStart() != $r2.getSpanEnd(this) + 1) {
                            TokenCompleteTextView.this.setSelection($r2.getSpanEnd(this) + 1);
                            return;
                        }
                        return;
                }
                TokenCompleteTextView.this.removeSpan(this);
            }
        }
    }

    private class TokenInputConnection extends InputConnectionWrapper {
        public TokenInputConnection(InputConnection $r2, boolean $z0) throws  {
            super($r2, $z0);
        }

        public boolean deleteSurroundingText(int $i1, int $i0) throws  {
            System.out.println("before: " + $i1 + " after: " + $i0);
            System.out.println("selection: " + TokenCompleteTextView.this.getSelectionStart() + " end: " + TokenCompleteTextView.this.getSelectionEnd());
            if (TokenCompleteTextView.this.getSelectionStart() <= TokenCompleteTextView.this.prefix.length()) {
                $i1 = 0;
            }
            return TokenCompleteTextView.this.deleteSelectedObject(false) || super.deleteSurroundingText($i1, $i0);
        }
    }

    public interface TokenListener {
        void onTokenAdded(Object obj) throws ;

        void onTokenRemoved(Object obj) throws ;
    }

    private class TokenSpanWatcher implements SpanWatcher {
        private TokenSpanWatcher() throws  {
        }

        private void updateCountSpan(final int $i0) throws  {
            final Editable $r3 = TokenCompleteTextView.this.getText();
            if ($r3 != null && TokenCompleteTextView.this.lastLayout != null) {
                CountSpan[] $r6 = (CountSpan[]) $r3.getSpans(0, $r3.length(), CountSpan.class);
                if ($r6.length == 1) {
                    final CountSpan $r1 = $r6[0];
                    TokenCompleteTextView.this.post(new Runnable() {
                        public void run() throws  {
                            int $i1 = $r3.getSpanStart($r1);
                            int $i2 = $r3.getSpanEnd($r1);
                            $r1.setCount($r1.getCount() + $i0);
                            if ($r1.getCount() > 0) {
                                $r3.replace($i1, $i2, $r1.text);
                                return;
                            }
                            $r3.delete($i1, $i2);
                            $r3.removeSpan($r1);
                        }
                    });
                }
            }
        }

        public void onSpanAdded(Spannable text, Object $r2, int start, int end) throws  {
            if (($r2 instanceof TokenImageSpan) && !TokenCompleteTextView.this.savingState) {
                TokenImageSpan $r4 = (TokenImageSpan) $r2;
                TokenCompleteTextView.this.objects.add($r4.getToken());
                updateCountSpan(1);
                if (TokenCompleteTextView.this.listener != null) {
                    TokenCompleteTextView.this.listener.onTokenAdded($r4.getToken());
                }
            }
        }

        public void onSpanRemoved(Spannable text, Object $r2, int start, int end) throws  {
            if (($r2 instanceof TokenImageSpan) && !TokenCompleteTextView.this.savingState) {
                TokenImageSpan $r4 = (TokenImageSpan) $r2;
                if (TokenCompleteTextView.this.objects.contains($r4.getToken())) {
                    TokenCompleteTextView.this.objects.remove($r4.getToken());
                    updateCountSpan(-1);
                }
                if (TokenCompleteTextView.this.listener != null) {
                    TokenCompleteTextView.this.listener.onTokenRemoved($r4.getToken());
                }
            }
        }

        public void onSpanChanged(Spannable text, Object what, int ostart, int oend, int nstart, int nend) throws  {
        }
    }

    private class TokenTextWatcher implements TextWatcher {
        private TokenTextWatcher() throws  {
        }

        protected void removeToken(TokenImageSpan $r1, Editable $r2) throws  {
            $r2.removeSpan($r1);
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void afterTextChanged(Editable s) throws  {
        }

        public void onTextChanged(CharSequence $r1, int $i0, int $i1, int $i2) throws  {
            System.out.println("changing text: " + $r1);
            Editable $r7 = TokenCompleteTextView.this.getText();
            if ($r7 != null) {
                TokenCompleteTextView.this.clearSelections();
                TokenCompleteTextView.this.updateHint();
                for (TokenImageSpan $r2 : (TokenImageSpan[]) $r7.getSpans($i0 - $i1, ($i0 - $i1) + $i2, TokenImageSpan.class)) {
                    int $i3 = $i0 + $i2;
                    if ($r7.getSpanStart($r2) < $i3 && $i3 <= $r7.getSpanEnd($r2)) {
                        $i3 = $r7.getSpanStart($r2);
                        int $i5 = $r7.getSpanEnd($r2);
                        removeToken($r2, $r7);
                        $i5--;
                        if ($i5 >= 0 && $r7.charAt($i5) == ',') {
                            $r7.delete($i5, $i5 + 1);
                        }
                        if ($i3 > 0 && $r7.charAt($i3) == ',') {
                            $r7.delete($i3, $i3 + 1);
                        }
                    }
                }
            }
        }
    }

    public interface ViewListener {
        void onViewSelected(View view, float f, float f2) throws ;

        void onViewUnselected(View view) throws ;
    }

    protected abstract Object defaultObject(String str) throws ;

    protected abstract View getViewForObject(Object obj) throws ;

    private void resetListeners() throws  {
        Editable $r1 = getText();
        if ($r1 != null) {
            $r1.setSpan(this.spanWatcher, 0, $r1.length(), 18);
            addTextChangedListener(new TokenTextWatcher());
        }
    }

    private void init() throws  {
        setTokenizer(new CommaTokenizer());
        this.objects = new ArrayList();
        Editable $r1 = getText();
        if ($assertionsDisabled || $r1 != null) {
            this.spanWatcher = new TokenSpanWatcher();
            resetListeners();
            setTextIsSelectable(false);
            setLongClickable(false);
            setInputType(589824);
            setOnEditorActionListener(this);
            setFilters(new InputFilter[]{new C10871()});
            setDeletionStyle(TokenDeleteStyle.Clear);
            this.initialized = true;
            return;
        }
        throw new AssertionError();
    }

    public TokenCompleteTextView(Context $r1) throws  {
        super($r1);
        init();
    }

    public TokenCompleteTextView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        init();
    }

    public TokenCompleteTextView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        init();
    }

    protected void performFiltering(CharSequence $r1, int $i2, int $i0, int keyCode) throws  {
        if ($i2 < this.prefix.length()) {
            $i2 = this.prefix.length();
        }
        Filter $r3 = getFilter();
        if ($r3 != null) {
            $r3.filter($r1.subSequence($i2, $i0), this);
        }
    }

    public void setTokenizer(Tokenizer $r1) throws  {
        super.setTokenizer($r1);
        this.tokenizer = $r1;
    }

    public void setDeletionStyle(TokenDeleteStyle $r1) throws  {
        this.deletionStyle = $r1;
    }

    public void setTokenClickStyle(TokenClickStyle $r1) throws  {
        this.tokenClickStyle = $r1;
    }

    public void setTokenListener(TokenListener $r1) throws  {
        this.listener = $r1;
    }

    public void setViewListener(ViewListener $r1) throws  {
        this.mViewListener = $r1;
    }

    public void setPrefix(String $r1) throws  {
        this.prefix = "";
        Editable $r2 = getText();
        if ($r2 != null) {
            $r2.insert(0, $r1);
        }
        this.prefix = $r1;
        updateHint();
    }

    public List<Object> getObjects() throws  {
        return this.objects;
    }

    public void allowDuplicates(boolean $z0) throws  {
        this.allowDuplicates = $z0;
    }

    public void allowCollapse(boolean $z0) throws  {
        this.allowCollapse = $z0;
    }

    protected String currentCompletionText() throws  {
        if (this.hintVisible) {
            return "";
        }
        Editable $r1 = getText();
        int $i0 = getSelectionEnd();
        int $i1 = this.tokenizer.findTokenStart($r1, $i0);
        int $i2 = $i1;
        if ($i1 < this.prefix.length()) {
            $i2 = this.prefix.length();
        }
        return TextUtils.substring($r1, $i2, $i0);
    }

    private float maxTextWidth() throws  {
        return (float) ((getWidth() - getPaddingLeft()) - getPaddingRight());
    }

    @TargetApi(16)
    private void api16Invalidate() throws  {
        if (this.initialized && !this.inInvalidate) {
            this.inInvalidate = true;
            setShadowLayer(getShadowRadius(), getShadowDx(), getShadowDy(), getShadowColor());
            this.inInvalidate = false;
        }
    }

    public void invalidate() throws  {
        if (VERSION.SDK_INT >= 16) {
            api16Invalidate();
        }
        super.invalidate();
    }

    public boolean enoughToFilter() throws  {
        Editable $r1 = getText();
        int $i0 = getSelectionEnd();
        if ($i0 < 0) {
            return false;
        }
        if (this.tokenizer == null) {
            return false;
        }
        int $i1 = this.tokenizer.findTokenStart($r1, $i0);
        int $i2 = $i1;
        if ($i1 < this.prefix.length()) {
            $i2 = this.prefix.length();
        }
        return $i0 - $i2 >= getThreshold();
    }

    public void performCompletion() throws  {
        if (getListSelection() == -1) {
            Object $r2;
            if (getAdapter().getCount() > 0) {
                $r2 = getAdapter().getItem(0);
            } else {
                $r2 = defaultObject(currentCompletionText());
            }
            replaceText(convertSelectionToString($r2));
            return;
        }
        super.performCompletion();
    }

    public InputConnection onCreateInputConnection(EditorInfo $r1) throws  {
        TokenInputConnection $r2 = new TokenInputConnection(super.onCreateInputConnection($r1), true);
        int $i0 = $r1.imeOptions & 255;
        if (($i0 & 6) != 0) {
            $r1.imeOptions ^= $i0;
            $r1.imeOptions |= 6;
        }
        if (($r1.imeOptions & 1073741824) == 0) {
            return $r2;
        }
        $r1.imeOptions &= -1073741825;
        return $r2;
    }

    private void handleDone() throws  {
        if (enoughToFilter()) {
            performCompletion();
            return;
        }
        View $r1 = focusSearch(130);
        if ($r1 != null) {
            $r1.requestFocus();
        }
    }

    public boolean onKeyUp(int $i0, KeyEvent $r1) throws  {
        boolean $z0 = super.onKeyUp($i0, $r1);
        if (!this.shouldFocusNext) {
            return $z0;
        }
        this.shouldFocusNext = false;
        handleDone();
        return $z0;
    }

    public boolean onKeyDown(int $i0, KeyEvent $r1) throws  {
        boolean $z0 = false;
        switch ($i0) {
            case 23:
            case 61:
            case 66:
                if ($r1.hasNoModifiers()) {
                    this.shouldFocusNext = true;
                    $z0 = true;
                    break;
                }
                break;
            case 67:
                $z0 = deleteSelectedObject(false);
                break;
            default:
                break;
        }
        if ($z0 || super.onKeyDown($i0, $r1)) {
            return true;
        }
        return false;
    }

    private boolean deleteSelectedObject(boolean $z0) throws  {
        if (this.tokenClickStyle != null && this.tokenClickStyle.isSelectable()) {
            Editable $r3 = getText();
            if ($r3 == null) {
                return $z0;
            }
            for (TokenImageSpan $r1 : (TokenImageSpan[]) $r3.getSpans(0, $r3.length(), TokenImageSpan.class)) {
                if ($r1.view.isSelected()) {
                    removeSpan($r1);
                    $z0 = true;
                    break;
                }
            }
        }
        return $z0;
    }

    public boolean onEditorAction(TextView view, int $i0, KeyEvent keyEvent) throws  {
        if ($i0 != 6) {
            return false;
        }
        handleDone();
        return true;
    }

    public boolean onTouchEvent(MotionEvent $r1) throws  {
        int $i0 = $r1.getActionMasked();
        Editable $r2 = getText();
        boolean $z0 = false;
        if (this.tokenClickStyle == TokenClickStyle.None) {
            $z0 = super.onTouchEvent($r1);
        }
        if (isFocused() && $r2 != null && this.lastLayout != null && $i0 == 1) {
            $i0 = getOffsetForPosition($r1.getX(), $r1.getY());
            if ($i0 != -1) {
                TokenImageSpan[] $r7 = (TokenImageSpan[]) $r2.getSpans($i0, $i0, TokenImageSpan.class);
                if ($r7.length > 0) {
                    $r7[0].onClick();
                    $z0 = true;
                }
            }
        }
        if ($z0 || this.tokenClickStyle == TokenClickStyle.None) {
            return $z0;
        }
        return super.onTouchEvent($r1);
    }

    protected void onSelectionChanged(int $i1, int selEnd) throws  {
        if (this.hintVisible) {
            $i1 = 0;
        }
        selEnd = $i1;
        if (!(this.tokenClickStyle == null || !this.tokenClickStyle.isSelectable() || getText() == null)) {
            clearSelections();
        }
        if (this.prefix == null || ($i1 >= this.prefix.length() && selEnd >= this.prefix.length())) {
            Editable $r3 = getText();
            if ($r3 != null) {
                TokenImageSpan[] $r6 = (TokenImageSpan[]) $r3.getSpans($i1, selEnd, TokenImageSpan.class);
                int $i0 = $r6.length;
                int $i4 = 0;
                while ($i4 < $i0) {
                    TokenImageSpan $r1 = $r6[$i4];
                    int $i3 = $r3.getSpanEnd($r1);
                    if ($i1 > $i3 || $r3.getSpanStart($r1) >= $i1) {
                        $i4++;
                    } else if ($i3 == $r3.length()) {
                        setSelection($i3);
                        return;
                    } else {
                        setSelection($i3 + 1);
                        return;
                    }
                }
            }
            super.onSelectionChanged($i1, selEnd);
            return;
        }
        setSelection(this.prefix.length());
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        this.lastLayout = getLayout();
    }

    protected void handleFocus(boolean $z0) throws  {
        Editable $r3;
        int $i1;
        int $i2;
        if ($z0 || !this.allowCollapse) {
            setSingleLine(false);
            $r3 = getText();
            if ($r3 != null) {
                for (CountSpan $r1 : (CountSpan[]) $r3.getSpans(0, $r3.length(), CountSpan.class)) {
                    $r3.delete($r3.getSpanStart($r1), $r3.getSpanEnd($r1));
                    $r3.removeSpan($r1);
                }
                if (this.hintVisible) {
                    String $r9 = this.prefix;
                    setSelection($r9.length());
                } else {
                    setSelection($r3.length());
                }
                if (((TokenSpanWatcher[]) getText().getSpans(0, getText().length(), TokenSpanWatcher.class)).length == 0) {
                    $r3.setSpan(this.spanWatcher, 0, $r3.length(), 18);
                    return;
                }
                return;
            }
            return;
        }
        setSingleLine(true);
        $r3 = getText();
        if ($r3 != null && this.lastLayout != null) {
            $i1 = this.lastLayout.getLineVisibleEnd(0);
            TokenImageSpan[] $r6 = (TokenImageSpan[]) $r3.getSpans(0, $i1, TokenImageSpan.class);
            $i2 = this.objects.size() - $r6.length;
            if ($i2 > 0) {
                $i1++;
                CountSpan countSpan = new CountSpan($i2, getContext(), getCurrentTextColor(), (int) getTextSize(), (int) maxTextWidth());
                $r9 = countSpan.text;
                $r3.insert($i1, $r9);
                $r9 = countSpan.text;
                if (Layout.getDesiredWidth($r3, 0, $r9.length() + $i1, this.lastLayout.getPaint()) > maxTextWidth()) {
                    $r9 = countSpan.text;
                    int $i0 = $r9.length() + $i1;
                    int i = $i0;
                    $r3.delete($i1, $i0);
                    if ($r6.length > 0) {
                        $i1 = $r3.getSpanStart($r6[$r6.length - 1]);
                        countSpan.setCount($i2 + 1);
                    } else {
                        $r9 = this.prefix;
                        $i1 = $r9.length();
                    }
                    $r9 = countSpan.text;
                    $r3.insert($i1, $r9);
                }
                $r9 = countSpan.text;
                $r3.setSpan(countSpan, $i1, $r9.length() + $i1, 33);
            }
        }
    }

    public void onFocusChanged(boolean $z0, int $i0, Rect $r1) throws  {
        super.onFocusChanged($z0, $i0, $r1);
        handleFocus($z0);
    }

    protected CharSequence convertSelectionToString(Object $r1) throws  {
        this.selectedObject = $r1;
        switch (this.deletionStyle) {
            case Clear:
                return "";
            case PartialCompletion:
                return currentCompletionText();
            case ToString:
                return $r1.toString();
            default:
                return super.convertSelectionToString($r1);
        }
    }

    private SpannableStringBuilder buildSpannableForText(CharSequence $r1) throws  {
        return new SpannableStringBuilder("," + this.tokenizer.terminateToken($r1));
    }

    protected TokenImageSpan buildSpanForObject(Object $r1) throws  {
        return $r1 == null ? null : new TokenImageSpan(getViewForObject($r1), $r1);
    }

    protected void replaceText(CharSequence $r1) throws  {
        clearComposingText();
        SpannableStringBuilder $r2 = buildSpannableForText($r1);
        TokenImageSpan $r4 = buildSpanForObject(this.selectedObject);
        Editable $r5 = getText();
        int $i0 = getSelectionEnd();
        int $i1 = this.tokenizer.findTokenStart($r5, $i0);
        int $i2 = $i1;
        if ($i1 < this.prefix.length()) {
            $i2 = this.prefix.length();
        }
        String $r7 = TextUtils.substring($r5, $i2, $i0);
        if ($r5 == null) {
            return;
        }
        if ($r4 == null) {
            $r5.replace($i2, $i0, " ");
        } else if (this.allowDuplicates || !this.objects.contains($r4.getToken())) {
            QwertyKeyListener.markAsReplaced($r5, $i2, $i0, $r7);
            $r5.replace($i2, $i0, $r2);
            $r5.setSpan($r4, $i2, ($r2.length() + $i2) - 1, 33);
        } else {
            $r5.replace($i2, $i0, " ");
        }
    }

    public void addObject(final Object $r1, final CharSequence $r2) throws  {
        post(new Runnable() {
            public void run() throws  {
                if ($r1 != null) {
                    if (TokenCompleteTextView.this.allowDuplicates || !TokenCompleteTextView.this.objects.contains($r1)) {
                        SpannableStringBuilder $r5 = TokenCompleteTextView.this.buildSpannableForText($r2);
                        TokenImageSpan $r6 = TokenCompleteTextView.this.buildSpanForObject($r1);
                        Editable $r7 = TokenCompleteTextView.this.getText();
                        if ($r7 != null) {
                            int $i0 = $r7.length();
                            if (TokenCompleteTextView.this.hintVisible) {
                                int $i1 = TokenCompleteTextView.this.prefix.length();
                                $i0 = $i1;
                                $r7.insert($i1, $r5);
                            } else {
                                $r7.append($r5);
                            }
                            $r7.setSpan($r6, $i0, ($r5.length() + $i0) - 1, 33);
                            if (!TokenCompleteTextView.this.objects.contains($r1)) {
                                TokenCompleteTextView.this.spanWatcher.onSpanAdded($r7, $r6, $i0, ($r5.length() + $i0) - 1);
                            }
                            TokenCompleteTextView.this.setSelection($r7.length());
                        }
                    }
                }
            }
        });
    }

    public void addObject(Object $r1) throws  {
        addObject($r1, "");
    }

    public void removeObject(final Object $r1) throws  {
        post(new Runnable() {
            public void run() throws  {
                Editable $r4 = TokenCompleteTextView.this.getText();
                if ($r4 != null) {
                    for (TokenImageSpan $r1 : (TokenImageSpan[]) $r4.getSpans(0, $r4.length(), TokenImageSpan.class)) {
                        if ($r1.getToken().equals($r1)) {
                            TokenCompleteTextView.this.removeSpan($r1);
                        }
                    }
                }
            }
        });
    }

    private void removeSpan(TokenImageSpan $r1) throws  {
        Editable $r2 = getText();
        if ($r2 != null) {
            if (((TokenSpanWatcher[]) $r2.getSpans(0, $r2.length(), TokenSpanWatcher.class)).length == 0) {
                this.spanWatcher.onSpanRemoved($r2, $r1, $r2.getSpanStart($r1), $r2.getSpanEnd($r1));
            }
            $r2.delete($r2.getSpanStart($r1), $r2.getSpanEnd($r1) + 1);
        }
    }

    private void updateHint() throws  {
        Editable $r2 = getText();
        CharSequence $r3 = getHint();
        if ($r2 != null && $r3 != null && this.prefix.length() > 0) {
            HintSpan[] $r6 = (HintSpan[]) $r2.getSpans(0, $r2.length(), HintSpan.class);
            HintSpan $r1 = null;
            int $i0 = this.prefix.length();
            int $i1 = $i0;
            if ($r6.length > 0) {
                $r1 = $r6[0];
                $i1 = $i0 + ($r2.getSpanEnd($r1) - $r2.getSpanStart($r1));
            }
            if ($r2.length() == $i1) {
                this.hintVisible = true;
                if ($r1 == null) {
                    Typeface $r7 = getTypeface();
                    $i0 = 0;
                    if ($r7 != null) {
                        $i0 = $r7.getStyle();
                    }
                    ColorStateList $r8 = getHintTextColors();
                    HintSpan hintSpan = new HintSpan(null, $i0, (int) getTextSize(), $r8, $r8);
                    $r2.insert(this.prefix.length(), $r3);
                    $r2.setSpan(hintSpan, this.prefix.length(), this.prefix.length() + getHint().length(), 33);
                    setSelection(this.prefix.length());
                }
            } else if ($r1 != null) {
                $i0 = $r2.getSpanStart($r1);
                $i1 = $r2.getSpanEnd($r1);
                $r2.removeSpan($r1);
                $r2.replace($i0, $i1, "");
                this.hintVisible = false;
            }
        }
    }

    public void clearSelections() throws  {
        if (this.tokenClickStyle != null && this.tokenClickStyle.isSelectable()) {
            Editable $r4 = getText();
            if ($r4 != null) {
                for (TokenImageSpan $r1 : (TokenImageSpan[]) $r4.getSpans(0, $r4.length(), TokenImageSpan.class)) {
                    $r1.view.setSelected(false);
                    if (this.mViewListener != null) {
                        this.mViewListener.onViewUnselected($r1.view);
                    }
                }
                invalidate();
            }
        }
    }

    protected ArrayList<Serializable> getSerializableObjects() throws  {
        ArrayList $r1 = new ArrayList();
        for (Object $r4 : getObjects()) {
            if ($r4 instanceof Serializable) {
                $r1.add((Serializable) $r4);
            } else {
                System.out.println("Unable to save '" + $r4 + "'");
            }
        }
        if ($r1.size() == this.objects.size()) {
            return $r1;
        }
        System.out.println("You should make your objects Serializable or override");
        System.out.println("getSerializableObjects and convertSerializableArrayToObjectArray");
        return $r1;
    }

    protected ArrayList<Object> convertSerializableArrayToObjectArray(@Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/io/Serializable;", ">;)", "Ljava/util/ArrayList", "<", "Ljava/lang/Object;", ">;"}) ArrayList<Serializable> $r1) throws  {
        return $r1;
    }

    public Parcelable onSaveInstanceState() throws  {
        ArrayList $r2 = getSerializableObjects();
        this.savingState = true;
        Parcelable $r3 = super.onSaveInstanceState();
        this.savingState = false;
        SavedState $r1 = new SavedState($r3);
        $r1.prefix = this.prefix;
        $r1.allowDuplicates = this.allowDuplicates;
        $r1.tokenClickStyle = this.tokenClickStyle;
        $r1.tokenDeleteStyle = this.deletionStyle;
        $r1.baseObjects = $r2;
        return $r1;
    }

    public void onRestoreInstanceState(Parcelable $r1) throws  {
        if ($r1 instanceof SavedState) {
            SavedState $r2 = (SavedState) $r1;
            super.onRestoreInstanceState($r2.getSuperState());
            setText($r2.prefix);
            this.prefix = $r2.prefix;
            updateHint();
            this.allowDuplicates = $r2.allowDuplicates;
            this.tokenClickStyle = $r2.tokenClickStyle;
            this.deletionStyle = $r2.tokenDeleteStyle;
            resetListeners();
            Iterator $r7 = convertSerializableArrayToObjectArray($r2.baseObjects).iterator();
            while ($r7.hasNext()) {
                addObject($r7.next());
            }
            if (!isFocused()) {
                post(new C10904());
                return;
            }
            return;
        }
        super.onRestoreInstanceState($r1);
    }
}
