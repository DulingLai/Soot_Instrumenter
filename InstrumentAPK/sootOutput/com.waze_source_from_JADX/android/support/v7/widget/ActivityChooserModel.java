package android.support.v7.widget;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.database.DataSetObservable;
import android.os.AsyncTask;
import android.support.v4.os.AsyncTaskCompat;
import android.text.TextUtils;
import dalvik.annotation.Signature;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class ActivityChooserModel extends DataSetObservable {
    private static final String ATTRIBUTE_ACTIVITY = "activity";
    private static final String ATTRIBUTE_TIME = "time";
    private static final String ATTRIBUTE_WEIGHT = "weight";
    private static final boolean DEBUG = false;
    private static final int DEFAULT_ACTIVITY_INFLATION = 5;
    private static final float DEFAULT_HISTORICAL_RECORD_WEIGHT = 1.0f;
    public static final String DEFAULT_HISTORY_FILE_NAME = "activity_choser_model_history.xml";
    public static final int DEFAULT_HISTORY_MAX_LENGTH = 50;
    private static final String HISTORY_FILE_EXTENSION = ".xml";
    private static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = ActivityChooserModel.class.getSimpleName();
    private static final String TAG_HISTORICAL_RECORD = "historical-record";
    private static final String TAG_HISTORICAL_RECORDS = "historical-records";
    private static final Map<String, ActivityChooserModel> sDataModelRegistry = new HashMap();
    private static final Object sRegistryLock = new Object();
    private final List<ActivityResolveInfo> mActivities = new ArrayList();
    private OnChooseActivityListener mActivityChoserModelPolicy;
    private ActivitySorter mActivitySorter = new DefaultSorter();
    private boolean mCanReadHistoricalData = true;
    private final Context mContext;
    private final List<HistoricalRecord> mHistoricalRecords = new ArrayList();
    private boolean mHistoricalRecordsChanged = true;
    private final String mHistoryFileName;
    private int mHistoryMaxSize = 50;
    private final Object mInstanceLock = new Object();
    private Intent mIntent;
    private boolean mReadShareHistoryCalled = false;
    private boolean mReloadActivities = false;

    public interface ActivityChooserModelClient {
        void setActivityChooserModel(ActivityChooserModel activityChooserModel) throws ;
    }

    public final class ActivityResolveInfo implements Comparable<ActivityResolveInfo> {
        public final ResolveInfo resolveInfo;
        public float weight;

        public ActivityResolveInfo(ResolveInfo $r2) throws  {
            this.resolveInfo = $r2;
        }

        public int hashCode() throws  {
            return Float.floatToIntBits(this.weight) + 31;
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null) {
                return false;
            }
            if (getClass() != $r1.getClass()) {
                return false;
            }
            return Float.floatToIntBits(this.weight) == Float.floatToIntBits(((ActivityResolveInfo) $r1).weight);
        }

        public int compareTo(ActivityResolveInfo $r1) throws  {
            return Float.floatToIntBits($r1.weight) - Float.floatToIntBits(this.weight);
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder();
            $r1.append("[");
            $r1.append("resolveInfo:").append(this.resolveInfo.toString());
            $r1.append("; weight:").append(new BigDecimal((double) this.weight));
            $r1.append("]");
            return $r1.toString();
        }
    }

    public interface ActivitySorter {
        void sort(@Signature({"(", "Landroid/content/Intent;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$ActivityResolveInfo;", ">;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$HistoricalRecord;", ">;)V"}) Intent intent, @Signature({"(", "Landroid/content/Intent;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$ActivityResolveInfo;", ">;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$HistoricalRecord;", ">;)V"}) List<ActivityResolveInfo> list, @Signature({"(", "Landroid/content/Intent;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$ActivityResolveInfo;", ">;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$HistoricalRecord;", ">;)V"}) List<HistoricalRecord> list2) throws ;
    }

    private final class DefaultSorter implements ActivitySorter {
        private static final float WEIGHT_DECAY_COEFFICIENT = 0.95f;
        private final Map<ComponentName, ActivityResolveInfo> mPackageNameToActivityMap;

        private DefaultSorter() throws  {
            this.mPackageNameToActivityMap = new HashMap();
        }

        public void sort(@Signature({"(", "Landroid/content/Intent;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$ActivityResolveInfo;", ">;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$HistoricalRecord;", ">;)V"}) Intent intent, @Signature({"(", "Landroid/content/Intent;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$ActivityResolveInfo;", ">;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$HistoricalRecord;", ">;)V"}) List<ActivityResolveInfo> $r2, @Signature({"(", "Landroid/content/Intent;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$ActivityResolveInfo;", ">;", "Ljava/util/List", "<", "Landroid/support/v7/widget/ActivityChooserModel$HistoricalRecord;", ">;)V"}) List<HistoricalRecord> $r3) throws  {
            Map $r4 = this.mPackageNameToActivityMap;
            $r4.clear();
            int $i0 = $r2.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                ActivityResolveInfo $r6 = (ActivityResolveInfo) $r2.get($i1);
                $r6.weight = 0.0f;
                $r4.put(new ComponentName($r6.resolveInfo.activityInfo.packageName, $r6.resolveInfo.activityInfo.name), $r6);
            }
            float $f0 = 1.0f;
            for ($i0 = $r3.size() - 1; $i0 >= 0; $i0--) {
                HistoricalRecord $r12 = (HistoricalRecord) $r3.get($i0);
                $r6 = (ActivityResolveInfo) $r4.get($r12.activity);
                if ($r6 != null) {
                    float $f1 = $r6.weight;
                    float $f2 = $r12.weight;
                    $f2 = $f1 + ($f2 * $f0);
                    $f1 = $f2;
                    $r6.weight = $f2;
                    $f0 *= 0.95f;
                }
            }
            Collections.sort($r2);
        }
    }

    public static final class HistoricalRecord {
        public final ComponentName activity;
        public final long time;
        public final float weight;

        public HistoricalRecord(String $r1, long $l0, float $f0) throws  {
            this(ComponentName.unflattenFromString($r1), $l0, $f0);
        }

        public HistoricalRecord(ComponentName $r1, long $l0, float $f0) throws  {
            this.activity = $r1;
            this.time = $l0;
            this.weight = $f0;
        }

        public int hashCode() throws  {
            return (((((this.activity == null ? 0 : this.activity.hashCode()) + 31) * 31) + ((int) (this.time ^ (this.time >>> 32)))) * 31) + Float.floatToIntBits(this.weight);
        }

        public boolean equals(Object $r1) throws  {
            if (this == $r1) {
                return true;
            }
            if ($r1 == null) {
                return false;
            }
            if (getClass() != $r1.getClass()) {
                return false;
            }
            HistoricalRecord $r4 = (HistoricalRecord) $r1;
            if (this.activity == null) {
                if ($r4.activity != null) {
                    return false;
                }
            } else if (!this.activity.equals($r4.activity)) {
                return false;
            }
            if (this.time != $r4.time) {
                return false;
            }
            return Float.floatToIntBits(this.weight) == Float.floatToIntBits($r4.weight);
        }

        public String toString() throws  {
            StringBuilder $r1 = new StringBuilder();
            $r1.append("[");
            $r1.append("; activity:").append(this.activity);
            $r1.append("; time:").append(this.time);
            $r1.append("; weight:").append(new BigDecimal((double) this.weight));
            $r1.append("]");
            return $r1.toString();
        }
    }

    public interface OnChooseActivityListener {
        boolean onChooseActivity(ActivityChooserModel activityChooserModel, Intent intent) throws ;
    }

    private final class PersistHistoryAsyncTask extends AsyncTask<Object, Void, Void> {
        public java.lang.Void doInBackground(java.lang.Object... r37) throws  {
            /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:34:0x0114
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
            /*
            r36 = this;
            r4 = 0;
            r3 = r37[r4];
            r6 = r3;
            r6 = (java.util.List) r6;
            r5 = r6;
            r4 = 1;
            r3 = r37[r4];
            r8 = r3;
            r8 = (java.lang.String) r8;
            r7 = r8;
            r0 = r36;
            r9 = android.support.v7.widget.ActivityChooserModel.this;
            r10 = r9.mContext;	 Catch:{ FileNotFoundException -> 0x0091 }
            r4 = 0;	 Catch:{ FileNotFoundException -> 0x0091 }
            r11 = r10.openFileOutput(r7, r4);	 Catch:{ FileNotFoundException -> 0x0091 }
            r12 = android.util.Xml.newSerializer();
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.setOutput(r11, r13);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r4 = 1;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r14 = java.lang.Boolean.valueOf(r4);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "UTF-8";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.startDocument(r15, r14);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "historical-records";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.startTag(r13, r15);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r16 = r5.size();	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r17 = 0;
        L_0x0039:
            r0 = r17;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r1 = r16;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            if (r0 >= r1) goto L_0x00ba;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
        L_0x003f:
            r4 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r3 = r5.remove(r4);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r19 = r3;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r19 = (android.support.v7.widget.ActivityChooserModel.HistoricalRecord) r19;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r18 = r19;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "historical-record";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.startTag(r13, r15);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r18;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r0.activity;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r20 = r0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r21 = r0.flattenToString();	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "activity";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r21;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.attribute(r13, r15, r0);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r18;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r0.time;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r22 = r0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r21 = java.lang.String.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "time";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r21;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.attribute(r13, r15, r0);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r18;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r0.weight;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r24 = r0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r21 = java.lang.String.valueOf(r0);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "weight";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r21;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.attribute(r13, r15, r0);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "historical-record";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.endTag(r13, r15);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r17 = r17 + 1;
            goto L_0x0039;
        L_0x0091:
            r25 = move-exception;
            r21 = android.support.v7.widget.ActivityChooserModel.LOG_TAG;
            r26 = new java.lang.StringBuilder;
            r0 = r26;
            r0.<init>();
            r15 = "Error writing historical recrod file: ";
            r0 = r26;
            r26 = r0.append(r15);
            r0 = r26;
            r26 = r0.append(r7);
            r0 = r26;
            r7 = r0.toString();
            r0 = r21;
            r1 = r25;
            android.util.Log.e(r0, r7, r1);
            r13 = 0;
            return r13;
        L_0x00ba:
            r13 = 0;	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r15 = "historical-records";	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.endTag(r13, r15);	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r12.endDocument();	 Catch:{ IllegalArgumentException -> 0x00d2, IllegalStateException -> 0x0119, IOException -> 0x0164 }
            r0 = r36;
            r9 = android.support.v7.widget.ActivityChooserModel.this;
            r4 = 1;
            r9.mCanReadHistoricalData = r4;
            if (r11 == 0) goto L_0x00d0;
        L_0x00cd:
            r11.close();	 Catch:{ IOException -> 0x01b2 }
        L_0x00d0:
            r13 = 0;
            return r13;
        L_0x00d2:
            r27 = move-exception;
            r21 = android.support.v7.widget.ActivityChooserModel.LOG_TAG;	 Catch:{ Throwable -> 0x01a3 }
            r26 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r0.<init>();	 Catch:{ Throwable -> 0x01a3 }
            r15 = "Error writing historical recrod file: ";	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r26 = r0.append(r15);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r36;	 Catch:{ Throwable -> 0x01a3 }
            r9 = android.support.v7.widget.ActivityChooserModel.this;	 Catch:{ Throwable -> 0x01a3 }
            r7 = r9.mHistoryFileName;	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r26 = r0.append(r7);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r7 = r0.toString();	 Catch:{ Throwable -> 0x01a3 }
            r0 = r21;	 Catch:{ Throwable -> 0x01a3 }
            r1 = r27;	 Catch:{ Throwable -> 0x01a3 }
            android.util.Log.e(r0, r7, r1);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r36;
            r9 = android.support.v7.widget.ActivityChooserModel.this;
            r4 = 1;
            r9.mCanReadHistoricalData = r4;
            if (r11 == 0) goto L_0x00d0;
        L_0x010b:
            r11.close();	 Catch:{ IOException -> 0x010f }
            goto L_0x00d0;
        L_0x010f:
            r28 = move-exception;
            goto L_0x0118;
        L_0x0111:
            goto L_0x00d0;
            goto L_0x0118;
        L_0x0115:
            goto L_0x00d0;
        L_0x0118:
            goto L_0x00d0;
        L_0x0119:
            r29 = move-exception;
            r21 = android.support.v7.widget.ActivityChooserModel.LOG_TAG;	 Catch:{ Throwable -> 0x01a3 }
            r26 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r0.<init>();	 Catch:{ Throwable -> 0x01a3 }
            r15 = "Error writing historical recrod file: ";	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r26 = r0.append(r15);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r36;	 Catch:{ Throwable -> 0x01a3 }
            r9 = android.support.v7.widget.ActivityChooserModel.this;	 Catch:{ Throwable -> 0x01a3 }
            r7 = r9.mHistoryFileName;	 Catch:{ Throwable -> 0x01a3 }
            goto L_0x0139;	 Catch:{ Throwable -> 0x01a3 }
        L_0x0136:
            goto L_0x00d0;	 Catch:{ Throwable -> 0x01a3 }
        L_0x0139:
            goto L_0x0141;	 Catch:{ Throwable -> 0x01a3 }
        L_0x013a:
            goto L_0x00d0;	 Catch:{ Throwable -> 0x01a3 }
            goto L_0x0141;	 Catch:{ Throwable -> 0x01a3 }
        L_0x013e:
            goto L_0x00d0;	 Catch:{ Throwable -> 0x01a3 }
        L_0x0141:
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r26 = r0.append(r7);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r7 = r0.toString();	 Catch:{ Throwable -> 0x01a3 }
            r0 = r21;	 Catch:{ Throwable -> 0x01a3 }
            r1 = r29;	 Catch:{ Throwable -> 0x01a3 }
            android.util.Log.e(r0, r7, r1);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r36;
            r9 = android.support.v7.widget.ActivityChooserModel.this;
            r4 = 1;
            r9.mCanReadHistoricalData = r4;
            if (r11 == 0) goto L_0x00d0;
        L_0x015e:
            r11.close();	 Catch:{ IOException -> 0x0162 }
            goto L_0x0111;
        L_0x0162:
            r30 = move-exception;
            goto L_0x0115;
        L_0x0164:
            r31 = move-exception;
            r21 = android.support.v7.widget.ActivityChooserModel.LOG_TAG;	 Catch:{ Throwable -> 0x01a3 }
            r26 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r0.<init>();	 Catch:{ Throwable -> 0x01a3 }
            r15 = "Error writing historical recrod file: ";	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r26 = r0.append(r15);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r36;	 Catch:{ Throwable -> 0x01a3 }
            r9 = android.support.v7.widget.ActivityChooserModel.this;	 Catch:{ Throwable -> 0x01a3 }
            r7 = r9.mHistoryFileName;	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r26 = r0.append(r7);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r26;	 Catch:{ Throwable -> 0x01a3 }
            r7 = r0.toString();	 Catch:{ Throwable -> 0x01a3 }
            r0 = r21;	 Catch:{ Throwable -> 0x01a3 }
            r1 = r31;	 Catch:{ Throwable -> 0x01a3 }
            android.util.Log.e(r0, r7, r1);	 Catch:{ Throwable -> 0x01a3 }
            r0 = r36;
            r9 = android.support.v7.widget.ActivityChooserModel.this;
            r4 = 1;
            r9.mCanReadHistoricalData = r4;
            if (r11 == 0) goto L_0x00d0;
        L_0x019d:
            r11.close();	 Catch:{ IOException -> 0x01a1 }
            goto L_0x013a;
        L_0x01a1:
            r32 = move-exception;
            goto L_0x0136;
        L_0x01a3:
            r33 = move-exception;
            r0 = r36;
            r9 = android.support.v7.widget.ActivityChooserModel.this;
            r4 = 1;
            r9.mCanReadHistoricalData = r4;
            if (r11 == 0) goto L_0x01b1;
        L_0x01ae:
            r11.close();	 Catch:{ IOException -> 0x01b4 }
        L_0x01b1:
            throw r33;
        L_0x01b2:
            r34 = move-exception;
            goto L_0x013e;
        L_0x01b4:
            r35 = move-exception;
            goto L_0x01b1;
            */
            throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActivityChooserModel.PersistHistoryAsyncTask.doInBackground(java.lang.Object[]):java.lang.Void");
        }

        private PersistHistoryAsyncTask() throws  {
        }
    }

    private void readHistoricalDataImpl() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0064 in list [B:68:0x0104]
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r29 = this;
        r0 = r29;
        r5 = r0.mContext;
        r0 = r29;
        r6 = r0.mHistoryFileName;
        r7 = r5.openFileInput(r6);	 Catch:{ FileNotFoundException -> 0x0021 }
        r8 = android.util.Xml.newPullParser();	 Catch:{ IOException -> 0x0096 }
        r9 = "UTF-8";	 Catch:{ IOException -> 0x0096 }
        r8.setInput(r7, r9);	 Catch:{ IOException -> 0x0096 }
        r10 = 0;
    L_0x0016:
        r11 = 1;
        if (r10 == r11) goto L_0x0023;
    L_0x0019:
        r11 = 2;	 Catch:{ IOException -> 0x0096 }
        if (r10 == r11) goto L_0x0023;	 Catch:{ IOException -> 0x0096 }
    L_0x001c:
        r10 = r8.next();	 Catch:{ IOException -> 0x0096 }
        goto L_0x0016;
    L_0x0021:
        r12 = move-exception;
        return;
    L_0x0023:
        r6 = "historical-records";	 Catch:{ IOException -> 0x0096 }
        r13 = r8.getName();	 Catch:{ IOException -> 0x0096 }
        r14 = r6.equals(r13);	 Catch:{ IOException -> 0x0096 }
        if (r14 != 0) goto L_0x0064;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
    L_0x002f:
        r15 = new org.xmlpull.v1.XmlPullParserException;	 Catch:{  }
        r9 = "Share records file does not start with historical-records tag.";	 Catch:{  }
        r15.<init>(r9);	 Catch:{  }
        throw r15;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
    L_0x0037:
        r15 = move-exception;
        r6 = LOG_TAG;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r16 = new java.lang.StringBuilder;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0.<init>();	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r9 = "Error reading historical recrod file: ";	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r16 = r0.append(r9);	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r29;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r13 = r0.mHistoryFileName;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r16 = r0.append(r13);	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r13 = r0.toString();	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        android.util.Log.e(r6, r13, r15);	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        if (r7 == 0) goto L_0x010d;
    L_0x005e:
        r7.close();	 Catch:{ IOException -> 0x0062 }
        return;
    L_0x0062:
        r17 = move-exception;
        return;
    L_0x0064:
        r0 = r29;
        r0 = r0.mHistoricalRecords;
        r18 = r0;
        r0.clear();	 Catch:{ IOException -> 0x0096 }
    L_0x006d:
        r10 = r8.next();	 Catch:{ IOException -> 0x0096 }
        r11 = 1;
        if (r10 != r11) goto L_0x007c;
    L_0x0074:
        if (r7 == 0) goto L_0x010e;
    L_0x0076:
        r7.close();	 Catch:{ IOException -> 0x007a }
        return;
    L_0x007a:
        r19 = move-exception;
        return;
    L_0x007c:
        r11 = 3;
        if (r10 == r11) goto L_0x006d;
    L_0x007f:
        r11 = 4;
        if (r10 == r11) goto L_0x006d;
    L_0x0082:
        r6 = r8.getName();	 Catch:{  }
        r13 = "historical-record";	 Catch:{  }
        r14 = r13.equals(r6);	 Catch:{  }
        if (r14 != 0) goto L_0x00c9;	 Catch:{  }
    L_0x008e:
        r15 = new org.xmlpull.v1.XmlPullParserException;	 Catch:{  }
        r9 = "Share records file not well-formed.";	 Catch:{  }
        r15.<init>(r9);	 Catch:{  }
        throw r15;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
    L_0x0096:
        r20 = move-exception;
        r6 = LOG_TAG;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r16 = new java.lang.StringBuilder;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0.<init>();	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r9 = "Error reading historical recrod file: ";	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r16 = r0.append(r9);	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r29;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r13 = r0.mHistoryFileName;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r16 = r0.append(r13);	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        goto L_0x00b6;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
    L_0x00b3:
        goto L_0x006d;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
    L_0x00b6:
        r0 = r16;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r13 = r0.toString();	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        r0 = r20;	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        android.util.Log.e(r6, r13, r0);	 Catch:{ XmlPullParserException -> 0x0037, Throwable -> 0x0104 }
        if (r7 == 0) goto L_0x010f;
    L_0x00c3:
        r7.close();	 Catch:{ IOException -> 0x00c7 }
        return;
    L_0x00c7:
        r21 = move-exception;
        return;
    L_0x00c9:
        r22 = 0;	 Catch:{ IOException -> 0x0096 }
        r9 = "activity";	 Catch:{ IOException -> 0x0096 }
        r0 = r22;	 Catch:{ IOException -> 0x0096 }
        r6 = r8.getAttributeValue(r0, r9);	 Catch:{ IOException -> 0x0096 }
        r22 = 0;	 Catch:{ IOException -> 0x0096 }
        r9 = "time";	 Catch:{ IOException -> 0x0096 }
        r0 = r22;	 Catch:{ IOException -> 0x0096 }
        r13 = r8.getAttributeValue(r0, r9);	 Catch:{ IOException -> 0x0096 }
        r23 = java.lang.Long.parseLong(r13);	 Catch:{ IOException -> 0x0096 }
        r22 = 0;	 Catch:{ IOException -> 0x0096 }
        r9 = "weight";	 Catch:{ IOException -> 0x0096 }
        r0 = r22;	 Catch:{ IOException -> 0x0096 }
        r13 = r8.getAttributeValue(r0, r9);	 Catch:{ IOException -> 0x0096 }
        r25 = java.lang.Float.parseFloat(r13);	 Catch:{ IOException -> 0x0096 }
        r26 = new android.support.v7.widget.ActivityChooserModel$HistoricalRecord;	 Catch:{ IOException -> 0x0096 }
        r0 = r26;	 Catch:{ IOException -> 0x0096 }
        r1 = r23;	 Catch:{ IOException -> 0x0096 }
        r3 = r25;	 Catch:{ IOException -> 0x0096 }
        r0.<init>(r6, r1, r3);	 Catch:{ IOException -> 0x0096 }
        r0 = r18;	 Catch:{ IOException -> 0x0096 }
        r1 = r26;	 Catch:{ IOException -> 0x0096 }
        r0.add(r1);	 Catch:{ IOException -> 0x0096 }
        goto L_0x00b3;
    L_0x0104:
        r27 = move-exception;
        if (r7 == 0) goto L_0x010a;
    L_0x0107:
        r7.close();	 Catch:{ IOException -> 0x010b }
    L_0x010a:
        throw r27;
    L_0x010b:
        r28 = move-exception;
        goto L_0x010a;
    L_0x010d:
        return;
    L_0x010e:
        return;
    L_0x010f:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActivityChooserModel.readHistoricalDataImpl():void");
    }

    public static ActivityChooserModel get(Context $r0, String $r1) throws  {
        ActivityChooserModel $r5;
        synchronized (sRegistryLock) {
            $r5 = (ActivityChooserModel) sDataModelRegistry.get($r1);
            if ($r5 == null) {
                $r5 = new ActivityChooserModel($r0, $r1);
                sDataModelRegistry.put($r1, $r5);
            }
        }
        return $r5;
    }

    private ActivityChooserModel(Context $r1, String $r2) throws  {
        this.mContext = $r1.getApplicationContext();
        if (TextUtils.isEmpty($r2) || $r2.endsWith(HISTORY_FILE_EXTENSION)) {
            this.mHistoryFileName = $r2;
        } else {
            this.mHistoryFileName = $r2 + HISTORY_FILE_EXTENSION;
        }
    }

    public void setIntent(Intent $r1) throws  {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == $r1) {
                return;
            }
            this.mIntent = $r1;
            this.mReloadActivities = true;
            ensureConsistentState();
        }
    }

    public Intent getIntent() throws  {
        Intent r3;
        synchronized (this.mInstanceLock) {
            r3 = this.mIntent;
        }
        return r3;
    }

    public int getActivityCount() throws  {
        int $i0;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            $i0 = this.mActivities.size();
        }
        return $i0;
    }

    public ResolveInfo getActivity(int $i0) throws  {
        ResolveInfo r6;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            r6 = ((ActivityResolveInfo) this.mActivities.get($i0)).resolveInfo;
        }
        return r6;
    }

    public int getActivityIndex(ResolveInfo $r1) throws  {
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            List $r2 = this.mActivities;
            int $i0 = $r2.size();
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                if (((ActivityResolveInfo) $r2.get($i1)).resolveInfo == $r1) {
                    return $i1;
                }
            }
            return -1;
        }
    }

    public Intent chooseActivity(int $i0) throws  {
        synchronized (this.mInstanceLock) {
            if (this.mIntent == null) {
                return null;
            }
            ensureConsistentState();
            ActivityResolveInfo $r8 = (ActivityResolveInfo) this.mActivities.get($i0);
            String $r11 = $r8.resolveInfo.activityInfo.packageName;
            String $r12 = $r8.resolveInfo.activityInfo.name;
            ComponentName $r2 = new ComponentName($r11, $r12);
            Intent $r1 = this.mIntent;
            Intent $r5 = new Intent($r1);
            $r5.setComponent($r2);
            if (this.mActivityChoserModelPolicy != null) {
                $r1 = new Intent($r5);
                OnChooseActivityListener onChooseActivityListener = this.mActivityChoserModelPolicy;
                OnChooseActivityListener $r13 = onChooseActivityListener;
                if (onChooseActivityListener.onChooseActivity(this, $r1)) {
                    return null;
                }
            }
            addHisoricalRecord(new HistoricalRecord($r2, System.currentTimeMillis(), 1.0f));
            return $r5;
        }
    }

    public void setOnChooseActivityListener(OnChooseActivityListener $r1) throws  {
        synchronized (this.mInstanceLock) {
            this.mActivityChoserModelPolicy = $r1;
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public android.content.pm.ResolveInfo getDefaultActivity() throws  {
        /*
        r10 = this;
        r0 = r10.mInstanceLock;
        monitor-enter(r0);
        r10.ensureConsistentState();	 Catch:{ Throwable -> 0x0020 }
        r1 = r10.mActivities;	 Catch:{ Throwable -> 0x0020 }
        r2 = r1.isEmpty();	 Catch:{ Throwable -> 0x0020 }
        if (r2 != 0) goto L_0x001d;
    L_0x000e:
        r1 = r10.mActivities;	 Catch:{ Throwable -> 0x0020 }
        r4 = 0;
        r3 = r1.get(r4);	 Catch:{ Throwable -> 0x0020 }
        r6 = r3;
        r6 = (android.support.v7.widget.ActivityChooserModel.ActivityResolveInfo) r6;	 Catch:{ Throwable -> 0x0020 }
        r5 = r6;
        r7 = r5.resolveInfo;	 Catch:{ Throwable -> 0x0020 }
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0020 }
        return r7;
    L_0x001d:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0020 }
        r8 = 0;
        return r8;
    L_0x0020:
        r9 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0020 }
        throw r9;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActivityChooserModel.getDefaultActivity():android.content.pm.ResolveInfo");
    }

    public void setDefaultActivity(int $i0) throws  {
        synchronized (this.mInstanceLock) {
            float $f0;
            ensureConsistentState();
            ActivityResolveInfo $r6 = (ActivityResolveInfo) this.mActivities.get($i0);
            ActivityResolveInfo $r7 = (ActivityResolveInfo) this.mActivities.get(0);
            if ($r7 != null) {
                $f0 = ($r7.weight - $r6.weight) + 5.0f;
            } else {
                $f0 = 1.0f;
            }
            ActivityInfo $r9 = $r6.resolveInfo;
            ActivityInfo $r8 = $r9;
            String $r10 = $r9.activityInfo;
            String $r92 = $r10;
            String $r102 = $r10.packageName;
            $r9 = $r6.resolveInfo;
            $r8 = $r9;
            $r10 = $r9.activityInfo;
            $r92 = $r10;
            addHisoricalRecord(new HistoricalRecord(new ComponentName($r102, $r10.name), System.currentTimeMillis(), $f0));
        }
    }

    private void persistHistoricalDataIfNeeded() throws  {
        if (!this.mReadShareHistoryCalled) {
            throw new IllegalStateException("No preceding call to #readHistoricalData");
        } else if (this.mHistoricalRecordsChanged) {
            this.mHistoricalRecordsChanged = false;
            if (!TextUtils.isEmpty(this.mHistoryFileName)) {
                AsyncTaskCompat.executeParallel(new PersistHistoryAsyncTask(), new ArrayList(this.mHistoricalRecords), this.mHistoryFileName);
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setActivitySorter(android.support.v7.widget.ActivityChooserModel.ActivitySorter r5) throws  {
        /*
        r4 = this;
        r0 = r4.mInstanceLock;
        monitor-enter(r0);
        r1 = r4.mActivitySorter;	 Catch:{ Throwable -> 0x0016 }
        if (r1 != r5) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0016 }
        return;
    L_0x0009:
        r4.mActivitySorter = r5;	 Catch:{ Throwable -> 0x0016 }
        r2 = r4.sortActivitiesIfNeeded();	 Catch:{ Throwable -> 0x0016 }
        if (r2 == 0) goto L_0x0014;
    L_0x0011:
        r4.notifyChanged();	 Catch:{ Throwable -> 0x0016 }
    L_0x0014:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0016 }
        return;
    L_0x0016:
        r3 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0016 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActivityChooserModel.setActivitySorter(android.support.v7.widget.ActivityChooserModel$ActivitySorter):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void setHistoryMaxSize(int r5) throws  {
        /*
        r4 = this;
        r0 = r4.mInstanceLock;
        monitor-enter(r0);
        r1 = r4.mHistoryMaxSize;	 Catch:{ Throwable -> 0x0019 }
        if (r1 != r5) goto L_0x0009;
    L_0x0007:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0019 }
        return;
    L_0x0009:
        r4.mHistoryMaxSize = r5;	 Catch:{ Throwable -> 0x0019 }
        r4.pruneExcessiveHistoricalRecordsIfNeeded();	 Catch:{ Throwable -> 0x0019 }
        r2 = r4.sortActivitiesIfNeeded();	 Catch:{ Throwable -> 0x0019 }
        if (r2 == 0) goto L_0x0017;
    L_0x0014:
        r4.notifyChanged();	 Catch:{ Throwable -> 0x0019 }
    L_0x0017:
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0019 }
        return;
    L_0x0019:
        r3 = move-exception;
        monitor-exit(r0);	 Catch:{ Throwable -> 0x0019 }
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.widget.ActivityChooserModel.setHistoryMaxSize(int):void");
    }

    public int getHistoryMaxSize() throws  {
        int i0;
        synchronized (this.mInstanceLock) {
            i0 = this.mHistoryMaxSize;
        }
        return i0;
    }

    public int getHistorySize() throws  {
        int $i0;
        synchronized (this.mInstanceLock) {
            ensureConsistentState();
            $i0 = this.mHistoricalRecords.size();
        }
        return $i0;
    }

    private void ensureConsistentState() throws  {
        boolean $z0 = loadActivitiesIfNeeded() | readHistoricalDataIfNeeded();
        pruneExcessiveHistoricalRecordsIfNeeded();
        if ($z0) {
            sortActivitiesIfNeeded();
            notifyChanged();
        }
    }

    private boolean sortActivitiesIfNeeded() throws  {
        if (this.mActivitySorter == null || this.mIntent == null || this.mActivities.isEmpty() || this.mHistoricalRecords.isEmpty()) {
            return false;
        }
        this.mActivitySorter.sort(this.mIntent, this.mActivities, Collections.unmodifiableList(this.mHistoricalRecords));
        return true;
    }

    private boolean loadActivitiesIfNeeded() throws  {
        if (!this.mReloadActivities) {
            return false;
        }
        if (this.mIntent == null) {
            return false;
        }
        this.mReloadActivities = false;
        this.mActivities.clear();
        List $r2 = this.mContext.getPackageManager().queryIntentActivities(this.mIntent, 0);
        int $i0 = $r2.size();
        for (int $i1 = 0; $i1 < $i0; $i1++) {
            this.mActivities.add(new ActivityResolveInfo((ResolveInfo) $r2.get($i1)));
        }
        return true;
    }

    private boolean readHistoricalDataIfNeeded() throws  {
        if (!this.mCanReadHistoricalData || !this.mHistoricalRecordsChanged || TextUtils.isEmpty(this.mHistoryFileName)) {
            return false;
        }
        this.mCanReadHistoricalData = false;
        this.mReadShareHistoryCalled = true;
        readHistoricalDataImpl();
        return true;
    }

    private boolean addHisoricalRecord(HistoricalRecord $r1) throws  {
        boolean $z0 = this.mHistoricalRecords.add($r1);
        if (!$z0) {
            return $z0;
        }
        this.mHistoricalRecordsChanged = true;
        pruneExcessiveHistoricalRecordsIfNeeded();
        persistHistoricalDataIfNeeded();
        sortActivitiesIfNeeded();
        notifyChanged();
        return $z0;
    }

    private void pruneExcessiveHistoricalRecordsIfNeeded() throws  {
        int $i0 = this.mHistoricalRecords.size() - this.mHistoryMaxSize;
        if ($i0 > 0) {
            this.mHistoricalRecordsChanged = true;
            for (int $i1 = 0; $i1 < $i0; $i1++) {
                HistoricalRecord $r3 = (HistoricalRecord) this.mHistoricalRecords.remove(0);
            }
        }
    }
}
