package io.github.stangls

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

open class CachedDelegate<in C,T:Any?> (
        val del : ReadWriteProperty<C, T>,
        val timeout : Long? = null
) : ReadWriteProperty<C, T> {

    protected var cachedValue: T? = null
        set(value) {
            lastWritten = currentTimeMs()
            field=value
        }
    protected var lastWritten : Long = 0L

    override fun setValue(thisRef: C, property: KProperty<*>, value: T) {
        cachedValue = value
        del.setValue(thisRef,property,value)
    }

    override fun getValue(thisRef: C, property: KProperty<*>): T {
        val now = currentTimeMs()
        if (lastWritten==0L || timeout!=null && (lastWritten+timeout<=now)) {
            lastWritten=now
            cachedValue = del.getValue(thisRef,property)
        }
        return cachedValue as T
    }

}

