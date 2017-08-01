package android.support.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.Callback;
import android.graphics.drawable.Drawable.ConstantState;
import android.os.Build.VERSION;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.util.AttributeSet;
import android.util.Log;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

@TargetApi(21)
public class AnimatedVectorDrawableCompat extends VectorDrawableCommon implements Animatable {
    private static final String ANIMATED_VECTOR = "animated-vector";
    private static final boolean DBG_ANIMATION_VECTOR_DRAWABLE = false;
    private static final String LOGTAG = "AnimatedVDCompat";
    private static final String TARGET = "target";
    private AnimatedVectorDrawableCompatState mAnimatedVectorState;
    private ArgbEvaluator mArgbEvaluator;
    AnimatedVectorDrawableDelegateState mCachedConstantStateDelegate;
    private final Callback mCallback;
    private Context mContext;

    class C00001 implements Callback {
        C00001() throws  {
        }

        public void invalidateDrawable(Drawable who) throws  {
            AnimatedVectorDrawableCompat.this.invalidateSelf();
        }

        public void scheduleDrawable(Drawable who, Runnable $r2, long $l0) throws  {
            AnimatedVectorDrawableCompat.this.scheduleSelf($r2, $l0);
        }

        public void unscheduleDrawable(Drawable who, Runnable $r2) throws  {
            AnimatedVectorDrawableCompat.this.unscheduleSelf($r2);
        }
    }

    private static class AnimatedVectorDrawableCompatState extends ConstantState {
        ArrayList<Animator> mAnimators;
        int mChangingConfigurations;
        ArrayMap<Animator, String> mTargetNameMap;
        VectorDrawableCompat mVectorDrawable;

        public AnimatedVectorDrawableCompatState(Context context, AnimatedVectorDrawableCompatState $r2, Callback $r3, Resources $r4) throws  {
            ConstantState constantState = this;
            if ($r2 != null) {
                this.mChangingConfigurations = $r2.mChangingConfigurations;
                if ($r2.mVectorDrawable != null) {
                    ConstantState $r6 = $r2.mVectorDrawable.getConstantState();
                    if ($r4 != null) {
                        this.mVectorDrawable = (VectorDrawableCompat) $r6.newDrawable($r4);
                    } else {
                        this.mVectorDrawable = (VectorDrawableCompat) $r6.newDrawable();
                    }
                    this.mVectorDrawable = (VectorDrawableCompat) this.mVectorDrawable.mutate();
                    this.mVectorDrawable.setCallback($r3);
                    this.mVectorDrawable.setBounds($r2.mVectorDrawable.getBounds());
                    this.mVectorDrawable.setAllowCaching(false);
                }
                if ($r2.mAnimators != null) {
                    int $i0 = $r2.mAnimators.size();
                    this.mAnimators = new ArrayList($i0);
                    this.mTargetNameMap = new ArrayMap($i0);
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        Animator $r13 = (Animator) $r2.mAnimators.get($i1);
                        Animator $r14 = $r13.clone();
                        String $r15 = (String) $r2.mTargetNameMap.get($r13);
                        $r14.setTarget(this.mVectorDrawable.getTargetByName($r15));
                        this.mAnimators.add($r14);
                        this.mTargetNameMap.put($r14, $r15);
                    }
                }
            }
        }

        public Drawable newDrawable() throws  {
            throw new IllegalStateException("No constant state support for SDK < 23.");
        }

        public Drawable newDrawable(Resources res) throws  {
            throw new IllegalStateException("No constant state support for SDK < 23.");
        }

