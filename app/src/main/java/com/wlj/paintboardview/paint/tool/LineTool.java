package com.wlj.paintboardview.paint.tool;

import android.graphics.PointF;
import android.view.MotionEvent;

public class LineTool extends BaseTool {

    private PointF startPoint;
    private PointF endPoint;

    private float lastX, lastY;
    private float offsetX, offsetY;

    @Override
    public void onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (startPoint == null) {
                    startPoint = new PointF(event.getX(), event.getY());
                }
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (event.getPointerId(event.getActionIndex()) != 0) break;


                if (event.getPointerCount() > 1 && endPoint != null) {
                    float delX = event.getX() - lastX;
                    float delY = event.getY() - lastY;
                    offsetX += delX;
                    offsetY += delY;

                    getPath().reset();
                    getPath().moveTo(startPoint.x, startPoint.y);
                    getPath().lineTo(endPoint.x, endPoint.y);
                    getPath().offset(offsetX, offsetY);
                }

                if (event.getPointerCount() == 1 && endPoint == null) {
                    getPath().reset();
                    getPath().moveTo(startPoint.x, startPoint.y);
                    getPath().lineTo(event.getX(), event.getY());

                }


                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (endPoint == null) {
                    endPoint = new PointF(event.getX(), event.getY());
                    getPath().reset();
                    getPath().moveTo(startPoint.x, startPoint.y);
                    getPath().lineTo(endPoint.x, endPoint.y);
                }
                break;
        }
    }
}
