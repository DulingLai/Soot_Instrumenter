package android.support.v4.print;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build.VERSION;
import java.io.FileNotFoundException;

public final class PrintHelper {
    public static final int COLOR_MODE_COLOR = 2;
    public static final int COLOR_MODE_MONOCHROME = 1;
    public static final int ORIENTATION_LANDSCAPE = 1;
    public static final int ORIENTATION_PORTRAIT = 2;
    public static final int SCALE_MODE_FILL = 2;
    public static final int SCALE_MODE_FIT = 1;
    PrintHelperVersionImpl mImpl;

    public interface OnPrintFinishCallback {
        void onFinish() throws ;
    }

    interface PrintHelperVersionImpl {
        int getColorMode() throws ;

        int getOrientation() throws ;

        int getScaleMode() throws ;

        void printBitmap(String str, Bitmap bitmap, OnPrintFinishCallback onPrintFinishCallback) throws ;

        void printBitmap(String str, Uri uri, OnPrintFinishCallback onPrintFinishCallback) throws FileNotFoundException;

        void setColorMode(int i) throws ;

        void setOrientation(int i) throws ;

        void setScaleMode(int i) throws ;
    }

    private static final class PrintHelperKitkatImpl implements PrintHelperVersionImpl {
        private final PrintHelperKitkat mPrintHelper;

        PrintHelperKitkatImpl(Context $r1) throws  {
            this.mPrintHelper = new PrintHelperKitkat($r1);
        }

        public void setScaleMode(int $i0) throws  {
            this.mPrintHelper.setScaleMode($i0);
        }

        public int getScaleMode() throws  {
            return this.mPrintHelper.getScaleMode();
        }

        public void setColorMode(int $i0) throws  {
            this.mPrintHelper.setColorMode($i0);
        }

        public int getColorMode() throws  {
            return this.mPrintHelper.getColorMode();
        }

        public void setOrientation(int $i0) throws  {
            this.mPrintHelper.setOrientation($i0);
        }

        public int getOrientation() throws  {
            return this.mPrintHelper.getOrientation();
        }

        public void printBitmap(String $r1, Bitmap $r2, final OnPrintFinishCallback $r3) throws  {
            android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback $r5 = null;
            if ($r3 != null) {
                $r5 = new android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback() {
                    public void onFinish() throws  {
                        $r3.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap($r1, $r2, $r5);
        }

        public void printBitmap(String $r1, Uri $r2, final OnPrintFinishCallback $r3) throws FileNotFoundException {
            android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback $r5 = null;
            if ($r3 != null) {
                $r5 = new android.support.v4.print.PrintHelperKitkat.OnPrintFinishCallback() {
                    public void onFinish() throws  {
                        $r3.onFinish();
                    }
                };
            }
            this.mPrintHelper.printBitmap($r1, $r2, $r5);
        }
    }

    private static final class PrintHelperStubImpl implements PrintHelperVersionImpl {
        int mColorMode;
        int mOrientation;
        int mScaleMode;

        private PrintHelperStubImpl() throws  {
            this.mScaleMode = 2;
            this.mColorMode = 2;
            this.mOrientation = 1;
        }

        public void setScaleMode(int $i0) throws  {
            this.mScaleMode = $i0;
        }

        public int getColorMode() throws  {
            return this.mColorMode;
        }

        public void setColorMode(int $i0) throws  {
            this.mColorMode = $i0;
        }

        public void setOrientation(int $i0) throws  {
            this.mOrientation = $i0;
        }

        public int getOrientation() throws  {
            return this.mOrientation;
        }

        public int getScaleMode() throws  {
            return this.mScaleMode;
        }

        public void printBitmap(String jobName, Bitmap bitmap, OnPrintFinishCallback callback) throws  {
        }

        public void printBitmap(String jobName, Uri imageFile, OnPrintFinishCallback callback) throws  {
        }
    }

    public static boolean systemSupportsPrint() throws  {
        return VERSION.SDK_INT >= 19;
    }

    public PrintHelper(Context $r1) throws  {
        if (systemSupportsPrint()) {
            this.mImpl = new PrintHelperKitkatImpl($r1);
        } else {
            this.mImpl = new PrintHelperStubImpl();
        }
    }

    public void setScaleMode(int $i0) throws  {
        this.mImpl.setScaleMode($i0);
    }

    public int getScaleMode() throws  {
        return this.mImpl.getScaleMode();
    }

    public void setColorMode(int $i0) throws  {
        this.mImpl.setColorMode($i0);
    }

    public int getColorMode() throws  {
        return this.mImpl.getColorMode();
    }

    public void setOrientation(int $i0) throws  {
        this.mImpl.setOrientation($i0);
    }

    public int getOrientation() throws  {
        return this.mImpl.getOrientation();
    }

    public void printBitmap(String $r1, Bitmap $r2) throws  {
        this.mImpl.printBitmap($r1, $r2, null);
    }

    public void printBitmap(String $r1, Bitmap $r2, OnPrintFinishCallback $r3) throws  {
        this.mImpl.printBitmap($r1, $r2, $r3);
    }

    public void printBitmap(String $r1, Uri $r2) throws FileNotFoundException {
        this.mImpl.printBitmap($r1, $r2, null);
    }

    public void printBitmap(String $r1, Uri $r2, OnPrintFinishCallback $r3) throws FileNotFoundException {
        this.mImpl.printBitmap($r1, $r2, $r3);
    }
}
