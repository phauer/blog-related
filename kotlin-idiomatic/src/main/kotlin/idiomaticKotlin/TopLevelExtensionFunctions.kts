package idiomaticKotlin

// In Java, you often create static util methods in util classes.
object StringUtil {
    fun countAmountOfX(string: String): Int{
        return string.length - string.replace("x", "").length;
    }
}
StringUtil.countAmountOfX("xKotlinxFunx") //3

// In Kotlin, remove the uncessary wrapping util class and use top-level functions instead
// Often, you can addtionally use extension functions, which increases readability ("like a story").
fun String.countAmountOfX(): Int {
    return length - replace("x", "").length;
}
"xKotlinxFunx".countAmountOfX() //3

