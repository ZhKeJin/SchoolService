package com.display.hevttcdemo.activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.display.hevttcdemo.R;
import com.display.hevttcdemo.adapter.MyPagerAdapter;
import com.display.hevttcdemo.constant.Config;
import com.display.hevttcdemo.fragment.SecondBuyFragment;
import com.display.hevttcdemo.fragment.SecondSaleFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import cn.hugeterry.coordinatortablayout.CoordinatorTabLayout;

/**
 * 二手交易页面
 */
public class BuyActivity extends BaseAppCompatActivity {

    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.coordinatortablayout)
    CoordinatorTabLayout mCoordinatorTabLayout;

    public String[] mTitles = {Config.MODULE_NAME_BUY, Config.MODULE_NAME_SALE};
    private ArrayList<Fragment> mFragments;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_lose;
    }

    @Override
    protected void initView() {
        initFragments();
        initViewPager();

        int[] mImageArray = new int[]{
                R.mipmap.img_bg_buy,
                R.mipmap.img_bg_sale};
        int[] mColorArray = new int[]{
                android.R.color.holo_blue_light,
                android.R.color.holo_red_light};

        mCoordinatorTabLayout.setTransulcentStatusBar(this)
                .setTitle("二手交易")
                .setBackEnable(true)
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);
    }

    private void initViewPager() {
        mViewPager.setOffscreenPageLimit(4);
        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new SecondBuyFragment());
        mFragments.add(new SecondSaleFragment());
    }

    @OnClick(R.id.iv_add_lose)
    public void onViewClicked() {
        Intent intent = new Intent(BuyActivity.this, BuyAddActivity.class);
        startActivity(intent);
    }
}