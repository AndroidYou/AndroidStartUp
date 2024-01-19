package com.cms.startup.run

import android.content.Context
import com.cms.startup.dispatcher.ManagerDispatcher
import com.cms.startup.`interface`.Startup
import com.cms.startup.model.StartupSortStore

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