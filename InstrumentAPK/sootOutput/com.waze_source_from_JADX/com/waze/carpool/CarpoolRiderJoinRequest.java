package com.waze.carpool;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import com.waze.C1283R;
import com.waze.Logger;
import com.waze.MainActivity;
import com.waze.MainActivity.ITrackOrientation;
import com.waze.MsgBox;
import com.waze.analytics.AnalyticsBuilder;
import com.waze.analytics.AnalyticsEvents;
import com.waze.ifs.ui.ActivityBase;
import com.waze.ifs.ui.CircleFrameDrawable;
import com.waze.settings.SettingsCarpoolSeatsActivity;
import com.waze.strings.DisplayStrings;
import com.waze.utils.VolleyManager.ImageRequestListener;

public class CarpoolRiderJoinRequest extends Dialog implements ITrackOrientation {
    private ActivityBase mActivity;
    private CarpoolNativeManager mCpnm;
    private Runnable mDismissDialogRunnable = new C15251();
    private CarpoolDrive mDrive;
    private CarpoolRide[] mRidesJoined;

    class C15251 implements Runnable {
        C15251() throws  {
        }

        public void run() throws  {
            CarpoolRiderJoinRequest.this.dismiss();
        }
    }

    class C15262 implements ImageRequestListener {
        public boolean receivedCacheResponse = false;

        C15262() throws  {
        }

        public void onImageLoadComplete(Bitmap $r1, Object token, long duration) throws  {
            ((ImageView) CarpoolRiderJoinRequest.this.findViewById(C1283R.id.riderReqImageRider)).setImageDrawable(new CircleFrameDrawable($r1, 0, 4));
        }

        public void onImageLoadFailed(Object token, long duration) throws  {
            if (this.receivedCacheResponse) {
                Logger.m36d("CarpoolRiderJoinRequest: failed loading photo URL");
            } else {
                this.receivedCacheResponse = true;
            }
        }
    }

