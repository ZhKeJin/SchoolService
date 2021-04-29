package com.display.hevttcdemo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.RequiresApi;

import com.display.hevttcdemo.bean.MyUser;
import com.display.hevttcdemo.bean.Schedule;
import com.display.hevttcdemo.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.display.hevttcdemo.R;
import com.display.hevttcdemo.adapter.WeekDayAdapter;
//import com.display.hevttcdemo.jw.bean.Course;
//import com.display.hevttcdemo.jw.bean.CourseTable;
//import com.display.hevttcdemo.jw.service.BroadcastAction;
//import com.display.hevttcdemo.widget.CourseWidgetProvider;

import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * 课表主界面，显示课表内容
 * Created by FatCat on 2016/10/3.
 */
public class CourseActivity extends BaseActivity {

    /**
     * 课程表
     */
//    private CourseTable mCourseTable;

    /**
     * 当前周
     */
    private int mCurrWeek;

    /**
     * 选中的周数
     */
    private int mSelectWeek;

    /**
     * 选择周数的下拉框
     */
    private Spinner mWeekDaySpinner;

    /**
     * 下拉框的适配器
     */
    private WeekDayAdapter mAdapter;

    /**
     * 周数下拉框的数据
     */
    private ArrayList<String> mWeekArr;

    /**
     * 记录课程的map
     */
//    private HashMap<String, Course> mCourseMap;

    /**
     * 记录课程背景颜色的map
     */
    private HashMap<String, Integer> mBgColorMap;


    /**
     * 课程页面的button引用，6行7列
     */
    private int[][] lessons = {
            {R.id.lesson11, R.id.lesson12, R.id.lesson13, R.id.lesson14, R.id.lesson15, R.id.lesson16, R.id.lesson17},
            {R.id.lesson21, R.id.lesson22, R.id.lesson23, R.id.lesson24, R.id.lesson25, R.id.lesson26, R.id.lesson27},
            {R.id.lesson31, R.id.lesson32, R.id.lesson33, R.id.lesson34, R.id.lesson35, R.id.lesson36, R.id.lesson37},
            {R.id.lesson41, R.id.lesson42, R.id.lesson43, R.id.lesson44, R.id.lesson45, R.id.lesson46, R.id.lesson47},
            {R.id.lesson51, R.id.lesson52, R.id.lesson53, R.id.lesson54, R.id.lesson55, R.id.lesson56, R.id.lesson57},
            {R.id.lesson61, R.id.lesson62, R.id.lesson63, R.id.lesson64, R.id.lesson65, R.id.lesson66, R.id.lesson67}
    };

    /**
     * 某节课的背景图,用于随机获取
     */
    private int[] bg = {
            R.drawable.kb1, R.drawable.kb2, R.drawable.kb3, R.drawable.kb4, R.drawable.kb5,
            R.drawable.kb6, R.drawable.kb7, R.drawable.kb8, R.drawable.kb9, R.drawable.kb10,
            R.drawable.kb11, R.drawable.kb12, R.drawable.kb13, R.drawable.kb14, R.drawable.kb15,
            R.drawable.kb16, R.drawable.kb17, R.drawable.kb18, R.drawable.kb19, R.drawable.kb20,
            R.drawable.kb21, R.drawable.kb22, R.drawable.kb23, R.drawable.kb24, R.drawable.kb25
    };


    private ArrayList<Schedule> newsBeanList;


    @Override
    protected void initView() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        unregisterReceiver(mCourseBroadcast);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_course;
    }

    @Override
    protected void initData() {
        //填充数据
        newsBeanList = new ArrayList<>();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        BmobQuery<Schedule> schedule = new BmobQuery<>();
        schedule.order("day, lesson");
        schedule.addWhereEqualTo("phone", user.getMobilePhoneNumber());

        schedule.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> list, BmobException e) {
                if (e == null) {
                    LogUtils.e("schedule        " + list.size());

                    for (Schedule schedule1 : list) {
                        LogUtils.e("schedule" + schedule1.toString());
                        String newText = schedule1.getClassName() + "\n" + schedule1.getAdderss();
                        int i = Integer.parseInt(schedule1.getDay());
                        int j = Integer.parseInt(schedule1.getLesson());

                        Button btn = (Button) findViewById(lessons[j-1][i-1]);
                        btn.setText(newText);
                        btn.setTextColor(Color.WHITE);
                        btn.setBackgroundResource(bg[6]);

                    }
                    LogUtils.e("查询成功：共" + list.size() + "条数据。");
                } else {
                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });
    }

    //   事件处理
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @OnClick({R.id.lesson17, R.id.lesson11, R.id.lesson12, R.id.lesson13, R.id.lesson14, R.id.lesson15, R.id.lesson16,
            R.id.lesson27, R.id.lesson21, R.id.lesson22, R.id.lesson23, R.id.lesson24, R.id.lesson25, R.id.lesson26,
            R.id.lesson37, R.id.lesson31, R.id.lesson32, R.id.lesson33, R.id.lesson34, R.id.lesson35, R.id.lesson36,
            R.id.lesson47, R.id.lesson41, R.id.lesson42, R.id.lesson43, R.id.lesson44, R.id.lesson45, R.id.lesson46,
            R.id.lesson57, R.id.lesson51, R.id.lesson52, R.id.lesson53, R.id.lesson54, R.id.lesson55, R.id.lesson56,
            R.id.lesson67, R.id.lesson61, R.id.lesson62, R.id.lesson63, R.id.lesson64, R.id.lesson65, R.id.lesson66})
    public void onClick(Button view) {
        Integer i = Integer.parseInt(view.getTag().toString());
        LogUtils.e("oncli"+view.getTag().toString());

        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("lesson", i);
        intent.putExtras(bundle);
        ActionActivity ac = new ActionActivity();
        ac.showSheet(CourseActivity.this, i);
    }


}





































