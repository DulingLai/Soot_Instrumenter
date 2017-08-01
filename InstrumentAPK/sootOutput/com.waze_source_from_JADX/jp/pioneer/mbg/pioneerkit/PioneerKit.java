package jp.pioneer.mbg.pioneerkit;

import android.content.Context;
import jp.pioneer.mbg.pioneerkit.p005a.p007b.C3402a;
import jp.pioneer.mbg.pioneerkit.p005a.p007b.C3403b;
import jp.pioneer.mbg.pioneerkit.p008b.C3408a;
import jp.pioneer.mbg.pioneerkit.p008b.C3433z;
import jp.pioneer.mbg.pioneerkit.replydata.C3439b;
import jp.pioneer.mbg.pioneerkit.replydata.C3440a;
import jp.pioneer.mbg.pioneerkit.replydata.TrackInfoReplyDataBase;
import jp.pioneer.mbg.pioneerkit.replydata.TrackSettingInfoReplyData;

public class PioneerKit {
    public static final int ADVANCED_APP_MODE = 0;
    public static final int AV_APP_CTRL_MODE = 1;
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

    public static boolean pIsAdvancedAppMode() {
        return C3408a.m679b().m725d();
    }

    public static boolean pIsAvAppControlMode() {
        return C3408a.m679b().m735k();
    }

    public static boolean pIsDriveStopping() {
        return C3408a.m679b().m730f();
    }

    public static boolean pIsFocused() {
        return C3408a.m679b().m736l();
    }

    public static boolean pIsParking() {
        return C3408a.m679b().m730f();
    }

    public static boolean pIsParkingSwitch() {
        return C3408a.m679b().m727e();
    }

    public static boolean pRegisterAppFocusListener(IExtAppFocusListener iExtAppFocusListener) {
        return C3408a.m679b().m703a(iExtAppFocusListener);
    }

    public static boolean pRegisterLocationListener(IExtLocationListener iExtLocationListener) {
        return C3408a.m679b().m704a(iExtLocationListener);
    }

    public static boolean pRegisterMediaInfoReqListener(IExtMediaInfoReqListener iExtMediaInfoReqListener) {
        return C3408a.m679b().m705a(iExtMediaInfoReqListener);
    }

    public static boolean pRegisterRemoteCtrlListener(IExtRemoteCtrlListener iExtRemoteCtrlListener) {
        return C3408a.m679b().m706a(iExtRemoteCtrlListener);
    }

    public static boolean pRegisterSpecificDataListener(IExtSpecificDataListener iExtSpecificDataListener) {
        return C3408a.m679b().m708a(iExtSpecificDataListener);
    }

    public static void pSendSpecificData(byte[] bArr) {
        C3408a.m679b().m700a(bArr);
    }

    public static boolean pSendTrackInfo(int i, TrackInfoReplyDataBase trackInfoReplyDataBase) {
        byte[] a = C3440a.m787a(i, trackInfoReplyDataBase);
        return a == null ? false : C3408a.m679b().m701a((byte) i, a);
    }

    public static boolean pSendTrackInfoSetting(TrackSettingInfoReplyData trackSettingInfoReplyData) {
        byte[] a = C3440a.m789a((C3439b) trackSettingInfoReplyData);
        return a == null ? false : C3408a.m679b().m719b(a);
    }

    public static boolean pSetMediaPlayerStatus(int i) {
        return C3408a.m679b().m721c(i);
    }

    public static boolean pStartPioneerKit(Context context, IExtRequiredListener iExtRequiredListener) {
        if (context == null || iExtRequiredListener == null) {
            return false;
        }
        C3403b.m661a();
        C3402a.m660a(C3433z.m763b(context));
        C3408a.m679b().m707a(iExtRequiredListener);
        return C3408a.m679b().m722c(context);
    }

    public static boolean pStopPioneerKit(Context context, IExtRequiredListener iExtRequiredListener) {
        if (context == null || iExtRequiredListener == null) {
            return false;
        }
        if (iExtRequiredListener instanceof IExtLocationListener) {
            C3408a.m679b().m714b((IExtLocationListener) iExtRequiredListener);
        }
        if (iExtRequiredListener instanceof IExtRemoteCtrlListener) {
            C3408a.m679b().m716b((IExtRemoteCtrlListener) iExtRequiredListener);
        }
        if (iExtRequiredListener instanceof IExtMediaInfoReqListener) {
            C3408a.m679b().m715b((IExtMediaInfoReqListener) iExtRequiredListener);
        }
        if (iExtRequiredListener instanceof IExtAppFocusListener) {
            C3408a.m679b().m713b((IExtAppFocusListener) iExtRequiredListener);
        }
        C3408a.m679b().m717b(iExtRequiredListener);
        C3408a.m679b().m723d(context);
        return true;
    }

    public static boolean pUnregisterAppFocusListener(IExtAppFocusListener iExtAppFocusListener) {
        return C3408a.m679b().m713b(iExtAppFocusListener);
    }

    public static boolean pUnregisterLocationListener(IExtLocationListener iExtLocationListener) {
        return C3408a.m679b().m714b(iExtLocationListener);
    }

    public static boolean pUnregisterMediaInfoReqListener(IExtMediaInfoReqListener iExtMediaInfoReqListener) {
        return C3408a.m679b().m715b(iExtMediaInfoReqListener);
    }

    public static boolean pUnregisterRemoteCtrlListener(IExtRemoteCtrlListener iExtRemoteCtrlListener) {
        return C3408a.m679b().m716b(iExtRemoteCtrlListener);
    }

    public static boolean pUnregisterSpecificDataListener(IExtSpecificDataListener iExtSpecificDataListener) {
        return C3408a.m679b().m718b(iExtSpecificDataListener);
    }
}
