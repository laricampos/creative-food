package com.jellypump.creativefood.di.modules

import com.jellypump.creativefood.ui.screen.MainActivity
import com.jellypump.creativefood.ui.screen.tag.AddTagFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ViewModule {

    // Activities

    @ContributesAndroidInjector
    fun mainActivity(): MainActivity

    // Fragments

    @ContributesAndroidInjector
    fun addTagFragment(): AddTagFragment
}