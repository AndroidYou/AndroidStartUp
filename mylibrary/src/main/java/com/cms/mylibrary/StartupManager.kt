package com.cms.mylibrary

import android.content.Context
import android.os.Looper
import com.cms.mylibrary.dispatcher.StartupManagerDispatcher
import com.cms.mylibrary.exception.StartupException
import com.cms.mylibrary.`interface`.AndroidStartup
import com.cms.mylibrary.model.StartupSortStore
import com.cms.mylibrary.sort.TopologySort
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger

/**
 * @author: Mr.You
 * @create: 2023-03-15 13:30
 * @description:
 **/
class StartupManager private constructor(
    private val context: Context,
    private val startupList:List<AndroidStartup<*>>,
    private val needAwaitCount:AtomicInteger,
){
    private var mAwaitCountDownLatch:CountDownLatch? = null
    companion object{
        const val AWAIT_TIMEOUT = 10000L
    }
    fun start() = apply {
        if (Looper.getMainLooper() !=Looper.myLooper()){
            throw StartupException("this thread is not MainThread")
        }
        if (mAwaitCountDownLatch!=null){
            throw StartupException("start method already init ")
        }
        mAwaitCountDownLatch = CountDownLatch(needAwaitCount.get())
        if (startupList.isNullOrEmpty()){
            return@apply
        }
        TopologySort.sort(startupList).run {
           execute(this)
        }
    }
    fun await(){
        if (mAwaitCountDownLatch == null){
            throw StartupException("must be call start method")
        }
        mAwaitCountDownLatch?.await(AWAIT_TIMEOUT,TimeUnit.SECONDS)
    }
    private fun execute(sortStore: StartupSortStore){
        sortStore.result.forEach {
            mDefaultManagerDispatcher.dispatch(it,sortStore)
        }
    }

    private val mDefaultManagerDispatcher by lazy {
        StartupManagerDispatcher(context,needAwaitCount,mAwaitCountDownLatch)
    }


    class Builder{
        private var mStartupList = mutableListOf<AndroidStartup<*>>()
        private var mNeedAwaitCount = AtomicInteger()
        fun addStartup(startup: AndroidStartup<*>) =apply {
            mStartupList.add(startup)
        }
        fun addStartupList(startupList: List<AndroidStartup<*>>) =apply {
            mStartupList.addAll(startupList)
        }
        fun build(context: Context): StartupManager {
            mStartupList.forEach {
                if (it.waitOnMainThread() && !it.callCreateMainThread()){
                    mNeedAwaitCount.incrementAndGet()
                }
            }
            return StartupManager(context,mStartupList,mNeedAwaitCount)
        }
    }
}