package com.cms.androidstartup

import android.content.Context
import android.util.Log
import com.cms.mylibrary.`interface`.AndroidStartup

/**
 * @author: Mr.You
 * @create: 2023-03-18 10:18
 * @description:
 **/

class InitSdkOne : AndroidStartup<String>() {
    override fun create(context: Context): String {
        object :Thread(){
            override fun run() {
                sleep(1000)
                Log.i("TAG", "create: InitSdkOne初始化完成")
                super.run()
            }
        }.start()

        return "InitSdkOne create"
    }

    override fun callCreateMainThread(): Boolean {
       return false
    }

    override fun waitOnMainThread(): Boolean {
        return true
    }
}
