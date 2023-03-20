package com.cms.mylibrary.dispatcher

/**
 * @author: Mr.You
 * @create: 2023-03-15 10:03
 * @description:
 **/
interface Dispatcher {
    /**是否在主线程中调用**/
    fun callCreateMainThread():Boolean
    /***是否等待主线程任务执行完成*/
    fun waitOnMainThread():Boolean
    /**线程等待**/
    fun toWait()
    /**线程唤醒**/
    fun toNotify()
}