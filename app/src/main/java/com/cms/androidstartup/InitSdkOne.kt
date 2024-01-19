package com.cms.androidstartup

import android.content.Context
import android.util.Log
import com.cms.startup.`interface`.AndroidStartup

/**
 * @author: Mr.You
 * @create: 2023-03-18 10:18
 * @description:
 **/

class InitSdkOne : AndroidStartup<String>() {
    /**
     * 进行初始化工作
     * **/
    override fun create(context: Context): String {
        Log.i("TAG", "create: InitSdkOne初始化完成：当前线程："+Thread.currentThread().name)
        return "InitSdkOne create"
    }
    /**
     * 是否在主线程中调用
     * **/
    override fun callCreateMainThread(): Boolean {
       return false
    }
    /****
     * 是否等待主线程完成
     */
    override fun waitOnMainThread(): Boolean {
        return true
    }
}
