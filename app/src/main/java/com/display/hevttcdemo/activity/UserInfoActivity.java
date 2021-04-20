package com.display.hevttcdemo.activity;

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.display.hevttcdemo.bean.NewsBean;
import com.display.hevttcdemo.bean.UserInfo;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.helper.ErrorCode;
import cn.bmob.v3.listener.FetchUserInfoListener;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Response;

import com.display.hevttcdemo.R;
import com.display.hevttcdemo.bean.JsonRealBean;
import com.display.hevttcdemo.bean.MyUser;
import com.display.hevttcdemo.constant.MyUrl;
import com.display.hevttcdemo.constant.SPkey;
import com.display.hevttcdemo.utils.CommonUtil;
import com.display.hevttcdemo.utils.LogUtils;
import com.display.hevttcdemo.utils.SPUtils;
import com.display.hevttcdemo.utils.ToastUtil;
import com.display.hevttcdemo.view.LoadDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * @ 创建时间: 2021/5/23 on 22:09.
 * @ 描述：用户信息页面
 * @ 作者：zhangkejin
 */
public class UserInfoActivity extends BaseActivity {

    @BindView(R.id.tv_user_info_name)
    EditText tvUserInfoName;
    @BindView(R.id.tv_user_info_sex)
    EditText tvUserInfoSex;
    @BindView(R.id.tv_user_info_stuno)
    EditText tvUserInfoStuno;
    @BindView(R.id.tv_user_info_tel)
    TextView tvUserInfoTel;
    @BindView(R.id.tv_user_info_yuan)
    EditText tvUserInfoYuan;
    @BindView(R.id.tv_user_info_class)
    EditText tvUserInfoClass;
    @BindView(R.id.tv_user_info_created)
    TextView tvUserInfoCreated;
    @BindView(R.id.ll_user_info_sex)
    LinearLayout llUserInfoSex;
    @BindView(R.id.ll_user_info_stuno)
    LinearLayout llUserInfoStuno;
    @BindView(R.id.ll_user_info_yuan)
    LinearLayout llUserInfoYuan;
    @BindView(R.id.ll_user_info_class)
    LinearLayout llUserInfoClass;
    @BindView(R.id.bt_real_name)
    Button btRealName;

    private String userInfoName;
    private String userInfoSex;
    private String userInfoStuno;
    private String userInfoYuan;
    private String userInfoClass;

