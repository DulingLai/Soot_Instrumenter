package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Parcelable;
import android.support.annotation.StringRes;
import android.support.v4.content.IntentCompat;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import dalvik.annotation.Signature;
import java.util.ArrayList;

public final class ShareCompat {
    public static final String EXTRA_CALLING_ACTIVITY = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_PACKAGE = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
    private static ShareCompatImpl IMPL;

    public static class IntentBuilder {
        private Activity mActivity;
        private ArrayList<String> mBccAddresses;
        private ArrayList<String> mCcAddresses;
        private CharSequence mChooserTitle;
        private Intent mIntent = new Intent().setAction("android.intent.action.SEND");
        private ArrayList<Uri> mStreams;
        private ArrayList<String> mToAddresses;

        public static IntentBuilder from(Activity $r0) throws  {
            return new IntentBuilder($r0);
        }

        private IntentBuilder(Activity $r1) throws  {
            this.mActivity = $r1;
            this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, $r1.getPackageName());
            this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, $r1.getComponentName());
            this.mIntent.addFlags(524288);
        }

        public Intent getIntent() throws  {
            boolean $z0 = true;
            if (this.mToAddresses != null) {
                combineArrayExtra("android.intent.extra.EMAIL", this.mToAddresses);
                this.mToAddresses = null;
            }
            if (this.mCcAddresses != null) {
                combineArrayExtra("android.intent.extra.CC", this.mCcAddresses);
                this.mCcAddresses = null;
            }
            if (this.mBccAddresses != null) {
                combineArrayExtra("android.intent.extra.BCC", this.mBccAddresses);
                this.mBccAddresses = null;
            }
            if (this.mStreams == null || this.mStreams.size() <= 1) {
                $z0 = false;
            }
            boolean $z1 = this.mIntent.getAction().equals("android.intent.action.SEND_MULTIPLE");
            if (!$z0 && $z1) {
                this.mIntent.setAction("android.intent.action.SEND");
                if (this.mStreams == null || this.mStreams.isEmpty()) {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                } else {
                    this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable) this.mStreams.get(0));
                }
                this.mStreams = null;
            }
            if ($z0 && !$z1) {
                this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
                if (this.mStreams == null || this.mStreams.isEmpty()) {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                } else {
                    this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
                }
            }
            return this.mIntent;
        }

        Activity getActivity() throws  {
            return this.mActivity;
        }

        private void combineArrayExtra(@Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) String $r1, @Signature({"(", "Ljava/lang/String;", "Ljava/util/ArrayList", "<", "Ljava/lang/String;", ">;)V"}) ArrayList<String> $r2) throws  {
            int $i0;
            String[] $r5 = this.mIntent.getStringArrayExtra($r1);
            if ($r5 != null) {
                $i0 = $r5.length;
            } else {
                $i0 = 0;
            }
            String[] $r3 = new String[($r2.size() + $i0)];
            $r2.toArray($r3);
            if ($r5 != null) {
                System.arraycopy($r5, 0, $r3, $r2.size(), $i0);
            }
            this.mIntent.putExtra($r1, $r3);
        }

        private void combineArrayExtra(String $r1, String[] $r2) throws  {
            int $i0;
            Intent $r4 = getIntent();
            String[] $r5 = $r4.getStringArrayExtra($r1);
            if ($r5 != null) {
                $i0 = $r5.length;
            } else {
                $i0 = 0;
            }
            String[] $r3 = new String[($r2.length + $i0)];
            if ($r5 != null) {
                System.arraycopy($r5, 0, $r3, 0, $i0);
            }
            System.arraycopy($r2, 0, $r3, $i0, $r2.length);
            $r4.putExtra($r1, $r3);
        }

        public Intent createChooserIntent() throws  {
            return Intent.createChooser(getIntent(), this.mChooserTitle);
        }

        public void startChooser() throws  {
            this.mActivity.startActivity(createChooserIntent());
        }

        public IntentBuilder setChooserTitle(CharSequence $r1) throws  {
            this.mChooserTitle = $r1;
            return this;
        }

        public IntentBuilder setChooserTitle(@StringRes int $i0) throws  {
            return setChooserTitle(this.mActivity.getText($i0));
        }

        public IntentBuilder setType(String $r1) throws  {
            this.mIntent.setType($r1);
            return this;
        }

        public IntentBuilder setText(CharSequence $r1) throws  {
            this.mIntent.putExtra("android.intent.extra.TEXT", $r1);
            return this;
        }

        public IntentBuilder setHtmlText(String $r1) throws  {
            this.mIntent.putExtra(IntentCompat.EXTRA_HTML_TEXT, $r1);
            if (this.mIntent.hasExtra("android.intent.extra.TEXT")) {
                return this;
            }
            setText(Html.fromHtml($r1));
            return this;
        }

        public IntentBuilder setStream(Uri $r1) throws  {
            if (!this.mIntent.getAction().equals("android.intent.action.SEND")) {
                this.mIntent.setAction("android.intent.action.SEND");
            }
            this.mStreams = null;
            this.mIntent.putExtra("android.intent.extra.STREAM", $r1);
            return this;
        }

        public IntentBuilder addStream(Uri $r0) throws  {
            Uri $r4 = (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            if (this.mStreams == null && $r4 == null) {
                return setStream($r0);
            }
            if (this.mStreams == null) {
                this.mStreams = new ArrayList();
            }
            if ($r4 != null) {
                this.mIntent.removeExtra("android.intent.extra.STREAM");
                this.mStreams.add($r4);
            }
            this.mStreams.add($r0);
            return this;
        }

        public IntentBuilder setEmailTo(String[] $r1) throws  {
            if (this.mToAddresses != null) {
                this.mToAddresses = null;
            }
            this.mIntent.putExtra("android.intent.extra.EMAIL", $r1);
            return this;
        }

        public IntentBuilder addEmailTo(String $r1) throws  {
            if (this.mToAddresses == null) {
                this.mToAddresses = new ArrayList();
            }
            this.mToAddresses.add($r1);
            return this;
        }

        public IntentBuilder addEmailTo(String[] $r1) throws  {
            combineArrayExtra("android.intent.extra.EMAIL", $r1);
            return this;
        }

        public IntentBuilder setEmailCc(String[] $r1) throws  {
            this.mIntent.putExtra("android.intent.extra.CC", $r1);
            return this;
        }

        public IntentBuilder addEmailCc(String $r1) throws  {
            if (this.mCcAddresses == null) {
                this.mCcAddresses = new ArrayList();
            }
            this.mCcAddresses.add($r1);
            return this;
        }

        public IntentBuilder addEmailCc(String[] $r1) throws  {
            combineArrayExtra("android.intent.extra.CC", $r1);
            return this;
        }

        public IntentBuilder setEmailBcc(String[] $r1) throws  {
            this.mIntent.putExtra("android.intent.extra.BCC", $r1);
            return this;
        }

        public IntentBuilder addEmailBcc(String $r1) throws  {
            if (this.mBccAddresses == null) {
                this.mBccAddresses = new ArrayList();
            }
            this.mBccAddresses.add($r1);
            return this;
        }

        public IntentBuilder addEmailBcc(String[] $r1) throws  {
            combineArrayExtra("android.intent.extra.BCC", $r1);
            return this;
        }

        public IntentBuilder setSubject(String $r1) throws  {
            this.mIntent.putExtra("android.intent.extra.SUBJECT", $r1);
            return this;
        }
    }

    public static class IntentReader {
        private static final String TAG = "IntentReader";
        private Activity mActivity;
        private ComponentName mCallingActivity;
        private String mCallingPackage;
        private Intent mIntent;
        private ArrayList<Uri> mStreams;

        public static IntentReader from(Activity $r0) throws  {
            return new IntentReader($r0);
        }

        private IntentReader(Activity $r1) throws  {
            this.mActivity = $r1;
            this.mIntent = $r1.getIntent();
            this.mCallingPackage = ShareCompat.getCallingPackage($r1);
            this.mCallingActivity = ShareCompat.getCallingActivity($r1);
        }

        public boolean isShareIntent() throws  {
            String $r2 = this.mIntent.getAction();
            return "android.intent.action.SEND".equals($r2) || "android.intent.action.SEND_MULTIPLE".equals($r2);
        }

        public boolean isSingleShare() throws  {
            return "android.intent.action.SEND".equals(this.mIntent.getAction());
        }

        public boolean isMultipleShare() throws  {
            return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
        }

        public String getType() throws  {
            return this.mIntent.getType();
        }

        public CharSequence getText() throws  {
            return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
        }

        public String getHtmlText() throws  {
            String $r2 = this.mIntent.getStringExtra(IntentCompat.EXTRA_HTML_TEXT);
            if ($r2 != null) {
                return $r2;
            }
            CharSequence $r3 = getText();
            if ($r3 instanceof Spanned) {
                return Html.toHtml((Spanned) $r3);
            }
            return $r3 != null ? ShareCompat.IMPL.escapeHtml($r3) : $r2;
        }

        public Uri getStream() throws  {
            return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
        }

        public Uri getStream(int $i0) throws  {
            if (this.mStreams == null && isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if (this.mStreams != null) {
                return (Uri) this.mStreams.get($i0);
            }
            if ($i0 == 0) {
                return (Uri) this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            }
            throw new IndexOutOfBoundsException("Stream items available: " + getStreamCount() + " index requested: " + $i0);
        }

        public int getStreamCount() throws  {
            if (this.mStreams == null && isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if (this.mStreams != null) {
                return this.mStreams.size();
            }
            return this.mIntent.hasExtra("android.intent.extra.STREAM") ? 1 : 0;
        }

        public String[] getEmailTo() throws  {
            return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
        }

        public String[] getEmailCc() throws  {
            return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
        }

        public String[] getEmailBcc() throws  {
            return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
        }

        public String getSubject() throws  {
            return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
        }

        public String getCallingPackage() throws  {
            return this.mCallingPackage;
        }

        public ComponentName getCallingActivity() throws  {
            return this.mCallingActivity;
        }

        public Drawable getCallingActivityIcon() throws  {
            if (this.mCallingActivity == null) {
                return null;
            }
            try {
                return this.mActivity.getPackageManager().getActivityIcon(this.mCallingActivity);
            } catch (NameNotFoundException $r1) {
                Log.e(TAG, "Could not retrieve icon for calling activity", $r1);
                return null;
            }
        }

        public Drawable getCallingApplicationIcon() throws  {
            if (this.mCallingPackage == null) {
                return null;
            }
            try {
                return this.mActivity.getPackageManager().getApplicationIcon(this.mCallingPackage);
            } catch (NameNotFoundException $r1) {
                Log.e(TAG, "Could not retrieve icon for calling application", $r1);
                return null;
            }
        }

        public CharSequence getCallingApplicationLabel() throws  {
            if (this.mCallingPackage == null) {
                return null;
            }
            PackageManager $r4 = this.mActivity.getPackageManager();
            try {
                return $r4.getApplicationLabel($r4.getApplicationInfo(this.mCallingPackage, 0));
            } catch (NameNotFoundException $r1) {
                Log.e(TAG, "Could not retrieve label for calling application", $r1);
                return null;
            }
        }
    }

    interface ShareCompatImpl {
        void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder) throws ;

        String escapeHtml(CharSequence charSequence) throws ;
    }

    static class ShareCompatImplBase implements ShareCompatImpl {
        ShareCompatImplBase() throws  {
        }

        public void configureMenuItem(MenuItem $r1, IntentBuilder $r2) throws  {
            $r1.setIntent($r2.createChooserIntent());
        }

        public String escapeHtml(CharSequence $r1) throws  {
            StringBuilder $r2 = new StringBuilder();
            withinStyle($r2, $r1, 0, $r1.length());
            return $r2.toString();
        }

        private static void withinStyle(StringBuilder $r0, CharSequence $r1, int $i0, int $i1) throws  {
            while ($i0 < $i1) {
                char $c2 = $r1.charAt($i0);
                if ($c2 == '<') {
                    $r0.append("&lt;");
                } else if ($c2 == '>') {
                    $r0.append("&gt;");
                } else if ($c2 == '&') {
                    $r0.append("&amp;");
                } else if ($c2 > '~' || $c2 < ' ') {
                    $r0.append("&#" + $c2 + ";");
                } else if ($c2 == ' ') {
                    while ($i0 + 1 < $i1 && $r1.charAt($i0 + 1) == ' ') {
                        $r0.append("&nbsp;");
                        $i0++;
                    }
                    $r0.append(' ');
                } else {
                    $r0.append($c2);
                }
                $i0++;
            }
        }
    }

    static class ShareCompatImplICS extends ShareCompatImplBase {
        ShareCompatImplICS() throws  {
        }

        public void configureMenuItem(MenuItem $r1, IntentBuilder $r2) throws  {
            ShareCompatICS.configureMenuItem($r1, $r2.getActivity(), $r2.getIntent());
            if (shouldAddChooserIntent($r1)) {
                $r1.setIntent($r2.createChooserIntent());
            }
        }

        boolean shouldAddChooserIntent(MenuItem $r1) throws  {
            return !$r1.hasSubMenu();
        }
    }

    static class ShareCompatImplJB extends ShareCompatImplICS {
        boolean shouldAddChooserIntent(MenuItem item) throws  {
            return false;
        }

        ShareCompatImplJB() throws  {
        }

        public String escapeHtml(CharSequence $r1) throws  {
            return ShareCompatJB.escapeHtml($r1);
        }
    }

    static {
        if (VERSION.SDK_INT >= 16) {
            IMPL = new ShareCompatImplJB();
        } else if (VERSION.SDK_INT >= 14) {
            IMPL = new ShareCompatImplICS();
        } else {
            IMPL = new ShareCompatImplBase();
        }
    }

    private ShareCompat() throws  {
    }

    public static String getCallingPackage(Activity $r0) throws  {
        String $r1 = $r0.getCallingPackage();
        if ($r1 == null) {
            return $r0.getIntent().getStringExtra(EXTRA_CALLING_PACKAGE);
        }
        return $r1;
    }

    public static ComponentName getCallingActivity(Activity $r0) throws  {
        ComponentName $r1 = $r0.getCallingActivity();
        if ($r1 == null) {
            return (ComponentName) $r0.getIntent().getParcelableExtra(EXTRA_CALLING_ACTIVITY);
        }
        return $r1;
    }

    public static void configureMenuItem(MenuItem $r0, IntentBuilder $r1) throws  {
        IMPL.configureMenuItem($r0, $r1);
    }

    public static void configureMenuItem(Menu $r0, int $i0, IntentBuilder $r1) throws  {
        MenuItem $r3 = $r0.findItem($i0);
        if ($r3 == null) {
            throw new IllegalArgumentException("Could not find menu item with id " + $i0 + " in the supplied menu");
        }
        configureMenuItem($r3, $r1);
    }
}
