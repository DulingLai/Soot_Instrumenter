package jp.pioneer.ce.aam2.AAM2Kit_Lib;

import android.content.Context;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2CertifiedInfo;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2Kit;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2LocationInfo;
import jp.pioneer.ce.aam2.AAM2Kit.AAM2MainUnitSpecInfo;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackAlbumTitleReplyData;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackArtistNameReplyData;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackElapsedTimeNotificationData;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackInfoReplyData;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackInfoReplyDataBase;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackSettingInfoReplyData;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackTitleReplyData;
import jp.pioneer.mbg.pioneerkit.ExtCertifiedInfo;
import jp.pioneer.mbg.pioneerkit.ExtDeviceSpecInfo;
import jp.pioneer.mbg.pioneerkit.ExtLocation;
import jp.pioneer.mbg.pioneerkit.IExtAppFocusListener;
import jp.pioneer.mbg.pioneerkit.IExtLocationListener;
import jp.pioneer.mbg.pioneerkit.IExtMediaInfoReqListener;
import jp.pioneer.mbg.pioneerkit.IExtRemoteCtrlListener;
import jp.pioneer.mbg.pioneerkit.IExtRequiredListener;
import jp.pioneer.mbg.pioneerkit.PioneerKit;
import jp.pioneer.mbg.pioneerkit.replydata.TrackAlbumTitleReplyData;
import jp.pioneer.mbg.pioneerkit.replydata.TrackArtistNameReplyData;
import jp.pioneer.mbg.pioneerkit.replydata.TrackElapsedTimeNotificationData;
import jp.pioneer.mbg.pioneerkit.replydata.TrackInfoReplyData;
import jp.pioneer.mbg.pioneerkit.replydata.TrackInfoReplyDataBase;
import jp.pioneer.mbg.pioneerkit.replydata.TrackSettingInfoReplyData;
import jp.pioneer.mbg.pioneerkit.replydata.TrackTitleReplyData;

public class PioneerKitWrapper {
    public static final int AAM2_SOUND_CATEGORY_MIX_SOUND = 2;
    public static final int AAM2_SOUND_CATEGORY_SOLO_SOUND = 1;
    public static final int ADVANCED_APP_MODE = 0;
    public static final int ADVANCED_APP_MODE_2 = 2;
    public static final int AV_APP_CTRL_MODE = 1;
    public static final int DISCONNECTED_ADVANCED_APP_MODE = -1;
    public static final int EXT_DEVICE_CANBUS = 1;
    public static final int EXT_DEVICE_CANBUS_NONE = 0;
    public static final int EXT_DEVICE_LOCATION_GPS = 1;
    public static final int EXT_DEVICE_LOCATION_GPS_SENSOR = 2;
    public static final int EXT_DEVICE_LOCATION_GPS_SENSOR_PULSE = 3;
    public static final int EXT_DEVICE_LOCATION_INVALID = 0;
    public static final int EXT_DEVICE_LOCTION_ACCURACY_COARSE = 100;
    public static final int EXT_DEVICE_LOCTION_ACCURACY_FINE = 10;
    public static final int EXT_DEVICE_LOCTION_ACCURACY_INVALID = -1;
    public static final int EXT_DEVICE_REMOTE_CONTROLLER_AV_REMOTE_CONTROL = 1;
    public static final int EXT_DEVICE_REMOTE_CONTROLLER_INVALID = 0;
    public static final int EXT_DEVICE_SPEC_POINTER_1 = 1;
    public static final int EXT_DEVICE_SPEC_POINTER_2 = 2;
    public static final int EXT_DEVICE_SPEC_POINTER_3 = 3;
    public static final int EXT_DEVICE_SPEC_POINTER_INVALID = 0;
    public static final int EXT_REMOTE_CTRL_AV_CMD_FF = 5;
    public static final int EXT_REMOTE_CTRL_AV_CMD_PAUSE = 2;
    public static final int EXT_REMOTE_CTRL_AV_CMD_PLAY = 1;
    public static final int EXT_REMOTE_CTRL_AV_CMD_RATINGDOWN = 33;
    public static final int EXT_REMOTE_CTRL_AV_CMD_RATINGUP = 34;
    public static final int EXT_REMOTE_CTRL_AV_CMD_RW = 6;
    public static final int EXT_REMOTE_CTRL_AV_CMD_TOGGLE = 0;
    public static final int EXT_REMOTE_CTRL_AV_CMD_TRACKDOWN = 4;
    public static final int EXT_REMOTE_CTRL_AV_CMD_TRACKUP = 3;
    public static final long INVALID_TOKEN = -1;
    public static final int MEDIA_PLAYSTATUS_PAUSE = 0;
    public static final int MEDIA_PLAYSTATUS_PLAY = 1;
    public static final int TYPE_TRACK_ALBUM = 5;
    public static final int TYPE_TRACK_ARTIST = 4;
    public static final int TYPE_TRACK_ELAPSED_TIME = 6;
    public static final int TYPE_TRACK_INFO = 2;
    public static final int TYPE_TRACK_SETTING_INFO = 1;
    public static final int TYPE_TRACK_TITLE = 3;
    private static int isConnection = -1;
    public static wIAppFocusListener wAppFocusListener;
    public static wIRemoteCtrlListener wCtrlListener;
    public static wILocationListener wLocListener;
    public static wIMediaInfoReqListener wMediaInfoReqListener;
    public static wIRequiredListener wReqListener;

