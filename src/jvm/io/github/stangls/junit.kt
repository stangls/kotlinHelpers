package io.github.stangls

/**
 * Created by stefan on 21.04.17.
 */

inline fun <reified T:Throwable> assertException(block: () -> Unit) : Unit =
    try {
        block()
        throw RuntimeException("Throwable ${T::class} was not thrown")
    }catch(t:Throwable){
        if (t !is T){
            throw t
        }else{}
    }