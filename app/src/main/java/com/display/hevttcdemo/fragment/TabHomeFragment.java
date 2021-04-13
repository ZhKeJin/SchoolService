package com.display.hevttcdemo.fragment;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.display.hevttcdemo.R;
import com.display.hevttcdemo.activity.BuyActivity;
import com.display.hevttcdemo.activity.ContactsActivity;
import com.display.hevttcdemo.activity.CourseActivity;
import com.display.hevttcdemo.activity.LoseActivity;
import com.display.hevttcdemo.activity.LoveActivity;
import com.display.hevttcdemo.activity.PhotoSchoolActivity;
import com.display.hevttcdemo.activity.TeamActivity;
import com.display.hevttcdemo.activity.WebActivity;
import com.display.hevttcdemo.activity.XiaoliActivity;
import com.display.hevttcdemo.adapter.GeneralAdapter;
import com.display.hevttcdemo.bean.ModuleItem;
import com.display.hevttcdemo.constant.MyUrl;
import com.display.hevttcdemo.utils.GlideImageLoader;
import com.youth.banner.Banner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * @ 创建时间: 2017/7/21 on 19:39.
 * @ 描述：主页面fragment
 * @ 作者: vchao
 */
public class TabHomeFragment extends BaseFragment {

    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.rv_main)
    RecyclerView mRecyclerView;

    private GeneralAdapter moduleAdapter;
    private ArrayList<ModuleItem> moduleList = new ArrayList<>();
    //首页轮播图   图片资源
    List<Integer> bannerImages = Arrays.asList(R.mipmap.img_keshi1, R.mipmap.img_keshi2,
            R.mipmap.img_keshi3, R.mipmap.img_keshi4);

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        if (moduleList.size() > 0) {
            return;
        }
        initBanner();
        initModuleList();
    }

    private void initModuleList() {
        moduleList.add(new ModuleItem(R.mipmap.ic_home_loveshow, "表白墙", LoveActivity.class));
        moduleList.add(new ModuleItem(R.mipmap.ic_home_tellall, "校园街景", MyUrl.URL_QQMAP, WebActivity.class));
        moduleList.add(new ModuleItem(R.mipmap.ic_home_schooldate, "校历", XiaoliActivity.class));
        moduleList.add(new ModuleItem(R.mipmap.ic_home_schoolguide, "学校官网", MyUrl.URL_HEVTTC, WebActivity.class));

        moduleList.add(new ModuleItem(R.mipmap.ic_home_numbernote, "校园通讯录", ContactsActivity.class));
        moduleList.add(new ModuleItem(R.mipmap.ic_home_buysale, "图说校园", PhotoSchoolActivity.class));
        moduleList.add(new ModuleItem(R.mipmap.ic_home_friends, "社团组织", TeamActivity.class));

        moduleAdapter = new GeneralAdapter();
        moduleAdapter.setData(moduleList);
        mRecyclerView.setAdapter(moduleAdapter);
    }

    private void initBanner() {
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        banner.setDelayTime(3500);//设置轮播时间
        banner.setImages(bannerImages);//设置图片集合
        banner.start();//banner设置方法全部调用完毕时最后调用
    }

    @Override
    protected void initListener() {
        moduleAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int positon) {
                ModuleItem item = moduleList.get(positon);
                //跳转到对应功能的详情页面
                Intent intent = new Intent(mActivity, item.getClazz());
                if (!TextUtils.isEmpty(item.getWebUrl())) {
                    intent.putExtra("url", item.getWebUrl());
                }
                startActivity(intent);
            }
        });
    }

    @OnClick({R.id.home_moudle1, R.id.home_moudle2, R.id.home_moudle3, R.id.home_moudle4})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.home_moudle1:
                intent = new Intent(mActivity, CourseActivity.class);
                startActivity(intent);
                break;
            case R.id.home_moudle2:
                intent = new Intent(mActivity, WebActivity.class);
                intent.putExtra("url", MyUrl.URL_Libray);
                startActivity(intent);
                break;
            case R.id.home_moudle3:
                intent = new Intent(mActivity, LoseActivity.class);
                startActivity(intent);
                break;
            case R.id.home_moudle4:
                intent = new Intent(mActivity, BuyActivity.class);
                startActivity(intent);
                break;
        }
    }

}
