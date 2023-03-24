package com.cms.androidstartup

import android.app.Application
import android.util.Log

import com.cms.mylibrary.manager.StartupManager


class CustomApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.i("TAG", "InitSdk开始 ")
        StartupManager.Builder()
            .addStartup(InitSdkOne())
            .addStartup(InitSdkTwo())
            .build(this)
            .start()
    }
}