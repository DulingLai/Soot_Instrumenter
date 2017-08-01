package com.waze.carpool;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import com.waze.AppService;
import com.waze.ConfigManager;
import com.waze.LayoutManager;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.async.UpdateHandlers.MicroHandler;
import com.waze.ifs.async.UpdateHandlers.MicroHandler.MicroHandlerCallback;
import com.waze.mywaze.MyWazeData;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.mywaze.MyWazeNativeManager.MyWazeDataHandler;
import com.waze.navigate.AddHomeWorkActivity;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToHasAddressCallback;
import com.waze.phone.PhoneInputView;
import com.waze.phone.PhoneRegisterActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.TicketRoller;
import com.waze.utils.TicketRoller.Type;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;

public class CarpoolOnboardingManager implements MicroHandlerCallback {
    public static final int FLOW_HAS_RIDES = 2;
    public static final int FLOW_NO_RIDES = 1;
    public static final int NO_RESULT = -999;
    public static int REQ_CARPOOL_JOIN_ACTIVITY = TicketRoller.get(Type.ActivityResult);
    public static final int RES_CARPOOL_ACCEPT = TicketRoller.get(Type.ActivityResult);
    public static final int RES_CARPOOL_ACCEPT_RTR = TicketRoller.get(Type.ActivityResult);
    public static final int RES_CARPOOL_BACK = TicketRoller.get(Type.ActivityResult);
    public static final int RES_CARPOOL_DECLINE = TicketRoller.get(Type.ActivityResult);
    public static final int RES_CARPOOL_LATER = TicketRoller.get(Type.ActivityResult);
    public static final int START_JOIN_DIRECTLY = -888;
    private static CarpoolOnboardingManager mInstance = null;
    private boolean mBoardedThisSession = false;
    private boolean mCalledFromPush = false;
    private boolean mCalledFromSettings = false;
    private CarpoolNativeManager mCpnm;
    private int mFlow = 1;
    private MicroHandler mHandler = new MicroHandler(this);
    private boolean mHaveHomeWork = false;
    private boolean mHomeWorkReceived = false;
    private boolean mInProcess = false;
    private boolean mIsOnboarded;
    private boolean mIsRealtimeRide = false;
    private INextActionCallback mNextAnswerCb = null;
    private OnboardingState mNextState = null;
    private NativeManager mNm;
    private int mResult = -999;
    private boolean mRideAmountReceived = false;
    private int mRidesAmount = 0;
    private boolean mStartedOnboarding = false;
    private MyWazeData mWazeData = null;
    Runnable timeoutRunnable = new C14863();
    Runnable waitForInit = new C14874();

    public interface INextActionCallback {
        Context getContext() throws ;

        void setNextFragment(Fragment fragment) throws ;

        void setNextIntent(Intent intent) throws ;

        void setNextResult(int i) throws ;
    }

    class C14841 implements MyWazeDataHandler {
        C14841() throws  {
        }

        public void onMyWazeDataReceived(MyWazeData $r1) throws  {
            CarpoolOnboardingManager.this.mWazeData = $r1;
        }
    }

    class C14863 implements Runnable {
        C14863() throws  {
        }

