package com.display.hevttcdemo.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.utils.DateUtil;
import com.display.hevttcdemo.utils.TimeUtil;

/**
 * @ 创建时间: 2017/10/3 on 12:49.
 * @ 描述：校历页面
 * @ 作者: vchao
 */
public class XiaoliActivity extends BaseActivity {

    @BindView(R.id.tv_date_today)
    TextView tvDateToday;
    @BindView(R.id.tv_date_today_nongli)
    TextView tvDateTodayNongli;
    @BindView(R.id.tv_xiaoxi_tips)
    TextView tvXiaoxiTips;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_xiaoli;
    }

    @Override
    protected void initView() {
        initTitleBar("校历");
    }

    @Override
    protected void initData() {
        String lunar = DateUtil.getChnieseDate(TimeUtil.getYear(), TimeUtil.getMonth(), TimeUtil.getDate());
        tvDateTodayNongli.setText(lunar);
        String date = TimeUtil.getCurrentTimeInString(TimeUtil.DATE_FORMAT_DATE);
        tvDateToday.setText(date);
    }

    @OnClick(R.id.tv_xiaoxi_tips)
    public void onViewClicked() {
        View viewdialog = LayoutInflater.from(XiaoliActivity.this).inflate(R.layout.dialog_xiaoli_tips, null);
        AlertDialog dialog = new AlertDialog.Builder(XiaoliActivity.this)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setView(viewdialog)
                .create();
        dialog.show();
    }
}