    private ArrayList<UserInfo> userLists;
    private UserInfo userInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void initData() {
        userLists = new ArrayList<>();
        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        tvUserInfoTel.setText(user.getMobilePhoneNumber());
        tvUserInfoCreated.setText(user.getCreatedAt());

        //查询是否有录入数据
        BmobQuery<UserInfo> query = new BmobQuery<>();
        LogUtils.e("phone:" + user.getMobilePhoneNumber());
        query.addWhereEqualTo("phoneNum", user.getMobilePhoneNumber());
        query.setLimit(1);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + list.size() + "条数据。" + list.get(0));
                    UserInfo userInfo = list.get(0);
                    LogUtils.e("userLists:" + list.get(0));
                    tvUserInfoName.setText(userInfo.getName());
                    tvUserInfoSex.setText(userInfo.getSex());
                    tvUserInfoStuno.setText(userInfo.getStuno());
                    tvUserInfoYuan.setText(userInfo.getYuan());
                    tvUserInfoClass.setText(userInfo.getClazz());
//                    btRealName.setText(user.getName());
                } else {
                    LogUtils.e("userLists length 0");
                    tvUserInfoName.setText("");
                    tvUserInfoSex.setText("");
                    tvUserInfoStuno.setText("");
                    tvUserInfoYuan.setText("");
                    tvUserInfoClass.setText("");
                    LogUtils.e("没有数据：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });

        LogUtils.e("查询成功：共" + userLists.size() + "条数据。");

//        if (userLists.size() != 0) {
//
//        } else {
//            LogUtils.e("userLists length 0");
//            tvUserInfoName.setText("");
//            tvUserInfoSex.setText("");
//            tvUserInfoStuno.setText("");
//            tvUserInfoYuan.setText("");
//            tvUserInfoClass.setText("");
//        }
    }

    @OnClick(R.id.bt_real_name)
    public void onViewClicked() {
        userInfoName = tvUserInfoName.getText().toString().trim();
        userInfoSex = tvUserInfoSex.getText().toString().trim();
        userInfoStuno = tvUserInfoStuno.getText().toString().trim();
        userInfoYuan = tvUserInfoYuan.getText().toString().trim();
        userInfoClass = tvUserInfoClass.getText().toString().trim();
        if (!TextUtils.isEmpty(userInfoName) && (!TextUtils.isEmpty(userInfoSex)) &&
                (!TextUtils.isEmpty(userInfoStuno)) && (!TextUtils.isEmpty(userInfoYuan))
                && (!TextUtils.isEmpty(userInfoClass))) {

            //上傳數據
            BmobUpUser(false);
        } else {
            ToastUtil.showShort("请将信息填写完整！");
        }
    }

    private void BmobUpUser(boolean b) {
        LoadDialog.show(UserInfoActivity.this, "上传信息中...");
        LogUtils.e("开始上传……");

        //查询是否有录入数据
        BmobQuery<UserInfo> query = new BmobQuery<>();
        userInfo = new UserInfo();

        MyUser user = BmobUser.getCurrentUser(MyUser.class);
        LogUtils.e("phone:" + user.getMobilePhoneNumber());
        query.addWhereEqualTo("phoneNum", user.getMobilePhoneNumber());
        query.setLimit(1);
        query.findObjects(new FindListener<UserInfo>() {
            @Override
            public void done(List<UserInfo> list, BmobException e) {
                if (e == null) {
                    LogUtils.e("查询成功：共" + list.size() + "条数据。" + list.get(0));
                    userInfo = list.get(0);
                    LogUtils.e("userLists:" + list.get(0));

                    UserInfo myUser = new UserInfo();
                    myUser.setPhoneNum(user.getMobilePhoneNumber());
                    myUser.setName(userInfoName);
                    myUser.setSex(userInfoSex);
                    myUser.setYuan(userInfoYuan);
                    myUser.setClazz(userInfoClass);
                    myUser.setStuno(userInfoStuno);

                    if (!list.get(0).getPhoneNum().equals("")) {
                        //update
                        LogUtils.e("更新數據");
                        LogUtils.e("更新數據"+list.get(0).getObjectId());
                        myUser.update(list.get(0).getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    LogUtils.e("更新成功");
                                    ToastUtil.show(UserInfoActivity.this, "发布成功", Toast.LENGTH_SHORT);
                                    LogUtils.e("创建数据成功：" + list.get(0).getObjectId());
                                    LoadDialog.dismiss(UserInfoActivity.this);
                                    startActivity(new Intent(UserInfoActivity.this, UserInfoActivity.class));
                                    finish();
                                } else {
                                    LogUtils.e("更新失败：" + e.getMessage() + "," + e.getErrorCode());
                                    ToastUtil.show(UserInfoActivity.this, "发布失败，请稍后再试", Toast.LENGTH_SHORT);
                                    LoadDialog.dismiss(UserInfoActivity.this);
                                }
                            }
                        });

                    } else {
                        //create
                        myUser.save(new SaveListener<String>() {
                            @Override
                            public void done(String objectId, BmobException e) {
                                if (e == null) {
                                    ToastUtil.show(UserInfoActivity.this, "发布成功", Toast.LENGTH_SHORT);
                                    LogUtils.e("创建数据成功：" + objectId);
                                    LoadDialog.dismiss(UserInfoActivity.this);
                                    startActivity(new Intent(UserInfoActivity.this, UserInfoActivity.class));
                                    finish();
                                } else {
                                    LogUtils.e("失败：" + e.getMessage() + "," + e.getErrorCode());
                                    ToastUtil.show(UserInfoActivity.this, "发布失败，请稍后再试", Toast.LENGTH_SHORT);
                                    LoadDialog.dismiss(UserInfoActivity.this);
                                }
                            }
                        });
                    }
                } else {
                    LogUtils.e("没有数据：" + e.getMessage() + "," + e.getErrorCode());
                }
            }
        });


    }


    //    /**