    class C15273 implements OnClickListener {
        C15273() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_JOINED_OVERLAY_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_VIEW_RIDE).send();
            CarpoolRiderJoinRequest.this.mDismissDialogRunnable.run();
        }
    }

    class C15284 implements OnClickListener {
        C15284() throws  {
        }

        public void onClick(View v) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_JOINED_OVERLAY_CLICKED).addParam("ACTION", AnalyticsEvents.ANALYTICS_EVENT_VALUE_WHATS_THIS).send();
            CarpoolRiderJoinRequest.showMultipxIntroPopup(CarpoolRiderJoinRequest.this.mActivity, null);
            CarpoolRiderJoinRequest.this.mDismissDialogRunnable.run();
        }
    }

    static class C15306 implements OnCancelListener {
        C15306() throws  {
        }

        public void onCancel(DialogInterface dialog) throws  {
            AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_MULTIPAX_INTRO_POPUP_CLICKED).addParam("ACTION", "BACK").send();
        }
    }

    private void initLayout() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:39:0x02c6 in {7, 10, 13, 18, 23, 28, 30, 34, 37, 40, 43, 48} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r42 = this;
        r0 = r42;
        r2 = r0.mRidesJoined;
        if (r2 == 0) goto L_0x0013;
    L_0x0006:
        r0 = r42;
        r2 = r0.mRidesJoined;
        r3 = r2.length;
        if (r3 == 0) goto L_0x0013;
    L_0x000d:
        r0 = r42;
        r4 = r0.mDrive;
        if (r4 != 0) goto L_0x0019;
    L_0x0013:
        r5 = "CarpoolRiderJoinRequest: received empty drive or rider";
        com.waze.Logger.m38e(r5);
        return;
    L_0x0019:
        r6 = 2130903132; // 0x7f03005c float:1.7413073E38 double:1.052806032E-314;
        r0 = r42;
        r0.setContentView(r6);
        r0 = r42;
        r7 = r0.getWindow();
        if (r7 == 0) goto L_0x002e;
    L_0x0029:
        r6 = -1;
        r8 = -1;
        r7.setLayout(r6, r8);
    L_0x002e:
        r6 = 2131690214; // 0x7f0f02e6 float:1.9009465E38 double:1.0531949023E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r11 = r9;
        r11 = (android.widget.TextView) r11;
        r10 = r11;
        r6 = 3670; // 0xe56 float:5.143E-42 double:1.813E-320;
        r12 = com.waze.strings.DisplayStrings.displayString(r6);
        r10.setText(r12);
        r0 = r42;
        r4 = r0.mDrive;
        r13 = r4.itinerary;
        r14 = r13.free_offer;
        if (r14 == 0) goto L_0x01ef;
    L_0x004e:
        r6 = 2131690215; // 0x7f0f02e7 float:1.9009467E38 double:1.053194903E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 8;
        r9.setVisibility(r6);
        r6 = 2131690216; // 0x7f0f02e8 float:1.900947E38 double:1.0531949033E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 8;
        r9.setVisibility(r6);
    L_0x006a:
        r0 = r42;
        r2 = r0.mRidesJoined;
        r3 = r2.length;
        r6 = 1;
        if (r3 != r6) goto L_0x02e0;
    L_0x0072:
        r6 = 2131690213; // 0x7f0f02e5 float:1.9009463E38 double:1.053194902E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r15 = r9;
        r15 = (android.widget.TextView) r15;
        r10 = r15;
        r6 = 3668; // 0xe54 float:5.14E-42 double:1.812E-320;
        r12 = com.waze.strings.DisplayStrings.displayString(r6);
        r10.setText(r12);
        r6 = 2131690219; // 0x7f0f02eb float:1.9009475E38 double:1.053194905E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 0;
        r9.setVisibility(r6);
        r6 = 2131690220; // 0x7f0f02ec float:1.9009477E38 double:1.0531949053E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 0;
        r9.setVisibility(r6);
        r6 = 2131690224; // 0x7f0f02f0 float:1.9009486E38 double:1.0531949073E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 0;
        r9.setVisibility(r6);
        r6 = 2131690225; // 0x7f0f02f1 float:1.9009488E38 double:1.0531949077E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 8;
        r9.setVisibility(r6);
        r0 = r42;
        r2 = r0.mRidesJoined;
        r6 = 0;
        r16 = r2[r6];
        r0 = r16;
        r0 = r0.rider;
        r17 = r0;
        r6 = 2131692372; // 0x7f0f0b54 float:1.9013842E38 double:1.0531959685E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 2131690221; // 0x7f0f02ed float:1.900948E38 double:1.053194906E-314;
        r0 = r42;
        r18 = r0.findViewById(r6);
        r19 = r18;
        r19 = (android.widget.TextView) r19;
        r10 = r19;
        r0 = r17;
        r12 = r0.getName();
        if (r12 == 0) goto L_0x02ca;
    L_0x00ea:
        r0 = r17;
        r12 = r0.getName();
    L_0x00f0:
        r10.setText(r12);
        r0 = r17;
        r0 = r0.star_rating_as_pax;
        r20 = r0;
        r0 = r17;
        r3 = r0.completed_rides_pax;
        r0 = r17;
        r12 = r0.getFirstName();
        r0 = r20;
        com.waze.carpool.CarpoolUtils.setStarsView(r0, r3, r9, r12);
        r6 = 2131692378; // 0x7f0f0b5a float:1.9013854E38 double:1.0531959715E-314;
        r9 = r9.findViewById(r6);
        r6 = 8;
        r9.setVisibility(r6);
        r0 = r17;
        r12 = r0.getWorkplace();
        if (r12 == 0) goto L_0x02cd;
    L_0x011c:
        r0 = r17;
        r12 = r0.getWorkplace();
        r14 = r12.isEmpty();
        if (r14 != 0) goto L_0x02cd;
    L_0x0128:
        r6 = 2131690222; // 0x7f0f02ee float:1.9009482E38 double:1.0531949063E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r21 = r9;
        r21 = (android.widget.TextView) r21;
        r10 = r21;
        r6 = 3674; // 0xe5a float:5.148E-42 double:1.815E-320;
        r12 = com.waze.strings.DisplayStrings.displayString(r6);
        r6 = 1;
        r0 = new java.lang.Object[r6];
        r22 = r0;
        r0 = r17;
        r23 = r0.getWorkplace();
        r6 = 0;
        r22[r6] = r23;
        r0 = r22;
        r12 = java.lang.String.format(r12, r0);
        r10.setText(r12);
    L_0x0154:
        r0 = r17;
        r12 = r0.photo_url;
        if (r12 == 0) goto L_0x017c;
    L_0x015a:
        r0 = r17;
        r12 = r0.photo_url;
        r14 = r12.isEmpty();
        if (r14 != 0) goto L_0x017c;
    L_0x0164:
        r24 = com.waze.utils.VolleyManager.getInstance();
        r0 = r17;
        r12 = r0.photo_url;
        r25 = new com.waze.carpool.CarpoolRiderJoinRequest$2;
        r0 = r25;
        r1 = r42;
        r0.<init>();
        r0 = r24;
        r1 = r25;
        r0.loadImageFromUrl(r12, r1);
    L_0x017c:
        r6 = 2131690227; // 0x7f0f02f3 float:1.9009492E38 double:1.0531949087E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r26 = r9;
        r26 = (android.widget.TextView) r26;
        r10 = r26;
        r6 = 3675; // 0xe5b float:5.15E-42 double:1.8157E-320;
        r12 = com.waze.strings.DisplayStrings.displayString(r6);
        r10.setText(r12);
        r6 = 2131690226; // 0x7f0f02f2 float:1.900949E38 double:1.053194908E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r27 = new com.waze.carpool.CarpoolRiderJoinRequest$3;
        r0 = r27;
        r1 = r42;
        r0.<init>();
        r0 = r27;
        r9.setOnClickListener(r0);
        r6 = 3676; // 0xe5c float:5.151E-42 double:1.816E-320;
        r12 = com.waze.strings.DisplayStrings.displayString(r6);
        r28 = new android.text.SpannableString;
        r0 = r28;
        r0.<init>(r12);
        r29 = new android.text.style.UnderlineSpan;
        r0 = r29;
        r0.<init>();
        r3 = r12.length();
        r6 = 0;
        r8 = 0;
        r0 = r28;
        r1 = r29;
        r0.setSpan(r1, r6, r3, r8);
        r6 = 2131690228; // 0x7f0f02f4 float:1.9009494E38 double:1.053194909E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r30 = r9;
        r30 = (android.widget.TextView) r30;
        r10 = r30;
        r0 = r28;
        r10.setText(r0);
        r31 = new com.waze.carpool.CarpoolRiderJoinRequest$4;
        r0 = r31;
        r1 = r42;
        r0.<init>();
        r0 = r31;
        r10.setOnClickListener(r0);
        return;
    L_0x01ef:
        r6 = 2131690215; // 0x7f0f02e7 float:1.9009467E38 double:1.053194903E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 0;
        r9.setVisibility(r6);
        r6 = 2131690216; // 0x7f0f02e8 float:1.900947E38 double:1.0531949033E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 0;
        r9.setVisibility(r6);
        r6 = 2131690215; // 0x7f0f02e7 float:1.9009467E38 double:1.053194903E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r32 = r9;
        r32 = (android.widget.TextView) r32;
        r10 = r32;
        r6 = 3671; // 0xe57 float:5.144E-42 double:1.8137E-320;
        r12 = com.waze.strings.DisplayStrings.displayString(r6);
        r10.setText(r12);
        r6 = 2131690217; // 0x7f0f02e9 float:1.9009471E38 double:1.053194904E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r33 = r9;
        r33 = (android.widget.TextView) r33;
        r10 = r33;
        r6 = 3672; // 0xe58 float:5.146E-42 double:1.814E-320;
        r12 = com.waze.strings.DisplayStrings.displayString(r6);
        r6 = 1;
        r0 = new java.lang.Object[r6];
        r22 = r0;
        r0 = r42;
        r4 = r0.mDrive;
        r0 = r42;
        r34 = r0.getContext();
        r0 = r34;
        r23 = r4.getRewardString(r0);
        r6 = 0;
        r22[r6] = r23;
        r0 = r22;
        r12 = java.lang.String.format(r12, r0);
        r10.setText(r12);
        r0 = r42;
        r4 = r0.mDrive;
        r0 = r42;
        r34 = r0.getContext();
        r0 = r34;
        r12 = r4.getOriginalRewardString(r0);
        if (r12 == 0) goto L_0x02b3;
    L_0x0269:
        r6 = 3673; // 0xe59 float:5.147E-42 double:1.8147E-320;
        r23 = com.waze.strings.DisplayStrings.displayString(r6);
        r6 = 1;
        r0 = new java.lang.Object[r6];
        r22 = r0;
        r6 = 0;
        r22[r6] = r12;
        r0 = r23;
        r1 = r22;
        r12 = java.lang.String.format(r0, r1);
        r28 = new android.text.SpannableString;
        r0 = r28;
        r0.<init>(r12);
        r35 = new android.text.style.StrikethroughSpan;
        r0 = r35;
        r0.<init>();
        r3 = r12.length();
        r6 = 0;
        r8 = 0;
        r0 = r28;
        r1 = r35;
        r0.setSpan(r1, r6, r3, r8);
        r6 = 2131690218; // 0x7f0f02ea float:1.9009473E38 double:1.0531949043E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r36 = r9;
        r36 = (android.widget.TextView) r36;
        r10 = r36;
        goto L_0x02ad;
    L_0x02aa:
        goto L_0x006a;
    L_0x02ad:
        r0 = r28;
        r10.setText(r0);
        goto L_0x02aa;
    L_0x02b3:
        r6 = 2131690218; // 0x7f0f02ea float:1.9009473E38 double:1.0531949043E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        goto L_0x02c0;
    L_0x02bd:
        goto L_0x006a;
    L_0x02c0:
        r6 = 8;
        r9.setVisibility(r6);
        goto L_0x02bd;
        goto L_0x02ca;
    L_0x02c7:
        goto L_0x00f0;
    L_0x02ca:
        r12 = "";
        goto L_0x02c7;
    L_0x02cd:
        r6 = 2131690222; // 0x7f0f02ee float:1.9009482E38 double:1.0531949063E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        goto L_0x02da;
    L_0x02d7:
        goto L_0x0154;
    L_0x02da:
        r6 = 8;
        r9.setVisibility(r6);
        goto L_0x02d7;
    L_0x02e0:
        r6 = 2131690213; // 0x7f0f02e5 float:1.9009463E38 double:1.053194902E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r37 = r9;
        r37 = (android.widget.TextView) r37;
        r10 = r37;
        r6 = 1;
        r0 = new java.lang.Object[r6];
        r22 = r0;
        r0 = r42;
        r2 = r0.mRidesJoined;
        r3 = r2.length;
        r38 = java.lang.Integer.valueOf(r3);
        r6 = 0;
        r22[r6] = r38;
        r6 = 3669; // 0xe55 float:5.141E-42 double:1.8127E-320;
        r0 = r22;
        r12 = com.waze.strings.DisplayStrings.displayStringF(r6, r0);
        r10.setText(r12);
        r6 = 2131690219; // 0x7f0f02eb float:1.9009475E38 double:1.053194905E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 8;
        r9.setVisibility(r6);
        r6 = 2131690220; // 0x7f0f02ec float:1.9009477E38 double:1.0531949053E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 8;
        r9.setVisibility(r6);
        r6 = 2131690224; // 0x7f0f02f0 float:1.9009486E38 double:1.0531949073E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r6 = 8;
        r9.setVisibility(r6);
        r6 = 2131690225; // 0x7f0f02f1 float:1.9009488E38 double:1.0531949077E-314;
        r0 = r42;
        r9 = r0.findViewById(r6);
        r40 = r9;
        r40 = (com.waze.view.button.RidersImages) r40;
        r39 = r40;
        r6 = 0;
        r0 = r39;
        r0.setVisibility(r6);
        r0 = r39;
        r0.clearImages();
        r0 = r42;
        r2 = r0.mRidesJoined;
        r3 = r2.length;
        r41 = 0;
    L_0x0356:
        r0 = r41;
        if (r0 >= r3) goto L_0x017c;
    L_0x035a:
        r16 = r2[r41];
        r0 = r16;
        r0 = r0.rider;
        r17 = r0;
        r12 = r0.getImage();
        r0 = r39;
        r0.addImage(r12);
        r41 = r41 + 1;
        goto L_0x0356;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolRiderJoinRequest.initLayout():void");
    }

    public CarpoolRiderJoinRequest(ActivityBase $r1, CarpoolDrive $r2, CarpoolRide[] $r3) throws  {
        super($r1, C1283R.style.NoDimDialog);
        this.mRidesJoined = $r3;
        this.mDrive = $r2;
        this.mCpnm = CarpoolNativeManager.getInstance();
        this.mActivity = $r1;
        if (this.mActivity instanceof MainActivity) {
            ((MainActivity) this.mActivity).addOrientationTracker(this);
        }
        initLayout();
    }

    public static void showMultipxIntroPopup(ActivityBase $r0, Runnable $r1) throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_MULTIPAX_INTRO_POPUP_SHOWN).send();
        final ActivityBase activityBase = $r0;
        final Runnable runnable = $r1;
        Dialog $r9 = MsgBox.openConfirmDialogJavaCallback(DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MPAX_INTRO_TITLE), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MPAX_INTRO_TEXT), false, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int $i0) throws  {
                AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_MULTIPAX_INTRO_POPUP_CLICKED).addParam("ACTION", $i0 == 0 ? "CLOSE" : "SETTINGS").send();
                if ($i0 != 0) {
                    activityBase.startActivityForResult(new Intent(activityBase, SettingsCarpoolSeatsActivity.class), 0);
                    if (runnable != null) {
                        runnable.run();
                    }
                }
            }
        }, DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MPAX_INTRO_SET_RIDERS), DisplayStrings.displayString(DisplayStrings.DS_CARPOOL_MPAX_INTRO_CLOSE), -1, "carpool_multipax_popup_illustration", new C15306(), false, true, false);
        if ($r9 != null) {
            ImageView $r11 = (ImageView) $r9.findViewById(C1283R.id.confirmImage);
            $r11.getLayoutParams().width = -2;
            $r11.getLayoutParams().height = -2;
        }
    }

    public void show() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_JOINED_OVERLAY_SHOWN).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_DRIVE_ID, this.mDrive.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_RIDER_USER_ID, this.mRidesJoined[0].rider.getId()).addParam(AnalyticsEvents.ANALYTICS_EVENT_INFO_NUM_RIDERS, (long) this.mRidesJoined.length).send();
        super.show();
    }

    public void onOrientationChanged(int orientation) throws  {
        initLayout();
    }

    public void onBackPressed() throws  {
        AnalyticsBuilder.analytics(AnalyticsEvents.ANALYTICS_EVENT_RW_RIDER_JOINED_OVERLAY_CLICKED).addParam("ACTION", "BACK").send();
        super.onBackPressed();
    }
}
