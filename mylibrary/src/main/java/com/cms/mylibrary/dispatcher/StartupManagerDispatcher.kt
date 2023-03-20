package com.cms.mylibrary.dispatcher

import android.content.Context
import com.cms.mylibrary.`interface`.Startup
import com.cms.mylibrary.model.StartupSortStore
import com.cms.mylibrary.run.StartupRunnable
import com.cms.mylibrary.utils.getUniqueKey
import java.util.concurrent.CountDownLatch
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author: Mr.You
 * @create: 2023-03-15 15:48
 * @description: 任务分发管理器
 **/
internal class StartupManagerDispatcher(
    private val context: Context,
    private val needAwaitCount: AtomicInteger,
    private val awaitCountDownLatch: CountDownLatch?,
): ManagerDispatcher {
    override fun dispatch(startup: Startup<*>, sortStore: StartupSortStore) {
        val runnable = StartupRunnable(context, startup, sortStore, this)
        if (!startup.callCreateMainThread()){
            startup.createExecutor().execute(runnable)
        }else{
            runnable.run()
        }
    }

    override fun notifyChildren(dependencyParent: Startup<*>, result: Any?, sortStore: StartupSortStore) {
           if (dependencyParent.waitOnMainThread() && !dependencyParent.callCreateMainThread()){
               needAwaitCount.decrementAndGet()
               awaitCountDownLatch?.countDown()
           }
        sortStore.startupChildrenMap[dependencyParent::class.java.getUniqueKey()]?.forEach {
            sortStore.startupMap[it]?.run {
                toNotify()
            }
        }
    }

}