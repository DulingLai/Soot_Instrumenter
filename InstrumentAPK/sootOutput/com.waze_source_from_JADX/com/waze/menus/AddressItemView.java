package com.waze.menus;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.waze.C1283R;
import com.waze.MsgBox;
import com.waze.NativeManager;
import com.waze.carpool.CarpoolDrive;
import com.waze.carpool.CarpoolNativeManager.IResultObj;
import com.waze.ifs.ui.CircleShaderDrawable;
import com.waze.navigate.AddressItem;
import com.waze.share.ShareUtility;
import com.waze.strings.DisplayStrings;
import com.waze.utils.ImageRepository.ImageRepositoryListener;
import com.waze.utils.PixelMeasure;
import com.waze.utils.VolleyManager.ImageRequestListener;
import com.waze.view.drawables.MultiContactsBitmap;
import com.waze.view.listitems.ListItemSnapScrollView;
import com.waze.view.listitems.ListItemSnapScrollView.ListItemSnapScrollerListener;

public class AddressItemView extends FrameLayout {
    public static int totalPlannedDrives;
    private RelativeLayout mActionButton;
    private ImageView mActionButtonImage;
    private TextView mAddressItemAddress;
    private LinearLayout mAddressItemContainer;
    private TextView mAddressItemDistance;
    private ImageView mAddressItemImage;
    private ImageView mAddressItemImageUrl;
    private FrameLayout mAddressItemImageUrlFrame;
    private AddressItem mAddressItemModel;
    private TextView mAddressItemName;
    private ListItemSnapScrollView mAddressItemScroller;
    private TextView mAddressItemTouch;
    private TextView mAddressItemUserInitials;
    private FrameLayout mDeleteButton;
    private ImageView mDeleteGradient;
    private boolean mFavorites;
    private boolean mIgnoreRightPadding;
    private boolean mIsAutoClosing;
    private boolean mIsEditing;
    private AddressItemViewListener mListener;
    private FrameLayout mMoreButton;
    private ImageView mMoreGradient;
    private LinearLayout mScrollerContent;

    class C18741 implements OnClickListener {
        C18741() throws  {
        }

        public void onClick(View v) throws  {
            if (AddressItemView.this.mListener != null) {
                AddressItemView.this.mListener.onInfoActionClicked(AddressItemView.this.mAddressItemModel);
            }
            AddressItemView.this.closeSideButtons();
        }
    }

    class C18762 implements OnClickListener {

        class C18751 implements DialogInterface.OnClickListener {
            C18751() throws  {
            }

            public void onClick(DialogInterface dialog, int $i0) throws  {
                if ($i0 == 1) {
                    if (AddressItemView.this.mAddressItemModel != null && AddressItemView.this.mAddressItemModel.getCategory() == null) {
                        AddressItemView.this.mAddressItemModel.setCategory(Integer.valueOf(0));
                    }
                    if (AddressItemView.this.mListener != null) {
                        AddressItemView.this.mListener.onDeleteActionClicked(AddressItemView.this.mAddressItemModel);
                    }
                }
                AddressItemView.this.closeSideButtons();
            }
        }

        C18762() throws  {
        }

        public void onClick(View v) throws  {
            NativeManager $r3 = NativeManager.getInstance();
            MsgBox.openConfirmDialogJavaCallback($r3.getLanguageString((int) DisplayStrings.DS_ARE_YOU_SURE_Q), $r3.getLanguageString(AddressItemView.this.mFavorites ? (short) 980 : (short) 979), false, new C18751(), $r3.getLanguageString((int) DisplayStrings.DS_REMOVE), $r3.getLanguageString(344), -1);
        }
    }

    class C18783 implements ListItemSnapScrollerListener {
        C18783() throws  {
        }

        public void onScrollChanged(int $i0) throws  {
            float $f0 = ((float) $i0) / ((float) (AddressItemView.this.mMoreButton.getMeasuredWidth() + AddressItemView.this.mDeleteButton.getMeasuredWidth()));
            AddressItemView.this.mMoreButton.setPivotX(0.0f);
            AddressItemView.this.mMoreButton.setPivotY((float) (AddressItemView.this.mMoreButton.getMeasuredHeight() / 2));
            AddressItemView.this.mDeleteButton.setPivotX((float) AddressItemView.this.mDeleteButton.getMeasuredWidth());
            AddressItemView.this.mDeleteButton.setPivotY((float) (AddressItemView.this.mDeleteButton.getMeasuredHeight() / 2));
            AddressItemView.this.mMoreButton.setTranslationX((float) $i0);
            AddressItemView.this.mMoreButton.setRotationY(90.0f * $f0);
            AddressItemView.this.mDeleteButton.setRotationY(-90.0f * $f0);
            AddressItemView.this.mMoreGradient.setAlpha(1.0f - ((float) Math.cos((((double) $f0) * 3.141592653589793d) / 2.0d)));
            AddressItemView.this.mDeleteGradient.setAlpha(1.0f - ((float) Math.cos((((double) $f0) * 3.141592653589793d) / 2.0d)));
        }

        public void onScrollEnded(final int $i0) throws  {
            final int $i1 = AddressItemView.this.mMoreButton.getMeasuredWidth() + AddressItemView.this.mDeleteButton.getMeasuredWidth();
            AddressItemView.this.post(new Runnable() {
                public void run() throws  {
                    if ($i0 > 0 && $i0 <= $i1 / 2) {
                        AddressItemView.this.mAddressItemScroller.fullScroll(17);
                    } else if ($i0 < $i1 && $i0 > $i1 / 2) {
                        AddressItemView.this.mAddressItemScroller.fullScroll(66);
                    }
                }
            });
        }

        public void onScrollStarted() throws  {
            if (AddressItemView.this.mListener != null) {
                AddressItemView.this.mListener.onButtonsOpened(AddressItemView.this);
            }
        }
    }

    class C18794 implements Runnable {
        C18794() throws  {
        }

        public void run() throws  {
            AddressItemView.this.mAddressItemScroller.fullScroll(66);
        }
    }