        public void run() throws  {
            CarpoolOnboardingManager.this.mCpnm.unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDES_COUNTED, CarpoolOnboardingManager.this.mHandler);
            Logger.m38e("CarpoolOnboardingActivity: Timeout occurred when trying get rides amount and has home work");
            CarpoolOnboardingManager.this.start();
        }
    }

    class C14874 implements Runnable {
        C14874() throws  {
        }

        public void run() throws  {
            if (CarpoolOnboardingManager.this.mNextState == null) {
                Logger.m43w("CarpoolOnboardingActivity:waitForInit: mNextState == null");
                CarpoolOnboardingManager.this.mHandler.postDelayed(CarpoolOnboardingManager.this.waitForInit, 200);
                return;
            }
            Logger.m41i("CarpoolOnboardingActivity:waitForInit: received mNextState");
            CarpoolOnboardingManager.this.mNm.CloseProgressPopup();
            CarpoolOnboardingManager.this.returnAnswer();
        }
    }

    public abstract class OnboardingState {
        static final /* synthetic */ boolean $assertionsDisabled = (!CarpoolOnboardingManager.class.desiredAssertionStatus());
        protected OnboardingState mPrev = null;

        protected abstract void activate(INextActionCallback iNextActionCallback) throws ;

        public OnboardingState(OnboardingState $r2) throws  {
            this.mPrev = $r2;
        }

        public void getNext(int $i0, INextActionCallback cb) throws  {
            Logger.m36d("CarpoolOnboardingManager: OnboardingState: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                Logger.m41i("CarpoolOnboardingManager: OnboardingState: res back, returning to previous class " + this.mPrev.toString());
                CarpoolOnboardingManager.this.mNextState = this.mPrev;
            } else if ($i0 == -1) {
                Logger.m41i("CarpoolOnboardingManager: OnboardingState: res OK, restarting flow");
                CarpoolOnboardingManager.this.mNextState = new StartState(null);
            } else {
                Logger.m38e("CarpoolOnboardingManager: OnboardingState: received illegal result: " + $i0);
                CarpoolOnboardingManager.this.init();
                CarpoolOnboardingManager.mInstance.getNext($i0, CarpoolOnboardingManager.this.mNextAnswerCb);
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
            }
        }
    }

    class AddPhoto extends OnboardingState {
        private OnboardingState actual;
        private boolean mPicTimeoutOccurred = false;

        public AddPhoto(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: AddPhoto: activate; ");
            final Context $r5 = CarpoolOnboardingManager.this.mNextAnswerCb.getContext();
            if ($r5 == null) {
                Logger.m38e("CarpoolOnboardingManager:AddPhoto: received null context");
                return;
            }
            CarpoolOnboardingManager.this.mNm.OpenProgressPopup("");
            CarpoolUserData $r8 = CarpoolOnboardingManager.this.mCpnm.getCarpoolProfileNTV();
            String $r9 = null;
            if ($r8 != null) {
                $r9 = $r8.getImage();
            }
            if ($r9 == null || $r9.isEmpty()) {
                callPicStage($r1, $r5, true);
                return;
            }
            final INextActionCallback iNextActionCallback = $r1;
            final C14881 $r2 = new Runnable() {
                public void run() throws  {
                    AddPhoto.this.mPicTimeoutOccurred = true;
                    AddPhoto.this.callPicStage(iNextActionCallback, $r5, true);
                }
            };
            this.mPicTimeoutOccurred = false;
            CarpoolOnboardingManager.this.mHandler.postDelayed($r2, 10000);
            final INextActionCallback iNextActionCallback2 = $r1;
            VolleyManager.getInstance().loadImageFromUrl($r9, new ImageRequestListener() {
                public boolean receivedCacheResponse = false;

                public void onImageLoadComplete(Bitmap bitmap, Object token, long duration) throws  {
                    if (AddPhoto.this.mPicTimeoutOccurred) {
                        Logger.m43w("CarpoolOnboardingManager: AddPhoto: onImageLoadComplete: timeout occurred. Not continuing");
                        return;
                    }
                    CarpoolOnboardingManager.this.mHandler.removeCallbacks($r2);
                    CarpoolOnboardingManager.this.mNm.unlockProgressPopup();
                    CarpoolOnboardingManager.this.mNm.CloseProgressPopup();
                    if (CarpoolOnboardingManager.this.mNextState == AddPhoto.this) {
                        Logger.m43w("CarpoolOnboardingManager: AddPhoto: onImageLoadComplete: Add pic stage already called, cannot skip it");
                        return;
                    }
                    Logger.m41i("CarpoolOnboardingManager: AddPhoto: onImageLoadComplete: image exists; skipping this state");
                    AddPhoto.this.actual = AddPhoto.this.mPrev;
                    AddPhoto.this.getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, iNextActionCallback2);
                }

                public void onImageLoadFailed(Object token, long duration) throws  {
                    if (CarpoolOnboardingManager.this.mNextState == AddPhoto.this || AddPhoto.this.mPicTimeoutOccurred) {
                        Logger.m43w("CarpoolOnboardingManager: AddPhoto: onImageLoadFailed: Add pic stage already called, no point to continue");
                    } else if (this.receivedCacheResponse) {
                        CarpoolOnboardingManager.this.mHandler.removeCallbacks($r2);
                        Logger.m36d("CarpoolOnboardingManager: AddPhoto: Waze data image URL failed, not passing image");
                        AddPhoto.this.callPicStage(iNextActionCallback2, $r5, true);
                    } else {
                        Logger.m36d("CarpoolOnboardingManager: AddPhoto: received from cache");
                        this.receivedCacheResponse = true;
                    }
                }
            });
        }

        private void callPicStage(INextActionCallback $r1, Context $r2, boolean $z0) throws  {
            CarpoolOnboardingManager.this.mNm.unlockProgressPopup();
            CarpoolOnboardingManager.this.mNm.CloseProgressPopup();
            Logger.m41i("CarpoolOnboardingManager: AddPhoto: empty image");
            CarpoolOnboardingManager.this.mNextState = this;
            this.actual = this;
            Intent $r3 = new Intent($r2, CarpoolAddPhotoActivity.class);
            $r3.putExtra(CarpoolAddPhotoActivity.INTENT_USER_PICTURE_MANDATORY, $z0);
            $r3.putExtra(CarpoolAddPhotoActivity.INTENT_USER_PICTURE_ARRIVE_FROM, 1);
            $r1.setNextIntent($r3);
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: AddPhoto: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: AddPhoto: res accepted, moving to next class");
                new FinishState(this.actual).activate($r1);
            } else if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_LATER) {
                Logger.m41i("CarpoolOnboardingManager: AddPhoto: res later, moving to next class");
                new FinishState(this.actual).activate($r1);
            } else {
                super.getNext($i0, $r1);
            }
        }
    }

    public class FinishState extends OnboardingState {
        public FinishState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: FinishState: activate; ");
            final NativeManager $r2 = NativeManager.getInstance();
            CarpoolOnboardingManager.this.mBoardedThisSession = true;
            if (CarpoolOnboardingManager.this.mFlow == 1) {
                $r1.setNextResult(-1);
                CarpoolOnboardingManager.this.init();
                MainActivity $r4 = AppService.getMainActivity();
                if ($r4 != null) {
                    LayoutManager $r5 = $r4.getLayoutMgr();
                    if (!($r5 == null || $r5.getRightSideMenu() == null)) {
                        $r5.getRightSideMenu().resetOnboardingState();
                        $r5.refreshCarpoolPanel();
                    }
                }
                Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_ONBOARDING_ALL_DONE_SHOWN);
                if (CarpoolNativeManager.getInstance().configGetOpenTimeLeftNTV() == 0) {
                    MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ONBOARDING_DONE_TITLE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ONBOARDING_DONE_BODY), true, null, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ONBOARDING_DONE_BUTTON), null, -1, "carpool_end_of_onboarding", null, true);
                    return;
                }
                NativeManager.getInstance().OpenProgressIconPopup(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_ONBOARDING_DONE_SMALL), "sign_up_big_v");
            } else if (CarpoolOnboardingManager.this.mFlow == 2) {
                $r2.OpenProgressIconPopup(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_POP_PROFILE_CREATED), "sign_up_big_v");
                final INextActionCallback iNextActionCallback = $r1;
                CarpoolOnboardingManager.this.mHandler.postDelayed(new Runnable() {
                    public void run() throws  {
                        $r2.CloseProgressPopup();
                        iNextActionCallback.setNextResult(-1);
                        CarpoolOnboardingManager.this.init();
                    }
                }, 1000);
            }
        }

        public void getNext(int $i0, INextActionCallback cb) throws  {
            Logger.m36d("CarpoolOnboardingManager: FinishState: getNext; res=" + $i0);
        }
    }

    public class FromToOnboardingState extends OnboardingState {
        public FromToOnboardingState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RegularOnboardingState: activate; ");
            CarpoolOnboardingManager.this.mNextState = this;
            $r1.setNextFragment(new JoinCarpoolFromToFragment());
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RegularOnboardingState: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: RegularOnboardingState: res accepted, moving to next class");
            } else {
                super.getNext($i0, $r1);
            }
        }
    }

    class GoogleRegister extends OnboardingState {
        public GoogleRegister(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: GoogleRegister: activate; ");
            Context $r5 = CarpoolOnboardingManager.this.mNextAnswerCb.getContext();
            if ($r5 == null) {
                Logger.m38e("CarpoolOnboardingManager:GoogleRegister: received null context");
                return;
            }
            Logger.m41i("CarpoolOnboardingManager: GoogleRegister: registering");
            $r1.setNextIntent(new Intent($r5, CarpoolGoogleSignInActivity.class));
            CarpoolOnboardingManager.this.mNextState = this;
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: GoogleRegister: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: GoogleRegister: res accepted, moving to next class");
                MyWazeNativeManager.getInstance().setGuestUserNTV(false);
                new HomeWorkState(this).activate($r1);
                return;
            }
            super.getNext($i0, $r1);
        }
    }

    public class HomeWorkState extends OnboardingState {
        OnboardingState actual = null;
        INextActionCallback mCb = null;

        public HomeWorkState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: HomeWorkState: activate; ");
            this.mCb = $r1;
            if (!CarpoolOnboardingManager.this.mHaveHomeWork) {
                if (CarpoolOnboardingManager.this.mIsRealtimeRide) {
                    Logger.m43w("CarpoolOnboardingManager: HomeWorkState: DO NOT have H/W but RTR, moving to next class");
                } else {
                    doAction($r1);
                    return;
                }
            }
            if (CarpoolOnboardingManager.this.mFlow != 1 || CarpoolOnboardingManager.this.mIsRealtimeRide) {
                Logger.m41i("CarpoolOnboardingManager: HomeWorkState: already have H/W, moving to next class");
                this.actual = this.mPrev;
                getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, $r1);
                return;
            }
            doAction($r1);
        }

        private void doAction(INextActionCallback $r1) throws  {
            this.actual = this;
            Context $r3 = $r1.getContext();
            if ($r3 == null) {
                Logger.m38e("CarpoolOnboardingManager:HomeWorkState: received null context");
                return;
            }
            Logger.m41i("CarpoolOnboardingManager: HomeWorkState: adding H/W");
            Intent $r2 = new Intent($r3, AddHomeWorkActivity.class);
            $r2.putExtra("carpool", false);
            $r2.putExtra("onboarding", true);
            $r2.putExtra("edit_home", false);
            $r2.putExtra("edit_work", false);
            $r1.setNextIntent($r2);
            CarpoolOnboardingManager.this.mNextState = this;
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: HomeWorkState: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: HomeWorkState: res accepted, moving to next class");
                new AddPhoto(this.actual).activate($r1);
                return;
            }
            super.getNext($i0, $r1);
        }
    }

    public class RegularOnboardingState extends OnboardingState {
        public RegularOnboardingState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RegularOnboardingState: activate; ");
            CarpoolOnboardingManager.this.mNextState = this;
            $r1.setNextFragment(new JoinCarpoolBFragment());
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RegularOnboardingState: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: RegularOnboardingState: res accepted, moving to next class");
                WazeRegisterState $r2 = new WazeRegisterState(this);
                Logger.m41i("CarpoolOnboardingManager: RegularOnboardingState: Google connect first");
                $r2.activate($r1);
                return;
            }
            super.getNext($i0, $r1);
        }
    }

    public class RidesListState extends OnboardingState {
        public RidesListState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RidesListState: activate; ");
            if (CarpoolOnboardingManager.this.mCpnm.configGetOpenTimeLeftNTV() == 0) {
                CarpoolRidesFragment $r4 = new CarpoolRidesFragment();
                Bundle $r5 = new Bundle();
                $r5.putBoolean("onboarding", true);
                $r4.setArguments($r5);
                $r1.setNextFragment($r4);
                CarpoolOnboardingManager.this.mNextState = this;
                return;
            }
            CarpoolTimerFragment $r6 = new CarpoolTimerFragment();
            $r5 = new Bundle();
            $r5.putBoolean("onboarding", true);
            $r6.setArguments($r5);
            $r1.setNextFragment($r6);
            CarpoolOnboardingManager.this.mNextState = this;
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RidesListState: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: RidesListState: ride accepted, moving to next class");
                new WazeRegisterState(this).activate($r1);
            } else if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_DECLINE) {
                Logger.m41i("CarpoolOnboardingManager: RidesListState: ride rejected, re-initializing");
                if (CarpoolOnboardingManager.this.mRidesAmount <= 1) {
                    CarpoolOnboardingManager.this.init();
                    AppService.getMainActivity().getLayoutMgr().getRightSideMenu().resetOnboardingState();
                    AppService.getMainActivity().getLayoutMgr().refreshCarpoolPanel();
                    return;
                }
                CarpoolOnboardingManager.this.mRidesAmount = CarpoolOnboardingManager.this.mRidesAmount - 1;
            } else if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK) {
                CarpoolOnboardingManager.this.init();
                Logger.m41i("CarpoolOnboardingManager: RidesListState: res back, returning to initial state");
            } else {
                super.getNext($i0, $r1);
            }
        }
    }

    public class RidesOnboardingState extends OnboardingState {
        public RidesOnboardingState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RidesOnboardingState: activate; ");
            JoinCarpoolBFragment $r3 = new JoinCarpoolBFragment();
            Bundle $r2 = new Bundle();
            $r2.putInt("num_rides", CarpoolOnboardingManager.this.mRidesAmount);
            $r2.putBoolean("called_from_push", CarpoolOnboardingManager.this.mCalledFromPush);
            $r3.setArguments($r2);
            $r1.setNextFragment($r3);
            CarpoolOnboardingManager.this.mNextState = this;
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: RidesOnboardingState: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: RidesOnboardingState: res accepted, moving to next class");
                new RidesListState(this).activate($r1);
                return;
            }
            super.getNext($i0, $r1);
        }
    }

    public class StartState extends OnboardingState {
        static final /* synthetic */ boolean $assertionsDisabled = (!CarpoolOnboardingManager.class.desiredAssertionStatus());

        public StartState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback cb) throws  {
            Logger.m38e("CarpoolOnboardingManager: activate called");
            CarpoolOnboardingManager.this.init();
            if (!$assertionsDisabled) {
                throw new AssertionError();
            }
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            CarpoolOnboardingManager.this.getMyWazeData();
            Logger.m36d("CarpoolOnboardingManager: StartState: getNext; res=" + $i0);
            CarpoolOnboardingManager.this.mIsOnboarded = CarpoolOnboardingManager.this.mCpnm.isDriverOnboarded();
            CarpoolOnboardingManager.this.checkForHomeWork(false);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_BACK || $i0 == -1) {
                Logger.m41i("CarpoolOnboardingManager: StartState: received back/ok ret code without starting");
            } else if (CarpoolOnboardingManager.this.mIsOnboarded) {
                Logger.m43w("CarpoolOnboardingManager: StartState: already onboarded");
            } else {
                selectAndStartOnboarding($i0, $r1);
            }
        }

        private void selectAndStartOnboarding(int $i0, INextActionCallback $r1) throws  {
            OnboardingState $r3;
            CarpoolOnboardingManager.this.mStartedOnboarding = true;
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT_RTR || $i0 == CarpoolOnboardingManager.START_JOIN_DIRECTLY) {
                CarpoolOnboardingManager.this.mIsRealtimeRide = true;
                CarpoolOnboardingManager.this.mFlow = 2;
                Logger.m41i("CarpoolOnboardingManager: StartState: Google connect first");
                $r3 = r4;
                OnboardingState r4 = new WazeRegisterState(this);
            } else if (CarpoolOnboardingManager.this.mRidesAmount <= 0 || CarpoolOnboardingManager.this.mCalledFromSettings) {
                CarpoolOnboardingManager.this.mFlow = 1;
                if (ConfigValues.getBoolValue(57)) {
                    Logger.m41i("CarpoolOnboardingManager: StartState: NONE starting from-to join state");
                    $r3 = r6;
                    OnboardingState r6 = new FromToOnboardingState(this);
                } else {
                    Logger.m41i("CarpoolOnboardingManager: StartState: NONE starting regular join state");
                    $r3 = r7;
                    OnboardingState r7 = new RegularOnboardingState(this);
                }
            } else {
                CarpoolOnboardingManager.this.mFlow = 2;
                Logger.m41i("CarpoolOnboardingManager: StartState: NONE starting rides join state");
                $r3 = r5;
                OnboardingState r5 = new RidesOnboardingState(this);
            }
            $r3.activate($r1);
        }
    }

    class WazeRegisterState extends OnboardingState {
        OnboardingState actual = null;

        public WazeRegisterState(OnboardingState $r2) throws  {
            super($r2);
        }

        protected void activate(INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: WazeRegisterState: activate; ");
            CarpoolOnboardingManager.this.mNm.OpenProgressPopup("");
            if (CarpoolUtils.getPhoneIfLoggedIn() != null) {
                skipWazeReg($r1);
                return;
            }
            Context $r6 = CarpoolOnboardingManager.this.mNextAnswerCb.getContext();
            if ($r6 == null) {
                Logger.m38e("CarpoolOnboardingManager:WazeRegisterState: received null context");
            } else if (CarpoolOnboardingManager.this.mFlow != 1) {
                Logger.m41i("CarpoolOnboardingManager: WazeRegisterState: not rideless flow");
                activateWazeReg($r1, $r6);
            } else if (ConfigManager.getInstance().getConfigValueBool(35)) {
                Logger.m41i("CarpoolOnboardingManager: WazeRegisterState: AB test showing waze reg for rideless");
                activateWazeReg($r1, $r6);
            } else {
                Logger.m41i("CarpoolOnboardingManager: WazeRegisterState: AB test NOT showing waze reg for rideless");
                skipWazeReg($r1);
            }
        }

        private void skipWazeReg(INextActionCallback $r1) throws  {
            Logger.m41i("CarpoolOnboardingManager: WazeRegisterState: already registered, moving to next class");
            this.actual = this.mPrev;
            CarpoolOnboardingManager.this.mNm.CloseProgressPopup();
            getNext(CarpoolOnboardingManager.RES_CARPOOL_ACCEPT, $r1);
        }

        private void activateWazeReg(INextActionCallback $r1, Context $r2) throws  {
            Logger.m41i("CarpoolOnboardingManager: WazeRegisterState: registering");
            Intent $r3 = new Intent($r2, PhoneRegisterActivity.class);
            $r3.putExtra(PhoneRegisterActivity.EXTRA_TYPE, 4);
            CarpoolOnboardingManager.this.mNm.CloseProgressPopup();
            $r1.setNextIntent($r3);
            this.actual = this;
            CarpoolOnboardingManager.this.mNextState = this;
        }

        public void getNext(int $i0, INextActionCallback $r1) throws  {
            Logger.m36d("CarpoolOnboardingManager: WazeRegisterState: getNext; res=" + $i0);
            if ($i0 == CarpoolOnboardingManager.RES_CARPOOL_ACCEPT) {
                Logger.m41i("CarpoolOnboardingManager: WazeRegisterState: res accepted, moving to next class");
                new GoogleRegister(this.actual).activate($r1);
                return;
            }
            super.getNext($i0, $r1);
        }
    }

    private CarpoolOnboardingManager() throws  {
    }

    public static synchronized CarpoolOnboardingManager getInstance() throws  {
        CarpoolOnboardingManager $r1;
        synchronized (CarpoolOnboardingManager.class) {
            try {
                if (mInstance != null) {
                    $r1 = mInstance;
                } else {
                    mInstance = new CarpoolOnboardingManager();
                    mInstance.mCpnm = CarpoolNativeManager.getInstance();
                    mInstance.init();
                    $r1 = mInstance;
                }
            } catch (Throwable th) {
                Class cls = CarpoolOnboardingManager.class;
            }
        }
        return $r1;
    }

    public void setIsCalledFromSettings(boolean $z0) throws  {
        if (this.mCalledFromSettings != $z0) {
            init();
            this.mCalledFromSettings = $z0;
        }
    }

    public void setIsCalledFromPush(boolean $z0) throws  {
        this.mCalledFromPush = $z0;
    }

    public void changedSemiBoarded() throws  {
        mInstance.init();
    }

    public static boolean inWazeRegister() throws  {
        return mInstance == null ? false : mInstance.mNextState instanceof WazeRegisterState;
    }

    public static boolean didBoardThisSession() throws  {
        return mInstance == null ? false : mInstance.mBoardedThisSession;
    }

    public static boolean isDriverOnboarded() throws  {
        return CarpoolNativeManager.getInstance().isDriverOnboarded();
    }

    public static boolean wasOnboardingStarted() throws  {
        return mInstance == null ? false : mInstance.mStartedOnboarding;
    }

    public void resetOnboardingManager() throws  {
        Logger.m43w("CarpoolOnboardingManager: resetOnboardingManager, initting");
        init();
    }

    private void init() throws  {
        this.mNextState = null;
        this.mNextAnswerCb = null;
        this.mResult = -999;
        this.mCalledFromSettings = false;
        this.mIsOnboarded = this.mCpnm.isDriverOnboarded();
        this.mNm = NativeManager.getInstance();
        this.mStartedOnboarding = false;
        this.mIsRealtimeRide = false;
        getMyWazeData();
        this.mCpnm.setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDES_COUNTED, this.mHandler);
        this.mCpnm.getRideCount(false);
        checkForHomeWork(true);
        PhoneInputView.getCountryCodes();
        this.mHandler.postDelayed(this.timeoutRunnable, 7000);
    }

    private void getMyWazeData() throws  {
        MyWazeNativeManager.getInstance().getMyWazeData(new C14841());
    }

    private void checkForHomeWork(final boolean $z0) throws  {
        DriveToNativeManager.getInstance().hasHomeAndWork(new DriveToHasAddressCallback() {
            public void hasAddressCallback(boolean $z0) throws  {
                CarpoolOnboardingManager.this.mHaveHomeWork = $z0;
                Logger.m38e("CarpoolOnboardingActivity: Received mHaveHomeWork=" + CarpoolOnboardingManager.this.mHaveHomeWork);
                CarpoolOnboardingManager.this.mHomeWorkReceived = true;
                if ($z0) {
                    CarpoolOnboardingManager.this.waitForContinue();
                }
            }
        });
    }

    public void handleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_RIDES_COUNTED) {
            this.mHandler.removeCallbacks(this.timeoutRunnable);
            CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_RIDES_COUNTED, this.mHandler);
            if ($r1.getData() != null) {
                this.mRidesAmount = $r1.getData().getInt("offersCount", 0);
                Logger.m38e("CarpoolOnboardingActivity: Received ride amount=" + this.mRidesAmount);
            } else {
                Logger.m38e("CarpoolOnboardingActivity: empty bundle when trying get rides amount");
                this.mRidesAmount = 0;
            }
            this.mRideAmountReceived = true;
            waitForContinue();
        }
    }

    private void waitForContinue() throws  {
        if (this.mRideAmountReceived && this.mHomeWorkReceived) {
            Logger.m36d("CarpoolOnboardingActivity: Received hasHomeWork=" + this.mHaveHomeWork + "; rideAmount=" + this.mRidesAmount + "; Can continue");
            start();
            return;
        }
        Logger.m36d("CarpoolOnboardingActivity: Did not receive both hasHomeWork=" + this.mHaveHomeWork + "; rideAmount=" + this.mRidesAmount + "; so cannot continue");
    }

    private void start() throws  {
        this.mNextState = new StartState(null);
    }

    public static MyWazeData getWazeData() throws  {
        return mInstance == null ? null : mInstance.mWazeData;
    }

    public int getRidesAmount() throws  {
        if (this.mRideAmountReceived) {
            return this.mRidesAmount;
        }
        return 0;
    }

    public void getNext(int $i0, INextActionCallback $r1) throws  {
        if (this.mInProcess) {
            Logger.m43w("CarpoolOnboardingManager: getNext called again b4 completed, ignoring");
            return;
        }
        this.mInProcess = true;
        this.mNextAnswerCb = $r1;
        this.mResult = $i0;
        if (this.mNextState == null) {
            Logger.m43w("CarpoolOnboardingManager: getNext: next state still null, not initialized yet");
            this.mNm.OpenProgressPopup("");
            this.mHandler.postDelayed(this.waitForInit, 500);
            return;
        }
        returnAnswer();
    }

    private void returnAnswer() throws  {
        Logger.m41i("CarpoolOnboardingManager: returnAnswer: calling getNext of next state");
        this.mNextState.getNext(this.mResult, this.mNextAnswerCb);
        this.mInProcess = false;
    }
}
