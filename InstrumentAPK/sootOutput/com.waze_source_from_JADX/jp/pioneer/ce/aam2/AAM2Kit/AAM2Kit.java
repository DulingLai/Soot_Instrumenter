package jp.pioneer.ce.aam2.AAM2Kit;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.abaltatech.weblinkserver.WLServer;
import jp.pioneer.ce.aam2.AAM2Kit.p000a.C3331a;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b.C3348a;
import jp.pioneer.ce.aam2.AAM2Kit.p001b.p003b.C3349b;
import jp.pioneer.ce.aam2.AAM2Kit.p004c.C3354a;
import jp.pioneer.ce.aam2.AAM2Kit.p004c.C3379z;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackInfoReplyDataBase;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.AAM2TrackSettingInfoReplyData;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.C3386b;
import jp.pioneer.ce.aam2.AAM2Kit.replydata.C3387a;

public class AAM2Kit {
    public static final int AAM2_LOCATION_INFO_ACCURACY_COARSE = 100;
    public static final int AAM2_LOCATION_INFO_ACCURACY_FINE = 10;
    public static final int AAM2_LOCATION_INFO_ACCURACY_INVALID = -1;
    public static final int AAM2_MAINUNIT_SPEC_LOCATION_GPS = 1;
    public static final int AAM2_MAINUNIT_SPEC_LOCATION_GPS_SENSOR = 2;
    public static final int AAM2_MAINUNIT_SPEC_LOCATION_GPS_SENSOR_PULSE = 3;
    public static final int AAM2_MAINUNIT_SPEC_LOCATION_INVALID = 0;
    public static final int AAM2_MAINUNIT_SPEC_POINTER_1 = 1;
    public static final int AAM2_MAINUNIT_SPEC_POINTER_2 = 2;
    public static final int AAM2_MAINUNIT_SPEC_POINTER_INVALID = 0;
    public static final int AAM2_MAINUNIT_SPEC_REMOTE_CONTROLLER_AV = 1;
    public static final int AAM2_MAINUNIT_SPEC_REMOTE_CONTROLLER_INVALID = 0;
    public static final int AAM2_MEDIA_PLAYER_STATUS_PAUSE = 0;
    public static final int AAM2_MEDIA_PLAYER_STATUS_PLAY = 1;
    public static final int AAM2_REMOTE_CTRL_CMD_AV_FF = 5;
    public static final int AAM2_REMOTE_CTRL_CMD_AV_PAUSE = 2;
    public static final int AAM2_REMOTE_CTRL_CMD_AV_PLAY = 1;
    public static final int AAM2_REMOTE_CTRL_CMD_AV_RW = 6;
    public static final int AAM2_REMOTE_CTRL_CMD_AV_TOGGLE = 0;
    public static final int AAM2_REMOTE_CTRL_CMD_AV_TRACKDOWN = 4;
    public static final int AAM2_REMOTE_CTRL_CMD_AV_TRACKUP = 3;
    public static final int AAM2_SOUND_CATEGORY_MIX_SOUND = 2;
    public static final int AAM2_SOUND_CATEGORY_SOLO_SOUND = 1;
    public static final long AAM2_TRACKINFO_INVALID_TOKEN = -1;
    public static final int AAM2_TRACKINFO_TYPE_ALBUM = 5;
    public static final int AAM2_TRACKINFO_TYPE_ARTIST = 4;
    public static final int AAM2_TRACKINFO_TYPE_ELAPSED_TIME = 6;
    public static final int AAM2_TRACKINFO_TYPE_INFO = 2;
    public static final int AAM2_TRACKINFO_TYPE_TITLE = 3;
    public static final int ADVANCED_APP_MODE_2 = 0;

    public static boolean m51a(Context context) {
        if (context == null) {
            return false;
        }
        Context applicationContext = context.getApplicationContext();
        Intent intent = new Intent("abaltatech.intent.action.bindProtocolDispatcherPrivateService");
        intent.setComponent(new ComponentName("jp.pioneer.mbg.appradio.AppRadioLauncher", "com.abaltatech.protocoldispatcher.ProtocolDispatcherService"));
        if (applicationContext.startService(intent) != null) {
            C3331a.m67a().m71b();
        }
        C3354a.m313a(true);
        return true;
    }

    public static boolean pIsAdvancedAppMode() {
        return C3354a.m316b().m365d();
    }

    public static boolean pIsDriveStopping() {
        return C3354a.m316b().m371f();
    }

    public static boolean pIsFocused() {
        return C3354a.m316b().m378l();
    }

    public static boolean pIsParkingSwitch() {
        return C3354a.m316b().m367e();
    }

    public static boolean pRegisterAppFocusListener(IAAM2AppFocusListener iAAM2AppFocusListener) {
        return C3354a.m316b().m341a(iAAM2AppFocusListener);
    }

