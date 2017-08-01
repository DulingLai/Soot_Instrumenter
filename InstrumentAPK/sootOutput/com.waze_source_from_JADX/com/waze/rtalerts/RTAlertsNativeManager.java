package com.waze.rtalerts;

import android.app.Activity;
import com.waze.AppService;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.NativeManager;
import com.waze.ResManager;
import com.waze.ifs.async.RunnableExecutor;
import com.waze.ifs.async.RunnableUICallback;
import com.waze.strings.DisplayStrings;
import java.util.HashMap;

public class RTAlertsNativeManager {
    public static final int MENU_ITEM_ID_ACCIDENTS = 8;
    public static final int MENU_ITEM_ID_ALL = 1;
    public static final int MENU_ITEM_ID_CHITCHATS = 16;
    public static final int MENU_ITEM_ID_CLOSURE = 32;
    public static final int MENU_ITEM_ID_OTHER = 64;
    public static final int MENU_ITEM_ID_POLICE = 2;
    public static final int MENU_ITEM_ID_TRAFFIC = 4;
    private static final long POPUP_EVENT_DELAY = 300;
    public static final int RT_ALERTS_LAST_KNOWN_TYPE = 12;
    public static final int RT_ALERT_TYPE_ACCIDENT = 2;
    public static final int RT_ALERT_TYPE_CAMERA = 10;
    public static final int RT_ALERT_TYPE_CHIT_CHAT = 0;
    public static final int RT_ALERT_TYPE_CLOSURE = 12;
    public static final int RT_ALERT_TYPE_CONSTRUCTION = 7;
    public static final int RT_ALERT_TYPE_DYNAMIC = 9;
    public static final int RT_ALERT_TYPE_HAZARD = 5;
    public static final int RT_ALERT_TYPE_OTHER = 6;
    public static final int RT_ALERT_TYPE_PARKED = 11;
    public static final int RT_ALERT_TYPE_PARKING = 8;
    public static final int RT_ALERT_TYPE_POLICE = 1;
    public static final int RT_ALERT_TYPE_TRAFFIC_INFO = 4;
    public static final int RT_ALERT_TYPE_TRAFFIC_JAM = 3;
    protected static RTAlertsNativeManager mInstance = null;
    protected volatile HashMap<Integer, String> mLangHash = null;
    protected RTAlertsAlertData[] mListData = null;

    public interface MapProblemsListener {
        void onComplete(MapProblem[] mapProblemArr);
    }

    public interface IAlertsCommentDataHandler {
        void handler(RTAlertsCommentData[] rTAlertsCommentDataArr);
    }

    public interface IAlertsMenuDataHandler {
        void handler(String str, RTAlertsMenuData[] rTAlertsMenuDataArr);
    }

    public interface IAlertsListDataHandler {

        public static class Data {
            public RTAlertsAlertData[] mAlertsData;
        }

        void handler(Data data);
    }

    class C26048 implements Runnable {
        C26048() {
        }

        public void run() {
            RTAlertsNativeManager.this.InitNativeLayerNTV();
        }
    }

    public interface IAlertsCountHandler {
        void handler(int i);
    }

    private native RTAlertsCommentData[] GetRTAlertsCommentNTV(int i);

    private native int GetRTAlertsCountNTV();

    private native RTAlertsAlertData[] GetRTAlertsListNTV();

    private native RTAlertsMenuData[] GetRTAlertsMenuNTV();

    private native RTAlertsAlertData[] GetRTAlertsOnRouteNTV();

    private native void InitNativeLayerNTV();

    private native void PostCommentNTV(int i, String str);

    private native boolean PostCommentValidateNTV(int i);

    private native void ShowPopUpByIdNTV(int i);

    private native String getMapIssueIconNTV(int i);

    private native MapProblem[] getMapProblemsNTV();

    private native boolean policeSubtypesAllowedNTV();

    private native void reportBadRouteNTV();

    private native void reportMapIssueNTV(String str, int i);

    public native String formatDistanceNTV(int i);

    public static RTAlertsNativeManager create() {
        if (mInstance == null) {
            mInstance = new RTAlertsNativeManager();
            mInstance.initNativeLayer();
            mInstance.loadLangStrings();
        }
        return mInstance;
    }

    public static RTAlertsNativeManager getInstance() {
        create();
        return mInstance;
    }

    public void getAlertsCommentData(final int alertId, final IAlertsCommentDataHandler dataHandler) {
        NativeManager.Post(new RunnableUICallback() {
            RTAlertsCommentData[] mData;

            public void event() {
                this.mData = RTAlertsNativeManager.this.GetRTAlertsCommentNTV(alertId);
            }

            public void callback() {
                dataHandler.handler(this.mData);
            }
        });
    }

    public void getAlertsMenuData(final IAlertsMenuDataHandler dataHandler) {
        NativeManager.Post(new RunnableUICallback() {
            RTAlertsMenuData[] mData;
            String mTitle;

            public void event() {
                this.mTitle = NativeManager.getInstance().getLanguageString(DisplayStrings.DS_REPORTS);
                this.mData = RTAlertsNativeManager.this.GetRTAlertsMenuNTV();
            }

            public void callback() {
                dataHandler.handler(this.mTitle, this.mData);
            }
        });
    }

