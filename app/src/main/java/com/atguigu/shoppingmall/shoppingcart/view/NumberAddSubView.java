package com.atguigu.shoppingmall.shoppingcart.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;


/**
 * Created by Administrator on 2016/8/31.
 */
public class NumberAddSubView extends LinearLayout implements View.OnClickListener {
    private ImageView btn_sub;
    private ImageView btn_add;
    private TextView tv_count;
    private int value = 1;
    private int minValue = 1;
    private int maxValue = 10;

    public int getValue() {

        String countStr = tv_count.getText().toString().trim();//文本内容
        if (countStr != null) {
            value = Integer.valueOf(countStr);
        }

        return value;
    }

    public void setValue(int value) {
        this.value = value;
        tv_count.setText(String.valueOf(value));
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //把布局和当前类形成整体
        View.inflate(context, R.layout.number_add_sub_layout, this);
        btn_sub = (ImageView) findViewById(R.id.btn_sub);
        btn_add = (ImageView) findViewById(R.id.btn_add);
        tv_count = (TextView) findViewById(R.id.tv_count);

        getValue();

        //设置点击事件
        btn_add.setOnClickListener(this);
        btn_sub.setOnClickListener(this);

        if (attrs != null) {
            //取出属性
            TintTypedArray tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attrs, R.styleable.NumberAddSubView);
            int value = tintTypedArray.getInt(R.styleable.NumberAddSubView_value, 0);
            if (value > 0) {
                setValue(value);
            }
            int minValue = tintTypedArray.getInt(R.styleable.NumberAddSubView_minValue, 0);
            if (value > 0) {
                setMinValue(minValue);
            }
            int maxValue = tintTypedArray.getInt(R.styleable.NumberAddSubView_maxValue, 0);
            if (value > 0) {
                setMaxValue(maxValue);
            }
            Drawable addDrawable = tintTypedArray.getDrawable(R.styleable.NumberAddSubView_numberAddBackground);
            if (addDrawable != null) {
                btn_add.setImageDrawable(addDrawable);
            }
            Drawable subDrawable = tintTypedArray.getDrawable(R.styleable.NumberAddSubView_numberSubBackground);
            if (subDrawable != null) {
                btn_sub.setImageDrawable(subDrawable);
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_add) {
            //加
            addNumber();
            if (onNumberChangeListener != null) {
                onNumberChangeListener.addNumber(v, value);
            }
        } else {
            //减
            subNumber();
            if (onNumberChangeListener != null) {
                onNumberChangeListener.subNumner(v, value);
            }
        }
    }

    private void subNumber() {

        if (value > minValue) {
            value -= 1;
        }

        setValue(value);
    }

    private void addNumber() {

        if (value < maxValue) {
            value += 1;
        }

        setValue(value);
    }

    public interface OnNumberChangeListener {
        //当按钮被点击的时候回调
        void addNumber(View view, int value);

        void subNumner(View view, int value);
    }

    private OnNumberChangeListener onNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.onNumberChangeListener = onNumberChangeListener;
    }
}
