package com.cms.mylibrary.`interface`

import android.content.Context
import com.cms.mylibrary.dispatcher.Dispatcher
import com.cms.mylibrary.executor.StartupExecutor

interface Startup<T> : Dispatcher, StartupExecutor {
    /**执行任务**/
    fun create(context: Context):T?
    /**依赖任务集合**/
    fun dependencies():List<Class<out Startup<*>>>?
    /**依赖任务名称**/
    fun dependenciesByName():List<String>?
    /**依赖任务数**/
    fun getDependenciesCount():Int

}