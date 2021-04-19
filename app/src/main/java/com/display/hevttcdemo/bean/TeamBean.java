package com.display.hevttcdemo.bean;

import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.Serializable;

import cn.bmob.v3.BmobObject;
import com.display.hevttcdemo.R;
import com.display.hevttcdemo.utils.LogUtils;
import xyz.zpayh.adapter.BaseViewHolder;
import xyz.zpayh.adapter.IMultiItem;

/**
 * @ 创建时间: 2021/9/20 on 16:32.
 * @ 描述：社团组织
 * @ 作者：zhangkejin
 */

public class TeamBean extends BmobObject implements IMultiItem, Serializable {

    private String title;
    private String title2;
    private String time;
    private String pic1;
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle2() {
        return title2;
    }

    public void setTitle2(String title2) {
        this.title2 = title2;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    @Override
    public int getLayoutRes() {
        return R.layout.item_team;
    }

    @Override
    public void convert(BaseViewHolder holder) {

        holder.setText(R.id.tv_team_title, title);
        holder.setText(R.id.tv_team_title2, title2);
        holder.setText(R.id.tv_team_time, time);

        try {
            if (!TextUtils.isEmpty(pic1)) {
                ImageView sdv1 = holder.find(R.id.iv_team_logo);
                Glide.with(sdv1.getContext())                             //配置上下文
                        .load(pic1)      //设置图片路径(fix #8,文件名包含%符号 无法识别和显示)
                        .error(R.mipmap.default_image)           //设置错误图片
                        .placeholder(R.mipmap.default_image)     //设置占位图片
                        .centerCrop()
                        .into(sdv1);
            }
        } catch (Exception e) {
            LogUtils.e("出现异常，图片没有加载完成");
        }
    }

    @Override
    public int getSpanSize() {
        return 0;
    }
}
