package com.cms.mylibrary.model

import com.cms.mylibrary.`interface`.Startup

/**
 * @author: Mr.You
 * @create: 2023-03-15 11:13
 * @description:
 **/
data class StartupSortStore(
    val result:MutableList<Startup<*>>,
    val startupMap:Map<String, Startup<*>>,
    val startupChildrenMap:Map<String,MutableList<String>>
)