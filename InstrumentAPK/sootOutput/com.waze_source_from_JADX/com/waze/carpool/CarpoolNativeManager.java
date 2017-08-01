package com.waze.carpool;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.facebook.share.internal.ShareConstants;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.ConfigManager;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.NativeManager.IResultOk;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.async.UpdateHandlers;
import com.waze.ifs.ui.ActivityBase;
import com.waze.menus.MainSideMenu;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.navigate.PublicMacros;
import com.waze.strings.DisplayStrings;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import dalvik.annotation.Signature;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class CarpoolNativeManager {
    public static final String BUNDLE_CREATE_USER_SUCCESS = "success";
    public static final String BUNDLE_EXT_INFO = "ext_info";
    public static final String BUNDLE_USER_DATA = "user";
    public static final String BUNDLE_USER_ID = "user_id";
    public static final int CarpoolRideStateEntry_CANCELLED_AFTER_DEADLINE = 6;
    public static final int CarpoolRideStateEntry_COMPLETED = 4;
    public static final int CarpoolRideStateEntry_CONFIRMED = 7;
    public static final int CarpoolRideStateEntry_DRIVER_ARRIVED = 16;
    public static final int CarpoolRideStateEntry_DRIVER_CANCELLED = 3;
    public static final int CarpoolRideStateEntry_DRIVER_REVIEWED = 12;
    public static final int CarpoolRideStateEntry_DRIVER_STARTED = 10;
    public static final int CarpoolRideStateEntry_ENTRY_UNSPECIFIED = 0;
    public static final int CarpoolRideStateEntry_FAILED_NO_DRIVER = 5;
    public static final int CarpoolRideStateEntry_FINDING_DRIVER = 1;
    public static final int CarpoolRideStateEntry_LAST = 17;
    public static final int CarpoolRideStateEntry_MISSED = 15;
    public static final int CarpoolRideStateEntry_PAX_CANCELLED = 2;
    public static final int CarpoolRideStateEntry_PAX_DROPPED_OFF = 9;
    public static final int CarpoolRideStateEntry_PAX_PICKED_UP = 8;
    public static final int CarpoolRideStateEntry_PAX_REVIEWED = 11;
    public static final int CarpoolRideStateEntry_REQUESTED = 13;
    public static final int CarpoolRideStateEntry_SYSTEM_CANCELLED = 14;
    public static final int DETOUR_MODE_NONE = 0;
    public static final int DETOUR_MODE_TIME = 1;
    public static final int DETOUR_MODE_TIME_DISTANCE = 2;
    public static final int DriverOnboardingStatus_ALL_DONE = 5;
    public static final int DriverOnboardingStatus_GOOGLE_CONNECTED = 1;
    public static final int DriverOnboardingStatus_PHASE_ONE = 2;
    public static final int DriverOnboardingStatus_PHASE_THREE = 4;
    public static final int DriverOnboardingStatus_PHASE_TWO = 3;
    public static final int DriverOnboardingStatus_UNKNOWN = 0;
    public static final int ENDORSEMENT_COOLPOOLER = 1;
    public static final int ENDORSEMENT_DRIVER_CAREFUL_DRIVER = 5;
    public static final int ENDORSEMENT_DRIVER_CLEAN_RIDE = 4;
    public static final int ENDORSEMENT_DRIVER_SWEET_RIDE = 6;
    public static final int ENDORSEMENT_PUNCTUAL = 2;
    public static final int ENDORSEMENT_QUICK_TO_RESPOND = 3;
    public static final int ENDORSEMENT_UNSPECIFIED = 0;
    public static final String INTENT_IS_HISTORY = "isHistory";
    public static final int IdProvederConnected_FACEBOOK = 2;
    public static final int IdProvederConnected_GOOGLE = 1;
    public static final int IdProvederConnected_UNKNOWN = 0;
    public static final int PROFILE_ERR_ALREADY_EXISTS = 3;
    public static final int PROFILE_ERR_LAST = 9;
    public static final int PROFILE_ERR_MALFORMED_EMAIL_ADDRESS = 5;
    public static final int PROFILE_ERR_MISSING_EMAIL = 2;
    public static final int PROFILE_ERR_MISSING_GAIA_ID = 1;
    public static final int PROFILE_ERR_MISSING_NAME = 8;
    public static final int PROFILE_ERR_NOT_AN_ACCEPTABLE_NAME = 7;
    public static final int PROFILE_ERR_PHONE_NUMBER_ALREADY_IN_USE = 4;
    public static final int PROFILE_ERR_UNKNOWN = 0;
    public static final int PROFILE_ERR_WORK_EMAIL_DOMAIN_MUST_NOT_BE_PUBLIC = 6;
    public static final int RIDE_LOCATION_DESTINATION = 4;
    public static final int RIDE_LOCATION_DROP_OFF = 3;
    public static final int RIDE_LOCATION_ORIGIN = 1;
    public static final int RIDE_LOCATION_PICKUP = 2;
    public static int UH_BANK_ACCOUNT_SENT = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_BLOCK_USER_RES = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_CHAT_MESSAGE = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_CHAT_MESSAGE_READ = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_CHAT_MESSAGE_SENT_STATUS = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_CLEAR_CHAT_RES = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_CLOSE_RIDE_DETAILS = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_DRIVES_UPDATED = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_DRIVE_CREATED = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_DRIVE_REMOVED = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_DRIVE_UPDATED = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_ERROR = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_EXT_USER = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_MESSAGES_LOADED = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_PAYMENT_PAYEE = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_PAYMENT_URL = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_RATE_RIDER_RES = TicketRoller.get(Type.Handler);
    public static final int UH_CARPOOL_REJECT_RIDE_RES = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_REPORT_USER_RES = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_RIDES_COUNTED = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_TOKEN = TicketRoller.get(Type.Handler);
    public static int UH_CARPOOL_USER = TicketRoller.get(Type.Handler);
    public static int UH_HISTORY_LOADED = TicketRoller.get(Type.Handler);
    public static int UH_IS_AGE_OK = TicketRoller.get(Type.Handler);
    public static final String UH_KEY_MSG_ID = "ride_msg_send_status";
    public static final String UH_KEY_RIDE_ID = "ride_id";
    public static final String UH_KEY_RIDE_MSG = "ride_msg";
    public static final String UH_KEY_RIDE_SEND_MSG_SUCCESS = "ride_msg_send_status";
    public static final int UserSocialNetworkType_FACEBOOK = 0;
    public static final int UserSocialNetworkType_LINKEDIN = 1;
    public static final int _ENDORSEMENT_NUM_OF = 6;
    public static final int _RIDE_LOCATION_NONE = 0;
    public static final int _RIDE_LOCATION_NUM_OF = 4;
    private static CarpoolNativeManager mInstance = null;
    private UpdateHandlers handlers = new UpdateHandlers();
    private BottomSheet mSheet;
    private boolean mTuneupQuestionFlag = false;

    public interface IResultObj<T> {
        void onResult(@Signature({"(TT;)V"}) T t) throws ;
    }

    public interface CarpoolMessagesReceiveCompleteListener {
        void onComplete(CarpoolRideMessages carpoolRideMessages) throws ;
    }

    public static class CarColors implements Serializable {
        private static final long serialVersionUID = 1;
        String[] colorNames;
        int[] colorValues;
    }

    public static class CarpoolEmailWhitelist {
        public String[] companies;
        public String[] domains;
    }

    public class CarpoolRideMeetingIds {
        public String dropOffMeetingId;
        public String pickupMeetingId;
    }

    public static class CarpoolRidePickupMeetingDetails implements Parcelable {
        public static final Creator<CarpoolRidePickupMeetingDetails> CREATOR = new C14731();
        public String meetingId;
        public String[] meetingImagesUrl;
        public String meetingPlaceName;
        public long meetingStartTime = 0;
        public String meetingTitle;
        public int numPax;

        static class C14731 implements Creator<CarpoolRidePickupMeetingDetails> {
            C14731() throws  {
            }

            public CarpoolRidePickupMeetingDetails createFromParcel(Parcel $r1) throws  {
                return new CarpoolRidePickupMeetingDetails($r1);
            }

            public CarpoolRidePickupMeetingDetails[] newArray(int $i0) throws  {
                return new CarpoolRidePickupMeetingDetails[$i0];
            }
        }

        public int describeContents() throws  {
            return 0;
        }

        public CarpoolRidePickupMeetingDetails(long $l0, String[] $r1, String $r2, String $r3, String $r4, int $i1) throws  {
            this.meetingStartTime = $l0;
            this.meetingImagesUrl = $r1;
            this.meetingTitle = $r2;
            this.meetingPlaceName = $r3;
            this.meetingId = $r4;
            this.numPax = $i1;
        }

        protected CarpoolRidePickupMeetingDetails(Parcel $r1) throws  {
            this.meetingStartTime = $r1.readLong();
            this.meetingImagesUrl = $r1.createStringArray();
            this.meetingTitle = $r1.readString();
            this.meetingPlaceName = $r1.readString();
            this.meetingId = $r1.readString();
            this.numPax = $r1.readInt();
        }

        public void writeToParcel(Parcel $r1, int flags) throws  {
            $r1.writeLong(this.meetingStartTime);
            $r1.writeStringArray(this.meetingImagesUrl);
            $r1.writeString(this.meetingTitle);
            $r1.writeString(this.meetingPlaceName);
            $r1.writeString(this.meetingId);
            $r1.writeInt(this.numPax);
        }
    }

    public static class DriveUpdates {
        CarpoolRide[] ridesJoined;
        CarpoolRide[] ridesLeft;
    }

    public static class LastChatMsgDetails {
        CarpoolMessage msg;
        CarpoolRide ride;

        public LastChatMsgDetails(CarpoolMessage $r1, CarpoolRide $r2) throws  {
            this.msg = $r1;
            this.ride = $r2;
        }
    }

    public static class PaymentFieldValidators implements Serializable {
        private static final long serialVersionUID = 1;
        public String account;
        public String branch;
    }

    public interface PickDestinationCompleteListener {
        void onComplete() throws ;
    }

    public interface PickupBarDataCompleteListener {
        void onComplete(CarpoolPickupbarData carpoolPickupbarData) throws ;
    }

    public interface getMeetingDetailsForPickupReceiveCompleteListener {
        void onComplete(CarpoolRidePickupMeetingDetails carpoolRidePickupMeetingDetails) throws ;
    }

    private native void CarpoolRequestRideMessagesNTV(String str) throws ;

    private native void InitNativeLayerNTV() throws ;

    private native void RefreshListOfRidesNTV() throws ;

    private native void blockUserNTV(String str) throws ;

    private native boolean cancelRideOfferNTV(String str) throws ;

    private native boolean checkDriverArrivedNTV(String str) throws ;

    private native void checkUserAgeNTV(String str) throws ;

    private native void clearChatHistoryNTV(String str) throws ;

    private native void clearWorkEmailNTV() throws ;

    private native boolean confirmRideRequestNTV(String str, long j, boolean z, boolean z2) throws ;

    private native void createUserRequestNTV(int i, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, int i2, String str10, String str11) throws ;

    private native boolean deleteDriveNTV(String str, String str2) throws ;

    private native AddressItem driveGetAddressItemNTV(String str, int i) throws ;

    private native boolean driverArrivedNTV(String str) throws ;

    private native boolean drivesListAvailableNTV(boolean z) throws ;

    private native int getCarpoolProfileCompletionPercentageNTV() throws ;

    private native CarpoolRideMessages getCarpoolRideMessagesNTV(String str) throws ;

    private native void getCommunityTokenNTV() throws ;

    private native int getDetourDisplayModeNTV() throws ;

    private native CarpoolDrive getDriveByMeetingIdNTV(String str) throws ;

    private native CarpoolDrive getDriveByRideIdNTV(String str) throws ;

    private native CarpoolDrive getDriveInProgressNTV() throws ;

    private native DriveUpdates getDriveUpdatesNTV(String str, String str2, boolean z) throws ;

    private native CarpoolDrive[] getDrivesNTV(boolean z) throws ;

    private native void getExtUserObjectNTV(String str) throws ;

    private native int getIdProviderTypeNTV() throws ;

    private native CarpoolDrive getLiveOrUpcomingDriveNTV() throws ;

    private native CarpoolRidePickupMeetingDetails getMeetingDetailsForPickupNTV() throws ;

    private native String getMeetingIdByDriveIdNTV(String str, boolean z) throws ;

    private native String getNextMeetingIdNTV() throws ;

    private native void getPayeeFormNTV() throws ;

    private native void getPayeeNTV() throws ;

    private native int getRideCountNTV(boolean z) throws ;

    private native int getRideOffersCountNTV() throws ;

    private native boolean getRidesHistoryNTV() throws ;

    private native boolean getRwRtDontShowAgainValueNTV() throws ;

    private native CarpoolRideState[] getStateHistoryNTV(String str) throws ;

    private native int getUnreadCarpoolDriveMessagesCountNTV(String str) throws ;

    private native int getUnreadCarpoolRideMessagesCountNTV(String str) throws ;

    private native CarpoolEmailWhitelist getWhilelistEmailNTV() throws ;

    private native void idProviderConnectedNTV(int i) throws ;

    private native boolean isCarColorMandatoryNTV() throws ;

    private native boolean isCarMakeMandatoryNTV() throws ;

    private native boolean isCarModelMandatoryNTV() throws ;

    private native boolean isCarPictureMandatoryNTV() throws ;

    private native boolean isCarPlateMandatoryNTV() throws ;

    private native boolean isCurMeetingPickupNTV() throws ;

    private native boolean isDriverOnboardedNTV() throws ;

    private native boolean isMessagingEnabledNTV() throws ;

    private native boolean isRiderBlockedNTV(String str) throws ;

    private native boolean isUserPictureMandatoryNTV() throws ;

    private native boolean isUserWorkEmailMandatoryNTV() throws ;

    private native void manualRideDriveToDropoffNTV(String str, boolean z) throws ;

    private native void manualRideDriveToMeetingWithPickupNTV(String str, String str2, boolean z) throws ;

    private native void manualRideNavigateToDestinationNTV(String str, boolean z) throws ;

    private native void markCarpoolRideMessagesReadNTV(String str, long j) throws ;

    private native void navMenuPromoClosedNTV() throws ;

    private native void navMenuPromoSeenThisSessionNTV() throws ;

    private native boolean needUserAttentionNTV() throws ;

    private native boolean offerRideNTV(int i, int i2, String str, int i3, int i4, String str2, long j, boolean z) throws ;

    private native boolean quitCarpoolNTV() throws ;

    private native boolean rejectRideRequestNTV(String str, String str2) throws ;

    private native boolean removeRideFromDriveNTV(String str, String str2, boolean z, String str3) throws ;

    private native void reportUserNTV(String str, String str2, int i, String str3, boolean z) throws ;

    private native void resendWorkEmailNTV() throws ;

    private native AddressItem rideGetAddressItemNTV(String str, int i) throws ;

    private native boolean riderDroppedOffNTV(String str) throws ;

    private native boolean riderOnboardedNTV(String str) throws ;

    private native boolean safeDriveToMeetingNTV(String str, String str2, boolean z) throws ;

    private native void sendCarpoolRideMessagesNTV(String str, String str2) throws ;

    private native boolean sendMultiRatingNTV(String str, String str2, int[] iArr, int[] iArr2) throws ;

    private native boolean sendRatingNTV(String str, String str2, String str3, int i, int i2) throws ;

    private native void setAvaliableSeatsNTV(int i) throws ;

    private native void setManualRideTakeoverExpandedNTV(boolean z) throws ;

    private native void setManualRideTickerShownNTV(boolean z) throws ;

    private native void setMottoNTV(String str) throws ;

    private native void setRwRtDontShowAgainValueNTV(boolean z) throws ;

    private native void setWorkEmailNTV(String str) throws ;

    private native void settingsHelpClickedNTV() throws ;

    private native void settingsProblemClickedNTV() throws ;

    private native boolean submitSurveyNTV(String str, String str2, String str3, String str4) throws ;

    public static native void test_endorsementsEnumNTV(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8, int i9) throws ;

    public static native void test_rideStateEnum() throws ;

    private native void updateCarProfileNTV(String str, String str2, String str3, int i, String str4, String str5) throws ;

    private native boolean updateCommuteModelDayNTV(int i, int i2, int i3, int i4, int i5) throws ;

    private native void updateGetRidesRequestsNTV(boolean z) throws ;

    private native void updateNotificationFrequencyNTV(int i) throws ;

    public native String CarpoolServerURLNTV() throws ;

    public native String LHLearnMoreURL() throws ;

    public native String LHManageURL() throws ;

    public native String[] configGetBankNamesNTV() throws ;

    public native String[] configGetCancelReasonsNTV() throws ;

    public native CarColors configGetCarColorsNTV() throws ;

    public native String[] configGetDeclineReasonsNTV() throws ;

    public native String configGetNoShowCancelReasonNTV() throws ;

    public native int configGetOpenTimeLeftNTV() throws ;

    public native PaymentFieldValidators configGetPaymentFieldValidatorsNTV() throws ;

    public native String configGetPaymentTermsUrlNTV() throws ;

    public native String configGetPrivacyNoticeUrlNTV() throws ;

    public native String configGetRideListTimeFormatHistoryNTV() throws ;

    public native String configGetRideListTimeFormatNTV() throws ;

    public native String configGetRideWithTermsUrlNTV() throws ;

    public native String configGetSurveyUrlNTV() throws ;

    public native boolean configIsOnNTV() throws ;

    public native boolean configShouldAskCancelReasonNTV() throws ;

    public native boolean configShouldAskDeclineReasonNTV() throws ;

    public native CarpoolPayee getCachedPayeeNTV() throws ;

    public native CarpoolUserData getCarpoolProfileNTV() throws ;

    public native String getCurMeetingIdNTV() throws ;

    public native CarpoolDrive getEarliestDriveRequestNTV() throws ;

    public native int getEarlyCancelTimeNTV() throws ;

    public native String getLocaleNTV() throws ;

    public native int getMinReminderEta() throws ;

    public native boolean hasCarpoolProfileNTV() throws ;

    public native boolean isCarpoolShareOnly() throws ;

    public native boolean isDriveLiveNTV(String str) throws ;

    public native boolean isDriveUpcomingNTV(String str) throws ;

    public native void openCarpoolTakeoverIfNecessaryNTV() throws ;

    public native void openCarpoolTakeoverNTV() throws ;

    public native void refreshUserObjectNTV() throws ;

    public native boolean rideOfferTypeIsStripNTV() throws ;

    public native boolean rideOfferTypeIsTipNTV() throws ;

    public native void rideOfferWasShownNTV() throws ;

    public void setUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.setUpdateHandler($i0, $r1);
    }

    public void unsetUpdateHandler(int $i0, Handler $r1) throws  {
        this.handlers.unsetUpdateHandler($i0, $r1);
    }

    public boolean isMessagingEnabled() throws  {
        return isMessagingEnabledNTV();
    }

    public boolean isRiderBlocked(String $r1) throws  {
        return isRiderBlockedNTV($r1);
    }

    public void setManualRideTakeoverExpanded(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.setManualRideTakeoverExpandedNTV($z0);
            }
        });
    }

    public void setManualRideTickerOpen(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.setManualRideTickerShownNTV($z0);
            }
        });
    }

    public void manualRideNavigateToDestination(final String $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.manualRideNavigateToDestinationNTV($r1, $z0);
            }
        });
    }

    public void checkDriverArrived(final String $r1, final IResultOk $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final boolean $z0 = CarpoolNativeManager.this.checkDriverArrivedNTV($r1);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r2.onResult($z0);
                    }
                });
            }
        });
    }

    public void manualRideDriveToMeetingWithPickup(final String $r1, final String $r2, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.manualRideDriveToMeetingWithPickupNTV($r1, $r2, $z0);
            }
        });
    }

    public void manualRideDriveToDropoff(final String $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.manualRideDriveToDropoffNTV($r1, $z0);
            }
        });
    }

    public AddressItem rideGetAddressItem(CarpoolRide $r1, int $i0) throws  {
        return rideGetAddressItemNTV($r1.getId(), $i0);
    }

    public AddressItem driveGetAddressItem(CarpoolDrive $r1, int $i0) throws  {
        return driveGetAddressItemNTV($r1.getId(), $i0);
    }

    public void getLiveDrive(@Signature({"(", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final IResultObj<CarpoolDrive> $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final CarpoolDrive $r1 = CarpoolNativeManager.this.getDriveInProgressNTV();
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r1.onResult($r1);
                    }
                });
            }
        });
    }

    public void getUpcomingDrive(@Signature({"(", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final IResultObj<CarpoolDrive> $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final CarpoolDrive $r1 = CarpoolNativeManager.this.getLiveOrUpcomingDriveNTV();
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r1.onResult($r1);
                    }
                });
            }
        });
    }

    public void startOnboarding() throws  {
        final MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            CarpoolOnboardingManager $r3 = CarpoolOnboardingManager.getInstance();
            $r3.setIsCalledFromPush(true);
            if (CarpoolOnboardingManager.isDriverOnboarded()) {
                Logger.m36d("CarpoolNativeManager: startOnboarding: Driver already OB; opening RSM");
                $r2.openCarpoolSideMenu();
                return;
            }
            C14729 $r1 = new INextActionCallback() {
                public void setNextIntent(final Intent $r1) throws  {
                    Logger.m36d("CarpoolNativeManager:startOnboarding: received intent " + $r1.toString());
                    AppService.Post(new Runnable() {
                        public void run() throws  {
                            $r2.startActivity($r1);
                        }
                    });
                }

                public void setNextFragment(Fragment $r1) throws  {
                    Logger.m38e("CarpoolNativeManager: startOnboarding: received unexpected setNextFragment " + $r1.getClass().getName());
                }

                public void setNextResult(int $i0) throws  {
                    Logger.m38e("CarpoolNativeManager: startOnboarding: received unexpected setNextResult " + $i0);
                }

                public Context getContext() throws  {
                    return $r2;
                }
            };
            Logger.m38e("CarpoolNativeManager: Calling get next");
            $r3.getNext(CarpoolOnboardingManager.START_JOIN_DIRECTLY, $r1);
        }
    }

    public void getDriveUpdates(@Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolNativeManager$DriveUpdates;", ">;)V"}) final CarpoolDrive $r1, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolNativeManager$DriveUpdates;", ">;)V"}) final boolean $z0, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolNativeManager$DriveUpdates;", ">;)V"}) final IResultObj<DriveUpdates> $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final DriveUpdates $r6 = CarpoolNativeManager.this.getDriveUpdatesNTV($r1.getId(), $r1.getRidesAmount() > 0 ? $r1.getRide().getId() : "", $z0);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r2.onResult($r6);
                    }
                });
            }
        });
    }

    public static CarpoolNativeManager getInstance() throws  {
        create();
        return mInstance;
    }

    public static CarpoolNativeManager create() throws  {
        if (mInstance == null) {
            mInstance = new CarpoolNativeManager();
            mInstance.initNativeLayer();
        }
        return mInstance;
    }

    public String centsToString(Context ctx, int $i0, String $r4, String $r2) throws  {
        if (ConfigManager.getInstance().getConfigValueBool(15)) {
            CarpoolPayee $r6 = getCachedPayeeNTV();
            if ($r6 != null) {
                $r2 = $r6.currency_code;
            }
        }
        Locale.getDefault();
        Currency $r7 = null;
        if ($r4 == null || $r4.isEmpty()) {
            $r4 = getLocaleNTV();
        }
        if ($r2 != null) {
            try {
                $r7 = Currency.getInstance($r2);
            } catch (Exception e) {
            }
        }
        if ($r7 == null) {
            if (!($r4 == null || $r4.isEmpty())) {
                try {
                    try {
                        $r7 = Currency.getInstance(new Locale("", $r4));
                    } catch (Exception e2) {
                    }
                } catch (Exception e3) {
                }
            }
            if ($r7 == null) {
                $r7 = Currency.getInstance(new Locale("he", "IL"));
            }
        }
        double $d0 = ((double) $i0) / Math.pow(10.0d, (double) $r7.getDefaultFractionDigits());
        NumberFormat $r8 = NumberFormat.getCurrencyInstance();
        $r8.setCurrency($r7);
        return $r8.format($d0);
    }

    private void initNativeLayer() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.InitNativeLayerNTV();
            }
        });
    }

    public void checkUserAge(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.checkUserAgeNTV($r1);
            }
        });
    }

    public void getExtUserObject(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.getExtUserObjectNTV($r1);
            }
        });
    }

    public void createUser(final int $i0, final String $r1, final String $r2, final String $r3, final String $r4, final String $r5, final String $r6, final String $r7, final String $r8, final String $r9, final int $i1, final String $r10, final String $r11) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.createUserRequestNTV($i0, $r1, $r2, $r3, $r4, $r5, $r6, $r7, $r8, $r9, $i1, $r10, $r11);
            }
        });
    }

    public void RefreshListOfRides() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.RefreshListOfRidesNTV();
            }
        });
    }

    public void getPayeeForm() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.getPayeeFormNTV();
            }
        });
    }

    public void getPayee() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Logger.m36d("Payoneer: requesting payee");
                CarpoolNativeManager.this.getPayeeNTV();
            }
        });
    }

    public void updateCarProfile(final String $r1, final String $r2, final String $r3, final int $i0, final String $r4, final String $r5) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.updateCarProfileNTV($r1, $r2, $r3, $i0, $r4, $r5);
            }
        });
    }

    public void updateGetRidesRequests(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.updateGetRidesRequestsNTV($z0);
            }
        });
    }

    public void updateNotificationFrequency(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.updateNotificationFrequencyNTV($i0);
            }
        });
    }

    public void updateCarpoolDot(boolean $z0) throws  {
        if (!$z0) {
            AppService.Post(new Runnable() {
                public void run() throws  {
                    MainActivity $r1 = AppService.getMainActivity();
                    if ($r1 != null) {
                        LayoutManager $r2 = $r1.getLayoutMgr();
                        if ($r2 != null) {
                            $r2.setRidewithDot();
                        }
                    }
                }
            });
        }
    }

    public void getDrives(@Signature({"(Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<[", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final boolean $z0, @Signature({"(Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<[", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final IResultObj<CarpoolDrive[]> $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final CarpoolDrive[] $r1 = CarpoolNativeManager.this.getDrivesNTV($z0);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r1.onResult($r1);
                    }
                });
            }
        });
    }

    public void postDrivesUpdated(final CarpoolDrive[] $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Logger.m36d("postDrivesUpdated: drives ready");
                Bundle $r1 = new Bundle();
                $r1.putParcelableArray("drives", $r1);
                CarpoolNativeManager.this.handlers.sendUpdateMessage(CarpoolNativeManager.UH_CARPOOL_DRIVES_UPDATED, $r1);
            }
        });
    }

    public boolean drivesListAvailable(boolean $z0) throws  {
        if (mInstance != null) {
            return drivesListAvailableNTV($z0);
        }
        create();
        return false;
    }

    public void OpenRideScreen(final CarpoolDrive $r1, final String $r2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1;
                if ($r1 == null) {
                    Logger.m38e("Unavailable: CP Native manager: drive : " + $r1);
                    $r1 = new Intent(AppService.getActiveActivity(), RideUnavailableActivity.class);
                    if ($r2 != null) {
                        $r1.putExtra(RideUnavailableActivity.RIDE_ID, $r2);
                    }
                    AppService.getActiveActivity().startActivityForResult($r1, 1);
                    return;
                }
                $r1 = new Intent(AppService.getActiveActivity(), CarpoolRideDetailsActivity.class);
                CarpoolRide $r6 = $r1.getRideById($r2);
                $r1.putExtra(CarpoolDrive.class.getSimpleName(), $r1);
                $r1.putExtra(CarpoolRide.class.getSimpleName(), $r6);
                AppService.getActiveActivity().startActivityForResult($r1, PublicMacros.CARPOOL_RIDE_DETAILS_REQUEST_CODE);
            }
        });
    }

    public void OpenRiderScreen(final CarpoolDrive $r1, final String $r2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                Intent $r1;
                if ($r1 == null) {
                    Logger.m38e("Unavailable: CP Native manager: drive : " + $r1);
                    $r1 = new Intent(AppService.getActiveActivity(), RideUnavailableActivity.class);
                    if ($r2 != null) {
                        $r1.putExtra(RideUnavailableActivity.RIDE_ID, $r2);
                    }
                    AppService.getActiveActivity().startActivityForResult($r1, 1);
                    return;
                }
                $r1 = new Intent(AppService.getActiveActivity(), CarpoolRideDetailsActivity.class);
                $r1.putExtra(CarpoolDrive.class.getSimpleName(), $r1);
                $r1.putExtra("OpenRider", true);
                AppService.getActiveActivity().startActivityForResult($r1, PublicMacros.CARPOOL_RIDE_DETAILS_REQUEST_CODE);
            }
        });
    }

    public void confirmRideRequest(CarpoolRide $r1, long $l0, boolean $z0, boolean $z1) throws  {
        final CarpoolRide carpoolRide = $r1;
        final long j = $l0;
        final boolean z = $z1;
        final boolean z2 = $z0;
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.confirmRideRequestNTV(carpoolRide.uuid, j, z, z2);
            }
        });
        ((NotificationManager) AppService.getActiveActivity().getSystemService("notification")).cancel($r1.uuid, 2);
    }

    public void rejectRideRequest(final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.rejectRideRequestNTV($r1, $r2);
            }
        });
        ((NotificationManager) AppService.getActiveActivity().getSystemService("notification")).cancel($r1, 2);
    }

    private void riderOnboarded(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.riderOnboardedNTV($r1);
            }
        });
    }

    private void riderDroppedOff(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.riderDroppedOffNTV($r1);
            }
        });
    }

    public void driverArrived(final CarpoolDrive $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.driverArrivedNTV($r1.getId());
            }
        });
    }

    public void deleteDrive(final CarpoolDrive $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.deleteDriveNTV($r1.uuid, $r2);
            }
        });
    }

    public void removeRideFromDrive(final CarpoolDrive $r1, final String $r2, final boolean $z0, final String $r3) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.removeRideFromDriveNTV($r1.uuid, $r2, $z0, $r3);
            }
        });
    }

    public void blockUser(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.blockUserNTV($r1);
            }
        });
    }

    public void reportUser(final String $r1, final String $r2, final int $i0, final String $r3, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.reportUserNTV($r1, $r2, $i0, $r3, $z0);
            }
        });
    }

    public void clearChatHistory(final CarpoolRide $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.clearChatHistoryNTV($r1.getId());
            }
        });
    }

    public void getRidesHistory() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.getRidesHistoryNTV();
            }
        });
    }

    public void offerRide(final int $i0, final int $i1, final String $r1, final int $i2, final int $i3, final String $r2, final long $l4, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.offerRideNTV($i0, $i1, $r1, $i2, $i3, $r2, $l4, $z0);
            }
        });
    }

    public void cancelRideOffer(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.cancelRideOfferNTV($r1);
            }
        });
    }

    public boolean isContactRiderAllowed(CarpoolDrive $r1, CarpoolRide $r2) throws  {
        if ($r1 == null) {
            Logger.m38e("CPNM: isContactRiderAllowed: drive is null!");
            return false;
        }
        int $i0 = $r1.getState($r2);
        if ($i0 == 7 || $i0 == 10 || $i0 == 16 || $i0 == 8 || $i0 == 4 || $i0 == 2 || $i0 == 3 || $i0 == 9) {
            return true;
        }
        MsgBox.openMessageBox(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CARPOOL_CONTACTING_NOT_ALLOWED_STATE_TITLE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_CARPOOL_CONTACTING_NOT_ALLOWED_STATE), false);
        return false;
    }

    public void sendRating(final CarpoolDrive $r1, final CarpoolRide $r2, final CarpoolUserData $r3, final int $i0, final int $i1, final IResultOk $r4) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                boolean $z0 = CarpoolNativeManager.this.sendRatingNTV($r1.getId(), $r2.getId(), $r3.getId(), $i0, $i1);
                IResultOk $r8 = $r4;
                NativeManager.postResultOk($r8, $z0);
            }
        });
    }

    public void sendMultiRating(final CarpoolDrive $r1, final int[] $r2, final int[] $r3, final IResultOk $r4) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NativeManager.postResultOk($r4, CarpoolNativeManager.this.sendMultiRatingNTV($r1.getId(), $r1.getSomeRideId(), $r2, $r3));
            }
        });
    }

    public void onRateRiderResult(int $i0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putInt("res", $i0);
        this.handlers.sendUpdateMessage(UH_CARPOOL_RATE_RIDER_RES, $r1);
    }

    public boolean isUserPictureMandatory() throws  {
        return isUserPictureMandatoryNTV();
    }

    public boolean isUserWorkEmailMandatory() throws  {
        return isUserWorkEmailMandatoryNTV();
    }

    public boolean isCarPictureMandatory() throws  {
        return isCarPictureMandatoryNTV();
    }

    public boolean isCarMakeMandatory() throws  {
        return isCarMakeMandatoryNTV();
    }

    public boolean isCarModelMandatory() throws  {
        return isCarModelMandatoryNTV();
    }

    public boolean isCarColorMandatory() throws  {
        return isCarColorMandatoryNTV();
    }

    public boolean isCarPlateMandatory() throws  {
        return isCarPlateMandatoryNTV();
    }

    public int getDetourDisplayMode() throws  {
        return getDetourDisplayModeNTV();
    }

    public void setWorkEmail(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.setWorkEmailNTV($r1);
            }
        });
    }

    public void resendWorkEmail() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.resendWorkEmailNTV();
            }
        });
    }

    public void clearWorkEmail() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.clearWorkEmailNTV();
            }
        });
    }

    public void setMotto(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.setMottoNTV($r1);
            }
        });
    }

    public void setAvaliableSeats(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.setAvaliableSeatsNTV($i0);
            }
        });
    }

    public void quitCarpool(final IResultOk $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                boolean $z0 = CarpoolNativeManager.this.quitCarpoolNTV();
                if ($z0) {
                    NativeManager.postResultOk($r1, $z0);
                }
            }
        });
    }

    public void updateCommuteModelDay(final int $i0, final int $i1, final int $i2, final int $i3, final int $i4) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.updateCommuteModelDayNTV($i0, $i1, $i2, $i3, $i4);
            }
        });
    }

    public void submitSurvey(final CarpoolDrive $r1, final CarpoolRide $r2, final String $r3, final IResultOk $r4) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NativeManager.postResultOk($r4, CarpoolNativeManager.this.submitSurveyNTV($r1.getId(), $r2.getId(), $r2.rider.id, $r3));
            }
        });
    }

    public void settingsHelpClicked() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.settingsHelpClickedNTV();
            }
        });
    }

    public void settingsProblemClicked() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.settingsProblemClickedNTV();
            }
        });
    }

    public void GetCommunityToken() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.getCommunityTokenNTV();
            }
        });
    }

    public void safeDriveToMeeting(final String $r1, final boolean $z0, final IResultOk $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                NativeManager.postResultOk($r2, CarpoolNativeManager.this.safeDriveToMeetingNTV($r1, null, $z0));
            }
        });
    }

    public void getDriveInfoByMeetingId(@Signature({"(", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final String $r1, @Signature({"(", "Ljava/lang/String;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final IResultObj<CarpoolDrive> $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final CarpoolDrive $r2 = CarpoolNativeManager.this.getDriveByMeetingIdNTV($r1);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r2.onResult($r2);
                    }
                });
            }
        });
    }

    public void getMeetingIdByDrive(@Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/String;", ">;)V"}) final CarpoolDrive $r1, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/String;", ">;)V"}) final boolean $z0, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Z", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/String;", ">;)V"}) final IResultObj<String> $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final String $r3 = CarpoolNativeManager.this.getMeetingIdByDriveIdNTV($r1.getId(), $z0);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r2.onResult($r3);
                    }
                });
            }
        });
    }

    public void getMeetingIdsByDriveId(@Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolNativeManager$CarpoolRideMeetingIds;", ">;)V"}) final CarpoolDrive $r1, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolNativeManager$CarpoolRideMeetingIds;", ">;)V"}) final IResultObj<CarpoolRideMeetingIds> $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final String $r3 = CarpoolNativeManager.this.getMeetingIdByDriveIdNTV($r1.getId(), true);
                final String $r4 = CarpoolNativeManager.this.getMeetingIdByDriveIdNTV($r1.getId(), false);
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        CarpoolRideMeetingIds $r1 = new CarpoolRideMeetingIds();
                        $r1.dropOffMeetingId = $r4;
                        $r1.pickupMeetingId = $r3;
                        $r2.onResult($r1);
                    }
                });
            }
        });
    }

    public void isCurMeetingPickup(final IResultOk $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final boolean $z0 = CarpoolNativeManager.this.isCurMeetingPickupNTV();
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r1.onResult($z0);
                    }
                });
            }
        });
    }

    public void getDriveByRideId(@Signature({"(", "Lcom/waze/carpool/CarpoolRide;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final CarpoolRide $r1, @Signature({"(", "Lcom/waze/carpool/CarpoolRide;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolDrive;", ">;)V"}) final IResultObj<CarpoolDrive> $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final CarpoolDrive $r4 = CarpoolNativeManager.this.getDriveByRideIdNTV($r1.getId());
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r2.onResult($r4);
                    }
                });
            }
        });
    }

    public void getNextMeetingId(@Signature({"(", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Ljava/lang/String;", ">;)V"}) final IResultObj<String> $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                final String $r1 = CarpoolNativeManager.this.getNextMeetingIdNTV();
                AppService.Post(new Runnable() {
                    public void run() throws  {
                        $r1.onResult($r1);
                    }
                });
            }
        });
    }

    public void CreateUserResult(CarpoolUserData $r1) throws  {
        boolean $z0;
        Bundle $r2 = new Bundle();
        if ($r1 != null) {
            $z0 = true;
        } else {
            $z0 = false;
        }
        $r2.putBoolean("success", $z0);
        $r2.putParcelable("user", $r1);
        GoogleSignInActivity.SetStoredAuthorizedAccountName($r1.getEmail());
        this.handlers.sendUpdateMessage(UH_CARPOOL_USER, $r2);
    }

    public void onExtUserInfo(String $r1, UserExtendedInfo $r2) throws  {
        Bundle $r3 = new Bundle();
        $r3.putString("user_id", $r1);
        $r3.putParcelable(BUNDLE_EXT_INFO, $r2);
        this.handlers.sendUpdateMessage(UH_CARPOOL_EXT_USER, $r3);
    }

    public void getPayeeFormURLCallback(String $r1, int $i0) throws  {
        Bundle $r2 = new Bundle();
        $r2.putString("URL", $r1);
        $r2.putInt("RC", $i0);
        this.handlers.sendUpdateMessage(UH_CARPOOL_PAYMENT_URL, $r2);
    }

    public void getPayeeCallback(CarpoolPayee $r1) throws  {
        Logger.m36d("Payoneer: received payee CB");
        Bundle $r2 = new Bundle();
        $r2.putParcelable("payee", $r1);
        this.handlers.sendUpdateMessage(UH_CARPOOL_PAYMENT_PAYEE, $r2);
    }

    public void closeRideDetailsActivity() throws  {
        Logger.m36d("Manual Rides: closeRideDetailsActivity");
        this.handlers.sendUpdateMessage(UH_CARPOOL_CLOSE_RIDE_DETAILS, null);
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.closeSwipeableLayout();
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot Call showCandidateRideForRoute. Main activity is not available");
            }
        });
    }

    public void gotoJoin(boolean $z0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            if (!getInstance().hasCarpoolProfileNTV()) {
                CarpoolOnboardingManager.getInstance().setIsCalledFromPush($z0);
                $r2.openCarpoolSideMenu();
            } else if (getInstance().getCarpoolProfileNTV().didFinishOnboarding()) {
                $r2.startActivity(new Intent($r2, CarpoolRidesActivity.class));
            } else {
                CarpoolOnboardingManager.getInstance().setIsCalledFromPush($z0);
                $r2.openCarpoolSideMenu();
            }
        }
    }

    public void gotoJoinSkipMail(boolean $z0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            if (getInstance().hasCarpoolProfileNTV() && getInstance().getCarpoolProfileNTV().didFinishOnboarding()) {
                $r2.startActivity(new Intent($r2, CarpoolRidesActivity.class));
                return;
            }
            CarpoolOnboardingManager.getInstance().setIsCalledFromPush($z0);
            $r2.openCarpoolSideMenu();
        }
    }

    public void carpoolShowProfilePhoto() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            $r2.startActivity(new Intent($r2, CarpoolAddPhotoActivity.class));
        }
    }

    public void howRidersSeeYou() throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 != null) {
            $r2.startActivity(new Intent($r2, CarpoolDriverProfileActivity.class));
        }
    }

    public void showRides(boolean $z0) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        Intent $r1 = new Intent($r2, CarpoolRidesActivity.class);
        if ($z0) {
            $r1.putExtra("INT_VIEW_MODE", 2);
        }
        $r2.startActivity($r1);
    }

    public void showPickupCanceledPopUp(final String $r1, final String $r2, final int $i0, final AddressItem $r3) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r2 = AppService.getMainActivity();
                if ($r2 != null) {
                    LayoutManager $r3 = $r2.getLayoutMgr();
                    if ($r3 != null) {
                        $r3.showPickupCanceledPopUp($r1, $r2, $i0, $r3);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open Pickup Canceled Popup. Main activity is not available");
            }
        });
    }

    public void onCarpoolDriveUpdate(CarpoolDrive $r1) throws  {
        Logger.m36d("CPNM: onCarpoolDriveUpdate: received carpool drive updated");
        Bundle $r2 = new Bundle();
        $r2.putParcelable(CarpoolDrive.class.getSimpleName(), $r1);
        this.handlers.sendUpdateMessage(UH_CARPOOL_DRIVE_UPDATED, $r2);
    }

    public void onCarpoolDriveCreated(String $r1, CarpoolDrive $r2) throws  {
        Logger.m36d("onCarpoolDriveCreated: received new carpool drive");
        Bundle $r3 = new Bundle();
        $r3.putString("driveId", $r1);
        $r3.putParcelable(CarpoolDrive.class.getSimpleName(), $r2);
        this.handlers.sendUpdateMessage(UH_CARPOOL_DRIVE_CREATED, $r3);
    }

    public void onCarpoolDriveRemoved(String $r1) throws  {
        Logger.m36d("onCarpoolDriveRemoved: received carpool ride removed");
        Bundle $r2 = new Bundle();
        $r2.putString("id", $r1);
        this.handlers.sendUpdateMessage(UH_CARPOOL_DRIVE_REMOVED, $r2);
    }

    public void onCarpoolRideRemovedFromDrive(String driveId, String rideId, int $i0) throws  {
        Logger.m36d("onCarpoolDriveRemoved: received carpool ride removed");
        Bundle $r3 = new Bundle();
        $r3.putInt("res", $i0);
        this.handlers.sendUpdateMessage(UH_CARPOOL_RIDE_REMOVED_FROM_DRIVE, $r3);
    }

    public void onCarpoolCheckAgeResponse(boolean $z0) throws  {
        byte $b1;
        UpdateHandlers $r1 = this.handlers;
        int $i0 = UH_IS_AGE_OK;
        if ($z0) {
            $b1 = (byte) 1;
        } else {
            $b1 = (byte) 0;
        }
        $r1.sendUpdateMessage($i0, $b1, 0);
    }

    public void onGetHistoryResult(int $i0) throws  {
        this.handlers.sendUpdateMessage(UH_HISTORY_LOADED, $i0, 0);
    }

    public void pickDestinationDialog(final CarpoolDrive $r1, final PickDestinationCompleteListener $r2) throws  {
        getMeetingIdsByDriveId($r1, new IResultObj<CarpoolRideMeetingIds>() {
            public void onResult(CarpoolRideMeetingIds $r1) throws  {
                String $r6 = null;
                CarpoolLocation $r7 = $r1.getPickupLocation();
                CarpoolLocation $r8 = $r1.getDropOffLocation();
                CarpoolLocation $r9 = $r1.getDestination();
                CarpoolNativeManager $r10 = CarpoolNativeManager.this;
                String $r11 = $r7 != null ? $r7.address : null;
                String $r2 = $r1.pickupMeetingId;
                String $r12 = $r8 != null ? $r8.address : null;
                String $r3 = $r1.dropOffMeetingId;
                if ($r9 != null) {
                    $r6 = $r9.address;
                }
                $r10.pickDestinationDialog($r11, $r2, $r12, $r3, $r6, $r2, $r1);
            }
        });
    }

    public void pickDestinationDialog(String $r1, String $r2, String $r3, String $r4, CarpoolDrive $r5) throws  {
        pickDestinationDialog($r1, $r2, $r3, $r4, null, null, $r5);
    }

    public synchronized void pickDestinationDialog(String $r1, String $r2, String $r3, String $r4, String $r5, PickDestinationCompleteListener $r8, CarpoolDrive $r6) throws  {
        if (this.mSheet == null) {
            ActivityBase $r7 = AppService.getActiveActivity();
            if ($r8 == null && ($r7 instanceof PickDestinationCompleteListener)) {
                $r8 = (PickDestinationCompleteListener) $r7;
            }
            if ($r7 != null) {
                final ActivityBase activityBase = $r7;
                final String str = $r5;
                final String str2 = $r1;
                final String str3 = $r3;
                final String str4 = $r2;
                final CarpoolDrive carpoolDrive = $r6;
                final PickDestinationCompleteListener pickDestinationCompleteListener = $r8;
                final String str5 = $r4;
                $r7.post(new Runnable() {

                    class C14642 implements OnDismissListener {
                        C14642() throws  {
                        }

                        public void onDismiss(DialogInterface dialog) throws  {
                            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_DEST_CLARIFY_QUICKLINKS, AnalyticsEvents.ANALYTICS_EVENT_INFO_BUTTON, "CLOSE");
                            CarpoolNativeManager.this.mSheet = null;
                        }
                    }

                    public void run() throws  {
                        if (CarpoolNativeManager.this.mSheet == null) {
                            final NativeManager $r4 = AppService.getNativeManager();
                            final BottomSheet $r1 = new BottomSheet(activityBase, DisplayStrings.DS_CARPOOL_PICK_DEST_TITLE, Mode.COLUMN_TEXT_ICON);
                            CarpoolNativeManager.this.mSheet = $r1;
                            $r1.setAdapter(new Adapter() {

                                class C14601 implements DriveToNavigateCallback {
                                    C14601() throws  {
                                    }

                                    public void navigateCallback(int $i0) throws  {
                                        if ($i0 == 0) {
                                            $r1.dismiss();
                                            CarpoolNativeManager.this.mSheet = null;
                                            if (pickDestinationCompleteListener != null) {
                                                pickDestinationCompleteListener.onComplete();
                                            }
                                        }
                                    }
                                }

                                class C14612 implements DriveToNavigateCallback {
                                    C14612() throws  {
                                    }

                                    public void navigateCallback(int $i0) throws  {
                                        if ($i0 == 0) {
                                            $r1.dismiss();
                                            CarpoolNativeManager.this.mSheet = null;
                                            if (pickDestinationCompleteListener != null) {
                                                pickDestinationCompleteListener.onComplete();
                                            }
                                        }
                                    }
                                }

                                class C14623 implements DriveToNavigateCallback {
                                    C14623() throws  {
                                    }

                                    public void navigateCallback(int $i0) throws  {
                                        if ($i0 == 0) {
                                            $r1.dismiss();
                                            CarpoolNativeManager.this.mSheet = null;
                                            if (pickDestinationCompleteListener != null) {
                                                pickDestinationCompleteListener.onComplete();
                                            }
                                        }
                                    }
                                }

                                public void onClick(int r22) throws  {
                                    /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:14:0x0054
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
                                    /*
                                    r21 = this;
                                    r2 = 0;
                                    switch(r22) {
                                        case 0: goto L_0x002a;
                                        case 1: goto L_0x00ac;
                                        case 2: goto L_0x012a;
                                        default: goto L_0x0004;
                                    };
                                L_0x0004:
                                    goto L_0x0005;
                                L_0x0005:
                                    if (r2 != 0) goto L_0x015f;
                                L_0x0007:
                                    r0 = r21;
                                    r3 = r1;
                                    r3.dismiss();
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r5 = com.waze.carpool.CarpoolNativeManager.this;
                                    r6 = 0;
                                    r5.mSheet = r6;
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r7 = r8;
                                    if (r7 == 0) goto L_0x0160;
                                L_0x0020:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r7 = r8;
                                    r7.onComplete();
                                    return;
                                L_0x002a:
                                    r8 = "RW_DEST_CLARIFY_QUICKLINKS";
                                    r9 = "BUTTON";
                                    r10 = "PICKUP";
                                    com.waze.analytics.Analytics.log(r8, r9, r10);
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r11 = r6;
                                    if (r11 == 0) goto L_0x0067;
                                L_0x003b:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r11 = r6;
                                    r12 = r11.isEmpty();
                                    if (r12 != 0) goto L_0x0067;
                                L_0x0047:
                                    r8 = "DRIVE_TYPE";
                                    r9 = "VAUE";
                                    r10 = "PICKUP";
                                    com.waze.analytics.Analytics.log(r8, r9, r10);
                                    goto L_0x0058;
                                L_0x0051:
                                    goto L_0x0005;
                                    goto L_0x0058;
                                L_0x0055:
                                    goto L_0x0005;
                                L_0x0058:
                                    r13 = com.waze.navigate.DriveToNativeManager.getInstance();
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r11 = r6;
                                    r14 = 0;
                                    r13.drive(r11, r14);
                                    goto L_0x0005;
                                L_0x0067:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r15 = r7;
                                    if (r15 == 0) goto L_0x00a6;
                                L_0x006f:
                                    goto L_0x0073;
                                L_0x0070:
                                    goto L_0x0005;
                                L_0x0073:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r15 = r7;
                                    r16 = r15.getPickupLocation();
                                    if (r16 == 0) goto L_0x00a6;
                                L_0x007f:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r5 = com.waze.carpool.CarpoolNativeManager.this;
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r15 = r7;
                                    r14 = 2;
                                    r17 = r5.driveGetAddressItem(r15, r14);
                                    r13 = com.waze.navigate.DriveToNativeManager.getInstance();
                                    r18 = new com.waze.carpool.CarpoolNativeManager$62$1$1;
                                    r0 = r18;
                                    r1 = r21;
                                    r0.<init>();
                                    r0 = r17;
                                    r1 = r18;
                                    r13.navigate(r0, r1);
                                    r2 = 1;
                                    goto L_0x0051;
                                L_0x00a6:
                                    r8 = "pickDestinationDialog: no pickupMeetingId and no ride, can't navigate";
                                    com.waze.Logger.m38e(r8);
                                    goto L_0x0055;
                                L_0x00ac:
                                    r8 = "RW_DEST_CLARIFY_QUICKLINKS";
                                    r9 = "BUTTON";
                                    r10 = "DROPOFF";
                                    com.waze.analytics.Analytics.log(r8, r9, r10);
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r11 = r9;
                                    if (r11 == 0) goto L_0x00e1;
                                L_0x00bd:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r11 = r9;
                                    r12 = r11.isEmpty();
                                    if (r12 != 0) goto L_0x00e1;
                                L_0x00c9:
                                    r8 = "DRIVE_TYPE";
                                    r9 = "VAUE";
                                    r10 = "DROPOFF";
                                    com.waze.analytics.Analytics.log(r8, r9, r10);
                                    r13 = com.waze.navigate.DriveToNativeManager.getInstance();
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r11 = r9;
                                    r14 = 0;
                                    r13.drive(r11, r14);
                                    goto L_0x0070;
                                L_0x00e1:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r15 = r7;
                                    if (r15 == 0) goto L_0x0124;
                                L_0x00e9:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r15 = r7;
                                    r16 = r15.getDropOffLocation();
                                    if (r16 == 0) goto L_0x0124;
                                L_0x00f5:
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r5 = com.waze.carpool.CarpoolNativeManager.this;
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r15 = r7;
                                    r14 = 3;
                                    r17 = r5.driveGetAddressItem(r15, r14);
                                    r13 = com.waze.navigate.DriveToNativeManager.getInstance();
                                    r19 = new com.waze.carpool.CarpoolNativeManager$62$1$2;
                                    r0 = r19;
                                    r1 = r21;
                                    r0.<init>();
                                    r0 = r17;
                                    r1 = r19;
                                    r13.navigate(r0, r1);
                                    goto L_0x011e;
                                L_0x011b:
                                    goto L_0x0005;
                                L_0x011e:
                                    r2 = 1;
                                    goto L_0x011b;
                                    goto L_0x0124;
                                L_0x0121:
                                    goto L_0x0005;
                                L_0x0124:
                                    r8 = "pickDestinationDialog: no dropoffMeetingId and no ride, can't navigate";
                                    com.waze.Logger.m38e(r8);
                                    goto L_0x0121;
                                L_0x012a:
                                    r8 = "RW_DEST_CLARIFY_QUICKLINKS";
                                    r9 = "BUTTON";
                                    r10 = "DESTINATION";
                                    com.waze.analytics.Analytics.log(r8, r9, r10);
                                    r13 = com.waze.navigate.DriveToNativeManager.getInstance();
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r5 = com.waze.carpool.CarpoolNativeManager.this;
                                    r0 = r21;
                                    r4 = com.waze.carpool.CarpoolNativeManager.AnonymousClass62.this;
                                    r15 = r7;
                                    r14 = 4;
                                    r17 = r5.driveGetAddressItem(r15, r14);
                                    r20 = new com.waze.carpool.CarpoolNativeManager$62$1$3;
                                    r0 = r20;
                                    r1 = r21;
                                    r0.<init>();
                                    r14 = 1;
                                    r0 = r17;
                                    r1 = r20;
                                    r13.navigate(r0, r1, r14);
                                    goto L_0x015d;
                                L_0x015a:
                                    goto L_0x0005;
                                L_0x015d:
                                    r2 = 1;
                                    goto L_0x015a;
                                L_0x015f:
                                    return;
                                L_0x0160:
                                    return;
                                    */
                                    throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolNativeManager.62.1.onClick(int):void");
                                }

                                public int getCount() throws  {
                                    return str == null ? 2 : 3;
                                }

                                public void onConfigItem(int $i0, ItemDetails $r1) throws  {
                                    switch ($i0) {
                                        case 0:
                                            $r1.setItem($r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_PICK_DEST_PICKUP), activityBase.getResources().getDrawable(C1283R.drawable.rw_pickup_icon), str2);
                                            return;
                                        case 1:
                                            $r1.setItem($r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_PICK_DEST_DROPOFF), activityBase.getResources().getDrawable(C1283R.drawable.rw_dropoff_icon), str3);
                                            return;
                                        case 2:
                                            $r1.setItem($r4.getLanguageString((int) DisplayStrings.DS_CARPOOL_PICK_DEST_DESTINATION), activityBase.getResources().getDrawable(C1283R.drawable.rw_destination_icon), str);
                                            return;
                                        default:
                                            return;
                                    }
                                }
                            });
                            $r1.setOnDismissListener(new C14642());
                            $r1.show();
                        }
                    }
                });
            }
        }
    }

    public void onCarpoolUpdateBankAccountSent(boolean $z0) throws  {
        byte $b1;
        refreshUserObjectNTV();
        UpdateHandlers $r1 = this.handlers;
        int $i0 = UH_BANK_ACCOUNT_SENT;
        if ($z0) {
            $b1 = (byte) 1;
        } else {
            $b1 = (byte) 0;
        }
        $r1.sendUpdateMessage($i0, $b1, 0);
    }

    public void refreshCarpoolUser() throws  {
        refreshUserObjectNTV();
    }

    public void onCarpoolError(int $i0, String $r1) throws  {
        Bundle $r2 = new Bundle();
        $r2.putInt("type", $i0);
        $r2.putString(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, $r1);
        this.handlers.sendUpdateMessage(UH_CARPOOL_ERROR, $r2);
    }

    public void onTokenReceived(String $r1, String $r2) throws  {
        Bundle $r3 = new Bundle();
        $r3.putString("id", $r2);
        $r3.putString("token", $r1);
        this.handlers.sendUpdateMessage(UH_CARPOOL_TOKEN, $r3);
    }

    public void showCandidateRideForRoute(final CarpoolDrive $r1, final CarpoolRide $r2) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.showCandidateRideForRoute($r1, $r2);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot Call showCandidateRideForRoute. Main activity is not available");
            }
        });
    }

    public void showManualRidePopup(final CarpoolDrive $r1, final CarpoolRidePickupMeetingDetails $r2, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.showManualRidePopup($r1, $r2, $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "CarpoolNativeManager: Cannot Call showManualRidePopup. Main activity is not available");
            }
        });
    }

    public void hideManualRidePopup() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.hideManualRidePopup();
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "CarpoolNativeManager: Cannot Call hideManualRidePopup. Main activity is not available");
            }
        });
    }

    public void showFullScreenRideDialog(final CarpoolDrive $r1, final CarpoolRidePickupMeetingDetails $r2, final int $i0) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.showFullScreenRidePopup($r1, $r2, $i0);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "CarpoolNativeManager: Cannot Call showManualRidePopup. Main activity is not available");
            }
        });
    }

    public void collapseManualRidePopupToTicker() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.collapseManualRidePopupToTicker();
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "CarpoolNativeManager: Cannot Call collapseManualRidePopupToTicker. Main activity is not available");
            }
        });
    }

    public void showRideAssistance(String $r1, String $r2, boolean $z0, long $l0, long $l1, String $r3, String $r4, int $i2, CarpoolDrive $r5) throws  {
        final CarpoolDrive carpoolDrive = $r5;
        final String str = $r1;
        final String str2 = $r2;
        final boolean z = $z0;
        final long j = $l0;
        final long j2 = $l1;
        final String str3 = $r3;
        final String str4 = $r4;
        final int i = $i2;
        AppService.Post(new Runnable() {

            class C14651 implements Runnable {
                C14651() throws  {
                }

                public void run() throws  {
                    CarpoolNativeManager.this.showRideAssistance(str, str2, z, j, j2, str3, str4, i, carpoolDrive);
                }
            }

            public void run() throws  {
                ActivityBase $r1 = AppService.getActiveActivity();
                if ($r1 instanceof MainActivity) {
                    boolean $z0;
                    if ($r1.getResources().getConfiguration().orientation == 1) {
                        $z0 = true;
                    } else {
                        $z0 = false;
                    }
                    CarpoolDrive $r4 = carpoolDrive;
                    if ($r4.isMultiPax()) {
                        CarpoolUtils.setDialogRiderImages(carpoolDrive, $r1, MsgBox.getInstance().openConfirmDialogButtons(str, str2, z, j, j2, str3, str4, i, "", "", "", $z0), false);
                        return;
                    }
                    $r4 = carpoolDrive;
                    CarpoolUserData $r11 = $r4.getRider();
                    if ($r11 == null) {
                        MsgBox.getInstance().openConfirmDialogButtons(str, str2, z, j, j2, str3, str4, i, "", "", "", $z0);
                        return;
                    }
                    MsgBox.getInstance().openConfirmDialogButtons(str, str2, z, j, j2, str3, str4, i, $r11.getImage(), $r11.getFirstName(), $r11.getLastName(), $z0);
                    return;
                }
                MainActivity $r15 = AppService.getMainActivity();
                if ($r15 != null) {
                    LayoutManager $r16 = $r15.getLayoutMgr();
                    if ($r16 == null) {
                        Log.e(Logger.TAG, "Cannot open ride assistance Popup. Layout manager is not available");
                        return;
                    }
                    $r16.addOnResume(new C14651());
                    return;
                }
                Log.e(Logger.TAG, "Cannot open ride assistance Popup. Main activity is not available");
            }
        });
    }

    public void openCarpoolTakeover() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.openCarpoolTakeoverNTV();
            }
        });
    }

    public void openCarpoolTakeoverIfNecessary() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.openCarpoolTakeoverIfNecessaryNTV();
            }
        }, 600);
    }

    public boolean getRwRtDontShowAgainValue() throws  {
        return getRwRtDontShowAgainValueNTV();
    }

    public void setRwRtDontShowAgainValue(boolean $z0) throws  {
        setRwRtDontShowAgainValueNTV($z0);
    }

    public boolean getAndResetTuneupQuestionFlag() throws  {
        boolean z0 = this.mTuneupQuestionFlag;
        this.mTuneupQuestionFlag = false;
        return z0;
    }

    public void setTuneupQuestionFlag() throws  {
        this.mTuneupQuestionFlag = true;
    }

    public int getUnreadChatMessageCount(CarpoolRide $r1) throws  {
        return $r1 == null ? 0 : getUnreadCarpoolRideMessagesCountNTV($r1.getId());
    }

    public int getUnreadChatMessageCount(CarpoolDrive $r1) throws  {
        return getUnreadCarpoolDriveMessagesCountNTV($r1.getId());
    }

    public void getCarpoolRideMessages(final CarpoolRide $r1, final CarpoolMessagesReceiveCompleteListener $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private CarpoolRideMessages msgs;

            public void event() throws  {
                try {
                    Logger.m36d("Chat: getCarpoolRideMessages: request ride msgs for ride " + $r1.getId());
                    this.msgs = CarpoolNativeManager.this.getCarpoolRideMessagesNTV($r1.getId());
                } catch (Exception e) {
                    this.msgs = null;
                }
            }

            public void callback() throws  {
                if (this.msgs.messages != null) {
                    Logger.m36d("Chat: getCarpoolRideMessages: received " + this.msgs.messages.length + " ride msgs for ride " + this.msgs.ride_id);
                } else {
                    Logger.m36d("Chat: getCarpoolRideMessages: received no msgs for ride " + this.msgs.ride_id);
                }
                $r2.onComplete(this.msgs);
            }
        });
    }

    public void getCarpoolLastDriveMessage(@Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolNativeManager$LastChatMsgDetails;", ">;)V"}) final CarpoolDrive $r1, @Signature({"(", "Lcom/waze/carpool/CarpoolDrive;", "Lcom/waze/carpool/CarpoolNativeManager$IResultObj", "<", "Lcom/waze/carpool/CarpoolNativeManager$LastChatMsgDetails;", ">;)V"}) final IResultObj<LastChatMsgDetails> $r2) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private LastChatMsgDetails msgDet = null;

            public void event() throws  {
                if ($r1 != null && $r1.rides != null) {
                    for (CarpoolRide $r2 : $r1.rides) {
                        CarpoolRideMessages $r7 = CarpoolNativeManager.this.getCarpoolRideMessagesNTV($r2.getId());
                        if (!($r7 == null || $r7.messages == null || $r7.messages.length == 0)) {
                            CarpoolMessage $r1 = $r7.messages[$r7.messages.length - 1];
                            if (this.msgDet == null || this.msgDet.msg.sent_seconds < $r1.sent_seconds) {
                                this.msgDet = new LastChatMsgDetails($r1, $r2);
                            }
                        }
                    }
                }
            }

            public void callback() throws  {
                $r2.onResult(this.msgDet);
            }
        });
    }

    public void markCarpoolRideMessagesRead(final String $r1, final long $l0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.markCarpoolRideMessagesReadNTV($r1, $l0);
            }
        });
    }

    public void sendChatMessage(final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Logger.m36d("Chat: sendChatMessage: sending msg " + $r2 + "  for ride " + $r1);
                CarpoolNativeManager.this.sendCarpoolRideMessagesNTV($r1, $r2);
            }
        });
    }

    public void onChatMessageSent(final String $r1, final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Bundle $r1 = new Bundle();
                $r1.putString(CarpoolNativeManager.UH_KEY_RIDE_ID, $r1);
                $r1.putBoolean("ride_msg_send_status", $z0);
                CarpoolNativeManager.this.handlers.sendUpdateMessage(CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE_SENT_STATUS, $r1);
            }
        });
    }

    public void onMessagesRead(final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Bundle $r1 = new Bundle();
                $r1.putString(CarpoolNativeManager.UH_KEY_RIDE_ID, $r1);
                $r1.putString("ride_msg_send_status", $r2);
                CarpoolNativeManager.this.handlers.sendUpdateMessage(CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE_READ, $r1);
            }
        });
    }

    public void requestRideChatMessages(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Logger.m36d("Chat: requestRideChatMessages for ride id " + $r1);
                CarpoolNativeManager.this.CarpoolRequestRideMessagesNTV($r1);
            }
        });
    }

    public void onRideMessagePushed(final String $r1, final String $r2) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Logger.m36d("Chat: onRideMessagePushed: push msg " + $r2 + "  for ride " + $r1);
                Bundle $r1 = new Bundle();
                $r1.putString(CarpoolNativeManager.UH_KEY_RIDE_ID, $r1);
                $r1.putString(CarpoolNativeManager.UH_KEY_RIDE_MSG, $r2);
                CarpoolNativeManager.this.handlers.sendUpdateMessage(CarpoolNativeManager.UH_CARPOOL_CHAT_MESSAGE, $r1);
            }
        });
    }

    public void onRideMessagesUpdated(final String $r1) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                Logger.m36d("Chat: onRideMessagesUpdated:   for ride " + $r1);
                Bundle $r1 = new Bundle();
                $r1.putString(CarpoolNativeManager.UH_KEY_RIDE_ID, $r1);
                CarpoolNativeManager.this.handlers.sendUpdateMessage(CarpoolNativeManager.UH_CARPOOL_MESSAGES_LOADED, $r1);
            }
        });
    }

    public void showRideMessaging(CarpoolDrive $r1, String $r2) throws  {
        if ($r1 == null) {
            Logger.m38e("CPNM:showRideMessaging:: drive is null! cannot view ride details");
            return;
        }
        Logger.m36d("CPNM:showRideMessaging:: open drive messaging for ride " + $r2);
        Context $r6 = AppService.getAppContext();
        Intent $r3 = new Intent($r6, CarpoolRideDetailsActivity.class);
        $r3.putExtra(CarpoolDrive.class.getSimpleName(), $r1);
        CarpoolRide $r8 = $r1.getRideByRideId($r2);
        if ($r8 != null) {
            Logger.m36d("CPNM:showRideMessaging:: retrieved ride for ride id " + $r2 + "; drive id=" + $r1.getId());
            $r3.putExtra(CarpoolRide.class.getSimpleName(), $r8);
        } else {
            Logger.m38e("CPNM:showRideMessaging:: could not retrieve ride for ride id " + $r2 + "; drive id=" + $r1.getId());
        }
        $r3.putExtra(CarpoolRideDetailsActivity.INTENT_PARAM_MESSAGING, true);
        $r3.setFlags(268435456);
        $r6.startActivity($r3);
    }

    public void getMeetingDetailsForPickup(final getMeetingDetailsForPickupReceiveCompleteListener $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private CarpoolRidePickupMeetingDetails details;

            public void event() throws  {
                try {
                    Logger.m36d("Manual rides: getMeetingDetailsForPickup: request meeting for pickup");
                    this.details = CarpoolNativeManager.this.getMeetingDetailsForPickupNTV();
                } catch (Exception e) {
                    this.details = null;
                }
            }

            public void callback() throws  {
                $r1.onComplete(this.details);
            }
        });
    }

    public void openUpcomingCarpoolBar(final CarpoolDrive $r1) throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.openUpcomingCarpoolBar($r1);
                        return;
                    }
                    return;
                }
                Log.e(Logger.TAG, "Cannot open carpool Popup. Main activity is not available");
            }
        });
    }

    public void onBlockUserResult(int $i0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putInt("res", $i0);
        this.handlers.sendUpdateMessage(UH_CARPOOL_BLOCK_USER_RES, $r1);
    }

    public void onReportUserResult(int $i0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putInt("res", $i0);
        this.handlers.sendUpdateMessage(UH_CARPOOL_REPORT_USER_RES, $r1);
    }

    public void onRejectRideResult(int $i0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putInt("res", $i0);
        this.handlers.sendUpdateMessage(UH_CARPOOL_REJECT_RIDE_RES, $r1);
    }

    public void onClearChatResult(int $i0) throws  {
        Bundle $r1 = new Bundle();
        $r1.putInt("res", $i0);
        this.handlers.sendUpdateMessage(UH_CARPOOL_CLEAR_CHAT_RES, $r1);
    }

    public CarpoolEmailWhitelist getWhilelistEmail() throws  {
        return getWhilelistEmailNTV();
    }

    public boolean isDriverOnboarded() throws  {
        return isDriverOnboardedNTV();
    }

    public void getRideCount(final boolean $z0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                int $i0 = CarpoolNativeManager.this.getRideCountNTV($z0);
                Bundle $r1 = new Bundle();
                $r1.putInt("rideCount", $i0);
                if (!$z0) {
                    $r1.putInt("offersCount", CarpoolNativeManager.this.getRideOffersCountNTV());
                }
                CarpoolNativeManager.this.handlers.sendUpdateMessage(CarpoolNativeManager.UH_CARPOOL_RIDES_COUNTED, $r1);
            }
        });
    }

    public void idProviderConnected(final int $i0) throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.idProviderConnectedNTV($i0);
            }
        });
    }

    public int getCarpoolProfileCompletionPercentage() throws  {
        return getCarpoolProfileCompletionPercentageNTV();
    }

    public void carpoolSettingsChanged() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        $r2.setRightSideMenu();
                    }
                }
                Log.e(Logger.TAG, "Cannot Call checkCarpoolAvailability. Main activity/LayoutManager/FriendsSideMenu is not available");
            }
        });
    }

    public void carpoolShowNavListPromo() throws  {
        AppService.Post(new Runnable() {
            public void run() throws  {
                MainActivity $r1 = AppService.getMainActivity();
                if ($r1 != null) {
                    LayoutManager $r2 = $r1.getLayoutMgr();
                    if ($r2 != null) {
                        MainSideMenu $r3 = $r2.getMainSideMenu();
                        if ($r3 != null) {
                            Logger.m43w("CarpoolNativeManager: carpoolShowNavListPromo: showing promo");
                            $r3.showCarpoolPromo();
                            return;
                        }
                    }
                }
                Log.e(Logger.TAG, "Cannot Call checkCarpoolAvailability. Main activity/LayoutManager/FriendsSideMenu is not available");
            }
        });
    }

    public void navMenuPromoClosed() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.navMenuPromoClosedNTV();
            }
        });
    }

    public void navMenuPromoSeenThisSession() throws  {
        NativeManager.Post(new Runnable() {
            public void run() throws  {
                CarpoolNativeManager.this.navMenuPromoSeenThisSessionNTV();
            }
        });
    }

    public boolean needUserAttention() throws  {
        return needUserAttentionNTV();
    }

    public CarpoolRideState[] getStateHistory(CarpoolRide $r1) throws  {
        return getStateHistoryNTV($r1.getId());
    }

    public int getIdProviderType() throws  {
        return getIdProviderTypeNTV();
    }

    public static void test_endorsementsEnum() throws  {
        test_endorsementsEnumNTV(0, 1, 1, 2, 3, 4, 5, 6, 6);
    }
}
