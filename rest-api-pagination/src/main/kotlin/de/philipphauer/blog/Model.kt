package de.philipphauer.blog

data class Design(
        val id: Int,
        val title: String,
        val imageUrl: String
)

data class DesignsPage(
        val designs: List<Design>,
        /** contains continuation token */
        val nextPage: String
)