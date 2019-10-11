package com.jellypump.creativefood.di.modules

import android.content.Context
import androidx.room.Room
import com.jellypump.creativefood.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "foodcraft_database").build()
    }

    @Provides
    @Singleton
    fun provideIngredientDAO(appDatabase: AppDatabase) = appDatabase.ingredientDao()

    @Provides
    @Singleton
    fun provideTagDAO(appDatabase: AppDatabase) = appDatabase.tagDao()
}