package io.github.stangls

/**
 * Created by stefan on 19.04.17.
 */

fun <T> onExcNull(function: () -> T): T? =
    try {
        function()
    }catch(_:Throwable){
        null
    }