    public PioneerKitWrapper(Context context) throws  {
        isConnection = -1;
    }

    public static boolean pStartPioneerKit(Context $r0, IExtRequiredListener $r1) throws  {
        isConnection = -1;
        wReqListener = new wIRequiredListener($r1);
        return PioneerKit.pStartPioneerKit($r0, wReqListener) || AAM2Kit.pStartAAM2Kit($r0, wReqListener);
    }

    public static boolean pStopPioneerKit(Context $r0, IExtRequiredListener $r1) throws  {
        if (wReqListener == null) {
            wReqListener = new wIRequiredListener($r1);
        }
        boolean $z0 = PioneerKit.pStopPioneerKit($r0, wReqListener);
        boolean $z1 = AAM2Kit.pStopAAM2Kit($r0, wReqListener);
        wReqListener = null;
        if (wLocListener != null) {
            pUnregisterLocationListener(wLocListener);
        }
        if (wCtrlListener != null) {
            pUnregisterRemoteCtrlListener(wCtrlListener);
        }
        return $z0 || $z1;
    }

    public static boolean pRegisterLocationListener(IExtLocationListener $r0) throws  {
        wLocListener = new wILocationListener($r0);
        return PioneerKit.pRegisterLocationListener(wLocListener) || AAM2Kit.pRegisterLocationListener(wLocListener);
    }

    public static boolean pUnregisterLocationListener(IExtLocationListener $r0) throws  {
        if (wLocListener == null) {
            wLocListener = new wILocationListener($r0);
        }
        boolean $z0 = PioneerKit.pUnregisterLocationListener(wLocListener);
        boolean $z1 = AAM2Kit.pUnregisterLocationListener(wLocListener);
        wLocListener = null;
        return $z0 || $z1;
    }

    public static boolean pRegisterRemoteCtrlListener(IExtRemoteCtrlListener $r0) throws  {
        wCtrlListener = new wIRemoteCtrlListener($r0);
        return PioneerKit.pRegisterRemoteCtrlListener(wCtrlListener) || AAM2Kit.pRegisterRemoteCtrlListener(wCtrlListener);
    }

    public static boolean pUnregisterRemoteCtrlListener(IExtRemoteCtrlListener $r0) throws  {
        if (wCtrlListener == null) {
            wCtrlListener = new wIRemoteCtrlListener($r0);
        }
        boolean $z0 = PioneerKit.pUnregisterRemoteCtrlListener(wCtrlListener);
        boolean $z1 = AAM2Kit.pUnregisterRemoteCtrlListener(wCtrlListener);
        wCtrlListener = null;
        return $z0 || $z1;
    }

    public static boolean pIsAdvancedAppMode() throws  {
        boolean $z0 = false;
        if (isConnection == 0) {
            $z0 = PioneerKit.pIsAdvancedAppMode();
        }
        if (isConnection == 2) {
            return AAM2Kit.pIsAdvancedAppMode();
        }
        return $z0;
    }

