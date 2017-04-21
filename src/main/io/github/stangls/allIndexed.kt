
fun <T> Iterable<T>.allIndexed(func: (Int,T)->Boolean): Boolean =
        mapIndexed(func).all { it }