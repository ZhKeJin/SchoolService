package com.display.hevttcdemo.activity;


import com.display.hevttcdemo.R;

/**
 * @ 创建时间: 2021/6/18 on 17:12.
 * @ 描述：关于界面
 * @ 作者：zhangkejin
 */
public class AboutActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        initTitleBar("关于");
    }
}
