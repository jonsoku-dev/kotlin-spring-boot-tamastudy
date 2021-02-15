package com.tamastudy.tama.config

import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.util.StringUtils
import java.lang.reflect.Method

class BoardCacheGenerator : KeyGenerator {
    override fun generate(target: Any, method: Method, vararg params: Any): Any {
        return target.javaClass.simpleName + "_" + method.name + "_" +
                StringUtils.arrayToDelimitedString(params, "_")
    }
}