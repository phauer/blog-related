package functions

val value: String = "Clean Code"
//val value2: String = null

val nullValue: String? = "Clean Code"
val nullValue2: String? = null

//val assign1: String = nullValue
val assign2: String = if (nullValue == null) "default" else nullValue //smart-cast
val assign3: String = nullValue ?: "default"




data class Order(val customer: Customer?)
data class Customer(val address: Address?)
data class Address(val city: String)

fun ship(order: Order?){
    //every time you do if-null-checks, hold on.
if (order == null || order.customer == null || order.customer.address == null){
    throw IllegalArgumentException("Invalid Order")
}
val city = order.customer.address.city
}

fun ship2(order: Order?){
    // Often, you can use null-safe call (?.) or the elvis operator (?:) instead.
    val city = order?.customer?.address?.city ?: throw IllegalArgumentException("Invalid Order")
}


interface Service
class CustomerService : Service {
    fun getCustomer() {}
}

fun getMetrics(service: Service){
    // also hold on for if-type-checks
    if (service !is CustomerService) {
        throw IllegalArgumentException("No CustomerService")
    }
    service.getCustomer()
}

fun getMetrics2(service: Service){
    //check type, (smart-)cast it and throw exception if the type is not the expected one. all in one expression!
    service as? CustomerService ?: throw IllegalArgumentException("No CustomerService")
    service.getCustomer()
}


fun foo(order: Order?){
    // avoid yelling !! where every possible. search for better solutions by verifying the variable up front and handle nulls. (quote book)
    order!!.customer!!.address!!.city
}

fun findOrder(): Order? {
    return null
}
fun dun(customer: Customer?){

}

fun handle(){
    // Don't
    val order: Order? = findOrder()
    if (order != null){
        dun(order.customer)
    }

    // with let(), there is no need for an extra variable
    // can write as one expression
    findOrder()?.let { dun(it.customer) }
    findOrder()?.customer?.let(::dun)

}