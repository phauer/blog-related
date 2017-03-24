package idiomaticKotlin

data class EmailAddress(val address: String)

//without value object:
fun process1(email: String){}

//with value object:
fun process2(email:EmailAddress){}
//=> expressive, readable, safe