package android.support.v4.media.session;

import android.media.session.PlaybackState;
import android.media.session.PlaybackState.Builder;
import android.media.session.PlaybackState.CustomAction;
import android.os.Bundle;
import dalvik.annotation.Signature;
import java.util.Iterator;
import java.util.List;

class PlaybackStateCompatApi22 {
    PlaybackStateCompatApi22() throws  {
    }

    public static Bundle getExtras(Object $r1) throws  {
        return ((PlaybackState) $r1).getExtras();
    }

    public static Object newInstance(@Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) int $i0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) long $l1, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) long $l2, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) float $f0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) long $l3, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) CharSequence $r0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) long $l4, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) List<Object> $r1, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) long $l5, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Ljava/lang/Object;", ">;J", "Landroid/os/Bundle;", ")", "Ljava/lang/Object;"}) Bundle $r2) throws  {
        Builder $r3 = new Builder();
        $r3.setState($i0, $l1, $f0, $l4);
        $r3.setBufferedPosition($l2);
        $r3.setActions($l3);
        $r3.setErrorMessage($r0);
        Iterator $r4 = $r1.iterator();
        while ($r4.hasNext()) {
            $r3.addCustomAction((CustomAction) $r4.next());
        }
        $r3.setActiveQueueItemId($l5);
        $r3.setExtras($r2);
        return $r3.build();
    }
}
