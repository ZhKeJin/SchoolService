package com.display.hevttcdemo.activity;

import android.view.KeyEvent;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.display.hevttcdemo.R;
import com.display.hevttcdemo.fragment.TabHomeFragment;
import com.display.hevttcdemo.fragment.TabMyFragment;
import com.display.hevttcdemo.fragment.TabNewsFragment;
import com.display.hevttcdemo.utils.ToastUtil;
import com.display.hevttcdemo.view.BottomTabView;

/**
 * @ 创建时间: 2021/5/17 on 11:31.
 * @ 描述：主界面 Activity
 * @ 作者：zhangkejin
 */

public class HomeActivity extends BottomTabBaseActivity {
    private long mExitTime;

    @Override
    protected List<BottomTabView.TabItemView> getTabViews() {
        List<BottomTabView.TabItemView> tabItemViews = new ArrayList<>();
        tabItemViews.add(new BottomTabView.TabItemView(this, "首页", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.main_home_nor, R.mipmap.main_home_pre));
        tabItemViews.add(new BottomTabView.TabItemView(this, "新闻", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.main_buy_nor, R.mipmap.main_buy_pre));
        tabItemViews.add(new BottomTabView.TabItemView(this, "我的", R.color.colorPrimary,
                R.color.colorAccent, R.mipmap.main_user_nor, R.mipmap.main_user_pre));
        return tabItemViews;
    }

    @Override
    protected List<Fragment> getFragments() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TabHomeFragment());
        fragments.add(new TabNewsFragment());
        fragments.add(new TabMyFragment());
        return fragments;
    }

    /**
     * 重写返回键返回方法，防止误触退出
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtil.showShort("再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
