package de.philipphauer.blog.misc.constructorinjection

class CustomerResourceKotlin(private val repo: CustomerRepository,
                             private val client: CRMClient){
    //that's it!
}


