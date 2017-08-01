package com.waze.carpool;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.waze.C1283R;
import com.waze.NativeManager;
import com.waze.carpool.CarpoolNativeManager.CarColors;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.ifs.ui.PointsView;
import com.waze.map.CanvasFont;
import com.waze.reports.PointsViewTextWatcher;
import com.waze.reports.PointsViewTextWatcher.TextValidator;
import com.waze.reports.PointsViewTextWatcher.ValidatedPointsViewsMgr;
import com.waze.settings.WazeSettingsView;
import com.waze.strings.DisplayStrings;
import com.waze.utils.EditTextUtils;
import com.waze.utils.ImageRepository;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.utils.PixelMeasure;
import com.waze.view.anim.AnimationUtils;
import com.waze.view.popups.BottomSheet;
import com.waze.view.popups.BottomSheet.Adapter;
import com.waze.view.popups.BottomSheet.ItemDetails;
import com.waze.view.popups.BottomSheet.Mode;
import com.waze.view.text.AutoResizeTextView;
import java.util.ArrayList;
import java.util.Iterator;

public class CarpoolCarProfileFragment extends Fragment implements ValidatedPointsViewsMgr {
    private static final String CARPOOL_MANDATORY_INDICATOR = " <font color=#FF7878>*</font>";
    private static final String TAG = CarpoolCarProfileFragment.class.getName();
    private boolean mCarColorMandatory;
    private boolean mCarMakeMandatory;
    private boolean mCarModelMandatory;
    private boolean mCarPictureMandatory;
    private boolean mCarPlateMandatory;
    private String mColorName = null;
    private PointsView mColorPts;
    private int mColorVal = 0;
    private CarpoolNativeManager mCpNm;
    private WazeSettingsView mEtColor;
    private WazeSettingsView mEtLicensePlate;
    private WazeSettingsView mEtMake;
    private WazeSettingsView mEtModel;
    private boolean mHideLicencePlate = true;
    private Bitmap mImageBitmap = null;
    private NativeManager mNm;
    private CarpoolUserData mProfile;
    private boolean mStandalone = false;
    private ArrayList<PointsView> mValidatedPointsViews = new ArrayList(16);
    private boolean mWasEdited = false;
    private View f81r;

