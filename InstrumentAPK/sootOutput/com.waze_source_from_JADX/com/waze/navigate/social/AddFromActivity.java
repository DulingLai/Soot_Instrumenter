package com.waze.navigate.social;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.util.SparseIntArray;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.tokenautocomplete.FilteredArrayAdapter;
import com.tokenautocomplete.TokenCompleteTextView.TokenListener;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.autocomplete.ContactsCompletionView;
import com.waze.autocomplete.ContactsCompletionView.IGetViewForObject;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.WazeCheckBoxView;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.IFriendsChanged;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.AddFriendsListener;
import com.waze.navigate.DriveToNativeManager.FriendsListListener;
import com.waze.navigate.DriveToNativeManager.PersonMappingListener;
import com.waze.phone.AddressBookImpl;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.utils.ImageRepository;
import com.waze.view.text.AutoResizeTextView;
import com.waze.view.title.TitleBar;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AddFromActivity extends ActivityBase implements OnItemClickListener, TokenListener, IFriendsChanged {
    public static int INTENT_FROM_DEFAULT = 0;
    public static int INTENT_FROM_FB = 1;
    public static int INTENT_FROM_SHARE = 2;
    public static String INTENT_FROM_WHERE = "INTENT_FROM_WHERE";
    public static String INTENT_SELECTED = "INTENT_SELECTED";
    public static String INTENT_SUGGESTED = "INTENT_SUGGESTED";
    private ContactsCompletionView mCompletionView;
    private SparseIntArray mCurFriendsUids;
    private ListView mFriendsListView;
    boolean mHasReadAddressBook = false;
    boolean mHasReadCurFriends = false;
    boolean mHasReadUidMap = false;
    private int mNumSuggestions = 0;
    private ArrayList<PersonBase> mPersonArray;
    private PersonArrayAdapter mPersonArrayAdapter;
    private PersonFilteredArrayAdapter mPersonFilteredArrayAdapter;
    private SparseIntArray mPersonIdMatch;
    private SparseArray<PersonBase> mPreSelected = new SparseArray();
    private int mSource = INTENT_FROM_DEFAULT;
    private SparseArray<PersonBase> mSuggested = new SparseArray();
    private SparseIntArray mSuggestionsUids;
    private TitleBar mTitleBar;

    class C21901 implements OnClickListener {
        C21901() throws  {
        }

        public void onClick(View v) throws  {
            if (AddFromActivity.this.mSource == AddFromActivity.INTENT_FROM_FB) {
                AddFromActivity.this.addFromFacebook();
                AddFromActivity.this.setResult(201);
            } else if (AddFromActivity.this.mSource == AddFromActivity.INTENT_FROM_SHARE) {
                AddFromActivity.this.addFromShare();
            } else {
                AddFromActivity.this.addFromContacts();
                AddFromActivity.this.setResult(201);
            }
            AddFromActivity.this.finish();
        }
    }

    class C21912 implements OnEditorActionListener {
        C21912() throws  {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) throws  {
            if (!AddFromActivity.this.mCompletionView.enoughToFilter() || AddFromActivity.this.mPersonFilteredArrayAdapter == null || AddFromActivity.this.mPersonFilteredArrayAdapter.getCount() <= 0) {
                ((InputMethodManager) AddFromActivity.this.getSystemService("input_method")).hideSoftInputFromWindow(AddFromActivity.this.mCompletionView.getWindowToken(), 0);
            } else {
                if (!AddFromActivity.this.mCompletionView.getObjects().contains(AddFromActivity.this.mPersonFilteredArrayAdapter.getItem(0))) {
                    AddFromActivity.this.mCompletionView.performCompletion();
                    AddFromActivity.this.mPersonArrayAdapter.notifyDataSetChanged();
                }
            }
            return true;
        }
    }

    class C21923 implements OnScrollListener {
        C21923() throws  {
        }

        public void onScrollStateChanged(AbsListView view, int $i0) throws  {
            if ($i0 == 1) {
                AddFromActivity.this.mFriendsListView.requestFocus();
            }
        }

        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) throws  {
        }
    }

    class C21934 implements IGetViewForObject {
        C21934() throws  {
        }

        public View getViewForObject(Object $r1) throws  {
            PersonBase $r2 = (PersonBase) $r1;
            View $r7 = AddFriendsUtils.inflateFriendToken($r2, (ViewGroup) AddFromActivity.this.mCompletionView.getParent());
            $r7.setTag($r2);
            return $r7;
        }
    }

    public interface IOnReadDone {
        void onReadDone(@Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) int i, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ArrayList<PersonBase> arrayList) throws ;
    }

    class C21945 implements IOnReadDone {
        C21945() throws  {
        }

        public void onReadDone(@Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ArrayList<PersonBase> $r1) throws  {
            AddFromActivity.this.mNumSuggestions = $i0;
            AddFromActivity.this.readDone();
            Iterator $r3 = $r1.iterator();
            while ($r3.hasNext()) {
                AddFromActivity.this.mCompletionView.addObject((PersonBase) $r3.next());
            }
        }
    }

    class C21956 implements Runnable {
        C21956() throws  {
        }

        public void run() throws  {
            AddFromActivity.this.mCompletionView.setMaxHeightLimit((int) (120.0f * AddFromActivity.this.getResources().getDisplayMetrics().density));
        }
    }

    class C21998 implements AddFriendsListener {
        C21998() throws  {
        }

        public void onComplete(AddFriendsData $r1) throws  {
            AddFromActivity.this.mPersonArray = new ArrayList();
            AddFromActivity.this.mSuggestionsUids = new SparseIntArray($r1.SuggestionFriends.length);
            int $i0 = 1;
            if ($r1 != null && $r1.SuggestionFriends.length > 0) {
                AddFromActivity.this.mNumSuggestions = $r1.SuggestionFriends.length;
                for (FriendUserData $r2 : $r1.SuggestionFriends) {
                    AddFromActivity.this.mPersonArray.add($r2);
                    AddFromActivity.this.mSuggestionsUids.put($r2.getID(), $i0);
                    $i0++;
                }
            }
            AddFromActivity.this.mHasReadAddressBook = true;
            if (AddFromActivity.this.mHasReadUidMap && AddFromActivity.this.mHasReadCurFriends) {
                AddFromActivity.this.readAddressBook();
            }
        }
    }

    class C22009 implements OnCompleteTaskListener {
        C22009() throws  {
        }

        public void onCompleted() throws  {
            AddFromActivity.this.mHasReadUidMap = true;
            if (AddFromActivity.this.mHasReadAddressBook && AddFromActivity.this.mHasReadCurFriends) {
                AddFromActivity.this.readAddressBook();
            }
        }
    }

    public static class PersonArrayAdapter extends ArrayAdapter<PersonBase> {
        private NativeManager _nativeManager;
        private SparseBooleanArray _selectedIds;
        private ActivityBase activity;
        private boolean hasMore;
        private LayoutInflater inflater;
        private int mHeaderBg;
        private int mHeaderText;
        private boolean mShowOnline;
        private int numSuggested;
        private boolean showStatus;

        public PersonArrayAdapter(@Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/List", "<", "Lcom/waze/user/PersonBase;", ">;IZ)V"}) ActivityBase $r1, @Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/List", "<", "Lcom/waze/user/PersonBase;", ">;IZ)V"}) List<PersonBase> $r2, @Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/List", "<", "Lcom/waze/user/PersonBase;", ">;IZ)V"}) int $i0, @Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/List", "<", "Lcom/waze/user/PersonBase;", ">;IZ)V"}) boolean $z0) throws  {
            super($r1, 0, $r2);
            this.inflater = (LayoutInflater) $r1.getSystemService("layout_inflater");
            this.activity = $r1;
            this.numSuggested = $i0;
            boolean $z1 = $i0 > 0 && $i0 < $r2.size();
            this.hasMore = $z1;
            this.showStatus = $z0;
            this._nativeManager = NativeManager.getInstance();
            this._selectedIds = new SparseBooleanArray();
            this.mHeaderBg = $r1.getResources().getColor(C1283R.color.White);
            this.mHeaderText = $r1.getResources().getColor(C1283R.color.Light);
        }

        public int getCount() throws  {
            int $i0 = super.getCount();
            if (this.hasMore) {
                return $i0 + 2;
            }
            return $i0;
        }

        void setSelected(int $i0) throws  {
            this._selectedIds.put($i0, true);
        }

        void setUnselected(int $i0) throws  {
            this._selectedIds.put($i0, false);
        }

        public View getView(int $i0, View $r1, ViewGroup $r2) throws  {
            if (this.hasMore) {
                if ($i0 == 0) {
                    return makeHeader(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_SUGGESTED_FRIENDS));
                }
                $i0--;
                if ($i0 == this.numSuggested) {
                    return makeHeader(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_MORE_FRIENDS));
                }
                if ($i0 > this.numSuggested) {
                    $i0--;
                }
            }
            PersonBase $r6 = (PersonBase) getItem($i0);
            if ($r1 == null || ($r1 instanceof TextView)) {
                $r1 = this.inflater.inflate(C1283R.layout.add_friends_in_list, $r2, false);
            }
            $r1.setTag($r6);
            AddFromActivity.fillAddFriendInList($r6, $r1, this.activity, this.showStatus, true, this._selectedIds.get($r6.getID()), this.mShowOnline, true);
            return $r1;
        }

        public void setHeaderColors(int $i0, int $i1) throws  {
            this.mHeaderBg = $i0;
            this.mHeaderText = $i1;
        }

        private View makeHeader(String $r1) throws  {
            TextView $r2 = new TextView(this.activity);
            $r2.setText($r1);
            $r2.setBackgroundColor(this.mHeaderBg);
            $r2.setTextColor(this.mHeaderText);
            $r2.setTextSize(2, 18.0f);
            float $f0 = this.activity.getResources().getDisplayMetrics().density;
            int $i0 = (int) (AutoResizeTextView.MIN_TEXT_SIZE * $f0);
            int $i1 = (int) (5.0f * $f0);
            $r2.setPadding($i0, $i1, $i0, $i1);
            return $r2;
        }

        public void setShowOnline(boolean $z0) throws  {
            this.mShowOnline = $z0;
        }
    }

    public static final class PersonFilteredArrayAdapter extends FilteredArrayAdapter<PersonBase> {
        ActivityBase ab;

        public PersonFilteredArrayAdapter(@Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/List", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ActivityBase $r1, @Signature({"(", "Lcom/waze/ifs/ui/ActivityBase;", "Ljava/util/List", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) List<PersonBase> $r2) throws  {
            super((Context) $r1, 0, (List) $r2);
            this.ab = $r1;
        }

        public View getView(int $i0, View $r2, ViewGroup $r1) throws  {
            if ($r2 == null) {
                $r2 = ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.add_friends_in_list, $r1, false);
            }
            AddFromActivity.fillAddFriendInList(getItem($i0), $r2, this.ab, true, false, false, false, true);
            return $r2;
        }

        public PersonBase getItem(int $i0) throws  {
            return (PersonBase) super.getItem($i0);
        }

        protected boolean keepObject(PersonBase $r1, String $r2) throws  {
            return AddFromActivity.nameContains($r1, $r2);
        }
    }

    static void fillAddFriendInList(com.waze.user.PersonBase r16, android.view.View r17, com.waze.ifs.ui.ActivityBase r18, boolean r19, boolean r20, boolean r21, boolean r22, boolean r23) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:22:0x008f
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
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
        r0 = r16;
        r2 = r0.getName();
        r0 = r16;
        r3 = r0.getImage();
        r0 = r18;
        r1 = r17;
        com.waze.navigate.social.AddFriendsUtils.setNameAndImage(r0, r1, r2, r3);
        r5 = 2131689713; // 0x7f0f00f1 float:1.900845E38 double:1.053194655E-314;
        r0 = r17;
        r4 = r0.findViewById(r5);
        r7 = r4;
        r7 = (android.widget.TextView) r7;
        r6 = r7;
        if (r19 != 0) goto L_0x009a;
    L_0x0022:
        r5 = 8;
        r6.setVisibility(r5);
    L_0x0027:
        r5 = 2131689721; // 0x7f0f00f9 float:1.9008465E38 double:1.0531946587E-314;
        r0 = r17;
        r4 = r0.findViewById(r5);
        r9 = r4;
        r9 = (com.waze.ifs.ui.WazeCheckBoxView) r9;
        r8 = r9;
        if (r20 == 0) goto L_0x0106;
    L_0x0036:
        r5 = 0;
        r8.setVisibility(r5);
        r0 = r21;
        r8.setValue(r0);
    L_0x003f:
        r5 = 2131689711; // 0x7f0f00ef float:1.9008445E38 double:1.053194654E-314;
        r0 = r17;
        r4 = r0.findViewById(r5);
        r5 = 8;
        r4.setVisibility(r5);
        if (r22 == 0) goto L_0x010e;
    L_0x004f:
        r0 = r16;
        r0 = r0 instanceof com.waze.user.FriendUserData;
        r19 = r0;
        if (r19 == 0) goto L_0x010e;
    L_0x0057:
        r11 = r16;
        r11 = (com.waze.user.FriendUserData) r11;
        r10 = r11;
        r5 = 2131689706; // 0x7f0f00ea float:1.9008435E38 double:1.0531946513E-314;
        r0 = r17;
        r4 = r0.findViewById(r5);
        r0 = r10.isOnline;
        r19 = r0;
        if (r19 == 0) goto L_0x010c;
    L_0x006c:
        goto L_0x0070;
    L_0x006d:
        goto L_0x0027;
    L_0x0070:
        r12 = 0;
        goto L_0x0075;
    L_0x0072:
        goto L_0x0027;
    L_0x0075:
        r4.setVisibility(r12);
    L_0x0078:
        if (r23 == 0) goto L_0x011c;
    L_0x007a:
        r0 = r16;
        r19 = r0.getIsOnWaze();
        if (r19 == 0) goto L_0x011c;
    L_0x0082:
        r5 = 2131689720; // 0x7f0f00f8 float:1.9008463E38 double:1.053194658E-314;
        r0 = r17;
        r17 = r0.findViewById(r5);
        goto L_0x0093;
    L_0x008c:
        goto L_0x0027;
        goto L_0x0093;
    L_0x0090:
        goto L_0x0027;
    L_0x0093:
        r5 = 0;
        r0 = r17;
        r0.setVisibility(r5);
        return;
    L_0x009a:
        r5 = 0;
        r6.setVisibility(r5);
        goto L_0x00a2;
    L_0x009f:
        goto L_0x003f;
    L_0x00a2:
        r0 = r16;
        r19 = r0.getIsOnWaze();
        if (r19 == 0) goto L_0x00d0;
    L_0x00aa:
        r0 = r16;
        r2 = r0.mPhone;
        if (r2 == 0) goto L_0x00ca;
    L_0x00b0:
        r0 = r16;
        r2 = r0.mPhone;
        r13 = r2.length();
        if (r13 <= 0) goto L_0x00ca;
    L_0x00ba:
        r0 = r16;
        r2 = r0.mPhone;
        goto L_0x00c2;
    L_0x00bf:
        goto L_0x0075;
    L_0x00c2:
        r6.setText(r2);
        goto L_0x006d;
        goto L_0x00ca;
    L_0x00c7:
        goto L_0x0078;
    L_0x00ca:
        r5 = 8;
        r6.setVisibility(r5);
        goto L_0x0072;
    L_0x00d0:
        r14 = com.waze.NativeManager.getInstance();
        r0 = r16;
        r2 = r0.mPhone;
        if (r2 == 0) goto L_0x00fc;
    L_0x00da:
        r0 = r16;
        r2 = r0.mPhone;
        r13 = r2.length();
        if (r13 <= 0) goto L_0x00fc;
    L_0x00e4:
        r5 = 1403; // 0x57b float:1.966E-42 double:6.93E-321;
        r3 = r14.getLanguageString(r5);
        r5 = 1;
        r15 = new java.lang.Object[r5];
        r0 = r16;
        r2 = r0.mPhone;
        r5 = 0;
        r15[r5] = r2;
        r2 = java.lang.String.format(r3, r15);
        r6.setText(r2);
        goto L_0x008c;
    L_0x00fc:
        r5 = 1286; // 0x506 float:1.802E-42 double:6.354E-321;
        r2 = r14.getLanguageString(r5);
        r6.setText(r2);
        goto L_0x0090;
    L_0x0106:
        r5 = 8;
        r8.setVisibility(r5);
        goto L_0x009f;
    L_0x010c:
        r12 = 4;
        goto L_0x00bf;
    L_0x010e:
        r5 = 2131689706; // 0x7f0f00ea float:1.9008435E38 double:1.0531946513E-314;
        r0 = r17;
        r4 = r0.findViewById(r5);
        r5 = 4;
        r4.setVisibility(r5);
        goto L_0x00c7;
    L_0x011c:
        r5 = 2131689720; // 0x7f0f00f8 float:1.9008463E38 double:1.053194658E-314;
        r0 = r17;
        r17 = r0.findViewById(r5);
        r5 = 8;
        r0 = r17;
        r0.setVisibility(r5);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.social.AddFromActivity.fillAddFriendInList(com.waze.user.PersonBase, android.view.View, com.waze.ifs.ui.ActivityBase, boolean, boolean, boolean, boolean, boolean):void");
    }

    protected void onCreate(android.os.Bundle r44) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:76:0x0297 in {2, 20, 34, 41, 42, 45, 48, 50, 52, 58, 61, 63, 68, 71, 72, 74, 77, 78} preds:[]
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
        r43 = this;
        r3 = 375; // 0x177 float:5.25E-43 double:1.853E-321;
        r0 = r43;
        r1 = r44;
        super.onCreate(r1);
        r0 = r43;
        r4 = r0.getWindow();
        r5 = 3;
        r4.setSoftInputMode(r5);
        r6 = com.waze.utils.ImageRepository.instance;
        r6.initExecutor();
        r7 = com.waze.phone.AddressBookImpl.getInstance();
        r8 = r7.wasSyncExecuted();
        if (r8 != 0) goto L_0x002b;
    L_0x0022:
        r7 = com.waze.phone.AddressBookImpl.getInstance();
        r5 = 1;
        r9 = 0;
        r7.performSync(r5, r9);
    L_0x002b:
        r0 = r43;
        r10 = r0.getIntent();
        if (r10 == 0) goto L_0x010f;
    L_0x0033:
        r11 = INTENT_FROM_WHERE;
        r8 = r10.hasExtra(r11);
        if (r8 == 0) goto L_0x010f;
    L_0x003b:
        r11 = INTENT_FROM_WHERE;
        r12 = INTENT_FROM_DEFAULT;
        r12 = r10.getIntExtra(r11, r12);
        r0 = r43;
        r0.mSource = r12;
        r11 = INTENT_SELECTED;
        r8 = r10.hasExtra(r11);
        if (r8 == 0) goto L_0x00ab;
    L_0x004f:
        r0 = r43;
        r13 = r0.getIntent();
        r44 = r13.getExtras();
        r11 = INTENT_SELECTED;
        r0 = r44;
        r14 = r0.getSerializable(r11);
        r8 = r14 instanceof java.util.ArrayList;
        if (r8 == 0) goto L_0x00ab;
    L_0x0065:
        r16 = r14;
        r16 = (java.util.ArrayList) r16;
        r15 = r16;
        if (r15 == 0) goto L_0x00ab;
    L_0x006d:
        r8 = r15.isEmpty();
        if (r8 != 0) goto L_0x00ab;
    L_0x0073:
        r17 = r15.iterator();
    L_0x0077:
        r0 = r17;
        r8 = r0.hasNext();
        if (r8 == 0) goto L_0x00ab;
    L_0x007f:
        r0 = r17;
        r18 = r0.next();
        r0 = r18;
        r8 = r0 instanceof com.waze.user.PersonBase;
        if (r8 == 0) goto L_0x0077;
    L_0x008b:
        r0 = r43;
        r0 = r0.mPreSelected;
        r19 = r0;
        r21 = r18;
        r21 = (com.waze.user.PersonBase) r21;
        r20 = r21;
        r0 = r20;
        r12 = r0.getID();
        r22 = r18;
        r22 = (com.waze.user.PersonBase) r22;
        r20 = r22;
        r0 = r19;
        r1 = r20;
        r0.put(r12, r1);
        goto L_0x0077;
    L_0x00ab:
        r11 = INTENT_SUGGESTED;
        r8 = r10.hasExtra(r11);
        if (r8 == 0) goto L_0x010f;
    L_0x00b3:
        r0 = r43;
        r10 = r0.getIntent();
        r44 = r10.getExtras();
        r11 = INTENT_SUGGESTED;
        r0 = r44;
        r14 = r0.getSerializable(r11);
        r8 = r14 instanceof java.util.ArrayList;
        if (r8 == 0) goto L_0x010f;
    L_0x00c9:
        r23 = r14;
        r23 = (java.util.ArrayList) r23;
        r15 = r23;
        if (r15 == 0) goto L_0x010f;
    L_0x00d1:
        r8 = r15.isEmpty();
        if (r8 != 0) goto L_0x010f;
    L_0x00d7:
        r17 = r15.iterator();
    L_0x00db:
        r0 = r17;
        r8 = r0.hasNext();
        if (r8 == 0) goto L_0x010f;
    L_0x00e3:
        r0 = r17;
        r18 = r0.next();
        r0 = r18;
        r8 = r0 instanceof com.waze.user.PersonBase;
        if (r8 == 0) goto L_0x00db;
    L_0x00ef:
        r0 = r43;
        r0 = r0.mSuggested;
        r19 = r0;
        r24 = r18;
        r24 = (com.waze.user.PersonBase) r24;
        r20 = r24;
        r0 = r20;
        r12 = r0.getID();
        r25 = r18;
        r25 = (com.waze.user.PersonBase) r25;
        r20 = r25;
        r0 = r19;
        r1 = r20;
        r0.put(r12, r1);
        goto L_0x00db;
    L_0x010f:
        r5 = 2130903076; // 0x7f030024 float:1.741296E38 double:1.0528060045E-314;
        r0 = r43;
        r0.setContentView(r5);
        r26 = com.waze.NativeManager.getInstance();
        r5 = 2131689674; // 0x7f0f00ca float:1.900837E38 double:1.0531946355E-314;
        r0 = r43;
        r27 = r0.findViewById(r5);
        r29 = r27;
        r29 = (com.waze.view.title.TitleBar) r29;
        r28 = r29;
        r0 = r28;
        r1 = r43;
        r1.mTitleBar = r0;
        r0 = r43;
        r10 = r0.getIntent();
        r44 = r10.getExtras();
        r30 = "type";
        r0 = r44;
        r1 = r30;
        r12 = r0.getInt(r1);
        if (r12 <= 0) goto L_0x0275;
    L_0x0147:
        r5 = 1;
        if (r12 != r5) goto L_0x0258;
    L_0x014a:
        r0 = r43;
        r0 = r0.mTitleBar;
        r28 = r0;
        r0 = r43;
        r12 = r0.mSource;
        r31 = INTENT_FROM_SHARE;
        r0 = r31;
        if (r12 != r0) goto L_0x0255;
    L_0x015a:
        r3 = 375; // 0x177 float:5.25E-43 double:1.853E-321;
    L_0x015c:
        r5 = 44;
        r0 = r28;
        r1 = r43;
        r0.init(r1, r5, r3);
    L_0x0165:
        r0 = r43;
        r0 = r0.mTitleBar;
        r28 = r0;
        r32 = new com.waze.navigate.social.AddFromActivity$1;
        r0 = r32;
        r1 = r43;
        r0.<init>();
        r0 = r28;
        r1 = r32;
        r0.setOnClickCloseListener(r1);
        r0 = r43;
        r12 = r0.mSource;
        r31 = INTENT_FROM_SHARE;
        r0 = r31;
        if (r12 == r0) goto L_0x0191;
    L_0x0185:
        r0 = r43;
        r0 = r0.mTitleBar;
        r28 = r0;
        r5 = 1;
        r0 = r28;
        r0.setCloseButtonDisabled(r5);
    L_0x0191:
        r5 = 2131689735; // 0x7f0f0107 float:1.9008494E38 double:1.0531946657E-314;
        r0 = r43;
        r27 = r0.findViewById(r5);
        r34 = r27;
        r34 = (com.waze.autocomplete.ContactsCompletionView) r34;
        r33 = r34;
        r0 = r33;
        r1 = r43;
        r1.mCompletionView = r0;
        r0 = r43;
        r12 = r0.mSource;
        r31 = INTENT_FROM_SHARE;
        r0 = r31;
        if (r12 == r0) goto L_0x029e;
    L_0x01b0:
        r0 = r43;
        r0 = r0.mCompletionView;
        r33 = r0;
        r5 = 1459; // 0x5b3 float:2.044E-42 double:7.21E-321;
        r0 = r26;
        r11 = r0.getLanguageString(r5);
        r0 = r33;
        r0.setHint(r11);
    L_0x01c3:
        r0 = r43;
        r0 = r0.mCompletionView;
        r33 = r0;
        r5 = 0;
        r0 = r33;
        r0.allowDuplicates(r5);
        goto L_0x01d3;
    L_0x01d0:
        goto L_0x015c;
    L_0x01d3:
        r0 = r43;
        r0 = r0.mCompletionView;
        r33 = r0;
        r35 = new com.waze.navigate.social.AddFromActivity$2;
        r0 = r35;
        r1 = r43;
        r0.<init>();
        goto L_0x01e6;
    L_0x01e3:
        goto L_0x0165;
    L_0x01e6:
        r0 = r33;
        r1 = r35;
        r0.setOnEditorActionListener(r1);
        r5 = 2131689736; // 0x7f0f0108 float:1.9008496E38 double:1.053194666E-314;
        r0 = r43;
        r27 = r0.findViewById(r5);
        r37 = r27;
        r37 = (android.widget.ListView) r37;
        r36 = r37;
        r0 = r36;
        r1 = r43;
        r1.mFriendsListView = r0;
        r0 = r43;
        r0 = r0.mFriendsListView;
        r36 = r0;
        r38 = new com.waze.navigate.social.AddFromActivity$3;
        r0 = r38;
        r1 = r43;
        r0.<init>();
        goto L_0x0215;
    L_0x0212:
        goto L_0x01d0;
    L_0x0215:
        r0 = r36;
        r1 = r38;
        r0.setOnScrollListener(r1);
        r0 = r43;
        r0 = r0.mCompletionView;
        r33 = r0;
        r39 = new com.waze.navigate.social.AddFromActivity$4;
        goto L_0x0228;
    L_0x0225:
        goto L_0x01e3;
    L_0x0228:
        r0 = r39;
        r1 = r43;
        r0.<init>();
        r0 = r33;
        r1 = r39;
        r0.setIGetViewForObject(r1);
        r0 = r43;
        r12 = r0.mSource;
        r31 = INTENT_FROM_FB;
        goto L_0x0240;
    L_0x023d:
        goto L_0x01c3;
    L_0x0240:
        r0 = r31;
        if (r12 != r0) goto L_0x02b2;
    L_0x0244:
        r0 = r43;
        r0.getFriendsDataFromFacebook();
    L_0x0249:
        r40 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r0 = r40;
        r1 = r43;
        r0.addFriendsChangedListener(r1);
        return;
    L_0x0255:
        r3 = 293; // 0x125 float:4.1E-43 double:1.45E-321;
        goto L_0x0212;
    L_0x0258:
        r0 = r43;
        r0 = r0.mTitleBar;
        r28 = r0;
        r0 = r43;
        r12 = r0.mSource;
        r31 = INTENT_FROM_SHARE;
        r0 = r31;
        if (r12 != r0) goto L_0x0272;
    L_0x0268:
        r5 = 1141; // 0x475 float:1.599E-42 double:5.637E-321;
        r0 = r28;
        r1 = r43;
        r0.init(r1, r5, r3);
        goto L_0x0225;
    L_0x0272:
        r3 = 293; // 0x125 float:4.1E-43 double:1.45E-321;
        goto L_0x0268;
    L_0x0275:
        r0 = r43;
        r0 = r0.mTitleBar;
        r28 = r0;
        r0 = r43;
        r12 = r0.mSource;
        r31 = INTENT_FROM_SHARE;
        r0 = r31;
        if (r12 != r0) goto L_0x029b;
    L_0x0285:
        goto L_0x0289;
    L_0x0286:
        goto L_0x0165;
    L_0x0289:
        r5 = 1219; // 0x4c3 float:1.708E-42 double:6.023E-321;
        r0 = r28;
        r1 = r43;
        r0.init(r1, r5, r3);
        goto L_0x0296;
    L_0x0293:
        goto L_0x0249;
    L_0x0296:
        goto L_0x0286;
        goto L_0x029b;
    L_0x0298:
        goto L_0x0249;
    L_0x029b:
        r3 = 293; // 0x125 float:4.1E-43 double:1.45E-321;
        goto L_0x0289;
    L_0x029e:
        r0 = r43;
        r0 = r0.mCompletionView;
        r33 = r0;
        r5 = 1040; // 0x410 float:1.457E-42 double:5.14E-321;
        r0 = r26;
        r11 = r0.getLanguageString(r5);
        r0 = r33;
        r0.setHint(r11);
        goto L_0x023d;
    L_0x02b2:
        r0 = r43;
        r12 = r0.mSource;
        r31 = INTENT_FROM_SHARE;
        r0 = r31;
        if (r12 != r0) goto L_0x02e8;
    L_0x02bc:
        r15 = new java.util.ArrayList;
        r15.<init>();
        r0 = r43;
        r0.mPersonArray = r15;
        r0 = r43;
        r15 = r0.mPersonArray;
        r0 = r43;
        r0 = r0.mPreSelected;
        r19 = r0;
        r0 = r43;
        r0 = r0.mSuggested;
        r41 = r0;
        r42 = new com.waze.navigate.social.AddFromActivity$5;
        r0 = r42;
        r1 = r43;
        r0.<init>();
        r0 = r19;
        r1 = r41;
        r2 = r42;
        getFriendsDataFromShare(r15, r0, r1, r2);
        goto L_0x0293;
    L_0x02e8:
        r0 = r43;
        r0.getFriendsDataFromAddressBook();
        goto L_0x0298;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.social.AddFromActivity.onCreate(android.os.Bundle):void");
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        super.onActivityResult($i0, $i1, $r1);
    }

    void addFromFacebook() throws  {
        List<PersonBase> $r3 = this.mCompletionView.getObjects();
        int[] $r1 = new int[$r3.size()];
        int $i0 = 0;
        for (PersonBase id : $r3) {
            $r1[$i0] = id.getID();
            $i0++;
        }
        MyWazeNativeManager.getInstance().sendSocialAddFriends($r1, $i0, "Added " + $r3.size() + " friends.");
    }

    protected void onResume() throws  {
        super.onResume();
        this.mCompletionView.postDelayed(new C21956(), 1);
    }

    private void addFromShare() throws  {
        ArrayList $r4 = (ArrayList) this.mCompletionView.getObjects();
        Intent $r1 = new Intent();
        $r1.putExtra(INTENT_SELECTED, $r4);
        setResult(-1, $r1);
    }

    void addFromContacts() throws  {
        List<PersonBase> $r5 = this.mCompletionView.getObjects();
        int[] $r1 = new int[$r5.size()];
        String[] $r3 = new String[$r5.size()];
        int[] $r2 = new int[$r5.size()];
        int $i0 = 0;
        int $i1 = 0;
        for (PersonBase $r8 : $r5) {
            int $i2;
            if (this.mSuggestionsUids.get($r8.getID()) != 0) {
                $i2 = $r8.getID();
            } else {
                $i2 = this.mPersonIdMatch.get($r8.getID());
            }
            if ($i2 == 0) {
                $r3[$i1] = $r8.getPhone();
                $r2[$i1] = $r8.getID();
                $i1++;
            } else {
                $r1[$i0] = $i2;
                $i0++;
            }
        }
        if ($i1 > 0) {
            MyWazeNativeManager.getInstance().sendSocialInviteFriends($r2, $r3, $i1, String.format(NativeManager.getInstance().getLanguageString(1237), new Object[]{Integer.valueOf($i1)}));
        }
        if ($i0 > 0) {
            MyWazeNativeManager.getInstance().sendSocialAddFriends($r1, $i0, String.format(NativeManager.getInstance().getLanguageString(DisplayStrings.DS_PD_FRIENDS_ADDED), new Object[]{Integer.valueOf($i0)}));
        }
    }

    protected void onDestroy() throws  {
        MyWazeNativeManager.getInstance().removeFriendsChangedListener(this);
        ImageRepository.instance.endExecutor();
        super.onDestroy();
    }

    public static void getFriendsDataFromShare(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Lcom/waze/navigate/social/AddFromActivity$IOnReadDone;", ")V"}) final ArrayList<PersonBase> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Lcom/waze/navigate/social/AddFromActivity$IOnReadDone;", ")V"}) final SparseArray<PersonBase> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Lcom/waze/navigate/social/AddFromActivity$IOnReadDone;", ")V"}) final SparseArray<PersonBase> $r2, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Landroid/util/SparseArray", "<", "Lcom/waze/user/PersonBase;", ">;", "Lcom/waze/navigate/social/AddFromActivity$IOnReadDone;", ")V"}) final IOnReadDone $r3) throws  {
        AddressBookImpl.getInstance().GetPersonArrayWithMapping(new PersonMappingListener() {
            public void onComplete(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ArrayList<PersonBase> $r1) throws  {
                final ArrayList $r3 = new ArrayList();
                final SparseArray $r2 = new SparseArray();
                if ($r1 != null) {
                    Iterator $r4 = $r1.iterator();
                    while ($r4.hasNext()) {
                        PersonBase $r6 = (PersonBase) $r4.next();
                        $r0.add($r6);
                        $r2.put($r6.getID(), $r6);
                        if ($r1.get($r6.getID()) != null) {
                            $r3.add($r6);
                        }
                    }
                }
                DriveToNativeManager.getInstance().getFriendsListData(new FriendsListListener() {

                    class C21961 implements Comparator<PersonBase> {
                        C21961() throws  {
                        }

                        public int compare(PersonBase $r1, PersonBase $r2) throws  {
                            boolean $z1;
                            byte $b0 = (byte) -1;
                            boolean $z0 = $r2.get($r1.getID()) != null;
                            if ($r2.get($r2.getID()) != null) {
                                $z1 = true;
                            } else {
                                $z1 = false;
                            }
                            if ($z0 != $z1) {
                                return $z0 ? -1 : 1;
                            } else {
                                if ($r1.getIsOnWaze() == $r2.getIsOnWaze()) {
                                    return $r1.getName().compareToIgnoreCase($r2.getName());
                                }
                                if (!$r1.getIsOnWaze()) {
                                    $b0 = (byte) 1;
                                }
                                return $b0;
                            }
                        }
                    }

                    public void onComplete(FriendsListData $r1) throws  {
                        if ($r1 != null) {
                            FriendUserData[] $r4 = $r1;
                            $r1 = $r4;
                            for (FriendUserData $r3 : $r4.friends) {
                                PersonBase $r7 = (PersonBase) $r2.get($r3.getID());
                                if ($r7 != null) {
                                    if ($r7.getImage() == null) {
                                        $r7.setImage($r3.getImage());
                                    }
                                    if (!$r7.getIsOnWaze() && $r3.getIsOnWaze()) {
                                        $r7.setIsOnWaze(true);
                                    }
                                } else {
                                    $r0.add($r3);
                                    if ($r1.get($r3.getID()) != null) {
                                        $r3.add($r3);
                                    }
                                }
                            }
                        }
                        Collections.sort($r0, new C21961());
                        $r3.onReadDone($r2.size(), $r3);
                    }
                });
            }
        });
    }

    private void getFriendsDataFromAddressBook() throws  {
        DriveToNativeManager $r3 = DriveToNativeManager.getInstance();
        $r3.getAddFriendsData(new C21998());
        this.mPersonIdMatch = new SparseIntArray();
        AddressBookImpl.getInstance().fillMapBetweenContactIdToUid(this.mPersonIdMatch, new C22009());
        $r3.getFriendsListData(new FriendsListListener() {
            public void onComplete(FriendsListData $r1) throws  {
                AddFromActivity.this.mCurFriendsUids = new SparseIntArray($r1.friends.length);
                int $i0 = 1;
                if ($r1 != null && $r1.friends.length > 0) {
                    for (FriendUserData $r2 : $r1.friends) {
                        AddFromActivity.this.mCurFriendsUids.put($r2.getID(), $i0);
                        $i0++;
                    }
                }
                AddFromActivity.this.mHasReadCurFriends = true;
                if (AddFromActivity.this.mHasReadUidMap && AddFromActivity.this.mHasReadAddressBook) {
                    AddFromActivity.this.readAddressBook();
                }
            }
        });
    }

    private void getFriendsDataFromFacebook() throws  {
        DriveToNativeManager.getInstance().getRemovedFriendsData(new FriendsListListener() {

            class C21891 implements Comparator<PersonBase> {
                C21891() throws  {
                }

                public int compare(PersonBase $r1, PersonBase $r2) throws  {
                    return $r1.getName().compareToIgnoreCase($r2.getName());
                }
            }

            public void onComplete(FriendsListData $r1) throws  {
                AddFromActivity.this.mPersonArray = new ArrayList();
                AddFromActivity.this.mNumSuggestions = 0;
                if ($r1 != null && $r1.friends.length > 0) {
                    for (FriendUserData $r3 : $r1.friends) {
                        AddFromActivity.this.mPersonArray.add($r3);
                    }
                }
                Collections.sort(AddFromActivity.this.mPersonArray, new C21891());
                AddFromActivity.this.readDone();
            }
        });
    }

    void readAddressBook() throws  {
        AddressBookImpl $r3 = AddressBookImpl.getInstance();
        if ($r3.isAddressBookInitialized()) {
            ArrayList $r7 = $r3.GetPersonArrayFromAddressBook();
            if (!$r7.isEmpty()) {
                Collections.sort($r7, new Comparator<PersonBase>() {
                    public int compare(PersonBase $r1, PersonBase $r2) throws  {
                        return $r1.getName().compareToIgnoreCase($r2.getName());
                    }
                });
                Iterator $r8 = $r7.iterator();
                while ($r8.hasNext()) {
                    PersonBase $r10 = (PersonBase) $r8.next();
                    int $i0 = this.mPersonIdMatch.get($r10.getID());
                    if ($i0 != 0) {
                        int $i1 = this.mSuggestionsUids.get($i0);
                        if ($i1 != 0) {
                            PersonBase $r12 = (PersonBase) this.mPersonArray.get($i1 - 1);
                            if ($r10.getImage() != null) {
                                $r12.setImage($r10.getImage());
                            }
                            if ($r10.mPhone != null && $r10.mPhone.length() > 0) {
                                if ($r12.mPhone == null || $r12.mPhone.length() == 0) {
                                    $r12.mPhone = $r10.mPhone;
                                }
                            }
                        } else if (this.mCurFriendsUids.get($i0) == 0) {
                            $r10.setIsOnWaze(true);
                        }
                    }
                    this.mPersonArray.add($r10);
                }
            }
            readDone();
            return;
        }
        Logger.m43w("AddFromActivity: Addressbook not initialized yet");
        NativeManager $r4 = NativeManager.getInstance();
        MsgBox.openMessageBoxWithCallback($r4.getLanguageString((int) DisplayStrings.DS_UHHOHE), $r4.getLanguageString((int) DisplayStrings.DS_ADDRESS_BOOK_CONTACTS_NOT_LOADED_YET), false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) throws  {
                AddFromActivity.this.finish();
            }
        });
    }

    private void readDone() throws  {
        if (this.mPersonArray.isEmpty()) {
            NativeManager $r2 = NativeManager.getInstance();
            ((ImageView) findViewById(C1283R.id.friendsListEmptyImage)).setImageDrawable(ResManager.GetSkinDrawable("moods/sad.png"));
            findViewById(C1283R.id.friendsListEmptyLayout).setVisibility(0);
            ((TextView) findViewById(C1283R.id.friendsListEmptyMessage)).setText($r2.getLanguageString((int) DisplayStrings.DS_NO_FRIENDS_TO_WAZE_WITH));
            return;
        }
        findViewById(C1283R.id.friendsListEmptyLayout).setVisibility(8);
        friendsListPopulateFriends();
    }

    private void friendsListPopulateFriends() throws  {
        if (this.mPersonArray != null) {
            ArrayList $r1 = this.mPersonArray;
            int $i0 = this.mNumSuggestions;
            boolean $z0 = this.mSource == INTENT_FROM_DEFAULT || this.mSource == INTENT_FROM_SHARE;
            this.mPersonArrayAdapter = new PersonArrayAdapter(this, $r1, $i0, $z0);
            this.mFriendsListView.setAdapter(this.mPersonArrayAdapter);
            this.mFriendsListView.setOnItemClickListener(this);
            this.mPersonFilteredArrayAdapter = new PersonFilteredArrayAdapter(this, this.mPersonArray);
            this.mCompletionView.setAdapter(this.mPersonFilteredArrayAdapter);
            this.mCompletionView.setTokenListener(this);
            for (PersonBase $r9 : this.mCompletionView.getObjects()) {
                this.mPersonArrayAdapter.setSelected($r9.getID());
            }
        }
    }

    public void onTokenAdded(Object $r1) throws  {
        this.mPersonArrayAdapter.setSelected(((PersonBase) $r1).getID());
        View $r5 = this.mFriendsListView.findViewWithTag($r1);
        if ($r5 != null) {
            WazeCheckBoxView $r6 = (WazeCheckBoxView) $r5.findViewById(C1283R.id.addFriendsCheckbox);
            if (!$r6.isChecked()) {
                $r6.setValue(true);
            }
        }
        if (this.mSource != INTENT_FROM_SHARE) {
            this.mTitleBar.setCloseButtonDisabled(false);
        }
    }

    public void onTokenRemoved(Object $r1) throws  {
        this.mPersonArrayAdapter.setUnselected(((PersonBase) $r1).getID());
        View $r5 = this.mFriendsListView.findViewWithTag($r1);
        if ($r5 != null) {
            WazeCheckBoxView $r6 = (WazeCheckBoxView) $r5.findViewById(C1283R.id.addFriendsCheckbox);
            if ($r6.isChecked()) {
                $r6.setValue(false);
            }
        }
        if (this.mSource != INTENT_FROM_SHARE) {
            this.mTitleBar.setCloseButtonDisabled(this.mCompletionView.getObjects().isEmpty());
        }
    }

    public static boolean nameContains(PersonBase $r0, String $r1) throws  {
        $r1 = $r1.toLowerCase();
        String $r3 = $r0.getName().toLowerCase().replaceAll("[\\p{Cc}\\p{Cf}\\p{Co}\\p{Cn}]", "");
        String $r2 = $r3;
        if ($r3.startsWith($r1)) {
            return true;
        }
        int $i0 = $r3.indexOf(32);
        while ($i0 >= 0) {
            $r3 = $r2.substring($i0 + 1);
            $r2 = $r3;
            if ($r3.startsWith($r1)) {
                return true;
            }
            $i0 = $r3.indexOf(32);
        }
        return false;
    }

    public void onItemClick(@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) AdapterView<?> adapterView, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) View $r2, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) int position, @Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"}) long id) throws  {
        boolean $z0 = $r2;
        $r2 = $z0;
        if (!($z0 instanceof TextView)) {
            WazeCheckBoxView $r4 = (WazeCheckBoxView) $r2.findViewById(C1283R.id.addFriendsCheckbox);
            PersonBase $r6 = (PersonBase) $r2.getTag();
            if ($r4.isChecked()) {
                $r4.setValue(false, true);
                this.mCompletionView.removeObject($r6);
                for (Object $r5 : this.mCompletionView.getObjects()) {
                    if (((PersonBase) $r5).getID() == $r6.getID()) {
                        this.mCompletionView.removeObject($r5);
                    }
                }
            } else {
                $r4.setValue(true, true);
                for (Object $r52 : this.mCompletionView.getObjects()) {
                    if (((PersonBase) $r52).getID() == $r6.getID()) {
                        this.mCompletionView.removeObject($r52);
                    }
                }
                this.mCompletionView.addObject($r6);
            }
            this.mCompletionView.setMaxLines(1);
            this.mCompletionView.requestFocus();
            this.mCompletionView.setMaxLines(1);
        }
    }

    public void onFriendsChanged() throws  {
    }
}
