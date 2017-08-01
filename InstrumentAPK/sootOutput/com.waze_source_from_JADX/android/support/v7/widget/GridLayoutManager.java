package android.support.v7.widget;

import android.content.Context;
import android.graphics.Rect;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat.CollectionItemInfoCompat;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.MarginLayoutParams;

public class GridLayoutManager extends LinearLayoutManager {
    private static final boolean DEBUG = false;
    public static final int DEFAULT_SPAN_COUNT = -1;
    private static final String TAG = "GridLayoutManager";
    int[] mCachedBorders;
    final Rect mDecorInsets = new Rect();
    boolean mPendingSpanCountChange = false;
    final SparseIntArray mPreLayoutSpanIndexCache = new SparseIntArray();
    final SparseIntArray mPreLayoutSpanSizeCache = new SparseIntArray();
    View[] mSet;
    int mSpanCount = -1;
    SpanSizeLookup mSpanSizeLookup = new DefaultSpanSizeLookup();

    public static abstract class SpanSizeLookup {
        private boolean mCacheSpanIndices = false;
        final SparseIntArray mSpanIndexCache = new SparseIntArray();

        public abstract int getSpanSize(int i) throws ;

        public void setSpanIndexCacheEnabled(boolean $z0) throws  {
            this.mCacheSpanIndices = $z0;
        }

        public void invalidateSpanIndexCache() throws  {
            this.mSpanIndexCache.clear();
        }

        public boolean isSpanIndexCacheEnabled() throws  {
            return this.mCacheSpanIndices;
        }

        int getCachedSpanIndex(int $i0, int $i1) throws  {
            if (!this.mCacheSpanIndices) {
                return getSpanIndex($i0, $i1);
            }
            int $i2 = this.mSpanIndexCache.get($i0, -1);
            if ($i2 != -1) {
                return $i2;
            }
            $i1 = getSpanIndex($i0, $i1);
            this.mSpanIndexCache.put($i0, $i1);
            return $i1;
        }

        public int getSpanIndex(int $i0, int $i1) throws  {
            int $i2 = getSpanSize($i0);
            if ($i2 == $i1) {
                return 0;
            }
            int $i5;
            int $i3 = 0;
            int $i4 = 0;
            if (this.mCacheSpanIndices && this.mSpanIndexCache.size() > 0) {
                $i5 = findReferenceIndexFromCache($i0);
                if ($i5 >= 0) {
                    $i3 = this.mSpanIndexCache.get($i5) + getSpanSize($i5);
                    $i4 = $i5 + 1;
                }
            }
            for ($i5 = $i4; $i5 < $i0; $i5++) {
                $i4 = getSpanSize($i5);
                $i3 += $i4;
                if ($i3 == $i1) {
                    $i3 = 0;
                } else if ($i3 > $i1) {
                    $i3 = $i4;
                }
            }
            return $i3 + $i2 > $i1 ? 0 : $i3;
        }

        int findReferenceIndexFromCache(int $i0) throws  {
            int $i2 = 0;
            int $i3 = this.mSpanIndexCache.size() - 1;
            while ($i2 <= $i3) {
                int $i1 = ($i2 + $i3) >>> 1;
                if (this.mSpanIndexCache.keyAt($i1) < $i0) {
                    $i2 = $i1 + 1;
                } else {
                    $i3 = $i1 - 1;
                }
            }
            $i0 = $i2 - 1;
            if ($i0 < 0 || $i0 >= this.mSpanIndexCache.size()) {
                return -1;
            }
            return this.mSpanIndexCache.keyAt($i0);
        }

        public int getSpanGroupIndex(int $i0, int $i1) throws  {
            int $i2 = 0;
            int $i3 = 0;
            int $i4 = getSpanSize($i0);
            for (int $i5 = 0; $i5 < $i0; $i5++) {
                int $i6 = getSpanSize($i5);
                $i2 += $i6;
                if ($i2 == $i1) {
                    $i2 = 0;
                    $i3++;
                } else if ($i2 > $i1) {
                    $i2 = $i6;
                    $i3++;
                }
            }
            if ($i2 + $i4 > $i1) {
                return $i3 + 1;
            }
            return $i3;
        }
    }

    public static final class DefaultSpanSizeLookup extends SpanSizeLookup {
        public int getSpanSize(int position) throws  {
            return 1;
        }

        public int getSpanIndex(int $i0, int $i1) throws  {
            return $i0 % $i1;
        }
    }

    public static class LayoutParams extends android.support.v7.widget.RecyclerView.LayoutParams {
        public static final int INVALID_SPAN_ID = -1;
        private int mSpanIndex = -1;
        private int mSpanSize = 0;

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
        }

        public LayoutParams(int $i0, int $i1) throws  {
            super($i0, $i1);
        }

        public LayoutParams(MarginLayoutParams $r1) throws  {
            super($r1);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
        }

        public LayoutParams(android.support.v7.widget.RecyclerView.LayoutParams $r1) throws  {
            super($r1);
        }

        public int getSpanIndex() throws  {
            return this.mSpanIndex;
        }

