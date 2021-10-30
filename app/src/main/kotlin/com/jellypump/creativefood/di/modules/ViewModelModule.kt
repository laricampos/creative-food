package com.jellypump.creativefood.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jellypump.creativefood.di.ViewModelFactory
import com.jellypump.creativefood.di.ViewModelKey
import com.jellypump.creativefood.ui.screen.home.HomeViewModel
import com.jellypump.creativefood.ui.screen.ingredient.manage.ManageIngredientViewModel
import com.jellypump.creativefood.ui.screen.ingredient.add.AddIngredientViewModel
import com.jellypump.creativefood.ui.screen.recipe.GenerateRecipeViewModel
import com.jellypump.creativefood.ui.screen.tag.AddTagViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(ManageIngredientViewModel::class)
    abstract fun bindManageIngredientViewModel(viewModel: ManageIngredientViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTagViewModel::class)
    abstract fun bindTagViewModel(viewModel: AddTagViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddIngredientViewModel::class)
    abstract fun bindAddIngredientViewModel(viewModel: AddIngredientViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(GenerateRecipeViewModel::class)
    abstract fun bindGenerateRecipeViewModel(viewModel: GenerateRecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}