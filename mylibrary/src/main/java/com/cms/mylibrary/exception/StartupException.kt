package com.cms.mylibrary.exception

/**
 * @author: Mr.You
 * @create: 2023-03-15 11:16
 * @description:自定义异常
 **/
internal class StartupException : RuntimeException{
    constructor(message:String?):super(message)
    constructor(t:Throwable):super(t)
}