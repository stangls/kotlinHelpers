package io.github.stangls

fun List<String>.joinIndent(indent: String="  "): String =
    joinToString(separator = "\n", postfix="\n").prependIndent(indent)