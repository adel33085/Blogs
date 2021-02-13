package com.example.blogs.features.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.example.blogs.R
import com.example.blogs.base.platform.BaseActivity
import com.example.blogs.base.utils.EventObserver
import com.example.blogs.features.domain.Blog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : BaseActivity(), OnBlogClickListener {

    private val viewModel: MainViewModel by viewModels()
    private val adapter by lazy { BlogsAdapter(this) }

    companion object {
        const val KEY_BLOG = "blog"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handleLoading()

        handleError()

        viewModel.getBlogs()
        viewModel.blogs.observe(this, EventObserver {
            blogsRV.visibility = View.VISIBLE
            adapter.submitList(it)
            blogsRV.adapter = adapter
        })
    }

    private fun handleLoading() {
        viewModel.loading.observe(this, EventObserver {
            if (it.loading) {
                blogsRV.visibility = View.GONE
                errorMessageTV.visibility = View.GONE
                showLoading()
            } else {
                hideLoading()
            }
        })
    }

    private fun handleError() {
        viewModel.error.observe(this, EventObserver {
            hideLoading()
            errorMessageTV.visibility = View.VISIBLE
            errorMessageTV.text = when {
                it.exception.errorMessage.isNullOrEmpty().not() -> {
                    it.exception.errorMessage
                }
                it.exception.errorMessageRes != null -> {
                    resources.getString(it.exception.errorMessageRes)
                }
                else -> {
                    resources.getString(R.string.error_something_went_wrong)
                }
            }
        })
    }

    override fun onBlogClick(blog: Blog) {
        val intent = Intent(this, DetailsActivity::class.java)
        intent.putExtra(KEY_BLOG, blog)
        startActivity(intent)
    }
}
