package com.waze.view.anim;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

public class Flip3dAnimation extends Animation {
    private Camera mCamera;
    private final float mCenterX;
    private final float mCenterY;
    private final float mFromDegrees;
    private final float mToDegrees;

    public Flip3dAnimation(float fromDegrees, float toDegrees, float centerX, float centerY) {
        this.mFromDegrees = fromDegrees;
        this.mToDegrees = toDegrees;
        this.mCenterX = centerX;
        this.mCenterY = centerY;
    }

    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        this.mCamera = new Camera();
    }

    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float fromDegrees = this.mFromDegrees;
        float degrees = fromDegrees + ((this.mToDegrees - fromDegrees) * interpolatedTime);
        float centerX = this.mCenterX;
        float centerY = this.mCenterY;
        Camera camera = this.mCamera;
        Matrix matrix = t.getMatrix();
        camera.save();
        camera.rotateY(degrees);
        camera.getMatrix(matrix);
        camera.restore();
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX, centerY);
    }
}
