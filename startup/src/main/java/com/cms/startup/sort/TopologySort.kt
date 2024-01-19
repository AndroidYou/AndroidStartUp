package com.cms.startup.sort

import com.cms.startup.exception.StartupException
import com.cms.startup.`interface`.Startup
import com.cms.startup.model.StartupSortStore
import com.cms.startup.utils.getUniqueKey

import java.util.*

/**
 */
internal object TopologySort {

    fun sort(startupList: List<Startup<*>>): StartupSortStore {

        val mainResult = mutableListOf<Startup<*>>()
        val ioResult = mutableListOf<Startup<*>>()
        val temp = mutableListOf<Startup<*>>()
        val startupMap = hashMapOf<String, Startup<*>>()
        val zeroDeque = ArrayDeque<String>()
        val startupChildrenMap = hashMapOf<String, MutableList<String>>()
        val inDegreeMap = hashMapOf<String, Int>()

        startupList.forEach {
            val uniqueKey = it::class.java.getUniqueKey()
            if (!startupMap.containsKey(uniqueKey)) {
                startupMap[uniqueKey] = it
                inDegreeMap[uniqueKey] = it.getDependenciesCount()
                if (it.dependenciesByName().isNullOrEmpty() && it.dependencies().isNullOrEmpty()) {
                    zeroDeque.offer(uniqueKey)
                } else if (it.dependenciesByName().isNullOrEmpty()) {
                    it.dependencies()?.forEach { parent ->
                        val parentUniqueKey = parent.getUniqueKey()
                        if (startupChildrenMap[parentUniqueKey] == null) {
                            startupChildrenMap[parentUniqueKey] = arrayListOf()
                        }
                        startupChildrenMap[parentUniqueKey]?.add(uniqueKey)
                    }
                } else {
                    it.dependenciesByName()?.forEach { parent ->
                        val parentUniqueKey = parent.getUniqueKey()
                        if (startupChildrenMap[parentUniqueKey] == null) {
                            startupChildrenMap[parentUniqueKey] = arrayListOf()
                        }
                        startupChildrenMap[parentUniqueKey]?.add(uniqueKey)
                    }
                }
            } else {
                throw StartupException("$it multiple add.")
            }
        }

        while (!zeroDeque.isEmpty()) {
            zeroDeque.poll()?.let {
                startupMap[it]?.let { androidStartup ->
                    temp.add(androidStartup)
                    if (androidStartup.callCreateMainThread()) {
                        mainResult.add(androidStartup)
                    } else {
                        ioResult.add(androidStartup)
                    }
                }
                startupChildrenMap[it]?.forEach { children ->
                    inDegreeMap[children] = inDegreeMap[children]?.minus(1) ?: 0
                    if (inDegreeMap[children] == 0) {
                        zeroDeque.offer(children)
                    }
                }
            }
        }

        if (mainResult.size + ioResult.size != startupList.size) {
            throw StartupException("lack of dependencies or have circle dependencies.")
        }

        val result = mutableListOf<Startup<*>>().apply {
            addAll(ioResult)
            addAll(mainResult)
        }
        return StartupSortStore(
            result,
            startupMap,
            startupChildrenMap
        )
    }


}