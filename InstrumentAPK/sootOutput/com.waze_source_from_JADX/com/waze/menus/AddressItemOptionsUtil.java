package com.waze.menus;

import android.content.Context;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolUtils;
import com.waze.navigate.AddressItem;
import com.waze.navigate.NavigateNativeManager;
import com.waze.reports.SimpleBottomSheet;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetListener;
import com.waze.reports.SimpleBottomSheet.SimpleBottomSheetValue;
import com.waze.strings.DisplayStrings;

public class AddressItemOptionsUtil {
    private static final int[] ADD_FAVORITE_ITEM = new int[]{0, DisplayStrings.DS_NAVLIST_OPTIONS_ADD_FAVORITE, C1283R.drawable.navlist_add_favorite};
    private static final String[] ANALYTICS_EVENT_NAMES = new String[]{"ADD_FAVORITE", "REMOVE_FAVORITE", AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_CHANGE_LOCATION, "PARKING", "PLAN_DRIVE", AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_CALENDAR_SETTINGS, "INFO", "SEND_LOCATION", AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_ALTERNATIVE_ROUTES, AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_EDIT_HOME, AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_EDIT_WORK, AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_SET_LOCATION, "DELETE", AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_SET_AS_START_POINT, AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_RENAME_FAVORITE, "RIDE_DETAILS", "CANCEL_RIDE", AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ACTION_SEND_MESSAGE};
    private static final int[] CALENDAR_SETTINGS_ITEM = new int[]{5, DisplayStrings.DS_NAVLIST_OPTIONS_CALENDAR_SETTINGS, C1283R.drawable.navlist_calender_settings};
    private static final int[] CANCEL_RIDE_ITEM = new int[]{16, DisplayStrings.DS_NAVLIST_OPTIONS_CANCEL_RIDE_REQUEST, C1283R.drawable.carpool_options_cancel_ride};
    private static final Items CARPOOL_ITEMS = new Items("CARPOOL");
    private static final int[][] CARPOOL_ITEMS_CONFIG = new int[][]{RIDE_DETAILS_ITEM, SEND_LOCATION_ITEM, ROUTES_ITEM, SEND_MESSAGE_ITEM, CANCEL_RIDE_ITEM};
    private static final int[][] CARPOOL_ITEMS_PICKEDUP_CONFIG = new int[][]{RIDE_DETAILS_ITEM, SEND_LOCATION_ITEM, ROUTES_ITEM, SEND_MESSAGE_ITEM};
    private static final Items CARPOOL_ITEMS_PICKED_UP = new Items("CARPOOL");
    private static final int[] CHANGE_LOCATION_ITEM = new int[]{2, DisplayStrings.DS_NAVLIST_OPTIONS_CHANGE_LOCATION, C1283R.drawable.navlist_change_location};
    private static final int CONFIG_INDEX_ICON = 2;
    private static final int CONFIG_INDEX_ID = 0;
    private static final int CONFIG_INDEX_TITLE = 1;
    private static final int[] DELETE_ITEM = new int[]{12, DisplayStrings.DS_NAVLIST_OPTIONS_DELETE, C1283R.drawable.navlist_delete};
    private static final int[] EDIT_HOME_ITEM = new int[]{9, DisplayStrings.DS_NAVLIST_OPTIONS_EDIT_HOME, C1283R.drawable.navlist_edit_location};
    private static final int[] EDIT_PLANNED_DRIVE = new int[]{18, DisplayStrings.DS_NAVLIST_OPTIONS_EDIT_PLANNED_DRIVE, C1283R.drawable.navlist_edit_time};
    private static final int[] EDIT_WORK_ITEM = new int[]{10, DisplayStrings.DS_NAVLIST_OPTIONS_EDIT_WORK, C1283R.drawable.navlist_edit_location};
    private static final int[][] FAVORITES_ITEMS_CONFIG = new int[][]{SEND_LOCATION_ITEM, ROUTES_ITEM, INFO_ITEM, PARKING_ITEM, PLAN_DRIVE_ITEM, RENAME_FAVORITE_ITEM, REMOVE_FAVORITE_ITEM, DELETE_ITEM};
    private static final Items FAVORITE_ITEMS = new Items("FAVORITE");
    private static final Items FAVORITE_SCREEN_ITEMS = new Items("FAVORITE_SCREEN");
    private static final int[][] FAVORITE_SCREEN_ITEMS_CONFIG = new int[][]{SEND_LOCATION_ITEM, ROUTES_ITEM, INFO_ITEM, PARKING_ITEM, PLAN_DRIVE_ITEM, RENAME_FAVORITE_ITEM, DELETE_ITEM};
    private static final Items HISTORY_ITEMS = new Items("HISTORY");
    private static final int[][] HISTORY_ITEMS_CONFIG = new int[][]{SEND_LOCATION_ITEM, ADD_FAVORITE_ITEM, INFO_ITEM, PARKING_ITEM, ROUTES_ITEM, PLAN_DRIVE_ITEM, DELETE_ITEM};
    private static final Items HOME_ITEMS = new Items("HOME");
    private static final int[][] HOME_ITEMS_CONFIG = new int[][]{SEND_LOCATION_ITEM, EDIT_HOME_ITEM, INFO_ITEM, PARKING_ITEM, ROUTES_ITEM, PLAN_DRIVE_ITEM, DELETE_ITEM};
    private static final int[] INFO_ITEM = new int[]{6, DisplayStrings.DS_NAVLIST_OPTIONS_INFO, C1283R.drawable.navlist_info};
    public static final int OPTION_ADD_FAVORITE = 0;
    public static final int OPTION_CALENDAR_SETTINGS = 5;
    public static final int OPTION_CANCEL_RIDE = 16;
    public static final int OPTION_CHANGE_LOCATION = 2;
    public static final int OPTION_DELETE = 12;
    public static final int OPTION_EDIT_HOME = 9;
    public static final int OPTION_EDIT_PLANNED_DRIVE = 18;
    public static final int OPTION_EDIT_WORK = 10;
    public static final int OPTION_INFO = 6;
    public static final int OPTION_PARKING = 3;
    public static final int OPTION_PLAN_DRIVE = 4;
    public static final int OPTION_REMOVE_FAVORITE = 1;
    public static final int OPTION_RENAME_FAVORITE = 14;
    public static final int OPTION_RIDE_DETAILS = 15;
    public static final int OPTION_ROUTES = 8;
    public static final int OPTION_SEND_LOCATION = 7;
    public static final int OPTION_SEND_MESSAGE = 17;
    public static final int OPTION_SET_LOCATION = 11;
    public static final int OPTION_SET_START_POINT = 13;
    private static final int[] PARKING_ITEM = new int[]{3, DisplayStrings.DS_NAVLIST_OPTIONS_PARKING, C1283R.drawable.navlist_parking};
    private static final Items PLANNED_VERIFIED_EVENTS = new Items(AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ITEM_TYPE_PLANNED_VERIFIED_EVENT);
    private static final int[][] PLANNED_VERIFIED_ITEMS_CONFIG = new int[][]{EDIT_PLANNED_DRIVE, INFO_ITEM, PARKING_ITEM, CALENDAR_SETTINGS_ITEM, DELETE_ITEM};
    private static final int[] PLAN_DRIVE_ITEM = new int[]{4, DisplayStrings.DS_NAVLIST_OPTIONS_PLAN_DRIVE, C1283R.drawable.navlist_plan_a_drive};
    private static final int[] REMOVE_FAVORITE_ITEM = new int[]{1, DisplayStrings.DS_NAVLIST_OPTIONS_REMOVE_FAVORITE, C1283R.drawable.navlist_remove_favorite};
    private static final int[] RENAME_FAVORITE_ITEM = new int[]{14, DisplayStrings.DS_NAVLIST_OPTIONS_RENAME_FAVORITE, C1283R.drawable.navlist_edit_location};
    private static final int[] RIDE_DETAILS_ITEM = new int[]{15, DisplayStrings.DS_NAVLIST_OPTIONS_RIDE_DETAILS, C1283R.drawable.navlist_info};
    private static final int[] ROUTES_ITEM = new int[]{8, DisplayStrings.DS_NAVLIST_OPTIONS_ROUTES, C1283R.drawable.navlist_routes};
    private static final int[] SEND_LOCATION_ITEM = new int[]{7, DisplayStrings.DS_NAVLIST_OPTIONS_SEND_LOCATION, C1283R.drawable.navlist_share_location};
    private static final int[] SEND_MESSAGE_ITEM = new int[]{17, DisplayStrings.DS_NAVLIST_OPTIONS_SEND_MESSAGE, C1283R.drawable.list_icon_message};
    private static final int[] SET_LOCATION_ITEM = new int[]{11, DisplayStrings.DS_NAVLIST_OPTIONS_SET_LOCATION, C1283R.drawable.navlist_set_location};
    private static final Items UNVERIFIED_EVENTS = new Items("UNVERIFIED_EVENT");
    private static final int[][] UNVERIFIED_ITEMS_CONFIG = new int[][]{SET_LOCATION_ITEM, INFO_ITEM, CALENDAR_SETTINGS_ITEM, DELETE_ITEM};
    private static final Items VERIFIED_EVENTS = new Items(AnalyticsEvents.ANALYTICS_EVENT_NAV_LIST_ITEM_TYPE_VERIFIED_EVENT);
    private static final int[][] VERIFIED_ITEMS_CONFIG = new int[][]{CHANGE_LOCATION_ITEM, INFO_ITEM, ROUTES_ITEM, PARKING_ITEM, CALENDAR_SETTINGS_ITEM, DELETE_ITEM};
    private static final Items WORK_ITEMS = new Items("WORK");
    private static final int[][] WORK_ITEMS_CONFIG = new int[][]{SEND_LOCATION_ITEM, EDIT_WORK_ITEM, INFO_ITEM, PARKING_ITEM, ROUTES_ITEM, PLAN_DRIVE_ITEM, DELETE_ITEM};