        public int getChangingConfigurations() throws  {
            return this.mChangingConfigurations;
        }
    }

    private static class AnimatedVectorDrawableDelegateState extends ConstantState {
        private final ConstantState mDelegateState;

        public AnimatedVectorDrawableDelegateState(ConstantState $r1) throws  {
            this.mDelegateState = $r1;
        }

        public Drawable newDrawable() throws  {
            AnimatedVectorDrawableCompat $r1 = new AnimatedVectorDrawableCompat();
            $r1.mDelegateDrawable = this.mDelegateState.newDrawable();
            $r1.mDelegateDrawable.setCallback($r1.mCallback);
            return $r1;
        }

        public Drawable newDrawable(Resources $r1) throws  {
            AnimatedVectorDrawableCompat $r2 = new AnimatedVectorDrawableCompat();
            $r2.mDelegateDrawable = this.mDelegateState.newDrawable($r1);
            $r2.mDelegateDrawable.setCallback($r2.mCallback);
            return $r2;
        }

        public Drawable newDrawable(Resources $r1, Theme $r2) throws  {
            AnimatedVectorDrawableCompat $r3 = new AnimatedVectorDrawableCompat();
            $r3.mDelegateDrawable = this.mDelegateState.newDrawable($r1, $r2);
            $r3.mDelegateDrawable.setCallback($r3.mCallback);
            return $r3;
        }

        public boolean canApplyTheme() throws  {
            return this.mDelegateState.canApplyTheme();
        }

        public int getChangingConfigurations() throws  {
            return this.mDelegateState.getChangingConfigurations();
        }
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

    private AnimatedVectorDrawableCompat() throws  {
        this(null, null, null);
    }

    private AnimatedVectorDrawableCompat(@Nullable Context $r1) throws  {
        this($r1, null, null);
    }

    private AnimatedVectorDrawableCompat(@Nullable Context $r1, @Nullable AnimatedVectorDrawableCompatState $r2, @Nullable Resources $r3) throws  {
        this.mArgbEvaluator = null;
        this.mCallback = new C00001();
        this.mContext = $r1;
        if ($r2 != null) {
            this.mAnimatedVectorState = $r2;
        } else {
            this.mAnimatedVectorState = new AnimatedVectorDrawableCompatState($r1, $r2, this.mCallback, $r3);
        }
    }

    public Drawable mutate() throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.mutate();
            return this;
        }
        throw new IllegalStateException("Mutate() is not supported for older platform");
    }

    @Nullable
    public static AnimatedVectorDrawableCompat create(@NonNull Context $r0, @DrawableRes int $i0) throws  {
        if (VERSION.SDK_INT >= 23) {
            AnimatedVectorDrawableCompat $r1 = new AnimatedVectorDrawableCompat($r0);
            $r1.mDelegateDrawable = ResourcesCompat.getDrawable($r0.getResources(), $i0, $r0.getTheme());
            $r1.mDelegateDrawable.setCallback($r1.mCallback);
            $r1.mCachedConstantStateDelegate = new AnimatedVectorDrawableDelegateState($r1.mDelegateDrawable.getConstantState());
            return $r1;
        }
        try {
            XmlResourceParser $r8 = $r0.getResources().getXml($i0);
            AttributeSet $r9 = Xml.asAttributeSet($r8);
            do {
                $i0 = $r8.next();
                if ($i0 == 2) {
                    break;
                }
            } while ($i0 != 1);
            if ($i0 != 2) {
                try {
                    throw new XmlPullParserException("No start tag found");
                } catch (XmlPullParserException $r10) {
                    Log.e(LOGTAG, "parser error", $r10);
                }
            } else {
                return createFromXmlInner($r0, $r0.getResources(), $r8, $r9, $r0.getTheme());
            }
        } catch (Throwable $r11) {
            Log.e(LOGTAG, "parser error", $r11);
            return null;
        }
    }

    public static AnimatedVectorDrawableCompat createFromXmlInner(Context $r0, Resources $r1, XmlPullParser $r2, AttributeSet $r3, Theme $r4) throws XmlPullParserException, IOException {
        AnimatedVectorDrawableCompat $r5 = new AnimatedVectorDrawableCompat($r0);
        $r5.inflate($r1, $r2, $r3, $r4);
        return $r5;
    }

    public ConstantState getConstantState() throws  {
        if (this.mDelegateDrawable != null) {
            return new AnimatedVectorDrawableDelegateState(this.mDelegateDrawable.getConstantState());
        }
        return null;
    }

    public int getChangingConfigurations() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getChangingConfigurations();
        }
        return super.getChangingConfigurations() | this.mAnimatedVectorState.mChangingConfigurations;
    }

    public void draw(Canvas $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.draw($r1);
            return;
        }
        this.mAnimatedVectorState.mVectorDrawable.draw($r1);
        if (isStarted()) {
            invalidateSelf();
        }
    }

    protected void onBoundsChange(Rect $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setBounds($r1);
        } else {
            this.mAnimatedVectorState.mVectorDrawable.setBounds($r1);
        }
    }

    protected boolean onStateChange(int[] $r1) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setState($r1);
        }
        return this.mAnimatedVectorState.mVectorDrawable.setState($r1);
    }

    protected boolean onLevelChange(int $i0) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setLevel($i0);
        }
        return this.mAnimatedVectorState.mVectorDrawable.setLevel($i0);
    }

    public int getAlpha() throws  {
        if (this.mDelegateDrawable != null) {
            return DrawableCompat.getAlpha(this.mDelegateDrawable);
        }
        return this.mAnimatedVectorState.mVectorDrawable.getAlpha();
    }

    public void setAlpha(int $i0) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setAlpha($i0);
        } else {
            this.mAnimatedVectorState.mVectorDrawable.setAlpha($i0);
        }
    }

    public void setColorFilter(ColorFilter $r1) throws  {
        if (this.mDelegateDrawable != null) {
            this.mDelegateDrawable.setColorFilter($r1);
        } else {
            this.mAnimatedVectorState.mVectorDrawable.setColorFilter($r1);
        }
    }

    public void setTint(int $i0) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTint(this.mDelegateDrawable, $i0);
        } else {
            this.mAnimatedVectorState.mVectorDrawable.setTint($i0);
        }
    }

    public void setTintList(ColorStateList $r1) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintList(this.mDelegateDrawable, $r1);
        } else {
            this.mAnimatedVectorState.mVectorDrawable.setTintList($r1);
        }
    }

    public void setTintMode(Mode $r1) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.setTintMode(this.mDelegateDrawable, $r1);
        } else {
            this.mAnimatedVectorState.mVectorDrawable.setTintMode($r1);
        }
    }

    public boolean setVisible(boolean $z0, boolean $z1) throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.setVisible($z0, $z1);
        }
        this.mAnimatedVectorState.mVectorDrawable.setVisible($z0, $z1);
        return super.setVisible($z0, $z1);
    }

    public boolean isStateful() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.isStateful();
        }
        return this.mAnimatedVectorState.mVectorDrawable.isStateful();
    }

    public int getOpacity() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getOpacity();
        }
        return this.mAnimatedVectorState.mVectorDrawable.getOpacity();
    }

    public int getIntrinsicWidth() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getIntrinsicWidth();
        }
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicWidth();
    }

    public int getIntrinsicHeight() throws  {
        if (this.mDelegateDrawable != null) {
            return this.mDelegateDrawable.getIntrinsicHeight();
        }
        return this.mAnimatedVectorState.mVectorDrawable.getIntrinsicHeight();
    }

    static TypedArray obtainAttributes(Resources $r0, Theme $r1, AttributeSet $r2, int[] $r3) throws  {
        if ($r1 == null) {
            return $r0.obtainAttributes($r2, $r3);
        }
        return $r1.obtainStyledAttributes($r2, $r3, 0, 0);
    }

    public void inflate(Resources $r1, XmlPullParser $r2, AttributeSet $r3, Theme $r4) throws XmlPullParserException, IOException {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.inflate(this.mDelegateDrawable, $r1, $r2, $r3, $r4);
            return;
        }
        int $i0 = $r2.getEventType();
        while ($i0 != 1) {
            if ($i0 == 2) {
                String $r6 = $r2.getName();
                TypedArray $r9;
                if (ANIMATED_VECTOR.equals($r6)) {
                    $r9 = obtainAttributes($r1, $r4, $r3, AndroidResources.styleable_AnimatedVectorDrawable);
                    $i0 = $r9.getResourceId(0, 0);
                    if ($i0 != 0) {
                        VectorDrawableCompat $r10 = VectorDrawableCompat.create($r1, $i0, $r4);
                        $r10.setAllowCaching(false);
                        $r10.setCallback(this.mCallback);
                        if (this.mAnimatedVectorState.mVectorDrawable != null) {
                            this.mAnimatedVectorState.mVectorDrawable.setCallback(null);
                        }
                        this.mAnimatedVectorState.mVectorDrawable = $r10;
                    }
                    $r9.recycle();
                } else if (TARGET.equals($r6)) {
                    $r9 = $r1.obtainAttributes($r3, AndroidResources.styleable_AnimatedVectorDrawableTarget);
                    $r6 = $r9.getString(0);
                    $i0 = $r9.getResourceId(1, 0);
                    if ($i0 != 0) {
                        if (this.mContext != null) {
                            Context $r14 = this.mContext;
                            setupAnimatorsForTarget($r6, AnimatorInflater.loadAnimator($r14, $i0));
                        } else {
                            throw new IllegalStateException("Context can't be null when inflating animators");
                        }
                    }
                    $r9.recycle();
                } else {
                    continue;
                }
            }
            $i0 = $r2.next();
        }
    }

    public void inflate(Resources $r1, XmlPullParser $r2, AttributeSet $r3) throws XmlPullParserException, IOException {
        inflate($r1, $r2, $r3, null);
    }

    public void applyTheme(Theme $r1) throws  {
        if (this.mDelegateDrawable != null) {
            DrawableCompat.applyTheme(this.mDelegateDrawable, $r1);
        }
    }

    public boolean canApplyTheme() throws  {
        if (this.mDelegateDrawable != null) {
            return DrawableCompat.canApplyTheme(this.mDelegateDrawable);
        }
        return false;
    }

    private void setupColorAnimator(Animator $r1) throws  {
        if ($r1 instanceof AnimatorSet) {
            ArrayList $r3 = ((AnimatorSet) $r1).getChildAnimations();
            if ($r3 != null) {
                for (int $i0 = 0; $i0 < $r3.size(); $i0++) {
                    setupColorAnimator((Animator) $r3.get($i0));
                }
            }
        }
        if ($r1 instanceof ObjectAnimator) {
            ObjectAnimator $r6 = (ObjectAnimator) $r1;
            String $r7 = $r6.getPropertyName();
            if ("fillColor".equals($r7) || "strokeColor".equals($r7)) {
                if (this.mArgbEvaluator == null) {
                    this.mArgbEvaluator = new ArgbEvaluator();
                }
                $r6.setEvaluator(this.mArgbEvaluator);
            }
        }
    }

    private void setupAnimatorsForTarget(String $r1, Animator $r2) throws  {
        $r2.setTarget(this.mAnimatedVectorState.mVectorDrawable.getTargetByName($r1));
        if (VERSION.SDK_INT < 21) {
            setupColorAnimator($r2);
        }
        if (this.mAnimatedVectorState.mAnimators == null) {
            this.mAnimatedVectorState.mAnimators = new ArrayList();
            this.mAnimatedVectorState.mTargetNameMap = new ArrayMap();
        }
        this.mAnimatedVectorState.mAnimators.add($r2);
        this.mAnimatedVectorState.mTargetNameMap.put($r2, $r1);
    }

    public boolean isRunning() throws  {
        if (this.mDelegateDrawable != null) {
            return ((AnimatedVectorDrawable) this.mDelegateDrawable).isRunning();
        }
        ArrayList $r1 = this.mAnimatedVectorState.mAnimators;
        int $i0 = $r1.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (((Animator) $r1.get($i1)).isRunning()) {
                return true;
            }
        }
        return false;
    }

    private boolean isStarted() throws  {
        ArrayList $r1 = this.mAnimatedVectorState.mAnimators;
        if ($r1 == null) {
            return false;
        }
        int $i0 = $r1.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if (((Animator) $r1.get($i1)).isRunning()) {
                return true;
            }
        }
        return false;
    }

    public void start() throws  {
        if (this.mDelegateDrawable != null) {
            ((AnimatedVectorDrawable) this.mDelegateDrawable).start();
        } else if (!isStarted()) {
            ArrayList $r1 = this.mAnimatedVectorState.mAnimators;
            int $i0 = $r1.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ((Animator) $r1.get($i1)).start();
            }
            invalidateSelf();
        }
    }

    public void stop() throws  {
        if (this.mDelegateDrawable != null) {
            ((AnimatedVectorDrawable) this.mDelegateDrawable).stop();
            return;
        }
        ArrayList $r1 = this.mAnimatedVectorState.mAnimators;
        int $i0 = $r1.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            ((Animator) $r1.get($i1)).end();
        }
    }
}
