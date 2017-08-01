package com.facebook.share.widget;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.C0496R;
import com.facebook.FacebookException;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.Utility;
import com.facebook.share.internal.LikeActionController;
import com.facebook.share.internal.LikeActionController.CreationCallback;
import com.facebook.share.internal.LikeBoxCountView;
import com.facebook.share.internal.LikeBoxCountView.LikeBoxCountViewCaretPosition;
import com.facebook.share.internal.LikeButton;
import dalvik.annotation.Signature;

public class LikeView extends FrameLayout {
    private static final int NO_FOREGROUND_COLOR = -1;
    private AuxiliaryViewPosition auxiliaryViewPosition = AuxiliaryViewPosition.DEFAULT;
    private BroadcastReceiver broadcastReceiver;
    private LinearLayout containerView;
    private LikeActionControllerCreationCallback creationCallback;
    private int edgePadding;
    private boolean explicitlyDisabled;
    private int foregroundColor = -1;
    private HorizontalAlignment horizontalAlignment = HorizontalAlignment.DEFAULT;
    private int internalPadding;
    private LikeActionController likeActionController;
    private LikeBoxCountView likeBoxCountView;
    private LikeButton likeButton;
    private Style likeViewStyle = Style.DEFAULT;
    private String objectId;
    private ObjectType objectType;
    private OnErrorListener onErrorListener;
    private Fragment parentFragment;
    private TextView socialSentenceView;

    class C06301 implements OnClickListener {
        C06301() throws  {
        }

        public void onClick(View v) throws  {
            LikeView.this.toggleLike();
        }
    }

    public enum AuxiliaryViewPosition {
        BOTTOM("bottom", 0),
        INLINE("inline", 1),
        TOP("top", 2);
        
        static AuxiliaryViewPosition DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = BOTTOM;
        }

        static AuxiliaryViewPosition fromInt(int $i0) throws  {
            for (AuxiliaryViewPosition $r1 : values()) {
                if ($r1.getValue() == $i0) {
                    return $r1;
                }
            }
            return null;
        }

        private AuxiliaryViewPosition(@Signature({"(", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "I)V"}) int $i1) throws  {
            this.stringValue = $r2;
            this.intValue = $i1;
        }

        public String toString() throws  {
            return this.stringValue;
        }

