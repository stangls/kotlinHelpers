package io.github.stangls

/**
 * Created by stefan on 18.04.17.
 */
external fun encodeURI(uri:String):String
external fun decodeURI(uri:String):String

fun String.urlencode(): String = encodeURI(this)
fun String.urldecode(): String = decodeURI(this)