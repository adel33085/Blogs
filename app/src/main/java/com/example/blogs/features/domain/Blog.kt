package com.example.blogs.features.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Blog(
        val id: Int,
        val title: String,
        val body: String,
        val image: String,
        val category: String
) : Parcelable