//     * 实名认证
//     */
//    private void RealName() {
//        View view = LayoutInflater.from(UserInfoActivity.this).inflate(R.layout.dialog_reallyname, null);
//        final EditText et_real_stuno = (EditText) view.findViewById(R.id.et_real_stuno);
//        final EditText et_real_name = (EditText) view.findViewById(R.id.et_real_name);
//
//        AlertDialog dialog = new AlertDialog.Builder(UserInfoActivity.this)
//                .setNegativeButton("取消", null)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        String name = CommonUtil.getText(et_real_name);
//                        String stuno = CommonUtil.getText(et_real_stuno);
//                        if (CommonUtil.isAllNotNull(et_real_name, et_real_stuno)) {
//                            UpdateReal(name, stuno);
//                        } else {
//                            ToastUtil.showShort("请填写完整信息");
//                        }
//                    }
//                })
//                .setTitle("实名认证")
//                .setIcon(R.mipmap.ic_app_logo)
//                .setView(view)
//                .create();
//        dialog.show();
//    }
//
//    private void UpdateReal(String name, final String stuno) {
//        LoadDialog.show(UserInfoActivity.this, "实名认证中");
//        OkGo.get(MyUrl.URL_QUERY)
//                .params("stuname", name)
//                .execute(new StringCallback() {
//                    @Override
//                    public void onSuccess(String s, Call call, Response response) {
//                        LogUtils.e("onSuccess: " + s);
//                        JsonRealBean jsonRealBean = new Gson().fromJson(s, JsonRealBean.class);
//
//                        for (int i = 0; i < jsonRealBean.getList().size(); i++) {
//                            if (TextUtils.equals(stuno, jsonRealBean.getList().get(i).getStu_no() + "")) {
//                                tvUserInfoName.setText(jsonRealBean.getList().get(i).getName());
//                                tvUserInfoSex.setText(jsonRealBean.getList().get(i).getSex());
//                                tvUserInfoStuno.setText(jsonRealBean.getList().get(i).getStu_no());
//                                tvUserInfoYuan.setText(jsonRealBean.getList().get(i).getYuanxi());
//                                tvUserInfoClass.setText(jsonRealBean.getList().get(i).getZhuanye());
//                                SPUtils.put(UserInfoActivity.this, "real", "true");
//                                LogUtils.e("onSuccess: 实名认证成功！");
//                                MyUser bean = new MyUser();
//                                bean.setUsername(jsonRealBean.getList().get(i).getName());
//                                bean.setStuno(jsonRealBean.getList().get(i).getStu_no());
//                                bean.setSex(jsonRealBean.getList().get(i).getSex());
//                                bean.setClazz(jsonRealBean.getList().get(i).getZhuanye());
//                                bean.setYuan(jsonRealBean.getList().get(i).getYuanxi());
//
//                                MyUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
//                                bean.update(bmobUser.getObjectId(), new UpdateListener() {
//
//                                    @Override
//                                    public void done(BmobException e) {
//                                        if (e == null) {
//                                            LogUtils.e("个人信息更新成功！");
//                                            btRealName.setVisibility(View.GONE);
//                                            ToastUtil.show(UserInfoActivity.this, "实名认证成功", Toast.LENGTH_SHORT);
//                                            fetchUserInfo();
//                                        } else {
//                                            LogUtils.e("错误：" + e.getMessage());
//                                            ToastUtil.show(UserInfoActivity.this, "实名认证失败", Toast.LENGTH_SHORT);
//                                        }
//                                    }
//                                });
//
//                                LoadDialog.dismiss(UserInfoActivity.this);
//                                return;
//                            }
//                        }
//                        LoadDialog.dismiss(UserInfoActivity.this);
//                    }
//
//                    @Override
//                    public void onError(Call call, Response response, Exception e) {
//                        super.onError(call, response, e);
//                        LoadDialog.dismiss(UserInfoActivity.this);
//                    }
//                });
//    }
//
//
//    /**
//     * 更新本地用户信息
//     * 注意：需要先登录，否则会报9024错误
//     *
//     * @see ErrorCode#E9024S
//     */
//    private void fetchUserInfo() {
//        BmobUser.fetchUserJsonInfo(new FetchUserInfoListener<String>() {
//            @Override
//            public void done(String s, BmobException e) {
//                if (e == null) {
//                    LogUtils.e("Newest UserInfo is " + s);
//                } else {
//                    LogUtils.e(e.toString());
//                }
//            }
//        });
//    }
//
//
    @OnClick({R.id.bt_real_name, R.id.layout_title, R.id.ll_user_info_name, R.id.ll_user_info_sex, R.id.ll_user_info_stuno,
            R.id.ll_user_info_tel, R.id.ll_user_info_yuan, R.id.ll_user_info_class, R.id.ll_user_info_create})
    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.bt_real_name:
//            case R.id.ll_user_info_class:
//            case R.id.ll_user_info_sex:
//            case R.id.ll_user_info_stuno:
//            case R.id.ll_user_info_yuan:
//                String isReal = SPUtils.look(UserInfoActivity.this, SPkey.isRealName, "false");
//                if (!TextUtils.equals(isReal, "true")) {
////                    RealName();
//                }
//        }
    }
}