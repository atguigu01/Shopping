package com.atguigu.shoppingmall.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.atguigu.shoppingmall.R;

// 登录页面
public class LoginActivity extends Activity implements View.OnClickListener {
    private ImageButton ibLoginBack;
    private EditText etLoginPwd;
    private ImageButton ibLoginVisible;
    private Button btnLogin;
    private ImageButton ib_weibo;
    private ImageButton ib_qq;
    private ImageButton ib_wechat;

    private int count;

    // 初始化view
    private void findViews() {
        ibLoginBack = (ImageButton) findViewById(R.id.ib_login_back);
        etLoginPwd = (EditText) findViewById(R.id.et_login_pwd);
        ibLoginVisible = (ImageButton) findViewById(R.id.ib_login_visible);
        btnLogin = (Button) findViewById(R.id.btn_login);
        ib_weibo = (ImageButton) findViewById(R.id.ib_weibo);
        ib_qq = (ImageButton) findViewById(R.id.ib_qq);
        ib_wechat = (ImageButton) findViewById(R.id.ib_wechat);

        ibLoginBack.setOnClickListener(this);
        ibLoginVisible.setOnClickListener(this);
        btnLogin.setOnClickListener(this);
        ib_weibo.setOnClickListener(this);
        ib_qq.setOnClickListener(this);
        ib_wechat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == ibLoginBack) {
            finish();
        } else if (v == ibLoginVisible) {

            count++;
            if (count % 2 == 0) {
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_invisible);
                etLoginPwd.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            } else {
                ibLoginVisible.setBackgroundResource(R.drawable.new_password_drawable_visible);
                etLoginPwd.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
