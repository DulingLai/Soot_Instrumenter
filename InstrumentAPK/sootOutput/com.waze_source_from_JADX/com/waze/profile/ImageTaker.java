package com.waze.profile;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore.Images.Media;
import android.support.v4.content.ContextCompat;
import com.abaltatech.mcp.mcs.fileupload.FileUploadSession;
import com.waze.RequestPermissionActivity;
import com.waze.ResManager;
import com.waze.strings.DisplayStrings;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;

public class ImageTaker {
    static final String IMAGE_UNSPECIFIED = "image/*";
    public static final int REQUEST_CODE_CROP_PHOTO = 223;
    public static final int REQUEST_CODE_TAKE_FROM_CAMERA = 222;
    private final Activity mActivity;
    private int mAspectX = 1;
    private int mAspectY = 1;
    private String mCaptureFileFullPath = null;
    private String mCroppedFileFullPath = null;
    private final String mFileName;
    Bitmap mFinalBitmap = null;
    private int mOutX = 512;
    private int mOutY = 512;

    class C23592 implements FilenameFilter {
        C23592() {
        }

        public boolean accept(File dir, String filename) {
            return filename.startsWith(ImageTaker.this.getCaptureFileNameWC());
        }
    }

    public ImageTaker(Activity a, String fileName) {
        this.mActivity = a;
        this.mFileName = fileName;
    }

    private String getCaptureFileName() {
        return this.mFileName + "Capture.tmp";
    }

    private String getCaptureFileNameWC() {
        return this.mFileName + "Capture";
    }

    private String getImageFileName() {
        return this.mFileName + "Final";
    }

    public void sendIntent() {
        final BottomSheet bs = new BottomSheet(this.mActivity, DisplayStrings.DS_IMAGE_TAKER_TITLE, Mode.COLUMN_TEXT);
        bs.setAdapter(new Adapter() {
            public int getCount() {
                return 2;
            }

            public void onConfigItem(int position, ItemDetails item) {
                if (position == 0) {
                    item.setItem((int) DisplayStrings.DS_IMAGE_TAKER_TAKE_NEW);
                } else {
                    item.setItem((int) DisplayStrings.DS_IMAGE_TAKER_CHOOSE);
                }
            }

            public void onClick(int position) {
                Intent intent;
                boolean needCameraPerm = false;
                if (position == 0) {
                    intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    File imageFile = new File(Environment.getExternalStorageDirectory().getPath(), ImageTaker.this.getCaptureFileName());
                    imageFile.deleteOnExit();
                    intent.putExtra("output", Uri.fromFile(imageFile));
                    needCameraPerm = ContextCompat.checkSelfPermission(ImageTaker.this.mActivity, "android.permission.CAMERA") != 0;
                } else {
                    intent = new Intent("android.intent.action.PICK", Media.EXTERNAL_CONTENT_URI);
                }
                boolean needStoragePerm = ContextCompat.checkSelfPermission(ImageTaker.this.mActivity, "android.permission.READ_EXTERNAL_STORAGE") != 0;
                if (needCameraPerm || needStoragePerm) {
                    int i;
                    Intent permissionsIntent = new Intent(ImageTaker.this.mActivity, RequestPermissionActivity.class);
                    int size = (needCameraPerm && needStoragePerm) ? 2 : 1;
                    String[] perms = new String[size];
                    if (needCameraPerm) {
                        i = 0 + 1;
                        perms[0] = "android.permission.CAMERA";
                    } else {
                        i = 0;
                    }
                    int i2;
                    if (needStoragePerm) {
                        i2 = i + 1;
                        perms[i] = "android.permission.READ_EXTERNAL_STORAGE";
                    } else {
                        i2 = i;
                    }
                    permissionsIntent.putExtra(RequestPermissionActivity.INT_NEEDED_PERMISSIONS, perms);
                    permissionsIntent.putExtra(RequestPermissionActivity.INT_ON_GRANTED, intent);
                    ImageTaker.this.mActivity.startActivityForResult(permissionsIntent, 222);
                } else {
                    ImageTaker.this.mActivity.startActivityForResult(intent, 222);
                }
                bs.dismiss();
            }
        });
        bs.show();
    }

