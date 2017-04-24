package io.github.stangls.cmdline

data class Argument(
    val short : String? = null,
    val long : String? = null,
    val valueName: String? = null,
    val description: String,
    val required: Boolean = false
){
    val shortText =
        if (short!=null) "-$short"+(if(valueName!=null) " $valueName" else "") else null
    val longText =
        if(long!=null) ("--$long"+if(valueName!=null) "=$valueName" else "" ) else null
    val leftSide : String =
        if(shortText!=null && longText!=null)
            "$shortText, $longText"
        else if (shortText!=null)
            "$shortText"
        else "    $longText"

    val rightSide : String = description
}