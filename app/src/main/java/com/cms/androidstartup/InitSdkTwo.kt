package com.cms.androidstartup

import android.content.Context
import android.util.Log
import com.cms.mylibrary.`interface`.AndroidStartup

/**
 * @author: Mr.You
 * @create: 2023-03-18 10:18
 * @description:
 **/

class InitSdkTwo : AndroidStartup<String>() {
    override fun create(context: Context): String {
        Log.i("TAG", "create: InitSdkTwo初始化完成")
        return "InitSdkOne create"
    }

    override fun callCreateMainThread(): Boolean {
       return false
    }

    override fun waitOnMainThread(): Boolean {
        return false
    }
}