    public static boolean pIsDriveStopping() throws  {
        if (isConnection == 0) {
            return PioneerKit.pIsDriveStopping();
        }
        return isConnection == 2 ? AAM2Kit.pIsDriveStopping() : false;
    }

    public static boolean pIsParkingSwitch() throws  {
        if (isConnection == 0) {
            return PioneerKit.pIsParkingSwitch();
        }
        return isConnection == 2 ? AAM2Kit.pIsParkingSwitch() : false;
    }

    @Deprecated
    public static boolean pIsParking() throws  {
        if (isConnection == 0) {
            return PioneerKit.pIsParking();
        }
        return isConnection == 2 ? AAM2Kit.pIsDriveStopping() : false;
    }

    public static boolean pSendTrackInfo(int $i0, TrackInfoReplyDataBase $r0) throws  {
        if (isConnection == 0) {
            return PioneerKit.pSendTrackInfo($i0, $r0);
        }
        if (isConnection != 2) {
            return false;
        }
        AAM2TrackInfoReplyDataBase $r1 = ConvertToAAM2($i0, $r0);
        return $r1 != null ? AAM2Kit.pSendTrackInfo($i0, $r1) : false;
    }

    public static boolean pSendTrackInfoSetting(TrackSettingInfoReplyData $r0) throws  {
        if (isConnection == 0) {
            return PioneerKit.pSendTrackInfoSetting($r0);
        }
        return isConnection == 2 ? AAM2Kit.pSendTrackInfoSetting(ConvertToAAM2($r0)) : false;
    }

    public static boolean pSetMediaPlayerStatus(int $i0) throws  {
        if (isConnection == 0) {
            return PioneerKit.pSetMediaPlayerStatus($i0);
        }
        return isConnection == 2 ? AAM2Kit.pSetMediaPlayerStatus($i0) : false;
    }

    public static boolean pIsFocused() throws  {
        if (isConnection == 0) {
            return PioneerKit.pIsFocused();
        }
        return isConnection == 2 ? AAM2Kit.pIsFocused() : false;
    }

    public static boolean pRegisterAppFocusListener(IExtAppFocusListener $r0) throws  {
        wAppFocusListener = new wIAppFocusListener($r0);
        return PioneerKit.pRegisterAppFocusListener(wAppFocusListener) || AAM2Kit.pRegisterAppFocusListener(wAppFocusListener);
    }

    public static boolean pUnregisterAppFocusListener(IExtAppFocusListener $r0) throws  {
        if (wAppFocusListener == null) {
            wAppFocusListener = new wIAppFocusListener($r0);
        }
        boolean $z0 = PioneerKit.pUnregisterAppFocusListener(wAppFocusListener);
        boolean $z1 = AAM2Kit.pUnregisterAppFocusListener(wAppFocusListener);
        wAppFocusListener = null;
        return $z0 || $z1;
    }

    public static boolean pRegisterMediaInfoReqListener(IExtMediaInfoReqListener $r0) throws  {
        wMediaInfoReqListener = new wIMediaInfoReqListener($r0);
        return PioneerKit.pRegisterMediaInfoReqListener(wMediaInfoReqListener) || AAM2Kit.pRegisterMediaInfoReqListener(wMediaInfoReqListener);
    }

    public static boolean pUnregisterMediaInfoReqListener(IExtMediaInfoReqListener $r0) throws  {
        if (wMediaInfoReqListener == null) {
            wMediaInfoReqListener = new wIMediaInfoReqListener($r0);
        }
        boolean $z0 = PioneerKit.pUnregisterMediaInfoReqListener(wMediaInfoReqListener);
        boolean $z1 = AAM2Kit.pUnregisterMediaInfoReqListener(wMediaInfoReqListener);
        wAppFocusListener = null;
        return $z0 || $z1;
    }

    public static boolean pSetSoundCategory(int $i0) throws  {
        return AAM2Kit.pSetSoundCategory($i0);
    }

    public static int pGetAAMMode() throws  {
        if (isConnection == 0) {
            return 0;
        }
        return isConnection == 2 ? 2 : -1;
    }

