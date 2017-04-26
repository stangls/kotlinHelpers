package io.github.stangls.jsonHelper

import io.github.stangls.joinIndent
import org.json.JSONArray
import org.json.JSONObject
import kotlin.reflect.KClass
import kotlin.reflect.KParameter
import kotlin.reflect.KType
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.full.safeCast

class UnmappableException(msg:String):Throwable(msg)

fun asType(jsonVal:Any?, type: KType) : Any? {
    val c = type.classifier as? KClass<*> ?: throw UnmappableException("not a class: $type")
    if (jsonVal == JSONObject.NULL) return null
    return when(jsonVal){
        is JSONObject -> (jsonVal as JSONObject).instantiate(c)
        is JSONArray -> (jsonVal as JSONArray).asListOf(type)
        else -> // plain type
            if (!c.isInstance(jsonVal)) {
                throw UnmappableException("can not put into $c : $jsonVal")
            } else {
                return c.safeCast(jsonVal)
            }
    }
}

fun JSONArray.asListOf(type: KType): List<Any?> {
    val c = type.classifier as? KClass<*> ?: throw UnmappableException("not a class: $type")
    if (!c.isSuperclassOf(List::class)) throw UnmappableException("json array should be put into list")
    val arrElemType = type.arguments.first().type ?: throw UnmappableException("list subtype is unsupported yet")
    return map { asType(it, arrElemType) }
}

fun <T:Any> JSONObject.instantiate(c: KClass<T>): T {
    val possibleConstructions = c.constructors.map{ constructor ->
        data class ParamInfo(
            val mappedValue:Any?,
            val ignoreParam:Boolean = false,
            val kParameter:KParameter,
            val error:String?
        )
        val params = constructor.parameters.map { param ->
            try {
                val mapped =
                        if (!this.has(param.name)){
                            null
                        }else {
                            asType(
                                    this[param.name],
                                    param.type
                            )
                        }
                if (mapped == null && !param.type.isMarkedNullable && !param.isOptional) {
                    ParamInfo(null, false, param, "can not be null (and no default-value supplied)")
                }else {
                    ParamInfo(mapped, param.isOptional, param, null)
                }
            }catch(e: UnmappableException){
                ParamInfo(null, false, param, e.message?:"unmapple with unknown cause")
            }
        }
        constructor to params
    }
    val (func,params) = possibleConstructions.mapNotNull { (func, paramQ) ->
        if (paramQ.all{ (_,_,_,error) -> error==null }){
            func to paramQ.filter{!it.ignoreParam}.map{(value,_,kParameter,_)->kParameter to value}
        }else{
            null
        }
    }.firstOrNull() ?: throw UnmappableException(
            "no fitting constructor for class $c because\n"+
                    possibleConstructions.map{ (func, paramTriples) ->
                        "$func does not fit:\n"+
                                paramTriples.map { (value,_,kParameter,message) ->
                                    val name = kParameter.name
                                    if (message==null) {
                                        "$name = $value"
                                    } else {
                                        "$name ----------------------> $message"
                                    }
                                }.joinIndent("  ")
                    }.joinIndent("  ")
    )
    return func.callBy(params.toMap())
}