package android.support.v4.content;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ProviderInfo;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParserException;

public class FileProvider extends ContentProvider {
    private static final String ATTR_NAME = "name";
    private static final String ATTR_PATH = "path";
    private static final String[] COLUMNS = new String[]{"_display_name", "_size"};
    private static final File DEVICE_ROOT = new File("/");
    private static final String META_DATA_FILE_PROVIDER_PATHS = "android.support.FILE_PROVIDER_PATHS";
    private static final String TAG_CACHE_PATH = "cache-path";
    private static final String TAG_EXTERNAL = "external-path";
    private static final String TAG_FILES_PATH = "files-path";
    private static final String TAG_ROOT_PATH = "root-path";
    private static HashMap<String, PathStrategy> sCache = new HashMap();
    private PathStrategy mStrategy;

    interface PathStrategy {
        File getFileForUri(Uri uri) throws ;

        Uri getUriForFile(File file) throws ;
    }

    static class SimplePathStrategy implements PathStrategy {
        private final String mAuthority;
        private final HashMap<String, File> mRoots = new HashMap();

        public SimplePathStrategy(String $r1) throws  {
            this.mAuthority = $r1;
        }

        public void addRoot(String $r1, File $r3) throws  {
            if (TextUtils.isEmpty($r1)) {
                throw new IllegalArgumentException("Name must not be empty");
            }
            try {
                $r3 = $r3.getCanonicalFile();
                this.mRoots.put($r1, $r3);
            } catch (IOException $r2) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + $r3, $r2);
            }
        }

        public Uri getUriForFile(File $r1) throws  {
            try {
                String $r10;
                String $r3 = $r1.getCanonicalPath();
                Entry $r4 = null;
                for (Entry $r9 : this.mRoots.entrySet()) {
                    $r10 = ((File) $r9.getValue()).getPath();
                    if ($r3.startsWith($r10) && ($r4 == null || $r10.length() > ((File) $r4.getValue()).getPath().length())) {
                        $r4 = $r9;
                    }
                }
                if ($r4 == null) {
                    throw new IllegalArgumentException("Failed to find configured root that contains " + $r3);
                }
                $r10 = ((File) $r4.getValue()).getPath();
                if ($r10.endsWith("/")) {
                    $r3 = $r3.substring($r10.length());
                } else {
                    $r3 = $r3.substring($r10.length() + 1);
                }
                return new Builder().scheme("content").authority(this.mAuthority).encodedPath(Uri.encode((String) $r4.getKey()) + '/' + Uri.encode($r3, "/")).build();
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + $r1);
            }
        }

        public File getFileForUri(Uri $r1) throws  {
            String $r3 = $r1.getEncodedPath();
            int $i0 = $r3.indexOf(47, 1);
            String $r4 = Uri.decode($r3.substring(1, $i0));
            $r3 = Uri.decode($r3.substring($i0 + 1));
            File $r7 = (File) this.mRoots.get($r4);
            if ($r7 == null) {
                throw new IllegalArgumentException("Unable to find configured root for " + $r1);
            }
            File $r10 = new File($r7, $r3);
            try {
                $r10 = $r10.getCanonicalFile();
                if ($r10.getPath().startsWith($r7.getPath())) {
                    return $r10;
                }
                throw new SecurityException("Resolved path jumped beyond configured root");
            } catch (IOException e) {
                throw new IllegalArgumentException("Failed to resolve canonical path for " + $r10);
            }
        }
    }

    public boolean onCreate() throws  {
        return true;
    }

    public void attachInfo(Context $r1, ProviderInfo $r2) throws  {
        super.attachInfo($r1, $r2);
        if ($r2.exported) {
            throw new SecurityException("Provider must not be exported");
        } else if ($r2.grantUriPermissions) {
            this.mStrategy = getPathStrategy($r1, $r2.authority);
        } else {
            throw new SecurityException("Provider must grant uri permissions");
        }
    }

    public static Uri getUriForFile(Context $r0, String $r1, File $r2) throws  {
        return getPathStrategy($r0, $r1).getUriForFile($r2);
    }

    public Cursor query(Uri $r1, String[] $r6, String selection, String[] selectionArgs, String sortOrder) throws  {
        File $r8 = this.mStrategy.getFileForUri($r1);
        if ($r6 == null) {
            $r6 = COLUMNS;
        }
        String[] $r9 = new String[$r6.length];
        Object[] $r10 = new Object[$r6.length];
        selectionArgs = $r6;
        int $i0 = $r6.length;
        int $i1 = 0;
        int $i2 = 0;
        while ($i1 < $i0) {
            int $i3;
            selection = selectionArgs[$i1];
            if ("_display_name".equals(selection)) {
                $r9[$i2] = "_display_name";
                $i3 = $i2 + 1;
                $r10[$i2] = $r8.getName();
            } else if ("_size".equals(selection)) {
                $r9[$i2] = "_size";
                $i3 = $i2 + 1;
                $r10[$i2] = Long.valueOf($r8.length());
            } else {
                $i3 = $i2;
            }
            $i1++;
            $i2 = $i3;
        }
        $r6 = copyOf($r9, $i2);
        $r10 = copyOf($r10, $i2);
        MatrixCursor $r5 = new MatrixCursor($r6, 1);
        $r5.addRow($r10);
        return $r5;
    }

    public String getType(Uri $r1) throws  {
        File $r3 = this.mStrategy.getFileForUri($r1);
        int $i0 = $r3.getName().lastIndexOf(46);
        if ($i0 >= 0) {
            String $r4 = MimeTypeMap.getSingleton().getMimeTypeFromExtension($r3.getName().substring($i0 + 1));
            if ($r4 != null) {
                return $r4;
            }
        }
        return "application/octet-stream";
    }

    public Uri insert(Uri uri, ContentValues values) throws  {
        throw new UnsupportedOperationException("No external inserts");
    }

    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) throws  {
        throw new UnsupportedOperationException("No external updates");
    }

    public int delete(Uri $r1, String selection, String[] selectionArgs) throws  {
        return this.mStrategy.getFileForUri($r1).delete() ? 1 : 0;
    }

    public ParcelFileDescriptor openFile(Uri $r1, String $r2) throws FileNotFoundException {
        return ParcelFileDescriptor.open(this.mStrategy.getFileForUri($r1), modeToMode($r2));
    }

    private static PathStrategy getPathStrategy(Context $r0, String $r1) throws  {
        PathStrategy $r5;
        synchronized (sCache) {
            $r5 = (PathStrategy) sCache.get($r1);
            if ($r5 == null) {
                try {
                    PathStrategy $r6 = parsePathStrategy($r0, $r1);
                    $r5 = $r6;
                    sCache.put($r1, $r6);
                } catch (IOException $r7) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", $r7);
                } catch (XmlPullParserException $r10) {
                    throw new IllegalArgumentException("Failed to parse android.support.FILE_PROVIDER_PATHS meta-data", $r10);
                }
            }
        }
        return $r5;
    }

    private static PathStrategy parsePathStrategy(Context $r0, String $r1) throws IOException, XmlPullParserException {
        SimplePathStrategy $r2 = new SimplePathStrategy($r1);
        XmlResourceParser $r5 = $r0.getPackageManager().resolveContentProvider($r1, 128).loadXmlMetaData($r0.getPackageManager(), META_DATA_FILE_PROVIDER_PATHS);
        if ($r5 == null) {
            throw new IllegalArgumentException("Missing android.support.FILE_PROVIDER_PATHS meta-data");
        }
        while (true) {
            int $i0 = $r5.next();
            if ($i0 == 1) {
                return $r2;
            }
            if ($i0 == 2) {
                $r1 = $r5.getName();
                String $r7 = $r5.getAttributeValue(null, "name");
                String $r8 = $r5.getAttributeValue(null, ATTR_PATH);
                File $r9 = null;
                if (TAG_ROOT_PATH.equals($r1)) {
                    $r9 = buildPath(DEVICE_ROOT, $r8);
                } else if (TAG_FILES_PATH.equals($r1)) {
                    $r9 = buildPath($r0.getFilesDir(), $r8);
                } else if (TAG_CACHE_PATH.equals($r1)) {
                    $r9 = buildPath($r0.getCacheDir(), $r8);
                } else if (TAG_EXTERNAL.equals($r1)) {
                    $r9 = buildPath(Environment.getExternalStorageDirectory(), $r8);
                }
                if ($r9 != null) {
                    $r2.addRoot($r7, $r9);
                }
            }
        }
    }

    private static int modeToMode(String $r0) throws  {
        if ("r".equals($r0)) {
            return 268435456;
        }
        if ("w".equals($r0) || "wt".equals($r0)) {
            return 738197504;
        }
        if ("wa".equals($r0)) {
            return 704643072;
        }
        if ("rw".equals($r0)) {
            return 939524096;
        }
        if ("rwt".equals($r0)) {
            return 1006632960;
        }
        throw new IllegalArgumentException("Invalid mode: " + $r0);
    }

    private static File buildPath(File $r0, String... $r1) throws  {
        int $i0 = $r1.length;
        int $i1 = 0;
        while ($i1 < $i0) {
            File $r3;
            String $r2 = $r1[$i1];
            if ($r2 != null) {
                $r3 = new File($r0, $r2);
            } else {
                $r3 = $r0;
            }
            $i1++;
            $r0 = $r3;
        }
        return $r0;
    }

    private static String[] copyOf(String[] $r0, int $i0) throws  {
        String[] $r1 = new String[$i0];
        System.arraycopy($r0, 0, $r1, 0, $i0);
        return $r1;
    }

    private static Object[] copyOf(Object[] $r0, int $i0) throws  {
        Object[] $r1 = new Object[$i0];
        System.arraycopy($r0, 0, $r1, 0, $i0);
        return $r1;
    }
}
