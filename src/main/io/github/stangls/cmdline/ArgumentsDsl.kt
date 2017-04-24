package io.github.stangls.cmdline

class ArgumentsDsl(args: List<String>) {

    var name : String = System.getProperty("sun.java.command")
    var header : String = name
    protected val arguments = ArgumentsParser(args)
    protected val possibleOptions: MutableList<Argument> = mutableListOf()
    // TODO: possible parameters (non-prefixed with -x)
    protected val possibleParams: MutableList<String> = mutableListOf()
    protected val errors : MutableList<String> = mutableListOf()

    fun opt(short: String, long: String, valueName: String="value", description: String, block:(String)->Unit) {
        possibleOptions.add(Argument(short, long, valueName, description))
        val values = arguments.get(short,1) ?: arguments.get(long,1) ?: return
        val value = values.firstOrNull()
        if (value==null){
            errors.add("Option -$short / --$long has one parameter.")
        }else {
            block(value)
        }
    }
    fun opt(short: String, long: String, description: String, block:()->Unit) {
        possibleOptions.add(Argument(short, long, null, description))
        val values = arguments.get(short,0) ?: arguments.get(long,0) ?: return
        if (values.isNotEmpty()){
            errors.add("Option -$short / --$long has no parameter.")
        }else{
            block()
        }
    }

    fun req(short: String, long: String, valueName: String="value", description: String, block:(String)->Unit) {
        possibleOptions.add(Argument(short, long, valueName, description, true))
        val values = arguments.get(short,1) ?: arguments.get(long,1)
        if (values==null){
            errors.add("Option -$short / --$long is required")
            return
        }
        val value = values.firstOrNull()
        if (value==null){
            errors.add("Option -$short / --$long has one parameter.")
        }else {
            block(value)
        }
    }

    fun usage(short:String="-h", long:String="--help", description: String="Help. Prints this usage monolog."){
        opt(short,long,description) {
            errors.add("")
        }
    }

    internal fun finish() : Boolean{
        if (errors.isEmpty()){
            return true
        }else{
            println(header)
            errors.forEach { println(it) }
            println()
            println(
                "$name "+
                (if (possibleOptions.isNotEmpty()) "[options]" else "") +
                possibleParams.joinToString(" ")
            )
            println()
            val leftSideWidth = (possibleOptions.map{ it.leftSide.length }.max()?:0)+5
            possibleOptions.forEach{

            }
            return false
        }
    }

}

