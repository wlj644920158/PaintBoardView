package com.wlj.paintboardview.paint.tool;

import android.graphics.PointF;
import android.view.MotionEvent;


/**
 * 二姐贝塞尔
 */
public class CurveTool extends BaseTool {

    private PointF startPoint;
    private PointF controlPoint;
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
                    getPath().quadTo(controlPoint.x, controlPoint.y,endPoint.x, endPoint.y);
                    getPath().offset(offsetX, offsetY);
                }

                if (event.getPointerCount() == 1 && endPoint == null) {
                    getPath().reset();
                    getPath().moveTo(startPoint.x, startPoint.y);
                    getPath().lineTo(event.getX(), event.getY());
                }

                if(event.getPointerCount() == 1 && endPoint != null){
                    float delX = event.getX() - lastX;
                    float delY = event.getY() - lastY;

                    controlPoint.offset(delX,delY);
                    getPath().reset();
                    getPath().moveTo(startPoint.x, startPoint.y);
                    getPath().quadTo(controlPoint.x, controlPoint.y,endPoint.x, endPoint.y);
                    getPath().offset(offsetX, offsetY);
                }


                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                if (endPoint == null) {
                    endPoint = new PointF(event.getX(), event.getY());
                    controlPoint = new PointF((startPoint.x + endPoint.x) / 2, (startPoint.y + endPoint.y) / 2);
                    getPath().reset();
                    getPath().moveTo(startPoint.x, startPoint.y);
                    getPath().quadTo(controlPoint.x, controlPoint.y,endPoint.x, endPoint.y);
                }
                break;
        }
    }

}
