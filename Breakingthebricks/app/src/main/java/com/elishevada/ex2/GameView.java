package com.elishevada.ex2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;



public class GameView extends View {
    private int width, height;
    private int ROWS;
    private int COLS;
    private int bgColor;
    private Ball gameball;
    private Paddle gamepaddle;
    private BrickCollection brick_collection;
    private int state_machine=1;
    private Paint textPaint;
    private  boolean startPaddleMove=false;
    private  float touchx;
    private  float touchy;
    private float brickWidth;
    private  float brickheight;
    private int num_of_bricks;
    private boolean lastBrick;
    private int LIVES;
    private int score;
    private Ball live1ball,live2ball,live3ball;
    private final MediaPlayer mp_when_collide;

    //------------------------------------------

    public GameView(Context context, AttributeSet attrs){
        super(context,attrs);
        ROWS =new Random().nextInt(5)+2;
        COLS =new Random().nextInt(5)+3;
        num_of_bricks=ROWS*COLS;
        bgColor = Color.GRAY;
        textPaint=new Paint();
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setColor(Color.GREEN);
        textPaint.setTextSize(55);
        lastBrick=false;
        score=0;
        LIVES=3;
        mp_when_collide = MediaPlayer.create(context, R.raw.mp);
        mp_when_collide.setVolume(50, 50);
    }


    //--------------------------------------------------------------


    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("mylog","ondraw()");
        super.onDraw(canvas);
        canvas.drawColor(bgColor);

        //----------------check where did the user pressed left or right

        if(startPaddleMove){
            if(touchx>=width/2)
                gamepaddle.move(width,false);
            else if(touchx<width/2)
                gamepaddle.move(width,true);
        }


        //----------------check collid paddle and ball

        if(gameball.getCy() + gameball.getRadius() > gamepaddle.getTop()) {
            if (gamepaddle.isCollide(gameball))
                gameball.startMoveAfterCollid();
            else{
                LIVES--;
                if(LIVES==1)
                    live2ball.setStyle(Paint.Style.STROKE);
                else if(LIVES==2)
                    live1ball.setStyle(Paint.Style.STROKE);
                if(LIVES==0){
                    live3ball.setStyle(Paint.Style.STROKE);
                    canvas.drawText("GAME OVER-you Loss!", width/2, height/2, textPaint);
                    set_paddle_ball_location();
                    state_machine=3;
                }
                else {
                    set_paddle_ball_location();
                    state_machine = 1;
                }

            }
        }


        //----------------check collid brick and ball

        boolean firstCollid=true;//need because when the ball collid the brick it continue colid on y till finish the brick and we want only first time
        brick_collection.drawCollection(canvas);
        if(lastBrick){//to desapear the last brick when win
            set_paddle_ball_location();
            state_machine=3;
        }
        for(int i=0;i<ROWS;i++){
            for(int j=0;j<COLS;j++){
                if( firstCollid &&brick_collection.getBrick_collection()[i][j].getBrickShown() && brick_collection.getBrick_collection()[i][j].isCollid(gameball)){
                      brick_collection.getBrick_collection()[i][j].setBrickShown(false);
                      mp_when_collide.start();
                      num_of_bricks--;
                      score+=5*LIVES;
                      if(num_of_bricks==0) {
                          lastBrick=true;
                      }
                      gameball.startMoveAfterCollid();
                      firstCollid=false;
                }
            }
        }



        ///-----------------------------score and live
        canvas.drawText("Score: "+score, 150, 85, textPaint);
        canvas.drawText("Lives:", 1725, 85, textPaint);
        live1ball.draw(canvas);
        live2ball.draw(canvas);
        live3ball.draw(canvas);



        //----------win
        if(state_machine==3 && num_of_bricks==0){
            canvas.drawText("GAME OVER-you won!", width/2, height/2, textPaint);
        }


        //-----------state get ready
        if(state_machine==1 && LIVES>0 && num_of_bricks!=0){
            // draw text
            canvas.drawText("Click to PLAY!", width/2, height/2, textPaint);
        }

        //---------the  game products
        gameball.draw(canvas);
        gameball.move(width,height);
        gamepaddle.draw(canvas);
        brick_collection.drawCollection(canvas);


        //looping
        if(state_machine==1 || state_machine==3)
            return;
        else
            new Thread(new Runnable() {
                public void run() {
                    invalidate();
                }
            }).start();



    }


    //---------------------------------------------------

    private void set_paddle_ball_location() {
        gamepaddle.setLeft((width/2)-(brickWidth/2));
        gamepaddle.setRight((width/2)+(brickWidth/2));
        gamepaddle.setBottom(height-50);
        gamepaddle.setTop(height-50-(height/40));
        gameball.setCx(width/2);
        gameball.setCy(gamepaddle.getTop()-gameball.getRadius());
    }



    //--------------------------------------------------------


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d("mylog","onSizeChanged()");

        width = w;
        height = h;

        brick_collection=new BrickCollection(width,height,ROWS,COLS);
        brick_collection.initFlagShown();
        brickWidth=brick_collection.getBrickWidth();
        brickheight=brick_collection.getBrickHeight();
        gamepaddle=new Paddle((width/2)-(brickWidth/2),height-50-(height/40),(width/2)+(brickWidth/2),height-50,Color.BLUE);
        gameball=new Ball(width/2,gamepaddle.getTop()-(brickheight/2),(height/40),Color.WHITE);
        live1ball=new Ball(1850,70,20,Color.GREEN);
        live2ball=new Ball(1900,70,20,Color.GREEN);
        live3ball=new Ball(1950,70,20,Color.GREEN);

    }



    //--------------------------------------------------------


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        touchx = event.getX();
        touchy = event.getY();
        Log.d("mylog", "onTouchEvent touchx=" + touchx + ",touchy=" + touchy);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if(state_machine==2) {
                    startPaddleMove = true;
                }
                else if(state_machine==3){
                    state_machine=1;
                    ROWS =new Random().nextInt(5)+2;
                    COLS =new Random().nextInt(5)+3;
                    brick_collection=new BrickCollection(width,height,ROWS,COLS);
                    brick_collection.initFlagShown();
                    num_of_bricks=ROWS*COLS;
                    score=0;
                    LIVES=3;
                    lastBrick=false;
                    live3ball.setStyle(Paint.Style.FILL);
                    live2ball.setStyle(Paint.Style.FILL);
                    live1ball.setStyle(Paint.Style.FILL);
                    set_paddle_ball_location();
                }
                else if (state_machine == 1)
                    state_machine = 2;
                new Thread(new Runnable() {
                    public void run() {
                        invalidate();
                    }
                }).start();

                break;
            case MotionEvent.ACTION_UP:
                startPaddleMove=false;
                break;

        }
        return true;
    }
}
