package com.example.blogs.features.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.blogs.R
import com.example.blogs.features.domain.Blog
import kotlinx.android.synthetic.main.blog_list_item.view.*

class BlogsAdapter(
        private val clickListener: OnBlogClickListener
) : ListAdapter<Blog, BlogViewHolder>(BLOG_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BlogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.blog_list_item, parent, false)
        return BlogViewHolder(view)
    }

    override fun onBindViewHolder(holder: BlogViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

    companion object {
        private val BLOG_COMPARATOR = object : DiffUtil.ItemCallback<Blog>() {
            override fun areItemsTheSame(oldItem: Blog, newItem: Blog): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Blog, newItem: Blog): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface OnBlogClickListener {
    fun onBlogClick(blog: Blog)
}

class BlogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(blog: Blog, clickListener: OnBlogClickListener) {
        with(itemView) {
            Glide.with(blogImageImgV).load(blog.image).into(blogImageImgV)
            blogTitleTV.text = blog.title
            setOnClickListener {
                clickListener.onBlogClick(blog)
            }
        }
    }
}
