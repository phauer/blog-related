package idiomaticKotlin

data class EmailAddress(val value: String)

//without value object:
fun send(target: String){}

//with value object:
fun send(target:EmailAddress){}
//=> expressive, readable, safe