package android.support.v4.media.session;

import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import dalvik.annotation.Signature;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

public final class PlaybackStateCompat implements Parcelable {
    public static final long ACTION_FAST_FORWARD = 64;
    public static final long ACTION_PAUSE = 2;
    public static final long ACTION_PLAY = 4;
    public static final long ACTION_PLAY_FROM_MEDIA_ID = 1024;
    public static final long ACTION_PLAY_FROM_SEARCH = 2048;
    public static final long ACTION_PLAY_FROM_URI = 8192;
    public static final long ACTION_PLAY_PAUSE = 512;
    public static final long ACTION_REWIND = 8;
    public static final long ACTION_SEEK_TO = 256;
    public static final long ACTION_SET_RATING = 128;
    public static final long ACTION_SKIP_TO_NEXT = 32;
    public static final long ACTION_SKIP_TO_PREVIOUS = 16;
    public static final long ACTION_SKIP_TO_QUEUE_ITEM = 4096;
    public static final long ACTION_STOP = 1;
    public static final Creator<PlaybackStateCompat> CREATOR = new C00931();
    public static final long PLAYBACK_POSITION_UNKNOWN = -1;
    public static final int STATE_BUFFERING = 6;
    public static final int STATE_CONNECTING = 8;
    public static final int STATE_ERROR = 7;
    public static final int STATE_FAST_FORWARDING = 4;
    public static final int STATE_NONE = 0;
    public static final int STATE_PAUSED = 2;
    public static final int STATE_PLAYING = 3;
    public static final int STATE_REWINDING = 5;
    public static final int STATE_SKIPPING_TO_NEXT = 10;
    public static final int STATE_SKIPPING_TO_PREVIOUS = 9;
    public static final int STATE_SKIPPING_TO_QUEUE_ITEM = 11;
    public static final int STATE_STOPPED = 1;
    private final long mActions;
    private final long mActiveItemId;
    private final long mBufferedPosition;
    private List<CustomAction> mCustomActions;
    private final CharSequence mErrorMessage;
    private final Bundle mExtras;
    private final long mPosition;
    private final float mSpeed;
    private final int mState;
    private Object mStateObj;
    private final long mUpdateTime;

    static class C00931 implements Creator<PlaybackStateCompat> {
        C00931() throws  {
        }

        public PlaybackStateCompat createFromParcel(Parcel $r1) throws  {
            return new PlaybackStateCompat($r1);
        }

        public PlaybackStateCompat[] newArray(int $i0) throws  {
            return new PlaybackStateCompat[$i0];
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Actions {
    }

    public static final class Builder {
        private long mActions;
        private long mActiveItemId = -1;
        private long mBufferedPosition;
        private final List<CustomAction> mCustomActions = new ArrayList();
        private CharSequence mErrorMessage;
        private Bundle mExtras;
        private long mPosition;
        private float mRate;
        private int mState;
        private long mUpdateTime;

        public Builder(PlaybackStateCompat $r1) throws  {
            this.mState = $r1.mState;
            this.mPosition = $r1.mPosition;
            this.mRate = $r1.mSpeed;
            this.mUpdateTime = $r1.mUpdateTime;
            this.mBufferedPosition = $r1.mBufferedPosition;
            this.mActions = $r1.mActions;
            this.mErrorMessage = $r1.mErrorMessage;
            if ($r1.mCustomActions != null) {
                this.mCustomActions.addAll($r1.mCustomActions);
            }
            this.mActiveItemId = $r1.mActiveItemId;
            this.mExtras = $r1.mExtras;
        }

        public Builder setState(int $i0, long $l1, float $f0) throws  {
            return setState($i0, $l1, $f0, SystemClock.elapsedRealtime());
        }

        public Builder setState(int $i0, long $l1, float $f0, long $l2) throws  {
            this.mState = $i0;
            this.mPosition = $l1;
            this.mUpdateTime = $l2;
            this.mRate = $f0;
            return this;
        }

        public Builder setBufferedPosition(long $l0) throws  {
            this.mBufferedPosition = $l0;
            return this;
        }

        public Builder setActions(long $l0) throws  {
            this.mActions = $l0;
            return this;
        }

        public Builder addCustomAction(String $r1, String $r2, int $i0) throws  {
            return addCustomAction(new CustomAction($r1, $r2, $i0, null));
        }

        public Builder addCustomAction(CustomAction $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("You may not add a null CustomAction to PlaybackStateCompat.");
            }
            this.mCustomActions.add($r1);
            return this;
        }

        public Builder setActiveQueueItemId(long $l0) throws  {
            this.mActiveItemId = $l0;
            return this;
        }

        public Builder setErrorMessage(CharSequence $r1) throws  {
            this.mErrorMessage = $r1;
            return this;
        }

        public Builder setExtras(Bundle $r1) throws  {
            this.mExtras = $r1;
            return this;
        }

        public PlaybackStateCompat build() throws  {
            return new PlaybackStateCompat(this.mState, this.mPosition, this.mBufferedPosition, this.mRate, this.mActions, this.mErrorMessage, this.mUpdateTime, this.mCustomActions, this.mActiveItemId, this.mExtras);
        }
    }

