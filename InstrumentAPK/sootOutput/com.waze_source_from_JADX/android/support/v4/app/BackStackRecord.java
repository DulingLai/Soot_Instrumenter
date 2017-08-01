package android.support.v4.app;

import android.os.Build.VERSION;
import android.support.v4.app.FragmentManager.BackStackEntry;
import android.support.v4.app.FragmentTransitionCompat21.EpicenterView;
import android.support.v4.app.FragmentTransitionCompat21.ViewRetriever;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LogWriter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import dalvik.annotation.Signature;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

final class BackStackRecord extends FragmentTransaction implements BackStackEntry, Runnable {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SHOW = 5;
    static final boolean SUPPORTS_TRANSITIONS = (VERSION.SDK_INT >= 21);
    static final String TAG = "FragmentManager";
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack = true;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    Op mHead;
    int mIndex = -1;
    final FragmentManagerImpl mManager;
    String mName;
    int mNumOp;
    int mPopEnterAnim;
    int mPopExitAnim;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    Op mTail;
    int mTransition;
    int mTransitionStyle;

    static final class Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        Fragment fragment;
        Op next;
        int popEnterAnim;
        int popExitAnim;
        Op prev;
        ArrayList<Fragment> removed;

        Op() throws  {
        }
    }

    public class TransitionState {
        public EpicenterView enteringEpicenterView = new EpicenterView();
        public ArrayList<View> hiddenFragmentViews = new ArrayList();
        public ArrayMap<String, String> nameOverrides = new ArrayMap();
        public View nonExistentView;
    }

    public void dump(java.lang.String r17, java.io.PrintWriter r18, boolean r19) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:45:0x020b
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r16 = this;
        if (r19 == 0) goto L_0x0152;
    L_0x0002:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "mName=";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r3 = r0.mName;
        r0 = r18;
        r0.print(r3);
        r2 = " mIndex=";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mIndex;
        r0 = r18;
        r0.print(r4);
        r2 = " mCommitted=";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r5 = r0.mCommitted;
        r0 = r18;
        r0.println(r5);
        r0 = r16;
        r4 = r0.mTransition;
        if (r4 == 0) goto L_0x006e;
    L_0x003f:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "mTransition=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mTransition;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.print(r3);
        r2 = " mTransitionStyle=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mTransitionStyle;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.println(r3);
    L_0x006e:
        r0 = r16;
        r4 = r0.mEnterAnim;
        if (r4 != 0) goto L_0x007a;
    L_0x0074:
        r0 = r16;
        r4 = r0.mExitAnim;
        if (r4 == 0) goto L_0x00a9;
    L_0x007a:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "mEnterAnim=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mEnterAnim;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.print(r3);
        r2 = " mExitAnim=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mExitAnim;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.println(r3);
    L_0x00a9:
        r0 = r16;
        r4 = r0.mPopEnterAnim;
        if (r4 != 0) goto L_0x00b5;
    L_0x00af:
        r0 = r16;
        r4 = r0.mPopExitAnim;
        if (r4 == 0) goto L_0x00e4;
    L_0x00b5:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "mPopEnterAnim=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mPopEnterAnim;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.print(r3);
        r2 = " mPopExitAnim=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mPopExitAnim;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.println(r3);
    L_0x00e4:
        r0 = r16;
        r4 = r0.mBreadCrumbTitleRes;
        if (r4 != 0) goto L_0x00f0;
    L_0x00ea:
        r0 = r16;
        r6 = r0.mBreadCrumbTitleText;
        if (r6 == 0) goto L_0x011b;
    L_0x00f0:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "mBreadCrumbTitleRes=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mBreadCrumbTitleRes;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.print(r3);
        r2 = " mBreadCrumbTitleText=";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r6 = r0.mBreadCrumbTitleText;
        r0 = r18;
        r0.println(r6);
    L_0x011b:
        r0 = r16;
        r4 = r0.mBreadCrumbShortTitleRes;
        if (r4 != 0) goto L_0x0127;
    L_0x0121:
        r0 = r16;
        r6 = r0.mBreadCrumbShortTitleText;
        if (r6 == 0) goto L_0x0152;
    L_0x0127:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "mBreadCrumbShortTitleRes=#";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r4 = r0.mBreadCrumbShortTitleRes;
        r3 = java.lang.Integer.toHexString(r4);
        r0 = r18;
        r0.print(r3);
        r2 = " mBreadCrumbShortTitleText=";
        r0 = r18;
        r0.print(r2);
        r0 = r16;
        r6 = r0.mBreadCrumbShortTitleText;
        r0 = r18;
        r0.println(r6);
    L_0x0152:
        r0 = r16;
        r7 = r0.mHead;
        if (r7 == 0) goto L_0x02e9;
    L_0x0158:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "Operations:";
        r0 = r18;
        r0.println(r2);
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r0 = r17;
        r8 = r8.append(r0);
        r2 = "    ";
        r8 = r8.append(r2);
        r3 = r8.toString();
        r0 = r16;
        r7 = r0.mHead;
        r4 = 0;
    L_0x0180:
        if (r7 == 0) goto L_0x02ea;
    L_0x0182:
        r9 = r7.cmd;
        switch(r9) {
            case 0: goto L_0x02a2;
            case 1: goto L_0x02a5;
            case 2: goto L_0x02a8;
            case 3: goto L_0x02ab;
            case 4: goto L_0x02ae;
            case 5: goto L_0x02b1;
            case 6: goto L_0x02b4;
            case 7: goto L_0x02bb;
            default: goto L_0x0187;
        };
    L_0x0187:
        goto L_0x0188;
    L_0x0188:
        r8 = new java.lang.StringBuilder;
        r8.<init>();
        r2 = "cmd=";
        r8 = r8.append(r2);
        r9 = r7.cmd;
        r8 = r8.append(r9);
        r10 = r8.toString();
    L_0x019d:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "  Op #";
        r0 = r18;
        r0.print(r2);
        r0 = r18;
        r0.print(r4);
        r2 = ": ";
        r0 = r18;
        r0.print(r2);
        r0 = r18;
        r0.print(r10);
        r2 = " ";
        r0 = r18;
        r0.print(r2);
        r11 = r7.fragment;
        r0 = r18;
        r0.println(r11);
        if (r19 == 0) goto L_0x025a;
    L_0x01cc:
        r9 = r7.enterAnim;
        if (r9 != 0) goto L_0x01d4;
    L_0x01d0:
        r9 = r7.exitAnim;
        if (r9 == 0) goto L_0x01ff;
    L_0x01d4:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "enterAnim=#";
        r0 = r18;
        r0.print(r2);
        r9 = r7.enterAnim;
        r10 = java.lang.Integer.toHexString(r9);
        r0 = r18;
        r0.print(r10);
        r2 = " exitAnim=#";
        r0 = r18;
        r0.print(r2);
        r9 = r7.exitAnim;
        r10 = java.lang.Integer.toHexString(r9);
        r0 = r18;
        r0.println(r10);
    L_0x01ff:
        r9 = r7.popEnterAnim;
        if (r9 != 0) goto L_0x0223;
    L_0x0203:
        r9 = r7.popExitAnim;
        if (r9 == 0) goto L_0x025a;
    L_0x0207:
        goto L_0x0223;
    L_0x0208:
        goto L_0x019d;
        goto L_0x0223;
    L_0x020c:
        goto L_0x019d;
        goto L_0x0223;
    L_0x0210:
        goto L_0x019d;
        goto L_0x0223;
    L_0x0214:
        goto L_0x019d;
        goto L_0x0223;
    L_0x0218:
        goto L_0x019d;
        goto L_0x0223;
    L_0x021c:
        goto L_0x019d;
        goto L_0x0223;
    L_0x0220:
        goto L_0x019d;
    L_0x0223:
        r0 = r18;
        r1 = r17;
        r0.print(r1);
        r2 = "popEnterAnim=#";
        r0 = r18;
        r0.print(r2);
        r9 = r7.popEnterAnim;
        r10 = java.lang.Integer.toHexString(r9);
        r0 = r18;
        r0.print(r10);
        r2 = " popExitAnim=#";
        r0 = r18;
        r0.print(r2);
        r9 = r7.popExitAnim;
        r10 = java.lang.Integer.toHexString(r9);
        goto L_0x0251;
    L_0x024a:
        goto L_0x0208;
        goto L_0x0251;
    L_0x024e:
        goto L_0x020c;
    L_0x0251:
        r0 = r18;
        r0.println(r10);
        goto L_0x025a;
    L_0x0257:
        goto L_0x0210;
    L_0x025a:
        r12 = r7.removed;
        goto L_0x0260;
    L_0x025d:
        goto L_0x0214;
    L_0x0260:
        if (r12 == 0) goto L_0x02e0;
    L_0x0262:
        goto L_0x0266;
    L_0x0263:
        goto L_0x0218;
    L_0x0266:
        r12 = r7.removed;
        goto L_0x0270;
    L_0x0269:
        goto L_0x021c;
        goto L_0x0270;
    L_0x026d:
        goto L_0x0220;
    L_0x0270:
        r9 = r12.size();
        if (r9 <= 0) goto L_0x02e0;
    L_0x0276:
        r9 = 0;
    L_0x0277:
        r12 = r7.removed;
        r13 = r12.size();
        if (r9 >= r13) goto L_0x02e0;
    L_0x027f:
        r0 = r18;
        r0.print(r3);
        r12 = r7.removed;
        r13 = r12.size();
        r14 = 1;
        if (r13 != r14) goto L_0x02be;
    L_0x028d:
        r2 = "Removed: ";
        r0 = r18;
        r0.print(r2);
    L_0x0294:
        r12 = r7.removed;
        r15 = r12.get(r9);
        r0 = r18;
        r0.println(r15);
        r9 = r9 + 1;
        goto L_0x0277;
    L_0x02a2:
        r10 = "NULL";
        goto L_0x024a;
    L_0x02a5:
        r10 = "ADD";
        goto L_0x024e;
    L_0x02a8:
        r10 = "REPLACE";
        goto L_0x0257;
    L_0x02ab:
        r10 = "REMOVE";
        goto L_0x025d;
    L_0x02ae:
        r10 = "HIDE";
        goto L_0x0263;
    L_0x02b1:
        r10 = "SHOW";
        goto L_0x0269;
    L_0x02b4:
        r10 = "DETACH";
        goto L_0x026d;
        goto L_0x02bb;
    L_0x02b8:
        goto L_0x019d;
    L_0x02bb:
        r10 = "ATTACH";
        goto L_0x02b8;
    L_0x02be:
        if (r9 != 0) goto L_0x02c7;
    L_0x02c0:
        r2 = "Removed:";
        r0 = r18;
        r0.println(r2);
    L_0x02c7:
        r0 = r18;
        r0.print(r3);
        r2 = "  #";
        r0 = r18;
        r0.print(r2);
        r0 = r18;
        r0.print(r9);
        r2 = ": ";
        r0 = r18;
        r0.print(r2);
        goto L_0x0294;
    L_0x02e0:
        r7 = r7.next;
        goto L_0x02e6;
    L_0x02e3:
        goto L_0x0180;
    L_0x02e6:
        r4 = r4 + 1;
        goto L_0x02e3;
    L_0x02e9:
        return;
    L_0x02ea:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.BackStackRecord.dump(java.lang.String, java.io.PrintWriter, boolean):void");
    }

    public android.support.v4.app.BackStackRecord.TransitionState popFromBackStack(@dalvik.annotation.Signature({"(Z", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)", "Landroid/support/v4/app/BackStackRecord$TransitionState;"}) boolean r27, @dalvik.annotation.Signature({"(Z", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)", "Landroid/support/v4/app/BackStackRecord$TransitionState;"}) android.support.v4.app.BackStackRecord.TransitionState r28, @dalvik.annotation.Signature({"(Z", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)", "Landroid/support/v4/app/BackStackRecord$TransitionState;"}) android.util.SparseArray<android.support.v4.app.Fragment> r29, @dalvik.annotation.Signature({"(Z", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)", "Landroid/support/v4/app/BackStackRecord$TransitionState;"}) android.util.SparseArray<android.support.v4.app.Fragment> r30) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:50:0x0140 in {2, 11, 12, 15, 17, 21, 23, 28, 30, 31, 32, 33, 34, 35, 36, 39, 46, 48, 49, 51, 53, 54, 60, 64, 65} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r26 = this;
        r3 = android.support.v4.app.FragmentManagerImpl.DEBUG;
        if (r3 == 0) goto L_0x0033;
    L_0x0004:
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "popFromBackStack: ";
        r4 = r4.append(r5);
        r0 = r26;
        r4 = r4.append(r0);
        r6 = r4.toString();
        r5 = "FragmentManager";
        android.util.Log.v(r5, r6);
        r7 = new android.support.v4.util.LogWriter;
        r5 = "FragmentManager";
        r7.<init>(r5);
        r8 = new java.io.PrintWriter;
        r8.<init>(r7);
        r5 = "  ";
        r9 = 0;
        r10 = 0;
        r0 = r26;
        r0.dump(r5, r9, r8, r10);
    L_0x0033:
        r3 = SUPPORTS_TRANSITIONS;
        if (r3 == 0) goto L_0x005d;
    L_0x0037:
        r0 = r26;
        r11 = r0.mManager;
        r12 = r11.mCurState;
        r13 = 1;
        if (r12 < r13) goto L_0x005d;
    L_0x0040:
        if (r28 != 0) goto L_0x009c;
    L_0x0042:
        r0 = r29;
        r12 = r0.size();
        if (r12 != 0) goto L_0x0052;
    L_0x004a:
        r0 = r30;
        r12 = r0.size();
        if (r12 == 0) goto L_0x005d;
    L_0x0052:
        r13 = 1;
        r0 = r26;
        r1 = r29;
        r2 = r30;
        r28 = r0.beginTransition(r1, r2, r13);
    L_0x005d:
        r13 = -1;
        r0 = r26;
        r0.bumpBackStackNesting(r13);
        if (r28 == 0) goto L_0x00b4;
    L_0x0065:
        r12 = 0;
    L_0x0066:
        if (r28 == 0) goto L_0x00b9;
    L_0x0068:
        r14 = 0;
    L_0x0069:
        r0 = r26;
        r15 = r0.mTail;
    L_0x006d:
        if (r15 == 0) goto L_0x01c5;
    L_0x006f:
        if (r28 == 0) goto L_0x00be;
    L_0x0071:
        r16 = 0;
    L_0x0073:
        if (r28 == 0) goto L_0x00c3;
    L_0x0075:
        r17 = 0;
    L_0x0077:
        r0 = r15.cmd;
        r18 = r0;
        switch(r18) {
            case 1: goto L_0x00c8;
            case 2: goto L_0x00e4;
            case 3: goto L_0x0144;
            case 4: goto L_0x015d;
            case 5: goto L_0x0177;
            case 6: goto L_0x0191;
            case 7: goto L_0x01ab;
            default: goto L_0x007e;
        };
    L_0x007e:
        goto L_0x007f;
    L_0x007f:
        r19 = new java.lang.IllegalArgumentException;
        r4 = new java.lang.StringBuilder;
        r4.<init>();
        r5 = "Unknown cmd: ";
        r4 = r4.append(r5);
        r12 = r15.cmd;
        r4 = r4.append(r12);
        r6 = r4.toString();
        r0 = r19;
        r0.<init>(r6);
        throw r19;
    L_0x009c:
        if (r27 != 0) goto L_0x005d;
    L_0x009e:
        r0 = r26;
        r0 = r0.mSharedElementTargetNames;
        r20 = r0;
        r0 = r26;
        r0 = r0.mSharedElementSourceNames;
        r21 = r0;
        r0 = r28;
        r1 = r20;
        r2 = r21;
        setNameOverrides(r0, r1, r2);
        goto L_0x005d;
    L_0x00b4:
        r0 = r26;
        r12 = r0.mTransitionStyle;
        goto L_0x0066;
    L_0x00b9:
        r0 = r26;
        r14 = r0.mTransition;
        goto L_0x0069;
    L_0x00be:
        r0 = r15.popEnterAnim;
        r16 = r0;
        goto L_0x0073;
    L_0x00c3:
        r0 = r15.popExitAnim;
        r17 = r0;
        goto L_0x0077;
    L_0x00c8:
        r0 = r15.fragment;
        r22 = r0;
        r0 = r17;
        r1 = r22;
        r1.mNextAnim = r0;
        r0 = r26;
        r11 = r0.mManager;
        r16 = android.support.v4.app.FragmentManagerImpl.reverseTransit(r14);
        r0 = r22;
        r1 = r16;
        r11.removeFragment(r0, r1, r12);
    L_0x00e1:
        r15 = r15.prev;
        goto L_0x006d;
    L_0x00e4:
        r0 = r15.fragment;
        r22 = r0;
        if (r22 == 0) goto L_0x00ff;
    L_0x00ea:
        r0 = r17;
        r1 = r22;
        r1.mNextAnim = r0;
        r0 = r26;
        r11 = r0.mManager;
        r17 = android.support.v4.app.FragmentManagerImpl.reverseTransit(r14);
        r0 = r22;
        r1 = r17;
        r11.removeFragment(r0, r1, r12);
    L_0x00ff:
        r0 = r15.removed;
        r20 = r0;
        if (r20 == 0) goto L_0x00e1;
    L_0x0105:
        r17 = 0;
    L_0x0107:
        r0 = r15.removed;
        r20 = r0;
        r18 = r0.size();
        r0 = r17;
        r1 = r18;
        if (r0 >= r1) goto L_0x00e1;
    L_0x0115:
        r0 = r15.removed;
        r20 = r0;
        r1 = r17;
        r23 = r0.get(r1);
        goto L_0x0123;
    L_0x0120:
        goto L_0x00e1;
    L_0x0123:
        r24 = r23;
        r24 = (android.support.v4.app.Fragment) r24;
        r22 = r24;
        r0 = r16;
        r1 = r22;
        r1.mNextAnim = r0;
        r0 = r26;
        r11 = r0.mManager;
        goto L_0x0137;
    L_0x0134:
        goto L_0x00e1;
    L_0x0137:
        r13 = 0;
        r0 = r22;
        r11.addFragment(r0, r13);
        r17 = r17 + 1;
        goto L_0x0107;
        goto L_0x0144;
    L_0x0141:
        goto L_0x00e1;
    L_0x0144:
        r0 = r15.fragment;
        r22 = r0;
        r0 = r16;
        r1 = r22;
        r1.mNextAnim = r0;
        goto L_0x0152;
    L_0x014f:
        goto L_0x00e1;
    L_0x0152:
        r0 = r26;
        r11 = r0.mManager;
        r13 = 0;
        r0 = r22;
        r11.addFragment(r0, r13);
        goto L_0x00e1;
    L_0x015d:
        r0 = r15.fragment;
        r22 = r0;
        r0 = r16;
        r1 = r22;
        r1.mNextAnim = r0;
        r0 = r26;
        r11 = r0.mManager;
        r16 = android.support.v4.app.FragmentManagerImpl.reverseTransit(r14);
        r0 = r22;
        r1 = r16;
        r11.showFragment(r0, r1, r12);
        goto L_0x0120;
    L_0x0177:
        r0 = r15.fragment;
        r22 = r0;
        r0 = r17;
        r1 = r22;
        r1.mNextAnim = r0;
        r0 = r26;
        r11 = r0.mManager;
        r16 = android.support.v4.app.FragmentManagerImpl.reverseTransit(r14);
        r0 = r22;
        r1 = r16;
        r11.hideFragment(r0, r1, r12);
        goto L_0x0134;
    L_0x0191:
        r0 = r15.fragment;
        r22 = r0;
        r0 = r16;
        r1 = r22;
        r1.mNextAnim = r0;
        r0 = r26;
        r11 = r0.mManager;
        r16 = android.support.v4.app.FragmentManagerImpl.reverseTransit(r14);
        r0 = r22;
        r1 = r16;
        r11.attachFragment(r0, r1, r12);
        goto L_0x0141;
    L_0x01ab:
        r0 = r15.fragment;
        r22 = r0;
        r0 = r16;
        r1 = r22;
        r1.mNextAnim = r0;
        r0 = r26;
        r11 = r0.mManager;
        r16 = android.support.v4.app.FragmentManagerImpl.reverseTransit(r14);
        r0 = r22;
        r1 = r16;
        r11.detachFragment(r0, r1, r12);
        goto L_0x014f;
    L_0x01c5:
        if (r27 == 0) goto L_0x01e1;
    L_0x01c7:
        r0 = r26;
        r11 = r0.mManager;
        r0 = r26;
        r0 = r0.mManager;
        r25 = r0;
        r0 = r0.mCurState;
        r16 = r0;
        r14 = android.support.v4.app.FragmentManagerImpl.reverseTransit(r14);
        r13 = 1;
        r0 = r16;
        r11.moveToState(r0, r14, r12, r13);
        r28 = 0;
    L_0x01e1:
        r0 = r26;
        r12 = r0.mIndex;
        if (r12 < 0) goto L_0x01f8;
    L_0x01e7:
        r0 = r26;
        r11 = r0.mManager;
        r0 = r26;
        r12 = r0.mIndex;
        r11.freeBackStackIndex(r12);
        r13 = -1;
        r0 = r26;
        r0.mIndex = r13;
        return r28;
    L_0x01f8:
        return r28;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.BackStackRecord.popFromBackStack(boolean, android.support.v4.app.BackStackRecord$TransitionState, android.util.SparseArray, android.util.SparseArray):android.support.v4.app.BackStackRecord$TransitionState");
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder(128);
        $r1.append("BackStackEntry{");
        $r1.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            $r1.append(" #");
            $r1.append(this.mIndex);
        }
        if (this.mName != null) {
            $r1.append(" ");
            $r1.append(this.mName);
        }
        $r1.append("}");
        return $r1.toString();
    }

    public void dump(String $r1, FileDescriptor fd, PrintWriter $r3, String[] args) throws  {
        dump($r1, $r3, true);
    }

    public BackStackRecord(FragmentManagerImpl $r1) throws  {
        this.mManager = $r1;
    }

    public int getId() throws  {
        return this.mIndex;
    }

    public int getBreadCrumbTitleRes() throws  {
        return this.mBreadCrumbTitleRes;
    }

    public int getBreadCrumbShortTitleRes() throws  {
        return this.mBreadCrumbShortTitleRes;
    }

    public CharSequence getBreadCrumbTitle() throws  {
        if (this.mBreadCrumbTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
        }
        return this.mBreadCrumbTitleText;
    }

    public CharSequence getBreadCrumbShortTitle() throws  {
        if (this.mBreadCrumbShortTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
        }
        return this.mBreadCrumbShortTitleText;
    }

    void addOp(Op $r1) throws  {
        if (this.mHead == null) {
            this.mTail = $r1;
            this.mHead = $r1;
        } else {
            $r1.prev = this.mTail;
            this.mTail.next = $r1;
            this.mTail = $r1;
        }
        $r1.enterAnim = this.mEnterAnim;
        $r1.exitAnim = this.mExitAnim;
        $r1.popEnterAnim = this.mPopEnterAnim;
        $r1.popExitAnim = this.mPopExitAnim;
        this.mNumOp++;
    }

    public FragmentTransaction add(Fragment $r1, String $r2) throws  {
        doAddOp(0, $r1, $r2, 1);
        return this;
    }

    public FragmentTransaction add(int $i0, Fragment $r1) throws  {
        doAddOp($i0, $r1, null, 1);
        return this;
    }

    public FragmentTransaction add(int $i0, Fragment $r1, String $r2) throws  {
        doAddOp($i0, $r1, $r2, 1);
        return this;
    }

    private void doAddOp(int $i0, Fragment $r1, String $r2, int $i1) throws  {
        $r1.mFragmentManager = this.mManager;
        if ($r2 != null) {
            if ($r1.mTag == null || $r2.equals($r1.mTag)) {
                $r1.mTag = $r2;
            } else {
                throw new IllegalStateException("Can't change tag of fragment " + $r1 + ": was " + $r1.mTag + " now " + $r2);
            }
        }
        if ($i0 != 0) {
            if ($r1.mFragmentId == 0 || $r1.mFragmentId == $i0) {
                $r1.mFragmentId = $i0;
                $r1.mContainerId = $i0;
            } else {
                throw new IllegalStateException("Can't change container ID of fragment " + $r1 + ": was " + $r1.mFragmentId + " now " + $i0);
            }
        }
        Op $r3 = new Op();
        $r3.cmd = $i1;
        $r3.fragment = $r1;
        addOp($r3);
    }

    public FragmentTransaction replace(int $i0, Fragment $r1) throws  {
        return replace($i0, $r1, null);
    }

    public FragmentTransaction replace(int $i0, Fragment $r1, String $r2) throws  {
        if ($i0 == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        doAddOp($i0, $r1, $r2, 2);
        return this;
    }

    public FragmentTransaction remove(Fragment $r1) throws  {
        Op $r2 = new Op();
        $r2.cmd = 3;
        $r2.fragment = $r1;
        addOp($r2);
        return this;
    }

    public FragmentTransaction hide(Fragment $r1) throws  {
        Op $r2 = new Op();
        $r2.cmd = 4;
        $r2.fragment = $r1;
        addOp($r2);
        return this;
    }

    public FragmentTransaction show(Fragment $r1) throws  {
        Op $r2 = new Op();
        $r2.cmd = 5;
        $r2.fragment = $r1;
        addOp($r2);
        return this;
    }

    public FragmentTransaction detach(Fragment $r1) throws  {
        Op $r2 = new Op();
        $r2.cmd = 6;
        $r2.fragment = $r1;
        addOp($r2);
        return this;
    }

    public FragmentTransaction attach(Fragment $r1) throws  {
        Op $r2 = new Op();
        $r2.cmd = 7;
        $r2.fragment = $r1;
        addOp($r2);
        return this;
    }

    public FragmentTransaction setCustomAnimations(int $i0, int $i1) throws  {
        return setCustomAnimations($i0, $i1, 0, 0);
    }

    public FragmentTransaction setCustomAnimations(int $i0, int $i1, int $i2, int $i3) throws  {
        this.mEnterAnim = $i0;
        this.mExitAnim = $i1;
        this.mPopEnterAnim = $i2;
        this.mPopExitAnim = $i3;
        return this;
    }

    public FragmentTransaction setTransition(int $i0) throws  {
        this.mTransition = $i0;
        return this;
    }

    public FragmentTransaction addSharedElement(View $r1, String $r2) throws  {
        if (!SUPPORTS_TRANSITIONS) {
            return this;
        }
        String $r3 = FragmentTransitionCompat21.getTransitionName($r1);
        if ($r3 == null) {
            throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
        }
        if (this.mSharedElementSourceNames == null) {
            this.mSharedElementSourceNames = new ArrayList();
            this.mSharedElementTargetNames = new ArrayList();
        }
        this.mSharedElementSourceNames.add($r3);
        this.mSharedElementTargetNames.add($r2);
        return this;
    }

    public FragmentTransaction setTransitionStyle(int $i0) throws  {
        this.mTransitionStyle = $i0;
        return this;
    }

    public FragmentTransaction addToBackStack(String $r1) throws  {
        if (this.mAllowAddToBackStack) {
            this.mAddToBackStack = true;
            this.mName = $r1;
            return this;
        }
        throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
    }

    public boolean isAddToBackStackAllowed() throws  {
        return this.mAllowAddToBackStack;
    }

    public FragmentTransaction disallowAddToBackStack() throws  {
        if (this.mAddToBackStack) {
            throw new IllegalStateException("This transaction is already being added to the back stack");
        }
        this.mAllowAddToBackStack = false;
        return this;
    }

    public FragmentTransaction setBreadCrumbTitle(int $i0) throws  {
        this.mBreadCrumbTitleRes = $i0;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    public FragmentTransaction setBreadCrumbTitle(CharSequence $r1) throws  {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = $r1;
        return this;
    }

    public FragmentTransaction setBreadCrumbShortTitle(int $i0) throws  {
        this.mBreadCrumbShortTitleRes = $i0;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    public FragmentTransaction setBreadCrumbShortTitle(CharSequence $r1) throws  {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = $r1;
        return this;
    }

    void bumpBackStackNesting(int $i0) throws  {
        if (this.mAddToBackStack) {
            if (FragmentManagerImpl.DEBUG) {
                Log.v(TAG, "Bump nesting in " + this + " by " + $i0);
            }
            for (Op $r3 = this.mHead; $r3 != null; $r3 = $r3.next) {
                Fragment $r4;
                if ($r3.fragment != null) {
                    $r4 = $r3.fragment;
                    $r4.mBackStackNesting += $i0;
                    if (FragmentManagerImpl.DEBUG) {
                        Log.v(TAG, "Bump nesting of " + $r3.fragment + " to " + $r3.fragment.mBackStackNesting);
                    }
                }
                if ($r3.removed != null) {
                    for (int $i1 = $r3.removed.size() - 1; $i1 >= 0; $i1--) {
                        $r4 = (Fragment) $r3.removed.get($i1);
                        $r4.mBackStackNesting += $i0;
                        if (FragmentManagerImpl.DEBUG) {
                            Log.v(TAG, "Bump nesting of " + $r4 + " to " + $r4.mBackStackNesting);
                        }
                    }
                }
            }
        }
    }

    public int commit() throws  {
        return commitInternal(false);
    }

    public int commitAllowingStateLoss() throws  {
        return commitInternal(true);
    }

    int commitInternal(boolean $z0) throws  {
        if (this.mCommitted) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManagerImpl.DEBUG) {
            Log.v(TAG, "Commit: " + this);
            dump("  ", null, new PrintWriter(new LogWriter(TAG)), null);
        }
        this.mCommitted = true;
        if (this.mAddToBackStack) {
            this.mIndex = this.mManager.allocBackStackIndex(this);
        } else {
            this.mIndex = -1;
        }
        this.mManager.enqueueAction(this, $z0);
        return this.mIndex;
    }

    public void run() throws  {
        if (FragmentManagerImpl.DEBUG) {
            Log.v(TAG, "Run: " + this);
        }
        if (!this.mAddToBackStack || this.mIndex >= 0) {
            int $i1;
            int $i2;
            bumpBackStackNesting(1);
            TransitionState $r4 = null;
            if (SUPPORTS_TRANSITIONS && this.mManager.mCurState >= 1) {
                SparseArray $r6 = new SparseArray();
                SparseArray $r7 = new SparseArray();
                calculateFragments($r6, $r7);
                $r4 = beginTransition($r6, $r7, false);
            }
            if ($r4 != null) {
                $i1 = 0;
            } else {
                $i1 = this.mTransitionStyle;
            }
            if ($r4 != null) {
                $i2 = 0;
            } else {
                $i2 = this.mTransition;
            }
            for (Op $r8 = this.mHead; $r8 != null; $r8 = $r8.next) {
                int $i3;
                int $i4;
                if ($r4 != null) {
                    $i3 = 0;
                } else {
                    $i3 = $r8.enterAnim;
                }
                if ($r4 != null) {
                    $i4 = 0;
                } else {
                    $i4 = $r8.exitAnim;
                }
                Fragment $r10;
                Fragment $r102;
                switch ($r8.cmd) {
                    case 1:
                        $r10 = $r8.fragment;
                        $r102 = $r10;
                        $r10.mNextAnim = $i3;
                        this.mManager.addFragment($r102, false);
                        break;
                    case 2:
                        int $i0 = $r8.fragment;
                        $r102 = $i0;
                        int $i02 = $i0.mContainerId;
                        if (this.mManager.mAdded != null) {
                            ArrayList $r11 = this.mManager.mAdded;
                            for (int $i5 = $r11.size() - 1; $i5 >= 0; $i5--) {
                                $r11 = this.mManager.mAdded;
                                ArrayList $r112 = $r11;
                                Fragment $r13 = (Fragment) $r11.get($i5);
                                if (FragmentManagerImpl.DEBUG) {
                                    Log.v(TAG, "OP_REPLACE: adding=" + $r102 + " old=" + $r13);
                                }
                                $i0 = $r13.mContainerId;
                                int $i6 = $i0;
                                if ($i0 == $i02) {
                                    if ($r13 == $r102) {
                                        $r102 = null;
                                        $r8.fragment = null;
                                    } else {
                                        if ($r8.removed == null) {
                                            $r8.removed = new ArrayList();
                                        }
                                        $r11 = $r8.removed;
                                        $r112 = $r11;
                                        $r11.add($r13);
                                        $r13.mNextAnim = $i4;
                                        if (this.mAddToBackStack) {
                                            $r13.mBackStackNesting++;
                                            if (FragmentManagerImpl.DEBUG) {
                                                StringBuilder $r1 = new StringBuilder().append("Bump nesting of ").append($r13).append(" to ");
                                                $i0 = $r13.mBackStackNesting;
                                                Log.v(TAG, $r1.append($i0).toString());
                                            }
                                        }
                                        this.mManager.removeFragment($r13, $i2, $i1);
                                    }
                                }
                            }
                        }
                        if ($r102 == null) {
                            break;
                        }
                        $r102.mNextAnim = $i3;
                        this.mManager.addFragment($r102, false);
                        break;
                    case 3:
                        $r102 = $r8.fragment;
                        $r102.mNextAnim = $i4;
                        this.mManager.removeFragment($r102, $i2, $i1);
                        break;
                    case 4:
                        $r102 = $r8.fragment;
                        $r102.mNextAnim = $i4;
                        this.mManager.hideFragment($r102, $i2, $i1);
                        break;
                    case 5:
                        $r10 = $r8.fragment;
                        $r102 = $r10;
                        $r10.mNextAnim = $i3;
                        this.mManager.showFragment($r102, $i2, $i1);
                        break;
                    case 6:
                        $r102 = $r8.fragment;
                        $r102.mNextAnim = $i4;
                        this.mManager.detachFragment($r102, $i2, $i1);
                        break;
                    case 7:
                        $r10 = $r8.fragment;
                        $r102 = $r10;
                        $r10.mNextAnim = $i3;
                        this.mManager.attachFragment($r102, $i2, $i1);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown cmd: " + $r8.cmd);
                }
            }
            FragmentManagerImpl $r5 = this.mManager;
            FragmentManagerImpl $r14 = this.mManager;
            $r5.moveToState($r14.mCurState, $i2, $i1, true);
            if (this.mAddToBackStack) {
                this.mManager.addBackStackState(this);
                return;
            }
            return;
        }
        throw new IllegalStateException("addToBackStack() called after commit()");
    }

    private static void setFirstOut(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/support/v4/app/Fragment;", ")V"}) SparseArray<Fragment> $r0, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/support/v4/app/Fragment;", ")V"}) SparseArray<Fragment> $r1, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/support/v4/app/Fragment;", ")V"}) Fragment $r2) throws  {
        if ($r2 != null) {
            int $i0 = $r2.mContainerId;
            if ($i0 != 0 && !$r2.isHidden()) {
                if ($r2.isAdded() && $r2.getView() != null && $r0.get($i0) == null) {
                    $r0.put($i0, $r2);
                }
                if ($r1.get($i0) == $r2) {
                    $r1.remove($i0);
                }
            }
        }
    }

    private void setLastIn(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/support/v4/app/Fragment;", ")V"}) SparseArray<Fragment> $r1, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/support/v4/app/Fragment;", ")V"}) SparseArray<Fragment> $r2, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/support/v4/app/Fragment;", ")V"}) Fragment $r3) throws  {
        if ($r3 != null) {
            int $i0 = $r3.mContainerId;
            if ($i0 != 0) {
                if (!$r3.isAdded()) {
                    $r2.put($i0, $r3);
                }
                if ($r1.get($i0) == $r3) {
                    $r1.remove($i0);
                }
            }
            if ($r3.mState < 1 && this.mManager.mCurState >= 1) {
                this.mManager.makeActive($r3);
                this.mManager.moveToState($r3, 1, 0, 0, false);
            }
        }
    }

    private void calculateFragments(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) SparseArray<Fragment> $r1, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) SparseArray<Fragment> $r2) throws  {
        if (this.mManager.mContainer.onHasView()) {
            for (Op $r5 = this.mHead; $r5 != null; $r5 = $r5.next) {
                switch ($r5.cmd) {
                    case 1:
                        setLastIn($r1, $r2, $r5.fragment);
                        break;
                    case 2:
                        Fragment $r6 = $r5.fragment;
                        if (this.mManager.mAdded != null) {
                            for (int $i1 = 0; $i1 < this.mManager.mAdded.size(); $i1++) {
                                Fragment $r9 = (Fragment) this.mManager.mAdded.get($i1);
                                if ($r6 == null || $r9.mContainerId == $r6.mContainerId) {
                                    if ($r9 == $r6) {
                                        $r6 = null;
                                        $r2.remove($r9.mContainerId);
                                    } else {
                                        setFirstOut($r1, $r2, $r9);
                                    }
                                }
                            }
                        }
                        setLastIn($r1, $r2, $r5.fragment);
                        break;
                    case 3:
                        setFirstOut($r1, $r2, $r5.fragment);
                        break;
                    case 4:
                        setFirstOut($r1, $r2, $r5.fragment);
                        break;
                    case 5:
                        setLastIn($r1, $r2, $r5.fragment);
                        break;
                    case 6:
                        setFirstOut($r1, $r2, $r5.fragment);
                        break;
                    case 7:
                        setLastIn($r1, $r2, $r5.fragment);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void calculateBackFragments(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) SparseArray<Fragment> $r1, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)V"}) SparseArray<Fragment> $r2) throws  {
        if (this.mManager.mContainer.onHasView()) {
            for (Op $r5 = this.mTail; $r5 != null; $r5 = $r5.prev) {
                switch ($r5.cmd) {
                    case 1:
                        setFirstOut($r1, $r2, $r5.fragment);
                        break;
                    case 2:
                        if ($r5.removed != null) {
                            for (int $i0 = $r5.removed.size() - 1; $i0 >= 0; $i0--) {
                                setLastIn($r1, $r2, (Fragment) $r5.removed.get($i0));
                            }
                        }
                        setFirstOut($r1, $r2, $r5.fragment);
                        break;
                    case 3:
                        setLastIn($r1, $r2, $r5.fragment);
                        break;
                    case 4:
                        setLastIn($r1, $r2, $r5.fragment);
                        break;
                    case 5:
                        setFirstOut($r1, $r2, $r5.fragment);
                        break;
                    case 6:
                        setLastIn($r1, $r2, $r5.fragment);
                        break;
                    case 7:
                        setFirstOut($r1, $r2, $r5.fragment);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public String getName() throws  {
        return this.mName;
    }

    public int getTransition() throws  {
        return this.mTransition;
    }

    public int getTransitionStyle() throws  {
        return this.mTransitionStyle;
    }

    public boolean isEmpty() throws  {
        return this.mNumOp == 0;
    }

    private TransitionState beginTransition(@Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;Z)", "Landroid/support/v4/app/BackStackRecord$TransitionState;"}) SparseArray<Fragment> $r1, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;Z)", "Landroid/support/v4/app/BackStackRecord$TransitionState;"}) SparseArray<Fragment> $r2, @Signature({"(", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;Z)", "Landroid/support/v4/app/BackStackRecord$TransitionState;"}) boolean $z0) throws  {
        int $i0;
        TransitionState $r3 = new TransitionState();
        $r3.nonExistentView = new View(this.mManager.mHost.getContext());
        boolean $z1 = false;
        for ($i0 = 0; $i0 < $r1.size(); $i0++) {
            if (configureTransitions($r1.keyAt($i0), $r3, $z0, $r1, $r2)) {
                $z1 = true;
            }
        }
        for ($i0 = 0; $i0 < $r2.size(); $i0++) {
            int $i1 = $r2.keyAt($i0);
            if ($r1.get($i1) == null && configureTransitions($i1, $r3, $z0, $r1, $r2)) {
                $z1 = true;
            }
        }
        return !$z1 ? null : $r3;
    }

    private static Object getEnterTransition(Fragment $r0, boolean $z0) throws  {
        if ($r0 == null) {
            return null;
        }
        return FragmentTransitionCompat21.cloneTransition($z0 ? $r0.getReenterTransition() : $r0.getEnterTransition());
    }

    private static Object getExitTransition(Fragment $r0, boolean $z0) throws  {
        if ($r0 == null) {
            return null;
        }
        return FragmentTransitionCompat21.cloneTransition($z0 ? $r0.getReturnTransition() : $r0.getExitTransition());
    }

    private static Object getSharedElementTransition(Fragment $r0, Fragment $r1, boolean $z0) throws  {
        if ($r0 == null || $r1 == null) {
            return null;
        }
        return FragmentTransitionCompat21.wrapSharedElementTransition($z0 ? $r1.getSharedElementReturnTransition() : $r0.getSharedElementEnterTransition());
    }

    private static Object captureExitingViews(@Signature({"(", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) Object $r4, @Signature({"(", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) Fragment $r0, @Signature({"(", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) ArrayList<View> $r1, @Signature({"(", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) ArrayMap<String, View> $r2, @Signature({"(", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) View $r3) throws  {
        if ($r4 != null) {
            return FragmentTransitionCompat21.captureExitingViews($r4, $r0.getView(), $r1, $r2, $r3);
        }
        return $r4;
    }

    private ArrayMap<String, View> remapSharedElements(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Z)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) TransitionState $r1, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Z)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) Fragment $r2, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Z)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) boolean $z0) throws  {
        ArrayMap $r3 = new ArrayMap();
        if (this.mSharedElementSourceNames != null) {
            FragmentTransitionCompat21.findNamedViews($r3, $r2.getView());
            if ($z0) {
                $r3.retainAll(this.mSharedElementTargetNames);
            } else {
                $r3 = remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, $r3);
            }
        }
        if ($z0) {
            if ($r2.mEnterTransitionCallback != null) {
                $r2.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, $r3);
            }
            setBackNameOverrides($r1, $r3, false);
            return $r3;
        }
        if ($r2.mExitTransitionCallback != null) {
            $r2.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, $r3);
        }
        setNameOverrides($r1, $r3, false);
        return $r3;
    }

    private boolean configureTransitions(@Signature({"(I", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)Z"}) int $i0, @Signature({"(I", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)Z"}) TransitionState $r1, @Signature({"(I", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)Z"}) boolean $z0, @Signature({"(I", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)Z"}) SparseArray<Fragment> $r2, @Signature({"(I", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;", "Landroid/util/SparseArray", "<", "Landroid/support/v4/app/Fragment;", ">;)Z"}) SparseArray<Fragment> $r3) throws  {
        View $r12 = (ViewGroup) this.mManager.mContainer.onFindViewById($i0);
        if ($r12 == null) {
            return false;
        }
        Fragment fragment = (Fragment) $r3.get($i0);
        Fragment $r15 = (Fragment) $r2.get($i0);
        Object $r13 = getEnterTransition(fragment, $z0);
        Object $r16 = getSharedElementTransition(fragment, $r15, $z0);
        Object $r17 = $r16;
        Object $r18 = getExitTransition($r15, $z0);
        ArrayMap $r19 = null;
        ArrayList arrayList = new ArrayList();
        if ($r16 != null) {
            ArrayMap $r7 = remapSharedElements($r1, $r15, $z0);
            $r19 = $r7;
            if ($r7.isEmpty()) {
                $r17 = null;
                $r19 = null;
            } else {
                SharedElementCallback $r20;
                if ($z0) {
                    $r20 = $r15.mEnterTransitionCallback;
                } else {
                    $r20 = fragment.mEnterTransitionCallback;
                }
                if ($r20 != null) {
                    $r20.onSharedElementStart(new ArrayList($r7.keySet()), new ArrayList($r7.values()), null);
                }
                prepareSharedElementTransition($r1, $r12, $r16, fragment, $r15, $z0, arrayList);
            }
        }
        if ($r13 == null && $r17 == null && $r18 == null) {
            return false;
        }
        arrayList = new ArrayList();
        $r16 = captureExitingViews($r18, $r15, arrayList, $r19, $r1.nonExistentView);
        if (!(this.mSharedElementTargetNames == null || $r19 == null)) {
            View $r11 = (View) $r19.get(this.mSharedElementTargetNames.get(0));
            if ($r11 != null) {
                if ($r16 != null) {
                    FragmentTransitionCompat21.setEpicenter($r16, $r11);
                }
                if ($r17 != null) {
                    FragmentTransitionCompat21.setEpicenter($r17, $r11);
                }
            }
        }
        final Fragment fragment2 = fragment;
        C00121 c00121 = new ViewRetriever() {
            public View getView() throws  {
                return fragment2.getView();
            }
        };
        arrayList = new ArrayList();
        ArrayMap arrayMap = new ArrayMap();
        boolean $z1 = true;
        if (fragment != null) {
            $z1 = $z0 ? fragment.getAllowReturnTransitionOverlap() : fragment.getAllowEnterTransitionOverlap();
        }
        $r18 = FragmentTransitionCompat21.mergeTransitions($r13, $r16, $r17, $z1);
        if ($r18 != null) {
            FragmentTransitionCompat21.addTransitionTargets($r13, $r17, $r12, c00121, $r1.nonExistentView, $r1.enteringEpicenterView, $r1.nameOverrides, arrayList, $r19, arrayMap, arrayList);
            excludeHiddenFragmentsAfterEnter($r12, $r1, $i0, $r18);
            FragmentTransitionCompat21.excludeTarget($r18, $r1.nonExistentView, true);
            excludeHiddenFragments($r1, $i0, $r18);
            FragmentTransitionCompat21.beginDelayedTransition($r12, $r18);
            FragmentTransitionCompat21.cleanupTransitions($r12, $r1.nonExistentView, $r13, arrayList, $r16, arrayList, $r17, arrayList, $r18, $r1.hiddenFragmentViews, arrayMap);
        }
        return $r18 != null;
    }

    private void prepareSharedElementTransition(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/view/View;", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) TransitionState $r1, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/view/View;", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) View $r2, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/view/View;", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Object $r3, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/view/View;", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Fragment $r4, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/view/View;", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Fragment $r5, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/view/View;", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) boolean $z0, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/view/View;", "Ljava/lang/Object;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r6) throws  {
        final View view = $r2;
        final Object obj = $r3;
        final ArrayList<View> arrayList = $r6;
        final TransitionState transitionState = $r1;
        final boolean z = $z0;
        final Fragment fragment = $r4;
        final Fragment fragment2 = $r5;
        $r2.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            public boolean onPreDraw() throws  {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                if (obj != null) {
                    FragmentTransitionCompat21.removeTargets(obj, arrayList);
                    arrayList.clear();
                    ArrayMap $r8 = BackStackRecord.this.mapSharedElementsIn(transitionState, z, fragment);
                    FragmentTransitionCompat21.setSharedElementTargets(obj, transitionState.nonExistentView, $r8, arrayList);
                    BackStackRecord.this.setEpicenterIn($r8, transitionState);
                    BackStackRecord.this.callSharedElementEnd(transitionState, fragment, fragment2, z, $r8);
                }
                return true;
            }
        });
    }

    private void callSharedElementEnd(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) TransitionState state, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Fragment $r2, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Fragment $r3, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) boolean $z0, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Landroid/support/v4/app/Fragment;", "Z", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) ArrayMap<String, View> $r4) throws  {
        SharedElementCallback $r7;
        if ($z0) {
            $r7 = $r3.mEnterTransitionCallback;
        } else {
            $r7 = $r2.mEnterTransitionCallback;
        }
        if ($r7 != null) {
            $r7.onSharedElementEnd(new ArrayList($r4.keySet()), new ArrayList($r4.values()), null);
        }
    }

    private void setEpicenterIn(@Signature({"(", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/support/v4/app/BackStackRecord$TransitionState;", ")V"}) ArrayMap<String, View> $r1, @Signature({"(", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/support/v4/app/BackStackRecord$TransitionState;", ")V"}) TransitionState $r2) throws  {
        if (this.mSharedElementTargetNames != null && !$r1.isEmpty()) {
            View $r5 = (View) $r1.get(this.mSharedElementTargetNames.get(0));
            if ($r5 != null) {
                $r2.enteringEpicenterView.epicenter = $r5;
            }
        }
    }

    private ArrayMap<String, View> mapSharedElementsIn(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/support/v4/app/Fragment;", ")", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) TransitionState $r1, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/support/v4/app/Fragment;", ")", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) boolean $z0, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Z", "Landroid/support/v4/app/Fragment;", ")", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) Fragment $r2) throws  {
        ArrayMap $r3 = mapEnteringSharedElements($r1, $r2, $z0);
        if ($z0) {
            if ($r2.mExitTransitionCallback != null) {
                $r2.mExitTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, $r3);
            }
            setBackNameOverrides($r1, $r3, true);
            return $r3;
        }
        if ($r2.mEnterTransitionCallback != null) {
            $r2.mEnterTransitionCallback.onMapSharedElements(this.mSharedElementTargetNames, $r3);
        }
        setNameOverrides($r1, $r3, true);
        return $r3;
    }

    private static ArrayMap<String, View> remapNames(@Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) ArrayList<String> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) ArrayList<String> $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) ArrayMap<String, View> $r3) throws  {
        if ($r3.isEmpty()) {
            return $r3;
        }
        ArrayMap $r2 = new ArrayMap();
        int $i0 = $r0.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            View $r5 = (View) $r3.get($r0.get($i1));
            if ($r5 != null) {
                $r2.put($r1.get($i1), $r5);
            }
        }
        return $r2;
    }

    private ArrayMap<String, View> mapEnteringSharedElements(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Z)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) TransitionState state, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Z)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) Fragment $r2, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/app/Fragment;", "Z)", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;"}) boolean $z0) throws  {
        ArrayMap $r4 = new ArrayMap();
        View $r5 = $r2.getView();
        if ($r5 == null || this.mSharedElementSourceNames == null) {
            return $r4;
        }
        FragmentTransitionCompat21.findNamedViews($r4, $r5);
        if ($z0) {
            return remapNames(this.mSharedElementSourceNames, this.mSharedElementTargetNames, $r4);
        }
        $r4.retainAll(this.mSharedElementTargetNames);
        return $r4;
    }

    private void excludeHiddenFragmentsAfterEnter(final View $r1, final TransitionState $r2, final int $i0, final Object $r3) throws  {
        $r1.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
            public boolean onPreDraw() throws  {
                $r1.getViewTreeObserver().removeOnPreDrawListener(this);
                BackStackRecord.this.excludeHiddenFragments($r2, $i0, $r3);
                return true;
            }
        });
    }

    private void excludeHiddenFragments(TransitionState $r1, int $i0, Object $r2) throws  {
        if (this.mManager.mAdded != null) {
            for (int $i1 = 0; $i1 < this.mManager.mAdded.size(); $i1++) {
                Fragment $r6 = (Fragment) this.mManager.mAdded.get($i1);
                if (!($r6.mView == null || $r6.mContainer == null || $r6.mContainerId != $i0)) {
                    if (!$r6.mHidden) {
                        FragmentTransitionCompat21.excludeTarget($r2, $r6.mView, false);
                        $r1.hiddenFragmentViews.remove($r6.mView);
                    } else if (!$r1.hiddenFragmentViews.contains($r6.mView)) {
                        FragmentTransitionCompat21.excludeTarget($r2, $r6.mView, true);
                        $r1.hiddenFragmentViews.add($r6.mView);
                    }
                }
            }
        }
    }

    private static void setNameOverride(@Signature({"(", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) ArrayMap<String, String> $r0, @Signature({"(", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r1, @Signature({"(", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")V"}) String $r2) throws  {
        if ($r1 != null && $r2 != null) {
            for (int $i0 = 0; $i0 < $r0.size(); $i0++) {
                if ($r1.equals($r0.valueAt($i0))) {
                    $r0.setValueAt($i0, $r2);
                    return;
                }
            }
            $r0.put($r1, $r2);
        }
    }

    private static void setNameOverrides(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) TransitionState $r0, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r1, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r2) throws  {
        if ($r1 != null) {
            for (int $i0 = 0; $i0 < $r1.size(); $i0++) {
                setNameOverride($r0.nameOverrides, (String) $r1.get($i0), (String) $r2.get($i0));
            }
        }
    }

    private void setBackNameOverrides(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;Z)V"}) TransitionState $r1, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;Z)V"}) ArrayMap<String, View> $r2, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;Z)V"}) boolean $z0) throws  {
        int $i0 = this.mSharedElementTargetNames == null ? 0 : this.mSharedElementTargetNames.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            String $r5 = (String) this.mSharedElementSourceNames.get($i1);
            View $r7 = (View) $r2.get((String) this.mSharedElementTargetNames.get($i1));
            if ($r7 != null) {
                String $r6 = FragmentTransitionCompat21.getTransitionName($r7);
                if ($z0) {
                    setNameOverride($r1.nameOverrides, $r5, $r6);
                } else {
                    setNameOverride($r1.nameOverrides, $r6, $r5);
                }
            }
        }
    }

    private void setNameOverrides(@Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;Z)V"}) TransitionState $r1, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;Z)V"}) ArrayMap<String, View> $r2, @Signature({"(", "Landroid/support/v4/app/BackStackRecord$TransitionState;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;Z)V"}) boolean $z0) throws  {
        int $i0 = $r2.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            String $r4 = (String) $r2.keyAt($i1);
            String $r6 = FragmentTransitionCompat21.getTransitionName((View) $r2.valueAt($i1));
            if ($z0) {
                setNameOverride($r1.nameOverrides, $r4, $r6);
            } else {
                setNameOverride($r1.nameOverrides, $r6, $r4);
            }
        }
    }
}
