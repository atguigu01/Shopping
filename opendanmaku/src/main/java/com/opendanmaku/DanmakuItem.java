package com.opendanmaku;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.SpannableString;
import android.text.StaticLayout;
import android.text.TextPaint;

public class DanmakuItem implements IDanmakuItem {

    /** X axis base speed*/
    private static int sBaseSpeed = 3;

    private Context mContext;

    /**DanmakuView width, height*/
    private int mContainerWidth, mContainerHeight;

    private int mTextSize;

    private int mTextColor = Color.WHITE;

    private SpannableString mContent;

    private int mCurrX, mCurrY;

    /** X axis speed factor*/
    private float mFactor;

    private StaticLayout staticLayout;
    private StaticLayout borderStaticLayout;
    private static TextPaint strokePaint = new TextPaint();

    private int mContentWidth , mContentHeight;


    static {
        strokePaint.setARGB(255, 0, 0, 0);
//        strokePaint.setTextAlign(Paint.Align.CENTER);
//        strokePaint.setTextSize(16);
//        strokePaint.setTypeface(Typeface.DEFAULT_BOLD);
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setStrokeWidth(4);
        strokePaint.setAntiAlias(true);

    }
    /**
     * construct a DanmakuItem
     * @param context Context
     * @param content paint text as content
     * @param startX start position of X axis,
     *               normally should be the screen width, e.g. right side of the view).
     *               the Y axis position will be assigned a channel by the DanmakuView randomly.
     */
    public DanmakuItem(Context context, CharSequence content, int startX) {
        this(context, new SpannableString(content), startX, 0, 0, 0, 1f);
    }

    public DanmakuItem(Context context, CharSequence content, int startX, int startY) {
        this(context, new SpannableString(content), startX, startY, 0, 0, 1f);
    }


    public DanmakuItem(Context context, SpannableString content, int startX, int startY,
                       int textColorResId, int textSizeInDip, float speedFactor) {
        this.mContext = context;
        this.mContent = content;
        this.mCurrX = startX;
        this.mCurrY = startY;
        setTextColor(textColorResId);
        setTextSize(textSizeInDip);
        mFactor = speedFactor;
        measure();
    }

    private void measure() {
        TextPaint tp = new TextPaint();
        tp.setAntiAlias(true);
        tp.setColor(mTextColor);
        tp.setTextSize(mTextSize);
        strokePaint.setTextSize(mTextSize);
//        tp.setShadowLayer(4, 0, 0, Color.BLACK);
        mContentHeight = getFontHeight(tp);
        staticLayout = new StaticLayout(mContent,
                            tp,
                            (int) Layout.getDesiredWidth(mContent, 0, mContent.length(), tp) + 1,
                            Layout.Alignment.ALIGN_NORMAL,
                            1.0f,
                            0.0f,
                            false);
        mContentWidth = staticLayout.getWidth();
        borderStaticLayout = new StaticLayout(mContent,
                strokePaint,
                (int) Layout.getDesiredWidth(mContent, 0, mContent.length(), tp) + 1,
                Layout.Alignment.ALIGN_NORMAL,
                1.0f,
                0.0f,
                false);
    }

    @Override
    public void doDraw(Canvas canvas) {
        int canvasWidth = canvas.getWidth();
        int canvasHeight = canvas.getHeight();

        if (canvasWidth != this.mContainerWidth || canvasHeight != this.mContainerHeight) {//phone rotated !
            this.mContainerWidth = canvasWidth;
            this.mContainerHeight = canvasHeight;
        }
        canvas.save();
        canvas.translate(mCurrX,mCurrY);
//        for (int i = 0; i < 4; i++) { //加深阴影,产生描边效果. stroke/outline effect
//            staticLayout.draw(canvas);
//        }
        borderStaticLayout.draw(canvas);
        staticLayout.draw(canvas);
        canvas.restore();
        mCurrX = (int) (mCurrX - sBaseSpeed * mFactor);//only support moving along X axis
    }

    @Override
    public void setTextSize(int textSizeInDip) {
        if (textSizeInDip > 0) {
            this.mTextSize = dip2px(mContext, textSizeInDip);
            measure();
        } else {
            this.mTextSize = dip2px(mContext, 12); // textSize default to 12 dp
        }

    }

    @Override
    public void setTextColor(int textColorResId) {
        if (textColorResId > 0) {
            this.mTextColor = mContext.getResources().getColor(textColorResId);
            measure();
        }
    }

    @Override
    public void setStartPosition(int x, int y) {
        this.mCurrX = x;
        this.mCurrY = y;
    }

    @Override
    public void setSpeedFactor(float factor) {
        this.mFactor = factor;
    }

    @Override
    public float getSpeedFactor() {
        return mFactor;
    }

    @Override
    public boolean isOut() {
        return mCurrX < 0 && Math.abs(mCurrX) > mContentWidth;
    }

    @Override
    public void release() {
        mContext = null;
    }

    @Override
    public int getWidth() {
        return mContentWidth;
    }

    @Override
    public int getHeight() {
        return mContentHeight;
    }

    @Override
    public int getCurrX() {
        return mCurrX;
    }

    @Override
    public int getCurrY() {
        return mCurrY;
    }

    /***
     * test whether this Danmaku Item would be hit the already running one or not
     * if it to be run on the same channel
     * @param runningItem item is already moving on the channel
     * @return hit or not
     */
    public boolean willHit(IDanmakuItem runningItem) {
        if (runningItem.getWidth() + runningItem.getCurrX() > mContainerWidth) {
            return true;
        }

        if (runningItem.getSpeedFactor()>= mFactor) {
            return false;
        }

        float len1 = runningItem.getCurrX() + runningItem.getWidth();
        float t1 = len1 / (runningItem.getSpeedFactor() * DanmakuItem.sBaseSpeed);
        float len2 = t1 * mFactor * DanmakuItem.sBaseSpeed;
        if (len2 > len1) {
            return true;
        } else {
            return false;
        }

    }


    public static int getBaseSpeed() {
        return sBaseSpeed;
    }

    public static void setBaseSpeed(int baseSpeed) {
        DanmakuItem.sBaseSpeed = baseSpeed;
    }


    private static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private static int getFontHeight(TextPaint paint){
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }
}
