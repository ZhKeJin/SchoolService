package com.display.hevttcdemo.activity;

import butterknife.OnClick;
import com.display.hevttcdemo.R;

public class PushOKActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_push_ok;
    }

    @Override
    protected void initView() {
        initTitleBar("发布成功");
    }

    @OnClick(R.id.btn_ok_back)
    public void onViewClicked() {
        onBackPressed();
    }
}
