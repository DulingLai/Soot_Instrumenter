package com.waze.navigate;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.utils.PixelMeasure;

class SearchResultItemView extends SearchResultItemViewBase {
    private static final float KM_TO_MILES = 0.621371f;
    private TextView mAdLabel;
    private TextView mCurrencyLabel;
    private ViewGroup mDealContainer;
    private TextView mDealTextLabel;
    private TextView mDistanceLabel;
    private ViewGroup mExtendedRightPanelContainer;
    private TextView mKmAwayLabel;
    private ViewGroup mPriceContainer;
    private TextView mPriceLabel;
    private Paint mSeparatorPaint;
    private TextView mTimeOffRouteLabel;
    private TextView mTimeStampLabel;

    protected void setFields() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:38:0x01f7
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.modifyBlocksTree(BlockProcessor.java:248)
	at jadx.core.dex.visitors.blocksmaker.BlockProcessor.processBlocksTree(BlockProcessor.java:52)
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
        r39 = this;
        r0 = r39;
        super.setFields();
        r0 = r39;
        r4 = r0.mAddressItem;
        r5 = r4.currency;
        if (r5 != 0) goto L_0x01d3;
    L_0x000d:
        r5 = "";
    L_0x000f:
        r6 = com.waze.NativeManager.getInstance();
        r0 = r39;
        r7 = r0.mCurrencyLabel;
        r5 = r6.getLanguageString(r5);
        r7.setText(r5);
        r0 = r39;
        r4 = r0.mAddressItem;
        r5 = r4.getDistance();
        r8 = android.text.TextUtils.isEmpty(r5);
        if (r8 != 0) goto L_0x01de;
    L_0x002c:
        r0 = r39;
        r7 = r0.mTimeOffRouteLabel;
        r9 = 0;
        r7.setVisibility(r9);
        r0 = r39;
        r7 = r0.mTimeOffRouteLabel;
        r0 = r39;
        r4 = r0.mAddressItem;
        r5 = r4.getDistance();
        r7.setText(r5);
    L_0x0043:
        r10 = com.waze.share.ShareNativeManager.getInstance();
        r8 = r10.isMetricUnitsNTV();
        r0 = r39;
        r4 = r0.mAddressItem;
        r11 = r4.distanceMeters;
        r12 = (float) r11;
        if (r8 == 0) goto L_0x01fb;
    L_0x0054:
        r13 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
    L_0x0057:
        r12 = r13 * r12;
        r14 = 1112014848; // 0x42480000 float:50.0 double:5.49408334E-315;
        r12 = r12 + r14;
        r14 = 1120403456; // 0x42c80000 float:100.0 double:5.53552857E-315;
        r12 = r12 / r14;
        r11 = (int) r12;
        r9 = 1776; // 0x6f0 float:2.489E-42 double:8.775E-321;
        r5 = r6.getLanguageString(r9);
        r9 = 1;
        r15 = new java.lang.Object[r9];
        if (r8 == 0) goto L_0x0203;
    L_0x006d:
        r16 = 133; // 0x85 float:1.86E-43 double:6.57E-322;
    L_0x006f:
        r0 = r16;
        r17 = r6.getLanguageString(r0);
        r9 = 0;
        r15[r9] = r17;
        r5 = java.lang.String.format(r5, r15);
        r0 = r39;
        r7 = r0.mKmAwayLabel;
        r7.setText(r5);
        r0 = r39;
        r7 = r0.mDistanceLabel;
        r9 = 2;
        r15 = new java.lang.Object[r9];
        r18 = r11 / 10;
        r0 = r18;
        r19 = java.lang.Integer.valueOf(r0);
        r9 = 0;
        r15[r9] = r19;
        r11 = r11 % 10;
        r19 = java.lang.Integer.valueOf(r11);
        r9 = 1;
        r15[r9] = r19;
        r20 = "%2d.%1d";
        r0 = r20;
        r5 = java.lang.String.format(r0, r15);
        r7.setText(r5);
        r0 = r39;
        r4 = r0.mAddressItem;
        r5 = r4.dealText;
        r8 = android.text.TextUtils.isEmpty(r5);
        if (r8 != 0) goto L_0x0206;
    L_0x00b5:
        r0 = r39;
        r0 = r0.mDealContainer;
        r21 = r0;
        r9 = 0;
        r0 = r21;
        r0.setVisibility(r9);
        r0 = r39;
        r7 = r0.mDealTextLabel;
        r0 = r39;
        r4 = r0.mAddressItem;
        r5 = r4.dealText;
        r7.setText(r5);
    L_0x00ce:
        r0 = r39;
        r7 = r0.mAdLabel;
        r0 = r39;
        r4 = r0.mAddressItem;
        r8 = r4.sponsored;
        if (r8 == 0) goto L_0x021c;
    L_0x00da:
        r22 = 0;
    L_0x00dc:
        r0 = r22;
        r7.setVisibility(r0);
        r0 = r39;
        r4 = r0.mAddressItem;
        r12 = r4.price;
        r14 = 0;
        r22 = (r12 > r14 ? 1 : (r12 == r14 ? 0 : -1));
        if (r22 == 0) goto L_0x02a9;
    L_0x00ec:
        r0 = r39;
        r0 = r0.mExtendedRightPanelContainer;
        r21 = r0;
        r9 = 0;
        r0 = r21;
        r0.setVisibility(r9);
        r0 = r39;
        r7 = r0.mPriceLabel;
        r9 = 1;
        r15 = new java.lang.Object[r9];
        r0 = r39;
        r4 = r0.mAddressItem;
        r12 = r4.price;
        r23 = java.lang.Float.valueOf(r12);
        r9 = 0;
        r15[r9] = r23;
        r20 = "%2.2f";
        r0 = r20;
        r5 = java.lang.String.format(r0, r15);
        r7.setText(r5);
        r0 = r39;
        r4 = r0.mAddressItem;
        r11 = r4.range;
        r9 = 1;
        if (r11 != r9) goto L_0x021f;
    L_0x0120:
        r0 = r39;
        r0 = r0.mPriceContainer;
        r21 = r0;
        r24 = new com.waze.view.drawables.ShadowedRectDrawable;
        r0 = r39;
        r25 = r0.getResources();
        r9 = 2131623936; // 0x7f0e0000 float:1.8875038E38 double:1.0531621566E-314;
        r0 = r25;
        r11 = r0.getColor(r9);
        r0 = r24;
        r0.<init>(r11);
        r27 = r24;
        r27 = (android.graphics.drawable.Drawable) r27;
        r26 = r27;
        r0 = r21;
        r1 = r26;
        r0.setBackgroundDrawable(r1);
    L_0x0149:
        r28 = java.lang.System.currentTimeMillis();
        r30 = 1000; // 0x3e8 float:1.401E-42 double:4.94E-321;
        r0 = r28;
        r2 = r30;
        r0 = r0 / r2;
        r28 = r0;
        r0 = r39;
        r4 = r0.mAddressItem;
        r0 = r4.lastUpdated;
        r32 = r0;
        r0 = r28;
        r2 = r32;
        r0 = r0 - r2;
        r28 = r0;
        r30 = 3600; // 0xe10 float:5.045E-42 double:1.7786E-320;
        r0 = r28;
        r2 = r30;
        r0 = r0 / r2;
        r28 = r0;
        r30 = 24;
        r0 = r28;
        r2 = r30;
        r0 = r0 / r2;
        r28 = r0;
        r30 = 1;
        r22 = (r28 > r30 ? 1 : (r28 == r30 ? 0 : -1));
        if (r22 <= 0) goto L_0x028d;
    L_0x017d:
        r9 = 55;
        r5 = r6.getLanguageString(r9);
        r9 = 1;
        r15 = new java.lang.Object[r9];
        r0 = r28;
        r11 = (int) r0;
        r19 = java.lang.Integer.valueOf(r11);
        r9 = 0;
        r15[r9] = r19;
        r5 = java.lang.String.format(r5, r15);
    L_0x0194:
        if (r5 == 0) goto L_0x01cb;
    L_0x0196:
        r11 = r5.length();
        if (r11 <= 0) goto L_0x01cb;
    L_0x019c:
        r34 = new java.lang.StringBuilder;
        r0 = r34;
        r0.<init>();
        r9 = 0;
        r35 = 1;
        r0 = r35;
        r17 = r5.substring(r9, r0);
        r0 = r17;
        r17 = r0.toUpperCase();
        r0 = r34;
        r1 = r17;
        r36 = r0.append(r1);
        r9 = 1;
        r5 = r5.substring(r9);
        r0 = r36;
        r36 = r0.append(r5);
        r0 = r36;
        r5 = r0.toString();
    L_0x01cb:
        r0 = r39;
        r7 = r0.mTimeStampLabel;
        r7.setText(r5);
        return;
    L_0x01d3:
        r0 = r39;
        r4 = r0.mAddressItem;
        goto L_0x01db;
    L_0x01d8:
        goto L_0x000f;
    L_0x01db:
        r5 = r4.currency;
        goto L_0x01d8;
    L_0x01de:
        r0 = r39;
        r7 = r0.mTimeOffRouteLabel;
        r20 = "";
        r0 = r20;
        r7.setText(r0);
        r0 = r39;
        r7 = r0.mTimeOffRouteLabel;
        goto L_0x01f1;
    L_0x01ee:
        goto L_0x0043;
    L_0x01f1:
        r9 = 8;
        r7.setVisibility(r9);
        goto L_0x01ee;
        goto L_0x01fb;
    L_0x01f8:
        goto L_0x0057;
    L_0x01fb:
        r13 = 1059000875; // 0x3f1f122b float:0.621371 double:5.232159513E-315;
        goto L_0x01f8;
        goto L_0x0203;
    L_0x0200:
        goto L_0x006f;
    L_0x0203:
        r16 = 464; // 0x1d0 float:6.5E-43 double:2.29E-321;
        goto L_0x0200;
    L_0x0206:
        r0 = r39;
        r0 = r0.mDealContainer;
        r21 = r0;
        goto L_0x0210;
    L_0x020d:
        goto L_0x00ce;
    L_0x0210:
        r9 = 8;
        r0 = r21;
        r0.setVisibility(r9);
        goto L_0x020d;
        goto L_0x021c;
    L_0x0219:
        goto L_0x00dc;
    L_0x021c:
        r22 = 8;
        goto L_0x0219;
    L_0x021f:
        r0 = r39;
        r4 = r0.mAddressItem;
        r11 = r4.range;
        r9 = 2;
        if (r11 != r9) goto L_0x0256;
    L_0x0228:
        r0 = r39;
        r0 = r0.mPriceContainer;
        r21 = r0;
        r24 = new com.waze.view.drawables.ShadowedRectDrawable;
        r0 = r39;
        r25 = r0.getResources();
        r9 = 2131624097; // 0x7f0e00a1 float:1.8875364E38 double:1.053162236E-314;
        r0 = r25;
        r11 = r0.getColor(r9);
        r0 = r24;
        r0.<init>(r11);
        r37 = r24;
        r37 = (android.graphics.drawable.Drawable) r37;
        r26 = r37;
        goto L_0x024e;
    L_0x024b:
        goto L_0x0149;
    L_0x024e:
        r0 = r21;
        r1 = r26;
        r0.setBackgroundDrawable(r1);
        goto L_0x024b;
    L_0x0256:
        r0 = r39;
        r4 = r0.mAddressItem;
        r11 = r4.range;
        r9 = 3;
        if (r11 != r9) goto L_0x0149;
    L_0x025f:
        r0 = r39;
        r0 = r0.mPriceContainer;
        r21 = r0;
        r24 = new com.waze.view.drawables.ShadowedRectDrawable;
        r0 = r39;
        r25 = r0.getResources();
        r9 = 2131623969; // 0x7f0e0021 float:1.8875104E38 double:1.053162173E-314;
        r0 = r25;
        r11 = r0.getColor(r9);
        r0 = r24;
        r0.<init>(r11);
        r38 = r24;
        r38 = (android.graphics.drawable.Drawable) r38;
        r26 = r38;
        goto L_0x0285;
    L_0x0282:
        goto L_0x0149;
    L_0x0285:
        r0 = r21;
        r1 = r26;
        r0.setBackgroundDrawable(r1);
        goto L_0x0282;
    L_0x028d:
        r30 = 1;
        r22 = (r28 > r30 ? 1 : (r28 == r30 ? 0 : -1));
        if (r22 != 0) goto L_0x02a2;
    L_0x0293:
        goto L_0x0297;
    L_0x0294:
        goto L_0x0194;
    L_0x0297:
        r9 = 788; // 0x314 float:1.104E-42 double:3.893E-321;
        r5 = r6.getLanguageString(r9);
        goto L_0x0294;
        goto L_0x02a2;
    L_0x029f:
        goto L_0x0194;
    L_0x02a2:
        r9 = 647; // 0x287 float:9.07E-43 double:3.197E-321;
        r5 = r6.getLanguageString(r9);
        goto L_0x029f;
    L_0x02a9:
        r0 = r39;
        r0 = r0.mExtendedRightPanelContainer;
        r21 = r0;
        r9 = 8;
        r0 = r21;
        r0.setVisibility(r9);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.navigate.SearchResultItemView.setFields():void");
    }

    public SearchResultItemView(Context $r1) throws  {
        this($r1, null);
    }

    public SearchResultItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public SearchResultItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
    }

    protected void init() throws  {
        this.content = LayoutInflater.from(getContext()).inflate(C1283R.layout.search_result_item, null);
        super.init();
        this.mTimeOffRouteLabel = (TextView) this.content.findViewById(C1283R.id.lblTimeOffRoute);
        this.mDistanceLabel = (TextView) this.content.findViewById(C1283R.id.lblDistance);
        this.mKmAwayLabel = (TextView) this.content.findViewById(C1283R.id.lblKmAway);
        this.mPriceLabel = (TextView) this.content.findViewById(C1283R.id.lblAveragePrice);
        this.mPriceContainer = (ViewGroup) this.content.findViewById(C1283R.id.priceContainer);
        this.mTimeStampLabel = (TextView) this.content.findViewById(C1283R.id.lblTimeStamp);
        this.mCurrencyLabel = (TextView) this.content.findViewById(C1283R.id.lblCurrency);
        this.mExtendedRightPanelContainer = (ViewGroup) this.content.findViewById(C1283R.id.extendedRightPanelContainer);
        this.mAdLabel = (TextView) this.content.findViewById(C1283R.id.lblAd);
        this.mDealTextLabel = (TextView) this.content.findViewById(C1283R.id.lblDealText);
        this.mDealContainer = (ViewGroup) this.content.findViewById(C1283R.id.dealContainer);
        this.mSeparatorPaint = new Paint();
        this.mSeparatorPaint.setColor(getResources().getColor(C1283R.color.PassiveGrey));
        this.mSeparatorPaint.setStrokeWidth((float) PixelMeasure.dp(1));
        this.mSeparatorPaint.setStyle(Style.STROKE);
    }

    public void setVerticalPadding(boolean $z0, boolean $z1) throws  {
        byte $b1;
        byte $b0 = (byte) 8;
        LayoutParams $r3 = (LayoutParams) this.mMainContainer.getLayoutParams();
        if ($z0) {
            $b1 = (byte) 8;
        } else {
            $b1 = (byte) 4;
        }
        $r3.topMargin = PixelMeasure.dp($b1);
        if (!$z1) {
            $b0 = (byte) 4;
        }
        $r3.bottomMargin = PixelMeasure.dp($b0);
        this.mMainContainer.setLayoutParams($r3);
    }

    protected void dispatchDraw(Canvas $r1) throws  {
        super.dispatchDraw($r1);
        float $f0 = (float) ((this.mMainContainer.getLeft() + this.mMainContainer.getMeasuredWidth()) - PixelMeasure.dp(80));
        $r1.drawLine($f0, (float) (this.mMainContainer.getTop() + PixelMeasure.dp(8)), $f0, (float) (this.mMainContainer.getBottom() - PixelMeasure.dp(8)), this.mSeparatorPaint);
    }
}
