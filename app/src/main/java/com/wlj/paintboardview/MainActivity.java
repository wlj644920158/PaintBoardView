package com.wlj.paintboardview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;

import com.wlj.paintboardview.paint.PaintBoardListener;
import com.wlj.paintboardview.paint.PaintBoardView;

public class MainActivity extends AppCompatActivity {

    PaintBoardView paint_board_view;
    RadioGroup rg_tools;
    RadioGroup rg_colors;
    LinearLayout ll_confirm;
    CheckBox cb_fill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        paint_board_view = findViewById(R.id.paint_board_view);
        rg_tools = findViewById(R.id.rg_tools);
        rg_colors = findViewById(R.id.rg_colors);

        cb_fill = findViewById(R.id.cb_fill);

        cb_fill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                paint_board_view.setFill(isChecked);
            }
        });

        rg_tools.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int type;
                switch (checkedId) {
                    case R.id.rb_line:
                        type = PaintBoardView.TOOL_LINE;
                        break;
                    case R.id.rb_rect:
                        type = PaintBoardView.TOOL_RECT;
                        break;
                    case R.id.rb_circle:
                        type = PaintBoardView.TOOL_CIRCLE;
                        break;
                    case R.id.rb_curve:
                        type = PaintBoardView.TOOL_CURVE;
                        break;
                    case R.id.rb_eraser:
                        type = PaintBoardView.TOOL_ERASER;
                        break;
                    default:
                        type = PaintBoardView.TOOL_PEN;
                }
                paint_board_view.changeTool(type);
            }
        });

        rg_colors.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_r:
                        paint_board_view.setColor(Color.RED);
                        break;
                    case R.id.rb_g:
                        paint_board_view.setColor(Color.GREEN);
                        break;
                    case R.id.rb_b:
                        paint_board_view.setColor(Color.BLUE);
                        break;
                }
            }
        });

        paint_board_view.setPaintBoardListener(new PaintBoardListener() {
            @Override
            public void onStartEditing() {
                ll_confirm.setVisibility(View.VISIBLE);
            }
        });

        ll_confirm = findViewById(R.id.ll_confirm);
    }

    public void onBack(View view) {
        if (paint_board_view.isEditing()) return;
        paint_board_view.back();
    }

    public void onSave(View view) {
        paint_board_view.save();
    }

    public void onClean(View view) {
        ll_confirm.setVisibility(View.GONE);
        paint_board_view.clean();
    }

    public void onCancel(View view) {
        ll_confirm.setVisibility(View.GONE);
        paint_board_view.resetCurTool();
    }

    public void onFinish(View view) {
        ll_confirm.setVisibility(View.GONE);
        paint_board_view.finish();
    }
}