package de.philipphauer.blog.misc

fun process1(emails: List<String>){}
//vs

data class EmailAddress(val address: String)

fun process2(emails: List<EmailAddress>){}
//=> expressive, readable, safe
