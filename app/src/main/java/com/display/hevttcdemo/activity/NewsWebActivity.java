package com.display.hevttcdemo.activity;

import android.os.Build;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;

import com.bumptech.glide.Glide;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.bean.NewsBean;
import com.display.hevttcdemo.utils.LogUtils;
import com.display.hevttcdemo.utils.ToastUtil;

public class NewsWebActivity extends BaseActivity {
    @BindView(R.id.iv_news_web_logo)
    ImageView ivTeamDetailLogo;
    @BindView(R.id.tv_news_web_title)
    TextView tvTeamDetailTitle;
    @BindView(R.id.tv_news_web_time)
    TextView tvTeamDetailTime;
    @BindView(R.id.tv_news_web_content)
    TextView tvTeamDetailContent;
    private NewsBean newsBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news_web;
    }

    @Override
    protected void initView() {
        newsBean = (NewsBean) getIntent().getSerializableExtra("newsbean");
    }

    @Override
    protected void initData() {
        LogUtils.e("[initData]"+newsBean);
        tvTeamDetailTitle.setText(newsBean.getTitle());
        tvTeamDetailTime.setText(newsBean.getTime());
        tvTeamDetailContent.setText(newsBean.getContent());
        if (newsBean.getPic1() != null) {
            Glide.with(NewsWebActivity.this)                             //配置上下文
                    .load(newsBean.getPic1())      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                    .error(R.mipmap.default_image)           //设置错误图片
                    .placeholder(R.mipmap.default_image)     //设置占位图片
                    .fitCenter()
                    .into(ivTeamDetailLogo);
        }

    }

//    @BindView(R.id.wv_news)
//    WebView wvNews;
//
//    @Override
//    protected int getLayoutId() {
//        return R.layout.activity_news_web;
//    }
//
//    @Override
//    protected void initData() {
//        String url = getIntent().getStringExtra("url");
//        if (TextUtils.isEmpty(url)) {
//            ToastUtil.showShort("该新闻已经失效！");
//            return;
//        }
//        MyWebViewClient myWebViewClient = new MyWebViewClient();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            wvNews.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//        }
//        wvNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//设置js可以直接打开窗口，如window.open()，默认为false
//        wvNews.getSettings().setJavaScriptEnabled(true);//是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
//        wvNews.getSettings().setSupportZoom(true);//是否可以缩放，默认true
//        wvNews.getSettings().setBuiltInZoomControls(true);//是否显示缩放按钮，默认false
//        wvNews.getSettings().setUseWideViewPort(true);//设置此属性，可任意比例缩放。大视图模式
//        wvNews.getSettings().setLoadWithOverviewMode(true);//和setUseWideViewPort(true)一起解决网页自适应问题
//        wvNews.getSettings().setAppCacheEnabled(true);//是否使用缓存
//        wvNews.getSettings().setDomStorageEnabled(true);//DOM Storage
//        wvNews.setWebChromeClient(new WebChromeClient());
//        wvNews.setWebViewClient(myWebViewClient);
//        wvNews.loadUrl(url);
//
//    }
//
//    private class MyWebViewClient extends WebViewClient {
//        //重写父类方法，让新打开的网页在当前的WebView中显示
//        public boolean shouldOverrideUrlLoading(WebView view, String url) {
//            view.loadUrl(url);
//            return true;
//        }
//
//        @Override
//        public void onPageFinished(WebView view, String url) {
//            super.onPageFinished(view, url);
//        }
//    }
}
