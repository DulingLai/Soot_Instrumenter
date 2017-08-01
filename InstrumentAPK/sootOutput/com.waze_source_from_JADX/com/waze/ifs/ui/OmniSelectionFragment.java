package com.waze.ifs.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.Filter;
import android.widget.Filter.FilterResults;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import com.waze.settings.SettingsValue;
import dalvik.annotation.Signature;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import junit.framework.Assert;

public class OmniSelectionFragment extends Fragment {
    static final boolean OPTIMIZE = true;
    private static final String TAG = OmniSelectionFragment.class.getName();
    private OmniSelectAdapter mAdapter;
    private SettingsValue mAddUserInput;
    private AutoCompleteOmniSelectAdapter mAutoCompleteAdapter;
    private String mEditBoxHint = null;
    private EditText mEditText;
    private boolean mIsAutoComplete = false;
    private boolean mIsExpandable = false;
    private boolean mIsFilter = false;
    private boolean mIsIndexed = false;
    private boolean mIsMultiSelect = false;
    private boolean mIsPortrait = false;
    private boolean mIsSearch = false;
    private boolean mIsUserInput = false;
    private ListView mListView;
    private int mMaxUserInputLength = 0;
    private int mPrevGroup = -1;
    private String mSubTitle = null;
    private int mTimesEnteredSearch = 0;
    private String mTitleText = null;
    private int mTopOptionIcon = 0;
    private String mTopOptionText = null;
    private String mUserInputFormat = null;
    private SettingsValue[] mValues = null;
    private View f83r;

    public interface IOmniSelect {
        void isSearching(int i) throws ;

        void omniSelected(int i, String str, String str2, boolean z) throws ;
    }

    class C17361 implements OnClickListener {
        C17361() throws  {
        }

        public void onClick(View v) throws  {
            if (OmniSelectionFragment.this.mIsMultiSelect || OmniSelectionFragment.this.mIsExpandable) {
                SettingsValue $r4 = OmniSelectionFragment.this.mAdapter.getSelectedItem();
                if ($r4 != null) {
                    OmniSelectionFragment.this.setResultValueAndContinue(OmniSelectionFragment.this.mAdapter.getSelectedPosition(), $r4, false);
                }
            }
        }
    }

    class C17372 implements OnClickListener {
        C17372() throws  {
        }

        public void onClick(View v) throws  {
            ((IOmniSelect) OmniSelectionFragment.this.getActivity()).omniSelected(-1, null, null, false);
        }
    }

    class C17383 implements TextWatcher {
        C17383() throws  {
        }

        public void onTextChanged(CharSequence $r1, int start, int before, int count) throws  {
            boolean $z0 = true;
            if (OmniSelectionFragment.this.mAdapter instanceof FilteredOmniSelectAdapter) {
                String $r4 = $r1.toString().trim();
                if (OmniSelectionFragment.this.mIsUserInput) {
                    OmniSelectionFragment.this.mAddUserInput.display = String.format(OmniSelectionFragment.this.mUserInputFormat, new Object[]{$r4});
                    OmniSelectionFragment.this.mAddUserInput.value = "" + $r4;
                    if ($r4.length() < 2) {
                        $z0 = false;
                    }
                    OmniSelectionFragment.this.mAdapter.setShowLastItem($z0);
                    OmniSelectionFragment.this.mAdapter.setBoldLastItem($z0);
                } else {
                    OmniSelectionFragment.this.mAdapter.setShowLastItem(true);
                    OmniSelectionFragment.this.mAdapter.setBoldLastItem(false);
                }
                ((FilteredOmniSelectAdapter) OmniSelectionFragment.this.mAdapter).getFilter().filter($r4);
            } else if (OmniSelectionFragment.this.mAdapter instanceof FilteredOmniSelectExpandableAdapter) {
                ((FilteredOmniSelectExpandableAdapter) OmniSelectionFragment.this.mAdapter).getFilter().filter($r1);
            } else if (OmniSelectionFragment.this.mIsAutoComplete) {
                OmniSelectionFragment.this.mAutoCompleteAdapter.getFilter().filter($r1);
            }
        }

        public void beforeTextChanged(CharSequence s, int start, int count, int after) throws  {
        }

        public void afterTextChanged(Editable s) throws  {
        }
    }

    class C17394 implements OnEditorActionListener {
        C17394() throws  {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) throws  {
            if (!OmniSelectionFragment.this.mIsAutoComplete) {
                if (OmniSelectionFragment.this.mIsUserInput && OmniSelectionFragment.this.mAddUserInput.value.length() >= 2) {
                    OmniSelectionFragment.this.setResultValueAndContinue(OmniSelectionFragment.this.mValues.length, OmniSelectionFragment.this.mAddUserInput, true);
                }
                return false;
            } else if (OmniSelectionFragment.this.mAutoCompleteAdapter.getCount() <= 0) {
                return true;
            } else {
                SettingsValue $r6 = (SettingsValue) OmniSelectionFragment.this.mAutoCompleteAdapter.getItem(0);
                if ($r6.rank < LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) {
                    return true;
                }
                OmniSelectionFragment.this.setResultValueAndContinue(OmniSelectionFragment.this.mAutoCompleteAdapter.getOriginalIndex($r6), $r6, false);
                return true;
            }
        }
    }

