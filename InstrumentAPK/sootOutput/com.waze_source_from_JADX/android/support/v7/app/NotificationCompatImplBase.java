package android.support.v7.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatBase.Action;
import android.support.v7.appcompat.C0192R;
import android.widget.RemoteViews;
import dalvik.annotation.Signature;
import java.text.NumberFormat;
import java.util.List;

class NotificationCompatImplBase {
    static final int MAX_MEDIA_BUTTONS = 5;
    static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;

    NotificationCompatImplBase() throws  {
    }

    public static <T extends Action> void overrideContentView(@Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) NotificationBuilderWithBuilderAccessor $r0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) Context $r1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r2, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r3, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r4, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) int $i0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) Bitmap $r5, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r6, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) boolean $z0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) long $l1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) List<T> $r7, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) int[] $r8, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) boolean $z1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/support/v4/app/NotificationBuilderWithBuilderAccessor;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")V"}) PendingIntent $r9) throws  {
        $r0.getBuilder().setContent(generateContentView($r1, $r2, $r3, $r4, $i0, $r5, $r6, $z0, $l1, $r7, $r8, $z1, $r9));
        if ($z1) {
            $r0.getBuilder().setOngoing(true);
        }
    }

    private static <T extends Action> RemoteViews generateContentView(@Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) Context $r0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r2, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r3, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) int $i0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) Bitmap $r4, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r5, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) boolean $z0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) long $l1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) List<T> $r6, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) int[] $r7, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) boolean $z1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;[IZ", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) PendingIntent $r8) throws  {
        int $i2;
        RemoteViews $r9 = applyStandardTemplate($r0, $r1, $r2, $r3, $i0, $r4, $r5, $z0, $l1, C0192R.layout.notification_template_media, true);
        $i0 = $r6.size();
        if ($r7 == null) {
            $i2 = 0;
        } else {
            $i2 = Math.min($r7.length, 3);
        }
        $r9.removeAllViews(C0192R.id.media_actions);
        if ($i2 > 0) {
            for (int $i3 = 0; $i3 < $i2; $i3++) {
                if ($i3 >= $i0) {
                    throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", new Object[]{Integer.valueOf($i3), Integer.valueOf($i0 - 1)}));
                }
                $r9.addView(C0192R.id.media_actions, generateMediaActionButton($r0, (Action) $r6.get($r7[$i3])));
            }
        }
        if ($z1) {
            $r9.setViewVisibility(C0192R.id.end_padder, 8);
            $r9.setViewVisibility(C0192R.id.cancel_action, 0);
            $r9.setOnClickPendingIntent(C0192R.id.cancel_action, $r8);
            $r9.setInt(C0192R.id.cancel_action, "setAlpha", $r0.getResources().getInteger(C0192R.integer.cancel_button_image_alpha));
            return $r9;
        }
        $r9.setViewVisibility(C0192R.id.end_padder, 0);
        $r9.setViewVisibility(C0192R.id.cancel_action, 8);
        return $r9;
    }

    public static <T extends Action> void overrideBigContentView(@Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) Notification $r0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) Context $r1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r2, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r3, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r4, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) int $i0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) Bitmap $r5, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) CharSequence $r6, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) boolean $z0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) long $l1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) List<T> $r7, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) boolean $z1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/app/Notification;", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")V"}) PendingIntent $r8) throws  {
        $r0.bigContentView = generateBigContentView($r1, $r2, $r3, $r4, $i0, $r5, $r6, $z0, $l1, $r7, $z1, $r8);
        if ($z1) {
            $r0.flags |= 2;
        }
    }

    private static <T extends Action> RemoteViews generateBigContentView(@Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) Context $r0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r2, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r3, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) int $i0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) Bitmap $r4, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) CharSequence $r5, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) boolean $z0, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) long $l1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) List<T> $r6, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) boolean $z1, @Signature({"<T:", "Landroid/support/v4/app/NotificationCompatBase$Action;", ">(", "Landroid/content/Context;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "Ljava/lang/CharSequence;", "I", "Landroid/graphics/Bitmap;", "Ljava/lang/CharSequence;", "ZJ", "Ljava/util/List", "<TT;>;Z", "Landroid/app/PendingIntent;", ")", "Landroid/widget/RemoteViews;"}) PendingIntent $r7) throws  {
        int $i2 = Math.min($r6.size(), 5);
        RemoteViews $r8 = applyStandardTemplate($r0, $r1, $r2, $r3, $i0, $r4, $r5, $z0, $l1, getBigLayoutResource($i2), false);
        $r8.removeAllViews(C0192R.id.media_actions);
        if ($i2 > 0) {
            for ($i0 = 0; $i0 < $i2; $i0++) {
                $r8.addView(C0192R.id.media_actions, generateMediaActionButton($r0, (Action) $r6.get($i0)));
            }
        }
        if ($z1) {
            $r8.setViewVisibility(C0192R.id.cancel_action, 0);
            $r8.setInt(C0192R.id.cancel_action, "setAlpha", $r0.getResources().getInteger(C0192R.integer.cancel_button_image_alpha));
            $r8.setOnClickPendingIntent(C0192R.id.cancel_action, $r7);
            return $r8;
        }
        $r8.setViewVisibility(C0192R.id.cancel_action, 8);
        return $r8;
    }

    private static RemoteViews generateMediaActionButton(Context $r0, Action $r1) throws  {
        boolean $z0 = $r1.getActionIntent() == null;
        RemoteViews $r2 = new RemoteViews($r0.getPackageName(), C0192R.layout.notification_media_action);
        $r2.setImageViewResource(C0192R.id.action0, $r1.getIcon());
        if (!$z0) {
            $r2.setOnClickPendingIntent(C0192R.id.action0, $r1.getActionIntent());
        }
        if (VERSION.SDK_INT < 15) {
            return $r2;
        }
        $r2.setContentDescription(C0192R.id.action0, $r1.getTitle());
        return $r2;
    }

    private static int getBigLayoutResource(int $i0) throws  {
        if ($i0 <= 3) {
            return C0192R.layout.notification_template_big_media_narrow;
        }
        return C0192R.layout.notification_template_big_media;
    }

    private static RemoteViews applyStandardTemplate(Context $r0, CharSequence $r1, CharSequence $r2, CharSequence $r3, int $i0, Bitmap $r4, CharSequence $r5, boolean $z0, long $l1, int $i2, boolean $z1) throws  {
        RemoteViews $r6 = new RemoteViews($r0.getPackageName(), $i2);
        boolean $z2 = false;
        boolean $z3 = false;
        if ($r4 == null || VERSION.SDK_INT < 16) {
            $r6.setViewVisibility(C0192R.id.icon, 8);
        } else {
            $r6.setViewVisibility(C0192R.id.icon, 0);
            $r6.setImageViewBitmap(C0192R.id.icon, $r4);
        }
        if ($r1 != null) {
            $r6.setTextViewText(C0192R.id.title, $r1);
        }
        if ($r2 != null) {
            $r6.setTextViewText(C0192R.id.text, $r2);
            $z2 = true;
        }
        if ($r3 != null) {
            $r6.setTextViewText(C0192R.id.info, $r3);
            $r6.setViewVisibility(C0192R.id.info, 0);
            $z2 = true;
        } else if ($i0 > 0) {
            if ($i0 > $r0.getResources().getInteger(C0192R.integer.status_bar_notification_info_maxnum)) {
                $r6.setTextViewText(C0192R.id.info, $r0.getResources().getString(C0192R.string.status_bar_notification_info_overflow));
            } else {
                $r6.setTextViewText(C0192R.id.info, NumberFormat.getIntegerInstance().format((long) $i0));
            }
            $r6.setViewVisibility(C0192R.id.info, 0);
            $z2 = true;
        } else {
            $r6.setViewVisibility(C0192R.id.info, 8);
        }
        if ($r5 != null && VERSION.SDK_INT >= 16) {
            $r6.setTextViewText(C0192R.id.text, $r5);
            if ($r2 != null) {
                $r6.setTextViewText(C0192R.id.text2, $r2);
                $r6.setViewVisibility(C0192R.id.text2, 0);
                $z3 = true;
            } else {
                $r6.setViewVisibility(C0192R.id.text2, 8);
            }
        }
        if ($z3 && VERSION.SDK_INT >= 16) {
            if ($z1) {
                $r6.setTextViewTextSize(C0192R.id.text, 0, (float) $r0.getResources().getDimensionPixelSize(C0192R.dimen.notification_subtext_size));
            }
            $r6.setViewPadding(C0192R.id.line1, 0, 0, 0, 0);
        }
        if ($l1 != 0) {
            if ($z0) {
                $r6.setViewVisibility(C0192R.id.chronometer, 0);
                $r6.setLong(C0192R.id.chronometer, "setBase", (SystemClock.elapsedRealtime() - System.currentTimeMillis()) + $l1);
                $r6.setBoolean(C0192R.id.chronometer, "setStarted", true);
            } else {
                $r6.setViewVisibility(C0192R.id.time, 0);
                $r6.setLong(C0192R.id.time, "setTime", $l1);
            }
        }
        $r6.setViewVisibility(C0192R.id.line3, $z2 ? (byte) 0 : (byte) 8);
        return $r6;
    }
}