    public static final class CustomAction implements Parcelable {
        public static final Creator<CustomAction> CREATOR = new C00941();
        private final String mAction;
        private Object mCustomActionObj;
        private final Bundle mExtras;
        private final int mIcon;
        private final CharSequence mName;

        static class C00941 implements Creator<CustomAction> {
            C00941() throws  {
            }

            public CustomAction createFromParcel(Parcel $r1) throws  {
                return new CustomAction($r1);
            }

            public CustomAction[] newArray(int $i0) throws  {
                return new CustomAction[$i0];
            }
        }

        public static final class Builder {
            private final String mAction;
            private Bundle mExtras;
            private final int mIcon;
            private final CharSequence mName;

            public Builder(String $r1, CharSequence $r2, int $i0) throws  {
                if (TextUtils.isEmpty($r1)) {
                    throw new IllegalArgumentException("You must specify an action to build a CustomAction.");
                } else if (TextUtils.isEmpty($r2)) {
                    throw new IllegalArgumentException("You must specify a name to build a CustomAction.");
                } else if ($i0 == 0) {
                    throw new IllegalArgumentException("You must specify an icon resource id to build a CustomAction.");
                } else {
                    this.mAction = $r1;
                    this.mName = $r2;
                    this.mIcon = $i0;
                }
            }

            public Builder setExtras(Bundle $r1) throws  {
                this.mExtras = $r1;
                return this;
            }

