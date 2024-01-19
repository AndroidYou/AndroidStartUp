package com.cms.startup.utils

import com.cms.startup.`interface`.Startup

/**
 * @author: Mr.You
 * @create: 2023-03-15 11:19
 * @description:
 **/
private const val DEFAULT_KEY = "com.cms.startup.defaultKey"
internal fun Class<out Startup<*>>.getUniqueKey():String{
    return "$DEFAULT_KEY:$name"
}
internal fun String.getUniqueKey():String = "$DEFAULT_KEY:$this"