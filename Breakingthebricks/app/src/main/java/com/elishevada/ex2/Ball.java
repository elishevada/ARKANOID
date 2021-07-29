package com.elishevada.ex2;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Ball {

    private float cx,cy, dx, dy;
    private int radius;
    private Paint ballPaint;

    public Ball(float cx, float cy, int radius, int color)
    {
        dx=dy=5;
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
        this.ballPaint = new Paint();
        ballPaint.setColor(color);
        ballPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void draw(Canvas canvas)
    {
        canvas.drawCircle(cx, cy, radius, ballPaint);
    }

    public void move(int width, int height)
    {
        cx += dx;
        cy += dy;

        // if out left or right
        if(cx-radius<=0 || cx+radius >= width )
            dx = -dx;

        // if out top or bottom
        if(cy-radius<=0 || cy+radius >= height)
            dy = -dy;
    }



    public void startMoveAfterCollid()
    {
        dy = -dy;
    }


    public void setColor(int color)
    {
        ballPaint.setColor(color);
    }

    public void setStyle(Paint.Style style)
    {
        ballPaint.setStyle(style);
    }

    public float getCx()
    {
        return cx;
    }

    public void setCx(float cx)
    {
        this.cx = cx;
    }

    public float getCy()
    {
        return cy;
    }

    public void setCy(float cy)
    {
        this.cy = cy;
    }

    public float getDx()
    {
        return dx;
    }

    public void setDx(float dx)
    {
        this.dx = dx;
    }

    public float getDy()
    {
        return dy;
    }

    public void setDy(float dy)
    {
        this.dy = dy;
    }

    public int getRadius()
    {
        return radius;
    }

    public void setRadius(int radius)
    {
        this.radius = radius;
    }

    public Paint getBallPaint()
    {
        return ballPaint;
    }

    public void setBallPaint(Paint ballPaint)
    {
        this.ballPaint = ballPaint;
    }
}
