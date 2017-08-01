package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.Resources.Theme;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Drawable.ConstantState;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v7.appcompat.C0192R;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.util.Xml;
import com.waze.view.popups.BottomSheet;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class AppCompatDrawableManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[]{C0192R.drawable.abc_popup_background_mtrl_mult, C0192R.drawable.abc_cab_background_internal_bg, C0192R.drawable.abc_menu_hardkey_panel_mtrl_mult};
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[]{C0192R.drawable.abc_textfield_activated_mtrl_alpha, C0192R.drawable.abc_textfield_search_activated_mtrl_alpha, C0192R.drawable.abc_cab_background_top_mtrl_alpha, C0192R.drawable.abc_text_cursor_material};
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[]{C0192R.drawable.abc_textfield_search_default_mtrl_alpha, C0192R.drawable.abc_textfield_default_mtrl_alpha, C0192R.drawable.abc_ab_share_pack_mtrl_alpha};
    private static final ColorFilterLruCache COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
    private static final boolean DEBUG = false;
    private static final Mode DEFAULT_MODE = Mode.SRC_IN;
    private static AppCompatDrawableManager INSTANCE = null;
    private static final String PLATFORM_VD_CLAZZ = "android.graphics.drawable.VectorDrawable";
    private static final String SKIP_DRAWABLE_TAG = "appcompat_skip_skip";
    private static final String TAG = "AppCompatDrawableManager";
    private static final int[] TINT_CHECKABLE_BUTTON_LIST = new int[]{C0192R.drawable.abc_btn_check_material, C0192R.drawable.abc_btn_radio_material};
    private static final int[] TINT_COLOR_CONTROL_NORMAL = new int[]{C0192R.drawable.abc_ic_ab_back_mtrl_am_alpha, C0192R.drawable.abc_ic_go_search_api_mtrl_alpha, C0192R.drawable.abc_ic_search_api_mtrl_alpha, C0192R.drawable.abc_ic_commit_search_api_mtrl_alpha, C0192R.drawable.abc_ic_clear_mtrl_alpha, C0192R.drawable.abc_ic_menu_share_mtrl_alpha, C0192R.drawable.abc_ic_menu_copy_mtrl_am_alpha, C0192R.drawable.abc_ic_menu_cut_mtrl_alpha, C0192R.drawable.abc_ic_menu_selectall_mtrl_alpha, C0192R.drawable.abc_ic_menu_paste_mtrl_am_alpha, C0192R.drawable.abc_ic_menu_moreoverflow_mtrl_alpha, C0192R.drawable.abc_ic_voice_search_api_mtrl_alpha};
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST = new int[]{C0192R.drawable.abc_edit_text_material, C0192R.drawable.abc_tab_indicator_material, C0192R.drawable.abc_textfield_search_material, C0192R.drawable.abc_spinner_mtrl_am_alpha, C0192R.drawable.abc_spinner_textfield_background_material, C0192R.drawable.abc_ratingbar_full_material, C0192R.drawable.abc_switch_track_mtrl_alpha, C0192R.drawable.abc_switch_thumb_material, C0192R.drawable.abc_btn_default_mtrl_shape, C0192R.drawable.abc_btn_borderless_material};
    private ArrayMap<String, InflateDelegate> mDelegates;
    private final Object mDrawableCacheLock = new Object();
    private final WeakHashMap<Context, LongSparseArray<WeakReference<ConstantState>>> mDrawableCaches = new WeakHashMap(0);
    private boolean mHasCheckedVectorDrawableSetup;
    private SparseArray<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArray<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    private interface InflateDelegate {
        Drawable createFromXmlInner(@NonNull Context context, @NonNull XmlPullParser xmlPullParser, @NonNull AttributeSet attributeSet, @Nullable Theme theme) throws ;
    }

    private static class AvdcInflateDelegate implements InflateDelegate {
        private AvdcInflateDelegate() throws  {
        }

        public Drawable createFromXmlInner(@NonNull Context $r1, @NonNull XmlPullParser $r2, @NonNull AttributeSet $r3, @Nullable Theme $r4) throws  {
            try {
                return AnimatedVectorDrawableCompat.createFromXmlInner($r1, $r1.getResources(), $r2, $r3, $r4);
            } catch (Exception $r5) {
                Log.e("AvdcInflateDelegate", "Exception while inflating <animated-vector>", $r5);
                return null;
            }
        }
    }

    private static class ColorFilterLruCache extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int $i0) throws  {
            super($i0);
        }

        PorterDuffColorFilter get(int $i0, Mode $r1) throws  {
            return (PorterDuffColorFilter) get(Integer.valueOf(generateCacheKey($i0, $r1)));
        }

        PorterDuffColorFilter put(int $i0, Mode $r1, PorterDuffColorFilter $r2) throws  {
            return (PorterDuffColorFilter) put(Integer.valueOf(generateCacheKey($i0, $r1)), $r2);
        }

        private static int generateCacheKey(int $i0, Mode $r0) throws  {
            return (($i0 + 31) * 31) + $r0.hashCode();
        }
    }

    private static class VdcInflateDelegate implements InflateDelegate {
        private VdcInflateDelegate() throws  {
        }

        public Drawable createFromXmlInner(@NonNull Context $r1, @NonNull XmlPullParser $r2, @NonNull AttributeSet $r3, @Nullable Theme $r4) throws  {
            try {
                return VectorDrawableCompat.createFromXmlInner($r1.getResources(), $r2, $r3, $r4);
            } catch (Exception $r5) {
                Log.e("VdcInflateDelegate", "Exception while inflating <vector>", $r5);
                return null;
            }
        }
    }

    public static AppCompatDrawableManager get() throws  {
        if (INSTANCE == null) {
            INSTANCE = new AppCompatDrawableManager();
            installDefaultInflateDelegates(INSTANCE);
        }
        return INSTANCE;
    }

    private static void installDefaultInflateDelegates(@NonNull AppCompatDrawableManager $r0) throws  {
        int $i0 = VERSION.SDK_INT;
        if ($i0 < 23) {
            $r0.addDelegate("vector", new VdcInflateDelegate());
            if ($i0 >= 11) {
                $r0.addDelegate("animated-vector", new AvdcInflateDelegate());
            }
        }
    }

    public Drawable getDrawable(@NonNull Context $r1, @DrawableRes int $i0) throws  {
        return getDrawable($r1, $i0, false);
    }

    public Drawable getDrawable(@NonNull Context $r1, @DrawableRes int $i0, boolean $z0) throws  {
        Drawable $r2 = loadDrawableFromDelegates($r1, $i0);
        Drawable $r3 = $r2;
        if ($r2 == null) {
            $r3 = createDrawableIfNeeded($r1, $i0);
        }
        if ($r3 == null) {
            $r3 = ContextCompat.getDrawable($r1, $i0);
        }
        if ($r3 != null) {
            $r3 = tintDrawable($r1, $i0, $z0, $r3);
        }
        if ($r3 == null) {
            return $r3;
        }
        DrawableUtils.fixDrawable($r3);
        return $r3;
    }

    private static long createCacheKey(TypedValue $r0) throws  {
        return (((long) $r0.assetCookie) << 32) | ((long) $r0.data);
    }

    private Drawable createDrawableIfNeeded(@NonNull Context $r1, @DrawableRes int $i0) throws  {
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue $r2 = this.mTypedValue;
        $r1.getResources().getValue($i0, $r2, true);
        long $l1 = createCacheKey($r2);
        Drawable $r4 = getCachedDrawable($r1, $l1);
        Drawable $r5 = $r4;
        if ($r4 != null) {
            return $r4;
        }
        if ($i0 == C0192R.drawable.abc_cab_background_top_material) {
            $r5 = r8;
            Drawable r8 = new LayerDrawable(new Drawable[]{getDrawable($r1, C0192R.drawable.abc_cab_background_internal_bg), getDrawable($r1, C0192R.drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if ($r5 != null) {
            $r5.setChangingConfigurations($r2.changingConfigurations);
            addDrawableToCache($r1, $l1, $r5);
        }
        return $r5;
    }

    private Drawable tintDrawable(@NonNull Context $r1, @DrawableRes int $i0, boolean $z0, @NonNull Drawable $r2) throws  {
        ColorStateList $r3 = getTintList($r1, $i0);
        if ($r3 != null) {
            if (DrawableUtils.canSafelyMutateDrawable($r2)) {
                $r2 = $r2.mutate();
            }
            $r2 = DrawableCompat.wrap($r2);
            DrawableCompat.setTintList($r2, $r3);
            Mode $r4 = getTintMode($i0);
            if ($r4 == null) {
                return $r2;
            }
            DrawableCompat.setTintMode($r2, $r4);
            return $r2;
        } else if ($i0 == C0192R.drawable.abc_seekbar_track_material) {
            $r5 = (LayerDrawable) $r2;
            setPorterDuffColorFilter($r5.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter($r5.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter($r5.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated), DEFAULT_MODE);
            return $r2;
        } else if ($i0 != C0192R.drawable.abc_ratingbar_indicator_material && $i0 != C0192R.drawable.abc_ratingbar_small_material) {
            return (tintDrawableUsingColorFilter($r1, $i0, $r2) || !$z0) ? $r2 : null;
        } else {
            $r5 = (LayerDrawable) $r2;
            setPorterDuffColorFilter($r5.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor($r1, C0192R.attr.colorControlNormal), DEFAULT_MODE);
            setPorterDuffColorFilter($r5.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated), DEFAULT_MODE);
            setPorterDuffColorFilter($r5.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated), DEFAULT_MODE);
            return $r2;
        }
    }

    private Drawable loadDrawableFromDelegates(@NonNull Context $r1, @DrawableRes int $i0) throws  {
        if (this.mDelegates == null || this.mDelegates.isEmpty()) {
            return null;
        }
        String $r7;
        if (this.mKnownDrawableIdTags != null) {
            $r7 = (String) this.mKnownDrawableIdTags.get($i0);
            if (SKIP_DRAWABLE_TAG.equals($r7) || ($r7 != null && this.mDelegates.get($r7) == null)) {
                return null;
            }
        }
        this.mKnownDrawableIdTags = new SparseArray();
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue $r3 = this.mTypedValue;
        Resources $r10 = $r1.getResources();
        $r10.getValue($i0, $r3, true);
        long $l1 = createCacheKey($r3);
        Drawable $r11 = getCachedDrawable($r1, $l1);
        Drawable $r9 = $r11;
        if ($r11 != null) {
            return $r11;
        }
        if ($r3.string != null) {
            CharSequence $r12 = $r3.string;
            if ($r12.toString().endsWith(".xml")) {
                try {
                    int $i2;
                    XmlResourceParser $r13 = $r10.getXml($i0);
                    AttributeSet $r14 = Xml.asAttributeSet($r13);
                    do {
                        $i2 = $r13.next();
                        if ($i2 == 2) {
                            break;
                        }
                    } while ($i2 != 1);
                    if ($i2 != 2) {
                        throw new XmlPullParserException("No start tag found");
                    }
                    $r7 = $r13.getName();
                    this.mKnownDrawableIdTags.append($i0, $r7);
                    InflateDelegate $r16 = (InflateDelegate) this.mDelegates.get($r7);
                    if ($r16 != null) {
                        $r9 = $r16.createFromXmlInner($r1, $r13, $r14, $r1.getTheme());
                    }
                    if ($r9 != null) {
                        $r9.setChangingConfigurations($r3.changingConfigurations);
                        if (addDrawableToCache($r1, $l1, $r9)) {
                        }
                    }
                } catch (Throwable $r2) {
                    Log.e(TAG, "Exception while inflating drawable", $r2);
                }
            }
        }
        if ($r9 != null) {
            return $r9;
        }
        this.mKnownDrawableIdTags.append($i0, SKIP_DRAWABLE_TAG);
        return $r9;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private android.graphics.drawable.Drawable getCachedDrawable(@android.support.annotation.NonNull android.content.Context r17, long r18) throws  {
        /*
        r16 = this;
        r0 = r16;
        r3 = r0.mDrawableCacheLock;
        monitor-enter(r3);
        r0 = r16;
        r4 = r0.mDrawableCaches;	 Catch:{ Throwable -> 0x003a }
        r0 = r17;
        r5 = r4.get(r0);	 Catch:{ Throwable -> 0x003a }
        r7 = r5;
        r7 = (android.support.v4.util.LongSparseArray) r7;	 Catch:{ Throwable -> 0x003a }
        r6 = r7;
        if (r6 != 0) goto L_0x0018;
    L_0x0015:
        monitor-exit(r3);	 Catch:{ Throwable -> 0x003a }
        r8 = 0;
        return r8;
    L_0x0018:
        r0 = r18;
        r5 = r6.get(r0);	 Catch:{ Throwable -> 0x003a }
        r10 = r5;
        r10 = (java.lang.ref.WeakReference) r10;	 Catch:{ Throwable -> 0x003a }
        r9 = r10;
        if (r9 == 0) goto L_0x0042;
    L_0x0024:
        r5 = r9.get();	 Catch:{ Throwable -> 0x003a }
        r12 = r5;
        r12 = (android.graphics.drawable.Drawable.ConstantState) r12;	 Catch:{ Throwable -> 0x003a }
        r11 = r12;
        if (r11 == 0) goto L_0x003d;
    L_0x002e:
        r0 = r17;
        r13 = r0.getResources();	 Catch:{ Throwable -> 0x003a }
        r14 = r11.newDrawable(r13);	 Catch:{ Throwable -> 0x003a }
        monitor-exit(r3);	 Catch:{ Throwable -> 0x003a }
        return r14;
    L_0x003a:
        r15 = move-exception;
        monitor-exit(r3);	 Catch:{ Throwable -> 0x003a }
        throw r15;
    L_0x003d:
        r0 = r18;
        r6.delete(r0);	 Catch:{ Throwable -> 0x003a }
    L_0x0042:
        monitor-exit(r3);	 Catch:{ Throwable -> 0x003a }
        r8 = 0;
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.AppCompatDrawableManager.getCachedDrawable(android.content.Context, long):android.graphics.drawable.Drawable");
    }

    private boolean addDrawableToCache(@NonNull Context $r1, long $l0, @NonNull Drawable $r2) throws  {
        ConstantState $r4 = $r2.getConstantState();
        if ($r4 == null) {
            return false;
        }
        synchronized (this.mDrawableCacheLock) {
            LongSparseArray $r7 = (LongSparseArray) this.mDrawableCaches.get($r1);
            if ($r7 == null) {
                $r7 = new LongSparseArray();
                this.mDrawableCaches.put($r1, $r7);
            }
            $r7.put($l0, new WeakReference($r4));
        }
        return true;
    }

    public final Drawable onDrawableLoadedFromResources(@NonNull Context $r1, @NonNull VectorEnabledTintResources $r2, @DrawableRes int $i0) throws  {
        Drawable $r3 = loadDrawableFromDelegates($r1, $i0);
        Drawable $r4 = $r3;
        if ($r3 == null) {
            $r4 = $r2.superGetDrawable($i0);
        }
        if ($r4 != null) {
            return tintDrawable($r1, $i0, false, $r4);
        }
        return null;
    }

    static boolean tintDrawableUsingColorFilter(@NonNull Context $r0, @DrawableRes int $i0, @NonNull Drawable $r1) throws  {
        Mode $r2 = DEFAULT_MODE;
        boolean $z0 = false;
        int $i1 = 0;
        int $i2 = -1;
        if (arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, $i0)) {
            $i1 = C0192R.attr.colorControlNormal;
            $z0 = true;
        } else if (arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, $i0)) {
            $i1 = C0192R.attr.colorControlActivated;
            $z0 = true;
        } else if (arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, $i0)) {
            $i1 = 16842801;
            $z0 = true;
            $r2 = Mode.MULTIPLY;
        } else if ($i0 == C0192R.drawable.abc_list_divider_mtrl_alpha) {
            $i1 = 16842800;
            $z0 = true;
            $i2 = Math.round(40.8f);
        }
        if (!$z0) {
            return false;
        }
        if (DrawableUtils.canSafelyMutateDrawable($r1)) {
            $r1 = $r1.mutate();
        }
        $r1.setColorFilter(getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor($r0, $i1), $r2));
        if ($i2 != -1) {
            $r1.setAlpha($i2);
        }
        return true;
    }

    private void addDelegate(@NonNull String $r1, @NonNull InflateDelegate $r2) throws  {
        if (this.mDelegates == null) {
            this.mDelegates = new ArrayMap();
        }
        this.mDelegates.put($r1, $r2);
    }

    private void removeDelegate(@NonNull String $r1, @NonNull InflateDelegate $r2) throws  {
        if (this.mDelegates != null && this.mDelegates.get($r1) == $r2) {
            this.mDelegates.remove($r1);
        }
    }

    private static boolean arrayContains(int[] $r0, int $i0) throws  {
        for (int $i1 : $r0) {
            if ($i1 == $i0) {
                return true;
            }
        }
        return false;
    }

    final Mode getTintMode(int $i0) throws  {
        if ($i0 == C0192R.drawable.abc_switch_thumb_material) {
            return Mode.MULTIPLY;
        }
        return null;
    }

    public final ColorStateList getTintList(@NonNull Context $r1, @DrawableRes int $i0) throws  {
        ColorStateList $r2 = getTintListFromCache($r1, $i0);
        ColorStateList $r3 = $r2;
        if ($r2 != null) {
            return $r2;
        }
        if ($i0 == C0192R.drawable.abc_edit_text_material) {
            $r3 = createEditTextColorStateList($r1);
        } else if ($i0 == C0192R.drawable.abc_switch_track_mtrl_alpha) {
            $r3 = createSwitchTrackColorStateList($r1);
        } else if ($i0 == C0192R.drawable.abc_switch_thumb_material) {
            $r3 = createSwitchThumbColorStateList($r1);
        } else if ($i0 == C0192R.drawable.abc_btn_default_mtrl_shape) {
            $r3 = createDefaultButtonColorStateList($r1);
        } else if ($i0 == C0192R.drawable.abc_btn_borderless_material) {
            $r3 = createBorderlessButtonColorStateList($r1);
        } else if ($i0 == C0192R.drawable.abc_btn_colored_material) {
            $r3 = createColoredButtonColorStateList($r1);
        } else if ($i0 == C0192R.drawable.abc_spinner_mtrl_am_alpha || $i0 == C0192R.drawable.abc_spinner_textfield_background_material) {
            $r3 = createSpinnerColorStateList($r1);
        } else if (arrayContains(TINT_COLOR_CONTROL_NORMAL, $i0)) {
            $r3 = ThemeUtils.getThemeAttrColorStateList($r1, C0192R.attr.colorControlNormal);
        } else if (arrayContains(TINT_COLOR_CONTROL_STATE_LIST, $i0)) {
            $r3 = createDefaultColorStateList($r1);
        } else if (arrayContains(TINT_CHECKABLE_BUTTON_LIST, $i0)) {
            $r3 = createCheckableButtonColorStateList($r1);
        } else if ($i0 == C0192R.drawable.abc_seekbar_thumb_material) {
            $r3 = createSeekbarThumbColorStateList($r1);
        }
        if ($r3 == null) {
            return $r3;
        }
        addTintListToCache($r1, $i0, $r3);
        return $r3;
    }

    private ColorStateList getTintListFromCache(@NonNull Context $r1, @DrawableRes int $i0) throws  {
        if (this.mTintLists == null) {
            return null;
        }
        SparseArray $r5 = (SparseArray) this.mTintLists.get($r1);
        if ($r5 != null) {
            return (ColorStateList) $r5.get($i0);
        }
        return null;
    }

    private void addTintListToCache(@NonNull Context $r1, @DrawableRes int $i0, @NonNull ColorStateList $r2) throws  {
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap();
        }
        SparseArray $r5 = (SparseArray) this.mTintLists.get($r1);
        if ($r5 == null) {
            $r5 = new SparseArray();
            this.mTintLists.put($r1, $r5);
        }
        $r5.append($i0, $r2);
    }

    private ColorStateList createDefaultColorStateList(Context $r1) throws  {
        int $i0 = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlNormal);
        int $i1 = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated);
        int[][] $r3 = new int[7][];
        $r2 = new int[7];
        int $i2 = 0 + 1;
        $r3[$i2] = ThemeUtils.FOCUSED_STATE_SET;
        $r2[$i2] = $i1;
        $i2++;
        $r3[$i2] = ThemeUtils.ACTIVATED_STATE_SET;
        $r2[$i2] = $i1;
        $i2++;
        $r3[$i2] = ThemeUtils.PRESSED_STATE_SET;
        $r2[$i2] = $i1;
        $i2++;
        $r3[$i2] = ThemeUtils.CHECKED_STATE_SET;
        $r2[$i2] = $i1;
        $i2++;
        $r3[$i2] = ThemeUtils.SELECTED_STATE_SET;
        $r2[$i2] = $i1;
        $i1 = $i2 + 1;
        $r3[$i1] = ThemeUtils.EMPTY_STATE_SET;
        $r2[$i1] = $i0;
        return new ColorStateList($r3, $r2);
    }

    private ColorStateList createCheckableButtonColorStateList(Context $r1) throws  {
        $r3 = new int[3][];
        int[] $r2 = new int[3];
        $r3[0] = ThemeUtils.DISABLED_STATE_SET;
        $r2[0] = ThemeUtils.getDisabledThemeAttrColor($r1, C0192R.attr.colorControlNormal);
        int $i0 = 0 + 1;
        $r3[$i0] = ThemeUtils.CHECKED_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated);
        $i0++;
        $r3[$i0] = ThemeUtils.EMPTY_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlNormal);
        return new ColorStateList($r3, $r2);
    }

    private ColorStateList createSwitchTrackColorStateList(Context $r1) throws  {
        $r3 = new int[3][];
        int[] $r2 = new int[3];
        $r3[0] = ThemeUtils.DISABLED_STATE_SET;
        $r2[0] = ThemeUtils.getThemeAttrColor($r1, 16842800, 0.1f);
        int $i0 = 0 + 1;
        $r3[$i0] = ThemeUtils.CHECKED_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated, BottomSheet.DISABLED_ALPHA);
        $i0++;
        $r3[$i0] = ThemeUtils.EMPTY_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, 16842800, BottomSheet.DISABLED_ALPHA);
        return new ColorStateList($r3, $r2);
    }

    private ColorStateList createSwitchThumbColorStateList(Context $r1) throws  {
        int[][] $r3 = new int[3][];
        int[] $r2 = new int[3];
        ColorStateList $r4 = ThemeUtils.getThemeAttrColorStateList($r1, C0192R.attr.colorSwitchThumbNormal);
        int $i0;
        if ($r4 == null || !$r4.isStateful()) {
            $r3[0] = ThemeUtils.DISABLED_STATE_SET;
            $r2[0] = ThemeUtils.getDisabledThemeAttrColor($r1, C0192R.attr.colorSwitchThumbNormal);
            $i0 = 0 + 1;
            $r3[$i0] = ThemeUtils.CHECKED_STATE_SET;
            $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated);
            $i0++;
            $r3[$i0] = ThemeUtils.EMPTY_STATE_SET;
            $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorSwitchThumbNormal);
        } else {
            $r3[0] = ThemeUtils.DISABLED_STATE_SET;
            $r2[0] = $r4.getColorForState($r3[0], 0);
            $i0 = 0 + 1;
            $r3[$i0] = ThemeUtils.CHECKED_STATE_SET;
            $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated);
            $i0++;
            $r3[$i0] = ThemeUtils.EMPTY_STATE_SET;
            $r2[$i0] = $r4.getDefaultColor();
        }
        return new ColorStateList($r3, $r2);
    }

    private ColorStateList createEditTextColorStateList(Context $r1) throws  {
        $r3 = new int[3][];
        int[] $r2 = new int[3];
        $r3[0] = ThemeUtils.DISABLED_STATE_SET;
        $r2[0] = ThemeUtils.getDisabledThemeAttrColor($r1, C0192R.attr.colorControlNormal);
        int $i0 = 0 + 1;
        $r3[$i0] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlNormal);
        $i0++;
        $r3[$i0] = ThemeUtils.EMPTY_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated);
        return new ColorStateList($r3, $r2);
    }

    private ColorStateList createDefaultButtonColorStateList(Context $r1) throws  {
        return createButtonColorStateList($r1, ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorButtonNormal));
    }

    private ColorStateList createBorderlessButtonColorStateList(Context $r1) throws  {
        return createButtonColorStateList($r1, 0);
    }

    private ColorStateList createColoredButtonColorStateList(Context $r1) throws  {
        return createButtonColorStateList($r1, ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorAccent));
    }

    private ColorStateList createButtonColorStateList(Context $r1, @ColorInt int $i0) throws  {
        $r3 = new int[4][];
        int[] $r2 = new int[4];
        int $i1 = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlHighlight);
        $r3[0] = ThemeUtils.DISABLED_STATE_SET;
        $r2[0] = ThemeUtils.getDisabledThemeAttrColor($r1, C0192R.attr.colorButtonNormal);
        int $i2 = 0 + 1;
        $r3[$i2] = ThemeUtils.PRESSED_STATE_SET;
        $r2[$i2] = ColorUtils.compositeColors($i1, $i0);
        $i2++;
        $r3[$i2] = ThemeUtils.FOCUSED_STATE_SET;
        $r2[$i2] = ColorUtils.compositeColors($i1, $i0);
        $i1 = $i2 + 1;
        $r3[$i1] = ThemeUtils.EMPTY_STATE_SET;
        $r2[$i1] = $i0;
        return new ColorStateList($r3, $r2);
    }

    private ColorStateList createSpinnerColorStateList(Context $r1) throws  {
        $r3 = new int[3][];
        int[] $r2 = new int[3];
        $r3[0] = ThemeUtils.DISABLED_STATE_SET;
        $r2[0] = ThemeUtils.getDisabledThemeAttrColor($r1, C0192R.attr.colorControlNormal);
        int $i0 = 0 + 1;
        $r3[$i0] = ThemeUtils.NOT_PRESSED_OR_FOCUSED_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlNormal);
        $i0++;
        $r3[$i0] = ThemeUtils.EMPTY_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated);
        return new ColorStateList($r3, $r2);
    }

    private ColorStateList createSeekbarThumbColorStateList(Context $r1) throws  {
        $r3 = new int[2][];
        int[] $r2 = new int[]{ThemeUtils.DISABLED_STATE_SET, ThemeUtils.getDisabledThemeAttrColor($r1, C0192R.attr.colorControlActivated)};
        int $i0 = 0 + 1;
        $r3[$i0] = ThemeUtils.EMPTY_STATE_SET;
        $r2[$i0] = ThemeUtils.getThemeAttrColor($r1, C0192R.attr.colorControlActivated);
        return new ColorStateList($r3, $r2);
    }

    public static void tintDrawable(Drawable $r0, TintInfo $r1, int[] $r2) throws  {
        if (!DrawableUtils.canSafelyMutateDrawable($r0) || $r0.mutate() == $r0) {
            if ($r1.mHasTintList || $r1.mHasTintMode) {
                $r0.setColorFilter(createTintFilter($r1.mHasTintList ? $r1.mTintList : null, $r1.mHasTintMode ? $r1.mTintMode : DEFAULT_MODE, $r2));
            } else {
                $r0.clearColorFilter();
            }
            if (VERSION.SDK_INT <= 23) {
                $r0.invalidateSelf();
                return;
            }
            return;
        }
        Log.d(TAG, "Mutated drawable is not the same instance as the input.");
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList $r0, Mode $r1, int[] $r2) throws  {
        return ($r0 == null || $r1 == null) ? null : getPorterDuffColorFilter($r0.getColorForState($r2, 0), $r1);
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int $i0, Mode $r0) throws  {
        PorterDuffColorFilter $r1 = COLOR_FILTER_CACHE.get($i0, $r0);
        if ($r1 != null) {
            return $r1;
        }
        $r1 = new PorterDuffColorFilter($i0, $r0);
        COLOR_FILTER_CACHE.put($i0, $r0, $r1);
        return $r1;
    }

    private static void setPorterDuffColorFilter(Drawable $r0, int $i0, Mode $r1) throws  {
        if (DrawableUtils.canSafelyMutateDrawable($r0)) {
            $r0 = $r0.mutate();
        }
        if ($r1 == null) {
            $r1 = DEFAULT_MODE;
        }
        $r0.setColorFilter(getPorterDuffColorFilter($i0, $r1));
    }

    private static boolean isVectorDrawable(@NonNull Drawable $r0) throws  {
        return ($r0 instanceof VectorDrawableCompat) || PLATFORM_VD_CLAZZ.equals($r0.getClass().getName());
    }
}
