package com.example.blogs.base.di

import com.example.blogs.base.utils.ConnectivityUtils
import com.example.blogs.base.utils.IConnectivityUtils
import com.example.blogs.base.utils.ISharedPreferences
import com.example.blogs.base.utils.SharedPreferences
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

@InstallIn(ApplicationComponent::class)
@Module
abstract class MainModule {

    @Binds
    abstract fun bindConnectivityUtils(connectivityUtils: ConnectivityUtils): IConnectivityUtils

    @Binds
    abstract fun bindSharedPreferences(sharedPreferences: SharedPreferences): ISharedPreferences
}
