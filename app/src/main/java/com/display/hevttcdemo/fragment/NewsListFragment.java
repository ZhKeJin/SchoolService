package com.display.hevttcdemo.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.activity.NewsWebActivity;
import com.display.hevttcdemo.adapter.GeneralAdapter;
import com.display.hevttcdemo.bean.NewsBean;
import com.display.hevttcdemo.utils.LogUtils;
import com.display.hevttcdemo.utils.ToastUtil;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * @ 创建时间: 2021/10/3 on 12:49.
 * @ 描述：新闻列表页面
 * @ 作者：zhangkejin
 */
public class NewsListFragment extends BaseFragment {

    @BindView(R.id.recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.spl_main_news)
    SwipeRefreshLayout splMainNews;

    private GeneralAdapter mAdapter;
    private ArrayList<NewsBean> newsBeanList;
    private int refreshCount = 1;

    private static final String BUNDLE_TITLE_TRYPE = "titleType";
    private String mTitleType;

    public static NewsListFragment getInstance(String title) {
        NewsListFragment fra = new NewsListFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE_TRYPE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    public void getPreIntent() {
        mTitleType = getArguments().getString(BUNDLE_TITLE_TRYPE);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                LogUtils.e(newsBeanList.get(position).getContent());
                Intent intent = new Intent(getActivity(), NewsWebActivity.class);
                intent.putExtra("url", newsBeanList.get(position).getContent());
                startActivity(intent);
            }
        });
        splMainNews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        SystemClock.sleep(1500);
                        BmobQuery<NewsBean> query = new BmobQuery<NewsBean>();
                        query.order("-time");
                        query.setLimit(10);
                        query.addWhereEqualTo("tag", mTitleType);
                        LogUtils.e("类型：" + mTitleType);
                        query.setSkip(10 * refreshCount);
                        //执行查询方法
                        query.findObjects(new FindListener<NewsBean>() {
                            @Override
                            public void done(List<NewsBean> object, BmobException e) {
                                if (e == null) {
                                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                                    refreshCount++;
                                    if (object.size() == 0) {
                                        ToastUtil.showShort("暂无更多数据");
                                    } else {
                                        for (NewsBean newsBean : object) {
                                            newsBeanList.add(0, newsBean);
                                            LogUtils.e(newsBean.getAuthor());
                                        }
                                        mAdapter.setData(newsBeanList);
                                        //得到adapter.然后刷新
                                        mRecyclerView.getAdapter().notifyDataSetChanged();
                                        ToastUtil.showShort("刷新成功");
                                    }
                                    //停止刷新操作
                                    splMainNews.setRefreshing(false);
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

    @Override
    protected void initData() {
        mAdapter = new GeneralAdapter();
        newsBeanList = new ArrayList<>();
        BmobQuery<NewsBean> query = new BmobQuery<NewsBean>();
        query.order("-time");
        query.addWhereEqualTo("tag", mTitleType);
        LogUtils.e("类型：" + mTitleType);

        query.setLimit(10);
        query.findObjects(new FindListener<NewsBean>() {
            @Override
            public void done(List<NewsBean> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    newsBeanList.addAll(object);
                    mAdapter.setData(newsBeanList);
                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

}