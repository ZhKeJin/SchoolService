package com.display.hevttcdemo.activity;

import android.app.AlertDialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.display.hevttcdemo.R;
import com.display.hevttcdemo.bean.MyUser;
import com.display.hevttcdemo.bean.Schedule;
import com.display.hevttcdemo.bean.UserInfo;
import com.display.hevttcdemo.jw.service.BroadcastAction;
import com.display.hevttcdemo.utils.LogUtils;
import com.display.hevttcdemo.utils.ToastUtil;
import com.display.hevttcdemo.view.LoadDialog;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class ActionActivity extends BaseActivity {
//    @BindView(R.id.tv_class_name)
//    EditText tvClassName;
//    @BindView(R.id.tv_teacher)
//    EditText tvTeacher;
//    @BindView(R.id.tv_address)
//    EditText tvAddress;

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        Integer lesson = (Integer) getIntent().getSerializableExtra("lesson");
        LogUtils.e(lesson.toString());
        if (lesson != null) {
//            tvAddress.setText(lesson.getAdderss());
//            tvClassName.setText(lesson.getClassName());
//            tvTeacher.setText(lesson.getTeacher());
        }

    }

    public interface OnActionSheetSelected {
        void onClick(int whichButton);
    }

    public ActionActivity() {
    }

    public Dialog showSheet(Context context, Integer pos) {

        final Dialog dlg = new Dialog(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.activity_action, null);
        final int cFullFillWidth = 10000;
        layout.setMinimumWidth(cFullFillWidth);

        TextView mContent = (TextView) layout.findViewById(R.id.content);
        TextView mCancel = (TextView) layout.findViewById(R.id.cancel);
        EditText address = (EditText) layout.findViewById(R.id.tv_address);
        EditText teacher = (EditText) layout.findViewById(R.id.tv_teacher);
        EditText lesson = (EditText) layout.findViewById(R.id.tv_class_name);

        Integer i = pos % 10;
        Integer j = pos / 10;
        LogUtils.e("showSheet......." + pos + "..." + i + "...." + j);


        BmobQuery<Schedule> schedule = new BmobQuery<>();
        final ArrayList<Schedule> schedule1 = new ArrayList<>();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        schedule.addWhereEqualTo("phone", user.getMobilePhoneNumber());
        schedule.addWhereEqualTo("lesson", j.toString());
        schedule.addWhereEqualTo("day", i.toString());
        schedule.findObjects(new FindListener<Schedule>() {
            @Override
            public void done(List<Schedule> list, BmobException e) {
                if (e == null && list.size() != 0) {
                    schedule1.addAll(list);
                    LogUtils.e("查询成功：共" + list.size() + "条数据。" + schedule1.get(0).getAdderss());
                    LogUtils.e("查询成功：共" + list.size() + "条数据。" + list.get(0).getAdderss());
                    address.setText(schedule1.get(0).getAdderss());
                    teacher.setText(schedule1.get(0).getTeacher());
                    lesson.setText(schedule1.get(0).getClassName());
                }
            }
        });

        LogUtils.e("查询成功：共" + schedule1.size() + "条数据。");

        mContent.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtils.e("查询成功：共" + schedule1.size() + "条数据。");

                LogUtils.e("showSheet .... content");

                if (TextUtils.isEmpty(address.getText()) || (TextUtils.isEmpty(teacher.toString())) ||
                        (TextUtils.isEmpty(lesson.toString()))) {
                    ToastUtil.show(ActionActivity.this, "请将信息填写完整", Toast.LENGTH_SHORT);
                    LoadDialog.dismiss(ActionActivity.this);
                    dlg.dismiss();
                    finish();
                    return;
                }

                LogUtils.e("showSheet .... content");
                Schedule schedule = new Schedule();
                schedule.setPhone(user.getMobilePhoneNumber());
                schedule.setAdderss(address.getText().toString());
                schedule.setTeacher(teacher.getText().toString());
                schedule.setClassName(lesson.getText().toString());
                schedule.setDay(i.toString());
                schedule.setLesson(j.toString());
                if (schedule1.size() == 0) {
                    schedule.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {

                            if (e == null) {
                                LogUtils.e("创建数据成功：" + s);
                                ToastUtil.show(ActionActivity.this, "发布成功", Toast.LENGTH_SHORT);
                                LoadDialog.dismiss(ActionActivity.this);
//                                startActivity(new Intent(ActionActivity.this, CourseActivity.class));
                                finish();
                            } else {
                                LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                                ToastUtil.show(ActionActivity.this, "发布失败，请稍后再试", Toast.LENGTH_SHORT);
                                LoadDialog.dismiss(ActionActivity.this);
                            }
                        }
                    });

                } else {
                    LogUtils.e("更新數據");
                    Schedule schedule2 = schedule1.get(0);
                    schedule.update(schedule2.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                LogUtils.e("更新成功");
                                ToastUtil.show(ActionActivity.this, "发布成功", Toast.LENGTH_SHORT);
                                LogUtils.e("创建数据成功：" + schedule2.getObjectId());
                                LoadDialog.dismiss(ActionActivity.this);
//                                startActivity(new Intent(ActionActivity.this, CourseActivity.class));
                                finish();
                            } else {
                                LogUtils.e("更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                ToastUtil.show(ActionActivity.this, "发布失败，请稍后再试", Toast.LENGTH_SHORT);
                                LoadDialog.dismiss(ActionActivity.this);
                            }
                        }
                    });

                }
                dlg.dismiss();
//                startActivity(new Intent(, CourseActivity.class));
                finish();
            }
        });

        mCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LogUtils.e("showSheet .... mCancel");
                // TODO Auto-generated method stub
                dlg.dismiss();
            }
        });

        Window w = dlg.getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        lp.x = 0;
        final int cMakeBottom = 0;
        lp.y = cMakeBottom;
        lp.gravity = Gravity.CENTER;
        dlg.onWindowAttributesChanged(lp);
        dlg.setCanceledOnTouchOutside(false);
        dlg.setContentView(layout);
        dlg.show();

        return dlg;
    }

}