    class C18805 implements Runnable {
        C18805() throws  {
        }

        public void run() throws  {
            AddressItemView.this.mIsAutoClosing = false;
        }
    }

    class C18826 extends ImageRepositoryListener {
        C18826() throws  {
        }

        public void onImageRetrieved(final Bitmap $r1) throws  {
            if ($r1 != null) {
                AddressItemView.this.mAddressItemUserInitials.setVisibility(8);
                AddressItemView.this.post(new Runnable() {
                    public void run() throws  {
                        AddressItemView.this.mAddressItemImageUrl.setImageDrawable(new CircleShaderDrawable($r1, 0));
                    }
                });
            } else if (!TextUtils.isEmpty(AddressItemView.this.mAddressItemModel.getIcon())) {
                AddressItemView.this.mAddressItemUserInitials.setVisibility(0);
                AddressItemView.this.mAddressItemUserInitials.setText(ShareUtility.getInitials(AddressItemView.this.mAddressItemModel.getIcon()));
            }
        }
    }

    class C18847 implements IResultObj<CarpoolDrive> {

        class C18831 extends ImageRepositoryListener {
            C18831() throws  {
            }

            public void onImageRetrieved(Bitmap $r1) throws  {
                AddressItemView.this.mAddressItemImageUrl.setImageDrawable(new CircleShaderDrawable($r1, 0));
            }
        }

        C18847() throws  {
        }

        public void onResult(CarpoolDrive $r1) throws  {
            if ($r1 != null) {
                new MultiContactsBitmap(new C18831(), AddressItemView.this.getResources(), C1283R.drawable.default_avatar).buildBitmap($r1);
            }
        }
    }

    class C18858 implements ImageRequestListener {
        C18858() throws  {
        }

        public void onImageLoadComplete(Bitmap $r1, Object $r2, long duration) throws  {
            if ($r2 == AddressItemView.this.mAddressItemModel) {
                AddressItemView.this.mAddressItemImage.setImageBitmap($r1);
            }
        }

        public void onImageLoadFailed(Object token, long duration) throws  {
        }
    }

    class C18869 implements OnTouchListener {
        C18869() throws  {
        }

        public boolean onTouch(View v, MotionEvent $r2) throws  {
            if ($r2.getAction() == 0) {
                AddressItemView.this.mListener.onGrabStart(AddressItemView.this.mAddressItemModel);
            }
            return false;
        }
    }

    public interface AddressItemViewListener {
        void onButtonsOpened(AddressItemView addressItemView) throws ;

        void onDeleteActionClicked(AddressItem addressItem) throws ;

        void onGrabStart(AddressItem addressItem) throws ;

        void onInfoActionClicked(AddressItem addressItem) throws ;

        void onRefreshed(AddressItem addressItem) throws ;

        void onShowOptionsClick(AddressItem addressItem) throws ;
    }

