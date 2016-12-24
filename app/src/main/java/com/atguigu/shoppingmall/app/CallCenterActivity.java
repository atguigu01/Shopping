package com.atguigu.shoppingmall.app;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.atguigu.shoppingmall.R;
import com.atguigu.shoppingmall.utils.Constants;

// 电话客服页面
public class CallCenterActivity extends Activity {
    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_center);
        webview = (WebView) findViewById(R.id.webview);
        setWebView(Constants.CALL_CENTER);
    }

    private void setWebView(String url) {

        if (url != null) {
            webview.loadUrl(url);
            //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
            webview.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                    view.loadUrl(url);
                    return true;
                }
            });
            //启用支持javascript
            WebSettings settings = webview.getSettings();
            settings.setJavaScriptEnabled(true);

            //优先使用缓存
            webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }
}
