package com.waze.navigate;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.CalendarContract.Calendars;
import android.provider.CalendarContract.Events;
import android.support.v4.content.ContextCompat;
import com.facebook.appevents.AppEventsConstants;
import com.waze.AppService;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.OfflineNativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.autocomplete.PlaceData;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolRide;
import com.waze.ifs.async.RunnableCallback;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.async.UpdateHandlers;
import com.waze.ifs.ui.ActivityBase;
import com.waze.main.navigate.Category;
import com.waze.main.navigate.EventOnRoute;
import com.waze.main.navigate.LocationData;
import com.waze.main.navigate.MajorEventOnRoute;
import com.waze.navigate.social.AddFriendsData;
import com.waze.navigate.social.EndDriveData;
import com.waze.navigate.social.FriendsListData;
import com.waze.navigate.social.IdsMatchData;
import com.waze.planned_drive.PlannedDriveActivity;
import com.waze.planned_drive.PlannedDriveListListener;
import com.waze.planned_drive.PlannedDriveOptionsListener;
import com.waze.reports.GasPriceReport;
import com.waze.reports.VenueData;
import com.waze.routes.AlternativeRoute;
import com.waze.strings.DisplayStrings;
import com.waze.user.FriendUserData;
import com.waze.user.PersonBase;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import com.waze.widget.WazeAppWidgetLog;
import com.waze.widget.WazeAppWidgetProvider;
import com.waze.widget.WazeAppWidgetService;
import dalvik.annotation.Signature;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DriveToNativeManager {
    public static final String[] CALENDAR_PROJECTION = new String[]{"_id", "account_name", "ownerAccount", "visible"};
    public static final String[] EVENT_PROJECTION = new String[]{"_id", "calendar_id"};
    public static final String IGNORED_CALENDAR_DELIMITER = "//";
    public static final int UH_ANIMATION_ENDED = TicketRoller.get(Type.Handler);
    public static int UH_DANGER_ZONE_FOUND = TicketRoller.get(Type.Handler);
    public static final int UH_ETA = TicketRoller.get(Type.Handler);
    public static final int UH_NEARBY_STATIONS = TicketRoller.get(Type.Handler);
    public static final int UH_SEARCH_ADD_RESULT = TicketRoller.get(Type.Handler);
    public static final int UH_SEARCH_FAIL = TicketRoller.get(Type.Handler);
    public static final int UH_SEARCH_FINALIZE = TicketRoller.get(Type.Handler);
    private static DriveToNativeManager instance = null;
    private static boolean ready = false;
    private UpdateHandlers handlers;
    AddressItem mAddress;
    private boolean mIsLoadingRoutes;
    private PlannedDriveListListener mPlannedDriveListListener;
    private PlannedDriveOptionsListener mPlannedDriveOptionsListener;

    public interface EndDriveListener {
        void onComplete(EndDriveData endDriveData) throws ;
    }

    public interface FriendsListListener {
        void onComplete(FriendsListData friendsListData) throws ;
    }

    public interface LocationDataListener {
        void onComplete(LocationData locationData) throws ;
    }

    public interface EventsOnRouteListener {
        void onComplete(EventOnRoute[] eventOnRouteArr) throws ;
    }

    public interface DriveToHasAddressCallback {
        void hasAddressCallback(boolean z) throws ;
    }

    public interface DriveToGetAddressItemArrayCallback {
        void getAddressItemArrayCallback(AddressItem[] addressItemArr) throws ;
    }

    public interface ObjectListener {
        void onComplete(Object obj) throws ;
    }

    public interface ProductListener {
        void onComplete(Product product) throws ;
    }

    public interface AddFriendsListener {
        void onComplete(AddFriendsData addFriendsData) throws ;
    }

    public static class AddressItemAppData {
        public boolean bNearingMinimized;
    }

    public interface AlternativeRoutesListener {
        void onComplete(AlternativeRoute[] alternativeRouteArr) throws ;
    }

    public interface AutoCompleteAdsResult {
        void onComplete(PlaceData placeData) throws ;
    }

    public class CalendarEvent {
        public String address;
        public String beginTime;
        public boolean recurring;
        public int timeModified;
        public String title;
    }

    public interface CategoriesListener {
        void onComplete(Category[] categoryArr) throws ;
    }

    public interface IdsMatchListener {
        void onComplete(IdsMatchData idsMatchData) throws ;
    }

    public interface MajorEventsOnRouteListener {
        void onComplete(MajorEventOnRoute[] majorEventOnRouteArr) throws ;
    }

    public interface NumberOfFriendsDrivingListener {
        void onComplete(int i) throws ;
    }

    public interface PersonMappingListener {
        void onComplete(@Signature({"(", "Ljava/util/ArrayList", "<", "Lcom/waze/user/PersonBase;", ">;)V"}) ArrayList<PersonBase> arrayList) throws ;
    }

    public interface PriceFormatListener {
        void onComplete(String str) throws ;
    }

    public interface PriceFormatsListener {
        void onComplete(String[] strArr) throws ;
    }

    private static final class SearchResultHandler extends Handler {
        private SearchResultHandler() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            if ($r1.what == DriveToNativeManager.UH_SEARCH_ADD_RESULT) {
                DriveToNativeManager.getInstance().unsetUpdateHandler(DriveToNativeManager.UH_SEARCH_ADD_RESULT, this);
                AddressItem $r6 = (AddressItem) $r1.getData().getSerializable("address_item");
                if ($r6 != null) {
                    MainActivity $r7 = AppService.getMainActivity();
                    if ($r7 != null) {
                        Intent $r2 = new Intent($r7, AddressPreviewActivity.class);
                        $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r6);
                        $r7.startActivity($r2);
                        return;
                    }
                    return;
                }
                return;
            }
            super.handleMessage($r1);
        }
    }

    public interface SortPreferencesListener {
        void onComplete(SortPreferences sortPreferences) throws ;
    }

    private native void CalendarAddressRemoveNTV(String str) throws ;

    private native void CalendarAddressVerifiedByIndexNTV(int i, String str) throws ;

    private native void CalendarAddressVerifiedNTV(String str, int i, int i2, String str2, String str3) throws ;

    private native void DriveEventNTV(String str, boolean z) throws ;

    private native PlaceData GetAutoCompleteAds(String str) throws ;

    private native String GetPriceFormatNTV(String str) throws ;

    private native String[] GetPriceFormatsNTV(String str) throws ;

    private native void InitMeetingNTV(String str) throws ;

    private native int UpdatePriceFormatNTV() throws ;

    private native void VerifyEventAndDriveByIndexNTV(int i, String str, String str2) throws ;

    private native void VerifyEventByIndexNTV(int i, String str, String str2) throws ;

    private native void VerifyEventWithNoAddressNTV(String str) throws ;

    private native void addCalendarEventNTV(String str, long j, long j2, boolean z, boolean z2, String str2, String str3) throws ;

    private native void cancelStopPointNTV() throws ;

    private native void convertFavoriteToRecentNTV(String str) throws ;

    private native void createPlannedDriveNTV(String str, int i, int i2, String str2, String str3, String str4, String str5, String str6, long j, String str7) throws ;

    private native void eraseAddressItemNTV(String str, int i, String str2) throws ;

    private native AddFriendsData getAddFriendsDataNTV() throws ;

    private native AddressItemAppData getAddressItemAppDataNTV(String str) throws ;

    private native EventOnRoute[] getAlertsOnRouteNTV() throws ;

    private native AlternativeRoute[] getAlternativeRoutesNTV() throws ;

    private native AddressItem[] getAutoCompleteNTV() throws ;

    private native Category[] getCategoriesNTV() throws ;

    private native EndDriveData getEndDriveDataNTV() throws ;

    private native EventOnRoute[] getEventsOnRouteNTV() throws ;

    private native AddressItem[] getFavoritesNTV(boolean z) throws ;

    private native EndDriveData getFriendsDrivingDataNTV(String str) throws ;

    private native FriendsListData getFriendsListDataNTV() throws ;

    private native AddressItem[] getFriendsSharedPlacesNTV(int i) throws ;

    private native AddressItem[] getHistoryNTV() throws ;

    private native AddressItem[] getHomeNTV() throws ;

    private native IdsMatchData getIdsMatchDataNTV() throws ;

    private native LocationData getLocationDataNTV(int i, int i2, int i3, String str) throws ;

    private native MajorEventOnRoute[] getMajorEventsOnRouteNTV() throws ;

    private native FriendsListData getMySharedDriveUsersNTV() throws ;

    private native NearbyStation[] getNearbyStationsNTV() throws ;

    private native int getNumberOfFriendsDrivingNTV() throws ;

    private native Product getProductNTV(int i) throws ;

    private native FriendsListData getRemovedFriendsDataNTV() throws ;

    private native SearchEngine[] getSearchEnginesNTV(String str) throws ;

    private native void getSearchResultsEtaNTV(String str) throws ;

    private native FriendsListData getShareFriendsListDataNTV(int i, int i2) throws ;

    private native SortPreferences getSortPreferencesNTV(String str) throws ;

    private native AddressItem[] getTopTenFavoritesNTV() throws ;

    private native FriendsListData getUsersWithDriveSharingNTV(boolean z, boolean z2, boolean z3) throws ;

    private native AddressItem[] getWorkNTV() throws ;

    private native boolean hasHomeAndWorkNTV() throws ;

    private native void initNativeLayerNTV() throws ;

    private native boolean isAutocompleteServerAdsNTV() throws ;

    private native boolean isDayModeNTV() throws ;

    private native void loadCarpoolDriveOptionsNTV(long j, long j2, long j3, String str) throws ;

    private native void loadPlannedDriveOptionsNTV(int i, int i2, int i3, int i4, int i5, String str, String str2, String str3, String str4, String str5) throws ;

    private native void logAnalyticsOnGoNTV(int i) throws ;

    private native void moveFavoriteAddressItemNTV(int i, int i2) throws ;

    private native void navigateNTV(int i, int i2, String str, String str2, int i3, String str3, String str4, String str5, String str6, String str7, String str8, String str9, String str10, boolean z) throws ;

    private native void navigate_waypoint_clearNTV() throws ;

    private native void notifyAddressItemClickedNTV(int i) throws ;

    private native void notifyAddressItemNavigateNTV(int i) throws ;

    private native void notifyAddressItemShownInNavigateNTV(int i) throws ;

    private native void notifyAddressItemShownNTV(int i, boolean z) throws ;

    private native void onAlternativeRoutesClosedNTV() throws ;

    private native void openPrivateMessageNTV(int i) throws ;

    private native void playDueToTTSNTV() throws ;

    private native void removeDeparturePoiNTV() throws ;

    private native void removeEventByLocationNTV(String str) throws ;

    private native void removeEventNTV(String str, boolean z) throws ;

    private native void removePlannedDriveNTV(String str) throws ;

    private native void removeStartPointNTV() throws ;

    private native void renameFavoriteNTV(String str, String str2) throws ;

    private native void requestPlannedDriveEtaNTV(String str) throws ;

    private native void requestRouteNTV(boolean z) throws ;

    private native void requestStopPointNTV(int i) throws ;

    private native void rerouteNTV(boolean z) throws ;

    private native int searchBrandsNTV(boolean z, String str, boolean z2) throws ;

    private native int searchNTV(boolean z, String str, String str2, String str3, String str4) throws ;

    private native void searchNearbyStationsNTV() throws ;

    private native void selectAlternativeRouteNTV(int i) throws ;

    private native void setCarpoolMapNTV(String str, String str2, boolean z) throws ;

    private native void setMapAreaZoomNTV(float f, int i, int i2) throws ;

    private native void setMeetingNTV(String str) throws ;

    private native void setProductPricesNTV(int i, float[] fArr, int[] iArr, int i2) throws ;

    private native void setSearchMapViewNTV(boolean z) throws ;

    private native void setSearchModeNTV(boolean z) throws ;

    private native void setSearchResultPinsNTV(String str, String str2, String str3) throws ;

    private native void setShareMapModeNTV(boolean z) throws ;

    private native void setSkipConfirmWaypointNTV(boolean z) throws ;

    private native void setStartPointLLNTV(int i, int i2) throws ;

    private native void setStartPointNTV(VenueData venueData) throws ;

    private native void setStopPointNTV(boolean z) throws ;

    private native void setStopPointPreviewNTV(int i, int i2, int i3) throws ;

    private native void showOnMapNTV(int i, int i2) throws ;

    private native void showVenueOnMapNTV(VenueData venueData) throws ;

    private java.lang.String storeAddressItemInt(com.waze.navigate.AddressItem r39, boolean r40) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:13:0x00f3 in {4, 7, 10, 12, 14, 16} preds:[]
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
        r38 = this;
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = "store ai event running in thread ";
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = java.lang.Thread.currentThread();
        r0 = r18;
        r19 = r0.getId();
        r0 = r16;
        r1 = r19;
        r16 = r0.append(r1);
        r0 = r16;
        r21 = r0.toString();
        r0 = r21;
        com.waze.Logger.m36d(r0);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = "ai= ";
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r0 = r16;
        r1 = r39;
        r16 = r0.append(r1);
        r0 = r16;
        r21 = r0.toString();
        r0 = r21;
        com.waze.Logger.m36d(r0);
        r0 = r39;
        r22 = r0.getLocationX();
        r0 = r22;
        r23 = r0.intValue();
        r0 = r39;
        r22 = r0.getLocationY();
        r0 = r22;
        r24 = r0.intValue();
        r21 = "";
        r25 = "";
        r0 = r39;
        r0 = r0.venueData;
        r26 = r0;
        if (r26 == 0) goto L_0x014a;
    L_0x0077:
        r0 = r39;
        r0 = r0.venueData;
        r26 = r0;
        r0 = r0.context;
        r27 = r0;
        if (r27 == 0) goto L_0x008d;
    L_0x0083:
        r0 = r39;
        r0 = r0.venueData;
        r26 = r0;
        r0 = r0.context;
        r21 = r0;
    L_0x008d:
        r0 = r39;
        r0 = r0.venueData;
        r26 = r0;
        r0 = r0.RoutingContext;
        r27 = r0;
        if (r27 == 0) goto L_0x00a3;
    L_0x0099:
        r0 = r39;
        r0 = r0.venueData;
        r26 = r0;
        r0 = r0.RoutingContext;
        r25 = r0;
    L_0x00a3:
        r27 = 0;
        r0 = r39;
        r28 = r0.hasIcon();
        if (r28 == 0) goto L_0x0159;
    L_0x00ad:
        r0 = r39;
        r27 = r0.getIcon();
    L_0x00b3:
        r0 = r39;
        r29 = r0.getId();
        r0 = r39;
        r22 = r0.getCategory();
        r0 = r22;
        r30 = r0.intValue();
        r0 = r39;
        r31 = r0.getCity();
        r0 = r39;
        r32 = r0.getStreet();
        r0 = r39;
        r33 = r0.getHouse();
        r0 = r39;
        r34 = r0.getState();
        r0 = r39;
        r35 = r0.getTitle();
        r0 = r39;
        r0 = r0.VanueID;
        r36 = r0;
        r0 = r39;
        r37 = r0.getAddress();
        goto L_0x00f7;
    L_0x00f0:
        goto L_0x00a3;
        goto L_0x00f7;
    L_0x00f4:
        goto L_0x00b3;
    L_0x00f7:
        r0 = r38;
        r1 = r29;
        r2 = r30;
        r3 = r31;
        r4 = r32;
        r5 = r33;
        r6 = r34;
        r7 = r35;
        r8 = r23;
        r9 = r24;
        r10 = r36;
        r11 = r21;
        r12 = r25;
        r13 = r27;
        r14 = r40;
        r15 = r37;
        r21 = r0.storeAddressItemNTV(r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15);
        r16 = new java.lang.StringBuilder;
        r0 = r16;
        r0.<init>();
        r17 = "after storeAddressItemNTV in thread ";
        r0 = r16;
        r1 = r17;
        r16 = r0.append(r1);
        r18 = java.lang.Thread.currentThread();
        r0 = r18;
        r19 = r0.getId();
        r0 = r16;
        r1 = r19;
        r16 = r0.append(r1);
        r0 = r16;
        r25 = r0.toString();
        r0 = r25;
        com.waze.Logger.m36d(r0);
        return r21;
    L_0x014a:
        r0 = r39;
        r0 = r0.routingContext;
        r27 = r0;
        if (r27 == 0) goto L_0x00a3;
    L_0x0152:
        r0 = r39;
        r0 = r0.routingContext;
        r25 = r0;
        goto L_0x00f0;
    L_0x0159:
        r0 = r39;
        r0 = r0.mPreviewIcon;
        r36 = r0;
        if (r36 == 0) goto L_0x00b3;
    L_0x0161:
        r0 = r39;
        r0 = r0.mPreviewIcon;
        r36 = r0;
        r28 = r0.isEmpty();
        if (r28 != 0) goto L_0x00b3;
    L_0x016d:
        r0 = r39;
        r0 = r0.mPreviewIcon;
        r27 = r0;
        goto L_0x00f4;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.DriveToNativeManager.storeAddressItemInt(com.waze.navigate.AddressItem, boolean):java.lang.String");
    }

    private native String storeAddressItemNTV(String str, int i, String str2, String str3, String str4, String str5, String str6, int i2, int i3, String str7, String str8, String str9, String str10, boolean z, String str11) throws ;

    private native void storeAddressItemsNTV(String str, int i, String str2, String str3, String str4, String str5, String str6, int i2, int i3, String str7, int i4, String str8, String str9, String str10, String str11, String str12, int i5, int i6, boolean z) throws ;

    private native void unsetMeetingNTV() throws ;

    private native void unsetSearchMapViewNTV() throws ;

    private native void updateAddressItemAppDataNTV(String str, AddressItemAppData addressItemAppData) throws ;

    private native void updateEventPlaceNTV(String str, long j, long j2, String str2) throws ;

    private native void updateEventVenueNTV(String str, VenueData venueData) throws ;

    private native void updatePlannedDriveNTV(String str, long j) throws ;

    public native void addDangerZoneStatNTV(int i, int i2, String str, String str2) throws ;

    public native String[] configGetVehicleTypesNTV() throws ;

    public native String[] getClosureSuggestionsNTV() throws ;

    public native AddressItem getCurrentNavigatingAddressItemNTV() throws ;

    public native String getDestinationIdNTV() throws ;

    public native String getFriendImageNTV(int i, int i2) throws ;

    public native int getMeetingLatitudeNTV(String str) throws ;

    public native int getMeetingLongitudeNTV(String str) throws ;

    public native int getMyShareDriveUsersCountNTV() throws ;

    public native AddressItem[] getPlannedDriveEventsNTV() throws ;

    public native String getShareStatusTextNTV(int i) throws ;

    public native String getTimeToParkNearingStrNTV() throws ;

    public native String getTimeToParkStrFormatNTV(int i, int i2) throws ;

    public native String getTimeToParkStrNTV() throws ;

    public native int getTotalEventsNTV() throws ;

    public native boolean isDrivingHomeNTV() throws ;

    public native int isInDangerZoneNTV(int i, int i2) throws ;

    public native int isMeetingInDangerZoneNTV(String str) throws ;

    public native boolean isStopPointSearchNTV() throws ;

    public native boolean isViaFerry() throws ;

    public native boolean shouldShowPlanDrivePromo() throws ;

    public void setUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.setUpdateHandler($i0, $r1);
    }

    public void setUpdateHandler(@Signature({"(I", "Ljava/lang/ref/WeakReference", "<", "Landroid/os/Handler;", ">;)V"}) int $i0, @Signature({"(I", "Ljava/lang/ref/WeakReference", "<", "Landroid/os/Handler;", ">;)V"}) WeakReference<Handler> $r1) throws  {
        this.handlers.setUpdateHandler($i0, (WeakReference) $r1);
    }

    public void unsetUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.unsetUpdateHandler($i0, $r1);
    }

    private void init() throws  {
        Logger.m36d("init driveto ready=" + ready);
        if (!ready) {
            Logger.m36d("initNativeLayerNTV " + Thread.currentThread().getId());
            initNativeLayerNTV();
            this.handlers = new UpdateHandlers();
            ready = true;
        }
    }

    public static DriveToNativeManager getInstance() throws  {
        if (instance == null) {
            instance = new DriveToNativeManager();
        }
        return instance;
    }

    private DriveToNativeManager() throws  {
        init();
        Logger.m36d("ctr running in thread " + Thread.currentThread().getId());
    }

    public void getEndDriveData(final EndDriveListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private EndDriveData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getEndDriveDataNTV();
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getFriendsDriveData(final EndDriveListener $r1, final String $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private EndDriveData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getFriendsDrivingDataNTV($r2);
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getAddFriendsData(final AddFriendsListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private AddFriendsData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getAddFriendsDataNTV();
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getIdsMatchData(final IdsMatchListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private IdsMatchData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getIdsMatchDataNTV();
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public static boolean isSameDay(Calendar $r0, Calendar $r1) throws  {
        if ($r0 != null && $r1 != null) {
            return $r0.get(0) == $r1.get(0) && $r0.get(1) == $r1.get(1) && $r0.get(6) == $r1.get(6);
        } else {
            throw new IllegalArgumentException("The dates must not be null");
        }
    }

    public static boolean isSameDay(Date $r0, Date $r1) throws  {
        if ($r0 == null || $r1 == null) {
            throw new IllegalArgumentException("The dates must not be null");
        }
        Calendar $r3 = Calendar.getInstance();
        $r3.setTime($r0);
        Calendar $r4 = Calendar.getInstance();
        $r4.setTime($r1);
        return isSameDay($r3, $r4);
    }

    public static boolean isToday(Date $r0) throws  {
        return isSameDay($r0, Calendar.getInstance().getTime());
    }

    private boolean loadEvent(@Signature({"(", "Landroid/database/Cursor;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[", "Ljava/lang/String;", ")Z"}) Cursor $r1, @Signature({"(", "Landroid/database/Cursor;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[", "Ljava/lang/String;", ")Z"}) String $r2, @Signature({"(", "Landroid/database/Cursor;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[", "Ljava/lang/String;", ")Z"}) Map<String, String> $r3, @Signature({"(", "Landroid/database/Cursor;", "Ljava/lang/String;", "Ljava/util/Map", "<", "Ljava/lang/String;", "Ljava/lang/String;", ">;[", "Ljava/lang/String;", ")Z"}) String[] $r4) throws  {
        String $r6 = $r1.getString(0);
        $r1.getString(1);
        String $r7 = $r1.getString(2);
        long $l0 = $r1.getLong(3);
        long $l1 = $r1.getLong(4);
        boolean $z0 = !$r1.getString(5).equals(AppEventsConstants.EVENT_PARAM_VALUE_NO);
        String $r8 = $r1.getString(6);
        if (Arrays.binarySearch($r4, (String) $r3.get($r6)) > 0) {
            return false;
        }
        if ($r8 == null || $r8.length() == 0 || $r8.matches($r2)) {
            return false;
        }
        $r2 = $r8.replaceAll("[\\t\\n\\r]+", " ");
        boolean $z1 = $r1.getString(7) != null;
        $r6 = formatGloballyUniqueId($r6);
        Logger.m36d("DriveToNativeManager:loadEvent: Calendar Id: " + $r6 + " Display Name: " + $r7 + " Location: " + $r2 + " AllDay: " + $z0 + " IsRecurring: " + $z1 + " Start: " + new Date($l0) + " End: " + new Date($l1));
        Date date = new Date($l0);
        if (AppService.getAppContext() == null) {
            OfflineNativeManager.getContext();
        }
        addCalendarEventNTV($r6, $l0 / 1000, $l1 / 1000, $z0, $z1, $r2, $r7);
        return true;
    }

    public void updateEvent(final String $r1, final AddressItem $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                if ($r2.venueData != null) {
                    DriveToNativeManager.this.updateEventVenueNTV($r1, $r2.venueData);
                }
                DriveToNativeManager.this.updateEventPlaceNTV($r1, (long) $r2.getLocationX().intValue(), (long) $r2.getLocationY().intValue(), $r2.getAddress());
            }
        });
    }

    public void removeEvent(final String $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.removeEventNTV($r1, $z0);
            }
        });
    }

    public void removeEventByLocation(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.removeEventByLocationNTV($r1);
            }
        });
    }

    public int fetchCalendarEvents(int $i0, int $i1, String $r1) throws  {
        Logger.m36d(String.format("fetchCalendarEvents called for %d days and %d events", new Object[]{Integer.valueOf($i0), Integer.valueOf($i1)}));
        if (OfflineNativeManager.isOfflineMode() || NativeManager.getInstance().getAccessToCalendarAllowed()) {
            int $i2 = 0;
            Builder $r8 = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
            long $l3 = new Date().getTime();
            ContentUris.appendId($r8, $l3 - 3600000);
            ContentUris.appendId($r8, (86400000 * ((long) $i0)) + $l3);
            Context $r10 = AppService.getAppContext();
            Context $r11 = $r10;
            if ($r10 == null) {
                $r11 = OfflineNativeManager.getContext();
            }
            if (ContextCompat.checkSelfPermission($r11, "android.permission.READ_CALENDAR") != 0) {
                Logger.m38e("getEventToCalendarMap has no permission to read calendars");
                return 0;
            }
            try {
                Cursor $r14 = $r11.getContentResolver().query($r8.build(), new String[]{"event_id", "_id", "title", "begin", "end", "allDay", "eventLocation", "rrule"}, null, null, "startDay ASC, startMinute ASC");
                if ($r14 == null) {
                    Logger.m36d("fetchCalendarEvents failed to open cursor");
                    return 0;
                }
                String $r5;
                if (OfflineNativeManager.isOfflineMode()) {
                    $r5 = OfflineNativeManager.existingInstance().getExcludedCalendarsNTV();
                } else {
                    Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CALENDAR_READ, AnalyticsEvents.ANALYTICS_EVENT_INFO_COUNT, $r14.getCount());
                    $r5 = NativeManager.getInstance().getExcludedCalendarsNTV();
                }
                Map $r16 = getEventToCalendarMap();
                String[] $r13 = getIgnoredCalendarIds($r5);
                Arrays.sort($r13);
                Logger.m36d("fetchCalendarEvents cursor size:" + $r14.getCount());
                for (boolean $z0 = $r14.moveToFirst(); $z0 && $i2 < $i1; $z0 = $r14.moveToNext()) {
                    if (loadEvent($r14, $r1, $r16, $r13)) {
                        $i2++;
                    }
                }
                $r14.close();
                Logger.m36d("fetchCalendarEvents returning " + $i2);
                return $i2;
            } catch (Exception $r2) {
                Logger.m36d("fetchCalendarEvents exception " + $r2);
                return 0;
            }
        }
        Logger.m36d("fetchCalendarEvents no permissions");
        return 0;
    }

    String[] getIgnoredCalendarIds(String $r1) throws  {
        ArrayList $r4 = new ArrayList();
        Context $r5 = AppService.getAppContext();
        Context $r6 = $r5;
        if ($r5 == null) {
            $r6 = OfflineNativeManager.getContext();
        }
        ContentResolver $r7 = $r6.getContentResolver();
        if (ContextCompat.checkSelfPermission($r6, "android.permission.READ_CALENDAR") != 0) {
            Logger.m38e("getIgnoredCalendarIds has no permission to read calendars");
            return null;
        }
        try {
            Cursor $r10 = $r7.query(Calendars.CONTENT_URI, CALENDAR_PROJECTION, null, null, null);
            if ($r10 == null) {
                Logger.m38e("getIgnoredCalendarIds failed to query calendars");
                return null;
            }
            String[] $r8 = $r1.split(",");
            for (boolean $z0 = $r10.moveToFirst(); $z0; $z0 = $r10.moveToNext()) {
                $r1 = $r10.getString(0);
                String $r12 = $r10.getString(1);
                String $r13 = $r10.getString(2);
                String $r3 = $r10.getString(3);
                $r12 = getIgnoredCalendarId($r12, $r13);
                if ($r1 != null) {
                    if ($r3.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES)) {
                        for (String $r32 : $r8) {
                            if ($r32.equals($r12)) {
                                $r4.add($r1);
                                break;
                            }
                        }
                    } else {
                        $r4.add($r1);
                    }
                } else {
                    Logger.m38e("DTNM:getIgnoredCalendarIds: null Calendar ID, ignoring event");
                }
            }
            $r10.close();
            return (String[]) $r4.toArray(new String[$r4.size()]);
        } catch (Exception $r2) {
            Logger.m38e("getIgnoredCalendarIds exception " + $r2);
            return null;
        }
    }

    private Map<String, String> getEventToCalendarMap() throws  {
        HashMap $r2 = new HashMap();
        Context $r3 = AppService.getAppContext();
        Context $r4 = $r3;
        if ($r3 == null) {
            $r4 = OfflineNativeManager.getContext();
        }
        ContentResolver $r5 = $r4.getContentResolver();
        if (ContextCompat.checkSelfPermission($r4, "android.permission.READ_CALENDAR") != 0) {
            Logger.m38e("getEventToCalendarMap has no permission to read calendars");
            return null;
        }
        Cursor $r7 = $r5.query(Events.CONTENT_URI, EVENT_PROJECTION, null, null, null);
        if ($r7 == null) {
            Logger.m38e("getEventToCalendarMap failed to query calendars");
            return null;
        }
        for (boolean $z0 = $r7.moveToFirst(); $z0; $z0 = $r7.moveToNext()) {
            String $r8 = $r7.getString(0);
            String $r9 = $r7.getString(1);
            if (!($r8 == null || $r9 == null)) {
                $r2.put($r8, $r9);
            }
        }
        $r7.close();
        return $r2;
    }

    public static String getIgnoredCalendarId(String $r0, String $r1) throws  {
        return $r0 + IGNORED_CALENDAR_DELIMITER + $r1;
    }

    public CalendarEvent fetchCalendarEvent(String $r1) throws  {
        if (NativeManager.getInstance().getAccessToCalendarAllowed()) {
            Builder $r6 = Uri.parse("content://com.android.calendar/instances/when").buildUpon();
            long $l0 = new Date().getTime();
            ContentUris.appendId($r6, $l0 - 3600000);
            ContentUris.appendId($r6, 86400000000L + $l0);
            try {
                Cursor $r11 = AppService.getAppContext().getContentResolver().query($r6.build(), new String[]{"event_id", "_id", "title", "begin", "end", "allDay", "eventLocation", "rrule"}, null, null, "startDay ASC, startMinute ASC");
                if ($r11 == null) {
                    Logger.m38e("fetchCalendarEvent failed to open cursor");
                    return null;
                }
                $r11.moveToFirst();
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_CALENDAR_READ, AnalyticsEvents.ANALYTICS_EVENT_INFO_COUNT, $r11.getCount());
                Map $r13 = getEventToCalendarMap();
                String[] $r10 = getIgnoredCalendarIds(NativeManager.getInstance().getExcludedCalendarsNTV());
                Arrays.sort($r10);
                boolean $z0 = $r11.getCount() > 0;
                while ($z0) {
                    String $r14 = $r11.getString(0);
                    $r11.getString(1);
                    if (!formatGloballyUniqueId($r14).equals($r1)) {
                        $z0 = $r11.moveToNext();
                    } else if (Arrays.binarySearch($r10, (String) $r13.get($r14)) > 0) {
                        Logger.m43w("fetchCalendarEvent the event belongs to an ignored calendar");
                        return null;
                    } else {
                        CalendarEvent calendarEvent = new CalendarEvent();
                        calendarEvent.address = $r11.getString(6);
                        calendarEvent.recurring = $r11.getString(7) != null;
                        calendarEvent.beginTime = $r11.getString(3);
                        calendarEvent.title = $r11.getString(2);
                        $r11.close();
                        Logger.m36d("fetchCalendarEvent event found, id=" + $r1);
                        return calendarEvent;
                    }
                }
                $r11.close();
                Logger.m36d("fetchCalendarEvent event not found, id=" + $r1);
                return null;
            } catch (Exception $r2) {
                Logger.m38e("fetchCalendarEvent failed : " + $r2);
                return null;
            }
        }
        Logger.m38e("fetchCalendarEvent called but no access to calendar");
        return null;
    }

    private String formatGloballyUniqueId(String $r1) throws  {
        String $r3;
        if (OfflineNativeManager.isOfflineMode()) {
            $r3 = OfflineNativeManager.existingInstance().getPushInstallationUUID();
        } else {
            $r3 = NativeManager.getInstance().getPushInstallationUUID();
        }
        return $r3 + "*" + $r1;
    }

    public void getNumberOfFriendsDriving(final NumberOfFriendsDrivingListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private int nFriends;

            public void event() throws  {
                try {
                    this.nFriends = DriveToNativeManager.this.getNumberOfFriendsDrivingNTV();
                } catch (Exception e) {
                    this.nFriends = -1;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.nFriends);
            }
        });
    }

    public void getFriendsListData(final FriendsListListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private FriendsListData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getFriendsListDataNTV();
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getShareFriendsListData(final int $i0, final int $i1, final FriendsListListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private FriendsListData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getShareFriendsListDataNTV($i0, $i1);
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getMySharedDriveUsers(final FriendsListListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private FriendsListData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getMySharedDriveUsersNTV();
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getUsersWithDriveSharing(final boolean $z0, final boolean $z1, final boolean $z2, final FriendsListListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private FriendsListData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getUsersWithDriveSharingNTV($z0, $z1, $z2);
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getRemovedFriendsData(final FriendsListListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private FriendsListData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getRemovedFriendsDataNTV();
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.data);
            }
        });
    }

    public void getCategories(final CategoriesListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private Category[] categories;

            public void event() throws  {
                try {
                    this.categories = DriveToNativeManager.this.getCategoriesNTV();
                } catch (Exception e) {
                    this.categories = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.categories);
            }
        });
    }

    public void getProduct(final int $i0, final ProductListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private Product product;

            public void event() throws  {
                try {
                    this.product = DriveToNativeManager.this.getProductNTV($i0);
                } catch (Exception e) {
                    this.product = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.product);
            }
        });
    }

    public void getSortPreferences(final String $r1, final SortPreferencesListener $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private SortPreferences sortPreferences;

            public void event() throws  {
                this.sortPreferences = DriveToNativeManager.this.getSortPreferencesNTV($r1);
            }

            public void callback() throws  {
                $r2.onComplete(this.sortPreferences);
            }
        });
    }

    public void getPriceFormat(final PriceFormatListener $r1, final String $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private String priceFormat;

            public void event() throws  {
                this.priceFormat = DriveToNativeManager.this.GetPriceFormatNTV($r2);
            }

            public void callback() throws  {
                $r1.onComplete(this.priceFormat);
            }
        });
    }

    public void getPriceFormats(final PriceFormatsListener $r1, final String $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private String[] priceFormats;

            public void event() throws  {
                this.priceFormats = DriveToNativeManager.this.GetPriceFormatsNTV($r2);
            }

            public void callback() throws  {
                $r1.onComplete(this.priceFormats);
            }
        });
    }

    public void getAutoCompleteAdsResult(final AutoCompleteAdsResult $r1, final String $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private PlaceData AdsData;

            public void event() throws  {
                this.AdsData = DriveToNativeManager.this.GetAutoCompleteAds($r2);
            }

            public void callback() throws  {
                $r1.onComplete(this.AdsData);
            }
        });
    }

    public PlaceData getAutoCompleteAdsResultNative(String $r1) throws  {
        return GetAutoCompleteAds($r1);
    }

    public void getFavorites(final DriveToGetAddressItemArrayCallback $r1, final boolean $z0) throws  {
        Logger.m36d("getFavorites running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            AddressItem[] ai = null;

            public void event() throws  {
                Logger.m36d("getFavorites event running in thread " + Thread.currentThread().getId());
                this.ai = DriveToNativeManager.this.getFavoritesNTV($z0);
            }

            public void callback() throws  {
                Logger.m36d("getFavorites callback running in thread " + Thread.currentThread().getId());
                $r1.getAddressItemArrayCallback(this.ai);
            }
        });
    }

    public void getHome(final DriveToGetAddressItemArrayCallback $r1) throws  {
        Logger.m36d("getFavorites running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            AddressItem[] ai = null;

            public void event() throws  {
                Logger.m36d("getFavorites event running in thread " + Thread.currentThread().getId());
                this.ai = DriveToNativeManager.this.getHomeNTV();
            }

            public void callback() throws  {
                Logger.m36d("getFavorites callback running in thread " + Thread.currentThread().getId());
                $r1.getAddressItemArrayCallback(this.ai);
            }
        });
    }

    public void hasHomeAndWork(final DriveToHasAddressCallback $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            boolean res;

            public void event() throws  {
                this.res = DriveToNativeManager.this.hasHomeAndWorkNTV();
            }

            public void callback() throws  {
                $r1.hasAddressCallback(this.res);
            }
        });
    }

    public void getWork(final DriveToGetAddressItemArrayCallback $r1) throws  {
        Logger.m36d("getFavorites running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            AddressItem[] ai = null;

            public void event() throws  {
                Logger.m36d("getFavorites event running in thread " + Thread.currentThread().getId());
                this.ai = DriveToNativeManager.this.getWorkNTV();
            }

            public void callback() throws  {
                Logger.m36d("getFavorites callback running in thread " + Thread.currentThread().getId());
                $r1.getAddressItemArrayCallback(this.ai);
            }
        });
    }

    public void getHistory(final DriveToGetAddressItemArrayCallback $r1) throws  {
        Logger.m36d("getHistory running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            AddressItem[] ai = null;

            public void event() throws  {
                Logger.m36d("getHistory event running in thread " + Thread.currentThread().getId());
                this.ai = DriveToNativeManager.this.getHistoryNTV();
            }

            public void callback() throws  {
                Logger.m36d("getHistory callback running in thread " + Thread.currentThread().getId());
                $r1.getAddressItemArrayCallback(this.ai);
            }
        });
    }

    public void getFriendsSharedPlaces(final int $i0, final DriveToGetAddressItemArrayCallback $r1) throws  {
        Logger.m36d("getFriendsSharedPlaces running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            AddressItem[] ai = null;

            public void event() throws  {
                Logger.m36d("getFriendsSharedPlaces event running in thread " + Thread.currentThread().getId());
                this.ai = DriveToNativeManager.this.getFriendsSharedPlacesNTV($i0);
            }

            public void callback() throws  {
                Logger.m36d("getFriendsSharedPlaces callback running in thread " + Thread.currentThread().getId());
                $r1.getAddressItemArrayCallback(this.ai);
            }
        });
    }

    public void getAutoCompleteData(final DriveToGetAddressItemArrayCallback $r1) throws  {
        Logger.m36d("getHistory running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            AddressItem[] ai = null;

            public void event() throws  {
                Logger.m36d("getHistory event running in thread " + Thread.currentThread().getId());
                this.ai = DriveToNativeManager.this.getAutoCompleteNTV();
            }

            public void callback() throws  {
                Logger.m36d("getHistory callback running in thread " + Thread.currentThread().getId());
                $r1.getAddressItemArrayCallback(this.ai);
            }
        });
    }

    public void search(String $r1, String $r2, String $r3, String $r4, boolean $z0, DriveToSearchCallback $r5) throws  {
        Logger.m36d("search running in thread " + Thread.currentThread().getId());
        final boolean z = $z0;
        final String str = $r1;
        final String str2 = $r2;
        final String str3 = $r3;
        final String str4 = $r4;
        final DriveToSearchCallback driveToSearchCallback = $r5;
        NativeManager.Post(new RunnableUICallback() {
            int rc;

            public void event() throws  {
                Logger.m36d("search event running in thread " + Thread.currentThread().getId());
                this.rc = DriveToNativeManager.this.searchNTV(z, str, str2, str3, str4);
                StringBuilder $r4 = new StringBuilder().append("search rc=");
                int $i1 = this.rc;
                Logger.m36d($r4.append($i1).toString());
            }

            public void callback() throws  {
                Logger.m36d("search callback running in thread " + Thread.currentThread().getId());
                if (driveToSearchCallback != null) {
                    driveToSearchCallback.searchCallback(this.rc);
                }
            }
        });
    }

    public void notifyAddressItemShown(final int $i0, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.notifyAddressItemShownNTV($i0, $z0);
            }
        });
    }

    public void notifyAddressItemShownBeforeNavigate(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.notifyAddressItemShownInNavigateNTV($i0);
            }
        });
    }

    public void notifyAddressItemNavigate(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.notifyAddressItemNavigateNTV($i0);
            }
        });
    }

    public void notifyAddressItemClicked(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.notifyAddressItemClickedNTV($i0);
            }
        });
    }

    public void navigate(AddressItem $r1, DriveToNavigateCallback $r2) throws  {
        navigate($r1, $r2, false, true, false);
    }

    public void navigate(AddressItem $r1, DriveToNavigateCallback $r2, boolean $z0) throws  {
        navigate($r1, $r2, false, false, $z0);
    }

    public void navigate(AddressItem $r1, DriveToNavigateCallback $r2, boolean $z0, boolean $z1) throws  {
        navigate($r1, $r2, $z0, $z1, false);
    }

    public void reroute(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.rerouteNTV($z0);
            }
        });
    }

    public void navigate(final AddressItem $r1, final DriveToNavigateCallback $r2, final boolean $z0, final boolean $z1, final boolean $z2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private int danger;

            class C21081 implements OnClickListener {
                C21081() throws  {
                }

                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        DriveToNativeManager.this.doNavigate($r1, $r2, $z0, $z1, $z2);
                        DriveToNativeManager.this.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                        return;
                    }
                    DriveToNativeManager.this.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
                    if ($r2 != null) {
                        $r2.navigateCallback(-1);
                    }
                }
            }

            class C21092 implements OnCancelListener {
                C21092() throws  {
                }

                public void onCancel(DialogInterface dialog) throws  {
                    DriveToNativeManager.this.addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_NAVIGATE_TO_DANGER_ZONE, "BACK");
                }
            }

            public void event() throws  {
                this.danger = DriveToNativeManager.this.isInDangerZoneNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue());
            }

            public void callback() throws  {
                NativeManager $r2 = NativeManager.getInstance();
                if (this.danger >= 0) {
                    MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava($r2.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), $r2.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_GO), false, new C21081(), $r2.getLanguageString(DisplayStrings.DS_KEEP_DRIVE), $r2.getLanguageString(344), -1, "dangerous_zone_icon", new C21092(), true, true);
                    return;
                }
                DriveToNativeManager.this.doNavigate($r1, $r2, $z0, $z1, $z2);
            }
        });
    }

    private void doNavigate(AddressItem $r1, DriveToNavigateCallback $r2, boolean $z0, boolean $z1, boolean $z2) throws  {
        navigateAnalytics($r1);
        if ($r2 != null) {
            $r2.navigateCallback(0);
        }
        final AddressItem addressItem = $r1;
        final boolean z = $z1;
        final boolean z2 = $z0;
        final boolean z3 = $z2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                String $r3;
                AddressItem $r4;
                String $r7;
                DriveToNativeManager $r5;
                int $i1;
                String $r72;
                int $i12;
                int $i2;
                String $r9;
                int $i3;
                String $r42;
                String $r10;
                String $r11;
                String $r12;
                String $r13;
                String $r14;
                String $r15;
                Logger.m36d("navigate event running in thread " + Thread.currentThread().getId());
                StringBuilder $r1 = new StringBuilder().append("ai=");
                AddressItem $r43 = addressItem;
                Logger.m36d($r1.append($r43).toString());
                if (z) {
                    $r3 = DriveToNativeManager.this.storeAddressItemInt(addressItem, false);
                    if ($r3 != null) {
                        $r43 = addressItem;
                        $r4 = $r43;
                        $r43.setId($r3);
                    }
                }
                $r3 = "";
                VenueData $r6 = addressItem;
                VenueData $r44 = $r6;
                if ($r6.venueData != null) {
                    $r6 = addressItem;
                    $r44 = $r6;
                    $r7 = $r6.venueData;
                    String $r62 = $r7;
                    if ($r7.RoutingContext != null) {
                        $r6 = addressItem;
                        $r44 = $r6;
                        $r7 = $r6.venueData;
                        $r62 = $r7;
                        $r3 = $r7.RoutingContext;
                        if (z2) {
                            $r5 = DriveToNativeManager.this;
                            $i1 = addressItem;
                            $r4 = $i1;
                            $r5.logAnalyticsOnGoNTV($i1.index);
                        }
                        $r43 = addressItem;
                        $r72 = $r43.getTitle();
                        $r43 = addressItem;
                        if ($r43.getCategory().intValue() == 7) {
                            $r43 = addressItem;
                            $r72 = $r43.getSecondaryTitle();
                        }
                        if ($r72 == null || $r72.isEmpty()) {
                            $r43 = addressItem;
                            $r72 = $r43.getAddress();
                        }
                        $r43 = addressItem;
                        PlannedDriveActivity.setNavigationAddressItem($r43);
                        $r5 = DriveToNativeManager.this;
                        $r43 = addressItem;
                        $i12 = $r43.getLocationX().intValue();
                        $r43 = addressItem;
                        $i2 = $r43.getLocationY().intValue();
                        $r43 = addressItem;
                        $r9 = $r43.getDealId();
                        $i1 = addressItem;
                        $r4 = $i1;
                        $i3 = $i1.advPointId;
                        $r7 = addressItem;
                        $r42 = $r7;
                        $r10 = $r7.VanueID;
                        $r43 = addressItem;
                        $r11 = $r43.getId();
                        $r43 = addressItem;
                        $r12 = $r43.getCountry();
                        $r43 = addressItem;
                        $r13 = $r43.getState();
                        $r43 = addressItem;
                        $r14 = $r43.getCity();
                        $r43 = addressItem;
                        $r15 = $r43.getStreet();
                        $r43 = addressItem;
                        $r5.navigateNTV($i12, $i2, $r72, $r9, $i3, $r10, $r11, $r12, $r13, $r14, $r15, $r43.getHouse(), $r3, z3);
                    }
                }
                $r7 = addressItem;
                $r42 = $r7;
                if ($r7.routingContext != null) {
                    $r7 = addressItem;
                    $r42 = $r7;
                    $r3 = $r7.routingContext;
                }
                if (z2) {
                    $r5 = DriveToNativeManager.this;
                    $i1 = addressItem;
                    $r4 = $i1;
                    $r5.logAnalyticsOnGoNTV($i1.index);
                }
                $r43 = addressItem;
                $r72 = $r43.getTitle();
                $r43 = addressItem;
                if ($r43.getCategory().intValue() == 7) {
                    $r43 = addressItem;
                    $r72 = $r43.getSecondaryTitle();
                }
                $r43 = addressItem;
                $r72 = $r43.getAddress();
                $r43 = addressItem;
                PlannedDriveActivity.setNavigationAddressItem($r43);
                $r5 = DriveToNativeManager.this;
                $r43 = addressItem;
                $i12 = $r43.getLocationX().intValue();
                $r43 = addressItem;
                $i2 = $r43.getLocationY().intValue();
                $r43 = addressItem;
                $r9 = $r43.getDealId();
                $i1 = addressItem;
                $r4 = $i1;
                $i3 = $i1.advPointId;
                $r7 = addressItem;
                $r42 = $r7;
                $r10 = $r7.VanueID;
                $r43 = addressItem;
                $r11 = $r43.getId();
                $r43 = addressItem;
                $r12 = $r43.getCountry();
                $r43 = addressItem;
                $r13 = $r43.getState();
                $r43 = addressItem;
                $r14 = $r43.getCity();
                $r43 = addressItem;
                $r15 = $r43.getStreet();
                $r43 = addressItem;
                $r5.navigateNTV($i12, $i2, $r72, $r9, $i3, $r10, $r11, $r12, $r13, $r14, $r15, $r43.getHouse(), $r3, z3);
            }
        });
    }

    public void OpenCalendarVerifyScreen(final AddressItem $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                if (NativeManager.getInstance().getAutoCompleteFeatures() != 0) {
                    AppService.getMainActivity().startNavigateActivity();
                    return;
                }
                Intent $r1 = new Intent(AppService.getActiveActivity(), AddFavoriteActivity.class);
                $r1.putExtra(PublicMacros.SEARCH_STRING_KEY, $r1.getAddress());
                $r1.putExtra(PublicMacros.ADDRESS_TYPE, 11);
                $r1.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
                AppService.getActiveActivity().startActivityForResult($r1, 1);
            }
        });
    }

    private void navigateAnalytics(final AddressItem $r1) throws  {
        if ($r1.getCategory().intValue() == 1) {
            instance.getTopTenFavorites(new DriveToGetAddressItemArrayCallback() {
                public void getAddressItemArrayCallback(AddressItem[] $r1) throws  {
                    if ($r1 != null) {
                        for (AddressItem $r2 : $r1) {
                            if ($r2.getId().equals($r1.getId())) {
                                Analytics.log("NAVIGATE", AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, "TOP_FAVORITE");
                                return;
                            }
                        }
                    }
                    Analytics.log("NAVIGATE", AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, "FAVORITE");
                }
            });
        }
        if ($r1.getCategory().intValue() == 3) {
            Analytics.log("NAVIGATE", AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, "SEARCH");
        }
        if ($r1.getCategory().intValue() == 3) {
            Analytics.log("NAVIGATE", AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, "SEARCH");
        }
        if ($r1.getCategory().intValue() == 3 && $r1.getCategoryDesc() != null) {
            Analytics.log("NAVIGATE", AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, AnalyticsEvents.ANALYTICS_CATEGORIES);
        }
        if ($r1.getCategory().intValue() == 2) {
            Analytics.log("NAVIGATE", AnalyticsEvents.ANALYTICS_EVENT_INFO_SOURCE, "HISTORY");
        }
    }

    public void StoreAddressItem(final AddressItem $r1, final boolean $z0) throws  {
        Logger.m36d("store ai running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                DriveToNativeManager.this.storeAddressItemInt($r1, $z0);
            }

            public void callback() throws  {
                Logger.m36d("store ai callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public void StoreAddressItems(final AddressItem[] $r1, final boolean $z0) throws  {
        Logger.m36d("store ais running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            public void event() throws  {
                DriveToNativeManager.this.storeAddressItemsInt($r1, $z0);
            }

            public void callback() throws  {
                Logger.m36d("store ais callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    private void storeAddressItemsInt(AddressItem[] $r1, boolean $z0) throws  {
        if ($r1.length != 2) {
            Logger.m38e("storeAddressItemsInt called with wrong number of :" + $r1.length);
            return;
        }
        Logger.m36d("store ai event running in thread " + Thread.currentThread().getId());
        Logger.m36d("ais= " + $r1);
        int $i0 = $r1[0].getLocationX().intValue();
        int $i2 = $r1[0].getLocationY().intValue();
        int $i3 = $r1[1].getLocationX().intValue();
        int $i4 = $r1[1].getLocationY().intValue();
        storeAddressItemsNTV($r1[0].getId(), $r1[0].getCategory().intValue(), $r1[0].getCity(), $r1[0].getStreet(), $r1[0].getHouse(), $r1[0].getState(), $r1[0].getTitle(), $i0, $i2, $r1[1].getId(), $r1[1].getCategory().intValue(), $r1[1].getCity(), $r1[1].getStreet(), $r1[1].getHouse(), $r1[1].getState(), $r1[1].getTitle(), $i3, $i4, $z0);
        Logger.m36d("after storeAddressItemsNTV in thread " + Thread.currentThread().getId());
    }

    public void forceWidgetRefresh() throws  {
        if (WazeAppWidgetProvider.isWidgetEnabled(AppService.getAppContext())) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    try {
                        WazeAppWidgetLog.d("in DriveToNativeManager :: forceWidgetRefresh");
                        WazeAppWidgetProvider.getControlIntent(AppService.getActiveActivity(), WazeAppWidgetService.APPWIDGET_ACTION_CMD_FORCE_REFRESH).send();
                    } catch (Exception $r1) {
                        Logger.m38e("failed firing widget refresh. exception:" + $r1.getMessage() + " trace:" + $r1.getStackTrace());
                    }
                }
            });
        }
    }

    public void eraseAddressItem(final AddressItem $r1) throws  {
        Logger.m36d("erase ai running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {

            class C21111 implements Runnable {
                C21111() throws  {
                }

                public void run() throws  {
                    AppService.getMainActivity().getLayoutMgr().refreshRecentsNavigationList();
                }
            }

            public void event() throws  {
                Logger.m36d("erase ai event running in thread " + Thread.currentThread().getId());
                Logger.m36d("ai= " + $r1);
                DriveToNativeManager.this.eraseAddressItemNTV($r1.getId(), $r1.getCategory().intValue(), $r1.getTitle());
                Logger.m36d("erase eraseAddressItemNTV in thread " + Thread.currentThread().getId());
            }

            public void callback() throws  {
                Logger.m36d("erase ai callback running in thread " + Thread.currentThread().getId());
                AppService.getMainActivity().post(new C21111());
            }
        });
    }

    public void getAddressItemAppData(final String $r1, final ObjectListener $r2) throws  {
        if ($r1 == null) {
            $r2.onComplete(null);
        }
        NativeManager.Post(new RunnableUICallback() {
            private AddressItemAppData data;

            public void event() throws  {
                try {
                    this.data = DriveToNativeManager.this.getAddressItemAppDataNTV($r1);
                } catch (Exception e) {
                    this.data = null;
                }
            }

            public void callback() throws  {
                $r2.onComplete(this.data);
            }
        });
    }

    public void updateAddressItemAppData(final String $r1, final AddressItemAppData $r2) throws  {
        if ($r1 != null) {
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    DriveToNativeManager.this.updateAddressItemAppDataNTV($r1, $r2);
                }
            });
        }
    }

    public static void openSearchActivity(final String $r0, final boolean $z0) throws  {
        Logger.m36d("openSearchActivity running in thread " + Thread.currentThread().getId());
        AppService.Post(new RunnableCallback(AppService.getNativeManager()) {
            public void event() throws  {
                Logger.m36d("openSearchActivity event running in thread " + Thread.currentThread().getId());
                Logger.m36d("address=" + $r0 + " autoNav=" + $z0);
                Intent $r1 = new Intent(AppService.getActiveActivity(), SearchResultsActivity.class);
                $r1.putExtra(PublicMacros.SEARCH_STRING_KEY, $r0);
                if ($z0) {
                    $r1.putExtra(PublicMacros.SEARCH_MODE, 5);
                } else {
                    $r1.putExtra(PublicMacros.SEARCH_MODE, 2);
                }
                if (!(AppService.getMainActivity() == null || AppService.getMainActivity().getLayoutMgr() == null)) {
                    AppService.getMainActivity().getLayoutMgr().unsetSideMenuSearchUpdateHandler();
                }
                AppService.getActiveActivity().startActivityForResult($r1, 1);
            }

            public void callback() throws  {
                Logger.m36d("openSearchActivity callback running in thread " + Thread.currentThread().getId());
            }
        });
    }

    public static void searchResults(String $r0, AddressItem $r1) throws  {
        Bundle $r2 = new Bundle();
        $r2.putString("provider", $r0);
        $r2.putSerializable("address_item", $r1);
        instance.handlers.sendUpdateMessage(UH_SEARCH_ADD_RESULT, $r2);
    }

    public static void nearbyGasStationsFound(NearbyStation[] $r0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putSerializable(GasPriceReport.NEARBY_STATIONS, $r0);
        instance.handlers.sendUpdateMessage(UH_NEARBY_STATIONS, $r1);
    }

    public void openPrivateMessage(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.openPrivateMessageNTV($i0);
            }
        });
    }

    public void VerifyEventWithNoAddress(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.VerifyEventWithNoAddressNTV($r1);
            }
        });
    }

    public void VerifyEventByIndex(int $i0, String $r1, String $r2, Boolean $r3) throws  {
        final Boolean bool = $r3;
        final int i = $i0;
        final String str = $r1;
        final String str2 = $r2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                if (bool.booleanValue()) {
                    DriveToNativeManager.this.VerifyEventAndDriveByIndexNTV(i, str, str2);
                } else {
                    DriveToNativeManager.this.VerifyEventByIndexNTV(i, str, str2);
                }
            }
        });
    }

    public void CalendarAddressVerifiedByIndex(final int $i0, final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.CalendarAddressVerifiedByIndexNTV($i0, $r1);
            }
        });
    }

    public void CalendarAddressVerified(String $r1, int $i0, int $i1, String $r2, String $r3) throws  {
        final String str = $r1;
        final int i = $i1;
        final int i2 = $i0;
        final String str2 = $r2;
        final String str3 = $r3;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.CalendarAddressVerifiedNTV(str, i, i2, str2, str3);
            }
        });
    }

    public void CalendarAddressRemove(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.CalendarAddressRemoveNTV($r1);
            }
        });
    }

    public void drive(final String $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.DriveEventNTV($r1, $z0);
            }
        });
    }

    public void setSkipConfirmWaypoint(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setSkipConfirmWaypointNTV($z0);
            }
        });
    }

    public static void finalizeSearch(String $r0) throws  {
        Logger.m36d("searchResults finalize. Active provider: " + $r0);
        Bundle $r1 = new Bundle();
        $r1.putString("provider", $r0);
        instance.handlers.sendUpdateMessage(UH_SEARCH_FINALIZE, $r1);
    }

    public void updateDangerZone(int $i0, int $i1, int $i2) throws  {
        Bundle $r1 = new Bundle();
        $r1.putInt("lon", $i0);
        $r1.putInt("lat", $i1);
        $r1.putInt("tv", $i2);
        this.handlers.sendUpdateMessage(UH_DANGER_ZONE_FOUND, $r1);
    }

    public static void updateEta(String $r0, String $r1, String $r2) throws  {
        Bundle $r3 = new Bundle();
        $r3.putString("provider", $r0);
        $r3.putString("distance", $r1);
        $r3.putString("id", $r2);
        instance.handlers.sendUpdateMessage(UH_ETA, $r3);
    }

    public void OpenFriendOnTheWayPopUp(final FriendUserData $r1, final int $i0) throws  {
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            final LayoutManager $r4 = $r3.getLayoutMgr();
            if ($r4 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r4.showFriendOnTheWayPopup($r1, $i0);
                    }
                });
            }
        }
    }

    public void OpenVerifyEvent(final AddressItem $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1 = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
                $r1.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
                AppService.getActiveActivity().startActivityForResult($r1, 1);
            }
        });
    }

    public static void searchFail(String $r0, String $r1, boolean $z0) throws  {
        Bundle $r2 = new Bundle();
        $r2.putString("provider", $r0);
        $r2.putString("errMsg", $r1);
        $r2.putBoolean("canRetry", $z0);
        instance.handlers.sendUpdateMessage(UH_SEARCH_FAIL, $r2);
    }

    public void onVenuePinWaitForVenuePreview() throws  {
        setUpdateHandler(UH_SEARCH_ADD_RESULT, new SearchResultHandler());
    }

    public void getLocationData(int $i0, Integer $r1, Integer $r2, LocationDataListener $r3, String $r4) throws  {
        final int i = $i0;
        final Integer num = $r1;
        final Integer num2 = $r2;
        final String str = $r4;
        final LocationDataListener locationDataListener = $r3;
        NativeManager.Post(new RunnableUICallback() {
            private LocationData locationData;

            public void event() throws  {
                try {
                    this.locationData = DriveToNativeManager.this.getLocationDataNTV(i, num.intValue(), num2.intValue(), str);
                } catch (Exception e) {
                    this.locationData = null;
                }
            }

            public void callback() throws  {
                locationDataListener.onComplete(this.locationData);
            }
        });
    }

    public void animationEnded() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.instance.handlers.sendUpdateMessage(DriveToNativeManager.UH_ANIMATION_ENDED, null);
            }
        });
    }

    public void getEventsOnRoute(final EventsOnRouteListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private EventOnRoute[] events;

            public void event() throws  {
                try {
                    this.events = DriveToNativeManager.this.getEventsOnRouteNTV();
                } catch (Exception e) {
                    this.events = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.events);
            }
        });
    }

    public void getAlertsOnRoute(final EventsOnRouteListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private EventOnRoute[] events;

            public void event() throws  {
                try {
                    this.events = DriveToNativeManager.this.getAlertsOnRouteNTV();
                } catch (Exception e) {
                    this.events = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.events);
            }
        });
    }

    public void getMajorEventsOnRoute(final MajorEventsOnRouteListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private MajorEventOnRoute[] events;

            public void event() throws  {
                try {
                    this.events = DriveToNativeManager.this.getMajorEventsOnRouteNTV();
                } catch (Exception e) {
                    this.events = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.events);
            }
        });
    }

    public void requestRoute(final boolean $z0) throws  {
        if (!this.mIsLoadingRoutes) {
            this.mIsLoadingRoutes = true;
            NativeManager.Post(new Runnable() {
                public void run() throws  {
                    DriveToNativeManager.this.requestRouteNTV($z0);
                }
            });
        }
    }

    public void getAlternativeRoutes(final AlternativeRoutesListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private AlternativeRoute[] routes;

            public void event() throws  {
                this.routes = DriveToNativeManager.this.getAlternativeRoutesNTV();
            }

            public void callback() throws  {
                $r1.onComplete(this.routes);
            }
        });
    }

    public void getTopTenFavorites(final DriveToGetAddressItemArrayCallback $r1) throws  {
        Logger.m36d("getTopTenFavorites running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            AddressItem[] ai = null;

            public void event() throws  {
                Logger.m36d("getTopTenFavorites event running in thread " + Thread.currentThread().getId());
                this.ai = DriveToNativeManager.this.getTopTenFavoritesNTV();
            }

            public void callback() throws  {
                Logger.m36d("getTopTenFavorites callback running in thread " + Thread.currentThread().getId());
                $r1.getAddressItemArrayCallback(this.ai);
            }
        });
    }

    public void getTopTenFavoritesUIThread(DriveToGetAddressItemArrayCallback $r1) throws  {
        $r1.getAddressItemArrayCallback(getTopTenFavoritesNTV());
    }

    public void loadingRoutesFinished() throws  {
        this.mIsLoadingRoutes = false;
    }

    public void OpenAlternativeRoutes() throws  {
        this.mIsLoadingRoutes = false;
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 != null) {
            $r1.startRoutesActivity();
        }
    }

    public void refreshAddressItemsIconsOnSearchActivity(final int $i0, final String $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                ActivityBase $r1 = AppService.getActiveActivity();
                if ($r1 instanceof SearchResultsActivity) {
                    Logger.m38e("refreshAddressItemsIconsOnSearchActivity callback running in thread " + Thread.currentThread().getId());
                    ((SearchResultsActivity) $r1).refreshAdressItemsIcons($i0, $r1);
                }
            }
        });
    }

    public void getSearchEngines(final String $r1, final DriveToGetSearchEnginesCallback $r2) throws  {
        Logger.m36d("getSearchEngines running in thread " + Thread.currentThread().getId());
        NativeManager.Post(new RunnableUICallback() {
            List<SearchEngine> enginesList = new LinkedList();

            public void event() throws  {
                Logger.m36d("getSearchEngines event running in thread " + Thread.currentThread().getId());
                SearchEngine[] $r5 = DriveToNativeManager.this.getSearchEnginesNTV($r1);
                for (int $i1 = 0; $i1 < $r5.length; $i1++) {
                    this.enginesList.add(($i1 % 2) * this.enginesList.size(), $r5[$i1]);
                }
            }

            public void callback() throws  {
                Logger.m36d("getSearchEngines callback running in thread " + Thread.currentThread().getId());
                $r2.getSearchEnginesCallback(this.enginesList);
            }
        });
    }

    public void selectAlternativeRoute(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.selectAlternativeRouteNTV($i0);
            }
        });
    }

    public void setProductPrices(final int $i0, final float[] $r1, final int[] $r2, final int $i1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setProductPricesNTV($i0, $r1, $r2, $i1);
            }
        });
    }

    public void onAlternativeRoutesClosed() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.onAlternativeRoutesClosedNTV();
            }
        });
    }

    public void requestStopPoint(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.requestStopPointNTV($i0);
            }
        });
    }

    public void InitMeeting(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.InitMeetingNTV($r1);
            }
        });
    }

    public void setMapMode(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setShareMapModeNTV($z0);
            }
        });
    }

    public void setMeeting(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setMeetingNTV($r1);
            }
        });
    }

    public void unsetMeeting() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.unsetMeetingNTV();
            }
        });
    }

    public void setStopPointPreview(final int $i0, final int $i1, final int $i2, String icon) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setStopPointPreviewNTV($i0, $i1, $i2);
            }
        });
    }

    public void setStartPoint(final AddressItem $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                if ($r1.venueData != null) {
                    DriveToNativeManager.this.setStartPointNTV($r1.venueData);
                } else {
                    DriveToNativeManager.this.setStartPointLLNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue());
                }
            }
        });
    }

    public void removeStartPoint() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.removeStartPointNTV();
            }
        });
    }

    public void showOnMap(final int $i0, final int $i1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.showOnMapNTV($i0, $i1);
            }
        });
    }

    public void showOnMap(final VenueData $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.showVenueOnMapNTV($r1);
            }
        });
    }

    public void centerMapInAddressOptionsView(int $i0, int $i1, int $i2, boolean $z0, String icon) throws  {
        final boolean z = $z0;
        final int i = $i0;
        final int i2 = $i1;
        final int i3 = $i2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                if (DriveToNativeManager.this.isStopPointSearchNTV() && z) {
                    DriveToNativeManager.this.setStopPointPreviewNTV(i, i2, i3);
                }
            }
        });
    }

    public void moveFavoriteAddressItem(final int $i0, final int $i1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.moveFavoriteAddressItemNTV($i0, $i1);
            }
        });
    }

    public void unsetSearchMapView() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.unsetSearchMapViewNTV();
            }
        });
    }

    public void setSearchResultPins(final String $r1, final String $r2, final String $r3) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setSearchResultPinsNTV($r1, $r2, $r3);
            }
        });
    }

    public void setCarpoolPins(final CarpoolDrive $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setCarpoolMapNTV($r1.getId(), $r1.getSomeRideId(), $z0);
            }
        });
    }

    public void cancelStopPointAndRemoveDeparturePoi() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setStopPointNTV(false);
                DriveToNativeManager.this.removeDeparturePoiNTV();
            }
        });
    }

    public void setSearchMode(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setSearchModeNTV($z0);
            }
        });
    }

    public void setStopPoint(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.setStopPointNTV($z0);
            }
        });
    }

    public void cancelStopPoint() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.cancelStopPointNTV();
            }
        });
    }

    public void searchNearbyStations() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.searchNearbyStationsNTV();
            }
        });
    }

    public void getSearchResultsEta(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.getSearchResultsEtaNTV($r1);
            }
        });
    }

    public void openWorkHome(int $i0, int $i1, int $i2, boolean $z0, String $r1, String $r2, String $r3, String $r4, String $r5, String $r6) throws  {
        final boolean z = $z0;
        final int i = $i1;
        final int i2 = $i2;
        final String str = $r1;
        final int i3 = $i0;
        final String str2 = $r5;
        final String str3 = $r2;
        final String str4 = $r3;
        final String str5 = $r4;
        final String str6 = $r6;
        AppService.Post(new Runnable() {
            public void run() throws  {
                int i = 1;
                String $r4 = "Home";
                if (z) {
                    i = 3;
                    $r4 = "Work";
                }
                int $i1 = i;
                Integer $r5 = Integer.valueOf($i1);
                $i1 = i2;
                Integer $r6 = Integer.valueOf($i1);
                String $r2 = str;
                Integer $r7 = Integer.valueOf(1);
                $i1 = i3;
                AddressItem addressItem = new AddressItem($r5, $r6, $r4, null, $r2, null, null, null, $r7, Integer.valueOf($i1).toString(), Integer.valueOf(i), null, null, null, null, null, str2, str3, str4, str5, null, str6);
                Intent intent = new Intent(AppService.getAppContext(), AddHomeWorkActivity.class);
                intent.putExtra("ai", addressItem);
                AppService.getActiveActivity().startActivity(intent);
            }
        });
    }

    public void setStreetNameShown(final boolean $z0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.setStreetNameShown($z0);
                    }
                });
            }
        }
    }

    public void setCurrentStreetName(final String $r1) throws  {
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 != null) {
            final LayoutManager $r4 = $r3.getLayoutMgr();
            if ($r4 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r4.setCurrentStreetName($r1);
                    }
                });
            }
        }
    }

    public void setStreetNameColors(final int $i0, final int $i1) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            final LayoutManager $r3 = $r2.getLayoutMgr();
            if ($r3 != null) {
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r3.setStreetNameColors($i0, $i1);
                    }
                });
            }
        }
    }

    public void renameFavorite(final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.renameFavoriteNTV($r1, $r2);
            }
        });
    }

    public void convertFavoriteToRecent(final String $r1, final Runnable $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.convertFavoriteToRecentNTV($r1);
                if ($r2 != null) {
                    $r2.run();
                }
            }
        });
    }

    public void searchBrands(final boolean $z0, final String $r1, final boolean $z1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.searchBrandsNTV($z0, $r1, $z1);
            }
        });
    }

    public boolean isDayMode() throws  {
        return isDayModeNTV();
    }

    public boolean isAutocompleteServerAds() throws  {
        return isAutocompleteServerAdsNTV();
    }

    public void addDangerZoneStat(final int $i0, final int $i1, final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.addDangerZoneStatNTV($i0, $i1, $r1, $r2);
            }
        });
    }

    public void loadPlannedDriveOptions(final int $i0, final int $i1, final int $i2, final int $i3, final int $i4, final String $r1, final String $r2, final String $r3, final String $r4, final String $r5, PlannedDriveOptionsListener $r6) throws  {
        this.mPlannedDriveOptionsListener = $r6;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.loadPlannedDriveOptionsNTV($i0, $i1, $i2, $i3, $i4, $r1, $r2, $r3, $r4, $r5);
            }
        });
    }

    public void loadCarpoolDriveOptions(final long $l0, final long $l1, final long $l2, final CarpoolRide $r1, PlannedDriveOptionsListener $r2) throws  {
        this.mPlannedDriveOptionsListener = $r2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.loadCarpoolDriveOptionsNTV($l0, $l1, $l2, $r1.getId());
            }
        });
    }

    public void onPlannedDriveOptionsLoaded(int[] $r1, int[] $r2, boolean $z0) throws  {
        if (this.mPlannedDriveOptionsListener != null) {
            this.mPlannedDriveOptionsListener.onPlannedDriveOptionsLoaded($r1, $r2, $z0);
        }
    }

    public void requestPlannedDriveEta(final String $r1, PlannedDriveListListener $r2) throws  {
        this.mPlannedDriveListListener = $r2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.requestPlannedDriveEtaNTV($r1);
            }
        });
    }

    public void onPlannedDriveEtaResponse(String $r1, int $i0) throws  {
        if (this.mPlannedDriveListListener != null) {
            this.mPlannedDriveListListener.onPlannedDriveEtaResponse($r1, $i0);
        }
    }

    public void removedPlannedDrive(final String $r1, PlannedDriveListListener $r2) throws  {
        this.mPlannedDriveListListener = $r2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.removePlannedDriveNTV($r1);
            }
        });
    }

    public void updatePlannedDrive(final String $r1, final long $l0, PlannedDriveOptionsListener $r2) throws  {
        this.mPlannedDriveOptionsListener = $r2;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.updatePlannedDriveNTV($r1, $l0);
            }
        });
    }

    public void createPlannedDrive(final String $r1, final int $i0, final int $i1, final String $r2, final String $r3, final String $r4, final String $r5, final String $r6, final long $l2, final String $r7, PlannedDriveOptionsListener $r8) throws  {
        this.mPlannedDriveOptionsListener = $r8;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.createPlannedDriveNTV($r1, $i0, $i1, $r2, $r3, $r4, $r5, $r6, $l2, $r7);
            }
        });
    }

    public void onPlannedDriveCreated(int $i0) throws  {
        if (this.mPlannedDriveOptionsListener != null && $i0 != 0) {
            this.mPlannedDriveOptionsListener.onPlannedDriveCreationFailed();
        }
    }

    public void onPlannedDriveResponse() throws  {
        if (this.mPlannedDriveOptionsListener != null) {
            this.mPlannedDriveOptionsListener.onPlannedDriveCreationSuccess();
        }
    }

    public void onPlannedDriveRemoved(int $i0) throws  {
        if (this.mPlannedDriveListListener == null) {
            return;
        }
        if ($i0 == 0) {
            this.mPlannedDriveListListener.onPlannedDriveRemoveSuccess();
        } else {
            this.mPlannedDriveListListener.onPlannedDriveRemoveFailed();
        }
    }

    public void onPlannedDriveUpdated(int $i0) throws  {
        if (this.mPlannedDriveOptionsListener == null) {
            return;
        }
        if ($i0 == 0) {
            this.mPlannedDriveOptionsListener.onPlannedDriveUpdatedSuccess();
        } else {
            this.mPlannedDriveOptionsListener.onPlannedDriveUpdatedFailed();
        }
    }

    public void playDueToTTS() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                DriveToNativeManager.this.playDueToTTSNTV();
            }
        });
    }
}
