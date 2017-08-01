package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.media.session.PlaybackState.CustomAction.Builder;
import android.os.Bundle;
import dalvik.annotation.Signature;
import java.util.Iterator;
import java.util.List;

class PlaybackStateCompatApi21 {

    static final class CustomAction {
        CustomAction() throws  {
        }

        public static String getAction(Object $r1) throws  {
            return ((android.media.session.PlaybackState.CustomAction) $r1).getAction();
        }

        public static CharSequence getName(Object $r1) throws  {
            return ((android.media.session.PlaybackState.CustomAction) $r1).getName();
        }

        public static int getIcon(Object $r0) throws  {
            return ((android.media.session.PlaybackState.CustomAction) $r0).getIcon();
        }

        public static Bundle getExtras(Object $r1) throws  {
            return ((android.media.session.PlaybackState.CustomAction) $r1).getExtras();
        }

        public static Object newInstance(String $r0, CharSequence $r1, int $i0, Bundle $r2) throws  {
            Builder $r3 = new Builder($r0, $r1, $i0);
            $r3.setExtras($r2);
            return $r3.build();
        }
    }

    PlaybackStateCompatApi21() throws  {
    }

    public static int getState(Object $r0) throws  {
        return ((PlaybackState) $r0).getState();
    }

    public static long getPosition(Object $r0) throws  {
        return ((PlaybackState) $r0).getPosition();
    }

    public static long getBufferedPosition(Object $r0) throws  {
        return ((PlaybackState) $r0).getBufferedPosition();
    }

    public static float getPlaybackSpeed(Object $r0) throws  {
        return ((PlaybackState) $r0).getPlaybackSpeed();
    }

    public static long getActions(Object $r0) throws  {
        return ((PlaybackState) $r0).getActions();
    }

    public static CharSequence getErrorMessage(Object $r1) throws  {
        return ((PlaybackState) $r1).getErrorMessage();
    }

    public static long getLastPositionUpdateTime(Object $r0) throws  {
        return ((PlaybackState) $r0).getLastPositionUpdateTime();
    }

    public static List<Object> getCustomActions(@Signature({"(", "Ljava/lang/Object;", ")", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;"}) Object $r1) throws  {
        return ((PlaybackState) $r1).getCustomActions();
    }

    public static long getActiveQueueItemId(Object $r0) throws  {
        return ((PlaybackState) $r0).getActiveQueueItemId();
    }

    public static Object newInstance(@Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) int $i0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) long $l1, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) long $l2, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) float $f0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) long $l3, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) CharSequence $r0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) long $l4, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) List<Object> $r1, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J)", "Ljava/lang/Object;"}) long $l5) throws  {
        PlaybackState.Builder $r2 = new PlaybackState.Builder();
        $r2.setState($i0, $l1, $f0, $l4);
        $r2.setBufferedPosition($l2);
        $r2.setActions($l3);
        $r2.setErrorMessage($r0);
        Iterator $r3 = $r1.iterator();
        while ($r3.hasNext()) {
            $r2.addCustomAction((android.media.session.PlaybackState.CustomAction) $r3.next());
        }
        $r2.setActiveQueueItemId($l5);
        return $r2.build();
    }
}
