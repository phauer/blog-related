package de.philipphauer.blog

import java.net.URL

data class DesignDTO(
        val id: String,
        val title: String,
        val imageUrl: URL
)

data class DesignsPageDTO(
        val designs: List<DesignDTO>,
        /** contains continuation token */
        val nextPage: URL
)