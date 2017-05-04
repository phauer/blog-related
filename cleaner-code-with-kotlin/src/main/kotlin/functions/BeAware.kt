package functions

fun add(value: String?){
    val map = mutableMapOf<String, String>()


value?.emptyToNull()?.let { map.put("bla", it) }

if (value?.isNotEmpty() ?: false){
    map.put("key", value!!)
}


    
    //KISS
if (!value.isNullOrEmpty()){
    map.put("key", value!!)
}
if (value != null && value.isNotEmpty()){
    map.put("key", value)
}
// or with smart-cast/without null-assertion
}

fun  String.emptyToNull() = if (this.isEmpty()) null else this
    