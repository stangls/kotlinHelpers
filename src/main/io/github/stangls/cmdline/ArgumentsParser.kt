package io.github.stangls.cmdline

class ArgumentsParser(val args:List<String>) {



    init{
        var curArg : String? = null
        fun addCurArg(){

        }
        args.forEach {
            if (it.startsWith("-")) {
                addCurArg()
                curArg = it.removePrefix("--")
            } else if (it.startsWith("-")){
                addCurArg()
                curArg = it.removePrefix("-")
            } else {
                if (curArg==null){

                }
            }
        }
    }

    fun get(argName: String, numParams: Int): List<String>? {
        TODO() // todo
    }

}