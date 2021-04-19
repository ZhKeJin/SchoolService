package com.display.hevttcdemo.fragment;

import android.content.Intent;
import android.os.SystemClock;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.activity.BuyDetailActivity;
import com.display.hevttcdemo.adapter.GeneralAdapter;
import com.display.hevttcdemo.bean.BuyItem;
import com.display.hevttcdemo.utils.LogUtils;
import com.display.hevttcdemo.utils.ToastUtil;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * @ 创建时间: 2021/9/14 on 14:29.
 * @ 描述：二手交易 【换点银子】
 * 用户想要出手二手物品， 展示买的条目列表
 * @ 作者：zhangkejin
 */
public class SecondSaleFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spl_main_news)
    SwipeRefreshLayout splMainNews;

    private ArrayList<BuyItem> buyBeen;
    private GeneralAdapter mAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        mAdapter = new GeneralAdapter();
        buyBeen = new ArrayList<>();
        BmobQuery<BuyItem> query = new BmobQuery<BuyItem>();
        query.order("-createdAt");
        //返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<BuyItem>() {
            @Override
            public void done(List<BuyItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    buyBeen.addAll(object);
                    mAdapter.setData(buyBeen);
                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), BuyDetailActivity.class);
                intent.putExtra("buybean", buyBeen.get(position));
                startActivity(intent);
            }
        });
        splMainNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                TODO 网络请求数据（现在是假刷新）

//                1.5秒 后停止刷新操作
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 停止刷新操作
                                splMainNews.setRefreshing(false);
                                ToastUtil.showShort("没有更多数据了！");
                            }
                        });
                    }
                }).start();

            }
        });

    }
}