    private static AAM2CertifiedInfo ConvertToAAM2(ExtCertifiedInfo $r0) throws  {
        return new AAM2CertifiedInfo($r0.getCompanyName(), $r0.getPackageName(), $r0.getSecretKey());
    }

    private static AAM2TrackSettingInfoReplyData ConvertToAAM2(TrackSettingInfoReplyData $r0) throws  {
        AAM2TrackSettingInfoReplyData $r1 = new AAM2TrackSettingInfoReplyData();
        $r1.setHasTrackInformation($r0.hasTrackInformation());
        $r1.setHasTrackTitle($r0.hasTrackTitle());
        $r1.setHasArtistName($r0.hasArtistName());
        $r1.setHasAlbumTitle($r0.hasAlbumTitle());
        $r1.setHasElapsedTime($r0.hasElapsedTime());
        return $r1;
    }

    private static AAM2TrackInfoReplyDataBase ConvertToAAM2(int $i0, TrackInfoReplyDataBase $r0) throws  {
        if ($i0 == 2) {
            TrackInfoReplyData $r6 = (TrackInfoReplyData) $r0;
            AAM2TrackInfoReplyData $r4 = new AAM2TrackInfoReplyData($r6.getTrackToken());
            $r4.setTrackNumber($r6.getTrackNumber());
            $r4.setDurationTime($r6.getDurationTime());
            return $r4;
        } else if ($i0 == 4) {
            TrackArtistNameReplyData $r7 = (TrackArtistNameReplyData) $r0;
            AAM2TrackArtistNameReplyData $r2 = new AAM2TrackArtistNameReplyData($r7.getTrackToken());
            $r2.setArtistName($r7.getArtistName());
            return $r2;
        } else if ($i0 == 5) {
            TrackAlbumTitleReplyData $r9 = (TrackAlbumTitleReplyData) $r0;
            AAM2TrackAlbumTitleReplyData $r1 = new AAM2TrackAlbumTitleReplyData($r9.getTrackToken());
            $r1.setAlbumTitle($r9.getAlbumTitle());
            return $r1;
        } else if ($i0 == 3) {
            TrackTitleReplyData $r10 = (TrackTitleReplyData) $r0;
            AAM2TrackTitleReplyData aAM2TrackTitleReplyData = new AAM2TrackTitleReplyData($r10.getTrackToken());
            aAM2TrackTitleReplyData.setTrackTitle($r10.getTrackTitle());
            return aAM2TrackTitleReplyData;
        } else if ($i0 != 6) {
            return null;
        } else {
            TrackElapsedTimeNotificationData $r11 = (TrackElapsedTimeNotificationData) $r0;
            AAM2TrackElapsedTimeNotificationData aAM2TrackElapsedTimeNotificationData = new AAM2TrackElapsedTimeNotificationData($r11.getTrackToken());
            aAM2TrackElapsedTimeNotificationData.setElapsedTime($r11.getElapsedTime());
            return aAM2TrackElapsedTimeNotificationData;
        }
    }

    private static ExtDeviceSpecInfo ConvertToAAM1(AAM2MainUnitSpecInfo $r0) throws  {
        ExtDeviceSpecInfo $r1 = new ExtDeviceSpecInfo();
        $r1.setExtDeviceID($r0.getMainUnitID());
        $r1.setPointerNum($r0.getPointerNum());
        $r1.setLocationDevice($r0.getLocationDevice());
        $r1.setRemoteController($r0.getRemoteController());
        $r1.setConnectedMode(2);
        $r1.setCanBusController(0);
        return $r1;
    }

    private static ExtLocation ConvertToAAM1(AAM2LocationInfo $r0) throws  {
        ExtLocation $r1 = new ExtLocation();
        $r1.setLatitude($r0.getLatitude());
        $r1.setLongitude($r0.getLongitude());
        $r1.setAltitude($r0.getAltitude());
        $r1.setSpeed($r0.getSpeed());
        $r1.setBearing($r0.getBearing());
        $r1.setTime($r0.getTime());
        $r1.setRealGpsTime(true);
        $r1.setAccuracy($r0.getAccuracy());
        return $r1;
    }
}
