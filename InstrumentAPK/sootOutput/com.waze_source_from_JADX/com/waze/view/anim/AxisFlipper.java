package com.waze.view.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class AxisFlipper extends Animation {
    private Camera camera;
    private float centerX;
    private float centerY;
    private double fromX;
    private double fromY;
    private double fromZ;
    private double pivotX;
    private double pivotY;
    private double toX;
    private double toY;
    private double toZ;

    public AxisFlipper(double fromX, double toX, double fromY, double toY, double fromZ, double toZ, double pivotX, double pivotY) {
        this.fromX = fromX;
        this.toX = toX;
        this.fromY = fromY;
        this.toY = toY;
        this.fromZ = fromZ;
        this.toZ = toZ;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.centerX = (float) (this.pivotX * ((double) width));
        this.centerY = (float) (this.pivotY * ((double) height));
        this.camera = new Camera();
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        double degreesX = (this.fromX * ((double) (1.0f - interpolatedTime))) + (this.toX * ((double) interpolatedTime));
        double degreesY = (this.fromY * ((double) (1.0f - interpolatedTime))) + (this.toY * ((double) interpolatedTime));
        double degreesZ = (this.fromZ * ((double) (1.0f - interpolatedTime))) + (this.toZ * ((double) interpolatedTime));
        Matrix matrix = t.getMatrix();
        this.camera.save();
        if (degreesX != 0.0d) {
            this.camera.rotateX((float) degreesX);
        }
        if (degreesY != 0.0d) {
            this.camera.rotateX((float) degreesY);
        }
        if (degreesZ != 0.0d) {
            this.camera.rotateX((float) degreesZ);
        }
        this.camera.getMatrix(matrix);
        this.camera.restore();
        matrix.preTranslate(-this.centerX, -this.centerY);
        matrix.postTranslate(this.centerX, this.centerY);
    }
}
