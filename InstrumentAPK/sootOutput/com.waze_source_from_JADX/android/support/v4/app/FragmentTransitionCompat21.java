package android.support.v4.app;

import android.graphics.Rect;
import android.transition.Transition;
import android.transition.Transition.EpicenterCallback;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnPreDrawListener;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

class FragmentTransitionCompat21 {

    public interface ViewRetriever {
        View getView() throws ;
    }

    public static class EpicenterView {
        public View epicenter;
    }

    FragmentTransitionCompat21() throws  {
    }

    public static String getTransitionName(View $r0) throws  {
        return $r0.getTransitionName();
    }

    public static Object cloneTransition(Object $r1) throws  {
        if ($r1 != null) {
            return ((Transition) $r1).clone();
        }
        return $r1;
    }

    public static Object captureExitingViews(@Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) Object $r4, @Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) View $r0, @Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) ArrayList<View> $r1, @Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) Map<String, View> $r2, @Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")", "Ljava/lang/Object;"}) View $r3) throws  {
        if ($r4 == null) {
            return $r4;
        }
        captureTransitioningViews($r1, $r0);
        if ($r2 != null) {
            $r1.removeAll($r2.values());
        }
        if ($r1.isEmpty()) {
            return null;
        }
        $r1.add($r3);
        addTargets((Transition) $r4, $r1);
        return $r4;
    }

    public static void excludeTarget(Object $r0, View $r1, boolean $z0) throws  {
        ((Transition) $r0).excludeTarget($r1, $z0);
    }

    public static void beginDelayedTransition(ViewGroup $r0, Object $r1) throws  {
        TransitionManager.beginDelayedTransition($r0, (Transition) $r1);
    }

    public static void setEpicenter(Object $r0, View $r1) throws  {
        Transition $r4 = (Transition) $r0;
        final Rect $r3 = getBoundsOnScreen($r1);
        $r4.setEpicenterCallback(new EpicenterCallback() {
            public Rect onGetEpicenter(Transition transition) throws  {
                return $r3;
            }
        });
    }

    public static Object wrapSharedElementTransition(Object $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        Transition $r2 = (Transition) $r0;
        if ($r2 == null) {
            return null;
        }
        TransitionSet $r1 = new TransitionSet();
        $r1.addTransition($r2);
        return $r1;
    }

    public static void addTransitionTargets(@Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Object $r0, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Object $r1, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) View $r2, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ViewRetriever $r3, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) View $r4, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) EpicenterView $r5, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Map<String, String> $r6, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r7, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Map<String, View> $r8, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Map<String, View> $r9, @Signature({"(", "Ljava/lang/Object;", "Ljava/lang/Object;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$ViewRetriever;", "Landroid/view/View;", "Landroid/support/v4/app/FragmentTransitionCompat21$EpicenterView;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r10) throws  {
        if ($r0 != null || $r1 != null) {
            Transition $r13 = (Transition) $r0;
            if ($r13 != null) {
                $r13.addTarget($r4);
            }
            if ($r1 != null) {
                setSharedElementTargets($r1, $r4, $r8, $r10);
            }
            if ($r3 != null) {
                final View view = $r2;
                final Transition transition = $r13;
                final View view2 = $r4;
                final ViewRetriever viewRetriever = $r3;
                final Map<String, String> map = $r6;
                final Map<String, View> map2 = $r9;
                final ArrayList<View> arrayList = $r7;
                $r2.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                    public boolean onPreDraw() throws  {
                        view.getViewTreeObserver().removeOnPreDrawListener(this);
                        if (transition != null) {
                            Transition $r3 = transition;
                            this = this;
                            $r3.removeTarget(view2);
                        }
                        View $r1 = viewRetriever.getView();
                        if ($r1 != null) {
                            if (!map.isEmpty()) {
                                FragmentTransitionCompat21.findNamedViews(map2, $r1);
                                map2.keySet().retainAll(map.values());
                                for (Entry $r10 : map.entrySet()) {
                                    View $r12 = (View) map2.get((String) $r10.getValue());
                                    if ($r12 != null) {
                                        $r12.setTransitionName((String) $r10.getKey());
                                    }
                                }
                            }
                            if (transition != null) {
                                ArrayList $r13 = arrayList;
                                FragmentTransitionCompat21.captureTransitioningViews($r13, $r1);
                                arrayList.removeAll(map2.values());
                                C00312 c00312 = this;
                                this = c00312;
                                arrayList.add(view2);
                                $r3 = transition;
                                $r13 = arrayList;
                                FragmentTransitionCompat21.addTargets($r3, $r13);
                            }
                        }
                        return true;
                    }
                });
            }
            setSharedElementEpicenter($r13, $r5);
        }
    }

    public static Object mergeTransitions(Object $r0, Object $r1, Object $r2, boolean $z0) throws  {
        boolean $z1 = true;
        Transition $r4 = (Transition) $r0;
        Transition $r5 = (Transition) $r1;
        Transition $r6 = (Transition) $r2;
        if (!($r4 == null || $r5 == null)) {
            $z1 = $z0;
        }
        TransitionSet $r3;
        if ($z1) {
            $r3 = new TransitionSet();
            if ($r4 != null) {
                $r3.addTransition($r4);
            }
            if ($r5 != null) {
                $r3.addTransition($r5);
            }
            if ($r6 != null) {
                $r3.addTransition($r6);
            }
            return $r3;
        }
        Transition $r7 = null;
        if ($r5 != null && $r4 != null) {
            $r7 = new TransitionSet().addTransition($r5).addTransition($r4).setOrdering(1);
        } else if ($r5 != null) {
            $r7 = $r5;
        } else if ($r4 != null) {
            $r7 = $r4;
        }
        if ($r6 == null) {
            return $r7;
        }
        $r3 = new TransitionSet();
        if ($r7 != null) {
            $r3.addTransition($r7);
        }
        $r3.addTransition($r6);
        return $r3;
    }

    public static void setSharedElementTargets(@Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Object $r0, @Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) View $r1, @Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Map<String, View> $r2, @Signature({"(", "Ljava/lang/Object;", "Landroid/view/View;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r3) throws  {
        TransitionSet $r4 = (TransitionSet) $r0;
        $r3.clear();
        $r3.addAll($r2.values());
        List $r6 = $r4.getTargets();
        $r6.clear();
        int $i0 = $r3.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            bfsAddViewChildren($r6, (View) $r3.get($i1));
        }
        $r3.add($r1);
        addTargets($r4, $r3);
    }

    private static void bfsAddViewChildren(@Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Landroid/view/View;", ")V"}) List<View> $r0, @Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Landroid/view/View;", ")V"}) View $r1) throws  {
        int $i0 = $r0.size();
        if (!containedBeforeIndex($r0, $r1, $i0)) {
            $r0.add($r1);
            for (int $i1 = $i0; $i1 < $r0.size(); $i1++) {
                $r1 = (View) $r0.get($i1);
                if ($r1 instanceof ViewGroup) {
                    ViewGroup $r3 = (ViewGroup) $r1;
                    int $i2 = $r3.getChildCount();
                    for (int $i3 = 0; $i3 < $i2; $i3++) {
                        $r1 = $r3.getChildAt($i3);
                        if (!containedBeforeIndex($r0, $r1, $i0)) {
                            $r0.add($r1);
                        }
                    }
                }
            }
        }
    }

    private static boolean containedBeforeIndex(@Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Landroid/view/View;", "I)Z"}) List<View> $r0, @Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Landroid/view/View;", "I)Z"}) View $r1, @Signature({"(", "Ljava/util/List", "<", "Landroid/view/View;", ">;", "Landroid/view/View;", "I)Z"}) int $i0) throws  {
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            if ($r0.get($i1) == $r1) {
                return true;
            }
        }
        return false;
    }

    private static void setSharedElementEpicenter(Transition $r0, final EpicenterView $r1) throws  {
        if ($r0 != null) {
            $r0.setEpicenterCallback(new EpicenterCallback() {
                private Rect mEpicenter;

                public Rect onGetEpicenter(Transition transition) throws  {
                    if (this.mEpicenter == null && $r1.epicenter != null) {
                        this.mEpicenter = FragmentTransitionCompat21.getBoundsOnScreen($r1.epicenter);
                    }
                    return this.mEpicenter;
                }
            });
        }
    }

    private static Rect getBoundsOnScreen(View $r0) throws  {
        Rect $r1 = new Rect();
        int[] $r2 = new int[2];
        $r0.getLocationOnScreen($r2);
        $r1.set($r2[0], $r2[1], $r2[0] + $r0.getWidth(), $r2[1] + $r0.getHeight());
        return $r1;
    }

    private static void captureTransitioningViews(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Landroid/view/View;", ")V"}) ArrayList<View> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Landroid/view/View;", ")V"}) View $r1) throws  {
        if ($r1.getVisibility() != 0) {
            return;
        }
        if ($r1 instanceof ViewGroup) {
            ViewGroup $r2 = (ViewGroup) $r1;
            if ($r2.isTransitionGroup()) {
                $r0.add($r2);
                return;
            }
            int $i0 = $r2.getChildCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                captureTransitioningViews($r0, $r2.getChildAt($i1));
            }
            return;
        }
        $r0.add($r1);
    }

    public static void findNamedViews(@Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")V"}) Map<String, View> $r0, @Signature({"(", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;", "Landroid/view/View;", ")V"}) View $r1) throws  {
        if ($r1.getVisibility() == 0) {
            String $r2 = $r1.getTransitionName();
            if ($r2 != null) {
                $r0.put($r2, $r1);
            }
            if ($r1 instanceof ViewGroup) {
                ViewGroup $r3 = (ViewGroup) $r1;
                int $i0 = $r3.getChildCount();
                for (int $i1 = 0; $i1 < $i0; $i1++) {
                    findNamedViews($r0, $r3.getChildAt($i1));
                }
            }
        }
    }

    public static void cleanupTransitions(@Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) View $r0, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) View $r1, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Object $r2, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r3, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Object $r4, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r5, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Object $r6, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r7, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Object $r8, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r9, @Signature({"(", "Landroid/view/View;", "Landroid/view/View;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Landroid/view/View;", ">;)V"}) Map<String, View> $r10) throws  {
        Transition $r13 = (Transition) $r2;
        Transition $r14 = (Transition) $r4;
        Transition $r15 = (Transition) $r6;
        Transition $r16 = (Transition) $r8;
        if ($r16 != null) {
            final View view = $r0;
            final Transition transition = $r13;
            final ArrayList<View> arrayList = $r3;
            final Transition transition2 = $r14;
            final ArrayList<View> arrayList2 = $r5;
            final Transition transition3 = $r15;
            final ArrayList<View> arrayList3 = $r7;
            final Map<String, View> map = $r10;
            final ArrayList<View> arrayList4 = $r9;
            final Transition transition4 = $r16;
            final View view2 = $r1;
            $r0.getViewTreeObserver().addOnPreDrawListener(new OnPreDrawListener() {
                public boolean onPreDraw() throws  {
                    view.getViewTreeObserver().removeOnPreDrawListener(this);
                    if (transition != null) {
                        FragmentTransitionCompat21.removeTargets(transition, arrayList);
                    }
                    if (transition2 != null) {
                        FragmentTransitionCompat21.removeTargets(transition2, arrayList2);
                    }
                    if (transition3 != null) {
                        FragmentTransitionCompat21.removeTargets(transition3, arrayList3);
                    }
                    for (Entry $r9 : map.entrySet()) {
                        ((View) $r9.getValue()).setTransitionName((String) $r9.getKey());
                    }
                    int $i0 = arrayList4.size();
                    for (int $i1 = 0; $i1 < $i0; $i1++) {
                        transition4.excludeTarget((View) arrayList4.get($i1), false);
                    }
                    Transition $r3 = transition4;
                    $r3.excludeTarget(view2, null);
                    return true;
                }
            });
        }
    }

    public static void removeTargets(@Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Object $r0, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1) throws  {
        Transition $r2 = (Transition) $r0;
        int $i0;
        if ($r2 instanceof TransitionSet) {
            TransitionSet $r3 = (TransitionSet) $r2;
            $i0 = $r3.getTransitionCount();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                removeTargets($r3.getTransitionAt($i1), $r1);
            }
        } else if (!hasSimpleTarget($r2)) {
            List $r4 = $r2.getTargets();
            if ($r4 != null && $r4.size() == $r1.size() && $r4.containsAll($r1)) {
                for ($i0 = $r1.size() - 1; $i0 >= 0; $i0--) {
                    $r2.removeTarget((View) $r1.get($i0));
                }
            }
        }
    }

    public static void addTargets(@Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) Object $r0, @Signature({"(", "Ljava/lang/Object;", "Ljava/util/ArrayList", "<", "Landroid/view/View;", ">;)V"}) ArrayList<View> $r1) throws  {
        Transition $r2 = (Transition) $r0;
        int $i0;
        int $i1;
        if ($r2 instanceof TransitionSet) {
            TransitionSet $r3 = (TransitionSet) $r2;
            $i0 = $r3.getTransitionCount();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                addTargets($r3.getTransitionAt($i1), $r1);
            }
        } else if (!hasSimpleTarget($r2) && isNullOrEmpty($r2.getTargets())) {
            $i0 = $r1.size();
            for ($i1 = 0; $i1 < $i0; $i1++) {
                $r2.addTarget((View) $r1.get($i1));
            }
        }
    }

    private static boolean hasSimpleTarget(Transition $r0) throws  {
        return (isNullOrEmpty($r0.getTargetIds()) && isNullOrEmpty($r0.getTargetNames()) && isNullOrEmpty($r0.getTargetTypes())) ? false : true;
    }

    private static boolean isNullOrEmpty(List $r0) throws  {
        return $r0 == null || $r0.isEmpty();
    }
}
