package com.jellypump.creativefood.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jellypump.creativefood.db.dao.IngredientDao
import com.jellypump.creativefood.db.dao.TagDao
import com.jellypump.creativefood.db.model.Ingredient
import com.jellypump.creativefood.db.model.Tag

@Database(entities = [Ingredient::class, Tag::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun tagDao(): TagDao
}