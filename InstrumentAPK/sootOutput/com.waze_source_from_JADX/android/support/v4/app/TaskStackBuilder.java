package android.support.v4.app;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Iterator;

public final class TaskStackBuilder implements Iterable<Intent> {
    private static final TaskStackBuilderImpl IMPL;
    private static final String TAG = "TaskStackBuilder";
    private final ArrayList<Intent> mIntents = new ArrayList();
    private final Context mSourceContext;

    public interface SupportParentable {
        Intent getSupportParentActivityIntent() throws ;
    }

    interface TaskStackBuilderImpl {
        PendingIntent getPendingIntent(Context context, Intent[] intentArr, int i, int i2, Bundle bundle) throws ;
    }

    static class TaskStackBuilderImplBase implements TaskStackBuilderImpl {
        TaskStackBuilderImplBase() throws  {
        }

        public PendingIntent getPendingIntent(Context $r1, Intent[] $r2, int $i0, int $i1, Bundle options) throws  {
            Intent $r4 = new Intent($r2[$r2.length - 1]);
            $r4.addFlags(268435456);
            return PendingIntent.getActivity($r1, $i0, $r4, $i1);
        }
    }

    static class TaskStackBuilderImplHoneycomb implements TaskStackBuilderImpl {
        TaskStackBuilderImplHoneycomb() throws  {
        }

        public PendingIntent getPendingIntent(Context $r1, Intent[] $r2, int $i0, int $i1, Bundle options) throws  {
            $r2[0] = new Intent($r2[0]).addFlags(268484608);
            return TaskStackBuilderHoneycomb.getActivitiesPendingIntent($r1, $i0, $r2, $i1);
        }
    }

    static class TaskStackBuilderImplJellybean implements TaskStackBuilderImpl {
        TaskStackBuilderImplJellybean() throws  {
        }

        public PendingIntent getPendingIntent(Context $r1, Intent[] $r2, int $i0, int $i1, Bundle $r3) throws  {
            $r2[0] = new Intent($r2[0]).addFlags(268484608);
            return TaskStackBuilderJellybean.getActivitiesPendingIntent($r1, $i0, $r2, $i1, $r3);
        }
    }

    static {
        if (VERSION.SDK_INT >= 11) {
            IMPL = new TaskStackBuilderImplHoneycomb();
        } else {
            IMPL = new TaskStackBuilderImplBase();
        }
    }

    private TaskStackBuilder(Context $r1) throws  {
        this.mSourceContext = $r1;
    }

    public static TaskStackBuilder create(Context $r0) throws  {
        return new TaskStackBuilder($r0);
    }

    public static TaskStackBuilder from(Context $r0) throws  {
        return create($r0);
    }

    public TaskStackBuilder addNextIntent(Intent $r1) throws  {
        this.mIntents.add($r1);
        return this;
    }

    public TaskStackBuilder addNextIntentWithParentStack(Intent $r1) throws  {
        ComponentName $r2 = $r1.getComponent();
        ComponentName $r3 = $r2;
        if ($r2 == null) {
            $r3 = $r1.resolveActivity(this.mSourceContext.getPackageManager());
        }
        if ($r3 != null) {
            addParentStack($r3);
        }
        addNextIntent($r1);
        return this;
    }

    public TaskStackBuilder addParentStack(Activity $r1) throws  {
        Intent $r2 = null;
        if ($r1 instanceof SupportParentable) {
            $r2 = ((SupportParentable) $r1).getSupportParentActivityIntent();
        }
        if ($r2 == null) {
            $r2 = NavUtils.getParentActivityIntent($r1);
        }
        if ($r2 == null) {
            return this;
        }
        ComponentName $r4 = $r2.getComponent();
        ComponentName $r5 = $r4;
        if ($r4 == null) {
            $r5 = $r2.resolveActivity(this.mSourceContext.getPackageManager());
        }
        addParentStack($r5);
        addNextIntent($r2);
        return this;
    }

    public TaskStackBuilder addParentStack(@Signature({"(", "Ljava/lang/Class", "<*>;)", "Landroid/support/v4/app/TaskStackBuilder;"}) Class<?> $r1) throws  {
        return addParentStack(new ComponentName(this.mSourceContext, $r1));
    }

    public TaskStackBuilder addParentStack(ComponentName $r1) throws  {
        int $i0 = this.mIntents.size();
        try {
            Intent $r5 = NavUtils.getParentActivityIntent(this.mSourceContext, $r1);
            while ($r5 != null) {
                this.mIntents.add($i0, $r5);
                $r5 = NavUtils.getParentActivityIntent(this.mSourceContext, $r5.getComponent());
            }
            return this;
        } catch (NameNotFoundException $r2) {
            Log.e(TAG, "Bad ComponentName while traversing activity parent metadata");
            throw new IllegalArgumentException($r2);
        }
    }

    public int getIntentCount() throws  {
        return this.mIntents.size();
    }

    public Intent getIntent(int $i0) throws  {
        return editIntentAt($i0);
    }

    public Intent editIntentAt(int $i0) throws  {
        return (Intent) this.mIntents.get($i0);
    }

    public Iterator<Intent> iterator() throws  {
        return this.mIntents.iterator();
    }

    public void startActivities() throws  {
        startActivities(null);
    }

    public void startActivities(Bundle $r1) throws  {
        if (this.mIntents.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot startActivities");
        }
        Intent[] $r6 = (Intent[]) this.mIntents.toArray(new Intent[this.mIntents.size()]);
        $r6[0] = new Intent($r6[0]).addFlags(268484608);
        if (!ContextCompat.startActivities(this.mSourceContext, $r6, $r1)) {
            Intent $r2 = new Intent($r6[$r6.length - 1]);
            $r2.addFlags(268435456);
            this.mSourceContext.startActivity($r2);
        }
    }

    public PendingIntent getPendingIntent(int $i0, int $i1) throws  {
        return getPendingIntent($i0, $i1, null);
    }

    public PendingIntent getPendingIntent(int $i0, int $i1, Bundle $r1) throws  {
        if (this.mIntents.isEmpty()) {
            throw new IllegalStateException("No intents added to TaskStackBuilder; cannot getPendingIntent");
        }
        Intent[] $r5 = (Intent[]) this.mIntents.toArray(new Intent[this.mIntents.size()]);
        $r5[0] = new Intent($r5[0]).addFlags(268484608);
        return IMPL.getPendingIntent(this.mSourceContext, $r5, $i0, $i1, $r1);
    }

    public Intent[] getIntents() throws  {
        Intent[] $r1 = new Intent[this.mIntents.size()];
        if ($r1.length == 0) {
            return $r1;
        }
        $r1[0] = new Intent((Intent) this.mIntents.get(0)).addFlags(268484608);
        for (int $i0 = 1; $i0 < $r1.length; $i0++) {
            $r1[$i0] = new Intent((Intent) this.mIntents.get($i0));
        }
        return $r1;
    }
}
