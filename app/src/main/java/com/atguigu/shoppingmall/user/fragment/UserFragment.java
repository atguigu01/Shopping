package com.atguigu.shoppingmall.user.fragment;


import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.hankkin.gradationscroll.GradationScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

// 个人中心页面
public class UserFragment extends BaseFragment  {

    @Bind(R.id.ib_user_avator)
    ImageButton ibUserAvator;
    @Bind(R.id.rl_person_header)
    RelativeLayout rlPersonHeader;
    @Bind(R.id.sv_person)
    GradationScrollView svPerson;
    @Bind(R.id.tv_usercenter)
    TextView tvUsercenter;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_user, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();

        ViewTreeObserver vto = rlPersonHeader.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

            private int height;

            @Override
            public void onGlobalLayout() {
                // 移除监听
                tvUsercenter.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                // 获取顶部图片的高度
                height = rlPersonHeader.getHeight();

                // 监听滑动，改变透明度
                svPerson.setScrollViewListener(new GradationScrollView.ScrollViewListener() {
                    @Override
                    public void onScrollChanged(GradationScrollView scrollView, int x, int y, int oldx, int oldy) {

                        if (y <= 0) {   //设置标题的背景颜色
                            tvUsercenter.setBackgroundColor(Color.argb((int) 0, 255, 0, 0));
                        } else if (y > 0 && y <= height) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) y / height;
                            float alpha = (255 * scale);
                            tvUsercenter.setTextColor(Color.argb((int) alpha, 255, 255, 255));
                            tvUsercenter.setBackgroundColor(Color.argb((int) alpha, 255, 0, 0));
                        } else {    //滑动到banner下面设置普通颜色
                            tvUsercenter.setBackgroundColor(Color.argb((int) 255, 255, 0, 0));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
