package idiomaticKotlin

class SearchConfig {
    fun setRoot(s: String): SearchConfig { return this }
    fun setTerm(s: String): SearchConfig { return this }
    fun setRecursive(s: Boolean): SearchConfig { return this }
    fun setFollowSymlinks(s: Boolean): SearchConfig { return this }
}

//Back in Java, fluent setters where used to simulate named and default arguments and avoid huge parameter lists (error-prone and hard to read).
val config = SearchConfig()
        .setRoot("~/folder")
        .setTerm("kotlin")
        .setRecursive(true)
        .setFollowSymlinks(true)

//in kotlin, named and default arguments are built into the language
val config2 = SearchConfig2(
        root = "~/folder",
        term = "kotlin",
        recursive = true,
        followSymlinks = true
)

//definition:
class SearchConfig2(
        val root: String,
        val term: String,
        val recursive: Boolean = false,
        val followSymlinks: Boolean = false
)