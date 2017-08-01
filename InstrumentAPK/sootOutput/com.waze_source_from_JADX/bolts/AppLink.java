package bolts;

import android.net.Uri;
import dalvik.annotation.Signature;
import java.util.Collections;
import java.util.List;

public class AppLink {
    private Uri sourceUrl;
    private List<Target> targets;
    private Uri webUrl;

    public static class Target {
        private final String appName;
        private final String className;
        private final String packageName;
        private final Uri url;

        public Target(String $r1, String $r2, Uri $r3, String $r4) throws  {
            this.packageName = $r1;
            this.className = $r2;
            this.url = $r3;
            this.appName = $r4;
        }

        public Uri getUrl() throws  {
            return this.url;
        }

        public String getAppName() throws  {
            return this.appName;
        }

        public String getClassName() throws  {
            return this.className;
        }

        public String getPackageName() throws  {
            return this.packageName;
        }
    }

    public AppLink(@Signature({"(", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lbolts/AppLink$Target;", ">;", "Landroid/net/Uri;", ")V"}) Uri $r1, @Signature({"(", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lbolts/AppLink$Target;", ">;", "Landroid/net/Uri;", ")V"}) List<Target> $r3, @Signature({"(", "Landroid/net/Uri;", "Ljava/util/List", "<", "Lbolts/AppLink$Target;", ">;", "Landroid/net/Uri;", ")V"}) Uri $r2) throws  {
        List $r32;
        this.sourceUrl = $r1;
        if ($r3 == null) {
            $r32 = Collections.emptyList();
        }
        this.targets = $r32;
        this.webUrl = $r2;
    }

    public Uri getSourceUrl() throws  {
        return this.sourceUrl;
    }

    public List<Target> getTargets() throws  {
        return Collections.unmodifiableList(this.targets);
    }

    public Uri getWebUrl() throws  {
        return this.webUrl;
    }
}
