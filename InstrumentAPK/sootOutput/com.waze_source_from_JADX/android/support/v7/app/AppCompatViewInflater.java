package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.C0192R;
import android.support.v7.view.ContextThemeWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import android.view.View.OnClickListener;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
    private static final Map<String, Constructor<? extends View>> sConstructorMap = new ArrayMap();
    private static final Class<?>[] sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
    private static final int[] sOnClickAttrs = new int[]{16843375};
    private final Object[] mConstructorArgs = new Object[2];

    private static class DeclaredOnClickListener implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(@NonNull View $r1, @NonNull String $r2) throws  {
            this.mHostView = $r1;
            this.mMethodName = $r2;
        }

        public void onClick(@NonNull View $r1) throws  {
            if (this.mResolvedMethod == null) {
                resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, new Object[]{$r1});
            } catch (IllegalAccessException $r7) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", $r7);
            } catch (InvocationTargetException $r9) {
                throw new IllegalStateException("Could not execute method for android:onClick", $r9);
            }
        }

        @NonNull
        private void resolveMethod(@Nullable Context $r2, @NonNull String name) throws  {
            while ($r2 != null) {
                try {
                    if (!$r2.isRestricted()) {
                        Class $r3 = $r2.getClass();
                        name = this.mMethodName;
                        Class[] $r4 = new Class[1];
                        $r4[0] = View.class;
                        Method $r5 = $r3.getMethod(name, $r4);
                        if ($r5 != null) {
                            this.mResolvedMethod = $r5;
                            this.mResolvedContext = $r2;
                            return;
                        }
                    }
                } catch (NoSuchMethodException e) {
                }
                if ($r2 instanceof ContextWrapper) {
                    $r2 = ((ContextWrapper) $r2).getBaseContext();
                } else {
                    $r2 = null;
                }
            }
            int $i0 = this.mHostView.getId();
            if ($i0 == -1) {
                name = "";
            } else {
                name = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName($i0) + "'";
            }
            StringBuilder $r10 = new StringBuilder().append("Could not find method ");
            String $r11 = this.mMethodName;
            throw new IllegalStateException($r10.append($r11).append("(View) in a parent or ancestor Context for android:onClick ").append("attribute defined on view ").append(this.mHostView.getClass()).append(name).toString());
        }
    }

    public final android.view.View createView(android.view.View r22, java.lang.String r23, @android.support.annotation.NonNull android.content.Context r24, @android.support.annotation.NonNull android.util.AttributeSet r25, boolean r26, boolean r27, boolean r28, boolean r29) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.JadxRuntimeException: Unreachable block: B:36:0x0083
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
        r21 = this;
        r4 = r24;
        if (r26 == 0) goto L_0x000c;
    L_0x0004:
        if (r22 == 0) goto L_0x000c;
    L_0x0006:
        r0 = r22;
        r24 = r0.getContext();
    L_0x000c:
        if (r27 != 0) goto L_0x0010;
    L_0x000e:
        if (r28 == 0) goto L_0x001c;
    L_0x0010:
        r0 = r24;
        r1 = r25;
        r2 = r27;
        r3 = r28;
        r24 = themifyContext(r0, r1, r2, r3);
    L_0x001c:
        if (r29 == 0) goto L_0x0024;
    L_0x001e:
        r0 = r24;
        r24 = android.support.v7.widget.TintContextWrapper.wrap(r0);
    L_0x0024:
        r22 = 0;
        r5 = -1;
        r0 = r23;
        r6 = r0.hashCode();
        switch(r6) {
            case -1946472170: goto L_0x00ff;
            case -1455429095: goto L_0x00d8;
            case -1346021293: goto L_0x00f2;
            case -938935918: goto L_0x0053;
            case -937446323: goto L_0x00b4;
            case -658531749: goto L_0x010c;
            case -339785223: goto L_0x00a3;
            case 776382189: goto L_0x00cc;
            case 1125864064: goto L_0x005f;
            case 1413872058: goto L_0x00e5;
            case 1601505219: goto L_0x00c0;
            case 1666676343: goto L_0x008b;
            case 2001146706: goto L_0x006b;
            default: goto L_0x0030;
        };
    L_0x0030:
        goto L_0x0031;
    L_0x0031:
        switch(r5) {
            case 0: goto L_0x0119;
            case 1: goto L_0x0125;
            case 2: goto L_0x0135;
            case 3: goto L_0x0145;
            case 4: goto L_0x0155;
            case 5: goto L_0x0165;
            case 6: goto L_0x0175;
            case 7: goto L_0x0185;
            case 8: goto L_0x0195;
            case 9: goto L_0x01a7;
            case 10: goto L_0x01b9;
            case 11: goto L_0x01cb;
            case 12: goto L_0x01dd;
            default: goto L_0x0034;
        };
    L_0x0034:
        goto L_0x0035;
    L_0x0035:
        if (r22 != 0) goto L_0x0047;
    L_0x0037:
        r0 = r24;
        if (r4 == r0) goto L_0x0047;
    L_0x003b:
        r0 = r21;
        r1 = r24;
        r2 = r23;
        r3 = r25;
        r22 = r0.createViewFromTag(r1, r2, r3);
    L_0x0047:
        if (r22 == 0) goto L_0x01ef;
    L_0x0049:
        r0 = r21;
        r1 = r22;
        r2 = r25;
        r0.checkOnClickListener(r1, r2);
        return r22;
    L_0x0053:
        r7 = "TextView";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x005d:
        r5 = 0;
        goto L_0x0031;
    L_0x005f:
        r7 = "ImageView";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x0069:
        r5 = 1;
        goto L_0x0031;
    L_0x006b:
        r7 = "Button";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x0075:
        goto L_0x0079;
    L_0x0076:
        goto L_0x0031;
    L_0x0079:
        r5 = 2;
        goto L_0x007e;
    L_0x007b:
        goto L_0x0031;
    L_0x007e:
        goto L_0x0082;
    L_0x007f:
        goto L_0x0031;
    L_0x0082:
        goto L_0x0031;
        goto L_0x008b;
    L_0x0084:
        goto L_0x0031;
        goto L_0x008b;
    L_0x0088:
        goto L_0x0031;
    L_0x008b:
        r7 = "EditText";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x0095:
        r5 = 3;
        goto L_0x009a;
    L_0x0097:
        goto L_0x0031;
    L_0x009a:
        goto L_0x0031;
        goto L_0x00a3;
    L_0x009c:
        goto L_0x0031;
        goto L_0x00a3;
    L_0x00a0:
        goto L_0x0031;
    L_0x00a3:
        r7 = "Spinner";
        r0 = r23;
        r26 = r0.equals(r7);
        goto L_0x00af;
    L_0x00ac:
        goto L_0x0035;
    L_0x00af:
        if (r26 == 0) goto L_0x0031;
    L_0x00b1:
        r5 = 4;
        goto L_0x0031;
    L_0x00b4:
        r7 = "ImageButton";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x00be:
        r5 = 5;
        goto L_0x0076;
    L_0x00c0:
        r7 = "CheckBox";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x00ca:
        r5 = 6;
        goto L_0x007b;
    L_0x00cc:
        r7 = "RadioButton";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x00d6:
        r5 = 7;
        goto L_0x007f;
    L_0x00d8:
        r7 = "CheckedTextView";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x00e2:
        r5 = 8;
        goto L_0x0084;
    L_0x00e5:
        r7 = "AutoCompleteTextView";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x00ef:
        r5 = 9;
        goto L_0x0088;
    L_0x00f2:
        r7 = "MultiAutoCompleteTextView";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x00fc:
        r5 = 10;
        goto L_0x0097;
    L_0x00ff:
        r7 = "RatingBar";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x0109:
        r5 = 11;
        goto L_0x009c;
    L_0x010c:
        r7 = "SeekBar";
        r0 = r23;
        r26 = r0.equals(r7);
        if (r26 == 0) goto L_0x0031;
    L_0x0116:
        r5 = 12;
        goto L_0x00a0;
    L_0x0119:
        r8 = new android.support.v7.widget.AppCompatTextView;
        r22 = r8;
        r0 = r24;
        r1 = r25;
        r8.<init>(r0, r1);
        goto L_0x00ac;
    L_0x0125:
        r9 = new android.support.v7.widget.AppCompatImageView;
        r22 = r9;
        goto L_0x012d;
    L_0x012a:
        goto L_0x0035;
    L_0x012d:
        r0 = r24;
        r1 = r25;
        r9.<init>(r0, r1);
        goto L_0x012a;
    L_0x0135:
        r10 = new android.support.v7.widget.AppCompatButton;
        r22 = r10;
        goto L_0x013d;
    L_0x013a:
        goto L_0x0035;
    L_0x013d:
        r0 = r24;
        r1 = r25;
        r10.<init>(r0, r1);
        goto L_0x013a;
    L_0x0145:
        r11 = new android.support.v7.widget.AppCompatEditText;
        r22 = r11;
        goto L_0x014d;
    L_0x014a:
        goto L_0x0035;
    L_0x014d:
        r0 = r24;
        r1 = r25;
        r11.<init>(r0, r1);
        goto L_0x014a;
    L_0x0155:
        r12 = new android.support.v7.widget.AppCompatSpinner;
        r22 = r12;
        goto L_0x015d;
    L_0x015a:
        goto L_0x0035;
    L_0x015d:
        r0 = r24;
        r1 = r25;
        r12.<init>(r0, r1);
        goto L_0x015a;
    L_0x0165:
        r13 = new android.support.v7.widget.AppCompatImageButton;
        r22 = r13;
        goto L_0x016d;
    L_0x016a:
        goto L_0x0035;
    L_0x016d:
        r0 = r24;
        r1 = r25;
        r13.<init>(r0, r1);
        goto L_0x016a;
    L_0x0175:
        r14 = new android.support.v7.widget.AppCompatCheckBox;
        r22 = r14;
        goto L_0x017d;
    L_0x017a:
        goto L_0x0035;
    L_0x017d:
        r0 = r24;
        r1 = r25;
        r14.<init>(r0, r1);
        goto L_0x017a;
    L_0x0185:
        r15 = new android.support.v7.widget.AppCompatRadioButton;
        r22 = r15;
        goto L_0x018d;
    L_0x018a:
        goto L_0x0035;
    L_0x018d:
        r0 = r24;
        r1 = r25;
        r15.<init>(r0, r1);
        goto L_0x018a;
    L_0x0195:
        r16 = new android.support.v7.widget.AppCompatCheckedTextView;
        r22 = r16;
        goto L_0x019d;
    L_0x019a:
        goto L_0x0035;
    L_0x019d:
        r0 = r16;
        r1 = r24;
        r2 = r25;
        r0.<init>(r1, r2);
        goto L_0x019a;
    L_0x01a7:
        r17 = new android.support.v7.widget.AppCompatAutoCompleteTextView;
        r22 = r17;
        goto L_0x01af;
    L_0x01ac:
        goto L_0x0035;
    L_0x01af:
        r0 = r17;
        r1 = r24;
        r2 = r25;
        r0.<init>(r1, r2);
        goto L_0x01ac;
    L_0x01b9:
        r18 = new android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
        r22 = r18;
        goto L_0x01c1;
    L_0x01be:
        goto L_0x0035;
    L_0x01c1:
        r0 = r18;
        r1 = r24;
        r2 = r25;
        r0.<init>(r1, r2);
        goto L_0x01be;
    L_0x01cb:
        r19 = new android.support.v7.widget.AppCompatRatingBar;
        r22 = r19;
        goto L_0x01d3;
    L_0x01d0:
        goto L_0x0035;
    L_0x01d3:
        r0 = r19;
        r1 = r24;
        r2 = r25;
        r0.<init>(r1, r2);
        goto L_0x01d0;
    L_0x01dd:
        r20 = new android.support.v7.widget.AppCompatSeekBar;
        r22 = r20;
        goto L_0x01e5;
    L_0x01e2:
        goto L_0x0035;
    L_0x01e5:
        r0 = r20;
        r1 = r24;
        r2 = r25;
        r0.<init>(r1, r2);
        goto L_0x01e2;
    L_0x01ef:
        return r22;
        */
        throw new UnsupportedOperationException("Method not decompiled: android.support.v7.app.AppCompatViewInflater.createView(android.view.View, java.lang.String, android.content.Context, android.util.AttributeSet, boolean, boolean, boolean, boolean):android.view.View");
    }

    AppCompatViewInflater() throws  {
    }

    private View createViewFromTag(Context $r1, String $r4, AttributeSet $r2) throws  {
        if ($r4.equals("view")) {
            $r4 = $r2.getAttributeValue(null, "class");
        }
        this.mConstructorArgs[0] = $r1;
        this.mConstructorArgs[1] = $r2;
        View $r8;
        if (-1 == $r4.indexOf(46)) {
            try {
                int $i0 = 0;
                while ($i0 < sClassPrefixList.length) {
                    try {
                        $r8 = createView($r1, $r4, sClassPrefixList[$i0]);
                        if ($r8 != null) {
                            return $r8;
                        }
                        $i0++;
                    } catch (Exception e) {
                        this.mConstructorArgs[0] = null;
                        this.mConstructorArgs[1] = null;
                        return null;
                    }
                }
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return null;
            } finally {
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
            }
        } else {
            $r8 = createView($r1, $r4, null);
            this.mConstructorArgs[0] = null;
            this.mConstructorArgs[1] = null;
            return $r8;
        }
    }

    private void checkOnClickListener(View $r1, AttributeSet $r2) throws  {
        Context $r3 = $r1.getContext();
        if (!($r3 instanceof ContextWrapper)) {
            return;
        }
        if (VERSION.SDK_INT < 15 || ViewCompat.hasOnClickListeners($r1)) {
            TypedArray $r5 = $r3.obtainStyledAttributes($r2, sOnClickAttrs);
            String $r6 = $r5.getString(0);
            if ($r6 != null) {
                $r1.setOnClickListener(new DeclaredOnClickListener($r1, $r6));
            }
            $r5.recycle();
        }
    }

    private View createView(Context $r1, String $r2, String $r3) throws ClassNotFoundException, InflateException {
        Constructor $r7 = (Constructor) sConstructorMap.get($r2);
        if ($r7 == null) {
            try {
                Constructor $r12 = $r1.getClassLoader().loadClass($r3 != null ? $r3 + $r2 : $r2).asSubclass(View.class).getConstructor(sConstructorSignature);
                $r7 = $r12;
                sConstructorMap.put($r2, $r12);
            } catch (Exception e) {
                return null;
            }
        }
        $r7.setAccessible(true);
        return (View) $r7.newInstance(this.mConstructorArgs);
    }

    private static Context themifyContext(Context $r2, AttributeSet $r0, boolean $z0, boolean $z1) throws  {
        TypedArray $r4 = $r2.obtainStyledAttributes($r0, C0192R.styleable.View, 0, 0);
        int $i0 = 0;
        if ($z0) {
            $i0 = $r4.getResourceId(C0192R.styleable.View_android_theme, 0);
        }
        if ($z1 && $i0 == 0) {
            int $i1 = $r4.getResourceId(C0192R.styleable.View_theme, 0);
            $i0 = $i1;
            if ($i1 != 0) {
                Log.i(LOG_TAG, "app:theme is now deprecated. Please move to using android:theme instead.");
            }
        }
        $r4.recycle();
        if ($i0 == 0) {
            return $r2;
        }
        if (($r2 instanceof ContextThemeWrapper) && ((ContextThemeWrapper) $r2).getThemeResId() == $i0) {
            return $r2;
        }
        return new ContextThemeWrapper($r2, $i0);
    }
}
