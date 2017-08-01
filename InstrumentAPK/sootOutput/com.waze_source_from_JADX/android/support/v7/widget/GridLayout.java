package android.support.v7.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewGroupCompat;
import android.support.v7.gridlayout.C0194R;
import android.util.AttributeSet;
import android.util.LogPrinter;
import android.util.Pair;
import android.util.Printer;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import com.waze.inbox.InboxNativeManager;
import dalvik.annotation.Signature;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GridLayout extends ViewGroup {
    private static final int ALIGNMENT_MODE = C0194R.styleable.GridLayout_alignmentMode;
    public static final int ALIGN_BOUNDS = 0;
    public static final int ALIGN_MARGINS = 1;
    public static final Alignment BASELINE = new C02397();
    public static final Alignment BOTTOM = TRAILING;
    private static final int CAN_STRETCH = 2;
    public static final Alignment CENTER = new C02376();
    private static final int COLUMN_COUNT = C0194R.styleable.GridLayout_columnCount;
    private static final int COLUMN_ORDER_PRESERVED = C0194R.styleable.GridLayout_columnOrderPreserved;
    private static final int DEFAULT_ALIGNMENT_MODE = 1;
    static final int DEFAULT_CONTAINER_MARGIN = 0;
    private static final int DEFAULT_COUNT = Integer.MIN_VALUE;
    private static final boolean DEFAULT_ORDER_PRESERVED = true;
    private static final int DEFAULT_ORIENTATION = 0;
    private static final boolean DEFAULT_USE_DEFAULT_MARGINS = false;
    public static final Alignment END = TRAILING;
    public static final Alignment FILL = new C02408();
    public static final int HORIZONTAL = 0;
    private static final int INFLEXIBLE = 0;
    private static final Alignment LEADING = new C02343();
    public static final Alignment LEFT = createSwitchingAlignment(START, END);
    static final Printer LOG_PRINTER = new LogPrinter(3, GridLayout.class.getName());
    static final int MAX_SIZE = 100000;
    static final Printer NO_PRINTER = new C02321();
    private static final int ORIENTATION = C0194R.styleable.GridLayout_orientation;
    public static final Alignment RIGHT = createSwitchingAlignment(END, START);
    private static final int ROW_COUNT = C0194R.styleable.GridLayout_rowCount;
    private static final int ROW_ORDER_PRESERVED = C0194R.styleable.GridLayout_rowOrderPreserved;
    public static final Alignment START = LEADING;
    public static final Alignment TOP = LEADING;
    private static final Alignment TRAILING = new C02354();
    public static final int UNDEFINED = Integer.MIN_VALUE;
    static final Alignment UNDEFINED_ALIGNMENT = new C02332();
    static final int UNINITIALIZED_HASH = 0;
    private static final int USE_DEFAULT_MARGINS = C0194R.styleable.GridLayout_useDefaultMargins;
    public static final int VERTICAL = 1;
    int mAlignmentMode;
    int mDefaultGap;
    final Axis mHorizontalAxis;
    int mLastLayoutParamsHashCode;
    int mOrientation;
    Printer mPrinter;
    boolean mUseDefaultMargins;
    final Axis mVerticalAxis;

    static class C02321 implements Printer {
        C02321() throws  {
        }

        public void println(String x) throws  {
        }
    }

    public static abstract class Alignment {
        abstract int getAlignmentValue(View view, int i, int i2) throws ;

        abstract String getDebugString() throws ;

        abstract int getGravityOffset(View view, int i) throws ;

        Alignment() throws  {
        }

        int getSizeInCell(View view, int $i0, int cellSize) throws  {
            return $i0;
        }

        Bounds getBounds() throws  {
            return new Bounds();
        }

        public String toString() throws  {
            return "Alignment:" + getDebugString();
        }
    }

    static class C02332 extends Alignment {
        public int getAlignmentValue(View view, int viewSize, int mode) throws  {
            return Integer.MIN_VALUE;
        }

        String getDebugString() throws  {
            return "UNDEFINED";
        }

        int getGravityOffset(View view, int cellDelta) throws  {
            return Integer.MIN_VALUE;
        }

        C02332() throws  {
        }
    }

    static class C02343 extends Alignment {
        public int getAlignmentValue(View view, int viewSize, int mode) throws  {
            return 0;
        }

        String getDebugString() throws  {
            return "LEADING";
        }

        int getGravityOffset(View view, int cellDelta) throws  {
            return 0;
        }

        C02343() throws  {
        }
    }

    static class C02354 extends Alignment {
        String getDebugString() throws  {
            return "TRAILING";
        }

        C02354() throws  {
        }

        int getGravityOffset(View view, int $i0) throws  {
            return $i0;
        }

        public int getAlignmentValue(View view, int $i0, int mode) throws  {
            return $i0;
        }
    }

    static class C02376 extends Alignment {
        String getDebugString() throws  {
            return "CENTER";
        }

        C02376() throws  {
        }

        int getGravityOffset(View view, int $i0) throws  {
            return $i0 >> 1;
        }

        public int getAlignmentValue(View view, int $i0, int mode) throws  {
            return $i0 >> 1;
        }
    }

    static class Bounds {
        public int after;
        public int before;
        public int flexibility;

        private Bounds() throws  {
            reset();
        }

        protected void reset() throws  {
            this.before = Integer.MIN_VALUE;
            this.after = Integer.MIN_VALUE;
            this.flexibility = 2;
        }

        protected void include(int $i0, int $i1) throws  {
            this.before = Math.max(this.before, $i0);
            this.after = Math.max(this.after, $i1);
        }

        protected int size(boolean $z0) throws  {
            return ($z0 || !GridLayout.canStretch(this.flexibility)) ? this.before + this.after : GridLayout.MAX_SIZE;
        }

        protected int getOffset(GridLayout $r1, View $r2, Alignment $r3, int $i0, boolean horizontal) throws  {
            return this.before - $r3.getAlignmentValue($r2, $i0, ViewGroupCompat.getLayoutMode($r1));
        }

        protected final void include(GridLayout $r1, View $r2, Spec $r3, Axis $r4, int $i0) throws  {
            this.flexibility &= $r3.getFlexibility();
            int $i1 = $r3.getAbsoluteAlignment($r4.horizontal).getAlignmentValue($r2, $i0, ViewGroupCompat.getLayoutMode($r1));
            include($i1, $i0 - $i1);
        }

        public String toString() throws  {
            return "Bounds{before=" + this.before + ", after=" + this.after + '}';
        }
    }

    static class C02397 extends Alignment {

        class C02381 extends Bounds {
            private int size;

            C02381() throws  {
                super();
            }

            protected void reset() throws  {
                super.reset();
                this.size = Integer.MIN_VALUE;
            }

            protected void include(int $i0, int $i1) throws  {
                super.include($i0, $i1);
                this.size = Math.max(this.size, $i0 + $i1);
            }

            protected int size(boolean $z0) throws  {
                return Math.max(super.size($z0), this.size);
            }

            protected int getOffset(GridLayout $r1, View $r2, Alignment $r3, int $i0, boolean $z0) throws  {
                return Math.max(0, super.getOffset($r1, $r2, $r3, $i0, $z0));
            }
        }

        String getDebugString() throws  {
            return "BASELINE";
        }

        int getGravityOffset(View view, int cellDelta) throws  {
            return 0;
        }

        C02397() throws  {
        }

        public int getAlignmentValue(View $r1, int viewSize, int mode) throws  {
            if ($r1.getVisibility() == 8) {
                return 0;
            }
            viewSize = $r1.getBaseline();
            return viewSize == -1 ? Integer.MIN_VALUE : viewSize;
        }

        public Bounds getBounds() throws  {
            return new C02381();
        }
    }

    static class C02408 extends Alignment {
        public int getAlignmentValue(View view, int viewSize, int mode) throws  {
            return Integer.MIN_VALUE;
        }

        String getDebugString() throws  {
            return "FILL";
        }

        int getGravityOffset(View view, int cellDelta) throws  {
            return 0;
        }

        C02408() throws  {
        }

        public int getSizeInCell(View view, int viewSize, int $i1) throws  {
            return $i1;
        }
    }

    static final class Arc {
        public final Interval span;
        public boolean valid = true;
        public final MutableInt value;

        public Arc(Interval $r1, MutableInt $r2) throws  {
            this.span = $r1;
            this.value = $r2;
        }

        public String toString() throws  {
            return this.span + " " + (!this.valid ? "+>" : "->") + " " + this.value;
        }
    }

    static final class Assoc<K, V> extends ArrayList<Pair<K, V>> {
        private final Class<K> keyType;
        private final Class<V> valueType;

        private Assoc(@Signature({"(", "Ljava/lang/Class", "<TK;>;", "Ljava/lang/Class", "<TV;>;)V"}) Class<K> $r1, @Signature({"(", "Ljava/lang/Class", "<TK;>;", "Ljava/lang/Class", "<TV;>;)V"}) Class<V> $r2) throws  {
            this.keyType = $r1;
            this.valueType = $r2;
        }

        public static <K, V> Assoc<K, V> of(@Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TK;>;", "Ljava/lang/Class", "<TV;>;)", "Landroid/support/v7/widget/GridLayout$Assoc", "<TK;TV;>;"}) Class<K> $r0, @Signature({"<K:", "Ljava/lang/Object;", "V:", "Ljava/lang/Object;", ">(", "Ljava/lang/Class", "<TK;>;", "Ljava/lang/Class", "<TV;>;)", "Landroid/support/v7/widget/GridLayout$Assoc", "<TK;TV;>;"}) Class<V> $r1) throws  {
            return new Assoc($r0, $r1);
        }

        public void put(@Signature({"(TK;TV;)V"}) K $r1, @Signature({"(TK;TV;)V"}) V $r2) throws  {
            add(Pair.create($r1, $r2));
        }

        public PackedMap<K, V> pack() throws  {
            int $i0 = size();
            Object[] $r3 = (Object[]) Array.newInstance(this.keyType, $i0);
            Object[] $r4 = (Object[]) Array.newInstance(this.valueType, $i0);
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r3[$i1] = ((Pair) get($i1)).first;
                $r4[$i1] = ((Pair) get($i1)).second;
            }
            return new PackedMap($r3, $r4);
        }
    }

    final class Axis {
        static final /* synthetic */ boolean $assertionsDisabled = (!GridLayout.class.desiredAssertionStatus());
        private static final int COMPLETE = 2;
        private static final int NEW = 0;
        private static final int PENDING = 1;
        public Arc[] arcs;
        public boolean arcsValid;
        PackedMap<Interval, MutableInt> backwardLinks;
        public boolean backwardLinksValid;
        public int definedCount;
        public int[] deltas;
        PackedMap<Interval, MutableInt> forwardLinks;
        public boolean forwardLinksValid;
        PackedMap<Spec, Bounds> groupBounds;
        public boolean groupBoundsValid;
        public boolean hasWeights;
        public boolean hasWeightsValid;
        public final boolean horizontal;
        public int[] leadingMargins;
        public boolean leadingMarginsValid;
        public int[] locations;
        public boolean locationsValid;
        private int maxIndex;
        boolean orderPreserved;
        private MutableInt parentMax;
        private MutableInt parentMin;
        public int[] trailingMargins;
        public boolean trailingMarginsValid;

        private Axis(boolean $z0) throws  {
            this.definedCount = Integer.MIN_VALUE;
            this.maxIndex = Integer.MIN_VALUE;
            this.groupBoundsValid = false;
            this.forwardLinksValid = false;
            this.backwardLinksValid = false;
            this.leadingMarginsValid = false;
            this.trailingMarginsValid = false;
            this.arcsValid = false;
            this.locationsValid = false;
            this.hasWeightsValid = false;
            this.orderPreserved = true;
            this.parentMin = new MutableInt(0);
            this.parentMax = new MutableInt(-100000);
            this.horizontal = $z0;
        }

        private int calculateMaxIndex() throws  {
            int $i0 = -1;
            int $i2 = GridLayout.this.getChildCount();
            for (int $i1 = 0; $i1 < $i2; $i1++) {
                LayoutParams $r4 = GridLayout.this.getLayoutParams(GridLayout.this.getChildAt($i1));
                Interval $r1 = (this.horizontal ? $r4.columnSpec : $r4.rowSpec).span;
                $i0 = Math.max(Math.max(Math.max($i0, $r1.min), $r1.max), $r1.size());
            }
            return $i0 == -1 ? Integer.MIN_VALUE : $i0;
        }

        private int getMaxIndex() throws  {
            if (this.maxIndex == Integer.MIN_VALUE) {
                this.maxIndex = Math.max(0, calculateMaxIndex());
            }
            return this.maxIndex;
        }

        public int getCount() throws  {
            return Math.max(this.definedCount, getMaxIndex());
        }

        public void setCount(int $i0) throws  {
            if ($i0 != Integer.MIN_VALUE && $i0 < getMaxIndex()) {
                GridLayout.handleInvalidParams((this.horizontal ? "column" : "row") + "Count must be greater than or equal to the maximum of all grid indices " + "(and spans) defined in the LayoutParams of each child");
            }
            this.definedCount = $i0;
        }

        public boolean isOrderPreserved() throws  {
            return this.orderPreserved;
        }

        public void setOrderPreserved(boolean $z0) throws  {
            this.orderPreserved = $z0;
            invalidateStructure();
        }

        private PackedMap<Spec, Bounds> createGroupBounds() throws  {
            Assoc $r1 = Assoc.of(Spec.class, Bounds.class);
            int $i1 = GridLayout.this.getChildCount();
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                LayoutParams $r4 = GridLayout.this.getLayoutParams(GridLayout.this.getChildAt($i0));
                Spec $r5 = this.horizontal ? $r4.columnSpec : $r4.rowSpec;
                $r1.put($r5, $r5.getAbsoluteAlignment(this.horizontal).getBounds());
            }
            return $r1.pack();
        }

        private void computeGroupBounds() throws  {
            Bounds[] $r3 = (Bounds[]) this.groupBounds.values;
            for (Bounds $r4 : $r3) {
                $r4.reset();
            }
            int $i1 = 0;
            int $i2 = GridLayout.this.getChildCount();
            while ($i1 < $i2) {
                View $r6 = GridLayout.this.getChildAt($i1);
                LayoutParams $r7 = GridLayout.this.getLayoutParams($r6);
                Spec $r8 = this.horizontal ? $r7.columnSpec : $r7.rowSpec;
                GridLayout $r5 = GridLayout.this;
                boolean $z0 = this.horizontal;
                ((Bounds) this.groupBounds.getValue($i1)).include(GridLayout.this, $r6, $r8, this, $r5.getMeasurementIncludingMargin($r6, $z0) + ($r8.weight == 0.0f ? 0 : getDeltas()[$i1]));
                $i1++;
            }
        }

        public PackedMap<Spec, Bounds> getGroupBounds() throws  {
            if (this.groupBounds == null) {
                this.groupBounds = createGroupBounds();
            }
            if (!this.groupBoundsValid) {
                computeGroupBounds();
                this.groupBoundsValid = true;
            }
            return this.groupBounds;
        }

        private PackedMap<Interval, MutableInt> createLinks(@Signature({"(Z)", "Landroid/support/v7/widget/GridLayout$PackedMap", "<", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ">;"}) boolean $z0) throws  {
            Assoc $r1 = Assoc.of(Interval.class, MutableInt.class);
            Spec[] $r4 = (Spec[]) getGroupBounds().keys;
            int $i0 = $r4.length;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                Interval $r6;
                if ($z0) {
                    $r6 = $r4[$i1].span;
                } else {
                    $r6 = $r4[$i1].span.inverse();
                }
                $r1.put($r6, new MutableInt());
            }
            return $r1.pack();
        }

        private void computeLinks(@Signature({"(", "Landroid/support/v7/widget/GridLayout$PackedMap", "<", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ">;Z)V"}) PackedMap<Interval, MutableInt> $r1, @Signature({"(", "Landroid/support/v7/widget/GridLayout$PackedMap", "<", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ">;Z)V"}) boolean $z0) throws  {
            int $i0;
            Object[] $r2 = $r1;
            PackedMap $r12 = $r2;
            MutableInt[] $r3 = (MutableInt[]) $r2.values;
            for (MutableInt $r4 : $r3) {
                MutableInt $r42;
                $r42.reset();
            }
            Bounds[] $r6 = (Bounds[]) getGroupBounds().values;
            for ($i0 = 0; $i0 < $r6.length; $i0++) {
                int $i1 = $r6[$i0].size($z0);
                int $i2 = $i1;
                $r42 = (MutableInt) $r12.getValue($i0);
                int $i3 = $r42.value;
                if (!$z0) {
                    $i2 = -$i1;
                }
                $r42.value = Math.max($i3, $i2);
            }
        }

        private PackedMap<Interval, MutableInt> getForwardLinks() throws  {
            if (this.forwardLinks == null) {
                this.forwardLinks = createLinks(true);
            }
            if (!this.forwardLinksValid) {
                computeLinks(this.forwardLinks, true);
                this.forwardLinksValid = true;
            }
            return this.forwardLinks;
        }

        private PackedMap<Interval, MutableInt> getBackwardLinks() throws  {
            if (this.backwardLinks == null) {
                this.backwardLinks = createLinks(false);
            }
            if (!this.backwardLinksValid) {
                computeLinks(this.backwardLinks, false);
                this.backwardLinksValid = true;
            }
            return this.backwardLinks;
        }

        private void include(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", "Z)V"}) List<Arc> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", "Z)V"}) Interval $r2, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", "Z)V"}) MutableInt $r3, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", "Z)V"}) boolean $z0) throws  {
            if ($r2.size() != 0) {
                if ($z0) {
                    for (Arc arc : $r1) {
                        if (arc.span.equals($r2)) {
                            return;
                        }
                    }
                }
                $r1.add(new Arc($r2, $r3));
            }
        }

        private void include(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ")V"}) List<Arc> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ")V"}) Interval $r2, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ")V"}) MutableInt $r3) throws  {
            include($r1, $r2, $r3, true);
        }

        Arc[][] groupArcsByFirstVertex(Arc[] $r1) throws  {
            int $i0 = getCount() + 1;
            Arc[][] $r2 = new Arc[$i0][];
            int[] $r3 = new int[$i0];
            for (Arc $r4 : $r1) {
                int $i2 = $r4.span.min;
                $r3[$i2] = $r3[$i2] + 1;
            }
            for ($i0 = 0; $i0 < $r3.length; $i0++) {
                $r2[$i0] = new Arc[$r3[$i0]];
            }
            Arrays.fill($r3, 0);
            for (Arc $r42 : $r1) {
                int $i3 = $r42.span.min;
                Arc[] $r6 = $r2[$i3];
                int $i4 = $r3[$i3];
                $r3[$i3] = $i4 + 1;
                $r6[$i4] = $r42;
            }
            return $r2;
        }

        private Arc[] topologicalSort(final Arc[] $r1) throws  {
            return new Object() {
                static final /* synthetic */ boolean $assertionsDisabled = (!GridLayout.class.desiredAssertionStatus());
                Arc[][] arcsByVertex = Axis.this.groupArcsByFirstVertex($r1);
                int cursor = (this.result.length - 1);
                Arc[] result = new Arc[$r1.length];
                int[] visited = new int[(Axis.this.getCount() + 1)];

                void walk(int $i0) throws  {
                    switch (this.visited[$i0]) {
                        case 0:
                            this.visited[$i0] = 1;
                            for (Arc $r1 : this.arcsByVertex[$i0]) {
                                walk($r1.span.max);
                                Arc[] $r6 = this.result;
                                int $i4 = this.cursor;
                                this.cursor = $i4 - 1;
                                $r6[$i4] = $r1;
                            }
                            this.visited[$i0] = 2;
                            return;
                        case 1:
                            if (!$assertionsDisabled) {
                                throw new AssertionError();
                            }
                            return;
                        default:
                            return;
                    }
                }

                Arc[] sort() throws  {
                    int $i0 = this.arcsByVertex.length;
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        walk($i1);
                    }
                    if ($assertionsDisabled || this.cursor == -1) {
                        return this.result;
                    }
                    throw new AssertionError();
                }
            }.sort();
        }

        private Arc[] topologicalSort(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;)[", "Landroid/support/v7/widget/GridLayout$Arc;"}) List<Arc> $r1) throws  {
            return topologicalSort((Arc[]) $r1.toArray(new Arc[$r1.size()]));
        }

        private void addComponentSizes(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$PackedMap", "<", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ">;)V"}) List<Arc> $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;", "Landroid/support/v7/widget/GridLayout$PackedMap", "<", "Landroid/support/v7/widget/GridLayout$Interval;", "Landroid/support/v7/widget/GridLayout$MutableInt;", ">;)V"}) PackedMap<Interval, MutableInt> $r2) throws  {
            for (int $i0 = 0; $i0 < ((Interval[]) $r2.keys).length; $i0++) {
                include($r1, ((Interval[]) $r2.keys)[$i0], ((MutableInt[]) $r2.values)[$i0], false);
            }
        }

        private Arc[] createArcs() throws  {
            int $i0;
            List $r2 = new ArrayList();
            List $r1 = new ArrayList();
            addComponentSizes($r2, getForwardLinks());
            addComponentSizes($r1, getBackwardLinks());
            if (this.orderPreserved) {
                for ($i0 = 0; $i0 < getCount(); $i0++) {
                    include($r2, new Interval($i0, $i0 + 1), new MutableInt(0));
                }
            }
            $i0 = getCount();
            include($r2, new Interval(0, $i0), this.parentMin, false);
            include($r1, new Interval($i0, 0), this.parentMax, false);
            return (Arc[]) GridLayout.append(topologicalSort($r2), topologicalSort($r1));
        }

        private void computeArcs() throws  {
            getForwardLinks();
            getBackwardLinks();
        }

        public Arc[] getArcs() throws  {
            if (this.arcs == null) {
                this.arcs = createArcs();
            }
            if (!this.arcsValid) {
                computeArcs();
                this.arcsValid = true;
            }
            return this.arcs;
        }

        private boolean relax(int[] $r1, Arc $r2) throws  {
            if (!$r2.valid) {
                return false;
            }
            Interval $r3 = $r2.span;
            int $i1 = $r3.min;
            int $i2 = $r3.max;
            int $i0 = $r1[$i1] + $r2.value.value;
            if ($i0 <= $r1[$i2]) {
                return false;
            }
            $r1[$i2] = $i0;
            return true;
        }

        private void init(int[] $r1) throws  {
            Arrays.fill($r1, 0);
        }

        private String arcsToString(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v7/widget/GridLayout$Arc;", ">;)", "Ljava/lang/String;"}) List<Arc> $r1) throws  {
            boolean $z0 = this.horizontal;
            this = this;
            String $r2 = $z0 ? "x" : "y";
            StringBuilder $r3 = new StringBuilder();
            $z0 = true;
            for (Arc $r6 : $r1) {
                if ($z0) {
                    $z0 = false;
                } else {
                    $r3 = $r3.append(", ");
                }
                int $i1 = $r6.span.min;
                int $i0 = $r6.span.max;
                int $i2 = $r6.value.value;
                $r3.append($i1 < $i0 ? $r2 + $i0 + "-" + $r2 + $i1 + ">=" + $i2 : $r2 + $i1 + "-" + $r2 + $i0 + "<=" + (-$i2));
            }
            return $r3.toString();
        }

        private void logError(String $r1, Arc[] $r2, boolean[] $r3) throws  {
            ArrayList $r5 = new ArrayList();
            ArrayList $r6 = new ArrayList();
            for (int $i0 = 0; $i0 < $r2.length; $i0++) {
                Arc $r4 = $r2[$i0];
                if ($r3[$i0]) {
                    $r5.add($r4);
                }
                if (!$r4.valid) {
                    $r6.add($r4);
                }
            }
            GridLayout.this.mPrinter.println($r1 + " constraints: " + arcsToString($r5) + " are inconsistent; permanently removing: " + arcsToString($r6) + ". ");
        }

        private boolean solve(Arc[] $r1, int[] $r2) throws  {
            return solve($r1, $r2, true);
        }

        private boolean solve(Arc[] $r1, int[] $r2, boolean $z0) throws  {
            String $r5 = this.horizontal ? "horizontal" : "vertical";
            int $i0 = getCount() + 1;
            boolean[] $r6 = null;
            for (int $i1 = 0; $i1 < $r1.length; $i1++) {
                Arc $r3;
                init($r2);
                int $i2 = 0;
                while ($i2 < $i0) {
                    int $i3;
                    boolean $z1 = false;
                    for (Arc $r32 : $r1) {
                        $z1 |= relax($r2, $r32);
                    }
                    if ($z1) {
                        $i2++;
                    } else {
                        if ($r6 != null) {
                            logError($r5, $r1, $r6);
                        }
                        return true;
                    }
                }
                if (!$z0) {
                    return false;
                }
                boolean[] $r4 = new boolean[$r1.length];
                for ($i2 = 0; $i2 < $i0; $i2++) {
                    int $i4 = $r1.length;
                    for ($i3 = 0; $i3 < $i4; $i3++) {
                        $r4[$i3] = $r4[$i3] | relax($r2, $r1[$i3]);
                    }
                }
                if ($i1 == 0) {
                    $r6 = $r4;
                }
                for ($i2 = 0; $i2 < $r1.length; $i2++) {
                    if ($r4[$i2]) {
                        $r32 = $r1[$i2];
                        if ($r32.span.min >= $r32.span.max) {
                            $r32.valid = false;
                            break;
                        }
                    }
                }
            }
            return true;
        }

        private void computeMargins(boolean $z0) throws  {
            int[] $r2;
            if ($z0) {
                $r2 = this.leadingMargins;
            } else {
                $r2 = this.trailingMargins;
            }
            int $i1 = GridLayout.this.getChildCount();
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                View $r4 = GridLayout.this.getChildAt($i0);
                if ($r4.getVisibility() != 8) {
                    int $i2;
                    LayoutParams $r5 = GridLayout.this.getLayoutParams($r4);
                    Interval $r1 = (this.horizontal ? $r5.columnSpec : $r5.rowSpec).span;
                    if ($z0) {
                        $i2 = $r1.min;
                    } else {
                        $i2 = $r1.max;
                    }
                    $r2[$i2] = Math.max($r2[$i2], GridLayout.this.getMargin1($r4, this.horizontal, $z0));
                }
            }
        }

        public int[] getLeadingMargins() throws  {
            if (this.leadingMargins == null) {
                this.leadingMargins = new int[(getCount() + 1)];
            }
            if (!this.leadingMarginsValid) {
                computeMargins(true);
                this.leadingMarginsValid = true;
            }
            return this.leadingMargins;
        }

        public int[] getTrailingMargins() throws  {
            if (this.trailingMargins == null) {
                this.trailingMargins = new int[(getCount() + 1)];
            }
            if (!this.trailingMarginsValid) {
                computeMargins(false);
                this.trailingMarginsValid = true;
            }
            return this.trailingMargins;
        }

        private boolean solve(int[] $r1) throws  {
            return solve(getArcs(), $r1);
        }

        private boolean computeHasWeights() throws  {
            int $i1 = GridLayout.this.getChildCount();
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                View $r2 = GridLayout.this.getChildAt($i0);
                if ($r2.getVisibility() != 8) {
                    LayoutParams $r3 = GridLayout.this.getLayoutParams($r2);
                    if ((this.horizontal ? $r3.columnSpec : $r3.rowSpec).weight != 0.0f) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean hasWeights() throws  {
            if (!this.hasWeightsValid) {
                this.hasWeights = computeHasWeights();
                this.hasWeightsValid = true;
            }
            return this.hasWeights;
        }

        public int[] getDeltas() throws  {
            if (this.deltas == null) {
                this.deltas = new int[GridLayout.this.getChildCount()];
            }
            return this.deltas;
        }

        private void shareOutDelta(int $i0, float $f1) throws  {
            Arrays.fill(this.deltas, 0);
            int $i2 = GridLayout.this.getChildCount();
            for (int $i1 = 0; $i1 < $i2; $i1++) {
                View $r3 = GridLayout.this.getChildAt($i1);
                if ($r3.getVisibility() != 8) {
                    LayoutParams $r4 = GridLayout.this.getLayoutParams($r3);
                    float $f0 = (this.horizontal ? $r4.columnSpec : $r4.rowSpec).weight;
                    if ($f0 != 0.0f) {
                        int $i3 = Math.round((((float) $i0) * $f0) / $f1);
                        this.deltas[$i1] = $i3;
                        $i0 -= $i3;
                        $f1 -= $f0;
                    }
                }
            }
        }

        private void solveAndDistributeSpace(int[] $r1) throws  {
            Arrays.fill(getDeltas(), 0);
            solve($r1);
            int $i1 = (this.parentMin.value * GridLayout.this.getChildCount()) + 1;
            if ($i1 >= 2) {
                int $i2 = 0;
                float $f0 = calculateTotalWeight();
                int $i3 = -1;
                boolean $z0 = true;
                while ($i2 < $i1) {
                    long $l5 = (long) $i1;
                    int $i0 = (int) ((((long) $i2) + $l5) / 2);
                    invalidateValues();
                    shareOutDelta($i0, $f0);
                    boolean $z1 = solve(getArcs(), $r1, false);
                    $z0 = $z1;
                    if ($z1) {
                        $i3 = $i0;
                        $i2 = $i0 + 1;
                    } else {
                        $i1 = $i0;
                    }
                }
                if ($i3 > 0 && !$z0) {
                    invalidateValues();
                    shareOutDelta($i3, $f0);
                    solve($r1);
                }
            }
        }

        private float calculateTotalWeight() throws  {
            float $f0 = 0.0f;
            int $i1 = GridLayout.this.getChildCount();
            for (int $i0 = 0; $i0 < $i1; $i0++) {
                View $r2 = GridLayout.this.getChildAt($i0);
                if ($r2.getVisibility() != 8) {
                    LayoutParams $r3 = GridLayout.this.getLayoutParams($r2);
                    $f0 += (this.horizontal ? $r3.columnSpec : $r3.rowSpec).weight;
                }
            }
            return $f0;
        }

        private void computeLocations(int[] $r1) throws  {
            if (hasWeights()) {
                solveAndDistributeSpace($r1);
            } else {
                solve($r1);
            }
            if (!this.orderPreserved) {
                int $i1 = $r1[0];
                int $i0 = $r1.length;
                for (int $i2 = 0; $i2 < $i0; $i2++) {
                    $r1[$i2] = $r1[$i2] - $i1;
                }
            }
        }

        public int[] getLocations() throws  {
            if (this.locations == null) {
                this.locations = new int[(getCount() + 1)];
            }
            if (!this.locationsValid) {
                computeLocations(this.locations);
                this.locationsValid = true;
            }
            return this.locations;
        }

        private int size(int[] $r1) throws  {
            return $r1[getCount()];
        }

        private void setParentConstraints(int $i0, int $i1) throws  {
            this.parentMin.value = $i0;
            this.parentMax.value = -$i1;
            this.locationsValid = false;
        }

        private int getMeasure(int $i0, int $i1) throws  {
            setParentConstraints($i0, $i1);
            return size(getLocations());
        }

        public int getMeasure(int $i0) throws  {
            int $i1 = MeasureSpec.getMode($i0);
            $i0 = MeasureSpec.getSize($i0);
            switch ($i1) {
                case Integer.MIN_VALUE:
                    return getMeasure(0, $i0);
                case 0:
                    return getMeasure(0, GridLayout.MAX_SIZE);
                case 1073741824:
                    return getMeasure($i0, $i0);
                default:
                    if ($assertionsDisabled) {
                        return 0;
                    }
                    throw new AssertionError();
            }
        }

        public void layout(int $i0) throws  {
            setParentConstraints($i0, $i0);
            getLocations();
        }

        public void invalidateStructure() throws  {
            this.maxIndex = Integer.MIN_VALUE;
            this.groupBounds = null;
            this.forwardLinks = null;
            this.backwardLinks = null;
            this.leadingMargins = null;
            this.trailingMargins = null;
            this.arcs = null;
            this.locations = null;
            this.deltas = null;
            this.hasWeightsValid = false;
            invalidateValues();
        }

        public void invalidateValues() throws  {
            this.groupBoundsValid = false;
            this.forwardLinksValid = false;
            this.backwardLinksValid = false;
            this.leadingMarginsValid = false;
            this.trailingMarginsValid = false;
            this.arcsValid = false;
            this.locationsValid = false;
        }
    }

    static final class Interval {
        public final int max;
        public final int min;

        public Interval(int $i0, int $i1) throws  {
            this.min = $i0;
            this.max = $i1;
        }

        int size() throws  {
            return this.max - this.min;
        }

        Interval inverse() throws  {
            return new Interval(this.max, this.min);
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null || getClass() != $r1.getClass()) {
                return false;
            }
            Interval $r4 = (Interval) $r1;
            if (this.max != $r4.max) {
                return false;
            }
            return this.min == $r4.min;
        }

        public int hashCode() throws  {
            return (this.min * 31) + this.max;
        }

        public String toString() throws  {
            return "[" + this.min + ", " + this.max + "]";
        }
    }

    public static class LayoutParams extends MarginLayoutParams {
        private static final int BOTTOM_MARGIN = C0194R.styleable.GridLayout_Layout_android_layout_marginBottom;
        private static final int COLUMN = C0194R.styleable.GridLayout_Layout_layout_column;
        private static final int COLUMN_SPAN = C0194R.styleable.GridLayout_Layout_layout_columnSpan;
        private static final int COLUMN_WEIGHT = C0194R.styleable.GridLayout_Layout_layout_columnWeight;
        private static final int DEFAULT_COLUMN = Integer.MIN_VALUE;
        private static final int DEFAULT_HEIGHT = -2;
        private static final int DEFAULT_MARGIN = Integer.MIN_VALUE;
        private static final int DEFAULT_ROW = Integer.MIN_VALUE;
        private static final Interval DEFAULT_SPAN = new Interval(Integer.MIN_VALUE, InboxNativeManager.INBOX_STATUS_FAILURE_TIMEOUT);
        private static final int DEFAULT_SPAN_SIZE = DEFAULT_SPAN.size();
        private static final int DEFAULT_WIDTH = -2;
        private static final int GRAVITY = C0194R.styleable.GridLayout_Layout_layout_gravity;
        private static final int LEFT_MARGIN = C0194R.styleable.GridLayout_Layout_android_layout_marginLeft;
        private static final int MARGIN = C0194R.styleable.GridLayout_Layout_android_layout_margin;
        private static final int RIGHT_MARGIN = C0194R.styleable.GridLayout_Layout_android_layout_marginRight;
        private static final int ROW = C0194R.styleable.GridLayout_Layout_layout_row;
        private static final int ROW_SPAN = C0194R.styleable.GridLayout_Layout_layout_rowSpan;
        private static final int ROW_WEIGHT = C0194R.styleable.GridLayout_Layout_layout_rowWeight;
        private static final int TOP_MARGIN = C0194R.styleable.GridLayout_Layout_android_layout_marginTop;
        public Spec columnSpec;
        public Spec rowSpec;

        private LayoutParams(int $i0, int $i1, int $i2, int $i3, int $i4, int $i5, Spec $r1, Spec $r2) throws  {
            super($i0, $i1);
            this.rowSpec = Spec.UNDEFINED;
            this.columnSpec = Spec.UNDEFINED;
            setMargins($i2, $i3, $i4, $i5);
            this.rowSpec = $r1;
            this.columnSpec = $r2;
        }

        public LayoutParams(Spec $r1, Spec $r2) throws  {
            this(-2, -2, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE, $r1, $r2);
        }

        public LayoutParams() throws  {
            this(Spec.UNDEFINED, Spec.UNDEFINED);
        }

        public LayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
            super($r1);
            this.rowSpec = Spec.UNDEFINED;
            this.columnSpec = Spec.UNDEFINED;
        }

        public LayoutParams(MarginLayoutParams $r1) throws  {
            super($r1);
            this.rowSpec = Spec.UNDEFINED;
            this.columnSpec = Spec.UNDEFINED;
        }

        public LayoutParams(LayoutParams $r1) throws  {
            super($r1);
            this.rowSpec = Spec.UNDEFINED;
            this.columnSpec = Spec.UNDEFINED;
            this.rowSpec = $r1.rowSpec;
            this.columnSpec = $r1.columnSpec;
        }

        public LayoutParams(Context $r1, AttributeSet $r2) throws  {
            super($r1, $r2);
            this.rowSpec = Spec.UNDEFINED;
            this.columnSpec = Spec.UNDEFINED;
            reInitSuper($r1, $r2);
            init($r1, $r2);
        }

        private void reInitSuper(Context $r1, AttributeSet $r2) throws  {
            TypedArray $r4 = $r1.obtainStyledAttributes($r2, C0194R.styleable.GridLayout_Layout);
            try {
                int $i0 = $r4.getDimensionPixelSize(MARGIN, Integer.MIN_VALUE);
                this.leftMargin = $r4.getDimensionPixelSize(LEFT_MARGIN, $i0);
                this.topMargin = $r4.getDimensionPixelSize(TOP_MARGIN, $i0);
                this.rightMargin = $r4.getDimensionPixelSize(RIGHT_MARGIN, $i0);
                this.bottomMargin = $r4.getDimensionPixelSize(BOTTOM_MARGIN, $i0);
            } finally {
                $r4.recycle();
            }
        }

        private void init(Context $r1, AttributeSet $r2) throws  {
            TypedArray $r4 = $r1.obtainStyledAttributes($r2, C0194R.styleable.GridLayout_Layout);
            try {
                int $i0 = $r4.getInt(GRAVITY, 0);
                this.columnSpec = GridLayout.spec($r4.getInt(COLUMN, Integer.MIN_VALUE), $r4.getInt(COLUMN_SPAN, DEFAULT_SPAN_SIZE), GridLayout.getAlignment($i0, true), $r4.getFloat(COLUMN_WEIGHT, 0.0f));
                this.rowSpec = GridLayout.spec($r4.getInt(ROW, Integer.MIN_VALUE), $r4.getInt(ROW_SPAN, DEFAULT_SPAN_SIZE), GridLayout.getAlignment($i0, false), $r4.getFloat(ROW_WEIGHT, 0.0f));
            } finally {
                $r4.recycle();
            }
        }

        public void setGravity(int $i0) throws  {
            this.rowSpec = this.rowSpec.copyWriteAlignment(GridLayout.getAlignment($i0, false));
            this.columnSpec = this.columnSpec.copyWriteAlignment(GridLayout.getAlignment($i0, true));
        }

        protected void setBaseAttributes(TypedArray $r1, int $i0, int $i1) throws  {
            this.width = $r1.getLayoutDimension($i0, -2);
            this.height = $r1.getLayoutDimension($i1, -2);
        }

        final void setRowSpecSpan(Interval $r1) throws  {
            this.rowSpec = this.rowSpec.copyWriteSpan($r1);
        }

        final void setColumnSpecSpan(Interval $r1) throws  {
            this.columnSpec = this.columnSpec.copyWriteSpan($r1);
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null || getClass() != $r1.getClass()) {
                return false;
            }
            LayoutParams $r4 = (LayoutParams) $r1;
            if (this.columnSpec.equals($r4.columnSpec)) {
                return this.rowSpec.equals($r4.rowSpec);
            } else {
                return false;
            }
        }

        public int hashCode() throws  {
            return (this.rowSpec.hashCode() * 31) + this.columnSpec.hashCode();
        }
    }

    static final class MutableInt {
        public int value;

        public MutableInt() throws  {
            reset();
        }

        public MutableInt(int $i0) throws  {
            this.value = $i0;
        }

        public void reset() throws  {
            this.value = Integer.MIN_VALUE;
        }

        public String toString() throws  {
            return Integer.toString(this.value);
        }
    }

    static final class PackedMap<K, V> {
        public final int[] index;
        public final K[] keys;
        public final V[] values;

        private PackedMap(@Signature({"([TK;[TV;)V"}) K[] $r1, @Signature({"([TK;[TV;)V"}) V[] $r2) throws  {
            this.index = createIndex($r1);
            this.keys = compact($r1, this.index);
            this.values = compact($r2, this.index);
        }

        public V getValue(@Signature({"(I)TV;"}) int $i0) throws  {
            return this.values[this.index[$i0]];
        }

        private static <K> int[] createIndex(@Signature({"<K:", "Ljava/lang/Object;", ">([TK;)[I"}) K[] $r0) throws  {
            int $i0 = $r0.length;
            int[] $r3 = new int[$i0];
            HashMap $r2 = new HashMap();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                Object $r1 = $r0[$i1];
                Integer $r5 = (Integer) $r2.get($r1);
                if ($r5 == null) {
                    Integer $r6 = Integer.valueOf($r2.size());
                    $r5 = $r6;
                    $r2.put($r1, $r6);
                }
                $r3[$i1] = $r5.intValue();
            }
            return $r3;
        }

        private static <K> K[] compact(@Signature({"<K:", "Ljava/lang/Object;", ">([TK;[I)[TK;"}) K[] $r0, @Signature({"<K:", "Ljava/lang/Object;", ">([TK;[I)[TK;"}) int[] $r1) throws  {
            int $i0 = $r0.length;
            Object[] $r4 = (Object[]) Array.newInstance($r0.getClass().getComponentType(), GridLayout.max2($r1, -1) + 1);
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r4[$r1[$i1]] = $r0[$i1];
            }
            return $r4;
        }
    }

    public static class Spec {
        static final float DEFAULT_WEIGHT = 0.0f;
        static final Spec UNDEFINED = GridLayout.spec(Integer.MIN_VALUE);
        final Alignment alignment;
        final Interval span;
        final boolean startDefined;
        final float weight;

        private Spec(boolean $z0, Interval $r1, Alignment $r2, float $f0) throws  {
            this.startDefined = $z0;
            this.span = $r1;
            this.alignment = $r2;
            this.weight = $f0;
        }

        private Spec(boolean $z0, int $i0, int $i1, Alignment $r1, float $f0) throws  {
            this($z0, new Interval($i0, $i0 + $i1), $r1, $f0);
        }

        public Alignment getAbsoluteAlignment(boolean $z0) throws  {
            if (this.alignment != GridLayout.UNDEFINED_ALIGNMENT) {
                return this.alignment;
            }
            if (this.weight == 0.0f) {
                return $z0 ? GridLayout.START : GridLayout.BASELINE;
            } else {
                return GridLayout.FILL;
            }
        }

        final Spec copyWriteSpan(Interval $r1) throws  {
            return new Spec(this.startDefined, $r1, this.alignment, this.weight);
        }

        final Spec copyWriteAlignment(Alignment $r1) throws  {
            return new Spec(this.startDefined, this.span, $r1, this.weight);
        }

        final int getFlexibility() throws  {
            return (this.alignment == GridLayout.UNDEFINED_ALIGNMENT && this.weight == 0.0f) ? 0 : 2;
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null || getClass() != $r1.getClass()) {
                return false;
            }
            Spec $r4 = (Spec) $r1;
            if (this.alignment.equals($r4.alignment)) {
                return this.span.equals($r4.span);
            } else {
                return false;
            }
        }

        public int hashCode() throws  {
            return (this.span.hashCode() * 31) + this.alignment.hashCode();
        }
    }

    public GridLayout(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mHorizontalAxis = new Axis(true);
        this.mVerticalAxis = new Axis(false);
        this.mOrientation = 0;
        this.mUseDefaultMargins = false;
        this.mAlignmentMode = 1;
        this.mLastLayoutParamsHashCode = 0;
        this.mPrinter = LOG_PRINTER;
        this.mDefaultGap = $r1.getResources().getDimensionPixelOffset(C0194R.dimen.default_gap);
        TypedArray $r7 = $r1.obtainStyledAttributes($r2, C0194R.styleable.GridLayout);
        try {
            setRowCount($r7.getInt(ROW_COUNT, Integer.MIN_VALUE));
            setColumnCount($r7.getInt(COLUMN_COUNT, Integer.MIN_VALUE));
            setOrientation($r7.getInt(ORIENTATION, 0));
            setUseDefaultMargins($r7.getBoolean(USE_DEFAULT_MARGINS, false));
            setAlignmentMode($r7.getInt(ALIGNMENT_MODE, 1));
            setRowOrderPreserved($r7.getBoolean(ROW_ORDER_PRESERVED, true));
            setColumnOrderPreserved($r7.getBoolean(COLUMN_ORDER_PRESERVED, true));
        } finally {
            $r7.recycle();
        }
    }

    public GridLayout(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public GridLayout(Context $r1) throws  {
        this($r1, null);
    }

    public int getOrientation() throws  {
        return this.mOrientation;
    }

    public void setOrientation(int $i0) throws  {
        if (this.mOrientation != $i0) {
            this.mOrientation = $i0;
            invalidateStructure();
            requestLayout();
        }
    }

    public int getRowCount() throws  {
        return this.mVerticalAxis.getCount();
    }

    public void setRowCount(int $i0) throws  {
        this.mVerticalAxis.setCount($i0);
        invalidateStructure();
        requestLayout();
    }

    public int getColumnCount() throws  {
        return this.mHorizontalAxis.getCount();
    }

    public void setColumnCount(int $i0) throws  {
        this.mHorizontalAxis.setCount($i0);
        invalidateStructure();
        requestLayout();
    }

    public boolean getUseDefaultMargins() throws  {
        return this.mUseDefaultMargins;
    }

    public void setUseDefaultMargins(boolean $z0) throws  {
        this.mUseDefaultMargins = $z0;
        requestLayout();
    }

    public int getAlignmentMode() throws  {
        return this.mAlignmentMode;
    }

    public void setAlignmentMode(int $i0) throws  {
        this.mAlignmentMode = $i0;
        requestLayout();
    }

    public boolean isRowOrderPreserved() throws  {
        return this.mVerticalAxis.isOrderPreserved();
    }

    public void setRowOrderPreserved(boolean $z0) throws  {
        this.mVerticalAxis.setOrderPreserved($z0);
        invalidateStructure();
        requestLayout();
    }

    public boolean isColumnOrderPreserved() throws  {
        return this.mHorizontalAxis.isOrderPreserved();
    }

    public void setColumnOrderPreserved(boolean $z0) throws  {
        this.mHorizontalAxis.setOrderPreserved($z0);
        invalidateStructure();
        requestLayout();
    }

    public Printer getPrinter() throws  {
        return this.mPrinter;
    }

    public void setPrinter(Printer $r1) throws  {
        if ($r1 == null) {
            $r1 = NO_PRINTER;
        }
        this.mPrinter = $r1;
    }

    static int max2(int[] $r0, int $i0) throws  {
        int $i2 = $i0;
        for (int $i1 : $r0) {
            $i2 = Math.max($i2, $i1);
        }
        return $i2;
    }

    static <T> T[] append(@Signature({"<T:", "Ljava/lang/Object;", ">([TT;[TT;)[TT;"}) T[] $r0, @Signature({"<T:", "Ljava/lang/Object;", ">([TT;[TT;)[TT;"}) T[] $r1) throws  {
        Object[] $r4 = (Object[]) Array.newInstance($r0.getClass().getComponentType(), $r0.length + $r1.length);
        System.arraycopy($r0, 0, $r4, 0, $r0.length);
        System.arraycopy($r1, 0, $r4, $r0.length, $r1.length);
        return $r4;
    }

    static Alignment getAlignment(int $i0, boolean $z0) throws  {
        byte $b1;
        byte $b2;
        if ($z0) {
            $b1 = (byte) 7;
        } else {
            $b1 = (byte) 112;
        }
        if ($z0) {
            $b2 = (byte) 0;
        } else {
            $b2 = (byte) 4;
        }
        switch (($i0 & $b1) >> $b2) {
            case 1:
                return CENTER;
            case 3:
                return $z0 ? LEFT : TOP;
            case 5:
                return $z0 ? RIGHT : BOTTOM;
            case 7:
                return FILL;
            case GravityCompat.START /*8388611*/:
                return START;
            case GravityCompat.END /*8388613*/:
                return END;
            default:
                return UNDEFINED_ALIGNMENT;
        }
    }

    private int getDefaultMargin(View $r1, boolean horizontal, boolean leading) throws  {
        return $r1.getClass() == Space.class ? 0 : this.mDefaultGap / 2;
    }

    private int getDefaultMargin(View $r1, boolean isAtEdge, boolean $z1, boolean $z2) throws  {
        return getDefaultMargin($r1, $z1, $z2);
    }

    private int getDefaultMargin(View $r1, LayoutParams $r2, boolean $z0, boolean $z1) throws  {
        boolean $z2 = true;
        if (!this.mUseDefaultMargins) {
            return 0;
        }
        Spec $r4;
        Axis $r5;
        if ($z0) {
            $r4 = $r2.columnSpec;
        } else {
            $r4 = $r2.rowSpec;
        }
        if ($z0) {
            $r5 = this.mHorizontalAxis;
        } else {
            $r5 = this.mVerticalAxis;
        }
        Interval $r3 = $r4.span;
        boolean $z3 = ($z0 && isLayoutRtlCompat()) ? !$z1 : $z1;
        if ($z3) {
            if ($r3.min != 0) {
                $z2 = false;
            }
        } else if ($r3.max != $r5.getCount()) {
            $z2 = false;
        }
        return getDefaultMargin($r1, $z2, $z0, $z1);
    }

    int getMargin1(View $r1, boolean $z0, boolean $z1) throws  {
        LayoutParams $r2 = getLayoutParams($r1);
        int $i0 = $z0 ? $z1 ? $r2.leftMargin : $r2.rightMargin : $z1 ? $r2.topMargin : $r2.bottomMargin;
        if ($i0 == Integer.MIN_VALUE) {
            return getDefaultMargin($r1, $r2, $z0, $z1);
        }
        return $i0;
    }

    private boolean isLayoutRtlCompat() throws  {
        return ViewCompat.getLayoutDirection(this) == 1;
    }

    private int getMargin(View $r1, boolean $z0, boolean $z1) throws  {
        if (this.mAlignmentMode == 1) {
            return getMargin1($r1, $z0, $z1);
        }
        Axis $r2;
        Spec $r5;
        int $i0;
        if ($z0) {
            $r2 = this.mHorizontalAxis;
        } else {
            $r2 = this.mVerticalAxis;
        }
        int[] $r3 = $z1 ? $r2.getLeadingMargins() : $r2.getTrailingMargins();
        LayoutParams $r4 = getLayoutParams($r1);
        if ($z0) {
            $r5 = $r4.columnSpec;
        } else {
            $r5 = $r4.rowSpec;
        }
        if ($z1) {
            $i0 = $r5.span.min;
        } else {
            $i0 = $r5.span.max;
        }
        return $r3[$i0];
    }

    private int getTotalMargin(View $r1, boolean $z0) throws  {
        return getMargin($r1, $z0, true) + getMargin($r1, $z0, false);
    }

    private static boolean fits(int[] $r0, int $i0, int $i1, int $i2) throws  {
        if ($i2 > $r0.length) {
            return false;
        }
        while ($i1 < $i2) {
            if ($r0[$i1] > $i0) {
                return false;
            }
            $i1++;
        }
        return true;
    }

    private static void procrusteanFill(int[] $r0, int $i0, int $i1, int $i2) throws  {
        int $i3 = $r0.length;
        Arrays.fill($r0, Math.min($i0, $i3), Math.min($i1, $i3), $i2);
    }

    private static void setCellGroup(LayoutParams $r0, int $i0, int $i1, int $i2, int $i3) throws  {
        $r0.setRowSpecSpan(new Interval($i0, $i0 + $i1));
        $r0.setColumnSpecSpan(new Interval($i2, $i2 + $i3));
    }

    private static int clip(Interval $r0, boolean $z0, int $i0) throws  {
        int $i1 = $r0.size();
        if ($i0 == 0) {
            return $i1;
        }
        return Math.min($i1, $i0 - ($z0 ? Math.min($r0.min, $i0) : 0));
    }

    private void validateLayoutParams() throws  {
        boolean $z1;
        Axis $r3;
        if (this.mOrientation == 0) {
            $z1 = true;
        } else {
            $z1 = false;
        }
        if ($z1) {
            $r3 = this.mHorizontalAxis;
        } else {
            $r3 = this.mVerticalAxis;
        }
        int $i0 = $r3.definedCount != Integer.MIN_VALUE ? $r3.definedCount : 0;
        int $i1 = 0;
        int $i2 = 0;
        int[] $r2 = new int[$i0];
        int $i4 = getChildCount();
        for (int $i3 = 0; $i3 < $i4; $i3++) {
            Spec $r7;
            LayoutParams $r6 = (LayoutParams) getChildAt($i3).getLayoutParams();
            if ($z1) {
                $r7 = $r6.rowSpec;
            } else {
                $r7 = $r6.columnSpec;
            }
            Interval $r1 = $r7.span;
            boolean $z0 = $r7.startDefined;
            int $i5 = $r1.size();
            if ($z0) {
                $i1 = $r1.min;
            }
            if ($z1) {
                $r7 = $r6.columnSpec;
            } else {
                $r7 = $r6.rowSpec;
            }
            $r1 = $r7.span;
            boolean $z2 = $r7.startDefined;
            int $i6 = clip($r1, $z2, $i0);
            if ($z2) {
                $i2 = $r1.min;
            }
            if ($i0 != 0) {
                if (!($z0 && $z2)) {
                    while (!fits($r2, $i1, $i2, $i2 + $i6)) {
                        if ($z2) {
                            $i1++;
                        } else if ($i2 + $i6 <= $i0) {
                            $i2++;
                        } else {
                            $i2 = 0;
                            $i1++;
                        }
                    }
                }
                procrusteanFill($r2, $i2, $i2 + $i6, $i1 + $i5);
            }
            if ($z1) {
                setCellGroup($r6, $i1, $i5, $i2, $i6);
            } else {
                setCellGroup($r6, $i2, $i6, $i1, $i5);
            }
            $i2 += $i6;
        }
    }

    private void invalidateStructure() throws  {
        this.mLastLayoutParamsHashCode = 0;
        if (this.mHorizontalAxis != null) {
            this.mHorizontalAxis.invalidateStructure();
        }
        if (this.mVerticalAxis != null) {
            this.mVerticalAxis.invalidateStructure();
        }
        invalidateValues();
    }

    private void invalidateValues() throws  {
        if (this.mHorizontalAxis != null && this.mVerticalAxis != null) {
            this.mHorizontalAxis.invalidateValues();
            this.mVerticalAxis.invalidateValues();
        }
    }

    final LayoutParams getLayoutParams(View $r1) throws  {
        return (LayoutParams) $r1.getLayoutParams();
    }

    private static void handleInvalidParams(String $r0) throws  {
        throw new IllegalArgumentException($r0 + ". ");
    }

    private void checkLayoutParams(LayoutParams $r1, boolean $z0) throws  {
        String $r3;
        Spec $r4;
        Axis $r7;
        if ($z0) {
            $r3 = "column";
        } else {
            $r3 = "row";
        }
        if ($z0) {
            $r4 = $r1.columnSpec;
        } else {
            $r4 = $r1.rowSpec;
        }
        Interval $r2 = $r4.span;
        if ($r2.min != Integer.MIN_VALUE && $r2.min < 0) {
            handleInvalidParams($r3 + " indices must be positive");
        }
        if ($z0) {
            $r7 = this.mHorizontalAxis;
        } else {
            $r7 = this.mVerticalAxis;
        }
        int $i0 = $r7.definedCount;
        if ($i0 != Integer.MIN_VALUE) {
            if ($r2.max > $i0) {
                handleInvalidParams($r3 + " indices (start + span) mustn't exceed the " + $r3 + " count");
            }
            if ($r2.size() > $i0) {
                handleInvalidParams($r3 + " span mustn't exceed the " + $r3 + " count");
            }
        }
    }

    protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        if (!($r1 instanceof LayoutParams)) {
            return false;
        }
        LayoutParams $r2 = (LayoutParams) $r1;
        checkLayoutParams($r2, true);
        checkLayoutParams($r2, false);
        return true;
    }

    protected LayoutParams generateDefaultLayoutParams() throws  {
        return new LayoutParams();
    }

    public LayoutParams generateLayoutParams(AttributeSet $r1) throws  {
        return new LayoutParams(getContext(), $r1);
    }

    protected LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams $r1) throws  {
        return new LayoutParams($r1);
    }

    private void drawLine(Canvas $r1, int $i0, int $i1, int $i2, int $i3, Paint $r2) throws  {
        if (isLayoutRtlCompat()) {
            int $i4 = getWidth();
            $r1.drawLine((float) ($i4 - $i0), (float) $i1, (float) ($i4 - $i2), (float) $i3, $r2);
            return;
        }
        $r1.drawLine((float) $i0, (float) $i1, (float) $i2, (float) $i3, $r2);
    }

    private int computeLayoutParamsHashCode() throws  {
        int $i0 = 1;
        int $i2 = getChildCount();
        for (int $i1 = 0; $i1 < $i2; $i1++) {
            View $r1 = getChildAt($i1);
            if ($r1.getVisibility() != 8) {
                $i0 = ($i0 * 31) + ((LayoutParams) $r1.getLayoutParams()).hashCode();
            }
        }
        return $i0;
    }

    private void consistencyCheck() throws  {
        if (this.mLastLayoutParamsHashCode == 0) {
            validateLayoutParams();
            this.mLastLayoutParamsHashCode = computeLayoutParamsHashCode();
        } else if (this.mLastLayoutParamsHashCode != computeLayoutParamsHashCode()) {
            this.mPrinter.println("The fields of some layout parameters were modified in between layout operations. Check the javadoc for GridLayout.LayoutParams#rowSpec.");
            invalidateStructure();
            consistencyCheck();
        }
    }

    private void measureChildWithMargins2(View $r1, int $i0, int $i1, int $i2, int $i3) throws  {
        $r1.measure(getChildMeasureSpec($i0, getTotalMargin($r1, true), $i2), getChildMeasureSpec($i1, getTotalMargin($r1, false), $i3));
    }

    private void measureChildrenWithMargins(int $i0, int $i1, boolean $z0) throws  {
        int $i5 = getChildCount();
        for (int $i4 = 0; $i4 < $i5; $i4++) {
            View $r1 = getChildAt($i4);
            if ($r1.getVisibility() != 8) {
                LayoutParams $r2 = getLayoutParams($r1);
                if ($z0) {
                    measureChildWithMargins2($r1, $i0, $i1, $r2.width, $r2.height);
                } else {
                    boolean $z1;
                    Spec $r3;
                    if (this.mOrientation == 0) {
                        $z1 = true;
                    } else {
                        $z1 = false;
                    }
                    if ($z1) {
                        $r3 = $r2.columnSpec;
                    } else {
                        $r3 = $r2.rowSpec;
                    }
                    if ($r3.getAbsoluteAlignment($z1) == FILL) {
                        Axis $r7;
                        Interval $r6 = $r3.span;
                        if ($z1) {
                            $r7 = this.mHorizontalAxis;
                        } else {
                            $r7 = this.mVerticalAxis;
                        }
                        int[] $r8 = $r7.getLocations();
                        int $i3 = ($r8[$r6.max] - $r8[$r6.min]) - getTotalMargin($r1, $z1);
                        if ($z1) {
                            measureChildWithMargins2($r1, $i0, $i1, $i3, $r2.height);
                        } else {
                            measureChildWithMargins2($r1, $i0, $i1, $r2.width, $i3);
                        }
                    }
                }
            }
        }
    }

    static int adjust(int $i0, int $i1) throws  {
        return MeasureSpec.makeMeasureSpec(MeasureSpec.getSize($i0 + $i1), MeasureSpec.getMode($i0));
    }

    protected void onMeasure(int $i0, int $i1) throws  {
        int $i7;
        int $i5;
        consistencyCheck();
        invalidateValues();
        int $i2 = getPaddingLeft() + getPaddingRight();
        int $i3 = getPaddingTop() + getPaddingBottom();
        int $i4 = adjust($i0, -$i2);
        int $i6 = adjust($i1, -$i3);
        measureChildrenWithMargins($i4, $i6, true);
        if (this.mOrientation == 0) {
            $i7 = this.mHorizontalAxis.getMeasure($i4);
            measureChildrenWithMargins($i4, $i6, false);
            $i5 = this.mVerticalAxis.getMeasure($i6);
        } else {
            $i5 = this.mVerticalAxis.getMeasure($i6);
            measureChildrenWithMargins($i4, $i6, false);
            $i7 = this.mHorizontalAxis.getMeasure($i4);
        }
        setMeasuredDimension(ViewCompat.resolveSizeAndState(Math.max($i7 + $i2, getSuggestedMinimumWidth()), $i0, 0), ViewCompat.resolveSizeAndState(Math.max($i5 + $i3, getSuggestedMinimumHeight()), $i1, 0));
    }

    private int getMeasurement(View $r1, boolean $z0) throws  {
        return $z0 ? $r1.getMeasuredWidth() : $r1.getMeasuredHeight();
    }

    final int getMeasurementIncludingMargin(View $r1, boolean $z0) throws  {
        return $r1.getVisibility() == 8 ? 0 : getMeasurement($r1, $z0) + getTotalMargin($r1, $z0);
    }

    public void requestLayout() throws  {
        super.requestLayout();
        invalidateStructure();
    }

    protected void onLayout(boolean changed, int $i0, int $i1, int $i2, int $i3) throws  {
        consistencyCheck();
        $i0 = $i2 - $i0;
        int $i11 = $i3 - $i1;
        $i1 = getPaddingLeft();
        $i2 = getPaddingTop();
        $i3 = getPaddingRight();
        int $i14 = getPaddingBottom();
        this.mHorizontalAxis.layout(($i0 - $i1) - $i3);
        this.mVerticalAxis.layout(($i11 - $i2) - $i14);
        int[] $r2 = this.mHorizontalAxis.getLocations();
        int[] $r3 = this.mVerticalAxis.getLocations();
        $i11 = getChildCount();
        for ($i14 = 0; $i14 < $i11; $i14++) {
            View $r4 = getChildAt($i14);
            if ($r4.getVisibility() != 8) {
                LayoutParams $r5 = getLayoutParams($r4);
                Spec $r6 = $r5.columnSpec;
                Spec $r7 = $r5.rowSpec;
                Interval $r8 = $r6.span;
                Interval $r9 = $r7.span;
                int $i8 = $r2[$r8.min];
                int $i7 = $r3[$r9.min];
                int $i6 = $r2[$r8.max] - $i8;
                int $i5 = $r3[$r9.max] - $i7;
                int $i15 = getMeasurement($r4, true);
                int $i16 = getMeasurement($r4, false);
                Alignment $r10 = $r6.getAbsoluteAlignment(true);
                Alignment $r11 = $r7.getAbsoluteAlignment(false);
                Bounds $r14 = (Bounds) this.mHorizontalAxis.getGroupBounds().getValue($i14);
                Bounds $r15 = (Bounds) this.mVerticalAxis.getGroupBounds().getValue($i14);
                int $i17 = $r10.getGravityOffset($r4, $i6 - $r14.size(true));
                int $i13 = $r11.getGravityOffset($r4, $i5 - $r15.size(true));
                int $i18 = getMargin($r4, true, true);
                int $i12 = getMargin($r4, false, true);
                int $i19 = getMargin($r4, true, false);
                int $i9 = $i18 + $i19;
                int $i10 = $i12 + getMargin($r4, false, false);
                int $i20 = $r14.getOffset(this, $r4, $r10, $i15 + $i9, true);
                int $i4 = $r15.getOffset(this, $r4, $r11, $i16 + $i10, false);
                $i6 = $r10.getSizeInCell($r4, $i15, $i6 - $i9);
                $i16 = $r11.getSizeInCell($r4, $i16, $i5 - $i10);
                int $i82 = $i8 + $i17;
                $i8 = $i82;
                $i8 = $i82 + $i20;
                $i8 = !isLayoutRtlCompat() ? ($i1 + $i18) + $i8 : ((($i0 - $i6) - $i3) - $i19) - $i8;
                $i7 = ((($i2 + $i7) + $i13) + $i4) + $i12;
                if (!($i6 == $r4.getMeasuredWidth() && $i16 == $r4.getMeasuredHeight())) {
                    $r4.measure(MeasureSpec.makeMeasureSpec($i6, 1073741824), MeasureSpec.makeMeasureSpec($i16, 1073741824));
                }
                $r4.layout($i8, $i7, $i8 + $i6, $i7 + $i16);
            }
        }
    }

    public static Spec spec(int $i0, int $i1, Alignment $r0, float $f0) throws  {
        return new Spec($i0 != Integer.MIN_VALUE, $i0, $i1, $r0, $f0);
    }

    public static Spec spec(int $i0, Alignment $r0, float $f0) throws  {
        return spec($i0, 1, $r0, $f0);
    }

    public static Spec spec(int $i0, int $i1, float $f0) throws  {
        return spec($i0, $i1, UNDEFINED_ALIGNMENT, $f0);
    }

    public static Spec spec(int $i0, float $f0) throws  {
        return spec($i0, 1, $f0);
    }

    public static Spec spec(int $i0, int $i1, Alignment $r0) throws  {
        return spec($i0, $i1, $r0, 0.0f);
    }

    public static Spec spec(int $i0, Alignment $r0) throws  {
        return spec($i0, 1, $r0);
    }

    public static Spec spec(int $i0, int $i1) throws  {
        return spec($i0, $i1, UNDEFINED_ALIGNMENT);
    }

    public static Spec spec(int $i0) throws  {
        return spec($i0, 1);
    }

    private static Alignment createSwitchingAlignment(final Alignment $r0, final Alignment $r1) throws  {
        return new Alignment() {
            int getGravityOffset(View $r1, int $i0) throws  {
                Alignment $r2;
                boolean $z0 = true;
                if (ViewCompat.getLayoutDirection($r1) != 1) {
                    $z0 = false;
                }
                if ($z0) {
                    $r2 = $r1;
                } else {
                    $r2 = $r0;
                }
                return $r2.getGravityOffset($r1, $i0);
            }

            public int getAlignmentValue(View $r1, int $i0, int $i1) throws  {
                Alignment $r2;
                boolean $z0 = true;
                if (ViewCompat.getLayoutDirection($r1) != 1) {
                    $z0 = false;
                }
                if ($z0) {
                    $r2 = $r1;
                } else {
                    $r2 = $r0;
                }
                return $r2.getAlignmentValue($r1, $i0, $i1);
            }

            String getDebugString() throws  {
                return "SWITCHING[L:" + $r0.getDebugString() + ", R:" + $r1.getDebugString() + "]";
            }
        };
    }

    static boolean canStretch(int $i0) throws  {
        return ($i0 & 2) != 0;
    }
}