    public void setOutputResolution(int x, int y, int aspectX, int aspectY) {
        this.mOutX = x;
        this.mOutY = y;
        this.mAspectX = aspectX;
        this.mAspectY = aspectY;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        File imageFile;
        if (requestCode == 222 && resultCode == -1) {
            if (!(data == null || data.getData() == null)) {
                Uri uri = data.getData();
                String imageFilePath = null;
                ContentResolver res = this.mActivity.getContentResolver();
                try {
                    Cursor cursor = res.query(uri, new String[]{"_data"}, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        imageFilePath = cursor.getString(0);
                        cursor.close();
                    }
                } catch (Exception e) {
                }
                if (imageFilePath == null || imageFilePath.length() <= 0) {
                    Bitmap bmp = decodeSampledBitmapFromResource(res, uri, DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO, DisplayStrings.DS_NAVIGATE_TO_S_DRIVE_TO);
                    if (bmp != null) {
                        this.mCaptureFileFullPath = saveToFile(bmp, getCaptureFileName());
                    }
                } else {
                    Uri tryUri = Uri.parse(imageFilePath);
                    if (tryUri == null || tryUri.getScheme() == null || !(tryUri.getScheme().equals("http") || tryUri.getScheme().equals("https"))) {
                        this.mCaptureFileFullPath = imageFilePath;
                    } else {
                        cropImage(tryUri);
                        return;
                    }
                }
            }
            if (this.mCaptureFileFullPath == null) {
                imageFile = new File(Environment.getExternalStorageDirectory().getPath(), getCaptureFileName());
                if (imageFile.exists()) {
                    this.mCaptureFileFullPath = imageFile.getAbsolutePath();
                }
            }
            if (this.mCaptureFileFullPath == null && data != null) {
                if (data.hasExtra("data")) {
                    this.mCaptureFileFullPath = saveToFile((Bitmap) data.getExtras().get("data"), getCaptureFileName());
                }
            }
            if (this.mCaptureFileFullPath != null) {
                File imgFile = new File(this.mCaptureFileFullPath);
                if (imgFile.exists()) {
                    this.mCaptureFileFullPath = imgFile.getAbsolutePath();
                    cropImage(Uri.fromFile(imgFile));
                }
            }
        } else if (requestCode == 223) {
            if (data != null) {
                if (data.hasExtra("data")) {
                    this.mFinalBitmap = (Bitmap) data.getExtras().get("data");
                    if (this.mFinalBitmap != null) {
                        this.mCroppedFileFullPath = saveToFile(this.mFinalBitmap, getImageFileName());
                    }
                }
            }
            if (this.mCroppedFileFullPath == null) {
                imageFile = new File(Environment.getExternalStorageDirectory().getPath(), getImageFileName());
                if (imageFile.exists()) {
                    this.mFinalBitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
                    if (this.mFinalBitmap != null) {
                        this.mCroppedFileFullPath = imageFile.getAbsolutePath();
                    }
                }
            }
            if (this.mCroppedFileFullPath == null && this.mCaptureFileFullPath != null) {
                fallbackCrop(this.mCaptureFileFullPath, new File(ResManager.mAppDir, getImageFileName()));
            }
            for (File f : new File(ResManager.mAppDir, FileUploadSession.SEPARATOR).listFiles(new C23592())) {
                f.delete();
            }
        }
    }

    public boolean hasImage() {
        return this.mFinalBitmap != null;
    }

    public String getImagePath() {
        return this.mCroppedFileFullPath;
    }

    public Bitmap getImage() {
        return this.mFinalBitmap;
    }

    private String saveToFile(Bitmap bitmap, String fileName) {
        try {
            File imageFile = new File(ResManager.mAppDir, fileName);
            if (imageFile.exists()) {
                imageFile.delete();
            }
            FileOutputStream fos = new FileOutputStream(imageFile);
            bitmap.compress(CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
            imageFile.deleteOnExit();
            return imageFile.getAbsolutePath();
        } catch (IOException e) {
            return null;
        }
    }

    public static Bitmap decodeSampledBitmapFromResource(ContentResolver res, Uri uri, int reqWidth, int reqHeight) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(res.openInputStream(uri), null, options);
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
            options.inJustDecodeBounds = false;
            try {
                return BitmapFactory.decodeStream(res.openInputStream(uri), null, options);
            } catch (FileNotFoundException e) {
                return null;
            }
        } catch (Exception e2) {
            return null;
        }
    }

    public static int calculateInSampleSize(Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int halfHeight = height / 2;
            int halfWidth = width / 2;
            while (halfHeight / inSampleSize > reqHeight && halfWidth / inSampleSize > reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    private void cropImage(Uri imageCaptureUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageCaptureUri, IMAGE_UNSPECIFIED);
        File imageFile = new File(Environment.getExternalStorageDirectory().getPath(), getImageFileName());
        intent.putExtra("output", Uri.fromFile(imageFile));
        intent.putExtra("outputX", this.mOutX);
        intent.putExtra("outputY", this.mOutY);
        intent.putExtra("aspectX", this.mAspectX);
        intent.putExtra("aspectY", this.mAspectY);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false);
        try {
            this.mActivity.startActivityForResult(intent, 223);
        } catch (ActivityNotFoundException e) {
            fallbackCrop(this.mCaptureFileFullPath, imageFile);
        }
    }

    private void fallbackCrop(String capturedPath, File cropped) {
        Options options = new Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(capturedPath, options);
        int mWidth = options.outWidth;
        int mHeight = options.outHeight;
        int sampleSize = 1;
        if (mHeight > 1024 || mWidth > 1024) {
            int halfHeight = mHeight / 2;
            int halfWidth = mWidth / 2;
            while (halfHeight / sampleSize > 1024 && halfWidth / sampleSize > 1024) {
                sampleSize *= 2;
            }
        }
        options.inJustDecodeBounds = false;
        options.inSampleSize = sampleSize;
        Bitmap mCapturedBitmap = BitmapFactory.decodeFile(capturedPath, options);
        mWidth = mCapturedBitmap.getWidth();
        mHeight = mCapturedBitmap.getHeight();
        if (this.mAspectY * mWidth > this.mAspectX * mHeight) {
            mWidth = (this.mAspectY * mHeight) / this.mAspectX;
        }
        if (this.mAspectX * mHeight > this.mAspectY * mWidth) {
            mHeight = (this.mAspectX * mWidth) / this.mAspectY;
        }
        this.mFinalBitmap = Bitmap.createScaledBitmap(Bitmap.createBitmap(mCapturedBitmap, (mCapturedBitmap.getWidth() - mWidth) / 2, (mCapturedBitmap.getHeight() - mHeight) / 2, mWidth, mHeight), this.mOutX, this.mOutY, true);
        this.mCroppedFileFullPath = saveToFile(this.mFinalBitmap, getImageFileName());
    }
}
