package com.waze.view.anim;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.support.v4.graphics.ColorUtils;
import android.util.AttributeSet;
import android.view.View;
import com.waze.FriendsBarFragment;
import com.waze.LayoutManager;
import com.waze.map.CanvasFont;
import com.waze.view.popups.BottomSheet;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class ParticleSystem extends View {
    HandlerThread calcThread = new HandlerThread("ParticleSystemCalc", 10);
    Handler calcThreadHandler;
    int f165h;
    long lastDrawTime;
    ArrayList<Particle> particles = new ArrayList(50);
    private Random rand;
    int f166w;

    class C29612 implements Runnable {
        C29612() {
        }

        public void run() {
            ParticleSystem.this.calcTime();
        }
    }

    class C29634 implements Runnable {
        C29634() {
        }

        public void run() {
        }
    }

    abstract class Particle {
        float alpha;
        float ax;
        float ay;
        float az;
        float fade;
        float rx;
        float ry;
        float rz;
        float sx;
        float sy;
        float sz;
        float vx;
        float vy;
        float vz;
        float f160x;
        float f161y;
        float f162z;

        abstract void draw(Canvas canvas);

        Particle(float x, float y, float z, float vx, float vy, float vz, float ax, float ay, float az, float sx, float sy, float sz, float fade) {
            this.f160x = x;
            this.f161y = y;
            this.f162z = z;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.ax = ax;
            this.ay = ay;
            this.az = az;
            this.sx = sx;
            this.sy = sy;
            this.sz = sz;
            this.alpha = 1.0f;
            this.fade = fade;
        }

        Particle(ParticleSystem this$0, float x, float y, float z, float vx, float vy, float vz, float ax, float ay, float az, float fade) {
            this(x, y, z, vx, vy, vz, ax, ay, az, 0.0f, 0.0f, 0.0f, fade);
        }

        Particle(ParticleSystem this$0, float x, float y, float z, float vx, float vy, float vz, float fade) {
            this(x, y, z, vx, vy, vz, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, fade);
        }

        void step(float time) {
            this.f160x += this.vx * time;
            this.f161y += this.vy * time;
            this.f162z += this.vz * time;
            this.vx += this.ax * time;
            this.vy += this.ay * time;
            this.vz += this.az * time;
            this.alpha -= this.fade * time;
            if (this.alpha < 0.0f) {
                this.alpha = 0.0f;
            }
        }

        void setAlpha(float alpha) {
            this.alpha = alpha;
        }
    }

    class DotParticle extends Particle {
        int color;
        float[] hsl;
        Paint f163p;
        float f164r;

        DotParticle(float x, float y, float z, float vx, float vy, float vz, float ax, float ay, float az, float fade, int color, float r) {
            super(x, y, z, vx, vy, vz, ax, ay, az, 0.0f, 0.0f, 0.0f, fade);
            this.hsl = new float[3];
            init(color, r);
        }

        DotParticle(float x, float y, float z, float vx, float vy, float vz, float fade, int color, float r) {
            super(x, y, z, vx, vy, vz, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, fade);
            this.hsl = new float[3];
            init(color, r);
        }

        private void init(int color, float r) {
            this.color = color;
            this.f164r = r;
            ColorUtils.colorToHSL(color, this.hsl);
            float[] fArr = this.hsl;
            fArr[2] = fArr[2] * (1.0f + this.z);
            int zColor = ColorUtils.HSLToColor(this.hsl);
            this.f163p = new Paint();
            this.f163p.setColor(zColor);
            this.f163p.setAlpha((int) (this.alpha * 255.0f));
            this.f163p.setStyle(Style.FILL_AND_STROKE);
            this.f163p.setAntiAlias(true);
        }

        void step(float time) {
            super.step(time);
            ColorUtils.colorToHSL(this.color, this.hsl);
            float[] fArr = this.hsl;
            fArr[2] = fArr[2] * (1.0f + (this.z / LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
            this.f163p.setColor(ColorUtils.HSLToColor(this.hsl));
            this.f163p.setAlpha((int) (this.alpha * 255.0f));
        }

        void setAlpha(float alpha) {
            super.setAlpha(alpha);
            this.f163p.setAlpha((int) (255.0f * alpha));
        }

        void draw(Canvas c) {
            if (this.alpha > 0.0f) {
                c.drawCircle(this.x, this.y, this.f164r * (1.0f + this.z), this.f163p);
            }
        }
    }

    public void emmitDots(final int num, final int color, final float r) {
        this.calcThreadHandler.post(new Runnable() {
            public void run() {
                for (int i = 0; i < num; i++) {
                    double speed = (double) ParticleSystem.this.getRandRange(BottomSheet.DISABLED_ALPHA, 0.6f);
                    double angle = ((double) (ParticleSystem.this.rand.nextFloat() * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN)) * 3.141592653589793d;
                    float vx = (float) (Math.cos(angle) * speed);
                    float vy = (float) (Math.sin(angle) * speed);
                    float vz = ParticleSystem.this.getRandRange(0.0f, CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) / 1000.0f;
                    ParticleSystem.this.particles.add(new DotParticle(0.0f, 0.0f, 0.0f, vx, vy, vz, 0.0f, color, r));
                }
                ParticleSystem.this.lastDrawTime = System.currentTimeMillis();
                ParticleSystem.this.postInvalidate();
            }
        });
    }

    float getRandRange(float from, float to) {
        return ((float) (this.rand.nextBoolean() ? 1 : -1)) * ((this.rand.nextFloat() * (to - from)) + from);
    }

    double getRandGaussianRange(double center) {
        return ((this.rand.nextGaussian() / 3.0d) + FriendsBarFragment.END_LOCATION_POSITION) * center;
    }

    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.f166w = w;
        this.f165h = h;
        if (isInEditMode()) {
            Iterator it = this.particles.iterator();
            while (it.hasNext()) {
                Particle p = (Particle) it.next();
                float r = Math.max(Math.abs(p.f160x / ((float) (w / 2))), Math.abs(p.f161y / ((float) (h / 2))));
                if (r > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
                    p.setAlpha(1.0f - ((r - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        boolean didDraw = false;
        canvas.save();
        canvas.translate((float) (this.f166w / 2), (float) (this.f165h / 2));
        Iterator it = this.particles.iterator();
        while (it.hasNext()) {
            Particle p = (Particle) it.next();
            if (p.alpha > 0.0f) {
                p.draw(canvas);
                didDraw = true;
            }
        }
        canvas.restore();
        if (didDraw) {
            this.calcThreadHandler.post(new C29612());
        }
    }

    private void calcTime() {
        long diff = System.currentTimeMillis() - this.lastDrawTime;
        this.lastDrawTime = System.currentTimeMillis();
        Iterator it = this.particles.iterator();
        while (it.hasNext()) {
            Particle p = (Particle) it.next();
            p.step((float) diff);
            float r = Math.max(Math.abs(p.f160x / ((float) (this.f166w / 2))), Math.abs(p.f161y / ((float) (this.f165h / 2))));
            if (r > CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) {
                p.setAlpha(1.0f - ((r - CanvasFont.OUTLINE_GLYPH_HEIGHT_FACTOR) * LayoutManager.WAZE_LAYOUT_EDIT_SIDE_MARGIN));
            }
        }
        postInvalidate();
    }

    private void init() {
        this.rand = new Random(System.currentTimeMillis());
        this.calcThread.start();
        this.calcThreadHandler = new Handler(this.calcThread.getLooper()) {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
            }
        };
        this.calcThreadHandler.post(new C29634());
        if (isInEditMode()) {
            emmitDots(20, -10053121, 12.0f);
            Iterator it = this.particles.iterator();
            while (it.hasNext()) {
                ((Particle) it.next()).step(500.0f);
            }
        }
    }

    public ParticleSystem(Context context) {
        super(context);
        init();
    }

    public ParticleSystem(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ParticleSystem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(21)
    public ParticleSystem(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.calcThreadHandler = null;
        this.calcThread.quit();
        this.calcThread = null;
    }
}