    public void getAlertsListData(final IAlertsListDataHandler dataHandler) {
        NativeManager.Post(new RunnableUICallback() {
            final Data mData = new Data();

            public void event() {
                this.mData.mAlertsData = RTAlertsNativeManager.this.GetRTAlertsListNTV();
            }

            public void callback() {
                dataHandler.handler(this.mData);
            }
        });
    }

    public void getAlertsOnRouteData(final IAlertsListDataHandler dataHandler) {
        NativeManager.Post(new RunnableUICallback() {
            final Data mData = new Data();

            public void event() {
                this.mData.mAlertsData = RTAlertsNativeManager.this.GetRTAlertsOnRouteNTV();
            }

            public void callback() {
                dataHandler.handler(this.mData);
            }
        });
    }

    public void showAlertPopUp(final int alertId) {
        NativeManager.Post(new Runnable() {
            public void run() {
                if (alertId != -1) {
                    RTAlertsNativeManager.this.ShowPopUpByIdNTV(alertId);
                } else {
                    Logger.ee("Invalid alertId was supplied - cannot show PopUp");
                }
            }
        }, 300);
    }

    public String getLangStr(int res_id) {
        if (this.mLangHash == null) {
            return null;
        }
        return (String) this.mLangHash.get(Integer.valueOf(res_id));
    }

    public void getAlertsCount(final IAlertsCountHandler dataHandler) {
        NativeManager.Post(new RunnableUICallback() {
            int mCount;

            public void event() {
                this.mCount = RTAlertsNativeManager.this.GetRTAlertsCountNTV();
            }

            public void callback() {
                dataHandler.handler(this.mCount);
            }
        });
    }

    public void getMapProblems(final MapProblemsListener listener) {
        NativeManager.Post(new RunnableUICallback() {
            MapProblem[] problems;

            public void event() {
                this.problems = RTAlertsNativeManager.this.getMapProblemsNTV();
            }

            public void callback() {
                listener.onComplete(this.problems);
            }
        });
    }

    public int getMapIssueIcon(int index) {
        return ResManager.GetDrawableId(getMapIssueIconNTV(index));
    }

    private void initNativeLayer() {
        NativeManager.Post(new C26048());
    }

    private void loadLangStrings() {
        NativeManager.registerOnAppStartedEvent(new RunnableExecutor(NativeManager.getInstance()) {
            public void event() {
                HashMap<Integer, String> data = new HashMap();
                NativeManager mgr = NativeManager.getInstance();
                data.put(Integer.valueOf(C1283R.string.rtalerts_list_comments), mgr.getLanguageString(3));
                data.put(Integer.valueOf(C1283R.string.rtalerts_list_thanks), mgr.getLanguageString(DisplayStrings.DS_THANKS));
                data.put(Integer.valueOf(C1283R.string.rtalerts_list_away), mgr.getLanguageString(333));
                data.put(Integer.valueOf(C1283R.string.rtalerts_comments_title), mgr.getLanguageString(3));
                data.put(Integer.valueOf(C1283R.string.rtalerts_comments_add_comment), mgr.getLanguageString(294));
                RTAlertsNativeManager.this.mLangHash = data;
            }
        });
    }

    public boolean isPoliceSubtypesAllowed() {
        return policeSubtypesAllowedNTV();
    }

    public void postCommentValidate(final Activity context4Result, final int alertId) {
        NativeManager.Post(new RunnableUICallback() {
            boolean mValidated;

            public void event() {
                this.mValidated = RTAlertsNativeManager.this.PostCommentValidateNTV(alertId);
            }

            public void callback() {
                if (this.mValidated) {
                    RTAlertsCommentsEditor.start(context4Result, alertId);
                }
            }
        });
    }

    public void postComment(final int alertId, final String comment) {
        NativeManager.Post(new Runnable() {
            public void run() {
                RTAlertsNativeManager.this.PostCommentNTV(alertId, comment);
            }
        });
    }

    public void reportMapIssue(final String desc, final int problemIndex) {
        NativeManager.Post(new Runnable() {
            public void run() {
                RTAlertsNativeManager.this.reportMapIssueNTV(desc, problemIndex);
            }
        });
    }

    public void reportBadRoute() {
        NativeManager.Post(new Runnable() {
            public void run() {
                RTAlertsNativeManager.this.reportBadRouteNTV();
            }
        });
    }

    public void onFbImageDownload(int alertId, int commentId, byte[] image, int width, int height) {
        final byte[] imageCopy = (byte[]) image.clone();
        final int i = alertId;
        final int i2 = commentId;
        final int i3 = width;
        final int i4 = height;
        AppService.Post(new Runnable() {
            public void run() {
                RTAlertsComments.updateFbImage(i, i2, imageCopy, i3, i4);
            }
        });
    }
}
