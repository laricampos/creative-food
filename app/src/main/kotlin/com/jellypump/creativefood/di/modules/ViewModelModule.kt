package com.jellypump.creativefood.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.jellypump.creativefood.di.ViewModelFactory
import com.jellypump.creativefood.di.ViewModelKey
import com.jellypump.creativefood.ui.viewmodel.TagViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(TagViewModel::class)
    abstract fun bindTagViewModel(viewModel: TagViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}