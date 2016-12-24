package com.atguigu.shoppingmall.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.base.BaseFragment;
import com.atguigu.shoppingmall.community.fragment.CommunityFragment;
import com.atguigu.shoppingmall.home.fragment.HomeFragment;
import com.atguigu.shoppingmall.shoppingcart.fragment.ShoppingCartFragment;
import com.atguigu.shoppingmall.type.fragment.TypeFragment;
import com.atguigu.shoppingmall.user.fragment.UserFragment;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

// 主页面
public class MainActivity extends FragmentActivity {

    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.rb_home)
    RadioButton rbHome;
    @Bind(R.id.rb_type)
    RadioButton rbType;
    @Bind(R.id.rb_community)
    RadioButton rbCommunity;
    @Bind(R.id.rb_cart)
    RadioButton rbCart;
    @Bind(R.id.rb_user)
    RadioButton rbUser;
    @Bind(R.id.rg_main)
    RadioGroup rgMain;
    private ArrayList<BaseFragment> fragments;
    private int position;
    private TypeFragment typeFragment;
    private BaseFragment mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除title
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        //去掉Activity上面的状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        initFragment();
        initListener();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        typeFragment = new TypeFragment();
        fragments.add(new HomeFragment());
        fragments.add(typeFragment);
        fragments.add(new CommunityFragment());
        fragments.add(new ShoppingCartFragment());
        fragments.add(new UserFragment());
    }

    private void initListener() {

        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        break;

                    case R.id.rb_type:
                        position = 1;
                        break;

                    case R.id.rb_community:
                        position = 2;
                        break;

                    case R.id.rb_cart:
                        position = 3;
                        break;

                    case R.id.rb_user:
                        position = 4;
                        break;
                }

                BaseFragment baseFragment = getFragment(position);
                switchFragment(mContext, baseFragment);
            }
        });

        rgMain.check(R.id.rb_home);
    }

    /**
     * @param position
     * @return
     */
    private BaseFragment getFragment(int position) {

        if (fragments != null && fragments.size() > 0) {
            BaseFragment baseFragment = fragments.get(position);
            return baseFragment;
        }

        return null;
    }

    private void switchFragment(Fragment fromFragment, BaseFragment nextFragment) {

        if (mContext != nextFragment) {

            mContext = nextFragment;

            if (nextFragment != null) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

                //判断nextFragment是否添加
                if (!nextFragment.isAdded()) {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.add(R.id.frameLayout, nextFragment).commit();
                } else {
                    //隐藏当前Fragment
                    if (fromFragment != null) {
                        transaction.hide(fromFragment);
                    }
                    transaction.show(nextFragment).commit();
                }
            }
        }
    }
}
