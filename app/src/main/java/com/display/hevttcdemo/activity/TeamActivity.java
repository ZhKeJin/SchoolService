package com.display.hevttcdemo.activity;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.adapter.GeneralAdapter;
import com.display.hevttcdemo.bean.TeamBean;
import com.display.hevttcdemo.utils.LogUtils;
import xyz.zpayh.adapter.OnItemClickListener;

/**
 * @ 创建时间: 2017/10/4 on 22:09.
 * @ 描述：组织社团页面
 * @ 作者: vchao
 */
public class TeamActivity extends BaseActivity {

    @BindView(R.id.rv_team)
    RecyclerView rvTeam;

    private GeneralAdapter mAdapter;
    private ArrayList<TeamBean> teamBeen;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_team;
    }

    @Override
    protected void initView() {
        initTitleBar("社团组织");
    }

    @Override
    protected void initData() {
        mAdapter = new GeneralAdapter();
        teamBeen = new ArrayList<>();
        rvTeam.setLayoutManager(new LinearLayoutManager(TeamActivity.this));

        BmobQuery<TeamBean> query = new BmobQuery<TeamBean>();
        query.order("-time");
        query.setLimit(15);
        query.findObjects(new FindListener<TeamBean>() {
            @Override
            public void done(List<TeamBean> object, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + object.size() + "条数据。");
                    teamBeen.addAll(object);
                    mAdapter.setData(teamBeen);
                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
        rvTeam.setAdapter(mAdapter);
    }

    @Override
    protected void initListener() {
        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull View view, int position) {
                Intent intent = new Intent(TeamActivity.this, TeamDetailActivity.class);
                intent.putExtra("teambean", teamBeen.get(position));
                startActivity(intent);
            }
        });

    }
}
