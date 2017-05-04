package functions

fun varVal(){

    val id = 1
    // id = 2

    var id2 = 1
    id2 = 2

    println(id)
    println(id2)
}

fun collections() {
val list = listOf(1,2,3,4)
//list.add(1)
val evenList = list.filter { it % 2 == 0 }
}


data class DesignMetaData(
        val id: Int,
        val fileName: String,
        val uploaderId: Int,
        val width: Int = 0,
        val height: Int = 0
)
val design = DesignMetaData(id = 1, fileName = "cat.jpg", uploaderId = 2)
val id = design.id
// design.id = 2
val design2 = design.copy(fileName = "dog.jpg")

enum class DesignType {PIXEL, VECTOR}