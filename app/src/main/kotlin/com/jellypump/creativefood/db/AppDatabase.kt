package com.jellypump.creativefood.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jellypump.creativefood.db.dao.IngredientDao
import com.jellypump.creativefood.db.dao.TagDao
import com.jellypump.creativefood.db.entity.IngredientEntity
import com.jellypump.creativefood.db.entity.IngredientTagEntity
import com.jellypump.creativefood.db.entity.TagEntity

@Database(entities = [IngredientEntity::class, TagEntity::class, IngredientTagEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun tagDao(): TagDao
}