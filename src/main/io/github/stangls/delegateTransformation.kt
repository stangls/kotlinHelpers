package io.github.stangls

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun  <C, Outer, Inner>
        ReadWriteProperty<C, Inner>.map(outwards: (Inner) -> Outer, inwards: (Outer) -> Inner)
    = object : ReadWriteProperty<C, Outer>
{
    override fun getValue(thisRef: C, property: KProperty<*>): Outer
        = outwards(this@map.getValue(thisRef, property))
    override fun setValue(thisRef: C, property: KProperty<*>, value: Outer)
        = this@map.setValue(thisRef,property,inwards(value))
}