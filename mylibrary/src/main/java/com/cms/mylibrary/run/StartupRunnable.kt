package com.cms.mylibrary.run

import android.content.Context
import com.cms.mylibrary.dispatcher.ManagerDispatcher
import com.cms.mylibrary.`interface`.Startup
import com.cms.mylibrary.model.StartupSortStore

/**
 * @author: Mr.You
 * @create: 2023-03-15 15:50
 * @description:
 **/
class StartupRunnable(
    private val context: Context,
    private val startup: Startup<*>,
    private val sortStore: StartupSortStore,
    private val dispatcher: ManagerDispatcher
):Runnable {

    override fun run() {
        startup.toWait()
        val result = startup.create(context)
        dispatcher.notifyChildren(startup,result,sortStore)
    }
}