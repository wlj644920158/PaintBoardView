package com.wlj.paintboardview.paint;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.wlj.paintboardview.paint.tool.BaseTool;
import com.wlj.paintboardview.paint.tool.CircleTool;
import com.wlj.paintboardview.paint.tool.CurveTool;
import com.wlj.paintboardview.paint.tool.EraserTool;
import com.wlj.paintboardview.paint.tool.LineTool;
import com.wlj.paintboardview.paint.tool.PenTool;
import com.wlj.paintboardview.paint.tool.RectTool;

import java.util.ArrayList;
import java.util.List;

public class PaintBoardView extends View {

    public static final int TOOL_PEN = 0;
    public static final int TOOL_LINE = 1;
    public static final int TOOL_RECT = 2;
    public static final int TOOL_CIRCLE = 3;
    public static final int TOOL_CURVE = 4;
    public static final int TOOL_ERASER = 5;


    private List<BaseTool> toolHistory = new ArrayList<>();
    private List<BaseTool> drawingQueue = new ArrayList<>();
    private BaseTool curTool;
    private boolean editing;//当前是不是正在编辑

    private PaintBoardListener paintBoardListener;

    public void setPaintBoardListener(PaintBoardListener listener) {
        this.paintBoardListener = listener;
    }

    private int curType = TOOL_PEN;
    private Paint paint;
    private int color = Color.RED;
    private boolean isFill;
    private int strokeWidth = 10;

    public boolean isEditing() {
        return editing;
    }

    public PaintBoardView(Context context) {
        this(context, null);
    }

    public PaintBoardView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PaintBoardView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        getToolByType();
    }

    public void changeTool(int type) {
        curType = type;
        resetCurTool();
    }

    private void getToolByType() {
        switch (curType) {
            case TOOL_PEN:
                curTool = new PenTool();
                break;
            case TOOL_LINE:
                curTool = new LineTool();
                break;
            case TOOL_RECT:
                curTool = new RectTool();
                break;
            case TOOL_CIRCLE:
                curTool = new CircleTool();
                break;
            case TOOL_CURVE:
                curTool = new CurveTool();
                break;
            case TOOL_ERASER:
                curTool = new EraserTool();
                break;
        }
        curTool.setColor(color);
        curTool.setFill(isFill);
        curTool.setStrokeWidth(strokeWidth);
        curTool.setPath(new Path());
    }

    public void back() {
        int size = toolHistory.size();
        if (size <= 0) return;
        toolHistory.remove(size - 1);
        invalidate();
    }

    /**
     * 可以在这个自定义view里面进行绘制到bitmap
     * 可以保存view的视图
     * 截屏,屏蔽一些按钮
     */
    public void save() {
        invalidate();
    }

    public void clean() {
        toolHistory.clear();
        resetCurTool();
    }

    public void resetCurTool() {
        editing = false;
        getToolByType();
        invalidate();
    }

    public void finish() {
        toolHistory.add(curTool);
        resetCurTool();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
            editing = true;
            if (paintBoardListener != null) {
                paintBoardListener.onStartEditing();
            }
        }
        curTool.onTouchEvent(event);
        invalidate();
        return true;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //当前正在绘制的工具是不是也要绘制
        drawingQueue.clear();
        drawingQueue.addAll(toolHistory);
        if (editing) {
            drawingQueue.add(curTool);
        }

        int scount = canvas.saveLayer(0, 0, getWidth(), getHeight(), paint);

        for (BaseTool tool : drawingQueue) {
            if (tool instanceof EraserTool) {
                paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                tool.onDraw(canvas, paint);
                paint.setXfermode(null);
            } else {
                tool.onDraw(canvas, paint);
            }
        }

        canvas.restoreToCount(scount);

    }

    public void setFill(boolean isChecked) {
        isFill = isChecked;
    }

    public void setColor(int c) {
        color = c;
    }
}
