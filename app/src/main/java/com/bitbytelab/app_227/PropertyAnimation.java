package com.bitbytelab.app_227;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

public class PropertyAnimation extends View {

    final int AnimationDuration = 4000;
    final int AnimationDelay = 1000;
    final int ColorAdjust = 5;
    Paint paint;

    public PropertyAnimation(Context context) {
        super(context);
    }

    public PropertyAnimation(Context context, @Nullable AttributeSet attrs){
        super(context,attrs);
        paint = new Paint();
        //AnimationSet  animationSet = new AnimationSet();
    }

//    void setRadius(){
//
//    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh){
        super.onSizeChanged(w,h,oldw,oldh);

        ObjectAnimator growAnimator = ObjectAnimator.ofFloat(this, "radius",0,getWidth());

        growAnimator.setDuration(AnimationDuration);
        growAnimator.setInterpolator(new LinearInterpolator());

        ObjectAnimator shrinkAnimator = ObjectAnimator.ofFloat(this,"radius",getWidth(),0);

    }
    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                if(  animatorSet != null && animatorSet.isRunning()){
//                    animatorSet.cancel();
//
//                }
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        //canvas.drawCircle(x,y,radius,paint);
    }
}
