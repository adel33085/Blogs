package com.example.blogs.features.domain

data class Blog(
        val id: Int,
        val title: String,
        val body: String,
        val image: String,
        val category: String
)
