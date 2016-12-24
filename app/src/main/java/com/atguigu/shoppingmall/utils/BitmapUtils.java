package com.atguigu.shoppingmall.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

/**
 * 图片处理工具类
 */
public class BitmapUtils {

    //提供一个圆形的Bitmap对象
    public static Bitmap circleBitmap(Bitmap source) {
        //获取图片的宽度
        int width = source.getWidth();
        //创建一个与source等宽的Bitmap对象
        Bitmap bitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //创建等大小的画布
        Canvas canvas = new Canvas(bitmap);
        //绘制一个圆圈：将此圆圈理解为下层图片
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);

        //设置图片相交情况下的处理方式
        //setXfermode：设置当绘制的图像出现相交情况时候的处理方式的,它包含的常用模式有哪几种
        //PorterDuff.Mode.SRC_IN 取两层图像交集部门,只显示上层图像,注意这里是指取相交叉的部分,然后显示上层图像
        //PorterDuff.Mode.DST_IN 取两层图像交集部门,只显示下层图像
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //source:理解为上层图片
        canvas.drawBitmap(source, 0, 0, paint);

        return bitmap;
    }

    //图片的压缩
    public static Bitmap zoom(Bitmap source,float w ,float h){//参数2，3：不能声明int
        Matrix matrix = new Matrix();
        matrix.postScale(w / source.getWidth(),h / source.getHeight());

        Bitmap bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);

        return bitmap;
    }
}
