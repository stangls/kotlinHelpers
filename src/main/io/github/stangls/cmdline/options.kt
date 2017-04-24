package io.github.stangls.cmdline

fun Array<String>.parse(func : ArgumentsDsl.()->Unit ){
    ArgumentsDsl(this.toList()).apply{
        func()
        if (!finish()){
            System.exit(1)
        }
    }
}