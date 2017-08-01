package com.waze;

import android.graphics.Typeface;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

public class WzTypefaceSpan extends TypefaceSpan {
    private final Typeface mNewType;

    private static void applyTypeFace(android.graphics.Paint r1, android.graphics.Typeface r2) throws  {
        /* JADX: method processing error */
/*
Error: jadx.core.utils.exceptions.DecodeException: Load method exception in method: com.waze.WzTypefaceSpan.applyTypeFace(android.graphics.Paint, android.graphics.Typeface):void
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:116)
	at jadx.core.dex.nodes.ClassNode.load(ClassNode.java:249)
	at jadx.core.ProcessClass.process(ProcessClass.java:34)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:306)
	at jadx.api.JavaClass.decompile(JavaClass.java:62)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:199)
Caused by: jadx.core.utils.exceptions.DecodeException: Unknown instruction: not-int
	at jadx.core.dex.instructions.InsnDecoder.decode(InsnDecoder.java:568)
	at jadx.core.dex.instructions.InsnDecoder.process(InsnDecoder.java:56)
	at jadx.core.dex.nodes.MethodNode.load(MethodNode.java:102)
	... 5 more
*/
        /*
        // Can't load method instructions.
        */
        throw new UnsupportedOperationException("Method not decompiled: com.waze.WzTypefaceSpan.applyTypeFace(android.graphics.Paint, android.graphics.Typeface):void");
    }

    public WzTypefaceSpan(Typeface $r1) throws  {
        super("sans");
        this.mNewType = $r1;
    }

    public WzTypefaceSpan(String $r1, Typeface $r2) throws  {
        super($r1);
        this.mNewType = $r2;
    }

    public void updateDrawState(TextPaint $r1) throws  {
        applyTypeFace($r1, this.mNewType);
    }

    public void updateMeasureState(TextPaint $r1) throws  {
        applyTypeFace($r1, this.mNewType);
    }
}