    public static boolean pRegisterExtendedDataListener(IAAM2ExtendedDataListener iAAM2ExtendedDataListener) {
        return C3354a.m316b().m342a(iAAM2ExtendedDataListener);
    }

    public static boolean pRegisterLocationListener(IAAM2LocationListener iAAM2LocationListener) {
        return C3354a.m316b().m343a(iAAM2LocationListener);
    }

    public static boolean pRegisterMediaInfoReqListener(IAAM2MediaInfoReqListener iAAM2MediaInfoReqListener) {
        return C3354a.m316b().m344a(iAAM2MediaInfoReqListener);
    }

    public static boolean pRegisterRemoteCtrlListener(IAAM2RemoteCtrlListener iAAM2RemoteCtrlListener) {
        return C3354a.m316b().m345a(iAAM2RemoteCtrlListener);
    }

    public static void pSendExtendedData(byte[] bArr) {
        C3354a.m316b().m337a(bArr);
    }

    public static boolean pSendTrackInfo(int i, AAM2TrackInfoReplyDataBase aAM2TrackInfoReplyDataBase) {
        byte[] a = C3387a.m441a(i, aAM2TrackInfoReplyDataBase);
        return a == null ? false : C3354a.m316b().m338a((byte) i, a);
    }

    public static boolean pSendTrackInfoSetting(AAM2TrackSettingInfoReplyData aAM2TrackSettingInfoReplyData) {
        byte[] a = C3387a.m443a((C3386b) aAM2TrackSettingInfoReplyData);
        return a == null ? false : C3354a.m316b().m358b(a);
    }

    public static boolean pSetMediaPlayerStatus(int i) {
        return C3354a.m316b().m360c(i);
    }

    public static void pSetNonCacheViewClass(Class cls) {
        if (cls != null && WLServer.getInstance() != null) {
            WLServer.getInstance().registerNonCacheView(cls);
        }
    }

    public static boolean pSetSoundCategory(int i) {
        C3354a.m316b().m362d(i);
        return true;
    }

    public static boolean pSoundInterruptionRequest(boolean z) {
        return C3354a.m316b().m373g(z);
    }

    public static boolean pStartAAM2Kit(Context context, IAAM2RequiredListener iAAM2RequiredListener) {
        if (context == null || iAAM2RequiredListener == null) {
            return false;
        }
        WLServer.init(context);
        C3349b.m297a();
        C3348a.m296a(C3379z.m406b(context));
        C3354a.m316b().m346a(iAAM2RequiredListener);
        return C3354a.m316b().m361c(context);
    }

    public static boolean pStopAAM2Kit(Context context, IAAM2RequiredListener iAAM2RequiredListener) {
        if (context == null || iAAM2RequiredListener == null) {
            return false;
        }
        if (iAAM2RequiredListener instanceof IAAM2LocationListener) {
            C3354a.m316b().m354b((IAAM2LocationListener) iAAM2RequiredListener);
        }
        if (iAAM2RequiredListener instanceof IAAM2RemoteCtrlListener) {
            C3354a.m316b().m356b((IAAM2RemoteCtrlListener) iAAM2RequiredListener);
        }
        if (iAAM2RequiredListener instanceof IAAM2MediaInfoReqListener) {
            C3354a.m316b().m355b((IAAM2MediaInfoReqListener) iAAM2RequiredListener);
        }
        if (iAAM2RequiredListener instanceof IAAM2AppFocusListener) {
            C3354a.m316b().m352b((IAAM2AppFocusListener) iAAM2RequiredListener);
        }
        if (iAAM2RequiredListener instanceof C3380c) {
            C3354a.m316b().m347a((C3380c) iAAM2RequiredListener);
        }
        C3354a.m316b().m357b(iAAM2RequiredListener);
        C3354a.m316b().m363d(context);
        C3354a.m316b().m362d(0);
        return true;
    }

    public static boolean pUnregisterAppFocusListener(IAAM2AppFocusListener iAAM2AppFocusListener) {
        return C3354a.m316b().m352b(iAAM2AppFocusListener);
    }

    public static boolean pUnregisterExtendedDataListener(IAAM2ExtendedDataListener iAAM2ExtendedDataListener) {
        return C3354a.m316b().m353b(iAAM2ExtendedDataListener);
    }

    public static boolean pUnregisterLocationListener(IAAM2LocationListener iAAM2LocationListener) {
        return C3354a.m316b().m354b(iAAM2LocationListener);
    }

    public static boolean pUnregisterMediaInfoReqListener(IAAM2MediaInfoReqListener iAAM2MediaInfoReqListener) {
        return C3354a.m316b().m355b(iAAM2MediaInfoReqListener);
    }

    public static boolean pUnregisterRemoteCtrlListener(IAAM2RemoteCtrlListener iAAM2RemoteCtrlListener) {
        return C3354a.m316b().m356b(iAAM2RemoteCtrlListener);
    }
}
