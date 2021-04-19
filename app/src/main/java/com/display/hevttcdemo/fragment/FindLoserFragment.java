package com.display.hevttcdemo.fragment;

import android.content.Intent;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.activity.LoseDetailActivity;
import com.display.hevttcdemo.adapter.GeneralAdapter;
import com.display.hevttcdemo.bean.LoseItem;
import com.display.hevttcdemo.utils.LogUtils;
import com.display.hevttcdemo.utils.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * @ 创建时间: 2021/9/14 on 15:29.
 * @ 描述：【失物招领】
 * 捡东西了 ==> 寻找失主 ==> 看谁丢了 ===>  展示 丢东西的人 列表
 * @ 作者：zhangkejin
 */
public class FindLoserFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spl_main_news)
    SwipeRefreshLayout splMainNews;

    private GeneralAdapter LoseAdapter;
    private ArrayList<LoseItem> LoseBeen;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initData() {
        LoseAdapter = new GeneralAdapter();
        LoseBeen = new ArrayList<>();

        BmobQuery<LoseItem> query = new BmobQuery<LoseItem>();
        query.order("-time");
        //返回10条数据
        query.setLimit(10);
        //执行查询方法
        query.findObjects(new FindListener<LoseItem>() {
            @Override
            public void done(List<LoseItem> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    LoseBeen.addAll(object);
                    LoseAdapter.setData(LoseBeen);
                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        mRecyclerView.setAdapter(LoseAdapter);
    }

    @Override
    protected void initListener() {
        LoseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                Intent intent = new Intent(getActivity(), LoseDetailActivity.class);
                intent.putExtra("losebean", LoseBeen.get(position));
                startActivity(intent);
            }
        });

        splMainNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                TODO 网络请求数据（现在是假刷新）

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);

                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //停止刷新操作
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