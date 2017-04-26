package io.github.stangls

import kotlin.reflect.KCallable
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1

operator fun <X: KCallable<*>> Collection<X>.get(name:String) = firstOrNull{ it.name==name }
inline fun <T> Collection<KProperty1<T, *>>.mutable() = mapNotNull { it as? KMutableProperty1<T,*> }