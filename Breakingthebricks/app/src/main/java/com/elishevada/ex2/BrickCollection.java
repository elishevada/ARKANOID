package com.elishevada.ex2;

import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;

;
public class BrickCollection {
    private int width;
    private int height;
    private int ROWS;
    private int COLS;
    private float brickHeight;
    private float brickWidth;
    private Brick[][] brick_collection;
    private final int spaceBetween=5;





    public BrickCollection(int width,int height,int rows,int cols){
        this.width=width;
        this.height=height;
        this.ROWS=rows;
        this.COLS=cols;
        this.brickHeight=height/20;
        this.brickWidth=(width-(spaceBetween*cols))/cols;
    }


    public void initFlagShown(){
        brick_collection = new Brick [ROWS][COLS];
        for(int i = 0; i < ROWS; i++)
            for (int j = 0; j < COLS; j++)
                brick_collection[i][j]=new Brick(0,0,0,0,Color.RED);

    }

    public void drawCollection(Canvas canvas){
        float l,t,r,b,posH=0,posW;
        for(int i=0;i<ROWS;i++){
            posW=0;
            for(int j=0;j<COLS;j++){
                l=posW+spaceBetween;
                t=130+posH+spaceBetween;
                r=((j+1)*spaceBetween)+((j+1)*brickWidth);
                b=130+((i+1)*brickHeight)+((i+1)*spaceBetween);
                this.brick_collection[i][j].setLeft(l);
                this.brick_collection[i][j].setTop(t);
                this.brick_collection[i][j].setRight(r);
                this.brick_collection[i][j].setBottom(b);
                this.brick_collection[i][j].draw(canvas);
                posW=posW+brickWidth+spaceBetween;
            }
            posH=posH+brickHeight+spaceBetween;
        }
    }

    public float getBrickWidth() {
        return brickWidth;
    }

    public float getBrickHeight() {
        return  brickHeight;
    }

    public Brick[][] getBrick_collection() {
        return brick_collection;
    }
}
