package com.waze;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Base64;
import android.util.Log;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.auth.firstparty.proximity.data.PermitAccess;
import com.google.android.gms.common.GoogleApiAvailability;
import com.waze.NavBarManager.DoublePosition;
import com.waze.main.navigate.NavigationItem;
import com.waze.navigate.DriveToNativeManager;
import dalvik.annotation.Signature;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.FileChannel;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {
    static byte[] ivBytes = new byte[]{(byte) 105, (byte) -35, (byte) -88, (byte) 69, (byte) 92, (byte) 125, (byte) -44, (byte) 37, (byte) 75, (byte) -13, (byte) 83, (byte) -73, (byte) 115, (byte) 48, (byte) 78, (byte) -20};

    public static class ExceptionEntry {
        public static final int COMPARE_ENDS_WITH = 4;
        public static final int COMPARE_END_OF = 5;
        public static final int COMPARE_EQUAL = 1;
        public static final int COMPARE_STARTS_WITH = 3;
        public static final int COMPARE_START_OF = 2;
        public static final int COMPARE_USE_ANOTHER_SIDE = 0;
        public static final int EXCLUDE_ALWAYS = 0;
        public static final int EXCLUDE_IF_EXIST = 1;
        public int mCompareType = 1;
        public String mEntry = null;
        public int mType = 0;

        public ExceptionEntry(String $r1, int $i0) throws  {
            setEntry($r1, $i0);
        }

        public ExceptionEntry(String $r1, int $i0, int $i1) throws  {
            setEntry($r1, $i0, $i1);
        }

        public void setEntry(String $r1, int $i0) throws  {
            this.mEntry = new String($r1);
            this.mType = $i0;
        }

        public void setEntry(String $r1, int $i0, int $i1) throws  {
            this.mEntry = new String($r1);
            this.mType = $i0;
            this.mCompareType = $i1;
        }

        public boolean equals(Object $r1) throws  {
            boolean $z0 = false;
            if ($r1 instanceof ExceptionEntry) {
                $z0 = equalTo((ExceptionEntry) $r1);
            }
            if ($r1 instanceof String) {
                return equalTo((String) $r1);
            }
            return $z0;
        }

        private boolean equalTo(ExceptionEntry $r1) throws  {
            if (this.mCompareType == 0) {
                return $r1.equalTo(this.mEntry);
            }
            return equalTo($r1.mEntry);
        }

        private boolean equalTo(String $r1) throws  {
            boolean $z0 = false;
            if (this.mCompareType == 1 || this.mCompareType == 0) {
                $z0 = this.mEntry.equals($r1);
            }
            if (this.mCompareType == 2) {
                $z0 = $r1.startsWith(this.mEntry);
            }
            if (this.mCompareType == 3) {
                $z0 = this.mEntry.startsWith($r1);
            }
            if (this.mCompareType == 4) {
                $z0 = this.mEntry.endsWith($r1);
            }
            if (this.mCompareType == 5) {
                return $r1.endsWith(this.mEntry);
            }
            return $z0;
        }
    }

    public static long convertDaysToMilliseconds(long $l0) throws  {
        return ((1000 * $l0) * 24) * 3600;
    }

    public static int getHours(int $i0) throws  {
        return $i0 > 0 ? ($i0 / 60) / 60 : 0;
    }

    public static int getMinutes(int $i0) throws  {
        return $i0 > 0 ? ($i0 / 60) % 60 : 0;
    }

    public static SDKNavigationItem CopyFromNavigationItem(NavigationItem $r0) throws  {
        return new SDKNavigationItem($r0.distance, $r0.address, $r0.instruction, $r0.instructionExit);
    }

    public static SDKNavigationItem[] CopyFromNavigationItemArray(NavigationItem[] $r0) throws  {
        SDKNavigationItem[] $r1 = new SDKNavigationItem[$r0.length];
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            $r1[$i0] = CopyFromNavigationItem($r0[$i0]);
        }
        return $r1;
    }

    public static long getTransportationLong(String $r0) throws  {
        return AppService.getAppContext().getSharedPreferences("com.waze.transportation", 0).getLong($r0, 0);
    }

    public static void setTransportationLong(String $r0, long $l0) throws  {
        SharedPreferences $r2 = AppService.getAppContext().getSharedPreferences("com.waze.transportation", 0);
        $r2.edit().putLong($r0, $l0).apply();
        $r2.edit().commit();
    }

    public static String getCertificateSHA1Fingerprint(String $r0, Context $r1) throws  {
        PackageInfo $r6 = null;
        try {
            $r6 = $r1.getPackageManager().getPackageInfo($r0, 64);
        } catch (NameNotFoundException $r13) {
            $r13.printStackTrace();
        }
        ByteArrayInputStream $r3 = new ByteArrayInputStream($r6.signatures[0].toByteArray());
        CertificateFactory $r9 = null;
        try {
            $r9 = CertificateFactory.getInstance("X509");
        } catch (CertificateException $r14) {
            $r14.printStackTrace();
        }
        X509Certificate $r10 = null;
        try {
            $r10 = (X509Certificate) $r9.generateCertificate($r3);
        } catch (CertificateException $r15) {
            $r15.printStackTrace();
        }
        try {
            return byte2HexFormatted(MessageDigest.getInstance("SHA1").digest($r10.getEncoded()));
        } catch (NoSuchAlgorithmException $r2) {
            $r2.printStackTrace();
            return null;
        } catch (CertificateEncodingException $r16) {
            $r16.printStackTrace();
            return null;
        }
    }

    public static String buildJsonFromDoublePoints(DoublePosition[] $r0) throws  {
        JSONObject $r4 = new JSONObject();
        JSONObject $r3 = new JSONObject();
        $r4.put("geometry", $r3);
        $r3.put("type", "LineString");
        JSONArray $r1 = new JSONArray();
        $r3.put("coordinates", $r1);
        int $i0 = 0;
        while ($i0 < $r0.length) {
            JSONArray $r6 = new JSONArray();
            $r6.put($r0[$i0].lon);
            try {
                $r6.put($r0[$i0].lat);
                $r1.put($r6);
                $i0++;
            } catch (JSONException $r2) {
                $r2.printStackTrace();
            }
        }
        return $r4.toString();
    }

    public static byte[] EncryptUsingSymetricKey(String $r0, String $r1) throws  {
        IvParameterSpec $r2 = new IvParameterSpec(ivBytes);
        try {
            Cipher $r5 = Cipher.getInstance("AES/CTR/NoPadding");
            try {
                $r5.init(1, new SecretKeySpec(Base64.decode($r1, 0), PermitAccess.TYPE_AES), $r2);
            } catch (InvalidAlgorithmParameterException e) {
            } catch (InvalidKeyException e2) {
                return null;
            }
            byte[] $r4 = new byte[0];
            try {
                return $r5.doFinal($r0.getBytes());
            } catch (IllegalBlockSizeException e3) {
                return null;
            } catch (BadPaddingException e4) {
                return null;
            }
        } catch (NoSuchAlgorithmException e5) {
            return null;
        } catch (NoSuchPaddingException e6) {
            return null;
        }
    }

    private static PublicKey getRSAKeyFromString(String $r0) throws  {
        try {
            return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode($r0, 0)));
        } catch (InvalidKeySpecException e) {
            return null;
        } catch (NoSuchAlgorithmException e2) {
            return null;
        }
    }

    public static String createAESKey() throws  {
        try {
            KeyGenerator $r1 = KeyGenerator.getInstance(PermitAccess.TYPE_AES);
            $r1.init(128);
            return Base64.encodeToString($r1.generateKey().getEncoded(), 0);
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    public static String byte2HexFormatted(byte[] $r0) throws  {
        StringBuilder $r1 = new StringBuilder($r0.length * 2);
        for (int $i0 = 0; $i0 < $r0.length; $i0++) {
            String $r2 = Integer.toHexString($r0[$i0]);
            String $r3 = $r2;
            int $i1 = $r2.length();
            if ($i1 == 1) {
                $r3 = AppEventsConstants.EVENT_PARAM_VALUE_NO + $r2;
            }
            if ($i1 > 2) {
                $r3 = $r3.substring($i1 - 2, $i1);
            }
            $r1.append($r3.toUpperCase());
            if ($i0 < $r0.length - 1) {
                $r1.append(':');
            }
        }
        return $r1.toString();
    }

    public static String removeMultipleSlash(String $r0) throws  {
        while ($r0.contains(DriveToNativeManager.IGNORED_CALENDAR_DELIMITER)) {
            $r0 = $r0.replace(DriveToNativeManager.IGNORED_CALENDAR_DELIMITER, "/");
        }
        return $r0;
    }

    public static void CopyFile() throws  {
    }

    public static void Copy(String $r0, String $r1) throws  {
        File $r4 = new File($r0);
        File $r3 = new File($r1);
        String $r2 = new String("/");
        if (!$r4.exists()) {
            Log.e("CopyDir", "Source does not exist!");
        } else if ($r4.isDirectory()) {
            $r3.mkdirs();
            String[] $r6 = $r4.list();
            for (int $i0 = 0; $i0 < $r6.length; $i0++) {
                Copy($r0 + $r2 + $r6[$i0], $r1 + $r2 + $r6[$i0]);
            }
        } else {
            CopyFile($r0, $r1);
        }
    }

    private static void CopyFile(String $r0, String $r1) throws  {
        try {
            FileChannel $r3 = new FileInputStream($r0).getChannel();
            FileChannel $r5 = new FileOutputStream($r1).getChannel();
            $r5.transferFrom($r3, 0, $r3.size());
            if ($r3 != null) {
                $r3.close();
            }
            if ($r5 != null) {
                $r5.close();
            }
        } catch (FileNotFoundException $r6) {
            Log.i("CopyFile", "File not found " + $r6.getMessage());
            $r6.printStackTrace();
        } catch (IOException $r8) {
            Log.i("CopyFile", "Transfer data error " + $r8.getMessage());
            $r8.printStackTrace();
        }
    }

    public static void Delete(File $r0) throws  {
        Delete($r0, null);
    }

    private static void Delete(@Signature({"(", "Ljava/io/File;", "Ljava/util/ArrayList", "<", "Lcom/waze/Utils$ExceptionEntry;", ">;)V"}) File $r0, @Signature({"(", "Ljava/io/File;", "Ljava/util/ArrayList", "<", "Lcom/waze/Utils$ExceptionEntry;", ">;)V"}) ArrayList<ExceptionEntry> $r1) throws  {
        try {
            if (!$r0.exists()) {
                Log.e("Delete", "Source does not exist! " + $r0.getAbsolutePath());
            } else if (!CheckDeleteFileException($r1, $r0.getName())) {
                if ($r0.isDirectory()) {
                    File[] $r5 = $r0.listFiles();
                    for (File $r6 : $r5) {
                        Delete($r6, $r1);
                    }
                }
                $r0.delete();
            }
        } catch (Exception $r2) {
            Log.e("Delete", "Delete failed for: " + $r0.getAbsolutePath() + " " + $r2.getMessage());
            $r2.printStackTrace();
        }
    }

    private static boolean CheckDeleteFileException(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/Utils$ExceptionEntry;", ">;", "Ljava/lang/String;", ")Z"}) ArrayList<ExceptionEntry> $r0, @Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/Utils$ExceptionEntry;", ">;", "Ljava/lang/String;", ")Z"}) String $r1) throws  {
        if ($r0 != null) {
            return $r0.indexOf(new ExceptionEntry($r1, 4, 0)) != -1;
        } else {
            return false;
        }
    }

    public static void DeleteDir(String $r0) throws  {
        DeleteDir($r0, false, null);
    }

    public static void DeleteDir(String $r0, boolean $z0) throws  {
        DeleteDir($r0, $z0, null);
    }

    public static void DeleteDir(String $r0, boolean $z0, ExceptionEntry[] $r1) throws  {
        ArrayList $r3 = null;
        if ($r1 != null) {
            $r3 = new ArrayList(Arrays.asList($r1));
        }
        File $r2 = new File($r0);
        if ($z0) {
            File[] $r5 = $r2.listFiles();
            if ($r5 != null) {
                for (File $r22 : $r5) {
                    Delete($r22, $r3);
                }
                return;
            }
            return;
        }
        Delete($r22, $r3);
    }

    public static byte[] ReadStream(InputStream $r0) throws IOException {
        byte[] $r1 = new byte[8192];
        ByteArrayOutputStream $r2 = new ByteArrayOutputStream();
        while (true) {
            int $i0 = $r0.read($r1);
            if ($i0 != -1) {
                $r2.write($r1, 0, $i0);
            } else {
                $r1 = $r2.toByteArray();
                $r2.close();
                return $r1;
            }
        }
    }

    public static Long bytes2Long(byte[] $r0) throws  {
        Long $r1 = new Long(0);
        int $i0 = 0;
        if ($r0 == null || $r0.length != 8) {
            return null;
        }
        for (int $i1 = 0; $i1 < 8; $i1++) {
            $r1 = Long.valueOf($r1.longValue() + ((((long) $r0[$i1]) & 255) << ($i0 * 8)));
            $i0++;
        }
        return $r1;
    }

    public static byte[] long2bytes(long $l0) throws  {
        byte[] $r0 = new byte[8];
        for (int $i1 = 0; $i1 < 8; $i1++) {
            $r0[$i1] = (byte) ((int) (255 & ($l0 >> ($i1 * 8))));
        }
        return $r0;
    }

    public static String formatTime(Context $r0, int $i0) throws  {
        TimeZone $r1 = TimeZone.getTimeZone("UTC");
        DateFormat $r2 = android.text.format.DateFormat.getTimeFormat($r0);
        $r2.setTimeZone($r1);
        return $r2.format(new Date((long) ($i0 * 1000)));
    }

    public static String formatTimeRelative(Context $r0, int $i0) throws  {
        TimeZone $r2 = Calendar.getInstance().getTimeZone();
        DateFormat $r3 = android.text.format.DateFormat.getTimeFormat($r0);
        $r3.setTimeZone($r2);
        return $r3.format(new Date(System.currentTimeMillis() + ((long) ($i0 * 1000))));
    }

    public static boolean checkIsGooglePlayServicesAvailable() throws  {
        return GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(AppService.getAppContext()) == 0;
    }

    public static LayoutParams duplicateLayoutParams(LayoutParams $r0) throws  {
        if (!($r0 instanceof FrameLayout.LayoutParams)) {
            return null;
        }
        FrameLayout.LayoutParams $r1 = (FrameLayout.LayoutParams) $r0;
        FrameLayout.LayoutParams $r2 = new FrameLayout.LayoutParams($r1.height, $r1.width);
        $r2.gravity = $r1.gravity;
        $r2.bottomMargin = $r1.bottomMargin;
        $r2.topMargin = $r1.topMargin;
        $r2.leftMargin = $r1.leftMargin;
        $r2.rightMargin = $r1.rightMargin;
        return $r2;
    }

    public static String getCallerClassName(String calledClassName) throws  {
        StackTraceElement[] $r3 = Thread.currentThread().getStackTrace();
        for (int $i0 = 1; $i0 < $r3.length; $i0++) {
            StackTraceElement $r1 = $r3[$i0];
            Log.e("WAZE trace", $i0 + ": " + $r1.getClassName() + ":" + $r1.getLineNumber() + ":" + $r1.getFileName() + ":" + $r1.getMethodName());
        }
        return null;
    }
}
