package idiomaticKotlin

// don't overload methods and constructors to realize default arguments ("chaining")
fun find(name: String){
    find(name, true)
}
fun find(name: String, recursive: Boolean){
}

// that are crutches. instead, Kotlin provides use named arguments
fun find2(name: String, recursive: Boolean = true){
}

// in fact, default arguments removed nearly all use cases for method and constructor overloading.