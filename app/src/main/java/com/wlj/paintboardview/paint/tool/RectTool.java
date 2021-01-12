package com.wlj.paintboardview.paint.tool;

import android.graphics.Path;
import android.graphics.PointF;
import android.view.MotionEvent;

public class RectTool extends BaseTool {

    private PointF centerPoint;

    private float lastX, lastY;
    private float halfWidth, halfHeight;
    private float offsetX, offsetY;

    @Override
    public void onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if (centerPoint == null) {
                    centerPoint = new PointF(event.getX(), event.getY());
                }
                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float delX = event.getX() - lastX;
                float delY = event.getY() - lastY;

                if (event.getPointerId(event.getActionIndex()) != 0) break;
                if (event.getPointerCount() > 1) {

                    offsetX += delX;
                    offsetY += delY;

                } else {
                    halfWidth += delX;
                    halfHeight += delY;

                }
                getPath().reset();
                getPath().addRect(centerPoint.x - Math.abs(halfWidth), centerPoint.y - Math.abs(halfHeight),
                        centerPoint.x + Math.abs(halfWidth), centerPoint.y + Math.abs(halfHeight), Path.Direction.CCW);
                getPath().offset(offsetX, offsetY);


                lastX = event.getX();
                lastY = event.getY();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
    }
}
