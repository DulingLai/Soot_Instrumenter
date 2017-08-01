package com.waze.carpool;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.PagerAdapter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.LayoutManager;
import com.waze.NativeManager;
import com.waze.analytics.Analytics;
import com.waze.analytics.AnalyticsEvents;
import com.waze.config.ConfigValues;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.mywaze.MyWazeNativeManager.FacebookCallback;
import com.waze.mywaze.MyWazeNativeManager.FacebookSettings;
import com.waze.mywaze.social.FacebookActivity;
import com.waze.profile.AccountSignInDetails;
import com.waze.profile.ImageTaker;
import com.waze.profile.MyProfileActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.utils.VolleyManager;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.anim.MaterialDesignImageAnimationHelper;
import com.waze.view.map.ProgressAnimation;
import com.waze.view.text.WazeTextView;
import java.util.ArrayList;

public class CarpoolDriverProfileActivity extends ActivityBase implements FacebookCallback {
    private static int BANK_ENTERED_WEIGHT = 10;
    private static int BANK_VERIFIED_WEIGHT = 0;
    private static int CAR_COLOR_WEIGHT = 10;
    private static int CAR_MAKE_WEIGHT = 10;
    private static int CAR_MODEL_WEIGHT = 10;
    private static int CAR_PHOTO_WEIGHT = 10;
    private static int CAR_PLATE_WEIGHT = 10;
    private static int COMPLETED_RIDES_WEIGHT = 0;
    private static int FACEBOOK_WEIGHT = 10;
    private static int LINKEDIN_WEIGHT = 10;
    private static int MOTTO_WEIGHT = 10;
    private static int PERMANENT_WEIGHT = 40;
    private static int PHOTO_WEIGHT = 10;
    public static final int REQUEST_CODE_BANK = 11;
    public static final int REQUEST_CODE_EDIT_USER = 12;
    public static final int REQUEST_CODE_FB = 10;
    private static int THANKS_WEIGHT = 0;
    private static int TOTAL_WEIGHT = (((((((((((((((PERMANENT_WEIGHT + PHOTO_WEIGHT) + MOTTO_WEIGHT) + CAR_COLOR_WEIGHT) + CAR_MAKE_WEIGHT) + CAR_MODEL_WEIGHT) + CAR_PLATE_WEIGHT) + CAR_PHOTO_WEIGHT) + WORKPLACE_ENTERED_WEIGHT) + WORKPLACE_VERIFIED_WEIGHT) + FACEBOOK_WEIGHT) + LINKEDIN_WEIGHT) + BANK_ENTERED_WEIGHT) + BANK_VERIFIED_WEIGHT) + COMPLETED_RIDES_WEIGHT) + THANKS_WEIGHT);
    private static int WORKPLACE_ENTERED_WEIGHT = 10;
    private static int WORKPLACE_VERIFIED_WEIGHT = 10;
    private boolean mAccountIsSet = false;
    Animator mAnimator;
    CarpoolNativeManager mCpnm;
    private GestureDetectorCompat mDetector;
    UserExtendedInfo mExtInfo = null;
    private ImageTaker mImageTaker;
    private String mMotto;
    NativeManager mNm;
    CarpoolUserData mUser = null;

    class C14001 extends SimpleOnGestureListener {
        C14001() throws  {
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float $f1) throws  {
            if ($f1 > 13000.0f) {
                CarpoolDriverProfileActivity.this.finish();
            }
            return false;
        }
    }

    class C14012 implements OnClickListener {
        C14012() throws  {
        }

        public void onClick(View v) throws  {
            CarpoolDriverProfileActivity.this.onBackPressed();
        }
    }

    class C14023 implements Runnable {
        C14023() throws  {
        }

        public void run() throws  {
            CarpoolDriverProfileActivity.this.hideDriverPhoto();
        }
    }

    class C14034 implements ImageRequestListener {
        final /* synthetic */ Runnable val$giveUp;

        C14034(Runnable $r2) throws  {
            this.val$giveUp = $r2;
        }

        public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
            CarpoolDriverProfileActivity.this.cancel(this.val$giveUp);
            BitmapDrawable $r3 = new BitmapDrawable($r1);
            ((ImageView) CarpoolDriverProfileActivity.this.findViewById(C1283R.id.driverPhoto)).setImageDrawable($r3);
            MaterialDesignImageAnimationHelper.animateImageEntrance($r3, 1500);
            CarpoolDriverProfileActivity.this.showDriverPhoto($r1);
        }

