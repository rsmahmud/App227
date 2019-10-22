package com.bitbytelab.app_227;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MySurfaceView extends SurfaceView implements Runnable {

    SurfaceHolder sh;
    Paint p;
    Canvas canvas;
    Boolean isRunning;
    int x=0;
    Thread t;


    public MySurfaceView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //super.onDraw(canvas);
        if(sh.getSurface().isValid()){
            canvas.drawColor(Color.RED);

            p.setColor(Color.YELLOW);
            canvas.drawArc(500,500,800,800,x,30,true,p);
            canvas.drawArc(500,500,800,800,x+120,30,true,p);
            canvas.drawArc(500,500,800,800,x+240,30,true,p);

            x = x + 10;
        }
    }

    @SuppressLint("WrongCall")
    @Override
    public void run() {
        do{
            try {
                synchronized (this){
                    Thread.sleep(300);
                    canvas = sh.lockCanvas();
                    onDraw(canvas);
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }finally {

            }
        }while (isRunning);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                isRunning = true;
                //startThread();
                break;
            case MotionEvent.ACTION_UP:
                stopRunning();
                break;
        }
        return true;
    }

    private void stopRunning() {
        isRunning = false;
        try {
            t.join();
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
