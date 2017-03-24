package idiomaticKotlin

//destruction useful for

//a) returning multiple values from a function (define an own data class or use Pair (but less expressive))
data class ServiceConfig(val host: String, val port: Int)

fun getServiceConfig(): ServiceConfig {
    //...
    return ServiceConfig("api.domain.io", 9389)
}

fun bla(){
    val (host, port) = getServiceConfig()
}

//b) iterate over maps
fun foo(){
    val map = mapOf("api.domain.io" to 9389, "localhost" to 8080)
    for ((host, port) in map){
        //...
    }
}

