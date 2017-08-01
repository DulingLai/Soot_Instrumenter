package bolts;

import android.net.Uri;
import dalvik.annotation.Signature;

public interface AppLinkResolver {
    Task<AppLink> getAppLinkFromUrlInBackground(@Signature({"(", "Landroid/net/Uri;", ")", "Lbolts/Task", "<", "Lbolts/AppLink;", ">;"}) Uri uri) throws ;
}
