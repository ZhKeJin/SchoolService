package com.display.hevttcdemo;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.display.hevttcdemo.bean.DaoMaster;
import com.display.hevttcdemo.bean.DaoSession;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheEntity;
import com.lzy.okgo.cookie.store.PersistentCookieStore;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.display.hevttcdemo.constant.Constant;
import com.display.hevttcdemo.utils.LogUtils;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobConfig;

/**
 * @ 创建时间: 2021/6/10 on 17:36.
 * @ 描述：MyApplication
 * @ 作者：zhangkejin
 */
public class MyApplication extends Application {

    private static DaoSession daoSession;
    private static MyApplication INSTANCE;

    public static MyApplication INSTANCE() {
        return INSTANCE;
    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }

    @Override
    public void onCreate() {
        // 初始化
        super.onCreate();
        INSTANCE = this;
        initOKGO();

        //Bmob初始化
//        Bmob.initialize(this, Constant.BMOB_APP_KEY);
        BmobConfig config = new BmobConfig.Builder(this)
        //设置appkey
        .setApplicationId(Constant.BMOB_APP_KEY)
        //请求超时时间（单位为秒）：默认15s
        .setConnectTimeout(60)
        //文件分片上传时每片的大小（单位字节），默认512*1024
        .setUploadBlockSize(1024*1024)
        //文件的过期时间(单位为秒)：默认1800s
        .setFileExpiration(2500)
        .build();
        Bmob.initialize(config);

        //配置数据库
//        setupDatabase();
        LogUtils.i("onCreate: MyApplication配置完成。");
    }

    private void initOKGO() {
        //必须调用初始化
        OkGo.init(this);

        try {
            OkGo.getInstance()
                    .debug("OkGo")
                    .setConnectTimeout(OkGo.DEFAULT_MILLISECONDS)  //全局的连接超时时间
                    .setReadTimeOut(OkGo.DEFAULT_MILLISECONDS)     //全局的读取超时时间
                    .setWriteTimeOut(OkGo.DEFAULT_MILLISECONDS)    //全局的写入超时时间
//                    .setCacheMode(CacheMode.NO_CACHE)
                    .setCacheTime(CacheEntity.CACHE_NEVER_EXPIRE)
                    .setCookieStore(new PersistentCookieStore());          //cookie持久化存储，如果cookie不过期，则一直有效
        } catch (Exception e) {
            e.printStackTrace();
        }
//        MultiDex.install(this);
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db"
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        // 获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        // 获取Dao对象管理者
        daoSession = daoMaster.newSession();
    }

}
