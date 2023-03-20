package com.cms.mylibrary.executor

import android.os.Handler
import android.os.Looper
import java.util.concurrent.*
import kotlin.math.max
import kotlin.math.min

/**
 * @author: Mr.You
 * @create: 2023-03-15 10:38
 * @description:
 **/
class ExecutorManager {
    var cpuExecutor:ThreadPoolExecutor
        private set
    var ioExecutor:ExecutorService
        private set
    var mainExecutor:Executor
        private set
    private val handler = RejectedExecutionHandler{_,_ ->Executors.newCachedThreadPool(Executors.defaultThreadFactory())}

    companion object{
        @JvmStatic
        val instance by lazy { ExecutorManager() }
        private val CPU_COUNT = Runtime.getRuntime().availableProcessors()
        private val CORE_POOL_SIZE = max(2, min(CPU_COUNT - 1,5))
        private val MAX_POOL_SIZE = CORE_POOL_SIZE
        private const val KEEP_ALIVE_TIME = 5L
    }
    init {
        cpuExecutor = ThreadPoolExecutor(
            CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME,
            TimeUnit.SECONDS,
            LinkedBlockingQueue<Runnable>(),
            Executors.defaultThreadFactory(),
            handler
        ).apply { allowCoreThreadTimeOut(true) }

        ioExecutor = Executors.newCachedThreadPool(Executors.defaultThreadFactory())
        val handler = Handler(Looper.getMainLooper())
        mainExecutor = Executor { handler.post(it)}
    }
}