package android.support.v4.app;

import android.content.Intent;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.RemoteInputCompatBase.RemoteInput.Factory;
import android.util.Log;

public final class RemoteInput extends android.support.v4.app.RemoteInputCompatBase.RemoteInput {
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    public static final Factory FACTORY = new C00401();
    private static final Impl IMPL;
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    private static final String TAG = "RemoteInput";
    private final boolean mAllowFreeFormInput;
    private final CharSequence[] mChoices;
    private final Bundle mExtras;
    private final CharSequence mLabel;
    private final String mResultKey;

    static class C00401 implements Factory {
        C00401() throws  {
        }

        public RemoteInput build(String $r1, CharSequence $r2, CharSequence[] $r3, boolean $z0, Bundle $r4) throws  {
            return new RemoteInput($r1, $r2, $r3, $z0, $r4);
        }

        public RemoteInput[] newArray(int $i0) throws  {
            return new RemoteInput[$i0];
        }
    }

    public static final class Builder {
        private boolean mAllowFreeFormInput = true;
        private CharSequence[] mChoices;
        private Bundle mExtras = new Bundle();
        private CharSequence mLabel;
        private final String mResultKey;

        public Builder(String $r1) throws  {
            if ($r1 == null) {
                throw new IllegalArgumentException("Result key can't be null");
            }
            this.mResultKey = $r1;
        }

        public Builder setLabel(CharSequence $r1) throws  {
            this.mLabel = $r1;
            return this;
        }

        public Builder setChoices(CharSequence[] $r1) throws  {
            this.mChoices = $r1;
            return this;
        }

        public Builder setAllowFreeFormInput(boolean $z0) throws  {
            this.mAllowFreeFormInput = $z0;
            return this;
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

        public RemoteInput build() throws  {
            return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormInput, this.mExtras);
        }
    }

    interface Impl {
        void addResultsToIntent(RemoteInput[] remoteInputArr, Intent intent, Bundle bundle) throws ;

        Bundle getResultsFromIntent(Intent intent) throws ;
    }

    static class ImplApi20 implements Impl {
        ImplApi20() throws  {
        }

        public Bundle getResultsFromIntent(Intent $r1) throws  {
            return RemoteInputCompatApi20.getResultsFromIntent($r1);
        }

        public void addResultsToIntent(RemoteInput[] $r1, Intent $r2, Bundle $r3) throws  {
            RemoteInputCompatApi20.addResultsToIntent($r1, $r2, $r3);
        }
    }

    static class ImplBase implements Impl {
        ImplBase() throws  {
        }

        public Bundle getResultsFromIntent(Intent intent) throws  {
            Log.w(RemoteInput.TAG, "RemoteInput is only supported from API Level 16");
            return null;
        }

        public void addResultsToIntent(RemoteInput[] remoteInputs, Intent intent, Bundle results) throws  {
            Log.w(RemoteInput.TAG, "RemoteInput is only supported from API Level 16");
        }
    }

    static class ImplJellybean implements Impl {
        ImplJellybean() throws  {
        }

        public Bundle getResultsFromIntent(Intent $r1) throws  {
            return RemoteInputCompatJellybean.getResultsFromIntent($r1);
        }

        public void addResultsToIntent(RemoteInput[] $r1, Intent $r2, Bundle $r3) throws  {
            RemoteInputCompatJellybean.addResultsToIntent($r1, $r2, $r3);
        }
    }

    private RemoteInput(String $r1, CharSequence $r2, CharSequence[] $r3, boolean $z0, Bundle $r4) throws  {
        this.mResultKey = $r1;
        this.mLabel = $r2;
        this.mChoices = $r3;
        this.mAllowFreeFormInput = $z0;
        this.mExtras = $r4;
    }

    public String getResultKey() throws  {
        return this.mResultKey;
    }

    public CharSequence getLabel() throws  {
        return this.mLabel;
    }

    public CharSequence[] getChoices() throws  {
        return this.mChoices;
    }

    public boolean getAllowFreeFormInput() throws  {
        return this.mAllowFreeFormInput;
    }

    public Bundle getExtras() throws  {
        return this.mExtras;
    }

    public static Bundle getResultsFromIntent(Intent $r0) throws  {
        return IMPL.getResultsFromIntent($r0);
    }

    public static void addResultsToIntent(RemoteInput[] $r0, Intent $r1, Bundle $r2) throws  {
        IMPL.addResultsToIntent($r0, $r1, $r2);
    }

    static {
        if (VERSION.SDK_INT >= 20) {
            IMPL = new ImplApi20();
        } else if (VERSION.SDK_INT >= 16) {
            IMPL = new ImplJellybean();
        } else {
            IMPL = new ImplBase();
        }
    }
}
