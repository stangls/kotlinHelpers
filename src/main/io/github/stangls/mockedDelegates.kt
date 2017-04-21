package io.github.stangls

import kotlin.properties.Delegates
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Created by stefan on 18.04.17.
 */

fun <C,T:Any?> Delegates.mocked(value: T): CachedDelegate<C, T> {
    return CachedDelegate<C,T>(object: ReadWriteProperty<C,T> {
        override fun getValue(thisRef: C, property: KProperty<*>): T = value
        override fun setValue(thisRef: C, property: KProperty<*>, value: T) {}
    },timeout=null)
}