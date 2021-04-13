package com.display.hevttcdemo.activity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.adapter.GeneralAdapter;
import com.display.hevttcdemo.bean.PhotoBean;
import com.display.hevttcdemo.utils.LogUtils;
import com.display.hevttcdemo.utils.RomUtils;
import com.display.hevttcdemo.utils.ToastUtil;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * @ 创建时间: 2017/10/4 on 22:09.
 * @ 描述：图说校园
 * @ 作者: vchao
 */
public class PhotoSchoolActivity extends BaseActivity {

    @BindView(R.id.rv_my)
    RecyclerView mRecyclerView;
    @BindView(R.id.spl_main)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private List<PhotoBean> mStaggerData;

    private GeneralAdapter photoAdapter;
    private int refreshCount = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_photo_school;
    }

    @Override
    protected void initView() {
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
    }

    protected void initData() {
        photoAdapter = new GeneralAdapter();
        mStaggerData = new ArrayList<PhotoBean>();
        BmobQuery<PhotoBean> query = new BmobQuery<PhotoBean>();
        query.order("id");
        //返回10条数据，默认返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<PhotoBean>() {
            @Override
            public void done(List<PhotoBean> object, BmobException e) {
                if (e == null) {
                    mStaggerData.addAll(object);
                    photoAdapter.setData(mStaggerData);
                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        mRecyclerView.setAdapter(photoAdapter);

        initRefresh();
    }

    @Override
    protected void initListener() {
        photoAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                LogUtils.e("onItemClick: " + position);
                Intent intent = new Intent(PhotoSchoolActivity.this, PicDetailActivity.class);
                intent.putExtra("bean", mStaggerData.get(position));
                if (RomUtils.isAndroid5()) {
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(PhotoSchoolActivity.this,
                            view.findViewById(R.id.iv_icon),
                            "shareView").toBundle();
                    startActivity(intent, bundle);
                } else {
                    startActivity(intent);
                }
            }
        });
    }

    /**
     * 刷新
     */
    private void initRefresh() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        BmobQuery<PhotoBean> query = new BmobQuery<PhotoBean>();
                        query.order("id");
                        query.setLimit(10);
                        query.setSkip(10 * refreshCount);
                        //执行查询方法
                        query.findObjects(new FindListener<PhotoBean>() {
                            @Override
                            public void done(List<PhotoBean> object, BmobException e) {
                                if (e == null) {
                                    refreshCount++;
                                    Log.e("zwc", "查询成功：共" + object.size() + "条数据。");
                                    if (object.size() == 0) {
                                        ToastUtil.show(PhotoSchoolActivity.this, "暂无更多数据", Toast.LENGTH_SHORT);
                                    } else {
                                        for (PhotoBean bean : object) {
                                            mStaggerData.add(0, bean);
                                        }
                                        photoAdapter.setData(mStaggerData);
                                        //得到adapter.然后刷新
                                        mRecyclerView.getAdapter().notifyDataSetChanged();
                                    }
                                    //停止刷新操作
                                    mSwipeRefreshLayout.setRefreshing(false);

                                } else {
                                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                                }
                            }
                        });
                    }
                }).start();
            }
        });
    }

}