        public void onImageLoadFailed(Object token, long duration) throws  {
            CarpoolDriverProfileActivity.this.cancel(this.val$giveUp);
            CarpoolDriverProfileActivity.this.hideDriverPhoto();
        }
    }

    class C14045 implements OnClickListener {
        C14045() throws  {
        }

        public void onClick(View view) throws  {
        }
    }

    class C14056 implements OnClickListener {
        C14056() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PROFILE_COMPLETION_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CHANGE_PHOTO);
            CarpoolDriverProfileActivity.this.mImageTaker = new ImageTaker(CarpoolDriverProfileActivity.this, AccountSignInDetails.PROFILE_IMAGE_FILE);
            int $i0 = ConfigValues.getIntValue(40);
            CarpoolDriverProfileActivity.this.mImageTaker.setOutputResolution($i0, $i0, 1, 1);
            CarpoolDriverProfileActivity.this.mImageTaker.sendIntent();
        }
    }

    class C14067 implements OnClickListener {
        C14067() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PROFILE_COMPLETION_CLICK, "ACTION", "EDIT_NAME");
            CarpoolDriverProfileActivity.this.startActivityForResult(new Intent(CarpoolDriverProfileActivity.this, MyProfileActivity.class), 12);
        }
    }

    class C14078 implements OnClickListener {
        C14078() throws  {
        }

        public void onClick(View v) throws  {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PROFILE_COMPLETION_CLICK, "ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_CAR_DETAILS);
            CarpoolDriverProfileActivity.this.startActivityForResult(new Intent(CarpoolDriverProfileActivity.this, EditCarActivity.class), 0);
        }
    }

    class C14109 extends ImageRepositoryListener {
        final /* synthetic */ ImageView val$ivDriverProfile;

        C14109(ImageView $r2) throws  {
            this.val$ivDriverProfile = $r2;
        }

        public void onImageRetrieved(final Bitmap $r1) throws  {
            CarpoolDriverProfileActivity.this.post(new Runnable() {

                class C14081 implements OnClickListener {
                    C14081() throws  {
                    }

                    public void onClick(View v) throws  {
                        CarpoolDriverProfileActivity.this.zoomCarImage(C14109.this.val$ivDriverProfile, CarpoolDriverProfileActivity.this.findViewById(C1283R.id.driverProfileLayout), $r1);
                    }
                }

                public void run() throws  {
                    if ($r1 != null) {
                        C14109.this.val$ivDriverProfile.setImageDrawable(new CircleShaderDrawable($r1, 0));
                        C14109.this.val$ivDriverProfile.setOnClickListener(new C14081());
                    }
                }
            });
        }
    }

    public static class SimpleImageUrlsPagerAdapter extends PagerAdapter {
        private final Context ctx;
        private final String[] imageUrls;
        public ArrayList<ImageView> images = new ArrayList(4);
        private final SimpleImageUrlsPagerOnClick onClick;

        public boolean isViewFromObject(View $r1, Object $r2) throws  {
            return $r1 == $r2;
        }

        public SimpleImageUrlsPagerAdapter(Context $r1, String[] $r2, SimpleImageUrlsPagerOnClick $r3) throws  {
            this.ctx = $r1;
            this.imageUrls = $r2;
            this.onClick = $r3;
        }

        public int getCount() throws  {
            return this.imageUrls.length;
        }

        public Object instantiateItem(ViewGroup $r1, int $i0) throws  {
            final ImageView $r2 = new ImageView(this.ctx);
            $r2.setScaleType(ScaleType.CENTER_CROP);
            VolleyManager.getInstance().loadImageFromUrl(this.imageUrls[$i0], new ImageRequestListener() {
                public void onImageLoadComplete(final Bitmap $r1, Object token, long duration) throws  {
                    BitmapDrawable $r3 = new BitmapDrawable($r1);
                    $r2.setImageDrawable($r3);
                    MaterialDesignImageAnimationHelper.animateImageEntrance($r3, 1500);
                    if (SimpleImageUrlsPagerAdapter.this.onClick != null) {
                        $r2.setOnClickListener(new OnClickListener() {
                            public void onClick(View view) throws  {
                                SimpleImageUrlsPagerAdapter.this.onClick.onClick($r2, null, $r1);
                            }
                        });
                    }
                }

                public void onImageLoadFailed(Object token, long duration) throws  {
                }
            });
            $r1.addView($r2, -1, -1);
            this.images.add($i0, $r2);
            return $r2;
        }

        public void destroyItem(ViewGroup $r1, int position, Object $r2) throws  {
            $r1.removeView((View) $r2);
        }
    }

    public interface SimpleImageUrlsPagerOnClick {
        void onClick(View view, View view2, Bitmap bitmap) throws ;
    }

    private void setupActivity() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:104:0x0563
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r104 = this;
        r0 = r104;
        r5 = r0.mCpnm;
        r6 = r5.getCarpoolProfileNTV();
        r0 = r104;
        r0.mUser = r6;
        r0 = r104;
        r6 = r0.mUser;
        if (r6 != 0) goto L_0x0018;
    L_0x0012:
        r7 = "CarpoolDriverProfileActivity:setupActivity: null Carpool driver profile!";
        com.waze.Logger.m38e(r7);
        return;
    L_0x0018:
        r0 = r104;
        r8 = r0.mMotto;
        if (r8 != 0) goto L_0x0028;
    L_0x001e:
        r0 = r104;
        r6 = r0.mUser;
        r8 = r6.motto;
        r0 = r104;
        r0.mMotto = r8;
    L_0x0028:
        r0 = r104;
        r6 = r0.mUser;
        r8 = r6.getFullImage();
        if (r8 == 0) goto L_0x0038;
    L_0x0032:
        r9 = r8.isEmpty();
        if (r9 == 0) goto L_0x04ef;
    L_0x0038:
        r0 = r104;
        r0.hideDriverPhoto();
    L_0x003d:
        r0 = r104;
        r6 = r0.mUser;
        r9 = isProfilePhotoBad(r6);
        if (r9 == 0) goto L_0x0534;
    L_0x0047:
        r11 = 2131690379; // 0x7f0f038b float:1.90098E38 double:1.053194984E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r12 = new com.waze.carpool.CarpoolDriverProfileActivity$5;
        r0 = r104;
        r12.<init>();
        r10.setOnClickListener(r12);
        r11 = 2131690380; // 0x7f0f038c float:1.9009802E38 double:1.0531949843E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r14 = r10;
        r14 = (android.widget.TextView) r14;
        r13 = r14;
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2154; // 0x86a float:3.018E-42 double:1.064E-320;
        r8 = r15.getLanguageString(r11);
        r13.setText(r8);
        r11 = 2131690381; // 0x7f0f038d float:1.9009804E38 double:1.053194985E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r16 = r10;
        r16 = (android.widget.TextView) r16;
        r13 = r16;
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2154; // 0x86a float:3.018E-42 double:1.064E-320;
        r8 = r15.getLanguageString(r11);
        r13.setText(r8);
    L_0x0090:
        r11 = 2131690383; // 0x7f0f038f float:1.9009808E38 double:1.053194986E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r17 = r10;
        r17 = (android.widget.TextView) r17;
        r13 = r17;
        r0 = r104;
        r6 = r0.mUser;
        r8 = r6.getName();
        r13.setText(r8);
        r11 = 2131690378; // 0x7f0f038a float:1.9009798E38 double:1.0531949833E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r18 = new com.waze.carpool.CarpoolDriverProfileActivity$6;
        r0 = r18;
        r1 = r104;
        r0.<init>();
        r0 = r18;
        r10.setOnClickListener(r0);
        r19 = new com.waze.carpool.CarpoolDriverProfileActivity$7;
        r0 = r19;
        r1 = r104;
        r0.<init>();
        r0 = r19;
        r13.setOnClickListener(r0);
        r11 = 2131692372; // 0x7f0f0b54 float:1.9013842E38 double:1.0531959685E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.star_rating_as_driver;
        r20 = r0;
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.completed_rides_driver;
        r21 = r0;
        r22 = 0;
        r0 = r20;
        r1 = r21;
        r2 = r22;
        com.waze.carpool.CarpoolUtils.setStarsView(r0, r1, r10, r2);
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r8 = r0.color;
        r9 = empty(r8);
        if (r9 != 0) goto L_0x0567;
    L_0x0103:
        r9 = 1;
    L_0x0104:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r8 = r0.model;
        r24 = empty(r8);
        if (r24 != 0) goto L_0x056d;
    L_0x0114:
        r24 = 1;
    L_0x0116:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r8 = r0.make;
        r25 = empty(r8);
        if (r25 != 0) goto L_0x0574;
    L_0x0126:
        r25 = 1;
    L_0x0128:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r8 = r0.license_plate;
        r26 = empty(r8);
        if (r26 != 0) goto L_0x057b;
    L_0x0138:
        r26 = 1;
    L_0x013a:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r8 = r0.photo_url;
        r27 = empty(r8);
        if (r27 != 0) goto L_0x0582;
    L_0x014a:
        r27 = 1;
    L_0x014c:
        if (r9 != 0) goto L_0x0156;
    L_0x014e:
        if (r24 != 0) goto L_0x0156;
    L_0x0150:
        if (r25 != 0) goto L_0x0156;
    L_0x0152:
        if (r26 != 0) goto L_0x0156;
    L_0x0154:
        if (r27 == 0) goto L_0x0589;
    L_0x0156:
        r28 = 1;
    L_0x0158:
        if (r9 == 0) goto L_0x0590;
    L_0x015a:
        if (r24 == 0) goto L_0x0590;
    L_0x015c:
        if (r25 == 0) goto L_0x0590;
    L_0x015e:
        if (r26 == 0) goto L_0x0590;
    L_0x0160:
        if (r27 == 0) goto L_0x0590;
    L_0x0162:
        r29 = 1;
    L_0x0164:
        r11 = 2131690398; // 0x7f0f039e float:1.9009838E38 double:1.053194993E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r31 = r10;
        r31 = (com.waze.view.text.WazeTextView) r31;
        r30 = r31;
        if (r29 == 0) goto L_0x0593;
    L_0x0175:
        r11 = 2131690397; // 0x7f0f039d float:1.9009836E38 double:1.0531949927E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
    L_0x0183:
        r32 = new com.waze.carpool.CarpoolDriverProfileActivity$8;
        r33 = r32;
        r0 = r32;
        r1 = r104;
        r0.<init>();
        r11 = 2131690390; // 0x7f0f0396 float:1.9009822E38 double:1.0531949893E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r0 = r33;
        r10.setOnClickListener(r0);
        r0 = r30;
        r1 = r33;
        r0.setOnClickListener(r1);
        r11 = 2131690390; // 0x7f0f0396 float:1.9009822E38 double:1.0531949893E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r34 = r10.getLayoutParams();
        if (r28 == 0) goto L_0x05b5;
    L_0x01b2:
        r11 = -2;
        r0 = r34;
        r0.height = r11;
    L_0x01b7:
        r11 = 2131690395; // 0x7f0f039b float:1.9009832E38 double:1.0531949917E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r36 = r10;
        r36 = (android.widget.ImageView) r36;
        r35 = r36;
        if (r27 == 0) goto L_0x05bf;
    L_0x01c8:
        r11 = 0;
        r0 = r35;
        r0.setVisibility(r11);
        r37 = com.waze.utils.ImageRepository.instance;
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r8 = r0.photo_url;
        r38 = new com.waze.carpool.CarpoolDriverProfileActivity$9;
        r0 = r38;
        r1 = r104;
        r2 = r35;
        r0.<init>(r2);
        r0 = r37;
        r1 = r38;
        r0.getImage(r8, r1);
    L_0x01ec:
        if (r28 == 0) goto L_0x02b4;
    L_0x01ee:
        r11 = 2131690392; // 0x7f0f0398 float:1.9009826E38 double:1.0531949903E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r39 = r10;
        r39 = (android.widget.TextView) r39;
        r13 = r39;
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2168; // 0x878 float:3.038E-42 double:1.071E-320;
        r8 = r15.getLanguageString(r11);
        r13.setText(r8);
        r11 = 2131690394; // 0x7f0f039a float:1.900983E38 double:1.053194991E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r40 = r10;
        r40 = (android.widget.TextView) r40;
        r13 = r40;
        if (r26 == 0) goto L_0x05cc;
    L_0x021b:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r8 = r0.license_plate;
        r13.setText(r8);
        r11 = 0;
        r13.setVisibility(r11);
    L_0x022c:
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2162; // 0x872 float:3.03E-42 double:1.068E-320;
        r8 = r15.getLanguageString(r11);
        if (r9 == 0) goto L_0x05d6;
    L_0x0238:
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2163; // 0x873 float:3.031E-42 double:1.0687E-320;
        r41 = r15.getLanguageString(r11);
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r0 = r0.color;
        r42 = r0;
        r7 = "<COLOR>";
        r0 = r41;
        r1 = r42;
        r41 = r0.replace(r7, r1);
        r7 = "<COLOR>";
        r0 = r41;
        r8 = r8.replace(r7, r0);
    L_0x0260:
        if (r25 == 0) goto L_0x05e5;
    L_0x0262:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r0 = r0.make;
        r41 = r0;
    L_0x026e:
        r7 = "<MAKE>";
        r0 = r41;
        r8 = r8.replace(r7, r0);
        if (r24 == 0) goto L_0x05ec;
    L_0x0278:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.car_info;
        r23 = r0;
        r0 = r0.model;
        r41 = r0;
    L_0x0284:
        r7 = "<MODEL>";
        r0 = r41;
        r8 = r8.replace(r7, r0);
        r7 = " +";
        r43 = " ";
        r0 = r43;
        r8 = r8.replaceAll(r7, r0);
        r8 = r8.trim();
        r11 = 2131690393; // 0x7f0f0399 float:1.9009828E38 double:1.0531949908E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r44 = r10;
        r44 = (android.widget.TextView) r44;
        r13 = r44;
        r9 = r8.isEmpty();
        if (r9 == 0) goto L_0x05ef;
    L_0x02af:
        r11 = 8;
        r13.setVisibility(r11);
    L_0x02b4:
        r9 = 0;
        r11 = 2131690404; // 0x7f0f03a4 float:1.900985E38 double:1.053194996E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r45 = r10;
        r45 = (com.waze.view.text.WazeTextView) r45;
        r30 = r45;
        r11 = 2131690384; // 0x7f0f0390 float:1.900981E38 double:1.0531949863E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r47 = r10;
        r47 = (com.waze.view.text.WazeTextView) r47;
        r46 = r47;
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.work_email_verified;
        r24 = r0;
        if (r24 == 0) goto L_0x0620;
    L_0x02dd:
        r0 = r104;
        r6 = r0.mUser;
        r8 = r6.getWorkplace();
        r9 = r8.isEmpty();
        if (r9 == 0) goto L_0x05fb;
    L_0x02eb:
        r0 = r104;
        r6 = r0.mUser;
        r8 = r6.getWorkEmail();
        r0 = r104;
        r6 = r0.mUser;
        r41 = r6.getWorkEmail();
        r11 = 64;
        r0 = r41;
        r21 = r0.indexOf(r11);
        r0 = r21;
        r8 = r8.substring(r0);
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2177; // 0x881 float:3.05E-42 double:1.0756E-320;
        r41 = r15.getLanguageString(r11);
        r11 = 1;
        r0 = new java.lang.Object[r11];
        r48 = r0;
        r11 = 0;
        r48[r11] = r8;
        r0 = r41;
        r1 = r48;
        r8 = java.lang.String.format(r0, r1);
    L_0x0323:
        r11 = 8;
        r0 = r30;
        r0.setVisibility(r11);
        r11 = 2131690405; // 0x7f0f03a5 float:1.9009853E38 double:1.0531949967E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r49 = new android.text.SpannableString;
        r50 = r49;
        r0 = r49;
        r0.<init>(r8);
        r51 = new android.text.style.UnderlineSpan;
        r0 = r51;
        r0.<init>();
        r21 = r8.length();
        r11 = 0;
        r52 = 0;
        r0 = r50;
        r1 = r51;
        r2 = r21;
        r3 = r52;
        r0.setSpan(r1, r11, r2, r3);
        r53 = r46;
        r53 = (android.widget.TextView) r53;
        r13 = r53;
        r0 = r50;
        r13.setText(r0);
        r9 = 1;
    L_0x0366:
        r54 = new com.waze.carpool.CarpoolDriverProfileActivity$10;
        r55 = r54;
        r0 = r54;
        r1 = r104;
        r0.<init>();
        r0 = r30;
        r1 = r55;
        r0.setOnClickListener(r1);
        r0 = r46;
        r1 = r55;
        r0.setOnClickListener(r1);
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.total_carpooled_meters_driver;
        r21 = r0;
        if (r21 <= 0) goto L_0x06fc;
    L_0x0389:
        r11 = 2131690407; // 0x7f0f03a7 float:1.9009857E38 double:1.0531949977E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 0;
        r10.setVisibility(r11);
        r11 = 2131690406; // 0x7f0f03a6 float:1.9009855E38 double:1.053194997E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 0;
        r10.setVisibility(r11);
        r56 = com.waze.share.ShareNativeManager.getInstance();
        r0 = r56;
        r24 = r0.isMetricUnitsNTV();
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.total_carpooled_meters_driver;
        r21 = r0;
        r0 = (float) r0;
        r20 = r0;
        if (r24 == 0) goto L_0x06f1;
    L_0x03ba:
        r57 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
    L_0x03bd:
        r20 = r57 * r20;
        r58 = 1140457472; // 0x43fa0000 float:500.0 double:5.634608575E-315;
        r0 = r20;
        r1 = r58;
        r0 = r0 + r1;
        r20 = r0;
        r58 = 1148846080; // 0x447a0000 float:1000.0 double:5.676053805E-315;
        r0 = r20;
        r1 = r58;
        r0 = r0 / r1;
        r20 = r0;
        r0 = (int) r0;
        r21 = r0;
        r11 = 2;
        r0 = new java.lang.Object[r11];
        r48 = r0;
        r0 = r21;
        r59 = java.lang.Integer.valueOf(r0);
        r11 = 0;
        r48[r11] = r59;
        if (r24 == 0) goto L_0x06f9;
    L_0x03e6:
        r60 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
    L_0x03e8:
        r0 = r60;
        r8 = com.waze.strings.DisplayStrings.displayString(r0);
        r11 = 1;
        r48[r11] = r8;
        r11 = 2128; // 0x850 float:2.982E-42 double:1.0514E-320;
        r0 = r48;
        r8 = com.waze.strings.DisplayStrings.displayStringF(r11, r0);
        r11 = 2131690406; // 0x7f0f03a6 float:1.9009855E38 double:1.053194997E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r61 = r10;
        r61 = (com.waze.view.text.WazeTextView) r61;
        r30 = r61;
        r0 = r104;
        r1 = r30;
        r0.setConfigured(r1, r8);
    L_0x040f:
        r11 = 2131690385; // 0x7f0f0391 float:1.9009812E38 double:1.053194987E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r11 = 2131690408; // 0x7f0f03a8 float:1.9009859E38 double:1.053194998E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r11 = 2131690409; // 0x7f0f03a9 float:1.900986E38 double:1.0531949987E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r11 = 2131690386; // 0x7f0f0392 float:1.9009814E38 double:1.0531949873E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r11 = 2131690387; // 0x7f0f0393 float:1.9009816E38 double:1.053194988E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        if (r9 == 0) goto L_0x0721;
    L_0x0452:
        r62 = 0;
    L_0x0454:
        r0 = r62;
        r10.setVisibility(r0);
        r11 = 2131690399; // 0x7f0f039f float:1.900984E38 double:1.0531949937E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r63 = r10;
        r63 = (com.waze.view.text.WazeTextView) r63;
        r30 = r63;
        r0 = r104;
        r8 = r0.mMotto;
        if (r8 == 0) goto L_0x0478;
    L_0x046e:
        r0 = r104;
        r8 = r0.mMotto;
        r9 = r8.isEmpty();
        if (r9 == 0) goto L_0x0724;
    L_0x0478:
        r11 = 2165; // 0x875 float:3.034E-42 double:1.0697E-320;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
    L_0x0481:
        r64 = new com.waze.carpool.CarpoolDriverProfileActivity$11;
        r0 = r64;
        r1 = r104;
        r0.<init>();
        r0 = r30;
        r1 = r64;
        r0.setOnClickListener(r1);
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.driver_social_networks;
        r65 = r0;
        if (r65 == 0) goto L_0x0843;
    L_0x049b:
        r21 = 0;
    L_0x049d:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.driver_social_networks;
        r65 = r0;
        r0 = r0.length;
        r66 = r0;
        r0 = r21;
        r1 = r66;
        if (r0 >= r1) goto L_0x0843;
    L_0x04ae:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.driver_social_networks;
        r65 = r0;
        r67 = r65[r21];
        r0 = r67;
        r0 = r0.network_type;
        r66 = r0;
        if (r66 != 0) goto L_0x07fd;
    L_0x04c0:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.driver_social_networks;
        r65 = r0;
        r67 = r65[r21];
        r0 = r67;
        r8 = r0.profile_url;
        r68 = new java.lang.StringBuilder;
        r0 = r68;
        r0.<init>();
        r7 = "CarpoolDriverProfile: found driver FB profile: ";
        r0 = r68;
        r69 = r0.append(r7);
        r0 = r69;
        r69 = r0.append(r8);
        r0 = r69;
        r8 = r0.toString();
        com.waze.Logger.m36d(r8);
    L_0x04ec:
        r21 = r21 + 1;
        goto L_0x049d;
    L_0x04ef:
        r11 = 2131690373; // 0x7f0f0385 float:1.9009788E38 double:1.053194981E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r71 = r10;
        r71 = (com.waze.view.map.ProgressAnimation) r71;
        r70 = r71;
        r0 = r70;
        r0.start();
        r72 = new com.waze.carpool.CarpoolDriverProfileActivity$3;
        r73 = r72;
        r0 = r72;
        r1 = r104;
        r0.<init>();
        r74 = 5000; // 0x1388 float:7.006E-42 double:2.4703E-320;
        r0 = r104;
        r1 = r73;
        r2 = r74;
        r0.postDelayed(r1, r2);
        r76 = com.waze.utils.VolleyManager.getInstance();
        r77 = new com.waze.carpool.CarpoolDriverProfileActivity$4;
        r0 = r77;
        r1 = r104;
        r2 = r73;
        r0.<init>(r2);
        goto L_0x052c;
    L_0x0529:
        goto L_0x003d;
    L_0x052c:
        r0 = r76;
        r1 = r77;
        r0.loadImageFromUrl(r8, r1);
        goto L_0x0529;
    L_0x0534:
        r11 = 2131690379; // 0x7f0f038b float:1.90098E38 double:1.053194984E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r11 = 2131690381; // 0x7f0f038d float:1.9009804E38 double:1.053194985E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r11 = 2131690382; // 0x7f0f038e float:1.9009806E38 double:1.0531949853E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        goto L_0x055d;
    L_0x055a:
        goto L_0x0090;
    L_0x055d:
        r11 = 8;
        r10.setVisibility(r11);
        goto L_0x055a;
        goto L_0x0567;
    L_0x0564:
        goto L_0x0104;
    L_0x0567:
        r9 = 0;
        goto L_0x0564;
        goto L_0x056d;
    L_0x056a:
        goto L_0x0116;
    L_0x056d:
        r24 = 0;
        goto L_0x056a;
        goto L_0x0574;
    L_0x0571:
        goto L_0x0128;
    L_0x0574:
        r25 = 0;
        goto L_0x0571;
        goto L_0x057b;
    L_0x0578:
        goto L_0x013a;
    L_0x057b:
        r26 = 0;
        goto L_0x0578;
        goto L_0x0582;
    L_0x057f:
        goto L_0x014c;
    L_0x0582:
        r27 = 0;
        goto L_0x057f;
        goto L_0x0589;
    L_0x0586:
        goto L_0x0158;
    L_0x0589:
        r28 = 0;
        goto L_0x0586;
        goto L_0x0590;
    L_0x058d:
        goto L_0x0164;
    L_0x0590:
        r29 = 0;
        goto L_0x058d;
    L_0x0593:
        if (r28 == 0) goto L_0x05a7;
    L_0x0595:
        goto L_0x0599;
    L_0x0596:
        goto L_0x0183;
    L_0x0599:
        r11 = 2170; // 0x87a float:3.041E-42 double:1.072E-320;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
        goto L_0x0596;
        goto L_0x05a7;
    L_0x05a4:
        goto L_0x0183;
    L_0x05a7:
        r11 = 2169; // 0x879 float:3.04E-42 double:1.0716E-320;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
        goto L_0x05a4;
        goto L_0x05b5;
    L_0x05b2:
        goto L_0x01b7;
    L_0x05b5:
        r11 = 0;
        r0 = r34;
        r0.height = r11;
        goto L_0x05b2;
        goto L_0x05bf;
    L_0x05bc:
        goto L_0x01ec;
    L_0x05bf:
        r11 = 2130839076; // 0x7f020624 float:1.7283152E38 double:1.0527743843E-314;
        r0 = r35;
        r0.setImageResource(r11);
        goto L_0x05bc;
        goto L_0x05cc;
    L_0x05c9:
        goto L_0x022c;
    L_0x05cc:
        r11 = 8;
        r13.setVisibility(r11);
        goto L_0x05c9;
        goto L_0x05d6;
    L_0x05d3:
        goto L_0x0260;
    L_0x05d6:
        r7 = "<COLOR>";
        r43 = "";
        r0 = r43;
        r8 = r8.replace(r7, r0);
        goto L_0x05d3;
        goto L_0x05e5;
    L_0x05e2:
        goto L_0x026e;
    L_0x05e5:
        r41 = "";
        goto L_0x05e2;
        goto L_0x05ec;
    L_0x05e9:
        goto L_0x0284;
    L_0x05ec:
        r41 = "";
        goto L_0x05e9;
    L_0x05ef:
        r13.setText(r8);
        goto L_0x05f6;
    L_0x05f3:
        goto L_0x02b4;
    L_0x05f6:
        r11 = 0;
        r13.setVisibility(r11);
        goto L_0x05f3;
    L_0x05fb:
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2176; // 0x880 float:3.049E-42 double:1.075E-320;
        r8 = r15.getLanguageString(r11);
        r11 = 1;
        r0 = new java.lang.Object[r11];
        r48 = r0;
        r0 = r104;
        r6 = r0.mUser;
        r41 = r6.getWorkplace();
        r11 = 0;
        r48[r11] = r41;
        goto L_0x0619;
    L_0x0616:
        goto L_0x0323;
    L_0x0619:
        r0 = r48;
        r8 = java.lang.String.format(r8, r0);
        goto L_0x0616;
    L_0x0620:
        r0 = r104;
        r6 = r0.mUser;
        r8 = r6.work_email;
        r24 = empty(r8);
        if (r24 != 0) goto L_0x06d8;
    L_0x062c:
        r11 = 2174; // 0x87e float:3.046E-42 double:1.074E-320;
        r8 = com.waze.strings.DisplayStrings.displayString(r11);
        r11 = 2175; // 0x87f float:3.048E-42 double:1.0746E-320;
        r41 = com.waze.strings.DisplayStrings.displayString(r11);
        r49 = new android.text.SpannableString;
        r50 = r49;
        r68 = new java.lang.StringBuilder;
        r0 = r68;
        r0.<init>();
        r0 = r68;
        r69 = r0.append(r8);
        r7 = "\n";
        r0 = r69;
        r69 = r0.append(r7);
        r0 = r69;
        r1 = r41;
        r69 = r0.append(r1);
        r0 = r69;
        r42 = r0.toString();
        r0 = r49;
        r1 = r42;
        r0.<init>(r1);
        r51 = new android.text.style.UnderlineSpan;
        r0 = r51;
        r0.<init>();
        r21 = r8.length();
        r11 = 0;
        r52 = 0;
        r0 = r50;
        r1 = r51;
        r2 = r21;
        r3 = r52;
        r0.setSpan(r1, r11, r2, r3);
        r78 = new android.text.style.ForegroundColorSpan;
        r0 = r104;
        r79 = r0.getResources();
        r11 = 2131623969; // 0x7f0e0021 float:1.8875104E38 double:1.053162173E-314;
        r0 = r79;
        r21 = r0.getColor(r11);
        r0 = r78;
        r1 = r21;
        r0.<init>(r1);
        r21 = r8.length();
        r21 = r21 + 1;
        r66 = r8.length();
        r0 = r41;
        r80 = r0.length();
        r0 = r66;
        r1 = r80;
        r0 = r0 + r1;
        r66 = r0;
        r66 = r66 + 1;
        r11 = 0;
        r0 = r50;
        r1 = r78;
        r2 = r21;
        r3 = r66;
        r0.setSpan(r1, r2, r3, r11);
        r0 = r30;
        r1 = r50;
        r0.setText(r1);
        r22 = 0;
        r0 = r30;
        r1 = r22;
        r0.setDrawableRight(r1);
        goto L_0x06d0;
    L_0x06cd:
        goto L_0x0366;
    L_0x06d0:
        r11 = 8;
        r0 = r46;
        r0.setVisibility(r11);
        goto L_0x06cd;
    L_0x06d8:
        r11 = 2173; // 0x87d float:3.045E-42 double:1.0736E-320;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
        goto L_0x06e5;
    L_0x06e2:
        goto L_0x0366;
    L_0x06e5:
        r11 = 8;
        r0 = r46;
        r0.setVisibility(r11);
        goto L_0x06e2;
        goto L_0x06f1;
    L_0x06ee:
        goto L_0x03bd;
    L_0x06f1:
        r57 = 1059000875; // 0x3f1f122b float:0.621371 double:5.232159513E-315;
        goto L_0x06ee;
        goto L_0x06f9;
    L_0x06f6:
        goto L_0x03e8;
    L_0x06f9:
        r60 = 464; // 0x1d0 float:6.5E-43 double:2.29E-321;
        goto L_0x06f6;
    L_0x06fc:
        r11 = 2131690407; // 0x7f0f03a7 float:1.9009857E38 double:1.0531949977E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        r11 = 2131690406; // 0x7f0f03a6 float:1.9009855E38 double:1.053194997E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        goto L_0x0717;
    L_0x0714:
        goto L_0x040f;
    L_0x0717:
        r11 = 8;
        r10.setVisibility(r11);
        goto L_0x0714;
        goto L_0x0721;
    L_0x071e:
        goto L_0x0454;
    L_0x0721:
        r62 = 8;
        goto L_0x071e;
    L_0x0724:
        r11 = 1;
        r0 = new java.lang.Object[r11];
        r48 = r0;
        r0 = r104;
        r8 = r0.mMotto;
        r11 = 0;
        r48[r11] = r8;
        r11 = 2158; // 0x86e float:3.024E-42 double:1.066E-320;
        r0 = r48;
        r8 = com.waze.strings.DisplayStrings.displayStringF(r11, r0);
        r11 = 2159; // 0x86f float:3.025E-42 double:1.0667E-320;
        r41 = com.waze.strings.DisplayStrings.displayString(r11);
        r49 = new android.text.SpannableString;
        r50 = r49;
        r68 = new java.lang.StringBuilder;
        r0 = r68;
        r0.<init>();
        r0 = r68;
        r69 = r0.append(r8);
        r0 = r69;
        r1 = r41;
        r69 = r0.append(r1);
        r0 = r69;
        r42 = r0.toString();
        r0 = r49;
        r1 = r42;
        r0.<init>(r1);
        r78 = new android.text.style.ForegroundColorSpan;
        r0 = r104;
        r79 = r0.getResources();
        r11 = 2131623958; // 0x7f0e0016 float:1.8875082E38 double:1.0531621675E-314;
        r0 = r79;
        r21 = r0.getColor(r11);
        r0 = r78;
        r1 = r21;
        r0.<init>(r1);
        r21 = r8.length();
        r11 = 0;
        r52 = 0;
        r0 = r50;
        r1 = r78;
        r2 = r21;
        r3 = r52;
        r0.setSpan(r1, r11, r2, r3);
        r78 = new android.text.style.ForegroundColorSpan;
        r0 = r104;
        r79 = r0.getResources();
        r11 = 2131623954; // 0x7f0e0012 float:1.8875074E38 double:1.0531621655E-314;
        r0 = r79;
        r21 = r0.getColor(r11);
        r0 = r78;
        r1 = r21;
        r0.<init>(r1);
        r21 = r8.length();
        r66 = r8.length();
        r0 = r41;
        r80 = r0.length();
        r0 = r66;
        r1 = r80;
        r0 = r0 + r1;
        r66 = r0;
        r11 = 0;
        r0 = r50;
        r1 = r78;
        r2 = r21;
        r3 = r66;
        r0.setSpan(r1, r2, r3, r11);
        r51 = new android.text.style.UnderlineSpan;
        r0 = r51;
        r0.<init>();
        r21 = r8.length();
        r66 = r8.length();
        r0 = r41;
        r80 = r0.length();
        r0 = r66;
        r1 = r80;
        r0 = r0 + r1;
        r66 = r0;
        r11 = 0;
        r0 = r50;
        r1 = r51;
        r2 = r21;
        r3 = r66;
        r0.setSpan(r1, r2, r3, r11);
        goto L_0x07f3;
    L_0x07f0:
        goto L_0x0481;
    L_0x07f3:
        r0 = r104;
        r1 = r30;
        r2 = r50;
        r0.setConfigured(r1, r2);
        goto L_0x07f0;
    L_0x07fd:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.driver_social_networks;
        r65 = r0;
        r67 = r65[r21];
        r0 = r67;
        r0 = r0.network_type;
        r66 = r0;
        r11 = 1;
        r0 = r66;
        if (r0 != r11) goto L_0x04ec;
    L_0x0812:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.driver_social_networks;
        r65 = r0;
        r67 = r65[r21];
        r0 = r67;
        r8 = r0.profile_url;
        r68 = new java.lang.StringBuilder;
        r0 = r68;
        r0.<init>();
        r7 = "CarpoolDriverProfile: found driver LI profile: ";
        r0 = r68;
        r69 = r0.append(r7);
        r0 = r69;
        r69 = r0.append(r8);
        r0 = r69;
        r8 = r0.toString();
        goto L_0x083f;
    L_0x083c:
        goto L_0x04ec;
    L_0x083f:
        com.waze.Logger.m36d(r8);
        goto L_0x083c;
    L_0x0843:
        r11 = 2131690400; // 0x7f0f03a0 float:1.9009843E38 double:1.053194994E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r81 = r10;
        r81 = (com.waze.view.text.WazeTextView) r81;
        r30 = r81;
        r82 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r0 = r82;
        r9 = r0.getFacebookLoggedInNTV();
        if (r9 == 0) goto L_0x0acb;
    L_0x085e:
        r11 = 2180; // 0x884 float:3.055E-42 double:1.077E-320;
        r0 = r104;
        r1 = r30;
        r0.setConfiguredUnderlined(r1, r11);
        r0 = r104;
        r79 = r0.getResources();
        r11 = 2131623955; // 0x7f0e0013 float:1.8875076E38 double:1.053162166E-314;
        r0 = r79;
        r21 = r0.getColor(r11);
        r0 = r30;
        r1 = r21;
        r0.setTextColor(r1);
        r11 = 11;
        r52 = 0;
        r0 = r30;
        r1 = r52;
        r0.setFont(r11, r1);
        r11 = 1;
        r58 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r0 = r30;
        r1 = r58;
        r0.setTextSize(r11, r1);
        r83 = new com.waze.carpool.CarpoolDriverProfileActivity$12;
        r0 = r83;
        r1 = r104;
        r0.<init>();
        r0 = r30;
        r1 = r83;
        r0.setOnClickListener(r1);
    L_0x08a3:
        r11 = 2131690402; // 0x7f0f03a2 float:1.9009847E38 double:1.053194995E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r84 = r10;
        r84 = (com.waze.view.text.WazeTextView) r84;
        r30 = r84;
        r82 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r0 = r82;
        r9 = r0.getLinkedinLoggedInNTV();
        if (r9 == 0) goto L_0x0aed;
    L_0x08be:
        r11 = 2740; // 0xab4 float:3.84E-42 double:1.3537E-320;
        r0 = r104;
        r1 = r30;
        r0.setConfiguredUnderlined(r1, r11);
        r0 = r104;
        r79 = r0.getResources();
        r11 = 2131623962; // 0x7f0e001a float:1.887509E38 double:1.0531621695E-314;
        r0 = r79;
        r21 = r0.getColor(r11);
        r0 = r30;
        r1 = r21;
        r0.setTextColor(r1);
        r11 = 11;
        r52 = 0;
        r0 = r30;
        r1 = r52;
        r0.setFont(r11, r1);
        r11 = 1;
        r58 = 1099431936; // 0x41880000 float:17.0 double:5.431915495E-315;
        r0 = r30;
        r1 = r58;
        r0.setTextSize(r11, r1);
        r85 = new com.waze.carpool.CarpoolDriverProfileActivity$14;
        r0 = r85;
        r1 = r104;
        r0.<init>();
        r0 = r30;
        r1 = r85;
        r0.setOnClickListener(r1);
    L_0x0903:
        r11 = 2131690410; // 0x7f0f03aa float:1.9009863E38 double:1.053194999E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r86 = r10;
        r86 = (com.waze.view.text.WazeTextView) r86;
        r30 = r86;
        r0 = r104;
        r6 = r0.mUser;
        r0 = r104;
        r9 = r0.mAccountIsSet;
        r9 = com.waze.carpool.CarpoolUtils.hasPaymentMeans(r6, r9);
        if (r9 == 0) goto L_0x0b0b;
    L_0x0920:
        r11 = 2184; // 0x888 float:3.06E-42 double:1.079E-320;
        r0 = r104;
        r1 = r30;
        r0.setConfigured(r1, r11);
    L_0x0929:
        r11 = 2131690413; // 0x7f0f03ad float:1.9009869E38 double:1.0531950006E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r87 = r10;
        r87 = (com.waze.view.text.WazeTextView) r87;
        r30 = r87;
        r82 = com.waze.mywaze.MyWazeNativeManager.getInstance();
        r0 = r82;
        r8 = r0.getPhoneNumberNTV();
        if (r8 == 0) goto L_0x094a;
    L_0x0944:
        r9 = r8.isEmpty();
        if (r9 == 0) goto L_0x0b31;
    L_0x094a:
        r11 = 1204; // 0x4b4 float:1.687E-42 double:5.95E-321;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
    L_0x0953:
        r88 = new com.waze.carpool.CarpoolDriverProfileActivity$17;
        r0 = r88;
        r1 = r104;
        r0.<init>();
        r0 = r30;
        r1 = r88;
        r0.setOnClickListener(r1);
        r0 = r104;
        r6 = r0.mUser;
        r8 = r6.getEmail();
        if (r8 != 0) goto L_0x0b39;
    L_0x096d:
        r11 = 2131690412; // 0x7f0f03ac float:1.9009867E38 double:1.053195E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
    L_0x097b:
        r11 = 2131690412; // 0x7f0f03ac float:1.9009867E38 double:1.053195E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r89 = new com.waze.carpool.CarpoolDriverProfileActivity$18;
        r0 = r89;
        r1 = r104;
        r0.<init>();
        r0 = r89;
        r10.setOnClickListener(r0);
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.waze_join_date_sec;
        r90 = r0;
        r74 = 0;
        r62 = (r90 > r74 ? 1 : (r90 == r74 ? 0 : -1));
        if (r62 <= 0) goto L_0x0b54;
    L_0x09a0:
        r92 = java.util.Calendar.getInstance();
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.waze_join_date_sec;
        r90 = r0;
        r74 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r90;
        r2 = r74;
        r0 = r0 * r2;
        r90 = r0;
        r0 = r92;
        r1 = r90;
        r0.setTimeInMillis(r1);
        r93 = new java.text.SimpleDateFormat;
        r7 = "MMM yyyy";
        r0 = r93;
        r0.<init>(r7);
        r0 = r92;
        r94 = r0.getTime();
        r0 = r93;
        r1 = r94;
        r8 = r0.format(r1);
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2187; // 0x88b float:3.065E-42 double:1.0805E-320;
        r41 = r15.getLanguageString(r11);
        r11 = 1;
        r0 = new java.lang.Object[r11];
        r48 = r0;
        r11 = 0;
        r48[r11] = r8;
        r0 = r41;
        r1 = r48;
        r8 = java.lang.String.format(r0, r1);
        r11 = 2131690415; // 0x7f0f03af float:1.9009873E38 double:1.0531950016E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r95 = r10;
        r95 = (com.waze.view.text.WazeTextView) r95;
        r30 = r95;
        r0 = r104;
        r1 = r30;
        r0.setConfigured(r1, r8);
    L_0x0a03:
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.join_time_utc_seconds;
        r90 = r0;
        r74 = 0;
        r62 = (r90 > r74 ? 1 : (r90 == r74 ? 0 : -1));
        if (r62 <= 0) goto L_0x0b67;
    L_0x0a11:
        r92 = java.util.Calendar.getInstance();
        r0 = r104;
        r6 = r0.mUser;
        r0 = r6.join_time_utc_seconds;
        r90 = r0;
        r74 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r90;
        r2 = r74;
        r0 = r0 * r2;
        r90 = r0;
        r0 = r92;
        r1 = r90;
        r0.setTimeInMillis(r1);
        r93 = new java.text.SimpleDateFormat;
        r7 = "MMM yyyy";
        r0 = r93;
        r0.<init>(r7);
        r0 = r92;
        r94 = r0.getTime();
        r0 = r93;
        r1 = r94;
        r8 = r0.format(r1);
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2188; // 0x88c float:3.066E-42 double:1.081E-320;
        r41 = r15.getLanguageString(r11);
        r11 = 1;
        r0 = new java.lang.Object[r11];
        r48 = r0;
        r11 = 0;
        r48[r11] = r8;
        r0 = r41;
        r1 = r48;
        r8 = java.lang.String.format(r0, r1);
        r11 = 2131690417; // 0x7f0f03b1 float:1.9009877E38 double:1.0531950026E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r96 = r10;
        r96 = (com.waze.view.text.WazeTextView) r96;
        r30 = r96;
        r0 = r104;
        r1 = r30;
        r0.setConfigured(r1, r8);
    L_0x0a74:
        r0 = r104;
        r0.updateEndorsements();
        r21 = getPercentCompleteProfile();
        r0 = r104;
        r15 = r0.mNm;
        r11 = 2164; // 0x874 float:3.032E-42 double:1.069E-320;
        r8 = r15.getLanguageString(r11);
        r11 = 1;
        r0 = new java.lang.Object[r11];
        r48 = r0;
        r0 = r21;
        r59 = java.lang.Integer.valueOf(r0);
        r11 = 0;
        r48[r11] = r59;
        r0 = r48;
        r8 = java.lang.String.format(r8, r0);
        r11 = 2131690388; // 0x7f0f0394 float:1.9009818E38 double:1.0531949883E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r97 = r10;
        r97 = (android.widget.TextView) r97;
        r13 = r97;
        r13.setText(r8);
        r11 = 2131690389; // 0x7f0f0395 float:1.900982E38 double:1.053194989E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r99 = r10;
        r99 = (android.widget.ProgressBar) r99;
        r98 = r99;
        r0 = r98;
        r1 = r21;
        r0.setProgress(r1);
        r11 = 100;
        r0 = r98;
        r0.setMax(r11);
        return;
    L_0x0acb:
        r11 = 2179; // 0x883 float:3.053E-42 double:1.0766E-320;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
        r100 = new com.waze.carpool.CarpoolDriverProfileActivity$13;
        r0 = r100;
        r1 = r104;
        r0.<init>();
        goto L_0x0ae1;
    L_0x0ade:
        goto L_0x08a3;
    L_0x0ae1:
        r0 = r30;
        r1 = r100;
        r0.setOnClickListener(r1);
        goto L_0x0ade;
        goto L_0x0aed;
    L_0x0aea:
        goto L_0x0a74;
    L_0x0aed:
        r11 = 2742; // 0xab6 float:3.842E-42 double:1.3547E-320;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
        r101 = new com.waze.carpool.CarpoolDriverProfileActivity$15;
        r0 = r101;
        r1 = r104;
        r0.<init>();
        goto L_0x0b03;
    L_0x0b00:
        goto L_0x0903;
    L_0x0b03:
        r0 = r30;
        r1 = r101;
        r0.setOnClickListener(r1);
        goto L_0x0b00;
    L_0x0b0b:
        r11 = 2182; // 0x886 float:3.058E-42 double:1.078E-320;
        r0 = r104;
        r1 = r30;
        r0.setUnconfigured(r1, r11);
        r102 = new com.waze.carpool.CarpoolDriverProfileActivity$16;
        r0 = r102;
        r1 = r104;
        r0.<init>();
        goto L_0x0b21;
    L_0x0b1e:
        goto L_0x0929;
    L_0x0b21:
        r0 = r30;
        r1 = r102;
        r0.setOnClickListener(r1);
        goto L_0x0b1e;
        goto L_0x0b31;
    L_0x0b2a:
        goto L_0x0953;
        goto L_0x0b31;
    L_0x0b2e:
        goto L_0x0aea;
    L_0x0b31:
        r0 = r104;
        r1 = r30;
        r0.setConfiguredUnderlined(r1, r8);
        goto L_0x0b2a;
    L_0x0b39:
        r11 = 2131690412; // 0x7f0f03ac float:1.9009867E38 double:1.053195E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r103 = r10;
        r103 = (com.waze.view.text.WazeTextView) r103;
        r30 = r103;
        goto L_0x0b4c;
    L_0x0b49:
        goto L_0x097b;
    L_0x0b4c:
        r0 = r104;
        r1 = r30;
        r0.setConfiguredUnderlined(r1, r8);
        goto L_0x0b49;
    L_0x0b54:
        r11 = 2131690414; // 0x7f0f03ae float:1.900987E38 double:1.053195001E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        goto L_0x0b61;
    L_0x0b5e:
        goto L_0x0a03;
    L_0x0b61:
        r11 = 8;
        r10.setVisibility(r11);
        goto L_0x0b5e;
    L_0x0b67:
        r11 = 2131690416; // 0x7f0f03b0 float:1.9009875E38 double:1.053195002E-314;
        r0 = r104;
        r10 = r0.findViewById(r11);
        r11 = 8;
        r10.setVisibility(r11);
        goto L_0x0b2e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolDriverProfileActivity.setupActivity():void");
    }

    public void onCreate(Bundle $r1) throws  {
        super.onCreate($r1);
        if ($r1 == null) {
            Analytics.log(AnalyticsEvents.ANALYTICS_EVENT_RW_PROFILE_COMPLETION_SHOWN);
        }
        this.mNm = NativeManager.getInstance();
        this.mCpnm = CarpoolNativeManager.getInstance();
        getWindow().setSoftInputMode(3);
        requestWindowFeature(1);
        setContentView(C1283R.layout.driver_profile);
        setupActivity();
        CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        this.mDetector = new GestureDetectorCompat(this, new C14001());
        findViewById(C1283R.id.driverProfileButtonBack).setOnClickListener(new C14012());
        if (this.mUser != null) {
            CarpoolNativeManager.getInstance().setUpdateHandler(CarpoolNativeManager.UH_CARPOOL_EXT_USER, this.mHandler);
            CarpoolNativeManager.getInstance().getExtUserObject(this.mUser.id);
        }
    }

    protected void onDestroy() throws  {
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_USER, this.mHandler);
        CarpoolNativeManager.getInstance().unsetUpdateHandler(CarpoolNativeManager.UH_CARPOOL_EXT_USER, this.mHandler);
        super.onDestroy();
    }

    static boolean empty(String $r0) throws  {
        return $r0 == null || $r0.isEmpty();
    }

    private void updateEndorsements() throws  {
        LinearLayout $r2 = (LinearLayout) findViewById(C1283R.id.endorsements);
        if (this.mExtInfo == null || !this.mExtInfo.hasEndorsements()) {
            $r2.setVisibility(8);
            return;
        }
        int $i1;
        int $i0 = 0;
        for ($i1 = 1; $i1 <= 6; $i1++) {
            if (this.mExtInfo.endorsement_count[$i1] > 0) {
                $i0++;
            }
        }
        if ($i0 <= 3) {
            $i1 = 0;
        } else {
            $i1 = $i0 / 2;
            $i0 -= $i1;
        }
        $r2.setVisibility(0);
        int $i2 = this.mExtInfo.nextEndorsement(0);
        LinearLayout $r5 = (LinearLayout) $r2.getChildAt(1);
        String $r6 = DisplayStrings.displayString(DisplayStrings.DS_DRIVER_PROFILE_ENDORSEMENTS_PS);
        setEndorsementLine($i1, setEndorsementLine($i0, $i2, $r5, $r6), (LinearLayout) $r2.getChildAt(2), $r6);
    }

    private int setEndorsementLine(int $i0, int $i1, LinearLayout $r1, String $r2) throws  {
        for (int $i2 = 0; $i2 < 3; $i2++) {
            LinearLayout $r4 = (LinearLayout) $r1.getChildAt(($i2 * 2) + 1);
            if ($i2 >= $i0 || $i1 < 0) {
                $r4.setVisibility(8);
            } else {
                TextView $r6 = (TextView) $r4.getChildAt(1);
                TextView $r7 = (TextView) $r4.getChildAt(3);
                ((ImageView) $r4.getChildAt(0)).setImageResource(CarpoolEndorsement.icon[$i1]);
                $r7.setText(String.format($r2, new Object[]{DisplayStrings.displayString(CarpoolEndorsement.name[$i1])}));
                UserExtendedInfo $r11 = this.mExtInfo;
                $r6.setText(Integer.toString($r11.endorsement_count[$i1]));
                $r11 = this.mExtInfo;
                UserExtendedInfo $r112 = $r11;
                $i1 = $r11.nextEndorsement($i1);
            }
        }
        return $i1;
    }

    private void openMyProfileScroll(int $i0) throws  {
        Intent $r1 = new Intent(this, MyProfileActivity.class);
        $r1.putExtra(MyProfileActivity.INTENT_SCROLL_TO, $i0);
        startActivityForResult($r1, 0);
    }

    private void hideDriverPhoto() throws  {
        ((TextView) findViewById(C1283R.id.driverNoPhotoText)).setText(DisplayStrings.displayString(DisplayStrings.DS_DRIVER_PROFILE_NO_PHOTO));
        findViewById(C1283R.id.driverNoPhotoText).setVisibility(0);
        findViewById(C1283R.id.driverImageProgress).setVisibility(8);
        ((ProgressAnimation) findViewById(C1283R.id.driverImageProgress)).stop();
        findViewById(C1283R.id.driverPhoto).setVisibility(8);
        findViewById(C1283R.id.driverPhotoGradient).setVisibility(8);
    }

    private void showDriverPhoto(final Bitmap $r1) throws  {
        findViewById(C1283R.id.driverPhoto).setOnClickListener(new OnClickListener() {
            public void onClick(View $r1) throws  {
                CarpoolDriverProfileActivity.this.zoomCarImage($r1, CarpoolDriverProfileActivity.this.findViewById(C1283R.id.driverProfileLayout), $r1);
            }
        });
        findViewById(C1283R.id.driverPhoto).setVisibility(0);
        findViewById(C1283R.id.driverPhotoGradient).setVisibility(0);
        findViewById(C1283R.id.driverNoPhotoText).setVisibility(8);
        findViewById(C1283R.id.driverImageProgress).setVisibility(8);
    }

    private static boolean isProfilePhotoBad(CarpoolUserData $r0) throws  {
        if ($r0 == null) {
            return false;
        }
        if (ConfigValues.getBoolValue(6)) {
            return $r0.photoAbuseStatus == 3;
        } else {
            return false;
        }
    }

    public static int getPercentCompleteProfile() throws  {
        return CarpoolNativeManager.getInstance().getCarpoolProfileCompletionPercentage();
    }

    protected void onActivityResult(int $i0, int $i1, Intent $r1) throws  {
        if ($i0 == 11 && $r1 != null) {
            if ($r1.hasExtra("account_is_set")) {
                this.mAccountIsSet = $r1.getBooleanExtra("account_is_set", false);
            }
        }
        if ($i0 == 12) {
        }
        if ($i0 == 222 || $i0 == 223) {
            if (this.mImageTaker != null) {
                this.mImageTaker.onActivityResult($i0, $i1, $r1);
                if (this.mImageTaker.hasImage()) {
                    ImageView $r6 = (ImageView) findViewById(C1283R.id.driverPhoto);
                    $r6.setImageDrawable(new BitmapDrawable(this.mImageTaker.getImage()));
                    showDriverPhoto(this.mImageTaker.getImage());
                    $r6.invalidate();
                    NativeManager.getInstance().UploadProfileImage(this.mImageTaker.getImagePath());
                    String $r8 = this.mUser.getImage();
                    if (!($r8 == null || $r8.isEmpty())) {
                        ImageRepository.instance.unCache($r8);
                        ImageRepository.instance.forceCache($r8, this.mImageTaker.getImage(), false);
                    }
                }
            }
        } else if ($i1 == -1) {
            setResult(-1);
            finish();
        }
        setupActivity();
        super.onActivityResult($i0, $i1, $r1);
    }

    private void zoomCarImage(View $r1, View $r2, Bitmap $r3) throws  {
        float height;
        if (this.mAnimator != null) {
            this.mAnimator.cancel();
        }
        Rect $r7 = $r4;
        Rect $r4 = new Rect();
        $r4 = r17;
        Rect r17 = new Rect();
        Point $r5 = r18;
        Point r18 = new Point();
        $r1.getGlobalVisibleRect($r7);
        if ($r2 != null) {
            $r2.getGlobalVisibleRect($r4, $r5);
            $r7.offset(-$r5.x, -$r5.y);
            $r4.offset(-$r5.x, -$r5.y);
        }
        if ($r3 != null) {
            int $i0 = ((($r7.height() * $r3.getWidth()) / $r3.getHeight()) - $r7.width()) / 2;
            $r7.set($r7.left - $i0, $r7.top, $r7.right + $i0, $r7.bottom);
            ((ImageView) findViewById(C1283R.id.driverProfileZoomedCar)).setImageBitmap($r3);
        }
        float $f0;
        float $f2;
        float f;
        if (((float) $r4.width()) / ((float) $r4.height()) > ((float) $r7.width()) / ((float) $r7.height())) {
            height = ((float) $r7.height()) / ((float) $r4.height());
            $f0 = ((height * ((float) $r4.width())) - ((float) $r7.width())) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            $f2 = (float) $r7.left;
            $f2 -= $f0;
            f = $f2;
            $r7.left = (int) $f2;
            $r7.right = (int) (((float) $r7.right) + $f0);
        } else {
            height = ((float) $r7.width()) / ((float) $r4.width());
            $f0 = ((height * ((float) $r4.height())) - ((float) $r7.height())) / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN;
            $f2 = (float) $r7.top;
            $f2 -= $f0;
            f = $f2;
            $r7.top = (int) $f2;
            $r7.bottom = (int) (((float) $r7.bottom) + $f0);
        }
        findViewById(C1283R.id.driverProfileZoomedCar).setVisibility(0);
        findViewById(C1283R.id.driverProfileZoomedCar).setPivotX(0.0f);
        findViewById(C1283R.id.driverProfileZoomedCar).setPivotY(0.0f);
        Animator $r6 = r0;
        Animator animatorSet = new AnimatorSet();
        $r6.play(ObjectAnimator.ofFloat(findViewById(C1283R.id.driverProfileZoomedCar), View.X, new float[]{(float) $r7.left, (float) $r4.left})).with(ObjectAnimator.ofFloat(findViewById(C1283R.id.driverProfileZoomedCar), View.Y, new float[]{(float) $r7.top, (float) $r4.top})).with(ObjectAnimator.ofFloat(findViewById(C1283R.id.driverProfileZoomedCar), View.SCALE_X, new float[]{height, 1.06535322E9f})).with(ObjectAnimator.ofFloat(findViewById(C1283R.id.driverProfileZoomedCar), View.SCALE_Y, new float[]{height, 1.06535322E9f})).with(ObjectAnimator.ofFloat(findViewById(C1283R.id.driverProfileZoomedCarBackground), View.ALPHA, new float[]{0.0f, 1.0f}));
        $r6.setDuration(400);
        $r6.setInterpolator(new OvershootInterpolator(1.0f));
        $r6.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator $r1) throws  {
                super.onAnimationEnd($r1);
                CarpoolDriverProfileActivity.this.mAnimator = null;
            }

            public void onAnimationStart(Animator $r1) throws  {
                super.onAnimationStart($r1);
                CarpoolDriverProfileActivity.this.findViewById(C1283R.id.driverProfileZoomedCarBackground).setVisibility(0);
            }
        });
        $r6.start();
        this.mAnimator = $r6;
        findViewById(C1283R.id.driverProfileZoomedCarBackground).setOnClickListener(new OnClickListener() {
            public void onClick(View v) throws  {
                CarpoolDriverProfileActivity.this.findViewById(C1283R.id.driverProfileZoomedCar).setVisibility(8);
                CarpoolDriverProfileActivity.this.findViewById(C1283R.id.driverProfileZoomedCarBackground).setVisibility(8);
                CarpoolDriverProfileActivity.this.findViewById(C1283R.id.driverProfileCarImage).setVisibility(0);
            }
        });
    }

    private void setUnconfigured(WazeTextView $r1, int $i0) throws  {
        String $r4 = this.mNm.getLanguageString($i0);
        SpannableString $r2 = new SpannableString($r4);
        $r2.setSpan(new UnderlineSpan(), 0, $r4.length(), 0);
        $r1.setText($r2);
        $r1.setDrawableRight(null);
        $r1.setTextColor(getResources().getColor(C1283R.color.BlueLagoon));
    }

    private void setConfigured(WazeTextView $r1, String $r2) throws  {
        $r1.setDrawableRight(getResources().getDrawable(C1283R.drawable.carpool_profile_verified));
        $r1.setTextColor(getResources().getColor(C1283R.color.Light));
        $r1.setTypeface(null, 0);
        $r1.setText($r2);
    }

    private void setConfigured(WazeTextView $r1, SpannableString $r2) throws  {
        $r1.setDrawableRight(null);
        $r1.setTextColor(getResources().getColor(C1283R.color.Light));
        $r1.setTypeface(null, 0);
        $r1.setText($r2);
    }

    private void setConfiguredUnderlined(WazeTextView $r1, int $i0) throws  {
        setConfiguredUnderlined($r1, this.mNm.getLanguageString($i0));
    }

    private void setConfiguredUnderlined(WazeTextView $r1, String $r2) throws  {
        if ($r2 == null) {
            $r2 = "";
        }
        setConfigured($r1, $r2);
        SpannableString $r3 = r5;
        SpannableString r5 = new SpannableString($r2);
        $r3.setSpan(new UnderlineSpan(), 0, $r2.length(), 0);
        $r1.setText($r3);
    }

    private void setConfigured(WazeTextView $r1, int $i0) throws  {
        setConfigured($r1, this.mNm.getLanguageString($i0));
    }

    protected boolean myHandleMessage(Message $r1) throws  {
        if ($r1.what == CarpoolNativeManager.UH_CARPOOL_USER) {
            if (!(this.mUser == null || TextUtils.isEmpty(this.mUser.getImage()))) {
                VolleyManager.getInstance().removeCache(this.mUser.getImage(), 0, 0);
            }
            setupActivity();
        }
        if ($r1.what != CarpoolNativeManager.UH_CARPOOL_EXT_USER) {
            return false;
        }
        this.mExtInfo = (UserExtendedInfo) $r1.getData().getParcelable(CarpoolNativeManager.BUNDLE_EXT_INFO);
        updateEndorsements();
        return false;
    }

    public void onFacebookSettings(FacebookSettings $r1) throws  {
        this.mNm.CloseProgressPopup();
        Intent $r2 = new Intent(this, FacebookActivity.class);
        $r2.putExtra("com.waze.mywaze.facebooksettings", $r1);
        $r2.putExtra("RideWith", true);
        startActivityForResult($r2, 10);
    }

    public boolean dispatchTouchEvent(MotionEvent $r1) throws  {
        this.mDetector.onTouchEvent($r1);
        return super.dispatchTouchEvent($r1);
    }
}
