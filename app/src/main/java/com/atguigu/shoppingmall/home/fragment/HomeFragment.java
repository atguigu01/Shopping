package com.atguigu.shoppingmall.home.fragment;


import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.home.adapter.HomeRecycleAdapter;
import com.atguigu.shoppingmall.home.bean.ResultBean;
import com.atguigu.shoppingmall.user.activity.MessageCenterActivity;
import com.atguigu.shoppingmall.utils.Constants;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Request;

// 首页
public class HomeFragment extends BaseFragment {

    private ResultBean resultBean;
    private RecyclerView rvHome;
    private ImageView ib_top;
    private HomeRecycleAdapter adapter;
    private TextView tv_search_home;
    private TextView tv_message_home;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_home, null);
        rvHome = (RecyclerView) view.findViewById(R.id.rv_home);
        ib_top = (ImageView) view.findViewById(R.id.ib_top);
        tv_search_home = (TextView) view.findViewById(R.id.tv_search_home);
        tv_message_home = (TextView) view.findViewById(R.id.tv_message_home);

        return view;
    }

    @Override
    public void initData() {
        //请求网络
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.HOME_URL)
                .id(100)
                .build()
                .execute(new MyStringCallback());
    }

    public class MyStringCallback extends StringCallback {

        @Override
        public void onBefore(Request request, int id) {
        }

        @Override
        public void onAfter(int id) {
        }

        @Override
        public void onError(okhttp3.Call call, Exception e, int id) {

        }

        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        adapter = new HomeRecycleAdapter(mContext, resultBean);
                        rvHome.setAdapter(adapter);

                        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);

                        //设置滑动到哪个位置了的监听
                        manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                            @Override
                            public int getSpanSize(int position) {
                                if (position <= 3) {
                                    ib_top.setVisibility(View.GONE);
                                } else {
                                    ib_top.setVisibility(View.VISIBLE);
                                }
                                return 1;
                            }
                        });
                        //设置网格布局
                        rvHome.setLayoutManager(manager);

                        initListener();
                    }
                    break;
                case 101:
                    break;
            }
        }
    }

    private void initListener() {

        //置顶的监听
        ib_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rvHome.scrollToPosition(0);
            }
        });

        //搜素的监听
        tv_search_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "搜索", Toast.LENGTH_SHORT).show();
            }
        });

        //消息的监听
        tv_message_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MessageCenterActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void processData(String json) {

        if (!TextUtils.isEmpty(json)) {
            JSONObject jsonObject = JSON.parseObject(json);
            //得到状态码
            String code = jsonObject.getString("code");
            String msg = jsonObject.getString("msg");
            String result = jsonObject.getString("result");

            //得到resultBean的数据
            JSONObject ResultBean = JSON.parseObject(result, ResultBean.class);
            String banner_info = ResultBean.getString("banner_info");
            String act_info = ResultBean.getString("act_info");
            String channel_info = ResultBean.getString("channel_info");
            String hot_info = ResultBean.getString("hot_info");
            String recommend_info = ResultBean.getString("recommend_info");
            String seckill_info = ResultBean.getString("seckill_info");

            resultBean = new ResultBean();

            //设置BannerInfoBean数据
            List<ResultBean.BannerInfoBean> bannerInfoBeans = JSON.parseArray(banner_info, ResultBean.BannerInfoBean.class);
            resultBean.setBanner_info(bannerInfoBeans);
            String value = jsonObject.getString("value");
            com.atguigu.shoppingmall.home.bean.ResultBean.BannerInfoBean.ValueBean valueBean = JSON.parseObject(value, com.atguigu.shoppingmall.home.bean.ResultBean.BannerInfoBean.ValueBean.class);


            //设置actInfoBeans数据
            List<ResultBean.ActInfoBean> actInfoBeans = JSON.parseArray(act_info, ResultBean.ActInfoBean.class);
            resultBean.setAct_info(actInfoBeans);

            //设置channelInfoBeans的数据
            List<ResultBean.ChannelInfoBean> channelInfoBeans = JSON.parseArray(channel_info, ResultBean.ChannelInfoBean.class);
            resultBean.setChannel_info(channelInfoBeans);

            //设置hotInfoBeans的数据
            List<ResultBean.HotInfoBean> hotInfoBeans = JSON.parseArray(hot_info, ResultBean.HotInfoBean.class);
            resultBean.setHot_info(hotInfoBeans);

            //设置recommendInfoBeans的数据
            List<ResultBean.RecommendInfoBean> recommendInfoBeans = JSON.parseArray(recommend_info, ResultBean.RecommendInfoBean.class);
            resultBean.setRecommend_info(recommendInfoBeans);

            //设置seckillInfoBean的数据
            ResultBean.SeckillInfoBean seckillInfoBean = JSON.parseObject(seckill_info, ResultBean.SeckillInfoBean.class);
            resultBean.setSeckill_info(seckillInfoBean);
        }
    }
}
