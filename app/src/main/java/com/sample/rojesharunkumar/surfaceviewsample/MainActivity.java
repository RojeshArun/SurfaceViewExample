package com.sample.rojesharunkumar.surfaceviewsample;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class MainActivity extends Activity implements View.OnTouchListener{

    OnView v;
    SurfaceHolder holder;
    Bitmap ball;
    int x, y;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        v = new OnView(this);
        v.setOnTouchListener(this);
        ball = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
        x = y  =0;
        setContentView(v);
    }

    @Override
    protected void onPause() {
        super.onPause();
        v.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        v.resume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent me) {

        //Method is called everytime a touch event occures
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }



        switch (me.getAction()){
            case MotionEvent.ACTION_DOWN:
                x= (int) me.getX();
                y = (int) me.getY();
                break;
            case MotionEvent.ACTION_UP:
                x= (int) me.getX();
                y = (int) me.getY();

                break;
            case MotionEvent.ACTION_MOVE:

                x= (int) me.getX();
                y = (int) me.getY();

                break;
        }
        return true;
    }


    public class OnView extends SurfaceView implements Runnable {
        private boolean isOk = false;
        Thread t = null; // Surface view draw is tedius task so we are shifting to a thead

        public OnView(Context context) {
            super(context);

            holder = getHolder();
        }

        @Override
        public void run() {
            while (isOk){
                // Perform canvas action
                if(!holder.getSurface().isValid()){
                    continue;
                }

                // Open canvas draw your info and close it
                Canvas c = holder.lockCanvas();
                c.drawARGB(255,150,150,10);
                c.drawBitmap(ball,x-ball.getWidth()/2,y-ball.getHeight()/2,null);
                holder.unlockCanvasAndPost(c);


            }
        }

        public void pause() {
            isOk = false;
            while (true){
                try {
                    t.join(); // Thread will go to waiting state
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                break;
            }
            t = null;
        }

        public void resume() {
            isOk = true;
            t = new Thread(this); // Starts the tread
            t.start();
        }
    }
}
