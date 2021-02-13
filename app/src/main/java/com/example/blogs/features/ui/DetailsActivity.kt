package com.example.blogs.features.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.blogs.R
import com.example.blogs.features.domain.Blog
import com.example.blogs.features.ui.MainActivity.Companion.KEY_BLOG
import kotlinx.android.synthetic.main.activity_details.*

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val blog = intent.getParcelableExtra<Blog>(KEY_BLOG)
        blog?.let {
            updateUi(it)
        }
    }

    private fun updateUi(blog: Blog) {
        Glide.with(blogImageImgV).load(blog.image).into(blogImageImgV)
        blogTitleTV.text = blog.title
        blogBodyTV.text = blog.body
    }
}