    private void init() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:14:0x032e in {2, 5, 8, 10, 12, 15} preds:[]
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
        r48 = this;
        r0 = r48;
        r4 = r0.getContext();
        r5 = android.view.LayoutInflater.from(r4);
        r7 = 2130903080; // 0x7f030028 float:1.7412968E38 double:1.0528060064E-314;
        r8 = 0;
        r6 = r5.inflate(r7, r8);
        r7 = 2131689773; // 0x7f0f012d float:1.900857E38 double:1.0531946844E-314;
        r9 = r6.findViewById(r7);
        r11 = r9;
        r11 = (android.widget.ImageView) r11;
        r10 = r11;
        r0 = r48;
        r0.mAddressItemImage = r10;
        r7 = 2131689775; // 0x7f0f012f float:1.9008575E38 double:1.0531946854E-314;
        r9 = r6.findViewById(r7);
        r12 = r9;
        r12 = (android.widget.ImageView) r12;
        r10 = r12;
        r0 = r48;
        r0.mAddressItemImageUrl = r10;
        r7 = 2131689774; // 0x7f0f012e float:1.9008573E38 double:1.053194685E-314;
        r9 = r6.findViewById(r7);
        r14 = r9;
        r14 = (android.widget.FrameLayout) r14;
        r13 = r14;
        r0 = r48;
        r0.mAddressItemImageUrlFrame = r13;
        r7 = 2131689777; // 0x7f0f0131 float:1.9008579E38 double:1.0531946864E-314;
        r9 = r6.findViewById(r7);
        r16 = r9;
        r16 = (android.widget.TextView) r16;
        r15 = r16;
        r0 = r48;
        r0.mAddressItemName = r15;
        r7 = 2131689778; // 0x7f0f0132 float:1.900858E38 double:1.053194687E-314;
        r9 = r6.findViewById(r7);
        r17 = r9;
        r17 = (android.widget.TextView) r17;
        r15 = r17;
        r0 = r48;
        r0.mAddressItemTouch = r15;
        r7 = 2131689779; // 0x7f0f0133 float:1.9008583E38 double:1.0531946874E-314;
        r9 = r6.findViewById(r7);
        r18 = r9;
        r18 = (android.widget.TextView) r18;
        r15 = r18;
        r0 = r48;
        r0.mAddressItemAddress = r15;
        r7 = 2131689781; // 0x7f0f0135 float:1.9008587E38 double:1.0531946884E-314;
        r9 = r6.findViewById(r7);
        r19 = r9;
        r19 = (android.widget.TextView) r19;
        r15 = r19;
        r0 = r48;
        r0.mAddressItemDistance = r15;
        r7 = 2131689819; // 0x7f0f015b float:1.9008664E38 double:1.053194707E-314;
        r9 = r6.findViewById(r7);
        r21 = r9;
        r21 = (android.widget.RelativeLayout) r21;
        r20 = r21;
        r0 = r20;
        r1 = r48;
        r1.mActionButton = r0;
        r7 = 2131689820; // 0x7f0f015c float:1.9008666E38 double:1.0531947077E-314;
        r9 = r6.findViewById(r7);
        r22 = r9;
        r22 = (android.widget.ImageView) r22;
        r10 = r22;
        r0 = r48;
        r0.mActionButtonImage = r10;
        r7 = 2131689476; // 0x7f0f0004 float:1.9007968E38 double:1.0531945377E-314;
        r9 = r6.findViewById(r7);
        r24 = r9;
        r24 = (android.widget.LinearLayout) r24;
        r23 = r24;
        r0 = r23;
        r1 = r48;
        r1.mAddressItemContainer = r0;
        r7 = 2131689811; // 0x7f0f0153 float:1.9008648E38 double:1.053194703E-314;
        r9 = r6.findViewById(r7);
        r26 = r9;
        r26 = (com.waze.view.listitems.ListItemSnapScrollView) r26;
        r25 = r26;
        r0 = r25;
        r1 = r48;
        r1.mAddressItemScroller = r0;
        r7 = 2131689812; // 0x7f0f0154 float:1.900865E38 double:1.0531947037E-314;
        r9 = r6.findViewById(r7);
        r27 = r9;
        r27 = (android.widget.LinearLayout) r27;
        r23 = r27;
        r0 = r23;
        r1 = r48;
        r1.mScrollerContent = r0;
        r7 = 2131689817; // 0x7f0f0159 float:1.900866E38 double:1.053194706E-314;
        r9 = r6.findViewById(r7);
        r28 = r9;
        r28 = (android.widget.TextView) r28;
        r15 = r28;
        r0 = r48;
        r0.mAddressItemUserInitials = r15;
        r7 = 2131689813; // 0x7f0f0155 float:1.9008652E38 double:1.053194704E-314;
        r9 = r6.findViewById(r7);
        r29 = r9;
        r29 = (android.widget.FrameLayout) r29;
        r13 = r29;
        r0 = r48;
        r0.mMoreButton = r13;
        r7 = 2131689815; // 0x7f0f0157 float:1.9008656E38 double:1.053194705E-314;
        r9 = r6.findViewById(r7);
        r30 = r9;
        r30 = (android.widget.FrameLayout) r30;
        r13 = r30;
        r0 = r48;
        r0.mDeleteButton = r13;
        r7 = 2131689814; // 0x7f0f0156 float:1.9008654E38 double:1.0531947047E-314;
        r9 = r6.findViewById(r7);
        r31 = r9;
        r31 = (android.widget.ImageView) r31;
        r10 = r31;
        r0 = r48;
        r0.mMoreGradient = r10;
        r7 = 2131689816; // 0x7f0f0158 float:1.9008658E38 double:1.0531947057E-314;
        r9 = r6.findViewById(r7);
        r32 = r9;
        r32 = (android.widget.ImageView) r32;
        r10 = r32;
        r0 = r48;
        r0.mDeleteGradient = r10;
        r33 = android.os.Build.VERSION.SDK_INT;
        r7 = 21;
        r0 = r33;
        if (r0 >= r7) goto L_0x027b;
    L_0x013d:
        r0 = r48;
        r0 = r0.mAddressItemContainer;
        r23 = r0;
        r0 = r48;
        r34 = r0.getResources();
        r7 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
        r0 = r34;
        r33 = r0.getColor(r7);
        r0 = r23;
        r1 = r33;
        r0.setBackgroundColor(r1);
    L_0x0159:
        r0 = r48;
        r13 = r0.mMoreButton;
        r35 = new com.waze.menus.AddressItemView$1;
        r0 = r35;
        r1 = r48;
        r0.<init>();
        r0 = r35;
        r13.setOnClickListener(r0);
        r0 = r48;
        r13 = r0.mDeleteButton;
        r36 = new com.waze.menus.AddressItemView$2;
        r0 = r36;
        r1 = r48;
        r0.<init>();
        r0 = r36;
        r13.setOnClickListener(r0);
        r0 = r48;
        r0 = r0.mAddressItemScroller;
        r25 = r0;
        r37 = new com.waze.menus.AddressItemView$3;
        r0 = r37;
        r1 = r48;
        r0.<init>();
        r0 = r25;
        r1 = r37;
        r0.setScrollListener(r1);
        r0 = r48;
        r34 = r0.getResources();
        r0 = r34;
        r38 = r0.getDisplayMetrics();
        r0 = r38;
        r0 = r0.widthPixels;
        r33 = r0;
        r0 = r48;
        r0 = r0.mIgnoreRightPadding;
        r39 = r0;
        if (r39 == 0) goto L_0x0332;
    L_0x01ad:
        r40 = 0;
    L_0x01af:
        r40 = r33 - r40;
        r7 = 2131361915; // 0x7f0a007b float:1.8343596E38 double:1.053032701E-314;
        r33 = com.waze.utils.PixelMeasure.dimension(r7);
        r33 = r33 * 2;
        r0 = r48;
        r0 = r0.mScrollerContent;
        r23 = r0;
        r41 = r40 + r33;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r41;
        r41 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r7 = 2131361933; // 0x7f0a008d float:1.8343632E38 double:1.05303271E-314;
        r42 = com.waze.utils.PixelMeasure.dimension(r7);
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r42;
        r42 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r0 = r23;
        r1 = r41;
        r2 = r42;
        r0.measure(r1, r2);
        r0 = r48;
        r0 = r0.mAddressItemScroller;
        r25 = r0;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r40;
        r41 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r7 = 2131361933; // 0x7f0a008d float:1.8343632E38 double:1.05303271E-314;
        r42 = com.waze.utils.PixelMeasure.dimension(r7);
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r42;
        r42 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r0 = r25;
        r1 = r41;
        r2 = r42;
        r0.measure(r1, r2);
        r0 = r48;
        r0 = r0.mAddressItemContainer;
        r23 = r0;
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r40;
        r41 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r7 = 2131361933; // 0x7f0a008d float:1.8343632E38 double:1.05303271E-314;
        r42 = com.waze.utils.PixelMeasure.dimension(r7);
        r7 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r0 = r42;
        r42 = android.view.View.MeasureSpec.makeMeasureSpec(r0, r7);
        r0 = r23;
        r1 = r41;
        r2 = r42;
        r0.measure(r1, r2);
        r0 = r48;
        r0 = r0.mScrollerContent;
        r23 = r0;
        r0 = r40;
        r1 = r33;
        r0 = r0 + r1;
        r40 = r0;
        r7 = 2131361933; // 0x7f0a008d float:1.8343632E38 double:1.05303271E-314;
        r41 = com.waze.utils.PixelMeasure.dimension(r7);
        r7 = 0;
        r43 = 0;
        r0 = r23;
        r1 = r43;
        r2 = r40;
        r3 = r41;
        r0.layout(r7, r1, r2, r3);
        r0 = r48;
        r0 = r0.mAddressItemScroller;
        r25 = r0;
        r7 = 0;
        r0 = r25;
        r1 = r33;
        r0.scrollTo(r1, r7);
        r33 = android.os.Build.VERSION.SDK_INT;
        r7 = 21;
        r0 = r33;
        if (r0 < r7) goto L_0x0275;
    L_0x026c:
        r44 = android.view.ViewOutlineProvider.BOUNDS;
        r0 = r48;
        r1 = r44;
        r0.setOutlineProvider(r1);
    L_0x0275:
        r0 = r48;
        r0.addView(r6);
        return;
    L_0x027b:
        r0 = r48;
        r0 = r0.mAddressItemContainer;
        r23 = r0;
        r45 = new android.graphics.drawable.RippleDrawable;
        r0 = r48;
        r34 = r0.getResources();
        r7 = 2131623943; // 0x7f0e0007 float:1.8875052E38 double:1.05316216E-314;
        r0 = r34;
        r33 = r0.getColor(r7);
        r0 = r33;
        r46 = android.content.res.ColorStateList.valueOf(r0);
        r47 = new android.graphics.drawable.ColorDrawable;
        r0 = r48;
        r34 = r0.getResources();
        r7 = 2131623978; // 0x7f0e002a float:1.8875123E38 double:1.0531621774E-314;
        r0 = r34;
        r33 = r0.getColor(r7);
        r0 = r47;
        r1 = r33;
        r0.<init>(r1);
        r8 = 0;
        r0 = r45;
        r1 = r46;
        r2 = r47;
        r0.<init>(r1, r2, r8);
        r0 = r23;
        r1 = r45;
        r0.setBackground(r1);
        r0 = r48;
        r13 = r0.mMoreButton;
        r45 = new android.graphics.drawable.RippleDrawable;
        r7 = -6377808; // 0xffffffffff9eaeb0 float:NaN double:NaN;
        r46 = android.content.res.ColorStateList.valueOf(r7);
        r47 = new android.graphics.drawable.ColorDrawable;
        r7 = -4272432; // 0xffffffffffbeced0 float:NaN double:NaN;
        r0 = r47;
        r0.<init>(r7);
        r8 = 0;
        r0 = r45;
        r1 = r46;
        r2 = r47;
        r0.<init>(r1, r2, r8);
        r0 = r45;
        r13.setBackground(r0);
        r0 = r48;
        r13 = r0.mDeleteButton;
        r45 = new android.graphics.drawable.RippleDrawable;
        r0 = r48;
        r34 = r0.getResources();
        r7 = 2131623969; // 0x7f0e0021 float:1.8875104E38 double:1.053162173E-314;
        r0 = r34;
        r33 = r0.getColor(r7);
        r0 = r33;
        r46 = android.content.res.ColorStateList.valueOf(r0);
        r47 = new android.graphics.drawable.ColorDrawable;
        r0 = r48;
        r34 = r0.getResources();
        r7 = 2131623969; // 0x7f0e0021 float:1.8875104E38 double:1.053162173E-314;
        r0 = r34;
        r33 = r0.getColor(r7);
        r0 = r47;
        r1 = r33;
        r0.<init>(r1);
        r8 = 0;
        r0 = r45;
        r1 = r46;
        r2 = r47;
        r0.<init>(r1, r2, r8);
        goto L_0x0328;
    L_0x0325:
        goto L_0x0159;
    L_0x0328:
        r0 = r45;
        r13.setBackground(r0);
        goto L_0x0325;
        goto L_0x0332;
    L_0x032f:
        goto L_0x01af;
    L_0x0332:
        r7 = 2131361939; // 0x7f0a0093 float:1.8343644E38 double:1.053032713E-314;
        r40 = com.waze.utils.PixelMeasure.dimension(r7);
        goto L_0x032f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.AddressItemView.init():void");
    }

    private void setFields() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:125:0x037e
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
        r41 = this;
        r0 = r41;
        r2 = r0.mAddressItemModel;
        if (r2 != 0) goto L_0x003d;
    L_0x0006:
        r0 = r41;
        r3 = r0.mAddressItemName;
        r4 = 8;
        r3.setVisibility(r4);
        r0 = r41;
        r3 = r0.mAddressItemTouch;
        r4 = 8;
        r3.setVisibility(r4);
        r0 = r41;
        r3 = r0.mAddressItemAddress;
        r4 = 8;
        r3.setVisibility(r4);
        r0 = r41;
        r3 = r0.mAddressItemDistance;
        r4 = 8;
        r3.setVisibility(r4);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r4 = 8;
        r5.setVisibility(r4);
        r0 = r41;
        r6 = r0.mActionButton;
        r4 = 8;
        r6.setVisibility(r4);
        return;
    L_0x003d:
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.setScaleX(r7);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r7 = 1065353216; // 0x3f800000 float:1.0 double:5.263544247E-315;
        r5.setScaleY(r7);
        r0 = r41;
        r8 = r0.mAddressItemContainer;
        r4 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r8.setPadding(r4, r9, r10, r11);
        r0 = r41;
        r3 = r0.mAddressItemName;
        r4 = 0;
        r3.setVisibility(r4);
        r0 = r41;
        r3 = r0.mAddressItemUserInitials;
        r4 = 8;
        r3.setVisibility(r4);
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r12 = r2.getType();
        r4 = 9;
        if (r12 != r4) goto L_0x022c;
    L_0x0079:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r13 = r2.getCategory();
        r14 = r13.intValue();
        r4 = 9;
        if (r14 != r4) goto L_0x022c;
    L_0x0089:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getStartTime();
        r0 = r41;
        r3 = r0.mAddressItemName;
        r3.setText(r15);
    L_0x0098:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getSecondaryTitle();
        r16 = r15;
        r4 = 16;
        if (r12 != r4) goto L_0x0290;
    L_0x00a6:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r13 = r2.getCategory();
        r14 = r13.intValue();
        r4 = 9;
        if (r14 != r4) goto L_0x0290;
    L_0x00b6:
        r16 = "";
        r14 = totalPlannedDrives;
        r4 = 1;
        if (r14 != r4) goto L_0x0270;
    L_0x00bd:
        r4 = 2066; // 0x812 float:2.895E-42 double:1.0207E-320;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
    L_0x00c3:
        r0 = r16;
        r17 = android.text.TextUtils.isEmpty(r0);
        if (r17 == 0) goto L_0x030d;
    L_0x00cb:
        r0 = r41;
        r3 = r0.mAddressItemTouch;
        r4 = 8;
        r3.setVisibility(r4);
    L_0x00d4:
        r0 = r41;
        r3 = r0.mAddressItemAddress;
        r4 = 8;
        r3.setVisibility(r4);
        r0 = r41;
        r3 = r0.mAddressItemDistance;
        r4 = 8;
        r3.setVisibility(r4);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r18 = 0;
        r0 = r18;
        r5.setTag(r0);
        r0 = r41;
        r5 = r0.mAddressItemImageUrl;
        r18 = 0;
        r0 = r18;
        r5.setTag(r0);
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r13 = r2.getImage();
        r0 = r41;
        r0 = r0.mAddressItemImageUrlFrame;
        r19 = r0;
        r4 = 8;
        r0 = r19;
        r0.setVisibility(r4);
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getId();
        r17 = android.text.TextUtils.isEmpty(r15);
        if (r17 == 0) goto L_0x012d;
    L_0x011f:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getMeetingId();
        r17 = android.text.TextUtils.isEmpty(r15);
        if (r17 != 0) goto L_0x013e;
    L_0x012d:
        r4 = 2;
        if (r12 == r4) goto L_0x013e;
    L_0x0130:
        r4 = 4;
        if (r12 == r4) goto L_0x013e;
    L_0x0133:
        r4 = 12;
        if (r12 == r4) goto L_0x013e;
    L_0x0137:
        r4 = 10;
        if (r12 == r4) goto L_0x013e;
    L_0x013b:
        r4 = 6;
        if (r12 != r4) goto L_0x0382;
    L_0x013e:
        r17 = 0;
    L_0x0140:
        r0 = r41;
        r0 = r0.mAddressItemScroller;
        r20 = r0;
        r4 = 0;
        r0 = r20;
        r0.setScrollEnabled(r4);
        r4 = 13;
        if (r12 != r4) goto L_0x03b3;
    L_0x0150:
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r4 = 8;
        r5.setVisibility(r4);
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.mImageURL;
        r0 = r41;
        r0 = r0.mAddressItemImageUrlFrame;
        r19 = r0;
        r4 = 0;
        r0 = r19;
        r0.setVisibility(r4);
        r0 = r41;
        r5 = r0.mAddressItemImageUrl;
        r4 = 2130838140; // 0x7f02027c float:1.7281254E38 double:1.052773922E-314;
        r5.setImageResource(r4);
        if (r15 == 0) goto L_0x0385;
    L_0x0177:
        r21 = com.waze.utils.ImageRepository.instance;
        r22 = new com.waze.menus.AddressItemView$6;
        r0 = r22;
        r1 = r41;
        r0.<init>();
        r4 = 1;
        r0 = r21;
        r1 = r22;
        r0.getImage(r15, r4, r1);
    L_0x018a:
        r0 = r41;
        r0 = r0.mIsEditing;
        r23 = r0;
        if (r23 == 0) goto L_0x04fc;
    L_0x0192:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r0 = r2.isAddNewFavorite;
        r23 = r0;
        if (r23 != 0) goto L_0x04fc;
    L_0x019c:
        r0 = r41;
        r5 = r0.mActionButtonImage;
        r4 = 0;
        r5.setVisibility(r4);
        r0 = r41;
        r5 = r0.mActionButtonImage;
        r4 = 2130838205; // 0x7f0202bd float:1.7281386E38 double:1.052773954E-314;
        r5.setImageResource(r4);
        r0 = r41;
        r5 = r0.mActionButtonImage;
        r4 = 8;
        r12 = com.waze.utils.PixelMeasure.dp(r4);
        r4 = 8;
        r14 = com.waze.utils.PixelMeasure.dp(r4);
        r4 = 8;
        r24 = com.waze.utils.PixelMeasure.dp(r4);
        r4 = 8;
        r25 = com.waze.utils.PixelMeasure.dp(r4);
        r0 = r24;
        r1 = r25;
        r5.setPadding(r12, r14, r0, r1);
        r0 = r41;
        r6 = r0.mActionButton;
        r18 = 0;
        r0 = r18;
        r6.setOnClickListener(r0);
        r0 = r41;
        r6 = r0.mActionButton;
        r4 = 0;
        r6.setVisibility(r4);
        r0 = r41;
        r6 = r0.mActionButton;
        r26 = new com.waze.menus.AddressItemView$9;
        r0 = r26;
        r1 = r41;
        r0.<init>();
        r0 = r26;
        r6.setOnTouchListener(r0);
    L_0x01f6:
        r0 = r41;
        r27 = r0.getResources();
        r0 = r27;
        r28 = r0.getDisplayMetrics();
        r0 = r28;
        r12 = r0.widthPixels;
        r0 = r41;
        r0 = r0.mIgnoreRightPadding;
        r17 = r0;
        if (r17 == 0) goto L_0x0562;
    L_0x020e:
        r14 = 0;
    L_0x020f:
        r14 = r12 - r14;
        r4 = 2131361933; // 0x7f0a008d float:1.8343632E38 double:1.05303271E-314;
        r12 = com.waze.utils.PixelMeasure.dimension(r4);
        r4 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r14 = android.view.View.MeasureSpec.makeMeasureSpec(r14, r4);
        r4 = 1073741824; // 0x40000000 float:2.0 double:5.304989477E-315;
        r12 = android.view.View.MeasureSpec.makeMeasureSpec(r12, r4);
        r0 = r41;
        r0.measure(r14, r12);
        return;
    L_0x022c:
        r4 = 1;
        if (r12 == r4) goto L_0x0232;
    L_0x022f:
        r4 = 2;
        if (r12 != r4) goto L_0x0244;
    L_0x0232:
        r0 = r41;
        r3 = r0.mAddressItemName;
        r4 = 23;
        r15 = com.waze.strings.DisplayStrings.displayString(r4);
        goto L_0x0240;
    L_0x023d:
        goto L_0x0098;
    L_0x0240:
        r3.setText(r15);
        goto L_0x023d;
    L_0x0244:
        r4 = 3;
        if (r12 == r4) goto L_0x024a;
    L_0x0247:
        r4 = 4;
        if (r12 != r4) goto L_0x025c;
    L_0x024a:
        r0 = r41;
        r3 = r0.mAddressItemName;
        r4 = 24;
        r15 = com.waze.strings.DisplayStrings.displayString(r4);
        goto L_0x0258;
    L_0x0255:
        goto L_0x0098;
    L_0x0258:
        r3.setText(r15);
        goto L_0x0255;
    L_0x025c:
        r0 = r41;
        r3 = r0.mAddressItemName;
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getTitle();
        goto L_0x026c;
    L_0x0269:
        goto L_0x0098;
    L_0x026c:
        r3.setText(r15);
        goto L_0x0269;
    L_0x0270:
        r14 = totalPlannedDrives;
        r4 = 1;
        if (r14 <= r4) goto L_0x00c3;
    L_0x0275:
        r4 = 1;
        r0 = new java.lang.Object[r4];
        r29 = r0;
        r14 = totalPlannedDrives;
        r13 = java.lang.Integer.valueOf(r14);
        r4 = 0;
        r29[r4] = r13;
        goto L_0x0287;
    L_0x0284:
        goto L_0x00c3;
    L_0x0287:
        r4 = 2065; // 0x811 float:2.894E-42 double:1.02E-320;
        r0 = r29;
        r16 = com.waze.strings.DisplayStrings.displayStringF(r4, r0);
        goto L_0x0284;
    L_0x0290:
        r4 = 9;
        if (r12 != r4) goto L_0x02c8;
    L_0x0294:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r13 = r2.getCategory();
        r14 = r13.intValue();
        r4 = 9;
        if (r14 != r4) goto L_0x02c8;
    L_0x02a4:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getAddress();
        r17 = android.text.TextUtils.isEmpty(r15);
        if (r17 == 0) goto L_0x02bf;
    L_0x02b2:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        goto L_0x02ba;
    L_0x02b7:
        goto L_0x00c3;
    L_0x02ba:
        r16 = r2.getTitle();
    L_0x02be:
        goto L_0x02b7;
    L_0x02bf:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r16 = r2.getAddress();
        goto L_0x02be;
    L_0x02c8:
        r4 = 9;
        if (r12 == r4) goto L_0x02d0;
    L_0x02cc:
        r4 = 11;
        if (r12 != r4) goto L_0x02f4;
    L_0x02d0:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getAddress();
        r17 = android.text.TextUtils.isEmpty(r15);
        if (r17 == 0) goto L_0x02eb;
    L_0x02de:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        goto L_0x02e6;
    L_0x02e3:
        goto L_0x00c3;
    L_0x02e6:
        r16 = r2.getSecondaryTitle();
    L_0x02ea:
        goto L_0x02e3;
    L_0x02eb:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r16 = r2.getAddress();
        goto L_0x02ea;
    L_0x02f4:
        r4 = 1;
        if (r12 == r4) goto L_0x0300;
    L_0x02f7:
        r4 = 3;
        if (r12 == r4) goto L_0x0300;
    L_0x02fa:
        r17 = android.text.TextUtils.isEmpty(r15);
        if (r17 == 0) goto L_0x00c3;
    L_0x0300:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        goto L_0x0308;
    L_0x0305:
        goto L_0x00c3;
    L_0x0308:
        r16 = r2.getAddress();
        goto L_0x0305;
    L_0x030d:
        r0 = r41;
        r3 = r0.mAddressItemTouch;
        r4 = 0;
        r3.setVisibility(r4);
        r4 = 2;
        if (r12 == r4) goto L_0x033b;
    L_0x0318:
        r4 = 4;
        if (r12 == r4) goto L_0x033b;
    L_0x031b:
        r4 = 12;
        if (r12 == r4) goto L_0x033b;
    L_0x031f:
        r4 = 16;
        if (r12 == r4) goto L_0x033b;
    L_0x0323:
        r4 = 10;
        if (r12 == r4) goto L_0x033b;
    L_0x0327:
        r4 = 9;
        if (r12 != r4) goto L_0x0369;
    L_0x032b:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r30 = r2.getIsValidate();
        r0 = r30;
        r17 = r0.booleanValue();
        if (r17 != 0) goto L_0x0369;
    L_0x033b:
        r0 = r41;
        r3 = r0.mAddressItemTouch;
        r27 = com.waze.AppService.getAppResources();
        r4 = 2131623954; // 0x7f0e0012 float:1.8875074E38 double:1.0531621655E-314;
        r0 = r27;
        r14 = r0.getColor(r4);
        r3.setTextColor(r14);
        r4 = 2;
        if (r12 == r4) goto L_0x0355;
    L_0x0352:
        r4 = 4;
        if (r12 != r4) goto L_0x035b;
    L_0x0355:
        r4 = 29;
        r16 = com.waze.strings.DisplayStrings.displayString(r4);
    L_0x035b:
        r0 = r41;
        r3 = r0.mAddressItemTouch;
        goto L_0x0363;
    L_0x0360:
        goto L_0x00d4;
    L_0x0363:
        r0 = r16;
        r3.setText(r0);
        goto L_0x0360;
    L_0x0369:
        r0 = r41;
        r3 = r0.mAddressItemTouch;
        r27 = com.waze.AppService.getAppResources();
        r4 = 2131624094; // 0x7f0e009e float:1.8875358E38 double:1.0531622347E-314;
        r0 = r27;
        r14 = r0.getColor(r4);
        r3.setTextColor(r14);
        goto L_0x035b;
        goto L_0x0382;
    L_0x037f:
        goto L_0x0140;
    L_0x0382:
        r17 = 1;
        goto L_0x037f;
    L_0x0385:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getIcon();
        r23 = android.text.TextUtils.isEmpty(r15);
        if (r23 != 0) goto L_0x018a;
    L_0x0393:
        r0 = r41;
        r3 = r0.mAddressItemUserInitials;
        r4 = 0;
        r3.setVisibility(r4);
        r0 = r41;
        r3 = r0.mAddressItemUserInitials;
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getIcon();
        r15 = com.waze.share.ShareUtility.getInitials(r15);
        goto L_0x03af;
    L_0x03ac:
        goto L_0x018a;
    L_0x03af:
        r3.setText(r15);
        goto L_0x03ac;
    L_0x03b3:
        r4 = 14;
        if (r12 == r4) goto L_0x03bb;
    L_0x03b7:
        r4 = 15;
        if (r12 != r4) goto L_0x03f3;
    L_0x03bb:
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r4 = 8;
        r5.setVisibility(r4);
        r0 = r41;
        r0 = r0.mAddressItemImageUrlFrame;
        r19 = r0;
        r4 = 0;
        r0 = r19;
        r0.setVisibility(r4);
        r0 = r41;
        r5 = r0.mAddressItemImageUrl;
        r4 = 2130838054; // 0x7f020226 float:1.728108E38 double:1.0527738793E-314;
        r5.setImageResource(r4);
        r31 = com.waze.carpool.CarpoolNativeManager.getInstance();
        r32 = new com.waze.menus.AddressItemView$7;
        r0 = r32;
        r1 = r41;
        r0.<init>();
        goto L_0x03eb;
    L_0x03e8:
        goto L_0x018a;
    L_0x03eb:
        r0 = r31;
        r1 = r32;
        r0.getUpcomingDrive(r1);
        goto L_0x03e8;
    L_0x03f3:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.mImageURL;
        r23 = android.text.TextUtils.isEmpty(r15);
        if (r23 != 0) goto L_0x042a;
    L_0x03ff:
        r33 = com.waze.utils.VolleyManager.getInstance();
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.mImageURL;
        r34 = new com.waze.menus.AddressItemView$8;
        r0 = r34;
        r1 = r41;
        r0.<init>();
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r0 = r33;
        r1 = r34;
        r0.loadImageFromUrl(r15, r1, r2);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        goto L_0x0425;
    L_0x0422:
        goto L_0x018a;
    L_0x0425:
        r4 = 0;
        r5.setVisibility(r4);
        goto L_0x0422;
    L_0x042a:
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r23 = r2.hasIcon();
        if (r23 == 0) goto L_0x04d4;
    L_0x0434:
        r35 = new java.lang.StringBuilder;
        r0 = r35;
        r0.<init>();
        r0 = r41;
        r2 = r0.mAddressItemModel;
        r15 = r2.getIcon();
        r0 = r35;
        r35 = r0.append(r15);
        r36 = ".png";
        r0 = r35;
        r1 = r36;
        r35 = r0.append(r1);
        r0 = r35;
        r15 = r0.toString();
        r37 = com.waze.ResManager.GetSkinDrawable(r15);
        if (r37 != 0) goto L_0x0487;
    L_0x045f:
        if (r13 == 0) goto L_0x0479;
    L_0x0461:
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r12 = r13.intValue();
        r5.setImageResource(r12);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        goto L_0x0474;
    L_0x0471:
        goto L_0x018a;
    L_0x0474:
        r4 = 0;
        r5.setVisibility(r4);
        goto L_0x0471;
    L_0x0479:
        r0 = r41;
        r5 = r0.mAddressItemImage;
        goto L_0x0481;
    L_0x047e:
        goto L_0x018a;
    L_0x0481:
        r4 = 8;
        r5.setVisibility(r4);
        goto L_0x047e;
    L_0x0487:
        r4 = 32;
        r12 = com.waze.utils.PixelMeasure.dp(r4);
        r0 = r37;
        r14 = r0.getIntrinsicWidth();
        r0 = r37;
        r24 = r0.getIntrinsicHeight();
        r0 = r24;
        r14 = java.lang.Math.max(r14, r0);
        r0 = (float) r12;
        r38 = r0;
        r0 = (float) r14;
        r39 = r0;
        r0 = r38;
        r1 = r39;
        r0 = r0 / r1;
        r38 = r0;
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r0 = r38;
        r5.setScaleX(r0);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r0 = r38;
        r5.setScaleY(r0);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r0 = r37;
        r5.setImageDrawable(r0);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        goto L_0x04cf;
    L_0x04cc:
        goto L_0x018a;
    L_0x04cf:
        r4 = 0;
        r5.setVisibility(r4);
        goto L_0x04cc;
    L_0x04d4:
        if (r13 == 0) goto L_0x04ee;
    L_0x04d6:
        r0 = r41;
        r5 = r0.mAddressItemImage;
        r12 = r13.intValue();
        r5.setImageResource(r12);
        r0 = r41;
        r5 = r0.mAddressItemImage;
        goto L_0x04e9;
    L_0x04e6:
        goto L_0x018a;
    L_0x04e9:
        r4 = 0;
        r5.setVisibility(r4);
        goto L_0x04e6;
    L_0x04ee:
        r0 = r41;
        r5 = r0.mAddressItemImage;
        goto L_0x04f6;
    L_0x04f3:
        goto L_0x018a;
    L_0x04f6:
        r4 = 8;
        r5.setVisibility(r4);
        goto L_0x04f3;
    L_0x04fc:
        if (r17 == 0) goto L_0x0545;
    L_0x04fe:
        r0 = r41;
        r5 = r0.mActionButtonImage;
        r4 = 0;
        r5.setVisibility(r4);
        r0 = r41;
        r5 = r0.mActionButtonImage;
        r4 = 2130838595; // 0x7f020443 float:1.7282177E38 double:1.0527741466E-314;
        r5.setImageResource(r4);
        r0 = r41;
        r5 = r0.mActionButtonImage;
        r4 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r5.setPadding(r4, r9, r10, r11);
        r0 = r41;
        r6 = r0.mActionButton;
        r4 = 0;
        r6.setVisibility(r4);
        r0 = r41;
        r6 = r0.mActionButton;
        r18 = 0;
        r0 = r18;
        r6.setOnTouchListener(r0);
        r0 = r41;
        r6 = r0.mActionButton;
        r40 = new com.waze.menus.AddressItemView$10;
        r0 = r40;
        r1 = r41;
        r0.<init>();
        goto L_0x053f;
    L_0x053c:
        goto L_0x01f6;
    L_0x053f:
        r0 = r40;
        r6.setOnClickListener(r0);
        goto L_0x053c;
    L_0x0545:
        r0 = r41;
        r5 = r0.mActionButtonImage;
        r4 = 8;
        r5.setVisibility(r4);
        r0 = r41;
        r6 = r0.mActionButton;
        goto L_0x0556;
    L_0x0553:
        goto L_0x01f6;
    L_0x0556:
        r18 = 0;
        r0 = r18;
        r6.setOnClickListener(r0);
        goto L_0x0553;
        goto L_0x0562;
    L_0x055f:
        goto L_0x020f;
    L_0x0562:
        r4 = 2131361939; // 0x7f0a0093 float:1.8343644E38 double:1.053032713E-314;
        r14 = com.waze.utils.PixelMeasure.dimension(r4);
        goto L_0x055f;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.menus.AddressItemView.setFields():void");
    }

    public AddressItemView(Context $r1, boolean $z0) throws  {
        super($r1, null, 0);
        this.mFavorites = false;
        this.mIgnoreRightPadding = $z0;
        init();
    }

    public AddressItemView(Context $r1) throws  {
        this($r1, null);
    }

    public AddressItemView(Context $r1, AttributeSet $r2) throws  {
        this($r1, $r2, 0);
    }

    public AddressItemView(Context $r1, AttributeSet $r2, int $i0) throws  {
        super($r1, $r2, $i0);
        this.mFavorites = false;
        init();
    }

    public void setIgnoreRightPadding(boolean $z0) throws  {
        this.mIgnoreRightPadding = $z0;
    }

    public void setIsEditing(boolean $z0) throws  {
        this.mIsEditing = $z0;
        setFields();
    }

    public void setFavorites(boolean $z0) throws  {
        this.mFavorites = $z0;
    }

    public void closeSideButtons() throws  {
        if (!this.mIsAutoClosing) {
            this.mIsAutoClosing = true;
            post(new C18794());
            postDelayed(new C18805(), 100);
        }
    }

    public void snapCloseSideButtons() throws  {
        this.mAddressItemScroller.scrollTo(PixelMeasure.dimension(C1283R.dimen.listItemButtonSize) * 2, 0);
    }

    public void setAddressItem(AddressItem $r1) throws  {
        this.mAddressItemModel = $r1;
        setFields();
    }

    public void setAddressItem(AddressItem $r1, boolean $z0) throws  {
        this.mAddressItemModel = $r1;
        this.mIsEditing = $z0;
        setFields();
    }

    public AddressItem getAddressItem() throws  {
        return this.mAddressItemModel;
    }

    public void setListener(AddressItemViewListener $r1) throws  {
        this.mListener = $r1;
    }

    public View getMainContentView() throws  {
        return this.mAddressItemContainer;
    }

    public void setIcon(int $i0) throws  {
        this.mAddressItemImage.setImageResource($i0);
        this.mAddressItemImage.setVisibility(0);
    }

    public View getInfoButtonIfVisible() throws  {
        if (this.mActionButtonImage.getVisibility() == 0) {
            return this.mActionButtonImage;
        }
        return null;
    }

    protected void onLayout(boolean $z0, int $i0, int $i1, int $i2, int $i3) throws  {
        super.onLayout($z0, $i0, $i1, $i2, $i3);
        this.mScrollerContent.layout(0, 0, (getResources().getDisplayMetrics().widthPixels - (this.mIgnoreRightPadding ? 0 : PixelMeasure.dimension(C1283R.dimen.sideMenuRightPadding))) + (PixelMeasure.dimension(C1283R.dimen.listItemButtonSize) * 2), PixelMeasure.dimension(C1283R.dimen.sideMenuAddressItemHeight));
    }

    protected void onMeasure(int widthMeasureSpec, int $i0) throws  {
        widthMeasureSpec = getResources().getDisplayMetrics().widthPixels - (this.mIgnoreRightPadding ? 0 : PixelMeasure.dimension(C1283R.dimen.sideMenuRightPadding));
        int $i3 = MeasureSpec.makeMeasureSpec(widthMeasureSpec, 1073741824);
        this.mAddressItemScroller.getLayoutParams().width = widthMeasureSpec;
        this.mAddressItemContainer.getLayoutParams().width = widthMeasureSpec;
        int $i1 = PixelMeasure.dimension(C1283R.dimen.listItemButtonSize) * 2;
        this.mScrollerContent.getLayoutParams().width = widthMeasureSpec + $i1;
        this.mAddressItemScroller.scrollTo($i1, 0);
        super.onMeasure($i3, $i0);
        this.mScrollerContent.measure(MeasureSpec.makeMeasureSpec(widthMeasureSpec + $i1, 1073741824), MeasureSpec.makeMeasureSpec(PixelMeasure.dimension(C1283R.dimen.sideMenuAddressItemHeight), 1073741824));
    }
}
