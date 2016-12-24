package com.opendanmaku;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * 弹幕View
 */
public class DanmakuView extends View {

    public static final String TAG = "DanmakuView";

    private final Context mContext;

    private int mMaxRow = 1; //最多几条弹道
    private int mPickItemInterval = 1000;//每隔多长时间取出一条弹幕来播放.
    private int mMaxRunningPerRow = 1; //每条弹道上最多同时有几个弹幕在屏幕上运行
    private float mStartYOffset = 0.1f; //第一个弹道在Y轴上的偏移占整个View的百分比
    private float mEndYOffset = 0.9f;//最后一个弹道在Y轴上的偏移占整个View的百分比



    private HashMap<Integer,ArrayList<IDanmakuItem>> mChannelMap;
    private final java.util.Deque<IDanmakuItem> mWaitingItems = new LinkedList<>();
    private int[] mChannelY; //每条弹道的Y坐标
    private static final float mPartition = 0.95f; //仅View顶部的部分可以播放弹幕百分比

    private static final int STATUS_RUNNING = 1;
    private static final int STATUS_PAUSE = 2;
    private static final int STATUS_STOP = 3;

    private volatile int status = STATUS_STOP;

    private static Random random = new Random();

    private boolean mShowDebug = false;
    private LinkedList<Long> times;
    private Paint fpsPaint;
    private long previousTime = 0;
    private LinkedList<Float> lines;


    public DanmakuView(Context context) {
        this(context, null);
    }

