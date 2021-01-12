package com.wlj.paintboardview.paint.tool;

import android.view.MotionEvent;

public class PenTool extends BaseTool {

    @Override
    public void onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                getPath().moveTo(event.getX(),event.getY());
                break;
            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
                getPath().lineTo(event.getX(),event.getY());
                break;
        }
    }
}
