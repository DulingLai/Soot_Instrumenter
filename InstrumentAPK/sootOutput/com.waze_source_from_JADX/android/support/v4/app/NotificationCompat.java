package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.v4.app.NotificationCompatBase.Action.Factory;
import android.support.v4.app.NotificationCompatBase.UnreadConversation;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput;
import android.widget.RemoteViews;
import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NotificationCompat {
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    @ColorInt
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    private static final NotificationCompatImpl IMPL;
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    public static class Action extends android.support.v4.app.NotificationCompatBase.Action {
        public static final Factory FACTORY = new C00371();
        public PendingIntent actionIntent;
        public int icon;
        private final Bundle mExtras;
        private final RemoteInput[] mRemoteInputs;
        public CharSequence title;

        static class C00371 implements Factory {
            C00371() throws  {
            }

            public Action build(int $i0, CharSequence $r1, PendingIntent $r2, Bundle $r3, RemoteInput[] $r5) throws  {
                return new Action($i0, $r1, $r2, $r3, (RemoteInput[]) $r5);
            }

            public Action[] newArray(int $i0) throws  {
                return new Action[$i0];
            }
        }

        public static final class Builder {
            private final Bundle mExtras;
            private final int mIcon;
            private final PendingIntent mIntent;
            private ArrayList<RemoteInput> mRemoteInputs;
            private final CharSequence mTitle;

            public Builder(int $i0, CharSequence $r1, PendingIntent $r2) throws  {
                this($i0, $r1, $r2, new Bundle());
            }

            public Builder(Action $r1) throws  {
                this($r1.icon, $r1.title, $r1.actionIntent, new Bundle($r1.mExtras));
            }

            private Builder(int $i0, CharSequence $r1, PendingIntent $r2, Bundle $r3) throws  {
                this.mIcon = $i0;
                this.mTitle = Builder.limitCharSequenceLength($r1);
                this.mIntent = $r2;
                this.mExtras = $r3;
            }

            public Builder addExtras(Bundle $r1) throws  {
                if ($r1 == null) {
                    return this;
                }
                this.mExtras.putAll($r1);
                return this;
            }

            public Bundle getExtras() throws  {
                return this.mExtras;
            }

            public Builder addRemoteInput(RemoteInput $r1) throws  {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList();
                }
                this.mRemoteInputs.add($r1);
                return this;
            }

            public Builder extend(Extender $r1) throws  {
                $r1.extend(this);
                return this;
            }

            public Action build() throws  {
                RemoteInput[] $r6;
                if (this.mRemoteInputs != null) {
                    $r6 = (RemoteInput[]) this.mRemoteInputs.toArray(new RemoteInput[this.mRemoteInputs.size()]);
                } else {
                    $r6 = null;
                }
                return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, $r6);
            }
        }

        public interface Extender {
            Builder extend(Builder builder) throws ;
        }

        public static final class WearableExtender implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags = 1;
            private CharSequence mInProgressLabel;

            private void setFlag(int r1, boolean r2) throws  {
                /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.app.NotificationCompat.Action.WearableExtender.setFlag(int, boolean):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 7 more
*/
                /*
                // Can't load method instructions.
                */
                throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompat.Action.WearableExtender.setFlag(int, boolean):void");
            }

            public WearableExtender(Action $r1) throws  {
                Bundle $r2 = $r1.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if ($r2 != null) {
                    this.mFlags = $r2.getInt(KEY_FLAGS, 1);
                    this.mInProgressLabel = $r2.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = $r2.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = $r2.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            public Builder extend(Builder $r1) throws  {
                Bundle $r2 = new Bundle();
                if (this.mFlags != 1) {
                    $r2.putInt(KEY_FLAGS, this.mFlags);
                }
                if (this.mInProgressLabel != null) {
                    $r2.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
                }
                if (this.mConfirmLabel != null) {
                    $r2.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
                }
                if (this.mCancelLabel != null) {
                    $r2.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
                }
                $r1.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, $r2);
                return $r1;
            }

            public WearableExtender clone() throws  {
                WearableExtender $r1 = new WearableExtender();
                $r1.mFlags = this.mFlags;
                $r1.mInProgressLabel = this.mInProgressLabel;
                $r1.mConfirmLabel = this.mConfirmLabel;
                $r1.mCancelLabel = this.mCancelLabel;
                return $r1;
            }

            public WearableExtender setAvailableOffline(boolean $z0) throws  {
                setFlag(1, $z0);
                return this;
            }

            public boolean isAvailableOffline() throws  {
                return (this.mFlags & 1) != 0;
            }

            public WearableExtender setInProgressLabel(CharSequence $r1) throws  {
                this.mInProgressLabel = $r1;
                return this;
            }

            public CharSequence getInProgressLabel() throws  {
                return this.mInProgressLabel;
            }

            public WearableExtender setConfirmLabel(CharSequence $r1) throws  {
                this.mConfirmLabel = $r1;
                return this;
            }

            public CharSequence getConfirmLabel() throws  {
                return this.mConfirmLabel;
            }

            public WearableExtender setCancelLabel(CharSequence $r1) throws  {
                this.mCancelLabel = $r1;
                return this;
            }

            public CharSequence getCancelLabel() throws  {
                return this.mCancelLabel;
            }
        }

        public Action(int $i0, CharSequence $r1, PendingIntent $r2) throws  {
            this($i0, $r1, $r2, new Bundle(), null);
        }

        private Action(int $i0, CharSequence $r1, PendingIntent $r2, Bundle $r4, RemoteInput[] $r3) throws  {
            this.icon = $i0;
            this.title = Builder.limitCharSequenceLength($r1);
            this.actionIntent = $r2;
            if ($r4 == null) {
                $r4 = new Bundle();
            }
            this.mExtras = $r4;
            this.mRemoteInputs = $r3;
        }

        public int getIcon() throws  {
            return this.icon;
        }

        public CharSequence getTitle() throws  {
            return this.title;
        }

        public PendingIntent getActionIntent() throws  {
            return this.actionIntent;
        }

        public Bundle getExtras() throws  {
            return this.mExtras;
        }

        public RemoteInput[] getRemoteInputs() throws  {
            return this.mRemoteInputs;
        }
    }

    public static abstract class Style {
        CharSequence mBigContentTitle;
        Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet = false;

        public void setBuilder(Builder $r1) throws  {
            if (this.mBuilder != $r1) {
                this.mBuilder = $r1;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }

        public Notification build() throws  {
            if (this.mBuilder != null) {
                return this.mBuilder.build();
            }
            return null;
        }
    }

    public static class BigPictureStyle extends Style {
        Bitmap mBigLargeIcon;
        boolean mBigLargeIconSet;
        Bitmap mPicture;

        public BigPictureStyle(Builder $r1) throws  {
            setBuilder($r1);
        }

        public BigPictureStyle setBigContentTitle(CharSequence $r1) throws  {
            this.mBigContentTitle = Builder.limitCharSequenceLength($r1);
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence $r1) throws  {
            this.mSummaryText = Builder.limitCharSequenceLength($r1);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap $r1) throws  {
            this.mPicture = $r1;
            return this;
        }

        public BigPictureStyle bigLargeIcon(Bitmap $r1) throws  {
            this.mBigLargeIcon = $r1;
            this.mBigLargeIconSet = true;
            return this;
        }
    }

    public static class BigTextStyle extends Style {
        CharSequence mBigText;

        public BigTextStyle(Builder $r1) throws  {
            setBuilder($r1);
        }

        public BigTextStyle setBigContentTitle(CharSequence $r1) throws  {
            this.mBigContentTitle = Builder.limitCharSequenceLength($r1);
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence $r1) throws  {
            this.mSummaryText = Builder.limitCharSequenceLength($r1);
            this.mSummaryTextSet = true;
            return this;
        }

        public BigTextStyle bigText(CharSequence $r1) throws  {
            this.mBigText = Builder.limitCharSequenceLength($r1);
            return this;
        }
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        public ArrayList<Action> mActions = new ArrayList();
        String mCategory;
        int mColor = 0;
        public CharSequence mContentInfo;
        PendingIntent mContentIntent;
        public CharSequence mContentText;
        public CharSequence mContentTitle;
        public Context mContext;
        Bundle mExtras;
        PendingIntent mFullScreenIntent;
        String mGroupKey;
        boolean mGroupSummary;
        public Bitmap mLargeIcon;
        boolean mLocalOnly = false;
        public Notification mNotification = new Notification();
        public int mNumber;
        public ArrayList<String> mPeople;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Notification mPublicVersion;
        boolean mShowWhen = true;
        String mSortKey;
        public Style mStyle;
        public CharSequence mSubText;
        RemoteViews mTickerView;
        public boolean mUseChronometer;
        int mVisibility = 0;

        private void setFlag(int r1, boolean r2) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.app.NotificationCompat.Builder.setFlag(int, boolean):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 6 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompat.Builder.setFlag(int, boolean):void");
        }

        public Builder(Context $r1) throws  {
            this.mContext = $r1;
            this.mNotification.when = System.currentTimeMillis();
            this.mNotification.audioStreamType = -1;
            this.mPriority = 0;
            this.mPeople = new ArrayList();
        }

        public Builder setWhen(long $l0) throws  {
            this.mNotification.when = $l0;
            return this;
        }

        public Builder setShowWhen(boolean $z0) throws  {
            this.mShowWhen = $z0;
            return this;
        }

        public Builder setUsesChronometer(boolean $z0) throws  {
            this.mUseChronometer = $z0;
            return this;
        }

        public Builder setSmallIcon(int $i0) throws  {
            this.mNotification.icon = $i0;
            return this;
        }

        public Builder setSmallIcon(int $i0, int $i1) throws  {
            this.mNotification.icon = $i0;
            this.mNotification.iconLevel = $i1;
            return this;
        }

        public Builder setContentTitle(CharSequence $r1) throws  {
            this.mContentTitle = limitCharSequenceLength($r1);
            return this;
        }

        public Builder setContentText(CharSequence $r1) throws  {
            this.mContentText = limitCharSequenceLength($r1);
            return this;
        }

        public Builder setSubText(CharSequence $r1) throws  {
            this.mSubText = limitCharSequenceLength($r1);
            return this;
        }

        public Builder setNumber(int $i0) throws  {
            this.mNumber = $i0;
            return this;
        }

        public Builder setContentInfo(CharSequence $r1) throws  {
            this.mContentInfo = limitCharSequenceLength($r1);
            return this;
        }

        public Builder setProgress(int $i0, int $i1, boolean $z0) throws  {
            this.mProgressMax = $i0;
            this.mProgress = $i1;
            this.mProgressIndeterminate = $z0;
            return this;
        }

        public Builder setContent(RemoteViews $r1) throws  {
            this.mNotification.contentView = $r1;
            return this;
        }

        public Builder setContentIntent(PendingIntent $r1) throws  {
            this.mContentIntent = $r1;
            return this;
        }

        public Builder setDeleteIntent(PendingIntent $r1) throws  {
            this.mNotification.deleteIntent = $r1;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent $r1, boolean $z0) throws  {
            this.mFullScreenIntent = $r1;
            setFlag(128, $z0);
            return this;
        }

        public Builder setTicker(CharSequence $r1) throws  {
            this.mNotification.tickerText = limitCharSequenceLength($r1);
            return this;
        }

        public Builder setTicker(CharSequence $r1, RemoteViews $r2) throws  {
            this.mNotification.tickerText = limitCharSequenceLength($r1);
            this.mTickerView = $r2;
            return this;
        }

        public Builder setLargeIcon(Bitmap $r1) throws  {
            this.mLargeIcon = $r1;
            return this;
        }

        public Builder setSound(Uri $r1) throws  {
            this.mNotification.sound = $r1;
            this.mNotification.audioStreamType = -1;
            return this;
        }

        public Builder setSound(Uri $r1, int $i0) throws  {
            this.mNotification.sound = $r1;
            this.mNotification.audioStreamType = $i0;
            return this;
        }

        public Builder setVibrate(long[] $r1) throws  {
            this.mNotification.vibrate = $r1;
            return this;
        }

        public Builder setLights(@ColorInt int $i0, int $i1, int $i2) throws  {
            boolean $z0;
            byte $b3 = (byte) 1;
            this.mNotification.ledARGB = $i0;
            this.mNotification.ledOnMS = $i1;
            this.mNotification.ledOffMS = $i2;
            if (this.mNotification.ledOnMS == 0 || this.mNotification.ledOffMS == 0) {
                $z0 = false;
            } else {
                $z0 = true;
            }
            Notification $r1 = this.mNotification;
            $i0 = this.mNotification.flags & -2;
            if (!$z0) {
                $b3 = (byte) 0;
            }
            $r1.flags = $b3 | $i0;
            return this;
        }

        public Builder setOngoing(boolean $z0) throws  {
            setFlag(2, $z0);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean $z0) throws  {
            setFlag(8, $z0);
            return this;
        }

        public Builder setAutoCancel(boolean $z0) throws  {
            setFlag(16, $z0);
            return this;
        }

        public Builder setLocalOnly(boolean $z0) throws  {
            this.mLocalOnly = $z0;
            return this;
        }

        public Builder setCategory(String $r1) throws  {
            this.mCategory = $r1;
            return this;
        }

        public Builder setDefaults(int $i0) throws  {
            this.mNotification.defaults = $i0;
            if (($i0 & 4) == 0) {
                return this;
            }
            Notification $r1 = this.mNotification;
            $r1.flags |= 1;
            return this;
        }

        public Builder setPriority(int $i0) throws  {
            this.mPriority = $i0;
            return this;
        }

        public Builder addPerson(String $r1) throws  {
            this.mPeople.add($r1);
            return this;
        }

        public Builder setGroup(String $r1) throws  {
            this.mGroupKey = $r1;
            return this;
        }

        public Builder setGroupSummary(boolean $z0) throws  {
            this.mGroupSummary = $z0;
            return this;
        }

        public Builder setSortKey(String $r1) throws  {
            this.mSortKey = $r1;
            return this;
        }

        public Builder addExtras(Bundle $r1) throws  {
            if ($r1 == null) {
                return this;
            }
            if (this.mExtras == null) {
                this.mExtras = new Bundle($r1);
                return this;
            }
            this.mExtras.putAll($r1);
            return this;
        }

        public Builder setExtras(Bundle $r1) throws  {
            this.mExtras = $r1;
            return this;
        }

        public Bundle getExtras() throws  {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            return this.mExtras;
        }

        public Builder addAction(int $i0, CharSequence $r1, PendingIntent $r2) throws  {
            this.mActions.add(new Action($i0, $r1, $r2));
            return this;
        }

        public Builder addAction(Action $r1) throws  {
            this.mActions.add($r1);
            return this;
        }

        public Builder setStyle(Style $r1) throws  {
            if (this.mStyle == $r1) {
                return this;
            }
            this.mStyle = $r1;
            if (this.mStyle == null) {
                return this;
            }
            this.mStyle.setBuilder(this);
            return this;
        }

        public Builder setColor(@ColorInt int $i0) throws  {
            this.mColor = $i0;
            return this;
        }

        public Builder setVisibility(int $i0) throws  {
            this.mVisibility = $i0;
            return this;
        }

        public Builder setPublicVersion(Notification $r1) throws  {
            this.mPublicVersion = $r1;
            return this;
        }

        public Builder extend(Extender $r1) throws  {
            $r1.extend(this);
            return this;
        }

        @Deprecated
        public Notification getNotification() throws  {
            return build();
        }

        public Notification build() throws  {
            return NotificationCompat.IMPL.build(this, getExtender());
        }

        protected BuilderExtender getExtender() throws  {
            return new BuilderExtender();
        }

        protected static CharSequence limitCharSequenceLength(CharSequence $r0) throws  {
            return ($r0 != null && $r0.length() > MAX_CHARSEQUENCE_LENGTH) ? $r0.subSequence(0, MAX_CHARSEQUENCE_LENGTH) : $r0;
        }
    }

    protected static class BuilderExtender {
        protected BuilderExtender() throws  {
        }

        public Notification build(Builder b, NotificationBuilderWithBuilderAccessor $r2) throws  {
            return $r2.build();
        }
    }

    public interface Extender {
        Builder extend(Builder builder) throws ;
    }

    public static final class CarExtender implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String TAG = "CarExtender";
        private int mColor = 0;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public static class UnreadConversation extends android.support.v4.app.NotificationCompatBase.UnreadConversation {
            static final android.support.v4.app.NotificationCompatBase.UnreadConversation.Factory FACTORY = new C00381();
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            static class C00381 implements android.support.v4.app.NotificationCompatBase.UnreadConversation.Factory {
                C00381() throws  {
                }

                public UnreadConversation build(String[] $r1, RemoteInput $r2, PendingIntent $r3, PendingIntent $r4, String[] $r5, long $l0) throws  {
                    return new UnreadConversation($r1, (RemoteInput) $r2, $r3, $r4, $r5, $l0);
                }
            }

            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages = new ArrayList();
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(String $r1) throws  {
                    this.mParticipant = $r1;
                }

                public Builder addMessage(String $r1) throws  {
                    this.mMessages.add($r1);
                    return this;
                }

                public Builder setReplyAction(PendingIntent $r1, RemoteInput $r2) throws  {
                    this.mRemoteInput = $r2;
                    this.mReplyPendingIntent = $r1;
                    return this;
                }

                public Builder setReadPendingIntent(PendingIntent $r1) throws  {
                    this.mReadPendingIntent = $r1;
                    return this;
                }

                public Builder setLatestTimestamp(long $l0) throws  {
                    this.mLatestTimestamp = $l0;
                    return this;
                }

                public UnreadConversation build() throws  {
                    String[] $r3 = new String[]{this.mParticipant};
                    return new UnreadConversation((String[]) this.mMessages.toArray(new String[this.mMessages.size()]), this.mRemoteInput, this.mReplyPendingIntent, this.mReadPendingIntent, $r3, this.mLatestTimestamp);
                }
            }

            UnreadConversation(String[] $r1, RemoteInput $r2, PendingIntent $r3, PendingIntent $r4, String[] $r5, long $l0) throws  {
                this.mMessages = $r1;
                this.mRemoteInput = $r2;
                this.mReadPendingIntent = $r4;
                this.mReplyPendingIntent = $r3;
                this.mParticipants = $r5;
                this.mLatestTimestamp = $l0;
            }

            public String[] getMessages() throws  {
                return this.mMessages;
            }

            public RemoteInput getRemoteInput() throws  {
                return this.mRemoteInput;
            }

            public PendingIntent getReplyPendingIntent() throws  {
                return this.mReplyPendingIntent;
            }

            public PendingIntent getReadPendingIntent() throws  {
                return this.mReadPendingIntent;
            }

            public String[] getParticipants() throws  {
                return this.mParticipants;
            }

            public String getParticipant() throws  {
                return this.mParticipants.length > 0 ? this.mParticipants[0] : null;
            }

            public long getLatestTimestamp() throws  {
                return this.mLatestTimestamp;
            }
        }

        public CarExtender(Notification $r1) throws  {
            if (VERSION.SDK_INT >= 21) {
                Bundle $r2 = NotificationCompat.getExtras($r1) == null ? null : NotificationCompat.getExtras($r1).getBundle(EXTRA_CAR_EXTENDER);
                if ($r2 != null) {
                    this.mLargeIcon = (Bitmap) $r2.getParcelable(EXTRA_LARGE_ICON);
                    this.mColor = $r2.getInt(EXTRA_COLOR, 0);
                    this.mUnreadConversation = (UnreadConversation) NotificationCompat.IMPL.getUnreadConversationFromBundle($r2.getBundle(EXTRA_CONVERSATION), UnreadConversation.FACTORY, RemoteInput.FACTORY);
                }
            }
        }

        public Builder extend(Builder $r1) throws  {
            if (VERSION.SDK_INT < 21) {
                return $r1;
            }
            Bundle $r2 = new Bundle();
            if (this.mLargeIcon != null) {
                $r2.putParcelable(EXTRA_LARGE_ICON, this.mLargeIcon);
            }
            if (this.mColor != 0) {
                $r2.putInt(EXTRA_COLOR, this.mColor);
            }
            if (this.mUnreadConversation != null) {
                $r2.putBundle(EXTRA_CONVERSATION, NotificationCompat.IMPL.getBundleForUnreadConversation(this.mUnreadConversation));
            }
            $r1.getExtras().putBundle(EXTRA_CAR_EXTENDER, $r2);
            return $r1;
        }

        public CarExtender setColor(@ColorInt int $i0) throws  {
            this.mColor = $i0;
            return this;
        }

        @ColorInt
        public int getColor() throws  {
            return this.mColor;
        }

        public CarExtender setLargeIcon(Bitmap $r1) throws  {
            this.mLargeIcon = $r1;
            return this;
        }

        public Bitmap getLargeIcon() throws  {
            return this.mLargeIcon;
        }

        public CarExtender setUnreadConversation(UnreadConversation $r1) throws  {
            this.mUnreadConversation = $r1;
            return this;
        }

        public UnreadConversation getUnreadConversation() throws  {
            return this.mUnreadConversation;
        }
    }

    public static class InboxStyle extends Style {
        ArrayList<CharSequence> mTexts = new ArrayList();

        public InboxStyle(Builder $r1) throws  {
            setBuilder($r1);
        }

        public InboxStyle setBigContentTitle(CharSequence $r1) throws  {
            this.mBigContentTitle = Builder.limitCharSequenceLength($r1);
            return this;
        }

        public InboxStyle setSummaryText(CharSequence $r1) throws  {
            this.mSummaryText = Builder.limitCharSequenceLength($r1);
            this.mSummaryTextSet = true;
            return this;
        }

        public InboxStyle addLine(CharSequence $r1) throws  {
            this.mTexts.add(Builder.limitCharSequenceLength($r1));
            return this;
        }
    }

    interface NotificationCompatImpl {
        Notification build(Builder builder, BuilderExtender builderExtender) throws ;

        Action getAction(Notification notification, int i) throws ;

        int getActionCount(Notification notification) throws ;

        Action[] getActionsFromParcelableArrayList(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;)[", "Landroid/support/v4/app/NotificationCompat$Action;"}) ArrayList<Parcelable> arrayList) throws ;

        Bundle getBundleForUnreadConversation(UnreadConversation unreadConversation) throws ;

        String getCategory(Notification notification) throws ;

        Bundle getExtras(Notification notification) throws ;

        String getGroup(Notification notification) throws ;

        boolean getLocalOnly(Notification notification) throws ;

        ArrayList<Parcelable> getParcelableArrayListForActions(@Signature({"([", "Landroid/support/v4/app/NotificationCompat$Action;", ")", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;"}) Action[] actionArr) throws ;

        String getSortKey(Notification notification) throws ;

        UnreadConversation getUnreadConversationFromBundle(Bundle bundle, UnreadConversation.Factory factory, RemoteInput.Factory factory2) throws ;

        boolean isGroupSummary(Notification notification) throws ;
    }

    static class NotificationCompatImplBase implements NotificationCompatImpl {
        public Action getAction(Notification n, int actionIndex) throws  {
            return null;
        }

        public int getActionCount(Notification n) throws  {
            return 0;
        }

        public Action[] getActionsFromParcelableArrayList(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;)[", "Landroid/support/v4/app/NotificationCompat$Action;"}) ArrayList<Parcelable> arrayList) throws  {
            return null;
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation uc) throws  {
            return null;
        }

        public String getCategory(Notification n) throws  {
            return null;
        }

        public Bundle getExtras(Notification n) throws  {
            return null;
        }

        public String getGroup(Notification n) throws  {
            return null;
        }

        public boolean getLocalOnly(Notification n) throws  {
            return false;
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(@Signature({"([", "Landroid/support/v4/app/NotificationCompat$Action;", ")", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;"}) Action[] actions) throws  {
            return null;
        }

        public String getSortKey(Notification n) throws  {
            return null;
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle b, UnreadConversation.Factory factory, RemoteInput.Factory remoteInputFactory) throws  {
            return null;
        }

        public boolean isGroupSummary(Notification n) throws  {
            return false;
        }

        NotificationCompatImplBase() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender extender) throws  {
            Notification $r6 = NotificationCompatBase.add($r1.mNotification, $r1.mContext, $r1.mContentTitle, $r1.mContentText, $r1.mContentIntent);
            if ($r1.mPriority <= 0) {
                return $r6;
            }
            $r6.flags |= 128;
            return $r6;
        }
    }

    static class NotificationCompatImplJellybean extends NotificationCompatImplBase {
        NotificationCompatImplJellybean() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender $r2) throws  {
            android.support.v4.app.NotificationCompatJellybean.Builder $r3 = new android.support.v4.app.NotificationCompatJellybean.Builder($r1.mContext, $r1.mNotification, $r1.mContentTitle, $r1.mContentText, $r1.mContentInfo, $r1.mTickerView, $r1.mNumber, $r1.mContentIntent, $r1.mFullScreenIntent, $r1.mLargeIcon, $r1.mProgressMax, $r1.mProgress, $r1.mProgressIndeterminate, $r1.mUseChronometer, $r1.mPriority, $r1.mSubText, $r1.mLocalOnly, $r1.mExtras, $r1.mGroupKey, $r1.mGroupSummary, $r1.mSortKey);
            ArrayList $r17 = $r1.mActions;
            NotificationCompat.addActionsToBuilder($r3, $r17);
            Style $r18 = $r1.mStyle;
            NotificationCompat.addStyleToBuilderJellybean($r3, $r18);
            return $r2.build($r1, $r3);
        }

        public Bundle getExtras(Notification $r1) throws  {
            return NotificationCompatJellybean.getExtras($r1);
        }

        public int getActionCount(Notification $r1) throws  {
            return NotificationCompatJellybean.getActionCount($r1);
        }

        public Action getAction(Notification $r1, int $i0) throws  {
            return (Action) NotificationCompatJellybean.getAction($r1, $i0, Action.FACTORY, RemoteInput.FACTORY);
        }

        public Action[] getActionsFromParcelableArrayList(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;)[", "Landroid/support/v4/app/NotificationCompat$Action;"}) ArrayList<Parcelable> $r1) throws  {
            return (Action[]) NotificationCompatJellybean.getActionsFromParcelableArrayList($r1, Action.FACTORY, RemoteInput.FACTORY);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(@Signature({"([", "Landroid/support/v4/app/NotificationCompat$Action;", ")", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;"}) Action[] $r1) throws  {
            return NotificationCompatJellybean.getParcelableArrayListForActions($r1);
        }

        public boolean getLocalOnly(Notification $r1) throws  {
            return NotificationCompatJellybean.getLocalOnly($r1);
        }

        public String getGroup(Notification $r1) throws  {
            return NotificationCompatJellybean.getGroup($r1);
        }

        public boolean isGroupSummary(Notification $r1) throws  {
            return NotificationCompatJellybean.isGroupSummary($r1);
        }

        public String getSortKey(Notification $r1) throws  {
            return NotificationCompatJellybean.getSortKey($r1);
        }
    }

    static class NotificationCompatImplKitKat extends NotificationCompatImplJellybean {
        NotificationCompatImplKitKat() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender $r2) throws  {
            android.support.v4.app.NotificationCompatKitKat.Builder $r3 = new android.support.v4.app.NotificationCompatKitKat.Builder($r1.mContext, $r1.mNotification, $r1.mContentTitle, $r1.mContentText, $r1.mContentInfo, $r1.mTickerView, $r1.mNumber, $r1.mContentIntent, $r1.mFullScreenIntent, $r1.mLargeIcon, $r1.mProgressMax, $r1.mProgress, $r1.mProgressIndeterminate, $r1.mShowWhen, $r1.mUseChronometer, $r1.mPriority, $r1.mSubText, $r1.mLocalOnly, $r1.mPeople, $r1.mExtras, $r1.mGroupKey, $r1.mGroupSummary, $r1.mSortKey);
            ArrayList $r14 = $r1.mActions;
            NotificationCompat.addActionsToBuilder($r3, $r14);
            Style $r18 = $r1.mStyle;
            NotificationCompat.addStyleToBuilderJellybean($r3, $r18);
            return $r2.build($r1, $r3);
        }

        public Bundle getExtras(Notification $r1) throws  {
            return NotificationCompatKitKat.getExtras($r1);
        }

        public int getActionCount(Notification $r1) throws  {
            return NotificationCompatKitKat.getActionCount($r1);
        }

        public Action getAction(Notification $r1, int $i0) throws  {
            return (Action) NotificationCompatKitKat.getAction($r1, $i0, Action.FACTORY, RemoteInput.FACTORY);
        }

        public boolean getLocalOnly(Notification $r1) throws  {
            return NotificationCompatKitKat.getLocalOnly($r1);
        }

        public String getGroup(Notification $r1) throws  {
            return NotificationCompatKitKat.getGroup($r1);
        }

        public boolean isGroupSummary(Notification $r1) throws  {
            return NotificationCompatKitKat.isGroupSummary($r1);
        }

        public String getSortKey(Notification $r1) throws  {
            return NotificationCompatKitKat.getSortKey($r1);
        }
    }

    static class NotificationCompatImplApi20 extends NotificationCompatImplKitKat {
        NotificationCompatImplApi20() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender $r2) throws  {
            android.support.v4.app.NotificationCompatApi20.Builder $r3 = new android.support.v4.app.NotificationCompatApi20.Builder($r1.mContext, $r1.mNotification, $r1.mContentTitle, $r1.mContentText, $r1.mContentInfo, $r1.mTickerView, $r1.mNumber, $r1.mContentIntent, $r1.mFullScreenIntent, $r1.mLargeIcon, $r1.mProgressMax, $r1.mProgress, $r1.mProgressIndeterminate, $r1.mShowWhen, $r1.mUseChronometer, $r1.mPriority, $r1.mSubText, $r1.mLocalOnly, $r1.mPeople, $r1.mExtras, $r1.mGroupKey, $r1.mGroupSummary, $r1.mSortKey);
            ArrayList $r14 = $r1.mActions;
            NotificationCompat.addActionsToBuilder($r3, $r14);
            Style $r18 = $r1.mStyle;
            NotificationCompat.addStyleToBuilderJellybean($r3, $r18);
            return $r2.build($r1, $r3);
        }

        public Action getAction(Notification $r1, int $i0) throws  {
            return (Action) NotificationCompatApi20.getAction($r1, $i0, Action.FACTORY, RemoteInput.FACTORY);
        }

        public Action[] getActionsFromParcelableArrayList(@Signature({"(", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;)[", "Landroid/support/v4/app/NotificationCompat$Action;"}) ArrayList<Parcelable> $r1) throws  {
            return (Action[]) NotificationCompatApi20.getActionsFromParcelableArrayList($r1, Action.FACTORY, RemoteInput.FACTORY);
        }

        public ArrayList<Parcelable> getParcelableArrayListForActions(@Signature({"([", "Landroid/support/v4/app/NotificationCompat$Action;", ")", "Ljava/util/ArrayList", "<", "Landroid/os/Parcelable;", ">;"}) Action[] $r1) throws  {
            return NotificationCompatApi20.getParcelableArrayListForActions($r1);
        }

        public boolean getLocalOnly(Notification $r1) throws  {
            return NotificationCompatApi20.getLocalOnly($r1);
        }

        public String getGroup(Notification $r1) throws  {
            return NotificationCompatApi20.getGroup($r1);
        }

        public boolean isGroupSummary(Notification $r1) throws  {
            return NotificationCompatApi20.isGroupSummary($r1);
        }

        public String getSortKey(Notification $r1) throws  {
            return NotificationCompatApi20.getSortKey($r1);
        }
    }

    static class NotificationCompatImplApi21 extends NotificationCompatImplApi20 {
        NotificationCompatImplApi21() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender $r2) throws  {
            android.support.v4.app.NotificationCompatApi21.Builder $r3 = new android.support.v4.app.NotificationCompatApi21.Builder($r1.mContext, $r1.mNotification, $r1.mContentTitle, $r1.mContentText, $r1.mContentInfo, $r1.mTickerView, $r1.mNumber, $r1.mContentIntent, $r1.mFullScreenIntent, $r1.mLargeIcon, $r1.mProgressMax, $r1.mProgress, $r1.mProgressIndeterminate, $r1.mShowWhen, $r1.mUseChronometer, $r1.mPriority, $r1.mSubText, $r1.mLocalOnly, $r1.mCategory, $r1.mPeople, $r1.mExtras, $r1.mColor, $r1.mVisibility, $r1.mPublicVersion, $r1.mGroupKey, $r1.mGroupSummary, $r1.mSortKey);
            ArrayList $r15 = $r1.mActions;
            NotificationCompat.addActionsToBuilder($r3, $r15);
            Style $r20 = $r1.mStyle;
            NotificationCompat.addStyleToBuilderJellybean($r3, $r20);
            return $r2.build($r1, $r3);
        }

        public String getCategory(Notification $r1) throws  {
            return NotificationCompatApi21.getCategory($r1);
        }

        public Bundle getBundleForUnreadConversation(UnreadConversation $r1) throws  {
            return NotificationCompatApi21.getBundleForUnreadConversation($r1);
        }

        public UnreadConversation getUnreadConversationFromBundle(Bundle $r1, UnreadConversation.Factory $r2, RemoteInput.Factory $r3) throws  {
            return NotificationCompatApi21.getUnreadConversationFromBundle($r1, $r2, $r3);
        }
    }

    static class NotificationCompatImplGingerbread extends NotificationCompatImplBase {
        NotificationCompatImplGingerbread() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender extender) throws  {
            Notification $r7 = NotificationCompatGingerbread.add($r1.mNotification, $r1.mContext, $r1.mContentTitle, $r1.mContentText, $r1.mContentIntent, $r1.mFullScreenIntent);
            if ($r1.mPriority <= 0) {
                return $r7;
            }
            $r7.flags |= 128;
            return $r7;
        }
    }

    static class NotificationCompatImplHoneycomb extends NotificationCompatImplBase {
        NotificationCompatImplHoneycomb() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender extender) throws  {
            return NotificationCompatHoneycomb.add($r1.mContext, $r1.mNotification, $r1.mContentTitle, $r1.mContentText, $r1.mContentInfo, $r1.mTickerView, $r1.mNumber, $r1.mContentIntent, $r1.mFullScreenIntent, $r1.mLargeIcon);
        }
    }

    static class NotificationCompatImplIceCreamSandwich extends NotificationCompatImplBase {
        NotificationCompatImplIceCreamSandwich() throws  {
        }

        public Notification build(Builder $r1, BuilderExtender $r2) throws  {
            return $r2.build($r1, new android.support.v4.app.NotificationCompatIceCreamSandwich.Builder($r1.mContext, $r1.mNotification, $r1.mContentTitle, $r1.mContentText, $r1.mContentInfo, $r1.mTickerView, $r1.mNumber, $r1.mContentIntent, $r1.mFullScreenIntent, $r1.mLargeIcon, $r1.mProgressMax, $r1.mProgress, $r1.mProgressIndeterminate));
        }
    }

    public static final class WearableExtender implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList();
        private Bitmap mBackground;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList();

        private void setFlag(int r1, boolean r2) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: android.support.v4.app.NotificationCompat.WearableExtender.setFlag(int, boolean):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:256)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 6 more
*/
            /*
            // Can't load method instructions.
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v4.app.NotificationCompat.WearableExtender.setFlag(int, boolean):void");
        }

        public WearableExtender(Notification $r1) throws  {
            Bundle $r3 = NotificationCompat.getExtras($r1);
            $r3 = $r3 != null ? $r3.getBundle(EXTRA_WEARABLE_EXTENSIONS) : null;
            if ($r3 != null) {
                Action[] $r5 = NotificationCompat.IMPL.getActionsFromParcelableArrayList($r3.getParcelableArrayList(KEY_ACTIONS));
                if ($r5 != null) {
                    Collections.addAll(this.mActions, $r5);
                }
                this.mFlags = $r3.getInt(KEY_FLAGS, 1);
                this.mDisplayIntent = (PendingIntent) $r3.getParcelable(KEY_DISPLAY_INTENT);
                Notification[] $r8 = NotificationCompat.getNotificationArrayFromBundle($r3, KEY_PAGES);
                if ($r8 != null) {
                    Collections.addAll(this.mPages, $r8);
                }
                this.mBackground = (Bitmap) $r3.getParcelable(KEY_BACKGROUND);
                this.mContentIcon = $r3.getInt(KEY_CONTENT_ICON);
                this.mContentIconGravity = $r3.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
                this.mContentActionIndex = $r3.getInt(KEY_CONTENT_ACTION_INDEX, -1);
                this.mCustomSizePreset = $r3.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
                this.mCustomContentHeight = $r3.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
                this.mGravity = $r3.getInt(KEY_GRAVITY, 80);
                this.mHintScreenTimeout = $r3.getInt(KEY_HINT_SCREEN_TIMEOUT);
            }
        }

        public Builder extend(Builder $r1) throws  {
            Bundle $r2 = new Bundle();
            if (!this.mActions.isEmpty()) {
                $r2.putParcelableArrayList(KEY_ACTIONS, NotificationCompat.IMPL.getParcelableArrayListForActions((Action[]) this.mActions.toArray(new Action[this.mActions.size()])));
            }
            if (this.mFlags != 1) {
                $r2.putInt(KEY_FLAGS, this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                $r2.putParcelable(KEY_DISPLAY_INTENT, this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                $r2.putParcelableArray(KEY_PAGES, (Parcelable[]) this.mPages.toArray(new Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                $r2.putParcelable(KEY_BACKGROUND, this.mBackground);
            }
            if (this.mContentIcon != 0) {
                $r2.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                $r2.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                $r2.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                $r2.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                $r2.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                $r2.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                $r2.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            $r1.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, $r2);
            return $r1;
        }

        public WearableExtender clone() throws  {
            WearableExtender $r1 = new WearableExtender();
            $r1.mActions = new ArrayList(this.mActions);
            $r1.mFlags = this.mFlags;
            $r1.mDisplayIntent = this.mDisplayIntent;
            $r1.mPages = new ArrayList(this.mPages);
            $r1.mBackground = this.mBackground;
            $r1.mContentIcon = this.mContentIcon;
            $r1.mContentIconGravity = this.mContentIconGravity;
            $r1.mContentActionIndex = this.mContentActionIndex;
            $r1.mCustomSizePreset = this.mCustomSizePreset;
            $r1.mCustomContentHeight = this.mCustomContentHeight;
            $r1.mGravity = this.mGravity;
            $r1.mHintScreenTimeout = this.mHintScreenTimeout;
            return $r1;
        }

        public WearableExtender addAction(Action $r1) throws  {
            this.mActions.add($r1);
            return this;
        }

        public WearableExtender addActions(@Signature({"(", "Ljava/util/List", "<", "Landroid/support/v4/app/NotificationCompat$Action;", ">;)", "Landroid/support/v4/app/NotificationCompat$WearableExtender;"}) List<Action> $r1) throws  {
            this.mActions.addAll($r1);
            return this;
        }

        public WearableExtender clearActions() throws  {
            this.mActions.clear();
            return this;
        }

        public List<Action> getActions() throws  {
            return this.mActions;
        }

        public WearableExtender setDisplayIntent(PendingIntent $r1) throws  {
            this.mDisplayIntent = $r1;
            return this;
        }

        public PendingIntent getDisplayIntent() throws  {
            return this.mDisplayIntent;
        }

        public WearableExtender addPage(Notification $r1) throws  {
            this.mPages.add($r1);
            return this;
        }

        public WearableExtender addPages(@Signature({"(", "Ljava/util/List", "<", "Landroid/app/Notification;", ">;)", "Landroid/support/v4/app/NotificationCompat$WearableExtender;"}) List<Notification> $r1) throws  {
            this.mPages.addAll($r1);
            return this;
        }

        public WearableExtender clearPages() throws  {
            this.mPages.clear();
            return this;
        }

        public List<Notification> getPages() throws  {
            return this.mPages;
        }

        public WearableExtender setBackground(Bitmap $r1) throws  {
            this.mBackground = $r1;
            return this;
        }

        public Bitmap getBackground() throws  {
            return this.mBackground;
        }

        public WearableExtender setContentIcon(int $i0) throws  {
            this.mContentIcon = $i0;
            return this;
        }

        public int getContentIcon() throws  {
            return this.mContentIcon;
        }

        public WearableExtender setContentIconGravity(int $i0) throws  {
            this.mContentIconGravity = $i0;
            return this;
        }

        public int getContentIconGravity() throws  {
            return this.mContentIconGravity;
        }

        public WearableExtender setContentAction(int $i0) throws  {
            this.mContentActionIndex = $i0;
            return this;
        }

        public int getContentAction() throws  {
            return this.mContentActionIndex;
        }

        public WearableExtender setGravity(int $i0) throws  {
            this.mGravity = $i0;
            return this;
        }

        public int getGravity() throws  {
            return this.mGravity;
        }

        public WearableExtender setCustomSizePreset(int $i0) throws  {
            this.mCustomSizePreset = $i0;
            return this;
        }

        public int getCustomSizePreset() throws  {
            return this.mCustomSizePreset;
        }

        public WearableExtender setCustomContentHeight(int $i0) throws  {
            this.mCustomContentHeight = $i0;
            return this;
        }

        public int getCustomContentHeight() throws  {
            return this.mCustomContentHeight;
        }

        public WearableExtender setStartScrollBottom(boolean $z0) throws  {
            setFlag(8, $z0);
            return this;
        }

        public boolean getStartScrollBottom() throws  {
            return (this.mFlags & 8) != 0;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean $z0) throws  {
            setFlag(1, $z0);
            return this;
        }

        public boolean getContentIntentAvailableOffline() throws  {
            return (this.mFlags & 1) != 0;
        }

        public WearableExtender setHintHideIcon(boolean $z0) throws  {
            setFlag(2, $z0);
            return this;
        }

        public boolean getHintHideIcon() throws  {
            return (this.mFlags & 2) != 0;
        }

        public WearableExtender setHintShowBackgroundOnly(boolean $z0) throws  {
            setFlag(4, $z0);
            return this;
        }

        public boolean getHintShowBackgroundOnly() throws  {
            return (this.mFlags & 4) != 0;
        }

        public WearableExtender setHintAvoidBackgroundClipping(boolean $z0) throws  {
            setFlag(16, $z0);
            return this;
        }

        public boolean getHintAvoidBackgroundClipping() throws  {
            return (this.mFlags & 16) != 0;
        }

        public WearableExtender setHintScreenTimeout(int $i0) throws  {
            this.mHintScreenTimeout = $i0;
            return this;
        }

        public int getHintScreenTimeout() throws  {
            return this.mHintScreenTimeout;
        }
    }

    private static void addActionsToBuilder(@Signature({"(", "Landroid/support/v4/app/NotificationBuilderWithActions;", "Ljava/util/ArrayList", "<", "Landroid/support/v4/app/NotificationCompat$Action;", ">;)V"}) NotificationBuilderWithActions $r0, @Signature({"(", "Landroid/support/v4/app/NotificationBuilderWithActions;", "Ljava/util/ArrayList", "<", "Landroid/support/v4/app/NotificationCompat$Action;", ">;)V"}) ArrayList<Action> $r1) throws  {
        Iterator $r2 = $r1.iterator();
        while ($r2.hasNext()) {
            $r0.addAction((Action) $r2.next());
        }
    }

    private static void addStyleToBuilderJellybean(NotificationBuilderWithBuilderAccessor $r0, Style $r1) throws  {
        if ($r1 == null) {
            return;
        }
        if ($r1 instanceof BigTextStyle) {
            BigTextStyle $r4 = (BigTextStyle) $r1;
            NotificationCompatJellybean.addBigTextStyle($r0, $r4.mBigContentTitle, $r4.mSummaryTextSet, $r4.mSummaryText, $r4.mBigText);
        } else if ($r1 instanceof InboxStyle) {
            InboxStyle $r8 = (InboxStyle) $r1;
            NotificationCompatJellybean.addInboxStyle($r0, $r8.mBigContentTitle, $r8.mSummaryTextSet, $r8.mSummaryText, $r8.mTexts);
        } else if ($r1 instanceof BigPictureStyle) {
            Style style = (BigPictureStyle) $r1;
            NotificationCompatJellybean.addBigPictureStyle($r0, style.mBigContentTitle, style.mSummaryTextSet, style.mSummaryText, style.mPicture, style.mBigLargeIcon, style.mBigLargeIconSet);
        }
    }

    static {
        if (VERSION.SDK_INT >= 21) {
            IMPL = new NotificationCompatImplApi21();
        } else if (VERSION.SDK_INT >= 20) {
            IMPL = new NotificationCompatImplApi20();
        } else if (VERSION.SDK_INT >= 19) {
            IMPL = new NotificationCompatImplKitKat();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new NotificationCompatImplJellybean();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new NotificationCompatImplIceCreamSandwich();
        } else if (VERSION.SDK_INT >= 11) {
            IMPL = new NotificationCompatImplHoneycomb();
        } else if (VERSION.SDK_INT >= 9) {
            IMPL = new NotificationCompatImplGingerbread();
        } else {
            IMPL = new NotificationCompatImplBase();
        }
    }

    private static Notification[] getNotificationArrayFromBundle(Bundle $r0, String $r1) throws  {
        Parcelable[] $r2 = $r0.getParcelableArray($r1);
        if (($r2 instanceof Notification[]) || $r2 == null) {
            return (Notification[]) $r2;
        }
        Notification[] $r3 = new Notification[$r2.length];
        for (int $i0 = 0; $i0 < $r2.length; $i0++) {
            $r3[$i0] = (Notification) $r2[$i0];
        }
        $r0.putParcelableArray($r1, $r3);
        return $r3;
    }

    public static Bundle getExtras(Notification $r0) throws  {
        return IMPL.getExtras($r0);
    }

    public static int getActionCount(Notification $r0) throws  {
        return IMPL.getActionCount($r0);
    }

    public static Action getAction(Notification $r0, int $i0) throws  {
        return IMPL.getAction($r0, $i0);
    }

    public static String getCategory(Notification $r0) throws  {
        return IMPL.getCategory($r0);
    }

    public static boolean getLocalOnly(Notification $r0) throws  {
        return IMPL.getLocalOnly($r0);
    }

    public static String getGroup(Notification $r0) throws  {
        return IMPL.getGroup($r0);
    }

    public static boolean isGroupSummary(Notification $r0) throws  {
        return IMPL.isGroupSummary($r0);
    }

    public static String getSortKey(Notification $r0) throws  {
        return IMPL.getSortKey($r0);
    }
}
