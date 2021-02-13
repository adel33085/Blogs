package com.example.blogs.main

import android.view.View
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.IdlingResource
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.recycler.KRecyclerItem
import com.agoda.kakao.recycler.KRecyclerView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.blogs.R
import com.example.blogs.base.utils.TestIdlingResource
import com.example.blogs.features.domain.Blog
import com.example.blogs.features.ui.MainActivity
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class MainActivityTest {

    val screen = MainActivityScreen()

    val blog = Blog(
            id = 0,
            title = "Vancouver PNE 2019",
            body = "Here is Jess and I at the Vancouver PNE. We ate a lot of food.",
            image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image8.png",
            category = "fun"
    )

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, true, true)

    var idlingResource: IdlingResource? = null

    @Before
    fun setup() {
        idlingResource = TestIdlingResource.countingIdlingResource
        IdlingRegistry.getInstance().register(idlingResource)
    }

    @After
    fun tearDown() {
        idlingResource?.let {
            IdlingRegistry.getInstance().unregister(it)
        }
    }

    @Test
    fun shouldRenderBlogs() = run {
        screen {
            errorMessageTV {
                isGone()
            }
            blogsRV {
                isVisible()
                hasSize(9)
                firstChild<BlogItem> {
                    blogImageImgV.isDisplayed()
                    blogTitleTV.hasText(blog.title)
                }
            }
        }
    }
}

class MainActivityScreen : Screen<MainActivityScreen>() {
    val blogsRV: KRecyclerView = KRecyclerView({
        withId(R.id.blogsRV)
    }, itemTypeBuilder = {
        itemType(::BlogItem)
    })
    val errorMessageTV: KTextView = KTextView { withId(R.id.errorMessageTV) }
}

class BlogItem(parent: Matcher<View>) : KRecyclerItem<BlogItem>(parent) {
    val blogImageImgV = KImageView(parent) { withId(R.id.blogImageImgV) }
    val blogTitleTV = KTextView(parent) { withId(R.id.blogTitleTV) }
}
