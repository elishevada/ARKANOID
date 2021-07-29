package com.elishevada.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

public class Brick {
    private float left, top, right, bottom;
    private Paint brickPaint;
    private boolean brickShown;




    public Brick(float left,float top,float right,float bottom,int color){
        this.brickShown=true;
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
        this.brickPaint = new Paint();
        brickPaint.setColor(color);
        brickPaint.setStyle(Paint.Style.FILL);
    }



    public void draw(Canvas canvas)
    {
        if(brickShown)
            canvas.drawRect(this.left,this.top,this.right,this.bottom,this.brickPaint);
    }

    public boolean isCollid(Ball ball)
    {
        // distance
        return (left - ball.getRadius() <= ball.getCx() && right + ball.getRadius() >= ball.getCx())
                && (top - ball.getRadius() <= ball.getCy() && bottom + ball.getRadius() >= ball.getCy());
    }



    public boolean getBrickShown()
    {
        return brickShown;
    }

    public void setBrickShown(boolean show)
    {
        this.brickShown = show;
    }

    public void setColor(int color)
    {
        brickPaint.setColor(color);
    }

    public float getLeft()
    {
        return left;
    }

    public void setLeft(float left)
    {
        this.left = left;
    }

    public float getTop(){ return top; }

    public void setTop(float top)
    {
        this.top = top;
    }

    public float getRight()
    {
        return right;
    }

    public void setRight(float right)
    {
        this.right = right;
    }

    public float getBottom()
    {
        return bottom;
    }

    public void setBottom(float bottom)
    {
        this.bottom = bottom;
    }

    public Paint getBrickPaint()
    {
        return brickPaint;
    }

    public void setBrickPaint(Paint ballPaint)
    {
        this.brickPaint = ballPaint;
    }

}
