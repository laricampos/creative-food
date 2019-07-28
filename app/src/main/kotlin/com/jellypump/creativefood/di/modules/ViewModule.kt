package com.jellypump.creativefood.di.modules

import com.jellypump.creativefood.ui.screen.MainActivity
import com.jellypump.creativefood.ui.screen.home.HomeFragment
import com.jellypump.creativefood.ui.screen.ingredient.AddIngredientFragment
import com.jellypump.creativefood.ui.screen.ingredient.ManageIngredientsFragment
import com.jellypump.creativefood.ui.screen.intro.IntroFragment
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
    fun introFragment(): IntroFragment

    @ContributesAndroidInjector
    fun homeFragment(): HomeFragment

    @ContributesAndroidInjector
    fun addIngredientFragment(): AddIngredientFragment

    @ContributesAndroidInjector
    fun manageIngredientsFragment(): ManageIngredientsFragment

    @ContributesAndroidInjector
    fun addTagFragment(): AddTagFragment
}