    class C17405 implements OnItemClickListener {
        C17405() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View view, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
            SettingsValue $r6 = (SettingsValue) OmniSelectionFragment.this.mAutoCompleteAdapter.getItem($i0);
            OmniSelectionFragment.this.setResultValueAndContinue(OmniSelectionFragment.this.mAutoCompleteAdapter.getOriginalIndex($r6), $r6, false);
        }
    }

    class C17416 implements OnFocusChangeListener {
        C17416() throws  {
        }

        public void onFocusChange(View v, boolean $z0) throws  {
            if ($z0) {
                OmniSelectionFragment.this.mTimesEnteredSearch = OmniSelectionFragment.this.mTimesEnteredSearch + 1;
                ((IOmniSelect) OmniSelectionFragment.this.getActivity()).isSearching(OmniSelectionFragment.this.mTimesEnteredSearch);
            }
        }
    }

    class C17427 implements OnGroupClickListener {
        final /* synthetic */ ExpandableListView val$lv;

        C17427(ExpandableListView $r2) throws  {
            this.val$lv = $r2;
        }

        public boolean onGroupClick(ExpandableListView parent, View v, int $i0, long id) throws  {
            int $i2 = ((OmniSelectExpandableAdapter) OmniSelectionFragment.this.mAdapter).getChildIndex($i0, -1);
            SettingsValue $r7 = (SettingsValue) ((OmniSelectExpandableAdapter) OmniSelectionFragment.this.mAdapter).getItem($i2);
            if ($r7.isSelected) {
                OmniSelectionFragment.this.setResultValueAndContinue($i2, $r7, false);
                return true;
            }
            if (OmniSelectionFragment.this.mAdapter.getSelectedItem() != null) {
                OmniSelectionFragment.this.mAdapter.getSelectedItem().isSelected = false;
            }
            $r7.isSelected = true;
            if (this.val$lv.isGroupExpanded($i0)) {
                this.val$lv.collapseGroup($i0);
                OmniSelectionFragment.this.mPrevGroup = -1;
                return true;
            }
            this.val$lv.expandGroup($i0);
            if (OmniSelectionFragment.this.mPrevGroup != -1) {
                this.val$lv.collapseGroup(OmniSelectionFragment.this.mPrevGroup);
            }
            OmniSelectionFragment.this.mPrevGroup = $i0;
            this.val$lv.smoothScrollToPosition($i0);
            return true;
        }
    }

    class C17438 implements OnChildClickListener {
        C17438() throws  {
        }

        public boolean onChildClick(ExpandableListView parent, View v, int $i0, int $i1, long id) throws  {
            $i0 = ((OmniSelectExpandableAdapter) OmniSelectionFragment.this.mAdapter).getChildIndex($i0, $i1);
            SettingsValue $r7 = (SettingsValue) ((OmniSelectExpandableAdapter) OmniSelectionFragment.this.mAdapter).getItem($i0);
            if ($r7.isSelected) {
                OmniSelectionFragment.this.setResultValueAndContinue($i0, $r7, false);
            } else {
                if (OmniSelectionFragment.this.mAdapter.getSelectedItem() != null) {
                    OmniSelectionFragment.this.mAdapter.getSelectedItem().isSelected = false;
                }
                $r7.isSelected = true;
            }
            OmniSelectionFragment.this.mAdapter.notifyDataSetChanged();
            return true;
        }
    }

    class C17449 implements OnItemClickListener {
        C17449() throws  {
        }

        public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View arg1, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int $i0, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long arg3) throws  {
            boolean $z0 = true;
            if (OmniSelectionFragment.this.mValues != null) {
                if (OmniSelectionFragment.this.mIsMultiSelect) {
                    OmniSelectionFragment.this.mValues[$i0].isSelected = true;
                    return;
                }
                SettingsValue $r6;
                if (OmniSelectionFragment.this.mAdapter.getSelectedItem() != null) {
                    OmniSelectionFragment.this.mAdapter.getSelectedItem().isSelected = false;
                }
                if (OmniSelectionFragment.this.mIsFilter) {
                    $r6 = (SettingsValue) ((FilteredOmniSelectAdapter) OmniSelectionFragment.this.mAdapter).getItem($i0);
                } else {
                    $r6 = (SettingsValue) OmniSelectionFragment.this.mAdapter.getItem($i0);
                }
                $r6.isSelected = true;
                if ($r6 != OmniSelectionFragment.this.mAddUserInput) {
                    $z0 = false;
                }
                if (!$z0 || (OmniSelectionFragment.this.mAddUserInput.value != null && OmniSelectionFragment.this.mAddUserInput.value.length() != 0)) {
                    OmniSelectionFragment.this.setResultValueAndContinue($i0, $r6, $z0);
                }
            }
        }
    }

    public static class OmniSelectAdapter extends BaseAdapter {
        private LayoutInflater inflater;
        private boolean mBoldLastItem = false;
        private SettingsValue mSelectedItem = null;
        private int mSelectedPos = -1;
        private boolean mShowLastItem = true;
        protected SettingsValue[] values;

        public android.view.View getView(int r27, android.view.View r28, android.view.ViewGroup r29) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:26:0x00b7 in {1, 7, 12, 17, 20, 23, 25, 27, 29, 31, 35, 38, 39, 41, 45, 54, 57, 58} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r26 = this;
            if (r28 != 0) goto L_0x0010;
        L_0x0002:
            r0 = r26;
            r2 = r0.inflater;
            r3 = 2130903294; // 0x7f0300fe float:1.7413402E38 double:1.052806112E-314;
            r4 = 0;
            r0 = r29;
            r28 = r2.inflate(r3, r0, r4);
        L_0x0010:
            r0 = r26;
            r5 = r0.values;
            r6 = r5[r27];
            r0 = r26;
            r5 = r0.values;
            r7 = r5.length;
            r7 = r7 + -1;
            r3 = 2131691319; // 0x7f0f0737 float:1.9011707E38 double:1.0531954483E-314;
            r0 = r28;
            r8 = r0.findViewById(r3);
            r10 = r8;
            r10 = (android.widget.TextView) r10;
            r9 = r10;
            r3 = 2131691320; // 0x7f0f0738 float:1.9011709E38 double:1.0531954487E-314;
            r0 = r28;
            r8 = r0.findViewById(r3);
            r12 = r8;
            r12 = (android.widget.TextView) r12;
            r11 = r12;
            r3 = 2131691317; // 0x7f0f0735 float:1.9011702E38 double:1.0531954473E-314;
            r0 = r28;
            r8 = r0.findViewById(r3);
            r14 = r8;
            r14 = (android.widget.ImageView) r14;
            r13 = r14;
            if (r13 == 0) goto L_0x0057;
        L_0x0048:
            if (r6 == 0) goto L_0x0057;
        L_0x004a:
            r15 = r6.icon;
            if (r15 == 0) goto L_0x00f4;
        L_0x004e:
            r15 = r6.icon;
            r13.setImageDrawable(r15);
            r3 = 0;
            r13.setVisibility(r3);
        L_0x0057:
            r0 = r6.display;
            r16 = r0;
            r9.setText(r0);
            r0 = r26;
            r0 = r0.mBoldLastItem;
            r17 = r0;
            if (r17 == 0) goto L_0x0139;
        L_0x0066:
            r0 = r27;
            if (r0 != r7) goto L_0x0139;
        L_0x006a:
            r18 = 0;
            r3 = 1;
            r0 = r18;
            r9.setTypeface(r0, r3);
        L_0x0072:
            r0 = r6.display2;
            r16 = r0;
            if (r16 == 0) goto L_0x0082;
        L_0x0078:
            r0 = r6.display2;
            r16 = r0;
            r19 = r0.length();
            if (r19 != 0) goto L_0x0142;
        L_0x0082:
            r3 = 8;
            r11.setVisibility(r3);
        L_0x0087:
            r0 = r6.isSelected;
            r17 = r0;
            if (r17 == 0) goto L_0x0097;
        L_0x008d:
            r0 = r26;
            r0.mSelectedItem = r6;
            r0 = r27;
            r1 = r26;
            r1.mSelectedPos = r0;
        L_0x0097:
            r3 = 2131691321; // 0x7f0f0739 float:1.901171E38 double:1.053195449E-314;
            r0 = r28;
            r8 = r0.findViewById(r3);
            r20 = r8;
            r20 = (android.widget.ImageView) r20;
            r13 = r20;
            r0 = r6.isSelected;
            r17 = r0;
            if (r17 == 0) goto L_0x014a;
        L_0x00ac:
            r21 = 0;
        L_0x00ae:
            r0 = r21;
            r13.setVisibility(r0);
            goto L_0x00bb;
        L_0x00b4:
            goto L_0x0057;
            goto L_0x00bb;
        L_0x00b8:
            goto L_0x0057;
        L_0x00bb:
            r0 = r26;
            r0 = r0.mShowLastItem;
            r17 = r0;
            goto L_0x00c5;
        L_0x00c2:
            goto L_0x0057;
        L_0x00c5:
            if (r17 != 0) goto L_0x00c9;
        L_0x00c7:
            r7 = r7 + -1;
        L_0x00c9:
            r3 = 2131691316; // 0x7f0f0734 float:1.90117E38 double:1.053195447E-314;
            r0 = r28;
            r8 = r0.findViewById(r3);
            if (r27 != 0) goto L_0x0154;
        L_0x00d4:
            goto L_0x00d8;
        L_0x00d5:
            goto L_0x0072;
        L_0x00d8:
            r0 = r27;
            if (r0 != r7) goto L_0x014d;
        L_0x00dc:
            r3 = 2130838381; // 0x7f02036d float:1.7281743E38 double:1.052774041E-314;
            r8.setBackgroundResource(r3);
            goto L_0x00e6;
        L_0x00e3:
            goto L_0x0087;
        L_0x00e6:
            r3 = 0;
            r4 = 0;
            r22 = 0;
            r23 = 0;
            r0 = r22;
            r1 = r23;
            r8.setPadding(r3, r4, r0, r1);
            return r28;
        L_0x00f4:
            r0 = r6.iconName;
            r16 = r0;
            if (r16 == 0) goto L_0x0133;
        L_0x00fa:
            r24 = new java.lang.StringBuilder;
            goto L_0x0100;
        L_0x00fd:
            goto L_0x00ae;
        L_0x0100:
            r0 = r24;
            r0.<init>();
            r0 = r6.iconName;
            r16 = r0;
            r0 = r24;
            r1 = r16;
            r24 = r0.append(r1);
            r25 = ".png";
            r0 = r24;
            r1 = r25;
            r24 = r0.append(r1);
            r0 = r24;
            r16 = r0.toString();
            r0 = r16;
            r15 = com.waze.ResManager.GetSkinDrawable(r0);
            if (r15 == 0) goto L_0x012d;
        L_0x0129:
            r13.setImageDrawable(r15);
            goto L_0x00b4;
        L_0x012d:
            r3 = 8;
            r13.setVisibility(r3);
            goto L_0x00b8;
        L_0x0133:
            r3 = 8;
            r13.setVisibility(r3);
            goto L_0x00c2;
        L_0x0139:
            r18 = 0;
            r3 = 0;
            r0 = r18;
            r9.setTypeface(r0, r3);
            goto L_0x00d5;
        L_0x0142:
            r0 = r6.display2;
            r16 = r0;
            r11.setText(r0);
            goto L_0x00e3;
        L_0x014a:
            r21 = 4;
            goto L_0x00fd;
        L_0x014d:
            r3 = 2130838382; // 0x7f02036e float:1.7281745E38 double:1.0527740414E-314;
            r8.setBackgroundResource(r3);
            goto L_0x00e6;
        L_0x0154:
            r0 = r27;
            if (r0 != r7) goto L_0x015f;
        L_0x0158:
            r3 = 2130838379; // 0x7f02036b float:1.7281739E38 double:1.05277404E-314;
            r8.setBackgroundResource(r3);
            goto L_0x00e6;
        L_0x015f:
            r3 = 2130838380; // 0x7f02036c float:1.728174E38 double:1.0527740404E-314;
            r8.setBackgroundResource(r3);
            goto L_0x00e6;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.OmniSelectionFragment.OmniSelectAdapter.getView(int, android.view.View, android.view.ViewGroup):android.view.View");
        }

        public OmniSelectAdapter(Context $r1) throws  {
            this.inflater = LayoutInflater.from($r1);
        }

        public SettingsValue[] getValues() throws  {
            return this.values;
        }

        public int getCount() throws  {
            if (this.values == null) {
                return 0;
            }
            if (this.mShowLastItem) {
                return this.values.length;
            }
            return this.values.length - 1;
        }

        public SettingsValue getSelectedItem() throws  {
            return this.mSelectedItem;
        }

        public int getSelectedPosition() throws  {
            return this.mSelectedPos;
        }

        public Object getItem(int $i0) throws  {
            return this.values == null ? null : this.values[$i0];
        }

        public long getItemId(int $i0) throws  {
            return (long) $i0;
        }

        public void setValues(SettingsValue[] $r1) throws  {
            this.values = $r1;
            notifyDataSetChanged();
        }

        public void setShowLastItem(boolean $z0) throws  {
            this.mShowLastItem = $z0;
        }

        public void setBoldLastItem(boolean $z0) throws  {
            this.mBoldLastItem = $z0;
        }
    }

    public static class FilteredOmniSelectAdapter extends OmniSelectAdapter implements Filterable {
        SettingsValue _last;
        SettingsValue[] originalValues;

        class C17461 extends Filter {

            class C17451 implements Comparator<SettingsValue> {
                C17451() throws  {
                }

                public int compare(SettingsValue $r1, SettingsValue $r2) throws  {
                    if ($r1.rank > $r2.rank) {
                        return -1;
                    }
                    return $r1.rank < $r2.rank ? 1 : 0;
                }
            }

            C17461() throws  {
            }

            protected void publishResults(CharSequence constraint, FilterResults $r2) throws  {
                FilteredOmniSelectAdapter.this.values = (SettingsValue[]) $r2.values;
                FilteredOmniSelectAdapter.this.notifyDataSetChanged();
            }

            protected FilterResults performFiltering(CharSequence $r5) throws  {
                FilterResults $r2 = new FilterResults();
                SettingsValue[] $r7 = new SettingsValue[FilteredOmniSelectAdapter.this.originalValues.length];
                String $r8 = $r5.toString().toLowerCase();
                int $i2 = 0;
                for (SettingsValue $r3 : FilteredOmniSelectAdapter.this.originalValues) {
                    if ($r3 == FilteredOmniSelectAdapter.this._last) {
                        $r3.rank = 0.0f;
                        $r7[$i2] = $r3;
                        $i2++;
                    } else {
                        $r3.rank = OmniSelectionFragment.calcRank($r3.display.toLowerCase(), $r8.toString());
                        if ($r3.aliases != null) {
                            int $i1 = $r3.aliases;
                            String[] $r11 = $i1;
                            int $i12 = $i1.length;
                            for (int $i4 = 0; $i4 < $i12; $i4++) {
                                String $r1 = $r11[$i4];
                                if ($r3.rank >= LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) {
                                    break;
                                }
                                if ($r1 != null) {
                                    $r3.rank = Math.max($r3.rank, OmniSelectionFragment.calcRank($r1.toLowerCase(), $r8.toString()));
                                }
                            }
                        }
                        if ($r3.rank >= 1.0f) {
                            $r7[$i2] = $r3;
                            $i2++;
                        }
                    }
                }
                $r2.count = $i2;
                SettingsValue[] $r4 = (SettingsValue[]) Arrays.copyOf($r7, $i2);
                Arrays.sort($r4, new C17451());
                $r2.values = $r4;
                return $r2;
            }
        }

        public FilteredOmniSelectAdapter(Context $r1) throws  {
            super($r1);
        }

        public void setValues(SettingsValue[] $r1) throws  {
            this.originalValues = $r1;
            super.setValues($r1);
        }

        public void setLast(SettingsValue $r1) throws  {
            this._last = $r1;
        }

        public int getOriginalIndex(SettingsValue $r1) throws  {
            for (int $i0 = 0; $i0 < this.originalValues.length; $i0++) {
                if (this.originalValues[$i0] == $r1) {
                    return $i0;
                }
            }
            return -1;
        }

        public Filter getFilter() throws  {
            return new C17461();
        }
    }

    public static class AutoCompleteOmniSelectAdapter extends FilteredOmniSelectAdapter implements Filterable {
        public AutoCompleteOmniSelectAdapter(Context $r1) throws  {
            super($r1);
        }

        public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
            $r1 = super.getView($i0, $r1, $r2);
            $r1.findViewById(C1283R.id.omniSelectItemCheck).setVisibility(8);
            return $r1;
        }
    }

    public static class OmniSelectExpandableAdapter extends OmniSelectAdapter implements ExpandableListAdapter {
        private static int MAX_GROUPS = 64;
        int[] _groups = new int[MAX_GROUPS];
        int _numGroups = 0;
        int prevGroup = -1;

        public long getCombinedChildId(long groupId, long childId) throws  {
            return 0;
        }

        public long getCombinedGroupId(long groupId) throws  {
            return 0;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) throws  {
            return true;
        }

        public OmniSelectExpandableAdapter(Context $r1) throws  {
            super($r1);
        }

        public void setValues(SettingsValue[] $r1) throws  {
            super.setValues($r1);
            calcGroups($r1);
        }

        protected void calcGroups(SettingsValue[] $r1) throws  {
            Arrays.fill(this._groups, 0);
            this._numGroups = 0;
            int $i0 = 0;
            while ($i0 < $r1.length) {
                if ($r1[$i0].isHeader) {
                    this._groups[this._numGroups] = $i0;
                    this._numGroups++;
                }
                if (this._numGroups != MAX_GROUPS) {
                    $i0++;
                } else {
                    return;
                }
            }
        }

        public int getGroupCount() throws  {
            return this._numGroups;
        }

        public int getChildrenCount(int $i0) throws  {
            int $i1;
            if ($i0 < this._numGroups - 1) {
                $i1 = this._groups[$i0 + 1];
            } else {
                $i1 = this.values.length + 1;
            }
            return ($i1 - this._groups[$i0]) - 1;
        }

        public Object getGroup(int $i0) throws  {
            return this.values[this._groups[$i0]];
        }

        public Object getChild(int $i0, int $i1) throws  {
            return this.values[getChildIndex($i0, $i1)];
        }

        public int getChildIndex(int $i0, int $i1) throws  {
            return (this._groups[$i0] + $i1) + 1;
        }

        public long getGroupId(int $i0) throws  {
            return (long) getChildIndex($i0, -1);
        }

        public long getChildId(int $i0, int $i1) throws  {
            return (long) getChildIndex($i0, $i1);
        }

        public int getItemViewType(int $i0) throws  {
            return $i0 == this.values.length ? -1 : super.getItemViewType($i0);
        }

        public View getGroupView(int $i0, boolean $z0, View $r1, ViewGroup $r2) throws  {
            $r1 = getView(this._groups[$i0], $r1, $r2);
            if (!$z0) {
                return $r1;
            }
            View $r4 = $r1.findViewById(C1283R.id.omniSelectItemContainer);
            $r4.setBackgroundResource(C1283R.drawable.item_selector_single);
            $r4.setPadding(0, 0, 0, 0);
            return $r1;
        }

        public View getChildView(int $i0, int $i1, boolean $z0, View $r2, ViewGroup $r1) throws  {
            $i0 = getChildIndex($i0, $i1);
            if ($i0 == this.values.length) {
                $r2 = new View($r1.getContext());
                $r2.setBackgroundResource(C1283R.drawable.bottom_last_innerlist);
                return $r2;
            }
            if (!($r2 instanceof ViewGroup)) {
                $r2 = null;
            }
            $r2 = getView($i0, $r2, $r1);
            View $r5 = $r2.findViewById(C1283R.id.omniSelectItemContainer);
            if ($i1 == 0) {
                if ($z0) {
                    $r5.setBackgroundResource(C1283R.drawable.inner_item_selector_single);
                } else {
                    $r5.setBackgroundResource(C1283R.drawable.inner_item_selector_top);
                }
            } else if ($z0 || $i0 == this.values.length - 1) {
                $r5.setBackgroundResource(C1283R.drawable.inner_item_selector_bottom);
            } else {
                $r5.setBackgroundResource(C1283R.drawable.inner_item_selector_middle);
            }
            $r5.setPadding(0, 0, 0, 0);
            return $r2;
        }

        public void onGroupExpanded(int groupPosition) throws  {
        }

        public void onGroupCollapsed(int groupPosition) throws  {
        }
    }

    public static class FilteredOmniSelectExpandableAdapter extends OmniSelectExpandableAdapter implements Filterable {
        SettingsValue[] originalValues;

        class C17471 extends Filter {
            C17471() throws  {
            }

            protected void publishResults(CharSequence constraint, FilterResults $r2) throws  {
                FilteredOmniSelectExpandableAdapter.this.values = (SettingsValue[]) $r2.values;
                FilteredOmniSelectExpandableAdapter.this.notifyDataSetChanged();
            }

            protected FilterResults performFiltering(CharSequence $r3) throws  {
                SettingsValue $r7;
                FilterResults $r2 = new FilterResults();
                SettingsValue[] $r5 = new SettingsValue[FilteredOmniSelectExpandableAdapter.this.originalValues.length];
                String $r6 = $r3.toString().toLowerCase();
                int $i1 = 0;
                for (SettingsValue $r72 : FilteredOmniSelectExpandableAdapter.this.originalValues) {
                    $r72.rank = OmniSelectionFragment.calcRank($r72.display.toLowerCase(), $r6.toString());
                    if ($r72.rank >= 1.0f || $r72.isHeader) {
                        $r5[$i1] = $r72;
                        $i1++;
                    }
                }
                $r5 = (SettingsValue[]) Arrays.copyOf($r5, $i1);
                FilteredOmniSelectExpandableAdapter.this.calcGroups($r5);
                int $i0 = 0;
                SettingsValue[] $r1 = new SettingsValue[$r5.length];
                $i1 = 0;
                while ($i1 < $r5.length) {
                    $r72 = $r5[$i1];
                    if ($r72.rank >= 1.0f) {
                        $r1[$i0] = $r72;
                        $i0++;
                    } else if ($r72.isHeader && $i1 < $r5.length - 1 && !$r5[$i1 + 1].isHeader) {
                        $r1[$i0] = $r72;
                        $i0++;
                    }
                    $i1++;
                }
                $r1 = (SettingsValue[]) Arrays.copyOf($r1, $i0);
                FilteredOmniSelectExpandableAdapter.this.calcGroups($r1);
                $r2.count = $r1.length;
                $r2.values = $r1;
                return $r2;
            }
        }

        public FilteredOmniSelectExpandableAdapter(Context $r1) throws  {
            super($r1);
        }

        public void setValues(SettingsValue[] $r1) throws  {
            this.originalValues = $r1;
            super.setValues($r1);
        }

        public int getOriginalIndex(SettingsValue $r1) throws  {
            for (int $i0 = 0; $i0 < this.originalValues.length; $i0++) {
                if (this.originalValues[$i0] == $r1) {
                    return $i0;
                }
            }
            return -1;
        }

        public Filter getFilter() throws  {
            return new C17471();
        }
    }

    public static class IndexedOmniSelectAdapter extends OmniSelectAdapter implements SectionIndexer {
        private static int MAX_SECTIONS = 64;
        int _numSections = 0;
        int[] _positionForSection = new int[MAX_SECTIONS];
        int[] _sectionForPosition;
        String[] _sections = new String[MAX_SECTIONS];

        public IndexedOmniSelectAdapter(Context $r1) throws  {
            super($r1);
        }

        public void setValues(SettingsValue[] $r1) throws  {
            this._sectionForPosition = new int[$r1.length];
            int $i1 = -1;
            for (int $i0 = 0; $i0 < $r1.length; $i0++) {
                char $c3 = $r1[$i0].display.charAt(0);
                if ($c3 != $i1) {
                    this._sections[this._numSections] = "" + $c3;
                    this._positionForSection[this._numSections] = $i0;
                    $i1 = $c3;
                    this._numSections++;
                }
                this._sectionForPosition[$i0] = this._numSections - 1;
                if (this._numSections == MAX_SECTIONS) {
                    break;
                }
            }
            this._sections = (String[]) Arrays.copyOf(this._sections, this._numSections);
            this._positionForSection = Arrays.copyOf(this._positionForSection, this._numSections);
            super.setValues($r1);
        }

        public int getPositionForSection(int $i0) throws  {
            if ($i0 < this._positionForSection.length) {
                return this._positionForSection[$i0];
            }
            return -1;
        }

        public int getSectionForPosition(int $i0) throws  {
            if ($i0 < this._sectionForPosition.length) {
                return this._sectionForPosition[$i0];
            }
            return -1;
        }

        public Object[] getSections() throws  {
            return this._sections;
        }
    }

    public android.view.View onCreateView(android.view.LayoutInflater r63, android.view.ViewGroup r64, android.os.Bundle r65) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:62:0x05dd in {4, 7, 12, 15, 18, 27, 32, 36, 39, 42, 43, 48, 51, 54, 56, 60, 63, 66, 69, 74, 79, 84, 87, 90} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r62 = this;
        r0 = r62;
        r1 = r63;
        r2 = r64;
        r3 = r65;
        super.onCreateView(r1, r2, r3);
        if (r65 == 0) goto L_0x058a;
    L_0x000d:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mIsIndexed";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r7 = r0.mIsIndexed;
        r0 = r65;
        r7 = r0.getBoolean(r5, r7);
        r0 = r62;
        r0.mIsIndexed = r7;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mIsSearch";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r7 = r0.mIsSearch;
        r0 = r65;
        r7 = r0.getBoolean(r5, r7);
        r0 = r62;
        r0.mIsSearch = r7;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mIsFilter";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r7 = r0.mIsFilter;
        r0 = r65;
        r7 = r0.getBoolean(r5, r7);
        r0 = r62;
        r0.mIsFilter = r7;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mIsMultiSelect";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r7 = r0.mIsMultiSelect;
        r0 = r65;
        r7 = r0.getBoolean(r5, r7);
        r0 = r62;
        r0.mIsMultiSelect = r7;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mIsUserInput";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r7 = r0.mIsUserInput;
        r0 = r65;
        r7 = r0.getBoolean(r5, r7);
        r0 = r62;
        r0.mIsUserInput = r7;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mUserInputFormat";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r8 = r0.mUserInputFormat;
        r0 = r65;
        r5 = r0.getString(r5, r8);
        r0 = r62;
        r0.mUserInputFormat = r5;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mIsExpandable";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r7 = r0.mIsExpandable;
        r0 = r65;
        r7 = r0.getBoolean(r5, r7);
        r0 = r62;
        r0.mIsExpandable = r7;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mIsAutoComplete";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r7 = r0.mIsAutoComplete;
        r0 = r65;
        r7 = r0.getBoolean(r5, r7);
        r0 = r62;
        r0.mIsAutoComplete = r7;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mTitleText";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r8 = r0.mTitleText;
        r0 = r65;
        r5 = r0.getString(r5, r8);
        r0 = r62;
        r0.mTitleText = r5;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mEditBoxHint";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r8 = r0.mEditBoxHint;
        r0 = r65;
        r5 = r0.getString(r5, r8);
        r0 = r62;
        r0.mEditBoxHint = r5;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mSubTitle";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r8 = r0.mSubTitle;
        r0 = r65;
        r5 = r0.getString(r5, r8);
        r0 = r62;
        r0.mSubTitle = r5;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mValues";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r65;
        r9 = r0.getParcelableArray(r5);
        r11 = r9;
        r11 = (com.waze.settings.SettingsValue[]) r11;
        r10 = r11;
        r10 = (com.waze.settings.SettingsValue[]) r10;
        r0 = r62;
        r0.mValues = r10;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mTimesEnteredSearch";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r12 = r0.mTimesEnteredSearch;
        r0 = r65;
        r12 = r0.getInt(r5, r12);
        r0 = r62;
        r0.mTimesEnteredSearch = r12;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mTopOptionText";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r8 = r0.mTopOptionText;
        r0 = r65;
        r5 = r0.getString(r5, r8);
        r0 = r62;
        r0.mTopOptionText = r5;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = TAG;
        r4 = r4.append(r5);
        r6 = ".mTopOptionIcon";
        r4 = r4.append(r6);
        r5 = r4.toString();
        r0 = r62;
        r12 = r0.mTopOptionIcon;
        r0 = r65;
        r12 = r0.getInt(r5, r12);
        r0 = r62;
        r0.mTopOptionIcon = r12;
        r0 = r62;
        r7 = r0.mIsUserInput;
        if (r7 == 0) goto L_0x0233;
    L_0x0222:
        r0 = r62;
        r10 = r0.mValues;
        r0 = r62;
        r13 = r0.mValues;
        r12 = r13.length;
        r12 = r12 + -1;
        r14 = r10[r12];
        r0 = r62;
        r0.mAddUserInput = r14;
    L_0x0233:
        r0 = r62;
        r15 = r0.getResources();
        r16 = r15.getConfiguration();
        r0 = r16;
        r12 = r0.orientation;
        r17 = 1;
        r0 = r17;
        if (r12 != r0) goto L_0x05e1;
    L_0x0247:
        r7 = 1;
    L_0x0248:
        r0 = r62;
        r0.mIsPortrait = r7;
        r17 = 2130903293; // 0x7f0300fd float:1.74134E38 double:1.0528061117E-314;
        r19 = 0;
        r0 = r63;
        r1 = r17;
        r2 = r64;
        r3 = r19;
        r18 = r0.inflate(r1, r2, r3);
        r0 = r18;
        r1 = r62;
        r1.f83r = r0;
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131689674; // 0x7f0f00ca float:1.900837E38 double:1.0531946355E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        r21 = r18;
        r21 = (com.waze.view.title.TitleBar) r21;
        r20 = r21;
        r0 = r62;
        r7 = r0.mIsMultiSelect;
        if (r7 != 0) goto L_0x0286;
    L_0x0280:
        r0 = r62;
        r7 = r0.mIsExpandable;
        if (r7 == 0) goto L_0x05e3;
    L_0x0286:
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r62;
        r5 = r0.mTitleText;
        r23 = com.waze.NativeManager.getInstance();
        r17 = 375; // 0x177 float:5.25E-43 double:1.853E-321;
        r0 = r23;
        r1 = r17;
        r8 = r0.getLanguageString(r1);
        r0 = r20;
        r1 = r22;
        r0.init(r1, r5, r8);
    L_0x02a5:
        r24 = new com.waze.ifs.ui.OmniSelectionFragment$1;
        r0 = r24;
        r1 = r62;
        r0.<init>();
        r0 = r20;
        r1 = r24;
        r0.setOnClickCloseListener(r1);
        r0 = r62;
        r5 = r0.mTopOptionText;
        if (r5 == 0) goto L_0x0325;
    L_0x02bb:
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131691309; // 0x7f0f072d float:1.9011686E38 double:1.0531954433E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        r17 = 0;
        r0 = r18;
        r1 = r17;
        r0.setVisibility(r1);
        r0 = r62;
        r0 = r0.f83r;
        r25 = r0;
        r17 = 2131691311; // 0x7f0f072f float:1.901169E38 double:1.0531954443E-314;
        r0 = r25;
        r1 = r17;
        r25 = r0.findViewById(r1);
        r27 = r25;
        r27 = (android.widget.TextView) r27;
        r26 = r27;
        r0 = r62;
        r5 = r0.mTopOptionText;
        r0 = r26;
        r0.setText(r5);
        r0 = r62;
        r0 = r0.f83r;
        r25 = r0;
        r17 = 2131691310; // 0x7f0f072e float:1.9011688E38 double:1.053195444E-314;
        r0 = r25;
        r1 = r17;
        r25 = r0.findViewById(r1);
        r29 = r25;
        r29 = (android.widget.ImageView) r29;
        r28 = r29;
        r0 = r62;
        r12 = r0.mTopOptionIcon;
        r0 = r28;
        r0.setImageResource(r12);
        r30 = new com.waze.ifs.ui.OmniSelectionFragment$2;
        r0 = r30;
        r1 = r62;
        r0.<init>();
        r0 = r18;
        r1 = r30;
        r0.setOnClickListener(r1);
    L_0x0325:
        r0 = r62;
        r5 = r0.mSubTitle;
        if (r5 == 0) goto L_0x0354;
    L_0x032b:
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131690485; // 0x7f0f03f5 float:1.9010015E38 double:1.053195036E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        r32 = r18;
        r32 = (com.waze.settings.SettingsTitleText) r32;
        r31 = r32;
        r0 = r62;
        r5 = r0.mSubTitle;
        r0 = r31;
        r0.setText(r5);
        r17 = 0;
        r0 = r31;
        r1 = r17;
        r0.setVisibility(r1);
    L_0x0354:
        r0 = r62;
        r7 = r0.mIsSearch;
        if (r7 != 0) goto L_0x0360;
    L_0x035a:
        r0 = r62;
        r7 = r0.mIsAutoComplete;
        if (r7 == 0) goto L_0x05f9;
    L_0x0360:
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131691312; // 0x7f0f0730 float:1.9011692E38 double:1.053195445E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        r17 = 0;
        r0 = r18;
        r1 = r17;
        r0.setVisibility(r1);
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131691313; // 0x7f0f0731 float:1.9011694E38 double:1.0531954453E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        r34 = r18;
        r34 = (android.widget.EditText) r34;
        r33 = r34;
        r0 = r33;
        r1 = r62;
        r1.mEditText = r0;
        r0 = r62;
        r7 = r0.mIsUserInput;
        if (r7 == 0) goto L_0x03ac;
    L_0x039d:
        r0 = r62;
        r7 = r0.mIsPortrait;
        if (r7 == 0) goto L_0x03ac;
    L_0x03a3:
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        r0.requestFocus();
    L_0x03ac:
        r0 = r62;
        r7 = r0.mIsUserInput;
        if (r7 == 0) goto L_0x03da;
    L_0x03b2:
        r0 = r62;
        r12 = r0.mMaxUserInputLength;
        if (r12 <= 0) goto L_0x03da;
    L_0x03b8:
        r17 = 1;
        r0 = r17;
        r0 = new android.text.InputFilter[r0];
        r35 = r0;
        r36 = new android.text.InputFilter$LengthFilter;
        r0 = r62;
        r12 = r0.mMaxUserInputLength;
        r0 = r36;
        r0.<init>(r12);
        r17 = 0;
        r35[r17] = r36;
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        r1 = r35;
        r0.setFilters(r1);
    L_0x03da:
        r0 = r62;
        r7 = r0.mIsFilter;
        if (r7 != 0) goto L_0x03e6;
    L_0x03e0:
        r0 = r62;
        r7 = r0.mIsAutoComplete;
        if (r7 == 0) goto L_0x047c;
    L_0x03e6:
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        r37 = new com.waze.ifs.ui.OmniSelectionFragment$3;
        r0 = r37;
        r1 = r62;
        r0.<init>();
        r0 = r33;
        r1 = r37;
        r0.addTextChangedListener(r1);
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        r38 = new com.waze.ifs.ui.OmniSelectionFragment$4;
        r0 = r38;
        r1 = r62;
        r0.<init>();
        r0 = r33;
        r1 = r38;
        r0.setOnEditorActionListener(r1);
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        if (r33 == 0) goto L_0x0429;
    L_0x041a:
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        r0 = r62;
        r5 = r0.mEditBoxHint;
        r0 = r33;
        r0.setHint(r5);
    L_0x0429:
        r0 = r62;
        r7 = r0.mIsAutoComplete;
        if (r7 == 0) goto L_0x047c;
    L_0x042f:
        r39 = new com.waze.ifs.ui.OmniSelectionFragment$AutoCompleteOmniSelectAdapter;
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r39;
        r1 = r22;
        r0.<init>(r1);
        r0 = r39;
        r1 = r62;
        r1.mAutoCompleteAdapter = r0;
        r0 = r62;
        r0 = r0.mAutoCompleteAdapter;
        r39 = r0;
        r0 = r62;
        r10 = r0.mValues;
        r0 = r39;
        r0.setValues(r10);
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        r41 = r33;
        r41 = (android.widget.AutoCompleteTextView) r41;
        r40 = r41;
        r0 = r62;
        r0 = r0.mAutoCompleteAdapter;
        r39 = r0;
        r0 = r40;
        r1 = r39;
        r0.setAdapter(r1);
        r42 = new com.waze.ifs.ui.OmniSelectionFragment$5;
        r0 = r42;
        r1 = r62;
        r0.<init>();
        r0 = r40;
        r1 = r42;
        r0.setOnItemClickListener(r1);
    L_0x047c:
        r0 = r62;
        r0 = r0.mEditText;
        r33 = r0;
        r43 = new com.waze.ifs.ui.OmniSelectionFragment$6;
        r0 = r43;
        r1 = r62;
        r0.<init>();
        r0 = r33;
        r1 = r43;
        r0.setOnFocusChangeListener(r1);
    L_0x0492:
        r0 = r62;
        r7 = r0.mIsFilter;
        if (r7 == 0) goto L_0x0618;
    L_0x0498:
        r0 = r62;
        r7 = r0.mIsExpandable;
        if (r7 == 0) goto L_0x0618;
    L_0x049e:
        r44 = new com.waze.ifs.ui.OmniSelectionFragment$FilteredOmniSelectExpandableAdapter;
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r44;
        r1 = r22;
        r0.<init>(r1);
        r0 = r44;
        r1 = r62;
        r1.mAdapter = r0;
    L_0x04b3:
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131691314; // 0x7f0f0732 float:1.9011696E38 double:1.053195446E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        r46 = r18;
        r46 = (android.widget.ListView) r46;
        r45 = r46;
        r0 = r45;
        r1 = r62;
        r1.mListView = r0;
        r0 = r62;
        r7 = r0.mIsIndexed;
        if (r7 == 0) goto L_0x04e5;
    L_0x04d6:
        r0 = r62;
        r0 = r0.mListView;
        r45 = r0;
        r17 = 1;
        r0 = r45;
        r1 = r17;
        r0.setFastScrollEnabled(r1);
    L_0x04e5:
        r0 = r62;
        r7 = r0.mIsExpandable;
        if (r7 == 0) goto L_0x06b6;
    L_0x04eb:
        r0 = r62;
        r0 = r0.mListView;
        r45 = r0;
        r17 = 8;
        r0 = r45;
        r1 = r17;
        r0.setVisibility(r1);
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131691315; // 0x7f0f0733 float:1.9011698E38 double:1.0531954463E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        r48 = r18;
        r48 = (android.widget.ExpandableListView) r48;
        r47 = r48;
        r17 = 0;
        r0 = r47;
        r1 = r17;
        r0.setVisibility(r1);
        r0 = r62;
        r0 = r0.mAdapter;
        r49 = r0;
        r51 = r49;
        r51 = (android.widget.ExpandableListAdapter) r51;
        r50 = r51;
        r0 = r47;
        r1 = r50;
        r0.setAdapter(r1);
        r52 = new com.waze.ifs.ui.OmniSelectionFragment$7;
        r0 = r52;
        r1 = r62;
        r2 = r47;
        r0.<init>(r2);
        r0 = r47;
        r1 = r52;
        r0.setOnGroupClickListener(r1);
        r53 = new com.waze.ifs.ui.OmniSelectionFragment$8;
        r0 = r53;
        r1 = r62;
        r0.<init>();
        r0 = r47;
        r1 = r53;
        r0.setOnChildClickListener(r1);
        r0 = r47;
        r1 = r62;
        r1.mListView = r0;
    L_0x0555:
        r0 = r62;
        r0 = r0.mAdapter;
        r49 = r0;
        r0 = r62;
        r10 = r0.mValues;
        r0 = r49;
        r0.setValues(r10);
        r0 = r62;
        r0 = r0.mListView;
        r45 = r0;
        r0.invalidateViews();
        r0 = r62;
        r0 = r0.mListView;
        r45 = r0;
        r54 = new com.waze.ifs.ui.OmniSelectionFragment$9;
        r0 = r54;
        r1 = r62;
        r0.<init>();
        r0 = r45;
        r1 = r54;
        r0.setOnItemClickListener(r1);
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        return r18;
    L_0x058a:
        r0 = r62;
        r7 = r0.mIsUserInput;
        if (r7 == 0) goto L_0x0233;
    L_0x0590:
        r0 = r62;
        r10 = r0.mValues;
        r12 = r10.length;
        r0 = r62;
        r10 = r0.mValues;
        r55 = r12 + 1;
        r0 = r55;
        r56 = java.util.Arrays.copyOf(r10, r0);
        r57 = r56;
        r57 = (com.waze.settings.SettingsValue[]) r57;
        r10 = r57;
        r0 = r62;
        r0.mValues = r10;
        r0 = r62;
        r10 = r0.mValues;
        r14 = new com.waze.settings.SettingsValue;
        r0 = r62;
        r5 = r0.mUserInputFormat;
        r17 = 1;
        r0 = r17;
        r0 = new java.lang.Object[r0];
        r56 = r0;
        r17 = 0;
        r6 = "";
        r56[r17] = r6;
        r0 = r56;
        r5 = java.lang.String.format(r5, r0);
        r6 = "";
        r17 = 0;
        r0 = r17;
        r14.<init>(r6, r5, r0);
        r0 = r62;
        r0.mAddUserInput = r14;
        goto L_0x05da;
    L_0x05d7:
        goto L_0x0233;
    L_0x05da:
        r10[r12] = r14;
        goto L_0x05d7;
        goto L_0x05e1;
    L_0x05de:
        goto L_0x0248;
    L_0x05e1:
        r7 = 0;
        goto L_0x05de;
    L_0x05e3:
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r62;
        r5 = r0.mTitleText;
        goto L_0x05f1;
    L_0x05ee:
        goto L_0x02a5;
    L_0x05f1:
        r0 = r20;
        r1 = r22;
        r0.init(r1, r5);
        goto L_0x05ee;
    L_0x05f9:
        r0 = r62;
        r0 = r0.f83r;
        r18 = r0;
        r17 = 2131691312; // 0x7f0f0730 float:1.9011692E38 double:1.053195445E-314;
        r0 = r18;
        r1 = r17;
        r18 = r0.findViewById(r1);
        goto L_0x060e;
    L_0x060b:
        goto L_0x0492;
    L_0x060e:
        r17 = 8;
        r0 = r18;
        r1 = r17;
        r0.setVisibility(r1);
        goto L_0x060b;
    L_0x0618:
        r0 = r62;
        r7 = r0.mIsFilter;
        if (r7 == 0) goto L_0x065c;
    L_0x061e:
        r58 = new com.waze.ifs.ui.OmniSelectionFragment$FilteredOmniSelectAdapter;
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r58;
        r1 = r22;
        r0.<init>(r1);
        r0 = r58;
        r1 = r62;
        r1.mAdapter = r0;
        r0 = r62;
        r0 = r0.mAdapter;
        r49 = r0;
        r59 = r49;
        r59 = (com.waze.ifs.ui.OmniSelectionFragment.FilteredOmniSelectAdapter) r59;
        r58 = r59;
        r0 = r62;
        r14 = r0.mAddUserInput;
        r0 = r58;
        r0.setLast(r14);
        r0 = r62;
        r0 = r0.mAdapter;
        r49 = r0;
        goto L_0x0652;
    L_0x064f:
        goto L_0x04b3;
    L_0x0652:
        r17 = 0;
        r0 = r49;
        r1 = r17;
        r0.setShowLastItem(r1);
        goto L_0x064f;
    L_0x065c:
        r0 = r62;
        r7 = r0.mIsIndexed;
        if (r7 == 0) goto L_0x067c;
    L_0x0662:
        r60 = new com.waze.ifs.ui.OmniSelectionFragment$IndexedOmniSelectAdapter;
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r60;
        r1 = r22;
        r0.<init>(r1);
        goto L_0x0675;
    L_0x0672:
        goto L_0x04b3;
    L_0x0675:
        r0 = r60;
        r1 = r62;
        r1.mAdapter = r0;
        goto L_0x0672;
    L_0x067c:
        r0 = r62;
        r7 = r0.mIsExpandable;
        if (r7 == 0) goto L_0x069c;
    L_0x0682:
        r61 = new com.waze.ifs.ui.OmniSelectionFragment$OmniSelectExpandableAdapter;
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r61;
        r1 = r22;
        r0.<init>(r1);
        goto L_0x0695;
    L_0x0692:
        goto L_0x04b3;
    L_0x0695:
        r0 = r61;
        r1 = r62;
        r1.mAdapter = r0;
        goto L_0x0692;
    L_0x069c:
        r49 = new com.waze.ifs.ui.OmniSelectionFragment$OmniSelectAdapter;
        r0 = r62;
        r22 = r0.getActivity();
        r0 = r49;
        r1 = r22;
        r0.<init>(r1);
        goto L_0x06af;
    L_0x06ac:
        goto L_0x04b3;
    L_0x06af:
        r0 = r49;
        r1 = r62;
        r1.mAdapter = r0;
        goto L_0x06ac;
    L_0x06b6:
        r0 = r62;
        r0 = r0.mListView;
        r45 = r0;
        r0 = r62;
        r0 = r0.mAdapter;
        r49 = r0;
        goto L_0x06c6;
    L_0x06c3:
        goto L_0x0555;
    L_0x06c6:
        r0 = r45;
        r1 = r49;
        r0.setAdapter(r1);
        goto L_0x06c3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ifs.ui.OmniSelectionFragment.onCreateView(android.view.LayoutInflater, android.view.ViewGroup, android.os.Bundle):android.view.View");
    }

    public void setIndexed(boolean $z0) throws  {
        this.mIsIndexed = $z0;
    }

    public void setSearch(boolean $z0) throws  {
        this.mIsSearch = $z0;
    }

    public void setFilter(boolean $z0) throws  {
        this.mIsFilter = $z0;
    }

    public void setMultiSelect(boolean $z0) throws  {
        this.mIsMultiSelect = $z0;
    }

    public void setUserInput(boolean $z0, String $r1) throws  {
        this.mIsUserInput = $z0;
        this.mUserInputFormat = $r1;
    }

    public void setAutoComplete(boolean $z0) throws  {
        this.mIsAutoComplete = $z0;
    }

    public void setExpandable(boolean $z0) throws  {
        this.mIsExpandable = $z0;
    }

    public void setTitle(String $r1) throws  {
        this.mTitleText = $r1;
    }

    public void setEditBoxHint(String $r1) throws  {
        this.mEditBoxHint = $r1;
    }

    public void setSubTitle(String $r1) throws  {
        this.mSubTitle = $r1;
    }

    public void setValues(SettingsValue[] $r1) throws  {
        this.mValues = $r1;
    }

    public void setTopOption(String $r1, int $i0) throws  {
        this.mTopOptionText = $r1;
        this.mTopOptionIcon = $i0;
    }

    public void setMaxUserInputLength(int $i0) throws  {
        this.mMaxUserInputLength = $i0;
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putBoolean(TAG + ".mIsIndexed", this.mIsIndexed);
        $r1.putBoolean(TAG + ".mIsSearch", this.mIsSearch);
        $r1.putBoolean(TAG + ".mIsFilter", this.mIsFilter);
        $r1.putBoolean(TAG + ".mIsMultiSelect", this.mIsMultiSelect);
        $r1.putBoolean(TAG + ".mIsUserInput", this.mIsUserInput);
        $r1.putString(TAG + ".mUserInputFormat", this.mUserInputFormat);
        $r1.putBoolean(TAG + ".mIsExpandable", this.mIsExpandable);
        $r1.putBoolean(TAG + ".mIsAutoComplete", this.mIsAutoComplete);
        $r1.putString(TAG + ".mTitleText", this.mTitleText);
        $r1.putString(TAG + ".mEditBoxHint", this.mEditBoxHint);
        $r1.putString(TAG + ".mSubTitle", this.mSubTitle);
        $r1.putParcelableArray(TAG + ".mValues", (Parcelable[]) this.mValues);
        $r1.putInt(TAG + ".mTimesEnteredSearch", this.mTimesEnteredSearch);
        $r1.putString(TAG + ".mTopOptionText", this.mTopOptionText);
        $r1.putInt(TAG + ".mTopOptionIcon", this.mTopOptionIcon);
    }

    public void onResume() throws  {
        super.onResume();
        EditText $r2 = (EditText) this.f83r.findViewById(C1283R.id.omniSelectSearch);
        InputMethodManager $r5 = (InputMethodManager) getActivity().getSystemService("input_method");
        if (!this.mIsSearch && !this.mIsAutoComplete && !this.mIsUserInput) {
            $r5.hideSoftInputFromWindow($r2.getWindowToken(), 0);
        } else if (this.mIsUserInput && this.mIsPortrait) {
            $r5.showSoftInput($r2, 0);
        }
    }

    private void setResultValueAndContinue(int $i0, SettingsValue $r1, boolean $z0) throws  {
        if ($r1 != null) {
            $r1.isSelected = false;
            if (this.mIsSearch || this.mIsAutoComplete) {
                ((InputMethodManager) getActivity().getSystemService("input_method")).hideSoftInputFromWindow(((EditText) this.f83r.findViewById(C1283R.id.omniSelectSearch)).getWindowToken(), 0);
            }
            if (this.mIsFilter) {
                if (this.mIsExpandable) {
                    $i0 = ((FilteredOmniSelectExpandableAdapter) this.mAdapter).getOriginalIndex($r1);
                } else {
                    $i0 = ((FilteredOmniSelectAdapter) this.mAdapter).getOriginalIndex($r1);
                }
            }
            ((IOmniSelect) getActivity()).omniSelected($i0, $r1.value, $r1.display, $z0);
        }
    }

    private static float calcRank(String $r0, String $r1) throws  {
        if ($r1.length() == 0) {
            return 1.0f;
        }
        if ($r0.length() == 0) {
            return 0.0f;
        }
        int $i0 = Math.min($r0.length(), $r1.length());
        byte $b2 = (byte) 2;
        if ($i0 <= 2) {
            $b2 = (byte) 0;
        }
        if ($i0 <= 4) {
            $b2 = (byte) 1;
        }
        return calcRank($r0, $r1, $b2);
    }

    private static float calcRank(String $r0, String $r1, int $i0) throws  {
        if ($r1.length() == 0) {
            return ((float) $i0) * CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
        }
        int $i1 = $r0.indexOf($r1.charAt(0));
        if ($i1 >= 0) {
            float $f0;
            if ($i1 == 0 || Character.isWhitespace($r0.charAt($i1 - 1))) {
                $f0 = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR;
            } else {
                $f0 = CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR - (((float) $i1) * CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR);
            }
            float $f1 = calcRank($r0.substring($i1 + 1), $r1.substring(1), $i0);
            if ($f1 + $f0 > 1.0f) {
                return $f0 + $f1;
            }
            float $f2 = calcRank($r0.substring($i1 + 1), $r1, $i0);
            if ($f2 + $f0 > 1.0f) {
                return $f0 + $f2;
            }
            if ($i0 > 0) {
                return $f0 + Math.max($f2, Math.max($f1, calcRank($r0, $r1.substring(1), $i0 - 1)));
            }
            return $f0 + Math.max($f2, $f1);
        } else if ($i0 > 0) {
            return calcRank($r0, $r1.substring(1), $i0 - 1);
        } else {
            return -0.5f * ((float) $r1.length());
        }
    }

    public static void testCalcRank() throws  {
        boolean $z1;
        boolean $z0 = true;
        Assert.assertTrue(calcRank("car rental", "car") > LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN);
        if (calcRank("car rental", "rent") >= LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("car rental", "re") >= 1.0f) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("car rental", "boat") < 1.0f) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("waze", "ee") < 1.0f) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("college / university", "uni") >= LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("college / university", "univ") > LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("college / university", "univer") > 3.0f) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("college / university", "universit") > 4.0f) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("waze", "wazeeeeee") < 1.0f) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("waze", "wazeeeeeeee") <= 0.0f) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        Assert.assertTrue($z1);
        if (calcRank("waze", "") < 1.0f) {
            $z0 = false;
        }
        Assert.assertTrue($z0);
    }

    public static SettingsValue[] testValues1() throws  {
        Random $r0 = new Random();
        int $i0 = $r0.nextInt(100) + 50;
        SettingsValue[] $r1 = new SettingsValue[$i0];
        for (int $i4 = 0; $i4 < $i0; $i4++) {
            SettingsValue $r2;
            $r1[$i4] = new SettingsValue("", "", false);
            $r1[$i4].display = "";
            int $i3 = $r0.nextInt(2) + 1;
            for (int $i5 = 0; $i5 < $i3; $i5++) {
                int $i2 = $r0.nextInt(6) + 3;
                int $i6 = 0;
                while ($i6 < $i2) {
                    char $c1 = (char) (($i6 == 0 ? (byte) 65 : (byte) 97) + $r0.nextInt(10));
                    StringBuilder stringBuilder = new StringBuilder();
                    $r2 = $r1[$i4];
                    $r2.display = stringBuilder.append($r2.display).append($c1).toString();
                    $i6++;
                }
                if ($i5 < $i3 - 1) {
                    stringBuilder = new StringBuilder();
                    $r2 = $r1[$i4];
                    $r2.display = stringBuilder.append($r2.display).append(' ').toString();
                }
            }
            $r2 = $r1[$i4];
            String $r4 = $r1[$i4].display;
            $r2.value = $r4;
            $r2 = $r1[$i4];
            $r4 = $r1[$i4].display;
            $r2.display2 = $r4;
        }
        return $r1;
    }
}
