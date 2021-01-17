package com.example.blogs.application

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.annotation.CallSuper
import com.example.blogs.base.utils.ApplicationLanguage
import com.example.blogs.base.utils.ISharedPreferences
import com.example.blogs.base.utils.LocaleHelper
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var sharedPreferences: ISharedPreferences

    private var language: String = if (Locale.getDefault().language.equals(ApplicationLanguage.ENGLISH, true)) {
        ApplicationLanguage.ENGLISH
    } else {
        ApplicationLanguage.ARABIC
    }

    override fun onCreate() {
        super.onCreate()
        LocaleHelper.onAttach(this, sharedPreferences.language)
    }

    @CallSuper
    override fun onConfigurationChanged(newConfig: Configuration) {
        LocaleHelper.onAttach(this, language)
        super.onConfigurationChanged(newConfig)
    }

    @CallSuper
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!, language))
    }
}
