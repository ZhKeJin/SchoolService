package com.display.hevttcdemo.activity;

import com.stephentuso.welcome.BasicPage;
import com.stephentuso.welcome.TitlePage;
import com.stephentuso.welcome.WelcomeActivity;
import com.stephentuso.welcome.WelcomeConfiguration;

import com.display.hevttcdemo.R;

/**
 * @ 创建时间: 2021/5/23 on 22:09.
 * @ 描述：引导欢迎页面
 * @ 作者：zhangkejin
 */
public class MyWelcomeActivity extends WelcomeActivity {

    @Override
    protected WelcomeConfiguration configuration() {
        return new WelcomeConfiguration.Builder(this)
                .defaultBackgroundColor(R.color.start1)
                .page(new TitlePage(R.mipmap.start2,
                        "掌上西邮  一触即达").background(R.color.start1)
                )
                .page(new BasicPage(R.mipmap.start1,
                        "立足西邮",
                        "聚合校内活动信息").background(R.color.start2)
                )
                .page(new TitlePage(R.mipmap.start3,
                        "丰富资讯   任你阅读").background(R.color.start3)
                )
                .page(new TitlePage(R.mipmap.start4,
                        "西邮有约   与你相约").background(R.color.start4)
                )
                .swipeToDismiss(true)
                .build();
    }
}
