package com.atguigu.shoppingmall.community.fragment;

import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.community.adapter.NewPostListViewAdapter;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.community.bean.NewPostBean;
import com.atguigu.shoppingmall.utils.Constants;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.List;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 新帖
 */
public class NewPostFragment extends BaseFragment {
    private ListView lv_new_post;
    private List<NewPostBean.ResultBean> result;

    @Override
    public View initView() {
        View view = View.inflate(mContext, R.layout.fragment_new_post, null);
        lv_new_post = (ListView) view.findViewById(R.id.lv_new_post);
        return view;
    }

    @Override
    public void initData() {
        getDataFromNet();
    }

    public void getDataFromNet() {
        OkHttpUtils
                .get()
                .url(Constants.NEW_POST_URL)
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
        public void onError(Call call, Exception e, int id) {
            Log.e("TAG", "联网失败" + e.getMessage());
        }

        @Override
        public void onResponse(String response, int id) {

            switch (id) {
                case 100:
                    if (response != null) {
                        processData(response);
                        NewPostListViewAdapter adapter = new NewPostListViewAdapter(mContext, result);
                        lv_new_post.setAdapter(adapter);
                    }
                    break;

                case 101:
                    Toast.makeText(mContext, "https", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    private void processData(String json) {
        Gson gson = new Gson();
        NewPostBean newPostBean = gson.fromJson(json, NewPostBean.class);
        result = newPostBean.getResult();
    }
}
