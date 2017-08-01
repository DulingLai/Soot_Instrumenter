package android.support.graphics.drawable;

import android.annotation.TargetApi;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Cap;
import android.graphics.Paint.Join;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.VectorDrawable;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.PathParser.PathDataNode;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import dalvik.annotation.Signature;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@TargetApi(21)
public class VectorDrawableCompat extends VectorDrawableCommon {
    private static final boolean DBG_VECTOR_DRAWABLE = false;
    static final Mode DEFAULT_TINT_MODE = Mode.SRC_IN;
    private static final int LINECAP_BUTT = 0;
    private static final int LINECAP_ROUND = 1;
    private static final int LINECAP_SQUARE = 2;
    private static final int LINEJOIN_BEVEL = 2;
    private static final int LINEJOIN_MITER = 0;
    private static final int LINEJOIN_ROUND = 1;
    static final String LOGTAG = "VectorDrawableCompat";
    private static final int MAX_CACHED_BITMAP_SIZE = 2048;
    private static final String SHAPE_CLIP_PATH = "clip-path";
    private static final String SHAPE_GROUP = "group";
    private static final String SHAPE_PATH = "path";
    private static final String SHAPE_VECTOR = "vector";
    private boolean mAllowCaching;
    private ConstantState mCachedConstantStateDelegate;
    private ColorFilter mColorFilter;
    private boolean mMutated;
    private PorterDuffColorFilter mTintFilter;
    private final Rect mTmpBounds;
    private final float[] mTmpFloats;
    private final Matrix mTmpMatrix;
    private VectorDrawableCompatState mVectorState;

    private static class VPath {
        int mChangingConfigurations;
        protected PathDataNode[] mNodes = null;
        String mPathName;

        public boolean canApplyTheme() throws  {
            return false;
        }

        public boolean isClipPath() throws  {
            return false;
        }

        public void printVPath(int $i0) throws  {
            String $r1 = "";
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                $r1 = $r1 + "    ";
            }
            Log.v(VectorDrawableCompat.LOGTAG, $r1 + "current path is :" + this.mPathName + " pathData is " + NodesToString(this.mNodes));
        }

        public String NodesToString(PathDataNode[] $r1) throws  {
            String $r3 = " ";
            for (int $i0 = 0; $i0 < $r1.length; $i0++) {
                $r3 = $r3 + $r1[$i0].type + ":";
                for (float $f0 : $r1[$i0].params) {
                    $r3 = $r3 + $f0 + ",";
                }
            }
            return $r3;
        }

        public VPath(VPath $r1) throws  {
            this.mPathName = $r1.mPathName;
            this.mChangingConfigurations = $r1.mChangingConfigurations;
            this.mNodes = PathParser.deepCopyNodes($r1.mNodes);
        }

        public void toPath(Path $r1) throws  {
            $r1.reset();
            if (this.mNodes != null) {
                PathDataNode.nodesToPath(this.mNodes, $r1);
            }
        }

        public String getPathName() throws  {
            return this.mPathName;
        }

        public void applyTheme(Theme t) throws  {
        }

        public PathDataNode[] getPathData() throws  {
            return this.mNodes;
        }

