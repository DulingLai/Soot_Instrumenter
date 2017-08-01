package com.waze;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.waze.carpool.CarpoolNativeManager;
import com.waze.carpool.CarpoolOnboardingManager;
import com.waze.carpool.CarpoolOnboardingManager.INextActionCallback;
import com.waze.carpool.CarpoolPayee;
import com.waze.carpool.CarpoolRidesFragment;
import com.waze.carpool.CarpoolTimerFragment;
import com.waze.carpool.CarpoolUserData;
import com.waze.map.NativeCanvasRenderer;
import com.waze.mywaze.MyWazeNativeManager;
import com.waze.settings.SettingsCarpoolActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.ViewPropertyAnimatorHelper;
import com.waze.view.layout.SwipeableLayout.SwipeableLayoutActionProvider;
import com.waze.view.layout.SwipeableLayout.SwipeableLayoutListener;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.text.WazeTextView;

public class RightSideMenu extends FrameLayout implements SwipeableLayoutListener {
    private INextActionCallback carpoolManagerAnswerCb;
    private Fragment mCarpoolFragment;
    private FrameLayout mCarpoolLayout;
    private CarpoolUserData mCarpoolProfile;
    private Handler mHandler;
    private LinearLayout mHeader;
    private ProgressAnimation mLoadingIcon;
    private ViewGroup mLoadingPopup;
    private boolean mLoginDone;
    private boolean mOnboardingManagerStarted;
    private CarpoolPayee mPayee;
    private boolean mPayeeRequested;
    private boolean mReady;
    private LinearLayout mRootContainer;
    private View mSep;
    private ImageView mSettings;
    private WazeTextView mSubtitle;
    private SwipeableLayoutActionProvider mSwipeableLayoutActionProvider;
    private CarpoolTimerFragment mTimerFragment;
    private WazeTextView mTitle;
    private Runnable timeoutWaiter;

    class C12861 extends Handler {
        C12861() throws  {
        }

