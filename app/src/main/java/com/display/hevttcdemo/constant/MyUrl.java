package com.display.hevttcdemo.constant;


/**
 * 创建日期:2021/5/5 on 21:19
 * 描述: 网址url
 * （之前采用自建服务器搭建了若干接口，现原服务器已停用，
 * 后续有时间会重写这部分接口）
 * 作者：zhangkejin
 */

public class MyUrl {
    //主机地址
    public static final String HOST = "http://123.206.54.13:8080/StuInfo";
    //检查更新网址
    public static final String URL_UPDATE = "http://123.206.54.13:8080/keshi/update/update.json";
    //    查询网址
    public static final String URL_QUERY = HOST + "/QueryLet";
    //    获取问题网址
    public static final String URL_GETQUEST1 = "http://123.207.155.175:8080/Examine1/GetQuestionLet";
    //  学校官网
    public static final String URL_HEVTTC = "http://www.xiyou.edu.cn/";
    //    腾讯街景
    public static final String URL_QQMAP = "https://www.vrqjcs.com/p/db4633195739afca";

    public static final String URL_QueryBook = "http://www.niowoo.com/weixin.php/Home/Library/searchBook/library_id/126";
    public static final String URL_Libray = "http://mc.m.5read.com/weixin/customize_hpindex.jspx?wxProductId=758&hptype=2";


}
