package com.cms.mylibrary.dispatcher

import com.cms.mylibrary.`interface`.Startup
import com.cms.mylibrary.model.StartupSortStore

/**
 * @author: Mr.You
 * @create: 2023-03-15 15:46
 * @description:
 **/
interface ManagerDispatcher {
    /**任务分发**/
    fun dispatch(startup: Startup<*>, sortStore: StartupSortStore)
    /**唤醒所有子任务**/
    fun notifyChildren(dependencyParent: Startup<*>, result:Any?, sortStore: StartupSortStore)
}