    public DanmakuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DanmakuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.DanmakuView, 0, 0);
        mMaxRow = a.getInteger(R.styleable.DanmakuView_max_row, 1);
        mPickItemInterval = a.getInteger(R.styleable.DanmakuView_pick_interval, 1000);
        mMaxRunningPerRow = a.getInteger(R.styleable.DanmakuView_max_running_per_row, 1);
        mShowDebug = a.getBoolean(R.styleable.DanmakuView_show_debug, false);
        mStartYOffset = a.getFloat(R.styleable.DanmakuView_start_Y_offset, 0.1f);
        mEndYOffset = a.getFloat(R.styleable.DanmakuView_end_Y_offset, 0.9f);
        a.recycle();
        checkYOffset(mStartYOffset, mEndYOffset);
        init();
    }

    private void checkYOffset(float start, float end) {
        if (start >= end ){
            throw new IllegalArgumentException("start_Y_offset must < end_Y_offset");
        }
        if (start < 0f || start >= 1f || end < 0f || end > 1f) {
            throw new IllegalArgumentException("start_Y_offset and end_Y_offset must between 0 and 1)");
        }
    }

    private void init() {
        setBackgroundColor(Color.TRANSPARENT);
        setDrawingCacheBackgroundColor(Color.TRANSPARENT);
        calculation();
    }

    private void calculation() {
        if (mShowDebug) {
            fpsPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
            fpsPaint.setColor(Color.YELLOW);
            fpsPaint.setTextSize(20);
            times = new LinkedList<>();
            lines = new LinkedList<>();
        }
        initChannelMap();
        initChannelY();
    }

    private void initChannelMap(){
        mChannelMap = new HashMap<>(mMaxRow);
        for (int i = 0; i < mMaxRow; i++) {
            ArrayList<IDanmakuItem> runningRow= new ArrayList<IDanmakuItem>(mMaxRunningPerRow);
            mChannelMap.put(i, runningRow);
        }
    }

    private void initChannelY() {
        if (mChannelY == null){
            mChannelY = new int[mMaxRow];
        }

        float rowHeight = getHeight() * (mEndYOffset - mStartYOffset) / mMaxRow;
        float baseOffset = getHeight() * mStartYOffset;
        for (int i = 0; i < mMaxRow; i++) {
            mChannelY[i] = (int) (baseOffset + rowHeight * (i + 1) - rowHeight * 3 / 4);//每一行空间顶部留1/4,剩下3/4显示文字

        }
        if (mShowDebug) {
            lines.add(baseOffset);
            for (int i = 0; i < mMaxRow; i++) {
                lines.add(baseOffset + rowHeight * (i + 1));
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (status == STATUS_RUNNING) {
            try {
                canvas.drawColor(Color.TRANSPARENT);

                //先绘制正在播放的弹幕
                for (int i = 0; i < mChannelMap.size(); i++) {
                    ArrayList<IDanmakuItem> list = mChannelMap.get(i);
                    for (Iterator<IDanmakuItem> it = list.iterator(); it.hasNext(); ) {
                        IDanmakuItem item = it.next();
                        if (item.isOut()) {
                            it.remove();
                        } else {
                            item.doDraw(canvas);
                        }
                    }
                }

                //检查是否需要加载播放下一个弹幕
                if (System.currentTimeMillis() - previousTime > mPickItemInterval) {
                    previousTime = System.currentTimeMillis();
//                    Log.d(TAG, "start pick new item..");
                    IDanmakuItem di = mWaitingItems.pollFirst();
                    if (di != null) {
                        int indexY = findVacant(di);
                        if (indexY >= 0) {
//                            Log.d(TAG, "find vacant channel");
                            di.setStartPosition(canvas.getWidth() - 2, mChannelY[indexY]);
//                            Log.d(TAG, "draw new, text:" + di.getText());
                            //Log.d(TAG, String.format("doDraw, position,x=%s,y=%s", c.getWidth() - 1, mChannelY[indexY]));
                            di.doDraw(canvas);
                            mChannelMap.get(indexY).add(di);//不要忘记加入正运行的维护的列表中

                        } else {
//                                Log.d(TAG, "Not find vacant channel, add it back");
                            addItemToHead(di);//找不到可以播放的弹道,则把它放回列表中
                        }

                    } else {
                        //no item 弹幕播放完毕,
                    }

                }

                if (mShowDebug) {
                    int fps = (int) fps();
                    canvas.drawText("FPS:" + fps, 5f, 20f, fpsPaint);
                    for (float yp : lines) {
                        canvas.drawLine(0f, yp, getWidth(), yp, fpsPaint);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            invalidate();

        } else {//暂停或停止,隐藏弹幕内容
            canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        }
    }


    /**随机寻找一个可以播放弹幕而不会发生碰撞的弹道,返回弹道的Y坐标在mChannelY上的index,如果没有找到则返回-1*/
    private int findVacant(IDanmakuItem item) {
        try {//fix NPT exception
            for (int i = 0; i < mMaxRow; i++) {
                ArrayList<IDanmakuItem> list = mChannelMap.get(i);
                if (list.size() == 0) {
                    return i;
                }
            }
            int ind = random.nextInt(mMaxRow);
            for (int i = 0; i < mMaxRow; i++) {
                ArrayList<IDanmakuItem> list = mChannelMap.get((i + ind) % mMaxRow);
                if (list.size() > mMaxRunningPerRow) {//每个弹道最多mMaxRunning个弹幕
                    continue;
                }
                IDanmakuItem di = list.get(list.size() - 1);
                if (!item.willHit(di)) {
                    return (i + ind) % mMaxRow;
                }
            }
        } catch (Exception e) {
            Log.w(TAG, "findVacant,Exception:" + e.toString());
//                e.printStackTrace();
        }

        return -1;
    }

    private void clearPlayingItems() {
        if (mChannelMap != null) {
            synchronized (mChannelMap) {
                for (int i = 0; i < mChannelMap.size(); i++) {
                    ArrayList<IDanmakuItem> list = mChannelMap.get(i);
                    if (list != null) {
                        list.clear();
                    }
                }
            }
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        initChannelY();//可能屏幕方向切换了,得重新计算坐标
    }

    public boolean isPaused() {
        return STATUS_PAUSE == status;
    }


    /**播放显示弹幕*/
    public void show() {
        status = STATUS_RUNNING;
        invalidate();
    }

    /**隐藏弹幕,暂停播放*/
    public void hide() {
        status = STATUS_PAUSE;
        invalidate();
    }

    /**清空正在播放和等待播放的弹幕*/
    public void clear() {
        status = STATUS_STOP;
        clearItems();
        invalidate();
    }

//    /**清空弹幕等待队列,暂停播放*/
//    public void pauseAndClear() {
//        if (mWaitingItems != null) {
//            synchronized (mWaitingItems) {
//                mWaitingItems.clear();
//            }
//        }
//        clearPlayingItems();
//    }

    private void clearItems() {
        clearRunning();
        clearWaiting();
    }

    private void clearRunning() {
        if (null != mChannelMap && !mChannelMap.isEmpty()) {
            mChannelMap.clear();
        }
    }

    private void clearWaiting(){
        if (null != mWaitingItems && !mWaitingItems.isEmpty()) {
            mWaitingItems.clear();
        }
    }

    public void setMaxRow(int maxRow) {
        this.mMaxRow = maxRow;
        calculation();
        clearRunning();
    }

    public void setPickItemInterval(int pickItemInterval) {
        this.mPickItemInterval = pickItemInterval;
    }

    public void setMaxRunningPerRow(int maxRunningPerRow) {
        this.mMaxRunningPerRow = maxRunningPerRow;
    }

    public void setStartYOffset(float startYOffset, float endYOffset) {
        checkYOffset(startYOffset, endYOffset);
        clearRunning();
        this.mStartYOffset = startYOffset;
        this.mEndYOffset = endYOffset;
        calculation();
    }


    public void addItem(IDanmakuItem item) {
        synchronized (mWaitingItems) {
            this.mWaitingItems.add(item);
        }
    }

    public void addItemToHead(IDanmakuItem item) {
        synchronized (mWaitingItems) {
            this.mWaitingItems.offerFirst(item);
        }
    }

    /**是否新建后台线程来执行添加任务*/
    public void addItem(final List<IDanmakuItem> list, boolean backgroundLoad) {
        if (backgroundLoad) {
            new Thread(){
                @Override
                public void run() {
                    synchronized (mWaitingItems) {
                        mWaitingItems.addAll(list);
                    }
                    postInvalidate();
                }
            }.start();
        } else {
            this.mWaitingItems.addAll(list);
        }
    }


    /** Calculates and returns frames per second */
    private double fps() {
        long lastTime = System.nanoTime();
        times.addLast(lastTime);
        double NANOS = 1000000000.0;
        double difference = (lastTime - times.getFirst()) / NANOS;
        int size = times.size();
        int MAX_SIZE = 100;
        if (size > MAX_SIZE) {
            times.removeFirst();
        }
        return difference > 0 ? times.size() / difference : 0.0;
    }
}
