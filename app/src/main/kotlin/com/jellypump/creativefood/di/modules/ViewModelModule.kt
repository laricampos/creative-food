package com.jellypump.creativefood.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jellypump.creativefood.di.ViewModelFactory
import com.jellypump.creativefood.di.ViewModelKey
import com.jellypump.creativefood.ui.screen.ingredient.add.AddIngredientViewModel
import com.jellypump.creativefood.ui.screen.ingredient.IngredientViewModel
import com.jellypump.creativefood.ui.screen.tag.AddTagViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(IngredientViewModel::class)
    abstract fun bindIngredientViewModel(viewModel: IngredientViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTagViewModel::class)
    abstract fun bindTagViewModel(viewModel: AddTagViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddIngredientViewModel::class)
    abstract fun bindAddIngredientViewModel(viewModel: AddIngredientViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}