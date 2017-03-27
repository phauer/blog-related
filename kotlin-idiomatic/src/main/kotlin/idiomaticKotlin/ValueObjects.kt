package idiomaticKotlin

//without value object:
fun send(target: String){}

//with value object:
data class EmailAddress(val value: String)

fun send(target: EmailAddress){}
//expressive, readable, safe