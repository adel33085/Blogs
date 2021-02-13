package com.example.blogs.details

import android.content.Context
import android.content.Intent
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import com.agoda.kakao.image.KImageView
import com.agoda.kakao.screen.Screen
import com.agoda.kakao.text.KTextView
import com.example.blogs.R
import com.example.blogs.features.domain.Blog
import com.example.blogs.features.ui.DetailsActivity
import org.junit.Rule
import org.junit.Test

@LargeTest
class DetailsActivityTest {

    val screen = DetailsActivityScreen()

    val blog = Blog(
            id = 0,
            title = "Vancouver PNE 2019",
            body = "Here is Jess and I at the Vancouver PNE. We ate a lot of food.",
            image = "https://cdn.open-api.xyz/open-api-static/static-blog-images/image8.png",
            category = "fun"
    )

    @Rule
    @JvmField
    var activityTestRule: ActivityTestRule<DetailsActivity> = object : ActivityTestRule<DetailsActivity>(DetailsActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val targetContext: Context = InstrumentationRegistry.getInstrumentation().targetContext
            val result = Intent(targetContext, DetailsActivity::class.java)
            result.putExtra("blog", blog)
            return result
        }
    }

    @Test
    fun shouldRenderBlogDetails() {
        screen {
            blogImageImgV {
                isDisplayed()
            }
            blogTitleTV {
                isVisible()
                hasText(blog.title)
            }
            blogBodyTV {
                isVisible()
                hasText(blog.body)
            }
        }
    }
}

class DetailsActivityScreen : Screen<DetailsActivityScreen>() {
    val blogImageImgV: KImageView = KImageView { withId(R.id.blogImageImgV) }
    val blogTitleTV: KTextView = KTextView { withId(R.id.blogTitleTV) }
    val blogBodyTV: KTextView = KTextView { withId(R.id.blogBodyTV) }
}
