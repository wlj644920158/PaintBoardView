package com.wlj.paintboardview.paint.tool;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;

public class BaseTool {

    private int color;
    private boolean isFill;
    private int strokeWidth;
    private Path path;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public boolean isFill() {
        return isFill;
    }

    public void setFill(boolean fill) {
        isFill = fill;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(int strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public Path getPath() {
        return path;
    }

    public void setPath(Path path) {
        this.path = path;
    }

    public void onTouchEvent(MotionEvent event) {

    }

    public void onDraw(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(getStrokeWidth());
        paint.setStyle(isFill() ? Paint.Style.FILL : Paint.Style.STROKE);
        paint.setColor(getColor());
        canvas.drawPath(path, paint);
    }

}
