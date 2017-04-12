package idiomaticKotlin

//without value object:
fun send(target: String){}

//expressive, readable, safe
fun send(target: EmailAddress){}

//with value object:
data class EmailAddress(val value: String)
