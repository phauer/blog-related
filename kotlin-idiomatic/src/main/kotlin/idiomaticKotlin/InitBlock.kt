package idiomaticKotlin

import org.apache.http.client.HttpClient
import org.apache.http.impl.client.HttpClientBuilder
import java.util.concurrent.TimeUnit

// think twice before you define a init block/constructor body
// (first, named and default argument can help)
// second, you can refer to primary constructor para in property initializers (and not only in the init block)
// apply() can help to group initialization code and get along with a single expression.

// Don't
class UsersClient(baseUrl: String, appName: String) {
    private val usersUrl: String
    private val httpClient: HttpClient
    init {
        usersUrl = "$baseUrl/users"
        val builder = HttpClientBuilder.create()
        builder.setUserAgent(appName)
        builder.setConnectionTimeToLive(10, TimeUnit.SECONDS)
        httpClient = builder.build()
    }
    fun getUsers(){
        //call service using httpClient and usersUrl
    }
}

// Do
class UsersClient2(baseUrl: String, appName: String) {
    private val usersUrl = "$baseUrl/users"
    private val httpClient = HttpClientBuilder.create().apply {
        setUserAgent(appName)
        setConnectionTimeToLive(10, TimeUnit.SECONDS)
    }.build()
    fun getUsers(){
        //call service using httpClient and usersUrl
    }
}

//- `with()` returns result of lambda (and it's invoked statically)
//- `apply()` returns receiver obj (and it's invoked on receiver obj)