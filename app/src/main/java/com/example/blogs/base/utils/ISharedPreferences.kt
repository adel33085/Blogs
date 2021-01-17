package com.example.blogs.base.utils

import androidx.annotation.StringDef
import com.example.blogs.base.utils.ApplicationLanguage.Companion.ARABIC
import com.example.blogs.base.utils.ApplicationLanguage.Companion.ENGLISH
import kotlin.reflect.KClass

interface ISharedPreferences {

    @ApplicationLanguage
    var language: String

    fun putString(key: String, value: String?)

    fun getString(key: String): String?

    fun putBoolean(key: String, value: Boolean)

    fun getBoolean(key: String): Boolean

    fun putInt(key: String, value: Int)

    fun getInt(key: String, defaultValue: Int = -1): Int

    fun <T> putObject(key: String, value: T)

    fun <T : Any> getObject(key: String, type: KClass<T>): T?

    fun clearData()
}

@Retention(AnnotationRetention.SOURCE)
@StringDef(ARABIC, ENGLISH)
annotation class ApplicationLanguage {
    companion object {
        const val ARABIC = "ar"
        const val ENGLISH = "en"
    }
}
