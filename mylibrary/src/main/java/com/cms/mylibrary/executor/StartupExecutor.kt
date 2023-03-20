package com.cms.mylibrary.executor

import java.util.concurrent.Executor

interface StartupExecutor {
    /**创建线程池**/
    fun createExecutor():Executor
}