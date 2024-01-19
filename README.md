# AndroidStartUp 安卓启动框架
## 背景
Android应用程序在启动过程中，一般会在Application中初始化大量第三方SDK,大多数使用子线程去初始化，但是如果有些SDK初始化具体前后关联性，那么仅仅使用子线程去初始化肯定是不够的。
当不同初始化任务之间具有依赖性，需要使用根据任务之间的关联性进行排序，依次初始化。
## 使用说明
1. 自定义初始化任务类继承自AndroidStartup,根据需要配置是否在主线程中调用callCreateMainThread()，是否需要等待主线程完成waitOnMainThread（）
```
package com.cms.androidstartup

import android.content.Context
import android.util.Log
import com.cms.startup.`interface`.AndroidStartup

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

```
2. 将定义的初始化任务添加到任务列表
```
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
```
   

## 版本迭代
### 1.0.0 

1.区分子线程与主线程。

2.具有依赖性的任务能够按顺序初始化。

3.主线程与子线程之间能够互相切换执行。
