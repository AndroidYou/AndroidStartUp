package com.cms.mylibrary.`interface`

import com.cms.mylibrary.executor.ExecutorManager
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executor

/**
 * @author: Mr.You
 * @create: 2023-03-15 10:11
 * @description:
 **/
abstract class AndroidStartup<T> : Startup<T> {
    private val mWaitCountDownLatch by lazy {
        CountDownLatch(getDependenciesCount())
    }

    override fun toWait() {
        try {
            mWaitCountDownLatch.await()
        }catch (e:InterruptedException){
        }
    }

    override fun toNotify() {
        mWaitCountDownLatch.countDown()
    }

    override fun dependencies(): List<Class<out Startup<*>>>? {
        return null
    }

    override fun dependenciesByName(): List<String>? {
        return null
    }

    override fun getDependenciesCount(): Int {
        if (dependenciesByName().isNullOrEmpty()) return dependencies()?.size?:0
        return dependenciesByName()?.size?:0
    }

    override fun createExecutor(): Executor = ExecutorManager.instance.ioExecutor

}