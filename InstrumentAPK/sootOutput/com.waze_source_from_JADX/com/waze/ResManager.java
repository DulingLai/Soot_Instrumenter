package com.waze;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build.VERSION;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.C1283R.drawable;
import com.waze.Utils.ExceptionEntry;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.RunnableCallback;
import com.waze.ifs.async.RunnableUICallback;
import dalvik.annotation.Signature;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;

public final class ResManager {
    static final /* synthetic */ boolean $assertionsDisabled = (!ResManager.class.desiredAssertionStatus());
    private static final boolean EXTRACT_LIBRARY = false;
    private static final boolean EXTRACT_SKINS_BIN_FILES = false;
    private static final int SCREEN_HD_DIM_PIX = 640;
    private static final int SCREEN_LD_DIM_PIX = 240;
    private static final int SCREEN_SD_DIM_PIX = 320;
    private static final String SKIN_SUFFIX_10X = "@10x";
    private static final String SKIN_SUFFIX_2X = "@2x";
    private static final String SKIN_SUFFIX_3X = "@3x";
    private static final String SKIN_SUFFIX_5X = "@5x";
    public static String mAppDir = null;
    public static final String mAssetsResDir = "assets/res/";
    public static final String mEditorDbExt = ".dat";
    private static Typeface mFaceProximaBold = null;
    private static Typeface mFaceProximaExBold = null;
    private static Typeface mFaceProximaExBoldIt = null;
    private static Typeface mFaceProximaLight = null;
    private static Typeface mFaceProximaLightIt = null;
    private static Typeface mFaceProximaReg = null;
    private static Typeface mFaceProximaRegIt = null;
    private static Typeface mFaceProximaSemibold = null;
    private static Typeface mFaceProximaSemiboldIt = null;
    private static Typeface mFaceRobotoBlack = null;
    private static Typeface mFaceRobotoBold = null;
    private static Typeface mFaceRobotoBoldIt = null;
    private static Typeface mFaceRobotoCondensedBold = null;
    private static Typeface mFaceRobotoCondensedLight = null;
    private static Typeface mFaceRobotoCondensedReg = null;
    private static Typeface mFaceRobotoLight = null;
    private static Typeface mFaceRobotoLightIt = null;
    private static Typeface mFaceRobotoMedium = null;
    private static Typeface mFaceRobotoMediumIt = null;
    private static Typeface mFaceRobotoReg = null;
    private static Typeface mFaceRobotoRegIt = null;
    private static Typeface mFaceRobotoSlabBold = null;
    private static Typeface mFaceRobotoSlabReg = null;
    public static final String mFontRobotoBlackPath = "fonts/Roboto-Black.ttf";
    public static final String mFontRobotoBoldItPath = "fonts/Roboto-BoldItalic.ttf";
    public static final String mFontRobotoBoldPath = "fonts/Roboto-Bold.ttf";
    public static final String mFontRobotoCondensedBoldPath = "fonts/RobotoCondensed-Bold.ttf";
    public static final String mFontRobotoCondensedLightPath = "fonts/RobotoCondensed-Light.ttf";
    public static final String mFontRobotoCondensedRegPath = "fonts/RobotoCondensed-Regular.ttf";
    public static final String mFontRobotoLightItPath = "fonts/Roboto-LightItalic.ttf";
    public static final String mFontRobotoLightPath = "fonts/Roboto-Light.ttf";
    public static final String mFontRobotoMediumItPath = "fonts/Roboto-MediumItalic.ttf";
    public static final String mFontRobotoMediumPath = "fonts/Roboto-Medium.ttf";
    public static final String mFontRobotoRegItPath = "fonts/Roboto-Italic.ttf";
    public static final String mFontRobotoRegPath = "fonts/Roboto-Regular.ttf";
    public static final String mFontRobotoSlabBoldPath = "fonts/RobotoSlab-Bold.ttf";
    public static final String mFontRobotoSlabRegPath = "fonts/RobotoSlab-Regular.ttf";
    public static final String mHistoryFile = "history";
    public static final String mImageExtension = ".png";
    private static ResManager mInstance = null;
    public static final String mLangPrefix = "lang.";
    public static final String mLibFile = "libwaze.so";
    public static final String mLogCatFile = "logcat.txt";
    public static final String mLogFile = "waze_log.txt";
    public static final String mLogFileCopy = "waze_log.txt.copy";
    public static final String mMapTilesDir = "77001";
    public static final String mMapsDir = "maps/";
    public static final String mPkgCacheDir = "cache";
    public static final String mPkgCodeCacheDir = "code_cache";
    public static final String mPkgDatabasesDir = "databases";
    public static String mPkgDir = null;
    public static final String mPkgLibDir = "lib";
    public static final String mPkgSharedPrefsDir = "shared_prefs";
    public static final String mPrefFile = "preferences";
    public static final String mPromptsConf = "prompts_conf.buf";
    public static final String mPromptsOldConf = "prompts.conf";
    public static final String mProximaBoldPath = "fonts/ProximaNova-Bold.otf";
    public static final String mProximaExBoldItPath = "fonts/proximanova-extraboldit.otf";
    public static final String mProximaExBoldPath = "fonts/proximanova-extrabold.otf";
    public static final String mProximaLightItPath = "fonts/proximanova-lightit.otf";
    public static final String mProximaLightPath = "fonts/proximanova-light.otf";
    public static final String mProximaRegItPath = "fonts/proximanova-regularit.otf";
    public static final String mProximaRegPath = "fonts/proximanova-regular.otf";
    public static final String mProximaSemiboldItPath = "fonts/proximanova-semiboldit.otf";
    public static final String mProximaSemiboldPath = "fonts/proximanova-semibold.otf";
    public static final String mResDir = "res/";
    public static final String mSDCardDir = (Environment.getExternalStorageDirectory().getPath() + "/waze/");
    public static String mSDCardResDir = null;
    public static final String mSearchConf = "search_conf";
    public static final String mSessionFile = "session";
    public static final String mSkinsPath = "skins/default/";
    public static final String mSoundDir = "sound";
    public static final String mSplashName = "welcome.png";
    public static final String mSplashPath = "skins/default/welcome.png";
    public static final String mSplashWidePath = "skins/default/welcome_wide.png";
    public static final String mTtsDir = "tts/";
    public static byte mUpgradeRun = (byte) 0;
    public static final String mUserDbFile = "user.db";
    public static final String mUserFile = "user";
    public static final int mVersionCodeSize = 7;
    public static final String mVersionFile = "version";
    static boolean m_bIsExtractSuccess = true;

    public static class ResData {
        public byte[] buf;
        public int size;

        public ResData(byte[] $r1, int $i0) throws  {
            this.buf = $r1;
            this.size = $i0;
        }
    }

    public enum ZipEntryType {
        ZIP_ENTRY_FILE,
        ZIP_ENTRY_DIRECTORY
    }

