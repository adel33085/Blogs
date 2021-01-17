package com.example.blogs.features.entity

import com.example.blogs.features.domain.Blog
import com.google.gson.annotations.SerializedName

data class BlogResponse(
        @SerializedName("pk")
        val id: Int?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("body")
        val body: String?,
        @SerializedName("image")
        val image: String?,
        @SerializedName("category")
        val category: String?
)

fun BlogResponse.toBlog(): Blog? {
    if (id != null
            && title != null
            && body != null
            && image != null
            && category != null
    ) {
        return Blog(
                id = id,
                title = title,
                body = body,
                image = image,
                category = category
        )
    }
    return null
}
