package com.tfx.shoppingmall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tfx.shoppingmall.R;

/**
 * Created by Tfx on 2016/12/4.
 */

public class NumberAddSubView extends LinearLayout implements View.OnClickListener {
    ImageView mIvAdd;
    TextView mTvValue;
    ImageView mIvSub;

    int maxValue = 5;
    int minValue = 1;
    int value = 1;

    public NumberAddSubView(Context context) {
        this(context, null);
    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View rootView = View.inflate(context, R.layout.addsubview_layout, this);
        mIvAdd = (ImageView) rootView.findViewById(R.id.iv_add);
        mTvValue = (TextView) rootView.findViewById(R.id.tv_value);
        mIvSub = (ImageView) rootView.findViewById(R.id.iv_sub);

        mIvAdd.setOnClickListener(this);
        mIvSub.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == mIvAdd) {
            //加
            addNumber(view);
        } else if (view == mIvSub) {
            //减
            subNumber(view);
        }

    }

    private void subNumber(View view) {
        if (value > minValue) {
            value--;
            setValue(value);
        }
        if (mOnNumberChangeListener != null) {
            mOnNumberChangeListener.onNumberChange(view, value);
        }
    }

    private void addNumber(View view) {
        if (value < maxValue) {
            value++;
            setValue(value);
        }
        if (mOnNumberChangeListener != null) {
            mOnNumberChangeListener.onNumberChange(view, value);
        }
    }

    public interface OnNumberChangeListener {
        void onNumberChange(View view, int number);
    }

    private OnNumberChangeListener mOnNumberChangeListener;

    public void setOnNumberChangeListener(OnNumberChangeListener onNumberChangeListener) {
        this.mOnNumberChangeListener = onNumberChangeListener;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
        mTvValue.setText(value + "");
    }
}