        public void handleMessage(Message $r1) throws  {
            if ($r1.what == NativeManager.UH_LOGIN_DONE) {
                RightSideMenu.this.mHandler.removeCallbacks(RightSideMenu.this.timeoutWaiter);
                NativeManager.getInstance().unsetUpdateHandler(NativeManager.UH_LOGIN_DONE, this);
                RightSideMenu.this.setRightSideProgressVisiblity(false);
                RightSideMenu.this.mLoginDone = true;
                RightSideMenu.this.doneWaitingForCarpoolData();
            } else if ($r1.what == CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE) {
                NativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE, this);
                Logger.m36d("RightSideMenu: Received updated payee");
                RightSideMenu.this.mPayeeRequested = false;
                RightSideMenu.this.mPayee = CarpoolNativeManager.getInstance().getCachedPayeeNTV();
                RightSideMenu.this.setSubtitle();
            } else {
                super.handleMessage($r1);
            }
        }
    }

    class C12872 implements INextActionCallback {
        public Context getContext() throws  {
            return null;
        }

        C12872() throws  {
        }

        public void setNextIntent(Intent intent) throws  {
            Logger.m38e("RightSideMenu: received unexpected setNextIntent");
        }

        public void setNextFragment(Fragment $r1) throws  {
            RightSideMenu.this.replaceCarpoolFragment($r1);
        }

        public void setNextResult(int $i0) throws  {
            if ($i0 == -1) {
                Logger.m41i("RightSideMenu: received RESULT OK");
            } else {
                Logger.m38e("RightSideMenu: received unexpected setNextResult");
            }
        }
    }

    class C12883 implements OnClickListener {
        C12883() throws  {
        }

        public void onClick(View v) throws  {
            AppService.getActiveActivity().startActivity(new Intent(AppService.getAppContext(), SettingsCarpoolActivity.class));
        }
    }

    class C12894 implements Runnable {
        C12894() throws  {
        }

        public void run() throws  {
            RightSideMenu.this.removeCarpoolFragment();
        }
    }

    class C12905 implements Runnable {
        C12905() throws  {
        }

        public void run() throws  {
            RightSideMenu.this.setRightSideProgressVisiblity(false);
            NativeManager.getInstance().unsetUpdateHandler(NativeManager.UH_LOGIN_DONE, RightSideMenu.this.mHandler);
            Logger.m43w("RightSideMenu: Opening right side without logging done");
            MsgBox.openMessageBoxTimeout(NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_UHHOHE), NativeManager.getInstance().getLanguageString((int) DisplayStrings.DS_NETWORK_CONNECTION_PROBLEMS__PLEASE_TRY_AGAIN_LATER_), 5, null);
        }
    }

    class C12938 implements Runnable {
        C12938() throws  {
        }

        public void run() throws  {
            RightSideMenu.this.doneWaitingForCarpoolData();
        }
    }

    class C12949 implements Runnable {
        C12949() throws  {
        }

        public void run() throws  {
            RightSideMenu.this.doneWaitingForCarpoolData();
        }
    }

    public RightSideMenu(Context $r1) throws  {
        this($r1, null);
    }

    public RightSideMenu(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public RightSideMenu(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mCarpoolFragment = null;
        this.mOnboardingManagerStarted = false;
        this.mReady = false;
        this.mLoginDone = false;
        this.mHandler = new C12861();
        this.carpoolManagerAnswerCb = new C12872();
        this.mCarpoolProfile = null;
        this.mPayee = null;
        this.mPayeeRequested = false;
        this.timeoutWaiter = new C12905();
        init();
    }

    private void init() throws  {
        if (isInEditMode()) {
            PixelMeasure.setResources(getResources());
        }
        View $r4 = LayoutInflater.from(getContext()).inflate(C1283R.layout.right_side_menu, null);
        this.mRootContainer = (LinearLayout) $r4.findViewById(C1283R.id.rootRightSideContainer);
        this.mHeader = (LinearLayout) $r4.findViewById(C1283R.id.rightSideMenuHeader);
        this.mTitle = (WazeTextView) $r4.findViewById(C1283R.id.rightSideMenuTitle);
        this.mSubtitle = (WazeTextView) $r4.findViewById(C1283R.id.rightSideMenuSubTitle);
        this.mSep = $r4.findViewById(C1283R.id.rightSideMenuSep);
        this.mCarpoolLayout = (FrameLayout) $r4.findViewById(C1283R.id.rideWithPage);
        this.mSubtitle.setVisibility(8);
        this.mCarpoolLayout.setVisibility(0);
        this.mLoadingPopup = (ViewGroup) $r4.findViewById(C1283R.id.loadingPopup);
        this.mLoadingIcon = (ProgressAnimation) $r4.findViewById(C1283R.id.loadingProgressBar);
        ProgressAnimation $r10 = this.mLoadingIcon;
        $r10.start();
        this.mSettings = (ImageView) $r4.findViewById(C1283R.id.rightSideMenuSettings);
        this.mSettings.setOnClickListener(new C12883());
        addView($r4);
        initStrings();
    }

    private void initStrings() throws  {
        this.mTitle.setText(DisplayStrings.displayString(3508));
        setSubtitle();
    }

    public void setSwipeableLayoutActionProvider(SwipeableLayoutActionProvider $r1) throws  {
        this.mSwipeableLayoutActionProvider = $r1;
    }

    private void setSubtitle() throws  {
        if (!ConfigManager.getInstance().getConfigValueBool(15)) {
            this.mSubtitle.setVisibility(8);
        } else if (this.mPayee != null) {
            String $r3 = this.mPayee.currency_code;
            int $i0 = this.mPayee.unpaid_balance;
            Logger.m36d("RightSideMenu: Received details from payee");
            this.mSubtitle.setVisibility(0);
            $r3 = CarpoolNativeManager.getInstance().centsToString(AppService.getAppContext(), $i0, null, $r3);
            this.mSubtitle.setText(String.format(DisplayStrings.displayString(3509), new Object[]{$r3}));
        } else {
            Logger.m36d("RightSideMenu: Payee is null, not displaying balance");
            this.mSubtitle.setVisibility(8);
            if (!this.mPayeeRequested && this.mLoginDone) {
                Logger.m36d("RightSideMenu: Payee is null, requesting payee from RT");
                NativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_PAYMENT_PAYEE, this.mHandler);
                CarpoolNativeManager.getInstance().getPayee();
                this.mPayeeRequested = true;
            }
        }
    }

    public void onSwipeChanged(float $f0) throws  {
        if ($f0 < 0.0f) {
            $f0 = 0.0f;
        }
        if ($f0 > 1.0f) {
            $f0 = 1.0f;
        }
        if ($f0 == 0.0f && getVisibility() == 0) {
            setVisibility(8);
        } else if ($f0 > 0.0f && getVisibility() != 0) {
            setVisibility(0);
        }
        setTranslationX(((float) PixelMeasure.dimension(C1283R.dimen.sideMenuInitialTranslation)) * (1.0f - $f0));
    }

    public void setVisibility(int $i0) throws  {
        boolean $z0 = $i0 != getVisibility();
        super.setVisibility($i0);
        if ($i0 == 0) {
            if ($z0) {
                NativeCanvasRenderer.OnMainCanvasOverlayShown();
            }
            setupCarpoolFragment(true);
            if (AppService.getMainActivity() != null) {
                AppService.getMainActivity().getLayoutMgr().getTooltipManager().closeTooltip(true, 8);
                return;
            }
            return;
        }
        if ($z0) {
            NativeCanvasRenderer.OnMainCanvasOverlayHidden();
            if (!delayIfPaused(new C12894())) {
                removeCarpoolFragment();
            }
        }
        resetOnboardingManagerState();
    }

    private void removeCarpoolFragment() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        if ($r1 != null) {
            if (this.mCarpoolFragment != null) {
                $r1.getFragmentManager().beginTransaction().remove(this.mCarpoolFragment).commit();
                this.mCarpoolFragment = null;
            }
            if (this.mTimerFragment != null) {
                $r1.getFragmentManager().beginTransaction().remove(this.mTimerFragment).commit();
                this.mTimerFragment = null;
            }
        }
    }

    private void resetOnboardingManagerState() throws  {
        if (this.mOnboardingManagerStarted) {
            CarpoolOnboardingManager.getInstance().getNext(CarpoolOnboardingManager.RES_CARPOOL_BACK, this.carpoolManagerAnswerCb);
            this.mOnboardingManagerStarted = false;
        }
    }

    public boolean reactToBackButton() throws  {
        resetOnboardingManagerState();
        return false;
    }

    public void resetOnboardingState() throws  {
        this.mOnboardingManagerStarted = false;
    }

    boolean delayIfPaused(Runnable $r1) throws  {
        MainActivity $r2 = AppService.getMainActivity();
        if ($r2 == null) {
            return true;
        }
        LayoutManager $r3 = $r2.getLayoutMgr();
        if ($r3 == null) {
            return true;
        }
        if (!$r3.isPaused() || $r1 == null) {
            return false;
        }
        $r3.addOnResume($r1);
        return true;
    }

    public void setupCarpoolFragment(final boolean $z0) throws  {
        boolean $z1 = delayIfPaused(new Runnable() {
            public void run() throws  {
                RightSideMenu.this.setupCarpoolFragment($z0);
            }
        });
        if (AppService.getMainActivity() == null) {
            postDelayed(new Runnable() {
                public void run() throws  {
                    RightSideMenu.this.setupCarpoolFragment($z0);
                }
            }, 500);
        } else if (!$z1) {
            if (MyWazeNativeManager.getInstance().HasSocialInfoNTV()) {
                doneWaitingForCarpoolData();
                return;
            }
            setRightSideProgressVisiblity(true);
            NativeManager.getInstance().setUpdateHandler(NativeManager.UH_LOGIN_DONE, this.mHandler);
        }
    }

    private void doneWaitingForCarpoolData() throws  {
        MainActivity $r1 = AppService.getMainActivity();
        boolean $z0 = delayIfPaused(new C12938());
        if ($r1 == null) {
            postDelayed(new C12949(), 500);
        } else if (!$z0) {
            CarpoolNativeManager $r4 = CarpoolNativeManager.getInstance();
            this.mCarpoolProfile = $r4.getCarpoolProfileNTV();
            this.mPayee = $r4.getCachedPayeeNTV();
            if (this.mCarpoolProfile == null || !CarpoolOnboardingManager.isDriverOnboarded()) {
                if (!this.mOnboardingManagerStarted) {
                    this.mHeader.setVisibility(8);
                    CarpoolOnboardingManager $r8 = CarpoolOnboardingManager.getInstance();
                    this.mOnboardingManagerStarted = true;
                    $r8.getNext(-999, this.carpoolManagerAnswerCb);
                }
            } else if ($r4.configGetOpenTimeLeftNTV() == 0) {
                moveToCarpoolFragment($r1);
            } else {
                moveToTimerFragment($r1);
            }
        }
    }

    private void moveToCarpoolFragment(MainActivity $r1) throws  {
        this.mHeader.setVisibility(0);
        setSubtitle();
        this.mSettings.setVisibility(0);
        if (this.mCarpoolFragment != null) {
            $r1.getFragmentManager().beginTransaction().remove(this.mCarpoolFragment).commit();
            this.mCarpoolFragment = null;
        }
        if (this.mTimerFragment != null) {
            $r1.getFragmentManager().beginTransaction().remove(this.mTimerFragment).commit();
            this.mTimerFragment = null;
        }
        setRightSideProgressVisiblity(false);
        this.mCarpoolFragment = new CarpoolRidesFragment();
        $r1.getFragmentManager().beginTransaction().add(C1283R.id.rideWithPage, this.mCarpoolFragment).commit();
        this.mReady = true;
    }

    private void moveToTimerFragment(MainActivity $r1) throws  {
        this.mHeader.setVisibility(0);
        setSubtitle();
        this.mSettings.setVisibility(0);
        setRightSideProgressVisiblity(false);
        if (this.mTimerFragment == null) {
            this.mTimerFragment = new CarpoolTimerFragment();
            $r1.getFragmentManager().beginTransaction().add(C1283R.id.rideWithPage, this.mTimerFragment).commit();
        }
        this.mReady = true;
    }

    public CarpoolRidesFragment getCarpoolRidesFragment() throws  {
        return !(this.mCarpoolFragment instanceof CarpoolRidesFragment) ? null : (CarpoolRidesFragment) this.mCarpoolFragment;
    }

    public void replaceCarpoolFragment(Fragment $r1) throws  {
        final Fragment fragment = $r1;
        boolean $z0 = delayIfPaused(new Runnable() {
            public void run() throws  {
                RightSideMenu.this.replaceCarpoolFragment(fragment);
            }
        });
        MainActivity $r3 = AppService.getMainActivity();
        if ($r3 == null) {
            fragment = $r1;
            postDelayed(new Runnable() {
                public void run() throws  {
                    RightSideMenu.this.replaceCarpoolFragment(fragment);
                }
            }, 500);
        } else if (!$z0) {
            if ($r1 != null) {
                if (($r1 instanceof CarpoolRidesFragment) || ($r1 instanceof CarpoolTimerFragment)) {
                    this.mHeader.setVisibility(0);
                    setSubtitle();
                    this.mSettings.setVisibility(8);
                } else {
                    this.mHeader.setVisibility(8);
                }
                if (this.mTimerFragment != null) {
                    $r3.getFragmentManager().beginTransaction().remove(this.mTimerFragment).commit();
                    this.mTimerFragment = null;
                }
                if (this.mCarpoolFragment != null) {
                    $r3.getFragmentManager().beginTransaction().setCustomAnimations(C1283R.animator.fade_in, C1283R.animator.fade_out).remove(this.mCarpoolFragment).commit();
                    this.mCarpoolFragment = null;
                    $r3.getFragmentManager().beginTransaction().setCustomAnimations(C1283R.animator.fade_in, C1283R.animator.fade_out).add(C1283R.id.rideWithPage, $r1).commit();
                } else {
                    $r3.getFragmentManager().beginTransaction().add(C1283R.id.rideWithPage, $r1).commit();
                }
                this.mCarpoolFragment = $r1;
            } else {
                Logger.m38e("FriendSideMenu: received null fragment");
            }
            this.mReady = true;
            setRightSideProgressVisiblity(false);
        }
    }

    public boolean isReady() throws  {
        return this.mReady;
    }

    public void setRightSideProgressVisiblity(boolean $z0) throws  {
        if ($z0 && this.mLoadingPopup.getVisibility() != 0) {
            this.mLoadingPopup.setVisibility(0);
            this.mLoadingPopup.setScaleX(0.0f);
            this.mLoadingPopup.setScaleY(0.0f);
            this.mLoadingPopup.setAlpha(0.0f);
            ViewPropertyAnimatorHelper.initAnimation(this.mLoadingPopup).scaleX(1.0f).scaleY(1.0f).alpha(1.0f).setListener(null);
        } else if (!$z0 && this.mLoadingPopup.getVisibility() == 0) {
            ViewPropertyAnimatorHelper.initAnimation(this.mLoadingPopup).scaleX(0.0f).scaleY(0.0f).alpha(0.0f).setListener(ViewPropertyAnimatorHelper.createGoneWhenDoneListener(this.mLoadingPopup));
        }
    }
}
