package com.waze;

import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public final class CPUProfiler extends TimerTask {
    private static final int CPU_LIMIT_ANIMATION = 1500000;
    private static final int OVERALL_IDLE_IDX = 5;
    private static final int OVERALL_SYSTEM_IDX = 4;
    private static final int OVERALL_USER_IDX = 2;
    private static final int PROCESS_KERNEL_IDX = 14;
    private static final int PROCESS_USER_IDX = 13;
    private static final long SAMPLE_PERIOD = 60000;
    private static final float USAGE_THRESHOLD = 80.0f;
    private static final String WAZE_CPU_PROFILER = "WAZE CPU PROFILER";
    private static CPUProfiler mInstance = null;
    static int s_cpuMaxFreq = 0;
    private final boolean SHOW_LOGCAT = false;
    private long mCurUsageCPUIdle = 0;
    private long mCurUsageCPUProcess = 0;
    private long mCurUsageCPUSys = 0;
    private long mCurUsageCPUUser = 0;
    private long mLastIdleCPU = 0;
    private long mLastProcessCPU = 0;
    private long mLastSysCPU = 0;
    private long mLastTotal = 0;
    private long mLastUserCPU = 0;

    private boolean ReadCPU() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x005c in list [B:27:0x012f]
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
        r31 = this;
        r4 = new java.io.BufferedReader;
        r5 = new java.io.InputStreamReader;
        r6 = new java.io.FileInputStream;
        r7 = "/proc/stat";	 Catch:{ Exception -> 0x012f }
        r6.<init>(r7);	 Catch:{ Exception -> 0x012f }
        r5.<init>(r6);	 Catch:{ Exception -> 0x012f }
        r8 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;	 Catch:{ Exception -> 0x012f }
        r4.<init>(r5, r8);	 Catch:{ Exception -> 0x012f }
        r9 = r4.readLine();	 Catch:{ Exception -> 0x012f }
        r4.close();	 Catch:{ Exception -> 0x012f }
        r7 = " ";	 Catch:{ Exception -> 0x012f }
        r10 = r9.split(r7);	 Catch:{ Exception -> 0x012f }
        r8 = 2;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r11 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r8 = 4;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r13 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r11 = r11 + r13;	 Catch:{ Exception -> 0x012f }
        r8 = 5;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r13 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r11 = r11 + r13;	 Catch:{ Exception -> 0x012f }
        r8 = 2;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r13 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r8 = 4;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r15 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r8 = 5;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r17 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r0 = r31;	 Catch:{ Exception -> 0x012f }
        r0 = r0.mLastTotal;	 Catch:{ Exception -> 0x012f }
        r19 = r0;	 Catch:{ Exception -> 0x012f }
        r19 = r11 - r19;
        r22 = 0;
        r21 = (r19 > r22 ? 1 : (r19 == r22 ? 0 : -1));
        if (r21 != 0) goto L_0x005c;	 Catch:{ Exception -> 0x012f }
    L_0x005a:
        r8 = 0;
        return r8;
    L_0x005c:
        r0 = r31;	 Catch:{ Exception -> 0x012f }
        r0 = r0.mLastIdleCPU;	 Catch:{ Exception -> 0x012f }
        r24 = r0;	 Catch:{ Exception -> 0x012f }
        r24 = r17 - r24;
        r22 = 100;	 Catch:{ Exception -> 0x012f }
        r24 = r22 * r24;	 Catch:{ Exception -> 0x012f }
        r0 = r24;	 Catch:{ Exception -> 0x012f }
        r2 = r19;	 Catch:{ Exception -> 0x012f }
        r0 = r0 / r2;	 Catch:{ Exception -> 0x012f }
        r24 = r0;	 Catch:{ Exception -> 0x012f }
        r2 = r31;	 Catch:{ Exception -> 0x012f }
        r2.mCurUsageCPUIdle = r0;	 Catch:{ Exception -> 0x012f }
        r0 = r31;	 Catch:{ Exception -> 0x012f }
        r0 = r0.mLastUserCPU;	 Catch:{ Exception -> 0x012f }
        r24 = r0;	 Catch:{ Exception -> 0x012f }
        r24 = r13 - r24;
        r22 = 100;	 Catch:{ Exception -> 0x012f }
        r24 = r22 * r24;	 Catch:{ Exception -> 0x012f }
        r0 = r24;	 Catch:{ Exception -> 0x012f }
        r2 = r19;	 Catch:{ Exception -> 0x012f }
        r0 = r0 / r2;	 Catch:{ Exception -> 0x012f }
        r24 = r0;	 Catch:{ Exception -> 0x012f }
        r2 = r31;	 Catch:{ Exception -> 0x012f }
        r2.mCurUsageCPUUser = r0;	 Catch:{ Exception -> 0x012f }
        r0 = r31;	 Catch:{ Exception -> 0x012f }
        r0 = r0.mLastSysCPU;	 Catch:{ Exception -> 0x012f }
        r24 = r0;	 Catch:{ Exception -> 0x012f }
        r24 = r15 - r24;
        r22 = 100;	 Catch:{ Exception -> 0x012f }
        r24 = r22 * r24;	 Catch:{ Exception -> 0x012f }
        r0 = r24;	 Catch:{ Exception -> 0x012f }
        r2 = r19;	 Catch:{ Exception -> 0x012f }
        r0 = r0 / r2;	 Catch:{ Exception -> 0x012f }
        r24 = r0;	 Catch:{ Exception -> 0x012f }
        r2 = r31;	 Catch:{ Exception -> 0x012f }
        r2.mCurUsageCPUSys = r0;	 Catch:{ Exception -> 0x012f }
        r26 = android.os.Process.myPid();	 Catch:{ Exception -> 0x012f }
        r4 = new java.io.BufferedReader;
        r5 = new java.io.InputStreamReader;
        r6 = new java.io.FileInputStream;
        r27 = new java.lang.StringBuilder;	 Catch:{ Exception -> 0x012f }
        r0 = r27;	 Catch:{ Exception -> 0x012f }
        r0.<init>();	 Catch:{ Exception -> 0x012f }
        r7 = "/proc/";	 Catch:{ Exception -> 0x012f }
        r0 = r27;	 Catch:{ Exception -> 0x012f }
        r27 = r0.append(r7);	 Catch:{ Exception -> 0x012f }
        r0 = r27;	 Catch:{ Exception -> 0x012f }
        r1 = r26;	 Catch:{ Exception -> 0x012f }
        r27 = r0.append(r1);	 Catch:{ Exception -> 0x012f }
        r7 = "/stat";	 Catch:{ Exception -> 0x012f }
        r0 = r27;	 Catch:{ Exception -> 0x012f }
        r27 = r0.append(r7);	 Catch:{ Exception -> 0x012f }
        r0 = r27;	 Catch:{ Exception -> 0x012f }
        r9 = r0.toString();	 Catch:{ Exception -> 0x012f }
        r6.<init>(r9);	 Catch:{ Exception -> 0x012f }
        r5.<init>(r6);	 Catch:{ Exception -> 0x012f }
        r8 = 100;	 Catch:{ Exception -> 0x012f }
        r4.<init>(r5, r8);	 Catch:{ Exception -> 0x012f }
        r9 = r4.readLine();	 Catch:{ Exception -> 0x012f }
        r4.close();	 Catch:{ Exception -> 0x012f }
        r7 = " ";	 Catch:{ Exception -> 0x012f }
        r10 = r9.split(r7);	 Catch:{ Exception -> 0x012f }
        r8 = 13;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r24 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r8 = 14;	 Catch:{ Exception -> 0x012f }
        r9 = r10[r8];	 Catch:{ Exception -> 0x012f }
        r28 = java.lang.Long.parseLong(r9);	 Catch:{ Exception -> 0x012f }
        r0 = r24;	 Catch:{ Exception -> 0x012f }
        r2 = r28;	 Catch:{ Exception -> 0x012f }
        r0 = r0 + r2;	 Catch:{ Exception -> 0x012f }
        r24 = r0;	 Catch:{ Exception -> 0x012f }
        r0 = r31;	 Catch:{ Exception -> 0x012f }
        r0 = r0.mLastProcessCPU;	 Catch:{ Exception -> 0x012f }
        r28 = r0;	 Catch:{ Exception -> 0x012f }
        r28 = r24 - r28;
        r22 = 100;	 Catch:{ Exception -> 0x012f }
        r28 = r22 * r28;	 Catch:{ Exception -> 0x012f }
        r19 = r28 / r19;	 Catch:{ Exception -> 0x012f }
        r0 = r19;	 Catch:{ Exception -> 0x012f }
        r2 = r31;	 Catch:{ Exception -> 0x012f }
        r2.mCurUsageCPUProcess = r0;	 Catch:{ Exception -> 0x012f }
        r0 = r31;	 Catch:{ Exception -> 0x012f }
        r0.mLastTotal = r11;	 Catch:{ Exception -> 0x012f }
        r0 = r31;	 Catch:{ Exception -> 0x012f }
        r0.mLastUserCPU = r13;	 Catch:{ Exception -> 0x012f }
        r0 = r15;	 Catch:{ Exception -> 0x012f }
        r2 = r31;	 Catch:{ Exception -> 0x012f }
        r2.mLastSysCPU = r0;	 Catch:{ Exception -> 0x012f }
        r0 = r17;	 Catch:{ Exception -> 0x012f }
        r2 = r31;	 Catch:{ Exception -> 0x012f }
        r2.mLastIdleCPU = r0;	 Catch:{ Exception -> 0x012f }
        r0 = r24;	 Catch:{ Exception -> 0x012f }
        r2 = r31;	 Catch:{ Exception -> 0x012f }
        r2.mLastProcessCPU = r0;	 Catch:{ Exception -> 0x012f }
        r8 = 1;
        return r8;
    L_0x012f:
        r30 = move-exception;
        r7 = "WAZE CPU PROFILER";
        r0 = r30;
        com.waze.Logger.m39e(r7, r0);
        r8 = 0;
        return r8;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.CPUProfiler.ReadCPU():boolean");
    }

    public void run() throws  {
        if (ReadCPU() && ((float) (this.mCurUsageCPUSys + this.mCurUsageCPUUser)) >= USAGE_THRESHOLD) {
            PostResultToLog();
        }
    }

    public static void Start(Timer $r0) throws  {
        mInstance = new CPUProfiler();
        $r0.scheduleAtFixedRate(mInstance, 0, SAMPLE_PERIOD);
    }

    public static void getPerformance() throws  {
        try {
            RandomAccessFile $r1 = new RandomAccessFile("/sys/devices/system/cpu/cpu0/cpufreq/cpuinfo_max_freq", "r");
            String $r2 = $r1.readLine();
            $r1.close();
            s_cpuMaxFreq = Integer.parseInt($r2);
        } catch (Throwable $r0) {
            $r0.printStackTrace();
        }
    }

    public static boolean shouldAnimate() throws  {
        if (s_cpuMaxFreq == 0) {
            return true;
        }
        return s_cpuMaxFreq >= CPU_LIMIT_ANIMATION;
    }

    private CPUProfiler() throws  {
    }

    private void PostResultToLog() throws  {
        final String $r2 = FormatResultString();
        AppService.getNativeManager().PostRunnable(new Runnable() {
            public void run() throws  {
                Logger.m43w($r2);
            }
        });
    }

    private String FormatResultString() throws  {
        return "WAZE CPU PROFILER(percents). User: " + this.mCurUsageCPUUser + ". System: " + this.mCurUsageCPUSys + ". Idle: " + this.mCurUsageCPUIdle + ". WAZE: " + this.mCurUsageCPUProcess + ". Post time: " + new SimpleDateFormat("hh:mm:ss").format(new Date());
    }
}
