package com.hankkin.gradationscroll;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

public class MaterialIndicator extends View implements ViewPager.OnPageChangeListener {

    private static final String TAG = MaterialIndicator.class.getSimpleName();
    private static final int UNDEFINED_PADDING = -1;
    private final Interpolator interpolator = new FastOutSlowInInterpolator();
    private final Paint indicatorPaint;
    private final Paint selectedIndicatorPaint;
    private final float indicatorRadius;
    private final float indicatorPadding;

    private final RectF selectorRect;
    private int count;
    private int selectedPage = 0;
    private float deselectedAlpha = 0.2f;
    private float offset;

    public MaterialIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MaterialIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        selectedIndicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        indicatorPaint.setColor(Color.BLACK);
        indicatorPaint.setAlpha((int) (deselectedAlpha * 255));
        selectorRect = new RectF();
        if (isInEditMode()) {
            count = 3;
        }
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MaterialIndicator, 0, R.style.MaterialIndicator);
        try {
            indicatorRadius = typedArray.getDimension(R.styleable.MaterialIndicator_mi_indicatorRadius, 0);
            indicatorPadding = typedArray.getDimension(R.styleable.MaterialIndicator_mi_indicatorPadding, UNDEFINED_PADDING);
            selectedIndicatorPaint.setColor(typedArray.getColor(R.styleable.MaterialIndicator_mi_indicatorColor, 0));
        } finally {
            typedArray.recycle();
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        selectedPage = position;
        offset = positionOffset;
        invalidate();
    }

    @Override
    public void onPageSelected(int position) {
        selectedPage = position;
        offset = 0;
        invalidate();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void setAdapter(PagerAdapter adapter) {
        this.count = adapter.getCount();
        requestLayout();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = getMeasuredWidth();
        if (getLayoutParams().width == ViewPager.LayoutParams.WRAP_CONTENT) {
            width = getSuggestedMinimumWidth();
        }
        setMeasuredDimension(width, getSuggestedMinimumHeight());
    }

    @Override
    protected int getSuggestedMinimumWidth() {
        return (int) (indicatorDiameter() * count + getInternalPadding());
    }

    private float getInternalPadding() {
        if (indicatorPadding == UNDEFINED_PADDING || indicatorPadding == 0 || count == 0) {
            return 0;
        }
        return indicatorPadding * (count - 1);
    }

    @Override
    protected int getSuggestedMinimumHeight() {
        return getPaddingTop() + getPaddingBottom() + (int) indicatorDiameter();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float gap = getGapBetweenIndicators();
        for (int i = 0; i < count; i++) {
            float position = indicatorStartX(gap, i);
            canvas.drawCircle(position + indicatorRadius, midY(), indicatorRadius, indicatorPaint);
        }
        float extenderStart = indicatorStartX(gap, selectedPage) + Math.max(gap * (interpolatedOffset() - 0.5f) * 2, 0);
        float extenderEnd = indicatorStartX(gap, selectedPage) + indicatorDiameter() + Math.min(gap * interpolatedOffset() * 2, gap);
        selectorRect.set(extenderStart, midY() - indicatorRadius, extenderEnd, midY() + indicatorRadius);
        canvas.drawRoundRect(selectorRect, indicatorRadius, indicatorRadius, selectedIndicatorPaint);
    }

    private float getGapBetweenIndicators() {
        if (indicatorPadding == UNDEFINED_PADDING) {
            return (getWidth() - indicatorDiameter()) / (count + 1);
        } else {
            return indicatorPadding;
        }
    }

    private float indicatorStartX(float gap, int page) {
        return ViewCompat.getPaddingStart(this) + gap * page + indicatorRadius;
    }

    private float interpolatedOffset() {
        return interpolator.getInterpolation(offset);
    }

    private float indicatorDiameter() {
        return indicatorRadius * 2;
    }

    private float midY() {
        return getHeight() / 2f;
    }

}