    static class C18711 implements IResultObj<CarpoolDrive> {
        final /* synthetic */ AddressItem val$addressItem;
        final /* synthetic */ Context val$context;
        final /* synthetic */ AddressItemOptionListener val$listener;

        C18711(Context $r1, AddressItem $r2, AddressItemOptionListener $r3) throws  {
            this.val$context = $r1;
            this.val$addressItem = $r2;
            this.val$listener = $r3;
        }

        public void onResult(CarpoolDrive $r1) throws  {
            if ($r1 == null || $r1.getRide() == null) {
                Logger.m38e("AddressItem open bottom sheet: Could not find ride!");
                return;
            }
            Items $r5;
            if (CarpoolUtils.canCancelRide($r1.getRide().getState())) {
                $r5 = AddressItemOptionsUtil.CARPOOL_ITEMS;
                AddressItemOptionsUtil.setupItems(AddressItemOptionsUtil.CARPOOL_ITEMS_CONFIG, AddressItemOptionsUtil.CARPOOL_ITEMS);
            } else {
                $r5 = AddressItemOptionsUtil.CARPOOL_ITEMS_PICKED_UP;
                AddressItemOptionsUtil.setupItems(AddressItemOptionsUtil.CARPOOL_ITEMS_PICKEDUP_CONFIG, AddressItemOptionsUtil.CARPOOL_ITEMS_PICKED_UP);
            }
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ICON_IN_NAV_LIST_TAPPED, "TYPE", AddressItemOptionsUtil.CARPOOL_ITEMS.itemsTitle);
            AddressItemOptionsUtil.showBottomSheet(this.val$context, this.val$addressItem, $r5, this.val$listener);
        }
    }

    public interface AddressItemOptionListener {
        void onAddressItemOptionClicked(AddressItem addressItem, int i) throws ;
    }

    private static class Items {
        public int[] ICONS;
        public int[] IDS;
        public String[] LABELS;
        public String itemsTitle;

        public Items(String $r1) throws  {
            this.itemsTitle = $r1;
        }
    }

    public static void showNavItemOptions(android.content.Context r12, com.waze.navigate.AddressItem r13, com.waze.menus.AddressItemOptionsUtil.AddressItemOptionListener r14) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:31:0x0088 in {2, 6, 9, 19, 20, 24, 26, 29, 30, 32, 34, 37, 40, 44, 58, 66, 67} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r0 = 0;
        r1 = 0;
        r2 = r13.getType();
        r3 = 3;
        if (r2 != r3) goto L_0x0021;
    L_0x0009:
        r4 = WORK_ITEMS_CONFIG;
        r0 = WORK_ITEMS;
        setupItems(r4, r0);
        r0 = WORK_ITEMS;
    L_0x0012:
        if (r0 == 0) goto L_0x0108;
    L_0x0014:
        if (r1 == 0) goto L_0x0105;
    L_0x0016:
        r5 = "NAV_LIST_OPTIONS_SHOWN";
        r6 = "TYPE";
        com.waze.analytics.Analytics.log(r5, r6, r1);
        showBottomSheet(r12, r13, r0, r14);
        return;
    L_0x0021:
        r2 = r13.getType();
        r3 = 1;
        if (r2 != r3) goto L_0x0032;
    L_0x0028:
        r4 = HOME_ITEMS_CONFIG;
        r0 = HOME_ITEMS;
        setupItems(r4, r0);
        r0 = HOME_ITEMS;
        goto L_0x0012;
    L_0x0032:
        r2 = r13.getType();
        r3 = 11;
        if (r2 == r3) goto L_0x0042;
    L_0x003a:
        r2 = r13.getType();
        r3 = 9;
        if (r2 != r3) goto L_0x00ba;
    L_0x0042:
        r7 = r13.getCategory();
        r2 = r7.intValue();
        r3 = 9;
        if (r2 != r3) goto L_0x0062;
    L_0x004e:
        r8 = r13.hasLocation();
        if (r8 == 0) goto L_0x0062;
    L_0x0054:
        r4 = PLANNED_VERIFIED_ITEMS_CONFIG;
        goto L_0x005a;
    L_0x0057:
        goto L_0x0012;
    L_0x005a:
        r0 = PLANNED_VERIFIED_EVENTS;
        setupItems(r4, r0);
        r0 = PLANNED_VERIFIED_EVENTS;
        goto L_0x0012;
    L_0x0062:
        r9 = r13.getIsValidate();
        r8 = r9.booleanValue();
        if (r8 == 0) goto L_0x009b;
    L_0x006c:
        r4 = VERIFIED_ITEMS_CONFIG;
        goto L_0x0072;
    L_0x006f:
        goto L_0x0012;
    L_0x0072:
        r0 = VERIFIED_EVENTS;
        setupItems(r4, r0);
        goto L_0x007b;
    L_0x0078:
        goto L_0x0012;
    L_0x007b:
        r2 = r13.getType();
        r3 = 11;
        if (r2 != r3) goto L_0x008c;
    L_0x0083:
        r1 = "CALENDAR_VERIFIED";
    L_0x0085:
        r0 = VERIFIED_EVENTS;
        goto L_0x0012;
        goto L_0x008c;
    L_0x0089:
        goto L_0x0012;
    L_0x008c:
        r2 = r13.getType();
        goto L_0x0094;
    L_0x0091:
        goto L_0x0016;
    L_0x0094:
        r3 = 9;
        if (r2 != r3) goto L_0x0085;
    L_0x0098:
        r1 = "FB_VERIFIED";
        goto L_0x0085;
    L_0x009b:
        r4 = UNVERIFIED_ITEMS_CONFIG;
        r0 = UNVERIFIED_EVENTS;
        setupItems(r4, r0);
        r2 = r13.getType();
        r3 = 11;
        if (r2 != r3) goto L_0x00af;
    L_0x00aa:
        r1 = "CALENDAR_UNVERIFIED";
    L_0x00ac:
        r0 = UNVERIFIED_EVENTS;
        goto L_0x0057;
    L_0x00af:
        r2 = r13.getType();
        r3 = 9;
        if (r2 != r3) goto L_0x00ac;
    L_0x00b7:
        r1 = "FB_UNVERIFIED";
        goto L_0x00ac;
    L_0x00ba:
        r2 = r13.getType();
        r3 = 5;
        if (r2 == r3) goto L_0x00cc;
    L_0x00c1:
        r7 = r13.getCategory();
        r2 = r7.intValue();
        r3 = 1;
        if (r2 != r3) goto L_0x00e4;
    L_0x00cc:
        r8 = r12 instanceof com.waze.navigate.FavoritesActivity;
        if (r8 == 0) goto L_0x00da;
    L_0x00d0:
        r4 = FAVORITE_SCREEN_ITEMS_CONFIG;
        r0 = FAVORITE_SCREEN_ITEMS;
        setupItems(r4, r0);
        r0 = FAVORITE_SCREEN_ITEMS;
        goto L_0x006f;
    L_0x00da:
        r4 = FAVORITES_ITEMS_CONFIG;
        r0 = FAVORITE_ITEMS;
        setupItems(r4, r0);
        r0 = FAVORITE_ITEMS;
        goto L_0x0078;
    L_0x00e4:
        r2 = r13.getType();
        r3 = 8;
        if (r2 == r3) goto L_0x00fb;
    L_0x00ec:
        r2 = r13.getType();
        r3 = 7;
        if (r2 == r3) goto L_0x00fb;
    L_0x00f3:
        r2 = r13.getType();
        r3 = 13;
        if (r2 != r3) goto L_0x0012;
    L_0x00fb:
        r4 = HISTORY_ITEMS_CONFIG;
        r0 = HISTORY_ITEMS;
        setupItems(r4, r0);
        r0 = HISTORY_ITEMS;
        goto L_0x0089;
    L_0x0105:
        r1 = r0.itemsTitle;
        goto L_0x0091;
    L_0x0108:
        r2 = r13.getType();
        r3 = 14;
        if (r2 == r3) goto L_0x0118;
    L_0x0110:
        r2 = r13.getType();
        r3 = 15;
        if (r2 != r3) goto L_0x0125;
    L_0x0118:
        r10 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r11 = new com.waze.menus.AddressItemOptionsUtil$1;
        r11.<init>(r12, r13, r14);
        r10.getUpcomingDrive(r11);
        return;
    L_0x0125:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.AddressItemOptionsUtil.showNavItemOptions(android.content.Context, com.waze.navigate.AddressItem, com.waze.menus.AddressItemOptionsUtil$AddressItemOptionListener):void");
    }

    private static void showBottomSheet(Context $r0, AddressItem $r1, Items $r2, AddressItemOptionListener $r3) throws  {
        SimpleBottomSheetValue[] $r5 = new SimpleBottomSheetValue[$r2.IDS.length];
        for (int $i0 = 0; $i0 < $r2.IDS.length; $i0++) {
            $r5[$i0] = new SimpleBottomSheetValue($r2.IDS[$i0], $r2.LABELS[$i0], $r0.getResources().getDrawable($r2.ICONS[$i0]));
        }
        final AddressItemOptionListener addressItemOptionListener = $r3;
        final AddressItem addressItem = $r1;
        SimpleBottomSheet c18733 = new SimpleBottomSheet($r0, DisplayStrings.DS_NAVLIST_OPTIONS_INFO, $r5, new SimpleBottomSheetListener() {
            public void onComplete(SimpleBottomSheetValue $r1) throws  {
                if ($r1.id >= 0 && $r1.id < AddressItemOptionsUtil.ANALYTICS_EVENT_NAMES.length) {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_ACTION_IN_NAV_MENU_TAPPED, "ACTION", AddressItemOptionsUtil.ANALYTICS_EVENT_NAMES[$r1.id]);
                }
                if (addressItemOptionListener != null) {
                    addressItemOptionListener.onAddressItemOptionClicked(addressItem, $r1.id);
                }
            }
        }, false) {
            public void onClick(int $i0) throws  {
                super.onClick($i0);
                dismiss();
            }
        };
        c18733.setTitleStr($r1.getTitle());
        c18733.show();
    }

    static boolean shouldFilterOut(int $i0) throws  {
        return $i0 == 3 && !NavigateNativeManager.instance().suggestParkingEnabled();
    }

    private static void setupItems(int[][] $r0, Items $r1) throws  {
        int $i1;
        int $i0 = $r0.length;
        for (int[] $r2 : $r0) {
            if (shouldFilterOut($r2[0])) {
                $i0--;
            }
        }
        if ($r1.LABELS == null) {
            $r1.LABELS = new String[$i0];
            $r1.ICONS = new int[$i0];
            $r1.IDS = new int[$i0];
            $i0 = 0;
            for ($i1 = 0; $i1 < $r0.length; $i1++) {
                if (!shouldFilterOut($r0[$i1][0])) {
                    $r1.LABELS[$i0] = DisplayStrings.displayString($r0[$i1][1]);
                    $r1.ICONS[$i0] = $r0[$i1][2];
                    $r1.IDS[$i0] = $r0[$i1][0];
                    $i0++;
                }
            }
        }
    }
}
