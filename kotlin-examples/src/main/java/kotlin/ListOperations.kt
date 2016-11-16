package kotlin

// less boilerplate (stream(), collect()). result is already a list.
// string interpolation
fun mapToOrderURLs(orderIds: List<Int>): List<String> {
    val refs = orderIds.map { id -> "v1/orders/$id" }
    return refs
}
//or shorter: as single expression function and inferred return type for method
fun mapToOrderURLs2(orderIds: List<Int>) = orderIds.map { id -> "v1/orders/$id" }

//or even shorter: using "it" for the parameter (when there is only one parameter)
fun mapToOrderURLs3(orderIds: List<Int>) = orderIds.map { "v1/orders/$it" }