            public CustomAction build() throws  {
                return new CustomAction(this.mAction, this.mName, this.mIcon, this.mExtras);
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        private CustomAction(String $r1, CharSequence $r2, int $i0, Bundle $r3) throws  {
            this.mAction = $r1;
            this.mName = $r2;
            this.mIcon = $i0;
            this.mExtras = $r3;
        }

        private CustomAction(Parcel $r1) throws  {
            this.mAction = $r1.readString();
            this.mName = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
            this.mIcon = $r1.readInt();
            this.mExtras = $r1.readBundle();
        }

        public void writeToParcel(Parcel $r1, int $i0) throws  {
            $r1.writeString(this.mAction);
            TextUtils.writeToParcel(this.mName, $r1, $i0);
            $r1.writeInt(this.mIcon);
            $r1.writeBundle(this.mExtras);
        }

        public static CustomAction fromCustomAction(Object $r0) throws  {
            if ($r0 == null || VERSION.SDK_INT < 21) {
                return null;
            }
            CustomAction $r1 = new CustomAction(CustomAction.getAction($r0), CustomAction.getName($r0), CustomAction.getIcon($r0), CustomAction.getExtras($r0));
            $r1.mCustomActionObj = $r0;
            return $r1;
        }

        public Object getCustomAction() throws  {
            if (this.mCustomActionObj != null || VERSION.SDK_INT < 21) {
                return this.mCustomActionObj;
            }
            this.mCustomActionObj = CustomAction.newInstance(this.mAction, this.mName, this.mIcon, this.mExtras);
            return this.mCustomActionObj;
        }

        public String getAction() throws  {
            return this.mAction;
        }

        public CharSequence getName() throws  {
            return this.mName;
        }

        public int getIcon() throws  {
            return this.mIcon;
        }

        public Bundle getExtras() throws  {
            return this.mExtras;
        }

        public String toString() throws  {
            return "Action:mName='" + this.mName + ", mIcon=" + this.mIcon + ", mExtras=" + this.mExtras;
        }
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    public int describeContents() throws  {
        return 0;
    }

    private PlaybackStateCompat(@Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) int $i0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) long $l1, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) long $l2, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) float $f0, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) long $l3, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) CharSequence $r1, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) long $l4, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) List<CustomAction> $r2, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) long $l5, @Signature({"(IJJFJ", "Ljava/lang/CharSequence;", "J", "Ljava/util/List", "<", "Landroid/support/v4/media/session/PlaybackStateCompat$CustomAction;", ">;J", "Landroid/os/Bundle;", ")V"}) Bundle $r3) throws  {
        this.mState = $i0;
        this.mPosition = $l1;
        this.mBufferedPosition = $l2;
        this.mSpeed = $f0;
        this.mActions = $l3;
        this.mErrorMessage = $r1;
        this.mUpdateTime = $l4;
        this.mCustomActions = new ArrayList($r2);
        this.mActiveItemId = $l5;
        this.mExtras = $r3;
    }

    private PlaybackStateCompat(Parcel $r1) throws  {
        this.mState = $r1.readInt();
        this.mPosition = $r1.readLong();
        this.mSpeed = $r1.readFloat();
        this.mUpdateTime = $r1.readLong();
        this.mBufferedPosition = $r1.readLong();
        this.mActions = $r1.readLong();
        this.mErrorMessage = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel($r1);
        this.mCustomActions = $r1.createTypedArrayList(CustomAction.CREATOR);
        this.mActiveItemId = $r1.readLong();
        this.mExtras = $r1.readBundle();
    }

    public String toString() throws  {
        StringBuilder $r1 = new StringBuilder("PlaybackState {");
        $r1.append("state=").append(this.mState);
        $r1.append(", position=").append(this.mPosition);
        $r1.append(", buffered position=").append(this.mBufferedPosition);
        $r1.append(", speed=").append(this.mSpeed);
        $r1.append(", updated=").append(this.mUpdateTime);
        $r1.append(", actions=").append(this.mActions);
        $r1.append(", error=").append(this.mErrorMessage);
        $r1.append(", custom actions=").append(this.mCustomActions);
        $r1.append(", active item id=").append(this.mActiveItemId);
        $r1.append("}");
        return $r1.toString();
    }

    public void writeToParcel(Parcel $r1, int $i0) throws  {
        $r1.writeInt(this.mState);
        $r1.writeLong(this.mPosition);
        $r1.writeFloat(this.mSpeed);
        $r1.writeLong(this.mUpdateTime);
        $r1.writeLong(this.mBufferedPosition);
        $r1.writeLong(this.mActions);
        TextUtils.writeToParcel(this.mErrorMessage, $r1, $i0);
        $r1.writeTypedList(this.mCustomActions);
        $r1.writeLong(this.mActiveItemId);
        $r1.writeBundle(this.mExtras);
    }

    public int getState() throws  {
        return this.mState;
    }

    public long getPosition() throws  {
        return this.mPosition;
    }

    public long getBufferedPosition() throws  {
        return this.mBufferedPosition;
    }

    public float getPlaybackSpeed() throws  {
        return this.mSpeed;
    }

    public long getActions() throws  {
        return this.mActions;
    }

    public List<CustomAction> getCustomActions() throws  {
        return this.mCustomActions;
    }

    public CharSequence getErrorMessage() throws  {
        return this.mErrorMessage;
    }

    public long getLastPositionUpdateTime() throws  {
        return this.mUpdateTime;
    }

    public long getActiveQueueItemId() throws  {
        return this.mActiveItemId;
    }

    @Nullable
    public Bundle getExtras() throws  {
        return this.mExtras;
    }

    public static PlaybackStateCompat fromPlaybackState(Object $r0) throws  {
        if ($r0 == null || VERSION.SDK_INT < 21) {
            return null;
        }
        List<Object> $r2 = PlaybackStateCompatApi21.getCustomActions($r0);
        ArrayList $r3 = null;
        if ($r2 != null) {
            ArrayList arrayList = new ArrayList($r2.size());
            for (Object $r5 : $r2) {
                arrayList.add(CustomAction.fromCustomAction($r5));
            }
        }
        PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(PlaybackStateCompatApi21.getState($r0), PlaybackStateCompatApi21.getPosition($r0), PlaybackStateCompatApi21.getBufferedPosition($r0), PlaybackStateCompatApi21.getPlaybackSpeed($r0), PlaybackStateCompatApi21.getActions($r0), PlaybackStateCompatApi21.getErrorMessage($r0), PlaybackStateCompatApi21.getLastPositionUpdateTime($r0), $r3, PlaybackStateCompatApi21.getActiveQueueItemId($r0), VERSION.SDK_INT >= 22 ? PlaybackStateCompatApi22.getExtras($r0) : null);
        playbackStateCompat.mStateObj = $r0;
        return playbackStateCompat;
    }

    public Object getPlaybackState() throws  {
        if (this.mStateObj != null || VERSION.SDK_INT < 21) {
            return this.mStateObj;
        }
        ArrayList $r2 = null;
        if (this.mCustomActions != null) {
            List $r3 = this.mCustomActions;
            ArrayList arrayList = new ArrayList($r3.size());
            List<CustomAction> $r32 = this.mCustomActions;
            for (CustomAction customAction : $r32) {
                arrayList.add(customAction.getCustomAction());
            }
        }
        if (VERSION.SDK_INT >= 22) {
            this.mStateObj = PlaybackStateCompatApi22.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, $r2, this.mActiveItemId, this.mExtras);
        } else {
            this.mStateObj = PlaybackStateCompatApi21.newInstance(this.mState, this.mPosition, this.mBufferedPosition, this.mSpeed, this.mActions, this.mErrorMessage, this.mUpdateTime, $r2, this.mActiveItemId);
        }
        return this.mStateObj;
    }
}