        public void setPathData(PathDataNode[] $r1) throws  {
            if (PathParser.canMorph(this.mNodes, $r1)) {
                PathParser.updateNodes(this.mNodes, $r1);
            } else {
                this.mNodes = PathParser.deepCopyNodes($r1);
            }
        }
    }

    private static class VClipPath extends VPath {
        public boolean isClipPath() throws  {
            return true;
        }

        public VClipPath(VClipPath $r1) throws  {
            super($r1);
        }

        public void inflate(Resources $r1, AttributeSet $r2, Theme $r3, XmlPullParser $r4) throws  {
            if (TypedArrayUtils.hasAttribute($r4, "pathData")) {
                TypedArray $r6 = VectorDrawableCommon.obtainAttributes($r1, $r3, $r2, AndroidResources.styleable_VectorDrawableClipPath);
                updateStateFromTypedArray($r6);
                $r6.recycle();
            }
        }

        private void updateStateFromTypedArray(TypedArray $r1) throws  {
            String $r2 = $r1.getString(0);
            if ($r2 != null) {
                this.mPathName = $r2;
            }
            $r2 = $r1.getString(1);
            if ($r2 != null) {
                this.mNodes = PathParser.createNodesFromPathData($r2);
            }
        }
    }

    private static class VFullPath extends VPath {
        float mFillAlpha = 1.0f;
        int mFillColor = 0;
        int mFillRule;
        float mStrokeAlpha = 1.0f;
        int mStrokeColor = 0;
        Cap mStrokeLineCap = Cap.BUTT;
        Join mStrokeLineJoin = Join.MITER;
        float mStrokeMiterlimit = 4.0f;
        float mStrokeWidth = 0.0f;
        private int[] mThemeAttrs;
        float mTrimPathEnd = 1.0f;
        float mTrimPathOffset = 0.0f;
        float mTrimPathStart = 0.0f;

        public VFullPath(VFullPath $r1) throws  {
            super($r1);
            this.mThemeAttrs = $r1.mThemeAttrs;
            this.mStrokeColor = $r1.mStrokeColor;
            this.mStrokeWidth = $r1.mStrokeWidth;
            this.mStrokeAlpha = $r1.mStrokeAlpha;
            this.mFillColor = $r1.mFillColor;
            this.mFillRule = $r1.mFillRule;
            this.mFillAlpha = $r1.mFillAlpha;
            this.mTrimPathStart = $r1.mTrimPathStart;
            this.mTrimPathEnd = $r1.mTrimPathEnd;
            this.mTrimPathOffset = $r1.mTrimPathOffset;
            this.mStrokeLineCap = $r1.mStrokeLineCap;
            this.mStrokeLineJoin = $r1.mStrokeLineJoin;
            this.mStrokeMiterlimit = $r1.mStrokeMiterlimit;
        }

        private Cap getStrokeLineCap(int $i0, Cap $r1) throws  {
            switch ($i0) {
                case 0:
                    return Cap.BUTT;
                case 1:
                    return Cap.ROUND;
                case 2:
                    return Cap.SQUARE;
                default:
                    return $r1;
            }
        }

        private Join getStrokeLineJoin(int $i0, Join $r1) throws  {
            switch ($i0) {
                case 0:
                    return Join.MITER;
                case 1:
                    return Join.ROUND;
                case 2:
                    return Join.BEVEL;
                default:
                    return $r1;
            }
        }

        public boolean canApplyTheme() throws  {
            return this.mThemeAttrs != null;
        }

        public void inflate(Resources $r1, AttributeSet $r2, Theme $r3, XmlPullParser $r4) throws  {
            TypedArray $r6 = VectorDrawableCommon.obtainAttributes($r1, $r3, $r2, AndroidResources.styleable_VectorDrawablePath);
            updateStateFromTypedArray($r6, $r4);
            $r6.recycle();
        }

        private void updateStateFromTypedArray(TypedArray $r1, XmlPullParser $r2) throws  {
            this.mThemeAttrs = null;
            if (TypedArrayUtils.hasAttribute($r2, "pathData")) {
                String $r3 = $r1.getString(0);
                if ($r3 != null) {
                    this.mPathName = $r3;
                }
                $r3 = $r1.getString(2);
                if ($r3 != null) {
                    this.mNodes = PathParser.createNodesFromPathData($r3);
                }
                this.mFillColor = TypedArrayUtils.getNamedColor($r1, $r2, "fillColor", 1, this.mFillColor);
                this.mFillAlpha = TypedArrayUtils.getNamedFloat($r1, $r2, "fillAlpha", 12, this.mFillAlpha);
                this.mStrokeLineCap = getStrokeLineCap(TypedArrayUtils.getNamedInt($r1, $r2, "strokeLineCap", 8, -1), this.mStrokeLineCap);
                this.mStrokeLineJoin = getStrokeLineJoin(TypedArrayUtils.getNamedInt($r1, $r2, "strokeLineJoin", 9, -1), this.mStrokeLineJoin);
                this.mStrokeMiterlimit = TypedArrayUtils.getNamedFloat($r1, $r2, "strokeMiterLimit", 10, this.mStrokeMiterlimit);
                this.mStrokeColor = TypedArrayUtils.getNamedColor($r1, $r2, "strokeColor", 3, this.mStrokeColor);
                this.mStrokeAlpha = TypedArrayUtils.getNamedFloat($r1, $r2, "strokeAlpha", 11, this.mStrokeAlpha);
                this.mStrokeWidth = TypedArrayUtils.getNamedFloat($r1, $r2, "strokeWidth", 4, this.mStrokeWidth);
                this.mTrimPathEnd = TypedArrayUtils.getNamedFloat($r1, $r2, "trimPathEnd", 6, this.mTrimPathEnd);
                this.mTrimPathOffset = TypedArrayUtils.getNamedFloat($r1, $r2, "trimPathOffset", 7, this.mTrimPathOffset);
                this.mTrimPathStart = TypedArrayUtils.getNamedFloat($r1, $r2, "trimPathStart", 5, this.mTrimPathStart);
            }
        }

        public void applyTheme(Theme t) throws  {
            if (this.mThemeAttrs != null) {
            }
        }

        int getStrokeColor() throws  {
            return this.mStrokeColor;
        }

        void setStrokeColor(int $i0) throws  {
            this.mStrokeColor = $i0;
        }

        float getStrokeWidth() throws  {
            return this.mStrokeWidth;
        }

        void setStrokeWidth(float $f0) throws  {
            this.mStrokeWidth = $f0;
        }

        float getStrokeAlpha() throws  {
            return this.mStrokeAlpha;
        }

        void setStrokeAlpha(float $f0) throws  {
            this.mStrokeAlpha = $f0;
        }

        int getFillColor() throws  {
            return this.mFillColor;
        }

        void setFillColor(int $i0) throws  {
            this.mFillColor = $i0;
        }

        float getFillAlpha() throws  {
            return this.mFillAlpha;
        }

        void setFillAlpha(float $f0) throws  {
            this.mFillAlpha = $f0;
        }

        float getTrimPathStart() throws  {
            return this.mTrimPathStart;
        }

        void setTrimPathStart(float $f0) throws  {
            this.mTrimPathStart = $f0;
        }

        float getTrimPathEnd() throws  {
            return this.mTrimPathEnd;
        }

        void setTrimPathEnd(float $f0) throws  {
            this.mTrimPathEnd = $f0;
        }

        float getTrimPathOffset() throws  {
            return this.mTrimPathOffset;
        }

        void setTrimPathOffset(float $f0) throws  {
            this.mTrimPathOffset = $f0;
        }
    }

    private static class VGroup {
        private int mChangingConfigurations;
        final ArrayList<Object> mChildren = new ArrayList();
        private String mGroupName = null;
        private final Matrix mLocalMatrix = new Matrix();
        private float mPivotX = 0.0f;
        private float mPivotY = 0.0f;
        private float mRotate = 0.0f;
        private float mScaleX = 1.0f;
        private float mScaleY = 1.0f;
        private final Matrix mStackedMatrix = new Matrix();
        private int[] mThemeAttrs;
        private float mTranslateX = 0.0f;
        private float mTranslateY = 0.0f;

        public VGroup(@Signature({"(", "Landroid/support/graphics/drawable/VectorDrawableCompat$VGroup;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)V"}) VGroup $r1, @Signature({"(", "Landroid/support/graphics/drawable/VectorDrawableCompat$VGroup;", "Landroid/support/v4/util/ArrayMap", "<", "Ljava/lang/String;", "Ljava/lang/Object;", ">;)V"}) ArrayMap<String, Object> $r2) throws  {
            VGroup vGroup = this;
            this.mRotate = $r1.mRotate;
            this.mPivotX = $r1.mPivotX;
            this.mPivotY = $r1.mPivotY;
            this.mScaleX = $r1.mScaleX;
            this.mScaleY = $r1.mScaleY;
            this.mTranslateX = $r1.mTranslateX;
            this.mTranslateY = $r1.mTranslateY;
            this.mThemeAttrs = $r1.mThemeAttrs;
            this.mGroupName = $r1.mGroupName;
            this.mChangingConfigurations = $r1.mChangingConfigurations;
            if (this.mGroupName != null) {
                $r2.put(this.mGroupName, this);
            }
            this.mLocalMatrix.set($r1.mLocalMatrix);
            ArrayList $r3 = $r1.mChildren;
            for (int $i0 = 0; $i0 < $r3.size(); $i0++) {
                Object $r8 = $r3.get($i0);
                if ($r8 instanceof VGroup) {
                    this.mChildren.add(new VGroup((VGroup) $r8, $r2));
                } else {
                    VPath $r11;
                    VPath vFullPath;
                    if ($r8 instanceof VFullPath) {
                        $r11 = vFullPath;
                        vFullPath = new VFullPath((VFullPath) $r8);
                    } else if ($r8 instanceof VClipPath) {
                        $r11 = vFullPath;
                        vFullPath = new VClipPath((VClipPath) $r8);
                    } else {
                        throw new IllegalStateException("Unknown object in the tree!");
                    }
                    this.mChildren.add($r11);
                    if ($r11.mPathName != null) {
                        $r2.put($r11.mPathName, $r11);
                    }
                }
            }
        }

        public String getGroupName() throws  {
            return this.mGroupName;
        }

        public Matrix getLocalMatrix() throws  {
            return this.mLocalMatrix;
        }

        public void inflate(Resources $r1, AttributeSet $r2, Theme $r3, XmlPullParser $r4) throws  {
            TypedArray $r6 = VectorDrawableCommon.obtainAttributes($r1, $r3, $r2, AndroidResources.styleable_VectorDrawableGroup);
            updateStateFromTypedArray($r6, $r4);
            $r6.recycle();
        }

        private void updateStateFromTypedArray(TypedArray $r1, XmlPullParser $r2) throws  {
            this.mThemeAttrs = null;
            this.mRotate = TypedArrayUtils.getNamedFloat($r1, $r2, "rotation", 5, this.mRotate);
            this.mPivotX = $r1.getFloat(1, this.mPivotX);
            this.mPivotY = $r1.getFloat(2, this.mPivotY);
            this.mScaleX = TypedArrayUtils.getNamedFloat($r1, $r2, "scaleX", 3, this.mScaleX);
            this.mScaleY = TypedArrayUtils.getNamedFloat($r1, $r2, "scaleY", 4, this.mScaleY);
            this.mTranslateX = TypedArrayUtils.getNamedFloat($r1, $r2, "translateX", 6, this.mTranslateX);
            this.mTranslateY = TypedArrayUtils.getNamedFloat($r1, $r2, "translateY", 7, this.mTranslateY);
            String $r3 = $r1.getString(0);
            if ($r3 != null) {
                this.mGroupName = $r3;
            }
            updateLocalMatrix();
        }

        private void updateLocalMatrix() throws  {
            this.mLocalMatrix.reset();
            this.mLocalMatrix.postTranslate(-this.mPivotX, -this.mPivotY);
            this.mLocalMatrix.postScale(this.mScaleX, this.mScaleY);
            this.mLocalMatrix.postRotate(this.mRotate, 0.0f, 0.0f);
            this.mLocalMatrix.postTranslate(this.mTranslateX + this.mPivotX, this.mTranslateY + this.mPivotY);
        }

        public float getRotation() throws  {
            return this.mRotate;
        }

        public void setRotation(float $f0) throws  {
            if ($f0 != this.mRotate) {
                this.mRotate = $f0;
                updateLocalMatrix();
            }
        }

        public float getPivotX() throws  {
            return this.mPivotX;
        }

        public void setPivotX(float $f0) throws  {
            if ($f0 != this.mPivotX) {
                this.mPivotX = $f0;
                updateLocalMatrix();
            }
        }

        public float getPivotY() throws  {
            return this.mPivotY;
        }

        public void setPivotY(float $f0) throws  {
            if ($f0 != this.mPivotY) {
                this.mPivotY = $f0;
                updateLocalMatrix();
            }
        }

        public float getScaleX() throws  {
            return this.mScaleX;
        }

        public void setScaleX(float $f0) throws  {
            if ($f0 != this.mScaleX) {
                this.mScaleX = $f0;
                updateLocalMatrix();
            }
        }

        public float getScaleY() throws  {
            return this.mScaleY;
        }

        public void setScaleY(float $f0) throws  {
            if ($f0 != this.mScaleY) {
                this.mScaleY = $f0;
                updateLocalMatrix();
            }
        }

        public float getTranslateX() throws  {
            return this.mTranslateX;
        }

        public void setTranslateX(float $f0) throws  {
            if ($f0 != this.mTranslateX) {
                this.mTranslateX = $f0;
                updateLocalMatrix();
            }
        }

        public float getTranslateY() throws  {
            return this.mTranslateY;
        }

        public void setTranslateY(float $f0) throws  {
            if ($f0 != this.mTranslateY) {
                this.mTranslateY = $f0;
                updateLocalMatrix();
            }
        }
    }

    private static class VPathRenderer {
        private static final Matrix IDENTITY_MATRIX = new Matrix();
        float mBaseHeight;
        float mBaseWidth;
        private int mChangingConfigurations;
        private Paint mFillPaint;
        private final Matrix mFinalPathMatrix;
        private final Path mPath;
        private PathMeasure mPathMeasure;
        private final Path mRenderPath;
        int mRootAlpha;
        private final VGroup mRootGroup;
        String mRootName;
        private Paint mStrokePaint;
        final ArrayMap<String, Object> mVGTargetsMap;
        float mViewportHeight;
        float mViewportWidth;

        public VPathRenderer() throws  {
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mVGTargetsMap = new ArrayMap();
            this.mRootGroup = new VGroup();
            this.mPath = new Path();
            this.mRenderPath = new Path();
        }

        public void setRootAlpha(int $i0) throws  {
            this.mRootAlpha = $i0;
        }

        public int getRootAlpha() throws  {
            return this.mRootAlpha;
        }

        public void setAlpha(float $f0) throws  {
            setRootAlpha((int) (255.0f * $f0));
        }

        public float getAlpha() throws  {
            return ((float) getRootAlpha()) / 255.0f;
        }

        public VPathRenderer(VPathRenderer $r1) throws  {
            this.mFinalPathMatrix = new Matrix();
            this.mBaseWidth = 0.0f;
            this.mBaseHeight = 0.0f;
            this.mViewportWidth = 0.0f;
            this.mViewportHeight = 0.0f;
            this.mRootAlpha = 255;
            this.mRootName = null;
            this.mVGTargetsMap = new ArrayMap();
            this.mRootGroup = new VGroup($r1.mRootGroup, this.mVGTargetsMap);
            this.mPath = new Path($r1.mPath);
            this.mRenderPath = new Path($r1.mRenderPath);
            this.mBaseWidth = $r1.mBaseWidth;
            this.mBaseHeight = $r1.mBaseHeight;
            this.mViewportWidth = $r1.mViewportWidth;
            this.mViewportHeight = $r1.mViewportHeight;
            this.mChangingConfigurations = $r1.mChangingConfigurations;
            this.mRootAlpha = $r1.mRootAlpha;
            this.mRootName = $r1.mRootName;
            if ($r1.mRootName != null) {
                this.mVGTargetsMap.put($r1.mRootName, this);
            }
        }

        private void drawGroupTree(VGroup $r1, Matrix $r2, Canvas $r3, int $i0, int $i1, ColorFilter $r4) throws  {
            $r1.mStackedMatrix.set($r2);
            $r1.mStackedMatrix.preConcat($r1.mLocalMatrix);
            for (int $i2 = 0; $i2 < $r1.mChildren.size(); $i2++) {
                Object $r7 = $r1.mChildren.get($i2);
                if ($r7 instanceof VGroup) {
                    drawGroupTree((VGroup) $r7, $r1.mStackedMatrix, $r3, $i0, $i1, $r4);
                } else if ($r7 instanceof VPath) {
                    drawPath($r1, (VPath) $r7, $r3, $i0, $i1, $r4);
                }
            }
        }

        public void draw(Canvas $r1, int $i0, int $i1, ColorFilter $r2) throws  {
            drawGroupTree(this.mRootGroup, IDENTITY_MATRIX, $r1, $i0, $i1, $r2);
        }

        private void drawPath(VGroup $r1, VPath $r2, Canvas $r3, int $i0, int $i1, ColorFilter $r4) throws  {
            float $f1 = ((float) $i0) / this.mViewportWidth;
            float $f2 = ((float) $i1) / this.mViewportHeight;
            float $f0 = Math.min($f1, $f2);
            Matrix $r7 = $r1.mStackedMatrix;
            this.mFinalPathMatrix.set($r7);
            this.mFinalPathMatrix.postScale($f1, $f2);
            $f1 = getMatrixScale($r7);
            if ($f1 != 0.0f) {
                $r2.toPath(this.mPath);
                Path $r6 = this.mPath;
                this.mRenderPath.reset();
                if ($r2.isClipPath()) {
                    this.mRenderPath.addPath($r6, this.mFinalPathMatrix);
                    $r3.clipPath(this.mRenderPath, Op.REPLACE);
                    return;
                }
                Paint $r5;
                VFullPath $r11 = (VFullPath) $r2;
                if (!($r11.mTrimPathStart == 0.0f && $r11.mTrimPathEnd == 1.0f)) {
                    $f2 = $r11.mTrimPathStart;
                    float $f3 = $r11.mTrimPathOffset;
                    $f2 = ($f2 + $f3) % 1.0f;
                    float $f4 = ($r11.mTrimPathEnd + $r11.mTrimPathOffset) % 1.0f;
                    if (this.mPathMeasure == null) {
                        this.mPathMeasure = new PathMeasure();
                    }
                    this.mPathMeasure.setPath(this.mPath, false);
                    PathMeasure $r12 = this.mPathMeasure;
                    float $f32 = $r12.getLength();
                    $f2 *= $f32;
                    $f4 *= $f32;
                    $r6.reset();
                    if ($f2 > $f4) {
                        this.mPathMeasure.getSegment($f2, $f32, $r6, true);
                        this.mPathMeasure.getSegment(0.0f, $f4, $r6, true);
                    } else {
                        this.mPathMeasure.getSegment($f2, $f4, $r6, true);
                    }
                    $r6.rLineTo(0.0f, 0.0f);
                }
                this.mRenderPath.addPath($r6, this.mFinalPathMatrix);
                if ($r11.mFillColor != 0) {
                    if (this.mFillPaint == null) {
                        this.mFillPaint = new Paint();
                        this.mFillPaint.setStyle(Style.FILL);
                        this.mFillPaint.setAntiAlias(true);
                    }
                    $r5 = this.mFillPaint;
                    $r5.setColor(VectorDrawableCompat.applyAlpha($r11.mFillColor, $r11.mFillAlpha));
                    $r5.setColorFilter($r4);
                    $r3.drawPath(this.mRenderPath, $r5);
                }
                if ($r11.mStrokeColor != 0) {
                    if (this.mStrokePaint == null) {
                        this.mStrokePaint = new Paint();
                        this.mStrokePaint.setStyle(Style.STROKE);
                        this.mStrokePaint.setAntiAlias(true);
                    }
                    $r5 = this.mStrokePaint;
                    if ($r11.mStrokeLineJoin != null) {
                        $r5.setStrokeJoin($r11.mStrokeLineJoin);
                    }
                    if ($r11.mStrokeLineCap != null) {
                        $r5.setStrokeCap($r11.mStrokeLineCap);
                    }
                    $r5.setStrokeMiter($r11.mStrokeMiterlimit);
                    $r5.setColor(VectorDrawableCompat.applyAlpha($r11.mStrokeColor, $r11.mStrokeAlpha));
                    $r5.setColorFilter($r4);
                    Paint paint = $r5;
                    paint.setStrokeWidth($r11.mStrokeWidth * ($f0 * $f1));
                    $r3.drawPath(this.mRenderPath, $r5);
                }
            }
        }

        private static float cross(float $f0, float $f1, float $f2, float $f3) throws  {
            return ($f0 * $f3) - ($f1 * $f2);
        }

        private float getMatrixScale(Matrix $r1) throws  {
            float[] $r2 = new float[]{0.0f, 1.0f, 1.0f, 0.0f};
            $r1.mapVectors($r2);
            float $f0 = (float) Math.hypot((double) $r2[0], (double) $r2[1]);
            float $f1 = (float) Math.hypot((double) $r2[2], (double) $r2[3]);
            float $f2 = cross($r2[0], $r2[1], $r2[2], $r2[3]);
            $f0 = Math.max($f0, $f1);
            if ($f0 > 0.0f) {
                return Math.abs($f2) / $f0;
            }
            return 0.0f;
        }
    }

    private static class VectorDrawableCompatState extends ConstantState {
        boolean mAutoMirrored;
        boolean mCacheDirty;
        boolean mCachedAutoMirrored;
        Bitmap mCachedBitmap;
        int mCachedRootAlpha;
        int[] mCachedThemeAttrs;
        ColorStateList mCachedTint;
        Mode mCachedTintMode;
        int mChangingConfigurations;
        Paint mTempPaint;
        ColorStateList mTint;
        Mode mTintMode;
        VPathRenderer mVPathRenderer;

        public VectorDrawableCompatState(VectorDrawableCompatState $r1) throws  {
            this.mTint = null;
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE;
            if ($r1 != null) {
                this.mChangingConfigurations = $r1.mChangingConfigurations;
                this.mVPathRenderer = new VPathRenderer($r1.mVPathRenderer);
                if ($r1.mVPathRenderer.mFillPaint != null) {
                    this.mVPathRenderer.mFillPaint = new Paint($r1.mVPathRenderer.mFillPaint);
                }
                if ($r1.mVPathRenderer.mStrokePaint != null) {
                    this.mVPathRenderer.mStrokePaint = new Paint($r1.mVPathRenderer.mStrokePaint);
                }
                this.mTint = $r1.mTint;
                this.mTintMode = $r1.mTintMode;
                this.mAutoMirrored = $r1.mAutoMirrored;
            }
        }

        public void drawCachedBitmapWithRootAlpha(Canvas $r1, ColorFilter $r2, Rect $r3) throws  {
            $r1.drawBitmap(this.mCachedBitmap, null, $r3, getPaint($r2));
        }

        public boolean hasTranslucentRoot() throws  {
            return this.mVPathRenderer.getRootAlpha() < 255;
        }

        public Paint getPaint(ColorFilter $r1) throws  {
            if (!hasTranslucentRoot() && $r1 == null) {
                return null;
            }
            if (this.mTempPaint == null) {
                this.mTempPaint = new Paint();
                this.mTempPaint.setFilterBitmap(true);
            }
            this.mTempPaint.setAlpha(this.mVPathRenderer.getRootAlpha());
            this.mTempPaint.setColorFilter($r1);
            return this.mTempPaint;
        }

        public void updateCachedBitmap(int $i0, int $i1) throws  {
            this.mCachedBitmap.eraseColor(0);
            this.mVPathRenderer.draw(new Canvas(this.mCachedBitmap), $i0, $i1, null);
        }

        public void createCachedBitmapIfNeeded(int $i0, int $i1) throws  {
            if (this.mCachedBitmap == null || !canReuseBitmap($i0, $i1)) {
                this.mCachedBitmap = Bitmap.createBitmap($i0, $i1, Config.ARGB_8888);
                this.mCacheDirty = true;
            }
        }

        public boolean canReuseBitmap(int $i0, int $i1) throws  {
            return $i0 == this.mCachedBitmap.getWidth() && $i1 == this.mCachedBitmap.getHeight();
        }

        public boolean canReuseCache() throws  {
            return !this.mCacheDirty && this.mCachedTint == this.mTint && this.mCachedTintMode == this.mTintMode && this.mCachedAutoMirrored == this.mAutoMirrored && this.mCachedRootAlpha == this.mVPathRenderer.getRootAlpha();
        }

        public void updateCacheStates() throws  {
            this.mCachedTint = this.mTint;
            this.mCachedTintMode = this.mTintMode;
            this.mCachedRootAlpha = this.mVPathRenderer.getRootAlpha();
            this.mCachedAutoMirrored = this.mAutoMirrored;
            this.mCacheDirty = false;
        }

        public VectorDrawableCompatState() throws  {
            this.mTint = null;
            this.mTintMode = VectorDrawableCompat.DEFAULT_TINT_MODE;
            this.mVPathRenderer = new VPathRenderer();
        }

        public Drawable newDrawable() throws  {
            return new VectorDrawableCompat();
        }

        public Drawable newDrawable(Resources res) throws  {
            return new VectorDrawableCompat();
        }

        public int getChangingConfigurations() throws  {
            return this.mChangingConfigurations;
        }
    }

    private static class VectorDrawableDelegateState extends ConstantState {
        private final ConstantState mDelegateState;

        public VectorDrawableDelegateState(ConstantState $r1) throws  {
            this.mDelegateState = $r1;
        }

        public Drawable newDrawable() throws  {
            VectorDrawableCompat $r1 = new VectorDrawableCompat();
            $r1.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable();
            return $r1;
        }

        public Drawable newDrawable(Resources $r1) throws  {
            VectorDrawableCompat $r2 = new VectorDrawableCompat();
            $r2.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable($r1);
            return $r2;
        }

        public Drawable newDrawable(Resources $r1, Theme $r2) throws  {
            VectorDrawableCompat $r3 = new VectorDrawableCompat();
            $r3.mDelegateDrawable = (VectorDrawable) this.mDelegateState.newDrawable($r1, $r2);
            return $r3;
        }

        public boolean canApplyTheme() throws  {
            return this.mDelegateState.canApplyTheme();
        }

        public int getChangingConfigurations() throws  {
            return this.mDelegateState.getChangingConfigurations();
        }
    }

    private void inflateInternal(android.content.res.Resources r28, org.xmlpull.v1.XmlPullParser r29, android.util.AttributeSet r30, android.content.res.Resources.Theme r31) throws org.xmlpull.v1.XmlPullParserException, java.io.IOException {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:20:0x00d0 in {9, 10, 11, 16, 18, 19, 21, 26, 36, 38, 39} preds:[]
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
        r27 = this;
        r0 = r27;
        r5 = r0.mVectorState;
        r6 = r5.mVPathRenderer;
        r7 = 1;
        r8 = new java.util.Stack;
        r8.<init>();
        r9 = r6.mRootGroup;
        r8.push(r9);
        r0 = r29;
        r10 = r0.getEventType();
    L_0x0019:
        r11 = 1;
        if (r10 == r11) goto L_0x012e;
    L_0x001c:
        r11 = 2;
        if (r10 != r11) goto L_0x0117;
    L_0x001f:
        r0 = r29;
        r12 = r0.getName();
        r13 = r8.peek();
        r15 = r13;
        r15 = (android.support.graphics.drawable.VectorDrawableCompat.VGroup) r15;
        r14 = r15;
        r16 = "path";
        r0 = r16;
        r17 = r0.equals(r12);
        if (r17 == 0) goto L_0x0080;
    L_0x0037:
        r18 = new android.support.graphics.drawable.VectorDrawableCompat$VFullPath;
        r0 = r18;
        r0.<init>();
        r0 = r18;
        r1 = r28;
        r2 = r30;
        r3 = r31;
        r4 = r29;
        r0.inflate(r1, r2, r3, r4);
        r0 = r14.mChildren;
        r19 = r0;
        r1 = r18;
        r0.add(r1);
        r0 = r18;
        r12 = r0.getPathName();
        if (r12 == 0) goto L_0x006d;
    L_0x005c:
        r0 = r6.mVGTargetsMap;
        r20 = r0;
        r0 = r18;
        r12 = r0.getPathName();
        r0 = r20;
        r1 = r18;
        r0.put(r12, r1);
    L_0x006d:
        r7 = 0;
        r10 = r5.mChangingConfigurations;
        r0 = r18;
        r0 = r0.mChangingConfigurations;
        r21 = r0;
        r10 = r10 | r0;
        r5.mChangingConfigurations = r10;
    L_0x0079:
        r0 = r29;
        r10 = r0.next();
        goto L_0x0019;
    L_0x0080:
        r16 = "clip-path";
        r0 = r16;
        r17 = r0.equals(r12);
        if (r17 == 0) goto L_0x00d4;
    L_0x008a:
        r22 = new android.support.graphics.drawable.VectorDrawableCompat$VClipPath;
        r0 = r22;
        r0.<init>();
        r0 = r22;
        r1 = r28;
        r2 = r30;
        r3 = r31;
        r4 = r29;
        r0.inflate(r1, r2, r3, r4);
        r0 = r14.mChildren;
        r19 = r0;
        r1 = r22;
        r0.add(r1);
        r0 = r22;
        r12 = r0.getPathName();
        if (r12 == 0) goto L_0x00c0;
    L_0x00af:
        r0 = r6.mVGTargetsMap;
        r20 = r0;
        r0 = r22;
        r12 = r0.getPathName();
        r0 = r20;
        r1 = r22;
        r0.put(r12, r1);
    L_0x00c0:
        r10 = r5.mChangingConfigurations;
        goto L_0x00c6;
    L_0x00c3:
        goto L_0x0079;
    L_0x00c6:
        r0 = r22;
        r0 = r0.mChangingConfigurations;
        r21 = r0;
        r10 = r10 | r0;
        r5.mChangingConfigurations = r10;
        goto L_0x0079;
        goto L_0x00d4;
    L_0x00d1:
        goto L_0x0079;
    L_0x00d4:
        r16 = "group";
        r0 = r16;
        r17 = r0.equals(r12);
        if (r17 == 0) goto L_0x0079;
    L_0x00de:
        r9 = new android.support.graphics.drawable.VectorDrawableCompat$VGroup;
        r9.<init>();
        r0 = r28;
        r1 = r30;
        r2 = r31;
        r3 = r29;
        r9.inflate(r0, r1, r2, r3);
        r0 = r14.mChildren;
        r19 = r0;
        r0.add(r9);
        r8.push(r9);
        r12 = r9.getGroupName();
        if (r12 == 0) goto L_0x010b;
    L_0x00fe:
        r0 = r6.mVGTargetsMap;
        r20 = r0;
        r12 = r9.getGroupName();
        r0 = r20;
        r0.put(r12, r9);
    L_0x010b:
        r10 = r5.mChangingConfigurations;
        r21 = r9.mChangingConfigurations;
        r0 = r21;
        r10 = r10 | r0;
        r5.mChangingConfigurations = r10;
        goto L_0x00c3;
    L_0x0117:
        r11 = 3;
        if (r10 != r11) goto L_0x0079;
    L_0x011a:
        r0 = r29;
        r12 = r0.getName();
        r16 = "group";
        r0 = r16;
        r17 = r0.equals(r12);
        if (r17 == 0) goto L_0x0079;
    L_0x012a:
        r8.pop();
        goto L_0x00d1;
    L_0x012e:
        if (r7 == 0) goto L_0x0182;
    L_0x0130:
        r23 = new java.lang.StringBuffer;
        r0 = r23;
        r0.<init>();
        r0 = r23;
        r10 = r0.length();
        if (r10 <= 0) goto L_0x0148;
    L_0x013f:
        r24 = " or ";
        r0 = r23;
        r1 = r24;
        r0.append(r1);
    L_0x0148:
        r24 = "path";
        r0 = r23;
        r1 = r24;
        r0.append(r1);
        r25 = new org.xmlpull.v1.XmlPullParserException;
        r26 = new java.lang.StringBuilder;
        r0 = r26;
        r0.<init>();
        r24 = "no ";
        r0 = r26;
        r1 = r24;
        r26 = r0.append(r1);
        r0 = r26;
        r1 = r23;
        r26 = r0.append(r1);
        r24 = " defined";
        r0 = r26;
        r1 = r24;
        r26 = r0.append(r1);
        r0 = r26;
        r12 = r0.toString();
        r0 = r25;
        r0.<init>(r12);
        throw r25;
    L_0x0182:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.graphics.drawable.VectorDrawableCompat.inflateInternal(android.content.res.Resources, org.xmlpull.v1.XmlPullParser, android.util.AttributeSet, android.content.res.Resources$Theme):void");
    }

    private boolean needMirroring() throws  {
        return false;
    }

    public /* bridge */ /* synthetic */ void applyTheme(Theme $r1) throws  {
        super.applyTheme($r1);
    }

    public /* bridge */ /* synthetic */ void clearColorFilter() throws  {
        super.clearColorFilter();
    }

    public /* bridge */ /* synthetic */ ColorFilter getColorFilter() throws  {
        return super.getColorFilter();
    }

    public /* bridge */ /* synthetic */ Drawable getCurrent() throws  {
        return super.getCurrent();
    }

    public /* bridge */ /* synthetic */ int getLayoutDirection() throws  {
        return super.getLayoutDirection();
    }

    public /* bridge */ /* synthetic */ int getMinimumHeight() throws  {
        return super.getMinimumHeight();
    }

    public /* bridge */ /* synthetic */ int getMinimumWidth() throws  {
        return super.getMinimumWidth();
    }

    public /* bridge */ /* synthetic */ boolean getPadding(Rect $r1) throws  {
        return super.getPadding($r1);
    }

    public /* bridge */ /* synthetic */ int[] getState() throws  {
        return super.getState();
    }

    public /* bridge */ /* synthetic */ Region getTransparentRegion() throws  {
        return super.getTransparentRegion();
    }

    public /* bridge */ /* synthetic */ boolean isAutoMirrored() throws  {
        return super.isAutoMirrored();
    }

    public /* bridge */ /* synthetic */ void jumpToCurrentState() throws  {
        super.jumpToCurrentState();
    }

    public /* bridge */ /* synthetic */ void setAutoMirrored(boolean $z0) throws  {
        super.setAutoMirrored($z0);
    }

    public /* bridge */ /* synthetic */ void setChangingConfigurations(int $i0) throws  {
        super.setChangingConfigurations($i0);
    }

    public /* bridge */ /* synthetic */ void setColorFilter(int $i0, Mode $r1) throws  {
        super.setColorFilter($i0, $r1);
    }

    public /* bridge */ /* synthetic */ void setFilterBitmap(boolean $z0) throws  {
        super.setFilterBitmap($z0);
    }

    public /* bridge */ /* synthetic */ void setHotspot(float $f0, float $f1) throws  {
        super.setHotspot($f0, $f1);
    }

    public /* bridge */ /* synthetic */ void setHotspotBounds(int $i0, int $i1, int $i2, int $i3) throws  {
        super.setHotspotBounds($i0, $i1, $i2, $i3);
    }

    public /* bridge */ /* synthetic */ boolean setState(int[] $r1) throws  {
        return super.setState($r1);
    }

    private VectorDrawableCompat() throws  {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = new VectorDrawableCompatState();
    }

    private VectorDrawableCompat(@NonNull VectorDrawableCompatState $r1) throws  {
        this.mAllowCaching = true;
        this.mTmpFloats = new float[9];
        this.mTmpMatrix = new Matrix();
        this.mTmpBounds = new Rect();
        this.mVectorState = $r1;
        this.mTintFilter = updateTintFilter(this.mTintFilter, $r1.mTint, $r1.mTintMode);
    }

    public Drawable mutate() throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.mutate();
            return this;
        } else if (this.mMutated || super.mutate() != this) {
            return this;
        } else {
            this.mVectorState = new VectorDrawableCompatState(this.mVectorState);
            this.mMutated = true;
            return this;
        }
    }

    Object getTargetByName(String $r1) throws  {
        return this.mVectorState.mVPathRenderer.mVGTargetsMap.get($r1);
    }

    public ConstantState getConstantState() throws  {
        if (this.mDelegateDrawable != null) {
            return new VectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
        }
        this.mVectorState.mChangingConfigurations = getChangingConfigurations();
        return this.mVectorState;
    }

    public void draw(Canvas $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.draw($r1);
            return;
        }
        copyBounds(this.mTmpBounds);
        if (this.mTmpBounds.width() > 0 && this.mTmpBounds.height() > 0) {
            ColorFilter $r4 = this.mColorFilter == null ? this.mTintFilter : this.mColorFilter;
            $r1.getMatrix(this.mTmpMatrix);
            this.mTmpMatrix.getValues(this.mTmpFloats);
            float $f0 = Math.abs(this.mTmpFloats[0]);
            float $f1 = Math.abs(this.mTmpFloats[4]);
            float $f2 = Math.abs(this.mTmpFloats[1]);
            float $f3 = Math.abs(this.mTmpFloats[3]);
            if (!($f2 == 0.0f && $f3 == 0.0f)) {
                $f0 = 1.0f;
                $f1 = 1.0f;
            }
            int $i2 = (int) (((float) this.mTmpBounds.height()) * $f1);
            int $i0 = Math.min(2048, (int) (((float) this.mTmpBounds.width()) * $f0));
            $i2 = Math.min(2048, $i2);
            if ($i0 > 0 && $i2 > 0) {
                int $i3 = $r1.save();
                int $i4 = this.mTmpBounds.left;
                $f0 = (float) $i4;
                $i4 = this.mTmpBounds.top;
                $r1.translate($f0, (float) $i4);
                if (needMirroring()) {
                    $r1.translate((float) this.mTmpBounds.width(), 0.0f);
                    $r1.scale(-1.0f, 1.0f);
                }
                this.mTmpBounds.offsetTo(0, 0);
                VectorDrawableCompatState $r7 = this.mVectorState;
                $r7.createCachedBitmapIfNeeded($i0, $i2);
                if (this.mAllowCaching) {
                    $r7 = this.mVectorState;
                    if (!$r7.canReuseCache()) {
                        $r7 = this.mVectorState;
                        $r7.updateCachedBitmap($i0, $i2);
                        $r7 = this.mVectorState;
                        $r7.updateCacheStates();
                    }
                } else {
                    $r7 = this.mVectorState;
                    $r7.updateCachedBitmap($i0, $i2);
                }
                this.mVectorState.drawCachedBitmapWithRootAlpha($r1, $r4, this.mTmpBounds);
                $r1.restoreToCount($i3);
            }
        }
    }

    public int getAlpha() throws  {
        if (this.mDelegateDrawable != null) {
            return DrawableCompat.getAlpha(this.mDelegateDrawable);
        }
        return this.mVectorState.mVPathRenderer.getRootAlpha();
    }

    public void setAlpha(int $i0) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setAlpha($i0);
        } else if (this.mVectorState.mVPathRenderer.getRootAlpha() != $i0) {
            this.mVectorState.mVPathRenderer.setRootAlpha($i0);
            invalidateSelf();
        }
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter($r1);
            return;
        }
        this.mColorFilter = $r1;
        invalidateSelf();
    }

    PorterDuffColorFilter updateTintFilter(PorterDuffColorFilter tintFilter, ColorStateList $r2, Mode $r3) throws  {
        return ($r2 == null || $r3 == null) ? null : new PorterDuffColorFilter($r2.getColorForState(getState(), 0), $r3);
    }

    public void setTint(int $i0) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTint(this.mDelegateDrawable, $i0);
        } else {
            setTintList(ColorStateList.valueOf($i0));
        }
    }

    public void setTintList(ColorStateList $r1) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintList(this.mDelegateDrawable, $r1);
            return;
        }
        VectorDrawableCompatState $r2 = this.mVectorState;
        if ($r2.mTint != $r1) {
            $r2.mTint = $r1;
            this.mTintFilter = updateTintFilter(this.mTintFilter, $r1, $r2.mTintMode);
            invalidateSelf();
        }
    }

    public void setTintMode(Mode $r1) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintMode(this.mDelegateDrawable, $r1);
            return;
        }
        VectorDrawableCompatState $r2 = this.mVectorState;
        if ($r2.mTintMode != $r1) {
            $r2.mTintMode = $r1;
            this.mTintFilter = updateTintFilter(this.mTintFilter, $r2.mTint, $r1);
            invalidateSelf();
        }
    }

    public boolean isStateful() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.isStateful();
        }
        return super.isStateful() || !(this.mVectorState == null || this.mVectorState.mTint == null || !this.mVectorState.mTint.isStateful());
    }

    protected boolean onStateChange(int[] $r1) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState($r1);
        }
        VectorDrawableCompatState $r2 = this.mVectorState;
        if ($r2.mTint == null || $r2.mTintMode == null) {
            return false;
        }
        this.mTintFilter = updateTintFilter(this.mTintFilter, $r2.mTint, $r2.mTintMode);
        invalidateSelf();
        return true;
    }

    public int getOpacity() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getOpacity();
        }
        return -3;
    }

    public int getIntrinsicWidth() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getIntrinsicWidth();
        }
        return (int) this.mVectorState.mVPathRenderer.mBaseWidth;
    }

    public int getIntrinsicHeight() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getIntrinsicHeight();
        }
        return (int) this.mVectorState.mVPathRenderer.mBaseHeight;
    }

    public boolean canApplyTheme() throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.canApplyTheme(this.mDelegateDrawable);
        }
        return false;
    }

    public float getPixelSize() throws  {
        if ((this.mVectorState == null && this.mVectorState.mVPathRenderer == null) || this.mVectorState.mVPathRenderer.mBaseWidth == 0.0f || this.mVectorState.mVPathRenderer.mBaseHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportHeight == 0.0f || this.mVectorState.mVPathRenderer.mViewportWidth == 0.0f) {
            return 1.0f;
        }
        float $f1 = this.mVectorState.mVPathRenderer.mBaseWidth;
        float $f0 = this.mVectorState.mVPathRenderer.mBaseHeight;
        return Math.min(this.mVectorState.mVPathRenderer.mViewportWidth / $f1, this.mVectorState.mVPathRenderer.mViewportHeight / $f0);
    }

    @Nullable
    public static VectorDrawableCompat create(@NonNull Resources $r0, @DrawableRes int $i0, @Nullable Theme $r1) throws  {
        if (VERSION.SDK_INT >= 23) {
            VectorDrawableCompat $r2 = new VectorDrawableCompat();
            $r2.mDelegateDrawable = ResourcesCompat.getDrawable($r0, $i0, $r1);
            $r2.mCachedConstantStateDelegate = new VectorDrawableDelegateState($r2.mDelegateDrawable.getConstantState());
            return $r2;
        }
        try {
            XmlResourceParser $r6 = $r0.getXml($i0);
            AttributeSet $r7 = Xml.asAttributeSet($r6);
            do {
                $i0 = $r6.next();
                if ($i0 == 2) {
                    break;
                }
            } while ($i0 != 1);
            if ($i0 == 2) {
                return createFromXmlInner($r0, $r6, $r7, $r1);
            }
            try {
                throw new XmlPullParserException("No start tag found");
            } catch (XmlPullParserException $r8) {
                Log.e(LOGTAG, "parser error", $r8);
            }
        } catch (IOException $r9) {
            Log.e(LOGTAG, "parser error", $r9);
            return null;
        }
    }

    public static VectorDrawableCompat createFromXmlInner(Resources $r0, XmlPullParser $r1, AttributeSet $r2, Theme $r3) throws XmlPullParserException, IOException {
        VectorDrawableCompat $r4 = new VectorDrawableCompat();
        $r4.inflate($r0, $r1, $r2, $r3);
        return $r4;
    }

    private static int applyAlpha(int $i1, float $f0) throws  {
        return ($i1 & ViewCompat.MEASURED_SIZE_MASK) | (((int) (((float) Color.alpha($i1)) * $f0)) << 24);
    }

    public void inflate(Resources $r1, XmlPullParser $r2, AttributeSet $r3) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.inflate($r1, $r2, $r3);
        } else {
            inflate($r1, $r2, $r3, null);
        }
    }

    public void inflate(Resources $r1, XmlPullParser $r2, AttributeSet $r3, Theme $r4) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.inflate(this.mDelegateDrawable, $r1, $r2, $r3, $r4);
            return;
        }
        VectorDrawableCompatState $r6 = this.mVectorState;
        $r6.mVPathRenderer = new VPathRenderer();
        TypedArray $r11 = VectorDrawableCommon.obtainAttributes($r1, $r4, $r3, AndroidResources.styleable_VectorDrawableTypeArray);
        updateStateFromTypedArray($r11, $r2);
        $r11.recycle();
        $r6.mChangingConfigurations = getChangingConfigurations();
        $r6.mCacheDirty = true;
        inflateInternal($r1, $r2, $r3, $r4);
        this.mTintFilter = updateTintFilter(this.mTintFilter, $r6.mTint, $r6.mTintMode);
    }

    private static Mode parseTintModeCompat(int $i0, Mode $r0) throws  {
        switch ($i0) {
            case 3:
                return Mode.SRC_OVER;
            case 4:
            case 6:
            case 7:
            case 8:
            case 10:
            case 11:
            case 12:
            case 13:
                break;
            case 5:
                return Mode.SRC_IN;
            case 9:
                return Mode.SRC_ATOP;
            case 14:
                return Mode.MULTIPLY;
            case 15:
                return Mode.SCREEN;
            case 16:
                return Mode.ADD;
            default:
                break;
        }
        return $r0;
    }

    private void updateStateFromTypedArray(TypedArray $r1, XmlPullParser $r2) throws XmlPullParserException {
        VectorDrawableCompatState $r4 = this.mVectorState;
        VPathRenderer $r3 = $r4.mVPathRenderer;
        $r4.mTintMode = parseTintModeCompat(TypedArrayUtils.getNamedInt($r1, $r2, "tintMode", 6, -1), Mode.SRC_IN);
        ColorStateList $r6 = $r1.getColorStateList(1);
        if ($r6 != null) {
            $r4.mTint = $r6;
        }
        TypedArray typedArray = $r1;
        XmlPullParser xmlPullParser = $r2;
        $r4.mAutoMirrored = TypedArrayUtils.getNamedBoolean(typedArray, xmlPullParser, "autoMirrored", 5, $r4.mAutoMirrored);
        typedArray = $r1;
        xmlPullParser = $r2;
        $r3.mViewportWidth = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportWidth", 7, $r3.mViewportWidth);
        typedArray = $r1;
        xmlPullParser = $r2;
        $r3.mViewportHeight = TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "viewportHeight", 8, $r3.mViewportHeight);
        if ($r3.mViewportWidth <= 0.0f) {
            throw new XmlPullParserException($r1.getPositionDescription() + "<vector> tag requires viewportWidth > 0");
        } else if ($r3.mViewportHeight <= 0.0f) {
            throw new XmlPullParserException($r1.getPositionDescription() + "<vector> tag requires viewportHeight > 0");
        } else {
            $r3.mBaseWidth = $r1.getDimension(3, $r3.mBaseWidth);
            $r3.mBaseHeight = $r1.getDimension(2, $r3.mBaseHeight);
            if ($r3.mBaseWidth <= 0.0f) {
                throw new XmlPullParserException($r1.getPositionDescription() + "<vector> tag requires width > 0");
            } else if ($r3.mBaseHeight <= 0.0f) {
                throw new XmlPullParserException($r1.getPositionDescription() + "<vector> tag requires height > 0");
            } else {
                typedArray = $r1;
                xmlPullParser = $r2;
                $r3.setAlpha(TypedArrayUtils.getNamedFloat(typedArray, xmlPullParser, "alpha", 4, $r3.getAlpha()));
                String $r9 = $r1.getString(0);
                if ($r9 != null) {
                    $r3.mRootName = $r9;
                    ArrayMap arrayMap = $r3.mVGTargetsMap;
                    ArrayMap $r10 = arrayMap;
                    arrayMap.put($r9, $r3);
                }
            }
        }
    }

    private void printGroupTree(VGroup $r1, int $i0) throws  {
        int $i1;
        String $r2 = "";
        for ($i1 = 0; $i1 < $i0; $i1++) {
            $r2 = $r2 + "    ";
        }
        Log.v(LOGTAG, $r2 + "current group is :" + $r1.getGroupName() + " rotation is " + $r1.mRotate);
        Log.v(LOGTAG, $r2 + "matrix is :" + $r1.getLocalMatrix().toString());
        for ($i1 = 0; $i1 < $r1.mChildren.size(); $i1++) {
            Object $r7 = $r1.mChildren.get($i1);
            if ($r7 instanceof VGroup) {
                printGroupTree((VGroup) $r7, $i0 + 1);
            } else {
                ((VPath) $r7).printVPath($i0 + 1);
            }
        }
    }

    void setAllowCaching(boolean $z0) throws  {
        this.mAllowCaching = $z0;
    }

    protected void onBoundsChange(Rect $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds($r1);
        }
    }

    public int getChangingConfigurations() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.mVectorState.getChangingConfigurations();
    }

    public void invalidateSelf() throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.invalidateSelf();
        } else {
            super.invalidateSelf();
        }
    }

    public void scheduleSelf(Runnable $r1, long $l0) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.scheduleSelf($r1, $l0);
        } else {
            super.scheduleSelf($r1, $l0);
        }
    }

    public boolean setVisible(boolean $z0, boolean $z1) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setVisible($z0, $z1);
        }
        return super.setVisible($z0, $z1);
    }

    public void unscheduleSelf(Runnable $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.unscheduleSelf($r1);
        } else {
            super.unscheduleSelf($r1);
        }
    }
}