        public int getSpanSize() throws  {
            return this.mSpanSize;
        }
    }

    void layoutChunk(android.support.v7.widget.RecyclerView.Recycler r44, android.support.v7.widget.RecyclerView.State r45, android.support.v7.widget.LinearLayoutManager.LayoutState r46, android.support.v7.widget.LinearLayoutManager.LayoutChunkResult r47) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:65:0x0205
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
        r43 = this;
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r7 = r6.getModeInOther();
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        if (r7 == r8) goto L_0x00f9;
    L_0x000d:
        r9 = 1;
    L_0x000e:
        r0 = r43;
        r10 = r0.getChildCount();
        if (r10 <= 0) goto L_0x00fb;
    L_0x0016:
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r43;
        r10 = r0.mSpanCount;
        r12 = r11[r10];
    L_0x0020:
        if (r9 == 0) goto L_0x0027;
    L_0x0022:
        r0 = r43;
        r0.updateMeasurements();
    L_0x0027:
        r0 = r46;
        r10 = r0.mItemDirection;
        r8 = 1;
        if (r10 != r8) goto L_0x00fd;
    L_0x002e:
        r13 = 1;
    L_0x002f:
        r10 = 0;
        r14 = 0;
        r0 = r43;
        r15 = r0.mSpanCount;
        if (r13 != 0) goto L_0x005a;
    L_0x0037:
        r0 = r46;
        r15 = r0.mCurrentPosition;
        r0 = r43;
        r1 = r44;
        r2 = r45;
        r15 = r0.getSpanIndex(r1, r2, r15);
        r0 = r46;
        r0 = r0.mCurrentPosition;
        r16 = r0;
        r0 = r43;
        r1 = r44;
        r2 = r45;
        r3 = r16;
        r16 = r0.getSpanSize(r1, r2, r3);
        r0 = r16;
        r15 = r15 + r0;
    L_0x005a:
        r0 = r43;
        r0 = r0.mSpanCount;
        r16 = r0;
        if (r10 >= r0) goto L_0x0104;
    L_0x0062:
        r0 = r46;
        r1 = r45;
        r17 = r0.hasMore(r1);
        if (r17 == 0) goto L_0x0104;
    L_0x006c:
        if (r15 <= 0) goto L_0x0104;
    L_0x006e:
        r0 = r46;
        r0 = r0.mCurrentPosition;
        r16 = r0;
        goto L_0x0078;
    L_0x0075:
        goto L_0x000e;
    L_0x0078:
        r0 = r43;
        r1 = r44;
        r2 = r45;
        r3 = r16;
        r18 = r0.getSpanSize(r1, r2, r3);
        goto L_0x0088;
    L_0x0085:
        goto L_0x0020;
    L_0x0088:
        r0 = r43;
        r0 = r0.mSpanCount;
        r19 = r0;
        goto L_0x0092;
    L_0x008f:
        goto L_0x002f;
    L_0x0092:
        r0 = r18;
        r1 = r19;
        if (r0 <= r1) goto L_0x00ff;
    L_0x0098:
        r20 = new java.lang.IllegalArgumentException;
        r21 = new java.lang.StringBuilder;
        r0 = r21;
        r0.<init>();
        r22 = "Item at position ";
        r0 = r21;
        r1 = r22;
        r21 = r0.append(r1);
        r0 = r21;
        r1 = r16;
        r21 = r0.append(r1);
        goto L_0x00b7;
    L_0x00b4:
        goto L_0x0075;
    L_0x00b7:
        r22 = " requires ";
        r0 = r21;
        r1 = r22;
        r21 = r0.append(r1);
        goto L_0x00c5;
    L_0x00c2:
        goto L_0x005a;
    L_0x00c5:
        r0 = r21;
        r1 = r18;
        r21 = r0.append(r1);
        r22 = " spans but GridLayoutManager has only ";
        r0 = r21;
        r1 = r22;
        r21 = r0.append(r1);
        r0 = r43;
        r10 = r0.mSpanCount;
        r0 = r21;
        r21 = r0.append(r10);
        r22 = " spans.";
        r0 = r21;
        r1 = r22;
        r21 = r0.append(r1);
        r0 = r21;
        r23 = r0.toString();
        r0 = r20;
        r1 = r23;
        r0.<init>(r1);
        throw r20;
    L_0x00f9:
        r9 = 0;
        goto L_0x00b4;
    L_0x00fb:
        r12 = 0;
        goto L_0x0085;
    L_0x00fd:
        r13 = 0;
        goto L_0x008f;
    L_0x00ff:
        r0 = r18;
        r15 = r15 - r0;
        if (r15 >= 0) goto L_0x010c;
    L_0x0104:
        if (r10 != 0) goto L_0x0124;
    L_0x0106:
        r8 = 1;
        r0 = r47;
        r0.mFinished = r8;
        return;
    L_0x010c:
        r0 = r46;
        r1 = r44;
        r24 = r0.next(r1);
        if (r24 == 0) goto L_0x0104;
    L_0x0116:
        r0 = r18;
        r14 = r14 + r0;
        r0 = r43;
        r0 = r0.mSet;
        r25 = r0;
        r25[r10] = r24;
        r10 = r10 + 1;
        goto L_0x00c2;
    L_0x0124:
        r15 = 0;
        r26 = 0;
        r0 = r43;
        r1 = r44;
        r2 = r45;
        r3 = r10;
        r4 = r14;
        r5 = r13;
        r0.assignSpans(r1, r2, r3, r4, r5);
        r14 = 0;
    L_0x0134:
        if (r14 >= r10) goto L_0x02a9;
    L_0x0136:
        r0 = r43;
        r0 = r0.mSet;
        r25 = r0;
        r24 = r25[r14];
        r0 = r46;
        r0 = r0.mScrapList;
        r27 = r0;
        if (r27 != 0) goto L_0x025d;
    L_0x0146:
        if (r13 == 0) goto L_0x0254;
    L_0x0148:
        r0 = r43;
        r1 = r24;
        r0.addView(r1);
    L_0x014f:
        r0 = r24;
        r28 = r0.getLayoutParams();
        r30 = r28;
        r30 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r30;
        r29 = r30;
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r16 = r0.mSpanIndex;
        r0 = r29;
        r18 = r0.mSpanSize;
        r0 = r16;
        r1 = r18;
        r0 = r0 + r1;
        r16 = r0;
        r16 = r11[r16];
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r18 = r0.mSpanIndex;
        r18 = r11[r18];
        r0 = r16;
        r1 = r18;
        r0 = r0 - r1;
        r16 = r0;
        r0 = r43;
        r0 = r0.mOrientation;
        r18 = r0;
        if (r18 != 0) goto L_0x0278;
    L_0x018f:
        r0 = r29;
        r0 = r0.height;
        r18 = r0;
    L_0x0195:
        r8 = 0;
        r31 = 0;
        r0 = r16;
        r1 = r18;
        r2 = r31;
        r16 = android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(r0, r7, r8, r1, r2);
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r18 = r6.getTotalSpace();
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r19 = r6.getMode();
        r0 = r43;
        r0 = r0.mOrientation;
        r32 = r0;
        r8 = 1;
        r0 = r32;
        if (r0 != r8) goto L_0x027f;
    L_0x01bd:
        r0 = r29;
        r0 = r0.height;
        r32 = r0;
        goto L_0x01c7;
    L_0x01c4:
        goto L_0x014f;
    L_0x01c7:
        r8 = 0;
        r31 = 1;
        r0 = r18;
        r1 = r19;
        r2 = r32;
        r3 = r31;
        r18 = android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(r0, r1, r8, r2, r3);
        r0 = r43;
        r0 = r0.mOrientation;
        r19 = r0;
        r8 = 1;
        r0 = r19;
        if (r0 != r8) goto L_0x0289;
    L_0x01e1:
        r0 = r29;
        r0 = r0.height;
        r19 = r0;
        r8 = -1;
        r0 = r19;
        if (r0 != r8) goto L_0x0286;
    L_0x01ec:
        r17 = 1;
    L_0x01ee:
        r8 = 0;
        r0 = r43;
        r1 = r24;
        r2 = r16;
        r3 = r18;
        r4 = r17;
        r5 = r8;
        r0.measureChildWithDecorationsAndMargin(r1, r2, r3, r4, r5);
    L_0x01fd:
        r0 = r43;
        r6 = r0.mOrientationHelper;
        goto L_0x0209;
    L_0x0202:
        goto L_0x01c4;
        goto L_0x0209;
    L_0x0206:
        goto L_0x0195;
    L_0x0209:
        r0 = r24;
        r16 = r6.getDecoratedMeasurement(r0);
        r0 = r16;
        if (r0 <= r15) goto L_0x0215;
    L_0x0213:
        r15 = r16;
    L_0x0215:
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r0 = r24;
        r16 = r6.getDecoratedMeasurementInOther(r0);
        r0 = r16;
        r0 = (float) r0;
        r33 = r0;
        goto L_0x0228;
    L_0x0225:
        goto L_0x01c7;
    L_0x0228:
        r34 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r33 = r34 * r33;
        r0 = r29;
        r16 = r0.mSpanSize;
        r0 = r16;
        r0 = (float) r0;
        r35 = r0;
        r0 = r33;
        r1 = r35;
        r0 = r0 / r1;
        r33 = r0;
        goto L_0x0243;
    L_0x0240:
        goto L_0x01ee;
    L_0x0243:
        r36 = (r33 > r26 ? 1 : (r33 == r26 ? 0 : -1));
        if (r36 <= 0) goto L_0x024d;
    L_0x0247:
        r26 = r33;
        goto L_0x024d;
    L_0x024a:
        goto L_0x0134;
    L_0x024d:
        r14 = r14 + 1;
        goto L_0x024a;
        goto L_0x0254;
    L_0x0251:
        goto L_0x01fd;
    L_0x0254:
        r8 = 0;
        r0 = r43;
        r1 = r24;
        r0.addView(r1, r8);
        goto L_0x0202;
    L_0x025d:
        if (r13 == 0) goto L_0x026f;
    L_0x025f:
        goto L_0x0263;
    L_0x0260:
        goto L_0x014f;
    L_0x0263:
        r0 = r43;
        r1 = r24;
        r0.addDisappearingView(r1);
        goto L_0x0260;
        goto L_0x026f;
    L_0x026c:
        goto L_0x014f;
    L_0x026f:
        r8 = 0;
        r0 = r43;
        r1 = r24;
        r0.addDisappearingView(r1, r8);
        goto L_0x026c;
    L_0x0278:
        r0 = r29;
        r0 = r0.width;
        r18 = r0;
        goto L_0x0206;
    L_0x027f:
        r0 = r29;
        r0 = r0.width;
        r32 = r0;
        goto L_0x0225;
    L_0x0286:
        r17 = 0;
        goto L_0x0240;
    L_0x0289:
        r0 = r29;
        r0 = r0.width;
        r19 = r0;
        r8 = -1;
        r0 = r19;
        if (r0 != r8) goto L_0x02a6;
    L_0x0294:
        r17 = 1;
    L_0x0296:
        r8 = 0;
        r0 = r43;
        r1 = r24;
        r2 = r18;
        r3 = r16;
        r4 = r17;
        r5 = r8;
        r0.measureChildWithDecorationsAndMargin(r1, r2, r3, r4, r5);
        goto L_0x0251;
    L_0x02a6:
        r17 = 0;
        goto L_0x0296;
    L_0x02a9:
        if (r9 == 0) goto L_0x037b;
    L_0x02ab:
        r0 = r43;
        r1 = r26;
        r0.guessMeasurement(r1, r12);
        r15 = 0;
        r7 = 0;
    L_0x02b4:
        if (r7 >= r10) goto L_0x037b;
    L_0x02b6:
        r0 = r43;
        r0 = r0.mSet;
        r25 = r0;
        r24 = r25[r7];
        r0 = r24;
        r28 = r0.getLayoutParams();
        r37 = r28;
        r37 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r37;
        r29 = r37;
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r12 = r0.mSpanIndex;
        r0 = r29;
        r14 = r0.mSpanSize;
        r12 = r12 + r14;
        r12 = r11[r12];
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r14 = r0.mSpanIndex;
        r14 = r11[r14];
        r12 = r12 - r14;
        r0 = r43;
        r14 = r0.mOrientation;
        if (r14 != 0) goto L_0x035f;
    L_0x02f0:
        r0 = r29;
        r14 = r0.height;
    L_0x02f4:
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r31 = 0;
        r38 = 0;
        r0 = r31;
        r1 = r38;
        r12 = android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(r12, r8, r0, r14, r1);
        r0 = r43;
        r6 = r0.mOrientationHelper;
        goto L_0x030b;
    L_0x0308:
        goto L_0x02b4;
    L_0x030b:
        r14 = r6.getTotalSpace();
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r16 = r6.getMode();
        r0 = r43;
        r0 = r0.mOrientation;
        r18 = r0;
        r8 = 1;
        r0 = r18;
        if (r0 != r8) goto L_0x0364;
    L_0x0322:
        r0 = r29;
        r0 = r0.height;
        r18 = r0;
    L_0x0328:
        r8 = 0;
        r31 = 1;
        r0 = r16;
        r1 = r18;
        r2 = r31;
        r14 = android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(r14, r0, r8, r1, r2);
        r0 = r43;
        r0 = r0.mOrientation;
        r16 = r0;
        r8 = 1;
        r0 = r16;
        if (r0 != r8) goto L_0x036b;
    L_0x0340:
        r8 = 0;
        r31 = 1;
        r0 = r43;
        r1 = r24;
        r2 = r12;
        r3 = r14;
        r4 = r8;
        r5 = r31;
        r0.measureChildWithDecorationsAndMargin(r1, r2, r3, r4, r5);
    L_0x034f:
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r0 = r24;
        r12 = r6.getDecoratedMeasurement(r0);
        if (r12 <= r15) goto L_0x035c;
    L_0x035b:
        r15 = r12;
    L_0x035c:
        r7 = r7 + 1;
        goto L_0x0308;
    L_0x035f:
        r0 = r29;
        r14 = r0.width;
        goto L_0x02f4;
    L_0x0364:
        r0 = r29;
        r0 = r0.width;
        r18 = r0;
        goto L_0x0328;
    L_0x036b:
        r8 = 0;
        r31 = 1;
        r0 = r43;
        r1 = r24;
        r2 = r14;
        r3 = r12;
        r4 = r8;
        r5 = r31;
        r0.measureChildWithDecorationsAndMargin(r1, r2, r3, r4, r5);
        goto L_0x034f;
    L_0x037b:
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r7 = android.view.View.MeasureSpec.makeMeasureSpec(r15, r8);
        r12 = 0;
    L_0x0383:
        if (r12 >= r10) goto L_0x0420;
    L_0x0385:
        r0 = r43;
        r0 = r0.mSet;
        r25 = r0;
        r24 = r25[r12];
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r0 = r24;
        r14 = r6.getDecoratedMeasurement(r0);
        if (r14 == r15) goto L_0x0406;
    L_0x0399:
        r0 = r24;
        r28 = r0.getLayoutParams();
        r39 = r28;
        r39 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r39;
        r29 = r39;
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r14 = r0.mSpanIndex;
        r0 = r29;
        r16 = r0.mSpanSize;
        r0 = r16;
        r14 = r14 + r0;
        r14 = r11[r14];
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r16 = r0.mSpanIndex;
        goto L_0x03c8;
    L_0x03c5:
        goto L_0x0383;
    L_0x03c8:
        r16 = r11[r16];
        r0 = r16;
        r14 = r14 - r0;
        r0 = r43;
        r0 = r0.mOrientation;
        r16 = r0;
        if (r16 != 0) goto L_0x0409;
    L_0x03d5:
        r0 = r29;
        r0 = r0.height;
        r16 = r0;
    L_0x03db:
        r8 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r31 = 0;
        r38 = 0;
        r0 = r31;
        r1 = r16;
        r2 = r38;
        r14 = android.support.v7.widget.RecyclerView.LayoutManager.getChildMeasureSpec(r14, r8, r0, r1, r2);
        r0 = r43;
        r0 = r0.mOrientation;
        r16 = r0;
        r8 = 1;
        r0 = r16;
        if (r0 != r8) goto L_0x0410;
    L_0x03f7:
        r8 = 1;
        r31 = 1;
        r0 = r43;
        r1 = r24;
        r2 = r14;
        r3 = r7;
        r4 = r8;
        r5 = r31;
        r0.measureChildWithDecorationsAndMargin(r1, r2, r3, r4, r5);
    L_0x0406:
        r12 = r12 + 1;
        goto L_0x03c5;
    L_0x0409:
        r0 = r29;
        r0 = r0.width;
        r16 = r0;
        goto L_0x03db;
    L_0x0410:
        r8 = 1;
        r31 = 1;
        r0 = r43;
        r1 = r24;
        r2 = r7;
        r3 = r14;
        r4 = r8;
        r5 = r31;
        r0.measureChildWithDecorationsAndMargin(r1, r2, r3, r4, r5);
        goto L_0x0406;
    L_0x0420:
        r0 = r47;
        r0.mConsumed = r15;
        r14 = 0;
        r16 = 0;
        r7 = 0;
        r12 = 0;
        r0 = r43;
        r0 = r0.mOrientation;
        r18 = r0;
        r8 = 1;
        r0 = r18;
        if (r0 != r8) goto L_0x050d;
    L_0x0434:
        r0 = r46;
        r7 = r0.mLayoutDirection;
        r8 = -1;
        if (r7 != r8) goto L_0x0502;
    L_0x043b:
        r0 = r46;
        r7 = r0.mOffset;
        r12 = r7;
        r7 = r7 - r15;
    L_0x0441:
        r15 = 0;
    L_0x0442:
        if (r15 >= r10) goto L_0x056c;
    L_0x0444:
        r0 = r43;
        r0 = r0.mSet;
        r25 = r0;
        r24 = r25[r15];
        r0 = r24;
        r28 = r0.getLayoutParams();
        r40 = r28;
        r40 = (android.support.v7.widget.GridLayoutManager.LayoutParams) r40;
        r29 = r40;
        r0 = r43;
        r0 = r0.mOrientation;
        r18 = r0;
        r8 = 1;
        r0 = r18;
        if (r0 != r8) goto L_0x054c;
    L_0x0463:
        r0 = r43;
        r9 = r0.isLayoutRTL();
        if (r9 == 0) goto L_0x052a;
    L_0x046b:
        r0 = r43;
        r14 = r0.getPaddingLeft();
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r16 = r0.mSpanIndex;
        r0 = r29;
        r18 = r0.mSpanSize;
        r0 = r16;
        r1 = r18;
        r0 = r0 + r1;
        r16 = r0;
        r16 = r11[r16];
        r16 = r14 + r16;
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r0 = r24;
        r14 = r6.getDecoratedMeasurementInOther(r0);
        goto L_0x049a;
    L_0x0497:
        goto L_0x0442;
    L_0x049a:
        r14 = r16 - r14;
        goto L_0x04a0;
    L_0x049d:
        goto L_0x0441;
    L_0x04a0:
        r0 = r29;
        r0 = r0.leftMargin;
        r18 = r0;
        r18 = r14 + r18;
        goto L_0x04ac;
    L_0x04a9:
        goto L_0x0441;
    L_0x04ac:
        r0 = r29;
        r0 = r0.topMargin;
        r19 = r0;
        goto L_0x04b6;
    L_0x04b3:
        goto L_0x0441;
    L_0x04b6:
        r19 = r7 + r19;
        r0 = r29;
        r0 = r0.rightMargin;
        r32 = r0;
        r32 = r16 - r32;
        r0 = r29;
        r0 = r0.bottomMargin;
        r41 = r0;
        r41 = r12 - r41;
        r0 = r43;
        r1 = r24;
        r2 = r18;
        r3 = r19;
        r4 = r32;
        r5 = r41;
        r0.layoutDecorated(r1, r2, r3, r4, r5);
        r0 = r29;
        r9 = r0.isItemRemoved();
        if (r9 != 0) goto L_0x04e7;
    L_0x04df:
        r0 = r29;
        r9 = r0.isItemChanged();
        if (r9 == 0) goto L_0x04ec;
    L_0x04e7:
        r8 = 1;
        r0 = r47;
        r0.mIgnoreConsumed = r8;
    L_0x04ec:
        r0 = r47;
        r9 = r0.mFocusable;
        r0 = r24;
        r13 = r0.isFocusable();
        r9 = r9 | r13;
        goto L_0x04fb;
    L_0x04f8:
        goto L_0x04a0;
    L_0x04fb:
        r0 = r47;
        r0.mFocusable = r9;
        r15 = r15 + 1;
        goto L_0x0497;
    L_0x0502:
        r0 = r46;
        r12 = r0.mOffset;
        r7 = r12;
        r12 = r12 + r15;
        goto L_0x050c;
    L_0x0509:
        goto L_0x04a0;
    L_0x050c:
        goto L_0x049d;
    L_0x050d:
        r0 = r46;
        r14 = r0.mLayoutDirection;
        r8 = -1;
        if (r14 != r8) goto L_0x051c;
    L_0x0514:
        r0 = r46;
        r14 = r0.mOffset;
        r16 = r14;
        r14 = r14 - r15;
        goto L_0x04a9;
    L_0x051c:
        r0 = r46;
        r0 = r0.mOffset;
        r16 = r0;
        r14 = r16;
        r0 = r16;
        r0 = r0 + r15;
        r16 = r0;
        goto L_0x04b3;
    L_0x052a:
        r0 = r43;
        r14 = r0.getPaddingLeft();
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r16 = r0.mSpanIndex;
        r16 = r11[r16];
        r0 = r16;
        r14 = r14 + r0;
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r0 = r24;
        r16 = r6.getDecoratedMeasurementInOther(r0);
        r16 = r14 + r16;
        goto L_0x04f8;
    L_0x054c:
        r0 = r43;
        r7 = r0.getPaddingTop();
        r0 = r43;
        r11 = r0.mCachedBorders;
        r0 = r29;
        r12 = r0.mSpanIndex;
        r12 = r11[r12];
        r7 = r7 + r12;
        r0 = r43;
        r6 = r0.mOrientationHelper;
        r0 = r24;
        r12 = r6.getDecoratedMeasurementInOther(r0);
        r12 = r7 + r12;
        goto L_0x0509;
    L_0x056c:
        r0 = r43;
        r0 = r0.mSet;
        r25 = r0;
        r42 = 0;
        r0 = r25;
        r1 = r42;
        java.util.Arrays.fill(r0, r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.GridLayoutManager.layoutChunk(android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State, android.support.v7.widget.LinearLayoutManager$LayoutState, android.support.v7.widget.LinearLayoutManager$LayoutChunkResult):void");
    }

    public GridLayoutManager(Context $r1, AttributeSet $r2, int $i0, int $i1) throws  {
        super($r1, $r2, $i0, $i1);
        setSpanCount(LayoutManager.getProperties($r1, $r2, $i0, $i1).spanCount);
    }

    public GridLayoutManager(Context $r1, int $i0) throws  {
        super($r1);
        setSpanCount($i0);
    }

    public GridLayoutManager(Context $r1, int $i0, int $i1, boolean $z0) throws  {
        super($r1, $i1, $z0);
        setSpanCount($i0);
    }

    public void setStackFromEnd(boolean $z0) throws  {
        if ($z0) {
            throw new UnsupportedOperationException("GridLayoutManager does not support stack from end. Consider using reverse layout");
        }
        super.setStackFromEnd(false);
    }

    public int getRowCountForAccessibility(Recycler $r1, State $r2) throws  {
        if (this.mOrientation == 0) {
            return this.mSpanCount;
        }
        if ($r2.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex($r1, $r2, $r2.getItemCount() - 1) + 1;
    }

    public int getColumnCountForAccessibility(Recycler $r1, State $r2) throws  {
        if (this.mOrientation == 1) {
            return this.mSpanCount;
        }
        if ($r2.getItemCount() < 1) {
            return 0;
        }
        return getSpanGroupIndex($r1, $r2, $r2.getItemCount() - 1) + 1;
    }

    public void onInitializeAccessibilityNodeInfoForItem(Recycler $r1, State $r2, View $r3, AccessibilityNodeInfoCompat $r4) throws  {
        android.view.ViewGroup.LayoutParams $r5 = $r3.getLayoutParams();
        if ($r5 instanceof LayoutParams) {
            LayoutParams $r6 = (LayoutParams) $r5;
            int $i0 = getSpanGroupIndex($r1, $r2, $r6.getViewLayoutPosition());
            int $i1;
            int $i2;
            boolean $z0;
            if (this.mOrientation == 0) {
                $i1 = $r6.getSpanIndex();
                $i2 = $r6.getSpanSize();
                $z0 = this.mSpanCount > 1 && $r6.getSpanSize() == this.mSpanCount;
                $r4.setCollectionItemInfo(CollectionItemInfoCompat.obtain($i1, $i2, $i0, 1, $z0, false));
                return;
            }
            $i1 = $r6.getSpanIndex();
            $i2 = $r6.getSpanSize();
            $z0 = this.mSpanCount > 1 && $r6.getSpanSize() == this.mSpanCount;
            $r4.setCollectionItemInfo(CollectionItemInfoCompat.obtain($i0, 1, $i1, $i2, $z0, false));
            return;
        }
        super.onInitializeAccessibilityNodeInfoForItem($r3, $r4);
    }

    public void onLayoutChildren(Recycler $r1, State $r2) throws  {
        if ($r2.isPreLayout()) {
            cachePreLayoutSpanMapping();
        }
        super.onLayoutChildren($r1, $r2);
        clearPreLayoutSpanMappingCache();
        if (!$r2.isPreLayout()) {
            this.mPendingSpanCountChange = false;
        }
    }

    private void clearPreLayoutSpanMappingCache() throws  {
        this.mPreLayoutSpanSizeCache.clear();
        this.mPreLayoutSpanIndexCache.clear();
    }

    private void cachePreLayoutSpanMapping() throws  {
        int $i0 = getChildCount();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            LayoutParams $r3 = (LayoutParams) getChildAt($i1).getLayoutParams();
            int $i2 = $r3.getViewLayoutPosition();
            this.mPreLayoutSpanSizeCache.put($i2, $r3.getSpanSize());
            this.mPreLayoutSpanIndexCache.put($i2, $r3.getSpanIndex());
        }
    }

    public void onItemsAdded(RecyclerView recyclerView, int positionStart, int itemCount) throws  {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsChanged(RecyclerView recyclerView) throws  {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsRemoved(RecyclerView recyclerView, int positionStart, int itemCount) throws  {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsUpdated(RecyclerView recyclerView, int positionStart, int itemCount, Object payload) throws  {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public void onItemsMoved(RecyclerView recyclerView, int from, int to, int itemCount) throws  {
        this.mSpanSizeLookup.invalidateSpanIndexCache();
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateDefaultLayoutParams() throws  {
        if (this.mOrientation == 0) {
            return new LayoutParams(-2, -1);
        }
        return new LayoutParams(-1, -2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(Context $r1, AttributeSet $r2) throws  {
        return new LayoutParams($r1, $r2);
    }

    public android.support.v7.widget.RecyclerView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        if ($r1 instanceof MarginLayoutParams) {
            return new LayoutParams((MarginLayoutParams) $r1);
        }
        return new LayoutParams($r1);
    }

    public boolean checkLayoutParams(android.support.v7.widget.RecyclerView.LayoutParams $r1) throws  {
        return $r1 instanceof LayoutParams;
    }

    public void setSpanSizeLookup(SpanSizeLookup $r1) throws  {
        this.mSpanSizeLookup = $r1;
    }

    public SpanSizeLookup getSpanSizeLookup() throws  {
        return this.mSpanSizeLookup;
    }

    private void updateMeasurements() throws  {
        int $i0;
        if (getOrientation() == 1) {
            $i0 = (getWidth() - getPaddingRight()) - getPaddingLeft();
        } else {
            $i0 = (getHeight() - getPaddingBottom()) - getPaddingTop();
        }
        calculateItemBorders($i0);
    }

    public void setMeasuredDimension(Rect $r1, int $i0, int $i1) throws  {
        if (this.mCachedBorders == null) {
            super.setMeasuredDimension($r1, $i0, $i1);
        }
        int $i2 = getPaddingLeft() + getPaddingRight();
        int $i3 = getPaddingTop() + getPaddingBottom();
        if (this.mOrientation == 1) {
            $i1 = LayoutManager.chooseSize($i1, $r1.height() + $i3, getMinimumHeight());
            $i0 = LayoutManager.chooseSize($i0, this.mCachedBorders[this.mCachedBorders.length - 1] + $i2, getMinimumWidth());
        } else {
            $i0 = LayoutManager.chooseSize($i0, $r1.width() + $i2, getMinimumWidth());
            $i1 = LayoutManager.chooseSize($i1, this.mCachedBorders[this.mCachedBorders.length - 1] + $i3, getMinimumHeight());
        }
        setMeasuredDimension($i0, $i1);
    }

    private void calculateItemBorders(int $i0) throws  {
        this.mCachedBorders = calculateItemBorders(this.mCachedBorders, this.mSpanCount, $i0);
    }

    static int[] calculateItemBorders(int[] $r0, int $i0, int $i1) throws  {
        if (!($r0 != null && $r0.length == $i0 + 1 && $r0[$r0.length - 1] == $i1)) {
            $r0 = new int[($i0 + 1)];
        }
        $r0[0] = 0;
        int $i2 = $i1 / $i0;
        $i1 %= $i0;
        int $i3 = 0;
        int $i4 = 0;
        for (int $i5 = 1; $i5 <= $i0; $i5++) {
            int $i6 = $i2;
            $i4 += $i1;
            if ($i4 > 0 && $i0 - $i4 < $i1) {
                $i6 = $i2 + 1;
                $i4 -= $i0;
            }
            $i3 += $i6;
            $r0[$i5] = $i3;
        }
        return $r0;
    }

    void onAnchorReady(Recycler $r1, State $r2, AnchorInfo $r3, int $i0) throws  {
        super.onAnchorReady($r1, $r2, $r3, $i0);
        updateMeasurements();
        if ($r2.getItemCount() > 0 && !$r2.isPreLayout()) {
            ensureAnchorIsInCorrectSpan($r1, $r2, $r3, $i0);
        }
        ensureViewSet();
    }

    private void ensureViewSet() throws  {
        if (this.mSet == null || this.mSet.length != this.mSpanCount) {
            this.mSet = new View[this.mSpanCount];
        }
    }

    public int scrollHorizontallyBy(int $i0, Recycler $r1, State $r2) throws  {
        updateMeasurements();
        ensureViewSet();
        return super.scrollHorizontallyBy($i0, $r1, $r2);
    }

    public int scrollVerticallyBy(int $i0, Recycler $r1, State $r2) throws  {
        updateMeasurements();
        ensureViewSet();
        return super.scrollVerticallyBy($i0, $r1, $r2);
    }

    private void ensureAnchorIsInCorrectSpan(Recycler $r1, State $r2, AnchorInfo $r3, int $i0) throws  {
        boolean $z0 = true;
        if ($i0 != 1) {
            $z0 = false;
        }
        int $i1 = getSpanIndex($r1, $r2, $r3.mPosition);
        $i0 = $i1;
        if ($z0) {
            while ($i0 > 0 && $r3.mPosition > 0) {
                $r3.mPosition--;
                $i0 = getSpanIndex($r1, $r2, $r3.mPosition);
            }
            return;
        }
        $i0 = $r2.getItemCount() - 1;
        int $i2 = $r3.mPosition;
        while ($i2 < $i0) {
            int $i3 = getSpanIndex($r1, $r2, $i2 + 1);
            if ($i3 <= $i1) {
                break;
            }
            $i2++;
            $i1 = $i3;
        }
        $r3.mPosition = $i2;
    }

    View findReferenceChild(Recycler $r1, State $r2, int $i0, int $i1, int $i2) throws  {
        byte $b5;
        ensureLayoutState();
        View $r3 = null;
        View $r4 = null;
        int $i3 = this.mOrientationHelper.getStartAfterPadding();
        int $i4 = this.mOrientationHelper.getEndAfterPadding();
        if ($i1 > $i0) {
            $b5 = (byte) 1;
        } else {
            $b5 = (byte) -1;
        }
        while ($i0 != $i1) {
            View $r6 = getChildAt($i0);
            int $i6 = getPosition($r6);
            if ($i6 >= 0 && $i6 < $i2 && getSpanIndex($r1, $r2, $i6) == 0) {
                if (((android.support.v7.widget.RecyclerView.LayoutParams) $r6.getLayoutParams()).isItemRemoved()) {
                    if ($r3 == null) {
                        $r3 = $r6;
                    }
                } else if (this.mOrientationHelper.getDecoratedStart($r6) < $i4 && this.mOrientationHelper.getDecoratedEnd($r6) >= $i3) {
                    return $r6;
                } else {
                    if ($r4 == null) {
                        $r4 = $r6;
                    }
                }
            }
            $i0 += $b5;
        }
        if ($r4 == null) {
            $r4 = $r3;
        }
        return $r4;
    }

    private int getSpanGroupIndex(Recycler $r1, State $r2, int $i0) throws  {
        if (!$r2.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanGroupIndex($i0, this.mSpanCount);
        }
        int $i1 = $r1.convertPreLayoutPositionToPostLayout($i0);
        if ($i1 != -1) {
            return this.mSpanSizeLookup.getSpanGroupIndex($i1, this.mSpanCount);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. " + $i0);
        return 0;
    }

    private int getSpanIndex(Recycler $r1, State $r2, int $i0) throws  {
        if (!$r2.isPreLayout()) {
            return this.mSpanSizeLookup.getCachedSpanIndex($i0, this.mSpanCount);
        }
        int $i1 = this.mPreLayoutSpanIndexCache.get($i0, -1);
        if ($i1 != -1) {
            return $i1;
        }
        $i1 = $r1.convertPreLayoutPositionToPostLayout($i0);
        if ($i1 != -1) {
            return this.mSpanSizeLookup.getCachedSpanIndex($i1, this.mSpanCount);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + $i0);
        return 0;
    }

    private int getSpanSize(Recycler $r1, State $r2, int $i0) throws  {
        if (!$r2.isPreLayout()) {
            return this.mSpanSizeLookup.getSpanSize($i0);
        }
        int $i1 = this.mPreLayoutSpanSizeCache.get($i0, -1);
        if ($i1 != -1) {
            return $i1;
        }
        $i1 = $r1.convertPreLayoutPositionToPostLayout($i0);
        if ($i1 != -1) {
            return this.mSpanSizeLookup.getSpanSize($i1);
        }
        Log.w(TAG, "Cannot find span size for pre layout position. It is not cached, not in the adapter. Pos:" + $i0);
        return 1;
    }

    private void guessMeasurement(float $f0, int $i0) throws  {
        calculateItemBorders(Math.max(Math.round(((float) this.mSpanCount) * $f0), $i0));
    }

    private void measureChildWithDecorationsAndMargin(View $r1, int $i0, int $i1, boolean $z0, boolean $z1) throws  {
        calculateItemDecorationsForChild($r1, this.mDecorInsets);
        android.support.v7.widget.RecyclerView.LayoutParams $r4 = (android.support.v7.widget.RecyclerView.LayoutParams) $r1.getLayoutParams();
        if ($z0 || this.mOrientation == 1) {
            $i0 = updateSpecWithExtra($i0, $r4.leftMargin + this.mDecorInsets.left, $r4.rightMargin + this.mDecorInsets.right);
        }
        if ($z0 || this.mOrientation == 0) {
            $i1 = updateSpecWithExtra($i1, $r4.topMargin + this.mDecorInsets.top, $r4.bottomMargin + this.mDecorInsets.bottom);
        }
        if ($z1) {
            $z0 = shouldReMeasureChild($r1, $i0, $i1, $r4);
        } else {
            $z0 = shouldMeasureChild($r1, $i0, $i1, $r4);
        }
        if ($z0) {
            $r1.measure($i0, $i1);
        }
    }

    private int updateSpecWithExtra(int $i2, int $i0, int $i1) throws  {
        if ($i0 == 0 && $i1 == 0) {
            return $i2;
        }
        int $i3 = MeasureSpec.getMode($i2);
        return ($i3 == Integer.MIN_VALUE || $i3 == 1073741824) ? MeasureSpec.makeMeasureSpec(Math.max(0, (MeasureSpec.getSize($i2) - $i0) - $i1), $i3) : $i2;
    }

    private void assignSpans(Recycler $r1, State $r2, int $i0, int consumedSpanCount, boolean $z0) throws  {
        int $i2;
        byte $b3;
        byte $b4;
        if ($z0) {
            $i2 = 0;
            $b3 = (byte) 1;
        } else {
            $i2 = $i0 - 1;
            $i0 = -1;
            $b3 = (byte) -1;
        }
        if (this.mOrientation == 1 && isLayoutRTL()) {
            consumedSpanCount = this.mSpanCount - 1;
            $b4 = (byte) -1;
        } else {
            consumedSpanCount = 0;
            $b4 = (byte) 1;
        }
        while ($i2 != $i0) {
            View $r3 = this.mSet[$i2];
            LayoutParams $r6 = (LayoutParams) $r3.getLayoutParams();
            $r6.mSpanSize = getSpanSize($r1, $r2, getPosition($r3));
            if ($b4 != (byte) -1 || $r6.mSpanSize <= 1) {
                $r6.mSpanIndex = consumedSpanCount;
            } else {
                $r6.mSpanIndex = consumedSpanCount - ($r6.mSpanSize - 1);
            }
            consumedSpanCount += $r6.mSpanSize * $b4;
            $i2 += $b3;
        }
    }

    public int getSpanCount() throws  {
        return this.mSpanCount;
    }

    public void setSpanCount(int $i0) throws  {
        if ($i0 != this.mSpanCount) {
            this.mPendingSpanCountChange = true;
            if ($i0 < 1) {
                throw new IllegalArgumentException("Span count should be at least 1. Provided " + $i0);
            }
            this.mSpanCount = $i0;
            this.mSpanSizeLookup.invalidateSpanIndexCache();
        }
    }

    public View onFocusSearchFailed(View $r1, int $i0, Recycler $r2, State $r3) throws  {
        View $r4 = findContainingItemView($r1);
        if ($r4 == null) {
            return null;
        }
        LayoutParams $r6 = (LayoutParams) $r4.getLayoutParams();
        int $i4 = $r6.mSpanIndex;
        int $i3 = $r6.mSpanIndex + $r6.mSpanSize;
        if (super.onFocusSearchFailed($r1, $i0, $r2, $r3) == null) {
            return null;
        }
        boolean $z0;
        int $i6;
        int i;
        if (convertFocusDirectionToLayoutDirection($i0) == 1) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if ($z0 != this.mShouldReverseLayout) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        if ($z0) {
            $i6 = getChildCount() - 1;
            i = -1;
            $i0 = -1;
        } else {
            $i6 = 0;
            i = 1;
            $i0 = getChildCount();
        }
        $z0 = this.mOrientation == 1 && isLayoutRTL();
        $r1 = null;
        int $i5 = -1;
        int i2 = 0;
        for ($i6 = 
/*
Method generation error in method: android.support.v7.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):android.view.View
jadx.core.utils.exceptions.CodegenException: Error generate insn: PHI: (r16_1 '$i6' int) = (r16_0 '$i6' int), (r16_4 '$i6' int) binds: {(r16_0 '$i6' int)=B:15:0x004a, (r16_4 '$i6' int)=B:29:0x0082} in method: android.support.v7.widget.GridLayoutManager.onFocusSearchFailed(android.view.View, int, android.support.v7.widget.RecyclerView$Recycler, android.support.v7.widget.RecyclerView$State):android.view.View
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:226)
	at jadx.core.codegen.RegionGen.makeLoop(RegionGen.java:184)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:61)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.RegionGen.makeSimpleRegion(RegionGen.java:87)
	at jadx.core.codegen.RegionGen.makeRegion(RegionGen.java:53)
	at jadx.core.codegen.MethodGen.addInstructions(MethodGen.java:183)
	at jadx.core.codegen.ClassGen.addMethod(ClassGen.java:328)
	at jadx.core.codegen.ClassGen.addMethods(ClassGen.java:265)
	at jadx.core.codegen.ClassGen.addClassBody(ClassGen.java:228)
	at jadx.core.codegen.ClassGen.addClassCode(ClassGen.java:118)
	at jadx.core.codegen.ClassGen.makeClass(ClassGen.java:83)
	at jadx.core.codegen.CodeGen.visit(CodeGen.java:19)
	at jadx.core.ProcessClass.process(ProcessClass.java:43)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.CodegenException: PHI can be used only in fallback mode
	at jadx.core.codegen.InsnGen.fallbackOnlyInsn(InsnGen.java:530)
	at jadx.core.codegen.InsnGen.makeInsnBody(InsnGen.java:514)
	at jadx.core.codegen.InsnGen.makeInsn(InsnGen.java:220)
	... 23 more

*/

        public boolean supportsPredictiveItemAnimations() throws  {
            return this.mPendingSavedState == null && !this.mPendingSpanCountChange;
        }
    }