        private int getValue() throws  {
            return this.intValue;
        }
    }

    public enum HorizontalAlignment {
        CENTER("center", 0),
        LEFT("left", 1),
        RIGHT("right", 2);
        
        static HorizontalAlignment DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = CENTER;
        }

        static HorizontalAlignment fromInt(int $i0) throws  {
            for (HorizontalAlignment $r1 : values()) {
                if ($r1.getValue() == $i0) {
                    return $r1;
                }
            }
            return null;
        }

        private HorizontalAlignment(@Signature({"(", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "I)V"}) int $i1) throws  {
            this.stringValue = $r2;
            this.intValue = $i1;
        }

        public String toString() throws  {
            return this.stringValue;
        }

        private int getValue() throws  {
            return this.intValue;
        }
    }

    private class LikeActionControllerCreationCallback implements CreationCallback {
        private boolean isCancelled;

        private LikeActionControllerCreationCallback() throws  {
        }

        public void cancel() throws  {
            this.isCancelled = true;
        }

        public void onComplete(LikeActionController $r1, FacebookException $r2) throws  {
            if (!this.isCancelled) {
                if ($r1 != null) {
                    if (!$r1.shouldEnableView()) {
                        $r2 = new FacebookException("Cannot use LikeView. The device may not be supported.");
                    }
                    LikeView.this.associateWithLikeActionController($r1);
                    LikeView.this.updateLikeStateAndLayout();
                }
                if (!($r2 == null || LikeView.this.onErrorListener == null)) {
                    LikeView.this.onErrorListener.onError($r2);
                }
                LikeView.this.creationCallback = null;
            }
        }
    }

    private class LikeControllerBroadcastReceiver extends BroadcastReceiver {
        private LikeControllerBroadcastReceiver() throws  {
        }

        public void onReceive(Context context, Intent $r2) throws  {
            String $r3 = $r2.getAction();
            Bundle $r4 = $r2.getExtras();
            boolean $z0 = true;
            if ($r4 != null) {
                String $r5 = $r4.getString(LikeActionController.ACTION_OBJECT_ID_KEY);
                if (Utility.isNullOrEmpty($r5) || Utility.areObjectsEqual(LikeView.this.objectId, $r5)) {
                    $z0 = true;
                } else {
                    $z0 = false;
                }
            }
            if (!$z0) {
                return;
            }
            if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED.equals($r3)) {
                LikeView.this.updateLikeStateAndLayout();
            } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR.equals($r3)) {
                if (LikeView.this.onErrorListener != null) {
                    LikeView.this.onErrorListener.onError(NativeProtocol.getExceptionFromErrorData($r4));
                }
            } else if (LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET.equals($r3)) {
                LikeView.this.setObjectIdAndTypeForced(LikeView.this.objectId, LikeView.this.objectType);
                LikeView.this.updateLikeStateAndLayout();
            }
        }
    }

    public enum ObjectType {
        UNKNOWN("unknown", 0),
        OPEN_GRAPH(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_OPENGRAPH, 1),
        PAGE("page", 2);
        
        public static ObjectType DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = UNKNOWN;
        }

        public static ObjectType fromInt(int $i0) throws  {
            for (ObjectType $r1 : values()) {
                if ($r1.getValue() == $i0) {
                    return $r1;
                }
            }
            return null;
        }

        private ObjectType(@Signature({"(", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "I)V"}) int $i1) throws  {
            this.stringValue = $r2;
            this.intValue = $i1;
        }

        public String toString() throws  {
            return this.stringValue;
        }

        public int getValue() throws  {
            return this.intValue;
        }
    }

    public interface OnErrorListener {
        void onError(FacebookException facebookException) throws ;
    }

    public enum Style {
        STANDARD("standard", 0),
        BUTTON("button", 1),
        BOX_COUNT("box_count", 2);
        
        static Style DEFAULT;
        private int intValue;
        private String stringValue;

        static {
            DEFAULT = STANDARD;
        }

        static Style fromInt(int $i0) throws  {
            for (Style $r1 : values()) {
                if ($r1.getValue() == $i0) {
                    return $r1;
                }
            }
            return null;
        }

        private Style(@Signature({"(", "Ljava/lang/String;", "I)V"}) String $r2, @Signature({"(", "Ljava/lang/String;", "I)V"}) int $i1) throws  {
            this.stringValue = $r2;
            this.intValue = $i1;
        }

        public String toString() throws  {
            return this.stringValue;
        }

        private int getValue() throws  {
            return this.intValue;
        }
    }

    private void updateLayout() throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Can't find immediate dominator for block B:12:0x0093 in {2, 9, 11, 13, 16, 22, 24, 25, 29, 41, 43, 45, 49, 51, 52, 53, 54} preds:[]
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
        r34 = this;
        r5 = 1;
        r0 = r34;
        r6 = r0.containerView;
        r7 = r6.getLayoutParams();
        r9 = r7;
        r9 = (android.widget.FrameLayout.LayoutParams) r9;
        r8 = r9;
        r0 = r34;
        r10 = r0.likeButton;
        r7 = r10.getLayoutParams();
        r12 = r7;
        r12 = (android.widget.LinearLayout.LayoutParams) r12;
        r11 = r12;
        r0 = r34;
        r13 = r0.horizontalAlignment;
        r14 = com.facebook.share.widget.LikeView.HorizontalAlignment.LEFT;
        if (r13 != r14) goto L_0x00fe;
    L_0x0021:
        r15 = 3;
    L_0x0022:
        r17 = 48;
        r16 = r15 | r17;
        r0 = r16;
        r8.gravity = r0;
        r11.gravity = r15;
        r0 = r34;
        r0 = r0.socialSentenceView;
        r18 = r0;
        r17 = 8;
        r0 = r18;
        r1 = r17;
        r0.setVisibility(r1);
        r0 = r34;
        r0 = r0.likeBoxCountView;
        r19 = r0;
        r17 = 8;
        r0 = r19;
        r1 = r17;
        r0.setVisibility(r1);
        r0 = r34;
        r0 = r0.likeViewStyle;
        r20 = r0;
        r21 = com.facebook.share.widget.LikeView.Style.STANDARD;
        r0 = r20;
        r1 = r21;
        if (r0 != r1) goto L_0x010a;
    L_0x0058:
        r0 = r34;
        r0 = r0.likeActionController;
        r22 = r0;
        if (r22 == 0) goto L_0x010a;
    L_0x0060:
        r0 = r34;
        r0 = r0.likeActionController;
        r22 = r0;
        r23 = r0.getSocialSentence();
        r0 = r23;
        r24 = com.facebook.internal.Utility.isNullOrEmpty(r0);
        if (r24 != 0) goto L_0x010a;
    L_0x0072:
        r0 = r34;
        r0 = r0.socialSentenceView;
        r25 = r0;
    L_0x0078:
        r17 = 0;
        r0 = r25;
        r1 = r17;
        r0.setVisibility(r1);
        r0 = r25;
        r7 = r0.getLayoutParams();
        r26 = r7;
        r26 = (android.widget.LinearLayout.LayoutParams) r26;
        r11 = r26;
        r11.gravity = r15;
        goto L_0x0097;
    L_0x0090:
        goto L_0x0022;
        goto L_0x0097;
    L_0x0094:
        goto L_0x0022;
    L_0x0097:
        r0 = r34;
        r6 = r0.containerView;
        r0 = r34;
        r0 = r0.auxiliaryViewPosition;
        r27 = r0;
        r28 = com.facebook.share.widget.LikeView.AuxiliaryViewPosition.INLINE;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x00aa;
    L_0x00a9:
        r5 = 0;
    L_0x00aa:
        r6.setOrientation(r5);
        r0 = r34;
        r0 = r0.auxiliaryViewPosition;
        r27 = r0;
        r28 = com.facebook.share.widget.LikeView.AuxiliaryViewPosition.TOP;
        r0 = r27;
        r1 = r28;
        if (r0 == r1) goto L_0x00d1;
    L_0x00bb:
        r0 = r34;
        r0 = r0.auxiliaryViewPosition;
        r27 = r0;
        r28 = com.facebook.share.widget.LikeView.AuxiliaryViewPosition.INLINE;
        r0 = r27;
        r1 = r28;
        if (r0 != r1) goto L_0x013e;
    L_0x00c9:
        r0 = r34;
        r13 = r0.horizontalAlignment;
        r14 = com.facebook.share.widget.LikeView.HorizontalAlignment.RIGHT;
        if (r13 != r14) goto L_0x013e;
    L_0x00d1:
        r0 = r34;
        r6 = r0.containerView;
        r0 = r34;
        r10 = r0.likeButton;
        r6.removeView(r10);
        goto L_0x00e0;
    L_0x00dd:
        goto L_0x0078;
    L_0x00e0:
        r0 = r34;
        r6 = r0.containerView;
        r0 = r34;
        r10 = r0.likeButton;
        r6.addView(r10);
    L_0x00eb:
        r29 = com.facebook.share.widget.LikeView.C06312.f24x30689ac;
        r0 = r34;
        r0 = r0.auxiliaryViewPosition;
        r27 = r0;
        r30 = r0.ordinal();
        r30 = r29[r30];
        switch(r30) {
            case 1: goto L_0x0151;
            case 2: goto L_0x0177;
            case 3: goto L_0x019d;
            default: goto L_0x00fc;
        };
    L_0x00fc:
        goto L_0x00fd;
    L_0x00fd:
        return;
    L_0x00fe:
        r0 = r34;
        r13 = r0.horizontalAlignment;
        r14 = com.facebook.share.widget.LikeView.HorizontalAlignment.CENTER;
        if (r13 != r14) goto L_0x0108;
    L_0x0106:
        r15 = 1;
        goto L_0x0090;
    L_0x0108:
        r15 = 5;
        goto L_0x0094;
    L_0x010a:
        r0 = r34;
        r0 = r0.likeViewStyle;
        r20 = r0;
        r21 = com.facebook.share.widget.LikeView.Style.BOX_COUNT;
        r0 = r20;
        r1 = r21;
        if (r0 != r1) goto L_0x01f1;
    L_0x0118:
        r0 = r34;
        r0 = r0.likeActionController;
        r22 = r0;
        if (r22 == 0) goto L_0x01f2;
    L_0x0120:
        r0 = r34;
        r0 = r0.likeActionController;
        r22 = r0;
        r23 = r0.getLikeCountString();
        r0 = r23;
        r24 = com.facebook.internal.Utility.isNullOrEmpty(r0);
        if (r24 != 0) goto L_0x01f3;
    L_0x0132:
        r0 = r34;
        r0.updateBoxCountCaretPosition();
        r0 = r34;
        r0 = r0.likeBoxCountView;
        r25 = r0;
        goto L_0x00dd;
    L_0x013e:
        r0 = r34;
        r6 = r0.containerView;
        r0 = r25;
        r6.removeView(r0);
        r0 = r34;
        r6 = r0.containerView;
        r0 = r25;
        r6.addView(r0);
        goto L_0x00eb;
    L_0x0151:
        r0 = r34;
        r0 = r0.edgePadding;
        r30 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r31 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r32 = r0;
        r0 = r34;
        r0 = r0.internalPadding;
        r33 = r0;
        r0 = r25;
        r1 = r30;
        r2 = r31;
        r3 = r32;
        r4 = r33;
        r0.setPadding(r1, r2, r3, r4);
        return;
    L_0x0177:
        r0 = r34;
        r0 = r0.edgePadding;
        r30 = r0;
        r0 = r34;
        r0 = r0.internalPadding;
        r31 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r32 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r33 = r0;
        r0 = r25;
        r1 = r30;
        r2 = r31;
        r3 = r32;
        r4 = r33;
        r0.setPadding(r1, r2, r3, r4);
        return;
    L_0x019d:
        r0 = r34;
        r13 = r0.horizontalAlignment;
        r14 = com.facebook.share.widget.LikeView.HorizontalAlignment.RIGHT;
        if (r13 != r14) goto L_0x01cb;
    L_0x01a5:
        r0 = r34;
        r0 = r0.edgePadding;
        r30 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r31 = r0;
        r0 = r34;
        r0 = r0.internalPadding;
        r32 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r33 = r0;
        r0 = r25;
        r1 = r30;
        r2 = r31;
        r3 = r32;
        r4 = r33;
        r0.setPadding(r1, r2, r3, r4);
        return;
    L_0x01cb:
        r0 = r34;
        r0 = r0.internalPadding;
        r30 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r31 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r32 = r0;
        r0 = r34;
        r0 = r0.edgePadding;
        r33 = r0;
        r0 = r25;
        r1 = r30;
        r2 = r31;
        r3 = r32;
        r4 = r33;
        r0.setPadding(r1, r2, r3, r4);
        return;
    L_0x01f1:
        return;
    L_0x01f2:
        return;
    L_0x01f3:
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.share.widget.LikeView.updateLayout():void");
    }

    public LikeView(Context $r1) throws  {
        super($r1);
        initialize($r1);
    }

    public LikeView(Context $r1, AttributeSet $r2) throws  {
        super($r1, $r2);
        parseAttributes($r2);
        initialize($r1);
    }

    public void setObjectIdAndType(String $r1, ObjectType $r2) throws  {
        $r1 = Utility.coerceValueIfNullOrEmpty($r1, null);
        if ($r2 == null) {
            $r2 = ObjectType.DEFAULT;
        }
        if (!Utility.areObjectsEqual($r1, this.objectId) || $r2 != this.objectType) {
            setObjectIdAndTypeForced($r1, $r2);
            updateLikeStateAndLayout();
        }
    }

    public void setLikeViewStyle(Style $r2) throws  {
        if ($r2 == null) {
            $r2 = Style.DEFAULT;
        }
        if (this.likeViewStyle != $r2) {
            this.likeViewStyle = $r2;
            updateLayout();
        }
    }

    public void setAuxiliaryViewPosition(AuxiliaryViewPosition $r2) throws  {
        if ($r2 == null) {
            $r2 = AuxiliaryViewPosition.DEFAULT;
        }
        if (this.auxiliaryViewPosition != $r2) {
            this.auxiliaryViewPosition = $r2;
            updateLayout();
        }
    }

    public void setHorizontalAlignment(HorizontalAlignment $r2) throws  {
        if ($r2 == null) {
            $r2 = HorizontalAlignment.DEFAULT;
        }
        if (this.horizontalAlignment != $r2) {
            this.horizontalAlignment = $r2;
            updateLayout();
        }
    }

    public void setForegroundColor(int $i0) throws  {
        if (this.foregroundColor != $i0) {
            this.socialSentenceView.setTextColor($i0);
        }
    }

    public void setOnErrorListener(OnErrorListener $r1) throws  {
        this.onErrorListener = $r1;
    }

    public OnErrorListener getOnErrorListener() throws  {
        return this.onErrorListener;
    }

    public void setFragment(Fragment $r1) throws  {
        this.parentFragment = $r1;
    }

    public void setEnabled(boolean $z0) throws  {
        if ($z0) {
            $z0 = false;
        } else {
            $z0 = true;
        }
        this.explicitlyDisabled = $z0;
        updateLikeStateAndLayout();
    }

    protected void onDetachedFromWindow() throws  {
        setObjectIdAndType(null, ObjectType.UNKNOWN);
        super.onDetachedFromWindow();
    }

    private void parseAttributes(AttributeSet $r1) throws  {
        if ($r1 != null && getContext() != null) {
            TypedArray $r4 = getContext().obtainStyledAttributes($r1, C0496R.styleable.com_facebook_like_view);
            if ($r4 != null) {
                this.objectId = Utility.coerceValueIfNullOrEmpty($r4.getString(C0496R.styleable.com_facebook_like_view_com_facebook_object_id), null);
                this.objectType = ObjectType.fromInt($r4.getInt(C0496R.styleable.com_facebook_like_view_com_facebook_object_type, ObjectType.DEFAULT.getValue()));
                this.likeViewStyle = Style.fromInt($r4.getInt(C0496R.styleable.com_facebook_like_view_com_facebook_style, Style.DEFAULT.getValue()));
                if (this.likeViewStyle == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'style'");
                }
                this.auxiliaryViewPosition = AuxiliaryViewPosition.fromInt($r4.getInt(C0496R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position, AuxiliaryViewPosition.DEFAULT.getValue()));
                if (this.auxiliaryViewPosition == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'auxiliary_view_position'");
                }
                this.horizontalAlignment = HorizontalAlignment.fromInt($r4.getInt(C0496R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment, HorizontalAlignment.DEFAULT.getValue()));
                if (this.horizontalAlignment == null) {
                    throw new IllegalArgumentException("Unsupported value for LikeView 'horizontal_alignment'");
                }
                this.foregroundColor = $r4.getColor(C0496R.styleable.com_facebook_like_view_com_facebook_foreground_color, -1);
                $r4.recycle();
            }
        }
    }

    private void initialize(Context $r1) throws  {
        this.edgePadding = getResources().getDimensionPixelSize(C0496R.dimen.com_facebook_likeview_edge_padding);
        this.internalPadding = getResources().getDimensionPixelSize(C0496R.dimen.com_facebook_likeview_internal_padding);
        if (this.foregroundColor == -1) {
            this.foregroundColor = getResources().getColor(C0496R.color.com_facebook_likeview_text_color);
        }
        setBackgroundColor(0);
        this.containerView = new LinearLayout($r1);
        this.containerView.setLayoutParams(new LayoutParams(-2, -2));
        initializeLikeButton($r1);
        initializeSocialSentenceView($r1);
        initializeLikeCountView($r1);
        this.containerView.addView(this.likeButton);
        this.containerView.addView(this.socialSentenceView);
        this.containerView.addView(this.likeBoxCountView);
        addView(this.containerView);
        setObjectIdAndTypeForced(this.objectId, this.objectType);
        updateLikeStateAndLayout();
    }

    private void initializeLikeButton(Context $r1) throws  {
        boolean $z0 = this.likeActionController != null && this.likeActionController.isObjectLiked();
        this.likeButton = new LikeButton($r1, $z0);
        this.likeButton.setOnClickListener(new C06301());
        this.likeButton.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    }

    private void initializeSocialSentenceView(Context $r1) throws  {
        this.socialSentenceView = new TextView($r1);
        this.socialSentenceView.setTextSize(0, getResources().getDimension(C0496R.dimen.com_facebook_likeview_text_size));
        this.socialSentenceView.setMaxLines(2);
        this.socialSentenceView.setTextColor(this.foregroundColor);
        this.socialSentenceView.setGravity(17);
        this.socialSentenceView.setLayoutParams(new LinearLayout.LayoutParams(-2, -1));
    }

    private void initializeLikeCountView(Context $r1) throws  {
        this.likeBoxCountView = new LikeBoxCountView($r1);
        this.likeBoxCountView.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
    }

    private void toggleLike() throws  {
        if (this.likeActionController != null) {
            Activity $r3 = null;
            if (this.parentFragment == null) {
                $r3 = getActivity();
            }
            this.likeActionController.toggleLike($r3, this.parentFragment, getAnalyticsParameters());
        }
    }

    private Activity getActivity() throws  {
        Context $r1 = getContext();
        while (!($r1 instanceof Activity) && ($r1 instanceof ContextWrapper)) {
            $r1 = ((ContextWrapper) $r1).getBaseContext();
        }
        if ($r1 instanceof Activity) {
            return (Activity) $r1;
        }
        throw new FacebookException("Unable to get Activity.");
    }

    private Bundle getAnalyticsParameters() throws  {
        Bundle $r1 = new Bundle();
        $r1.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_STYLE, this.likeViewStyle.toString());
        $r1.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_AUXILIARY_POSITION, this.auxiliaryViewPosition.toString());
        $r1.putString(AnalyticsEvents.PARAMETER_LIKE_VIEW_HORIZONTAL_ALIGNMENT, this.horizontalAlignment.toString());
        $r1.putString("object_id", Utility.coerceValueIfNullOrEmpty(this.objectId, ""));
        $r1.putString("object_type", this.objectType.toString());
        return $r1;
    }

    private void setObjectIdAndTypeForced(String $r1, ObjectType $r2) throws  {
        tearDownObjectAssociations();
        this.objectId = $r1;
        this.objectType = $r2;
        if (!Utility.isNullOrEmpty($r1)) {
            this.creationCallback = new LikeActionControllerCreationCallback();
            if (!isInEditMode()) {
                LikeActionController.getControllerForObjectId($r1, $r2, this.creationCallback);
            }
        }
    }

    private void associateWithLikeActionController(LikeActionController $r1) throws  {
        this.likeActionController = $r1;
        this.broadcastReceiver = new LikeControllerBroadcastReceiver();
        LocalBroadcastManager $r5 = LocalBroadcastManager.getInstance(getContext());
        IntentFilter $r2 = new IntentFilter();
        $r2.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_UPDATED);
        $r2.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_ERROR);
        $r2.addAction(LikeActionController.ACTION_LIKE_ACTION_CONTROLLER_DID_RESET);
        $r5.registerReceiver(this.broadcastReceiver, $r2);
    }

    private void tearDownObjectAssociations() throws  {
        if (this.broadcastReceiver != null) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(this.broadcastReceiver);
            this.broadcastReceiver = null;
        }
        if (this.creationCallback != null) {
            this.creationCallback.cancel();
            this.creationCallback = null;
        }
        this.likeActionController = null;
    }

    private void updateLikeStateAndLayout() throws  {
        boolean $z0 = !this.explicitlyDisabled;
        if (this.likeActionController == null) {
            this.likeButton.setSelected(false);
            this.socialSentenceView.setText(null);
            this.likeBoxCountView.setText(null);
        } else {
            this.likeButton.setSelected(this.likeActionController.isObjectLiked());
            this.socialSentenceView.setText(this.likeActionController.getSocialSentence());
            this.likeBoxCountView.setText(this.likeActionController.getLikeCountString());
            $z0 &= this.likeActionController.shouldEnableView();
        }
        super.setEnabled($z0);
        this.likeButton.setEnabled($z0);
        updateLayout();
    }

    private void updateBoxCountCaretPosition() throws  {
        switch (this.auxiliaryViewPosition) {
            case TOP:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountViewCaretPosition.BOTTOM);
                return;
            case BOTTOM:
                this.likeBoxCountView.setCaretPosition(LikeBoxCountViewCaretPosition.TOP);
                return;
            case INLINE:
                this.likeBoxCountView.setCaretPosition(this.horizontalAlignment == HorizontalAlignment.RIGHT ? LikeBoxCountViewCaretPosition.RIGHT : LikeBoxCountViewCaretPosition.LEFT);
                return;
            default:
                return;
        }
    }
}