    private static void CarryOn() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:79:0x0952 in {2, 10, 18, 19, 22, 26, 28, 31, 33, 36, 39, 43, 47, 51, 55, 59, 63, 74, 75, 77, 80, 84, 86} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r4 = 0;
        r5 = com.waze.AppService.getAppContext();	 Catch:{ NameNotFoundException -> 0x01a2 }
        r6 = r5.getPackageManager();	 Catch:{ NameNotFoundException -> 0x01a2 }
        r5 = com.waze.AppService.getAppContext();	 Catch:{ NameNotFoundException -> 0x01a2 }
        r7 = r5.getPackageName();	 Catch:{ NameNotFoundException -> 0x01a2 }
        r8 = 0;	 Catch:{ NameNotFoundException -> 0x01a2 }
        r4 = r6.getPackageInfo(r7, r8);	 Catch:{ NameNotFoundException -> 0x01a2 }
    L_0x0016:
        r9 = r4.versionCode;
        r10 = GetVersionFromFile();
        r7 = mAppDir;
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mPkgDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "waze";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r17;
        r15.<init>(r0);
        r8 = -1;
        if (r10 == r8) goto L_0x0080;
    L_0x004e:
        r17 = getExternalStorage();
        r19 = mSDCardDir;
        r0 = r17;
        r1 = r19;
        r20 = r0.equals(r1);
        if (r20 != 0) goto L_0x0080;
    L_0x005e:
        r20 = r15.exists();
        if (r20 != 0) goto L_0x0080;
    L_0x0064:
        r17 = mSDCardDir;
        r15 = new java.io.File;
        r0 = r17;
        r15.<init>(r0);
        r17 = mSDCardResDir;
        r21 = new java.io.File;
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r20 = r15.renameTo(r0);
        if (r20 != 0) goto L_0x0080;
    L_0x0080:
        r15 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "skins";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r17;
        r15.<init>(r0);
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "skinsold";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r15.renameTo(r0);
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = getExternalStorage();
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "/skin_backup";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r0.mkdir();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "skinsold/default";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r22 = r0.listFiles();
        if (r22 == 0) goto L_0x01b1;
    L_0x013d:
        r0 = r22;
        r0 = r0.length;
        r23 = r0;
        r24 = 0;
    L_0x0144:
        r0 = r24;
        r1 = r23;
        if (r0 >= r1) goto L_0x01b1;
    L_0x014a:
        r21 = r22[r24];
        r0 = r21;
        r17 = r0.getName();
        r18 = "category_group_";
        r0 = r17;
        r1 = r18;
        r20 = r0.contains(r1);
        if (r20 == 0) goto L_0x019f;
    L_0x015e:
        r25 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = getExternalStorage();
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "/skin_backup/";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r21;
        r17 = r0.getName();
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r25;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r1 = r25;
        r0.renameTo(r1);
    L_0x019f:
        r24 = r24 + 1;
        goto L_0x0144;
    L_0x01a2:
        r26 = move-exception;
        goto L_0x01a7;
    L_0x01a4:
        goto L_0x0016;
    L_0x01a7:
        r18 = "Prepare failure";
        r0 = r18;
        r1 = r26;
        com.waze.Logger.m39e(r0, r1);
        goto L_0x01a4;
    L_0x01b1:
        r27 = BuildSkinsExceptionList();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mSDCardResDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "waze_log.txt";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r20 = r0.exists();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mPkgDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "waze_log.txt";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r28 = r0.exists();
        if (r20 == 0) goto L_0x0249;
    L_0x0219:
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mSDCardResDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "waze_log.txt";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r0.delete();
    L_0x0249:
        if (r28 == 0) goto L_0x027b;
    L_0x024b:
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mPkgDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "waze_log.txt";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r0.delete();
    L_0x027b:
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mSDCardResDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "logcat.txt";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r0.delete();
        r29 = com.waze.AppService.mLogCatMonitor;
        if (r29 == 0) goto L_0x02b5;
    L_0x02ae:
        r29 = com.waze.AppService.mLogCatMonitor;
        r0 = r29;
        r0.setOutFileInvalid();
    L_0x02b5:
        if (r10 >= 0) goto L_0x0477;
    L_0x02b7:
        com.waze.AppService.setFirstRun();
        r8 = 0;
        r30 = BuildCleanUpPkgDirExceptions(r8);
        r17 = mPkgDir;
        r8 = 1;
        r0 = r17;
        r1 = r30;
        com.waze.Utils.DeleteDir(r0, r8, r1);
        r8 = 1;
        r31 = 0;
        r0 = r31;
        com.waze.Utils.DeleteDir(r7, r8, r0);
        r30 = BuildCleanInstallExceptionList();
    L_0x02d5:
        java.lang.System.gc();
        r17 = "assets/res/";
        r18 = "/";
        r0 = r17;
        r1 = r18;
        r20 = r0.endsWith(r1);
        if (r20 == 0) goto L_0x0956;
    L_0x02e6:
        r17 = "assets/res/";
        r19 = "assets/res/";
        r0 = r19;
        r10 = r0.length();
        r10 = r10 + -1;
        r8 = 0;
        r0 = r17;
        r17 = r0.substring(r8, r10);
    L_0x02f9:
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_DIRECTORY;
        r0 = r17;
        r1 = r32;
        r2 = r30;
        ExtractFromZip(r0, r7, r1, r2);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "skins/";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_DIRECTORY;
        r18 = "assets/res/skins";
        r0 = r18;
        r1 = r17;
        r2 = r32;
        r3 = r27;
        ExtractFromZip(r0, r1, r2, r3);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "sound/";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r7 = r0.toString();
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_DIRECTORY;
        r18 = "assets/res/sound";
        r0 = r18;
        r1 = r32;
        r2 = r27;
        ExtractFromZip(r0, r7, r1, r2);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r7 = mPkgDir;
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "user";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r7 = r0.toString();
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_FILE;
        r18 = "assets/res/user";
        r0 = r18;
        r1 = r32;
        ExtractFromZip(r0, r7, r1, r11);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r7 = mPkgDir;
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "session";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r7 = r0.toString();
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_FILE;
        r18 = "assets/res/session";
        r0 = r18;
        r1 = r32;
        ExtractFromZip(r0, r7, r1, r12);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r7 = mPkgDir;
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "preferences";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r7 = r0.toString();
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_FILE;
        r18 = "assets/res/preferences";
        r0 = r18;
        r1 = r32;
        ExtractFromZip(r0, r7, r1, r13);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r7 = mPkgDir;
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "history";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r7 = r0.toString();
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_FILE;
        r18 = "assets/res/history";
        r0 = r18;
        r1 = r32;
        ExtractFromZip(r0, r7, r1, r14);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r7 = mPkgDir;
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "cacert.pem";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r7 = r0.toString();
        r32 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_FILE;
        r18 = "assets/res/cacert.pem";
        r31 = 0;
        r0 = r18;
        r1 = r32;
        r2 = r31;
        ExtractFromZip(r0, r7, r1, r2);
        r20 = m_bIsExtractSuccess;
        if (r20 == 0) goto L_0x043a;
    L_0x0437:
        SetVersionToFile(r9);
    L_0x043a:
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r7 = mSDCardResDir;
        r0 = r16;
        r16 = r0.append(r7);
        r18 = "/.nomedia";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r7 = r0.toString();
        r0 = r21;
        r0.<init>(r7);
        r0 = r21;
        r20 = r0.exists();
        if (r20 != 0) goto L_0x046d;
    L_0x0468:
        r0 = r21;	 Catch:{ IOException -> 0x0959 }
        r0.createNewFile();	 Catch:{ IOException -> 0x0959 }
    L_0x046d:
        r20 = r15.exists();
        if (r20 == 0) goto L_0x0968;
    L_0x0473:
        r15.delete();
        return;
    L_0x0477:
        r8 = 1;
        r33 = BuildCleanUpPkgDirExceptions(r8);
        r34 = BuildUpgradeExceptionList();
        r30 = r34;
        r8 = 1;
        r12 = new com.waze.Utils.ExceptionEntry[r8];
        r11 = r12;
        r35 = new com.waze.Utils$ExceptionEntry;
        r18 = "user";
        r8 = 1;
        r0 = r35;
        r1 = r18;
        r0.<init>(r1, r8);
        r8 = 0;
        r12[r8] = r35;
        r8 = 1;
        r13 = new com.waze.Utils.ExceptionEntry[r8];
        r12 = r13;
        r35 = new com.waze.Utils$ExceptionEntry;
        r18 = "session";
        r8 = 1;
        r0 = r35;
        r1 = r18;
        r0.<init>(r1, r8);
        r8 = 0;
        r13[r8] = r35;
        r8 = 1;
        r14 = new com.waze.Utils.ExceptionEntry[r8];
        r13 = r14;
        r35 = new com.waze.Utils$ExceptionEntry;
        r18 = "preferences";
        r8 = 1;
        r0 = r35;
        r1 = r18;
        r0.<init>(r1, r8);
        r8 = 0;
        r14[r8] = r35;
        r8 = 1;
        r0 = new com.waze.Utils.ExceptionEntry[r8];
        r36 = r0;
        r14 = r36;
        r35 = new com.waze.Utils$ExceptionEntry;
        r18 = "history";
        r8 = 1;
        r0 = r35;
        r1 = r18;
        r0.<init>(r1, r8);
        r8 = 0;
        r36[r8] = r35;
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "user";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r20 = r0.exists();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mPkgDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "user";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r28 = r0.exists();
        if (r20 == 0) goto L_0x05aa;
    L_0x0536:
        if (r28 != 0) goto L_0x05aa;
    L_0x0538:
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "user";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r19 = mPkgDir;
        r0 = r16;
        r1 = r19;
        r16 = r0.append(r1);
        r18 = "user";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r19 = r0.toString();
        r0 = r17;
        r1 = r19;
        com.waze.Utils.Copy(r0, r1);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "user";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r17;
        com.waze.Utils.DeleteDir(r0);
    L_0x05aa:
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "session";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r20 = r0.exists();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mPkgDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "session";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r28 = r0.exists();
        if (r20 == 0) goto L_0x067d;
    L_0x060c:
        if (r28 != 0) goto L_0x067d;
    L_0x060e:
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "session";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r19 = mPkgDir;
        r0 = r16;
        r1 = r19;
        r16 = r0.append(r1);
        r18 = "session";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r19 = r0.toString();
        r0 = r17;
        r1 = r19;
        com.waze.Utils.Copy(r0, r1);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "session";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r17;
        com.waze.Utils.DeleteDir(r0);
    L_0x067d:
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "preferences";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r20 = r0.exists();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mPkgDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "preferences";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r28 = r0.exists();
        if (r20 == 0) goto L_0x0750;
    L_0x06df:
        if (r28 != 0) goto L_0x0750;
    L_0x06e1:
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "preferences";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r19 = mPkgDir;
        r0 = r16;
        r1 = r19;
        r16 = r0.append(r1);
        r18 = "preferences";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r19 = r0.toString();
        r0 = r17;
        r1 = r19;
        com.waze.Utils.Copy(r0, r1);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "preferences";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r17;
        com.waze.Utils.DeleteDir(r0);
    L_0x0750:
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "history";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r20 = r0.exists();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mPkgDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "history";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r28 = r0.exists();
        if (r20 == 0) goto L_0x0823;
    L_0x07b2:
        if (r28 != 0) goto L_0x0823;
    L_0x07b4:
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "history";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r19 = mPkgDir;
        r0 = r16;
        r1 = r19;
        r16 = r0.append(r1);
        r18 = "history";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r19 = r0.toString();
        r0 = r17;
        r1 = r19;
        com.waze.Utils.Copy(r0, r1);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "history";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r17;
        com.waze.Utils.DeleteDir(r0);
    L_0x0823:
        r8 = 1;
        r0 = r34;
        com.waze.Utils.DeleteDir(r7, r8, r0);
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "maps/";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r37 = r0.list();
        if (r37 == 0) goto L_0x08aa;
    L_0x085b:
        r10 = 0;
    L_0x085c:
        r0 = r37;
        r0 = r0.length;
        r23 = r0;
        if (r10 >= r0) goto L_0x08aa;
    L_0x0863:
        if (r37 == 0) goto L_0x08a7;
    L_0x0865:
        r17 = r37[r10];
        if (r17 == 0) goto L_0x08a7;
    L_0x0869:
        r17 = r37[r10];
        r18 = ".dat";
        r0 = r17;
        r1 = r18;
        r20 = r0.endsWith(r1);
        if (r20 == 0) goto L_0x08a7;
    L_0x0877:
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "maps/";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r17 = r37[r10];
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r17;
        com.waze.Utils.DeleteDir(r0);
    L_0x08a7:
        r10 = r10 + 1;
        goto L_0x085c;
    L_0x08aa:
        r17 = mPkgDir;
        r8 = 1;
        r0 = r17;
        r1 = r33;
        com.waze.Utils.DeleteDir(r0, r8, r1);
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "skins";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r0 = r21;
        r0.mkdir();
        r21 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = getExternalStorage();
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "/";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r18 = "skin_backup";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r21;
        r1 = r17;
        r0.<init>(r1);
        r25 = new java.io.File;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = mAppDir;
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = "skins/default";
        r0 = r16;
        r1 = r18;
        r16 = r0.append(r1);
        r0 = r16;
        r17 = r0.toString();
        r0 = r25;
        r1 = r17;
        r0.<init>(r1);
        goto L_0x094a;
    L_0x0947:
        goto L_0x02d5;
    L_0x094a:
        r0 = r21;
        r1 = r25;
        r0.renameTo(r1);
        goto L_0x0947;
        goto L_0x0956;
    L_0x0953:
        goto L_0x02f9;
    L_0x0956:
        r17 = "assets/res/";
        goto L_0x0953;
    L_0x0959:
        r38 = move-exception;
        goto L_0x095e;
    L_0x095b:
        goto L_0x046d;
    L_0x095e:
        r18 = "Prepare failure";
        r0 = r18;
        r1 = r38;
        com.waze.Logger.m39e(r0, r1);
        goto L_0x095b;
    L_0x0968:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ResManager.CarryOn():void");
    }

    private native void DownloadResNTV(int i, String str, DownloadResCallback downloadResCallback) throws ;

    private static void ExtractFromZip(java.lang.String r34, java.lang.String r35, com.waze.ResManager.ZipEntryType r36, com.waze.Utils.ExceptionEntry[] r37) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0033 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r3 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r2 = new byte[r3];
        r4 = 0;
        r5 = 0;
        if (r37 == 0) goto L_0x0014;
    L_0x0009:
        r4 = new java.util.ArrayList;
        r0 = r37;
        r6 = java.util.Arrays.asList(r0);
        r4.<init>(r6);
    L_0x0014:
        r7 = 0;
        r8 = com.waze.AppService.getAppContext();	 Catch:{ NameNotFoundException -> 0x01a4, IOException -> 0x01a2 }
        r9 = r8.getPackageManager();	 Catch:{ NameNotFoundException -> 0x01a4, IOException -> 0x01a2 }
        r10 = r8.getPackageName();	 Catch:{ NameNotFoundException -> 0x01a4, IOException -> 0x01a2 }
        r3 = 0;	 Catch:{ NameNotFoundException -> 0x01a4, IOException -> 0x01a2 }
        r11 = r9.getApplicationInfo(r10, r3);	 Catch:{ NameNotFoundException -> 0x01a4, IOException -> 0x01a2 }
        r10 = r11.sourceDir;
        r12 = new java.util.zip.ZipFile;	 Catch:{ NameNotFoundException -> 0x01a4, IOException -> 0x01a2 }
        r12.<init>(r10);	 Catch:{ NameNotFoundException -> 0x01a4, IOException -> 0x01a2 }
        r13 = r12.entries();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r14 = 0;
        r10 = 0;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x0033:
        r15 = r13.hasMoreElements();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        if (r15 == 0) goto L_0x018c;
    L_0x0039:
        if (r7 != 0) goto L_0x018c;
    L_0x003b:
        r16 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_FILE;
        r0 = r36;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r16;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        if (r0 != r1) goto L_0x004c;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x0043:
        r0 = r34;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r14 = r12.getEntry(r0);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r10 = r35;
        r7 = 1;
    L_0x004c:
        r16 = com.waze.ResManager.ZipEntryType.ZIP_ENTRY_DIRECTORY;
        r0 = r36;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r16;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        if (r0 != r1) goto L_0x00ca;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x0054:
        r17 = r13.nextElement();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r18 = r17;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r18 = (java.util.zip.ZipEntry) r18;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r14 = r18;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r19 = r14.getName();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r19;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r34;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r15 = r0.startsWith(r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        if (r15 == 0) goto L_0x013e;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x006c:
        r19 = r14.getName();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r34;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r20 = r0.length();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r19;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r20;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r21 = r0.charAt(r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r3 = 47;
        r0 = r21;
        if (r0 != r3) goto L_0x013e;
    L_0x0084:
        r15 = 1;
    L_0x0085:
        if (r15 == 0) goto L_0x0033;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x0087:
        r0 = r34;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r20 = r0.length();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r20 = r20 + 1;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r10 = r14.getName();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r22 = r10.length();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r10 = r14.getName();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r20;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r22;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r19 = r10.substring(r0, r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r23 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r23;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0.<init>();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r23;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r35;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r23 = r0.append(r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r23;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r19;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r23 = r0.append(r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r23;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r10 = r0.toString();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r19;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r35;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r15 = CheckException(r4, r0, r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        if (r15 != 0) goto L_0x0033;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x00ca:
        if (r14 == 0) goto L_0x0033;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x00cc:
        r24 = r12.getInputStream(r14);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r25 = new java.io.File;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r25;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0.<init>(r10);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r25;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r26 = r0.getParentFile();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r26;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0.mkdirs();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        goto L_0x00e6;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x00e3:
        goto L_0x0085;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x00e6:
        r27 = new java.io.FileOutputStream;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r27;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r25;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0.<init>(r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x00ef:
        r0 = r24;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r20 = r0.read(r2);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        if (r20 <= 0) goto L_0x0140;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x00f7:
        r3 = 0;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0 = r27;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r1 = r20;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0.write(r2, r3, r1);	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        goto L_0x00ef;
    L_0x0100:
        r28 = move-exception;
        r5 = r12;
    L_0x0102:
        r23 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x018e }
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r0.<init>();	 Catch:{ Throwable -> 0x018e }
        r29 = "Error! Archive not found";	 Catch:{ Throwable -> 0x018e }
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r1 = r29;	 Catch:{ Throwable -> 0x018e }
        r23 = r0.append(r1);	 Catch:{ Throwable -> 0x018e }
        r0 = r28;	 Catch:{ Throwable -> 0x018e }
        r34 = r0.getMessage();	 Catch:{ Throwable -> 0x018e }
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r1 = r34;	 Catch:{ Throwable -> 0x018e }
        r23 = r0.append(r1);	 Catch:{ Throwable -> 0x018e }
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r34 = r0.toString();	 Catch:{ Throwable -> 0x018e }
        r29 = "WAZE";	 Catch:{ Throwable -> 0x018e }
        r0 = r29;	 Catch:{ Throwable -> 0x018e }
        r1 = r34;	 Catch:{ Throwable -> 0x018e }
        android.util.Log.e(r0, r1);	 Catch:{ Throwable -> 0x018e }
        r0 = r28;	 Catch:{ Throwable -> 0x018e }
        r0.printStackTrace();	 Catch:{ Throwable -> 0x018e }
        r3 = 0;	 Catch:{ Throwable -> 0x018e }
        m_bIsExtractSuccess = r3;	 Catch:{ Throwable -> 0x018e }
    L_0x0138:
        if (r5 == 0) goto L_0x01a6;
    L_0x013a:
        r5.close();	 Catch:{ IOException -> 0x0190 }
        return;
    L_0x013e:
        r15 = 0;
        goto L_0x00e3;
    L_0x0140:
        r0 = r27;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0.close();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        goto L_0x0149;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x0146:
        goto L_0x0033;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
    L_0x0149:
        r0 = r24;	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        r0.close();	 Catch:{ NameNotFoundException -> 0x0100, IOException -> 0x014f, Throwable -> 0x01a0 }
        goto L_0x0146;
    L_0x014f:
        r30 = move-exception;
        r5 = r12;
    L_0x0151:
        r23 = new java.lang.StringBuilder;	 Catch:{ Throwable -> 0x018e }
        goto L_0x0157;	 Catch:{ Throwable -> 0x018e }
    L_0x0154:
        goto L_0x0102;	 Catch:{ Throwable -> 0x018e }
    L_0x0157:
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r0.<init>();	 Catch:{ Throwable -> 0x018e }
        r29 = "Error! While file I/O";	 Catch:{ Throwable -> 0x018e }
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r1 = r29;	 Catch:{ Throwable -> 0x018e }
        r23 = r0.append(r1);	 Catch:{ Throwable -> 0x018e }
        r0 = r30;	 Catch:{ Throwable -> 0x018e }
        r34 = r0.getMessage();	 Catch:{ Throwable -> 0x018e }
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r1 = r34;	 Catch:{ Throwable -> 0x018e }
        r23 = r0.append(r1);	 Catch:{ Throwable -> 0x018e }
        r0 = r23;	 Catch:{ Throwable -> 0x018e }
        r34 = r0.toString();	 Catch:{ Throwable -> 0x018e }
        r29 = "WAZE";	 Catch:{ Throwable -> 0x018e }
        r0 = r29;	 Catch:{ Throwable -> 0x018e }
        r1 = r34;	 Catch:{ Throwable -> 0x018e }
        android.util.Log.e(r0, r1);	 Catch:{ Throwable -> 0x018e }
        r0 = r30;	 Catch:{ Throwable -> 0x018e }
        r0.printStackTrace();	 Catch:{ Throwable -> 0x018e }
        r3 = 0;	 Catch:{ Throwable -> 0x018e }
        m_bIsExtractSuccess = r3;	 Catch:{ Throwable -> 0x018e }
        goto L_0x0138;
    L_0x018c:
        r5 = r12;
        goto L_0x0138;
    L_0x018e:
        r31 = move-exception;
    L_0x018f:
        throw r31;
    L_0x0190:
        r32 = move-exception;
        r29 = "Waze";
        r33 = "Error closing the archive";
        r0 = r29;
        r1 = r33;
        android.util.Log.e(r0, r1);
        r3 = 0;
        m_bIsExtractSuccess = r3;
        return;
    L_0x01a0:
        r31 = move-exception;
        goto L_0x018f;
    L_0x01a2:
        r30 = move-exception;
        goto L_0x0151;
    L_0x01a4:
        r28 = move-exception;
        goto L_0x0154;
    L_0x01a6:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ResManager.ExtractFromZip(java.lang.String, java.lang.String, com.waze.ResManager$ZipEntryType, com.waze.Utils$ExceptionEntry[]):void");
    }

    public static String GetSplashName(boolean wide) throws  {
        return mSplashName;
    }

    private native void InitResManagerNTV() throws ;

    public static void Prepare() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find block by offset: 0x0083 in list []
	at jadx.core.utils.BlockUtils.getBlockByOffset(BlockUtils.java:42)
	at jadx.core.dex.instructions.IfNode.initBlocks(IfNode.java:60)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.initBlocksInIfNodes(BlockFinish.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockFinish.visit(BlockFinish.java:33)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = com.waze.AppService.getAppContext();
        if (r0 != 0) goto L_0x000e;
    L_0x0006:
        r1 = "WAZE";
        r2 = "The context is not initialized";
        android.util.Log.e(r1, r2);
        return;
    L_0x000e:
        r3 = new java.lang.StringBuilder;
        r3.<init>();	 Catch:{ NameNotFoundException -> 0x0090 }
        r4 = getExternalStorage();	 Catch:{ NameNotFoundException -> 0x0090 }
        r3 = r3.append(r4);	 Catch:{ NameNotFoundException -> 0x0090 }
        r1 = "/waze/";	 Catch:{ NameNotFoundException -> 0x0090 }
        r3 = r3.append(r1);	 Catch:{ NameNotFoundException -> 0x0090 }
        r4 = r3.toString();	 Catch:{ NameNotFoundException -> 0x0090 }
        mSDCardResDir = r4;
        r3 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0090 }
        r3.<init>();	 Catch:{ NameNotFoundException -> 0x0090 }
        r4 = getExternalStorage();	 Catch:{ NameNotFoundException -> 0x0090 }
        r3 = r3.append(r4);	 Catch:{ NameNotFoundException -> 0x0090 }
        r1 = "/waze/";	 Catch:{ NameNotFoundException -> 0x0090 }
        r3 = r3.append(r1);	 Catch:{ NameNotFoundException -> 0x0090 }
        r4 = r3.toString();	 Catch:{ NameNotFoundException -> 0x0090 }
        mAppDir = r4;
        r3 = new java.lang.StringBuilder;	 Catch:{ NameNotFoundException -> 0x0090 }
        r3.<init>();	 Catch:{ NameNotFoundException -> 0x0090 }
        r5 = com.waze.AppService.getAppContext();	 Catch:{ NameNotFoundException -> 0x0090 }
        r6 = r5.getFilesDir();	 Catch:{ NameNotFoundException -> 0x0090 }
        r4 = r6.getParent();	 Catch:{ NameNotFoundException -> 0x0090 }
        r3 = r3.append(r4);	 Catch:{ NameNotFoundException -> 0x0090 }
        r1 = "/";	 Catch:{ NameNotFoundException -> 0x0090 }
        r3 = r3.append(r1);	 Catch:{ NameNotFoundException -> 0x0090 }
        r4 = r3.toString();	 Catch:{ NameNotFoundException -> 0x0090 }
        mPkgDir = r4;	 Catch:{ NameNotFoundException -> 0x0090 }
        r7 = r0.getPackageManager();	 Catch:{ NameNotFoundException -> 0x0090 }
        r4 = r0.getPackageName();	 Catch:{ NameNotFoundException -> 0x0090 }
        r9 = 0;	 Catch:{ NameNotFoundException -> 0x0090 }
        r8 = r7.getPackageInfo(r4, r9);	 Catch:{ NameNotFoundException -> 0x0090 }
        r6 = new java.io.File;
        r4 = mAppDir;	 Catch:{ NameNotFoundException -> 0x0090 }
        r6.<init>(r4);	 Catch:{ NameNotFoundException -> 0x0090 }
        r10 = GetVersionFromFile();	 Catch:{ NameNotFoundException -> 0x0090 }
        r11 = r8.versionCode;
        if (r10 < r11) goto L_0x0083;	 Catch:{ NameNotFoundException -> 0x0090 }
    L_0x007d:
        r12 = r6.exists();	 Catch:{ NameNotFoundException -> 0x0090 }
        if (r12 != 0) goto L_0x009b;	 Catch:{ NameNotFoundException -> 0x0090 }
    L_0x0083:
        r9 = 1;
        mUpgradeRun = r9;
        r11 = android.os.Build.VERSION.SDK_INT;
        r9 = 23;	 Catch:{ NameNotFoundException -> 0x0090 }
        if (r11 < r9) goto L_0x0097;	 Catch:{ NameNotFoundException -> 0x0090 }
    L_0x008c:
        CarryOn();	 Catch:{ NameNotFoundException -> 0x0090 }
        return;
    L_0x0090:
        r13 = move-exception;
        r1 = "Prepare failure";
        com.waze.Logger.m39e(r1, r13);
        return;
    L_0x0097:
        CarryOn();	 Catch:{ NameNotFoundException -> 0x0090 }
        return;
    L_0x009b:
        r1 = "WAZE";	 Catch:{ NameNotFoundException -> 0x0090 }
        r2 = "Resources extraction unnecessary";	 Catch:{ NameNotFoundException -> 0x0090 }
        android.util.Log.d(r1, r2);	 Catch:{ NameNotFoundException -> 0x0090 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.ResManager.Prepare():void");
    }

    public static ResManager create() throws  {
        if (mInstance == null) {
            mInstance = new ResManager();
        }
        return mInstance;
    }

    public static ResManager getInstance() throws  {
        create();
        return mInstance;
    }

    public static void CopyFilesToInternalMemory() throws  {
        if (!new File(mSDCardDir).renameTo(new File(mSDCardResDir))) {
        }
    }

    private static String getExternalStorage() throws  {
        if (VERSION.SDK_INT >= 23) {
            return AppService.getAppContext().getFilesDir().getParent();
        }
        return Environment.getExternalStorageDirectory().getPath();
    }

    public static boolean Is3x() throws  {
        Display $r0 = AppService.getDisplay();
        return ((float) Math.min($r0.getHeight(), $r0.getWidth())) / 320.0f >= 3.0f;
    }

    public static boolean Is2x() throws  {
        Display $r0 = AppService.getDisplay();
        return ((float) Math.min($r0.getHeight(), $r0.getWidth())) / 320.0f >= LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
    }

    public static boolean IsHD() throws  {
        Display $r0 = AppService.getDisplay();
        return $r0.getHeight() >= 640 || $r0.getWidth() >= 640;
    }

    public static boolean IsLD() throws  {
        Display $r0 = AppService.getDisplay();
        return $r0.getHeight() <= 240 || $r0.getWidth() <= 240;
    }

    public static Typeface getRobotoBlack(Context $r0) throws  {
        if (mFaceRobotoBlack == null) {
            mFaceRobotoBlack = Typeface.createFromAsset($r0.getAssets(), mFontRobotoBlackPath);
        }
        return mFaceRobotoBlack;
    }

    public static Typeface getRobotoBold(Context $r0) throws  {
        if (mFaceRobotoBold == null) {
            mFaceRobotoBold = Typeface.createFromAsset($r0.getAssets(), mFontRobotoBoldPath);
        }
        return mFaceRobotoBold;
    }

    public static Typeface getRobotoLight(Context $r0) throws  {
        if (mFaceRobotoLight == null) {
            mFaceRobotoLight = Typeface.createFromAsset($r0.getAssets(), mFontRobotoLightPath);
        }
        return mFaceRobotoLight;
    }

    public static Typeface getRobotoMedium(Context $r0) throws  {
        if (mFaceRobotoMedium == null) {
            mFaceRobotoMedium = Typeface.createFromAsset($r0.getAssets(), mFontRobotoMediumPath);
        }
        return mFaceRobotoMedium;
    }

    public static Typeface getRobotoReg(Context $r0) throws  {
        if (mFaceRobotoReg == null) {
            mFaceRobotoReg = Typeface.createFromAsset($r0.getAssets(), mFontRobotoRegPath);
        }
        return mFaceRobotoReg;
    }

    public static Typeface getRobotoBoldItalic(Context $r0) throws  {
        if (mFaceRobotoBoldIt == null) {
            mFaceRobotoBoldIt = Typeface.createFromAsset($r0.getAssets(), mFontRobotoBoldItPath);
        }
        return mFaceRobotoBoldIt;
    }

    public static Typeface getRobotoLightItalic(Context $r0) throws  {
        if (mFaceRobotoLightIt == null) {
            mFaceRobotoLightIt = Typeface.createFromAsset($r0.getAssets(), mFontRobotoLightItPath);
        }
        return mFaceRobotoLightIt;
    }

    public static Typeface getRobotoMediumItalic(Context $r0) throws  {
        if (mFaceRobotoMediumIt == null) {
            mFaceRobotoMediumIt = Typeface.createFromAsset($r0.getAssets(), mFontRobotoMediumItPath);
        }
        return mFaceRobotoMediumIt;
    }

    public static Typeface getRobotoRegItalic(Context $r0) throws  {
        if (mFaceRobotoRegIt == null) {
            mFaceRobotoRegIt = Typeface.createFromAsset($r0.getAssets(), mFontRobotoRegItPath);
        }
        return mFaceRobotoRegIt;
    }

    public static Typeface getRobotoCondensedBold(Context $r0) throws  {
        if (mFaceRobotoCondensedBold == null) {
            mFaceRobotoCondensedBold = Typeface.createFromAsset($r0.getAssets(), mFontRobotoCondensedBoldPath);
        }
        return mFaceRobotoCondensedBold;
    }

    public static Typeface getRobotoCondensedLight(Context $r0) throws  {
        if (mFaceRobotoCondensedLight == null) {
            mFaceRobotoCondensedLight = Typeface.createFromAsset($r0.getAssets(), mFontRobotoCondensedLightPath);
        }
        return mFaceRobotoCondensedLight;
    }

    public static Typeface getRobotoCondensedReg(Context $r0) throws  {
        if (mFaceRobotoCondensedReg == null) {
            mFaceRobotoCondensedReg = Typeface.createFromAsset($r0.getAssets(), mFontRobotoCondensedRegPath);
        }
        return mFaceRobotoCondensedReg;
    }

    public static Typeface getRobotoSlabBold(Context $r0) throws  {
        if (mFaceRobotoSlabBold == null) {
            mFaceRobotoSlabBold = Typeface.createFromAsset($r0.getAssets(), mFontRobotoSlabBoldPath);
        }
        return mFaceRobotoSlabBold;
    }

    public static Typeface getRobotoSlabReg(Context $r0) throws  {
        if (mFaceRobotoSlabReg == null) {
            mFaceRobotoSlabReg = Typeface.createFromAsset($r0.getAssets(), mFontRobotoSlabRegPath);
        }
        return mFaceRobotoSlabReg;
    }

    public static Typeface getProximaBold(Context $r0) throws  {
        if (mFaceProximaBold == null) {
            mFaceProximaBold = Typeface.createFromAsset($r0.getAssets(), mProximaBoldPath);
        }
        return mFaceProximaBold;
    }

    public static Typeface getProximaExBold(Context $r0) throws  {
        if (mFaceProximaExBold == null) {
            mFaceProximaExBold = Typeface.createFromAsset($r0.getAssets(), mProximaExBoldPath);
        }
        return mFaceProximaExBold;
    }

    public static Typeface getProximaLight(Context $r0) throws  {
        if (mFaceProximaLight == null) {
            mFaceProximaLight = Typeface.createFromAsset($r0.getAssets(), mProximaLightPath);
        }
        return mFaceProximaLight;
    }

    public static Typeface getProximaSemibold(Context $r0) throws  {
        if (mFaceProximaSemibold == null) {
            mFaceProximaSemibold = Typeface.createFromAsset($r0.getAssets(), mProximaSemiboldPath);
        }
        return mFaceProximaSemibold;
    }

    public static Typeface getProximaReg(Context $r0) throws  {
        if (mFaceProximaReg == null) {
            mFaceProximaReg = Typeface.createFromAsset($r0.getAssets(), mProximaRegPath);
        }
        return mFaceProximaReg;
    }

    public static Typeface getProximaExtraBoldItalic(Context $r0) throws  {
        if (mFaceProximaExBoldIt == null) {
            mFaceProximaExBoldIt = Typeface.createFromAsset($r0.getAssets(), mProximaExBoldItPath);
        }
        return mFaceProximaExBoldIt;
    }

    public static Typeface getProximaLightItalic(Context $r0) throws  {
        if (mFaceProximaLightIt == null) {
            mFaceProximaLightIt = Typeface.createFromAsset($r0.getAssets(), mProximaLightItPath);
        }
        return mFaceProximaLightIt;
    }

    public static Typeface getProximaSemiboldItalic(Context $r0) throws  {
        if (mFaceProximaSemiboldIt == null) {
            mFaceProximaSemiboldIt = Typeface.createFromAsset($r0.getAssets(), mProximaSemiboldItPath);
        }
        return mFaceProximaSemiboldIt;
    }

    public static Typeface getProximaRegItalic(Context $r0) throws  {
        if (mFaceProximaRegIt == null) {
            mFaceProximaRegIt = Typeface.createFromAsset($r0.getAssets(), mProximaRegItPath);
        }
        return mFaceProximaRegIt;
    }

    public static String GetSplashPath(boolean $z0) throws  {
        return $z0 ? mSplashWidePath : mSplashPath;
    }

    public static String GetSkinsPath() throws  {
        return mSDCardResDir + mSkinsPath;
    }

    public static String GetEncPath() throws  {
        return mSDCardResDir;
    }

    public static InputStream LoadResStream(String $r0, String $r1, String[] $r2) throws  {
        $r1 = LocateFullResPath($r0, $r1, $r2);
        InputStream $r5 = null;
        if ($r1 != null) {
            try {
                return new FileInputStream($r1);
            } catch (Exception $r3) {
                Logger.m39e("Error loading image from package", $r3);
                return null;
            }
        }
        if (Is3x()) {
            $r5 = LoadAssetStream(addResImageExtension(add3xSuffix($r0)), $r2);
        }
        if (Is2x()) {
            $r5 = LoadAssetStream(addResImageExtension(add2xSuffix($r0)), $r2);
        }
        return $r5 == null ? LoadAssetStream(addResImageExtension($r0), $r2) : $r5;
    }

    public static String LocateFullResPath(String $r0, String $r1, String[] aAssetPathList) throws  {
        File $r3 = new File($r1 + "/" + addResImageExtension($r0));
        File $r4 = new File($r1 + "/" + addResImageExtension(add2xSuffix($r0)));
        File $r5 = new File($r1 + "/" + addResImageExtension(add3xSuffix($r0)));
        if (Is3x() && $r5.exists()) {
            return $r5.getAbsolutePath();
        }
        if (Is2x() && $r4.exists()) {
            return $r4.getAbsolutePath();
        }
        return $r3.exists() ? $r3.getAbsolutePath() : null;
    }

    private static InputStream LoadAssetStream(String $r0, String[] $r1) throws  {
        InputStream $r4 = null;
        try {
            AssetManager $r6 = AppService.getAppContext().getAssets();
            for (String $r3 : $r1) {
                InputStream $r8 = LoadAssetEntry($r6, $r3 + $r0);
                $r4 = $r8;
                if ($r8 != null) {
                    return $r8;
                }
            }
            return $r4;
        } catch (Exception $r2) {
            Logger.m39e("Error loading asset", $r2);
            return $r4;
        }
    }

    public ResData LoadSkin(String $r1) throws  {
        return LoadResData($r1, GetSkinsPath(), new String[]{"res/skins/default/"});
    }

    public ResData LoadResData(String $r1, String $r2, String[] $r3) throws  {
        Exception $r8;
        ResData $r5 = null;
        try {
            InputStream $r6 = LoadResStream($r1, $r2, $r3);
            if ($r6 == null) {
                return null;
            }
            ResData $r4 = new ResData(Utils.ReadStream($r6), 0);
            try {
                $r6.close();
                return $r4;
            } catch (Exception e) {
                $r8 = e;
                $r5 = $r4;
                Logger.m39e("Error loading image from package", $r8);
                return $r5;
            }
        } catch (Exception e2) {
            $r8 = e2;
            Logger.m39e("Error loading image from package", $r8);
            return $r5;
        }
    }

    public static int GetDrawableId(String $r0) throws  {
        try {
            return drawable.class.getField($r0).getInt(null);
        } catch (Exception $r1) {
            Logger.m38e("Error generating resource id for resource: " + $r0);
            $r1.printStackTrace();
            if ($assertionsDisabled) {
                return 0;
            }
            throw new AssertionError();
        }
    }

    public static int[] GetDrawableIds(String[] $r0) throws  {
        int[] $r1 = new int[$r0.length];
        for (int $i1 = 0; $i1 < $r0.length; $i1++) {
            $r1[$i1] = GetDrawableId($r0[$i1]);
        }
        return $r1;
    }

    public static Drawable GetSkinDrawable(String $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        InputStream $r4 = LoadSkinStream($r0);
        Resources $r6 = AppService.getAppContext().getResources();
        BitmapDrawable $r3 = new BitmapDrawable($r6, $r4);
        if ($r3.getBitmap() == null && ConfigValues.getBoolValue(443)) {
            String $r8 = getSkinPath($r0);
            if (!($r8 == null || $r8.isEmpty())) {
                new File($r8).delete();
            }
        }
        Display $r9 = AppService.getDisplay();
        DisplayMetrics $r2 = new DisplayMetrics();
        $r9.getMetrics($r2);
        $r3.setTargetDensity($r2);
        if ($r4 != null) {
            return $r3;
        }
        int $i0 = GetDrawableId($r0);
        return $i0 != 0 ? $r6.getDrawable($i0) : null;
    }

    public static Bitmap GetEncBitmap(String $r0) throws  {
        if ($r0 == null) {
            return null;
        }
        Bitmap $r3 = BitmapFactory.decodeStream(LoadEncStream($r0));
        if ($r3 != null) {
            return $r0.endsWith(".9.png") ? Bitmap.createBitmap($r3, 1, 1, $r3.getWidth() - 2, $r3.getHeight() - 2) : $r3;
        } else {
            $r0 = getEncPath($r0);
            if ($r0 == null) {
                return null;
            }
            if ($r0.isEmpty()) {
                return null;
            }
            new File($r0).delete();
            return null;
        }
    }

    public static Drawable[] GetSkinDrawables(String[] $r0) throws  {
        Drawable[] $r1 = new Drawable[$r0.length];
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r1[$i0] = GetSkinDrawable($r0[$i0]);
        }
        return $r1;
    }

    public static InputStream LoadSkinStream(String $r0) throws  {
        return LoadResStream($r0, GetSkinsPath(), new String[]{"res/skins/default/"});
    }

    public static String getSkinPath(String $r0) throws  {
        return LocateFullResPath(mSkinsPath + $r0, GetEncPath(), new String[]{"res/skins/default/"});
    }

    public static InputStream LoadEncStream(String $r0) throws  {
        return LoadResStream(mSkinsPath + $r0, GetEncPath(), new String[]{"res/skins/default/"});
    }

    public static String getEncPath(String $r0) throws  {
        return LocateFullResPath(mSkinsPath + $r0, GetEncPath(), new String[]{"res/skins/default/"});
    }

    public String[] LoadResList(String $r1) throws  {
        String[] $r4 = null;
        AssetManager $r6 = AppService.getAppContext().getAssets();
        if ($r6 == null) {
            Logger.ee("Error loading resources list. Can't access asset manager");
            return null;
        }
        ArrayList $r3 = new ArrayList();
        try {
            $r3.addAll(Arrays.asList($r6.list(Utils.removeMultipleSlash($r1))));
            $r4 = (String[]) $r3.toArray(new String[$r3.size()]);
        } catch (Exception $r2) {
            Logger.m39e("Exception while loading list of resources. Path: " + $r1, $r2);
        }
        return $r4;
    }

    public static void downloadRes(final int $i0, final String $r0, final DownloadResCallback $r1) throws  {
        Log.d(Logger.TAG, "downloadRes running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                Log.d(Logger.TAG, "downloadRes event running in thread " + Thread.currentThread().getId());
                ResManager.getInstance().DownloadResNTV($i0, $r0, $r1);
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "downloadRes callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public static void downloadResCallback(final DownloadResCallback $r0, final int $i0) throws  {
        Log.d(Logger.TAG, "downloadResCallback running in thread " + Thread.currentThread().getId());
        AppService.Post(new RunnableCallback(AppService.getNativeManager()) {
            public void event() throws  {
                Log.d(Logger.TAG, "downloadResCallback event running in thread " + Thread.currentThread().getId());
                if ($r0 != null) {
                    $r0.downloadResCallback($i0);
                }
            }

            public void callback() throws  {
                Log.d(Logger.TAG, "downloadResCallback callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    private ResManager() throws  {
        InitResManagerNTV();
    }

    private static InputStream LoadAssetEntry(AssetManager $r0, String $r1) throws  {
        try {
            return $r0.open($r1);
        } catch (Exception e) {
            return null;
        }
    }

    private static ExceptionEntry[] BuildUpgradeExceptionList() throws  {
        String $r1 = mMapsDir.replaceAll("/", "");
        String $r2 = mTtsDir.replaceAll("/", "");
        return new ExceptionEntry[]{new ExceptionEntry(mHistoryFile, 0), new ExceptionEntry(mSessionFile, 0), new ExceptionEntry(mPrefFile, 0), new ExceptionEntry(mSearchConf, 1), new ExceptionEntry(mSoundDir, 1, 2), new ExceptionEntry("user", 0), new ExceptionEntry(mUserDbFile, 0), new ExceptionEntry($r1, 1, 2), new ExceptionEntry(mLangPrefix, 1, 2), new ExceptionEntry(mPromptsConf, 1), new ExceptionEntry(mPromptsOldConf, 1), new ExceptionEntry(mSkinsPath, 0, 2), new ExceptionEntry($r2, 1, 2)};
    }

    private static ExceptionEntry[] BuildCleanUpPkgDirExceptions(boolean $z0) throws  {
        ArrayList $r1 = new ArrayList();
        $r1.add(new ExceptionEntry(mPkgCacheDir, 0));
        $r1.add(new ExceptionEntry(mPkgDatabasesDir, 0));
        $r1.add(new ExceptionEntry(mPkgLibDir, 0));
        $r1.add(new ExceptionEntry(mPkgSharedPrefsDir, 0));
        $r1.add(new ExceptionEntry(mPkgCodeCacheDir, 0));
        $r1.add(new ExceptionEntry("custom_recordings", 0));
        if ($z0) {
            String $r3 = mMapsDir.replaceAll("/", "");
            String $r4 = mTtsDir.replaceAll("/", "");
            $r1.add(new ExceptionEntry("user", 0));
            $r1.add(new ExceptionEntry(mSessionFile, 0));
            $r1.add(new ExceptionEntry(mPrefFile, 0));
            $r1.add(new ExceptionEntry(mHistoryFile, 0));
            $r1.add(new ExceptionEntry(mUserDbFile, 0));
            $r1.add(new ExceptionEntry(mSearchConf, 0));
            $r1.add(new ExceptionEntry(mSoundDir, 0, 2));
            $r1.add(new ExceptionEntry($r3, 0, 2));
            $r1.add(new ExceptionEntry(mLangPrefix, 0, 2));
            $r1.add(new ExceptionEntry(mPromptsConf, 0));
            $r1.add(new ExceptionEntry(mPromptsOldConf, 0));
            $r1.add(new ExceptionEntry(mSkinsPath, 0, 2));
            $r1.add(new ExceptionEntry($r4, 0, 2));
            $r1.add(new ExceptionEntry(mSearchConf, 0, 2));
            $r1.add(new ExceptionEntry("skin_backup", 0, 2));
        }
        ExceptionEntry[] $r0 = new ExceptionEntry[$r1.size()];
        $r1.toArray($r0);
        $r1.clear();
        return $r0;
    }

    private static ExceptionEntry[] BuildCleanInstallExceptionList() throws  {
        return new ExceptionEntry[]{new ExceptionEntry("user", 0), new ExceptionEntry(mSessionFile, 0), new ExceptionEntry(mPrefFile, 0), new ExceptionEntry(mHistoryFile, 0), new ExceptionEntry(mSkinsPath, 0, 2)};
    }

    private static ExceptionEntry[] BuildSkinsExceptionList() throws  {
        return new ExceptionEntry[]{new ExceptionEntry(mImageExtension, 0, 5)};
    }

    private static boolean CheckException(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/Utils$ExceptionEntry;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")Z"}) ArrayList<ExceptionEntry> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/Utils$ExceptionEntry;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")Z"}) String $r1, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/Utils$ExceptionEntry;", ">;", "Ljava/lang/String;", "Ljava/lang/String;", ")Z"}) String $r2) throws  {
        if ($r0 == null) {
            return false;
        }
        int $i0 = $r0.indexOf(new ExceptionEntry($r1, 1, 0));
        if ($i0 == -1) {
            return false;
        }
        if (((ExceptionEntry) $r0.get($i0)).mType == 1) {
            return new File(new StringBuilder().append($r2).append($r1).toString()).exists();
        } else {
            return true;
        }
    }

    private static int GetVersionFromFile() throws  {
        String $r4 = mPkgDir + "version";
        try {
            if (!new File($r4).exists()) {
                return -1;
            }
            char[] $r2 = new char[7];
            FileReader $r1 = new FileReader($r4);
            $r1.read($r2, 0, 7);
            int $i0 = Integer.parseInt(new String($r2));
            $r1.close();
            return $i0;
        } catch (Exception $r0) {
            Log.e(Logger.TAG, "Error! While file I/O" + $r0.getMessage() + $r0.getStackTrace());
            return -1;
        }
    }

    private static void SetVersionToFile(int $i0) throws  {
        try {
            FileWriter $r1 = new FileWriter(mPkgDir + "version");
            $r1.write(String.valueOf($i0));
            $r1.close();
        } catch (Exception $r0) {
            Log.e(Logger.TAG, "Error! Failed to update version file" + $r0.getStackTrace());
        }
    }

    private static String add2xSuffix(String $r0) throws  {
        return addResSuffix($r0, SKIN_SUFFIX_2X);
    }

    private static String add3xSuffix(String $r0) throws  {
        return addResSuffix($r0, SKIN_SUFFIX_3X);
    }

    private static String addResSuffix(String $r1, String $r0) throws  {
        if ($r1.contains(SKIN_SUFFIX_2X) || $r1.contains(SKIN_SUFFIX_3X) || $r1.contains(SKIN_SUFFIX_5X) || $r1.contains(SKIN_SUFFIX_10X)) {
            return $r1;
        }
        int $i0 = $r1.lastIndexOf(FileUploadSession.SEPARATOR);
        if ($i0 != -1) {
            $r1 = $r1.substring(0, $i0) + $r0 + $r1.substring($i0, $r1.length());
        } else {
            $r1 = $r1 + $r0;
        }
        return $r1;
    }

    private static String addResImageExtension(String $r0) throws  {
        return $r0.endsWith(mImageExtension) ? $r0 : $r0 + mImageExtension;
    }
}
