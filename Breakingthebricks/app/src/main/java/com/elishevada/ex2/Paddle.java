package com.elishevada.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Paddle {
    private float left, top, right, bottom,dy;
    private Paint paddlePaint;


    public Paddle(float left,float top,float right,float bottom,int color){
        this.dy=15;
        this.left=left;
        this.top=top;
        this.right=right;
        this.bottom=bottom;
        this.paddlePaint = new Paint();
        paddlePaint.setColor(color);
        paddlePaint.setStyle(Paint.Style.FILL);

    }



    public void draw(Canvas canvas)
    {
        canvas.drawRect(left,top,right,bottom,paddlePaint);
    }


    public void move(int width,boolean pressScreenLeft)
    {
        if(pressScreenLeft){
            if(left - 1 < 0){
                left += dy;
                right += dy;
            }
            left -= dy;
            right -= dy;
        }
        else{
            if(right + 1 >= width){
                left -= dy;
                right -= dy;
            }
            right += dy;
            left += dy;

        }
    }


    public boolean isCollide(Ball ball)
    {
        return left-ball.getRadius() <= ball.getCx()  && right+ball.getRadius() >= ball.getCx();
    }




    public void setColor(int color)
    {
        paddlePaint.setColor(color);
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

    public Paint getBallPaint()
    {
        return paddlePaint;
    }

    public void setBallPaint(Paint ballPaint)
    {
        this.paddlePaint = ballPaint;
    }

}