    class C13871 implements OnEditorActionListener {
        C13871() throws  {
        }

        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) throws  {
            EditTextUtils.closeKeyboard(CarpoolCarProfileFragment.this.getActivity(), CarpoolCarProfileFragment.this.mEtModel);
            new ColorDialog(CarpoolCarProfileFragment.this.getActivity()).show();
            return true;
        }
    }

    class C13882 implements OnClickListener {
        C13882() throws  {
        }

        public void onClick(View v) throws  {
            new ColorDialog(CarpoolCarProfileFragment.this.getActivity()).show();
        }
    }

    class C13893 implements OnClickListener {
        C13893() throws  {
        }

        public void onClick(View v) throws  {
            ((iTakeCarPicture) CarpoolCarProfileFragment.this.getActivity()).takeCarPicture();
        }
    }

    class C13904 implements OnClickListener {
        C13904() throws  {
        }

        public void onClick(View v) throws  {
            ((IPressNext) CarpoolCarProfileFragment.this.getActivity()).nextPressed();
        }
    }

    class C13925 extends ImageRepositoryListener {
        C13925() throws  {
        }

        public void onImageRetrieved(final Bitmap $r1) throws  {
            CarpoolCarProfileFragment.this.f81r.post(new Runnable() {
                public void run() throws  {
                    CarpoolCarProfileFragment.this.f81r.findViewById(C1283R.id.profileCarPicProgress).setVisibility(8);
                    if ($r1 != null) {
                        ((ImageView) CarpoolCarProfileFragment.this.f81r.findViewById(C1283R.id.profileCarPic)).setImageDrawable(new CircleShaderDrawable($r1, 0));
                        CarpoolCarProfileFragment.this.mImageBitmap = $r1;
                        CarpoolCarProfileFragment.this.f81r.findViewById(C1283R.id.profileCarPicMustAdd).setVisibility(8);
                    }
                }
            });
        }
    }

    public class ColorDialog extends BottomSheet implements Adapter {
        private Paint _borderPaint;
        private Paint _fillPaint;
        private CarColors mCarColors = CarpoolNativeManager.getInstance().configGetCarColorsNTV();
        private Context mCtx;
        private final int mDotSize;

        class C13931 implements Runnable {
            C13931() throws  {
            }

            public void run() throws  {
                EditTextUtils.openKeyboard(CarpoolCarProfileFragment.this.getActivity(), CarpoolCarProfileFragment.this.mEtLicensePlate.getValue());
            }
        }

        public ColorDialog(Context $r2) throws  {
            super($r2, DisplayStrings.DS_CARPOOL_CHOOSE_COLOR_TITLE, Mode.GRID_SMALL);
            super.setAdapter(this);
            this.mCtx = $r2;
            Context $r22 = this.mCtx;
            float $f0 = $r22.getResources().getDisplayMetrics().density;
            this._borderPaint = new Paint();
            this._borderPaint.setColor(855638016);
            this._borderPaint.setAntiAlias(true);
            this._borderPaint.setStyle(Style.STROKE);
            this._borderPaint.setStrokeWidth($f0);
            this._fillPaint = new Paint();
            this._fillPaint.setColor(-1);
            this._fillPaint.setAntiAlias(true);
            this._fillPaint.setStyle(Style.FILL);
            this._fillPaint.setStrokeWidth($f0);
            this._fillPaint.setMaskFilter(new EmbossMaskFilter(new float[]{0.0f, -1.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR}, CanvasFont.OUTLINE_GLYPH_WIDTH_FACTOR, AutoResizeTextView.MIN_TEXT_SIZE, $f0));
            this.mDotSize = PixelMeasure.dp(50);
        }

        public int getCount() throws  {
            return this.mCarColors.colorNames.length;
        }

        public void onConfigItem(int $i0, ItemDetails $r1) throws  {
            Drawable $r7;
            int $i1 = this.mCarColors.colorValues[$i0];
            if (Color.alpha($i1) == 0) {
                $r7 = this.mCtx.getResources().getDrawable(C1283R.drawable.signup_car_colour);
            } else {
                Bitmap $r11 = Bitmap.createBitmap(this.mDotSize, this.mDotSize, Config.ARGB_8888);
                Canvas $r2 = r0;
                Canvas canvas = new Canvas($r11);
                Paint $r12 = this._fillPaint;
                $r12.setColor($i1);
                float $f0 = (float) (this.mDotSize / 2);
                float $f1 = (float) (this.mDotSize / 2);
                float $f2 = (float) ((this.mDotSize / 2) - 1);
                $r2.drawCircle($f0, $f1, $f2, this._fillPaint);
                $r2.drawCircle((float) (this.mDotSize / 2), (float) (this.mDotSize / 2), (float) ((this.mDotSize / 2) - 1), this._borderPaint);
                $r7 = r0;
                Drawable bitmapDrawable = new BitmapDrawable(this.mCtx.getResources(), $r11);
            }
            $r1.setItem(this.mCarColors.colorNames[$i0], $r7);
        }

        public void onClick(int $i0) throws  {
            CarpoolCarProfileFragment.this.mWasEdited = true;
            CarpoolCarProfileFragment.this.setColor(this, this.mCarColors.colorNames[$i0], this.mCarColors.colorValues[$i0]);
            dismiss();
            if (!CarpoolCarProfileFragment.this.mHideLicencePlate && CarpoolCarProfileFragment.this.mEtLicensePlate != null) {
                CarpoolCarProfileFragment.this.mEtLicensePlate.requestFocus();
                CarpoolCarProfileFragment.this.mEtLicensePlate.postDelayed(new C13931(), 200);
            }
        }
    }

    public interface iTakeCarPicture {
        void takeCarPicture() throws ;
    }

    public void setUpFragment() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:33:0x029c
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
        r45 = this;
        r6 = new com.waze.reports.PointsViewTextWatcher$HasContentValidator;
        r7 = r6;
        r6.<init>();
        r0 = r45;
        r8 = r0.mProfile;
        if (r8 != 0) goto L_0x028d;
    L_0x000c:
        r9 = "";
    L_0x000e:
        r0 = r45;
        r10 = r0.mCarMakeMandatory;
        if (r10 == 0) goto L_0x02a0;
    L_0x0014:
        r6 = r7;
    L_0x0015:
        r0 = r45;
        r10 = r0.mCarMakeMandatory;
        r12 = 2131691551; // 0x7f0f081f float:1.9012177E38 double:1.053195563E-314;
        r13 = 1841; // 0x731 float:2.58E-42 double:9.096E-321;
        r0 = r45;
        r1 = r12;
        r2 = r13;
        r3 = r9;
        r4 = r6;
        r5 = r10;
        r11 = r0.setEditTextAndPoints(r1, r2, r3, r4, r5);
        r0 = r45;
        r0.mEtMake = r11;
        r0 = r45;
        r11 = r0.mEtMake;
        r12 = 5;
        r11.setImeOptions(r12);
        r0 = r45;
        r8 = r0.mProfile;
        if (r8 != 0) goto L_0x02a2;
    L_0x003b:
        r9 = "";
    L_0x003d:
        r0 = r45;
        r10 = r0.mCarModelMandatory;
        if (r10 == 0) goto L_0x02b5;
    L_0x0043:
        r6 = r7;
    L_0x0044:
        r0 = r45;
        r10 = r0.mCarModelMandatory;
        r12 = 2131691552; // 0x7f0f0820 float:1.901218E38 double:1.0531955634E-314;
        r13 = 1842; // 0x732 float:2.581E-42 double:9.1E-321;
        r0 = r45;
        r1 = r12;
        r2 = r13;
        r3 = r9;
        r4 = r6;
        r5 = r10;
        r11 = r0.setEditTextAndPoints(r1, r2, r3, r4, r5);
        r0 = r45;
        r0.mEtModel = r11;
        r0 = r45;
        r11 = r0.mEtModel;
        r12 = 5;
        r11.setImeOptions(r12);
        r0 = r45;
        r11 = r0.mEtModel;
        r14 = r11.getValue();
        r16 = r14;
        r16 = (android.widget.EditText) r16;
        r15 = r16;
        r17 = new com.waze.carpool.CarpoolCarProfileFragment$1;
        r0 = r17;
        r1 = r45;
        r0.<init>();
        r0 = r17;
        r15.setOnEditorActionListener(r0);
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691553; // 0x7f0f0821 float:1.9012181E38 double:1.053195564E-314;
        r14 = r14.findViewById(r12);
        r18 = r14;
        r18 = (com.waze.settings.WazeSettingsView) r18;
        r11 = r18;
        r0 = r45;
        r0.mEtColor = r11;
        r0 = r45;
        r11 = r0.mEtColor;
        r19 = r11.getValidation();
        r0 = r19;
        r1 = r45;
        r1.mColorPts = r0;
        r0 = r45;
        r11 = r0.mEtColor;
        r20 = new com.waze.reports.PointsViewTextWatcher;
        r21 = r20;
        r0 = r45;
        r0 = r0.mColorPts;
        r19 = r0;
        r0 = r45;
        r10 = r0.mCarColorMandatory;
        if (r10 == 0) goto L_0x02bb;
    L_0x00b7:
        r6 = r7;
    L_0x00b8:
        r12 = 0;
        r22 = "";
        r0 = r20;
        r1 = r45;
        r2 = r19;
        r3 = r12;
        r4 = r6;
        r5 = r22;
        r0.<init>(r1, r2, r3, r4, r5);
        r24 = r21;
        r24 = (android.text.TextWatcher) r24;
        r23 = r24;
        r0 = r23;
        r11.addTextChangedListener(r0);
        r0 = r45;
        r0 = r0.mNm;
        r25 = r0;
        r12 = 1843; // 0x733 float:2.583E-42 double:9.106E-321;
        r0 = r25;
        r9 = r0.getLanguageString(r12);
        r26 = r9;
        r0 = r45;
        r10 = r0.mCarColorMandatory;
        if (r10 == 0) goto L_0x0106;
    L_0x00e9:
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r0 = r27;
        r28 = r0.append(r9);
        r22 = " <font color=#FF7878>*</font>";
        r0 = r28;
        r1 = r22;
        r28 = r0.append(r1);
        r0 = r28;
        r26 = r0.toString();
    L_0x0106:
        r0 = r45;
        r11 = r0.mEtColor;
        r0 = r26;
        r29 = android.text.Html.fromHtml(r0);
        r0 = r29;
        r11.setKeyText(r0);
        r0 = r45;
        r11 = r0.mEtColor;
        r22 = "";
        r0 = r22;
        r11.setValueText(r0);
        r30 = new com.waze.carpool.CarpoolCarProfileFragment$2;
        r31 = r30;
        r0 = r30;
        r1 = r45;
        r0.<init>();
        r0 = r45;
        r11 = r0.mEtColor;
        r0 = r31;
        r11.setOnClickListener(r0);
        r0 = r45;
        r11 = r0.mEtColor;
        r14 = r11.getValue();
        r12 = 0;
        r14.setClickable(r12);
        r0 = r45;
        r11 = r0.mEtColor;
        r14 = r11.getKey();
        r0 = r31;
        r14.setOnClickListener(r0);
        r0 = r45;
        r11 = r0.mEtColor;
        r14 = r11.getValue();
        r33 = r14;
        r33 = (com.waze.view.text.WazeEditText) r33;
        r32 = r33;
        r0 = r32;
        r1 = r31;
        r0.setMyOnClickListener(r1);
        r0 = r45;
        r11 = r0.mEtColor;
        r14 = r11.getValue();
        r12 = 0;
        r14.setEnabled(r12);
        r0 = r45;
        r10 = r0.mCarPlateMandatory;
        if (r10 != 0) goto L_0x02bd;
    L_0x0174:
        r0 = r45;
        r10 = r0.mHideLicencePlate;
        if (r10 == 0) goto L_0x02bd;
    L_0x017a:
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691554; // 0x7f0f0822 float:1.9012183E38 double:1.0531955644E-314;
        r14 = r14.findViewById(r12);
        r12 = 8;
        r14.setVisibility(r12);
    L_0x018a:
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691544; // 0x7f0f0818 float:1.9012163E38 double:1.0531955594E-314;
        r14 = r14.findViewById(r12);
        r34 = new com.waze.carpool.CarpoolCarProfileFragment$3;
        r0 = r34;
        r1 = r45;
        r0.<init>();
        r0 = r34;
        r14.setOnClickListener(r0);
        r0 = r45;
        r0 = r0.mNm;
        r25 = r0;
        r12 = 1849; // 0x739 float:2.591E-42 double:9.135E-321;
        r0 = r25;
        r9 = r0.getLanguageString(r12);
        r26 = r9;
        r0 = r45;
        r10 = r0.mCarPictureMandatory;
        if (r10 == 0) goto L_0x0208;
    L_0x01b9:
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691546; // 0x7f0f081a float:1.9012167E38 double:1.0531955604E-314;
        r14 = r14.findViewById(r12);
        r36 = r14;
        r36 = (android.widget.ImageView) r36;
        r35 = r36;
        r12 = 2130839220; // 0x7f0206b4 float:1.7283444E38 double:1.0527744554E-314;
        r0 = r35;
        r0.setImageResource(r12);
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691548; // 0x7f0f081c float:1.901217E38 double:1.0531955614E-314;
        r14 = r14.findViewById(r12);
        r37 = r14;
        r37 = (android.widget.ImageView) r37;
        r35 = r37;
        r12 = 2130839263; // 0x7f0206df float:1.7283532E38 double:1.0527744767E-314;
        r0 = r35;
        r0.setImageResource(r12);
        r27 = new java.lang.StringBuilder;
        r0 = r27;
        r0.<init>();
        r0 = r27;
        r28 = r0.append(r9);
        r22 = " <font color=#FF7878>*</font>";
        r0 = r28;
        r1 = r22;
        r28 = r0.append(r1);
        r0 = r28;
        r26 = r0.toString();
    L_0x0208:
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691550; // 0x7f0f081e float:1.9012175E38 double:1.0531955624E-314;
        r14 = r14.findViewById(r12);
        r39 = r14;
        r39 = (android.widget.TextView) r39;
        r38 = r39;
        r0 = r26;
        r29 = android.text.Html.fromHtml(r0);
        r0 = r38;
        r1 = r29;
        r0.setText(r1);
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691542; // 0x7f0f0816 float:1.9012159E38 double:1.0531955584E-314;
        r14 = r14.findViewById(r12);
        r40 = r14;
        r40 = (android.widget.TextView) r40;
        r38 = r40;
        r0 = r45;
        r0 = r0.mNm;
        r25 = r0;
        r12 = 1847; // 0x737 float:2.588E-42 double:9.125E-321;
        r0 = r25;
        r9 = r0.getLanguageString(r12);
        r0 = r38;
        r0.setText(r9);
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691543; // 0x7f0f0817 float:1.901216E38 double:1.053195559E-314;
        r14 = r14.findViewById(r12);
        r41 = r14;
        r41 = (android.widget.TextView) r41;
        r38 = r41;
        r0 = r45;
        r0 = r0.mNm;
        r25 = r0;
        r12 = 1848; // 0x738 float:2.59E-42 double:9.13E-321;
        r0 = r25;
        r9 = r0.getLanguageString(r12);
        r0 = r38;
        r0.setText(r9);
        r0 = r45;
        r14 = r0.f81r;
        r12 = 2131691555; // 0x7f0f0823 float:1.9012185E38 double:1.053195565E-314;
        r14 = r14.findViewById(r12);
        r42 = r14;
        r42 = (android.widget.TextView) r42;
        r38 = r42;
        r0 = r45;
        r10 = r0.mStandalone;
        if (r10 == 0) goto L_0x0306;
    L_0x0285:
        r12 = 8;
        r0 = r38;
        r0.setVisibility(r12);
        return;
    L_0x028d:
        r0 = r45;
        r8 = r0.mProfile;
        r0 = r8.car_info;
        r43 = r0;
        goto L_0x0299;
    L_0x0296:
        goto L_0x000e;
    L_0x0299:
        r9 = r0.make;
        goto L_0x0296;
        goto L_0x02a0;
    L_0x029d:
        goto L_0x0015;
    L_0x02a0:
        r6 = 0;
        goto L_0x029d;
    L_0x02a2:
        r0 = r45;
        r8 = r0.mProfile;
        r0 = r8.car_info;
        r43 = r0;
        goto L_0x02ae;
    L_0x02ab:
        goto L_0x003d;
    L_0x02ae:
        r9 = r0.model;
        goto L_0x02ab;
        goto L_0x02b5;
    L_0x02b2:
        goto L_0x0044;
    L_0x02b5:
        r6 = 0;
        goto L_0x02b2;
        goto L_0x02bb;
    L_0x02b8:
        goto L_0x00b8;
    L_0x02bb:
        r6 = 0;
        goto L_0x02b8;
    L_0x02bd:
        r0 = r45;
        r8 = r0.mProfile;
        if (r8 != 0) goto L_0x02f9;
    L_0x02c3:
        r9 = "";
    L_0x02c5:
        r0 = r45;
        r10 = r0.mCarPlateMandatory;
        if (r10 == 0) goto L_0x0304;
    L_0x02cb:
        r0 = r45;
        r10 = r0.mCarPlateMandatory;
        r12 = 2131691554; // 0x7f0f0822 float:1.9012183E38 double:1.0531955644E-314;
        r13 = 1844; // 0x734 float:2.584E-42 double:9.11E-321;
        r0 = r45;
        r1 = r12;
        r2 = r13;
        r3 = r9;
        r4 = r7;
        r5 = r10;
        r11 = r0.setEditTextAndPoints(r1, r2, r3, r4, r5);
        r0 = r45;
        r0.mEtLicensePlate = r11;
        r0 = r45;
        r11 = r0.mEtLicensePlate;
        r12 = 6;
        r11.setImeOptions(r12);
        r0 = r45;
        r11 = r0.mEtLicensePlate;
        goto L_0x02f3;
    L_0x02f0:
        goto L_0x018a;
    L_0x02f3:
        r12 = 4096; // 0x1000 float:5.74E-42 double:2.0237E-320;
        r11.setEditCapizalized(r12);
        goto L_0x02f0;
    L_0x02f9:
        r0 = r45;
        r8 = r0.mProfile;
        r0 = r8.car_info;
        r43 = r0;
        r9 = r0.license_plate;
        goto L_0x02c5;
    L_0x0304:
        r7 = 0;
        goto L_0x02cb;
    L_0x0306:
        r0 = r45;
        r0 = r0.mNm;
        r25 = r0;
        r12 = 488; // 0x1e8 float:6.84E-43 double:2.41E-321;
        r0 = r25;
        r9 = r0.getLanguageString(r12);
        r0 = r38;
        r0.setText(r9);
        r44 = new com.waze.carpool.CarpoolCarProfileFragment$4;
        r0 = r44;
        r1 = r45;
        r0.<init>();
        r0 = r38;
        r1 = r44;
        r0.setOnClickListener(r1);
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.carpool.CarpoolCarProfileFragment.setUpFragment():void");
    }

    public View onCreateView(LayoutInflater $r1, ViewGroup $r2, Bundle $r3) throws  {
        super.onCreateView($r1, $r2, $r3);
        getActivity().getWindow().setSoftInputMode(3);
        this.mNm = NativeManager.getInstance();
        this.mCpNm = CarpoolNativeManager.getInstance();
        if ($r3 != null) {
            this.mProfile = (CarpoolUserData) $r3.getParcelable(TAG + ".mProfile");
            this.mImageBitmap = (Bitmap) $r3.getParcelable(TAG + ".mImageBitmap");
            this.mColorName = $r3.getString(TAG + ".mColorName");
            this.mColorVal = $r3.getInt(TAG + ".mColorVal", this.mColorVal);
        }
        this.mCarPictureMandatory = this.mCpNm.isCarPictureMandatory();
        this.mCarMakeMandatory = this.mCpNm.isCarMakeMandatory();
        this.mCarModelMandatory = this.mCpNm.isCarModelMandatory();
        this.mCarColorMandatory = this.mCpNm.isCarColorMandatory();
        this.mCarPlateMandatory = this.mCpNm.isCarPlateMandatory();
        ArrayList $r14 = this.mValidatedPointsViews;
        $r14.clear();
        this.f81r = $r1.inflate(C1283R.layout.profile_fragment_car, $r2, false);
        setUpFragment();
        if ($r3 != null) {
            if (this.mImageBitmap != null) {
                ((ImageView) this.f81r.findViewById(C1283R.id.profileCarPic)).setImageDrawable(new CircleShaderDrawable(this.mImageBitmap, 0));
                this.f81r.findViewById(C1283R.id.profileCarPicMustAdd).setVisibility(8);
            }
            if (this.mColorVal != 0) {
                setColor(new ColorDialog(getActivity()), this.mColorName, this.mColorVal);
            }
            String $r10 = $r3.getString(TAG + ".mEtMake", "");
            WazeSettingsView $r18 = this.mEtMake;
            $r18.setValueText($r10);
            $r10 = $r3.getString(TAG + ".mEtModel", "");
            $r18 = this.mEtModel;
            $r18.setValueText($r10);
            if (this.mEtLicensePlate != null) {
                $r10 = $r3.getString(TAG + ".mEtLicensePlate", "");
                $r18 = this.mEtLicensePlate;
                $r18.setValueText($r10);
            }
        } else if (this.mProfile != null) {
            setupFromProfile();
        }
        return this.f81r;
    }

    public void onSaveInstanceState(Bundle $r1) throws  {
        super.onSaveInstanceState($r1);
        $r1.putParcelable(TAG + ".mProfile", this.mProfile);
        $r1.putParcelable(TAG + ".mImageBitmap", this.mImageBitmap);
        $r1.putString(TAG + ".mColorName", this.mColorName);
        $r1.putInt(TAG + ".mColorVal", this.mColorVal);
        $r1.putString(TAG + ".mEtMake", this.mEtMake.getValueText().toString());
        $r1.putString(TAG + ".mEtModel", this.mEtModel.getValueText().toString());
        if (this.mEtLicensePlate != null) {
            $r1.putString(TAG + ".mEtLicensePlate", this.mEtLicensePlate.getValueText().toString());
        }
    }

    private void setupFromProfile() throws  {
        setMake(this.mProfile.car_info.make);
        setModel(this.mProfile.car_info.model);
        setColor(this.mProfile.car_info.color);
        setLicensePlate(this.mProfile.car_info.license_plate);
        setImage(this.mProfile.car_info.photo_url);
        this.mWasEdited = false;
    }

    private WazeSettingsView setEditTextAndPoints(int $i0, int $i1, String $r1, TextValidator $r2, boolean $z0) throws  {
        WazeSettingsView $r4 = (WazeSettingsView) this.f81r.findViewById($i0);
        PointsView $r5 = $r4.getValidation();
        $r4.setValueText($r1);
        $r4.setEditCapizalized(16384);
        $r4.addTextChangedListener(new PointsViewTextWatcher(this, $r5, 0, $r2, $r1));
        $r1 = this.mNm.getLanguageString($i1);
        String $r8 = $r1;
        if ($r2 == null) {
            $r5.setVisibility(8);
        } else {
            $r5.setVisibility(0);
            $r5.setIgnoreFirst(true);
        }
        if ($z0) {
            $r8 = $r1 + CARPOOL_MANDATORY_INDICATOR;
        }
        $r4.setKeyText(Html.fromHtml($r8));
        return $r4;
    }

    public boolean checkIfCarIsGood() throws  {
        boolean $z0 = true;
        ScrollView $r2 = (ScrollView) this.f81r.findViewById(C1283R.id.profileCarScroll);
        if (this.mImageBitmap == null && this.mCarPictureMandatory) {
            View $r1 = this.f81r.findViewById(C1283R.id.profileCarPicMustAdd);
            AnimationUtils.focusOnView($r2, $r1);
            AnimationUtils.flashView($r1);
            $z0 = false;
        }
        this.mEtMake.setValueText(this.mEtMake.getValueText().toString());
        this.mEtModel.setValueText(this.mEtModel.getValueText().toString());
        this.mEtColor.setValueText(this.mEtColor.getValueText().toString());
        if (this.mEtLicensePlate != null) {
            this.mEtLicensePlate.setValueText(this.mEtLicensePlate.getValueText().toString());
        }
        Iterator $r9 = this.mValidatedPointsViews.iterator();
        while ($r9.hasNext()) {
            View $r11 = (PointsView) $r9.next();
            if (!$r11.isValid()) {
                $r11.setIsOn(true, false, false);
                if ($z0) {
                    AnimationUtils.focusOnView($r2, $r11);
                }
                AnimationUtils.flashView($r11);
                $z0 = false;
            }
        }
        return $z0;
    }

    public void clearInvalidFields() throws  {
    }

    public void addValidatedPointsView(PointsView $r1) throws  {
        this.mValidatedPointsViews.add($r1);
    }

    public void updatePoints(int ptsMod) throws  {
    }

    public void onEdit() throws  {
        this.mWasEdited = true;
    }

    public boolean wasEdited() throws  {
        return this.mWasEdited;
    }

    void setImage(Bitmap $r1) throws  {
        this.mImageBitmap = $r1;
        ((ImageView) this.f81r.findViewById(C1283R.id.profileCarPic)).setImageDrawable(new CircleShaderDrawable(this.mImageBitmap, 0));
        this.mImageBitmap = this.mImageBitmap;
        this.mWasEdited = true;
        this.f81r.findViewById(C1283R.id.profileCarPicMustAdd).setVisibility(8);
        this.f81r.findViewById(C1283R.id.profileCarPicProgress).setVisibility(8);
    }

    public void setImage(String $r1) throws  {
        if ($r1 != null && !$r1.isEmpty()) {
            View $r2 = this.f81r.findViewById(C1283R.id.profileCarPicProgress);
            if (!ImageRepository.instance.isCached($r1)) {
                $r2.setVisibility(0);
            }
            ImageRepository.instance.getImage($r1, new C13925());
        }
    }

    public String getMake() throws  {
        return this.mEtMake.getValueText().toString();
    }

    public void setMake(String $r1) throws  {
        if ($r1 == null) {
            this.mEtMake.setValueText("");
        } else {
            this.mEtMake.setValueText($r1);
        }
    }

    public String getModel() throws  {
        return this.mEtModel.getValueText().toString();
    }

    public void setModel(String $r1) throws  {
        if ($r1 == null) {
            this.mEtModel.setValueText("");
        } else {
            this.mEtModel.setValueText($r1);
        }
    }

    public String getColor() throws  {
        return this.mEtColor.getValueText().toString();
    }

    public void setColor(String $r1) throws  {
        CarColors $r3 = CarpoolNativeManager.getInstance().configGetCarColorsNTV();
        for (int $i0 = 0; $i0 < $r3.colorNames.length; $i0++) {
            if ($r3.colorNames[$i0].equalsIgnoreCase($r1)) {
                setColor(new ColorDialog(getActivity()), $r3.colorNames[$i0], $r3.colorValues[$i0]);
            }
        }
    }

    public String getLicensePlate() throws  {
        return this.mEtLicensePlate == null ? null : this.mEtLicensePlate.getValueText().toString();
    }

    public void setLicensePlate(String $r1) throws  {
        if (this.mEtLicensePlate != null) {
            if ($r1 == null) {
                this.mEtLicensePlate.setValueText("");
            } else {
                this.mEtLicensePlate.setValueText($r1);
            }
        }
    }

    private void setColor(ColorDialog colorDialog, String $r2, int $i0) throws  {
        this.mColorVal = $i0;
        this.mColorName = $r2;
        this.mEtColor.setValueText($r2);
    }

    public void setProfile(CarpoolUserData $r1) throws  {
        this.mProfile = $r1;
    }

    public void setHideLicencePlate(boolean $z0) throws  {
        this.mHideLicencePlate = $z0;
    }

    public void setStandalone(boolean $z0) throws  {
        this.mStandalone = $z0;
        if (this.f81r != null) {
            this.f81r.findViewById(C1283R.id.profileCarButton).setVisibility($z0 ? (byte) 8 : (byte) 0);
        }
    }
}
