package com.waze.navigate;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import com.waze.AppService;
import com.waze.Logger;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.carpool.CarpoolRideDetailsActivity;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.ifs.ui.ActivityBase;
import com.waze.menus.AddressItemOptionsUtil.AddressItemOptionListener;
import com.waze.planned_drive.PlannedDriveActivity;
import com.waze.share.ShareUtility;
import com.waze.share.ShareUtility.ShareType;
import com.waze.strings.DisplayStrings;

public abstract class AddressItemListActivity extends ActivityBase implements AddressItemOptionListener {

    class C20661 implements IResultObj<CarpoolDrive> {
        C20661() throws  {
        }

        public void onResult(CarpoolDrive $r1) throws  {
            Intent $r2 = new Intent(AppService.getActiveActivity(), CarpoolRideDetailsActivity.class);
            if ($r1.getRide() == null) {
                Logger.m38e("FavoritesActivity: ride is null! cannot view ride details");
                MsgBox.openMessageBoxFull(DisplayStrings.displayString(DisplayStrings.DS_UHHOHE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ERR_NO_RIDE), DisplayStrings.displayString(334), -1, null);
                return;
            }
            $r2.putExtra("CarpoolUserData", $r1.getRider());
            $r2.putExtra("CarpoolRide", $r1.getRide());
            AppService.getActiveActivity().startActivityForResult($r2, 1);
        }
    }

    class C20703 implements Runnable {
        C20703() throws  {
        }

        public void run() throws  {
        }
    }

    protected abstract String getClickEventId() throws ;

    protected abstract String getParkingContext() throws ;

    public void onAddressItemOptionClicked(AddressItem $r1, int $i0) throws  {
        switch ($i0) {
            case 0:
                onAddFavorite($r1);
                return;
            case 1:
            case 2:
            case 5:
            case 11:
            case 13:
                break;
            case 3:
                Intent $r2 = r7;
                Intent r7 = new Intent(AppService.getActiveActivity(), ParkingSearchResultsActivity.class);
                $r2.putExtra(PublicMacros.PREVIEW_PARKING_VENUE, $r1.getVenueDataForParking());
                $r2.putExtra(PublicMacros.PREVIEW_PARKING_CONTEXT, getParkingContext());
                AppService.getActiveActivity().startActivityForResult($r2, 0);
                return;
            case 4:
                onCreatePlannedDrive($r1);
                return;
            case 6:
                onInfoActionClicked($r1);
                return;
            case 7:
                ShareUtility.OpenShareActivityOrLogin(AppService.getActiveActivity(), ShareType.ShareType_ShareSelection, null, $r1, null);
                return;
            case 8:
                onOpenRoutes($r1);
                return;
            case 9:
            case 10:
                addHomeWork($r1.getType());
                return;
            case 12:
                onDeleteActionClicked($r1);
                return;
            case 14:
                onRenameFavorite($r1);
                return;
            default:
                break;
        }
    }

    protected void onRenameFavorite(AddressItem addressItem) throws  {
    }

    protected void onItemDeleted() throws  {
    }

    public void onDeleteActionClicked(AddressItem $r1) throws  {
        Analytics.log(getClickEventId(), "ACTION", "DELETE");
        DriveToNativeManager.getInstance().eraseAddressItem($r1);
        onItemDeleted();
    }

    public void onInfoActionClicked(AddressItem $r1) throws  {
        Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_FAVOURITE_CLICK, "ACTION", "INFO");
        if ($r1.getType() == 13) {
            NativeManager.getInstance().setSharedAddressItem($r1);
            DriveToNativeManager.getInstance().InitMeeting($r1.getMeetingId());
        } else if ($r1.getType() == 14 || $r1.getType() == 15) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_NAVLIST_RIDE_INFO_CLICKED, "TYPE|RIDE_ID", ($r1.getType() == 14 ? "DROPOFF" : "PICKUP") + "|" + $r1.getMeetingId());
            CarpoolNativeManager.getInstance().getDriveInfoByMeetingId($r1.getMeetingId(), new C20661());
        } else {
            Intent $r2 = new Intent(AppService.getActiveActivity(), AddressPreviewActivity.class);
            $r2.putExtra(PublicMacros.ADDRESS_ITEM, $r1);
            if (!($r1.VanueID == null || $r1.VanueID.isEmpty())) {
                $r2.putExtra(PublicMacros.PREVIEW_LOAD_VENUE, true);
            }
            AppService.getActiveActivity().startActivityForResult($r2, 1);
        }
    }

    private void onCreatePlannedDrive(AddressItem $r1) throws  {
        PlannedDriveActivity.setNavigationAddressItem($r1);
        AppService.getActiveActivity().startActivity(new Intent(this, PlannedDriveActivity.class));
    }

    private void onOpenRoutes(AddressItem $r1) throws  {
        NativeManager.getInstance().setShowRoutesWhenNavigationStarts(true);
        DriveToNativeManager.getInstance().navigate($r1, null);
    }

    private void addHomeWork(int $i0) throws  {
        Intent $r1 = new Intent(AppService.getActiveActivity(), AddHomeWorkActivity.class);
        $r1.putExtra(PublicMacros.ADDRESS_TYPE, $i0);
        AppService.getActiveActivity().startActivityForResult($r1, 1001);
    }

    private void onAddFavorite(final AddressItem $r1) throws  {
        NativeManager.Post(new RunnableUICallback() {
            private int danger;

            class C20671 implements OnClickListener {
                C20671() throws  {
                }

                public void onClick(DialogInterface dialog, int $i0) throws  {
                    if ($i0 == 1) {
                        AddressItemListActivity.this.addFavorite($r1);
                        DriveToNativeManager.getInstance().addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_YES);
                        return;
                    }
                    DriveToNativeManager.getInstance().addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, AnalyticsEvents.ANALYTICS_NO);
                }
            }

            class C20682 implements OnCancelListener {
                C20682() throws  {
                }

                public void onCancel(DialogInterface dialog) throws  {
                    DriveToNativeManager.getInstance().addDangerZoneStat($r1.getLocationX().intValue(), $r1.getLocationY().intValue(), AnalyticsEvents.ANALYTICS_EVENT_FAVORITE_IN_DANGER_ZONE, "BACK");
                }
            }

            public void event() throws  {
                this.danger = DriveToNativeManager.getInstance().isInDangerZoneNTV($r1.getLocationX().intValue(), $r1.getLocationY().intValue());
            }

            public void callback() throws  {
                NativeManager $r3 = NativeManager.getInstance();
                if (this.danger >= 0) {
                    MsgBox.getInstance().OpenConfirmDialogCustomTimeoutCbJava($r3.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_AREA_DIALOG_TITLE), $r3.getLanguageString(this.danger + DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE), false, new C20671(), $r3.getLanguageString(DisplayStrings.DS_DANGEROUS_ADDRESS_SAVE_BUTTON), $r3.getLanguageString(344), -1, "dangerous_zone_icon", new C20682(), true, true);
                    return;
                }
                AddressItemListActivity.this.addFavorite($r1);
            }
        });
    }

    private void addFavorite(AddressItem $r1) throws  {
        NameFavoriteView.showNameFavorite(AppService.getActiveActivity(), $r1, new C20703());
    }
}
