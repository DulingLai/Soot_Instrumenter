package com.waze.view.popups;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.ifs.ui.ActivityBase;
import com.waze.navigate.AddressItem;
import com.waze.navigate.DriveToNativeManager;
import com.waze.navigate.DriveToNativeManager.DriveToGetAddressItemArrayCallback;
import com.waze.navigate.DriveToNavigateCallback;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.view.timer.TimerView;

public class PickupCanceledPopUp extends PopUp implements DriveToNavigateCallback {
    private boolean mIsShown = false;
    private LayoutManager mLayoutManager;

    class C31772 implements OnClickListener {
        C31772() {
        }

        public void onClick(View v) {
            PickupCanceledPopUp.this.onClose();
        }
    }

    class C31783 implements Runnable {
        C31783() {
        }

        public void run() {
            PickupCanceledPopUp.this.findViewById(C1283R.id.puCanceledPopupTitleText).requestLayout();
            PickupCanceledPopUp.this.findViewById(C1283R.id.puCanceledPopupTitleText).invalidate();
        }
    }

    public PickupCanceledPopUp(Context context, LayoutManager layoutManager) {
        super(context, layoutManager);
        this.mLayoutManager = layoutManager;
        init();
    }

    public boolean onBackPressed() {
        hide();
        return true;
    }

    private void setUpTitle(String titleText) {
        ((TextView) findViewById(C1283R.id.puCanceledPopupTitleText)).setText(titleText);
    }

    private void setUpButton(String text, final AddressItem dest) {
        TextView but = (TextView) findViewById(C1283R.id.puCanceledPopupButDrive);
        but.setText(text);
        but.setOnClickListener(new OnClickListener() {

            class C31731 implements DriveToGetAddressItemArrayCallback {
                C31731() {
                }

                public void getAddressItemArrayCallback(AddressItem[] ai) {
                    if (ai == null || ai.length <= 0) {
                        DriveToNativeManager.getInstance().navigate(dest, PickupCanceledPopUp.this, false, false, true);
                    } else {
                        DriveToNativeManager.getInstance().navigate(ai[0], PickupCanceledPopUp.this, false, false, true);
                    }
                }
            }

            class C31742 implements DriveToGetAddressItemArrayCallback {
                C31742() {
                }

                public void getAddressItemArrayCallback(AddressItem[] ai) {
                    if (ai == null || ai.length <= 0) {
                        DriveToNativeManager.getInstance().navigate(dest, PickupCanceledPopUp.this, false, false, true);
                    } else {
                        DriveToNativeManager.getInstance().navigate(ai[0], PickupCanceledPopUp.this, false, false, true);
                    }
                }
            }

            class C31753 implements DriveToGetAddressItemArrayCallback {
                C31753() {
                }

                public void getAddressItemArrayCallback(AddressItem[] ais) {
                    boolean found = false;
                    if (ais != null && ais.length > 0) {
                        for (AddressItem ai : ais) {
                            if (ai != null && ai.getTitle() != null && ai.getTitle().contentEquals(dest.getTitle())) {
                                found = true;
                                DriveToNativeManager.getInstance().navigate(ai, PickupCanceledPopUp.this, false, false, true);
                                break;
                            }
                        }
                    }
                    if (!found) {
                        DriveToNativeManager.getInstance().navigate(dest, PickupCanceledPopUp.this, false, false, true);
                    }
                }
            }

            public void onClick(View v) {
                int destType = dest.getType();
                if (destType == 1) {
                    DriveToNativeManager.getInstance().getHome(new C31731());
                } else if (destType == 3) {
                    DriveToNativeManager.getInstance().getWork(new C31742());
                } else if (destType == 5) {
                    DriveToNativeManager.getInstance().getFavorites(new C31753(), false);
                } else {
                    DriveToNativeManager.getInstance().navigate(dest, PickupCanceledPopUp.this, false, false, true);
                }
            }
        });
    }

    private void setRiderImageUrl(String imageUrl, ActivityBase ab) {
        ImageView riderImage = (ImageView) findViewById(C1283R.id.puCanceledPopupRiderImage);
        riderImage.setImageResource(C1283R.drawable.ridecard_profilepic_placeholder);
        if (imageUrl != null && !imageUrl.isEmpty()) {
            ImageRepository.instance.getImage(imageUrl, 2, riderImage, null, ab);
        }
    }

    private void setText(String body) {
        ((TextView) findViewById(C1283R.id.puCanceledPopupBody)).setText(body);
    }

    private void stopCloseTimer() {
        ((TimerView) findViewById(C1283R.id.puCanceledPopupCloseButtonTimer)).stop();
    }

    private void setCloseTimer(int iTimeOut) {
        ((TimerView) findViewById(C1283R.id.puCanceledPopupCloseButtonTimer)).reset();
        ((TimerView) findViewById(C1283R.id.puCanceledPopupCloseButtonTimer)).setWhiteColor();
        ((TimerView) findViewById(C1283R.id.puCanceledPopupCloseButtonTimer)).setTime(iTimeOut);
        ((TimerView) findViewById(C1283R.id.puCanceledPopupCloseButtonTimer)).start();
    }

    public void hide() {
        this.mIsShown = false;
        stopCloseTimer();
        this.mLayoutManager.dismiss(this);
    }

    private void onClose() {
        hide();
    }

    private void init() {
        ((LayoutInflater) getContext().getSystemService("layout_inflater")).inflate(C1283R.layout.pickup_canceled_popup, this);
        findViewById(C1283R.id.puCanceledPopupCloseButton).setOnClickListener(new C31772());
    }

    public void show(String name, String imageUrl, ActivityBase ab, int timeout, AddressItem dest) {
        if (this.mIsShown) {
            hide();
        }
        NativeManager nm = NativeManager.getInstance();
        setUpTitle(nm.getLanguageString(DisplayStrings.DS_CARPOOL_CANCELED_PICKUP_TITLE));
        setText(String.format(nm.getLanguageString(DisplayStrings.DS_CARPOOL_PS_CANCELED_PICKUP), new Object[]{name}));
        setRiderImageUrl(imageUrl, ab);
        setUpButton(nm.getLanguageString(DisplayStrings.DS_CARPOOL_CANCELED_PICKUP_BUTTON), dest);
        this.mIsShown = true;
        LayoutParams p = new LayoutParams(-1, -2);
        p.addRule(2, C1283R.id.MainBottomBarLayout);
        p.addRule(14);
        this.mLayoutManager.addView(this, p);
        setCloseTimer(timeout);
        postDelayed(new C31783(), 1);
    }

    public void navigateCallback(int rc) {
        if (rc == 0) {
            hide();
        }
    }
}
