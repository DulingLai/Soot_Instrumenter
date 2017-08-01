package com.waze.navigate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.navigate.ParkingSearchResultsActivity.ParkingSuggestionAddressItem;
import com.waze.utils.PixelMeasure;

class ParkingResultItemView extends SearchResultItemViewBase {
    private final int AD_BORDER_COLOR;
    private TextView ETALabel;
    private TextView adLabel;
    private TextView groupLabel;
    private View root;
    private TextView walkingTimeLabel;

    protected void setFields() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:21:0x00e9 in {2, 5, 8, 12, 15, 18, 22, 31, 35, 38, 42, 47, 49, 50} preds:[]
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.computeDominators(BlockProcessor.java:129)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:48)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.visit(BlockProcessor.java:38)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
*/
        /*
        r32 = this;
        r0 = r32;
        super.setFields();
        r0 = r32;
        r4 = r0.mAddressItem;
        r5 = r4.getTitle();
        r6 = android.text.TextUtils.isEmpty(r5);
        if (r6 != 0) goto L_0x011e;
    L_0x0013:
        r0 = r32;
        r7 = r0.mTitleLabel;
        r0 = r32;
        r4 = r0.mAddressItem;
        r5 = r4.getTitle();
        r7.setText(r5);
    L_0x0022:
        r0 = r32;
        r8 = r0.getParkingAddressItem();
        r9 = com.waze.navigate.NavigateNativeManager.instance();
        r10 = r8.walkingDistance;
        r10 = r9.calcWalkingMinutesNTV(r10);
        r11 = r10;
        if (r10 >= 0) goto L_0x0036;
    L_0x0035:
        r11 = 0;
    L_0x0036:
        r13 = 2131691342; // 0x7f0f074e float:1.9011753E38 double:1.0531954596E-314;
        r0 = r32;
        r12 = r0.findViewById(r13);
        r13 = 0;
        r12.setVisibility(r13);
        r0 = r32;
        r7 = r0.walkingTimeLabel;
        r13 = 1;
        r14 = new java.lang.Object[r13];
        r15 = java.lang.Integer.valueOf(r11);
        r13 = 0;
        r14[r13] = r15;
        r13 = 1783; // 0x6f7 float:2.499E-42 double:8.81E-321;
        r5 = com.waze.strings.DisplayStrings.displayStringF(r13, r14);
        r7.setText(r5);
        r13 = 2131691341; // 0x7f0f074d float:1.9011751E38 double:1.053195459E-314;
        r0 = r32;
        r12 = r0.findViewById(r13);
        r17 = r12;
        r17 = (com.waze.view.map.ProgressAnimation) r17;
        r16 = r17;
        r10 = r8.ETA;
        r13 = -1;
        if (r10 != r13) goto L_0x0134;
    L_0x006e:
        r0 = r32;
        r7 = r0.ETALabel;
        r13 = 0;
        r7.setVisibility(r13);
        r0 = r32;
        r7 = r0.ETALabel;
        r13 = 1782; // 0x6f6 float:2.497E-42 double:8.804E-321;
        r5 = com.waze.strings.DisplayStrings.displayString(r13);
        r7.setText(r5);
        r0 = r16;
        r0.start();
        r13 = 0;
        r0 = r16;
        r0.setVisibility(r13);
    L_0x008e:
        r6 = r8.hasIcon();
        if (r6 == 0) goto L_0x01bf;
    L_0x0094:
        r18 = new java.lang.StringBuilder;
        r0 = r18;
        r0.<init>();
        r5 = r8.getIcon();
        goto L_0x00a3;
    L_0x00a0:
        goto L_0x0022;
    L_0x00a3:
        r0 = r18;
        r18 = r0.append(r5);
        r19 = ".png";
        r0 = r18;
        r1 = r19;
        r18 = r0.append(r1);
        r0 = r18;
        r5 = r0.toString();
        r20 = com.waze.ResManager.GetSkinDrawable(r5);
        if (r20 == 0) goto L_0x00ca;
    L_0x00bf:
        r0 = r32;
        r0 = r0.mIconImage;
        r21 = r0;
        r1 = r20;
        r0.setImageDrawable(r1);
    L_0x00ca:
        r6 = r8.sponsored;
        if (r6 == 0) goto L_0x01ce;
    L_0x00ce:
        r0 = r32;
        r7 = r0.adLabel;
        r13 = 0;
        r7.setVisibility(r13);
        r0 = r32;
        r7 = r0.adLabel;
        r13 = 1777; // 0x6f1 float:2.49E-42 double:8.78E-321;
        r5 = com.waze.strings.DisplayStrings.displayString(r13);
        r7.setText(r5);
    L_0x00e3:
        r6 = r8.firstTitle;
        goto L_0x00ed;
    L_0x00e6:
        goto L_0x00a0;
        goto L_0x00ed;
    L_0x00ea:
        goto L_0x008e;
    L_0x00ed:
        if (r6 != 0) goto L_0x00f3;
    L_0x00ef:
        r6 = r8.secondTitle;
        if (r6 == 0) goto L_0x01ff;
    L_0x00f3:
        r0 = r32;
        r7 = r0.groupLabel;
        r13 = 0;
        r7.setVisibility(r13);
        r6 = r8.firstTitle;
        if (r6 == 0) goto L_0x01d8;
    L_0x00ff:
        r0 = r32;
        r7 = r0.groupLabel;
        r13 = 1779; // 0x6f3 float:2.493E-42 double:8.79E-321;
        r5 = com.waze.strings.DisplayStrings.displayString(r13);
        r7.setText(r5);
        r6 = r8.sponsored;
        if (r6 != 0) goto L_0x0217;
    L_0x0110:
        r13 = 2131691337; // 0x7f0f0749 float:1.9011743E38 double:1.053195457E-314;
        r0 = r32;
        r12 = r0.findViewById(r13);
        r13 = 0;
        r12.setVisibility(r13);
        return;
    L_0x011e:
        r0 = r32;
        r7 = r0.mTitleLabel;
        r13 = 0;
        r7.setVisibility(r13);
        r0 = r32;
        r7 = r0.mTitleLabel;
        r13 = 1784; // 0x6f8 float:2.5E-42 double:8.814E-321;
        r5 = com.waze.strings.DisplayStrings.displayString(r13);
        r7.setText(r5);
        goto L_0x00e6;
    L_0x0134:
        if (r10 != 0) goto L_0x0150;
    L_0x0136:
        r0 = r32;
        r7 = r0.ETALabel;
        r13 = 8;
        r7.setVisibility(r13);
        r0 = r16;
        r0.stop();
        goto L_0x0148;
    L_0x0145:
        goto L_0x00ca;
    L_0x0148:
        r13 = 8;
        r0 = r16;
        r0.setVisibility(r13);
        goto L_0x00ea;
    L_0x0150:
        r0 = r32;
        r7 = r0.ETALabel;
        r13 = 0;
        r7.setVisibility(r13);
        r22 = java.util.Calendar.getInstance();
        goto L_0x0160;
    L_0x015d:
        goto L_0x00e3;
    L_0x0160:
        r0 = r22;
        r23 = r0.getTimeZone();
        r24 = com.waze.AppService.getAppContext();
        r0 = r24;
        r25 = android.text.format.DateFormat.getTimeFormat(r0);
        r0 = r25;
        r1 = r23;
        r0.setTimeZone(r1);
        r26 = new java.util.Date;
        r27 = java.lang.System.currentTimeMillis();
        r10 = r10 * 1000;
        r0 = (long) r10;
        r29 = r0;
        goto L_0x0186;
    L_0x0183:
        goto L_0x0145;
    L_0x0186:
        r0 = r27;
        r2 = r29;
        r0 = r0 + r2;
        r27 = r0;
        r0 = r26;
        r1 = r27;
        r0.<init>(r1);
        r0 = r25;
        r1 = r26;
        r5 = r0.format(r1);
        r0 = r32;
        r7 = r0.ETALabel;
        r13 = 1;
        r14 = new java.lang.Object[r13];
        r13 = 0;
        r14[r13] = r5;
        r13 = 1781; // 0x6f5 float:2.496E-42 double:8.8E-321;
        r5 = com.waze.strings.DisplayStrings.displayStringF(r13, r14);
        r7.setText(r5);
        r0 = r16;
        r0.stop();
        goto L_0x01b8;
    L_0x01b5:
        goto L_0x008e;
    L_0x01b8:
        r13 = 4;
        r0 = r16;
        r0.setVisibility(r13);
        goto L_0x01b5;
    L_0x01bf:
        r0 = r32;
        r0 = r0.mIconImage;
        r21 = r0;
        r13 = 2130838711; // 0x7f0204b7 float:1.7282412E38 double:1.052774204E-314;
        r0 = r21;
        r0.setImageResource(r13);
        goto L_0x0183;
    L_0x01ce:
        r0 = r32;
        r7 = r0.adLabel;
        r13 = 8;
        r7.setVisibility(r13);
        goto L_0x015d;
    L_0x01d8:
        r0 = r32;
        r7 = r0.groupLabel;
        r13 = 1780; // 0x6f4 float:2.494E-42 double:8.794E-321;
        r5 = com.waze.strings.DisplayStrings.displayString(r13);
        r7.setText(r5);
        r0 = r32;
        r7 = r0.groupLabel;
        r31 = 0;
        r0 = r31;
        r7.setBackgroundDrawable(r0);
        r13 = 2131691337; // 0x7f0f0749 float:1.9011743E38 double:1.053195457E-314;
        r0 = r32;
        r12 = r0.findViewById(r13);
        r13 = 8;
        r12.setVisibility(r13);
        return;
    L_0x01ff:
        r0 = r32;
        r7 = r0.groupLabel;
        r13 = 8;
        r7.setVisibility(r13);
        r13 = 2131691337; // 0x7f0f0749 float:1.9011743E38 double:1.053195457E-314;
        r0 = r32;
        r12 = r0.findViewById(r13);
        r13 = 8;
        r12.setVisibility(r13);
        return;
    L_0x0217:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.ParkingResultItemView.setFields():void");
    }

    private ParkingSuggestionAddressItem getParkingAddressItem() throws  {
        return (ParkingSuggestionAddressItem) this.mAddressItem;
    }

    public ParkingResultItemView(Context $r1) throws  {
        this($r1, null);
    }

    public ParkingResultItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public ParkingResultItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.AD_BORDER_COLOR = -9984071;
    }

    protected void init() throws  {
        this.content = LayoutInflater.from(getContext()).inflate(C1283R.layout.parking_result_item, null);
        super.init();
        this.ETALabel = (TextView) this.content.findViewById(C1283R.id.lblETA);
        this.walkingTimeLabel = (TextView) this.content.findViewById(C1283R.id.lblTime);
        this.groupLabel = (TextView) this.content.findViewById(C1283R.id.lblGroupTitle);
        this.adLabel = (TextView) this.content.findViewById(C1283R.id.lblAd);
        this.root = this.content.findViewById(C1283R.id.mainWrapper);
    }

    protected void setItemImage() throws  {
        if (this.mAddressItem.sponsored) {
            super.setItemImage();
        }
    }

    public void setIsFirstAdItem(boolean $z0, int $i0) throws  {
        LayoutParams $r3 = (LayoutParams) this.mMainContainer.getLayoutParams();
        if ($z0) {
            if ($i0 == 0) {
                this.groupLabel.setBackgroundColor(-9984071);
            } else {
                this.groupLabel.setBackgroundColor(getResources().getColor(C1283R.color.BlueWhale));
            }
            this.root.setBackgroundColor(-9984071);
            $r3.topMargin = PixelMeasure.dp(8);
            $r3.bottomMargin = PixelMeasure.dp(8);
            return;
        }
        this.groupLabel.setBackgroundColor(getResources().getColor(C1283R.color.BlueWhale));
        this.root.setBackgroundColor(getResources().getColor(C1283R.color.BlueWhale));
        $r3.topMargin = PixelMeasure.dp(4);
        $r3.bottomMargin = PixelMeasure.dp(4);
    }

    public void setFirstItem() throws  {
        setVerticalPadding(true, false);
    }

    public void setLastItem() throws  {
        setVerticalPadding(false, true);
    }

    public void setOnlyItem() throws  {
        setVerticalPadding(true, true);
    }
}
