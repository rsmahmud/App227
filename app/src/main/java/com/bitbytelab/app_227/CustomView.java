package com.bitbytelab.app_227;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

public class CustomView extends View {
    Paint p;
    int x=0;
    Bitmap bp;


    public CustomView(Context context) {
        super(context);
        init();
    }

    public void init(){
        p = new Paint();
        //bp = BitmapFactory.decodeResource(getResources(),R.drawable.pro_pic_fb);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.drawColor(Color.RED);

        p.setColor(Color.YELLOW);
        canvas.drawRect(100,100,500,500,p);

        p.setColor(Color.GREEN);
        canvas.drawArc(500,500,800,800,x,30,true,p);
        canvas.drawArc(500,500,800,800,x+120,30,true,p);
        canvas.drawArc(500,500,800,800,x+240,30,true,p);
        //canvas.drawBitmap(bp,x,500,null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                startFan();
                break;
            case MotionEvent.ACTION_UP:
                break;

        }
        return true;
    }
    void startFan(){
        x = x + 5;
        invalidate();
    }
}


