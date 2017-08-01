package com.waze.share;

import com.waze.ConfigItem;

public final class ShareConstants {
    public static final int CFG_ARRAY_FB_FEATURE_ENABLED_ID = 0;
    public static final int CFG_ARRAY_FB_USER_SHARE_PREFIX_ID = 3;
    public static final int CFG_ARRAY_FOURSQUARE_FEATURE_ENABLED_ID = 2;
    public static final int CFG_ARRAY_TWITTER_FEATURE_ENABLED_ID = 1;
    public static final ConfigItem CFG_ITEM_SHARE_FB_FEATURE_ENABLED = new ConfigItem(CFG_SHARE_FB_CATEGORY, "Feature enabled", "yes");
    public static final ConfigItem CFG_ITEM_SHARE_FB_USER_SHARE_PREFIX = new ConfigItem(CFG_SHARE_FB_CATEGORY, CFG_SHARE_FB_USER_SHARE_PREFIX, "");
    public static final ConfigItem CFG_ITEM_SHARE_FOURSQUARE_FEATURE_ENABLED = new ConfigItem(CFG_SHARE_FOURSQUARE_CATEGORY, CFG_SHARE_FOURSQUARE_FEATURE_ENABLED, "yes");
    public static final ConfigItem CFG_ITEM_SHARE_TWITTER_FEATURE_ENABLED = new ConfigItem(CFG_SHARE_TWITTER_CATEGORY, "Feature enabled", "yes");
    public static final String CFG_NO = "no";
    public static final String CFG_SHARE_FB_CATEGORY = "Facebook";
    public static final String CFG_SHARE_FB_FEATURE_ENABLED = "Feature enabled";
    public static final String CFG_SHARE_FB_USER_SHARE_PREFIX = "Sharing Url Prefix";
    public static final String CFG_SHARE_FOURSQUARE_CATEGORY = "Foursquare";
    public static final String CFG_SHARE_FOURSQUARE_FEATURE_ENABLED = "Feauture enabled";
    public static final String CFG_SHARE_TWITTER_CATEGORY = "Twitter";
    public static final String CFG_SHARE_TWITTER_FEATURE_ENABLED = "Feature enabled";
    public static final String CFG_YES = "yes";
    public static final int FRIENDS_ADD_FRIENDS = 1001;
    public static final String SHARE_EXTRA_ID_FRIENDS_LIST = "Friends list";
    public static final String SHARE_EXTRA_ID_LIKE_TITLE = "LikeTitle";
    public static final String SHARE_EXTRA_ID_LIKE_URL = "LikeUrl";
    public static final String SHARE_EXTRA_ID_LOCATION_ID = "Position id";
    public static final String SHARE_EXTRA_LI_CONNECT_TITLE = "LinkedInConnectTitle";
    public static final String SHARE_EXTRA_LI_PROFILE_TITLE = "LinkedInProfileTitle";
    public static final String SHARE_EXTRA_LI_PROFILE_URL = "LinkedInProfileUrl";
    public static final String SHARE_FB_LIKE_APP_URL_PREFIX = "fb://";
    public static final String SHARE_LINKEDIN_PACKAGE_NAME = "com.linkedin.android";
    public static final int SHARE_RC_FB_LOCATION_ACTIVITY_RESULT = 3;
    public static final int SHARE_RC_FB_WITH_ACTIVITY_CLOSED = 2;
    public static final int SHARE_RC_FB_WITH_ACTIVITY_RESULT = 1;
    public static final int SHARE_RQC_FB_LOCATION_ACTIVITY = 3;
    public static final int SHARE_RQC_FB_MAIN_ACTIVITY = 1;
    public static final int SHARE_RQC_FB_WITH_ACTIVITY = 2;
    public static final String SHARE_SOCIAL_NETS_ITEM_FB = "Post to facebook";
    public static final String SHARE_SOCIAL_NETS_ITEM_FSQ = "Check in on Foursquare";
    public static final String SHARE_SOCIAL_NETS_ITEM_TWITTER = "Tweet";
}
