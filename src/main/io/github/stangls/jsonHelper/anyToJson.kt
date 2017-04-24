package io.github.stangls.jsonHelper

import io.github.stangls.mutable
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.reflect.full.memberProperties

/**
 * Created by stefan on 21.04.17.
 */

inline fun <reified T:Any> T.toJson(onlyMutable:Boolean=false) : Any
    = this.toJson(this::class as KClass<T>, onlyMutable)

fun <T:Any> T.toJson(c: KClass<T>, onlyMutable:Boolean=false) : Any =
    when (this){
        is List<*> -> JSONArray(this.map { it?.toJson() })
        is String -> this
        is Number -> this
        else -> run {
            var props = c.memberProperties
            if (onlyMutable) props = props.mutable()
            JSONObject(mapOf(*props.map {
                val value = it.get(this)?.toJson()
                it.name to value
            }.toTypedArray()))
        }
    }
